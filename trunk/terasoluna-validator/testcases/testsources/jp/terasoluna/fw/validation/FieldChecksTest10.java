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
import java.util.List;
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
public class FieldChecksTest10 extends TestCase {

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
        junit.swingui.TestRunner.run(FieldChecksTest10.class);
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
    public FieldChecksTest10(String name) {
        super(name);
    }

    /**
     * testValidateDateRange01()
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
    public void testValidateDateRange01() throws Exception {
        // �e�X�g���{
        // ����
        assertTrue(new FieldChecks().validateDateRange(null, va, field, errors));

        // addErrors�m�F
        assertEquals(0, errors.addErrorCount);
    }

    /**
     * testValidateDateRange02()
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
    public void testValidateDateRange02() throws Exception {
        // �e�X�g���{
        // ����
        assertTrue(new FieldChecks().validateDateRange("", va, field, errors));

        // addErrors�m�F
        assertEquals(0, errors.addErrorCount);
    }

    /**
     * testValidateDateRange03()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FF,G
     * <br><br>
     * ���͒l�F(����) bean:"2005/11/22"<br>
     *         (����) va:not null<br>
     *         (����) field:var<br>
     *                datePattern="abc"<br>
     *                datePatternStrict="yyyy.MM.dd"<br>
     *         (����) errors:not null<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:ValidatorException<br>
     *                    ���b�Z�[�W�FIllegal pattern character 'b'<br>
     *         (��ԕω�) ���O:���O���x���F�G���[<br>
     *                    ���b�Z�[�W�FIllegal pattern character 'b'<br>
     *
     * <br>
     * var��datePattern�ɕs���ȃp�^�[���������܂܂��ꍇ�A
     * ValidatorException���������邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateDateRange03() throws Exception {
        // �O����
        Var var1 = new Var();
        var1.setName("datePattern");
        var1.setValue("abc");
        field.addVar(var1);
        
        Var var2 = new Var();
        var2.setName("datePatternStrict");
        var2.setValue("yyyy.MM.dd");
        field.addVar(var2);
        
        // �e�X�g���{
        // ����
        try {
            new FieldChecks().validateDateRange("2005/11/22", va, field, errors);
            fail();
        } catch(ValidatorException e) {
            assertEquals("Illegal pattern character 'b'", e.getMessage());
            assertTrue(LogUTUtil.checkError("Illegal pattern character 'b'"));
        }
    }

    /**
     * testValidateDateRange04()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FF,G
     * <br><br>
     * ���͒l�F(����) bean:"2005/11/22"<br>
     *         (����) va:not null<br>
     *         (����) field:var<br>
     *                datePatternStrict="abc"<br>
     *         (����) errors:not null<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:ValidatorException<br>
     *                    ���b�Z�[�W�FIllegal pattern character 'b'<br>
     *         (��ԕω�) ���O:���O���x���F�G���[<br>
     *                    ���b�Z�[�W�FIllegal pattern character 'b'<br>
     *
     * <br>
     * var��datePatternStrict�ɕs���ȃp�^�[���������܂܂��ꍇ�A
     * ValidatorException���������邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateDateRange04() throws Exception {
        // �O����
        Var var = new Var();
        var.setName("datePatternStrict");
        var.setValue("abc");
        field.addVar(var);
        
        // �e�X�g���{
        // ����
        try {
            new FieldChecks().validateDateRange("2005/11/22", va, field, errors);
            fail();
        } catch(ValidatorException e) {
            assertEquals("Illegal pattern character 'b'", e.getMessage());
            assertTrue(LogUTUtil.checkError("Illegal pattern character 'b'"));
        }
    }

    /**
     * testValidateDateRange05()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FF,G
     * <br><br>
     * ���͒l�F(����) bean:"2005/11/22"<br>
     *         (����) va:not null<br>
     *         (����) field:var:<br>
     *                datePattern=null<br>
     *                datePatternStrict=null<br>
     *         (����) errors:not null<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:ValidatorException<br>
     *                    ���b�Z�[�W�FdatePattern or datePatternStrict must be specified.<br>
     *         (��ԕω�) ���O:���O���x���F�G���[<br>
     *                    ���b�Z�[�W�FdatePattern or datePatternStrict must be specified.<br>
     *
     * <br>
     * var��datePattern�AdatePatternStrict��null�̏ꍇ�A
     * ValidatorException���������邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateDateRange05() throws Exception {
        // �O����
        Var var1 = new Var();
        var1.setName("datePattern");
        var1.setValue(null);
        field.addVar(var1);
        
        Var var2 = new Var();
        var2.setName("datePatternStrict");
        var2.setValue(null);
        field.addVar(var2);
        
        // �e�X�g���{
        // ����
        try {
            new FieldChecks().validateDateRange("2005/11/22", va, field, errors);
            fail();
        } catch(ValidatorException e) {
            assertEquals("datePattern or datePatternStrict must be specified.", e.getMessage());
            assertTrue(LogUTUtil.checkError("datePattern or datePatternStrict must be specified."));
        }
    }

    /**
     * testValidateDateRange06()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FF,G
     * <br><br>
     * ���͒l�F(����) bean:"2005/11/22"<br>
     *         (����) va:not null<br>
     *         (����) field:var:<br>
     *                datePattern=""<br>
     *                datePatternStrict=""<br>
     *         (����) errors:not null<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:ValidatorException<br>
     *                    ���b�Z�[�W�FdatePattern or datePatternStrict must be specified.<br>
     *         (��ԕω�) ���O:���O���x���F�G���[<br>
     *                    ���b�Z�[�W�FdatePattern or datePatternStrict must be specified.<br>
     *
     * <br>
     * var��datePattern�AdatePatternStrict���󕶎��̏ꍇ�AValidatorException���������邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateDateRange06() throws Exception {
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
            new FieldChecks().validateDateRange("2005/11/22", va, field, errors);
            fail();
        } catch(ValidatorException e) {
            assertEquals("datePattern or datePatternStrict must be specified.", e.getMessage());
            assertTrue(LogUTUtil.checkError("datePattern or datePatternStrict must be specified."));
        }
    }

    /**
     * testValidateDateRange07()
     * <br><br>
     *
     * (����n) 
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:"test"<br>
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
     * bean�����t�ɕϊ��ł��Ȃ��ꍇ�A�G���[��ǉ�����false���ԋp����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateDateRange07() throws Exception {
        // �O����
        Var var = new Var();
        var.setName("datePattern");
        var.setValue("yyyy/MM/dd");
        field.addVar(var);
        
        // �e�X�g���{
        // ����
        assertFalse(new FieldChecks().validateDateRange("test", va, field, errors));
        assertEquals(1, errors.addErrorCount);
        assertEquals("test", errors.beanList.get(0));
        assertSame(va, errors.vaList.get(0));
        assertSame(field, errors.fieldList.get(0));
    }

    /**
     * testValidateDateRange08()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FF,G
     * <br><br>
     * ���͒l�F(����) bean:"2005/11/22"<br>
     *         (����) va:not null<br>
     *         (����) field:var:<br>
     *                datePattern="yyyy/MM/dd"<br>
     *                startDate="test"<br>
     *         (����) errors:not null<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:ValidatorException<br>
     *                    ���b�Z�[�W�FstartDate is unparseable[test]<br>
     *         (��ԕω�) ���O:���O���x���F�G���[<br>
     *                    ���b�Z�[�W�FstartDate is unparseable[test]<br>
     *
     * <br>
     * startDate�����t�ɕϊ��ł��Ȃ��ꍇ�AValidatorException���������邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateDateRange08() throws Exception {
        // �O����
        Var var1 = new Var();
        var1.setName("datePattern");
        var1.setValue("yyyy/MM/dd");
        field.addVar(var1);
        
        Var var2 = new Var();
        var2.setName("startDate");
        var2.setValue("test");
        field.addVar(var2);
        
        // �e�X�g���{
        // ����
        try {
            new FieldChecks().validateDateRange("2005/11/22", va, field, errors);
            fail();
        } catch(ValidatorException e) {
            assertEquals("startDate is unparseable[test]", e.getMessage());
            assertTrue(LogUTUtil.checkError("startDate is unparseable[test]"));
        }
    }

    /**
     * testValidateDateRange09()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FF,G
     * <br><br>
     * ���͒l�F(����) bean:"2005/11/22"<br>
     *         (����) va:not null<br>
     *         (����) field:var:<br>
     *                datePattern="yyyy/MM/dd"<br>
     *                endDate="test"<br>
     *         (����) errors:not null<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:ValidatorException<br>
     *                    ���b�Z�[�W�FendDate is unparseable[test]<br>
     *         (��ԕω�) ���O:���O���x���F�G���[<br>
     *                    ���b�Z�[�W�FendDate is unparseable[test]<br>
     *
     * <br>
     * endDate�����t�ɕϊ��ł��Ȃ��ꍇ�AValidatorException���������邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateDateRange09() throws Exception {
        // �O����
        Var var1 = new Var();
        var1.setName("datePattern");
        var1.setValue("yyyy/MM/dd");
        field.addVar(var1);
        
        Var var2 = new Var();
        var2.setName("endDate");
        var2.setValue("test");
        field.addVar(var2);
        
        // �e�X�g���{
        // ����
        try {
            new FieldChecks().validateDateRange("2005/11/22", va, field, errors);
            fail();
        } catch(ValidatorException e) {
            assertEquals("endDate is unparseable[test]", e.getMessage());
            assertTrue(LogUTUtil.checkError("endDate is unparseable[test]"));
        }
    }

    /**
     * testValidateDateRange10()
     * <br><br>
     *
     * (����n) 
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:"2005/11/22"<br>
     *         (����) va:not null<br>
     *         (����) field:var:<br>
     *                datePattern="yyyy/MM/dd"<br>
     *                startDate="2005/11/23"<br>
     *                endDate="2005/12/31"<br>
     *         (����) errors:not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:false<br>
     *         (��ԕω�) errors:bean,field,va�������Ƃ���
     *         addErrors���Ăяo�����B<br>
     *
     * <br>
     * bean�̓��t��startDate�̓��t�ȑO�̏ꍇ�A
     * �G���[��ǉ�����false���ԋp����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateDateRange10() throws Exception {
        // �O����
        Var var1 = new Var();
        var1.setName("datePattern");
        var1.setValue("yyyy/MM/dd");
        field.addVar(var1);
        
        Var var2 = new Var();
        var2.setName("startDate");
        var2.setValue("2005/11/23");
        field.addVar(var2);
        
        Var var3 = new Var();
        var3.setName("endDate");
        var3.setValue("2005/12/31");
        field.addVar(var3);
        
        // �e�X�g���{
        // ����
        assertFalse(new FieldChecks().validateDateRange("2005/11/22", va, field, errors));
        assertEquals(1, errors.addErrorCount);
        assertEquals("2005/11/22", errors.beanList.get(0));
        assertSame(va, errors.vaList.get(0));
        assertSame(field, errors.fieldList.get(0));
    }

    /**
     * testValidateDateRange11()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:"2005/11/22"<br>
     *         (����) va:not null<br>
     *         (����) field:var:<br>
     *                datePattern="yyyy/MM/dd"<br>
     *                startDate="2005/1/1"<br>
     *                endDate="2005/11/21"<br>
     *         (����) errors:not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:false<br>
     *         (��ԕω�) errors:bean,field,va�������Ƃ���addErrors���Ăяo�����B<br>
     *
     * <br>
     * bean�̓��t��endDate�̓��t�ȍ~�̏ꍇ�A�G���[��ǉ�����false���ԋp����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateDateRange11() throws Exception {
        // �O����
        Var var1 = new Var();
        var1.setName("datePattern");
        var1.setValue("yyyy/MM/dd");
        field.addVar(var1);
        
        Var var2 = new Var();
        var2.setName("startDate");
        var2.setValue("2005/1/1");
        field.addVar(var2);
        
        Var var3 = new Var();
        var3.setName("endDate");
        var3.setValue("2005/11/21");
        field.addVar(var3);
        
        // �e�X�g���{
        // ����
        assertFalse(new FieldChecks().validateDateRange("2005/11/22", va, field, errors));
        assertEquals(1, errors.addErrorCount);
        assertEquals("2005/11/22", errors.beanList.get(0));
        assertSame(va, errors.vaList.get(0));
        assertSame(field, errors.fieldList.get(0));
    }

    /**
     * testValidateDateRange12()
     * <br><br>
     *
     * (����n) 
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:"2005/11/22"<br>
     *         (����) va:not null<br>
     *         (����) field:var:<br>
     *                datePattern="yyyy/MM/dd"<br>
     *                startDate="2005/11/22"<br>
     *                endDate="2005/11/22"<br>
     *         (����) errors:not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *         (��ԕω�) errors:�Ăяo����Ȃ�<br>
     *
     * <br>
     * bean�̓��t��startDate,endDate�̓��t���������ꍇ�Atrue���ԋp����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateDateRange12() throws Exception {
        // �O����
        Var var1 = new Var();
        var1.setName("datePattern");
        var1.setValue("yyyy/MM/dd");
        field.addVar(var1);
        
        Var var2 = new Var();
        var2.setName("startDate");
        var2.setValue("2005/11/22");
        field.addVar(var2);
        
        Var var3 = new Var();
        var3.setName("endDate");
        var3.setValue("2005/11/22");
        field.addVar(var3);
        
        // �e�X�g���{
        // ����
        assertTrue(new FieldChecks().validateDateRange("2005/11/22", va, field, errors));
        assertEquals(0, errors.addErrorCount);
    }

    /**
     * testValidateDateRange13()
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
     *                startDate="2005/01/01"<br>
     *                endDate="2005/01/01"<br>
     *         (����) errors:not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:false<br>
     *         (��ԕω�) errors:bean,field,va�������Ƃ���addErrors���Ăяo�����B<br>
     *
     * <br>
     * bean�̓��t��datePatternStrict�̃p�^�[���̕��������������Ȃ��ꍇ�A
     * �G���[��ǉ�����false���ԋp����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateDateRange13() throws Exception {
        // �O����
        Var var1 = new Var();
        var1.setName("datePatternStrict");
        var1.setValue("yyyy/MM/dd");
        field.addVar(var1);
        
        Var var2 = new Var();
        var2.setName("startDate");
        var2.setValue("2005/01/01");
        field.addVar(var2);
        
        Var var3 = new Var();
        var3.setName("endDate");
        var3.setValue("2005/01/01");
        field.addVar(var3);
        
        // �e�X�g���{
        // ����
        assertFalse(new FieldChecks().validateDateRange("2005/1/1", va, field, errors));
        assertEquals(1, errors.addErrorCount);
        assertEquals("2005/1/1", errors.beanList.get(0));
        assertSame(va, errors.vaList.get(0));
        assertSame(field, errors.fieldList.get(0));
    }

    /**
     * testValidateArraysIndex01()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FF,G
     * <br><br>
     * ���͒l�F(����) bean:null<br>
     *         (����) va:not null<br>
     *         (����) field:not null<br>
     *         (����) errors:not null<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:ValidatorException<br>
     *                    ���b�Z�[�W�F"validation target bean is null."<br>
     *         (��ԕω�) ���O:���O���x���F�G���[<br>
     *                    ���b�Z�[�W�F"validation target bean is null."<br>
     *
     * <br>
     * ������bean��null�̏ꍇ�AValidatorException���X���[����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateArraysIndex01() throws Exception {
        // �e�X�g���{
        // ����
        try {
            new FieldChecks().validateArraysIndex(null, va, field, errors);
        } catch (ValidatorException e) {
            assertEquals("validation target bean is null.", e.getMessage());
            assertTrue(LogUTUtil.checkError("validation target bean is null."));
        }
    }

    /**
     * testValidateArraysIndex02()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FF,G
     * <br><br>
     * ���͒l�F(����) bean:not null<br>
     *         (����) va:mehodParams=""<br>
     *         (����) field:not null<br>
     *         (����) errors:not null<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:ValidatorException<br>
     *                    ���b�Z�[�W�F"Mistake on validation rule file. 
     *                    - Can not get argument class. 
     *                    You'll have to check it over. "<br>
     *         (��ԕω�) ���O:���O���x���F�G���[<br>
     *                    ���b�Z�[�W�F"Mistake on validation rule file. 
     *                    - Can not get argument class. 
     *                    You'll have to check it over. "<br>
     *
     * <br>
     * va����methodParams���擾�ł��Ȃ��ꍇ�A
     * ValidatorException���������邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateArraysIndex02() throws Exception {
        // �O����
        va.setMethodParams("");
        
        // �e�X�g���{
        // ����
        try {
            new FieldChecks().validateArraysIndex(new Object(), va, field, errors);
        } catch (ValidatorException e) {
            String expect ="Mistake on validation rule file. "
                    + "- Can not get argument class. You'll have to check it over. ";
            assertEquals(expect, e.getMessage());
            assertTrue(LogUTUtil.checkError(expect));
        }
    }

    /**
     * testValidateArraysIndex03()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FF,G
     * <br><br>
     * ���͒l�F(����) bean:not null<br>
     *         (����) va:mehodParams="aaaaa"<br>
     *         (����) field:not null<br>
     *         (����) errors:not null<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:ValidatorException<br>
     *                    ���b�Z�[�W�F"Mistake on validation rule file. 
     *                    - Can not get argument class. 
     *                    You'll have to check it over. "<br>
     *         (��ԕω�) ���O:���O���x���F�G���[<br>
     *                    ���b�Z�[�W�F"Mistake on validation rule file. 
     *                    - Can not get argument class. 
     *                    You'll have to check it over. "<br>
     *
     * <br>
     * va����methodParams���擾�ł��Ȃ��ꍇ�AValidatorException���������邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateArraysIndex03() throws Exception {
        // �O����
        va.setMethodParams("aaaaa");
        
        // �e�X�g���{
        // ����
        try {
            new FieldChecks().validateArraysIndex(new Object(), va, field, errors);
        } catch (ValidatorException e) {
            String expect ="Mistake on validation rule file. "
                    + "- Can not get argument class. You'll have to check it over. ";
            assertEquals(expect, e.getMessage());
            assertTrue(LogUTUtil.checkError(expect));
        }
    }

    /**
     * testValidateArraysIndex04()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FF,G
     * <br><br>
     * ���͒l�F(����) bean:not null<br>
     *         (����) va:mehodParams="java.lang.String"<br>
     *                name="hoge"<br>
     *         (����) field:not null<br>
     *         (����) errors:not null<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:ValidatorException<br>
     *                    ���b�Z�[�W�F"Mistake on validation rule file. 
     *                    - Can not get validateMethod. You'll have to check it over. "<br>
     *         (��ԕω�) ���O:���O���x���F�G���[<br>
     *                    ���b�Z�[�W�F"Mistake on validation rule file. 
     *                    - Can not get validateMethod. You'll have to check it over. "<br>
     *
     * <br>
     * va�ɐݒ肳�ꂽname�̃��\�b�h�����݂��Ȃ��ꍇ�AValidatorException���������邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateArraysIndex04() throws Exception {
        // �O����
        va.setMethodParams("java.lang.String");
        va.setName("hoge");
        
        // �e�X�g���{
        // ����
        try {
            new FieldChecks().validateArraysIndex(new Object(), va, field, errors);
        } catch (ValidatorException e) {
            String expect ="Mistake on validation rule file. "
                    + "- Can not get validateMethod. You'll have to check it over. ";
            assertEquals(expect, e.getMessage());
            assertTrue(LogUTUtil.checkError(expect));
        }
    }

    /**
     * testValidateArraysIndex05()
     * <br><br>
     *
     * (����n) 
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:JavaBean<br>
     *                 String[] array = {<br>
     *                   "a", "b", "c"<br>
     *                 };<br>
     *         (����) va:methodParams="java.lang.Object,
     *         org.apache.commons.validator.ValidatorAction,
     *         org.apache.commons.validator.Field,
     *         jp.terasoluna.fw.validation.ValidationErrors"<br>
     *                name="requiredArray"<br>
     *         (����) field:property="array"<br>
     *         (����) errors:not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *         (��ԕω�) errors:�Ăяo����Ȃ�<br>
     *
     * <br>
     * var�̃v���p�e�B�Ŏw�肳�ꂽ�t�B�[���h���z��^�̃t�B�[���h�ŁA
     * �S�Ẵt�B�[���h�ɂ���va��name�Ŏw�肳�ꂽ�`�F�b�N�ᔽ���Ȃ��ꍇ�A
     * true���擾�ł��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateArraysIndex05() throws Exception {
        // �O���� 
        // JavaBean
        FieldChecks_JavaBeanStub01 bean = new FieldChecks_JavaBeanStub01();
        bean.setArray(new String[]{"a", "b", "c"});
        
        // ValidatorAction
        va.setMethodParams("java.lang.Object,"
                + "org.apache.commons.validator.ValidatorAction,"
                + "org.apache.commons.validator.Field," 
                + "jp.terasoluna.fw.validation.ValidationErrors");
        va.setName("requiredArray");
        
        // Field
        field.setProperty("array");
        
        // �e�X�g���{
        // ����
        assertTrue(new FieldChecks().validateArraysIndex(bean, va, field, errors));
        assertEquals(0, errors.addErrorCount);
    }

    /**
     * testValidateArraysIndex06()
     * <br><br>
     *
     * (����n) 
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:JavaBean<br>
     *                 List list = {<br>
     *                   "a", null, null, "d"<br>
     *                 };<br>
     *         (����) va:methodParams="java.lang.Object,
     *         org.apache.commons.validator.ValidatorAction,
     *         org.apache.commons.validator.Field,
     *         jp.terasoluna.fw.validation.ValidationErrors"<br>
     *                name="requiredArray"<br>
     *         (����) field:property="list"<br>
     *         (����) errors:not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:false<br>
     *         (��ԕω�) errors:2��Ăяo�����<br>
     *                    �P�Fbean=������bean�Ɠ���<br>
     *                        field:<br>
     *                          key="lsit[1]"<br>
     *                          property="list[1]"<br>
     *                        va=������va�Ɠ���<br>
     *                    �Q�Fbean=������bean�Ɠ���l<br>
     *                        field:<br>
     *                          key="list[2]"<br>
     *                          property="list[2]"<br>
     *                        va=������va�Ɠ���<br>
     *
     * <br>
     * var�̃v���p�e�B�Ŏw�肳�ꂽ�t�B�[���h��Collection�^�̃t�B�[���h�ŁA
     * va��name�Ŏw�肳�ꂽ�`�F�b�N�̈ᔽ���������݂���ꍇ�A
     * ���̉񐔕�errors��addErrors���Ăяo����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateArraysIndex06() throws Exception {
        // �O���� 
        // JavaBean
        FieldChecks_JavaBeanStub01 bean = new FieldChecks_JavaBeanStub01();
        List<String> list = new ArrayList<String>();
        list.add("a");
        list.add(null);
        list.add(null);
        list.add("d");
        bean.setList(list);
        
        // ValidatorAction
        va.setMethodParams("java.lang.Object,"
                + "org.apache.commons.validator.ValidatorAction,"
                + "org.apache.commons.validator.Field," 
                + "jp.terasoluna.fw.validation.ValidationErrors");
        va.setName("requiredArray");
        
        // Field
        field.setProperty("list");
        
        // �e�X�g���{
        // ����
        assertFalse(new FieldChecks().validateArraysIndex(bean, va, field, errors));
        assertEquals(2, errors.addErrorCount);
        assertSame(bean, errors.beanList.get(0));
        assertSame(va, errors.vaList.get(0));
        assertEquals("list[1]", ((Field) errors.fieldList.get(0)).getKey());
        assertEquals("list[1]", ((Field) errors.fieldList.get(0)).getProperty());
        
        assertSame(bean, errors.beanList.get(1));
        assertSame(va, errors.vaList.get(1));
        assertEquals("list[2]", ((Field) errors.fieldList.get(1)).getKey());
        assertEquals("list[2]", ((Field) errors.fieldList.get(1)).getProperty());
    }

    /**
     * testValidateArraysIndex07()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:JavaBean<br>
     *                int[] intArray = {<br>
     *                   0,2,5<br>
     *                 };<br>
     *         (����) va:methodParams="java.lang.Object,
     *         org.apache.commons.validator.ValidatorAction,
     *         org.apache.commons.validator.Field,
     *         jp.terasoluna.fw.validation.ValidationErrors"<br>
     *                name="intRangeArray"<br>
     *         (����) field:property="intArray"<br>
     *                var:<br>
     *                intRangeMin=1<br>
     *                intRangeMax=3<br>
     *         (����) errors:not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:false<br>
     *         (��ԕω�) errors:2��Ăяo�����<br>
     *                    �P�Fbean=������bean�Ɠ���<br>
     *                        field:<br>
     *                          key="intArray[0]"<br>
     *                          property="intArray[0]"<br>
     *                          var:<br>
     *                            intRangeMin="1"<br>
     *                            intRangeMax="3"<br>
     *                        va=������va�Ɠ���<br>
     *                    �Q�Fbean=������bean�Ɠ���<br>
     *                        field:<br>
     *                          key="intArray[2]"<br>
     *                          property="intArray[2]"<br>
     *                          var:<br>
     *                            intRangeMin="1"<br>
     *                            intRangeMax="3"<br>
     *                        va=������va�Ɠ���<br>
     *
     * <br>
     * var�̃v���p�e�B�Ŏw�肳�ꂽ�t�B�[���h���v���~�e�B�u�z��^�̃t�B�[���h�ŁA
     * va��name�Ŏw�肳�ꂽ�`�F�b�N�̈ᔽ���������݂���ꍇ�A
     * ���̉񐔕�errors��addErrors���Ăяo����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateArraysIndex07() throws Exception {
        // �O���� 
        // JavaBean
        FieldChecks_JavaBeanStub01 bean = new FieldChecks_JavaBeanStub01();
        bean.setIntArray(new int[]{0, 2, 5});
        
        // ValidatorAction
        va.setMethodParams("java.lang.Object,"
                + "org.apache.commons.validator.ValidatorAction,"
                + "org.apache.commons.validator.Field," 
                + "jp.terasoluna.fw.validation.ValidationErrors");
        va.setName("intRangeArray");
        
        // Field
        field.setProperty("intArray");
        Var var1 = new Var();
        var1.setName("intRangeMin");
        var1.setValue("1");
        field.addVar(var1);
        
        Var var2 = new Var();
        var2.setName("intRangeMax");
        var2.setValue("3");
        field.addVar(var2);
        
        // �e�X�g���{
        // ����
        assertFalse(new FieldChecks().validateArraysIndex(bean, va, field, errors));
        assertEquals(2, errors.addErrorCount);
        assertSame(bean, errors.beanList.get(0));
        assertSame(va, errors.vaList.get(0));
        assertEquals("intArray[0]", ((Field) errors.fieldList.get(0)).getKey());
        assertEquals("intArray[0]", ((Field) errors.fieldList.get(0)).getProperty());
        
        assertSame(bean, errors.beanList.get(1));
        assertSame(va, errors.vaList.get(1));
        assertEquals("intArray[2]", ((Field) errors.fieldList.get(1)).getKey());
        assertEquals("intArray[2]", ((Field) errors.fieldList.get(1)).getProperty());
    }

    /**
     * testValidateArraysIndex08()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:JavaBean<br>
     *                 String field = null;<br>
     *         (����) va:methodParams="java.lang.Object,
     *         org.apache.commons.validator.ValidatorAction,
     *         org.apache.commons.validator.Field,
     *         jp.terasoluna.fw.validation.ValidationErrors"<br>
     *                name="requiredArray"<br>
     *         (����) field:property="field"<br>
     *         (����) errors:not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:false<br>
     *         (��ԕω�) errors:bean=������bean�Ɠ���<br>
     *                      field:<br>
     *                        key="field"<br>
     *                        property="field"<br>
     *                      va=������va�Ɠ���<br>
     *
     * <br>
     * var�̃v���p�e�B�Ŏw�肳�ꂽ�t�B�[���h���z��ACollection�^�ł͂Ȃ��ꍇ�A
     * ���̃t�B�[���h�ɑ΂��Ẵ`�F�b�N���s���邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateArraysIndex08() throws Exception {
        // �O���� 
        // JavaBean
        FieldChecks_JavaBeanStub01 bean = new FieldChecks_JavaBeanStub01();
        bean.setField(null);
        
        // ValidatorAction
        va.setMethodParams("java.lang.Object,"
                + "org.apache.commons.validator.ValidatorAction,"
                + "org.apache.commons.validator.Field," 
                + "jp.terasoluna.fw.validation.ValidationErrors");
        va.setName("requiredArray");
        
        // Field
        field.setProperty("field");
        
        // �e�X�g���{
        // ����
        assertFalse(new FieldChecks().validateArraysIndex(bean, va, field, errors));
        assertEquals(1, errors.addErrorCount);
        assertSame(bean, errors.beanList.get(0));
        assertSame(va, errors.vaList.get(0));
        assertEquals("field", ((Field) errors.fieldList.get(0)).getKey());
        assertEquals("field", ((Field) errors.fieldList.get(0)).getProperty());
    }

    /**
     * testValidateArraysIndex09()
     * <br><br>
     *
     * (����n) 
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:JavaBean<br>
     *                  ��JavaBean[] beanArray���v�f���R<br>
     *                        ��JavaBean[0]<br>
     *                             String[] array = {<br>
     *                               "a", null, "c"<br>
     *                             };<br>
     *                        ��JavaBean[1]=null<br>
     *                        ��JavaBean[2]<br>
     *                             String[] array = {<br>
     *                               "a", null, null<br>
     *                             };<br>
     *         (����) va:methodParams="java.lang.Object,
     *         org.apache.commons.validator.ValidatorAction,
     *         org.apache.commons.validator.Field,
     *         jp.terasoluna.fw.validation.ValidationErrors"<br>
     *                name="requiredArray"<br>
     *         (����) field:property="beanArray.array"<br>
     *         (����) errors:not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:false<br>
     *         (��ԕω�) errors:3��Ăяo�����<br>
     *                    �P�Fbean=������bean�Ɠ���<br>
     *                        field:<br>
     *                          key="beanArray[0].array[1]"<br>
     *                          property="beanArray[0].array[1]"<br>
     *                        va=������va�Ɠ���<br>
     *                    �Q�Fbean=������bean�Ɠ���<br>
     *                        field:<br>
     *                          key="beanArray[2].array[1]"<br>
     *                          property="beanArray[2].array[1]"<br>
     *                        va=������va�Ɠ���<br>
     *                    �R�Fbean=������bean�Ɠ���<br>
     *                        field:<br>
     *                          key="beanArray[2].array[2]"<br>
     *                          property="beanArray[2].array[2]"<br>
     *                        va=������va�Ɠ���<br>
     *
     * <br>
     * var�̃v���p�e�B�Ŏw�肳�ꂽ�t�B�[���h���z��^�̃t�B�[���h�ŁA
     * �S�Ẵt�B�[���h�ɂ���va��name�Ŏw�肳�ꂽ�`�F�b�N�ᔽ���Ȃ��ꍇ�A
     * true���擾�ł��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateArraysIndex09() throws Exception {
        // �O���� 
        // JavaBean-beanArray[0]
        FieldChecks_JavaBeanStub01 beanArray0 = new FieldChecks_JavaBeanStub01();
        beanArray0.setArray(new String[]{"a", null, "c"});
        
        // JavaBean-beanArray[1]
        FieldChecks_JavaBeanStub01 beanArray1 = null;
        
        // JavaBean-beanArray[2]
        FieldChecks_JavaBeanStub01 beanArray2 = new FieldChecks_JavaBeanStub01();
        beanArray2.setArray(new String[]{"a", null, null});
        
        // JavaBean
        FieldChecks_JavaBeanStub01 bean = new FieldChecks_JavaBeanStub01();
        bean.setBeanArray(new Object[]{beanArray0, beanArray1, beanArray2});
        
        // ValidatorAction
        va.setMethodParams("java.lang.Object,"
                + "org.apache.commons.validator.ValidatorAction,"
                + "org.apache.commons.validator.Field," 
                + "jp.terasoluna.fw.validation.ValidationErrors");
        va.setName("requiredArray");
        
        // Field
        field.setProperty("beanArray.array");
        
        // �e�X�g���{
        // ����
        assertFalse(new FieldChecks().validateArraysIndex(bean, va, field, errors));
        assertEquals(3, errors.addErrorCount);
        assertSame(bean, errors.beanList.get(0));
        assertSame(va, errors.vaList.get(0));
        assertEquals("beanArray[0].array[1]", ((Field) errors.fieldList.get(0)).getKey());
        assertEquals("beanArray[0].array[1]", ((Field) errors.fieldList.get(0)).getProperty());
        
        assertSame(bean, errors.beanList.get(1));
        assertSame(va, errors.vaList.get(1));
        assertEquals("beanArray[2].array[1]", ((Field) errors.fieldList.get(1)).getKey());
        assertEquals("beanArray[2].array[1]", ((Field) errors.fieldList.get(1)).getProperty());
        
        assertSame(bean, errors.beanList.get(2));
        assertSame(va, errors.vaList.get(1));
        assertEquals("beanArray[2].array[2]", ((Field) errors.fieldList.get(2)).getKey());
        assertEquals("beanArray[2].array[2]", ((Field) errors.fieldList.get(2)).getProperty());
    }

    /**
     * testValidateArraysIndex10()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:JavaBean<br>
     *                 String[] array = {};<br>
     *         (����) va:methodParams="java.lang.Object,
     *         org.apache.commons.validator.ValidatorAction,
     *         org.apache.commons.validator.Field,
     *         jp.terasoluna.fw.validation.ValidationErrors"<br>
     *                name="requiredArray"<br>
     *         (����) field:property="array"<br>
     *         (����) errors:not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *         (��ԕω�) errors:�Ăяo����Ȃ�<br>
     *
     * <br>
     * var�̃v���p�e�B�Ŏw�肳�ꂽ�t�B�[���h���z��^�̃t�B�[���h�ŁA
     * �v�f���Ȃ��ꍇtrue���擾�ł��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateArraysIndex10() throws Exception {
        // �O���� 
        // JavaBean
        FieldChecks_JavaBeanStub01 bean = new FieldChecks_JavaBeanStub01();
        bean.setArray(new String[]{});
        
        // ValidatorAction
        va.setMethodParams("java.lang.Object,"
                + "org.apache.commons.validator.ValidatorAction,"
                + "org.apache.commons.validator.Field," 
                + "jp.terasoluna.fw.validation.ValidationErrors");
        va.setName("requiredArray");
        
        // Field
        field.setProperty("array");
        
        // �e�X�g���{
        // ����
        assertTrue(new FieldChecks().validateArraysIndex(bean, va, field, errors));
        assertEquals(0, errors.addErrorCount);
    }

    /**
     * testValidateArraysIndex11()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) bean:JavaBean<br>
     *                 String[] array = {};<br>
     *         (����) va:null<br>
     *                ���{�����肦�Ȃ����J�o���b�W�̈׏����ɒǉ�<br>
     *         (����) field:property="array"<br>
     *         (����) errors:not null<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:ValidatorException<br>
     *                    ���b�Z�[�W�Fnull<br>
     *         (��ԕω�) ���O:���O���x���F�G���[<br>
     *                    ���b�Z�[�W�Fnull<br>
     *                    ��O�FNullPointerException<br>
     *
     * <br>
     * ��������ValidatorException�AInvocationTargetException�ȊO�̗�O�����������ꍇ�A
     * ValidatorException���������邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateArraysIndex11() throws Exception {
        // �O���� 
        // JavaBean
        FieldChecks_JavaBeanStub01 bean = new FieldChecks_JavaBeanStub01();
        bean.setArray(new String[]{});
        
        // ValidatorAction
        va = null;
        
        // Field
        field.setProperty("array");
        
        // �e�X�g���{
        // ����
        try {
            new FieldChecks().validateArraysIndex(bean, va, field, errors);
            fail();
        } catch (ValidatorException e) {
            // OK
            assertNull(e.getMessage());
            assertTrue(LogUTUtil.checkError(null, new NullPointerException()));
        }
    }

    /**
     * testValidateArraysIndex12()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) bean:JavaBean<br>
     *                 String[] array = {<br>
     *                  "a"<br>
     *                };<br>
     *         (����) va:methodParams="java.lang.Object,
     *         org.apache.commons.validator.ValidatorAction,
     *         org.apache.commons.validator.Field,
     *         jp.terasoluna.fw.validation.ValidationErrors"<br>
     *                name="maskArray"<br>
     *         (����) field:property="array"<br>
     *         (����) errors:not null<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:ValidatorException<br>
     *                    ���b�Z�[�W�F"var[mask] must be specified."<br>
     *
     * <br>
     * ��������ValidatorException�����b�v����InvocationTargetException�����������ꍇ�A
     * ���b�v����ValidatorException���X���[����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateArraysIndex12() throws Exception {
        // �O���� 
        // JavaBean
        FieldChecks_JavaBeanStub01 bean = new FieldChecks_JavaBeanStub01();
        bean.setArray(new String[]{"a"});
        
        // ValidatorAction
        va.setMethodParams("java.lang.Object,"
                + "org.apache.commons.validator.ValidatorAction,"
                + "org.apache.commons.validator.Field," 
                + "jp.terasoluna.fw.validation.ValidationErrors");
        va.setName("maskArray");
        
        // Field
        field.setProperty("array");
        
        // �e�X�g���{
        // ����
        try {
            new FieldChecks().validateArraysIndex(bean, va, field, errors);
            fail();
        } catch (ValidatorException e) {
            // OK
            assertEquals("var[mask] must be specified.", e.getMessage());
            assertTrue(LogUTUtil.checkError(e.getMessage()));
        }
    }

    /**
     * testValidateArraysIndex13()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) bean:JavaBean<br>
     *                 String[] array = {"a", null, "c"}<br>
     *         (����) va:methodParams="java.lang.Object,
     *         org.apache.commons.validator.ValidatorAction,
     *         org.apache.commons.validator.Field,
     *         jp.terasoluna.fw.validation.ValidationErrors"<br>
     *                name="requiredArray"<br>
     *         (����) field:property="array"<br>
     *         (����) errors:addErrors��RuntimeException������<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:ValidatorException<br>
     *                    ���b�Z�[�W�FRuntimeException.getMessage();<br>
     *         (��ԕω�) ���O:���O���x���F�G���[<br>
     *                    ���b�Z�[�W�FRuntimeException.getMessage();<br>
     *                    ��O�FRuntimeException<br>
     *
     * <br>
     * ��������ValidatorException�ȊO�̗�O�����b�v����
     * InvocationTargetException�����������ꍇ�A
     * ���b�v������O�̃��b�Z�[�W��ێ�����ValidatorException���X���[����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateArraysIndex13() throws Exception {
        // �O���� 
        // JavaBean
        FieldChecks_JavaBeanStub01 bean = new FieldChecks_JavaBeanStub01();
        bean.setArray(new String[]{"a", null, "c"});
        
        // ValidatorAction
        va.setMethodParams("java.lang.Object,"
                + "org.apache.commons.validator.ValidatorAction,"
                + "org.apache.commons.validator.Field," 
                + "jp.terasoluna.fw.validation.ValidationErrors");
        va.setName("requiredArray");
        
        // Field
        field.setProperty("array");
        
        // errors �i�G���[�ǉ����ɗ�O����������j
        FieldChecks_ValidationErrorsImpl02 errors2 = 
            new FieldChecks_ValidationErrorsImpl02();
        
        // �e�X�g���{
        // ����
        try {
            new FieldChecks().validateArraysIndex(bean, va, field, errors2);
            fail();
        } catch (ValidatorException e) {
            // OK
            assertEquals(new RuntimeException().getMessage(), e.getMessage());
        }
    }

    /**
     * testValidateArraysIndex14()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) bean:JavaBean<br>
     *                 String[] array = {};<br>
     *         (����) va:methodParams="java.lang.Object,
     *         org.apache.commons.validator.ValidatorAction,
     *         org.apache.commons.validator.Field,
     *         jp.terasoluna.fw.validation.ValidationErrors"<br>
     *                name="requiredArray"<br>
     *         (����) field:null<br>
     *                ���{�����肦�Ȃ����J�o���b�W�̈׏����ɒǉ�<br>
     *         (����) errors:not null<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:ValidatorException<br>
     *                    ���b�Z�[�W�Fnull<br>
     *         (��ԕω�) ���O:���O���x���F�G���[<br>
     *                    ���b�Z�[�W�Fnull<br>
     *                    ��O�FNullPointerException<br>
     *
     * <br>
     * ��������ValidatorException�AInvocationTargetException�ȊO�̗�O�����������ꍇ�A
     * ValidatorException���������邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateArraysIndex14() throws Exception {
        // �O���� 
        // JavaBean
        FieldChecks_JavaBeanStub01 bean = new FieldChecks_JavaBeanStub01();
        bean.setArray(new String[]{});
        
        // ValidatorAction
        va.setMethodParams("java.lang.Object,"
                + "org.apache.commons.validator.ValidatorAction,"
                + "org.apache.commons.validator.Field," 
                + "jp.terasoluna.fw.validation.ValidationErrors");
        va.setName("requiredArray");
        
        // Field
        field = null;
        
        // �e�X�g���{
        // ����
        try {
            new FieldChecks().validateArraysIndex(bean, va, field, errors);
            fail();
        } catch (ValidatorException e) {
            // OK
            assertNull(e.getMessage());
            assertTrue(LogUTUtil.checkError(null, new NullPointerException()));
        }
    }
}
