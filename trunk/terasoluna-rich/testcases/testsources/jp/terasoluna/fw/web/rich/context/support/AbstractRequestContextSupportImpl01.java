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
 * AbstractRequestContextSupportTestで利用する。
 * 
 */
public class AbstractRequestContextSupportImpl01 extends
        AbstractRequestContextSupport {

    // doGenerateContext()呼び出し確認用フラグ
    private boolean doGenerateContextCalled = false;

    // doGenerateContext()呼び出し時の引数を保持する
    private HttpServletRequest doGenerateContextArg = null;

    // getRequestContex()で返却する値　テストケースで値を設定する
    private RequestContext testCtx = null;

    /**
     * testCtxを返却する。
     * 
     * @return testCtx
     */
    public RequestContext getTestCtx() {
        return testCtx;
    }

    /**
     * testCtxを設定する。テストケースから使用する。
     * 
     * @param testCtx テスト用RequestContext
     */
    public void setTestCtx(RequestContext testCtx) {
        this.testCtx = testCtx;
    }

    /**
     * doGenerateContextArgを返却する。
     * 
     * @return doGenerateContextArg
     */
    public HttpServletRequest getDoGenerateContextArg() {
        return doGenerateContextArg;
    }

    /**
     * doGenerateContextCalledを返却する。
     * 
     * @return doGenerateContextCalled
     */
    public boolean isDoGenerateContextCalled() {
        return doGenerateContextCalled;
    }

    /**
     * getRequestContext()の結果を返却する。
     * 
     * @return RequestContext テスト用RequestContext
     */
    @Override
    protected RequestContext doGenerateContext(HttpServletRequest request) {
        this.doGenerateContextCalled = true;
        this.doGenerateContextArg = request;
        return getRequestContext();
    }

    /**
     * testCtxを返却する。
     * 
     * @return testCtx テスト用RequestContext
     */
    @Override
    protected RequestContext getRequestContext() {
        return this.testCtx;
    }
}
