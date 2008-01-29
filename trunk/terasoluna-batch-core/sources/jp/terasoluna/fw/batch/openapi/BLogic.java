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

package jp.terasoluna.fw.batch.openapi;

/**
 * ビジネスロジックを実行するインタフェース。
 * 
 * <p>
 * 業務開発者は、Collectorで取得された処理対象データクラスと
 * <code>JobContext</code>抽象クラスを実装したジョブコンテキストを受け取り、
 * ビジネスロジック処理結果の<code>BLogicResult</code>を返却する処理を実装する
 * こと。
 * </p>
 * 
 * <strong>設定例</strong><br>
 * 開発者が作成したビジネスロジックをジョブBean定義ファイルに設定する。<br>
 * <pre>
 *     &lt;bean id=&quot;blogic&quot; class=&quot;jp.terasoluna.batch.sample.checksample.SampleBLogic&quot; /&gt;
 * </pre>
 * 
 * <p>
 * 1つのジョブに対し、1つのビジネスロジックを定義する。
 * </p>
 * @param <T> 処理対象データ
 * @param <S> ジョブコンテキスト
 */
public interface BLogic<T, S extends JobContext> {

    /**
     * <code>BLogic</code>を実行する。
     *
     * @param param 処理対象データ
     * @param jobContext ジョブコンテキスト
     * @return ビジネスロジック処理結果
     */
    BLogicResult execute(T param, S jobContext);
}
