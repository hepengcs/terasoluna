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

import jp.terasoluna.fw.batch.core.CollectedDataHandlerFactory;
import jp.terasoluna.fw.batch.core.WorkQueue;
import jp.terasoluna.fw.batch.openapi.JobContext;

/**
 * ジョブ依頼情報格納用ワーカキュー生成用クラス。
 *
 */
public class JobRequestInfoHandlerFactory 
    implements CollectedDataHandlerFactory {

    /**
     * ジョブ依頼情報格納用ワーカキューを生成し、取得する。
     *
     * @param workQueue ワークキュー
     * @param jobContext ジョブコンテクスト
     * @return ジョブ依頼情報格納用ワーカキュー
     */
    public JobRequestInfoHandler getHandler(WorkQueue workQueue,
            JobContext jobContext) {
        return new JobRequestInfoHandler(workQueue);
    }
}
