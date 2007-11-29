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

import java.util.HashMap;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionForward;
import org.apache.struts.config.ModuleConfig;
import org.apache.struts.config.impl.ModuleConfigImpl;

import jp.terasoluna.fw.web.struts.action.ActionMappingEx;
import jp.terasoluna.fw.web.struts.form.DynaValidatorActionFormEx;
import jp.terasoluna.utlib.MockHttpServletRequest;
import jp.terasoluna.utlib.MockHttpServletResponse;
import jp.terasoluna.utlib.UTUtil;
import junit.framework.TestCase;

/**
 * {@link jp.terasoluna.fw.web.struts.actions.DispatchAction} クラスのブラックボックステスト。
 * 
 * <p>
 * <h4>【クラスの概要】</h4>
 * フォワード先の振り分け処理を行うアクション。
 * <p>
 * 
 * @see jp.terasoluna.fw.web.struts.actions.DispatchAction
 */
@SuppressWarnings("unchecked")
public class DispatchActionTest extends TestCase {

    /**
     * このテストケースを実行する為の
     * GUI アプリケーションを起動する。
     * 
     * @param args java コマンドに設定されたパラメータ
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(DispatchActionTest.class);
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
    public DispatchActionTest(String name) {
        super(name);
    }

    /**
     * testSetEvent01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) value:"event"<br>
     *         (状態) event:null<br>
     *         
     * <br>
     * 期待値：(戻り値) void:－<br>
     *         (状態変化) event:"event"<br>
     *         
     * <br>
     * 引数に指定した値がeventに正常に格納されること。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetEvent01() throws Exception {
        // DispatchAction生成
        DispatchAction action = new DispatchAction();

        // テスト実施
        action.setEvent("event");

        // 判定
        assertEquals("event", UTUtil.getPrivateField(action, "event"));
    }

    /**
     * testDoExecute01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) mapping:ActionForwardName："success"<br>
     *         (引数) form:not null<br>
     *         (引数) req:not null<br>
     *         (引数) res:not null<br>
     *         (状態) event:null<br>
     *         (状態) eventField:"event"<br>
     *         (状態) params:["event"：{"forward_success"}]<br>
     *                ["forward_success2"：{"ABC"}]<br>
     *         (状態) forward:"success"<br>
     *         (状態) attribute:"SERVER_BLOCKAGE_THRU_KEY"："THRU"<br>
     *                "BLOCKAGE_THRU_KEY"："BLOCK"<br>
     *         
     * <br>
     * 期待値：(戻り値) ActionForward:mappingに設定されていたActionFoward<br>
     *                  (Name："success")<br>
     *         (状態変化) restrictionEscapePaths:"SERVER_BLOCKAGE_THRU_KEY"と"BLOCKAGE_THRU_KEY"が存在しない<br>
     *         
     * <br>
     * "event"に該当するリクエストのパラメータ値が"forward_success"の場合、マッピングに設定されているActionForward"success"が返却されることを確認する。<br>
     * 閉塞チェックフラグ"SERVER_BLOCKAGE_THRU_KEY"と"BLOCKAGE_THRU_KEY"が削除されることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testDoExecute01() throws Exception {
        // ActionMappingを作成
        ActionMappingEx mapping = new ActionMappingEx();

        // "success"のアクションフォワードの生成
        ActionForward forward = new ActionForward();
        forward.setName("success");
        // ModuleConfigを作成
        ModuleConfig mConfig = new ModuleConfigImpl("");
        // 返却されるActionForwardを指定
        mConfig.addForwardConfig(forward);
        // モジュールコンフィグのforward属性指定
        mapping.setModuleConfig(mConfig);

        // ActionFormExの作成
        DispatchAction_DynaValidatorActionFormExStub01 formEx =
            new DispatchAction_DynaValidatorActionFormExStub01();

        // HTTPリクエストの生成
        MockHttpServletRequest req = new MockHttpServletRequest();
        String[] eventParamValues1 = { "forward_success" };
        req.setParameter("event", eventParamValues1);
        String[] eventParamValues2 = { "ABC" };
        req.setParameter("forward_success2", eventParamValues2);
        // 属性の設定
        req.setAttribute("SERVER_BLOCKAGE_THRU_KEY", "THRU");
        req.setAttribute("BLOCKAGE_THRU_KEY", "BLOCK");

        // HTTPレスポンスの生成
        MockHttpServletResponse res = new MockHttpServletResponse();

        // DispatchAction生成
        DispatchAction action = new DispatchAction();

        // テスト実行
        ActionForward ret = action.doExecute(mapping, formEx, req, res);

        // 結果の確認
        // マッピングに設定されている"success"のアクションフォワードを返却すること
        assertEquals("success", ret.getName());
        // 属性が削除されていること
        assertNull(req.getAttribute("SERVER_BLOCKAGE_THRU_KEY"));
        assertNull(req.getAttribute("BLOCKAGE_THRU_KEY"));
    }

    /**
     * testDoExecute02()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) mapping:ActionForwardName："success"<br>
     *         (引数) form:not null<br>
     *         (引数) req:not null<br>
     *         (引数) res:not null<br>
     *         (状態) event:null<br>
     *         (状態) eventField:"event"<br>
     *         (状態) params:["event"：{"forward_success1"}]<br>
     *         (状態) forward:"success1"<br>
     *         (状態) attribute:null<br>
     *         
     * <br>
     * 期待値：(戻り値) ActionForward:null<br>
     *         (状態変化) restrictionEscapePaths:"SERVER_BLOCKAGE_THRU_KEY"と"BLOCKAGE_THRU_KEY"が存在しない<br>
     *         
     * <br>
     * "event"に該当するリクエストのパラメータ値が"forward_success1"の場合、マッピングに設定されているActionForward"success1"が存在しないためnullのアクションフォワードが返却されることを確認する。<br>
     * 閉塞チェックフラグ"SERVER_BLOCKAGE_THRU_KEY"と"BLOCKAGE_THRU_KEY"が存在しないことを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testDoExecute02() throws Exception {
        // ActionMappingを作成
        ActionMappingEx mapping = new ActionMappingEx();

        // "success"のアクションフォワードの生成
        ActionForward forward = new ActionForward();
        forward.setName("success");
        // ModuleConfigを作成
        ModuleConfig mConfig = new ModuleConfigImpl("");
        // 返却されるActionForwardを指定
        mConfig.addForwardConfig(forward);
        // モジュールコンフィグのforward属性指定
        mapping.setModuleConfig(mConfig);

        // ActionFormExの作成
        DispatchAction_DynaValidatorActionFormExStub01 formEx =
            new DispatchAction_DynaValidatorActionFormExStub01();

        // HTTPリクエストの生成
        MockHttpServletRequest req = new MockHttpServletRequest();
        String[] eventParamValues1 = { "forward_success1" };
        req.setParameter("event", eventParamValues1);

        // HTTPレスポンスの生成
        MockHttpServletResponse res = new MockHttpServletResponse();

        // DispatchAction生成
        DispatchAction action = new DispatchAction();

        // テスト実行
        ActionForward ret = action.doExecute(mapping, formEx, req, res);

        // 結果の確認
        // nullのアクションフォワードを返却すること
        assertNull(ret);
        // 属性が削除されていること
        assertNull(req.getAttribute("SERVER_BLOCKAGE_THRU_KEY"));
        assertNull(req.getAttribute("BLOCKAGE_THRU_KEY"));
    }

    /**
     * testDoExecute03()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：AC
     * <br><br>
     * 入力値：(引数) mapping:ActionForwardName："success"<br>
     *         (引数) form:null<br>
     *         (引数) req:not null<br>
     *         (引数) res:not null<br>
     *         (状態) event:null<br>
     *         (状態) eventField:"event"<br>
     *         (状態) params:["event"：{"forward_success"}]<br>
     *         (状態) forward:"success"<br>
     *         (状態) attribute:"BLOCKAGE_THRU_KEY"："BLOCK"<br>
     *         
     * <br>
     * 期待値：(戻り値) ActionForward:mappingに設定されていたActionFoward<br>
     *                  (Name："success")<br>
     *         (状態変化) restrictionEscapePaths:"SERVER_BLOCKAGE_THRU_KEY"と"BLOCKAGE_THRU_KEY"が存在しない<br>
     *         
     * <br>
     * formExがnullの場合、例外が発生することなく正しく動作することを確認する。<br>
     * 閉塞チェックフラグ"SERVER_BLOCKAGE_THRU_KEY"と"BLOCKAGE_KEY"が存在しないことを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testDoExecute03() throws Exception {
        // ActionMappingを作成
        ActionMappingEx mapping = new ActionMappingEx();

        // "success"のアクションフォワードの生成
        ActionForward forward = new ActionForward();
        forward.setName("success");
        // ModuleConfigを作成
        ModuleConfig mConfig = new ModuleConfigImpl("");
        // 返却されるActionForwardを指定
        mConfig.addForwardConfig(forward);
        // モジュールコンフィグのforward属性指定
        mapping.setModuleConfig(mConfig);

        // ActionFormExの作成
        DynaValidatorActionFormEx formEx = null;

        // HTTPリクエストの生成
        MockHttpServletRequest req = new MockHttpServletRequest();
        String[] eventParamValues1 = { "forward_success" };
        req.setParameter("event", eventParamValues1);
        // 属性の設定
        req.setAttribute("SERVER_BLOCKAGE_THRU_KEY", "THRU");
        req.setAttribute("BLOCKAGE_THRU_KEY", "BLOCK");

        // HTTPレスポンスの生成
        MockHttpServletResponse res = new MockHttpServletResponse();

        // DispatchAction生成
        DispatchAction action = new DispatchAction();

        // テスト実行
        ActionForward ret = action.doExecute(mapping, formEx, req, res);

        // 結果の確認
        // マッピングに設定されている"success"のアクションフォワードを返却すること
        assertEquals("success", ret.getName());
        // 属性が削除されていること
        assertNull(req.getAttribute("SERVER_BLOCKAGE_THRU_KEY"));
        assertNull(req.getAttribute("BLOCKAGE_THRU_KEY"));
    }

    /**
     * testDoExecute04()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) mapping:ActionForwardName："success"<br>
     *         (引数) form:not null<br>
     *         (引数) req:not null<br>
     *         (引数) res:not null<br>
     *         (状態) event:"EVENT_FIELD"<br>
     *         (状態) eventField:"event"<br>
     *         (状態) params:["event"：{"forward_success1"}]<br>
     *                ["EVENT_FIELD"：{"forward_success"}]<br>
     *         (状態) forward:"success"<br>
     *         (状態) attribute:"SERVER_BLOCKAGE_THRU_KEY"："THRU"<br>
     *         
     * <br>
     * 期待値：(戻り値) ActionForward:mappingに設定されていたActionFoward<br>
     *                  (Name："success")<br>
     *         (状態変化) restrictionEscapePaths:"SERVER_BLOCKAGE_THRU_KEY"と"BLOCKAGE_THRU_KEY"が存在しない<br>
     *         
     * <br>
     * mappingのイベントフィールド値が"EVENT_FIELD"の場合、リクエストのパラメータ値よりキー："EVENT_FIELD"として値を取得し、マッピングに設定されているActionForward"success"が返却されることを確認する。<br>
     * 閉塞チェックフラグ"SERVER_BLOCKAGE_THRU_KEY"と"BLOCKAGE_THRU_KEY"が存在しないことを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testDoExecute04() throws Exception {
        // ActionMappingを作成
        ActionMappingEx mapping = new ActionMappingEx();

        // "success"のアクションフォワードの生成
        ActionForward forward = new ActionForward();
        forward.setName("success");
        // ModuleConfigを作成
        ModuleConfig mConfig = new ModuleConfigImpl("");
        // 返却されるActionForwardを指定
        mConfig.addForwardConfig(forward);
        // モジュールコンフィグのforward属性指定
        mapping.setModuleConfig(mConfig);

        // ActionFormExの作成
        DispatchAction_DynaValidatorActionFormExStub01 formEx =
            new DispatchAction_DynaValidatorActionFormExStub01();

        // HTTPリクエストの生成
        MockHttpServletRequest req = new MockHttpServletRequest();
        String[] eventParamValues1 = { "forward_success1" };
        req.setParameter("event", eventParamValues1);
        String[] eventParamValues2 = { "forward_success" };
        req.setParameter("EVENT_FIELD", eventParamValues2);
        // 属性の設定
        req.setAttribute("SERVER_BLOCKAGE_THRU_KEY", "THRU");

        // HTTPレスポンスの生成
        MockHttpServletResponse res = new MockHttpServletResponse();

        // DispatchAction生成
        DispatchAction action = new DispatchAction();
        
        // event："EVENT_FIELD"
        UTUtil.setPrivateField(action, "event", "EVENT_FIELD");

        // テスト実行
        ActionForward ret = action.doExecute(mapping, formEx, req, res);

        // 結果の確認
        // マッピングに設定されている"success"のアクションフォワードを返却すること
        assertEquals("success", ret.getName());
        // 属性が削除されていること
        assertNull(req.getAttribute("SERVER_BLOCKAGE_THRU_KEY"));
        assertNull(req.getAttribute("BLOCKAGE_THRU_KEY"));
    }

    /**
     * testDoExecute05()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) mapping:Input："/input.jsp"<br>
     *                ActionForwardName："success"<br>
     *         (引数) form:not null<br>
     *         (引数) req:not null<br>
     *         (引数) res:not null<br>
     *         (状態) event:null<br>
     *         (状態) eventField:"event"<br>
     *         (状態) params:["event"：{"forward_#input"}]<br>
     *                ["EVENT_FIELD"：{"forward_success"}]<br>
     *         (状態) forward:"#input"<br>
     *         (状態) attribute:"SERVER_BLOCKAGE_THRU_KEY"："THRU"<br>
     *                "BLOCKAGE_THRU_KEY"："BLOCK"<br>
     *         
     * <br>
     * 期待値：(戻り値) ActionForward:新たに作成したActionFoward<br>
     *                  （Path："/input.jsp"）<br>
     *         (状態変化) restrictionEscapePaths:"SERVER_BLOCKAGE_THRU_KEY"と"BLOCKAGE_THRU_KEY"が存在しない<br>
     *         
     * <br>
     * リクエストのパラメータ値が"forward_#input"で、マッピングにinput属性とアクションフォワード名の両方を設定した場合、Input属性をパス名とするActionForwardが新たに生成され、返却されることを確認する。<br>
     * 閉塞チェックフラグ"SERVER_BLOCKAGE_THRU_KEY"と"BLOCKAGE_THRU_KEY"が存在しないことを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testDoExecute05() throws Exception {
        // ActionMappingを作成
        ActionMappingEx mapping = new ActionMappingEx();
        mapping.setInput("/input.jsp");

        // "success"のアクションフォワードの生成
        ActionForward forward = new ActionForward();
        forward.setName("success");
        // ModuleConfigを作成
        ModuleConfig mConfig = new ModuleConfigImpl("");
        // 返却されるActionForwardを指定
        mConfig.addForwardConfig(forward);
        // モジュールコンフィグのforward属性指定
        mapping.setModuleConfig(mConfig);

        // ActionFormExの作成
        DispatchAction_DynaValidatorActionFormExStub01 formEx =
            new DispatchAction_DynaValidatorActionFormExStub01();

        // HTTPリクエストの生成
        MockHttpServletRequest req = new MockHttpServletRequest();
        String[] eventParamValues1 = { "forward_#input" };
        req.setParameter("event", eventParamValues1);
        String[] eventParamValues2 = { "forward_success" };
        req.setParameter("EVENT_FIELD", eventParamValues2);
        // 属性の設定
        req.setAttribute("SERVER_BLOCKAGE_THRU_KEY", "THRU");
        req.setAttribute("BLOCKAGE_THRU_KEY", "BLOCK");

        // HTTPレスポンスの生成
        MockHttpServletResponse res = new MockHttpServletResponse();

        // DispatchAction生成
        DispatchAction action = new DispatchAction();

        // テスト実行
        ActionForward ret = action.doExecute(mapping, formEx, req, res);

        // 結果の確認
        // マッピングに設定されている"/input.jsp"のアクションフォワードを返却すること
        assertEquals("/input.jsp", ret.getPath());
        // 属性が削除されていること
        assertNull(req.getAttribute("SERVER_BLOCKAGE_THRU_KEY"));
        assertNull(req.getAttribute("BLOCKAGE_THRU_KEY"));
    }

    /**
     * testDoExecute06()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) mapping:Input："/input.jsp"<br>
     *                ActionForwardName："success"<br>
     *         (引数) form:not null<br>
     *         (引数) req:not null<br>
     *         (引数) res:not null<br>
     *         (状態) event:null<br>
     *         (状態) eventField:"event"<br>
     *         (状態) params:["event"：{"forward_#INPUT"}]<br>
     *                ["EVENT_FIELD"：{"forward_success"}]<br>
     *         (状態) forward:"#INPUT"<br>
     *         (状態) attribute:"SERVER_BLOCKAGE_THRU_KEY"："THRU"<br>
     *                "BLOCKAGE_THRU_KEY"："BLOCK"<br>
     *         
     * <br>
     * 期待値：(戻り値) ActionForward:新たに作成したActionFoward<br>
     *                  （Path："/input.jsp"）<br>
     *         (状態変化) restrictionEscapePaths:"SERVER_BLOCKAGE_THRU_KEY"と"BLOCKAGE_THRU_KEY"が存在しない<br>
     *         
     * <br>
     * リクエストのパラメータ値が"forward_#INPUT"と大文字の場合でも、Input属性をパス名とするActionForwardが新たに生成され、返却されることを確認する。<br>
     * 閉塞チェックフラグ"SERVER_BLOCKAGE_THRU_KEY"と"BLOCKAGE_THRU_KEY"が存在しないことを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testDoExecute06() throws Exception {
        // ActionMappingを作成
        ActionMappingEx mapping = new ActionMappingEx();
        mapping.setInput("/input.jsp");

        // "success"のアクションフォワードの生成
        ActionForward forward = new ActionForward();
        forward.setName("success");
        // ModuleConfigを作成
        ModuleConfig mConfig = new ModuleConfigImpl("");
        // 返却されるActionForwardを指定
        mConfig.addForwardConfig(forward);
        // モジュールコンフィグのforward属性指定
        mapping.setModuleConfig(mConfig);

        // ActionFormExの作成
        DispatchAction_DynaValidatorActionFormExStub01 formEx =
            new DispatchAction_DynaValidatorActionFormExStub01();

        // HTTPリクエストの生成
        MockHttpServletRequest req = new MockHttpServletRequest();
        String[] eventParamValues1 = { "forward_#INPUT" };
        req.setParameter("event", eventParamValues1);
        String[] eventParamValues2 = { "forward_success" };
        req.setParameter("EVENT_FIELD", eventParamValues2);
        // 属性の設定
        req.setAttribute("SERVER_BLOCKAGE_THRU_KEY", "THRU");
        req.setAttribute("BLOCKAGE_THRU_KEY", "BLOCK");

        // HTTPレスポンスの生成
        MockHttpServletResponse res = new MockHttpServletResponse();

        // DispatchAction生成
        DispatchAction action = new DispatchAction();

        // テスト実行
        ActionForward ret = action.doExecute(mapping, formEx, req, res);

        // 結果の確認
        // マッピングに設定されている"/input.jsp"のアクションフォワードを返却すること
        assertEquals("/input.jsp", ret.getPath());
        // 属性が削除されていること
        assertNull(req.getAttribute("SERVER_BLOCKAGE_THRU_KEY"));
        assertNull(req.getAttribute("BLOCKAGE_THRU_KEY"));
    }

    /**
     * testDoExecute07()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) mapping:ActionForwardName："success"<br>
     *                ActionForwardName："default"<br>
     *         (引数) form:not null<br>
     *         (引数) req:not null<br>
     *         (引数) res:not null<br>
     *         (状態) event:null<br>
     *         (状態) eventField:"event"<br>
     *         (状態) params:["not_event"：{"forward_success"}]<br>
     *         (状態) forward:"default"<br>
     *         (状態) attribute:"SERVER_BLOCKAGE_THRU_KEY"："THRU"<br>
     *                "BLOCKAGE_THRU_KEY"："BLOCK"<br>
     *         
     * <br>
     * 期待値：(戻り値) ActionForward:mappingに設定されていたActionFoward<br>
     *                  (Name："default")<br>
     *         (状態変化) restrictionEscapePaths:"SERVER_BLOCKAGE_THRU_KEY"と"BLOCKAGE_THRU_KEY"が存在しない<br>
     *         
     * <br>
     * "event"に該当するリクエストパラメータ値が存在しないため論理フォワード名が"default"である場合、マッピングに設定されているActionForward"default"が返却されることを確認する。<br>
     * 閉塞チェックフラグ"SERVER_BLOCKAGE_THRU_KEY"と"BLOCKAGE_THRU_KEY"が存在しないことを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testDoExecute07() throws Exception {
        // ActionMappingを作成
        ActionMappingEx mapping = new ActionMappingEx();

        // "success"のアクションフォワードの生成
        ActionForward forward = new ActionForward();
        forward.setName("success");
        // "default"のアクションフォワードの生成
        ActionForward dForward = new ActionForward();
        dForward.setName("default");
        // ModuleConfigを作成
        ModuleConfig mConfig = new ModuleConfigImpl("");
        // 返却されるActionForwardを指定
        mConfig.addForwardConfig(forward);
        mConfig.addForwardConfig(dForward);
        // モジュールコンフィグのforward属性指定
        mapping.setModuleConfig(mConfig);

        // ActionFormExの作成
        DispatchAction_DynaValidatorActionFormExStub01 formEx =
            new DispatchAction_DynaValidatorActionFormExStub01();

        // HTTPリクエストの生成
        MockHttpServletRequest req = new MockHttpServletRequest();
        String[] eventParamValues1 = { "forward_success" };
        req.setParameter("not_event", eventParamValues1);
        // 属性の設定
        req.setAttribute("SERVER_BLOCKAGE_THRU_KEY", "THRU");
        req.setAttribute("BLOCKAGE_THRU_KEY", "BLOCK");

        // HTTPレスポンスの生成
        MockHttpServletResponse res = new MockHttpServletResponse();

        // DispatchAction生成
        DispatchAction action = new DispatchAction();

        // テスト実行
        ActionForward ret = action.doExecute(mapping, formEx, req, res);

        // 結果の確認
        // マッピングに設定されている"default"のアクションフォワードを返却すること
        assertEquals("default", ret.getName());
        // 属性が削除されていること
        assertNull(req.getAttribute("SERVER_BLOCKAGE_THRU_KEY"));
        assertNull(req.getAttribute("BLOCKAGE_THRU_KEY"));
    }

    /**
     * testDoExecute08()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：AC
     * <br><br>
     * 入力値：(引数) mapping:ActionForwardName："success"<br>
     *         (引数) form:not null<br>
     *         (引数) req:not null<br>
     *         (引数) res:not null<br>
     *         (状態) event:null<br>
     *         (状態) eventField:"event"<br>
     *         (状態) params:["event"：{"forward_"}]<br>
     *         (状態) forward:""<br>
     *         (状態) attribute:"SERVER_BLOCKAGE_THRU_KEY"："THRU"<br>
     *                "BLOCKAGE_THRU_KEY"："BLOCK"<br>
     *         
     * <br>
     * 期待値：(戻り値) ActionForward:null<br>
     *         (状態変化) restrictionEscapePaths:"SERVER_BLOCKAGE_THRU_KEY"と"BLOCKAGE_THRU_KEY"が存在しない<br>
     *         
     * <br>
     * "event"に該当するリクエストのパラメータ値が"forward_"の場合、マッピングに設定されているActionForward""が存在しないためnullのアクションフォワードが返却されることを確認する。<br>
     * 閉塞チェックフラグ"SERVER_BLOCKAGE_THRU_KEY"と"BLOCKAGE_THRU_KEY"が存在しないことを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testDoExecute08() throws Exception {
        // ActionMappingを作成
        ActionMappingEx mapping = new ActionMappingEx();

        // "success"のアクションフォワードの生成
        ActionForward forward = new ActionForward();
        forward.setName("success");
        // ModuleConfigを作成
        ModuleConfig mConfig = new ModuleConfigImpl("");
        // 返却されるActionForwardを指定
        mConfig.addForwardConfig(forward);
        // モジュールコンフィグのforward属性指定
        mapping.setModuleConfig(mConfig);

        // ActionFormExの作成
        DispatchAction_DynaValidatorActionFormExStub01 formEx =
            new DispatchAction_DynaValidatorActionFormExStub01();

        // HTTPリクエストの生成
        MockHttpServletRequest req = new MockHttpServletRequest();
        String[] eventParamValues1 = { "forward_" };
        req.setParameter("event", eventParamValues1);
        // 属性の設定
        req.setAttribute("SERVER_BLOCKAGE_THRU_KEY", "THRU");
        req.setAttribute("BLOCKAGE_THRU_KEY", "BLOCK");

        // HTTPレスポンスの生成
        MockHttpServletResponse res = new MockHttpServletResponse();

        // DispatchAction生成
        DispatchAction action = new DispatchAction();

        // テスト実行
        ActionForward ret = action.doExecute(mapping, formEx, req, res);

        // 結果の確認
        // nullのアクションフォワードを返却すること
        assertNull(ret);
        // 属性が削除されていること
        assertNull(req.getAttribute("SERVER_BLOCKAGE_THRU_KEY"));
        assertNull(req.getAttribute("BLOCKAGE_THRU_KEY"));
    }

    /**
     * testDoExecute09()
     * <br><br>
     * 
     * (異常系)
     * <br>
     * 観点：CG
     * <br><br>
     * 入力値：(引数) mapping:not null<br>
     *         (引数) form:not null<br>
     *         (引数) req:{CANCEL_KEY != null}<br>
     *         (引数) res:not null<br>
     *         (状態) event:null<br>
     *         (状態) eventField:*<br>
     *         (状態) params:not null<br>
     *         (状態) attribute:"SERVER_BLOCKAGE_THRU_KEY"："THRU"<br>
     *                "BLOCKAGE_THRU_KEY"："BLOCK"<br>
     *         
     * <br>
     * 期待値：(戻り値) ActionForward:cancellメソッドから返却したアクションパス<br>
     *         (状態変化) restrictionEscapePaths:"SERVER_BLOCKAGE_THRU_KEY"と"BLOCKAGE_THRU_KEY"が存在する<br>
     *         
     * <br>
     * リクエストにてCANCEL_KEYが存在する場合、cancellメソッドを呼び出すことを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testDoExecute09() throws Exception {
        // ActionMappingを作成
        ActionMappingEx mapping = new ActionMappingEx();

        // ActionFormExの作成
        DispatchAction_DynaValidatorActionFormExStub01 formEx =
            new DispatchAction_DynaValidatorActionFormExStub01();

        // HTTPリクエストの生成
        MockHttpServletRequest req = new MockHttpServletRequest();
        req.setAttribute(Globals.CANCEL_KEY, "true");
        UTUtil.setPrivateField(req, "params", null);
        // 属性の設定
        req.setAttribute("SERVER_BLOCKAGE_THRU_KEY", "THRU");
        req.setAttribute("BLOCKAGE_THRU_KEY", "BLOCK");

        // HTTPレスポンスの生成
        MockHttpServletResponse res = new MockHttpServletResponse();

        // DispatchAction生成
        DispatchAction action = new DispatchActionImpl01();

        // テスト実行
        ActionForward ret = action.doExecute(mapping, formEx, req, res);
        
        assertEquals("cancelled", ret.getName());
        
        // 属性が削除されていない
        assertEquals("THRU", req.getAttribute("SERVER_BLOCKAGE_THRU_KEY"));
        assertEquals("BLOCK", req.getAttribute("BLOCKAGE_THRU_KEY"));
    }

    /**
     * testDoExecute10()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) mapping:ActionForwardName："success"<br>
     *         (引数) form:not null<br>
     *         (引数) req:{CANCEL_KEY != null}<br>
     *         (引数) res:not null<br>
     *         (状態) event:"EVENT_FIELD"<br>
     *         (状態) eventField:"event"<br>
     *         (状態) params:["event"：{"forward_success1"}]<br>
     *                ["EVENT_FIELD"：{"forward_success"}]<br>
     *         (状態) forward:"success"<br>
     *         (状態) attribute:"SERVER_BLOCKAGE_THRU_KEY"："THRU"<br>
     *         
     * <br>
     * 期待値：(戻り値) ActionForward:mappingに設定されていたActionFoward<br>
     *                  (Name："success")<br>
     *         (状態変化) restrictionEscapePaths:"SERVER_BLOCKAGE_THRU_KEY"と"BLOCKAGE_THRU_KEY"が存在しない<br>
     *         
     * <br>
     * リクエストにてCANCEL_KEYが存在し、cancellメソッドがnullを返却したとき、マッピングに設定されているActionForward"success"が返却されることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testDoExecute10() throws Exception {
        // ActionMappingを作成
        ActionMappingEx mapping = new ActionMappingEx();

        // "success"のアクションフォワードの生成
        ActionForward forward = new ActionForward();
        forward.setName("success");
        // ModuleConfigを作成
        ModuleConfig mConfig = new ModuleConfigImpl("");
        // 返却されるActionForwardを指定
        mConfig.addForwardConfig(forward);
        // モジュールコンフィグのforward属性指定
        mapping.setModuleConfig(mConfig);

        // ActionFormExの作成
        DispatchAction_DynaValidatorActionFormExStub01 formEx =
            new DispatchAction_DynaValidatorActionFormExStub01();

        // HTTPリクエストの生成
        MockHttpServletRequest req = new MockHttpServletRequest();
        String[] eventParamValues1 = { "forward_success1" };
        req.setParameter("event", eventParamValues1);
        String[] eventParamValues2 = { "forward_success" };
        req.setParameter("EVENT_FIELD", eventParamValues2);
        req.setAttribute(Globals.CANCEL_KEY, "true");
        // 属性の設定
        req.setAttribute("SERVER_BLOCKAGE_THRU_KEY", "THRU");

        // HTTPレスポンスの生成
        MockHttpServletResponse res = new MockHttpServletResponse();

        // DispatchAction生成
        DispatchAction action = new DispatchAction();
        
        // event："EVENT_FIELD"
        UTUtil.setPrivateField(action, "event", "EVENT_FIELD");

        // テスト実行
        ActionForward ret = action.doExecute(mapping, formEx, req, res);

        // 結果の確認
        // マッピングに設定されている"success"のアクションフォワードを返却すること
        assertEquals("success", ret.getName());
        // 属性が削除されていること
        assertNull(req.getAttribute("SERVER_BLOCKAGE_THRU_KEY"));
        assertNull(req.getAttribute("BLOCKAGE_THRU_KEY"));
    }

    /**
     * testDoExecute11()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) mapping:ActionForwardName："success"<br>
     *                ActionForwardName："default"<br>
     *         (引数) form:not null<br>
     *         (引数) req:not null<br>
     *         (引数) res:not null<br>
     *         (状態) event:"EVENT_FIELD"<br>
     *         (状態) eventField:"event"<br>
     *         (状態) params:["event"：{"forward_XXXX"}]<br>
     *         (状態) forward:"default"<br>
     *         (状態) attribute:"SERVER_BLOCKAGE_THRU_KEY"："THRU"<br>
     *         
     * <br>
     * 期待値：(戻り値) ActionForward:mappingに設定されていたActionFoward<br>
     *                  (Name："default")<br>
     *         (状態変化) restrictionEscapePaths:"SERVER_BLOCKAGE_THRU_KEY"と"BLOCKAGE_THRU_KEY"が存在しない<br>
     *
     * <br>
     * ActionMapping の論理フォワード名："default"が設定されている前提で、
     * "event"に該当するリクエストパラメータ値が"forward_XXXX"
     * （XXXX: ActionMapping には存在しない論理フォワード名）のとき、
     * 返却値として "default"のActionMapping が返却されることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testDoExecute11() throws Exception {
        // ActionMappingを作成
        ActionMappingEx mapping = new ActionMappingEx();

        // "success"のアクションフォワードの生成
        ActionForward forwardSuccess = new ActionForward();
        forwardSuccess.setName("success");
        // "default"のアクションフォワードの生成
        ActionForward forwardDefault = new ActionForward();
        forwardDefault.setName("default");

        // ModuleConfigを作成
        ModuleConfig mConfig = new ModuleConfigImpl("");
        // 返却されるActionForwardを指定
        mConfig.addForwardConfig(forwardSuccess);
        mConfig.addForwardConfig(forwardDefault);
        // モジュールコンフィグのforward属性指定
        mapping.setModuleConfig(mConfig);

        // ActionFormExの作成
        DispatchAction_DynaValidatorActionFormExStub01 formEx =
            new DispatchAction_DynaValidatorActionFormExStub01();

        // HTTPリクエストの生成
        MockHttpServletRequest req = new MockHttpServletRequest();
        String[] eventParamValues1 = { "forward_XXXX" };
        req.setParameter("event", eventParamValues1);
        // 属性の設定
        req.setAttribute("SERVER_BLOCKAGE_THRU_KEY", "THRU");

        // HTTPレスポンスの生成
        MockHttpServletResponse res = new MockHttpServletResponse();

        // DispatchAction生成
        DispatchAction action = new DispatchAction();
        
        // テスト実行
        ActionForward ret = action.doExecute(mapping, formEx, req, res);

        // 結果の確認
        // マッピングに設定されている"default"のアクションフォワードを返却すること
        assertNotNull(ret);
        assertEquals("default", ret.getName());
        // 属性が削除されていること
        assertNull(req.getAttribute("SERVER_BLOCKAGE_THRU_KEY"));
        assertNull(req.getAttribute("BLOCKAGE_THRU_KEY"));
    }
    
    /**
     * testDoExecute12()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) mapping:ActionForwardName："success"<br>
     *                ActionForwardName："default"<br>
     *         (引数) form:not null<br>
     *         (引数) req:not null<br>
     *         (引数) res:not null<br>
     *         (状態) event:"EVENT_FIELD"<br>
     *         (状態) eventField:"event"<br>
     *         (状態) params:["forward_XXXX"：{"ABC"}]<br>
     *         (状態) forward:"default"<br>
     *         (状態) attribute:"SERVER_BLOCKAGE_THRU_KEY"："THRU"<br>
     *         
     * <br>
     * 期待値：(戻り値) ActionForward:mappingに設定されていたActionFoward<br>
     *                  (Name："default")<br>
     *         (状態変化) restrictionEscapePaths:"SERVER_BLOCKAGE_THRU_KEY"と"BLOCKAGE_THRU_KEY"が存在しない<br>
     *
     * <br>
     * ActionMapping の論理フォワード名："default"が設定されている前提で、
     * "event"に該当するリクエストパラメータ値が"forward_XXXX"
     * （XXXX: ActionMapping には存在しない論理フォワード名）のとき、
     * 返却値として "default"のActionMapping が返却されることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testDoExecute12() throws Exception {
        // ActionMappingを作成
        ActionMappingEx mapping = new ActionMappingEx();

        // "success"のアクションフォワードの生成
        ActionForward forwardSuccess = new ActionForward();
        forwardSuccess.setName("success");
        // "default"のアクションフォワードの生成
        ActionForward forwardDefault = new ActionForward();
        forwardDefault.setName("default");

        // ModuleConfigを作成
        ModuleConfig mConfig = new ModuleConfigImpl("");
        // 返却されるActionForwardを指定
        mConfig.addForwardConfig(forwardSuccess);
        mConfig.addForwardConfig(forwardDefault);
        // モジュールコンフィグのforward属性指定
        mapping.setModuleConfig(mConfig);

        // ActionFormExの作成
        DispatchAction_DynaValidatorActionFormExStub01 formEx =
            new DispatchAction_DynaValidatorActionFormExStub01();

        // HTTPリクエストの生成
        MockHttpServletRequest req = new MockHttpServletRequest();
        String[] eventParamValues1 = { "ABC" };
        req.setParameter("forward_XXXX", eventParamValues1);
        // 属性の設定
        req.setAttribute("SERVER_BLOCKAGE_THRU_KEY", "THRU");

        // HTTPレスポンスの生成
        MockHttpServletResponse res = new MockHttpServletResponse();

        // DispatchAction生成
        DispatchAction action = new DispatchAction();
        
        // テスト実行
        ActionForward ret = action.doExecute(mapping, formEx, req, res);

        // 結果の確認
        // マッピングに設定されている"success"のアクションフォワードを返却すること
        assertNotNull(ret);
        assertEquals("default", ret.getName());
        // 属性が削除されていること
        assertNull(req.getAttribute("SERVER_BLOCKAGE_THRU_KEY"));
        assertNull(req.getAttribute("BLOCKAGE_THRU_KEY"));
    }
    
    /**
     * testDoDetermineForward01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) params:["event"：{"forward_success1"}]<br>
     *                ["forward_success2"：{"ABC"}]<br>
     *         (引数) event:"event"<br>
     *         
     * <br>
     * 期待値：(戻り値) String:"success1"<br>
     *         
     * <br>
     * paramsにキー"event"が存在し、その値が"forward_"から始まっていれば、"forward_"を除いた文字列を返却することを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testDoDetermineForward01() throws Exception {
        // リクエストパラメータ（マップ形式）生成
        HashMap params = new HashMap();
        String[] eventParamValues1 = { "forward_success1" };
        params.put("event", eventParamValues1);
        String[] eventParamValues2 = { "ABC" };
        params.put("forward_success2", eventParamValues2);

        // DispatchAction生成
        DispatchAction action = new DispatchAction();

        // テスト実行
        String ret = action.doDetermineForward(params, "event");

        // 結果確認
        assertEquals("success1", ret);
    }

    /**
     * testDoDetermineForward02()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) params:["event"：{"forward1_success","forward_success1"}]<br>
     *                ["forward_success2"：{"ABC"}]<br>
     *         (引数) event:"event"<br>
     *         
     * <br>
     * 期待値：(戻り値) String:"success1"<br>
     *         
     * <br>
     * paramsにキー"event"が存在し、その内1つの値が"forward_"から始まっていれば、"forward_"を除いた文字列が返却されることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testDoDetermineForward02() throws Exception {
        // リクエストパラメータ（マップ形式）生成
        HashMap params = new HashMap();
        String[] eventParamValues1 = { "forward1_success", "forward_success1" };
        params.put("event", eventParamValues1);
        String[] eventParamValues2 = { "ABC" };
        params.put("forward_success2", eventParamValues2);

        // DispatchAction生成
        DispatchAction action = new DispatchAction();

        // テスト実行
        String ret = action.doDetermineForward(params, "event");

        // 結果確認
        assertEquals("success1", ret);
    }

    /**
     * testDoDetermineForward03()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) params:["event"：{"success1","success2"}]<br>
     *                ["forward_success2"：{"ABC"}]<br>
     *         (引数) event:"event"<br>
     *         
     * <br>
     * 期待値：(戻り値) String:"success2"<br>
     *         
     * <br>
     * paramsにキー"event"が存在し、その値に"forward_"から始まるものが無い場合、"forward_"から始まるキーがあれば、"forward_"を除いた文字列を返却することを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testDoDetermineForward03() throws Exception {
        // リクエストパラメータ（マップ形式）生成
        HashMap params = new HashMap();
        String[] eventParamValues1 = { "success1", "success2" };
        params.put("event", eventParamValues1);
        String[] eventParamValues2 = { "ABC" };
        params.put("forward_success2", eventParamValues2);

        // DispatchAction生成
        DispatchAction action = new DispatchAction();

        // テスト実行
        String ret = action.doDetermineForward(params, "event");

        // 結果確認
        assertEquals("success2", ret);
    }

    /**
     * testDoDetermineForward04()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) params:["not_event"：{"forward_success1"}]<br>
     *                ["forward_success2"：{"ABC"}]<br>
     *         (引数) event:"event"<br>
     *         
     * <br>
     * 期待値：(戻り値) String:"success2"<br>
     *         
     * <br>
     * paramsにキー"event"が存在しない場合、"forward_"から始まるキーがあれば、"forward_"を除いた文字列を返却することを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testDoDetermineForward04() throws Exception {
        // リクエストパラメータ（マップ形式）生成
        HashMap params = new HashMap();
        String[] eventParamValues1 = { "forward_success1" };
        params.put("not_event", eventParamValues1);
        String[] eventParamValues2 = { "ABC" };
        params.put("forward_success2", eventParamValues2);

        // DispatchAction生成
        DispatchAction action = new DispatchAction();

        // テスト実行
        String ret = action.doDetermineForward(params, "event");

        // 結果確認
        assertEquals("success2", ret);
    }

    /**
     * testDoDetermineForward05()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) params:["not_event"："forward_success1"]<br>
     *                ["not_forward_success2"：{"ABC"}]<br>
     *         (引数) event:"event"<br>
     *         
     * <br>
     * 期待値：(戻り値) String:"default"<br>
     *         
     * <br>
     * paramsにキー"event"が存在せず、さらに"forward_"から始まるキーが存在しない場合、"default"が返却されることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testDoDetermineForward05() throws Exception {
        // リクエストパラメータ（マップ形式）生成
        HashMap params = new HashMap();
        String[] eventParamValues1 = { "forward_success1" };
        params.put("not_event", eventParamValues1);
        String[] eventParamValues2 = { "ABC" };
        params.put("not_forward_success2", eventParamValues2);

        // DispatchAction生成
        DispatchAction action = new DispatchAction();

        // テスト実行
        String ret = action.doDetermineForward(params, "event");

        // 結果確認
        assertEquals("default", ret);
    }

    /**
     * testDoDetermineForward06()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) params:["event"：{"forward_"}]<br>
     *                ["forward_success2"：{"ABC"}]<br>
     *         (引数) event:"event"<br>
     *         
     * <br>
     * 期待値：(戻り値) String:""<br>
     *         
     * <br>
     * paramsにキー"event"が存在し、その値が"forward_"の場合、空文字が返却されることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testDoDetermineForward06() throws Exception {
        // リクエストパラメータ（マップ形式）生成
        HashMap params = new HashMap();
        String[] eventParamValues1 = { "forward_" };
        params.put("event", eventParamValues1);
        String[] eventParamValues2 = { "ABC" };
        params.put("not_forward_success2", eventParamValues2);

        // DispatchAction生成
        DispatchAction action = new DispatchAction();

        // テスト実行
        String ret = action.doDetermineForward(params, "event");

        // 結果確認
        assertEquals("", ret);
    }

    /**
     * testDoDetermineForward07()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：AC
     * <br><br>
     * 入力値：(引数) params:空のMap<br>
     *         (引数) event:"event"<br>
     *         
     * <br>
     * 期待値：(戻り値) String:"default"<br>
     *         
     * <br>
     * paramsが空の場合、"default"が返却されることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testDoDetermineForward07() throws Exception {
        // リクエストパラメータ（マップ形式）生成
        HashMap params = new HashMap();

        // DispatchAction生成
        DispatchAction action = new DispatchAction();

        // テスト実行
        String ret = action.doDetermineForward(params, "event");

        // 結果確認
        assertEquals("default", ret);
    }

    /**
     * testDoDetermineForward08()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) params:["event"：{"forward_success1"}]<br>
     *                ["forward_success2"：{"forward_ABC","forward_success"}]<br>
     *         (引数) event:"forward_success2"<br>
     *         
     * <br>
     * 期待値：(戻り値) String:"ABC"<br>
     *         
     * <br>
     * eventFieldを"forward_success2"にした場合、paramsにキーが存在し、その値が"forward_"から始まっていれば、"forward_"を除いた文字列を返却することを確認する。<br>
     * "forward_"から始まる値が複数個存在する場合は、indexの最も若い値の"forward_"を除いた文字列が返却されることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testDoDetermineForward08() throws Exception {
        // リクエストパラメータ（マップ形式）生成
        HashMap params = new HashMap();
        String[] eventParamValues1 = { "forward_success1" };
        params.put("event", eventParamValues1);
        String[] eventParamValues2 = { "forward_ABC", "forward_success" };
        params.put("forward_success2", eventParamValues2);

        // DispatchAction生成
        DispatchAction action = new DispatchAction();

        // テスト実行
        String ret = action.doDetermineForward(params, "forward_success2");

        // 結果確認
        assertEquals("ABC", ret);
    }

    /**
     * testDoDetermineForward09()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) params:["event"：{"forward_success1"}]<br>
     *                ["forward_"：{"forward_ABC"}]<br>
     *         (引数) event:""<br>
     *         
     * <br>
     * 期待値：(戻り値) String:""<br>
     *         
     * <br>
     * eventFieldを空文字に設定し、paramsから取得した値が"forward_"の場合、空文字を返却することを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testDoDetermineForward09() throws Exception {
        // リクエストパラメータ（マップ形式）生成
        HashMap params = new HashMap();
        String[] eventParamValues1 = { "forward_success1" };
        params.put("event", eventParamValues1);
        String[] eventParamValues2 = { "forward_ABC" };
        params.put("forward_", eventParamValues2);

        // DispatchAction生成
        DispatchAction action = new DispatchAction();

        // テスト実行
        String ret = action.doDetermineForward(params, "");

        // 結果確認
        assertEquals("", ret);
    }

    /**
     * testDoDetermineForward10()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：AC
     * <br><br>
     * 入力値：(引数) params:[""：{"forward_success1"}]<br>
     *                ["forward_success2"：{"ABC"}]<br>
     *         (引数) event:""<br>
     *         
     * <br>
     * 期待値：(戻り値) String:"success1"<br>
     *         
     * <br>
     * eventFieldを空文字にした場合、paramsにキーが存在し、その値が"forward_"から始まっていれば、"forward_"を除いた文字列を返却することを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testDoDetermineForward10() throws Exception {
        // リクエストパラメータ（マップ形式）生成
        HashMap params = new HashMap();
        String[] eventParamValues1 = { "forward_success1" };
        params.put("", eventParamValues1);
        String[] eventParamValues2 = { "ABC" };
        params.put("forward_success2", eventParamValues2);

        // DispatchAction生成
        DispatchAction action = new DispatchAction();

        // テスト実行
        String ret = action.doDetermineForward(params, "");

        // 結果確認
        assertEquals("success1", ret);
    }

    /**
     * testDoDetermineForward11()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) params:null<br>
     *         
     * <br>
     * 期待値：(戻り値) String:"default"<br>
     *         
     * <br>
     * paramsがnullの場合、"default"が返却されることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testDoDetermineForward11() throws Exception {
        // DispatchAction生成
        DispatchAction action = new DispatchAction();

        // テスト実行
        String ret = action.doDetermineForward(null, "event");
        
        // 結果確認
        assertEquals("default", ret);
    }
    
    /**
     * testDoDetermineForward12()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：AC
     * <br><br>
     * 入力値：(引数) params:["forward_success.x"：{"100"}]<br>
     *                ["forward_success.y"：{"200"}]<br>
     *         (引数) event:""<br>
     *         
     * <br>
     * 期待値：(戻り値) String:"success"<br>
     *         
     * <br>
     * eventFieldを空文字にした場合、paramsにキーが存在し、その値が"forward_"から始まり、".x"か".y"が終わっていれば、"forward_"と".x"と".y"を除いた文字列を返却することを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testDoDetermineForward12() throws Exception {
        // リクエストパラメータ（マップ形式）生成
        HashMap params = new HashMap();
        String[] eventParamValues1 = { "100" };
        params.put("forward_success.x", eventParamValues1);
        String[] eventParamValues2 = { "200" };
        params.put("forward_success.y", eventParamValues2);

        // DispatchAction生成
        DispatchAction action = new DispatchAction();

        // テスト実行
        String ret = action.doDetermineForward(params, "");

        // 結果確認
        assertEquals("success", ret);
    }


    /**
     * testExists01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) params:{["PARAM_NAME":"PARAM_VALUE"]}<br>
     *         (引数) name:"PARAM_NAME"<br>
     *         
     * <br>
     * 期待値：(戻り値) boolean:true<br>
     *         (状態変化) 例外:－<br>
     *         
     * <br>
     * paramsにnameをキーとする要素がある場合、trueが返却されることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testExists01() throws Exception {
        // リクエストパラメータ（マップ形式）生成
        HashMap params = new HashMap();
        params.put("PARAM_NAME", "PARAM_VALUE");

        // リクエストパラメータ名
        String name = "PARAM_NAME";

        // DispatchAction生成
        DispatchAction action = new DispatchAction();

        // テスト実行
        boolean ret = action.exists(params, name);

        // 結果確認
        assertTrue(ret);
    }

    /**
     * testExists02()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) params:空のMap<br>
     *         (引数) name:"PARAM_NAME"<br>
     *         
     * <br>
     * 期待値：(戻り値) boolean:false<br>
     *         (状態変化) 例外:－<br>
     *         
     * <br>
     * paramsにnameをキーとする要素がない場合、falseが返却されることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testExists02() throws Exception {
        // リクエストパラメータ（マップ形式）生成
        HashMap params = new HashMap();

        // リクエストパラメータ名
        String name = "PARAM_NAME";

        // DispatchAction生成
        DispatchAction action = new DispatchAction();

        // テスト実行
        boolean ret = action.exists(params, name);

        // 結果確認
        assertFalse(ret);
    }

    /**
     * testExists03()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) params:{["":"PARAM_VALUE"]}<br>
     *         (引数) name:""<br>
     *         
     * <br>
     * 期待値：(戻り値) boolean:true<br>
     *         (状態変化) 例外:－<br>
     *         
     * <br>
     * nameが空文字の場合でも例外が発生せずに、一般的な文字列と同様の処理が実行されることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testExists03() throws Exception {
        // リクエストパラメータ（マップ形式）生成
        HashMap params = new HashMap();
        params.put("", "PARAM_VALUE");

        // リクエストパラメータ名
        String name = "";

        // DispatchAction生成
        DispatchAction action = new DispatchAction();

        // テスト実行
        boolean ret = action.exists(params, name);

        // 結果確認
        assertTrue(ret);
    }

    /**
     * testExists04()
     * <br><br>
     * 
     * (異常系)
     * <br>
     * 観点：CG
     * <br><br>
     * 入力値：(引数) params:null<br>
     *         (引数) name:"PARAM_NAME"<br>
     *         
     * <br>
     * 期待値：(戻り値) boolean:－<br>
     *         (状態変化) 例外:NullPointerException<br>
     *         
     * <br>
     * paramsがnullの場合、NullPointerExceptionが発生することを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testExists04() throws Exception {
        // リクエストパラメータ（マップ形式）生成
        HashMap params = null;

        // リクエストパラメータ名
        String name = "PARAM_NAME";

        // DispatchAction生成
        DispatchAction action = new DispatchAction();

        try {
            // テスト実行
            action.exists(params, name);
            fail();
        } catch (NullPointerException npe) {
            // 結果確認
        }
    }

    /**
     * testCancelled01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) mapping:not null<br>
     *         (引数) form:not null<br>
     *         (引数) request:not null<br>
     *         (引数) response:not null<br>
     *         
     * <br>
     * 期待値：(戻り値) ActionForward:null<br>
     *         
     * <br>
     * nullが返却されること。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testCancelled01() throws Exception {
        // ActionMappingを作成
        ActionMappingEx mapping = new ActionMappingEx();
        
        // ActionFormExの作成
        DispatchAction_DynaValidatorActionFormExStub01 formEx =
            new DispatchAction_DynaValidatorActionFormExStub01();

        // HTTPリクエストの生成
        MockHttpServletRequest req = new MockHttpServletRequest();

        // HTTPレスポンスの生成
        MockHttpServletResponse res = new MockHttpServletResponse();

        
        // DispatchAction生成
        DispatchAction action = new DispatchAction();

        // テスト実施
        ActionForward af = action.cancelled(mapping, formEx, req, res);
        
        // 判定
        assertNull(af);
    }

    /**
     * testCancelled02()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) mapping:not null<br>
     *         (引数) form:null<br>
     *         (引数) request:not null<br>
     *         (引数) response:not null<br>
     *         
     * <br>
     * 期待値：(戻り値) ActionForward:null<br>
     *         
     * <br>
     * nullが返却されること。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testCancelled02() throws Exception {
        // ActionMappingを作成
        ActionMappingEx mapping = new ActionMappingEx();
        
        // HTTPリクエストの生成
        MockHttpServletRequest req = new MockHttpServletRequest();

        // HTTPレスポンスの生成
        MockHttpServletResponse res = new MockHttpServletResponse();

        
        // DispatchAction生成
        DispatchAction action = new DispatchAction();

        // テスト実施
        ActionForward af = action.cancelled(mapping, null, req, res);
        
        // 判定
        assertNull(af);
    }

}
