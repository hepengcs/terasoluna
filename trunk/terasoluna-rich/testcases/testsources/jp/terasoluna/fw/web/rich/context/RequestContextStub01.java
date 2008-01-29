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

package jp.terasoluna.fw.web.rich.context;

import jp.terasoluna.fw.web.rich.context.RequestContext;

/**
 * リクエスト名・業務プロパティを保持するスタブクラス。
 *
 */
public class RequestContextStub01 extends RequestContext {

    /**
     * プロパティキー確認用。
     */
    public String key = null;

    /**
     * プロパティ値確認用。
     */
    public Object value = null;

    /**
     * 業務プロパティを取得する。
     * @param key プロパティキー
     * @return value プロパティ値
     */
    @Override
    public Object getProperty(String key) {
    	this.key = key;
        return this.value;
    }
    
    /**
     * 業務プロパティを設定する。
     * @param key プロパティキー
     * @param value プロパティ値
     */
    @Override
    public void setProperty(String key, Object value) {
    	this.key = key;
    	this.value = value;
    }
    
    

}
