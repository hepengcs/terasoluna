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

package jp.terasoluna.fw.web.rich.context;

import jp.terasoluna.utlib.LogUTUtil;
import jp.terasoluna.utlib.UTUtil;
import jp.terasoluna.fw.web.rich.context.RequestContext;
import jp.terasoluna.fw.web.rich.context.RequestContextManager;
import junit.framework.TestCase;

/**
 * {@link jp.terasoluna.fw.web.rich.context.RequestContextManager} クラスのブラックボックステスト。
 *
 * <p>
 * <h4>【クラスの概要】</h4>
 * 制御情報を管理するマネージャ。<br>
 * 前提条件：ThreadLocalはNull値にならない。
 * <p>
 *
 * @see jp.terasoluna.fw.web.rich.context.RequestContextManager
 */
public class RequestContextManagerTest extends TestCase {

    /**
     * このテストケースを実行する為の
     * GUI アプリケーションを起動する。
     *
     * @param args java コマンドに設定されたパラメータ
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(RequestContextManagerTest.class);
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
    public RequestContextManagerTest(String name) {
        super(name);
    }

    /**
     * testGetRequestContext01()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(状態) this.resources:ThreadLocal<br>
     *                （RequestContext｛<br>
     *                　requestName="test"<br>
     *                ｝）<br>
     *
     * <br>
     * 期待値：(戻り値) RequestContext:ThreadLocal<br>
     *                  （RequestContext｛<br>
     *                  　requestName="test"<br>
     *                  ｝）<br>
     *
     * <br>
     * resources属性のgetterメソッドのテスト。
     * スレッドローカルに設定されている制御情報を返す。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetRequestContext01() throws Exception {
        // 前処理
        ThreadLocal<RequestContext> tlocal = new ThreadLocal<RequestContext>();
        RequestContext ctx = new RequestContext();
        ctx.setRequestName("test");
        tlocal.set(ctx);
        UTUtil.setPrivateField(
                RequestContextManager.class, "resources", tlocal);

        // テスト実施
        // 判定
        assertSame(ctx, RequestContextManager.getRequestContext());
    }

    /**
     * testGetRequestContext02()
     * <br><br>
     *
     * (異常系)
     * <br>
     * 観点：F,G
     * <br><br>
     * 入力値：(状態) this.resources:ThreadLocal<br>
     *                （空）<br>
     *
     * <br>
     * 期待値：(状態変化) 例外:IllegalStateException<br>
     *                    ("No RequestContext  bound to thread [<br>
     *                    (Thread.currentThread().getName())])<br>
     *         (状態変化) ログ:ログレベル：エラー<br>
     *                    No RequestContext bound to thread!<br>
     *
     * <br>
     * resources属性のgetterメソッドのテスト。
     * スレッドローカルに制御情報が設定されていないため、例外が発生する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetRequestContext02() throws Exception {
        // 前処理
        ThreadLocal<RequestContext> tlocal = new ThreadLocal<RequestContext>();
        UTUtil.setPrivateField(
                RequestContextManager.class, "resources", tlocal);

        try {
            // テスト実施
            RequestContextManager.getRequestContext();
            fail();
        } catch (IllegalStateException e) {
            // 判定
            assertEquals("No RequestContext  bound to thread ["
                    + Thread.currentThread().getName() + "]", e.getMessage());
            assertTrue(
                    LogUTUtil.checkError("No RequestContext bound to thread!"));
        }
    }

    /**
     * testHasRequestContext01()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(状態) this.requestName:ThreadLocal<br>
     *                （RequestContext｛<br>
     *                　requestName="test"<br>
     *                ｝）<br>
     *
     * <br>
     * 期待値：(戻り値) boolean:true<br>
     *
     * <br>
     * ThreadLocalに値が設定されているか検査するメソッド。
     * ThreadLocalに実行スレッドのオブジェクトをセットしたパターンのテスト。
     * TRUEを返す。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testHasRequestContext01() throws Exception {
        // 前処理
        ThreadLocal<RequestContext> tlocal = new ThreadLocal<RequestContext>();
        RequestContext ctx = new RequestContext();
        ctx.setRequestName("test");
        tlocal.set(ctx);
        UTUtil.setPrivateField(
                RequestContextManager.class, "resources", tlocal);

        // テスト実施
        // 判定
        assertTrue(RequestContextManager.hasRequestContext());
    }

    /**
     * testHasRequestContext02()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(状態) this.requestName:ThreadLocal<br>
     *                （空）<br>
     *
     * <br>
     * 期待値：(戻り値) boolean:false<br>
     *
     * <br>
     * ThreadLocalに値が設定されているか検査するメソッド。
     * 空のThreadLocalのパターンのテスト。FALSEを返す。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testHasRequestContext02() throws Exception {
        // 前処理
        ThreadLocal<RequestContext> tlocal = new ThreadLocal<RequestContext>();
        UTUtil.setPrivateField(
                RequestContextManager.class, "resources", tlocal);

        // テスト実施
        // 判定
        assertFalse(RequestContextManager.hasRequestContext());
    }

    /**
     * testBindRequestContext01()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) ｃｔｘ:RequestContext｛<br>
     *                　requestName="arg"<br>
     *                ｝<br>
     *         (状態) this.resources:ThreadLocal<br>
     *                （空）<br>
     *
     * <br>
     * 期待値：(状態変化) this.resources:ThreadLocal<br>
     *                    （RequestContext｛<br>
     *                    　requestName="arg"<br>
     *                    ｝）<br>
     *
     * <br>
     * 制御情報を生成するメソッドのテスト。
     * 制御情報が空のスレッドローカルオブジェクトに、コンテキストを設定する。
     * 正常ケース。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testBindRequestContext01() throws Exception {
        // 前処理
        ThreadLocal<RequestContext> tlocal = new ThreadLocal<RequestContext>();
        UTUtil.setPrivateField(
                RequestContextManager.class, "resources", tlocal);

        RequestContext ctx = new RequestContext();
        ctx.setRequestName("arg");

        // テスト実施
        RequestContextManager.bindRequestContext(ctx);

        // 判定
        ThreadLocal t =
            (ThreadLocal) UTUtil.getPrivateField(
                    RequestContextManager.class, "resources");
        Object result = t.get();
        assertSame(ctx, result);
    }

    /**
     * testBindRequestContext02()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F,G
     * <br><br>
     * 入力値：(引数) ｃｔｘ:RequestContext｛<br>
     *                　requestName="arg"<br>
     *                ｝<br>
     *         (状態) this.resources:ThreadLocal<br>
     *                （RequestContext｛<br>
     *                　requestName="test"<br>
     *                ｝）<br>
     *
     * <br>
     * 期待値：(状態変化) 例外:IllegalStateException<br>
     *                    （Already RequestContext [(コンテキストの文字列表現)]<br>
     *                     [（Thread.currentThread().getName()）]）<br>
     *         (状態変化) ログ:ログレベル：エラー<br>
     *                    Already RequestContext bound to thread!<br>
     *
     * <br>
     * 制御情報を生成するメソッドのテスト。
     * 制御情報が既に設定されているスレッドローカルオブジェクトに、
     * コンテキストを設定する。正常ケース。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testBindRequestContext02() throws Exception {
        // 前処理
        ThreadLocal<RequestContext> tlocal = new ThreadLocal<RequestContext>();
        RequestContext rctx = new RequestContext();
        tlocal.set(rctx);
        UTUtil.setPrivateField(
                RequestContextManager.class, "resources", tlocal);

        RequestContext ctx = new RequestContext();
        ctx.setRequestName("arg");

        try {
            // テスト実施
            RequestContextManager.bindRequestContext(ctx);
            fail();
        } catch (IllegalStateException e) {
            // 判定
            assertEquals("Already RequestContext [" + rctx + "]" + "   ["
                    + Thread.currentThread().getName() + "]", e.getMessage());
            assertTrue(LogUTUtil.checkError(
                    "Already RequestContext bound to thread!"));
        }
    }

    /**
     * testBindRequestContext03()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F,G
     * <br><br>
     * 入力値：(引数) ｃｔｘ:null<br>
     *         (状態) this.resources:ThreadLocal<br>
     *                （空）<br>
     *
     * <br>
     * 期待値：(状態変化) 例外:IllegalArgumentException(<br>
     *                    "RequestContext cannot set null.")<br>
     *         (状態変化) ログ:ログレベル：エラー<br>
     *                    RequestContext cannot set null.<br>
     *
     * <br>
     * 制御情報を生成するメソッドのテスト。
     * Nullの制御情報を設定しようとするケース。例外を投げる。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testBindRequestContext03() throws Exception {
        // 前処理
        ThreadLocal<RequestContext> tlocal = new ThreadLocal<RequestContext>();
        UTUtil.setPrivateField(
                RequestContextManager.class, "resources", tlocal);

        // テスト実施
        try {
            RequestContextManager.bindRequestContext(null);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("RequestContext cannot set null.", e.getMessage());
            assertTrue(LogUTUtil.checkError("RequestContext cannot set null."));
        }
    }

    /**
     * testUnbindRequestContext01()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F,G
     * <br><br>
     * 入力値：(状態) this.resources:ThreadLocal<br>
     *                （空）<br>
     *
     * <br>
     * 期待値：(状態変化) 例外:IllegalStateException<br>
     *                    （No RequestContext bound to thread<br>
     *                     [（Thread.currentThread().getName()）]）<br>
     *         (状態変化) ログ:ログレベル：エラー<br>
     *                    No RequestContext bound to thread!<br>
     *
     * <br>
     * 制御情報を破棄するメソッド。
     * スレッドローカルオブジェクトに何も制御情報が入っていないパターンの
     * テスト。例外が発生する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testUnbindRequestContext01() throws Exception {
        // 前処理
        ThreadLocal tlocal = new ThreadLocal();
        UTUtil.setPrivateField(
                RequestContextManager.class, "resources", tlocal);

        // テスト実施
        try {
            RequestContextManager.unbindRequestContext();
            fail();
        } catch (IllegalStateException e) {
            assertEquals("No RequestContext  bound to thread ["
                    + Thread.currentThread().getName() + "]", e.getMessage());
            assertTrue(
                    LogUTUtil.checkError("No RequestContext bound to thread!"));
        }
    }

    /**
     * testUnbindRequestContext02()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：C,F
     * <br><br>
     * 入力値：(状態) this.resources:ThreadLocal<br>
     *                （RequestContext｛<br>
     *                　requestName="test"<br>
     *                ｝）<br>
     *
     * <br>
     * 期待値：(状態変化) this.resources:ThreadLocal<br>
     *                    （空）<br>
     *
     * <br>
     * 制御情報を破棄するメソッド。
     * スレッドローカルオブジェクトに制御情報が入っているパターンのテスト。
     * 制御情報が削除される。正常ケース。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testUnbindRequestContext02() throws Exception {
        // 前処理
        ThreadLocal<RequestContext> tlocal = new ThreadLocal<RequestContext>();
        RequestContext ctx = new RequestContext();
        ctx.setRequestName("test");
        tlocal.set(ctx);
        UTUtil.setPrivateField(
                RequestContextManager.class, "resources", tlocal);

        // テスト実施
        RequestContextManager.unbindRequestContext();

        // 判定
        ThreadLocal t = (ThreadLocal)
            UTUtil.getPrivateField(RequestContextManager.class, "resources");
        assertNull(t.get());
    }

}
