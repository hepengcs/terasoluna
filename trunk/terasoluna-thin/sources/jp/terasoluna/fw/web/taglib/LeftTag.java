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

/**
 * <p>
 *  文字列の左端から指定された文字数分の文字列を切り出す <code>left</code>
 *  タグの実装クラス。
 * </p>
 *
 * <p>
 *  {@link jp.terasoluna.fw.util.StringUtil} クラスの
 * <code>substring()</code> メソッドによって、文字列の左端から指定された
 * 文字数を切り出す。
 * </p>
 *
 * <br>
 *
 * <h5>タグがサポートする属性</h5>
 * <p> {@link StringFormatterTagBase} を参照。</p>
 *
 * <br>
 *
 * <h5>カスタムタグのスクリプティング変数</h5>
 * <p>
 *  {@link StringFormatterTagBase} を参照。<br>
 *  以下、このクラスで追加されたスクリプティング変数
 * </p>
 *
 * <p><div align="center">
 * <table width="90%" border="1">
 *  <tr>
 *   <th>属性名</th>
 *   <th>デフォルト</th>
 *   <th>実行時式</th>
 *   <th>記述</th>
 *  </tr>
 *  <tr>
 *   <td> length </td>
 *   <td> - </td>
 *   <td> <code>true</code> </td>
 *   <td align="left">
 *    フォーマット対象の文字列から切り出す文字数。
 *   </td>
 *  </tr>
 * </table>
 * </div></p>
 *
 * <br>
 *
 * <h5>使用方法</h5>
 * <p>
 *  <code>&quot;form0001&quot;</code> beanの
 *  <code>&quot;field001&quot;</code> プロパティの値を、左側５文字を切り出して
 *  出力するには、以下のように記述する。
 * </p>
 *
 * <p><code><pre>
 *  &lt;t:left name="form0001"
 *             property="field001"
 *             length="5" /&gt;
 * </pre></code></p>
 *
 * <p>
 *  上記の beanのプロパティを、カスタムタグで出力せずにスクリプティング変数
 *  <code>&quot;cut&quot;</code>へセットする場合には、以下のように記述する。
 * </p>
 *
 * <p><code><pre>
 *  &lt;t:left id="cut"
 *             name="form0001"
 *             property="field001"
 *             length="5" /&gt;
 * </pre></code></p>
 *
 * <p>
 *  指定文字数分切り出す元となる文字列を、beanから取り出さずに、
 *  カスタムタグの <code>value</code>
 *  属性によって指定する場合には、以下のように記述する。
 * </p>
 *
 * <p><code><pre>
 *  &lt;t:left value="指定文字数分切り出し前の文字列  "
 *             length="5" /&gt;
 * </pre></code></p>
 *
 * @see jp.terasoluna.fw.web.taglib.StringFormatterTagBase
 *
 */
public class LeftTag extends StringFormatterTagBase {

    /**
     * シリアルバージョンID
     */
    private static final long serialVersionUID = 220591418239073704L;

    /**
     * 切り出す文字列の長さ。
     *
     */
    protected int length = 0;

    /**
     * 切り出す文字列の長さを取得する。
     *
     * @return 切り出す文字列の長さ
     */
    public int getLength() {
        return this.length;
    }

    /**
     * 切り出す文字列の長さを設定する。
     *
     * @param length 切り出す文字列の長さ
     */
    public void setLength(int length) {
        this.length = length;
    }

    /**
     * すべてのアロケートされた資源を解放する。
     */
    @Override
    public void release() {
        super.release();
        length = 0;
    }

    /**
     * 指定された文字列の左端から <code>lenth</code> 属性で指定された文字数分の
     * 文字列を取得する。
     *
     * @param s フォーマット対象の文字列
     * @return 切り出された文字列
     */
    @Override
    protected String doFormat(String s) {
        return s.length() > length ? s.substring(0, length) : s;
    }

}
