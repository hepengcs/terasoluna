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

import org.apache.struts.util.MessageResources;
import org.apache.struts.util.MessageResourcesFactory;

/**
 * PropertyMessageResourcesExを生成するファクトリクラス。
 *
 * <p>
 * システム、および業務共通のメッセージリソースを
 * 取得可能にする{@link PropertyMessageResourcesEx}を生成するファクトリクラス。
 * </p>
 * <h5>使用方法</h5>
 * <p>
 * Struts設定ファイル(struts-config.xml)中の&lt;message-resources&gt;要素に
 * 以下の記述を追加して、PropertyMessageResourcesExFactory
 * がメッセージファクトリとして用いられるようにする。
 * </p>
 * <pre><code>
 * &lt;message-resources parameter="message"
 *                    factory="jp.terasoluna.fw.web.struts.action.PropertyMessageResourcesExFactory"/&gt;
 * </code></pre>
 * <p>
 * 上記例では、message.propertiesというメッセージリソース定義ファイル、
 * 及び、ファクトリクラスであるPropertyMessageResourcesExFactoryを
 * 指定している。<br>
 * Strutsの提供するメッセージリソースを利用する方法、及び設定の詳細については、
 * {@link DBMessageResources}を参照のこと。
 * </p>
 *
 * @see jp.terasoluna.fw.web.struts.action.PropertyMessageResourcesEx
 * @see jp.terasoluna.fw.web.struts.action.DBMessageResourcesFactory
 * @see jp.terasoluna.fw.web.struts.action.DBMessageResources
 * @see jp.terasoluna.fw.web.struts.action.GlobalMessageResources
 *
 */
public class PropertyMessageResourcesExFactory extends MessageResourcesFactory {

    /**
	 * シリアルバージョンID
	 */
	private static final long serialVersionUID = 2857185835853238603L;

	/**
     *  新規に <code>PropertyMessageResourcesEx</code> を生成する。
     *
     * @param config リクエストバンドルに対する設定パラメータ
     * 
     * @return <code>PropertyMessageResourcesEx</code> インスタンス
     */
    @Override
    public MessageResources createResources(@SuppressWarnings("hiding") String config) {
        return new PropertyMessageResourcesEx(
            this, config, super.returnNull);
    }
}
