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

import org.apache.commons.validator.Field;
import org.apache.commons.validator.ValidatorAction;
import org.apache.commons.validator.ValidatorException;
import org.apache.commons.validator.Var;

import jp.terasoluna.utlib.LogUTUtil;
import jp.terasoluna.utlib.UTUtil;
import jp.terasoluna.fw.validation.FieldChecks;
import jp.terasoluna.fw.validation.FieldChecks_ValidationErrorsImpl01;
import junit.framework.TestCase;

/**
 * {@link jp.terasoluna.fw.validation.FieldChecks} �N���X�̃u���b�N�{�b�N�X�e�X�g�B
 *
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4>
 * TERASOLUNA�̓��̓`�F�b�N�@�\�ŋ��ʂɎg�p����錟�؃��[���N���X�B
 * <p>
 *
 * @see jp.terasoluna.fw.validation.FieldChecks
 */
public class FieldChecksTest06 extends TestCase {

    /**
     * ���̃e�X�g�P�[�X�����s����ׂ�
     * GUI �A�v���P�[�V�������N������B
     *
     * @param args java �R�}���h�ɐݒ肳�ꂽ�p�����[�^
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(FieldChecksTest06.class);
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
    public FieldChecksTest06(String name) {
        super(name);
    }

    /**
     * testValidateAlphaNumericString01()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FA
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
    public void testValidateAlphaNumericString01() throws Exception {
        // �O����
        // bean : null
        Object bean = null;
        // va : not null
        ValidatorAction va = new ValidatorAction();
        // field : not null
        Field field = new Field();
        // errors : not null
        FieldChecks_ValidationErrorsImpl01 errors =
            new FieldChecks_ValidationErrorsImpl01();
        
        // �e�X�g���{
        boolean b =
            new FieldChecks().validateAlphaNumericString(bean, va, field, errors);

        // ����
        assertTrue(b);
        assertEquals(0, errors.addErrorCount);
    }

    /**
     * testValidateAlphaNumericString02()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FA
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
    public void testValidateAlphaNumericString02() throws Exception {
        // �O����
        // bean : ""
        Object bean = "";
        // va : not null
        ValidatorAction va = new ValidatorAction();
        // field : not null
        Field field = new Field();
        // errors : not null
        FieldChecks_ValidationErrorsImpl01 errors =
            new FieldChecks_ValidationErrorsImpl01();
        
        // �e�X�g���{
        boolean b =
            new FieldChecks().validateAlphaNumericString(bean, va, field, errors);

        // ����
        assertTrue(b);
        assertEquals(0, errors.addErrorCount);
    }

    /**
     * testValidateAlphaNumericString03()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) bean:"a0A"<br>
     *         (����) va:not null<br>
     *         (����) field:not null<br>
     *         (����) errors:not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *         (��ԕω�) errors:�Ăяo����Ȃ�<br>
     *
     * <br>
     * ������bean�����p�p�������݂̂ō\������Ă���ꍇ�Atrue���ԋp����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateAlphaNumericString03() throws Exception {
        // �O����
        // bean : "a0A"
        Object bean = "a0A";
        // va : not null
        ValidatorAction va = new ValidatorAction();
        // field : not null
        Field field = new Field();
        // errors : not null
        FieldChecks_ValidationErrorsImpl01 errors =
            new FieldChecks_ValidationErrorsImpl01();
        
        // �e�X�g���{
        boolean b =
            new FieldChecks().validateAlphaNumericString(bean, va, field, errors);

        // ����
        assertTrue(b);
        assertEquals(0, errors.addErrorCount);
    }

    /**
     * testValidateAlphaNumericString04()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) bean:"Zg3%"<br>
     *         (����) va:not null<br>
     *         (����) field:not null<br>
     *         (����) errors:not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:false<br>
     *         (��ԕω�) errors:bean,field,va�������Ƃ���addErrors���Ăяo�����B<br>
     *
     * <br>
     * ������bean�ɔ��p�p�������ȊO�̕������܂܂�Ă���ꍇ�A�G���[��ǉ�����true���ԋp����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateAlphaNumericString04() throws Exception {
        // �O����
        // bean : "Zg3%"
        Object bean = "Zg3%";
        // va : not null
        ValidatorAction va = new ValidatorAction();
        // field : not null
        Field field = new Field();
        // errors : not null
        FieldChecks_ValidationErrorsImpl01 errors =
            new FieldChecks_ValidationErrorsImpl01();
        
        // �e�X�g���{
        boolean b = new FieldChecks().validateAlphaNumericString(bean, va, field, errors);

        // ����
        assertFalse(b);
        // �ďo�m�F
        assertEquals(1, errors.addErrorCount);
        // �����m�F
        ArrayList beanList = (ArrayList) UTUtil.getPrivateField(errors, "beanList");
        assertSame(bean, beanList.get(0));
        ArrayList vaList = (ArrayList) UTUtil.getPrivateField(errors, "vaList");
        assertSame(va, vaList.get(0));
        ArrayList fieldList = (ArrayList) UTUtil.getPrivateField(errors, "fieldList");
        assertSame(field, fieldList.get(0));
    }

    /**
     * testValidateCapAlphaNumericString01()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FA
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
    public void testValidateCapAlphaNumericString01() throws Exception {
        // �O����
        // bean : null
        Object bean = null;
        // va : not null
        ValidatorAction va = new ValidatorAction();
        // field : not null
        Field field = new Field();
        // errors : not null
        FieldChecks_ValidationErrorsImpl01 errors =
            new FieldChecks_ValidationErrorsImpl01();
        
        // �e�X�g���{
        boolean b =
            new FieldChecks().validateCapAlphaNumericString(bean, va, field, errors);

        // ����
        assertTrue(b);
        assertEquals(0, errors.addErrorCount);
    }

    /**
     * testValidateCapAlphaNumericString02()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FA
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
    public void testValidateCapAlphaNumericString02() throws Exception {
        // �O����
        // bean : ""
        Object bean = "";
        // va : not null
        ValidatorAction va = new ValidatorAction();
        // field : not null
        Field field = new Field();
        // errors : not null
        FieldChecks_ValidationErrorsImpl01 errors =
            new FieldChecks_ValidationErrorsImpl01();
        
        // �e�X�g���{
        boolean b =
            new FieldChecks().validateCapAlphaNumericString(bean, va, field, errors);

        // ����
        assertTrue(b);
        assertEquals(0, errors.addErrorCount);
    }

    /**
     * testValidateCapAlphaNumericString03()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) bean:"ABC0"<br>
     *         (����) va:not null<br>
     *         (����) field:not null<br>
     *         (����) errors:not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *         (��ԕω�) errors:�Ăяo����Ȃ�<br>
     *
     * <br>
     * ������bean���啶���̔��p�p�������݂̂ō\������Ă���ꍇ�Atrue���ԋp����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateCapAlphaNumericString03() throws Exception {
        // �O����
        // bean : "ABC0"
        Object bean = "ABC0";
        // va : not null
        ValidatorAction va = new ValidatorAction();
        // field : not null
        Field field = new Field();
        // errors : not null
        FieldChecks_ValidationErrorsImpl01 errors =
            new FieldChecks_ValidationErrorsImpl01();
        
        // �e�X�g���{
        boolean b =
            new FieldChecks().validateCapAlphaNumericString(bean, va, field, errors);

        // ����
        assertTrue(b);
        assertEquals(0, errors.addErrorCount);
    }

    /**
     * testValidateCapAlphaNumericString04()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) bean:"Aa0"<br>
     *         (����) va:not null<br>
     *         (����) field:not null<br>
     *         (����) errors:not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:false<br>
     *         (��ԕω�) errors:bean,field,va�������Ƃ���addErrors���Ăяo�����B<br>
     *
     * <br>
     * ������bean�ɑ啶���̔��p�p�������ȊO�̕������܂܂�Ă���ꍇ�A�G���[��ǉ�����true���ԋp����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateCapAlphaNumericString04() throws Exception {
        // �O����
        // bean : "Aa0"
        Object bean = "Aa0";
        // va : not null
        ValidatorAction va = new ValidatorAction();
        // field : not null
        Field field = new Field();
        // errors : not null
        FieldChecks_ValidationErrorsImpl01 errors =
            new FieldChecks_ValidationErrorsImpl01();
        
        // �e�X�g���{
        boolean b = new FieldChecks().validateCapAlphaNumericString(bean, va, field, errors);

        // ����
        assertFalse(b);
        // �ďo�m�F
        assertEquals(1, errors.addErrorCount);
        // �����m�F
        ArrayList beanList = (ArrayList) UTUtil.getPrivateField(errors, "beanList");
        assertSame(bean, beanList.get(0));
        ArrayList vaList = (ArrayList) UTUtil.getPrivateField(errors, "vaList");
        assertSame(va, vaList.get(0));
        ArrayList fieldList = (ArrayList) UTUtil.getPrivateField(errors, "fieldList");
        assertSame(field, fieldList.get(0));
    }

    /**
     * testValidateNumber01()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FA
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
    public void testValidateNumber01() throws Exception {
        // �O����
        // bean : null
        Object bean = null;
        // va : not null
        ValidatorAction va = new ValidatorAction();
        // field : not null
        Field field = new Field();
        // errors : not null
        FieldChecks_ValidationErrorsImpl01 errors =
            new FieldChecks_ValidationErrorsImpl01();
        
        // �e�X�g���{
        boolean b =
            new FieldChecks().validateNumber(bean, va, field, errors);

        // ����
        assertTrue(b);
        assertEquals(0, errors.addErrorCount);
    }

    /**
     * testValidateNumber02()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FA
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
    public void testValidateNumber02() throws Exception {
        // �O����
        // bean : ""
        Object bean = "";
        // va : not null
        ValidatorAction va = new ValidatorAction();
        // field : not null
        Field field = new Field();
        // errors : not null
        FieldChecks_ValidationErrorsImpl01 errors =
            new FieldChecks_ValidationErrorsImpl01();
        
        // �e�X�g���{
        boolean b =
            new FieldChecks().validateNumber(bean, va, field, errors);

        // ����
        assertTrue(b);
        assertEquals(0, errors.addErrorCount);
    }

    /**
     * testValidateNumber03()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) bean:"�T"�i�S�p�j<br>
     *         (����) va:not null<br>
     *         (����) field:not null<br>
     *         (����) errors:not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:false<br>
     *         (��ԕω�) errors:bean,field,va�������Ƃ���addErrors���Ăяo�����B<br>
     *
     * <br>
     * ���͒l���S�p�̏ꍇ�A�G���[��ǉ���false���ԋp����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateNumber03() throws Exception {
        // �O����
        // bean : "�T"
        Object bean = "�T";
        // va : not null
        ValidatorAction va = new ValidatorAction();
        // field : not null
        Field field = new Field();
        // errors : not null
        FieldChecks_ValidationErrorsImpl01 errors =
            new FieldChecks_ValidationErrorsImpl01();
        
        // �e�X�g���{
        boolean b =
            new FieldChecks().validateNumber(bean, va, field, errors);

        // ����
        assertFalse(b);
        // �ďo�m�F
        assertEquals(1, errors.addErrorCount);
        // �����m�F
        ArrayList beanList = (ArrayList) UTUtil.getPrivateField(errors, "beanList");
        assertSame(bean, beanList.get(0));
        ArrayList vaList = (ArrayList) UTUtil.getPrivateField(errors, "vaList");
        assertSame(va, vaList.get(0));
        ArrayList fieldList = (ArrayList) UTUtil.getPrivateField(errors, "fieldList");
        assertSame(field, fieldList.get(0));
    }

    /**
     * testValidateNumber04()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) bean:"test"<br>
     *         (����) va:not null<br>
     *         (����) field:not null<br>
     *         (����) errors:not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:false<br>
     *         (��ԕω�) errors:bean,field,va�������Ƃ���addErrors���Ăяo�����B<br>
     *
     * <br>
     * ���͒l�����l�ɕϊ��ł��Ȃ��ꍇ�A�G���[��ǉ���false���ԋp����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateNumber04() throws Exception {
        // �O����
        // bean : "test"
        Object bean = "test";
        // va : not null
        ValidatorAction va = new ValidatorAction();
        // field : not null
        Field field = new Field();
        // errors : not null
        FieldChecks_ValidationErrorsImpl01 errors =
            new FieldChecks_ValidationErrorsImpl01();
        
        // �e�X�g���{
        boolean b =
            new FieldChecks().validateNumber(bean, va, field, errors);

        // ����
        assertFalse(b);
        // �ďo�m�F
        assertEquals(1, errors.addErrorCount);
        // �����m�F
        ArrayList beanList = (ArrayList) UTUtil.getPrivateField(errors, "beanList");
        assertSame(bean, beanList.get(0));
        ArrayList vaList = (ArrayList) UTUtil.getPrivateField(errors, "vaList");
        assertSame(va, vaList.get(0));
        ArrayList fieldList = (ArrayList) UTUtil.getPrivateField(errors, "fieldList");
        assertSame(field, fieldList.get(0));
    }

    /**
     * testValidateNumber05()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) bean:"5"<br>
     *         (����) va:not null<br>
     *         (����) field:var:<br>
     *                integerLength="abc"<br>
     *         (����) errors:not null<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:ValidatorException<br>
     *                    ���b�Z�[�W�F"Mistake on validation definition file. - integerLength is not number. You'll have to check it over. "<br>
     *         (��ԕω�) ���O:���O���x���F�G���[<br>
     *                    ���b�Z�[�W�F"Mistake on validation definition file. - integerLength is not number. You'll have to check it over. ", new NumberFormatException()<br>
     *
     * <br>
     * var��integerLength�����l�ɕϊ��ł��Ȃ��ꍇ�AValidatorException���������邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateNumber05() throws Exception {
        // �O����
        // bean : "5"
        Object bean = "5";
        // va : not null
        ValidatorAction va = new ValidatorAction();
        // field : var:integerLength="abc"
        Field field = new Field();
        Var var = new Var();
        var.setName("integerLength");
        var.setValue("abc");
        field.addVar(var);
        
        // errors : not null
        FieldChecks_ValidationErrorsImpl01 errors =
            new FieldChecks_ValidationErrorsImpl01();
        
        try {
            // �e�X�g���{
            @SuppressWarnings("unused") boolean b =
                new FieldChecks().validateNumber(bean, va, field, errors);
            fail();
        } catch (ValidatorException e) {
            // ����
            String message = "Mistake on validation definition file. "
                + "- integerLength is not number. "
                + "You'll have to check it over. ";
            assertEquals(message, e.getMessage());
            assertTrue(LogUTUtil.checkError(message, new NumberFormatException()));
        }
    }

    /**
     * testValidateNumber06()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) bean:"5"<br>
     *         (����) va:not null<br>
     *         (����) field:var:<br>
     *                scale="abc"<br>
     *         (����) errors:not null<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:ValidatorException<br>
     *                    ���b�Z�[�W�F"Mistake on validation definition file. - scale is not number. You'll have to check it over. "<br>
     *         (��ԕω�) ���O:���O���x���F�G���[<br>
     *                    ���b�Z�[�W�F"Mistake on validation definition file. - scale is not number. You'll have to check it over. ", new NumberFormatException()<br>
     *
     * <br>
     * var��scale�����l�ɕϊ��ł��Ȃ��ꍇ�AValidatorException���������邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateNumber06() throws Exception {
        // �O����
        // bean : "5"
        Object bean = "5";
        // va : not null
        ValidatorAction va = new ValidatorAction();
        // field : var:scale="abc"
        Field field = new Field();
        Var var = new Var();
        var.setName("scale");
        var.setValue("abc");
        field.addVar(var);
        
        // errors : not null
        FieldChecks_ValidationErrorsImpl01 errors =
            new FieldChecks_ValidationErrorsImpl01();
        
        try {
            // �e�X�g���{
            @SuppressWarnings("unused") boolean b =
                new FieldChecks().validateNumber(bean, va, field, errors);
            fail();
        } catch (ValidatorException e) {
            // ����
            String message = "Mistake on validation definition file. "
                + "- scale is not number. "
                + "You'll have to check it over. ";
            assertEquals(message, e.getMessage());
            assertTrue(LogUTUtil.checkError(message, new NumberFormatException()));
        }
    }

    /**
     * testValidateNumber07()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�F
     * <br><br>
     * ���͒l�F(����) bean:"100.05"<br>
     *         (����) va:not null<br>
     *         (����) field:var:<br>
     *                integerLength="5"<br>
     *                scale="3"<br>
     *         (����) errors:not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *         (��ԕω�) errors:�Ăяo����Ȃ�<br>
     *
     * <br>
     * ���͂��ꂽ�����̐������̌������Avar��integerLength�̒l��菬�����A�������̌�����var��scale�̒l��菬�����ꍇ�Atrue���ԋp����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateNumber07() throws Exception {
        // �O����
        // bean : "100.05"
        Object bean = "100.05";
        // va : not null
        ValidatorAction va = new ValidatorAction();
        // field : var:integerLength="5" scale="3"
        Field field = new Field();
        Var var1 = new Var();
        var1.setName("integerLength");
        var1.setValue("5");
        field.addVar(var1);
        
        Var var2 = new Var();
        var2.setName("scale");
        var2.setValue("3");
        field.addVar(var2);
        
        // errors : not null
        FieldChecks_ValidationErrorsImpl01 errors =
            new FieldChecks_ValidationErrorsImpl01();
        
        // �e�X�g���{
        boolean b =
            new FieldChecks().validateNumber(bean, va, field, errors);

        // ����
        assertTrue(b);
        assertEquals(0, errors.addErrorCount);
    }

    /**
     * testValidateNumber08()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) bean:"100.05"<br>
     *         (����) va:not null<br>
     *         (����) field:var:<br>
     *                integerLength="3"<br>
     *                scale="2"<br>
     *                isAccordedInteger="true"<br>
     *                isAccordedScale="true"<br>
     *         (����) errors:not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *         (��ԕω�) errors:�Ăяo����Ȃ�<br>
     *
     * <br>
     * var��isAccordedInteger��true���w�肳��Ă��āA���͂��ꂽ�����̐������̌������Avar��integerLength�̒l�Ɠ������Avar��isAccordedScale��true���w�肳��Ă��āA�������̌�����var��scale�̒l�Ɠ������ꍇ�Atrue���ԋp����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateNumber08() throws Exception {
        // �O����
        // bean : "100.05"
        Object bean = "100.05";
        // va : not null
        ValidatorAction va = new ValidatorAction();
        // field : var:integerLength="3" scale="2" isAccordedInteger="true" isAccordedScale="true"
        Field field = new Field();
        Var var1 = new Var();
        var1.setName("integerLength");
        var1.setValue("3");
        field.addVar(var1);
        
        Var var2 = new Var();
        var2.setName("scale");
        var2.setValue("2");
        field.addVar(var2);
        
        Var var3 = new Var();
        var3.setName("isAccordedInteger");
        var3.setValue("true");
        field.addVar(var3);
        
        Var var4 = new Var();
        var4.setName("isAccordedScale");
        var4.setValue("true");
        field.addVar(var4);
        
        // errors : not null
        FieldChecks_ValidationErrorsImpl01 errors =
            new FieldChecks_ValidationErrorsImpl01();
        
        // �e�X�g���{
        boolean b =
            new FieldChecks().validateNumber(bean, va, field, errors);

        // ����
        assertTrue(b);
        assertEquals(0, errors.addErrorCount);
    }

    /**
     * testValidateNumber09()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) bean:"100.05"<br>
     *         (����) va:not null<br>
     *         (����) field:var:<br>
     *                integerLength="5"<br>
     *                scale="3"<br>
     *                isAccordedInteger="test"<br>
     *                isAccordedScale="test"<br>
     *         (����) errors:not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *         (��ԕω�) errors:�Ăяo����Ȃ�<br>
     *
     * <br>
     * var��isAccordedInteger��true�ȊO�̕����񂪎w�肳��Ă��āA���͂��ꂽ�����̐������̌������Avar��integerLength�̒l��菬�����Avar��isAccordedScale��true�ȊO�̕����񂪎w�肳��Ă��āA�������̌�����var��scale�̒l��菬�����ꍇ�Atrue���ԋp����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateNumber09() throws Exception {
        // �O����
        // bean : "100.05"
        Object bean = "100.05";
        // va : not null
        ValidatorAction va = new ValidatorAction();
        // field : var:integerLength="5" scale="3" isAccordedInteger="test" isAccordedScale="test"
        Field field = new Field();
        
        Var var1 = new Var();
        var1.setName("integerLength");
        var1.setValue("5");
        field.addVar(var1);
        
        Var var2 = new Var();
        var2.setName("scale");
        var2.setValue("3");
        field.addVar(var2);
        
        Var var3 = new Var();
        var3.setName("isAccordedInteger");
        var3.setValue("test");
        field.addVar(var3);
        
        Var var4 = new Var();
        var4.setName("isAccordedScale");
        var4.setValue("test");
        field.addVar(var4);
        
        // errors : not null
        FieldChecks_ValidationErrorsImpl01 errors =
            new FieldChecks_ValidationErrorsImpl01();
        
        // �e�X�g���{
        boolean b =
            new FieldChecks().validateNumber(bean, va, field, errors);

        // ����
        assertTrue(b);
        assertEquals(0, errors.addErrorCount);
    }

    /**
     * testValidateNumber10()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) bean:"100.05"<br>
     *         (����) va:not null<br>
     *         (����) field:var:<br>
     *                integerLength="2"<br>
     *         (����) errors:not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:false<br>
     *         (��ԕω�) errors:bean,field,va�������Ƃ���addErrors���Ăяo�����B<br>
     *
     * <br>
     * ���͂��ꂽ�����̐������̌������Avar��integerLength�̒l���傫���ꍇ�A�G���[��ǉ�����false���ԋp����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateNumber10() throws Exception {
        // �O����
        // bean : "100.05"
        Object bean = "100.05";
        // va : not null
        ValidatorAction va = new ValidatorAction();
        
        // field : var:integerLength="2"
        Field field = new Field();
        Var var = new Var();
        var.setName("integerLength");
        var.setValue("2");
        field.addVar(var);
        
        // errors : not null
        FieldChecks_ValidationErrorsImpl01 errors =
            new FieldChecks_ValidationErrorsImpl01();
        
        // �e�X�g���{
        boolean b =
            new FieldChecks().validateNumber(bean, va, field, errors);
        
        // ����
        assertFalse(b);
        // �ďo�m�F
        assertEquals(1, errors.addErrorCount);
        // �����m�F
        ArrayList beanList = (ArrayList) UTUtil.getPrivateField(errors, "beanList");
        assertSame(bean, beanList.get(0));
        ArrayList vaList = (ArrayList) UTUtil.getPrivateField(errors, "vaList");
        assertSame(va, vaList.get(0));
        ArrayList fieldList = (ArrayList) UTUtil.getPrivateField(errors, "fieldList");
        assertSame(field, fieldList.get(0));
    }

    /**
     * testValidateNumber11()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) bean:"100.05"<br>
     *         (����) va:not null<br>
     *         (����) field:var:<br>
     *                scale="1"<br>
     *         (����) errors:not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:false<br>
     *         (��ԕω�) errors:bean,field,va�������Ƃ���addErrors���Ăяo�����B<br>
     *
     * <br>
     * ���͂��ꂽ�����̏������̌������Avar��scale�̒l���傫���ꍇ�A�G���[��ǉ�����false���ԋp����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateNumber11() throws Exception {
        // �O����
        // bean : "100.05"
        Object bean = "100.05";
        // va : not null
        ValidatorAction va = new ValidatorAction();
        
        // field : var:scale="1"
        Field field = new Field();
        Var var = new Var();
        var.setName("scale");
        var.setValue("1");
        field.addVar(var);
        
        // errors : not null
        FieldChecks_ValidationErrorsImpl01 errors =
            new FieldChecks_ValidationErrorsImpl01();
        
        // �e�X�g���{
        boolean b =
            new FieldChecks().validateNumber(bean, va, field, errors);
        
        // ����
        assertFalse(b);
        // �ďo�m�F
        assertEquals(1, errors.addErrorCount);
        // �����m�F
        ArrayList beanList = (ArrayList) UTUtil.getPrivateField(errors, "beanList");
        assertSame(bean, beanList.get(0));
        ArrayList vaList = (ArrayList) UTUtil.getPrivateField(errors, "vaList");
        assertSame(va, vaList.get(0));
        ArrayList fieldList = (ArrayList) UTUtil.getPrivateField(errors, "fieldList");
        assertSame(field, fieldList.get(0));
    }

    /**
     * testValidateNumber12()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) bean:"100.05"<br>
     *         (����) va:not null<br>
     *         (����) field:var:<br>
     *                integerLength="5"<br>
     *                isAccordedInteger="true"<br>
     *         (����) errors:not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:false<br>
     *         (��ԕω�) errors:bean,field,va�������Ƃ���addErrors���Ăяo�����B<br>
     *
     * <br>
     * var��isAccordedInteger��true���w�肳��Ă��āA���͂��ꂽ�����̐������̌������Avar��integerLength�̒l��菬�����ꍇ�A�G���[��ǉ�����false���ԋp����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateNumber12() throws Exception {
        // �O����
        // bean : "100.05"
        Object bean = "100.05";
        // va : not null
        ValidatorAction va = new ValidatorAction();
        // field : var:scale="5" isAccordedInteger="true"
        Field field = new Field();
        Var var1 = new Var();
        var1.setName("scale");
        var1.setValue("5");
        field.addVar(var1);
        
        Var var2 = new Var();
        var2.setName("isAccordedInteger");
        var2.setValue("true");
        field.addVar(var2);
        
        // errors : not null
        FieldChecks_ValidationErrorsImpl01 errors =
            new FieldChecks_ValidationErrorsImpl01();
        
        // �e�X�g���{
        boolean b =
            new FieldChecks().validateNumber(bean, va, field, errors);
        
        // ����
        assertFalse(b);
        // �ďo�m�F
        assertEquals(1, errors.addErrorCount);
        // �����m�F
        ArrayList beanList = (ArrayList) UTUtil.getPrivateField(errors, "beanList");
        assertSame(bean, beanList.get(0));
        ArrayList vaList = (ArrayList) UTUtil.getPrivateField(errors, "vaList");
        assertSame(va, vaList.get(0));
        ArrayList fieldList = (ArrayList) UTUtil.getPrivateField(errors, "fieldList");
        assertSame(field, fieldList.get(0));
    }

    /**
     * testValidateNumber13()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) bean:"100.05"<br>
     *         (����) va:not null<br>
     *         (����) field:var:<br>
     *                scale="3"<br>
     *                isAccordedScale="true"<br>
     *         (����) errors:not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:false<br>
     *         (��ԕω�) errors:bean,field,va�������Ƃ���addErrors���Ăяo�����B<br>
     *
     * <br>
     * var��isAccordedScale��true���w�肳��Ă��āA���͂��ꂽ�����̏������̌������Avar��scale�̒l��菬�����ꍇ�A�G���[��ǉ�����false���ԋp����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateNumber13() throws Exception {
        // �O����
        // bean : "100.05"
        Object bean = "100.05";
        // va : not null
        ValidatorAction va = new ValidatorAction();
        // field : var:scale="3" isAccordedScale="true"
        Field field = new Field();
        Var var1 = new Var();
        var1.setName("scale");
        var1.setValue("3");
        field.addVar(var1);
        
        Var var2 = new Var();
        var2.setName("isAccordedScale");
        var2.setValue("true");
        field.addVar(var2);
        
        // errors : not null
        FieldChecks_ValidationErrorsImpl01 errors =
            new FieldChecks_ValidationErrorsImpl01();
        
        // �e�X�g���{
        boolean b =
            new FieldChecks().validateNumber(bean, va, field, errors);
        
        // ����
        assertFalse(b);
        // �ďo�m�F
        assertEquals(1, errors.addErrorCount);
        // �����m�F
        ArrayList beanList = (ArrayList) UTUtil.getPrivateField(errors, "beanList");
        assertSame(bean, beanList.get(0));
        ArrayList vaList = (ArrayList) UTUtil.getPrivateField(errors, "vaList");
        assertSame(va, vaList.get(0));
        ArrayList fieldList = (ArrayList) UTUtil.getPrivateField(errors, "fieldList");
        assertSame(field, fieldList.get(0));
    }
}