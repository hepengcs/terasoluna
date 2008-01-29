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

package jp.terasoluna.fw.web.rich;

import javax.servlet.http.HttpServletRequest;

import jp.terasoluna.fw.web.rich.context.support.RequestContextSupport;

/**
 * RequestContextHandlingFilterTestで利用する。
 * 
 */
public class RequestContextHandlingFilter_RequestContextSupportStub01
        implements RequestContextSupport {

    // generateContext()呼び出し確認用フラグ
    private boolean generateContextCalled = false;

    // destroyContext()呼び出し確認用フラグ
    private boolean destroyContextCalled = false;

    /**
     * destroyContextCalledを返却する。
     * 
     * @return destroyContextCalled
     */
    public boolean isDestroyContextCalled() {
        return destroyContextCalled;
    }



    /**
     * generateContextCalledを返却する。
     * 
     * @return generateContextCalled
     */
    public boolean isGenerateContextCalled() {
        return generateContextCalled;
    }

    /**
     * 特に使用しない。
     * 
     * @return null
     */
    public String getRequestName() {
        return null;
    }

    /**
     * 特に使用しない。
     * 
     * @return null
     */
    public String getProperty(String key) {
        return null;
    }
    
    /**
     * 特に使用しない。
     * 
     * @return null
     */
    public <E> E getProperty(String key, Class<E> clazz) {
        return null;
    }

    /**
     * 特に使用しない。
     * 
     * @return null
     */
    public String getPropertyString(String key) {
        return null;
    }
    /**
     * generateContextCalledにtrueを設定する。
     * 
     * @param request HttpServletRequest
     */
    public void generateContext(HttpServletRequest request) {
        this.generateContextCalled = true;
    }

    /**
     * destroyContextCalledにtrueを設定する。
     */
    public void destroyContext() {
        this.destroyContextCalled = true;
    }

}
