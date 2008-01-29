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

package jp.terasoluna.fw.batch.partition;

import jp.terasoluna.fw.batch.core.CollectedDataHandler;
import jp.terasoluna.fw.batch.core.WorkQueue;
import jp.terasoluna.fw.batch.openapi.JobContext;

/**
 * 分割処理時にパーティションキーを処理する対象データハンドラ。
 *
 */
public class PartitionKeyHandler implements CollectedDataHandler {

    /**
     * キュー。
     */
    private WorkQueue queue;
    
    /**
     * ジョブコンテクスト。
     */
    private JobContext jobContext;

    /**
     * コンストラクタ。
     *
     * @param queue ワークキュー
     * @param jobContext ジョブコンテクスト
     */
    public PartitionKeyHandler(WorkQueue queue, JobContext jobContext) {
        this.queue = queue;
        this.jobContext = jobContext;
    }

    /**
     * 分割キーをキューに追加する。
     *
     * @param partitionKey 分割キー
     * @param partitonNo 分割番号
     */
    public void handle(Object partitionKey, int partitonNo) {
        JobContext partitionJobContext = 
            jobContext.getChildJobContext(partitionKey);
        partitionJobContext.setPartitionNo(partitonNo);
        PartitionRowObject partitionRowObject
            = new PartitionRowObject(partitonNo, partitionKey,
                    partitionJobContext);
        queue.put(partitionRowObject);
    }

    /**
     * キューを終了する。
     * キューの終端であるインスタンスをキューに追加する。
     */
    public void close() {
        queue.close();
    }
}
