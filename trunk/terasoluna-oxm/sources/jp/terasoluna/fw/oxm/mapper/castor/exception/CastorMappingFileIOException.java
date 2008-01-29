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
 * Castorのマッピング定義ファイルの読み込みに失敗した場合に、スローされるバインド処理例外。
 * 
 * <p>
 * OXMappingExceptionのサブクラスとして提供している。
 * </p>
 * 
 */
public class CastorMappingFileIOException extends OXMappingException {

    /**
     * シリアルバージョンID。
     */
    private static final long serialVersionUID = 856879408423123020L;

    /**
     * コンストラクタ
     * 
     * @param cause 例外
     */
    public CastorMappingFileIOException(Throwable cause) {
        super(cause);
    }

}
