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

package jp.terasoluna.fw.batch.commonj.usequeue;

import jp.terasoluna.fw.batch.commonj.WorkerExecutorService;
import jp.terasoluna.fw.batch.core.InitializeException;
import jp.terasoluna.fw.batch.core.JobStatus;
import jp.terasoluna.fw.batch.core.WorkQueue;
import jp.terasoluna.fw.batch.core.WorkQueueFactory;
import jp.terasoluna.fw.batch.standard.QueueProcessor;

import commonj.work.WorkItem;
import commonj.work.WorkManager;

/**
 * <code>WorkQueueFactory</code> インタフェースのCommonj用実装クラス。
 * 
 * <p>
 * このクラスでは、<coed>WorkManagerTaskWorkQueue</code> のインスタンスを作成し、 
 * 作成した<coed>WorkManagerTaskWorkQueue</code> のインスタンスをパラメータとして
 * キュー処理プロセッサを起動したあとで、呼び出しもとに 
 * <coed>WorkManagerTaskWorkQueue</code> のインスタンスを返す。
 * </p>
 * 
 */
public class WorkManagerTaskWorkQueueFactory implements WorkQueueFactory {

    /**
     * キューの長さ。
     */
    private int queueLength = 0;

    /**
     * キュー処理プロセッサ。
     */
    private QueueProcessor queueProcessor = null;

    /**
     * ワーカー実行サービス。
     */
    private WorkerExecutorService workerExecutorService = null;

    /**
     * 多重実行数。
     */
    private int multiplicity = 1;

    /**
     * ジョブを開始するまでのタイムアウト（ミリ秒）。
     */
    private long workTimeout = WorkManager.INDEFINITE;

    /**
     * ワークキューを取得する。
     * 
     * @param jobStatus ジョブステータス
     * @return ワークキュー
     */
    public WorkQueue getWorkQueue(JobStatus jobStatus) {
        WorkManagerTaskWorkQueue workQueue = new WorkManagerTaskWorkQueue(
                queueLength, jobStatus);

        workQueue.setWorkManager(workerExecutorService.getWorkManager());
        workQueue.setWorkTimeout(workTimeout);

        for (int i = 0; i < multiplicity; i++) {
            WorkItem workItem = workerExecutorService.submit(queueProcessor,
                    workQueue, jobStatus);
            workQueue.addWorkItem(workItem);
        }

        return workQueue;
    }

    /**
     * キューの長さを設定する。
     * 
     * @param queueLength キューの長さ
     */
    public void setQueueLength(int queueLength) {
        if (queueLength <= 0) {
            StringBuffer buffer = new StringBuffer("QueueLength is illegal. ");
            buffer.append(queueLength);
            throw new InitializeException(buffer.toString());
        }
        this.queueLength = queueLength;
    }

    /**
     * キュー処理プロセッサを設定する。
     * 
     * @param queueProcessor キュー処理プロセッサ
     */
    public void setQueueProcessor(QueueProcessor queueProcessor) {
        this.queueProcessor = queueProcessor;
    }

    /**
     * ワーカー実行サービスを設定する。
     * 
     * @param workerExecutorService ワーカー実行サービス
     */
    public void setWorkerExecutorService(
            WorkerExecutorService workerExecutorService) {
        this.workerExecutorService = workerExecutorService;
    }

    /**
     * 多重実行数を設定する。
     * 
     * @param multiplicity 多重実行数
     */
    public void setMultiplicity(int multiplicity) {
        if (multiplicity <= 0) {
            StringBuffer buffer = new StringBuffer("Multiplicity is illegal. ");
            buffer.append(multiplicity);
            throw new InitializeException(buffer.toString());
        }
        this.multiplicity = multiplicity;
    }

    /**
     * ジョブを開始するまでのタイムアウト（ミリ秒）の設定。
     * 
     * @param workTimeout タイムアウト（ミリ秒）
     */
    public void setWorkTimeout(long workTimeout) {
        this.workTimeout = workTimeout;
    }
    
}
