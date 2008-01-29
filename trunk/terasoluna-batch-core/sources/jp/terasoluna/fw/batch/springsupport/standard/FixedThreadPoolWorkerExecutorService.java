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

package jp.terasoluna.fw.batch.springsupport.standard;

import java.util.LinkedHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import jp.terasoluna.fw.batch.core.InitializeException;
import jp.terasoluna.fw.batch.core.JobException;
import jp.terasoluna.fw.batch.core.JobExceptionHandler;
import jp.terasoluna.fw.batch.core.JobStatus;
import jp.terasoluna.fw.batch.core.WorkQueue;
import jp.terasoluna.fw.batch.standard.QueueProcessor;
import jp.terasoluna.fw.batch.standard.RunnableQueueProcessor;
import jp.terasoluna.fw.batch.standard.WorkerExecutorService;

import org.springframework.beans.factory.DisposableBean;

/**
 * <code>WorkerExecutorService</code> インタフェースの標準実装クラス。
 * 
 * <p><code>Executors.newFixedThreadPool()</code> で作成したスレッドプールで、
 * ワーカを実行する。</p>
 * 
 */
public class FixedThreadPoolWorkerExecutorService
        implements WorkerExecutorService, DisposableBean {

    /**
     * 実行サービス。
     */
    private ExecutorService executorService = null;

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
     * コンストラクタ。
     * 
     * @param threads スレッド数
     */
    public FixedThreadPoolWorkerExecutorService(int threads) {
        if (threads <= 0) {
                    StringBuilder builder = 
                        new StringBuilder("Threads is illegal. ");
                    builder.append(threads);
                throw new InitializeException(builder.toString());
           }
        executorService = Executors.newFixedThreadPool(threads);
    }

    /**
     * ワーカー実行を登録する。
     *
     * @param queueProcessor キュー処理プロセッサ
     * @param workQueue キュー
     * @param jobStatus ジョブステータス
     * @return ワーカの処理結果に非同期でアクセスすためのフューチャー
     */
    public Future< ? > submit(QueueProcessor queueProcessor,
            WorkQueue workQueue, JobStatus jobStatus) {
        return executorService.submit(new RunnableQueueProcessor(
                queueProcessor, workQueue, jobStatus, exceptionHandlerMap,
                defaultJobExceptionHandler));
    }

    /**
     * インスタンスを破棄する際に、Spring fremeworkから呼ばれるコール
     * バックメソッド。
     * 
     * <p>実行サービスをシャットダウンする。</p>
     */
    public void destroy() {
        executorService.shutdown();
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
}
