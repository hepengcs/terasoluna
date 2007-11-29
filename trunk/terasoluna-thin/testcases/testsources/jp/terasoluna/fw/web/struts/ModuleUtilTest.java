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

package jp.terasoluna.fw.web.struts;

import org.apache.struts.Globals;
import org.apache.struts.config.ModuleConfig;
import org.apache.struts.config.impl.ModuleConfigImpl;

import jp.terasoluna.utlib.MockHttpServletRequest;
import jp.terasoluna.utlib.MockHttpSession;
import jp.terasoluna.utlib.MockServletContext;
import junit.framework.TestCase;

/**
 * {@link jp.terasoluna.fw.web.struts.ModuleUtil} クラスのブラックボックステスト。
 *
 * <p>
 * <h4>【クラスの概要】</h4>
 * ModuleConfig に関するユーティリティ。
 * <p>
 *
 * @see jp.terasoluna.fw.web.struts.ModuleUtil
 */
public class ModuleUtilTest extends TestCase {

    /**
     * このテストケースを実行する為の
     * GUI アプリケーションを起動する。
     *
     * @param args java コマンドに設定されたパラメータ
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(ModuleUtilTest.class);
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
    public ModuleUtilTest(String name) {
        super(name);
    }

    /**
     * testGetModuleConfig01()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) request:null<br>
     *
     * <br>
     * 期待値：(戻り値) ModuleConfig:null<br>
     *
     * <br>
     * 引数のrequestがnullの場合、nullが取得できることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetModuleConfig01() throws Exception {
        // テスト実施
        Object result = ModuleUtil.getModuleConfig(null);

        assertNull(result);
    }

    /**
     * testGetModuleConfig02()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) request:not null<br>
     *         (状態) request.getAttribute(<br>
     *                    Globals.MODULE_KEY):null<br>
     *         (状態) ServletContext.getAttribute(<br>
     *                    Globals.MODULE_KEY):not null<br>
     *
     * <br>
     * 期待値：(戻り値) ModuleConfig:サーブレットコンテキストのGlobals.MODULE_KEYで登録されている<br>
     *                  デフォルトモジュールが返却される。<br>
     *
     * <br>
     * リクエストにModuleConfigが存在せず、サーブレットコンテキストに存在する場合、サーブレットコンテキストからModuleConfigを取得して返却することを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetModuleConfig02() throws Exception {
        //テストデータ設定

        // 擬似リクエスト
        MockHttpServletRequest req = new MockHttpServletRequest();
        // モジュールコンフィグ
        ModuleConfig apConfig = new ModuleConfigImpl("");
        // 擬似セッション
        MockHttpSession session = new MockHttpSession();
        // 擬似サーブレットコンテキスト
        MockServletContext context = new MockServletContext();

        // モジュールコンフィグがサーブレットコンテキストのみに登録されている。
        req.setAttribute(Globals.MODULE_KEY, null);
        context.setAttribute(Globals.MODULE_KEY, apConfig);
        session.setServletContext(context);
        req.setSession(session);

        //テスト実行
        ModuleConfig retConfig = ModuleUtil.getModuleConfig(req);

        //テスト結果確認

        // サーブレットコンテキストに登録されているモジュールコンフィグが
        // 返却されること
        assertSame(apConfig, retConfig);
    }

    /**
     * testGetModuleConfig03()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) request:not null<br>
     *         (状態) request.getAttribute(<br>
     *                    Globals.MODULE_KEY):not null<br>
     *         (状態) ServletContext.getAttribute(<br>
     *                    Globals.MODULE_KEY):null<br>
     *
     * <br>
     * 期待値：(戻り値) ModuleConfig:HTTPリクエストのGlobals.MODULE_KEYで登録されているデフォルトモジュールが<br>
     *                  返却される。<br>
     *
     * <br>
     * リクエストにModuleConfigが存在する場合、リクエストからModuleConfigを取得して返却することを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetModuleConfig03() throws Exception {
        //テストデータ設定

        // 擬似リクエスト
        MockHttpServletRequest req = new MockHttpServletRequest();
        // モジュールコンフィグ(リクエストに登録)
        ModuleConfig reqConfig = new ModuleConfigImpl("/sub1");
        req.setAttribute(Globals.MODULE_KEY, reqConfig);

        // 擬似セッション
        MockHttpSession session = new MockHttpSession();
        // 擬似サーブレットコンテキスト
        MockServletContext context = new MockServletContext();
        // モジュールコンフィグ(サーブレットコンテキストに登録)
        ModuleConfig apConfig = new ModuleConfigImpl("");

        // サーブレットコンテキストにもモジュールコンフィグを登録されている。
        context.setAttribute(Globals.MODULE_KEY, apConfig);
        session.setServletContext(context);
        req.setSession(session);

        //テスト実行
        ModuleConfig retConfig = ModuleUtil.getModuleConfig(req);

        //テスト結果確認

        // リクエストに登録されているモジュールコンフィグが
        // 返却されること
        assertSame(reqConfig, retConfig);
    }

    /**
     * testGetModuleConfig04()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) request:not null<br>
     *         (状態) request.getAttribute(<br>
     *                    Globals.MODULE_KEY):null<br>
     *         (状態) ServletContext.getAttribute(<br>
     *                    Globals.MODULE_KEY):null<br>
     *
     * <br>
     * 期待値：(戻り値) ModuleConfig:null<br>
     *
     * <br>
     * リクエスト、サーブレットコンテキストにModuleConfigが存在しない場合、nullが返却されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetModuleConfig04() throws Exception {
        //テストデータ設定

        // 擬似リクエスト
        MockHttpServletRequest req = new MockHttpServletRequest();

        // 擬似セッション
        MockHttpSession session = new MockHttpSession();

        // 擬似サーブレットコンテキスト
        MockServletContext context = new MockServletContext();

        session.setServletContext(context);
        req.setSession(session);

        //テスト実行
        ModuleConfig retConfig = ModuleUtil.getModuleConfig(req);

        //テスト結果確認

        // サーブレットコンテキストに登録されているモジュールコンフィグが
        // 返却されること
        assertNull(retConfig);
    }

    /**
     * testGetPrefix01()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) request:null<br>
     *
     * <br>
     * 期待値：(戻り値) String:null<br>
     *
     * <br>
     * 引数のrequestがnullの場合、nullが返却されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetPrefix01() throws Exception {
        Object result = ModuleUtil.getPrefix(null);
        assertNull(result);
    }

    /**
     * testGetPrefix02()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) request:not null<br>
     *         (状態) getModuleConfigの結果:null<br>
     *
     * <br>
     * 期待値：(戻り値) String:null<br>
     *
     * <br>
     * getModuleConfigがnullを返却する場合、nullが返却されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetPrefix02() throws Exception {
        //テストデータ設定

        // 擬似リクエスト
        MockHttpServletRequest req = new MockHttpServletRequest();

        // 擬似セッション
        MockHttpSession session = new MockHttpSession();

        // 擬似サーブレットコンテキスト
        MockServletContext context = new MockServletContext();

        session.setServletContext(context);
        req.setSession(session);

        //テスト実行
        Object result = ModuleUtil.getPrefix(req);

        //テスト結果確認
        assertNull(result);
    }

    /**
     * testGetPrefix03()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：E
     * <br><br>
     * 入力値：(引数) request:not null<br>
     *         (状態) getModuleConfigの結果:not null<br>
     *                prefix="abc"<br>
     *
     * <br>
     * 期待値：(戻り値) String:abc<br>
     *
     * <br>
     * ModuleConfigのprefixの値が返却されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetPrefix03() throws Exception {
        //テストデータ設定

        // 擬似リクエスト
        MockHttpServletRequest req = new MockHttpServletRequest();
        // モジュールコンフィグ
        ModuleConfig apConfig = new ModuleConfigImpl("abc");
        // 擬似セッション
        MockHttpSession session = new MockHttpSession();
        // 擬似サーブレットコンテキスト
        MockServletContext context = new MockServletContext();

        // モジュールコンフィグがサーブレットコンテキストのみに登録されている。
        req.setAttribute(Globals.MODULE_KEY, null);
        context.setAttribute(Globals.MODULE_KEY, apConfig);
        session.setServletContext(context);
        req.setSession(session);

        //テスト実行
        String prefix = ModuleUtil.getPrefix(req);

        //テスト結果確認

        // サーブレットコンテキストに登録されているモジュールコンフィグが
        // 返却されること
        assertEquals("abc", prefix);
    }

    /**
     * testGetPrefix04()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) request:not null<br>
     *         (状態) getModuleConfigの結果:not null<br>
     *                prefix=null<br>
     *
     * <br>
     * 期待値：(戻り値) String:null<br>
     *
     * <br>
     * ModuleConfigのprefixの値が返却されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetPrefix04() throws Exception {
        //テストデータ設定

        // 擬似リクエスト
        MockHttpServletRequest req = new MockHttpServletRequest();
        // モジュールコンフィグ
        ModuleConfig apConfig = new ModuleConfigImpl(null);
        // 擬似セッション
        MockHttpSession session = new MockHttpSession();
        // 擬似サーブレットコンテキスト
        MockServletContext context = new MockServletContext();

        // モジュールコンフィグがサーブレットコンテキストのみに登録されている。
        req.setAttribute(Globals.MODULE_KEY, null);
        context.setAttribute(Globals.MODULE_KEY, apConfig);
        session.setServletContext(context);
        req.setSession(session);

        //テスト実行
        String prefix = ModuleUtil.getPrefix(req);

        //テスト結果確認

        // サーブレットコンテキストに登録されているモジュールコンフィグが
        // 返却されること
        assertNull(prefix);
    }

}
