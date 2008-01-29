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

package jp.terasoluna.fw.web.rich.springmvc.servlet.view.castor;

import org.springframework.web.context.support.GenericWebApplicationContext;
import org.springframework.web.servlet.view.AbstractUrlBasedView;

import jp.terasoluna.fw.oxm.mapper.OXMapper;
import jp.terasoluna.utlib.LogUTUtil;
import jp.terasoluna.utlib.UTUtil;
import jp.terasoluna.fw.web.rich.springmvc.servlet.view.castor.CastorView;
import jp.terasoluna.fw.web.rich.springmvc.servlet.view.castor.CastorViewResolver;
import junit.framework.TestCase;

/**
 * {@link jp.terasoluna.fw.web.rich.springmvc.servlet.view.castor.CastorViewResolver} クラスのブラックボックステスト。
 * 
 * <p>
 * <h4>【クラスの概要】</h4>
 * Castor用のViewResolver実装クラス。<br>
 * 前提条件：model、HTTPリクエスト、HTTPレスポンスはNull値にならない。
 * <p>
 * 
 * @see jp.terasoluna.fw.web.rich.springmvc.servlet.view.castor.CastorViewResolver
 */
public class CastorViewResolverTest extends TestCase {

    /**
     * このテストケースを実行する為の
     * GUI アプリケーションを起動する。
     * 
     * @param args java コマンドに設定されたパラメータ
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(CastorViewResolverTest.class);
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
    public CastorViewResolverTest(String name) {
        super(name);
    }

    /**
     * testCastorViewResolver01()
     * <br><br>
     * 
     * (正常系) 
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：
     * <br>
     * 期待値：(状態変化) this.viewClass:CastorViewクラスが設定される。<br>
     *         
     * <br>
     * コンストラクタのテスト。使用するビュークラスを設定する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testCastorViewResolver01() throws Exception {
        // 前処理

        // テスト実施
        CastorViewResolver viewResolver = new CastorViewResolver();

        // 判定
        Object result = UTUtil.getPrivateField(viewResolver, "viewClass");
        assertSame(CastorView.class, result);
    }

    /**
     * testSetOrder01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) order:not null<br>
     *         (状態) this.order:Integer.MAX_VALUE<br>
     *         
     * <br>
     * 期待値：(状態変化) this.order:引数で設定した値<br>
     *         
     * <br>
     * order属性のsetterメソッドのテスト。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetOrder01() throws Exception {
        // 前処理
        CastorViewResolver viewResolver = new CastorViewResolver();
        UTUtil.setPrivateField(viewResolver, "order", Integer.MAX_VALUE);

        // テスト実施
        int order = 100;
        viewResolver.setOrder(order);

        // 判定
        Object result = UTUtil.getPrivateField(viewResolver, "order");
        assertEquals(order, result);
    }

    /**
     * testGetOrder01()
     * <br><br>
     * 
     * (正常系) 
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(状態) this.order:not null<br>
     *         
     * <br>
     * 期待値：(戻り値) int:this.order<br>
     *         
     * <br>
     * order属性のgetterメソッドのテスト。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetOrder01() throws Exception {
        // 前処理
        CastorViewResolver viewResolver = new CastorViewResolver();
        int order = 100;
        UTUtil.setPrivateField(viewResolver, "order", order);

        // テスト実施
        Object result = viewResolver.getOrder();

        // 判定
        assertEquals(order, result);
    }

    /**
     * testGetOxmapper01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(状態) this.oxmapper:not null<br>
     *         
     * <br>
     * 期待値：(戻り値) int:this.oxmapper<br>
     *         
     * <br>
     * oxmapper属性のgetterメソッドのテスト。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetOxmapper01() throws Exception {
        // 前処理
        CastorViewResolver viewResolver = new CastorViewResolver();
        OXMapper oxmapper = new CastorViewResolver_OXMapperImplStub01();
        UTUtil.setPrivateField(viewResolver, "oxmapper", oxmapper);

        // テスト実施
        Object result = viewResolver.getOxmapper();

        // 判定
        assertSame(oxmapper, result);
    }

    /**
     * testSetOxmapper01()
     * <br><br>
     * 
     * (正常系) 
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) oxmapper:not null<br>
     *         (状態) this.oxmapper:null<br>
     *         
     * <br>
     * 期待値：(状態変化) this.oxmapper:引数で設定した値<br>
     *         
     * <br>
     * oxmapper属性のsetterメソッドのテスト。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetOxmapper01() throws Exception {
        // 前処理
        CastorViewResolver viewResolver = new CastorViewResolver();
        UTUtil.setPrivateField(viewResolver, "oxmapper", null);

        // テスト実施
        OXMapper oxmapper = new CastorViewResolver_OXMapperImplStub01();
        viewResolver.setOxmapper(oxmapper);

        // 判定
        Object result = UTUtil.getPrivateField(viewResolver, "oxmapper");
        assertSame(oxmapper, result);
    }

    /**
     * testAfterPropertiesSet01()
     * <br><br>
     * 
     * (異常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(状態) this.oxmapper:null<br>
     *         
     * <br>
     * 期待値：(状態変化) 例外:IllegalStateException<br>
     *                    （OXMapper class isn't set in CastorViewResolver. 
     *                    Check Spring Bean definition file.）<br>
     *         (状態変化) ログ:ログレベル：error<br>
     *                    OXMapper class isn't set in CastorViewResolver. 
     *                    Check Spring Bean definition file.<br>
     *         
     * <br>
     * OXMapperが設定されているかチェックするテスト。
     * Nullを設定し、例外が発生することを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testAfterPropertiesSet01() throws Exception {
        // 前処理
        CastorViewResolver viewResolver = new CastorViewResolver();
        UTUtil.setPrivateField(viewResolver, "oxmapper", null);

        // テスト実施
        try {
            viewResolver.afterPropertiesSet();
            fail();
        } catch (IllegalStateException e) {
            String expect = "OXMapper class isn't set in CastorViewResolver. " +
                    "Check Spring Bean definition file.";
            assertEquals(expect, e.getMessage());
            assertTrue(LogUTUtil.checkError(expect));
        }
    }

    /**
     * testAfterPropertiesSet02()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(状態) this.oxmapper:not null<br>
     *         
     * <br>
     * 期待値：
     * <br>
     * OXMapperが設定されているかチェックするテスト。
     * OXMapperを設定し、例外が発生しないことを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testAfterPropertiesSet02() throws Exception {
        // 前処理
        CastorViewResolver viewResolver = new CastorViewResolver();
        OXMapper oxmapper = new CastorViewResolver_OXMapperImplStub01();
        UTUtil.setPrivateField(viewResolver, "oxmapper", oxmapper);

        // テスト実施
        try {
            viewResolver.afterPropertiesSet();
        } catch (Exception e) {
            fail();
        }
        // （例外が発生しなければOK)
    }

    /**
     * testRequiredViewClass01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：
     * <br>
     * 期待値：(戻り値) Class:CastorViewクラスを返す。<br>
     *         
     * <br>
     * 当クラスが使用するべきビュークラスを設定するメソッド。
     * 固定でCastorビュークラスを返す。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testRequiredViewClass01() throws Exception {
        // 前処理
        CastorViewResolver viewResolver = new CastorViewResolver();

        // テスト実施
        Class result = viewResolver.requiredViewClass();

        // 判定
        assertSame(CastorView.class, result);
    }

    /**
     * testLoadView01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) viewName:not null<br>
     *                （空文字）<br>
     *         (引数) locale:使用しない<br>
     *         (状態) buildView（）:not null<br>
     *                （viewNameが空文字の場合、Castorビューを返す仕様）<br>
     *         (状態) this.oxmapper:not null<br>
     *                （このメソッドではNullにならない）<br>
     *         (状態) super.getApplicationContext():not null<br>
     *         
     * <br>
     * 期待値：(戻り値) View:Castorビュー<br>
     *                  oxmapper、アプリケーションコンテキストが設定されている。<br>
     *         (状態変化) view.afterPropertiesSet():呼び出し確認を行う<br>
     *         
     * <br>
     * ビュー名に空文字を入れ、Castorビューが返却されることを確認するテスト。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testLoadView01() throws Exception {
        // 前処理
        GenericWebApplicationContext context = 
            new GenericWebApplicationContext();
        CastorViewResolver viewResolver = new CastorViewResolverImpl01();
        viewResolver.setApplicationContext(context);
        OXMapper oxmapper = new CastorViewResolver_OXMapperImplStub01();
        UTUtil.setPrivateField(viewResolver, "oxmapper", oxmapper);

        // テスト実施
        String viewName = "";
        Object result = viewResolver.loadView(viewName, null);

        // 判定
        assertSame(result.getClass(), CastorViewResolver_CastorViewStub01.class);
        CastorViewResolver_CastorViewStub01 resultView = 
            (CastorViewResolver_CastorViewStub01) result;
        assertSame(oxmapper, resultView.getOxmapper());
        assertTrue(resultView.isAfterPropertiesSet);
    }

    /**
     * testLoadView02()
     * <br><br>
     * 
     * (正常系) 
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) viewName:not null<br>
     *                （１文字以上）<br>
     *         (引数) locale:使用しない<br>
     *         (状態) buildView（）:null<br>
     *                （viewNameが１文字以上の場合、Nullを返す仕様）<br>
     *         (状態) this.oxmapper:not null<br>
     *                （このメソッドではNullにならない）<br>
     *         (状態) super.getApplicationContext():not null<br>
     *         
     * <br>
     * 期待値：(戻り値) View:null<br>
     *         (状態変化) -<br>
     *         
     * <br>
     * ビュー名に１文字以上の文字列を入れ、Nullが返却されることを確認するテスト。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testLoadView02() throws Exception {
        // 前処理
        GenericWebApplicationContext context = 
            new GenericWebApplicationContext();
        CastorViewResolver viewResolver = new CastorViewResolver();
        viewResolver.setApplicationContext(context);
        OXMapper oxmapper = new CastorViewResolver_OXMapperImplStub01();
        UTUtil.setPrivateField(viewResolver, "oxmapper", oxmapper);

        // テスト実施
        String viewName = "viewName";
        Object result = viewResolver.loadView(viewName, null);

        // 判定
        assertNull(result);
    }

    /**
     * testLoadView03()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) viewName:null<br>
     *         (引数) locale:使用しない<br>
     *         (状態) buildView（）:not null<br>
     *                （viewNameがNullの場合、Castorビューを返す仕様）<br>
     *         (状態) this.oxmapper:not null<br>
     *                （このメソッドではNullにならない）<br>
     *         (状態) super.getApplicationContext():not null<br>
     *         
     * <br>
     * 期待値：(戻り値) View:Castorビュー<br>
     *                  oxmapper、アプリケーションコンテキストが設定されている。<br>
     *         (状態変化) view.afterPropertiesSet():呼び出し確認を行う<br>
     *         
     * <br>
     * ビュー名にNullを入れ、Castorビューが返却されることを確認するテスト。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testLoadView03() throws Exception {
        // 前処理
        GenericWebApplicationContext context = 
            new GenericWebApplicationContext();
        CastorViewResolver viewResolver = new CastorViewResolverImpl01();
        viewResolver.setApplicationContext(context);
        OXMapper oxmapper = new CastorViewResolver_OXMapperImplStub01();
        UTUtil.setPrivateField(viewResolver, "oxmapper", oxmapper);

        // テスト実施
        String viewName = null;
        Object result = viewResolver.loadView(viewName, null);

        // 判定
        assertTrue(result.getClass() == CastorViewResolver_CastorViewStub01.class);
        CastorViewResolver_CastorViewStub01 resultView = 
            (CastorViewResolver_CastorViewStub01) result;
        assertSame(oxmapper, resultView.getOxmapper());
        assertTrue(resultView.isAfterPropertiesSet);
    }

    /**
     * testBuildView01()
     * <br><br>
     * 
     * (正常系) 
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) viewName:not null<br>
     *                （空文字）<br>
     *         (状態) super.buildView():Castorビューを作成する。<br>
     *         
     * <br>
     * 期待値：(戻り値) AbstractUrlBasedView:Castorビュー<br>
     *         
     * <br>
     * ビュー名に空文字を入れ、Castorビューが返却されることを確認するテスト。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testBuildView01() throws Exception {
        // 前処理
        CastorViewResolver viewResolver = new CastorViewResolver();

        // テスト実施
        String viewName = "";
        AbstractUrlBasedView result = viewResolver.buildView(viewName);

        // 判定
        assertSame(CastorView.class, result.getClass());
    }

    /**
     * testBuildView02()
     * <br><br>
     * 
     * (正常系) 
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) viewName:not null<br>
     *                （１文字以上）<br>
     *         (状態) super.buildView():Castorビューを作成する。<br>
     *         
     * <br>
     * 期待値：(戻り値) AbstractUrlBasedView:null<br>
     *         
     * <br>
     * ビュー名に１文字以上の文字列を入れ、Nullが返却されることを確認するテスト。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testBuildView02() throws Exception {
        // 前処理
        CastorViewResolver viewResolver = new CastorViewResolver();

        // テスト実施
        String viewName = "viewName";
        AbstractUrlBasedView result = viewResolver.buildView(viewName);

        // 判定
        assertNull(result);
    }

    /**
     * testBuildView03()
     * <br><br>
     * 
     * (正常系) 
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) viewName:null<br>
     *         (状態) super.buildView():Castorビューを作成する。<br>
     *         
     * <br>
     * 期待値：(戻り値) AbstractUrlBasedView:Castorビュー<br>
     *         
     * <br>
     * ビュー名にNullを入れ、Castorビューが返却されることを確認するテスト。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testBuildView03() throws Exception {
        // 前処理
        CastorViewResolver viewResolver = new CastorViewResolver();

        // テスト実施
        String viewName = null;
        AbstractUrlBasedView result = viewResolver.buildView(viewName);

        // 判定
        assertSame(CastorView.class, result.getClass());
    }

}
