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

package jp.terasoluna.fw.dao;

import org.springframework.dao.DataAccessException;

/**
 * QueryDAO�C���^�[�t�F�[�X�̔z��ϊ����ɗ�O�����������ꍇ��Runtime��O�N���X�B
 * 
 * �����̃N���X�ƁA�߂�l�̌^����v���Ȃ��ꍇ�A
 * �����Map�̔z��ϊ�����DAO�����N���X���瓊�������O�ł���B
 * 
 */
public class IllegalClassTypeException extends DataAccessException {

    /**
     * �V���A���o�[�W����ID�B
     */
    private static final long serialVersionUID = -3147888263699426883L;
    
    /**
     * �G���[���b�Z�[�W
     */
    public static final String ERROR_ILLEGAL_CLASS_TYPE
        = "The illegal Class Type of the argument.";

    /**
     * �R���X�g���N�^�B
     */
    public IllegalClassTypeException() {
        super(ERROR_ILLEGAL_CLASS_TYPE);
    }

    /**
     * �R���X�g���N�^�B
     *
     * @param message ���b�Z�[�W
     */
    public IllegalClassTypeException(String message) {
        super(message);
    }

    /**
     * �R���X�g���N�^�B
     *
     * @param cause �����ƂȂ�����O
     */
    public IllegalClassTypeException(Throwable cause) {
        super(ERROR_ILLEGAL_CLASS_TYPE, cause);
    }

    /**
     * �R���X�g���N�^�B
     *
     * @param message ���b�Z�[�W
     * @param cause �����ƂȂ�����O
     */
    public IllegalClassTypeException(String message, Throwable cause) {
        super(message, cause);
    }
}
