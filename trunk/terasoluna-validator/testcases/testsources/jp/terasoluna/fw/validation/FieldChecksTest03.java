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

import jp.terasoluna.utlib.LogUTUtil;
import jp.terasoluna.fw.validation.FieldChecks;
import junit.framework.TestCase;

import org.apache.commons.validator.Field;
import org.apache.commons.validator.ValidatorAction;
import org.apache.commons.validator.ValidatorException;
import org.apache.commons.validator.Var;

/**
 * {@link jp.terasoluna.fw.validation.FieldChecks}
 * �N���X�̃u���b�N�{�b�N�X�e�X�g�B
 *
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4>
 * TERASOLUNA�̓��̓`�F�b�N�@�\�ŋ��ʂɎg�p����錟�؃��[���N���X�B
 * <p>
 *
 * @see jp.terasoluna.fw.validation.FieldChecks
 */
public class FieldChecksTest03 extends TestCase {

    /**
     * �e�X�g�p�C���X�^���X�B
     */
    private ValidatorAction va = null;

    /**
     * �e�X�g�p�C���X�^���X�B
     */
    private Field field = null;

    /**
     * �e�X�g�p�C���X�^���X�B
     */
    private FieldChecks_ValidationErrorsImpl01 errors = null;

    /**
     * ���̃e�X�g�P�[�X�����s����ׂ�
     * GUI �A�v���P�[�V�������N������B
     *
     * @param args java �R�}���h�ɐݒ肳�ꂽ�p�����[�^
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(FieldChecksTest03.class);
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
        va = new ValidatorAction();
        field = new Field();
        errors = new FieldChecks_ValidationErrorsImpl01();
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
    public FieldChecksTest03(String name) {
        super(name);
    }

    /**
     * testValidateLong01()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC,F
     * <br><br>
     * ���͒l�F(����) bean:null<br>
     *         (����) va:not null<br>
     *         (����) field:not null<br>
     *         (����) errors:not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *         (��ԕω�) errors:�Ăяo����Ȃ�<br>
     *
     * <br>
     * ������bean��null�̏ꍇ�Atrue���ԋp����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateLong01() throws Exception {
        // �e�X�g���{
        // ����
        assertTrue(new FieldChecks().validateLong(null, va, field, errors));

        // addErrors�m�F
        assertEquals(0, errors.addErrorCount);
    }

    /**
     * testValidateLong02()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC,F
     * <br><br>
     * ���͒l�F(����) bean:""<br>
     *         (����) va:not null<br>
     *         (����) field:not null<br>
     *         (����) errors:not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *         (��ԕω�) errors:�Ăяo����Ȃ�<br>
     *
     * <br>
     * ������bean���󕶎��̏ꍇ�Atrue���ԋp����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateLong02() throws Exception {
        // �e�X�g���{
        // ����
        assertTrue(new FieldChecks().validateLong("", va, field, errors));

        // addErrors�m�F
        assertEquals(0, errors.addErrorCount);
    }

    /**
     * testValidateLong03()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:"-9223372036854775808"<br>
     *         (����) va:not null<br>
     *         (����) field:not null<br>
     *         (����) errors:not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *         (��ԕω�) errors:�Ăяo����Ȃ�<br>
     *
     * <br>
     * ������bean�̒l���Along�ɕϊ��ł���ꍇ�Atrue���ԋp����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateLong03() throws Exception {
        // �e�X�g���{
        // ����
        assertTrue(
            new FieldChecks().validateLong(
                "-9223372036854775808", va, field, errors));

        // addErrors�m�F
        assertEquals(0, errors.addErrorCount);
    }

    /**
     * testValidateLong04()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:"��"<br>
     *         (����) va:not null<br>
     *         (����) field:not null<br>
     *         (����) errors:not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:false<br>
     *         (��ԕω�) errors:bean,field,va�������Ƃ���
     *                           addErrors���Ăяo�����B<br>
     *
     * <br>
     * ������bean�̒l���Along�ɕϊ��ł��Ȃ��ꍇ�A�G���[��ǉ����āA
     * false���ԋp����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateLong04() throws Exception {
        // �e�X�g���{
        // ����
        assertFalse(new FieldChecks().validateLong("��", va, field, errors));

        // addErrors�m�F
        assertEquals(1, errors.addErrorCount);
        assertEquals("��", errors.beanList.get(0));
        assertSame(va, errors.vaList.get(0));
        assertSame(field, errors.fieldList.get(0));
    }

    /**
     * testValidateFloat01()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF,G
     * <br><br>
     * ���͒l�F(����) bean:null<br>
     *         (����) va:not null<br>
     *         (����) field:not null<br>
     *         (����) errors:not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *         (��ԕω�) errors:�Ăяo����Ȃ�<br>
     *
     * <br>
     * ������bean��null�̏ꍇ�Atrue���ԋp����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateFloat01() throws Exception {
        // �e�X�g���{
        // ����
        assertTrue(new FieldChecks().validateFloat(null, va, field, errors));

        // addErrors�m�F
        assertEquals(0, errors.addErrorCount);
    }

    /**
     * testValidateFloat02()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC,F
     * <br><br>
     * ���͒l�F(����) bean:""<br>
     *         (����) va:not null<br>
     *         (����) field:not null<br>
     *         (����) errors:not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *         (��ԕω�) errors:�Ăяo����Ȃ�<br>
     *
     * <br>
     * ������bean���󕶎��̏ꍇ�Atrue���ԋp����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateFloat02() throws Exception {
        // �e�X�g���{
        // ����
        assertTrue(new FieldChecks().validateFloat("", va, field, errors));

        // addErrors�m�F
        assertEquals(0, errors.addErrorCount);
    }

    /**
     * testValidateFloat03()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:"1.4E-45"<br>
     *         (����) va:not null<br>
     *         (����) field:not null<br>
     *         (����) errors:not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *         (��ԕω�) errors:�Ăяo����Ȃ�<br>
     *
     * <br>
     * ������bean�̒l���Afloat�ɕϊ��ł���ꍇ�Atrue���ԋp����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateFloat03() throws Exception {
        // �e�X�g���{
        // ����
        assertTrue(new FieldChecks().validateFloat("1.4E-45", va, field, errors));

        // addErrors�m�F
        assertEquals(0, errors.addErrorCount);
    }

    /**
     * testValidateFloat04()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:"��"<br>
     *         (����) va:not null<br>
     *         (����) field:not null<br>
     *         (����) errors:not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:false<br>
     *         (��ԕω�) errors:bean,field,va�������Ƃ���addErrors���Ăяo�����B<br>
     *
     * <br>
     * ������bean�̒l���Afloat�ɕϊ��ł��Ȃ��ꍇ�A�G���[��ǉ����āAfalse���ԋp����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateFloat04() throws Exception {
        // �e�X�g���{
        // ����
        assertFalse(new FieldChecks().validateFloat("��", va, field, errors));

        // addErrors�m�F
        assertEquals(1, errors.addErrorCount);
        assertEquals("��", errors.beanList.get(0));
        assertSame(va, errors.vaList.get(0));
        assertSame(field, errors.fieldList.get(0));
    }

    /**
     * testValidateDouble01()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC,F
     * <br><br>
     * ���͒l�F(����) bean:null<br>
     *         (����) va:not null<br>
     *         (����) field:not null<br>
     *         (����) errors:not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *         (��ԕω�) errors:�Ăяo����Ȃ�<br>
     *
     * <br>
     * ������bean��null�̏ꍇ�Atrue���ԋp����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateDouble01() throws Exception {
        // �e�X�g���{
        // ����
        assertTrue(new FieldChecks().validateDouble(null, va, field, errors));

        // addErrors�m�F
        assertEquals(0, errors.addErrorCount);
    }

    /**
     * testValidateDouble02()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC,F
     * <br><br>
     * ���͒l�F(����) bean:""<br>
     *         (����) va:not null<br>
     *         (����) field:not null<br>
     *         (����) errors:not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *         (��ԕω�) errors:�Ăяo����Ȃ�<br>
     *
     * <br>
     * ������bean���󕶎��̏ꍇ�Atrue���ԋp����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateDouble02() throws Exception {
        // �e�X�g���{
        // ����
        assertTrue(new FieldChecks().validateDouble("", va, field, errors));

        // addErrors�m�F
        assertEquals(0, errors.addErrorCount);
    }

    /**
     * testValidateDouble03()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:"4.9E-324"<br>
     *         (����) va:not null<br>
     *         (����) field:not null<br>
     *         (����) errors:not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *         (��ԕω�) errors:�Ăяo����Ȃ�<br>
     *
     * <br>
     * ������bean�̒l���Adouble�ɕϊ��ł���ꍇ�Atrue���ԋp����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateDouble03() throws Exception {
        // �e�X�g���{
        // ����
        assertTrue(new FieldChecks().validateDouble("4.9E-324", va, field, errors));

        // addErrors�m�F
        assertEquals(0, errors.addErrorCount);
    }

    /**
     * testValidateDouble04()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:"��"<br>
     *         (����) va:not null<br>
     *         (����) field:not null<br>
     *         (����) errors:not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:false<br>
     *         (��ԕω�) errors:bean,field,va�������Ƃ���addErrors���Ăяo�����B<br>
     *
     * <br>
     * ������bean�̒l���Adouble�ɕϊ��ł��Ȃ��ꍇ�A�G���[��ǉ����āAfalse���ԋp����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateDouble04() throws Exception {
        // �e�X�g���{
        // ����
        assertFalse(new FieldChecks().validateDouble("��", va, field, errors));

        // addErrors�m�F
        assertEquals(1, errors.addErrorCount);
        assertEquals("��", errors.beanList.get(0));
        assertSame(va, errors.vaList.get(0));
        assertSame(field, errors.fieldList.get(0));
    }

    /**
     * testValidateDate01()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC,F
     * <br><br>
     * ���͒l�F(����) bean:null<br>
     *         (����) va:not null<br>
     *         (����) field:not null<br>
     *         (����) errors:not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *         (��ԕω�) errors:�Ăяo����Ȃ�<br>
     *
     * <br>
     * ������bean��null�̏ꍇ�Atrue���ԋp����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateDate01() throws Exception {
        // �e�X�g���{
        // ����
        assertTrue(new FieldChecks().validateDate(null, va, field, errors));

        // addErrors�m�F
        assertEquals(0, errors.addErrorCount);
    }

    /**
     * testValidateDate02()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC,F
     * <br><br>
     * ���͒l�F(����) bean:""<br>
     *         (����) va:not null<br>
     *         (����) field:not null<br>
     *         (����) errors:not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *         (��ԕω�) errors:�Ăяo����Ȃ�<br>
     *
     * <br>
     * ������bean���󕶎��̏ꍇ�Atrue���ԋp����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateDate02() throws Exception {
        // �e�X�g���{
        // ����
        assertTrue(new FieldChecks().validateDate("", va, field, errors));

        // addErrors�m�F
        assertEquals(0, errors.addErrorCount);
    }

    /**
     * testValidateDate03()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FF,G
     * <br><br>
     * ���͒l�F(����) bean:"2005/11/17"<br>
     *         (����) va:not null<br>
     *         (����) field:var:<br>
     *                datePattern=null<br>
     *                datePatternStrict=null<br>
     *         (����) errors:not null<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:ValidatorException<br>
     *                    ���b�Z�[�W�F"Mistake on validation definition file.
     *                     - datePattern or datePatternStrict is invalid.
     *                     You'll have to check it over. "<br>
     *         (��ԕω�) ���O:���O���x���F�G���[<br>
     *                    ���b�Z�[�W�F"Mistake on validation definition file.
     *                     - datePattern or datePatternStrict is invalid.
     *                      You'll have to check it over. ", new IllegalArgumentException()<br>
     *
     * <br>
     * datePattern�AdatePatternStrict�������Ƃ�null�̏ꍇ�A
     * ValidatorException���X���[����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateDate03() throws Exception {
        // �e�X�g���{
        // ����
        try {
            new FieldChecks().validateDate("2005/11/17", va, field, errors);
            fail();
        } catch (ValidatorException e) {
            String message = "Mistake on validation definition file. " +
                "- datePattern or datePatternStrict is invalid." +
                " You'll have to check it over. ";
            assertEquals(message, e.getMessage());
            assertTrue(LogUTUtil.checkError(message, new IllegalArgumentException()));
        }
    }

    /**
     * testValidateDate04()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FF,G
     * <br><br>
     * ���͒l�F(����) bean:"2005/11/17"<br>
     *         (����) va:not null<br>
     *         (����) field:var:<br>
     *                datePattern=""<br>
     *                datePatternStrict=""<br>
     *         (����) errors:not null<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:ValidatorException<br>
     *                    ���b�Z�[�W�F"Mistake on validation definition file.
     *                     - datePattern or datePatternStrict is invalid.
     *                     You'll have to check it over. "<br>
     *         (��ԕω�) ���O:���O���x���F�G���[<br>
     *                    ���b�Z�[�W�F"Mistake on validation definition file.
     *                     - datePattern or datePatternStrict is invalid.
     *                      You'll have to check it over. ", new IllegalArgumentException()<br>
     *
     * <br>
     * datePattern�AdatePatternStrict�������Ƃ�null�̏ꍇ�A
     * ValidatorException���X���[����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateDate04() throws Exception {
        // �O����
        Var var1 = new Var();
        var1.setName("datePattern");
        var1.setValue("");
        field.addVar(var1);
        Var var2 = new Var();
        var2.setName("datePatternStrict");
        var2.setValue("");
        field.addVar(var2);

        // �e�X�g���{
        // ����
        try {
            new FieldChecks().validateDate("2005/11/17", va, field, errors);
            fail();
        } catch (ValidatorException e) {
            String message = "Mistake on validation definition file. " +
                "- datePattern or datePatternStrict is invalid." +
                " You'll have to check it over. ";
            assertEquals(message, e.getMessage());
            assertTrue(LogUTUtil.checkError(message, new IllegalArgumentException()));
        }
    }

    /**
     * testValidateDate05()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FF,G
     * <br><br>
     * ���͒l�F(����) bean:"2005/11/17"<br>
     *         (����) va:not null<br>
     *         (����) field:var:<br>
     *                datePattern="abc"<br>
     *                datePatternStrict=""<br>
     *         (����) errors:not null<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:ValidatorException<br>
     *                    ���b�Z�[�W�F"Mistake on validation definition file.
     *                     - datePattern or datePatternStrict is invalid.
     *                     You'll have to check it over. "<br>
     *         (��ԕω�) ���O:���O���x���F�G���[<br>
     *                    ���b�Z�[�W�F"Mistake on validation definition file.
     *                     - datePattern or datePatternStrict is invalid.
     *                      You'll have to check it over. ", new IllegalArgumentException()<br>
     *
     * <br>
     * datePattern�ɓ��t�`���Ƃ��ĉ��߂ł��Ȃ��������܂܂��ꍇ�A
     * ValidatorException���X���[����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateDate05() throws Exception {
        // �O����
        Var var1 = new Var();
        var1.setName("datePattern");
        var1.setValue("abc");
        field.addVar(var1);
        
        Var var2 = new Var();
        var2.setName("datePatternStrict");
        var2.setValue("");
        field.addVar(var2);

        // �e�X�g���{
        // ����
        try {
            new FieldChecks().validateDate("2005/11/17", va, field, errors);
            fail();
        } catch (ValidatorException e) {
            String message = "Mistake on validation definition file. " +
                "- datePattern or datePatternStrict is invalid." +
                " You'll have to check it over. ";
            assertEquals(message, e.getMessage());
            assertTrue(LogUTUtil.checkError(message, new IllegalArgumentException()));
        }
    }

    /**
     * testValidateDate06()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FF,G
     * <br><br>
     * ���͒l�F(����) bean:"2005/11/17"<br>
     *         (����) va:not null<br>
     *         (����) field:var:<br>
     *                datePattern=null<br>
     *                datePatternStrict="abc"<br>
     *         (����) errors:not null<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:ValidatorException<br>
     *                    ���b�Z�[�W�F"Mistake on validation definition file.
     *                     - datePattern or datePatternStrict is invalid.
     *                     You'll have to check it over. "<br>
     *         (��ԕω�) ���O:���O���x���F�G���[<br>
     *                    ���b�Z�[�W�F"Mistake on validation definition file.
     *                     - datePattern or datePatternStrict is invalid.
     *                      You'll have to check it over. ", new IllegalArgumentException()<br>
     *
     * <br>
     * datePatternStrict�ɓ��t�`���Ƃ��ĉ��߂ł��Ȃ��������܂܂��ꍇ�A
     * ValidatorException���X���[����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateDate06() throws Exception {
        // �O����
        Var var = new Var();
        var.setName("datePatternStrict");
        var.setValue("abc");
        field.addVar(var);

        // �e�X�g���{
        // ����
        try {
            new FieldChecks().validateDate("2005/11/17", va, field, errors);
            fail();
        } catch (ValidatorException e) {
            String message = "Mistake on validation definition file. " +
                "- datePattern or datePatternStrict is invalid." +
                " You'll have to check it over. ";
            assertEquals(message, e.getMessage());
            assertTrue(LogUTUtil.checkError(message, new IllegalArgumentException()));
        }
    }

    /**
     * testValidateDate07()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:"2005/1/1"<br>
     *         (����) va:not null<br>
     *         (����) field:var:<br>
     *                datePattern="yyyy/MM/dd"<br>
     *                datePatternStrict="yyyy.MM.dd"<br>
     *         (����) errors:not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *         (��ԕω�) errors:�Ăяo����Ȃ�<br>
     *
     * <br>
     * datePattern�AdatePatternStrict�̗����ɐ��������t�`�����w�肳���ꍇ�A
     * datePattern�̌`���œ��͒l�̉��߂��s���邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateDate07() throws Exception {
        // �O����
        Var var1 = new Var();
        var1.setName("datePattern");
        var1.setValue("yyyy/MM/dd");
        field.addVar(var1);
        
        Var var2 = new Var();
        var2.setName("datePatternStrict");
        var2.setValue("yyyy.MM.dd");
        field.addVar(var2);

        // �e�X�g���{
        // ����
        assertTrue(new FieldChecks().validateDate("2005/1/1", va, field, errors));

        // addErrors�m�F
        assertEquals(0, errors.addErrorCount);
    }

    /**
     * testValidateDate08()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:"2005/1/1"<br>
     *         (����) va:not null<br>
     *         (����) field:var:<br>
     *                datePatternStrict="yyyy/MM/dd"<br>
     *         (����) errors:not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:false<br>
     *         (��ԕω�) errors:bean,field,va�������Ƃ���addErrors���Ăяo�����B<br>
     *
     * <br>
     * datePatternStrict�ɓ��t�`�����w�肳��Ă���A�`�������S�Ɉ�v���Ȃ��ꍇ�A�G���[��ǉ�����false���ԋp����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateDate08() throws Exception {
        // �O����
        Var var = new Var();
        var.setName("datePatternStrict");
        var.setValue("yyyy/MM/dd");
        field.addVar(var);

        // �e�X�g���{
        // ����
        assertFalse(new FieldChecks().validateDate("2005/1/1", va, field, errors));

        // addErrors�m�F
        assertEquals(1, errors.addErrorCount);
        assertEquals("2005/1/1", errors.beanList.get(0));
        assertSame(va, errors.vaList.get(0));
        assertSame(field, errors.fieldList.get(0));
    }

    /**
     * testValidateDate09()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:"2005/2/29"<br>
     *         (����) va:not null<br>
     *         (����) field:var:<br>
     *                datePattern="yyyy/MM/dd"<br>
     *         (����) errors:not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:false<br>
     *         (��ԕω�) errors:bean,field,va�������Ƃ���addErrors���Ăяo�����B<br>
     *
     * <br>
     * ���݂��Ȃ����t�����͂��ꂽ�ꍇ�A�G���[��ǉ�����false���ԋp����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateDate09() throws Exception {
        // �O����
        Var var = new Var();
        var.setName("datePattern");
        var.setValue("yyyy/MM/dd");
        field.addVar(var);

        // �e�X�g���{
        // ����
        assertFalse(new FieldChecks().validateDate("2005/2/29", va, field, errors));

        // addErrors�m�F
        assertEquals(1, errors.addErrorCount);
        assertEquals("2005/2/29", errors.beanList.get(0));
        assertSame(va, errors.vaList.get(0));
        assertSame(field, errors.fieldList.get(0));
    }

}
