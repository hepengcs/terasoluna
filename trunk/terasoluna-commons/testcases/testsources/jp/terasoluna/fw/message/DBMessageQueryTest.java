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

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.mockrunner.mock.jdbc.MockResultSet;

import jp.terasoluna.utlib.LogUTUtil;
import jp.terasoluna.utlib.MockDataSource;
import jp.terasoluna.utlib.UTUtil;
import jp.terasoluna.fw.message.DBMessage;
import jp.terasoluna.fw.message.DBMessageQuery;
import junit.framework.TestCase;

/**
 * {@link jp.terasoluna.fw.message.DBMessageQuery} �N���X�̃u���b�N�{�b�N�X�e�X�g�B
 * 
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4>
 * ���b�Z�[�W���\�[�X���擾����RDBMS�I�y���[�V�����N���X
 * <p>
 * 
 * @see jp.terasoluna.fw.message.DBMessageQuery
 */
public class DBMessageQueryTest extends TestCase {

    /**
     * ���̃e�X�g�P�[�X�����s����ׂ�
     * GUI �A�v���P�[�V�������N������B
     * 
     * @param args java �R�}���h�ɐݒ肳�ꂽ�p�����[�^
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(DBMessageQueryTest.class);
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
    public DBMessageQueryTest(String name) {
        super(name);
    }

    /**
     * testDBMessageDataSource01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) ds:not null<br>
     *         (����) sql:"SELECT CODE,MESSAGE FROM MESSAGES"<br>
     *         (����) codeColumn:"CODE"<br>
     *         (����) languageColumn:"LANGUAGE"<br>
     *         (����) countryColumn:"COUNTRY"<br>
     *         (����) variantColumn:"VARIANT"<br>
     *         (����) messageColumn:"MESSAGE"<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) rsCodeColumn:"CODE"<br>
     *         (��ԕω�) rsLanguageColumn:"LANGUAGE"<br>
     *         (��ԕω�) rsCountryColumn:"COUNTRY"<br>
     *         (��ԕω�) rsVariantColumn:"VARIANT"<br>
     *         (��ԕω�) message:"MESSAGE"<br>
     *         (��ԕω�) compile():�Ăяo���ꂽ���Ƃ��m�F����B<br>
     *         
     * <br>
     * ������String�������ꍇ�A�����n���ꂽ�l���ω��Ȃ��i�[����邩���m�F�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDBMessageDataSource01()
            throws Exception {
        // �O����
        DataSource ds = new MockDataSource();
        DBMessageQuery db = new DBMessageQuery(ds,
                "SELECT CODE,MESSAGE FROM MESSAGES", "CODE", "LANGUAGE",
                "COUNTRY", "VARIANT", "MESSAGE");

        // �e�X�g���{

        // ����
        assertEquals("CODE", UTUtil.getPrivateField(db, "rsCodeColumn"));
        assertEquals("LANGUAGE",
                UTUtil.getPrivateField(db, "rsLanguageColumn"));
        assertEquals("COUNTRY", UTUtil.getPrivateField(db, "rsCountryColumn"));
        assertEquals("VARIANT", UTUtil.getPrivateField(db, "rsVariantColumn"));
        assertEquals("MESSAGE", UTUtil.getPrivateField(db, "rsMessageColumn"));
        assertTrue(db.isCompiled());
    }

    /**
     * testDBMessageDataSource02()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA,C
     * <br><br>
     * ���͒l�F(����) ds:not null<br>
     *         (����) sql:"SELECT CODE,MESSAGE FROM MESSAGES"<br>
     *         (����) codeColumn:""<br>
     *         (����) languageColumn:""<br>
     *         (����) countryColumn:""<br>
     *         (����) variantColumn:""<br>
     *         (����) messageColumn:""<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) rsCodeColumn:""<br>
     *         (��ԕω�) rsLanguageColumn:""<br>
     *         (��ԕω�) rsCountryColumn:""<br>
     *         (��ԕω�) rsVariantColumn:""<br>
     *         (��ԕω�) message:""<br>
     *         (��ԕω�) compile():�Ăяo���ꂽ���Ƃ��m�F����B<br>
     *         
     * <br>
     * �������󕶎��������ꍇ�A�����n���ꂽ�l���ω��Ȃ��i�[����邩���m�F�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDBMessageDataSource02()
            throws Exception {
        // �O����
        DataSource ds = new MockDataSource();
        DBMessageQuery db = new DBMessageQuery(ds,
                "SELECT CODE,MESSAGE FROM MESSAGES", "", "", "", "", "");

        // �e�X�g���{

        // ����
        assertEquals("", UTUtil.getPrivateField(db, "rsCodeColumn"));
        assertEquals("", UTUtil.getPrivateField(db, "rsLanguageColumn"));
        assertEquals("", UTUtil.getPrivateField(db, "rsCountryColumn"));
        assertEquals("", UTUtil.getPrivateField(db, "rsVariantColumn"));
        assertEquals("", UTUtil.getPrivateField(db, "rsMessageColumn"));
        assertTrue(db.isCompiled());
    }

    /**
     * testDBMessageDataSource03()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA,C
     * <br><br>
     * ���͒l�F(����) ds:not null<br>
     *         (����) sql:"SELECT CODE,MESSAGE FROM MESSAGES"<br>
     *         (����) codeColumn:null<br>
     *         (����) languageColumn:null<br>
     *         (����) countryColumn:null<br>
     *         (����) variantColumn:null<br>
     *         (����) messageColumn:null<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) rsCodeColumn:null<br>
     *         (��ԕω�) rsLanguageColumn:null<br>
     *         (��ԕω�) rsCountryColumn:null<br>
     *         (��ԕω�) rsVariantColumn:null<br>
     *         (��ԕω�) message:null<br>
     *         (��ԕω�) compile():�Ăяo���ꂽ���Ƃ��m�F����B<br>
     *         
     * <br>
     * ������null�������ꍇ�A�����n���ꂽ�l���ω��Ȃ��i�[����邩���m�F�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDBMessageDataSource03()
            throws Exception {
        // �O����
        DataSource ds = new MockDataSource();
        DBMessageQuery db = new DBMessageQuery(ds,
                "SELECT CODE,MESSAGE FROM MESSAGES", null, null, null, null,
                null);

        // �e�X�g���{

        // ����
        assertNull(UTUtil.getPrivateField(db, "rsCodeColumn"));
        assertNull(UTUtil.getPrivateField(db, "rsLanguageColumn"));
        assertNull(UTUtil.getPrivateField(db, "rsCountryColumn"));
        assertNull(UTUtil.getPrivateField(db, "rsVariantColumn"));
        assertNull(UTUtil.getPrivateField(db, "rsMessageColumn"));
        assertTrue(db.isCompiled());
    }

    /**
     * testMapRow01()
     * <br><br>
     * 
     * (����n) 
     * <br>
     * �ϓ_�FA,E
     * <br><br>
     * ���͒l�F(����) rs:|"code"="test01"|"language"="ja"|"country"="JP"
     * |"variant"="kaisai"|"message"="�e�X�g���b�Z�[�W�O�P"|"hoge"="�֌W�Ȃ��J����"|<br>
     *                �Ƃ������e��ResultSet<br>
     *         (����) rowNum:not null<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) DBMessage Bean:code->"test01"<br>
     *                  language->"ja"<br>
     *                  country->"JP"<br>
     *                  variant->"kansai"<br>
     *                  message->"�e�X�g���b�Z�[�W�O�P"<br>
     *         
     * <br>
     * ResultSet�̃J�����̓��e��String�ł������ꍇ�A�l�̎擾���o���邩�̊m�F����B
     * �܂��A�v�����Ă��Ȃ�"hoge"�J����������ꍇ�A�G���[�ɂȂ炸�ɖ������邩��
     * �m�F����B<br>
     * �擾����String����������̂܂�DBMessageBean�Ɋi�[����B
     * "hoge"�J�����͖�������A�ǂ��ɂ��e�����Ȃ��B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testMapRow01() throws Exception {
        // �O����
        DataSource ds = new MockDataSource();
        DBMessageQuery db = new DBMessageQuery(ds,
                "SELECT CODE,MESSAGE FROM MESSAGES", "CODE", "LANGUAGE",
                "COUNTRY", "VARIANT", "MESSAGE");
        int rowNum = 0;
        db.rsCodeColumn = "code";
        db.rsLanguageColumn = "language"; 
        db.rsCountryColumn = "country";
        db.rsVariantColumn = "variant";
        db.rsMessageColumn = "message";
        
        // �[��ResultSet�̐ݒ�
        MockResultSet rs = new MockResultSet("TestResult");
        
        List<String> list1 = new ArrayList<String>();
        list1.add("test01");
        rs.addColumn("code", list1);

        List<String> list2 = new ArrayList<String>();
        list2.add("ja");
        rs.addColumn("language", list2);
        
        List<String> list3 = new ArrayList<String>();
        list3.add("JP");
        rs.addColumn("country", list3);
        
        List<String> list4 = new ArrayList<String>();
        list4.add("kansai");
        rs.addColumn("variant", list4);
        
        List<String> list5 = new ArrayList<String>();
        list5.add("�e�X�g���b�Z�[�W�O�P");
        rs.addColumn("message", list5);
        
        rs.first();
        
        // �e�X�g���{
        DBMessage dbmReturn = (DBMessage) db.mapRow(rs, rowNum);

        // ����
        assertEquals("test01", dbmReturn.getCode());
        assertEquals("ja", dbmReturn.getLanguage());
        assertEquals("JP", dbmReturn.getCountry());
        assertEquals("kansai", dbmReturn.getVariant());
        assertEquals("�e�X�g���b�Z�[�W�O�P", dbmReturn.getMessage());
    }

    /**
     * testMapRow02()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA,E
     * <br><br>
     * ���͒l�F(����) rs:|"code"=""|"language"=""|"country"=""|"variant"=""|
     * "message"=""|"hoge"=""|<br>
     *                �Ƃ������e��ResultSet<br>
     *         (����) rowNum:not null<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) DBMessage Bean:code->""<br>
     *                  language->""<br>
     *                  country->""<br>
     *                  variant->""<br>
     *                  message->""<br>
     *         
     * <br>
     * ResultSet�̃J�����̓��e���󕶎��ł������ꍇ�A�l�̎擾���o���邩�̊m�F����B
     * �܂��A�v�����Ă��Ȃ�"hoge"�J����������ꍇ�A�G���[�ɂȂ炸�ɖ������邩��
     * �m�F����B<br>
     * �擾�����󕶎������̂܂�DBMessageBean�Ɋi�[����B"hoge"�J�����͖�������A
     * �ǂ��ɂ��e�����Ȃ��B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testMapRow02() throws Exception {
        // �O����
        DataSource ds = new MockDataSource();
        DBMessageQuery db = new DBMessageQuery(ds,
                "SELECT CODE,MESSAGE FROM MESSAGES", "CODE", "LANGUAGE",
                "COUNTRY", "VARIANT", "MESSAGE");
        int rowNum = 0;
        db.rsCodeColumn = "code";
        db.rsLanguageColumn = "language"; 
        db.rsCountryColumn = "country";
        db.rsVariantColumn = "variant";
        db.rsMessageColumn = "message";
        
        // �[��ResultSet�̐ݒ�
        MockResultSet rs = new MockResultSet("TestResult");
        
        List<String> list1 = new ArrayList<String>();
        list1.add("");
        rs.addColumn("code", list1);

        List<String> list2 = new ArrayList<String>();
        list2.add("");
        rs.addColumn("language", list2);
        
        List<String> list3 = new ArrayList<String>();
        list3.add("");
        rs.addColumn("country", list3);
        
        List<String> list4 = new ArrayList<String>();
        list4.add("");
        rs.addColumn("variant", list4);
        
        List<String> list5 = new ArrayList<String>();
        list5.add("");
        rs.addColumn("message", list5);
        
        rs.first();
        
        // �e�X�g���{
        DBMessage dbmReturn = (DBMessage) db.mapRow(rs, rowNum);

        // ����
        assertEquals("", dbmReturn.getCode());
        assertEquals("", dbmReturn.getLanguage());
        assertEquals("", dbmReturn.getCountry());
        assertEquals("", dbmReturn.getVariant());
        assertEquals("", dbmReturn.getMessage());
    }

    /**
     * testMapRowResultSetint03()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FA,E
     * <br><br>
     * ���͒l�F(����) rs:|"code"=null|"language"=null|"country"=null
     * |"variant"=null|"message"=null|"hoge"=null|<br>
     *                �Ƃ������e��ResultSet<br>
     *         (����) rowNum:not null<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) DBMessage Bean:code->""<br>
     *                  language->""<br>
     *                  country->""<br>
     *                  variant->""<br>
     *                  message->""<br>
     *         (��ԕω�) ���O:�y�x�����O�z<br>
     *                    �����b�Z�[�W��<br>
     *                    "MessageCode is null"<br>
     *         
     * <br>
     * ResultSet�̃J�����̓��e��null�ł������ꍇ�A"hoge"�J�����������A�󕶎���
     * �ϊ�����DBMessageBean�Ɋi�[����B�܂�MessageCode�J�����̓��e��null��
     * �������ꍇ�́A�x�����O���o�͂���B"hoge"�J�����͖�������A
     * �ǂ��ɂ��e�����Ȃ��B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testMapRowResultSetint03() throws Exception {
        // �O����
        DataSource ds = new MockDataSource();
        DBMessageQuery db = new DBMessageQuery(ds,
                "SELECT CODE,MESSAGE FROM MESSAGES", "CODE", "LANGUAGE",
                "COUNTRY", "VARIANT", "MESSAGE");
        int rowNum = 0;
        db.rsCodeColumn = "code";
        db.rsLanguageColumn = "language"; 
        db.rsCountryColumn = "country";
        db.rsVariantColumn = "variant";
        db.rsMessageColumn = "message";
        
        // �[��ResultSet�̐ݒ�
        MockResultSet rs = new MockResultSet("TestResult");
        
        List<String> list1 = new ArrayList<String>();
        list1.add(null);
        rs.addColumn("code", list1);

        List<String> list2 = new ArrayList<String>();
        list2.add(null);
        rs.addColumn("language", list2);
        
        List<String> list3 = new ArrayList<String>();
        list3.add(null);
        rs.addColumn("country", list3);
        
        List<String> list4 = new ArrayList<String>();
        list4.add(null);
        rs.addColumn("variant", list4);
        
        List<String> list5 = new ArrayList<String>();
        list5.add(null);
        rs.addColumn("message", list5);
        
        rs.first();
        
        // �e�X�g���{
        DBMessage dbmReturn = (DBMessage) db.mapRow(rs, rowNum);

        // ����
        assertEquals("", dbmReturn.getCode());
        assertEquals("", dbmReturn.getLanguage());
        assertEquals("", dbmReturn.getCountry());
        assertEquals("", dbmReturn.getVariant());
        assertEquals("", dbmReturn.getMessage());
        assertTrue(LogUTUtil.checkWarn("MessageCode is null"));
        
    }
}
