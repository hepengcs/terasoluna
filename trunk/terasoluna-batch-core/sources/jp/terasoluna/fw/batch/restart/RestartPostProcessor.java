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

import jp.terasoluna.fw.batch.core.JobStatus;
import jp.terasoluna.fw.batch.core.SupportProcessor;
import jp.terasoluna.fw.batch.openapi.JobContext;
import jp.terasoluna.fw.batch.openapi.SupportLogic;

/**
 * ジョブリスタート時のジョブ後処理用クラス。<BR>
 * ジョブ後処理が正常に終了したらリスタートポイントクリア処理を行う。
 * 
 */
public class RestartPostProcessor implements SupportProcessor {

    /**
     * ジョブリスタートテーブルハンドラのインスタンス
     */
    private JobRestartTableHandler jobRestartTableHandler = null;

    /**
     * ジョブ後処理用プロセッサー
     */
    private SupportProcessor postProcessor = null;

    /**
     * サポートロジックのリスト。
     */
    private List<SupportLogic<JobContext>> supportLogicList = null;

    /**
     * リスタートポイントクリア用SQLキー。
     */
    private String sqlKey = null;
    
    /**
     * Rootマネジャー有無
     */
    private boolean parentManager = false;

    /**
     * ジョブ後処理用プロセッサーメソッド。<BR>
     * ジョブ後処理が正常に終了したらリスタートポイントクリア処理を行う。
     * 
     * @param jobContext
     *            ジョブコンテキスト
     * @param jobStatus
     *            ジョブ処理状況
     */
    public void process(JobContext jobContext, JobStatus jobStatus) {
        postProcessor.process(jobContext, jobStatus);

        //リスタートポイントのクリア処理（削除）
        //ジョブが異常終了していない
        if (!isAbendOrSuspending(jobStatus)
                && (parentManager || (supportLogicList != null 
                && supportLogicList.size() > 0))) {
            jobRestartTableHandler.restartPointClear(jobStatus, sqlKey);
        }
    }

    /**
     * ジョブリスタートテーブルハンドラのインスタンスを設定する。
     * 
     * @param jobRestartTableHandler
     *            ジョブリスタートテーブルハンドラのインスタンス
     */
    public void setJobRestartTableHandler(
            JobRestartTableHandler jobRestartTableHandler) {
        this.jobRestartTableHandler = jobRestartTableHandler;
    }

    /**
     * 処理スキップを評価する。
     * 
     * @return 評価結果（リスタートテーブルクリア処理は、業務的な後処理がない
     * 場合でもスキップしない）
     */
    public boolean canSkip() {
        // リスタートテーブルクリア処理は、業務的な後処理がない場合でもスキップ
        // しない
        return false;
    }

    /**
     * ジョブ後処理を設定する。
     * 
     * @param postProcessor
     *            ジョブ後処理
     */
    public void setPostProcessor(SupportProcessor postProcessor) {
        this.postProcessor = postProcessor;
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

    /**
     *  Rootマネジャー有無を設定する。
     * 
     * @param parentManager Rootマネジャー有無
     */
    public void setParentManager(boolean parentManager) {
        this.parentManager = parentManager;
    }
    
    /**
     *  JobStatusの状態を確認する。
     * 
     * @param jobStatus ジョブ処理状況
     * @return 親、子のJobStatusの処理状態中<code>ENDING_ABNORMALLY</code>、
     * <code>SUSPENDING</code>が一つでもあれば<code>true</code>を返す。
     */
    private boolean isAbendOrSuspending(JobStatus jobStatus) {
        if (jobStatus.getChildJobStatusList() == null
                || jobStatus.getChildJobStatusList().isEmpty()) {

            if (jobStatus.getJobState() == JobStatus.STATE.ENDING_ABNORMALLY
                    || jobStatus.getJobState() == JobStatus.STATE.SUSPENDING) {
                return true;
            } else {
                return false;
            }
        }

        for (JobStatus childJobStatus : jobStatus.getChildJobStatusList()) {
            if (isAbendOrSuspending(childJobStatus)) {
                return true;
            }
        }
        return false;
    }
}
