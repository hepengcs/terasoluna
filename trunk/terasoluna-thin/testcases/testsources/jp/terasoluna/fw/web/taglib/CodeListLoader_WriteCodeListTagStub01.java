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

package jp.terasoluna.fw.web.taglib;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import jp.terasoluna.fw.web.codelist.CodeBean;
import jp.terasoluna.fw.web.codelist.CodeListLoader;

/**
 * コードリスト情報の初期化を <code>Map</code> で行う、
 * {@link jp.terasoluna.fw.web.codelist.CodeListLoader} 実装クラス。<br>
 * <br>
 * このクラスを用いてコードリストを形成する場合は、 {@link #setCodeListMap(Map)} メソッドでコードリスト情報を
 * <code>Map</code> 形式で与えた後、{@link #load()} を実行する必要がある。<br>
 * <h5>Springフレームワークでの使用例。</h5>
 * <code><pre>
 *  &lt;bean id=&quot;reader1&quot;
 *        class=&quot;jp.terasoluna.fw.web.codelist.MappedCodeListLoader&quot;
 *        init-method=&quot;load&quot;&gt;
 *      &lt;property name=&quot;codeListMap&quot;&gt;
 *          &lt;map&gt;
 *              &lt;entry key=&quot;001&quot;&gt;
 *                  &lt;value&gt;value001&lt;/value&gt;
 *              &lt;/entry&gt;
 *              &lt;entry key=&quot;002&quot;&gt;
 *                  &lt;value&gt;value002&lt;/value&gt;
 *              &lt;/entry&gt;
 *              &lt;entry key=&quot;003&quot;&gt;
 *                  &lt;value&gt;value003&lt;/value&gt;
 *              &lt;/entry&gt;
 *          &lt;/map&gt;
 *      &lt;/property&gt;
 *  &lt;/bean&gt;
 * </pre></code>
 * 
 */
public class CodeListLoader_WriteCodeListTagStub01 implements CodeListLoader {


		/**
		 * コードリスト情報を初期化時に与えるための <code>Map</code> 。
		 */
		private Map<String, String> codeListMap = null;

		/**
		 * コードリスト。
		 * 
		 * @see CodeBean
		 */
		private List<CodeBean> codeLists = null;

		/**
		 * コードリスト初期化情報 <code>Map</code> を取得する。
		 * 
		 * @return コードリスト初期化情報 <code>Map</code>
		 */
		public Map getCodeListMap() {
			return codeListMap;
		}

		/**
		 * コードリスト初期化情報 <code>Map</code> を設定する。
		 * 
		 * @param codeListMap
		 *            コードリスト初期化情報 <code>Map</code>
		 */
		public void setCodeListMap(Map<String, String> codeListMap) {
			this.codeListMap = codeListMap;
		}

		/**
		 * コードリストの初期化を行う。<br>
		 * <br>
		 * 既に設定されている <code>codeListMap</code> の情報から {@link CodeBean} を生成する。
		 */
		public void load() {
			if (codeLists != null) {
				return;
			}
			if (codeListMap == null) {
				codeLists = Collections.unmodifiableList(new ArrayList<CodeBean>());
				return;
			}
			List<CodeBean> list = new ArrayList<CodeBean>();
			Iterator it = codeListMap.keySet().iterator();
			while (it.hasNext()) {
				String key = (String) it.next();
				String value = codeListMap.get(key);
				CodeBean cb = new CodeBean();
				cb.setId(key);
				cb.setName(value);
				list.add(cb);
			}
			codeLists = Collections.unmodifiableList(list);
		}

		/**
		 * コードリストを取得する。<br>
		 * <br>
		 * コードリストは {@link CodeBean} の配列として取得できる。<br>
		 * ※コードリストは原則としてアプリケーション中で一意となる情報である。 このメソッドをオーバーライドする場合は、業務ロジックなどで
		 * コードリストの内容が編集されても影響がないように実装する必要がある。
		 * 
		 * @return コードリスト
		 */
		public CodeBean[] getCodeBeans() {
				return null;
		}
}
