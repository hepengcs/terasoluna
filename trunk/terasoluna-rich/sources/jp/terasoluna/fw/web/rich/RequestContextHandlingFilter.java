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

import jp.terasoluna.fw.web.rich.context.support.RequestContextSupport;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * 制御情報の生成・破棄を行うサーブレットフィルタ。
 * 
 * <p>
 * リクエスト処理開始時に制御情報を生成し、リクエスト処理終了時に破棄する。
 * 制御情報の生成処理後チェインされた処理の実行中に例外が発生しても、必ず制御情報は破棄される。
 *
 * 制御情報の詳細は、RequestContextSupportを参照のこと。
 * </p>
 * 
 * <p>
 * 本サーブレットフィルタを利用するには、web.xmlの設定が必要である。
 * </p>
 * 
 * <p>
 * 【デプロイメントディスクリプタ（web.xml）の設定例】<br>
 * <code><pre>
 *  &lt;filter&gt;
 *   &lt;filter-name&gt;requestContextHandlingFilter&lt;/filter-name&gt;
 *   &lt;filter-class&gt;
 *     jp.terasoluna.fw.web.rich.RequestContextHandlingFilter
 *   &lt;/filter-class&gt;
 *  &lt;/filter&gt;
 *  &lt;filter-mapping&gt;
 *    &lt;filter-name&gt;requestContextHandlingFilter&lt;/filter-name&gt;
 *    &lt;url-pattern&gt;/*&lt;/url-pattern&gt;
 *  &lt;/filter-mapping&gt;
 * </pre></code>
 * </p>
 * 
 * <p>
 * 制御情報の取得のため、DIコンテナから制御情報サポートクラスを取得する。
 * 取得するBeanIDは”ctxSupport”とする。
 * ただしRequestContextSupportを取得する際に利用するBeanIDをカスタマイズしたい場合は、
 * フィルタの初期化パラメータ「ctxSupportBeanID」に
 * RequestContextSupportを取得するためのBean名を記述することで直接指定することができる。
 * 通常は、初期化パラメータの指定は不要である。
 * </p>
 * 
 * <p>
 * 【initParameterを利用したデプロイメントディスクリプタ（web.xml）の設定例】<br>
 * <code><pre>
 *  &lt;filter&gt;
 *     &lt;filter-name&gt;requestContextHandlingFilter&lt;/filter-name&gt;
 *     &lt;filter-class&gt;
 *       jp.terasoluna.fw.web.rich.RequestContextHandlingFilter
 *     &lt;/filter-class&gt;
 *     &lt;init-param&gt;
 *       &lt;param-name&gt;ctxSupportBeanID&lt;/param-name&gt;
 *       &lt;param-value&gt;newCtxSupport&lt;/param-value&gt;
 *     &lt;/init-param&gt;
 *   &lt;/filter&gt;
 *   &lt;filter-mapping&gt;
 *     &lt;filter-name&gt;requestContextHandlingFilter&lt;/filter-name&gt;
 *     &lt;url-pattern&gt;/*&lt;/url-pattern&gt;
 *   &lt;/filter-mapping&gt;
 * </pre></code>
 * </p>
 * 
 * @see jp.terasoluna.fw.web.rich.context.support.RequestContextSupport
 * 
 */
public class RequestContextHandlingFilter implements Filter {
    
    /**
     * DIコンテナからRequestContextSupportを取得する際のBeanIDのデフォルト値。
     */
    private static final String DEFAULT_CTXSUPPORT_BEANID = "ctxSupport";
    
    /**
     * 初期化パラメータからRequestContextSupportのBeanIDを取得するためのキー。
     */
    private static final String INITPARAM_KEY_CTXSUPPORT_BEANID
                                                     = "ctxSupportBeanID";
    
    /**
     * RequestContextSupport。
     */
    private RequestContextSupport ctxSupport = null;

    /**
     * フィルタの初期化を行う。
     * RequestContextSupportをDIコンテナより取得する。
     * 
     * @param config フィルタ設定
     * @throws ServletException サーブレット例外
     */
    public void init(FilterConfig config) throws ServletException {
        String ctxSupportBeanID
            = config.getInitParameter(INITPARAM_KEY_CTXSUPPORT_BEANID);
        if (ctxSupportBeanID == null) {
            ctxSupportBeanID = DEFAULT_CTXSUPPORT_BEANID;
        }
        // コンテキストの取得
        ApplicationContext context 
            = WebApplicationContextUtils.getWebApplicationContext(
                config.getServletContext());
        this.ctxSupport
            = (RequestContextSupport) context.getBean(ctxSupportBeanID);
    }

    /**
     * フィルタ処理する。
     * 制御情報の登録・破棄を行う。
     * 
     * @param request HTTPリクエスト
     * @param response HTTPレスポンス
     * @param chain チェインされたフィルタ
     * @throws IOException 入出力例外
     * @throws ServletException サーブレット例外
     */
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        try {
            // コンテキストの生成
            ctxSupport.generateContext((HttpServletRequest) request);
            chain.doFilter(request, response);
        } finally {
            // コンテストの削除
            ctxSupport.destroyContext();
        }
    }

    /**
     * フィルタの破棄処理を行う。
     * なにもしない。
     */
    public void destroy() {
    }
}
