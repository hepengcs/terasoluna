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
 * {@link jp.terasoluna.fw.validation.FieldChecks} �N���X�̃u���b�N�{�b�N�X�e�X�g�B
 *
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4>
 * TERASOLUNA�̓��̓`�F�b�N�@�\�ŋ��ʂɎg�p����錟�؃��[���N���X�B
 * <p>
 *
 * @see jp.terasoluna.fw.validation.FieldChecks
 */
public class FieldChecksTest05 extends TestCase {

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
        junit.swingui.TestRunner.run(FieldChecksTest05.class);
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
    public FieldChecksTest05(String name) {
        super(name);
    }

    /**
     * testValidateFloatRange01()
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
    public void testValidateFloatRange01() throws Exception {
        // �e�X�g���{
        // ����
        assertTrue(new FieldChecks().validateFloatRange(null, va, field, errors));

        // addErrors�m�F
        assertEquals(0, errors.addErrorCount);
    }

    /**
     * testValidateFloatRange02()
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
    public void testValidateFloatRange02() throws Exception {
        // �e�X�g���{
        // ����
        assertTrue(new FieldChecks().validateFloatRange("", va, field, errors));

        // addErrors�m�F
        assertEquals(0, errors.addErrorCount);
    }

    /**
     * testValidateFloatRange03()
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
     *         (��ԕω�) errors:bean,field,va�������Ƃ���addErrors���Ăяo�����B<br>
     *
     * <br>
     * ������bean��float�^�ɕϊ��ł��Ȃ��ꍇ�A�G���[��ǉ�����false���ԋp����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateFloatRange03() throws Exception {
        // �e�X�g���{
        // ����
        assertFalse(new FieldChecks().validateFloatRange("test", va, field, errors));

        // addErrors�m�F
        assertEquals(1, errors.addErrorCount);
        assertEquals("test", errors.beanList.get(0));
        assertSame(va, errors.vaList.get(0));
        assertSame(field, errors.fieldList.get(0));
    }

    /**
     * testValidateFloatRange04()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:"1.4E-45"<br>
     *         (����) va:not null<br>
     *         (����) field:var:<br>
     *                floatRangeMin=null<br>
     *         (����) errors:not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *         (��ԕω�) errors:�Ăяo����Ȃ�<br>
     *
     * <br>
     * var��floatRangeMin��null�̏ꍇ�A�͈͂̍ŏ��l��Float.MIN_VALUE�ł��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateFloatRange04() throws Exception {
        // �e�X�g���{
        // ����
        assertTrue(
                new FieldChecks().validateFloatRange("1.4E-45", va, field, errors));

        // addErrors�m�F
        assertEquals(0, errors.addErrorCount);
    }

    /**
     * testValidateFloatRange05()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:"1.4E-45"<br>
     *         (����) va:not null<br>
     *         (����) field:var:<br>
     *                floatRangeMin=""<br>
     *         (����) errors:not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *         (��ԕω�) errors:�Ăяo����Ȃ�<br>
     *
     * <br>
     * var��floatRangeMin���󕶎��̏ꍇ�A�͈͂̍ŏ��l��Float.MIN_VALUE�ł��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateFloatRange05() throws Exception {
        // �O����
        Var var = new Var();
        var.setName("floatRangeMin");
        var.setValue("");
        field.addVar(var);

        // �e�X�g���{
        // ����
        assertTrue(
                new FieldChecks().validateFloatRange("1.4E-45", va, field, errors));

        // addErrors�m�F
        assertEquals(0, errors.addErrorCount);
    }

    /**
     * testValidateFloatRange06()
     * <br><br>
     *
     * (����n) or (�ُ�n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:"3.4028235E38"<br>
     *         (����) va:not null<br>
     *         (����) field:var:<br>
     *                floatRangeMax=null<br>
     *         (����) errors:not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *         (��ԕω�) errors:�Ăяo����Ȃ�<br>
     *
     * <br>
     * var��floatRangeMax��null�̏ꍇ�A�͈͂̍ő�l��Float.MAX_VALUE�ł��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateFloatRange06() throws Exception {
        // �e�X�g���{
        // ����
        assertTrue(
                new FieldChecks().validateFloatRange(
                        "3.4028235E38", va, field, errors));

        // addErrors�m�F
        assertEquals(0, errors.addErrorCount);
    }

    /**
     * testValidateFloatRange07()
     * <br><br>
     *
     * (����n) or (�ُ�n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:"3.4028235E38"<br>
     *         (����) va:not null<br>
     *         (����) field:var:<br>
     *                floatRangeMax=""<br>
     *         (����) errors:not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *         (��ԕω�) errors:�Ăяo����Ȃ�<br>
     *
     * <br>
     * var��floatRangeMax���󕶎��̏ꍇ�A�͈͂̍ő�l��Float.MAX_VALUE�ł��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateFloatRange07() throws Exception {
        // �O����
        Var var = new Var();
        var.setName("floatRangeMax");
        var.setValue("");
        field.addVar(var);

        // �e�X�g���{
        // ����
        assertTrue(
                new FieldChecks().validateFloatRange(
                        "3.4028235E38", va, field, errors));

        // addErrors�m�F
        assertEquals(0, errors.addErrorCount);
    }

    /**
     * testValidateFloatRange08()
     * <br><br>
     *
     * (����n) or (�ُ�n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:"0.5E1"<br>
     *         (����) va:not null<br>
     *         (����) field:var:<br>
     *                floatRangeMin="test"<br>
     *         (����) errors:not null<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:ValidatorException<br>
     *                    ���b�Z�[�W�F"Mistake on validation definition file. - floatRangeMin is not number. You'll have to check it over. "<br>
     *         (��ԕω�) ���O:���O���x���F�G���[<br>
     *                    ���b�Z�[�W�F"Mistake on validation definition file. - floatRangeMin is not number. You'll have to check it over. ", new NumberFormatException()<br>
     *
     * <br>
     * var��floatRangeMin�̒l��float�^�ɕϊ��ł��Ȃ��ꍇ�AValidatorException���������邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateFloatRange08() throws Exception {
        // �O����
        Var var = new Var();
        var.setName("floatRangeMin");
        var.setValue("test");
        field.addVar(var);

        // �e�X�g���{
        // ����
        try {
            new FieldChecks().validateFloatRange("0.5E1", va, field, errors);
            fail();
        } catch (ValidatorException e) {
            String message = "Mistake on validation definition file. " +
                    "- floatRangeMin is not number. You'll have to check " +
                    "it over. ";
            assertEquals(message, e.getMessage());
            assertTrue(LogUTUtil.checkError(message, new NumberFormatException()));
        }
    }

    /**
     * testValidateFloatRange09()
     * <br><br>
     *
     * (����n) or (�ُ�n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:"0.5E1"<br>
     *         (����) va:not null<br>
     *         (����) field:var:<br>
     *                floatRangeMax="test"<br>
     *         (����) errors:not null<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:ValidatorException<br>
     *                    ���b�Z�[�W�F"Mistake on validation definition file. - floatRangeMax is not number. You'll have to check it over. "<br>
     *         (��ԕω�) ���O:���O���x���F�G���[<br>
     *                    ���b�Z�[�W�F"Mistake on validation definition file. - floatRangeMax is not number. You'll have to check it over. ", new NumberFormatException()<br>
     *
     * <br>
     * var��floatRangeMax�̒l��float�^�ɕϊ��ł��Ȃ��ꍇ�AValidatorException���������邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateFloatRange09() throws Exception {
        // �O����
        Var var = new Var();
        var.setName("floatRangeMax");
        var.setValue("test");
        field.addVar(var);

        // �e�X�g���{
        // ����
        try {
            new FieldChecks().validateFloatRange("0.5E1", va, field, errors);
            fail();
        } catch (ValidatorException e) {
            String message = "Mistake on validation definition file. " +
                    "- floatRangeMax is not number. You'll have to check " +
                    "it over. ";
            assertEquals(message, e.getMessage());
            assertTrue(LogUTUtil.checkError(message, new NumberFormatException()));
        }
    }

    /**
     * testValidateFloatRange10()
     * <br><br>
     *
     * (����n) or (�ُ�n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:"0.5E1"<br>
     *         (����) va:not null<br>
     *         (����) field:var:<br>
     *                floatRangeMin="0.1E1"<br>
     *                floatRangeMax="0.1E2"<br>
     *         (����) errors:not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *         (��ԕω�) errors:�Ăяo����Ȃ�<br>
     *
     * <br>
     * bean�̒l���AfloatRangeMin��floatRangeMax�͈͓̔��̒l�̏ꍇ�Atrue���ԋp����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateFloatRange10() throws Exception {
        // �O����
        Var var1 = new Var();
        var1.setName("floatRangeMin");
        var1.setValue("0.1E1");
        field.addVar(var1);
        
        Var var2 = new Var();
        var2.setName("floatRangeMax");
        var2.setValue("0.1E2");
        field.addVar(var2);

        // �e�X�g���{
        // ����
        assertTrue(
                new FieldChecks().validateFloatRange("0.5E1", va, field, errors));

        // addErrors�m�F
        assertEquals(0, errors.addErrorCount);

    }

    /**
     * testValidateFloatRange11()
     * <br><br>
     *
     * (����n) or (�ُ�n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:"0.5E1"<br>
     *         (����) va:not null<br>
     *         (����) field:var:<br>
     *                floatRangeMin="0.1E2"<br>
     *                floatRangeMax="0.1E3"<br>
     *         (����) errors:not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:false<br>
     *         (��ԕω�) errors:bean,field,va�������Ƃ���addErrors���Ăяo�����B<br>
     *
     * <br>
     * bean�̒l���AfloatRangeMin�̒l��菬�����ꍇ�A�G���[��ǉ�����false���ԋp����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateFloatRange11() throws Exception {
        // �O����
        Var var1 = new Var();
        var1.setName("floatRangeMin");
        var1.setValue("0.1E2");
        field.addVar(var1);
        
        Var var2 = new Var();
        var2.setName("floatRangeMax");
        var2.setValue("0.1E3");
        field.addVar(var2);

        // �e�X�g���{
        // ����
        assertFalse(
                new FieldChecks().validateFloatRange("0.5E1", va, field, errors));

        // addErrors�m�F
        assertEquals(1, errors.addErrorCount);
        assertEquals("0.5E1", errors.beanList.get(0));
        assertSame(va, errors.vaList.get(0));
        assertSame(field, errors.fieldList.get(0));
    }

    /**
     * testValidateFloatRange12()
     * <br><br>
     *
     * (����n) or (�ُ�n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:"0.5E1"<br>
     *         (����) va:not null<br>
     *         (����) field:var:<br>
     *                floatRangeMin="0.1E1"<br>
     *                floatRangeMax="0.4E1"<br>
     *         (����) errors:not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:false<br>
     *         (��ԕω�) errors:bean,field,va�������Ƃ���addErrors���Ăяo�����B<br>
     *
     * <br>
     * bean�̒l���AfloatRangeMax�̒l���傫���ꍇ�A�G���[��ǉ�����false���ԋp����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateFloatRange12() throws Exception {
        // �O����
        Var var1 = new Var();
        var1.setName("floatRangeMin");
        var1.setValue("0.1E1");
        field.addVar(var1);
        
        Var var2 = new Var();
        var2.setName("floatRangeMax");
        var2.setValue("0.4E1");
        field.addVar(var2);

        // �e�X�g���{
        // ����
        assertFalse(
                new FieldChecks().validateFloatRange("0.5E1", va, field, errors));

        // addErrors�m�F
        assertEquals(1, errors.addErrorCount);
        assertEquals("0.5E1", errors.beanList.get(0));
        assertSame(va, errors.vaList.get(0));
        assertSame(field, errors.fieldList.get(0));
    }

    /**
     * testValidateFloatRange13()
     * <br><br>
     *
     * (����n) or (�ُ�n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:"0.5E1"<br>
     *         (����) va:not null<br>
     *         (����) field:var:<br>
     *                floatRangeMin="0.5E1"<br>
     *                floatRangeMax="0.5E1"<br>
     *         (����) errors:not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *         (��ԕω�) errors:�Ăяo����Ȃ�<br>
     *
     * <br>
     * bean�̒l�ƁAfloatRangeMax,floatRangeMin�̒l�Ɠ������ꍇ�Atrue���ԋp����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateFloatRange13() throws Exception {
        // �O����
        Var var1 = new Var();
        var1.setName("floatRangeMin");
        var1.setValue("0.5E1");
        field.addVar(var1);
        
        Var var2 = new Var();
        var2.setName("floatRangeMax");
        var2.setValue("0.5E1");
        field.addVar(var2);

        // �e�X�g���{
        // ����
        assertTrue(
                new FieldChecks().validateFloatRange("0.5E1", va, field, errors));

        // addErrors�m�F
        assertEquals(0, errors.addErrorCount);
    }

    /**
     * testValidateMaxLength01()
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
    public void testValidateMaxLength01() throws Exception {
        // �e�X�g���{
        // ����
        assertTrue(new FieldChecks().validateMaxLength(null, va, field, errors));

        // addErrors�m�F
        assertEquals(0, errors.addErrorCount);
    }

    /**
     * testValidateMaxLength02()
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
     * ���Ғl�F(��ԕω�) return: true<br>
     *
     * <br>
     * var��maxlength��null�̏ꍇ�AValidatorException���������邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateMaxLength02() throws Exception {
        // �e�X�g���{
        // ����
        assertTrue(new FieldChecks().validateMaxLength("", va, field, errors));
        
        // addErrors�m�F
        assertEquals(0, errors.addErrorCount);
    }

    /**
     * testValidateMaxLength03()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FF,G
     * <br><br>
     * ���͒l�F(����) bean:"test"<br>
     *         (����) va:not null<br>
     *         (����) field:var:<br>
     *                maxlength=null<br>
     *         (����) errors:not null<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:ValidatorException<br>
     *                    ���b�Z�[�W�F"Mistake on validation definition file. - maxlength is not number. You'll have to check it over. "<br>
     *         (��ԕω�) ���O:���O���x���F�G���[<br>
     *                    ���b�Z�[�W�F"Mistake on validation definition file. - maxlength is not number. You'll have to check it over. ", new NumberFormatException()<br>
     *
     * <br>
     * var��maxlength��null�̏ꍇ�AValidatorException���������邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateMaxLength03() throws Exception {
        // �e�X�g���{
        // ����
        try {
            new FieldChecks().validateMaxLength("test", va, field, errors);
            fail();
        } catch (ValidatorException e) {
            String message = "Mistake on validation definition file. " +
                    "- maxlength is not number. You'll have to check it over. ";
            assertEquals(message, e.getMessage());
            assertTrue(LogUTUtil.checkError(message, new NumberFormatException()));
        }
    }

    /**
     * testValidateMaxLength04()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FF,G
     * <br><br>
     * ���͒l�F(����) bean:"test"<br>
     *         (����) va:not null<br>
     *         (����) field:var:<br>
     *                maxlength=""<br>
     *         (����) errors:not null<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:ValidatorException<br>
     *                    ���b�Z�[�W�F"Mistake on validation definition file. - maxlength is not number. You'll have to check it over. "<br>
     *         (��ԕω�) ���O:���O���x���F�G���[<br>
     *                    ���b�Z�[�W�F"Mistake on validation definition file. - maxlength is not number. You'll have to check it over. ", new NumberFormatException()<br>
     *
     * <br>
     * var��maxlength���󕶎��̏ꍇ�AValidatorException���������邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateMaxLength04() throws Exception {
        // �O����
        Var var = new Var();
        var.setName("maxlength");
        var.setValue("");
        field.addVar(var);

        // �e�X�g���{
        // ����
        try {
            new FieldChecks().validateMaxLength("test", va, field, errors);
            fail();
        } catch (ValidatorException e) {
            String message = "Mistake on validation definition file. " +
                    "- maxlength is not number. You'll have to check it over. ";
            assertEquals(message, e.getMessage());
            assertTrue(LogUTUtil.checkError(message, new NumberFormatException()));
        }
    }

    /**
     * testValidateMaxLength05()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FF,G
     * <br><br>
     * ���͒l�F(����) bean:"test"<br>
     *         (����) va:not null<br>
     *         (����) field:var:<br>
     *                maxlength="abc"<br>
     *         (����) errors:not null<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:ValidatorException<br>
     *                    ���b�Z�[�W�F"Mistake on validation definition file. - maxlength is not number. You'll have to check it over. "<br>
     *         (��ԕω�) ���O:���O���x���F�G���[<br>
     *                    ���b�Z�[�W�F"Mistake on validation definition file. - maxlength is not number. You'll have to check it over. ", new NumberFormatException()<br>
     *
     * <br>
     * var��maxlength�����l�ɕϊ��ł��Ȃ��ꍇ�AValidatorException���������邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateMaxLength05() throws Exception {
        // �O����
        Var var = new Var();
        var.setName("maxlength");
        var.setValue("abc");
        field.addVar(var);

        // �e�X�g���{
        // ����
        try {
            new FieldChecks().validateMaxLength("test", va, field, errors);
            fail();
        } catch (ValidatorException e) {
            String message = "Mistake on validation definition file. " +
                    "- maxlength is not number. You'll have to check it over. ";
            assertEquals(message, e.getMessage());
            assertTrue(LogUTUtil.checkError(message, new NumberFormatException()));
        }
    }

    /**
     * testValidateMaxLength06()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:"test"<br>
     *         (����) va:not null<br>
     *         (����) field:var:<br>
     *                maxlength="5"<br>
     *         (����) errors:not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *         (��ԕω�) errors:�Ăяo����Ȃ�<br>
     *
     * <br>
     * ���͂��ꂽ�����̌������Avar��maxlength�̒l��菬�����ꍇ�Atrue���擾�ł��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateMaxLength06() throws Exception {
        // �O����
        Var var = new Var();
        var.setName("maxlength");
        var.setValue("5");
        field.addVar(var);

        // �e�X�g���{
        // ����
        assertTrue(new FieldChecks().validateMaxLength("test", va, field, errors));

        // addErrors�m�F
        assertEquals(0, errors.addErrorCount);

    }

    /**
     * testValidateMaxLength07()
     * <br><br>
     *
     * (����n) or (�ُ�n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:"test"<br>
     *         (����) va:not null<br>
     *         (����) field:var:<br>
     *                maxlength="3"<br>
     *         (����) errors:not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:false<br>
     *         (��ԕω�) errors:bean,field,va�������Ƃ���addErrors���Ăяo�����B<br>
     *
     * <br>
     * ���͂��ꂽ�����̌������Avar��maxlength�̒l���傫���ꍇ�A�G���[��ǉ�����false���ԋp����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateMaxLength07() throws Exception {
        // �O����
        Var var = new Var();
        var.setName("maxlength");
        var.setValue("3");
        field.addVar(var);

        // �e�X�g���{
        // ����
        assertFalse(new FieldChecks().validateMaxLength("test", va, field, errors));

        // addErrors�m�F
        assertEquals(1, errors.addErrorCount);
        assertEquals("test", errors.beanList.get(0));
        assertSame(va, errors.vaList.get(0));
        assertSame(field, errors.fieldList.get(0));

    }

    /**
     * testValidateMaxLength08()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:"test"<br>
     *         (����) va:not null<br>
     *         (����) field:var:<br>
     *                maxlength="4"<br>
     *         (����) errors:not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *         (��ԕω�) errors:�Ăяo����Ȃ�<br>
     *
     * <br>
     * ���͂��ꂽ�����̌������Avar��maxlength�̒l�Ɠ������ꍇ�Atrue���ԋp����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateMaxLength08() throws Exception {
        // �O����
        Var var = new Var();
        var.setName("maxlength");
        var.setValue("4");
        field.addVar(var);

        // �e�X�g���{
        // ����
        assertTrue(new FieldChecks().validateMaxLength("test", va, field, errors));

        // addErrors�m�F
        assertEquals(0, errors.addErrorCount);

    }

    /**
     * testValidateMinLength01()
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
    public void testValidateMinLength01() throws Exception {
        // �e�X�g���{
        // ����
        assertTrue(new FieldChecks().validateMinLength(null, va, field, errors));

        // addErrors�m�F
        assertEquals(0, errors.addErrorCount);
    }

    /**
     * testValidateMinLength02()
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
     * ���Ғl�F(��ԕω�) return : true<br>
     *
     * <br>
     * var��minlength��null�̏ꍇ�AValidatorException���������邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateMinLength02() throws Exception {
        // �e�X�g���{
        // ����
        assertTrue(new FieldChecks().validateMinLength("", va, field, errors));
     
        // addErrors�m�F
        assertEquals(0, errors.addErrorCount);
    }

    /**
     * testValidateMinLength03()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FF,G
     * <br><br>
     * ���͒l�F(����) bean:"test"<br>
     *         (����) va:not null<br>
     *         (����) field:var:<br>
     *                minlength=null<br>
     *         (����) errors:not null<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:ValidatorException<br>
     *                    ���b�Z�[�W�F"Mistake on validation definition file.
     *                    - minlength is not number.
     *                    You'll have to check it over. "<br>
     *         (��ԕω�) ���O:���O���x���F�G���[<br>
     *                    ���b�Z�[�W�F"Mistake on validation definition file.
     *                    - minlength is not number.
     *                    You'll have to check it over. ", new NumberFormatException()<br>
     *
     * <br>
     * var��minlength��null�̏ꍇ�AValidatorException���������邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateMinLength03() throws Exception {
        // �e�X�g���{
        // ����
        try {
            new FieldChecks().validateMinLength("test", va, field, errors);
            fail();
        } catch (ValidatorException e) {
            String message = "Mistake on validation definition file. " +
                    "- minlength is not number. You'll have to check it over. ";
            assertEquals(message, e.getMessage());
            assertTrue(LogUTUtil.checkError(message, new NumberFormatException()));
        }
    }

    /**
     * testValidateMinLength04()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FF,G
     * <br><br>
     * ���͒l�F(����) bean:"test"<br>
     *         (����) va:not null<br>
     *         (����) field:var:<br>
     *                minlength=""<br>
     *         (����) errors:not null<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:ValidatorException<br>
     *                    ���b�Z�[�W�F"Mistake on validation definition file.
     *                    - minlength is not number.
     *                    You'll have to check it over. "<br>
     *         (��ԕω�) ���O:���O���x���F�G���[<br>
     *                    ���b�Z�[�W�F"Mistake on validation definition file.
     *                    - minlength is not number.
     *                    You'll have to check it over. ", new NumberFormatException()<br>
     *
     * <br>
     * var��minlength���󕶎��̏ꍇ�AValidatorException���������邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateMinLength04() throws Exception {
        // �O����
        Var var = new Var();
        var.setName("minlength");
        var.setValue("");
        field.addVar(var);

        // �e�X�g���{
        // ����
        try {
            new FieldChecks().validateMinLength("test", va, field, errors);
            fail();
        } catch (ValidatorException e) {
            String message = "Mistake on validation definition file. " +
                    "- minlength is not number. You'll have to check it over. ";
            assertEquals(message, e.getMessage());
            assertTrue(LogUTUtil.checkError(message, new NumberFormatException()));
        }
    }

    /**
     * testValidateMinLength05()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FF,G
     * <br><br>
     * ���͒l�F(����) bean:"test"<br>
     *         (����) va:not null<br>
     *         (����) field:var:<br>
     *                minlength="abc"<br>
     *         (����) errors:not null<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:ValidatorException<br>
     *                    ���b�Z�[�W�F"Mistake on validation definition file.
     *                    - minlength is not number.
     *                    You'll have to check it over. "<br>
     *         (��ԕω�) ���O:���O���x���F�G���[<br>
     *                    ���b�Z�[�W�F"Mistake on validation definition file.
     *                    - minlength is not number.
     *                    You'll have to check it over. ", new NumberFormatException()<br>
     *
     * <br>
     * var��minlength�����l�ɕϊ��ł��Ȃ��ꍇ�AValidatorException���������邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateMinLength05() throws Exception {
        // �O����
        Var var = new Var();
        var.setName("minlength");
        var.setValue("abc");
        field.addVar(var);

        // �e�X�g���{
        // ����
        try {
            new FieldChecks().validateMinLength("test", va, field, errors);
            fail();
        } catch (ValidatorException e) {
            String message = "Mistake on validation definition file. " +
                    "- minlength is not number. You'll have to check it over. ";
            assertEquals(message, e.getMessage());
            assertTrue(LogUTUtil.checkError(message, new NumberFormatException()));
        }
    }

    /**
     * testValidateMinLength06()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:"test"<br>
     *         (����) va:not null<br>
     *         (����) field:var:<br>
     *                minlength="3"<br>
     *         (����) errors:not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *         (��ԕω�) errors:�Ăяo����Ȃ�<br>
     *
     * <br>
     * ���͂��ꂽ�����̌������Avar��minlength�̒l���傫���ꍇ�A
     * true���擾�ł��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateMinLength06() throws Exception {
        // �O����
        Var var = new Var();
        var.setName("minlength");
        var.setValue("3");
        field.addVar(var);

        // �e�X�g���{
        // ����
        assertTrue(new FieldChecks().validateMinLength("test", va, field, errors));

        // addErrors�m�F
        assertEquals(0, errors.addErrorCount);

    }

    /**
     * testValidateMinLength07()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:"test"<br>
     *         (����) va:not null<br>
     *         (����) field:var:<br>
     *                minlength="5"<br>
     *         (����) errors:not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:false<br>
     *         (��ԕω�) errors:bean,field,va�������Ƃ���
     *                           addErrors���Ăяo�����B<br>
     *
     * <br>
     * ���͂��ꂽ�����̌������Avar��minlength�̒l��菬�����ꍇ�A
     * �G���[��ǉ�����false���ԋp����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateMinLength07() throws Exception {
        // �O����
        Var var = new Var();
        var.setName("minlength");
        var.setValue("5");
        field.addVar(var);

        // �e�X�g���{
        // ����
        assertFalse(new FieldChecks().validateMinLength("test", va, field, errors));

        // addErrors�m�F
        assertEquals(1, errors.addErrorCount);
        assertEquals("test", errors.beanList.get(0));
        assertSame(va, errors.vaList.get(0));
        assertSame(field, errors.fieldList.get(0));

    }

    /**
     * testValidateMinLength08()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:"test"<br>
     *         (����) va:not null<br>
     *         (����) field:var:<br>
     *                minlength="4"<br>
     *         (����) errors:not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *         (��ԕω�) errors:�Ăяo����Ȃ�<br>
     *
     * <br>
     * ���͂��ꂽ�����̌������Avar��minlength�̒l�Ɠ������ꍇ�A
     * true���ԋp����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateMinLength08() throws Exception {
        // �O����
        Var var = new Var();
        var.setName("minlength");
        var.setValue("4");
        field.addVar(var);

        // �e�X�g���{
        // ����
        assertTrue(new FieldChecks().validateMinLength("test", va, field, errors));

        // addErrors�m�F
        assertEquals(0, errors.addErrorCount);

    }

}
