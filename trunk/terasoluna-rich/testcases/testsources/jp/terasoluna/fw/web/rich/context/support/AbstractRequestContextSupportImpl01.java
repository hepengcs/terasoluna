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

package jp.terasoluna.fw.web.rich.context.support;

import javax.servlet.http.HttpServletRequest;
import jp.terasoluna.fw.web.rich.context.RequestContext;
import jp.terasoluna.fw.web.rich.context.support.AbstractRequestContextSupport;

/**
 * AbstractRequestContextSupportTest�ŗ��p����B
 * 
 */
public class AbstractRequestContextSupportImpl01 extends
        AbstractRequestContextSupport {

    // doGenerateContext()�Ăяo���m�F�p�t���O
    private boolean doGenerateContextCalled = false;

    // doGenerateContext()�Ăяo�����̈�����ێ�����
    private HttpServletRequest doGenerateContextArg = null;

    // getRequestContex()�ŕԋp����l�@�e�X�g�P�[�X�Œl��ݒ肷��
    private RequestContext testCtx = null;

    /**
     * testCtx��ԋp����B
     * 
     * @return testCtx
     */
    public RequestContext getTestCtx() {
        return testCtx;
    }

    /**
     * testCtx��ݒ肷��B�e�X�g�P�[�X����g�p����B
     * 
     * @param testCtx �e�X�g�pRequestContext
     */
    public void setTestCtx(RequestContext testCtx) {
        this.testCtx = testCtx;
    }

    /**
     * doGenerateContextArg��ԋp����B
     * 
     * @return doGenerateContextArg
     */
    public HttpServletRequest getDoGenerateContextArg() {
        return doGenerateContextArg;
    }

    /**
     * doGenerateContextCalled��ԋp����B
     * 
     * @return doGenerateContextCalled
     */
    public boolean isDoGenerateContextCalled() {
        return doGenerateContextCalled;
    }

    /**
     * getRequestContext()�̌��ʂ�ԋp����B
     * 
     * @return RequestContext �e�X�g�pRequestContext
     */
    @Override
    protected RequestContext doGenerateContext(HttpServletRequest request) {
        this.doGenerateContextCalled = true;
        this.doGenerateContextArg = request;
        return getRequestContext();
    }

    /**
     * testCtx��ԋp����B
     * 
     * @return testCtx �e�X�g�pRequestContext
     */
    @Override
    protected RequestContext getRequestContext() {
        return this.testCtx;
    }
}
