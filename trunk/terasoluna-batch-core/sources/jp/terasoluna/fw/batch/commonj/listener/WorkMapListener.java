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

import commonj.work.Work;
import commonj.work.WorkItem;
import commonj.work.WorkListener;

/**
 * スケジュールされたワークを監視するためのインタフェース。<br>
 * commonj.work.WorkListenerを拡張して、WorkItemとWorkを紐付けするための 
 * メソッドを用意している。
 * 
 * 
 */
public interface WorkMapListener extends WorkListener {

    /**
     * スケジュールされたワークの登録。
     * 
     * @param workItem ワークをワークマネジャーでスケジュールしたときの返却値
     * @param work
     *            スケジュールしたワーク
     */
    void addWork(WorkItem workItem, Work work);

    /**
     * スケジュールされているワークの取得。
     * 
     * @param workItem ワークをワークマネジャーでスケジュールしたときの返却値
     * @return スケジュールされているワーク
     */
    Object getWork(WorkItem workItem);

    /**
     * 作業が完了したワークを削除する。
     * 
     * @param workItem ワークをワークマネジャーでスケジュールしたときの返却値
     * @return スケジュールされているワーク
     */
    Object removeWork(WorkItem workItem);

}
