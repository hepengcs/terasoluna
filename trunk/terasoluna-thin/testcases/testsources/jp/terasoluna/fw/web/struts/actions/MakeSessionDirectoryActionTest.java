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

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import jp.terasoluna.fw.exception.SystemException;
import jp.terasoluna.fw.util.FileUtil;
import jp.terasoluna.fw.util.PropertyUtil;
import jp.terasoluna.fw.web.struts.action.ActionMappingEx;
import jp.terasoluna.fw.web.struts.form.DynaValidatorActionFormEx;
import jp.terasoluna.utlib.LogUTUtil;
import jp.terasoluna.utlib.MockHttpServletRequest;
import jp.terasoluna.utlib.MockHttpServletResponse;
import jp.terasoluna.utlib.PropertyTestCase;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;

/**
 * {@link jp.terasoluna.fw.web.struts.actions.MakeSessionDirectoryAction}
 * クラスのブラックボックステスト。
 *
 * <p>
 * <h4>【クラスの概要】</h4>
 * セッションディレクトリを作成するアクション。
 * <p>
 *
 * @see jp.terasoluna.fw.web.struts.actions.MakeSessionDirectoryAction
 */
public class MakeSessionDirectoryActionTest extends PropertyTestCase {

    /**
     * 初期化処理を行う。
     *
     * @throws Exception このメソッドで発生した例外
     * @see jp.terasoluna.utlib.spring.PropertyTestCase#setUpData()
     */
    @Override
    protected void setUpData() throws Exception {
        LogUTUtil.flush();
        Thread.sleep(100);

        addProperty("session.dir.base", "c:/sessions");

        // ファイル削除
        FileUtil.removeSessionDirectory("dummyID");
    }

    /**
     * 終了処理を行う。
     *
     * @throws Exception このメソッドで発生した例外
     * @see jp.terasoluna.utlib.spring.PropertyTestCase#cleanUpData()
     */
    @Override
    protected void cleanUpData() throws Exception {
        // ファイル削除
        FileUtil.removeSessionDirectory("dummyID");
    }

    /**
     * コンストラクタ。
     *
     * @param name このテストケースの名前。
     */
    public MakeSessionDirectoryActionTest(String name) {
        super(name);
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
     *         (状態) session:null<br>
     *
     * <br>
     * 期待値：(状態変化) 例外:SystemException：<br>
     *                    メッセージキー："error.session.not.found"<br>
     *                    ラップした例外：null<br>
     *         (状態変化) ＜ログ＞<br>
     *                    エラーログ："HttpSession is not available."<br>
     *
     * <br>
     * HttpSessionがnullだった場合SystemExceptionが発生することを確認
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testDoExecute01() throws Exception {
        // 前処理
        MakeSessionDirectoryAction action = new MakeSessionDirectoryAction();

        // パラメータの設定
        ActionMappingEx mapping = new ActionMappingEx();

        ActionForm form = new DynaValidatorActionFormEx();

        // session:null
        MockHttpServletRequest req = new MockHttpServletRequest();
        req.setSession(null);

        // sendErrorの呼び出し確認用
        MockHttpServletResponse res = new MockHttpServletResponse();

        // テスト実施
        try {
            action.doExecute(mapping, form, req, res);
            fail("例外が発生しませんでした");
        } catch (SystemException e) {
            // 判定
            // エラーログ確認
            assertTrue(LogUTUtil.checkError("HttpSession is not available."));
            assertEquals("error.session.not.found", e.getErrorCode());
        }
    }

    /**
     * testDoExecute02()
     * <br><br>
     *
     * (異常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) mapping:not null<br>
     *         (引数) form:not null<br>
     *         (引数) req:not null<br>
     *         (引数) res:not null<br>
     *         (状態) session:not null<br>
     *         (状態) sessionId:"dummyID"<br>
     *         (状態) path:null<br>
     *
     * <br>
     * 期待値：(戻り値) actionForward:null<br>
     *         (状態変化) directory:
     *         "c:/sessions/" + FileUtil.getSessionDirectoryName("dummyID")
     *         ディレクトリが存在することを確認<br>
     *         (状態変化) res.sendError():呼び出し<br>
     *
     * <br>
     * mappingからパラメータの取得結果がnullであり、
     * HttpServletResponse.sendError()が呼び出された場合、nullを返すことを確認
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testDoExecute02() throws Exception {
        // 前処理
        MakeSessionDirectoryAction action = new MakeSessionDirectoryAction();

        // パラメータの設定
        ActionMappingEx mapping = new ActionMappingEx();
        // path : null
        String path = null;
        mapping.setParameter(path);

        ActionForm form = new DynaValidatorActionFormEx();

        // sessionId:"dummyID"
        MockHttpServletRequest req = new MockHttpServletRequest();

        // sendErrorの呼び出し確認用
        MockHttpServletResponse res = new MockHttpServletResponse();

        ActionForward forward = null;

        // テスト実施
        forward = action.doExecute(mapping, form, req, res);

        // 判定
        assertNull(forward);
        File file = new File(PropertyUtil.getProperty("session.dir.base") + "/"
                + FileUtil.getSessionDirectoryName(req.getSession().getId()));
        assertTrue(file.exists());
        assertEquals(HttpServletResponse.SC_NOT_FOUND, res.getStatus());
    }

    /**
     * testDoExecute03()
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
     *         (状態) session:not null<br>
     *         (状態) sessionId:"dummyID"<br>
     *         (状態) path:null<br>
     *         (状態) プロパティ:session.dir.base=c:/sessions<br>
     *         (状態) res.sendError():IOException発生<br>
     *
     * <br>
     * 期待値：(状態変化) directory:
     *         "c:/sessions/" + FileUtil.getSessionDirectoryName("dummyID")
     *         ディレクトリが存在することを確認<br>
     *         (状態変化) 例外:SystemException：<br>
     *                    メッセージキー："error.forward.errorpage"<br>
     *                    ラップした例外：IOException<br>
     *         (状態変化) ＜ログ＞<br>
     *                    エラーログ："Error page(404) forwarding failed."<br>
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
    public void testDoExecute03() throws Exception {
        // 前処理
        MakeSessionDirectoryAction action = new MakeSessionDirectoryAction();

        // パラメータの設定
        ActionMappingEx mapping = new ActionMappingEx();
        // path : null
        String path = null;
        mapping.setParameter(path);

        ActionForm form = new DynaValidatorActionFormEx();

        // sessionId:"dummyID"
        MockHttpServletRequest req = new MockHttpServletRequest();

        // sendError:IOException
        MakeSessionDirectoryAction_HttpServletResponseStub01 res =
            new MakeSessionDirectoryAction_HttpServletResponseStub01();

        // テスト実施
        try {
            action.doExecute(mapping, form, req, res);
            fail("例外が発生しませんでした");
        } catch (SystemException e) {
            // 判定
            // エラーログ確認
            assertTrue(LogUTUtil.checkError("Error page(404) forwarding failed."));
            // IOExceptionをラップしたか
            assertEquals(IOException.class.getName(),
                    e.getCause().getClass().getName());
            File file = FileUtil.getSessionDirectory(req.getSession().getId());
            assertTrue(file.exists());
            assertEquals("error.forward.errorpage", e.getErrorCode());
        }
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
     *         (状態) sessionId:"dummyID"<br>
     *         (状態) path:"abc"<br>
     *         (状態) プロパティ:session.dir.base=c:/sessions<br>
     *
     * <br>
     * 期待値：(戻り値) actionForward:ActionForward.getPath()："abc"<br>
     *         (状態変化) directory:
     *         "c:/sessions/" + FileUtil.getSessionDirectoryName("dummyID")
     *         ディレクトリが存在することを確認<br>
     *
     * <br>
     * mappingからパラメータの取得結果がnot nullの場合、
     * 指定したパスにセッションディレクトリが生成されたか確認する。
     * またActionForwardのpathが指定したpathと一致するか確認
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testDoExecute04() throws Exception {
        // 前処理
        MakeSessionDirectoryAction action = new MakeSessionDirectoryAction();

        // パラメータの設定
        ActionMappingEx mapping = new ActionMappingEx();
        // path : "abc"
        String path = "abc";
        mapping.setParameter(path);

        ActionForm form = new DynaValidatorActionFormEx();

        // sessionId:"dummyID"
        MockHttpServletRequest req = new MockHttpServletRequest();

        HttpServletResponse res = new MockHttpServletResponse();

        ActionForward forward = null;

        // テスト実施
        forward = action.doExecute(mapping, form, req, res);

        // 判定
        File file = new File(PropertyUtil.getProperty("session.dir.base") +"/"
                + FileUtil.getSessionDirectoryName(req.getSession().getId()));
        assertTrue(file.exists());
        assertEquals("abc", forward.getPath());
    }
}
