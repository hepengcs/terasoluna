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

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import jp.terasoluna.fw.oxm.mapper.OXMapper;
import jp.terasoluna.utlib.LogUTUtil;
import jp.terasoluna.utlib.MockHttpServletRequest;
import jp.terasoluna.utlib.MockHttpServletResponse;
import jp.terasoluna.utlib.UTUtil;
import jp.terasoluna.fw.web.rich.springmvc.Constants;
import jp.terasoluna.fw.web.rich.springmvc.servlet.view.castor.CastorView;
import junit.framework.TestCase;

/**
 * {@link jp.terasoluna.fw.web.rich.springmvc.servlet.view.castor.CastorView} クラスのブラックボックステスト。
 * 
 * <p>
 * <h4>【クラスの概要】</h4>
 * Castorを利用してHTTPレスポンス生成を行うクラス。<br>
 * 前提条件：model、HTTPリクエスト、HTTPレスポンス、oxmapperはNull値にならない。
 * <p>
 * 
 * @see jp.terasoluna.fw.web.rich.springmvc.servlet.view.castor.
 * CastorView
 */
public class CastorViewTest extends TestCase {

    /**
     * このテストケースを実行する為の
     * GUI アプリケーションを起動する。
     * 
     * @param args java コマンドに設定されたパラメータ
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(CastorViewTest.class);
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
    public CastorViewTest(String name) {
        super(name);
    }
    
    /**
     * testRenderMergedOutputModel01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) model:not null<br>
     *         (引数) request:not null<br>
     *         (引数) response:not null<br>
     *         (状態) this.getContentType():null<br>
     *         (状態) response.getWriter():出力ストリームを返す。<br>
     *         (状態) responseWriter.close():正常にクローズする。<br>
     *         
     * <br>
     * 期待値：(状態変化) response:oxmapper.marshal()の結果が書き込まれる。
     * コンテンツタイプが設定されない。<br>
     *         (状態変化) oxmapper.marshal（）:呼び出されたことを確認する。<br>
     *         (状態変化) response.getWriter():呼び出されたことを確認する。<br>
     *         (状態変化) responseWriter.write():呼び出されたことを確認する。<br>
     *         (状態変化) responseWriter.close():呼び出されたことを確認する。<br>
     *         
     * <br>
     * オブジェクトデータをXMLに変換し、HTTPレスポンスに設定するテスト。
     * コンテンツタイプをHTTPレスポンスに書き込まないパターン。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testRenderMergedOutputModel01() throws Exception {
        // 前処理
    	CastorViewStub01 view = new CastorViewStub01();
        UTUtil.setPrivateField(view, "contentType", null);
        CastorView_OXMapperImplStub01 oxmapper = 
            new CastorView_OXMapperImplStub01();
        UTUtil.setPrivateField(view, "oxmapper", oxmapper);
        
        MockHttpServletRequest request = new MockHttpServletRequest();
        CastorView_MockHttpServletResponseStub02 response = 
            new CastorView_MockHttpServletResponseStub02();
        CastorView_PrintWriterStub01 printWriter = 
            new CastorView_PrintWriterStub01(System.out);
        response.setWriter(printWriter);
        Map<String, String> model = new HashMap<String, String>();
        model.put(Constants.RESULT_KEY, "test");

        // テスト実施
        view.renderMergedOutputModel(model, request, response);

        // 判定
        assertTrue(oxmapper.isMarshal);
        Object resultContentType = 
            UTUtil.getPrivateField(response, "contentType");
        assertEquals(null, resultContentType);
        assertTrue(response.isGetWriter);
        assertTrue(printWriter.isWrite);
        assertTrue(printWriter.isClose);
        
        // addHeader()の呼び出し確認
        assertEquals(model, view.modelData);
        assertEquals(request, view.requestData);
        assertEquals(response, view.responseData);
    }

    /**
     * testRenderMergedOutputModel02()
     * <br><br>
     * 
     * (正常系) 
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) model:not null<br>
     *         (引数) request:not null<br>
     *         (引数) response:not null<br>
     *         (状態) this.getContentType():not null<br>
     *         (状態) response.getWriter():出力ストリームを返す。<br>
     *         (状態) responseWriter.close():正常にクローズする。<br>
     *         
     * <br>
     * 期待値：(状態変化) response:oxmapper.marshal()の結果が書き込まれる。
     * コンテンツタイプが設定される。<br>
     *         (状態変化) oxmapper.marshal（）:呼び出されたことを確認する。<br>
     *         (状態変化) response.getWriter():呼び出されたことを確認する。<br>
     *         (状態変化) responseWriter.write():呼び出されたことを確認する。<br>
     *         (状態変化) responseWriter.close():呼び出されたことを確認する。<br>
     *         
     * <br>
     * オブジェクトデータをXMLに変換し、HTTPレスポンスに設定するテスト。
     * コンテンツタイプをHTTPレスポンスに書き込むパターン。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testRenderMergedOutputModel02() throws Exception {
        // 前処理
    	CastorViewStub01 view = new CastorViewStub01();
        String contentType = "text/xml;charset=UTF-8";
        UTUtil.setPrivateField(view, "contentType", contentType);
        CastorView_OXMapperImplStub01 oxmapper = 
            new CastorView_OXMapperImplStub01();
        UTUtil.setPrivateField(view, "oxmapper", oxmapper);
        
        MockHttpServletRequest request = new MockHttpServletRequest();
        CastorView_MockHttpServletResponseStub02 response = 
            new CastorView_MockHttpServletResponseStub02();
        CastorView_PrintWriterStub01 printWriter = 
            new CastorView_PrintWriterStub01(System.out);
        response.setWriter(printWriter);
        Map<String, String> model = new HashMap<String, String>();
        model.put(Constants.RESULT_KEY, "test");

        // テスト実施
        view.renderMergedOutputModel(model, request, response);

        // 判定
        assertTrue(oxmapper.isMarshal);
        Object resultContentType = 
            UTUtil.getPrivateField(response, "contentType");
        assertEquals(contentType, resultContentType);
        assertTrue(response.isGetWriter);
        assertTrue(printWriter.isWrite);
        assertTrue(printWriter.isClose);
        
        // addHeader()の呼び出し確認
        assertEquals(model, view.modelData);
        assertEquals(request, view.requestData);
        assertEquals(response, view.responseData);
    }

    /**
     * testRenderMergedOutputModel03()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) model:not null<br>
     *         (引数) request:not null<br>
     *         (引数) response:not null<br>
     *         (状態) this.getContentType():not null<br>
     *         (状態) response.getWriter():IO例外を返す。<br>
     *         
     * <br>
     * 期待値：(状態変化) 例外:IOException<br>
     *         (状態変化) ログ:Cannot get Response Writer object.<br>
     *         
     * <br>
     * 出力ストリーム取得時にIO例外が発生するパターンのテスト。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testRenderMergedOutputModel03() throws Exception {
        // 前処理
        CastorView view = new CastorView();
        String contentType = "text/xml;charset=UTF-8";
        UTUtil.setPrivateField(view, "contentType", contentType);
        CastorView_OXMapperImplStub01 oxmapper = 
            new CastorView_OXMapperImplStub01();
        UTUtil.setPrivateField(view, "oxmapper", oxmapper);
        MockHttpServletResponse response = 
            new CastorView_MockHttpServletResponseStub01();
        response.setWriter(new PrintWriter(System.out));
        Map<String, String> model = new HashMap<String, String>();
        model.put(Constants.RESULT_KEY, "test");

        // テスト実施
        try {
            view.renderMergedOutputModel(model, null, response);
            fail();
        } catch (IOException e) {
            // 結果確認
            assertTrue(LogUTUtil.checkError(
                    "Cannot get Response Writer object."));
        }
    }

    /**
     * testRenderMergedOutputModel04()
     * <br><br>
     * 
     * (正常系) 
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) model:not null<br>
     *         (引数) request:not null<br>
     *         (引数) response:not null<br>
     *         (状態) this.getContentType():not null<br>
     *         (状態) response.getWriter():Nullを返す。<br>
     *         
     * <br>
     * 期待値：(状態変化) oxmapper.marshal（）:呼び出されたことを確認する。<br>
     *         (状態変化) 例外:NullPointerException<br>
     *         
     * <br>
     * 出力ストリームがNullの場合、closeメソッドが呼ばれないことを確認するテスト。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testRenderMergedOutputModel04() throws Exception {
        // 前処理
        CastorView view = new CastorView();
        String contentType = "text/xml;charset=UTF-8";
        UTUtil.setPrivateField(view, "contentType", contentType);
        CastorView_OXMapperImplStub01 oxmapper = 
            new CastorView_OXMapperImplStub01();
        UTUtil.setPrivateField(view, "oxmapper", oxmapper);
        CastorView_MockHttpServletResponseStub02 response = 
            new CastorView_MockHttpServletResponseStub02();
        response.setWriter(null);
        Map<String, String> model = new HashMap<String, String>();
        model.put(Constants.RESULT_KEY, "test");

        // テスト実施
        try {
            view.renderMergedOutputModel(model, null, response);
            fail();
        } catch (NullPointerException e) {
            // OK
        	return;
        }

        // 判定
        assertTrue(oxmapper.isMarshal);
        Object resultContentType = 
            UTUtil.getPrivateField(response, "contentType");
        assertEquals("text/xml;charset=UTF-8", resultContentType);
        assertTrue(response.isGetWriter);
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
     * 期待値：(戻り値) OXMapper:this.oxmapper<br>
     *         
     * <br>
     * oxmapper属性のgetterメソッドのテスト。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetOxmapper01() throws Exception {
        // 前処理
        CastorView view = new CastorView();
        OXMapper oxmapper = new CastorView_OXMapperImplStub01();
        UTUtil.setPrivateField(view, "oxmapper", oxmapper);

        // テスト実施
        Object result = view.getOxmapper();

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
        CastorView view = new CastorView();
        UTUtil.setPrivateField(view, "oxmapper", null);

        // テスト実施
        OXMapper oxmapper = new CastorView_OXMapperImplStub01();
        view.setOxmapper(oxmapper);

        // 判定
        Object result = UTUtil.getPrivateField(view, "oxmapper");
        assertSame(oxmapper, result);
    }
}
