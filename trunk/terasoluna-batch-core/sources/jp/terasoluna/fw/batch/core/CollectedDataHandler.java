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

/**
 * 対象データのハンドラインタフェース。
 * 
 * <p><code>Collector</code> で収集された対象データは、このインタフェースを
 * 通して渡される。このインタフェースの実装クラスでは、取得した対象データの種別
 * （ビジネスロジックで処理するデータであるか、あるいはジョブを分割するための
 * 分割キーであるか、等）に応じて処理する。</p>
 * 
 */
public interface CollectedDataHandler {

    /**
     * 対象データを処理する。
     * 
     * @param collectedData コレクタで収集したデータ
     * @param index データのインデックス
     */
    void handle(Object collectedData, int index);
    
    /**
     * 対象データの処理を終了する。
     *
     */
    void close();
}
