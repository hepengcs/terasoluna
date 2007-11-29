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
 * {@link jp.terasoluna.fw.web.thin.ServerBlockageControlFilter} クラスのブラックボックステスト。
 * 
 * <p>
 * <h4>【クラスの概要】</h4>
 * サーバ閉塞状態かどうかのチェックを行う。<br>
 * サーバ閉塞状態だった場合は、{@link ServerBlockageException}をスローする。
 * <p>
 * 
 * @see jp.terasoluna.fw.web.thin.ServerBlockageControlFilter
 */
public class ServerBlockageControlFilterTest extends TestCase {

    /**
     * このテストケースを実行する為の
     * GUI アプリケーションを起動する。
     * 
     * @param args java コマンドに設定されたパラメータ
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(ServerBlockageControlFilterTest.class);
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
        UTUtil.setPrivateField(ServerBlockageControlFilter.class, 
                               "controller",
                               null);  
    }

    /**
     * コンストラクタ。
     * 
     * @param name このテストケースの名前。
     */
    public ServerBlockageControlFilterTest(String name) {
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
     * 入力値：(引数) config:not null<br>
     *         (状態) config:not null<br>
     *         (状態) getController():ServerBlockageControlFilter_ServerBlockageControllerStub01インスタンス<br>
     *         (状態) controller:ServerBlockageControlFilter_ServerBlockageControllerStub01インスタンス<br>
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
        ServerBlockageController existingController =
            new ServerBlockageControlFilter_ServerBlockageControllerStub01();
        
        UTUtil.setPrivateField(ServerBlockageControlFilter.class,
                                "controller",
                                existingController);
        // フィルタの用意
        ServerBlockageControlFilter_ServerBlockageControlFilterStub01 filter
            = new ServerBlockageControlFilter_ServerBlockageControlFilterStub01();
        
        filter.setConfig(existingConfig);
        
        // 新しく設定されるコンフィグ
        MockFilterConfig newConfig = new MockFilterConfig();
        // 新しく設定されるコントローラ
        ServerBlockageControlFilter_ServerBlockageControllerStub01 newController 
            = new ServerBlockageControlFilter_ServerBlockageControllerStub01();
        filter.newController = newController;
        
        // テスト実施
        filter.init(newConfig);
        // 判定
        assertSame(newConfig, UTUtil.getPrivateField(filter, "config"));
        assertFalse(filter.isCalled);
        
        assertSame(existingController,
                UTUtil.getPrivateField(ServerBlockageControlFilter.class,
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
     *         (状態) getController():ServerBlockageControlFilter_ServerBlockageControllerStub01インスタンス<br>
     *         (状態) controller:null<br>
     *         
     * <br>
     * 期待値：(状態変化) config:引数で与えたFilterConfigと同一のインスタンス<br>
     *         (状態変化) getController():呼び出される<br>
     *         (状態変化) controller:getController()から得られるインスタンスと同一のインスタンス<br>
     *         
     * <br>
     * controllerがnullの場合、引数で与えたconfigが設定され、getControllerが呼び出され、フィールドに設定されることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testInit02() throws Exception {
        // 前処理
        // フィルタの用意
        ServerBlockageControlFilter_ServerBlockageControlFilterStub01 filter
            = new ServerBlockageControlFilter_ServerBlockageControlFilterStub01();
        
        // 新しく設定されるコンフィグ
        MockFilterConfig newConfig = new MockFilterConfig();
        // 新しく設定されるコントローラ
        ServerBlockageControlFilter_ServerBlockageControllerStub01 newController 
            = new ServerBlockageControlFilter_ServerBlockageControllerStub01();
        filter.newController = newController;
        
        // テスト実施
        filter.init(newConfig);
        // 判定
        assertSame(newConfig, UTUtil.getPrivateField(filter, "config"));
        assertTrue(filter.isCalled);
        
        assertSame(newController,
                UTUtil.getPrivateField(ServerBlockageControlFilter.class,
                        "controller"));
    }

    /**
     * testGetServerBlockageController01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(状態) controller:ServerBlockageControlFilter_ServerBlockageControllerStub01インスタンス<br>
     *         
     * <br>
     * 期待値：(戻り値) ServerBlockageController:前提条件で設定したインスタンスと同一のインスタンス<br>
     *         
     * <br>
     * 正常系1件のみテスト
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetServerBlockageController01() throws Exception {
        // 前処理
        ServerBlockageController existingController =
            new ServerBlockageControlFilter_ServerBlockageControllerStub01();
        
        UTUtil.setPrivateField(ServerBlockageControlFilter.class,
                                "controller",
                                existingController);
        // テスト実施
        ServerBlockageController actual =
            ServerBlockageControlFilter.getServerBlockageController();
        
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
     *         (状態) SERVER_BLOCKAGE_THRU_KEY:"aaaaa"<br>
     *         (状態) serverBlockageController:ServerBlockageControlFilter_ServerBlockageControllerStub01<br>
     *         
     * <br>
     * 期待値：(状態変化) SERVER_BLOCKAGE_THRU_KEY:前提条件と同じ値<br>
     *         (状態変化) chain.doFilter:実行する<br>
     *         
     * <br>
     * requestの属性値であるSERVER_BLOCKAGE_THRU_KEYがnullでない場合、別業務への遷移チェックを行わずに終了すること。<br>
     * FilterChain#doFilter()メソッドを実行すること。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testDoFilter01() throws Exception {
        // 前処理
        ServerBlockageController existingController =
            new ServerBlockageControlFilter_ServerBlockageControllerStub01();
 
        // フィルタの用意
        ServerBlockageControlFilter filter = new ServerBlockageControlFilter();
        // テストデータの設定
        UTUtil.setPrivateField(filter,
                                "controller",
                                existingController);
        
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        request.setAttribute("SERVER_BLOCKAGE_THRU_KEY", "aaaaa");
        
        ServerBlockageControlFilter_FilterChainStub01 filterChain 
            = new ServerBlockageControlFilter_FilterChainStub01();
  
        // テスト実施
        filter.doFilter(request, response, filterChain);    
        // 判定
        assertEquals("aaaaa", request.getAttribute("SERVER_BLOCKAGE_THRU_KEY"));
        assertTrue(filterChain.isCalled);
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
     *         (状態) SERVER_BLOCKAGE_THRU_KEY:null<br>
     *         (状態) serverBlockageController:ServerBlockageControlFilter_ServerBlockageControllerStub01<br>
     *         (状態) .isBlockaded():false<br>
     *         
     * <br>
     * 期待値：(状態変化) SERVER_BLOCKAGE_THRU_KEY:"true"<br>
     *         (状態変化) chain.doFilter:実行する<br>
     *         
     * <br>
     * 別業務への遷移チェックがfalseの場合、以降のチェックを行わず、requestの属性値SERVER_BLOCKAGE_THRU_KEYFilterにtrueを設定して処理を終了すること。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testDoFilter02() throws Exception {
        // 前処理
        ServerBlockageController existingController =
            new ServerBlockageControlFilter_ServerBlockageControllerStub01();
 
        // フィルタの用意
        ServerBlockageControlFilter filter = new ServerBlockageControlFilter();
        // テストデータの設定
        UTUtil.setPrivateField(filter,
                                "controller",
                                existingController);
        
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRequestURI("/test/logon.do");
        request.setContextPath("/test");
        MockHttpServletResponse response = new MockHttpServletResponse();
        
        ServerBlockageControlFilter_FilterChainStub01 filterChain 
            = new ServerBlockageControlFilter_FilterChainStub01();
  
        // テスト実施
        filter.doFilter(request, response, filterChain);    
        // 判定
        assertEquals("true", request.getAttribute("SERVER_BLOCKAGE_THRU_KEY"));
        assertTrue(filterChain.isCalled);
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
     *         (状態) SERVER_BLOCKAGE_THRU_KEY:null<br>
     *         (状態) serverBlockageController:ServerBlockageControlFilter_ServerBlockageControllerStub02<br>
     *         (状態) .isBlockaded():true<br>
     *         
     * <br>
     * 期待値：(状態変化) SERVER_BLOCKAGE_THRU_KEY:"true"<br>
     *         (状態変化) chain.doFilter:実行しない<br>
     *         (状態変化) 例外:ServerBlockageException：<br>
     *         
     * <br>
     * 別業務への遷移チェックがtureで、サーバー閉塞チェックがtrueの場合、requestの属性値SERVER_BLOCKAGE_THRU_KEYを設定してServerBlockageExceptionが発生すること。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testDoFilter03() throws Exception {
        // 前処理
        ServerBlockageController existingController =
            new ServerBlockageControlFilter_ServerBlockageControllerStub02();
 
        // フィルタの用意
        ServerBlockageControlFilter filter = new ServerBlockageControlFilter();
        // テストデータの設定
        UTUtil.setPrivateField(filter,
                                "controller",
                                existingController);
        
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRequestURI("/test/logon.do");
        request.setContextPath("/test");
        MockHttpServletResponse response = new MockHttpServletResponse();
        
        ServerBlockageControlFilter_FilterChainStub01 filterChain 
            = new ServerBlockageControlFilter_FilterChainStub01();
  
        // テスト実施
        try {
            filter.doFilter(request, response, filterChain);
            fail();
        } catch (ServerBlockageException ex) {
            // 判定
            assertEquals("true", request.getAttribute("SERVER_BLOCKAGE_THRU_KEY"));
            assertFalse(filterChain.isCalled);            
        }

    }

    /**
     * testDoFilter04()
     * <br><br>
     * 
     * (異常系)
     * <br>
     * 観点：G
     * <br><br>
     * 入力値：(引数) req:not null<br>
     *         (引数) res:not null<br>
     *         (引数) chain:not null<br>
     *         (状態) SERVER_BLOCKAGE_THRU_KEY:null<br>
     *         (状態) serverBlockageController:null<br>
     *         
     * <br>
     * 期待値：(状態変化) chain.doFilter:実行しない<br>
     *         (状態変化) 例外:NullPointerException：<br>
     *         
     * <br>
     * serverBlockageControllerがnullの場合、NullPointerExceptionが発生すること。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testDoFilter04() throws Exception {
        // 前処理
        // フィルタの用意
        ServerBlockageControlFilter filter = new ServerBlockageControlFilter();
        
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRequestURI("/test/logon.do");
        request.setContextPath("/test");
        MockHttpServletResponse response = new MockHttpServletResponse();
        
        ServerBlockageControlFilter_FilterChainStub01 filterChain 
            = new ServerBlockageControlFilter_FilterChainStub01();
  
        // テスト実施
        try {
            filter.doFilter(request, response, filterChain);
            fail();
        } catch (NullPointerException e) {
            // 判定
            // 実行しない
            assertFalse(filterChain.isCalled);
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
     * 入力値：(状態) SERVER_BLOCKAGE_CONTROLLER_CLASS:ServerBlockageController.class<br>
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
        ServerBlockageControlFilter filter = new ServerBlockageControlFilter();
        
        //  テスト実施
        Class actual = filter.getControllerClass();
        
        //  判定
        assertEquals(ServerBlockageController.class, actual);
    }

    /**
     * testGetErrorCode01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(状態) SERVER_BLOCKAGE_CONTROLLER_ERROR:"errors.server.blockage.controller"<br>
     *                ※static final<br>
     *         
     * <br>
     * 期待値：(戻り値) String:"errors.server.blockage.controller"<br>
     *         
     * <br>
     * 正常系1件のみテスト
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetErrorCode01() throws Exception {
        // 前処理
        ServerBlockageControlFilter filter = new ServerBlockageControlFilter();
        
        //  テスト実施
        String actual = filter.getErrorCode();
        
        //  判定
        assertEquals("errors.server.blockage.controller", actual);
    }

    /**
     * testGetDefaultControllerBeanId01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(状態) DEFAULT_SERVER_BLOCKAGE_BEAN_ID:"serverBlocakgeController"<br>
     *                ※static final<br>
     *         
     * <br>
     * 期待値：(戻り値) String:"serverBlocakgeController"<br>
     *         
     * <br>
     * 正常系1件のみテスト
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetDefaultControllerBeanId01() throws Exception {
        // 前処理
        ServerBlockageControlFilter filter = new ServerBlockageControlFilter();
        
        //  テスト実施
        String actual = filter.getDefaultControllerBeanId();
        
        //  判定
        assertEquals("serverBlockageController", actual);
    }

}
