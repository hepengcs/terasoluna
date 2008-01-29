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

import org.apache.commons.validator.Field;
import org.apache.commons.validator.ValidatorAction;
import org.apache.commons.validator.ValidatorException;
import org.apache.commons.validator.Var;
import jp.terasoluna.utlib.LogUTUtil;
import jp.terasoluna.fw.validation.FieldChecks;
import junit.framework.TestCase;

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
public class FieldChecksTest04 extends TestCase {

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
        junit.swingui.TestRunner.run(FieldChecksTest04.class);
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
    public FieldChecksTest04(String name) {
        super(name);
    }

    /**
     * testValidateIntRange01()
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
    public void testValidateIntRange01() throws Exception {
        // �e�X�g���{
        // ����
        assertTrue(new FieldChecks().validateIntRange(null, va, field, errors));

        // addErrors�m�F
        assertEquals(0, errors.addErrorCount);
    }

    /**
     * testValidateIntRange02()
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
    public void testValidateIntRange02() throws Exception {
        // �e�X�g���{
        // ����
        assertTrue(new FieldChecks().validateIntRange("", va, field, errors));

        // addErrors�m�F
        assertEquals(0, errors.addErrorCount);
    }

    /**
     * testValidateIntRange03()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:"1.5"<br>
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
     * ������bean��int�^�ɕϊ��ł��Ȃ��ꍇ�A
     * �G���[��ǉ�����false���ԋp����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateIntRange03() throws Exception {
        // �e�X�g���{
        // ����
        assertFalse(new FieldChecks().validateIntRange("1.5", va, field, errors));

        // addErrors�m�F
        assertEquals(1, errors.addErrorCount);
        assertEquals("1.5", errors.beanList.get(0));
        assertSame(va, errors.vaList.get(0));
        assertSame(field, errors.fieldList.get(0));
    }

    /**
     * testValidateIntRange04()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:"-2147483648"<br>
     *         (����) va:not null<br>
     *         (����) field:var:<br>
     *                intRangeMin=null<br>
     *         (����) errors:not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *         (��ԕω�) errors:�Ăяo����Ȃ�<br>
     *
     * <br>
     * var��intRangeMin��null�̏ꍇ�A
     * �͈͂̍ŏ��l��Integer.MIN_VALUE�ł��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateIntRange04() throws Exception {
        // �e�X�g���{
        // ����
        assertTrue(
                new FieldChecks().validateIntRange("-2147483648", va, field, errors));

        // addErrors�m�F
        assertEquals(0, errors.addErrorCount);
    }

    /**
     * testValidateIntRange05()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:"-2147483649"<br>
     *         (����) va:not null<br>
     *         (����) field:var:<br>
     *                intRangeMin=null<br>
     *         (����) errors:not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:false<br>
     *         (��ԕω�) errors:bean,field,va�������Ƃ���
     *                           addErrors���Ăяo�����B<br>
     *
     * <br>
     * var��intRangeMin��null�̏ꍇ�A
     * �͈͂̍ŏ��l��Integer.MIN_VALUE�ł��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateIntRange05() throws Exception {
        // �e�X�g���{
        // ����
        assertFalse(
                new FieldChecks().validateIntRange("-2147483649", va, field, errors));

        // addErrors�m�F
        assertEquals(1, errors.addErrorCount);
        assertEquals("-2147483649", errors.beanList.get(0));
        assertSame(va, errors.vaList.get(0));
        assertSame(field, errors.fieldList.get(0));
    }

    /**
     * testValidateIntRange06()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:"-2147483648"<br>
     *         (����) va:not null<br>
     *         (����) field:var:<br>
     *                intRangeMin=""<br>
     *         (����) errors:not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *         (��ԕω�) errors:�Ăяo����Ȃ�<br>
     *
     * <br>
     * var��intRangeMin���󕶎��̏ꍇ�A
     * �͈͂̍ŏ��l��Integer.MIN_VALUE�ł��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateIntRange06() throws Exception {
        // �O����
        Var var = new Var();
        var.setName("intRangeMin");
        var.setValue("");
        field.addVar(var);

        // �e�X�g���{
        // ����
        assertTrue(
                new FieldChecks().validateIntRange("-2147483648", va, field, errors));

        // addErrors�m�F
        assertEquals(0, errors.addErrorCount);
    }

    /**
     * testValidateIntRange07()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:"-2147483649"<br>
     *         (����) va:not null<br>
     *         (����) field:var:<br>
     *                intRangeMin=""<br>
     *         (����) errors:not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:false<br>
     *         (��ԕω�) errors:bean,field,va�������Ƃ���
     *                           addErrors���Ăяo�����B<br>
     *
     * <br>
     * var��intRangeMin���󕶎��̏ꍇ�A
     * �͈͂̍ŏ��l��Integer.MIN_VALUE�ł��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateIntRange07() throws Exception {
        // �O����
        Var var = new Var();
        var.setName("intRangeMin");
        var.setValue("");
        field.addVar(var);

        // �e�X�g���{
        // ����
        assertFalse(
                new FieldChecks().validateIntRange("-2147483649", va, field, errors));

        // addErrors�m�F
        assertEquals(1, errors.addErrorCount);
        assertEquals("-2147483649", errors.beanList.get(0));
        assertSame(va, errors.vaList.get(0));
        assertSame(field, errors.fieldList.get(0));
    }

    /**
     * testValidateIntRange08()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:"2147483647"<br>
     *         (����) va:not null<br>
     *         (����) field:var:<br>
     *                intRangeMax=null<br>
     *         (����) errors:not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *         (��ԕω�) errors:�Ăяo����Ȃ�<br>
     *
     * <br>
     * var��intRangeMax��null�̏ꍇ�A
     * �͈͂̍ő�l��Integer.MAX_VALUE�ł��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateIntRange08() throws Exception {
        // �e�X�g���{
        // ����
        assertTrue(
                new FieldChecks().validateIntRange("2147483647", va, field, errors));

        // addErrors�m�F
        assertEquals(0, errors.addErrorCount);
    }

    /**
     * testValidateIntRange09()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:"2147483648"<br>
     *         (����) va:not null<br>
     *         (����) field:var:<br>
     *                intRangeMax=null<br>
     *         (����) errors:not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:false<br>
     *         (��ԕω�) errors:bean,field,va�������Ƃ���
     *                           addErrors���Ăяo�����B<br>
     *
     * <br>
     * var��intRangeMax��null�̏ꍇ�A
     * �͈͂̍ő�l��Integer.MAX_VALUE�ł��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateIntRange09() throws Exception {
        // �e�X�g���{
        // ����
        assertFalse(
                new FieldChecks().validateIntRange("2147483648", va, field, errors));

        // addErrors�m�F
        assertEquals(1, errors.addErrorCount);
        assertEquals("2147483648", errors.beanList.get(0));
        assertSame(va, errors.vaList.get(0));
        assertSame(field, errors.fieldList.get(0));
    }

    /**
     * testValidateIntRange10()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:"2147483647"<br>
     *         (����) va:not null<br>
     *         (����) field:var:<br>
     *                intRangeMax=""<br>
     *         (����) errors:not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *         (��ԕω�) errors:�Ăяo����Ȃ�<br>
     *
     * <br>
     * var��intRangeMax���󕶎��̏ꍇ�A
     * �͈͂̍ő�l��Integer.MAX_VALUE�ł��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateIntRange10() throws Exception {
        // �O����
        Var var = new Var();
        var.setName("intRangeMin");
        var.setValue("");
        field.addVar(var);

        // �e�X�g���{
        // ����
        assertTrue(
                new FieldChecks().validateIntRange("2147483647", va, field, errors));

        // addErrors�m�F
        assertEquals(0, errors.addErrorCount);
    }

    /**
     * testValidateIntRange11()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:"2147483648"<br>
     *         (����) va:not null<br>
     *         (����) field:var:<br>
     *                intRangeMax=""<br>
     *         (����) errors:not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:false<br>
     *         (��ԕω�) errors:bean,field,va�������Ƃ���
     *                           addErrors���Ăяo�����B<br>
     *
     * <br>
     * var��intRangeMax���󕶎��̏ꍇ�A
     * �͈͂̍ő�l��Integer.MAX_VALUE�ł��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateIntRange11() throws Exception {
        // �O����
        Var var = new Var();
        var.setName("intRangeMin");
        var.setValue("");
        field.addVar(var);

        // �e�X�g���{
        // ����
        assertFalse(
                new FieldChecks().validateIntRange("2147483648", va, field, errors));

        // addErrors�m�F
        assertEquals(1, errors.addErrorCount);
        assertEquals("2147483648", errors.beanList.get(0));
        assertSame(va, errors.vaList.get(0));
        assertSame(field, errors.fieldList.get(0));
    }

    /**
     * testValidateIntRange12()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FF,G
     * <br><br>
     * ���͒l�F(����) bean:"5"<br>
     *         (����) va:not null<br>
     *         (����) field:var:<br>
     *                intRangeMin="1.5"<br>
     *         (����) errors:not null<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:ValidatorException<br>
     *                    ���b�Z�[�W�F"Mistake on validation definition file.
     *                    - intRangeMin is not number.
     *                    You'll have to check it over. "<br>
     *         (��ԕω�) ���O:���O���x���F�G���[<br>
     *                    ���b�Z�[�W�F"Mistake on validation definition file.
     *                    - intRangeMin is not number.
     *                    You'll have to check it over. ", new NumberFormatException()<br>
     *
     * <br>
     * var��intRangeMin�̒l��int�^�ɕϊ��ł��Ȃ��ꍇ�A
     * ValidatorException���������邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateIntRange12() throws Exception {
        // �O����
        Var var = new Var();
        var.setName("intRangeMin");
        var.setValue("1.5");
        field.addVar(var);

        // �e�X�g���{
        // ����
        try {
            new FieldChecks().validateIntRange("5", va, field, errors);
            fail();
        } catch (ValidatorException e) {
            String message = "Mistake on validation definition file. " +
                    "- intRangeMin is not number. You'll have to check " +
                    "it over. ";
            assertEquals(message, e.getMessage());
            assertTrue(LogUTUtil.checkError(message, new NumberFormatException()));
        }
    }

    /**
     * testValidateIntRange13()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FF,G
     * <br><br>
     * ���͒l�F(����) bean:"5"<br>
     *         (����) va:not null<br>
     *         (����) field:var:<br>
     *                intRangeMax="5.5"<br>
     *         (����) errors:not null<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:ValidatorException<br>
     *                    ���b�Z�[�W�F"Mistake on validation definition file.
     *                    - intRangeMax is not number.
     *                    You'll have to check it over. "<br>
     *         (��ԕω�) ���O:���O���x���F�G���[<br>
     *                    ���b�Z�[�W�F"Mistake on validation definition file.
     *                    - intRangeMax is not number.
     *                    You'll have to check it over. ", new NumberFormatException()<br>
     *
     * <br>
     * var��intRangeMax�̒l��int�^�ɕϊ��ł��Ȃ��ꍇ�A
     * ValidatorException���������邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateIntRange13() throws Exception {
        // �O����
        Var var = new Var();
        var.setName("intRangeMax");
        var.setValue("5.5");
        field.addVar(var);

        // �e�X�g���{
        // ����
        try {
            new FieldChecks().validateIntRange("5", va, field, errors);
            fail();
        } catch (ValidatorException e) {
            String message = "Mistake on validation definition file. " +
                    "- intRangeMax is not number. You'll have to check " +
                    "it over. ";
            assertEquals(message, e.getMessage());
            assertTrue(LogUTUtil.checkError(message, new NumberFormatException()));
        }
    }

    /**
     * testValidateIntRange14()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:"5"<br>
     *         (����) va:not null<br>
     *         (����) field:var:<br>
     *                intRangeMin="1"<br>
     *                intRangeMax="10"<br>
     *         (����) errors:not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *         (��ԕω�) errors:�Ăяo����Ȃ�<br>
     *
     * <br>
     * bean�̒l���AintRangeMin��intRangeMax�͈͓̔��̒l�̏ꍇ�A
     * true���ԋp����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateIntRange14() throws Exception {
        // �O����
        Var var1 = new Var();
        var1.setName("intRangeMin");
        var1.setValue("1");
        field.addVar(var1);
        
        Var var2 = new Var();
        var2.setName("intRangeMax");
        var2.setValue("10");
        field.addVar(var2);

        // �e�X�g���{
        // ����
        assertTrue(
                new FieldChecks().validateIntRange("5", va, field, errors));

        // addErrors�m�F
        assertEquals(0, errors.addErrorCount);

    }

    /**
     * testValidateIntRange15()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:"5"<br>
     *         (����) va:not null<br>
     *         (����) field:var:<br>
     *                intRangeMin="6"<br>
     *                intRangeMax="10"<br>
     *         (����) errors:not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:false<br>
     *         (��ԕω�) errors:bean,field,va�������Ƃ���
     *                           addErrors���Ăяo�����B<br>
     *
     * <br>
     * bean�̒l���AintRangeMin�̒l��菬�����ꍇ�A
     * �G���[��ǉ�����false���ԋp����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateIntRange15() throws Exception {
        // �O����
        Var var1 = new Var();
        var1.setName("intRangeMin");
        var1.setValue("6");
        field.addVar(var1);
        
        Var var2 = new Var();
        var2.setName("intRangeMax");
        var2.setValue("10");
        field.addVar(var2);

        // �e�X�g���{
        // ����
        assertFalse(
                new FieldChecks().validateIntRange("5", va, field, errors));

        // addErrors�m�F
        assertEquals(1, errors.addErrorCount);
        assertEquals("5", errors.beanList.get(0));
        assertSame(va, errors.vaList.get(0));
        assertSame(field, errors.fieldList.get(0));

    }

    /**
     * testValidateIntRange16()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:"5"<br>
     *         (����) va:not null<br>
     *         (����) field:var:<br>
     *                intRangeMin="1"<br>
     *                intRangeMax="4"<br>
     *         (����) errors:not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:false<br>
     *         (��ԕω�) errors:bean,field,va�������Ƃ���
     *                           addErrors���Ăяo�����B<br>
     *
     * <br>
     * bean�̒l���AintRangeMax�̒l���傫���ꍇ�A
     * �G���[��ǉ�����false���ԋp����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateIntRange16() throws Exception {
        // �O����
        Var var1 = new Var();
        var1.setName("intRangeMin");
        var1.setValue("1");
        field.addVar(var1);
        
        Var var2 = new Var();
        var2.setName("intRangeMax");
        var2.setValue("4");
        field.addVar(var2);

        // �e�X�g���{
        // ����
        assertFalse(
                new FieldChecks().validateIntRange("5", va, field, errors));

        // addErrors�m�F
        assertEquals(1, errors.addErrorCount);
        assertEquals("5", errors.beanList.get(0));
        assertSame(va, errors.vaList.get(0));
        assertSame(field, errors.fieldList.get(0));

    }

    /**
     * testValidateIntRange17()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:"5"<br>
     *         (����) va:not null<br>
     *         (����) field:var:<br>
     *                intRangeMin="5"<br>
     *                intRangeMax="5"<br>
     *         (����) errors:not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *         (��ԕω�) errors:�Ăяo����Ȃ�<br>
     *
     * <br>
     * bean�̒l�ƁAintRangeMax,intRangeMin�̒l�Ɠ������ꍇ�A
     * true���ԋp����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateIntRange17() throws Exception {
        // �O����
        Var var1 = new Var();
        var1.setName("intRangeMin");
        var1.setValue("5");
        field.addVar(var1);
        
        Var var2 = new Var();
        var2.setName("intRangeMax");
        var2.setValue("5");
        field.addVar(var2);

        // �e�X�g���{
        // ����
        assertTrue(
                new FieldChecks().validateIntRange("5", va, field, errors));

        // addErrors�m�F
        assertEquals(0, errors.addErrorCount);

    }

    /**
     * testValidateDoubleRange01()
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
    public void testValidateDoubleRange01() throws Exception {
        // �e�X�g���{
        // ����
        assertTrue(new FieldChecks().validateDoubleRange(null, va, field, errors));

        // addErrors�m�F
        assertEquals(0, errors.addErrorCount);
    }

    /**
     * testValidateDoubleRange02()
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
    public void testValidateDoubleRange02() throws Exception {
        // �e�X�g���{
        // ����
        assertTrue(new FieldChecks().validateDoubleRange("", va, field, errors));

        // addErrors�m�F
        assertEquals(0, errors.addErrorCount);
    }

    /**
     * testValidateDoubleRange03()
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
     * ���Ғl�F(�߂�l) boolean:false<br>
     *         (��ԕω�) errors:bean,field,va�������Ƃ���
     *                           addErrors���Ăяo�����B<br>
     *
     * <br>
     * ������bean��double�^�ɕϊ��ł��Ȃ��ꍇ�A
     * �G���[��ǉ�����false���ԋp����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateDoubleRange03() throws Exception {
        // �e�X�g���{
        // ����
        assertFalse(new FieldChecks().validateDoubleRange("test", va, field, errors));

        // addErrors�m�F
        assertEquals(1, errors.addErrorCount);
        assertEquals("test", errors.beanList.get(0));
        assertSame(va, errors.vaList.get(0));
        assertSame(field, errors.fieldList.get(0));
    }

    /**
     * testValidateDoubleRange04()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:"4.9E-324"<br>
     *         (����) va:not null<br>
     *         (����) field:var:<br>
     *                doubleRangeMin=null<br>
     *         (����) errors:not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *         (��ԕω�) errors:�Ăяo����Ȃ�<br>
     *
     * <br>
     * var��doubleRangeMin��null�̏ꍇ�A
     * �͈͂̍ŏ��l��Double.MIN_VALUE�ł��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateDoubleRange04() throws Exception {
        // �e�X�g���{
        // ����
        assertTrue(
                new FieldChecks().validateDoubleRange("4.9E-324", va, field, errors));

        // addErrors�m�F
        assertEquals(0, errors.addErrorCount);
    }

    /**
     * testValidateDoubleRange05()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:"4.9E-324"<br>
     *         (����) va:not null<br>
     *         (����) field:var:<br>
     *                doubleRangeMin=""<br>
     *         (����) errors:not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *         (��ԕω�) errors:�Ăяo����Ȃ�<br>
     *
     * <br>
     * var��doubleRangeMin���󕶎��̏ꍇ�A
     * �͈͂̍ŏ��l��Double.MIN_VALUE�ł��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateDoubleRange05() throws Exception {
        // �O����
        Var var = new Var();
        var.setName("doubleRangeMin");
        var.setValue("");
        field.addVar(var);

        // �e�X�g���{
        // ����
        assertTrue(
                new FieldChecks().validateDoubleRange("4.9E-324", va, field, errors));

        // addErrors�m�F
        assertEquals(0, errors.addErrorCount);
    }

    /**
     * testValidateDoubleRange06()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:"1.7976931348623157E308"<br>
     *         (����) va:not null<br>
     *         (����) field:var:<br>
     *                doubleRangeMax=null<br>
     *         (����) errors:not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *         (��ԕω�) errors:�Ăяo����Ȃ�<br>
     *
     * <br>
     * var��doubleRangeMax��null�̏ꍇ�A�͈͂̍ő�l��Double.MAX_VALUE�ł��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateDoubleRange06() throws Exception {
        // �e�X�g���{
        // ����
        assertTrue(
                new FieldChecks().validateDoubleRange(
                        "1.7976931348623157E308", va, field, errors));

        // addErrors�m�F
        assertEquals(0, errors.addErrorCount);
    }

    /**
     * testValidateDoubleRange07()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:"1.7976931348623157E308"<br>
     *         (����) va:not null<br>
     *         (����) field:var:<br>
     *                doubleRangeMax=""<br>
     *         (����) errors:not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *         (��ԕω�) errors:�Ăяo����Ȃ�<br>
     *
     * <br>
     * var��doubleRangeMax���󕶎��̏ꍇ�A�͈͂̍ő�l��Double.MAX_VALUE�ł��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateDoubleRange07() throws Exception {
        // �O����
        Var var = new Var();
        var.setName("doubleRangeMax");
        var.setValue("");
        field.addVar(var);

        // �e�X�g���{
        // ����
        assertTrue(
                new FieldChecks().validateDoubleRange(
                        "1.7976931348623157E308", va, field, errors));

        // addErrors�m�F
        assertEquals(0, errors.addErrorCount);
    }

    /**
     * testValidateDoubleRange08()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:"0.5E1"<br>
     *         (����) va:not null<br>
     *         (����) field:var:<br>
     *                doubleRangeMin="test"<br>
     *         (����) errors:not null<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:ValidatorException<br>
     *                    ���b�Z�[�W�F"Mistake on validation definition file.
     *                    - doubleRangeMin is not number.
     *                    You'll have to check it over. "<br>
     *         (��ԕω�) ���O:���O���x���F�G���[<br>
     *                    ���b�Z�[�W�F"Mistake on validation definition file.
     *                    - doubleRangeMin is not number.
     *                    You'll have to check it over. ", new NumberFormatException()<br>
     *
     * <br>
     * var��doubleRangeMin�̒l��double�^�ɕϊ��ł��Ȃ��ꍇ�A
     * ValidatorException���������邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateDoubleRange08() throws Exception {
        // �O����
        Var var = new Var();
        var.setName("doubleRangeMin");
        var.setValue("test");
        field.addVar(var);

        // �e�X�g���{
        // ����
        try {
            new FieldChecks().validateDoubleRange("0.5E1", va, field, errors);
            fail();
        } catch (ValidatorException e) {
            String message = "Mistake on validation definition file. " +
                    "- doubleRangeMin is not number. You'll have to check " +
                    "it over. ";
            assertEquals(message, e.getMessage());
            assertTrue(LogUTUtil.checkError(message, new NumberFormatException()));
        }
    }

    /**
     * testValidateDoubleRange09()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:"0.5E1"<br>
     *         (����) va:not null<br>
     *         (����) field:var:<br>
     *                doubleRangeMax="test"<br>
     *         (����) errors:not null<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:ValidatorException<br>
     *                    ���b�Z�[�W�F"Mistake on validation definition file.
     *                    - doubleRangeMax is not number.
     *                    You'll have to check it over. "<br>
     *         (��ԕω�) ���O:���O���x���F�G���[<br>
     *                    ���b�Z�[�W�F"Mistake on validation definition file.
     *                    - doubleRangeMax is not number.
     *                    You'll have to check it over. ", new NumberFormatException()<br>
     *
     * <br>
     * var��doubleRangeMax�̒l��double�^�ɕϊ��ł��Ȃ��ꍇ�A
     * ValidatorException���������邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateDoubleRange09() throws Exception {
        // �O����
        Var var = new Var();
        var.setName("doubleRangeMax");
        var.setValue("test");
        field.addVar(var);

        // �e�X�g���{
        // ����
        try {
            new FieldChecks().validateDoubleRange("0.5E1", va, field, errors);
            fail();
        } catch (ValidatorException e) {
            String message = "Mistake on validation definition file. " +
                    "- doubleRangeMax is not number. You'll have to check " +
                    "it over. ";
            assertEquals(message, e.getMessage());
            assertTrue(LogUTUtil.checkError(message, new NumberFormatException()));
        }
    }

    /**
     * testValidateDoubleRange10()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:"0.5E1"<br>
     *         (����) va:not null<br>
     *         (����) field:var:<br>
     *                doubleRangeMin="0.1E1"<br>
     *                doubleRangeMax="0.1E2"<br>
     *         (����) errors:not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *         (��ԕω�) errors:�Ăяo����Ȃ�<br>
     *
     * <br>
     * bean�̒l���AdoubleRangeMin��doubleRangeMax�͈͓̔��̒l�̏ꍇ�A
     * true���ԋp����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateDoubleRange10() throws Exception {
        // �O����
        Var var1 = new Var();
        var1.setName("doubleRangeMin");
        var1.setValue("0.1E1");
        field.addVar(var1);
        
        Var var2 = new Var();
        var2.setName("doubleRangeMax");
        var2.setValue("0.1E2");
        field.addVar(var2);

        // �e�X�g���{
        // ����
        assertTrue(
                new FieldChecks().validateDoubleRange("0.5E1", va, field, errors));

        // addErrors�m�F
        assertEquals(0, errors.addErrorCount);

    }

    /**
     * testValidateDoubleRange11()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:"0.5E1"<br>
     *         (����) va:not null<br>
     *         (����) field:var:<br>
     *                doubleRangeMin="0.1E2"<br>
     *                doubleRangeMax="0.1E3"<br>
     *         (����) errors:not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:false<br>
     *         (��ԕω�) errors:bean,field,va�������Ƃ���addErrors���Ăяo�����B<br>
     *
     * <br>
     * bean�̒l���AdoubleRangeMin�̒l��菬�����ꍇ�A�G���[��ǉ�����false���ԋp����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateDoubleRange11() throws Exception {
        // �O����
        Var var1 = new Var();
        var1.setName("doubleRangeMin");
        var1.setValue("0.1E2");
        field.addVar(var1);
        
        Var var2 = new Var();
        var2.setName("doubleRangeMax");
        var2.setValue("0.1E3");
        field.addVar(var2);

        // �e�X�g���{
        // ����
        assertFalse(
                new FieldChecks().validateDoubleRange("0.5E1", va, field, errors));

        // addErrors�m�F
        assertEquals(1, errors.addErrorCount);
        assertEquals("0.5E1", errors.beanList.get(0));
        assertSame(va, errors.vaList.get(0));
        assertSame(field, errors.fieldList.get(0));
    }

    /**
     * testValidateDoubleRange12()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:"0.5E1"<br>
     *         (����) va:not null<br>
     *         (����) field:var:<br>
     *                doubleRangeMin="0.1E1"<br>
     *                doubleRangeMax="0.4E1"<br>
     *         (����) errors:not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:false<br>
     *         (��ԕω�) errors:bean,field,va�������Ƃ���addErrors���Ăяo�����B<br>
     *
     * <br>
     * bean�̒l���AdoubleRangeMax�̒l���傫���ꍇ�A�G���[��ǉ�����false���ԋp����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateDoubleRange12() throws Exception {
        // �O����
        Var var1 = new Var();
        var1.setName("doubleRangeMin");
        var1.setValue("0.1E1");
        field.addVar(var1);
        
        Var var2 = new Var();
        var2.setName("doubleRangeMax");
        var2.setValue("0.4E1");
        field.addVar(var2);

        // �e�X�g���{
        // ����
        assertFalse(
                new FieldChecks().validateDoubleRange("0.5E1", va, field, errors));

        // addErrors�m�F
        assertEquals(1, errors.addErrorCount);
        assertEquals("0.5E1", errors.beanList.get(0));
        assertSame(va, errors.vaList.get(0));
        assertSame(field, errors.fieldList.get(0));
    }

    /**
     * testValidateDoubleRange13()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:"0.5E1"<br>
     *         (����) va:not null<br>
     *         (����) field:var:<br>
     *                doubleRangeMin="0.5E1"<br>
     *                doubleRangeMax="0.5E1"<br>
     *         (����) errors:not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *         (��ԕω�) errors:�Ăяo����Ȃ�<br>
     *
     * <br>
     * bean�̒l�ƁAdoubleRangeMax,doubleRangeMin�̒l�Ɠ������ꍇ�Atrue���ԋp����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateDoubleRange13() throws Exception {
        // �O����
        Var var1 = new Var();
        var1.setName("doubleRangeMin");
        var1.setValue("0.5E1");
        field.addVar(var1);
        
        Var var2 = new Var();
        var2.setName("doubleRangeMax");
        var2.setValue("0.5E1");
        field.addVar(var2);

        // �e�X�g���{
        // ����
        assertTrue(
                new FieldChecks().validateDoubleRange("0.5E1", va, field, errors));

        // addErrors�m�F
        assertEquals(0, errors.addErrorCount);
    }

}
