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

package jp.terasoluna.fw.web.struts.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jp.terasoluna.fw.web.struts.action.ActionMappingEx;
import jp.terasoluna.fw.web.struts.action.RequestProcessorEx;
import jp.terasoluna.fw.web.struts.form.FormEx;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.Globals;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

/**
 * アクション基底クラス。
 *
 * <p>
 *  StrutsのActionに、ログ出力機能・トランザクション
 *  トークンチェック機能を追加している。<br>
 *  本クラスを利用するためには、doExecute()メソッドを
 *  オーバライドしたサブクラスを作成し、struts-config.xmlの
 *  &lt;action&gt;要素内のtype属性でクラス名を設定する。<br>
 *  トランザクショントークンチェックとは、サブミット２度押しや、
 *  ブラウザの「戻る」ボタンを使った重複サブミットを防ぐ機能である。<br>
 *  トランザクショントークンチェックを行うためには、以下の設定を
 *  行う必要がある。
 *  <ul>
 *   <li>
 *     アクションクラスのBean定義で
 *     &lt;property&gt;要素の
 *     &quot;tokenCheck&quot;に対し、
 *     &quot;true&quot;を明示する必要がある
 *   </li>
 *   <li>
 *     struts-config.xmlの
 *     &lt;forward&gt;要素（&lt;grobal-forwards&gt;
 *     内でも可）で&quot;txtoken-error&quot;という名前で
 *     トークンエラー時のパスを指定する
 *   </li>
 *  </ul>
 *  ActionExを使用した場合、saveToken()に
 *  よって自動的にトークンが保存されるが、保存しない場合には以下の設定を行う
 *  必要がある。<br>
 *  <ul>
 *   <li>
 *     アクションクラスのBean定義で
 *     &lt;property&gt;要素の
 *     &quot;saveToken&quot;に対し、
 *     &quot;false&quot;を明示する必要がある
 *   </li>
 *  </ul>
 *  トランザクショントークンチェックを含めたstruts-config.xml
 *  及びBean定義ファイルの設定例を、下記に示す。<br>
 * </p>
 * <p>
 *  <strong>サブクラスLogoffActionのBean定義ファイルの設定例</strong>
 *  <code><pre>
 *  &lt;bean name="/admin/Logout" scope="prototype"
 *      class="jp.terasoluna.fw.web.struts.actions.LogoffAction"&gt
 *    &lt;property property="tokenCheck"&gt;
 *      &lt;value&gt;true&lt;/value&gt;
 *    &lt;/property&gt;
 *    &lt;property property="saveToken"&gt;
 *      &lt;value&gt;false&lt;/value&gt;
 *    &lt;/property&gt;
 *  &lt;/bean&gt
 *  </pre></code>
 * </p>
 * <p>
 *  <strong>サブクラスLogoffActionのstruts-config.xmlの設定例</strong>
 *  <code><pre>
 *  &lt;action path="/admin/Logout"
 *      name="logonSampleForm"
 *      scope="session"
 *      parameter="/sub/logout.jsp"&gt
 *    &lt;forward name="txtoken-error" modeule="/sub"
 *        path="/doubleRegistError.jsp"/&gt
 *  &lt;/action&gt
 *  </pre></code>
 *
 *  なお、内部要素のforwardで、pathの先頭にスラッシュ
 *  &quot;/&quot;を付け、moduleを設定した場合、
 *  モジュールごとの相対パスで遷移することができる。<br>
 *  また、本機能をサブクラスで継承する場合、doExecute()メソッドを
 *  エントリポイントとして実装する必要がある。<br>
 *  アクションマッピングの設定については、ActionMappingExを参照のこと。
 * </p>
 *
 * @see jp.terasoluna.fw.web.struts.action.ActionMappingEx
 *
 */
public abstract class ActionEx extends Action {

    /**
     * ログクラス。
     */
    private static Log log = LogFactory.getLog(ActionEx.class);

    /**
     * トランザクショントークンチェックエラー時の論理フォワード名。
     */
    protected static final String FORWARD_TXTOKEN_ERROR = "txtoken-error";

    /**
     * トランザクショントークンチェックを行うかどうか。
     * デフォルトはfalse（行わない）。
     */
    private boolean tokenCheck = false;

    /**
     * トランザクショントークンの保存をするかどうか。
     * デフォルトはtrue（保存する）。
     */
    private boolean saveToken = true;

    /**
     * トランザクショントークンチェックフラグを設定する。
     *
     * @param tokenCheck トランザクショントークンチェックフラグ
     */
    public void setTokenCheck(boolean tokenCheck) {
        this.tokenCheck = tokenCheck;
    }

    /**
     * トランザクショントークン保存フラグを設定する。
     *
     * @param saveToken トランザクショントークン保存フラグ
     */
    public void setSaveToken(boolean saveToken) {
        this.saveToken = saveToken;
    }
    
    /**
     * トランザクショントークンチェックフラグを取得する。
     *
     * @return トランザクショントークンチェックフラグ
     */
    protected boolean isTokenCheck() {
        return tokenCheck;
    }
    
    /**
     * トランザクショントークン保存フラグを取得する
     *
     * @return トランザクショントークン保存フラグ
     */
    protected boolean isSaveToken() {
        return saveToken;
    }

    /**
     * アクションを実行する。
     * <p>
     *  サブクラスで実装されたdoExecute()メソッド呼び出し前に、
     *  アクションフォームのmodified属性をfalseに書き換える。<br>
     *  doExecute()の実行後、アクションフォームの
     *  フィールド値に変更があればリクエスト属性にSKIP_POPULATE
     *  を保存し、RequestProcessorExで
     *  processPopulate()の処理を抑制している。
     * </p>
     *
     * @param mapping このアクションに紐づいたアクションマッピング
     * @param form アクションフォーム
     * @param request リクエスト情報
     * @param response レスポンス情報
     * @return 遷移先情報
     * @throws Exception アクション実行時の例外
     */
    @Override
    public ActionForward execute(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
            throws Exception {
        if (log.isDebugEnabled()) {
            log.debug("execute() called.");
        }

        if (!processTokenCheck(mapping, request)) {
            if (log.isInfoEnabled()) {
                log.info("Transaction token error.");                
            }
            if (log.isDebugEnabled()) {
                log.debug("forward = " + FORWARD_TXTOKEN_ERROR);
            }
            return mapping.findForward(FORWARD_TXTOKEN_ERROR);
        }

        if (form != null && form instanceof FormEx) {
            FormEx formEx = (FormEx) form;
            formEx.setModified(false);
        }

        if (log.isDebugEnabled()) {
            log.debug("doExecute() called.");
        }
        ActionForward forward = doExecute(mapping, form, request, response);
        if (log.isDebugEnabled()) {
            log.debug("doExecute() finished.");
        }

        if (form != null && form instanceof FormEx) {
            FormEx formEx = (FormEx) form;
            if (formEx.isModified()) {
                request.setAttribute(RequestProcessorEx.SKIP_POPULATE,
                    mapping.getName());
            }
        }

        if (log.isDebugEnabled()) {
            if (forward != null) {
                log.debug("forward = " + forward.getName()
                    + "(" + forward.getPath() + ")");
            } else {
                log.debug("forward = null");
            }
        }

        return forward;
    }

    /**
     * アクションを実行する抽象メソッド。
     * <p>
     *  このメソッドをサブクラスでオーバーライドし、
     *  アクションの機能を拡張する。
     * </p>
     *
     * @param mapping このアクションに紐づいたアクションマッピング
     * @param form アクションフォーム
     * @param request リクエスト情報
     * @param response レスポンス情報
     * @return 遷移先情報
     * @throws Exception 予期しない例外
     */
    public abstract ActionForward doExecute(ActionMapping mapping,
                                            ActionForm form,
                                            HttpServletRequest request,
                                            HttpServletResponse response)
        throws Exception;

    /**
     * トランザクショントークンのチェックを行う。
     *
     * @param mapping アクションマッピング
     * @param req HTTPリクエスト
     * @return トークンが正当であれば <code>true</code>
     */
    protected boolean processTokenCheck(ActionMapping mapping,
                                        HttpServletRequest req) {
        if (log.isDebugEnabled()) {
            log.debug("processTokenCheck() called.");
        }

        // mappingがActionMappingExではないとき、無条件でtrueを返却する
        if (!(mapping instanceof ActionMappingEx)) {
            if (log.isDebugEnabled()) {
                log.debug("mapping is not instance of ActionMappingEx.");
            }
            return true;
        }

        boolean result = true;
        synchronized (req.getSession().getId().intern()) {
            if (tokenCheck) {
                if (!isTokenValid(req)) {
                    result = false;
                }
            }
            if (saveToken) {
                saveToken(req);
            }
        }
        return result;
    }

    /**
     * セッションから、Globals.ERROR_KEYをキーとして
     * ActionMessagesを取得し、追加後に再度格納を行う。
     *
     * @param session セッション
     * @param errors エラーメッセージ情報
     */
    protected void addErrors(HttpSession session, ActionMessages errors) {

        if (session == null) {
            return;
        }
        
        if (errors == null) {
            return;
        }

        // セッションからエラーメッセージ情報を取得する、または新しい対象を作成する
        ActionMessages sessionErrors =
            (ActionMessages) session.getAttribute(Globals.ERROR_KEY);
        if (sessionErrors == null) {
            sessionErrors = new ActionMessages();
        }
        // 引込みエラーを追加する
        sessionErrors.add(errors);

        // まだ、エラーメッセージ情報が空の場合、セッションの全てのエラーメッセージ情報を削除する
        if (sessionErrors.isEmpty()) {
            session.removeAttribute(Globals.ERROR_KEY);
            return;
        }

        // セッションにエラーメッセージを保存する
        session.setAttribute(Globals.ERROR_KEY, sessionErrors);

    }

    /**
     * セッションから、Globals.MESSAGE_KEYをキーとして
     * ActionMessagesを取得し、追加後に再度格納を行う。
     *
     * @param session セッション
     * @param messages メッセージ情報
     */
    protected void addMessages(HttpSession session, ActionMessages messages) {

        if (session == null) {
            return;
        }
        
        if (messages == null) {
            return;
        }

        // セッションからメッセージ情報を取得する、または新しい対象を作成する
        ActionMessages sessionMessages =
            (ActionMessages) session.getAttribute(Globals.MESSAGE_KEY);
        if (sessionMessages == null) {
            sessionMessages = new ActionMessages();
        }
        // 引込みメッセージを追加する
        sessionMessages.add(messages);

        // まだ、メッセージ情報が空の場合、セッションの全てのメッセージ情報を削除する
        if (sessionMessages.isEmpty()) {
            session.removeAttribute(Globals.MESSAGE_KEY);
            return;
        }

        // セッションにメッセージを保存する
        session.setAttribute(Globals.MESSAGE_KEY, sessionMessages);
    }

}
