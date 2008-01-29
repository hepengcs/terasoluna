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

import java.util.LinkedHashMap;
import java.util.List;

import jp.terasoluna.fw.batch.core.BatchUpdateResultHandler;
import jp.terasoluna.fw.batch.core.JobStatus;

/**
 * <code>BatchUpdateResultHandler</code> インタフェースの標準実装クラス。
 * 
 */
public class StandardBatchUpdateResultHandler 
    implements BatchUpdateResultHandler {

    /**
     * バッチ更新結果をジョブステータスへ反映する。
     *
     * @param jobStatus ジョブステータス
     * @param batchUpdateMapList バッチ更新用のSQLIDとパラメータを保持したMapのリスト
     */
    public void handle(JobStatus jobStatus, 
            List<LinkedHashMap<String, Object>> batchUpdateMapList) {
        int totalSize = 0;
        for (LinkedHashMap<String, Object> batchUpdateMap
                : batchUpdateMapList) {
            if (batchUpdateMap == null || batchUpdateMap.size() == 0) {
                continue;
            }
            totalSize += batchUpdateMap.size();
        }
        jobStatus.incrementBatchUpdateCount(totalSize);
    }
}
