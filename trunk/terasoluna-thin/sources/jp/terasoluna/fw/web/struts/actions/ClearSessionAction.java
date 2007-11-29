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
import java.util.List;

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
 * セッションから指定されたプロパティを削除する。
 *
 * <p>
 *  Bean定義ファイルに指定されたキーのリストから、
 *  セッションより削除するキーを取得し削除を行う。<br>
 * </p>
 *
 * <p>
 *  遷移先は、常に&lt;action&gt;要素の
 *  parameter属性に指定されたアドレスにフォワードするため、
 *  parameter属性が必須となる。
 *  続いて、下記はBean定義ファイル及びstruts-config.xmlの
 *  設定例である。
 * </p>
 * <p>
 *  <strong>Bean定義ファイルのClearSessionAction設定例</strong>
 * <code><pre>
 * &lt;bean name="/clearSessionAction" singleton="false"
 *   class="jp.terasoluna.fw.web.struts.actions.ClearSessionAction"&gt;
 *   <strong>&lt;property name="clearSessionKeys"&gt;
 *   &lt;list&gt;
 *     &lt;value&gt;userAddress&lt;/value&gt;
 *     &lt;value&gt;userPhoneNo&lt;/value&gt;
 *     &lt;value&gt;sampleSession&lt;/value&gt;
 *   &lt;/list&gt;
 *   &lt;/property&gt;</strong>
 * &lt;/bean&gt;
 * </pre></code>
 * </p>
 * <p>
 *  <strong>struts-config.xmlの設定例</strong>
 * <code><pre>
 * &lt;action path="/clearSessionAction"
 *   name="_sampleForm"
 *   scope="session"
 *   parameter="/sessionCleared.do"&gt;
 * &lt;/action&gt;
 * </pre></code>
 * 上記設定により、アクションパス名&quot;/clearSessionAction&quot;
 * が実行されると、clearSessionKeysプロパティが削除対象のキー
 * となり、その結果、userAddress、userPhoneNo、sampleSessionという
 * 3つのセッションキーから参照されるセッション情報が削除される。<br>
 * 現在のセッションそのものを破棄する場合は、LogoffActionを参照のこと。
 * </p>
 *
 * @see jp.terasoluna.fw.web.struts.actions.LogoffAction
 *
 */
public class ClearSessionAction extends ActionEx {

    /**
     * ログクラス。
     */
    private static Log log = LogFactory.getLog(ClearSessionAction.class);
    
    /**
     * エラーページ（404）遷移失敗を示すエラーコード。
     */
    private static final String FORWARD_ERRORPAGE_ERROR = 
        "error.forward.errorpage";

    /**
     * セッションから削除するキーのリスト。
     */
    private List clearSessionKeys = null;

    /**
     * セッションから削除するキーのリストを設定。
     *
     * @param clearSessionKeys セッションから削除するキーのリスト
     */
    public void setClearSessionKeys(List clearSessionKeys) {
        this.clearSessionKeys = clearSessionKeys;
    }

    /**
     * セッションクリアを行い、次画面へフォワードする。
     * <p>
     *  削除対象のキーが１つも見つからない場合は、
     *  遷移先情報を返却し、処理を終了する。
     * </p>
     * @param mapping アクションマッピング
     * @param form アクションフォーム
     * @param request <code>HTTP</code>リクエスト
     * @param response <code>HTTP</code>レスポンス
     * @return 遷移先情報
     */
    @Override
    public ActionForward doExecute(ActionMapping mapping,
                                     ActionForm form,
                                     HttpServletRequest request,
                                     HttpServletResponse response) {

        HttpSession session = request.getSession();

        if (clearSessionKeys != null) {
            for (int cnt = 0; cnt < clearSessionKeys.size(); cnt++) {
                String sKey = (String) clearSessionKeys.get(cnt);
                if (log.isDebugEnabled()) {
                    log.debug("removing [" + sKey + "] from HttpSession");
                }
                //セッションから削除を実行
                session.removeAttribute(sKey);
            }
        }

        // パラメータ属性（フォワード先）を取得
        String path = mapping.getParameter();

        if (path == null) {
            // パラメータ属性が設定されていない場合、(404)エラーを返却する
            try {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
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
