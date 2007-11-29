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

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <p>
 *  <code>date</code>タグの実装クラス。
 * </p>
 * <p>
 *  <code>pattern</code> 属性によって指定された形式に従って
 *  日付・時刻をフォーマットする。
 *  <code>DateTag</code> クラスでは、<code>pattern</code> 属性で指定された
 *  出力形式の文字列を <code>java.text.SimpleDateFormat</code> クラスの
 *  <em>時刻パターン文字列</em>として解釈し、フォーマットする。
 *  時刻パターン文字列の詳細については、<code>java.text.SimpleDateFormat</code>
 *  クラスのドキュメントを参照のこと。
 * </p>
 *
 * <br>
 *
 * <h5>タグがサポートする属性</h5>
 * <p>{@link DateFormatterTagBase} を参照。</p>
 *
 * <br>
 *
 * <h5>カスタムタグのスクリプティング変数</h5>
 * <p>{@link DateFormatterTagBase} を参照。</p>
 *
 * <br>
 *
 * <h5>使用方法</h5>
 * <li>
 *  <code>&quot;form0001&quot;</code> beanの
 *  <code>&quot;field001&quot;</code> プロパティの値を指定した形式にフォーマット
 *  して出力する。
 * </li>
 * <p><code><pre>
 *  &lt;t:date name="form0001"
 *             property="field001"
 *             pattern="yyyyy.MMMMM.dd GGG hh:mm aaa" /&gt;
 * =&gt 1996.July.10 AD 12:08 PM
 * </pre></code></p>
 * <br>
 * <li>
 *  上記の beanのプロパティを、出力せずにスクリプティング変数
 *  <code>&quot;formatted&quot;</code>へセットする。
 * </li>
 * <p><code><pre>
 *  &lt;t:date id="formatted"
 *             name="form0001"
 *             property="field001"
 *             pattern="yyyyy.MMMMM.dd GGG hh:mm aaa" /&gt;
 * </pre></code></p>
 * <br>
 * <li>
 *  フォーマットする日付時刻データを <code>bean</code> から取り出さずに、
 *  タグの <code>value</code>属性によって指定する場合。
 * </li>
 * <p><code><pre>
 *  &lt;t:date value="2002/07/25 04:56:23"
 *             pattern="yyyyy.MMMMM.dd GGG hh:mm aaa" /&gt;
 * </pre></code></p>
 *
 * @see java.text.SimpleDateFormat
 * @see jp.terasoluna.fw.web.taglib.DateFormatterTagBase
 *
 */
public class DateTag extends DateFormatterTagBase {

    /**
	 * シリアルバージョンID
	 */
	private static final long serialVersionUID = -9087010541359988539L;

	/**
     * <code>pattern</code> 属性で指定された <code>SimpleDateFormat</code>
     * 形式のフォーマットに従って、指定された日付時刻データをフォーマットする。
     *
     * @param date 日付時刻データ
     * @return <code>pattern</code> 属性で指定された出力形式の文字列
     */
    @Override
    protected String doFormat(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }

}
