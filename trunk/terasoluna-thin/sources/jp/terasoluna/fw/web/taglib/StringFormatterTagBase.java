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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>
 *  文字列をフォーマットして出力、あるいはスクリプティング変数として定義する
 *  カスタムタグための抽象基底クラス。
 *  </p>
 * <p>
 *  文字列の切り出し、トリム等のフォーマットを行うカスタムタグのクラスは
 *  この基底クラスを継承して作成する。(<code>TERASOLUNA</code> の提供する
 *  <code>fmtx</code> カスタムタグライブラリ等)
 * </p>
 * <p>
 *  このクラスによってフォーマットされた文字列が、レスポンスへ出力される
 *  のか、あるいはスクリプティング変数にセットされるのか動作の違いは、
 *  <code>id</code> 属性の有無で決定される。
 * </p>
 * <p>
 *  フォーマット対象となる文字列は、以下の優先順序で決定される。
 * </p>
 * <ol>
 *  <li><code>value</code> 属性が指定されている場合には、
 *      その指定された文字列をフォーマットする。
 *  <li><code>name</code> 属性で <code>bean</code>名が指定されている場合には、
 *      その指定された <code>bean</code> のインスタンスの、
 *      <code>property</code> 属性で指定されたプロパティをフォーマットする。
 * </ol>
 * <p>
 *  ただし、<code>name</code> 属性が指定されているときに、
 *  <code>property</code> 属性が指定されていない場合には、<code>name</code>
 *  属性で示されるインスタンスの値（<code>toString()</code> メソッドで
 *  返されるオブジェクトの文字列表現）が、フォーマットの対象となる。
 * </p>
 * <p>
 *  <code>name</code> 属性で指定された <code>bean</code> が見つからなかったときの
 *  カスタムタグの動作は、<code>ignore</code> 属性によって決定される。
 *  <code>ignore</code> 属性がtrueである場合には、
 *  <code>bean</code> が見つからなかったときには単に無視され、何も出力しない
 *  （<code>id</code> 属性が指定されている場合には、スクリプティング変数に
 *  セットされない）。
 * </p>
 * <p>
 *  <code>ignore</code> 属性がfalse（デフォルト）である場合には、
 *  <code>name</code> 属性が指定されたbeanが見つからなかったときには
 *  <code>JspException</code> が投げられる。
 * </p>
 * <p>
 *  <code>bean</code> の検索対象として、<code>scope</code>
 *  属性を指定することができる。
 *  <code>scope</code> 属性を指定しなかった場合には、
 *  <code>javax.servlet.jsp.PageContext</code> クラスの
 *  <code>findAttribute()</code> メソッドの検索順序で検索される。
 * </p>
 * <p>
 *  <code>id</code> 属性が指定された場合には、フォーマットされた文字列を出力
 *  せずに、<code>id</code> 属性で指定された変数名のスクリプティング変数にセット
 *  する。
 * </p>
 * <p>
 *  <code>filter</code> 属性が <code>true</code>（デフォルト）で
 *  あるときには、出力される際に、<code>HTML</code> 特殊文字（
 *  <code>org.apache.struts.util.ResponceUtils</code> クラスの
 *  <code>filter()</code> メソッドで処理されるもの。
 *  「&lt;」、「&gt;」、「&amp;」、「&quot;」の４文字)をエスケープする。
 *  スクリプティング変数にセットする場合には、<code>filter</code> 属性は無視
 *  され、フォーマットされた文字列がそのままスクリプティング変数にセットされ
 *  る。
 * </p>
 * <p>
 *  <code>replaceSpToNbsp</code>属性が<code>true</code>（デフォルト）で
 *  あるときには、出力される際に、1Byteコードのスペース（半角スペース）が
 *  存在する場合「&amp;nbsp;」に変換する。
 *  スクリプティング変数にセットする場合には、<code>replaceSpToNbsp</code> 属性は無視
 *  され、フォーマットされた文字列がそのままスクリプティング変数にセットされ
 *  る
 * </p>
 * <p>
 *  {@link StringFormatterTagBase} クラスを継承したサブクラスでは、
 *  実際に文字列のフォーマットを行う抽象メソッド <code>doFormat()</code>
 *  を実装する。
 * </p>
 *
 * <br>
 *
 * <h5>タグがサポートする属性</h5>
 * <p>
 *  <code>StringFormatterTagBase</code> では、以下の属性をサポートする。
 * </p>
 * <div align="center">
 *  <table width="90%" border="1">
 *   <tr>
 *    <td> <b>属性名</b> </td>
 *    <td> <b>デフォルト</b> </td>
 *    <td> <b>必須性</b> </td>
 *    <td> <b>実行時式</b> </td>
 *    <td> <b>記述</b> </td>
 *   </tr>
 *   <tr>
 *    <td> id </td>
 *    <td> - </td>
 *    <td> 任意 </td>
 *    <td> false </td>
 *    <td align="left">
 *     フォーマットした文字列を出力せずに、スクリプティング変数
 *     にセットする際に指定する。フォーマットされた文字列をスクリプティング
 *     変数にセットする場合には、<code>filter</code> 属性の指定に関わらずHTML
 *     特殊文字はエスケープされない。
 *    </td>
 *   </tr>
 *   <tr>
 *    <td> filter </td>
 *    <td>true</td>
 *    <td> - </td>
 *    <td> 任意 </td>
 *    <td align="left">
 *     フォーマットされた文字列を出力する際に、<code>HTML</code> 特殊文字を
 *     エスケープするかどうかを指定する。ただし、<code>id</code> 属性が
 *     指定されていた場合には、無視される。
 *    </td>
 *   </tr>
 *   <tr>
 *    <td> ignore </td>
 *    <td>false</td>
 *    <td> - </td>
 *    <td> 任意 </td>
 *    <td align="left">
 *     <code>name</code> 属性で指定した beanが
 *     見つからなかったときに無視するかどうかを指定する。<code>false</code> を
 *     指定すると、<code>bean</code> が見つからなかったときに
 *     <code>JspException</code>が投げられる。
 *    </td>
 *   </tr>
 *   <tr>
 *    <td> name </td>
 *    <td> - </td>
 *    <td> - </td>
 *    <td> 任意 </td>
 *    <td align="left">
 *     フォーマット対象の文字列をプロパティに持つ
 *     <code>bean</code> の名前。<code>property</code> 属性が指定されて
 *     いなかったときには、<code>name</code> 属性で指定されたインスタンスの
 *     文字列表現 <code>toString()</code> メソッドで返される文字列）
 *     がフォーマットの対象となる。<code>value</code>
 *     属性が指定されていた場合には、無視される。
 *    </td>
 *   </tr>
 *   <tr>
 *    <td> property </td>
 *    <td> - </td>
 *    <td> - </td>
 *    <td> 任意 </td>
 *    <td align="left"><code>name</code> 属性で指定された <code>bean</code>
 *     においてアクセスされるプロパティの名前。<code>value</code> 属性が
 *     指定されていた場合には無視される。
 *    </td>
 *   </tr>
 *   <tr>
 *    <td> scope </td>
 *    <td>（<code>findAttribute()</code>メソッドの検索順序）</td>
 *    <td> - </td>
 *    <td> 任意 </td>
 *    <td align="left"><code>name</code> 属性で指定された <code>bean</code>
 *     を検索する際のスコープ。
 *    </td>
 *   </tr>
 *   <tr>
 *    <td> value </td>
 *    <td>なし</td>
 *    <td> - </td>
 *    <td> 任意 </td>
 *    <td align="left">フォーマットする文字列。<code>value</code> 属性を
 *     指定した場合には、<code>name</code> 属性、および <code>property</code>
 *     属性は無視される。
 *    </td>
 *   </tr>
 *   <tr>
 *    <td> <code>replaceSpToNbsp</code> </td>
 *    <td> - </td>
 *    <td> <code>false</code> </td>
 *    <td> <code>true</code> </td>
 *    <td align="left">
 *     この属性がtrueにセットされ、
 *     指定したbeanプロパティの値に1Byteコードのスペースが存在する場合
 *     <code>&amp;nbsp;</code>に置換する。
 *     無効にするためには、この属性に明示的に false をセットする必要がある。
 *     ただし、<code>id</code> 属性が指定されていた場合には、無視される。
 *     </td>
 *   </tr>
 *  </table>
 * </div>
 *
 * <br>
 *
 * <h5>カスタムタグのスクリプティング変数</h5>
 * <p>
 *  <code>StringFormatterTagBase</code> では以下の変数をサポートする。
 * </p>
 * <div align="center">
 * <table width="90%" border="1">
 *  <tr>
 *   <td> <b>スクリプティング変数名</b> </td>
 *   <td> <b>型</b> </td>
 *   <td> <b>変数の利用可能範囲</b> </td>
 *   <td> <b>記述</b> </td>
 *  </tr>
 *  <tr>
 *   <td>カスタムタグの<code>id</code>属性で指定された名前</td>
 *   <td><code>java.lang.String</code></td>
 *   <td>開始タグ以降</td>
 *   <td align="left">
 *    このカスタムタグで出力せず、スクリプティング変数へ 設定する場合の変数名。
 *   </td>
 *  </tr>
 * </table>
 * </div>
 *
 * @see jp.terasoluna.fw.web.taglib.LeftTag
 * @see jp.terasoluna.fw.web.taglib.RTrimTag
 * @see jp.terasoluna.fw.web.taglib.LTrimTag
 * @see jp.terasoluna.fw.web.taglib.TrimTag
 *
 */
public abstract class StringFormatterTagBase extends TagSupport {

    /**
     * ログクラス
     */
    private static Log log = LogFactory.getLog(StringFormatterTagBase.class);
    
    /**
     * そのページ内で利用できるようにするためのスクリプティング変数の名前。
     *
     */
    protected String id = null;

    /**
     * スクリプティング変数の名前を取得する。
     *
     * @return スクリプティング変数名
     */
    @Override
    public String getId() {
        return this.id;
    }

    /**
     * スクリプティング変数の名前を設定する。
     *
     * @param id スクリプティング変数名
     */
    @Override
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 出力中のHTML特殊文字をフィルターするかどうか。デフォルトはtrue。
     *
     */
    protected boolean filter = true;

    /**
     * 出力中のHTML特殊文字をフィルターするかどうかを取得する。
     *
     * @return 出力中のHTML特殊文字をフィルターする場合はtrue
     */
    public boolean getFilter() {
        return this.filter;
    }

    /**
     * 出力中のHTML特殊文字をフィルターするかどうかを設定する。
     *
     * @param filter 出力中のHTML特殊文字をフィルターする場合はtrue
     */
    public void setFilter(boolean filter) {
        this.filter = filter;
    }

    /**
     * beanが見つからなかったときに、単に無視する（何も出力しない）かどうか。
     * 無視しない場合には、例外を投げる。デフォルトはfalse（例外を投げる）。
     */
    protected boolean ignore = false;

    /**
     * beanが見つからなかった場合に無視するかどうかを取得する。
     *
     * @return 無視する場合はtrue
     */
    public boolean getIgnore() {
        return this.ignore;
    }

    /**
     * beanが見つからなかった場合に無視するかどうかを設定する。
     *
     * @param ignore 無視する場合はtrue
     */
    public void setIgnore(boolean ignore) {
        this.ignore = ignore;
    }

    /**
     * フォーマット対象のデータを含むbeanの名前。
     *
     */
    protected String name = null;

    /**
     * フォーマット対象のデータを含むbeanの名前を取得する。
     *
     * @return beanの名前
     */
    public String getName() {
        return this.name;
    }

    /**
     * フォーマット対象のデータを含むbeanの名前を設定する。
     *
     * @param name beanの名前
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 指定されたbeanにおいてアクセスされるプロパティの名前。
     *
     */
    protected String property = null;

    /**
     * 指定されたbeanにおいてアクセスされるプロパティ名を取得する。
     *
     * @return プロパティ名
     */
    public String getProperty() {
        return this.property;
    }

    /**
     * 指定されたbeanにおいてアクセスされるプロパティ名を設定する。
     *
     * @param property プロパティ名
     */
    public void setProperty(String property) {
        this.property = property;
    }

    /**
     * 指定されたbeanを検索するスコープ
     *
     */
    protected String scope = null;

    /**
     * 指定されたbeanを検索するスコープを取得する。
     *
     * @return スコープ
     */
    public String getScope() {
        return this.scope;
    }

    /**
     * 指定されたbeanを検索するスコープを設定する。
     *
     * @param scope スコープ
     */
    public void setScope(String scope) {
        this.scope = scope;
    }

    /**
     * フォーマット対象の値（文字列）
     *
     */
    protected String value = null;

    /**
     * フォーマット対象の値を取得する。
     *
     * @return フォーマット対象の値
     */
    public String getValue() {
        return this.value;
    }

    /**
     * フォーマット対象の値を設定する。
     *
     * @param value フォーマット対象の値
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * <p>
     *  半角スペースを <code>&amp;nbsp;</code> と置換する。
     * </p>
     */
    protected boolean replaceSpToNbsp = true;

    /**
     * <p>
     *  <code>replaceSpToNbsp</code> の <code>get</code> メソッド
     * </p>
     *
     * @return replaceSpToNbsp
     *   半角スペースから <code>&amp;nbsp;</code> 変換フラグ
     */
    public boolean getReplaceSpToNbsp() {
        return this.replaceSpToNbsp;
    }

    /**
     * <p>
     *  <code>replaceSpToNbsp</code> の <code>set</code> メソッド
     * </p>
     *
     * @param replaceSpToNbsp
     *   半角スペースから <code>&amp;nbsp;</code> 変換フラグ
     */
    public void setReplaceSpToNbsp(boolean replaceSpToNbsp) {
        this.replaceSpToNbsp = replaceSpToNbsp;
    }

    /**
     * タグ評価開始時に呼ばれるメソッド。
     *
     * @return 処理制御指示
     * @throws JspException タグ評価時にエラー
     */
    @Override
    public int doStartTag() throws JspException {

        Object value = this.value;
        if (value == null) {
            // beanがなくても無視する場合には、要求されたbeanをルックアップし
            // 見つからなった場合には、リターンする
            if (ignore) {
                if (TagUtil.lookup(pageContext, name, scope) == null) {
                    return SKIP_BODY;  // 何も出力しない
                }
            }

            // 要求されたプロパティの値をルックアップする
            value = TagUtil.lookup(pageContext, name, property, scope);
            if (value == null) {
                return SKIP_BODY;  // 何も出力しない
            }
        }

        // フォーマットする
        String output = doFormat(value.toString());

        if (id != null) {
            // idが指定されていたときには、スクリプティング変数として利用できる
            // ようにページスコープにセットする。
            pageContext.setAttribute(id, output);
        } else {
            // idが指定されていないときには、プロパティの値をライタにプリント
            // する。適切にフィルタする。
            if (filter) {
                output = TagUtil.filter(output);
            }

            // 半角スペースを&nbsp;に置換
            StringReader sr = null;
            BufferedReader br = null;
            String line = null;
            try {
                sr = new StringReader(output);
                br = new BufferedReader(sr);
                StringBuilder sbuf = new StringBuilder();
                StringBuilder strBuf = new StringBuilder();
                int index = 0;
                while ((line = br.readLine()) != null) {
                     // 半角スペースを&nbsp;に置換
                    if (replaceSpToNbsp && !"".equals(line)) {
                        strBuf.setLength(0);
                        char ch = line.charAt(0);
                        int i = 0;
                        for (i = 0; i < line.length(); i++) {
                            ch = line.charAt(i);
                            // 半角スペースである場合、"&nbsp;"に変換します。
                            if (ch == ' ') {
                                strBuf.append("&nbsp;");
                            } else {
                                strBuf.append(ch);
                            }
                        }
                        line = strBuf.toString();
                    }
                    sbuf.append(line);
                    ++index;
                }
                output = sbuf.toString();
            } catch (IOException e) {
                if (log.isWarnEnabled()) {
                    log.warn("StringReader IO error : " + e);
                }
            } finally {
                if (sr != null) {
                    sr.close();
                }
                try {
                    if (br != null) {
                        br.close();
                    }
                } catch (IOException e1) {
                    if (log.isWarnEnabled()) {
                        log.warn("StringReader close error : " + e1);
                    }
                }
            }

            TagUtil.write(pageContext, output);
        }

        return SKIP_BODY;
    }

    /**
     * すべてのアロケートされた資源を解放する
     */
    @Override
    public void release() {
        super.release();
        id = null;
        filter = true;
        ignore = false;
        name = null;
        property = null;
        scope = null;
        value = null;
        replaceSpToNbsp = true;
    }

    /**
     * 文字列のフォーマットを行う抽象メソッド。
     * サブクラスでオーバーライドする。
     *
     * @param s フォーマット対象文字列
     * @return フォーマットされた文字列
     */
    protected abstract String doFormat(String s);

}
