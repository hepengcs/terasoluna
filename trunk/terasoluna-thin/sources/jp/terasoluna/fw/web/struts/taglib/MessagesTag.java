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
 * ポップアップ画面で、アクションメッセージの表示を行う。
 *
 * <code>Struts</code>の<code>MessagesTag</code>を拡張し、
 * &lt;ts:messagePopup&gt;<br>タグと共にポップアップが行われる
 * メッセージ情報をセッションからリクエストに移しかえて、セッションからは
 * 削除を行う。<br>
 *
 * <b>※ 注意、TERASOLUNA1.1.xとのMessagesTagの機能とは異なる。</b><br>
 *
 * ポップアップ画面でのメッセージ情報表示は、このタグが使用されない限り、
 * セッションからメッセージ情報は削除されないため、注意すること。<br>
 *
 * <h5>タグがサポートする属性</h5>
 * <p>{@link MessagesTag} では、以下の属性をサポートする。</p>
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
 *    <td> <code>true</code> </td>
 *    <td> <code>false</code> </td>
 *    <td align="left">
 *     メッセージを格納したい<code>bean</code>名。
 *    </td>
 *   </tr>
 *   <tr>
 *    <td> <code>bundle</code> </td>
 *    <td> - </td>
 *    <td> <code>false</code> </td>
 *    <td> <code>true</code> </td>
 *    <td align="left">
 *     メッセージリソース名を指定する。ここを指定しない場合
 *     デフォルトのメッセージリソースが使用される。
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
 *     表示を行なうアクションメッセージのメッセージキーを個別に指定する。
 *     <code>message</code>属性の値を&quot;true&quot;に指定した場合は、
 *     必ず、<code>Globals.MESSAGE_KEY</code>が設定される。
 *     なお、設定が行なわれていない場合、<code>Globals.ERROR_KEY</code>
 *     が設定される。
 *    </td>
 *   </tr>
 *   <tr>
 *    <td> <code>property</code> </td>
 *    <td> - </td>
 *    <td> <code>false</code> </td>
 *    <td> <code>true</code> </td>
 *    <td align="left">
 *     表示を行なう（フォーム）プロパティ名を指定する。
 *     ここが指定されない場合、プロパティ名に関わらず、全ての
 *     アクションメッセージが表示される。
 *    </td>
 *   </tr>
 *   <tr>
 *    <td> <code>header</code> </td>
 *    <td> - </td>
 *    <td> <code>false</code> </td>
 *    <td> <code>true</code> </td>
 *    <td align="left">
 *     メッセージ本文一覧の前に出力されるヘッダメッセージキーを
 *     指定する。
 *    </td>
 *   </tr>
 *   <tr>
 *    <td> <code>footer</code> </td>
 *    <td> - </td>
 *    <td> <code>false</code> </td>
 *    <td> <code>true</code> </td>
 *    <td align="left">
 *     メッセージ本文一覧の後に出力されるフッタメッセージキーを
 *     指定する。
 *    </td>
 *   </tr>
 *   <tr>
 *    <td> <code>message</code> </td>
 *    <td> - </td>
 *    <td> <code>false</code> </td>
 *    <td> <code>true</code> </td>
 *    <td>
 *     値を <code>&quot;true&quot;</code>に指定したとき、<code>name</code>
 *     属性が、<code>Globals.MESSAGE_KEY</code>として設定される。
 *    </td>
 *   </tr>
 *  </table>
 * </div>
 * <br><br>
 * <h5>カスタムタグのスクリプティング変数</h5>
 * <p>このタグによって設定されるスクリプティング変数はありません。</p>
 * <br>
 *
 * <h5>使用方法</h5>
 * <code>&lt;ts:messages&gt;</code>の<code>id</code>属性で指定した
 * <code>bean</code>名で内部タグから参照可能となる。
 * <p><code><pre>
 * &lt;ts:messages id=&quot;msg&quot; bundle=&quot;sampleResources&quot;
 *     message=&quot;true&quot;&gt;
 *    &lt;bean:write name="msg"/&gt;
 * &lt;/ts:messages&gt;
 * </pre></code></p>
 *
 */
public class MessagesTag extends org.apache.struts.taglib.html.MessagesTag {

    /**
     * シリアルバージョンID
     */
    private static final long serialVersionUID = 1811855638381884729L;

    /**
     * ポップアップ画面で、セッションに格納されているメッセージ情報を
     * リクエストに移動し、メッセージ情報の表示処理を行う。
     *
     * @return 処理結果ステータス
     * @throws javax.servlet.jsp.JspException JSP例外
     */
    @Override
    public int doStartTag() throws JspException {
        // リクエストパラメータで送信されているメッセージキーを取得する。
        HttpServletRequest request
            = (HttpServletRequest) pageContext.getRequest();
        String messageKey = request.getParameter(
            MessagesPopupTag.POPUP_MESSAGE_KEY);
        if (messageKey == null) {
            // (Popup画面と通常のメッセージ表示画面を兼ねている場合に備え)
            // メッセージキーが存在しない場合、Strutsのメッセージ表示処理に
            // 移行する。
            return super.doStartTag();
        }

        HttpSession session = pageContext.getSession();
        ActionMessages messages
            = (ActionMessages) session.getAttribute(messageKey);
        request.setAttribute(Globals.MESSAGE_KEY, messages);

        // セッションからエラーを削除する。
        session.removeAttribute(messageKey);

        return super.doStartTag();
    }
}
