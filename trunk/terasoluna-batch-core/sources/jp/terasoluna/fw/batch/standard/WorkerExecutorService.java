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

import java.util.concurrent.Future;

import jp.terasoluna.fw.batch.core.JobStatus;
import jp.terasoluna.fw.batch.core.WorkQueue;

/**
 * ワーカー実行サービスのインタフェース。
 *
 */
public interface WorkerExecutorService {

    /**
     * ワーカー実行を登録する。
     *
     * @param queueProcessor キュー処理プロセッサ
     * @param workQueue キュー
     * @param jobStatus ジョブステータス
     * @return ワーカの処理結果に非同期でアクセスすためのフューチャー
     */
    Future< ? > submit(QueueProcessor queueProcessor,
            WorkQueue workQueue, JobStatus jobStatus);
}
