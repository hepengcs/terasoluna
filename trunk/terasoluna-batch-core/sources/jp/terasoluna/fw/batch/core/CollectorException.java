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
 * 対象データ例外クラス。
 * 
 * <p>対象データ取得処理(<code>Collector</code>)で例外が発生した場合に、
 * フレームワークによってこのクラスでラップされ、解析等に必要な情報が
 * 追加される。</p>
 * このクラスでは、原因例外、および対象データ取得結果を保持する。</p>
 * 
 * <p>対象データ取得結果は、原因例外の発生元が対象データ取得結果ハンドラである
 * 場合にのみ設定される。原因例外の発生元が対象データ取得処理である場合には、
 * 対象データ取得結果には<code>null</code> が設定される。</p>
 * 
 * <p>例外ハンドラマップにおいて、キーとして登録することができる。キーとして
 * 登録するために、この例外クラスは、引数のない <code>public</code> な
 * コンストラクタを持つ。</p>
 *
 */
public class CollectorException extends JobException {

    /**
     * Serializable用バージョンID。
     */
    private static final long serialVersionUID = 2520093984053973902L;

    /**
     * コレクタの処理結果。
     */
    private CollectorResult collectorResult = null;
    
    /**
     * コンストラクタ。
     */
    public CollectorException() {
    }

    /**
     * コンストラクタ。
     *
     * @param e 発生した例外
     * @param collectorResult コレクタの処理結果。<code>null</code> 可。
     */
    public CollectorException(Exception e, CollectorResult collectorResult) {
        super(e);
        this.collectorResult = collectorResult;
    }

    /**
     * コレクタの処理結果を取得する。
     *
     * @return コレクタの処理結果
     */
    public CollectorResult getCollectorResult() {
        return collectorResult;
    }
}
