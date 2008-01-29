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

import java.util.HashMap;
import java.util.Map;

/**
 * リクエスト名・業務プロパティを保持するためのクラス。
 *
 * <p>
 * 業務処理を行う上で必要となる制御情報を保持する。
 * 制御情報とは以下をあらわす。
 * </p>
 * 
 * <ol>
 * <li>リクエスト名。</li>
 * <li>業務プロパティ。</li>
 * </ol>
 * 
 * <p>
 * リクエスト名とは、リクエストを識別するための文字列であり、フレームワークから利用される。
 * </p>
 * 
 * <p>
 * 業務プロパティは、リクエスト間で共通的に扱う情報を保持するものであり、
 * デフォルトではフレームワークから利用されない。
 * 業務の要件にあわせて必要に応じて利用すること。
 * </p>
 * 
 * @see jp.terasoluna.fw.web.rich.context.support.AbstractRequestContextSupport
 * 
 */
public class RequestContext  {
    /**
     * リクエスト名。
     */
    private String requestName = null;
    
    /**
     * 業務プロパティ。
     */
    private Map<String, Object> propertyMap = new HashMap<String, Object>();

    /**
     * リクエスト名を取得する。
     * @return リクエスト名
     */
    public String getRequestName() {
        return requestName;
    }

    /**
     * リクエスト名を設定する。
     * @param requestName リクエスト名
     */
    public void setRequestName(String requestName) {
        this.requestName = requestName;
    }
    
    /**
     * 業務プロパティを取得する。
     * @param key プロパティキー
     * @return プロパティ値
     */
    public Object getProperty(String key) {
        return propertyMap.get(key);
    }
    
    /**
     * 業務プロパティを設定する。
     * @param key プロパティキー
     * @param value プロパティ値
     */
    public void setProperty(String key, Object value) {
    	propertyMap.put(key, value);
    }
    
    /**
     * 業務プロパティを取得する。
     * プロパティ値がString型でない場合はnullを返す。
     * @param key プロパティキー
     * @return プロパティ値
     */
    public String getPropertyString(String key) {
    	Object object = getProperty(key);
    	
    	if (object instanceof String) {
    		return (String) object;
        }
    	
        return null;
    }
    
    /**
     * 業務プロパティを設定する。
     * @param key プロパティキー
     * @param value プロパティ値
     */
    public void setPropertyString(String key, String value) {
    	setProperty(key, value);
    }
    
    /**
     * 当インスタンスの文字列表現を返す。
     * @return 当インスタンスの文字列表現。
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        
        // リクエスト名の文字列化
        sb.append("requestName:");
        sb.append(requestName);
        sb.append(",");
        
        // 業務プロパティの文字列化
        sb.append("properties:");
        sb.append(propertyMap.toString());
        
        return sb.toString();
    }
}
