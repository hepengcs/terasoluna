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
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.apache.commons.digester.xmlrules.XmlLoadException;
import org.apache.struts.config.ModuleConfig;
import org.apache.struts.config.impl.ModuleConfigImpl;
import org.xml.sax.SAXException;

import jp.terasoluna.fw.util.ClassLoadException;
import jp.terasoluna.fw.web.struts.reset.ActionReset;
import jp.terasoluna.fw.web.struts.reset.FieldReset;
import jp.terasoluna.fw.web.struts.reset.ResetterImpl;
import jp.terasoluna.fw.web.struts.reset.ResetterResources;
import jp.terasoluna.utlib.LogUTUtil;
import jp.terasoluna.utlib.UTUtil;
import junit.framework.TestCase;

/**
 * {@link jp.terasoluna.fw.web.struts.plugins.ResetterPlugIn} クラスのブラックボックステスト。
 * 
 * <p>
 * <h4>【クラスの概要】</h4>
 * フォームのリセット設定をロードするプラグイン。<br>
 * PlugIn機能を使用し、サーブレット初期化時にフォームのリセット機能の設定を読み込み、サーブレットコンテキストに保存する。
 * <p>
 * 
 * @see jp.terasoluna.fw.web.struts.plugins.ResetterPlugIn
 */
public class ResetterPlugInTest extends TestCase {

    /**
     * ファイルパスをプロパティから取得
     */
    private static final String RESET_FILE_NAME = ResetterPlugInTest.class
            .getResource("ResetterPlugInTest-reset.xml").getPath();

    /**
     * ファイルパスをプロパティから取得
     */
    private static final String RESET_FILE_NAME2 = ResetterPlugInTest.class
            .getResource("ResetterPlugInTest-reset2.xml").getPath();

    /**
     * ファイルパスをプロパティから取得
     */
    private static final String RESET_FILE_NAME3 = ResetterPlugInTest.class
            .getResource("ResetterPlugInTest-reset3.xml").getPath();

    /**
     * ファイルパスをプロパティから取得
     */
    private static final String RESET_RULES_FILE_NAME = ResetterPlugInTest.class
            .getResource("ResetterPlugInTest-reset-rules.xml").getPath();
    
    /**
     * ResetterPlugIn。
     */
    private ResetterPlugIn plugin = null;

    /**
     * このテストケースを実行する為の
     * GUI アプリケーションを起動する。
     * 
     * @param args java コマンドに設定されたパラメータ
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(ResetterPlugInTest.class);
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
        
        plugin = new ResetterPlugIn();
        UTUtil.setPrivateField(ResetterPlugIn.class, "digester", null);
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
    public ResetterPlugInTest(String name) {
        super(name);
    }

    /**
     * testInit01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) servlet:not null<br>
     *         (引数) config:モジュール名(属性)=""<br>
     *         (状態) this.resetter:ResetterPlugIn_ResetterStub01（存在するクラス）<br>
     *         (状態) this.resourcePath:"ResetterPlugInTest-reset.xml"（存在するファイル）<br>
     *         
     * <br>
     * 期待値：(状態変化) RESETTER_RESOURCES:サーブレットコンテキストの"RESETTER_RESOURCES"をキーにしてResetterResourcesインスタンスが格納されること。<br>
     *         (状態変化) RESETTER:サーブレットコンテキストの"RESETTER"をキーにしてResetterPlugIn_ResetterStub01インスタンスが格納されること。<br>
     *         
     * <br>
     * 初期化処理が正常に行われることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testInit01() throws Exception {
        // モジュール設定情報
        ModuleConfig conf = new ModuleConfigImpl("");

        // リセッタークラス完全修飾クラス名設定
        UTUtil.setPrivateField(plugin, "resetter",
                "jp.terasoluna.fw.web.struts.plugins."
                        + "ResetterPlugIn_ResetterStub01");

        // リセッタークラス完全修飾クラス名設定
        UTUtil.setPrivateField(plugin, "resourcesPath", RESET_FILE_NAME);

        // アクションサーブレット作成
        ResetterPlugIn_ActionServletStub01 servlet = new ResetterPlugIn_ActionServletStub01();

        // リセットルールを指定
        UTUtil.setPrivateField(plugin, "digesterRules", RESET_RULES_FILE_NAME);

        // テスト実行
        plugin.init(servlet, conf);
        
        // 結果確認
        // サーブレットコンテキスト取得
        ServletContext ctx = servlet.getServletContext();
        assertEquals(
                "jp.terasoluna.fw.web.struts.plugins.ResetterPlugIn_ResetterStub01",
                ((ResetterImpl) ctx.getAttribute("RESETTER")).getClass().getName());
        assertEquals(
                "jp.terasoluna.fw.web.struts.reset.ResetterResources",
                ((ResetterResources) ctx.getAttribute("RESETTER_RESOURCES"))
                        .getClass().getName());
    }

    /**
     * testInitResetter01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) servlet:not null<br>
     *         (引数) config:モジュール名(属性)=""<br>
     *         (状態) this.resetter:null<br>
     *         
     * <br>
     * 期待値：(状態変化) RESETTER:サーブレットコンテキストの"RESETTER"をキーにしてResetterインスタンスが格納されること。<br>
     *         (状態変化) 例外:－<br>
     *         (状態変化) ログ:－<br>
     *         
     * <br>
     * this.resetterがnullの時、正常にデフォルトresetterがサーブレットコンテキストに追加されることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testInitResetter01() throws Exception {
        // モジュール設定情報
        ModuleConfig conf = new ModuleConfigImpl("");

        // リセッタークラス完全修飾クラス名設定
        UTUtil.setPrivateField(plugin, "resetter", null);

        // アクションサーブレット作成
        ResetterPlugIn_ActionServletStub01 servlet = new ResetterPlugIn_ActionServletStub01();

        // テスト実行
        plugin.initResetter(servlet, conf);

        // 結果確認
        // サーブレットコンテキスト取得
        ServletContext ctx = servlet.getServletContext();
        assertEquals("jp.terasoluna.fw.web.struts.reset.ResetterImpl",
                ((ResetterImpl) ctx.getAttribute("RESETTER")).getClass().getName());
    }

    /**
     * testInitResetter02()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) servlet:not null<br>
     *         (引数) config:モジュール名(属性)=""<br>
     *         (状態) this.resetter:""<br>
     *         
     * <br>
     * 期待値：(状態変化) RESETTER:サーブレットコンテキストの"RESETTER"をキーにしてResetterインスタンスが格納されること。<br>
     *         (状態変化) 例外:－<br>
     *         (状態変化) ログ:－<br>
     *         
     * <br>
     * this.resetterが空文字の時、正常にデフォルトresetterがサーブレットコンテキストに追加することを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testInitResetter02() throws Exception {
        // モジュール設定情報
        ModuleConfig conf = new ModuleConfigImpl("");

        // リセッタークラス完全修飾クラス名設定
        UTUtil.setPrivateField(plugin, "resetter", "");

        // アクションサーブレット作成
        ResetterPlugIn_ActionServletStub01 servlet = new ResetterPlugIn_ActionServletStub01();

        // テスト実行
        plugin.initResetter(servlet, conf);

        // 結果確認
        // サーブレットコンテキスト取得
        ServletContext ctx = servlet.getServletContext();
        assertEquals("jp.terasoluna.fw.web.struts.reset.ResetterImpl",
                ((ResetterImpl) ctx.getAttribute("RESETTER")).getClass().getName());
    }

    /**
     * testInitResetter03()
     * <br><br>
     * 
     * (異常系)
     * <br>
     * 観点：G
     * <br><br>
     * 入力値：(引数) servlet:not null<br>
     *         (引数) config:モジュール名(属性)=""<br>
     *         (状態) this.resetter:Nothing（存在しないクラス）<br>
     *         
     * <br>
     * 期待値：(状態変化) RESETTER:－<br>
     *         (状態変化) 例外:ServletException<br>
     *                    ラップした例外：ClassLoadException(ClassNotFoundException)<br>
     *         (状態変化) ログ:＜ログ＞<br>
     *                    エラーログ：""<br>
     *                    例外：ClassLoadException<br>
     *         
     * <br>
     * this.resetterが存在しないクラスの時、ServletExceptionが発生することを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testInitResetter03() throws Exception {
        // モジュール設定情報
        ModuleConfig conf = new ModuleConfigImpl("");

        // リセッタークラス完全修飾クラス名設定
        UTUtil.setPrivateField(plugin, "resetter", "Nothing");

        // アクションサーブレット作成
        ResetterPlugIn_ActionServletStub01 servlet = new ResetterPlugIn_ActionServletStub01();

        try {
            // テスト実行
            plugin.initResetter(servlet, conf);
            fail();
        } catch (ServletException e) {
            // 結果確認
            assertEquals(ClassLoadException.class.getName(), e.getRootCause()
                    .getClass().getName());
            assertEquals(ClassNotFoundException.class.getName(), e
                    .getRootCause().getCause().getClass().getName());
            assertTrue(LogUTUtil.checkError("", e.getRootCause()));
        }
    }

    /**
     * testInitResetter04()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) servlet:not null<br>
     *         (引数) config:モジュール名(属性)=""<br>
     *         (状態) this.resetter:ResetterPlugIn_ResetterStub01（存在するクラス）<br>
     *         
     * <br>
     * 期待値：(状態変化) RESETTER:サーブレットコンテキストの"RESETTER"をキーにしてResetterPlugIn_ResetterStub01インスタンスが格納されること。<br>
     *         (状態変化) 例外:－<br>
     *         (状態変化) ログ:－<br>
     *         
     * <br>
     * this.resetterが存在するクラスの時、正常にresetterをサーブレットコンテキストに追加することを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testInitResetter04() throws Exception {
        // モジュール設定情報
        ModuleConfig conf = new ModuleConfigImpl("");

        // リセッタークラス完全修飾クラス名設定
        UTUtil.setPrivateField(plugin, "resetter",
                "jp.terasoluna.fw.web.struts.plugins."
                        + "ResetterPlugIn_ResetterStub01");

        // アクションサーブレット作成
        ResetterPlugIn_ActionServletStub01 servlet = new ResetterPlugIn_ActionServletStub01();

        // テスト実行
        plugin.initResetter(servlet, conf);

        // 結果確認
        // サーブレットコンテキスト取得
        ServletContext ctx = servlet.getServletContext();
        assertEquals(
                "jp.terasoluna.fw.web.struts.plugins.ResetterPlugIn_ResetterStub01",
                ((ResetterImpl) ctx.getAttribute("RESETTER")).getClass().getName());

    }

    /**
     * testInitResetter05()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) servlet:not null<br>
     *         (引数) config:モジュール名(属性)="test1"<br>
     *         (状態) this.resetter:ResetterPlugIn_ResetterStub01（存在するクラス）<br>
     *         
     * <br>
     * 期待値：(状態変化) RESETTER:サーブレットコンテキストの"RESETTER/test1"をキーにしてResetterPlugIn_ResetterStub01インスタンスが格納されること。<br>
     *         (状態変化) 例外:－<br>
     *         (状態変化) ログ:－<br>
     *         
     * <br>
     * モジュール名が空文字以外の時、サーブレットコンテキストに登録されるキーに、モジュール名が追加されていることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testInitResetter05() throws Exception {
        // モジュール設定情報
        ModuleConfig conf = new ModuleConfigImpl("/test1");

        // リセッタークラス完全修飾クラス名設定
        UTUtil.setPrivateField(plugin, "resetter",
                "jp.terasoluna.fw.web.struts.plugins."
                        + "ResetterPlugIn_ResetterStub01");

        // アクションサーブレット作成
        ResetterPlugIn_ActionServletStub01 servlet = new ResetterPlugIn_ActionServletStub01();

        // テスト実行
        plugin.initResetter(servlet, conf);

        // 結果確認
        // サーブレットコンテキスト取得
        ServletContext ctx = servlet.getServletContext();
        assertEquals(
                "jp.terasoluna.fw.web.struts.plugins.ResetterPlugIn_ResetterStub01",
                ((ResetterImpl) ctx.getAttribute("RESETTER/test1")).getClass()
                        .getName());

    }

    /**
     * testInitResources01()
     * <br><br>
     * 
     * (異常系)
     * <br>
     * 観点：G
     * <br><br>
     * 入力値：(引数) servlet:not null<br>
     *         (引数) config:モジュール名(属性)=""<br>
     *         (状態) this.resourcePath:null<br>
     *         (状態) digesterRules:－<br>
     *         (状態) ServletContext#getResource():－<br>
     *         
     * <br>
     * 期待値：(状態変化) RESETTER_RESOURCES:－<br>
     *         (状態変化) digesterRules:－<br>
     *         (状態変化) 例外:ServletException<br>
     *                    メッセージ："resources file location is not specified"<br>
     *         (状態変化) ログ:＜ログ＞<br>
     *                    エラーログ："resources file location is not specified"<br>
     *         
     * <br>
     * this.resourcesPathがnullの時ServletExceptionが発生することを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testInitResources01() throws Exception {
        // モジュール設定情報
        ModuleConfig conf = new ModuleConfigImpl("");

        // reset.xmlのパス設定
        UTUtil.setPrivateField(plugin, "resourcesPath", null);

        // アクションサーブレット作成
        ResetterPlugIn_ActionServletStub01 servlet = new ResetterPlugIn_ActionServletStub01();

        // テスト実行
        try {
            plugin.initResources(servlet, conf);
            fail();
        } catch (ServletException e) {
            assertEquals("resources file location is not specified", e
                    .getMessage());
            assertTrue(LogUTUtil
                    .checkError("resources file location is not specified"));
        }
    }

    /**
     * testInitResources02()
     * <br><br>
     * 
     * (異常系)
     * <br>
     * 観点：G
     * <br><br>
     * 入力値：(引数) servlet:not null<br>
     *         (引数) config:モジュール名(属性)=""<br>
     *         (状態) this.resourcePath:""<br>
     *         (状態) digesterRules:－<br>
     *         (状態) ServletContext#getResource():－<br>
     *         
     * <br>
     * 期待値：(状態変化) RESETTER_RESOURCES:－<br>
     *         (状態変化) digesterRules:－<br>
     *         (状態変化) 例外:ServletException<br>
     *                    メッセージ："resources file location is not specified"<br>
     *         (状態変化) ログ:＜ログ＞<br>
     *                    エラーログ："resources file location is not specified"<br>
     *         
     * <br>
     * this.resourcesPathが空文字の時ServletExceptionが発生することを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testInitResources02() throws Exception {
        // モジュール設定情報
        ModuleConfig conf = new ModuleConfigImpl("");

        // reset.xmlのパス設定
        UTUtil.setPrivateField(plugin, "resourcesPath", "");

        // アクションサーブレット作成
        ResetterPlugIn_ActionServletStub01 servlet = new ResetterPlugIn_ActionServletStub01();

        // テスト実行
        try {
            plugin.initResources(servlet, conf);
            fail();
        } catch (ServletException e) {
            assertEquals("resources file location is not specified", e
                    .getMessage());
            assertTrue(LogUTUtil
                    .checkError("resources file location is not specified"));
        }
    }

    /**
     * testInitResources03()
     * <br><br>
     * 
     * (異常系)
     * <br>
     * 観点：G
     * <br><br>
     * 入力値：(引数) servlet:not null<br>
     *         (引数) config:モジュール名(属性)=""<br>
     *         (状態) this.resourcePath:"ResetterPlugInTest-reset.xml"<br>
     *         (状態) digesterRules:null<br>
     *         (状態) ServletContext#getResource():－<br>
     *         
     * <br>
     * 期待値：(状態変化) RESETTER_RESOURCES:－<br>
     *         (状態変化) digesterRules:"/WEB-INF/reset-rules.xml"<br>
     *         (状態変化) 例外:ServletException<br>
     *                    ラップした例外：XmlLoadException<br>
     *         (状態変化) ログ:＜ログ＞<br>
     *                    エラーログ：""<br>
     *                    例外：XmlLoadException<br>
     *         
     * <br>
     * digesterRulesが指定されない場合、デフォルトのファイル名が格納されていることを確認する。また、digesterRulesファイルが存在しないファイルの時ServletExceptionが発生することを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testInitResources03() throws Exception {
        // モジュール設定情報
        ModuleConfig conf = new ModuleConfigImpl("");

        // reset.xmlのパス設定
        UTUtil.setPrivateField(plugin, "resourcesPath", RESET_FILE_NAME);

        // アクションサーブレット作成
        ResetterPlugIn_ActionServletStub01 servlet = new ResetterPlugIn_ActionServletStub01();

        try {
            // テスト実行
            plugin.initResources(servlet, conf);
            fail();
        } catch (ServletException e) {
            assertEquals(XmlLoadException.class.getName(), e.getRootCause()
                    .getClass().getName());
            assertTrue(LogUTUtil.checkError("", e.getRootCause()));
            assertEquals("/WEB-INF/reset-rules.xml", UTUtil.getPrivateField(
                    plugin, "digesterRules"));
        }
    }

    /**
     * testInitResources04()
     * <br><br>
     * 
     * (異常系)
     * <br>
     * 観点：G
     * <br><br>
     * 入力値：(引数) servlet:not null<br>
     *         (引数) config:モジュール名(属性)=""<br>
     *         (状態) this.resourcePath:"ResetterPlugInTest-reset.xml"<br>
     *         (状態) digesterRules:"ResetterPlugInTest-reset-rules.xml"<br>
     *         (状態) ServletContext#getResource():MalformedURLException発生<br>
     *         
     * <br>
     * 期待値：(状態変化) RESETTER_RESOURCES:－<br>
     *         (状態変化) digesterRules:"ResetterPlugInTest-reset-rules.xml"<br>
     *         (状態変化) 例外:ServletException<br>
     *                    ラップした例外：MalformedURLException<br>
     *         (状態変化) ログ:＜ログ＞<br>
     *                    エラーログ：""<br>
     *                    例外：MalformedURLException<br>
     *         
     * <br>
     * ServletContext#getResource()でMalformedURLExceptionが発生した時、ServletExceptionでラップすることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testInitResources04() throws Exception {
        // モジュール設定情報
        ModuleConfig conf = new ModuleConfigImpl("");

        // reset.xmlのパス設定
        UTUtil.setPrivateField(plugin, "resourcesPath", RESET_FILE_NAME);
        // reset-rules.xmlのパス設定
        UTUtil.setPrivateField(plugin, "digesterRules", RESET_RULES_FILE_NAME);

        // アクションサーブレット作成
        ResetterPlugIn_ActionServletStub02 servlet = new ResetterPlugIn_ActionServletStub02();

        try {
            // テスト実行
            plugin.initResources(servlet, conf);
            fail();
        } catch (ServletException e) {
            assertEquals(MalformedURLException.class.getName(), e
                    .getRootCause().getClass().getName());
            assertTrue(LogUTUtil.checkError("", e.getRootCause()));
            assertEquals(RESET_RULES_FILE_NAME, UTUtil.getPrivateField(plugin,
                    "digesterRules"));
        }
    }
    
    /**
     * testInitResources05()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) servlet:not null<br>
     *         (引数) config:モジュール名(属性)=""<br>
     *         (状態) this.resourcePath:"ResetterPlugInTest-reset.xml"<br>
     *         (状態) digesterRules:"ResetterPlugInTest-reset-rules.xml"<br>
     *         (状態) ServletContext#getResource():－<br>
     *         
     * <br>
     * 期待値：(状態変化) RESETTER_RESOURCES:サーブレットコンテキストの"RESETTER_RESOURCES"をキーにしてResetterResourcesインスタンスが格納されること。<br>
     *         (状態変化) digesterRules:－<br>
     *         (状態変化) 例外:－<br>
     *         (状態変化) ログ:－<br>
     *         
     * <br>
     * RESETTER_RESOURCESをキーにして、正常にサーブレットコンテキストに追加されていることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testInitResources05() throws Exception {
        // モジュール設定情報
        ModuleConfig conf = new ModuleConfigImpl("");

        // reset.xmlのパス設定
        UTUtil.setPrivateField(plugin, "resourcesPath", RESET_FILE_NAME);
        // reset-rules.xmlのパス設定
        UTUtil.setPrivateField(plugin, "digesterRules", RESET_RULES_FILE_NAME);

        // アクションサーブレット作成
        ResetterPlugIn_ActionServletStub01 servlet = new ResetterPlugIn_ActionServletStub01();

        // テスト実行
        plugin.initResources(servlet, conf);

        // 結果確認
        // サーブレットコンテキスト取得
        ServletContext ctx = servlet.getServletContext();
        assertEquals(
                "jp.terasoluna.fw.web.struts.reset.ResetterResources",
                ((ResetterResources) ctx.getAttribute("RESETTER_RESOURCES"))
                        .getClass().getName());
    }

    /**
     * testInitResources06()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) servlet:not null<br>
     *         (引数) config:モジュール名(属性)="/test2"<br>
     *         (状態) this.resourcePath:"ResetterPlugInTest-reset.xml"<br>
     *         (状態) digesterRules:"ResetterPlugInTest-reset-rules.xml"<br>
     *         (状態) ServletContext#getResource():－<br>
     *         
     * <br>
     * 期待値：(状態変化) RESETTER_RESOURCES:サーブレットコンテキストの"RESETTER_RESOURCES/test2"をキーにしてResetterResourcesインスタンスが格納されること。<br>
     *         (状態変化) digesterRules:－<br>
     *         (状態変化) 例外:－<br>
     *         (状態変化) ログ:－<br>
     *         
     * <br>
     * モジュール名が空文字以外の時、サーブレットコンテキストに登録されるキーに、モジュール名が追加されていることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testInitResources06() throws Exception {
        // モジュール設定情報
        ModuleConfig conf = new ModuleConfigImpl("/test2");

        // reset.xmlのパス設定
        UTUtil.setPrivateField(plugin, "resourcesPath", RESET_FILE_NAME);
        // reset-rules.xmlのパス設定
        UTUtil.setPrivateField(plugin, "digesterRules", RESET_RULES_FILE_NAME);

        // アクションサーブレット作成
        ResetterPlugIn_ActionServletStub01 servlet = new ResetterPlugIn_ActionServletStub01();

        // テスト実行
        plugin.initResources(servlet, conf);

        // 結果確認
        // サーブレットコンテキスト取得
        ServletContext ctx = servlet.getServletContext();
        assertEquals(
                "jp.terasoluna.fw.web.struts.reset.ResetterResources",
                ((ResetterResources) ctx
                        .getAttribute("RESETTER_RESOURCES/test2")).getClass()
                        .getName());
    }

    /**
     * testInitResources07()
     * <br><br>
     * 
     * (異常系)
     * <br>
     * 観点：G
     * <br><br>
     * 入力値：(引数) servlet:not null<br>
     *         (引数) config:モジュール名(属性)=""<br>
     *         (状態) this.resourcePath:"nothing.xml"（存在しないファイル名）<br>
     *         (状態) digesterRules:"ResetterPlugInTest-reset-rules.xml"<br>
     *         (状態) ServletContext#getResource():－<br>
     *         
     * <br>
     * 期待値：(状態変化) RESETTER_RESOURCES:－<br>
     *         (状態変化) digesterRules:－<br>
     *         (状態変化) 例外:ServletException<br>
     *                    ラップした例外：IOException<br>
     *         (状態変化) ログ:＜ログ＞<br>
     *                    エラーログ：""<br>
     *                    例外：IOException<br>
     *         
     * <br>
     * this.resourcePathに存在しないファイル名を指定した場合、ServletExceptionが発生することを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testInitResources07() throws Exception {
        // モジュール設定情報
        ModuleConfig conf = new ModuleConfigImpl("");

        // reset.xmlのパス設定
        UTUtil.setPrivateField(plugin, "resourcesPath", "nothing.xml");

        // reset-rules.xmlのパス設定
        UTUtil.setPrivateField(plugin, "digesterRules", RESET_RULES_FILE_NAME);

        // アクションサーブレット作成
        ResetterPlugIn_ActionServletStub01 servlet = new ResetterPlugIn_ActionServletStub01();

        // テスト実行
        try {
            plugin.initResources(servlet, conf);
            fail();
        } catch (ServletException e) {
            // 結果確認
            // 実際この場合は、MalformedURLExceptionが発生するが、catchはIOExceptionなのでinstanceofでチェックする
            assertTrue(e.getRootCause() instanceof IOException);
            assertTrue(LogUTUtil.checkError("", e.getRootCause()));
        }
    }
    
    /**
     * testInitResources08()
     * <br><br>
     * 
     * (異常系)
     * <br>
     * 観点：G
     * <br><br>
     * 入力値：(引数) servlet:not null<br>
     *         (引数) config:モジュール名(属性)=""<br>
     *         (状態) this.resourcePath:"ResetterPlugInTest-error-reset.xml"(パースエラーが発生するファイル名)<br>
     *         (状態) digesterRules:"ResetterPlugInTest-reset-rules.xml"<br>
     *         (状態) ServletContext#getResource():－<br>
     *         
     * <br>
     * 期待値：(状態変化) RESETTER_RESOURCES:－<br>
     *         (状態変化) digesterRules:－<br>
     *         (状態変化) 例外:ServletException<br>
     *                    ラップした例外：SAXException<br>
     *         (状態変化) ログ:＜ログ＞<br>
     *                    エラーログ：""<br>
     *                    例外：SAXException<br>
     *         
     * <br>
     * this.resourcesPathにパースエラーが発生するファイルを指定した時、ServletExceptionが発生することを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testInitResources08() throws Exception {
        // モジュール設定情報
        ModuleConfig conf = new ModuleConfigImpl("");

        // reset.xmlのパス設定
        UTUtil.setPrivateField(plugin, "resourcesPath",
                ResetterPlugInTest.class.getResource(
                        "ResetterPlugInTest-error-reset.xml").getPath());

        // reset-rules.xmlのパス設定
        UTUtil.setPrivateField(plugin, "digesterRules", RESET_RULES_FILE_NAME);

        // アクションサーブレット作成
        ResetterPlugIn_ActionServletStub01 servlet = new ResetterPlugIn_ActionServletStub01();

        // テスト実行
        try {
            plugin.init(servlet, conf);
            fail();
        } catch (ServletException e) {
            // 結果確認
            // 実際この場合は、SAXParseExceptionが発生するが、catchはSAXExceptionなのでinstanceofでチェックする
            assertTrue(e.getRootCause() instanceof SAXException);
            assertTrue(LogUTUtil.checkError("", e.getRootCause()));
        }
    }
    
    /**
     * testInitResources09()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) servlet:not null<br>
     *         (引数) config:モジュール名(属性)=""<br>
     *         (状態) this.resourcePath:"ResetterPlugInTest-reset.xml","ResetterPlugInTest-reset2.xml","ResetterPlugInTest-reset3.xml"<br>
     *         (状態) digesterRules:"ResetterPlugInTest-reset-rules.xml"<br>
     *         (状態) ServletContext#getResource():－<br>
     *         
     * <br>
     * 期待値：(状態変化) RESETTER_RESOURCES:サーブレットコンテキストの"RESETTER_RESOURCES"をキーにしてResetterResourcesインスタンスが格納されること。（ResetterResourcesの中身の確認も行なう）<br>
     *         (状態変化) digesterRules:－<br>
     *         (状態変化) 例外:－<br>
     *         (状態変化) ログ:－<br>
     *         
     * <br>
     * this.resourcesPathに複数のファイル名を指定した時、RESETTER_RESOURCESをキーにして、正常にサーブレットコンテキストに追加されていることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testInitResources09() throws Exception {
        // モジュール設定情報
        ModuleConfig conf = new ModuleConfigImpl("");

        // reset.xmlのパス設定
        UTUtil.setPrivateField(plugin, "resourcesPath", RESET_FILE_NAME + ","
                + RESET_FILE_NAME2 + "," + RESET_FILE_NAME3);
        // reset-rules.xmlのパス設定
        UTUtil.setPrivateField(plugin, "digesterRules", RESET_RULES_FILE_NAME);

        // アクションサーブレット作成
        ResetterPlugIn_ActionServletStub01 servlet = new ResetterPlugIn_ActionServletStub01();

        // テスト実行
        plugin.initResources(servlet, conf);

        // 結果確認
        // サーブレットコンテキスト取得
        ServletContext ctx = servlet.getServletContext();
        assertEquals(
                "jp.terasoluna.fw.web.struts.reset.ResetterResources",
                ((ResetterResources) ctx.getAttribute("RESETTER_RESOURCES"))
                        .getClass().getName());

        // XMLをパースした結果を取得
        ResetterResources resource = (ResetterResources) ctx
                .getAttribute("RESETTER_RESOURCES");
        Map actionResets = (Map) UTUtil.getPrivateField(resource,
                "actionResets");

        // １ファイル目のresetファイルが正常に読み込めていること
        ActionReset actionReset1_1 = (ActionReset) actionResets
                .get("/changePage");
        // fieldResetチェック
        Map fieldResets1_1 = (Map) UTUtil.getPrivateField(actionReset1_1,
                "fieldResets");
        assertEquals(1, fieldResets1_1.size());
        FieldReset fieldReset1_1 = (FieldReset) fieldResets1_1.get("field1-1");
        assertEquals("field1-1", fieldReset1_1.getFieldName());
        assertFalse(fieldReset1_1.isSelect());

        ActionReset actionReset1_2 = (ActionReset) actionResets.get("/bbb/BBB");
        // fieldResetチェック
        Map fieldResets1_2 = (Map) UTUtil.getPrivateField(actionReset1_2,
                "fieldResets");
        assertEquals(3, fieldResets1_2.size());
        FieldReset fieldReset1_2 = (FieldReset) fieldResets1_2.get("field1-1");
        assertEquals("field1-1", fieldReset1_2.getFieldName());
        assertFalse(fieldReset1_2.isSelect());
        FieldReset fieldReset1_3 = (FieldReset) fieldResets1_2.get("field1-2");
        assertEquals("field1-2", fieldReset1_3.getFieldName());
        assertTrue(fieldReset1_3.isSelect());
        FieldReset fieldReset1_4 = (FieldReset) fieldResets1_2.get("field1-3");
        assertEquals("field1-3", fieldReset1_4.getFieldName());
        assertTrue(fieldReset1_4.isSelect());

        ActionReset actionReset1_3 = (ActionReset) actionResets.get("/ccc/CCC");
        // fieldResetチェック
        Map fieldResets1_3 = (Map) UTUtil.getPrivateField(actionReset1_3,
                "fieldResets");
        assertEquals(2, fieldResets1_3.size());
        FieldReset fieldReset1_5 = (FieldReset) fieldResets1_3.get("field1-1");
        assertEquals("field1-1", fieldReset1_5.getFieldName());
        assertFalse(fieldReset1_5.isSelect());
        FieldReset fieldReset1_6 = (FieldReset) fieldResets1_3.get("field1-2");
        assertEquals("field1-2", fieldReset1_6.getFieldName());
        assertFalse(fieldReset1_6.isSelect());

        // ２ファイル目のresetファイルが正常に読み込めていること
        ActionReset actionReset2_1 = (ActionReset) actionResets
                .get("/aaa/AAA2");
        // fieldResetチェック
        Map fieldResets2_1 = (Map) UTUtil.getPrivateField(actionReset2_1,
                "fieldResets");
        assertEquals(1, fieldResets2_1.size());
        FieldReset fieldReset2_1 = (FieldReset) fieldResets2_1.get("field2-1");
        assertEquals("field2-1", fieldReset2_1.getFieldName());
        assertFalse(fieldReset2_1.isSelect());

        ActionReset actionReset2_2 = (ActionReset) actionResets
                .get("/bbb/BBB2");
        // fieldResetチェック
        Map fieldResets2_2 = (Map) UTUtil.getPrivateField(actionReset2_2,
                "fieldResets");
        assertEquals(3, fieldResets2_2.size());
        FieldReset fieldReset2_2 = (FieldReset) fieldResets2_2.get("field2-1");
        assertEquals("field2-1", fieldReset2_2.getFieldName());
        assertFalse(fieldReset2_2.isSelect());
        FieldReset fieldReset2_3 = (FieldReset) fieldResets2_2.get("field2-2");
        assertEquals("field2-2", fieldReset2_3.getFieldName());
        assertTrue(fieldReset2_3.isSelect());
        FieldReset fieldReset2_4 = (FieldReset) fieldResets2_2.get("field2-3");
        assertEquals("field2-3", fieldReset2_4.getFieldName());
        assertTrue(fieldReset2_4.isSelect());

        ActionReset actionReset2_3 = (ActionReset) actionResets
                .get("/ccc/CCC2");
        // fieldResetチェック
        Map fieldResets2_3 = (Map) UTUtil.getPrivateField(actionReset2_3,
                "fieldResets");
        assertEquals(2, fieldResets2_3.size());
        FieldReset fieldReset2_5 = (FieldReset) fieldResets2_3.get("field2-1");
        assertEquals("field2-1", fieldReset2_5.getFieldName());
        assertFalse(fieldReset2_5.isSelect());
        FieldReset fieldReset2_6 = (FieldReset) fieldResets2_3.get("field2-2");
        assertEquals("field2-2", fieldReset2_6.getFieldName());
        assertFalse(fieldReset2_6.isSelect());

        // ３ファイル目のresetファイルが正常に読み込めていること
        ActionReset actionReset3_1 = (ActionReset) actionResets
                .get("/aaa/AAA3");
        // fieldResetチェック
        Map fieldResets3_1 = (Map) UTUtil.getPrivateField(actionReset3_1,
                "fieldResets");
        assertEquals(1, fieldResets3_1.size());
        FieldReset fieldReset3_1 = (FieldReset) fieldResets3_1.get("field3-1");
        assertEquals("field3-1", fieldReset3_1.getFieldName());
        assertFalse(fieldReset3_1.isSelect());

        ActionReset actionReset3_2 = (ActionReset) actionResets
                .get("/bbb/BBB3");
        // fieldResetチェック
        Map fieldResets3_2 = (Map) UTUtil.getPrivateField(actionReset3_2,
                "fieldResets");
        assertEquals(3, fieldResets3_2.size());
        FieldReset fieldReset3_2 = (FieldReset) fieldResets3_2.get("field3-1");
        assertEquals("field3-1", fieldReset3_2.getFieldName());
        assertFalse(fieldReset3_2.isSelect());
        FieldReset fieldReset3_3 = (FieldReset) fieldResets3_2.get("field3-2");
        assertEquals("field3-2", fieldReset3_3.getFieldName());
        assertTrue(fieldReset3_3.isSelect());
        FieldReset fieldReset3_4 = (FieldReset) fieldResets3_2.get("field3-3");
        assertEquals("field3-3", fieldReset3_4.getFieldName());
        assertTrue(fieldReset3_4.isSelect());

        ActionReset actionReset3_3 = (ActionReset) actionResets
                .get("/ccc/CCC3");
        // fieldResetチェック
        Map fieldResets3_3 = (Map) UTUtil.getPrivateField(actionReset3_3,
                "fieldResets");
        assertEquals(2, fieldResets3_3.size());
        FieldReset fieldReset3_5 = (FieldReset) fieldResets3_3.get("field3-1");
        assertEquals("field3-1", fieldReset3_5.getFieldName());
        assertFalse(fieldReset3_5.isSelect());
        FieldReset fieldReset3_6 = (FieldReset) fieldResets3_3.get("field3-2");
        assertEquals("field3-2", fieldReset3_6.getFieldName());
        assertFalse(fieldReset3_6.isSelect());

        // ２ファイル目と３ファイル目のresetファイルに重複したパスが存在した場合、
        // 後から設定されたファイルの情報が設定されていること
        ActionReset actionResetDuplicate = (ActionReset) actionResets
                .get("/duplicate");
        // fieldResetチェック
        Map duplicate = (Map) UTUtil.getPrivateField(actionResetDuplicate,
                "fieldResets");
        assertEquals(1, duplicate.size());
        FieldReset fieldReset1 = (FieldReset) duplicate.get("duplicate1");
        assertNull(fieldReset1);
        FieldReset fieldReset2 = (FieldReset) duplicate.get("duplicate2");
        assertEquals("duplicate2", fieldReset2.getFieldName());
        assertFalse(fieldReset2.isSelect());
    }

    /**
     * testInitResources10()
     * <br><br>
     * 
     * (異常系)
     * <br>
     * 観点：G
     * <br><br>
     * 入力値：(引数) servlet:not null<br>
     *         (引数) config:モジュール名(属性)=""<br>
     *         (状態) this.resourcePath:"ResetterPlugInTest-reset.xml","ResetterPlugInTest-reset2.xml","ResetterPlugInTest-reset3.xml"<br>
     *         (状態) digesterRules:"ResetterPlugInTest-reset-rules.xml"<br>
     *         (状態) ServletContext#getResource():－<br>
     *         
     * <br>
     * 期待値：(状態変化) RESETTER_RESOURCES:－<br>
     *         (状態変化) digesterRules:－<br>
     *         (状態変化) 例外:ServletException<br>
     *                    ラップした例外：IOException<br>
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
    public void testInitResources10() throws Exception {
        // モジュール設定情報
        ModuleConfig conf = new ModuleConfigImpl("");

        plugin = new ResetterPlugIn() {
            @Override
            public String getPublicIdentifier() {
            	return "";
            }
        };

        // reset.xmlのパス設定
        UTUtil.setPrivateField(plugin, "resourcesPath", RESET_FILE_NAME + ","
                + RESET_FILE_NAME2 + "," + RESET_FILE_NAME3);
        // reset-rules.xmlのパス設定
        UTUtil.setPrivateField(plugin, "digesterRules", RESET_RULES_FILE_NAME);

        // アクションサーブレット作成
        ResetterPlugIn_ActionServletStub01 servlet = new ResetterPlugIn_ActionServletStub01();

        // テスト実行
        try {
            plugin.initResources(servlet, conf);
            fail("テスト失敗");
        } catch (ServletException e) {
        	assertTrue(e.getRootCause() instanceof IOException);
            assertTrue(LogUTUtil.checkError("", e.getRootCause()));
        }

    }

    /**
     * testSetResetter01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) string:"string"<br>
     *         (状態) resetter:null<br>
     *         
     * <br>
     * 期待値：(状態変化) resetter:"string"<br>
     *         
     * <br>
     * 引数のstringが正しく格納されること
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetResetter01() throws Exception {
        // 初期設定
        ResetterPlugIn rpi = new ResetterPlugIn();
        // テスト実行
        rpi.setResetter("string");
        // 結果確認
        assertEquals("string", UTUtil.getPrivateField(rpi, "resetter"));
    }

    /**
     * testSetResources01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) string:"string"<br>
     *         (状態) resourcesPath:null<br>
     *         
     * <br>
     * 期待値：(状態変化) resourcesPath:"string"<br>
     *         
     * <br>
     * 引数のstringが正しく格納されること
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetResources01() throws Exception {
        // 初期設定
        ResetterPlugIn rpi = new ResetterPlugIn();
        // テスト実行
        rpi.setResources("string");
        // 結果確認
        assertEquals("string", UTUtil.getPrivateField(rpi, "resourcesPath"));
    }

    /**
     * testSetDigesterRules01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) digesterRules:"string"<br>
     *         (状態) digesterRules:null<br>
     *         
     * <br>
     * 期待値：(状態変化) digesterRules:"string"<br>
     *         
     * <br>
     * 引数のdigesterRulesが正しく格納されること
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetDigesterRules01() throws Exception {
        // 初期設定
        ResetterPlugIn rpi = new ResetterPlugIn();
        // テスト実行
        rpi.setDigesterRules("string");
        // 結果確認
        assertEquals("string", UTUtil.getPrivateField(rpi, "digesterRules"));
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
