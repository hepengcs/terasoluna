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

import java.io.IOException;
import java.util.Iterator;

import javax.servlet.http.HttpSession;

import jp.terasoluna.fw.web.struts.action.ActionMappingEx;
import jp.terasoluna.fw.web.struts.form.DynaValidatorActionFormEx;
import jp.terasoluna.utlib.LogUTUtil;
import jp.terasoluna.utlib.MockHttpServletRequest;
import jp.terasoluna.utlib.MockHttpServletResponse;
import jp.terasoluna.utlib.MockHttpSession;
import jp.terasoluna.utlib.UTUtil;
import junit.framework.TestCase;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.validator.DynaValidatorActionForm;

import com.mockrunner.mock.web.MockActionMapping;

/**
 * {@link jp.terasoluna.fw.web.struts.actions.ActionEx} クラスのブラックボックステスト。
 *
 * <p>
 * <h4>【クラスの概要】</h4>
 * ログ出力やトランザクショントークンチェックが出来るアクション基底クラス
 * <p>
 *
 * @see jp.terasoluna.fw.web.struts.actions.ActionEx
 */
public class ActionExTest extends TestCase {

    /**
     * このテストケースを実行する為の
     * GUI アプリケーションを起動する。
     *
     * @param args java コマンドに設定されたパラメータ
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(ActionExTest.class);
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
    public ActionExTest(String name) {
        super(name);
    }

    /**
     * testSetTokenCheck01()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) tokenCheck:true<br>
     *         (状態) tokenCheck:false<br>
     *
     * <br>
     * 期待値：(状態変化) tokenCheck:true<br>
     *
     * <br>
     * 引数に指定した値がActionExのtokenCheckに正常に格納されること。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetTokenCheck01() throws Exception {
        // 前処理
        ActionEx action = new ActionEx_ActionExStub01();

        UTUtil.setPrivateField(action, "tokenCheck", false);

        // テスト実施
        action.setTokenCheck(true);

        // 判定
        boolean tokenCheck =
            ((Boolean) UTUtil.getPrivateField(action, "tokenCheck")).booleanValue();
        assertTrue(tokenCheck);
    }

    /**
     * testIsTokenCheck01()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(状態) tokenCheck:true<br>
     *
     * <br>
     * 期待値：(戻り値) boolean:true<br>
     *
     * <br>
     * ActionExのtokenCheckがかえることを確認
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testIsTokenCheck01() throws Exception {
        // 前処理
        ActionEx action = new ActionEx_ActionExStub01();

        UTUtil.setPrivateField(action, "tokenCheck", true);

        // テスト実施
        boolean b = action.isTokenCheck();

        // 判定
        assertTrue(b);
    }

    /**
     * testSetSaveToken01()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) saveToken:false<br>
     *         (状態) saveToken:true<br>
     *
     * <br>
     * 期待値：(状態変化) saveToken:false<br>
     *
     * <br>
     * 引数に指定した値がActionExのsaveTokenに正常に格納されること。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetSaveToken01() throws Exception {
        // 前処理
        ActionEx action = new ActionEx_ActionExStub01();

        UTUtil.setPrivateField(action, "saveToken", false);

        // テスト実施
        action.setSaveToken(true);

        // 判定
        boolean saveToken =
            ((Boolean) UTUtil.getPrivateField(action, "saveToken"))
            .booleanValue();
        assertTrue(saveToken);
    }

    /**
     * testIsSaveToken01()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(状態) saveToken:true<br>
     *
     * <br>
     * 期待値：(戻り値) boolean:true<br>
     *
     * <br>
     * ActionExのsaveTokenがかえることを確認
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testIsSaveToken01() throws Exception {
        // 前処理
        ActionEx action = new ActionEx_ActionExStub01();

        UTUtil.setPrivateField(action, "saveToken", true);

        // テスト実施
        boolean b = action.isSaveToken();

        // 判定
        assertTrue(b);
    }

    /**
     * testExecute01()
     * <br><br>
     *
     * (異常系)
     * <br>
     * 観点：G
     * <br><br>
     * 入力値：(引数) mapping:not null<br>
     *         (引数) req:not null<br>
     *         (引数) res:not null<br>
     *         (状態) processTokenCheck():false<br>
     *
     * <br>
     * 期待値：(戻り値) actionForward:ActionFoward<br>
     *                  (Name："txtoken-error")<br>
     *         (状態変化) ログ:＜ログ＞<br>
     *                    インフォログ："Transaction token error."<br>
     * <br>
     * processTokenCheck()がfalseを返した場合、"txtoken-error"に遷移する
     * ActionForwardを返すことを確認
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testExecute01() throws Exception {
        // 前処理
        MockActionMapping mapping = new MockActionMapping();

        ActionForm form = new DynaValidatorActionFormEx();

        MockHttpServletRequest req = new MockHttpServletRequest();
        MockHttpServletResponse res = new MockHttpServletResponse();

        // action:processTokenCheck = false
        ActionEx action = new ActionEx_ActionExStub01();
        ActionForward forward = null;

        // テスト実施
        forward = action.execute(mapping, form, req, res);

        // 判定
        assertEquals("txtoken-error", forward.getName());
        assertTrue(LogUTUtil.checkInfo("Transaction token error."));
    }

    /**
     * testExecute02()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) mapping:not null<br>
     *         (引数) form:null<br>
     *         (引数) req:not null<br>
     *         (引数) res:not null<br>
     *         (状態) processTokenCheck():true<br>
     *         (状態) doExecute():ActionForwardName："success"<br>
     *
     * <br>
     * 期待値：(戻り値) actionForward:mappingに設定されていたActionFoward<br>
     *                  (Name："success")<br>
     *
     * <br>
     * processTokenCheck()がtrueであり、引数formがnullだった場合、
     * doExecute()のリターン値を返すか確認
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testExecute02() throws Exception {
        // 前処理
        MockActionMapping mapping = new MockActionMapping();

        ActionForm form = null;

        MockHttpServletRequest req = new MockHttpServletRequest();
        MockHttpServletResponse res = new MockHttpServletResponse();

        // action:processTokenCheck = true
        ActionEx action = new ActionEx_ActionExStub02();
        ActionForward forward = null;

        // テスト実施
        forward = action.execute(mapping, form, req, res);

        // 判定
        assertEquals("success", forward.getName());
    }

    /**
     * testExecute03()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) mapping:not null<br>
     *         (引数) form:Form<br>
     *         (引数) req:not null<br>
     *         (引数) res:not null<br>
     *         (状態) processTokenCheck():true<br>
     *         (状態) doExecute():ActionForwardName："success"<br>
     *
     * <br>
     * 期待値：(戻り値) actionForward:mappingに設定されていたActionFoward<br>
     *                  (Name："success")<br>
     *
     * <br>
     * 引数formがFormExでなかった場合、doExecute()のリターン値を返すか確認
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testExecute03() throws Exception {
        // 前処理
        MockActionMapping mapping = new MockActionMapping();

        // ActionForm
        ActionForm form = new DynaValidatorActionForm();

        MockHttpServletRequest req = new MockHttpServletRequest();
        MockHttpServletResponse res = new MockHttpServletResponse();

        // action:processTokenCheck = true
        ActionEx action = new ActionEx_ActionExStub02();
        ActionForward forward = null;

        // テスト実施
        forward = action.execute(mapping, form, req, res);

        // 判定
        assertEquals("success", forward.getName());
    }

    /**
     * testExecute04()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) mapping:not null<br>
     *         (引数) form:FormEx<br>
     *         (引数) req:not null<br>
     *         (引数) res:not null<br>
     *         (状態) processTokenCheck():true<br>
     *         (状態) doExecute():null<br>
     *         (状態) doExecute()実行後の<br>
     *                form.isModified():false<br>
     *
     * <br>
     * 期待値：(戻り値) actionForward:null<br>
     *
     * <br>
     * doExecute()の結果がnullであり、doExecute()実行後のform.isModified()が
     * falseだった場合、nullを返すか確認
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testExecute04() throws Exception {
        // 前処理
        MockActionMapping mapping = new MockActionMapping();

        // FormEx
        DynaValidatorActionFormEx form = new DynaValidatorActionFormEx();

        MockHttpServletRequest req = new MockHttpServletRequest();
        MockHttpServletResponse res = new MockHttpServletResponse();

        // action:processTokenCheck = true
        // formEx.modified = false
        // doExecute = null
        ActionEx action = new ActionEx_ActionExStub06();
        ActionForward forward = null;

        // テスト実施
        forward = action.execute(mapping, form, req, res);

        // 判定
        assertNull(forward);
        assertFalse(form.isModified());
    }

    /**
     * testExecute05()
     * <br><br>
     *
     * (異常系)
     * <br>
     * 観点：G
     * <br><br>
     * 入力値：(引数) mapping:not null<br>
     *         (引数) form:FormEx<br>
     *         (引数) req:not null<br>
     *         (引数) res:not null<br>
     *         (状態) processTokenCheck():true<br>
     *         (状態) doExecute():Exception<br>
     *
     * <br>
     * 期待値：(状態変化) ログ:Exception<br>
     *
     * <br>
     * doExecute()が例外を起こした場合、Exceptionがおきるか確認
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testExecute05() throws Exception {
        // 前処理
        MockActionMapping mapping = new MockActionMapping();

        // FormEx
        DynaValidatorActionFormEx form = new DynaValidatorActionFormEx();

        MockHttpServletRequest req = new MockHttpServletRequest();
        MockHttpServletResponse res = new MockHttpServletResponse();

        // action:processTokenCheck = true
        ActionEx action = new ActionEx_ActionExStub03();

        // テスト実施
        try {
            action.execute(mapping, form, req, res);
            fail();
        } catch (Exception e) {
            // 判定
            assertEquals(IOException.class.getName(),
                    e.getCause().getClass().getName());
        }
    }

    /**
     * testExecute06()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) mapping:not null<br>
     *         (引数) form:FormEx<br>
     *         (引数) req:not null<br>
     *         (引数) res:not null<br>
     *         (状態) processTokenCheck():true<br>
     *         (状態) doExecute():ActionForwardName："success"<br>
     *         (状態) doExecute()実行後の<br>
     *                form.isModified():false<br>
     *
     * <br>
     * 期待値：(戻り値) actionForward:mappingに設定されていたActionFoward<br>
     *                  (Name："success")<br>
     *
     * <br>
     * doExecute()がActionForwardを返し、form.isModified()がfalseだった場合、
     * doExecute()のリターン値を返すか確認
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testExecute06() throws Exception {
        // 前処理
        MockActionMapping mapping = new MockActionMapping();

        // FormEx
        DynaValidatorActionFormEx form = new DynaValidatorActionFormEx();

        MockHttpServletRequest req = new MockHttpServletRequest();
        MockHttpServletResponse res = new MockHttpServletResponse();

        // action:processTokenCheck = true
        ActionEx action = new ActionEx_ActionExStub04();

        // form.isModified = false;
        UTUtil.setPrivateField(action, "modified", false);
        ActionForward forward = null;

        // テスト実施
        forward = action.execute(mapping, form, req, res);

        // 判定
        assertEquals("success", forward.getName());
        assertNull(req.getAttribute("SKIP_POPULATE"));
    }

    /**
     * testExecute07()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) mapping:not null<br>
     *         (引数) form:FormEx<br>
     *         (引数) req:not null<br>
     *         (引数) res:not null<br>
     *         (状態) processTokenCheck():true<br>
     *         (状態) doExecute():ActionForwardName："success"<br>
     *         (状態) doExecute()実行後の<br>
     *                form.isModified():true<br>
     *         (状態) mapping.getName():"abc"<br>
     *
     * <br>
     * 期待値：(戻り値) actionForward:mappingに設定されていたActionFoward<br>
     *                  (Name："success")<br>
     *         (状態変化) req.getAttribute("SKIP_POPULATE"):"abc"<br>
     *
     * <br>
     * doExecute()がActionForwardを返し、form.isModified()がtrueだった場合、
     * doExecute()のリターン値を返すかと、HttpServletRequestから取得した
     * "SKIP_POPULATE"に該当する属性がmapping.getName()と一致するか確認
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testExecute07() throws Exception {
        // 前処理
        MockActionMapping mapping = new MockActionMapping();
        mapping.setName("abc");

        // FormEx
        DynaValidatorActionFormEx form = new DynaValidatorActionFormEx();

        MockHttpServletRequest req = new MockHttpServletRequest();
        MockHttpServletResponse res = new MockHttpServletResponse();

        // action:processTokenCheck = true
        ActionEx_ActionExStub04 action = new ActionEx_ActionExStub04();

        // form.isModified = true;
        UTUtil.setPrivateField(action, "modified", true);
        ActionForward forward = null;

        // テスト実施
        forward = action.execute(mapping, form, req, res);

        // 判定
        assertEquals("success", forward.getName());
        assertEquals("abc", req.getAttribute("SKIP_POPULATE"));
    }

    /**
     * testProcessTokenCheck01()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) mapping:ActionMapping<br>
     *         (引数) req:not null<br>
     *
     * <br>
     * 期待値：(戻り値) boolean:true<br>
     *
     * <br>
     * 引数mappingがActionMappingExでなかった場合trueを返すか確認
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testProcessTokenCheck01() throws Exception {
        // 前処理
        MockActionMapping mapping = new MockActionMapping();
        MockHttpServletRequest req = new MockHttpServletRequest();

        ActionEx_ActionExStub05 action = new ActionEx_ActionExStub05();

        // テスト実施
        boolean b = action.processTokenCheck(mapping, req);

        // 判定
        assertTrue(b);
        assertFalse(action.isSaveToken);
    }

    /**
     * testProcessTokenCheck02()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) mapping:ActionMappingEx<br>
     *         (引数) req:not null<br>
     *         (状態) tokenCheck:true<br>
     *         (状態) Action.isTokenValid():false<br>
     *         (状態) saveToken:true<br>
     *
     * <br>
     * 期待値：(戻り値) boolean:false<br>
     *         (状態変化) Action.saveToken():実行<br>
     *
     * <br>
     * フィールドtokenCheckがtrueであり、Action.isTokenValid()がfalse、saveTokenがtrueだった場合、falseを返すとともにAction.saveToken()が呼び出されたか確認
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testProcessTokenCheck02() throws Exception {
        // 前処理
        ActionMappingEx mapping = new ActionMappingEx();
        MockHttpServletRequest req = new MockHttpServletRequest();

        ActionEx action = new ActionEx_ActionExStub05();
        UTUtil.setPrivateField(action, "tokenCheck", true);
        UTUtil.setPrivateField(action, "tokenValid", false);
        UTUtil.setPrivateField(action, "saveToken", true);

        // テスト実施
        boolean b = action.processTokenCheck(mapping, req);

        // 判定
        assertFalse(b);
        boolean isSaveToken =
            ((Boolean) UTUtil.getPrivateField(action, "isSaveToken"))
            .booleanValue();
        assertTrue(isSaveToken);
    }

    /**
     * testProcessTokenCheck03()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) mapping:ActionMappingEx<br>
     *         (引数) req:not null<br>
     *         (状態) tokenCheck:true<br>
     *         (状態) Action.isTokenValid():true<br>
     *         (状態) saveToken:true<br>
     *
     * <br>
     * 期待値：(戻り値) boolean:true<br>
     *         (状態変化) Action.saveToken():実行<br>
     *
     * <br>
     * フィールドtokenCheckがtrueであり、Action.isTokenValid()がtrue、saveTokenがtrueだった場合、trueを返すとともにAction.saveToken()が呼び出されたか確認
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testProcessTokenCheck03() throws Exception {
        // 前処理
        ActionMappingEx mapping = new ActionMappingEx();
        MockHttpServletRequest req = new MockHttpServletRequest();

        ActionEx action = new ActionEx_ActionExStub05();
        UTUtil.setPrivateField(action, "tokenCheck", true);
        UTUtil.setPrivateField(action, "tokenValid", true);
        UTUtil.setPrivateField(action, "saveToken", true);

        // テスト実施
        boolean b = action.processTokenCheck(mapping, req);

        // 判定
        assertTrue(b);
        boolean isSaveToken =
            ((Boolean) UTUtil.getPrivateField(action, "isSaveToken"))
            .booleanValue();
        assertTrue(isSaveToken);
    }

    /**
     * testProcessTokenCheck04()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) mapping:ActionMappingEx<br>
     *         (引数) req:not null<br>
     *         (状態) tokenCheck:false<br>
     *         (状態) Action.isTokenValid():true<br>
     *         (状態) saveToken:false<br>
     *
     * <br>
     * 期待値：(戻り値) boolean:true<br>
     *         (状態変化) Action.saveToken():未実行<br>
     *
     * <br>
     * フィールドtokenCheckがfalseであり、Action.isTokenValid()がtrue、saveTokenがfalseだった場合、trueを返すとともにAction.saveToken()が呼び出されなかったことを確認
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testProcessTokenCheck04() throws Exception {
        // 前処理
        ActionMappingEx mapping = new ActionMappingEx();
        MockHttpServletRequest req = new MockHttpServletRequest();

        ActionEx action = new ActionEx_ActionExStub05();
        UTUtil.setPrivateField(action, "tokenCheck", false);
        UTUtil.setPrivateField(action, "tokenValid", true);
        UTUtil.setPrivateField(action, "saveToken", false);

        // テスト実施
        boolean b = action.processTokenCheck(mapping, req);

        // 判定
        assertTrue(b);
        boolean isSaveToken =
            ((Boolean) UTUtil.getPrivateField(action, "isSaveToken"))
            .booleanValue();
        assertFalse(isSaveToken);
    }

    /**
     * testAddErrors01()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) session:null<br>
     *
     * <br>
     *
     * <br>
     * 引数sessionがnullだった場合、処理を中断することを確認
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testAddErrors01() throws Exception {
        // 前処理
        HttpSession session = null;
        ActionMessages errors = new ActionMessages();
        ActionEx action = new ActionEx_ActionExStub01();

        // テスト実施
        action.addErrors(session, errors);
    }

    /**
     * testAddErrors02()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) session:not null<br>
     *         (引数) errors:null<br>
     *
     * <br>
     * 期待値：(状態変化) sessioin#getAttribute(<br>
     *                    Globals.ERROR_KEY):null<br>
     *
     * <br>
     * 引数errorsがnullだった場合、処理を中断することを確認
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testAddErrors02() throws Exception {
        // 前処理
        MockHttpSession session = new MockHttpSession();
        ActionMessages errors = null;
        ActionEx action = new ActionEx_ActionExStub01();

        // テスト実施
        action.addErrors(session, errors);

        // 判定
        ActionMessages msgs =
            (ActionMessages) session.getAttribute(Globals.ERROR_KEY);
        assertNull(msgs);
    }

    /**
     * testAddErrors03()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) session:not null<br>
     *         (引数) errors:要素数0のActionMessages<br>
     *         (状態) (session#getAttribute(<br>
     *                Globals.ERROR_KEY)).size():null<br>
     *
     * <br>
     * 期待値：(状態変化) sessioin#getAttribute(<br>
     *                    Globals.ERROR_KEY):null<br>
     *
     * <br>
     * sessionにGlobals.ERROR_KEYに該当する属性が見つからなく、
     * 引数のerrorsのサイズが0だった場合、処理を中断することを確認
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testAddErrors03() throws Exception {
        // 前処理
        MockHttpSession session = new MockHttpSession();
        ActionMessages errors = new ActionMessages();

        ActionEx action = new ActionEx_ActionExStub01();

        // テスト実施
        action.addErrors(session, errors);

        // 判定
        ActionMessages msgs =
            (ActionMessages) session.getAttribute(Globals.ERROR_KEY);
        assertNull(msgs);
    }

    /**
     * testAddErrors04()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：D
     * <br><br>
     * 入力値：(引数) session:not null<br>
     *         (引数) errors:要素数1のActionMessages：<br>
     *                ([key="key01"、value="value01"])<br>
     *         (状態) (session#getAttribute(<br>
     *                Globals.ERROR_KEY)).size():null<br>
     *
     * <br>
     * 期待値：(状態変化) sessioin#getAttribute(<br>
     *                    Globals.ERROR_KEY):要素数1のActionMessages：<br>
     *                    ([key="key01"、value="value01"])<br>
     *
     * <br>
     * sessionにGlobals.ERROR_KEYに該当する属性が見つからなく、引数のerrorsのサイズが1だった場合、sesssionのGlobals.ERROR_KEY属性にerrorsの要素が入っていることを確認
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testAddErrors04() throws Exception {
        // 前処理
        MockHttpSession session = new MockHttpSession();
        ActionMessages errors = new ActionMessages();
        ActionMessage msg = new ActionMessage("key01", "value01");

        errors.add(Globals.ERROR_KEY, msg);

        ActionEx action = new ActionEx_ActionExStub01();

        // テスト実施
        action.addErrors(session, errors);

        // 判定
        ActionMessages msgs =
            (ActionMessages) session.getAttribute(Globals.ERROR_KEY);

        Iterator it = msgs.get(Globals.ERROR_KEY);
        ActionMessage message = (ActionMessage) it.next();
        String key = message.getKey();
        String value = (String) message.getValues()[0];
        assertEquals("key01", key);
        assertEquals("value01", value);
    }

    /**
     * testAddErrors05()
     * <br><br>
     *
     * (異常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) session:not null<br>
     *         (引数) errors:要素数0のActionMessages<br>
     *         (状態) (session#getAttribute(<br>
     *                Globals.ERROR_KEY)).size():要素数0のActionMessages<br>
     *
     * <br>
     * 期待値：(状態変化) sessioin#getAttribute(<br>
     *                    Globals.ERROR_KEY):null<br>
     *
     * <br>
     * sessionにGlobals.ERROR_KEYに該当する属性のサイズが0であり、
     * 引数のerrorsのサイズが0だった場合、処理を中断することを確認
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testAddErrors05() throws Exception {
        // 前処理
        MockHttpSession session = new MockHttpSession();
        ActionMessages errors = new ActionMessages();
        // 0件のエラー追加
        session.setAttribute(Globals.ERROR_KEY, errors);

        ActionEx action = new ActionEx_ActionExStub01();

        // テスト実施
        action.addErrors(session, errors);

        // 判定
        ActionMessages msgs =
            (ActionMessages) session.getAttribute(Globals.ERROR_KEY);

        assertNull(msgs);
    }

    /**
     * testAddErrors06()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) session:not null<br>
     *         (引数) errors:要素数1のActionMessages：<br>
     *                ([key="key01"、value="value01"])<br>
     *         (状態) (session#getAttribute(<br>
     *                Globals.ERROR_KEY)).size():要素数0のActionMessages<br>
     *
     * <br>
     * 期待値：(状態変化) sessioin#getAttribute(<br>
     *                    Globals.ERROR_KEY):要素数1のActionMessages：<br>
     *                    ([key="key01"、value="value01"])<br>
     *
     * <br>
     * sessionにGlobals.ERROR_KEYに該当する属性のサイズが0であり、
     * 引数のerrorsのサイズが1だった場合、sesssionのGlobals.ERROR_KEY属性に
     * errorsの要素が入っていることを確認
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testAddErrors06() throws Exception {
        // 前処理
        // sessionにセットする要素数0のActionMessages
        MockHttpSession session = new MockHttpSession();
        ActionMessages serrors = new ActionMessages();
        session.setAttribute(Globals.ERROR_KEY, serrors);

        // パラメータとして渡す要素数1のActionMessages
        ActionMessages errors = new ActionMessages();
        ActionMessage msg = new ActionMessage("key01", "value01");

        errors.add(Globals.ERROR_KEY, msg);

        ActionEx action = new ActionEx_ActionExStub01();

        // テスト実施
        action.addErrors(session, errors);

        // 判定
        ActionMessages msgs =
            (ActionMessages) session.getAttribute(Globals.ERROR_KEY);

        Iterator it = msgs.get(Globals.ERROR_KEY);
        ActionMessage message = (ActionMessage) it.next();
        String key = message.getKey();
        String value = (String) message.getValues()[0];
        assertEquals("key01", key);
        assertEquals("value01", value);
    }

    /**
     * testAddErrors07()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：D
     * <br><br>
     * 入力値：(引数) session:not null<br>
     *         (引数) errors:要素数3のActionMessages：<br>
     *                ([key="key01"、value="value01"]、<br>
     *                [key="key02"、value="value02"]、<br>
     *                [key="key03"、value="value03"])<br>
     *         (状態) (session#getAttribute(<br>
     *                Globals.ERROR_KEY)).size():要素数1のActionMessages：<br>
     *                ([key="key04"、value="value04"])<br>
     *
     * <br>
     * 期待値：(状態変化) sessioin#getAttribute(<br>
     *                    Globals.ERROR_KEY):要素数4のActionMessages：<br>
     *                    [key="key04"、value="value04"])<br>
     *                    ([key="key01"、value="value01"]、<br>
     *                    [key="key02"、value="value02"]、<br>
     *                    [key="key03"、value="value03"]、<br>
     *
     * <br>
     * sessionにGlobals.ERROR_KEYに該当する属性のサイズが1であり、
     * 引数のerrorsのサイズが3だった場合、sesssionのGlobals.ERROR_KEY属性
     * errorsの要素とsessionの属性の要素が入っていることを確認
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testAddErrors07() throws Exception {
        // 前処理
        // sessionにセットされる要素数1のActionMessages
        MockHttpSession session = new MockHttpSession();
        ActionMessage smsg = new ActionMessage("key04", "value04");

        ActionMessages serrors = new ActionMessages();
        serrors.add(Globals.ERROR_KEY, smsg);

        session.setAttribute(Globals.ERROR_KEY, serrors);


        // パラメータとして渡す要素数3のActionMessages
        ActionMessages errors = new ActionMessages();

        ActionMessage msg = new ActionMessage("key01", "value01");
        errors.add(Globals.ERROR_KEY, msg);

        msg = new ActionMessage("key02", "value02");
        errors.add(Globals.ERROR_KEY, msg);

        msg = new ActionMessage("key03", "value03");
        errors.add(Globals.ERROR_KEY, msg);


        ActionEx action = new ActionEx_ActionExStub01();

        // テスト実施
        action.addErrors(session, errors);

        // 判定
        ActionMessages msgs =
            (ActionMessages) session.getAttribute(Globals.ERROR_KEY);

        Iterator it = msgs.get(Globals.ERROR_KEY);
        ActionMessage message = (ActionMessage) it.next();
        String key = message.getKey();
        String value = (String) message.getValues()[0];
        assertEquals("key04", key);
        assertEquals("value04", value);

        message = (ActionMessage) it.next();
        key = message.getKey();
        value = (String) message.getValues()[0];
        assertEquals("key01", key);
        assertEquals("value01", value);

        message = (ActionMessage) it.next();
        key = message.getKey();
        value = (String) message.getValues()[0];
        assertEquals("key02", key);
        assertEquals("value02", value);

        message = (ActionMessage) it.next();
        key = message.getKey();
        value = (String) message.getValues()[0];
        assertEquals("key03", key);
        assertEquals("value03", value);
    }

    /**
     * testAddErrors08()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：D
     * <br><br>
     * 入力値：(引数) session:not null<br>
     *         (引数) errors:要素数1のActionMessages：<br>
     *                ([key="key01"、value="value01"])<br>
     *         (状態) (session#getAttribute(<br>
     *                Globals.ERROR_KEY)).size():要素数3のActionMessages：<br>
     *                ([key="key02"、value="value02"]、<br>
     *                [key="key03"、value="value03"]、<br>
     *                [key="key04"、value="value04"])<br>
     *
     * <br>
     * 期待値：(状態変化) sessioin#getAttribute(<br>
     *                    Globals.ERROR_KEY):要素数4のActionMessages：<br>
     *                    ([key="key02"、value="value02"]、<br>
     *                    [key="key03"、value="value03"]、<br>
     *                    [key="key04"、value="value04"]、<br>
     *                    [key="key01"、value="value01"])<br>
     *
     * <br>
     * sessionにGlobals.ERROR_KEYに該当する属性のサイズが3であり、
     * 引数のerrorsのサイズが1だった場合、sesssionのGlobals.ERROR_KEY属性
     * errorsの要素とsessionの属性の要素が入っていることを確認
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testAddErrors08() throws Exception {
        // 前処理
        // sessionにセットされる要素数3のActionMessages
        MockHttpSession session = new MockHttpSession();
        ActionMessage smsg = new ActionMessage("key02", "value02");
        ActionMessages serrors = new ActionMessages();
        serrors.add(Globals.ERROR_KEY, smsg);

        smsg = new ActionMessage("key03", "value03");
        serrors.add(Globals.ERROR_KEY, smsg);

        smsg = new ActionMessage("key04", "value04");
        serrors.add(Globals.ERROR_KEY, smsg);

        session.setAttribute(Globals.ERROR_KEY, serrors);


        // パラメータとして渡す要素数1のActionMessages
        ActionMessages errors = new ActionMessages();
        ActionMessage msg = new ActionMessage("key01", "value01");
        errors.add(Globals.ERROR_KEY, msg);


        ActionEx action = new ActionEx_ActionExStub01();

        // テスト実施
        action.addErrors(session, errors);

        // 判定
        ActionMessages msgs =
            (ActionMessages) session.getAttribute(Globals.ERROR_KEY);

        Iterator it = msgs.get(Globals.ERROR_KEY);
        ActionMessage message = (ActionMessage) it.next();
        String key = message.getKey();
        String value = (String) message.getValues()[0];
        assertEquals("key02", key);
        assertEquals("value02", value);

        message = (ActionMessage) it.next();
        key = message.getKey();
        value = (String) message.getValues()[0];
        assertEquals("key03", key);
        assertEquals("value03", value);

        message = (ActionMessage) it.next();
        key = message.getKey();
        value = (String) message.getValues()[0];
        assertEquals("key04", key);
        assertEquals("value04", value);

        message = (ActionMessage) it.next();
        key = message.getKey();
        value = (String) message.getValues()[0];
        assertEquals("key01", key);
        assertEquals("value01", value);
    }

    /**
     * testAddMessages01()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) session:null<br>
     *
     * <br>
     *
     * <br>
     * 引数sessionがnullだった場合、処理を中断することを確認
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testAddMessages01() throws Exception {
        // 前処理
        HttpSession session = null;
        ActionMessages errors = new ActionMessages();
        ActionEx action = new ActionEx_ActionExStub01();

        // テスト実施
        action.addMessages(session, errors);
    }

    /**
     * testAddMessages02()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) session:not null<br>
     *         (引数) messages:null<br>
     *
     * <br>
     * 期待値：(状態変化) sessioin#getAttribute(<br>
     *                    Globals.MESSAGE_KEY):null<br>
     *
     * <br>
     * 引数messagesがnullだった場合、処理を中断することを確認
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testAddMessages02() throws Exception {
        // 前処理
        MockHttpSession session = new MockHttpSession();
        ActionMessages messages = null;
        ActionEx action = new ActionEx_ActionExStub01();

        // テスト実施
        action.addMessages(session, messages);

        // 判定
        ActionMessages msgs =
            (ActionMessages) session.getAttribute(Globals.MESSAGE_KEY);
        assertNull(msgs);
    }

    /**
     * testAddMessages03()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) session:not null<br>
     *         (引数) messages:要素数0のActionMessages<br>
     *         (状態) (session#getAttribute(<br>
     *                Globals.MESSAGE_KEY)).size():null<br>
     *
     * <br>
     * 期待値：(状態変化) sessioin#getAttribute(<br>
     *                    Globals.MESSAGE_KEY):null<br>
     *
     * <br>
     * sessionにGlobals.MESSAGE_KEYに該当する属性が見つからなく、
     * 引数のmessagesのサイズが0だった場合、処理を中断することを確認
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testAddMessages03() throws Exception {
        // 前処理
        MockHttpSession session = new MockHttpSession();
        ActionMessages messages = new ActionMessages();

        ActionEx action = new ActionEx_ActionExStub01();

        // テスト実施
        action.addMessages(session, messages);

        // 判定
        ActionMessages msgs =
            (ActionMessages) session.getAttribute(Globals.MESSAGE_KEY);
        assertNull(msgs);
    }

    /**
     * testAddMessages04()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：D
     * <br><br>
     * 入力値：(引数) session:not null<br>
     *         (引数) messages:要素数1のActionMessages：<br>
     *                ([key="key01"、value="value01"])<br>
     *         (状態) (session#getAttribute(<br>
     *                Globals.MESSAGE_KEY)).size():null<br>
     *
     * <br>
     * 期待値：(状態変化) sessioin#getAttribute(<br>
     *                    Globals.MESSAGE_KEY):要素数1のActionMessages：<br>
     *                    ([key="key01"、value="value01"])<br>
     *
     * <br>
     * sessionにGlobals.MESSAGE_KEYに該当する属性が見つからなく、
     * 引数のmessagesのサイズが1だった場合、sesssionのGlobals.MESSAGE_KEY属性に
     * messagesの要素が入っていることを確認
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testAddMessages04() throws Exception {
        // 前処理
        MockHttpSession session = new MockHttpSession();
        ActionMessages messages = new ActionMessages();
        ActionMessage msg = new ActionMessage("key01", "value01");

        messages.add(Globals.MESSAGE_KEY, msg);

        ActionEx action = new ActionEx_ActionExStub01();

        // テスト実施
        action.addMessages(session, messages);

        // 判定
        ActionMessages msgs =
            (ActionMessages) session.getAttribute(Globals.MESSAGE_KEY);

        Iterator it = msgs.get(Globals.MESSAGE_KEY);
        ActionMessage message = (ActionMessage) it.next();
        String key = message.getKey();
        String value = (String) message.getValues()[0];
        assertEquals("key01", key);
        assertEquals("value01", value);
    }

    /**
     * testAddMessages05()
     * <br><br>
     *
     * (異常系)
     * <br>
     * 観点：D
     * <br><br>
     * 入力値：(引数) session:not null<br>
     *         (引数) messages:要素数0のActionMessages<br>
     *         (状態) (session#getAttribute(<br>
     *                Globals.MESSAGE_KEY)).size():要素数0のActionMessages<br>
     *
     * <br>
     * 期待値：(状態変化) sessioin#getAttribute(<br>
     *                    Globals.MESSAGE_KEY):null<br>
     *
     * <br>
     * sessionにGlobals.MESSAGE_KEYに該当する属性のサイズが0であり、
     * 引数のmessagesのサイズが0だった場合、処理を中断することを確認
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testAddMessages05() throws Exception {
        // 前処理
        MockHttpSession session = new MockHttpSession();
        ActionMessages messages = new ActionMessages();
        session.setAttribute(Globals.MESSAGE_KEY, messages);

        ActionEx action = new ActionEx_ActionExStub01();

        // テスト実施
        action.addMessages(session, messages);

        // 判定
        ActionMessages msgs =
            (ActionMessages) session.getAttribute(Globals.MESSAGE_KEY);

        assertNull(msgs);
    }

    /**
     * testAddMessages06()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：D
     * <br><br>
     * 入力値：(引数) session:not null<br>
     *         (引数) messages:要素数1のActionMessages：<br>
     *                ([key="key01"、value="value01"])<br>
     *         (状態) (session#getAttribute(<br>
     *                Globals.MESSAGE_KEY)).size():要素数0のActionMessages<br>
     *
     * <br>
     * 期待値：(状態変化) sessioin#getAttribute(<br>
     *                    Globals.MESSAGE_KEY):要素数1のActionMessages：<br>
     *                    ([key="key01"、value="value01"])<br>
     *
     * <br>
     * sessionにGlobals.MESSAGE_KEYに該当する属性のサイズが0であり、
     * 引数のmessagesのサイズが1だった場合、sesssionのGlobals.MESSAGE_KEY属性に
     * messagesの要素が入っていることを確認
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testAddMessages06() throws Exception {
        // 前処理
        // sessionにセットする要素数0のActionMessages
        MockHttpSession session = new MockHttpSession();
        ActionMessages smessages = new ActionMessages();
        session.setAttribute(Globals.MESSAGE_KEY, smessages);

        // パラメータとして渡す要素数1のActionMessages
        ActionMessages messages = new ActionMessages();
        ActionMessage msg = new ActionMessage("key01", "value01");

        messages.add(Globals.MESSAGE_KEY, msg);

        ActionEx action = new ActionEx_ActionExStub01();

        // テスト実施
        action.addMessages(session, messages);

        // 判定
        ActionMessages msgs =
            (ActionMessages) session.getAttribute(Globals.MESSAGE_KEY);

        Iterator it = msgs.get(Globals.MESSAGE_KEY);
        ActionMessage message = (ActionMessage) it.next();
        String key = message.getKey();
        String value = (String) message.getValues()[0];
        assertEquals("key01", key);
        assertEquals("value01", value);
    }

    /**
     * testAddMessages07()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：D
     * <br><br>
     * 入力値：(引数) session:not null<br>
     *         (引数) messages:要素数3のActionMessages：<br>
     *                ([key="key01"、value="value01"]、<br>
     *                [key="key02"、value="value02"]、<br>
     *                [key="key03"、value="value03"])<br>
     *         (状態) (session#getAttribute(<br>
     *                Globals.MESSAGE_KEY)).size():要素数1のActionMessages：<br>
     *                ([key="key04"、value="value04"])<br>
     *
     * <br>
     * 期待値：(状態変化) sessioin#getAttribute(<br>
     *                    Globals.MESSAGE_KEY):要素数4のActionMessages：<br>
     *                    ([key="key01"、value="value01"]、<br>
     *                    [key="key02"、value="value02"]、<br>
     *                    [key="key03"、value="value03"]、<br>
     *                    [key="key04"、value="value04"])<br>
     *
     * <br>
     * sessionにGlobals.MESSAGE_KEYに該当する属性のサイズが1であり、
     * 引数のmessagesのサイズが3だった場合、sesssionのGlobals.MESSAGE_KEY属性
     * messagesの要素とsessionの属性の要素が入っていることを確認
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testAddMessages07() throws Exception {
        // 前処理
        // sessionにセットされる要素数1のActionMessages
        MockHttpSession session = new MockHttpSession();
        ActionMessage smsg = new ActionMessage("key04", "value04");

        ActionMessages smessages = new ActionMessages();
        smessages.add(Globals.MESSAGE_KEY, smsg);

        session.setAttribute(Globals.MESSAGE_KEY, smessages);


        // パラメータとして渡す要素数3のActionMessages
        ActionMessages messages = new ActionMessages();

        ActionMessage msg = new ActionMessage("key01", "value01");
        messages.add(Globals.MESSAGE_KEY, msg);

        msg = new ActionMessage("key02", "value02");
        messages.add(Globals.MESSAGE_KEY, msg);

        msg = new ActionMessage("key03", "value03");
        messages.add(Globals.MESSAGE_KEY, msg);


        ActionEx action = new ActionEx_ActionExStub01();

        // テスト実施
        action.addMessages(session, messages);

        // 判定
        ActionMessages msgs =
            (ActionMessages) session.getAttribute(Globals.MESSAGE_KEY);

        Iterator it = msgs.get(Globals.MESSAGE_KEY);
        ActionMessage message = (ActionMessage) it.next();
        String key = message.getKey();
        String value = (String) message.getValues()[0];
        assertEquals("key04", key);
        assertEquals("value04", value);

        message = (ActionMessage) it.next();
        key = message.getKey();
        value = (String) message.getValues()[0];
        assertEquals("key01", key);
        assertEquals("value01", value);

        message = (ActionMessage) it.next();
        key = message.getKey();
        value = (String) message.getValues()[0];
        assertEquals("key02", key);
        assertEquals("value02", value);

        message = (ActionMessage) it.next();
        key = message.getKey();
        value = (String) message.getValues()[0];
        assertEquals("key03", key);
        assertEquals("value03", value);
    }

    /**
     * testAddMessages08()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：D
     * <br><br>
     * 入力値：(引数) session:not null<br>
     *         (引数) messages:要素数1のActionMessages：<br>
     *                ([key="key01"、value="value01"])<br>
     *         (状態) (session#getAttribute(<br>
     *                Globals.MESSAGE_KEY)).size():要素数3のActionMessages：<br>
     *                ([key="key02"、value="value02"]、<br>
     *                [key="key03"、value="value03"]、<br>
     *                [key="key04"、value="value04"])<br>
     *
     * <br>
     * 期待値：(状態変化) sessioin#getAttribute(<br>
     *                    Globals.MESSAGE_KEY):要素数4のActionMessages：<br>
     *                    ([key="key02"、value="value02"]、<br>
     *                    [key="key03"、value="value03"]、<br>
     *                    [key="key04"、value="value04"]、<br>
     *                    [key="key01"、value="value01"])<br>
     *
     * <br>
     * sessionにGlobals.MESSAGE_KEYに該当する属性のサイズが3であり、
     * 引数のmessagesのサイズが1だった場合、sesssionのGlobals.MESSAGE_KEY属性
     * messagesの要素とsessionの属性の要素が入っていることを確認
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testAddMessages08() throws Exception {
        // 前処理
        // sessionにセットされる要素数3のActionMessages
        MockHttpSession session = new MockHttpSession();
        ActionMessage smsg = new ActionMessage("key02", "value02");
        ActionMessages smessages = new ActionMessages();
        smessages.add(Globals.MESSAGE_KEY, smsg);

        smsg = new ActionMessage("key03", "value03");
        smessages.add(Globals.MESSAGE_KEY, smsg);

        smsg = new ActionMessage("key04", "value04");
        smessages.add(Globals.MESSAGE_KEY, smsg);

        session.setAttribute(Globals.MESSAGE_KEY, smessages);


        // パラメータとして渡す要素数1のActionMessages
        ActionMessages messages = new ActionMessages();
        ActionMessage msg = new ActionMessage("key01", "value01");
        messages.add(Globals.MESSAGE_KEY, msg);


        ActionEx action = new ActionEx_ActionExStub01();

        // テスト実施
        action.addMessages(session, messages);

        // 判定
        ActionMessages msgs =
            (ActionMessages) session.getAttribute(Globals.MESSAGE_KEY);

        Iterator it = msgs.get(Globals.MESSAGE_KEY);
        ActionMessage message = (ActionMessage) it.next();
        String key = message.getKey();
        String value = (String) message.getValues()[0];
        assertEquals("key02", key);
        assertEquals("value02", value);

        message = (ActionMessage) it.next();
        key = message.getKey();
        value = (String) message.getValues()[0];
        assertEquals("key03", key);
        assertEquals("value03", value);

        message = (ActionMessage) it.next();
        key = message.getKey();
        value = (String) message.getValues()[0];
        assertEquals("key04", key);
        assertEquals("value04", value);

        message = (ActionMessage) it.next();
        key = message.getKey();
        value = (String) message.getValues()[0];
        assertEquals("key01", key);
        assertEquals("value01", value);
    }
}
