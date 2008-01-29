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

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

import jp.terasoluna.fw.batch.core.JobException;
import jp.terasoluna.fw.batch.core.JobStatus;
import jp.terasoluna.fw.batch.standard.StandardWorkQueue;

import commonj.work.WorkItem;
import commonj.work.WorkManager;

/**
 * <code>WorkQueue</code> インタフェースのCommonj用実装クラス。
 * 
 * <p>
 * この実装では、JDKの <code>BLockingQueue</code> によってキューイングを実現する。
 * </p>
 * 
 * <p>
 * キューへの要素追加、キューからの要素取得の際には、ブロックし続けるのではなく、
 * タイムアウト時間だけ試行したあとで<code>JobStatus</code> をチェックし 
 * <code>QueueingException</code> 例外をスローする。 
 * キューへの要素追加、キューからの要素取得の際に、スレッドがインタラプトされた
 * 際にも<code>QueueingException</code> 例外をスローする。
 * </p>
 * 
 */
public class WorkManagerTaskWorkQueue extends StandardWorkQueue {

    /**
     * キューの要素の処理結果のリスト。
     */
    private List<WorkItem> workItemList = new ArrayList<WorkItem>();

    /**
     * ワークマネージャ。
     */
    private WorkManager workManager = null;

    /**
     * ジョブを開始するまでのタイムアウト（ミリ秒）。
     */
    private long workTimeout = WorkManager.INDEFINITE;

    /**
     * コンストラクタ。
     * 
     * @param queueLength キューの長さ
     * @param jobStatus ジョブステータス
     */
    public WorkManagerTaskWorkQueue(int queueLength, JobStatus jobStatus) {
        super(queueLength, jobStatus);
    }

    /**
     * キューの要素を処理するすべてのワーカの終了を待ち合わせる。
     */
    @Override
    public void waitForAllWorkers() {
        try {
            workManager.waitForAll(workItemList, workTimeout);
        } catch (IllegalArgumentException e) {
            throw new JobException(e);
        } catch (InterruptedException e) {
            throw new JobException(e);
        }
    }

    /**
     * ワーカの処理結果に非同期でアクセスすためのWorkItemを追加する。
     * 
     * @param workItem ワーカの処理結果に非同期でアクセスすためのWorkItem
     */
    public void addWorkItem(WorkItem workItem) {
        workItemList.add(workItem);
    }

    /**
     * ワークマネージャを設定する。
     * 
     * @param workManager workManager ワークマネージャ
     */
    public void setWorkManager(WorkManager workManager) {
        this.workManager = workManager;
    }

    /**
     * ジョブを開始するまでのタイムアウト（ミリ秒）の設定。
     * 
     * @param workTimeout タイムアウト（ミリ秒）
     */
    public void setWorkTimeout(long workTimeout) {
        this.workTimeout = workTimeout;
    }

    /**
     * 処理結果に非同期でアクセスすためのフューチャーを追加する。
     * このクラスでは使用しないため、UnsupportedOperationExceptionをスローする。
     *  
     * @param futureResult 処理結果に非同期でアクセスするフューチャー
     */
    @Override
    public void addFutureJobResult(Future< ? > futureResult) {
        throw new UnsupportedOperationException();
    }

}
