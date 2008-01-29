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

package jp.terasoluna.fw.validation.springmodules;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.validator.Field;
import org.apache.commons.validator.ValidatorAction;
import org.springframework.validation.Errors;
import org.springmodules.validation.commons.MessageUtils;

import jp.terasoluna.fw.validation.ValidationErrors;

/**
 * {@link jp.terasoluna.fw.validation.FieldChecks}�N���X�ɂāA
 * Spring�t���[�����[�N��{@link org.springframework.validation.Errors}
 * �C���^�t�F�[�X�ɃG���[����ǉ����邽�߂̃N���X�B
 * 
 * <p>�{�N���X��{@link jp.terasoluna.fw.validation.FieldChecks}�N���X
 * �ɂĎg�p����Ă���G���[�C���^�t�F�[�X
 * {@link jp.terasoluna.fw.validation.ValidationErrors}
 * �̎����N���X�ł���B
 * {@link jp.terasoluna.fw.validation.FieldChecks}�N���X�ɂ�
 * Validator�����N���X�̊g���N���X�ł���
 * {@link jp.terasoluna.fw.validation.springmodules.DefaultValidatorFactoryEx}
 * ���g�p����ƁA�{�N���X���g�p�����B</p>
 * 
 * @see jp.terasoluna.fw.validation.FieldChecks
 * @see jp.terasoluna.fw.validation.springmodules.DefaultValidatorFactoryEx
 */
public class SpringValidationErrors implements ValidationErrors {
    
    /**
     * �{�N���X�ŗ��p���郍�O�B
     */
    private static Log log = 
        LogFactory.getLog(SpringValidationErrors.class);
    
    /**
     * ���b�v����Spring�t���[�����[�N�̃G���[�I�u�W�F�N�g�B
     */
    private Errors errors = null;
    
    /**
     * Spring�t���[�����[�N�̃G���[�I�u�W�F�N�g��ݒ肷��B
     * @param errors �ݒ肷�� errors�B
     */
    public void setErrors(Errors errors) {
        this.errors = errors;
    }

    /**
     * Spring�t���[�����[�N�̃G���[�I�u�W�F�N�g���擾����B
     * @return errors ��߂��܂��B
     */
    public Errors getErrors() {
        return errors;
    }
    
    /**
     * �G���[��ǉ�����B
     * ���b�v����Spring�t���[�����[�N�̃G���[�I�u�W�F�N�g��
     * �G���[��ǉ�����B
     * 
     * @param bean ���ؒ��̃I�u�W�F�N�g�B
     * @param field commons-validator��Field�I�u�W�F�N�g�B
     * @param va  commons-validator��ValidatorAction�I�u�W�F�N�g�B
     */
    public void addError(Object bean, Field field, ValidatorAction va) {
        // �G���[���̎��o��
        String fieldCode = field.getKey();
        String errorCode = MessageUtils.getMessageKey(va, field);
        Object[] args = MessageUtils.getArgs(va, field);

        if (log.isDebugEnabled()) {
            log.debug("Rejecting value [field='" + fieldCode + "', errorCode='"
                    + errorCode + "']");
        }

        // �G���[�ǉ�
        errors.rejectValue(fieldCode, errorCode, args, errorCode);
    }
}
