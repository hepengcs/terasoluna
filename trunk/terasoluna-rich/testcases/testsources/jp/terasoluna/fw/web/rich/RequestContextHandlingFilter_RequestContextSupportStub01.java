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
 * RequestContextHandlingFilterTest�ŗ��p����B
 * 
 */
public class RequestContextHandlingFilter_RequestContextSupportStub01
        implements RequestContextSupport {

    // generateContext()�Ăяo���m�F�p�t���O
    private boolean generateContextCalled = false;

    // destroyContext()�Ăяo���m�F�p�t���O
    private boolean destroyContextCalled = false;

    /**
     * destroyContextCalled��ԋp����B
     * 
     * @return destroyContextCalled
     */
    public boolean isDestroyContextCalled() {
        return destroyContextCalled;
    }



    /**
     * generateContextCalled��ԋp����B
     * 
     * @return generateContextCalled
     */
    public boolean isGenerateContextCalled() {
        return generateContextCalled;
    }

    /**
     * ���Ɏg�p���Ȃ��B
     * 
     * @return null
     */
    public String getRequestName() {
        return null;
    }

    /**
     * ���Ɏg�p���Ȃ��B
     * 
     * @return null
     */
    public String getProperty(String key) {
        return null;
    }
    
    /**
     * ���Ɏg�p���Ȃ��B
     * 
     * @return null
     */
    public <E> E getProperty(String key, Class<E> clazz) {
        return null;
    }

    /**
     * ���Ɏg�p���Ȃ��B
     * 
     * @return null
     */
    public String getPropertyString(String key) {
        return null;
    }
    /**
     * generateContextCalled��true��ݒ肷��B
     * 
     * @param request HttpServletRequest
     */
    public void generateContext(HttpServletRequest request) {
        this.generateContextCalled = true;
    }

    /**
     * destroyContextCalled��true��ݒ肷��B
     */
    public void destroyContext() {
        this.destroyContextCalled = true;
    }

}
