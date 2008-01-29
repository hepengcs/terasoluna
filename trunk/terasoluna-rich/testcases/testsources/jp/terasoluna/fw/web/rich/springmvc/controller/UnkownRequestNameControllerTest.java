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
import jp.terasoluna.fw.web.rich.springmvc.controller.UnkownRequestNameController;
import jp.terasoluna.fw.web.rich.springmvc.exception.UnknownRequestNameException;
import junit.framework.TestCase;

/**
 * {@link jp.terasoluna.fw.web.rich.springmvc.controller.
 * UnkownRequestNameController} クラスのブラックボックステスト。
 * 
 * <p>
 * <h4>【クラスの概要】</h4>
 * 不正なリクエスト名がリクエストされてきた場合に実行される
 * リクエストコントローラ。<br>
 * 前提条件：HTTPリクエスト、HTTPレスポンスはNull値にならない。
 * <p>
 * 
 * @see jp.terasoluna.fw.web.rich.springmvc.controller.
 * UnkownRequestNameController
 */
public class UnkownRequestNameControllerTest extends TestCase {

    /**
     * このテストケースを実行する為の
     * GUI アプリケーションを起動する。
     * 
     * @param args java コマンドに設定されたパラメータ
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(UnkownRequestNameControllerTest.class);
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
    public UnkownRequestNameControllerTest(String name) {
        super(name);
    }

    /**
     * testHandleRequest01()
     * <br><br>
     * 
     * (異常系)
     * <br>
     * 観点：G
     * <br><br>
     * 入力値：(引数) request:使用しない<br>
     *         (引数) response:使用しない<br>
     *         
     * <br>
     * 期待値：(状態変化) 例外:UnknownRequestNameException<br>
     *         
     * <br>
     * リクエストコントローラが見つからない場合に呼ばれるコントローラのテスト。
     * 必ず例外を発生させる。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testHandleRequest01() throws Exception {
        // 前処理
        UnkownRequestNameController controller = 
            new UnkownRequestNameController();
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        // テスト実施
        try {
            controller.handleRequest(request, response);
            fail();
        } catch (UnknownRequestNameException e) {
            // OK
        	return;
        }
    }
}
