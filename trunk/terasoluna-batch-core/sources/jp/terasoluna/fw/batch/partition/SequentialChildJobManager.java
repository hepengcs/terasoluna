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

package jp.terasoluna.fw.batch.partition;

import jp.terasoluna.fw.batch.core.JobManager;
import jp.terasoluna.fw.batch.core.JobStatus;

/**
 * <p>分割ジョブにおいて、子ジョブを多重度１で逐次実行するためのジョブマネージ
 * ャ。逐次実行している子ジョブのどれかが途中で終了した場合に、残りの子ジョブを
 * 実行せずにジョブを停止する。</p>
 * 
 * <p>作業終了時に、このジョブマネージャのジョブステータスが終了状態であるときに
 * 親のジョブステータスをこのジョブと同じジョブステータスに更新する。</p>
 * 
 */
public class SequentialChildJobManager extends JobManager {

    /**
     * 作業終了処理を行う。
     * 
     * @param parentJobStatus 起動元のジョブステータス
     * @param jobStatus このジョブマネージャのジョブステータス
     */
    @Override
    protected void finishWork(
            JobStatus parentJobStatus, JobStatus jobStatus) {
        if (jobStatus.isExecuting() || jobStatus.isShutdownGraceful()
                || jobStatus.isShutdownImmediate()) {
            jobStatus.setJobState(JobStatus.STATE.ENDING_NORMALLY);
        } else {
            parentJobStatus.setJobState(jobStatus.getJobState());
        }
    }

}
