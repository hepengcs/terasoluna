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

package jp.terasoluna.fw.web.rich.springmvc.servlet.handler;

import java.util.HashMap;
import java.util.Map;

import jp.terasoluna.utlib.MockHttpServletRequest;
import jp.terasoluna.utlib.UTUtil;
import jp.terasoluna.fw.web.rich.springmvc.servlet.handler.ErrorPageHandlerMapping;
import junit.framework.TestCase;

/**
 * {@link jp.terasoluna.fw.web.rich.springmvc.servlet.handler.ErrorPageHandlerMapping} クラスのブラックボックステスト。
 * 
 * <p>
 * <h4>【クラスの概要】</h4>
 * リクエストが保持する例外の有無によってコントローラを返すハンドラ。<br>
 * 前提条件：
 * <p>
 * 
 * @see jp.terasoluna.fw.web.rich.springmvc.servlet.handler.ErrorPageHandlerMapping
 */
public class ErrorPageHandlerMappingTest extends TestCase {

    /**
     * このテストケースを実行する為の
     * GUI アプリケーションを起動する。
     * 
     * @param args java コマンドに設定されたパラメータ
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(ErrorPageHandlerMappingTest.class);
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
    public ErrorPageHandlerMappingTest(String name) {
        super(name);
    }

    /**
     * testGetBeanId01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(状態) this.beanId:"/testController"<br>
     *         
     * <br>
     * 期待値：(戻り値) -:"/testController"<br>
     *         
     * <br>
     * beanId属性のgetメソッドのテスト。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetBeanId01() throws Exception {
        // 前処理
        ErrorPageHandlerMapping hm = new ErrorPageHandlerMapping();
        UTUtil.setPrivateField(hm, "beanId", "/testController");

        // テスト実施
        assertEquals("/testController", hm.getBeanId());
    }

    /**
     * testSetBeanId01()
     * <br><br>
     * 
     * (正常系) 
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) beanId:"/testController"<br>
     *         (状態) this.beanId:null<br>
     *         
     * <br>
     * 期待値：(状態変化) this.beanId:"/testController"<br>
     *         
     * <br>
     * beanId属性のsetメソッドのテスト。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetBeanId01() throws Exception {
        // 前処理
        ErrorPageHandlerMapping hm = new ErrorPageHandlerMapping();

        // テスト実施
        hm.setBeanId("/testController");

        // 判定
        assertEquals("/testController", UTUtil.getPrivateField(hm, "beanId"));
    }

    /**
     * testGetHandlerInternal01()
     * <br><br>
     * 
     * (正常系) 
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) request:not null<br>
     *                (javax.servlet.error.exception={Exceptionオブジェクト}）<br>
     *         (状態) this.handlerMap:Map{<br>
     *                  "/exceptionController"=オブジェクト<br>
     *                }<br>
     *         
     * <br>
     * 期待値：(戻り値) Object:this.handlerMapで保持しているオブジェクト<br>
     *         
     * <br>
     * リクエストで例外を保持していたため、<br>
     * 例外用コントローラを返すパターン。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    @SuppressWarnings("unchecked")
    public void testGetHandlerInternal01() throws Exception {
        // 前処理
        ErrorPageHandlerMapping hm = new ErrorPageHandlerMapping();
        
        // ハンドラマッピングのプロパティ
        Map handlerMap = new HashMap();
        Object object = new Object();
        handlerMap.put("/exceptionController", object);
        UTUtil.setPrivateField(hm, "handlerMap", handlerMap);
        
        // リクエスト
        Exception exception = new Exception();
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setAttribute("javax.servlet.error.exception", exception);

        // テスト実施
        assertSame(object, hm.getHandlerInternal(request));
    }

    /**
     * testGetHandlerInternal02()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) request:not null<br>
     *                (javax.servlet.error.exception={Exceptionオブジェクト}）<br>
     *         (状態) this.handlerMap:Map{}<br>
     *         
     * <br>
     * 期待値：(戻り値) Object:null<br>
     *         
     * <br>
     * リクエストで例外を保持していたが、<br>
     * 例外用コントローラがなかったパターン。<br>
     * Nullを返す。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetHandlerInternal02() throws Exception {
        // 前処理
        ErrorPageHandlerMapping hm = new ErrorPageHandlerMapping();
        
        // リクエスト
        Exception exception = new Exception();
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setAttribute("javax.servlet.error.exception", exception);

        // テスト実施
        assertNull(hm.getHandlerInternal(request));
    }

    /**
     * testGetHandlerInternal03()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) request:not null<br>
     *         (状態) this.handlerMap:Map{
     *         "/exceptionController"=オブジェクト}<br>
     *         
     * <br>
     * 期待値：(戻り値) Object:null<br>
     *         
     * <br>
     * リクエストで例外が発生していなかったパターン。Nullを返す。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    @SuppressWarnings("unchecked")
    public void testGetHandlerInternal03() throws Exception {
        // 前処理
        ErrorPageHandlerMapping hm = new ErrorPageHandlerMapping();
        
        // ハンドラマッピングのプロパティ
        Map handlerMap = new HashMap();
        Object object = new Object();
        handlerMap.put("/exceptionController", object);
        UTUtil.setPrivateField(hm, "handlerMap", handlerMap);
        
        // リクエスト
        MockHttpServletRequest request = new MockHttpServletRequest();

        // テスト実施
        assertNull(hm.getHandlerInternal(request));
    }

}
