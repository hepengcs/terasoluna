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

package jp.terasoluna.fw.batch.validation;

import org.springframework.validation.BindException;

import jp.terasoluna.fw.batch.core.JobException;

/**
 * ���̓`�F�b�N�p��O�N���X�B
 * 
 * <p>���̓`�F�b�N�������A���̓`�F�b�N�G���[�����o���ꂽ�ꍇ�A
 * ���̗�O�𔭐�����<code>Collector</code>�����𒆒f������B</p>
 * <p>���̃N���X�ł́A<code>BindException</code>�A��O�������ɓ��͂��ꂽ���Ă���
 * ���̓f�[�^�A��ێ�����B</p>
 * <p>���̓`�F�b�N�G���[�̏ڍׂȓ��e�͌�����O��<code>BindException</code>�����
 * ���邱�Ƃŕ��͂��邱�Ƃ��ł���B</p>
 *
 */
public class ValidateException extends JobException {

    /**
     * Serializable�p�o�[�W����ID
     */
    private static final long serialVersionUID = -841154748141051078L;

    /**
     * ���̓f�[�^
     */
    private Object inputData = null;

    /**
     * �R���X�g���N�^�B
     *
     * @param bindException ������O
     */
    public ValidateException(BindException bindException) {
        super(bindException);
    }

    /**
     * �R���X�g���N�^�B
     *
     * @param bindException ������O
     * @param inputData ���̓f�[�^
     */
    public ValidateException(BindException bindException, Object inputData) {
        super(bindException);
        this.inputData = inputData;
    }

    /**
     * ���̓f�[�^���擾����B
     *
     * @return ���̓f�[�^
     */
    public Object getInputData() {
        return inputData;
    }

}
