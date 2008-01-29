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

import java.lang.reflect.Method;

import jp.terasoluna.fw.validation.FieldChecks;
import jp.terasoluna.fw.validation.ValidationErrors;
import junit.framework.TestCase;

import org.apache.commons.validator.Field;
import org.apache.commons.validator.ValidatorAction;

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
public class FieldChecksTest01 extends TestCase {

    /**
     * �e�X�g�p�C���X�^���X�B
     */
    private ValidatorAction va = null;

    /**
     * �e�X�g�p�C���X�^���X�B
     */
    private Field field = null;

    /**
     * ���̃e�X�g�P�[�X�����s����ׂ�
     * GUI �A�v���P�[�V�������N������B
     *
     * @param args java �R�}���h�ɐݒ肳�ꂽ�p�����[�^
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(FieldChecksTest01.class);
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
    public FieldChecksTest01(String name) {
        super(name);
    }

    /**
     * testGetParamClass01()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) va:not null<br>
     *         (���) va.methodParams:""<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) Class[]:Class[]{}<br>
     *                  �v�f��0<br>
     *
     * <br>
     * va��methodParams���󕶎��̂Ƃ��A
     * �v�f��0��Class�^�z�񂪎擾�ł��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetParamClass01() throws Exception {
        // �O����
        va.setMethodParams("");

        // �e�X�g���{
        Class[] result = new FieldChecks().getParamClass(va);

        // ����
        assertEquals(0, result.length);
    }

    /**
     * testGetParamClass02()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) va:not null<br>
     *         (���) va.methodParams:
     *                "java.lang.String,java.lang.Integer,java.lang.Boolean"<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) Class[]:{String.class,<br>
     *                   Integer.class,<br>
     *                   Boolean.class}<br>
     *
     * <br>
     * va��methodParams�ɐݒ肳��Ă���A
     * �J���}��؂�̃N���X���̃N���X�C���X�^���X���擾�ł��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetParamClass02() throws Exception {
        // �O����
        va.setMethodParams(
                "java.lang.String,java.lang.Integer,java.lang.Boolean");

        // �e�X�g���{
        Class[] result = new FieldChecks().getParamClass(va);

        // ����
        assertEquals(3, result.length);
        assertSame(String.class, result[0]);
        assertSame(Integer.class, result[1]);
        assertSame(Boolean.class, result[2]);
    }

    /**
     * testGetParamClass03()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) va:not null<br>
     *         (���) va.methodParams:"java.lang.String,bbb"<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) Class[]:null<br>
     *
     * <br>
     * va��methodParams�ɃN���X�p�X�ɑ��݂��Ȃ��N���X�����܂܂�Ă���ꍇ�A
     * null���擾�ł��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetParamClass03() throws Exception {
        // �O����
        va.setMethodParams("java.lang.String,bbb");

        // �e�X�g���{
        // ����
        assertNull(new FieldChecks().getParamClass(va));

    }

    /**
     * testGetMethod01()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FC,F
     * <br><br>
     * ���͒l�F(����) va:not null<br>
     *         (���) va.getName:null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) Method:null<br>
     *
     * <br>
     * va����擾�������\�b�h����null�̏ꍇ�Anull���ԋp����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetMethod01() throws Exception {
        // �O����
        va.setName(null);

        // �e�X�g���{
        // ����
        assertNull(new FieldChecks().getMethod(va, new Class[]{}));
    }

    /**
     * testGetMethod02()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FC,F
     * <br><br>
     * ���͒l�F(����) va:not null<br>
     *         (���) va.getName:�󕶎�<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) Method:null<br>
     *
     * <br>
     * va����擾�������\�b�h�����󕶎��̏ꍇ�Anull���ԋp����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetMethod02() throws Exception {
        // �O����
        va.setName("");

        // �e�X�g���{
        // ����
        assertNull(new FieldChecks().getMethod(va, new Class[]{}));
    }

    /**
     * testGetMethod03()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) va:not null<br>
     *         (����) paramClass:{Object.class,<br>
     *                 ValidatorAction.class,<br>
     *                 Field.class,<br>
     *                 ValidationErrors.class}<br>
     *         (���) va.getName:"requiredArray"<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) Method:new FieldChecks()#validateRequired<br>
     *
     * <br>
     * va����擾�������\�b�h�̍Ō��5�����������A
     * �擪��validate��t�^�������\�b�h�����݂���ꍇ�A
     * ���̃��\�b�h�̃C���X�^���X���擾�ł��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetMethod03() throws Exception {
        // �O����
        va.setName("requiredArray");
        Class[] paramClass = {
            Object.class,
            ValidatorAction.class,
            Field.class,
            ValidationErrors.class
        };

        // �e�X�g���{
        Method result = new FieldChecks().getMethod(va,paramClass);

        // ����
        assertEquals(FieldChecks.class, result.getDeclaringClass());
        assertEquals("validateRequired", result.getName());
    }

    /**
     * testGetMethod04()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) va:not null<br>
     *         (����) paramClass:{Object.class,<br>
     *                 ValidatorAction.class,<br>
     *                 Field.class}<br>
     *         (���) va.getName:"requiredArray"<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) Method:null<br>
     *
     * <br>
     * va����擾�������\�b�h�̍Ō��5�����������A
     * �擪��validate��t�^�������\�b�h�����݂��邪�A
     * �����̃p�^�[����paramClass�ƈ�v���Ȃ��ꍇ�A
     * null���ԋp����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetMethod04() throws Exception {
        // �O����
        va.setName("requiredArray");
        Class[] paramClass = {
            Object.class,
            ValidatorAction.class,
            Field.class
        };

        // �e�X�g���{
        // ����
        assertNull(new FieldChecks().getMethod(va,paramClass));
    }

    /**
     * testGetMethod05()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) va:not null<br>
     *         (����) paramClass:{Object.class,<br>
     *                 ValidatorAction.class,<br>
     *                 Field.class,<br>
     *                 ValidationErrors.class}<br>
     *         (���) va.getName:"requiredXXXXX"<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) Method:new FieldChecks()#validateRequired<br>
     *
     * <br>
     * va����擾�������\�b�h�̍Ō��5�����������A
     * �擪��validate��t�^�������\�b�h�����݂���ꍇ�A
     * ���̃��\�b�h�̃C���X�^���X���擾�ł��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetMethod05() throws Exception {
        // �O����
        va.setName("requiredXXXXX");
        Class[] paramClass = {
            Object.class,
            ValidatorAction.class,
            Field.class,
            ValidationErrors.class
        };

        // �e�X�g���{
        Method result = new FieldChecks().getMethod(va,paramClass);

        // ����
        assertEquals(FieldChecks.class, result.getDeclaringClass());
        assertEquals("validateRequired", result.getName());
    }

    /**
     * testExtractValue01()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC,F
     * <br><br>
     * ���͒l�F(����) bean:null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) String:null<br>
     *
     * <br>
     * ������bean��null�̏ꍇ�Anull���ԋp����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testExtractValue01() throws Exception {
        // �e�X�g���{
        // ����
        assertNull(new FieldChecks().extractValue(null, field));
    }

    /**
     * testExtractValue02()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:"test"<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) String:"test"<br>
     *
     * <br>
     * ������bean��String�^�̏ꍇ�Abean�̒l���ԋp����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testExtractValue02() throws Exception {
        // �e�X�g���{
        // ����
        assertEquals("test", new FieldChecks().extractValue("test", field));
    }

    /**
     * testExtractValue03()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:JavaBean {<br>
     *                   field="testProperty"<br>
     *                }<br>
     *         (����) field:property="field"<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) String:"testProperty"<br>
     *
     * <br>
     * bean�ɁAfield��property�����l�̃t�B�[���h�����݂���ꍇ�A
     * ���̒l���擾�ł��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testExtractValue03() throws Exception {
        // �O����
        FieldChecks_JavaBeanStub01 bean
            = new FieldChecks_JavaBeanStub01();
        field.setProperty("field");

        // �e�X�g���{
        // ����
        assertEquals("testProperty", new FieldChecks().extractValue(bean, field));
    }

    /**
     * testExtractValue04()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:JavaBean {<br>
     *                   field="testProperty"<br>
     *                }<br>
     *         (����) field:property="field2"<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) String:null<br>
     *
     * <br>
     * bean�ɁAfield��property�����l�̃t�B�[���h�����݂��Ȃ��ꍇ�A
     * null���擾�ł��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testExtractValue04() throws Exception {
        // �O����
        FieldChecks_JavaBeanStub01 bean
            = new FieldChecks_JavaBeanStub01();
        field.setProperty("field2");

        // �e�X�g���{
        // ����
        assertNull(new FieldChecks().extractValue(bean, field));
    }

    /**
     * testExtractValue05()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:new Integer(12345)<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) String:"12345"<br>
     *         
     * <br>
     * ������bean��Number�^�̏ꍇ�Abean�̒l���ԋp����邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testExtractValue05() throws Exception {
        // �e�X�g���{
        // ����
        assertEquals("12345", new FieldChecks().extractValue(new Integer(12345), field));
    }

    /**
     * testExtractValue06()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:new Boolean(true)<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) String:"true"<br>
     *         
     * <br>
     * ������bean��Boolean�^�̏ꍇ�Abean�̒l���ԋp����邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testExtractValue06() throws Exception {
        // �e�X�g���{
        // ����
        assertEquals("true", new FieldChecks().extractValue(new Boolean(true), field)); 
    }

    /**
     * testExtractValue07()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:new Character('@')<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) String:"@"<br>
     *         
     * <br>
     * ������bean��Character�^�̏ꍇ�Abean�̒l���ԋp����邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testExtractValue07() throws Exception {
        // �e�X�g���{
        // ����
        assertEquals("@", new FieldChecks().extractValue(new Character('@'), field)); 
    }

    /**
     * testRejectValue01()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) errors:not null<br>
     *         (����) field:not null<br>
     *         (����) va:not null<br>
     *         (����) bean:not null<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) errors:bean,field,va�������Ƃ���
     * addErrors���Ăяo�����B<br>
     *
     * <br>
     * bean,field,va�������Ƃ���addErrors���Ăяo����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testRejectValue01() throws Exception {
        // �O����
        FieldChecks_JavaBeanStub01 bean = new FieldChecks_JavaBeanStub01();
        FieldChecks_ValidationErrorsImpl01 errors =
            new FieldChecks_ValidationErrorsImpl01();

        // �e�X�g���{
        new FieldChecks().rejectValue(errors, field, va, bean);

        // ����
        assertEquals(1, errors.addErrorCount);
        assertSame(field, errors.fieldList.get(0));
        assertSame(va, errors.vaList.get(0));
        assertSame(bean, errors.beanList.get(0));
    }

}
