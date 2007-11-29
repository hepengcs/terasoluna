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

package jp.terasoluna.fw.exception;

import jp.terasoluna.utlib.UTUtil;
import junit.framework.TestCase;

/**
 * SystemException �u���b�N�{�b�N�X�e�X�g�B<br>
 *
 *
 * @version 2004/04/21
 */

public class SystemExceptionTest extends TestCase {

    /**
      * �e�X�g�pSystemException�t�B�[���h�B
      */
    private SystemException se1 = null;

    /**
     * SystemException���e�X�g����ۂɍs�������������B
     * @see TestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    /**
     * @see TestCase#tearDown()
     */
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * testSystemExceptionThrowable01()<br>
     *
     * (����n)<br>
     * �ϓ_�FA<br>
     *
     * ���͒l�Fcause = not null<br>
     * ���Ғl�Fcause = not null, errorCode = ""���ݒ肳���B<br>
     * 
     * �T�v�F����cause��NotNull�ŁA���b�Z�[�W������̏ꍇ
     * @throws Exception ��O
     */
    public void testSystemExceptionThrowable01() throws Exception {

        // ���͒l�̐ݒ�B
        Throwable cause = new Throwable("testException");
        // �e�X�g�ΏۃR���X�g���N�^�̎��s�B
        se1 = new SystemException(cause);

        // �o�͒l�̊m�F�B
        String throwWord =
            ((Throwable) UTUtil.getPrivateField(se1, "cause")).getMessage();
        String errorCode = (String) UTUtil.getPrivateField(se1, "errorCode");
        assertEquals("testException", throwWord);
        assertEquals("", errorCode);

    }

    /**
     * testSystemExceptionThrowable02()<br>
     *
     * (����n)<br>
     * �ϓ_�FC<br>
     *
     * ���͒l�Fcause = ""<br>
     * ���Ғl�Fcause = "", errorCode = ""���ݒ肳���B<br>
     * 
     * �T�v�F����cause��NotNull�ŁA���b�Z�[�W���󔒂̏ꍇ
     * @throws Exception ��O
     */
    public void testSystemExceptionThrowable02() throws Exception {
        // ���͒l�̐ݒ�B
        Throwable cause = new Throwable("");

        // �e�X�g�ΏۃR���X�g���N�^�̎��s�B
        se1 = new SystemException(cause);

        // �o�͒l�̊m�F�B
        String throwWord =
            ((Throwable) UTUtil.getPrivateField(se1, "cause")).getMessage();
        String errorCode = (String) UTUtil.getPrivateField(se1, "errorCode");
        assertEquals("", throwWord);
        assertEquals("", errorCode);
    }

    /**
     * testSystemExceptionThrowable03()<br>
     *
     * (����n)<br>
     * �ϓ_�FC<br>
     *
     * ���͒l�Fcause = null<br>
     * ���Ғl�Fcause = null, errorCode = ""���ݒ肳���B<br>
     * 
     * �T�v�F����cause��null�̏ꍇ
     * @throws Exception ��O
     */
    public void testSystemExceptionThrowable03() throws Exception {

        // �e�X�g�ΏۃR���X�g���N�^�̎��s�B
        se1 = new SystemException(null);

        // �o�͒l�̊m�F�B
        Throwable throWord = (Throwable) UTUtil.getPrivateField(se1, "cause");
        String errorCode = (String) UTUtil.getPrivateField(se1, "errorCode");
        assertNull(throWord);
        assertEquals("", errorCode);
    }

    /**
     * testSystemExceptionThrowableString01()<br>
     *
     * (����n)<br>
     * �ϓ_�FA<br>
     *
     * ���͒l�Fcause = not null, errorCode = not null<br>
     * ���Ғl�Fcause = not null, errorCode = not null���ݒ肳���B<br>
     * 
     * �T�v�F����cause��NotNull�Ń��b�Z�[�W������A����errorCode��"test01"�̏ꍇ
     * @throws Exception ��O
     */
    public void testSystemExceptionThrowableString01() throws Exception {
        // ���͒l�̐ݒ�B
        Throwable cause = new Throwable("testException");
        String errorCode = "test01";

        // �e�X�g�ΏۃR���X�g���N�^�̎��s�B
        se1 = new SystemException(cause, errorCode);

        // �o�͒l�̊m�F�B
        String throwWord =
            ((Throwable) UTUtil.getPrivateField(se1, "cause")).getMessage();
        errorCode = (String) UTUtil.getPrivateField(se1, "errorCode");
        assertEquals("testException", throwWord);
        assertEquals("test01", errorCode);
    }

    /**
     * testSystemExceptionThrowableString02()<br>
     *
     * (����n)<br>
     * �ϓ_�FC<br>
     *
     * ���͒l�Fcause = not null, errorCode = ""<br>
     * ���Ғl�Fcause = not null, errorCode = ""���ݒ肳���B<br>
     * 
     * �T�v�F����cause��NotNull�Ń��b�Z�[�W������A����errorCode���󔒂̏ꍇ
     * @throws Exception ��O
     */
    public void testSystemExceptionThrowableString02() throws Exception {
        // ���͒l�̐ݒ�B
        Throwable cause = new Throwable("testException");

        // �e�X�g�ΏۃR���X�g���N�^�̎��s�B
        se1 = new SystemException(cause, "");

        // �o�͒l�̊m�F�B
        String throwWord =
            ((Throwable) UTUtil.getPrivateField(se1, "cause")).getMessage();
        String errorCode = (String) UTUtil.getPrivateField(se1, "errorCode");
        assertEquals("testException", throwWord);
        assertEquals("", errorCode);
    }
    /**
     * testSystemExceptionThrowableString03<br>
     *
     * (����n)<br>
     * �ϓ_�FC<br>
     *
     * ���͒l�Fcause = not null, errorCode = null<br>
     * ���Ғl�Fcause = not null, errorCode = null���ݒ肳���B<br>
     * 
     * �T�v�F����cause��NotNull�Ń��b�Z�[�W������A����errorCode��null�̏ꍇ
     * @throws Exception ��O
     */
    public void testSystemExceptionThrowableString03() throws Exception {
        // ���͒l�̐ݒ�B
        Throwable cause = new Throwable("testException");

        // �e�X�g�ΏۃR���X�g���N�^�̎��s�B
        se1 = new SystemException(cause, null);

        // �o�͒l�̊m�F�B
        String throwWord =
            ((Throwable) UTUtil.getPrivateField(se1, "cause")).getMessage();
        String errorCode = (String) UTUtil.getPrivateField(se1, "errorCode");
        assertEquals("testException", throwWord);
        assertNull(errorCode);
    }

    /**
     * testSystemExceptionThrowableStringStringArray01<br>
     *
     * (����n)<br>
     *
     * �ϓ_�FA<br>
     *
     * ���͒l�Fcause = not null, errorCode = not null, <br>
     *        optionStrings = not null<br>
     * ���Ғl�Fcause = not null, errorCode = not null, <br>
     *        options = not null���ݒ肳���B<br>
     * 
     * �T�v�F����cause��NotNull�Ń��b�Z�[�W������A����errorCode��"test01"�A����options���u{ "a" }�v�̏ꍇ
     * @throws Exception ��O
     */
    public void testSystemExceptionThrowableStringStringArray01()
        throws Exception {
        // ���͒l�̐ݒ�B
        Throwable cause = new Throwable("testException");
        String errorCode = "test01";
        String[] optionStrings = { "a" };

        // �e�X�g�ΏۃR���X�g���N�^�̎��s�B
        se1 = new SystemException(cause, errorCode, optionStrings);

        // �o�͒l�̊m�F�B
        String throwWord =
            ((Throwable) UTUtil.getPrivateField(se1, "cause")).getMessage();
        errorCode = (String) UTUtil.getPrivateField(se1, "errorCode");
        String[] options = (String[]) UTUtil.getPrivateField(se1, "options");
        assertEquals("testException", throwWord);
        assertEquals("test01", errorCode);
        assertEquals("a", options[0]);
    }

    /**
     * testSystemExceptionThrowableStringStringArray02()<br>
     *
     * (����n)<br>
     *
     * �ϓ_�FA<br>
     *
     * ���͒l�Fcause = not null, errorCode = "", <br>
     *        optionStrings = not null<br>
     * ���Ғl�Fcause = not null, errorCode = "", <br>
     *        options = not null���ݒ肳���B<br>
     * 
     * �T�v�F����cause��NotNull�Ń��b�Z�[�W������A����errorCode��"test01"�A����options���u{ "a", "b" }�v�̏ꍇ
     * @throws Exception ��O
     */
    public void testSystemExceptionThrowableStringStringArray02()
        throws Exception {
        // ���͒l�̐ݒ�B
        Throwable cause = new Throwable("testException");
        String[] optionStrings = { "a", "b" };

        // �e�X�g�ΏۃR���X�g���N�^�̎��s�B
        se1 = new SystemException(cause, "", optionStrings);

        // �o�͒l�̊m�F�B
        String throwWord =
            ((Throwable) UTUtil.getPrivateField(se1, "cause")).getMessage();
        String errorCode = (String) UTUtil.getPrivateField(se1, "errorCode");
        String[] options = (String[]) UTUtil.getPrivateField(se1, "options");
        assertEquals("testException", throwWord);
        assertEquals("", errorCode);
        assertEquals("a", options[0]);
        assertEquals("b", options[1]);
    }

    /**
     * testSystemExceptionThrowableStringStringArray03()<br>
     *
     * (����n)<br>
     *
     * �ϓ_�FA<br>
     *
     * ���͒l�Fcause = not null, errorCode = null, <br>
     *        optionStrings = not null<br>
     * ���Ғl�Fcause = not null, errorCode = null, <br>
     *        options = not null���ݒ肳���B<br>
     * 
     * �T�v�F����cause��NotNull�Ń��b�Z�[�W������A����errorCode��"test01"�A����options���u{ "a", "b", "c" }�v�̏ꍇ
     * @throws Exception ��O
     */
    public void testSystemExceptionThrowableStringStringArray03()
        throws Exception {
        // ���͒l�̐ݒ�B
        Throwable cause = new Throwable("testException");
        String[] optionStrings = { "a", "b", "c" };

        // �e�X�g�ΏۃR���X�g���N�^�̎��s�B
        se1 = new SystemException(cause, null, optionStrings);

        // �o�͒l�̊m�F�B
        String throwWord =
            ((Throwable) UTUtil.getPrivateField(se1, "cause")).getMessage();
        String errorCode = (String) UTUtil.getPrivateField(se1, "errorCode");
        String[] options = (String[]) UTUtil.getPrivateField(se1, "options");
        assertEquals("testException", throwWord);
        assertNull(errorCode);
        assertEquals("a", options[0]);
        assertEquals("b", options[1]);
        assertEquals("c", options[2]);
    }

    /**
     * testSystemExceptionThrowableStringStringArray04<br>
     *
     * (����n)<br>
     *
     * �ϓ_�FC<br>
     *
     * ���͒l�Fcause = not null, errorCode = "", <br>
     *        optionStrings = null<br>
     * ���Ғl�Fcause = not null, errorCode = "", <br>
     *        options = null���ݒ肳���B<br>
     * 
     * �T�v�F����cause��NotNull�Ń��b�Z�[�W������A����errorCode��"test01"�A����options��null�̏ꍇ
     * @throws Exception ��O
     */
    public void testSystemExceptionThrowableStringStringArray04()
        throws Exception {
        // ���͒l�̐ݒ�B
        Throwable cause = new Throwable("testException");
        String[] optionStrings = null;

        // �e�X�g�ΏۃR���X�g���N�^�̎��s�B
        se1 = new SystemException(cause, "", optionStrings);

        // �o�͒l�̊m�F�B
        String throwWord =
            ((Throwable) UTUtil.getPrivateField(se1, "cause")).getMessage();
        String errorCode = (String) UTUtil.getPrivateField(se1, "errorCode");
        String[] options = (String[]) UTUtil.getPrivateField(se1, "options");
        assertEquals("testException", throwWord);
        assertEquals("", errorCode);
        assertNull(options);

    }

    /**
     * testSystemExceptionThrowableStringStringArray05<br>
     *
     * (����n)<br>
     *
     * �ϓ_�FC<br>
     *
     * ���͒l�Fcause = not null, errorCode = null, <br>
     *        optionStrings = not null(�z��v�f�ɋ󔒂���)<br>
     * ���Ғl�Fcause = not null, errorCode = null, <br>
     *        options = not null(�z��v�f�ɋ󔒂���)���ݒ肳���B<br>
     * 
     * �T�v�F����cause��NotNull�Ń��b�Z�[�W������A����errorCode��"test01"�A����options���u{ "", "", "" }�v�̏ꍇ
     * @throws Exception ��O
     */
    public void testSystemExceptionThrowableStringStringArray05()
        throws Exception {
        // ���͒l�̐ݒ�B
        Throwable cause = new Throwable("testException");
        String[] optionStrings = { "", "", "" };

        // �e�X�g�ΏۃR���X�g���N�^�̎��s�B
        se1 = new SystemException(cause, null, optionStrings);

        // �o�͒l�̊m�F�B
        String throwWord =
            ((Throwable) UTUtil.getPrivateField(se1, "cause")).getMessage();
        String errorCode = (String) UTUtil.getPrivateField(se1, "errorCode");
        String[] options = (String[]) UTUtil.getPrivateField(se1, "options");
        assertEquals("testException", throwWord);
        assertEquals(null, errorCode);
        assertEquals("", options[0]);
        assertEquals("", options[1]);
        assertEquals("", options[2]);

    }

    /**
     * testSystemExceptionThrowableStringStringArray06<br>
     *
     * (����n)<br>
     *
     * �ϓ_�FC<br>
     *
     * ���͒l�Fcause = not null, errorCode = not null, <br>
     *        optionStrings = not null(�z��v�f��null����)<br>
     * ���Ғl�Fcause = not null, errorCode = not null, <br>
     *        options = not null(�z��v�f��null����)���ݒ肳���B<br>
     * 
     * �T�v�F����cause��NotNull�Ń��b�Z�[�W������A����errorCode��"test01"�A����options���u{ null, null, null }�v�̏ꍇ
     * @throws Exception ��O
     */
    public void testSystemExceptionThrowableStringStringArray06()
        throws Exception {
        // ���͒l�̐ݒ�B
        Throwable cause = new Throwable("testException");
        String errorCode = "test01";
        String[] optionStrings = { null, null, null };

        // �e�X�g�ΏۃR���X�g���N�^�̎��s�B
        se1 = new SystemException(cause, errorCode, optionStrings);

        // �o�͒l�̊m�F�B
        String throwWord =
            ((Throwable) UTUtil.getPrivateField(se1, "cause")).getMessage();
        errorCode = (String) UTUtil.getPrivateField(se1, "errorCode");
        String[] options = (String[]) UTUtil.getPrivateField(se1, "options");
        assertEquals("testException", throwWord);
        assertEquals("test01", errorCode);
        assertNull(options[0]);
        assertNull(options[1]);
        assertNull(options[2]);

    }

    /**
     * testSystemExceptionThrowableStringStringArray07<br>
     *
     * (����n)<br>
     *
     * �ϓ_�FC<br>
     *
     * ���͒l�Fcause = not null, errorCode = not null, <br>
     *        optionStrings = not null(�z��v�f�ɋ󔒁Anull����)<br>
     * ���Ғl�Fcause = not null, errorCode = not null, <br>
     *        options = not null(�z��v�f�ɋ󔒁Anull����)���ݒ肳���B<br>
     * 
     * �T�v�F����cause��NotNull�Ń��b�Z�[�W������A����errorCode��"test01"�A����options���u{ "a", "", null }�v�̏ꍇ
     * @throws Exception ��O
     */
    public void testSystemExceptionThrowableStringStringArray07()
        throws Exception {
        // ���͒l�̐ݒ�B
        Throwable cause = new Throwable("testException");
        String errorCode = "test01";
        String[] optionStrings = { "a", "", null };

        // �e�X�g�ΏۃR���X�g���N�^�̎��s�B
        se1 = new SystemException(cause, errorCode, optionStrings);

        // �o�͒l�̊m�F�B
        String throwWord =
            ((Throwable) UTUtil.getPrivateField(se1, "cause")).getMessage();
        errorCode = (String) UTUtil.getPrivateField(se1, "errorCode");
        String[] options = (String[]) UTUtil.getPrivateField(se1, "options");
        assertEquals("testException", throwWord);
        assertEquals("test01", errorCode);
        assertEquals("a", options[0]);
        assertEquals("", options[1]);
        assertNull(options[2]);

    }

    /**
     * testSystemExceptionThrowableStringString01()<br>
     *
     * (����n)<br>
     *
     * �ϓ_�FA<br>
     *
     * ���͒l�Fcause = not null, errorCode = not null, s0 = not null<br>
     * ���Ғl�Fcause = not null, errorCode = not null, <br>
     *        options = not null���ݒ肳���B<br>
     * 
     * �T�v�F����cause��NotNull�Ń��b�Z�[�W������A����errorCode��"test01"�A����s0��"a"�̏ꍇ
     * @throws Exception ��O
     */
    public void testSystemExceptionThrowableStringString01() throws Exception {
        // ���͒l�̐ݒ�B
        Throwable cause = new Throwable("testException");
        String errorCode = "test01";
        String s0 = "a";

        // �e�X�g�ΏۃR���X�g���N�^�̎��s�B
        se1 = new SystemException(cause, errorCode, s0);

        // �o�͒l�̊m�F�B
        String throwWord =
            ((Throwable) UTUtil.getPrivateField(se1, "cause")).getMessage();
        errorCode = (String) UTUtil.getPrivateField(se1, "errorCode");
        String[] options = (String[]) UTUtil.getPrivateField(se1, "options");
        assertEquals("testException", throwWord);
        assertEquals("test01", errorCode);
        assertEquals("a", options[0]);
    }

    /**
     * testSystemExceptionThrowableStringString02()<br>
     *
     * (����n)<br>
     *
     * �ϓ_�FC<br>
     *
     * ���͒l�Fcause = not null, errorCode = "", s0 = ""<br>
     * ���Ғl�Fcause = not null, errorCode = "", <br>
     *        options = not null(�z��v�f�ɋ󔒂���)���ݒ肳���B<br>
     * 
     * �T�v�F����cause��NotNull�Ń��b�Z�[�W������A����errorCode��""�A����s0���󔒂̏ꍇ
     * @throws Exception ��O
     */
    public void testSystemExceptionThrowableStringString02() throws Exception {
        // ���͒l�̐ݒ�B
        Throwable cause = new Throwable("testException");

        // �e�X�g�ΏۃR���X�g���N�^�̎��s�B
        se1 = new SystemException(cause, "", "");

        // �o�͒l�̊m�F�B
        String throwWord =
            ((Throwable) UTUtil.getPrivateField(se1, "cause")).getMessage();
        String errorCode = (String) UTUtil.getPrivateField(se1, "errorCode");
        String[] options = (String[]) UTUtil.getPrivateField(se1, "options");
        assertEquals("testException", throwWord);
        assertEquals("", errorCode);
        assertEquals("", options[0]);
    }

    /**
     * testSystemExceptionThrowableStringString03()<br>
     *
     * (����n)<br>
     *
     * �ϓ_�FC<br>
     *
     * ���͒l�Fcause = not null, errorCode = null, s0 = null<br>
     * ���Ғl�Fcause = not null, errorCode = null, <br>
     *        options = not null(�z��v�f��null����)���ݒ肳���B<br>
     * 
     * �T�v�F����cause��NotNull�Ń��b�Z�[�W������A����errorCode��null�A����s0��null�̏ꍇ
     * @throws Exception ��O
     */
    public void testSystemExceptionThrowableStringString03() throws Exception {
        // ���͒l�̐ݒ�B
        Throwable cause = new Throwable("testException");
        String s0 = null;

        // �e�X�g�ΏۃR���X�g���N�^�̎��s�B
        se1 = new SystemException(cause, null, s0);

        // �o�͒l�̊m�F�B
        String throwWord =
            ((Throwable) UTUtil.getPrivateField(se1, "cause")).getMessage();
        String errorCode = (String) UTUtil.getPrivateField(se1, "errorCode");
        String[] options = (String[]) UTUtil.getPrivateField(se1, "options");
        assertEquals("testException", throwWord);
        assertNull(errorCode);
        assertNull(options[0]);
    }

    /**
     * testSystemExceptionThrowableStringStringString01()<br>
     *
     * (����n)<br>
     *
     * �ϓ_�FA<br>
     *
     * ���͒l�Fcause = not null, errorCode = not null,<br>
     *        s0 = not null, s1 = not null<br>
     * ���Ғl�Fcause = not null, errorCode = not null, <br>
     *        options = not null���ݒ肳���B<br>
     * 
     * �T�v�F����cause��NotNull�Ń��b�Z�[�W������A����errorCode��"test01"�A����s0��"a"�A����s1��"b"�̏ꍇ
     * @throws Exception ��O
     */
    public void testSystemExceptionThrowableStringStringString01()
        throws Exception {
        // ���͒l�̐ݒ�B
        Throwable cause = new Throwable("testException");
        String errorCode = "test01";
        String s0 = "a";
        String s1 = "b";

        // �e�X�g�ΏۃR���X�g���N�^�̎��s�B
        se1 = new SystemException(cause, errorCode, s0, s1);

        // �o�͒l�̊m�F�B
        String throwWord =
            ((Throwable) UTUtil.getPrivateField(se1, "cause")).getMessage();
        errorCode = (String) UTUtil.getPrivateField(se1, "errorCode");
        String[] options = (String[]) UTUtil.getPrivateField(se1, "options");
        assertEquals("testException", throwWord);
        assertEquals("test01", errorCode);
        assertEquals("a", options[0]);
        assertEquals("b", options[1]);
    }

    /**
     * testSystemExceptionThrowableStringStringString02()<br>
     *
     * (����n)<br>
     *
     * �ϓ_�FC<br>
     *
     * ���͒l�Fcause = not null, errorCode = not null, s0 = "", s1 = ""<br>
     * ���Ғl�Fcause = not null, errorCode = not null, <br>
     *        options = not null(�z��v�f��""����)���ݒ肳���B<br>
     * 
     * �T�v�F����cause��NotNull�Ń��b�Z�[�W������A����errorCode��"test01"�A����s0���󔒁A����s1���󔒂̏ꍇ
     * @throws Exception ��O
     */
    public void testSystemExceptionThrowableStringStringString02()
        throws Exception {
        // ���͒l�̐ݒ�B
        Throwable cause = new Throwable("testException");
        String errorCode = "test01";

        // �e�X�g�ΏۃR���X�g���N�^�̎��s�B
        se1 = new SystemException(cause, errorCode, "", "");

        // �o�͒l�̊m�F�B
        String throwWord =
            ((Throwable) UTUtil.getPrivateField(se1, "cause")).getMessage();
        errorCode = (String) UTUtil.getPrivateField(se1, "errorCode");
        String[] options = (String[]) UTUtil.getPrivateField(se1, "options");
        assertEquals("testException", throwWord);
        assertEquals("test01", errorCode);
        assertEquals("", options[0]);
        assertEquals("", options[1]);
    }

    /**
     * testSystemExceptionThrowableStringStringString03()<br>
     *
     * (����n)<br>
     *
     * �ϓ_�FC<br>
     *
     * ���͒l�Fcause = not null, errorCode = "", s0 = null, s1 = null<br>
     * ���Ғl�Fcause = not null, errorCode = "", <br>
     *        options = not null(�z��v�f��null����)���ݒ肳���B<br>
     * 
     * �T�v�F����cause��NotNull�Ń��b�Z�[�W������A����errorCode���󔒁A����s0��null�A����s1��null�̏ꍇ
     * @throws Exception ��O
     */
    public void testSystemExceptionThrowableStringStringString03()
        throws Exception {
        // ���͒l�̐ݒ�B
        Throwable cause = new Throwable("testException");

        // �e�X�g�ΏۃR���X�g���N�^�̎��s�B
        se1 = new SystemException(cause, "", null, null);

        // �o�͒l�̊m�F�B
        String throwWord =
            ((Throwable) UTUtil.getPrivateField(se1, "cause")).getMessage();
        String errorCode = (String) UTUtil.getPrivateField(se1, "errorCode");
        String[] options = (String[]) UTUtil.getPrivateField(se1, "options");
        assertEquals("testException", throwWord);
        assertEquals("", errorCode);
        assertNull(options[0]);
        assertNull(options[1]);
    }

    /**
     * testSystemExceptionThrowableStringStringString04()<br>
     *
     * (����n)<br>
     *
     * �ϓ_�FC<br>
     *
     * ���͒l�Fcause = not null, errorCode = null, s0 = "", s1 = not null<br>
     * ���Ғl�Fcause = not null, errorCode = null, <br>
     *        options = not null(�z��v�f�ɋ󔒂���)���ݒ肳���B<br>
     * 
     * �T�v�F����cause��NotNull�Ń��b�Z�[�W������A����errorCode��null�A����s0���󔒁A����s1��"a"�̏ꍇ
     * @throws Exception ��O
     */
    public void testSystemExceptionThrowableStringStringString04()
        throws Exception {
        // ���͒l�̐ݒ�B
        Throwable cause = new Throwable("testException");
        String s1 = "a";

        // �e�X�g�ΏۃR���X�g���N�^�̎��s�B
        se1 = new SystemException(cause, null, "", s1);

        // �o�͒l�̊m�F�B
        String throwWord =
            ((Throwable) UTUtil.getPrivateField(se1, "cause")).getMessage();
        String errorCode = (String) UTUtil.getPrivateField(se1, "errorCode");
        String[] options = (String[]) UTUtil.getPrivateField(se1, "options");
        assertEquals("testException", throwWord);
        assertNull(errorCode);
        assertEquals("", options[0]);
        assertEquals("a", options[1]);
    }

    /**
     * testSystemExceptionThrowableStringStringString05()<br>
     *
     * (����n)<br>
     *
     * �ϓ_�FC<br>
     *
     * ���͒l�Fcause = not null, errorCode = not null, s0 = null, s1 = not null<br>
     * ���Ғl�Fcause = not null, errorCode = not null, <br>
     *        options = not null(�z��v�f��null����)���ݒ肳���B<br>
     * 
     * �T�v�F����cause��NotNull�Ń��b�Z�[�W������A����errorCode��"test01"�A����s0��null�A����s1��"a"�̏ꍇ
     * @throws Exception ��O
     */
    public void testSystemExceptionThrowableStringStringString05()
        throws Exception {
        // ���͒l�̐ݒ�B
        Throwable cause = new Throwable("testException");
        String errorCode = "test01";
        String s1 = "a";

        // �e�X�g�ΏۃR���X�g���N�^�̎��s�B
        se1 = new SystemException(cause, errorCode, null, s1);

        // �o�͒l�̊m�F�B
        String throwWord =
            ((Throwable) UTUtil.getPrivateField(se1, "cause")).getMessage();
        errorCode = (String) UTUtil.getPrivateField(se1, "errorCode");
        String[] options = (String[]) UTUtil.getPrivateField(se1, "options");
        assertEquals("testException", throwWord);
        assertEquals("test01", errorCode);
        assertNull(options[0]);
        assertEquals("a", options[1]);
    }

    /**
     * testSystemExceptionThrowableStringStringStringString01()<br>
     *
     * (����n)<br>
     *
     * �ϓ_�FA<br>
     *
     * ���͒l�Fcause = not null, errorCode = not null, <br>
     *        s0 = not null, s1 = not null, s2 = not null<br>
     * ���Ғl�Fcause = not null, errorCode = not null, <br>
     *        options = not null���ݒ肳���B<br>
     * 
     * �T�v�F����cause��NotNull�Ń��b�Z�[�W������A����errorCode��"test01"�A����s0��"a"�A����s1��"b"�A����s2��"c"�̏ꍇ
     * @throws Exception ��O
     */
    public void testSystemExceptionThrowableStringStringStringString01()
        throws Exception {
        // ���͒l�̐ݒ�B
        Throwable cause = new Throwable("testException");
        String errorCode = "test01";
        String s0 = "a";
        String s1 = "b";
        String s2 = "c";

        // �e�X�g�ΏۃR���X�g���N�^�̎��s�B
        se1 = new SystemException(cause, errorCode, s0, s1, s2);

        // �o�͒l�̊m�F�B
        String throwWord =
            ((Throwable) UTUtil.getPrivateField(se1, "cause")).getMessage();
        errorCode = (String) UTUtil.getPrivateField(se1, "errorCode");
        String[] options = (String[]) UTUtil.getPrivateField(se1, "options");
        assertEquals("testException", throwWord);
        assertEquals("test01", errorCode);
        assertEquals("a", options[0]);
        assertEquals("b", options[1]);
        assertEquals("c", options[2]);
    }

    /**
     * testSystemExceptionThrowableStringStringStringString02()<br>
     *
     * (����n)<br>
     *
     * �ϓ_�FC<br>
     *
     * ���͒l�Fcause = not null, errorCode = "", <br>
     *        s0 = "", s1 = "", s2 = ""<br>
     * ���Ғl�Fcause = not null, errorCode = "", <br>
     *        options = not null(�z��v�f�ɋ󔒂���)���ݒ肳���B<br>
     * 
     * �T�v�F����cause��NotNull�Ń��b�Z�[�W������A����errorCode���󔒁A����s0���󔒁A����s1���󔒁A����s2���󔒂̏ꍇ
     * @throws Exception ��O
     */
    public void testSystemExceptionThrowableStringStringStringString02()
        throws Exception {
        // ���͒l�̐ݒ�B
        Throwable cause = new Throwable("testException");

        // �e�X�g�ΏۃR���X�g���N�^�̎��s�B
        se1 = new SystemException(cause, "", "", "", "");

        // �o�͒l�̊m�F�B
        String throwWord =
            ((Throwable) UTUtil.getPrivateField(se1, "cause")).getMessage();
        String errorCode = (String) UTUtil.getPrivateField(se1, "errorCode");
        String[] options = (String[]) UTUtil.getPrivateField(se1, "options");
        assertEquals("testException", throwWord);
        assertEquals("", errorCode);
        assertEquals("", options[0]);
        assertEquals("", options[1]);
        assertEquals("", options[2]);
    }

    /**
     * testSystemExceptionThrowableStringStringStringString03()<br>
     *
     * (����n)<br>
     *
     * �ϓ_�FC<br>
     *
     * ���͒l�Fcause = not null, errorCode = null, <br>
     *        s0 = null, s1 = null, s2 = null<br>
     * ���Ғl�Fcause = not null, errorCode = null, <br>
     *        options = not null(�z��v�f�ɋ󔒂���)���ݒ肳���B<br>
     * 
     * �T�v�F����cause��NotNull�Ń��b�Z�[�W������A����errorCode��null�A����s0��null�A����s1��null�A����s2��null�̏ꍇ
     * @throws Exception ��O
     */
    public void testSystemExceptionThrowableStringStringStringString03()
        throws Exception {
        // ���͒l�̐ݒ�B
        Throwable cause = new Throwable("testException");

        // �e�X�g�ΏۃR���X�g���N�^�̎��s�B
        se1 = new SystemException(cause, null, null, null, null);

        // �o�͒l�̊m�F�B
        String throwWord =
            ((Throwable) UTUtil.getPrivateField(se1, "cause")).getMessage();
        String errorCode = (String) UTUtil.getPrivateField(se1, "errorCode");
        String[] options = (String[]) UTUtil.getPrivateField(se1, "options");
        assertEquals("testException", throwWord);
        assertNull(errorCode);
        assertNull(options[0]);
        assertNull(options[1]);
        assertNull(options[2]);
    }

    /**
     * testSystemExceptionThrowableStringStringStringString04()<br>
     *
     * (����n)<br>
     *
     * �ϓ_�FC<br>
     *
     * ���͒l�Fcause = not null, errorCode = not null,<br>
     *        s0 = not null, s1 = "", s2 = null<br>
     * ���Ғl�Fcause = not null, errorCode = not null,<br>
     *        options = not null���ݒ肳���B<br>
     * 
     * �T�v�F����cause��NotNull�Ń��b�Z�[�W������A����errorCode��"test01"�A����s0��"a"�A����s1���󔒁A����s2��null�̏ꍇ
     * @throws Exception ��O
     */
    public void testSystemExceptionThrowableStringStringStringString04()
        throws Exception {
        // ���͒l�̐ݒ�B
        Throwable cause = new Throwable("testException");
        String errorCode = "test01";
        String s0 = "a";

        // �e�X�g�ΏۃR���X�g���N�^�̎��s�B
        se1 = new SystemException(cause, errorCode, s0, "", null);

        // �o�͒l�̊m�F�B
        String throwWord =
            ((Throwable) UTUtil.getPrivateField(se1, "cause")).getMessage();
        errorCode = (String) UTUtil.getPrivateField(se1, "errorCode");
        String[] options = (String[]) UTUtil.getPrivateField(se1, "options");
        assertEquals("testException", throwWord);
        assertEquals("test01", errorCode);
        assertEquals("a", options[0]);
        assertEquals("", options[1]);
        assertNull(options[2]);
    }

    /**
     * testSystemExceptionThrowableStringStringStringStringString01()<br>
     *
     * (����n)<br>
     *
     * �ϓ_�FA<br>
     *
     * ���͒l�Fcause = not null, errorCode = null,<br>
     *        s0 = not null, s1 = not null, s2 = not null, s3 = not null<br>
     * ���Ғl�Fcause = not null, errorCode = null, <br>
     *        options = not null���ݒ肳���B<br>
     * 
     * �T�v�F����cause��NotNull�Ń��b�Z�[�W������A����errorCode��null�A����s0��"a"�A����s1��"b"�A����s2��"c"�A����s3��"d"�̏ꍇ
     * @throws Exception ��O
     */
    public void testSystemExceptionThrowableStringStringStringStringString01()
        throws Exception {
        // ���͒l�̐ݒ�B
        Throwable cause = new Throwable("testException");
        String s0 = "a";
        String s1 = "b";
        String s2 = "c";
        String s3 = "d";

        // �e�X�g�ΏۃR���X�g���N�^�̎��s�B
        se1 = new SystemException(cause, null, s0, s1, s2, s3);

        // �o�͒l�̊m�F�B
        String throwWord =
            ((Throwable) UTUtil.getPrivateField(se1, "cause")).getMessage();
        String errorCode = (String) UTUtil.getPrivateField(se1, "errorCode");
        String[] options = (String[]) UTUtil.getPrivateField(se1, "options");
        assertEquals("testException", throwWord);
        assertNull(errorCode);
        assertEquals("a", options[0]);
        assertEquals("b", options[1]);
        assertEquals("c", options[2]);
        assertEquals("d", options[3]);
    }

    /**
     * testSystemExceptionThrowableStringStringStringStringString02()<br>
     *
     * (����n)<br>
     *
     * �ϓ_�FC<br>
     *
     * ���͒l�Fcause = not null, errorCode = not null,<br>
     *        s0 = "", s1 = "", s2 = "", s3 = ""<br>
     * ���Ғl�Fcause = not null, errorCode = not null, <br>
     *        options = not null(�z��v�f�ɋ󔒂���)���ݒ肳���B<br>
     * 
     * �T�v�F����cause��NotNull�Ń��b�Z�[�W������A����errorCode��"test01"�A����s0���󔒁A����s1���󔒁A����s2���󔒁A����s3���󔒂̏ꍇ
     * @throws Exception ��O
     */
    public void testSystemExceptionThrowableStringStringStringStringString02()
        throws Exception {
        // ���͒l�̐ݒ�B
        Throwable cause = new Throwable("testException");
        String errorCode = "test01";

        // �e�X�g�ΏۃR���X�g���N�^�̎��s�B
        se1 = new SystemException(cause, errorCode, "", "", "", "");

        // �o�͒l�̊m�F�B
        String throwWord =
            ((Throwable) UTUtil.getPrivateField(se1, "cause")).getMessage();
        errorCode = (String) UTUtil.getPrivateField(se1, "errorCode");
        String[] options = (String[]) UTUtil.getPrivateField(se1, "options");
        assertEquals("testException", throwWord);
        assertEquals("test01", errorCode);
        assertEquals("", options[0]);
        assertEquals("", options[1]);
        assertEquals("", options[2]);
        assertEquals("", options[3]);
    }

    /**
     * testSystemExceptionThrowableStringStringStringStringString03()<br>
     *
     * (����n)<br>
     *
     * �ϓ_�FC<br>
     *
     * ���͒l�Fcause = not null, errorCode = "", <br>
     *        s0 = null, s1 = null, s2 = null, s3 = null<br>
     * ���Ғl�Fcause = not null, errorCode = "", <br>
     *        options = not null(�z��v�f��null����)���ݒ肳���B<br>
     * 
     * �T�v�F����cause��NotNull�Ń��b�Z�[�W������A����errorCode���󔒁A����s0��null�A����s1��null�A����s2��null�A����s3��null�̏ꍇ
     * @throws Exception ��O
     */
    public void testSystemExceptionThrowableStringStringStringStringString03()
        throws Exception {
        // ���͒l�̐ݒ�B
        Throwable cause = new Throwable("testException");

        // �e�X�g�ΏۃR���X�g���N�^�̎��s�B
        se1 = new SystemException(cause, "", null, null, null, null);

        // �o�͒l�̊m�F�B
        String throwWord =
            ((Throwable) UTUtil.getPrivateField(se1, "cause")).getMessage();
        String errorCode = (String) UTUtil.getPrivateField(se1, "errorCode");
        String[] options = (String[]) UTUtil.getPrivateField(se1, "options");
        assertEquals("testException", throwWord);
        assertEquals("", errorCode);
        assertNull(options[0]);
        assertNull(options[1]);
        assertNull(options[2]);
        assertNull(options[3]);
    }

    /**
     * testSystemExceptionThrowableStringStringStringStringString04()<br>
     *
     * (����n)<br>
     *
     * �ϓ_�FC<br>
     *
     * ���͒l�Fcause = not null, errorCode = null,<br>
     *        s0 = not null, s1 = not null, s2 = "", s3 = null<br>
     * ���Ғl�Fcause = not null, errorCode = null, <br>
     *        options = not null(�z��v�f�ɋ󔒁Anull����)���ݒ肳���B<br>
     * 
     * �T�v�F����cause��NotNull�Ń��b�Z�[�W������A����errorCode��null�A����s0��"a"�A����s1��"b"�A����s2���󔒁A����s3��null�̏ꍇ
     * @throws Exception ��O
     */
    public void testSystemExceptionThrowableStringStringStringStringString04()
        throws Exception {
        // ���͒l�̐ݒ�B
        Throwable cause = new Throwable("testException");
        String s0 = "a";
        String s1 = "b";

        // �e�X�g�ΏۃR���X�g���N�^�̎��s�B
        se1 = new SystemException(cause, null, s0, s1, "", null);

        // �o�͒l�̊m�F�B
        String throwWord =
            ((Throwable) UTUtil.getPrivateField(se1, "cause")).getMessage();
        String errorCode = (String) UTUtil.getPrivateField(se1, "errorCode");
        String[] options = (String[]) UTUtil.getPrivateField(se1, "options");
        assertEquals("testException", throwWord);
        assertNull(errorCode);
        assertEquals("a", options[0]);
        assertEquals("b", options[1]);
        assertEquals("", options[2]);
        assertNull(options[3]);
    }

    /**
     * testGetErrorCode01()<br>
     *
     * (����n)<br>
     * �ϓ_�FA<br>
     *
     * ���͒l�Fcause = not null, errorCode = not null<br>
     * ���Ғl�FSystemException��errorCode�������擾�ł��Ă��邩�m�F����B
     * 
     * �T�v�F������n�ꌏ�̂݃e�X�g
     * @throws Exception ��O
     */
    public void testGetErrorCode01() throws Exception {
        // SystemException�̐ݒ�B
        Throwable cause = new Throwable("testException");

        // �e�X�g�ΏۃR���X�g���N�^�̎��s�B
        se1 = new SystemException(cause);
        UTUtil.setPrivateField(se1, "errorCode", "abc");

        // �e�X�g�Ώۃ��\�b�h�̎��s�Əo�͒l�̊m�F�B
        assertEquals("abc", se1.getErrorCode());
    }

    /**
     * testGetOptions01()<br>
     *
     * (����n)<br>
     * �ϓ_�FA<br>
     *
     * ���͒l�Foptions = not null<br>
     * ���Ғl�FSystemException��options�������擾�ł��Ă��邩�m�F����B
     * 
     * �T�v�F������n�ꌏ�̂݃e�X�g
     * @throws Exception ��O
     */
    public void testGetOptions01() throws Exception {
        // SystemException�̐ݒ�B
        Throwable cause = new Throwable("testException");
        String[] options = { "a", "b" };
        se1 = new SystemException(cause);
        UTUtil.setPrivateField(se1, "options", options);

        // �e�X�g�Ώۃ��\�b�h�̎��s�Əo�͒l�̊m�F�B
        assertEquals("a", se1.getOptions()[0]);
        assertEquals("b", se1.getOptions()[1]);
        assertEquals(2, se1.getOptions().length);
    }

    /**
     * testSetMessage01()<br>
     *
     * (����n)<br>
     * �ϓ_�FA<br>
     *
     * ���͒l�Fcause = not null, message = not null<br>
     * ���Ғl�FSystemException��message���������͂ł��Ă��邩�m�F����B
     * 
     * �T�v�F������n�ꌏ�̂݃e�X�g
     * @throws Exception ��O
     */
    public void testSetMessage01() throws Exception {
        // SystemException�̐ݒ�B
        Throwable cause = new Throwable("testException");
        se1 = new SystemException(cause);
        String message = "abc";

        // �e�X�g�Ώۃ��\�b�h�̎��s�B
        se1.setMessage(message);

        // �o�͒l�̊m�F�B
        assertEquals("abc", UTUtil.getPrivateField(se1, "message"));
    }

    /**
     * testGetMessage01()<br>
     *
     * (����n)<br>
     * �ϓ_�FA<br>
     *
     * ���͒l�Fcause = not null, message = not null<br>
     * ���Ғl�FSystemException��message�������擾�ł��Ă��邩�m�F����B
     * 
     * �T�v�Fmessage�̒l��not null�̏ꍇ�Amessage�̒l���擾�ł��邱�Ƃ��m�F����B
     * @throws Exception ��O
     */
    public void testGetMessage01() throws Exception {
        // SystemException�̐ݒ�B
        Throwable cause = new Throwable("testException");

        // �e�X�g�ΏۃR���X�g���N�^�̎��s�B
        se1 = new SystemException(cause);
        UTUtil.setPrivateField(se1, "message", "abc");

        // �e�X�g�Ώۃ��\�b�h�̎��s�Əo�͒l�̊m�F�B
        assertEquals("abc", se1.getMessage());
    }

    /**
     * testGetMessage02()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(���) message:null<br>
     *         (���) errorCode:"def"<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) message:"def"<br>
     *
     * <br>
     * message�̒l��null�̏ꍇ�AerrorCode�̒l���擾�ł��邱�Ƃ��m�F����B
     * <br>
     * 
     * �T�v�Fmessage�̒l��null�̏ꍇ�AerrorCode�̒l���擾�ł��邱�Ƃ��m�F����B
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetMessage02() throws Exception {
        // SystemException�̐ݒ�B
        Throwable cause = new Throwable("testException");

        // �e�X�g�ΏۃR���X�g���N�^�̎��s�B
        se1 = new SystemException(cause);
        UTUtil.setPrivateField(se1, "message", null);
        UTUtil.setPrivateField(se1, "errorCode", "def");

        // �e�X�g�Ώۃ��\�b�h�̎��s�Əo�͒l�̊m�F�B
        assertEquals("def", se1.getMessage());
    }
}