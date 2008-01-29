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

package jp.terasoluna.fw.oxm.mapper.castor;

import java.io.Writer;

import jp.terasoluna.fw.oxm.mapper.castor.CastorOXMapperImpl;

import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;

/**
 * CastorOXMapperImpl�̌p���N���X�B
 * 
 */
public class CastorOXMapperImpl_CastorOXMapperImplStub01 extends CastorOXMapperImpl {

    protected Object out = null;

    protected Object in = null;

    protected Unmarshaller unmarshaller = null;

    protected Marshaller marshaller = null;    

    protected Writer writer = null;

    /**
     * �������n���ꂽ���Ƃ̊m�F���s���B<br>
     * CastorOXMapperImpl_UnmarshallerStub01�𐶐�����B
     */
    @Override
    protected Unmarshaller createUnmarshaller(Object out) {
        this.out = out;
        return unmarshaller;
    }
    
    /**
     * �������n���ꂽ���Ƃ̊m�F���s���B<br>
     * CastorOXMapperImpl_MarshallerStub01�𐶐�����B
     */
    @Override
    protected Marshaller createMarshaller(Object in, Writer writer){
        this.in = in;
        this.writer = writer;
        return marshaller;
    }

}
