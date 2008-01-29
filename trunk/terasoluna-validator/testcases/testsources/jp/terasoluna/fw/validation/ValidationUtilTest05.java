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
public class ValidationUtilTest05 extends PropertyTestCase {

    /**
     * ���̃e�X�g�P�[�X�����s����ׂ�
     * GUI �A�v���P�[�V�������N������B
     *
     * @param args java �R�}���h�ɐݒ肳�ꂽ�p�����[�^
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(ValidationUtilTest05.class);
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
    public ValidationUtilTest05(String name) {
        super(name);
    }

    /**
     * testIsByteInRange01()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) value:null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *
     * <br>
     * ����value��null�̏ꍇ
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testIsByteInRange01() throws Exception {
        // �O����
        String value = null;
        String encoding = null;
        int min = 0;
        int max = 0;

        // �e�X�g���{
        boolean result = ValidationUtil.isByteInRange(
                value, encoding, min, max);

        // ����
        assertTrue(result);
    }

    /**
     * testIsByteInRange02()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) value:""<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *
     * <br>
     * ����value���󔒂̏ꍇ
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testIsByteInRange02() throws Exception {
        // �O����
        String value = "";
        String encoding = null;
        int min = 0;
        int max = 0;

        // �e�X�g���{
        boolean result = ValidationUtil.isByteInRange(
                value, encoding, min, max);

        // ����
        assertTrue(result);
    }

    /**
     * testIsByteInRange03()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) value:"abc"<br>
     *         (����) encoding:null<br>
     *         (����) min:0<br>
     *         (����) max:10<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *
     * <br>
     * ����value��NotNull�Aencoding��null�ŁA����ɒ������Ƃ�A�w��͈͓��̏ꍇ
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testIsByteInRange03() throws Exception {
        // �O����
        String value = "abc";
        String encoding = null;
        int min = 0;
        int max = 10;

        // �e�X�g���{
        boolean result = ValidationUtil.isByteInRange(
                value, encoding, min, max);

        // ����
        assertTrue(result);
    }

    /**
     * testIsByteInRange04()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) value:"abc"<br>
     *         (����) encoding:""<br>
     *         (����) min:5<br>
     *         (����) max:10<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:false<br>
     *
     * <br>
     * ����value��NotNull�Aencoding���󔒂ŁA����ɒ������Ƃ�A�w��͈͊O�̏ꍇ
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testIsByteInRange04() throws Exception {
        // �O����
        String value = "abc";
        String encoding = "";
        int min = 5;
        int max = 10;

        // �e�X�g���{
        boolean result = ValidationUtil.isByteInRange(
                value, encoding, min, max);

        // ����
        assertFalse(result);
    }

    /**
     * testIsByteInRange05()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) value:"abc"<br>
     *         (����) encoding:"UTF-8"<br>
     *         (����) min:3<br>
     *         (����) max:3<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *
     * <br>
     * ����value��NotNull�Aencoding��NotNull�ŁA����ɒ������Ƃ�A�w��͈͓��̏ꍇ
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testIsByteInRange05() throws Exception {
        // �O����
        String value = "abc";
        String encoding = "UTF-8";
        int min = 3;
        int max = 3;

        // �e�X�g���{
        boolean result = ValidationUtil.isByteInRange(
                value, encoding, min, max);

        // ����
        assertTrue(result);
    }

    /**
     * testIsByteInRange06()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) value:"abc"<br>
     *         (����) encoding:"aaa"<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:��O�FIllegalArgumentException<br>
     *
     * <br>
     * ����value��NotNull�Aencoding���s���ȕ�����̏ꍇ
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testIsByteInRange06() throws Exception {
        // �O����
        String value = "abc";
        String encoding = "aaa";
        int min = 5;
        int max = 10;

        // �e�X�g���{
        try {
            ValidationUtil.isByteInRange(
                    value, encoding, min, max);
            fail("��O���������Ȃ��B");
        } catch (IllegalArgumentException e) {
            // ����
            //��O�����������OK�B
            assertNotNull(e);
        }
    }

    /**
     * testIsDateInRange01()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) value:null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *
     * <br>
     * ����value��null�̏ꍇ
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testIsDateInRange01() throws Exception {
        // �O����
        String value = null;
        String startDateStr = null;
        String endDateStr = null;
        String datePattern = null;
        String datePatternStrict = null;

        // �e�X�g���{
        boolean result = ValidationUtil.isDateInRange(value, startDateStr,
                endDateStr,datePattern, datePatternStrict);

        // ����
        assertTrue(result);
    }

    /**
     * testIsDateInRange02()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) value:""<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *
     * <br>
     * ����value���󔒂̏ꍇ
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testIsDateInRange02() throws Exception {
        // �O����
        String value = "";
        String startDateStr = null;
        String endDateStr = null;
        String datePattern = null;
        String datePatternStrict = null;

        // �e�X�g���{
        boolean result = ValidationUtil.isDateInRange(value, startDateStr,
                endDateStr,datePattern, datePatternStrict);

        // ����
        assertTrue(result);
    }

    /**
     * testIsDateInRange03()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) value:"2005/11/1"<br>
     *         (����) datePattern:null<br>
     *         (����) datePatternStrict:"yyyy/MM/dd"<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:false<br>
     *
     * <br>
     * ����value������ɓ��t�ɕϊ��ł��Ȃ��ꍇ
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testIsDateInRange03() throws Exception {
        // �O����
        String value = "2005/11/1";
        String startDateStr = null;
        String endDateStr = null;
        String datePattern = null;
        String datePatternStrict = "yyyy/MM/dd";

        // �e�X�g���{
        boolean result = ValidationUtil.isDateInRange(value, startDateStr,
                endDateStr,datePattern, datePatternStrict);

        // ����
        assertFalse(result);
    }

    /**
     * testIsDateInRange04()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) value:"2005/11/01"<br>
     *         (����) startDateStr:null<br>
     *         (����) endDateStr:null<br>
     *         (����) datePattern:null<br>
     *         (����) datePatternStrict:"yyyy/MM/dd"<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *
     * <br>
     * ����startDateStr�AendDateStr��null�̏ꍇ
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testIsDateInRange04() throws Exception {
        // �O����
        String value = "2005/11/01";
        String startDateStr = null;
        String endDateStr = null;
        String datePattern = null;
        String datePatternStrict = "yyyy/MM/dd";

        // �e�X�g���{
        boolean result = ValidationUtil.isDateInRange(value, startDateStr,
                endDateStr,datePattern, datePatternStrict);

        // ����
        assertTrue(result);
    }

    /**
     * testIsDateInRange05()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) value:"2005/11/1"<br>
     *         (����) startDateStr:""<br>
     *         (����) endDateStr:""<br>
     *         (����) datePattern:"yyyy/MM/dd"<br>
     *         (����) datePatternStrict:null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *
     * <br>
     * ����startDateStr�AendDateStr���󔒂̏ꍇ
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testIsDateInRange05() throws Exception {
        // �O����
        String value = "2005/11/1";
        String startDateStr = "";
        String endDateStr = "";
        String datePattern = "yyyy/MM/dd";
        String datePatternStrict = null;

        // �e�X�g���{
        boolean result = ValidationUtil.isDateInRange(value, startDateStr,
                endDateStr,datePattern, datePatternStrict);

        // ����
        assertTrue(result);
    }

    /**
     * testIsDateInRange06()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) value:"2005/11/01"<br>
     *         (����) startDateStr:"2005/12/1"<br>
     *         (����) endDateStr:null<br>
     *         (����) datePattern:null<br>
     *         (����) datePatternStrict:"yyyy/MM/dd"<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:-<br>
     *         (��ԕω�) ��O:IllegalArgumentException<br>
     *                    ���b�Z�[�W�F"startDate is unparseable[2005/12/1]"<br>
     *
     * <br>
     * ����startDateStr��NotNull�����A���t�ɕϊ��ł��Ȃ��ꍇ
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testIsDateInRange06() throws Exception {
        // �O����
        String value = "2005/11/01";
        String startDateStr = "2005/12/1";
        String endDateStr = null;
        String datePattern = null;
        String datePatternStrict = "yyyy/MM/dd";

        // �e�X�g���{
        try {
            ValidationUtil.isDateInRange(value, startDateStr,
                    endDateStr,datePattern, datePatternStrict);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("startDate is unparseable[2005/12/1]", e.getMessage());
        }
    }

    /**
     * testIsDateInRange07()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) value:"2005/11/1"<br>
     *         (����) startDateStr:"2005/12/1"<br>
     *         (����) endDateStr:null<br>
     *         (����) datePattern:"yyyy/MM/dd"<br>
     *         (����) datePatternStrict:null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:false<br>
     *
     * <br>
     * ����startDateStr������ɓ��t�ɕϊ��ł������Avalue��startDateStr���ȑO�������ꍇ
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testIsDateInRange07() throws Exception {
        // �O����
        String value = "2005/11/1";
        String startDateStr = "2005/12/1";
        String endDateStr = null;
        String datePattern = "yyyy/MM/dd";
        String datePatternStrict = null;

        // �e�X�g���{
        boolean result = ValidationUtil.isDateInRange(value, startDateStr,
                endDateStr,datePattern, datePatternStrict);

        // ����
        assertFalse(result);
    }

    /**
     * testIsDateInRange08()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) value:"2005/11/01"<br>
     *         (����) startDateStr:null<br>
     *         (����) endDateStr:"2005/10/1"<br>
     *         (����) datePattern:null<br>
     *         (����) datePatternStrict:"yyyy/MM/dd"<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:-<br>
     *         (��ԕω�) ��O:IllegalArgumentException<br>
     *                    ���b�Z�[�W�F"endDate is unparseable[2005/10/1]"<br>
     *
     * <br>
     * ����endDateStr��NotNull�����A���t�ɕϊ��ł��Ȃ��ꍇ
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testIsDateInRange08() throws Exception {
        // �O����
        String value = "2005/11/01";
        String startDateStr = null;
        String endDateStr = "2005/10/1";
        String datePattern = null;
        String datePatternStrict = "yyyy/MM/dd";

        // �e�X�g���{
        try {
            ValidationUtil.isDateInRange(value, startDateStr,
                    endDateStr,datePattern, datePatternStrict);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("endDate is unparseable[2005/10/1]", e.getMessage());
        }
    }

    /**
     * testIsDateInRange09()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) value:"2005/11/1"<br>
     *         (����) startDateStr:null<br>
     *         (����) endDateStr:"2005/10/1"<br>
     *         (����) datePattern:"yyyy/MM/dd"<br>
     *         (����) datePatternStrict:null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:false<br>
     *
     * <br>
     * ����endDateStr������ɓ��t�ɕϊ��ł������Avalue��endDateStr���Ȍゾ�����ꍇ
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testIsDateInRange09() throws Exception {
        // �O����
        String value = "2005/11/1";
        String startDateStr = null;
        String endDateStr = "2005/10/1";
        String datePattern = "yyyy/MM/dd";
        String datePatternStrict = null;

        // �e�X�g���{
        boolean result = ValidationUtil.isDateInRange(value, startDateStr,
                endDateStr,datePattern, datePatternStrict);

        // ����
        assertFalse(result);
    }

    /**
     * testIsDateInRange10()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) value:"2005/11/1"<br>
     *         (����) startDateStr:"2005/10/1"<br>
     *         (����) endDateStr:"2005/12/1"<br>
     *         (����) datePattern:"yyyy/MM/dd"<br>
     *         (����) datePatternStrict:null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *
     * <br>
     * ����value�AstartDateStr�AendDateStr���ׂĐ���ɓ��t�ɕϊ��ł��Avalue��startDateStr��endDateStr�̊Ԃ̓��t�̏ꍇ
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testIsDateInRange10() throws Exception {
        // �O����
        String value = "2005/11/1";
        String startDateStr = "2005/10/1";
        String endDateStr = "2005/12/1";
        String datePattern = "yyyy/MM/dd";
        String datePatternStrict = null;

        // �e�X�g���{
        boolean result = ValidationUtil.isDateInRange(value, startDateStr,
                endDateStr,datePattern, datePatternStrict);

        // ����
        assertTrue(result);
    }

    /**
     * testIsDateInRange11()
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
     * datePattern�AdatePatternStrict��null�̏ꍇ�AIllegalArgumentException���������邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testIsDateInRange11() throws Exception {
        // �O����
        String value = "2005/11/1";
        String startDateStr = "2005/10/1";
        String endDateStr = "2005/12/1";
        String datePattern = null;
        String datePatternStrict = null;

        // �e�X�g���{
        try {
            ValidationUtil.isDateInRange(value, startDateStr,
                    endDateStr,datePattern, datePatternStrict);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("datePattern or datePatternStrict must be specified.",
                    e.getMessage());
        }
    }

    /**
     * testIsDateInRange12()
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
     * datePattern�AdatePatternStrict���󕶎��̏ꍇ�AIllegalArgumentException���������邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testIsDateInRange12() throws Exception {
        // �O����
        String value = "2005/11/1";
        String startDateStr = "2005/10/1";
        String endDateStr = "2005/12/1";
        String datePattern = "";
        String datePatternStrict = "";

        // �e�X�g���{
        try {
            ValidationUtil.isDateInRange(value, startDateStr,
                    endDateStr,datePattern, datePatternStrict);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("datePattern or datePatternStrict must be specified.",
                    e.getMessage());
        }
    }

    /**
     * testIsDateInRange13()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FF,G
     * <br><br>
     * ���͒l�F(����) value:"2005/11/1"<br>
     *         (����) datePattern:"abc"<br>
     *         (����) datePatternStrict:null<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalArgumentException<br>
     *                    ���b�Z�[�W�F<br>
     *                    "Illegal pattern character 'b'"<br>
     *
     * <br>
     * datePattern���s���ȏꍇ�AIllegalArgumentException���������邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testIsDateInRange13() throws Exception {
        // �O����
        String value = "2005/11/1";
        String startDateStr = "2005/10/1";
        String endDateStr = "2005/12/1";
        String datePattern = "abc";
        String datePatternStrict = null;

        // �e�X�g���{
        try {
            ValidationUtil.isDateInRange(value, startDateStr,
                    endDateStr,datePattern, datePatternStrict);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("Illegal pattern character 'b'",
                    e.getMessage());
        }
    }

    /**
     * testIsDateInRange14()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FF,G
     * <br><br>
     * ���͒l�F(����) value:"2005/11/1"<br>
     *         (����) datePattern:null<br>
     *         (����) datePatternStrict:"abc"<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalArgumentException<br>
     *                    ���b�Z�[�W�F<br>
     *                    "Illegal pattern character 'b'"<br>
     *
     * <br>
     * datePatternStrict���s���ȏꍇ�AIllegalArgumentException���������邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testIsDateInRange14() throws Exception {
        // �O����
        String value = "2005/11/1";
        String startDateStr = "2005/10/1";
        String endDateStr = "2005/12/1";
        String datePattern = null;
        String datePatternStrict = "abc";

        // �e�X�g���{
        try {
            ValidationUtil.isDateInRange(value, startDateStr,
                    endDateStr,datePattern, datePatternStrict);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("Illegal pattern character 'b'",
                    e.getMessage());
        }
    }

    /**
     * testIsDateInRange15()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FF,G
     * <br><br>
     * ���͒l�F(����) value:"2005/11/1"<br>
     *         (����) startDateStr:"test"<br>
     *         (����) datePattern:"yyyy/MM/dd"<br>
     *         (����) datePatternStrict:null<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalArgumentException<br>
     *                    ���b�Z�[�W�F"startDate is unparseable[test]"<br>
     *
     * <br>
     * startDateStr�����t�ɕϊ��ł��Ȃ��ꍇ�AIllegalArgumentException���������邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testIsDateInRange15() throws Exception {
        // �O����
        String value = "2005/11/1";
        String startDateStr = "test";
        String endDateStr = null;
        String datePattern = "yyyy/MM/dd";
        String datePatternStrict = null;

        // �e�X�g���{
        try {
            ValidationUtil.isDateInRange(value, startDateStr,
                    endDateStr,datePattern, datePatternStrict);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("startDate is unparseable[test]",
                    e.getMessage());
        }
    }

    /**
     * testIsDateInRange16()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FF,G
     * <br><br>
     * ���͒l�F(����) value:"2005/11/1"<br>
     *         (����) endDateStr:"test"<br>
     *         (����) datePattern:"yyyy/MM/dd"<br>
     *         (����) datePatternStrict:null<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalArgumentException<br>
     *                    ���b�Z�[�W�F"endDate is unparseable[test]"<br>
     *
     * <br>
     * endDateStr�����t�ɕϊ��ł��Ȃ��ꍇ�AIllegalArgumentException���������邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testIsDateInRange16() throws Exception {
        // �O����
        String value = "2005/11/1";
        String startDateStr = null;
        String endDateStr = "test";
        String datePattern = "yyyy/MM/dd";
        String datePatternStrict = null;

        // �e�X�g���{
        try {
            ValidationUtil.isDateInRange(value, startDateStr,
                    endDateStr,datePattern, datePatternStrict);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("endDate is unparseable[test]",
                    e.getMessage());
        }
    }

    /**
     * testIsDateInRange17()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FF,G
     * <br><br>
     * ���͒l�F(����) value:"2005/11/1"<br>
     *         (����) startDateStr:"2005/11/1"<br>
     *         (����) endDateStr:"2005/11/1"<br>
     *         (����) datePattern:"yyyy/MM/dd"<br>
     *         (����) datePatternStrict:null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *
     * <br>
     * ���͒l��startDateStr�AendDateStr�̓��t���������ꍇ�Atrue���擾�ł��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testIsDateInRange17() throws Exception {
        // �O����
        String value = "2005/11/1";
        String startDateStr = "2005/11/1";
        String endDateStr = "2005/11/1";
        String datePattern = "yyyy/MM/dd";
        String datePatternStrict = null;

        // �e�X�g���{
        boolean result = ValidationUtil.isDateInRange(value, startDateStr,
                    endDateStr,datePattern, datePatternStrict);

        // ����
        assertTrue(result);
    }

    /**
     * testIsDateInRange18()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) value:"test"<br>
     *         (����) startDateStr:"2005/1/1"<br>
     *         (����) endDateStr:"2005/12/31"<br>
     *         (����) datePattern:"yyyy/MM/dd"<br>
     *         (����) datePatternStrict:null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:false<br>
     *
     * <br>
     * ���͒l�����t�ɕϊ��ł��Ȃ��ꍇ�Afalse���擾�ł��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testIsDateInRange18() throws Exception {
        // �O����
        String value = "test";
        String startDateStr = "2005/11/1";
        String endDateStr = "2005/12/31";
        String datePattern = "yyyy/MM/dd";
        String datePatternStrict = null;

        // �e�X�g���{
        boolean result = ValidationUtil.isDateInRange(value, startDateStr,
                    endDateStr,datePattern, datePatternStrict);

        // ����
        assertFalse(result);
    }

}
