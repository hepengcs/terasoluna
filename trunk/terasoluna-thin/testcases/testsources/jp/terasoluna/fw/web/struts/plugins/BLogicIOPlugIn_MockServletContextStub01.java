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

package jp.terasoluna.fw.web.struts.plugins;

import java.net.MalformedURLException;
import java.net.URL;

import jp.terasoluna.utlib.MockServletContext;

/**
 * BLogicIOPlugInTestクラスで利用する。
 * 
 * @version 2004/04/28
 */
public class BLogicIOPlugIn_MockServletContextStub01
        extends MockServletContext {

    /**
     * getResourceで呼び出されたリソース。
     */
    private String calledResources = null;  
    
    /**
     * 呼び出されたリソースのURLを返却する。
     * 
     * @return getResourceで呼び出されたURL
     */
    public String getCalledResources() {
        return calledResources;
    }

    /**
     * リソースに対して、呼ばれたURLを保持する。
     */
    @Override
    public URL getResource(String s) throws MalformedURLException {
        this.calledResources = s;
        URL retUrl = super.getResource(s);
        return retUrl;
    }
}
