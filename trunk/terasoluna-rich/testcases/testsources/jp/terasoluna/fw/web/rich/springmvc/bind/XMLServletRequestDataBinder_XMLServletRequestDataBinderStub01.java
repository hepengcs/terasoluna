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

package jp.terasoluna.fw.web.rich.springmvc.bind;

import java.io.InputStream;

import org.w3c.dom.Document;

import jp.terasoluna.fw.oxm.mapper.OXMapper;
import jp.terasoluna.fw.oxm.xsd.SchemaValidator;
import jp.terasoluna.fw.web.rich.springmvc.bind.XMLServletRequestDataBinder;

/**
 * 
 * XMLServletRequestDataBinderの実装クラス
 *
 * <p>
 * validateメソッドに引数が渡されたことを確認する。
 * </p>
 *
 */

public class XMLServletRequestDataBinder_XMLServletRequestDataBinderStub01 extends
        XMLServletRequestDataBinder {

    public InputStream in = null;

    public Document doc = null;
    
    public XMLServletRequestDataBinder_XMLServletRequestDataBinderStub01(Object target, OXMapper oxmapper,
            SchemaValidator schemaValidator, String objectName) {
        super(target, oxmapper, schemaValidator, objectName);
    }

    @Override
    public Document validate(InputStream in) {
        this.in = in;
        return doc;
    }
    
}
