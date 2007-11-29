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

package jp.terasoluna.fw.message;

import jp.terasoluna.utlib.UTUtil;
import jp.terasoluna.fw.message.DBMessage;
import junit.framework.TestCase;

/**
 * {@link jp.terasoluna.fw.message.DBMessage} �N���X�̃u���b�N�{�b�N�X�e�X�g�B
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4>
 * ���b�Z�[�W���\�[�X��ێ�����N���X�B
 * <p>
 * 
 * @see jp.terasoluna.fw.message.DBMessage
 * @version 2005/12/5
 */
public class DBMessageTest extends TestCase {

    /**
     * ���̃e�X�g�P�[�X�����s����ׂ� GUI �A�v���P�[�V�������N������B
     * 
     * @param args
     *            java �R�}���h�ɐݒ肳�ꂽ�p�����[�^
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(DBMessageTest.class);
    }

    /**
     * �������������s���B
     * 
     * @throws Exception
     *             ���̃��\�b�h�Ŕ���������O
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    /**
     * �I���������s���B
     * 
     * @throws Exception
     *             ���̃��\�b�h�Ŕ���������O
     * @see junit.framework.TestCase#tearDown()
     */
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * �R���X�g���N�^�B
     * 
     * @param name
     *            ���̃e�X�g�P�[�X�̖��O�B
     */
    public DBMessageTest(String name) {
        super(name);
    }

    /**
     * testDBMessage01() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FA <br>
     * <br>
     * ���͒l�F(����) code:"abc"<br>
     * (����) language:"def"<br>
     * (����) country:"ghi"<br>
     * (����) variant:"jkl"<br>
     * (����) message:"mno"<br>
     * <br>
     * ���Ғl�F(��ԕω�) code:"abc"<br>
     * (��ԕω�) language:"def"<br>
     * (��ԕω�) country:"ghi"<br>
     * (��ԕω�) variant:"jkl"<br>
     * (��ԕω�) message:"mno"<br>
     * <br>
     * �����Ƃ��ė^����ꂽ�l�ɂȂ邩���m�F�B <br>
     * 
     * @throws Exception
     *             ���̃��\�b�h�Ŕ���������O
     */
    public void testDBMessage01()
            throws Exception {
        // �O����
        DBMessage db = new DBMessage("abc", "def", "ghi", "jkl", "mno");

        // �e�X�g���{

        // ����
        assertEquals("abc", UTUtil.getPrivateField(db, "code"));
        assertEquals("def", UTUtil.getPrivateField(db, "language"));
        assertEquals("ghi", UTUtil.getPrivateField(db, "country"));
        assertEquals("jkl", UTUtil.getPrivateField(db, "variant"));
        assertEquals("mno", UTUtil.getPrivateField(db, "message"));
    }

    /**
     * testGetCode01() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FA <br>
     * <br>
     * ���͒l�F(���) code:"abc"<br>
     * <br>
     * ���Ғl�F(�߂�l) String:"abc"<br>
     * <br>
     * ����n1���̂݃e�X�g <br>
     * 
     * @throws Exception
     *             ���̃��\�b�h�Ŕ���������O
     */
    public void testGetCode01() throws Exception {
        // �O����
        DBMessage db = new DBMessage(null, null, null, null, null);
        UTUtil.setPrivateField(db, "code", "abc");
        
        // �e�X�g���{
        String returnCode = db.getCode();

        // ����
        assertEquals("abc", returnCode);
    }

    /**
     * testGetLanguage01() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FA <br>
     * <br>
     * ���͒l�F(���) language:"abc"<br>
     * <br>
     * ���Ғl�F(�߂�l) String:"abc"<br>
     * <br>
     * ����n1���̂݃e�X�g <br>
     * 
     * @throws Exception
     *             ���̃��\�b�h�Ŕ���������O
     */
    public void testGetLanguage01() throws Exception {
        // �O����
        DBMessage db = new DBMessage(null, null, null, null, null);
        UTUtil.setPrivateField(db, "language", "abc");
        
        // �e�X�g���{
        String returnLanguage = db.getLanguage();

        // ����
        assertEquals("abc", returnLanguage);
    }

    /**
     * testGetCountry01() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FA <br>
     * <br>
     * ���͒l�F(���) country:"abc"<br>
     * <br>
     * ���Ғl�F(�߂�l) String:"abc"<br>
     * <br>
     * ����n1���̂݃e�X�g <br>
     * 
     * @throws Exception
     *             ���̃��\�b�h�Ŕ���������O
     */
    public void testGetCountry01() throws Exception {
        // �O����
        DBMessage db = new DBMessage(null, null, null, null, null);
        UTUtil.setPrivateField(db, "country", "abc");
        
        // �e�X�g���{
        String returnCountry = db.getCountry();
        // ����
        assertEquals("abc", returnCountry);
    }

    /**
     * testGetVariant01() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FA <br>
     * <br>
     * ���͒l�F(���) variant:"abc"<br>
     * <br>
     * ���Ғl�F(�߂�l) String:"abc"<br>
     * <br>
     * ����n1���̂݃e�X�g <br>
     * 
     * @throws Exception
     *             ���̃��\�b�h�Ŕ���������O
     */
    public void testGetVariant01() throws Exception {
        // �O����
        DBMessage db = new DBMessage(null, null, null, null, null);
        UTUtil.setPrivateField(db, "variant", "abc");
        
        // �e�X�g���{
        String returnVariant = db.getVariant();

        // ����
        assertEquals("abc", returnVariant);
    }

    /**
     * testGetMessage01() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FA <br>
     * <br>
     * ���͒l�F(���) message:"abc"<br>
     * <br>
     * ���Ғl�F(�߂�l) String:"abc"<br>
     * <br>
     * ����n1���̂݃e�X�g <br>
     * 
     * @throws Exception
     *             ���̃��\�b�h�Ŕ���������O
     */
    public void testGetMessage01() throws Exception {
        // �O����
        DBMessage db = new DBMessage(null, null, null, null, null);
        UTUtil.setPrivateField(db, "message", "abc");
        
        // �e�X�g���{
        String returnMessage = db.getMessage();
        
        // ����
        assertEquals("abc", returnMessage);
    }
}
