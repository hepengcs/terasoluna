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

package jp.terasoluna.fw.web.thin;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanNotOfRequiredTypeException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;

import jp.terasoluna.fw.exception.SystemException;
import jp.terasoluna.utlib.LogUTUtil;
import jp.terasoluna.utlib.MockFilterConfig;
import jp.terasoluna.utlib.UTUtil;
import jp.terasoluna.utlib.spring.SpringTestCase;

/**
 * {@link jp.terasoluna.fw.web.thin.AbstractControlFilter} クラスのブラックボックステスト。
 * 
 * <p>
 * <h4>【クラスの概要】</h4>
 * beanとして実装されたコントローラを用いてアクセス制御を行う抽象クラス。<br>
 * <p>
 * 
 * @see jp.terasoluna.fw.web.thin.AbstractControlFilter
 */
@SuppressWarnings("unused")
public class AbstractControlFilterTest extends SpringTestCase {

    /**
     * このテストケースを実行する為の
     * GUI アプリケーションを起動する。
     * 
     * @param args java コマンドに設定されたパラメータ
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(AbstractControlFilterTest.class);
    }


    /**
     * testInit01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) config:初期化パラメータ名：controller<br>
     *                初期化パラメータ：testControllerStub01<br>
     *         (状態) this.config:null<br>
     *         
     * <br>
     * 期待値：(状態変化) config:引数で与えられたFilterConfigインスタンスと同一のインスタンス<br>
     *         
     * <br>
     * 与えられたconfigが設定されることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testInit01() throws Exception {
        // 前処理
        MockFilterConfig config = new MockFilterConfig();
        config.setInitParameter("controller", "testControllerStub01");
        AbstractControlFilterImpl01 filter = new AbstractControlFilterImpl01();
        UTUtil.setPrivateField(filter, "config", null);
        
        // テスト実施
        filter.init(config);

        // 判定
        assertSame(config,
                   UTUtil.getPrivateField(filter, "config"));
    }

    /**
     * testGetController01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(状態) config.getInitParameter("controller"):null<br>
     *         (状態) getErrorCode():"errors.test.controller"<br>
     *         (状態) getBean():AbstractControlFilter_ControllerStub01インスタンス
     *         (状態) getControllerClass():AbstractCotnrollerFilter_TestControllerクラス
     *         (状態) getDefaultControllerBeanId():"testControllerStub01"<br>
     *         (状態) Bean定義ファイル:<bean id="testControllerStub01"<br>
     *                        class="jp.terasoluna.fw.web.thin.AbstractControlFilter_ControllerStub01"/><br>
     *         
     * <br>
     * 期待値：(戻り値) AbstractControlFilter_Controller:DIコンテナから取得できるControllerインスタンスと等しいインスタンス<br>
     *         
     * <br>
     * フィルタの初期化パラメータが定義されていない場合、
     * デフォルトのbean名で取得されたコントローラが
     * 返却されることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetController01() throws Exception {
        // 前処理
        AbstractControlFilterImpl01 filter = new AbstractControlFilterImpl01();
        MockFilterConfig config = new MockFilterConfig();
        config.setInitParameter("controller", null);
        config.setServletContext(servletContext);
        UTUtil.setPrivateField(filter, "config", config);
        
        // テスト実施
        Object obj = filter.getController();

        // 判定        
        assertEquals(obj.getClass().getName(), 
                     AbstractControlFilter_ControllerStub01.class.getName());
    }

    /**
     * testGetController02()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(状態) config.getInitParameter("controller"):""<br>
     *         (状態) getErrorCode():"errors.test.controller"<br>
     *         (状態) getBean():AbstractControlFilter_ControllerStub01インスタンス
     *         (状態) getControllerClass():AbstractCotnrollerFilter_TestControllerクラス
     *         (状態) getDefaultControllerBeanId():"testControllerStub01"<br>
     *         (状態) Bean定義ファイル:<bean id="testControllerStub01"<br>
     *                        class="jp.terasoluna.fw.web.thin.AbstractControlFilter_ControllerStub01"/><br>
     *         
     * <br>
     * 期待値：(戻り値) AbstractControlFilter_Controller:DIコンテナから取得できるControllerインスタンスと等しいインスタンス<br>
     *         
     * <br>
     * フィルタの初期化パラメータが空文字列の場合、
     * デフォルトのbean名で取得されたコントローラが
     * 返却されることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetController02() throws Exception {
        // 前処理
        AbstractControlFilterImpl01 filter = new AbstractControlFilterImpl01();
        MockFilterConfig config = new MockFilterConfig();
        config.setInitParameter("controller", "");
        config.setServletContext(servletContext);
        UTUtil.setPrivateField(filter, "config", config);
        
        // テスト実施
        Object obj = filter.getController();

        // 判定
        assertEquals(obj.getClass().getName(), 
                     AbstractControlFilter_ControllerStub01.class.getName());
    }

    /**
     * testGetController03()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(状態) config.getInitParameter("controller"):"testControllerStub02"<br>
     *         (状態) getErrorCode():"errors.test.controller"<br>
     *         (状態) getBean():AbstractControlFilter_ControllerStub1インスタンス
     *         (状態) getControllerClass():AbstractCotnrollerFilter_TestControllerクラス
     *         (状態) getDefaultControllerBeanId():"testControllerStub01"<br>
     *         (状態) Bean定義ファイル:<bean id="testControllerStub02"<br>
     *                        class="jp.terasoluna.fw.web.thin.AbstractControlFilter_ControllerStub01"/><br>
     *         
     * <br>
     * 期待値：(戻り値) AbstractControlFilter_Controller:DIコンテナから取得できるControllerインスタンスと等しいインスタンス<br>
     *         
     * <br>
     * フィルタに初期化パラメータを正常に与えた場合、
     * 与えられたFilterConfigインスタンスと、
     * DIコンテナから初期化パラメータ名で取得したインスタンスが
     * 返却されることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetController03() throws Exception {
        // 前処理
        AbstractControlFilterImpl01 filter = new AbstractControlFilterImpl01();
        MockFilterConfig config = new MockFilterConfig();
        config.setInitParameter("controller", "testControllerStub02");
        config.setServletContext(servletContext);
        UTUtil.setPrivateField(filter, "config", config);
        
        // テスト実施
        Object obj = filter.getController();

        // 判定        
        assertEquals(obj.getClass().getName(), 
                     AbstractControlFilter_ControllerStub02.class.getName());
    }

    /**
     * testGetController04()
     * <br><br>
     * 
     * (異常系)
     * <br>
     * 観点：G
     * <br><br>
     * 入力値：(状態) config.getInitParameter("controller"):"aaaaa"<br>
     *         (状態) getErrorCode():"errors.test.controller"<br>
     *         (状態) getBean():-
     *         (状態) getControllerClass():AbstractCotnrollerFilter_TestControllerクラス
     *         (状態) getDefaultControllerBeanId():"testControllerStub01"<br>
     *         (状態) Bean定義ファイル:id属性がaaaaaである<bean>要素が定義されていない<br>
     *         
     * <br>
     * 期待値：(戻り値) AbstractControlFilter_Controller:-<br>
     *         (状態変化) 例外:SystemException：<br>
     *                    エラーコード："errors.test.controller"<br>
     *                    ラップした例外：NoSuchBeanDefinitionException<br>
     *         (状態変化) ログ:【エラーログ】<br>
     *                    メッセージ："not found aaaaa. controller bean not defined in Beans definition file."<br>
     *                    例外：NoSuchBeanDefinitionException<br>
     *         
     * <br>
     * Bean定義ファイルに指定されたidを持つ<bean>要素が
     * 定義されていなかった場合、エラーログを出力し、
     * 例外をスローすることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetController04() throws Exception {
        // 前処理
        AbstractControlFilterImpl01 filter = new AbstractControlFilterImpl01();
        MockFilterConfig config = new MockFilterConfig();
        config.setInitParameter("controller", "aaaaa");
        config.setServletContext(servletContext);
        UTUtil.setPrivateField(filter, "config", config);
        
        // テスト実施
        try {
            Object obj = filter.getController();
            fail();
        } catch (SystemException e) {
            // 判定   
            assertEquals("errors.test.controller", e.getErrorCode());
            assertEquals(e.getCause().getClass().getName(), 
                         NoSuchBeanDefinitionException.class.getName());
            assertTrue(LogUTUtil
                    .checkError("not found aaaaa. controller bean not defined in Beans definition file.",
                                e.getCause()));
        }                                   
    }

    /**
     * testGetController05()
     * <br><br>
     * 
     * (異常系)
     * <br>
     * 観点：G
     * <br><br>
     * 入力値：(状態) config.getInitParameter("controller"):"object"<br>
     *         (状態) getErrorCode():"errors.test.controller"<br>
     *         (状態) getBean():-
     *         (状態) getControllerClass():AbstractCotnrollerFilter_TestControllerクラス
     *         (状態) getDefaultControllerBeanId():"testControllerStub01"<br>
     *         (状態) Bean定義ファイル:<bean id="object"<br>
     *                        class="java.lang.Object"/><br>
     *         
     * <br>
     * 期待値：(戻り値) AbstractControlFilter_Controller:-<br>
     *         (状態変化) 例外:SystemException：<br>
     *                    エラーコード："errors.test.controller"<br>
     *                    ラップした例外：BeanNotOfRequiredTypeException<br>
     *         (状態変化) ログ:【エラーログ】<br>
     *                    メッセージ："controller not implemented " + getControllerClass().toString() + "."<br>
     *                    例外：BeanNotOfRequiredTypeException<br>
     *         
     * <br>
     * Bean定義ファイルから取得したインスタンスがフィルタの
     * 指定しているインタフェースを実装していなかった場合、
     * エラーログを出力し、例外をスローすることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetController05() throws Exception {
        // 前処理
        AbstractControlFilterImpl01 filter = new AbstractControlFilterImpl01();
        MockFilterConfig config = new MockFilterConfig();
        config.setInitParameter("controller", "object");
        config.setServletContext(servletContext);
        UTUtil.setPrivateField(filter, "config", config);
        
        // テスト実施
        try {
            Object obj = filter.getController();
            fail();
        } catch (SystemException e) {
            // 判定   
            assertEquals("errors.test.controller", e.getErrorCode());
            assertEquals(e.getCause().getClass().getName(),
                         BeanNotOfRequiredTypeException.class.getName());
            assertTrue(LogUTUtil
                    .checkError("controller not implemented " 
                                + filter.getControllerClass().toString() + ".",
                                e.getCause()));
        } 
    }

    /**
     * testGetController06()
     * <br><br>
     * 
     * (異常系)
     * <br>
     * 観点：G
     * <br><br>
     * 入力値：(状態) config.getInitParameter("controller"):"testControllerStub03"<br>
     *         (状態) getErrorCode():"errors.test.controller"<br>
     *         (状態) getBean():-
     *         (状態) getControllerClass():AbstractCotnrollerFilter_TestControllerクラス
     *         (状態) getDefaultControllerBeanId():"testControllerStub01"<br>
     *         (状態) Bean定義ファイル:<bean id="testControllerStub03"<br>
     *                        class="jp.terasoluna.fw.web.thin.AbstractControlFilter_ControllerStub02"<br>
     *                        abstract="true"/><br>
     *         
     * <br>
     * 期待値：(戻り値) AbstractControlFilter_Controller:-<br>
     *         (状態変化) 例外:SystemException：<br>
     *                    エラーコード："errors.test.controller"<br>
     *                    ラップした例外：BeansException<br>
     *         (状態変化) ログ:【エラーログ】<br>
     *                    メッセージ："bean generation failed."<br>
     *                    例外：BeansException<br>
     *         
     * <br>
     * Bean定義ファイルから取得しようとしたインスタンスのクラスが
     * 生成できない場合、エラーログを出力し、例外をスローすることを
     * 確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetController06() throws Exception {
        // 前処理
        AbstractControlFilterImpl01 filter = new AbstractControlFilterImpl01();
        MockFilterConfig config = new MockFilterConfig();
        config.setInitParameter("controller", "testControllerStub03");
        config.setServletContext(servletContext);
        UTUtil.setPrivateField(filter, "config", config);
        
        // テスト実施
        try {
            Object obj = filter.getController();
            System.err.println(obj.getClass().toString());
            fail();
        } catch (SystemException e) {
            // 判定   
            assertEquals("errors.test.controller", e.getErrorCode());
            assertTrue(e.getCause() instanceof BeansException);
            assertFalse(e.getCause() instanceof BeanNotOfRequiredTypeException);
            assertFalse(e.getCause() instanceof NoSuchBeanDefinitionException);
            System.out.println(e.getCause().toString());
            assertTrue(LogUTUtil.checkError("bean generation failed.",
                                            e.getCause()));
        } 
    }
    
    /**
     * 初期化処理を行う。
     */
    @Override
    protected void doOnSetUp() throws Exception {
    }

    /**
     * 後始末を行う。
     */
    @Override
    protected void onTearDown() throws Exception {
        super.onTearDown();
    }

    /**
     * XMLファイルの位置を返す。
     */
    @Override
    protected String[] getConfigLocations() {
        return new String[] {
            "jp/terasoluna/fw/web/thin"
            + "/AbstractControlFilterTest.xml"};
    }

}
