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

import org.apache.struts.util.MessageResources;

import jp.terasoluna.fw.exception.SystemException;
import jp.terasoluna.fw.util.ClassLoadException;
import jp.terasoluna.utlib.LogUTUtil;
import jp.terasoluna.utlib.PropertyTestCase;
import jp.terasoluna.utlib.UTUtil;

/**
 * {@link jp.terasoluna.fw.web.struts.action.DBMessageResourcesFactory} クラスのブラックボックステスト。
 * 
 * <p>
 * <h4>【クラスの概要】</h4>
 * DBMessageResourcesを生成するファクトリクラス。
 * <p>
 * 
 * @see jp.terasoluna.fw.web.struts.action.DBMessageResourcesFactory
 */
public class DBMessageResourcesFactoryTest extends PropertyTestCase {

    /**
     * このテストケースを実行する為の
     * GUI アプリケーションを起動する。
     * 
     * @param args java コマンドに設定されたパラメータ
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(DBMessageResourcesFactoryTest.class);
    }

    /**
     * 初期化処理を行う。
     * 
     * @throws Exception このメソッドで発生した例外
     * @see jp.terasoluna.utlib.spring.PropertyTestCase#setUpData()
     */
    @Override
    protected void setUpData() throws Exception {
    }

    /**
     * 終了処理を行う。
     * 
     * @throws Exception このメソッドで発生した例外
     * @see jp.terasoluna.utlib.spring.PropertyTestCase#cleanUpData()
     */
    @Override
    protected void cleanUpData() throws Exception {
    }

    /**
     * コンストラクタ。
     * 
     * @param name このテストケースの名前。
     */
    public DBMessageResourcesFactoryTest(String name) {
        super(name);
    }

    /**
     * testCreateResources01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：G
     * <br><br>
     * 入力値：(引数) config:"DBMessageResourcesFactory_MessageResources01"<br>
     *                （空のファイル）<br>
     *         (状態) system.properties内の関連するプロパティ:messages.dao="aaaaa"<br>
     *                messages.sql=SELECT KEY, VALUE FROM MESSAGES<br>
     *         (状態) DBMessageResourcesのdbMessages:null<br>
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
     *         
     * <br>
     * システム設定プロパティファイル（system.properties）に、存在しないdaoがmessages.daoというキーで定義してある場合、エラーログを出力し、例外がスローされることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testCreateResources01() throws Exception {
        // 前処理
        DBMessageResourcesFactory factory 
            = new DBMessageResourcesFactory();
        addProperty("messages.dao", "aaaaa");
        addProperty("messages.sql", "SELECT KEY, VALUE FROM MESSAGES");
        UTUtil.setPrivateField(DBMessageResources.class, "dbMessages", null);
        
        // テスト実施
        try {
            factory.createResources(
                    "DBMessageResourcesFactory_MessageResources01");
            fail();
        } catch (SystemException e) {
            // 判定
            assertEquals("errors.db.message.resources.init", e.getErrorCode());
            assertEquals(ClassLoadException.class.getName(),
                         e.getCause().getClass().getName());
            assertTrue(LogUTUtil.checkError("\"aaaaa\" cannot loaded.",
                                            e.getCause()));
            
        }
    }

    /**
     * testCreateResources02()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) config:"DBMessageResourcesFactory_MessageResources01"<br>
     *                （空のファイル）<br>
     *         (状態) system.properties内の関連するプロパティ:messages.daoをキーとするプロパティが存在しない<br>
     *                messages.sqlをキーとするプロパティが存在しない<br>
     *         (状態) DBMessageResourcesのdbMessages:null<br>
     *         
     * <br>
     * 期待値：(戻り値) DBMessageResources:factory: 戻り値を生成したDBMessageResourcesFactoryインスタンス自身<br>
     *                  config: "DBMessageResourcesFactory_MessageResources01"<br>
     *                  returnNull: super.returnNull<br>
     *         
     * <br>
     * DBMessageResourcesが生成されていることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testCreateResources02() throws Exception {
        // 前処理
        DBMessageResourcesFactory factory 
            = new DBMessageResourcesFactory();
        deleteProperty("messages.dao");
        deleteProperty("messages.sql");
        UTUtil.setPrivateField(DBMessageResources.class, "dbMessages", null);
        
        // テスト実施
        MessageResources resources 
            = factory.createResources(
                "DBMessageResourcesFactory_MessageResources01");
       
        // 判定
        assertEquals(DBMessageResources.class.getName(),
                     resources.getClass().getName());
        assertSame(factory,
                   UTUtil.getPrivateField(resources, "factory"));
        assertEquals("DBMessageResourcesFactory_MessageResources01",
                     UTUtil.getPrivateField(resources, "config"));
        assertEquals(UTUtil.getPrivateField(factory, "returnNull"),
                     UTUtil.getPrivateField(resources, "returnNull"));
    }

    /**
     * testCreateResources03()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) config:""<br>
     *         (状態) DBMessageResourcesのdbMessages:null<br>
     *         
     * <br>
     * 期待値：(戻り値) DBMessageResources:factory: 戻り値を生成したDBMessageResourcesFactoryインスタンス自身<br>
     *                  config: ""<br>
     *                  returnNull: super.returnNull<br>
     *         (状態変化) ログ:【エラーログ】<br>
     *                    ＜メッセージ＞<br>
     *                    "Message resources file "" is illegal."<br>
     *         
     * <br>
     * メッセージリソース定義ファイルに空文字列を指定した場合、エラーログを出力し、DBMessageResourcesが生成されることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testCreateResources03() throws Exception {
        // 前処理
        DBMessageResourcesFactory factory 
            = new DBMessageResourcesFactory();
        deleteProperty("messages.dao");
        deleteProperty("messages.sql");
        UTUtil.setPrivateField(DBMessageResources.class, "dbMessages", null);
        
        // テスト実施
        MessageResources resources 
            = factory.createResources(
                "");
       
        // 判定
        assertTrue(resources instanceof DBMessageResources);
        assertSame(factory,
                   UTUtil.getPrivateField(resources, "factory"));
        assertEquals("",
                     UTUtil.getPrivateField(resources, "config"));
        assertEquals(UTUtil.getPrivateField(factory, "returnNull"),
                     UTUtil.getPrivateField(resources, "returnNull"));
        assertTrue((LogUTUtil.checkError("Message resources file \"\" is illegal.")));
    }

    /**
     * testCreateResources04()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) config:null<br>
     *         (状態) DBMessageResourcesのdbMessages:null<br>
     *         
     * <br>
     * 期待値：(戻り値) DBMessageResources:factory: 戻り値を生成したDBMessageResourcesFactoryインスタンス自身<br>
     *                  config: null<br>
     *                  returnNull: super.returnNull<br>
     *         (状態変化) ログ:【エラーログ】<br>
     *                    ＜メッセージ＞<br>
     *                    "Message resources file "null" is illegal."<br>
     *         
     * <br>
     * メッセージリソース定義ファイルにnullを指定した場合、エラーログを出力し、DBMessageResourcesが生成されることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testCreateResources04() throws Exception {
        // 前処理
        DBMessageResourcesFactory factory 
            = new DBMessageResourcesFactory();
        deleteProperty("messages.dao");
        deleteProperty("messages.sql");
        UTUtil.setPrivateField(DBMessageResources.class, "dbMessages", null);
        
        // テスト実施
        MessageResources resources 
            = factory.createResources(
                null);
       
        // 判定
        assertTrue(resources instanceof DBMessageResources);
        assertSame(factory,
                   UTUtil.getPrivateField(resources, "factory"));
        assertNull(UTUtil.getPrivateField(resources, "config"));
        assertEquals(UTUtil.getPrivateField(factory, "returnNull"),
                     UTUtil.getPrivateField(resources, "returnNull"));
        assertTrue(LogUTUtil.checkError("Message resources file \"null\" is illegal."));
    }

}
