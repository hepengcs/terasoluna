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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionMessages;

/**
 * ポップアップ画面でエラー表示を行う。
 *
 * <code>Struts</code>の<code>ErrorsTag</code>を拡張し、
 * &lt;ts:messagePopup&gt;<br>タグと共にポップアップが行われる
 * エラー情報をセッションからリクエストに移しかえて、セッションからは
 * 削除を行う。<br>
 *
 * <b>※ 注意、TERASOLUNA1.1.xとのErrorsTagの機能とは異なる。</b><br>
 *
 * ポップアップ画面でのエラー表示は、このタグが使用されない限り、
 * セッションからエラー情報は削除されないため、注意すること。<br>
 *
 * <h5>タグがサポートする属性</h5>
 * <p>{@link ErrorsTag} では、以下の属性をサポートする。</p>
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
 *    <td> <code>bundle</code> </td>
 *    <td> - </td>
 *    <td> <code>false</code> </td>
 *    <td> <code>true</code> </td>
 *    <td align="left">
 *     メッセージリソース名を指定する。ここを指定しない場合デフォルト
 *     のメッセージリソースとなる。
 *    </td>
 *   </tr>
 *   <tr>
 *    <td> <code>locale</code> </td>
 *    <td> - </td>
 *    <td> <code>false</code> </td>
 *    <td> <code>true</code> </td>
 *    <td align="left">
 *     出力メッセージのロケールを指定する。
 *     ここを指定しない場合、デフォルトのロケールが使用される。
 *    </td>
 *   </tr>
 *   <tr>
 *    <td> <code>name</code> </td>
 *    <td> - </td>
 *    <td> <code>false</code> </td>
 *    <td> <code>true</code> </td>
 *    <td align="left">
 *     表示を行なうアクションエラーのエラーキーを個別に指定する。
 *     ここを指定しない場合、<code>Globals.ERROR_KEY</code>を元に取得される
 *     エラーメッセージ一覧を表示する。
 *    </td>
 *   </tr>
 *   <tr>
 *    <td> <code>property</code> </td>
 *    <td> - </td>
 *    <td> <code>false</code> </td>
 *    <td> <code>true</code> </td>
 *    <td align="left">
 *     表示を行なう（フォーム）プロパティ名を指定する。
 *     ここが指定されない場合、プロパティ名に関わらず、
 *     全てのアクションエラーが表示される。
 *    </td>
 *   </tr>
 *  </table>
 * </div>
 * <br><br>
 * <h5>カスタムタグのスクリプティング変数</h5>
 * <p>このタグによって設定されるスクリプティング変数はありません。</p>
 *
 * <br>
 *
 * <h5>使用方法</h5>
 * <p><code><pre>
 * &lt;ts:errors bundle=&quot;sampleResources&quot;
 *     name=&quot;normalKey&quot; /&gt;
 * </pre></code></p>
 *
 */
public class ErrorsTag extends org.apache.struts.taglib.html.ErrorsTag {

    /**
	 * シリアルバージョンID
	 */
	private static final long serialVersionUID = -1764868652464908717L;

	/**
     * ポップアップ画面で、セッションに格納されているエラー情報を
     * リクエストに移動し、エラー情報の表示処理を行う。
     *
     * @return 処理結果ステータス
     * @throws javax.servlet.jsp.JspException JSP例外
     */
    @Override
    public int doStartTag() throws JspException {
        // リクエストパラメータで送信されているエラーキーを取得する。
        HttpServletRequest request
            = (HttpServletRequest) pageContext.getRequest();
        String errorKey = request.getParameter(
            MessagesPopupTag.POPUP_ERROR_KEY);
        if (errorKey == null) {
            // (Popup画面と通常のエラー表示画面を兼ねている場合に備え)
            // エラーキーが存在しない場合、Strutsのエラー表示処理に
            // 移行する。
            return super.doStartTag();
        }

        HttpSession session = pageContext.getSession();
        ActionMessages errors = (ActionMessages) session.getAttribute(errorKey);
        request.setAttribute(Globals.ERROR_KEY, errors);

        // セッションからエラーを削除する。
        session.removeAttribute(errorKey);

        return super.doStartTag();
    }
}
