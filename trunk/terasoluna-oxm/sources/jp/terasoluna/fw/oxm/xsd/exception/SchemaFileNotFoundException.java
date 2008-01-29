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
 * 指定パス上にスキーマ定義ファイルが存在しない場合に、スローされる形式チェック例外。
 * 
 * <p>
 * OXMappingExceptionのサブクラスとして提供している。
 * </p>
 * 
 */
public class SchemaFileNotFoundException extends OXMappingException {

    /**
     * シリアルバージョンID。
     */
    private static final long serialVersionUID = 7231692049763141057L;

    /**
     * コンストラクタ
     * 
     */
    public SchemaFileNotFoundException() {
        super();
    }

}
