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

package jp.terasoluna.fw.batch.restart;

import jp.terasoluna.fw.batch.core.JobStatus;
import jp.terasoluna.fw.batch.core.WorkQueue;
import jp.terasoluna.fw.batch.core.WorkUnit;
import jp.terasoluna.fw.batch.core.Workable;
import jp.terasoluna.fw.batch.standard.QueueProcessor;

/**
 * リスタート時のキュー処理用クラス。
 *
 * <p>キューから要素を取り出し、取り出したそれぞれの要素をパラメータとしてワーカ
 * ーを起動する。</p>
 * 
 * <p>キューの最後の要素であればリスタート用ワーカを起動する。
 * キューの最後の要素ではない場合は通常のワーカを起動する。</p>
 *
 */
public class RestartQueueProcessor extends QueueProcessor {

    /**
     * ワーカー。
     */
    private Workable<WorkUnit> normalWorker = null;

    /**
     * リスタート用ワーカー。
     */
    private Workable<WorkUnit> restartWorker = null;

    /**
     * キューの処理を行う。
     *
     * @param workQueue キュー
     * @param jobStatus ジョブステータス
     */
    public void process(WorkQueue workQueue, JobStatus jobStatus) {
        printStartLog(jobStatus);

        WorkUnit prev = null;
        while (true) {
            WorkUnit element = workQueue.take();
            
            if (element.isEndMark()) {
                // 本処理
                if (prev != null) {
                    restartWorker.work(prev, jobStatus);
                }
                break;
            } else if (prev != null) {
                // 本処理
                normalWorker.work(prev, jobStatus);
            }

            if (!jobStatus.isContinue()) {
                break;
            }
            
            prev = element;
        }

        printEndLog(jobStatus);
    }

    /**
     * ワーカーを設定する。
     *
     * @param worker ワーカー
     */
    public void setWorker(Workable<WorkUnit> worker) {
        this.normalWorker = worker;
    }

    /**
     * リスタート用ワーカーを設定する。
     *
     * @param restartWorker リスタート用ワーカー
     */
    public void setRestartWorker(Workable<WorkUnit> restartWorker) {
        this.restartWorker = restartWorker;
    }

}
