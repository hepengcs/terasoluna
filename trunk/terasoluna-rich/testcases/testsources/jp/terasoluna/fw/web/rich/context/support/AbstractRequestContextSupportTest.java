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

import jp.terasoluna.utlib.LogUTUtil;
import jp.terasoluna.utlib.MockHttpServletRequest;
import jp.terasoluna.utlib.UTUtil;
import jp.terasoluna.fw.web.rich.context.RequestContext;
import jp.terasoluna.fw.web.rich.context.RequestContextManager;
import jp.terasoluna.fw.web.rich.context.exception.IllegalContextPropertyClassTypeException;
import junit.framework.TestCase;

/**
 * {@link jp.terasoluna.fw.web.rich.context.support.AbstractRequestContextSupport} クラスのブラックボックステスト。
 * 
 * <p>
 * <h4>【クラスの概要】</h4>
 * 制御情報を扱うための補助ロジックインタフェースを継承した抽象クラス。<br>
 * 前提条件：
 * <p>
 * 
 * @see jp.terasoluna.fw.web.rich.context.support.AbstractRequestContextSupport
 */
public class AbstractRequestContextSupportTest extends TestCase {

    /**
     * このテストケースを実行する為の
     * GUI アプリケーションを起動する。
     * 
     * @param args java コマンドに設定されたパラメータ
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(AbstractRequestContextSupportTest.class);
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
    public AbstractRequestContextSupportTest(String name) {
        super(name);
    }

    /**
     * testGetRequestName01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(状態) getRequestContext():not null<br>
     *                （nullは返らない）<br>
     *         
     * <br>
     * 期待値：(戻り値) String:getRequestContext().getRequestName()の戻り値と等しいことを確認する。<br>
     *         
     * <br>
     * リクエスト名を取得するメソッドのテスト。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetRequestName01() throws Exception {
        // AbstractRequestContextSupport実装クラスの生成
        AbstractRequestContextSupportImpl01 target 
        	= new AbstractRequestContextSupportImpl01();
        
        // RequestContextの設定
        RequestContext ctx = new RequestContext();
        ctx.setRequestName("test");
        target.setTestCtx(ctx);
        
        // テスト実施・判定
        assertEquals(target.getRequestContext().getRequestName(), target
                .getRequestName());
    }

    /**
     * testGetProperty01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) key:"arg1"<br>
     *         
     * <br>
     * 期待値：(戻り値) Object:getRequestContext().getProperty()の戻り値と等しいことを確認する。<br>
     *         
     * <br>
     * 業務パラメータを取得するメソッドのテスト。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetProperty01() throws Exception {
        // AbstractRequestContextSupport実装クラスの生成
        AbstractRequestContextSupportImpl01 target = new AbstractRequestContextSupportImpl01();
        
        // RequestContextの設定
        RequestContext ctx = new RequestContext();
        Object object = new Object();
        ctx.setProperty("arg1", object);
        target.setTestCtx(ctx);

        // テスト実施・判定
        assertSame(object, target.getProperty("arg1"));
    }
    
    /**
     * testGetPropertyStringClass01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) key:"arg1"<br>
     *                clazz:Object.class<br>
     * <br>
     * 期待値：(戻り値) Object:getRequestContext().getProperty()の戻り値と等しいことを確認する。<br>
     *         
     * <br>
     * 業務パラメータを取得するメソッドのテスト。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetPropertyStringClass01() throws Exception {
        // AbstractRequestContextSupport実装クラスの生成
        AbstractRequestContextSupportImpl01 target = new AbstractRequestContextSupportImpl01();
        
        // RequestContextの設定
        RequestContext ctx = new RequestContext();
        String string = "";
        ctx.setProperty("arg1", string);
        target.setTestCtx(ctx);

        // テスト実施・判定
        assertSame(string, target.getProperty("arg1",String.class));
    }
    
    /**
     * testGetPropertyStringClass02()
     * <br><br>
     * 
     * (異常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) key:"arg1"<br>
     *                clazz:String.class<br>
     * <br>
     * 期待値：(例外) IllegalArgumentException:<br>
     *                 メッセージ：Must not use null for clazz of an argument.
     *         
     * <br>
     * 取得する業務プロパティの型を指定しない場合のテスト。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetPropertyStringClass02() throws Exception {
        // AbstractRequestContextSupport実装クラスの生成
        AbstractRequestContextSupportImpl01 target = new AbstractRequestContextSupportImpl01();
        
        // RequestContextの設定
        RequestContext ctx = new RequestContext();
        String string = "";
        ctx.setProperty("arg1", string);
        target.setTestCtx(ctx);

        // テスト実施
        try {
        	target.getProperty("arg1", null);
            fail();
        } catch (IllegalArgumentException e) {  
            // 判定
            assertEquals("Must not use null for clazz of an argument.",e.getMessage());
        }
    }
    
    /**
     * testGetPropertyStringClass03()
     * <br><br>
     * 
     * (異常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) key:"arg1"<br>
     *                clazz:Integer.class<br>
     * <br>
     * 期待値：(状態変化) 例外:IllegalContextPropertyClassTypeException<br>
     *         
     * <br>
     * 取得する業務プロパティの型と指定された型が違う場合のテスト。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetPropertyStringClass03() throws Exception {
        // AbstractRequestContextSupport実装クラスの生成
        AbstractRequestContextSupportImpl01 target = new AbstractRequestContextSupportImpl01();
        
        // RequestContextの設定
        RequestContext ctx = new RequestContext();
        String string = "abc";
        ctx.setProperty("arg1", string);
        target.setTestCtx(ctx);

        // テスト実施
        try {
        	target.getProperty("arg1",Integer.class);
            fail();
        } catch (IllegalContextPropertyClassTypeException e) { 
            // 判定
            return;
        }
    }
    
    /**
     * testGetPropertyString01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) key:"arg1"<br>
     * <br>
     * 期待値：(戻り値) String:getRequestContext().getProperty()の戻り値と等しいことを確認する。<br>
     *         
     * <br>
     * 業務パラメータを取得するメソッドのテスト。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetPropertyString01() throws Exception {
        // AbstractRequestContextSupport実装クラスの生成
        AbstractRequestContextSupportImpl01 target = new AbstractRequestContextSupportImpl01();
        
        // RequestContextの設定
        RequestContext ctx = new RequestContext();
        String string = "";
        ctx.setProperty("arg1", string);
        target.setTestCtx(ctx);

        // テスト実施・判定
        assertSame(string, target.getPropertyString("arg1"));
    }
    
    /**
     * testGetPropertyString02()
     * <br><br>
     * 
     * (異常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) key:"arg1"<br>
     * <br>
     * 期待値：(状態変化) 例外:IllegalContextPropertyClassTypeException<br>
     *         
     * <br>
     * 取得する業務プロパティの型がString型でない場合のテスト。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetPropertyString02() throws Exception {
        // AbstractRequestContextSupport実装クラスの生成
        AbstractRequestContextSupportImpl01 target = new AbstractRequestContextSupportImpl01();
        
        // RequestContextの設定
        RequestContext ctx = new RequestContext();
        Integer integer = new Integer(1);
        ctx.setProperty("arg1", integer);
        target.setTestCtx(ctx);

        
        // テスト実施
        try {
        	target.getPropertyString("arg1");
            fail();
        } catch (IllegalContextPropertyClassTypeException e) {  
            // 判定
            return;
        }
        
    }
    
    /**
     * testGenerateContext01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) request:not null<br>
     *         
     * <br>
     * 期待値：(状態変化) doGenerateContext():メソッドが呼び出されたことを確認する。<br>
     *         (状態変化) RequestContextManager.bindRequestContext():メソッドが呼び出されたことを確認する。<br>
     *         
     * <br>
     * リクエストコンテキストを生成するメソッドのテスト。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGenerateContext01() throws Exception {
        // AbstractRequestContextSupport実装クラスの生成
        AbstractRequestContextSupportImpl01 target = new AbstractRequestContextSupportImpl01();

        // RequestContextの設定
        RequestContext ctx = new RequestContext();
        ctx.setRequestName("test");
        target.setTestCtx(ctx);

        // 引数の生成
        MockHttpServletRequest request = new MockHttpServletRequest();

        // テスト実施
        target.generateContext(request);

        // 判定
        assertTrue(target.isDoGenerateContextCalled());
        assertSame(request, target.getDoGenerateContextArg());
        
        // 実行結果をもって呼び出し確認とする
        assertSame(ctx, RequestContextManager.getRequestContext());
    }

    /**
     * testDestroyContext01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(状態) RequestContextManager.hasRequestContext():true<br>
     *         
     * <br>
     * 期待値：(状態変化) RequestContextManager.unbindRequestContext():メソッドが呼び出されたことを確認する。<br>
     *         
     * <br>
     * リクエストコンテキストを削除するメソッドのテスト。リクエストコンテキストが生成されていたため、正常に削除される。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testDestroyContext01() throws Exception {
        // RequestContextManagerの設定
        ThreadLocal<RequestContext> tlocal = new ThreadLocal<RequestContext>();
        RequestContext ctx = new RequestContext();
        ctx.setRequestName("test");
        tlocal.set(ctx);
        UTUtil.setPrivateField(
                RequestContextManager.class, "resources", tlocal);

        // AbstractRequestContextSupport実装クラスの生成
        AbstractRequestContextSupportImpl02 target = new AbstractRequestContextSupportImpl02();

        // テスト実施
        target.destroyContext();

        // 判定
        assertFalse(RequestContextManager.hasRequestContext());
    }

    /**
     * testDestroyContext02()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(状態) RequestContextManager.hasRequestContext():false<br>
     *         
     * <br>
     * 期待値：(状態変化) RequestContextManager.unbindRequestContext():メソッドが呼び出されていないことを確認する。<br>
     *         
     * <br>
     * リクエストコンテキストを削除するメソッドのテスト。リクエストコンテキストが生成されていなかったため、何もしないで終了する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testDestroyContext02() throws Exception {
        // AbstractRequestContextSupport実装クラスの生成
        AbstractRequestContextSupportImpl02 target = new AbstractRequestContextSupportImpl02();

        // テスト実施
        target.destroyContext();

        // 判定
        assertFalse(RequestContextManager.hasRequestContext());
        
        // この条件でunbindRequestContext()が呼ばれれば、エラーログが出力される
        assertFalse(LogUTUtil.checkError("No RequestContext bound to thread!"));
    }

    /**
     * testGetRequestContext01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：
     * <br>
     * 期待値：(戻り値) RequestContextManager.getRequestContext()の戻り値と等しいことを確認する。<br>
     *         
     * <br>
     * リクエストコンテキストを取得するメソッドのテスト。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetRequestContext01() throws Exception {
        // RequestContextManagerの設定
        ThreadLocal<RequestContext> tlocal = new ThreadLocal<RequestContext>();
        RequestContext ctx = new RequestContext();
        ctx.setRequestName("test");
        tlocal.set(ctx);
        UTUtil.setPrivateField(
                RequestContextManager.class, "resources", tlocal);

        // AbstractRequestContextSupport実装クラスの生成
        AbstractRequestContextSupportImpl02 target = new AbstractRequestContextSupportImpl02();

        // テスト実施
        RequestContext result = target.getRequestContext();

        // 判定
        assertSame(RequestContextManager.getRequestContext(), result);
    }

}
