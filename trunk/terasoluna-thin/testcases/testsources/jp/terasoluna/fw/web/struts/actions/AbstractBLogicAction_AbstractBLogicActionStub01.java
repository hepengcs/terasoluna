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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.terasoluna.fw.service.thin.BLogicResult;
import jp.terasoluna.fw.web.struts.action.ActionMappingEx;

/**
 * AbstractBLogicActionTestクラスで利用する。
 * 
 */
public class AbstractBLogicAction_AbstractBLogicActionStub01
extends AbstractBLogicAction {

    public Object params = null;
    public BLogicResult result = null;
    
    public boolean isDoExecuteBLogic = false;
    public boolean isEvaluateBLogicResult = false;
    public boolean isPostDoExecuteBLogic = false;
    public boolean isPreDoExecuteBLogic = false;
    
    // doExecuteBLogicのパラメータ
    public Object paramDoExecuteBLogic = null;
    
    // getBLogicParamsのパラメータ
    public ActionMappingEx mappingGetBLogicParams = null;
    public HttpServletRequest requestGetBLogicParams = null;
    public HttpServletResponse responseGetBLogicParams = null;
    
    // evaluateBLogicResultのパラメータ
    public BLogicResult resultEvaluateBLogicResult = null;
    public HttpServletRequest requestEvaluateBLogicResult = null;
    public HttpServletResponse responseEvaluateBLogicResult = null;
    public ActionMappingEx mappingEvaluateBLogicResult = null;
    
    // preDoExecuteBLogicのパラメータ
    public HttpServletRequest requestPre = null;
    public HttpServletResponse responsePre = null;
    public Object paramsPre = null;
    
    // postDoExecuteBLogicのパラメータ    
    public HttpServletRequest requestPost = null;
    public HttpServletResponse responsePost = null;
    public Object paramsPost = null;
    public BLogicResult resultPost = null;
    
    @Override
    public BLogicResult doExecuteBLogic(Object param) throws Exception {
        this.isDoExecuteBLogic = true;
        
        this.paramDoExecuteBLogic = param;
        return result;
    }

    @Override
    protected Object getBLogicParams(ActionMappingEx mapping, 
            HttpServletRequest request, HttpServletResponse response)
    throws Exception {
        this.mappingGetBLogicParams = mapping;
        this.requestGetBLogicParams = request;
        this.responseGetBLogicParams = response;
        
        return params;
    }

    @Override
    protected void evaluateBLogicResult(@SuppressWarnings("hiding") BLogicResult result, 
            HttpServletRequest request, HttpServletResponse response, 
            ActionMappingEx mappingEx) {
        this.isEvaluateBLogicResult = true;
        
        this.resultEvaluateBLogicResult = result;
        this.requestEvaluateBLogicResult = request;
        this.responseEvaluateBLogicResult = response;
        this.mappingEvaluateBLogicResult = mappingEx;
    }

    @Override
    protected void preDoExecuteBLogic(HttpServletRequest request, 
            HttpServletResponse response, Object params) throws Exception {
        this.isPreDoExecuteBLogic = true;
        
        this.requestPre = request;
        this.responsePre = response;
        this.paramsPre = params;
    }
    
    @Override
    protected void postDoExecuteBLogic(HttpServletRequest request, 
            HttpServletResponse response, Object params, BLogicResult result)
    throws Exception {
        this.isPostDoExecuteBLogic = true;
        
        this.requestPost = request;
        this.responsePost = response;
        this.paramsPost = params;
        this.resultPost = result;
    }
}
