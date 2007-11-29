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

package jp.terasoluna.fw.web.struts.plugins;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;

import jp.terasoluna.fw.service.thin.BLogicIO;
import jp.terasoluna.fw.service.thin.BLogicProperty;
import jp.terasoluna.fw.service.thin.BLogicResources;
import jp.terasoluna.fw.util.ClassLoadException;
import jp.terasoluna.utlib.LogUTUtil;
import jp.terasoluna.utlib.UTUtil;
import junit.framework.TestCase;

import org.apache.commons.digester.xmlrules.XmlLoadException;
import org.apache.struts.config.ModuleConfig;
import org.apache.struts.config.impl.ModuleConfigImpl;
import org.xml.sax.SAXException;

/**
 * {@link jp.terasoluna.fw.web.struts.plugins.BLogicIOPlugIn} クラスの
 * ブラックボックステスト。
 *
 * <p>
 * <h4>【クラスの概要】</h4>
 * ビジネスロジック入出力の設定情報をロードするプラグイン。 <br>
 * StrutsのPlugIn機能を使用し、サーブレット初期化時にビジネスロジック入出力の
 * 設定を読み込み、サーブレットコンテキストに保存する。<br>
 * <br>
 * 前提条件：<br>
 * init()メソッドは、initResources()、initParser()の試験を内包する。<br>
 * 前提条件で指定するxmlファイルが格納されていることが前提である。<br>
 * また、引数のServlet、ModuleConfigとその属性のモジュール名は
 * nullであることはありえない。
 * <p>
 *
 * @see jp.terasoluna.fw.web.struts.plugins.BLogicIOPlugIn
 */
public class BLogicIOPlugInTest extends TestCase {

    /**
     * このテストケースを実行する為の GUI アプリケーションを起動する。
     *
     * @param args
     *            java コマンドに設定されたパラメータ
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(BLogicIOPlugInTest.class);
    }

    /**
     * BLogicIOPlugIn。
     */
    private BLogicIOPlugIn plugin = null;

    /**
     * 初期化処理を行う。
     *
     * @throws Exception
     *             このメソッドで発生した例外
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();

        // BLogicIOPlugInインスタンス
        plugin = new BLogicIOPlugIn();

        UTUtil.setPrivateField(BLogicIOPlugIn.class, "digester", null);
    }

    /**
     * 終了処理を行う。
     *
     * @throws Exception
     *             このメソッドで発生した例外
     * @see junit.framework.TestCase#tearDown()
     */
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * コンストラクタ。
     *
     * @param name
     *            このテストケースの名前。
     */
    public BLogicIOPlugInTest(String name) {
        super(name);
    }

    /**
     * testInit01()
     * <br><br>
     *
     *  (異常系)
     * <br>
     * 観点：CG
     * <br><br>
     * 入力値：(引数) servlet:not null<br>
     *         (引数) config:モジュール名(属性)=""<br>
     *         (状態) rules（Digesterルールファイル名）:null<br>
     *         (状態) blogic-io（業務ロジック入出力情報マッピング定義ファイル名）:"BLogicIOPlugInTest-blogic-io.xml"<br>
     *         (状態) mapperStr（業務ロジック入出力情報反映クラス名）:null<br>
     *         (状態) digester:null<br>
     *
     * <br>
     * 期待値：(状態変化) 例外:ServletException<br>
     *                    ラップした例外：XmlLoadException<br>
     *                    ※デフォルトのルールファイルが存在しない為、発生する例外<br>
     *         (状態変化) ログ:＜ログ＞<br>
     *                    エラーログ：""<br>
     *                    例外：XmlLoadException<br>
     *         (状態変化) digesterRules:"/WEB-INF/blogic-io-rules.xml"<br>
     *
     * <br>
     * Digesterのルールファイル名が指定されていない場合、サーブレットコンテキストのリソースにはデフォルトのルールファイルが登録されていること。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testInit01() throws Exception {
        // テストデータ設定
        // BLogic定義のルールファイル名
        UTUtil.setPrivateField(plugin, "digesterRules", null);
        // BLogic定義ファイル名
        UTUtil.setPrivateField(plugin, "resources", BLogicIOPlugInTest.class
                .getResource("BLogicIOPlugInTest-blogic-io.xml").getPath());

        // マッパークラス名
        UTUtil.setPrivateField(plugin, "mapperStr", null);

        // アクションサーブレット作成
        BLogicIOPlugIn_ActionServletStub01 servlet = new BLogicIOPlugIn_ActionServletStub01();

        // モジュール設定情報
        ModuleConfig config = new ModuleConfigImpl("");

        // テスト実行
        try {
            plugin.init(servlet, config);
            fail();
        } catch (ServletException e) {
            // テスト結果確認
            assertEquals(XmlLoadException.class.getName(), e.getRootCause()
                    .getClass().getName());
            assertEquals("/WEB-INF/blogic-io-rules.xml", UTUtil
                    .getPrivateField(plugin, "digesterRules"));
            assertTrue(LogUTUtil.checkError("", e.getRootCause()));
            // サーブレットコンテキストに登録されているリソースが
            // デフォルトのルールファイルであること。
            BLogicIOPlugIn_MockServletContextStub01 ctx = (BLogicIOPlugIn_MockServletContextStub01) servlet
                    .getServletContext();
            assertEquals("/WEB-INF/blogic-io-rules.xml", ctx
                    .getCalledResources());

        }
    }

    /**
     * testInit02()
     * <br><br>
     *
     * (異常系)
     * <br>
     * 観点：CG
     * <br><br>
     * 入力値：(引数) servlet:not null<br>
     *         (引数) config:モジュール名(属性)=""<br>
     *         (状態) rules（Digesterルールファイル名）:""(空文字)<br>
     *         (状態) blogic-io（業務ロジック入出力情報マッピング定義ファイル名）:"BLogicIOPlugInTest-blogic-io.xml"<br>
     *         (状態) mapperStr（業務ロジック入出力情報反映クラス名）:null<br>
     *         (状態) digester:null<br>
     *
     * <br>
     * 期待値：(状態変化) 例外:ServletException<br>
     *                    ラップした例外：XmlLoadException<br>
     *         (状態変化) ログ:＜ログ＞<br>
     *                    エラーログ：""<br>
     *                    例外：XmlLoadException<br>
     *
     * <br>
     * ルールファイル名が空文字の場合、ServletExceptionがスローされること。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testInit02() throws Exception {
        // テストデータ設定
        // モジュール設定情報
        ModuleConfig config = new ModuleConfigImpl("");
        // BLogic定義のルールファイル名
        UTUtil.setPrivateField(plugin, "digesterRules", "");
        // BLogic定義ファイル名
        UTUtil.setPrivateField(plugin, "resources", BLogicIOPlugInTest.class
                .getResource("BLogicIOPlugInTest-blogic-io.xml").getPath());
        // マッパークラス名
        UTUtil.setPrivateField(plugin, "mapperStr", null);

        // アクションサーブレット作成
        BLogicIOPlugIn_ActionServletStub01 servlet = new BLogicIOPlugIn_ActionServletStub01();

        // テスト実行
        try {
            plugin.init(servlet, config);
            fail();
        } catch (ServletException e) {
            // テスト結果確認
            assertEquals(XmlLoadException.class.getName(), e.getRootCause()
                    .getClass().getName());
            assertTrue(LogUTUtil.checkError("", e.getRootCause()));
        }
    }

    /**
     * testInit03()
     * <br><br>
     *
     * (異常系)
     * <br>
     * 観点：CG
     * <br><br>
     * 入力値：(引数) servlet:not null<br>
     *         (引数) config:モジュール名(属性)=""<br>
     *         (状態) rules（Digesterルールファイル名）:"/nothing.xml"（存在しないルールファイル名）<br>
     *         (状態) blogic-io（業務ロジック入出力情報マッピング定義ファイル名）:"BLogicIOPlugInTest-blogic-io.xml"<br>
     *         (状態) mapperStr（業務ロジック入出力情報反映クラス名）:null<br>
     *         (状態) digester:null<br>
     *
     * <br>
     * 期待値：(状態変化) 例外:ServletException<br>
     *                    ラップした例外：XmlLoadException<br>
     *         (状態変化) ログ:＜ログ＞<br>
     *                    エラーログ：""<br>
     *                    例外：XmlLoadException<br>
     *
     * <br>
     * 存在しないルールファイル名が指定されている場合、ServletExceptionがスローされること。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testInit03() throws Exception {
        // テストデータ設定
        // モジュール設定情報
        ModuleConfig config = new ModuleConfigImpl("");
        // BLogic定義のルールファイル名
        UTUtil.setPrivateField(plugin, "digesterRules", "/nothing.xml");
        // BLogic定義ファイル名
        UTUtil.setPrivateField(plugin, "resources", BLogicIOPlugInTest.class
                .getResource("BLogicIOPlugInTest-blogic-io.xml").getPath());
        // マッパークラス名
        UTUtil.setPrivateField(plugin, "mapperStr", null);

        // アクションサーブレット作成
        BLogicIOPlugIn_ActionServletStub01 servlet = new BLogicIOPlugIn_ActionServletStub01();

        // テスト実行
        try {
            plugin.init(servlet, config);
            fail();
        } catch (ServletException e) {
            // テスト結果確認
            assertEquals(XmlLoadException.class.getName(), e.getRootCause()
                    .getClass().getName());
            assertTrue(LogUTUtil.checkError("", e.getRootCause()));
        }
    }

    /**
     * testInit04()
     * <br><br>
     *
     * (異常系)
     * <br>
     * 観点：CG
     * <br><br>
     * 入力値：(引数) servlet:not null<br>
     *         (引数) config:モジュール名(属性)=""<br>
     *         (状態) rules（Digesterルールファイル名）:"BLogicIOPlugInTest-blogic-io-empty.xml"（パースエラーが発生するファイル名）<br>
     *         (状態) blogic-io（業務ロジック入出力情報マッピング定義ファイル名）:"BLogicIOPlugInTest-blogic-io.xml"<br>
     *         (状態) mapperStr（業務ロジック入出力情報反映クラス名）:null<br>
     *         (状態) digester:null<br>
     *
     * <br>
     * 期待値：(状態変化) 例外:ServletException<br>
     *                    ラップした例外：XmlLoadException<br>
     *         (状態変化) ログ:＜ログ＞<br>
     *                    エラーログ：""<br>
     *                    例外：XmlLoadException<br>
     *
     * <br>
     * パースエラーが発生するルールファイル名が指定された場合、ServletExceptionがスローされること。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testInit04() throws Exception {
        // テストデータ設定
        // モジュール設定情報
        ModuleConfig config = new ModuleConfigImpl("");

        // パースエラーを発生させるファイル名（rulesではない）
        UTUtil.setPrivateField(plugin, "digesterRules",
                BLogicIOPlugInTest.class.getResource(
                        "BLogicIOPlugInTest-blogic-io-empty.xml").getPath());
        // BLogic定義ファイル名
        UTUtil.setPrivateField(plugin, "resources", BLogicIOPlugInTest.class
                .getResource("BLogicIOPlugInTest-blogic-io.xml").getPath());
        // マッパークラス名
        UTUtil.setPrivateField(plugin, "mapperStr", null);

        // アクションサーブレット作成
        BLogicIOPlugIn_ActionServletStub01 servlet = new BLogicIOPlugIn_ActionServletStub01();

        // テスト実行
        try {
            plugin.init(servlet, config);
            fail();
        } catch (ServletException e) {
            // テスト結果確認
            assertEquals(XmlLoadException.class.getName(), e.getRootCause()
                    .getClass().getName());
            assertTrue(LogUTUtil.checkError("", e.getRootCause()));
        }
    }

    /**
     * testInit05()
     * <br><br>
     *
     * (異常系)
     * <br>
     * 観点：G
     * <br><br>
     * 入力値：(引数) servlet:not null<br>
     *         (引数) config:モジュール名(属性)=""<br>
     *         (状態) rules（Digesterルールファイル名）:"BLogicIOPlugInTest-blogic-io-rules.xml"<br>
     *         (状態) blogic-io（業務ロジック入出力情報マッピング定義ファイル名）:"BLogicIOPlugInTest-blogic-io.xml"<br>
     *         (状態) mapperStr（業務ロジック入出力情報反映クラス名）:null<br>
     *         (状態) digester:parse()メソッドでIOExceptionをスローするDigester<br>
     *
     * <br>
     * 期待値：(状態変化) 例外:ServletException<br>
     *                    ラップした例外：IOException<br>
     *         (状態変化) ログ:＜ログ＞<br>
     *                    エラーログ：""<br>
     *                    例外：IOException<br>
     *
     * <br>
     * IOExceptionが発生した場合、ServletExceptionでラップしてスローされること。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testInit05() throws Exception {
        // テストデータ設定
        // モジュール設定情報
        ModuleConfig config = new ModuleConfigImpl("");

        // BLogic定義ルールファイル名
        UTUtil.setPrivateField(plugin, "digesterRules",
                BLogicIOPlugInTest.class.getResource(
                        "BLogicIOPlugInTest-blogic-io-rules.xml").getPath());
        // BLogic定義ファイル名
        UTUtil.setPrivateField(plugin, "resources", BLogicIOPlugInTest.class
                .getResource("BLogicIOPlugInTest-blogic-io.xml").getPath());
        // マッパークラス名
        UTUtil.setPrivateField(plugin, "mapperStr",
                "jp.terasoluna.fw.web.struts.plugins"
                        + ".BLogicIOPlugIn_BLogicMapperStub01");
        UTUtil.setPrivateField(plugin, "mapperStr", null);

        // アクションサーブレット作成
        BLogicIOPlugIn_ActionServletStub01 servlet = new BLogicIOPlugIn_ActionServletStub01();

        // Digester作成（IOExceptionが発生する）
        BLogicIOPlugIn_DigesterStub01 digester = new BLogicIOPlugIn_DigesterStub01();
        UTUtil.setPrivateField(BLogicIOPlugIn.class, "digester", digester);

        // テスト実行
        try {
            plugin.init(servlet, config);
            fail();
        } catch (ServletException e) {
            // テスト結果確認
            assertEquals(IOException.class.getName(), e.getRootCause()
                    .getClass().getName());
            assertTrue(LogUTUtil.checkError("", e.getRootCause()));
        }
    }

    /**
     * testInit06()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) servlet:not null<br>
     *         (引数) config:モジュール名(属性)=""<br>
     *         (状態) rules（Digesterルールファイル名）:"BLogicIOPlugInTest-blogic-io-rules.xml"<br>
     *         (状態) blogic-io（業務ロジック入出力情報マッピング定義ファイル名）:"BLogicIOPlugInTest-blogic-io.xml"<br>
     *         (状態) mapperStr（業務ロジック入出力情報反映クラス名）:null<br>
     *         (状態) digester:null<br>
     *
     * <br>
     * 期待値：(状態変化) resources:サーブレットコンテキストのBLOGIC_RESOURCESをキーにしてBLogicResourcesインスタンスが格納されること。<br>
     *         (状態変化) mapper:サーブレットコンテキストのBLOGIC_MAPPERをキーにしてBLogicMapperインスタンスが格納されること。<br>
     *
     * <br>
     * マッパークラス名がnullの時、デフォルトのマッパークラス"jp.terasoluna.fw.service.thin.BLogicMapper"のインスタンスが生成されること。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testInit06() throws Exception {
        // テストデータ設定
        // モジュール設定情報
        ModuleConfig config = new ModuleConfigImpl("");

        // BLogic定義ルールファイル名
        UTUtil.setPrivateField(plugin, "digesterRules",
                BLogicIOPlugInTest.class.getResource(
                        "BLogicIOPlugInTest-blogic-io-rules.xml").getPath());
        // BLogic定義ファイル名
        UTUtil.setPrivateField(plugin, "resources", BLogicIOPlugInTest.class
                .getResource("BLogicIOPlugInTest-blogic-io.xml").getPath());
        // マッパークラス名
        UTUtil.setPrivateField(plugin, "mapperStr", null);

        // アクションサーブレット作成
        BLogicIOPlugIn_ActionServletStub01 servlet = new BLogicIOPlugIn_ActionServletStub01();

        // テスト実行
        plugin.init(servlet, config);

        // サーブレットコンテキスト取得
        BLogicIOPlugIn_MockServletContextStub01 ctx = (BLogicIOPlugIn_MockServletContextStub01) servlet
                .getServletContext();

        // サーブレットコンテキスト内にデフォルトのBLogicMapperインスタンスが
        // 格納されていること
        assertEquals("jp.terasoluna.fw.service.thin.BLogicMapper",
                ctx.getAttribute("BLOGIC_MAPPER").getClass().getName());

        // サーブレットコンテキスト内にBLogicResourcesインスタンスが
        // 格納されていること
        assertTrue(ctx.getAttribute("BLOGIC_RESOURCES") instanceof BLogicResources);

        // サーブレットコンテキストに登録されているリソースが
        // 指定したルールファイルであること。
        assertEquals(BLogicIOPlugInTest.class.getResource(
                "BLogicIOPlugInTest-blogic-io-rules.xml").getPath(), ctx
                .getCalledResources());
    }

    /**
     * testInit07()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) servlet:not null<br>
     *         (引数) config:モジュール名(属性)=""<br>
     *         (状態) rules（Digesterルールファイル名）:"BLogicIOPlugInTest-blogic-io-rules.xml"<br>
     *         (状態) blogic-io（業務ロジック入出力情報マッピング定義ファイル名）:"BLogicIOPlugInTest-blogic-io.xml"<br>
     *         (状態) mapperStr（業務ロジック入出力情報反映クラス名）:BLogicMapperを継承したスタブ名指定：<br>
     *                "jp.terasoluna.fw.web.struts.plugins.BLogicMapperStub"<br>
     *         (状態) digester:null<br>
     *
     * <br>
     * 期待値：(状態変化) resources:サーブレットコンテキストのBLOGIC_RESOURCESをキーにしてBLogicResourcesインスタンスが格納されること。<br>
     *         (状態変化) mapper:サーブレットコンテキストのBLOGIC_MAPPERをキーにしてBLogicMapperStubインスタンスが格納されること。<br>
     *
     * <br>
     * マッパークラス名がBLogicMapperを継承したクラスの時、このクラスのインスタンスがServletContextに登録されていること。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testInit07() throws Exception {
        // テストデータ設定
        // モジュール設定情報
        ModuleConfig config = new ModuleConfigImpl("");

        // BLogic定義ルールファイル名
        UTUtil.setPrivateField(plugin, "digesterRules",
                BLogicIOPlugInTest.class.getResource(
                        "BLogicIOPlugInTest-blogic-io-rules.xml").getPath());
        // BLogic定義ファイル名
        UTUtil.setPrivateField(plugin, "resources", BLogicIOPlugInTest.class
                .getResource("BLogicIOPlugInTest-blogic-io.xml").getPath());
        // マッパークラス名
        UTUtil.setPrivateField(plugin, "mapperStr",
                "jp.terasoluna.fw.web.struts.plugins"
                        + ".BLogicIOPlugIn_BLogicMapperStub01");

        // アクションサーブレット作成
        BLogicIOPlugIn_ActionServletStub01 servlet = new BLogicIOPlugIn_ActionServletStub01();

        // テスト実行
        plugin.init(servlet, config);

        // サーブレットコンテキスト取得
        BLogicIOPlugIn_MockServletContextStub01 ctx = (BLogicIOPlugIn_MockServletContextStub01) servlet
                .getServletContext();

        // サーブレットコンテキスト内にプラグインで指定した
        // BLogicMapper継承インスタンスが格納されていること
        assertEquals("jp.terasoluna.fw.web.struts.plugins"
                + ".BLogicIOPlugIn_BLogicMapperStub01", ctx.getAttribute(
                "BLOGIC_MAPPER").getClass().getName());

        // サーブレットコンテキスト内にBLogicResourcesインスタンスが
        // 格納されていること
        assertTrue(ctx.getAttribute("BLOGIC_RESOURCES") instanceof BLogicResources);

        // サーブレットコンテキストに登録されているリソースが
        // 指定したルールファイルであること。
        assertEquals(BLogicIOPlugInTest.class.getResource(
                "BLogicIOPlugInTest-blogic-io-rules.xml").getPath(), ctx
                .getCalledResources());
    }

    /**
     * testInit08()
     * <br><br>
     *
     * (異常系)
     * <br>
     * 観点：CG
     * <br><br>
     * 入力値：(引数) servlet:not null<br>
     *         (引数) config:モジュール名(属性)=""<br>
     *         (状態) rules（Digesterルールファイル名）:"BLogicIOPlugInTest-blogic-io-rules.xml"<br>
     *         (状態) blogic-io（業務ロジック入出力情報マッピング定義ファイル名）:"BLogicIOPlugInTest-blogic-io.xml"<br>
     *         (状態) mapperStr（業務ロジック入出力情報反映クラス名）:BLogicMapper、AbstractBLogicMapperを継承していないクラス名を指定<br>
     *         (状態) digester:null<br>
     *
     * <br>
     * 期待値：(状態変化) 例外:ServletException<br>
     *                    ラップした例外：ClassCastException<br>
     *         (状態変化) ログ:＜ログ＞<br>
     *                    エラーログ：""<br>
     *                    例外：ClassCastException<br>
     *
     * <br>
     * マッパークラス名がBLogicMapper、AbstractBLogicMapperを継承したクラスでない時、ServletExceptionがスローされること。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testInit08() throws Exception {
        // テストデータ設定
        // モジュール設定情報
        ModuleConfig config = new ModuleConfigImpl("");

        // BLogic定義ルールファイル名
        UTUtil.setPrivateField(plugin, "digesterRules",
                BLogicIOPlugInTest.class.getResource(
                        "BLogicIOPlugInTest-blogic-io-rules.xml").getPath());
        // BLogic定義ファイル名
        UTUtil.setPrivateField(plugin, "resources", BLogicIOPlugInTest.class
                .getResource("BLogicIOPlugInTest-blogic-io.xml").getPath());
        // マッパークラス名(BLogicMapperクラスではない）
        UTUtil.setPrivateField(plugin, "mapperStr",
                "jp.terasoluna.fw.web.struts.plugins."
                        + "BLogicIOPlugIn_BLogicMapperStub02");

        // アクションサーブレット作成
        BLogicIOPlugIn_ActionServletStub01 servlet = new BLogicIOPlugIn_ActionServletStub01();

        // テスト実行
        try {
            plugin.init(servlet, config);
            fail();
        } catch (ServletException e) {
            // テスト結果確認
            assertEquals(ClassCastException.class.getName(), e.getRootCause()
                    .getClass().getName());
            assertTrue(LogUTUtil.checkError("", e.getRootCause()));
        }
    }

    /**
     * testInit09()
     * <br><br>
     *
     * (異常系)
     * <br>
     * 観点：CG
     * <br><br>
     * 入力値：(引数) servlet:not null<br>
     *         (引数) config:モジュール名(属性)=""<br>
     *         (状態) rules（Digesterルールファイル名）:"BLogicIOPlugInTest-blogic-io-rules.xml"<br>
     *         (状態) blogic-io（業務ロジック入出力情報マッピング定義ファイル名）:"BLogicIOPlugInTest-blogic-io.xml"<br>
     *         (状態) mapperStr（業務ロジック入出力情報反映クラス名）:存在しないクラス名を指定<br>
     *         (状態) digester:null<br>
     *
     * <br>
     * 期待値：(状態変化) 例外:ServletException<br>
     *                    ラップした例外：ClassLoadException<br>
     *         (状態変化) ログ:＜ログ＞<br>
     *                    エラーログ：""<br>
     *                    例外：ClassLoadException<br>
     *
     * <br>
     * 存在しないマッパークラス名を指定した場合、ServletExceptionがスローされること。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testInit09() throws Exception {
        // テストデータ設定
        // モジュール設定情報
        ModuleConfig config = new ModuleConfigImpl("");

        // BLogic定義ルールファイル名
        UTUtil.setPrivateField(plugin, "digesterRules",
                BLogicIOPlugInTest.class.getResource(
                        "BLogicIOPlugInTest-blogic-io-rules.xml").getPath());
        // BLogic定義ファイル名
        UTUtil.setPrivateField(plugin, "resources", BLogicIOPlugInTest.class
                .getResource("BLogicIOPlugInTest-blogic-io.xml").getPath());
        // マッパークラス名(存在しないクラス名）
        UTUtil.setPrivateField(plugin, "mapperStr", "nothing.NothingClass");

        // アクションサーブレット作成
        BLogicIOPlugIn_ActionServletStub01 servlet = new BLogicIOPlugIn_ActionServletStub01();

        // テスト実行
        try {
            plugin.init(servlet, config);
            fail();
        } catch (ServletException e) {
            // テスト結果確認
            assertEquals(ClassLoadException.class.getName(), e.getRootCause()
                    .getClass().getName());
            assertTrue(LogUTUtil.checkError("", e.getRootCause()));
        }
    }

    /**
     * testInit10()
     * <br><br>
     *
     * (異常系)
     * <br>
     * 観点：CG
     * <br><br>
     * 入力値：(引数) servlet:not null<br>
     *         (引数) config:モジュール名(属性)=""<br>
     *         (状態) rules（Digesterルールファイル名）:"BLogicIOPlugInTest-blogic-io-rules.xml"<br>
     *         (状態) blogic-io（業務ロジック入出力情報マッピング定義ファイル名）:"BLogicIOPlugInTest-blogic-io.xml"<br>
     *         (状態) mapperStr（業務ロジック入出力情報反映クラス名）:""(空文字)<br>
     *         (状態) digester:null<br>
     *
     * <br>
     * 期待値：(状態変化) 例外:ServletException<br>
     *                    ラップした例外：ClassLoadException<br>
     *         (状態変化) ログ:＜ログ＞<br>
     *                    エラーログ：""<br>
     *                    例外：ClassLoadException<br>
     *
     * <br>
     * マッパークラスが空文字の時、ServletExceptionがスローされること。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testInit10() throws Exception {
        // テストデータ設定
        // モジュール設定情報
        ModuleConfig config = new ModuleConfigImpl("");

        // BLogic定義ルールファイル名
        UTUtil.setPrivateField(plugin, "digesterRules",
                BLogicIOPlugInTest.class.getResource(
                        "BLogicIOPlugInTest-blogic-io-rules.xml").getPath());
        // BLogic定義ファイル名
        UTUtil.setPrivateField(plugin, "resources", BLogicIOPlugInTest.class
                .getResource("BLogicIOPlugInTest-blogic-io.xml").getPath());
        // マッパークラス名(空文字）
        UTUtil.setPrivateField(plugin, "mapperStr", "");

        // アクションサーブレット作成
        BLogicIOPlugIn_ActionServletStub01 servlet = new BLogicIOPlugIn_ActionServletStub01();

        // テスト実行
        try {
            plugin.init(servlet, config);
            fail();
        } catch (ServletException e) {
            // テスト結果確認
            assertEquals(ClassLoadException.class.getName(), e.getRootCause()
                    .getClass().getName());
            assertTrue(LogUTUtil.checkError("", e.getRootCause()));
        }
    }

    /**
     * testInit11()
     * <br><br>
     *
     * (異常系)
     * <br>
     * 観点：CG
     * <br><br>
     * 入力値：(引数) servlet:not null<br>
     *         (引数) config:モジュール名(属性)=""<br>
     *         (状態) rules（Digesterルールファイル名）:"BLogicIOPlugInTest-blogic-io-rules.xml"<br>
     *         (状態) blogic-io（業務ロジック入出力情報マッピング定義ファイル名）:null<br>
     *         (状態) mapperStr（業務ロジック入出力情報反映クラス名）:null<br>
     *         (状態) digester:null<br>
     *
     * <br>
     * 期待値：(状態変化) 例外:ServletException<br>
     *                    メッセージ："resources file location is not specified"<br>
     *         (状態変化) ログ:＜ログ＞<br>
     *                    エラーログ："resources file location is not specified"<br>
     *
     * <br>
     * 業務ロジック入出力情報マッピング定義ファイル名が指定されていない場合、ServletExceptionがスローされること。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testInit11() throws Exception {
        // テストデータ設定
        // モジュール設定情報
        ModuleConfig config = new ModuleConfigImpl("");

        // BLogic定義ルールファイル名
        UTUtil.setPrivateField(plugin, "digesterRules",
                BLogicIOPlugInTest.class.getResource(
                        "BLogicIOPlugInTest-blogic-io-rules.xml").getPath());
        // BLogic定義ファイル名(null)
        UTUtil.setPrivateField(plugin, "resources", "");
        // マッパークラス名
        UTUtil.setPrivateField(plugin, "mapperStr", null);

        // アクションサーブレット作成
        BLogicIOPlugIn_ActionServletStub01 servlet = new BLogicIOPlugIn_ActionServletStub01();

        // テスト実行
        try {
            plugin.init(servlet, config);
            fail();
        } catch (IllegalArgumentException e) {
            // テスト結果確認
            // メッセージ確認
            assertEquals("errors.resources.file", e
                    .getMessage());
            assertTrue(LogUTUtil
                    .checkError("resources file location is not specified"));
           }
    }

    /**
     * testInit12()
     * <br><br>
     *
     * (異常系)
     * <br>
     * 観点：CG
     * <br><br>
     * 入力値：(引数) servlet:not null<br>
     *         (引数) config:モジュール名(属性)=""<br>
     *         (状態) rules（Digesterルールファイル名）:"BLogicIOPlugInTest-blogic-io-rules.xml"<br>
     *         (状態) blogic-io（業務ロジック入出力情報マッピング定義ファイル名）:"nothing.xml"(存在しないファイル名)<br>
     *         (状態) mapperStr（業務ロジック入出力情報反映クラス名）:null<br>
     *         (状態) digester:null<br>
     *
     * <br>
     * 期待値：(状態変化) 例外:ServletException<br>
     *                    ラップした例外：MalformedURLException<br>
     *         (状態変化) ログ:＜ログ＞<br>
     *                    エラーログ：""<br>
     *                    例外：MalformedURLException<br>
     *
     * <br>
     * 存在しない業務ロジック入出力情報マッピング定義ファイル名が指定されている場合、ServletExceptionがスローされること。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testInit12() throws Exception {
        // テストデータ設定
        // モジュール設定情報
        ModuleConfig config = new ModuleConfigImpl("");

        // BLogic定義ルールファイル名
        UTUtil.setPrivateField(plugin, "digesterRules",
                BLogicIOPlugInTest.class.getResource(
                        "BLogicIOPlugInTest-blogic-io-rules.xml").getPath());
        // BLogic定義ファイル名(存在しないファイル名)
        UTUtil.setPrivateField(plugin, "resources", "nothing.xml");
        // マッパークラス名
        UTUtil.setPrivateField(plugin, "mapperStr", null);

        // アクションサーブレット作成
        BLogicIOPlugIn_ActionServletStub01 servlet = new BLogicIOPlugIn_ActionServletStub01();

        // テスト実行
        try {
            plugin.init(servlet, config);
            fail();
        } catch (ServletException e) {
            // テスト結果確認
            assertEquals(MalformedURLException.class.getName(), e
                    .getRootCause().getClass().getName());
            assertTrue(LogUTUtil.checkError("", e.getRootCause()));
        }
    }

    /**
     * testInit13()
     * <br><br>
     *
     * (異常系)
     * <br>
     * 観点：CG
     * <br><br>
     * 入力値：(引数) servlet:not null<br>
     *         (引数) config:モジュール名(属性)=""<br>
     *         (状態) rules（Digesterルールファイル名）:"BLogicIOPlugInTest-blogic-io-rules.xml"<br>
     *         (状態) blogic-io（業務ロジック入出力情報マッピング定義ファイル名）:""(空文字)<br>
     *         (状態) mapperStr（業務ロジック入出力情報反映クラス名）:null<br>
     *         (状態) digester:null<br>
     *
     * <br>
     * 期待値：(状態変化) 例外:ServletException<br>
     *                    メッセージ："resources file location is not specified"<br>
     *         (状態変化) ログ:＜ログ＞<br>
     *                    エラーログ："resources file location is not specified"<br>
     *
     * <br>
     * 業務ロジック入出力情報マッピング定義ファイル名が空文字の場合、ServletExceptionがスローされること。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testInit13() throws Exception {
        // テストデータ設定
        // モジュール設定情報
        ModuleConfig config = new ModuleConfigImpl("");

        // BLogic定義ルールファイル名
        UTUtil.setPrivateField(plugin, "digesterRules",
                BLogicIOPlugInTest.class.getResource(
                        "BLogicIOPlugInTest-blogic-io-rules.xml").getPath());
        // BLogic定義ファイル名(空文字)
        UTUtil.setPrivateField(plugin, "resources", "");
        // マッパークラス名
        UTUtil.setPrivateField(plugin, "mapperStr", null);

        // アクションサーブレット作成
        BLogicIOPlugIn_ActionServletStub01 servlet = new BLogicIOPlugIn_ActionServletStub01();

        // テスト実行
        try {
            plugin.init(servlet, config);
            fail();
        } catch (IllegalArgumentException e) {
            // テスト結果確認
            // メッセージ確認
            assertEquals("errors.resources.file", e
                    .getMessage());
            assertTrue(LogUTUtil
                    .checkError("resources file location is not specified"));
        }
    }

    /**
     * testInit14()
     * <br><br>
     *
     * (異常系)
     * <br>
     * 観点：CG
     * <br><br>
     * 入力値：(引数) servlet:not null<br>
     *         (引数) config:モジュール名(属性)=""<br>
     *         (状態) rules（Digesterルールファイル名）:"BLogicIOPlugInTest-blogic-io-rules.xml"<br>
     *         (状態) blogic-io（業務ロジック入出力情報マッピング定義ファイル名）:"BLogicIOPlugInTest-error-blogic-io.xml"(パースエラーが発生するファイル名)<br>
     *         (状態) mapperStr（業務ロジック入出力情報反映クラス名）:null<br>
     *         (状態) digester:null<br>
     *
     * <br>
     * 期待値：(状態変化) 例外:ServletException<br>
     *                    ラップした例外：<br>
     *                    SAXException<br>
     *         (状態変化) ログ:＜ログ＞<br>
     *                    エラーログ：""<br>
     *                    例外：SAXException<br>
     *
     * <br>
     * パースエラーが発生する業務ロジック入出力情報マッピング定義ファイル名が指定された場合、ServletExceptionがスローされること。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testInit14() throws Exception {
        // テストデータ設定
        // モジュール設定情報
        ModuleConfig config = new ModuleConfigImpl("");

        // BLogic定義ルールファイル名
        UTUtil.setPrivateField(plugin, "digesterRules",
                BLogicIOPlugInTest.class.getResource(
                        "BLogicIOPlugInTest-blogic-io-rules.xml").getPath());
        // BLogic定義ファイル名(パースエラー発生）
        UTUtil.setPrivateField(plugin, "resources", BLogicIOPlugInTest.class
                .getResource("BLogicIOPlugInTest-error-blogic-io.xml")
                .getPath());
        // マッパークラス名
        UTUtil.setPrivateField(plugin, "mapperStr", null);

        // アクションサーブレット作成
        BLogicIOPlugIn_ActionServletStub01 servlet = new BLogicIOPlugIn_ActionServletStub01();

        // テスト実行
        try {
            plugin.init(servlet, config);
            fail();
        } catch (ServletException e) {
            // テスト結果確認
            // 実際この場合は、SAXParseExceptionが発生するが、catchはSAXExceptionなのでinstanceofでチェックする
            assertTrue(e.getRootCause() instanceof SAXException);
            assertTrue(LogUTUtil.checkError("", e.getRootCause()));
        }
    }

    /**
     * testInit15()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) servlet:not null<br>
     *         (引数) config:モジュール名(属性)="/sub1"<br>
     *         (状態) rules（Digesterルールファイル名）:"BLogicIOPlugInTest-blogic-io-rules.xml"<br>
     *         (状態) blogic-io（業務ロジック入出力情報マッピング定義ファイル名）:"BLogicIOPlugInTest-blogic-io.xml"<br>
     *         (状態) mapperStr（業務ロジック入出力情報反映クラス名）:null<br>
     *         (状態) digester:null<br>
     *
     * <br>
     * 期待値：(状態変化) resources:サーブレットコンテキストのBLOGIC_RESOURCES/sub1をキーにしてBLogicResourcesインスタンスが格納されること。<br>
     *         (状態変化) mapper:サーブレットコンテキストのBLOGIC_MAPPER/sub1をキーにしてBLogicMapperStubインスタンスが格納されること。<br>
     *
     * <br>
     * モジュール名が空文字以外の時、サーブレットコンテキストに登録されるBLogicResources、BLogicMapperのキーに、モジュール名が追加されていること。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testInit15() throws Exception {
        // テストデータ設定
        // モジュール設定情報("/sub1"をモジュール名に指定）
        ModuleConfig config = new ModuleConfigImpl("/sub1");

        // BLogic定義ルールファイル名
        UTUtil.setPrivateField(plugin, "digesterRules",
                BLogicIOPlugInTest.class.getResource(
                        "BLogicIOPlugInTest-blogic-io-rules.xml").getPath());
        // BLogic定義ファイル名
        UTUtil.setPrivateField(plugin, "resources", BLogicIOPlugInTest.class
                .getResource("BLogicIOPlugInTest-blogic-io.xml").getPath());
        // マッパークラス名
        UTUtil.setPrivateField(plugin, "mapperStr", null);

        // アクションサーブレット作成
        BLogicIOPlugIn_ActionServletStub01 servlet = new BLogicIOPlugIn_ActionServletStub01();

        // テスト実行
        plugin.init(servlet, config);

        // サーブレットコンテキスト取得
        BLogicIOPlugIn_MockServletContextStub01 ctx = (BLogicIOPlugIn_MockServletContextStub01) servlet
                .getServletContext();

        // サーブレットコンテキスト内にデフォルトのBLogicMapperインスタンスが
        // 格納されていること（"/sub1"モジュールのキーで）
        assertEquals("jp.terasoluna.fw.service.thin.BLogicMapper",
                ctx.getAttribute("BLOGIC_MAPPER/sub1").getClass().getName());

        // サーブレットコンテキスト内にBLogicResourcesインスタンスが
        // 格納されていること（"/sub1"モジュールのキーで）
        assertTrue(ctx.getAttribute("BLOGIC_RESOURCES/sub1") instanceof BLogicResources);
        assertNull(ctx.getAttribute("BLOGIC_RESOURCES"));

        // サーブレットコンテキストに登録されているリソースが
        // 指定したルールファイルであること。
        assertEquals(BLogicIOPlugInTest.class.getResource(
                "BLogicIOPlugInTest-blogic-io-rules.xml").getPath(), ctx
                .getCalledResources());
    }

    /**
     * testInit16()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：D
     * <br><br>
     * 入力値：(引数) servlet:not null<br>
     *         (引数) config:モジュール名(属性)=""<br>
     *         (状態) rules（Digesterルールファイル名）:"BLogicIOPlugInTest-blogic-io-rules.xml"<br>
     *         (状態) blogic-io（業務ロジック入出力情報マッピング定義ファイル名）:"BLogicIOPlugInTest-blogic-io.xml"、"BLogicIOPlugInTest-blogic-io2.xml"、BLogicIOPlugInTest-blogic-io3.xml"（複数ファイル指定時）<br>
     *         (状態) mapperStr（業務ロジック入出力情報反映クラス名）:null<br>
     *         (状態) digester:null<br>
     *
     * <br>
     * 期待値：(状態変化) resources:サーブレットコンテキストのBLOGIC_RESOURCESをキーにしてBLogicResourcesインスタンスが格納されること。<br>
     *         (状態変化) mapper:サーブレットコンテキストのBLOGIC_MAPPERをキーにしてBLogicMapperインスタンスが格納されること。<br>
     *
     * <br>
     * blogic-io.xmlファイルを３ファイル連結し、それぞれが正しくBLogicPropertyに保存されていること。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testInit16() throws Exception {
        // テストデータ設定
        // モジュール設定情報
        ModuleConfig config = new ModuleConfigImpl("");

        // BLogic定義ルールファイル名
        UTUtil.setPrivateField(plugin, "digesterRules",
                BLogicIOPlugInTest.class.getResource(
                        "BLogicIOPlugInTest-blogic-io-rules.xml").getPath());
        // BLogic定義ファイル名
        UTUtil.setPrivateField(plugin, "resources", BLogicIOPlugInTest.class
                .getResource("BLogicIOPlugInTest-blogic-io.xml").getPath()
                + ","
                + BLogicIOPlugInTest.class.getResource(
                        "BLogicIOPlugInTest-blogic-io2.xml").getPath()
                + ","
                + BLogicIOPlugInTest.class.getResource(
                        "BLogicIOPlugInTest-blogic-io3.xml").getPath());
        // マッパークラス名
        UTUtil.setPrivateField(plugin, "mapperStr", null);

        // アクションサーブレット作成
        BLogicIOPlugIn_ActionServletStub01 servlet = new BLogicIOPlugIn_ActionServletStub01();

        // テスト実行
        plugin.init(servlet, config);

        // サーブレットコンテキスト取得
        BLogicIOPlugIn_MockServletContextStub01 ctx = (BLogicIOPlugIn_MockServletContextStub01) servlet
                .getServletContext();

        // サーブレットコンテキスト内にデフォルトのBLogicMapperインスタンスが
        // 格納されていること
        assertEquals("jp.terasoluna.fw.service.thin.BLogicMapper",
                ctx.getAttribute("BLOGIC_MAPPER").getClass().getName());

        // サーブレットコンテキストに登録されているリソースが
        // 指定したルールファイルであること。
        assertEquals(BLogicIOPlugInTest.class.getResource(
                "BLogicIOPlugInTest-blogic-io-rules.xml").getPath(), ctx
                .getCalledResources());

        // XMLをパースした結果を取得
        BLogicResources resource = (BLogicResources) ctx
                .getAttribute("BLOGIC_RESOURCES");
        Map map = (Map) UTUtil.getPrivateField(resource, "blogicIO");

        // １ファイル目のblogic-ioファイルが正常に読み込めていること
        BLogicIO logonIO = (BLogicIO) map.get("/logon");
        // blogic-paramsチェック
        assertEquals("test_bean_name1-1", logonIO.getInputBeanName());
        List<BLogicProperty> logonParamList = logonIO.getBLogicParams();
        assertEquals(1, logonParamList.size());
        BLogicProperty property = logonParamList.get(0);
        assertEquals("test_params_blogic", property.getBLogicProperty());
        assertEquals("test_params_property", property.getProperty());
        assertEquals("form", property.getSource());
        // blogic-resultチェック
        List<BLogicProperty> logonResultList = logonIO.getBLogicResults();
        assertEquals(1, logonResultList.size());
        BLogicProperty result = logonResultList.get(0);
        assertEquals("test_result_blogic", result.getBLogicProperty());
        assertEquals("test_result_property", result.getProperty());
        assertEquals("session", result.getDest());

        BLogicIO dbIO = (BLogicIO) map.get("/db");
        // blogic-paramsチェック
        assertEquals("test_bean_name1-2", dbIO.getInputBeanName());
        List dbParamList = dbIO.getBLogicParams();
        assertEquals(0, dbParamList.size());
        // blogic-resultチェック
        List dbResultList = dbIO.getBLogicResults();
        assertEquals(0, dbResultList.size());

        // ２ファイル目のblogic-ioファイルが正常に読み込めていること
        BLogicIO logon2IO = (BLogicIO) map.get("/logon2");
        // blogic-paramsチェック
        assertEquals("test_bean_name2-1", logon2IO.getInputBeanName());
        List<BLogicProperty> logon2ParamList = logon2IO.getBLogicParams();
        assertEquals(2, logon2ParamList.size());
        BLogicProperty property2_1 = logon2ParamList.get(0);
        assertEquals("test_params_blogic2-1", property2_1.getBLogicProperty());
        assertEquals("test_params_property2-1", property2_1.getProperty());
        assertEquals("form", property2_1.getSource());
        BLogicProperty property2_2 = logon2ParamList.get(1);
        assertEquals("test_params_blogic2-2", property2_2.getBLogicProperty());
        assertEquals("test_params_property2-2", property2_2.getProperty());
        assertEquals("form", property2_2.getSource());
        // blogic-resultチェック
        List<BLogicProperty> logon2ResultList = logon2IO.getBLogicResults();
        assertEquals(2, logon2ResultList.size());
        BLogicProperty result2_1 = logon2ResultList.get(0);
        assertEquals("test_result_blogic2-1", result2_1.getBLogicProperty());
        assertEquals("test_result_property2-1", result2_1.getProperty());
        assertEquals("session", result2_1.getDest());
        BLogicProperty result2_2 = logon2ResultList.get(1);
        assertEquals("test_result_blogic2-2", result2_2.getBLogicProperty());
        assertEquals("test_result_property2-2", result2_2.getProperty());
        assertEquals("session", result2_2.getDest());

        BLogicIO db2IO = (BLogicIO) map.get("/db2");
        // blogic-paramsチェック
        assertEquals("test_bean_name2-3", db2IO.getInputBeanName());
        List db2ParamList = db2IO.getBLogicParams();
        assertEquals(0, db2ParamList.size());
        // blogic-resultチェック
        List db2ResultList = db2IO.getBLogicResults();
        assertEquals(0, db2ResultList.size());

        // ３ファイル目のblogic-ioファイルが正常に読み込めていること
        BLogicIO logon3IO = (BLogicIO) map.get("/logon3");
        // blogic-paramsチェック
        assertEquals("test_bean_name3-1", logon3IO.getInputBeanName());
        List<BLogicProperty> logon3ParamList = logon3IO.getBLogicParams();
        assertEquals(3, logon3ParamList.size());
        BLogicProperty property3_1 = logon3ParamList.get(0);
        assertEquals("test_params_blogic3-1", property3_1.getBLogicProperty());
        assertEquals("test_params_property3-1", property3_1.getProperty());
        assertEquals("form", property3_1.getSource());
        BLogicProperty property3_2 = logon3ParamList.get(1);
        assertEquals("test_params_blogic3-2", property3_2.getBLogicProperty());
        assertEquals("test_params_property3-2", property3_2.getProperty());
        assertEquals("form", property3_2.getSource());
        BLogicProperty property3_3 = logon3ParamList.get(2);
        assertEquals("test_params_blogic3-3", property3_3.getBLogicProperty());
        assertEquals("test_params_property3-3", property3_3.getProperty());
        assertEquals("form", property3_3.getSource());
        // blogic-resultチェック
        List<BLogicProperty> logon3ResultList = logon3IO.getBLogicResults();
        assertEquals(3, logon3ResultList.size());
        BLogicProperty result3_1 = logon3ResultList.get(0);
        assertEquals("test_result_blogic3-1", result3_1.getBLogicProperty());
        assertEquals("test_result_property3-1", result3_1.getProperty());
        assertEquals("session", result3_1.getDest());
        BLogicProperty result3_2 = logon3ResultList.get(1);
        assertEquals("test_result_blogic3-2", result3_2.getBLogicProperty());
        assertEquals("test_result_property3-2", result3_2.getProperty());
        BLogicProperty result3_3 = logon3ResultList.get(2);
        assertEquals("test_result_blogic3-3", result3_3.getBLogicProperty());
        assertEquals("test_result_property3-3", result3_3.getProperty());
        assertEquals("session", result3_3.getDest());

        // ２ファイル目と３ファイル目のblogic-ioファイルに重複したパスが存在した場合、
        // 後から設定されたファイルの情報が設定されていること
        BLogicIO duplicateIO = (BLogicIO) map.get("/duplicate");
        // blogic-paramsチェック
        assertEquals("test_bean_name3-2", duplicateIO.getInputBeanName());
        List<BLogicProperty> duplicateParamList = duplicateIO.getBLogicParams();
        assertEquals(1, duplicateParamList.size());
        BLogicProperty duplicate1 = duplicateParamList.get(0);
        assertEquals("duplicate_params_blogic2", duplicate1.getBLogicProperty());
        assertEquals("duplicate_params_property2", duplicate1.getProperty());
        assertEquals("form", duplicate1.getSource());
        // blogic-resultチェック
        List<BLogicProperty> duplicateResultList = duplicateIO
                .getBLogicResults();
        assertEquals(1, duplicateResultList.size());
        BLogicProperty duplicate2 = duplicateResultList.get(0);
        assertEquals("duplicate_result_blogic2", duplicate2.getBLogicProperty());
        assertEquals("duplicate_result_property2", duplicate2.getProperty());
        assertEquals("session", duplicate2.getDest());

        BLogicIO db3IO = (BLogicIO) map.get("/db3");
        // blogic-paramsチェック
        assertEquals("test_bean_name3-3", db3IO.getInputBeanName());
        List db3ParamList = db3IO.getBLogicParams();
        assertEquals(0, db3ParamList.size());
        // blogic-resultチェック
        List db3ResultList = db3IO.getBLogicResults();
        assertEquals(0, db3ResultList.size());
    }

    /**
     * testInit17()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) servlet:not null<br>
     *         (引数) config:モジュール名(属性)=""<br>
     *         (状態) rules（Digesterルールファイル名）:"BLogicIOPlugInTest-blogic-io-rules.xml"<br>
     *         (状態) blogic-io（業務ロジック入出力情報マッピング定義ファイル名）:"BLogicIOPlugInTest-blogic-io4.xml"<br>
     *                bean-nameの指定なし。
     *         (状態) mapperStr（業務ロジック入出力情報反映クラス名）:null<br>
     *         (状態) digester:null<br>
     *
     * <br>
     * 期待値：(状態変化) resources:サーブレットコンテキストのBLOGIC_RESOURCESをキーにしてBLogicResourcesインスタンスが格納されること。<br>
     *                              BlogicIOインスタンスのinputBeanNameがnullであること。
     *         (状態変化) mapper:サーブレットコンテキストのBLOGIC_MAPPERをキーにしてBLogicMapperインスタンスが格納されること。<br>
     *
     * <br>
     * blogic-io.xmlファイルのbean-name属性がnullのとき、BLogicIOのinputBeanNameがnullであることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testInit17() throws Exception {
        // テストデータ設定
        // モジュール設定情報
        ModuleConfig config = new ModuleConfigImpl("");

        // BLogic定義ルールファイル名
        UTUtil.setPrivateField(plugin, "digesterRules",
                BLogicIOPlugInTest.class.getResource(
                        "BLogicIOPlugInTest-blogic-io-rules.xml").getPath());
        // BLogic定義ファイル名
        UTUtil.setPrivateField(plugin, "resources", BLogicIOPlugInTest.class
                .getResource("BLogicIOPlugInTest-blogic-io4.xml").getPath());
        // マッパークラス名
        UTUtil.setPrivateField(plugin, "mapperStr", null);

        // アクションサーブレット作成
        BLogicIOPlugIn_ActionServletStub01 servlet = new BLogicIOPlugIn_ActionServletStub01();

        // テスト実行
        plugin.init(servlet, config);

        // サーブレットコンテキスト取得
        BLogicIOPlugIn_MockServletContextStub01 ctx = (BLogicIOPlugIn_MockServletContextStub01) servlet
                .getServletContext();

        // サーブレットコンテキスト内にデフォルトのBLogicMapperインスタンスが
        // 格納されていること
        assertEquals("jp.terasoluna.fw.service.thin.BLogicMapper",
                ctx.getAttribute("BLOGIC_MAPPER").getClass().getName());

        // サーブレットコンテキスト内にBLogicResourcesインスタンスが
        // 格納されていること
        assertTrue(ctx.getAttribute("BLOGIC_RESOURCES") instanceof BLogicResources);

        BLogicResources resources =
            (BLogicResources) ctx.getAttribute("BLOGIC_RESOURCES");
        BLogicIO io = resources.getBLogicIO("/logon");

        //BLogicIO#inputBeanNameがnullであることを確認。
        assertNull(io.getInputBeanName());

        // サーブレットコンテキストに登録されているリソースが
        // 指定したルールファイルであること。
        assertEquals(BLogicIOPlugInTest.class.getResource(
                "BLogicIOPlugInTest-blogic-io-rules.xml").getPath(), ctx
                .getCalledResources());
    }

    /**
     * testInit18()
     * <br><br>
     *
     * (異常系)
     * <br>
     * 観点：G
     * <br><br>
     * 入力値：(引数) servlet:not null<br>
     *         (引数) config:モジュール名(属性)=""<br>
     *         (状態) rules（Digesterルールファイル名）:"BLogicIOPlugInTest-blogic-io-rules.xml"<br>
     *         (状態) blogic-io（業務ロジック入出力情報マッピング定義ファイル名）:"BLogicIOPlugInTest-blogic-io4.xml"<br>
     *                bean-nameの指定なし。
     *         (状態) mapperStr（業務ロジック入出力情報反映クラス名）:null<br>
     *         (状態) digester:null<br>
     *
     * <br>
     * 期待値：(状態変化) 例外:ServletException<br>
     *                    ラップした例外：<br>
     *                    IOException<br>
     *         (状態変化) ログ:＜ログ＞<br>
     *                    エラーログ：""<br>
     *                    例外：IOException<br>
     *
     * <br>
     * getPublicIdentifier()の戻りが空白で、URLのオブジェクトが生成できない場合
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testInit18() throws Exception {
        // テストデータ設定
        // モジュール設定情報
        ModuleConfig config = new ModuleConfigImpl("");

        plugin = new BLogicIOPlugIn() {
            @Override
            public String getPublicIdentifier() {
            	return "";
            }
        };

        // BLogic定義ルールファイル名
        UTUtil.setPrivateField(plugin, "digesterRules",
                BLogicIOPlugInTest.class.getResource(
                        "BLogicIOPlugInTest-blogic-io-rules.xml").getPath());
        // BLogic定義ファイル名
        UTUtil.setPrivateField(plugin, "resources", BLogicIOPlugInTest.class
                .getResource("BLogicIOPlugInTest-blogic-io4.xml").getPath());
        // マッパークラス名
        UTUtil.setPrivateField(plugin, "mapperStr", null);

        // アクションサーブレット作成
        BLogicIOPlugIn_ActionServletStub01 servlet = new BLogicIOPlugIn_ActionServletStub01();

        // テスト実行
        try {
            plugin.init(servlet, config);
            fail("テスト失敗");
        } catch (ServletException e) {
        	assertTrue(e.getRootCause() instanceof IOException);
            assertTrue(LogUTUtil.checkError("", e.getRootCause()));
        }

    }

    /**
     * testSetDigesterRules01()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) digesterRules:"digesterRules"<br>
     *         (状態) digesterRules:null<br>
     *
     * <br>
     * 期待値：(状態変化) digesterRules:"digesterRules"<br>
     *
     * <br>
     * 引数のdigesterRulesが正しく格納されること
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetDigesterRules01() throws Exception {
        // テスト実施
        plugin.setDigesterRules("digesterRules");

        // 判定
        assertEquals("digesterRules", UTUtil.getPrivateField(plugin,
                "digesterRules"));
    }

    /**
     * testSetResources01()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) resources:"resources"<br>
     *         (状態) resources:null<br>
     *
     * <br>
     * 期待値：(状態変化) resources:"resources"<br>
     *
     * <br>
     * 引数のresourcesが正しく格納されること
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetResources01() throws Exception {
        // テスト実施
        plugin.setResources("resources");

        // 判定
        assertEquals("resources", UTUtil.getPrivateField(plugin, "resources"));
    }

    /**
     * testSetMapperClass01()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) mapperStr:"mapperStr"<br>
     *         (状態) mapperStr:null<br>
     *
     * <br>
     * 期待値：(状態変化) mapperStr:"mapperStr"<br>
     *
     * <br>
     * 引数のmapperStrが正しく格納されること
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetMapperClass01() throws Exception {
        // テスト実施
        plugin.setMapperClass("mapperStr");

        // 判定
        assertEquals("mapperStr", UTUtil.getPrivateField(plugin, "mapperStr"));
    }

    /**
     * testSetPublicIdentifier01()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) publicIdentifier:"publicIdentifier"<br>
     *         (状態) publicIdentifier:not null（デフォルト値）<br>
     *
     * <br>
     * 期待値：(状態変化) publicIdentifier:"publicIdentifier"<br>
     *
     * <br>
     * 引数のpublicIdentifierが正しく格納されること
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetPublicIdentifier01() throws Exception {
        // テスト実施
        plugin.setPublicIdentifier("publicIdentifier");

        // 判定
        assertEquals("publicIdentifier", UTUtil.getPrivateField(plugin,
                "publicIdentifier"));
    }

    /**
     * testSetDtdUrl01()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) dtdUrl:"dtdUrl"<br>
     *         (状態) dtdUrl:not null（デフォルト値）<br>
     *
     * <br>
     * 期待値：(状態変化) dtdUrl:"dtdUrl"<br>
     *
     * <br>
     * 引数のdtdUrlが正しく格納されること
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetDtdUrl01() throws Exception {
        // テスト実施
        plugin.setDtdUrl("dtdUrl");

        // 判定
        assertEquals("dtdUrl", UTUtil.getPrivateField(plugin, "dtdUrl"));
    }

}
