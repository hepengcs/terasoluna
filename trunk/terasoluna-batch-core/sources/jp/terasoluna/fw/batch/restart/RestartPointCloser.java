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

package jp.terasoluna.fw.batch.restart;

import java.util.List;

import jp.terasoluna.fw.batch.core.Chunk;
import jp.terasoluna.fw.batch.core.JobStatus;
import jp.terasoluna.fw.batch.core.JobWorker;
import jp.terasoluna.fw.batch.core.Workable;
import jp.terasoluna.fw.batch.openapi.JobContext;
import jp.terasoluna.fw.batch.openapi.SupportLogic;

/**
 * リスタート情報クローズ用ワーカクラス。
 * 
 */
public class RestartPointCloser implements Workable<Chunk> {

    /**
     * 起動するワーカ。
     */
    private JobWorker jobWorker = null;
    
    /**
     * ジョブリスタートテーブルハンドラ。
     */
    private JobRestartTableHandler jobRestartTableHandler = null;

    /**
     * サポートロジックのリスト。
     */
    private List<SupportLogic<JobContext>> supportLogicList = null;

    /**
     * リスタートポイントクリア用SQLキー。
     */
    private String sqlKey = null;
    
    /**
     * チャンクの処理を行う。
     * 
     * <p>
     * チャンクの処理が成功した場合に、ジョブリスタートポイント登録処理を行う。
     * </p>
     * 
     * @param chunk チャンク
     * @param jobStatus ジョブ状態
     */
    public void work(Chunk chunk, JobStatus jobStatus) {
        jobWorker.work(chunk, jobStatus);

        //リスタートポイント登録処理
        if (jobStatus.isExecuting()
                || jobStatus.getJobState() == JobStatus.STATE.ENDING_NORMALLY
                || jobStatus.isShutdownGraceful()) {
            jobRestartTableHandler.registerRestartPoint(chunk.getJobContext(),
                    jobStatus);
            //リスタートポイントクリア処理
            if (supportLogicList == null || supportLogicList.size() == 0) {
                jobRestartTableHandler.restartPointClear(jobStatus, sqlKey);
            }
        }
    }

    /**
     * ジョブリスタートテーブルハンドラを設定する。
     * 
     * @param jobRestartTableHandler ジョブリスタートテーブルハンドラ
     */
    public void setJobRestartTableHandler(
            JobRestartTableHandler jobRestartTableHandler) {
        this.jobRestartTableHandler = jobRestartTableHandler;
    }

    /**
     * ジョブワーカを設定する。
     * 
     * @param jobWorker ジョブワーカ
     */
    public void setJobWorker(JobWorker jobWorker) {
        this.jobWorker = jobWorker;
    }

    /**
     * サポートロジックのリストを設定する。
     * 
     * @param supportLogicList サポートロジックのリスト
     */
    public void setSupportLogicList(
            List<SupportLogic<JobContext>> supportLogicList) {
        this.supportLogicList = supportLogicList;
    }
    
    /**
     * リスタートポイントクリア用SQLキーを設定する。
     * 
     * @param sqlKey リスタートポイントクリア用SQLキー
     */
    public void setSqlKey(String sqlKey) {
        this.sqlKey = sqlKey;
    }
}
