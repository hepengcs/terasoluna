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

import java.util.LinkedHashMap;
import java.util.List;

import jp.terasoluna.fw.batch.openapi.BLogic;
import jp.terasoluna.fw.batch.openapi.BLogicResult;
import jp.terasoluna.fw.batch.openapi.JobContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * ビジネスロジック実行クラスの標準実装。
 * 
 * <p>このクラスでは、アプリケーションのビジネスロジック（<code>BLogic<code> 
 * インタフェースを実装したクラス）を 起動し、起動結果をジョブステータスへ反映す
 * る。</p>
 * 
 * <p>ビジネスロジックの処理結果は、このクラスの属性に設定されているビジネスロジ
 * ック処理結果ハンドラによってジョブステータスに反映される。ビジネスロジック処
 * 理結果ハンドラは、ビジネスロジックが返却したリターンコードに関わらず起動され
 * る。つまり、ビジネスロジックが正常続行(<code>NORMAL_CONTINUE</code>)を返却し
 * た場合でも、異常終了(<code>ERROR_END</code>)を返却した場合であっても処理結果
 * ハンドラが呼ばれる。</p>
 * 
 * <p>ビジネスロジックで例外が発生した場合には、このクラスの属性に設定されている
 * 例外ハンドラの設定にしたがって例外ハンドラが起動される。
 * また、ビジネスロジックからスローされた例外は、例外ハンドラに渡される前にフレ
 * ームワークのビジネスロジック例外クラス(<code>BLogicException<code>)によって、
 * ラップされる。</p>
 * 
 * <p>例外が発生した場合には、ビジネスロジック処理結果ハンドラは呼ばれないことに
 * 留意すること。
 * </p>
 * 
 * <p>このクラスではトランザクションに関わる処理は行われない。</p>
 * 
 * @see jp.terasoluna.fw.batch.openapi.BLogic
 * @see jp.terasoluna.fw.batch.core.BLogicResultHandler
 * @see jp.terasoluna.fw.batch.core.JobExceptionHandler
 * 
 */
public class StandardBLogicExecutor implements BLogicExecutor {

    /**
     * ログインスタンス。
     */
    private static Log log = LogFactory.getLog(StandardBLogicExecutor.class);

    /**
     * ビジネスロジック結果処理ハンドラ。
     */
    private BLogicResultHandler bLogicResultHandler = null;

    /**
     * ビジネスロジック。
     */
    private BLogic<Object, JobContext> blogic = null;

    /**
     * 例外ハンドラを格納したMap。
     */
    private LinkedHashMap<JobException, JobExceptionHandler>
            exceptionHandlerMap = null;

    /**
     * デフォルト例外ハンドラ。
     */
    private JobExceptionHandler defaultJobExceptionHandler = null;

    /**
     * ビジネスロジックを実行し、ビジネスロジック実行結果の処理を行う。
     *
     * @param blogicInputData 処理対象データ
     * @param jobContext ジョブコンテキスト
     * @param jobStatus ジョブ処理状況
     * @param batchUpdateMapList バッチ更新リスト
     */
    public void executeBLogic(Object blogicInputData, JobContext jobContext,
            JobStatus jobStatus,
            List<LinkedHashMap<String, Object>> batchUpdateMapList) {
        BLogicResult blogicResult = null;
        try {

            blogicResult = blogic.execute(blogicInputData, jobContext);

            bLogicResultHandler.handle(blogicResult, blogicInputData, jobStatus,
                    batchUpdateMapList);

            writeLog(jobStatus, blogicResult);
        } catch (RuntimeException e) {
            // BLogic、BLogic処理結果ハンドラで発生した例外の処理
            BLogicException wrappingException
                = new BLogicException(e, blogicInputData, blogicResult);

            JobExceptionHandler handler
                = ExceptionHandlerUtil.getJobExceptionHandler(wrappingException,
                                                            exceptionHandlerMap,
                                                    defaultJobExceptionHandler);

            handler.handlException(jobContext, wrappingException, jobStatus);
        }
    }

    /**
     * ジョブ結果ハンドラを設定する。
     *
     * @param bLogicResultHandler ジョブ結果ハンドラ
     */
    public void setBlogicResultHandler(
            BLogicResultHandler bLogicResultHandler) {
        this.bLogicResultHandler = bLogicResultHandler;
    }

    /**
     * ビジネスロジックを設定する。
     *
     * @param blogic ビジネスロジック
     */
    public void setBlogic(BLogic<Object, JobContext> blogic) {
        this.blogic = blogic;
    }

    /**
     * 例外ハンドラを格納したMapを設定する。
     *
     * @param exceptionHandlerMap 例外ハンドラを格納したMap
     */
    public void setExceptionHandlerMap(
         LinkedHashMap<JobException, JobExceptionHandler> exceptionHandlerMap) {
        this.exceptionHandlerMap = exceptionHandlerMap;
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
     * BLogicExecutorの結果ログを出力する。
     * 
     * @param jobStatus ジョブステータス
     * @param blogicResult ビジネスロジック実行結果
     */
    private void writeLog(JobStatus jobStatus, BLogicResult blogicResult) {
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
            logStr.append(" [BLogicName=");
            logStr.append(blogic.getClass().getSimpleName());
            logStr.append("]");
            logStr.append(" [ResultHandler=");
            logStr.append(bLogicResultHandler.getClass().getSimpleName());
            logStr.append("]");
            logStr.append(" [ReturnCode：");
            logStr.append(blogicResult.getReturnCode());
            logStr.append(" JobExitCode=");
            logStr.append(blogicResult.getClass().getSimpleName());
            logStr.append("]");
            logStr.append(" [JobState=");
            logStr.append(jobStatus.getJobState());
            logStr.append("]");
            log.debug(logStr.toString());
        }
    }
}
