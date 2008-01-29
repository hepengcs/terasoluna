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

package jp.terasoluna.fw.batch.commonj.listener;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import commonj.work.Work;
import commonj.work.WorkEvent;
import commonj.work.WorkItem;

/**
 * CommonJワークマネージャ上で実行しているワークキューの状態を監視するクラス。
 * 
 * 
 */
public class WorkQueueListener implements WorkMapListener {

    /**
     * ログインスタンス。
     */
    private static Log log = LogFactory.getLog(WorkQueueListener.class);

    /**
     * スケジュールされたワークを保持するマップ。
     */
    private Map<WorkItem, Work> map = Collections
            .synchronizedMap(new HashMap<WorkItem, Work>());

    /**
     * スケジュールされたワークの登録。
     * 
     * @param workItem ワークをワークマネジャーでスケジュールしたときの返却値
     * @param work スケジュールしたワーク
     */
    public void addWork(WorkItem workItem, Work work) {
        map.put(workItem, work);
    }

    /**
     * スケジュールされているワークの取得。
     * 
     * @param workItem ワークをワークマネジャーでスケジュールしたときの返却値
     * @return スケジュールされているワーク
     */
    public Work getWork(WorkItem workItem) {
        return (Work) map.get(workItem);
    }

    /**
     * 作業が完了したワークを削除する。
     * 
     * @param workItem ワークをワークマネジャーでスケジュールしたときの返却値
     * @return スケジュールされているワーク
     */
    public Work removeWork(WorkItem workItem) {
        return (Work) map.remove(workItem);
    }

    /**
     * ワークの受付時に実行されるメソッド。
     * 
     * @param we ワークイベント
     */
    public void workAccepted(WorkEvent we) {
        printSimpleLog("Work accepted: ", getWork(we.getWorkItem()));
    }

    /**
     * ワークの完了時に実行されるメソッド。
     * 
     * @param we ワークイベント
     */
    public void workCompleted(WorkEvent we) {
        printSimpleLog("Work completed: ", removeWork(we.getWorkItem()));
    }

    /**
     * ワークの受付拒否時に実行されるメソッド。
     * 
     * @param we ワークイベント
     */
    public void workRejected(WorkEvent we) {
        printSimpleLog("Work rejected: ", removeWork(we.getWorkItem()));
    }

    /**
     * ワークの開始時に実行されるメソッド。
     * 
     * @param we ワークイベント
     */
    public void workStarted(WorkEvent we) {
        printSimpleLog("Work started: ", getWork(we.getWorkItem()));
    }

    /**
     * 簡単なログ出力。
     * 
     * @param message ログメッセージ
     * @param object 出力オブジェクト
     */
    private void printSimpleLog(String message, Object object) {
        if (log.isDebugEnabled()) {
            StringBuilder builder = new StringBuilder(message);
            builder.append("[");
            builder.append(object);
            builder.append("]");
            log.debug(builder.toString());
        }
    }

}
