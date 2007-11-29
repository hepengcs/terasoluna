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

import org.apache.struts.action.ActionMapping;

import jp.terasoluna.fw.service.thin.AbstractBLogicMapper;
import jp.terasoluna.fw.service.thin.BLogicIO;
import jp.terasoluna.fw.service.thin.BLogicResult;

/**
 * AbstractBLogicActionTestクラスで利用する。
 * 
 */
public class AbstractBLogicAction_AbstractBLogicActionStub04 extends
        AbstractBLogicAction {

    public boolean isGetBLogicIO = false;    
    
    @Override
    public BLogicResult doExecuteBLogic(Object param) throws Exception {
        return null;
    }

    @Override
    protected BLogicIO getBLogicIO(ActionMapping mapping,
            HttpServletRequest request) {
        this.isGetBLogicIO = true;
        return null;
    }

    @Override
    protected AbstractBLogicMapper getBLogicMapper(HttpServletRequest req) {
        return new AbstractBLogicAction_AbstractBLogicMapperStub02();
    }   
}
