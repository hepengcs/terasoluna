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

package jp.terasoluna.fw.web.rich.springmvc.servlet.handler;

import jp.terasoluna.fw.web.rich.context.support.DefaultRequestContextSupportImpl;

/**
 * 正常にリクエスト名を返すリクエストコンテキストサポートクラスのスタブ。
 *
 */
public class BeanNameUrlHandlerMappingEx_DefaultRequestContextSupportImplStub01 
    extends DefaultRequestContextSupportImpl {
    
    private String requestName = null;
    
    /**
     * リクエスト名を設定するメソッド。
     * @param requestName
     */
    public void setRequestName(String requestName) {
        this.requestName = requestName;
    }

    @Override
    public String getRequestName() {
        return requestName;
    }
}
