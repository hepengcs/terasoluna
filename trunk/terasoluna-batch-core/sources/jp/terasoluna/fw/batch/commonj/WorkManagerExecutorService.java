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

package jp.terasoluna.fw.batch.commonj;

import java.util.LinkedHashMap;

import org.springframework.scheduling.commonj.DelegatingWork;

import jp.terasoluna.fw.batch.commonj.listener.WorkMapListener;
import jp.terasoluna.fw.batch.core.JobException;
import jp.terasoluna.fw.batch.core.JobExceptionHandler;
import jp.terasoluna.fw.batch.core.JobStatus;
import jp.terasoluna.fw.batch.core.WorkQueue;
import jp.terasoluna.fw.batch.standard.QueueProcessor;
import jp.terasoluna.fw.batch.standard.RunnableQueueProcessor;

import commonj.work.WorkException;
import commonj.work.WorkItem;
import commonj.work.WorkManager;
import commonj.work.WorkRejectedException;

/**
 * <code>WorkerExecutorService</code> インタフェースのCommonj用実装クラス。
 * 
 * <p>
 * <code>WorkManager</code> でワーカを実行する。
 * </p>
 * 
 *
 */
public class WorkManagerExecutorService implements WorkerExecutorService {

    /**
     * ワークマネージャ。
     */
    private WorkManager workManager;
    
    /**
     * ワークリスナー。
     */
    private WorkMapListener workMapListener;
    
    /**
     * 例外ハンドラを格納したマップ。
     */
    private LinkedHashMap<JobException, JobExceptionHandler> 
        exceptionHandlerMap = null;

    /**
     * デフォルト例外ハンドラ。
     */
    private JobExceptionHandler defaultJobExceptionHandler = null;

    /**
     * デフォルト例外ハンドラの取得。
     * 
     * @return デフォルト例外ハンドラ
     */
    public JobExceptionHandler getDefaultJobExceptionHandler() {
        return defaultJobExceptionHandler;
    }
    
    /**
     * デフォルト例外ハンドラの設定。
     * 
     * @param defaultJobExceptionHandler デフォルト例外ハンドラ
     */
    public void setDefaultJobExceptionHandler(
            JobExceptionHandler defaultJobExceptionHandler) {
        this.defaultJobExceptionHandler = defaultJobExceptionHandler;
    }

    /**
     * 例外ハンドラを格納したマップの取得。
     * 
     * @return 例外ハンドラを格納したマップ
     */
    public LinkedHashMap<JobException, JobExceptionHandler> 
        getExceptionHandlerMap() {
        return exceptionHandlerMap;
    }

    /**
     * 例外ハンドラを格納したマップの設定。
     * 
     * @param exceptionHandlerMap 例外ハンドラを格納したマップ
     */
    public void setExceptionHandlerMap(
            LinkedHashMap<JobException, JobExceptionHandler> 
                exceptionHandlerMap) {
        this.exceptionHandlerMap = exceptionHandlerMap;
    }

    /**
     * ワークマネージャの取得。
     * 
     * @return ワークマネージャ
     */
    public WorkManager getWorkManager() {
        return workManager;
    }

    /**
     * ワークマネージャの設定。
     * 
     * @param workManager ワークマネージャ
     */
    public void setWorkManager(WorkManager workManager) {
        this.workManager = workManager;
    }

    /**
     * ワークリスナーの取得。
     * 
     * @return ワークリスナー
     */
    public WorkMapListener getWorkMapListener() {
        return workMapListener;
    }

    /**
     * ワークリスナーの設定。
     * 
     * @param workMapListener ワークリスナー
     */
    public void setWorkMapListener(WorkMapListener workMapListener) {
        this.workMapListener = workMapListener;
    }

    /**
     * Workerの実行を登録する。
     * 
     * @param queueProcessor キュー処理プロセッサ
     * @param workQueue キュー
     * @param jobStatus ジョブステータス
     * @return ワーカの処理結果に非同期でアクセスすためのワークアイテム
     */
   public WorkItem submit(QueueProcessor queueProcessor, 
           WorkQueue workQueue, JobStatus jobStatus) {
       try {
           DelegatingWork work = new DelegatingWork(
                   new RunnableQueueProcessor(queueProcessor, workQueue,
                           jobStatus, exceptionHandlerMap,
                           defaultJobExceptionHandler));
           if (workMapListener == null) {
               return workManager.schedule(work);
           } else {
               WorkItem workItem = workManager.schedule(work, workMapListener);
               workMapListener.addWork(workItem, work);
               return workItem;
           }
       } catch (IllegalArgumentException e) {
           throw new JobException(e);
       } catch (WorkRejectedException e) {
           throw new JobException(e);
       } catch (WorkException e) {
           throw new JobException(e);
       }
    }
    
}
