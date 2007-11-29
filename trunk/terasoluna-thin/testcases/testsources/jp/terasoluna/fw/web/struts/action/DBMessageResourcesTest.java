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

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import jp.terasoluna.fw.exception.SystemException;
import jp.terasoluna.fw.util.ClassLoadException;
import jp.terasoluna.utlib.LogUTUtil;
import jp.terasoluna.utlib.PropertyTestCase;
import jp.terasoluna.utlib.UTUtil;

/**
 * {@link jp.terasoluna.fw.web.struts.action.DBMessageResources} 
 * クラスのブラックボックステスト。
 * 
 * <p>
 * <h4>【クラスの概要】</h4>
 * メッセージリソースクラス。<br>
 * このクラスを使用することによって、メッセージリソース定義ファイル
 * （通常Strutsで使われるプロパティファイル形式のメッセージリソース）
 * だけでなく、クラスロード時にDBを参照し、DBからのメッセージリソースを
 * 使用することが可能である。
 * <p>
 * 
 * @see jp.terasoluna.fw.web.struts.action.DBMessageResources
 */
public class DBMessageResourcesTest extends PropertyTestCase {

    /**
     * このテストケースを実行する為の
     * GUI アプリケーションを起動する。
     * 
     * @param args java コマンドに設定されたパラメータ
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(DBMessageResourcesTest.class);
    }

    /**
     * 初期化処理を行う。
     * 
     * @throws Exception このメソッドで発生した例外
     * @see jp.terasoluna.utlib.spring.PropertyTestCase#setUpData()
     */
    @Override
    protected void setUpData() throws Exception {
        UTUtil.setPrivateField(DBMessageResources.class, "dbMessages", null);
    }

    /**
     * 終了処理を行う。
     * 
     * @throws Exception このメソッドで発生した例外
     * @see jp.terasoluna.utlib.spring.PropertyTestCase#cleanUpData()
     */
    @Override
    protected void cleanUpData() throws Exception {
        UTUtil.setPrivateField(DBMessageResources.class, "dbMessages", null);
    }

    /**
     * コンストラクタ。
     * 
     * @param name このテストケースの名前。
     */
    public DBMessageResourcesTest(String name) {
        super(name);
    }

    /**
     * testDbInit01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(状態) messages.sql:"messages.sql"が存在しない<br>
     *         (状態) messages.dao:"messages.dao"が存在しない<br>
     *         
     * <br>
     * 期待値：(状態変化) dbMessages:空のMap<br>
     *         
     * <br>
     * メッセージリソース定義ファイルにキー"messages.sql"も"messages.dao"も
     * 定義されない場合、dbMessagesに空のMapが設定されることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testDbInit01() throws Exception {
        // 前処理
        deleteProperty("messages.sql");
        deleteProperty("messages.dao");
        
        // テスト実施
        DBMessageResources.dbInit();

        // 判定
        Map map = (Map) UTUtil.getPrivateField(DBMessageResources.class,
                                               "dbMessages");
        assertTrue(map.isEmpty());
    }

    /**
     * testDbInit02()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(状態) messages.sql:"messages.sql"が存在しない<br>
     *         (状態) messages.dao:"jp.terasoluna.fw.web.action.MessageResources_MessageResourcesDAOStub01"<br>
     *         
     * <br>
     * 期待値：(状態変化) ログ:【警告ログ】<br>
     *                    ＜メッセージ＞<br>
     *                    "defined only one of the pair - messages.dao and messages.sql."<br>
     *         (状態変化) dbMessages:空のMap<br>
     *         
     * <br>
     * メッセージリソース定義ファイルにキー"messages.sql"が存在しない場合、
     * 警告ログを出し、dbMessagesに空のMapが設定されることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testDbInit02() throws Exception {
        // 前処理
        deleteProperty("messages.sql");
        addProperty("messages.dao", 
                    DBMessageResources_MessageResourcesDAOStub01.class.getName());        
        // テスト実施
        DBMessageResources.dbInit();

        // 判定
        assertTrue(LogUTUtil.checkWarn(
            "defined only one of the pair - messages.dao and messages.sql."));
        Map map = (Map) UTUtil.getPrivateField(DBMessageResources.class,
                                               "dbMessages");
        assertTrue(map.isEmpty());
    }

    /**
     * testDbInit03()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(状態) messages.sql:"SELECT MESSAGE_KEY, MESSAGE_VALUE FROM MESSAGE"<br>
     *         (状態) messages.dao:"messages.dao"が存在しない<br>
     *         
     * <br>
     * 期待値：(状態変化) ログ:【警告ログ】<br>
     *                    ＜メッセージ＞<br>
     *                    "defined only one of the pair - messages.dao and messages.sql."<br>
     *         (状態変化) dbMessages:空のMap<br>
     *         
     * <br>
     * メッセージリソース定義ファイルにキー"messages.dao"が存在しない場合、
     * 警告ログを出し、dbMessagesに空のMapが設定されることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testDbInit03() throws Exception {
        // 前処理
        addProperty("messages.sql",
                    "SELECT MESSAGE_KEY, MESSAGE_VALUE FROM MESSAGE");
        deleteProperty("messages.dao");
        
        // テスト実施
        DBMessageResources.dbInit();

        // 判定
        assertTrue(LogUTUtil.checkWarn(
            "defined only one of the pair - messages.dao and messages.sql."));
        Map map = (Map) UTUtil.getPrivateField(DBMessageResources.class,
                                               "dbMessages");
        assertTrue(map.isEmpty());
    }

    /**
     * testDbInit04()
     * <br><br>
     * 
     * (異常系)
     * <br>
     * 観点：G
     * <br><br>
     * 入力値：(状態) messages.sql:"SELECT MESSAGE_KEY, MESSAGE_VALUE FROM MESSAGE"<br>
     *         (状態) messages.dao:""<br>
     *         
     * <br>
     * 期待値：(状態変化) 例外:SystemException：<br>
     *                    エラーコード："errors.db.message.resources.init"<br>
     *                    ラップした例外：ClassLoadException<br>
     *         (状態変化) ログ:【エラーログ】<br>
     *                    ＜メッセージ＞<br>
     *                    "" cannot loaded."<br>
     *                    ＜例外＞<br>
     *                    ClassLoadException<br>
     *         (状態変化) dbMessages:空のMap<br>
     *         
     * <br>
     * メッセージリソース定義ファイルにキー"messages.dao"で登録されている値が
     * 空の場合、dbmessagesには空のMapが設定され、エラーログを出力し、
     * 例外がスローされることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testDbInit04() throws Exception {
        // 前処理
        addProperty("messages.sql",
                    "SELECT MESSAGE_KEY, MESSAGE_VALUE FROM MESSAGE");
        addProperty("messages.dao", "");
        
        // テスト実施
        try {
            DBMessageResources.dbInit();
            fail();
        } catch (SystemException e) {
            // 判定
            assertTrue(e.getCause() instanceof ClassLoadException);
            assertEquals("errors.db.message.resources.init",
                         e.getErrorCode());
            assertTrue(LogUTUtil.checkError("\"\" cannot loaded.", e.getCause()));
            Map map = (Map) UTUtil.getPrivateField(DBMessageResources.class,
                                                   "dbMessages");
            assertTrue(map.isEmpty());
        }
        
    }

    /**
     * testDbInit05()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(状態) messages.sql:"SELECT MESSAGE_KEY, MESSAGE_VALUE FROM MESSAGE"<br>
     *         (状態) messages.dao:"aaaaa"<br>
     *         
     * <br>
     * 期待値：(状態変化) 例外:SystemException：<br>
     *                    エラーコード："errors.db.message.resources.init"<br>
     *                    ラップした例外：ClassLoadException<br>
     *         (状態変化) ログ:【エラーログ】<br>
     *                    ＜メッセージ＞<br>
     *                    ""aaaaa" cannot loaded."<br>
     *                    ＜例外＞<br>
     *                    ClassLoadException<br>
     *         (状態変化) dbMessages:空のMap<br>
     *         
     * <br>
     * メッセージリソース定義ファイルにキー"messages.dao"で登録されている値が
     * 存在しないクラスの場合、dbmessagesには空のMapが設定され、エラーログを
     * 出力し、例外がスローされることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testDbInit05() throws Exception {
        // 前処理
        addProperty("messages.sql",
                    "SELECT MESSAGE_KEY, MESSAGE_VALUE FROM MESSAGE");
        addProperty("messages.dao",
                    "aaaaa");
        
        // テスト実施
        try {
            DBMessageResources.dbInit();
            fail();
        } catch (SystemException e) {
            // 判定
            assertTrue(e.getCause() instanceof ClassLoadException);
            assertEquals("errors.db.message.resources.init",
                         e.getErrorCode());
            assertTrue(LogUTUtil.checkError("\"aaaaa\" cannot loaded.", e.getCause()));
            Map map = (Map) UTUtil.getPrivateField(DBMessageResources.class,
                                                   "dbMessages");
            assertTrue(map.isEmpty());
        }
        
    }

    /**
     * testDbInit06()
     * <br><br>
     * 
     * (異常系)
     * <br>
     * 観点：G
     * <br><br>
     * 入力値：(状態) messages.sql:"SELECT MESSAGE_KEY, MESSAGE_VALUE FROM MESSAGE"<br>
     *         (状態) messages.dao:"java.lang.Object"<br>
     *         
     * <br>
     * 期待値：(状態変化) 例外:SystemException：<br>
     *                    エラーコード："errors.db.message.resources.init"<br>
     *                    ラップした例外：ClassCastException<br>
     *         (状態変化) ログ:【エラーログ】<br>
     *                    ＜メッセージ＞<br>
     *                    ""java.lang.Object" not implemented MessageResourcesDAO"<br>
     *                    ＜例外＞<br>
     *                    ClassCastException<br>
     *         (状態変化) dbMessages:空のMap<br>
     *         
     * <br>
     * メッセージリソース定義ファイルにキー"messages.dao"で登録されている
     * クラスがMessageResourcesDAOを実装していない場合、dbmessagesには
     * 空のMapが設定され、エラーログを出力し、例外がスローされることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testDbInit06() throws Exception {
        // 前処理
        addProperty("messages.sql",
                    "SELECT MESSAGE_KEY, MESSAGE_VALUE FROM MESSAGE");
        addProperty("messages.dao", "java.lang.Object");
        UTUtil.setPrivateField(DBMessageResources.class, 
                               "dbMessages",
                               new HashMap<String, String>());
        
        // テスト実施
        try {
            DBMessageResources.dbInit();
            fail();
        } catch (SystemException e) {
            // 判定
            assertEquals(ClassCastException.class.getName(),
                         e.getCause().getClass().getName());
            assertEquals("errors.db.message.resources.init",
                         e.getErrorCode());
            assertTrue(LogUTUtil.checkError(
                "\"java.lang.Object\" not implemented MessageResourcesDAO", 
                e.getCause()));
            Map map = (Map) UTUtil.getPrivateField(DBMessageResources.class,
                                                   "dbMessages");
            assertTrue(map.isEmpty());
        }
    }

    /**
     * testDbInit07()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(状態) messages.sql:"SELECT MESSAGE_KEY, MESSAGE_VALUE FROM MESSAGE"<br>
     *         (状態) messages.dao:"jp.terasoluna.fw.web.struts.action.DBMessageResources_MessageResourcesDAOStub01"<br>
     *         (状態) queryMessageMap:["TEST01"->"テストめっせーじ０１"]<br>
     *                ["TEST01"->"testMESSAGE02"]<br>
     *                ["TEST01"->"試験的文零三"]<br>
     *         
     * <br>
     * 期待値：(状態変化) dbMessages:["TEST01"->"テストめっせーじ０１"]<br>
     *                    ["TEST02"->"testMESSAGE02"]<br>
     *                    ["TEST03"->"試験的文零三"]<br>
     *         
     * <br>
     * メッセージリソース定義ファイルに"messages.dao"と"messages.sql"が
     * 正しく定義されている場合、DBから取得したメッセージキーとメッセージ文言が
     * 正しく設定されることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testDbInit07() throws Exception {
        // 前処理
        addProperty("messages.sql",
                    "SELECT MESSAGE_KEY, MESSAGE_VALUE FROM MESSAGE");
        addProperty("messages.dao", 
                    DBMessageResources_MessageResourcesDAOStub01.class.getName());

        // テスト実施
        DBMessageResources.dbInit();
        
        // 判定
        Map map = (Map) UTUtil.getPrivateField(DBMessageResources.class,
                                               "dbMessages");
        assertEquals(3, map.size());
        assertEquals("テストめっせーじ０１", map.get("TEST01"));
        assertEquals("testMESSAGE02", map.get("TEST02"));
        assertEquals("試験的文零三", map.get("TEST03"));
    }

    /**
     * testPropertyInit01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) propertyFile:null<br>
     *         
     * <br>
     * 期待値：(状態変化) ログ:【エラーログ】<br>
     *                    ＜メッセージ＞<br>
     *                    "Message resources file "null" is illegal."<br>
     *         (状態変化) messages:空のMap<br>
     *         
     * <br>
     * メッセージリソース定義ファイル名がnullのとき、メッセージを取得せずに
     * エラーログを出力して終了することを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testPropertyInit01() throws Exception {
        // 前処理、テスト実施
        DBMessageResources resources 
            = new DBMessageResources(null, null);

        // 判定
        assertTrue(LogUTUtil.checkError(
                "Message resources file \"null\" is illegal."));
        Map map = (Map) UTUtil.getPrivateField(resources, "messages");
        assertTrue(map.isEmpty());
    }

    /**
     * testPropertyInit02()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) propertyFile:""<br>
     *         
     * <br>
     * 期待値：(状態変化) ログ:【エラーログ】<br>
     *                    ＜メッセージ＞<br>
     *                    "Message resources file "" is illegal."<br>
     *         (状態変化) messages:空のMap<br>
     *         
     * <br>
     * メッセージリソース定義ファイル名が空文字列のとき、メッセージを
     * 取得せずにエラーログを出力して終了することを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testPropertyInit02() throws Exception {
        // 前処理
        DBMessageResources resources 
            = new DBMessageResources(null, "");
        
        // 判定
        assertTrue(LogUTUtil.checkError("Message resources file \"\" is illegal."));
        Map map = (Map) UTUtil.getPrivateField(resources, "messages");
        assertTrue(map.isEmpty());
    }

    /**
     * testPropertyInit03()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) propertyFile:"aaaaa"<br>
     *         
     * <br>
     * 期待値：(状態変化) ログ:【エラーログ】<br>
     *                    "Message resources file "aaaaa" is illegal."<br>
     *         (状態変化) messages:空のMap<br>
     *         
     * <br>
     * メッセージリソース定義ファイルが存在しないとき、メッセージを取得せずに
     * エラーログを出力して終了することを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testPropertyInit03() throws Exception {
        // 前処理
        DBMessageResources resources 
            = new DBMessageResources(null, "aaaaa");
        
        // 判定
        assertTrue(LogUTUtil.checkError(
                "Message resources file \"aaaaa\" is illegal."));
        Map map = (Map) UTUtil.getPrivateField(resources, "messages");
        assertTrue(map.isEmpty());
    }

    /**
     * testPropertyInit04()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) propertyFile:"DBMessageResources_MessageResources01"<br>
     *                （空のファイル）<br>
     *         
     * <br>
     * 期待値：(状態変化) messages:空のMap<br>
     *         
     * <br>
     * メッセージリソース定義ファイルの中身が空の場合、メッセージを取得せずに
     * 終了することを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testPropertyInit04() throws Exception {
        // 前処理
        DBMessageResources resources 
            = new DBMessageResources(
                null, this.getClass().getPackage().getName().replace('.', '/')
                + "/DBMessageResources_MessageResources01");
        
        // 判定
        Map map = (Map) UTUtil.getPrivateField(resources, "messages");
        assertTrue(map.isEmpty());
    }

    /**
     * testPropertyInit05()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) propertyFile:"DBMessageResources_MessageResources02"<br>
     *                【ファイルの内容】<br>
     *                message.error.required=<br>
     *         
     * <br>
     * 期待値：(状態変化) messages:["message.error.required"->""]<br>
     *         
     * <br>
     * メッセージリソース定義ファイルから取得したキーに対する値が空文字列の
     * 場合、メッセージが空文字列で設定されることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testPropertyInit05() throws Exception {
        // 前処理
        DBMessageResources resources 
        = new DBMessageResources(
                null, this.getClass().getPackage().getName().replace('.', '/')
                + "/DBMessageResources_MessageResources02");
        // 判定
        Map map = (Map) UTUtil.getPrivateField(resources, "messages");
        assertEquals(1, map.size());
        assertEquals("", map.get("message.error.required"));
    }

    /**
     * testPropertyInit06()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) propertyFile:"DBMessageResources_MessageResources03"<br>
     *                【ファイルの内容】<br>
     *                message.error.required={0}を入力してください。<br>
     *         
     * <br>
     * 期待値：(状態変化) messages:["message.error.required"->"{0}を入力してください。"]<br>
     *         
     * <br>
     * メッセージリソース定義ファイルから取得した結果が1件の場合、
     * 正しく設定されることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testPropertyInit06() throws Exception {
        // 前処理
        DBMessageResources resources 
        = new DBMessageResources(
                null, this.getClass().getPackage().getName().replace('.', '/')
                + "/DBMessageResources_MessageResources03");
        
        // 判定
        Map map = (Map) UTUtil.getPrivateField(resources, "messages");
        assertEquals(1, map.size());
        assertEquals("{0}を入力してください。", map.get("message.error.required"));
    }

    /**
     * testPropertyInit07()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) propertyFile:"DBMessageResources_MessageResources04"<br>
     *                【ファイルの内容】<br>
     *                error.date.format={0}の日時は{1}形式で入力してください。<br>
     *                message.error.required={0}を入力してください。<br>
     *                error.prohibited={0}に入力禁止文字"{1}"が含まれています。<br>
     *         
     * <br>
     * 期待値：(状態変化) messages:["error.date.format"->"{0}の日時は{1}形式で入力してください。"]<br>
     *                    ["message.error.required"->"{0}を入力してください。"]<br>
     *                    ["error.prohibited"->"{0}に入力禁止文字"{1}"が含まれています。"]<br>
     *         
     * <br>
     * メッセージリソース定義ファイルから取得した結果が複数件の場合、
     * 正しく設定されることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testPropertyInit07() throws Exception {
        // 前処理
        DBMessageResources resources 
        = new DBMessageResources(
                null, this.getClass().getPackage().getName().replace('.', '/')
                + "/DBMessageResources_MessageResources04");
        
        // 判定
        Map map = (Map) UTUtil.getPrivateField(resources, "messages");
        assertEquals(3, map.size());
        assertEquals("{0}の日時は{1}形式で入力してください。", 
                     map.get("error.date.format"));
        assertEquals("{0}を入力してください。", 
                     map.get("message.error.required"));
        assertEquals("{0}に入力禁止文字\"{1}\"が含まれています。", 
                     map.get("error.prohibited"));
    }

    /**
     * testGetMessageLocaleString01()
     * <br><br>
     * 
     * (異常系)
     * <br>
     * 観点：G
     * <br><br>
     * 入力値：(引数) locale:locale.JAPAN<br>
     *         (引数) key:""<br>
     *         (状態) messages:[""->"テストメッセージ０１"]<br>
     *         (状態) returnNull:true<br>
     *         
     * <br>
     * 期待値：(戻り値) String:"テストメッセージ０１"<br>
     *         (状態変化) 例外:SystemException：<br>
     *                         エラーコード："errors.db.message.resources"<br>
     *                         ラップした例外：null<br>
     *         (状態変化) ログ:【エラーログ】<br>
     *                         ＜メッセージ＞<br>
     *                         "Message key 'null' or empty not allowed."<br>
     *         
     * <br>
     * keyが空文字列の場合、エラーログを出力し、例外をスローすることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetMessageLocaleString01() throws Exception {
        // 前処理
        DBMessageResources resources 
            = new DBMessageResources(null, null);
        Map<String, String> map = new HashMap<String, String>(1);
        map.put("", "テストメッセージ０１");
        UTUtil.setPrivateField(resources, "messages", map);
        UTUtil.setPrivateField(resources, "returnNull", Boolean.TRUE);
        
        // テスト実施
        try {
            resources.getMessage(Locale.JAPAN, "");
            fail();
        } catch (SystemException e) {
            // 判定
            assertNull(e.getCause());
            assertEquals("errors.db.message.resources",
                         e.getErrorCode());
            assertTrue(LogUTUtil.checkError(
                "Message key 'null' or empty not allowed."));
        }
        
    }

    /**
     * testGetMessageLocaleString02()
     * <br><br>
     * 
     * (異常系)
     * <br>
     * 観点：G
     * <br><br>
     * 入力値：(引数) locale:locale.JAPAN<br>
     *         (引数) key:""<br>
     *         (状態) messages:空のMap<br>
     *         (状態) dbMessages:[""->"ＤＢテストメッセージ０１"]<br>
     *         (状態) returnNull:true<br>
     *         
     * <br>
     * 期待値：(状態変化) 例外:SystemException：<br>
     *                         エラーコード："errors.db.message.resources"<br>
     *                         ラップした例外：null<br>
     *         (状態変化) ログ:【エラーログ】<br>
     *                         ＜メッセージ＞<br>
     *                         "Message key 'null' or empty not allowed."<br>
     *         
     * <br>
     * keyが空文字列の場合、エラーログを出力し、例外をスローすることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetMessageLocaleString02() throws Exception {
        // 前処理
        DBMessageResources resources 
            = new DBMessageResources(null, null);
        HashMap<String, String> map = new HashMap<String, String>(1);
        map.put("", "ＤＢテストメッセージ０１");
        UTUtil.setPrivateField(resources, "dbMessages", map);
        UTUtil.setPrivateField(resources, "returnNull", Boolean.TRUE);
        
        // テスト実施
        try {
            resources.getMessage(Locale.JAPAN, "");
            fail();
        } catch (SystemException e) {
            // 判定
            assertNull(e.getCause());
            assertEquals("errors.db.message.resources",
                         e.getErrorCode());
            assertTrue(LogUTUtil.checkError(
                "Message key 'null' or empty not allowed."));
        }
    }

    /**
     * testGetMessageLocaleString03()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) locale:locale.JAPAN<br>
     *         (引数) key:"TEST01"<br>
     *         (状態) messages:["TEST01"->""]<br>
     *         (状態) returnNull:true<br>
     *         
     * <br>
     * 期待値：(戻り値) String:""<br>
     *         
     * <br>
     * messagesに登録されているキーとメッセージキーが一致したメッセージ文言が
     * 空文字列の場合、空文字列が返却されることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetMessageLocaleString03() throws Exception {
        // 前処理
        DBMessageResources resources 
            = new DBMessageResources(null, null);
        Map<String, String> map = new HashMap<String, String>(1);
        map.put("TEST01", "");
        UTUtil.setPrivateField(resources, "messages", map);
        UTUtil.setPrivateField(resources, "returnNull", Boolean.TRUE);
        
        // テスト実施
        String actual = resources.getMessage(Locale.JAPAN, "TEST01");

        // 判定
        assertEquals("", actual);
    }

    /**
     * testGetMessageLocaleString04()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) locale:locale.JAPAN<br>
     *         (引数) key:"TEST01"<br>
     *         (状態) messages:["TEST01"->"テストメッセージ０１"]<br>
     *         (状態) returnNull:true<br>
     *         
     * <br>
     * 期待値：(戻り値) String:"テストメッセージ０１"<br>
     *         
     * <br>
     * messagesにはメッセージが1件登録されていて、メッセージキーが一致する場合、
     * メッセージ文言を取得して返却することを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetMessageLocaleString04() throws Exception {
        // 前処理
        DBMessageResources resources 
            = new DBMessageResources(null, null);
        Map<String, String> map = new HashMap<String, String>(1);
        map.put("TEST01", "テストメッセージ０１");
        UTUtil.setPrivateField(resources, "messages", map);
        UTUtil.setPrivateField(resources, "returnNull", Boolean.TRUE);
        
        // テスト実施
        String actual = resources.getMessage(Locale.JAPAN, "TEST01");

        // 判定
        assertEquals("テストメッセージ０１", actual);
    }

    /**
     * testGetMessageLocaleString05()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) locale:locale.JAPAN<br>
     *         (引数) key:"TEST02"<br>
     *         (状態) messages:["TEST01"->"テストメッセージ０１"]<br>
     *                ["TEST02"->"テストメッセージ０２"]<br>
     *                ["TEST03"->"テストメッセージ０３"]<br>
     *         (状態) returnNull:true<br>
     *         
     * <br>
     * 期待値：(戻り値) String:"テストメッセージ０２"<br>
     *         
     * <br>
     * messagesにはメッセージが複数件登録されいてメッセージキーがその内の1件と
     * 一致する場合、メッセージ文言を取得して返却することを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetMessageLocaleString05() throws Exception {
        // 前処理
        DBMessageResources resources 
            = new DBMessageResources(null, null);
        Map<String, String> map = new HashMap<String, String>(1);
        map.put("TEST01", "テストメッセージ０１");
        map.put("TEST02", "テストメッセージ０２");
        map.put("TEST03", "テストメッセージ０３");
        UTUtil.setPrivateField(resources, "messages", map);
        UTUtil.setPrivateField(resources, "returnNull", Boolean.TRUE);
        
        // テスト実施
        String actual = resources.getMessage(Locale.JAPAN, "TEST02");

        // 判定
        assertEquals("テストメッセージ０２", actual);
    }

    /**
     * testGetMessageLocaleString06()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) locale:locale.JAPAN<br>
     *         (引数) key:"TEST01"<br>
     *         (状態) messages:空のMap<br>
     *         (状態) dbMessages:["TEST01"->""]<br>
     *         (状態) returnNull:true<br>
     *         
     * <br>
     * 期待値：(戻り値) String:""<br>
     *         
     * <br>
     * messagesに何も登録されておらず、dbMessagesに登録されているキーと
     * メッセージキーが一致したメッセージ文言が空文字列の場合、空文字列が
     * 返却されることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetMessageLocaleString06() throws Exception {
        // 前処理
        DBMessageResources resources 
            = new DBMessageResources(null, null);
        Map<String, String> map = new HashMap<String, String>();
        Map<String, String> dbmap = new HashMap<String, String>(1);
        dbmap.put("TEST01", "");
        UTUtil.setPrivateField(resources, "messages", map);
        UTUtil.setPrivateField(resources, "dbMessages", dbmap);
        UTUtil.setPrivateField(resources, "returnNull", Boolean.TRUE);
        
        // テスト実施
        String actual = resources.getMessage(Locale.JAPAN, "TEST01");

        // 判定
        assertEquals("", actual);
    }

    /**
     * testGetMessageLocaleString07()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) locale:locale.JAPAN<br>
     *         (引数) key:"TEST01"<br>
     *         (状態) messages:空のMap<br>
     *         (状態) dbMessages:["TEST01"->"ＤＢテストメッセージ０１"]<br>
     *         (状態) returnNull:true<br>
     *         
     * <br>
     * 期待値：(戻り値) String:"ＤＢテストメッセージ０１"<br>
     *         
     * <br>
     * messagesが何も登録されておらず、dbMessagesにメッセージが
     * 1件登録されいてメッセージキーが一致する場合、メッセージ文言を
     * 取得して返却することを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetMessageLocaleString07() throws Exception {
        // 前処理
        DBMessageResources resources 
            = new DBMessageResources(null, null);
        Map<String, String> map = new HashMap<String, String>();
        Map<String, String> dbmap = new HashMap<String, String>(1);
        dbmap.put("TEST01", "ＤＢテストメッセージ０１");
        UTUtil.setPrivateField(resources, "messages", map);
        UTUtil.setPrivateField(resources, "dbMessages", dbmap);
        UTUtil.setPrivateField(resources, "returnNull", Boolean.TRUE);
        
        // テスト実施
        String actual = resources.getMessage(Locale.JAPAN, "TEST01");

        // 判定
        assertEquals("ＤＢテストメッセージ０１", actual);
    }

    /**
     * testGetMessageLocaleString08()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) locale:locale.JAPAN<br>
     *         (引数) key:"TEST02"<br>
     *         (状態) messages:空のMap<br>
     *         (状態) dbMessages:["TEST01"->"ＤＢテストメッセージ０１"]<br>
     *                ["TEST02"->"ＤＢテストメッセージ０２"]<br>
     *                ["TEST03"->"ＤＢテストメッセージ０３"]<br>
     *         (状態) returnNull:true<br>
     *         
     * <br>
     * 期待値：(戻り値) String:"ＤＢテストメッセージ０２"<br>
     *         
     * <br>
     * messagesが何も登録されておらず、dbMessagesにメッセージが
     * 複数件登録されいてメッセージキーがその内の1件と一致する場合、
     * メッセージ文言を取得して返却することを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetMessageLocaleString08() throws Exception {
        // 前処理
        DBMessageResources resources 
            = new DBMessageResources(null, null);
        Map<String, String> map = new HashMap<String, String>();
        Map<String, String> dbmap = new HashMap<String, String>(1);
        dbmap.put("TEST01", "ＤＢテストメッセージ０１");
        dbmap.put("TEST02", "ＤＢテストメッセージ０２");
        dbmap.put("TEST03", "ＤＢテストメッセージ０３");
        UTUtil.setPrivateField(resources, "messages", map);
        UTUtil.setPrivateField(resources, "dbMessages", dbmap);
        UTUtil.setPrivateField(resources, "returnNull", Boolean.TRUE);
        
        // テスト実施
        String actual = resources.getMessage(Locale.JAPAN, "TEST02");

        // 判定
        assertEquals("ＤＢテストメッセージ０２", actual);
    }

    /**
     * testGetMessageLocaleString09()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) locale:locale.JAPAN<br>
     *         (引数) key:"TEST01"<br>
     *         (状態) messages:["TEST01"->"テストメッセージ０１"]<br>
     *         (状態) dbMessages:["TEST01"->"ＤＢテストメッセージ０１"]<br>
     *         (状態) returnNull:true<br>
     *         
     * <br>
     * 期待値：(戻り値) String:"テストメッセージ０１"<br>
     *         
     * <br>
     * メッセージキーがdbMessagesとmessagesに存在する場合、messagesから
     * メッセージ文言を取得して返却することを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetMessageLocaleString09() throws Exception {
        // 前処理
        DBMessageResources resources 
            = new DBMessageResources(null, null);
        Map<String, String> map = new HashMap<String, String>();
        map.put("TEST01", "テストメッセージ０１");
        Map<String, String> dbmap = new HashMap<String, String>(1);
        dbmap.put("TEST01", "ＤＢテストメッセージ０１");
        UTUtil.setPrivateField(resources, "messages", map);
        UTUtil.setPrivateField(resources, "dbMessages", dbmap);
        UTUtil.setPrivateField(resources, "returnNull", Boolean.TRUE);
        
        // テスト実施
        String actual = resources.getMessage(Locale.JAPAN, "TEST01");

        // 判定
        assertEquals("テストメッセージ０１", actual);
    }

    /**
     * testGetMessageLocaleString10()
     * <br><br>
     * 
     * T(異常系)
     * <br>
     * 観点：G
     * <br><br>
     * 入力値：(引数) locale:locale.JAPAN<br>
     *         (引数) key:null<br>
     *         (状態) messages:空のMap<br>
     *         (状態) dbMessages:空のMap<br>
     *         (状態) returnNull:true<br>
     *         
     * <br>
     * 期待値：(状態変化) 例外:SystemException：<br>
     *                    エラーコード："errors.db.message.resources"<br>
     *                    ラップした例外：null<br>
     *         (状態変化) ログ:【エラーログ】<br>
     *                    ＜メッセージ＞<br>
     *                    "Message key 'null' or empty not allowed."<br>
     *         
     * <br>
     * keyがnullの場合、エラーログを出力し、例外をスローすることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetMessageLocaleString10() throws Exception {
        // 前処理
        DBMessageResources resources 
            = new DBMessageResources(null, null);
        Map<String, String> map = new HashMap<String, String>();
        Map<String, String> dbmap = new HashMap<String, String>();
        UTUtil.setPrivateField(resources, "messages", map);
        UTUtil.setPrivateField(resources, "dbMessages", dbmap);
        UTUtil.setPrivateField(resources, "returnNull", Boolean.TRUE);
        
        // テスト実施
        try {
            resources.getMessage(Locale.JAPAN, null);
            fail();
        } catch (SystemException e) {
            // 判定
            assertNull(e.getCause());
            assertEquals("errors.db.message.resources",
                         e.getErrorCode());
            assertTrue(LogUTUtil.checkError(
                "Message key 'null' or empty not allowed."));
        }
    }

    /**
     * testGetMessageLocaleString11()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) locale:locale.JAPAN<br>
     *         (引数) key:"TEST01"<br>
     *         (状態) messages:["TEST01"->null]<br>
     *         (状態) dbMessages:["TEST01"->null]<br>
     *         (状態) fwMessage(GlobalMessageResources):["TEST01"->"ＦＷテストメッセージ０１"]<br>
     *         (状態) returnNull:true<br>
     *         
     * <br>
     * 期待値：(戻り値) String:"ＦＷテストメッセージ０１"<br>
     *         
     * <br>
     * messages、dbMessagesに該当するメッセージキーが登録されておらず、
     * フレームワークのメッセージリソースのメッセージキーと一致する場合、
     * フレームワークからのメッセージが取得できることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetMessageLocaleString11() throws Exception {
        // 前処理
        DBMessageResources resources 
            = new DBMessageResources(null, null);
        Map<String, String> map = new HashMap<String, String>();
        map.put("TEST01", null);
        Map<String, String> dbmap = new HashMap<String, String>(1);
        dbmap.put("TEST01", null);
        UTUtil.setPrivateField(resources, "messages", map);
        UTUtil.setPrivateField(resources, "dbMessages", dbmap);
        UTUtil.setPrivateField(resources, "returnNull", Boolean.TRUE);
        
        // GlobalMessageResources内のメッセージリソースをバックアップ用に退避させる
        Object tempFwMap 
            = UTUtil.getPrivateField(GlobalMessageResources.getInstance(), 
                                     "fwMessages");
        Map<String, String> newFwMap
            = new HashMap<String, String>();
        newFwMap.put("TEST01", "ＦＷテストメッセージ０１");
        UTUtil.setPrivateField(GlobalMessageResources.getInstance(), 
                               "fwMessages", 
                               newFwMap);
        
        Object tempGlobalMap 
            = UTUtil.getPrivateField(GlobalMessageResources.getInstance(), 
                                     "globalMessages");
        UTUtil.setPrivateField(GlobalMessageResources.getInstance(), 
                               "globalMessages", 
                               new HashMap());

        // テスト実施
        String actual = resources.getMessage(Locale.JAPAN, "TEST01");

        // 判定
        assertEquals("ＦＷテストメッセージ０１", actual);
        
        // 元に戻す
        UTUtil.setPrivateField(GlobalMessageResources.getInstance(), 
                               "fwMessages", 
                               tempFwMap);
        UTUtil.setPrivateField(GlobalMessageResources.getInstance(), 
                               "globalMessages", 
                               tempGlobalMap);
    }

    /**
     * testGetMessageLocaleString12()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) locale:locale.JAPAN<br>
     *         (引数) key:"TEST01"<br>
     *         (状態) messages:空のMap<br>
     *         (状態) dbMessages:null<br>
     *         (状態) fwMessage(GlobalMessageResources):TEST01をキーとするエントリはない<br>
     *         (状態) returnNull:true<br>
     *         
     * <br>
     * 期待値：(戻り値) String:null<br>
     *         
     * <br>
     * dbMessagesがnullで、messagesが空のMapで、さらにシステムのメッセージリソースにも登録されていない場合、nullを返却することを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetMessageLocaleString12() throws Exception {
        // 前処理
        DBMessageResources resources 
            = new DBMessageResources(null, null);
        Map<String, String> map = new HashMap<String, String>();
        UTUtil.setPrivateField(resources, "messages", map);
        UTUtil.setPrivateField(resources, "dbMessages", null);
        UTUtil.setPrivateField(resources, "returnNull", Boolean.TRUE);
        
        // GlobalMessageResources内のメッセージリソースをバックアップ用に退避させる
        Object tempFwMap 
            = UTUtil.getPrivateField(GlobalMessageResources.getInstance(), 
                                     "fwMessages");        
        Object tempGlobalMap 
            = UTUtil.getPrivateField(GlobalMessageResources.getInstance(), 
                                     "globalMessages");
        UTUtil.setPrivateField(GlobalMessageResources.getInstance(), 
                               "fwMessages",
                               new HashMap());
        UTUtil.setPrivateField(GlobalMessageResources.getInstance(), 
                               "globalMessages", 
                               new HashMap());

        // テスト実施
        String actual = resources.getMessage(Locale.JAPAN, "TEST01");

        // 判定
        assertNull(actual);
        
        // 元に戻す
        UTUtil.setPrivateField(GlobalMessageResources.getInstance(), 
                               "fwMessages",
                               tempFwMap);
        UTUtil.setPrivateField(GlobalMessageResources.getInstance(), 
                               "globalMessages", 
                               tempGlobalMap);

    }

    /**
     * testGetMessageLocaleString13()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) locale:locale.JAPAN<br>
     *         (引数) key:"TEST01"<br>
     *         (状態) messages:null<br>
     *         (状態) dbMessages:null<br>
     *         (状態) fwMessage(GlobalMessageResources):TEST01をキーとするエントリはない<br>
     *         (状態) returnNull:false<br>
     *         
     * <br>
     * 期待値：(戻り値) String:"???ja_JP.TEST01???"<br>
     *         
     * <br>
     * dbMessagesがnullで、messagesがnullで、さらにシステムのメッセージリソースにも登録されていない場合、returnNullがfalseならば???Locale.key???形式で返却することを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetMessageLocaleString13() throws Exception {
        // 前処理
        DBMessageResources resources 
            = new DBMessageResources(null, null);
        UTUtil.setPrivateField(resources, "messages", null);
        UTUtil.setPrivateField(resources, "dbMessages", null);
        UTUtil.setPrivateField(resources, "returnNull", Boolean.FALSE);
        
        // GlobalMessageResources内のメッセージリソースをバックアップ用に退避させる
        Object tempFwMap 
            = UTUtil.getPrivateField(GlobalMessageResources.getInstance(), 
                                     "fwMessages");        
        Object tempGlobalMap 
            = UTUtil.getPrivateField(GlobalMessageResources.getInstance(), 
                                     "globalMessages");
        UTUtil.setPrivateField(GlobalMessageResources.getInstance(), 
                               "fwMessages", 
                               new HashMap());
        UTUtil.setPrivateField(GlobalMessageResources.getInstance(), 
                               "globalMessages", 
                               new HashMap());

        // テスト実施
        String actual = resources.getMessage(Locale.JAPAN, "TEST01");

        // 判定
        assertEquals("???ja_JP.TEST01???", actual);
        
        // 元に戻す
        UTUtil.setPrivateField(GlobalMessageResources.getInstance(), 
                               "fwMessages", 
                               tempFwMap);
        UTUtil.setPrivateField(GlobalMessageResources.getInstance(), 
                               "globalMessages", 
                               tempGlobalMap);

    }

    /**
     * testGetMessageLocaleString14()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) locale:null<br>
     *         (引数) key:"TEST01"<br>
     *         (状態) messages:空のMap<br>
     *         (状態) dbMessages:null<br>
     *         (状態) fwMessage(GlobalMessageResources):TEST01をキーとするエントリはない<br>
     *         (状態) returnNull:false<br>
     *         
     * <br>
     * 期待値：(戻り値) String:"???.TEST01???"<br>
     *         
     * <br>
     * dbMessagesがnullで、messagesが空のMapで、さらにシステムの
     * メッセージリソースにも登録されていない場合、returnNullがfalseならば
     * ロケールがnullのときに???.key???形式で返却することを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetMessageLocaleString14() throws Exception {
        // 前処理
        DBMessageResources resources 
            = new DBMessageResources(null, null);
        Map<String, String> map = new HashMap<String, String>();
        UTUtil.setPrivateField(resources, "messages", map);
        UTUtil.setPrivateField(resources, "dbMessages", null);
        UTUtil.setPrivateField(resources, "returnNull", Boolean.FALSE);
        
        // GlobalMessageResources内のメッセージリソースをバックアップ用に退避させる
        Object tempFwMap 
            = UTUtil.getPrivateField(GlobalMessageResources.getInstance(), 
                                     "fwMessages");        
        Object tempGlobalMap 
            = UTUtil.getPrivateField(GlobalMessageResources.getInstance(), 
                                     "globalMessages");
        UTUtil.setPrivateField(GlobalMessageResources.getInstance(), 
                               "fwMessages", 
                               new HashMap());
        UTUtil.setPrivateField(GlobalMessageResources.getInstance(), 
                               "globalMessages",
                               new HashMap());

        // テスト実施
        Locale locale = null;
        String actual = resources.getMessage(locale, "TEST01");

        // 判定
        assertEquals("???.TEST01???", actual);
        
        // 元に戻す
        UTUtil.setPrivateField(GlobalMessageResources.getInstance(), 
                               "fwMessages", 
                               tempFwMap);
        UTUtil.setPrivateField(GlobalMessageResources.getInstance(), 
                               "globalMessages", 
                               tempGlobalMap);

    }

    /**
     * testGetMessageString01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) key:"TEST01"<br>
     *         (状態) messages:["TEST01"->"テストメッセージ０１"]<br>
     *         
     * <br>
     * 期待値：(戻り値) String:"テストメッセージ０１"<br>
     *         
     * <br>
     * getMessage(Locale, String)が呼び出されているか確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetMessageString01() throws Exception {
        // 前処理
        DBMessageResources resources 
            = new DBMessageResources(null, null);
        Map<String, String> map = new HashMap<String, String>();
        map.put("TEST01", "テストメッセージ０１");
        UTUtil.setPrivateField(resources, "messages", map);
        
        // テスト実施
        String actual = resources.getMessage("TEST01");

        // 判定
        assertEquals("テストメッセージ０１", actual);
    }

    /**
     * testDBMessageResourcesMessageResourcesFactoryString01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) factory:DBMessageResourcesFactoryインスタンス<br>
     *         (引数) config:"DBMessageResources_MessageResources05"<br>
     *                       【ファイルの内容】<br>
     *                       error.date.format={0}の日時は{1}形式で入力してください。<br>
     *         (状態) dbMessages:null<br>
     *         
     * <br>
     * 期待値：(戻り値) DBMessageResources:factory->前提条件で設定したインスタンス<br>
     *                  config->"DBMessageResources_MessageResources05"<br>
     *         (状態変化) dbInit()の呼び出し:呼び出される<br>
     *         (状態変化) propertyInit()の呼び出し:引数がconfigと等しい文字列で呼び出される<br>
     *         
     * <br>
     * dbInitが呼ばれ、propertyInitが呼ばれていることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testDBMessageResourcesMessageResourcesFactoryString01() throws Exception {
        // 前処理
        DBMessageResourcesFactory factory
            = new DBMessageResourcesFactory();
        
        // テスト実施
        DBMessageResources resources
            = new DBMessageResources(factory, 
                DBMessageResources.class.getPackage().getName()
                .replace('.', '/') + "/DBMessageResources_MessageResources05");     
        
        // 判定
        assertSame(factory, UTUtil.getPrivateField(resources, "factory"));
        assertEquals(DBMessageResources.class.getPackage().getName()
            .replace('.', '/') + "/DBMessageResources_MessageResources05",
            UTUtil.getPrivateField(resources, "config"));
        
        Map actualDbMap 
            = (Map) UTUtil.getPrivateField(DBMessageResources.class, 
                                           "dbMessages");
        assertTrue(actualDbMap.isEmpty());
        
        Map actualMap = (Map) UTUtil.getPrivateField(resources, "messages");
        assertEquals(1, actualMap.size());
        assertEquals("{0}の日時は{1}形式で入力してください。",
                     actualMap.get("error.date.format"));
    }

    /**
     * testDBMessageResourcesMessageResourcesFactoryString02()
     * <br><br>
     * 
     * T(正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) factory:DBMessageResourcesFactoryインスタンス<br>
     *         (引数) config:"DBMessageResources_MessageResources05"<br>
     *                       【ファイルの内容】<br>
     *                       error.date.format={0}の日時は{1}形式で入力してください。<br>
     *         (状態) dbMessages:["TEST01"->"テストメッセージ０１"]<br>
     *         
     * <br>
     * 期待値：(戻り値) DBMessageResources:factory->前提条件で設定したインスタンス<br>
     *                  config->"DBMessageResources_MessageResources05"<br>
     *         (状態変化) dbInit()の呼び出し:呼び出されない<br>
     *         (状態変化) propertyInit()の呼び出し:引数がconfigと等しい文字列で呼び出される<br>
     *         
     * <br>
     * dbInitが呼ばれず、propertyInitが呼ばれていることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testDBMessageResourcesMessageResourcesFactoryString02() throws Exception {
        // 前処理        
        // dbInitが呼ばれないようにmapを設定する
        Map<String, String> dbMap
            = new HashMap<String, String>();
        dbMap.put("TEST01", "テストメッセージ０１");
        UTUtil.setPrivateField(
            DBMessageResources.class,
            "dbMessages",
            dbMap);
        DBMessageResourcesFactory factory
            = new DBMessageResourcesFactory();
        
        // テスト実施
        DBMessageResources resources
            = new DBMessageResources(factory, 
                DBMessageResources.class.getPackage().getName()
                .replace('.', '/') + "/DBMessageResources_MessageResources05");  
        
        // 判定
        assertSame(factory, UTUtil.getPrivateField(resources, "factory"));
        assertEquals(
            DBMessageResources.class.getPackage().getName()
            .replace('.', '/') + "/DBMessageResources_MessageResources05",
            UTUtil.getPrivateField(resources, "config"));
        
        Map actualDbMap = (Map) UTUtil.getPrivateField(resources, "dbMessages");
        assertSame(dbMap, actualDbMap);
        assertEquals(1, actualDbMap.size());
        assertEquals("テストメッセージ０１", actualDbMap.get("TEST01"));
        
        Map actualMap = (Map) UTUtil.getPrivateField(resources, "messages");
        assertEquals(1, actualMap.size());
        assertEquals("{0}の日時は{1}形式で入力してください。",
                     actualMap.get("error.date.format"));
        
    }

    /**
     * testDBMessageResourcesMessageResourcesFactoryString03()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) factory:null<br>
     *         (引数) config:"DBMessageResources_MessageResources05"<br>
     *                       【ファイルの内容】<br>
     *                       error.date.format={0}の日時は{1}形式で入力してください。<br>
     *         (状態) dbMessages:["TEST01"->"テストメッセージ０１"]<br>
     *         
     * <br>
     * 期待値：(戻り値) DBMessageResources:factory->null<br>
     *                  config->"DBMessageResources_MessageResources05"<br>
     *         (状態変化) dbInit()の呼び出し:呼び出されない<br>
     *         (状態変化) propertyInit()の呼び出し:引数がconfigと等しい文字列で呼び出される<br>
     *         
     * <br>
     * dbInitが呼ばれず、propertyInitが呼ばれていることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testDBMessageResourcesMessageResourcesFactoryString03() throws Exception {
        // 前処理        
        // dbInitが呼ばれないようにmapを設定する
        Map<String, String> dbMap
            = new HashMap<String, String>();
        dbMap.put("TEST01", "テストメッセージ０１");
        UTUtil.setPrivateField(
                DBMessageResources.class,
                "dbMessages",
                dbMap);
        
        // テスト実施
        DBMessageResources resources 
            = new DBMessageResources(null, 
                DBMessageResources.class.getPackage().getName()
                .replace('.', '/') + "/DBMessageResources_MessageResources05");

        // 判定
        assertNull(UTUtil.getPrivateField(resources, "factory"));
        assertEquals(
                DBMessageResources.class.getPackage().getName()
                .replace('.', '/') + "/DBMessageResources_MessageResources05",
                UTUtil.getPrivateField(resources, "config"));
        
        Map actualDbMap = (Map) UTUtil.getPrivateField(resources, "dbMessages");
        assertSame(dbMap, actualDbMap);
        assertEquals(1, actualDbMap.size());
        assertEquals("テストメッセージ０１", actualDbMap.get("TEST01"));
        
        Map actualMap = (Map) UTUtil.getPrivateField(resources, "messages");
        assertEquals(1, actualMap.size());
        assertEquals("{0}の日時は{1}形式で入力してください。",
                     actualMap.get("error.date.format"));
    }

    /**
     * testDBMessageResourcesMessageResourcesFactoryString04()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) factory:DBMessageResourcesFactoryインスタンス<br>
     *         (引数) config:null<br>
     *         (状態) dbMessages:["TEST01"->"テストメッセージ０１"]<br>
     *         
     * <br>
     * 期待値：(戻り値) DBMessageResources:factory->前提条件で設定したインスタンス<br>
     *                  config->null<br>
     *         (状態変化) dbInit()の呼び出し:呼び出されない<br>
     *         (状態変化) propertyInit()の呼び出し:引数がnullで呼び出される<br>
     *         (状態変化) ログ:【エラーログ】<br>
     *                    ＜メッセージ＞<br>
     *                    "Message resources file "null" is illegal."<br>
     *         
     * <br>
     * configがnullの場合、dbInitが呼ばれず、propertyInitが呼ばれ、エラーログを出力し、インスタンスを返却することを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testDBMessageResourcesMessageResourcesFactoryString04() throws Exception {
        // 前処理
        
        // dbInitが呼ばれないようにmapを設定する
        Map<String, String> dbMap
            = new HashMap<String, String>();
        dbMap.put("TEST01", "テストメッセージ０１");
        UTUtil.setPrivateField(
            DBMessageResources.class,
            "dbMessages",
            dbMap);
        DBMessageResourcesFactory factory
            = new DBMessageResourcesFactory();
        
        // テスト実施
        DBMessageResources resources
            = new DBMessageResources(factory, null);

        // 判定
        assertSame(factory, UTUtil.getPrivateField(resources, "factory"));
        assertNull(UTUtil.getPrivateField(resources, "config"));
        
        Map actualDbMap = (Map) UTUtil.getPrivateField(resources, "dbMessages");
        assertSame(dbMap, actualDbMap);
        assertEquals(1, actualDbMap.size());
        assertEquals("テストメッセージ０１", actualDbMap.get("TEST01"));
       
        Map actualMap = (Map) UTUtil.getPrivateField(resources, "messages");
        assertTrue(actualMap.isEmpty());

        assertTrue(LogUTUtil.checkError(
            "Message resources file \"null\" is illegal."));
        
    }

    /**
     * testDBMessageResourcesMessageResourcesFactoryString05()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) factory:DBMessageResourcesFactoryインスタンス<br>
     *         (引数) config:""<br>
     *         (状態) dbMessages:["TEST01"->"テストメッセージ０１"]<br>
     *         
     * <br>
     * 期待値：(戻り値) DBMessageResources:factory->前提条件で設定したインスタンス<br>
     *                  config->""<br>
     *         (状態変化) dbInit()の呼び出し:呼び出されない<br>
     *         (状態変化) propertyInit()の呼び出し:引数がconfigと等しい文字列で呼び出される<br>
     *         (状態変化) ログ:【エラーログ】<br>
     *                    ＜メッセージ＞<br>
     *                    "Message resources file "" is illegal."<br>
     *         
     * <br>
     * configがnullの場合、dbInitが呼ばれず、propertyInitが呼ばれ、
     * エラーログを出力し、インスタンスを返却することを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testDBMessageResourcesMessageResourcesFactoryString05() throws Exception {
        // 前処理        
        // dbInitが呼ばれないようにmapを設定する
        Map<String, String> dbMap
            = new HashMap<String, String>();
        dbMap.put("TEST01", "テストメッセージ０１");
        UTUtil.setPrivateField(
            DBMessageResources.class,
            "dbMessages",
            dbMap);
        DBMessageResourcesFactory factory
            = new DBMessageResourcesFactory();
        
        // テスト実施
        DBMessageResources resources
            = new DBMessageResources(factory, "");

        // 判定
        assertSame(factory, UTUtil.getPrivateField(resources, "factory"));
        assertEquals("", UTUtil.getPrivateField(resources, "config"));
        
        Map actualDbMap = (Map) UTUtil.getPrivateField(resources, "dbMessages");
        assertSame(dbMap, actualDbMap);
        assertEquals(1, actualDbMap.size());
        assertEquals("テストメッセージ０１", actualDbMap.get("TEST01"));
        
        Map actualMap = (Map) UTUtil.getPrivateField(resources, "messages");
        assertTrue(actualMap.isEmpty());
        assertTrue(LogUTUtil.checkError(
            "Message resources file \"\" is illegal."));
        
    }

    /**
     * testDBMessageResourcesMessageResourcesFactoryStringboolean01()
     * <br><br>
     * 
     *  (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) factory:DBMessageResourcesFactoryインスタンス<br>
     *         (引数) config:"DBMessageResources_MessageResources05"<br>
     *                       【ファイルの内容】<br>
     *                       error.date.format={0}の日時は{1}形式で入力してください。<br>
     *         (引数) returnNull:true<br>
     *         (状態) dbMessages:null<br>
     *         
     * <br>
     * 期待値：(戻り値) DBMessageResources:factory->前提条件で設定したインスタンス<br>
     *                  config->"DBMessageResources_MessageResources05"<br>
     *                  returnNull->true<br>
     *         (状態変化) dbInit()の呼び出し:呼び出される<br>
     *         (状態変化) propertyInit()の呼び出し:引数がconfigと等しい文字列で呼び出される<br>
     *         
     * <br>
     * dbInitが呼ばれ、propertyInitが呼ばれていることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testDBMessageResourcesMessageResourcesFactoryStringboolean01() throws Exception {
        // 前処理
        UTUtil.setPrivateField(
                DBMessageResources.class,
                "dbMessages",
                null);
        DBMessageResourcesFactory factory
            = new DBMessageResourcesFactory();
        
        // テスト実施
        DBMessageResources resources
        = new DBMessageResources(factory, 
            DBMessageResources.class.getPackage().getName()
            .replace('.', '/') + "/DBMessageResources_MessageResources05",
            true);    
        
        // 判定
        assertSame(factory, UTUtil.getPrivateField(resources, "factory"));
        assertEquals(DBMessageResources.class.getPackage().getName()
                .replace('.', '/') + "/DBMessageResources_MessageResources05",
                UTUtil.getPrivateField(resources, "config"));
        assertEquals(true, UTUtil.getPrivateField(resources, "returnNull"));
        
        Map actualDbMap 
            = (Map) UTUtil.getPrivateField(DBMessageResources.class, 
            "dbMessages");
        assertTrue(actualDbMap.isEmpty());
        
        Map actualMap = (Map) UTUtil.getPrivateField(resources, "messages");
        assertEquals(1, actualMap.size());
        assertEquals("{0}の日時は{1}形式で入力してください。",
                     actualMap.get("error.date.format"));
    }

    /**
     * testDBMessageResourcesMessageResourcesFactoryStringboolean02()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) factory:DBMessageResourcesFactoryインスタンス<br>
     *         (引数) config:"DBMessageResources_MessageResources05"<br>
     *                       【ファイルの内容】<br>
     *                       error.date.format={0}の日時は{1}形式で入力してください。<br>
     *         (引数) returnNull:true<br>
     *         (状態) dbMessages:["TEST01"->"テストメッセージ０１"]<br>
     *         
     * <br>
     * 期待値：(戻り値) DBMessageResources:factory->前提条件で設定したインスタンス<br>
     *                  config->"DBMessageResources_MessageResources05"<br>
     *                  returnNull->true<br>
     *         (状態変化) dbInit()の呼び出し:呼び出されない<br>
     *         (状態変化) propertyInit()の呼び出し:引数がconfigと等しい文字列で呼び出される<br>
     *         
     * <br>
     * dbInitが呼ばれず、propertyInitが呼ばれていることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testDBMessageResourcesMessageResourcesFactoryStringboolean02() throws Exception {
        // 前処理
        // dbInitが呼ばれないようにmapを設定する
        Map<String, String> dbMap
            = new HashMap<String, String>();
        dbMap.put("TEST01", "テストメッセージ０１");
        UTUtil.setPrivateField(
                DBMessageResources.class,
                "dbMessages",
                dbMap);
        DBMessageResourcesFactory factory
            = new DBMessageResourcesFactory();
        
        // テスト実施
        DBMessageResources resources
        = new DBMessageResources(factory, 
            DBMessageResources.class.getPackage().getName()
            .replace('.', '/') + "/DBMessageResources_MessageResources05",
            true);     
        
        // 判定
        assertSame(factory, UTUtil.getPrivateField(resources, "factory"));
        assertEquals(
                DBMessageResources.class.getPackage().getName()
                .replace('.', '/') + "/DBMessageResources_MessageResources05",
                UTUtil.getPrivateField(resources, "config"));
        assertEquals(true, UTUtil.getPrivateField(resources, "returnNull"));
        
        Map actualDbMap = (Map) UTUtil.getPrivateField(resources, "dbMessages");
        assertSame(dbMap, actualDbMap);
        assertEquals(1, actualDbMap.size());
        assertEquals("テストメッセージ０１", actualDbMap.get("TEST01"));
        
        Map actualMap = (Map) UTUtil.getPrivateField(resources, "messages");
        assertEquals(1, actualMap.size());
        assertEquals("{0}の日時は{1}形式で入力してください。",
                     actualMap.get("error.date.format"));
        
    }

    /**
     * testDBMessageResourcesMessageResourcesFactoryStringboolean03()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) factory:null<br>
     *         (引数) config:"DBMessageResources_MessageResources05"<br>
     *                       【ファイルの内容】<br>
     *                       error.date.format={0}の日時は{1}形式で入力してください。<br>
     *         (引数) returnNull:true<br>
     *         (状態) dbMessages:["TEST01"->"テストメッセージ０１"]<br>
     *         
     * <br>
     * 期待値：(戻り値) DBMessageResources:factory->null<br>
     *                  config->"DBMessageResources_MessageResources05"<br>
     *                  returnNull->true<br>
     *         (状態変化) dbInit()の呼び出し:呼び出されない<br>
     *         (状態変化) propertyInit()の呼び出し:引数がconfigと等しい文字列で呼び出される<br>
     *         
     * <br>
     * dbInitが呼ばれず、propertyInitが呼ばれていることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testDBMessageResourcesMessageResourcesFactoryStringboolean03() throws Exception {
        // 前処理        
        // dbInitが呼ばれないようにmapを設定する
        Map<String, String> dbMap
            = new HashMap<String, String>();
        dbMap.put("TEST01", "テストメッセージ０１");
        UTUtil.setPrivateField(
                DBMessageResources.class,
                "dbMessages",
                dbMap);
        
        // テスト実施
        DBMessageResources resources 
        = new DBMessageResources(null, 
            DBMessageResources.class.getPackage().getName()
            .replace('.', '/') + "/DBMessageResources_MessageResources05",
            true);   
        
        // 判定
        assertNull(UTUtil.getPrivateField(resources, "factory"));
        assertEquals(
                DBMessageResources.class.getPackage().getName()
                .replace('.', '/') + "/DBMessageResources_MessageResources05",
                UTUtil.getPrivateField(resources, "config"));
        assertEquals(true, UTUtil.getPrivateField(resources, "returnNull"));
        
        Map actualDbMap = (Map) UTUtil.getPrivateField(resources, "dbMessages");
        assertSame(dbMap, actualDbMap);
        assertEquals(1, actualDbMap.size());
        assertEquals("テストメッセージ０１", actualDbMap.get("TEST01"));
        
        Map actualMap = (Map) UTUtil.getPrivateField(resources, "messages");
        assertEquals(1, actualMap.size());
        assertEquals("{0}の日時は{1}形式で入力してください。",
                     actualMap.get("error.date.format"));
        
    }

    /**
     * testDBMessageResourcesMessageResourcesFactoryStringboolean04()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) factory:DBMessageResourcesFactoryインスタンス<br>
     *         (引数) config:null<br>
     *         (引数) returnNull:true<br>
     *         (状態) dbMessages:["TEST01"->"テストメッセージ０１"]<br>
     *         
     * <br>
     * 期待値：(戻り値) DBMessageResources:factory->前提条件で設定したインスタンス<br>
     *                  config->null<br>
     *                  returnNull->true<br>
     *         (状態変化) dbInit()の呼び出し:呼び出されない<br>
     *         (状態変化) propertyInit()の呼び出し:引数がconfigと等しい文字列で呼び出される<br>
     *         (状態変化) ログ:【エラーログ】<br>
     *                    ＜メッセージ＞<br>
     *                    "Message resources file "null" is illegal."<br>
     *         
     * <br>
     * configがnullの場合、dbInitが呼ばれず、propertyInitが呼ばれ、
     * エラーログを出力し、インスタンスを返却することを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testDBMessageResourcesMessageResourcesFactoryStringboolean04() throws Exception {
        // 前処理        
        // dbInitが呼ばれないようにmapを設定する
        Map<String, String> dbMap
            = new HashMap<String, String>();
        dbMap.put("TEST01", "テストメッセージ０１");
        UTUtil.setPrivateField(
                DBMessageResources.class,
                "dbMessages",
                dbMap);
        DBMessageResourcesFactory factory
            = new DBMessageResourcesFactory();
        
        // テスト実施
        DBMessageResources resources
            = new DBMessageResources(factory, null, true);

        // 判定
        assertSame(factory, UTUtil.getPrivateField(resources, "factory"));
        assertNull(UTUtil.getPrivateField(resources, "config"));
        assertEquals(true, UTUtil.getPrivateField(resources, "returnNull"));

        Map actualDbMap = (Map) UTUtil.getPrivateField(resources, "dbMessages");
        assertSame(dbMap, actualDbMap);
        assertEquals(1, actualDbMap.size());
        assertEquals("テストメッセージ０１", actualDbMap.get("TEST01"));
        
        Map actualMap = (Map) UTUtil.getPrivateField(resources, "messages");
        assertTrue(actualMap.isEmpty());

        assertTrue(LogUTUtil.checkError(
                "Message resources file \"null\" is illegal."));
        
    }

    /**
     * testDBMessageResourcesMessageResourcesFactoryStringboolean05()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) factory:DBMessageResourcesFactoryインスタンス<br>
     *         (引数) config:""<br>
     *         (引数) returnNull:true<br>
     *         (状態) dbMessages:["TEST01"->"テストメッセージ０１"]<br>
     *         
     * <br>
     * 期待値：(戻り値) DBMessageResources:factory->前提条件で設定したインスタンス<br>
     *                  config->""<br>
     *                  returnNull->true<br>
     *         (状態変化) dbInit()の呼び出し:呼び出されない<br>
     *         (状態変化) propertyInit()の呼び出し:引数がconfigと等しい文字列で呼び出される<br>
     *         (状態変化) ログ:【エラーログ】<br>
     *                    ＜メッセージ＞<br>
     *                    "Message resources file "" is illegal."<br>
     *         
     * <br>
     * configがnullの場合、dbInitが呼ばれず、propertyInitが呼ばれ、
     * エラーログを出力し、インスタンスを返却することを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testDBMessageResourcesMessageResourcesFactoryStringboolean05() throws Exception {
        // 前処理        
        // dbInitが呼ばれないようにmapを設定する
        Map<String, String> dbMap
            = new HashMap<String, String>();
        dbMap.put("TEST01", "テストメッセージ０１");
        UTUtil.setPrivateField(
                DBMessageResources.class,
                "dbMessages",
                dbMap);
        DBMessageResourcesFactory factory
            = new DBMessageResourcesFactory();
        
        // テスト実施
        DBMessageResources resources
            = new DBMessageResources(factory, "", true);

        // 判定
        assertSame(factory, UTUtil.getPrivateField(resources, "factory"));
        assertEquals("", UTUtil.getPrivateField(resources, "config"));
        assertEquals(true, UTUtil.getPrivateField(resources, "returnNull"));
        
        Map actualDbMap = (Map) UTUtil.getPrivateField(resources, "dbMessages");
        assertSame(dbMap, actualDbMap);
        assertEquals(1, actualDbMap.size());
        assertEquals("テストメッセージ０１", actualDbMap.get("TEST01"));
        
        Map actualMap = (Map) UTUtil.getPrivateField(resources, "messages");
        assertTrue(actualMap.isEmpty());
        assertTrue(LogUTUtil.checkError(
                "Message resources file \"\" is illegal."));
    }

    /**
     * testDBMessageResourcesMessageResourcesFactoryStringboolean06()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) factory:DBMessageResourcesFactoryインスタンス<br>
     *         (引数) config:"DBMessageResources_MessageResources05"<br>
     *                       【ファイルの内容】<br>
     *                       error.date.format={0}の日時は{1}形式で入力してください。<br>
     *         (引数) returnNull:false<br>
     *         (状態) dbMessages:["TEST01"->"テストメッセージ０１"]<br>
     *         
     * <br>
     * 期待値：(戻り値) DBMessageResources:factory->前提条件で設定したインスタンス<br>
     *                  config->"DBMessageResources_MessageResources05"<br>
     *                  returnNull->false<br>
     *         (状態変化) dbInit()の呼び出し:呼び出されない<br>
     *         (状態変化) propertyInit()の呼び出し:引数がconfigと等しい文字列で呼び出される<br>
     *         
     * <br>
     * dbInitが呼ばれず、propertyInitが呼ばれていることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testDBMessageResourcesMessageResourcesFactoryStringboolean06() throws Exception {
        // 前処理        
        // dbInitが呼ばれないようにmapを設定する
        Map<String, String> dbMap
            = new HashMap<String, String>();
        dbMap.put("TEST01", "テストメッセージ０１");
        UTUtil.setPrivateField(
                DBMessageResources.class,
                "dbMessages",
                dbMap);
        DBMessageResourcesFactory factory
            = new DBMessageResourcesFactory();
        
        // テスト実施

        DBMessageResources resources
            = new DBMessageResources(factory, 
                DBMessageResources.class.getPackage().getName()
                .replace('.', '/') + "/DBMessageResources_MessageResources05",
                false);   
        
        // 判定
        assertSame(factory, UTUtil.getPrivateField(resources, "factory"));
        assertEquals(
                DBMessageResources.class.getPackage().getName()
                .replace('.', '/') + "/DBMessageResources_MessageResources05",
                UTUtil.getPrivateField(resources, "config"));
        assertEquals(false, UTUtil.getPrivateField(resources, "returnNull"));
        
        Map actualDbMap = (Map) UTUtil.getPrivateField(resources, "dbMessages");
        assertSame(dbMap, actualDbMap);
        assertEquals(1, actualDbMap.size());
        assertEquals("テストメッセージ０１", actualDbMap.get("TEST01"));
        
        Map actualMap = (Map) UTUtil.getPrivateField(resources, "messages");
        assertEquals(1, actualMap.size());
        assertEquals("{0}の日時は{1}形式で入力してください。",
                     actualMap.get("error.date.format"));
    }

}
