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

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import jp.terasoluna.utlib.MockFilterConfig;
import jp.terasoluna.utlib.MockHttpServletRequest;
import jp.terasoluna.utlib.MockHttpServletResponse;
import jp.terasoluna.utlib.UTUtil;
import jp.terasoluna.utlib.spring.SpringTestCase;
import jp.terasoluna.fw.web.rich.RequestContextHandlingFilter;

/**
 * {@link jp.terasoluna.fw.web.rich.RequestContextHandlingFilter} クラスのブラックボックステスト。
 * 
 * <p>
 * <h4>【クラスの概要】</h4>
 * 制御情報の生成・破棄を行うサーブレットフィルタ。<br>
 * 前提条件：FilterConfig、FilterChain、サーブレットリクエスト、サーブレットレスポンスはNull値にならない。サーブレットリクエストは必ずHTTPリクエストにキャストできる。
 * <p>
 * 
 * @see jp.terasoluna.fw.web.rich.RequestContextHandlingFilter
 */
public class RequestContextHandlingFilterTest extends SpringTestCase {

    /**
     * このテストケースを実行する為の
     * GUI アプリケーションを起動する。
     * 
     * @param args java コマンドに設定されたパラメータ
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(RequestContextHandlingFilterTest.class);
    }

    /**
     * 初期化処理を行う。
     * 
     * @throws Exception このメソッドで発生した例外
     * @see jp.terasoluna.utlib.spring.SpringTestCase#doOnSetUp()
     */
    @Override
    protected void doOnSetUp() throws Exception {
        
    }

    /**
     * Bean定義ファイルの場所を取得する。
     * 
     * @throws Exception このメソッドで発生した例外
     * @see jp.terasoluna.utlib.spring.SpringTestCase#getConfigLocations()
     */
    @Override
    protected String[] getConfigLocations() {
        return new String[]{
            "jp/terasoluna/fw/web/rich/RequestContextHandlingFilterTest.xml"
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
     * 入力値：(引数) config:FilterConfig実装クラス<br>
     *                ｛getInitParamter("ctxSupportBeanID"）：null｝<br>
     *         (状態) this.ctxSupprt:null<br>
     *         
     * <br>
     * 期待値：(状態変化) this.ctxSupprt:context.getBean("ctxSupport")の戻り値と等しいことを確認する。<br>
     *         
     * <br>
     * RequestContextSupportクラスのBeanIDを指定せず、デフォルトのBeanIDを使用するパターンのテスト。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testInit01() throws Exception {
        // FilterConfigの設定
        MockFilterConfig config = new MockFilterConfig();
        config.setServletContext(servletContext);

        // RequestContextHandlingFilterの生成
        RequestContextHandlingFilter filter = new RequestContextHandlingFilter();

        // テスト実施
        filter.init(config);

        // 判定
        ApplicationContext context = WebApplicationContextUtils
                .getWebApplicationContext(config.getServletContext());
        assertSame(context.getBean("ctxSupport"), UTUtil.getPrivateField(
                filter, "ctxSupport"));
    }

    /**
     * testInit02()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) config:FilterConfig実装クラス<br>
     *                ｛getInitParamter("ctxSupportBeanID"）："sampleCtxSupport"｝<br>
     *         (状態) this.ctxSupprt:null<br>
     *         
     * <br>
     * 期待値：(状態変化) this.ctxSupprt:context.getBean("sampleCtxSupport")の戻り値と等しいことを確認する。<br>
     *         
     * <br>
     * RequestContextSupportクラスのBeanIDを直接指定するテスト。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testInit02() throws Exception {
        // FilterConfigの設定
        MockFilterConfig config = new MockFilterConfig();
        config.setInitParameter("ctxSupportBeanID", "sampleCtxSupport");
        config.setServletContext(servletContext);

        // RequestContextHandlingFilterの生成
        RequestContextHandlingFilter filter = new RequestContextHandlingFilter();

        // テスト実施
        filter.init(config);

        // 判定
        ApplicationContext context = WebApplicationContextUtils
                .getWebApplicationContext(config.getServletContext());
        assertSame(context.getBean("sampleCtxSupport"), UTUtil.getPrivateField(
                filter, "ctxSupport"));
    }

    /**
     * testDoFilter01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) request:not null<br>
     *         (引数) response:not null<br>
     *         (引数) chain:not null<br>
     *         
     * <br>
     * 期待値：(状態変化) ctxSupport.generateContext():メソッドが呼び出されたことを確認する。引数を受け取ったことを確認する。<br>
     *         (状態変化) chain.doFilter():メソッドが呼び出されたことを確認する。引数を受け取ったことを確認する。<br>
     *         (状態変化) ctxSupprt.destroyContext():メソッドが呼び出されたことを確認する。<br>
     *         
     * <br>
     * フィルタ処理のテスト。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testDoFilter01() throws Exception {
        // RequestContextHandlingFilterの生成
        RequestContextHandlingFilter filter = new RequestContextHandlingFilter();
        RequestContextHandlingFilter_RequestContextSupportStub01 support = new RequestContextHandlingFilter_RequestContextSupportStub01();
        UTUtil.setPrivateField(filter, "ctxSupport", support);

        // 引数の生成
        MockHttpServletRequest req = new MockHttpServletRequest();
        MockHttpServletResponse res = new MockHttpServletResponse();
        RequestContextHandlingFilter_FilterChainStub01 chain
            = new RequestContextHandlingFilter_FilterChainStub01();

        // テスト実行
        filter.doFilter(req, res, chain);

        // 判定        
        assertTrue(chain.isDoFilterCalled());
        assertTrue(support.isGenerateContextCalled());
        assertTrue(support.isDestroyContextCalled());
    }
}
