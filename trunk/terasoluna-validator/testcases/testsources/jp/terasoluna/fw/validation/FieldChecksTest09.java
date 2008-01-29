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
public class FieldChecksTest09 extends TestCase {

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
        junit.swingui.TestRunner.run(FieldChecksTest09.class);
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
    public FieldChecksTest09(String name) {
        super(name);
    }

    /**
     * testValidateUrl01()
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
    public void testValidateUrl01() throws Exception {
        // �e�X�g���{
        // ����
        assertTrue(new FieldChecks().validateUrl(null, va, field, errors));

        // addErrors�m�F
        assertEquals(0, errors.addErrorCount);
    }

    /**
     * testValidateUrl02()
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
    public void testValidateUrl02() throws Exception {
        // �e�X�g���{
        // ����
        assertTrue(new FieldChecks().validateUrl("", va, field, errors));

        // addErrors�m�F
        assertEquals(0, errors.addErrorCount);
    }

    /**
     * testValidateUrl03()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:abc://terasoluna.com/index.html#fragment<br>
     *         (����) va:not null<br>
     *         (����) field:var:<br>
     *                allowallschemes=true<br>
     *         (����) errors:not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *         (��ԕω�) errors:�Ăяo����Ȃ�<br>
     *
     * <br>
     * var��allowallschemes��true�̂Ƃ��Abean�̃X�L�[�}�����s���ȏꍇ�ł�true���擾�ł��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateUrl03() throws Exception {
        // �O����
        Var var = new Var();
        var.setName("allowallschemes");
        var.setValue("true");
        field.addVar(var);

        // �e�X�g���{
        // ����
        assertTrue(new FieldChecks().validateUrl(
            "abc://terasoluna.com/index.html#fragment", va, field, errors));

        // addErrors�m�F
        assertEquals(0, errors.addErrorCount);
    }

    /**
     * testValidateUrl04()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:abc://terasoluna.com/index.html<br>
     *         (����) va:not null<br>
     *         (����) field:var:<br>
     *                allowallschemes=null<br>
     *                schemes=http,ftp<br>
     *         (����) errors:not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:false<br>
     *         (��ԕω�) errors:bean,field,va�������Ƃ���addErrors���Ăяo�����B<br>
     *
     * <br>
     * var��allowallschemes��null�ŁAbean�̃X�L�[�}����var��schemes�Ɏw�肳�ꂽ�X�L�[�}�ƈ�v���Ȃ��ꍇ�A�G���[��ǉ�����false���ԋp����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateUrl04() throws Exception {
        // �O����
        Var var = new Var();
        var.setName("schemes");
        var.setValue("http,ftp");
        field.addVar(var);

        // �e�X�g���{
        // ����
        assertFalse(new FieldChecks().validateUrl(
            "abc://terasoluna.com/index.html", va, field, errors));

        // addErrors�m�F
        assertEquals(1, errors.addErrorCount);
        assertEquals("abc://terasoluna.com/index.html", errors.beanList.get(0));
        assertSame(va, errors.vaList.get(0));
        assertSame(field, errors.fieldList.get(0));
    }

    /**
     * testValidateUrl05()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:abc://terasoluna.com/index.html<br>
     *         (����) va:not null<br>
     *         (����) field:var:<br>
     *                allowallschemes=""<br>
     *                schemes=http,ftp<br>
     *         (����) errors:not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:false<br>
     *         (��ԕω�) errors:bean,field,va�������Ƃ���
     *                           addErrors���Ăяo�����B<br>
     *
     * <br>
     * var��allowallschemes���󕶎��ŁA
     * bean�̃X�L�[�}����var��schemes�Ɏw�肳�ꂽ�X�L�[�}�ƈ�v���Ȃ��ꍇ�A
     * �G���[��ǉ�����false���ԋp����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateUrl05() throws Exception {
        // �O����
        Var var1 = new Var();
        var1.setName("allowallschemes");
        var1.setValue("");
        field.addVar(var1);
        
        Var var2 = new Var();
        var2.setName("schemes");
        var2.setValue("http,ftp");
        field.addVar(var2);

        // �e�X�g���{
        // ����
        assertFalse(new FieldChecks().validateUrl(
            "abc://terasoluna.com/index.html", va, field, errors));

        // addErrors�m�F
        assertEquals(1, errors.addErrorCount);
        assertEquals("abc://terasoluna.com/index.html", errors.beanList.get(0));
        assertSame(va, errors.vaList.get(0));
        assertSame(field, errors.fieldList.get(0));
    }

    /**
     * testValidateUrl06()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:abc://terasoluna.com/index.html<br>
     *         (����) va:not null<br>
     *         (����) field:var:<br>
     *                allowallschemes="test"<br>
     *                schemes=http,ftp<br>
     *         (����) errors:not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:false<br>
     *         (��ԕω�) errors:bean,field,va�������Ƃ���
     *                           addErrors���Ăяo�����B<br>
     *
     * <br>
     * var��allowallschemes��true�ȊO�̕�����ŁA
     * bean�̃X�L�[�}����var��schemes�Ɏw�肳�ꂽ�X�L�[�}�ƈ�v���Ȃ��ꍇ�A
     * �G���[��ǉ�����false���ԋp����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateUrl06() throws Exception {
        // �O����
        Var var1 = new Var();
        var1.setName("allowallschemes");
        var1.setValue("test");
        field.addVar(var1);
        
        Var var2 = new Var();
        var2.setName("schemes");
        var2.setValue("http,ftp");
        field.addVar(var2);

        // �e�X�g���{
        // ����
        assertFalse(new FieldChecks().validateUrl(
            "abc://terasoluna.com/index.html", va, field, errors));

        // addErrors�m�F
        assertEquals(1, errors.addErrorCount);
        assertEquals("abc://terasoluna.com/index.html", errors.beanList.get(0));
        assertSame(va, errors.vaList.get(0));
        assertSame(field, errors.fieldList.get(0));
    }

    /**
     * testValidateUrl07()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:abc://terasoluna.com/index.html<br>
     *         (����) va:not null<br>
     *         (����) field:var:<br>
     *                allowallschemes="test"<br>
     *                schemes=http,ftp,abc<br>
     *         (����) errors:not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *         (��ԕω�) errors:�Ăяo����Ȃ�<br>
     *
     * <br>
     * var��allowallschemes��true�ȊO�̕�����ŁA
     * bean�̃X�L�[�}����var��schemes�Ɏw�肳�ꂽ�X�L�[�}�ƈ�v����ꍇ�A
     * true���ԋp����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateUrl07() throws Exception {
        // �O����
        Var var1 = new Var();
        var1.setName("allowallschemes");
        var1.setValue("test");
        field.addVar(var1);
        
        Var var2 = new Var();
        var2.setName("schemes");
        var2.setValue("http,ftp,abc");
        field.addVar(var2);

        // �e�X�g���{
        // ����
        assertTrue(new FieldChecks().validateUrl(
            "abc://terasoluna.com/index.html", va, field, errors));

        // addErrors�m�F
        assertEquals(0, errors.addErrorCount);
    }

    /**
     * testValidateUrl08()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:abc://terasoluna.com//index.html<br>
     *         (����) va:not null<br>
     *         (����) field:var:<br>
     *                allowallschemes="true"<br>
     *                allow2slashes="true"<br>
     *         (����) errors:not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *         (��ԕω�) errors:�Ăяo����Ȃ�<br>
     *
     * <br>
     * var��allow2slashes��true�̂Ƃ��Abean��url�̋�؂�蕶���Ɂu//�v���܂܂�Ă��Ă�true���擾�ł��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateUrl08() throws Exception {
        // �O����
        Var var1 = new Var();
        var1.setName("allowallschemes");
        var1.setValue("true");
        field.addVar(var1);
        
        Var var2 = new Var();
        var2.setName("allow2slashes");
        var2.setValue("true");
        field.addVar(var2);

        // �e�X�g���{
        // ����
        assertTrue(new FieldChecks().validateUrl(
            "abc://terasoluna.com//index.html", va, field, errors));

        // addErrors�m�F
        assertEquals(0, errors.addErrorCount);
    }

    /**
     * testValidateUrl09()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:abc://terasoluna.com//index.html<br>
     *         (����) va:not null<br>
     *         (����) field:var:<br>
     *                allowallschemes="true"<br>
     *                allow2slashes=null<br>
     *         (����) errors:not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:false<br>
     *         (��ԕω�) errors:bean,field,va�������Ƃ���
     *                           addErrors���Ăяo�����B<br>
     *
     * <br>
     * var��allow2slashes��null�ŁA
     * bean��url�̋�؂�蕶���Ɂu//�v���܂܂�Ă���ꍇ�A
     * �G���[��ǉ�����false���ԋp����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateUrl09() throws Exception {
        // �O����
        Var var = new Var();
        var.setName("allowallschemes");
        var.setValue("true");
        field.addVar(var);

        // �e�X�g���{
        // ����
        assertFalse(new FieldChecks().validateUrl(
            "abc://terasoluna.com//index.html", va, field, errors));

        // addErrors�m�F
        assertEquals(1, errors.addErrorCount);
        assertEquals("abc://terasoluna.com//index.html", errors.beanList.get(0));
        assertSame(va, errors.vaList.get(0));
        assertSame(field, errors.fieldList.get(0));
    }

    /**
     * testValidateUrl10()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:abc://terasoluna.com//index.html<br>
     *         (����) va:not null<br>
     *         (����) field:var:<br>
     *                allowallschemes="true"<br>
     *                allow2slashes=""<br>
     *         (����) errors:not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:false<br>
     *         (��ԕω�) errors:bean,field,va�������Ƃ���
     *                           addErrors���Ăяo�����B<br>
     *
     * <br>
     * var��allow2slashes���󕶎��ŁA
     * bean��url�̋�؂�蕶���Ɂu//�v���܂܂�Ă���ꍇ�A
     * �G���[��ǉ�����false���ԋp����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateUrl10() throws Exception {
        // �O����
        Var var1 = new Var();
        var1.setName("allowallschemes");
        var1.setValue("true");
        field.addVar(var1);
        
        Var var2 = new Var();
        var2.setName("allow2slashes");
        var2.setValue("");
        field.addVar(var2);

        // �e�X�g���{
        // ����
        assertFalse(new FieldChecks().validateUrl(
            "abc://terasoluna.com//index.html", va, field, errors));

        // addErrors�m�F
        assertEquals(1, errors.addErrorCount);
        assertEquals("abc://terasoluna.com//index.html", errors.beanList.get(0));
        assertSame(va, errors.vaList.get(0));
        assertSame(field, errors.fieldList.get(0));
    }

    /**
     * testValidateUrl11()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:abc://terasoluna.com//index.html<br>
     *         (����) va:not null<br>
     *         (����) field:var:<br>
     *                allowallschemes="true"<br>
     *                allow2slashes="test"<br>
     *         (����) errors:not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:false<br>
     *         (��ԕω�) errors:bean,field,va�������Ƃ���
     *                           addErrors���Ăяo�����B<br>
     *
     * <br>
     * var��allow2slashes��true�ȊO�̕�����ŁA
     * bean��url�̋�؂�蕶���Ɂu//�v���܂܂�Ă���ꍇ�A
     * �G���[��ǉ�����false���ԋp����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateUrl11() throws Exception {
        // �O����
        Var var1 = new Var();
        var1.setName("allowallschemes");
        var1.setValue("true");
        field.addVar(var1);
        
        Var var2 = new Var();
        var2.setName("allow2slashes");
        var2.setValue("test");
        field.addVar(var2);

        // �e�X�g���{
        // ����
        assertFalse(new FieldChecks().validateUrl(
            "abc://terasoluna.com//index.html", va, field, errors));

        // addErrors�m�F
        assertEquals(1, errors.addErrorCount);
        assertEquals("abc://terasoluna.com//index.html", errors.beanList.get(0));
        assertSame(va, errors.vaList.get(0));
        assertSame(field, errors.fieldList.get(0));
    }

    /**
     * testValidateUrl12()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:abc://terasoluna.com/index.html#fragment<br>
     *         (����) va:not null<br>
     *         (����) field:var:<br>
     *                allowallschemes="true"<br>
     *                nofragments="true"<br>
     *         (����) errors:not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:false<br>
     *         (��ԕω�) errors:bean,field,va�������Ƃ���
     *                           addErrors���Ăяo�����B<br>
     *
     * <br>
     * var��nofragments��true�ŁAbean��url�Ƀt���O�����g�����݂���ꍇ�A
     * �G���[��ǉ�����false���ԋp����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateUrl12() throws Exception {
        // �O����
        Var var1 = new Var();
        var1.setName("allowallschemes");
        var1.setValue("true");
        field.addVar(var1);
        
        Var var2 = new Var();
        var2.setName("nofragments");
        var2.setValue("true");
        field.addVar(var2);

        // �e�X�g���{
        // ����
        assertFalse(new FieldChecks().validateUrl(
            "abc://terasoluna.com/index.html#fragment", va, field, errors));

        // addErrors�m�F
        assertEquals(1, errors.addErrorCount);
        assertEquals("abc://terasoluna.com/index.html#fragment",
                errors.beanList.get(0));
        assertSame(va, errors.vaList.get(0));
        assertSame(field, errors.fieldList.get(0));
    }

    /**
     * testValidateUrl13()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:abc://terasoluna.com/index.html#fragment<br>
     *         (����) va:not null<br>
     *         (����) field:var:<br>
     *                allowallschemes="true"<br>
     *                nofragments=null<br>
     *         (����) errors:not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *         (��ԕω�) errors:�Ăяo����Ȃ�<br>
     *
     * <br>
     * var��nofragments��null�ŁAbean��url�Ƀt���O�����g�����݂���ꍇ�Atrue���ԋp����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateUrl13() throws Exception {
        // �O����
        Var var = new Var();
        var.setName("allowallschemes");
        var.setValue("true");
        field.addVar(var);

        // �e�X�g���{
        // ����
        assertTrue(new FieldChecks().validateUrl(
            "abc://terasoluna.com/index.html#fragment", va, field, errors));

        // addErrors�m�F
        assertEquals(0, errors.addErrorCount);
    }

    /**
     * testValidateUrl14()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:abc://terasoluna.com/index.html#fragment<br>
     *         (����) va:not null<br>
     *         (����) field:var:<br>
     *                allowallschemes="true"<br>
     *                nofragments=""<br>
     *         (����) errors:not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *         (��ԕω�) errors:�Ăяo����Ȃ�<br>
     *
     * <br>
     * var��nofragments���󕶎��ŁAbean��url�Ƀt���O�����g�����݂���ꍇ�Atrue���ԋp����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateUrl14() throws Exception {
        // �O����
        Var var1 = new Var();
        var1.setName("allowallschemes");
        var1.setValue("true");
        field.addVar(var1);
        
        Var var2 = new Var();
        var2.setName("nofragments");
        var2.setValue("");
        field.addVar(var2);

        // �e�X�g���{
        // ����
        assertTrue(new FieldChecks().validateUrl(
            "abc://terasoluna.com/index.html#fragment", va, field, errors));

        // addErrors�m�F
        assertEquals(0, errors.addErrorCount);
    }

    /**
     * testValidateUrl15()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:abc://terasoluna.com/index.html#fragment<br>
     *         (����) va:not null<br>
     *         (����) field:var:<br>
     *                allowallschemes="true"<br>
     *                nofragments="test"<br>
     *         (����) errors:not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *         (��ԕω�) errors:�Ăяo����Ȃ�<br>
     *
     * <br>
     * var��nofragments��true�ȊO�̕�����ŁA
     * bean��url�Ƀt���O�����g�����݂���ꍇ�Atrue���ԋp����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateUrl15() throws Exception {
        // �O����
        Var var1 = new Var();
        var1.setName("allowallschemes");
        var1.setValue("true");
        field.addVar(var1);
        
        Var var2 = new Var();
        var2.setName("nofragments");
        var2.setValue("test");
        field.addVar(var2);

        // �e�X�g���{
        // ����
        assertTrue(new FieldChecks().validateUrl(
            "abc://terasoluna.com/index.html#fragment", va, field, errors));

        // addErrors�m�F
        assertEquals(0, errors.addErrorCount);
    }

    /**
     * testValidateByteRange01()
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
    public void testValidateByteRange01() throws Exception {
        // �e�X�g���{
        // ����
        assertTrue(new FieldChecks().validateByteRange(null, va, field, errors));

        // addErrors�m�F
        assertEquals(0, errors.addErrorCount);
    }

    /**
     * testValidateByteRange02()
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
    public void testValidateByteRange02() throws Exception {
        // �e�X�g���{
        // ����
        assertTrue(new FieldChecks().validateByteRange("", va, field, errors));

        // addErrors�m�F
        assertEquals(0, errors.addErrorCount);
    }

    /**
     * testValidateByteRange03()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FF,G
     * <br><br>
     * ���͒l�F(����) bean:"test"<br>
     *         (����) va:not null<br>
     *         (����) field:var:<br>
     *                minByteLength="test"<br>
     *         (����) errors:not null<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:ValidatorException<br>
     *                    ���b�Z�[�W�F"Mistake on validation definition file.
     *                    - minByteLength is not number.
     *                    You'll have to check it over. "<br>
     *         (��ԕω�) ���O:���O���x���F�G���[<br>
     *                    ���b�Z�[�W�F"Mistake on validation definition file.
     *                    - minByteLength is not number.
     *                    You'll have to check it over. ", new NumberFormatException()<br>
     *
     * <br>
     * var��maxByteLength�̒l�����l�ɕϊ��ł��Ȃ��ꍇ�A
     * ValidatorException���������邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateByteRange03() throws Exception {
        // �O����
        Var var = new Var();
        var.setName("minByteLength");
        var.setValue("test");
        field.addVar(var);

        // �e�X�g���{
        // ����
        try {
            new FieldChecks().validateByteRange("test", va, field, errors);
            fail();
        } catch (ValidatorException e) {
            String message = "Mistake on validation definition file. " +
                    "- minByteLength is not number. " +
                    "You'll have to check it over. ";
            assertEquals(message, e.getMessage());
            assertTrue(LogUTUtil.checkError(message, new NumberFormatException()));
        }
    }

    /**
     * testValidateByteRange04()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FF,G
     * <br><br>
     * ���͒l�F(����) bean:"test"<br>
     *         (����) va:not null<br>
     *         (����) field:var:<br>
     *                maxByteLength="test"<br>
     *         (����) errors:not null<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:ValidatorException<br>
     *                    ���b�Z�[�W�F"Mistake on validation definition file.
     *                    - maxByteLength is not number.
     *                    You'll have to check it over. "<br>
     *         (��ԕω�) ���O:���O���x���F�G���[<br>
     *                    ���b�Z�[�W�F"Mistake on validation definition file.
     *                    - maxByteLength is not number.
     *                    You'll have to check it over. ", new NumberFormatException()<br>
     *
     * <br>
     * var��maxByteLength�̒l�����l�ɕϊ��ł��Ȃ��ꍇ�A
     * ValidatorException���������邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateByteRange04() throws Exception {
        // �O����
        Var var = new Var();
        var.setName("maxByteLength");
        var.setValue("test");
        field.addVar(var);

        // �e�X�g���{
        // ����
        try {
            new FieldChecks().validateByteRange("test", va, field, errors);
            fail();
        } catch (ValidatorException e) {
            String message = "Mistake on validation definition file. " +
                    "- maxByteLength is not number. " +
                    "You'll have to check it over. ";
            assertEquals(message, e.getMessage());
            assertTrue(LogUTUtil.checkError(message, new NumberFormatException()));
        }
    }

    /**
     * testValidateByteRange05()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:"test"<br>
     *         (����) va:not null<br>
     *         (����) field:not null<br>
     *                minByteLength=null<br>
     *                maxByteLength=null<br>
     *         (����) errors:not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *         (��ԕω�) errors:�Ăяo����Ȃ�<br>
     *
     * <br>
     * var��minByteLength�AmaxByteLength��null�̏ꍇ�A
     * �͈͂�0�`Integer.MAX_LENGTH�Ƃ��ă`�F�b�N���s���邱�Ƃ��m�F����B<br>
     * �����E�l�e�X�g�͕����I�ɕs�\�Ȃ��ߏȗ�����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateByteRange05() throws Exception {
        // �e�X�g���{
        // ����
        assertTrue(new FieldChecks().validateByteRange("test", va, field, errors));

        // addErrors�m�F
        assertEquals(0, errors.addErrorCount);
    }

    /**
     * testValidateByteRange06()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:"test"<br>
     *         (����) va:not null<br>
     *         (����) field:not null<br>
     *                minByteLength=""<br>
     *                maxByteLength=""<br>
     *         (����) errors:not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *         (��ԕω�) errors:�Ăяo����Ȃ�<br>
     *
     * <br>
     * var��minByteLength�AmaxByteLength���󕶎��̏ꍇ�A
     * �͈͂�0�`Integer.MAX_LENGTH�Ƃ��ă`�F�b�N���s���邱�Ƃ��m�F����B<br>
     * �����E�l�e�X�g�͕����I�ɕs�\�Ȃ��ߏȗ�����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateByteRange06() throws Exception {
        // �O����
        Var var1 = new Var();
        var1.setName("minByteLength");
        var1.setValue("");
        field.addVar(var1);
        
        Var var2 = new Var();
        var2.setName("minByteLength");
        var2.setValue("");
        field.addVar(var2);

        // �e�X�g���{
        // ����
        assertTrue(new FieldChecks().validateByteRange("test", va, field, errors));

        // addErrors�m�F
        assertEquals(0, errors.addErrorCount);
    }

    /**
     * testValidateByteRange07()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FF,G
     * <br><br>
     * ���͒l�F(����) bean:"test"<br>
     *         (����) va:not null<br>
     *         (����) field:var:<br>
     *                encoding="test"<br>
     *         (����) errors:not null<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:ValidatorException<br>
     *                    ���b�Z�[�W�F"encoding[test] is not supported."<br>
     *         (��ԕω�) ���O:���O���x���F�G���[<br>
     *                    ���b�Z�[�W�F"encoding[test] is not supported."<br>
     *
     * <br>
     * var��encoding���T�|�[�g����Ȃ��G���R�[�f�B���O�̏ꍇ�A
     * ValidatorException���������邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateByteRange07() throws Exception {
        // �O����
        Var var = new Var();
        var.setName("encoding");
        var.setValue("test");
        field.addVar(var);

        // �e�X�g���{
        // ����
        try {
            new FieldChecks().validateByteRange("test", va, field, errors);
            fail();
        } catch (ValidatorException e) {
            String message = "encoding[test] is not supported.";
            assertEquals(message, e.getMessage());
            assertTrue(LogUTUtil.checkError(message));
        }
    }

    /**
     * testValidateByteRange08()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:"������"<br>
     *         (����) va:not null<br>
     *         (����) field:var:<br>
     *                minByteLength="6"<br>
     *                maxByteLength="6"<br>
     *                encoding=null<br>
     *         (����) errors:not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *         (��ԕω�) errors:�Ăяo����Ȃ�<br>
     *
     * <br>
     * var��encoding��null�̏ꍇ�A
     * �f�t�H���g�̃G���R�[�f�B���O�ŃG���R�[�h���s���邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateByteRange08() throws Exception {
        // �O����
        Var var1 = new Var();
        var1.setName("minByteLength");
        var1.setValue("6");
        field.addVar(var1);
        
        Var var2 = new Var();
        var2.setName("maxByteLength");
        var2.setValue("6");
        field.addVar(var2);

        // �e�X�g���{
        // ����
        assertTrue(new FieldChecks().validateByteRange("������", va, field, errors));

        // addErrors�m�F
        assertEquals(0, errors.addErrorCount);
    }

    /**
     * testValidateByteRange09()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:"������"<br>
     *         (����) va:not null<br>
     *         (����) field:var:<br>
     *                minByteLength="6"<br>
     *                maxByteLength="6"<br>
     *                encoding=""<br>
     *         (����) errors:not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *         (��ԕω�) errors:�Ăяo����Ȃ�<br>
     *
     * <br>
     * var��encoding���󕶎��̏ꍇ�A
     * �f�t�H���g�̃G���R�[�f�B���O�ŃG���R�[�h���s���邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateByteRange09() throws Exception {
        // �O����
        Var var1 = new Var();
        var1.setName("minByteLength");
        var1.setValue("6");
        field.addVar(var1);
        
        Var var2 = new Var();
        var2.setName("maxByteLength");
        var2.setValue("6");
        field.addVar(var2);
        
        Var var3 = new Var();
        var3.setName("encoding");
        var3.setValue("");
        field.addVar(var3);

        // �e�X�g���{
        // ����
        assertTrue(new FieldChecks().validateByteRange("������", va, field, errors));

        // addErrors�m�F
        assertEquals(0, errors.addErrorCount);
    }

    /**
     * testValidateByteRange10()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:"������"<br>
     *         (����) va:not null<br>
     *         (����) field:var:<br>
     *                minByteLength="7"<br>
     *                maxByteLength="10"<br>
     *                encoding="UTF-8"<br>
     *         (����) errors:not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *         (��ԕω�) errors:�Ăяo����Ȃ�<br>
     *
     * <br>
     * bean�̒l���w�肵���G���R�[�f�B���O�Ŕ͈͓��ł���ꍇ�A
     * true���擾�ł��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateByteRange10() throws Exception {
        // �O����
        Var var1 = new Var();
        var1.setName("minByteLength");
        var1.setValue("7");
        field.addVar(var1);
        
        Var var2 = new Var();
        var2.setName("maxByteLength");
        var2.setValue("10");
        field.addVar(var2);
        
        Var var3 = new Var();
        var3.setName("encoding");
        var3.setValue("UTF-8");
        field.addVar(var3);

        // �e�X�g���{
        // ����
        assertTrue(new FieldChecks().validateByteRange("������", va, field, errors));

        // addErrors�m�F
        assertEquals(0, errors.addErrorCount);
    }

    /**
     * testValidateByteRange11()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:"������"<br>
     *         (����) va:not null<br>
     *         (����) field:var:<br>
     *                minByteLength="7"<br>
     *                maxByteLength="10"<br>
     *                encoding="Windows-31J"<br>
     *         (����) errors:not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:false<br>
     *         (��ԕω�) errors:bean,field,va�������Ƃ���
     *                           addErrors���Ăяo�����B<br>
     *
     * <br>
     * bean�̒l��var��encoding�ŃG���R�[�h�����o�C�g����
     * minByteLength�̒l��菬�����ꍇ�A
     * �G���[��ǉ�����false���ԋp����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateByteRange11() throws Exception {
        // �O����
        Var var1 = new Var();
        var1.setName("minByteLength");
        var1.setValue("7");
        field.addVar(var1);
        
        Var var2 = new Var();
        var2.setName("maxByteLength");
        var2.setValue("10");
        field.addVar(var2);
        
        Var var3 = new Var();
        var3.setName("encoding");
        var3.setValue("Windows-31J");
        field.addVar(var3);

        // �e�X�g���{
        // ����
        assertFalse(new FieldChecks().validateByteRange("������", va, field, errors));

        // addErrors�m�F
        assertEquals(1, errors.addErrorCount);
        assertEquals("������", errors.beanList.get(0));
        assertSame(va, errors.vaList.get(0));
        assertSame(field, errors.fieldList.get(0));
    }

    /**
     * testValidateByteRange12()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:"������"<br>
     *         (����) va:not null<br>
     *         (����) field:var:<br>
     *                minByteLength="5"<br>
     *                maxByteLength="8"<br>
     *                encoding="UTF-8"<br>
     *         (����) errors:not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:false<br>
     *         (��ԕω�) errors:bean,field,va�������Ƃ���
     *                           addErrors���Ăяo�����B<br>
     *
     * <br>
     * bean�̒l��var��encoding�ŃG���R�[�h�����o�C�g����
     * maxByteLength�̒l���傫���ꍇ�A
     * �G���[��ǉ�����false���ԋp����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateByteRange12() throws Exception {
        // �O����
        Var var1 = new Var();
        var1.setName("minByteLength");
        var1.setValue("5");
        field.addVar(var1);
        
        Var var2 = new Var();
        var2.setName("maxByteLength");
        var2.setValue("8");
        field.addVar(var2);
        
        Var var3 = new Var();
        var3.setName("encoding");
        var3.setValue("UTF-8");
        field.addVar(var3);

        // �e�X�g���{
        // ����
        assertFalse(new FieldChecks().validateByteRange("������", va, field, errors));

        // addErrors�m�F
        assertEquals(1, errors.addErrorCount);
        assertEquals("������", errors.beanList.get(0));
        assertSame(va, errors.vaList.get(0));
        assertSame(field, errors.fieldList.get(0));
    }

}
