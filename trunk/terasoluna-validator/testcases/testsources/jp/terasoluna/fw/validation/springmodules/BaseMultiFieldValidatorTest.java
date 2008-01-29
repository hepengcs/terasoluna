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

import org.springframework.validation.Errors;

import junit.framework.TestCase;

/**
 * {@link jp.terasoluna.fw.validation.springmodules.
 * BaseMultiFieldValidator} �N���X�̃u���b�N�{�b�N�X�e�X�g�B
 * 
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4>
 * ���փ`�F�b�N���s�Ȃ����ۃN���X�B<br>
 * �O������F�����Ώۂ̃I�u�W�F�N�g�A�G���[�I�u�W�F�N�g��Null�l�ɂȂ�Ȃ��B
 * <p>
 * 
 * @see jp.terasoluna.fw.validation.springmodules.
 * BaseMultiFieldValidator
 */
public class BaseMultiFieldValidatorTest extends TestCase {

    /**
     * ���̃e�X�g�P�[�X�����s����ׂ�
     * GUI �A�v���P�[�V�������N������B
     * 
     * @param args java �R�}���h�ɐݒ肳�ꂽ�p�����[�^
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(BaseMultiFieldValidatorTest.class);
    }

    /**
     * �������������s���B
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    /**
     * �I���������s���B
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     * @see junit.framework.TestCase#tearDown()
     */
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * �R���X�g���N�^�B
     * 
     * @param name ���̃e�X�g�P�[�X�̖��O�B
     */
    public BaseMultiFieldValidatorTest(String name) {
        super(name);
    }

    /**
     * testValidate01()
     * <br><br>
     * 
     * (����n) 
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) obj:not null<br>
     *         (����) error��:not null<br>
     *         (���) super.validate�i�j:�G���[���������Ȃ��p�^�[��<br>
     *                �i������errors��errors.hasErrors()���\�b�h��
     *                FALSE�ɂȂ�悤�ɂ���j<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) super.validate�i�j:
     *         ���\�b�h���Ăяo����邱�Ƃ��m�F����B<br>
     *         (��ԕω�) validateMultiField():
     *         ���\�b�h���Ăяo����邱�Ƃ��m�F����B
     *         �������󂯎�������Ƃ��m�F����B<br>
     *         
     * <br>
     * �P���ڃ`�F�b�N�ŃG���[�����������A
     * ���փ`�F�b�N���\�b�h�����s����p�^�[���̃e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidate01() throws Exception {
        // �O���� --------------------------------------------------------------
        BaseMultiFieldValidatorImpl01 validator = 
            new BaseMultiFieldValidatorImpl01();
        
        // ValidatorFactory�̍쐬
        BaseMultiFieldValidator_ValidatorFactoryStub01 factory =
            new BaseMultiFieldValidator_ValidatorFactoryStub01();
        BaseMutiFieldValidator_ValidatorStub01 commonsValidator =
            new BaseMutiFieldValidator_ValidatorStub01();
        factory.setValidator(commonsValidator);
        
        // ������ݒ�
        validator.setValidatorFactory(factory);
        
        // ���\�b�h����
        Object obj = new Object();
        Errors errors = 
            new BaseMultiFieldValidator_BindExceptionStub01(obj, "");

        // �e�X�g���{ ----------------------------------------------------------
        validator.validate(obj, errors);

        // ����
        assertTrue(commonsValidator.isValidate);
        assertSame(obj, validator.obj);
        assertSame(errors, validator.errors);
    }

    /**
     * testValidate02()
     * <br><br>
     * 
     * (����n) 
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) obj:not null<br>
     *         (����) error��:not null<br>
     *         (���) super.validate�i�j:�G���[����������p�^�[��<br>
     *                �i������errors��errors.hasErrors()���\�b�h��
     *                TRUE�ɂȂ�悤�ɂ���j<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) super.validate�i�j:
     *          ���\�b�h���Ăяo����邱�Ƃ��m�F����B
     *          �������󂯎�������Ƃ��m�F����B<br>
     *         (��ԕω�) validateMultiField():
     *         ���\�b�h���Ăяo����Ȃ����Ƃ��m�F����B<br>
     *         
     * <br>
     * �P���ڃ`�F�b�N�ŃG���[���������A
     * ���փ`�F�b�N���\�b�h�����s����Ȃ��p�^�[���̃e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidate02() throws Exception {
        // �O���� --------------------------------------------------------------
        BaseMultiFieldValidatorImpl01 validator = 
            new BaseMultiFieldValidatorImpl01();
        
        // ValidatorFactory�̍쐬
        BaseMultiFieldValidator_ValidatorFactoryStub01 factory =
            new BaseMultiFieldValidator_ValidatorFactoryStub01();
        BaseMutiFieldValidator_ValidatorStub01 commonsValidator =
            new BaseMutiFieldValidator_ValidatorStub01();
        factory.setValidator(commonsValidator);
        
        // ������ݒ�
        validator.setValidatorFactory(factory);
        
        // ���\�b�h����
        Object obj = new Object();
        Errors errors = 
            new BaseMultiFieldValidator_BindExceptionStub02(obj, "");

        // �e�X�g���{ ----------------------------------------------------------
        validator.validate(obj, errors);

        // ����
        assertTrue(commonsValidator.isValidate);
        assertSame(null, validator.obj);
        assertSame(null, validator.errors);
    }

}
