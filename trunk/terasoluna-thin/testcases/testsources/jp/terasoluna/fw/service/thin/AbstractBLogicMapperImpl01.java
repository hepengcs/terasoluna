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

package jp.terasoluna.fw.service.thin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * AbstratBLogicMapperTestクラスで利用する。
 *
 */
public class AbstractBLogicMapperImpl01 extends AbstractBLogicMapper {

    /**
     * 「propName + "_FromRequest"」を返却する。
     */
    @Override
    public Object getValueFromRequest(String propName,
            HttpServletRequest request, HttpServletResponse response) {
        return propName + "_FromRequest";
    }

    /**
     * 「propName + "_FromSession"」を返却する。
     */
    @Override
    public Object getValueFromSession(String propName,
            HttpServletRequest request, HttpServletResponse response) {
        return propName + "_FromSession";
    }

    /**
     * 「propName + "_FromForm"」を返却する。
     */
    public Object getValueFromForm(String propName, HttpServletRequest request,
            HttpServletResponse response) {
        return propName + "_FromForm";
    }

    /**
     * requestに「propName + "_ToRequest"」というキーでvalueを設定する。
     */
    @Override
    public void setValueToRequest(Object value, String propName,
            HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute(propName + "_ToRequest", value);

    }

    /**
     * requestに「propName + "_ToSession"」というキーでvalueを設定する。
     */
    @Override
    public void setValueToSession(Object value, String propName,
            HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute(propName + "_ToSession", value);

    }

    /**
     * requestに「propName + "_ToForm"」というキーでvalueを設定する。
     */
    public void setValueToForm(Object value, String propName,
            HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute(propName + "_ToForm", value);
    }

    /**
     * Exceptionをスローする。
     */
    public Object getValueFromError(String propName,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        throw new Exception();
    }

    /**
     * Exceptionをスローする。
     */
    public void setValueToError(Object value, String propName,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        throw new Exception();
    }

    /**
     * 「propName + "_FromApplication"」を返却する。
     */
    @Override
    public Object getValueFromApplication(String propName,
            HttpServletRequest request, HttpServletResponse response) {
        return propName + "_FromApplication";
    }
}
