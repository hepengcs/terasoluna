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

package jp.terasoluna.fw.web.rich.springmvc.servlet.view.castor;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.terasoluna.fw.web.rich.springmvc.servlet.view.castor.CastorView;

/**
 * CastorView�̃X�^�u�B
 * addHeader���\�b�h�̌Ăяo���m�F�p�B
 *
 */
public class CastorViewStub01 extends CastorView {
    
    protected Map modelData = null;
    protected HttpServletRequest requestData = null;
    protected HttpServletResponse responseData = null;

    @Override
    protected void addResponseHeader(Map model, HttpServletRequest request, HttpServletResponse response) {
        modelData = model;
        requestData = request;
        responseData = response;
    }
}
