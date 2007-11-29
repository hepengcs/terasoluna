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

import jp.terasoluna.utlib.LogUTUtil;
import jp.terasoluna.utlib.PropertyTestCase;
import jp.terasoluna.utlib.UTUtil;

/**
 * {@link jp.terasoluna.fw.web.struts.action.GlobalMessageResources} 
 * クラスのブラックボックステスト。
 * 
 * <p>
 * <h4>【クラスの概要】</h4>
 * 業務共通、システムのメッセージリソースを生成するクラス。
 * privateなコンストラクタしかないため、テスト対象ンスタンスの生成にgetInstance()を用いている。
 * <p>
 * 
 * @see jp.terasoluna.fw.web.struts.action.GlobalMessageResources
 */
@SuppressWarnings("unchecked")
public class GlobalMessageResourcesTest extends PropertyTestCase {

    /**
     * このテストケースを実行する為の
     * GUI アプリケーションを起動する。
     * 
     * @param args java コマンドに設定されたパラメータ
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(GlobalMessageResourcesTest.class);
    }

    /**
     * コンストラクタ。
     * 
     * @param name このテストケースの名前。
     */
    public GlobalMessageResourcesTest(String name) {
        super(name);
    }

    /**
     * testGetInstance01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(状態) globalMessageResources:null<br>
     *         
     * <br>
     * 期待値：(戻り値) GlobalMessageResources:not null<br>
     *         (状態変化) globalMessageResources:返された値と同一のインスタンス<br>
     *         
     * <br>
     * メソッドを呼び出したことでフィールドに戻り値と同じ値が
     * 設定されていることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetInstance01() throws Exception {
        // 前処理
        // シングルトンインスタンスにnullを設定
        UTUtil.setPrivateField(GlobalMessageResources.class,
                               "globalMessageResources", null);
            
        // テスト実施
        Object result = 
            GlobalMessageResources.getInstance();
        
        // 判定
        assertEquals(GlobalMessageResources.class.getName(),
                     result.getClass().getName());
        assertSame(result, UTUtil.getPrivateField(GlobalMessageResources.class,
            "globalMessageResources"));
    }

    /**
     * testGetInstance02()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(状態) globalMessageResources:not null<br>
     *         
     * <br>
     * 期待値：(戻り値) GlobalMessageResources:前提条件と同一のインスタンス<br>
     *         (状態変化) globalMessageResources:前提条件と同一のインスタンス<br>
     *         
     * <br>
     * フィールドに設定されているインスタンスと同一のインスタンスが
     * 返されることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetInstance02() throws Exception {
        // 前処理
        Object prepare = GlobalMessageResources.getInstance();
        UTUtil.setPrivateField(GlobalMessageResources.class,
                "globalMessageResources", prepare);

        // テスト実施
        Object result = 
            GlobalMessageResources.getInstance();

        // 判定
        assertSame(prepare, result);
    }

    /**
     * testGlobalInit01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(状態) config:"aaaaa"<br>
     *                （存在しないファイル名）<br>
     *         (状態) fwMessages:["key"->"value"]<br>
     *         
     * <br>
     * 期待値：(状態変化) fwMessages:空のMap<br>
     *         
     * <br>
     * プロパティが正常に取得できない場合、システムのメッセージが
     * クリアされ、空になっていることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGlobalInit01() throws Exception {
        // 前処理
        GlobalMessageResources resources = GlobalMessageResources.getInstance();
        UTUtil.setPrivateField(resources, "config", "aaaaa");
        
        Map fwMap = new HashMap();
        fwMap.put("key", "value");
        UTUtil.setPrivateField(resources, "fwMessages", fwMap);
        
        // テスト実施
        UTUtil.invokePrivate(resources, "globalInit");
        
        // 判定
        Map resultMap = (Map) UTUtil.getPrivateField(resources, "fwMessages");
        assertTrue(resultMap.isEmpty());
    }

    /**
     * testGlobalInit02()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(状態) config:"GlobalMessageResources_system-messages01"<br>
     *                （空のファイル）<br>
     *         (状態) fwMessages:空のMap<br>
     *         
     * <br>
     * 期待値：(状態変化) fwMessages:空のMap<br>
     *         
     * <br>
     * プロパティファイル内で設定されたプロパティが0件の時、
     * システムのメッセージが空であることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGlobalInit02() throws Exception {
        // 前処理
        GlobalMessageResources resources = GlobalMessageResources.getInstance();
        // configに0件プロパティファイル名を設定
        UTUtil.setPrivateField(resources, "config",
                GlobalMessageResources.class.getPackage().getName().replace(
                        '.', '/')
                        + "/GlobalMessageResources_system-messages01");
        UTUtil.setPrivateField(resources, "fwMessages", new HashMap());
        
        // テスト実施
        UTUtil.invokePrivate(resources, "globalInit");
        
        // 判定
        // システムメッセージが0件であること。
        Map resultMap = (Map) UTUtil.getPrivateField(resources, "fwMessages");
        assertTrue(resultMap.isEmpty());
    }

    /**
     * testGlobalInit03()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(状態) config:"GlobalMessageResources_system-messages02"<br>
     *                【ファイルの内容】<br>
     *                test.fw.message.01=ＦＷテストメッセージ０１<br>
     *         (状態) fwMessages:空のMap<br>
     *         
     * <br>
     * 期待値：(状態変化) fwMessages:["test.fw.messages.01"->"ＦＷテストメッセージ０１"]<br>
     *         
     * <br>
     * プロパティファイル内で設定されたプロパティが1件の時、
     * システムのメッセージが設定されていることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGlobalInit03() throws Exception {
        // 前処理
        GlobalMessageResources resources = GlobalMessageResources.getInstance();
        // configに1件プロパティファイル名を設定
        UTUtil.setPrivateField(resources, "config",
                GlobalMessageResources.class.getPackage().getName().replace(
                        '.', '/')
                        + "/GlobalMessageResources_system-messages02");
        UTUtil.setPrivateField(resources, "fwMessages", new HashMap());
        
        // テスト実施
        UTUtil.invokePrivate(resources, "globalInit");
        
        // 判定
        // システムメッセージが1件であること。
        Map resultMap = (Map) UTUtil.getPrivateField(resources, "fwMessages");
        assertEquals(1, resultMap.size());
        assertEquals("ＦＷテストメッセージ０１",
                     resultMap.get("test.fw.message.01"));
    }

    /**
     * testGlobalInit04()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(状態) config:"GlobalMessageResources_system-messages03"<br>
     *                【ファイルの内容】<br>
     *                test.fw.message.01=ＦＷテストメッセージ０１<br>
     *                test.fw.message.02=ＦＷテストメッセージ０２<br>
     *                test.fw.message.03=ＦＷテストメッセージ０３<br>
     *         (状態) fwMessages:空のMap<br>
     *         
     * <br>
     * 期待値：(状態変化) fwMessages:["test.fw.messages.01"->"ＦＷテストメッセージ０１"]<br>
     *                    ["test.fw.messages.02"->"ＦＷテストメッセージ０２"]<br>
     *                    ["test.fw.messages.03"->"ＦＷテストメッセージ０３"]<br>
     *         
     * <br>
     * プロパティファイル内で設定されたプロパティが3件の時、
     * システムのメッセージが設定されていることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGlobalInit04() throws Exception {
        // 前処理
        GlobalMessageResources resources = GlobalMessageResources.getInstance();
        // configに3件プロパティファイル名を設定
        UTUtil.setPrivateField(resources, "config",
                GlobalMessageResources.class.getPackage().getName().replace(
                        '.', '/')
                        + "/GlobalMessageResources_system-messages03");
        UTUtil.setPrivateField(resources, "fwMessages", new HashMap());
        
        // テスト実施
        UTUtil.invokePrivate(resources, "globalInit");
        
        // 判定
        // システムメッセージが3件であること。
        Map resultMap = (Map) UTUtil.getPrivateField(resources, "fwMessages");
        assertEquals(3, resultMap.size());

        assertEquals("ＦＷテストメッセージ０１", resultMap.get("test.fw.message.01"));
        assertEquals("ＦＷテストメッセージ０２", resultMap.get("test.fw.message.02"));
        assertEquals("ＦＷテストメッセージ０３", resultMap.get("test.fw.message.03"));
    }

    /**
     * testApplicationInit01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(状態) PropertyUtil.getProperty("application.messages"):null<br>
     *         (状態) 業務共通メッセージリソース定義ファイル（application-messages.properties）:test.gb.message.01=ＧＢテストメッセージ０１<br>
     *         (状態) globalMessages:空のMap<br>
     *         
     * <br>
     * 期待値：(状態変化) globalMessages:["test.gb.message.01"->"ＧＢテストメッセージ０１"]<br>
     *         
     * <br>
     * PropertyUtil.getProperty("application.messages")がnullの時、
     * application-messages.propertiesがロードされ、ルートの業務共通メッセージとして設定されること。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testApplicationInit01() throws Exception {
        // 前処理
        GlobalMessageResources resources = GlobalMessageResources.getInstance();
        deleteProperty("application.messages");
        UTUtil.setPrivateField(resources, "globalMessages", new HashMap());
        
        // テスト実施
        UTUtil.invokePrivate(resources, "applicationInit");

        // 判定
        // application-resources.propertiesがロードされ、
        // app.resource=リソース が取得できること。
        Map appMap = (Map) UTUtil.getPrivateField(resources, "globalMessages");
        assertEquals("ＧＢテストメッセージ０１", appMap.get("test.gb.message.01"));
    }

    /**
     * testApplicationInit02()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(状態) PropertyUtil.getProperty("application.messages"):"GlobalMessageResources_application-messages01"<br>
     *         (状態) 業務共通メッセージリソース定義ファイル（application-messages.properties）:test.gb.message.01=ＧＢテストメッセージ０１<br>
     *                test.gb.message.02=ＧＢテストメッセージ０２<br>
     *                test.gb.message.03=ＧＢテストメッセージ０３<br>
     *         (状態) globalMessages:空のMap<br>
     *         
     * <br>
     * 期待値：(状態変化) globalMessages:["test.gb.message.01"->"ＧＢテストメッセージ０１"]<br>
     *                    ["test.gb.message.02"->"ＧＢテストメッセージ０２"]<br>
     *                    ["test.gb.message.03"->"ＧＢテストメッセージ０３"]<br>
     *         
     * <br>
     * application.messagesキーが"GlobalMessageResources_application-messages01"
     * の時、application-messages01.propertiesがロードされ、
     * 業務共通メッセージとして設定されること。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testApplicationInit02() throws Exception {
        // 前処理
        GlobalMessageResources resources = GlobalMessageResources.getInstance();

        // プロパティを書き換える。
        deleteProperty("application.messages");
        addProperty("application.messages", 
                    "jp/terasoluna/fw/web/struts/action/GlobalMessageResources_application-messages01");
        UTUtil.setPrivateField(resources, "globalMessages", new HashMap());
        
        // テスト実施
        UTUtil.invokePrivate(resources, "applicationInit");

        // 判定
        Map appMap = (Map) UTUtil
            .getPrivateField(resources, "globalMessages");
        assertEquals("ＧＢテストメッセージ０１", appMap.get("test.gb.message.01"));
        assertEquals("ＧＢテストメッセージ０２", appMap.get("test.gb.message.02"));
        assertEquals("ＧＢテストメッセージ０３", appMap.get("test.gb.message.03"));
    }

    /**
     * testApplicationInit03()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(状態) PropertyUtil.getProperty("application.messages"):"aaaaa"<br>
     *         (状態) globalMessages:["test.gb.message.01"->"既存のＧＢテストメッセージ０１"]<br>
     *                ["test.gb.message.02"->"既存のＧＢテストメッセージ０２"]<br>
     *                ["test.gb.message.03"->"既存のＧＢテストメッセージ０３"]<br>
     *         
     * <br>
     * 期待値：(状態変化) globalMessages:["test.gb.message.01"->"既存のＧＢテストメッセージ０１"]<br>
     *                    ["test.gb.message.02"->"既存のＧＢテストメッセージ０２"]<br>
     *                    ["test.gb.message.03"->"既存のＧＢテストメッセージ０３"]<br>
     *         
     * <br>
     * application.messagesキーで登録されている値が存在しないファイルの時、
     * 業務共通メッセージは登録されず、また、クリアもされないこと。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testApplicationInit03() throws Exception {
        // 前処理
        GlobalMessageResources resources = GlobalMessageResources.getInstance();

        // プロパティを書き換える。
        deleteProperty("application.messages");
        addProperty("application.messages", "jp/terasoluna/fw/web/struts/action/aaaaa");
        
        // 既存の業務共通メッセージを設定
        Map defaultMap = new HashMap();
        defaultMap.put("test.gb.message.01", "既存のＧＢテストメッセージ０１");
        defaultMap.put("test.gb.message.02", "既存のＧＢテストメッセージ０２");
        defaultMap.put("test.gb.message.03", "既存のＧＢテストメッセージ０３");
        
        UTUtil.setPrivateField(resources, "globalMessages", defaultMap);
        
        // テスト実施
        UTUtil.invokePrivate(resources, "applicationInit");

        // 判定
        Map appMap = (Map) UTUtil
            .getPrivateField(resources, "globalMessages");
        assertSame(defaultMap, appMap);
        assertEquals(3, appMap.size());
        assertEquals("既存のＧＢテストメッセージ０１", appMap.get("test.gb.message.01"));
        assertEquals("既存のＧＢテストメッセージ０２", appMap.get("test.gb.message.02"));
        assertEquals("既存のＧＢテストメッセージ０３", appMap.get("test.gb.message.03"));
    }

    /**
     * testApplicationInit04()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(状態) PropertyUtil.getProperty("application.messages"):"GlobalMessageResources_application-messages02"<br>
     *         (状態) 業務共通メッセージリソース定義ファイル（application-messages.properties）:空のファイル<br>
     *         (状態) globalMessages:["test.gb.message.01"->"既存のＧＢテストメッセージ０１"]<br>
     *                ["test.gb.message.02"->"既存のＧＢテストメッセージ０２"]<br>
     *                ["test.gb.message.03"->"既存のＧＢテストメッセージ０３"]<br>
     *         
     * <br>
     * 期待値：(状態変化) globalMessages:空のMap<br>
     *         
     * <br>
     * 0件のメッセージが格納されたプロパティファイルを指定した時、
     * 既存のメッセージは、0件のメッセージで上書きされること。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testApplicationInit04() throws Exception {
        // 前処理
        GlobalMessageResources resources = GlobalMessageResources.getInstance();

        // プロパティを書き換える。
        deleteProperty("application.messages");
        addProperty("application.messages", 
                    "jp/terasoluna/fw/web/struts/action/GlobalMessageResources_application-messages02");
        
        // 既存の業務共通メッセージを設定
        Map defaultMap = new HashMap();
        defaultMap.put("test.gb.message.01", "既存のＧＢテストメッセージ０１");
        defaultMap.put("test.gb.message.02", "既存のＧＢテストメッセージ０２");
        defaultMap.put("test.gb.message.03", "既存のＧＢテストメッセージ０３");
        UTUtil.setPrivateField(resources, "globalMessages", defaultMap);
        
        // テスト実施
        UTUtil.invokePrivate(resources, "applicationInit");

        // 判定
        Map appMap = (Map) UTUtil.getPrivateField(resources, "globalMessages");
        assertTrue(appMap.isEmpty());
    }

    /**
     * testApplicationInit05()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(状態) PropertyUtil.getProperty("application.messages"):"GlobalMessageResources_application-messages01"<br>
     *         (状態) 業務共通メッセージリソース定義ファイル（application-messages.properties）:test.gb.message.01=ＧＢテストメッセージ０１<br>
     *                test.gb.message.02=ＧＢテストメッセージ０２<br>
     *                test.gb.message.03=ＧＢテストメッセージ０３<br>
     *         (状態) globalMessages:["test.gb.message.01"->"既存のＧＢテストメッセージ０１"]<br>
     *         
     * <br>
     * 期待値：(状態変化) globalMessages:["test.gb.message.01"->"ＧＢテストメッセージ０１"]<br>
     *                    ["test.gb.message.02"->"ＧＢテストメッセージ０２"]<br>
     *                    ["test.gb.message.03"->"ＧＢテストメッセージ０３"]<br>
     *         
     * <br>
     * 3件のメッセージが格納されたプロパティファイルを指定した時、
     * 既存のメッセージは、3件のメッセージで上書きされること。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testApplicationInit05() throws Exception {
        // 前処理
        GlobalMessageResources resources = GlobalMessageResources.getInstance();

        // プロパティを書き換える。
        deleteProperty("application.messages");
        addProperty("application.messages", 
                    "jp/terasoluna/fw/web/struts/action/GlobalMessageResources_application-messages01");
        
        // 既存の業務共通メッセージを設定
        Map defaultMap = new HashMap();
        defaultMap.put("test.gb.message.01", "既存のＧＢテストメッセージ０１");
        UTUtil.setPrivateField(resources, "globalMessages", defaultMap);
        
        // テスト実施
        UTUtil.invokePrivate(resources, "applicationInit");

        // 判定
        Map appMap = (Map) UTUtil
            .getPrivateField(resources, "globalMessages");
        assertEquals(3, appMap.size());
        
        assertEquals("ＧＢテストメッセージ０１", appMap.get("test.gb.message.01"));
        assertEquals("ＧＢテストメッセージ０２", appMap.get("test.gb.message.02"));
        assertEquals("ＧＢテストメッセージ０３", appMap.get("test.gb.message.03"));
    }

    /**
     * testApplicationInit06()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(状態) PropertyUtil.getProperty("application.messages"):"GlobalMessageResources_application-messages04"<br>
     *         (状態) 業務共通メッセージリソース定義ファイル（application-messages.properties）:test.gb.message.01=ＧＢテストメッセージ０１<br>
     *                add.message.file.1=GlobalMessageResources_application-messages04_add<br>
     *         (状態) 追加用の業務共通メッセージリソース定義ファイル:test.gb.message.addition=追加用ＧＢテストメッセージ０１<br>
     *         (状態) globalMessages:["test.gb.message.01"->"既存のＧＢテストメッセージ０１"]<br>
     *         
     * <br>
     * 期待値：(状態変化) globalMessages:["test.gb.message.01"->"ＧＢテストメッセージ０１"]<br>
     *                    ["test.gb.message.addition"->"追加用ＧＢテストメッセージ０１"]<br>
     *         
     * <br>
     * 業務共通のルートプロパティファイルが、1件のメッセージファイルを
     * 呼び出しており、キーが衝突していない場合は、
     * 2件のメッセージが登録されること。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testApplicationInit06() throws Exception {
        // 前処理
        GlobalMessageResources resources = GlobalMessageResources.getInstance();

        // プロパティを書き換える。
        deleteProperty("application.messages");
        addProperty("application.messages", 
                    "jp/terasoluna/fw/web/struts/action/GlobalMessageResources_application-messages04");
        
        // 既存の業務共通メッセージを設定
        Map defaultMap = new HashMap();
        defaultMap.put("test.gb.message.01", "既存のＧＢテストメッセージ０１");
        UTUtil.setPrivateField(resources, "globalMessages", defaultMap);
        
        // テスト実施
        UTUtil.invokePrivate(resources, "applicationInit");

        // 判定
        Map appMap = (Map) UTUtil
            .getPrivateField(resources, "globalMessages");
        assertEquals(2, appMap.size());
        
        assertEquals("ＧＢテストメッセージ０１", appMap.get("test.gb.message.01"));
        assertEquals("追加用ＧＢテストメッセージ０１", appMap.get("test.gb.message.addition"));
    }

    /**
     * testApplicationInit07()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(状態) PropertyUtil.getProperty("application.messages"):"GlobalMessageResources_application-messages05"<br>
     *         (状態) 業務共通メッセージリソース定義ファイル（application-messages.properties）:test.gb.message.01=ＧＢテストメッセージ０１<br>
     *                add.message.file.1=GlobalMessageResources_application-messages05_add<br>
     *         (状態) 追加用の業務共通メッセージリソース定義ファイル:test.gb.message.01=追加用ＧＢテストメッセージ０１<br>
     *         (状態) globalMessages:["test.gb.message.01"->"既存のＧＢテストメッセージ０１"]<br>
     *         
     * <br>
     * 期待値：(状態変化) globalMessages:["test.gb.message.01"->"ＧＢテストメッセージ０１"]<br>
     *         
     * <br>
     * 業務共通のルートプロパティファイルが、1件のメッセージファイルを
     * 呼び出しており、キーが衝突している場合は、ルートのメッセージが
     * 採用されること。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testApplicationInit07() throws Exception {
        // 前処理
        GlobalMessageResources resources = GlobalMessageResources.getInstance();

        // プロパティを書き換える。
        deleteProperty("application.messages");
        addProperty("application.messages", 
                    "jp/terasoluna/fw/web/struts/action/GlobalMessageResources_application-messages05");
        
        // 既存の業務共通メッセージを設定
        Map defaultMap = new HashMap();
        defaultMap.put("test.gb.message.01", "既存のＧＢテストメッセージ０１");
        UTUtil.setPrivateField(resources, "globalMessages", defaultMap);
        
        // テスト実施
        UTUtil.invokePrivate(resources, "applicationInit");

        // 判定
        // 1件のプロパティで既存の業務共通メッセージリソースが更新されること。
        Map appMap = (Map) UTUtil
            .getPrivateField(resources, "globalMessages");
        assertEquals(1, appMap.size());
        assertEquals("ＧＢテストメッセージ０１", 
                     appMap.get("test.gb.message.01"));
    }

    /**
     * testApplicationInit08()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(状態) PropertyUtil.getProperty("application.messages"):"GlobalMessageResources_application-messages06"<br>
     *         (状態) 業務共通メッセージリソース定義ファイル（application-messages.properties）:test.gb.message.01=ＧＢテストメッセージ０１<br>
     *                add.message.file.1=GlobalMessageResources_application-messages06<br>
     *         (状態) 追加用の業務共通メッセージリソース定義ファイル:左と同一のファイルのため省略<br>
     *         (状態) globalMessages:["test.gb.message.01"->"既存のＧＢテストメッセージ０１"]<br>
     *         
     * <br>
     * 期待値：(状態変化) globalMessages:["test.gb.message.01"->"ＧＢテストメッセージ０１"]<br>
     *         
     * <br>
     * 業務共通のルートプロパティファイルが、ルートプロパティファイル自身を
     * 呼び出している場合は、読み飛ばされることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testApplicationInit08() throws Exception {
        // 前処理
        GlobalMessageResources resources = GlobalMessageResources.getInstance();

        // プロパティを書き換える。
        deleteProperty("application.messages");
        addProperty("application.messages", 
                    "jp/terasoluna/fw/web/struts/action/GlobalMessageResources_application-messages06");

        // 既存の業務共通メッセージを設定
        Map defaultMap = new HashMap();
        defaultMap.put("test.gb.message.01", "既存のＧＢテストメッセージ０１");
        UTUtil.setPrivateField(resources, "globalMessages", defaultMap);
        
        // テスト実施
        UTUtil.invokePrivate(resources, "applicationInit");

        // 判定
        Map appMap = (Map) UTUtil
            .getPrivateField(resources, "globalMessages");
        assertEquals(1, appMap.size());
        assertEquals("ＧＢテストメッセージ０１", 
                     appMap.get("test.gb.message.01"));
    }

    /**
     * testApplicationInit09()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(状態) PropertyUtil.getProperty("application.messages"):"GlobalMessageResources_application-messages07"<br>
     *         (状態) 業務共通メッセージリソース定義ファイル（application-messages.properties）:test.gb.message.01=ＧＢテストメッセージ０１<br>
     *                test.gb.message.02=ＧＢテストメッセージ０２<br>
     *                test.gb.message.03=ＧＢテストメッセージ０３<br>
     *                add.message.file.1=GlobalMessageResources_application-messages07_add1<br>
     *                add.message.file.2=GlobalMessageResources_application-messages07_add2<br>
     *                add.message.file.3=GlobalMessageResources_application-messages07_add3<br>
     *         (状態) 追加用の業務共通メッセージリソース定義ファイル:【GlobalMessageResources_application-messages07_add1】<br>
     *                test.gb.message.addition.01=追加用ＧＢテストメッセージ０１<br>
     *                【GlobalMessageResources_application-messages07_add2】<br>
     *                test.gb.message.addition.02=追加用ＧＢテストメッセージ０２<br>
     *                【GlobalMessageResources_application-messages07_add3】<br>
     *                test.gb.message.addition.03=追加用ＧＢテストメッセージ０３<br>
     *                test.gb.message.addition.04=追加用ＧＢテストメッセージ０４<br>
     *         (状態) globalMessages:["test.gb.message.01"->"既存のＧＢテストメッセージ０１"]<br>
     *         
     * <br>
     * 期待値：(状態変化) globalMessages:test.gb.message.01=ＧＢテストメッセージ０１<br>
     *                    test.gb.message.02=ＧＢテストメッセージ０２<br>
     *                    test.gb.message.03=ＧＢテストメッセージ０３<br>
     *                    test.gb.message.addition.01=追加用ＧＢテストメッセージ０１<br>
     *                    test.gb.message.addition.02=追加用ＧＢテストメッセージ０２<br>
     *                    test.gb.message.addition.03=追加用ＧＢテストメッセージ０３<br>
     *                    test.gb.message.addition.04=追加用ＧＢテストメッセージ０４<br>
     *         
     * <br>
     * 複数の外部ファイル、複数のルートメッセージを計7件取得することを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testApplicationInit09() throws Exception {
        // 前処理
        GlobalMessageResources resources = GlobalMessageResources.getInstance();

        // プロパティを書き換える。
        deleteProperty("application.messages");
        addProperty("application.messages", 
                    "jp/terasoluna/fw/web/struts/action/GlobalMessageResources_application-messages07");

        // 既存の業務共通メッセージを設定
        Map defaultMap = new HashMap();
        defaultMap.put("test.gb.message.01", "既存のＧＢテストメッセージ０１");
        UTUtil.setPrivateField(resources, "globalMessages", defaultMap);
        
        // テスト実施
        UTUtil.invokePrivate(resources, "applicationInit");

        // 判定
        Map appMap = (Map) UTUtil.getPrivateField(resources, "globalMessages");
        assertEquals(7, appMap.size());

        assertEquals("ＧＢテストメッセージ０１", appMap.get("test.gb.message.01"));
        assertEquals("ＧＢテストメッセージ０２", appMap.get("test.gb.message.02"));
        assertEquals("ＧＢテストメッセージ０３", appMap.get("test.gb.message.03"));
        assertEquals("追加用ＧＢテストメッセージ０１", appMap.get("test.gb.message.addition.01"));
        assertEquals("追加用ＧＢテストメッセージ０２", appMap.get("test.gb.message.addition.02"));
        assertEquals("追加用ＧＢテストメッセージ０３", appMap.get("test.gb.message.addition.03"));
        assertEquals("追加用ＧＢテストメッセージ０４", appMap.get("test.gb.message.addition.04"));
    }

    /**
     * testApplicationInit10()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(状態) PropertyUtil.getProperty("application.messages"):"GlobalMessageResources_application-messages03"<br>
     *         (状態) 業務共通メッセージリソース定義ファイル（application-messages.properties）:test.gb.message.01=ＧＢテストメッセージ０１<br>
     *                add.message.file.1=aaaaa<br>
     *         (状態) 追加用の業務共通メッセージリソース定義ファイル:存在しない<br>
     *         (状態) globalMessages:["test.gb.message.01"->"既存のＧＢテストメッセージ０１"]<br>
     *         
     * <br>
     * 期待値：(状態変化) globalMessages:["test.gb.message.01"="ＧＢテストメッセージ０１"]<br>
     *         (状態変化) ログ:【警告ログ】<br>
     *                    ＜メッセージ＞<br>
     *                    "aaaaa" is illegal."<br>
     *         
     * <br>
     * 追加用のファイル名が不正だった場合、警告ログを出し、
     * そのほかの業務共通メッセージリソース定義ファイル
     * （application-messages.properties）に設定されているメッセージリソースは
     * 正常に設定されていることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testApplicationInit10() throws Exception {
        // 前処理
        GlobalMessageResources resources = GlobalMessageResources.getInstance();

        // プロパティを書き換える。
        deleteProperty("application.messages");
        addProperty("application.messages", 
                    "jp/terasoluna/fw/web/struts/action/GlobalMessageResources_application-messages03");

        // 既存の業務共通メッセージを設定
        Map defaultMap = new HashMap();
        defaultMap.put("test.gb.message.01", "既存のＧＢテストメッセージ０１");
        UTUtil.setPrivateField(resources, "globalMessages", defaultMap);
        
        // テスト実施
        UTUtil.invokePrivate(resources, "applicationInit");

        // 判定
        Map appMap = (Map) UTUtil.getPrivateField(resources, "globalMessages");
        assertEquals(1, appMap.size());
        assertEquals("ＧＢテストメッセージ０１", appMap.get("test.gb.message.01"));
        assertTrue(LogUTUtil.checkWarn("\"aaaaa\" is illegal."));
    }

    /**
     * testGetMessage01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) key:"message"<br>
     *         (状態) fwMessages:空のMap<br>
     *         (状態) globalMessages:空のMap<br>
     *         
     * <br>
     * 期待値：(戻り値) String:null<br>
     *         
     * <br>
     * システムのメッセージ、業務共通メッセージが共に空のMapの時、
     * nullが返却されることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetMessage01() throws Exception {
        // 前処理
        GlobalMessageResources resources = GlobalMessageResources.getInstance();
        // システムメッセージ設定
        UTUtil.setPrivateField(resources, "fwMessages", new HashMap());
        // 業務共通メッセージ設定
        UTUtil.setPrivateField(resources, "globalMessages", new HashMap());

        // テスト実施
        // 判定
        Locale locale = null;
        assertNull(resources.getMessage(locale, "message"));
    }

    /**
     * testGetMessage02()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) key:"message.fw"<br>
     *         (状態) fwMessages:["message.fw"->"ＦＷメッセージ"]<br>
     *         (状態) globalMessages:["message.gb"->"業務メッセージ"]<br>
     *         
     * <br>
     * 期待値：(戻り値) String:"ＦＷメッセージ"<br>
     *         
     * <br>
     * システムメッセージに該当するメッセージが存在する時、
     * メッセージを返却することを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetMessage02() throws Exception {
        // 前処理
        GlobalMessageResources resources = GlobalMessageResources.getInstance();
        // システムメッセージ設定
        Map fwMessagesMap = new HashMap();
        fwMessagesMap.put("message.fw", "ＦＷメッセージ");
        UTUtil.setPrivateField(resources, "fwMessages", fwMessagesMap);

        // 業務共通メッセージ設定
        Map globalMessageMap = new HashMap();
        fwMessagesMap.put("message.gb", "業務メッセージ");
        UTUtil.setPrivateField(resources, "globalMessages", globalMessageMap);

        // テスト実施
        String result = resources.getMessage(Locale.getDefault(), "message.fw");

        // 判定
        // システムメッセージが取得されていること
        assertEquals("ＦＷメッセージ", result);
    }

    /**
     * testGetMessage03()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) key:"message.gb"<br>
     *         (状態) fwMessages:["message.fw"->"ＦＷメッセージ"]<br>
     *         (状態) globalMessages:["message.gb"->"業務メッセージ"]<br>
     *         
     * <br>
     * 期待値：(戻り値) String:"業務メッセージ"<br>
     *         
     * <br>
     * 業務メッセージに該当するメッセージが存在する時、
     * メッセージを返却することを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetMessage03() throws Exception {
        // 前処理
        GlobalMessageResources resources = GlobalMessageResources.getInstance();
        // システムメッセージ設定
        Map fwMessagesMap = new HashMap();
        fwMessagesMap.put("message.fw", "ＦＷメッセージ");
        UTUtil.setPrivateField(resources, "fwMessages", fwMessagesMap);

        // 業務共通メッセージ設定
        Map globalMessageMap = new HashMap();
        globalMessageMap.put("message.gb", "業務メッセージ");
        UTUtil.setPrivateField(resources, "globalMessages", globalMessageMap);

        // テスト実施
        Locale locale = null;
        String result = resources.getMessage(locale, "message.gb");

        // 判定
        // 業務メッセージが取得されていること
        assertEquals("業務メッセージ", result);
    }

    /**
     * testGetMessage04()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) key:"message"<br>
     *         (状態) fwMessages:["message"->"ＦＷメッセージ"]<br>
     *         (状態) globalMessages:["message"->"業務メッセージ"]<br>
     *         
     * <br>
     * 期待値：(戻り値) String:"業務メッセージ"<br>
     *         
     * <br>
     * 指定されたメッセージキーが、システムと業務共通の双方に存在する時、
     * 業務メッセージが返却されることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetMessage04() throws Exception {
        // 前処理
        GlobalMessageResources resources = GlobalMessageResources.getInstance();
        // システムメッセージ設定
        Map fwMessagesMap = new HashMap();
        fwMessagesMap.put("message", "ＦＷメッセージ");
        UTUtil.setPrivateField(resources, "fwMessages", fwMessagesMap);

        // 業務共通メッセージ設定
        Map globalMessageMap = new HashMap();
        fwMessagesMap.put("message", "業務メッセージ");
        UTUtil.setPrivateField(resources, "globalMessages", globalMessageMap);

        // テスト実施
        Locale locale = null;
        String result = resources.getMessage(locale, "message");

        // 判定
        // 業務メッセージが取得されていること
        assertEquals("業務メッセージ", result);
    }

    /**
     * testGetMessage05()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) key:null<br>
     *         (状態) fwMessages:["message.fw"->"ＦＷメッセージ"]<br>
     *         (状態) globalMessages:["message.gb"->"業務メッセージ"]<br>
     *         
     * <br>
     * 期待値：(戻り値) String:null<br>
     *         
     * <br>
     * 指定されたメッセージキーがnullの時、nullを返却することを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetMessage05() throws Exception {
        // 前処理
        GlobalMessageResources resources = GlobalMessageResources.getInstance();
        // システムメッセージ設定
        Map fwMessagesMap = new HashMap();
        fwMessagesMap.put("message.fw", "ＦＷメッセージ");
        UTUtil.setPrivateField(resources, "fwMessages", fwMessagesMap);

        // 業務共通メッセージ設定
        Map globalMessageMap = new HashMap();
        globalMessageMap.put("message.gb", "業務メッセージ");
        UTUtil.setPrivateField(resources, "globalMessages", globalMessageMap);

        // テスト実施
        // 判定
        Locale locale = null;
        assertNull(resources.getMessage(locale, null));
    }

    /**
     * シングルトンインスタンスを初期状態にする。
     * 
     */
    @Override
    protected void setUpData() throws Exception {

        UTUtil.setPrivateField(GlobalMessageResources.class,
                               "globalMessageResources",
                               null);
        
    }

    /**
     * シングルトンインスタンスを初期状態に戻す。
     */
    @Override
    protected void cleanUpData() throws Exception {

        UTUtil.setPrivateField(GlobalMessageResources.class,
                "globalMessageResources",
                null);
        
    }

}
