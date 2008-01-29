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

import jp.terasoluna.fw.batch.openapi.BLogicResult;

/**
 * ビジネスロジック実行結果のハンドラインタフェース。
 * 
 * <p>このインタフェースは、ビジネスロジックが起動される毎に、
 * そのビジネスロジックが返却したビジネスロジック実行結果を処理するために
 * 起動される。
 * </p>
 * 
 * <p>このインタフェースの実装では、パラメータとして渡されたビジネスロジック
 * 実行結果に基づいてジョブステータスへの反映や、ログ出力などを行うことができる。
 * また、ジョブステータスを変更することでジョブの継続、終了を指定することができ
 * る。</p>
 *
 */
public interface BLogicResultHandler {

    /**
     * ビジネスロジック実行結果を処理する。
     *
     * @param result ビジネスロジック実行結果
     * @param blogicInputData ビジネスロジック入力データ
     * @param jobStatus ジョブステータス
     * @param batchUpdateMapList バッチ更新リスト
     */
    void handle(BLogicResult result, Object blogicInputData,
            JobStatus jobStatus,
            List<LinkedHashMap<String, Object>> batchUpdateMapList);

}
