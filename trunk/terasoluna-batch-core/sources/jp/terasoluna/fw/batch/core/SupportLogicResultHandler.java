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

import jp.terasoluna.fw.batch.openapi.BLogicResult;

/**
 * サポート処理実行結果のハンドラインタフェース。
 * 
 * <p>このインタフェースは、サポート処理（<code>SupportLogic</code> インタフェー
 * スの実装クラス）が起動される毎に、そのサポート処理が返却したサポート処理の実
 * 行結果を処理するために起動される。</p>
 * 
 * <p>このインタフェースの実装では、パラメータとして渡されたサポート処理の実行結
 * 果に基づいてジョブステータスへの反映や、ログ出力などを行うことができる。
 * また、ジョブステータスを変更することでジョブの継続、終了を指定することができ
 * る。</p>
 * 
 */
public interface SupportLogicResultHandler {

    /**
     * サポート処理の実行結果を処理する。
     * 
     * @param result サポート処理の実行結果
     * @param jobStatus ジョブステータス
     * @param name サポート処理クラスに設定された名前
     */
    void handle(BLogicResult result, JobStatus jobStatus, String name);
}
