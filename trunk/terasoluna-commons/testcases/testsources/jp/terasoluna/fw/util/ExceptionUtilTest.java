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

import javax.servlet.ServletException;

import jp.terasoluna.fw.exception.SystemException;
import junit.framework.TestCase;

/**
 * ExceptionUtil �u���b�N�{�b�N�X�e�X�g�B<br>
 * <br>
 * (�O�����)
 * �@�@�@�@�Ȃ�<br>
 * 
 * <br>
 */
public class ExceptionUtilTest extends TestCase {

    /**
     * Constructor for ExceptionUtilTest.
     * @param arg0
     */
    public ExceptionUtilTest(String arg0) {
        super(arg0);
    }

    /*
     * @see TestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    /*
     * @see TestCase#tearDown()
     */
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }
    /**
     * testGetStackTrace01()�B<br>
     * 
     * �i����n�j<br>
     * �ϓ_�FA<br>
     * ���͒l :1�̗�O�I�u�W�F�N�g<br>
     * ���Ғl :�X�^�b�N�g���[�X<br>
     *
     *  1�̗�O�I�u�W�F�N�g����X�^�b�N�g���[�X���擾�ł��邱�ƁB<br>
     *
     */
    public void testGetStackTrace01() {
        //�����ݒ�
        NullPointerException ne = new NullPointerException();

        //�e�X�g���s
        String result = ExceptionUtil.getStackTrace(ne);

        //SystemException�ANullPointerException��
        //�X�^�b�N�g���[�X�̑��s�ڂ��܂܂�Ă��邱�ƁB
        assertTrue(result.indexOf("java.lang.NullPointerException") != -1);
    }

    /**
     * testGetStackTrace02()�B<br>
     * 
     * �i����n�j<br>
     * �ϓ_�FA<br>
     * ���͒l :��O�I�u�W�F�N�g�������O�I�u�W�F�N�g<br>
     * ���Ғl :�������L�̗�O���珇�ɘA�����ꂽ�X�^�b�N�g���[�X<br>
     *
     * ��O�I�u�W�F�N�g�������O�I�u�W�F�N�g�ɑ΂��Ď��s����ƁA
     * ������̗�O���珇�ɃX�^�b�N�g���[�X���A������Ď擾�ł��邱�ƁB<br>
     *
     */
    public void testGetStackTrace02() {
        //�����ݒ�
        SystemException se = new SystemException(new ServletException(new NullPointerException()));

        //�e�X�g���s
        String result = ExceptionUtil.getStackTrace(se);


        //SystemException�AServletException�ANullPointerException��
        //�X�^�b�N�g���[�X�̑��s�ڂ��܂܂�Ă��邱�ƁB
        assertTrue(
            result.indexOf(
                "jp.terasoluna.fw.exception.SystemException")
                != -1);
        assertTrue(result.indexOf("\njavax.servlet.ServletException") != -1);
        assertTrue(result.indexOf("\njava.lang.NullPointerException") != -1);

        // SystemException��ServletException�̏��ŁA�o�͂���Ă��邱��
        assertTrue(
            result.indexOf(
                "jp.terasoluna.framework.exception.SystemException")
                < result.indexOf("\njavax.servlet.ServletException"));

        // ServletException��NullPointerException�̏��ŁA�o�͂���Ă��邱��
        assertTrue(
            result.indexOf(
                "\njavax.servlet.ServletException")
                < result.indexOf("\njava.lang.NullPointerException"));
    }

    /**
     * testGetStackTrace03()�B<br>
     * 
     * �i����n�j<br>
     * �ϓ_�FC<br>
     * ���͒l :������null<br>
     * ���Ғl :�󕶎�<br>
     *
     * ������null�̎��A��̃X�^�b�N�g���[�X���ԋp����邱�Ƃ��m�F����B<br>
     *
     */
    public void testGetStackTrace03() {
        //�e�X�g���s
        String trace = ExceptionUtil.getStackTrace(null);

        //���ʊm�F
        assertEquals("", trace);
    }
}
