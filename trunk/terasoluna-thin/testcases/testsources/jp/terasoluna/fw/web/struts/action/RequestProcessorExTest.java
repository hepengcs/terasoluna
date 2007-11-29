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

package jp.terasoluna.fw.web.struts.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.config.ModuleConfig;
import org.apache.struts.config.impl.ModuleConfigImpl;

import jp.terasoluna.fw.util.ExceptionUtil;
import jp.terasoluna.utlib.LogUTUtil;
import jp.terasoluna.utlib.MockHttpServletRequest;
import jp.terasoluna.utlib.MockHttpServletResponse;
import jp.terasoluna.utlib.MockHttpSession;
import junit.framework.TestCase;

/**
 * RequestProcessorEx ブラックボックステスト。<br>
 * 前提条件<br>
 * ・processActionFormEx()メソッドの確認は、processActionForm()
 * メソッド試験に内包される。<br>
 * 
 * @version 2004/03/18
 */
public class RequestProcessorExTest extends TestCase {

    /**
     * コンストラクタ。
     */
    public RequestProcessorExTest(String arg) {
        super(arg);
    }

    /**
     * @see TestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        LogUTUtil.flush();
    }

    /**
     * @see TestCase#tearDown()
     */
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * testProcess01。<br>
     * 
     * (正常系)<br>
     * 観点：A<br>
     * 
     * 入力値
     * req=HttpServletRequestインスタンス<br>
     * res=not null<br>
     * 
     * 期待値
     * 戻り値:void<br>
     * 
     * reqがHttpServletRequestのインスタンスの時、
     * HttpServletRequestExにラップされること※1<br>
     * 
     * @throws Exception 例外<br>
     */
    public void testProcess01() throws Exception {

        //テストデータ設定

        // process()で渡されたリクエストオブジェクトを返却する継承クラス
        // process()内で最初に呼ばれるメソッド、processPath()内で
        // nullを返却することにより、processPath()以降の処理を進めない。
        RequestProcessorEx_RequestProcessorExStub01 processor =
            new RequestProcessorEx_RequestProcessorExStub01();
        HttpServletRequest req = new MockHttpServletRequest();
        HttpServletResponse res = new MockHttpServletResponse();

        //テスト実行
        processor.process(req, res);

        //テスト結果確認
        assertSame(processor.getRequestEx(), req);
    }

    /**
     * testProcess02。<br>
     * 
     * (異常系)<br>
     * 観点：G<br>
     * 
     * 入力値
     * req=not null<br>
     * res=not null<br>
     * 
     * 期待値
     * 戻り値:void<br>
     * 
     * 親クラスのprocess()がIOExceptionをスローするとき、
     * そのままスローされること<br>
     * 
     * @throws Exception 例外<br>
     */
    public void testProcess02() throws Exception {
        // process()で渡されたリクエストオブジェクトを返却する継承クラス
        // process()内で最初に呼ばれるメソッド、processPath()内で
        // IOExceptionをスローすることにより、processPath()以降の処理を
        // 進めない。
        RequestProcessorEx_RequestProcessorExStub02 processor =
            new RequestProcessorEx_RequestProcessorExStub02();
        HttpServletRequest req = new MockHttpServletRequest();
        HttpServletResponse res = new MockHttpServletResponse();

        try {
            //テスト実行
            processor.process(req, res);
            fail();
        } catch (IOException e) {
            // ログ出力の確認（IOException）
            assertTrue(LogUTUtil.checkError(ExceptionUtil.getStackTrace(e)));
        }
    }

    /**
     * testProcess03。<br>
     * 
     * (異常系)<br>
     * 観点：G<br>
     * 
     * 入力値
     * req=not null<br>
     * res=not null<br>
     * 
     * 期待値
     * 戻り値:void<br>
     * 
     * 親クラスのprocess()がServletExceptionをスローするとき、
     * そのままスローされること<br>
     * 
     * @throws Exception 例外<br>
     */
    public void testProcess03() throws Exception {
        // process()で渡されたリクエストオブジェクトを返却する継承クラス
        // process()内で呼ばれるメソッド、processActionPerform()で
        // ServletExceptionをスローする。
        // processActionForm()にたどり着くまでの間、
        // 処理が終了してしまうメソッドは全てオーバライドする。
        RequestProcessorEx_RequestProcessorExStub03 processor =
            new RequestProcessorEx_RequestProcessorExStub03();
        HttpServletRequest req = new MockHttpServletRequest();
        HttpServletResponse res = new MockHttpServletResponse();

        //テスト実行
        try {
            processor.process(req, res);
            fail();
        } catch (ServletException e) {
            // ログ出力の確認（javax.servlet.ServletException）
            assertTrue(LogUTUtil.checkError(ExceptionUtil.getStackTrace(e)));
        }
    }

    /**
     * testProcess04。<br>
     * 
     * (異常系)<br>
     * 観点：G<br>
     * 
     * 入力値
     * req=not null<br>
     * res=not null<br>
     * 
     * 期待値
     * 戻り値:void<br>
     * 
     * 親クラスのprocess()がIOException、ServletException以外の
     * 例外をスローするとき、ServletExceptionにラップされ、
     * スローされること<br>
     * 
     * @throws Exception 例外<br>
     */
    public void testProcess04() throws Exception {
        //テストデータ設定

        // process()で渡されたリクエストオブジェクトを返却する継承クラス
        // process()内で呼ばれるメソッド、processPath()で
        // RuntimeExceptionをスローする。
        RequestProcessorEx_RequestProcessorExStub04 processor =
            new RequestProcessorEx_RequestProcessorExStub04();
        HttpServletRequest req = new MockHttpServletRequest();
        HttpServletResponse res = new MockHttpServletResponse();

        //テスト実行
        try {
            processor.process(req, res);
            fail();
        } catch (ServletException e) {
            //テスト結果確認
            // ServletExceptionがスローされた場合のみテスト成功。
            assertTrue(LogUTUtil.checkError(ExceptionUtil.getStackTrace(e.getRootCause())));
        }
    }

    /**
     * testProcessPopulate01。<br>
     * 
     * (正常系)<br>
     * 観点：A<br>
     * 
     * 入力値
     * req=パラメータ：companyId="companyId"<br>
     * res=not null<br>
     * form=not null<br>
     * mapping=not null<br>
     * request(SKIP_POPULATE属性)=null<br>
     * name="formName"<br>
     * 
     * 期待値
     * 戻り値:void<br>
     * SKIP_POPULATE=null<br>
     * form(フィールド値)=companyId="companyId"<br>
     * 
     * リクエスト属性にSKIP_POPULATEが登録されていない場合、
     * リクエストパラメータがフォームに反映され、
     * リクエスト属性のSKIP_POPULATEキーはnullのままである。<br>
     * 
     * @throws Exception 例外<br>
     */
    public void testProcessPopulate01() throws Exception {
        //テストデータ設定
        RequestProcessorEx processor = new RequestProcessorEx();

        // 擬似リクエスト・レスポンス
        MockHttpServletRequest req = new MockHttpServletRequest();
        req.setParameter("companyId", "companyId");
        req.setAttribute(RequestProcessorEx.SKIP_POPULATE, null);

        HttpServletResponse res = new MockHttpServletResponse();
        RequestProcessorEx_ActionFormStub01 form =
            new RequestProcessorEx_ActionFormStub01();

        ActionMappingEx mapping = new ActionMappingEx();
        mapping.setName("formName");

        processor.processPopulate(req, res, form, mapping);

        //テスト結果確認
        assertEquals("companyId", form.getCompanyId());
        assertNull(req.getAttribute(RequestProcessorEx.SKIP_POPULATE));
    }

    /**
     * testProcessPopulate02。<br>
     * 
     * (正常系)<br>
     * 観点：A<br>
     * 
     * 入力値
     * req=パラメータ：companyId="companyId"<br>
     * res=not null<br>
     * form=not null<br>
     * mapping=not null<br>
     * request(SKIP_POPULATE属性)=""<br>
     * name="formName"<br>
     * 
     * 期待値
     * 戻り値:void<br>
     * SKIP_POPULATE=""<br>
     * form(フィールド値)=companyId="companyId"<br>
     * 
     * リクエスト属性にSKIP_POPULATEで空文字が登録され、
     * フォーム名が空文字ではないとき、リクエストパラメータが
     * フォームに反映され、リクエスト属性のSKIP_POPULATE
     * キーは削除される。<br>
     * 
     * @throws Exception 例外<br>
     */
    public void testProcessPopulate02() throws Exception {
        //テストデータ設定
        RequestProcessorEx processor = new RequestProcessorEx();

        // 擬似リクエスト・レスポンス
        MockHttpServletRequest req = new MockHttpServletRequest();
        req.setParameter("companyId", "companyId");
        req.setAttribute(RequestProcessorEx.SKIP_POPULATE, "");

        HttpServletResponse res = new MockHttpServletResponse();
        RequestProcessorEx_ActionFormStub01 form =
            new RequestProcessorEx_ActionFormStub01();

        ActionMappingEx mapping = new ActionMappingEx();
        mapping.setName("formName");

        processor.processPopulate(req, res, form, mapping);

        //テスト結果確認
        assertEquals("companyId", form.getCompanyId());
        assertNull(req.getAttribute(RequestProcessorEx.SKIP_POPULATE));
    }

    /**
     * testProcessPopulate03。<br>
     * 
     * (正常系)<br>
     * 観点：A<br>
     * 
     * 入力値
     * req=パラメータ：companyId="companyId"<br>
     * res=not null<br>
     * form=not null<br>
     * mapping=not null<br>
     * request(SKIP_POPULATE属性)="formName"<br>
     * name="formName"<br>
     * 
     * 期待値
     * 戻り値:void<br>
     * SKIP_POPULATE=null<br>
     * form(フィールド値)=companyId=null<br>
     * 
     * リクエスト属性にSKIP_POPULATEで登録されたフォーム名と、
     * アクションマッピングのフォーム名が一致しているとき、
     * リクエストパラメータは反映されず、リクエスト属性の
     * SKIP_POPULATEは削除されない。<br>
     * 
     * @throws Exception 例外<br>
     */
    public void testProcessPopulate03() throws Exception {
        //テストデータ設定
        RequestProcessorEx processor = new RequestProcessorEx();

        // 擬似リクエスト・レスポンス
        MockHttpServletRequest req = new MockHttpServletRequest();
        req.setParameter("companyId", "companyId");
        req.setAttribute(RequestProcessorEx.SKIP_POPULATE, "formName");

        HttpServletResponse res = new MockHttpServletResponse();
        RequestProcessorEx_ActionFormStub01 form =
            new RequestProcessorEx_ActionFormStub01();

        ActionMappingEx mapping = new ActionMappingEx();
        mapping.setName("formName");

        processor.processPopulate(req, res, form, mapping);

        //テスト結果確認
        assertNull(form.getCompanyId());
        assertEquals(
            "formName",
            req.getAttribute(RequestProcessorEx.SKIP_POPULATE));
    }

    /**
     * testProcessPopulate04。<br>
     * 
     * (正常系)<br>
     * 観点：A<br>
     * 
     * 入力値
     * req=パラメータ：companyId="companyId"<br>
     * res=not null<br>
     * form=not null<br>
     * mapping=not null<br>
     * request(SKIP_POPULATE属性)="anotherName"<br>
     * name="formName"<br>
     * 
     * 期待値
     * 戻り値:void<br>
     * SKIP_POPULATE="anotherName"<br>
     * form(フィールド値)=companyId="companyId"<br>
     * 
     * リクエスト属性にSKIP_POPULATEで登録されたフォーム名と、
     * アクションマッピングのフォーム名が一致していないとき、
     * リクエストパラメータは反映され、リクエスト属性の
     * SKIP_POPULATEは削除される。<br>
     * 
     * @throws Exception 例外<br>
     */
    public void testProcessPopulate04() throws Exception {
        //テストデータ設定
        RequestProcessorEx processor = new RequestProcessorEx();

        // 擬似リクエスト・レスポンス
        MockHttpServletRequest req = new MockHttpServletRequest();
        req.setParameter("companyId", "companyId");
        req.setAttribute(RequestProcessorEx.SKIP_POPULATE, "anotherName");

        HttpServletResponse res = new MockHttpServletResponse();
        RequestProcessorEx_ActionFormStub01 form =
            new RequestProcessorEx_ActionFormStub01();

        ActionMappingEx mapping = new ActionMappingEx();
        mapping.setName("formName");

        processor.processPopulate(req, res, form, mapping);

        //テスト結果確認
        assertEquals("companyId", form.getCompanyId());
        assertNull(req.getAttribute(RequestProcessorEx.SKIP_POPULATE));
    }

    /**
     * testProcessPopulate05。<br>
     * 
     * (正常系)<br>
     * 観点：A<br>
     * 
     * 入力値
     * req=パラメータ：companyId="companyId"<br>
     * res=not null<br>
     * form=not null<br>
     * mapping=not null<br>
     * request(SKIP_POPULATE属性)=null<br>
     * name="formName"<br>
     * cancelPopulate=true<br>
     * 
     * 期待値
     * 戻り値:void<br>
     * SKIP_POPULATE=null<br>
     * form(フィールド値)=companyId=null<br>
     * 
     * ActionMappingExの属性cancelPopulateがtrueのとき、
     * リクエストパラメータは反映されず、
     * リクエスト属性のSKIP_POPULATEキーはnullのままである。<br>
     * 
     * @throws Exception 例外<br>
     */
    public void testProcessPopulate05() throws Exception {
        //テストデータ設定
        RequestProcessorEx processor = new RequestProcessorEx();

        // 擬似リクエスト・レスポンス
        MockHttpServletRequest req = new MockHttpServletRequest();
        req.setParameter("companyId", "companyId");
        req.setAttribute(RequestProcessorEx.SKIP_POPULATE, null);

        HttpServletResponse res = new MockHttpServletResponse();
        RequestProcessorEx_ActionFormStub01 form =
            new RequestProcessorEx_ActionFormStub01();

        ActionMappingEx mapping = new ActionMappingEx();
        mapping.setName("formName");
        mapping.setCancelPopulate(true);

        processor.processPopulate(req, res, form, mapping);

        //テスト結果確認
        assertNull(form.getCompanyId());
        assertNull(req.getAttribute(RequestProcessorEx.SKIP_POPULATE));
    }

    /**
     * testProcessActionForm01。<br>
     * 
     * (正常系)<br>
     * 観点：A<br>
     * 
     * 入力値
     * req=not null<br>
     * res=not null<br>
     * mapping=ActionMappingインスタンス<br>
     * scope="session"<br>
     * name="_formName"<br>
     * 
     * 期待値
     * 戻り値:form=ActionFormが返却される<br>
     * session="_anothorForm"が削除されないこと<br>
     * 
     * 引数のアクションマッピングがActionMappingEx
     * のインスタンスではない時、
     * super.processActionForm()が呼ばれていること。<br>
     */
    public void testProcessActionForm01() throws Exception {

        RequestProcessorEx processor = new RequestProcessorEx();
        MockHttpServletRequest req = new MockHttpServletRequest();
        MockHttpServletResponse res = new MockHttpServletResponse();
        MockHttpSession session = new MockHttpSession();
        
        //テストデータ設定
        // テスト遂行には、ModuleConfigを設定する必要がある。
        ModuleConfig config = new ModuleConfigImpl("");
        processor.init(null, config);

        // 擬似リクエスト・レスポンス
        session.setAttribute("_anotherForm", new DynaActionForm());

        // 擬似セッションをリクエストに登録
        req.setSession(session);

        // Strutsのアクションマッピングを用意
        ActionMapping normalMapping = new ActionMapping();
        normalMapping.setScope("session");
        normalMapping.setName("_formName");

        //テスト実行
        processor.processActionForm(req, res, normalMapping);

        //テスト結果確認
        assertNotNull(session.getAttribute("_anotherForm"));
    }

    /**
     * testProcessActionForm02。<br>
     * 
     * (正常系)<br>
     * 観点：A<br>
     * 
     * 入力値
     * req=not null<br>
     * res=not null<br>
     * mapping=ActionMappingExインスタンス<br>
     * scope=null<br>
     * name=null<br>
     * 
     * 期待値
     * 戻り値:form=ActionFormが返却される<br>
     * session="_anothorForm"が削除されないこと<br>
     * 
     * スコープ文字列がnullの時、super.processActionForm()が
     * 呼ばれていること。<br>
     */
    public void testProcessActionForm02() throws Exception {
        //テストデータ設定
        RequestProcessorEx processor = new RequestProcessorEx();
        MockHttpServletRequest req = new MockHttpServletRequest();
        MockHttpServletResponse res = new MockHttpServletResponse();
        MockHttpSession session = new MockHttpSession();
        ActionMappingEx mapping = new ActionMappingEx();

        // 擬似リクエスト・レスポンス
        session.setAttribute("_anotherForm", new DynaActionForm());

        // 擬似セッションをリクエストに登録
        req.setSession(session);

        mapping.setScope(null);
        mapping.setName(null);

        // ActionFormUtil.clearForm()に必要な設定(未設定)
        //mapping.setClearForm("_formName");

        //テスト実行
        processor.processActionForm(req, res, mapping);

        //テスト結果確認
        assertNotNull(session.getAttribute("_anotherForm"));
    }

    /**
     * testProcessActionForm03。<br>
     * 
     * (正常系)<br>
     * 観点：A<br>
     * 
     * 入力値
     * req=not null<br>
     * res=not null<br>
     * mapping=not null<br>
     * scope="※(request)"<br>
     * name=null<br>
     * 
     * 期待値
     * 戻り値:form=ActionFormが返却される<br>
     * session="_anothorForm"が削除されないこと<br>
     * 
     * スコープ文字列が"session"以外の文字列の時、
     * super.processActionForm()が呼ばれていること。<br>
     */
    public void testProcessActionForm03() throws Exception {
        //テストデータ設定
        RequestProcessorEx processor = new RequestProcessorEx();
        MockHttpServletRequest req = new MockHttpServletRequest();
        MockHttpServletResponse res = new MockHttpServletResponse();
        MockHttpSession session = new MockHttpSession();
        ActionMappingEx mapping = new ActionMappingEx();
        
        // 擬似リクエスト・レスポンス
        session.setAttribute("_anotherForm", new DynaActionForm());

        // 擬似セッションをリクエストに登録
        req.setSession(session);

        mapping.setScope("request");
        mapping.setName(null);

        // ActionFormUtil.clearForm()に必要な設定（未設定）
        // mapping.setClearForm("_formName");

        //テスト実行
        processor.processActionForm(req, res, mapping);

        //テスト結果確認
        assertNotNull(session.getAttribute("_anotherForm"));
    }

    /**
     * testProcessActionForm04。<br>
     * 
     * (正常系)<br>
     * 観点：A<br>
     * 
     * 入力値
     * req=not null<br>
     * res=not null<br>
     * mapping=not null<br>
     * scope="session"<br>
     * name=null<br>
     * 
     * 期待値
     * 戻り値:form=ActionFormが返却される<br>
     * session="_anothorForm"が削除されないこと<br>
     * 
     * フォーム名がnullの時、super.processActionForm()
     * が呼ばれていること。<br>
     */
    public void testProcessActionForm04() throws Exception {
        //テストデータ設定
        RequestProcessorEx processor = new RequestProcessorEx();
        MockHttpServletRequest req = new MockHttpServletRequest();
        MockHttpServletResponse res = new MockHttpServletResponse();
        MockHttpSession session = new MockHttpSession();
        ActionMappingEx mapping = new ActionMappingEx();

        session.setAttribute("_anotherForm", new DynaActionForm());

        // 擬似セッションをリクエストに登録
        req.setSession(session);

        mapping.setScope("session");
        mapping.setName(null);

        // ActionFormUtil.clearForm()に必要な設定（未設定）
        // mapping.setClearForm("_formName");

        //テスト実行
        processor.processActionForm(req, res, mapping);

        //テスト結果確認
        assertNotNull(session.getAttribute("_anotherForm"));
    }

    /**
     * testProcessActionForm05。<br>
     * 
     * (正常系)<br>
     * 観点：A<br>
     * 
     * 入力値
     * req=not null<br>
     * res=not null<br>
     * mapping=not null<br>
     * scope="session"<br>
     * name="※(formName)"<br>
     * 
     * 期待値
     * 戻り値:form=ActionFormが返却される<br>
     * session="_anothorForm"が削除されないこと<br>
     * 
     * フォーム名の先頭がアンダースコアではない時、
     * super.processActionForm()が呼ばれていること。<br>
     */
    public void testProcessActionForm05() throws Exception {
        //テストデータ設定
        RequestProcessorEx processor = new RequestProcessorEx();
        MockHttpServletRequest req = new MockHttpServletRequest();
        MockHttpServletResponse res = new MockHttpServletResponse();
        MockHttpSession session = new MockHttpSession();
        ActionMappingEx mapping = new ActionMappingEx();

        // テスト遂行には、ModuleConfigを設定する必要がある。
        ModuleConfig config = new ModuleConfigImpl("");
        processor.init(null, config);

        // 擬似リクエスト・レスポンス
        session.setAttribute("_anotherForm", new DynaActionForm());

        // 擬似セッションをリクエストに登録
        req.setSession(session);

        mapping.setScope("session");
        mapping.setName("formName");

        // ActionFormUtil.clearForm()に必要な設定（未設定）
        // mapping.setClearForm("_formName");

        //テスト実行
        processor.processActionForm(req, res, mapping);

        //テスト結果確認
        assertNotNull(session.getAttribute("_anotherForm"));
    }

    /**
     * testProcessActionForm06。<br>
     * 
     * (正常系)<br>
     * 観点：A<br>
     * 
     * 入力値
     * req=not null<br>
     * res=not null<br>
     * mapping=not null<br>
     * scope="session"<br>
     * name="_※(_formName)"<br>
     * clearForm=true<br>
     * 
     * 期待値
     * 戻り値:form=ActionFormが返却される<br>
     * session=_anothorFormが削除されること<br>
     * 
     * フォーム名の先頭がアンダースコアである時、
     * processActionFormEx()が呼ばれ、"_"付きのフォームが全て削除
     * されていること。<br>
     */
    public void testProcessActionForm06() throws Exception {

        //テストデータ設定
        RequestProcessorEx processor = new RequestProcessorEx();
        MockHttpServletRequest req = new MockHttpServletRequest();
        MockHttpServletResponse res = new MockHttpServletResponse();
        MockHttpSession session = new MockHttpSession();
        ActionMappingEx mapping = new ActionMappingEx();

        // テスト遂行には、ModuleConfigを設定する必要がある。
        ModuleConfig config = new ModuleConfigImpl("");
        processor.init(null, config);

        // 擬似リクエスト・レスポンス
        session.setAttribute("_formName", new DynaActionForm());
        session.setAttribute("_anotherForm", new DynaActionForm());

        // 擬似セッションをリクエストに登録
        req.setSession(session);

        mapping.setScope("session");
        mapping.setName("_formName");
        mapping.setClearForm(true);

        // ActionFormUtil.clearForm()に必要な設定
        mapping.setClearForm(true);

        //テスト実行
        processor.processActionForm(req, res, mapping);

        //テスト結果確認
        assertNull(session.getAttribute("_formName"));
        assertNull(session.getAttribute("_anotherForm"));
    }

    /**
     * testProcessActionForm07。<br>
     * 
     * (正常系)<br>
     * 観点：A<br>
     * 
     * 入力値
     * req=not null<br>
     * res=not null<br>
     * mapping=not null<br>
     * scope="session"<br>
     * name="_※(_formName)"<br>
     * clearForm=false<br>
     * 
     * 期待値
     * 戻り値:form=ActionFormが返却される<br>
     * session=_anothorFormが削除されていること<br>
     * 
     * フォーム名の先頭がアンダースコアである時、
     * processActionFormEx()が呼ばれ、"_"付きのフォームで、
     * 現在アクションマッピングで指定されているフォーム以外のものが削除
     * されていること。<br>
     */
    public void testProcessActionForm07() throws Exception {

        //テストデータ設定
        RequestProcessorEx processor = new RequestProcessorEx();
        MockHttpServletRequest req = new MockHttpServletRequest();
        MockHttpServletResponse res = new MockHttpServletResponse();
        MockHttpSession session = new MockHttpSession();
        ActionMappingEx mapping = new ActionMappingEx();

        // テスト遂行には、ModuleConfigを設定する必要がある。
        ModuleConfig config = new ModuleConfigImpl("");
        processor.init(null, config);

        // 擬似リクエスト・レスポンス
        session.setAttribute("_formName", new DynaActionForm());
        session.setAttribute("_anotherForm", new DynaActionForm());

        // 擬似セッションをリクエストに登録
        req.setSession(session);

        mapping.setScope("session");
        mapping.setName("_formName");
        mapping.setClearForm(true);

        // ActionFormUtil.clearForm()に必要な設定
        mapping.setClearForm(false);

        //テスト実行
        processor.processActionForm(req, res, mapping);

        //テスト結果確認
        //アクションマッピングに登録されているフォームは削除されないこと。
        assertNotNull(session.getAttribute("_formName"));
        assertNull(session.getAttribute("_anotherForm"));
    }
}
