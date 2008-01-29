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

package jp.terasoluna.fw.validation;

import jp.terasoluna.fw.validation.MultiFieldValidator;

/**
 * {@link FieldChecksExtend}���e�X�g���邽�߂�{@link MultiFieldValidator}�����N���X�B
 * 
 */
public class FieldChecks_MultiFieldValidatorImpl01 implements
        MultiFieldValidator {

    /**
     * <code>validate</code>���\�b�h�̌��ʂƂ���l�B
     */
    protected static boolean result = false;

    /**
     * <code>validate</code>���\�b�h���R�[�����ꂽ�J�E���g�B
     */
    protected static int validateCalledCount = 0;
    
    /**
     * <code>validate</code>���\�b�h�̑������̒l�B
     */
    protected static Object value = null;

    /**
     * <code>validate</code>���\�b�h�̑������̒l�B
     */
    protected static Object[] fields = null;
    
    /**
     * �����t�B�[���h�̑��֓��̓`�F�b�N�����s����B
     * <br>
     * ���ؑΏۂ̒l�͑������œn�����B���؂ɕK�v�ȑ��̃t�B�[���h��
     * �l�͑������ɔz��Ƃ��ēn�����B���؃G���[�̏ꍇ�� <code>false</code>
     * ��ԋp���邱�ƁB
     *
     * @param value ���ؑΏۂ̒l
     * @param fields ���؂ɕK�v�ȑ��̃t�B�[���h�̒l�z��
     * @return �G���[���Ȃ���� <code>true</code>
     */
    @SuppressWarnings("hiding")
    public boolean validate(Object value, Object[] fields) {
        FieldChecks_MultiFieldValidatorImpl01.validateCalledCount++;
        FieldChecks_MultiFieldValidatorImpl01.value = value;
        FieldChecks_MultiFieldValidatorImpl01.fields = fields;
        return FieldChecks_MultiFieldValidatorImpl01.result;
    }
}
