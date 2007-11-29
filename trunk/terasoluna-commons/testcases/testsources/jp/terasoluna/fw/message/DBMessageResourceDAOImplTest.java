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

import jp.terasoluna.utlib.LogUTUtil;
import jp.terasoluna.utlib.MockDataSource;
import jp.terasoluna.utlib.UTUtil;
import jp.terasoluna.fw.message.DBMessageQuery;
import jp.terasoluna.fw.message.DBMessageResourceDAOImpl;
import junit.framework.TestCase;

/**
 * {@link jp.terasoluna.fw.message.DBMessageResourceDAOImpl}
 * �N���X�̃u���b�N�{�b�N�X�e�X�g�B
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4>
 * DB���烁�b�Z�[�W���\�[�X���擾����DBMessageResourceDAO�̎����N���X
 * <p>
 * 
 * @see jp.terasoluna.fw.message.DBMessageResourceDAOImpl
 */
public class DBMessageResourceDAOImplTest extends TestCase {

    /**
     * ���̃e�X�g�P�[�X�����s����ׂ�
     * GUI �A�v���P�[�V�������N������B
     * 
     * @param args java �R�}���h�ɐݒ肳�ꂽ�p�����[�^
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(DBMessageResourceDAOImplTest.class);
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
    public DBMessageResourceDAOImplTest(String name) {
        super(name);
    }

    /**
     * testSetTableName01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) tableName:"test01"<br>
     *         (���) this.tableName:"MESSAGES"<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) this.tableName:�����Őݒ肵���l<br>
     *         
     * <br>
     * tableName������setter���\�b�h�̃e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetTableName01() throws Exception {
        // �O����
        DBMessageResourceDAOImpl daoImpl = new DBMessageResourceDAOImpl();
        daoImpl.tableName = "MESSAGES";

        // �e�X�g���{
        daoImpl.setTableName("test01");

        // ����
        assertEquals("test01", UTUtil.getPrivateField(daoImpl, "tableName"));
    }

    /**
     * testSetCodeColumn01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) codeColumn:"test01"<br>
     *         (���) this.codeColumn:"CODE"<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) this.codeColumn:�����Őݒ肵���l<br>
     *         
     * <br>
     * codeColumn������setter���\�b�h�̃e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetCodeColumn01() throws Exception {
        // �O����
        DBMessageResourceDAOImpl daoImpl = new DBMessageResourceDAOImpl();
        daoImpl.codeColumn = "CODE";

        // �e�X�g���{
        daoImpl.setCodeColumn("test01");

        // ����
        assertEquals("test01", UTUtil.getPrivateField(daoImpl, "codeColumn"));
    }

    /**
     * testSetLaunguageColumn01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) languageColumn:"test01"<br>
     *         (���) this.languageColumn:null<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) this.languageColumn:�����Őݒ肵���l<br>
     *         
     * <br>
     * languageColumn������setter���\�b�h�̃e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetLaunguageColumn01() throws Exception {
        // �O����
        DBMessageResourceDAOImpl daoImpl = new DBMessageResourceDAOImpl();
        daoImpl.languageColumn = null;

        // �e�X�g���{
        daoImpl.setLanguageColumn("test01");

        // ����
        assertEquals("test01", UTUtil
                .getPrivateField(daoImpl, "languageColumn"));
    }

    /**
     * testSetCountryColumn01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) countryColumn:"test01"<br>
     *         (���) this.countryColumn:null<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) this.countryColumn:�����Őݒ肵���l<br>
     *         
     * <br>
     * countryColumn������setter���\�b�h�̃e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetCountryColumn01() throws Exception {
        // �O����
        DBMessageResourceDAOImpl daoImpl = new DBMessageResourceDAOImpl();
        daoImpl.countryColumn = null;

        // �e�X�g���{
        daoImpl.setCountryColumn("test01");

        // ����
        assertEquals("test01", UTUtil
                .getPrivateField(daoImpl, "countryColumn"));
    }

    /**
     * testSetVariantColumn01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) variantColumn:"test01"<br>
     *         (���) this.variantColumn:null<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) this.variantColumn:�����Őݒ肵���l<br>
     *         
     * <br>
     * variantColumn������setter���\�b�h�̃e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetVariantColumn01() throws Exception {
        // �O����
        DBMessageResourceDAOImpl daoImpl = new DBMessageResourceDAOImpl();
        daoImpl.variantColumn = null;

        // �e�X�g���{
        daoImpl.setVariantColumn("test01");

        // ����
        assertEquals("test01", UTUtil
                .getPrivateField(daoImpl, "variantColumn"));
    }

    /**
     * testSetMessageColumn01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) messageColumn:"test01"<br>
     *         (���) this.messageColumn:"MESSAGE"<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) this.messageColumn:�����Őݒ肵���l<br>
     *         
     * <br>
     * messageColumn������setter���\�b�h�̃e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetMessageColumn01() throws Exception {
        // �O����
        DBMessageResourceDAOImpl daoImpl = new DBMessageResourceDAOImpl();
        daoImpl.messageColumn = "MESSAGE";

        // �e�X�g���{
        daoImpl.setMessageColumn("test01");

        // ����
        assertEquals("test01", UTUtil
                .getPrivateField(daoImpl, "messageColumn"));
    }

    /**
     * testSetFindMessageSql01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) findMessageSql:"test01"<br>
     *         (���) this.findMessageSql:null<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) this.findMessageSql:�����Őݒ肵���l<br>
     *         
     * <br>
     * findMessageSql������setter���\�b�h�̃e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetFindMessageSql01() throws Exception {
        // �O����
        DBMessageResourceDAOImpl daoImpl = new DBMessageResourceDAOImpl();
        daoImpl.findMessageSql = null;

        // �e�X�g���{
        daoImpl.setFindMessageSql("test01");

        // ����
        assertEquals("test01", UTUtil
                .getPrivateField(daoImpl, "findMessageSql"));
    }

    /**
     * testInitDao01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(���) getDataSource():not null<br>
     *         (���) makeSql():"SELECT FOO FROM BAR"<br>
     *         (���) dBMessageQuery:null<br>
     *         (���) codeColumn:"CODE"<br>
     *         (���) languageColumn:"LANGUAGE"<br>
     *         (���) countryColumn:"COUNTRY"<br>
     *         (���) variantColumn:"VARIANT"<br>
     *         (���) messageColumn:"MESSAGE"<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) DBMessageQuery:DBMessageQuery�I�u�W�F�N�g�����������B
     * <br>
     *         
     * <br>
     * DBMessageQuery���Ăяo����Athis.dBMessageQuery�Ɋi�[����邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testInitDao01() throws Exception {
        // �O����
        DataSource dataSource = new MockDataSource();
        DBMessageResourceDAOImpl_JdbcTemplateStub01 jdbc
            = new DBMessageResourceDAOImpl_JdbcTemplateStub01();
        DBMessageResourceDAOImpl_DBMessageResourceDAOImplStub01 dbmr
                = new DBMessageResourceDAOImpl_DBMessageResourceDAOImplStub01();
        jdbc.ds = dataSource;
        UTUtil.setPrivateField(dbmr, "jdbcTemplate", jdbc);
        
        dbmr.dBMessageQuery = null;
        dbmr.codeColumn = "CODE";
        dbmr.languageColumn = "LANGUAGE";
        dbmr.countryColumn = "COUNTRY";
        dbmr.variantColumn = "VARIANT";
        dbmr.messageColumn = "MESSAGE";
        
        // �e�X�g���{
        dbmr.initDao();

        // ����
        DBMessageQuery query = dbmr.dBMessageQuery;
        
        assertEquals("CODE", query.rsCodeColumn);
        assertEquals("LANGUAGE", query.rsLanguageColumn);
        assertEquals("COUNTRY", query.rsCountryColumn);
        assertEquals("VARIANT", query.rsVariantColumn);
        assertEquals("MESSAGE", query.rsMessageColumn);
        assertTrue(dbmr.isRead);
    }

    /**
     * testInitDao02()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(���) getDataSource():null<br>
     *         (���) makeSql():"SELECT FOO FROM BAR"<br>
     *         (���) dBMessageQuery:null<br>
     *         (���) codeColumn:"CODE"<br>
     *         (���) languageColumn:null<br>
     *         (���) countryColumn:null<br>
     *         (���) variantColumn:null<br>
     *         (���) messageColumn:"MESSAGE"<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) DBMessageQuery:�[<br>
     *         (��ԕω�) ��O:illgalArgumentException���������邱�Ƃ��m�F����B
     *         <br>
     *                    �����b�Z�[�W��<br>
     *                    Missing dataSource in spring configuration file.<br>
     *         (��ԕω�) ���O:�y�G���[���O�z<br>
     *                    �����b�Z�[�W��<br>
     *                    Missing dataSource in spring configuration file.<br>
     *         
     * <br>
     * DBMessageQuery���Ăяo���ꂸ�A��O���������邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testInitDao02() throws Exception {
        // �O����
        DBMessageResourceDAOImpl_JdbcTemplateStub01 jdbc
            = new DBMessageResourceDAOImpl_JdbcTemplateStub01();
        DBMessageResourceDAOImpl_DBMessageResourceDAOImplStub01 dbmr
                = new DBMessageResourceDAOImpl_DBMessageResourceDAOImplStub01();
        jdbc.ds = null;
        
        dbmr.dBMessageQuery = null;
       
        dbmr.codeColumn = "CODE";
        dbmr.languageColumn = "LANGUAGE";
        dbmr.countryColumn = "COUNTRY";
        dbmr.variantColumn = "VARIANT";
        dbmr.messageColumn = "MESSAGE";
        
        // �e�X�g���{
        try {
            dbmr.initDao();
            fail();
        } catch (IllegalArgumentException e) {
            // ����
            assertEquals(
                    "Missing dataSource in spring configuration file.",
                    e.getMessage());
            assertTrue(LogUTUtil.checkError(
                    "Missing dataSource in spring configuration file."));
        }
    }

    /**
     * testInitDao03()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(���) getDataSource():not null<br>
     *         (���) makeSql():"SELECT FOO FROM BAR"<br>
     *         (���) dBMessageQuery:null<br>
     *         (���) codeColumn:"CODE"<br>
     *         (���) languageColumn:null<br>
     *         (���) countryColumn:null<br>
     *         (���) variantColumn:null<br>
     *         (���) messageColumn:"MESSAGE"<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) DBMessageQuery:DBMessageQuery�I�u�W�F�N�g�����������B
     * <br>       
     * <br>
     * DBMessageQuery���Ăяo����Athis.dBMessageQuery�Ɋi�[����邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testInitDao03() throws Exception {
        // �O����
        DataSource dataSource = new MockDataSource();
        DBMessageResourceDAOImpl_JdbcTemplateStub01 jdbc
            = new DBMessageResourceDAOImpl_JdbcTemplateStub01();
        DBMessageResourceDAOImpl_DBMessageResourceDAOImplStub01 dbmr
                = new DBMessageResourceDAOImpl_DBMessageResourceDAOImplStub01();
        jdbc.ds = dataSource;
        UTUtil.setPrivateField(dbmr, "jdbcTemplate", jdbc);
        
        dbmr.dBMessageQuery = null;
        dbmr.codeColumn = "CODE";
        dbmr.languageColumn = null;
        dbmr.countryColumn = null;
        dbmr.variantColumn = null;
        dbmr.messageColumn = "MESSAGE";
        
        // �e�X�g���{
        dbmr.initDao();

        // ����
        // ����
        DBMessageQuery query = dbmr.dBMessageQuery;
        
        assertEquals("CODE", query.rsCodeColumn);
        assertNull(query.rsLanguageColumn);
        assertNull(query.rsCountryColumn);
        assertNull(query.rsVariantColumn);
        assertEquals("MESSAGE", query.rsMessageColumn);
        assertTrue(dbmr.isRead);
    }

    /**
     * testMakeSql01() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FA <br>
     * <br>
     * ���͒l�F(���) codeColumn:"CODE"<br>
     * (���) languageColumn:"LANGUAGE"<br>
     * (���) countryColumn:"COUNTRY"<br>
     * (���) variantColumn:"VARIANT"<br>
     * (���) messageColumn:"MESSAGE"<br>
     * (���) tableName:"MESSAGES"<br>
     * (���) findMessageSql:null<br>
     * <br>
     * ���Ғl�F(�߂�l) sql:"SELECT CODE,LANGUAGE,COUNTRY,VARIANT,MESSAGE FROM
     * MESSAGES"<br>
     * <br>
     * �����Ƃ��Ă�������ꂽ�����񂩂�r�p�k�����쐬���AString�ŕԋp����B<br>
     * 
     * @throws Exception
     *             ���̃��\�b�h�Ŕ���������O
     */
    public void testMakeSql01() throws Exception {
        // �O����
        DBMessageResourceDAOImpl dbmr = new DBMessageResourceDAOImpl();
        dbmr.codeColumn = "CODE";
        dbmr.languageColumn = "LANGUAGE";
        dbmr.countryColumn = "COUNTRY";
        dbmr.variantColumn = "VARIANT";
        dbmr.messageColumn = "MESSAGE";
        dbmr.tableName = "MESSAGES";
        dbmr.findMessageSql = null;
        
        // �e�X�g���{
        String sql = dbmr.makeSql();

        // ����
        assertEquals(
            "SELECT CODE,LANGUAGE,COUNTRY,VARIANT,MESSAGE FROM MESSAGES", sql);
    }

    /**
     * testMakeSql02() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FG<br>
     * <br>
     * ���͒l�F(���) codeColumn:""<br>
     * (���) languageColumn:"LANGUAGE"<br>
     * (���) countryColumn:"COUNTRY"<br>
     * (���) variantColumn:"VARIANT"<br>
     * (���) messageColumn:"MESSAGE"<br>
     * (���) tableName:"MESSAGES"<br>
     * (���) findMessageSql:null<br>
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalArgumentException�F<br>
     * �����b�Z�[�W��<br>
     * "codeColumn is illegalAurgument"<br>
     * (��ԕω�) ���O:�y�G���[���O�z<br>
     * �����b�Z�[�W��<br>
     * "codeColumn is illegalAurgument"<br>
     * <br>
     * �R�[�h�J�������󕶎��̂��߁A�Ăяo���惁�\�b�h�ɂĒ�~����B <br>
     * 
     * @throws Exception
     *             ���̃��\�b�h�Ŕ���������O
     */
    public void testMakeSql02() throws Exception {
        // �O����
        DBMessageResourceDAOImpl dbmr 
                = new DBMessageResourceDAOImpl();
        dbmr.codeColumn = "";
        dbmr.languageColumn = "LANGUAGE";
        dbmr.countryColumn = "COUNTRY";
        dbmr.variantColumn = "VARIANT";
        dbmr.messageColumn = "MESSAGE";
        dbmr.tableName = "MESSAGES";
        dbmr.findMessageSql = null;
        
        // �e�X�g���{
        try {
            dbmr.makeSql();
            fail();
        } catch (IllegalArgumentException e) {

            // ����
            assertEquals(
                    "illegalArgument: codeColumn is null or empty.", e.getMessage());
            assertTrue(LogUTUtil
                    .checkError("illegalArgument: codeColumn is null or empty."));
        }
    }

    /**
     * testMakeSql03() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FG <br>
     * <br>
     * ���͒l�F(���) codeColumn:null<br>
     * (���) languageColumn:"LANGUAGE"<br>
     * (���) countryColumn:"COUNTRY"<br>
     * (���) variantColumn:"VARIANT"<br>
     * (���) messageColumn:"MESSAGE"<br>
     * (���) tableName:"MESSAGES"<br>
     * (���) findMessageSql:null<br>
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalArgumentException�F<br>
     * �����b�Z�[�W��<br>
     * "codeColumn is illegalArgument"<br>
     * (��ԕω�) ���O:�y�G���[���O�z<br>
     * �����b�Z�[�W��<br>
     * "codeColumn is illegalArgument"<br>
     * <br>
     * �R�[�h�J������null�̂��߁A�Ăяo���惁�\�b�h�ɂĒ�~����B <br>
     * 
     * @throws Exception
     *             ���̃��\�b�h�Ŕ���������O
     */
    public void testMakeSql03() throws Exception {
        // �O����
        DBMessageResourceDAOImpl dbmr 
                = new DBMessageResourceDAOImpl();
        dbmr.codeColumn = null;
        dbmr.languageColumn = "LANGUAGE";
        dbmr.countryColumn = "COUNTRY";
        dbmr.variantColumn = "VARIANT";
        dbmr.messageColumn = "MESSAGE";
        dbmr.tableName = "MESSAGES";
        dbmr.findMessageSql = null;
        
        // �e�X�g���{
        try {
            dbmr.makeSql();
            fail();
        } catch (IllegalArgumentException e) {

            // ����
            assertEquals(
                    "illegalArgument: codeColumn is null or empty.", e.getMessage());
            assertTrue(LogUTUtil
                    .checkError("illegalArgument: codeColumn is null or empty."));
        }
    }

    /**
     * testMakeSql04() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FG <br>
     * <br>
     * ���͒l�F(���) codeColumn:"CODE"<br>
     * (���) languageColumn:""<br>
     * (���) countryColumn:null<br>
     * (���) variantColumn:null<br>
     * (���) messageColumn:null<br>
     * (���) tableName:"MESSAGES"<br>
     * (���) findMessageSql:null<br>
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalArgumentException�F<br>
     * �����b�Z�[�W��<br>
     * "languageColumn is illegalArgument"<br>
     * (��ԕω�) ���O:�y�G���[���O�z<br>
     * �����b�Z�[�W��<br>
     * "languageColumn is illegalArgument"<br>
     * <br>
     * ����J�������󕶎��̂��߁A�Ăяo���惁�\�b�h�ɂĒ�~����B <br>
     * 
     * @throws Exception
     *             ���̃��\�b�h�Ŕ���������O
     */
    public void testMakeSql04() throws Exception {
        // �O����
        DBMessageResourceDAOImpl dbmr 
                = new DBMessageResourceDAOImpl();
        dbmr.codeColumn = "CODE";
        dbmr.languageColumn = "";
        dbmr.countryColumn = null;
        dbmr.variantColumn = null;
        dbmr.messageColumn = null;
        dbmr.tableName = "MESSAGES";
        dbmr.findMessageSql = null;
        
        // �e�X�g���{
        try {
            dbmr.makeSql();
            fail();
        } catch (IllegalArgumentException e) {

            // ����
            assertEquals(
                    "illegalArgument: languageColumn is empty.", e.getMessage());
            assertTrue(LogUTUtil
                    .checkError("illegalArgument: languageColumn is empty."));
        }
    }

    /**
     * testMakeSql05() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FG <br>
     * <br>
     * ���͒l�F(���) codeColumn:"CODE"<br>
     * (���) languageColumn:null<br>
     * (���) countryColumn:""<br>
     * (���) variantColumn:null<br>
     * (���) messageColumn:null<br>
     * (���) tableName:"MESSAGES"<br>
     * (���) findMessageSql:null<br>
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalArgumentException�F<br>
     * �����b�Z�[�W��<br>
     * "countryColumn is illegalArgument"<br>
     * (��ԕω�) ���O:�y�G���[���O�z<br>
     * �����b�Z�[�W��<br>
     * "countryColumn is illegalArgument"<br>
     * <br>
     * ���J�������󕶎��̂��߁A�Ăяo���惁�\�b�h�ɂĒ�~����B <br>
     * 
     * @throws Exception
     *             ���̃��\�b�h�Ŕ���������O
     */
    public void testMakeSql05() throws Exception {
        // �O����
        DBMessageResourceDAOImpl dbmr 
                = new DBMessageResourceDAOImpl();
        dbmr.codeColumn = "CODE";
        dbmr.languageColumn = null;
        dbmr.countryColumn = "";
        dbmr.variantColumn = null;
        dbmr.messageColumn = null;
        dbmr.tableName = "MESSAGES";
        dbmr.findMessageSql = null;
        
        // �e�X�g���{
        try {
            dbmr.makeSql();
            fail();
        } catch (IllegalArgumentException e) {

            // ����
            assertEquals(
                    "illegalArgument: countryColumn is empty.", e.getMessage());
            assertTrue(LogUTUtil
                    .checkError("illegalArgument: countryColumn is empty."));
        }
    }

    /**
     * testMakeSql06() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FG <br>
     * <br>
     * ���͒l�F(���) codeColumn:"CODE"<br>
     * (���) languageColumn:null<br>
     * (���) countryColumn:null<br>
     * (���) variantColumn:""<br>
     * (���) messageColumn:null<br>
     * (���) tableName:"MESSAGES"<br>
     * (���) findMessageSql:null<br>
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalArgumentException�F<br>
     * �����b�Z�[�W��<br>
     * "variantColumn is illegalArgument"<br>
     * (��ԕω�) ���O:�y�G���[���O�z<br>
     * �����b�Z�[�W��<br>
     * "variantColumn is illegalArgument"<br>
     * <br>
     * �o���A���g�J�������󕶎��̂��߁A�Ăяo���惁�\�b�h�ɂĒ�~����B <br>
     * 
     * @throws Exception
     *             ���̃��\�b�h�Ŕ���������O
     */
    public void testMakeSql06() throws Exception {
        // �O����
        DBMessageResourceDAOImpl dbmr 
                = new DBMessageResourceDAOImpl();
        dbmr.codeColumn = "CODE";
        dbmr.languageColumn = null;
        dbmr.countryColumn = null;
        dbmr.variantColumn = "";
        dbmr.messageColumn = null;
        dbmr.tableName = "MESSAGES";
        dbmr.findMessageSql = null;
        
        // �e�X�g���{
        try {
            dbmr.makeSql();
            fail();
        } catch (IllegalArgumentException e) {

            // ����
            assertEquals(
                    "illegalArgument: variantColumn is empty.", e.getMessage());
            assertTrue(LogUTUtil
                    .checkError("illegalArgument: variantColumn is empty."));
        }
    }

    /**
     * testMakeSql07() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FG <br>
     * <br>
     * ���͒l�F(���) codeColumn:"CODE"<br>
     * (���) languageColumn:null<br>
     * (���) countryColumn:null<br>
     * (���) variantColumn:null<br>
     * (���) messageColumn:""<br>
     * (���) tableName:"MESSAGES"<br>
     * (���) findMessageSql:null<br>
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalArgumentException�F<br>
     * �����b�Z�[�W��<br>
     * "messageColumn is illegalArgument"<br>
     * (��ԕω�) ���O:�y�G���[���O�z<br>
     * �����b�Z�[�W��<br>
     * "messageColumn is illegalArgument"<br>
     * <br>
     * ���b�Z�[�W�J�������󕶎��̂��߁A�Ăяo���惁�\�b�h�ɂĒ�~����B <br>
     * 
     * @throws Exception
     *             ���̃��\�b�h�Ŕ���������O
     */
    public void testMakeSql07() throws Exception {
        // �O����
        DBMessageResourceDAOImpl dbmr 
                = new DBMessageResourceDAOImpl();
        dbmr.codeColumn = "CODE";
        dbmr.languageColumn = null;
        dbmr.countryColumn = null;
        dbmr.variantColumn = null;
        dbmr.messageColumn = "";
        dbmr.tableName = "MESSAGES";
        dbmr.findMessageSql = null;
        
        // �e�X�g���{
        try {
            dbmr.makeSql();
            fail();
        } catch (IllegalArgumentException e) {

            // ����
            assertEquals(
                    "illegalArgument: messageColumn is null or empty.", e.getMessage());
            assertTrue(LogUTUtil
                    .checkError("illegalArgument: messageColumn is null or empty."));
        }
    }

    /**
     * testMakeSql08() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FG <br>
     * <br>
     * ���͒l�F(���) codeColumn:"CODE"<br>
     * (���) languageColumn:null<br>
     * (���) countryColumn:null<br>
     * (���) variantColumn:null<br>
     * (���) messageColumn:null<br>
     * (���) tableName:"MESSAGES"<br>
     * (���) findMessageSql:null<br>
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalArgumentException�F<br>
     * �����b�Z�[�W��<br>
     * "MessageColumn is illegalArgument"<br>
     * (��ԕω�) ���O:�y�G���[���O�z<br>
     * �����b�Z�[�W��<br>
     * "MessageColumn is illegalArgument"<br>
     * <br>
     * ���b�Z�[�W�J������null�̂��߁A�Ăяo���惁�\�b�h�ɂĒ�~����B <br>
     * 
     * @throws Exception
     *             ���̃��\�b�h�Ŕ���������O
     */
    public void testMakeSql08() throws Exception {
        // �O����
        DBMessageResourceDAOImpl dbmr 
                = new DBMessageResourceDAOImpl();
        dbmr.codeColumn = "CODE";
        dbmr.languageColumn = null;
        dbmr.countryColumn = null;
        dbmr.variantColumn = null;
        dbmr.messageColumn = null;
        dbmr.tableName = "MESSAGES";
        dbmr.findMessageSql = null;
        
        // �e�X�g���{
        try {
            dbmr.makeSql();
            fail();
        } catch (IllegalArgumentException e) {

            // ����
            assertEquals(
                    "illegalArgument: messageColumn is null or empty.", e.getMessage());
            assertTrue(LogUTUtil
                    .checkError("illegalArgument: messageColumn is null or empty."));
        }
    }
    
    /**
     * testMakeSql09() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FG <br>
     * <br>
     * ���͒l�F(���) codeColumn:"CODE"<br>
     * (���) languageColumn:null<br>
     * (���) countryColumn:null<br>
     * (���) variantColumn:null<br>
     * (���) messageColumn:"MESSAGE"<br>
     * (���) tableName:""<br>
     * (���) findMessageSql:null<br>
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalArgumentException�F<br>
     * �����b�Z�[�W��<br>
     * "tableName is illegalArgument"<br>
     * (��ԕω�) ���O:�y�G���[���O�z<br>
     * �����b�Z�[�W��<br>
     * "tableName is illegalArgument"<br>
     * <br>
     * �e�[�u�������󕶎��̂��߁A�Ăяo���惁�\�b�h�ɂĒ�~����B <br>
     * 
     * @throws Exception
     *             ���̃��\�b�h�Ŕ���������O
     */
    public void testMakeSql09() throws Exception {
        // �O����
        DBMessageResourceDAOImpl dbmr 
                = new DBMessageResourceDAOImpl();
        dbmr.codeColumn = "CODE";
        dbmr.languageColumn = null;
        dbmr.countryColumn = null;
        dbmr.variantColumn = null;
        dbmr.messageColumn = "MESSAGE";
        dbmr.tableName = "";
        dbmr.findMessageSql = null;
        
        // �e�X�g���{
        try {
            dbmr.makeSql();
            fail();
        } catch (IllegalArgumentException e) {

            // ����
            assertEquals(
                    "illegalArgument: tableName is null or empty.", e.getMessage());
            assertTrue(LogUTUtil
                    .checkError("illegalArgument: tableName is null or empty."));
        }
    }

    /**
     * testMakeSql10() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FG <br>
     * <br>
     * ���͒l�F(���) codeColumn:"CODE"<br>
     * (���) languageColumn:null<br>
     * (���) countryColumn:null<br>
     * (���) variantColumn:null<br>
     * (���) messageColumn:"MESSAGE"<br>
     * (���) tableName:null<br>
     * (���) findMessageSql:null<br>
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalArgumentException�F<br>
     * �����b�Z�[�W��<br>
     * "tableName is illegalArgument"<br>
     * (��ԕω�) ���O:�y�G���[���O�z<br>
     * �����b�Z�[�W��<br>
     * "tableName is illegalArgument"<br>
     * <br>
     * �e�[�u������null�̂��߁A�Ăяo���惁�\�b�h�ɂĒ�~����B <br>
     * 
     * @throws Exception
     *             ���̃��\�b�h�Ŕ���������O
     */
    public void testMakeSql10() throws Exception {
        // �O����
        DBMessageResourceDAOImpl dbmr 
                = new DBMessageResourceDAOImpl();
        dbmr.codeColumn = "CODE";
        dbmr.languageColumn = null;
        dbmr.countryColumn = null;
        dbmr.variantColumn = null;
        dbmr.messageColumn = "MESSAGE";
        dbmr.tableName = null;
        dbmr.findMessageSql = null;
        
        // �e�X�g���{
        try {
            dbmr.makeSql();
            fail();
        } catch (IllegalArgumentException e) {

            // ����
            assertEquals(
                    "illegalArgument: tableName is null or empty.", e.getMessage());
            assertTrue(LogUTUtil
                    .checkError("illegalArgument: tableName is null or empty."));
        }
    }

    /**
     * testMakeSql11() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FA,F <br>
     * <br>
     * ���͒l�F(���) codeColumn:"CODE"<br>
     * (���) languageColumn:null<br>
     * (���) countryColumn:null<br>
     * (���) variantColumn:null<br>
     * (���) messageColumn:"MESSAGE"<br>
     * (���) tableName:"MESSAGES"<br>
     * (���) findMessageSql:"SELECT CODE,LANGUAGE,COUNTRY,VARIANT,MESSAGE FROM
     * MESSAGES"<br>
     * <br>
     * ���Ғl�F(�߂�l) sql:"SELECT CODE,LANGUAGE,COUNTRY,VARIANT,MESSAGE FROM
     * MESSAGES"<br>
     * <br>
     * �����Ƃ��Ă�������ꂽSQL�����AString�ŕԋp����B<br>
     * SQL���������W�b�N�͎g�p���Ȃ��B <br>
     * 
     * @throws Exception
     *             ���̃��\�b�h�Ŕ���������O
     */
    public void testMakeSql11() throws Exception {
        // �O����
        DBMessageResourceDAOImpl dbmr 
                = new DBMessageResourceDAOImpl();
        String sql ="SELECT CODE,LANGUAGE,COUNTRY,VARIANT,MESSAGE FROM MESSAGES";
        dbmr.codeColumn = "CODE";
        dbmr.languageColumn = null;
        dbmr.countryColumn = null;
        dbmr.variantColumn = null;
        dbmr.messageColumn = "MESSAGE";
        dbmr.tableName = "MESSAGES";
        dbmr.findMessageSql = sql;
        
        // �e�X�g���{
        String sqlReturn = dbmr.makeSql();
        
        // ����
        assertEquals(sql, sqlReturn);
    }

    /**
     * testFindDBMessages01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FE
     * <br><br>
     * ���͒l�F(���) dBmessageQuery:not null<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) dBMessageQuery.execute()�̌���<br>
     *         
     * <br>
     * execute���\�b�h���Ăяo����A�߂�l�Ԃ��Ă��邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
	public void testFindDBMessages01() throws Exception {
        // �O����
        DataSource ds = new MockDataSource();
        DBMessageResourceDAOImpl_DBMessageQueryStub01 query
                = new DBMessageResourceDAOImpl_DBMessageQueryStub01(ds,
                "SELECT CODE,MESSAGE FROM MESSAGES", "CODE", "LANGUAGE",
                "COUNTRY", "VARIANT", "MESSAGE");
        DBMessageResourceDAOImpl dbmr = new DBMessageResourceDAOImpl();
        dbmr.dBMessageQuery = query;
        
        List list = new ArrayList();
        list.add("success");
        query.list = list;
        
        // �e�X�g���{
        List listReturn = dbmr.findDBMessages();
        
        // ����
        assertSame(list, listReturn);
    }
}
