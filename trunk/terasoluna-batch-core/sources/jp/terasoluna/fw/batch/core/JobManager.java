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

import jp.terasoluna.fw.batch.openapi.JobContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * ジョブ等のフレームワークで規定する作業単位を管理、実行するクラス。
 * 
 * <p><code>{@link JobWorker}</code> などの他の <code>{@link Workable}</code>
 * インタフェースの実装クラスと共に、作業の階層構造を構成する。<code>JobManager
 * </code>は、作業の階層構造の中で入力処理とその入力に対応する処理を管理する。
 * <code>JobManager</code> は、作業階層の中でジョブに対応する作業を実行するだけ
 * でなく、これらの"入力処理とその入力に対応する処理"で構成されるすべての作業を
 * 実行する。</p>
 * 
 * <p>作業の階層の中で、もっとも最下層（もっとも小さな作業単位）の作業は 
 * <code>JobWorker</code>によって行われるが、<code>JobWorker</code> を入力処理と
 * 結びつけて管理する作業は<code>JobManager</code> が行う。さらには、分割ジョブ
 * など、<code>JobManager</code>をその入力を結びつける必要がある場合には、
 * 上位階層の <code>JobManager</code> によって、入力処理とその入力を処理する
 *  <code>JobManager</code> が結び付けられる。</p>
 * 
 * <p>作業階層は、<code>Workable</code> インタフェースの実装クラスが階層的に
 * 積み上げられたコンポジットな構造になっている。<code>JobManager</code> は、
 * 最下層以外の部分を構成する。</p>
 * 
 * <p><strong>キューの作成、コレクタ呼び出し</strong></p>
 *
 * <p><code>JobManager</code> では、<code>workQueueFactory</code> 属性に
 * 設定されている作業キューファクトリから、入力処理と、その入力を処理する作業の
 * 間のキューを取得する。</p>
 * 
 * <p>取得したキューは、入力処理を行う <code>collector</code> 属性のコレクタに
 * 渡される。また、このクラスでは、コレクタの処理結果ハンドラの呼び出しと、
 * これらの入力処理で例外が発生した場合の例外ハンドラ呼び出しを行う。</p>
 * 
 * <p><strong>終了時の処理</strong></p>
 *
 * <p>コレクタによる入力処理、および作業キューにキューイングされた作業のすべての
 * 終了を待ったあとで、このクラスの処理はリターンする。このクラスの処理から
 * リターンする際には、ジョブステータスが実行中（<code>JobStatus.STATE.STARTED
 * </code>、あるいは<code>JobStatus.STATE.RESTARTED</code>)である場合には正常終
 * 了（<code>JobStatus.STATE.ENDING_NORMALLY</code>）に更新する。</p>
 * 
 * <p><strong>前処理</strong></p>
 * 
 * <p>コレクタによる入力処理の起動が行われる前に、<code>preProcessor</code> 属性
 * に設定されたサポートプロセッサによって前処理が行われる。前処理が行われた後で
 * 、ジョブステータスのチェックが行われる。ジョブステータスが継続状態でない場合
 * には、コレクタによる入力処理などは起動されず、直ちにこのクラスからリターンす
 * る。</p>
 * 
 * <p><strong>後処理</strong></p>
 * 
 * <p>コレクタによる入力処理、および作業キューにキューイングされた作業が終わった
 * 後では、<code>postProcessor</code> 属性に設定されたサポートプロセッサによって
 * 後処理が行われる。コレクタによる入力処理、および作業キューにキューイングされ
 * た作業によってジョブステータスが正常終了、異常終了などの終了状態に更新されて
 * いた場合には、ジョブ後処理は起動されない。</p>
 *
 */
public class JobManager implements Workable<WorkUnit> {

    /**
     * ログインスタンス。
     */
    private static Log log = LogFactory.getLog(JobManager.class);

    /**
     * コレクタ。
     */
    private Collector<JobContext> collector = null;

    /**
     * 対象データ取得結果のハンドラ。
     */
    private CollectorResultHandler collectorResultHandler = null;

    /**
     * 作業キューのファクトリ。
     */
    private WorkQueueFactory workQueueFactory = null;

    /**
     * ジョブマネジャー名。
     */
    private String name = null;

    /**
     * 例外ハンドラ定義。例外をキーとして、キーの例外に対応する例外ハンドラを
     * 設定する。
     */
    private LinkedHashMap<JobException, JobExceptionHandler> 
        exceptionHandlerMap = null;

    /**
     * デフォルト例外ハンドラ。
     */
    private JobExceptionHandler defaultJobExceptionHandler = null;

    /**
     * ジョブ前処理を行うサポートプロセッサ。
     */
    private SupportProcessor preProcessor = null;

    /**
     * ジョブ後処理を行うサポートプロセッサ。
     */
    private SupportProcessor postProcessor = null;

    /**
     * 作業を行う。
     *
     * @param workUnit 作業単位
     * @param jobStatus ジョブステータス
     */
    public void work(WorkUnit workUnit, JobStatus jobStatus) {
        JobStatus childJobStatus = null;
        childJobStatus = jobStatus.getChild(workUnit.getJobContext());
        
        //開始ログ
        writeStartLog(childJobStatus);

        preProcessor.process(workUnit.getJobContext(), childJobStatus);
        if (!childJobStatus.isContinue()) {
            return;
        }

        // 本処理
        WorkQueue managerQueue = workQueueFactory.getWorkQueue(childJobStatus);

        processCollect(workUnit.getJobContext(), managerQueue, childJobStatus);

        managerQueue.waitForAllWorkers();

        postProcessor.process(workUnit.getJobContext(), childJobStatus);

        finishWork(jobStatus, childJobStatus);
        //終了ログ
        writeEndLog(childJobStatus);
    }

    /**
     * 作業終了処理を行う。
     * 
     * @param parentJobStatus 起動元のジョブステータス
     * @param jobStatus このジョブマネージャのジョブステータス
     */
    protected void finishWork(JobStatus parentJobStatus, JobStatus jobStatus) {
        if (jobStatus.isExecuting() || jobStatus.isShutdownGraceful()
                || jobStatus.isShutdownImmediate()) {
            jobStatus.setJobState(JobStatus.STATE.ENDING_NORMALLY);
        }
    }

    /**
     * 対象データ取得を行う。
     *
     * @param jobContext ジョブコンテキスト
     * @param workQueue 対象データ格納用キュー
     * @param jobStatus ジョブステータス
     */
    private void processCollect(JobContext jobContext, WorkQueue workQueue,
             JobStatus jobStatus) {
        CollectorResult collectorResult = null;
        try {
            collectorResult    = collector.collect(jobContext, workQueue,
                    jobStatus);

            collectorResultHandler.handle(collectorResult, jobStatus);
        } catch (RuntimeException e) {
            // Collector、Collector処理結果ハンドラで発生した例外の処理

            CollectorException wrappingException
                = new CollectorException(e, collectorResult);

            JobExceptionHandler handler
                = ExceptionHandlerUtil.getJobExceptionHandler(wrappingException,
                            exceptionHandlerMap, defaultJobExceptionHandler);

            handler.handlException(jobContext, wrappingException, jobStatus);
        }
    }

    /**
     * 対象データ取得結果のハンドラを設定する。
     *
     * @param collectorResultHandler 対象データ取得結果のハンドラ
     */
    public void setCollectorResultHandler(
            CollectorResultHandler collectorResultHandler) {
        this.collectorResultHandler = collectorResultHandler;
    }

    /**
     * 作業キューのファクトリを設定する。
     *
     * @param workQueueFactory 作業キューのファクトリ
     */
    public void setWorkQueueFactory(WorkQueueFactory workQueueFactory) {
        this.workQueueFactory = workQueueFactory;
    }

    /**
     * コレクタを設定する。
     *
     * @param collector コレクタ
     */
    public void setCollector(Collector<JobContext> collector) {
        this.collector = collector;
    }

    /**
     * ジョブマネジャー名を設定する。
     *
     * @param name ジョブマネジャー名
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * ジョブ後処理を行うサポートプロセッサを設定する。
     *
     * @param postProcessor ジョブ後処理を行うサポートプロセッサ
     */
    public void setPostProcessor(SupportProcessor postProcessor) {
        this.postProcessor = postProcessor;
    }

    /**
     * ジョブ前処理を行うサポートプロセッサを設定する。
     *
     * @param preProcessor ジョブ前処理を行うサポートプロセッサ
     */
    public void setPreProcessor(SupportProcessor preProcessor) {
        this.preProcessor = preProcessor;
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
     * デフォルト例外ハンドラを設定する。
     * 
     * @param defaultJobExceptionHandler デフォルト例外ハンドラ
     */
    public void setDefaultJobExceptionHandler(
        JobExceptionHandler defaultJobExceptionHandler) {
        this.defaultJobExceptionHandler = defaultJobExceptionHandler;
    }
    
    /**
     * JobManagerの開始ログを出力する。
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
            logStr.append(" [JobManagerName=");
            logStr.append(name);
            logStr.append("]");
            logStr.append(" [JobState=");
            logStr.append(jobStatus.getJobState());
            logStr.append("]");
            log.debug(logStr.toString());
        }
    }

    /**
     * JobManagerの終了ログを出力する。
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
            logStr.append(" [JobManagerName=");
            logStr.append(name);
            logStr.append("]");
            logStr.append(" [JobState=");
            logStr.append(jobStatus.getJobState());
            logStr.append("]");
            log.debug(logStr.toString());
        }
    }
}
