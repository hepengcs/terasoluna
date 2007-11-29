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

package jp.terasoluna.fw.web.codelist;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import jp.terasoluna.utlib.MockDataSource;
import jp.terasoluna.utlib.UTUtil;
import junit.framework.TestCase;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.UncategorizedSQLException;

/**
 * {@link jp.terasoluna.fw.web.codelist.DBCodeListLoader} �N���X�̃u���b�N�{�b�N�X�e�X�g�B
 * 
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4>
 * �R�[�h���X�g���̏��������f�[�^�x�[�X��p���čs���N���X<br>
 * �E�O�����<br>
 * �Eutlib.conf��p�ӂ�DB�ڑ������L�q���邱�ƁB<br>
 * �Ecreate_dbcodetest.sql�Őݒ肳���DB��񂪂��炩���ߐݒ肳��Ă���K�v������B<br>
 * ���̃t�@�C���̒��ł͈ȉ��̂悤��DB���쐬���A�e�X�g�p�^�[�����ɉ��L�̗v�f��DB�ɐݒ肷��B<br>
 * DB�̖��O�FDBCODETEST<br>
 * �J�����FKEY, VALUE<br>
 * �v�f�F <br>
 * �i�P�jKEY = '1' , VALUE = 'abc'<br>
 * �i�Q�jKEY = '2' , VALUE = 'xyz'<br>
 * �i�R�jKEY = '3' , VALUE = '����������'
 * <p>
 * 
 * @see jp.terasoluna.fw.web.codelist.DBCodeListLoader
 */
@SuppressWarnings("unchecked")
public class DBCodeListLoaderTest extends TestCase {

    /**
     * ���̃e�X�g�P�[�X�����s����ׂ�
     * GUI �A�v���P�[�V�������N������B
     * 
     * @param args java �R�}���h�ɐݒ肳�ꂽ�p�����[�^
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(DBCodeListLoaderTest.class);
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
        
        // DB�f�[�^�ݒ�       
        UTUtil.deleteAll("DBCODETEST");
        String[][] data = { { "KEY", "VALUE" }, { "1", "abc" }, { "2", "xyz" },
                { "3", "����������" } };
        UTUtil.setData("DBCODETEST", data);
    }

    /**
     * �I���������s���B
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     * @see junit.framework.TestCase#tearDown()
     */
    @Override
    protected void tearDown() throws Exception {
        
        // DB�f�[�^�폜       
        UTUtil.deleteAll("DBCODETEST");
        
        super.tearDown();
    }

    /**
     * �R���X�g���N�^�B
     * 
     * @param name ���̃e�X�g�P�[�X�̖��O�B
     */
    public DBCodeListLoaderTest(String name) {
        super(name);
    }

    /**
     * testLoad01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(���) codeLists:not null<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) loadCodeList():���s����Ȃ��B<br>
     *         
     * <br>
     * codeLists�����݂���ꍇ�͉��������I�����邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testLoad01() throws Exception {
        // �O����
        DBCodeListLoaderImpl01 loader = new DBCodeListLoaderImpl01();
        List codeLists = new ArrayList();
        UTUtil.setPrivateField(loader, "codeLists", codeLists);
        
        // �e�X�g���{
        loader.load();

        // ����
        assertFalse(loader.isLoadCodeListIsCalled());
    }

    /**
     * testLoad02()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(���) codeLists:null<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) loadCodeList():���s�����B<br>
     *         
     * <br>
     * codeLists�����݂��Ȃ��ꍇ��loadCodeList()���Ăяo����邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testLoad02() throws Exception {
        // �O����
        DBCodeListLoaderImpl01 loader = new DBCodeListLoaderImpl01();
        UTUtil.setPrivateField(loader, "codeLists", null);
        
        // �e�X�g���{
        loader.load();

        // ����
        assertTrue(loader.isLoadCodeListIsCalled());
    }

    /**
     * testReload01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(���) codeLists:not null<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) loadCodeList():���s�����B<br>
     *         
     * <br>
     * ���̃��\�b�h���Ă΂���loadCodeList()���Ă΂�邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testReload01() throws Exception {
        // �O����
        DBCodeListLoaderImpl01 loader = new DBCodeListLoaderImpl01();
        List codeLists = new ArrayList();
        UTUtil.setPrivateField(loader, "codeLists", codeLists);
        
        // �e�X�g���{
        loader.reload();

        // ����
        assertTrue(loader.isLoadCodeListIsCalled());
    }

    /**
     * testReload02()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(���) codeLists:null<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) loadCodeList():���s�����B<br>
     *         
     * <br>
     * codeLists��null�̏ꍇ�ł�loadCodeList()���Ă΂�邱�Ƃ��m�F����B�isynchronized���������ɌĂԁj
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testReload02() throws Exception {
        // �O����
        DBCodeListLoaderImpl01 loader = new DBCodeListLoaderImpl01();
        UTUtil.setPrivateField(loader, "codeLists", null);
        
        // �e�X�g���{
        loader.reload();

        // ����
        assertTrue(loader.isLoadCodeListIsCalled());
    }

    /**
     * testLoadCodeList01()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FCG
     * <br><br>
     * ���͒l�F(���) codeLists:null<br>
     *         (���) dataSource:null<br>
     *         (���) sql:SELECT KEY,VALUE FROM DBCODETEST WHERE KEY = '1' AND KEY = '3'<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) ��O:InvalidDataAccessApiUsageException<br>
     *                    ���b�Z�[�W�FProperty 'dataSource' is required<br>
     *         
     * <br>
     * dataSource��null�̏ꍇInvalidDataAccessApiUsageException���������邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testLoadCodeList01() throws Exception {
        // �O����
        DBCodeListLoader loader = new DBCodeListLoader();
        UTUtil.setPrivateField(loader, "sql",
            "SELECT KEY,VALUE FROM DBCODETEST WHERE KEY = '1' AND KEY = '3'");
        UTUtil.setPrivateField(loader, "codeLists", null);
        UTUtil.setPrivateField(loader, "dataSource", null);
        
        // �e�X�g���{
        try {
            loader.loadCodeList();
        } catch (InvalidDataAccessApiUsageException e) {
            // ����
            assertEquals("Property 'dataSource' is required", e.getMessage());   
        }
    }

    /**
     * testLoadCodeList02()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�F
     * <br><br>
     * ���͒l�F(���) codeLists:null<br>
     *         (���) dataSource:not null<br>
     *         (���) sql:SELECT KEY,VALUE FROM DBCODETEST WHERE KEY = '1' AND KEY = '3' ORDER BY KEY ASC<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) codeLists:�������ʂ�2���������ݒ肳���B<br>
     *                    codeLists��0�ԖځFid = 1,name = 'abc'<br>
     *                    codeLists��1�ԖځFid = 3,name = '����������'<br>
     *         
     * <br>
     * sql���ݒ肳��Ă����ꍇ�ɂ���SQL����p���Č������邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testLoadCodeList02() throws Exception {
        // �O����
        DBCodeListLoader loader = new DBCodeListLoader();
        DataSource ds = new MockDataSource();
        UTUtil.setPrivateField(loader, "codeLists", null);
        UTUtil.setPrivateField(loader, "dataSource", ds);
        UTUtil.setPrivateField(loader, "sql",
            "SELECT KEY,VALUE FROM DBCODETEST WHERE KEY = '1' OR KEY = '3' ORDER BY KEY ASC");
        
        // �e�X�g���{
        loader.loadCodeList();
        
        // ����
        List<CodeBean> result = (List) UTUtil.getPrivateField(loader, "codeLists");
        
        assertEquals(2, result.size());
        assertEquals("1", result.get(0).getId());
        assertEquals("abc", result.get(0).getName());
        assertEquals("3", result.get(1).getId());
        assertEquals("����������", result.get(1).getName());

    }

    /**
     * testLoadCodeList06()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FCG
     * <br><br>
     * ���͒l�F(���) codeLists:null<br>
     *         (���) dataSource:not null<br>
     *         (���) sql:null<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) ��O:InvalidDataAccessApiUsageException<br>
     *                    ���b�Z�[�W�FProperty 'sql' is required<br>
     *         
     * <br>
     * SQL�������݂��Ȃ��ꍇ�AInvalidDataAccessApiUsageException���������邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testLoadCodeList06() throws Exception {
        // �O����
        DBCodeListLoader loader = new DBCodeListLoader();
        DataSource ds = new MockDataSource();
        UTUtil.setPrivateField(loader, "codeLists", null);
        UTUtil.setPrivateField(loader, "dataSource", ds);
        UTUtil.setPrivateField(loader, "sql", null);

        // �e�X�g���{
        try {
            loader.loadCodeList();
        } catch (InvalidDataAccessApiUsageException e) {
            // ����
            assertEquals("Property 'sql' is required", e.getMessage());
        }
    }

    /**
     * testLoadCodeList07()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FD
     * <br><br>
     * ���͒l�F(���) codeLists:null<br>
     *         (���) dataSource:not null<br>
     *         (���) sql:SELECT KEY,VALUE FROM DBCODETEST WHERE KEY = '200' ORDER BY KEY ASC<br>
     *         (���) sql���ł̎擾����:�������ʂ�0��<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) codeLists:���List<br>
     *         
     * <br>
     * �������ʂ�0���̏ꍇ�A���List���o�^����邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testLoadCodeList07() throws Exception {
        // �O����
        DBCodeListLoader loader = new DBCodeListLoader();
        DataSource ds = new MockDataSource();
        UTUtil.setPrivateField(loader, "codeLists", null);
        UTUtil.setPrivateField(loader, "dataSource", ds);
        UTUtil.setPrivateField(loader, "sql", "SELECT KEY,VALUE FROM DBCODETEST WHERE KEY = '200' ORDER BY KEY ASC");

        // �e�X�g���{
        loader.loadCodeList();

        // ����
        List result = (List) UTUtil.getPrivateField(loader, "codeLists");
        assertEquals(0, result.size());
    }

    /**
     * testLoadCodeList08()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FD
     * <br><br>
     * ���͒l�F(���) codeLists:null<br>
     *         (���) dataSource:not null<br>
     *         (���) sql:SELECT KEY,VALUE FROM DBCODETEST WHERE KEY = '1'<br>
     *         (���) sql���ł̎擾����:�������ʂ�1��<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) codeLists:�������ʂ�1���������ݒ肳���B<br>
     *                    codeLists��0�ԖځFid = 1,name = 'abc'<br>
     *         
     * <br>
     * �������ʂ��P���̏ꍇ�ɐ���ɓo�^����邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testLoadCodeList08() throws Exception {
        // �O����
        DBCodeListLoader loader = new DBCodeListLoader();
        DataSource ds = new MockDataSource();
        UTUtil.setPrivateField(loader, "codeLists", null);
        UTUtil.setPrivateField(loader, "dataSource", ds);
        UTUtil.setPrivateField(loader, "sql", "SELECT KEY,VALUE FROM DBCODETEST WHERE KEY = '1'");

        // �e�X�g���{
        loader.loadCodeList();

        // ����
        List<CodeBean> result = (List) UTUtil.getPrivateField(loader,
                "codeLists");
        assertEquals(1, result.size());
        assertEquals("1", result.get(0).getId());
        assertEquals("abc", result.get(0).getName());
    }

    /**
     * testLoadCodeList09()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FD
     * <br><br>
     * ���͒l�F(���) codeLists:null<br>
     *         (���) dataSource:not null<br>
     *         (���) sql:SELECT KEY,VALUE FROM DBCODETEST WHERE KEY <= '3' ORDER BY KEY ASC<br>
     *         (���) sql���ł̎擾����:�������ʂ�3��<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) codeLists:�������ʂ�3���������ݒ肳���B<br>
     *                    codeLists��0�ԖځFid = 1,name = 'abc'<br>
     *                    codeLists��1�ԖځFid = 2,name = 'xyz'<br>
     *                    codeLists��2�ԖځFid = 3,name = '����������'<br>
     *         
     * <br>
     * �������ʂ��R���̏ꍇ�ɐ���ɓo�^����邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testLoadCodeList09() throws Exception {
        // �O����
        DBCodeListLoader loader = new DBCodeListLoader();
        DataSource ds = new MockDataSource();
        UTUtil.setPrivateField(loader, "codeLists", null);
        UTUtil.setPrivateField(loader, "dataSource", ds);
        UTUtil.setPrivateField(loader, "sql", "SELECT KEY,VALUE FROM DBCODETEST WHERE KEY <= '3' ORDER BY KEY ASC");

        // �e�X�g���{
        loader.loadCodeList();

        // ����
        List<CodeBean> result = (List) UTUtil.getPrivateField(loader,
                "codeLists");
        assertEquals(3, result.size());
        assertEquals("1", result.get(0).getId());
        assertEquals("abc", result.get(0).getName());
        assertEquals("2", result.get(1).getId());
        assertEquals("xyz", result.get(1).getName());
        assertEquals("3", result.get(2).getId());
        assertEquals("����������", result.get(2).getName());
    }

    /**
     * testLoadCodeList10()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(���) codeLists:null<br>
     *         (���) dataSource:not null<br>
     *         (���) sql:SELECT KEY,VALUE FROM NODB<br>
     *         (���) sql���ł̎擾����:�e�[�u�������݂��Ȃ�<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) ��O:BadSqlGrammarException<br>
     *                    ���b�v���ꂽ��O�FSQLException<br>
     *         
     * <br>
     * SQL���Ŏw�肵���e�[�u�������݂��Ȃ��ꍇBadSqlGrammarException���������邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testLoadCodeList10() throws Exception {
        // �O����
        DBCodeListLoader loader = new DBCodeListLoader();
        DataSource ds = new MockDataSource();
        UTUtil.setPrivateField(loader, "codeLists", null);
        UTUtil.setPrivateField(loader, "dataSource", ds);
        UTUtil.setPrivateField(loader, "sql", "SELECT KEY,VALUE FROM NODB");

        // �e�X�g���{
        try {
            loader.loadCodeList();
            fail();
        } catch (BadSqlGrammarException e) {
            // ����
            assertEquals(SQLException.class.getName(), e.getCause().getClass()
                    .getName());

        } finally {
            // PostgreSQL�̏ꍇ�ABadSqlGrammarException�����������ꍇ�A
            // ���[���o�b�N���s��Ȃ��ƁA�ȍ~�̃e�X�g�ɉe����^����
        	UTUtil.getConnection().rollback();
        }
    }

    /**
     * testLoadCodeList11()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(���) codeLists:null<br>
     *         (���) dataSource:not null<br>
     *         (���) sql:""<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) ��O:UncategorizedSQLException<br>
     *         
     * <br>
     * SQL�����󕶎��̏ꍇUncategorizedSQLException���������邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testLoadCodeList11() throws Exception {
        // �O����
        DBCodeListLoader loader = new DBCodeListLoader();
        DataSource ds = new MockDataSource();
        UTUtil.setPrivateField(loader, "codeLists", null);
        UTUtil.setPrivateField(loader, "dataSource", ds);
        UTUtil.setPrivateField(loader, "sql", "");

        // �e�X�g���{
        try {
            loader.loadCodeList();
            fail();
        } catch (UncategorizedSQLException e) {
            // ����
            // Oracle�̏ꍇ�́Ae.getCause()��SQLException�ł��B
            // Postgres�̏ꍇ�́Ae.getCause()��org.postgresql.util.PSQLException�ł��B        	
        }
    }

    /**
     * testLoadCodeList12()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(���) codeLists:null<br>
     *         (���) dataSource:not null<br>
     *         (���) sql:UPDATE DBCODETEST SET VALUE = 'test'<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) ��O:DataAccessException<br>
     *
     * <br>
     * SQL����SELECT���ł͂Ȃ�UPDATE�������s���ꂽ�ꍇDataAccessException���������邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testLoadCodeList12() throws Exception {
        // �O����
        DBCodeListLoader loader = new DBCodeListLoader();
        DataSource ds = new MockDataSource();
        UTUtil.setPrivateField(loader, "codeLists", null);
        UTUtil.setPrivateField(loader, "dataSource", ds);
        UTUtil.setPrivateField(loader, "sql", "UPDATE DBCODETEST SET VALUE = 'test'");

        // �e�X�g���{
        try {
            loader.loadCodeList();
            fail();
        } catch (DataAccessException e) {
            // ����
            // Oracle�̏ꍇ�́Ae��BadSqlGrammarException�ł��A
            //           e.getCause()��SQLException�ł��B
            // Postgres�̏ꍇ�́Ae��UncategorizedSQLException�ł��A
            //           e.getCause()��org.postgresql.util.PSQLException�ł��B
        }
    }

    /**
     * testLoadCodeList13()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(���) codeLists:null<br>
     *         (���) dataSource:not null<br>
     *         (���) sql:aaaaa<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) ��O:BadSqlGrammarException<br>
     *                    ���b�v���ꂽ��O�FSQLException<br>
     *         
     * <br>
     * SQL���̕��@���Ԉ���Ă���ꍇ�ABadSqlGrammarException���������邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testLoadCodeList13() throws Exception {
        // �O����
        DBCodeListLoader loader = new DBCodeListLoader();
        DataSource ds = new MockDataSource();
        UTUtil.setPrivateField(loader, "codeLists", null);
        UTUtil.setPrivateField(loader, "dataSource", ds);
        UTUtil.setPrivateField(loader, "sql", "aaaaa");

        // �e�X�g���{
        try {
            loader.loadCodeList();
            fail();
        } catch (BadSqlGrammarException e) {
            // ����
            assertEquals(SQLException.class.getName(), e.getCause().getClass()
                    .getName());
            
        } finally {
        	// PostgreSQL�̏ꍇ�ABadSqlGrammarException�����������ꍇ�A
            // ���[���o�b�N���s��Ȃ��ƁA�ȍ~�̃e�X�g�ɉe����^����
        	UTUtil.getConnection().rollback();
        }
    }

    /**
     * testGetCodeBeans01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(���) codeLists:null<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) CodeBean[]:��̔z��<br>
     *         
     * <br>
     * codeLists��null�̏ꍇ��̔z�񂪕Ԃ邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetCodeBeans01() throws Exception {
        // �O����
        DBCodeListLoader loader = new DBCodeListLoader();
        UTUtil.setPrivateField(loader, "codeLists", null);

        // �e�X�g���{
        CodeBean[] result = loader.getCodeBeans();

        // ����
        assertEquals(0, result.length);
    }

    /**
     * testGetCodeBeans02()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(���) codeLists:��̃��X�g<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) CodeBean[]:��̔z��<br>
     *         
     * <br>
     * codeLists����̃��X�g�̏ꍇ��̔z�񂪕Ԃ���邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetCodeBeans02() throws Exception {
        // �O����
        DBCodeListLoader loader = new DBCodeListLoader();
        List<CodeBean> codeLists = new ArrayList<CodeBean>();
        UTUtil.setPrivateField(loader, "codeLists", codeLists);

        // �e�X�g���{
        CodeBean[] result = loader.getCodeBeans();

        // ����
        assertEquals(0, result.length);
    }

    /**
     * testGetCodeBeans03()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FD
     * <br><br>
     * ���͒l�F(���) codeLists:CodeBean��1�������������X�g�B���g�͈ȉ��̂Ƃ���B<br>
     *                �i�P�jid = "id1"�@name="name1"<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) CodeBean[]:id = "id1"�@name="name1"��CodeBean����������z��B<br>
     *         
     * <br>
     * codeLists���P�v�f�����ꍇcodeLists�ɓ����Ă����P�v�f�����z�񂪕Ԃ���邱�Ƃ��m�F����B����������炪�V�����[�R�s�[�łȂ��f�B�[�v�R�s�[�ł��邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetCodeBeans03() throws Exception {
        // �O����
        DBCodeListLoader loader = new DBCodeListLoader();
        List<CodeBean> codeLists = new ArrayList<CodeBean>();
        CodeBean codeBean = new CodeBean();
        codeBean.setId("id1");
        codeBean.setName("name1");
        codeLists.add(codeBean);
        UTUtil.setPrivateField(loader, "codeLists", codeLists);

        // �e�X�g���{
        CodeBean[] result = loader.getCodeBeans();

        // ����
        assertEquals(1, result.length);
        assertEquals("id1", result[0].getId());
        assertEquals("name1", result[0].getName());
        assertNotSame(codeBean, result[0]);
    }

    /**
     * testGetCodeBeans04()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FD
     * <br><br>
     * ���͒l�F(���) codeLists:CodeBean��3���������X�g�B���g�͈ȉ��̂Ƃ���B<br>
     *                �i�P�jid = "id1"�@name="name1"<br>
     *                �i�Q�jid = "id2"�@name="name2"<br>
     *                �i�R�jid = "id3"�@name="name3"<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) CodeBean[]:�i�P�jid = "id1"�@name="name1"<br>
     *                  �i�Q�jid = "id2"�@name="name2"<br>
     *                  �i�R�jid = "id3"�@name="name3"<br>
     *                  �̂R��CodeBean���������z��B<br>
     *         
     * <br>
     * codeLists���R�v�f�����ꍇcodeLists�ɓ����Ă����R�v�f�����z�񂪕Ԃ���邱�Ƃ��m�F����B����������炪�V�����[�R�s�[�łȂ��f�B�[�v�R�s�[�ł��邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetCodeBeans04() throws Exception {
        // �O����
        DBCodeListLoader loader = new DBCodeListLoader();
        List<CodeBean> codeLists = new ArrayList<CodeBean>();
        CodeBean codeBean = new CodeBean();
        codeBean.setId("id1");
        codeBean.setName("name1");
        CodeBean codeBean2 = new CodeBean();
        codeBean2.setId("id2");
        codeBean2.setName("name2");
        CodeBean codeBean3 = new CodeBean();
        codeBean3.setId("id3");
        codeBean3.setName("name3");
        codeLists.add(codeBean);
        codeLists.add(codeBean2);
        codeLists.add(codeBean3);
        UTUtil.setPrivateField(loader, "codeLists", codeLists);

        // �e�X�g���{
        CodeBean[] result = loader.getCodeBeans();

        // ����
        assertEquals(3, result.length);
        assertEquals("id1", result[0].getId());
        assertEquals("name1", result[0].getName());
        assertNotSame(codeBean, result[0]);
        assertEquals("id2", result[1].getId());
        assertEquals("name2", result[1].getName());
        assertNotSame(codeBean2, result[1]);
        assertEquals("id3", result[2].getId());
        assertEquals("name3", result[2].getName());
        assertNotSame(codeBean3, result[2]);
    }

    /**
     * testGetDataSource01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(���) this.dataSource:not null<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) DataSource:not null<br>
     *         
     * <br>
     * ������n�ꌏ�̂݃e�X�g
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetDataSource01() throws Exception {
        // �O����
        DBCodeListLoader loader = new DBCodeListLoader();
        DataSource ds = new MockDataSource();
        UTUtil.setPrivateField(loader, "dataSource", ds);
        
        // �e�X�g���{�E����
        assertSame(ds, loader.getDataSource());
    }

    /**
     * testSetDataSource01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) dataSource:not null<br>
     *         (���) this.dataSource:null<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) this.dataSource:not null<br>
     *         
     * <br>
     * ������n�ꌏ�̂݃e�X�g
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetDataSource01() throws Exception {
        // �O����
        DBCodeListLoader loader = new DBCodeListLoader();
        DataSource ds = new MockDataSource();
        
        // �e�X�g���{
        loader.setDataSource(ds);

        // ����
        assertSame(ds, UTUtil.getPrivateField(loader, "dataSource"));
    }

    /**
     * testGetSQL01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(���) sql:"abc"<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) String:"abc"<br>
     *         
     * <br>
     * ������n�ꌏ�̂݃e�X�g
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSql01() throws Exception {
        // �O����
        DBCodeListLoader loader = new DBCodeListLoader();
        UTUtil.setPrivateField(loader, "sql", "abc");
        
        // �e�X�g���{�E����
        assertEquals("abc", loader.getSql());
    }

    /**
     * testSetSql01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) sql:"abc"<br>
     *         (���) this.sql:null<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) this.sql:"abc"<br>
     *         
     * <br>
     * ������n�ꌏ�̂݃e�X�g
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetSql01() throws Exception {
        // �O����
        DBCodeListLoader loader = new DBCodeListLoader();
        
        // �e�X�g���{
        loader.setSql("abc");

        // ����
        assertEquals("abc", UTUtil.getPrivateField(loader, "sql"));
    }

}
