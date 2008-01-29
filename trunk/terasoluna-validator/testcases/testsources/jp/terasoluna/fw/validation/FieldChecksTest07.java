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
public class FieldChecksTest07 extends TestCase {

    /**
     * ���̃e�X�g�P�[�X�����s����ׂ�
     * GUI �A�v���P�[�V�������N������B
     *
     * @param args java �R�}���h�ɐݒ肳�ꂽ�p�����[�^
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(FieldChecksTest07.class);
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
    public FieldChecksTest07(String name) {
        super(name);
    }

    /**
     * testValidateHankakuKanaString01()
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
    public void testValidateHankakuKanaString01() throws Exception {
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
            new FieldChecks().validateHankakuKanaString(bean, va, field, errors);

        // ����
        assertTrue(b);
        assertEquals(0, errors.addErrorCount);
    }

    /**
     * testValidateHankakuKanaString02()
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
    public void testValidateHankakuKanaString02() throws Exception {
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
            new FieldChecks().validateHankakuKanaString(bean, va, field, errors);

        // ����
        assertTrue(b);
        assertEquals(0, errors.addErrorCount);
    }

    /**
     * testValidateHankakuKanaString03()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) bean:"�ݶ�"<br>
     *         (����) va:not null<br>
     *         (����) field:not null<br>
     *         (����) errors:not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *         (��ԕω�) errors:�Ăяo����Ȃ�<br>
     *
     * <br>
     * ������bean�����p�J�i�����݂̂ō\������Ă���ꍇ�Atrue���ԋp����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateHankakuKanaString03() throws Exception {
        // �O����
        // bean : "�ݶ�"
        Object bean = "�ݶ�";
        // va : not null
        ValidatorAction va = new ValidatorAction();
        // field : not null
        Field field = new Field();
        // errors : not null
        FieldChecks_ValidationErrorsImpl01 errors =
            new FieldChecks_ValidationErrorsImpl01();
        
        // �e�X�g���{
        boolean b =
            new FieldChecks().validateHankakuKanaString(bean, va, field, errors);

        // ����
        assertTrue(b);
        assertEquals(0, errors.addErrorCount);
    }

    /**
     * testValidateHankakuKanaString04()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) bean:"�n���J�N"<br>
     *         (����) va:not null<br>
     *         (����) field:not null<br>
     *         (����) errors:not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:false<br>
     *         (��ԕω�) errors:bean,field,va�������Ƃ���addErrors���Ăяo�����B<br>
     *
     * <br>
     * ������bean�ɔ��p�J�i�ȊO�̕������܂܂�Ă���ꍇ�A�G���[��ǉ�����false���ԋp����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateHankakuKanaString04() throws Exception {
        // �O����
        // bean : "�n���J�N"
        Object bean = "�n���J�N";
        // va : not null
        ValidatorAction va = new ValidatorAction();
        // field : not null
        Field field = new Field();
        // errors : not null
        FieldChecks_ValidationErrorsImpl01 errors =
            new FieldChecks_ValidationErrorsImpl01();
        
        // �e�X�g���{
        boolean b =
            new FieldChecks().validateHankakuKanaString(bean, va, field, errors);

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
     * testValidateHankakuString01()
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
    public void testValidateHankakuString01() throws Exception {
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
            new FieldChecks().validateHankakuString(bean, va, field, errors);

        // ����
        assertTrue(b);
        assertEquals(0, errors.addErrorCount);
    }

    /**
     * testValidateHankakuString02()
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
    public void testValidateHankakuString02() throws Exception {
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
            new FieldChecks().validateHankakuString(bean, va, field, errors);

        // ����
        assertTrue(b);
        assertEquals(0, errors.addErrorCount);
    }

    /**
     * testValidateHankakuString03()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) bean:"1a�"<br>
     *         (����) va:not null<br>
     *         (����) field:not null<br>
     *         (����) errors:not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *         (��ԕω�) errors:�Ăяo����Ȃ�<br>
     *
     * <br>
     * ������bean�����p�����݂̂ō\������Ă���ꍇ�Atrue���ԋp����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateHankakuString03() throws Exception {
        // �O����
        // bean : "1a�"
        Object bean = "1a�";
        // va : not null
        ValidatorAction va = new ValidatorAction();
        // field : not null
        Field field = new Field();
        // errors : not null
        FieldChecks_ValidationErrorsImpl01 errors =
            new FieldChecks_ValidationErrorsImpl01();
        
        // �e�X�g���{
        boolean b =
            new FieldChecks().validateHankakuString(bean, va, field, errors);

        // ����
        assertTrue(b);
        assertEquals(0, errors.addErrorCount);
    }

    /**
     * testValidateHankakuString04()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) bean:"�S�p�A"<br>
     *         (����) va:not null<br>
     *         (����) field:not null<br>
     *         (����) errors:not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:false<br>
     *         (��ԕω�) errors:bean,field,va�������Ƃ���addErrors���Ăяo�����B<br>
     *
     * <br>
     * ������bean�ɔ��p�ȊO�̕������܂܂�Ă���ꍇ�A�G���[��ǉ�����false���ԋp����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateHankakuString04() throws Exception {
        // �O����
        // bean : "�S�p�A"
        Object bean = "�S�p�A";
        // va : not null
        ValidatorAction va = new ValidatorAction();
        // field : not null
        Field field = new Field();
        // errors : not null
        FieldChecks_ValidationErrorsImpl01 errors =
            new FieldChecks_ValidationErrorsImpl01();
        
        // �e�X�g���{
        boolean b =
            new FieldChecks().validateHankakuString(bean, va, field, errors);

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
     * testValidateZenkakuString01()
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
    public void testValidateZenkakuString01() throws Exception {
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
            new FieldChecks().validateZenkakuString(bean, va, field, errors);

        // ����
        assertTrue(b);
        assertEquals(0, errors.addErrorCount);
    }

    /**
     * testValidateZenkakuString02()
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
    public void testValidateZenkakuString02() throws Exception {
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
            new FieldChecks().validateZenkakuString(bean, va, field, errors);

        // ����
        assertTrue(b);
        assertEquals(0, errors.addErrorCount);
    }

    /**
     * testValidateZenkakuString03()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) bean:"�S�p�A"<br>
     *         (����) va:not null<br>
     *         (����) field:not null<br>
     *         (����) errors:not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *         (��ԕω�) errors:�Ăяo����Ȃ�<br>
     *
     * <br>
     * ������bean���S�p�����݂̂ō\������Ă���ꍇ�Atrue���ԋp����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateZenkakuString03() throws Exception {
        // �O����
        // bean : "�S�p�A"
        Object bean = "�S�p�A";
        // va : not null
        ValidatorAction va = new ValidatorAction();
        // field : not null
        Field field = new Field();
        // errors : not null
        FieldChecks_ValidationErrorsImpl01 errors =
            new FieldChecks_ValidationErrorsImpl01();
        
        // �e�X�g���{
        boolean b =
            new FieldChecks().validateZenkakuString(bean, va, field, errors);

        // ����
        assertTrue(b);
        assertEquals(0, errors.addErrorCount);
    }

    /**
     * testValidateZenkakuString04()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) bean:"1a�"<br>
     *         (����) va:not null<br>
     *         (����) field:not null<br>
     *         (����) errors:not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:false<br>
     *         (��ԕω�) errors:bean,field,va�������Ƃ���addErrors���Ăяo�����B<br>
     *
     * <br>
     * ������bean�ɑS�p�ȊO�̕������܂܂�Ă���ꍇ�A�G���[��ǉ�����false���ԋp����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateZenkakuString04() throws Exception {
        // �O����
        // bean : "1a�"
        Object bean = "1a�";
        // va : not null
        ValidatorAction va = new ValidatorAction();
        // field : not null
        Field field = new Field();
        // errors : not null
        FieldChecks_ValidationErrorsImpl01 errors =
            new FieldChecks_ValidationErrorsImpl01();
        
        // �e�X�g���{
        boolean b =
            new FieldChecks().validateZenkakuString(bean, va, field, errors);

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
     * testValidateZenkakuKanaString01()
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
    public void testValidateZenkakuKanaString01() throws Exception {
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
            new FieldChecks().validateZenkakuKanaString(bean, va, field, errors);

        // ����
        assertTrue(b);
        assertEquals(0, errors.addErrorCount);
    }

    /**
     * testValidateZenkakuKanaString02()
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
    public void testValidateZenkakuKanaString02() throws Exception {
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
            new FieldChecks().validateZenkakuKanaString(bean, va, field, errors);

        // ����
        assertTrue(b);
        assertEquals(0, errors.addErrorCount);
    }

    /**
     * testValidateZenkakuKanaString03()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) bean:"�[���J�N"<br>
     *         (����) va:not null<br>
     *         (����) field:not null<br>
     *         (����) errors:not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *         (��ԕω�) errors:�Ăяo����Ȃ�<br>
     *
     * <br>
     * ������bean���S�p�J�i�����݂̂ō\������Ă���ꍇ�Atrue���ԋp����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateZenkakuKanaString03() throws Exception {
        // �O����
        // bean : "�[���J�N"
        Object bean = "�[���J�N";
        // va : not null
        ValidatorAction va = new ValidatorAction();
        // field : not null
        Field field = new Field();
        // errors : not null
        FieldChecks_ValidationErrorsImpl01 errors =
            new FieldChecks_ValidationErrorsImpl01();
        
        // �e�X�g���{
        boolean b =
            new FieldChecks().validateZenkakuKanaString(bean, va, field, errors);

        // ����
        assertTrue(b);
        assertEquals(0, errors.addErrorCount);
    }

    /**
     * testValidateZenkakuKanaString04()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) bean:"1a��"<br>
     *         (����) va:not null<br>
     *         (����) field:not null<br>
     *         (����) errors:not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:false<br>
     *         (��ԕω�) errors:bean,field,va�������Ƃ���addErrors���Ăяo�����B<br>
     *
     * <br>
     * ������bean�ɑS�p�J�i�ȊO�̕������܂܂�Ă���ꍇ�A�G���[��ǉ�����false���ԋp����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateZenkakuKanaString04() throws Exception {
        // �O����
        // bean : "1a��"
        Object bean = "1a��";
        // va : not null
        ValidatorAction va = new ValidatorAction();
        // field : not null
        Field field = new Field();
        // errors : not null
        FieldChecks_ValidationErrorsImpl01 errors =
            new FieldChecks_ValidationErrorsImpl01();
        
        // �e�X�g���{
        boolean b =
            new FieldChecks().validateZenkakuKanaString(bean, va, field, errors);

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
     * testValidateProhibited01()
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
    public void testValidateProhibited01() throws Exception {
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
            new FieldChecks().validateProhibited(bean, va, field, errors);

        // ����
        assertTrue(b);
        assertEquals(0, errors.addErrorCount);
    }

    /**
     * testValidateProhibited02()
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
    public void testValidateProhibited02() throws Exception {
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
            new FieldChecks().validateProhibited(bean, va, field, errors);

        // ����
        assertTrue(b);
        assertEquals(0, errors.addErrorCount);
    }

    /**
     * testValidateProhibited03()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) bean:"test"<br>
     *         (����) va:not null<br>
     *         (����) field:var:<br>
     *                chars=null<br>
     *         (����) errors:not null<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:ValidatorException<br>
     *                    ���b�Z�[�W�F"var[chars] must be specified."<br>
     *         (��ԕω�) ���O:���O���x���F�G���[<br>
     *                    ���b�Z�[�W�F"var[chars] must be specified."<br>
     *
     * <br>
     * var��chars��null�̏ꍇ�AValidatorException���X���[����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateProhibited03() throws Exception {
        // �O����
        // bean : "test"
        Object bean = "test";
        // va : not null
        ValidatorAction va = new ValidatorAction();
        // field : not null
        Field field = new Field();
        Var var = new Var();
        var.setName("chars");
        var.setValue(null);
        field.addVar(var);
        
        // errors : not null
        FieldChecks_ValidationErrorsImpl01 errors =
            new FieldChecks_ValidationErrorsImpl01();
        
        
        try {
            // �e�X�g���{
            @SuppressWarnings("unused") boolean b =
                new FieldChecks().validateProhibited(bean, va, field, errors);
            fail();
        } catch (ValidatorException e) {
            // ����
            String message = "var[chars] must be specified.";
            assertEquals(message, e.getMessage());
            assertTrue(LogUTUtil.checkError(message));
        }
    }

    /**
     * testValidateProhibited04()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) bean:"test"<br>
     *         (����) va:not null<br>
     *         (����) field:var:<br>
     *                chars=""<br>
     *         (����) errors:not null<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:ValidatorException<br>
     *                    ���b�Z�[�W�F"var[chars] must be specified."<br>
     *         (��ԕω�) ���O:���O���x���F�G���[<br>
     *                    ���b�Z�[�W�F"var[chars] must be specified."<br>
     *
     * <br>
     * var��chars��null�̏ꍇ�AValidatorException���X���[����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateProhibited04() throws Exception {
        // �O����
        // bean : "test"
        Object bean = "test";
        // va : not null
        ValidatorAction va = new ValidatorAction();
        // field : not null
        Field field = new Field();
        Var var = new Var();
        var.setName("chars");
        var.setValue("");
        field.addVar(var);
        
        // errors : not null
        FieldChecks_ValidationErrorsImpl01 errors =
            new FieldChecks_ValidationErrorsImpl01();
        
        
        try {
            // �e�X�g���{
            @SuppressWarnings("unused") boolean b =
                new FieldChecks().validateProhibited(bean, va, field, errors);
            fail();
        } catch (ValidatorException e) {
            // ����
            String message = "var[chars] must be specified.";
            assertEquals(message, e.getMessage());
            assertTrue(LogUTUtil.checkError(message));
        }
    }

    /**
     * testValidateProhibited05()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) bean:"�B�A�@"<br>
     *         (����) va:not null<br>
     *         (����) field:var:<br>
     *                chars="���@t"<br>
     *         (����) errors:not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:false<br>
     *         (��ԕω�) errors:bean,field,va�������Ƃ���addErrors���Ăяo�����B<br>
     *
     * <br>
     * bean��var��chars�Ŏw�肳�ꂽ�������܂܂��ꍇ�A�G���[��ǉ�����false���ԋp����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateProhibited05() throws Exception {
        // �O����
        // bean : "�B�A�@"
        Object bean = "�B�A�@";
        // va : not null
        ValidatorAction va = new ValidatorAction();
        
        // field : not null
        Field field = new Field();
        Var var = new Var();
        var.setName("chars");
        var.setValue("���@t");
        field.addVar(var);
        
        // errors : not null
        FieldChecks_ValidationErrorsImpl01 errors =
            new FieldChecks_ValidationErrorsImpl01();
        
        // �e�X�g���{
        boolean b =
            new FieldChecks().validateProhibited(bean, va, field, errors);

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
     * testValidateProhibited06()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) bean:"�B�A�@"<br>
     *         (����) va:not null<br>
     *         (����) field:var:<br>
     *                chars="�C�D�E"<br>
     *         (����) errors:not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *         (��ԕω�) errors:�Ăяo����Ȃ�<br>
     *
     * <br>
     * bean��var��chars�Ŏw�肳�ꂽ�������܂܂�Ȃ��ꍇ�Atrue���ԋp����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateProhibited06() throws Exception {
        // �O����
        // bean : "�B�A�@"
        Object bean = "�B�A�@";
        // va : not null
        ValidatorAction va = new ValidatorAction();
        
        // field : not null
        Field field = new Field();
        Var var = new Var();
        var.setName("chars");
        var.setValue("�C�D�E");
        field.addVar(var);
        
        // errors : not null
        FieldChecks_ValidationErrorsImpl01 errors =
            new FieldChecks_ValidationErrorsImpl01();
        
        // �e�X�g���{
        boolean b =
            new FieldChecks().validateProhibited(bean, va, field, errors);

        // ����
        assertTrue(b);
        // �ďo�m�F
        assertEquals(0, errors.addErrorCount);
    }
}