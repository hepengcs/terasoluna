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

import java.util.LinkedHashMap;

import jp.terasoluna.fw.batch.core.ExceptionHandlerUtil;
import jp.terasoluna.fw.batch.core.JobException;
import jp.terasoluna.fw.batch.core.JobExceptionHandler;
import jp.terasoluna.fw.batch.core.JobStatus;
import jp.terasoluna.fw.batch.core.WorkQueue;

/**
 * キュープロセッサをマルチスレッドで実行するためのラッパークラス。
 *
 */
public class RunnableQueueProcessor implements Runnable {

    /**
     * キュープロセッサ。
     */
    private QueueProcessor queueProcessor;

    /**
     * 処理対処を格納するキュー。
     */
    private WorkQueue workQueue;

    /**
     * ジョブステータス。
     */
    private JobStatus jobStatus;

    /**
     * 例外ハンドラを格納したMap。
     */
    private LinkedHashMap<JobException, JobExceptionHandler> 
        exceptionHandlerMap;

    /**
     * デフォルト例外ハンドラ。
     */
    private JobExceptionHandler defaultJobExceptionHandler;

    /**
     * コンストラクタ。
     * 
     * <p>引数で渡されたインスタンスを設定する。</p>
     *
     * @param queueProcessor キュープロセッサ
     * @param workQueue ワークキュー
     * @param jobStatus ジョブステータス
     * @param exceptionHandlerMap 例外ハンドラを格納したMap
     * @param defaultJobExceptionHandler デフォルト例外ハンドラ
     */
    public RunnableQueueProcessor(
            QueueProcessor queueProcessor,
            WorkQueue workQueue,
            JobStatus jobStatus,
            LinkedHashMap<JobException, JobExceptionHandler> 
                exceptionHandlerMap,
            JobExceptionHandler defaultJobExceptionHandler) {
        this.queueProcessor = queueProcessor;
        this.workQueue = workQueue;
        this.jobStatus = jobStatus;
        this.exceptionHandlerMap = exceptionHandlerMap;
        this.defaultJobExceptionHandler = defaultJobExceptionHandler;
    }

    /**
     * キュープロセッサを起動する。
     *
     */
    public void run() {
        try {
            queueProcessor.process(workQueue, jobStatus);
        } catch (Exception e) {
            // BLogic、BLogic処理結果ハンドラで発生した例外の処理
            JobException jobException = new JobException(e);

            JobExceptionHandler handler
                = ExceptionHandlerUtil.getJobExceptionHandler(jobException,
                                                            exceptionHandlerMap,
                                                    defaultJobExceptionHandler);
            handler.handlException(null, jobException, jobStatus);
        }
    }
}
