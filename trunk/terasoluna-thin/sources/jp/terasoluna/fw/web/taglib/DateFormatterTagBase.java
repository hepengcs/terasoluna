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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.terasoluna.fw.util.StringUtil;

/**
 * <p>
 *  日付時刻をフォーマットして出力、あるいはスクリプティング変数として
 *  定義するカスタムタグための抽象基底クラス。
 * </p>
 * <p>
 *  日付時刻のフォーマットを行うカスタムタグのクラスはこの基底クラスを
 *  継承して作成する。
 * </p>
 * <p>
 *  フォーマット対象となる日付時刻データとしては、<code>java.util.Date</code>
 *  型、あるいは <code>java.lang.String</code> 型をサポートする。ただし、
 *  <code>java.lang.String</code> 型のデータをフォーマットする際には、
 *  その文字列が <code>&quot;yyyy/MM/dd hh:mm:ss&quot;</code>
 *  の形式となっている必要がある。<br>
 * </p>
 * <p>
 *  {@link DateFormatterTagBase} クラスを継承したサブクラスでは、
 *  実際に文字列のフォーマットを行う抽象メソッド
 *  <code>doFormat(Date date)</code> を実装する。
 * </p>
 * <h5>タグがサポートする属性</h5>
 * <p><code>DateFormatterTagBase</code> では、以下の属性をサポートする。</p>
 * <div align="center">
 *  <table width="90%" border="1" bgcolor="#FFFFFF">
 *   <tr>
 *    <td> <b>属性名</b> </td>
 *    <td> <b>デフォルト値</b> </td>
 *    <td> <b>必須性</b> </td>
 *    <td> <b>実行時式</b> </td>
 *    <td> <b>概要</b> </td>
 *   </tr>
 *   <tr>
 *    <td> <code>id</code> </td>
 *    <td> - </td>
 *    <td> <code>false</code> </td>
 *    <td> <code>false</code> </td>
 *    <td align="left">フォーマットされた文字列をレスポンスへ出力せずに、
 *      スクリプティング変数にセットする際に指定する。
 *      フォーマットされた文字列をスクリプティング変数にセットする場合には、
 *      <code>filter</code> 属性の指定に関わらずHTML
 *      特殊文字はエスケープされない。
 *    </td>
 *   </tr>
 *   <tr>
 *    <td> <code>filter</code> </td>
 *    <td> <code>true</code> </td>
 *    <td> <code>false</code> </td>
 *    <td> <code>true</code> </td>
 *    <td align="left">フォーマットされた文字列を出力する際に、HTML特殊文字を
 *      エスケープするかどうかを指定する。ただし、<code>id</code> 属性が
 *      指定されていた場合には、無視される。
 *    </td>
 *   </tr>
 *   <tr>
 *    <td><code>ignore</code></td>
 *    <td><code>false</code></td>
 *    <td> <code>false</code> </td>
 *    <td> <code>true</code> </td>
 *    <td align="left"><code>name</code> 属性で指定した beanが
 *      見つからなかったときに無視するかどうかを指定する。<code>false</code>
 *      を指定すると、beanが見つからなかったときに <code>JspException</code>
 *      が投げられる。
 *    </td>
 *   </tr>
 *   <tr>
 *    <td><code>name</code></td>
 *    <td> - </td>
 *    <td> <code>false</code> </td>
 *    <td> <code>true</code> </td>
 *    <td align="left">フォーマット対象の文字列をプロパティに持つ
 *      beanの名前。<code>property</code> 属性が指定されて
 *      いなかったときには、<code>name</code> 属性で指定されたインスタンスが
 *      がフォーマットの対象となる。この場合は、
 *      そのインスタンス自身が <code>java.util.Date</code> 型であるか、あるいは
 *      <code>java.lang.String</code> 型（かつ&quot;yyyy/MM/dd hh:mm:ss&quot;
 *      の形式となっているもの）のどちらかである必要がある。<code>value</code>
 *      属性が指定されていた場合には、無視される。
 *    </td>
 *   </tr>
 *   <tr>
 *    <td><code>property</code></td>
 *    <td> - </td>
 *    <td> <code>false</code> </td>
 *    <td> <code>true</code> </td>
 *    <td align="left"><code>name</code> 属性で指定された bean
 *      においてアクセスされるプロパティの名前。<code>value</code> 属性が
 *      指定されていた場合には無視される。
 *    </td>
 *   </tr>
 *   <tr>
 *    <td><code>scope</code></td>
 *    <td> <code>false</code> </td>
 *    <td> <code>true</code> </td>
 *    <td>（<code>findAttribute()</code>メソッドの検索順序）</td>
 *    <td align="left"><code>name</code> 属性で指定された bean
 *      を検索する際のスコープ。
 *    </td>
 *   </tr>
 *   <tr>
 *    <td><code>value</code></td>
 *    <td> - </td>
 *    <td> <code>false</code> </td>
 *    <td> <code>true</code> </td>
 *    <td align="left">フォーマットする文字列。文字列は、
 *      &quot;yyyy/MM/dd hh:mm:ss&quot;の形式となっている必要がある。
 *      <code>value</code> 属性を指定した場合には、<code>name</code>
 *      属性、および <code>property</code>
 *      属性は無視される。
 *    </td>
 *   </tr>
 *   <tr>
 *    <td><code>pattern</code></td>
 *    <td> - </td>
 *    <td> <code>true</code> </td>
 *    <td> <code>true</code> </td>
 *    <td align="left">フォーマットする出力形式。<code>pattern</code>
 *      属性で指定した出力形式は、<code>DateFormatterTagBase</code>
 *      クラスのサブクラスで解釈される。詳細は、サブクラスのドキュメントを
 *      参照のこと。
 *    </td>
 *   </tr>
 * </table>
 * </div>
 * <br><br>
 * <h5>カスタムタグのスクリプティング変数</h5>
 * <p>
 *  <code>DateFormatterTagBase</code> では以下の変数をサポートする。
 * </p>
 * <div align="center">
 * <table width="90%" border="1">
 *  <tr>
 *   <td><b>変数名</b></td>
 *   <td><b>型</b></td>
 *   <td><b>有効範囲</b></td>
 *   <td><b>記述</b></td>
 *  </tr>
 *  <tr>
 *   <td>カスタムタグの <code>id</code> 属性で指定された名前</td>
 *   <td><code>String</code></td>
 *   <td>開始タグ以降</td>
 *   <td align="left">
 *    このカスタムタグで出力せず、スクリプティング変数へ
 *    設定する場合の変数名。
 *   </td>
 *  </tr>
 * </table>
 * </div>
 *
 * @see jp.terasoluna.fw.web.taglib.DateTag
 * @see jp.terasoluna.fw.web.taglib.JDateTag
 *
 */
public abstract class DateFormatterTagBase extends TagSupport {

    /**
     * ログクラス。
     */
    private static Log log = LogFactory.getLog(DateFormatterTagBase.class);
    
    /**
     * 日付時刻のフォーマット。
     * 文字列データを日付時刻として解釈する際に使用される。
     *
     */
    private static final String DATE_FORMAT = "yyyy/MM/dd hh:mm:ss";

    /**
     * そのページ内で利用できるようにするためのスクリプティング変数の名前。
     *
     */
    protected String id = null;

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
     * 出力中のHTML特殊文字をフィルターするかどうか。デフォルトは
     * <code>true</code>。
     *
     */
    protected boolean filter = true;

    /**
     * 出力中のHTML特殊文字をフィルターするかどうかを設定する。
     *
     * @param filter 出力中のHTML特殊文字をフィルターする場合は
     *               <code>true</code>
     */
    public void setFilter(boolean filter) {
        this.filter = filter;
    }

    /**
     * beanが見つからなかったときに、単に無視する（何も出力しない）かどうか。
     * 無視しない場合には、例外を投げる。デフォルトは <code>false</code>
     * （例外を投げる）。
     */
    protected boolean ignore = false;

    /**
     * beanが見つからなかった場合に無視するかどうかを設定する。
     *
     * @param ignore 無視する場合は <code>true</code>
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
     * 指定されたbeanにおいてアクセスされるプロパティ名を設定する。
     *
     * @param property プロパティ名
     */
    public void setProperty(String property) {
        this.property = property;
    }

    /**
     * 指定されたbeanを検索するスコープ。
     *
     */
    protected String scope = null;

    /**
     * 指定されたbeanを検索するスコープを設定する。
     *
     * @param scope スコープ
     */
    public void setScope(String scope) {
        this.scope = scope;
    }

    /**
     * 出力のフォーマットを指定するパターン。
     * <code>SimpleDateFormat</code> と同じ書式で指定する。
     *
     */
    protected String pattern = null;

    /**
     * 出力のフォーマットを指定するパターンを設定する。
     *
     * @param pattern パターン
     */
    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    /**
     * フォーマット対象の値（文字列）。
     *
     */
    protected String value = null;

    /**
     * フォーマット対象の値を設定する。
     *
     * @param value フォーマット対象の値
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * タグ評価開始時に呼ばれるメソッド。
     *
     * @return 処理制御指示。常に <code>SKIP_BODY</code>
     * @throws JspException JSP例外
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

        // プロパティの値がString型であれば一度Dateへ変換する
        Date date = null;
        if (value instanceof String) {
            // 右側トリムをする
            String trimed = StringUtil.rtrim((String) value);

            // 一度Date型に変換する
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
            try {
                date = sdf.parse(trimed);
            } catch (ParseException e) {
                log.error("Date parsing error.");
                
                throw new JspTagException(e.getMessage());
            }
        } else if (value instanceof Date) {
            date = (Date) value;
        } else {
            return SKIP_BODY;  // 何も出力しない
        }

        // フォーマットする
        String output = doFormat(date);

        if (id != null) {
            // idが指定されていたときには、スクリプティング変数として利用できる
            // ようにページスコープにセットする。
            pageContext.setAttribute(id, output);
        } else {
            // idが指定されていないときには、プロパティの値をライタにプリント
            // する。適切にフィルタする。
            if (filter) {
                TagUtil.write(pageContext, TagUtil.filter(output));
            } else {
                TagUtil.write(pageContext, output);
            }
        }

        return SKIP_BODY;
    }

    /**
     * すべてのアロケートされた資源を解放する。
     *
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
        pattern = null;
        value = null;
    }

    /**
     * 日付時刻のフォーマットを行う抽象メソッド。
     * サブクラスでオーバーライドする。
     *
     * @param date 日付時刻
     * @return フォーマットされた文字列
     */
    protected abstract String doFormat(Date date);

}
