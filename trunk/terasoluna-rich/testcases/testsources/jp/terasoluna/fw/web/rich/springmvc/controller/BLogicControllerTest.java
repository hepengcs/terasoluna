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

import jp.terasoluna.fw.service.rich.BLogic;
import jp.terasoluna.fw.web.rich.context.support.DefaultRequestContextSupportImpl;
import jp.terasoluna.utlib.LogUTUtil;
import jp.terasoluna.utlib.UTUtil;
import jp.terasoluna.fw.web.rich.springmvc.bind.creator.XMLServletRequestDataBinderCreator;
import jp.terasoluna.fw.web.rich.springmvc.controller.BLogicController;
import junit.framework.TestCase;

/**
 * {@link jp.terasoluna.fw.web.rich.springmvc.controller.BLogicController} クラスのブラックボックステスト。
 * 
 * <p>
 * <h4>【クラスの概要】</h4>
 * BLogicインタフェース実装クラス実行用リクエストコントローラ。<br>
 * 前提条件：blogicはnullにならない
 * <p>
 * 
 * @see jp.terasoluna.fw.web.rich.springmvc.controller.BLogicController
 */
public class BLogicControllerTest extends TestCase {

    /**
     * このテストケースを実行する為の
     * GUI アプリケーションを起動する。
     * 
     * @param args java コマンドに設定されたパラメータ
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(BLogicControllerTest.class);
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
    public BLogicControllerTest(String name) {
        super(name);
    }

    /**
     * testSetBlogic01()
     * <br><br>
     * 
     * (正常系) 
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) blogic:Blogic実装クラス<br>
     *         (状態) this.blogic:null<br>
     *         
     * <br>
     * 期待値：(状態変化) this.blogic:引数で指定したblogic<br>
     *         
     * <br>
     * blogic属性のSetterメソッドのテスト。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetBlogic01() throws Exception {
        // 前処理 
        BLogicController controller = new BLogicController();
        BLogic<Object, Object> blogic = new BLogicController_BLogicImplStub01();
        UTUtil.setPrivateField(controller, "blogic", null);
        
        // テスト実施 
        controller.setBlogic(blogic);

        // 判定 
        assertSame(blogic, UTUtil.getPrivateField(controller, "blogic"));
    }

    /**
     * testAfterPropertiesSet01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：G
     * <br><br>
     * 入力値：(状態) this.dataBinderCreator:null<br>
     *         (状態) this.ctxSupport:null<br>
     *         (状態) this.blogic:null<br>
     *         
     * <br>
     * 期待値：(状態変化) 例外:IllegalStateException("DataBinderCreator is Null.")<br>
     *         (状態変化) ログ:ログレベル：エラーDataBinderCreator is Null.<br>
     *         
     * <br>
     * 親クラスのメソッドが呼ばれているか確認するテスト。
     * データバインダ生成クラスが設定されていないため例外を発生させる。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testAfterPropertiesSet01() throws Exception {
        // 前処理 
        BLogicController controller = new BLogicController();
        UTUtil.setPrivateField(controller, "dataBinderCreator", null);
        UTUtil.setPrivateField(controller, "ctxSupport", null);
        UTUtil.setPrivateField(controller, "blogic", null);
        
        // テスト実施
        try {
            controller.afterPropertiesSet();
            fail();
        } catch (IllegalStateException e) {
            // OK
            String expect = "DataBinderCreator is Null.";
            assertEquals(expect, e.getMessage());
            LogUTUtil.checkError(expect);
        }
    }

    /**
     * testAfterPropertiesSet02()
     * <br><br>
     * 
     * (異常系)
     * <br>
     * 観点：G
     * <br><br>
     * 入力値：(状態) this.dataBinderCreator:not null<br>
     *         (状態) this.ctxSupport:not null<br>
     *         (状態) this.blogic:null<br>
     *         
     * <br>
     * 期待値：(状態変化) 例外:IllegalStateException<br>
     *                    （”Cannot create BLogicController without blogic(BLogic) being set. 
     *                    Check Bean definition file."）<br>
     *         (状態変化) ログ:ログレベル：エラー<br>
     *                    Cannot create BLogicController without blogic(BLogic) being set. 
     *                    Check Bean definition file.<br>
     *         
     * <br>
     * インスタンス時にDIコンテナより呼ばれるメソッド。
     * blogicがNullであるため、例外が発生するパターン。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testAfterPropertiesSet02() throws Exception {
        // 前処理 
        BLogicController controller = new BLogicController();
        UTUtil.setPrivateField(controller, "dataBinderCreator", 
                new XMLServletRequestDataBinderCreator());
        UTUtil.setPrivateField(controller, "ctxSupport", 
                new DefaultRequestContextSupportImpl());
        UTUtil.setPrivateField(controller, "blogic", null);
        
        // テスト実施
        try {
            controller.afterPropertiesSet();
            fail();
        } catch (IllegalStateException e) {
            // OK
            String expect = "Cannot create BLogicController without blogic(BLogic) being set. "
                + "Check Bean definition file.";
            assertEquals(expect, e.getMessage());
            LogUTUtil.checkError(expect);
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
     *         (状態) this.blogic:not null<br>
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
    public void testAfterPropertiesSet03() throws Exception {
        // 前処理 
        BLogicController controller = new BLogicController();
        UTUtil.setPrivateField(controller, "dataBinderCreator", 
                new XMLServletRequestDataBinderCreator());
        UTUtil.setPrivateField(controller, "ctxSupport", 
                new DefaultRequestContextSupportImpl());
        UTUtil.setPrivateField(controller, "blogic", 
                new BLogicController_BLogicImplStub01());
        
        // テスト実施
        controller.afterPropertiesSet();
        
        // OK
    }

    /**
     * testGetCommandType01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(状態) this.blogic:BlogicImp implements Blogic<Integer, Long><br>
     *         
     * <br>
     * 期待値：(戻り値) Type:Integer<br>
     *         
     * <br>
     * Blogicが型パラメータを定義している正常ケース。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
//    BLogicControllerを拡張したため削除
//    public void testGetCommandType01() throws Exception {
//        // 前処理 
//        BLogicController controller = new BLogicController();
//        BLogic<Integer, Long> blogic = new BLogicController_BLogicImplStub02();
//        UTUtil.setPrivateField(controller, "blogic", blogic);
//        
//        // テスト実施 
//        Type result = controller.getCommandType();
//
//        // 判定 
//        assertSame(Integer.class, result);
//    }

    /**
     * testGetCommandType02()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(状態) this.blogic:Proxyインスタンス<br>
     *                ・ラップしているBlogic<br>
     *                BlogicImp implements Blogic<Integer, Long><br>
     *         
     * <br>
     * 期待値：(戻り値) Type:Integer<br>
     *         
     * <br>
     * プロキシにBlogicがラップされているパターンのテスト。
     * ラップされているBlogicのパラメータを取得する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
//  BLogicControllerを拡張したため削除
//    public void testGetCommandType02() throws Exception {
//        // 前処理 
//        BLogicController controller = new BLogicController();
//        ProxyFactory pf = new ProxyFactory(new BLogicController_BLogicImplStub02());
//        BLogic blogic = (BLogic) pf.getProxy();
//        UTUtil.setPrivateField(controller, "blogic", blogic);
        
        // テスト実施 
//        Type result = controller.getCommandType();

        // 判定 
//        assertSame(Integer.class, result);
//    }

    /**
     * testGetCommandType03()
     * <br><br>
     * 
     * (正常系) 
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(状態) this.blogic:BlogicImp implements Blogic<Integer, Long> , <br>
     *                Iterator<Float>,<br>
     *                List<br>
     *         
     * <br>
     * 期待値：(戻り値) Type:Integer<br>
     *         
     * <br>
     * Blogicが３つのインタフェースを実装しているパターン。
     * 型パラメータを定義しているインタフェースも複数持つ。
     * Blogicの型パラメータを返す。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
//    public void testGetCommandType03() throws Exception {
    
    // FW改善により削除
    //  コマンド型の取得方法を、型パラメータではなく、
    //  executeメソッドのパラメータで取得するよう変更したため、削除
    
    
//        // 前処理 
//        BLogicController controller = new BLogicController();
//        BLogic<Integer, Long> blogic = new BLogicController_BLogicImplStub03();
//        UTUtil.setPrivateField(controller, "blogic", blogic);
//        
//        // テスト実施 
//        Type result = controller.getCommandType();
//
//        // 判定 
//        assertSame(Integer.class, result);
//    }

    /**
     * testGetCommandType04()
     * <br><br>
     * 
     * (異常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(状態) this.blogic:BlogicImp implements Blogic<br>
     *                (型パラメータ指定なし）<br>
     *         
     * <br>
     * 期待値：(状態変化) 例外:IllegalStateException<br>
     *                    ("Cannot get BLogic<P, R> parameter P.")<br>
     *         (状態変化) ログ:ログレベル:エラー<br>
     *                    Cannot get BLogic<P, R> parameter P.<br>
     *         
     * <br>
     * Blogicが型パラメータを定義していないパターン。例外をスローする。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
//  BLogicControllerを拡張したため削除
//    public void testGetCommandType04() throws Exception {
//        // 前処理 
//        BLogicController controller = new BLogicController();
//        BLogic blogic = new BLogicController_BLogicImplStub04();
//        UTUtil.setPrivateField(controller, "blogic", blogic);
//        
//        // テスト実施 
//        try {
//            controller.getCommandType();
//            fail();
//        } catch (IllegalStateException e) {
//            // OK
//            String expect = "Cannot get Command type. "
//                + "execute(P) method argument P must not use Object class.";
//            assertEquals(expect, e.getMessage());
//            assertTrue(LogUTUtil.checkError(expect));
//        }
//    }
    
    /**
     * testGetCommandType05()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(状態) this.blogic:・抽象クラスを継承するBlogic<br>
     *                public class BLogicController_BLogicImplStub06<br>
     *                    extends BLogicController_BLogicImplStub05<Integer, Long><br>
     *                public abstract class BLogicController_BLogicImplStub05<P, R><br>
     *                    extends AbstractBLogic<P, R><br>
     *         
     * <br>
     * 期待値：(戻り値) Type:Integer<br>
     *         
     * <br>
     * Blogicが抽象クラスを継承しているパターン。正しく型パラメータを取得する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
//  BLogicControllerを拡張したため削除
//    public void testGetCommandType05() throws Exception {
//        // 前処理 
//        BLogicController controller = new BLogicController();
//        BLogic<Integer, Long> blogic = new BLogicController_BLogicImplStub06();
//        UTUtil.setPrivateField(controller, "blogic", blogic);
//        
//        // テスト実施 
//        Type result = controller.getCommandType();
//
//        // 判定 
//        assertSame(Integer.class, result);
//    }

    /**
     * testGetCommandType06()
     * <br><br>
     * 
     * (異常系)
     * <br>
     * 観点：G
     * <br><br>
     * 入力値：(状態) this.blogic:executeメソッドをオーバーロードしているBlogic<br>
     *         
     * <br>
     * 期待値：(状態変化) 例外:IllegalStateException<br>
     *                    ("Cannot get Command type. BLogic class cannot be overload execute(P) method.")<br>
     *         (状態変化) ログ:ログレベル:エラー<br>
     *                    Cannot get Command type. BLogic class cannot be overload execute(P) method.<br>
     *         
     * <br>
     * Blogicがexecuteメソッドをオーバーロードしているパターン。例外をスローする。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
//  BLogicControllerを拡張したため削除
//    public void testGetCommandType06() throws Exception {
//        // 前処理 
//        BLogicController controller = new BLogicController();
//        BLogic blogic = new BLogicController_BLogicImplStub07();
//        UTUtil.setPrivateField(controller, "blogic", blogic);
//        
//        // テスト実施 
//        try {
//            controller.getCommandType();
//            fail();
//        } catch (IllegalStateException e) {
//            // OK
//            String expect = "Cannot get Command type. "
//                + "BLogic class cannot be overload execute(P) method.";
//            assertEquals(expect, e.getMessage());
//            assertTrue(LogUTUtil.checkError(expect));
//        }
//    }

    /**
     * testExecuteService01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) command:not null<br>
     *         (状態) this.blogic:not null<br>
     *         
     * <br>
     * 期待値：(戻り値) Object:blogic.execute()の戻り値と等しいことを確認する。<br>
     *         (状態変化) blogic.execute():メソッドが呼び出されたことを確認する。<br>
     *                    引数を受け取ったことを確認する。<br>
     *         
     * <br>
     * サービスロジックの正常系テスト。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testExecuteService01() throws Exception {
        // 前処理 
        BLogicController controller = new BLogicController();
        BLogic<Object, Object> blogic = new BLogicController_BLogicImplStub01();
        UTUtil.setPrivateField(controller, "blogic", blogic);
        Object command = new Object();
        
        // テスト実施 
        Object result = controller.executeService(command);

        // 判定 
        assertSame(command, result);
        assertSame(command, UTUtil.getPrivateField(blogic, "command"));
    }
    
    /**
     * testSetBusinessLogic01()
     * <br><br>
     * 
     * (正常系) 
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) blogic:Blogic実装クラス<br>
     *         (状態) this.blogic:null<br>
     *         
     * <br>
     * 期待値：(状態変化) this.blogic:引数で指定したblogic<br>
     *         
     * <br>
     * blogic属性のSetterメソッドのテスト。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetBusinessLogic01() throws Exception {

        // 前処理 
        BLogicController controller = new BLogicController();
        BLogic<Object, Object> blogic = new BLogicController_BLogicImplStub01();
        UTUtil.setPrivateField(controller, "blogic", null);

        // テスト実施 
        controller.setBusinessLogic(blogic);

        // 判定 
        assertSame(blogic, UTUtil.getPrivateField(controller, "blogic"));
    }


}
