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

package jp.terasoluna.fw.batch.openapi;

import java.util.LinkedHashMap;

import jp.terasoluna.fw.batch.messages.BLogicMessages;

/**
 * �r�W�l�X���W�b�N���̌��ʂ�ێ�����I�u�W�F�N�g�B <BR>
 * �r�W�l�X���W�b�N�A�W���u�O�����A�W���u�㏈���A �擪�`�����N�O�����A�ŏI�`����
 * �N�㏈�������
 * �R���g���[���u���C�N�n���h���ł̌��ʁB
 *
 */
public class BLogicResult {

    /**
     * �ԋp�R�[�h�B
     */
    private ReturnCode returnCode;

    /**
     * �o�b�`�X�V�p��Map�B
     */
    private LinkedHashMap<String, Object> batchUpdateMap = null;

    /**
     * �W���u�I���R�[�h�B
     */
    private Integer jobExitCode = null;

    /**
     * �r�W�l�X���W�b�N���Ő��������G���[�pBLogicMessages�B
     */
    private BLogicMessages errors = null;

    /**
     * �r�W�l�X���W�b�N���Ő�������郁�b�Z�[�W�pBLogicMessages�B
     */
    private BLogicMessages messages = null;

    /**
     * �R���X�g���N�^�B<BR>
     *
     * @param returnCode �ԋp�R�[�h
     */
    public BLogicResult(ReturnCode returnCode) {
        this.returnCode = returnCode;
    }

    /**
     * �R���X�g���N�^�B<BR>
     *
     * @param returnCode �ԋp�R�[�h
     * @param jobExitCode �W���u�I���R�[�h
     */
    public BLogicResult(ReturnCode returnCode, Integer jobExitCode) {
        this.returnCode = returnCode;
        this.jobExitCode = jobExitCode;
    }

    /**
     * �R���X�g���N�^�B<BR>
     *
     * @param returnCode �ԋp�R�[�h
     * @param jobExitCode �W���u�I���R�[�h
     * @param batchUpdateMap �o�b�`�X�V���X�g
     */
    public BLogicResult(ReturnCode returnCode, Integer jobExitCode, 
            LinkedHashMap<String, Object> batchUpdateMap) {
        this.returnCode = returnCode;
        this.jobExitCode = jobExitCode;
        this.batchUpdateMap = batchUpdateMap;
    }

    /**
     * �R���X�g���N�^�B<BR>
     *  BLogicResult�̃C���X�^���X�͑�ʂɍ쐬����邽�߁A �o�b�`�X�V�p��Map��
     *  �x������������B
     *
     * @param returnCode �ԋp�R�[�h
     * @param batchUpdateMap �o�b�`�X�V���X�g
     */
    public BLogicResult(ReturnCode returnCode, 
            LinkedHashMap<String, Object> batchUpdateMap) {
        this.returnCode = returnCode;
        this.batchUpdateMap = batchUpdateMap;
    }

    /**
     * �p�����[�^�𕶎���ɐݒ�B
     * 
     * @return �r�W�l�X���W�b�N���ʂ̕�����\��
     */
    @Override
    public String toString() {
        StringBuilder buffer = new StringBuilder();
        buffer.append("ReturnCode:");
        buffer.append(getReturnCode());
        buffer.append(" JobExitCode:");
        buffer.append(getJobExitCode());
        buffer.append(" ");
                
        return buffer.toString();
    }

    /**
     * ���^�[���R�[�h��ԋp����B
     *
     * @return ���^�[���R�[�h
     */
    public ReturnCode getReturnCode() {
        return returnCode;
    }

    /**
     * �o�b�`�X�V�p��SQLID�ƃp�����[�^��ێ�����Map��ԋp����B
     *
     * @return SQLID�ƃp�����[�^��ێ�����Map�B
     *          �o�b�`�X�V�p��SQLID�ƃp�����[�^���ݒ肳��Ă��Ȃ��ꍇ�ɂ� 
     *          <code>null</code>
     */
    public LinkedHashMap<String, Object> getBatchUpdateMap() {
        return batchUpdateMap;
    }

    /**
     * �W���u�I���R�[�h��ԋp����B
     *
     * @return �W���u�I���R�[�h�B
     *          �W���u�I���R�[�h���ݒ肳��Ă��Ȃ��ꍇ�ɂ� <code>null</code>
     */
    public Integer getJobExitCode() {
        return jobExitCode;
    }
    
    /**
     * �r�W�l�X���W�b�N���Ő������ꂽ�A�G���[�pBLogicMessages���擾����B
     *
     * @return �r�W�l�X���W�b�N���Ő������ꂽ�A�G���[�pBLogicMessages
     */
    public BLogicMessages getErrors() {
        return this.errors;
    }

    /**
     * �r�W�l�X���W�b�N���Ő������ꂽ�A���b�Z�[�W�pBLogicMessages���擾����B
     *
     * @return �r�W�l�X���W�b�N���Ő������ꂽ�A���b�Z�[�W�pBLogicMessages
     */
    public BLogicMessages getMessages() {
        return this.messages;
    }

    /**
     * �r�W�l�X���W�b�N���Ő������ꂽ�A�G���[�pBLogicMessages��ݒ肷��B
     *
     * @param paramErrors
     *            �r�W�l�X���W�b�N���Ő������ꂽ�A�G���[�pBLogicMessages
     */
    public void setErrors(BLogicMessages paramErrors) {
        this.errors = paramErrors;
    }

    /**
     * �r�W�l�X���W�b�N���Ő������ꂽ�A���b�Z�[�W�pBLogicMessages��ݒ肷��B
     *
     * @param paramMessages
     *            �r�W�l�X���W�b�N���Ő������ꂽ�A���b�Z�[�W�pBLogicMessages
     */
    public void setMessages(BLogicMessages paramMessages) {
        this.messages = paramMessages;
    }
}