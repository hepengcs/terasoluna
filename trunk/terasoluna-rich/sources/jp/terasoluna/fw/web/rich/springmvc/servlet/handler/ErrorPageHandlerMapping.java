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

package jp.terasoluna.fw.web.rich.springmvc.servlet.handler;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.handler.BeanNameUrlHandlerMapping;

/**
 * リクエストが保持する例外の有無によってコントローラを返すハンドラ。
 * 
 * <p>Servlet内、もしくはFilterで例外が発生した場合に
 * コントローラを返す。
 * 返却するコントローラはBean定義ファイルに設定しておくこと。
 * コントローラのBean定義IDは{@link #beanId}で定義する。
 * デフォルト値は<code><pre>/exceptionController</pre></code>
 * となっている。</p>
 * 
 * <p>このクラスを使用するには、Bean定義ファイルに以下の設定が必要である。
 * 
 * <code><pre>
 * &lt;bean id="urlHandlerMapping" 
 *     class="jp.terasoluna.fw.web.rich.springmvc.servlet.handler.ErrorPageHandlerMapping"&gt;
 *   &lt;property name="order" value="1"/&gt;
 * &lt;/bean&gt;
 * </pre></code>
 * </p>
 */
public class ErrorPageHandlerMapping extends BeanNameUrlHandlerMapping {

    /**
     * HTTPリクエスト内で発生した例外を保持しているキー。
     */
    private static final String JAVAX_SERVLET_ERROR_EXCEPTION = 
        "javax.servlet.error.exception";

    /**
     * このハンドラが返すコントローラのBean定義ID。
     */
    private String beanId = DEFAULT_BEAN_ID;
    
    /**
     * このハンドラが返すデフォルトのコントローラのBean定義ID。
     */
    private static final String DEFAULT_BEAN_ID = "/exceptionController";
    
    /**
     * デフォルトコンストラクタ。
     */
    public ErrorPageHandlerMapping() {
        super();
    }

    /**
     * beanIdを返す。
     * @return コントローラのBean定義ID。
     */
    public String getBeanId() {
        return beanId;
    }

    /**
     * beanIdを設定する。
     * @param beanId コントローラのBean定義ID。
     */
    public void setBeanId(String beanId) {
        this.beanId = beanId;
    }

    /**
     * ハンドリング処理。
     * Servlet、Filterで例外が発生した場合に使用するコントローラを返す。
     * Servlet、Filterにて例外が発生していない場合、nullを返す。
     * @param request HttpServletRequest
     * @return Object コントローラ
     * @throws Exception 例外
     */
    @Override
    protected Object getHandlerInternal(HttpServletRequest request)
            throws Exception {
        Object exception = request.getAttribute(JAVAX_SERVLET_ERROR_EXCEPTION);
        
        if (exception != null && exception instanceof Exception) {
            return super.lookupHandler(beanId, request);
        }
        return null;
    }
}
