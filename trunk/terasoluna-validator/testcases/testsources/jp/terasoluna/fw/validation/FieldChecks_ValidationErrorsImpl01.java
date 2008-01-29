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

import java.util.ArrayList;
import java.util.List;

import jp.terasoluna.fw.validation.ValidationErrors;

import org.apache.commons.validator.Field;
import org.apache.commons.validator.ValidatorAction;

/**
 * FieldChecksTest�Ŏg�p����ValidationErrors�̃X�^�u�N���X�B
 */
public class FieldChecks_ValidationErrorsImpl01 implements ValidationErrors {

    /**
     * addError���R�[�������ƃJ�E���g�A�b�v����B
     */
    public int addErrorCount = 0;

    /**
     * �������̒l���X�g�B
     */
    public List beanList = new ArrayList();

    /**
     * �������̒l���X�g�B
     */
    public List fieldList = new ArrayList();

    /**
     * ��O�����̒l���X�g�B
     */
    public List vaList = new ArrayList();

    /**
     * �X�^�u���\�b�h�B�Ăяo���m�F�̂��߁A�Ă΂ꂽ�񐔂ƈ������L���b�V������B
     * @param bean �����Ώۂ�JavaBean�C���X�^���X
     * @param field �t�B�[���h�C���X�^���X
     * @param va Validator�ɂ��p�ӂ��ꂽValidatorAction
     */
    @SuppressWarnings("unchecked")
    public void addError(Object bean, Field field, ValidatorAction va) {
        addErrorCount++;
        beanList.add(bean);
        fieldList.add(field);
        vaList.add(va);
    }

}
