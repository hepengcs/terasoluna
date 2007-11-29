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

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import jp.terasoluna.utlib.LogUTUtil;
import jp.terasoluna.fw.message.DBMessage;
import jp.terasoluna.fw.message.DataSourceMessageSource;
import junit.framework.TestCase;

/**
 * {@link jp.terasoluna.fw.message.DataSourceMessageSource}
 * �N���X�̃u���b�N�{�b�N�X�e�X�g�B
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4>
 * DAO����擾�������b�Z�[�W���\�[�X���A���b�Z�[�W�R�[�h�y�у��P�[�����L�[<br>
 * �Ƃ��āA���b�Z�[�W�������̓��b�Z�[�W�t�H�[�}�b�g�����肷��N���X
 * <p>
 * 
 * @see jp.terasoluna.fw.message.DataSourceMessageSource
 */
public class DataSourceMessageSourceTest extends TestCase {

    /**
     * ���̃e�X�g�P�[�X�����s����ׂ� GUI �A�v���P�[�V�������N������B
     * 
     * @param args
     *            java �R�}���h�ɐݒ肳�ꂽ�p�����[�^
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(DataSourceMessageSourceTest.class);
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
    public DataSourceMessageSourceTest(String name) {
        super(name);
    }

    /**
     * testSetDefaultLocale01() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FA <br>
     * <br>
     * ���͒l�F(����) defaultLocale:Locale JAPAN<br>
     *         (���) this.defaultLocale:null<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) this.defaultLocale:�����Őݒ肵��Locale<br>
     *         
     * <br>
     * Locale������setter���\�b�h�̃e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetDefaultLocale01() throws Exception {
        // �O����
        DataSourceMessageSource ds = new DataSourceMessageSource();
        ds.defaultLocale = null;
        
        Locale locale = Locale.JAPAN;
        
        // �e�X�g���{
        ds.setDefaultLocale(locale);

        // ����
        assertEquals(locale, ds.defaultLocale);
    }

    /**
     * testSetDbMessageResourceDAO01() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FE <br>
     * <br>
     * ���͒l�F(����) dbMessageResourceDAO:DBMessageResourceDAO�I�u�W�F�N�g<br>
     *         (���) this.dbMessageResourceDAO:null<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) this.dbMessageResourceDAO:�����Őݒ肵��DAO�I�u�W�F�N�g<br>
     *         
     * <br>
     * DBMessageResourceDAO������setter���\�b�h�̃e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetDbMessageResourceDAO01() throws Exception {
        // �O����
        DataSourceMessageSource ds = new DataSourceMessageSource();
        DataSourceMessageSource_DBMessageResoueceDAOStub01 dbmr
                = new DataSourceMessageSource_DBMessageResoueceDAOStub01();
        ds.dbMessageResourceDAO = null;

        // �e�X�g���{
        ds.setDbMessageResourceDAO(dbmr);
        // ����
        assertSame(dbmr, ds.dbMessageResourceDAO);
    }

    /**
     * testAfterPropertiesSet01() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FA <br>
     * <br>
     * ���͒l�F
     * <br>
     * ���Ғl�F(��ԕω�) readMessageFromDataSource:�Ăяo����Ă���̂��m�F����B<br>
     *         
     * <br>
     * afterPropertiesSet�����s������readMessageFromDataSource�����s�����̂��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testAfterPropertiesSet01() throws Exception {
        // �O����
        DataSourceMessageSource_DataSourceMessageSourceStub01 ds
                = new DataSourceMessageSource_DataSourceMessageSourceStub01();

        // �e�X�g���{
        ds.afterPropertiesSet();

        // ����
        assertTrue(ds.isRead);
    }

    /**
     * testReloadDataSourceMessage01() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FA <br>
     * <br>
     * ���͒l�F
     * <br>
     * ���Ғl�F(��ԕω�) readMessageFromDataSource:�Ăяo����Ă���̂��m�F����B<br>
     *         
     * <br>
     * reloadDataSourceMessage�����s������readMessageFromDataSource�����s�����̂��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testReloadDataSourceMessage01() throws Exception {
        // �O����
        DataSourceMessageSource_DataSourceMessageSourceStub01 ds 
                = new DataSourceMessageSource_DataSourceMessageSourceStub01();

        // �e�X�g���{
        ds.reloadDataSourceMessage();

        // ����
        assertTrue(ds.isRead);
    }

    /**
     * testReadMessageFromDataSource01() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FD,E <br>
     * <br>
     * ���͒l�F(���) dbMessageResourceDAO.findDBMessage():List�̗v�f�����O<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) cachedMergedProperties.clear():�Ăяo����邱�Ƃ��m�F����B<br>
     *         (��ԕω�) cachedMessageFormats.clear():�Ăяo����邱�Ƃ��m�F����B<br>
     *         (��ԕω�) dbMessageResourceDAO.findDBMessage():�Ăяo����邱�Ƃ��m�F����B<br>
     *         
     * <br>
     * DBMessage�̃L���b�V�����s�Ȃ�ꂸ�A�������Ȃ��ŏI������B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
	public void testReadMessageFromDataSource01() throws Exception {
        // �O����
        DataSourceMessageSource_DataSourceMessageSourceStub02 ds 
                = new DataSourceMessageSource_DataSourceMessageSourceStub02();
        DataSourceMessageSource_DBMessageResoueceDAOStub01 dbmr 
                = new DataSourceMessageSource_DBMessageResoueceDAOStub01();
        Properties prop = new Properties();
        HashMap map = new HashMap();
        ds.cachedMergedProperties.put(Locale.JAPAN, prop);
        ds.cachedMessageFormats.put("foo", map);
        ds.dbMessageResourceDAO = dbmr;
        
        dbmr.list = new ArrayList();
        
        // �e�X�g���{
        ds.readMessagesFromDataSource();

        // ����
        assertTrue(ds.cachedMergedProperties.isEmpty());
        assertTrue(ds.cachedMessageFormats.isEmpty());
        assertTrue(dbmr.isRead);
        assertEquals(0, ds.list.size());
    }

    /**
     * testReadMessageFromDataSource02() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FD,E <br>
     * <br>
     * ���͒l�F(���) dbMessageResourceDAO.findDBMessage():List�̗v�f�����P<br>
     *                DBMessage("test01","JP","ja","kaisai","�e�X�g���b�Z�[�W�O�P")<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) cachedMergedProperties.clear():�Ăяo����邱�Ƃ��m�F����B<br>
     *         (��ԕω�) cachedMessageFormats.clear():�Ăяo����邱�Ƃ��m�F����B<br>
     *         (��ԕω�) dbMessageResourceDAO.findDBMessage():�Ăяo����邱�Ƃ��m�F����B<br>
     *         (��ԕω�) mapMessage():�Ăяo���ꂽ���Ƃ��m�F����B<br>
     *                    �������킽������Ă��邱�Ƃ��m�F����B<br>
     *         
     * <br>
     * DBMessage�I�u�W�F�N�g����l�����o���A�}�b�v�ɂ߂Ȃ������Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
	public void testReadMessageFromDataSource02() throws Exception {
        // �O����
        DataSourceMessageSource_DataSourceMessageSourceStub02 ds 
                = new DataSourceMessageSource_DataSourceMessageSourceStub02();
        DataSourceMessageSource_DBMessageResoueceDAOStub01 dbmr 
                = new DataSourceMessageSource_DBMessageResoueceDAOStub01();
        Properties prop = new Properties();
        HashMap map = new HashMap();
        ds.cachedMergedProperties.put(Locale.JAPAN, prop);
        ds.cachedMessageFormats.put("foo", map);
        ds.dbMessageResourceDAO = dbmr;
        
        List<DBMessage> list = new ArrayList<DBMessage>();
        DBMessage db1 = new DBMessage("test01", "JP", "ja", "kaisai",
                "�e�X�g���b�Z�[�W�O�P");
        list.add(db1);
        dbmr.list = list;
        
     
        // �e�X�g���{
        ds.readMessagesFromDataSource();

        // ����
        assertTrue(ds.cachedMergedProperties.isEmpty());
        assertTrue(ds.cachedMessageFormats.isEmpty());
        assertTrue(dbmr.isRead);
        assertEquals(1, ds.list.size());
        assertSame(db1, ds.list.get(0));
    }

    /**
     * testReadMessageFromDataSource03() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FD,E <br>
     * <br>
     * ���͒l�F(���) dbMessageResourceDAO.findDBMessage():LIst�̗v�f�����R<br>
     *                DBMessage("test01","JP","ja","kaisai","�e�X�g���b�Z�[�W�O�P")<br>
     *                DBMessage("test02","JP","ja","kaisai","�e�X�g���b�Z�[�W�O�Q")<br>
     *                DBMessage("test03","JP","ja","kaisai","�e�X�g���b�Z�[�W�O�R")<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) cachedMergedProperties.clear():�Ăяo����邱�Ƃ��m�F����B<br>
     *         (��ԕω�) cachedMessageFormats.clear():�Ăяo����邱�Ƃ��m�F����B<br>
     *         (��ԕω�) dbMessageResourceDAO.findDBMessage():�Ăяo����邱�Ƃ��m�F����B<br>
     *         (��ԕω�) mapMessage():�Ăяo���ꂽ���Ƃ��m�F����B<br>
     *                    �������킽������Ă��邱�Ƃ��m�F����B<br>
     *         
     * <br>
     * DBMessage�I�u�W�F�N�g����l�����o���A�}�b�v�ɂ߂Ȃ������Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
	public void testReadMessageFromDataSource03() throws Exception {
        // �O����
        DataSourceMessageSource_DataSourceMessageSourceStub02 ds
                = new DataSourceMessageSource_DataSourceMessageSourceStub02();
        DataSourceMessageSource_DBMessageResoueceDAOStub01 dbmr 
                = new DataSourceMessageSource_DBMessageResoueceDAOStub01();
        Properties prop = new Properties();
        HashMap map = new HashMap();
        ds.cachedMergedProperties.put(Locale.JAPAN, prop);
        ds.cachedMessageFormats.put("foo", map);
        
        ds.dbMessageResourceDAO = dbmr;
        
        List<DBMessage> list = new ArrayList<DBMessage>();
        DBMessage db1 = new DBMessage("test01", "JP", "ja", "kaisai",
                "�e�X�g���b�Z�[�W�O�P");
        DBMessage db2 = new DBMessage("test02", "JP", "ja", "kaisai",
                "�e�X�g���b�Z�[�W�O�Q");
        DBMessage db3 = new DBMessage("test03", "JP", "ja", "kaisai",
                "�e�X�g���b�Z�[�W�O�R");
        list.add(db1);
        list.add(db2);
        list.add(db3);
        dbmr.list = list;        

        // �e�X�g���{
        ds.readMessagesFromDataSource();

        // ����
        assertTrue(ds.cachedMergedProperties.isEmpty());
        assertTrue(ds.cachedMessageFormats.isEmpty());
        assertTrue(dbmr.isRead);
        assertEquals(3, ds.list.size());
        assertSame(db1, ds.list.get(0));
        assertSame(db2, ds.list.get(1));
        assertSame(db3, ds.list.get(2));
    }

    /**
     * testReadMessageFromDataSource04() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FE <br>
     * <br>
     * ���͒l�F(���) dbMessageResourceDAO.findDBMessage():List�̗v�f�����P<br>
     *                DBMessage("","","","","")<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) cachedMergedProperties.clear():�Ăяo����邱�Ƃ��m�F����B<br>
     *         (��ԕω�) cachedMessageFormats.clear():�Ăяo����邱�Ƃ��m�F����B<br>
     *         (��ԕω�) dbMessageResourceDAO.findDBMessage():�Ăяo����邱�Ƃ��m�F����B<br>
     *         (��ԕω�) mapMessage():�Ăяo���ꂽ���Ƃ��m�F����B<br>
     *                    �������킽������Ă��邱�Ƃ��m�F����B<br>
     *         
     * <br>
     * DBMessage�I�u�W�F�N�g����l�����o���A�}�b�v�ɂ߂Ȃ������Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
	public void testReadMessageFromDataSource04() throws Exception {
        // �O����
        DataSourceMessageSource_DataSourceMessageSourceStub02 ds
                = new DataSourceMessageSource_DataSourceMessageSourceStub02();
        DataSourceMessageSource_DBMessageResoueceDAOStub01 dbmr 
                = new DataSourceMessageSource_DBMessageResoueceDAOStub01();
        Properties prop = new Properties();
        HashMap map = new HashMap();
        ds.cachedMergedProperties.put(Locale.JAPAN, prop);
        ds.cachedMessageFormats.put("foo", map);
        
        ds.dbMessageResourceDAO = dbmr;
        
        List<DBMessage> list = new ArrayList<DBMessage>();
        DBMessage db1 = new DBMessage("", "", "", "", "");
        
        list.add(db1);
        dbmr.list = list;           
        

        // �e�X�g���{
        ds.readMessagesFromDataSource();

        // ����
        assertTrue(ds.cachedMergedProperties.isEmpty());
        assertTrue(ds.cachedMessageFormats.isEmpty());
        assertTrue(dbmr.isRead);
        assertEquals(1, ds.list.size());
        assertSame(db1, ds.list.get(0));
    }

    /**
     * testReadMessageFromDataSource05() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FE <br>
     * <br>
     * ���͒l�F(���) dbMessageResourceDAO.findDBMessage():List�̗v�f�����P<br>
     *                DBMessage(null,null,null.null.null)<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) cachedMergedProperties.clear():�Ăяo����邱�Ƃ��m�F����B<br>
     *         (��ԕω�) cachedMessageFormats.clear():�Ăяo����邱�Ƃ��m�F����B<br>
     *         (��ԕω�) dbMessageResourceDAO.findDBMessage():�Ăяo����邱�Ƃ��m�F����B<br>
     *         (��ԕω�) mapMessage():�Ăяo����Ă��Ȃ����Ƃ��m�F����B<br>
     *         
     * <br>
     * ���b�Z�[�W�R�[�h�������̓��b�Z�[�W�{�̂��Ȃ��ƃ}�b�v�Ɋi�[����Ȃ����Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
	public void testReadMessageFromDataSource05() throws Exception {
        // �O����
        DataSourceMessageSource_DataSourceMessageSourceStub02 ds
                = new DataSourceMessageSource_DataSourceMessageSourceStub02();
        DataSourceMessageSource_DBMessageResoueceDAOStub01 dbmr 
                = new DataSourceMessageSource_DBMessageResoueceDAOStub01();
        Properties prop = new Properties();
        HashMap map = new HashMap();
        ds.cachedMergedProperties.put(Locale.JAPAN, prop);
        ds.cachedMessageFormats.put("foo", map);
        
        ds.dbMessageResourceDAO = dbmr;
        
        List list = new ArrayList();
        DBMessage db1 = new DBMessage(null, null, null, null, null);
        list.add(db1);
        dbmr.list = list;
        
        // �e�X�g���{
        ds.readMessagesFromDataSource();

        // ����
        assertTrue(ds.cachedMergedProperties.isEmpty());
        assertTrue(ds.cachedMessageFormats.isEmpty());
        assertTrue(dbmr.isRead);
        assertEquals(0, ds.list.size());
    }

    /**
     * testReadMessageFromDataSource06() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FE <br>
     * <br>
     * ���͒l�F(���) dbMessageResourceDAO.findDBMessage():LIst�̗v�f�����P<br>
     *                DBMessage(null,"JP","ja","kaisai","�e�X�g���b�Z�[�W�O�P")<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) cachedMergedProperties.clear():�Ăяo����邱�Ƃ��m�F����B<br>
     *         (��ԕω�) cachedMessageFormats.clear():�Ăяo����邱�Ƃ��m�F����B<br>
     *         (��ԕω�) dbMessageResourceDAO.findDBMessage():�Ăяo����邱�Ƃ��m�F����B<br>
     *         (��ԕω�) mapMessage():�Ăяo����Ă��Ȃ����Ƃ��m�F����B<br>
     *         
     * <br>
     * ���b�Z�[�W�R�[�h�������̓��b�Z�[�W�{�̂��Ȃ��ƃ}�b�v�Ɋi�[����Ȃ����Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
	public void testReadMessageFromDataSource06() throws Exception {
        // �O����
        DataSourceMessageSource_DataSourceMessageSourceStub02 ds 
                = new DataSourceMessageSource_DataSourceMessageSourceStub02();
        DataSourceMessageSource_DBMessageResoueceDAOStub01 dbmr 
                = new DataSourceMessageSource_DBMessageResoueceDAOStub01();
        Properties prop = new Properties();
        HashMap map = new HashMap();
        ds.cachedMergedProperties.put(Locale.JAPAN, prop);
        ds.cachedMessageFormats.put("foo", map);

        ds.dbMessageResourceDAO = dbmr;
        
        List list = new ArrayList();
        DBMessage db1 
            = new DBMessage(null, "JP", "ja", "kaisai", "�e�X�g���b�Z�[�W�O�P");
        list.add(db1);
        dbmr.list = list;
        
        // �e�X�g���{
        ds.readMessagesFromDataSource();

        // ����
        assertTrue(ds.cachedMergedProperties.isEmpty());
        assertTrue(ds.cachedMessageFormats.isEmpty());
        assertTrue(dbmr.isRead);
        assertEquals(0, ds.list.size());
    }

    /**
     * testReadMessageFromDataSource07() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FE <br>
     * <br>
     * ���͒l�F(���) dbMessageResourceDAO.findDBMessage():List�̗v�f�����P<br>
     *                DBMessage("test01","JP","ja","kaisai",null)<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) cachedMergedProperties.clear():�Ăяo����邱�Ƃ��m�F����B<br>
     *         (��ԕω�) cachedMessageFormats.clear():�Ăяo����邱�Ƃ��m�F����B<br>
     *         (��ԕω�) dbMessageResourceDAO.findDBMessage():�Ăяo����邱�Ƃ��m�F����B<br>
     *         (��ԕω�) mapMessage():�Ăяo����Ă��Ȃ����Ƃ��m�F����B<br>
     *         
     * <br>
     * ���b�Z�[�W�R�[�h�������̓��b�Z�[�W�{�̂��Ȃ��ƃ}�b�v�Ɋi�[����Ȃ����Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
	public void testReadMessageFromDataSource07() throws Exception {
        // �O����
        DataSourceMessageSource_DataSourceMessageSourceStub02 ds 
                = new DataSourceMessageSource_DataSourceMessageSourceStub02();
        DataSourceMessageSource_DBMessageResoueceDAOStub01 dbmr 
                = new DataSourceMessageSource_DBMessageResoueceDAOStub01();
        Properties prop = new Properties();
        HashMap map = new HashMap();
        ds.cachedMergedProperties.put(Locale.JAPAN, prop);
        ds.cachedMessageFormats.put("foo", map);

        ds.dbMessageResourceDAO = dbmr;
        List list = new ArrayList();
        DBMessage db1 = new DBMessage("test01", "JP", "ja", "kaisai", null);
        list.add(db1);   
        
        dbmr.list = list;        
        
        // �e�X�g���{
        ds.readMessagesFromDataSource();

        // ����
        assertTrue(ds.cachedMergedProperties.isEmpty());
        assertTrue(ds.cachedMessageFormats.isEmpty());
        assertTrue(dbmr.isRead);
        assertEquals(0, ds.list.size());
    }

    /**
     * testReadMessageFromDataSource08() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FE,G <br>
     * <br>
     * ���͒l�F(���) dbMessageResourceDAO.findDBMessage():List�̗v�f�����P<br>
     *                DBMessage�I�u�W�F�N�g�ȊO�̃I�u�W�F�N�g<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) cachedMergedProperties.clear():�Ăяo����邱�Ƃ��m�F����B<br>
     *         (��ԕω�) cachedMessageFormats.clear():�Ăяo����邱�Ƃ��m�F����B<br>
     *         (��ԕω�) dbMessageResourceDAO.findDBMessage():�Ăяo����邱�Ƃ��m�F����B<br>
     *         (��ԕω�) mapMessage():�Ăяo����Ă��Ȃ����Ƃ��m�F����B<br>
     *         (��ԕω�) ��O:ClassCastException<br>
     *         
     * <br>
     * DBMessage�I�u�W�F�N�g��n���Ȃ��ƁA��O���������邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
	public void testReadMessageFromDataSource08() throws Exception {
        // �O����
        DataSourceMessageSource_DataSourceMessageSourceStub02 ds 
                = new DataSourceMessageSource_DataSourceMessageSourceStub02();
        DataSourceMessageSource_DBMessageResoueceDAOStub01 dbmr 
                = new DataSourceMessageSource_DBMessageResoueceDAOStub01();
        Properties prop = new Properties();
        HashMap map = new HashMap();
        ds.cachedMergedProperties.put(Locale.JAPAN, prop);
        ds.cachedMessageFormats.put("foo", map);

        ds.dbMessageResourceDAO = dbmr;
        List list = new ArrayList();
        Object db1 = new Object();
        list.add(db1);
        dbmr.list = list;
        
        try {
            // �e�X�g���{
            ds.readMessagesFromDataSource();
            fail();
        } catch (ClassCastException e) {
            // ����
            assertTrue(ds.cachedMergedProperties.isEmpty());
            assertTrue(ds.cachedMessageFormats.isEmpty());
            assertTrue(dbmr.isRead);
            assertEquals(0, ds.list.size());
        }
    }

    /**
     * testMapMessage01() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FA <br>
     * <br>
     * ���͒l�F(����) message:DBMessage�I�u�W�F�N�g<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) createLocale(message):�w�肳�ꂽ�����ŌĂяo����Ă��邱�Ƃ��m�F����B<br>
     *         (��ԕω�) getMessages:�w�肳�ꂽ�����ŌĂяo����Ă��邱�Ƃ��m�F����B<br>
     *         (��ԕω�) messages:message���擾�����l��messages�Ɋi�[����Ă��邱�Ƃ��m�F����B<br>
     *         
     * <br>
     * �擾����DBMessage�I�u�W�F�N�g����l�����o���A�e�[�u���Ɋi�[���邱�Ƃ��m�F�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testMapMessage01() throws Exception {
        // �O����
        DataSourceMessageSource_DataSourceMessageSourceStub03 ds 
                = new DataSourceMessageSource_DataSourceMessageSourceStub03();
        DBMessage message = new DBMessage("a", "b", "c", "d", "e");
        Locale locale = new Locale("ja", "JP");
        ds.locale = locale;
        Properties props = new Properties();
        ds.messages = props;
        
        // �e�X�g���{
        ds.mapMessage(message);

        // ����
        assertSame(message, ds.dbm);
        assertSame(locale, ds.locale2);
        assertSame(props, ds.messages);

    }

    /**
     * testCreateLocale01() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FA <br>
     * <br>
     * ���͒l�F(����) message:Language�J������null�̏ꍇ�B<br>
     *                DBMessage("test01",null,null,null,"�e�X�g���b�Z�[�W�O�P")<br>
     *         (���) defaultLocale:Locale.FRENCH<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) Locale�I�u�W�F�N�g:Locale("fr","","")<br>
     *         
     * <br>
     * �^����ꂽ��������Locale������ł��Ȃ��ꍇ�A�f�t�H���g���P�[�����g���B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testCreateLocale01() throws Exception {
        // �O����
        DataSourceMessageSource ds = new DataSourceMessageSource();
        DBMessage message = new DBMessage("test01", null, null, null,
                "�e�X�g���b�Z�[�W�O�P");
        ds.defaultLocale = Locale.FRENCH;

        // �e�X�g���{
        Locale returnLocale = ds.createLocale(message);

        // ����
        assertSame(ds.defaultLocale, returnLocale);
    }

    /**
     * testCreateLocale02() <br>
     * <br>
     * (�ُ�n) <br>
     * �ϓ_�FG <br>
     * <br>
     * ���͒l�F(����) message:Language�J������null�̏ꍇ�B<br>
     * DBMessage("test01",null,null,null,"�e�X�g���b�Z�[�W�O�P")<br>
     * (���) defaultLocale:null<br>
     * <br>
     * ���Ғl�F(�߂�l) Locale�I�u�W�F�N�g:��O����������B<br>
     * (��ԕω�) ��O:IllegalArgumentException<br>
     * ���b�Z�[�W�FCan't resolve Locale.Define Locale in MessageSource or
     * Defaultlocale<br>
     * (��ԕω�) ���O:�G���[���O�FCan't resolve Locale.Define Locale in MessageSource or
     * Defaultlocale<br>
     * <br>
     * Locale���w��ł����A��O���o���B <br>
     * 
     * @throws Exception
     *             ���̃��\�b�h�Ŕ���������O
     */
    public void testCreateLocale02() throws Exception {
        // �O����
        DataSourceMessageSource ds = new DataSourceMessageSource();
        DBMessage message = new DBMessage("test01", null, null, null,
                "�e�X�g���b�Z�[�W�O�P");
        ds.defaultLocale = null;

        // �e�X�g���{
        try {
            ds.createLocale(message);
            fail();
        } catch (IllegalArgumentException e) {

            // ����
            assertEquals(
                    "Can't resolve Locale.Define Locale in MessageSource or Defaultlocale.",
                    e.getMessage());
            assertTrue(LogUTUtil
                    .checkError("Can't resolve Locale.Define Locale in MessageSource or Defaultlocale."));
        }
    }

    /**
     * testCreateLocale03() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FA <br>
     * <br>
     * ���͒l�F(����) message:Country�J������null�̏ꍇ�B<br>
     *                DBMessage("test01","ja",null,null,"�e�X�g���b�Z�[�W�O�P")<br>
     *         (���) defaultLocale:null<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) Locale�I�u�W�F�N�g:Locale("ja","","")<br>
     *         
     * <br>
     * �^����ꂽ��������ALocale�����肷��B�^����������͌���R�[�h�ł���B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testCreateLocale03() throws Exception {
        // �O����
        DataSourceMessageSource ds = new DataSourceMessageSource();
        DBMessage message = new DBMessage("test01", "ja", null, null,
                "�e�X�g���b�Z�[�W�O�P");
        ds.defaultLocale = null;

        // �e�X�g���{
        Locale returnLocale = ds.createLocale(message);

        // ����
        assertEquals("ja", returnLocale.getLanguage());
        assertEquals("", returnLocale.getCountry());
        assertEquals("", returnLocale.getVariant());
    }

    /**
     * testCreateLocale04() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FA <br>
     * <br>
     * ���͒l�F(����) message:Variant�J������null�̏ꍇ�B<br>
     *                DBMessage("test01","ja","JP",null,"�e�X�g���b�Z�[�W�O�P")<br>
     *         (���) defaultLocale:null<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) Locale�I�u�W�F�N�g:Locale("ja","JP","")<br>
     *         
     * <br>
     * �^����ꂽ��������ALocale�����肷��B�^����������͌���R�[�h�ƍ��R�[�h�ł���B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testCreateLocale04() throws Exception {
        // �O����
        DataSourceMessageSource ds = new DataSourceMessageSource();
        DBMessage message = new DBMessage("test01", "ja", "JP", null,
                "�e�X�g���b�Z�[�W�O�P");
        ds.defaultLocale = null;

        // �e�X�g���{
        Locale returnLocale = ds.createLocale(message);

        // ����
        assertEquals("ja", returnLocale.getLanguage());
        assertEquals("JP", returnLocale.getCountry());
        assertEquals("", returnLocale.getVariant());

    }

    /**
     * testCreateLocale05() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FA <br>
     * <br>
      * ���͒l�F(����) message:Variant�J������null�łȂ��ꍇ�B<br>
     *                DBMessage("test01","ja","JP","kansai","�e�X�g���b�Z�[�W�O�P")<br>
     *         (���) defaultLocale:null<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) Locale�I�u�W�F�N�g:Locale("ja","JP","kansai")<br>
     *         
     * <br>
     * �^����ꂽ��������ALocale�����肷��B�^����������͌���R�[�h��
     * ���R�[�h�ƃo���A���g�R�[�h�ł���B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testCreateLocale05() throws Exception {
        // �O����
        DataSourceMessageSource ds = new DataSourceMessageSource();
        DBMessage message = new DBMessage("test01", "ja", "JP", "kansai",
                "�e�X�g���b�Z�[�W�O�P");
        ds.defaultLocale = null;
        
        // �e�X�g���{
        Locale returnLocale = ds.createLocale(message);

        // ����
        assertEquals("ja", returnLocale.getLanguage());
        assertEquals("JP", returnLocale.getCountry());
        assertEquals("kansai", returnLocale.getVariant());
    }

    /**
     * testGetMessages01() <br>
     * <br>
     * (����n)<br>
     * �ϓ_�FF<br>
     * <br>
     * ���͒l�F(����) locale:Locale("ja,JP,"")<br>
     * (���) cachedMergedProperties.get(locale):HashMap�o<br>
     * Local(ja_JP)=Properties()<br> �p<br>
     * <br>
     * ���Ғl�F(�߂�l) messages:Properties()<br>
     * (��ԕω�) cachedMergedProperties.get(locale):�w�肳�ꂽ�����ŌĂяo����
     * �Ă��邱�Ƃ��m�F����B<br>
     * <br>
     * �������L�[�Ƃ��ă}�b�v����l�����o���A���̒l��߂�l�Ƃ��ĕԋp���邱��
     * ���m�F����B <br>
     * 
     * @throws Exception
     *             ���̃��\�b�h�Ŕ���������O
     */
    public void testGetMessages01() throws Exception {
        // �O����
        DataSourceMessageSource ds = new DataSourceMessageSource();
        Locale locale = new Locale("ja", "JP", "");
        Properties props = new Properties();
        ds.cachedMergedProperties.put(locale, props);
        
        // �e�X�g���{
        Properties returnProp = ds.getMessages(locale);

        // ����
        assertSame(props, returnProp);
                
    }

    /**
     * testGetMessages02() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FF<br>
     * <br>
     * ���͒l�F(����) locale:Locale("ja,JP,"")<br>
     * (���) cachedMergedProperties.get(locale):���HashMap<br>
     * <br>
     * ���Ғl�F(�߂�l) messages:���Properties�I�u�W�F�N�g<br>
     * (��ԕω�) cachedMergedProperties.get(locale):�w�肳�ꂽ������
     * �Ăяo����Ă��邱�Ƃ��m�F����B<br>
     * (��ԕω�) cachedMergedProperties.put(locale,messages):
     * HashMap<Locale,<null,null>><br>
     * <br>
     * �������L�[�Ƃ��ă}�b�v������o�����l��null�������ꍇ�A�V���ɐ������A�i�[����B <br>
     * 
     * @throws Exception
     *             ���̃��\�b�h�Ŕ���������O
     */
    public void testGetMessages02() throws Exception {
        // �O����
        DataSourceMessageSource ds = new DataSourceMessageSource();
        Locale locale = new Locale("ja", "JP", "");
        ds.cachedMergedProperties.clear();
        
        // �e�X�g���{
        Properties returnProps = ds.getMessages(locale);

        // ����
        assertEquals(1, ds.cachedMergedProperties.size());
        Properties result = null;
        result = ds.cachedMergedProperties.get(locale);
        
        assertSame(returnProps, result);         
    }

    /**
     * testResolveCodeWithoutArguments01() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FA<br>
     * <br>
     * ���͒l�F(����) code:"code01"<br>
     * (����) locale:Locale("ja","JP","")<br>
     * (���) internalResolveCodeWithoutArguments:"�e�X�g���b�Z�[�W�O�P"<br>
     * <br>
     * ���Ғl�F(�߂�l) msg:"�e�X�g���b�Z�[�W�O�P"<br>
     * (��ԕω�) internalResolveCodeWithoutArguments:�w�肳�ꂽ�����ŌĂяo����Ă��邱�Ƃ��m�F����B<br>
     * <br>
     * �^����ꂽ������胁�b�Z�[�W�����肵�A�ԋp����B <br>
     * 
     * @throws Exception
     *             ���̃��\�b�h�Ŕ���������O
     */
    public void testResolveCodeWithoutArguments01() throws Exception {
        // �O����
        DataSourceMessageSource_DataSourceMessageSourceStub04 ds
                = new DataSourceMessageSource_DataSourceMessageSourceStub04();
        String code = "code01";
        Locale locale = new Locale("ja", "JP", "");
        String msg = "�e�X�g���b�Z�[�W�O�P";
        ds.msg = msg;
        
        // �e�X�g���{
        String returnMsg = ds.resolveCodeWithoutArguments(code, locale);

        // ����
        assertEquals(code, ds.code);
        assertSame(locale, ds.locale);
        assertTrue(ds.isRead);
        assertEquals(msg, returnMsg);
    }

    /**
     * testResolveCodeWithoutArguments02() <br>
     * <br>
     * (����n)<br>
     * �ϓ_�FA,C<br>
     * <br>
     * ���͒l�F(����) code:"code01"<br>
     * (����) locale:Locale("ja","JP","")<br>
     * (���) internalResolveCodeWithoutArguments:null<br>
     * <br>
     * ���Ғl�F(�߂�l) msg:null<br>
     * (��ԕω�) internalResolveCodeWithoutArguments:�w�肳�ꂽ�����ŌĂяo����Ă��邱�Ƃ��m�F����B<br>
     * <br>
     * �^����ꂽ������胁�b�Z�[�W������ł����Anull��ԋp����B <br>
     * 
     * @throws Exception
     *             ���̃��\�b�h�Ŕ���������O
     */
    public void testResolveCodeWithoutArguments02() throws Exception {
        // �O����
        DataSourceMessageSource_DataSourceMessageSourceStub04 ds
                = new DataSourceMessageSource_DataSourceMessageSourceStub04();
        String code = "code01";
        Locale locale = new Locale("ja", "JP", "");
        
        // �e�X�g���{
        String returnMsg = ds.resolveCodeWithoutArguments(code, locale);

        // ����
        assertEquals(code, ds.code);
        assertSame(locale, ds.locale);
        assertTrue(ds.isRead);
        assertNull(returnMsg);
    }

    /**
     * testInternalResolveCodeWithoutArguments01() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FF<br>
     * <br>
     * ���͒l�F(����) code:"code01"<br>
     *         (����) locale:Locale("ja","JP","")<br>
     *         (���) getMessage().getProperties:"�e�X�g���b�Z�[�W�O�P"<br>
     *         (���) ���b�Z�[�W��locale:Locale("ja",JP","")<br>
     *         (���) defaultLocale:null<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) msg:"�e�X�g���b�Z�[�W�O�P"<br>
     *         (��ԕω�) getMessage().getProperties:�w�肳�ꂽ�����ŌĂяo����Ă��邱�Ƃ��m�F����B<br>
     *         
     * <br>
     * �w�肳�ꂽ������胁�b�Z�[�W�����肵�A�ԋp����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
	public void testInternalResolveCodeWithoutArguments01() throws Exception {
        // �O����
        DataSourceMessageSource ds = new DataSourceMessageSource();
        ds.cachedMergedProperties = null;
        
        String code = "code01";
        Locale locale = new Locale("ja", "JP", "");
        ds.defaultLocale = null;
        
        //cachedMergedProperties����
        Map map = new HashMap();
        Properties props = new Properties();
        String msg = "�e�X�g���b�Z�[�W�O�P";
        props.put(code, msg);
        map.put(locale, props);
        ds.cachedMergedProperties = map;
        
        // �e�X�g���{
        String returnMsg = ds.internalResolveCodeWithoutArguments(code, locale);
        
        // ����
        assertEquals(msg, returnMsg);
    }

    /**
     * testInternalResolveCodeWithoutArguments02() <br>
     * <br>
     * (����n)<br>
     * �ϓ_�FF<br>
     * <br>
     * ���͒l�F(����) code:"code01"<br>
     * (����) locale:Locale("ja","JP","")<br>
     * (���) getMessage().getProperties:null<br>
     * (���) ���b�Z�[�W��locale:Locale("ja","","")<br>
     * (���) defaultLocale:null<br>
     * (���) getAlternativeLocales�i�j:List�o<br>
     * Locale�i"ja")<br> �p<br>
     * (���) getMessage(locales.get()).getProperties():1��ڂ̖߂�l<br>
     * "�e�X�g���b�Z�[�W�O�P"<br>
     * <br>
     * ���Ғl�F(�߂�l) msg:"�e�X�g���b�Z�[�W�O�P"<br>
     * (��ԕω�) getMessage().getProperties:�w�肳�ꂽ�����ŌĂяo����Ă��邱�Ƃ��m�F����B<br>
     * (��ԕω�) getAlternativeLocales:�w�肳�ꂽ�����ŌĂяo����Ă��邱�Ƃ��m�F����B<br>
     * (��ԕω�)
     * getMessages(locales.get()).getProperties():�w�肳�ꂽ�����ŌĂяo����Ă��邱�Ƃ��m�F����B<br>
     * <br>
     * �w�肳�ꂽ�����Ń��b�Z�[�W������ł����A�V���ɍ쐬���������ɂ�胁�b�Z�[�W�����肵�A
     * �ԋp����B�܂��A���P�[����null�ł��G���[�ɂȂ�Ȃ����Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception
     *             ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
	public void testInternalResolveCodeWithoutArguments02() throws Exception {
        // �O����
        DataSourceMessageSource ds = new DataSourceMessageSource();
        
        String code = "code01";
        Locale locale = new Locale("ja", "JP", "");
        ds.defaultLocale = null;
        
        //cachedMergedProperties����
        Map map = new HashMap();
        Properties props = new Properties();
        Locale msglocale = new Locale("ja", "", "");
        String msg = "�e�X�g���b�Z�[�W�O�P";
        props.put(code, msg);
        map.put(msglocale, props);
        ds.cachedMergedProperties = map;
        
        // �e�X�g���{
        String returnMsg = ds.internalResolveCodeWithoutArguments(code, locale);
        
        // ����
        assertEquals("�e�X�g���b�Z�[�W�O�P", returnMsg);
    }

    /**
     * testInternalResolveCodeWithoutArguments03() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FF<br>
     * <br>
     * ���͒l�F(����) code:"code01"<br>
     * (����) locale:Locale("ja","JP","")<br>
     * (���) getMessage().getProperties:null<br>
     * (���) ���b�Z�[�W��locale:Locale("en","","")<br>
     * (���) defaultLocale:Locale("en","US","")<br>
     * (���) getAlternativeLocales�i�j:List�o<br>
     * Locale("ja","","")<br>
     * Locale("en","US","")<br>
     * Locale�i"en","","")<br> �p<br>
     * (���) getMessage(locales.get()).getProperties():�P��ڂ̖߂�l<br>
     * null<br>
     * �Q��ڂ̖߂�l<br>
     * null<br>
     * �R��ڂ̖߂�l<br>
     * "�e�X�g���b�Z�[�W�O�P"<br>
     * <br>
     * ���Ғl�F(�߂�l) msg:"�e�X�g���b�Z�[�W�O�P"<br>
     * (��ԕω�) getMessage().getProperties:�w�肳�ꂽ�����ŌĂяo����Ă��邱�Ƃ��m�F����B<br>
     * (��ԕω�) getAlternativeLocales:�w�肳�ꂽ�����ŌĂяo����Ă��邱�Ƃ��m�F����B<br>
     * (��ԕω�)
     * getMessages(locales.get()).getProperties():�w�肳�ꂽ������
     * �Ăяo����Ă��邱�Ƃ��m�F����B<br>
     * <br>
     * �w�肳�ꂽ�����Ń��b�Z�[�W������ł����A�V���ɍ쐬���������ɂ��
     * ���b�Z�[�W�����肵�A�ԋp����B�܂��A���P�[����null�ł��G���[�ɂȂ�Ȃ����Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception
     *             ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
	public void testInternalResolveCodeWithoutArguments03() throws Exception {
        // �O����
        DataSourceMessageSource ds = new DataSourceMessageSource();

        String code = "code01";
        Locale locale = new Locale("ja", "JP", "");
        ds.defaultLocale = new Locale("en", "US", "");
        
        //cachedMergedProperties����
        Map map = new HashMap();
        Properties props = new Properties();
        Locale msglocale = new Locale("en", "", "");
        String msg = "�e�X�g���b�Z�[�W�O�P";
        props.put(code, msg);
        map.put(msglocale, props);
        ds.cachedMergedProperties = map;
        
        // �e�X�g���{
        String returnMsg = ds.internalResolveCodeWithoutArguments(code, locale);
        
        // ����
        assertEquals("�e�X�g���b�Z�[�W�O�P", returnMsg);
    }

    /**
     * testInternalResolveCodeWithoutArguments04() <br>
     * <br>
     * (����n)<br>
     * �ϓ_�FF<br>
     * <br>
     * ���͒l�F(����) code:"code01"<br>
     *         (����) locale:Locale("ja","JP","")<br>
     *         (���) getMessage().getProperties:null<br>
     *         (���) ���b�Z�[�W��locale:Locale("ja","JP","kansai")<br>
     *         (���) defaultLocale:Locale("en","US","")<br>
     *         (���) getAlternativeLocales�i�j:List�o<br>
     *                  Locale("ja","","")<br>
     *                  Locale("en","US","")<br>
     *                �@Locale�i"en","","")<br>
     *                �p<br>
     *         (���) getMessage(locales.get()).getProperties():�P��ڂ̖߂�l<br>
     *                null<br>
     *                �Q��ڂ̖߂�l<br>
     *                null<br>
     *                �R��ڂ̖߂�l<br>
     *                null<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) msg:null<br>
     *         (��ԕω�) getMessage().getProperties:�w�肳�ꂽ�����ŌĂяo����Ă��邱�Ƃ��m�F����B<br>
     *         (��ԕω�) getAlternativeLocales:�w�肳�ꂽ�����ŌĂяo����Ă��邱�Ƃ��m�F����B<br>
     *         (��ԕω�) getMessages(locales.get()).getProperties():
     *         �w�肳�ꂽ�����ŌĂяo����Ă��邱�Ƃ��m�F����B<br>
     *         
     * <br>
     * �w�肳�ꂽ�����y�ѐV���ɐ��������������A���b�Z�[�W������ł��Ȃ��ꍇ��null��ԋp����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
	public void testInternalResolveCodeWithoutArguments04() throws Exception {
        // �O����
        DataSourceMessageSource ds = new DataSourceMessageSource();
        
        String code = "code01";
        Locale locale = new Locale("ja", "JP", "");
        ds.defaultLocale = new Locale("en", "US", "");
        
        //cachedMergedProperties����
        Map map = new HashMap();
        Properties props = new Properties();
        Locale msglocale = new Locale("ja", "JP", "kansai");
        String msg = "�e�X�g���b�Z�[�W�O�P���";
        props.put(code, msg);
        map.put(msglocale, props);
        ds.cachedMergedProperties = map;
        
        // �e�X�g���{
        String returnMsg = ds.internalResolveCodeWithoutArguments(code, locale);
        
        // ����
        assertNull(returnMsg);
    }

    /**
     * testInternalResolveCodeWithoutArguments05() <br>
     * <br>
     * (����n)<br>
     * �ϓ_�FF<br>
     * <br>
     * ���͒l�F(����) code:"code01"<br>
     *         (����) locale:Locale("ja","","")<br>
     *         (���) getMessage().getProperties:null<br>
     *         (���) ���b�Z�[�W��locale:Locale("ja","JP","kansai")<br>
     *         (���) defaultLocale:null<br>
     *         (���) getAlternativeLocales�i�j:List�o<br>
     *                �p<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) msg:null<br>
     *         (��ԕω�) getMessage().getProperties:�w�肳�ꂽ�����ŌĂяo����Ă��邱�Ƃ��m�F����B<br>
     *         (��ԕω�) getAlternativeLocales:�w�肳�ꂽ�����ŌĂяo����Ă��邱�Ƃ��m�F����B<br>
     *         
     * <br>
     * ���P�[�����擾�ł��Ȃ��ꍇ�Anull��ԋp����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
	public void testInternalResolveCodeWithoutArguments05() throws Exception {
        // �O����
        DataSourceMessageSource ds = new DataSourceMessageSource();
        
        String code = "code01";
        Locale locale = new Locale("ja", "", "");
        ds.defaultLocale = null;
        
        //cachedMergedProperties����
        Map map = new HashMap();
        Properties props = new Properties();
        Locale msglocale = new Locale("ja", "JP", "kansai");
        String msg = "�e�X�g���b�Z�[�W�O�P���";
        props.put(code, msg);
        map.put(msglocale, props);
        ds.cachedMergedProperties = map;
        
        // �e�X�g���{
        String returnMsg = ds.internalResolveCodeWithoutArguments(code, locale);
        
        // ����
        assertNull(returnMsg);
    }

    /**
     * testGetAlternativeLocales01() <br>
     * <br>
     * (����n)<br>
     * �ϓ_�FF<br>
     * <br>
     * ���͒l�F(����) locale:Locale("ja","","")<br>
     *         (���) defaultLocale:Locale("en","","")<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) locales:�v�f��1��List<br>
     *                  Locale{"en","",""}<br>
     *         
     * <br>
     * ����locale�Ɍ���R�[�h�������݂��Ȃ��ꍇ�A�V����locale�̃p�^�[����
     * �������Ȃ��B�܂��AdefaultLocale�Ɍ���R�[�h�������݂��Ȃ��ꍇ�A1�p�^�[����locale�I�u�W�F�N�g�𐶐����A���X�g�Ɋi�[����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetAlternativeLocales01() throws Exception {
        // �O����
        DataSourceMessageSource ds = new DataSourceMessageSource();
        Locale locale = new Locale("ja", "", "");
        Locale defaultLocale = new Locale("en", "", "");
        ds.defaultLocale = defaultLocale;
        
        // �e�X�g���{
        List locales = ds.getAlternativeLocales(locale);
        
        // ����
        assertEquals(1, locales.size());
        assertEquals(defaultLocale, locales.get(0));
    }

    /**
     * testGetAlternativeLocales02() <br>
     * <br>
     * (����n)<br>
     * �ϓ_�FF<br>
     * <br>
     * ���͒l�F(����) locale:Locale("ja","JP","")<br>
     *         (���) defaultLocale:Locale("en","US","")<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) locales:�v�f��2��List<br>
     *                  Locale{"ja","",""},<br>
     *                  Locale{"en","US",""}<br>
     *                  Locale{"en","",""}<br>
     *         
     * <br>
     * ����locale�ɍ��R�[�h�܂ő��݂���ꍇ�A1�p�^�[����Locale�I�u�W�F�N�g��
     * ��������B�܂��AdefaultLocale�ɍ��R�[�h�܂ő��݂���ꍇ�A2�p�^�[����locale�I�u�W�F�N�g�𐶐����A���X�g�Ɋi�[����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetAlternativeLocales02() throws Exception {
        // �O����
        DataSourceMessageSource ds = new DataSourceMessageSource();
        Locale locale = new Locale("ja", "JP", "");
        Locale defaultLocale = new Locale("en", "US", "");
        ds.defaultLocale = defaultLocale;
        
        Locale locale1 = new Locale("ja", "", "");
        Locale defaultLocale2 = new Locale("en", "", "");
        
        // �e�X�g���{
        List locales = ds.getAlternativeLocales(locale);
        
        // ����
        assertEquals(3, locales.size());
        assertEquals(locale1, locales.get(0));
        assertEquals(defaultLocale, locales.get(1));
        assertEquals(defaultLocale2, locales.get(2));
    }

    /**
     * testGetAlternativeLocales03() <br>
     * <br>
     * (����n)<br>
     * �ϓ_�FF<br>
     * <br>
     * ���͒l�F(����) locale:Locale("ja","JP","kansai")<br>
     *         (���) defaultLocale:Locale("en","US","NY")<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) locales:�v�f��5��List<br>
     *                  Locale{"ja","JP",""}<br>
     *                  Locale{"ja","",""}<br>
     *                  Locale{"en","US","NY"}<br>
     *                  Locale{"en","US",""}<br>
     *                  Locale{"en","",""}<br>
     *         
     * <br>
     * ����locale�Ƀo���A���g�R�[�h�܂ő��݂���ꍇ�A2�p�^�[����
     * Locale�I�u�W�F�N�g�𐶐�����B
     * �܂��AdefaultLocale�Ƀo���A���g�R�[�h�܂ő��݂���ꍇ�A
     * 3�p�^�[����locale�I�u�W�F�N�g�𐶐����A���X�g�Ɋi�[����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetAlternativeLocales03() throws Exception {
        // �O����
        DataSourceMessageSource ds = new DataSourceMessageSource();
        Locale locale = new Locale("ja", "JP", "kansai");
        Locale defaultLocale = new Locale("en", "US", "NY");
        ds.defaultLocale = defaultLocale;
        
        Locale locale1 = new Locale("ja", "JP", "");
        Locale locale2 = new Locale("ja", "", "");
        Locale defaultLocale2 = new Locale("en", "US", "");
        Locale defaultLocale3 = new Locale("en", "", "");

        // �e�X�g���{
        List locales = ds.getAlternativeLocales(locale);
        
        // ����
        assertEquals(5, locales.size());
        assertEquals(locale1, locales.get(0));
        assertEquals(locale2, locales.get(1));
        assertEquals(defaultLocale, locales.get(2));
        assertEquals(defaultLocale2, locales.get(3));
        assertEquals(defaultLocale3, locales.get(4));     
    }

    /**
     * testGetAlternativeLocales04() <br>
     * <br>
     * (����n)<br>
     * �ϓ_�FF <br>
     * <br>
     * ���͒l�F(����) locale:Locale("ja","JP","kansai")<br>
     *         (���) defaultLocale:Locale("ja","JP","kansai")<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) locales:�v�f��2��List<br>
     *                  Locale{"ja","JP",""}<br>
     *                  Locale{"ja","",""}<br>
     *         
     * <br>
     * ����locale��defaultLocale�����l�ł������ꍇ�A����locale����
     * �V����locale�I�u�W�F�N�g�𐶐����A���X�g�Ɋi�[����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetAlternativeLocales04() throws Exception {
        // �O����
        DataSourceMessageSource ds = new DataSourceMessageSource();
        Locale locale = new Locale("ja", "JP", "kansai");
        ds.defaultLocale = locale;
        
        Locale locale1 = new Locale("ja", "JP", "");
        Locale locale2 = new Locale("ja", "", "");
 
        // �e�X�g���{
        List locales = ds.getAlternativeLocales(locale);
        
        // ����
        assertEquals(2, locales.size());
        assertEquals(locale1, locales.get(0));
        assertEquals(locale2, locales.get(1)); 
    }

    /**
     * testGetAlternativeLocales05() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FF<br>
     * <br>
     * ���͒l�F(����) locale:Locale("ja","","")<br>
     *         (���) defaultLocale:null<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) locales:�v�f��0��List<br>
     *         
     * <br>
     * defaultLocale�̐ݒ肪�s���Ă��Ȃ��ꍇ�́AList��0�ŕԂ�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetAlternativeLocales05() throws Exception {
        // �O����
        DataSourceMessageSource ds = new DataSourceMessageSource();
        Locale locale = new Locale("ja", "", "");
        ds.defaultLocale = null;
        
        // �e�X�g���{
        List locales = ds.getAlternativeLocales(locale);
        
        // ����
        assertEquals(0, locales.size());
    }
    /**
     * testResolveCode01() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FA<br>
     * <br>
     * ���͒l�F(����) code:"code01"<br>
     *         (����) locale:Locale("ja","JP","")<br>
     *         (���) getMessageFormat(code,locale):"�e�X�g���b�Z�[�W�O�P"<br>
     *         (���) defaultLocale:null<br>
     *         (���) ���b�Z�[�W�̃��P�[��:Locale("ja","JP","")<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) messageFormat:"�e�X�g���b�Z�[�W�O�P"<br>
     *         (��ԕω�) getMessageFormat(code,locale):�w�肳�ꂽ�����ɂ��
     *         �Ăяo���ꂽ���Ƃ��m�F����B<br>
     *         
     * <br>
     * �w�肳�ꂽ������胁�b�Z�[�W�t�H�[�}�b�g�����肵�A�ԋp����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
	public void testResolveCode01() throws Exception {
        // �O����
        DataSourceMessageSource ds = new DataSourceMessageSource();
        String code = "code01";
        Locale locale = new Locale("ja", "JP", "");
        
        //cachedMergedProperties����
        Map map = new HashMap();
        Locale formatlocale = new Locale("ja", "JP", "");
        MessageFormat mFormat = new MessageFormat("");
        mFormat.setLocale(formatlocale);
        mFormat.applyPattern("�e�X�g���b�Z�[�W�O�P");
        map.put(formatlocale, mFormat);
        ds.cachedMessageFormats.put(code, map);
        
        // �e�X�g���{
        MessageFormat mfreturn = ds.resolveCode(code, locale);
        
        // ����
        assertSame(mFormat, mfreturn);
    }

    /**
     * testResolveCode02() <br>
     * <br>
     * (����n)<br>
     * �ϓ_�FA<br>
     * <br>
     * ���͒l�F(����) code:"code01"<br>
     *         (����) locale:Locale("ja","JP","")<br>
     *         (���) getMessageFormat(code,locale):null<br>
     *         (���) defaultLocale:null<br>
     *         (���) ���b�Z�[�W�̃��P�[��:Locale("ja","","")<br>
     *         (���) getAlternativeLocales�i�j:List�o<br>
     *                �@Locale�i"ja")<br>
     *                �p<br>
     *         (���) getMessageFormat(code.locales.get(i)):1��ڂ̖߂�l<br>
     *                "�e�X�g���b�Z�[�W�O�P"<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) messageFormat:"�e�X�g���b�Z�[�W�O�P"<br>
     *         (��ԕω�) getAlternativeLocale:�w�肳�ꂽ�����ɂ��Ăяo���ꂽ
     *         ���Ƃ��m�F����B<br>
     *         (��ԕω�) getMessageFormat(code,locale):�w�肳�ꂽ�����ɂ��
     *         �Ăяo���ꂽ���Ƃ��m�F����B<br>
     *         (��ԕω�) getMessageFormat(code.locales.get(i)):�w�肳�ꂽ������
     *         ���Ăяo���ꂽ���Ƃ��m�F����B<br>
     *         
     * <br>
     * �w�肳�ꂽ�����Ń��b�Z�[�W�t�H�[�}�b�g������ł����A�V���ɍ쐬����������
     * ��胁�b�Z�[�W�t�H�[�}�b�g�����肵�A�ԋp����B
     * �܂��A���P�[����null�ł��G���[�ɂȂ�Ȃ����Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
	public void testResolveCode02() throws Exception {
        // �O����
        DataSourceMessageSource ds = new DataSourceMessageSource();
        String code = "code01";
        Locale locale = new Locale("ja", "JP", "");
        
        //cachedMergedProperties����
        Map map = new HashMap();
        Locale formatlocale = new Locale("ja", "", "");
        MessageFormat mFormat = new MessageFormat("");
        mFormat.setLocale(formatlocale);
        mFormat.applyPattern("�e�X�g���b�Z�[�W�O�P");
        map.put(formatlocale, mFormat);
        ds.cachedMessageFormats.put(code, map);
        
        // �e�X�g���{
        MessageFormat mfreturn = ds.resolveCode(code, locale);
        
        // ����
        assertSame(mFormat, mfreturn);
    }

    /**
     * testResolveCode03() <br>
     * <br>
     * (����n)<br>
     * �ϓ_�FA<br>
     * <br>
     * ���͒l�F(����) code:"code01"<br>
     *         (����) locale:Locale("ja","JP","kansai")<br>
     *         (���) getMessageFormat(code,locale):null<br>
     *         (���) defaultLocale:Locale("en","","")<br>
     *         (���) ���b�Z�[�W�̃��P�[��:Locale("en","","")<br>
     *         (���) getAlternativeLocales�i�j:List�o<br>
     *                  Locale("ja","JP","")<br>
     *                  Locale("ja","","")<br>
     *                �@Locale�i"en","","")<br>
     *                �p<br>
     *         (���) getMessageFormat(code.locales.get(i)):�P��ڂ̖߂�l<br>
     *                null<br>
     *                �Q��ڂ̖߂�l<br>
     *                null<br>
     *                �R��ڂ̖߂�l<br>
     *                "�e�X�g���b�Z�[�W�O�P"<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) messageFormat:"�e�X�g���b�Z�[�W�O�P"<br>
     *         (��ԕω�) getAlternativeLocale:�w�肳�ꂽ�����ɂ��Ăяo���ꂽ
     *         ���Ƃ��m�F����B<br>
     *         (��ԕω�) getMessageFormat(code,locale):�w�肳�ꂽ�����ɂ��
     *         �Ăяo���ꂽ���Ƃ��m�F����B<br>
     *         (��ԕω�) getMessageFormat(code.locales.get(i)):�w�肳�ꂽ����
     *         �ɂ��Ăяo���ꂽ���Ƃ��m�F����B<br>
     *         
     * <br>
     * �w�肳�ꂽ�����Ń��b�Z�[�W�t�H�[�}�b�g������ł����A�V���ɍ쐬��������
     * �ɂ�胁�b�Z�[�W�t�H�[�}�b�g�����肵�A�ԋp����B
     * �܂��A���P�[����null�ł��G���[�ɂȂ�Ȃ����Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
	public void testResolveCode03() throws Exception {
        // �O����
        DataSourceMessageSource ds = new DataSourceMessageSource();
        String code = "code01";
        Locale locale = new Locale("ja", "JP", "kansai");
        Locale defaultLocale = new Locale("en", "", "");
        ds.defaultLocale = defaultLocale;
        
        //cachedMergedProperties����
        Map map = new HashMap();
        Locale formatlocale = new Locale("en", "", "");
        MessageFormat mFormat = new MessageFormat("");
        mFormat.setLocale(formatlocale);
        mFormat.applyPattern("�e�X�g���b�Z�[�W�O�P");
        map.put(formatlocale, mFormat);
        ds.cachedMessageFormats.put(code, map);
        
        // �e�X�g���{
        MessageFormat mfreturn = ds.resolveCode(code, locale);
        
        // ����
        assertSame(mFormat, mfreturn);
    }

    /**
     * testResolveCode04() <br>
     * <br>
     * (����n)<br>
     * �ϓ_�FA <br>
     * <br>
     * ���͒l�F(����) code:"code01"<br>
     *         (����) locale:Locale("ja","JP","kansai")<br>
     *         (���) getMessageFormat(code,locale):null<br>
     *         (���) defaultLocale:Locale("en","","")<br>
     *         (���) ���b�Z�[�W�̃��P�[��:Locale("en","US","NY")<br>
     *         (���) getAlternativeLocales�i�j:List�o<br>
     *                  Locale("ja","JP","")<br>
     *                  Locale("ja","","")<br>
     *                �@Locale�i"en","","")<br>
     *                �p<br>
     *         (���) getMessageFormat(code.locales.get(i)):�P��ڂ̖߂�l<br>
     *                null<br>
     *                �Q��ڂ̖߂�l<br>
     *                null<br>
     *                �R��ڂ̖߂�l<br>
     *                null<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) messageFormat:null<br>
     *         (��ԕω�) getAlternativeLocale:�w�肳�ꂽ�����ɂ��Ăяo���ꂽ
     *         ���Ƃ��m�F����B<br>
     *         (��ԕω�) getMessageFormat(code,locale):�w�肳�ꂽ�����ɂ��
     *         �Ăяo���ꂽ���Ƃ��m�F����B<br>
     *         (��ԕω�) getMessageFormat(code.locales.get(i)):�w�肳�ꂽ����
     *         �ɂ��Ăяo���ꂽ���Ƃ��m�F����B<br>
     *         
     * <br>
     * �w�肳�ꂽ�����y�ѐV���ɐ��������������A���b�Z�[�W�t�H�[�}�b�g��
     * ����ł��Ȃ��ꍇ��null��ԋp����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
	public void testResolveCode04() throws Exception {
        // �O����
        DataSourceMessageSource ds = new DataSourceMessageSource();
        String code = "code01";
        Locale locale = new Locale("ja", "JP", "kansai");
        Locale defaultLocale = new Locale("en", "", "");
        ds.defaultLocale = defaultLocale;
        
        //cachedMergedProperties����
        Map map = new HashMap();
        Locale formatlocale = new Locale("en", "US", "NY");
        MessageFormat mFormat = new MessageFormat("");
        mFormat.setLocale(formatlocale);
        mFormat.applyPattern("�e�X�g���b�Z�[�W�O�P");
        map.put(formatlocale, mFormat);
        ds.cachedMessageFormats.put(code, map);
        
        // �e�X�g���{
        MessageFormat mfreturn = ds.resolveCode(code, locale);
        
        // ����
        assertNull(mfreturn);
    }

    /**
     * testResolveCode05() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FA <br>
     * <br>
     * ���͒l�F(����) code:"code01"<br>
     *         (����) locale:Locale("ja","","")<br>
     *         (���) getMessageFormat(code,locale):null<br>
     *         (���) defaultLocale:null<br>
     *         (���) ���b�Z�[�W�̃��P�[��:Locale("en","US","NY")<br>
     *         (���) getAlternativeLocales�i�j:List�o<br>
     *                �p<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) messageFormat:null<br>
     *         (��ԕω�) getAlternativeLocale:�w�肳�ꂽ�����ŌĂяo����Ă���
     *         ���Ƃ��m�F����B<br>
     *         (��ԕω�) getMessageFormat(code,locale):�w�肳�ꂽ������
     *         �Ăяo����Ă��邱�Ƃ��m�F����B<br>
     *         
     * <br>
     * ���P�[�����擾�ł��Ȃ��ꍇ�Anull��ԋp����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
	public void testResolveCode05() throws Exception {
        // �O����
        DataSourceMessageSource ds = new DataSourceMessageSource();
        String code = "code01";
        Locale locale = new Locale("ja", "", "");
        ds.defaultLocale = null;
        
        //cachedMergedProperties����
        Map map = new HashMap();
        Locale formatlocale = new Locale("en", "US", "NY");
        MessageFormat mFormat = new MessageFormat("");
        mFormat.setLocale(formatlocale);
        mFormat.applyPattern("�e�X�g���b�Z�[�W�O�P");
        map.put(formatlocale, mFormat);
        ds.cachedMessageFormats.put(code, map);
        
        // �e�X�g���{
        MessageFormat mfreturn = ds.resolveCode(code, locale);
        
        // ����
        assertNull(mfreturn);
    }

    /**
     * testGetMessageFormat01() <br>
     * <br>
     * (����n)<br>
     * �ϓ_�FA<br>
     * <br>
     * ���͒l�F(����) code:"code01"<br>
     *         (����) locale:Locale("ja",JP","")<br>
     *         (���) cashedMessageFormats:<"code01",<Locale("ja","JP"),
     *         MessageFormat("")>><br>
     *         (���) ���b�Z�[�W�t�H�[�}�b�g��locale:Locale("ja",JP","")<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) result:���b�Z�[�W�t�H�[�}�b�g�I�u�W�F�N�g<br>
     *         
     * <br>
     * �����ɑΉ�����l�����łɃL���b�V�����ꂽMap�ɂ���A�����o�����l��
     * null�łȂ��ꍇ�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
	public void testGetMessageFormat01() throws Exception {
        // �O����
        DataSourceMessageSource ds 
                = new DataSourceMessageSource();
        String code = "code01";
        Locale locale = new Locale("ja", "JP", "");
        MessageFormat mf = new MessageFormat("");
        Map map = new HashMap();
        map.put(locale, mf);
        ds.cachedMessageFormats.put(code, map);
        
        // �e�X�g���{
        MessageFormat result = ds.getMessageFormat(code, locale);
        
        // ����
        assertSame(mf, result);
    }

    /**
     * testGetMessageFormat02() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FA <br>
     * <br>
     * ���͒l�F(����) code:"code01"<br>
     *         (����) locale:Locale("ja",JP","")<br>
     *         (���) cashedMessageFormats:<"code01",<Locale("ja","JP"),null>><br>
     *         (���) ���b�Z�[�W�t�H�[�}�b�g��locale:Locale("ja",JP","")<br>
     *         (���) getMessage(locale):Properties<"code01","�e�X�g���b�Z�[�W�O�P"><br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) result:���b�Z�[�W�t�H�[�}�b�g�I�u�W�F�N�g<br>
     *         
     * <br>
     * �����ɑΉ�����l�����łɃL���b�V�����ꂽMap�ɂ��邪���o�����l��null�A
     * �����b�Z�[�W���\�[�X�Ɉ����ɑΉ�����l������A
     * ���b�Z�[�W�t�H�[�}�b�g�̐����ɐ��������ꍇ�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
	public void testGetMessageFormat02() throws Exception {
        // �O����
        DataSourceMessageSource_DataSourceMessageSourceStub18 ds 
                = new DataSourceMessageSource_DataSourceMessageSourceStub18();
        String code = "code01";
        Locale locale = new Locale("ja", "JP", "");
        MessageFormat mf = null;
        Map map = new HashMap();
        map.put(locale, mf);
        ds.cachedMessageFormats.put(code, map);
        
        // �e�X�g���{
        MessageFormat result = ds.getMessageFormat(code, locale);
        
        // ����
        assertSame(mf, result);
        assertTrue(ds.isRead_A1);
    }

    /**
     * testGetMessageFormat03() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FA <br>
     * <br>
     * ���͒l�F(����) code:"code01"<br>
     *         (����) locale:Locale("ja",JP","")<br>
     *         (���) cashedMessageFormats:<"code01",<Locale("ja","JP"),null><br>
     *         (���) ���b�Z�[�W�t�H�[�}�b�g��locale:Locale("ja",JP","")<br>
     *         (���) getMessage(locale):Properties<"abc",""><br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) result:null<br>
     *         
     * <br>
     * �����ɑΉ�����l�����łɃL���b�V�����ꂽMap�ɂ��邪���o�����l��null�A
     * �����b�Z�[�W���\�[�X�Ɉ����ɑΉ�����l���Ȃ��ꍇ�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
	public void testGetMessageFormat03() throws Exception {
        // �O����
        DataSourceMessageSource_DataSourceMessageSourceStub19 ds 
                = new DataSourceMessageSource_DataSourceMessageSourceStub19();
        String code = "code01";
        Locale locale = new Locale("ja", "JP", "");
        MessageFormat mf = null;
        Map map = new HashMap();
        map.put(locale, mf);
        ds.cachedMessageFormats.put(code, map);
        
        // �e�X�g���{
        MessageFormat result = ds.getMessageFormat(code, locale);
        
        // ����
        assertNull(result);
        assertTrue(ds.isRead_A1);
    }


    /**
     * testGetMessageFormat04() <br>
     * <br>
     * (����n)<br>
     * �ϓ_�FA <br>
     * <br>
     * ���͒l�F(����) code:"code01"<br>
     *         (����) locale:Locale("ja",JP","")<br>
     *         (���) cashedMessageFormats:<"code01",null><br>
     *         (���) ���b�Z�[�W�t�H�[�}�b�g��locale:Locale("ja",JP","")<br>
     *         (���) getMessage(locale):Properties<"code01","�e�X�g���b�Z�[�W�O�P"><br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) result:���b�Z�[�W�t�H�[�}�b�g�I�u�W�F�N�g<br>
     *         
     * <br>
     * �����ɑΉ�����l�����łɃL���b�V�����ꂽMap�ɂȂ��A�����b�Z�[�W���\�[�X
     * �Ɉ����ɑΉ�����l������A���b�Z�[�W�t�H�[�}�b�g�̐����ɐ��������ꍇ�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetMessageFormat04() throws Exception {
        // �O����
        DataSourceMessageSource_DataSourceMessageSourceStub18 ds 
                = new DataSourceMessageSource_DataSourceMessageSourceStub18();
        String code = "code01";
        Locale locale = new Locale("ja", "JP", "");
        ds.cachedMessageFormats.put(code, null);
        MessageFormat mf = new MessageFormat("");
        ds.messageFormat = mf;
        
        ds.cachedMessageFormats.clear();
        
        // �e�X�g���{
        MessageFormat result = ds.getMessageFormat(code, locale);
        
        // ����
        assertSame(locale, ds.locale);
        assertSame("�e�X�g���b�Z�[�W�O�P", ds.msg);
        assertSame(mf, result);
        
        assertEquals(1, ds.cachedMessageFormats.size());
        Map hm = ds.cachedMessageFormats.get(code);
        assertSame(mf, hm.get(locale));
        assertEquals(1, ds.cachedMessageFormats.size());
        assertTrue(ds.isRead_A1);
        
    }
}
