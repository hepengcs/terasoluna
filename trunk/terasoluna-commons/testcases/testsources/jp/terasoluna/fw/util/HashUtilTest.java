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

package jp.terasoluna.fw.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import junit.framework.TestCase;

/**
 * HashUtil�u���b�N�{�b�N�X�e�X�g<br>
 * 
 * (�O�����)<br>
 * �E�Ƃ��ɂȂ�<br>
 * 
 */
public class HashUtilTest extends TestCase {

    /**
     * Constructor for HashUtilTest.
     * @param arg0 �e�X�g�P�[�X�̃��\�b�h��
     */
    public HashUtilTest(String arg0) {
        super(arg0);
    }

    /**
     * @see TestCase#setUp()
     * @throws Exception �e�X�g�R�[�h�̖{���Ƃ������̖�����O
     * 
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    /**
     * @see TestCase#tearDown()
     * @throws Exception �e�X�g�R�[�h�̖{���Ƃ������̖�����O
     */
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * testHash01�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FA<br>
     * 
     * ���͒l�F���p������<br>
     * ���Ғl�F���p������̃n�b�V���l<br>
     * 
     * ���p������̃n�b�V���l�̎擾���o���邱�Ƃ̊m�F���m�F����B<br>
     * 
     * @throws Exception �e�X�g�R�[�h�̖{���Ƃ������̖�����O
     */
    public void testHash01() throws Exception {
        // ���͒l�ݒ�
        String paramAlgorithm = "MD5";
        String paramStr = "abc";

        // �e�X�g���s
        byte[] resultHashValue = HashUtil.hash(paramAlgorithm, paramStr);

        // ���ʊm�F
        assertTrue(
            MessageDigest.isEqual(
                MessageDigest.getInstance("MD5").digest("abc".getBytes()),
                resultHashValue));
    }

    /**
     * testHash02�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FA<br>
     * 
     * ���͒l�F�S�p������<br>
     * ���Ғl�F�S�p������̃n�b�V���l<br>
     * 
     * �S�p������̃n�b�V���l�̎擾���o���邱�Ƃ̊m�F���m�F����B<br>
     * 
     * @throws Exception �e�X�g�R�[�h�̖{���Ƃ������̖�����O
     */
    public void testHash02() throws Exception {
        // ���͒l�ݒ�
        String paramAlgorithm = "MD5";
        String paramStr = "������";

        // �e�X�g���s
        byte[] resultHashValue = HashUtil.hash(paramAlgorithm, paramStr);

        // ���ʊm�F
        assertTrue(
            MessageDigest.isEqual(
                MessageDigest.getInstance("MD5").digest("������".getBytes()),
                resultHashValue));
    }
    /**
     * testHash03�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FC<br>
     * 
     * ���͒l�F�󕶎���<br>
     * ���Ғl�F�󕶎���̃n�b�V���l<br>
     * 
     * �󕶎���̃n�b�V���l�̎擾���o���邱�Ƃ̊m�F���m�F����B<br>
     * 
     * @throws Exception �e�X�g�R�[�h�̖{���Ƃ������̖�����O
     */
    public void testHash03() throws Exception {
        // ���͒l�ݒ�
        String paramAlgorithm = "MD5";
        String paramStr = "";

        // �e�X�g���s
        byte[] resultHashValue = HashUtil.hash(paramAlgorithm, paramStr);

        // ���ʊm�F
        assertTrue(
            MessageDigest.isEqual(
                MessageDigest.getInstance("MD5").digest("".getBytes()),
                resultHashValue));
    }

    /**
     * testHash04�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FA<br>
     * 
     * ���͒l�F�n�b�V���A���S���Y���w���������<br>
     * ���Ғl�F�n�b�V���l�̎擾����<br>
     * 
     * �n�b�V���A���S���Y���w����������ōs�����ꍇ�ɂ��n�b�V���l�̎擾���o���邱�Ƃ��m�F����B<br>
     * 
     * @throws Exception �e�X�g�R�[�h�̖{���Ƃ������̖�����O
     */
    public void testHash04() throws Exception {
        // ���͒l�ݒ�
        String paramAlgorithm = "md5";
        String paramStr = "";

        // �e�X�g���s
        byte[] resultHashValue = HashUtil.hash(paramAlgorithm, paramStr);

        // ���ʊm�F
        assertTrue(
            MessageDigest.isEqual(
                MessageDigest.getInstance("MD5").digest("".getBytes()),
                resultHashValue));
    }

    /**
     * testHash05�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FC<br>
     * 
     * ���͒l�Fnull������<br>
     * ���Ғl�FNullPointerException������<br>
     * 
     * null������̃n�b�V���l�̎擾��NullPointerException���������邱�Ƃ��m�F����B<br>
     * 
     * @throws Exception �e�X�g�R�[�h�̖{���Ƃ������̖�����O
     */
    public void testHash05() throws Exception {
        // ���͒l�ݒ�
        String paramAlgorithm = "md5";
        String paramStr = null;

        // �e�X�g���s
        byte[] resultHashValue = HashUtil.hash(paramAlgorithm, paramStr);

        // ���ʊm�F
        assertNull(resultHashValue);
    }

    /**
     * testHash06�B<br>
     * 
     * (�ُ�n)<br>
     * �ϓ_�FA<br>
     * 
     * ���͒l�F�s���ȃn�b�V���A���S���Y��<br>
     * ���Ғl�FNoSuchAlgorithmException������<br>
     * 
     * �s���ȃn�b�V���A���S���Y���w�莞NoSuchAlgorithmException���������邱�Ƃ��m�F����B<br>
     * 
     * @throws Exception �e�X�g�R�[�h�̖{���Ƃ������̖�����O
     */
    public void testHash06() throws Exception {
        // ���͒l�ݒ�
        String paramAlgorithm = "NoSuchAlgorithm";
        String paramStr = "abc";

        try {
            // �e�X�g���s
            HashUtil.hash(paramAlgorithm, paramStr);
            fail();
        } catch (NoSuchAlgorithmException nsae) {
            return;
        }
    }

    /**
     * testHash07�B<br>
     * 
     * (�ُ�n)<br>
     * �ϓ_�FC<br>
     * 
     * ���͒l�F�n�b�V���A���S���Y���ɋ󕶎���w��<br>
     * ���Ғl�FNoSuchAlgorithmException������<br>
     * 
     * �n�b�V���A���S���Y���ɋ󕶎���w�莞NoSuchAlgorithmException���������邱�Ƃ��m�F����B<br>
     * 
     * @throws Exception �e�X�g�R�[�h�̖{���Ƃ������̖�����O
     */
    public void testHash07() throws Exception {
        // ���͒l�ݒ�
        String paramAlgorithm = "";
        String paramStr = "abc";

        try {
            // �e�X�g���s
            HashUtil.hash(paramAlgorithm, paramStr);
            fail();
        } catch (NoSuchAlgorithmException nsae) {
            return;
        }
    }

    /**
     * testHash08�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FC<br>
     * 
     * ���͒l�F�n�b�V���A���S���Y����null�w��<br>
     * ���Ғl�FNullPointerException������<br>
     * 
     * �n�b�V���A���S���Y����null�w�莞NullPointerException���������邱�Ƃ��m�F����B<br>
     * 
     * @throws Exception �e�X�g�R�[�h�̖{���Ƃ������̖�����O
     */
    public void testHash08() throws Exception {
        // ���͒l�ݒ�
        String paramAlgorithm = null;
        String paramStr = "abc";

        // �e�X�g���s
        byte[] resultHashValue = HashUtil.hash(paramAlgorithm, paramStr);

        // ���ʊm�F
        assertNull(resultHashValue);
    }

    /**
     * testHashMD501�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FA<br>
     * 
     * ���͒l�F���p������<br>
     * ���Ғl�F���p������̃n�b�V���l<br>
     * 
     * ���p������̃n�b�V���l�̎擾���o���邱�Ƃ��m�F����B<br>
     * 
     * @throws Exception �e�X�g�R�[�h�̖{���Ƃ������̖�����O
     */
    public void testHashMD501() throws Exception {
        // ���͒l�ݒ�
        String paramStr = "abc";

        // �e�X�g���s        
        byte[] resultHashValue = HashUtil.hashMD5(paramStr);

        // ���ʊm�F
        assertTrue(
            MessageDigest.isEqual(
                MessageDigest.getInstance("MD5").digest("abc".getBytes()),
                resultHashValue));

    }

    /**
     * testHashMD502�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FA<br>
     * 
     * ���͒l�F�S�p������<br>
     * ���Ғl�F�S�p������̃n�b�V���l<br>
     * 
     * �S�p������̃n�b�V���l�̎擾���o���邱�Ƃ��m�F����B<br>
     * 
     * @throws Exception �e�X�g�R�[�h�̖{���Ƃ������̖�����O
     */
    public void testHashMD502() throws Exception {
        // ���͒l�ݒ�
        String paramStr = "������";

        // �e�X�g���s
        byte[] resultHashValue = HashUtil.hashMD5(paramStr);

        // ���ʊm�F
        assertTrue(
            MessageDigest.isEqual(
                MessageDigest.getInstance("MD5").digest("������".getBytes()),
                resultHashValue));
    }

    /**
     * testHashMD503�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FC<br>
     * 
     * ���͒l�F�󕶎���<br>
     * ���Ғl�F�󕶎���̃n�b�V���l<br>
     * 
     * �󕶎���̃n�b�V���l�̎擾���o���邱�Ƃ��m�F����B<br>
     * 
     * @throws Exception �e�X�g�R�[�h�̖{���Ƃ������̖�����O
     */
    public void testHashMD503() throws Exception {
        // ���͒l�ݒ�
        String paramStr = "";

        // �e�X�g���s        
        byte[] resultHashValue = HashUtil.hashMD5(paramStr);

        // ���ʊm�F
        assertTrue(
            MessageDigest.isEqual(
                MessageDigest.getInstance("MD5").digest("".getBytes()),
                resultHashValue));
    }

    /**
     * testHashMD504�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FC<br>
     * 
     * ���͒l�Fnull������<br>
     * ���Ғl�FNullPointerException������<br>
     * 
     * null������̃n�b�V���l�̎擾��NullPointerException���������邱�Ƃ��m�F����B<br>
     * 
     * @throws Exception �e�X�g�R�[�h�̖{���Ƃ������̖�����O
     */
    public void testHashMD504() throws Exception {
        // ���͒l�ݒ�
        String paramStr = null;

        // �e�X�g���s
        byte[] resultHashValue = HashUtil.hashMD5(paramStr);

        // ���ʊm�F
        assertNull(resultHashValue);
    }

    /**
     * testHashSHA101�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FA<br>
     * 
     * ���͒l�F���p������<br>
     * ���Ғl�F���p������̃n�b�V���l<br>
     * 
     * ���p������̃n�b�V���l�̎擾���o���邱�Ƃ��m�F����B<br>
     * 
     * @throws Exception �e�X�g�R�[�h�̖{���Ƃ������̖�����O
     */
    public void testHashSHA101() throws Exception {
        // ���͒l�ݒ�
        String paramStr = "abc";

        // �e�X�g���s
        byte[] resultHashValue = HashUtil.hashSHA1(paramStr);

        // ���ʊm�F
        assertTrue(
            MessageDigest.isEqual(
                MessageDigest.getInstance("SHA1").digest("abc".getBytes()),
                resultHashValue));
    }

    /**
     * testHashSHA102�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FA<br>
     * 
     * ���͒l�F�S�p������<br>
     * ���Ғl�F�S�p������̃n�b�V���l<br>
     * 
     * �S�p������̃n�b�V���l�̎擾���o���邱�Ƃ��m�F����B<br>
     * 
     * @throws Exception �e�X�g�R�[�h�̖{���Ƃ������̖�����O
     */
    public void testHashSHA102() throws Exception {
        // ���͒l�ݒ�
        String paramStr = "������";

        // �e�X�g���s
        byte[] resultHashValue = HashUtil.hashSHA1(paramStr);

        // ���ʊm�F
        assertTrue(
            MessageDigest.isEqual(
                MessageDigest.getInstance("SHA1").digest("������".getBytes()),
                resultHashValue));
    }

    /**
     * testHashSHA103�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FC<br>
     * 
     * ���͒l�F�󕶎���<br>
     * ���Ғl�F�󕶎���̃n�b�V���l<br>
     * 
     * �S�p������̃n�b�V���l�̎擾���o���邱�Ƃ��m�F����B<br>
     * 
     * @throws Exception �e�X�g�R�[�h�̖{���Ƃ������̖�����O
     */
    public void testHashSHA103() throws Exception {
        // ���͒l�ݒ�
        String paramStr = "";

        // �e�X�g���s
        byte[] resultHashValue = HashUtil.hashSHA1(paramStr);

        // ���ʊm�F
        assertTrue(
            MessageDigest.isEqual(
                MessageDigest.getInstance("SHA1").digest("".getBytes()),
                resultHashValue));
    }

    /**
     * testHashSHA104�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FC<br>
     * 
     * ���͒l�Fnull������<br>
     * ���Ғl�FNullPointerException������<br>
     * 
     * null������̃n�b�V���l�̎擾��NullPointerException���������邱�Ƃ��m�F����B<br>
     * 
     * @throws Exception �e�X�g�R�[�h�̖{���Ƃ������̖�����O
     */
    public void testHashSHA104() throws Exception {
        // ���͒l�ݒ�
        String paramStr = null;

        // �e�X�g���s
        byte[] resultHashValue = HashUtil.hashSHA1(paramStr);

        // ���ʊm�F
        assertNull(resultHashValue);
    }

}
