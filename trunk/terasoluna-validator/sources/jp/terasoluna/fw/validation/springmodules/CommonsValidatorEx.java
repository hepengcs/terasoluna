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
import org.apache.commons.validator.ValidatorResources;
import org.apache.commons.validator.ValidatorResults;

/**
 * Jakarta Commons��Validator�p���N���X
 * 
 * <p>
 * validate()���\�b�h���I�[�o�[���C�h���Ă���B
 * �e�N���X��validate()���\�b�h���Ăяo�����ۂ�
 * validation.xml�Ȃǂ̋L�q�~�X�ɂ��A�o���f�[�g��O�����������ꍇ�A
 * ���̗�O�C���X�^���X�𑮐��ɕێ�����B
 * </p>
 * 
 * <p>
 * �g�p�O��clear()���\�b�h�ŏ����������ꍇ�̓X���b�h�Z�[�t�Ƃ��Ďg�p�\�B
 * </p>
 * 
 * <p>
 * �{�N���X�́ACommonsValidatorEx�ɂ���Đ��������B
 * �܂��A�����ɕێ�������O�C���X�^���X�́A
 * DefaultValidatorFactoryEx�ɂ���ė��p�����B
 * </p>
 * 
 * <p>
 * �{�N���X�𗘗p����ꍇ�ɕK�v��Bean��`�t�@�C���̐ݒ�ɂ��ẮA
 * DefaultValidatorFactoryEx��JavaDoc�̋L�q���Q�Ƃ̂��ƁB
 * </p>
 * 
 * 
 */
public class CommonsValidatorEx extends Validator {

    /**
     * �V���A���o�[�W����ID
     */
    private static final long serialVersionUID = -7315991856716383283L;
    
    /**
     * XML�f�[�^�̌��؎��ɔ���������O
     */
    private ValidatorException validatorException = null;

    /**
     * �R���X�g���N�^
     * @param resources ���؃��\�[�X
     * @param formName �t�H�[����
     */
    public CommonsValidatorEx(ValidatorResources resources, String formName) {
        super(resources, formName);
    }

    /**
     * XML�f�[�^�̌��؎��ɔ���������O���擾����
     * @return XML�f�[�^�̌��؎��ɔ���������O
     */
    public ValidatorException getValidatorException() {
        return validatorException;
    }

    /**
     * ���؃��\�b�h
     * Validator��validate()���\�b�h���Ăяo���A 
     * �G���[����������N���X�̑����ɐݒ肵�ăX���[����B 
     * @return ���،���
     * @throws XML�f�[�^�̌��؎��ɔ���������O
     */
    @Override
    public ValidatorResults validate() throws ValidatorException {
        try {
            return super.validate();
        } catch (ValidatorException e) {
            // XML�f�[�^�̌��؎��ɗ�O�����������ꍇ�́A��������
            // ��O��validatorException�����ɕێ����A�X���[����
            validatorException = e;
            throw e;
        }
    }
    
    /**
     * �N���A���\�b�h
     * Validator��clear()���\�b�h���Ăяo���A 
     * �N���X��validatorException������null�ɐݒ肵�܂��B 
     */
    @Override
    public void clear() {
        super.clear();
        this.validatorException = null;
    }
}
