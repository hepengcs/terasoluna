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

import jp.terasoluna.fw.oxm.mapper.OXMapper;
import jp.terasoluna.fw.oxm.xsd.SchemaValidator;
import jp.terasoluna.fw.web.rich.springmvc.bind.XMLServletRequestDataBinder;

/**
 * 
 * XMLServletRequestDataBinderの実装クラス
 *
 * <p>
 * createReplaceValuesメソッドに引数が渡されたことを確認する。
 * Stringの配列を返却する。
 * </p>
 *
 */

public class XMLServletRequestDataBinder_XMLServletRequestDataBinderStub03 extends
        XMLServletRequestDataBinder {

    protected int count = 0;
    protected String field01 = null;
    protected String[] replaceValues01 = null;
    protected String field02 = null;
    protected String[] replaceValues02 = null;
    protected String field03 = null;
    protected String[] replaceValues03 = null;
    
    public XMLServletRequestDataBinder_XMLServletRequestDataBinderStub03(Object target, OXMapper oxmapper,
            SchemaValidator schemaValidator, String objectName) {
        super(target, oxmapper, schemaValidator, objectName);
    }

    @Override
    protected String[] createReplaceValues(String field, String[] replaceValues) {
        switch (this.count++) {
        case 0:
            this.field01 = field;
            this.replaceValues01 = replaceValues;
            return new String[]{"jkl", "j"};
        case 1:            
            this.field02 = field;
            this.replaceValues02 = replaceValues;
            return new String[]{"mno", "k"};
        case 2:
            this.field03 = field;
            this.replaceValues03 = replaceValues;
            return new String[]{"pqr", "l"};
        default:
            throw new IllegalArgumentException();
        }
        
    }
    
}
