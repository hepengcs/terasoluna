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

package jp.terasoluna.fw.web.rich.context.exception;

/**
 * �擾����Ɩ��v���p�e�B�̌^�Ǝw�肳�ꂽ�^��������ꍇ��Runtime��O�N���X�B
 * 
 */
public class IllegalContextPropertyClassTypeException extends RuntimeException {

    /**
     * �V���A���o�[�W����ID�B
     */
    private static final long serialVersionUID = -6316012802099459622L;

    /**
     * �G���[���b�Z�[�W
     */
    public static final String ERROR_ILLEGAL_CLASS_TYPE
        = "The illegal Class Type of the context property.";

    /**
     * �R���X�g���N�^�B
     */
    public IllegalContextPropertyClassTypeException() {
        super(ERROR_ILLEGAL_CLASS_TYPE);
    }

    /**
     * �R���X�g���N�^�B
     *
     * @param message ���b�Z�[�W
     */
    public IllegalContextPropertyClassTypeException(String message) {
        super(message);
    }

    /**
     * �R���X�g���N�^�B
     *
     * @param cause �����ƂȂ�����O
     */
    public IllegalContextPropertyClassTypeException(Throwable cause) {
        super(ERROR_ILLEGAL_CLASS_TYPE, cause);
    }

    /**
     * �R���X�g���N�^�B
     *
     * @param message ���b�Z�[�W
     * @param cause �����ƂȂ�����O
     */
    public IllegalContextPropertyClassTypeException(String message, Throwable cause) {
        super(message, cause);
    }
}
