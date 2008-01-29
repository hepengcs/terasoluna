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

package jp.terasoluna.fw.batch.core;

import jp.terasoluna.fw.batch.openapi.JobContext;
import jp.terasoluna.fw.batch.openapi.ReturnCode;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * コレクター抽象クラス。
 *
 * <p>
 * 対象データ取得処理を開始する。<br>
 * 対象データ取得処理はこの<code>Abstract</code>
 * クラスを実装したクラスにて行う。
 * </p>
 * 
 * @see jp.terasoluna.fw.batch.ibatissupport.IBatisDbCollectorImpl
 * @see jp.terasoluna.fw.batch.init.JobRequestInfoCollector
 * @see jp.terasoluna.fw.batch.standard.ListPropertyCollector
 * @see jp.terasoluna.fw.batch.standard.StringArrayPropertyCollector
 * @param <T> ジョブコンテクストのサブクラス
 */
public abstract class AbstractCollector<T extends JobContext> 
    implements Collector<T> {

    /**
     * ログインスタンス。
     */
    private static Log log = LogFactory.getLog(AbstractCollector.class);

    /**
     * 対象データハンドラ。
     */
    private CollectedDataHandlerFactory collectedDataHandlerFactory = null;
    
    /**
     * 対象データ取得処理を開始する。
     *
     * @param jobContext ジョブコンテクスト
     * @param workQueue ワークキュー
     * @param jobStatus ジョブステータス
     * @return コレクタの処理結果
     */
    public CollectorResult collect(T jobContext, WorkQueue workQueue,
            JobStatus jobStatus) {
        //開始ログ
        writeStartLog(jobStatus);
        CollectedDataHandler collectedDataHandler
            = collectedDataHandlerFactory.getHandler(workQueue, jobContext);
        CollectorResult collectorResult = null;
        try {
            collectorResult = doCollect(jobContext, collectedDataHandler,
                    jobStatus);
            collectedDataHandler.close();
        } catch (QueueingException e) {
            if (jobStatus.isExecuting()) {
                collectedDataHandler.close();
                throw e;
            } else {
                collectorResult =
                    new CollectorResult(ReturnCode.NORMAL_END, -1);
            }
        } catch (RuntimeException e) {
            collectedDataHandler.close();
            throw e;
        }


        writeEndLog(jobStatus);
        return collectorResult;
    }

    /**
     * 収集データ処理ハンドラを設定する。
     * 
     * @param collectedDataHandlerFactory 収集したデータを処理するハンドラ
     */
    public void setCollectedDataHandlerFactory(
            CollectedDataHandlerFactory collectedDataHandlerFactory) {
        this.collectedDataHandlerFactory = collectedDataHandlerFactory;
    }

    /**
     * 対象データを取得する。
     *
     * @param jobContext ジョブコンテクスト
     * @param collectedDataHandler 収集したデータを処理するハンドラ
     * @param jobStatus ジョブステータス
     * @return コレクタの処理結果
     */
    protected abstract CollectorResult doCollect(T jobContext,
            CollectedDataHandler collectedDataHandler, JobStatus jobStatus);

    /**
     * AbstractCollectorの開始ログを出力する。
     * 
     * @param jobStatus ジョブステータス
     */
    private void writeStartLog(JobStatus jobStatus) {
        if (log.isDebugEnabled()) {
            StringBuilder logStr = new StringBuilder();
            logStr.append("【START】");
            logStr.append(" [jobId=");
            logStr.append(jobStatus.getJobId());
            logStr.append("]");
            logStr.append(" [jobRequestNo=");
            logStr.append(jobStatus.getJobRequestNo());
            logStr.append("]");
            logStr.append(" [partitionNo=");
            logStr.append(jobStatus.getPartitionNo());
            logStr.append("]");
            logStr.append(" [ClassName=");
            logStr.append(this.getClass().getSimpleName());
            logStr.append("]");
            logStr.append(" [JobState=");
            logStr.append(jobStatus.getJobState());
            logStr.append("]");
            log.debug(logStr.toString());
        }
    }

    /**
     * AbstractCollectorの終了ログを出力する。
     * 
     * @param jobStatus ジョブステータス
     */
    private void writeEndLog(JobStatus jobStatus) {
        if (log.isDebugEnabled()) {
            StringBuilder logStr = new StringBuilder();
            logStr.append("【 END 】");
            logStr.append(" [jobId=");
            logStr.append(jobStatus.getJobId());
            logStr.append("]");
            logStr.append(" [jobRequestNo=");
            logStr.append(jobStatus.getJobRequestNo());
            logStr.append("]");
            logStr.append(" [partitionNo=");
            logStr.append(jobStatus.getPartitionNo());
            logStr.append("]");
            logStr.append(" [ClassName=");
            logStr.append(this.getClass().getSimpleName());
            logStr.append("]");
            logStr.append(" [JobState=");
            logStr.append(jobStatus.getJobState());
            logStr.append("]");
            logStr.append(" [collected=");
            logStr.append(jobStatus.getCollected());
            logStr.append("]");
            log.debug(logStr.toString());
        }
    }
}
