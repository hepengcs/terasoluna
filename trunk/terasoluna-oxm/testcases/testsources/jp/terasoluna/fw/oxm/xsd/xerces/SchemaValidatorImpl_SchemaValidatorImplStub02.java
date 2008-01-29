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

import org.apache.xerces.parsers.DOMParser;
import org.xml.sax.SAXNotRecognizedException;

/**
 * SchemaValidatorImpl_SchemaValidatorImplStub01を継承したクラス。
 * 
 * <p>
 * createNonNamespaceDomParserメソッド呼び出し時に、例外をスローする。
 * </p>
 * 
 */
public class SchemaValidatorImpl_SchemaValidatorImplStub02 extends SchemaValidatorImpl_SchemaValidatorImplStub01{
    
    @Override
    protected DOMParser createDomParser(Object arg) throws org.xml.sax.SAXNotRecognizedException ,org.xml.sax.SAXNotSupportedException{
        throw new SAXNotRecognizedException();
    }
}
