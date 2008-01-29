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

package jp.terasoluna.fw.web.rich;

import javax.servlet.ServletContext;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanNotOfRequiredTypeException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.web.context.WebApplicationContext;
import jp.terasoluna.utlib.LogUTUtil;
import jp.terasoluna.utlib.MockFilterConfig;
import jp.terasoluna.utlib.MockHttpServletRequest;
import jp.terasoluna.utlib.MockHttpServletResponse;
import jp.terasoluna.utlib.UTUtil;
import jp.terasoluna.utlib.spring.SpringTestCase;
import jp.terasoluna.fw.web.rich.ForbiddenURIChecker;
import jp.terasoluna.fw.web.rich.ForbiddenURIFilter;
import jp.terasoluna.fw.web.rich.exception.ForbiddenURIException;

/**
 * {@link jp.terasoluna.fw.web.rich.ForbiddenURIFilter} 
 * クラスのブラックボックステスト。
 * 
 * <p>
 * <h4>【クラスの概要】</h4>
 * 許可されているURI以外へのアクセスを禁止するフィルタ。<br>
 * initメソッド、doFilterメソッドの引き数はnot nullとする。
 * <p>
 * 
 * @see jp.terasoluna.fw.web.rich.ForbiddenURIFilter
 */
public class ForbiddenURIFilterTest extends SpringTestCase {

    /**
     * このテストケースを実行する為の
     * GUI アプリケーションを起動する。
     * 
     * @param args java コマンドに設定されたパラメータ
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(ForbiddenURIFilterTest.class);
    }
    
    @Override
    protected void doOnSetUp() throws Exception {
        LogUTUtil.flush();
    }

    @Override
    protected String[] getConfigLocations() {
        return new String[] {
            "jp/terasoluna/fw/web/rich/ForbiddenURIFilterTest.xml" 
        };
    }

    /**
     * testInit01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(状態) context.getBean("forbiddenURIChecker"):ForbiddenCheckerインスタンス<br>
     *         (状態) config.getInitParameter(INITPARAM_KEY_CHECKER_BEANID):null<br>
     *         
     * <br>
     * 期待値：(状態変化) checker:getBeanの戻り値と同一のインスタンス<br>
     *         
     * <br>
     * getBeanの戻り値を正常に属性に設定すること。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testInit01() throws Exception {
        // 前処理
        // ForbiddenURIFilter
        ForbiddenURIFilter filter = new ForbiddenURIFilter();
        
        // FilterConfig
        MockFilterConfig config = new MockFilterConfig();
        
        // config.servletContext : this
        ServletContext context = this.servletContext;
        
        // getBeanでForbiddenURIChecker実装クラスのインスタンスを返す
        // WebApplicationContext実装クラス
        WebApplicationContextImpl01 webContext =
            new WebApplicationContextImpl01();
        
        // ForbiddenURIChecker実装クラス
        ForbiddenURIFilter_ForbiddenURICheckerStub01 checker =
            new ForbiddenURIFilter_ForbiddenURICheckerStub01();
        UTUtil.setPrivateField(webContext, "checker", checker);
        
        context.setAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE, webContext); 
        
        config.setServletContext(context);
        
        // config.getInitParameter(INITPARAM_KEY_CHECKER_BEANID) : null
        config.setInitParameter("checkerBeanID", null);
                
        // テスト実施
        filter.init(config);
        
        // 判定
        // getBeanの戻り値と同一のインスタンス
        assertSame(checker, UTUtil.getPrivateField(filter, "checker"));
        
        // 引数呼び出し確認
        // name
        assertEquals("forbiddenURIChecker", webContext.name);
        
        // requiredType : ソースコードでべた書きされているのでテストの意味はない
        assertEquals(ForbiddenURIChecker.class, webContext.requiredType);
    }

    /**
     * testInit02()
     * <br><br>
     * 
     * (異常系)
     * <br>
     * 観点：G
     * <br><br>
     * 入力値：(状態) context.getBean("forbiddenURIChecker"):ForbiddenChecker以外のインスタンス<br>
     *         (状態) config.getInitParameter(INITPARAM_KEY_CHECKER_BEANID):null<br>
     *         
     * <br>
     * 期待値：(状態変化) 例外:BeanNotOfRequiredTypeException<br>
     *         (状態変化) ログ:エラーレベル<br>
     *                    the bean is not of the required type - ForbiddenURIChecker.<br>
     *         
     * <br>
     * getBeanの戻り値が想定外の型だった場合、BeanNotOfRequiredTypeExceptionをスローすること
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testInit02() throws Exception {
        // 前処理
        // ForbiddenURIFilter
        ForbiddenURIFilter filter = new ForbiddenURIFilter();
        
        // FilterConfig
        MockFilterConfig config = new MockFilterConfig();
        
        // config.servletContext : this
        ServletContext context = this.servletContext;
        
        // getBeanでBeanNotOfRequiredTypeException例外を起こす
        // WebApplicationContext実装クラス
        WebApplicationContextImpl02 webContext =
            new WebApplicationContextImpl02();
        
        context.setAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE, webContext);
        
        config.setServletContext(context);
        
        // config.getInitParameter(INITPARAM_KEY_CHECKER_BEANID) : null
        config.setInitParameter("checkerBeanID", null);
        
        
        try {
            // テスト実施
            filter.init(config);
            fail();
        } catch (BeanNotOfRequiredTypeException e) {
            // 判定
            // log : error
            assertTrue(LogUTUtil.checkError("the bean is not of the required type - ForbiddenURIChecker."));
            
            // name
            assertEquals("forbiddenURIChecker", webContext.name);
            
            // requiredType : ソースコードでべた書きされているのでテストの意味はない
            assertEquals(ForbiddenURIChecker.class, webContext.requiredType);
        }
    }

    /**
     * testInit03()
     * <br><br>
     * 
     * (異常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(状態) context.getBean("forbiddenURIChecker"):null<br>
     *         (状態) config.getInitParameter(INITPARAM_KEY_CHECKER_BEANID):null<br>
     *         
     * <br>
     * 期待値：(状態変化) 例外:NoSuchBeanDefinitionException<br>
     *         (状態変化) ログ:エラーレベル<br>
     *                    there's no such bean definition. BeanID=checkerBeanID.<br>
     *         
     * <br>
     * getBeanで取得できなかった場合、NoSuchBeanDefinitionExceptionをスローすること
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testInit03() throws Exception {
        // 前処理
        // ForbiddenURIFilter
        ForbiddenURIFilter filter = new ForbiddenURIFilter();
        
        // FilterConfig
        MockFilterConfig config = new MockFilterConfig();
        
        // config.servletContext : this
        ServletContext context = this.servletContext;
        
        // getBeanでNoSuchBeanDefinitionException例外を起こす
        // WebApplicationContext実装クラス
        WebApplicationContextImpl03 webContext =
            new WebApplicationContextImpl03();
        
        context.setAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE, webContext);
        
        config.setServletContext(context);
        
        // config.getInitParameter(INITPARAM_KEY_CHECKER_BEANID) : null        
        config.setInitParameter("checkerBeanID", null);
        
        
        try {
            // テスト実施
            filter.init(config);
            fail();
        } catch (NoSuchBeanDefinitionException e) {
            // 判定
            // log : error
            assertTrue(LogUTUtil.checkError("there's no such bean definition. BeanID=forbiddenURIChecker."));
            
            // name
            assertEquals("forbiddenURIChecker", webContext.name);
            
            // requiredType : ソースコードでべた書きされているのでテストの意味はない
            assertEquals(ForbiddenURIChecker.class, webContext.requiredType);
        }
    }

    /**
     * testInit04()
     * <br><br>
     * 
     * (異常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(状態) context.getBean("forbiddenURIChecker"):生成できないインスタンス<br>
     *         (状態) config.getInitParameter(INITPARAM_KEY_CHECKER_BEANID):null<br>
     *         
     * <br>
     * 期待値：(状態変化) 例外:BeansException<br>
     *         (状態変化) ログ:エラーレベル<br>
     *                    the bean could not be created. BeanID=checkerBeanID.<br>
     *         
     * <br>
     * getBeanでインスタンスが生成できなかった場合、BeansExceptionをスローすること
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testInit04() throws Exception {
        // 前処理
        // ForbiddenURIFilter
        ForbiddenURIFilter filter = new ForbiddenURIFilter();
        
        // FilterConfig
        MockFilterConfig config = new MockFilterConfig();
        
        // config.servletContext : this
        ServletContext context = this.servletContext;
        
        // getBeanでNoSuchBeanDefinitionException例外を起こす
        // WebApplicationContext実装クラス
        WebApplicationContextImpl04 webContext =
            new WebApplicationContextImpl04();
        
        context.setAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE, webContext);
        
        config.setServletContext(context);
        
        // config.getInitParameter(INITPARAM_KEY_CHECKER_BEANID) : null
        config.setInitParameter("checkerBeanID", null);
        
        
        try {
            // テスト実施
            filter.init(config);
            fail();
        } catch (BeansException e) {
            // 判定
            // log : error
            assertTrue(LogUTUtil.checkError("the bean could not be created. BeanID=forbiddenURIChecker."));
            
            // name
            assertEquals("forbiddenURIChecker", webContext.name);
            
            // requiredType : ソースコードでべた書きされているのでテストの意味はない
            assertEquals(ForbiddenURIChecker.class, webContext.requiredType);
            
            // 例外種類確認：
            // BeansException実装例外ForbiddenURIFilter_BeansExceptionStub01
            assertEquals(
                    ForbiddenURIFilter_BeansExceptionStub01.class.getName(),
                    e.getClass().getName());
        }
    }

    /**
     * testInit05()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(状態) context.getBean("ABC"):ForbiddenCheckerインスタンス<br>
     *         (状態) config.getInitParameter(INITPARAM_KEY_CHECKER_BEANID):"ABC"<br>
     *         
     * <br>
     * 期待値：(状態変化) checker:getBeanの戻り値と同一のインスタンス<br>
     *         
     * <br>
     * getBeanの戻り値を正常に属性に設定すること。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testInit05() throws Exception {
        // 前処理
        // ForbiddenURIFilter
        ForbiddenURIFilter filter = new ForbiddenURIFilter();
        
        // FilterConfig
        MockFilterConfig config = new MockFilterConfig();
        
        // config.servletContext : this
        ServletContext context = this.servletContext;
        
        // getBeanでForbiddenURIChecker実装クラスのインスタンスを返す
        // WebApplicationContext実装クラス
        WebApplicationContextImpl01 webContext =
            new WebApplicationContextImpl01();
        
        // ForbiddenURIChecker実装クラス
        ForbiddenURIFilter_ForbiddenURICheckerStub01 checker =
            new ForbiddenURIFilter_ForbiddenURICheckerStub01();
        UTUtil.setPrivateField(webContext, "checker", checker);
        
        context.setAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE, webContext);
        
        config.setServletContext(context);
        
        // config.getInitParameter(INITPARAM_KEY_CHECKER_BEANID) : "ABC"
        config.setInitParameter("checkerBeanID", "ABC");
        
        
        // テスト実施
        filter.init(config);

        
        // 判定
        // getBeanの戻り値と同一のインスタンス
        assertSame(checker, UTUtil.getPrivateField(filter, "checker"));
        
        // name : "ABC"
        assertEquals("ABC", webContext.name);
        
        // requiredType : ソースコードでべた書きされているのでテストの意味はない
        assertEquals(ForbiddenURIChecker.class, webContext.requiredType);
    }

    /**
     * testDoFilter01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) req:HTTPリクエスト<br>
     *         (引数) req.getRequestURI():/sample2/secure/blogic.do<br>
     *         (引数) req.getContextPath():/sample2<br>
     *         (引数) res:HTTPレスポンス<br>
     *         (引数) chain:フィルタチェイン<br>
     *         (状態) checker.isAllowedURI():true<br>
     *         
     * <br>
     * 期待値：
     * <br>
     * checker.isAllowedURI()がtrueの場合、例外が発生せずに処理が終了することのテスト。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testDoFilter01() throws Exception {
        // 前処理
        MockHttpServletRequest req = new MockHttpServletRequest();
        MockHttpServletResponse res = new MockHttpServletResponse();
        FilterChainImpl01 chain = new FilterChainImpl01();
        
        // requestURI : "/sample2/secure/blogic.do"
        req.setRequestURI("/sample2/secure/blogic.do");
        // contextPath : "/sample2"
        req.setContextPath("/sample2");

        ForbiddenURIFilter filter = new ForbiddenURIFilter();
        
        // checker.isAllowedURI() : true
        ForbiddenURIFilter_ForbiddenURICheckerStub01 checker =
            new ForbiddenURIFilter_ForbiddenURICheckerStub01();
        checker.isAllowed = true;
        UTUtil.setPrivateField(filter, "checker", checker);

        // テスト実施
        filter.doFilter(req, res, chain);

        // 判定
        assertTrue(chain.isDoFilter);
    }

    /**
     * testDoFilter02()
     * <br><br>
     * 
     * (異常系)
     * <br>
     * 観点：G
     * <br><br>
     * 入力値：(引数) req:HTTPリクエスト<br>
     *         (引数) req.getRequestURI():/sample2/secure/blogic.do<br>
     *         (引数) req.getContextPath():/sample2<br>
     *         (引数) res:HTTPレスポンス<br>
     *         (引数) chain:フィルタチェイン<br>
     *         (状態) checker.isAllowedURI():false<br>
     *         
     * <br>
     * 期待値：(状態変化) 例外:ForbiddenURIExceptionスロー<br>
     *         (状態変化) ログ:request url is forbidden!<br>
     *         
     * <br>
     * checker.isAllowedURI()がfalseの場合、ForbiddenURIExceptionをスローする
     * ことのテスト。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testDoFilter02() throws Exception {
        // 前処理
        MockHttpServletRequest req = new MockHttpServletRequest();
        MockHttpServletResponse res = new MockHttpServletResponse();
        FilterChainImpl01 chain = new FilterChainImpl01();
        
        // requestURI : "/sample2/secure/blogic.do"
        req.setRequestURI("/sample2/secure/blogic.do");
        // contextPath : "/sample2"
        req.setContextPath("/sample2");

        ForbiddenURIFilter filter = new ForbiddenURIFilter();
        
        // checker.isAllowedURI() : false
        ForbiddenURIFilter_ForbiddenURICheckerStub01 checker =
            new ForbiddenURIFilter_ForbiddenURICheckerStub01();
        checker.isAllowed = false;
        UTUtil.setPrivateField(filter, "checker", checker);

        try {
            // テスト実施
            filter.doFilter(req, res, chain);
            fail();
        } catch (ForbiddenURIException e) {
            // 判定
            assertTrue(LogUTUtil.checkError("request url is forbidden!"));
            assertFalse(chain.isDoFilter);
        }
    }

    /**
     * testDoFilter03()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) req:HTTPリクエスト<br>
     *         (引数) req.getRequestURI():空文字<br>
     *         (引数) req.getContextPath():空文字<br>
     *         (引数) res:HTTPレスポンス<br>
     *         (引数) chain:フィルタチェイン<br>
     *         (状態) checker.isAllowedURI():true<br>
     *         
     * <br>
     * 期待値：
     * <br>
     * HTTPリクエストに設定されているURIが空文字で、フィルタ設定の許可URIも
     * 空文字の場合でも、checker.isAllowedURI()がtrueの場合、例外が発生せずに
     * 処理が終了することのテスト。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testDoFilter03() throws Exception {
        // 前処理
        MockHttpServletRequest req = new MockHttpServletRequest();
        MockHttpServletResponse res = new MockHttpServletResponse();
        FilterChainImpl01 chain = new FilterChainImpl01();
        
        // requestURI : 空文字
        req.setRequestURI("");
        // contextPath : 空文字
        req.setContextPath("");

        ForbiddenURIFilter filter = new ForbiddenURIFilter();
        
        // checker.isAllowedURI() : true
        ForbiddenURIFilter_ForbiddenURICheckerStub01 checker =
            new ForbiddenURIFilter_ForbiddenURICheckerStub01();
        checker.isAllowed = true;
        UTUtil.setPrivateField(filter, "checker", checker);

        // テスト実施
        filter.doFilter(req, res, chain);

        // 判定
        assertTrue(chain.isDoFilter);
    }

    /**
     * testDoFilter04()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) req:HTTPリクエスト<br>
     *         (引数) req.getRequestURI():/sample2<br>
     *         (引数) req.getContextPath():/sample2<br>
     *         (引数) res:HTTPレスポンス<br>
     *         (引数) chain:フィルタチェイン<br>
     *         (状態) checker.isAllowedURI():true<br>
     *         
     * <br>
     * 期待値：
     * <br>
     * HTTPリクエストに設定されているURIとコンテキスト名の文字列長が等しい場合
     * でも、checker.isAllowedURI()がtrueの場合、例外が発生せずに処理が終了する
     * ことのテスト。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testDoFilter04() throws Exception {
        // 前処理
        MockHttpServletRequest req = new MockHttpServletRequest();
        MockHttpServletResponse res = new MockHttpServletResponse();
        FilterChainImpl01 chain = new FilterChainImpl01();
        
        // requestURI : "/sample2"
        req.setRequestURI("/sample2");
        // contextPath : "/sample2"
        req.setContextPath("/sample2");

        ForbiddenURIFilter filter = new ForbiddenURIFilter();
        
        // checker.isAllowedURI() : true
        ForbiddenURIFilter_ForbiddenURICheckerStub01 checker =
            new ForbiddenURIFilter_ForbiddenURICheckerStub01();
        checker.isAllowed = true;
        UTUtil.setPrivateField(filter, "checker", checker);

        // テスト実施
        filter.doFilter(req, res, chain);

        // 判定
        assertTrue(chain.isDoFilter);
    }
}