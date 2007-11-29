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

package jp.terasoluna.fw.web.thin;

import jp.terasoluna.utlib.MockFilterConfig;
import jp.terasoluna.utlib.MockHttpServletRequest;
import jp.terasoluna.utlib.MockHttpServletResponse;
import jp.terasoluna.utlib.UTUtil;
import junit.framework.TestCase;

/**
 * {@link jp.terasoluna.fw.web.thin.AuthenticationControlFilter} 
 * クラスのブラックボックステスト。
 * 
 * <p>
 * <h4>【クラスの概要】</h4>
 * ログオン済みかどうかのチェックを行う。<br>
 * ログオンが必要なパスへのアクセスがあった場合は、 
 * ユーザがログオン済みかどうかを判別し、
 * ログオン済みではなかった場合、{@link UnauthenticatedException}をスローする。
 * <p>
 * 
 * @see jp.terasoluna.fw.web.thin.AuthenticationControlFilter
 */
public class AuthenticationControlFilterTest extends TestCase {



    /**
     * このテストケースを実行する為の
     * GUI アプリケーションを起動する。
     * 
     * @param args java コマンドに設定されたパラメータ
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(AuthenticationControlFilterTest.class);
    }

    /**
     * コンストラクタ。
     * 
     * @param name このテストケースの名前
     */
    public AuthenticationControlFilterTest(String name) {
        super(name);
    }
    
    /**
     * 初期化処理を行う。
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        // staticフィールドの初期化を行う。
        UTUtil.setPrivateField(AuthenticationControlFilter.class, 
                               "controller", 
                               null);   
    }

    /**
     * 後始末を行う。
     */
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        UTUtil.setPrivateField(AuthenticationControlFilter.class, 
                               "controller",
                               null);   
    }
    
    /**
     * testInit01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) config:not null<br>
     *         (状態) config:not null<br>
     *         (状態) getController():AuthenticationControlFilter_AuthenticationControllerStub01インスタンス<br>
     *         (状態) controller:AuthenticationControlFilter_AuthenticationControllerStub01インスタンス<br>
     *         
     * <br>
     * 期待値：(状態変化) config:引数で与えたFilterConfigと同一のインスタンス<br>
     *         (状態変化) getController():呼び出されない<br>
     *         (状態変化) controller:前提条件と同一のインスタンス<br>
     *         
     * <br>
     * controllerがnullで無い場合、conroll引数で与えたconfigが設定され、getControllerが呼び出されないことを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testInit01() throws Exception {
        // 前処理
        
        // 既に設定してあるコンフィグ
        MockFilterConfig existingConfig = new MockFilterConfig();
        // 既に設定してあるコントローラ
        AuthenticationController existingController
            = new AuthenticationControlFilter_AuthenticationControllerStub01();
        UTUtil.setPrivateField(AuthenticationControlFilter.class,
                               "controller",
                               existingController);
        // フィルタの用意
        AuthenticationControlFilter_AuthenticationControlFilterStub01 filter
            = new AuthenticationControlFilter_AuthenticationControlFilterStub01();
        filter.setConfig(existingConfig);

        // 新しく設定されるコンフィグ
        MockFilterConfig newConfig = new MockFilterConfig();                              
        // 新しく設定されるコントローラ
        AuthenticationControlFilter_AuthenticationControllerStub01 newController
            = new AuthenticationControlFilter_AuthenticationControllerStub01();
        filter.newController = newController;

        // テスト実施
        filter.init(newConfig);

        // 判定
        assertSame(newConfig, UTUtil.getPrivateField(filter, "config"));
        assertFalse(filter.isCalled);
        assertSame(existingController, 
                   UTUtil.getPrivateField(AuthenticationControlFilter.class,
                                          "controller"));
    }

    /**
     * testInit02()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) config:not null<br>
     *         (状態) config:null<br>
     *         (状態) getController():AuthenticationControlFilter_AuthenticationControllerStub01インスタンス<br>
     *         (状態) controller:null<br>
     *         
     * <br>
     * 期待値：(戻り値) String:引数で与えたFilterConfigと同一のインスタンス<br>
     *         (状態変化) getController():呼び出される<br>
     *         (状態変化) controller:getController()から得られるインスタンスと同一のインスタンス<br>
     *         
     * <br>
     * controllerがnullの場合、引数で与えたconfigが設定され、
     * getControllerが呼び出され、フィールドに設定されることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testInit02() throws Exception {
        // 前処理

        // 新しく設定されるコンフィグ
        MockFilterConfig newConfig = new MockFilterConfig();
        
        // フィルタの用意
        AuthenticationControlFilter_AuthenticationControlFilterStub01 filter
            = new AuthenticationControlFilter_AuthenticationControlFilterStub01();
                               
        // 新しく設定されるコントローラ
        AuthenticationController newController
            = new AuthenticationControlFilter_AuthenticationControllerStub01();
        filter.newController = newController;

        // テスト実施
        filter.init(newConfig);

        // 判定
        assertSame(newConfig, UTUtil.getPrivateField(filter, "config"));
        assertTrue(filter.isCalled);
        assertSame(newController, 
                   UTUtil.getPrivateField(AuthenticationControlFilter.class,
                                          "controller"));
        
    }

    /**
     * testGetAuthenticationController01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(状態) controller:AuthenticationControlFilter_AuthenticationControllerStub01インスタンス<br>
     *         
     * <br>
     * 期待値：(戻り値) AuthenticationController:前提条件で設定したインスタンスと同一のインスタンス<br>
     *         
     * <br>
     * 正常系1件のみテスト
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetAuthenticationController01() throws Exception {
        // 前処理
        AuthenticationController existingController
            = new AuthenticationControlFilter_AuthenticationControllerStub01();
        UTUtil.setPrivateField(AuthenticationControlFilter.class, 
                               "controller", 
                               existingController);

        // テスト実施
        AuthenticationController actual
            = AuthenticationControlFilter.getAuthenticationController();

        // 判定
        assertSame(existingController, actual);
    }

    /**
     * testDoFilter01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) req:not null<br>
     *         (引数) res:not null<br>
     *         (引数) chain:not null<br>
     *         (状態) AUTHENTICATION_THRU_KEY:"aaaaa"<br>
     *         (状態) authenticationController:AuthenticationControlFilter_AuthenticationControllerStub01<br>
     *         
     * <br>
     * 期待値：(状態変化) AUTHENTICATION_THRU_KEY:前提条件と同じ値<br>
     *         (状態変化) chain.doFilter:実行する<br>
     *         
     * <br>
     * requestの属性値であるAUTHENTICATION_THRU_KEYがnullでない場合、
     * 別業務への遷移チェックを行わずに終了すること。<br>
     * FilterChain#doFilter()メソッドを実行すること。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testDoFilter01() throws Exception {
        // 前処理
        AuthenticationControlFilter filter = new AuthenticationControlFilter();

        // テストデータの設定
        AuthenticationController controller
            = new AuthenticationControlFilter_AuthenticationControllerStub01();
        UTUtil.setPrivateField(AuthenticationControlFilter.class,
                               "controller",
                               controller);
        
        MockHttpServletRequest req = new MockHttpServletRequest();
        req.setAttribute("AUTHENTICATION_THRU_KEY", "aaaaa");
        MockHttpServletResponse res = new MockHttpServletResponse();
        AuthenticationControlFilter_FilterChainStub01 chain
            = new AuthenticationControlFilter_FilterChainStub01();

        // テスト実行
        filter.doFilter(req, res, chain);

        // 判定        
        assertEquals("aaaaa", req.getAttribute("AUTHENTICATION_THRU_KEY"));
        assertTrue(chain.isCalled);
    }

    /**
     * testDoFilter02()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) req:not null<br>
     *         (引数) res:not null<br>
     *         (引数) chain:not null<br>
     *         (状態) AUTHENTICATION_THRU_KEY:null<br>
     *         (状態) authenticationController:AuthenticationControlFilter_AuthenticationControllerStub01<br>
     *         (状態) .isCheckRequired():false<br>
     *         
     * <br>
     * 期待値：(状態変化) AUTHENTICATION_THRU_KEY:"true"<br>
     *         (状態変化) chain.doFilter:実行する<br>
     *         
     * <br>
     * 別業務への遷移チェックがfalseの場合、以降のチェックを行わず、
     * requestの属性値AUTHENTICATION_THRU_KEYにtrueを設定して処理を終了すること。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testDoFilter02() throws Exception {
        // 前処理
        AuthenticationControlFilter filter = new AuthenticationControlFilter();

        // テストデータの設定
        AuthenticationControlFilter_AuthenticationControllerStub01 controller
            = new AuthenticationControlFilter_AuthenticationControllerStub01();
        UTUtil.setPrivateField(AuthenticationControlFilter.class,
                               "controller",
                               controller);
        
        MockHttpServletRequest req = new MockHttpServletRequest();
        MockHttpServletResponse res = new MockHttpServletResponse();
        AuthenticationControlFilter_FilterChainStub01 chain
            = new AuthenticationControlFilter_FilterChainStub01();

        // テスト実行
        filter.doFilter(req, res, chain);

        // 判定        
        assertEquals("true", req.getAttribute("AUTHENTICATION_THRU_KEY"));
        assertTrue(chain.isCalled);
    }

    /**
     * testDoFilter03()
     * <br><br>
     * 
     * (異常系)
     * <br>
     * 観点：G
     * <br><br>
     * 入力値：(引数) req:not null<br>
     *         (引数) res:not null<br>
     *         (引数) chain:not null<br>
     *         (状態) AUTHENTICATION_THRU_KEY:null<br>
     *         (状態) authenticationController:AuthenticationControlFilter_AuthenticationControllerStub02<br>
     *         (状態) .isCheckRequired():true<br>
     *         (状態) .isAuthenticated():false<br>
     *         
     * <br>
     * 期待値：(状態変化) AUTHENTICATION_THRU_KEY:"true"<br>
     *         (状態変化) chain.doFilter:実行しない<br>
     *         (状態変化) 例外:UnauthenticatedException：<br>
     *         
     * <br>
     * 別業務への遷移チェックがtureで、ログオン済みチェックがfalseの場合、
     * requestの属性値AUTHENTICATION_THRU_KEYを設定して
     * UnauthenticatedExceptionが発生すること。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testDoFilter03() throws Exception {
        // 前処理
        AuthenticationControlFilter filter = new AuthenticationControlFilter();

        // テストデータの設定
        AuthenticationControlFilter_AuthenticationControllerStub02 controller
            = new AuthenticationControlFilter_AuthenticationControllerStub02();
        UTUtil.setPrivateField(AuthenticationControlFilter.class,
                               "controller",
                               controller);
        
        MockHttpServletRequest req = new MockHttpServletRequest();
        req.setRequestURI("/test/logon.do");
        req.setContextPath("/test");
        MockHttpServletResponse res = new MockHttpServletResponse();
        AuthenticationControlFilter_FilterChainStub01 chain
            = new AuthenticationControlFilter_FilterChainStub01();

        // テスト実行
        try {
            filter.doFilter(req, res, chain);
            fail();
        } catch (UnauthenticatedException e) {
            // 判定
            assertEquals("true", req.getAttribute("AUTHENTICATION_THRU_KEY"));
            assertFalse(chain.isCalled);
        }
    }

    /**
     * testDoFilter04()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) req:not null<br>
     *         (引数) res:not null<br>
     *         (引数) chain:not null<br>
     *         (状態) AUTHENTICATION_THRU_KEY:null<br>
     *         (状態) authenticationController:AuthenticationControlFilter_AuthenticationControllerStub03<br>
     *         (状態) .isCheckRequired():true<br>
     *         (状態) .isAuthenticated():true<br>
     *         
     * <br>
     * 期待値：(状態変化) AUTHENTICATION_THRU_KEY:"true"<br>
     *         (状態変化) chain.doFilter:実行する<br>
     *         
     * <br>
     * 別業務への遷移チェック、ログオン済みチェックがtrueの場合、
     * requestの属性値AUTHENTICATION_THRU_KEYを設定して処理を終了すること。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testDoFilter04() throws Exception {
        // 前処理
        AuthenticationControlFilter filter = new AuthenticationControlFilter();

        // テストデータの設定
        AuthenticationControlFilter_AuthenticationControllerStub03 controller
            = new AuthenticationControlFilter_AuthenticationControllerStub03();
        UTUtil.setPrivateField(AuthenticationControlFilter.class,
                               "controller",
                               controller);
        
        MockHttpServletRequest req = new MockHttpServletRequest();
        req.setRequestURI("/test/logon.do");
        req.setContextPath("/test");
        MockHttpServletResponse res = new MockHttpServletResponse();
        AuthenticationControlFilter_FilterChainStub01 chain
            = new AuthenticationControlFilter_FilterChainStub01();

        // テスト実行
        filter.doFilter(req, res, chain);
        
        // 判定
        assertEquals("true", req.getAttribute("AUTHENTICATION_THRU_KEY"));
        assertTrue(chain.isCalled);
    }

    /**
     * testDoFilter05()
     * <br><br>
     * 
     * (異常系)
     * <br>
     * 観点：G
     * <br><br>
     * 入力値：(引数) req:not null<br>
     *         (引数) res:not null<br>
     *         (引数) chain:not null<br>
     *         (状態) AUTHENTICATION_THRU_KEY:null<br>
     *         (状態) authenticationController:null<br>
     *         
     * <br>
     * 期待値：(状態変化) chain.doFilter:実行しない<br>
     *         (状態変化) 例外:NullPointerException：<br>
     *         
     * <br>
     * authenticationControllerがnullの場合、
     * NullPointerExceptionが発生すること。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testDoFilter05() throws Exception {
        // 前処理
        AuthenticationControlFilter filter = new AuthenticationControlFilter();

        // テストデータの設定       
        MockHttpServletRequest req = new MockHttpServletRequest();
        req.setRequestURI("/test/logon.do");
        req.setContextPath("/test");
        MockHttpServletResponse res = new MockHttpServletResponse();
        AuthenticationControlFilter_FilterChainStub01 chain
            = new AuthenticationControlFilter_FilterChainStub01();

        // テスト実行
        try {
            filter.doFilter(req, res, chain);
            fail();
        } catch (NullPointerException e) {
            // 判定
            assertEquals("true", req.getAttribute("AUTHENTICATION_THRU_KEY"));
            assertFalse(chain.isCalled);
        }
    }

    /**
     * testGetControllerClass01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(状態) AUTHENTICATION_CONTROLLER_CLASS:AuthenticationController.class<br>
     *                ※static final<br>
     *         
     * <br>
     * 期待値：(戻り値) Class:前提条件で設定したインスタンスと同一のインスタンス<br>
     *         
     * <br>
     * 正常系1件のみテスト
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetControllerClass01() throws Exception {
        // 前処理
        AuthenticationControlFilter filter = new AuthenticationControlFilter();

        // テスト実施
        Class actual = filter.getControllerClass();

        // 判定
        assertEquals(AuthenticationController.class, actual);
    }

    /**
     * testGetErrorCode01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(状態) AUTHENTICATION_CONTROLLER_ERROR:"errors.authentication.controller"<br>
     *                ※static final<br>
     *         
     * <br>
     * 期待値：(戻り値) String:"errors.authentication.controller"<br>
     *         
     * <br>
     * 正常系1件のみテスト
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetErrorCode01() throws Exception {
        // 前処理
        AuthenticationControlFilter filter = new AuthenticationControlFilter();

        // テスト実施
        String actual = filter.getErrorCode();

        // 判定
        assertEquals("errors.authentication.controller", actual);
    }

    /**
     * testGetDefaultControllerBeanId01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(状態) DEFAULT_AUTHENTICATION_BEAN_ID:"authenticationController"<br>
     *                ※static final<br>
     *         
     * <br>
     * 期待値：(戻り値) String:"authenticationController"<br>
     *         
     * <br>
     * 正常系1件のみテスト
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetDefaultControllerBeanId01() throws Exception {
        // 前処理
        AuthenticationControlFilter filter = new AuthenticationControlFilter();

        // テスト実施
        String actual = filter.getDefaultControllerBeanId();

        // 判定
        assertEquals("authenticationController", actual);
    }



}
