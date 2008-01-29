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

package jp.terasoluna.fw.file.dao.standard;

import jp.terasoluna.fw.file.dao.FileLineWriter;

/**
 * �t�@�C�������ݗp��FileLineWriter�����N���X�B
 * <p>
 * �Œ蒷�t�@�C���p��FileLineWriter�𐶐�����B
 * </p>
 * 
 * �s�I�u�W�F�N�g�ɐݒ�o����A�m�e�[�V�����̐�����{@link FixedFileLineWriter}
 * ��JavaDoc���Q�l���ĉ������B
 * 
 */
public class FixedFileUpdateDAO extends AbstractFileUpdateDAO {

    /**
     * FileLineWriter�擾�p���\�b�h�B
     * 
     * @param <T> 1�s���̕�������i�[����t�@�C���s�I�u�W�F�N�g�N���X
     * @param fileName �t�@�C����
     * @param clazz �p�����[�^�N���X
     * @return �Œ蒷�t�@�C���p��FileLineWriter
     */
    @Override
    public <T> FileLineWriter<T> execute(String fileName, Class<T> clazz) {

        //FileLineWriter�𐶐�����B
        FixedFileLineWriter<T> fileLineWriter = new FixedFileLineWriter<T>(
                fileName, clazz, getColumnFormatterMap());
        
        return fileLineWriter;
    }

}
