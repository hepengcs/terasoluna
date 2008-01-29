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

package jp.terasoluna.fw.oxm.xsd.xerces;

import jp.terasoluna.fw.oxm.xsd.message.ErrorMessages;
import jp.terasoluna.fw.oxm.xsd.xerces.XMLErrorReporterEx;

/**
 * XMLErrorReporterExのスタブ。
 *
 * <p>
 * indexResolveの呼び出し確認に使用する。<br>
 * "sample-dto[0]"を返却する。
 * </p>
 *
 */
public class XMLSchemaValidatorEx_XMLErrorReporterExStub01 extends XMLErrorReporterEx{

    protected String element = null;

    public XMLSchemaValidatorEx_XMLErrorReporterExStub01(ErrorMessages errorMessages) {
       super(errorMessages);
    }
    
    @Override
    protected String indexResolve(String element) {
        this.element = element;
        return "sample-dto[0]";
    }
    
}
