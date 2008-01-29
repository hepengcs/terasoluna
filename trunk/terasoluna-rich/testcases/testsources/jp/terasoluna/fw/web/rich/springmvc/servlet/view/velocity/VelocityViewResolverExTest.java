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

package jp.terasoluna.fw.web.rich.springmvc.servlet.view.velocity;

import org.springframework.web.servlet.view.velocity.VelocityView;

import jp.terasoluna.utlib.UTUtil;
import jp.terasoluna.fw.web.rich.springmvc.servlet.view.velocity.VelocityViewResolverEx;
import junit.framework.TestCase;

/**
 * {@link jp.terasoluna.fw.web.rich.springmvc.servlet.view.velocity.
 * VelocityViewResolverEx} クラスのブラックボックステスト。
 * 
 * <p>
 * <h4>【クラスの概要】</h4>
 * エンコーディング指定が可能なVelocityViewResolver拡張クラス。<br>
 * 前提条件：buidlViewメソッドの引数viewNameには空文字、Nullは入らない。
 * <p>
 * 
 * @see jp.terasoluna.fw.web.rich.springmvc.servlet.view.velocity.
 * VelocityViewResolverEx
 */
public class VelocityViewResolverExTest extends TestCase {

    /**
     * このテストケースを実行する為の
     * GUI アプリケーションを起動する。
     * 
     * @param args java コマンドに設定されたパラメータ
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(VelocityViewResolverExTest.class);
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
    public VelocityViewResolverExTest(String name) {
        super(name);
    }

    /**
     * testGetEncoding01()
     * <br><br>
     * 
     * (正常系) 
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(状態) this.encoding:not null<br>
     *                (”text/xml;charset=UTF-8”)<br>
     *         
     * <br>
     * 期待値：(戻り値) String:前提条件で指定したthis.encodingの値。<br>
     *         
     * <br>
     * encoding属性のgetメソッドのテスト。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetEncoding01() throws Exception {
        // 前処理
        VelocityViewResolverEx viewResolver = new VelocityViewResolverEx();
        String encoding = "text/xml;charset=UTF-8";
        UTUtil.setPrivateField(viewResolver, "encoding", encoding);

        // テスト実施
        Object result = viewResolver.getEncoding();

        // 判定
        assertEquals(encoding, result);
    }

    /**
     * testSetEncoding01()
     * <br><br>
     * 
     * (正常系) 
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) encoding:not null<br>
     *                (”text/xml;charset=UTF-8”)<br>
     *         (状態) this.encoding:null<br>
     *         
     * <br>
     * 期待値：(状態変化) this.encoding:引数で設定した値が設定されている。<br>
     *         
     * <br>
     * encoding属性のsetメソッドのテスト。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetEncoding01() throws Exception {
        // 前処理
        VelocityViewResolverEx viewResolver = new VelocityViewResolverEx();
        UTUtil.setPrivateField(viewResolver, "encoding", null);

        // テスト実施
        String encoding = "text/xml;charset=UTF-8";
        viewResolver.setEncoding(encoding);

        // 判定
        Object result = UTUtil.getPrivateField(viewResolver, "encoding");
        assertEquals(encoding, result);
    }

    /**
     * testBuildView01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) viewName:”sum”<br>
     *         (状態) this.encoding:not null<br>
     *         
     * <br>
     * 期待値：(戻り値) AbstractUrlBasedView:
     *         前提条件のencodingが設定されたVelocityビュー<br>
     *         (状態変化) super.buildView():
     *         メソッドが呼び出されたことを確認する。<br>
     *                    引数を受け取ったことを確認する。<br>
     *         
     * <br>
     * Velocityビューの正常系テスト。エンコーディングが指定されているか確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testBuildView01() throws Exception {
        // 前処理
        VelocityViewResolverEx viewResolver = new VelocityViewResolverEx();
        String encoding = "text/xml;charset=UTF-8";
        UTUtil.setPrivateField(viewResolver, "encoding", encoding);

        // テスト実施
        String viewName = "sum";
        Object result = viewResolver.buildView(viewName);

        // 判定
        assertTrue(result instanceof VelocityView);
        VelocityView resultView = (VelocityView) result;
        assertEquals(encoding, UTUtil.getPrivateField(resultView, "encoding"));
        assertEquals(viewName, UTUtil.getPrivateField(resultView, "url"));
    }

    /**
     * testBuildView02()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) viewName:”sum”<br>
     *         (状態) this.encoding:null<br>
     *         
     * <br>
     * 期待値：(戻り値) AbstractUrlBasedView:
     *         前提条件のencodingが設定されたVelocityビュー<br>
     *         (状態変化) super.buildView():
     *         メソッドが呼び出されたことを確認する。<br>
     *                    引数を受け取ったことを確認する。<br>
     *         
     * <br>
     * VelocityビューのエンコーディングがNullの場合のテスト。
     * エンコーディングNullのVelocityビューが作成される。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testBuildView02() throws Exception {
        // 前処理
        VelocityViewResolverEx viewResolver = new VelocityViewResolverEx();
        UTUtil.setPrivateField(viewResolver, "encoding", null);

        // テスト実施
        String viewName = "sum";
        Object result = viewResolver.buildView(viewName);

        // 判定
        assertTrue(result instanceof VelocityView);
        VelocityView resultView = (VelocityView) result;
        assertEquals(null, UTUtil.getPrivateField(resultView, "encoding"));
        assertEquals(viewName, UTUtil.getPrivateField(resultView, "url"));
    }

    /**
     * testBuildView03()
     * <br><br>
     * 
     * (正常系) 
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) viewName:”sum”<br>
     *         (状態) this.encoding:not null<br>
     *                （空文字）<br>
     *         
     * <br>
     * 期待値：(戻り値) AbstractUrlBasedView:
     *         前提条件のencodingが設定されたVelocityビュー<br>
     *         (状態変化) super.buildView():
     *         メソッドが呼び出されたことを確認する。<br>
     *                    引数を受け取ったことを確認する。<br>
     *         
     * <br>
     * Velocityビューのエンコーディングが空文字の場合のテスト。
     * エンコーディングが空文字のVelocityビューが作成される。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testBuildView03() throws Exception {
        // 前処理
        VelocityViewResolverEx viewResolver = new VelocityViewResolverEx();
        String encoding = "";
        UTUtil.setPrivateField(viewResolver, "encoding", encoding);

        // テスト実施
        String viewName = "sum";
        Object result = viewResolver.buildView(viewName);

        // 判定
        assertTrue(result instanceof VelocityView);
        VelocityView resultView = (VelocityView) result;
        assertEquals(encoding, UTUtil.getPrivateField(resultView, "encoding"));
        assertEquals(viewName, UTUtil.getPrivateField(resultView, "url"));
    }

}
