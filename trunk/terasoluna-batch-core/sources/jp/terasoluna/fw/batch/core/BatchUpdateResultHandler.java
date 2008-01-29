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

package jp.terasoluna.fw.batch.core;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * バッチ更新処理結果のハンドラクラス。
 * 
 * <p>バッチ更新成功時にのみ呼ばれ、ジョブステータス、および実行が成功した
 * バッチ更新リストが渡される。
 * バッチ更新リストの情報をジョブステータスに反映することができる。</p>
 *
 */
public interface BatchUpdateResultHandler {

    /**
     * バッチ更新処理結果を処理する。
     * 
     * @param jobStatus ジョブステータス
     * @param batchUpdateMapList バッチ更新用のSQLIDとパラメータを保持したMapのリスト
     */
    void handle(JobStatus jobStatus,
            List<LinkedHashMap<String, Object>> batchUpdateMapList);
}
