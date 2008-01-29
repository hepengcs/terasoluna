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

import java.lang.reflect.Type;
import java.util.Date;

import org.springframework.beans.PropertyEditorRegistrar;
import org.springframework.validation.AbstractBindingResult;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingErrorProcessor;
import org.springframework.validation.DefaultBindingErrorProcessor;
import org.springframework.validation.DefaultMessageCodesResolver;
import org.springframework.validation.MessageCodesResolver;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;

import jp.terasoluna.fw.util.ClassLoadException;
import jp.terasoluna.fw.web.rich.context.support.DefaultRequestContextSupportImpl;
import jp.terasoluna.fw.web.rich.context.support.RequestContextSupport;
import jp.terasoluna.utlib.LogUTUtil;
import jp.terasoluna.utlib.MockHttpServletRequest;
import jp.terasoluna.utlib.MockHttpServletResponse;
import jp.terasoluna.utlib.UTUtil;
import jp.terasoluna.fw.web.rich.springmvc.Constants;
import jp.terasoluna.fw.web.rich.springmvc.bind.creator.ServletRequestDataBinderCreator;
import jp.terasoluna.fw.web.rich.springmvc.bind.creator.XMLServletRequestDataBinderCreator;
import jp.terasoluna.fw.web.rich.springmvc.controller.TerasolunaController;
import junit.framework.TestCase;
/**
 * {@link jp.terasoluna.fw.web.rich.springmvc.controller.
 * TerasolunaController} 
 * クラスのブラックボックステスト。
 * 
 * <p>
 * <h4>【クラスの概要】</h4>
 * サービス層のクラスを実行するリクエストコントローラ抽象クラス。<br>
 * 前提条件：HTTPリクエスト、HTTPレスポンス、コマンドオブジェクトは
 * Null値にならない。
 * <p>
 * 
 * @see jp.terasoluna.fw.web.rich.springmvc.controller.
 * TerasolunaController
 */
public class TerasolunaControllerTest extends TestCase {

    /**
     * このテストケースを実行する為の
     * GUI アプリケーションを起動する。
     * 
     * @param args java コマンドに設定されたパラメータ
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(TerasolunaControllerTest.class);
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
    public TerasolunaControllerTest(String name) {
        super(name);
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
     * 期待値：(状態変化) this.ctxSupport:引数で設定した値。<br>
     *         
     * <br>
     * ctxSupport属性のSetterメソッドのテスト。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetCtxSupport01() throws Exception {
        // 前処理
        TerasolunaController controller = new TerasolunaControllerImpl01();
        UTUtil.setPrivateField(controller, "ctxSupport", null);
        RequestContextSupport ctxSupport = 
            new DefaultRequestContextSupportImpl();

        // テスト実施
        controller.setCtxSupport(ctxSupport);

        // 判定
        Object result = UTUtil.getPrivateField(controller, "ctxSupport");
        assertSame(ctxSupport, result);
    }

    /**
     * testSetDataBinderCreator01()
     * <br><br>
     * 
     * (正常系) 
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) dataBindExecuter:not null<br>
     *         (状態) this.dataBinderCreator:null<br>
     *         
     * <br>
     * 期待値：(状態変化) this.dataBinderCreator:引数で設定した値。<br>
     *         
     * <br>
     * dataBinderCreator属性のSetterメソッドのテスト。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetDataBinderCreator01() throws Exception {
        // 前処理
        TerasolunaController controller = new TerasolunaControllerImpl01();
        UTUtil.setPrivateField(controller, "dataBinderCreator", null);
        ServletRequestDataBinderCreator creator = 
            new XMLServletRequestDataBinderCreator();

        // テスト実施
        controller.setDataBinderCreator(creator);

        // 判定
        Object result = UTUtil.getPrivateField(controller, "dataBinderCreator");
        assertSame(creator, result);
    }

    /**
     * testSetViewName01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) viewName:"sampleView"<br>
     *         (状態) this.viewName:null<br>
     *         
     * <br>
     * 期待値：(状態変化) this.viewName:引数で設定した値。<br>
     *         
     * <br>
     * viewName属性のSetterメソッドのテスト。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetViewName01() throws Exception {
        // 前処理
        TerasolunaController controller = new TerasolunaControllerImpl01();
        UTUtil.setPrivateField(controller, "viewName", null);
        String argString = "sampleView";

        // テスト実施
        controller.setViewName(argString);

        // 判定
        Object result = UTUtil.getPrivateField(controller, "viewName");
        assertEquals(argString, result);
    }

    /**
     * testSetUseVelocityView01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) useVelocityView:true<br>
     *         (状態) this.useVelocityView:false<br>
     *         
     * <br>
     * 期待値：(状態変化) this.useVelocityView:true<br>
     *         
     * <br>
     * useVelocityView属性のSetterメソッドのテスト。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetUseVelocityView01() throws Exception {
        // 前処理
        TerasolunaController controller = new TerasolunaControllerImpl01();
        UTUtil.setPrivateField(controller, "useRequestNameView", false);

        // テスト実施
        controller.setUseRequestNameView(true);

        // 判定
        Boolean result = (Boolean)UTUtil.getPrivateField(
                controller, "useRequestNameView");
        assertTrue(result);
    }
    
    /**
     * testAfterPropertiesSet01()
     * <br><br>
     * 
     * (異常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(状態) this.dataBinderCreator:null<br>
     *         (状態) this.ctxSupport:not null<br>
     *         
     * <br>
     * 期待値：(状態変化) 例外:IllegalStateException(
     *          "DataBinderCreator is Null.")<br>
     *         (状態変化) ログ:ログレベル：エラーDataBinderCreator is Null.<br>
     *         
     * <br>
     * インスタンス時にDIコンテナより呼ばれるメソッド。
     * データバインダ生成クラスが設定されていない場合のテスト。
     * 例外を発生させる。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    @SuppressWarnings("unchecked")
    public void testAfterPropertiesSet01() throws Exception {
        // 前処理
        TerasolunaController controller = new TerasolunaControllerImpl01();
        UTUtil.setPrivateField(controller, "dataBinderCreator", null);
        UTUtil.setPrivateField(controller, "ctxSupport", 
                new DefaultRequestContextSupportImpl());

        // テスト実施
        try {
            controller.afterPropertiesSet();
            fail();
        } catch (IllegalStateException e) {
            // OK
            assertEquals("DataBinderCreator is Null.", e.getMessage());
            assertTrue(LogUTUtil.checkError("DataBinderCreator is Null."));
        }
    }

    /**
     * testAfterPropertiesSet02()
     * <br><br>
     * 
     * (異常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(状態) this.dataBinderCreator:not null<br>
     *         (状態) this.ctxSupport:null<br>
     *         
     * <br>
     * 期待値：(状態変化) 例外:IllegalStateException(
     *           "ContextSupport is Null.")<br>
     *         (状態変化) ログ:ログレベル：エラー<br>
     *                    ContextSupport is Null.<br>
     *         
     * <br>
     * インスタンス時にDIコンテナより呼ばれるメソッド。
     * ctxSupportがNullであるため、例外が発生するパターン。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    @SuppressWarnings("unchecked")
    public void testAfterPropertiesSet02() throws Exception {
        // 前処理
        TerasolunaController controller = new TerasolunaControllerImpl01();
        UTUtil.setPrivateField(controller, "ctxSupport", null);
        UTUtil.setPrivateField(controller, "dataBinderCreator", 
                new XMLServletRequestDataBinderCreator());

        // テスト実施
        try {
            controller.afterPropertiesSet();
            fail();
        } catch (IllegalStateException e) {
            // OK
            assertEquals("ContextSupport is Null.", e.getMessage());
            assertTrue(LogUTUtil.checkError("ContextSupport is Null."));
        }
    }

    /**
     * testAfterPropertiesSet03()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(状態) this.dataBinderCreator:not null<br>
     *         (状態) this.ctxSupport:not null<br>
     *         
     * <br>
     * 期待値：
     * <br>
     * インスタンス時にDIコンテナより呼ばれるメソッド。
     * 正常に属性が設定されているため、何も処理をしない。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    @SuppressWarnings("unchecked")
    public void testAfterPropertiesSet03() throws Exception {
        // 前処理
        TerasolunaController controller = new TerasolunaControllerImpl01();
        UTUtil.setPrivateField(controller, "dataBinderCreator", 
                new XMLServletRequestDataBinderCreator());
        UTUtil.setPrivateField(controller, "ctxSupport", 
                new DefaultRequestContextSupportImpl());

        // テスト実施
        controller.afterPropertiesSet();

        // 結果確認（例外が発生しなければOK)
    }

    /**
     * testGetCommand01()
     * <br><br>
     * 
     * (正常系) 
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) request:使用しない<br>
     *         (状態) getCommandType:クラス<br>
     *         
     * <br>
     * 期待値：(戻り値) Object:getCommand()の戻り値のクラスが
     * インスタンス化されたことを確認する。<br>
     *         
     * <br>
     * 型パラメータがクラスのパターンのテスト。正常にインスタンス化を行う。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetCommand01() throws Exception {
        // 前処理
        TerasolunaController controller = new TerasolunaControllerImpl08();
        MockHttpServletRequest request = new MockHttpServletRequest();

        // テスト実施
        Object result = controller.getCommand(request);

        // 判定
        assertEquals(Date.class, result.getClass());
    }

    /**
     * testGetCommand02()
     * <br><br>
     * 
     * (異常系)
     * <br>
     * 観点：G
     * <br><br>
     * 入力値：(引数) request:使用しない<br>
     *         (状態) getCommandType:抽象クラス<br>
     *         
     * <br>
     * 期待値：(状態変化) 例外:IllegalStateException("Invalid Command type.")<br>
     *         (状態変化) ログ:ログレベル：エラー
     *                         "Invalid Command type.", ClassLoadException<br>
     *         
     * <br>
     * 型パラメータが抽象クラスのパターンのテスト。
     * インスタンス化に失敗し、例外を投げる。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetCommand02() throws Exception {
        // 前処理
        TerasolunaController controller = new TerasolunaControllerImpl01();
        UTUtil.setPrivateField(controller, "commandType", TerasolunaController.class);
        MockHttpServletRequest request = new MockHttpServletRequest();

        // テスト実施
        try {
            controller.getCommand(request);
            fail();
        } catch (IllegalStateException e) {
            // OK
            assertEquals("Invalid Command type.", e.getMessage());
            assertTrue(LogUTUtil.checkError("Invalid Command type.", 
                new ClassLoadException(new InstantiationException())));
        }
    }

    /**
     * testGetCommand03()
     * <br><br>
     * 
     * (異常系)
     * <br>
     * 観点：G
     * <br><br>
     * 入力値：(引数) request:使用しない<br>
     *         (状態) getCommandType:クラス<br>
     *                （コンストラクタがprivate宣言されている）<br>
     *         
     * <br>
     * 期待値：(状態変化) 例外:IllegalStateException("Invalid Command type.")<br>
     *         (状態変化) ログ:ログレベル：エラー
     *                         "Invalid Command type.", ClassLoadException<br>
     *         
     * <br>
     * 型パラメータがクラスのパターンのテスト。
     * コンストラクタが呼べず、インスタンス化に失敗し、例外を投げる。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetCommand03() throws Exception {
        // 前処理
        TerasolunaController controller = new TerasolunaControllerImpl01();
        UTUtil.setPrivateField(controller, "commandType", Math.class);
        MockHttpServletRequest request = new MockHttpServletRequest();

        // テスト実施
        try {
            controller.getCommand(request);
            fail();
        } catch (IllegalStateException e) {
            // OK
            assertEquals("Invalid Command type.", e.getMessage());
            assertTrue(LogUTUtil.checkError("Invalid Command type.", 
                new ClassLoadException(new IllegalAccessException())));
        }
    }
    
    /**
     * testGetCommand04()
     * <br><br>
     * 
     * (異常系)
     * <br>
     * 観点：G
     * <br><br>
     * 入力値：(引数) request:使用しない<br>
     *         (状態) getCommandType:Object.class<br>
     *         
     * <br>
     * 期待値：(状態変化) 例外:IllegalStateException("Cannot get Command type. Controller cannot specify the Object type for parameterized type P.")<br>
     *         (状態変化) ログ:ログレベル：エラー　Cannot get Command type. Controller cannot specify the Object type for parameterized type P.<br>
     *         
     * <br>
     * 型パラメータがクラスのパターンのテスト。
     * コンストラクタが呼べず、インスタンス化に失敗し、例外を投げる。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetCommand04() throws Exception {
        // 前処理
        TerasolunaController controller = new TerasolunaControllerImpl07();
        MockHttpServletRequest request = new MockHttpServletRequest();

        // テスト実施
        try {
            controller.getCommand(request);
            fail();
        } catch (IllegalStateException e) {
            // OK
            String expect = "Cannot get Command type. " +
                    "Controller cannot specify the Object type for parameterized type P.";
            assertEquals(expect, e.getMessage());
            assertTrue(LogUTUtil.checkError(expect));
        }
    }

    /**
     * testGetCommandType01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(状態) this（コントローラ実装クラス）:
     *                 親クラス定義：TerasolunaController<P,R><br>
     *                 this定義：MyController extends TerasolunaController
     *                  <Integer,Boolean><br>
     *         
     * <br>
     * 期待値：(戻り値) Type:Integer<br>
     *         
     * <br>
     * Terasolunaコントローラを継承し、型パラメータPの型を指定するパターン。
     * 正常ケース。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    @SuppressWarnings("unchecked")
    public void testGetCommandType01() throws Exception {
        // 前処理
        TerasolunaController controller =
            new TerasolunaControllerImpl03();

        // テスト実施
        Type type = controller.getCommandType();

        // 判定
        assertSame(Integer.class, type);
    }

    /**
     * testGetCommandType02()
     * <br><br>
     * 
     *  (異常系)
     * <br>
     * 観点：G
     * <br><br>
     * 入力値：(引数) request:使用しない<br>
     *         (状態) getCommandType:抽象クラス<br>
     *         
     * <br>
     * 期待値：(状態変化) 例外:IllegalStateException("Invalid Command type.")<br>
     *         (状態変化) ログ:ログレベル：エラーInvalid Command type.<br>
     *         
     * <br>
     * 型パラメータが抽象クラスのパターンのテスト。
     * インスタンス化に失敗し、例外を投げる。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    @SuppressWarnings("unchecked")
    public void testGetCommandType02() throws Exception {
        // 前処理
        TerasolunaController controller =
            new TerasolunaControllerImpl02();

        // テスト実施
        try {
            controller.getCommandType();
            fail();
        } catch (IllegalStateException e) {
            // テスト結果確認
            String expect = "Controller class must be set ParameterizedType"; 
            assertEquals(expect, e.getMessage());
            assertTrue(LogUTUtil.checkError(expect));
        }
    }

    /**
     * testGetCommandType03()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(状態) this（コントローラ実装クラス）:親の親の親クラス定義：
     *                TerasolunaController<P, R><br>
     *                親の親クラス定義：TerasolunaControllerImpl04<P,R> 
     *                extends TerasolunaController<Date,Long><br>
     *                親クラス定義：TerasolunaControllerImpl05<P,R> 
     *                extends TerasolunaControllerImpl04<Double, String><br>
     *                this定義：TerasolunaControllerImpl06
     *                extends TerasolunaControllerImpl05<Float,Short><br>
     *         
     * <br>
     * 期待値：(戻り値) Type:Date<br>
     *         
     * <br>
     * コントローラを３代継承しているパターン。
     * TerasolunaControllerクラスの型パラメータを使用する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    @SuppressWarnings("unchecked")
    public void testGetCommandType03() throws Exception {
        // 前処理
        TerasolunaController controller =
            new TerasolunaControllerImpl06();

        // テスト実施
        Type type = controller.getCommandType();

        // 判定
        assertSame(Date.class, type);
    }

    /**
     * testCreateBinder01()
     * <br><br>
     * 
     * (異常系)
     * <br>
     * 観点：G
     * <br><br>
     * 入力値：(引数) request:使用しない<br>
     *         (引数) command:not null<br>
     *         (状態) this.dataBinderCreator:not null<br>
     *         (状態) this.dataBinderCreator.create():null<br>
     *         
     * <br>
     * 期待値：(状態変化) 例外:IllegalStateException("DataBinder is Null.")<br>
     *         (状態変化) ログ:ログレベル：エラーDataBinder is Null.<br>
     *         
     * <br>
     * データバインダの生成でNullが返されるパターン。例外を発生させる。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testCreateBinder01() throws Exception {
        // 前処理
        TerasolunaController controller = new TerasolunaControllerImpl01();
        UTUtil.setPrivateField(controller, "dataBinderCreator", 
                new TerasolunaController_ServletRequestDataBinderCreatorStub01());
        UTUtil.setPrivateField(controller, "ctxSupport", 
                new TerasolunaController_DefaultRequestContextSupportImplStub01());
        MockHttpServletRequest request = new MockHttpServletRequest();
        Object command = new Object();
        
        // テスト実施
        try {
            controller.createBinder(request, command);
            fail();
        } catch (IllegalStateException e) {
            assertEquals("DataBinder is Null.", e.getMessage());
            assertTrue(LogUTUtil.checkError("DataBinder is Null."));
        }
    }

    /**
     * testCreateBinder02()
     * <br><br>
     * 
     * (正常系) 
     * <br>
     * 観点：E
     * <br><br>
     * 入力値：(引数) request:使用しない<br>
     *         (引数) command:not null<br>
     *         (状態) this.dataBinderCreator:not null<br>
     *         (状態) this.dataBinderCreator.create():not null<br>
     *         (状態) this.getMessageCodesResolver():not null<br>
     *         
     * <br>
     * 期待値：(戻り値) ServletRequestDataBinder:
     *                  生成したServletRequestDataBinderオブジェクト。<br>
     *                  This.messageCodeResolverが設定されている。<br>
     *         (状態変化) initBinder():メソッドが呼び出されたことを確認する。<br>
     *                    引数を受け取ったことを確認する。<br>
     *         
     * <br>
     * 正常にデータバインダを生成するパターン。messageCodeResolverをセットする。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testCreateBinder02() throws Exception {
        // 前処理 -----------------------------------------------------
        TerasolunaControllerImpl01 controller = 
            new TerasolunaControllerImpl01();
        
        // データバインダ
        ServletRequestDataBinder binder = 
            new TerasolunaController_ServletRequestDataBinderStub01();
        
        // データバインダ生成クラス
        TerasolunaController_ServletRequestDataBinderCreatorStub02 creator = 
            new TerasolunaController_ServletRequestDataBinderCreatorStub02();
        creator.setBinder(binder);
        UTUtil.setPrivateField(controller, "dataBinderCreator", creator);
        
        // コンテキストサポートクラス
        UTUtil.setPrivateField(controller, "ctxSupport", 
                new TerasolunaController_DefaultRequestContextSupportImplStub01());

        // メッセージコードリゾルバ
        MessageCodesResolver msgCodesResolver = 
            new TerasolunaController_MessageCodesResolverStub01(); 
        UTUtil.setPrivateField(controller, "messageCodesResolver", 
                msgCodesResolver);
        
        // バインドエラープロセッサ
        BindingErrorProcessor errorProcessor = new DefaultBindingErrorProcessor();
        controller.setBindingErrorProcessor(errorProcessor);
        
        // プロパティエディタ
        PropertyEditorRegistrar[] editorRegistrars 
            = new PropertyEditorRegistrar[3];
        TerasolunaController_PropertyEditorRegistrarStab01 registrar1 = new TerasolunaController_PropertyEditorRegistrarStab01();
        TerasolunaController_PropertyEditorRegistrarStab01 registrar2 = new TerasolunaController_PropertyEditorRegistrarStab01();
        TerasolunaController_PropertyEditorRegistrarStab01 registrar3 = new TerasolunaController_PropertyEditorRegistrarStab01();
        editorRegistrars[0] = registrar1;
        editorRegistrars[1] = registrar2;
        editorRegistrars[2] = registrar3;
        controller.setPropertyEditorRegistrars(editorRegistrars);
        
        MockHttpServletRequest request = new MockHttpServletRequest();
        Object command = new Object();
        
        // テスト実施 --------------------------------------------------
        ServletRequestDataBinder result = 
            controller.createBinder(request, command);

        // 判定 --------------------------------------------------------
        assertSame(binder, result);

        assertSame(msgCodesResolver,((AbstractBindingResult) binder.getBindingResult()).getMessageCodesResolver());
        Object resultInitBinderRequest = 
            UTUtil.getPrivateField(controller, "initBinderRequest");
        assertSame(request, resultInitBinderRequest);
        Object resultBinder = UTUtil.getPrivateField(controller, "binder");
        assertSame(result, resultBinder);
        assertSame(errorProcessor, result.getBindingErrorProcessor());
        assertSame(result, registrar1.editorRegistry);
        assertSame(result, registrar2.editorRegistry);
        assertSame(result, registrar3.editorRegistry);
    }

    /**
     * testCreateBinder03()
     * <br><br>
     * 
     * (正常系) 
     * <br>
     * 観点：E
     * <br><br>
     * 入力値：(引数) request:使用しない<br>
     *         (引数) command:not null<br>
     *         (状態) this.dataBinderCreator:not null<br>
     *         (状態) this.dataBinderCreator.create():not null<br>
     *         (状態) this.getMessageCodesResolver():null<br>
     *         
     * <br>
     * 期待値：(戻り値) ServletRequestDataBinder:生成した
     *          ServletRequestDataBinderオブジェクト。
     *          デフォルトのmessageCodeResolver（DefaultMessageCodesResolver）が
     *          設定されている。<br>
     *         (状態変化) initBinder():メソッドが呼び出されたことを確認する。<br>
     *                    引数を受け取ったことを確認する。<br>
     *         
     * <br>
     * 正常にデータバインダを生成するパターン。
     * messageCodeResolverをセットしないため、
     * Nullが設定されている。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testCreateBinder03() throws Exception {
        // 前処理 -----------------------------------------------------
        TerasolunaControllerImpl01 controller = new TerasolunaControllerImpl01();
        
        // データバインダ
        ServletRequestDataBinder binder = 
            new TerasolunaController_ServletRequestDataBinderStub01();
        
        // データバインダ生成クラス
        TerasolunaController_ServletRequestDataBinderCreatorStub02 creator = 
            new TerasolunaController_ServletRequestDataBinderCreatorStub02();
        creator.setBinder(binder);
        UTUtil.setPrivateField(controller, "dataBinderCreator", creator);
        
        // コンテキストサポートクラス
        UTUtil.setPrivateField(controller, "ctxSupport", 
                new TerasolunaController_DefaultRequestContextSupportImplStub01());

        // メッセージコードリゾルバは設定しない
        
        MockHttpServletRequest request = new MockHttpServletRequest();
        Object command = new Object();
        
        // テスト実施 --------------------------------------------------
        ServletRequestDataBinder result = 
            controller.createBinder(request, command);

        // 判定 --------------------------------------------------------
        assertSame(binder, result);
        
        assertSame(DefaultMessageCodesResolver.class,
                ((AbstractBindingResult) binder.getBindingResult())
                        .getMessageCodesResolver().getClass());
        
        Object resultInitBinderRequest = 
            UTUtil.getPrivateField(controller, "initBinderRequest");
        assertSame(request, resultInitBinderRequest);
        Object resultBinder = UTUtil.getPrivateField(controller, "binder");
        assertSame(result, resultBinder);
    }

    /**
     * testOnBind01()
     * <br><br>
     * 
     * (正常系) 
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) request:使用しない<br>
     *         (引数) command:使用しない<br>
     *         (引数) errors:エラーあり<br>
     *         
     * <br>
     * 期待値：(状態変化) 例外:errorsを投げる。<br>
     *         
     * <br>
     * バインド処理でエラーが発生するパターンのテスト。例外を投げる。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testOnBind01() throws Exception {
        // 前処理
        TerasolunaController controller = new TerasolunaControllerImpl01();
        BindException errors = new TerasolunaController_BindExceptionStub01();
        MockHttpServletRequest request = new MockHttpServletRequest();
        Object command = new Object();

        // テスト実施
        try {
            controller.onBind(request, command, errors);
            fail();
        } catch (BindException e) {
            // OK
            assertSame(errors, e);
        }
    }

    /**
     * testOnBind02()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) request:使用しない<br>
     *         (引数) command:使用しない<br>
     *         (引数) errors:エラーなし<br>
     *         
     * <br>
     * 期待値：
     * <br>
     * バインド処理でエラーが発生しないパターンのテスト。何もしない。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testOnBind02() throws Exception {
        // 前処理
        TerasolunaController controller = new TerasolunaControllerImpl01();
        BindException errors = new TerasolunaController_BindExceptionStub02();
        MockHttpServletRequest request = new MockHttpServletRequest();
        Object command = new Object();

        // テスト実施
        controller.onBind(request, command, errors);
        
        // 結果確認(例外が発生しなければOK）
    }

    /**
     * testOnBindAndValidate01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) request:使用しない<br>
     *         (引数) command:使用しない<br>
     *         (引数) errors:エラーあり<br>
     *         
     * <br>
     * 期待値：(状態変化) 例外:errorsを投げる。<br>
     *         
     * <br>
     * 入力チェック処理でエラーが発生するパターンのテスト。例外を投げる。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testOnBindAndValidate01() throws Exception {
        // 前処理
        TerasolunaController controller = new TerasolunaControllerImpl01();
        BindException errors = new TerasolunaController_BindExceptionStub01();
        MockHttpServletRequest request = new MockHttpServletRequest();
        Object command = new Object();

        // テスト実施
        try {
            controller.onBindAndValidate(request, command, errors);
            fail();
        } catch (BindException e) {
            // OK
            assertSame(errors, e);
        }
    }

    /**
     * testOnBindAndValidate02()
     * <br><br>
     * 
     * (正常系) 
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) request:使用しない<br>
     *         (引数) command:使用しない<br>
     *         (引数) errors:エラーなし<br>
     *         
     * <br>
     * 期待値：
     * <br>
     * 入力チェック処理でエラーが発生しないパターンのテスト。何もしない。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testOnBindAndValidate02() throws Exception {
        // 前処理
        TerasolunaController controller = new TerasolunaControllerImpl01();
        BindException errors = new TerasolunaController_BindExceptionStub02();
        MockHttpServletRequest request = new MockHttpServletRequest();
        Object command = new Object();

        // テスト実施
        controller.onBindAndValidate(request, command, errors);
        
        // 結果確認(例外が発生しなければOK）
    }

    /**
     * testHandle01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) request:not null<br>
     *         (引数) response:not null<br>
     *         (引数) command:not null<br>
     *         (引数) errors:使用しない<br>
     *         (状態) this.viewName:”sample”<br>
     *         (状態) this.useRequestNameView:false<br>
     *         (状態) ctxSupport.getRequestName():”command”<br>
     *         
     * <br>
     * 期待値：(戻り値) ModelAndView:ModelAndViewを返す。<br>
     *                  ビュー名：sample<br>
     *         (状態変化) executeService（）:メソッドが呼び出されたことを確認する。<br>
     *                    引数を受け取ったことを確認する。<br>
     *         
     * <br>
     * ビュー名を直接入力するパターンのテスト。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testHandle01() throws Exception {
        // 前処理 --------------------------------------------------------------
        TerasolunaControllerImpl01 controller = new TerasolunaControllerImpl01();
        
        // ビュー名
        String viewName = "sample";
        UTUtil.setPrivateField(controller, "viewName", viewName);
        
        // コンテキストサポートクラス
        UTUtil.setPrivateField(controller, "ctxSupport", 
                new TerasolunaController_DefaultRequestContextSupportImplStub01());

        // Velocity使用フラグ
        UTUtil.setPrivateField(controller, "useRequestNameView", false);
        
        // 業務結果オブジェクト
        Object model = new Object();
        
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        BindException errors = new BindException(new Object(), "");
        
        // テスト実施 ----------------------------------------------------------
        ModelAndView mv = controller.handle(request, response, model, errors);

        // 判定 ----------------------------------------------------------------
        assertEquals(viewName, mv.getViewName());
        assertTrue(controller.isExecuteService);
        assertSame(model, mv.getModel().get(Constants.RESULT_KEY));
    }

    /**
     * testHandle02()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) request:not null<br>
     *         (引数) response:not null<br>
     *         (引数) command:not null<br>
     *         (引数) errors:使用しない<br>
     *         (状態) this.viewName:null<br>
     *         (状態) this.useRequestNameView:true<br>
     *         (状態) ctxSupport.getRequestName():”command”<br>
     *         
     * <br>
     * 期待値：(戻り値) ModelAndView:ModelAndViewを返す。<br>
     *                  ビュー名：/sum<br>
     *         (状態変化) executeService（）:
     *         メソッドが呼び出されたことを確認する。<br>
     *                    引数を受け取ったことを確認する。<br>
     *         
     * <br>
     * Velocityビューを使用するパターンのテスト。
     * ビュー名に"/"＋リクエスト名が使用される。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testHandle02() throws Exception {
        // 前処理 --------------------------------------------------------------
        TerasolunaControllerImpl01 controller = 
            new TerasolunaControllerImpl01();
        
        // ビュー名
        UTUtil.setPrivateField(controller, "viewName", null);
        
        // コンテキストサポートクラス
        UTUtil.setPrivateField(controller, "ctxSupport", 
                new TerasolunaController_DefaultRequestContextSupportImplStub01());
        
        // Velocity使用フラグ
        UTUtil.setPrivateField(controller, "useRequestNameView", true);

        // 業務結果オブジェクト
        Object model = new Object();
        
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        BindException errors = new BindException(new Object(), "");
        
        
        // テスト実施 ----------------------------------------------------------
        ModelAndView mv = controller.handle(request, response, model, errors);

        // 判定 ----------------------------------------------------------------
        assertEquals("/command", mv.getViewName());
        assertTrue(controller.isExecuteService);
        assertEquals(model, mv.getModel().get(Constants.RESULT_KEY));
    }

    /**
     * testHandle03()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) request:not null<br>
     *         (引数) response:not null<br>
     *         (引数) command:not null<br>
     *         (引数) errors:使用しない<br>
     *         (状態) this.viewName:”sample”<br>
     *         (状態) this.useRequestNameView:true<br>
     *         (状態) ctxSupport.getRequestName():”command”<br>
     *         
     * <br>
     * 期待値：(戻り値) ModelAndView:ModelAndViewを返す。<br>
     *                  ビュー名：sample<br>
     *         (状態変化) executeService（）:
     *         メソッドが呼び出されたことを確認する。<br>
     *                    引数を受け取ったことを確認する。<br>
     *         
     * <br>
     * ビュー名を直接入力し、かつVelocityビューを使用する設定が
     * あるパターンのテスト。
     * 直接入力されたビュー名が優先される。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testHandle03() throws Exception {
        // 前処理 --------------------------------------------------------------
        TerasolunaControllerImpl01 controller = new TerasolunaControllerImpl01();
        
        // ビュー名
        String viewName = "sample";
        UTUtil.setPrivateField(controller, "viewName", viewName);
        
        // コンテキストサポートクラス
        UTUtil.setPrivateField(controller, "ctxSupport", 
                new TerasolunaController_DefaultRequestContextSupportImplStub01());

        // Velocity使用フラグ
        UTUtil.setPrivateField(controller, "useRequestNameView", true);
        
        // 業務結果オブジェクト
        Object model = new Object();
        
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        BindException errors = new BindException(new Object(), "");
        
        // テスト実施 ----------------------------------------------------------
        ModelAndView mv = controller.handle(request, response, model, errors);

        // 判定 ----------------------------------------------------------------
        assertEquals(viewName, mv.getViewName());
        assertTrue(controller.isExecuteService);
        assertSame(model, mv.getModel().get(Constants.RESULT_KEY));
    }

    /**
     * testHandle04()
     * <br><br>
     * 
     * (正常系) 
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) request:not null<br>
     *         (引数) response:not null<br>
     *         (引数) command:not null<br>
     *         (引数) errors:使用しない<br>
     *         (状態) this.viewName:null<br>
     *         (状態) this.useRequestNameView:false<br>
     *         (状態) ctxSupport.getRequestName():”command”<br>
     *         
     * <br>
     * 期待値：(戻り値) ModelAndView:ModelAndViewを返す。<br>
     *                  ビュー名：（空文字）<br>
     *         (状態変化) executeService（）:
     *         メソッドが呼び出されたことを確認する。<br>
     *                    引数を受け取ったことを確認する。<br>
     *         
     * <br>
     * ビュー名の指定も、Velocityビューを使用する設定もしていない場合のテスト。
     * 空文字のビュー名が設定される。Castorビューを使用する場合の設定となる。
     * TERASOLUNAのビュー機能ではデフォルト仕様となっている。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testHandle04() throws Exception {
        // 前処理 --------------------------------------------------------------
        TerasolunaControllerImpl01 controller = new TerasolunaControllerImpl01();
        
        // ビュー名
        UTUtil.setPrivateField(controller, "viewName", null);
        
        // コンテキストサポートクラス
        UTUtil.setPrivateField(controller, "ctxSupport", 
                new TerasolunaController_DefaultRequestContextSupportImplStub01());
        
        // Velocity使用フラグ
        UTUtil.setPrivateField(controller, "useRequestNameView", false);

        // 業務結果オブジェクト
        Object model = new Object();
        
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        BindException errors = new BindException(new Object(), "");
        
        // テスト実施 ----------------------------------------------------------
        ModelAndView mv = controller.handle(request, response, model, errors);

        // 判定 ----------------------------------------------------------------
        assertEquals("", mv.getViewName());
        assertTrue(controller.isExecuteService);
        assertSame(model, mv.getModel().get(Constants.RESULT_KEY));
    }

    /**
     * testExecuteService01()
     * <br><br>
     * 
     * (正常系) 
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) request:使用しない<br>
     *         (引数) response:使用しない<br>
     *         (引数) command:not null<br>
     *         
     * <br>
     * 期待値：(戻り値) R:executeService（）
     *          メソッドの戻り値と等しいことを確認する。<br>
     *         (状態変化) preService（）:
     *         メソッドが呼び出されたことを確認する。<br>
     *                    引数を受け取ったことを確認する。<br>
     *         (状態変化) executeService（）:
     *         メソッドが呼び出されたことを確認する。<br>
     *                    引数を受け取ったことを確認する。<br>
     *         (状態変化) postService（）:
     *         メソッドが呼び出されたことを確認する。<br>
     *                    引数を受け取ったことを確認する。<br>
     *         
     * <br>
     * サービスロジックのテスト。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    @SuppressWarnings("unchecked")
    public void testExecuteService01() throws Exception {
        // 前処理
        TerasolunaControllerImpl01 controller = 
            new TerasolunaControllerImpl01();
        String model = "aaa";
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        
        // テスト実施
        Object result = controller.executeService(request, response, model);

        // 判定
        assertEquals(model, result);
        assertTrue(controller.isPreService);
        assertTrue(controller.isExecuteService);
        assertTrue(controller.isPostService);
    }
}
