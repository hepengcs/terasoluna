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

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * エラーメッセージのリストを保持するクラス。
 * 
 * @see jp.terasoluna.fw.oxm.xsd.SchemaValidator
 * @see jp.terasoluna.fw.oxm.xsd.xerces.SchemaValidatorImpl
 * @see jp.terasoluna.fw.oxm.xsd.xerces.XMLErrorReporterEx
 * @see jp.terasoluna.fw.web.rich.springmvc.bind.XMLServletRequestDataBinder
 * 
 */
public class ErrorMessages {
    
    /**
     * ログ。
     */
    private static Log log = LogFactory.getLog(ErrorMessages.class);

    /**
     * エラーメッセージのリスト。
     */
    private List<ErrorMessage> errorMessages = new ArrayList<ErrorMessage>();

    /**
     * エラーメッセージを追加する。
     * Nullを追加しようとした場合、例外が発生する。
     * @param errorMessage エラーメッセージ
     */
    public void add(ErrorMessage errorMessage) {
        if (errorMessage == null) {
            log.error("ErrorMessages instance cannot add null object.");
            throw new IllegalArgumentException(
                    "ErrorMessages instance cannot add null object.");
        }
        this.errorMessages.add(errorMessage);
    }

    /**
     * エラーメッセージのリストを取得する。
     * エラーメッセージにNullは存在しない。
     * 
     * @return エラーメッセージのリスト
     */
    public List<ErrorMessage> getErrorMessages() {
        return errorMessages;
    }

    /**
     * エラーメッセージを保持している場合、trueを返却する。
     * 
     * @return メッセージを持っている場合、trueを返す。それ以外はfalseを返す。
     */
    public boolean hasErrorMessage() {
        return !this.errorMessages.isEmpty();
    }
}
