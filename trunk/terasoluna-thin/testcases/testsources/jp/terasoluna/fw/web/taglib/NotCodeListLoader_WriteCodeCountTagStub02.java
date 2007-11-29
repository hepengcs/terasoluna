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

/**
 * クラスのブラックボックステスト。
 * 
 * <p>
 * <h4>【クラスの概要】</h4>
 * CodeListLoaderを実装しないクラスのインスタンス。
 * <p>	
 * 
 */

public class NotCodeListLoader_WriteCodeCountTagStub02 {
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
