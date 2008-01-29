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

package jp.terasoluna.fw.web.rich.springmvc.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * ServletやFilter内で発生した例外など、
 * web.xmlのerror-page設定による例外ハンドリングを
 * 行うときに使用するコントローラ。
 * 
 * <p>このクラスではServletやFilterで発生した例外をスローし、
 * Spring-MVCの例外ハンドリング機能に処理を投げることを責務とする。
 * スローした例外は、{@link jp.terasoluna.fw.web.rich.springmvc.servlet.handler.SimpleMappingExceptionResolverEx}
 * で適切にハンドリングされることを想定している。</p>
 * 
 * <p>Bean定義ファイルに以下の設定をすること。</p>
 * 
 * <hr>
 * <p>
 *  【<code>Bean定義ファイル</code>の設定例】<br>
 *  <code><pre>
 *  &lt;bean name="/exceptionController" 
        class="jp.terasoluna.fw.web.rich.springmvc.controller.ErrorPageHandlingController"/&gt;
 * </pre></code>
 * </p>
 * <hr>
 * 
 * また、このクラスを使用するには
 * {@link jp.terasoluna.fw.web.rich.springmvc.servlet.handler.ErrorPageHandlerMapping}
 * クラスをBean定義ファイルに設定しておく必要がある。詳細は
 * {@link jp.terasoluna.fw.web.rich.springmvc.servlet.handler.ErrorPageHandlerMapping}
 * クラスのJavaDocを参照すること。
 * 
 * @see jp.terasoluna.fw.web.rich.springmvc.servlet.handler.ErrorPageHandlerMapping
 * @see jp.terasoluna.fw.web.rich.springmvc.servlet.handler.SimpleMappingExceptionResolverEx
 * 
 */
public class ErrorPageHandlingController implements Controller {

    /**
     * HTTPリクエスト内で発生した例外を保持しているキー。
     */
    private static final String JAVAX_SERVLET_ERROR_EXCEPTION = 
        "javax.servlet.error.exception";

    /**
     * リクエスト処理を行う。
     * リクエストが保持している例外をスローする。
     * リクエストに例外がない場合、{@link java.lang.IllegalArgumentException}
     * がスローされる。
     * 
     * @param request HTTPリクエスト。
     * @param response HTTPレスポンス。
     * @return レスポンスをレンダリングするためのModelAndView。
     * @throws Exception 例外。
     */
    public ModelAndView handleRequest(
            HttpServletRequest request, 
            HttpServletResponse response) throws Exception {
        Exception e = 
            (Exception) request.getAttribute(JAVAX_SERVLET_ERROR_EXCEPTION);
        
        if (e == null) {
            String message = "ErrorPageHandlingController must be set "
                + "Exception in Request Data.";
            throw new IllegalStateException(message);
        }
        throw e;
    }
}
