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
 * DBMessageResourcesを生成するファクトリクラス。
 *
 * <p>
 * DBに設定されたメッセージリソースを利用するには、
 * Struts設定ファイル(struts-config.xml)中の
 * &lt;message-resource&gt;要素に以下の記述を追加して、
 * DBMessageResourcesFactory
 * がメッセージファクトリとして用いられるようにする。
 * </p>
 * <pre><code>
 * &lt;message-resources parameter="MessageResources"
 *                    factory="jp.terasoluna.fw.web.common.DBMessageResourcesFactory"
 * /&gt;
 * </code></pre>
 * <p>
 * 上記例では、MessageResources.propertiesというメッセージリソース定義ファイル
 * 及び、ファクトリクラスであるDBMessageResourcesFactoryを指定して
 * いる。<br>
 * Strutsが提供する、メッセージリソースを利用する方法、及び設定の詳細については、
 * DBMessageResourcesを参照のこと。
 * </p>
 *
 * @see jp.terasoluna.fw.web.struts.action.DBMessageResources
 * 
 */
public class DBMessageResourcesFactory extends MessageResourcesFactory {

    /**
     * シリアルバージョンID
     */
    private static final long serialVersionUID = -1027132907070919579L;

    /**
     *  新規にDBMessageResourcesを生成する。
     *
     * @param config リクエストバンドルに対する設定パラメータ
     * 
     * @return DBMessageResourcesインスタンス
     */
    @Override
    public MessageResources createResources(@SuppressWarnings("hiding") String config) {

        return new DBMessageResources(this, config, super.returnNull);
    }
}
