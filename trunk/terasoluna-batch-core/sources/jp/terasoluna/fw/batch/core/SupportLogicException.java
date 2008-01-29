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
 * サポート処理例外クラス。
 * 
 * <p>サポート処理、あるいはサポート処理の実行結果ハンドラから例外がスローされた
 * 場合に、フレームワークによってこのクラスでラップされ解析等に必要な情報が追
 * 加される。このクラスでは、原因例外、およびサポート処理の実行結果を保持する。
 * </p>
 * 
 * <p>サポート処理の実行結果は、原因例外の発生元がサポート処理の実行結果ハンドラ
 * である場合にのみ設定される。原因例外の発生元がサポート処理である場合には、
 * サポート処理の実行結果には <code>null</code> が設定される。</p>
 * 
 * <p>例外ハンドラマップにおいて、キーとして登録することができる。キーとして登録
 * するために、この例外クラスは、引数のない <code>public</code> なコンストラクタ
 * を持つ。</p>
 */
public class SupportLogicException extends JobException {

    /**
     * Serializable用バージョンID
     */
    private static final long serialVersionUID = 3772461298792124177L;

    /**
     * サポート処理の実行結果。
     */
    private BLogicResult blogicResult = null;

    /**
     * 処理名。
     */
    private String supportProcessorName = null;

    /**
     * コンストラクタ。
     */
    public SupportLogicException() {
    }
    
    /**
     * コンストラクタ。
     *
     * @param e 原因例外
     * @param blogicResult サポート処理の実行結果。<code>null</code> 可。
     * @param supportProcessorName サポート処理クラスに設定された名前
     */
    public SupportLogicException(Exception e, BLogicResult blogicResult,
            String supportProcessorName) {
        super(e);
        this.blogicResult = blogicResult;
        this.supportProcessorName = supportProcessorName;
    }

    /**
     * サポート処理の実行結果を取得する。
     *
     * @return サポート処理の実行結果。
     *          原因例外の発生元がサポート処理である場合には、<code>null</code>。
     */
    public BLogicResult getBlogicResult() {
        return blogicResult;
    }
    
    /**
     * 処理名を取得する。
     *
     * @return 処理名。
     */
    public String getSupportProcessorName() {
        return supportProcessorName;
    }
}
