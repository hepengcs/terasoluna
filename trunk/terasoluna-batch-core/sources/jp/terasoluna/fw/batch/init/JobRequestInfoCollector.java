/*
 * Copyright (c) 2007 NTT DATA Corporation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package jp.terasoluna.fw.batch.init;

import jp.terasoluna.fw.batch.core.AbstractCollector;
import jp.terasoluna.fw.batch.core.CollectedDataHandler;
import jp.terasoluna.fw.batch.core.CollectorException;
import jp.terasoluna.fw.batch.core.CollectorResult;
import jp.terasoluna.fw.batch.core.JobStatus;
import jp.terasoluna.fw.batch.openapi.JobContext;
import jp.terasoluna.fw.batch.openapi.ReturnCode;
import jp.terasoluna.fw.batch.springsupport.transaction.TransactionalJobStatus;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 非同期ジョブ起動時のジョブ依頼情報取得用クラス。
 *
 */
public class JobRequestInfoCollector extends AbstractCollector {

    /**
     * ログインスタンス。
     */
    private static Log log = LogFactory
            .getLog(JobRequestInfoCollector.class);
    
    /**
     * ジョブ管理テーブルハンドラクラスのインスタンス。
     */
    protected JobControlTableHandler jobControlTableHandler = null;

    /**
     * ジョブ管理テーブルの監視周期（秒）。
     */
    private long intervalSeconds = 0L;

    /**
     * リフレッシュカウント。
     */
    private int refreshCount = 0;

    /**
     * 実行ジョブ定義ファイルパス。
     */
    protected String jobBeanPath = null;

    /**
     * ジョブ依頼情報取得、監視処理の実行。<BR>
     * ジョブ監視を行い取得対象のジョブが無い場合はデーモン起動用ジョブBean定義
     * の監視周期時間を待機した後、再取得処理を行う。<BR>
     * ジョブ依頼情報の取得は直前取得したジョブリクエスト番号より大きいものを取
     * 得するがこの条件はデーモン起動用ジョブBean定義のリフレッシュ番号の回数毎
     * にリフレッシュされる。
     * 
     *
     * @param jobContext ジョブコンテキスト
     * @param collectedDataHandler ワーカキュー
     * @param jobStatus 処理状況
     * @return コレクタ処理結果
     */
    @Override
    protected CollectorResult doCollect(JobContext jobContext, 
            CollectedDataHandler collectedDataHandler,
            JobStatus jobStatus) {

        int collected = 0;
        int selectCount = 0;
        JobInfo jobControlInfo = null;
        
        TransactionalJobStatus transactionalJobStatus = 
            (TransactionalJobStatus) jobStatus;

        while (true) {
            
            jobControlInfo = getJobMessageInfo(selectCount, jobControlInfo);

            if (jobControlInfo == null) {
                try {
                    Thread.sleep(intervalSeconds * 1000);
                    selectCount = 0;
                } catch (InterruptedException e) {
                    throw new CollectorException(e, null);
                }
            } else {
                transactionalJobStatus.beginTransaction();
                //キューに入れる前にジョブ起動状況を開始に更新する。
                int count = updateJobStart(jobControlInfo);
                
                // 更新に成功した場合のみ結果をキューに入れる。
                if (count == 1) {
                    collectedDataHandler.handle(jobControlInfo, collected++);
                    transactionalJobStatus.commit();
                } else {
                    transactionalJobStatus.rollback();
                }
                
                if (jobBeanPath != null) {
                    overrideDescriptionPath(jobControlInfo);
                }

                // デーモン終了チェック
                if (AbstractJobControlInfo.STOP_DEMON.equals(jobControlInfo
                        .getJobId())) {
                    if (log.isDebugEnabled()) {
                        log.debug("stopped batch daemon ");
                    }
                    collectedDataHandler.close();
                    return 
                        new CollectorResult(ReturnCode.NORMAL_END, collected);
                }
                
                // テーブルの検索基準位置を初期化する。
                if (selectCount >= refreshCount) {
                    selectCount = 0;
                } else {
                    selectCount++;
                }
            }
        }
    }

    
    /**
     * キューに入れる前にジョブ起動状況を開始に更新する
     * 
     * @param jobInfo 対象ジョブ情報
     * @return 更新行数
     */
    protected int updateJobStart(JobInfo jobInfo) {
        return jobControlTableHandler.updateJobStart(jobInfo);
    }
    
    /**
     * 処理対象データの取得
     * 
     * @param selectCount 取得データ数
     * @param jobInfo 対象ジョブ情報
     * @return 対象ジョブ情報
     */
    protected JobInfo getJobMessageInfo(int selectCount, JobInfo jobInfo) {
        // ジョブ依頼情報の取得
        if (selectCount == 0) {
            return jobControlTableHandler.getJobRequestData();
        } else {
            return jobControlTableHandler.getJobRequestData(jobInfo);
        }
    }

    /**
     * ジョブBean定義パスの上書き
     * 
     * @param jobInfo 対象ジョブ情報 
     */
    protected void overrideDescriptionPath(JobInfo jobInfo) {
        // 上書きはしない
    }
    
    /**
     * ジョブ管理テーブルハンドラクラスのインスタンスを設定する。
     *
     * @param jobControlTableHandler ジョブ管理テーブルハンドラクラスのインスタ
     * ンス
     */
    public void setJobControlTableHandler(
            JobControlTableHandler jobControlTableHandler) {
        this.jobControlTableHandler = jobControlTableHandler;
    }

    /**
     * 監視周期を設定する。
     *
     * @param intervalSeconds 監視周期（秒）
     */
    public void setIntervalSeconds(long intervalSeconds) {
        this.intervalSeconds = intervalSeconds;
    }
    
    /**
     * リフレッシュカウントを設定する。
     * @param refreshCount リフレッシュカウント。
     */
    public void setRefreshCount(int refreshCount) {
        this.refreshCount = refreshCount;
    }
    
    /**
     * 実行ジョブ定義ファイルパス。
     * 
     * @param jobBeanPath 実行ジョブ定義ファイルパス
     */
    public void setJobBeanPath(String jobBeanPath) {
        throw new UnsupportedOperationException();
    }
}
