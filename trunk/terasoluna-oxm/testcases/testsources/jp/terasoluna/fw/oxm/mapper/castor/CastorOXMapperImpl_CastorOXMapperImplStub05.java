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

import java.net.URL;

import jp.terasoluna.fw.oxm.mapper.castor.CastorOXMapperImpl;

/**
 * CastorOXMapperImpl�̌p���N���X�B
 * 
 * <p>
 * getUrl���\�b�h�Ɉ������n���ꂽ���Ƃ��m�F����B
 * </p>
 * <p>
 * ����path�ɐݒ肳�ꂽ�t�@�C���p�X����AURL�I�u�W�F�N�g�𐶐�����B
 * </p>
 * 
 */
public class CastorOXMapperImpl_CastorOXMapperImplStub05 extends CastorOXMapperImpl {
    
    protected Class mappingClass = null;
 
    // �}�b�s���O��`�t�@�C���̃p�X
    protected String path = null;

    @Override
    protected URL getUrl(Class mappingClass) {
        
        this.mappingClass = mappingClass;
        
        // Castor�}�b�s���O��`�t�@�C�����擾����
        URL mappingURL = Thread.currentThread().getContextClassLoader().getResource(path);

        return mappingURL;
    }
    


}
