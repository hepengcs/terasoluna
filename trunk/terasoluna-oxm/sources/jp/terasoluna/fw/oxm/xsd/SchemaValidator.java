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

package jp.terasoluna.fw.oxm.xsd;

import java.io.InputStream;

import org.w3c.dom.Document;

import jp.terasoluna.fw.oxm.xsd.message.ErrorMessages;

/**
 * XMLデータの形式チェックを行うクラスが実装すべきインタフェース。
 * <p>
 * 通常は実装クラスとして、{@link jp.terasoluna.fw.oxm.xsd.xerces.SchemaValidatorImpl}を利用すればよい。
 * </p>
 * 
 * @see jp.terasoluna.fw.oxm.xsd.xerces.SchemaValidatorImpl
 * @see jp.terasoluna.fw.web.rich.springmvc.bind.XMLServletRequestDataBinder
 * 
 */
public interface SchemaValidator {

    /**
     * XMLデータの形式チェックを行うメソッド。
     * 
     * @param in XMLデータの入力ストリーム
     * @param object 定義ファイルのパスを解決するためのオブジェクト
     * @param errorMessages エラーメッセージが格納されるインスタンス
     * 
     * @return DOMツリー
     */
    Document validate(InputStream in, Object object,
            ErrorMessages errorMessages);
}
