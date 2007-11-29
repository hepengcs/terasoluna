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

import java.io.UnsupportedEncodingException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Vector;

import junit.framework.TestCase;

/**
 * 
 * StringUtil �u���b�N�{�b�N�X�e�X�g�B<br>
 * 
 * (�O�����)<br>
 * �@�E�v���p�e�B�t�@�C���ɁA�ȉ��̃L�[�ƒl������ɋL�q����Ă��邱��<br>
 * �@�@�@���O�o�̓f�B���N�g��(log4j.file.dir)<br> 
 * �@�@�@���O�t�@�C����(log4j.file.name)<br>
 *
 */
@SuppressWarnings("unused")
public class StringUtilTest extends TestCase {

    /**
    * �t�@�C�����O�o�͐�̃f�B���N�g�������v���p�e�B����擾����L�[�l�B
    */
    public static final String FILE_DIR = "log4j.file.dir";

    /**
    * �t�@�C�����O�o�͐�̃t�@�C�������v���p�e�B����擾����L�[�l�B
    *
    */
    private static final String FILE_NAME = "log4j.file.name";

    /**
     * ���s����OS�ŗp��������s�R�[�h���擾����L�[�l
     * 
     */
    private static final String LINE_SEP = System.getProperty("line.separator");

    /**
    * �t�@�C�����O�o�͐�̐�΃p�X�B
    *
    */
    String LOG_FILE_NAME =
        PropertyUtil.getProperty(FILE_DIR)
            + "/"
            + PropertyUtil.getProperty(FILE_NAME);

    /**
     * Constructor for StringUtilTest.
     * @param arg0
     */
    public StringUtilTest(String arg0) {
        super(arg0);
    }

    /*
     * @see TestCase#setUp()
     * @throws Exception ��O */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    /*
     * @see TestCase#tearDown()
     * @throws Exception ��O */
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * testIsWhitespace01()
     * 
     * (����n)<br>
     * 
     * �ϓ_�FA<br>
     * 
     * ���͒l�F���p�X�y�[�X<br>
     * 
     * ���Ғl�Ftrue<br>
     * 
     * ���p�X�y�[�X�L�����N�^����͂������Atrue��<br>
     * �ԋp����邱�Ƃ��m�F����B
     * 
     * @throws Exception ��O */
    public void testIsWhitespace01() throws Exception {
        // ���͒l�̐ݒ�
        char input = ' ';

        // �e�X�g���s�ƌ��ʊm�F
        assertTrue(StringUtil.isWhitespace(input));
    }

    /**
     * testIsWhitespace02()
     * 
     * (����n)<br>
     * 
     * �ϓ_�FA<br>
     * 
     * ���͒l�F���p������<br>
     * 
     * ���Ғl�Ffalse<br>
     * 
     * ���p�p���L�����N�^����͂������Afalse��<br>
     * �ԋp����邱�Ƃ��m�F����B
     * 
     * @throws Exception ��O */
    public void testIsWhitespace02() throws Exception {
        // ���͒l�̐ݒ�
        char input = 'a';

        // �e�X�g���s�ƌ��ʊm�F
        assertFalse(StringUtil.isWhitespace(input));
    }

    /**
     * testIsWhitespace03()
     * 
     * (����n)<br>
     * 
     * �ϓ_�FA<br>
     * 
     * ���͒l�F0<br>
     * 
     * ���Ғl�Ffalse<br>
     * 
     * 0�L�����N�^��ݒ肵�����Afalse���ԋp����邱��<br>
     * 
     * @throws Exception ��O */
    public void testIsWhitespace03() throws Exception {
        // ���͒l�̐ݒ�
        //char input = '\u0000';
        char input = 0;

        // �e�X�g���s�ƌ��ʊm�F
        assertFalse(StringUtil.isWhitespace(input));
    }

    /**
     * testRtrim01()
     * 
     * (����n)<br>
     * 
     * �ϓ_�FA<br>
     * 
     * ���͒l�F�����ɔ��p�X�y�[�X1�����镶����<br>
     * 
     * ���Ғl�F�E���̔��p�X�y�[�X1�̂ݏ������<br>
     * 
     * ���E�����ɃX�y�[�X�����݂��镶�������͂������A<br>
     * �E�X�y�[�X�̂ݏ�������Ă��邱�Ƃ��m�F�B
     * 
     * @throws Exception ��O */
    public void testRtrim01() throws Exception {
        // ���͒l�̐ݒ�
        String input = " TERASOLUNA ";

        // �e�X�g���s        
        String result = StringUtil.rtrim(input);

        // ���ʊm�F
        assertEquals(" TERASOLUNA", result);
    }

    /**
     * testRtrim02()
     * 
     * (����n)<br>
     * 
     * �ϓ_�FB<br>
     * 
     * ���͒l�F�����ɔ��p�X�y�[�X���������镶����<br>
     * 
     * ���Ғl�F�E���̔��p�X�y�[�X�����ׂď������<br>
     * 
     * ������E�[�ɔ��p�X�y�[�X���������݂���Ƃ��A
     * �A�������E�[�X�y�[�X����������Ă��邱�Ƃ��m�F�B
     * 
     * 
     * @throws Exception ��O */
    public void testRtrim02() throws Exception {
        // ���͒l�̐ݒ�
        String input = "   TERASOLUNA   ";

        // �e�X�g���s        
        String result = StringUtil.rtrim(input);

        // ���ʊm�F
        assertEquals("   TERASOLUNA", result);
    }

    /**
     * testRtrim03()
     * 
     * (����n)<br>
     * 
     * �ϓ_�FA<br>
     * 
     * ���͒l�F�E���ɔ��p�X�y�[�X���Ȃ�������<br>
     * 
     * ���Ғl�F���͂Ɠ���<br>
     * 
     * �E�[�ɃX�y�[�X���Ȃ��Ƃ��A���͒l�Ɠ���������<br>
     * �ԋp����邱�Ƃ��m�F����B
     * 
     * @throws Exception ��O */
    public void testRtrim03() throws Exception {
        // ���͒l�̐ݒ�
        String input = " TERASOLUNA";

        // �e�X�g���s        
        String result = StringUtil.rtrim(input);

        // ���ʊm�F
        assertEquals(" TERASOLUNA", result);
    }

    /**
     * testRtrim04()
     * 
     * (����n)<br>
     * 
     * �ϓ_�FC<br>
     * 
     * ���͒l�Fnull<br>
     * 
     * ���Ғl�Fnull<br>
     * 
     * null�����͒l�ɐݒ肳�ꂽ���A���̂܂�null��<br>
     * �ԋp����邱�ƁB
     * 
     * @throws Exception ��O */
    public void testRtrim04() throws Exception {
        // ���͒l�̐ݒ�
        String input = null;

        // �e�X�g���s        
        String result = StringUtil.rtrim(input);

        // ���ʊm�F
        assertNull(result);
    }

    /**
     * testRtrim05()
     * 
     * (����n)<br>
     * 
     * �ϓ_�FA<br>
     * 
     * ���͒l�F""(�󕶎�)<br>
     * 
     * ���Ғl�F""(�󕶎�)<br>
     * 
     * �󕶎������͒l�ɐݒ肳�ꂽ���A���̂܂܋󕶎����ԋp����邱��
     * 
     * @throws Exception ��O */
    public void testRtrim05() throws Exception {
        // ���͒l�̐ݒ�
        String input = "";

        // �e�X�g���s        
        String result = StringUtil.rtrim(input);

        // ���ʊm�F
        assertEquals("", result);
    }

    /**
     * testLtrim01()
     * 
     * (����n)<br>
     * 
     * �ϓ_�FA<br>
     * 
     * ���͒l�F�����ɔ��p�X�y�[�X��1�����镶����<br>
     * 
     * ���Ғl�F�����̔��p�X�y�[�X�̂ݏ����ꂽ������<br>
     * 
     * ���E�����ɃX�y�[�X�����݂��镶�������͂������A<br>
     * ���X�y�[�X�̂ݏ�������Ă��邱�Ƃ��m�F�B 
     * @throws Exception ��O */
    public void testLtrim01() throws Exception {
        // ���͒l�̐ݒ�
        String input = " TERASOLUNA ";

        // �e�X�g���s        
        String result = StringUtil.ltrim(input);

        // ���ʊm�F
        assertEquals("TERASOLUNA ", result);
    }

    /**
     * testLtrim02()
     * 
     * (����n)<br>
     * 
     * �ϓ_�FB<br>
     * 
     * ���͒l�F�����ɔ��p�X�y�[�X���������镶����<br>
     * 
     * ���Ғl�F�����̔��p�X�y�[�X�����ׂď����ꂽ������<br>
     * 
     * �����񍶒[�ɔ��p�X�y�[�X���������݂���Ƃ��A
     * �A���������[�X�y�[�X����������Ă��邱�Ƃ��m�F�B
     * 
     * @throws Exception ��O */
    public void testLtrim02() throws Exception {
        // ���͒l�̐ݒ�
        String input = "   TERASOLUNA   ";

        // �e�X�g���s        
        String result = StringUtil.ltrim(input);

        // ���ʊm�F
        assertEquals("TERASOLUNA   ", result);
    }

    /**
     * testLtrim03()
     * 
     * (����n)<br>
     * 
     * �ϓ_�FA<br>
     * 
     * ���͒l�F�����ɔ��p�X�y�[�X���Ȃ�������<br>
     * 
     * ���Ғl�F���͂Ɠ���<br>
     * 
     * ���[�ɃX�y�[�X���Ȃ��Ƃ��A���͒l�Ɠ���������<br>
     * �ԋp����邱�Ƃ��m�F����B
     * 
     * @throws Exception ��O */
    public void testLtrim03() throws Exception {
        // ���͒l�̐ݒ�
        String input = "TERASOLUNA ";

        // �e�X�g���s        
        String result = StringUtil.ltrim(input);

        // ���ʊm�F
        assertEquals("TERASOLUNA ", result);
    }

    /**
     * testLtrim04()
     * 
     * (����n)<br>
     * 
     * �ϓ_�FC<br>
     * 
     * ���͒l�Fnull<br>
     * 
     * ���Ғl�Fnull<br>
     * 
     * null�����͒l�ɐݒ肳�ꂽ���A���̂܂�null��<br>
     * �ԋp����邱�ƁB
     *  
     * @throws Exception ��O */
    public void testLtrim04() throws Exception {
        // ���͒l�̐ݒ�
        String input = null;

        // �e�X�g���s        
        String result = StringUtil.ltrim(input);

        // ���ʊm�F
        assertNull(result);
    }

    /**
     * testLtrim05()
     * 
     * (����n)<br>
     * 
     * �ϓ_�FA<br>
     * 
     * ���͒l�F""(�󕶎�)<br>
     * 
     * ���Ғl�F""(�󕶎�)<br>
     * 
     * �󕶎������͒l�ɐݒ肳�ꂽ���A���̂܂܋󕶎����ԋp����邱��
     * 
     * @throws Exception ��O */
    public void testLtrim05() throws Exception {
        // ���͒l�̐ݒ�
        String input = "";

        // �e�X�g���s        
        String result = StringUtil.ltrim(input);

        // ���ʊm�F
        assertEquals("", result);
    }

    /**
     * testTrim01()
     * 
     * (����n)<br>
     * 
     * �ϓ_�FA<br>
     * 
     * ���͒l�F�����ɔ��p�X�y�[�X��1�����镶����<br>
     * 
     * ���Ғl�F�����̔��p�X�y�[�X�������ꂽ������<br>
     * 
     * �����ɔ��p�X�y�[�X���P���镶���񂪓��͂��ꂽ���A<br>
     * �X�y�[�X���������ꂽ�����񂪕ԋp����邱�ƁB
     * StringUtils.trim()�̌Ăяo���m�F�̂��߂ɁA�P�P�[�X�݂̂Ƃ���B
     * 
     * @throws Exception ��O */
    public void testTrim01() throws Exception {
        // ���͒l�̐ݒ�
        String input = " TERASOLUNA ";

        // �e�X�g���s        
        String result = StringUtil.trim(input);

        // ���ʊm�F
        assertEquals("TERASOLUNA", result);
    }

    /**
     * testIsZenHankakuSpace01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) c:"�@"(�S�p�X�y�[�X�j<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *         
     * <br>
     * �S�p�X�y�[�X�L�����N�^����͂������Atrue���ԋp����邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testIsZenHankakuSpace01() throws Exception {
        // ���͒l�̐ݒ�
        char input = '�@';

        // �e�X�g���s�ƌ��ʊm�F
        assertTrue(StringUtil.isZenHankakuSpace(input));
    }

    /**
     * testIsZenHankakuSpace02()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) c:"a"<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) boolean:false<br>
     *         
     * <br>
     * ���p�p���L�����N�^����͂������Afalse���ԋp����邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testIsZenHankakuSpace02() throws Exception {
        // ���͒l�̐ݒ�
        char input = 'a';

        // �e�X�g���s�ƌ��ʊm�F
        assertFalse(StringUtil.isZenHankakuSpace(input));
    }

    /**
     * testIsZenHankakuSpace03()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) c:0<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) boolean:false<br>
     *         
     * <br>
     * 0�L�����N�^��ݒ肵�����Afalse���ԋp����邱��
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testIsZenHankakuSpace03() throws Exception {
        // ���͒l�̐ݒ�
        char input = 0;

        // �e�X�g���s�ƌ��ʊm�F
        assertFalse(StringUtil.isZenHankakuSpace(input));
    }

    /**
     * testRtrimZ01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) str:" �@ TERASOLUNA �@ "<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) String:" �@ TERASOLUNA"<br>
     *         
     * <br>
     * ���E�����ɃX�y�[�X�����݂��镶�������͂������A�E�X�y�[�X�̂ݏ�������Ă��邱�Ƃ��m�F�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testRtrimZ01() throws Exception {
        // ���͒l�̐ݒ�
        String input = " �@ TERASOLUNA �@ ";

        // �e�X�g���s        
        String result = StringUtil.rtrimZ(input);

        // ���ʊm�F
        assertEquals(" �@ TERASOLUNA", result);
    }

    /**
     * testRtrimZ02()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) str:" �@  �@TERASOLUNA �@ �@ "<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) String:" �@  �@TERASOLUNA"<br>
     *         
     * <br>
     * ������E�[�ɑS���p�X�y�[�X���������݂���Ƃ��A�A�������E�[�X�y�[�X����������Ă��邱�Ƃ��m�F�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testRtrimZ02() throws Exception {
        // ���͒l�̐ݒ�
        String input = " �@  �@TERASOLUNA �@ �@ ";

        // �e�X�g���s        
        String result = StringUtil.rtrimZ(input);

        // ���ʊm�F
        assertEquals(" �@  �@TERASOLUNA", result);
    }

    /**
     * testRtrimZ03()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) str:"�@ TERASOLUNA"<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) String:"�@ TERASOLUNA"<br>
     *         
     * <br>
     * �E�[�ɃX�y�[�X���Ȃ��Ƃ��A���͒l�Ɠ��������񂪕ԋp����邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testRtrimZ03() throws Exception {
        // ���͒l�̐ݒ�
        String input = "�@ TERASOLUNA";

        // �e�X�g���s        
        String result = StringUtil.rtrimZ(input);

        // ���ʊm�F
        assertEquals("�@ TERASOLUNA", result);
    }

    /**
     * testRtrimZ04()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) str:null<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) String:null<br>
     *         
     * <br>
     * null�����͒l�ɐݒ肳�ꂽ���A���̂܂�null�ŕԋp����邱�ƁB
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testRtrimZ04() throws Exception {
        // ���͒l�̐ݒ�
        String input = null;

        // �e�X�g���s        
        String result = StringUtil.rtrimZ(input);

        // ���ʊm�F
        assertNull(result);
    }

    /**
     * testRtrimZ05()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) str:""<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) String:""<br>
     *         
     * <br>
     * �󕶎������͒l�ɐݒ肳�ꂽ���A���̂܂܋󕶎����ԋp����邱��
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testRtrimZ05() throws Exception {
        // ���͒l�̐ݒ�
        String input = "";

        // �e�X�g���s        
        String result = StringUtil.rtrimZ(input);

        // ���ʊm�F
        assertEquals("", result);
    }

    /**
     * testLtrimZ01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) str:"�@  TERASOLUNA �@ "<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) String:"TERASOLUNA �@ "<br>
     *         
     * <br>
     * ���E�����ɃX�y�[�X�����݂��镶�������͂������A���X�y�[�X�̂ݏ�������Ă��邱�Ƃ��m�F�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testLtrimZ01() throws Exception {
        // ���͒l�̐ݒ�
        String input = "�@  TERASOLUNA �@ ";

        // �e�X�g���s        
        String result = StringUtil.ltrimZ(input);

        // ���ʊm�F
        assertEquals("TERASOLUNA �@ ", result);
    }

    /**
     * testLtrimZ02()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) str:" �@  �@TERASOLUNA �@ �@ "<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) String:"TERASOLUNA �@ �@ "<br>
     *         
     * <br>
     * �����񍶒[�ɑS���p�X�y�[�X���������݂���Ƃ��A�A���������[�X�y�[�X����������Ă��邱�Ƃ��m�F�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testLtrimZ02() throws Exception {
        // ���͒l�̐ݒ�
        String input = " �@  �@TERASOLUNA �@ �@ ";

        // �e�X�g���s        
        String result = StringUtil.ltrimZ(input);

        // ���ʊm�F
        assertEquals("TERASOLUNA �@ �@ ", result);
    }

    /**
     * testLtrimZ03()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) str:"TERASOLUNA �@"<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) String:"TERASOLUNA �@"<br>
     *         
     * <br>
     * ���[�ɃX�y�[�X���Ȃ��Ƃ��A���͒l�Ɠ��������񂪕ԋp����邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testLtrimZ03() throws Exception {
        // ���͒l�̐ݒ�
        String input = "TERASOLUNA �@";

        // �e�X�g���s        
        String result = StringUtil.ltrimZ(input);

        // ���ʊm�F
        assertEquals("TERASOLUNA �@", result);
    }

    /**
     * testLtrimZ04()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) str:null<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) String:null<br>
     *         
     * <br>
     * null�����͒l�ɐݒ肳�ꂽ���A���̂܂�null�ŕԋp����邱�ƁB
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testLtrimZ04() throws Exception {
        // ���͒l�̐ݒ�
        String input = null;

        // �e�X�g���s        
        String result = StringUtil.ltrimZ(input);

        // ���ʊm�F
        assertNull(result);
    }

    /**
     * testLtrimZ05()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) str:""<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) String:""<br>
     *         
     * <br>
     * �󕶎������͒l�ɐݒ肳�ꂽ���A���̂܂܋󕶎����ԋp����邱��
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testLtrimZ05() throws Exception {
        // ���͒l�̐ݒ�
        String input = "";

        // �e�X�g���s        
        String result = StringUtil.ltrimZ(input);

        // ���ʊm�F
        assertEquals("", result);
    }

    /**
     * testTrim01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) str:" �@ TERASOLUNA �@ "<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) String:"TERASOLUNA"<br>
     *         
     * <br>
     * �����ɑS�p���p�X�y�[�X���P�ȏ゠�镶���񂪓��͂��ꂽ���A�X�y�[�X���������ꂽ�����񂪕ԋp����邱�ƁB<br>
     * StringUtil.rtrimZ()��StringUtil.ltrimZ()�̌Ăяo���m�F�̂��߂ɁA�P�P�[�X�݂̂Ƃ���B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testTrimZ01() throws Exception {
        // ���͒l�̐ݒ�
        String input = " �@ TERASOLUNA �@ ";

        // �e�X�g���s        
        String result = StringUtil.trimZ(input);

        // ���ʊm�F
        assertEquals("TERASOLUNA", result);
    }

    /**
     * testToShortClassName01()
     * 
     * (����n)<br>
     * 
     * �ϓ_�FA<br>
     * 
     * ���͒l�F�p�b�P�[�W�C�����̃N���X��<br>
     * 
     * ���Ғl�F�p�b�P�[�W�C���̂Ȃ��N���X��<br>
     * 
     * �p�b�P�[�W���{�s���I�h�{�N���X�����ݒ肳�ꂽ���A<br>
     * �N���X�����ԋp����邱��
     * ClassUtils.getShortClassName()�̌Ăяo���m�F�̂��߂ɁA�P�P�[�X�݂̂Ƃ���B
     * 
     * @throws Exception ��O */
    public void testToShortClassName01() throws Exception {
        // ���͒l�̐ݒ�
        String input = "jp.terasoluna.util.StringUtil";

        // �e�X�g���s
        String result = StringUtil.toShortClassName(input);

        // ���ʊm�F
        assertEquals("StringUtil", result);
    }

    /**
     * testGetExtention01()
     * 
     * (����n)<br>
     * 
     * �ϓ_�FA<br>
     * 
     * ���͒l�F�g���q���̃t�@�C����<br>
     * 
     * ���Ғl�F�g���q<br>
     * 
     * �t�@�C�����{�s���I�h�{�g���q���ݒ肳�ꂽ���A<br>
     * �s���I�h�{�g���q���ԋp����邱�ƁB
     * 
     * @throws Exception ��O */
    public void testGetExtention01() throws Exception {
        // ���͒l�̐ݒ�
        String input = "sample.txt";

        // �e�X�g���s
        String result = StringUtil.getExtension(input);

        // ���ʊm�F
        assertEquals(".txt", result);
    }

    /**
     * testGetExtention02()
     * 
     * (����n)<br>
     * 
     * �ϓ_�FA<br>
     * 
     * ���͒l�F�g���q�̂Ȃ��t�@�C����<br>
     * 
     * ���Ғl�F�󕶎���<br>
     * 
     * �s���I�h�{�g���q�����݂��Ȃ��ꍇ�A<br>
     * ���͒l�̓t�@�C�����݂̂Ɖ��߂���A�󕶎����ԋp����邱�ƁB
     * 
     * @throws Exception ��O */
    public void testGetExtention02() throws Exception {
        // ���͒l�̐ݒ�
        String input = "sample";

        // �e�X�g���s
        String result = StringUtil.getExtension(input);

        // ���ʊm�F
        assertEquals("", result);
    }

    /**
     * testGetExtention03()
     * 
     * (����n)<br>
     * 
     * �ϓ_�FA<br>
     * 
     * ���͒l�F�g���q���̃t�@�C����(�g���q�ȊO��"."������)<br>
     * 
     * ���Ғl�F�g���q<br>
     * 
     * ���[�̃s���I�h�{�g���q���ԋp����邱��
     * 
     * @throws Exception ��O */
    public void testGetExtention03() throws Exception {
        // ���͒l�̐ݒ�
        String input = "sample.txt.bak";

        // �e�X�g���s
        String result = StringUtil.getExtension(input);

        // ���ʊm�F
        assertEquals(".bak", result);
    }

    /**
     * testGetExtention04()
     * 
     * (����n)<br>
     * 
     * �ϓ_�FC<br>
     * 
     * ���͒l�Fnull<br>
     * 
     * ���Ғl�FNullPointerException<br>
     * 
     * ������null�Őݒ肵�����ANullPointerException���������邱�ƁB
     * 
     * @throws Exception ��O */
    public void testGetExtention04() throws Exception {
        // ���͒l�̐ݒ�
        String input = null;

        // �e�X�g���s
        try {
            String result = StringUtil.getExtension(input);
            fail();
        } catch (NullPointerException e) {
            return;
        }
    }

    /**
     * testGetExtention05()
     * 
     * (����n)<br>
     * 
     * �ϓ_�FA<br>
     * 
     * ���͒l�F�󕶎���<br>
     * 
     * ���Ғl�F�󕶎���<br>
     * 
     * �󕶎��񂪐ݒ肳�ꂽ���A���̂܂܋󕶎����ԋp����邱�ƁB
     * 
     * @throws Exception ��O */
    public void testGetExtention05() throws Exception {
        // ���͒l�̐ݒ�
        String input = "";

        // �e�X�g���s
        String result = StringUtil.getExtension(input);

        // ���ʊm�F
        assertEquals("", result);
    }

    /**
     * testGetExtention06()
     * 
     * (����n)<br>
     * 
     * �ϓ_�FA<br>
     * 
     * ���͒l�F�����Ƀs���I�h�������t�@�C����<br>
     * 
     * ���Ғl�F�s���I�h�݂̂̕�����<br>
     * 
     * �������s���I�h�̂Ƃ��A�s���I�h�̂ݕԋp����邱�ƁB
     * 
     * @throws Exception ��O */
    public void testGetExtention06() throws Exception {
        // ���͒l�̐ݒ�
        String input = "sample.txt.";

        // �e�X�g���s
        String result = StringUtil.getExtension(input);

        // ���ʊm�F
        assertEquals(".", result);
    }

    /**
     * testToHexString01()
     * 
     * (����n)<br>
     * 
     * �ϓ_�FA<br>
     * 
     * ���͒l�F�v�f��1�̃o�C�g�z��<br>
     * 
     * ���Ғl�F���͗v�f��16�i�ϊ�<br>
     * 
     * ���͒l��16�i��2���ɕϊ�����Ă��邱�ƁB
     * 
     * @throws Exception ��O */
    public void testToHexString01() throws Exception {
        // ���͒l�̐ݒ�
        byte[] byteArray = { 0 };

        // �e�X�g���s
        String result = StringUtil.toHexString(byteArray, "-");

        // ���ʊm�F
        assertEquals("00", result);
    }

    /**
     * testToHexString02()
     * 
     * (����n)<br>
     * 
     * �ϓ_�FC<br>
     * 
     * ���͒l�F�v�f��0�̃o�C�g�z��<br>
     * 
     * ���Ғl�F�󕶎���<br>
     * 
     * �󕶎��񂪐ݒ肳�ꂽ���A�󕶎������̂܂ܕԋp����邱�ƁB
     * 
     * @throws Exception ��O */
    public void testToHexString02() throws Exception {
        // ���͒l�̐ݒ�
        byte[] byteArray = {
        };

        // �e�X�g���s
        String result = StringUtil.toHexString(byteArray, "-");

        // ���ʊm�F
        assertEquals("", result);
    }

    /**
     * testToHexString03()
     * 
     * (����n)<br>
     * 
     * �ϓ_�FB<br>
     * 
     * ���͒l�F�v�f��3�̃o�C�g�z��<br>
     * 
     * ���Ғl�F���͗v�f��16�i�ϊ����A�f���~�^�Ō�������������<br>
     * 
     * �R�̗v�f��16�i���ɕϊ����A�v�f�Ԃ��f���~�^�ŋ�؂�ꂽ<br>
     * �����񂪁A�ԋp����邱�ƁB
     * 
     * @throws Exception ��O */
    public void testToHexString03() throws Exception {
        // ���͒l�̐ݒ�
        byte[] byteArray = { 0, 10, 100 };

        // �e�X�g���s
        String result = StringUtil.toHexString(byteArray, "/");

        // ���ʊm�F
        assertEquals("00/0A/64", result);
    }

    /**
     * testToHexString04()
     * 
     * (����n)<br>
     * 
     * �ϓ_�FC<br>
     * 
     * ���͒l�F�v�f��3�̃o�C�g�z��A�f���~�^���󕶎���<br>
     * 
     * ���Ғl�F���͗v�f��16�i�ϊ����A��������������<br>
     * 
     * �f���~�^���󕶎��̎��A���͗v�f��16�i�ϊ�����A���̂܂�
     * ��������Ă��邱�ƁB
     * 
     * @throws Exception ��O */
    public void testToHexString04() throws Exception {
        // ���͒l�̐ݒ�
        byte[] byteArray = { 0, 10, 100 };

        // �e�X�g���s
        String result = StringUtil.toHexString(byteArray, "");

        // ���ʊm�F
        assertEquals("000A64", result);
    }

    /**
     * testToHexString05()
     * 
     * (����n)<br>
     * 
     * �ϓ_�FC<br>
     * 
     * ���͒l�F�v�f��3�̃o�C�g�z��A�f���~�^��null<br>
     * 
     * ���Ғl�F���͗v�f��16�i�ϊ����A��������������<br>
     * 
     * �f���~�^��null�̎��A���͗v�f��16�i�ϊ�����A���̂܂�
     * ��������Ă��邱�ƁB
     * 
     * @throws Exception ��O */
    public void testToHexString05() throws Exception {
        // ���͒l�̐ݒ�
        byte[] byteArray = { 0, 10, 100 };

        // �e�X�g���s
        String result = StringUtil.toHexString(byteArray, null);

        // ���ʊm�F
        assertEquals("000A64", result);
    }

    /**
     * testToHexString06()
     * 
     * (�ُ�n)<br>
     * 
     * �ϓ_�FC<br>
     * 
     * ���͒l�F�z��null�A�f���~�^��"/"<br>
     * 
     * ���Ғl�FNullPointerException����<br>
     * 
     * �z��null�̎��A<br>
     * NullPointerExcepti���������邱�Ƃ��m�F
     * 
     * @throws Exception ��O */
    public void testToHexString06() throws Exception {
        // ���͒l�̐ݒ�
        byte[] byteArray = null;

        try {
            // �e�X�g���s
            String result = StringUtil.toHexString(byteArray, "/");
            fail();
        } catch (NullPointerException e) {
            return;
        }

    }

    /**
     * testParseCSVString01()
     * 
     * (����n)<br>
     * 
     * �ϓ_�FA<br>
     * 
     * ���͒l�F1�P��(�J���}�Ȃ�)<br>
     * 
     * ���Ғl�F���͕�����1�̗v�f�Ƃ��镶����z��<br>
     * 
     * �z��v�f�̂����A�������ꂸ��String�z��̗v�f�Ƃ��āA
     * ���̂܂ܐݒ肳��Ă��邱�ƁB
     * 
     * @throws Exception ��O */
    public void testParseCSVString01() throws Exception {
        // ���͒l�̐ݒ�
        String input = "abcde";

        // �e�X�g���s
        String[] result = StringUtil.parseCSV(input);

        // ���ʊm�F
        // ���Ғl
        String[] hope = { "abcde" };
        assertEquals(hope.length, result.length);
        assertEquals(hope[0], result[0]);
    }

    /**
     * testParseCSVString02()
     * 
     * (����n)<br>
     * 
     * �ϓ_�FC<br>
     * 
     * ���͒l�Fnull<br>
     * 
     * ���Ғl�F�v�f��0�̕�����z��<br>
     * 
     * ���͒l��null�̂Ƃ��A������0�̕����񂪕ԋp����邱�ƁB
     * 
     * @throws Exception ��O */
    public void testParseCSVString02() throws Exception {
        // ���͒l�̐ݒ�
        String input = null;

        // �e�X�g���s
        String[] result = StringUtil.parseCSV(input);

        // ���ʊm�F
        assertEquals(0, result.length);
    }

    /**
     * testParseCSVString03()
     * 
     * (����n)<br>
     * 
     * �ϓ_�FB<br>
     * 
     * ���͒l�F5�P��J���}��؂�(�󕶎����擪�Ɩ�������ѓr���Ɋ܂�)<br>
     * 
     * ���Ғl�F�J���}�Ԃ�5�̕������v�f�Ƃ��镶����z��<br>
     * 
     * String[]�z�񂪃J���}���ŋ�؂��A�z�񉻂���Ă��邱�ƁB
     * 
     * @throws Exception ��O */
    public void testParseCSVString03() throws Exception {
        // ���͒l�̐ݒ�
        String input = ",abcde,,����������,";

        // �e�X�g���s
        String[] result = StringUtil.parseCSV(input);

        // ���ʊm�F
        // ���Ғl
        String[] hope = { "", "abcde", "", "����������", "" };
        assertEquals(hope.length, result.length);
        for (int i = 0; i < hope.length; i++) {
            assertEquals(hope[i], result[i]);
        }
    }

    /**
     * testParseCSVString04()
     * 
     * (����n)<br>
     * 
     * �ϓ_�FB<br>
     * 
     * ���͒l�F�󕶎�<br>
     * 
     * ���Ғl�F��̔z��<br>
     * 
     * ���͒l���󕶎��̂Ƃ��A��̔z�񂪕ԋp����邱�ƂƁB
     * 
     * @throws Exception ��O */
    public void testParseCSVString04() throws Exception {
        // ���͒l�̐ݒ�
        String input = "";

        // �e�X�g���s
        String[] result = StringUtil.parseCSV(input);

        // ���ʊm�F
        // ���Ғl
        String[] hope = { "" };
        assertEquals(hope.length, result.length);
        assertEquals(hope[0], result[0]);
    }

    /**
     * testParseCSVStringString01()
     * 
     * (����n)<br>
     * 
     * �ϓ_�FC<br>
     * 
     * ���͒l�F���͕���=""<br>
     * �G�X�P�[�v����="\\"
     * 
     * ���Ғl�F��̔z��<br>
     * 
     * ���͒l���󕶎��̂Ƃ��A���v�f���󕶎��̕����z�񂪕ԋp����邱�ƁB
     * @throws Exception ��O */
    public void testParseCSVStringString01() throws Exception {
        // ���͒l�̐ݒ�

        // �e�X�g���s
        String[] result = StringUtil.parseCSV("", "\\");

        // ���ʊm�F
        assertEquals(1, result.length);
        assertEquals("", result[0]);
    }

    /**
     * testParseCSVStringString02()
     * 
     * (����n)<br>
     * 
     * �ϓ_�FC<br>
     * 
     * ���͒l�F���͕���=null<br>
     * �G�X�P�[�v����="\\"
     * 
     * ���Ғl�F��̔z��<br>
     * 
     * ���͒l��null�̂Ƃ��A�v�f��0�̋�̔z�񂪕ԋp����邱�ƁB
     * @throws Exception ��O */
    public void testParseCSVStringString02() throws Exception {
        // ���͒l�̐ݒ�

        // �e�X�g���s
        String[] result = StringUtil.parseCSV(null, "\\");

        // ���ʊm�F
        assertEquals(0, result.length);
    }

    /**
     * testParseCSVStringString03()
     * 
     * (����n)<br>
     * 
     * �ϓ_�FC<br>
     * 
     * ���͒l�F���͕���="a,b"<br>
     * �G�X�P�[�v����="\\"
     * 
     * ���Ғl�FString[]{"a", "b"}<br>
     * 
     * �G�X�P�[�v�������J���}�̑O�ɑ��݂��Ȃ��ꍇ�A
     * �J���}��؂�̔z�񂪕ԋp����邱�ƁB
     * @throws Exception ��O */
    public void testParseCSVStringString03() throws Exception {
        // ���͒l�̐ݒ�

        // �e�X�g���s
        String[] result = StringUtil.parseCSV("a,b", "\\");

        // ���ʊm�F
        assertEquals(2, result.length);
        String[] hope = new String[]{"a", "b"};
        for (int i = 0; i < result.length; i++) {
            assertEquals(hope[i], result[i]);
        }
    }

    /**
     * testParseCSVStringString04()
     * 
     * (����n)<br>
     * 
     * �ϓ_�FC<br>
     * 
     * ���͒l�F���͕���=",a,b"<br>
     * �G�X�P�[�v����="\\"
     * 
     * ���Ғl�FString[]{"", "a", "b"}<br>
     * 
     * ���͕����̐擪���J���}�ł���Ƃ��A���v�f���󕶎��ł��邱�ƁB
     * �J���}��؂�̔z�񂪕ԋp����邱�ƁB
     * @throws Exception ��O */
    public void testParseCSVStringString04() throws Exception {
        // ���͒l�̐ݒ�

        // �e�X�g���s
        String[] result = StringUtil.parseCSV(",a,b", "\\");

        // ���ʊm�F
        assertEquals(3, result.length);
        String[] hope = new String[]{"", "a", "b"};
        for (int i = 0; i < hope.length; i++) {
            assertEquals(hope[i], result[i]);           
        }
    }

    /**
     * testParseCSVStringString05()
     * 
     * (����n)<br>
     * 
     * �ϓ_�FC<br>
     * 
     * ���͒l�F���͕���="a,b,"<br>
     * �G�X�P�[�v����="\\"
     * 
     * ���Ғl�FString[]{"a", "b", ""}<br>
     * 
     * ���͕����̖������J���}�ł���Ƃ��A�ŏI�v�f���󕶎��ł��邱�ƁB
     * �J���}��؂�̔z�񂪕ԋp����邱�ƁB
     * @throws Exception ��O */
    public void testParseCSVStringString05() throws Exception {
        // ���͒l�̐ݒ�

        // �e�X�g���s
        String[] result = StringUtil.parseCSV("a,b,", "\\");

        // ���ʊm�F
        assertEquals(3, result.length);
        String[] hope = new String[]{"a", "b", ""};
        for (int i = 0; i < hope.length; i++) {
            assertEquals(hope[i], result[i]);
        }
    }

    /**
     * testParseCSVStringString06()
     * 
     * (����n)<br>
     * 
     * �ϓ_�FC<br>
     * 
     * ���͒l�F���͕���="a\\,b,c"<br>
     * �G�X�P�[�v����="\\"
     * 
     * ���Ғl�FString[]{"a,b", "c"}<br>
     * 
     * ���͕����̒��ɃG�X�P�[�v�����{�J���}������Ƃ��A
     * ������؂肪�s�Ȃ��Ȃ����ƁB
     * ����ȊO�̃J���}�͋�؂��邱�ƁB
     * @throws Exception ��O */
    public void testParseCSVStringString06() throws Exception {
        // ���͒l�̐ݒ�

        // �e�X�g���s
        String[] result = StringUtil.parseCSV("a\\,b,c", "\\");

        // ���ʊm�F
        assertEquals(2, result.length);
        String[] hope = new String[]{"a,b", "c"};
        for (int i = 0; i < hope.length; i++) {
            assertEquals(hope[i], result[i]);
        }
    }

    /**
     * testParseCSVStringString07()
     * 
     * (����n)<br>
     * 
     * �ϓ_�FC<br>
     * 
     * ���͒l�F���͕���="a\\b,c"<br>
     * �G�X�P�[�v����="\\"
     * 
     * ���Ғl�FString[]{"ab", "c"}<br>
     * 
     * ���͕����̒��ɃG�X�P�[�v�����{�J���}�ȊO�̕���������Ƃ��A
     * ������؂肪�s�Ȃ�ꂸ�A�ԋp������ɃG�X�P�[�v�������������Ă��Ȃ����ƁB
     * @throws Exception ��O */
    public void testParseCSVStringString07() throws Exception {
        // ���͒l�̐ݒ�

        // �e�X�g���s
        String[] result = StringUtil.parseCSV("a\\b,c", "\\");

        // ���ʊm�F
        assertEquals(2, result.length);
        String[] hope = new String[]{"ab", "c"};
        for (int i = 0; i < hope.length; i++) {
            assertEquals(hope[i], result[i]);
        }
    }

    /**
     * testParseCSVStringString08()
     * 
     * (����n)<br>
     * 
     * �ϓ_�FC<br>
     * 
     * ���͒l�F���͕���="\\,ab,c"<br>
     * �G�X�P�[�v����="\\"
     * 
     * ���Ғl�FString[]{",ab", "c"}<br>
     * 
     * �擪���G�X�P�[�v�����{�J���}�����ł���Ƃ��A
     * �ԋp�����擪�̗v�f�̑�ꕶ�����J���}�ł��邱�ƁB
     * @throws Exception ��O */
    public void testParseCSVStringString08() throws Exception {
        // ���͒l�̐ݒ�

        // �e�X�g���s
        String[] result = StringUtil.parseCSV("\\,ab,c", "\\");

        // ���ʊm�F
        assertEquals(2, result.length);
        String[] hope = new String[]{",ab", "c"};
        for (int i = 0; i < hope.length; i++) {
            assertEquals(hope[i], result[i]);
        }
    }

    /**
     * testParseCSVStringString09()
     * 
     * (����n)<br>
     * 
     * �ϓ_�FC<br>
     * 
     * ���͒l�F���͕���="ab,c\\,"<br>
     * �G�X�P�[�v����="\\"
     * 
     * ���Ғl�FString[]{"ab", "c,"}<br>
     * 
     * �I�[���G�X�P�[�v�����{�J���}�����ł���Ƃ��A
     * �ԋp�����I�[�̗v�f�̍ŏI�������J���}�ł��邱�ƁB
     * @throws Exception ��O */
    public void testParseCSVStringString09() throws Exception {
        // ���͒l�̐ݒ�

        // �e�X�g���s
        String[] result = StringUtil.parseCSV("ab,c\\,", "\\");

        // ���ʊm�F
        assertEquals(2, result.length);
        String[] hope = new String[]{"ab", "c,"};
        for (int i = 0; i < hope.length; i++) {
            assertEquals(hope[i], result[i]);
        }
    }

    /**
     * testParseCSVStringString10()
     * 
     * (����n)<br>
     * 
     * �ϓ_�FC<br>
     * 
     * ���͒l�F���͕���="ab,,,c"<br>
     * �G�X�P�[�v����="\\"
     * 
     * ���Ғl�FString[]{"ab", "", "", "c"}<br>
     * 
     * �J���}�������A���œ��͂��ꂽ���A�J���}��؂萔����
     * �󕶎��v�f���ԋp����邱�ƁB
     * @throws Exception ��O */
    public void testParseCSVStringString10() throws Exception {
        // ���͒l�̐ݒ�

        // �e�X�g���s
        String[] result = StringUtil.parseCSV("ab,,,c", "\\");

        // ���ʊm�F
        assertEquals(4, result.length);
        String[] hope = new String[]{"ab", "", "", "c"};
        for (int i = 0; i < hope.length; i++) {
            assertEquals(hope[i], result[i]);
        }
    }

    /**
     * testParseCSVStringString11()
     * 
     * (����n)<br>
     * 
     * �ϓ_�FC<br>
     * 
     * ���͒l�F���͕���="ab!,#,,c"<br>
     * �G�X�P�[�v����="#!"
     * 
     * ���Ғl�FString[]{"ab,,", "c"}<br>
     * 
     * �G�X�P�[�v�����ƃJ���}�������A���œ��͂��ꂽ���A
     * �J���}�̂ݘA�����Ĕz��v�f�ɐݒ肳��邱�ƁB
     * @throws Exception ��O */
    public void testParseCSVStringString11() throws Exception {
        // ���͒l�̐ݒ�

        // �e�X�g���s
        String[] result = StringUtil.parseCSV("ab!,#,,c", "#!");

        // ���ʊm�F
        assertEquals(2, result.length);
        String[] hope = new String[]{"ab,,", "c"};
        for (int i = 0; i < hope.length; i++) {
            assertEquals(hope[i], result[i]);
        }
    }

    /**
     * testParseCSVStringString12()
     * 
     * (����n)<br>
     * 
     * �ϓ_�FC<br>
     * 
     * ���͒l�F���͕���="ab#!@,c"<br>
     * �G�X�P�[�v����="!@#"
     * 
     * ���Ғl�FString[]{"ab!@,", "c"}<br>
     * 
     * �G�X�P�[�v�����񂪕����A�����ē��͂��ꂽ�ꍇ
     * <ol>
     *  <li>�G�X�P�[�v�����̒���̃G�X�P�[�v�������ʏ핶���Ƃ���
     *      �o�͂���邱�ƁB</li>
     *  <li>����ɑ����ăG�X�P�[�v���������݂��鎞�A
     *      �G�X�P�[�v������Ƃ��č�p���邱�ƁB</li>
     * </ol>
     * @throws Exception ��O */
    public void testParseCSVStringString12() throws Exception {
        // ���͒l�̐ݒ�

        // �e�X�g���s
        String[] result = StringUtil.parseCSV("ab#!@,c", "!@#");

        // ���ʊm�F
        assertEquals(1, result.length);
        String[] hope = new String[]{"ab!,c"};
        for (int i = 0; i < hope.length; i++) {
            assertEquals(hope[i], result[i]);
        }
    }

    /**
     * testParseCSVStringString13()
     * 
     * (����n)<br>
     * 
     * �ϓ_�FC<br>
     * 
     * ���͒l�F���͕���="ab,c"<br>
     * �G�X�P�[�v����=""
     * 
     * ���Ғl�FString[]{"ab", "c"}<br>
     * 
     * �G�X�P�[�v�����񂪋󕶎��̎��A�G�X�P�[�v���s�Ȃ�ꂸ
     * �J���}��؂蕶���񂪔z��ɕϊ�����A�ԋp����邱�ƁB
     * @throws Exception ��O */
    public void testParseCSVStringString13() throws Exception {
        // ���͒l�̐ݒ�

        // �e�X�g���s
        String[] result = StringUtil.parseCSV("ab,c", "");

        // ���ʊm�F
        assertEquals(2, result.length);
        String[] hope = new String[]{"ab", "c"};
        for (int i = 0; i < hope.length; i++) {
            assertEquals(hope[i], result[i]);
        }
    }

    /**
     * testParseCSVStringString14()
     * 
     * (����n)<br>
     * 
     * �ϓ_�FC<br>
     * 
     * ���͒l�F���͕���="ab,c"<br>
     * �G�X�P�[�v����=null
     * 
     * ���Ғl�FString[]{"ab", "c"}<br>
     * 
     * �G�X�P�[�v������null�̎��A�G�X�P�[�v���s�Ȃ�ꂸ
     * �J���}��؂蕶���񂪔z��ɕϊ�����A�ԋp����邱�ƁB
     * @throws Exception ��O */
    public void testParseCSVStringString14() throws Exception {
        // ���͒l�̐ݒ�

        // �e�X�g���s
        String[] result = StringUtil.parseCSV("ab,c", null);

        // ���ʊm�F
        assertEquals(2, result.length);
        String[] hope = new String[]{"ab", "c"};
        for (int i = 0; i < hope.length; i++) {
            assertEquals(hope[i], result[i]);
        }
    }

    /**
     * testDump01�B<br>
     * 
     * �i����n�j<br>
     * �ϓ_�FA<br>
     * <br>
     * ���͒l :String(�v�f���P��)<br>
     * ���Ғl :�_���v�Ώە�����<br>
     * 
     * �����F�_���v�Ώە�����ɕϊ�����Ă��邱�Ƃ��m�F����B
     * @throws Exception ��O */
    public void testDump01() throws Exception {
        //���͒l�̐ݒ�
        final String num = "1";
        final String area = "����";

        Map<String, String> map = new LinkedHashMap<String, String>();
        map.put(num, area);

        //�e�X�g���s
        String result = StringUtil.dump(map);

        //����
        StringBuffer sb = new StringBuffer();
        sb.append(LINE_SEP);
        sb.append("Map{");
        sb.append(LINE_SEP);
        sb.append("1=����");
        sb.append(LINE_SEP);
        sb.append("}");

        //���ʊm�F
        assertEquals(sb.toString(), result);
    }

    /**
     * testDump02�B<br>
     * 
     * �i����n�j<br>
     * �ϓ_�FA<br>
     * <br>
     * ���͒l :String(�v�f������)<br>
     * ���Ғl :�_���v�Ώە�����<br>
     * 
     * �����F�_���v�Ώە�����ɕ����A�ϊ�����Ă��邱�Ƃ��m�F����B
     * @throws Exception ��O */
    public void testDump02() throws Exception {
        //���͒l�̐ݒ�
        final String[] num = { "1", "2", "3" };
        final String[] area = { "����", "���s", "����" };

        Map<String, String> map = new LinkedHashMap<String, String>();
        for (int i = 0; i < num.length; i++) {
            map.put(num[i], area[i]);
        }
        //�e�X�g���s
        String result = StringUtil.dump(map);

        //����
        StringBuffer sb = new StringBuffer();
        sb.append(LINE_SEP);
        sb.append("Map{");
        sb.append(LINE_SEP);
        sb.append("1=����");
        sb.append(LINE_SEP);
        sb.append("2=���s");
        sb.append(LINE_SEP);
        sb.append("3=����");
        sb.append(LINE_SEP);
        sb.append("}");

        //���ʊm�F
        assertEquals(sb.toString(), result);
    }

    /**
     * testDump03�B<br>
     * 
     * �i����n�j<br>
     * �ϓ_�FC<br>
     * <br>
     * ���͒l :��<br>
     * ���Ғl :�_���v�Ώە�����<br>
     * 
     * �����F�z��_���v�Ώە�����ɕϊ�����Ă��邱�Ƃ��m�F����B
     * @throws Exception ��O */
    public void testDump03() throws Exception {
        //���͒l�̐ݒ�
        Map map = new LinkedHashMap();

        //�e�X�g���s
        String result = StringUtil.dump(map);

        //����
        StringBuffer sb = new StringBuffer();
        sb.append(LINE_SEP);
        sb.append("Map{");
        sb.append(LINE_SEP);
        sb.append("}");

        //���ʊm�F
        assertEquals(sb.toString(), result);
    }

    /**
     * testDump04�B<br>
     * 
     * �i����n�j<br>
     * �ϓ_�FC<br>
     * <br>
     * ���͒l :�z�񒆂�null<br>
     * ���Ғl :null�Ƃ���������ŏo�͂����B<br>
     * 
     * �����F�z��v�f����null�����o���ꂽ�ꍇ�A<br>
     * "null"�Ƃ��������񂪏o�͂���邱�Ƃ��m�F����B
     * @throws Exception ��O */
    public void testDump04() throws Exception {
        //���͒l�̐ݒ�
        final String[] num = { "1", "2", "3" };
        final String[] area = { "����", null, "����" };

        Map<String, String> map = new LinkedHashMap<String, String>();
        for (int i = 0; i < num.length; i++) {
            map.put(num[i], area[i]);
        }
        //�e�X�g���s
        String result = StringUtil.dump(map);

        //����
        StringBuffer sb = new StringBuffer();
        sb.append(LINE_SEP);
        sb.append("Map{");
        sb.append(LINE_SEP);
        sb.append("1=����");
        sb.append(LINE_SEP);
        sb.append("2=null");
        sb.append(LINE_SEP);
        sb.append("3=����");
        sb.append(LINE_SEP);
        sb.append("}");

        //���ʊm�F
        assertEquals(sb.toString(), result);
    }

    /**
     * testDump05�B<br>
     * 
     * �i����n�j<br>
     * �ϓ_�FC<br>
     * <br>
     * ���͒l :�L�[��null<br>
     * ���Ғl :"null"���\������邱��<br>
     * 
     * �����F�L�[��null�̏ꍇ�A<br>
     * "null"���\������邱�Ƃ��m�F����B
     * @throws Exception ��O */
    public void testDump05() throws Exception {
        //���͒l�̐ݒ�
        final String num = null;
        final String area = "����";

        Map<String, String> map = new LinkedHashMap<String, String>();

        map.put(num, area);
        //�e�X�g���s
        String result = StringUtil.dump(map);
        //����
        StringBuffer sb = new StringBuffer();
        sb.append(LINE_SEP);
        sb.append("Map{");
        sb.append(LINE_SEP);
        sb.append("null=����");
        sb.append(LINE_SEP);
        sb.append("}");
        //���ʊm�F
        assertEquals(sb.toString(), result);
    }

    /**
     * testDump06�B<br>
     * 
     * �i����n�j<br>
     * �ϓ_�FA<br>
     * <br>
     * ���͒l :�z��(�L�[���P��)<br>
     * ���Ғl :�z��_���v�Ώە�����<br>
     * 
     * �����F�z��_���v�Ώە�����ɕϊ�����Ă��邱�Ƃ��m�F����B
     * @throws Exception ��O */
    public void testDump06() throws Exception {
        //���͒l�̐ݒ�
        final String num = "1";
        final Vector<String> area = new Vector<String>();
        area.add(0, "����");
        area.add(1, "���");

        Map<String, Vector> map = new LinkedHashMap<String, Vector>();
        map.put(num, area);

        //�e�X�g���s
        String result = StringUtil.dump(map);

        //���ʊm�F
        assertEquals(
            LINE_SEP + "Map{" + LINE_SEP + "1=[����, ���]" + LINE_SEP + "}",
            result);
    }

    /**
     * testDump07�B<br>
     * 
     * �i����n�j<br>
     * �ϓ_�FA<br>
     * <br>
     * ���͒l :�z��(�L�[������)<br>
     * ���Ғl :�z��_���v�Ώە�����<br>
     * 
     * �����F�z��_���v�Ώە�����ɕϊ�����Ă��邱�Ƃ��m�F����B
     * @throws Exception ��O */
    public void testDump07() throws Exception {
        //���͒l�̐ݒ�
        final String num1 = "1";
        final String num2 = "2";
        Vector<String> area = new Vector<String>();
        Vector<String> tel = new Vector<String>();

        area.add(0, "����");
        area.add(1, "���");
        tel.add(0, "03");
        tel.add(1, "06");

        Map<String, Vector> map = new LinkedHashMap<String, Vector>();
        map.put(num1, area);
        map.put(num2, tel);

        //�e�X�g���s
        String result = StringUtil.dump(map);

        //����
        StringBuffer sb = new StringBuffer();
        sb.append(LINE_SEP);
        sb.append("Map{");
        sb.append(LINE_SEP);
        sb.append("1=[����, ���]");
        sb.append(LINE_SEP);
        sb.append("2=[03, 06]");
        sb.append(LINE_SEP);
        sb.append("}");

        //���ʊm�F
        assertEquals(sb.toString(), result);
    }

    /**
     * testDump08�B<br>
     * 
     * �i����n�j<br>
     * �ϓ_�FC<br>
     * <br>
     * ���͒l :map��null<br>
     * ���Ғl :null<br>
     * 
     * �����Fmap��null�̏ꍇ�A<br>
     * null���ԋp����邱�Ƃ��m�F����B
     * @throws Exception ��O */
    public void testDump08() throws Exception {
        //���͒l�̐ݒ�        
        Map map = null;

        //�e�X�g���s
        String result = StringUtil.dump(map);

        //���ʊm�F
        assertNull(result);
    }

    /**
     * testDump9�B<br>
     * 
     * �i����n�j<br>
     * �ϓ_�FA<br>
     * <br>
     * ���͒l :�L�[�������i���g���z��j<br>
     * ���Ғl :�z��_���v�Ώە�����<br>
     * 
     * �����F�z��_���v�Ώە�����ɕϊ�����Ă��邱�Ƃ��m�F����B
     * @throws Exception ��O */
    public void testDump9() throws Exception {
        //���͒l�̐ݒ�
        final String num1 = "1";
        final String num2 = "2";
        Vector<String> area = new Vector<String>();
        String tel[] = { "03", "06" };

        area.add(0, "����");
        area.add(1, "���");

        Map<String, Object> map = new LinkedHashMap<String, Object>();
        map.put(num1, area);
        map.put(num2, tel);

        //�e�X�g���s
        String result = StringUtil.dump(map);

        //����
        StringBuffer sb = new StringBuffer();
        sb.append(LINE_SEP);
        sb.append("Map{");
        sb.append(LINE_SEP);
        sb.append("1=[����, ���]");
        sb.append(LINE_SEP);
        sb.append("2={03,06}");
        sb.append(LINE_SEP);
        sb.append("}");

        //���ʊm�F
        assertEquals(sb.toString(), result);
    }

    /**
     * testGetArraysStr01�B<br>
     * 
     * �i����n�j<br>
     * �ϓ_�FA<br>
     * <br>
     * ���͒l :String�z��<br>
     * ���Ғl :�z��_���v�Ώە�����<br>
     * 
     * �����F�z��_���v�Ώە�����ɕϊ�����Ă��邱�Ƃ��m�F����B
     * ArrayUtils.toString()�̌Ăяo���m�F�̂��߂ɁA�P�P�[�X�݂̂Ƃ���B
     * 
     * @throws Exception ��O */
    public void testGetArraysStr01() throws Exception {
        //���͒l�̐ݒ�
        final String[] str = { "1", "2", "3", "4", "5" };

        //�e�X�g���s
        String result = StringUtil.getArraysStr(str);

        //���ʊm�F
        assertEquals("{1,2,3,4,5}", result);
    }

    /**
     * testHankakuToZenkaku01�B<br>
     * 
     * �i����n�j<br>
     * �ϓ_�FA<br>
     * <br>
     * ���͒l�F���p����(�ꕶ��)<br>
     * ���Ғl�F�S�p����
     * 
     * �����F���p���ʉp�����ꕶ�����S�p���ʉp�����ɕϊ�����邱�Ƃ��m�F����B
     * @throws Exception ��O */
    public void testHankakuToZenkaku01() throws Exception {
        //���͒l�̐ݒ�
        String input = "A";

        //�e�X�g���s
        String result = StringUtil.hankakuToZenkaku(input);

        //���ʊm�F
        assertEquals("�`", result);
    }

    /**
     * testHankakuToZenkaku02�B<br>
     * 
     * �i����n�j<br>
     * �ϓ_�FA<br>
     * <br>
     * ���͒l�F���p����(��������)<br>
     * ���Ғl�F�S�p����
     * 
     * �����F���p���ʕ����������A�S�p���ʕ����ɕϊ�����邱�Ƃ��m�F����B
     * @throws Exception ��O */
    public void testHankakuToZenkaku02() throws Exception {
        //���͒l�̐ݒ�
        String input = "��!A8";

        //�e�X�g���s
        String result = StringUtil.hankakuToZenkaku(input);

        //���ʊm�F
        assertEquals("�A�J�I�`�W", result);
    }

    /**
     * testHankakuToZenkaku03�B<br>
     * 
     * �i����n�j<br>
     * �ϓ_�FA<br>
     * <br>
     * ���͒l�F���p�T(�ꕶ��)<br>
     * ���Ғl�F�S�p�T
     * 
     * �����F���p�T�ꕶ�����S�p�T�ɕϊ�����邱�Ƃ��m�F����B
     * @throws Exception ��O */
    public void testHankakuToZenkaku03() throws Exception {
        //���͒l�̐ݒ�
        String input = "�";

        //�e�X�g���s
        String result = StringUtil.hankakuToZenkaku(input);

        //���ʊm�F
        assertEquals("�T", result);
    }

    /**
     * testHankakuToZenkaku04�B<br>
     * 
     * �i����n�j<br>
     * �ϓ_�FA<br>
     * <br>
     * ���͒l�F���p�J�T�^�n�E(��������)<br>
     * ���Ғl�F�S�p�J�T�^�n�E
     * 
     * �����F���p�J�T�^�n�E�������A�S�p�J�T�^�n�E�ɕϊ�����邱�Ƃ��m�F����B
     * @throws Exception ��O */
    public void testHankakuToZenkaku04() throws Exception {
        //���͒l�̐ݒ�
        String input = "���ʳ";

        //�e�X�g���s
        String result = StringUtil.hankakuToZenkaku(input);

        //���ʊm�F
        assertEquals("�J�T�^�n�E", result);
    }

    /**
     * testHankakuToZenkaku05�B<br>
     * 
     * �i����n�j<br>
     * �ϓ_�FA<br>
     * <br>
     * ���͒l�F���p���_��(�ꕶ��)<br>
     * ���Ғl�F�S�p���_�K
     * 
     * �����F���p���_�ꕶ�����S�p���_�ɕϊ�����邱�Ƃ��m�F����B
     * @throws Exception ��O */
    public void testHankakuToZenkaku05() throws Exception {
        //���͒l�̐ݒ�
        String input = "��";

        //�e�X�g���s
        String result = StringUtil.hankakuToZenkaku(input);

        //���ʊm�F
        assertEquals("�K", result);
    }

    /**
     * testHankakuToZenkaku06�B<br>
     * 
     * �i����n�j<br>
     * �ϓ_�FA<br>
     * <br>
     * ���͒l�F���p���_�޻����޳�(��������)<br>
     * ���Ғl�F�S�p���_�K�U�_�o��
     * 
     * �����F���p���_�����������A�S�p���_�ɕϊ�����邱�Ƃ��m�F����B
     * @throws Exception ��O */
    public void testHankakuToZenkaku06() throws Exception {
        //���͒l�̐ݒ�
        String input = "�޻����޳�";

        //�e�X�g���s
        String result = StringUtil.hankakuToZenkaku(input);

        //���ʊm�F
        assertEquals("�K�U�_�o��", result);
    }

    /**
     * testHankakuToZenkaku07�B<br>
     * 
     * �i����n�j<br>
     * �ϓ_�FA<br>
     * <br>
     * ���͒l�F���p�����_�ߍs(�ꕶ��)<br>
     * ���Ғl�F�S�p�����_�p�s
     * 
     * �����F���p�����_(�ꕶ��)���S�p�����_�ɕϊ�����邱�Ƃ��m�F����B
     * @throws Exception ��O */
    public void testHankakuToZenkaku07() throws Exception {
        //���͒l�̐ݒ�
        String input = "��";

        //�e�X�g���s
        String result = StringUtil.hankakuToZenkaku(input);

        //���ʊm�F
        assertEquals("�|", result);
    }

    /**
     * testHankakuToZenkaku08�B<br>
     * 
     * �i����n�j<br>
     * �ϓ_�FA<br>
     * <br>
     * ���͒l�F���p�����_�ߍs(��������)<br>
     * ���Ғl�F�S�p�����_�p�s
     * 
     * �����F���p�����_�����������A�S�p�����_�ɕϊ�����邱�Ƃ��m�F����B
     * @throws Exception ��O */
    public void testHankakuToZenkaku08() throws Exception {
        //���͒l�̐ݒ�
        String input = "����������";

        //�e�X�g���s
        String result = StringUtil.hankakuToZenkaku(input);

        //���ʊm�F
        assertEquals("�p�s�v�y�|", result);
    }

    /**
     * testHankakuToZenkaku09�B<br>
     * 
     * �i����n�jA<br>
     * �ϓ_�F<br>
     * <br>
     * ���͒l�F���p�<br>
     * ���Ғl�F�S�p��
     * 
     * �����F���p�����S�p���ɕϊ�����邱�Ƃ��m�F����B
     * @throws Exception ��O */
    public void testHankakuToZenkaku09() throws Exception {
        //���͒l�̐ݒ�
        String input = "�";

        //�e�X�g���s
        String result = StringUtil.hankakuToZenkaku(input);

        //���ʊm�F
        assertEquals("��", result);
    }

    /**
     * testHankakuToZenkaku10�B<br>
     * 
     * �i����n�jA<br>
     * �ϓ_�F<br>
     * <br>
     * ���͒l�F���p�<br>
     * ���Ғl�F�S�p��
     * 
     * �����F���p�����S�p���ɕϊ�����邱�Ƃ��m�F����B
     * @throws Exception ��O */
    public void testHankakuToZenkaku10() throws Exception {
        //���͒l�̐ݒ�
        String input = "�";

        //�e�X�g���s
        String result = StringUtil.hankakuToZenkaku(input);

        //���ʊm�F
        assertEquals("��", result);
    }

    /**
     * testHankakuToZenkaku11�B<br>
     * 
     * �i����n�j<br>
     * �ϓ_�FA<br>
     * <br>
     * ���͒l�F���p�"<br>
     * ���Ғl�F�S�p���h
     * 
     * �����F���p���_�����S�p���_���ɕϊ�����邱�Ƃ��m�F����B
     * @throws Exception ��O */
    public void testHankakuToZenkaku11() throws Exception {
        //���͒l�̐ݒ�
        String input = "��";

        //�e�X�g���s
        String result = StringUtil.hankakuToZenkaku(input);

        //���ʊm�F
        assertEquals("\u30f7", result);
    }

    /**
     * testHankakuToZenkaku12�B<br>
     * 
     * �i����n�j<br>
     * �ϓ_�FA<br>
     * <br>
     * ���͒l�F���p�"<br>
     * ���Ғl�F�S�p���h
     * 
     * �����F���p���_�����S�p���_���ɕϊ�����邱�Ƃ��m�F����B
     * @throws Exception ��O */
    public void testHankakuToZenkaku12() throws Exception {
        //���͒l�̐ݒ�
        String input = "��";

        //�e�X�g���s
        String result = StringUtil.hankakuToZenkaku(input);

        //���ʊm�F
        assertEquals("\u30fa", result);
    }

    /**
     * testHankakuToZenkaku13�B<br>
     * 
     * �i����n�jA<br>
     * �ϓ_�F<br>
     * <br>
     * ���͒l�F���p�<br>
     * ���Ғl�F�S�p��
     * 
     * �����F���p����������̓r���ɂ��鎞�A�S�p���ɕϊ�����邱�Ƃ��m�F����B
     * @throws Exception ��O */
    public void testHankakuToZenkaku13() throws Exception {
        //���͒l�̐ݒ�
        String input = "A�1";

        //�e�X�g���s
        String result = StringUtil.hankakuToZenkaku(input);

        //���ʊm�F
        assertEquals("�`���P", result);
    }

    /**
     * testHankakuToZenkaku14�B<br>
     * 
     * �i����n�jA<br>
     * �ϓ_�F<br>
     * <br>
     * ���͒l�F���p�<br>
     * ���Ғl�F�S�p��
     * 
     * �����F���p����������̓r���ɂ��鎞�A�S�p���ɕϊ�����邱�Ƃ��m�F����B
     * @throws Exception ��O */
    public void testHankakuToZenkaku14() throws Exception {
        //���͒l�̐ݒ�
        String input = "B�8";

        //�e�X�g���s
        String result = StringUtil.hankakuToZenkaku(input);

        //���ʊm�F
        assertEquals("�a���W", result);
    }

    /**
     * testHankakuToZenkaku15�B<br>
     * 
     * �i����n�jA<br>
     * �ϓ_�F<br>
     * <br>
     * ���͒l�F���p��<br>
     * ���Ғl�F�S�p��
     * 
     * �����F���p���_����������̓r���ɂ��鎞�A�S�p���_���ɕϊ�����邱�Ƃ��m�F����B
     * @throws Exception ��O */
    public void testHankakuToZenkaku15() throws Exception {
        //���͒l�̐ݒ�
        String input = "B��8";

        //�e�X�g���s
        String result = StringUtil.hankakuToZenkaku(input);

        //���ʊm�F
        assertEquals("�a\u30f7�W", result);
    }

    /**
     * testHankakuToZenkaku16�B<br>
     * 
     * �i����n�jA<br>
     * �ϓ_�F<br>
     * <br>
     * ���͒l�F���p��<br>
     * ���Ғl�F�S�p��
     * 
     * �����F���p���_����������̓r���ɂ��鎞�A�S�p���_���ɕϊ�����邱�Ƃ��m�F����B
     * @throws Exception ��O */
    public void testHankakuToZenkaku16() throws Exception {
        //���͒l�̐ݒ�
        String input = "B��8";

        //�e�X�g���s
        String result = StringUtil.hankakuToZenkaku(input);

        //���ʊm�F
        assertEquals("�a\u30fa�W", result);
    }

    /**
     * testHankakuToZenkaku17�B<br>
     * 
     * �i����n�jA<br>
     * �ϓ_�F<br>
     * <br>
     * ���͒l�F���p�<br>
     * ���Ғl�F�S�p��
     * 
     * �����F���p����������̖����ɂ��鎞�A�S�p���ɕϊ�����邱�Ƃ��m�F����B
     * @throws Exception ��O */
    public void testHankakuToZenkaku17() throws Exception {
        //���͒l�̐ݒ�
        String input = "�A�";

        //�e�X�g���s
        String result = StringUtil.hankakuToZenkaku(input);

        //���ʊm�F
        assertEquals("�A�`��", result);
    }

    /**
     * testHankakuToZenkaku18�B<br>
     * 
     * �i����n�jA<br>
     * �ϓ_�F<br>
     * <br>
     * ���͒l�F���p�<br>
     * ���Ғl�F�S�p��
     * 
     * �����F���p����������̖����ɂ��鎞�A�S�p���ɕϊ�����邱�Ƃ��m�F����B
     * @throws Exception ��O */
    public void testHankakuToZenkaku18() throws Exception {
        //���͒l�̐ݒ�
        String input = "tB�";

        //�e�X�g���s
        String result = StringUtil.hankakuToZenkaku(input);

        //���ʊm�F
        assertEquals("���a��", result);
    }

    /**
     * testHankakuToZenkaku19�B<br>
     * 
     * �i����n�jA<br>
     * �ϓ_�F<br>
     * <br>
     * ���͒l�F���p��<br>
     * ���Ғl�F�S�p��
     * 
     * �����F���p���_����������̖����ɂ��鎞�A�S�p���_���ɕϊ�����邱�Ƃ��m�F����B
     * @throws Exception ��O */
    public void testHankakuToZenkaku19() throws Exception {
        //���͒l�̐ݒ�
        String input = "�B��";

        //�e�X�g���s
        String result = StringUtil.hankakuToZenkaku(input);

        //���ʊm�F
        assertEquals("�T�a\u30f7", result);
    }

    /**
     * testHankakuToZenkaku20�B<br>
     * 
     * �i����n�jA<br>
     * �ϓ_�F<br>
     * <br>
     * ���͒l�F���p��<br>
     * ���Ғl�F�S�p��
     * 
     * �����F���p���_����������̖����ɂ��鎞�A�S�p���_���ɕϊ�����邱�Ƃ��m�F����B
     * @throws Exception ��O */
    public void testHankakuToZenkaku20() throws Exception {
        //���͒l�̐ݒ�
        String input = "�B��";

        //�e�X�g���s
        String result = StringUtil.hankakuToZenkaku(input);

        //���ʊm�F
        assertEquals("�A�a\u30fa", result);
    }

    /**
     * testHankakuToZenkaku21�B<br>
     * 
     * �i����n�j<br>
     * �ϓ_�FA<br>
     * <br>
     * ���͒l�F���p�S�p��������"<br>
     * ���Ғl�F�S�p����
     * 
     * �����F���͒l�ɑS�p�����Ɣ��p�������������������̂�ݒ肵�A<br>
     * �S�đS�p�����ɕϊ�����邱�Ƃ��m�F����B
     * @throws Exception ��O */
    public void testHankakuToZenkaku21() throws Exception {
        //���͒l�̐ݒ�
        String input = "�T��Y�ރ\";

        //�e�X�g���s
        String result = StringUtil.hankakuToZenkaku(input);

        //���ʊm�F
        assertEquals("�T�V�Y�[�\", result);
    }

    /**
     * testHankakuToZenkaku22�B<br>
     * 
     * �i����n�j<br>
     * �ϓ_�FC<br>
     * <br>
     * ���͒l�Fnull<br>
     * ���Ғl�Fnull
     * �����F�u���Ώە�����null�̎��Anull��߂�l�Ƃ��������I�����邱�Ƃ��m�F����B
     * @throws Exception ��O */
    public void testHankakuToZenkaku22() throws Exception {
        //���͒l�̐ݒ�
        String input = null;

        //�e�X�g���s
        String result = StringUtil.hankakuToZenkaku(input);

        //���ʊm�F
        assertNull(result);
    }

    /**
     * testHankakuToZenkaku23�B<br>
     * 
     * �i����n�j<br>
     * �ϓ_�FC<br>
     * <br>
     * ���͒l�F�󕶎�<br>
     * ���Ғl�F�󕶎�
     * 
     * �����F�u���Ώە����񂪋󕶎��̎��A�󕶎���߂�l�Ƃ��������I�����邱�Ƃ��m�F����B
     * @throws Exception ��O */
    public void testHankakuToZenkaku23() throws Exception {
        //���͒l�̐ݒ�
        String input = "";

        //�e�X�g���s
        String result = StringUtil.hankakuToZenkaku(input);

        //���ʊm�F
        assertEquals("", result);
    }

    /**
     * testHankakuToZenkaku24�B<br>
     * 
     * �i����n�j<br>
     * �ϓ_�FA<br>
     * <br>
     * ���͒l�F���p�����_�����߷�<br>
     * ���Ғl�F�S�p�����_�����J�K�L�K
     * 
     * �����F���p�����_�������S�p�����_�����ɕϊ�����邱�Ƃ��m�F����B
     * @throws Exception ��O */
    public void testHankakuToZenkaku24() throws Exception {
        //���͒l�̐ݒ�
        String input = "�߷�";

        //�e�X�g���s
        String result = StringUtil.hankakuToZenkaku(input);

        //���ʊm�F
        assertEquals("�J�K�L�K", result);
    }

    /**
     * testZenkakuToHankaku01�B<br>
     * 
     * �i����n�j<br>
     * �ϓ_�FA<br>
     * <br>
     * ���͒l�F�S�p����<br>
     * ���Ғl�F���p�����i���������ϊ��j<br>
     * 
     * �����F�S�p���������p�����ɕ��������ϊ�����邱�Ƃ��m�F����B
     * @throws Exception ��O */
    public void testZenkakuToHankaku01() throws Exception {
        //���͒l�̐ݒ�
        String input = "�`�I�A";

        //�e�X�g���s
        String result = StringUtil.zenkakuToHankaku(input);

        //���ʊm�F
        assertEquals("A!�", result);
    }

    /**
     * testZenkakuToHankaku02�B<br>
     * 
     * �i����n�j<br>
     * �ϓ_�FA<br>
     * <br>
     * ���͒l�F�S�p����<br>
     * ���Ғl�F���p�����i�ꕶ���ϊ��j<br>
     * 
     * �����F�S�p�����ɑ΂��Ĕ��p�����Ɉꕶ���ϊ�����邱�Ƃ��m�F����B
     * @throws Exception ��O */
    public void testZenkakuToHankaku02() throws Exception {
        //���͒l�̐ݒ�
        String input = "�`";

        //�e�X�g���s
        String result = StringUtil.zenkakuToHankaku(input);

        //���ʊm�F
        assertEquals("A", result);
    }

    /**
     * testZenkakuToHankaku02�B<br>
     * 
     * �i����n�j<br>
     * �ϓ_�FA<br>
     * <br>
     * ���͒l�F�S�p�J�T�^�n<br>
     * ���Ғl�F���p�J�T�^�n�j<br>
     * 
     * �����F�S�p�J�T�^�n�����p�J�T�^�n�ɕϊ�����邱�Ƃ��m�F����B
     * @throws Exception ��O */
    public void testZenkakuToHankaku03() throws Exception {
        //���͒l�̐ݒ�
        String input = "�J�T�^�n";

        //�e�X�g���s
        String result = StringUtil.zenkakuToHankaku(input);

        //���ʊm�F
        assertEquals("����", result);
    }
    /**
     * testZenkakuToHankaku04�B<br>
     * 
     * �i����n�j<br>
     * �ϓ_�FA<br>
     * <br>
     * ���͒l�F�S�p���_<br>
     * ���Ғl�F���p���_<br>
     * 
     * �����F�S�p���_�������񕶎��ɕ�������ĕ\������邱�Ƃ��m�F����B
     * @throws Exception ��O */
    public void testZenkakuToHankaku04() throws Exception {
        //���͒l�̐ݒ�
        String input = "�K�U�_";

        //�e�X�g���s
        String result = StringUtil.zenkakuToHankaku(input);

        //���ʊm�F
        assertEquals("�޻���", result);
    }

    /**
     * testZenkakuToHankaku05�B<br>
     * 
     * �i����n�j<br>
     * �ϓ_�FA<br>
     * <br>
     * ���͒l�F�S�p�����_����<br>
     * ���Ғl�F���p�����_����<br>
     * 
     * �����F�S�p���_�E�����_�������񕶎��ɕ�������ĕ\������邱�Ƃ��m�F����B
     * @throws Exception ��O */
    public void testZenkakuToHankaku05() throws Exception {
        //���͒l�̐ݒ�
        String input = "�p�|";

        //�e�X�g���s
        String result = StringUtil.zenkakuToHankaku(input);

        //���ʊm�F
        assertEquals("����", result);
    }

    /**
     * testZenkakuToHankaku06�B<br>
     * 
     * �i����n�j<br>
     * �ϓ_�FA<br>
     * <br>
     * ���͒l�F�S�p��<br>
     * ���Ғl�F���p��<br>
     * 
     * �����F�S�p�������p���ɕϊ�����邱�Ƃ��m�F����B
     * @throws Exception ��O */
    public void testZenkakuToHankaku06() throws Exception {
        //���͒l�̐ݒ�
        String input = "�`��";

        //�e�X�g���s
        String result = StringUtil.zenkakuToHankaku(input);

        //���ʊm�F
        assertEquals("A�", result);
    }

    /**
     * testZenkakuToHankaku07�B<br>
     * 
     * �i����n�j<br>
     * �ϓ_�FA<br>
     * <br>
     * ���͒l�F�S�p��<br>
     * ���Ғl�F���p��<br>
     * 
     * �����F�S�p�������p���ɕϊ�����邱�Ƃ��m�F����B
     * @throws Exception ��O */
    public void testZenkakuToHankaku07() throws Exception {
        //���͒l�̐ݒ�
        String input = "�`��";

        //�e�X�g���s
        String result = StringUtil.zenkakuToHankaku(input);

        //���ʊm�F
        assertEquals("A�", result);
    }

    /**
     * testZenkakuToHankaku08�B<br>
     * 
     * �i����n�j<br>
     * �ϓ_�FA<br>
     * <br>
     * ���͒l�F�S�p���_��<br>
     * ���Ғl�F���p���_��<br>
     * 
     * �����F�S�p���_�������p���_܂ɕϊ�����邱�Ƃ��m�F����B
     * @throws Exception ��O */
    public void testZenkakuToHankaku08() throws Exception {
        //���͒l�̐ݒ�
        String input = "�A\u30f7";

        //�e�X�g���s
        String result = StringUtil.zenkakuToHankaku(input);

        //���ʊm�F
        assertEquals("���", result);
    }

    /**
     * testZenkakuToHankaku09�B<br>
     * 
     * �i����n�j<br>
     * �ϓ_�FA<br>
     * <br>
     * ���͒l�F�S�p���_��<br>
     * ���Ғl�F���p���_��<br>
     * 
     * �����F�S�p���_�������p���_��ɕϊ�����邱�Ƃ��m�F����B
     * @throws Exception ��O */
    public void testZenkakuToHankaku09() throws Exception {
        //���͒l�̐ݒ�
        String input = "\u30fa��";

        //�e�X�g���s
        String result = StringUtil.zenkakuToHankaku(input);

        //���ʊm�F
        assertEquals("�޳�", result);
    }

    /**
     * testZenkakuToHankaku10�B<br>
     * 
     * �i����n�j<br>
     * �ϓ_�FA<br>
     * <br>
     * ���͒l�F���p�E�S�p��������<br>
     * ���Ғl�F���p�����ɑS�ĕϊ�����邱��<br>
     * 
     * �����F���p�E�S�p�����������S�Ĕ��p�ɕϊ�����邱�Ƃ��m�F����B
     * @throws Exception ��O */
    public void testZenkakuToHankaku10() throws Exception {
        //���͒l�̐ݒ�
        String input = "�T\u30fa�A�s";

        //�e�X�g���s
        String result = StringUtil.zenkakuToHankaku(input);

        //���ʊm�F
        assertEquals("��޲A��", result);
    }

    /**
     * testZenkakuToHankaku11�B<br>
     * 
     * �i����n�j<br>
     * �ϓ_�FC<br>
     * <br>
     * ���͒l�F�󕶎�<br>
     * ���Ғl�F�󕶎�<br>
     * 
     * �����F�u���Ώە����񂪋󕶎��̎��A�󕶎���߂�l�Ƃ��������I�����邱�Ƃ��m�F����B
     * @throws Exception ��O */
    public void testZenkakuToHankaku11() throws Exception {
        //���͒l�̐ݒ�
        String input = "";

        //�e�X�g���s
        String result = StringUtil.zenkakuToHankaku(input);

        //���ʊm�F
        assertEquals("", result);
    }

    /**
     * testZenkakuToHankakuu12�B<br>
     * 
     * �i����n�j<br>
     * �ϓ_�FC<br>
     * <br>
     * ���͒l�Fnull<br>
     * ���Ғl�Fnull<br>
     * 
     * �����F�u���Ώە�����null�̎��Anull��߂�l�Ƃ��������I�����邱�Ƃ��m�F����B
     * @throws Exception ��O */
    public void testZenkakuToHankaku12() throws Exception {
        //���͒l�̐ݒ�
        String input = null;

        //�e�X�g���s
        String result = StringUtil.zenkakuToHankaku(input);

        //���ʊm�F
        assertEquals(null, result);
    }

    /**
     * testZenkakuToHankakuu13�B<br>
     * 
     * �i����n�j<br>
     * �ϓ_�FA<br>
     * <br>
     * ���͒l�F���p���s�\�ȕ���<br>
     * ���Ғl�F�S�p����<br>
     * 
     * �����F���p���s�\�ȕ����񂪂��̂܂ܑS�p������ŏo�͂���邱�Ƃ��m�F����B
     * @throws Exception ��O */
    public void testZenkakuToHankaku13() throws Exception {
        //���͒l�̐ݒ�
        String input = "���͒l";

        //�e�X�g���s
        String result = StringUtil.zenkakuToHankaku(input);

        //���ʊm�F
        assertEquals("���͒l", result);
    }

    /**
     * testFilter01�B<br>
     * 
     * �i����n�j<br>
     * �ϓ_�FA<br>
     * <br>
     * ���͒l�F����������iHTML���ɂ��̂܂܏o�͂���Ɩ�肪���镶�����ꕔ�܂ށj<br>
     * ���Ғl�FHTML���^����<br>
     * 
     * �����F�񕶎��ȏ�̕����񒆁A�ꕔ��HTML���^�����ɕϊ�����邱�Ƃ��m�F����B
     * @throws Exception ��O */
    public void testFilter01() throws Exception {
        //���͒l�̐ݒ�
        String input = "a & b";

        //�e�X�g���s
        String result = StringUtil.filter(input);

        //���ʊm�F
        assertEquals("a &amp; b", result);
    }

    /**
     * testFilter02�B<br>
     * 
     * �i����n�j<br>
     * �ϓ_�FA<br>
     * <br>
     * ���͒l�F����������iHTML���ɂ��̂܂܏o�͂���Ɩ�肪���镶�����܂܂Ȃ��j<br>
     * ���Ғl�F���̂܂܏o�͂����<br>
     * 
     * �����FHTML���^�����ɕϊ�����邱�ƂȂ����̂܂܏o�͂���邱�Ƃ��m�F����B
     * @throws Exception ��O */
    public void testFilter02() throws Exception {
        //���͒l�̐ݒ�
        String input = "abc";

        //�e�X�g���s
        String result = StringUtil.filter(input);

        //���ʊm�F
        assertEquals("abc", result);
    }

    /**
     * testFilter03�B<br>
     * 
     * �i����n�j<br>
     * �ϓ_�FA<br>
     * <br>
     * ���͒l�FHTML���ɂ��̂܂܏o�͂���Ɩ�肪���镶���i���������j<br>
     * ���Ғl�FHTML���^����<br>
     * 
     * �����FHTML���^�����ɑS�Ă��ϊ�����邱�Ƃ��m�F����B
     * @throws Exception ��O */
    public void testFilter03() throws Exception {
        //���͒l�̐ݒ�
        String input = "< & > \"";

        //�e�X�g���s
        String result = StringUtil.filter(input);

        //���ʊm�F
        assertEquals("&lt; &amp; &gt; &quot;", result);
    }

    /**
     * testFilter04�B<br>
     * 
     * �i����n�j<br>
     * �ϓ_�FA<br>
     * <br>
     * ���͒l�FHTML���ɂ��̂܂܏o�͂���Ɩ�肪���镶���i�ꕶ���ϊ��j<br>
     * ���Ғl�FHTML���^����<br>
     * 
     * �����FHTML���^�����Ɉꕶ���ϊ�����邱�Ƃ��m�F����B
     * @throws Exception ��O */
    public void testFilter04() throws Exception {
        //���͒l�̐ݒ�
        String input = "<";

        //�e�X�g���s
        String result = StringUtil.filter(input);

        //���ʊm�F
        assertEquals("&lt;", result);
    }

    /**
     * testFilter05�B<br>
     * 
     * �i����n�j<br>
     * �ϓ_�FA<br>
     * <br>
     * ���͒l�FHTML���ɂ��̂܂܏o�͂��Ă����̂Ȃ������i�ꕶ���ϊ��j<br>
     * ���Ғl�F���̂܂܏o�͂���邱��<br>
     * 
     * �����FHTML���^�����ɕϊ�����邱�ƂȂ��A���̂܂܏o�͂���邱�Ƃ��m�F����B
     * @throws Exception ��O */
    public void testFilter05() throws Exception {
        //���͒l�̐ݒ�
        String input = "�A";

        //�e�X�g���s
        String result = StringUtil.filter(input);

        //���ʊm�F
        assertEquals("�A", result);
    }

    /**
     * testFilter06�B<br>
     * 
     * �i�ُ�n�j<br>
     * �ϓ_�FC<br>
     * <br>
     * ���͒l�Fnull<br>
     * ���Ғl�FNullPointerException<br>
     * 
     * �����F�u���Ώە�����null�̂Ƃ��ANullPointerException��<br>
     * �������邱�Ƃ��m�F����B
     * @throws Exception ��O */
    public void testFilter06() throws Exception {
        //���͒l�̐ݒ�
        String input = null;

        //�e�X�g���s
        try {
            String result = StringUtil.filter(input);
            fail();
        } catch (NullPointerException e) {
            return;
        }
    }

    /**
     * testFilter07�B<br>
     * 
     * �i����n�j<br>
     * �ϓ_�FC<br>
     * <br>
     * ���͒l�F�󕶎�<br>
     * ���Ғl�F�󕶎�<br>
     * 
     * �����FHTML���^�����ɕϊ�����邱�ƂȂ��A�󕶎������̂܂܏o�͂���邱�Ƃ��m�F����B
     * @throws Exception ��O */
    public void testFilter07() throws Exception {
        //���͒l�̐ݒ�
        String input = "";

        //�e�X�g���s
        String result = StringUtil.filter(input);

        //���ʊm�F
        assertEquals("", result);
    }

    /**
     * testToLikeCondition01�B<br>
     * 
     * �i����n�j<br>
     * �ϓ_�FA<br>
     * <br>
     * ���͒l�F��������������<br>
     * ���Ғl�FLIKE�q��̃p�^�[��������(�ꕶ���ϊ��j<br>
     * 
     * �����F�������������񂪈ꕶ��LIKE�q��̃p�^�[��������ɕϊ�����邱�Ƃ��m�F����B
     * @throws Exception ��O */
    public void testToLikeCondition01() throws Exception {
        //���͒l�̐ݒ�
        String input = "a%";

        //�e�X�g���s
        String result = StringUtil.toLikeCondition(input);

        //���ʊm�F
        assertEquals("a~%%", result);
    }

    /**
     * testToLikeCondition02�B<br>
     * 
     * �i����n�j<br>
     * �ϓ_�FA<br>
     * <br>
     * ���͒l�F�G�X�P�[�v����<br>
     * ���Ғl�F�G�X�P�[�v<br>
     * 
     * �����FLIKE�q��̃p�^�[��������ŗp����G�X�P�[�v������<br>
     * �G�X�P�[�v�����ŃG�X�P�[�v����邱�Ƃ��m�F����B
     * @throws Exception ��O */
    public void testToLikeCondition02() throws Exception {
        //���͒l�̐ݒ�
        String input = "~";

        //�e�X�g���s
        String result = StringUtil.toLikeCondition(input);

        //���ʊm�F
        assertEquals("~~%", result);
    }

    /**
     * testToLikeCondition03�B<br>
     * 
     * �i����n�j<br>
     * �ϓ_�FA<br>
     * <br>
     * ���͒l�F��������������<br>
     * ���Ғl�FLIKE�q��̃p�^�[��������i���������ϊ��j<br>
     * 
     * �����F�������������񂪕�����������LIKE�q��̃p�^�[��������ɕϊ�����邱�Ƃ��m�F����B
     * @throws Exception ��O */
    public void testToLikeCondition03() throws Exception {
        //���͒l�̐ݒ�
        String input = "_a%";

        //�e�X�g���s
        String result = StringUtil.toLikeCondition(input);

        //���ʊm�F
        assertEquals("~_a~%%", result);
    }

    /**
     * testToLikeCondition04�B<br>
     * 
     * �i����n�j<br>
     * �ϓ_�FA<br>
     * <br>
     * ���͒l�F��������������ȊO�̕�����<br>
     * ���Ғl�F���̂܂܏o�͂���邱��<br>
     * 
     * �����F��������������ȊO�̕�����͕ϊ�����邱�ƂȂ��A���̂܂܏o�͂���邱�Ƃ��m�F����B
     * @throws Exception ��O */
    public void testToLikeCondition04() throws Exception {
        //���͒l�̐ݒ�
        String input = "aa";

        //�e�X�g���s
        String result = StringUtil.toLikeCondition(input);

        //���ʊm�F
        assertEquals("aa%", result);
    }
    /**
     * testToLikeCondition05�B<br>
     * 
     * �i�ُ�n�j<br>
     * �ϓ_�FC<br>
     * <br>
     * ���͒l�Fnull�j<br>
     * ���Ғl�FNullPointerException<br>
     * 
     * �����F�u���Ώە�����null�̂Ƃ��ANullPointerException��<br>
     * �������邱�Ƃ��m�F����B
     * @throws Exception ��O */
    public void testToLikeCondition05() throws Exception {
        //���͒l�̐ݒ�
        String input = null;

        //�e�X�g���s
        try {
            String result = StringUtil.toLikeCondition(input);
            fail();
        } catch (NullPointerException e) {
            return;
        }
    }

    /**
     * testToLikeCondition06�B<br>
     * 
     * �i����n�j<br>
     * �ϓ_�FC<br>
     * <br>
     * ���͒l�F�󕶎�<br>
     * ���Ғl�F%<br>
     * 
     * �����F�u���Ώە����񂪋󕶎��̂Ƃ��A"%"���o�͂���邱�Ƃ��m�F����B
     * @throws Exception ��O */
    public void testToLikeCondition06() throws Exception {
        //���͒l�̐ݒ�
        String input = "";

        //�e�X�g���s
        String result = StringUtil.toLikeCondition(input);

        //���ʊm�F
        assertEquals("%", result);
    }

    /**
     * testCapitalizeInitial01�B<br>
     * 
     * �i����n�j<br>
     * �ϓ_�FC<br>
     * <br>
     * ���͒l�F"abc"<br>
     * ���Ғl�F"Abc"<br>
     * 
     * �����F�ꕶ���ڂ�����ɑ啶���ɕϊ��ł���ꍇ
     * @throws Exception ��O */
    public void testCapitalizeInitial01() throws Exception {
        //���͒l�̐ݒ�
        String input = "abc";

        //�e�X�g���s
        String result = StringUtil.capitalizeInitial(input);

        //���ʊm�F
        assertEquals("Abc", result);
    }

    /**
     * testCapitalizeInitial02�B<br>
     * 
     * �i����n�j<br>
     * �ϓ_�FC<br>
     * <br>
     * ���͒l�F"Abc"<br>
     * ���Ғl�F"Abc"<br>
     * 
     * �����F�ꕶ���ڂ��ŏ�����啶���̏ꍇ
     * @throws Exception ��O */
    public void testCapitalizeInitial02() throws Exception {
        //���͒l�̐ݒ�
        String input = "Abc";

        //�e�X�g���s
        String result = StringUtil.capitalizeInitial(input);

        //���ʊm�F
        assertEquals("Abc", result);
    }

    /**
     * testCapitalizeInitial03�B<br>
     * 
     * �i����n�j<br>
     * �ϓ_�FC<br>
     * <br>
     * ���͒l�F"123"<br>
     * ���Ғl�F"123"<br>
     * 
     * �����F�ꕶ���ڂ��啶���ɕϊ��ł��Ȃ������̏ꍇ
     * @throws Exception ��O */
    public void testCapitalizeInitial03() throws Exception {
        //���͒l�̐ݒ�
        String input = "123";

        //�e�X�g���s
        String result = StringUtil.capitalizeInitial(input);

        //���ʊm�F
        assertEquals("123", result);
    }

    /**
     * testCapitalizeInitial04�B<br>
     * 
     * �i����n�j<br>
     * �ϓ_�FC<br>
     * <br>
     * ���͒l�F""<br>
     * ���Ғl�F""<br>
     * 
     * �����F�󔒂̏ꍇ
     * @throws Exception ��O */
    public void testCapitalizeInitial04() throws Exception {
        //���͒l�̐ݒ�
        String input = "";

        //�e�X�g���s
        String result = StringUtil.capitalizeInitial(input);

        //���ʊm�F
        assertEquals("", result);
    }

    /**
     * testCapitalizeInitial05�B<br>
     * 
     * �i����n�j<br>
     * �ϓ_�FC<br>
     * <br>
     * ���͒l�F<br>
     * ���Ғl�Fnull<br>
     * 
     * �����Fnull�̏ꍇ
     * @throws Exception ��O */
    public void testCapitalizeInitial05() throws Exception {
        //���͒l�̐ݒ�
        String input = null;

        //�e�X�g���s
        String result = StringUtil.capitalizeInitial(input);

        //���ʊm�F
        assertEquals(null, result);
    }

    /**
     * testGetByteLength01<br>
     * 
     * �i����n�j<br>
     * �ϓ_�FC<br>
     * <br>
     * ���͒l�Fvalue=null<br>
     * ���Ғl�F0<br>
     * 
     * �����F����value��null�̏ꍇ<br>
     * @throws Exception ��O */
    public void testGetByteLength01() throws Exception {
        // �O����
        String value = null;
        String encoding = null;
        
        // �e�X�g���s
        int i = StringUtil.getByteLength(value, encoding);
        
        // ����
        assertEquals(0, i);
    }
    
    /**
     * testGetByteLength02<br>
     * 
     * �i����n�j<br>
     * �ϓ_�FC<br>
     * <br>
     * ���͒l�Fvalue=�󕶎�<br>
     * ���Ғl�F0<br>
     * 
     * �����F����value���󕶎��̏ꍇ<br>
     * @throws Exception ��O */
    public void testGetByteLength02() throws Exception {
        // �O����
        String value = "";
        String encoding = null;
        
        // �e�X�g���s
        int i = StringUtil.getByteLength(value, encoding);
        
        // ����
        assertEquals(0, i);
    }
    
    /**
     * testGetByteLength03<br>
     * 
     * �i����n�j<br>
     * �ϓ_�FC<br>
     * <br>
     * ���͒l�Fvalue="aaa", encoding=null<br>
     * ���Ғl�F3<br>
     * 
     * �����F����encoding��null�̏ꍇ<br>
     * @throws Exception ��O */
    public void testGetByteLength03() throws Exception {
        // �O����
        String value = "aaa";
        String encoding = null;
        
        // �e�X�g���s
        int i = StringUtil.getByteLength(value, encoding);
        
        // ����
        assertEquals(3, i);
    }
    
    /**
     * testGetByteLength04<br>
     * 
     * �i����n�j<br>
     * �ϓ_�FC<br>
     * <br>
     * ���͒l�Fvalue="aaa", encoding=�󕶎�<br>
     * ���Ғl�F3<br>
     * 
     * �����F����encoding���󕶎��̏ꍇ<br>
     * @throws Exception ��O */
    public void testGetByteLength04() throws Exception {
        // �O����
        String value = "aaa";
        String encoding = "";
        
        // �e�X�g���s
        int i = StringUtil.getByteLength(value, encoding);
        
        // ����
        assertEquals(3, i);
    }
    
    /**
     * testGetByteLength05<br>
     * 
     * �i����n�j<br>
     * �ϓ_�FC<br>
     * <br>
     * ���͒l�Fvalue="������", encoding="UTF-8"<br>
     * ���Ғl�F9<br>
     * 
     * �����F����encoding�������������G���R�[�f�B���O�̏ꍇ<br>
     * @throws Exception ��O */
    public void testGetByteLength05() throws Exception {
        // �O����
        String value = "������";
        String encoding = "UTF-8";
        
        // �e�X�g���s
        int i = StringUtil.getByteLength(value, encoding);
        
        // ����
        assertEquals(9, i);
    }
    
    /**
     * testGetByteLength06<br>
     * 
     * �i�ُ�n�j<br>
     * �ϓ_�FG<br>
     * <br>
     * ���͒l�Fvalue="aaa", encoding="aaa"<br>
     * ���Ғl�F��O�FUnsupportedEncodingException<br>
     * 
     * �����F����encoding���s���ȕ����G���R�[�f�B���O�̏ꍇ<br>
     * @throws Exception ��O */
    public void testGetByteLength06() throws Exception {
        // �O����
        String value = "aaa";
        String encoding = "aaa";
        
        // �e�X�g���s
        try {
            int i = StringUtil.getByteLength(value, encoding);
            fail();
        } catch (UnsupportedEncodingException e) {
            // ����
        	return;
        }
    }
}