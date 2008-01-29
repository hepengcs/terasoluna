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

import org.apache.xerces.impl.xs.opti.DefaultXMLDocumentHandler;
import org.apache.xerces.xni.Augmentations;
import org.apache.xerces.xni.QName;
import org.apache.xerces.xni.XMLAttributes;
import org.apache.xerces.xni.XNIException;

/**
 * DefaultXMLDocumentHandlerのスタブ。
 *
 * <p>
 * super.startElement、super.endElementメソッドの呼び出し確認に使用する。
 * </p>
 *
 */
public class XMLSchemaValidatorEx_DefaultXMLDocumentHandlerStub01 extends DefaultXMLDocumentHandler {

    protected boolean isStartElement = false;
    protected boolean isEndElement = false;
    
    @Override
    public void startElement(QName element, XMLAttributes attributes, Augmentations augs) throws XNIException {
        this.isStartElement = true;
    }
    
    @Override
    public void endElement(QName element, Augmentations augs) throws XNIException {
        this.isEndElement = true;
    }

}
