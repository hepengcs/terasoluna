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

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import jp.terasoluna.utlib.LogUTUtil;
import junit.framework.TestCase;

/**
 * {@link jp.terasoluna.fw.web.thin.EvidenceLogFilter} クラスのブラックボックステスト。
 * 
 * <p>
 * <h4>【クラスの概要】</h4>
 * エビデンスログを出力処理を行う。
 * <p>
 * 
 * @see jp.terasoluna.fw.web.thin.EvidenceLogFilter
 */
public class EvidenceLogFilterTest extends TestCase {

    /**
     * このテストケースを実行する為の
     * GUI アプリケーションを起動する。
     * 
     * @param args java コマンドに設定されたパラメータ
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(EvidenceLogFilterTest.class);
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
    public EvidenceLogFilterTest(String name) {
        super(name);
    }

    /**
     * testDoFilter01()
     * <br><br>
     * 
     * (正常系)<br>
     * <br>
     * 観点：A <br>
     * <br><br>
     * 入力値：(引数) req:not null<br>
     *         (引数) res:not null<br>
     *         (引数) chain:not null<br>
     *         (状態) EVIDENCELOG_THRU_KEY:null<br>
     *         (状態) requestURI:null<br>
     *         (状態) paramMap:空のMap<br>
     *         
     * <br>
     * 期待値：(状態変化) EVIDENCELOG_THRU_KEY:"true"<br>
     *         (状態変化) ログ:【デバッグログ】<br>
     *                    "**** EVIDENCE ***: --------------------------------------------"<br>
     *                    "**** EVIDENCE ***: RequestURI = null<br>
     *                    "**** EVIDENCE ***: Parameters = {"<br>
     *                    "**** EVIDENCE ***: }"<br>
     *                    "**** EVIDENCE ***: --------------------------------------------"<br>
     *         (状態変化) doFilter:実行する<br>
     *         
     * <br>
     * EVIDENCELOG_THRU_KEYがnullの場合、 リクエストURI(null)とパラメータ(空のMap)をログに出力し、FilterChain#doFilter()メソッドを実行することを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testDoFilter01() throws Exception {
        // 前処理
        EvidenceLogFilter filter = new EvidenceLogFilter();
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        // requestURI:""
        request.setRequestURI(null);
        
        // 前提条件 ・EVIDENCELOG_THRU_KEYがNull
        //        ・requestURIがNull
        //        ・paramMapが空マップ
        EvidenceLogFilter_FilterChainStub01 filterChain =
            new EvidenceLogFilter_FilterChainStub01();
        
        // テスト実施
        filter.doFilter(request, response, filterChain);
        // 判定
        assertEquals("true", request.getAttribute("EVIDENCELOG_THRU_KEY"));
        // ログチェック
        assertTrue(LogUTUtil.checkDebug("**** EVIDENCE ***: --------------------------------------------"));
        assertTrue(LogUTUtil.checkDebug("**** EVIDENCE ***: RequestURI = null"));
        assertTrue(LogUTUtil.checkDebug("**** EVIDENCE ***: Parameters = {"));
        assertTrue(LogUTUtil.checkDebug("**** EVIDENCE ***: }"));
        assertTrue(LogUTUtil.checkDebug("**** EVIDENCE ***: --------------------------------------------"));
        // doFilterを実行するか
        assertTrue(filterChain.isCalled);
    }

    /**
     * testDoFilter02()
     * <br><br>
     * 
     * (正常系)<br>
     * <br>
     * 観点：A <br>
     * <br><br>
     * 入力値：(引数) req:not null<br>
     *         (引数) res:not null<br>
     *         (引数) chain:not null<br>
     *         (状態) EVIDENCELOG_THRU_KEY:null<br>
     *         (状態) requestURI:""<br>
     *         (状態) paramMap:["param1"->null]<br>
     *         
     * <br>
     * 期待値：(状態変化) EVIDENCELOG_THRU_KEY:"true"<br>
     *         (状態変化) ログ:【デバッグログ】<br>
     *                    "**** EVIDENCE ***: --------------------------------------------"<br>
     *                    "**** EVIDENCE ***: RequestURI = "<br>
     *                    "**** EVIDENCE ***: Parameters = {"<br>
     *                    "**** EVIDENCE ***:   param1[0] = null"<br>
     *                    "**** EVIDENCE ***: }"<br>
     *                    "**** EVIDENCE ***: --------------------------------------------"<br>
     *         (状態変化) doFilter:実行する<br>
     *         
     * <br>
     * EVIDENCELOG_THRU_KEYがnullの場合、リクエストURI(空文字)とパラメータ(1件で値がnull)をログに出力し、 FilterChain#doFilter()メソッドを実行することを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testDoFilter02() throws Exception {
        // 前処理
        EvidenceLogFilter filter = new EvidenceLogFilter();
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        // requestURI:""
        request.setRequestURI("");
        // paramMap:["param1"->null]
        String value = null;
        request.addParameter("param1", value);
        
        EvidenceLogFilter_FilterChainStub01 filterChain =
            new EvidenceLogFilter_FilterChainStub01();
        
        // テスト実施
        filter.doFilter(request, response, filterChain);
        // 判定
        assertEquals("true", request.getAttribute("EVIDENCELOG_THRU_KEY"));
        // ログチェック
        assertTrue(LogUTUtil.checkDebug("**** EVIDENCE ***: --------------------------------------------"));
        assertTrue(LogUTUtil.checkDebug("**** EVIDENCE ***: RequestURI = "));
        assertTrue(LogUTUtil.checkDebug("**** EVIDENCE ***: Parameters = {"));
        assertTrue(LogUTUtil.checkDebug("**** EVIDENCE ***: Parameters = {"));
        assertTrue(LogUTUtil.checkDebug("**** EVIDENCE ***:   param1[0] = null"));
        assertTrue(LogUTUtil.checkDebug("**** EVIDENCE ***: }"));
        assertTrue(LogUTUtil.checkDebug("**** EVIDENCE ***: --------------------------------------------"));
        // doFilterを実行するか
        assertTrue(filterChain.isCalled);
    }

    /**
     * testDoFilter03()
     * <br><br>
     * 
     * (正常系)<br>
     * <br>
     * 観点：A <br>
     * <br><br>
     * 入力値：(引数) req:not null<br>
     *         (引数) res:not null<br>
     *         (引数) chain:not null<br>
     *         (状態) EVIDENCELOG_THRU_KEY:null<br>
     *         (状態) requestURI:"reqURI"<br>
     *         (状態) paramMap:["param1"->""]<br>
     *         
     * <br>
     * 期待値：(状態変化) EVIDENCELOG_THRU_KEY:"true"<br>
     *         (状態変化) ログ:【デバッグログ】<br>
     *                    "**** EVIDENCE ***: --------------------------------------------"<br>
     *                    "**** EVIDENCE ***: RequestURI = reqURI"<br>
     *                    "**** EVIDENCE ***: Parameters = {"<br>
     *                    "**** EVIDENCE ***:   param1[0] = "<br>
     *                    "**** EVIDENCE ***: }"<br>
     *                    "**** EVIDENCE ***: --------------------------------------------"<br>
     *         (状態変化) doFilter:実行する<br>
     *         
     * <br>
     * EVIDENCELOG_THRU_KEYがnullの場合、リクエストURI(not null)とパラメータ(1件で値が空文字)をログに出力し、FilterChain#doFilter()メソッドを実行することを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testDoFilter03() throws Exception {
        // 前処理
        EvidenceLogFilter filter = new EvidenceLogFilter();
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        // requestURI:""
        request.setRequestURI("reqURI");
        // paramMap:["param1"->""]
        request.addParameter("param1", "");
        
        EvidenceLogFilter_FilterChainStub01 filterChain =
            new EvidenceLogFilter_FilterChainStub01();
        
        // テスト実施
        filter.doFilter(request, response, filterChain);
        // 判定
        assertEquals("true", request.getAttribute("EVIDENCELOG_THRU_KEY"));
        // ログチェック
        assertTrue(LogUTUtil.checkDebug("**** EVIDENCE ***: --------------------------------------------"));
        assertTrue(LogUTUtil.checkDebug("**** EVIDENCE ***: RequestURI = reqURI"));
        assertTrue(LogUTUtil.checkDebug("**** EVIDENCE ***: Parameters = {"));
        assertTrue(LogUTUtil.checkDebug("**** EVIDENCE ***:   param1[0] = "));
        assertTrue(LogUTUtil.checkDebug("**** EVIDENCE ***: }"));
        assertTrue(LogUTUtil.checkDebug("**** EVIDENCE ***: --------------------------------------------"));
        // doFilterを実行するか
        assertTrue(filterChain.isCalled);
    }

    /**
     * testDoFilter04()
     * <br><br>
     * 
     * (正常系)<br>
     * <br>
     * 観点：A <br>
     * <br><br>
     * 入力値：(引数) req:not null<br>
     *         (引数) res:not null<br>
     *         (引数) chain:not null<br>
     *         (状態) EVIDENCELOG_THRU_KEY:null<br>
     *         (状態) requestURI:"reqURI"<br>
     *         (状態) paramMap:["param1"->"test1"]<br>
     *         
     * <br>
     * 期待値：(状態変化) EVIDENCELOG_THRU_KEY:"true"<br>
     *         (状態変化) ログ:【デバッグログ】<br>
     *                    "**** EVIDENCE ***: --------------------------------------------"<br>
     *                    "**** EVIDENCE ***: RequestURI = reqURI"<br>
     *                    "**** EVIDENCE ***: Parameters = {"<br>
     *                    "**** EVIDENCE ***:   param1[0] = test1"<br>
     *                    "**** EVIDENCE ***: }"<br>
     *                    "**** EVIDENCE ***: --------------------------------------------"<br>
     *         (状態変化) doFilter:実行する<br>
     *         
     * <br>
     * EVIDENCELOG_THRU_KEYがnullの場合、リクエストURI(not null)とパラメータ(1件)をログに出力し、FilterChain#doFilter()メソッドを実行することを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testDoFilter04() throws Exception {
        // 前処理
        EvidenceLogFilter filter = new EvidenceLogFilter();
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        // requestURI:""
        request.setRequestURI("reqURI");
        // paramMap:["param1"->test1]
        request.addParameter("param1", "test1");
        
        EvidenceLogFilter_FilterChainStub01 filterChain =
            new EvidenceLogFilter_FilterChainStub01();
        
        // テスト実施
        filter.doFilter(request, response, filterChain);
        // 判定
        assertEquals("true", request.getAttribute("EVIDENCELOG_THRU_KEY"));
        // ログチェック
        assertTrue(LogUTUtil.checkDebug("**** EVIDENCE ***: --------------------------------------------"));
        assertTrue(LogUTUtil.checkDebug("**** EVIDENCE ***: RequestURI = reqURI"));
        assertTrue(LogUTUtil.checkDebug("**** EVIDENCE ***: Parameters = {"));
        assertTrue(LogUTUtil.checkDebug("**** EVIDENCE ***:   param1[0] = test1"));
        assertTrue(LogUTUtil.checkDebug("**** EVIDENCE ***: }"));
        assertTrue(LogUTUtil.checkDebug("**** EVIDENCE ***: --------------------------------------------"));
        // doFilterを実行するか
        assertTrue(filterChain.isCalled);
    }

    /**
     * testDoFilter05()
     * <br><br>
     * 
     * (正常系)<br>
     * <br>
     * 観点：A <br>
     * <br><br>
     * 入力値：(引数) req:not null<br>
     *         (引数) res:not null<br>
     *         (引数) chain:not null<br>
     *         (状態) EVIDENCELOG_THRU_KEY:null<br>
     *         (状態) requestURI:"reqURI"<br>
     *         (状態) paramMap:["param1"->"test1"]<br>
     *                ["param2"->"test2"]<br>
     *         
     * <br>
     * 期待値：(状態変化) EVIDENCELOG_THRU_KEY:"true"<br>
     *         (状態変化) ログ:【デバッグログ】<br>
     *                    "**** EVIDENCE ***: --------------------------------------------"<br>
     *                    "**** EVIDENCE ***: RequestURI = reqURI"<br>
     *                    "**** EVIDENCE ***: Parameters = {"<br>
     *                    "**** EVIDENCE ***:   param1[0] = test1"<br>
     *                    "**** EVIDENCE ***:   param2[0] = test2"<br>
     *                    "**** EVIDENCE ***: }"<br>
     *                    "**** EVIDENCE ***: --------------------------------------------"<br>
     *         (状態変化) doFilter:実行する<br>
     *         
     * <br>
     * EVIDENCELOG_THRU_KEYがnullの場合、リクエストURI(not null)とパラメータ(2件)をログに出力し、FilterChain#doFilter()メソッドを実行することを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testDoFilter05() throws Exception {
        // 前処理
        EvidenceLogFilter filter = new EvidenceLogFilter();
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        // requestURI:""
        request.setRequestURI("reqURI");
        // ["param1"->"test1"]
        // ["param2"->"test2"]
        request.addParameter("param1", "test1");
        request.addParameter("param2", "test2");
        
        EvidenceLogFilter_FilterChainStub01 filterChain =
            new EvidenceLogFilter_FilterChainStub01();
        
        // テスト実施
        filter.doFilter(request, response, filterChain);
        // 判定
        assertEquals("true", request.getAttribute("EVIDENCELOG_THRU_KEY"));
        // ログチェック
        assertTrue(LogUTUtil.checkDebug("**** EVIDENCE ***: --------------------------------------------"));
        assertTrue(LogUTUtil.checkDebug("**** EVIDENCE ***: RequestURI = reqURI"));
        assertTrue(LogUTUtil.checkDebug("**** EVIDENCE ***: Parameters = {"));
        assertTrue(LogUTUtil.checkDebug("**** EVIDENCE ***:   param1[0] = test1"));
        assertTrue(LogUTUtil.checkDebug("**** EVIDENCE ***:   param2[0] = test2"));
        assertTrue(LogUTUtil.checkDebug("**** EVIDENCE ***: }"));
        assertTrue(LogUTUtil.checkDebug("**** EVIDENCE ***: --------------------------------------------"));
        // doFilterを実行するか
        assertTrue(filterChain.isCalled);
    }

    /**
     * testDoFilter06()
     * <br><br>
     * 
     * (正常系)<br>
     * <br>
     * 観点：A <br>
     * <br><br>
     * 入力値：(引数) req:not null<br>
     *         (引数) res:not null<br>
     *         (引数) chain:not null<br>
     *         (状態) EVIDENCELOG_THRU_KEY:null<br>
     *         (状態) requestURI:"reqURI"<br>
     *         (状態) paramMap:["param1"->("test1", "test2", "test3")]<br>
     *         
     * <br>
     * 期待値：(状態変化) EVIDENCELOG_THRU_KEY:"true"<br>
     *         (状態変化) ログ:【デバッグログ】<br>
     *                    "**** EVIDENCE ***: --------------------------------------------"<br>
     *                    "**** EVIDENCE ***: RequestURI = reqURI"<br>
     *                    "**** EVIDENCE ***: Parameters = {"<br>
     *                    "**** EVIDENCE ***:   param1[0] = test1"<br>
     *                    "**** EVIDENCE ***:   param1[1] = test2"<br>
     *                    "**** EVIDENCE ***:   param1[2] = test3"<br>
     *                    "**** EVIDENCE ***: }"<br>
     *                    "**** EVIDENCE ***: --------------------------------------------"<br>
     *         (状態変化) doFilter:実行する<br>
     *         
     * <br>
     * EVIDENCELOG_THRU_KEYがnullの場合、リクエストURI(not null)とパラメータ(1件で値がArray)をログに出力し、FilterChain#doFilter()メソッドを実行することを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testDoFilter06() throws Exception {
        // 前処理
        EvidenceLogFilter filter = new EvidenceLogFilter();
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        // requestURI:""
        request.setRequestURI("reqURI");
        // ["param1"->("test1", "test2", "test3")]
        request.addParameter("param1", new String[]{"test1", "test2", "test3"});
        
        EvidenceLogFilter_FilterChainStub01 filterChain =
            new EvidenceLogFilter_FilterChainStub01();
        
        // テスト実施
        filter.doFilter(request, response, filterChain);
        // 判定
        assertEquals("true", request.getAttribute("EVIDENCELOG_THRU_KEY"));
        // ログチェック
        assertTrue(LogUTUtil.checkDebug("**** EVIDENCE ***: --------------------------------------------"));
        assertTrue(LogUTUtil.checkDebug("**** EVIDENCE ***: RequestURI = reqURI"));
        assertTrue(LogUTUtil.checkDebug("**** EVIDENCE ***: Parameters = {"));
        assertTrue(LogUTUtil.checkDebug("**** EVIDENCE ***:   param1[0] = test1"));
        assertTrue(LogUTUtil.checkDebug("**** EVIDENCE ***:   param1[1] = test2"));
        assertTrue(LogUTUtil.checkDebug("**** EVIDENCE ***:   param1[2] = test3"));
        assertTrue(LogUTUtil.checkDebug("**** EVIDENCE ***: }"));
        assertTrue(LogUTUtil.checkDebug("**** EVIDENCE ***: --------------------------------------------"));
        // doFilterを実行するか
        assertTrue(filterChain.isCalled);
    }

    /**
     * testDoFilter07()
     * <br><br>
     * 
     * (正常系)<br>
     * <br>
     * 観点：A <br>
     * <br><br>
     * 入力値：(引数) req:not null<br>
     *         (引数) res:not null<br>
     *         (引数) chain:not null<br>
     *         (状態) EVIDENCELOG_THRU_KEY:null<br>
     *         (状態) requestURI:"reqURI"<br>
     *         (状態) paramMap:["param1"->{"test1", "test2", "test3", "", null}]<br>
     *                ["param2"->"テスト"]<br>
     *                ["param3"->{}]※要素が無い配列<br>
     *         
     * <br>
     * 期待値：(状態変化) EVIDENCELOG_THRU_KEY:"true"<br>
     *         (状態変化) ログ:【デバッグログ】<br>
     *                    "**** EVIDENCE ***: --------------------------------------------"<br>
     *                    "**** EVIDENCE ***: RequestURI = reqURI"<br>
     *                    "**** EVIDENCE ***: Parameters = {"<br>
     *                    "**** EVIDENCE ***:   param1[0] = test1"<br>
     *                    "**** EVIDENCE ***:   param1[1] = test2"<br>
     *                    "**** EVIDENCE ***:   param1[2] = test3"<br>
     *                    "**** EVIDENCE ***:   param1[3] = "<br>
     *                    "**** EVIDENCE ***:   param1[4] = null"<br>
     *                    "**** EVIDENCE ***:   param2[0] = テスト"<br>
     *                    "**** EVIDENCE ***: }"<br>
     *                    "**** EVIDENCE ***: --------------------------------------------"<br>
     *         (状態変化) doFilter:実行する<br>
     *         
     * <br>
     * EVIDENCELOG_THRU_KEYがnullの場合、リクエストURI(not null)とパラメータ(3件で2つの値がArray)をログに出力し、FilterChain#doFilter()メソッドを実行することを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testDoFilter07() throws Exception {
        // 前処理
        EvidenceLogFilter filter = new EvidenceLogFilter();
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        // requestURI:""
        request.setRequestURI("reqURI");
        // ["param1"->{"test1", "test2", "test3", "", null}]
        // ["param2"->"テスト"]
        // ["param3"->{}]※要素が無い配列
        request.addParameter("param1", new String[]{"test1", "test2", "test3", "", null});
        request.addParameter("param2", "テスト");
        request.addParameter("param3", new String[]{});
        EvidenceLogFilter_FilterChainStub01 filterChain =
            new EvidenceLogFilter_FilterChainStub01();
        
        // テスト実施
        filter.doFilter(request, response, filterChain);
        // 判定
        assertEquals("true", request.getAttribute("EVIDENCELOG_THRU_KEY"));
        // ログチェック
        assertTrue(LogUTUtil.checkDebug("**** EVIDENCE ***: --------------------------------------------"));
        assertTrue(LogUTUtil.checkDebug("**** EVIDENCE ***: RequestURI = reqURI"));
        assertTrue(LogUTUtil.checkDebug("**** EVIDENCE ***: Parameters = {"));
        assertTrue(LogUTUtil.checkDebug("**** EVIDENCE ***:   param1[0] = test1"));
        assertTrue(LogUTUtil.checkDebug("**** EVIDENCE ***:   param1[1] = test2"));
        assertTrue(LogUTUtil.checkDebug("**** EVIDENCE ***:   param1[2] = test3"));
        assertTrue(LogUTUtil.checkDebug("**** EVIDENCE ***:   param1[3] = "));
        assertTrue(LogUTUtil.checkDebug("**** EVIDENCE ***:   param1[4] = null"));
        assertTrue(LogUTUtil.checkDebug("**** EVIDENCE ***:   param2[0] = テスト"));
        assertTrue(LogUTUtil.checkDebug("**** EVIDENCE ***: }"));
        assertTrue(LogUTUtil.checkDebug("**** EVIDENCE ***: --------------------------------------------"));
        // doFilterを実行するか
        assertTrue(filterChain.isCalled);
    }

}
