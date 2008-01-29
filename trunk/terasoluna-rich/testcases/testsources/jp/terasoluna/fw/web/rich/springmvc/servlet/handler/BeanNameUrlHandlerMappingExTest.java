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

import jp.terasoluna.fw.web.rich.context.support.DefaultRequestContextSupportImpl;
import jp.terasoluna.fw.web.rich.context.support.RequestContextSupport;
import jp.terasoluna.utlib.LogUTUtil;
import jp.terasoluna.utlib.MockHttpServletRequest;
import jp.terasoluna.utlib.UTUtil;
import jp.terasoluna.fw.web.rich.springmvc.servlet.handler.BeanNameUrlHandlerMappingEx;
import junit.framework.TestCase;

/**
 * {@link jp.terasoluna.fw.web.rich.springmvc.servlet.handler.
 * BeanNameUrlHandlerMappingEx} クラスのブラックボックステスト。
 * 
 * <p>
 * <h4>【クラスの概要】</h4>
 * リクエスト名と実行するリクエストコントローラBean定義のマッピングを
 * 行うハンドラ。<br>
 * 前提条件：Bean定義ファイルにコントローラを定義しておく必要がある。
 * <p>
 * 
 * @see jp.terasoluna.fw.web.rich.springmvc.servlet.handler.
 * BeanNameUrlHandlerMappingEx
 */
public class BeanNameUrlHandlerMappingExTest extends TestCase {

    /**
     * このテストケースを実行する為の
     * GUI アプリケーションを起動する。
     * 
     * @param args java コマンドに設定されたパラメータ
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(BeanNameUrlHandlerMappingExTest.class);
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
    public BeanNameUrlHandlerMappingExTest(String name) {
        super(name);
    }

    /**
     * testSetPrefix01()
     * <br><br>
     * 
     * (正常系) 
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) prefix:not null<br>
     *                （”/”）<br>
     *         (状態) this.prefix:not null<br>
     *                （””）<br>
     *         
     * <br>
     * 期待値：(状態変化) prefix:引数で設定した値が設定されている。<br>
     *         
     * <br>
     * prefix属性のsetメソッドのテスト。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetPrefix01() throws Exception {
        // 前処理
        BeanNameUrlHandlerMappingEx handlerMapping = 
            new BeanNameUrlHandlerMappingEx();
        UTUtil.setPrivateField(handlerMapping, "prefix", null);

        // テスト実施
        String value = "/";
        handlerMapping.setPrefix(value);

        // 判定
        Object result = UTUtil.getPrivateField(handlerMapping, "prefix");
        assertEquals(value, result);
    }

    /**
     * testSetSuffix01()
     * <br><br>
     * 
     * (正常系) 
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) suffix:not null<br>
     *                （”Controller”）<br>
     *         (状態) this.suffix:not null<br>
     *                （””）<br>
     *         
     * <br>
     * 期待値：(状態変化) suffix:引数で設定した値が設定されている。<br>
     *         
     * <br>
     * suffix属性のsetメソッドのテスト。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetSuffix01() throws Exception {
        // 前処理
        BeanNameUrlHandlerMappingEx handlerMapping = 
            new BeanNameUrlHandlerMappingEx();
        UTUtil.setPrivateField(handlerMapping, "suffix", null);

        // テスト実施
        String value = "Controller";
        handlerMapping.setSuffix(value);

        // 判定
        Object result = UTUtil.getPrivateField(handlerMapping, "suffix");
        assertEquals(value, result);
    }

    /**
     * testSetCtxSupport01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) ctxSupport:not null<br>
     *         (状態) this.ctxSupport:null<br>
     *         
     * <br>
     * 期待値：(状態変化) ctxSupport:引数で設定した値が設定されている。<br>
     *         
     * <br>
     * ctxSupport属性のsetメソッドのテスト。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetCtxSupport01() throws Exception {
        // 前処理
        BeanNameUrlHandlerMappingEx handlerMapping = 
            new BeanNameUrlHandlerMappingEx();
        UTUtil.setPrivateField(handlerMapping, "ctxSupport", null);

        // テスト実施
        RequestContextSupport ctxSupport = 
            new DefaultRequestContextSupportImpl();
        handlerMapping.setCtxSupport(ctxSupport);

        // 判定
        Object result = UTUtil.getPrivateField(handlerMapping, "ctxSupport");
        assertSame(ctxSupport, result);
    }

    /**
     * testLookupHandler01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(状態) ctxSupport.getRequestName():"sum"<br>
     *         (状態) prefix:"/"<br>
     *         (状態) suffix:"Controller"<br>
     *         
     * <br>
     * 期待値：(戻り値) Object:super.lookupHandler（）の戻り値と等しいことを
     *         確認する。<br>
     *         (状態変化) super.lookupHandler（）:
     *         呼び出されたことを確認する。<br>
     *                    引数を受け取ったことを確認する。<br>
     *         
     * <br>
     * コントローラ取得メソッドのテスト。
     * プリフィックス・サフィックスを使用してコントローラのIDを指定する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testLookupHandler01() throws Exception {
        // 前処理
        BeanNameUrlHandlerMappingEx handlerMapping = 
            new BeanNameUrlHandlerMappingEx();
        Object dummy = new Object();
        Map<Object, Object> handlerMap = new HashMap<Object, Object>();
        handlerMap.put("/sumController", dummy);
        UTUtil.setPrivateField(handlerMapping, "handlerMap", handlerMap);
        String prefix = "/";
        UTUtil.setPrivateField(handlerMapping, "prefix", prefix);
        String suffix = "Controller";
        UTUtil.setPrivateField(handlerMapping, "suffix", suffix);
        BeanNameUrlHandlerMappingEx_DefaultRequestContextSupportImplStub01 ctxSupport = 
            new BeanNameUrlHandlerMappingEx_DefaultRequestContextSupportImplStub01();
        ctxSupport.setRequestName("sum");
        UTUtil.setPrivateField(handlerMapping, "ctxSupport", ctxSupport); 
        MockHttpServletRequest request = new MockHttpServletRequest();

        // テスト実施
        Object result = handlerMapping.lookupHandler(null, request);

        // 判定
        assertSame(dummy, result);
    }

    /**
     * testLookupHandler02()
     * <br><br>
     * 
     * (正常系) 
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(状態) ctxSupport.getRequestName():null<br>
     *         (状態) prefix:null<br>
     *         (状態) suffix:null<br>
     *         
     * <br>
     * 期待値：(戻り値) Object:super.lookupHandler（）の戻り値と等しいことを
     *         確認する。<br>
     *         (状態変化) super.lookupHandler（）:
     *         呼び出されたことを確認する。<br>
     *                    引数を受け取ったことを確認する。<br>
     *         
     * <br>
     * コントローラ取得メソッドのテスト。リクエスト名がNullの場合のテスト。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testLookupHandler02() throws Exception {
        // 前処理
        BeanNameUrlHandlerMappingEx handlerMapping = 
            new BeanNameUrlHandlerMappingEx();
        Object dummy = new Object();
        Map<Object, Object> handlerMap = new HashMap<Object, Object>();
        handlerMap.put("/nullController", dummy);
        UTUtil.setPrivateField(handlerMapping, "handlerMap", handlerMap);
        String prefix = "/";
        UTUtil.setPrivateField(handlerMapping, "prefix", prefix);
        String suffix = "Controller";
        UTUtil.setPrivateField(handlerMapping, "suffix", suffix);
        BeanNameUrlHandlerMappingEx_DefaultRequestContextSupportImplStub01 ctxSupport = 
            new BeanNameUrlHandlerMappingEx_DefaultRequestContextSupportImplStub01();
        ctxSupport.setRequestName(null);
        UTUtil.setPrivateField(handlerMapping, "ctxSupport", ctxSupport);
        
        MockHttpServletRequest request = new MockHttpServletRequest();

        // テスト実施
        Object result = handlerMapping.lookupHandler(null, request);

        // 判定
        assertSame(dummy, result);
    }

    /**
     * testLookupHandler03()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(状態) ctxSupport.getRequestName()※１:空文字<br>
     *         (状態) prefix:空文字<br>
     *         (状態) suffix:空文字<br>
     *         
     * <br>
     * 期待値：(戻り値) Object:super.lookupHandler（）の戻り値と等しいことを
     *         確認する。<br>
     *         (状態変化) super.lookupHandler（）:
     *         呼び出されたことを確認する。<br>
     *                    引数を受け取ったことを確認する。<br>
     *         
     * <br>
     * コントローラ取得メソッドのテスト。リクエスト名が空文字の場合のテスト。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testLookupHandler03() throws Exception {
        // 前処理
        BeanNameUrlHandlerMappingEx handlerMapping = 
            new BeanNameUrlHandlerMappingEx();
        Object dummy = new Object();
        Map<Object, Object> handlerMap = new HashMap<Object, Object>();
        handlerMap.put("/Controller", dummy);
        UTUtil.setPrivateField(handlerMapping, "handlerMap", handlerMap);
        String prefix = "/";
        UTUtil.setPrivateField(handlerMapping, "prefix", prefix);
        String suffix = "Controller";
        UTUtil.setPrivateField(handlerMapping, "suffix", suffix);
        BeanNameUrlHandlerMappingEx_DefaultRequestContextSupportImplStub01 ctxSupport = 
            new BeanNameUrlHandlerMappingEx_DefaultRequestContextSupportImplStub01();
        ctxSupport.setRequestName("");
        UTUtil.setPrivateField(handlerMapping, "ctxSupport", ctxSupport);
        
        MockHttpServletRequest request = new MockHttpServletRequest();

        // テスト実施
        Object result = handlerMapping.lookupHandler(null, request);

        // 判定
        assertSame(dummy, result);    
    }

    /**
     * testLookupHandler04()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(状態) ctxSupport.getRequestName()※１:"sum"<br>
     *         (状態) prefix:"/"<br>
     *         (状態) suffix:"Controller"<br>
     *         
     * <br>
     * 期待値：(戻り値) Object:Null<br>
     *         (状態変化) super.lookupHandler（）:呼び出されたことを確認する。<br>
     *                    引数を受け取ったことを確認する。<br>
     *         (状態変化) ログ:ログレベル：エラー<br>
     *                    "Controller is not found. " + "BeanName:'" + newUrlPath + "'"<br>
     *         
     * <br>
     * 指定したコントローラがない場合のテスト。Nullを返す。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testLookupHandler04() throws Exception {
        // 前処理
        BeanNameUrlHandlerMappingEx handlerMapping = 
            new BeanNameUrlHandlerMappingEx();
        Map<Object, Object> handlerMap = new HashMap<Object, Object>();
        UTUtil.setPrivateField(handlerMapping, "handlerMap", handlerMap);
        String prefix = "/";
        UTUtil.setPrivateField(handlerMapping, "prefix", prefix);
        String suffix = "Controller";
        UTUtil.setPrivateField(handlerMapping, "suffix", suffix);
        BeanNameUrlHandlerMappingEx_DefaultRequestContextSupportImplStub01 ctxSupport = 
            new BeanNameUrlHandlerMappingEx_DefaultRequestContextSupportImplStub01();
        ctxSupport.setRequestName("sum");
        UTUtil.setPrivateField(handlerMapping, "ctxSupport", ctxSupport);
        
        MockHttpServletRequest request = new MockHttpServletRequest();

        // テスト実施
        Object result = handlerMapping.lookupHandler(null, request);

        // 判定
        assertSame(null, result);
        assertTrue(LogUTUtil.checkError("Controller is not found. " + "BeanName:" 
                + "'/sumController'"));
    }

    /**
     * testLookupHandler05()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(状態) ctxSupport.getRequestName()※１:"sum"<br>
     *         (状態) prefix:"/"<br>
     *         (状態) suffix:"Controller"<br>
     *         
     * <br>
     * 期待値：(戻り値) Object:super.lookupHandler（）の戻り値と等しいことを確認する。<br>
     *         (状態変化) super.lookupHandler（）:呼び出されたことを確認する。<br>
     *                    引数を受け取ったことを確認する。<br>
     *         
     * <br>
     * コントローラのNameがANT形式のパス指定をした場合のテスト。パターンに一致する、最も長い文字列のコントローラを返す。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testLookupHandler05() throws Exception {
        // 前処理
        BeanNameUrlHandlerMappingEx handlerMapping = 
            new BeanNameUrlHandlerMappingEx();
        Object dummy1 = new Object();
        Object dummy2 = new Object();
        Object dummy3 = new Object();
        Map<Object, Object> handlerMap = new HashMap<Object, Object>();
        handlerMap.put("/s*", dummy1);
        handlerMap.put("/sum*", dummy2);
        handlerMap.put("/sumControll*", dummy3);
        UTUtil.setPrivateField(handlerMapping, "handlerMap", handlerMap);
        String prefix = "/";
        UTUtil.setPrivateField(handlerMapping, "prefix", prefix);
        String suffix = "Controller";
        UTUtil.setPrivateField(handlerMapping, "suffix", suffix);
        BeanNameUrlHandlerMappingEx_DefaultRequestContextSupportImplStub01 ctxSupport = 
            new BeanNameUrlHandlerMappingEx_DefaultRequestContextSupportImplStub01();
        ctxSupport.setRequestName("sum");
        UTUtil.setPrivateField(handlerMapping, "ctxSupport", ctxSupport);
        
        MockHttpServletRequest request = new MockHttpServletRequest();

        // テスト実施
        Object result = handlerMapping.lookupHandler(null, request);

        // 判定
        assertSame(dummy3, result);
    }

    /**
     * testAfterPropertiesSet01()
     * <br><br>
     * 
     * (正常系) 
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(状態) this.ctxSupport:not null<br>
     *         
     * <br>
     * 期待値：
     * <br>
     * インスタンス時に呼ばれるメソッド。
     * コンテキストサポートが設定されているかチェックする。
     * コンテキストサポートが設定されているため、何もしないで終了するパターン。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testAfterPropertiesSet01() throws Exception {
        // 前処理
        BeanNameUrlHandlerMappingEx handlerMapping = 
            new BeanNameUrlHandlerMappingEx();
        RequestContextSupport ctxSupport = 
            new DefaultRequestContextSupportImpl();
        UTUtil.setPrivateField(handlerMapping, "ctxSupport", ctxSupport);

        // テスト実施
        handlerMapping.afterPropertiesSet();

        // 結果確認（例外が発生しなければOK）
    }

    /**
     * testAfterPropertiesSet02()
     * <br><br>
     * 
     * (異常系)
     * <br>
     * 観点：G
     * <br><br>
     * 入力値：(状態) this.ctxSupport:null<br>
     *         
     * <br>
     * 期待値：(状態変化) 例外:IllegalStateException( "BeanNameUrlHandlerMappingEx must be set ctxSupport.")<br>
     *         (状態変化) ログ:BeanNameUrlHandlerMappingEx must be set ctxSupport.<br>
     *         
     * <br>
     * インスタンス時に呼ばれるメソッド。コンテキストサポートが設定されているかチェックする。コンテキストサポートが設定されていないため、例外を投げる。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testAfterPropertiesSet02() throws Exception {
        // 前処理
        BeanNameUrlHandlerMappingEx handlerMapping = 
            new BeanNameUrlHandlerMappingEx();
        UTUtil.setPrivateField(handlerMapping, "ctxSupport", null);

        // テスト実施
        try {
            handlerMapping.afterPropertiesSet();
            fail();
        } catch (IllegalStateException e) {
            String expect = 
                "BeanNameUrlHandlerMappingEx must be set ctxSupport.";
            assertEquals(expect, e.getMessage());
            assertTrue(LogUTUtil.checkError(expect));
        }
    }
}
