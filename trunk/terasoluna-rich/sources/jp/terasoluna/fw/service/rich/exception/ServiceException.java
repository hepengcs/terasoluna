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

package jp.terasoluna.fw.service.rich.exception;

/**
 * �T�[�r�X�w�̃N���X�ŐV�K���������O������킷�N���X�B
 * 
 * <p>
 *  �T�[�r�X�w�̃N���X�ŁA�G���[���V�K���������ꍇ�ɗ��p����B
 *  �Ⴆ�΁A�Ɩ��I�Ƀf�[�^�̕s���������N���Ă���ꍇ�ɗ��p����B
 * </p>
 * 
 * <p>
 * �{�N���X�Őݒ肵���G���[�R�[�h����ђu��������́A�v���[���e�[�V�����w�E�N���C�A���g�T�C�h����
 * �K�؂ȃ��b�Z�[�W�ɕϊ����邱�ƁB
 * </p>
 * 
 */
public class ServiceException extends RuntimeException {

    /**
     * �V���A���o�[�W����ID�B
     */
    private static final long serialVersionUID = 3011574799470623851L;

    /**
     * �G���[�R�[�h�B
     */
    private String errorCode = null;

    /**
     * �G���[���b�Z�[�W�̒u��������B
     */
    private String[] options = null;
    
    /**
     * ���������B
     */
    public ServiceException() {
    }

    /**
     * ���������B
     *
     * @param errorCode �G���[�R�[�h
     * @param optionStrings ���b�Z�[�W����{n}��u�����镶����̔z��
     */
    public ServiceException(String errorCode,
                            String... optionStrings) {
        this.errorCode = errorCode;
        this.options = optionStrings;
    }

    /**
     * �G���[�R�[�h���擾����B
     *
     * @return �G���[�R�[�h
     */
    public String getErrorCode() {
        return this.errorCode;
    }

    /**
     * �G���[���b�Z�[�W�̒u����������擾����B
     *
     * @return �G���[���b�Z�[�W�u��������
     */
    public String[] getOptions() {
        return this.options;
    }
}
