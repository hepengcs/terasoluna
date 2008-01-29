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

import java.net.URL;

import jp.terasoluna.fw.oxm.xsd.message.ErrorMessages;
import jp.terasoluna.fw.oxm.xsd.xerces.SchemaValidatorImpl;

import org.apache.xerces.parsers.DOMParser;
import org.apache.xerces.xni.parser.XMLParserConfiguration;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;

/**
 * SchemaValidatorImplを継承したクラス。
 * 
 * <p>
 * validateメソッド内で呼び出されるメソッドの呼び出し確認を行う。
 * </p>
 * 
 */
public class SchemaValidatorImpl_SchemaValidatorImplStub01 extends SchemaValidatorImpl{

    protected boolean isGetUrl = false;

    protected URL url = null;

    protected boolean isGetNamespaceName = false;
    
    protected String namespaceName = null;

    protected boolean isCreateDomParser = false;

    protected DOMParser domParser = null;

    protected boolean isSetCommonParserProperty = false;

    protected boolean isSetCommonParserFeature = false;
    
    protected XMLParserConfiguration config = null;
    
    protected boolean isCreateXmlParserConfiguration = false;

    @Override
    protected URL getUrl(Object object) {
        this.isGetUrl = true;
        return url;
    }
    
    @Override
    protected String getNamespaceName(Object object) {
        this.isGetNamespaceName = true;
        return this.namespaceName;
    }
    
    @Override
    protected DOMParser createDomParser(Object arg) throws org.xml.sax.SAXNotRecognizedException ,org.xml.sax.SAXNotSupportedException{
        this.isCreateDomParser = true;
        return domParser;
    }
    
    @Override
    protected XMLParserConfiguration createXmlParserConfiguration() {
    	isCreateXmlParserConfiguration = true;
    	return config;
    }

    @Override
    protected void setCommonParserProperty(DOMParser parser, ErrorMessages errorMessages) throws SAXNotRecognizedException, SAXNotSupportedException {
        this.isSetCommonParserProperty = true;
    }

    @Override
    protected void setCommonParserFeature(DOMParser parser) throws SAXNotRecognizedException, SAXNotSupportedException {
        this.isSetCommonParserFeature = true;
    }
    
}
