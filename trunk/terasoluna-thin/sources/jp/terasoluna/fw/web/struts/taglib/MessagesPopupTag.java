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
import javax.servlet.jsp.tagext.TagSupport;

import jp.terasoluna.fw.util.PropertyUtil;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionMessages;

/**
 * <p>
 *  1ユーザが異なるユースケースの画面を操作し、エラー・メッセージのポップ
 *  アップ表示が同時に行われるとき、セッション上にユースケース毎のエラー・
 *  メッセージ情報が結合されて表示されてしまう。<br>
 *  このタグと&lt;ts:errors&gt;タグ、及び&lt;ts:messages&gt;タグと
 *  組み合わせる事により、画面単位で発生したポップアップエラーの混同を
 *  避けることが可能となる。<br>
 *  {@link MessagesPopupTag}は、リクエスト属性として登録されている
 *  エラー・メッセージ情報をセッションに保存する。
 * </p>
 * <p>
 *  {@link MessagesPopupTag} は、<code>&lt;ts:body&gt;</code>
 *  タグと連携してポップアップ画面を開く。
 *  {@link MessagesPopupTag} を用いる際には、必ず
 *  <code>&lt;ts:body&gt;</code>タグと共に用い、また、
 *  {@link MessagesPopupTag} が<code>&lt;ts:body&gt;</code>タグよりも
 *  前に記述されなくてはならない。
 * </p>
 * <p>
 *  {@link MessagesPopupTag} は、<code>JavaScript</code> の
 *  <code>onLoad</code>イベント発生時のスクリプトを追加することで、
 *  エラー・メッセージ情報表示用のポップアップ画面を開く。<br>
 *  従って、エラー・メッセージ情報表示用のポップアップ画面を表示する際、
 *  下記の手順を取る。そのため、エラー・メッセージを表示前の画面では無く、
 *  エラー・メッセージ発生時の遷移先画面にこのタグを配置するよう留意する
 *  こと。<br>
 * <ol>
 *  <li>
 *   一旦入力に対する結果画面が返される。
 *  </li>
 *  <li>
 *   その結果画面の<code>onLoad</code>イベントでポップアップ画面が開かれる。
 *  </li>
 *  <li>
 *   エラー・メッセージ情報表示用のリクエストがあらためてサーバに送信される。
 *  </li>
 * </ol>
 * </p>
 * <p>
 *  {@link MessagesPopupTag} は、<code>&lt;ts:body&gt;</code> タグが
 *  生成する本来の<code>onLoad</code>イベント処理スクリプトに上書きされる。
 *  <code>JSP</code> 内で {@link MessagesPopupTag}の前に
 *  <code>onLoad</code> タグがある場合には、エラーがないときには
 *  <code>onLoad</code> タグ内のスクリプトが有効になり、
 *  エラーがある場合には {@link MessagesPopupTag} による設定で上書きされ、
 *  エラー表示用のポップアップ画面が優先される。
 * </p>
 * <h5>タグがサポートする属性</h5>
 * <p>{@link MessagesPopupTag} では、以下の属性をサポートする。</p>
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
 *    <td> <code>popup</code> </td>
 *    <td> - </td>
 *    <td> <code>true</code> </td>
 *    <td> <code>true</code> </td>
 *    <td align="left">
 *     ポップアップ画面で表示するURL。<code>JavaScript</code>の
 *     <code>window.open()</code>の第一引数に対応する。<br>
 *    </td>
 *   </tr>
 *   <tr>
 *    <td> <code>title</code> </td>
 *    <td> <code>popup</code> </td>
 *    <td> <code>false</code> </td>
 *    <td> <code>true</code> </td>
 *    <td align="left">
 *     エラーを表示するポップアップ画面のタイトル。
 *    </td>
 *   </tr>
 *   <tr>
 *    <td> <code>param</code> </td>
 *    <td> - </td>
 *    <td> <code>false</code> </td>
 *    <td> <code>true</code> </td>
 *    <td align="left">
 *     <code>JavaScript</code> でポップアップ画面を開くときのパラメータ文字列。
 *    </td>
 *   </tr>
 *   <tr>
 *    <td> <code>paramType</code> </td>
 *    <td> - </td>
 *    <td> <code>false</code> </td>
 *    <td> <code>true</code> </td>
 *    <td align="left">
 *     <code>JavaScript</code> でポップアップ画面を開くときのパラメータ文字列を、
 *     <code>ApplicationResources</code> ファイルから取得する場合の
 *     リソースキー。
 *    </td>
 *   </tr>
 *   <tr>
 *    <td> <code>paramFunc</code> </td>
 *    <td> - </td>
 *    <td> <code>false</code> </td>
 *    <td> <code>true</code> </td>
 *    <td align="left">
 *     <code>JavaScript</code> でポップアップ画面を開くときの
 *     パラメータ文字列を取得する <code>JavaScript</code> 関数名。
 *    </td>
 *   </tr>
 *   <tr>
 *    <td> <code>windowId</code> </td>
 *    <td> - </td>
 *    <td> <code>false</code> </td>
 *    <td> <code>true</code> </td>
 *    <td align="left">
 *     開いたポップアップ画面を保持する <code>JavaScript</code> 変数名。
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
 *
 * <p><code><pre>
 * &lt;ts:messagesPopup popup="/popup/errors.do" /&gt;
 *   ...
 * &lt;ts:body ...&gt;
 * </pre></code></p>
 *
 * @see jp.terasoluna.fw.web.struts.taglib.BodyTag
 *
 */
public class MessagesPopupTag extends TagSupport {
    
    /**
     * シリアルバージョンID
     */
    private static final long serialVersionUID = 1148524236499924202L;

    /**
     * <code>onLoad</code> 時の処理をリクエスト属性に保存するときのキー。
     */
    public static final String ON_LOAD_KEY = BodyTag.ON_LOAD_KEY;

    /**
     * ポップアップ時にリクエストパラメータで渡されるエラー情報のキー。
     */
    public static final String POPUP_ERROR_KEY = "popup_error_key";

    /**
     * ポップアップ時にリクエストパラメータで渡されるメッセージ情報のキー。
     */
    public static final String POPUP_MESSAGE_KEY = "popup_message_key";

    /**
     * デフォルトタイトル。値は、<code>popup</code>。
     */
    private static final String DEFAULT_TITLE = "popup";

    /**
     * ポップアップ画面で表示するURL。コンテキストパスは含まない。
     */
    private String popup = null;

    /**
     * エラー情報・メッセージ情報を表示するポップアップ画面のタイトル。
     */
    private String title = DEFAULT_TITLE;

    /**
     * <code>JavaScript</code>
     * でポップアップ画面を開くときのパラメータ文字列。
     */
    private String param = null;

    /**
     * <code>JavaScript</code>
     * でポップアップ画面を開くときのパラメータ文字列を
     * 取得する <code>JavaScript</code> 関数名。
     */
    private String paramFunc = null;

    /**
     * 開いたポップアップ画面を保持する <code>JavaScript</code> 変数名。
     */
    private String windowId = null;

    /**
     * ポップアップ画面で表示するURLを設定する。
     *
     * @param value ポップアップ画面で表示するURL
     */
    public void setPopup(String value) {
        this.popup = value;
    }

    /**
     * エラーを表示するポップアップ画面のタイトルを設定する。
     *
     * @param value タイトル
     */
    public void setTitle(String value) {
        this.title = value;
    }

    /**
     * <code>JavaScript</code>
     * でポップアップ画面を開くときのパラメータ文字列を設定する。
     *
     * @param value パラメータ
     */
    public void setParam(String value) {
        this.param = value;
    }

    /**
     * <code>JavaScript</code>
     * でポップアップ画面を開くときのパラメータ文字列を、
     * <code>ApplicationResources</code> ファイルから取得して設定する。
     *
     * @param value パラメータ
     */
    public void setParamType(String value) {
        this.param = PropertyUtil.getProperty("messages.popup.param." + value);
    }

    /**
     * <code>JavaScript</code>
     * でポップアップ画面を開くときのパラメータ文字列として、
     * 指定された<code>JavaScript</code> 関数の戻り値を設定する。
     *
     * @param value パラメータ
     */
    public void setParamFunc(String value) {
        this.paramFunc = value;
    }

    /**
     * 開いたポップアップ画面を保持する <code>JavaScript</code>
     * 変数名を設定する。
     *
     * @param value 変数名
     */
    public void setWindowId(String value) {
        this.windowId = value;
    }

    /**
     * タグ評価開始時に呼ばれるメソッド。
     *
     * @return 処理制御指示。常に <code>EVAL_BODY_INCLUDE</code>
     * @throws JspException JSP例外
     */
    @Override
    public int doStartTag() throws JspException {
        HttpServletRequest req
            = (HttpServletRequest) pageContext.getRequest();

        if (req.getAttribute(Globals.ERROR_KEY) == null
                && req.getAttribute(Globals.MESSAGE_KEY) == null) {
            // リクエストにエラー情報・メッセージ情報が
            // 存在しないとき、以降の処理は行われない。
            return EVAL_BODY_INCLUDE;
        }

        ActionMessages errors =
            (ActionMessages) req.getAttribute(Globals.ERROR_KEY);

        ActionMessages messages =
            (ActionMessages) req.getAttribute(Globals.MESSAGE_KEY);

        String errorKey = null;
        String messageKey = null;

        HttpSession session
             = pageContext.getSession();
        if (errors != null && !errors.isEmpty()) {
            errorKey = RandomUtil.generateRandomID();
            // セッションにエラー情報を保存する。
            session.setAttribute(errorKey, errors);
        }
        if (messages != null && !messages.isEmpty()) {
            messageKey = RandomUtil.generateRandomID();
            // セッションにメッセージ情報を保存する。
            session.setAttribute(messageKey, messages);
        }

        // <body>タグのonLoad属性で指定されるスクリプト文字列
        // を取得する。
        String script = getOnLoadScript(req, errorKey, messageKey);
        req.setAttribute(ON_LOAD_KEY, script);

        return EVAL_BODY_INCLUDE;
    }

    /**
     * <code>&lt;body&gt;</code>タグの<code>onLoad</code>属性に
     * 記述されるウィンドウオープンのためのスクリプトを生成する。
     *
     * @param req HTTPリクエスト
     * @param errorKey エラー情報のキー
     * @param messageKey メッセージ情報のキー
     * @return ウィンドウオープンを行うスクリプト
     */
    private String getOnLoadScript(HttpServletRequest req,
            String errorKey, String messageKey) {

        // エラー・メッセージ情報のキーが共に
        // 存在しない場合、処理を終了する。
        if (errorKey == null && messageKey == null) {
            return null;
        }

        StringBuilder onLoad = new StringBuilder();
        onLoad.append("  ");
        if (this.windowId != null) {
            // ウィンドウID（スクリプトからの戻り値を格納）
            onLoad.append(this.windowId);
            onLoad.append(" = ");
        }
        onLoad.append("window.open(\"");
        // URLを指定
        onLoad.append(req.getContextPath());
        onLoad.append(this.popup);
        // エラー情報・メッセージ情報のいずれかが存在する時、
        // リクエストパラメータでキーを送信する。
        onLoad.append(getRequestParameterKey(errorKey,
            messageKey));
        onLoad.append("\", \"");
        if (this.title != null) {
            // ウィンドウタイトル表示を指定
            onLoad.append(this.title);
        }
        onLoad.append("\", ");
        if (this.paramFunc != null) {
            // JavaScript関数の戻り値を指定
            onLoad.append(this.paramFunc);
        } else {
            onLoad.append("\"");
            if (this.param != null) {
                // ポップアップ画面のサイズ、位置などの
                // パラメータ情報を指定
                onLoad.append(this.param);
            }
            onLoad.append("\"");
        }
        onLoad.append(");" + System.getProperty("line.separator"));

        return onLoad.toString();
    }

    /**
     * セッションに格納されているエラー・メッセージ
     * 情報のキーを元に、リクエストパラメータの
     * クエリ文字列を作成する。
     *
     * @param errorKey エラー情報キー
     * @param messageKey メッセージ情報キー
     * @return リクエストパラメータとして送信されるクエリ文字列
     */
    private String getRequestParameterKey(String errorKey,
            String messageKey) {

        StringBuilder param = new StringBuilder();
        param.append("?");
        if (errorKey != null) {
            param.append(POPUP_ERROR_KEY);
            param.append("=");
            param.append(errorKey);
        }
        if (errorKey != null && messageKey != null) {
            // エラー情報キー・メッセージ情報キーが両方
            // 存在する場合のみ、連結文字列を追加する。
            param.append("&");
        }
        if (messageKey != null) {
            param.append(POPUP_MESSAGE_KEY);
            param.append("=");
            param.append(messageKey);
        }

        return param.toString();
    }

    /**
     * タグ評価終了時に呼ばれるメソッド。
     *
     * @return 処理制御指示。常に <code>EVAL_PAGE</code>
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
        this.popup = null;
        this.title = DEFAULT_TITLE;
        this.param = null;
        this.paramFunc = null;
        this.windowId = null;
    }

}
