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
 * ビジネスロジック例外クラス。
 *
 * <p>ビジネスロジック、あるいはビジネスロジック処理結果ハンドラから例外が
 * スローされた場合に、フレームワークによってこのクラスでラップされ解析等に
 * 必要な情報が追加される。
 * このクラスでは、原因例外、例外発生時に実行していた入力データ、
 * およびビジネスロジック処理結果を保持する。</p>
 * 
 * <p>ビジネスロジック処理結果は、原因例外の発生元がビジネスロジック処理結果ハン
 * ドラである場合にのみ設定される。原因例外の発生元がビジネスロジックである場合
 * には、ビジネスロジック処理結果には <code>null</code> が設定される。</p>
 * 
 * <p>例外ハンドラマップにおいて、キーとして登録することができる。
 * キーとして登録するために、この例外クラスは、引数のない <code>public</code> 
 * なコンストラクタを持つ。</p>
 *
 */
public class BLogicException extends JobException {

    /**
     * Serializable用バージョンID
     */
    private static final long serialVersionUID = -6698989068077090127L;

    /**
     * 処理対象データ。
     */
    private Object blogicInputData = null;

    /**
     * ビジネスロジック処理結果。
     */
    private BLogicResult blogicResult = null;

    /**
     * コンストラクタ。
     *
     */
    public BLogicException() {
    }
    
    /**
     * コンストラクタ。
     *
     * @param e 例外
     * @param blogicInputData 処理対象データ
     * @param blogicResult ビジネスロジック処理結果。<code>null</code> 可。
     */
    public BLogicException(Exception e, Object blogicInputData,
            BLogicResult blogicResult) {
        super(e);
        this.blogicInputData = blogicInputData;
        this.blogicResult = blogicResult;
    }

    /**
     * 処理対象データを取得する。
     *
     * @return 処理対象データ
     */
    public Object getBlogicInputData() {
        return blogicInputData;
    }

    /**
     * ビジネスロジック処理結果を取得する。
     *
     * @return ビジネスロジック処理結果。
     *          原因例外の発生元がビジネスロジックである場合には、
     *          <code>null</code>。
     */
    public BLogicResult getBlogicResult() {
        return blogicResult;
    }
}
