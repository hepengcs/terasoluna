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

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * RequestProcessorExTestクラスで利用する。
 * RequestProcessor#processActionForm()内でServletExceptionを
 * スローさせる。
 * その他のメソッドには、例外を発生させずに、processActionForm()まで
 * 処理が届くよう、何も行なわないメソッドでオーバライドしている。
 * 
 * @version 2004/03/18
 */
public class RequestProcessorEx_RequestProcessorExStub03 extends RequestProcessorEx {


    /**
     * ServletExceptionをスローする。
     * 
     * @param request
     * @param response
     * @return
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected ActionForward processActionPerform(HttpServletRequest request, HttpServletResponse response, Action action, ActionForm form, ActionMapping mapping)
                throws IOException,
                        ServletException {
        throw new ServletException();
    }

    /**
     * processActionFormまで例外を発生させないよう、オーバライドする。
     */
    @Override
    protected void processLocale(HttpServletRequest request,
                                 HttpServletResponse response) {
    }

    /**
     * processActionFormまで例外を発生させないよう、オーバライドする。
     */   
    @Override
    protected void processContent(HttpServletRequest request,
                                  HttpServletResponse response) {
    }

    /**
     * processActionFormまで例外を発生させないよう、オーバライドする。
     */
    @Override
    protected void processNoCache(HttpServletRequest request,
                                  HttpServletResponse response) {
    }
 
 
    /**
     * processActionFormまで例外を発生させないよう、オーバライドする。
     */
    @Override
    protected ActionMapping processMapping(HttpServletRequest request,
                                           HttpServletResponse response,
                                           String path)
        throws IOException {
            // ActionMappingEx を返却
            return new ActionMappingEx();
    }

    /**
     * processActionFormまで例外を発生させないよう、オーバライドする。
     */
    @Override
    protected boolean processRoles(HttpServletRequest request,
                                   HttpServletResponse response,
                                   ActionMapping mapping)
        throws IOException, ServletException {
            // trueを返却
            return true;
    }   

    /**
     * processActionFormまで例外を発生させないよう、オーバライドする。
     */
    @Override
    protected String processPath(HttpServletRequest request,
                                  HttpServletResponse response) {
            return "";                                  
    }

    /**
     * processActionFormまで例外を発生させないよう、オーバライドする。
     */
    @Override
    protected Action processActionCreate(HttpServletRequest request,
                                  HttpServletResponse response,
                                  ActionMapping mapping) {
            return new Action();                  
    }

    /**
     * processActionFormまで例外を発生させないよう、オーバライドする。
     */
    @Override
    protected void processPopulate(HttpServletRequest req,
                                 HttpServletResponse res,
                                 ActionForm form,
                                 ActionMapping mapping) {
                                    
    }
}
