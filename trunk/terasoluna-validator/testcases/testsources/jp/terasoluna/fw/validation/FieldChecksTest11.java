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

import java.lang.reflect.InvocationTargetException;

import jp.terasoluna.fw.util.ClassLoadException;
import jp.terasoluna.utlib.LogUTUtil;
import jp.terasoluna.fw.validation.FieldChecks;
import junit.framework.TestCase;

import org.apache.commons.validator.Field;
import org.apache.commons.validator.ValidatorAction;
import org.apache.commons.validator.Var;

/**
 * {@link FieldChecks} �N���X�̃e�X�g�B
 * 
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4>
 * TERASOLUNA�̓��̓`�F�b�N�@�\�ŋ��ʂɎg�p����錟�؃��[���N���X�B
 * <p>
 * 
 * @see jp.terasoluna.fw.validation.FieldChecks
 */
public class FieldChecksTest11 extends TestCase {
    
    /**
     * ���̃e�X�g�P�[�X�����s����ׂ�
     * GUI �A�v���P�[�V�������N������B
     * 
     * @param args java �R�}���h�ɐݒ肳�ꂽ�p�����[�^
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(FieldChecksTest11.class);
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
        LogUTUtil.flush();
        FieldChecks_MultiFieldValidatorImpl01.result = false;
        FieldChecks_MultiFieldValidatorImpl01.validateCalledCount = 0;
        FieldChecks_MultiFieldValidatorImpl01.value = null;
        FieldChecks_MultiFieldValidatorImpl01.fields = null;
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
    public FieldChecksTest11(String name) {
        super(name);
    }

    /**
     * testValidateMultiField01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC, F, I
     * <br><br>
     * ���͒l�F(����) bean:null<br>
     *         (����) va:ValidatorActionn�N���X�̃C���X�^���X<br>
     *         (����) field:Field�N���X�̃C���X�^���X<br>
     *                <br>
     *                Msg("message, "message")<br>
     *         (����) errors:MockValidationErrors�N���X�̃C���X�^���X<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *         (��ԕω�) errors:�t�B�[���herrorMessage��null�ł��邱�Ƃ��m�F����B<br>
     *         (��ԕω�) ���O:���O���x���FERROR<br>
     *                    ���b�Z�[�W�F"bean is null."<br>
     *         
     * <br>
     * ����bean��null�̏ꍇ�ɃG���[���O���o�͂��āATRUE���ԋp����邱�Ƃ��m�F����e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateMultiField01() throws Exception {
        // �O����
        Object bean = null;
        ValidatorAction va = new ValidatorAction();
        Field field = new Field();
        FieldChecks_ValidationErrorsImpl03 errors = new FieldChecks_ValidationErrorsImpl03();

        // �e�X�g���{
        FieldChecks fieldChecks = new FieldChecks();
        boolean result = 
            fieldChecks.validateMultiField(bean, va, field, errors);

        // ����
        assertTrue(result);
        assertNull(errors.errorMessage);
        assertTrue(LogUTUtil.checkError("bean is null."));
    }

    /**
     * testValidateMultiField02()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FC, F, G, I
     * <br><br>
     * ���͒l�F(����) bean:""�i�󕶎��j<br>
     *         (����) va:ValidatorActionn�N���X�̃C���X�^���X<br>
     *         (����) field:�ȉ��̃t�B�[���h��ݒ肵��Field�N���X�̃C���X�^���X<br>
     *                <br>
     *                var�FmultiFieldValidator=null<br>
     *         (����) errors:MockValidationErrors�N���X�̃C���X�^���X<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) errors:�t�B�[���herrorMessage��null�ł��邱�Ƃ��m�F����B<br>
     *         (��ԕω�) ��O:��O�FIllegalArgumentException<br>
     *                    ���b�Z�[�W�F
     *                    "var value[multiFieldValidator] is required."<br>
     *         (��ԕω�) ���O:���O���x���FERROR<br>
     *                    ���b�Z�[�W�F
     *                    "var value[multiFieldValidator] is required."<br>
     *         
     * <br>
     * ����field�́Avar-name�FmultiFieldValidator�ɑΉ�����var-value��null�̏ꍇ�ɁA
     * �G���[���O���o�͂��āAIllegalArgumentException���X���[����邱�Ƃ��m�F����e�X�g�B<br>
     * <br>
     * ������bean���󕶎��̏ꍇ�Ƀ`�F�b�N�����s����邱�Ƃ��m�F����e�X�g���܂���B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateMultiField02() throws Exception {
        // �O����
        Object bean = "";
        ValidatorAction va = new ValidatorAction();
        Field field = new Field();
        Var var = new Var("multiFieldValidator", null, null);
        field.addVar(var);
        FieldChecks_ValidationErrorsImpl03 errors = new FieldChecks_ValidationErrorsImpl03();

        // �e�X�g���{
        FieldChecks fieldChecks = new FieldChecks();
        try {
            fieldChecks.validateMultiField(bean, va, field, errors);
            fail("IllegalArgumentException���X���[����Ȃ������B");
        } catch (IllegalArgumentException e) {
            // ����
            assertNull(errors.errorMessage);
            assertEquals(IllegalArgumentException.class.getName(), 
                        e.getClass().getName());
            assertEquals("var value[multiFieldValidator] is required.", 
                        e.getMessage());
            assertTrue(LogUTUtil.checkError(
                        "var value[multiFieldValidator] is required."));
        }
    }

    /**
     * testValidateMultiField03()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FC, F, G, I
     * <br><br>
     * ���͒l�F(����) bean:"bean"<br>
     *         (����) va:ValidatorActionn�N���X�̃C���X�^���X<br>
     *         (����) field:�ȉ��̃t�B�[���h��ݒ肵��Field�N���X�̃C���X�^���X<br>
     *                <br>
     *                var�FmultiFieldValidator=""�i�󕶎��j<br>
     *         (����) errors:MockValidationErrors�N���X�̃C���X�^���X<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) errors:�t�B�[���herrorMessage��null�ł��邱�Ƃ��m�F����B<br>
     *         (��ԕω�) ��O:��O�FIllegalArgumentException<br>
     *                    ���b�Z�[�W�F
     *                    "var value[multiFieldValidator] is required."<br>
     *         (��ԕω�) ���O:���O���x���FERROR<br>
     *                    ���b�Z�[�W�F
     *                    "var value[multiFieldValidator] is required."<br>
     *         
     * <br>
     * ����field�́Avar-name�FmultiFieldValidator�ɑΉ�����var-value���󕶎��̏ꍇ�ɁA
     * �G���[���O���o�͂��āAIllegalArgumentException���X���[����邱�Ƃ��m�F����e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateMultiField03() throws Exception {
        // �O����
        Object bean = "bean";
        ValidatorAction va = new ValidatorAction();
        Field field = new Field();
        Var var = new Var("multiFieldValidator", "", null);
        field.addVar(var);
        FieldChecks_ValidationErrorsImpl03 errors = new FieldChecks_ValidationErrorsImpl03();

        // �e�X�g���{
        FieldChecks fieldChecks = new FieldChecks();
        try {
            fieldChecks.validateMultiField(bean, va, field, errors);
            fail("IllegalArgumentException���X���[����Ȃ������B");
        } catch (IllegalArgumentException e) {
            // ����
            assertNull(errors.errorMessage);
            assertEquals(IllegalArgumentException.class.getName(), 
                        e.getClass().getName());
            assertEquals("var value[multiFieldValidator] is required.", 
                        e.getMessage());
            assertTrue(LogUTUtil.checkError(
                        "var value[multiFieldValidator] is required."));
        }
    }

    /**
     * testValidateMultiField04()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FF, G, I
     * <br><br>
     * ���͒l�F(����) bean:"bean"<br>
     *         (����) va:ValidatorActionn�N���X�̃C���X�^���X<br>
     *         (����) field:�ȉ��̃t�B�[���h��ݒ肵��Field�N���X�̃C���X�^���X<br>
     *                <br>
     *                var�FmultiFieldValidator="not.Exist"<br>
     *         (����) errors:MockValidationErrors�N���X�̃C���X�^���X<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) errors:�t�B�[���herrorMessage��null�ł��邱�Ƃ��m�F����B<br>
     *         (��ԕω�) ��O:��O�FIllegalArgumentException<br>
     *                    ���b�Z�[�W�F"var value[multiFieldValidator] is invalid."<br>
     *                    ���b�v���ꂽ��O�FClassLoadException<br>
     *         (��ԕω�) ���O:���O���x���FERROR<br>
     *                    ���b�Z�[�W�F"var value[multiFieldValidator] is invalid."<br>
     *                    ���b�v���ꂽ��O�FClassLoadException<br>
     *         
     * <br>
     * ����field�́Avar-name�FmultiFieldValidator�ɑΉ�����var-value���N���X�p�X���
     * ���݂��Ȃ��N���X���̏ꍇ�ɁA�G���[���O���o�͂��āAIllegalArgumentException��
     * �X���[����邱�Ƃ��m�F����e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateMultiField04() throws Exception {
        // �O����
        Object bean = "bean";
        ValidatorAction va = new ValidatorAction();
        Field field = new Field();
        Var var = new Var("multiFieldValidator", "not.Exist", null);
        field.addVar(var);
        FieldChecks_ValidationErrorsImpl03 errors = new FieldChecks_ValidationErrorsImpl03();

        // �e�X�g���{
        FieldChecks fieldChecks = new FieldChecks();
        try {
            fieldChecks.validateMultiField(bean, va, field, errors);
            fail("IllegalArgumentException���X���[����Ȃ������B");
        } catch (IllegalArgumentException e) {
            // ����
            assertNull(errors.errorMessage);
            assertEquals(IllegalArgumentException.class.getName(), 
                        e.getClass().getName());
            assertEquals("var value[multiFieldValidator] is invalid.", 
                        e.getMessage());
            assertTrue(e.getCause() instanceof ClassLoadException);
            assertTrue(LogUTUtil.checkError(
                        "var value[multiFieldValidator] is invalid.", 
                        new ClassLoadException(new RuntimeException())));
        }
    }

    /**
     * testValidateMultiField05()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FF, G, I
     * <br><br>
     * ���͒l�F(����) bean:"bean"<br>
     *         (����) va:ValidatorActionn�N���X�̃C���X�^���X<br>
     *         (����) field:�ȉ��̃t�B�[���h��ݒ肵��Field�N���X�̃C���X�^���X<br>
     *                <br>
     *                var�FmultiFieldValidator="java.lang.String"<br>
     *         (����) errors:MockValidationErrors�N���X�̃C���X�^���X<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) errors:�t�B�[���herrorMessage��null�ł��邱�Ƃ��m�F����B<br>
     *         (��ԕω�) ��O:��O�FIllegalArgumentException<br>
     *                    ���b�Z�[�W�F"var value[multiFieldValidator] is invalid."<br>
     *                    ���b�v���ꂽ��O�FClassCastException<br>
     *         (��ԕω�) ���O:���O���x���FERROR<br>
     *                    ���b�Z�[�W�F"var value[multiFieldValidator] is invalid."<br>
     *                    ���b�v���ꂽ��O�FClassCastException<br>
     *         
     * <br>
     * ����field�́Avar-name�FmultiFieldValidator�ɑΉ�����var-value��
     * MultiFieldValidator���������Ă��Ȃ��N���X���̏ꍇ�ɁA�G���[���O���o�͂��āA
     * IllegalArgumentException���X���[����邱�Ƃ��m�F����e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateMultiField05() throws Exception {
        // �O����
        Object bean = "bean";
        ValidatorAction va = new ValidatorAction();
        Field field = new Field();
        Var var = new Var("multiFieldValidator", "java.lang.String", null);
        field.addVar(var);
        FieldChecks_ValidationErrorsImpl03 errors = new FieldChecks_ValidationErrorsImpl03();

        // �e�X�g���{
        FieldChecks fieldChecks = new FieldChecks();
        try {
            fieldChecks.validateMultiField(bean, va, field, errors);
            fail("IllegalArgumentException���X���[����Ȃ������B");
        } catch (IllegalArgumentException e) {
            // ����
            assertNull(errors.errorMessage);
            assertEquals(IllegalArgumentException.class.getName(), 
                        e.getClass().getName());
            assertEquals("var value[multiFieldValidator] is invalid.", 
                        e.getMessage());
            assertTrue(e.getCause() instanceof ClassCastException);
            assertTrue(LogUTUtil.checkError(
                    "var value[multiFieldValidator] is invalid.", 
                    new ClassCastException()));
        }
    }

    /**
     * testValidateMultiField06()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:"bean"<br>
     *         (����) va:ValidatorActionn�N���X�̃C���X�^���X<br>
     *         (����) field:�ȉ��̃t�B�[���h��ݒ肵��Field�N���X�̃C���X�^���X<br>
     *                <br>
     *         var�FmultiFieldValidator=
     *         "jp.terasoluna.fw.validation.
     *         FieldChecks_MultiFieldValidatorImpl01"<br>
     *                var�Ffields=null<br>
     *         (����) errors:MockValidationErrors�N���X�̃C���X�^���X<br>
     *         (���) MultiFieldValidator#validate�̖߂�l:TRUE�ɐݒ�<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *         (��ԕω�) errors:�t�B�[���herrorMessage��null�ł��邱�Ƃ��m�F����B<br>
     *         (��ԕω�) MultiFieldValidator:�t�B�[���hvalidateCalledCount��
     *         1�ł��邱�Ƃ��m�F����B<br>
     *                    <br>
     *                    �t�B�[���hvalue��"bean"�ł��邱�Ƃ��m�F����B<br>
     *                    <br>
     *                    �t�B�[���hfields�̔z�񒷂�0�ł��邱�Ƃ��m�F����B<br>
     *         
     * <br>
     * field��var-name�Ffields�ɑΉ�����var-value��null�̏ꍇ�A
     * MultiFieldValidator#validate�̑������ɋ�̔z�񂪓n����邱�Ƃ��m�F����e�X�g�B<br>
     * <br>
     * ������bean��������̏ꍇ�ɁAMultiFieldValidator#validate��
     * �������ɂ��̕����񂪓n����邱�Ƃ��m�F����e�X�g���܂���B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateMultiField06() throws Exception {
        // �O����
        Object bean = "bean";
        ValidatorAction va = new ValidatorAction();
        Field field = new Field();
        Var var1 = new Var("multiFieldValidator", 
                "jp.terasoluna.fw.validation."
                + "FieldChecks_MultiFieldValidatorImpl01", null);
        Var var2 = new Var("fields", null, null);
        field.addVar(var1);
        field.addVar(var2);
        FieldChecks_ValidationErrorsImpl03 errors = new FieldChecks_ValidationErrorsImpl03();
        FieldChecks_MultiFieldValidatorImpl01.result = true;

        // �e�X�g���{
        FieldChecks fieldChecks = new FieldChecks();

        boolean result = 
            fieldChecks.validateMultiField(bean, va, field, errors);
    
        // ����
        assertTrue(result);
        assertNull(errors.errorMessage);
        assertEquals(1, FieldChecks_MultiFieldValidatorImpl01.
                validateCalledCount);
        assertEquals("bean", FieldChecks_MultiFieldValidatorImpl01.value);
        assertNotNull(FieldChecks_MultiFieldValidatorImpl01.fields);
        assertEquals(0, 
                FieldChecks_MultiFieldValidatorImpl01.fields.length);
    }

    /**
     * testValidateMultiField07()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:"bean"<br>
     *         (����) va:ValidatorActionn�N���X�̃C���X�^���X<br>
     *         (����) field:�ȉ��̃t�B�[���h��ݒ肵��Field�N���X�̃C���X�^���X<br>
     *                <br>
     *                var�FmultiFieldValidator=
     *                "jp.terasoluna.fw.validation.
     *                FieldChecks_MultiFieldValidatorImpl01"<br>
     *                var�Ffields=""�i�󕶎��j<br>
     *         (����) errors:MockValidationErrors�N���X�̃C���X�^���X<br>
     *         (���) MultiFieldValidator#validate�̖߂�l:TRUE�ɐݒ�<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *         (��ԕω�) errors:�t�B�[���herrorMessage��null�ł��邱�Ƃ��m�F����B<br>
     *         (��ԕω�) MultiFieldValidator:�t�B�[���hvalidateCalledCount��1��
     *         ���邱�Ƃ��m�F����B<br>
     *                    <br>
     *                    �t�B�[���hvalue��"bean"�ł��邱�Ƃ��m�F����B<br>
     *                    <br>
     *                    �t�B�[���hfields�̔z�񒷂�0�ł��邱�Ƃ��m�F����B<br>
     *         
     * <br>
     * field��var-name�Ffields�ɑΉ�����var-value���󕶎��̏ꍇ�A
     * MultiFieldValidator#validate�̑������ɋ�̔z�񂪓n����邱�Ƃ��m�F����e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateMultiField07() throws Exception {
        // �O����
        Object bean = "bean";
        ValidatorAction va = new ValidatorAction();
        Field field = new Field();
        Var var1 = new Var("multiFieldValidator", 
                "jp.terasoluna.fw.validation."
                + "FieldChecks_MultiFieldValidatorImpl01", null);
        Var var2 = new Var("fields", "", null);
        field.addVar(var1);
        field.addVar(var2);
        FieldChecks_ValidationErrorsImpl03 errors = new FieldChecks_ValidationErrorsImpl03();
        FieldChecks_MultiFieldValidatorImpl01.result = true;

        // �e�X�g���{
        FieldChecks fieldChecks = new FieldChecks();

        boolean result = 
            fieldChecks.validateMultiField(bean, va, field, errors);
    
        // ����
        assertTrue(result);
        assertNull(errors.errorMessage);
        assertEquals(1, FieldChecks_MultiFieldValidatorImpl01.
                validateCalledCount);
        assertEquals("bean", FieldChecks_MultiFieldValidatorImpl01.value);
        assertNotNull(FieldChecks_MultiFieldValidatorImpl01.fields);
        assertEquals(0, 
                FieldChecks_MultiFieldValidatorImpl01.fields.length);
    }

    /**
     * testValidateMultiField08()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:�ȉ��̃t�B�[���h��ݒ肵��FieldChecksExtend_BeanStub01�N���X�̃C���X�^���X<br>
     *                <br>
     *                field1=Object�N���X�̃C���^���X1<br>
     *                field2=Object�N���X�̃C���^���X2<br>
     *         (����) va:ValidatorActionn�N���X�̃C���X�^���X<br>
     *         (����) field:�ȉ��̃t�B�[���h��ݒ肵��Field�N���X�̃C���X�^���X<br>
     *                <br>
     *                property="field1"<br>
     *                var�FmultiFieldValidator=
     *                "jp.terasoluna.fw.validation.
     *                FieldChecks_MultiFieldValidatorImpl01"<br>
     *                var�Ffields="field2"<br>
     *         (����) errors:MockValidationErrors�N���X�̃C���X�^���X<br>
     *         (���) MultiFieldValidator#validate�̖߂�l:TRUE�ɐݒ�<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *         (��ԕω�) errors:�t�B�[���herrorMessage��null�ł��邱�Ƃ��m�F����B<br>
     *         (��ԕω�) MultiFieldValidator:�t�B�[���hvalidateCalledCount��
     *         1�ł��邱�Ƃ��m�F����B<br>
     *                    <br>
     *                    �t�B�[���hvalue���A�ݒ肳�ꂽObject�N���X�̃C���^���X1��
     *                    �����C���X�^���X�ł��邱�Ƃ��m�F����B<br>
     *                    <br>
     *                    �t�B�[���hfields�̔z�񒷂�1�ł��邱�Ƃ��m�F����B<br>
     *                    <br>
     *                    �t�B�[���hfields���ȉ���1�v�f�������Ƃ��m�F����B<br>
     *                    fields[0]=Object�N���X�̃C���^���X2�Ɠ����C���X�^���X<br>
     *         
     * <br>
     * field��var-name�Ffields�ɑΉ�����var-value�ɁA�J���}��؂薳���̕�����
     * �w�肳��Ă���ꍇ�A���̖��O�ɑΉ�����v���p�e�B�l������bean����擾���A
     * ����1�̔z��Ƃ���MultiFieldValidator�̑������ɓn����邱�Ƃ��m�F����e�X�g�B<br>
     * <br>
     * ������bean��JavaBean�̏ꍇ�ɁA����field�̃v���p�e�B���ɑΉ�����v���p�e�B�l��
     * ������bean����擾���A���ꂪMultiFieldValidator#validate�̑������ɓn�����
     * ���Ƃ��m�F����e�X�g���܂���B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateMultiField08() throws Exception {
        // �O����
        FieldChecks_JavaBeanStub02 bean = new FieldChecks_JavaBeanStub02();
        Object testValue1 = new Object();
        Object testValue2 = new Object();
        bean.field1 = testValue1;
        bean.field2 = testValue2;
        ValidatorAction va = new ValidatorAction();
        Field field = new Field();
        field.setProperty("field1");
        Var var1 = new Var("multiFieldValidator", 
                "jp.terasoluna.fw.validation."
                + "FieldChecks_MultiFieldValidatorImpl01", null);
        Var var2 = new Var("fields", "field2", null);
        field.addVar(var1);
        field.addVar(var2);
        FieldChecks_ValidationErrorsImpl03 errors = new FieldChecks_ValidationErrorsImpl03();
        FieldChecks_MultiFieldValidatorImpl01.result = true;

        // �e�X�g���{
        FieldChecks fieldChecks = new FieldChecks();

        boolean result = 
            fieldChecks.validateMultiField(bean, va, field, errors);
    
        // ����
        assertTrue(result);
        assertNull(errors.errorMessage);
        assertEquals(1, FieldChecks_MultiFieldValidatorImpl01.
                validateCalledCount);
        assertSame(testValue1, 
                FieldChecks_MultiFieldValidatorImpl01.value);
        assertNotNull(FieldChecks_MultiFieldValidatorImpl01.fields);
        assertEquals(1, 
                FieldChecks_MultiFieldValidatorImpl01.fields.length);
        assertSame(testValue2, 
                FieldChecks_MultiFieldValidatorImpl01.fields[0]);
    }

    /**
     * testValidateMultiField09()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA, D, F, I
     * <br><br>
     * ���͒l�F(����) bean:�ȉ��̃t�B�[���h��ݒ肵��FieldChecksExtend_BeanStub01�N���X�̃C���X�^���X<br>
     *                <br>
     *                field1=Object�N���X�̃C���^���X1<br>
     *                field2=Object�N���X�̃C���^���X2<br>
     *                field3=Object�N���X�̃C���^���X3<br>
     *         (����) va:ValidatorActionn�N���X�̃C���X�^���X<br>
     *         (����) field:�ȉ��̃t�B�[���h��ݒ肵��Field�N���X�̃C���X�^���X<br>
     *                <br>
     *                property="invalidProperty"<br>
     *                var�FmultiFieldValidator=
     *                "jp.terasoluna.fw.validation.
     *                FieldChecks_MultiFieldValidatorImpl01"<br>
     *                var�Ffields="field1 ,,invalidProperty,field2,field3"<br>
     *         (����) errors:MockValidationErrors�N���X�̃C���X�^���X<br>
     *         (���) MultiFieldValidator#validate�̖߂�l:FALSE�ɐݒ�<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) boolean:false<br>
     *         (��ԕω�) errors:�t�B�[���herrorMessage��"errorMessage"�ł��邱�Ƃ��m�F����B<br>
     *         (��ԕω�) MultiFieldValidator:�t�B�[���hvalidateCalledCount��
     *         1�ł��邱�Ƃ��m�F����B<br>
     *                    <br>
     *                    �t�B�[���hvalue���Anull�ł��邱�Ƃ��m�F����B<br>
     *                    <br>
     *                    �t�B�[���hfields�̔z�񒷂�3�ł��邱�Ƃ��m�F����B<br>
     *                    <br>
     *                    �t�B�[���hfields���ȉ���1�v�f�������Ƃ��m�F����B<br>
     *                    fields[0]=Object�N���X�̃C���^���X1�Ɠ����C���X�^���X<br>
     *                    fields[1]=Object�N���X�̃C���^���X2�Ɠ����C���X�^���X<br>
     *                    fields[2]=Object�N���X�̃C���^���X3�Ɠ����C���X�^���X<br>
     *         (��ԕω�) ���O:���O���x���FERROR<br>
     *                    ���b�Z�[�W�F"Unknown property 'invalidProperty'"<br>  
     *                    ���b�v���ꂽ��O�FNoSuchMethodException<br>
     *                    <br>
     *                    ���O���x���FERROR<br>
     *                    ���b�Z�[�W�F"Unknown property 'invalidProperty'"<br>
     *                    ���b�v���ꂽ��O�FNoSuchMethodException<br>
     *         
     * <br>
     * field��var-name�Ffields�ɑΉ�����var-value�ɁA�J���}��؂�̕�����
     * �w�肳��Ă���ꍇ�A�J���}�ŋ�؂�ꂽ�S�Ă̖��O�ɑΉ�����S�v���p�e�B�l��
     * ������bean����擾���A����3�̔z��Ƃ���MultiFieldValidator�̑�������
     * �n����邱�Ƃ��m�F����e�X�g�B<br>
     * <br>
     * ��MultiFieldValidator#validate�̕ԋp�l��false�̏ꍇ�A�G���[���b�Z�[�W��
     * �ǉ�����false���ԋp����邱�Ƃ��m�F����e�X�g���܂���B<br>
     * <br>
     * �����փ`�F�b�N�Ώۃt�B�[���h�l��������bean����擾����ۂɁA
     * PropertyUtils#getProperty�ɂ����Ĕ�������NoSuchMethodException�����b�v����
     * �G���[���O���o�͂��ă`�F�b�N�����s����邱�Ƃ��m�F����e�X�g���܂���B<br>
     * <br>
     * �����փ`�F�b�N�ˑ��t�B�[���h�l��������bean����擾����ۂɁA
     * PropertyUtils#getProperty�ɂ����Ĕ�������NoSuchMethodException�����b�v����
     * �G���[���O���o�͂��ă`�F�b�N�����s����邱�Ƃ��m�F����e�X�g���܂���B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateMultiField09() throws Exception {
        // �O����
        FieldChecks_JavaBeanStub02 bean = new FieldChecks_JavaBeanStub02();
        Object testValue1 = new Object();
        Object testValue2 = new Object();
        Object testValue3 = new Object();
        bean.field1 = testValue1;
        bean.field2 = testValue2;
        bean.field3 = testValue3;
        ValidatorAction va = new ValidatorAction();
        Field field = new Field();
        field.setProperty("invalidProperty");
        Var var1 = new Var("multiFieldValidator", 
                "jp.terasoluna.fw.validation."
                + "FieldChecks_MultiFieldValidatorImpl01", null);
        Var var2 = new Var(
                "fields", "field1 ,,invalidProperty,field2,field3", null);
        field.addVar(var1);
        field.addVar(var2);
        FieldChecks_ValidationErrorsImpl03 errors = new FieldChecks_ValidationErrorsImpl03();
        FieldChecks_MultiFieldValidatorImpl01.result = false;

        // �e�X�g���{
        FieldChecks fieldChecks = new FieldChecks();

        boolean result = 
            fieldChecks.validateMultiField(bean, va, field, errors);
    
        // ����
        assertFalse(result);
        assertEquals("errorMessage", errors.errorMessage);
        assertEquals(1, FieldChecks_MultiFieldValidatorImpl01.
                validateCalledCount);
        assertNull(FieldChecks_MultiFieldValidatorImpl01.value);
        assertNotNull(FieldChecks_MultiFieldValidatorImpl01.fields);
        assertEquals(3, 
                FieldChecks_MultiFieldValidatorImpl01.fields.length);
        assertSame(testValue1, 
                FieldChecks_MultiFieldValidatorImpl01.fields[0]);
        assertSame(testValue2, 
                FieldChecks_MultiFieldValidatorImpl01.fields[1]);
        assertSame(testValue3, 
                FieldChecks_MultiFieldValidatorImpl01.fields[2]);
        assertTrue(LogUTUtil.checkError("Unknown property 'invalidProperty'",
                new NoSuchMethodException()));
        assertTrue(LogUTUtil.checkError("Unknown property 'invalidProperty'",
                new NoSuchMethodException()));
    }

    /**
     * testValidateMultiField10()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA, F, I
     * <br><br>
     * ���͒l�F(����) bean:�ȉ�����������FieldChecksExtend_BeanStub03�N���X�̃C���X�^���X<br>
     *                <br>
     *                field1��getter��RuntimeException�����b�v����
     *                InvocationTargetException���X���[����B<br>
     *                field2��getter��RuntimeException�����b�v����
     *                InvocationTargetException���X���[����B<br>
     *         (����) va:ValidatorActionn�N���X�̃C���X�^���X<br>
     *         (����) field:�ȉ��̃t�B�[���h��ݒ肵��Field�N���X�̃C���X�^���X<br>
     *                <br>
     *                property="field1"<br>
     *                var�FmultiFieldValidator=
     *                "jp.terasoluna.fw.validation.
     *                FieldChecks_MultiFieldValidatorImpl01"<br>
     *                var�Ffields="field2"<br>
     *         (����) errors:MockValidationErrors�N���X�̃C���X�^���X<br>
     *         (���) MultiFieldValidator#validate�̖߂�l:TRUE�ɐݒ�<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *         (��ԕω�) errors:�t�B�[���herrorMessage��null�ł��邱�Ƃ��m�F����B<br>
     *         (��ԕω�) MultiFieldValidator:�t�B�[���hvalidateCalledCount��
     *         1�ł��邱�Ƃ��m�F����B<br>
     *                    <br>
     *                    �t�B�[���hvalue��null�ł��邱�Ƃ��m�F����B<br>
     *                    <br>
     *                    �t�B�[���hfields�̔z�񒷂�0�ł��邱�Ƃ��m�F����B<br>
     *         (��ԕω�) ���O:���O���x���FERROR<br>
     *                    ���b�v���ꂽ��O�FInvocationTargetException<br>
     *                    <br>
     *                    ���O���x���FERROR<br>
     *                    ���b�v���ꂽ��O�FInvocationTargetException<br>
     *         
     * <br>
     * ���փ`�F�b�N�Ώۃt�B�[���h�l��������bean����擾����ۂɁA
     * PropertyUtils#getProperty�ɂ����Ĕ�������InvocationTargetException�����b�v����
     * �G���[���O���o�͂��ă`�F�b�N�����s����邱�Ƃ��m�F����e�X�g�B<br>
     * <br>
     * �����փ`�F�b�N�ˑ��t�B�[���h�l��������bean����擾����ۂɁA
     * PropertyUtils#getProperty�ɂ����Ĕ�������InvocationTargetException�����b�v����
     * �G���[���O���o�͂��ă`�F�b�N�����s����邱�Ƃ��m�F����e�X�g���܂���B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateMultiField10() throws Exception {
        // �O����
        FieldChecks_JavaBeanStub03 bean = new FieldChecks_JavaBeanStub03();
        ValidatorAction va = new ValidatorAction();
        Field field = new Field();
        field.setProperty("field1");
        Var var1 = new Var("multiFieldValidator", 
                "jp.terasoluna.fw.validation."
                + "FieldChecks_MultiFieldValidatorImpl01", null);
        Var var2 = new Var("fields", "field2", null);
        field.addVar(var1);
        field.addVar(var2);
        FieldChecks_ValidationErrorsImpl03 errors = new FieldChecks_ValidationErrorsImpl03();
        FieldChecks_MultiFieldValidatorImpl01.result = true;

        // �e�X�g���{
        FieldChecks fieldChecks = new FieldChecks();

        boolean result = 
            fieldChecks.validateMultiField(bean, va, field, errors);
    
        // ����
        assertTrue(result);
        assertNull(errors.errorMessage);
        assertEquals(1, FieldChecks_MultiFieldValidatorImpl01.
                validateCalledCount);
        assertNull(FieldChecks_MultiFieldValidatorImpl01.value);
        assertNotNull(FieldChecks_MultiFieldValidatorImpl01.fields);
        assertEquals(0, 
                FieldChecks_MultiFieldValidatorImpl01.fields.length);
        assertTrue(LogUTUtil.checkError(null, 
                new InvocationTargetException(new RuntimeException())));
        assertTrue(LogUTUtil.checkError(null, 
                new InvocationTargetException(new RuntimeException())));
    }

}
