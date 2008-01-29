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

import java.io.IOException;
import java.io.Writer;

import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.ValidationException;

/**
 * Marshaller�̃X�^�u�B
 * 
 * <p>
 * marshal���\�b�h�Ăяo�����ɗ�O���X���[����B
 * </p>
 * 
 */
public class CastorOXMapperImpl_MarshallerStub01 extends Marshaller{

    protected Object in = null;
    
    public CastorOXMapperImpl_MarshallerStub01(Writer writer) throws IOException {
        super(writer);
    }
    
    @Override
    public void marshal(Object in) throws MarshalException, ValidationException {
        this.in = in;
        throw new MarshalException("");
    }

}
