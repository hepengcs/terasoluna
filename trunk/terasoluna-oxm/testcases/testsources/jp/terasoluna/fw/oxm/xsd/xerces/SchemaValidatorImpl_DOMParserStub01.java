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

import java.io.IOException;

import org.apache.xerces.parsers.DOMParser;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * 
 * DOMParserのスタブ。
 *
 * <p>
 * parseメソッドの呼び出し確認を行う。
 * </p>
 *
 */
public class SchemaValidatorImpl_DOMParserStub01 extends DOMParser{

    protected boolean isParse = false;

    protected boolean isGetDocument = false;
    
    @Override
    public void parse(InputSource inputSource) throws SAXException, IOException {
        this.isParse = true;
    }

    @Override
    public Document getDocument() {
        this.isGetDocument = true;
        return null;
    }    
}
