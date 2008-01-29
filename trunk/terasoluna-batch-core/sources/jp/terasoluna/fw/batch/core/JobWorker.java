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

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import jp.terasoluna.fw.batch.openapi.JobContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <code>Chunk</code> を処理単位として、<code>Chunk</code> が持つデータを入力
 * とするビジネスロジックを実行する。
 *
 * <p>ジョブを構成する <code>Workable</code> インタフェースの実装の階層構造
 * において、最も最下層（最も小さな作業単位）に位置する。<code>JobWorker</code>
 * は、<code>Chunk</code> に含まれるすべてのデータに対して、
 * 繰り返しビジネスロジックを起動する。</p>
 *
 * <p>ビジネスロジックの起動の際には、<code>Chunk</code> に含まれる
 * <code>JobContext</code> が渡される。
 * ビジネスロジックの実行は、<code>blogicExecutor</code> 属性に設定されている
 * ビジネスロジック実行クラスのインスタンスに委譲される。</p>
 *
 * <p><code>JobWorker</code> では、<code>Chunk</code> に含まれるすべてのデータに
 * 渡って、バッチ更新リストが保持される。バッチ更新リストは、
 * ビジネスロジックからバッチ更新の依頼がある毎に随時追加される。
 * バッチ更新リストは、<code>Chunk</code>に含まれるすべてのデータに対する
 * ビジネスロジックの実行が終了した後、処理される。
 * ただし、バッチ更新リストの処理は、<code>batchUpdateResultHandler</code>
 * 属性に設定されているバッチ更新プロセッサに委譲される。</p>
 *
 * <p>バッチ更新プロセッサを起動後に、バッチ更新の処理結果をジョブステータスへの
 * 反映や、ログ出力などが行えるように <code>batchUpdateResultHandler</code>
 * 属性に設定されたバッチ更新処理結果ハンドラが起動される。</p>
 *
 * <p>バッチ更新プロセッサ、およびバッチ更新処理結果ハンドラで例外が発生した
 * 場合には、例外ハンドラによって処理される。例外ハンドラは、
 * <code>exceptionHandlerMap</code>属性に設定されている例外ハンドラ定義から
 * 取得する。バッチ更新プロセッサ、およびバッチ更新処理結果で例外が発生した場合
 * のハンドラ例外ハンドラには、<code>BatchUpdateException</code> 例外をキーと
 * して定義されているものが使われる。</p>
 *
 * <p>例外ハンドラ定義から、<code>BatchUpdateException</code> 例外をキーをとする
 * 例外ハンドラが取得できなかった場合には、
 * <code>defaultJobExceptionHandler</code>属性に設定されている
 * デフォルト例外ハンドラが、例外処理に使われる。</p>
 *
 */
public class JobWorker implements Workable<Chunk> {

    /**
     * ログインスタンス。
     */
    private static Log log = LogFactory.getLog(JobWorker.class);

    /**
     * 例外ハンドラを格納したMap。
     */
    protected LinkedHashMap<JobException, JobExceptionHandler> 
               exceptionHandlerMap = null;

    /**
     * デフォルト例外ハンドラ。
     */
    protected JobExceptionHandler defaultJobExceptionHandler = null;

    /**
     * バッチ更新プロセッサ。
     */
    protected BatchUpdateProcessor batchUpdateProcessor = null;
    
    /**
     * バッチ更新の処理結果ハンドラ。
     */
    protected BatchUpdateResultHandler batchUpdateResultHandler = null;

    /**
     * ビジネスロジック実行クラスのインスタンス。
     */
    private BLogicExecutor blogicExecutor = null;
    
    /**
     * <code>Chunk</code> を処理する。
     *
     * @param chunk 処理対象データを格納したチャンク
     * @param jobStatus ジョブステータス
     */
    public void work(Chunk chunk, JobStatus jobStatus) {
        writeStartLog(jobStatus);

        if (chunk.size() == 0) {
            throw new IllegalArgumentException("chunk size is 0");
        }
        
        List<LinkedHashMap<String, Object>> batchUpdateMapList
            = new ArrayList<LinkedHashMap<String, Object>>();
        
        for (Object blogicInputData : chunk) {

            //強制終了
            if (jobStatus.isShutdownImmediate()) {
                jobStatus.suspend();
                return;
            }

            // BLogic、BLogic処理結果ハンドラの呼び出し
            blogicExecutor.executeBLogic(blogicInputData, chunk.getJobContext(),
                    jobStatus, batchUpdateMapList);
            
            if (!jobStatus.isContinue()) {
                return;
            }
        }

        // バッチ更新実行
        processBatchUpdate(chunk.getJobContext(), jobStatus,
                batchUpdateMapList);

        writeEndLog(jobStatus);
    }

    /**
     * バッチ更新を行う。
     *
     * @param jobContext ジョブコンテキスト
     * @param jobStatus ジョブ処理状況
     * @param batchUpdateMapList バッチ更新リスト
     */
    protected void processBatchUpdate(JobContext jobContext,
            JobStatus jobStatus,
            List<LinkedHashMap<String, Object>> batchUpdateMapList) {
        if (batchUpdateMapList.size() == 0) {
            return;
        }
        try {
            batchUpdateProcessor.processBatchUpdate(batchUpdateMapList);
            batchUpdateResultHandler.handle(jobStatus, batchUpdateMapList);
        } catch (RuntimeException e) {
            // BLogic、BLogic処理結果ハンドラで発生した例外の処理
            BatchUpdateException wrappingException
                = new BatchUpdateException(e, batchUpdateMapList);

            JobExceptionHandler handler
                = ExceptionHandlerUtil.getJobExceptionHandler(wrappingException,
                                                            exceptionHandlerMap,
                                                    defaultJobExceptionHandler);

            handler.handlException(jobContext, wrappingException, jobStatus);
        }
    }

    /**
     * 例外ハンドラ定義を設定する。
     *
     * @param exceptionHandlerMap 例外ハンドラ定義
     */
    public void setExceptionHandlerMap(
         LinkedHashMap<JobException, JobExceptionHandler> exceptionHandlerMap) {
        this.exceptionHandlerMap = exceptionHandlerMap;
    }

    /**
     * バッチ更新用クラスのインスタンスを設定する。
     *
     * @param batchUpdateProcessor バッチ更新用クラスのインスタンス
     */
    public void setBatchUpdateProcessor(
            BatchUpdateProcessor batchUpdateProcessor) {
        this.batchUpdateProcessor = batchUpdateProcessor;
    }

    /**
     * ビジネスロジック実行用クラスのインスタンスを設定する。
     *
     * @param blogicExecutor ビジネスロジック実行用クラスのインスタンス
     */
    public void setBlogicExecutor(BLogicExecutor blogicExecutor) {
        this.blogicExecutor = blogicExecutor;
    }

    /**
     * バッチ更新の処理結果ハンドラを設定する。
     * 
     * @param batchUpdateResultHandler バッチ更新の処理結果ハンドラ
     */
    public void setBatchUpdateResultHandler(
            BatchUpdateResultHandler batchUpdateResultHandler) {
        this.batchUpdateResultHandler = batchUpdateResultHandler;
    }

    /**
     * デフォルト例外ハンドラを設定する。
     * @param defaultJobExceptionHandler デフォルト例外ハンドラ
     */
    public void setDefaultJobExceptionHandler(
            JobExceptionHandler defaultJobExceptionHandler) {
        this.defaultJobExceptionHandler = defaultJobExceptionHandler;
    }
    
    /**
     * JobWorkerの開始ログを出力する。
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
            logStr.append(" [BLogicExecutorName=");
            logStr.append(blogicExecutor.getClass().getSimpleName());
            logStr.append("]");
            logStr.append(" [BatchUpdateProcessorName=");
            logStr.append(batchUpdateProcessor.getClass().getSimpleName());
            logStr.append("]");
            logStr.append(" [BatchUpdateResultHandlerName=");
            logStr.append(batchUpdateResultHandler.getClass().getSimpleName());
            logStr.append("]");
            log.debug(logStr.toString());
        }
    }

    /**
     * JobWorkerの終了ログを出力する。
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
            logStr.append(" [BLogicExecutorName=");
            logStr.append(blogicExecutor.getClass().getSimpleName());
            logStr.append("]");
            logStr.append(" [BatchUpdateProcessorName=");
            logStr.append(batchUpdateProcessor.getClass().getSimpleName());
            logStr.append("]");
            logStr.append(" [BatchUpdateResultHandlerName=");
            logStr.append(batchUpdateResultHandler.getClass().getSimpleName());
            logStr.append("]");
            logStr.append(" [BatchUpdateCount=");
            logStr.append(jobStatus.getBatchUpdateCount());
            logStr.append("]");
            log.debug(logStr.toString());
        }
    }
}
