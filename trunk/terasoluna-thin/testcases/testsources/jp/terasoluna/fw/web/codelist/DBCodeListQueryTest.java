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

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.jdbc.core.JdbcTemplate;

import com.mockrunner.mock.jdbc.MockResultSet;

import jp.terasoluna.utlib.MockDataSource;
import jp.terasoluna.utlib.UTUtil;
import junit.framework.TestCase;

/**
 * {@link jp.terasoluna.fw.web.codelist.DBCodeListQuery} クラスのブラックボックステスト。
 * 
 * <p>
 * <h4>【クラスの概要】</h4>
 * データベースからコードリスト取得を行う RDBMSオペレーションクラス。<br>
 * このクラスはjp.terasoluna.fw.web.codelist.DBCodeListLoaderでのみ利用される。<br>
 * ・前提条件<br>
 * mapRowメソッドはスーパークラスから呼び出され、引数のrsがnullや、列要素を一つも持たない状態は存在しない。
 * <p>
 * 
 * @see jp.terasoluna.fw.web.codelist.DBCodeListQuery
 */
@SuppressWarnings("unused")
public class DBCodeListQueryTest extends TestCase {

    /**
     * このテストケースを実行する為の
     * GUI アプリケーションを起動する。
     * 
     * @param args java コマンドに設定されたパラメータ
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(DBCodeListQueryTest.class);
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
    }

    /**
     * 終了処理を行う。
     * 
     * @throws Exception このメソッドで発生した例外
     * @see junit.framework.TestCase#tearDown()
     */
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * コンストラクタ。
     * 
     * @param name このテストケースの名前。
     */
    public DBCodeListQueryTest(String name) {
        super(name);
    }

    /**
     * testDBCodeListQuery01()
     * <br><br>
     * 
     * (異常系)
     * <br>
     * 観点：CG
     * <br><br>
     * 入力値：(引数) dataSource:null<br>
     *         
     * <br>
     * 期待値：(状態変化) 例外:「dataSource is required」と言うメッセージを持ったInvalidDataAccessApiUsageException<br>
     *         
     * <br>
     * dataSourceがnullの場合「dataSource is required」と言うメッセージを持ったInvalidDataAccessApiUsageExceptionが発生することを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testDBCodeListQuery01() throws Exception {

        try {
            // テスト実施
            DBCodeListQuery query = new DBCodeListQuery(null, "abc");
        } catch (InvalidDataAccessApiUsageException e) {
            // 判定
            assertEquals("dataSource is required", e.getMessage());
        }
    }

    /**
     * testDBCodeListQuery02()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) dataSource:not null<br>
     *         (引数) sql:null<br>
     *         
     * <br>
     * 期待値：(状態変化) jdbcTemplateのdataSource<br>
     *                    （親クラスRdbmsOperationのフィールド）:not null<br>
     *         (状態変化) sql<br>
     *                    （親クラスRdbmsOperationのフィールド）:null<br>
     *         
     * <br>
     * dataSourcetがnot null、sqlがnullの場合それぞれ、not null、nullがフィールドにセットされることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testDBCodeListQuery02() throws Exception {
        // 前処理
        DataSource ds = new MockDataSource();

        // テスト実施
        DBCodeListQuery query = new DBCodeListQuery(ds, null);

        // 判定
        JdbcTemplate template = (JdbcTemplate) UTUtil.getPrivateField(query,
                "jdbcTemplate");
        assertSame(ds, template.getDataSource());
        assertNull(UTUtil.getPrivateField(query, "sql"));

    }

    /**
     * testDBCodeListQuery03()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) dataSource:not null<br>
     *         (引数) sql:not null<br>
     *         
     * <br>
     * 期待値：(状態変化) jdbcTemplateのdataSource<br>
     *                    （親クラスRdbmsOperationのフィールド）:not null<br>
     *         (状態変化) sql<br>
     *                    （親クラスRdbmsOperationのフィールド）:not null<br>
     *         
     * <br>
     * dataSourcet、sqlがnot nullの場合二つが正しくセットされることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testDBCodeListQuery03() throws Exception {
        // 前処理
        DataSource ds = new MockDataSource();

        // テスト実施
        DBCodeListQuery query = new DBCodeListQuery(ds, "abc");

        // 判定
        JdbcTemplate template = (JdbcTemplate) UTUtil.getPrivateField(query,
                "jdbcTemplate");
        assertSame(ds, template.getDataSource());
        assertEquals("abc", UTUtil.getPrivateField(query, "sql"));
    }

    /**
     * testMapRow01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：D
     * <br><br>
     * 入力値：(引数) ResultSet rs:not null<br>
     *         (状態) ｒｓの1カラム目:not null<br>
     *         (状態) ｒｓの2カラム目:存在しない<br>
     *         (状態) ｒｓの3カラム目:存在しない<br>
     *         
     * <br>
     * 期待値：(戻り値) CodeBean:idに1カラム目の値、nameに空文字<br>
     *         
     * <br>
     * rsのカラム数が1の場合idが1カラム目の値、nameが空文字のCodeBeanが返ることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testMapRow01() throws Exception {
        // 前処理
        DataSource ds = new MockDataSource();
        DBCodeListQuery query = new DBCodeListQuery(ds, null);
        
        // テスト対象のコンストラクタを使用しているため、再度設定する
        JdbcTemplate template = (JdbcTemplate) UTUtil.getPrivateField(query,
                "jdbcTemplate");
        template.setDataSource(ds);
        UTUtil.setPrivateField(query, "sql", null);

        // 擬似ResultSetの設定
        MockResultSet rs = new MockResultSet("testid");
        List<String> list = new ArrayList<String>();
        list.add("value1");
        rs.addColumn("column1", list);
        rs.first();
        
        // テスト実施
        CodeBean result = (CodeBean) query.mapRow(rs, 0);

        // 判定
        assertEquals("value1", result.getId());
        assertEquals("", result.getName());
    }

    /**
     * testMapRow02()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：D
     * <br><br>
     * 入力値：(引数) ResultSet rs:not null<br>
     *         (状態) ｒｓの1カラム目:not null<br>
     *         (状態) ｒｓの2カラム目:not null<br>
     *         (状態) ｒｓの3カラム目:存在しない<br>
     *         
     * <br>
     * 期待値：(戻り値) CodeBean:idに1カラム目の値、nameに2カラム目の値<br>
     *         
     * <br>
     * rsのカラム数が2の場合idが1カラム目の値、nameが2カラム目の値を持ったCodeBeanが返ることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testMapRow02() throws Exception {
        // 前処理
        DataSource ds = new MockDataSource();
        DBCodeListQuery query = new DBCodeListQuery(ds, null);
        
        // テスト対象のコンストラクタを使用しているため、再度設定する
        JdbcTemplate template = (JdbcTemplate) UTUtil.getPrivateField(query,
                "jdbcTemplate");
        template.setDataSource(ds);
        UTUtil.setPrivateField(query, "sql", null);

        // 擬似ResultSetの設定
        MockResultSet rs = new MockResultSet("testid");
        List<String> list = new ArrayList<String>();
        list.add("value1");
        rs.addColumn("column1", list);
        List<String> list2 = new ArrayList<String>();
        list2.add("value2");
        rs.addColumn("column2", list2);
        rs.first();
        
        // テスト実施
        CodeBean result = (CodeBean) query.mapRow(rs, 0);

        // 判定
        assertEquals("value1", result.getId());
        assertEquals("value2", result.getName());
    }

    /**
     * testMapRow03()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：D
     * <br><br>
     * 入力値：(引数) ResultSet rs:not null<br>
     *         (状態) ｒｓの1カラム目:not null<br>
     *         (状態) ｒｓの2カラム目:not null<br>
     *         (状態) ｒｓの3カラム目:not null<br>
     *         
     * <br>
     * 期待値：(戻り値) CodeBean:idに1カラム目の値、nameに2カラム目の値<br>
     *         
     * <br>
     * rsのカラム数が3の場合idが1カラム目の値、nameが2カラム目の値を持ったCodeBeanが返り3カラム目は無視されることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testMapRow03() throws Exception {
        // 前処理
        DataSource ds = new MockDataSource();
        DBCodeListQuery query = new DBCodeListQuery(ds, null);
        
        // テスト対象のコンストラクタを使用しているため、再度設定する
        JdbcTemplate template = (JdbcTemplate) UTUtil.getPrivateField(query,
                "jdbcTemplate");
        template.setDataSource(ds);
        UTUtil.setPrivateField(query, "sql", null);

        // 擬似ResultSetの設定
        MockResultSet rs = new MockResultSet("testid");
        List<String> list = new ArrayList<String>();
        list.add("value1");
        rs.addColumn("column1", list);
        List<String> list2 = new ArrayList<String>();
        list2.add("value2");
        rs.addColumn("column2", list2);
        List<String> list3 = new ArrayList<String>();
        list3.add("value3");
        rs.addColumn("column3", list3);
        rs.first();
        
        // テスト実施
        CodeBean result = (CodeBean) query.mapRow(rs, 0);

        // 判定
        assertEquals("value1", result.getId());
        assertEquals("value2", result.getName());
    }

    /**
     * testMapRow04()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：CD
     * <br><br>
     * 入力値：(引数) ResultSet rs:not null<br>
     *         (状態) ｒｓの1カラム目:null<br>
     *         (状態) ｒｓの2カラム目:null<br>
     *         (状態) ｒｓの3カラム目:存在しない<br>
     *         
     * <br>
     * 期待値：(戻り値) CodeBean:idが空文字、nameが空文字<br>
     *         
     * <br>
     * rsのカラム数が2の場合で1カラム目、2カラム目がnullの場合idに空文字、nameに空文字の値を持ったCodeBeanが返されることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testMapRow04() throws Exception {
        // 前処理
        DataSource ds = new MockDataSource();
        DBCodeListQuery query = new DBCodeListQuery(ds, null);
        
        // テスト対象のコンストラクタを使用しているため、再度設定する
        JdbcTemplate template = (JdbcTemplate) UTUtil.getPrivateField(query,
                "jdbcTemplate");
        template.setDataSource(ds);
        UTUtil.setPrivateField(query, "sql", null);

        // 擬似ResultSetの設定
        MockResultSet rs = new MockResultSet("testid");
        rs.addColumn("column1");
        rs.addColumn("column2");
        
        // テスト実施
        CodeBean result = (CodeBean) query.mapRow(rs, 0);

        // 判定
        assertEquals("", result.getId());
        assertEquals("", result.getName());
    }

}
