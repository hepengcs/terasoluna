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

import jp.terasoluna.fw.util.StringUtil;

/**
 * <p>文字列の両側（左側、および右側）のホワイトスペースを削除するtrim
 * タグの実装クラス。</p>
 *
 * <p><code>jp.terasoluna.fw.util.StringUtil</code> クラスの
 * <code>trim()</code> メソッドによって文字列の左右のホワイトスペースを
 * 削除する。</p>
 *
 * <p>タグの属性や、スクリプティング変数については、
 * <code>jp.terasoluna.web.taglib.StringFormatterTagBase
 * </code> を継承する。詳細は
 * <code>jp.terasoluna.web.taglib.StringFormatterTagBase
 * </code> のドキュメントを参照のこと。</p>
 *
 * <p>ホワイトスペースとして判断される文字については、
 * <code>jp.terasoluna.fw.util.StringUtil</code> クラスの
 * ドキュメントを参照のこと。</p>
 *
 * <p><code>&quot;form0001&quot;</code> beanの
 * <code>&quot;field001&quot;</code> プロパティの値を左右両側の
 * ホワイトスペースを除去して出力するには、以下のように記述する。</p>
 *
 * <p>例:<br>
 * <code><pre>
 *  &lt;t:trim name="form0001"
 *             property="field001" /&gt;
 * </pre></code></p>
 *
 * <p>上記の beanのプロパティを、カスタムタグで出力せずにスクリプティング変数
 * <code>&quot;trimmed&quot;</code>
 * へセットする場合には、以下のように記述する。</p>
 *
 * <p>例:<br>
 * <code><pre>
 *  &lt;t:trim id="trimmed"
 *             name="form0001"
 *             property="field001" /&gt;
 * </pre></code></p>
 *
 * <p>左右両側のホワイトスペースを除去する元の文字列をbeanから取り出さずに、
 * カスタムタグの <code>value</code>
 * 属性によって指定する場合には、以下のように記述する。</p>
 *
 * <p>例:<br>
 * <code><pre>
 *  &lt;t:trim value="左右両側ホワイトスペース除去前の文字列  " /&gt;
 * </pre></code></p>
 *
 * @see jp.terasoluna.fw.web.taglib.StringFormatterTagBase
 *
 */
public class TrimTag extends StringFormatterTagBase {

	/**
	 * シリアルバージョンID
	 */
	private static final long serialVersionUID = 8026613148973363578L;

	/**
     * 全角スペースのトリム可否属性。
     */
    protected boolean zenkaku = false;

    /**
     * 全角スペースのトリム可否属性を取得する。
     *
     * @return 全角スペースのトリム可否属性
     */
    public boolean getZenkaku() {
        return this.zenkaku;
    }

    /**
     * 全角スペースのトリム可否属性を設定する。
     *
     * @param zenkaku 全角スペースのトリム可否属性
     */
    public void setZenkaku(boolean zenkaku) {
        this.zenkaku = zenkaku;
    }

    /**
     * <p>指定された文字列の右側のホワイトスペースを削除する。</p>
     *
     * @param s フォーマット対象の文字列
     * @return 両側（左側、および右側）のホワイトスペースが削除された文字列
     */
    @Override
    protected String doFormat(String s) {
    	if (zenkaku) {
    		return StringUtil.trimZ(s);
    	}
        return StringUtil.trim(s);
    }

}
