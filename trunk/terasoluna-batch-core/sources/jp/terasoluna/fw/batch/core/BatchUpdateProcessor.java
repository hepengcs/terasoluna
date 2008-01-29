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
 * バッチ更新プロセッサのインタフェース。
 * 
 * <p>BLogicから依頼されたバッチ更新処理を行うバッチ更新プロセッサが実装する
 * インタフェース。</p>
 * 
 * <p>バッチ更新の実行に失敗した場合には、例外 <code>BatchUpdateException</code>
 * をスローする。</p>
 *
 */
public interface BatchUpdateProcessor {

    /**
     * 引数で指定されたバッチ更新リストを処理する。
     * 
     * @param batchUpdateMapList バッチ更新リスト
     */
    void processBatchUpdate(
            List<LinkedHashMap<String, Object>> batchUpdateMapList);
}
