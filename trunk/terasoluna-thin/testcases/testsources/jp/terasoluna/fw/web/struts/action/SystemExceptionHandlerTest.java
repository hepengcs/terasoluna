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

import java.util.Iterator;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

import jp.terasoluna.fw.exception.SystemException;
import jp.terasoluna.fw.util.ExceptionUtil;
import jp.terasoluna.fw.web.RequestUtil;
import jp.terasoluna.utlib.LogUTUtil;
import jp.terasoluna.utlib.MockHttpServletRequest;
import jp.terasoluna.utlib.MockHttpServletResponse;
import jp.terasoluna.utlib.MockHttpSession;
import jp.terasoluna.utlib.MockServletContext;
import jp.terasoluna.utlib.UTUtil;
import junit.framework.TestCase;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.config.ExceptionConfig;
import org.apache.struts.util.MessageResources;
import org.apache.struts.util.MessageResourcesFactory;
import org.apache.struts.util.PropertyMessageResources;
import org.apache.struts.util.PropertyMessageResourcesFactory;

/**
 * {@link jp.terasoluna.fw.web.struts.action.SystemExceptionHandler} クラスのブラックボックステスト。
 * 
 * <p>
 * <h4>【クラスの概要】</h4>
 * SystemException例外処理クラス。<br>
 * システム例外時のログ出力と エラー画面への遷移を行う。<br>
 * アクション実行中にシステム例外が発生したときは、エラー情報をログ出力した上で、当該アクションマッピングに定義されているシステムエラー画面に遷移する。
 * <p>
 * 
 * @see jp.terasoluna.fw.web.struts.action.SystemExceptionHandler
 */
public class SystemExceptionHandlerTest extends TestCase {

    /**
     * このテストケースを実行する為の
     * GUI アプリケーションを起動する。
     * 
     * @param args java コマンドに設定されたパラメータ
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(SystemExceptionHandlerTest.class);
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
    public SystemExceptionHandlerTest(String name) {
        super(name);
    }

    /**
     * testExecute01()
     * <br><br>
     * 
     * 正常系
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) ex:Exception<br>
     *         (引数) eConfig:ExceptionConfig<br>
     *               【pathフィールドに"/errorPath.do"が設定されている】<br>
     *         (引数) mapping:not null<br>
     *         (引数) form:SystemExceptionHandler_ActionFormStub01<br>
     *         (引数) request:not null<br>
     *         (引数) response:not null<br>
     *         (状態) bundle:null<br>
     *         
     * <br>
     * 期待値：(戻り値) ActionForward:フォワード先："/errorPath.do"<br>
     *         (状態変化) request:リクエスト属性：<br>
     *                    PageContext.EXCEPTION：null<br>
     *         
     * <br>
     * 引数の例外がSystemExceptionではなく、path属性が指定された時、path属性で指定されたパスがアクションフォワードに設定され、相対コンテキストルートがtrueに設定されること。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testExecute01() throws Exception {
        // 前処理
        
        // システム例外ハンドラ
        SystemExceptionHandler handler = new SystemExceptionHandler();

        // システム例外ではない例外
        Exception e = new Exception();
        
        // 例外設定情報
        ExceptionConfig eConfig = new ExceptionConfig();
        eConfig.setPath("/errorPath.do");
        eConfig.setKey("action.message.key");
        
        ActionMapping mapping = new ActionMapping();
        DynaActionForm form = new DynaActionForm();
        MockHttpServletRequest req = new MockHttpServletRequest();
        MockHttpServletResponse res = new MockHttpServletResponse();
        MockHttpSession session = new MockHttpSession();
        MockServletContext context = new MockServletContext();
       
        // サーブレットコンテキストをリクエストから参照する。
        session.setServletContext(context);
        req.setSession(session);     

        // テスト実施
        ActionForward forward  
            = handler.execute(e, eConfig, mapping, form, req, res);

        // 判定
        
        // 戻り値の確認
        assertEquals("/errorPath.do", forward.getPath());
        // リクエストの状態変化の確認
        assertNull(req.getAttribute(PageContext.EXCEPTION));

    }

    /**
     * testExecute02()
     * <br><br>
     * 
     * 正常系
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) ex:Exception<br>
     *         (引数) eConfig:not null<br>
     *         (引数) mapping:not null<br>
     *               【inputフィールドに"/errorInput.do"が設定されている】
     *         (引数) form:not null<br>
     *         (引数) request:not null<br>
     *         (引数) response:not null<br>
     *         (状態) bundle:null<br>
     *         
     * <br>
     * 期待値：(戻り値) ActionForward:フォワード先："/errorInput.do"<br>
     *         (状態変化) request:リクエスト属性：<br>
     *                    PageContext.EXCEPTIONで、<br>
     *                    例外が登録されていないこと。<br>
     *         
     * <br>
     * 引数の例外がSystemExceptionではなく、アクションマッピングのinput属性が指定された時、input属性で指定されたパスがアクションフォワードに設定され相対コンテキストルートがtrueに設定されること。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testExecute02() throws Exception {
        // 前処理
        SystemExceptionHandler handler = new SystemExceptionHandler();

        // システム例外ではない例外
        Exception e = new Exception();
        
        // 例外設定情報
        ExceptionConfig eConfig = new ExceptionConfig();
        eConfig.setKey("action.message.key");
        
        // アクションマッピングにInput属性を設定
        ActionMapping mapping = new ActionMapping();
        mapping.setInput("/errorInput.do");
        
        DynaActionForm form = new DynaActionForm();
        MockHttpServletRequest req = new MockHttpServletRequest();
        MockHttpServletResponse res = new MockHttpServletResponse();
        MockHttpSession session = new MockHttpSession();
        MockServletContext context = new MockServletContext();
        
        // サーブレットコンテキストをリクエストから参照する。
        session.setServletContext(context);
        req.setSession(session);     

        // テスト実施
        ActionForward forward  
            = handler.execute(e, eConfig, mapping, form, req, res);

        // 判定
        
        // 戻り値の確認
        assertEquals("/errorInput.do", forward.getPath());
        
        // リクエスト属性の状態変化の確認
        assertNull(req.getAttribute(PageContext.EXCEPTION));
    }

    /**
     * testExecute03()
     * <br><br>
     * 
     * 異常系
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) ex:SystemException<br>
     *         (引数) eConfig:ExceptionConfig<br>
     *               【pathフィールドに"/errorPath.do"が設定されている】<br>
     *         (引数) mapping:not null<br>
     *               【inputフィールドに"/errorInput.do"が設定されている】
     *         (引数) form:not null<br>
     *         (引数) request:not null<br>
     *         (引数) response:not null<br>
     *         (状態) bundle:null<br>
     *         (状態) resources:【リクエスト属性】<br>
     *                Globals.MESSAGES_KEYで取得すると以下のMessageResourceを含むMessageResourcesが返る<br>
     *                MessageResources｛<br>
     *                 SystemExceptionHandlerTest.error.message="例外メッセージ"<br>
     *                }<br>
     *         
     * <br>
     * 期待値：(戻り値) ActionForward:フォワード先："/errorPath.do"<br>
     *         (状態変化) request:リクエスト属性：<br>
     *                    PageContext.EXCEPTION：SystemException<br>
     *         (状態変化) se:エラーメッセージ："例外メッセージ"<br>
     *         (状態変化) ログ:【エラーログ】<br>
     *                    ＜メッセージ＞<br>
     *                    "sessionHash = " + sessionHash<br>
     *                    ExceptionUtil.getStackTrace(se)<br>
     *         
     * <br>
     * 例外設定情報のpath属性と、アクションマッピングのinput属性が両方指定されている時、path属性が優先されること。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    @SuppressWarnings("static-access")
    public void testExecute03() throws Exception {
        // 前処理

        // システム例外ハンドラ
        SystemExceptionHandler handler = new SystemExceptionHandler();

        // システム例外
        SystemException e = new SystemException(new Exception(),
             "SystemExceptionHandlerTest.error.message");
        
        // 例外設定情報
        ExceptionConfig eConfig = new ExceptionConfig();
        eConfig.setPath("/errorPath.do");
        eConfig.setKey("action.message.key");
        
        // アクションマッピングにInput属性を設定
        ActionMapping mapping = new ActionMapping();
        mapping.setInput("/errorInput.do");
        
        DynaActionForm form = new DynaActionForm();
        MockHttpServletRequest req = new MockHttpServletRequest();
        MockHttpServletResponse res = new MockHttpServletResponse();
        MockHttpSession session = new MockHttpSession();
        MockServletContext context = new MockServletContext();
        
        // MessageResourcesの設定
        MessageResourcesFactory factory
            = PropertyMessageResourcesFactory.createFactory();
        PropertyMessageResources requestResources 
            = new PropertyMessageResources(factory,
                SystemExceptionHandler.class
                .getPackage().getName().replace('.', '/')
                + "/SystemExceptionHandler_MessageResources01");

        // リクエストにMessageResources登録
        req.setAttribute(Globals.MESSAGES_KEY, requestResources);
        
        // サーブレットコンテキストをリクエストから参照する。
        session.setServletContext(context);
        req.setSession(session);     

        // テスト実施
        ActionForward forward 
            = handler.execute(e, eConfig, mapping, form, req, res);

        // 判定
        
        // 戻り値の確認
        assertEquals("/errorPath.do", forward.getPath());
        
        // リクエストの状態変化の確認
        assertSame(e, req.getAttribute(PageContext.EXCEPTION));
        
        // 例外の状態変化の確認
        assertEquals("例外メッセージ", UTUtil.getPrivateField(e, "message"));
        
        // ActionMessagesの状態変化の確認
        ActionMessages messages
            = (ActionMessages) req.getAttribute(Globals.ERROR_KEY);
        assertEquals(1, messages.size());
        Iterator it = messages.get();
        while (it.hasNext()) {
            ActionMessage actionMessage = (ActionMessage) it.next();
            assertEquals("action.message.key", actionMessage.getKey());
        }
        
        // ログ出力の確認
        String sessionHash = RequestUtil.getSessionHash(req);
        assertTrue(LogUTUtil
            .checkError("sessionHash = " + sessionHash));
        assertTrue(LogUTUtil
            .checkError(ExceptionUtil.getStackTrace(e)));
    }

    /**
     * testExecute04()
     * <br><br>
     * 
     * 異常系
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) ex:SystemException<br>
     *         (引数) eConfig:not null<br>
     *         (引数) mapping:not null<br>
     *         (引数) form:not null<br>
     *         (引数) request:not null<br>
     *         (引数) response:not null<br>
     *         (状態) bundle:null<br>
     *         (状態) resources:【リクエスト属性】<br>
     *                Globals.MESSAGES_KEYで取得すると以下のMessageResourceを含むMessageResourcesが返る<br>
     *                MessageResources｛<br>
     *                 SystemExceptionHandlerTest.error.message="例外メッセージ"<br>
     *                ｝<br>
     *         
     * <br>
     * 期待値：(戻り値) ActionForward:フォワード先：null<br>
     *         (状態変化) request:リクエスト属性：<br>
     *                    PageContext.EXCEPTIONで、<br>
     *                    SystemExceptionが登録されていること。<br>
     *         (状態変化) se:エラーメッセージ："例外メッセージ"<br>
     *         (状態変化) ログ:【エラーログ】<br>
     *                    ＜メッセージ＞<br>
     *                    "sessionHash = " + sessionHash<br>
     *                    ExceptionUtil.getStackTrace(se)<br>
     *         
     * <br>
     * 例外設定情報のpath属性と、アクションマッピングのinput属性が両方nullの時、アクションフォワードの遷移先がnullであること。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    @SuppressWarnings("static-access")
    public void testExecute04() throws Exception {
        // 前処理

        // システム例外ハンドラ
        SystemExceptionHandler handler = new SystemExceptionHandler();

        // システム例外
        SystemException e = new SystemException(new Exception(),
             "SystemExceptionHandlerTest.error.message");
        
        ExceptionConfig eConfig = new ExceptionConfig();
        eConfig.setKey("action.message.key");
        
        ActionMapping mapping = new ActionMapping();
        DynaActionForm form = new DynaActionForm();
        MockHttpServletRequest req = new MockHttpServletRequest();
        MockHttpServletResponse res = new MockHttpServletResponse();
        MockHttpSession session = new MockHttpSession();
        MockServletContext context = new MockServletContext();
        
        // MessageResources
        MessageResourcesFactory factory
            = PropertyMessageResourcesFactory.createFactory();
        PropertyMessageResources requestResources 
            = new PropertyMessageResources(factory,
                SystemExceptionHandler.class
                .getPackage().getName().replace('.', '/')
                + "/SystemExceptionHandler_MessageResources01");

        // リクエストにMessageResourcesを設定
        req.setAttribute(Globals.MESSAGES_KEY, requestResources);
        
        // サーブレットコンテキストをリクエストから参照する。
        session.setServletContext(context);
        req.setSession(session);     

        // テスト実施
        ActionForward forward  
            = handler.execute(e, eConfig, mapping, form, req, res);

        // 判定
        
        // 戻り値の確認
        assertNull(forward.getPath());
        
        // リクエスト属性の状態変化の確認
        assertSame(e, req.getAttribute(PageContext.EXCEPTION));

        // システム例外の状態変化の確認。
        assertEquals("例外メッセージ", UTUtil.getPrivateField(e, "message"));
        
        // ActionMessagesの状態変化の確認
        ActionMessages messages
            = (ActionMessages) req.getAttribute(Globals.ERROR_KEY);
        assertEquals(1, messages.size());
        Iterator it = messages.get();
        while (it.hasNext()) {
            ActionMessage actionMessage = (ActionMessage) it.next();
            assertEquals("action.message.key", actionMessage.getKey());
        }
        
        // ログ出力の確認
        String sessionHash = RequestUtil.getSessionHash(req);
        assertTrue(LogUTUtil
            .checkError("sessionHash = " + sessionHash));
        assertTrue(LogUTUtil
            .checkError(ExceptionUtil.getStackTrace(e)));
    }

    /**
     * testExecute05()
     * <br><br>
     * 
     * 正常系
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) ex:SystemException<br>
     *         (引数) eConfig:ExceptionConfig<br>
     *               【pathフィールドに"/errorPath.do"が設定されている】<br>
     *         (引数) mapping:MockActionMapping<br>
     *         (引数) form:not null<br>
     *         (引数) request:not null<br>
     *         (引数) response:not null<br>
     *         (状態) bundle:Globals.MESSAGE_KEY<br>
     *         (状態) resources:【リクエスト属性】<br>
     *                Globals.MESSAGES_KEYで取得すると以下のMessageResourceを含むMessageResourcesが返る<br>
     *                MessageResources｛<br>
     *                 SystemExceptionHandlerTest.error.message ="例外メッセージ"<br>
     *                ｝<br>
     *         
     * <br>
     * 期待値：(戻り値) ActionForward:フォワード先："/errorPath.do"<br>
     *         (状態変化) request:リクエスト属性：<br>
     *                    PageContext.EXCEPTIONで、<br>
     *                    SystemExceptionが登録されていること。<br>
     *         (状態変化) se:エラーメッセージ："例外メッセージ"<br>
     *                    が設定されていること。<br>
     *         (状態変化) ログ:【エラーログ】<br>
     *                    ＜メッセージ＞<br>
     *                    "sessionHash = " + sessionHash<br>
     *                    ExceptionUtil.getStackTrace(se)<br>
     *         
     * <br>
     * 例外設定情報のbundle属性がGlobals.MESSAGE_KEYの時、キーがGlobals.MESSAGE_KEYであるMessageResourcesが用いられること。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    @SuppressWarnings("static-access")
    public void testExecute05() throws Exception {
        // 前処理

        // システム例外ハンドラ
        SystemExceptionHandler handler = new SystemExceptionHandler();

        // システム例外
        SystemException e = new SystemException(new Exception(),
             "SystemExceptionHandlerTest.error.message");
        
        // 例外設定情報
        ExceptionConfig eConfig = new ExceptionConfig();
        eConfig.setPath("/errorPath.do");
        // アクションメッセージのキーを指定
        eConfig.setKey("action.message.key");
        // メッセージリソースへのバンドルキーを指定
        eConfig.setBundle(Globals.MESSAGES_KEY);

        ActionMapping mapping = new ActionMapping();
        DynaActionForm form = new DynaActionForm();
        MockHttpServletRequest req = new MockHttpServletRequest();
        MockHttpServletResponse res = new MockHttpServletResponse();
        MockHttpSession session = new MockHttpSession();
        MockServletContext context = new MockServletContext();

        // MessageResources
        MessageResourcesFactory factory
            = PropertyMessageResourcesFactory.createFactory();
        PropertyMessageResources requestResources 
            = new PropertyMessageResources(factory,
                SystemExceptionHandler.class
                .getPackage().getName().replace('.', '/')
                + "/SystemExceptionHandler_MessageResources01");

        // リクエストにMessageResources登録
        req.setAttribute(Globals.MESSAGES_KEY, requestResources);
        
        // サーブレットコンテキストをリクエストから参照する。
        session.setServletContext(context);
        req.setSession(session);     

        // テスト実施
        ActionForward forward
            = handler.execute(e, eConfig, mapping, form, req, res);

        // 判定
        
        // 戻り値の確認
        assertEquals("/errorPath.do", forward.getPath());
        
        // リクエスト属性の状態変化の確認
        assertSame(e, req.getAttribute(PageContext.EXCEPTION));

        // システム例外の状態変化の確認
        assertEquals("例外メッセージ", UTUtil.getPrivateField(e, "message"));
        
        
        // ActionMessagesの状態変化の確認
        ActionMessages messages
            = (ActionMessages) req.getAttribute(Globals.ERROR_KEY);
        assertEquals(1, messages.size());
        Iterator it = messages.get();
        while (it.hasNext()) {
            ActionMessage actionMessage = (ActionMessage) it.next();
            assertEquals("action.message.key", actionMessage.getKey());
        }
        
        // ログ出力の確認
        String sessionHash = RequestUtil.getSessionHash(req);
        assertTrue(LogUTUtil
            .checkError("sessionHash = " + sessionHash));
        assertTrue(LogUTUtil
            .checkError(ExceptionUtil.getStackTrace(e)));
    }

    /**
     * testExecute06()
     * <br><br>
     * 
     * 正常系
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) ex:SystemException<br>
     *         (引数) eConfig:ExceptionConfig<br>
     *               【pathフィールドに"/errorPath.do"が設定されている】<br>
     *         (引数) mapping:MockActionMapping<br>
     *         (引数) form:not null<br>
     *         (引数) request:not null<br>
     *         (引数) response:not null<br>
     *         (状態) bundle:anotherKey<br>
     *         (状態) resources:【リクエスト属性】<br>
     *               Globals.MESSAGES_KEYで取得すると以下のMessageResourceを含むMessageResourcesが返る<br>
     *               MessageResources｛<br>
     *                SystemExceptionHandlerTest.error.message ="例外メッセージ"<br>
     *               ｝<br>
     *               anotherKeyで取得してもnullが返ってくる<br>
     *               【サーブレットコンテキスト属性】<br>
     *               anotherKeyで取得すると以下のMessageResourceを含むMessageResourcesが返る<br>
     *               MessageResources｛<br>
     *                SystemExceptionHandlerTest.error.message ="サーブレット例外メッセージ"<br>
     *               ｝<br>
     * <br>
     * 期待値：(戻り値) ActionForward:フォワード先："/errorPath.do"<br>
     *         (状態変化) request:リクエスト属性：<br>
     *                    PageContext.EXCEPTION:<br>
     *                    SystemExceptionが登録されていること。<br>
     *         (状態変化) se:エラーメッセージ："サーブレット例外メッセージ"<br>
     *         (状態変化) ログ:【エラーログ】<br>
     *                    ＜メッセージ＞<br>
     *                    "sessionHash = " + sessionHash<br>
     *                    ExceptionUtil.getStackTrace(se)<br>
     *         
     * <br>
     * 例外設定情報のbundle属性が、サーブレットコンテキストのキーであるとき、サーブレットコンテキストに格納されたメッセージリソースが<br>
     * 用いられること。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    @SuppressWarnings("static-access")
    public void testExecute06() throws Exception {
        // 前処理
        
        // システム例外ハンドラ
        SystemExceptionHandler handler = new SystemExceptionHandler();

        // システム例外
        SystemException e = new SystemException(new Exception(),
             "SystemExceptionHandlerTest.error.message");
        
        // 例外設定情報
        ExceptionConfig eConfig = new ExceptionConfig();
        eConfig.setPath("/errorPath.do");
        // メッセージリソースへのバンドルキーを指定
        eConfig.setBundle("anotherKey");
        // アクションメッセージのキー
        eConfig.setKey("action.message.key");

        ActionMapping mapping = new ActionMapping();
        DynaActionForm form = new DynaActionForm();
        MockHttpServletRequest req = new MockHttpServletRequest();
        MockHttpServletResponse res = new MockHttpServletResponse();
        MockHttpSession session = new MockHttpSession();
        MockServletContext context = new MockServletContext();

        // MessageResources
        MessageResourcesFactory factory
            = PropertyMessageResourcesFactory.createFactory();
        PropertyMessageResources requestResources 
            = new PropertyMessageResources(factory,
                SystemExceptionHandler.class
                .getPackage().getName().replace('.', '/')
                + "/SystemExceptionHandler_MessageResources01");
        PropertyMessageResources servletResources 
            = new PropertyMessageResources(factory,
                SystemExceptionHandler.class
                .getPackage().getName().replace('.', '/')
                + "/SystemExceptionHandler_MessageResources02");

        // サーブレットコンテキストにMessageResources登録
        context.setAttribute("anotherKey", servletResources);

        // リクエストにメッセージリソース登録
        req.setAttribute(Globals.MESSAGES_KEY, requestResources);
        
        // サーブレットコンテキストをリクエストから参照する。
        session.setServletContext(context);
        req.setSession(session);     

        // テスト実施
        ActionForward forward
            = handler.execute(e, eConfig, mapping, form, req, res);

        // 判定
        
        // 戻り値の確認
        assertEquals("/errorPath.do", forward.getPath());
        
        // リクエスト属性の状態変化の確認
        assertSame(e, req.getAttribute(PageContext.EXCEPTION));
        
        // ActionMessagesの状態変化の確認
        ActionMessages messages
            = (ActionMessages) req.getAttribute(Globals.ERROR_KEY);
        assertEquals(1, messages.size());
        Iterator it = messages.get();
        while (it.hasNext()) {
            ActionMessage actionMessage = (ActionMessage) it.next();
            assertEquals("action.message.key", actionMessage.getKey());
        }
        
        // システム例外の状態変化の確認
        assertEquals("サーブレット例外メッセージ", 
            UTUtil.getPrivateField(e, "message"));
        
        // ログ出力の確認
        String sessionHash = RequestUtil.getSessionHash(req);
        assertTrue(LogUTUtil
            .checkError("sessionHash = " + sessionHash));
        assertTrue(LogUTUtil
            .checkError(ExceptionUtil.getStackTrace(e)));
        
    }

    /**
     * testExecute07()
     * <br><br>
     * 
     * 正常系
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) ex:SystemException<br>
     *         (引数) eConfig:ExceptionConfig<br>
     *               【pathフィールドに"/errorPath.do"が設定されている】<br>
     *         (引数) mapping:MockActionMapping<br>
     *         (引数) form:not null<br>
     *         (引数) request:not null<br>
     *         (引数) response:not null<br>
     *         (状態) bundle:null<br>
     *         (状態) resources:【リクエスト属性】<br>
     *               Globals.MESSAGES_KEYで取得してもnullが返ってくる<br>
     *               【サーブレットコンテキスト属性】<br>
     *               Globals.MESSAGES_KEYで取得してもnullが返ってくる<br>
     * <br>
     * 期待値：(戻り値) ActionForward:フォワード先："/errorPath.do"<br>
     *         (状態変化) request:リクエスト属性：<br>
     *                    PageContext.EXCEPTIONで、<br>
     *                    SystemExceptionが登録されていること。<br>
     *         (状態変化) se:エラーメッセージ："SystemExceptionHandlerTest.error.message"<br>
     *         (状態変化) ログ:【エラーログ】<br>
     *                    ＜メッセージ＞<br>
     *                    "sessionHash = " + sessionHash<br>
     *                    ExceptionUtil.getStackTrace(se)<br>
     *         
     * <br>
     * メッセージリソースが取得できない時、エラーコードがメッセージの代わりに設定されること。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testExecute07() throws Exception {
        // 前処理
        
        // システム例外ハンドラ
        SystemExceptionHandler handler = new SystemExceptionHandler();

        // システム例外
        SystemException e = new SystemException(new Exception(),
             "SystemExceptionHandlerTest.error.message");
        
        // 例外設定情報
        ExceptionConfig eConfig = new ExceptionConfig();
        eConfig.setPath("/errorPath.do");
        eConfig.setKey("action.message.key");
        
        ActionMapping mapping = new ActionMapping();
        DynaActionForm form = new DynaActionForm();
        MockHttpServletRequest req = new MockHttpServletRequest();
        MockHttpServletResponse res = new MockHttpServletResponse();
        MockHttpSession session = new MockHttpSession();
        MockServletContext context = new MockServletContext();

        // メッセージリソースは設定しない
        
        // サーブレットコンテキストをリクエストから参照する。
        session.setServletContext(context);
        req.setSession(session);     

        // テスト実施
        ActionForward forward  
            = handler.execute(e, eConfig, mapping, form, req, res);

        // 判定
        
        // 戻り値の確認
        assertEquals("/errorPath.do", forward.getPath());
        
        // リクエスト属性の状態変化の確認
        assertSame(e, req.getAttribute(PageContext.EXCEPTION));

        // システム例外の状態変化の確認
        assertEquals("SystemExceptionHandlerTest.error.message",
            UTUtil.getPrivateField(e, "message"));
        
        // ActionMessagesの状態変化の確認
        ActionMessages messages
            = (ActionMessages) req.getAttribute(Globals.ERROR_KEY);
        assertEquals(1, messages.size());
        Iterator it = messages.get();
        while (it.hasNext()) {
            ActionMessage actionMessage = (ActionMessage) it.next();
            assertEquals("action.message.key", actionMessage.getKey());
            assertFalse(actionMessage.isResource());
        }
        
        // ログ出力の確認
        String sessionHash = RequestUtil.getSessionHash(req);
        assertTrue(LogUTUtil
            .checkError("sessionHash = " + sessionHash));
        assertTrue(LogUTUtil
            .checkError(ExceptionUtil.getStackTrace(e)));
    }

    /**
     * testExecute08()
     * <br><br>
     * 
     * 正常系
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) ex:SystemException<br>
     *         (引数) eConfig:ExceptionConfig<br>
     *               【pathフィールドに"/errorPath.do"が設定されている】<br>
     *         (引数) mapping:MockActionMapping<br>
     *         (引数) form:not null<br>
     *         (引数) request:not null<br>
     *         (引数) response:not null<br>
     *         (状態) bundle:null<br>
     *         (状態) resources:【リクエスト属性】<br>
     *                Globals.MESSAGES_KEYで取得すると以下のMessageResourceを含むMessageResourcesが返る<br>
     *                MessageResources｛<br>
     *                 "aaaaa"というメッセージキーで登録されていない<br>
     *                ｝<br>
     *         
     * <br>
     * 期待値：(戻り値) ActionForward:フォワード先："/errorPath.do"<br>
     *         (状態変化) request:リクエスト属性：<br>
     *                    PageContext.EXCEPTIONで、<br>
     *                    SystemExceptionが登録されていること。<br>
     *         (状態変化) se:エラーメッセージ：(nullであること)<br>
     *         (状態変化) ログ:【エラーログ】<br>
     *                    ＜メッセージ＞<br>
     *                    "sessionHash = " + sessionHash<br>
     *                    ExceptionUtil.getStackTrace(se)<br>
     *         
     * <br>
     * メッセージ文言が取得できない時、nullが設定されること。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    @SuppressWarnings("static-access")
    public void testExecute08() throws Exception {
        // 前処理
        
        // システム例外ハンドラ
        SystemExceptionHandler handler = new SystemExceptionHandler();

        // システム例外
        SystemException e = new SystemException(new Exception(),
             "aaaaa");
        
        // 例外設定情報
        ExceptionConfig eConfig = new ExceptionConfig();
        eConfig.setPath("/errorPath.do");
        eConfig.setKey("action.message.key");

        ActionMapping mapping = new ActionMapping();
        DynaActionForm form = new DynaActionForm();
        MockHttpServletRequest req = new MockHttpServletRequest();
        MockHttpServletResponse res = new MockHttpServletResponse();
        MockHttpSession session = new MockHttpSession();
        MockServletContext context = new MockServletContext();

        // MessageResources. returnNullをtrueにしているのに注意
        MessageResourcesFactory factory
            = PropertyMessageResourcesFactory.createFactory();
        PropertyMessageResources requestResources 
            = new PropertyMessageResources(factory,
                SystemExceptionHandler.class
                .getPackage().getName().replace('.', '/')
                + "/SystemExceptionHandler_MessageResources01",
                true); 
        
        // リクエストにMessageResources登録
        req.setAttribute(Globals.MESSAGES_KEY, requestResources);
        
        // サーブレットコンテキストをリクエストから参照する。
        session.setServletContext(context);
        req.setSession(session);

        // テスト実施
        ActionForward forward
            = handler.execute(e, eConfig, mapping, form, req, res);

        // 判定
        
        // 戻り値の確認
        assertEquals("/errorPath.do", forward.getPath());
        
        // リクエスト属性の状態変化の確認
        assertSame(e, req.getAttribute(PageContext.EXCEPTION));

        // システム例外の状態変化の確認
        // getMessage()は単なるgetterではなく、nullのときはエラーコードを返却するので注意
        assertNull(UTUtil.getPrivateField(e, "message"));
        
        // ActionMessagesの状態変化の確認
        ActionMessages messages
            = (ActionMessages) req.getAttribute(Globals.ERROR_KEY);
        assertEquals(1, messages.size());
        Iterator it = messages.get();
        while (it.hasNext()) {
            ActionMessage actionMessage = (ActionMessage) it.next();
            assertEquals("action.message.key", actionMessage.getKey());
        }
        
        // ログ出力の確認
        String sessionHash = RequestUtil.getSessionHash(req);
        assertTrue(LogUTUtil
            .checkError("sessionHash = " + sessionHash));
        assertTrue(LogUTUtil
            .checkError(ExceptionUtil.getStackTrace(e)));
    }

    /**
     * testExecute09()
     * <br><br>
     * 
     * 正常系
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) ex:SystemException<br>
     *         (引数) eConfig:ExceptionConfigEx<br>
     *               【pathフィールドに"/errorPath.do"が設定されている】<br>
     *         (引数) mapping:MockActionMapping<br>
     *         (引数) form:not null<br>
     *         (引数) request:not null<br>
     *         (引数) response:not null<br>
     *         (状態) bundle:null<br>
     *         (状態) resources:【リクエスト属性】<br>
     *                Globals.MESSAGES_KEYで取得すると以下のMessageResourceを含むMessageResourcesが返る<br>
     *                MessageResources｛<br>
     *                 SystemExceptionHandlerTest.error.message.null = null<br>
     *                ｝<br>
     *         
     * <br>
     * 期待値：(戻り値) ActionForward:フォワード先："/errorPath.do"<br>
     *                  例外設定情報のモジュール：アクションパスのモジュール<br>
     *         (状態変化) request:リクエスト属性：<br>
     *                    PageContext.EXCEPTIONで、<br>
     *                    SystemExceptionが登録されていること。<br>
     *         (状態変化) se:エラーメッセージ："error.msg"<br>
     *         (状態変化) ログ:【エラーログ】<br>
     *                    ＜メッセージ＞<br>
     *                    "sessionHash = " + sessionHash<br>
     *                    ExceptionUtil.getStackTrace(se)<br>
     *         
     * <br>
     * 例外設定情報がExceptionConfigExの場合、例外設定情報のモジュールがアクションパスのモジュールに設定すること。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    @SuppressWarnings("static-access")
    public void testExecute09() throws Exception {
        // 前処理

        // システム例外ハンドラ
        SystemExceptionHandler handler = new SystemExceptionHandler();

        // システム例外
        SystemException e = new SystemException(new Exception(),
             "SystemExceptionHandlerTest.error.message");
        
        // 例外設定情報
        ExceptionConfigEx eConfig = new ExceptionConfigEx();
        eConfig.setModule("error");
        eConfig.setPath("/errorPath.do");
        eConfig.setKey("action.message.key");
        
        ActionMapping mapping = new ActionMapping();
        DynaActionForm form = new DynaActionForm();
        MockHttpServletRequest req = new MockHttpServletRequest();
        MockHttpServletResponse res = new MockHttpServletResponse();
        MockHttpSession session = new MockHttpSession();
        MockServletContext context = new MockServletContext();

        // MessageResources
        MessageResourcesFactory factory
            = PropertyMessageResourcesFactory.createFactory();
        PropertyMessageResources requestResources 
        = new PropertyMessageResources(factory,
            SystemExceptionHandler.class
            .getPackage().getName().replace('.', '/')
            + "/SystemExceptionHandler_MessageResources01");
        
        // リクエストにMessageResources登録
        req.setAttribute(Globals.MESSAGES_KEY, requestResources);

        // サーブレットコンテキストをリクエストから参照する。
        session.setServletContext(context);
        req.setSession(session);     

        // テスト実施
        ActionForward forward
            = handler.execute(e, eConfig, mapping, form, req, res);

        // 判定
        
        // 戻り値の確認
        assertEquals("/errorPath.do", forward.getPath());
        assertEquals("error", forward.getModule());
        
        // リクエスト属性の状態変化の確認
        assertSame(e, req.getAttribute(PageContext.EXCEPTION));

        // システム例外の状態変化の確認
        assertEquals("例外メッセージ",UTUtil.getPrivateField(e, "message"));
        
        // ActionMessagesの状態変化の確認
        ActionMessages messages
            = (ActionMessages) req.getAttribute(Globals.ERROR_KEY);
        assertEquals(1, messages.size());
        Iterator it = messages.get();
        while (it.hasNext()) {
            ActionMessage actionMessage = (ActionMessage) it.next();
            assertEquals("action.message.key", actionMessage.getKey());
        }
        
        // ログ出力の確認
        String sessionHash = RequestUtil.getSessionHash(req);
        assertTrue(LogUTUtil
            .checkError("sessionHash = " + sessionHash));
        assertTrue(LogUTUtil
            .checkError(ExceptionUtil.getStackTrace(e)));
    }
    
    
    
    /**
     * testExecute10()
     * <br><br>
     * 
     * 正常系
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) ex:SystemException<br>
     *         (引数) eConfig:ExceptionConfig<br>
     *               【pathフィールドに"/errorPath.do"が設定されている】<br>
     *         (引数) mapping:MockActionMapping<br>
     *         (引数) form:not null<br>
     *         (引数) request:not null<br>
     *         (引数) response:not null<br>
     *         (状態) bundle:null<br>
     *         (状態) resources:【リクエスト属性】<br>
     *                Globals.MESSAGES_KEYで取得すると以下のMessageResourceを含むMessageResourcesが返る<br>
     *                MessageResources｛<br>
     *                 SystemExceptionHandlerTest.error.message.empty
     *                ｝<br>
     *         
     * <br>
     * 期待値：(戻り値) ActionForward:フォワード先："/errorPath.do"<br>
     *         (状態変化) request:リクエスト属性：<br>
     *                    PageContext.EXCEPTIONで、<br>
     *                    SystemExceptionが登録されていること。<br>
     *         (状態変化) se:エラーメッセージ：""<br>
     *         (状態変化) ログ:【エラーログ】<br>
     *                    ＜メッセージ＞<br>
     *                    "sessionHash = " + sessionHash<br>
     *                    ExceptionUtil.getStackTrace(se)<br>
     *         
     * <br>
     * メッセージ文言が空文字列の時、空文字列が設定されること。<br>
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    @SuppressWarnings("static-access")
    public void testExecute10() throws Exception {
        // 前処理
        
        // システム例外ハンドラ
        SystemExceptionHandler handler = new SystemExceptionHandler();

        // システム例外
        SystemException e = new SystemException(new Exception(),
             "SystemExceptionHandlerTest.error.message.empty");
        
        // 例外設定情報
        ExceptionConfig eConfig = new ExceptionConfig();
        eConfig.setPath("/errorPath.do");
        eConfig.setKey("action.message.key");

        ActionMapping mapping = new ActionMapping();
        DynaActionForm form = new DynaActionForm();
        MockHttpServletRequest req = new MockHttpServletRequest();
        MockHttpServletResponse res = new MockHttpServletResponse();
        MockHttpSession session = new MockHttpSession();
        MockServletContext context = new MockServletContext();

        // MessageResources
        MessageResourcesFactory factory
            = PropertyMessageResourcesFactory.createFactory();
        PropertyMessageResources requestResources 
            = new PropertyMessageResources(factory,
                SystemExceptionHandler.class
                .getPackage().getName().replace('.', '/')
                + "/SystemExceptionHandler_MessageResources01");
        
        // リクエストにMessageResources登録
        req.setAttribute(Globals.MESSAGES_KEY, requestResources);
        
        // サーブレットコンテキストをリクエストから参照する。
        session.setServletContext(context);
        req.setSession(session);     

        // テスト実施
        ActionForward forward  
            = handler.execute(e, eConfig, mapping, form, req, res);

        // 判定
        
        // 戻り値の確認
        assertEquals("/errorPath.do", forward.getPath());
        
        // リクエスト属性の状態変化の確認
        assertSame(e, req.getAttribute(PageContext.EXCEPTION));

        // システム例外の状態変化の確認
        assertEquals("", UTUtil.getPrivateField(e, "message"));
        
        // ActionMessagesの状態変化の確認
        ActionMessages messages
            = (ActionMessages) req.getAttribute(Globals.ERROR_KEY);
        assertEquals(1, messages.size());
        Iterator it = messages.get();
        while (it.hasNext()) {
            ActionMessage actionMessage = (ActionMessage) it.next();
            assertEquals("action.message.key", actionMessage.getKey());
        }
        
        // ログ出力の確認
        String sessionHash = RequestUtil.getSessionHash(req);
        assertTrue(LogUTUtil
            .checkError("sessionHash = " + sessionHash));
        assertTrue(LogUTUtil
            .checkError(ExceptionUtil.getStackTrace(e)));
    }
    

    /**
     * testGetErrorMessage01()
     * <br><br>
     * 
     * 正常系
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) req:not null<br>
     *         (引数) se:not null<br>
     *         (状態) errorCode:null<br>
     *         
     * <br>
     * 期待値：(戻り値) String:null<br>
     *         
     * <br>
     * エラーコードがnullの時、メッセージがnullで返却されること。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    @SuppressWarnings("static-access")
    public void testGetErrorMessage01() throws Exception {
        // 前処理

        // システム例外ハンドラ
        SystemExceptionHandler handler = new SystemExceptionHandler();

        // システム例外(エラーコードnull)
        SystemException e 
            = new SystemException(new Exception(), null);

        MockHttpServletRequest req = new MockHttpServletRequest();
        
        // MessageResources
        MessageResourcesFactory factory
            = PropertyMessageResourcesFactory.createFactory();
        PropertyMessageResources requestResources 
            = new PropertyMessageResources(factory,
                SystemExceptionHandler.class
                .getPackage().getName().replace('.', '/')
                + "/SystemExceptionHandler_MessageResources01");

        // 呼び出し準備
        Class[] clazz = {HttpServletRequest.class,
                         SystemException.class,
                         MessageResources.class};
        Object[] args = {req, e, requestResources};

        // テスト実施
        Object result 
            = UTUtil.invokePrivate(handler, "getErrorMessage", clazz, args);

        // 判定
        assertNull(result);
    }

    /**
     * testGetErrorMessage02()
     * <br><br>
     * 
     * 正常系
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) req:not null<br>
     *         (引数) se:not null<br>
     *         (引数) resource:key="{0}デフォルトメッセージ"<br>
     *                en_US.key="{0}message"<br>
     *         (状態) locale:null<br>
     *         (状態) errorCode:"key"<br>
     *         (状態) option:null<br>
     *         
     * <br>
     * 期待値：(戻り値) String:"{0}デフォルトメッセージ"<br>
     *         
     * <br>
     * ロケール、置換文字列がnullである時、デフォルトロケールのメッセージが置換されずに出力されること。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    @SuppressWarnings("static-access")
    public void testGetErrorMessage02() throws Exception {
        // 前処理

        // システム例外ハンドラ
        SystemExceptionHandler handler = new SystemExceptionHandler();

        // システム例外(エラーコードnull)
        SystemException e = new SystemException(new Exception(),
             "SystemExceptionHandlerTest.key");
        
        MockHttpServletRequest req = new MockHttpServletRequest();
        
        // MessageResources
        MessageResourcesFactory factory
            = PropertyMessageResourcesFactory.createFactory();
        PropertyMessageResources requestResources 
            = new PropertyMessageResources(factory,
                SystemExceptionHandler.class
                .getPackage().getName().replace('.', '/')
                + "/SystemExceptionHandler_MessageResources01");

        // 呼び出し準備
        Class[] clazz = {HttpServletRequest.class,
                         SystemException.class,
                         MessageResources.class};
        Object[] args = {req, e, requestResources};

        // テスト実施
        // privateメソッドを強制実行
        Object result = UTUtil.invokePrivate(handler,
            "getErrorMessage", clazz, args);

        // 判定
        assertEquals("{0}デフォルトメッセージ", (String)result);
    }

    /**
     * testGetErrorMessage03()
     * <br><br>
     * 
     * 正常系
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) req:not null<br>
     *         (引数) se:not null<br>
     *         (引数) resource:key="{0}デフォルトメッセージ"<br>
     *                en_US.key="{0}message"<br>
     *         (状態) locale:"en_US"<br>
     *         (状態) errorCode:"key"<br>
     *         (状態) option:null<br>
     *         
     * <br>
     * 期待値：(戻り値) String:"{0}message"<br>
     *         
     * <br>
     * ロケールが指定された時、指定されたロケールのメッセージが出力されること。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    @SuppressWarnings("static-access")
    public void testGetErrorMessage03() throws Exception {
        // 前処理
        
        // システム例外ハンドラ
        SystemExceptionHandler handler = new SystemExceptionHandler();

        // システム例外
        SystemException e = new SystemException(new Exception(),
             "SystemExceptionHandlerTest.key");

        MockHttpServletRequest req = new MockHttpServletRequest();
        Locale locale = new Locale("en_US");
        req.setLocale(locale);

        // MessageResources
        MessageResourcesFactory factory
            = PropertyMessageResourcesFactory.createFactory();
        PropertyMessageResources requestResources 
            = new PropertyMessageResources(factory,
                SystemExceptionHandler.class
                .getPackage().getName().replace('.', '/')
                + "/SystemExceptionHandler_MessageResources01");

        // 呼び出し準備
        Class[] clazz = {HttpServletRequest.class,
                         SystemException.class,
                         MessageResources.class};
        Object[] args = {req, e, requestResources};

        // テスト実施
        Object result 
            = UTUtil.invokePrivate(handler, "getErrorMessage", clazz, args);

        // 判定
        assertEquals("{0}message", (String)result);
    }

    /**
     * testGetErrorMessage04()
     * <br><br>
     * 
     * 正常系
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) req:not null<br>
     *         (引数) se:not null<br>
     *         (引数) resource:key="{0}デフォルトメッセージ"<br>
     *                en_US.key="{0}message"<br>
     *         (状態) locale:null<br>
     *         (状態) errorCode:"key"<br>
     *         (状態) option:{"option1"}<br>
     *         
     * <br>
     * 期待値：(戻り値) String:"option1デフォルトメッセージ"<br>
     *         
     * <br>
     * 置換文字列が指定されているとき、メッセージの置換が行なわれること。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    @SuppressWarnings("static-access")
    public void testGetErrorMessage04() throws Exception {
        // 前処理

        // システム例外ハンドラ
        SystemExceptionHandler handler = new SystemExceptionHandler();

        // エラーメッセージを置換するメッセージ
        String[] options = {"option1"};

        // システム例外
        SystemException e = new SystemException(new Exception(),
             "SystemExceptionHandlerTest.key", options);

        MockHttpServletRequest req = new MockHttpServletRequest();

        // MessageResources
        MessageResourcesFactory factory
            = PropertyMessageResourcesFactory.createFactory();
        PropertyMessageResources requestResources 
            = new PropertyMessageResources(factory,
                SystemExceptionHandler.class
                .getPackage().getName().replace('.', '/')
                + "/SystemExceptionHandler_MessageResources01");

        // 呼び出し準備
        Class[] clazz = {HttpServletRequest.class,
                         SystemException.class,
                         MessageResources.class};
        Object[] args = {req, e, requestResources};

        // テスト実施
        Object result 
            = UTUtil.invokePrivate(handler, "getErrorMessage", clazz, args);

        // 判定
        assertEquals("option1デフォルトメッセージ", (String)result);
    }

    /**
     * testGetErrorMessage05()
     * <br><br>
     * 
     * 正常系
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) req:not null<br>
     *         (引数) se:not null<br>
     *         (引数) resource:key="{0}デフォルトメッセージ"<br>
     *                en_US.key="{0}message"<br>
     *         (状態) locale:"en_US"<br>
     *         (状態) errorCode:"key"<br>
     *         (状態) option:{"option1"}<br>
     *         
     * <br>
     * 期待値：(戻り値) String:"option1message"<br>
     *         
     * <br>
     * ロケール、置換文字列が指定された時、指定されたロケールのメッセージが置換されて出力されること。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    @SuppressWarnings("static-access")
    public void testGetErrorMessage05() throws Exception {
        // 前処理

        // システム例外ハンドラ
        SystemExceptionHandler handler = new SystemExceptionHandler();

        // エラーメッセージを置換するメッセージ
        String[] options = {"option1"};

        // システム例外
        SystemException e = new SystemException(new Exception(),
             "SystemExceptionHandlerTest.key", options);

        // 擬似リクエスト
        MockHttpServletRequest req = new MockHttpServletRequest();
        // ロケール設定
        Locale locale = new Locale("en_US");
        req.setLocale(locale);

        // MessageResources
        MessageResourcesFactory factory
            = PropertyMessageResourcesFactory.createFactory();
        PropertyMessageResources requestResources 
            = new PropertyMessageResources(factory,
                SystemExceptionHandler.class
                .getPackage().getName().replace('.', '/')
                + "/SystemExceptionHandler_MessageResources01");

        // 呼び出し準備
        Class[] clazz = {HttpServletRequest.class,
                         SystemException.class,
                         MessageResources.class};
        Object[] args = {req, e, requestResources};

        // テスト実施
        Object result 
            = UTUtil.invokePrivate(handler, "getErrorMessage", clazz, args);

        // 判定
        assertEquals("option1message", (String)result);
    }

}
