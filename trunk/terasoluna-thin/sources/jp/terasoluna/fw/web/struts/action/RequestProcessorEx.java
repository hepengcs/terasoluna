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

package jp.terasoluna.fw.web.struts.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jp.terasoluna.fw.util.ExceptionUtil;
import jp.terasoluna.fw.web.RequestUtil;
import jp.terasoluna.fw.web.struts.form.ActionFormUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.struts.DelegatingRequestProcessor;

/**
 * DelegatingRequestProcessor拡張クラス。
 * <p>
 *  クライアントからのリクエストを捕捉し、URIに応じたアクションの起動などを行う。
 *  org.springframework.web.struts.DelegatingRequestProcessorを継承して、
 *  以下の機能を追加している。
 * </p>
 *
 * <ol>
 * <li>&quot;_&quot;で始まる
 *  アクションフォーム名のセッション内唯一性の保証</li>
 * <p>
 *  セッションスコープのアクションフォーム論理名が
 *  &quot;_&quot;で始まっていた場合、
 *  インスタンスを作成するときに、セッションに格納されている
 *  他のアクションフォームの論理名が、&quot;_&quot;
 *  で始まるものはすべて削除される。
 *  これにより、セッションでは最大でも1つのアクションフォー
 *  ムだけ生成されることが保証され、セッションスコープの
 *  アクションフォーム削除処理を記述しなくても、
 *  実用上問題ないメモリ使用量に押さえることができる。
 * </p>
 * <h5>struts-config.xmlのアクションフォーム指定例</h5>
 * <code><pre>
 * &lt;struts-config&gt;
 * &lt;form-beans&gt;
 * &lt;!-- フォームの先頭に"_"をつける  --&gt;
 * &lt;form-bean name="_sampleForm"
 * type="jp.terasoluna.fw.web.struts.form.DynaValidatorActionFormEx"
 * &gt;
 * &lt;form-property name="companyId" type="java.lang.String" /&gt;
 * 　　　　　・・・
 * &lt;/struts-config&gt;
 * </pre></code>
 *
 *
 * <li>フォーム反映抑止機能</li>
 * <p>
 *  ビジネスロジック実行結果を反映したアクションフォームから
 *  フォームが切り替わっていない場合は、processPopulate()をスキップする。
 * </p>
 *
 * <li>アクションフォームのエラー保持</li>
 * <p>
 *  入力チェックエラーがあった場合は、ポップアップ画面にエラーを
 *  表示させるため、アクションフォームにアクションエラーを保存する。
 *  エラーが発生していないときは、空のアクションエラーをアクション
 *  フォームに設定する。
 * </p>
 *
 * <li>例外発生時のログ出力機能</li>
 * <p>
 *  RequestProcessor#process()内で例外がスローされた時、
 *  例外スタックトレースをログに出力する。
 *  ログ出力後、発生した例外はServletExceptionにラップされ、スローされる。
 * </p>
 * </ol>
 *
 * <p>
 *  RequestProcessorExを利用するためには、struts-config.xmlの
 *  &lt;controller&gt;要素のprocessorClass属性に設定が必要である。
 * <h5>struts-config.xmlの設定例</h5>
 * <code><pre>
 * &lt;struts-config&gt;
 *   ・・・
 *   &lt;controller processorClass=
 *       "jp.terasoluna.fw.web.struts.RequestProcessorEx"/&gt;
 *   ・・・
 * &lt;/struts-config&gt;
 * </pre></code>
 * フォーム抑止機能の詳細については、ActionExを参照のこと。
 * </p>
 *
 * @see jp.terasoluna.fw.web.struts.actions.ActionEx
 * @see jp.terasoluna.fw.web.struts.form.FormEx
 *
 */
public class RequestProcessorEx extends DelegatingRequestProcessor {

    /**
     * ログクラス。
     */
    @SuppressWarnings("hiding")
	private static Log log
            = LogFactory.getLog(RequestProcessorEx.class);

    /**
     * processPopulate()をスキップするフラグをリクエストに設定するときのキー。
     */
    public static final String SKIP_POPULATE = "SKIP_POPULATE";

    /**
     * StrutsのHTTPリクエスト処理を拡張する。
     *
     * <p>StrutsのActionServletのprocess()メソッドでスローされた例外は
     * SystemExceptionを含めてこのメソッドでキャッチされる。
     * そして、例外の内容とセッションIDのハッシュ値がログ出力された後、
     * ServletExceptionにラップされてスローされる。</p>
     *
     * @param req HTTPリクエスト
     * @param res HTTPレスポンス
     * @throws IOException IO例外
     * @throws ServletException サーブレット例外
     */
    @Override
    public void process(HttpServletRequest req, HttpServletResponse res)
            throws IOException, ServletException {
        if (log.isDebugEnabled()) {
            log.debug("process() called.");
        }

        try {
            // スーパークラスの process() を実行する
            super.process(req, res);
        } catch (IOException e) {
            String sessionHash = RequestUtil.getSessionHash(req);
            log.error("sessionHash = " + sessionHash);
            log.error(ExceptionUtil.getStackTrace(e));
            throw e;
        } catch (ServletException e) {
            String sessionHash = RequestUtil.getSessionHash(req);
            log.error("sessionHash = " + sessionHash);
            log.error(ExceptionUtil.getStackTrace(e));
            throw e;
        } catch (Exception e) {
            String sessionHash = RequestUtil.getSessionHash(req);
            log.error("sessionHash = " + sessionHash);
            log.error(ExceptionUtil.getStackTrace(e));
            throw new ServletException(e);
        }

        if (log.isDebugEnabled()) {
            log.debug("process() finished.");
        }
    }

    /**
     * Strutsのアクションフォーム取得処理を拡張する。
     *
     * <p>アクションマッピングで指定されたアクションフォーム名が、
     * "_"で始まってるアクションフォームの場合は、
     * processActionFormEx()に処理を委譲する。</p>
     *
     * @param req HTTPリクエスト
     * @param res HTTPレスポンス
     * @param mapping アクションマッピング
     * @return ActionForm アクションフォーム
     */
    @Override
    protected ActionForm processActionForm(HttpServletRequest req,
                                           HttpServletResponse res,
                                           ActionMapping mapping) {
        if (log.isDebugEnabled()) {
            log.debug(
                "processActionForm() called. path = " + mapping.getPath());
            log.debug("mapping.name = " + mapping.getName());
            log.debug("mapping.scope = " + mapping.getScope());
        }

        if (mapping instanceof ActionMappingEx
                && mapping.getScope() != null
                && "session".equals(mapping.getScope())
                && mapping.getName() != null
                && mapping.getName().startsWith("_")) {

            // スコープが"session"、かつアクションフォーム論理名が"_"で
            // 始まっている場合は、processActionFormEx() を実行する
            return processActionFormEx(req,
                res,
                (ActionMappingEx) mapping);
        }
		// スーパークラスの processActionForm() を実行する
		return super.processActionForm(req, res, mapping);
    }

    /**
     * Strutsのアクションフォーム取得処理を拡張する。
     * processActionForm()から呼ばれる。
     *
     * <p>アクションマッピングで指定されたアクションフォーム名が、
     * "_"で始まってるアクションフォームを処理する。</p>
     *
     * <p>アクションフォームの処理後、
     * RequestProcessorのprocessPopulate()メソッドを起動する。</p>
     *
     * @param req HTTPリクエスト
     * @param res HTTPレスポンス
     * @param mapping アクションマッピング
     * @return アクションフォーム
     */
    protected ActionForm processActionFormEx(HttpServletRequest req,
                                             HttpServletResponse res,
                                             ActionMappingEx mapping) {
        if (log.isDebugEnabled()) {
            log.debug(
                "processActionFormEx() called. path = " + mapping.getPath());
        }

        boolean clearForm = false;
        // アクションマッピングのclearFormプロパティを参照する。
        clearForm = mapping.getClearForm();
        if (log.isDebugEnabled()) {
            log.debug("clearForm = " + clearForm);
        }

        if (clearForm) {
            // clearFormプロパティがtrueのときは、
            // "_"指定されたアクションフォームを、
            // セッションからすべて削除する。
            HttpSession session = req.getSession();
            ActionFormUtil.clearActionForm(session);
        } else {
            // clearFormプロパティがfalseのときは、
            // "_"で格納されたアクションフォームを、
            // セッションから削除する。
            // ただし、指定されたアクションフォームを除く。
            HttpSession session = req.getSession();
            ActionFormUtil.clearActionForm(session, mapping.getName());
        }

        // スーパークラスの processActionForm() を実行する
        return super.processActionForm(req, res, mapping);
    }

    /**
     * Strutsのアクションフォームへのリクエストパラメータ反映処理を拡張する。
     *
     * <p>ビジネスロジック実行結果を反映したアクションフォームから
     * 切り換わっている場合は、
     * RequestProcessorのprocessPopulate()メソッドを起動する。</p>
     * <p>struts-config.xmlのアクションマッピング設定時、
     * cancelPopulateをtrue にした場合、
     * processPopulate がキャンセルされる。</p>
     *
     * @param req HTTPリクエスト
     * @param res HTTPレスポンス
     * @param form アクションフォーム
     * @param mapping アクションマッピング
     * @throws ServletException サーブレット例外
     */
    @Override
    protected void processPopulate(HttpServletRequest req,
                                   HttpServletResponse res,
                                   ActionForm form,
                                   ActionMapping mapping)
            throws ServletException {

        if (log.isDebugEnabled()) {
            log.debug(
                "processPopulate() called. path = " + mapping.getPath());
        }

        boolean cancelPopulateflg  = false;
        cancelPopulateflg = ((ActionMappingEx) mapping).getCancelPopulate();
        if (cancelPopulateflg) {
            if (log.isDebugEnabled()) {
                log.debug("processPopulate() canceled.");
            }
            // 以降は実行しない
            return;
        }

        String skipPopulate = (String) req.getAttribute(SKIP_POPULATE);
        if (skipPopulate != null) {
            if (skipPopulate.equals(mapping.getName())) {

                if (log.isDebugEnabled()) {
                    log.debug("processPopulate() skipped.");
                }
                // 以降は実行しない
                return;
            }
			req.removeAttribute(SKIP_POPULATE);
        }
        // スーパークラスの processPopulate() を実行する
        super.processPopulate(req, res, form, mapping);

    }
}
