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

import org.apache.struts.action.ActionForward;

import jp.terasoluna.fw.exception.SystemException;
import jp.terasoluna.fw.web.struts.action.ActionMappingEx;
import jp.terasoluna.utlib.LogUTUtil;
import jp.terasoluna.utlib.MockHttpServletRequest;
import junit.framework.TestCase;

/**
 * {@link jp.terasoluna.fw.web.struts.actions.LogoffAction}
 * クラスのブラックボックステスト。
 * 
 * <p>
 * <h4>【クラスの概要】</h4>
 * JSPなどへフォワードするアクション
 * <p>
 * 
 * @see jp.terasoluna.fw.web.struts.actions.LogoffAction
 */
public class LogoffActionTest extends TestCase {

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
    public LogoffActionTest(String name) {
        super(name);
    }

    /**
     * testDoExecute01()
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
     *         (状態) session:null<br>
     *         (状態) path:null<br>
     *         
     * <br>
     * 期待値：(戻り値) actionForward:null<br>
     *         (状態変化) res.sendError():呼び出し確認<br>
     *         
     * <br>
     * mappingからパラメータの取得結果がnullであり、
     * HttpServletResponse.sendError()が呼び出された場合、nullを返すことを確認
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testDoExecute01() throws Exception {
        // 前処理
        LogoffAction action = new LogoffAction();
        
        // パラメータの設定
        ActionMappingEx mapping = new ActionMappingEx();
        
        // path : null
        String path = null;
        mapping.setParameter(path);
        
        LogoffAction_ActionFormStub01 form =
            new LogoffAction_ActionFormStub01();
        
        MockHttpServletRequest req = new MockHttpServletRequest();
        
        // sendErrorの呼び出し確認用
        LogoffAction_HttpServletResponseStub01 res =
            new LogoffAction_HttpServletResponseStub01();
        
        ActionForward forward = null;

        // テスト実施
        forward = action.doExecute(mapping, form, req, res);

        // 判定
        assertNull(forward);
        assertTrue(res.isSendError);
    }

    /**
     * testDoExecute02()
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
     *         (状態) session:null<br>
     *         (状態) path:null<br>
     *         (状態) res.sendError():IOException発生<br>
     *         
     * <br>
     * 期待値：(状態変化) 例外:SystemException：<br>
     *                    メッセージキー："error.forward.errorpage"<br>
     *                    ラップした例外：IOException<br>
     *         (状態変化) ログ:＜ログ＞<br>
     *                    エラーログ："Error page(404) forwarding failed."<br>
     *         
     * <br>
     * mappingからパラメータの取得結果がnullであり、
     * HttpServletResponse.sendError()呼出で例外が発生した場合、
     * SystemExceptionが発生することを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testDoExecute02() throws Exception {
        // 前処理
        LogoffAction action = new LogoffAction();
        
        // パラメータの設定
        ActionMappingEx mapping = new ActionMappingEx();
        
        // path : null
        String path = null;
        mapping.setParameter(path);
        
        LogoffAction_ActionFormStub01 form =
            new LogoffAction_ActionFormStub01();
        
        MockHttpServletRequest req = new MockHttpServletRequest();
        
        // sendErrorの呼び出し確認用
        LogoffAction_HttpServletResponseStub02 res =
            new LogoffAction_HttpServletResponseStub02();
        
        @SuppressWarnings("unused") ActionForward forward = null;

        // テスト実施
        try {
            forward = action.doExecute(mapping, form, req, res);
            fail("例外が発生しませんでした");
        } catch (SystemException e) {
            // 判定
            // IOExceptionをラップしたか
            assertEquals(e.getCause().getClass().getName(),
                    IOException.class.getName());
            // エラーログ確認
            assertTrue(LogUTUtil.checkError(
                    "Error page(404) forwarding failed."));
            assertEquals("error.forward.errorpage", e.getErrorCode());
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
     * 入力値：(引数) mapping:not null<br>
     *         (引数) form:not null<br>
     *         (引数) req:not null<br>
     *         (引数) res:not null<br>
     *         (状態) session:null<br>
     *         (状態) path:"abc"<br>
     *         
     * <br>
     * 期待値：(戻り値) actionForward:ActionForward.getPath()："abc"<br>
     *         
     * <br>
     * mappingからパラメータの取得結果がnot nullの場合、
     * ActionForwardのpathが指定したpathと一致するか確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testDoExecute03() throws Exception {
        // 前処理
        LogoffAction action = new LogoffAction();
        
        // パラメータの設定
        ActionMappingEx mapping = new ActionMappingEx();
        
        // path : "abc"
        String path = "abc";
        mapping.setParameter(path);
        
        LogoffAction_ActionFormStub01 form =
            new LogoffAction_ActionFormStub01();
        
        MockHttpServletRequest req = new MockHttpServletRequest();
        
        // sendErrorの呼び出し確認用
        LogoffAction_HttpServletResponseStub01 res =
            new LogoffAction_HttpServletResponseStub01();
        
        ActionForward forward = null;

        // テスト実施
        forward = action.doExecute(mapping, form, req, res);

        // 判定
        assertEquals("abc", forward.getPath());
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
     *         (状態) session:not null<br>
     *         (状態) path:"abc"<br>
     *         
     * <br>
     * 期待値：(戻り値) actionForward:ActionForward.getPath()："abc"<br>
     *         (状態変化) session.invalidate():呼び出し確認<br>
     *         
     * <br>
     * reqより取得するsessionがnot nullの場合、
     * session.invalidate()が呼び出されたか確認する。
     * ActionForwardのpathが指定したpathと一致するか確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testDoExecute04() throws Exception {
        // 前処理
        LogoffAction action = new LogoffAction();
        
        // パラメータの設定
        ActionMappingEx mapping = new ActionMappingEx();
        
        // path : "abc"
        String path = "abc";
        mapping.setParameter(path);
        
        LogoffAction_ActionFormStub01 form =
            new LogoffAction_ActionFormStub01();
        
        // LogoffAction_HttpSessionStub01を取得するスタブ
        LogoffAction_HttpServletRequestStub01 req =
            new LogoffAction_HttpServletRequestStub01();
        
        // sendErrorの呼び出し確認用
        LogoffAction_HttpServletResponseStub01 res =
            new LogoffAction_HttpServletResponseStub01();
        
        ActionForward forward = null;

        // テスト実施
        forward = action.doExecute(mapping, form, req, res);

        // 判定
        LogoffAction_HttpSessionStub01 session =
            (LogoffAction_HttpSessionStub01) req.getSession(false); 
        assertEquals("abc", forward.getPath());
        assertTrue(session.isInvalidate);
    }

}
