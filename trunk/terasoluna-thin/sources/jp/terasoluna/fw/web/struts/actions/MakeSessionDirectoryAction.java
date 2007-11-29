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
import jp.terasoluna.fw.util.FileUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <p>セッションディレクトリを作成するアクション。</p>
 *
 * <p>
 * サーバサイドで生成されたPDFファイルなどを格納するための一時ディレクトリ
 * （以降、セッションディレクトリ）をログオンユーザ毎に作成する。<br>
 * <br>
 * この機能を使うためにはシステム設定プロパティファイル（system.properties）
 * にセッションディレクトリベースパスを記述しておく必要がある。<br>
 * <br>
 * <h5>システム設定プロパティファイル（system.properties）の設定</h5>
 * <code><pre><strong>session.dir.base=/tmp/sessions</strong></pre></code>
 * セッションディレクトリの作成には以下の二つの方法で利用可能である。<br>
 * <br>
 * （１）{@link MakeSessionDirectoryAction}クラスの利用<br>
 * ログオンに成功したらMakeSessionDirectoryActionに遷移する。<br>
 * struts-config.xmlでactionプロパティのparameter属性に指定した先にフォワードする。<br>
 * struts-config.xml及びBean定義ファイルの設定は下記のとおりである。
 * <h5>struts-config.xmlの設定</h5>
 * <code><pre>
 * &lt;action path="<strong>/makeSessionDir</strong>"
 *     scope="session"
 *     parameter="/foo.jsp"&gt;;
 * &lt;/action&gt;
 * </pre></code>
 * <h5>Bean定義ファイルの設定</h5>
 * <code><pre>
 * &lt;bean name="<strong>/makeSessionDir</strong>" scope="prototype"
 *   class="<strong>jp.terasoluna.fw.web.struts.actions.MakeSessionDirectoryAction</strong>"&gt;
 * &lt;/bean&gt;
 * </pre></code>
 *
 * （２）アクションクラスで直接セッションディレクトリ作成<br>
 * MakeSessionDirectoryAction以外のアクションでは、ユーザログイン後に
 * {@link javax.servlet.http.HttpSession}が取得可能な場所で
 * FileUtil#makeSessionDirectory(String sessionId)を呼び出す必要がある。<br>
 * <br>
 * <br>
 * セッションディレクトリの削除は以下の方法で利用可能である。<br>
 * <br>
 * （１）{@link javax.servlet.http.HttpSessionListener}実装クラスの利用<br>
 * デプロイメントディスクリプタ（web.xml）に
 * HttpSessionListener実装クラスをリスナとして登録し、
 * セッションが破棄されたときセッションディレクトリを削除する処理
 * （FileUtil#removeSessionDirectory(String sessionId)）を
 * リスナに実装する。<br>
 * web.xmlの設定は下記のとおりである。<br>
 * <h5>デプロイメントディスクリプタ（web.xml）の設定</h5>
 * <code><pre>
 * &lt;web-app&gt;
 *   ・・・
 *   &lt;listener&gt;
 *     &lt;listener-class&gt;
 *       <strong>jp.terasoluna.fw.web.MyHttpSessionListener</strong>
 *     &lt;/listener-class&gt;
 *   &lt;/listener&gt;
 * &lt;/web-app&gt;
 * </pre></code>
 * </p>
 *
 */
public class MakeSessionDirectoryAction extends ActionEx {

    /**
     * ログクラス。
     */
    private static Log log =
        LogFactory.getLog(MakeSessionDirectoryAction.class);

    /**
     * セッション取得失敗を示すエラーコード。
     */
    private static final String SESSION_NOT_FOUND_ERROR =
        "error.session.not.found";
    
    /**
     * エラーページ（404）遷移失敗を示すエラーコード。
     */
    private static final String FORWARD_ERRORPAGE_ERROR = 
        "error.forward.errorpage";

    /**
     * <p>
     *  セッションディレクトリを生成する。<br>
     *  セッションが取得できた場合、セッションディレクトリを作成した後
     *  パラメータ属性に設定された遷移先をアクションフォワードにセットして返す。<br>
     *  パラメータ属性が設定されていない場合、(404)エラーを返す。<br>
     *  セッションが取得できなかった場合、システム例外を起こす。
     * </p>
     * @param mapping アクションマッピング
     * @param form アクションフォーム
     * @param req HTTPリクエスト
     * @param res HTTPレスポンス
     * @return 遷移先のアクションフォワード。
     */
    @Override
    public ActionForward doExecute(ActionMapping mapping, ActionForm form,
            HttpServletRequest req, HttpServletResponse res) {

        if (log.isDebugEnabled()) {
            log.debug("doExecute() called.");
        }

        // 利用中のセッションがなかったら例外を起こす
        HttpSession session = req.getSession(false);
        if (session == null) {
            log.error("HttpSession is not available.");
            throw new SystemException(null, SESSION_NOT_FOUND_ERROR);
        }

        // セッションIDを取得し、セッションディレクトリを生成する。
        FileUtil.makeSessionDirectory(session.getId());

        // パラメータ属性（フォワード先）を取得
        String path = mapping.getParameter();

        if (path == null) {
            // パラメータ属性が設定されていない場合、(404)エラーを返却する
            try {
                res.sendError(HttpServletResponse.SC_NOT_FOUND);
            } catch (IOException e) {
                log.error("Error page(404) forwarding failed.");
                throw new SystemException(e, FORWARD_ERRORPAGE_ERROR);
            }
            return null;
        }

        // 次の画面遷移
        ActionForward retVal = new ActionForward(path);
        return retVal;
    }
}
