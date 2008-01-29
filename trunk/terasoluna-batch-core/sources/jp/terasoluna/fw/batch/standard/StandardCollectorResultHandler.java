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

package jp.terasoluna.fw.batch.standard;

import jp.terasoluna.fw.batch.core.CollectorResult;
import jp.terasoluna.fw.batch.core.CollectorResultHandler;
import jp.terasoluna.fw.batch.core.JobStatus;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <code>CollectorResultHandler</code> インタフェースの標準実装クラス。
 * 
 * <p>このクラスでは、コレクタ処理結果が0件である場合にもエラーとせずにそのまま
 * 継続する。</p>
 * 
 * <p>このクラスでは、コレクタの処理結果にかかわらず常に情報/エラーログに出力
 * する。ただし、情報ログはコレクタ処理結果が0件であった場合に出力する。</p>
 * 
 */
public class StandardCollectorResultHandler 
    implements CollectorResultHandler {

    /**
     * ログインスタンス。
     */
    private static Log log = 
        LogFactory.getLog(StandardCollectorResultHandler.class);

    /**
     * コレクタ処理結果を処理する。
     * 
     * @param collectorResult コレクタ処理結果
     * @param jobStatus ジョブステータス
     */
    public void handle(CollectorResult collectorResult, JobStatus jobStatus) {
        switch (collectorResult.getReturnCode()) {
            case NORMAL_CONTINUE:
            case NORMAL_END:
                if (collectorResult.getCollected() == 0) {
                    writeInfoLog(jobStatus, collectorResult);
                }
                break;
            case ERROR_CONTINUE:
                writeErrorLog(jobStatus, collectorResult);
                break;
            case ERROR_END:
                jobStatus.setJobState(JobStatus.STATE.ENDING_ABNORMALLY);
                writeErrorLog(jobStatus, collectorResult);
                break;
            default:
                throw new IllegalArgumentException(
                        collectorResult.getCollected() + " illegal ReturnCode");
        }
    }

    /**
     * コレクタ処理結果での情報ログを出力する。
     * 
     * @param jobStatus ジョブステータス
     * @param collectorResult コレクタ処理結果
     */
    protected void writeInfoLog(JobStatus jobStatus,
            CollectorResult collectorResult) {
        StringBuilder logStr = new StringBuilder();
        logStr.append(" [jobId=");
        logStr.append(jobStatus.getJobId());
        logStr.append("]");
        logStr.append(" [jobRequestNo=");
        logStr.append(jobStatus.getJobRequestNo());
        logStr.append("]");
        logStr.append(" [partitionNo=");
        logStr.append(jobStatus.getPartitionNo());
        logStr.append("]");
        logStr.append(" [CollectorResult Info:");
        logStr.append(collectorResult);
        logStr.append("]");
        log.info(logStr.toString());
    }

    /**
     * コレクタ処理結果でのエラーログを出力する。
     * 
     * @param jobStatus ジョブステータス
     * @param collectorResult コレクタ処理結果
     */
    protected void writeErrorLog(JobStatus jobStatus,
            CollectorResult collectorResult) {
        StringBuilder logStr = new StringBuilder();
        logStr.append(" [jobId=");
        logStr.append(jobStatus.getJobId());
        logStr.append("]");
        logStr.append(" [jobRequestNo=");
        logStr.append(jobStatus.getJobRequestNo());
        logStr.append("]");
        logStr.append(" [partitionNo=");
        logStr.append(jobStatus.getPartitionNo());
        logStr.append("]");
        logStr.append(" [CollectorResult Error:");
        logStr.append(collectorResult);
        logStr.append("]");
        log.error(logStr.toString());
    }
}
