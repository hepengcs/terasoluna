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

package jp.terasoluna.fw.web.rich.context.support;

import jp.terasoluna.utlib.MockHttpServletRequest;
import jp.terasoluna.utlib.UTUtil;
import jp.terasoluna.fw.web.rich.context.RequestContext;
import jp.terasoluna.fw.web.rich.context.support.DefaultRequestContextSupportImpl;
import junit.framework.TestCase;

/**
 * {@link jp.terasoluna.fw.web.rich.context.support.DefaultRequestContextSupportImpl} クラスのブラックボックステスト。
 * 
 * <p>
 * <h4>【クラスの概要】</h4>
 * 制御情報を扱うための補助ロジックのデフォルト実装クラス。<br>
 * 前提条件：HTTPリクエストはNull値にならない。
 * <p>
 * 
 * @see jp.terasoluna.fw.web.rich.context.support.DefaultRequestContextSupportImpl
 */
public class DefaultRequestContextSupportImplTest extends TestCase {

    /**
     * このテストケースを実行する為の
     * GUI アプリケーションを起動する。
     * 
     * @param args java コマンドに設定されたパラメータ
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(DefaultRequestContextSupportImplTest.class);
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
    public DefaultRequestContextSupportImplTest(String name) {
        super(name);
    }

    /**
     * testSetRequestNameHeaderKey01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) requestName:"command"<br>
     *         (状態) this.requestNameHeaderKey:"requestName"<br>
     *         
     * <br>
     * 期待値：(状態変化) this.requestNameHeaderKey:"command"<br>
     *         
     * <br>
     * リクエスト名を保持するリクエストヘッダ名を設定する属性のsetterメソッドのテスト。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetRequestNameHeaderKey01() throws Exception {
        // テスト実施
        DefaultRequestContextSupportImpl target = new DefaultRequestContextSupportImpl();
        target.setRequestNameHeaderKey("command");

        // 判定
        assertEquals("command", UTUtil.getPrivateField(target, "requestNameHeaderKey"));
    }

    /**
     * testDoGenerateContext01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) request:リクエストヘッダ<br>
     *                requestName=sum<br>
     *         (状態) this.requestName:"requestName"<br>
     *         
     * <br>
     * 期待値：(戻り値) RequestContext:RequestContext｛<br>
     *                  　requestName="sum"<br>
     *                  ｝<br>
     *         (状態変化) getBLogicContext().getPropertyString():メソッドが呼び出されたことを確認する。<br>
     *         
     * <br>
     * リクエストコンテキストを生成するメソッド。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testDoGenerateContext01() throws Exception {
        // 前処理
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setHeader("requestName", "sum");
        
        // テスト実施
        DefaultRequestContextSupportImpl target = new DefaultRequestContextSupportImpl();
        RequestContext result = target.doGenerateContext(request);

        // 判定
        assertEquals("sum", result.getRequestName());
    }

}
