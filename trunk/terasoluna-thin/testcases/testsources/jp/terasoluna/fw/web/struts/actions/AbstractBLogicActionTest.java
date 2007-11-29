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

package jp.terasoluna.fw.web.struts.actions;

import java.util.Iterator;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import jp.terasoluna.fw.exception.SystemException;
import jp.terasoluna.fw.service.thin.AbstractBLogicMapper;
import jp.terasoluna.fw.service.thin.BLogicIO;
import jp.terasoluna.fw.service.thin.BLogicMessage;
import jp.terasoluna.fw.service.thin.BLogicMessages;
import jp.terasoluna.fw.service.thin.BLogicResources;
import jp.terasoluna.fw.service.thin.BLogicResult;
import jp.terasoluna.fw.web.struts.action.ActionMappingEx;
import jp.terasoluna.fw.web.struts.form.DynaValidatorActionFormEx;
import jp.terasoluna.fw.web.struts.plugins.BLogicIOPlugIn;
import jp.terasoluna.utlib.LogUTUtil;
import jp.terasoluna.utlib.MockHttpServletRequest;
import jp.terasoluna.utlib.MockHttpServletResponse;
import jp.terasoluna.utlib.UTUtil;
import junit.framework.TestCase;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.config.ModuleConfig;
import org.apache.struts.config.impl.ModuleConfigImpl;

import com.mockrunner.mock.web.MockActionMapping;
import com.mockrunner.mock.web.MockServletContext;

/**
 * {@link jp.terasoluna.fw.web.struts.actions.AbstractBLogicAction}
 * クラスのブラックボックステスト。
 *
 * <p>
 * <h4>【クラスの概要】</h4>
 * ビジネスロジック起動抽象クラス。
 * <p>
 *
 * @see jp.terasoluna.fw.web.struts.actions.AbstractBLogicAction
 */
public class AbstractBLogicActionTest extends TestCase {

    /**
     * このテストケースを実行する為の
     * GUI アプリケーションを起動する。
     *
     * @param args java コマンドに設定されたパラメータ
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(AbstractBLogicActionTest.class);
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
        LogUTUtil.flush();
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
    public AbstractBLogicActionTest(String name) {
        super(name);
    }

    /**
     * testSetSaveMessageScope01()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) saveMessageScope:"request"<br>
     *         (状態) saveMessageScope:null<br>
     *
     * <br>
     * 期待値：(状態変化) saveMessageScope:"request"<br>
     *
     * <br>
     * 引数に指定した値がAbstractBLogicActionのsaveMessageScopeに正常に格納されること。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetSaveMessageScope01() throws Exception {
        // 前処理
        AbstractBLogicAction_AbstractBLogicActionStub01 action =
            new AbstractBLogicAction_AbstractBLogicActionStub01();

        UTUtil.setPrivateField(action, "saveMessageScope", null);

        // テスト実施
        action.setSaveMessageScope("request");

        // 判定
        assertEquals("request", UTUtil.getPrivateField(action, "saveMessageScope"));
    }

    /**
     * testDoExecute02()
     * <br><br>
     *
     * (異常系)
     * <br>
     * 観点：G
     * <br><br>
     * 入力値：(引数) mapping:Mapping：<br>
     *                ClassCastException発生<br>
     *         (引数) form:FormEx<br>
     *         (引数) request:not null<br>
     *         (引数) response:not null<br>
     *
     * <br>
     * 期待値：(状態変化) 例外:SystemException：<br>
     *                    メッセージキー：<br>
     *                    "error.blogic.mapping"<br>
     *                    ラップした例外：<br>
     *                    ClassCastException<br>
     *         (状態変化) ログ:＜ログ＞<br>
     *                    エラーログ：<br>
     *                    "Illegal ActionMapping."<br>
     *
     * <br>
     * 引数mappingがActionMappingExのインスタンスでなかった場合、
     * 例外が発生することを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testDoExecute02() throws Exception {
        // 前処理
        MockActionMapping mapping = new MockActionMapping();

        DynaValidatorActionFormEx form = new DynaValidatorActionFormEx();

        MockHttpServletRequest req = new MockHttpServletRequest();
        MockHttpServletResponse res = new MockHttpServletResponse();

        AbstractBLogicAction_AbstractBLogicActionStub01 action =
            new AbstractBLogicAction_AbstractBLogicActionStub01();

        try {
            // テスト実施
            action.doExecute(mapping, form, req, res);
            fail();
        } catch (SystemException e) {
            // 判定
            assertEquals(ClassCastException.class.getName(),
                    e.getCause().getClass().getName());
            assertTrue(LogUTUtil.checkError("Illegal ActionMapping."));
            assertEquals("errors.blogic.mapping", e.getErrorCode());
        }
    }

    /**
     * testDoExecute03()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) mapping:MappingEx<br>
     *         (引数) form:ActionForm<br>
     *         (引数) request:not null<br>
     *         (引数) response:not null<br>
     *         (状態) getBLogic<br>
     *                Params():null<br>
     *         (状態) doExecuteBLogic()<br>
     *                リターン値result:BLogicResult<br>
     *                ([resultString=<br>
     *                "resultString"])<br>
     *
     * <br>
     * 期待値：(戻り値) ActionForward:ActionForward.<br>
     *                  getName()：<br>
     *                  "resultString"<br>
     *         (状態変化) preDoExecute<br>
     *                    BLogic():呼び出し確認：<br>
     *                    preDoExecuteBLogic(request, response, null)<br>
     *         (状態変化) doExecute<br>
     *                    BLogic():呼び出し確認：<br>
     *                    doExecuteBLogic(null)<br>
     *         (状態変化) postDoExecute<br>
     *                    Blogic():呼び出し確認：<br>
     *                    postDoExecuteBLogic(request, response, params, null)<br>
     *         (状態変化) evaluate<br>
     *                    BLogicResult():呼び出し確認<br>
     *                    evaluateBLogicResult(result, request, response, mappingEx)<br>
     *
     * <br>
     * AbstractBLogicActionのgetBLogicParams()がnullを返した場合、<br>
     * ログを出力した後、<br>
     * preDoExecuteBLogic()、doExecuteBLogic()、postDoExecuteBLogic()を呼び出す。
     * <br>
     * doExecuteBLogic()のリターン値がnullでなかった場合、evaluateBLogicResult()
     * を呼び出した後ActionForwardオブジェクトを返すことを確認する。<br>
     * ActionFormがFormExでなくても正常実行することを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testDoExecute03() throws Exception {
        // 前処理
        ActionMappingEx mapping = new ActionMappingEx();

        // "resultString"のアクションフォワードの生成
        ActionForward forw = new ActionForward();
        forw.setName("resultString");
        // ModuleConfigを作成
        ModuleConfig mConfig = new ModuleConfigImpl("");
        // 返却されるActionForwardを指定
        mConfig.addForwardConfig(forw);
        // モジュールコンフィグのforward属性指定
        mapping.setModuleConfig(mConfig);

        // ActionEx
        ActionForm form = 
        	new ActionForm(){
			    private static final long serialVersionUID = 1L;
	        };

        // request, response
        MockHttpServletRequest req = new MockHttpServletRequest();
        MockHttpServletResponse res = new MockHttpServletResponse();

        // AbstractBLogicAction
        AbstractBLogicAction_AbstractBLogicActionStub01 action =
            new AbstractBLogicAction_AbstractBLogicActionStub01();

        // BLogicParams : null
        Object params = null;
        UTUtil.setPrivateField(action, "params", params);

        // BLogicResult : resultString="resultString"
        BLogicResult result = new BLogicResult();
        result.setResultString("resultString");
        UTUtil.setPrivateField(action, "result", result);

        ActionForward forward = null;

        // テスト実施
        forward = action.doExecute(mapping, form, req, res);

        // 判定
        // forward.getName()
        assertEquals("resultString", forward.getName());

        // 呼び出し確認:preDoExecuteBLogic()
        assertTrue(action.isPreDoExecuteBLogic);
        assertSame(req, action.requestPre);
        assertSame(res, action.responsePre);
        assertSame(params, action.paramsPre);

        // 呼び出し確認:doExecuteBLogic()
        assertTrue(action.isDoExecuteBLogic);
        assertSame(params, action.paramDoExecuteBLogic);

        // 呼び出し確認:postDoExecuteBLogic()
        assertTrue(action.isPostDoExecuteBLogic);
        assertSame(req, action.requestPost);
        assertSame(res, action.responsePost);
        assertSame(params, action.paramsPost);
        assertSame(result, action.resultPost);

        // 呼び出し確認:evaluateBLogicResult()
        assertTrue(action.isEvaluateBLogicResult);
        assertSame(result, action.resultEvaluateBLogicResult);
        assertSame(req, action.requestEvaluateBLogicResult);
        assertSame(res, action.responseEvaluateBLogicResult);
        assertSame(mapping, action.mappingEvaluateBLogicResult);
    }

    /**
     * testDoExecute04()
     * <br><br>
     *
     * (異常系)
     * <br>
     * 観点：G
     * <br><br>
     * 入力値：(引数) mapping:MappingEx<br>
     *         (引数) form:FormEx<br>
     *         (引数) request:not null<br>
     *         (引数) response:not null<br>
     *         (状態) getBLogic<br>
     *                Params():オブジェクト<br>
     *         (状態) doExecuteBLogic()<br>
     *                リターン値result:null<br>
     *
     * <br>
     * 期待値：(状態変化) preDoExecute<br>
     *                    BLogic():呼び出し確認：<br>
     *                    preDoExecuteBLogic(request, response, params)<br>
     *         (状態変化) doExecute<br>
     *                    BLogic():呼び出し確認：<br>
     *                    doExecuteBLogic(params)<br>
     *         (状態変化) postDoExecute<br>
     *                    Blogic():呼び出し確認：<br>
     *                    postDoExecuteBLogic(request, response, params, null)<br>
     *         (状態変化) evaluate<br>
     *                    BLogicResult():呼び出ししないことを確認<br>
     *         (状態変化) 例外:SystemException：<br>
     *                    メッセージキー：<br>
     *                    "errors.blogic.result.null"<br>
     *                    ラップした例外：<br>
     *                    NullPointerException<br>
     *         (状態変化) ログ:＜ログ＞<br>
     *                    エラーログ：<br>
     *                    "BLogicResult is null."<br>
     *
     * <br>
     * AbstractBLogicActionのgetBLogicParams()がnot nullであり、doExcecute()が
     * BLogicResultインスタンスを返した場合、preDoExecuteBLogic()、
     * doExecuteBLogic()、postDoExecuteBLogic()を呼び出す。<br>
     * doExecuteBLogic()のリターン値がnullの場合、例外が発生することを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testDoExecute04() throws Exception {
        // 前処理
        ActionMappingEx mapping = new ActionMappingEx();

        // "resultString"のアクションフォワードの生成
        ActionForward forw = new ActionForward();
        forw.setName("resultString");
        // ModuleConfigを作成
        ModuleConfig mConfig = new ModuleConfigImpl("");
        // 返却されるActionForwardを指定
        mConfig.addForwardConfig(forw);
        // モジュールコンフィグのforward属性指定
        mapping.setModuleConfig(mConfig);

        // ActionEx
        DynaValidatorActionFormEx form = new DynaValidatorActionFormEx();

        // request, response
        MockHttpServletRequest req = new MockHttpServletRequest();
        MockHttpServletResponse res = new MockHttpServletResponse();

        // AbstractBLogicAction
        AbstractBLogicAction_AbstractBLogicActionStub01 action =
            new AbstractBLogicAction_AbstractBLogicActionStub01();

        // BLogicParams : Object
        Object params = new Object();
        UTUtil.setPrivateField(action, "params", params);

        // BLogicResult : null
        BLogicResult result = null;
        UTUtil.setPrivateField(action, "result", result);


        try {
            // テスト実施
            action.doExecute(mapping, form, req, res);
            fail();
        } catch (SystemException e) {
            // 判定
            assertEquals(NullPointerException.class.getName(),
                    e.getCause().getClass().getName());
            assertTrue(LogUTUtil.checkError("BLogicResult is null."));
            assertEquals("errors.blogic.result.null", e.getErrorCode());

            // 呼び出し確認:preDoExecuteBLogic()
            assertTrue(action.isPreDoExecuteBLogic);
            assertSame(req, action.requestPre);
            assertSame(res, action.responsePre);
            assertSame(params, action.paramsPre);

            // 呼び出し確認:doExecuteBLogic()
            assertTrue(action.isDoExecuteBLogic);
            assertSame(params, action.paramDoExecuteBLogic);

            // 呼び出し確認:postDoExecuteBLogic()
            assertTrue(action.isPostDoExecuteBLogic);
            assertSame(req, action.requestPost);
            assertSame(res, action.responsePost);
            assertSame(params, action.paramsPost);
            assertSame(result, action.resultPost);

            // 呼び出し確認:evaluateBLogicResult()
            assertFalse(action.isEvaluateBLogicResult);
        }
    }

    /**
     * testDoExecute05()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) mapping:MappingEx<br>
     *         (引数) form:FormEx<br>
     *         (引数) request:not null<br>
     *         (引数) response:not null<br>
     *         (状態) getBLogic<br>
     *                Params():オブジェクト<br>
     *         (状態) doExecuteBLogic()<br>
     *                リターン値result:BLogicResult<br>
     *                ([resultString=<br>
     *                "resultString"])<br>
     *
     * <br>
     * 期待値：(戻り値) ActionForward:ActionForward.<br>
     *                  getName()：<br>
     *                  "resultString"<br>
     *         (状態変化) preDoExecute<br>
     *                    BLogic():呼び出し確認：<br>
     *                    preDoExecuteBLogic(request, response, params)<br>
     *         (状態変化) doExecute<br>
     *                    BLogic():呼び出し確認：<br>
     *                    doExecuteBLogic(params)<br>
     *         (状態変化) postDoExecute<br>
     *                    Blogic():呼び出し確認：<br>
     *                    postDoExecuteBLogic(request, response, params, result)<br>
     *         (状態変化) evaluate<br>
     *                    BLogicResult():呼び出し確認<br>
     *                    evaluateBLogicResult(result, request, response, mappingEx)<br>
     *
     * <br>
     * AbstractBLogicActionのgetBLogicParams()がnot nullであり、doExcecute()が
     * BLogicResultインスタンスを返した場合、preDoExecuteBLogic()、
     * doExecuteBLogic()、postDoExecuteBLogic()を呼び出す。<br>
     * doExecuteBLogic()のリターン値がnullでなかった場合、evaluateBLogicResult()
     * を呼び出した後ActionForwardオブジェクトを返すことを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testDoExecute05() throws Exception {
        // 前処理
        ActionMappingEx mapping = new ActionMappingEx();

        // "resultString"のアクションフォワードの生成
        ActionForward forw = new ActionForward();
        forw.setName("resultString");
        // ModuleConfigを作成
        ModuleConfig mConfig = new ModuleConfigImpl("");
        // 返却されるActionForwardを指定
        mConfig.addForwardConfig(forw);
        // モジュールコンフィグのforward属性指定
        mapping.setModuleConfig(mConfig);

        // ActionEx
        DynaValidatorActionFormEx form = new DynaValidatorActionFormEx();

        // request, response
        MockHttpServletRequest req = new MockHttpServletRequest();
        MockHttpServletResponse res = new MockHttpServletResponse();

        // AbstractBLogicAction
        AbstractBLogicAction_AbstractBLogicActionStub01 action =
            new AbstractBLogicAction_AbstractBLogicActionStub01();

        // BLogicParams : Object
        Object params = "abc";
        UTUtil.setPrivateField(action, "params", params);

        // BLogicResult : resultString="resultString"
        BLogicResult result = new BLogicResult();
        result.setResultString("resultString");
        UTUtil.setPrivateField(action, "result", result);

        ActionForward forward = null;

        // テスト実施
        forward = action.doExecute(mapping, form, req, res);

        // 判定
        // forward.getName()
        assertEquals("resultString", forward.getName());

        // 呼び出し確認:preDoExecuteBLogic()
        assertTrue(action.isPreDoExecuteBLogic);
        assertSame(req, action.requestPre);
        assertSame(res, action.responsePre);
        assertSame(params, action.paramsPre);

        // 呼び出し確認:doExecuteBLogic()
        assertTrue(action.isDoExecuteBLogic);
        assertSame(params, action.paramDoExecuteBLogic);

        // 呼び出し確認:postDoExecuteBLogic()
        assertTrue(action.isPostDoExecuteBLogic);
        assertSame(req, action.requestPost);
        assertSame(res, action.responsePost);
        assertSame(params, action.paramsPost);
        assertSame(result, action.resultPost);

        // 呼び出し確認:evaluateBLogicResult()
        assertTrue(action.isEvaluateBLogicResult);
        assertSame(result, action.resultEvaluateBLogicResult);
        assertSame(req, action.requestEvaluateBLogicResult);
        assertSame(res, action.responseEvaluateBLogicResult);
        assertSame(mapping, action.mappingEvaluateBLogicResult);
    }

    /**
     * testGetBLogicParams01()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) mapping:not null<br>
     *         (引数) request:not null<br>
     *         (引数) response:not null<br>
     *         (状態) getBLogicIO(<br>
     *                request, response):BLogicIO<br>
     *                ([path="path"])<br>
     *         (状態) getBLogicMapper(<br>
     *                request):AbstractBLogicAction_AbstractBLogicMapperStub01の
     *                オブジェクト<br>
     *         (状態) AbstractBLogicMapper.<br>
     *                mapBLogicParams():null<br>
     *
     * <br>
     * 期待値：(戻り値) P:null<br>
     *
     * <br>
     * AbstractBLogicMapper.mapBLogicParams()がnullの場合、nullを返すことを
     * 確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetBLogicParams01() throws Exception {
        // 前処理
        // mapping
        ActionMappingEx mapping = new ActionMappingEx();

        // request, response
        MockHttpServletRequest req = new MockHttpServletRequest();
        MockHttpServletResponse res = new MockHttpServletResponse();

        // AbstractBLogicAction
        AbstractBLogicAction_AbstractBLogicActionStub03 action =
            new AbstractBLogicAction_AbstractBLogicActionStub03();

        // mapper.mapBLogicParams : null
        AbstractBLogicMapper mapper =
            new AbstractBLogicAction_AbstractBLogicMapperStub01();
        action.mapper = mapper;

        // テスト実施
        Object bean = action.getBLogicParams(mapping, req, res);

        // 判定
        assertNull(bean);
    }

    /**
     * testGetBLogicParams02()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) mapping:not null<br>
     *         (引数) request:not null<br>
     *         (引数) response:not null<br>
     *         (状態) getBLogicIO(<br>
     *                request, response):BLogicIO<br>
     *                ([path="path"])<br>
     *         (状態) getBLogicMapper(<br>
     *                request):AbstractBLogicAction_AbstractBLogicMapperStub02の
     *                オブジェクト<br>
     *         (状態) AbstractBLogicMapper.<br>
     *                mapBLogicParams():String("abc")<br>
     *
     * <br>
     * 期待値：(戻り値) P:String("abc")<br>
     *
     * <br>
     * AbstractBLogicMapper.mapBLogicParams()がオブジェクトの場合、
     * そのオブジェクトを返すことを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetBLogicParams02() throws Exception {
        // 前処理
        // mapping
        ActionMappingEx mapping = new ActionMappingEx();

        // request, response
        MockHttpServletRequest req = new MockHttpServletRequest();
        MockHttpServletResponse res = new MockHttpServletResponse();

        // AbstractBLogicAction
        AbstractBLogicAction_AbstractBLogicActionStub03 action =
            new AbstractBLogicAction_AbstractBLogicActionStub03();

        // mapper.mapBLogicParams : new String("abc")
        AbstractBLogicMapper mapper =
            new AbstractBLogicAction_AbstractBLogicMapperStub02();
        action.mapper = mapper;

        // テスト実施
        Object bean = action.getBLogicParams(mapping, req, res);

        // 判定
        assertEquals("abc", bean);
    }

    /**
     * testConvertMessages01()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) blogicMessages:null<br>
     *
     * <br>
     * 期待値：(戻り値) ActionMessages:null<br>
     *
     * <br>
     * 引数blogicMessagesがnullの場合、nullを返すことを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testConvertMessages01() throws Exception {
        // 前処理
        BLogicMessages errors = null;

        AbstractBLogicAction_AbstractBLogicActionStub05 action =
            new AbstractBLogicAction_AbstractBLogicActionStub05();

        // テスト実施
        ActionMessages messages = action.convertMessages(errors);

        // 判定
        assertNull(messages);
    }

    /**
     * testConvertMessages02()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) blogicMessages:0件<br>
     *         (状態) メッセージ一覧:list.size()==0<br>
     *         (状態) メッセージ<br>
     *                グループ名一覧:groupList.size()==0<br>
     *
     * <br>
     * 期待値：(戻り値) ActionMessages:ActionMessages：()<br>
     *
     * <br>
     * BLogicMessagesとメッセージグループ名のサイズが0の場合、空のActionMessagesを
     * 返すことを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testConvertMessages02() throws Exception {
        // 前処理
        // list size : 0
        BLogicMessages errors = new BLogicMessages();

        AbstractBLogicAction_AbstractBLogicActionStub05 action =
            new AbstractBLogicAction_AbstractBLogicActionStub05();

        // テスト実施
        ActionMessages messages = action.convertMessages(errors);

        // 判定
        assertTrue(messages.isEmpty());
    }

    /**
     * testConvertMessages03()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) blogicMessages:not null<br>
     *         (状態) メッセージ一覧:list：<br>
     *                (BLogicMessage：<br>
     *                [key："error01"、value："value01"]<br>
     *                )<br>
     *         (状態) メッセージ<br>
     *                グループ名一覧:groupList：<br>
     *                ("errors")<br>
     *         (状態) blogicMessage.<br>
     *                isResource():true<br>
     *
     * <br>
     * 期待値：(戻り値) ActionMessages:ActionMessages：<br>
     *                  (<br>
     *                  ["errors"、ActionMessage[<br>
     *                  key："error01"、value："value01"]]<br>
     *                  )<br>
     *
     * <br>
     * BLogicMessagesに1件のBLogicMessageが格納されており、メッセージグループ名
     * に1件のグループ名が格納されており、blogicMessage.isResource()がtrueの
     * 場合、前提条件のグループ名がキーであり、前提条件のBLogicMessasgeのキーと
     * バリューを持つActionMessageが格納されたActionMessagesが返されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testConvertMessages03() throws Exception {
        // 前処理
        BLogicMessages errors = new BLogicMessages();
        BLogicMessage error = new BLogicMessage("error01", "value01");
        UTUtil.setPrivateField(error, "resource", true);
        errors.add("errors", error);

        AbstractBLogicAction_AbstractBLogicActionStub05 action =
            new AbstractBLogicAction_AbstractBLogicActionStub05();

        // テスト実施
        ActionMessages messages = action.convertMessages(errors);

        // 判定
        // messages判定
        // group
        Iterator itProps = messages.properties();
        String prop = (String) itProps.next();
        assertEquals("errors", prop);
        // message
        Iterator itMessages = messages.get(prop);
        ActionMessage resultMessage = (ActionMessage) itMessages.next();
        assertEquals("error01", resultMessage.getKey());
        assertEquals("value01", resultMessage.getValues()[0]);
    }

    /**
     * testConvertMessages04()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) blogicMessages:not null<br>
     *         (状態) メッセージ一覧:list：<br>
     *                (BLogicMessage：<br>
     *                [key："error01"、value[0]："value0"、value[1]："value1"、
     *                value[2]："value2"]<br>
     *                )<br>
     *         (状態) メッセージ<br>
     *                グループ名一覧:groupList：<br>
     *                ("errors")<br>
     *         (状態) blogicMessage.<br>
     *                isResource():true<br>
     *
     * <br>
     * 期待値：(戻り値) ActionMessages:ActionMessages：<br>
     *                  (<br>
     *                  ["errors"、ActionMessage[<br>
     *                  key："error01"、value[0]："value0"、value[1]："value1"、
     *                  value[2]："value2"]]<br>
     *                  )<br>
     *
     * <br>
     * BLogicMessagesに1件のBLogicMessageが格納されており、メッセージグループ名
     * に1件のグループ名が格納されており、blogicMessage.isResource()がtrueの
     * 場合、前提条件のグループ名がキーであり、前提条件のBLogicMessasgeのキーと
     * バリューを持つActionMessageが格納されたActionMessagesが返されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testConvertMessages04() throws Exception {
        // 前処理
        BLogicMessages errors = new BLogicMessages();
        Object[] values = new Object[3];
        values[0] = "value0";
        values[1] = "value1";
        values[2] = "value2";
        BLogicMessage error = new BLogicMessage("error01", values);
        UTUtil.setPrivateField(error, "resource", true);
        errors.add("errors", error);

        AbstractBLogicAction_AbstractBLogicActionStub05 action =
            new AbstractBLogicAction_AbstractBLogicActionStub05();

        // テスト実施
        ActionMessages messages = action.convertMessages(errors);

        // 判定
        // messages判定
        // group
        Iterator itProps = messages.properties();
        String prop = (String) itProps.next();
        assertEquals("errors", prop);
        // message
        Iterator itMessages = messages.get(prop);
        ActionMessage resultMessage = (ActionMessage) itMessages.next();
        assertEquals("error01", resultMessage.getKey());
        assertEquals("value0", resultMessage.getValues()[0]);
        assertEquals("value1", resultMessage.getValues()[1]);
        assertEquals("value2", resultMessage.getValues()[2]);
    }

    /**
     * testConvertMessages05()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) blogicMessages:not null<br>
     *         (状態) メッセージ一覧:list：<br>
     *                (BLogicMessage：<br>
     *                [key："error01"、value："value01"]<br>
     *                )<br>
     *         (状態) メッセージ<br>
     *                グループ名一覧:groupList：<br>
     *                ("errors")<br>
     *         (状態) blogicMessage.<br>
     *                isResource():false<br>
     *
     * <br>
     * 期待値：(戻り値) ActionMessages:ActionMessages：<br>
     *                  (<br>
     *                  ["errors"、ActionMessage[<br>
     *                  key："error01"、value：null、resource：false]]<br>
     *                  )<br>
     *
     * <br>
     * BLogicMessagesに1件のBLogicMessageが格納されており、メッセージグループ名
     * に1件のグループ名が格納されており、blogicMessage.isResource()がtrue
     * でなかった場合、前提条件のグループ名がキーであり、前提条件の
     * BLogicMessasgeのキーとBLogicMessage.isResource()のバリューを持つ
     * ActionMessageが格納されたActionMessagesが返されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testConvertMessages05() throws Exception {
        // 前処理
        BLogicMessages errors = new BLogicMessages();
        BLogicMessage error = new BLogicMessage("error01", "value01");
        UTUtil.setPrivateField(error, "resource", false);
        errors.add("errors", error);

        AbstractBLogicAction_AbstractBLogicActionStub05 action =
            new AbstractBLogicAction_AbstractBLogicActionStub05();

        // テスト実施
        ActionMessages messages = action.convertMessages(errors);

        // 判定
        // messages判定
        // group
        Iterator itProps = messages.properties();
        String prop = (String) itProps.next();
        assertEquals("errors", prop);
        // message
        Iterator itMessages = messages.get(prop);
        ActionMessage resultMessage = (ActionMessage) itMessages.next();
        assertEquals("error01", resultMessage.getKey());
        assertNull(resultMessage.getValues());
        assertFalse(resultMessage.isResource());
    }

    /**
     * testConvertMessages06()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) blogicMessages:not null<br>
     *         (状態) メッセージ一覧:list：<br>
     *                (BLogicMessage：<br>
     *                [key："error01"、value："value01"]、<br>
     *                BLogicMessage：<br>
     *                [key："error02"、value："value02"]、<br>
     *                BLogicMessage：<br>
     *                [key："error03"、value："value03"]<br>
     *                )<br>
     *         (状態) メッセージ<br>
     *                グループ名一覧:groupList：<br>
     *                ("errors)<br>
     *         (状態) blogicMessage.<br>
     *                isResource():list.get(0)：true<br>
     *                list.get(1)：true<br>
     *                list.get(2)：true<br>
     *
     * <br>
     * 期待値：(戻り値) ActionMessages:ActionMessages：<br>
     *                  (<br>
     *                  ["errors"、ActionMessage[<br>
     *                  key："error01"、value："value01"]]、<br>
     *                  ["errors"、ActionMessage[<br>
     *                  key："error02"、value："value02"]]、<br>
     *                  ["errors"、ActionMessage[<br>
     *                  key："error03"、value："value03"]]<br>
     *                  )<br>
     *
     * <br>
     * blogicMessagesに複数のBLogicMessageとメッセージグループが格納されている
     * 場合、グループ名をキーとし、前提条件のBLogicMessageのキーとバリューを持つ
     * ActionMessageが順序どおりに格納されたActionMessagesが返されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testConvertMessages06() throws Exception {
        // 前処理
        BLogicMessages errors = new BLogicMessages();
        BLogicMessage error = new BLogicMessage("error01", "value01");
        UTUtil.setPrivateField(error, "resource", true);
        errors.add("errors", error);

        error = new BLogicMessage("error02", "value02");
        UTUtil.setPrivateField(error, "resource", true);
        errors.add("errors", error);

        error = new BLogicMessage("error03", "value03");
        UTUtil.setPrivateField(error, "resource", true);
        errors.add("errors", error);

        AbstractBLogicAction_AbstractBLogicActionStub05 action =
            new AbstractBLogicAction_AbstractBLogicActionStub05();

        // テスト実施
        ActionMessages messages = action.convertMessages(errors);

        // 判定
        // messages判定
        // group
        Iterator itProps = messages.properties();
        String prop = (String) itProps.next();
        assertEquals("errors", prop);

        // message
        Iterator itMessages = messages.get(prop);
        ActionMessage resultMessage = (ActionMessage) itMessages.next();
        assertEquals("error01", resultMessage.getKey());
        assertEquals("value01", resultMessage.getValues()[0]);

        resultMessage = (ActionMessage) itMessages.next();
        assertEquals("error02", resultMessage.getKey());
        assertEquals("value02", resultMessage.getValues()[0]);

        resultMessage = (ActionMessage) itMessages.next();
        assertEquals("error03", resultMessage.getKey());
        assertEquals("value03", resultMessage.getValues()[0]);
    }

    /**
     * testEvaluateBLogicResult01()
     * <br><br>
     *
     * (異常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) result:null<br>
     *         (引数) request:not null<br>
     *         (引数) response:not null<br>
     *         (引数) mapping:not null<br>
     *
     * <br>
     * 期待値：(状態変化) 例外:SystemException：<br>
     *                    メッセージキー：<br>
     *                    "errors.blogic.result.null"<br>
     *                    ラップした例外：<br>
     *                    NullPointerException<br>
     *         (状態変化) ログ:＜ログ＞<br>
     *                    エラーログ：<br>
     *                    "BLogicResult is null."<br>
     *
     * <br>
     * 引数resultがnullの場合、SystemExceptionが発生する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testEvaluateBLogicResult01() throws Exception {
        // 前処理
        // result : null
        BLogicResult result = null;

        // request, response
        MockHttpServletRequest req = new MockHttpServletRequest();
        MockHttpServletResponse res = new MockHttpServletResponse();

        // mapping
        ActionMappingEx mapping = new ActionMappingEx();

        // AbstractBLogicAction
        AbstractBLogicAction_AbstractBLogicActionStub04 action =
            new AbstractBLogicAction_AbstractBLogicActionStub04();

        try {
            // テスト実施
            action.evaluateBLogicResult(result, req, res, mapping);
            fail();
        } catch (SystemException e) {
            assertEquals(NullPointerException.class.getName(),
                    e.getCause().getClass().getName());
            assertTrue(LogUTUtil.checkError("BLogicResult is null."));
            assertEquals("errors.blogic.result.null", e.getErrorCode());
        }
    }

    /**
     * testEvaluateBLogicResult02()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：D
     * <br><br>
     * 入力値：(引数) result:BLogicResult<br>
     *                オブジェクト<br>
     *                result.getErrors()：<br>
     *                BLogicMessages.list(<br>
     *                  BLogicMessage[key="error01"、value="value01"]<br>
     *                )<br>
     *                BLogicMessages.groupList("errors")<br>
     *                <br>
     *                result.getMessages()：<br>
     *                BLogicMessages.list(<br>
     *                  BLogicMessage[key="message01"、value="value01"]<br>
     *                )<br>
     *                BLogicMessages.groupList("messages")<br>
     *         (引数) request:not null<br>
     *         (引数) response:not null<br>
     *         (引数) mapping:not null<br>
     *         (状態) saveMessageSope:null<br>
     *
     * <br>
     * 期待値：(状態変化) request.getAttribute(<br>
     *                    Globals.ERROR_KEY):ActionMessages：<br>
     *                    (<br>
     *                    ["errors"、ActionMessage[<br>
     *                    key："error01"、value："value01"]]<br>
     *                    )<br>
     *         (状態変化) request.getAttribute(<br>
     *                    Globals.MESSAGE_KEY):ActionMessages：<br>
     *                    (<br>
     *                    ["messages"、ActionMessage[<br>
     *                    key："message01"、value："value01"]]<br>
     *                    )<br>
     *         (状態変化) getBLogicIO(<br>
     *                    mappingEx, request):呼び出ししないことを確認<br>
     *         (状態変化) AbstractBLogicMapper.<br>
     *                    mapBLogicReslut():呼び出ししないことを確認<br>
     *
     * <br>
     * saveMessageScopeがnullの場合、requestにエラーとメッセージのリストが
     * 格納されていることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testEvaluateBLogicResult02() throws Exception {
        // 前処理
        // result : not null
        BLogicResult result = new BLogicResult();

        // errors
        BLogicMessages errors = new BLogicMessages();
        BLogicMessage error = new BLogicMessage("error01", "value01");
        errors.add("errors", error);

        // messages
        BLogicMessages messages = new BLogicMessages();
        BLogicMessage message = new BLogicMessage("message01", "value01");
        messages.add("messages", message);

        result.setErrors(errors);
        result.setMessages(messages);

        // request, response
        MockHttpServletRequest req = new MockHttpServletRequest();
        req.setAttribute("isMapBLogicResult", "false");
        MockHttpServletResponse res = new MockHttpServletResponse();

        // mapping
        ActionMappingEx mapping = new ActionMappingEx();

        // AbstractBLogicAction
        AbstractBLogicAction_AbstractBLogicActionStub04 action =
            new AbstractBLogicAction_AbstractBLogicActionStub04();

        // saveMessageScope : null
        String saveMessageScope = null;
        UTUtil.setPrivateField(action, "saveMessageScope", saveMessageScope);

        // テスト実施
        action.evaluateBLogicResult(result, req, res, mapping);

        // 判定
        // errors検証
        ActionMessages resultErrors =
            (ActionMessages) req.getAttribute(Globals.ERROR_KEY);
        // group
        Iterator itProps = resultErrors.properties();
        String prop = (String) itProps.next();
        assertEquals("errors", prop);
        // error
        Iterator itErrors = resultErrors.get(prop);
        ActionMessage resultError = (ActionMessage) itErrors.next();
        assertEquals("error01", resultError.getKey());
        assertEquals("value01", resultError.getValues()[0]);

        // messages検証
        ActionMessages resultMessages =
            (ActionMessages) req.getAttribute(Globals.MESSAGE_KEY);
        // group
        Iterator itMProps = resultMessages.properties();
        String mprop = (String) itMProps.next();
        assertEquals("messages", mprop);
        // message
        Iterator itMessages = resultMessages.get(mprop);
        ActionMessage resultMessage = (ActionMessage) itMessages.next();
        assertEquals("message01", resultMessage.getKey());
        assertEquals("value01", resultMessage.getValues()[0]);

        // getBLogicIO呼び出し確認
        assertFalse(action.isGetBLogicIO);

        // mapBLogicResult呼び出し確認
        assertEquals("false", req.getAttribute("isMapBLogicResult"));
    }

    /**
     * testEvaluateBLogicResult03()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：D
     * <br><br>
     * 入力値：(引数) result:BLogicResult<br>
     *                オブジェクト<br>
     *                result.getErrors()：<br>
     *                BLogicMessages.list(<br>
     *                  BLogicMessage[key="error01"、value="value01"]<br>
     *                )<br>
     *                BLogicMessages.groupList("errors")<br>
     *                <br>
     *                result.getMessages()：<br>
     *                BLogicMessages.list(<br>
     *                  BLogicMessage[key="message01"、value="value01"]<br>
     *                )<br>
     *                BLogicMessages.groupList("messages")<br>
     *         (引数) request:not null<br>
     *         (引数) response:not null<br>
     *         (引数) mapping:not null<br>
     *         (状態) saveMessageSope:""<br>
     *
     * <br>
     * 期待値：(状態変化) request.getAttribute(<br>
     *                    Globals.ERROR_KEY):ActionMessages：<br>
     *                    (<br>
     *                    ["errors"、ActionMessage[<br>
     *                    key："error01"、value："value01"]]<br>
     *                    )<br>
     *         (状態変化) request.getAttribute(<br>
     *                    Globals.MESSAGE_KEY):ActionMessages：<br>
     *                    (<br>
     *                    ["messages"、ActionMessage[<br>
     *                    key："message01"、value："value01"]]<br>
     *                    )<br>
     *         (状態変化) getBLogicIO(<br>
     *                    mappingEx, request):呼び出ししないことを確認<br>
     *         (状態変化) AbstractBLogicMapper.<br>
     *                    mapBLogicReslut():呼び出ししないことを確認<br>
     *
     * <br>
     * saveMessageScopeが"session"でなかった場合、requestにエラーとメッセージの
     * リストが格納されていることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testEvaluateBLogicResult03() throws Exception {
        // 前処理
        // result : not null
        BLogicResult result = new BLogicResult();

        // errors
        BLogicMessages errors = new BLogicMessages();
        BLogicMessage error = new BLogicMessage("error01", "value01");
        errors.add("errors", error);

        // messages
        BLogicMessages messages = new BLogicMessages();
        BLogicMessage message = new BLogicMessage("message01", "value01");
        messages.add("messages", message);

        result.setErrors(errors);
        result.setMessages(messages);

        // request, response
        MockHttpServletRequest req = new MockHttpServletRequest();
        req.setAttribute("isMapBLogicResult", "false");
        MockHttpServletResponse res = new MockHttpServletResponse();

        // mapping
        ActionMappingEx mapping = new ActionMappingEx();

        // AbstractBLogicAction
        AbstractBLogicAction_AbstractBLogicActionStub04 action =
            new AbstractBLogicAction_AbstractBLogicActionStub04();

        // saveMessageScope : ""
        String saveMessageScope = "";
        UTUtil.setPrivateField(action, "saveMessageScope", saveMessageScope);

        // テスト実施
        action.evaluateBLogicResult(result, req, res, mapping);

        // 判定
        // errors検証
        ActionMessages resultErrors =
            (ActionMessages) req.getAttribute(Globals.ERROR_KEY);
        // group
        Iterator itProps = resultErrors.properties();
        String prop = (String) itProps.next();
        assertEquals("errors", prop);
        // error
        Iterator itErrors = resultErrors.get(prop);
        ActionMessage resultError = (ActionMessage) itErrors.next();
        assertEquals("error01", resultError.getKey());
        assertEquals("value01", resultError.getValues()[0]);

        // messages検証
        ActionMessages resultMessages =
            (ActionMessages) req.getAttribute(Globals.MESSAGE_KEY);
        // group
        Iterator itMProps = resultMessages.properties();
        String mprop = (String) itMProps.next();
        assertEquals("messages", mprop);
        // message
        Iterator itMessages = resultMessages.get(mprop);
        ActionMessage resultMessage = (ActionMessage) itMessages.next();
        assertEquals("message01", resultMessage.getKey());
        assertEquals("value01", resultMessage.getValues()[0]);

        // getBLogicIO呼び出し確認
        assertFalse(action.isGetBLogicIO);

        // mapBLogicResult呼び出し確認
        assertEquals("false", req.getAttribute("isMapBLogicResult"));
    }

    /**
     * testEvaluateBLogicResult04()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：D
     * <br><br>
     * 入力値：(引数) result:BLogicResult<br>
     *                オブジェクト<br>
     *                result.getErrors()：<br>
     *                BLogicMessages.list(<br>
     *                  BLogicMessage[key="error01"、value="value01"]<br>
     *                )<br>
     *                BLogicMessages.groupList("errors")<br>
     *                <br>
     *                result.getMessages()：<br>
     *                BLogicMessages.list(<br>
     *                  BLogicMessage[key="message01"、value="value01"]<br>
     *                )<br>
     *                BLogicMessages.groupList("messages")<br>
     *         (引数) request:not null<br>
     *         (引数) response:not null<br>
     *         (引数) mapping:not null<br>
     *         (状態) saveMessageSope:"session"<br>
     *
     * <br>
     * 期待値：(状態変化) session.getAttribute(<br>
     *                    Globals.ERROR_KEY):ActionMessages：<br>
     *                    (<br>
     *                    ["errors"、ActionMessage[<br>
     *                    key："error01"、value："value01"]]<br>
     *                    )<br>
     *         (状態変化) session.getAttribute(<br>
     *                    Globals.MESSAGE_KEY):ActionMessages：<br>
     *                    (<br>
     *                    ["messages"、ActionMessage[<br>
     *                    key："message01"、value："value01"]]<br>
     *                    )<br>
     *         (状態変化) getBLogicIO(<br>
     *                    mappingEx, request):呼び出ししないことを確認<br>
     *         (状態変化) AbstractBLogicMapper.<br>
     *                    mapBLogicReslut():呼び出ししないことを確認<br>
     *
     * <br>
     * saveMessageScopeが"session"だった場合、sessionにエラーとメッセージの
     * リストが格納されていることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testEvaluateBLogicResult04() throws Exception {
        // 前処理
        // result : not null
        BLogicResult result = new BLogicResult();

        // errors
        BLogicMessages errors = new BLogicMessages();
        BLogicMessage error = new BLogicMessage("error01", "value01");
        errors.add("errors", error);

        // messages
        BLogicMessages messages = new BLogicMessages();
        BLogicMessage message = new BLogicMessage("message01", "value01");
        messages.add("messages", message);

        result.setErrors(errors);
        result.setMessages(messages);

        // request, response
        MockHttpServletRequest req = new MockHttpServletRequest();
        req.setAttribute("isMapBLogicResult", "false");
        MockHttpServletResponse res = new MockHttpServletResponse();

        // mapping
        ActionMappingEx mapping = new ActionMappingEx();

        // AbstractBLogicAction
        AbstractBLogicAction_AbstractBLogicActionStub04 action =
            new AbstractBLogicAction_AbstractBLogicActionStub04();

        // saveMessageScope : "session"
        String saveMessageScope = "session";
        UTUtil.setPrivateField(action, "saveMessageScope", saveMessageScope);

        // テスト実施
        action.evaluateBLogicResult(result, req, res, mapping);

        // 判定
        // sessionから取得する
        HttpSession session = req.getSession();
        // errors検証
        ActionMessages resultErrors =
            (ActionMessages) session.getAttribute(Globals.ERROR_KEY);
        // group
        Iterator itProps = resultErrors.properties();
        String prop = (String) itProps.next();
        assertEquals("errors", prop);
        // error
        Iterator itErrors = resultErrors.get(prop);
        ActionMessage resultError = (ActionMessage) itErrors.next();
        assertEquals("error01", resultError.getKey());
        assertEquals("value01", resultError.getValues()[0]);

        // messages検証
        ActionMessages resultMessages =
            (ActionMessages) session.getAttribute(Globals.MESSAGE_KEY);
        // group
        Iterator itMProps = resultMessages.properties();
        String mprop = (String) itMProps.next();
        assertEquals("messages", mprop);
        // message
        Iterator itMessages = resultMessages.get(mprop);
        ActionMessage resultMessage = (ActionMessage) itMessages.next();
        assertEquals("message01", resultMessage.getKey());
        assertEquals("value01", resultMessage.getValues()[0]);

        // getBLogicIO呼び出し確認
        assertFalse(action.isGetBLogicIO);

        // mapBLogicResult呼び出し確認
        assertEquals("false", req.getAttribute("isMapBLogicResult"));
    }

    /**
     * testEvaluateBLogicResult05()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：D
     * <br><br>
     * 入力値：(引数) result:BLogicResult<br>
     *                オブジェクト：<br>
     *                result.getErrors()：null<br>
     *                <br>
     *                result.getMessages()：<br>
     *                BLogicMessages.list(<br>
     *                  BLogicMessage[key="message01"、value="value01"]<br>
     *                )<br>
     *                BLogicMessages.groupList("messages")<br>
     *         (引数) request:not null<br>
     *         (引数) response:not null<br>
     *         (引数) mapping:not null<br>
     *         (状態) saveMessageSope:"SESSION"<br>
     *
     * <br>
     * 期待値：(状態変化) session.getAttribute(<br>
     *                    Globals.ERROR_KEY):null<br>
     *         (状態変化) session.getAttribute(<br>
     *                    Globals.MESSAGE_KEY):ActionMessages：<br>
     *                    (<br>
     *                    ["messages"、ActionMessage[<br>
     *                    key："message01"、value："value01"]]<br>
     *                    )<br>
     *         (状態変化) getBLogicIO(<br>
     *                    mappingEx, request):呼び出し確認：<br>
     *                    getBLogicIO(<br>
     *                    mappingEx, request)<br>
     *         (状態変化) AbstractBLogicMapper.<br>
     *                    mapBLogicReslut():呼び出し確認：<br>
     *                    mapBLogicResult(request, response, blogicIO, result)<br>
     *
     * <br>
     * 引数result.getErrors()がnullだった場合、AbstractBLogicMapper.<br>
     * mapBLogicReslut()が呼び出されることを確認
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testEvaluateBLogicResult05() throws Exception {
        // 前処理
        // result : not null
        BLogicResult result = new BLogicResult();

        // errors : null
        result.setErrors(null);

        // messages
        BLogicMessages messages = new BLogicMessages();
        BLogicMessage message = new BLogicMessage("message01", "value01");
        messages.add("messages", message);

        result.setMessages(messages);

        // request, response
        MockHttpServletRequest req = new MockHttpServletRequest();
        req.setAttribute("isMapBLogicResult", "false");
        MockHttpServletResponse res = new MockHttpServletResponse();

        // mapping
        ActionMappingEx mapping = new ActionMappingEx();

        // AbstractBLogicAction
        AbstractBLogicAction_AbstractBLogicActionStub04 action =
            new AbstractBLogicAction_AbstractBLogicActionStub04();

        // saveMessageScope : "SESSION"
        String saveMessageScope = "SESSION";
        UTUtil.setPrivateField(action, "saveMessageScope", saveMessageScope);

        // テスト実施
        action.evaluateBLogicResult(result, req, res, mapping);

        // 判定
        // sessionから取得する
        HttpSession session = req.getSession();
        // errors判定
        ActionMessages resultErrors =
            (ActionMessages) session.getAttribute(Globals.ERROR_KEY);
        assertNull(resultErrors);

        // messages判定
        ActionMessages resultMessages =
            (ActionMessages) session.getAttribute(Globals.MESSAGE_KEY);
        // group
        Iterator itMProps = resultMessages.properties();
        String mprop = (String) itMProps.next();
        assertEquals("messages", mprop);
        // message
        Iterator itMessages = resultMessages.get(mprop);
        ActionMessage resultMessage = (ActionMessage) itMessages.next();
        assertEquals("message01", resultMessage.getKey());
        assertEquals("value01", resultMessage.getValues()[0]);

        // getBLogicIO呼び出し確認
        assertTrue(action.isGetBLogicIO);

        // mapBLogicResult呼び出し確認
        assertEquals("true", req.getAttribute("isMapBLogicResult"));
    }

    /**
     * testIsErrorsEmpty01()
     * <br><br>
     *
     * (異常系)
     * <br>
     * 観点：G
     * <br><br>
     * 入力値：(引数) result:null<br>
     *
     * <br>
     * 期待値：(状態変化) 例外:SystemException：<br>
     *                    メッセージキー：<br>
     *                    "errors.blogic.result.null"<br>
     *                    ラップした例外：<br>
     *                    NullPointerException<br>
     *         (状態変化) ログ:＜ログ＞<br>
     *                    エラーログ：<br>
     *                    "BLogicResult is null."<br>
     *
     * <br>
     * resultがnullの場合、エラーが発生することを確認する
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testIsErrorsEmpty01() throws Exception {
        // 前処理
        BLogicResult result = null;

        AbstractBLogicAction_AbstractBLogicActionStub05 action =
            new AbstractBLogicAction_AbstractBLogicActionStub05();

        try {
            // テスト実施
            action.isErrorsEmpty(result);
            fail();
        } catch (SystemException e) {
            // 判定
            assertEquals(NullPointerException.class.getName(),
                    e.getCause().getClass().getName());
            assertTrue(LogUTUtil.checkError("BLogicResult is null."));
            assertEquals("errors.blogic.result.null", e.getErrorCode());
        }
    }

    /**
     * testIsErrorsEmpty02()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) result:BLogicResult<br>
     *                オブジェクト<br>
     *         (状態) result.getErrors():null<br>
     *
     * <br>
     * 期待値：(戻り値) boolean:true<br>
     *
     * <br>
     * result.getErrors()がnullの場合、trueが返されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testIsErrorsEmpty02() throws Exception {
        // 前処理
        BLogicResult result = new BLogicResult();
        BLogicMessages errors = null;
        result.setErrors(errors);

        AbstractBLogicAction_AbstractBLogicActionStub05 action =
            new AbstractBLogicAction_AbstractBLogicActionStub05();

        // テスト実施
        boolean b = action.isErrorsEmpty(result);

        // 判定
        assertTrue(b);
    }

    /**
     * testIsErrorsEmpty03()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) result:BLogicResult<br>
     *                オブジェクト<br>
     *         (状態) result.getErrors():BLogicMessages.list：(new ArrayList())
     *         <br>
     *         (状態) BLogicMessages.<br>
     *                isEmpty():true<br>
     *
     * <br>
     * 期待値：(戻り値) boolean:true<br>
     *
     * <br>
     * result.getErrors()のlistが0件のリストの場合、trueが返されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testIsErrorsEmpty03() throws Exception {
        // 前処理
        BLogicResult result = new BLogicResult();
        BLogicMessages errors = new BLogicMessages();
        result.setErrors(errors);

        AbstractBLogicAction_AbstractBLogicActionStub05 action =
            new AbstractBLogicAction_AbstractBLogicActionStub05();

        // テスト実施
        boolean b = action.isErrorsEmpty(result);

        // 判定
        assertTrue(b);
    }

    /**
     * testIsErrorsEmpty04()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) result:BLogicResult<br>
     *                オブジェクト<br>
     *         (状態) result.getErrors():BLogicMessages.list：<br>
     *                ([key="key"、value="value"])<br>
     *         (状態) BLogicMessages.<br>
     *                isEmpty():false<br>
     *
     * <br>
     * 期待値：(戻り値) boolean:false<br>
     *
     * <br>
     * result.getErrors()のlistが1件以上のリストの場合、falseが返されることを
     * 確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testIsErrorsEmpty04() throws Exception {
        // 前処理
        BLogicResult result = new BLogicResult();
        BLogicMessages errors = new BLogicMessages();
        BLogicMessage error = new BLogicMessage("key", "value");
        errors.add("errors", error);
        result.setErrors(errors);

        AbstractBLogicAction_AbstractBLogicActionStub05 action =
            new AbstractBLogicAction_AbstractBLogicActionStub05();

        // テスト実施
        boolean b = action.isErrorsEmpty(result);

        // 判定
        assertFalse(b);
    }

    /**
     * testGetBLogicMapper01()
     * <br><br>
     *
     * (異常系)
     * <br>
     * 観点：G
     * <br><br>
     * 入力値：(引数) req:not null<br>
     *         (状態) ServletContext.getAttribute("BLOGIC_MAPPER" +
     *         ModuleUtil.getPrefix(req)):null<br>
     *
     * <br>
     * 期待値：(状態変化) 例外:SystemException：<br>
     *                    メッセージキー：<br>
     *                    "errors.blogic.mapper.null"<br>
     *                    ラップした例外：<br>
     *                    NullPointerException<br>
     *         (状態変化) ログ:＜ログ＞<br>
     *                    エラーログ：<br>
     *                    "BLogicMapper is null."<br>
     *
     * <br>
     * ServletContextから取得したオブジェクトがnullの場合、
     * 例外が発生することを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetBLogicMapper01() throws Exception {
        // 前処理
        // request
        MockHttpServletRequest req = new MockHttpServletRequest();

        // ModuleUtil.getPrefix(req)："prefix"
        ModuleConfig moduleConfig = new ModuleConfigImpl01();
        req.setAttribute(Globals.MODULE_KEY, moduleConfig);

        // AbstractBLogicAction
        AbstractBLogicAction_AbstractBLogicActionStub02 action =
            new AbstractBLogicAction_AbstractBLogicActionStub02();

        // servlet
        AbstractBLogicAction_ActionServletStub01 servlet =
            new AbstractBLogicAction_ActionServletStub01();

        // ServletContext
        ServletContext context = new MockServletContext();
        context.setAttribute(BLogicIOPlugIn.BLOGIC_MAPPER_KEY + "prefix", null);
        servlet.setServletContext(context);

        UTUtil.setPrivateField(action, "servlet", servlet);

        try {
            // テスト実施
            action.getBLogicMapper(req);
            fail();
        } catch (SystemException e) {
            // 判定
            assertEquals(NullPointerException.class.getName(),
                    e.getCause().getClass().getName());
            assertTrue(LogUTUtil.checkError("BLogicMapper is null."));
            assertEquals("errors.blogic.mapper.null", e.getErrorCode());
        }
    }

    /**
     * testGetBLogicMapper02()
     * <br><br>
     *
     * (異常系)
     * <br>
     * 観点：G
     * <br><br>
     * 入力値：(引数) req:not null<br>
     *         (状態) ServletContext.getAttribute("BLOGIC_MAPPER" +
     *         ModuleUtil.getPrefix(req)):"abc"<br>
     *
     * <br>
     * 期待値：(状態変化) 例外:SystemException：<br>
     *                    メッセージキー：<br>
     *                    "errors.blogic.mapping"<br>
     *                    ラップした例外：<br>
     *                    ClassCastException<br>
     *         (状態変化) ログ:＜ログ＞<br>
     *                    エラーログ：<br>
     *                    "Cannot cast BLogicMapper : java.lang.String"<br>
     *
     * <br>
     * ServletContextから取得したオブジェクトがAbstractBLogicMapper
     * インスタンス以外の場合、例外が発生することを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetBLogicMapper02() throws Exception {
        // 前処理
        // request
        MockHttpServletRequest req = new MockHttpServletRequest();

        // ModuleUtil.getPrefix(req)："prefix"
        ModuleConfig moduleConfig = new ModuleConfigImpl01();
        req.setAttribute(Globals.MODULE_KEY, moduleConfig);

        // AbstractBLogicAction
        AbstractBLogicAction_AbstractBLogicActionStub02 action =
            new AbstractBLogicAction_AbstractBLogicActionStub02();

        // servlet
        AbstractBLogicAction_ActionServletStub01 servlet =
            new AbstractBLogicAction_ActionServletStub01();

        // ServletContext
        ServletContext context = new MockServletContext();
        context.setAttribute(BLogicIOPlugIn.BLOGIC_MAPPER_KEY + "prefix", "abc");
        servlet.setServletContext(context);

        UTUtil.setPrivateField(action, "servlet", servlet);

        try {
            // テスト実施
            action.getBLogicMapper(req);
            fail();
        } catch (SystemException e) {
            // 判定
            assertEquals(ClassCastException.class.getName(),
                    e.getCause().getClass().getName());
            assertTrue(LogUTUtil.checkError(
                    "Cannot cast BLogicMapper : java.lang.String"));
            assertEquals("errors.blogic.mapping", e.getErrorCode());
        }
    }

    /**
     * testGetBLogicMapper03()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) req:not null<br>
     *         (状態) ServletContext.getAttribute("BLOGIC_MAPPER" +
     *         ModuleUtil.getPrefix(req)):AbstractBLogicAction_AbstractBLogicMapperStub01のオブジェクト<br>
     *
     * <br>
     * 期待値：(戻り値) AbstractBLogicMapper:AbstractBLogicAction_AbstractBLogicMapperStub01のオブジェクト<br>
     *
     * <br>
     * ServletContextから取得したオブジェクトがAbstractBLogicMapperインスタンス
     * の場合、前提条件のAbstractBLogicMapperのインスタンスを返すことを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetBLogicMapper03() throws Exception {
        // 前処理
        // request
        MockHttpServletRequest req = new MockHttpServletRequest();

        // ModuleUtil.getPrefix(req)："prefix"
        ModuleConfig moduleConfig = new ModuleConfigImpl01();
        req.setAttribute(Globals.MODULE_KEY, moduleConfig);

        // AbstractBLogicAction
        AbstractBLogicAction_AbstractBLogicActionStub02 action =
            new AbstractBLogicAction_AbstractBLogicActionStub02();

        // servlet
        AbstractBLogicAction_ActionServletStub01 servlet =
            new AbstractBLogicAction_ActionServletStub01();

        AbstractBLogicAction_AbstractBLogicMapperStub01 tmapper =
            new AbstractBLogicAction_AbstractBLogicMapperStub01();

        // ServletContext
        ServletContext context = new MockServletContext();
        context.setAttribute(BLogicIOPlugIn.BLOGIC_MAPPER_KEY + "prefix", tmapper);
        servlet.setServletContext(context);

        UTUtil.setPrivateField(action, "servlet", servlet);

        AbstractBLogicMapper mapper = null;
        // テスト実施
        mapper = action.getBLogicMapper(req);

        // 判定
        assertSame(tmapper, mapper);
    }

    /**
     * testGetBLogicIO01()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) mapping:not null<br>
     *         (引数) req:not null<br>
     *         (状態) ServletContext.getAttribute(<br>
     *                "BLOGIC_RESOURCES" + <br>
     *                ModuleUtil.getPrefix(req)):null<br>
     *
     * <br>
     * 期待値：(戻り値) BLogicIO:null<br>
     *
     * <br>
     * ServletContextから取得したオブジェクトがnullの場合、
     * nullを返すことを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetBLogicIO01() throws Exception {
        // 前処理
        // request, response
        MockHttpServletRequest req = new MockHttpServletRequest();

        // mapping
        ActionMappingEx mapping = new ActionMappingEx();

        // ModuleUtil.getPrefix(req)："prefix"
        ModuleConfig moduleConfig = new ModuleConfigImpl01();
        req.setAttribute(Globals.MODULE_KEY, moduleConfig);

        // servlet
        AbstractBLogicAction_ActionServletStub01 servlet =
            new AbstractBLogicAction_ActionServletStub01();

        // ServletContext
        ServletContext context = new MockServletContext();
        context.setAttribute(
                BLogicResources.BLOGIC_RESOURCES_KEY + "prefix", null);
        servlet.setServletContext(context);

        // AbstractBLogicAction
        AbstractBLogicAction_AbstractBLogicActionStub02 action =
            new AbstractBLogicAction_AbstractBLogicActionStub02();

        UTUtil.setPrivateField(action, "servlet", servlet);

        BLogicIO io = null;

        // テスト実施
        io = action.getBLogicIO(mapping, req);

        // 判定
        assertNull(io);
    }

    /**
     * testGetBLogicIO02()
     * <br><br>
     *
     * (異常系)
     * <br>
     * 観点：G
     * <br><br>
     * 入力値：(引数) mapping:not null<br>
     *         (引数) req:not null<br>
     *         (状態) ServletContext.getAttribute(<br>
     *                "BLOGIC_RESOURCES" + <br>
     *                ModuleUtil.getPrefix(req)):"abc"<br>
     *
     * <br>
     * 期待値：(状態変化) 例外:SystemException：<br>
     *                    メッセージキー：<br>
     *                    "errors.blogic.resources"<br>
     *                    ラップした例外：<br>
     *                    ClassCastException<br>
     *         (状態変化) ログ:＜ログ＞<br>
     *                    エラーログ：<br>
     *                    "Cannot cast BLogicResources : java.lang.String"<br>
     *
     * <br>
     * ServletContextから取得したオブジェクトがBLogicResourcesインスタンス
     * 以外の場合、例外が発生することを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetBLogicIO02() throws Exception {
        // 前処理
        // request, response
        MockHttpServletRequest req = new MockHttpServletRequest();

        // mapping
        ActionMappingEx mapping = new ActionMappingEx();

        // ModuleUtil.getPrefix(req)："prefix"
        ModuleConfig moduleConfig = new ModuleConfigImpl01();
        req.setAttribute(Globals.MODULE_KEY, moduleConfig);

        // servlet
        AbstractBLogicAction_ActionServletStub01 servlet =
            new AbstractBLogicAction_ActionServletStub01();

        // ServletContext
        ServletContext context = new MockServletContext();
        context.setAttribute(
                BLogicResources.BLOGIC_RESOURCES_KEY + "prefix", "abc");
        servlet.setServletContext(context);

        // AbstractBLogicAction
        AbstractBLogicAction_AbstractBLogicActionStub02 action =
            new AbstractBLogicAction_AbstractBLogicActionStub02();

        UTUtil.setPrivateField(action, "servlet", servlet);

        BLogicIO io = null;

        try {
            // テスト実施
            io = action.getBLogicIO(mapping, req);
            fail();
        } catch (SystemException e) {
            // 判定
            assertEquals(ClassCastException.class.getName(),
                    e.getCause().getClass().getName());
            assertTrue(LogUTUtil.checkError(
                    "Cannot cast BLogicResources : java.lang.String"));
            assertEquals("errors.blogic.resources", e.getErrorCode());
        }

        // 判定
        assertNull(io);
    }

    /**
     * testGetBLogicIO03()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) mapping:not null<br>
     *         (引数) req:not null<br>
     *         (状態) ServletContext.getAttribute(<br>
     *                "BLOGIC_RESOURCES" + <br>
     *                ModuleUtil.getPrefix(req)):BLogicResourcesインスタンス<br>
     *         (状態) mapping.getPath():"abc"<br>
     *         (状態) resource.getBLogicIO("abc"):BLogicIO<br>
     *                ([path="abc"])<br>
     *
     * <br>
     * 期待値：(戻り値) BLogicIO:前提条件のresource.getBLogicIO("abc")と同一の
     * BLogicIOインスタンス<br>
     *
     * <br>
     * ServletContextから取得したオブジェクトがBLogicResourcesインスタンスの
     * 場合、前提条件のresource.getBLogicIO("abc")に該当するBLogicIOインスタンス
     * を返すことを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetBLogicIO03() throws Exception {
        // 前処理
        // request, response
        MockHttpServletRequest req = new MockHttpServletRequest();

        // mapping
        ActionMappingEx mapping = new ActionMappingEx();
        mapping.setPath("abc");

        // ModuleUtil.getPrefix(req)："prefix"
        ModuleConfig moduleConfig = new ModuleConfigImpl01();
        req.setAttribute(Globals.MODULE_KEY, moduleConfig);

        // servlet
        AbstractBLogicAction_ActionServletStub01 servlet =
            new AbstractBLogicAction_ActionServletStub01();

        // BLogicIO("abc") : path="abc"
        BLogicResources resources = new BLogicResources();
        BLogicIO blogicIO = new BLogicIO();
        blogicIO.setPath("abc");
        resources.setBLogicIO(blogicIO);

        // ServletContext
        ServletContext context = new MockServletContext();
        context.setAttribute(
                BLogicResources.BLOGIC_RESOURCES_KEY + "prefix", resources);
        servlet.setServletContext(context);

        // AbstractBLogicAction
        AbstractBLogicAction_AbstractBLogicActionStub02 action =
            new AbstractBLogicAction_AbstractBLogicActionStub02();

        UTUtil.setPrivateField(action, "servlet", servlet);

        BLogicIO io = null;

        // テスト実施
        io = action.getBLogicIO(mapping, req);

        // 判定
        assertSame(blogicIO, io);
    }

    /**
     * testGetBLogicIO04()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) mapping:not null<br>
     *         (引数) req:not null<br>
     *         (状態) ServletContext.getAttribute(<br>
     *                "BLOGIC_RESOURCES" + <br>
     *                ModuleUtil.getPrefix(req)):BLogicResourcesインスタンス<br>
     *         (状態) mapping.getPath():"abc"<br>
     *         (状態) resource.getBLogicIO("abc"):null<br>
     *
     * <br>
     * 期待値：(戻り値) BLogicIO:null<br>
     *
     * <br>
     * 前提条件のresource.getBLogicIO("abc")がnullを返す場合、nullが返されること
     * を確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetBLogicIO04() throws Exception {
        // 前処理
        // request, response
        MockHttpServletRequest req = new MockHttpServletRequest();

        // mapping
        ActionMappingEx mapping = new ActionMappingEx();
        mapping.setPath("abc");

        // ModuleUtil.getPrefix(req)："prefix"
        ModuleConfig moduleConfig = new ModuleConfigImpl01();
        req.setAttribute(Globals.MODULE_KEY, moduleConfig);

        // servlet
        AbstractBLogicAction_ActionServletStub01 servlet =
            new AbstractBLogicAction_ActionServletStub01();

        // BLogicIO("abc") : path="abc"
        BLogicResources resources = new BLogicResources();
        BLogicIO blogicIO = new BLogicIO();
        blogicIO.setPath("def");
        resources.setBLogicIO(blogicIO);

        // ServletContext
        ServletContext context = new MockServletContext();
        context.setAttribute(
                BLogicResources.BLOGIC_RESOURCES_KEY + "prefix", resources);
        servlet.setServletContext(context);

        // AbstractBLogicAction
        AbstractBLogicAction_AbstractBLogicActionStub02 action =
            new AbstractBLogicAction_AbstractBLogicActionStub02();

        UTUtil.setPrivateField(action, "servlet", servlet);

        BLogicIO io = null;

        // テスト実施
        io = action.getBLogicIO(mapping, req);

        // 判定
        assertNull(io);
    }
}
