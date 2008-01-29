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

import java.io.InputStreamReader;
import java.io.Reader;

import jp.terasoluna.fw.oxm.mapper.castor.CastorOXMapperImpl;

/**
 * CastorOXMapperImpl�̌p���N���X�B
 * 
 * <p>
 * unmarshal���\�b�h�Ɉ������n���ꂽ���Ƃ̊m�F���s���B
 * </p>
 * 
 */
public class CastorOXMapperImpl_CastorOXMapperImplStub02 extends CastorOXMapperImpl {

    protected Reader reader = null;

    protected Object out = null;

    protected String _charset = null;
    
    @Override
    public void unmarshal(Reader reader, Object out) {
        this.reader = reader;
        this.out = out;
        this._charset = ((InputStreamReader)reader).getEncoding();
    }

}