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
 * �J�����t�H�[�}�b�g(�t�@�C�������j���s���N���X�B
 * 
 * <p>
 * �t�@�C���s�I�u�W�F�N�g����f�[�^���擾���A�������FileUpdateDAO�ɕԋp����B
 * </p>
 * 
 */
public class NullColumnFormatter implements ColumnFormatter {

    /**
     * String�^�̃t�H�[�}�b�g�������s���A�������ԋp����B
     * 
     * @param t �t�@�C���s�I�u�W�F�N�g
     * @param method �J�����t�H�[�}�b�g���s�������̃Q�b�^���\�b�h
     * @param columnFormat �J�����t�H�[�}�b�g�p�̕�����B���̃N���X�ł͒P�ɖ��������B
     * @return ������
     * @throws IllegalArgumentException �t�@�C���s�I�u�W�F�N�g��getter���\�b�h�̃A�N�Z�X�Ɏ��s�����Ƃ�
     * @throws IllegalAccessException �t�@�C���s�I�u�W�F�N�g�ւ̐ݒ肪���s�����Ƃ�
     * @throws InvocationTargetException �t�@�C���s�I�u�W�F�N�g�̃��\�b�h����O���X���[�����Ƃ�
     */
    public String format(Object t, Method method, String columnFormat)
            throws IllegalArgumentException, IllegalAccessException,
            InvocationTargetException {
        return method.invoke(t).toString();
    }

}
