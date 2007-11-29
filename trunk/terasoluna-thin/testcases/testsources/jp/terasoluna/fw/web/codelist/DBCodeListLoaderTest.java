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
 * {@link jp.terasoluna.fw.web.codelist.DBCodeListLoader} クラスのブラックボックステスト。
 * 
 * <p>
 * <h4>【クラスの概要】</h4>
 * コードリスト情報の初期化をデータベースを用いて行うクラス<br>
 * ・前提条件<br>
 * ・utlib.confを用意しDB接続情報を記述すること。<br>
 * ・create_dbcodetest.sqlで設定されるDB情報があらかじめ設定されている必要がある。<br>
 * このファイルの中では以下のようにDBを作成し、テストパターン毎に下記の要素をDBに設定する。<br>
 * DBの名前：DBCODETEST<br>
 * カラム：KEY, VALUE<br>
 * 要素： <br>
 * （１）KEY = '1' , VALUE = 'abc'<br>
 * （２）KEY = '2' , VALUE = 'xyz'<br>
 * （３）KEY = '3' , VALUE = 'あいうえお'
 * <p>
 * 
 * @see jp.terasoluna.fw.web.codelist.DBCodeListLoader
 */
@SuppressWarnings("unchecked")
public class DBCodeListLoaderTest extends TestCase {

    /**
     * このテストケースを実行する為の
     * GUI アプリケーションを起動する。
     * 
     * @param args java コマンドに設定されたパラメータ
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(DBCodeListLoaderTest.class);
    }

    /**
     * 初期化処理を行う。
     * 
     * @throws Exception このメソッドで発生した例外
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        
        // DBデータ設定       
        UTUtil.deleteAll("DBCODETEST");
        String[][] data = { { "KEY", "VALUE" }, { "1", "abc" }, { "2", "xyz" },
                { "3", "あいうえお" } };
        UTUtil.setData("DBCODETEST", data);
    }

    /**
     * 終了処理を行う。
     * 
     * @throws Exception このメソッドで発生した例外
     * @see junit.framework.TestCase#tearDown()
     */
    @Override
    protected void tearDown() throws Exception {
        
        // DBデータ削除       
        UTUtil.deleteAll("DBCODETEST");
        
        super.tearDown();
    }

    /**
     * コンストラクタ。
     * 
     * @param name このテストケースの名前。
     */
    public DBCodeListLoaderTest(String name) {
        super(name);
    }

    /**
     * testLoad01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(状態) codeLists:not null<br>
     *         
     * <br>
     * 期待値：(状態変化) loadCodeList():実行されない。<br>
     *         
     * <br>
     * codeListsが存在する場合は何もせず終了することを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testLoad01() throws Exception {
        // 前処理
        DBCodeListLoaderImpl01 loader = new DBCodeListLoaderImpl01();
        List codeLists = new ArrayList();
        UTUtil.setPrivateField(loader, "codeLists", codeLists);
        
        // テスト実施
        loader.load();

        // 判定
        assertFalse(loader.isLoadCodeListIsCalled());
    }

    /**
     * testLoad02()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(状態) codeLists:null<br>
     *         
     * <br>
     * 期待値：(状態変化) loadCodeList():実行される。<br>
     *         
     * <br>
     * codeListsが存在しない場合はloadCodeList()が呼び出されることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testLoad02() throws Exception {
        // 前処理
        DBCodeListLoaderImpl01 loader = new DBCodeListLoaderImpl01();
        UTUtil.setPrivateField(loader, "codeLists", null);
        
        // テスト実施
        loader.load();

        // 判定
        assertTrue(loader.isLoadCodeListIsCalled());
    }

    /**
     * testReload01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(状態) codeLists:not null<br>
     *         
     * <br>
     * 期待値：(状態変化) loadCodeList():実行される。<br>
     *         
     * <br>
     * このメソッドが呼ばれるとloadCodeList()が呼ばれることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testReload01() throws Exception {
        // 前処理
        DBCodeListLoaderImpl01 loader = new DBCodeListLoaderImpl01();
        List codeLists = new ArrayList();
        UTUtil.setPrivateField(loader, "codeLists", codeLists);
        
        // テスト実施
        loader.reload();

        // 判定
        assertTrue(loader.isLoadCodeListIsCalled());
    }

    /**
     * testReload02()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(状態) codeLists:null<br>
     *         
     * <br>
     * 期待値：(状態変化) loadCodeList():実行される。<br>
     *         
     * <br>
     * codeListsがnullの場合でもloadCodeList()が呼ばれることを確認する。（synchronizedをかけずに呼ぶ）
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testReload02() throws Exception {
        // 前処理
        DBCodeListLoaderImpl01 loader = new DBCodeListLoaderImpl01();
        UTUtil.setPrivateField(loader, "codeLists", null);
        
        // テスト実施
        loader.reload();

        // 判定
        assertTrue(loader.isLoadCodeListIsCalled());
    }

    /**
     * testLoadCodeList01()
     * <br><br>
     * 
     * (異常系)
     * <br>
     * 観点：CG
     * <br><br>
     * 入力値：(状態) codeLists:null<br>
     *         (状態) dataSource:null<br>
     *         (状態) sql:SELECT KEY,VALUE FROM DBCODETEST WHERE KEY = '1' AND KEY = '3'<br>
     *         
     * <br>
     * 期待値：(状態変化) 例外:InvalidDataAccessApiUsageException<br>
     *                    メッセージ：Property 'dataSource' is required<br>
     *         
     * <br>
     * dataSourceがnullの場合InvalidDataAccessApiUsageExceptionが発生することを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testLoadCodeList01() throws Exception {
        // 前処理
        DBCodeListLoader loader = new DBCodeListLoader();
        UTUtil.setPrivateField(loader, "sql",
            "SELECT KEY,VALUE FROM DBCODETEST WHERE KEY = '1' AND KEY = '3'");
        UTUtil.setPrivateField(loader, "codeLists", null);
        UTUtil.setPrivateField(loader, "dataSource", null);
        
        // テスト実施
        try {
            loader.loadCodeList();
        } catch (InvalidDataAccessApiUsageException e) {
            // 判定
            assertEquals("Property 'dataSource' is required", e.getMessage());   
        }
    }

    /**
     * testLoadCodeList02()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：
     * <br><br>
     * 入力値：(状態) codeLists:null<br>
     *         (状態) dataSource:not null<br>
     *         (状態) sql:SELECT KEY,VALUE FROM DBCODETEST WHERE KEY = '1' AND KEY = '3' ORDER BY KEY ASC<br>
     *         
     * <br>
     * 期待値：(状態変化) codeLists:検索結果が2件正しく設定される。<br>
     *                    codeListsの0番目：id = 1,name = 'abc'<br>
     *                    codeListsの1番目：id = 3,name = 'あいうえお'<br>
     *         
     * <br>
     * sqlが設定されていた場合にそのSQL文を用いて検索することを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testLoadCodeList02() throws Exception {
        // 前処理
        DBCodeListLoader loader = new DBCodeListLoader();
        DataSource ds = new MockDataSource();
        UTUtil.setPrivateField(loader, "codeLists", null);
        UTUtil.setPrivateField(loader, "dataSource", ds);
        UTUtil.setPrivateField(loader, "sql",
            "SELECT KEY,VALUE FROM DBCODETEST WHERE KEY = '1' OR KEY = '3' ORDER BY KEY ASC");
        
        // テスト実施
        loader.loadCodeList();
        
        // 判定
        List<CodeBean> result = (List) UTUtil.getPrivateField(loader, "codeLists");
        
        assertEquals(2, result.size());
        assertEquals("1", result.get(0).getId());
        assertEquals("abc", result.get(0).getName());
        assertEquals("3", result.get(1).getId());
        assertEquals("あいうえお", result.get(1).getName());

    }

    /**
     * testLoadCodeList06()
     * <br><br>
     * 
     * (異常系)
     * <br>
     * 観点：CG
     * <br><br>
     * 入力値：(状態) codeLists:null<br>
     *         (状態) dataSource:not null<br>
     *         (状態) sql:null<br>
     *         
     * <br>
     * 期待値：(状態変化) 例外:InvalidDataAccessApiUsageException<br>
     *                    メッセージ：Property 'sql' is required<br>
     *         
     * <br>
     * SQL文が存在しない場合、InvalidDataAccessApiUsageExceptionが発生することを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testLoadCodeList06() throws Exception {
        // 前処理
        DBCodeListLoader loader = new DBCodeListLoader();
        DataSource ds = new MockDataSource();
        UTUtil.setPrivateField(loader, "codeLists", null);
        UTUtil.setPrivateField(loader, "dataSource", ds);
        UTUtil.setPrivateField(loader, "sql", null);

        // テスト実施
        try {
            loader.loadCodeList();
        } catch (InvalidDataAccessApiUsageException e) {
            // 判定
            assertEquals("Property 'sql' is required", e.getMessage());
        }
    }

    /**
     * testLoadCodeList07()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：D
     * <br><br>
     * 入力値：(状態) codeLists:null<br>
     *         (状態) dataSource:not null<br>
     *         (状態) sql:SELECT KEY,VALUE FROM DBCODETEST WHERE KEY = '200' ORDER BY KEY ASC<br>
     *         (状態) sql文での取得結果:検索結果が0件<br>
     *         
     * <br>
     * 期待値：(状態変化) codeLists:空のList<br>
     *         
     * <br>
     * 検索結果が0件の場合、空のListが登録されることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testLoadCodeList07() throws Exception {
        // 前処理
        DBCodeListLoader loader = new DBCodeListLoader();
        DataSource ds = new MockDataSource();
        UTUtil.setPrivateField(loader, "codeLists", null);
        UTUtil.setPrivateField(loader, "dataSource", ds);
        UTUtil.setPrivateField(loader, "sql", "SELECT KEY,VALUE FROM DBCODETEST WHERE KEY = '200' ORDER BY KEY ASC");

        // テスト実施
        loader.loadCodeList();

        // 判定
        List result = (List) UTUtil.getPrivateField(loader, "codeLists");
        assertEquals(0, result.size());
    }

    /**
     * testLoadCodeList08()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：D
     * <br><br>
     * 入力値：(状態) codeLists:null<br>
     *         (状態) dataSource:not null<br>
     *         (状態) sql:SELECT KEY,VALUE FROM DBCODETEST WHERE KEY = '1'<br>
     *         (状態) sql文での取得結果:検索結果が1件<br>
     *         
     * <br>
     * 期待値：(状態変化) codeLists:検索結果が1件正しく設定される。<br>
     *                    codeListsの0番目：id = 1,name = 'abc'<br>
     *         
     * <br>
     * 検索結果が１件の場合に正常に登録されることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testLoadCodeList08() throws Exception {
        // 前処理
        DBCodeListLoader loader = new DBCodeListLoader();
        DataSource ds = new MockDataSource();
        UTUtil.setPrivateField(loader, "codeLists", null);
        UTUtil.setPrivateField(loader, "dataSource", ds);
        UTUtil.setPrivateField(loader, "sql", "SELECT KEY,VALUE FROM DBCODETEST WHERE KEY = '1'");

        // テスト実施
        loader.loadCodeList();

        // 判定
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
     * (正常系)
     * <br>
     * 観点：D
     * <br><br>
     * 入力値：(状態) codeLists:null<br>
     *         (状態) dataSource:not null<br>
     *         (状態) sql:SELECT KEY,VALUE FROM DBCODETEST WHERE KEY <= '3' ORDER BY KEY ASC<br>
     *         (状態) sql文での取得結果:検索結果が3件<br>
     *         
     * <br>
     * 期待値：(状態変化) codeLists:検索結果が3件正しく設定される。<br>
     *                    codeListsの0番目：id = 1,name = 'abc'<br>
     *                    codeListsの1番目：id = 2,name = 'xyz'<br>
     *                    codeListsの2番目：id = 3,name = 'あいうえお'<br>
     *         
     * <br>
     * 検索結果が３件の場合に正常に登録されることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testLoadCodeList09() throws Exception {
        // 前処理
        DBCodeListLoader loader = new DBCodeListLoader();
        DataSource ds = new MockDataSource();
        UTUtil.setPrivateField(loader, "codeLists", null);
        UTUtil.setPrivateField(loader, "dataSource", ds);
        UTUtil.setPrivateField(loader, "sql", "SELECT KEY,VALUE FROM DBCODETEST WHERE KEY <= '3' ORDER BY KEY ASC");

        // テスト実施
        loader.loadCodeList();

        // 判定
        List<CodeBean> result = (List) UTUtil.getPrivateField(loader,
                "codeLists");
        assertEquals(3, result.size());
        assertEquals("1", result.get(0).getId());
        assertEquals("abc", result.get(0).getName());
        assertEquals("2", result.get(1).getId());
        assertEquals("xyz", result.get(1).getName());
        assertEquals("3", result.get(2).getId());
        assertEquals("あいうえお", result.get(2).getName());
    }

    /**
     * testLoadCodeList10()
     * <br><br>
     * 
     * (異常系)
     * <br>
     * 観点：G
     * <br><br>
     * 入力値：(状態) codeLists:null<br>
     *         (状態) dataSource:not null<br>
     *         (状態) sql:SELECT KEY,VALUE FROM NODB<br>
     *         (状態) sql文での取得結果:テーブルが存在しない<br>
     *         
     * <br>
     * 期待値：(状態変化) 例外:BadSqlGrammarException<br>
     *                    ラップされた例外：SQLException<br>
     *         
     * <br>
     * SQL文で指定したテーブルが存在しない場合BadSqlGrammarExceptionが発生することを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testLoadCodeList10() throws Exception {
        // 前処理
        DBCodeListLoader loader = new DBCodeListLoader();
        DataSource ds = new MockDataSource();
        UTUtil.setPrivateField(loader, "codeLists", null);
        UTUtil.setPrivateField(loader, "dataSource", ds);
        UTUtil.setPrivateField(loader, "sql", "SELECT KEY,VALUE FROM NODB");

        // テスト実施
        try {
            loader.loadCodeList();
            fail();
        } catch (BadSqlGrammarException e) {
            // 判定
            assertEquals(SQLException.class.getName(), e.getCause().getClass()
                    .getName());

        } finally {
            // PostgreSQLの場合、BadSqlGrammarExceptionが発生した場合、
            // ロールバックを行わないと、以降のテストに影響を与える
        	UTUtil.getConnection().rollback();
        }
    }

    /**
     * testLoadCodeList11()
     * <br><br>
     * 
     * (異常系)
     * <br>
     * 観点：G
     * <br><br>
     * 入力値：(状態) codeLists:null<br>
     *         (状態) dataSource:not null<br>
     *         (状態) sql:""<br>
     *         
     * <br>
     * 期待値：(状態変化) 例外:UncategorizedSQLException<br>
     *         
     * <br>
     * SQL文が空文字の場合UncategorizedSQLExceptionが発生することを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testLoadCodeList11() throws Exception {
        // 前処理
        DBCodeListLoader loader = new DBCodeListLoader();
        DataSource ds = new MockDataSource();
        UTUtil.setPrivateField(loader, "codeLists", null);
        UTUtil.setPrivateField(loader, "dataSource", ds);
        UTUtil.setPrivateField(loader, "sql", "");

        // テスト実施
        try {
            loader.loadCodeList();
            fail();
        } catch (UncategorizedSQLException e) {
            // 判定
            // Oracleの場合は、e.getCause()はSQLExceptionです。
            // Postgresの場合は、e.getCause()はorg.postgresql.util.PSQLExceptionです。        	
        }
    }

    /**
     * testLoadCodeList12()
     * <br><br>
     * 
     * (異常系)
     * <br>
     * 観点：G
     * <br><br>
     * 入力値：(状態) codeLists:null<br>
     *         (状態) dataSource:not null<br>
     *         (状態) sql:UPDATE DBCODETEST SET VALUE = 'test'<br>
     *         
     * <br>
     * 期待値：(状態変化) 例外:DataAccessException<br>
     *
     * <br>
     * SQL文がSELECT文ではなくUPDATE文が実行された場合DataAccessExceptionが発生することを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testLoadCodeList12() throws Exception {
        // 前処理
        DBCodeListLoader loader = new DBCodeListLoader();
        DataSource ds = new MockDataSource();
        UTUtil.setPrivateField(loader, "codeLists", null);
        UTUtil.setPrivateField(loader, "dataSource", ds);
        UTUtil.setPrivateField(loader, "sql", "UPDATE DBCODETEST SET VALUE = 'test'");

        // テスト実施
        try {
            loader.loadCodeList();
            fail();
        } catch (DataAccessException e) {
            // 判定
            // Oracleの場合は、eはBadSqlGrammarExceptionです、
            //           e.getCause()はSQLExceptionです。
            // Postgresの場合は、eはUncategorizedSQLExceptionです、
            //           e.getCause()はorg.postgresql.util.PSQLExceptionです。
        }
    }

    /**
     * testLoadCodeList13()
     * <br><br>
     * 
     * (異常系)
     * <br>
     * 観点：G
     * <br><br>
     * 入力値：(状態) codeLists:null<br>
     *         (状態) dataSource:not null<br>
     *         (状態) sql:aaaaa<br>
     *         
     * <br>
     * 期待値：(状態変化) 例外:BadSqlGrammarException<br>
     *                    ラップされた例外：SQLException<br>
     *         
     * <br>
     * SQL文の文法が間違っている場合、BadSqlGrammarExceptionが発生することを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testLoadCodeList13() throws Exception {
        // 前処理
        DBCodeListLoader loader = new DBCodeListLoader();
        DataSource ds = new MockDataSource();
        UTUtil.setPrivateField(loader, "codeLists", null);
        UTUtil.setPrivateField(loader, "dataSource", ds);
        UTUtil.setPrivateField(loader, "sql", "aaaaa");

        // テスト実施
        try {
            loader.loadCodeList();
            fail();
        } catch (BadSqlGrammarException e) {
            // 判定
            assertEquals(SQLException.class.getName(), e.getCause().getClass()
                    .getName());
            
        } finally {
        	// PostgreSQLの場合、BadSqlGrammarExceptionが発生した場合、
            // ロールバックを行わないと、以降のテストに影響を与える
        	UTUtil.getConnection().rollback();
        }
    }

    /**
     * testGetCodeBeans01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(状態) codeLists:null<br>
     *         
     * <br>
     * 期待値：(戻り値) CodeBean[]:空の配列<br>
     *         
     * <br>
     * codeListsがnullの場合空の配列が返ることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetCodeBeans01() throws Exception {
        // 前処理
        DBCodeListLoader loader = new DBCodeListLoader();
        UTUtil.setPrivateField(loader, "codeLists", null);

        // テスト実施
        CodeBean[] result = loader.getCodeBeans();

        // 判定
        assertEquals(0, result.length);
    }

    /**
     * testGetCodeBeans02()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(状態) codeLists:空のリスト<br>
     *         
     * <br>
     * 期待値：(戻り値) CodeBean[]:空の配列<br>
     *         
     * <br>
     * codeListsが空のリストの場合空の配列が返されることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetCodeBeans02() throws Exception {
        // 前処理
        DBCodeListLoader loader = new DBCodeListLoader();
        List<CodeBean> codeLists = new ArrayList<CodeBean>();
        UTUtil.setPrivateField(loader, "codeLists", codeLists);

        // テスト実施
        CodeBean[] result = loader.getCodeBeans();

        // 判定
        assertEquals(0, result.length);
    }

    /**
     * testGetCodeBeans03()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：D
     * <br><br>
     * 入力値：(状態) codeLists:CodeBeanを1つだけ持ったリスト。中身は以下のとおり。<br>
     *                （１）id = "id1"　name="name1"<br>
     *         
     * <br>
     * 期待値：(戻り値) CodeBean[]:id = "id1"　name="name1"のCodeBeanを一つだけ持つ配列。<br>
     *         
     * <br>
     * codeListsが１つ要素を持つ場合codeListsに入っていた１つ要素を持つ配列が返されることを確認する。ただしこれらがシャローコピーでなくディープコピーであることも確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetCodeBeans03() throws Exception {
        // 前処理
        DBCodeListLoader loader = new DBCodeListLoader();
        List<CodeBean> codeLists = new ArrayList<CodeBean>();
        CodeBean codeBean = new CodeBean();
        codeBean.setId("id1");
        codeBean.setName("name1");
        codeLists.add(codeBean);
        UTUtil.setPrivateField(loader, "codeLists", codeLists);

        // テスト実施
        CodeBean[] result = loader.getCodeBeans();

        // 判定
        assertEquals(1, result.length);
        assertEquals("id1", result[0].getId());
        assertEquals("name1", result[0].getName());
        assertNotSame(codeBean, result[0]);
    }

    /**
     * testGetCodeBeans04()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：D
     * <br><br>
     * 入力値：(状態) codeLists:CodeBeanを3つ持ったリスト。中身は以下のとおり。<br>
     *                （１）id = "id1"　name="name1"<br>
     *                （２）id = "id2"　name="name2"<br>
     *                （３）id = "id3"　name="name3"<br>
     *         
     * <br>
     * 期待値：(戻り値) CodeBean[]:（１）id = "id1"　name="name1"<br>
     *                  （２）id = "id2"　name="name2"<br>
     *                  （３）id = "id3"　name="name3"<br>
     *                  の３つのCodeBeanを持った配列。<br>
     *         
     * <br>
     * codeListsが３つ要素を持つ場合codeListsに入っていた３つ要素を持つ配列が返されることを確認する。ただしこれらがシャローコピーでなくディープコピーであることも確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetCodeBeans04() throws Exception {
        // 前処理
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

        // テスト実施
        CodeBean[] result = loader.getCodeBeans();

        // 判定
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
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(状態) this.dataSource:not null<br>
     *         
     * <br>
     * 期待値：(戻り値) DataSource:not null<br>
     *         
     * <br>
     * ※正常系一件のみテスト
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetDataSource01() throws Exception {
        // 前処理
        DBCodeListLoader loader = new DBCodeListLoader();
        DataSource ds = new MockDataSource();
        UTUtil.setPrivateField(loader, "dataSource", ds);
        
        // テスト実施・判定
        assertSame(ds, loader.getDataSource());
    }

    /**
     * testSetDataSource01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) dataSource:not null<br>
     *         (状態) this.dataSource:null<br>
     *         
     * <br>
     * 期待値：(状態変化) this.dataSource:not null<br>
     *         
     * <br>
     * ※正常系一件のみテスト
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetDataSource01() throws Exception {
        // 前処理
        DBCodeListLoader loader = new DBCodeListLoader();
        DataSource ds = new MockDataSource();
        
        // テスト実施
        loader.setDataSource(ds);

        // 判定
        assertSame(ds, UTUtil.getPrivateField(loader, "dataSource"));
    }

    /**
     * testGetSQL01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(状態) sql:"abc"<br>
     *         
     * <br>
     * 期待値：(戻り値) String:"abc"<br>
     *         
     * <br>
     * ※正常系一件のみテスト
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testSql01() throws Exception {
        // 前処理
        DBCodeListLoader loader = new DBCodeListLoader();
        UTUtil.setPrivateField(loader, "sql", "abc");
        
        // テスト実施・判定
        assertEquals("abc", loader.getSql());
    }

    /**
     * testSetSql01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) sql:"abc"<br>
     *         (状態) this.sql:null<br>
     *         
     * <br>
     * 期待値：(状態変化) this.sql:"abc"<br>
     *         
     * <br>
     * ※正常系一件のみテスト
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetSql01() throws Exception {
        // 前処理
        DBCodeListLoader loader = new DBCodeListLoader();
        
        // テスト実施
        loader.setSql("abc");

        // 判定
        assertEquals("abc", UTUtil.getPrivateField(loader, "sql"));
    }

}
