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

package jp.terasoluna.fw.web.struts.action;

import java.util.Locale;

import org.apache.struts.util.MessageResources;
import org.apache.struts.util.MessageResourcesFactory;
import org.apache.struts.util.PropertyMessageResources;

/**
 * システム（フレームワーク）と業務共通のメッセージリソースを表示可能にする
 * プロパティファイルメッセージリソース。
 * 
 * <p>
 * Strutsの仕様として、メッセージリソース定義ファイルを利用する場合、
 * その定義はモジュールごとに独立しているため、
 * すべてのモジュールに共通するメッセージリソースは一元的に定義できない。<br>
 * TERASOLUNAではモジュールすべてに共通な、
 * 業務共通メッセージや、システムメッセージを利用するための方法を
 * 提供している。<br>
 * このクラスは、StrutsのPropertyMessageResourcesを拡張し、
 * 各業務のメッセージリソース定義ファイルだけでなく、業務共通メッセージと、
 * システムのメッセージを利用可能にする。<br>
 * 業務共通・システムのメッセージリソースの定義内容については、
 * GlobalMessageResourcesを参照のこと。
 * </p>
 * <h5>使用方法</h5>
 *  このクラスを利用するには、struts-config.xml中の
 *  &lt;message-resource&gt;要素で
 * <ul>
 *  <li>parameter属性にプロパティファイル名(.propertiesは不要)</li>
 *  <li>factory属性に、PropertyMessageResourcesExFactory</li>
 * </ul>
 *  を指定する。下記はstruts-config.xmlの設定例である。
 * <pre><code>
 * &lt;struts-config&gt;
 *   …
 *   &lt;message-resources parameter="MessageResources"
 *                      factory="jp.terasoluna.fw.web.struts.action.PropertyMessageResourcesExFactory"
 *   /&gt;
 *  …
 * &lt;/struts-config&gt;
 * </code></pre>
 *
 * <h5>制限事項</h5>
 * <ol>
 *  <li>システムのメッセージリソースは国際化対応されない。</li>
 *  <li>業務共通のメッセージリソースは国際化対応されない。</li>
 * </ol>
 * 同一キーで取得されるメッセージリソースの優先順位は下記の通りとなる。
 * <ol>
 *   <li>メッセージリソース定義ファイルのメッセージリソース</li>
 *   <li>業務共通メッセージリソース定義ファイル
 *       （application-messages.properties）のメッセージリソース</li>
 *   <li>システムメッセージリソース定義ファイル（system-messages.properties）の
 *       メッセージリソース</li>
 * </ol>
 *
 * @see jp.terasoluna.fw.web.struts.action.PropertyMessageResourcesExFactory
 * @see jp.terasoluna.fw.web.struts.action.DBMessageResources
 * @see jp.terasoluna.fw.web.struts.action.GlobalMessageResources
 *
 */
public class PropertyMessageResourcesEx extends PropertyMessageResources {

    /**
	 * シリアルバージョンID
	 */
	private static final long serialVersionUID = -8239553100324527837L;

	/**
     * コンストラクタ。
     *
     * @param factory このクラスのファクトリオブジェクト
     * @param config メッセージリソースファイル名
     */
    public PropertyMessageResourcesEx(
            MessageResourcesFactory factory,
            String config) {
        super(factory, config);
    }

    /**
     * コンストラクタ。
     *
     * @param factory このクラスのファクトリオブジェクト
     * @param config メッセージリソースファイル名
     * @param returnNull メッセージリソースキーが登録されていない場合、nullを
     *                   返すかどうか
     */
    public PropertyMessageResourcesEx(MessageResourcesFactory factory,
                                      String config,
                                      boolean returnNull) {
        super(factory, config, returnNull);
    }

    /**
     * メッセージを取得する。
     * メッセージ取得の優先順位は下記のようになる。
     * <ol>
     *  <li>(各モジュールで定義された)メッセージリソース
     *  ファイル内のメッセージ</li>
     *  <li>業務共通のメッセージ</li>
     *  <li>システムのメッセージ</li>
     * </ol>
     *
     * @param locale リクエストから取得されたロケール
     * @param key メッセージリソースキー
     * @return メッセージ
     */
    @Override
    public String getMessage(Locale locale, String key) {
        // 業務のメッセージリソースファイルを検索し、メッセージを返却する。
        String localMessage = super.getMessage(locale, key);
        if (localMessage != null && !localMessage.startsWith("???")
                && !localMessage.endsWith("???")) {
            // nullが返却されず、キー値が"???"で挟まれていない
            // (コンストラクタのreturnNullがfalse)の時、
            // 業務メッセージを返却する。
            return localMessage;
        }

        // 業務共通・システムのメッセージが見つかった場合返却する。
        MessageResources globalMessageResources
            = GlobalMessageResources.getInstance();
        String globalMessage = globalMessageResources.getMessage(locale, key);
        if (globalMessage != null) {
            // 業務共通・システムメッセージが見つかった場合は、
            // メッセージを返却する。
            return globalMessage;
        }
        // 見つからない場合、Strutsのメッセージリソース形式で返却
        return localMessage;
    }
}
