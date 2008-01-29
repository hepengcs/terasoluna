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

import java.lang.reflect.Type;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.terasoluna.fw.web.rich.springmvc.controller.TerasolunaController;

import org.springframework.web.bind.ServletRequestDataBinder;

/**
 * 抽象クラスTerasolunaControllerの実装クラス。
 *
 */
public class TerasolunaControllerImpl01 extends TerasolunaController {
    
    protected Type commandType = null;
    protected ServletRequestDataBinder binder = null;
    protected HttpServletRequest initBinderRequest = null;
    protected boolean isPreService = false;
    protected boolean isExecuteService = false;
    protected boolean isPostService = false;
    
    @Override
    protected Type getCommandType() {
        return commandType;
    }
    
    /**
     * 呼び出し確認メソッド。
     */
    @Override
    protected void initBinder(HttpServletRequest request, 
            ServletRequestDataBinder binder) throws Exception {
        this.binder = binder;
        this.initBinderRequest = request;
    }
    
    @Override
    protected ServletRequestDataBinder createBinder(
            HttpServletRequest request, Object command) throws Exception {
        return super.createBinder(request, command);
    }
    
    /**
     * 呼び出し確認メソッド。
     * @param request
     * @param response
     * @param command
     * @throws Exception
     */
    @Override
    protected void preService(HttpServletRequest request, 
            HttpServletResponse response, Object command) throws Exception {
        isPreService = true;
    }
    
    /**
     * 呼び出し確認メソッド。
     * @param command
     * @return 引数で受け取ったオブジェクト。
     * @throws Exception
     */
    @Override
    protected Object executeService(Object command) throws Exception {
        isExecuteService = true;
        return command;
    }
    
    /**
     * 呼び出し確認メソッド。
     * @param request
     * @param response
     * @param command
     * @param modelAndView
     * @throws Exception
     */
    @Override
    protected void postService(HttpServletRequest request, 
            HttpServletResponse response, Object command, Object modelAndView) 
            throws Exception {
        isPostService = true;
    }

}
