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

package jp.terasoluna.fw.web.struts.action;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.mockrunner.mock.jdbc.MockResultSet;

import jp.terasoluna.fw.exception.SystemException;
import jp.terasoluna.utlib.LogUTUtil;
import jp.terasoluna.utlib.UTUtil;
import junit.framework.TestCase;

/**
 * {@link jp.terasoluna.fw.web.struts.action.DBMessageBeanMappingSqlQuery} 
 * クラスのブラックボックステスト。
 * 
 * <p>
 * <h4>【クラスの概要】</h4>
 * DBから取得した行をDBMessageBeanインスタンスに詰め替えて返すメソッドを
 * 実装したMappingSqlQuery実装クラス。
 * <p>
 * 
 * @see jp.terasoluna.fw.web.struts.action.DBMessageBeanMappingSqlQuery
 */
public class DBMessageBeanMappingSqlQueryTest extends TestCase {

    /**
     * このテストケースを実行する為の
     * GUI アプリケーションを起動する。
     * 
     * @param args java コマンドに設定されたパラメータ
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(DBMessageBeanMappingSqlQueryTest.class);
    }

    /**
     * コンストラクタ。
     * 
     * @param name このテストケースの名前。
     */
    public DBMessageBeanMappingSqlQueryTest(String name) {
        super(name);
    }

    /**
     * testMapRow01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) rs:|"test01"|"テストメッセージ０１"|<br>
     *                という内容のResultSet<br>
     *         (引数) rowNum:1<br>
     *         
     * <br>
     * 期待値：(戻り値) DBMessageBean:key->"test01"<br>
     *                  value->"テストメッセージ０１"<br>
     *         (状態変化) createDBMessageBean:引数に前提条件のrsを設定して呼び出される<br>
     *         
     * <br>
     * createDBMessageBeanメソッドが呼び出されていることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testMapRow01() throws Exception {
        // 前処理
        DataSource ds = null;
        String sql = null;
        DBMessageBeanMappingSqlQuery query 
            = new DBMessageBeanMappingSqlQuery(ds, sql);
        
        // 擬似ResultSetの設定
        MockResultSet rs = new MockResultSet("testid");
        
        List<String> list1 = new ArrayList<String>();
        list1.add("test01");
        rs.addColumn(list1);

        List<String> list2 = new ArrayList<String>();
        list2.add("テストメッセージ０１");
        rs.addColumn(list2);
        
        rs.first();
        
        // テスト実施
        DBMessageBean actual = query.mapRow(rs, 1);

        // 判定
        assertEquals("test01", actual.getKey());
        assertEquals("テストメッセージ０１", actual.getValue());
    }


    /**
     * testCreateDBMessageBean01()
     * <br><br>
     * 
     * (異常系)
     * <br>
     * 観点：G
     * <br><br>
     * 入力値：(引数) rs:カラム数が0のResultSet<br>
     *         
     * <br>
     * 期待値：(状態変化) 例外:SystemException：<br>
     *                    エラーコード："errors.db.message.bean.mapping.sql.query"<br>
     *                    ラップした例外：null<br>
     *         (状態変化) ログ:【エラーログ】<br>
     *                    ＜メッセージ＞<br>
     *                    "SQL for DB message returns 0 column(s) result set."<br>
     *         
     * <br>
     * ResultSetのカラム数が0の場合、エラーログを出力し、
     * 例外をスローすることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testCreateDBMessageBean01() throws Exception {
        // 前処理
        DataSource ds = null;
        String sql = null;
        DBMessageBeanMappingSqlQuery query 
            = new DBMessageBeanMappingSqlQuery(ds, sql);
        
        // 擬似ResultSetの設定
        MockResultSet rs = new MockResultSet("testid");        
        rs.first();

        // テスト実施
        try {
            query.createDBMessageBean(rs);
            fail();
        } catch (SystemException e) {
            // 判定
            assertEquals("errors.db.message.bean.mapping.sql.query",
                e.getErrorCode());
            assertTrue(LogUTUtil.checkError(
                "SQL for DB message returns 0 column(s) result set."));
        }
    }

    /**
     * testCreateDBMessageBean02()
     * <br><br>
     * 
     *  (正常系) or (異常系)
     * <br>
     * 観点：G
     * <br><br>
     * 入力値：(引数) rs:|"test01"|<br>
     *                という内容のResultSet<br>
     *         
     * <br>
     * 期待値：(状態変化) 例外:SystemException：<br>
     *                    エラーコード："errors.db.message.bean.mapping.sql.query"<br>
     *                    ラップした例外：null<br>
     *         (状態変化) ログ:【エラーログ】<br>
     *                    ＜メッセージ＞<br>
     *                    "SQL for DB message returns 1 column(s) result set.."<br>
     *         
     * <br>
     * ResultSetのカラム数が1の場合、エラーログを出力し、例外をスローすることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testCreateDBMessageBean02() throws Exception {
        // 前処理
        DataSource ds = null;
        String sql = null;
        DBMessageBeanMappingSqlQuery query 
            = new DBMessageBeanMappingSqlQuery(ds, sql);

        // 擬似ResultSetの設定
        MockResultSet rs = new MockResultSet("testid");
        
        List<String> list1 = new ArrayList<String>();
        list1.add("test01");
        rs.addColumn(list1);
        
        rs.first();

        // テスト実施
        try {
            query.createDBMessageBean(rs);
            fail();
        } catch (SystemException e) {
            // 判定
            assertEquals("errors.db.message.bean.mapping.sql.query",
                e.getErrorCode());
            assertTrue(LogUTUtil.checkError(
                "SQL for DB message returns 1 column(s) result set."));
        }
    }

    /**
     * testCreateDBMessageBean03()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) rs:|"test01"|"テストメッセージ０１"|<br>
     *                という内容のResultSet<br>
     *         
     * <br>
     * 期待値：(戻り値) DBMessageBean:key->"test01"<br>
     *                  value->"テストメッセージ０１"<br>
     *         
     * <br>
     * カラム数が2のとき、key, valueともに正常に設定されたDBMessageBeanが返されることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testCreateDBMessageBean03() throws Exception {
        // 前処理
        DataSource ds = null;
        String sql = null;
        DBMessageBeanMappingSqlQuery query 
            = new DBMessageBeanMappingSqlQuery(ds, sql);
        
        // 擬似ResultSetの設定
        MockResultSet rs = new MockResultSet("testid");
        
        List<String> list1 = new ArrayList<String>();
        list1.add("test01");
        rs.addColumn(list1);

        List<String> list2 = new ArrayList<String>();
        list2.add("テストメッセージ０１");
        rs.addColumn(list2);
        
        rs.first();

        // テスト実施
        DBMessageBean actual = query.createDBMessageBean(rs);
        
        // 判定
        assertEquals("test01", 
                     UTUtil.getPrivateField(actual, "key"));
        assertEquals("テストメッセージ０１",
                     UTUtil.getPrivateField(actual, "value"));
    }

    /**
     * testCreateDBMessageBean04()
     * <br><br>
     * 
     * (異常系)
     * <br>
     * 観点：G
     * <br><br>
     * 入力値：(引数) rs:|"test01"|"テストメッセージ０１"|"ja"|<br>
     *                という内容のResultSet<br>
     *         
     * <br>
     * 期待値：(状態変化) 例外:SystemException：<br>
     *                    エラーコード："errors.db.message.bean.mapping.sql.query"<br>
     *                    ラップした例外：null<br>
     *         (状態変化) ログ:【エラーログ】<br>
     *                    ＜メッセージ＞<br>
     *                    "SQL for DB message returns 3 column(s) result set."<br>
     *         
     * <br>
     * ResultSetのカラム数が3の場合、エラーログを出力し、例外をスローすることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testCreateDBMessageBean04() throws Exception {
        // 前処理
        DataSource ds = null;
        String sql = null;
        DBMessageBeanMappingSqlQuery query 
            = new DBMessageBeanMappingSqlQuery(ds, sql);

        // 擬似ResultSetの設定
        MockResultSet rs = new MockResultSet("testid");
        
        List<String> list1 = new ArrayList<String>();
        list1.add("test01");
        rs.addColumn(list1);

        List<String> list2 = new ArrayList<String>();
        list2.add("テストメッセージ０１");
        rs.addColumn(list2);
        
        List<String> list3 = new ArrayList<String>();
        list3.add("ja");
        rs.addColumn(list3);
        
        rs.first();

        // テスト実施
        try {
            query.createDBMessageBean(rs);
            fail();
        } catch (SystemException e) {
            // 判定
            assertEquals("errors.db.message.bean.mapping.sql.query",
                e.getErrorCode());
            assertTrue(LogUTUtil.checkError(
                "SQL for DB message returns 3 column(s) result set."));
        }
    }

    /**
     * testCreateDBMessageBean05()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) rs:|null|null|<br>
     *                という内容のResultSet<br>
     *         
     * <br>
     * 期待値：(戻り値) DBMessageBean:key->""<br>
     *                  value->""<br>
     *         (状態変化) ログ:【警告ログ】<br>
     *                    ＜メッセージ＞<br>
     *                    "DBMessage resource keys contain null or empty."<br>
     *         
     * <br>
     * ResultSetの第一カラムがnullで、かつ第二カラムもnullの場合、
     * 警告ログを出力し、key, valueともに空文字列の設定された
     * DBMessageBeanが返されることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testCreateDBMessageBean05() throws Exception {
        // 前処理
        DataSource ds = null;
        String sql = null;
        DBMessageBeanMappingSqlQuery query 
            = new DBMessageBeanMappingSqlQuery(ds, sql);

        // 擬似ResultSetの設定
        MockResultSet rs = new MockResultSet("testid");
        
        List<String> list1 = new ArrayList<String>();
        list1.add(null);
        rs.addColumn(list1);

        List<String> list2 = new ArrayList<String>();
        list2.add(null);
        rs.addColumn(list2);
        
        rs.first();

        // テスト実施
        DBMessageBean actual = query.createDBMessageBean(rs);
        
        // 判定
        assertEquals("", 
            UTUtil.getPrivateField(actual, "key"));
        assertEquals("",
            UTUtil.getPrivateField(actual, "value"));
        assertTrue(LogUTUtil.checkWarn(
            "DBMessage resource keys contain null or empty."));
    }

    /**
     * testCreateDBMessageBean06()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) rs:|""|""|<br>
     *                という内容のResultSet<br>
     *         
     * <br>
     * 期待値：(戻り値) DBMessageBean:key->""<br>
     *                  value->""<br>
     *         (状態変化) ログ:【警告ログ】<br>
     *                    ＜メッセージ＞<br>
     *                    "DBMessage resource keys contain null or empty."<br>
     *         
     * <br>
     * ResultSetの第一カラムが空文字列で、かつ第二カラムも空文字列の場合、
     * 警告ログを出力し、key, valueともに空文字列の設定されたDBMessageBeanが
     * 返されることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testCreateDBMessageBean06() throws Exception {
        // 前処理
        DataSource ds = null;
        String sql = null;
        DBMessageBeanMappingSqlQuery query 
            = new DBMessageBeanMappingSqlQuery(ds, sql);

        // 擬似ResultSetの設定
        MockResultSet rs = new MockResultSet("testid");
        
        List<String> list1 = new ArrayList<String>();
        list1.add("");
        rs.addColumn(list1);

        List<String> list2 = new ArrayList<String>();
        list2.add("");
        rs.addColumn(list2);
        
        rs.first();

        // テスト実施
        DBMessageBean actual = query.createDBMessageBean(rs);
        
        // 判定
        assertEquals("", 
            UTUtil.getPrivateField(actual, "key"));
        assertEquals("",
            UTUtil.getPrivateField(actual, "value"));
        assertTrue(LogUTUtil.checkWarn(
            "DBMessage resource keys contain null or empty."));
    }

}
