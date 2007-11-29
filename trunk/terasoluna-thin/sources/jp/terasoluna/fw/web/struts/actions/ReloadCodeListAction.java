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
import jp.terasoluna.fw.web.codelist.ReloadableCodeListLoader;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * キャッシュされているコードリストを再読み込みするアクション。
 *
 * <p>
 * 再読み込みを行う ReloadableCodeListLoader を
 * setCodeListLoaderメソッドで設定することによって、
 * このアクションが実行されるとそのReloadableCodeListLoader が持つ
 * reload() メソッドを呼び出し、コードリストの再読み込みを行う。
 * コードリスト再読み込みを実行後、Struts設定ファイル(struts-config.xml) で
 * action プロパティの parameter 属性に指定した先にフォワードする。
 * フォワード先が設定されていない場合、
 * SC_NOT_FOUND（404） エラーを返す。
 * </p>
 *
 * <strong>Bean定義ファイルの設定例。</strong><br>
 * 以下は ReloadableCodeListLoader として &quot;loader1&quot; が
 * 定義してある場合の例である。
 * <code><pre>
 * &lt;bean name=<Strong>"/reloadAction"</Strong> scope="prototype"
 *       class = "jp.terasoluna.fw.web.struts.actions.ReloadCodeListAction"&gt;
 *   <Strong>&lt;property name="codeListLoader"&gt;
 *     &lt;ref bean="loader1"/&gt;
 *   &lt;/property&gt;</Strong>
 * &lt;/bean&gt;
 * </pre></code>
 * <strong> Struts設定ファイル(struts-config.xml) 設定例</strong><br>
 * <code><pre>
 *  &lt;action path=<Strong>"/reloadAction"</Strong>
 *          name="_sampleFormBean"
 *          parameter = "/reloaded.do"/&gt;
 * </pre></code></p>
 * <p>
 *
 * ReloadableCodeListLoader そのものを再生成する機能ではないため、
 * その中身の SQL 文などを変更することは不可能である。<br>
 *
 * 再読み込み可能なコードリストの生成については、
 * ReloadableCodeListLoader を参照。<br>
 * またJSP内のコードリストの使用方法については、DefineCodeListTag、
 * DefineCodeListCountTag を参照。
 * </p>
 *
 * @see jp.terasoluna.fw.web.codelist.ReloadableCodeListLoader
 * @see jp.terasoluna.fw.web.codelist.DBCodeListLoader
 * @see jp.terasoluna.fw.web.taglib.DefineCodeListTag
 * @see jp.terasoluna.fw.web.taglib.WriteCodeCountTag
 *
 */
public class ReloadCodeListAction extends ActionEx {

    /**
     * ログクラス。
     */
    private static Log log = LogFactory.getLog(ReloadCodeListAction.class);

    /**
     * エラーページ（404）遷移失敗を示すエラーコード。
     */
    private static final String FORWARD_ERRORPAGE_ERROR =
        "error.forward.errorpage";

    /**
     * 再読み込みを実施するために使用する ReloadableCodeListLoader。
     */
    private ReloadableCodeListLoader codeListLoader = null;

    /**
     * キャッシュされているコードリストを再読込みする。
     *
     * <p>
     *  この処理実行後、常に parameter 属性に指定した先にフォワードする。
     *  parameter 属性が指定されていなかったり、codeListLoaderが存在しない場合
     * SC_NOT_FOUND（404） エラーを返す。
     * </p>
     *
     * @param mapping アクションマッピング
     * @param form アクションフォーム
     * @param req HTTPリクエスト
     * @param res HTTPレスポンス
     * @return parameter 属性に指定した遷移先情報
     */
    @Override
    public ActionForward doExecute(ActionMapping mapping,
                                        ActionForm form,
                                        HttpServletRequest req,
                                        HttpServletResponse res) {

        if (log.isDebugEnabled()) {
            log.debug("doExecute() called.");
        }

        if (codeListLoader != null) {
            codeListLoader.reload();
        }

        String path = mapping.getParameter();

        if (path == null) {
            // パラメータ属性が設定されていない場合、（404）エラーを返却する。
            try {
                res.sendError(HttpServletResponse.SC_NOT_FOUND);
            } catch (IOException e) {
                log.error("Error page(404) forwarding failed.");
                throw new SystemException(e, FORWARD_ERRORPAGE_ERROR);
            }
            return null;
        }

        return new ActionForward(path);
    }

    /**
     * codeListLoader を取得する。
     *
     * @return codeListLoader を表すフィールド値。
     */
    public ReloadableCodeListLoader getCodeListLoader() {
        return codeListLoader;
    }

    /**
     * codeListLoader を設定する。
     *
     * @param codeListLoader codeListLoader を表すフィールド値。
     */
    public void setCodeListLoader(ReloadableCodeListLoader codeListLoader) {
        this.codeListLoader = codeListLoader;
    }
}
