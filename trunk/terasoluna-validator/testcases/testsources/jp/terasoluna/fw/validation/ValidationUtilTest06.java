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

import java.text.SimpleDateFormat;
import java.util.Date;
import jp.terasoluna.utlib.PropertyTestCase;
import jp.terasoluna.fw.validation.ValidationUtil;

/**
 * {@link jp.terasoluna.fw.validation.ValidationUtil} �N���X�̃u���b�N�{�b�N�X�e�X�g�B
 *
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4>
 * ���؃��W�b�N�̃��[�e�B���e�B�N���X�B
 * <p>
 *
 * @see jp.terasoluna.fw.validation.ValidationUtil
 */
public class ValidationUtilTest06 extends PropertyTestCase {

    /**
     * ���̃e�X�g�P�[�X�����s����ׂ�
     * GUI �A�v���P�[�V�������N������B
     *
     * @param args java �R�}���h�ɐݒ肳�ꂽ�p�����[�^
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(ValidationUtilTest06.class);
    }

    /**
     * �������������s���B
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     * @see jp.terasoluna.utlib.spring.PropertyTestCase#setUpData()
     */
    @Override
    protected void setUpData() throws Exception {
    }

    /**
     * �I���������s���B
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     * @see jp.terasoluna.utlib.spring.PropertyTestCase#cleanUpData()
     */
    @Override
    protected void cleanUpData() throws Exception {
    }

    /**
     * �R���X�g���N�^�B
     *
     * @param name ���̃e�X�g�P�[�X�̖��O�B
     */
    public ValidationUtilTest06(String name) {
        super(name);
    }

    /**
     * testToDate01()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) value:null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) Date:null<br>
     *
     * <br>
     * ����value��null�̏ꍇ
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testToDate01() throws Exception {
        // �O����
        String value = null;
        String datePattern = "yyyy/MM/dd";
        String datePatternStrict = null;

        // �e�X�g���{
        Date result = ValidationUtil.toDate(
                value, datePattern, datePatternStrict);

        // ����
        assertNull(result);
    }

    /**
     * testToDate02()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) value:""<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) Date:null<br>
     *
     * <br>
     * ����value���󔒂̏ꍇ
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testToDate02() throws Exception {
        // �O����
        String value = "";
        String datePattern = "yyyy/MM/dd";
        String datePatternStrict = null;

        // �e�X�g���{
        Date result = ValidationUtil.toDate(
                value, datePattern, datePatternStrict);

        // ����
        assertNull(result);
    }

    /**
     * testToDate03()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FF,G
     * <br><br>
     * ���͒l�F(����) value:"2005/11/1"<br>
     *         (����) datePattern:null<br>
     *         (����) datePatternStrict:null<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalArgumentException<br>
     *                    ���b�Z�[�W�F"datePattern or datePatternStrict must be specified."<br>
     *
     * <br>
     * ����datePattern�AdatePatternStrict��null�̏ꍇ
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testToDate03() throws Exception {
        // �O����
        String value = "2005/11/1";
        String datePattern = null;
        String datePatternStrict = null;

        // �e�X�g���{
        try {
            ValidationUtil.toDate(value, datePattern, datePatternStrict);
            fail("��O���������Ȃ��B");
        } catch (Exception e) {
            //����
            assertEquals("datePattern or datePatternStrict must be specified.",
                    e.getMessage());
        }
    }

    /**
     * testToDate04()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FF,G
     * <br><br>
     * ���͒l�F(����) value:"2005/11/1"<br>
     *         (����) datePattern:""<br>
     *         (����) datePatternStrict:""<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalArgumentException<br>
     *                    ���b�Z�[�W�F"datePattern or datePatternStrict must be specified."<br>
     *
     * <br>
     * ����datePattern�AdatePatternStrict���󔒂̏ꍇ
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testToDate04() throws Exception {
        // �O����
        String value = "2005/11/1";
        String datePattern = "";
        String datePatternStrict = "";

        // �e�X�g���{
        try {
            ValidationUtil.toDate(value, datePattern, datePatternStrict);
            fail("��O���������Ȃ��B");
        } catch (Exception e) {
            //����
            assertEquals("datePattern or datePatternStrict must be specified.",
                    e.getMessage());
        }
    }

    /**
     * testToDate05()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) value:"2005/11/1"<br>
     *         (����) datePattern:"yyyy/MM/dd"<br>
     *         (����) datePatternStrict:"yyyy.MM.dd"<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) Date:"2005/11/1"��Date�^<br>
     *
     * <br>
     * ����datePattern,datePatternStrict��NotNull�Ő����Date�ɕϊ��ł���ꍇ
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testToDate05() throws Exception {
        // �O����
        String value = "2005/11/1";
        String datePattern = "yyyy/MM/dd";
        String datePatternStrict = "yyyy.MM.dd";

        // �e�X�g���{
        Date result = ValidationUtil.toDate(
                value, datePattern, datePatternStrict);

        // ����
        SimpleDateFormat format = new SimpleDateFormat(datePattern);
        Date hope = format.parse(value);
        assertEquals(hope, result);
        assertEquals("2005/11/01", format.format(result));
    }

    /**
     * testToDate06()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) value:"2005/11/1"<br>
     *         (����) datePattern:null<br>
     *         (����) datePatternStrict:"yyyy/MM/dd"<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) Date:null<br>
     *
     * <br>
     * ����datePatternStrict��NotNull��Date�ɕϊ��ł����Anull�ƂȂ�ꍇ
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testToDate06() throws Exception {
        // �O����
        String value = "2005/11/1";
        String datePattern = null;
        String datePatternStrict = "yyyy/MM/dd";

        // �e�X�g���{
        Date result = ValidationUtil.toDate(
                value, datePattern, datePatternStrict);

        // ����
        assertNull(result);
    }

    /**
     * testToDate07()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) value:"2005/11/24"<br>
     *         (����) datePattern:"yyyy/MM/dd"<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) Date:"2005/11/24"��Date�^<br>
     *
     * <br>
     * ����datePattern��NotNull�Ő����Date�ɕϊ��ł���ꍇ
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testToDate07() throws Exception {
        // �O����
        String value = "2005/11/24";
        String datePattern = "yyyy/MM/dd";
        String datePatternStrict = null;

        // �e�X�g���{
        Date result = ValidationUtil.toDate(
                value, datePattern, datePatternStrict);

        // ����
        SimpleDateFormat format = new SimpleDateFormat(datePattern);
        Date hope = format.parse(value);
        assertEquals(hope, result);
        assertEquals(value, format.format(result));
    }

    /**
     * testToDate08()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) value:"2005/11/24"<br>
     *         (����) datePattern:null<br>
     *         (����) datePatternStrict:"yyyy/MM/dd"<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) Date:"2005/11/24"��Date�^<br>
     *
     * <br>
     * ����datePatternStrict��NotNull�Ő����Date�ɕϊ��ł���ꍇ
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testToDate08() throws Exception {
        // �O����
        String value = "2005/11/24";
        String datePattern = null;
        String datePatternStrict = "yyyy/MM/dd";

        // �e�X�g���{
        Date result = ValidationUtil.toDate(
                value, datePattern, datePatternStrict);

        // ����
        SimpleDateFormat format = new SimpleDateFormat(datePatternStrict);
        Date hope = format.parse(value);
        assertEquals(hope, result);
        assertEquals(value, format.format(result));
    }

    /**
     * testToDate09()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FF,G
     * <br><br>
     * ���͒l�F(����) value:"2005/11/24"<br>
     *         (����) datePattern:"asdf"<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:��O�FIllegalArgumentException<br>
     *                    ���b�Z�[�W�FIllegal pattern character 'f'<br>
     *
     * <br>
     * datePattern�ɖ����ȕ����񂪂���ꍇ
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testToDate09() throws Exception {
        // �O����
        String value = "2005/11/24";
        String datePattern = "asdf";
        String datePatternStrict = null;

        // �e�X�g���{
        try {
            ValidationUtil.toDate(value, datePattern, datePatternStrict);
            fail("��O���������Ȃ��B");
        } catch (IllegalArgumentException e) {
            assertEquals("Illegal pattern character 'f'", e.getMessage());
        }
    }

    /**
     * testToDate10()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FF,G
     * <br><br>
     * ���͒l�F(����) value:"2005/11/24"<br>
     *         (����) datePattern:null<br>
     *         (����) datePatternStrict:"asdf"<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:��O�FIllegalArgumentException<br>
     *                    ���b�Z�[�W�FIllegal pattern character 'f'<br>
     *
     * <br>
     * datePatternStrict�ɖ����ȕ����񂪂���ꍇ
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testToDate10() throws Exception {
        // �O����
        String value = "2005/11/24";
        String datePattern = null;
        String datePatternStrict = "asdf";

        // �e�X�g���{
        try {
            ValidationUtil.toDate(value, datePattern, datePatternStrict);
            fail("��O���������Ȃ��B");
        } catch (IllegalArgumentException e) {
            assertEquals("Illegal pattern character 'f'", e.getMessage());
        }
    }

    /**
     * testToDate11()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) value:"2005/2/29"<br>
     *         (����) datePattern:"yyyy/MM/dd"<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) Date:null<br>
     *
     * <br>
     * ���͒l�����݂��Ȃ����t�̏ꍇ
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testToDate11() throws Exception {
        // �O����
        String value = "2005/2/29";
        String datePattern = "yyyy/MM/dd";
        String datePatternStrict = null;

        // �e�X�g���{
        Date result = ValidationUtil.toDate(
                value, datePattern, datePatternStrict);

        // ����
        assertNull(result);
    }

    /**
     * testToDate12()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) value:"2005/02/29"<br>
     *         (����) datePattern:null<br>
     *         (����) datePatternStrict:"yyyy/MM/dd"<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) Date:null<br>
     *
     * <br>
     * ���͒l�����݂��Ȃ����t�̏ꍇ
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testToDate12() throws Exception {
        // �O����
        String value = "2005/02/29";
        String datePattern = null;
        String datePatternStrict = "yyyy/MM/dd";

        // �e�X�g���{
        Date result = ValidationUtil.toDate(
                value, datePattern, datePatternStrict);

        // ����
        assertNull(result);
    }

    /**
     * testToDate13()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) value:"test"<br>
     *         (����) datePattern:"yyyy/MM/dd"<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) Date:null<br>
     *
     * <br>
     * ���͒l�����t�ɕϊ��ł��Ȃ��ꍇ
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testToDate13() throws Exception {
        // �O����
        String value = "test";
        String datePattern = "yyyy/MM/dd";
        String datePatternStrict = null;

        // �e�X�g���{
        Date result = ValidationUtil.toDate(
                value, datePattern, datePatternStrict);

        // ����
        assertNull(result);
    }

}
