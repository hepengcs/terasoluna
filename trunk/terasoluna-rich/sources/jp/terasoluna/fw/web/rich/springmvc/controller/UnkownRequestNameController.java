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

import jp.terasoluna.fw.web.rich.springmvc.exception.UnknownRequestNameException;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * 不正なリクエスト名がリクエストされてきた場合に実行されるリクエストコントローラ。
 * 
 * <p>
 * 必ずUnkownRequestNameExceptionをスローする。
 * 
 * BeanNameUrlHandlerMappingExでリクエスト名に対応するリクエストコントローラが
 * 存在しなかった場合に実行されることを想定している。
 * 
 * スローした例外は、SimpleMappingExceptionResolverExで
 * 適切にハンドリングされることを想定している。
 * </p>
 * 
 * <p>
 * Bean定義ファイルに以下の設定をすること。
 * </p>
 * 
 * <p>
 *  【<code>Bean定義ファイル</code>の設定例】<br>
 *  <code><pre>
 *  &lt;bean id="unkownRequestNameController"
 *      class="jp.terasoluna.fw.web.rich.springmvc.controller.UnkownRequestNameController"/&gt;
 * </pre></code>
 * </p>
 * 
 * <p>
 * また、ハンドラマッピングのBean定義の際、プロパティdefaultHandlerに上記Beanを設定すること。
 * 詳細は、BeanNameUrlHandlerMappingExを参照のこと。
 * </p>
 * 
 * <p>
 * さらに、handlerExceptionResolverのBean定義の際に
 * UnknownRequestNameExceptionがスローされた場合の処理を定義すること。
 * 詳細は、SimpleMappingExceptionResolverExを参照のこと。
 * </p>
 * 
 * @see jp.terasoluna.fw.web.rich.springmvc.servlet.handler.BeanNameUrlHandlerMappingEx
 * @see jp.terasoluna.fw.web.rich.springmvc.servlet.handler.SimpleMappingExceptionResolverEx
 * 
 */
public class UnkownRequestNameController implements Controller {

    /**
     * リクエスト処理を行う。
     * 必ずUnknownRequestNameExceptionをスローする。
     * 
     * @param request HTTPリクエスト。
     * @param response HTTPレスポンス。
     * @return レスポンスをレンダリングするためのModelAndView。
     * @throws Exception 例外。
     */
    public ModelAndView handleRequest(
            HttpServletRequest request, 
            HttpServletResponse response) throws Exception {
        throw new UnknownRequestNameException();
    }
}
