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
 * {@link jp.terasoluna.fw.web.thin.BlockageControlFilter} クラスのブラックボックステスト。
 * 
 * <p>
 * <h4>【クラスの概要】</h4>
 * 業務閉塞状態かどうかのチェックを行う。<br>
 * アクセスしたパスが業務閉塞状態だった場合は、{@link BlockageException} をスローする。
 * <p>
 * 
 * @see jp.terasoluna.fw.web.thin.BlockageControlFilter
 */
public class BlockageControlFilterTest extends TestCase {

    /**
     * このテストケースを実行する為の
     * GUI アプリケーションを起動する。
     * 
     * @param args java コマンドに設定されたパラメータ
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(BlockageControlFilterTest.class);
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
        UTUtil.setPrivateField(BlockageControlFilter.class, 
                               "controller",
                               null);   
    }

    /**
     * コンストラクタ。
     * 
     * @param name このテストケースの名前。
     */
    public BlockageControlFilterTest(String name) {
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
     *         (状態) getController():BlockageControlFilter_BlockageControllerStub01インスタンス<br>
     *         (状態) controller:BlockageControlFilter_BlockageControllerStub01インスタンス<br>
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
    	MockFilterConfig existingConfig = new MockFilterConfig();
    	BlockageController existingController = 
    		new BlockageControlFilter_BlockageControllerStub01();
    	
    	UTUtil.setPrivateField(BlockageControlFilter.class,
                    			"controller",
                        		existingController);
    	// フィルタの用意
    	BlockageControlFilter_BlockageControlFilterStub01 filter = 
    		new BlockageControlFilter_BlockageControlFilterStub01();
    	
    	filter.setConfig(existingConfig);
    	
    	// 新しく設定されるコンフィグ
    	MockFilterConfig newConfig = new MockFilterConfig();
    	
    	BlockageControlFilter_BlockageControllerStub01 newController = 
    		new BlockageControlFilter_BlockageControllerStub01();
    	
    	filter.newController = newController;
    	
        // テスト実施
    	filter.init(newConfig);
        // 判定
    	assertSame(newConfig,UTUtil.getPrivateField(filter,"config"));
    	assertFalse(filter.isCalled);
    	assertSame(existingController,
    				UTUtil.getPrivateField(BlockageControlFilter.class,
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
     *         (状態) getController():BlockageControlFilter_BlockageControllerStub01インスタンス<br>
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
    	BlockageControlFilter_BlockageControlFilterStub01 filter =
    		new BlockageControlFilter_BlockageControlFilterStub01();
    	
    	// 新しく設定されるコンフィグ
    	MockFilterConfig newConfig = new MockFilterConfig();
    	
    	BlockageControlFilter_BlockageControllerStub01 newController =
    		new BlockageControlFilter_BlockageControllerStub01();
    	
    	filter.newController = newController;
    	
        // テスト実施
    	filter.init(newConfig);
        // 判定
    	assertSame(newConfig,UTUtil.getPrivateField(filter,"config"));
    	assertTrue(filter.isCalled);
    	assertSame(newController,
        			UTUtil.getPrivateField(BlockageControlFilter.class,
        					"controller"));
    }

    /**
     * testGetBlockageController01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(状態) controller:BlockageControlFilter_BlockageControllerStub01インスタンス<br>
     *         
     * <br>
     * 期待値：(戻り値) BlockageController:前提条件で設定したインスタンスと同一のインスタンス<br>
     *         
     * <br>
     * 正常系1件のみテスト
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetBlockageController01() throws Exception {
        // 前処理
    	BlockageController existingController = 
    		new BlockageControlFilter_BlockageControllerStub01();
    	
    	UTUtil.setPrivateField(BlockageControlFilter.class,
                    			"controller",
                                existingController);

        // テスト実施
        BlockageController actual =
            BlockageControlFilter.getBlockageController();
        
        // 判定
        assertSame(existingController,actual);
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
     *         (状態) BLOCKAGE_THRU_KEY:"aaaaa"<br>
     *         (状態) blockageController:BlockageControlFilter_BlockageControllerStub01<br>
     *         
     * <br>
     * 期待値：(状態変化) BLOCKAGE_THRU_KEY:前提条件と同じ値<br>
     *         (状態変化) chain.doFilter:実行する<br>
     *         
     * <br>
     * requestの属性値であるBLOCKAGE_THRU_KEYがnullでない場合、別業務への遷移チェックを行わずに終了すること。<br>
     * FilterChain#doFilter()メソッドを実行すること。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testDoFilter01() throws Exception {
        // 前処理
        BlockageController newController =
            new BlockageControlFilter_BlockageControllerStub01();
        
        BlockageControlFilter filter = new BlockageControlFilter();
        UTUtil.setPrivateField(filter, "controller", newController);
        
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        request.setAttribute("BLOCKAGE_THRU_KEY","aaaaa");
        
        BlockageControlFilter_FilterChainStub01 filterChain =
            new BlockageControlFilter_FilterChainStub01();
        
        // テスト実施
        filter.doFilter(request,response,filterChain);
        // 判定
        assertEquals("aaaaa", request.getAttribute("BLOCKAGE_THRU_KEY"));
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
     *         (状態) BLOCKAGE_THRU_KEY:null<br>
     *         (状態) blockageController:BlockageControlFilter_BlockageControllerStub01<br>
     *         (状態) .isCheckRequired():false<br>
     *         
     * <br>
     * 期待値：(状態変化) BLOCKAGE_THRU_KEY:"true"<br>
     *         (状態変化) chain.doFilter:実行する<br>
     *         
     * <br>
     * 別業務への遷移チェックがfalseの場合、以降のチェックを行わず、requestの属性値BLOCKAGE_THRU_KEYにtrueを設定して処理を終了すること。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testDoFilter02() throws Exception {
        // 前処理
        BlockageController newController = 
            new BlockageControlFilter_BlockageControllerStub01();
        
        BlockageControlFilter filter = new BlockageControlFilter();
        UTUtil.setPrivateField(filter, "controller", newController);
        
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        
        BlockageControlFilter_FilterChainStub01 filterChain =
            new BlockageControlFilter_FilterChainStub01();
        
        // テスト実施
        filter.doFilter(request,response,filterChain);
        // 判定
        assertEquals("true", request.getAttribute("BLOCKAGE_THRU_KEY"));
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
     *         (状態) BLOCKAGE_THRU_KEY:null<br>
     *         (状態) blockageController:BlockageControlFilter_BlockageControllerStub02<br>
     *         (状態) .isCheckRequired():true<br>
     *         (状態) .isBlockaded():true<br>
     *         
     * <br>
     * 期待値：(状態変化) BLOCKAGE_THRU_KEY:"true"<br>
     *         (状態変化) chain.doFilter:実行しない<br>
     *         (状態変化) 例外:BlockageException：<br>
     *         
     * <br>
     * 別業務への遷移チェックがtureで、業務閉塞チェックがtrueの場合、requestの属性値BLOCKAGE_THRU_KEYを設定してBlockageExceptionが発生すること。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testDoFilter03() throws Exception {
        // 前処理
        BlockageController newController = 
            new BlockageControlFilter_BlockageControllerStub02();
        
        BlockageControlFilter filter = new BlockageControlFilter();
        UTUtil.setPrivateField(filter, "controller", newController);
        
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRequestURI("/test/logon.do");
        request.setContextPath("/test");
        MockHttpServletResponse response = new MockHttpServletResponse();
        
        BlockageControlFilter_FilterChainStub01 filterChain =
            new BlockageControlFilter_FilterChainStub01();
        
        // テスト実施
        try {
            filter.doFilter(request, response, filterChain);
            fail();
        } catch (BlockageException ex) {
            // 判定
            assertEquals("true", request.getAttribute("BLOCKAGE_THRU_KEY"));
            assertFalse(filterChain.isCalled);
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
     *         (状態) BLOCKAGE_THRU_KEY:null<br>
     *         (状態) blockageController:BlockageControlFilter_BlockageControllerStub03<br>
     *         (状態) .isCheckRequired():true<br>
     *         (状態) .isBlockaded():false<br>
     *         
     * <br>
     * 期待値：(状態変化) BLOCKAGE_THRU_KEY:"true"<br>
     *         (状態変化) chain.doFilter:実行する<br>
     *         
     * <br>
     * 別業務への遷移チェックがtrue、業務閉塞チェックがfalseの場合、requestの属性値BLOCKAGE_THRU_KEYを設定して処理を終了すること。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testDoFilter04() throws Exception {
        // 前処理
        BlockageController newController = 
            new BlockageControlFilter_BlockageControllerStub03();
        
        BlockageControlFilter filter = new BlockageControlFilter();
        UTUtil.setPrivateField(filter, "controller", newController);
        
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRequestURI("/test/logon.do");
        request.setContextPath("/test");
        MockHttpServletResponse response = new MockHttpServletResponse();
        
        BlockageControlFilter_FilterChainStub01 filterChain = 
            new BlockageControlFilter_FilterChainStub01();
        
        // テスト実施]
        filter.doFilter(request, response, filterChain);
        // 判定
        assertEquals("true", request.getAttribute("BLOCKAGE_THRU_KEY"));
        assertTrue(filterChain.isCalled);
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
     *         (状態) BLOCKAGE_THRU_KEY:null<br>
     *         (状態) blockageController:null<br>
     *         
     * <br>
     * 期待値：(状態変化) chain.doFilter:実行しない<br>
     *         (状態変化) 例外:NullPointerException：<br>
     *         
     * <br>
     * blockageControllerがnullの場合、NullPointerExceptionが発生すること。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testDoFilter05() throws Exception {
        // 前処理
        
        BlockageControlFilter filter = new BlockageControlFilter();
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRequestURI("/test/logon.do");
        request.setContextPath("/test");
        MockHttpServletResponse response = new MockHttpServletResponse();
        
        BlockageControlFilter_FilterChainStub01 filterChain = 
            new BlockageControlFilter_FilterChainStub01();
        
        // テスト実施
        try {
            filter.doFilter(request, response, filterChain);
            fail();
        } catch (NullPointerException e) {
            // 判定
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
     * 入力値：(状態) BLOCKAGE_CONTROLLER_CLASS:BlockageController.class<br>
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
        BlockageControlFilter filter = new BlockageControlFilter();
        
        // テスト実施
        Class actual = filter.getControllerClass();
        
        // 判定
        assertEquals(BlockageController.class,actual);
    }

    /**
     * testGetErrorCode01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(状態) BLOCKAGE_CONTROLLER_ERROR:"errors.blockage.controller"<br>
     *                ※static final<br>
     *         
     * <br>
     * 期待値：(戻り値) String:"errors.blockage.controller"<br>
     *         
     * <br>
     * 正常系1件のみテスト
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetErrorCode01() throws Exception {
        // 前処理
        BlockageControlFilter filter = new BlockageControlFilter();
        
        // テスト実施
        String actual = filter.getErrorCode();
        
        // 判定
        assertEquals("errors.blockage.controller",actual);
    }

    /**
     * testGetDefaultControllerBeanId01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(状態) DEFAULT_BLOCKAGE_BEAN_ID:"blockageController"<br>
     *                ※static final<br>
     *         
     * <br>
     * 期待値：(戻り値) String:"blockageController"<br>
     *         
     * <br>
     * 正常系1件のみテスト
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetDefaultControllerBeanId01() throws Exception {
        // 前処理
        BlockageControlFilter filter = new BlockageControlFilter();
        
        // テスト実施
        String actual = filter.getDefaultControllerBeanId();
        
        // 判定
        assertEquals("blockageController",actual);
    }

}
