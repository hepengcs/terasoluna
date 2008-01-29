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

import javax.servlet.ServletContext;

import jp.terasoluna.utlib.MockServletContext;

import org.apache.struts.action.ActionServlet;

/**
 * RequestProcessorExTestクラスで利用する。
 * 
 * @version 2004/03/18
 */
public class RequestProcessorEx_ActionServletStub01
        extends ActionServlet {

    /**
     * シリアルバージョンID
     */
    private static final long serialVersionUID = -341656069363746392L;

    /**
     * サーブレットコンテキストを返却する。
     * 
     * @return 擬似サーブレットコンテキスト
     */
    @Override
    public ServletContext getServletContext() {
        return new MockServletContext();
    }
}
