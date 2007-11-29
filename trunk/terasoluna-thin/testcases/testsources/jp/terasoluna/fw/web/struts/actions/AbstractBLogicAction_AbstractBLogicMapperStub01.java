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

import jp.terasoluna.fw.service.thin.AbstractBLogicMapper;
import jp.terasoluna.fw.service.thin.BLogicIO;

/**
 * AbstractBLogicActionTestクラスで利用する。
 *
 */
public class AbstractBLogicAction_AbstractBLogicMapperStub01 extends
        AbstractBLogicMapper {

    @Override
    public Object mapBLogicParams(HttpServletRequest request,
            HttpServletResponse response, BLogicIO io) {
        return null;
    }

    @Override
    public Object getValueFromRequest(String propName,
            HttpServletRequest request, HttpServletResponse response) {
        return null;
    }

    @Override
    public Object getValueFromSession(String propName,
            HttpServletRequest request, HttpServletResponse response) {
        return null;
    }

    @Override
    public void setValueToRequest(Object value, String propName,
            HttpServletRequest request, HttpServletResponse response) {
    }

    @Override
    public void setValueToSession(Object value, String propName,
            HttpServletRequest request, HttpServletResponse response) {
    }

    @Override
    public Object getValueFromApplication(String arg0,
            HttpServletRequest arg1, HttpServletResponse arg2) {
        return null;
    }

}
