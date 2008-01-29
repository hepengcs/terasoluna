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
import java.util.Collection;
import jp.terasoluna.fw.util.PropertyAccessException;
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
public class FieldChecksTest08 extends TestCase {

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
        junit.swingui.TestRunner.run(FieldChecksTest08.class);
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
    public FieldChecksTest08(String name) {
        super(name);
    }

    /**
     * testValidateNumericString01()
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
    public void testValidateNumericString01() throws Exception {
        // �e�X�g���{
        // ����
        assertTrue(new FieldChecks().validateNumericString(null, va, field, errors));

        // addErrors�m�F
        assertEquals(0, errors.addErrorCount);
    }

    /**
     * testValidateNumericString02()
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
    public void testValidateNumericString02() throws Exception {
        // �e�X�g���{
        // ����
        assertTrue(new FieldChecks().validateNumericString("", va, field, errors));

        // addErrors�m�F
        assertEquals(0, errors.addErrorCount);
    }

    /**
     * testValidateNumericString03()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:100.05<br>
     *         (����) va:not null<br>
     *         (����) field:not null<br>
     *         (����) errors:not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:false<br>
     *         (��ԕω�) errors:bean,field,va�������Ƃ���addErrors���Ăяo�����B<br>
     *
     * <br>
     * ������bean�ɐ����ȊO�̕������܂܂��ꍇ�A�G���[��ǉ�����false���ԋp����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateNumericString03() throws Exception {
        // �e�X�g���{
        // ����
        assertFalse(new FieldChecks().validateNumericString(
                "100.05", va, field, errors));

        // addErrors�m�F
        assertEquals(1, errors.addErrorCount);
        assertEquals("100.05", errors.beanList.get(0));
        assertSame(va, errors.vaList.get(0));
        assertSame(field, errors.fieldList.get(0));
    }

    /**
     * testValidateNumericString04()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:1234567890<br>
     *         (����) va:not null<br>
     *         (����) field:not null<br>
     *         (����) errors:not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *         (��ԕω�) errors:�Ăяo����Ȃ�<br>
     *
     * <br>
     * ������bean�������݂̂ō\������Ă���ꍇ�Atrue���ԋp����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateNumericString04() throws Exception {
        // �e�X�g���{
        // ����
        assertTrue(new FieldChecks().validateNumericString(
                "1234567890", va, field, errors));

        // addErrors�m�F
        assertEquals(0, errors.addErrorCount);
    }

    /**
     * testValidateStringLength01()
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
    public void testValidateStringLength01() throws Exception {
        // �e�X�g���{
        // ����
        assertTrue(new FieldChecks().validateStringLength(
                null, va, field, errors));

        // addErrors�m�F
        assertEquals(0, errors.addErrorCount);
    }

    /**
     * testValidateStringLength02()
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
    public void testValidateStringLength02() throws Exception {
        // �e�X�g���{
        // ����
        assertTrue(new FieldChecks().validateStringLength(
                "", va, field, errors));

        // addErrors�m�F
        assertEquals(0, errors.addErrorCount);
    }

    /**
     * testValidateStringLength03()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FF,G
     * <br><br>
     * ���͒l�F(����) bean:"test"<br>
     *         (����) va:not null<br>
     *         (����) field:var:<br>
     *                stringLength=null<br>
     *         (����) errors:not null<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:ValidatorException<br>
     *                    ���b�Z�[�W�F"Mistake on validation definition file. " +
                    "- stringLength is not number. " +
                    "You'll have to check it over. ";<br>
     *         (��ԕω�) ���O:���O���x���F�G���[<br>
     *                    ���b�Z�[�W�F"Mistake on validation definition file. " +
                    "- stringLength is not number. " +
                    "You'll have to check it over. ", new NumberFormatException()<br>
     *
     * <br>
     * var��stringLength��null�̏ꍇ�AValidatorException���X���[����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateStringLength03() throws Exception {
        // �e�X�g���{
        // ����
        try {
            new FieldChecks().validateStringLength("test", va, field, errors);
            fail();
        } catch (ValidatorException e) {
            String message = "Mistake on validation definition file. " +
            "- stringLength is not number. " +
            "You'll have to check it over. ";
            assertEquals(message, e.getMessage());
            assertTrue(LogUTUtil.checkError(message, new NumberFormatException()));
        }
    }

    /**
     * testValidateStringLength04()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FF,G
     * <br><br>
     * ���͒l�F(����) bean:"test"<br>
     *         (����) va:not null<br>
     *         (����) field:var:<br>
     *                stringLength=""<br>
     *         (����) errors:not null<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:ValidatorException<br>
     *                    ���b�Z�[�W�F"Mistake on validation definition file. " +
     *        "- stringLength is not number. " +
     *        "You'll have to check it over. "<br>
     *         (��ԕω�) ���O:���O���x���F�G���[<br>
     *                    ���b�Z�[�W�F"Mistake on validation definition file. " +
     *        "- stringLength is not number. " +
     *        "You'll have to check it over. ", new NumberFormatException()<br>
     *
     * <br>
     * var��stringLength���󕶎��̏ꍇ�A
     * ValidatorException���X���[����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateStringLength04() throws Exception {
        // �O����
        Var var = new Var();
        var.setName("stringLength");
        var.setValue("");
        field.addVar(var);

        // �e�X�g���{
        // ����
        try {
            new FieldChecks().validateStringLength("test", va, field, errors);
            fail();
        } catch (ValidatorException e) {
            String message = "Mistake on validation definition file. " +
            "- stringLength is not number. " +
            "You'll have to check it over. ";
            assertEquals(message, e.getMessage());
            assertTrue(LogUTUtil.checkError(message, new NumberFormatException()));
        }
    }

    /**
     * testValidateStringLength05()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FF,G
     * <br><br>
     * ���͒l�F(����) bean:"test"<br>
     *         (����) va:not null<br>
     *         (����) field:var:<br>
     *                stringLength="test"<br>
     *         (����) errors:not null<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:ValidatorException<br>
     *                    ���b�Z�[�W�F"Mistake on validation definition file.
     *                    - stringLength is not number.
     *                    You'll have to check it over. "<br>
     *         (��ԕω�) ���O:���O���x���F�G���[<br>
     *                    ���b�Z�[�W�F"Mistake on validation definition file.
     *                    - stringLength is not number.
     *                    You'll have to check it over. ", new NumberFormatException()<br>
     *
     * <br>
     * var��stringLength�����l�ɕϊ��ł��Ȃ��ꍇ�A
     * ValidatorException���������邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateStringLength05() throws Exception {
        // �O����
        Var var = new Var();
        var.setName("stringLength");
        var.setValue("test");
        field.addVar(var);

        // �e�X�g���{
        // ����
        try {
            new FieldChecks().validateStringLength("test", va, field, errors);
            fail();
        } catch (ValidatorException e) {
            String message = "Mistake on validation definition file. " +
                    "- stringLength is not number. " +
                    "You'll have to check it over. ";
            assertEquals(message, e.getMessage());
            assertTrue(LogUTUtil.checkError(message, new NumberFormatException()));
        }
    }

    /**
     * testValidateStringLength06()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:"test"<br>
     *         (����) va:not null<br>
     *         (����) field:var:<br>
     *                stringLength="3"<br>
     *         (����) errors:not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:false<br>
     *         (��ԕω�) errors:bean,field,va�������Ƃ���
     *                           addErrors���Ăяo�����B<br>
     *
     * <br>
     * ������bean�̌������Avar��stringLength�̒l���傫���ꍇ�A
     * �G���[��ǉ�����false���ԋp����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateStringLength06() throws Exception {
        // �O����
        Var var = new Var();
        var.setName("stringLength");
        var.setValue("3");
        field.addVar(var);

        // �e�X�g���{
        // ����
        assertFalse(
                new FieldChecks().validateStringLength("test", va, field, errors));

        // addErrors�m�F
        assertEquals(1, errors.addErrorCount);
        assertEquals("test", errors.beanList.get(0));
        assertSame(va, errors.vaList.get(0));
        assertSame(field, errors.fieldList.get(0));
    }

    /**
     * testValidateStringLength07()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:"test"<br>
     *         (����) va:not null<br>
     *         (����) field:var:<br>
     *                stringLength="5"<br>
     *         (����) errors:not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:false<br>
     *         (��ԕω�) errors:bean,field,va�������Ƃ���
     *                           addErrors���Ăяo�����B<br>
     *
     * <br>
     * ������bean�̌������Avar��stringLength�̒l��菬�����ꍇ�A
     * �G���[��ǉ�����false���ԋp����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateStringLength07() throws Exception {
        // �O����
        Var var = new Var();
        var.setName("stringLength");
        var.setValue("5");
        field.addVar(var);

        // �e�X�g���{
        // ����
        assertFalse(
                new FieldChecks().validateStringLength("test", va, field, errors));

        // addErrors�m�F
        assertEquals(1, errors.addErrorCount);
        assertEquals("test", errors.beanList.get(0));
        assertSame(va, errors.vaList.get(0));
        assertSame(field, errors.fieldList.get(0));
    }

    /**
     * testValidateStringLength08()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:"test"<br>
     *         (����) va:not null<br>
     *         (����) field:var:<br>
     *                stringLength="4"<br>
     *         (����) errors:not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *         (��ԕω�) errors:�Ăяo����Ȃ�<br>
     *
     * <br>
     * ������bean�̌������Avar��stringLength�̒l�ƈ�v����ꍇ�Atrue���ԋp����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateStringLength08() throws Exception {
        // �O����
        Var var = new Var();
        var.setName("stringLength");
        var.setValue("4");
        field.addVar(var);

        // �e�X�g���{
        // ����
        assertTrue(
                new FieldChecks().validateStringLength("test", va, field, errors));

        // addErrors�m�F
        assertEquals(0, errors.addErrorCount);
    }

    /**
     * testValidateArrayRange01()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC,F,G
     * <br><br>
     * ���͒l�F(����) bean:null<br>
     *         (����) va:not null<br>
     *         (����) field:property="field"<br>
     *         (����) errors:not null<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:ValidatorException<br>
     *                    ���b�Z�[�W�F
     *                    "target of validateArrayRange must be not null."<br>
     *         (��ԕω�) ���O:���O���x���F�G���[<br>
     *                    ���b�Z�[�W�F
     *                    "target of validateArrayRange must be not null."<br>
     *
     * <br>
     * ������bean��null�̏ꍇ�AValidatorException���X���[����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateArrayRange01() throws Exception {
        // �O����
        field.setProperty("field");

        // �e�X�g���{
        // ����
        try {
            new FieldChecks().validateArrayRange(null, va, field, errors);
            fail();
        } catch (ValidatorException e) {
            String message = "target of validateArrayRange must be not null.";
            assertEquals(message, e.getMessage());
            assertTrue(LogUTUtil.checkError(message));
        }

    }

    /**
     * testValidateArrayRange02()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FF,G
     * <br><br>
     * ���͒l�F(����) bean:JavaBean<br>
     *                 testField���������݂��Ȃ�<br>
     *         (����) va:not null<br>
     *         (����) field:property="testField"<br>
     *         (����) errors:not null<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:ValidatorException<br>
     *                    ���b�Z�[�W�F"Cannot get property type[" +
     *                    JavaBean.class.getName+".testField]"<br>
     *         (��ԕω�) ���O:ValidatorException<br>
     *                    ���b�Z�[�W�F"Cannot get property type[" +
     *                    JavaBean.class.getName+".testField]"<br>
     *
     * <br>
     * field��property�����Ŏw�肳�ꂽ������bean�ɑ��݂��Ȃ��ꍇ�A
     * ValidatorException���X���[����邱�Ƃ��m�F����B<br>
     * ��BeanUtil.getPropertyType��null��ԋp����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateArrayRange02() throws Exception {
        // �O����
        FieldChecks_JavaBeanStub01 bean = new FieldChecks_JavaBeanStub01();
        field.setProperty("testField");

        // �e�X�g���{
        // ����
        try {
            new FieldChecks().validateArrayRange(bean, va, field, errors);
            fail();
        } catch (ValidatorException e) {
            String message = "Cannot get property type[" +
                bean.getClass().getName() + ".testField]";
            assertEquals(message, e.getMessage());
            assertTrue(LogUTUtil.checkError(message));
        }
    }

    /**
     * testValidateArrayRange03()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:JavaBean<br>
     *         (����) va:not null<br>
     *         (����) field:property=null<br>
     *         (����) errors:not null<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:ValidatorException<br>
     *                    ���b�Z�[�W�F"Cannot get property type[" +
     *                    JavaBean.class.getName+".null]"<br>
     *         (��ԕω�) ���O:ValidatorException<br>
     *                    ���b�Z�[�W�F"Cannot get property type[" +
     *                    JavaBean.class.getName+".null]", new PropertyAccessException(new IllegalArgumentException())<br>
     *
     * <br>
     * field��property������null�̏ꍇ�A
     * ValidatorException���X���[����邱�Ƃ��m�F����B<br>
     * ��BeanUtil.getPropertyType��PropertyAccessException���X���[����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateArrayRange03() throws Exception {
        // �O����
        FieldChecks_JavaBeanStub01 bean = new FieldChecks_JavaBeanStub01();

        // �e�X�g���{
        // ����
        try {
            new FieldChecks().validateArrayRange(bean, va, field, errors);
            fail();
        } catch (ValidatorException e) {
            String message = "Cannot get property type[" +
                bean.getClass().getName() + ".null]";
            assertEquals(message, e.getMessage());
            assertTrue(LogUTUtil.checkError(message,
                new PropertyAccessException(new IllegalArgumentException())));
        }
    }

    /**
     * testValidateArrayRange04()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FF,G
     * <br><br>
     * ���͒l�F(����) bean:JavaBean<br>
     *         (����) va:not null<br>
     *         (����) field:property=""<br>
     *         (����) errors:not null<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:ValidatorException<br>
     *                    ���b�Z�[�W�F"Cannot get property type[" +
     *                    JavaBean.class.getName+".testField]"<br>
     *         (��ԕω�) ���O:ValidatorException<br>
     *                    ���b�Z�[�W�F"Cannot get property type[" +
     *                    JavaBean.class.getName+".testField]"<br>
     *
     * <br>
     * field��property�������󕶎��̏ꍇ�A
     * ValidatorException���X���[����邱�Ƃ��m�F����B<br>
     * ��BeanUtil.getPropertyType��null��ԋp����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateArrayRange04() throws Exception {
        // �O����
        FieldChecks_JavaBeanStub01 bean = new FieldChecks_JavaBeanStub01();
        field.setProperty("");

        // �e�X�g���{
        // ����
        try {
            new FieldChecks().validateArrayRange(bean, va, field, errors);
            fail();
        } catch (ValidatorException e) {
            String message = "Cannot get property type[" +
                bean.getClass().getName() + ".]";
            assertEquals(message, e.getMessage());
            assertTrue(LogUTUtil.checkError(message));
        }
    }

    /**
     * testValidateArrayRange05()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FF,G
     * <br><br>
     * ���͒l�F(����) bean:JavaBean<br>
     *                field1(String[]�^)=null<br>
     *         (����) va:not null<br>
     *         (����) field:property="field1"<br>
     *                var:<br>
     *                minArrayLength="test"<br>
     *         (����) errors:not null<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:ValidatorException<br>
     *                    ���b�Z�[�W�F"Mistake on validation definition file.
     *                    - minArrayLength is not number.
     *                    You'll have to check it over. "<br>
     *         (��ԕω�) ���O:���O���x���F�G���[<br>
     *                    ���b�Z�[�W�F"Mistake on validation definition file.
     *                    - minArrayLength is not number.
     *                    You'll have to check it over. ", new NumberFormatException()<br>
     *
     * <br>
     * var��minArrayLength�����l�ɕϊ��ł��Ȃ��ꍇ�AValidatorException���������邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateArrayRange05() throws Exception {
        // �O����
        FieldChecks_JavaBeanStub01 bean = new FieldChecks_JavaBeanStub01();
        bean.setField1(null);
        field.setProperty("field1");
        Var var = new Var();
        var.setName("minArrayLength");
        var.setValue("test");
        field.addVar(var);

        // �e�X�g���{
        // ����
        try {
            new FieldChecks().validateArrayRange(bean, va, field, errors);
            fail();
        } catch (ValidatorException e) {
            String message = "Mistake on validation definition file. " +
                    "- minArrayLength is not number. " +
                    "You'll have to check it over. ";
            assertEquals(message, e.getMessage());
            assertTrue(LogUTUtil.checkError(message, new NumberFormatException()));
        }
    }

    /**
     * testValidateArrayRange06()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FF,G
     * <br><br>
     * ���͒l�F(����) bean:JavaBean<br>
     *                field1(String[]�^)=null<br>
     *         (����) va:not null<br>
     *         (����) field:property="field1"<br>
     *                var:<br>
     *                maxArrayLength="test"<br>
     *         (����) errors:not null<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:ValidatorException<br>
     *                    ���b�Z�[�W�F"Mistake on validation definition file.
     *                    - maxArrayLength is not number.
     *                    You'll have to check it over. "<br>
     *         (��ԕω�) ���O:���O���x���F�G���[<br>
     *                    ���b�Z�[�W�F"Mistake on validation definition file.
     *                    - maxArrayLength is not number.
     *                    You'll have to check it over. ", new NumberFormatException()<br>
     *
     * <br>
     * var��maxArrayLength�����l�ɕϊ��ł��Ȃ��ꍇ�A
     * ValidatorException���������邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateArrayRange06() throws Exception {
        // �O����
        FieldChecks_JavaBeanStub01 bean = new FieldChecks_JavaBeanStub01();
        bean.setField1(null);
        field.setProperty("field1");
        Var var = new Var();
        var.setName("maxArrayLength");
        var.setValue("test");
        field.addVar(var);

        // �e�X�g���{
        // ����
        try {
            new FieldChecks().validateArrayRange(bean, va, field, errors);
            fail();
        } catch (ValidatorException e) {
            String message = "Mistake on validation definition file. " +
                    "- maxArrayLength is not number. " +
                    "You'll have to check it over. ";
            assertEquals(message, e.getMessage());
            assertTrue(LogUTUtil.checkError(message, new NumberFormatException()));
        }
    }

    /**
     * testValidateArrayRange07()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:JavaBean<br>
     *                field1(String[]�^)=null<br>
     *         (����) va:not null<br>
     *         (����) field:property="field1"<br>
     *                var:<br>
     *                minArrayLength="0"<br>
     *                maxArrayLength="0"<br>
     *         (����) errors:not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *         (��ԕω�) errors:�Ăяo����Ȃ�<br>
     *
     * <br>
     * bean�̃`�F�b�N�Ώۂ̃t�B�[���h�l��null�̏ꍇ�A
     * �z��T�C�Y�u0�v�Ƃ��ă`�F�b�N���s���邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateArrayRange07() throws Exception {
        // �O����
        FieldChecks_JavaBeanStub01 bean = new FieldChecks_JavaBeanStub01();
        bean.setField1(null);
        field.setProperty("field1");
        Var var1 = new Var();
        var1.setName("minArrayLength");
        var1.setValue("0");
        field.addVar(var1);
        
        Var var2 = new Var();
        var2.setName("maxArrayLength");
        var2.setValue("0");
        field.addVar(var2);

        // �e�X�g���{
        // ����
        assertTrue(new FieldChecks().validateArrayRange(bean, va, field, errors));

        // addErrors�m�F
        assertEquals(0, errors.addErrorCount);
    }

    /**
     * testValidateArrayRange08()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FF,G
     * <br><br>
     * ���͒l�F(����) bean:JavaBean<br>
     *                field2(String�^)="test"<br>
     *         (����) va:not null<br>
     *         (����) field:property="field2"<br>
     *                var:<br>
     *                minArrayLength=Integer.MAX_VALUE<br>
     *                maxArrayLength=Integer.MAX_VALUE<br>
     *         (����) errors:not null<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:ValidatorException<br>
     *                    ���b�Z�[�W�F"property [" +
     *                    JavaBean.class.getName +
     *                    ".field2] must be instance of Array or Collection."
     *         (��ԕω�) ���O:���O���x���F�G���[<br>
     *                    ���b�Z�[�W�F"property [" +
     *                    JavaBean.class.getName +
     *                    ".field2] must be instance of Array or Collection."
     *
     * <br>
     * bean�̃`�F�b�N�Ώۂ̃t�B�[���h���z��ECollection�^�ł͂Ȃ��ꍇ�A
     * ValidatorException���������邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateArrayRange08() throws Exception {
        // �O����
        FieldChecks_JavaBeanStub01 bean = new FieldChecks_JavaBeanStub01();
        bean.setField1(null);
        field.setProperty("field2");
        Var var1 = new Var();
        var1.setName("minArrayLength");
        var1.setValue(String.valueOf(Integer.MAX_VALUE));
        field.addVar(var1);
        
        Var var2 = new Var();
        var2.setName("maxArrayLength");
        var2.setValue(String.valueOf(Integer.MAX_VALUE));
        field.addVar(var2);

        // �e�X�g���{
        // ����
        try {
            new FieldChecks().validateArrayRange(bean, va, field, errors);
            fail();
        } catch (ValidatorException e) {
            String message = "property [" +bean.getClass().getName() +
                ".field2] must be instance of Array or Collection.";
            assertEquals(message, e.getMessage());
            assertTrue(LogUTUtil.checkError(message));
        }
    }

    /**
     * testValidateArrayRange09()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:JavaBean<br>
     *                field1(String[]�^)={}<br>
     *                ���v�f�Ȃ�<br>
     *         (����) va:not null<br>
     *         (����) field:property="field1"<br>
     *                var:<br>
     *                maxArrayLength=Integer.MAX_VALUE<br>
     *         (����) errors:not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *         (��ԕω�) errors:�Ăяo����Ȃ�<br>
     *
     * <br>
     * var��minArrayLength���擾�ł��Ȃ��ꍇ�A
     * �͈͂̍ŏ��l��0�Ń`�F�b�N���s���邱�Ƃ��m�F�B<br>
     * ��maxArrayLength���ȗ����ꂽ�ꍇ��Integer.MAX_LENGTH���ő�l�ɂȂ邪
     * �����I�ɕs�\�Ȃ��߃e�X�g�͍s��Ȃ��B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateArrayRange09() throws Exception {
        // �O����
        FieldChecks_JavaBeanStub01 bean = new FieldChecks_JavaBeanStub01();
        bean.setField1(null);
        field.setProperty("field1");
        Var var = new Var();
        var.setName("maxArrayLength");
        var.setValue(String.valueOf(Integer.MAX_VALUE));
        field.addVar(var);

        // �e�X�g���{
        // ����
        assertTrue(new FieldChecks().validateArrayRange(bean, va, field, errors));

        // addErrors�m�F
        assertEquals(0, errors.addErrorCount);
    }

    /**
     * testValidateArrayRange10()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:JavaBean<br>
     *                field1(String[]�^)={}<br>
     *                ���v�f�Ȃ�<br>
     *         (����) va:not null<br>
     *         (����) field:property="field1"<br>
     *                var:<br>
     *                minArrayLength=""<br>
     *         (����) errors:not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *         (��ԕω�) errors:�Ăяo����Ȃ�<br>
     *
     * <br>
     * var��minArrayLength���󕶎��̏ꍇ�A�͈͂̍ŏ��l��0�Ń`�F�b�N��
     * �s���邱�Ƃ��m�F�B<br>
     * ��maxArrayLength���󕶎��̏ꍇ��Integer.MAX_LENGTH���ő�l�ɂȂ邪
     * �����I�ɕs�\�Ȃ��߃e�X�g�͍s��Ȃ��B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateArrayRange10() throws Exception {
        // �O����
        FieldChecks_JavaBeanStub01 bean = new FieldChecks_JavaBeanStub01();
        bean.setField1(null);
        field.setProperty("field1");
        Var var = new Var();
        var.setName("minArrayLength");
        var.setValue("");
        field.addVar(var);

        // �e�X�g���{
        // ����
        assertTrue(new FieldChecks().validateArrayRange(bean, va, field, errors));

        // addErrors�m�F
        assertEquals(0, errors.addErrorCount);
    }

    /**
     * testValidateArrayRange11()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:JavaBean<br>
     *                field1(String[]�^)={<br>
     *                    "test1","test2","test3"<br>
     *                }<br>
     *                ���v�f�R<br>
     *         (����) va:not null<br>
     *         (����) field:property="field1"<br>
     *                var:<br>
     *                minArrayLength="1"<br>
     *                maxArrayLength="5"<br>
     *         (����) errors:not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *         (��ԕω�) errors:�Ăяo����Ȃ�<br>
     *
     * <br>
     * bean�̃`�F�b�N�Ώۂ̔z��t�B�[���h�̗v�f�����A
     * var��minArrayLength��maxArrayLength�͈͓̔��̂Ƃ��A
     * true���擾�ł��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateArrayRange11() throws Exception {
        // �O����
        FieldChecks_JavaBeanStub01 bean = new FieldChecks_JavaBeanStub01();
        String[] array = {
            "test1", "test2", "test3"
        };
        bean.setField1(array);
        field.setProperty("field1");
        Var var1 = new Var();
        var1.setName("minArrayLength");
        var1.setValue("1");
        field.addVar(var1);
        
        Var var2 = new Var();
        var2.setName("maxArrayLength");
        var2.setValue("5");
        field.addVar(var2);

        // �e�X�g���{
        // ����
        assertTrue(new FieldChecks().validateArrayRange(bean, va, field, errors));

        // addErrors�m�F
        assertEquals(0, errors.addErrorCount);
    }

    /**
     * testValidateArrayRange12()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:JavaBean<br>
     *                field3(Collection�^)={<br>
     *                    "test1","test2","test3"<br>
     *                }<br>
     *                ���v�f�R<br>
     *         (����) va:not null<br>
     *         (����) field:property="field3"<br>
     *                var:<br>
     *                minArrayLength="4"<br>
     *                maxArrayLength="10"<br>
     *         (����) errors:not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:false<br>
     *         (��ԕω�) errors:bean,field,va�������Ƃ���
     *                           addErrors���Ăяo�����B<br>
     *
     * <br>
     * bean�̃`�F�b�N�Ώۂ�Collection�^�t�B�[���h�̗v�f�����A
     * var��minArrayLength��maxArrayLength�͈̔͂�菭�Ȃ��ꍇ�A
     * �G���[��ǉ�����false���ԋp����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testValidateArrayRange12() throws Exception {
        // �O����
        FieldChecks_JavaBeanStub01 bean = new FieldChecks_JavaBeanStub01();
        Collection list = new ArrayList();
        list.add("test1");
        list.add("test2");
        list.add("test3");
        bean.setField3(list);
        field.setProperty("field3");
        Var var1 = new Var();
        var1.setName("minArrayLength");
        var1.setValue("4");
        field.addVar(var1);
        
        Var var2 = new Var();
        var2.setName("maxArrayLength");
        var2.setValue("10");
        field.addVar(var2);

        // �e�X�g���{
        // ����
        assertFalse(new FieldChecks().validateArrayRange(bean, va, field, errors));

        // addErrors�m�F
        assertEquals(1, errors.addErrorCount);
        assertSame(bean, errors.beanList.get(0));
        assertSame(va, errors.vaList.get(0));
        assertSame(field, errors.fieldList.get(0));
    }

    /**
     * testValidateArrayRange13()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:JavaBean<br>
     *                field4(int[]�^)={<br>
     *                    1,2,3,4,5,6,7,8,9,0<br>
     *                }<br>
     *                ���v�f10<br>
     *         (����) va:not null<br>
     *         (����) field:property="field4"<br>
     *                var:<br>
     *                minArrayLength="1"<br>
     *                maxArrayLength="5"<br>
     *         (����) errors:not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:false<br>
     *         (��ԕω�) errors:bean,field,va�������Ƃ���
     *                           addErrors���Ăяo�����B<br>
     *
     * <br>
     * bean�̃`�F�b�N�Ώۂ̃v���~�e�B�u�z��^�t�B�[���h�̗v�f�����A
     * var��minArrayLength��maxArrayLength�͈̔͂��傫���ꍇ�A
     * �G���[��ǉ�����false���ԋp����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateArrayRange13() throws Exception {
        // �O����
        FieldChecks_JavaBeanStub01 bean = new FieldChecks_JavaBeanStub01();
        int[] array = {
            1, 2, 3, 4, 5, 6, 7, 8, 9, 0
        };
        bean.setField4(array);
        field.setProperty("field4");
        Var var1 = new Var();
        var1.setName("minArrayLength");
        var1.setValue("1");
        field.addVar(var1);
        
        Var var2 = new Var();
        var2.setName("maxArrayLength");
        var2.setValue("5");
        field.addVar(var2);

        // �e�X�g���{
        // ����
        assertFalse(new FieldChecks().validateArrayRange(bean, va, field, errors));

        // addErrors�m�F
        assertEquals(1, errors.addErrorCount);
        assertSame(bean, errors.beanList.get(0));
        assertSame(va, errors.vaList.get(0));
        assertSame(field, errors.fieldList.get(0));
    }

    /**
     * testValidateArrayRange14()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:JavaBean<br>
     *                field1(String[]�^)={<br>
     *                    "test1","test2","test3"<br>
     *                }<br>
     *                ���v�f�R<br>
     *         (����) va:not null<br>
     *         (����) field:property="field1"<br>
     *                var:<br>
     *                minArrayLength="3"<br>
     *                maxArrayLength="3"<br>
     *         (����) errors:not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *         (��ԕω�) errors:�Ăяo����Ȃ�<br>
     *
     * <br>
     * bean�̃`�F�b�N�Ώۂ̔z��t�B�[���h�̗v�f���ƁA
     * var��minArrayLength��maxArrayLength�̒l���������Ƃ��A
     * true���擾�ł��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateArrayRange14() throws Exception {
        // �O����
        FieldChecks_JavaBeanStub01 bean = new FieldChecks_JavaBeanStub01();
        String[] array = {
            "test1", "test2", "test3"
        };
        bean.setField1(array);
        field.setProperty("field1");
        Var var1 = new Var();
        var1.setName("minArrayLength");
        var1.setValue("3");
        field.addVar(var1);
        
        Var var2 = new Var();
        var2.setName("maxArrayLength");
        var2.setValue("3");
        field.addVar(var2);

        // �e�X�g���{
        // ����
        assertTrue(new FieldChecks().validateArrayRange(bean, va, field, errors));

        // addErrors�m�F
        assertEquals(0, errors.addErrorCount);
    }

    /**
     * testValidateArrayRange15()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FF,G
     * <br><br>
     * ���͒l�F(����) bean:JavaBean<br>
     *                field5(Stiring[]�^)<br>
     *                getField5�ŗ�O����������B<br>
     *         (����) va:not null<br>
     *         (����) field:property="field5"<br>
     *                var:<br>
     *                minArrayLength="3"<br>
     *                maxArrayLength="3"<br>
     *         (����) errors:not null<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:ValidatorException<br>
     *                    ���b�Z�[�W�F"Cannot get property [" +
     *                    JavaBean.class.getName+".field5]"<br>
     *         (��ԕω�) ���O:���O���x���F�G���[<br>
     *                    ���b�Z�[�W�F"Cannot get property [" +
     *                    JavaBean.class.getName+".field5]", 
                new PropertyAccessException(new IllegalArgumentException())));<br>
     *
     * <br>
     * �`�F�b�N�Ώۂ̃v���p�e�B�̎擾���ɗ�O�����������ꍇ�A
     * ValidatorException���������邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateArrayRange15() throws Exception {
        // �O����
        FieldChecks_JavaBeanStub01 bean = new FieldChecks_JavaBeanStub01();
        field.setProperty("field5");
        Var var1 = new Var();
        var1.setName("minArrayLength");
        var1.setValue("3");
        field.addVar(var1);
        
        Var var2 = new Var();
        var2.setName("maxArrayLength");
        var2.setValue("3");
        field.addVar(var2);

        // �e�X�g���{
        // ����
        try {
            new FieldChecks().validateArrayRange(bean, va, field, errors);
            fail();
        } catch (ValidatorException e) {
            String message = "Cannot get property [" +
                bean.getClass().getName() + ".field5]";
            assertEquals(message, e.getMessage());
            assertTrue(LogUTUtil.checkError(message,
                new PropertyAccessException(new IllegalArgumentException())));
        }
    }

}
