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

import jp.terasoluna.fw.batch.core.Chunk;
import jp.terasoluna.fw.batch.core.JobStatus;
import jp.terasoluna.fw.batch.core.JobWorker;
import jp.terasoluna.fw.batch.core.Workable;

/**
 * リスタートポイント登録用ワーカクラス。
 * 
 */
public class RestartPointUpdater implements Workable<Chunk> {

    /**
     * 起動するワーカ。
     */
    private JobWorker jobWorker = null;
    
    /**
     * ジョブリスタートテーブルハンドラ。
     */
    private JobRestartTableHandler jobRestartTableHandler = null;

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
}
