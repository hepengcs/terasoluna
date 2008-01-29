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

package jp.terasoluna.fw.batch.init;

import jp.terasoluna.fw.batch.core.CollectedDataHandler;
import jp.terasoluna.fw.batch.core.WorkQueue;
import jp.terasoluna.fw.batch.core.WorkUnit;

/**
 * 非同期ジョブ起動時のジョブ依頼情報格納用キュークラス。
 *
 */
public class JobRequestInfoHandler implements CollectedDataHandler {

    /**
     * キュー。
     */
    private WorkQueue queue;

    /**
     * コンストラクタ。
     *
     * @param queue ワークキュー
     */
    public JobRequestInfoHandler(WorkQueue queue) {
        this.queue = queue;
    }

    /**
     * キューを終了する。
     * キューの終端であるインスタンスをキューに追加する。
     */
    public void close() {
        queue.close();
    }

    /**
     * ジョブ依頼情報をキューに追加する。
     *
     * @param obj ジョブ依頼情報
     * @param collected 取得番号
     */
    public void handle(Object obj, int collected) {
        queue.put((WorkUnit) obj);
    }
}
