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

package jp.terasoluna.fw.web.rich;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import jp.terasoluna.fw.web.rich.exception.ForbiddenURIException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanNotOfRequiredTypeException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
/**
 * 許可されているURI以外へのアクセスを禁止するフィルタ。
 *
 * <p>
 * ForbiddenURICheckerで許可されたURI以外は外部からアクセスできないようにする。
 * </p>
 *
 * <p>以下のように web.xmlにフィルタマッピング定義を行う。</p>
 * 
 * <p>
 * 【デプロイメントディスクリプタ（web.xml）の設定例】<br>
 * <code><pre>
 *   &lt;filter&gt;
 *     &lt;filter-name&gt;forbiddenURIFilter&lt;/filter-name&gt;
 *     &lt;filter-class&gt;jp.terasoluna.fw.web.rich.ForbiddenURIFilter&lt;/filter-class&gt;
 *   &lt;/filter&gt;
 *   &lt;filter-mapping&gt;
 *     &lt;filter-name&gt;forbiddenURIFilter&lt;/filter-name&gt;
 *     &lt;url-pattern&gt;/*&lt;/url-pattern&gt;
 *   &lt;/filter-mapping&gt;
 * </pre></code>
 * </p>
 * 
 * <p>
 * 許可URIのチェックのため、DIコンテナからForbiddenURICheckerを取得する。
 * 取得するBeanIDは”forbiddenURIChecker”とする。
 * ただしForbiddenURICheckerを取得する際に利用するBeanIDをカスタマイズしたい場合は、
 * フィルタの初期化パラメータ「”checkerBeanID”」に
 * ForbiddenURICheckerを取得するするためのBean名を記述することで直接指定することができる。
 * 通常は、initParamerの指定は不要である。
 * </p>
 * 
 * <p>
 * 【initParameterを利用したデプロイメントディスクリプタ（web.xml）の設定例】<br>
 * <code><pre>
 *   &lt;filter&gt;
 *     &lt;filter-name&gt;forbiddenURIFilter&lt;/filter-name&gt;
 *     &lt;filter-class&gt;jp.terasoluna.fw.web.rich.ForbiddenURIFilter&lt;/filter-class&gt;
 *     &lt;init-param&gt;
 *       &lt;param-name&gt;checkerBeanID&lt;/param-name&gt;
 *       &lt;param-value&gt;newChecker&lt;/param-value&gt;
 *     &lt;/init-param&gt;
 *   &lt;/filter&gt;
 *   &lt;filter-mapping&gt;
 *     &lt;filter-name&gt;forbiddenURIFilter&lt;/filter-name&gt;
 *     &lt;url-pattern&gt;/*&lt;/url-pattern&gt;
 *   &lt;/filter-mapping&gt;
 * </pre></code>
 * </p>
 *
 * <p>
 * リクエスト のURIが
 * アクセスを許可するURIではなかった場合には、
 * ForbiddenURIExceptionをスローする。
 * スローされた例外は、サーブレットのServletが提供しているエラーページの機能を利用すること。
 * SimpleMappingExceptionResolverExを参照のこと。
 * </p>
 * 
 * <p>
 * 【デプロイメントディスクリプタ（web.xml）の設定例】<br>
 * <code><pre>
 *   &lt;error-page&gt;
 *       &lt;exception-type&gt;jp.terasoluna.fw.web.rich.exception.ForbiddenURIException&lt;/exception-type&gt;
 *       &lt;location&gt;/error/forbidden-uri-error.jsp&lt;/location&gt;
 *   &lt;/error-page&gt;
 * 　　※ あらかじめ固定のエラー電文を記述した/error/forbidden-uri-error.jspを用意しておくこと。
 * </pre></code>
 * </p>
 *
 * <p>
 * 不正なアクセスを抑止するフィルタであるため、
 * 出来るだけ早いタイミングで最初に実行されるように設定することが望ましい。
 * </p>
 *
 */
public class ForbiddenURIFilter implements Filter {
    
    /**
     * ログクラス。
     */
    private static Log log = LogFactory.getLog(ForbiddenURIFilter.class);
    
    /**
     * DIコンテナから禁止URIチェッカを取得する際のBeanIDのデフォルト値。
     */
    private static final String DEFAULT_CHECKER_BEANID = 
        "forbiddenURIChecker";
    
    /**
     * 初期化パラメータから禁止URIチェッカのBeanIDを取得するためのキー。
     */
    private static final String INITPARAM_KEY_CHECKER_BEANID = 
        "checkerBeanID";

    /**
     * 禁止URIチェッカ。
     */
    private ForbiddenURIChecker checker = null;

    /**
     * フィルタの初期化を行う。
     * 禁止URIチェッカをDIコンテナより取得する。
     * 
     * @param config フィルタ設定
     * @throws ServletException サーブレット例外
     */
    public void init(FilterConfig config) throws ServletException {
        String checkerBeanID
            = config.getInitParameter(INITPARAM_KEY_CHECKER_BEANID);
        if (checkerBeanID == null) {
            checkerBeanID = DEFAULT_CHECKER_BEANID;
        }
        // コンテキストの取得
        ApplicationContext context 
            = WebApplicationContextUtils.getWebApplicationContext(
                config.getServletContext());
        try {
            this.checker = 
                (ForbiddenURIChecker) context.getBean(
                        checkerBeanID,
                        ForbiddenURIChecker.class);
        } catch (BeanNotOfRequiredTypeException e) {
            log.error("the bean is not of the required type"
                    + " - ForbiddenURIChecker.");
            throw e;
        } catch (NoSuchBeanDefinitionException e) {
            log.error("there's no such bean definition. BeanID="
                    + checkerBeanID + ".");
            throw e;
        } catch (BeansException e) {
            log.error("the bean could not be created. BeanID="
                    + checkerBeanID + ".");
            throw e;
        }
    }

    /**
     * フィルタ処理する。 禁止されたURIかチェックする。
     * 
     * @param req HTTPリクエスト
     * @param res HTTPレスポンス
     * @param chain チェインされたフィルタ
     * @throws IOException 入出力例外
     * @throws ServletException サーブレット例外
     */
    public void doFilter(
            ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        // Requestに設定されているURI取得
        String requestURI =
            ((HttpServletRequest) req).getRequestURI().replaceFirst(
                    ((HttpServletRequest) req).getContextPath(), "");
        // 禁止されたURIかチェック
        if (!checker.isAllowedURI(requestURI)) {
            log.error("request url is forbidden!");
            // 禁止されているURIだった例外スロー
            throw new ForbiddenURIException();
        }
        chain.doFilter(req, res);
    }

    /**
     * フィルタの破棄処理を行う。
     * なにもしない。
     */
    public void destroy() {
    }
}
