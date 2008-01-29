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

package jp.terasoluna.fw.oxm.xsd.message;

/**
 * エラーメッセージを保持するクラス。
 * 
 * <p>
 * 以下の情報を保持する。
 * <li>エラーコード</li>
 * <li>置換文字列</li>
 * <li>エラーが発生したフィールド情報</li>
 * </p>
 * 
 * <p>
 * 通常、このクラスはメッセージバンドルに使用される。
 * </p>
 * 
 * @see jp.terasoluna.fw.oxm.xsd.SchemaValidator
 * @see jp.terasoluna.fw.oxm.xsd.xerces.SchemaValidatorImpl
 * @see jp.terasoluna.fw.oxm.xsd.xerces.XMLErrorReporterEx
 * @see jp.terasoluna.fw.web.rich.springmvc.bind.XMLServletRequestDataBinder
 * 
 */
public class ErrorMessage {

    /**
     * エラーコード。
     */ 
    private String key = null;

    /**
     * 置換文字列。
     */
    private String[] replaceValues = null;

    /**
     * エラーが発生したフィールド。
     */
    private String field = null;

    /**
     * ErrorMessageを生成する。
     * 
     * @param key エラーコード
     * @param field エラーが発生したフィールド
     * @param values 置換文字列
     */
    public ErrorMessage(String key, String field, String... values) {
        this.key = key;
        this.field = field;
        this.replaceValues = values;
    }

    /**
     * エラーコードを返却する。
     * 
     * @return エラーコード
     */
    public String getKey() {
        return key;
    }

    /**
     * エラーコードを設定する。
     * 
     * @param key エラーコード
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * 置換文字列を返却する。
     * 
     * @return 置換文字列
     */
    public String[] getReplaceValues() {
        return replaceValues;
    }

    /**
     * 置換文字列を返却する。
     * 
     * @param replaceValues 置換文字列
     */
    public void setReplaceValues(String[] replaceValues) {
        this.replaceValues = replaceValues;
    }

    /**
     * エラーが発生したフィールドを返却する。
     * 
     * @return エラーが発生したフィールド
     */
    public String getField() {
        return field;
    }

    /**
     * エラーが発生したフィールドを設定する。
     * 
     * @param field エラーが発生したフィールド
     */
    public void setField(String field) {
        this.field = field;
    }

}
