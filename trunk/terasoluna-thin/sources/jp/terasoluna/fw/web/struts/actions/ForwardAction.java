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

import jp.terasoluna.fw.exception.SystemException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * 単純フォワードアクション。
 *
 * <p>
 *  ActionExの機能（遷移ログ出力・トランザクショントークンチェック）を継承し、
 *  JSPなどへフォワードするアクションである。
 *  Strutsが提供しているForwardActionと同様に
 *  struts-config.xmlで&lt;action&gt;要素の
 *  parameter属性に指定した先にフォワードする。
 *  parameter属性が設定されていない場合、
 *  固定の論理フォワード名「success」でアクションフォワードを取得する。
 *  フォワード先が設定されていない場合、
 *  SC_NOT_FOUND（404）エラーを返す。
 *  *.jspファイルへの直接アクセスが禁止されている場合に、
 *  JSPを業務処理を経ず単純に表示するには
 *  このアクションを用いてstruts-config.xml
 *  にエントリを作成する必要がある。
 *  ActionEx#execute()で行われている処理は、
 *  ここでも継承される。
 * </p>
 * <p>
 *  parameter属性では、モジュール相対パスの指定しか行なえない。
 *  モジュールを跨る遷移やリダイレクトを行ないたい場合は、
 *  &lt;forward&gt;要素を使用すること。
 * </p>
 *
 * <p>Bean定義ファイル及びstruts-config.xmlの記述例を以下に示す。</p>
 *
 * 例:<br>
 * <p>
 * <strong>Bean定義ファイルの設定</strong>
 *  <code><pre>
 *  &lt;bean name="/foo" scope="prototype"
 *      <strong>class="jp.terasoluna.fw.web.struts.actions.ForwardAction"</strong>&gt
 *  &lt;/bean&gt
 *  </pre></code>
 * </p>
 * <p>
 * <strong>struts-config.xmlの設定</strong>
 * <code><pre>
 *  &lt;action path="/foo"
 *          parameter="/foo.jsp"&gt;
 *  &lt;/action&gt;
 * </pre></code>
 * または
 * <code><pre>
 *  &lt;action path="/foo"
 *          parameter="/foo.jsp"&gt;
 *    &lt;forward name="success" path="/foo.jsp" module="/sub1" redirect="true"&gt;
 *  &lt;/action&gt;
 * </pre></code>
 * </p>
 *
 */
public class ForwardAction extends ActionEx {

    /**
     * ログクラス。
     */
    private static Log log
              = LogFactory.getLog(ForwardAction.class);

    /**
     * エラーページ（404）遷移失敗を示すエラーコード。
     */
    private static final String FORWARD_ERRORPAGE_ERROR =
        "error.forward.errorpage";

    /**
     * 固定の論理フォワード名。
     */
    private static final String FORWARD_SUCCESS = "success";

    /**
     * parameter属性に設定された遷移先を
     * アクションフォワードにセットして返す。
     * parameter属性が設定されていない場合、
     * 遷移先論理フォワード名"success"で
     * アクションフォワードを取得して返す。
     * どちらも設定されていない場合は、（404）エラーを返す。
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

        // parameter属性（フォワード先）を取得
        String path = mapping.getParameter();

        // アクションフォワードを生成
        ActionForward retVal = null;

        if (path == null) {

            // ActionMappingからActionForwardを取得
            retVal = mapping.findForward(FORWARD_SUCCESS);

            // ActionFowardが設定されていない場合
            if (retVal == null) {
                // parameter属性、forward要素がともに設定されていない場合、
                // （404）エラーを返却する
                try {
                    res.sendError(HttpServletResponse.SC_NOT_FOUND);
                } catch (IOException e) {
                    log.error("Error page(404) forwarding failed.");

                    throw new SystemException(e, FORWARD_ERRORPAGE_ERROR);
                }
                return null;
            }
			return retVal;
        }

        retVal = new ActionForward(path);

        return retVal;
    }
}
