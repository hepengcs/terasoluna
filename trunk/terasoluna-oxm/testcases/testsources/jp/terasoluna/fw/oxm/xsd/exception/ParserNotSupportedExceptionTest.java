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

package jp.terasoluna.fw.oxm.xsd.exception;

import jp.terasoluna.utlib.UTUtil;
import jp.terasoluna.fw.oxm.xsd.exception.ParserNotSupportedException;
import junit.framework.TestCase;

/**
 * {@link jp.terasoluna.fw.oxm.xsd.exception.ParserNotSupportedException} �N���X�̃u���b�N�{�b�N�X�e�X�g�B
 * 
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4>
 * Castor�̃}�b�s���O��`�t�@�C�����s���ȏꍇ�ɁA�X���[�����o�C���h������O�B
 * <p>
 * 
 * @see jp.terasoluna.fw.oxm.xsd.exception.ParserNotSupportedException
 */
public class ParserNotSupportedExceptionTest extends TestCase {
    
    /**
     * ���̃e�X�g�P�[�X�����s����ׂ�
     * GUI �A�v���P�[�V�������N������B
     * 
     * @param args java �R�}���h�ɐݒ肳�ꂽ�p�����[�^
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(ParserNotSupportedExceptionTest.class);
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
    public ParserNotSupportedExceptionTest(String name) {
        super(name);
    }

    /**
     * testParserNotSupportedException01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) cause:Throwable�C���X�^���X<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) cause:�����Ɠ����Throwable�C���X�^���X<br>
     *         
     * <br>
     * �����̃C���X�^���X�������ɐݒ肳��邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testParserNotSupportedException01() throws Exception {
        
        // ���͒l�̐ݒ�B
        Throwable cause = new Throwable();
        // �e�X�g�ΏۃR���X�g���N�^�̎��s�B
        ParserNotSupportedException ce = new ParserNotSupportedException(cause);

        // �o�͒l�̊m�F�B
        Throwable throwCause =
            ((Throwable) UTUtil.getPrivateField(ce, "cause"));
        
        assertSame(cause, throwCause);

    }

    /**
     * testParserNotSupportedException02()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA,C
     * <br><br>
     * ���͒l�F(����) cause:null<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) cause:null<br>
     *         
     * <br>
     * ������null�̏ꍇ�A������null���ݒ肳��邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testParserNotSupportedException02() throws Exception {
        
        // �e�X�g�ΏۃR���X�g���N�^�̎��s�B
        ParserNotSupportedException ce = new ParserNotSupportedException(null);

        // �o�͒l�̊m�F�B
        Throwable throwCause =
            ((Throwable) UTUtil.getPrivateField(ce, "cause"));
        
        assertNull(throwCause);

    }

}
