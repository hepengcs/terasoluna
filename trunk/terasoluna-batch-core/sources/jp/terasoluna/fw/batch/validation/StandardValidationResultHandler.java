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

package jp.terasoluna.fw.batch.validation;

import jp.terasoluna.fw.batch.messages.MessageAccessor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;

/**
 * <code>ValidationResultHandler</code> インタフェースの標準実装クラス。
 * 入力チェック結果から処理中断、処理継続の判定を行う。<BR>
 *
 */
public class StandardValidationResultHandler 
    implements ValidationResultHandler {

    /**
     * ログインスタンス。
     */
    private static Log log = 
        LogFactory.getLog(StandardValidationResultHandler.class);

    /**
     * エラー継続フラグ。
     */
    private boolean errorContinueFlg = false;
    
    /**
     * メッセージ取得クラスのインスタンス。
     */
    private MessageAccessor messageAccessor = null;
    
    /**
     * 入力チェック結果から処理中断、処理継続の判定を行う。
     * <code>errorContinueFlg<code>が<code>true<code>の場合は
     * 入力チェック結果がエラーの場合、<code>false<code>を返す。
     * <code>errorContinueFlg<code>が<code>false<code>の場合は
     * 入力チェック結果がエラーの場合、例外を発生させ処理を中断する。
     *
     * @param bindException キュー処理プロセッサ
     * @param value キュー
     * @return 判定結果、エラーの場合は<code>false<code>
     */
    public boolean handle(BindException bindException, Object value) {
        if (bindException.getErrorCount() > 0) {
            if (errorContinueFlg) {
                   StringBuilder errorString = new StringBuilder();
                   for (Object objectErrors : bindException.getAllErrors()) {
                       FieldError fieldError = (FieldError) objectErrors;
                    errorString.append(
                            messageAccessor.getMessage(
                                    fieldError.getDefaultMessage(), 
                                    fieldError.getArguments()));
                       
                   }
                log.warn(errorString.toString());
                return false;
            } else {
                throw new ValidateException(bindException);
            }
        }
        return true;
    }

    /**
     * エラー継続フラグを設定する。
     * 
     * @param errorContinueFlg エラー継続フラグ
     */
    public void setErrorContinueFlg(boolean errorContinueFlg) {
        this.errorContinueFlg = errorContinueFlg;
    }

    /**
     * メッセージ取得クラスのインスタンスを設定する。
     * 
     * @param messageAccessor メッセージ取得クラスのインスタンス
     */
    public void setMessageAccessor(MessageAccessor messageAccessor) {
        this.messageAccessor = messageAccessor;
    }
}
