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

package jp.terasoluna.fw.web.struts.action;

/**
 * DBから取得した行（メッセージリソース）を一時的にオブジェクトの形式で
 * 保管するためにあるクラス。
 * 
 * 利用のされ方に関しては以下のクラスを参照のこと。
 * 
 * @see jp.terasoluna.fw.web.struts.action.MessageResourcesDAOImpl
 * 
 */
public class DBMessageBean {
     
    /**
     * メッセージキー。
     */
    private String key = null;
    
    /**
     * メッセージ文言。
     */
    private String value = null;
    
    /**
     * コンストラクタ。特になにも処理はない。
     *
     */
    public DBMessageBean() {
        // 特に何もしない
    } 
    
    /**
     * メッセージキーを取得する。
     * 
     * @return メッセージキー。
     */
    public String getKey() {
        return key;
    }
    
    /**
     * メッセージ文言を取得する。
     * 
     * @return メッセージ文言。
     */
    public String getValue() {
        return value;
    }
    
    /**
     * メッセージキーを設定する。
     * 
     * @param key メッセージキー。
     */
    public void setKey(String key) {
        this.key = key;
    }
    
    /**
     * メッセージ文言を設定する。
     * 
     * @param value メッセージ文言。
     */
    public void setValue(String value) {
        this.value = value;
    }
}
