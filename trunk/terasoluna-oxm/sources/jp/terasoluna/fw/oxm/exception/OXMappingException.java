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

package jp.terasoluna.fw.oxm.exception;

/**
 *  形式チェックおよびバインド処理内で発生した例外をラップするクラス。
 *  
 * <p>
 *  実際に発生した例外については{@link java.lang.Throwable#getCause}メソッドから取得する。
 * </p>
 * 
 */
public class OXMappingException extends RuntimeException {

    /**
     * シリアルバージョンID。
     */
    private static final long serialVersionUID = -5964737290418496214L;

    /**
     * コンストラクタ
     * 
     */
    public OXMappingException() {
        super();
    }

    /**
     * コンストラクタ
     * 
     * @param cause スキーマによる形式チェック、およびCastorによるバインド処理で発生した例外
     */
    public OXMappingException(Throwable cause) {
        super(cause);
    }
}
