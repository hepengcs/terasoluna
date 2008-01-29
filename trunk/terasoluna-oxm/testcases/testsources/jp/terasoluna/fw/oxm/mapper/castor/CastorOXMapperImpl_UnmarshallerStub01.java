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

import java.io.Reader;

import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.Unmarshaller;
import org.exolab.castor.xml.ValidationException;
import org.w3c.dom.Node;

/**
 * 
 * Unmarshallerのスタブ。
 *
 * <p>
 * Unmarshaller,unmarshalメソッド呼び出し時に、例外をスローする
 * </p>
 *
 */
public class CastorOXMapperImpl_UnmarshallerStub01 extends Unmarshaller{

    protected Node node = null;
    
    protected Reader reader = null;
    
    @Override
    public Object unmarshal(Node node) throws MarshalException, ValidationException {
        this.node = node;
        throw new MarshalException("");
    }

    @Override
    public Object unmarshal(Reader reader) throws MarshalException, ValidationException {
        this.reader = reader;
        throw new MarshalException("");
    }
    



}
