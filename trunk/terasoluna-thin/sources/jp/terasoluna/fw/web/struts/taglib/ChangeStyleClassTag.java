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

package jp.terasoluna.fw.web.struts.taglib;

import java.io.IOException;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.Globals;
import org.apache.struts.action.ActionMessages;

/**
 * <p><code>changeStyleClass</code>タグの実装クラス。</p>
 *
 * <p>指定したフィールドについてのエラー情報が設定されているかどうかによって、
 * スタイルシートのクラス名の切り替えを行う。<br>
 * アクションフォームのフィールドにエラーがある場合に、そのフィールドの
 * 部分を赤字にするなどの表示を変更させる場合に利用する。</p>
 * <h5>タグがサポートする属性</h5>
 * <p><code>changeStyleClass</code> タグでは、以下の属性をサポートする。</p>
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
 *    <td> <code>name</code> </td>
 *    <td> - </td>
 *    <td> <code>true</code> </td>
 *    <td> <code>true</code> </td>
 *    <td align="left">
 *      エラー情報が設定されているかどうかを
 *      判定するフィールド名。
 *    </td>
 *   </tr>
 *   <tr>
 *    <td> <code>default</code> </td>
 *    <td> - </td>
 *    <td> <code>true</code> </td>
 *    <td> <code>true</code> </td>
 *    <td align="left">
 *      エラーがない場合のスタイルシートクラス名。
 *    </td>
 *   </tr>
 *   <tr>
 *    <td> <code>error</code> </td>
 *    <td> - </td>
 *    <td> <code>true</code> </td>
 *    <td> <code>true</code> </td>
 *    <td align="left">
 *      エラーがある場合のスタイルシートクラス名。
 *    </td>
 *   </tr>
 * </table>
 * </div>
 * <br><br>
 * <h5>カスタムタグのスクリプティング変数</h5>
 * <p>このタグによって設定されるスクリプティング変数はありません。</p>
 * <br>
 * <h5>使用方法</h5>
 * <p><code><pre>
 * &lt;td class='&lt;ts:changeStyleClass name="mou1"
 *    default="gaid" error="error"/&gt;'&gt;
 *   &lt;input type="text" name="mou1"&gt;
 * &lt;/td&gt;
 * </pre></code></p>
 */
public class ChangeStyleClassTag extends TagSupport {

    /**
     * シリアルバージョンID
     */
    private static final long serialVersionUID = 8969715040525132492L;

    /**
     * ログクラス。
     */
    private static Log log = LogFactory.getLog(ChangeStyleClassTag.class);
    
    /**
     * エラー情報が設定されているかどうかを判定するフィールド名。
     */
    private String name = null;

    /**
     * フィールドにエラーがない場合のスタイルシートのクラス名。
     */
    private String defaultValue = null;

    /**
     * フィールドにエラーがある場合のスタイルシートのクラス名。
     */
    private String errorValue = null;

    /**
     * フィールド名を設定する。
     *
     * @param name フィールド名
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * フィールドにエラーがない場合のスタイルシートのクラス名を設定する。
     *
     * @param value エラーがない場合のスタイルシートのクラス名
     */
    public void setDefault(String value) {
        this.defaultValue = value;
    }

    /**
     * フィールドにエラーがある場合のスタイルシートのクラス名を設定する。
     *
     * @param value エラーがある場合のスタイルシートのクラス名
     */
    public void setError(String value) {
        this.errorValue = value;
    }

    /**
     * 指定されたフィールドにエラー情報が設定されているかどうかによって、
     * スタイルシートのクラス名を返却する。
     *
     * @param req HTTPリクエスト
     * @param fieldName フィールド名
     * @param ifNormal エラーがない場合のスタイルシートのクラス名
     * @param ifError エラーがある場合のスタイルシートのクラス名
     * @return スタイルシートのクラス名
     */
    private String chooseClass(HttpServletRequest req,
                               String fieldName,
                               String ifNormal,
                               String ifError) {
        ActionMessages errors = (ActionMessages)
            req.getAttribute(Globals.ERROR_KEY);
        if (errors == null) {
            HttpSession session = req.getSession(true);
            errors = (ActionMessages) session.getAttribute(Globals.ERROR_KEY);
            if (errors == null) {
                return ifNormal;
            }
        }
        Iterator iter = errors.get(fieldName);
        int errorCount = 0;
        while (iter.hasNext()) {
            iter.next();
            errorCount++;
        }
        return (errorCount == 0) ? ifNormal : ifError;
    }

    /**
     * タグ評価開始時に呼ばれるメソッド。
     * エラーの有無によって出力するスタイルシートクラス名を変更する。
     *
     * @return 処理制御指示
     * @throws JspException JSP例外
     */
    @Override
    public int doStartTag() throws JspException {
        HttpServletRequest req
            = (HttpServletRequest) pageContext.getRequest();
        if (req == null) {
            return SKIP_BODY;
        }
        String result = chooseClass(req,
                                    this.name,
                                    this.defaultValue,
                                    this.errorValue);
        try {
            JspWriter out = pageContext.getOut();
            out.print(result);
        } catch (IOException e) {
            log.error("Output failed.");
            throw new JspTagException(e.toString());
        }
        return EVAL_BODY_INCLUDE;
    }

    /**
     * タグ評価終了時に呼ばれるメソッド。
     *
     * @return 処理制御指示
     * @throws JspException JSP例外
     */
    @Override
    public int doEndTag() throws JspException {
        return EVAL_PAGE;
    }

    /**
     * タグハンドラ解放時の処理。
     */
    @Override
    public void release() {
        super.release();
        this.name = null;
        this.defaultValue = null;
        this.errorValue = null;
    }

}
