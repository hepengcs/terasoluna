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

package jp.terasoluna.fw.batch.core;

import jp.terasoluna.fw.batch.openapi.ReturnCode;

/**
 * �������ʌ������J�E���g����N���X�B
 * 
 * <p>�r�W�l�X���W�b�N��A�O�����E�㏈���Ȃǂ̃T�|�[�g�����ȂǁA
 * <code>ReturnCode</code>��ԋp������̂ɂ��āA���̎��s�������J�E���g����B
 * <code>ReturnCode</code> �̂����A����p���i<code>ReturnCode.NORMAL_CONTINUE
 * </code>�j�A�ُ�p���i<code>ReturnCode.ERROR_CONTINUE</code>�j�̂݃J�E���g���A
 * ���̌�����ێ�����B</p>
 * 
 */
public class ResultCounter {

    /**
     * ����p���̏������ʌ����B
     */
    private int normalContinueCount = 0;

    /**
     * �ُ�p���̏������ʌ����B
     */
    private int errorContinueCount = 0;

    /**
     * �����������J�E���g����B
     * 
     * @param returnCode �J�E���g���郊�^�[���R�[�h
     */
    public void count(ReturnCode returnCode) {
        switch (returnCode) {
            case NORMAL_CONTINUE :
                normalContinueCount++;
                break;
            case ERROR_CONTINUE :
                errorContinueCount++;
                break;
            default :
                // NORMAL_CONTINUE�AERROR_CONTINUE�ȊO�̓J�E���g���Ȃ��B
                break;
        }
    }

    /**
     * �ُ�p���̏������ʌ������擾����B
     * 
     * @return �ُ�p���̏������ʌ���
     */
    public int getErrorContinueCount() {
        return errorContinueCount;
    }

    /**
     * ����p���̏������ʌ������擾����B
     * 
     * @return ����p���̏������ʌ���
     */
    public int getNormalContinueCount() {
        return normalContinueCount;
    }
    
    /**
     * ���������擾����B
     * 
     * @return ������
     */
    public int getToralCount() {
        return normalContinueCount + errorContinueCount;
    }
}
