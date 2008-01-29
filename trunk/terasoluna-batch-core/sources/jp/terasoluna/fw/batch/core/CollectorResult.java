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
 * �Ώۃf�[�^�擾���ʁB
 * 
 * <p>�Ώۃf�[�^�擾���ʂ́A���^�[���R�[�h�ƃR���N�^�Ŏ擾���ꂽ�f�[�^������
 * �ێ�����B</p>
 *
 */
public class CollectorResult {

    /**
     * ���^�[���R�[�h�B
     */
    private ReturnCode returnCode;
    
    /**
     * �R���N�^�Ŏ擾���ꂽ�f�[�^�����B
     */
    private int collected;

    /**
     * �R���X�g���N�^�B
     *
     * @param returnCode �������ʂɐݒ肷�郊�^�[���R�[�h
     * @param collected �R���N�^�Ŏ擾���ꂽ�f�[�^����
     */
    public CollectorResult(ReturnCode returnCode, int collected) {
        this.returnCode = returnCode;
        this.collected = collected;
    }

    /**
     * ���^�[���R�[�h���擾����B
     * 
     * @return ���^�[���R�[�h
     */
    public ReturnCode getReturnCode() {
        return returnCode;
    }

    /**
     * �Ώۃf�[�^�擾���ʂ̕�����\�����擾����B
     * 
     * @return �Ώۃf�[�^�擾���ʂ̕�����\��
     */
    public String toString() {
        StringBuilder buffer = new StringBuilder();
        buffer.append("ReturnCode: " + getReturnCode() + " ");

        return buffer.toString();
    }

    /**
     * �R���N�^�Ŏ擾���ꂽ�f�[�^�������擾����B
     * 
     * @return �R���N�^�Ŏ擾���ꂽ�f�[�^����
     */
    public int getCollected() {
        return collected;
    }

}
