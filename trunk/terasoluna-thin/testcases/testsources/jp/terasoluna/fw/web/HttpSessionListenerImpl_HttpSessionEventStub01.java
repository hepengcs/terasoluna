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

package jp.terasoluna.fw.web;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;

import jp.terasoluna.utlib.MockHttpSession;

/**
 * HttpSessionListenerImplTestクラスで利用する。
 * 
 */
public class HttpSessionListenerImpl_HttpSessionEventStub01 extends
        HttpSessionEvent {
    
    /**
     * シリアルバージョンID
     */
    private static final long serialVersionUID = -9058658477974930574L;

    public MockHttpSession session = new MockHttpSession();

    @Override
    public HttpSession getSession() {
        return session;
    }
    
    public HttpSessionListenerImpl_HttpSessionEventStub01(HttpSession source) {
        super(source);
    }
}
