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

package jp.terasoluna.fw.service.thin;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jp.terasoluna.fw.exception.SystemException;
import jp.terasoluna.fw.util.PropertyAccessException;
import jp.terasoluna.fw.web.struts.form.DynaValidatorActionFormEx;
import jp.terasoluna.fw.web.struts.form.FormEx;
import jp.terasoluna.utlib.DynaActionFormCreator;
import jp.terasoluna.utlib.LogUTUtil;
import jp.terasoluna.utlib.MockHttpServletResponse;
import jp.terasoluna.utlib.MockHttpSession;
import junit.framework.TestCase;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionMapping;

import com.mockrunner.mock.web.MockHttpServletRequest;

/**
 * {@link jp.terasoluna.fw.service.thin.BLogicMapper} クラスの
 * ブラックボックステスト。
 *
 * <p>
 * <h4>【クラスの概要】</h4>
 * ビジネスロジック入出力情報反映クラス。<br>
 * BLogicIOPlugInによって生成されたBLogicResourcesをもとに、
 * Web層のオブジェクトと、ビジネスロジック間のデータのマッピングを行う。
 * <p>
 *
 * @see jp.terasoluna.fw.service.thin.BLogicMapper
 */
@SuppressWarnings("unused")
public class BLogicMapperTest extends TestCase {

    /**
     * ファイルパスをプロパティから取得
     */
    private static final String CONFIG_FILE_NAME = BLogicMapperTest.class
            .getResource("BLogicMapperTest.xml").getPath();

    private static final String RULES_FILE_NAME = BLogicMapperTest.class
            .getResource("BLogicMapperTest-rules.xml").getPath();

    private final DynaActionFormCreator creator;

    private DynaValidatorActionFormEx formEx = null;
    
    private String resource = "resource.path";

    /**
     * 初期化処理を行う。
     *
     * @throws Exception このメソッドで発生した例外
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        this.formEx =
            (DynaValidatorActionFormEx) this.creator.create(CONFIG_FILE_NAME);
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
    public BLogicMapperTest(String name) {
        super(name);

        this.creator = new DynaActionFormCreator(RULES_FILE_NAME);
    }

    /**
     * testBLogicMapper01()
     * <br><br>
     * 
     * (異常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) resources:null<br>
     *         
     * <br>
     * 期待値：(状態変化) 例外:IllegalArgumentException：<br>
     *         (状態変化) ログ:＜ログ＞<br>
     *                    エラーログ：<br>
     *                    "resources file location is not specified"<br>
     *         
     * <br>
     * コンストラクタが引数null値の場合、例外が発生することを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testBLogicMapper01() throws Exception {
        
        // テスト実施
        try {
            new BLogicMapper(null);
            fail();
        } catch (IllegalArgumentException e) {
            // 判定
            assertEquals(IllegalArgumentException.class.getName(),
                    e.getClass().getName());
            assertTrue(LogUTUtil.checkError("resources file location is " +
                    "not specified"));
        }
        
    }

    /**
     * testBLogicMapper02()
     * <br><br>
     * 
     * (異常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) resources:""<br>
     *         
     * <br>
     * 期待値：(状態変化) 例外:IllegalArgumentException：<br>
     *         (状態変化) ログ:＜ログ＞<br>
     *                    エラーログ：<br>
     *                    "resources file location is not specified"<br>
     *         
     * <br>
     * コンストラクタが引数空値の場合、例外が発生することを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testBLogicMapper02() throws Exception {
        // テスト実施
        try {
            new BLogicMapper("");
            fail();
        } catch (IllegalArgumentException e) {
            // 判定
            assertEquals(IllegalArgumentException.class.getName(),
                    e.getClass().getName());
            assertTrue(LogUTUtil.checkError("resources file location is " +
                    "not specified"));
        }
        
    }

    /**
     * testBLogicMapper03()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) resources:"resource.path"<br>
     *         
     * <br>
     * 期待値：(戻り値) blogicMapper:not Null
     * <br>
     * コンストラクタの引数が存在する場合、インスタンスが生成されることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testBLogicMapper03() throws Exception {
        // テスト実施
        BLogicMapper blogicMapper = new BLogicMapper(resource);
        
        // 判定
        assertNotNull(blogicMapper);
    }

    /**
     * testGetValueFromRequest01()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) propName:"requestValue"<br>
     *         (引数) request:[requestValue="requestValue"]<br>
     *         (引数) response:not null<br>
     *
     * <br>
     * 期待値：(戻り値) Object:"requestValue"<br>
     *         (状態変化) 例外:－<br>
     *         (状態変化) ログ:－<br>
     *
     * <br>
     * 引数のpropNameをキーにrequestに値が格納されていた時、取得した値を返却すること。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetValueFromRequest01() throws Exception {
        // 擬似リクエスト
        HttpServletRequest request = new BLogicMapper_MockHttpServletRequest01();

        request.setAttribute("requestValue", "requestValue");

        // 擬似レスポンス
        HttpServletResponse response = new MockHttpServletResponse();

        // BLogicMapper生成
        BLogicMapper mapper = new BLogicMapper(resource);

        // テスト実施・判定
        assertEquals("requestValue", mapper.getValueFromRequest("requestValue",
                request, response));
    }

    /**
     * testGetValueFromRequest02()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) propName:"value"<br>
     *         (引数) request:[requestValue="requestValue"]<br>
     *         (引数) response:not null<br>
     *
     * <br>
     * 期待値：(戻り値) Object:null<br>
     *         (状態変化) 例外:－<br>
     *         (状態変化) ログ:－<br>
     *
     * <br>
     * 引数のpropNameをキーにrequestに値が格納されていない時、nullを返却すること。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetValueFromRequest02() throws Exception {
        // 擬似リクエスト
        HttpServletRequest request = new BLogicMapper_MockHttpServletRequest01();
        request.setAttribute("requestValue", "requestValue");

        // 擬似レスポンス
        HttpServletResponse response = new MockHttpServletResponse();

        // BLogicMapper生成
        BLogicMapper mapper = new BLogicMapper(resource);

        // テスト実施・判定
        assertNull(mapper.getValueFromRequest("value", request, response));
    }

    /**
     * testGetValueFromRequest03()
     * <br><br>
     *
     * (異常系)
     * <br>
     * 観点：CG
     * <br><br>
     * 入力値：(引数) propName:""<br>
     *         (引数) request:not null<br>
     *         (引数) response:not null<br>
     *
     * <br>
     * 期待値：(戻り値) Object:－<br>
     *         (状態変化) 例外:IllegalArgumentException<br>
     *         (状態変化) ログ:＜ログ＞<br>
     *                    エラーログ：<br>
     *                    "illegal argument:propName = []"<br>
     *
     * <br>
     * 引数のpropNameが空文字の時、例外が発生すること。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetValueFromRequest03() throws Exception {
        // 擬似リクエスト
        HttpServletRequest request = new BLogicMapper_MockHttpServletRequest01();

        // 擬似レスポンス
        HttpServletResponse response = new MockHttpServletResponse();

        // BLogicMapper生成
        BLogicMapper mapper = new BLogicMapper(resource);

        // テスト実行
        try {
            Object result = mapper.getValueFromRequest("", request, response);
            fail();
        } catch (IllegalArgumentException e) {
            // テスト結果確認
            assertTrue(LogUTUtil.checkError("illegal argument:propName = []"));
        }
    }

    /**
     * testGetValueFromRequest04()
     * <br><br>
     *
     * (異常系)
     * <br>
     * 観点：CG
     * <br><br>
     * 入力値：(引数) propName:null<br>
     *         (引数) request:not null<br>
     *         (引数) response:not null<br>
     *
     * <br>
     * 期待値：(戻り値) Object:－<br>
     *         (状態変化) 例外:IllegalArgumentException<br>
     *         (状態変化) ログ:＜ログ＞<br>
     *                    エラーログ：<br>
     *                    "illegal argument:propName = [null]"<br>
     *
     * <br>
     * 引数のpropNameがnullの時、例外が発生すること。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetValueFromRequest04() throws Exception {
        // 擬似リクエスト
        HttpServletRequest request = new BLogicMapper_MockHttpServletRequest01();

        // 擬似レスポンス
        HttpServletResponse response = new MockHttpServletResponse();

        // BLogicMapper生成
        BLogicMapper mapper = new BLogicMapper(resource);

        // テスト実行
        try {
            Object result = mapper.getValueFromRequest(null, request, response);
            fail();
        } catch (IllegalArgumentException e) {
            // テスト結果確認
            assertTrue(LogUTUtil
                    .checkError("illegal argument:propName = [null]"));
        }
    }

    /**
     * testSetValueToRequest01()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) value:"value"<br>
     *         (引数) propName:"requestValue"<br>
     *         (引数) request:[requestValue="requestValue"]<br>
     *         (引数) response:not null<br>
     *
     * <br>
     * 期待値：(戻り値) void:－<br>
     *         (状態変化) 例外:－<br>
     *         (状態変化) request:[requestValue="value"]<br>
     *
     * <br>
     * 引数valueの値がrequestに反映されること。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetValueToRequest01() throws Exception {
        // 擬似リクエスト
        HttpServletRequest request = new BLogicMapper_MockHttpServletRequest01();
        request.setAttribute("requestValue", "requestValue");

        // 擬似レスポンス
        HttpServletResponse response = new MockHttpServletResponse();

        // BLogicMapper生成
        BLogicMapper mapper = new BLogicMapper(resource);

        // テスト実施
        mapper.setValueToRequest("value", "requestValue", request, response);

        // 判定
        assertEquals("value", request.getAttribute("requestValue"));
    }

    /**
     * testSetValueToRequest02()
     * <br><br>
     *
     * (異常系)
     * <br>
     * 観点：CG
     * <br><br>
     * 入力値：(引数) value:"value"<br>
     *         (引数) propName:""<br>
     *         (引数) request:not null<br>
     *         (引数) response:not null<br>
     *
     * <br>
     * 期待値：(戻り値) void:－<br>
     *         (状態変化) 例外:IllegalArgumentException<br>
     *         (状態変化) ログ:＜ログ＞<br>
     *                    エラーログ：<br>
     *                    "illegal argument:propName = []"<br>
     *         (状態変化) request:－<br>
     *
     * <br>
     * 引数propNameの値が空文字の時、例外が発生すること。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetValueToRequest02() throws Exception {
        // 擬似リクエスト
        HttpServletRequest request = new BLogicMapper_MockHttpServletRequest01();

        // 擬似レスポンス
        HttpServletResponse response = new MockHttpServletResponse();

        // BLogicMapper生成
        BLogicMapper mapper = new BLogicMapper(resource);

        // テスト実施・判定
        try {
            mapper.setValueToRequest("value", "", request, response);
            fail();
        } catch (IllegalArgumentException e) {
            // テスト結果確認
            assertTrue(LogUTUtil.checkError("illegal argument:propName = []"));
        }
    }

    /**
     * testSetValueToRequest03()
     * <br><br>
     *
     * (異常系)
     * <br>
     * 観点：CG
     * <br><br>
     * 入力値：(引数) value:"value"<br>
     *         (引数) propName:null<br>
     *         (引数) request:not null<br>
     *         (引数) response:not null<br>
     *
     * <br>
     * 期待値：(戻り値) void:－<br>
     *         (状態変化) 例外:IllegalArgumentException<br>
     *         (状態変化) ログ:＜ログ＞<br>
     *                    エラーログ：<br>
     *                    "illegal argument:propName = [null]"<br>
     *         (状態変化) request:－<br>
     *
     * <br>
     * 引数propNameの値がnullの時、例外が発生すること。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetValueToRequest03() throws Exception {
        // 擬似リクエスト
        HttpServletRequest request = new BLogicMapper_MockHttpServletRequest01();

        // 擬似レスポンス
        HttpServletResponse response = new MockHttpServletResponse();

        // BLogicMapper生成
        BLogicMapper mapper = new BLogicMapper(resource);

        // テスト実施・判定
        try {
            mapper.setValueToRequest("value", null, request, response);
            fail();
        } catch (IllegalArgumentException e) {
            // テスト結果確認
            assertTrue(LogUTUtil
                    .checkError("illegal argument:propName = [null]"));
        }
    }

    /**
     * testSetValueToRequest04()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) value:null<br>
     *         (引数) propName:"requestValue"<br>
     *         (引数) request:[requestValue="requestValue"]<br>
     *         (引数) response:not null<br>
     *
     * <br>
     * 期待値：(戻り値) void:－<br>
     *         (状態変化) 例外:－<br>
     *         (状態変化) request:requestValueが削除される<br>
     *
     * <br>
     * valueがnullの時、requestに登録されていたAttributeが削除されること。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetValueToRequest04() throws Exception {
        // 擬似リクエスト
        HttpServletRequest request = new BLogicMapper_MockHttpServletRequest01();
        request.setAttribute("requestValue", "requestValue");

        // 擬似レスポンス
        HttpServletResponse response = new MockHttpServletResponse();

        // BLogicMapper生成
        BLogicMapper mapper = new BLogicMapper(resource);

        // テスト実施
        mapper.setValueToRequest(null, "requestValue", request, response);

        // 判定
        assertFalse(request.getAttributeNames().hasMoreElements());
    }

    /**
     * testSetValueToRequest05()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) value:"value"<br>
     *         (引数) propName:"requestValue"<br>
     *         (引数) request:requestValue設定なし<br>
     *         (引数) response:not null<br>
     *
     * <br>
     * 期待値：(戻り値) void:－<br>
     *         (状態変化) 例外:－<br>
     *         (状態変化) request:[requestValue="value"]<br>
     *
     * <br>
     * 引数valueの値がrequestに反映されること。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetValueToRequest05() throws Exception {
        // 擬似リクエスト
        HttpServletRequest request = new BLogicMapper_MockHttpServletRequest01();

        // 擬似レスポンス
        HttpServletResponse response = new MockHttpServletResponse();

        // BLogicMapper生成
        BLogicMapper mapper = new BLogicMapper(resource);

        // テスト実施
        mapper.setValueToRequest("value", "requestValue", request, response);

        // 判定
        assertEquals("value", request.getAttribute("requestValue"));
    }

    /**
     * testGetValueFromSession01()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) propName:"sessionValue"<br>
     *         (引数) request:not null<br>
     *         (引数) response:not null<br>
     *         (状態) session（request.getSession()）:[sessionValue="sessionValue"]<br>
     *
     * <br>
     * 期待値：(戻り値) Object:"sessionValue"<br>
     *         (状態変化) 例外:－<br>
     *         (状態変化) ログ:－<br>
     *
     * <br>
     * 引数のpropNameをキーにsessionに値が格納されていた時、取得した値を返却すること。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetValueFromSession01() throws Exception {
        // 擬似セッション
        HttpSession session = new MockHttpSession();
        session.setAttribute("sessionValue", "sessionValue");

        // 擬似リクエスト
        MockHttpServletRequest request = new BLogicMapper_MockHttpServletRequest01();
        request.setSession(session);

        // 擬似レスポンス
        HttpServletResponse response = new MockHttpServletResponse();

        // BLogicMapper生成
        BLogicMapper mapper = new BLogicMapper(resource);

        // テスト実施・判定
        assertEquals("sessionValue", mapper.getValueFromSession("sessionValue",
                request, response));
    }

    /**
     * testGetValueFromSession02()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) propName:"value"<br>
     *         (引数) request:not null<br>
     *         (引数) response:not null<br>
     *         (状態) session（request.getSession()）:[sessionValue="sessionValue"]<br>
     *
     * <br>
     * 期待値：(戻り値) Object:null<br>
     *         (状態変化) 例外:－<br>
     *         (状態変化) ログ:－<br>
     *
     * <br>
     * 引数のpropNameをキーにsessionに値が格納されていない時、nullを返却すること。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetValueFromSession02() throws Exception {
        // 擬似セッション
        HttpSession session = new MockHttpSession();
        session.setAttribute("sessionValue", "sessionValue");

        // 擬似リクエスト
        MockHttpServletRequest request = new BLogicMapper_MockHttpServletRequest01();
        request.setSession(session);

        // 擬似レスポンス
        HttpServletResponse response = new MockHttpServletResponse();

        // BLogicMapper生成
        BLogicMapper mapper = new BLogicMapper(resource);

        // テスト実施・判定
        assertNull(mapper.getValueFromSession("value", request, response));
    }

    /**
     * testGetValueFromSession03()
     * <br><br>
     *
     * (異常系)
     * <br>
     * 観点：CG
     * <br><br>
     * 入力値：(引数) propName:""<br>
     *         (引数) request:not null<br>
     *         (引数) response:not null<br>
     *         (状態) session（request.getSession()）:not null<br>
     *
     * <br>
     * 期待値：(戻り値) Object:－<br>
     *         (状態変化) 例外:IllegalArgumentException<br>
     *         (状態変化) ログ:＜ログ＞<br>
     *                    エラーログ：<br>
     *                    "illegal argument:propName = []"<br>
     *
     * <br>
     * 引数propNameの値が空文字の時、例外が発生すること。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetValueFromSession03() throws Exception {
        // 擬似セッション
        HttpSession session = new MockHttpSession();

        // 擬似リクエスト
        MockHttpServletRequest request = new BLogicMapper_MockHttpServletRequest01();
        request.setSession(session);

        // 擬似レスポンス
        HttpServletResponse response = new MockHttpServletResponse();

        // BLogicMapper生成
        BLogicMapper mapper = new BLogicMapper(resource);

        // テスト実施・判定
        try {
            Object result = mapper.getValueFromSession("", request, response);
            fail();
        } catch (IllegalArgumentException e) {
            // テスト結果確認
            assertTrue(LogUTUtil.checkError("illegal argument:propName = []"));
        }
    }

    /**
     * testGetValueFromSession04()
     * <br><br>
     *
     * (異常系)
     * <br>
     * 観点：CG
     * <br><br>
     * 入力値：(引数) propName:null<br>
     *         (引数) request:not null<br>
     *         (引数) response:not null<br>
     *         (状態) session（request.getSession()）:not null<br>
     *
     * <br>
     * 期待値：(戻り値) Object:－<br>
     *         (状態変化) 例外:IllegalArgumentException<br>
     *         (状態変化) ログ:＜ログ＞<br>
     *                    エラーログ：<br>
     *                    "illegal argument:propName = [null]"<br>
     *
     * <br>
     * 引数propNameの値がnullの時、例外が発生すること。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetValueFromSession04() throws Exception {
        // 擬似セッション
        HttpSession session = new MockHttpSession();

        // 擬似リクエスト
        MockHttpServletRequest request = new BLogicMapper_MockHttpServletRequest01();
        request.setSession(session);

        // 擬似レスポンス
        HttpServletResponse response = new MockHttpServletResponse();

        // BLogicMapper生成
        BLogicMapper mapper = new BLogicMapper(resource);

        // テスト実施・判定
        try {
            Object result = mapper.getValueFromSession(null, request, response);
            fail();
        } catch (IllegalArgumentException e) {
            // テスト結果確認
            assertTrue(LogUTUtil
                    .checkError("illegal argument:propName = [null]"));
        }
    }

    /**
     * testSetValueToSession01()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) value:"value"<br>
     *         (引数) propName:"sessionValue"<br>
     *         (引数) request:not null<br>
     *         (引数) response:not null<br>
     *         (状態) session（request.getSession()）:[sessionValue="sessionValue"]<br>
     *
     * <br>
     * 期待値：(戻り値) void:－<br>
     *         (状態変化) 例外:－<br>
     *         (状態変化) ログ:－<br>
     *         (状態変化) session:[sessionValue="value"]<br>
     *
     * <br>
     * 引数valueの値がsessionに反映されること。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetValueToSession01() throws Exception {
        // 擬似セッション
        HttpSession session = new MockHttpSession();
        session.setAttribute("sessionValue", "sessionValue");

        // 擬似リクエスト
        MockHttpServletRequest request = new BLogicMapper_MockHttpServletRequest01();
        request.setSession(session);

        // 擬似レスポンス
        HttpServletResponse response = new MockHttpServletResponse();

        // BLogicMapper生成
        BLogicMapper mapper = new BLogicMapper(resource);

        // テスト実施
        mapper.setValueToSession("value", "sessionValue", request, response);

        // 判定
        assertEquals("value", session.getAttribute("sessionValue"));
    }

    /**
     * testSetValueToSession02()
     * <br><br>
     *
     * (異常系)
     * <br>
     * 観点：CG
     * <br><br>
     * 入力値：(引数) value:"value"<br>
     *         (引数) propName:""<br>
     *         (引数) request:not null<br>
     *         (引数) response:not null<br>
     *         (状態) session（request.getSession()）:not null<br>
     *
     * <br>
     * 期待値：(戻り値) void:－<br>
     *         (状態変化) 例外:IllegalArgumentException<br>
     *         (状態変化) ログ:＜ログ＞<br>
     *                    エラーログ：<br>
     *                    "illegal argument:propName = []"<br>
     *         (状態変化) session:－<br>
     *
     * <br>
     * 引数propNameの値が空文字の時、例外が発生すること。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetValueToSession02() throws Exception {
        // 擬似セッション
        HttpSession session = new MockHttpSession();

        // 擬似リクエスト
        MockHttpServletRequest request = new BLogicMapper_MockHttpServletRequest01();
        request.setSession(session);

        // 擬似レスポンス
        HttpServletResponse response = new MockHttpServletResponse();

        // BLogicMapper生成
        BLogicMapper mapper = new BLogicMapper(resource);

        // テスト実施・判定
        try {
            mapper.setValueToSession("value", "", request, response);
            fail();
        } catch (IllegalArgumentException e) {
            // テスト結果確認
            assertTrue(LogUTUtil.checkError("illegal argument:propName = []"));
        }
    }

    /**
     * testSetValueToSession03()
     * <br><br>
     *
     * (異常系)
     * <br>
     * 観点：CG
     * <br><br>
     * 入力値：(引数) value:"value"<br>
     *         (引数) propName:null<br>
     *         (引数) request:not null<br>
     *         (引数) response:not null<br>
     *         (状態) session（request.getSession()）:not null<br>
     *
     * <br>
     * 期待値：(戻り値) void:－<br>
     *         (状態変化) 例外:IllegalArgumentException<br>
     *         (状態変化) ログ:＜ログ＞<br>
     *                    エラーログ：<br>
     *                    "illegal argument:propName = [null]"<br>
     *         (状態変化) session:－<br>
     *
     * <br>
     * 引数propNameの値がnullの時、例外が発生すること。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetValueToSession03() throws Exception {
        // 擬似セッション
        HttpSession session = new MockHttpSession();

        // 擬似リクエスト
        MockHttpServletRequest request = new BLogicMapper_MockHttpServletRequest01();
        request.setSession(session);

        // 擬似レスポンス
        HttpServletResponse response = new MockHttpServletResponse();

        // BLogicMapper生成
        BLogicMapper mapper = new BLogicMapper(resource);

        // テスト実施・判定
        try {
            mapper.setValueToSession("value", null, request, response);
            fail();
        } catch (IllegalArgumentException e) {
            // テスト結果確認
            assertTrue(LogUTUtil
                    .checkError("illegal argument:propName = [null]"));
        }
    }

    /**
     * testSetValueToSession04()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) value:null<br>
     *         (引数) propName:"sessionValue"<br>
     *         (引数) request:not null<br>
     *         (引数) response:not null<br>
     *         (状態) session（request.getSession()）:[sessionValue="sessionValue"]<br>
     *
     * <br>
     * 期待値：(戻り値) void:－<br>
     *         (状態変化) 例外:－<br>
     *         (状態変化) ログ:－<br>
     *         (状態変化) session:sessionValueが削除される<br>
     *
     * <br>
     * valueがnullの時、sessionに登録されていたAttributeが削除されること。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetValueToSession04() throws Exception {
        // 擬似セッション
        HttpSession session = new MockHttpSession();
        session.setAttribute("sessionValue", "sessionValue");

        // 擬似リクエスト
        MockHttpServletRequest request = new BLogicMapper_MockHttpServletRequest01();
        request.setSession(session);

        // 擬似レスポンス
        HttpServletResponse response = new MockHttpServletResponse();

        // BLogicMapper生成
        BLogicMapper mapper = new BLogicMapper(resource);

        // テスト実施
        mapper.setValueToSession(null, "sessionValue", request, response);

        // 判定
        assertFalse(session.getAttributeNames().hasMoreElements());
    }

    /**
     * testSetValueToSession05()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) value:"value"<br>
     *         (引数) propName:"sessionValue"<br>
     *         (引数) request:not null<br>
     *         (引数) response:not null<br>
     *         (状態) session（request.getSession()）:sessionValue設定なし<br>
     *
     * <br>
     * 期待値：(戻り値) void:－<br>
     *         (状態変化) 例外:－<br>
     *         (状態変化) ログ:－<br>
     *         (状態変化) session:[sessionValue="value"]<br>
     *
     * <br>
     * 引数valueの値がsessionに反映されること。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetValueToSession05() throws Exception {
        // 擬似セッション
        HttpSession session = new MockHttpSession();

        // 擬似リクエスト
        MockHttpServletRequest request = new BLogicMapper_MockHttpServletRequest01();
        request.setSession(session);

        // 擬似レスポンス
        HttpServletResponse response = new MockHttpServletResponse();

        // BLogicMapper生成
        BLogicMapper mapper = new BLogicMapper(resource);

        // テスト実施
        mapper.setValueToSession("value", "sessionValue", request, response);

        // 判定
        assertEquals("value", session.getAttribute("sessionValue"));
    }

    /**
     * testGetValueFromForm01()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) propName:"param1"<br>
     *         (引数) request:not null<br>
     *         (引数) response:not null<br>
     *         (状態) mapping:not null<br>
     *         (状態) mapping.getScope():"request"<br>
     *         (状態) form:[param1="param1"]<br>
     *
     * <br>
     * 期待値：(戻り値) Object:"param1"<br>
     *         (状態変化) 例外:－<br>
     *         (状態変化) ログ:－<br>
     *
     * <br>
     * 引数のpropNameをキーにformに値が格納されていた時、取得した値を返却すること。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetValueFromForm01() throws Exception {
        // アクションマッピング
        ActionMapping mapping = new ActionMapping();
        mapping.setScope("request");

        // アクションフォーム生成
        DynaValidatorActionFormEx form = this.formEx;
        form.set("param1", "param1");

        // 擬似リクエスト
        HttpServletRequest request = new BLogicMapper_MockHttpServletRequest01();
        request.setAttribute(Globals.MAPPING_KEY, mapping);
        request.setAttribute(mapping.getAttribute(), form);

        // 擬似レスポンス
        HttpServletResponse response = new MockHttpServletResponse();

        // BLogicMapper生成
        BLogicMapper mapper = new BLogicMapper(resource);

        // テスト実施・判定
        assertEquals("param1", mapper.getValueFromForm("param1", request,
                response));
    }

    /**
     * testGetValueFromForm02()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) propName:"param2"<br>
     *         (引数) request:not null<br>
     *         (引数) response:not null<br>
     *         (状態) mapping:not null<br>
     *         (状態) mapping.getScope():"session"<br>
     *         (状態) form:[param2="param2"]<br>
     *
     * <br>
     * 期待値：(戻り値) Object:"param2"<br>
     *         (状態変化) 例外:－<br>
     *         (状態変化) ログ:－<br>
     *
     * <br>
     * 引数のpropNameをキーにformに値が格納されていた時、取得した値を返却すること。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetValueFromForm02() throws Exception {
        // アクションマッピング
        ActionMapping mapping = new ActionMapping();
        mapping.setScope("session");

        // アクションフォーム生成
        DynaValidatorActionFormEx form = this.formEx;
        form.set("param2", "param2");

        // 擬似セッション
        HttpSession session = new MockHttpSession();
        session.setAttribute(mapping.getAttribute(), form);

        // 擬似リクエスト
        MockHttpServletRequest request = new BLogicMapper_MockHttpServletRequest01();
        request.setAttribute(Globals.MAPPING_KEY, mapping);
        request.setSession(session);

        // 擬似レスポンス
        HttpServletResponse response = new MockHttpServletResponse();

        // BLogicMapper生成
        BLogicMapper mapper = new BLogicMapper(resource);

        // テスト実施・判定
        assertEquals("param2", mapper.getValueFromForm("param2", request,
                response));
    }

    /**
     * testGetValueFromForm03()
     * <br><br>
     *
     * (異常系)
     * <br>
     * 観点：G
     * <br><br>
     * 入力値：(引数) propName:"param0"<br>
     *         (引数) request:not null<br>
     *         (引数) response:not null<br>
     *         (状態) mapping:not null<br>
     *         (状態) mapping.getScope():"session"<br>
     *         (状態) form:param0のフィールドなし<br>
     *
     * <br>
     * 期待値：(戻り値) Object:－<br>
     *         (状態変化) 例外:PropertyAccessException<br>
     *         (状態変化) ログ:＜ログ＞<br>
     *                    エラーログ：<br>
     *                    "illegal argument:propName = [param0]"<br>
     *
     * <br>
     * 引数propNameで指定されるフィールドがformに存在しない時、例外が発生すること。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetValueFromForm03() throws Exception {
        // アクションマッピング
        ActionMapping mapping = new ActionMapping();
        mapping.setScope("session");

        // アクションフォーム生成
        FormEx form = this.formEx;

        // 擬似セッション
        HttpSession session = new MockHttpSession();
        session.setAttribute(mapping.getAttribute(), form);

        // 擬似リクエスト
        MockHttpServletRequest request = new BLogicMapper_MockHttpServletRequest01();
        request.setAttribute(Globals.MAPPING_KEY, mapping);
        request.setSession(session);

        // 擬似レスポンス
        HttpServletResponse response = new MockHttpServletResponse();

        // BLogicMapper生成
        BLogicMapper mapper = new BLogicMapper(resource);

        // テスト実施・判定
        try {
            Object result = mapper
                    .getValueFromForm("param0", request, response);
            fail();
        } catch (PropertyAccessException e) {
            // テスト結果確認
            assertTrue(LogUTUtil
                    .checkError("illegal argument:propName = [param0]"));
        }
    }

    /**
     * testGetValueFromForm04()
     * <br><br>
     *
     * (異常系)
     * <br>
     * 観点：CG
     * <br><br>
     * 入力値：(引数) propName:""<br>
     *         (引数) request:not null<br>
     *         (引数) response:not null<br>
     *         (状態) mapping:not null<br>
     *         (状態) mapping.getScope():"request"<br>
     *         (状態) form:not null<br>
     *
     * <br>
     * 期待値：(戻り値) Object:－<br>
     *         (状態変化) 例外:PropertyAccessException<br>
     *         (状態変化) ログ:＜ログ＞<br>
     *                    エラーログ：<br>
     *                    "illegal argument:propName = []"<br>
     *
     * <br>
     * 引数のpropNameが空文字の時、例外が発生すること。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetValueFromForm04() throws Exception {
        // アクションマッピング
        ActionMapping mapping = new ActionMapping();
        mapping.setScope("request");

        // アクションフォーム生成
        FormEx form = this.formEx;

        // 擬似リクエスト
        HttpServletRequest request = new BLogicMapper_MockHttpServletRequest01();
        request.setAttribute(Globals.MAPPING_KEY, mapping);
        request.setAttribute(mapping.getAttribute(), form);

        // 擬似レスポンス
        HttpServletResponse response = new MockHttpServletResponse();

        // BLogicMapper生成
        BLogicMapper mapper = new BLogicMapper(resource);

        // テスト実施・判定
        try {
            Object result = mapper.getValueFromForm("", request, response);
            fail();
        } catch (PropertyAccessException e) {
            // テスト結果確認
            assertTrue(LogUTUtil.checkError("illegal argument:propName = []"));
        }
    }

    /**
     * testGetValueFromForm05()
     * <br><br>
     *
     * (異常系)
     * <br>
     * 観点：CG
     * <br><br>
     * 入力値：(引数) propName:null<br>
     *         (引数) request:not null<br>
     *         (引数) response:not null<br>
     *         (状態) mapping:not null<br>
     *         (状態) mapping.getScope():"request"<br>
     *         (状態) form:not null<br>
     *
     * <br>
     * 期待値：(戻り値) Object:－<br>
     *         (状態変化) 例外:PropertyAccessException<br>
     *         (状態変化) ログ:＜ログ＞<br>
     *                    エラーログ：<br>
     *                    "illegal argument:propName = [null]"<br>
     *
     * <br>
     * 引数のpropNameがnullの時、例外が発生すること。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetValueFromForm05() throws Exception {
        // アクションマッピング
        ActionMapping mapping = new ActionMapping();
        mapping.setScope("request");

        // アクションフォーム生成
        FormEx form = this.formEx;

        // 擬似リクエスト
        HttpServletRequest request = new BLogicMapper_MockHttpServletRequest01();
        request.setAttribute(Globals.MAPPING_KEY, mapping);
        request.setAttribute(mapping.getAttribute(), form);

        // 擬似レスポンス
        HttpServletResponse response = new MockHttpServletResponse();

        // BLogicMapper生成
        BLogicMapper mapper = new BLogicMapper(resource);

        // テスト実施・判定
        try {
            Object result = mapper.getValueFromForm(null, request, response);
            fail();
        } catch (PropertyAccessException e) {
            // テスト結果確認
            assertTrue(LogUTUtil
                    .checkError("illegal argument:propName = [null]"));
        }
    }

    /**
     * testGetValueFromForm06()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) propName:"param1"<br>
     *         (引数) request:not null<br>
     *         (引数) response:not null<br>
     *         (状態) mapping:not null<br>
     *         (状態) mapping.getScope():"request"<br>
     *         (状態) form:[param1="param1"]<br>
     *
     * <br>
     * 期待値：(戻り値) Object:"java.lang.String"<br>
     *         (状態変化) 例外:－<br>
     *         (状態変化) ログ:－<br>
     *
     * <br>
     * 引数のpropNameがネスとしたプロパティを参照している場合、
     * プロパティ値を正常に取得できることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetValueFromForm06() throws Exception {
        // アクションマッピング
        ActionMapping mapping = new ActionMapping();
        mapping.setScope("request");
        mapping.setName("testGetValueFromForm06");

        // アクションフォーム生成
        BlogicMapper_ActionFormStub01 form =
        	new BlogicMapper_ActionFormStub01();
        form.setParam1("param1");

        // 擬似リクエスト
        HttpServletRequest request =
        	new BLogicMapper_MockHttpServletRequest01();
        request.setAttribute(Globals.MAPPING_KEY, mapping);
        request.setAttribute(mapping.getAttribute(), form);

        // 擬似レスポンス
        HttpServletResponse response = new MockHttpServletResponse();

        // BLogicMapper生成
        BLogicMapper mapper = new BLogicMapper(resource);

        // テスト実施・判定
        assertEquals("java.lang.String",
        		mapper.getValueFromForm("param1.class.name", request,
        		response));
    }

    /**
     * testSetValueToForm01()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) value:"value"<br>
     *         (引数) propName:"param1"<br>
     *         (引数) request:not null<br>
     *         (引数) response:not null<br>
     *         (状態) mapping:not null<br>
     *         (状態) mapping.getScope():"request"<br>
     *         (状態) form:[param1="param1"]<br>
     *                modified=false<br>
     *
     * <br>
     * 期待値：(戻り値) void:－<br>
     *         (状態変化) 例外:－<br>
     *         (状態変化) form:[param1="value"]<br>
     *                    modified=true<br>
     *
     * <br>
     * 引数valueの値がformに反映されること。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetValueToForm01() throws Exception {
        // アクションマッピング
        ActionMapping mapping = new ActionMapping();
        mapping.setScope("request");

        // アクションフォーム生成
        DynaValidatorActionFormEx form = this.formEx;
        form.set("param1", "param1");
        form.setModified(false);

        // 擬似リクエスト
        HttpServletRequest request = new BLogicMapper_MockHttpServletRequest01();
        request.setAttribute(Globals.MAPPING_KEY, mapping);
        request.setAttribute(mapping.getAttribute(), form);

        // 擬似レスポンス
        HttpServletResponse response = new MockHttpServletResponse();

        // BLogicMapper生成
        BLogicMapper mapper = new BLogicMapper(resource);

        // テスト実施
        mapper.setValueToForm("value", "param1", request, response);

        // 判定
        assertEquals("value", form.get("param1"));
        assertTrue(form.isModified());
    }

    /**
     * testSetValueToForm02()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) value:"value"<br>
     *         (引数) propName:"param2"<br>
     *         (引数) request:not null<br>
     *         (引数) response:not null<br>
     *         (状態) mapping:not null<br>
     *         (状態) mapping.getScope():"session"<br>
     *         (状態) form:[param2="param2"]<br>
     *                modified=false<br>
     *
     * <br>
     * 期待値：(戻り値) void:－<br>
     *         (状態変化) 例外:－<br>
     *         (状態変化) form:[param2="value"]<br>
     *                    modified=true<br>
     *
     * <br>
     * 引数valueの値がformに反映されること。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetValueToForm02() throws Exception {
        // アクションマッピング
        ActionMapping mapping = new ActionMapping();
        mapping.setScope("session");

        // アクションフォーム生成
        DynaValidatorActionFormEx form = this.formEx;
        form.set("param2", "param2");
        form.setModified(false);

        // 擬似セッション
        HttpSession session = new MockHttpSession();
        session.setAttribute(mapping.getAttribute(), form);

        // 擬似リクエスト
        MockHttpServletRequest request = new BLogicMapper_MockHttpServletRequest01();
        request.setAttribute(Globals.MAPPING_KEY, mapping);
        request.setSession(session);

        // 擬似レスポンス
        HttpServletResponse response = new MockHttpServletResponse();

        // BLogicMapper生成
        BLogicMapper mapper = new BLogicMapper(resource);

        // テスト実施
        mapper.setValueToForm("value", "param2", request, response);

        // 判定
        assertEquals("value", form.get("param2"));
        assertTrue(form.isModified());
    }

    /**
     * testSetValueToForm03()
     * <br><br>
     *
     * (異常系)
     * <br>
     * 観点：G
     * <br><br>
     * 入力値：(引数) value:"value"<br>
     *         (引数) propName:"param0"<br>
     *         (引数) request:not null<br>
     *         (引数) response:not null<br>
     *         (状態) mapping:not null<br>
     *         (状態) mapping.getScope():"request"<br>
     *         (状態) form:param0のフィールドなし<br>
     *                modified=false<br>
     *
     * <br>
     * 期待値：(戻り値) void:－<br>
     *         (状態変化) 例外:PropertyAccessException<br>
     *         (状態変化) ログ:＜ログ＞<br>
     *                    エラーログ：<br>
     *                    "illegal argument:propName = [param0]"<br>
     *         (状態変化) form:modified=false<br>
     *
     * <br>
     * 引数propNameで指定されるフィールドがformに存在しない時、例外が発生すること。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetValueToForm03() throws Exception {
        // アクションマッピング
        ActionMapping mapping = new ActionMapping();
        mapping.setScope("request");

        // アクションフォーム生成
        FormEx form = this.formEx;
        form.setModified(false);

        // 擬似リクエスト
        HttpServletRequest request = new BLogicMapper_MockHttpServletRequest01();
        request.setAttribute(Globals.MAPPING_KEY, mapping);
        request.setAttribute(mapping.getAttribute(), form);

        // 擬似レスポンス
        HttpServletResponse response = new MockHttpServletResponse();

        // BLogicMapper生成
        BLogicMapper mapper = new BLogicMapper(resource);

        // テスト実施・判定
        try {
            mapper.setValueToForm("value", "param0", request, response);
            fail();
        } catch (PropertyAccessException e) {
            // テスト結果確認
            assertTrue(LogUTUtil
                    .checkError("illegal argument:propName = [param0]"));
            assertFalse(form.isModified());
        }
    }

    /**
     * testSetValueToForm04()
     * <br><br>
     *
     * (異常系)
     * <br>
     * 観点：CG
     * <br><br>
     * 入力値：(引数) value:"value"<br>
     *         (引数) propName:""<br>
     *         (引数) request:not null<br>
     *         (引数) response:not null<br>
     *         (状態) mapping:not null<br>
     *         (状態) mapping.getScope():"request"<br>
     *         (状態) form:not null<br>
     *                modified=false<br>
     *
     * <br>
     * 期待値：(戻り値) void:－<br>
     *         (状態変化) 例外:PropertyAccessException<br>
     *         (状態変化) ログ:＜ログ＞<br>
     *                    エラーログ：<br>
     *                    "illegal argument:propName = []"<br>
     *         (状態変化) form:modified=false<br>
     *
     * <br>
     * 引数propNameの値が空文字の時、例外が発生すること。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetValueToForm04() throws Exception {
        // アクションマッピング
        ActionMapping mapping = new ActionMapping();
        mapping.setScope("request");

        // アクションフォーム生成
        FormEx form = this.formEx;
        form.setModified(false);

        // 擬似リクエスト
        HttpServletRequest request = new BLogicMapper_MockHttpServletRequest01();
        request.setAttribute(Globals.MAPPING_KEY, mapping);
        request.setAttribute(mapping.getAttribute(), form);

        // 擬似レスポンス
        HttpServletResponse response = new MockHttpServletResponse();

        // BLogicMapper生成
        BLogicMapper mapper = new BLogicMapper(resource);

        // テスト実施・判定
        try {
            mapper.setValueToForm("value", "", request, response);
            fail();
        } catch (PropertyAccessException e) {
            // テスト結果確認
            assertTrue(LogUTUtil
                    .checkError("illegal argument:propName = []"));
            assertFalse(form.isModified());
        }
    }

    /**
     * testSetValueToForm05()
     * <br><br>
     *
     * (異常系)
     * <br>
     * 観点：CG
     * <br><br>
     * 入力値：(引数) value:"value"<br>
     *         (引数) propName:null<br>
     *         (引数) request:not null<br>
     *         (引数) response:not null<br>
     *         (状態) mapping:not null<br>
     *         (状態) mapping.getScope():"request"<br>
     *         (状態) form:not null<br>
     *                modified=false<br>
     *
     * <br>
     * 期待値：(戻り値) void:－<br>
     *         (状態変化) 例外:PropertyAccessException<br>
     *         (状態変化) ログ:＜ログ＞<br>
     *                    エラーログ：<br>
     *                    "illegal argument:propName = [null]"<br>
     *         (状態変化) form:modified=false<br>
     *
     * <br>
     * 引数propNameの値がnullの時、例外が発生すること。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetValueToForm05() throws Exception {
        // アクションマッピング
        ActionMapping mapping = new ActionMapping();
        mapping.setScope("request");

        // アクションフォーム生成
        FormEx form = this.formEx;
        form.setModified(false);

        // 擬似リクエスト
        HttpServletRequest request = new BLogicMapper_MockHttpServletRequest01();
        request.setAttribute(Globals.MAPPING_KEY, mapping);
        request.setAttribute(mapping.getAttribute(), form);

        // 擬似レスポンス
        HttpServletResponse response = new MockHttpServletResponse();

        // BLogicMapper生成
        BLogicMapper mapper = new BLogicMapper(resource);

        // テスト実施・判定
        try {
            mapper.setValueToForm("value", null, request, response);
            fail();
        } catch (PropertyAccessException e) {
            // テスト結果確認
            assertTrue(LogUTUtil
                    .checkError("illegal argument:propName = [null]"));
            assertFalse(form.isModified());
        }
    }

    /**
     * testSetValueToForm06()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) value:"value"<br>
     *         (引数) propName:"param3.value"<br>
     *         (引数) request:not null<br>
     *         (引数) response:not null<br>
     *         (状態) mapping:not null<br>
     *         (状態) mapping.getScope():"request"<br>
     *         (状態) form:[param3=HashMap]<br>
     *                ※ActionForm実装クラス<br>
     *
     * <br>
     * 期待値：(戻り値) void:－<br>
     *         (状態変化) 例外:－<br>
     *         (状態変化) form:[param3=HashMap{ value="value"}]<br>
     *
     * <br>
     * 引数のpropNameにネストしたプロパティが指定された場合、
     * 正常に値が設定されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetValueToForm06() throws Exception {
        // アクションマッピング
        ActionMapping mapping = new ActionMapping();
        mapping.setScope("request");
        Map map = new HashMap();

        // アクションフォーム生成
        BlogicMapper_ActionFormStub01 form =
        	new BlogicMapper_ActionFormStub01();
        form.setParam3(map);

        // 擬似リクエスト
        HttpServletRequest request = new BLogicMapper_MockHttpServletRequest01();
        request.setAttribute(Globals.MAPPING_KEY, mapping);
        request.setAttribute(mapping.getAttribute(), form);

        // 擬似レスポンス
        HttpServletResponse response = new MockHttpServletResponse();

        // BLogicMapper生成
        BLogicMapper mapper = new BLogicMapper(resource);

        // テスト実施
        mapper.setValueToForm("value", "param3.value", request, response);

        // 判定
        Map result = form.getParam3();
        assertEquals("value", result.get("value"));
    }

    /**
     * testGetValueFromApplication01()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) propName:"sessionValue"<br>
     *         (引数) request:not null<br>
     *         (引数) response:not null<br>
     *         (状態) servletContext:[applicationValue="applicationValue"]<br>
     *
     * <br>
     * 期待値：(戻り値) Object:"applicationValue"<br>
     *         (状態変化) 例外:－<br>
     *         (状態変化) ログ:－<br>
     *
     * <br>
     * 引数のpropNameをキーにサーブレットコンテキストに値が格納されていた時、
     * 取得した値を返却すること。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetValueFromApplication01() throws Exception {
        // 擬似セッション
        HttpSession session = new MockHttpSession();
        ServletContext context = session.getServletContext();
        context.setAttribute("applicationValue", "applicationValue");

        // 擬似リクエスト
        MockHttpServletRequest request = new BLogicMapper_MockHttpServletRequest01();
        request.setSession(session);

        // 擬似レスポンス
        HttpServletResponse response = new MockHttpServletResponse();

        // BLogicMapper生成
        BLogicMapper mapper = new BLogicMapper(resource);

        // テスト実施・判定
        assertEquals("applicationValue",
            mapper.getValueFromApplication("applicationValue", request, response));
    }

    /**
     * testGetValueFromApplication02()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) propName:"value"<br>
     *         (引数) request:not null<br>
     *         (引数) response:not null<br>
     *         (状態) servletContext:[applicationValue="applicationValue"]<br>
     *
     * <br>
     * 期待値：(戻り値) Object:null<br>
     *         (状態変化) 例外:－<br>
     *         (状態変化) ログ:－<br>
     *
     * <br>
     * 引数のpropNameをキーにサーブレットコンテキストに値が格納されていない時、
     * nullを返却すること。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetValueFromApplication02() throws Exception {
        // 擬似セッション
        HttpSession session = new MockHttpSession();
        ServletContext context = session.getServletContext();
        context.setAttribute("applicationValue", "applicationValue");

        // 擬似リクエスト
        MockHttpServletRequest request = new BLogicMapper_MockHttpServletRequest01();
        request.setSession(session);

        // 擬似レスポンス
        HttpServletResponse response = new MockHttpServletResponse();

        // BLogicMapper生成
        BLogicMapper mapper = new BLogicMapper(resource);

        // テスト実施・判定
        assertNull(mapper.getValueFromApplication("value", request, response));
    }

    /**
     * testGetValueFromApplication03()
     * <br><br>
     *
     * (異常系)
     * <br>
     * 観点：CG
     * <br><br>
     * 入力値：(引数) propName:""<br>
     *         (引数) request:not null<br>
     *         (引数) response:not null<br>
     *         (状態) servletContext:not null<br>
     *
     * <br>
     * 期待値：(戻り値) Object:－<br>
     *         (状態変化) 例外:IllegalArgumentException<br>
     *         (状態変化) ログ:＜ログ＞<br>
     *                    エラーログ：<br>
     *                    "illegal argument:propName = []"<br>
     *
     * <br>
     * 引数propNameの値が空文字の時、例外が発生すること。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetValueFromApplication03() throws Exception {
        // 擬似セッション
        HttpSession session = new MockHttpSession();

        // 擬似リクエスト
        MockHttpServletRequest request = new BLogicMapper_MockHttpServletRequest01();
        request.setSession(session);

        // 擬似レスポンス
        HttpServletResponse response = new MockHttpServletResponse();

        // BLogicMapper生成
        BLogicMapper mapper = new BLogicMapper(resource);

        // テスト実施・判定
        try {
            Object result = mapper.getValueFromApplication("", request, response);
            fail();
        } catch (IllegalArgumentException e) {
            // テスト結果確認
            assertTrue(LogUTUtil.checkError("illegal argument:propName = []"));
        }
    }

    /**
     * testGetValueFromApplication04()
     * <br><br>
     *
     * (異常系)
     * <br>
     * 観点：CG
     * <br><br>
     * 入力値：(引数) propName:null<br>
     *         (引数) request:not null<br>
     *         (引数) response:not null<br>
     *         (状態) servletContext:not null<br>
     *
     * <br>
     * 期待値：(戻り値) Object:－<br>
     *         (状態変化) 例外:IllegalArgumentException<br>
     *         (状態変化) ログ:＜ログ＞<br>
     *                    エラーログ：<br>
     *                    "illegal argument:propName = [null]"<br>
     *
     * <br>
     * 引数propNameの値がnullの時、例外が発生すること。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetValueFromApplication04() throws Exception {
        // 擬似セッション
        HttpSession session = new MockHttpSession();

        // 擬似リクエスト
        MockHttpServletRequest request = new BLogicMapper_MockHttpServletRequest01();
        request.setSession(session);

        // 擬似レスポンス
        HttpServletResponse response = new MockHttpServletResponse();

        // BLogicMapper生成
        BLogicMapper mapper = new BLogicMapper(resource);

        // テスト実施・判定
        try {
            Object result = mapper.getValueFromApplication(null, request, response);
            fail();
        } catch (IllegalArgumentException e) {
            // テスト結果確認
            assertTrue(LogUTUtil
                    .checkError("illegal argument:propName = [null]"));
        }
    }

}
