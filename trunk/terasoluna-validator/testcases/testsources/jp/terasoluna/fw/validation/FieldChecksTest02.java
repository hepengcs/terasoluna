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
public class FieldChecksTest02 extends TestCase {

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
        junit.swingui.TestRunner.run(FieldChecksTest02.class);
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
    public FieldChecksTest02(String name) {
        super(name);
    }

    /**
     * testValidateRequired01()
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
     * ���Ғl�F(�߂�l) boolean:false<br>
     *         (��ԕω�) errors:bean,field,va�������Ƃ���
     *                           addErrors���Ăяo�����B<br>
     *
     * <br>
     * ������bean��null�̏ꍇ�A�G���[��ǉ����Afalse���ԋp����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateRequired01() throws Exception {
        // �e�X�g���{
        // ����
        assertFalse(new FieldChecks().validateRequired(null, va, field, errors));

        // addErrors�m�F
        assertEquals(1, errors.addErrorCount);
        assertNull(errors.beanList.get(0));
        assertSame(va, errors.vaList.get(0));
        assertSame(field, errors.fieldList.get(0));

    }

    /**
     * testValidateRequired02()
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
     * ���Ғl�F(�߂�l) boolean:false<br>
     *         (��ԕω�) errors:bean,field,va�������Ƃ���
     *                           addErrors���Ăяo�����B<br>
     *
     * <br>
     * ������bean���󕶎��̏ꍇ�A�G���[��ǉ����A
     * false���ԋp����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateRequired02() throws Exception {
        // �e�X�g���{
        // ����
        assertFalse(new FieldChecks().validateRequired("", va, field, errors));

        // addErrors�m�F
        assertEquals(1, errors.addErrorCount);
        assertEquals("", errors.beanList.get(0));
        assertSame(va, errors.vaList.get(0));
        assertSame(field, errors.fieldList.get(0));

    }

    /**
     * testValidateRequired03()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC,F
     * <br><br>
     * ���͒l�F(����) bean:" "<br>
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
     * ������bean�����p�̃X�y�[�X�̏ꍇ�A�G���[��ǉ����A
     * false���ԋp����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateRequired03() throws Exception {
        // �e�X�g���{
        // ����
        assertFalse(new FieldChecks().validateRequired(" ", va, field, errors));

        // addErrors�m�F
        assertEquals(1, errors.addErrorCount);
        assertEquals(" ", errors.beanList.get(0));
        assertSame(va, errors.vaList.get(0));
        assertSame(field, errors.fieldList.get(0));

    }

    /**
     * testValidateRequired04()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:"test"<br>
     *         (����) va:not null<br>
     *         (����) field:not null<br>
     *         (����) errors:not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *         (��ԕω�) errors:�Ăяo����Ȃ�<br>
     *
     * <br>
     * ������bean��null�A�󕶎��łȂ��ꍇ�Atrue���ԋp����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateRequired04() throws Exception {
        // �e�X�g���{
        // ����
        assertTrue(new FieldChecks().validateRequired("test", va, field, errors));

        // addErrors�m�F
        assertEquals(0, errors.addErrorCount);
    }

    /**
     * testValidateMask01()
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
    public void testValidateMask01() throws Exception {
        // �e�X�g���{
        // ����
        assertTrue(new FieldChecks().validateMask(null, va, field, errors));

        // addErrors�m�F
        assertEquals(0, errors.addErrorCount);
    }

    /**
     * testValidateMask02()
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
    public void testValidateMask02() throws Exception {
        // �e�X�g���{
        // ����
        assertTrue(new FieldChecks().validateMask("", va, field, errors));

        // addErrors�m�F
        assertEquals(0, errors.addErrorCount);
    }

    /**
     * testValidateMask03()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FF,G
     * <br><br>
     * ���͒l�F(����) bean:"ABC"<br>
     *         (����) va:not null<br>
     *         (����) field:var:mask=null<br>
     *         (����) errors:not null<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:ValidatorException<br>
     *                    ���b�Z�[�W�F"var[mask] must be specified."<br>
     *         (��ԕω�) ���O:���O���x���F�G���[<br>
     *                    ���b�Z�[�W�F"var[mask] must be specified."<br>
     *
     * <br>
     * field��var����mask���擾�ł��Ȃ��ꍇ�A
     * ValidatorException���X���[����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateMask03() throws Exception {
        // �e�X�g���{
        // ����
        try {
            new FieldChecks().validateMask("ABC", va, field, errors);
            fail();
        } catch(ValidatorException e) {
            assertEquals("var[mask] must be specified.", e.getMessage());
            assertTrue(LogUTUtil.checkError("var[mask] must be specified."));
        }
    }

    /**
     * testValidateMask04()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FF,G
     * <br><br>
     * ���͒l�F(����) bean:"ABC"<br>
     *         (����) va:not null<br>
     *         (����) field:var:mask=""(�󕶎�)<br>
     *         (����) errors:not null<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:ValidatorException<br>
     *                    ���b�Z�[�W�F"var[mask] must be specified."<br>
     *         (��ԕω�) ���O:���O���x���F�G���[<br>
     *                    ���b�Z�[�W�F"var[mask] must be specified."<br>
     *
     * <br>
     * field��var����mask���擾�ł��Ȃ��ꍇ�A
     * ValidatorException���X���[����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateMask04() throws Exception {
        // �O����
        Var var = new Var();
        var.setName("mask");
        var.setValue("");
        field.addVar(var);

        // �e�X�g���{
        // ����
        try {
            new FieldChecks().validateMask("ABC", va, field, errors);
            fail();
        } catch(ValidatorException e) {
            assertEquals("var[mask] must be specified.", e.getMessage());
            assertTrue(LogUTUtil.checkError("var[mask] must be specified."));
        }
    }

    /**
     * testValidateMask05()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:"ABC"<br>
     *         (����) va:not null<br>
     *         (����) field:var:mask=""^([0-9]|[a-z]|[A-Z])*$""<br>
     *         (����) errors:not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *         (��ԕω�) errors:�Ăяo����Ȃ�<br>
     *
     * <br>
     * ������bean�̒l���A�w�肳�ꂽ���K�\���ƈ�v����ꍇ�A
     * true���ԋp����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateMask05() throws Exception {
        // �O����
        Var var = new Var();
        var.setName("mask");
        var.setValue("^([0-9]|[a-z]|[A-Z])*$");
        field.addVar(var);

        // �e�X�g���{
        // ����
        assertTrue(new FieldChecks().validateMask("ABC", va, field, errors));
        assertEquals(0, errors.addErrorCount);
    }

    /**
     * testValidateMask06()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:"������"<br>
     *         (����) va:not null<br>
     *         (����) field:var:mask=""^([0-9]|[a-z]|[A-Z])*$""<br>
     *         (����) errors:not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:false<br>
     *         (��ԕω�) errors:bean,field,va�������Ƃ���
     *                           addErrors���Ăяo�����B<br>
     *
     * <br>
     * ������bean�̒l���A�w�肳�ꂽ���K�\���ƈ�v���Ȃ��ꍇ�A�G���[��ǉ����āA
     * false���ԋp����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateMask06() throws Exception {
        // �O����
        Var var = new Var();
        var.setName("mask");
        var.setValue("^([0-9]|[a-z]|[A-Z])*$");
        field.addVar(var);

        // �e�X�g���{
        // ����
        assertFalse(new FieldChecks().validateMask("������", va, field, errors));
        assertEquals(1, errors.addErrorCount);
        assertEquals("������", errors.beanList.get(0));
        assertSame(va, errors.vaList.get(0));
        assertSame(field, errors.fieldList.get(0));
    }

    /**
     * testValidateByte01()
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
    public void testValidateByte01() throws Exception {
        // �e�X�g���{
        // ����
        assertTrue(new FieldChecks().validateByte(null, va, field, errors));

        // addErrors�m�F
        assertEquals(0, errors.addErrorCount);
    }

    /**
     * testValidateByte02()
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
    public void testValidateByte02() throws Exception {
        // �e�X�g���{
        // ����
        assertTrue(new FieldChecks().validateByte("", va, field, errors));

        // addErrors�m�F
        assertEquals(0, errors.addErrorCount);
    }

    /**
     * testValidateByte03()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:"0"<br>
     *         (����) va:not null<br>
     *         (����) field:not null<br>
     *         (����) errors:not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *         (��ԕω�) errors:�Ăяo����Ȃ�<br>
     *
     * <br>
     * ������bean�̒l���Abyte�ɕϊ��ł���ꍇ�Atrue���ԋp����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateByte03() throws Exception {
        // �e�X�g���{
        // ����
        assertTrue(new FieldChecks().validateByte("0", va, field, errors));

        // addErrors�m�F
        assertEquals(0, errors.addErrorCount);
    }

    /**
     * testValidateByte04()
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
     * ������bean�̒l���Abyte�ɕϊ��ł��Ȃ��ꍇ�A�G���[��ǉ����āAfalse���ԋp����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateByte04() throws Exception {
        // �e�X�g���{
        // ����
        assertFalse(new FieldChecks().validateByte("��", va, field, errors));

        // addErrors�m�F
        assertEquals(1, errors.addErrorCount);
        assertEquals("��", errors.beanList.get(0));
        assertSame(va, errors.vaList.get(0));
        assertSame(field, errors.fieldList.get(0));
    }

    /**
     * testValidateShort01()
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
    public void testValidateShort01() throws Exception {
        // �e�X�g���{
        // ����
        assertTrue(new FieldChecks().validateShort(null, va, field, errors));

        // addErrors�m�F
        assertEquals(0, errors.addErrorCount);
    }

    /**
     * testValidateShort02()
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
    public void testValidateShort02() throws Exception {
        // �e�X�g���{
        // ����
        assertTrue(new FieldChecks().validateShort("", va, field, errors));

        // addErrors�m�F
        assertEquals(0, errors.addErrorCount);
    }

    /**
     * testValidateShort03()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:"0"<br>
     *         (����) va:not null<br>
     *         (����) field:not null<br>
     *         (����) errors:not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *         (��ԕω�) errors:�Ăяo����Ȃ�<br>
     *
     * <br>
     * ������bean�̒l���Ashort�ɕϊ��ł���ꍇ�Atrue���ԋp����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateShort03() throws Exception {
        // �e�X�g���{
        // ����
        assertTrue(new FieldChecks().validateShort("0", va, field, errors));

        // addErrors�m�F
        assertEquals(0, errors.addErrorCount);
    }

    /**
     * testValidateShort04()
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
     * ������bean�̒l���Ashort�ɕϊ��ł��Ȃ��ꍇ�A�G���[��ǉ����āAfalse���ԋp����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateShort04() throws Exception {
        // �e�X�g���{
        // ����
        assertFalse(new FieldChecks().validateShort("��", va, field, errors));

        // addErrors�m�F
        assertEquals(1, errors.addErrorCount);
        assertEquals("��", errors.beanList.get(0));
        assertSame(va, errors.vaList.get(0));
        assertSame(field, errors.fieldList.get(0));
    }

    /**
     * testValidateInteger01()
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
    public void testValidateInteger01() throws Exception {
        // �e�X�g���{
        // ����
        assertTrue(new FieldChecks().validateInteger(null, va, field, errors));

        // addErrors�m�F
        assertEquals(0, errors.addErrorCount);
    }

    /**
     * testValidateInteger02()
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
    public void testValidateInteger02() throws Exception {
        // �e�X�g���{
        // ����
        assertTrue(new FieldChecks().validateInteger("", va, field, errors));

        // addErrors�m�F
        assertEquals(0, errors.addErrorCount);
    }

    /**
     * testValidateInteger03()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:"-2147483648"<br>
     *         (����) va:not null<br>
     *         (����) field:not null<br>
     *         (����) errors:not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *         (��ԕω�) errors:�Ăяo����Ȃ�<br>
     *
     * <br>
     * ������bean�̒l���Aint�ɕϊ��ł���ꍇ�Atrue���ԋp����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateInteger03() throws Exception {
        // �e�X�g���{
        // ����
        assertTrue(
                new FieldChecks().validateInteger("-2147483648", va, field, errors));

        // addErrors�m�F
        assertEquals(0, errors.addErrorCount);
    }

    /**
     * testValidateInteger04()
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
     * ������bean�̒l���Aint�ɕϊ��ł��Ȃ��ꍇ�A�G���[��ǉ����āA
     * false���ԋp����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateInteger04() throws Exception {
        // �e�X�g���{
        // ����
        assertFalse(new FieldChecks().validateInteger("��", va, field, errors));

        // addErrors�m�F
        assertEquals(1, errors.addErrorCount);
        assertEquals("��", errors.beanList.get(0));
        assertSame(va, errors.vaList.get(0));
        assertSame(field, errors.fieldList.get(0));
    }

}
