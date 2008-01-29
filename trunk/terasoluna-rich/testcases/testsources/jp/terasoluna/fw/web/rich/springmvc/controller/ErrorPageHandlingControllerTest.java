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

package jp.terasoluna.fw.web.rich.springmvc.controller;

import jp.terasoluna.utlib.MockHttpServletRequest;
import jp.terasoluna.utlib.MockHttpServletResponse;
import jp.terasoluna.fw.web.rich.springmvc.controller.ErrorPageHandlingController;
import junit.framework.TestCase;

/**
 * {@link jp.terasoluna.fw.web.rich.springmvc.controller.ErrorPageHandlingController} クラスのブラックボックステスト。
 * 
 * <p>
 * <h4>【クラスの概要】</h4>
 * web.xmlのerror-page設定による例外ハンドリングを行うときに使用するコントローラ。
 * <p>
 * 
 * @see jp.terasoluna.fw.web.rich.springmvc.controller.ErrorPageHandlingController
 */
public class ErrorPageHandlingControllerTest extends TestCase {

    /**
     * このテストケースを実行する為の
     * GUI アプリケーションを起動する。
     * 
     * @param args java コマンドに設定されたパラメータ
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(ErrorPageHandlingControllerTest.class);
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
    public ErrorPageHandlingControllerTest(String name) {
        super(name);
    }

    /**
     * testHandleRequest01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) request:not null<br>
     *                (javax.servlet.error.exception={Exceptionオブジェクト}）<br>
     *         (引数) response:not null<br>
     *         
     * <br>
     * 期待値：(戻り値) -:引数で保持しているExceptionオブジェクト<br>
     *         
     * <br>
     * handleRequestメソッドのテスト。リクエスト内で保持している例外を投げる。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testHandleRequest01() throws Exception {
        // 前処理
        ErrorPageHandlingController controller = new ErrorPageHandlingController();
        Exception exception = new Exception();
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setAttribute("javax.servlet.error.exception", exception);
        MockHttpServletResponse response = new MockHttpServletResponse();

        // テスト実施
        try {
            controller.handleRequest(request, response);
            fail();
        } catch(IllegalStateException e) {
            fail();
        } catch(Exception e) {
            assertSame(exception, e);
        }
    }

    /**
     * testHandleRequest02()
     * <br><br>
     * 
     * (異常系)
     * <br>
     * 観点：A,G
     * <br><br>
     * 入力値：(引数) request:not null<br>
     *         (引数) response:not null<br>
     *         
     * <br>
     * 期待値：(状態変化) 例外:IllegalStateException<br>
     *         
     * <br>
     * handleRequestメソッドのテスト。リクエスト内で保持している例外がないため、<br>
     * IllegalStateExceptionをスローする。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testHandleRequest02() throws Exception {
        // 前処理
        ErrorPageHandlingController controller = new ErrorPageHandlingController();
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        // テスト実施
        try {
            controller.handleRequest(request, response);
            fail();
        } catch(IllegalStateException e) {
            // OK
        	return;
        }
    }

}
