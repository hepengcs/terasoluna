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

import jp.terasoluna.fw.batch.openapi.JobContext;

/**
 * ビジネスロジック実行クラスが実装するインタフェース。
 * 
 * <p>ビジネスロジック実行クラスでは、引数で渡された入力データ、
 * ジョブコンテクストなどを用いて、１回のビジネスロジック実行を行う。</p>
 * 
 * <p>そのほかに、このインタフェースの実装クラスでは、ビジネス実行結果に対する
 * 処理結果ハンドラの呼び出しや、例外ハンドラの呼び出しなどを行う。</p>
 * 
 */
public interface BLogicExecutor {

    /**
     * ビジネスロジックを実行する。
     *
     * @param blogicInputData 処理対象データ
     * @param jobContext ジョブコンテキスト
     * @param jobStatus ジョブ処理状況
     * @param batchUpdateMapList バッチ更新リスト
     */
    void executeBLogic(Object blogicInputData, JobContext jobContext,
            JobStatus jobStatus,
            List<LinkedHashMap<String, Object>> batchUpdateMapList);
}
