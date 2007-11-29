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

package jp.terasoluna.fw.web;

import java.util.HashMap;

import jp.terasoluna.utlib.MockHttpServletRequest;
import jp.terasoluna.utlib.MockHttpSession;
import jp.terasoluna.utlib.MockServletContext;
import junit.framework.TestCase;

/**
 * {@link jp.terasoluna.fw.web.RequestUtil} クラスの
 * ブラックボックステスト。
 *
 * <p>
 * <h4>【クラスの概要】</h4>
 * Requestに関するユーティリティクラス。
 * <p>
 *
 * @see jp.terasoluna.fw.web.RequestUtil
 */
public class RequestUtilTest extends TestCase {

    /**
     * このテストケースを実行する為の
     * GUI アプリケーションを起動する。
     *
     * @param args java コマンドに設定されたパラメータ
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(RequestUtilTest.class);
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
    public RequestUtilTest(String name) {
        super(name);
    }

    /**
     * testGetPathInfo01()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) request:not null<br>
     *         (状態) request.getRequestURI:"/sample1/logon.do"<br>
     *         (状態) request.getContextPath:"/sample1"<br>
     *
     * <br>
     * 期待値：(戻り値) String:"/logon.do"<br>
     *
     * <br>
     * リクエストパスが<コンテキスト>/<リソース名>の形式の場合、
     * /<リソース名>が取得できることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetPathInfo01() throws Exception {
        //テストデータ設定

        // 擬似リクエスト
        MockHttpServletRequest req = new MockHttpServletRequest();
        req.setRequestURI("/sample1/logon.do");
        req.setContextPath("/sample1");

        //テスト実行
        String path = RequestUtil.getPathInfo(req);

        //テスト結果確認
        // コンテキストパス以降のURIが取得できること
        assertEquals("/logon.do", path);
    }

    /**
     * testGetPathInfo02()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) request:not null<br>
     *         (状態) request.getRequestURI:"/sample2"<br>
     *         (状態) request.getContextPath:"/sample2"<br>
     *
     * <br>
     * 期待値：(戻り値) String:""<br>
     *
     * <br>
     * リクエストパスが<コンテキスト>の形式の場合、
     * 空文字が取得できることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetPathInfo02() throws Exception {
        //テストデータ設定

        // 擬似リクエスト
        MockHttpServletRequest req = new MockHttpServletRequest();
        req.setRequestURI("/sample2");
        req.setContextPath("/sample2");

        // テスト実行
        String path = RequestUtil.getPathInfo(req);

        // テスト結果確認
        // 空文字列が取得できること。
        assertEquals("", path);
    }

    /**
     * testGetPathInfo03()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) request:null<br>
     *
     * <br>
     * 期待値：(戻り値) String:null<br>
     *
     * <br>
     * 引数のrequestがnullの場合、nullが取得できることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetPathInfo03() throws Exception {
        // テスト実施
        String path = RequestUtil.getPathInfo(null);

        // 判定
        assertNull(path);
    }

    /**
     * testGetServletContext01()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) request:not null<br>
     *         (状態) request.getSession:not null<br>
     *         (状態) session.getServletContext:not null<br>
     *
     * <br>
     * 期待値：(戻り値) ServletContext:sessionから取得した
     * サーブレットコンテキストインスタンス<br>
     *
     * <br>
     * セッションからサーブレットコンテキストインスタンスが取得できることを
     * 確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetServletContext01() throws Exception {
        // 前処理
        MockHttpServletRequest req = new MockHttpServletRequest();
        MockServletContext ctx = new MockServletContext();
        MockHttpSession session = new MockHttpSession();
        session.setServletContext(ctx);
        req.setSession(session);

        // テスト実施
        Object result = RequestUtil.getServletContext(req);

        // 判定
        assertSame(ctx, result);
    }

    /**
     * testGetServletContext02()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) request:null<br>
     *
     * <br>
     * 期待値：(戻り値) ServletContext:null<br>
     *
     * <br>
     * 引数のrequestがnullの場合、nullが取得できることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetServletContext02() throws Exception {
        // テスト実施
        Object result = RequestUtil.getServletContext(null);

        // 判定
        assertNull(result);
    }

    /**
     * testIsChanged01()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) request:not null<br>
     *         (状態) request.getRequestURI:"/sample1/logon/start.do"<br>
     *         (状態) request.getAttribute("PREV_PATH_INFO"):"/logon/start.do"
     *         <br>
     *
     * <br>
     * 期待値：(戻り値) boolean:falseが返却されること。<br>
     *
     * <br>
     * コンテキストルート以下の階層とリクエストに保存された前回のパスの
     * 第一階層が一致する場合、falseが返却されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testIsChanged01() throws Exception {
        //テストデータ設定
        // 擬似リクエスト
        MockHttpServletRequest req = new MockHttpServletRequest();

        // 現在のコンテキストパス
        req.setContextPath("/sample1");

        // 現在のリクエストパス
        req.setRequestURI("/sample1/logon/start.do");

        // 前回のリクエストパス
        req.setAttribute("PREV_PATH_INFO", "/logon/start.do");

        //テスト実行
        boolean result = RequestUtil.isChanged(req);

        //テスト結果確認
        // 業務が切り替わっていないこと。
        assertFalse(result);
    }

    /**
     * testIsChanged02()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) request:not null<br>
     *         (状態) request.getRequestURI:"/sample1/logon/start.do"<br>
     *         (状態) request.getAttribute("PREV_PATH_INFO"):"/logon/end.do"<br>
     *
     * <br>
     * 期待値：(戻り値) boolean:falseが返却されること。<br>
     *
     * <br>
     * コンテキストルート以下の階層とリクエストに保存された前回のパスの
     * 第一階層が一致する場合、falseが返却されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testIsChanged02() throws Exception {
        //テストデータ設定
        // 擬似リクエスト
        MockHttpServletRequest req = new MockHttpServletRequest();

        // 現在のコンテキストパス
        req.setContextPath("/sample1");

        // 現在のリクエストパス
        req.setRequestURI("/sample1/logon/start.do");

        // 前回のリクエストパス
        req.setAttribute("PREV_PATH_INFO", "/logon/end.do");

        //テスト実行
        boolean result = RequestUtil.isChanged(req);

        //テスト結果確認
        // 業務が切り替わっていないこと。
        assertFalse(result);
    }

    /**
     * testIsChanged03()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) request:not null<br>
     *         (状態) request.getRequestURI:"/sample1/logon/start.do"<br>
     *         (状態) request.getAttribute("PREV_PATH_INFO"):"/logout/end.do"
     *         <br>
     *
     * <br>
     * 期待値：(戻り値) boolean:trueが返却されること。<br>
     *
     * <br>
     * コンテキストルート以下の階層とリクエストに保存された前回のパスの
     * 第一階層が一致しない場合、trueが返却されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testIsChanged03() throws Exception {
        //テストデータ設定
        // 擬似リクエスト
        MockHttpServletRequest req = new MockHttpServletRequest();

        // 現在のコンテキストパス
        req.setContextPath("/sample1");

        // 現在のリクエストパス
        req.setRequestURI("/sample1/logon/start.do");

        // 前回のリクエストパス
        req.setAttribute("PREV_PATH_INFO", "/logout/end.do");

        //テスト実行
        boolean result = RequestUtil.isChanged(req);

        //テスト結果確認
        // 業務が切り替わったと判断されること。
        assertTrue(result);
    }

    /**
     * testIsChanged04()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) request:not null<br>
     *         (状態) request.getRequestURI:"/sample1/logon/start.do"<br>
     *         (状態) request.getAttribute("PREV_PATH_INFO"):null<br>
     *
     * <br>
     * 期待値：(戻り値) boolean:trueが返却されること。<br>
     *
     * <br>
     * リクエストから前回のパスが取得できない場合、
     * trueが返却されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testIsChanged04() throws Exception {
        //テストデータ設定
        // 擬似リクエスト
        MockHttpServletRequest req = new MockHttpServletRequest();

        // 現在のコンテキストパス
        req.setContextPath("/sample1");

        // 現在のリクエストパス
        req.setRequestURI("/sample1/logon/start.do");

        // 前回のリクエストパス
        req.setAttribute("PREV_PATH_INFO", null);

        //テスト実行
        boolean result = RequestUtil.isChanged(req);

        //テスト結果確認
        // 業務が切り替わったと判断されること。
        assertTrue(result);
    }

    /**
     * testIsChanged05()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) request:not null<br>
     *         (状態) request.getRequestURI:"/sample1/service/logon/start.do"
     *         <br>
     *         (状態) request.getAttribute("PREV_PATH_INFO"):
     *         "/service/logout/end.do"<br>
     *
     * <br>
     * 期待値：(戻り値) boolean:falseが返却されること。<br>
     *
     * <br>
     * コンテキストルート以下に複数の階層があり、リクエストに保存された
     * 前回のパスと第一階層が一致する場合、falseが返却されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testIsChanged05() throws Exception {
        //テストデータ設定
        // 擬似リクエスト
        MockHttpServletRequest req = new MockHttpServletRequest();

        // 現在のコンテキストパス
        req.setContextPath("/sample1");

        // 現在のリクエストパス
        req.setRequestURI("/sample1/service/logon/start.do");

        // 前回のリクエストパス
        req.setAttribute("PREV_PATH_INFO", "/service/logout/end.do");

        //テスト実行
        boolean result = RequestUtil.isChanged(req);

        //テスト結果確認
        // 業務が切り替わったと判断されること。
        assertFalse(result);
    }

    /**
     * testIsChanged06()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) request:null<br>
     *
     * <br>
     * 期待値：(戻り値) boolean:trueが返却されること。<br>
     *
     * <br>
     * 引数のrequestがnullの場合、trueが取得できることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testIsChanged06() throws Exception {
        // テスト実施
        boolean result = RequestUtil.isChanged(null);

        // 判定
        assertTrue(result);
    }

    /**
     * testGetSessionHash01()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) req:not null<br>
     *         (状態) req.getSession():not null<br>
     *         (状態) session.getId():"dummyId"<br>
     *
     * <br>
     * 期待値：(戻り値) String:"95E301072238E16FD3FFB3D256D3EA930544C6D7"<br>
     *
     * <br>
     * セッションIDのハッシュ値が取得できることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetSessionHash01() throws Exception {
        //テストデータ設定

        // 擬似リクエスト
        MockHttpServletRequest req = new MockHttpServletRequest();
        // 擬似セッション
        MockHttpSession session = new MockHttpSession();
        req.setSession(session);

        //テスト実行
        String hash = RequestUtil.getSessionHash(req);
        //テスト結果確認
        assertEquals("95E301072238E16FD3FFB3D256D3EA930544C6D7", hash);
    }

    /**
     * testGetSessionHash02()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) req:null<br>
     *
     * <br>
     * 期待値：(戻り値) String:null<br>
     *
     * <br>
     * 引数のrequestがnullの場合、nullが取得できることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetSessionHash02() throws Exception {
        // テスト実施
        String result = RequestUtil.getSessionHash(null);

        // 判定
        assertNull(result);
    }

    /**
     * testDumpRequestAttributes01()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) req:null<br>
     *
     * <br>
     * 期待値：(戻り値) String:null<br>
     *
     * <br>
     * 引数のリクエストがnullの場合、<br>
     * null<br>
     * が返却されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testDumpRequestAttributes01() throws Exception {
        //テストデータ設定

        // 擬似リクエスト
        MockHttpServletRequest req = null;
        // 属性に何も登録しない

        //テスト実行
        String dump = RequestUtil.dumpRequestAttributes(req);
        //テスト結果確認
        assertNull(dump);
    }

    /**
     * testDumpRequestAttributes02()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) req:not null<br>
     *         (状態) request.getAttributeNames():要素なし<br>
     *
     * <br>
     * 期待値：(戻り値) String:" RequestAttributes {}"<br>
     *
     * <br>
     * 引数のリクエストから取得した属性に要素がない場合、<br>
     * " RequestAttributes {}"<br>
     * が返却されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testDumpRequestAttributes02() throws Exception {
        //テストデータ設定

        // 擬似リクエスト
        MockHttpServletRequest req = new MockHttpServletRequest();
        // 属性に何も登録しない

        //テスト実行
        String dump = RequestUtil.dumpRequestAttributes(req);
        //テスト結果確認
        assertEquals(" RequestAttributes {}", dump);
    }

    /**
     * testDumpRequestAttributes03()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) req:not null<br>
     *         (状態) request.getAttributeNames():attr0="value0"<br>
     *
     * <br>
     * 期待値：(戻り値) String:" RequestAttributes {attr0 = value0}"<br>
     *
     * <br>
     * 引数のリクエストから取得した属性に値がString型の要素が一つある場合、<br>
     * " RequestAttributes {<要素名> = <値>}"<br>
     * が返却されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testDumpRequestAttributes03() throws Exception {
        //テストデータ設定

        // 擬似リクエスト
        MockHttpServletRequest req = new MockHttpServletRequest();
        // 属性に登録する。
        req.setAttribute("attr0", "value0");
        //テスト実行
        String dump = RequestUtil.dumpRequestAttributes(req);
        //テスト結果確認
        assertEquals(" RequestAttributes {attr0 = value0}", dump);
    }

    /**
     * testDumpRequestAttributes04()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) req:not null<br>
     *         (状態) request.getAttributeNames():attr0=""<br>
     *
     * <br>
     * 期待値：(戻り値) String:" RequestAttributes {attr0 = }"<br>
     *
     * <br>
     * 引数のリクエストから取得した属性に要素が一つあり、値が空文字の場合、<br>
     * " RequestAttributes {<要素名> = }"<br>
     * が返却されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testDumpRequestAttributes04() throws Exception {
        //テストデータ設定

        // 擬似リクエスト
        MockHttpServletRequest req = new MockHttpServletRequest();
        // 属性に登録する。
        req.setAttribute("attr0", "");
        //テスト実行
        String dump = RequestUtil.dumpRequestAttributes(req);
        //テスト結果確認
        assertEquals(" RequestAttributes {attr0 = }", dump);
    }

    /**
     * testDumpRequestAttributes05()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：E
     * <br><br>
     * 入力値：(引数) req:not null<br>
     *         (状態) request.getAttributeNames():
     *                attr0=HashMap(={inner0="inner0"})<br>
     *
     * <br>
     * 期待値：(戻り値) String:" RequestAttributes {attr0 = {inner0=inner0}}"<br>
     *
     * <br>
     * 引数のリクエストから取得した属性に値がString型以外の要素が一つある場合、
     * " RequestAttributes {<要素名> = <値のtoStringの結果>}"<br>
     * が返却されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testDumpRequestAttributes05() throws Exception {
        //テストデータ設定

        // 擬似リクエスト
        MockHttpServletRequest req = new MockHttpServletRequest();
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("inner0", "inner0");
        // 属性に登録する。
        req.setAttribute("attr0", map);
        //テスト実行
        String dump = RequestUtil.dumpRequestAttributes(req);
        //テスト結果確認
        assertEquals(" RequestAttributes {attr0 = {inner0=inner0}}", dump);
    }

    /**
     * testDumpRequestAttributes06()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：B
     * <br><br>
     * 入力値：(引数) req:not null<br>
     *         (状態) request.getAttributeNames():attr0="value0"<br>
     *                attr1="value1"<br>
     *                attr2="value2"<br>
     *
     * <br>
     * 期待値：(戻り値) String:" RequestAttributes
     *                  {attr0 = value0 , attr1 = value1 , attr2 = value2}"<br>
     *
     * <br>
     * 引数のリクエストから取得した属性に複数の要素があるの場合、<br>
     * " RequestAttributes {<要素名> = <値>*要素数分}"<br>
     * が返却されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testDumpRequestAttributes06() throws Exception {
        //テストデータ設定

        // 擬似リクエスト
        MockHttpServletRequest req = new MockHttpServletRequest();
        // 属性に登録する。
        req.setAttribute("attr0", "value0");
        req.setAttribute("attr1", "value1");
        req.setAttribute("attr2", "value2");
        //テスト実行
        String dump = RequestUtil.dumpRequestAttributes(req);
        //テスト結果確認
        String expect = " RequestAttributes {attr1 = value1 , "
            + "attr2 = value2 , attr0 = value0}";
        assertEquals(expect, dump);
    }

    /**
     * testDumpRequestParameters01()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) req:null<br>
     *
     * <br>
     * 期待値：(戻り値) String:null<br>
     *
     * <br>
     * 引数のリクエストがnullの場合、<br>
     * null<br>
     * が返却されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testDumpRequestParameters01() throws Exception {
        //テストデータ設定

        // 擬似リクエスト
        MockHttpServletRequest req = null;
        // パラメータに登録しない
        //テスト実行
        String dump = RequestUtil.dumpRequestParameters(req);
        //テスト結果確認
        assertNull(dump);
    }

    /**
     * testDumpRequestParameters02()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) req:not null<br>
     *         (状態) req.getParameterNames():{} (要素なし:0件)<br>
     *
     * <br>
     * 期待値：(戻り値) String:" RequestParameters {}"<br>
     *
     * <br>
     * 引数のリクエストから取得したパラメータ属性に要素がない場合、
     * " RequestAttributes {}"が返却されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testDumpRequestParameters02() throws Exception {
        //テストデータ設定

        // 擬似リクエスト
        MockHttpServletRequest req = new MockHttpServletRequest();

        //テスト実行
        String dump = RequestUtil.dumpRequestParameters(req);

        //テスト結果確認
        assertEquals(" RequestParameters {}", dump);
    }

    /**
     * testDumpRequestParameters03()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) req:not null<br>
     *         (状態) req.getParameterNames():{"param"}（1件）<br>
     *         (状態) parameterの内容:param[] = {null}(要素null:1件)<br>
     *
     * <br>
     * 期待値：(戻り値) String:" RequestParameters {}"<br>
     *
     * <br>
     * 引数のリクエストから取得したパラメータ属性に要素が1件存在し、
     * 配列の要素が1件のnullである場合、
     * " RequestAttributes {}"が返却されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testDumpRequestParameters03() throws Exception {
        //テストデータ設定

        // 擬似リクエスト
        MockHttpServletRequest req = new MockHttpServletRequest();
        String value = null;
        // パラメータに登録する
        req.setParameter("param", value);
        //テスト実行
        String dump = RequestUtil.dumpRequestParameters(req);
        //テスト結果確認
        assertEquals(" RequestParameters {}", dump);
    }

    /**
     * testDumpRequestParameters04()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) req:not null<br>
     *         (状態) req.getParameterNames():{"param"}（1件）<br>
     *         (状態) parameterの内容:param[] = {""}(要素空:1件)<br>
     *
     * <br>
     * 期待値：(戻り値) String:" RequestParameters {param[0] = }"<br>
     *
     * <br>
     * 引数のリクエストから取得したパラメータ属性に要素が1件存在し、
     * 配列の要素が1件の空文字である場合、
     * "RequestAttributes{<パラメータ名>[0]=}"が返却されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testDumpRequestParameters04() throws Exception {
        //テストデータ設定

        // 擬似リクエスト
        MockHttpServletRequest req = new MockHttpServletRequest();
        String[] values = {""};
        // パラメータに登録する
        req.setParameter("param", values);
        //テスト実行
        String dump = RequestUtil.dumpRequestParameters(req);
        //テスト結果確認
        assertEquals(" RequestParameters {param[0] = }", dump);
    }

    /**
     * testDumpRequestParameters05()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) req:not null<br>
     *         (状態) req.getParameterNames():{"param"}（1件）<br>
     *         (状態) parameterの内容:param[] = {"param0"}(要素:1件)<br>
     *
     * <br>
     * 期待値：(戻り値) String:"RequestParameters{param[0]=param0}"<br>
     *
     * <br>
     * 引数のリクエストから取得したパラメータ属性に要素が1件存在し、
     * 配列の要素が1件である場合、
     * "RequestAttributes{<パラメータ名>[0]=<値>}"が返却されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testDumpRequestParameters05() throws Exception {
        //テストデータ設定

        // 擬似リクエスト
        MockHttpServletRequest req = new MockHttpServletRequest();
        String[] values = {"param0"};
        // パラメータに登録する
        req.setParameter("param", values);
        //テスト実行
        String dump = RequestUtil.dumpRequestParameters(req);
        //テスト結果確認
        assertEquals(" RequestParameters {param[0] = param0}", dump);
    }

    /**
     * testDumpRequestParameters06()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) req:not null<br>
     *         (状態) req.getParameterNames():{"param"}（1件）<br>
     *         (状態) parameterの内容:param[]={"param0","param1","param2"}
     *         (要素:ｎ件)<br>
     *
     * <br>
     * 期待値：(戻り値) String:"RequestParameters{param[0]=param0,
     *                                            param[1] = param1,
     *                                            param[2]=param2}"<br>
     *
     * <br>
     * 引数のリクエストから取得したパラメータ属性に要素が1件存在し、
     * 配列の要素が複数件である場合、
     * "RequestAttributes{<パラメータ名>[n]=<値>*要素数分}"が返却されることを
     * 確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testDumpRequestParameters06() throws Exception {
        //テストデータ設定

        // 擬似リクエスト
        MockHttpServletRequest req = new MockHttpServletRequest();
        String[] values = {"param0", "param1", "param2"};
        // パラメータに登録する
        req.setParameter("param", values);
        //テスト実行
        String dump = RequestUtil.dumpRequestParameters(req);
        //テスト結果確認
        assertEquals(" RequestParameters {param[0] = param0 , param[1] = "
                + "param1 , param[2] = param2}", dump);
    }

    /**
     * testDumpRequestParameters07()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：D
     * <br><br>
     * 入力値：(引数) req:not null<br>
     *         (状態) req.getParameterNames():{"param0","param1"}(n件)<br>
     *         (状態) parameterの内容:param0[]={"param0","param1","param2"}<br>
     *                param1[]={"value0","param1","param2"}<br>
     *                (要素:n件)<br>
     *
     * <br>
     * 期待値：(戻り値) String:"RequestParameters{<br>
     *                  param0[0]=param0 , param0[1]=param1 , param0[2]=param2,
     *                  param1[0]=value0 , param1[1]=value1 , param1[2]=value2
     *                  }"<br>
     *
     * <br>
     * 引数のリクエストから取得したパラメータ属性に要素が複数件存在し、
     * 配列の要素が複数件である場合、
     * "RequestAttributes{<パラメータ名>[n]=<値>*要素数分*パラメータ要素数分}"
     * が返却されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testDumpRequestParameters07() throws Exception {
        //テストデータ設定

        // 擬似リクエスト
        MockHttpServletRequest req = new MockHttpServletRequest();
        String[] values  = {"param0", "param1", "param2"};
        String[] values2 = {"value0", "value1", "value2"};
        // パラメータに登録する
        req.setParameter("param0", values);
        req.setParameter("param1", values2);
        //テスト実行
        String dump = RequestUtil.dumpRequestParameters(req);
        //テスト結果確認
        assertEquals(" RequestParameters {"
          + "param0[0] = param0 , param0[1] = param1 , param0[2] = param2 , "
          + "param1[0] = value0 , param1[1] = value1 , param1[2] = value2}"
          , dump);
    }

    /**
     * testDumpRequest01()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) req:not null<br>
     *         (状態) parameter属性:param = {"param0"}<br>
     *         (状態) attribute属性:attr0="value0"<br>
     *
     * <br>
     * 期待値：(戻り値) String:" RequestParameters = {param[0]=param0}" ,
     *                         " RequestAttributes{attr0=value0} "<br>
     *                  (一行)<br>
     *
     * <br>
     * dumpRequestParametersとdumpRequestAttributesの結果がカンマ区切りで
     * 取得できること。<br>
     * ※正常系1件のみ確認
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testDumpRequest01() throws Exception {
        //テストデータ設定

        // 擬似リクエスト
        MockHttpServletRequest req = new MockHttpServletRequest();
        String[] values = {"param0"};
        // パラメータに登録する
        req.setParameter("param", values);
        req.setAttribute("attr0", "value0");
        //テスト実行
        String dump = RequestUtil.dumpRequest(req);
        //テスト結果確認
        assertEquals(" RequestParameters {param[0] = param0} ,  "
                + "RequestAttributes {attr0 = value0}", dump);
    }

    /**
     * testDeleteUrlParam01()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) url:null<br>
     *         (引数) key:null<br>
     *
     * <br>
     * 期待値：(戻り値) String:null<br>
     *
     * <br>
     * urlがnullの場合はnullがそのまま返却されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testDeleteUrlParam01() throws Exception {
        // 前処理
        String url = null;
        String key = null;

        // テスト実施
        String value = RequestUtil.deleteUrlParam(url, key);

        // 判定
        assertNull(value);
    }

    /**
     * testDeleteUrlParam02()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) url:""<br>
     *         (引数) key:""<br>
     *
     * <br>
     * 期待値：(戻り値) String:""<br>
     *
     * <br>
     * urlが空白の場合は空白がそのまま返却されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testDeleteUrlParam02() throws Exception {
        // 前処理
        String url = "";
        String key = "";

        // テスト実施
        String value = RequestUtil.deleteUrlParam(url, key);

        // 判定
        assertEquals("", value);
    }

    /**
     * testDeleteUrlParam03()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) url:"/test.do"<br>
     *         (引数) key:null<br>
     *
     * <br>
     * 期待値：(戻り値) String:"/test.do"<br>
     *
     * <br>
     * keyがnullの場合は、urlがそのまま返却されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testDeleteUrlParam03() throws Exception {
        // 前処理
        String url = "/test.do";
        String key = null;

        // テスト実施
        String value = RequestUtil.deleteUrlParam(url, key);

        // 判定
        assertEquals("/test.do", value);
    }

    /**
     * testDeleteUrlParam04()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) url:"/test.do"<br>
     *         (引数) key:""<br>
     *
     * <br>
     * 期待値：(戻り値) String:"/test.do"<br>
     *
     * <br>
     * keyが空白の場合は、urlがそのまま返却されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testDeleteUrlParam04() throws Exception {
        // 前処理
        String url = "/test.do";
        String key = "";

        // テスト実施
        String value = RequestUtil.deleteUrlParam(url, key);

        // 判定
        assertEquals("/test.do", value);
    }

    /**
     * testDeleteUrlParam05()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) url:"/test.do"<br>
     *         (引数) key:"ｒ"<br>
     *
     * <br>
     * 期待値：(戻り値) String:"/test.do"<br>
     *
     * <br>
     * urlに、パラメータが存在しない場合は、urlがそのまま返却されることを
     * 確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testDeleteUrlParam05() throws Exception {
        // 前処理
        String url = "/test.do";
        String key = "r";

        // テスト実施
        String value = RequestUtil.deleteUrlParam(url, key);

        // 判定
        assertEquals("/test.do", value);
    }

    /**
     * testDeleteUrlParam06()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) url:"/test.do?no2=2&no1=1"<br>
     *         (引数) key:"ｒ"<br>
     *
     * <br>
     * 期待値：(戻り値) String:"/test.do?no2=2&no1=1"<br>
     *
     * <br>
     * urlに、削除対象のパラメータが存在しない場合は、削除されずに、urlが
     * 返却されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testDeleteUrlParam06() throws Exception {
        // 前処理
        String url = "/test.do?no2=2&no1=1";
        String key = "r";

        // テスト実施
        String value = RequestUtil.deleteUrlParam(url, key);

        // 判定
        assertEquals("/test.do?no2=2&no1=1", value);
    }

    /**
     * testDeleteUrlParam07()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) url:"/test.do?r=8331352040"<br>
     *         (引数) key:"ｒ"<br>
     *
     * <br>
     * 期待値：(戻り値) String:"/test.do"<br>
     *
     * <br>
     * urlに、削除対象のパラメータのみの場合、パラメータが削除されたurlが
     * 返却されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testDeleteUrlParam07() throws Exception {
        // 前処理
        String url = "/test.do?r=8331352040";
        String key = "r";

        // テスト実施
        String value = RequestUtil.deleteUrlParam(url, key);

        // 判定
        assertEquals("/test.do", value);
    }

    /**
     * testDeleteUrlParam08()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) url:"/test.do?r=8331352040&no1=1"<br>
     *         (引数) key:"ｒ"<br>
     *
     * <br>
     * 期待値：(戻り値) String:"/test.do?no1=1"<br>
     *
     * <br>
     * urlに、削除対象のパラメータと、その後に別のパラメータが存在する場合、
     * 削除対象のパラメータだけ削除されたurlが返却されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testDeleteUrlParam08() throws Exception {
        // 前処理
        String url = "/test.do?r=8331352040&no1=1";
        String key = "r";

        // テスト実施
        String value = RequestUtil.deleteUrlParam(url, key);

        // 判定
        assertEquals("/test.do?no1=1", value);
    }

    /**
     * testDeleteUrlParam09()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) url:"/test.do?no2=2&r=8331352040"<br>
     *         (引数) key:"ｒ"<br>
     *
     * <br>
     * 期待値：(戻り値) String:"/test.do?no2=2"<br>
     *
     * <br>
     * urlに、削除対象のパラメータと、その前に別のパラメータが存在する場合、
     * 削除対象のパラメータだけ削除されたurlが返却されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testDeleteUrlParam09() throws Exception {
        // 前処理
        String url = "/test.do?no2=2&r=8331352040";
        String key = "r";

        // テスト実施
        String value = RequestUtil.deleteUrlParam(url, key);

        // 判定
        assertEquals("/test.do?no2=2", value);
    }

    /**
     * testDeleteUrlParam10()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) url:"/test.do?no2=2&r=8331352040&no1=1"<br>
     *         (引数) key:"ｒ"<br>
     *
     * <br>
     * 期待値：(戻り値) String:"/test.do?no2=2&no1=1"<br>
     *
     * <br>
     * urlに、削除対象のパラメータと、その前後に別のパラメータが存在する場合、
     * 削除対象のパラメータだけ削除されたurlが返却されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testDeleteUrlParam10() throws Exception {
        // 前処理
        String url = "/test.do?no2=2&r=8331352040&no1=1";
        String key = "r";

        // テスト実施
        String value = RequestUtil.deleteUrlParam(url, key);

        // 判定
        assertEquals("/test.do?no2=2&no1=1", value);
    }

    /**
     * testDeleteUrlParam11()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) url:"/test.do?r=9856456131&r=8331352040&no1=1"<br>
     *         (引数) key:"ｒ"<br>
     *
     * <br>
     * 期待値：(戻り値) String:"/test.do?no1=1"<br>
     *
     * <br>
     * urlに、削除対象のパラメータが複数存在する場合、対象となるパラメータが
     * すべて削除されたurlが返却されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testDeleteUrlParam11() throws Exception {
        // 前処理
        String url = "/test.do?r=9856456131&r=8331352040&no1=1";
        String key = "r";

        // テスト実施
        String value = RequestUtil.deleteUrlParam(url, key);

        // 判定
        assertEquals("/test.do?no1=1", value);
    }

    /**
     * testDeleteUrlParam12()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) url:"/test.do?r=8331352040"<br>
     *         (引数) key:"s"<br>
     *
     * <br>
     * 期待値：(戻り値) String:"/test.do?r=8331352040"<br>
     *
     * <br>
     * 削除対象のパラメータと引数keyが違う場合、パラメータが削除されずに
     * urlが返却されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testDeleteUrlParam12() throws Exception {
        // 前処理
        String url = "/test.do?r=8331352040";
        String key = "s";

        // テスト実施
        String value = RequestUtil.deleteUrlParam(url, key);

        // 判定
        assertEquals(url, value);
    }

}
