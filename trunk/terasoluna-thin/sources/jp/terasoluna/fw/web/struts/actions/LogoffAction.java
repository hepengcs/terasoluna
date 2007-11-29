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

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jp.terasoluna.fw.exception.SystemException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * ログオフ処理を実行するアクション。
 *
 * <p>
 * このクラスの実装では、現在のHTTPセッションを無効化し、
 * struts-config.xmlで&lt;action&gt;要素の
 * parameter属性に指定した先にフォワードする。
 * struts-config.xml及びBean定義ファイルの設定は下記のとおりである。
 * </p>
 * <p>
 * <strong>Bean定義ファイルの設定</strong>
 *  <code><pre>
 *  &lt;bean name="/logoff" scope="prototype"
 *      <strong>class="jp.terasoluna.fw.web.struts.actions.LogoffAction"</strong>&gt;
 *  &lt;/bean&gt;
 *  </pre></code>
 * </p>
 * <p>
 * <strong>struts-config.xmlの設定</strong>
 * <code><pre>
 * &lt;action path="/logoff"
 *     name="_logonForm"
 *     scope="session"
 *     parameter="/foo.jsp"&gt;
 * &lt;/action&gt;
 * </pre></code>
 * ログイン処理については、UserValueObject、BLogicを参照のこと。
 * </p>
 *
 */
public class LogoffAction extends ActionEx {

    /**
     * ログクラス。
     */
    private static Log log = LogFactory.getLog(LogoffAction.class);
    
    /**
     * エラーページ（404）遷移失敗を示すエラーコード。
     */
    private static final String FORWARD_ERRORPAGE_ERROR = 
        "error.forward.errorpage";

    /**
     * ログオフ処理を実行する。HTTPセッションを無効化し、
     * parameter属性に設定された遷移先を
     * アクションフォワードにセットして返す。
     * parameter属性が設定されていない場合、（404）エラーを返す。
     *
     * @param mapping アクションマッピング
     * @param form アクションフォーム
     * @param req HTTPリクエスト
     * @param res HTTPレスポンス
     * @return 遷移先のアクションフォワード
     */
    @Override
    public ActionForward doExecute(ActionMapping mapping,
                                    ActionForm form,
                                    HttpServletRequest req,
                                    HttpServletResponse res) {
        if (log.isDebugEnabled()) {
            log.debug("doExecute() called.");
        }

        // 利用中のセッションを無効化する
        HttpSession session = req.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        // parameter属性（フォワード先）を取得
        String path = mapping.getParameter();

        if (path == null) {
            // parameter属性が設定されていない場合、（404）エラーを返却する
            try {
                res.sendError(HttpServletResponse.SC_NOT_FOUND);
            } catch (IOException e) {
                log.error("Error page(404) forwarding failed.");
                
                throw new SystemException(e, FORWARD_ERRORPAGE_ERROR);
            }
            return null;
        }

        // アクションフォワードを生成
        ActionForward retVal = new ActionForward(path);

        return retVal;
    }

}
