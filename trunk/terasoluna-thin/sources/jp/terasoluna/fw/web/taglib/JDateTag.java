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

import java.util.Date;

import jp.terasoluna.fw.util.DateUtil;

/**
 * <p>
 *  日付時刻データを和暦としてフォーマットするjdateタグの実装クラス。
 * </p>
 *
 * <p>
 * 日付時刻のデータのフォーマットを行う際に、和暦の元号
 * <code>（&quot;昭和&quot;、&quot;S&quot;など）</code>や、
 * 和暦の年（西暦の <code>&quot;2002&quot;</code> 年ではなく、
 * 平成 <code>&quot;14&quot;年</code> など）、および曜日の日本語表記
 * （<code>&quot;月曜日&quot;、&quot;月&quot;</code> など）に変換する。
 * </p>
 *
 * <p>
 *  フォーマットは、<code>pattern</code> 属性によって指定された形式に従って
 *  行われる。{@link JDateTag} クラスでは、基本的には {@link DateTag}
 *  クラスと同様に <code>pattern</code> 属性で指定された出力形式の文字列を
 *  <code>java.text.SimpleDateFormat</code> クラスの<em>時刻パターン文字列</em>
 *  として解釈し、フォーマットする。ただし、時刻パターン文字列の解釈において
 *  以下の点が {@link DateTag} クラスと異なる。
 * </p>
 *
 * <div width="90%" align="center">
 *  <table border="1">
 *   <tr>
 *    <th>記号</th>
 *    <th><code>&nbsp;SimpleDateFormat</code> での意味&nbsp;</th>
 *    <th><code>&nbsp;JdateTag</code> での意味&nbsp;</th>
 *   </tr>
 *   <tr>
 *    <td>G</td>
 *    <td align="left">紀元<br>例：AD</td>
 *    <td align="left">和暦元号<br>
 *                     例：<code>HHHH</code> ⇒ 平成<br>
 *                         <code>HHH</code> ⇒ H</td>
 *   </tr>
 *   <tr>
 *    <td>y</td>
 *    <td align="left">年（西暦）<br>例：2002</td>
 *    <td align="left">年（和暦）<br>例：14</td>
 *   </tr>
 *   <tr>
 *    <td>E</td>
 *    <td align="left">曜日<br>例：Tuesday</td>
 *    <td align="left">曜日（日本語表記）<br><br>
 *                     例：<code>EEEE</code> ⇒ 水曜日<br>
 *                         <code>EEE</code> ⇒ 水</td>
 *   </tr>
 * </table>
 * </div>
 *
 * <p>
 *  上記の記号のうち、和暦元号、および和暦年については
 *  {@link jp.terasoluna.fw.util.DateUtil} クラスの
 *  <code>dateToWarekiString(String pattern, java.util.Date date)</code>
 *  メソッドによって変換される。和暦元号、および和暦年の設定方法や
 *  変換可能な年についての制限事項などは、
 *  {@link jp.terasoluna.fw.util.DateUtil} を参照。
 * </p>
 *
 * <p>
 *  また、曜日については <code>SimpleDateFotmat</code>
 *  のコンストラクタにおいてロケールを <code>&quot;ja&quot;</code>
 *  に指定することで変換される。ロケールや時刻パターン文字列の詳細については、
 *  <code>java.text.SimpleDateFormat</code>
 *  クラスのドキュメントを参照のこと。
 * </p>
 *
 * <br>
 *
 * <h5>タグがサポートする属性</h5>
 * <p> {@link DateFormatterTagBase} を参照。</p>
 *
 * <br>
 *
 * <h5>カスタムタグのスクリプティング変数</h5>
 * <p> {@link DateFormatterTagBase} を参照。</p>
 *
 * <br>
 *
 * <h5>使用方法</h5>
 * <p>
 *  <code>&quot;form0001&quot;</code> <code>bean</code>の
 *  <code>&quot;field001&quot;</code> プロパティの値を指定した形式にフォーマット
 *  して出力するには、以下のように記述する。
 * </p>
 *
 * <p><code><pre>
 *  &lt;t:jdate name="form0001"
 *              property="field001"
 *              pattern="GGGGyy年MM月dd日(EEEE) hh時mm分ss秒" /&gt;
 *
 * =&gt 平成14年07月26日(金曜日) 11時04分07秒
 *
 *  &lt;t:jdate name="form0001"
 *              property="field001"
 *              pattern="G. yy年MM月dd日(E) hh時mm分ss秒" /&gt;
 *
 * =&gt H. 14年07月26日(金) 11時04分07秒
 * </pre></code></p>
 *
 * <p>
 *  上記の beanのプロパティを、カスタムタグで出力せずにスクリプティング変数
 *  <code>&quot;formatted&quot;</code>
 *  へセットする場合には、以下のように記述する。
 * </p>
 *
 * <p><code><pre>
 *  &lt;t:jdate id="formatted"
 *              name="form0001"
 *              property="field001"
 *              pattern="GGGGyy年MM月dd日(EEEE) hh時mm分ss秒" /&gt;
 * </pre></code></p>
 *
 * <p>
 *  フォーマットする日付時刻データをbeanから取り出さずに、
 *  カスタムタグの <code>value</code>
 *  属性によって指定する場合には、以下のように記述する。
 * </p>
 *
 * <p><code><pre>
 *  &lt;t:jdate value="2002/07/25 04:56:23"
 *              pattern="GGGGyy年MM月dd日(EEEE) hh時mm分ss秒" /&gt;
 * </pre></code></p>
 *
 * @see jp.terasoluna.fw.util.DateUtil
 *
 */
public class JDateTag extends DateFormatterTagBase {

    /**
	 * シリアルバージョンID
	 */
	private static final long serialVersionUID = 4097026346025126637L;

	/**
     * <code>pattern</code> 属性で指定されたフォーマットにしたがって、
     * 指定された日付時刻データを和暦としてフォーマットする。
     *
     * @param date 日付時刻データ
     * @return 日付時刻データを和暦としてフォーマットした文字列
     */
    @Override
    protected String doFormat(Date date) {
        return DateUtil.dateToWarekiString(pattern, date);
    }

}
