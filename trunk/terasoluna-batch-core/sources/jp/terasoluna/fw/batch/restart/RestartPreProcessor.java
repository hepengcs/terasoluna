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

import jp.terasoluna.fw.batch.core.JobStatus;
import jp.terasoluna.fw.batch.core.SupportProcessor;
import jp.terasoluna.fw.batch.openapi.JobContext;

/**
 * ジョブリスタート時のジョブ前処理用クラス。<BR>
 * ジョブのリスタートであればジョブジョブ前処理をスキップする。<BR>
 * 分割ジョブの親ジョブの前処理の場合は親ジョブ用リスタートポイントを登録する。
 * 
 */
public class RestartPreProcessor implements SupportProcessor {

    /**
     * リスタートテーブルハンドラのインスタンス
     */
    private JobRestartTableHandler jobRestartTableHandler = null;

    /**
     * 実行するジョブ前処理
     */
    private SupportProcessor preProcessor = null;
    
    /**
     * Rootマネジャー有無
     */
    private boolean parentManager = false;

    /**
     * リスタート時のジョブ前処理を実行する。
     * 
     * @param jobContext ジョブコンテキスト
     * @param jobStatus ジョブ処理状況　
     */
    public void process(JobContext jobContext, JobStatus jobStatus) {
        // リスタートの場合には、前処理は起動しない
        if (jobStatus.getJobState() == JobStatus.STATE.RESTARTED) {
            return;
        }
        
        preProcessor.process(jobContext, jobStatus);
        if (!jobStatus.isContinue()) {
            return;
        }

        //Partitionの場合はリスタート用Root登録
        if (parentManager) {
            jobRestartTableHandler.registerRestartPoint(jobContext, jobStatus);
        }
    }
    
    /**
     * リスタートテーブルハンドラのインスタンスを設定する。
     * 
     * @param jobRestartTableHandler リスタートテーブルハンドラのインスタンス
     */
    public void setJobRestartTableHandler(
            JobRestartTableHandler jobRestartTableHandler) {
        this.jobRestartTableHandler = jobRestartTableHandler;
    }

    /**
     * 前処理をスキップするかを評価する。
     * 
     * @return 評価結果
     */
    public boolean canSkip() {
        return false;
    }

    /**
     * 実行するジョブ前処理を設定する。
     * 
     * @param preProcessor 実行するジョブ前処理
     */
    public void setPreProcessor(SupportProcessor preProcessor) {
        this.preProcessor = preProcessor;
    }

    /**
     *  Rootマネジャー有無を設定する。
     * 
     * @param parentManager Rootマネジャー有無
     */
    public void setParentManager(boolean parentManager) {
        this.parentManager = parentManager;
    }
}
