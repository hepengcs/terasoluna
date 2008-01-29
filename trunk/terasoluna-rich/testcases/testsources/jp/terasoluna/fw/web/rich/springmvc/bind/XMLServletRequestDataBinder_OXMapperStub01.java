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
import java.io.Reader;
import java.io.Writer;

import jp.terasoluna.fw.oxm.mapper.OXMapper;

import org.w3c.dom.Document;

/**
 * OXMapperインタフェース実装クラスのスタブ。
 * 
 * <p>
 * unmarshalメソッドに引数が渡されたことを確認する。
 * </p>
 * 
 */
public class XMLServletRequestDataBinder_OXMapperStub01 implements
        OXMapper {

    protected InputStream is = null;

    protected String argCharset = null;
    
    protected Object out = null;

    protected Document doc = null;

    public void unmarshal(InputStream is, String argCharset, Object out) {
        this.is = is;
        this.argCharset = argCharset;
        this.out = out;
    }
    
    public void unmarshal(Document doc, Object out) {
        this.doc = doc;
        this.out = out;
    }
    
    public void marshal(Object in, Writer writer) {

    }

    public void unmarshal(Reader reader, Object out) {

    }

}
