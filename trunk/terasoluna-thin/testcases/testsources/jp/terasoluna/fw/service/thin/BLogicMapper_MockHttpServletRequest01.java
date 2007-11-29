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

import com.mockrunner.mock.web.MockHttpServletRequest;

/**
 * BLogicMapperTestクラスで利用する。
 * 
 */
public class BLogicMapper_MockHttpServletRequest01
        extends MockHttpServletRequest implements HttpServletRequest {

    /**
     * setAttribute(key, attr)後、attrがnullの場合は、removeAttribute(key)する。
     */
    @Override
    public void setAttribute(String key, Object attr) {
        super.setAttribute(key, attr);
        if (attr == null) {
            super.removeAttribute(key);
        }
    }
}
