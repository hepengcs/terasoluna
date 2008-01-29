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

import org.apache.commons.validator.Validator;
import org.apache.commons.validator.ValidatorException;
import org.springmodules.validation.commons.DefaultBeanValidator;

/**
 * Spring Modules��DefaultBeanValidator�p���N���X�B
 * 
 * <p>
 * DefaultBeanValidator�́ACommons Validator��validator()���\�b�h���Ăяo������A
 * finally���cleanupValidator()���\�b�h���Ăяo���B
 * </p>
 * 
 * <p>
 * �{�N���X�ł́AcleanupValidator()���\�b�h���I�[�o�[���C�h���Ă���B
 * �������̃o���f�[�^�Ƀo���f�[�g��O���i�[����Ă����ꍇ�A
 * ���̗�O�������^�C���̃o���f�[�g��O�Ƀ��b�v���ăX���[����B
 * </p>
 * 
 * <p>
 * �{�N���X�́A
 * cleanupValidator�̈������Ƃ���CommonsValidatorEx�C���X�^���X��
 * �n����邱�Ƃ�O��Ƃ��Ă���B
 * CommonsValidatorEx�N���X�́A
 * DefaultValidatorFactoryEx�N���X�ɂ���Đ��������B
 * ����āA�{�N���X�𗘗p����ꍇ�́A
 * DefaultValidatorFactoryEx�N���X�������ɗ��p���Ȃ���΂Ȃ�Ȃ��B
 * </p>
 * 
 * <p>
 * �{�N���X�𗘗p����ꍇ�ɕK�v��Bean��`�t�@�C���̐ݒ�ɂ��ẮA
 * DefaultValidatorFactoryEx��JavaDoc�̋L�q���Q�Ƃ̂��ƁB
 * </p>
 * 
 *
 */
public class DefaultBeanValidatorEx extends DefaultBeanValidator {

    /**
     * Validator�̃N���[���A�b�v���\�b�h�B
     * validator�̑����ɔ��������݂����ꍇ��ValidatorException���X���[����B
     * 
     * @param validator Commons Validator
     * @throws jp.terasoluna.fw.validation.springmodules.ValidatorException 
     *          �o���f�[�g��O
     */
    @Override
    public void cleanupValidator(Validator validator) {
        
        // validator��CommonsValidatorEx�̏ꍇ
        if (validator instanceof CommonsValidatorEx) {
            // validator�̃`�F�b�N�Ƃ�����������O���擾����
            ValidatorException validatorException = 
                ((CommonsValidatorEx) validator).getValidatorException();
            // ���̗�O���������ꍇ
            if (validatorException != null) {
                throw new jp.terasoluna.fw.validation.springmodules.
                    ValidatorException(validatorException);
            }
        }
    }

}
