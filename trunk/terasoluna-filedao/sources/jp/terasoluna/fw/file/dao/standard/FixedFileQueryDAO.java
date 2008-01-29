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

import jp.terasoluna.fw.file.dao.FileLineIterator;

/**
 * �Œ蒷�t�@�C���ǎ�p��FileLineIterator�����N���X�B
 * <p>
 * �Œ蒷�t�@�C������t�@�C���s�I�u�W�F�N�g�𐶐�����<code>FileLineIterator</code>
 * �𐶐����邽�߂̃N���X�ł���B�����ɂ̓f�[�^��ǂݎ��Œ蒷�t�@�C���̃p�X��
 * �t�@�C���s�I�u�W�F�N�g�N���X��ݒ肷�邱�ƁB
 * </p>
 * 
 * �s�I�u�W�F�N�g�ɐݒ�o����A�m�e�[�V�����̐�����{@link FixedFileLineIterator}
 * ��JavaDoc���Q�l���ĉ������B
 * 
 */
public class FixedFileQueryDAO extends AbstractFileQueryDAO {

    /**
     * FileLineIterator�擾�p���\�b�h�B
     * 
     * @param <T> 1�s���̕�������i�[����t�@�C���s�I�u�W�F�N�g�N���X
     * @param fileName �t�@�C����
     * @param clazz �p�����[�^�N���X
     * @return �Œ蒷�t�@�C���ǎ�p�I�u�W�F�N�g
     */
    @Override
    public <T> FileLineIterator<T> execute(String fileName, Class<T> clazz) {

        //FileLineIterator�𐶐�����B
        FixedFileLineIterator<T> fileLineIterator =
            new FixedFileLineIterator<T>(fileName, clazz, getColumnParserMap());
        
        //FileLineIterator������������B
        fileLineIterator.init();

        return fileLineIterator;
    }
}