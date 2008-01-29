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

import jp.terasoluna.fw.batch.commonj.listener.WorkMapListener;
import jp.terasoluna.fw.batch.core.JobException;
import jp.terasoluna.fw.batch.core.JobExceptionHandler;
import jp.terasoluna.fw.batch.core.JobStatus;
import jp.terasoluna.fw.batch.core.WorkQueue;
import jp.terasoluna.fw.batch.standard.QueueProcessor;

import commonj.work.WorkItem;
import commonj.work.WorkManager;


/**
 * ワーカー実行サービス(commonJ)のインタフェース。
 *
 *
 */
public interface WorkerExecutorService {
    /**
     * デフォルト例外ハンドラの取得。
     * 
     * @return デフォルト例外ハンドラ
     */
    JobExceptionHandler getDefaultJobExceptionHandler();

    /**
     * 例外ハンドラを格納したMapの取得。
     * 
     * @return 例外ハンドラを格納したマップ
     */
    LinkedHashMap<JobException, JobExceptionHandler> getExceptionHandlerMap();

    /**
     * ワークマネージャの取得。
     * 
     * @return ワークマネージャ
     */
    WorkManager getWorkManager();

    /**
     * ワークリスナーの取得。
     * 
     * @return ワークリスナー
     */
    WorkMapListener getWorkMapListener();
    
    /**
     * Workerの実行を登録する。
     * 
     * @param queueProcessor キュー処理プロセッサ
     * @param workQueue キュー
     * @param jobStatus ジョブステータス
     * @return ワーカの処理結果に非同期でアクセスすためのワークアイテム
     */
     WorkItem submit(QueueProcessor queueProcessor,
            WorkQueue workQueue, JobStatus jobStatus);

}
