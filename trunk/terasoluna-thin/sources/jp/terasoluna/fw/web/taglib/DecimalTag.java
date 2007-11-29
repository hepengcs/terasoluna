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

import java.math.BigDecimal;
import java.text.DecimalFormat;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.terasoluna.fw.util.StringUtil;

/**
 * <p><code>decimal</code>タグの実装クラス。</p>
 *
 * <p>
 *  符号、および小数点付き数値をフォーマットして出力、
 *  あるいはスクリプティング変数として定義する。<br>
 * </p>
 * <p>
 *  フォーマット対象となる数値データは、<code>java.math.BigDecimal</code>
 *  型、あるいは <code>java.lang.String</code> 型をサポートする。
 *  <code>java.lang.String</code> 型の場合、<code>BigDecimal</code>
 *  のコンストラクタによって解釈可能な文字列となっている必要がある。
 * </p>
 *
 * <p>
 *  <code>BigDecimal</code> のコンストラクタで解釈不能であった場合は、
 *  <code>NumberFormatException</code> がスローされる。
 * </p>
 *
 * <br>
 *
 * <h5>タグがサポートする属性</h5>
 * <p><code>DecimalTag</code> では、以下の属性をサポートする。</p>
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
 *    <td><code>id</code></td>
 *    <td>なし</td>
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
 *    <td><code>filter</code></td>
 *    <td><code>true</code></td>
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
 *      見つからなかったときに無視するかどうかを指定する。<code>false</code> を
 *      指定すると、beanが見つからなかったときに <code>JspException</code>
 *      が投げられる。
 *    </td>
 *   </tr>
 *   <tr>
 *    <td><code>name</code></td>
 *    <td>なし</td>
 *    <td> <code>false</code> </td>
 *    <td> <code>true</code> </td>
 *    <td align="left">フォーマット対象の文字列をプロパティに持つbeanの名前。
 *      <code>property</code> 属性が指定されていなかったときには、
 *      <code>name</code> 属性で指定されたインスタンス
 *      がフォーマットの対象となる。この場合は、そのインスタンス自身が
 *      <code>java.math.BigDecimal</code> 型であるか、あるいは
 *      <code>java.lang.String</code> 型（かつ右側の空白除去後に
 *      <code>BigDecimal</code> のコンストラクタによって解釈可能であるもの）
 *      のどちらかである必要がある。<code>value</code>
 *      属性が指定されていた場合には、無視される。
 *    </td>
 *   </tr>
 *   <tr>
 *    <td><code>property</code></td>
 *    <td>なし</td>
 *    <td> <code>false</code> </td>
 *    <td> <code>true</code> </td>
 *    <td align="left"><code>name</code> 属性で指定された bean
 *      においてアクセスされるプロパティの名前。<code>value</code> 属性が
 *      指定されていた場合には無視される。
 *    </td>
 *   </tr>
 *   <tr>
 *    <td><code>scope</code></td>
 *    <td>（<code>findAttribute()</code> メソッドの検索順序）</td>
 *    <td> <code>false</code> </td>
 *    <td> <code>true</code> </td>
 *    <td align="left"><code>name</code> 属性で指定されたbean
 *      を検索する際のスコープ。
 *    </td>
 *   </tr>
 *   <tr>
 *    <td><code>value</code></td>
 *    <td>なし</td>
 *    <td> <code>false</code> </td>
 *    <td> <code>true</code> </td>
 *    <td align="left">フォーマットする文字列。文字列は、右側の空白除去後に
 *      <code>BigDecimal</code>
 *      のコンストラクタによって解釈可能である必要がある。<code>value</code>
 *      属性を指定した場合には、<code>name</code>
 *      属性、および <code>property</code> 属性は無視される。
 *    </td>
 *   </tr>
 *   <tr>
 *    <td><code>pattern</code></td>
 *    <td>なし</td>
 *    <td> <code>true</code> </td>
 *    <td> <code>true</code> </td>
 *    <td align="left">フォーマットする出力形式。<code>pattern</code>
 *      属性で指定した出力形式は、<code>DecimalFormat</code>
 *      クラスのパターンとして解釈される。詳細は、<code>DecimalFormat</code>
 *      クラスのドキュメントを参照のこと。
 *    </td>
 *   </tr>
 *   <tr>
 *    <td><code>scale</code></td>
 *    <td>なし</td>
 *    <td> <code>false</code> </td>
 *    <td> <code>true</code> </td>
 *    <td align="left">丸め動作後の小数点以下桁数。<code>n</code>
 *      を指定した場合には、小数第 <code>n + 1</code> 位が丸められる。
 *      丸めモードは<code>round</code>属性で指定する。<code>round</code>
 *      属性が指定されていない場合は、四捨五入が行われる。
 *    </td>
 *   </tr>
 *   <tr>
 *    <td><code>round</code></td>
 *    <td>なし</td>
 *    <td> <code>false</code> </td>
 *    <td> <code>true</code> </td>
 *    <td align="left">丸めモード。<code>scale</code>属性が
 *      指定されている時、有効になる。<code>ROUND_HALF_UP</code>（四捨五入）、
 *      <code>ROUND_FLOOR</code>（切り捨て）、<code>ROUND_CEILING</code>
 *     （切り上げ）が 設定可能である。デフォルトは<code>ROUND_HALF_UP
 *      </code>が実行される。これら３つの設定以外を指定した場合は、
 *      <code>IllegalArgumentException</code>がスローされる。
 *    </td>
 *   </tr>
 *  </table>
 * </div>
 * <h5>カスタムタグのスクリプティング変数</h5>
 * <p>
 *  <code>DecimalTag</code> では以下の変数をサポートする。
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
 *   <td align="left">このカスタムタグで出力せず、スクリプティング変数へ
 *    設定する場合の変数名。
 *   </td>
 *  </tr>
 * </table>
 * </div>
 */
public class DecimalTag extends TagSupport {

    /**
	 * シリアルバージョンID
	 */
	private static final long serialVersionUID = -2317420257734211793L;

	/**
     * ログクラス。
     */
    private static Log log = LogFactory.getLog(DecimalTag.class);
    
    /**
     * そのページ内で利用できるようにするためのスクリプティング変数の名前。
     *
     */
    protected String id = null;

    /**
     * スクリプティング変数の名前を取得する。
     *
     * @return スクリプティング変数
     */
    @Override
    public String getId() {
        return this.id;
    }

    /**
     * スクリプティング変数の名前を設定する。
     *
     * @param id スクリプティング変数の名前
     */
    @Override
    public void setId(String id) {
        this.id = id;
    }

    /**
     * HTML特殊文字を出力する際にフィルターするかどうか。デフォルトは
     * <code>true</code>。
     */
    protected boolean filter = true;

    /**
     * 出力中のHTML特殊文字をフィルターするかどうかを取得する。
     *
     * @return 出力中のHTML特殊文字をフィルターする場合は <code>true</code>
     */
    public boolean getFilter() {
        return this.filter;
    }

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
     * beanが見つからなかった場合に無視するかどうかを取得する。
     *
     * @return 無視する場合は <code>true</code>
     */
    public boolean getIgnore() {
        return this.ignore;
    }

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
     * 指定されたbeanにおいてアクセスされるプロパティの名前を取得する。
     *
     * @return 指定されたbeanにおいてアクセスされるプロパティの名前
     */
    public String getProperty() {
        return this.property;
    }

    /**
     * 指定されたbeanにおいてアクセスされるプロパティの名前を設定する。
     *
     * @param property 指定されたbeanにおいてアクセスされるプロパティの名前
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
     * @param scope 指定されたbeanを検索するスコープ
     */
    public void setScope(String scope) {
        this.scope = scope;
    }

    /**
     * 出力のフォーマットを指定するパターン。
     * <code>DecimalFormat</code> と同じ書式で指定する。
     *
     */
    protected String pattern = null;

    /**
     * 出力のフォーマットを指定するパターンを取得する。
     *
     * @return 出力のフォーマットを指定するパターン
     */
    public String getPattern() {
        return this.pattern;
    }

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
     * 四捨五入後の小数点以下の桁数。<code>n</code> を指定した場合には、
     * 小数第 <code>n + 1</code> 位が四捨五入される。
     *
     */
    protected int scale = -1;

    /**
     * 四捨五入後の小数点以下の桁数を取得する。
     *
     * @return 小数点以下の桁数
     */
    public int getScale() {
        return this.scale;
    }

    /**
     * 四捨五入後の小数点以下の桁数を設定する。
     *
     * @param scale 小数点以下の桁数
     */
    public void setScale(int scale) {
        this.scale = scale;
    }

    /**
     * 丸めモード。
     *
     */
    protected String round = null;

    /**
     * 丸めモードを取得する。
     *
     * @return 丸めモード
     */
    public String getRound() {
        return this.round;
    }

    /**
     * 丸めモードを設定する。
     *
     * @param round 丸めモード
     */
    public void setRound(String round) {
        this.round = round;
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

        // プロパティの値がString型であれば一度BigDecimalへ変換する
        BigDecimal bd = null;
        if (value instanceof String) {
            String trimed = StringUtil.rtrim((String) value);
            if ("".equals(trimed)) {
                return SKIP_BODY;  //  何も出力しない
            }
			bd = new BigDecimal(trimed);
        } else if (value instanceof BigDecimal) {
            bd = (BigDecimal) value;
        } else {
            return SKIP_BODY;  // 何も出力しない
        }

        // 小数点以下桁数が指定されていた場合
        if (scale >= 0) {
            // round属性に設定された丸めモードを実行する（設定が無い場合は四捨五入）
            if (round != null) {
                if ("ROUND_FLOOR".equalsIgnoreCase(round)) {
                    bd = bd.setScale(scale, BigDecimal.ROUND_FLOOR);
                } else if ("ROUND_CEILING".equalsIgnoreCase(round)) {
                    bd = bd.setScale(scale, BigDecimal.ROUND_CEILING);
                } else if ("ROUND_HALF_UP".equalsIgnoreCase(round)) {
                    bd = bd.setScale(scale, BigDecimal.ROUND_HALF_UP);
                } else {
                    log.error("Please set a rounding mode");
                    throw new IllegalArgumentException(
                            "Please set a rounding mode");
                }
            } else {
                bd = bd.setScale(scale, BigDecimal.ROUND_HALF_UP);
            }
        }

        // フォーマットする
        DecimalFormat df = new DecimalFormat(pattern);
        String output = df.format(bd);

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
        scale = -1;
    }

}
