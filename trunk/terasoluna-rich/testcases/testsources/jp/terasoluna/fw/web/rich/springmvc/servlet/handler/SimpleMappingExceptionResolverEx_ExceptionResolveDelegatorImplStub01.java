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

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import jp.terasoluna.fw.web.rich.springmvc.servlet.handler.ExceptionResolveDelegatorImpl;

import org.springframework.web.servlet.ModelAndView;

/**
 * ExceptionResolveDelegatorImplのスタブ。
 * <p>
 * 呼び出し確認用。
 * </p>
 *
 */
public class SimpleMappingExceptionResolverEx_ExceptionResolveDelegatorImplStub01 extends ExceptionResolveDelegatorImpl{

    protected boolean isAddObjectToModel = false;
    protected boolean isSetHeader = false;
    protected Map paramsMap = null;

    @Override
    public void setHeader(HttpServletResponse response) {
        this.isSetHeader = true;
    }
    
    @Override
    public void addObjectToModel(ModelAndView mv) {
        this.isAddObjectToModel = true;
    }
    
    @Override
    public void initMapping(String mappingKey, Object mappingValues, Map<String, String> params) {
        this.mappingKey = mappingKey;
        this.mappingValues = mappingValues;
        this.paramsMap = params;
    }

}
