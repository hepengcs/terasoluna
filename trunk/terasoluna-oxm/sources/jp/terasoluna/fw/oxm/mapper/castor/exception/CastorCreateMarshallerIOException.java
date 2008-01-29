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

package jp.terasoluna.fw.oxm.mapper.castor.exception;

import jp.terasoluna.fw.oxm.exception.OXMappingException;

/**
 * CastorのMarshaller生成時にIO例外が発生した場合、スローされる例外。
 * 
 * <p>
 * OXMappingExceptionのサブクラスとして提供している。
 * </p>
 * 
 */
public class CastorCreateMarshallerIOException extends OXMappingException {

    /**
     * シリアルバージョンID。
     */
    private static final long serialVersionUID = 3801084076373436920L;

    /**
     * コンストラクタ。
     * 
     * @param cause 例外
     */
    public CastorCreateMarshallerIOException(Throwable cause) {
        super(cause);
    }

}
