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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts.action.ActionForward;

import jp.terasoluna.fw.exception.SystemException;
import jp.terasoluna.fw.web.struts.action.ActionMappingEx;
import jp.terasoluna.utlib.LogUTUtil;
import jp.terasoluna.utlib.MockHttpServletRequest;
import jp.terasoluna.utlib.UTUtil;
import junit.framework.TestCase;

/**
 * {@link jp.terasoluna.fw.web.struts.actions.ClearSessionAction}
 * クラスのブラックボックステスト。
 * 
 * <p>
 * <h4>【クラスの概要】</h4>
 * セッションから指定されたプロパティを削除するクラス
 * <p>
 * 
 * @see jp.terasoluna.fw.web.struts.actions.ClearSessionAction
 */
@SuppressWarnings("unused")
public class ClearSessionActionTest extends TestCase {

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
    public ClearSessionActionTest(String name) {
        super(name);
    }

    /**
     * testSetClearSessionKeys01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) clearSessionKeys:ArrayListオブジェクト<br>
     *         (状態) clearSessionKeys:null<br>
     *         
     * <br>
     * 期待値：(状態変化) clearSessionKeys:引数と同一のArrayListオブジェクト<br>
     *         
     * <br>
     * 引数に指定した値がフィールドclearSessionKeysに正常に格納されることを確認
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetClearSessionKeys01() throws Exception {
        // 前処理
        // clearSessionKeys設定
        List<String> list = new ArrayList<String>();
        list.add("test");
        
        ClearSessionAction action = new ClearSessionAction();
        action.setClearSessionKeys(list);

        // テスト実行・結果確認
        assertSame(list, UTUtil.getPrivateField(action, "clearSessionKeys"));
    }

    /**
     * testDoExecute01()
     * <br><br>
     * 
     * (異常系)
     * <br>
     * 観点：G
     * <br><br>
     * 入力値：(引数) mapping:not null<br>
     *         (引数) form:not null<br>
     *         (引数) req:not null<br>
     *         (引数) res:not null<br>
     *         (状態) clearSessionKeys:null<br>
     *         (状態) path:null<br>
     *         (状態) res.sendError():IOException発生<br>
     *         
     * <br>
     * 期待値：(状態変化) 例外:SystemException：<br>
     *                    メッセージキー："error.forward.errorpage"<br>
     *                    ラップした例外：IOException<br>
     *         (状態変化) ログ:＜ログ＞<br>
     *                    エラーログ：<br>
     *                    "Error page(404) forwarding failed."<br>
     *         
     * <br>
     * mappingからパラメータの取得結果がnullであり、
     * HttpServletResponse.sendError()呼出で例外が発生した場合、
     * 指定したパスにセッションディレクトリが生成されたか確認する。
     * またSystemExceptionが発生することを確認
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testDoExecute01() throws Exception {
        // 前処理
        ClearSessionAction action = new ClearSessionAction();
        
        // パラメータの設定
        ActionMappingEx mapping = new ActionMappingEx();
        
        // path : null
        String path = null;
        mapping.setParameter(path);
        
        ClearSessionAction_ActionFormStub01 form =
            new ClearSessionAction_ActionFormStub01();
        
        MockHttpServletRequest req = new MockHttpServletRequest();
        
        // sendErrorの呼び出し確認用
        ClearSessionAction_HttpServletResponseStub02 res =
            new ClearSessionAction_HttpServletResponseStub02();
        
        ActionForward forward = null;

        // テスト実施
        try {
            forward = action.doExecute(mapping, form, req, res);
            fail("例外が発生しませんでした");
        } catch (SystemException e) {
            // 判定
            // IOExceptionをラップしたか
            assertEquals(IOException.class.getName(), e.getCause().getClass()
                    .getName());
            // エラーログ確認
            assertTrue(LogUTUtil.checkError(
                    "Error page(404) forwarding failed."));
            assertEquals("error.forward.errorpage", e.getErrorCode());
        }
    }

    /**
     * testDoExecute02()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) mapping:not null<br>
     *         (引数) form:not null<br>
     *         (引数) req:not null<br>
     *         (引数) res:not null<br>
     *         (状態) clearSessionKeys:0件のArrayList<br>
     *         (状態) path:null<br>
     *         
     * <br>
     * 期待値：(戻り値) actionForward:null<br>
     *         (状態変化) res.sendError():呼び出し確認<br>
     *         
     * <br>
     * clearSessionKeysが0件のArrayListであり、pathがnullだった場合、
     * res.sendRedirect()が呼び出されてnullを返すことを確認
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testDoExecute02() throws Exception {
        // 前処理
        ClearSessionAction action = new ClearSessionAction();
        
        // clearSessionKeys
        List<String> list = new ArrayList<String>();
        UTUtil.setPrivateField(action, "clearSessionKeys", list);
        
        // パラメータの設定
        ActionMappingEx mapping = new ActionMappingEx();
        
        // path : null
        String path = null;
        mapping.setParameter(path);
        
        ClearSessionAction_ActionFormStub01 form =
            new ClearSessionAction_ActionFormStub01();
        
        MockHttpServletRequest req = new MockHttpServletRequest();
        
        // sendErrorの呼び出し確認用
        ClearSessionAction_HttpServletResponseStub01 res =
            new ClearSessionAction_HttpServletResponseStub01();
        
        ActionForward forward = null;

        // テスト実施
        forward = action.doExecute(mapping, form, req, res);
        
        // 判定
        assertNull(forward);
        assertTrue(res.isSendError);
    }

    /**
     * testDoExecute03()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) mapping:not null<br>
     *         (引数) form:not null<br>
     *         (引数) req:not null<br>
     *         (引数) res:not null<br>
     *         (状態) clearSessionKeys:要素"abc"が入っているArrayList<br>
     *         (状態) session.getAttribute():session.getAttribute("abc")が"session01"<br>
     *         (状態) path:"abc"<br>
     *         
     * <br>
     * 期待値：(戻り値) actionForward:ActionForward.getPath()："abc"<br>
     *         (状態変化) session.getAttribute():session.getAttribute("abc")がnull<br>
     *         
     * <br>
     * clearSessionKeysが1件のArrayListの場合、その要素の文字列に該当する
     * sessionの属性が削除されていることと、画面遷移を行うことを確認
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testDoExecute03() throws Exception {
        // 前処理
        ClearSessionAction action = new ClearSessionAction();
        
        // clearSessionKeys
        List<String> list = new ArrayList<String>();
        list.add("abc");
        UTUtil.setPrivateField(action, "clearSessionKeys", list);
        
        // パラメータの設定
        ActionMappingEx mapping = new ActionMappingEx();
        
        // path : "abc"
        String path = "abc";
        mapping.setParameter(path);
        
        ClearSessionAction_ActionFormStub01 form =
            new ClearSessionAction_ActionFormStub01();
        
        ClearSessionAction_HttpServletRequestStub01 req =
            new ClearSessionAction_HttpServletRequestStub01();
        
        // session設定
        ClearSessionAction_HttpSessionStub01 session =
            (ClearSessionAction_HttpSessionStub01) req.getSession(false);
        
        // session属性設定
        Map<String, String> attrs = new HashMap<String, String>();
        attrs.put("abc", "session01");
        UTUtil.setPrivateField(session, "attrs", attrs);
        
        
        // sendErrorの呼び出し確認用
        ClearSessionAction_HttpServletResponseStub01 res =
            new ClearSessionAction_HttpServletResponseStub01();
        
        ActionForward forward = null;

        // テスト実施
        forward = action.doExecute(mapping, form, req, res);
        
        // 判定
        assertEquals("abc", forward.getPath());
        
        session = (ClearSessionAction_HttpSessionStub01) req.getSession(false);
        assertNull(session.getAttribute("abc"));
    }

    /**
     * testDoExecute04()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) mapping:not null<br>
     *         (引数) form:not null<br>
     *         (引数) req:not null<br>
     *         (引数) res:not null<br>
     *         (状態) clearSessionKeys:要素"abc01"、"abc02"、"abc03"が記述順に
     *         入っているArrayList<br>
     *         (状態) session.getAttribute():session.getAttribute("abc01")が
     *         "session01"<br>
     *                session.getAttribute("abc02")が"session02"<br>
     *                session.getAttribute("abc03")が"session03"<br>
     *                session.getAttribute("abc04")が"session04"<br>
     *         (状態) path:"abc"<br>
     *         
     * <br>
     * 期待値：(戻り値) actionForward:ActionForward.getPath()："abc"<br>
     *         (状態変化) session.getAttribute():session.getAttribute("abc01")が
     *         null<br>
     *                    session.getAttribute("abc02")がnull<br>
     *                    session.getAttribute("abc03")がnull<br>
     *                    session.getAttribute("abc04")が"session04"<br>
     *         
     * <br>
     * clearSessionKeysが複数のArrayListの場合、その要素の文字列に該当する
     * sessionの属性が全部削除されていることと、画面遷移を行うことを確認
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testDoExecute04() throws Exception {
        // 前処理
        ClearSessionAction action = new ClearSessionAction();
        
        // clearSessionKeys
        List<String> list = new ArrayList<String>();
        list.add("abc01");
        list.add("abc02");
        list.add("abc03");
        
        UTUtil.setPrivateField(action, "clearSessionKeys", list);
        
        // パラメータの設定
        ActionMappingEx mapping = new ActionMappingEx();
        
        // path : "abc"
        String path = "abc";
        mapping.setParameter(path);
        
        ClearSessionAction_ActionFormStub01 form =
            new ClearSessionAction_ActionFormStub01();
        
        ClearSessionAction_HttpServletRequestStub01 req =
            new ClearSessionAction_HttpServletRequestStub01();
        
        // session設定
        ClearSessionAction_HttpSessionStub01 session =
            (ClearSessionAction_HttpSessionStub01) req.getSession(false);
        
        // session属性設定
        Map<String, String> attrs = new HashMap<String, String>();
        attrs.put("abc01", "session01");
        attrs.put("abc02", "session02");
        attrs.put("abc03", "session03");
        attrs.put("abc04", "session04");
        UTUtil.setPrivateField(session, "attrs", attrs);
        
        
        // sendErrorの呼び出し確認用
        ClearSessionAction_HttpServletResponseStub01 res =
            new ClearSessionAction_HttpServletResponseStub01();
        
        ActionForward forward = null;

        // テスト実施
        forward = action.doExecute(mapping, form, req, res);
        
        // 判定
        assertEquals("abc", forward.getPath());
        
        session = (ClearSessionAction_HttpSessionStub01) req.getSession(false);
        assertNull(session.getAttribute("abc01"));
        assertNull(session.getAttribute("abc02"));
        assertNull(session.getAttribute("abc03"));
        assertEquals("session04", session.getAttribute("abc04"));
    }
}
