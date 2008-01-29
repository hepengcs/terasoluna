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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.validator.Field;
import org.apache.commons.validator.Form;
import org.apache.commons.validator.ValidatorException;
import org.apache.commons.validator.ValidatorResources;
import org.apache.commons.validator.ValidatorResult;
import org.apache.commons.validator.ValidatorResults;

import jp.terasoluna.fw.validation.springmodules.CommonsValidatorEx;
import jp.terasoluna.utlib.UTUtil;
import junit.framework.TestCase;

/**
 * {@link jp.terasoluna.fw.validation.springmodules.CommonsValidatorEx}
 * �N���X�̃e�X�g�B
 * 
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4>
 * Jakaruta-Commons��Validator�g���N���X�B
 * <p>
 * 
 * @see jp.terasoluna.fw.validation.springmodules.CommonsValidatorEx
 */
public class CommonsValidatorExTest extends TestCase {

    /**
     * ���̃e�X�g�P�[�X�����s����ׂ� GUI �A�v���P�[�V�������N������B
     * 
     * @param args
     *            java �R�}���h�ɐݒ肳�ꂽ�p�����[�^
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(CommonsValidatorExTest.class);
    }

    /**
     * �������������s���B
     * 
     * @throws Exception
     *             ���̃��\�b�h�Ŕ���������O
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    /**
     * �I���������s���B
     * 
     * @throws Exception
     *             ���̃��\�b�h�Ŕ���������O
     * @see junit.framework.TestCase#tearDown()
     */
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * �R���X�g���N�^�B
     * 
     * @param name
     *            ���̃e�X�g�P�[�X�̖��O�B
     */
    public CommonsValidatorExTest(String name) {
        super(name);
    }

    /**
     * testGetValidatorException01() <br>
     * <br>
     * 
     * (����n) <br>
     * �ϓ_�FA,C <br>
     * <br>
     * ���͒l�F(�O�����) this.validatorException:ValidatorException<br>
     * 
     * <br>
     * ���Ғl�F(�߂�l)
     * ValidatorException:this.validatorException�Ɠ���C���X�^���X��ValidatorException<br>
     * 
     * <br>
     * �����ɐݒ肳��Ă���validatorException��ԋp���邱�Ƃ̃e�X�g�B <br>
     * 
     * @throws Exception
     *             ���̃��\�b�h�Ŕ���������O
     */
    public void testGetValidatorException01() throws Exception {
        // �O����
        ValidatorResources resources = new ValidatorResources();
        CommonsValidatorEx commonsValidatorEx = new CommonsValidatorEx(
                resources, null);
        ValidatorException validatorException = new ValidatorException();
        UTUtil.setPrivateField(commonsValidatorEx, "validatorException",
                validatorException);

        // �e�X�g���{
        ValidatorException resultValidatorException = commonsValidatorEx
                .getValidatorException();

        // ����
        assertSame(validatorException, resultValidatorException);
    }

    /**
     * testValidate01() <br>
     * <br>
     * 
     * (����n) <br>
     * �ϓ_�FA <br>
     * <br>
     * ���͒l�F(�O�����) super.validate():��O���X���[���Ȃ�<br>
     * 
     * <br>
     * ���Ғl�F(�߂�l) ValidatorResults:super.validate()�̌���<br>
     * (��ԕω�) this.validatorException:null<br>
     * 
     * <br>
     * super.validate()����O���X���[���Ȃ��ꍇ�Asuper.validate()�̌��ʂ�ԋp���邱�Ƃ̃e�X�g�B�B
     * <br>
     * 
     * @throws Exception
     *             ���̃��\�b�h�Ŕ���������O
     */
    public void testValidate01() throws Exception {
        // �O����
        CommonsValidatorEx_ValidatorResourcesStub01 resources = 
            new CommonsValidatorEx_ValidatorResourcesStub01();
        Form form = new Form();
        resources.setForm(form);

        // super.validate()�̃I�[�o�[���C�h�͂ł��Ȃ����߁A
        // Field�̃��b�N�N���X���쐬���āA
        // super.validate()���Ăяo���Afield.validate()��
        // ���ʂ𑀍삷��B
        // super.validate()�́Afield.validate()�̌��ʂ��}�[�W���ĕԋp���Ă���B
        CommonsValidatorEx_FieldStub01 field =
            new CommonsValidatorEx_FieldStub01();
        List<Field> lFields = new ArrayList<Field>();
        lFields.add(field);
        UTUtil.setPrivateField(form, "lFields", lFields);

        ValidatorResults validatorResults = new ValidatorResults();
        Map<String, ValidatorResult> hResults
            = new HashMap<String, ValidatorResult>();
        ValidatorResult validatorResult = new ValidatorResult(field);
        hResults.put("test", validatorResult);
        UTUtil.setPrivateField(validatorResults, "hResults", hResults);
        
        field.validateReturn = validatorResults;
        
        CommonsValidatorEx commonsValidatorEx = new CommonsValidatorEx(
                resources, "formName");

        // �e�X�g���{
        ValidatorResults result = commonsValidatorEx.validate();

        // ����
        // result���Afield.validate()�̌��ʂ��܂�ł��邩���m�F����B
        Map resultHResults = (Map) UTUtil.getPrivateField(result, "hResults");
        assertEquals(1, resultHResults.size());
        assertSame(validatorResult, resultHResults.get("test"));
    }

    /**
     * testValidate02() <br>
     * <br>
     * 
     * (����n) <br>
     * �ϓ_�FA,G <br>
     * <br>
     * ���͒l�F(�O�����) super.validate():ValidatorException���X���[����<br>
     * 
     * <br>
     * ���Ғl�F(��ԕω�) ��O:super.validate()���X���[����ValidatorException<br>
     * (��ԕω�) this.validatorException:super.validate()���X���[����ValidatorException<br>
     * 
     * <br>
     * super.validate()��ValidatorException���X���[����ꍇ�A���̗�O�𑮐��ɐݒ肵����A���̂܂܃X���[���邱�Ƃ̃e�X�g�B
     * <br>
     * 
     * @throws Exception
     *             ���̃��\�b�h�Ŕ���������O
     */
    public void testValidate02() throws Exception {
        // �O����
        CommonsValidatorEx_ValidatorResourcesStub01 resources = 
            new CommonsValidatorEx_ValidatorResourcesStub01();
        Form form = new Form();
        resources.setForm(form);

        // super.validate()�̃I�[�o�[���C�h�͂ł��Ȃ����߁A
        // Field�̃��b�N�N���X���쐬���āA
        // super.validate()���Ăяo���Afield.validate()��
        // ���ʂ𑀍삷��B
        // super.validate()�́Afield.validate()���X���[����validatorException��
        // ���̂܂܃X���[���Ă���B
        CommonsValidatorEx_FieldStub01 field =
            new CommonsValidatorEx_FieldStub01();
        List<Field> lFields = new ArrayList<Field>();
        lFields.add(field);
        UTUtil.setPrivateField(form, "lFields", lFields);

        field.validatorException = new ValidatorException();
        
        CommonsValidatorEx commonsValidatorEx = new CommonsValidatorEx(
                resources, "formName");

        // �e�X�g���{
        try {
            commonsValidatorEx.validate();
            fail();
        } catch (ValidatorException e) {
            // ����
            // field.validate()���X���[����ValidatorException�Ɠ���C���X�^���X�����m�F����B
            assertSame(field.validatorException, e);
            assertSame(e, commonsValidatorEx.getValidatorException());
        }
    }
    
    /**
     * testClear01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA,C
     * <br><br>
     * ���͒l�F(�O�����) this.validatorException:ValidatorException<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) this.validatorException:null<br>
     *         
     * <br>
     * �����ɐݒ肳��Ă���validatorException���N���A���邱�Ƃ̃e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testClear01() throws Exception {
        // �O����
        ValidatorResources resources = new ValidatorResources();
        CommonsValidatorEx commonsValidatorEx = new CommonsValidatorEx(
                resources, null);
        ValidatorException validatorException = new ValidatorException();
        UTUtil.setPrivateField(commonsValidatorEx, "validatorException",
                validatorException);

        // �e�X�g���{
        commonsValidatorEx.clear();
        ValidatorException result = (ValidatorException)UTUtil
            .getPrivateField(commonsValidatorEx, "validatorException");

        // ����
        assertNull(result);
    }
}
