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

package jp.terasoluna.fw.web.rich.springmvc.servlet.view.castor;

import java.io.InputStream;
import java.io.Reader;
import java.io.Writer;

import jp.terasoluna.fw.oxm.mapper.OXMapper;

import org.w3c.dom.Document;


/**
 * OXMapperインタフェース実装クラスのスタブ。
 *
 */
public class CastorView_OXMapperImplStub01 implements OXMapper {
    
    protected boolean isMarshal = false;

    public void unmarshal(Document arg0, Object arg1) {
    }

    public void unmarshal(Reader arg0, Object arg1) {
    }

    public void unmarshal(InputStream arg0, String arg1, Object arg2) {
    }

    /**
     * marshalの呼び出し確認。
     */
    public void marshal(Object arg0, Writer arg1) {
        isMarshal = true;
    }

}
