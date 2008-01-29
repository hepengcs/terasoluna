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

import org.apache.commons.validator.Field;
import org.apache.commons.validator.ValidatorAction;

/**
 * Terasoluna���ʂ̃o���f�[�V�����Ŏg�p����鋤�ʃG���[�C���^�t�F�[�X�B
 * 
 */
public interface ValidationErrors {

    /**
     * �G���[����ǉ�����B
     * 
     * �e�t���[�����[�N�̃G���[�I�u�W�F�N�g�ɁA
     * �G���[����ǉ����鏈������������B
     * 
     * @param bean �����Ώۂ�JavaBean�C���X�^���X
     * @param field �t�B�[���h�C���X�^���X
     * @param va Validator�ɂ��p�ӂ��ꂽValidatorAction
     */
    void addError(Object bean, Field field, ValidatorAction va);
}
