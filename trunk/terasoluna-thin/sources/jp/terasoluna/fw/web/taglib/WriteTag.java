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
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>
 *  指定した bean プロパティの値を変換し<code>JspWriter</code>として
 *  表現する<code>write</code>タグの実装クラス。
 * </p>
 * <p>
 *  指定した<code>bean</code>プロパティの値を取り出し、
 *  <code>String</code>として現在の <code>JspWriter</code> に与える。
 *  プロパティ値のクラス用に構成されている<code>PropertyEditor</code>
 *  がある場合、 <code>getAsText()</code> メソッドが呼ばれる。
 *  それ以外の場合は、通常 <code>toString()</code> での変換が適用される。
 *  また、属性により、以下のように付加変換を行う。
 * </p>
 * <p>
 *  <li>nullもしくは空文字を <code>&quot;&amp;nbsp;&quot;</code> と置換</li>
 *  <li>半角スペースを <code>&quot;&amp;nbsp;&quot;</code> と置換</li>
 *  <li>改行コードを <code>&lt;br&gt;</code> と置換</li>
 *  <li>改行文字を無視</li>
 * </p>
 *
 * <br>
 *
 * <h5>タグがサポートする属性</h5>
 * <li><code>write</code> タグでは、以下の属性をサポートする。</li>
 * <br><br>
 * <div align="center">
 * <table width="90%" border="1">
 *   <tr>
 *    <td> <b>属性名</b> </td>
 *    <td> <b>デフォルト値</b> </td>
 *    <td> <b>必須性</b> </td>
 *    <td> <b>実行時式</b> </td>
 *    <td> <b>概要</b> </td>
 *   </tr>
 *   <tr>
 *    <td> <code>filter</code> </td>
 *    <td> - </td>
 *    <td> <code>false</code> </td>
 *    <td> <code>true</code> </td>
 *    <td align="left">
 *      この属性がtrueにセットされる場合、
 *      表現されたプロパティ値は HTML内でセンシティブな文字のために
 *      フィルターされる。 そしてこのような全ての文字は、
 *      等価な文字で置き換えられる。
 *      デフォルトでは、フィルタリングが行われる。
 *      無効にするためには、この属性に明示的に false をセットする必要がある。
 *    </td>
 *   </tr>
 *   <tr>
 *    <td> <code>replaceNullToNbsp</code> </td>
 *    <td> - </td>
 *    <td> <code>false</code> </td>
 *    <td> <code>true</code> </td>
 *    <td align="left">
 *      この属性がtrueにセットされ、
 *      指定したbeanプロパティの値が空文字及び、nullの場合
 *      <code>&amp;nbsp;</code>を出力する。
 *      無効にするためには、この属性に明示的に false をセットする必要がある。
 *    </td>
 *   </tr>
 *   <tr>
 *    <td> <code>replaceSpToNbsp</code> </td>
 *    <td> - </td>
 *    <td> <code>false</code> </td>
 *    <td> <code>true</code> </td>
 *    <td align="left">
 *      この属性がtrueにセットされ、
 *      指定したbeanプロパティの値に1Byteコードのスペースが存在する場合
 *      <code>&amp;nbsp;</code>に置換する。
 *      無効にするためには、この属性に明示的に false をセットする必要がある。
 *    </td>
 *   </tr>
 *   <tr>
 *    <td> <code>replaceLFtoBR</code> </td>
 *    <td> - </td>
 *    <td> <code>false</code> </td>
 *    <td> <code>true</code> </td>
 *    <td align="left">
 *      この属性がtrueにセットされる場合、
 *      指定したbeanプロパティの値の改行コードもしくは復帰文字が
 *      <code>&lt;br&gt;</code>に置換される。
 *      無効にするためには、この属性に明示的に false をセットする必要がある。
 *    </td>
 *   </tr>
 *   <tr>
 *    <td> <code>ignore</code> </td>
 *    <td> - </td>
 *    <td> <code>false</code> </td>
 *    <td> <code>true</code> </td>
 *    <td align="left">
 *      この属性がtrueにセットされ、
 *      name と scope属性で指定した bean が存在しない場合、
 *      なにもせずにリターンする。
 *      デフォルト値は false (このタグ ライブラリの中のほかのタグと
 *      矛盾しないように実行時例外がスローされる)。
 *    </td>
 *   </tr>
 *   <tr>
 *    <td> <code>name</code> </td>
 *    <td> - </td>
 *    <td> <code>true</code> </td>
 *    <td> <code>true</code> </td>
 *    <td align="left">
 *      property (指定がある場合) によって指定した値を
 *      取り出すために、プロパティがアクセスされる bean の属性名を指定する。
 *      property が指定されない場合、この bean 自身の値が表現される。
 *    </td>
 *   </tr>
 *   <tr>
 *    <td> <code>property</code> </td>
 *    <td> - </td>
 *    <td> <code>false</code> </td>
 *    <td> <code>true</code> </td>
 *    <td align="left">
 *      name によって指定した bean 上でアクセスされる
 *      プロパティの名前を指定する。 この値はシンプル、インデックス付き、
 *      またはネストされたプロパティ参照式になる。
 *      指定されない場合は、name によって識別された
 *      bean は それ自身を表現する。
 *      指定したプロパティがヌルを戻す場合、何も表現されない。
 *    </td>
 *   </tr>
 *   <tr>
 *    <td> <code>scope</code> </td>
 *    <td> - </td>
 *    <td> <code>false</code> </td>
 *    <td> <code>true</code> </td>
 *    <td align="left">
 *      name によって指定した bean を取り出すために検索された
 *      可変スコープを指定する。 指定されない場合、PageContext.findAttribute()
 *      によって適用されたデフォルトのルールが適用される。
 *    </td>
 *   </tr>
 *   <tr>
 *    <td> <code>fillColumn</code> </td>
 *    <td> - </td>
 *    <td> <code>false</code> </td>
 *    <td> <code>true</code> </td>
 *    <td align="left">
 *      fillColumnによって指定された文字数で区切り、
 *      区切った終端に&lt;br&gt;を付与する。 文字数の数え方は半角でも、全角でも
 *      １つの文字とみなす。
 *    </td>
 *   </tr>
 *   <tr>
 *    <td> <code>addBR</code> </td>
 *    <td> - </td>
 *    <td> <code>false</code> </td>
 *    <td> <code>true</code> </td>
 *    <td align="left">
 *      この属性がtrueにセットされる場合、プロパティ値の末尾に&lt;br&gt;を付与する。
 *      デフォルトはfalse。
 *    </td>
 *   </tr>
 *  </table>
 * </div>
 * <br>
 * <li>このタグによって設定されるスクリプティング変数はありません。</li>
 * <br>
 * <h5>使用方法</h5>
 * <p><code><pre>
 * &lt;logic:iterate id=&quot;form&quot;
 *     property="myMap" indexId=&quot;index&quot; &gt;
 *     &lt;t:write name=&quot;form&quot; property=&quot;value&quot; /&gt;
 * &lt;/logic:iterate&gt;
 * </pre></code></p>
 *
 */
public class WriteTag extends TagSupport {

    /**
     * シリアルバージョンID
     */
    private static final long serialVersionUID = -6953813130272994790L;

    /**
     * ログクラス
     */
    private static Log log = LogFactory.getLog(WriteTag.class);

    /**
     * <p>
     *  特殊文字を <code>HTML</code> に対応した文字に置き換える。
     * </p>
     */
    protected boolean filter = true;

    /**
     * <p>
     *  <code>filter</code>の<code>get</code>メソッド
     * </p>
     * @return filter filter
     */
    public boolean getFilter() {
        return this.filter;
    }

    /**
     * <p>
     *  <code>filter</code>の<code>set</code>メソッド
     * </p>
     *
     * @param filter filter属性値
     */
    public void setFilter(boolean filter) {
        this.filter = filter;
    }

    /**
     * <p>
     *  <code>null</code>もしくは空文字を&nbsp;と置換する。
     * </p>
     */
    protected boolean replaceNullToNbsp = true;

    /**
     * <p>
     *  <code>replaceNullToNbsp</code>の<code>get</code>メソッド
     * </p>
     *
     * @return replaceNullToNbsp
     *   <code>null</code> から <code>&amp;nbsp;</code> 変換フラグ
     */
    public boolean getReplaceNullToNbsp() {
        return this.replaceNullToNbsp;
    }

    /**
     * <p>
     *  <code>replaceNullToNbsp</code> の <code>set</code> メソッド
     * </p>
     *
     * @param replaceNullToNbsp
     *   <code>null</code> から <code>&amp;nbsp;</code> 変換フラグ
     */
    public void setReplaceNullToNbsp(boolean replaceNullToNbsp) {
        this.replaceNullToNbsp = replaceNullToNbsp;
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
     * <p>
     *  改行コードを<code>&lt;br&gt;</code>と置換する。
     * </p>
     */
    protected boolean replaceLFtoBR = true;

    /**
     * <p>
     *  <code>replaceLFtoBR</code> の <code>get</code> メソッド
     * </p>
     *
     * @return replaceLFtoBR
     *   改行コードから <code>&lt;br&gt;</code> 変換フラグ
     */
    public boolean getReplaceLFtoBR() {
        return this.replaceLFtoBR;
    }

    /**
     * <p>
     *  <code>replaceLFtoBR</code> の <code>set</code> メソッド
     * </p>
     *
     * @param replaceLFtoBR
     *   改行コードから <code>&lt;br&gt;</code> 変換フラグ
     */
    public void setReplaceLFtoBR(boolean replaceLFtoBR) {
        this.replaceLFtoBR = replaceLFtoBR;
    }

    /**
     * <p>
     *  <code>name</code> と <code>scope</code> 属性で指定された
     *  <code>Bean</code> が存在しない場合、何もしない
     * </p>
     */
    protected boolean ignore = false;

    /**
     * <p>
     *  <code>ignore</code> の <code>get</code> メソッド
     * </p>
     *
     * @return ignore ignore属性値
     */
    public boolean getIgnore() {
        return this.ignore;
    }

    /**
     * <p>
     *  <code>ignore</code> の <code>set</code> メソッド
     * </p>
     *
     * @param ignore ignore属性値
     */
    public void setIgnore(boolean ignore) {
        this.ignore = ignore;
    }

    /**
     * <p>
     *  <code>property</code> で指定した値を取り出す為の <code>Bean</code> 名
     * </p>
     */
    protected String name = null;

    /**
     * <p>
     *  <code>name</code> の <code>get</code> メソッド
     * </p>
     *
     * @return name
     */
    public String getName() {
        return this.name;
    }

    /**
     * <p>
     *  <code>name</code> の <code>set</code> メソッド
     * </p>
     *
     * @param name name属性値
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * <p>
     *  <code>name</code> によって指定された <code>Bean</code> 上で
     *  アクセスされるプロパティ名
     * </p>
     */
    protected String property = null;

    /**
     * <p>
     *  <code>property</code> の <code>get</code> メソッド
     * </p>
     *
     * @return property
     */
    public String getProperty() {
        return this.property;
    }

    /**
     * <p>
     *  <code>property</code> の <code>set</code> メソッド
     * </p>
     *
     * @param property property属性値
     */
    public void setProperty(String property) {
        this.property = property;
    }

    /**
     * <p>
     *  <code>scope</code> の <code>get</code> メソッド
     * </p>
     *
     * @return scope
     */
    public String getScope() {
        return this.scope;
    }

    /**
     * <p>
     *  <code>name</code> によって指定した <code>bean</code>
     * を取り出す為に検索するスコープ名
     * </p>
     */
    protected String scope = null;

    /**
     * <p>
     *  <code>scope</code> の <code>set</code> メソッド
     * </p>
     *
     * @param scope scope属性値
     */
    public void setScope(String scope) {
        this.scope = scope;
    }

    /**
     * <p>
     *  <code>fillColumn</code> によって指定された <code>Bean</code>
     *  上でアクセスされるプロパティ名
     * </p>
     */
    protected int fillColumn = -1;

    /**
     * <p>
     *  <code>fillColumn</code> の <code>get</code> メソッド
     * </p>
     *
     * @return fillColumn
     */
    public int getFillColumn() {
        return this.fillColumn;
    }

    /**
     * <p>
     *  <code>fillColumn</code> の <code>set</code> メソッド
     * </p>
     *
     * @param fillColumn fillColumn属性値
     */
    public void setFillColumn(int fillColumn) {
        this.fillColumn = fillColumn;
    }

    /**
     * <p>
     *  プロパティ値の末尾に&lt;br&gt;を加える。
     * </p>
     */
    protected boolean addBR = false;

    /**
     * <p>
     *  <code>addBR</code> の <code>get</code> メソッド
     * </p>
     *
     * @return addBR addBR属性値
     */
    public boolean getAddBR() {
        return this.addBR;
    }

    /**
     * <p>
     *  <code>addBR</code> の <code>set</code> メソッド
     * </p>
     *
     * @param addBR addBR属性値
     */
    public void setAddBR(boolean addBR) {
        this.addBR = addBR;
    }

    /**
     * <p>開始タグの処理を行います。</p>
     *
     * @return int 処理制御指示
     * @exception JspException JSP例外が発生した場合
     */
    @Override
    public int doStartTag() throws JspException {

        //要求されたbeanを検索する
        if (ignore && TagUtil.lookup(pageContext, name, scope) == null) {
                return SKIP_BODY;
        }

        //要求されたプロパティを検索する
        Object value = TagUtil.lookup(pageContext, name, property, scope);
        if (value == null) {
            if (replaceNullToNbsp) {
                TagUtil.write(pageContext, "&nbsp;");
            }
            return SKIP_BODY;
        }

        //プロパティ値を表示する
        String output = value.toString();
        if (filter) {
            output = TagUtil.filter(output);
        }
        StringReader   sr = null;
        BufferedReader br = null;
        try {
            sr = new StringReader(output);
            br = new BufferedReader(sr);
            StringBuilder sbuilder = new StringBuilder();
            StringBuilder strBuilder = new StringBuilder();
            String line = null;
            int sizeMngCount = 1;
            int index = 0;
            while ((line = br.readLine()) != null) {

                if (index > 0 && replaceLFtoBR) {
                    // 改行コードを<br>へ
                    sbuilder.append("<br>");
                }

                // 半角スペースを&nbsp;に置換
                if (replaceSpToNbsp && !"".equals(line)) {
                    strBuilder.setLength(0);
                    char ch = line.charAt(0);
                    for (int i = 0; i < line.length(); i++, sizeMngCount++) {
                        ch = line.charAt(i);
                        // 半角スペースである場合、"&nbsp;"に変換します。
                        if (ch == ' ') {
                            strBuilder.append("&nbsp;");
                        } else {
                            strBuilder.append(ch);
                        }
                        // 指定されたサイズで区切り、<br>を付与する。
                        if (fillColumn > 0 && sizeMngCount > 1
                                && sizeMngCount % fillColumn == 0
                                && i != line.length() - 1) {
                            strBuilder.append("<br>");
                        }
                    }
                    line = strBuilder.toString();
                }
                sbuilder.append(line);
                ++index;
            }

            // プロパティ値の末尾に<br>を付与する
            if (addBR) {
                sbuilder.append("<br>");
            }

            //""を&nbsp;に置換
            if (replaceNullToNbsp && index == 0) {
                line = br.readLine();
                if (line == null || "".equals(line)) {
                    sbuilder.append("&nbsp;");
                    output = sbuilder.toString();
                    TagUtil.write(pageContext, output);
                    return SKIP_BODY;
                }
            }
            output = sbuilder.toString();
        } catch (IOException e) {
            log.error("StringReader IO error.");

            throw new JspTagException(e.getMessage());
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

        return SKIP_BODY;
    }

    /**
     * <p>すべてのアロケートされた資源を解放する。</p>
     */
    @Override
    public void release() {
        super.release();
        this.filter = true;
        this.replaceNullToNbsp = true;
        this.replaceSpToNbsp = true;
        this.replaceLFtoBR = true;
        this.ignore = false;
        this.name = null;
        this.property = null;
        this.scope = null;
        this.fillColumn = -1;
    }

}
