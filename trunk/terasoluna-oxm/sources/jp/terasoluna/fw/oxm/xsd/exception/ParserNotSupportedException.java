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

package jp.terasoluna.fw.oxm.xsd.exception;

import jp.terasoluna.fw.oxm.exception.OXMappingException;

/**
 * DOMパーサに不正なプロパティを設定した場合に、スローされる形式チェック例外。
 * 
 * <p>
 * OXMappingExceptionのサブクラスとして提供している。
 * </p>
 * 
 */
public class ParserNotSupportedException extends OXMappingException {

    /**
     * シリアルバージョンID。
     */
    private static final long serialVersionUID = -2905372376210161806L;

    /**
     * コンストラクタ
     * 
     * @param cause 例外
     */
    public ParserNotSupportedException(Throwable cause) {
        super(cause);
    }

}
