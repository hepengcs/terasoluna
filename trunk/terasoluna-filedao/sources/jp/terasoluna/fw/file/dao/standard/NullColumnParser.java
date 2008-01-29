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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * �p�[�Y���s��ȂȂ��J�����p�[�U�[�N���X�B
 * 
 * <p>
 * �ϊ����ʂ��t�@�C���s�I�u�W�F�N�g��String�^�̑����ɒl���i�[����B
 * </p>
 * 
 * <p>�t�H�[�}�b�g������́A���������B</p>
 * 
 */
public class NullColumnParser implements ColumnParser {

    /**
     * �w�肳�ꂽ����������̂܂܃t�@�C���s�I�u�W�F�N�g�Ɋi�[����B
     * 
     * @param column �J�����̕�����
     * @param t �t�@�C���s�I�u�W�F�N�g
     * @param method �J�����̕�������t�@�C���s�I�u�W�F�N�g�Ɋi�[���郁�\�b�h
     * @param columnFormat �p�[�Y����ۂ̃t�H�[�}�b�g������
     * @throws IllegalArgumentException �t�@�C���s�I�u�W�F�N�g��setter���\�b�h�̃A�N�Z�X�Ɏ��s�����Ƃ�
     * @throws IllegalAccessException �t�@�C���s�I�u�W�F�N�g�ւ̐ݒ肪���s�����Ƃ�
     * @throws InvocationTargetException �t�@�C���s�I�u�W�F�N�g�̃��\�b�h����O���X���[�����Ƃ�
     */
    public void parse(String column, Object t, Method method,
            String columnFormat) throws IllegalArgumentException,
            IllegalAccessException, InvocationTargetException {
        method.invoke(t, column);
    }
}
