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

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import jp.terasoluna.fw.batch.core.JobException;
import jp.terasoluna.fw.batch.core.JobStatus;
import jp.terasoluna.fw.batch.core.QueueingException;
import jp.terasoluna.fw.batch.core.WorkQueue;
import jp.terasoluna.fw.batch.core.WorkUnit;
import jp.terasoluna.fw.batch.openapi.JobContext;

/**
 * <code>WorkQueue</code> インタフェースの標準実装クラス。
 * 
 * <p>この実装では、JDKの <code>BLockingQueue</code> によってキューイングを実現
 * する。</p>
 * 
 * <p>キューへの要素追加、キューからの要素取得の際には、ブロックし続けるのでは
 * なく、タイムアウト時間だけ試行したあとで <code>JobStatus</code> をチェックし
 * <code>QueueStoppedException</code> 例外をスローする。
 * キューへの要素追加、キューからの要素取得の際に、スレッドがインタラプトされた
 * 際にも<code>QueueStoppedException</code> 例外をスローする。
 * </p>
 *
 */
public class StandardWorkQueue implements WorkQueue {

    /**
     * キューをチェックする際のタイムアウト。（ミリ秒）
     */
    private int queueCheckTimeout = 10000;
    
    /**
     * キュー。
     */
    private BlockingQueue<WorkUnit> queue;

    /**
     * ジョブステータス。
     */
    private JobStatus jobStatus;

    /**
     * キューの要素の処理結果のリスト。
     */
    private List<Future< ? >> futureResultList = new ArrayList<Future< ? >>();

    /**
     * キューの終端を示すマーク。
     */
    public static final WorkUnit END_MARK = new WorkUnit() {
        public boolean isEndMark() {
            return true;
        }

        public JobContext getJobContext() {
            throw new UnsupportedOperationException();
        }

        public void setJobContext(JobContext jobContext) {
            throw new UnsupportedOperationException();
        }
    };

    /**
     * コンストラクタ。
     *
     * @param queueLength キューの長さ
     * @param jobStatus ジョブステータス
     */
    public StandardWorkQueue(int queueLength, JobStatus jobStatus) {
        queue = new ArrayBlockingQueue<WorkUnit>(queueLength);
        this.jobStatus = jobStatus;
    }

    /**
     * 処理対象データをキューに追加する。
     *
     * @param element キューに追加する要素
     */
    public void put(WorkUnit element) {
        try {
            while (!queue.offer(element, queueCheckTimeout, 
                    TimeUnit.MILLISECONDS)) {
                checkJobStatus();
            }
        } catch (InterruptedException e) {
            throw new QueueingException(e);
        }
    }

    /**
     * ジョブステータスをチェックする。
     *
     * <p>シャットダウンが要求されているときには、<code>JobStatus<code>
     * をシャットダウンに更新した後で、<code>QueueingStoppedException</code>
     * 例外をスローする。</p>
     * 
     * <p>さらに、ジョブステータスが <code>JobStatus.STATE.STARTED</code>、
     * あるいは <code>JobStatus.STATE.RESTARTED</code>
     * のどちらでもないときには、<code>QueueingStoppedException</code>
     * 例外をスローする。</p>
     */
    private void checkJobStatus() {
        if (jobStatus.isShutdownGraceful() || jobStatus.isShutdownImmediate()) {
            jobStatus.suspend();
            throw new QueueingException();
        }
        if (!jobStatus.isExecuting()) {
            throw new QueueingException();
        }
    }

    /**
     * キューを終了する。
     * 
     * <p>キューの終端であるインスタンスをキューに追加する。</p>
     */
    public void close() {
        put(END_MARK);
    }

    /**
     * キューの要素を取得する。
     *
     * @return キューの要素
     */
    public WorkUnit take() {

        //全スレッドが待機状態時、END_MARKがtakeされた場合は
        //END_MARKがqueueから削除されるため再投入が必要
        try {
            while (true) {
                WorkUnit workUnit = 
                    queue.poll(queueCheckTimeout, TimeUnit.MILLISECONDS);
                if (workUnit == END_MARK) {
                    put(END_MARK);
                    return END_MARK;
                }
                if (workUnit != null) {
                    return workUnit;
                }
                checkJobStatus();
            }
        } catch (InterruptedException e) {
            throw new QueueingException(e);
        }
    }

    /**
     * キューの要素を処理するすべてのワーカーの終了を待ち合わせる。
     */
    public void waitForAllWorkers() {
        for (Future< ? > futureResult : futureResultList) {
            try {
                futureResult.get();
            } catch (ExecutionException e) {
                throw new JobException(e);
            } catch (InterruptedException e) {
                throw new JobException(e);
            }
        }
    }
    
    /**
     * ワーカの処理結果に非同期でアクセスすためのフューチャーを追加する。
     * 
     * @param futureResult ワーカの処理結果に非同期でアクセスすためのフューチ
     * ャー
     */
    public void addFutureJobResult(Future< ? > futureResult) {
        futureResultList.add(futureResult);
    }

    /**
     * タイムアウト設定する。
     * 
     * @param timeout タイムアウト(ミリ秒)
     */
    public void setQueueCheckTimeout(int timeout) {
        queueCheckTimeout = timeout;
    }
}
