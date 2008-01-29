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

package jp.terasoluna.fw.web.rich.context.exception;

/**
 * 取得する業務プロパティの型と指定された型が違った場合のRuntime例外クラス。
 * 
 */
public class IllegalContextPropertyClassTypeException extends RuntimeException {

    /**
     * シリアルバージョンID。
     */
    private static final long serialVersionUID = -6316012802099459622L;

    /**
     * エラーメッセージ
     */
    public static final String ERROR_ILLEGAL_CLASS_TYPE
        = "The illegal Class Type of the context property.";

    /**
     * コンストラクタ。
     */
    public IllegalContextPropertyClassTypeException() {
        super(ERROR_ILLEGAL_CLASS_TYPE);
    }

    /**
     * コンストラクタ。
     *
     * @param message メッセージ
     */
    public IllegalContextPropertyClassTypeException(String message) {
        super(message);
    }

    /**
     * コンストラクタ。
     *
     * @param cause 原因となった例外
     */
    public IllegalContextPropertyClassTypeException(Throwable cause) {
        super(ERROR_ILLEGAL_CLASS_TYPE, cause);
    }

    /**
     * コンストラクタ。
     *
     * @param message メッセージ
     * @param cause 原因となった例外
     */
    public IllegalContextPropertyClassTypeException(String message, Throwable cause) {
        super(message, cause);
    }
}
