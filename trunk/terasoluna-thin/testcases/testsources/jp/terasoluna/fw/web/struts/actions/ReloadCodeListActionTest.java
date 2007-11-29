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
import jp.terasoluna.fw.web.codelist.ReloadableCodeListLoader;
import jp.terasoluna.fw.web.struts.action.ActionMappingEx;
import jp.terasoluna.utlib.LogUTUtil;
import jp.terasoluna.utlib.MockHttpServletRequest;
import jp.terasoluna.utlib.MockHttpServletResponse;
import jp.terasoluna.utlib.UTUtil;
import junit.framework.TestCase;

/**
 * {@link jp.terasoluna.fw.web.struts.actions.ReloadCodeListAction}
 * クラスのブラックボックステスト。
 *
 * <p>
 * <h4>【クラスの概要】</h4>
 * サーブレットコンテキスト内にキャッシュされたコードリストをすべてリロードする。
 * <p>
 *
 * @see jp.terasoluna.fw.web.struts.actions.ReloadCodeListAction
 */
@SuppressWarnings("unused")
public class ReloadCodeListActionTest extends TestCase {

    /**
     * このテストケースを実行する為の
     * GUI アプリケーションを起動する。
     *
     * @param args java コマンドに設定されたパラメータ
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(ReloadCodeListActionTest.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        LogUTUtil.flush();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * testGetCodeListLoader01()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(状態) codeListLoader:ReloadCodeListActionオブジェクト<br>
     *
     * <br>
     * 期待値：(戻り値) ReloadableCodeListLoader:前提条件と同一の
     * ReloadCodeListActionオブジェクト<br>
     *
     * <br>
     * ReloadCodeListActionに格納されているcodeListLoaderを正常に取得すること。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetCodeListLoader01() throws Exception {
        // 前処理
        // 引数設定
        ReloadableCodeListLoader loader = new ReloadableCodeListLoaderImpl01();

        ReloadCodeListAction action = new ReloadCodeListAction();
        UTUtil.setPrivateField(action, "codeListLoader", loader);

        // テスト実施
        // 判定
        assertSame(loader, action.getCodeListLoader());
    }

    /**
     * testSetCodeListLoader01()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) codeListLoader:ReloadableCodeListLoaderオブジェクト<br>
     *         (状態) codeListLoader:null<br>
     *
     * <br>
     * 期待値：(状態変化) codeListLoader:引数と同一の
     * ReloadableCodeListLoaderオブジェクト<br>
     *
     * <br>
     * 引数に指定した値がcodeListLoaderに正常に格納されることを確認
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetCodeListLoader01() throws Exception {
        // 前処理
        // 引数設定
        ReloadableCodeListLoader loader = new ReloadableCodeListLoaderImpl01();

        ReloadCodeListAction action = new ReloadCodeListAction();

        // テスト実施
        action.setCodeListLoader(loader);

        // 判定
        assertSame(loader, UTUtil.getPrivateField(action, "codeListLoader"));
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
     *         (状態) codeListLoader:null<br>
     *         (状態) path:null<br>
     *
     * <br>
     * 期待値：(戻り値) actionForward:null<br>
     *         (状態変化) res.sendError():呼出<br>
     *
     * <br>
     * codeListLoaderがnullで<br>
     * mappingからパラメータの取得結果がnullの場合、
     * HttpServletResponse.sendError()呼出されたらnullを返すことを確認
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testDoExecute01() throws Exception {
        // 前処理
        ReloadableCodeListLoader loader = null;
        ReloadCodeListAction action = new ReloadCodeListAction();
        UTUtil.setPrivateField(action, "codeListLoader", loader);

        // パラメータの設定
        ActionMappingEx mapping = new ActionMappingEx();

        // path : null
        String path = null;
        mapping.setParameter(path);

        ReloadCodeList_ActionFormStub01 form =
            new ReloadCodeList_ActionFormStub01();

        MockHttpServletRequest req = new MockHttpServletRequest();

        // sendErrorの呼び出し確認用
        ReloadCodeListAction_HttpServletResponseStub01 res =
            new ReloadCodeListAction_HttpServletResponseStub01();

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
     *         (状態) codeListLoader:null<br>
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
     * codeListLoaderがnullで<br>
     * mappingからパラメータの取得結果がnullの場合
     * HttpServletResponse.sendError()呼出で例外が発生した場合、
     * 指定したパスにセッションディレクトリが生成されたか確認する。
     * またSystemExceptionが発生することを確認
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testDoExecute02() throws Exception {
        // 前処理
        ReloadableCodeListLoader loader = null;
        ReloadCodeListAction action = new ReloadCodeListAction();
        UTUtil.setPrivateField(action, "codeListLoader", loader);

        // パラメータの設定
        ActionMappingEx mapping = new ActionMappingEx();

        // path : null
        String path = null;
        mapping.setParameter(path);

        ReloadCodeList_ActionFormStub01 form =
            new ReloadCodeList_ActionFormStub01();

        MockHttpServletRequest req = new MockHttpServletRequest();

        // sendErrorの呼び出し確認用
        ReloadCodeListAction_HttpServletResponseStub02 res =
            new ReloadCodeListAction_HttpServletResponseStub02();

        ActionForward forward = null;

        // テスト実施
        try {
            forward = action.doExecute(mapping, form, req, res);
            fail();
        } catch (SystemException e) {
            // 判定
            // IOExceptionをラップしたか
            assertTrue(e.getCause() instanceof IOException);
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
     *         (状態) codeListLoader:ReloadableCodeListLoaderインスタンス<br>
     *         (状態) path:"abc"<br>
     *
     * <br>
     * 期待値：(戻り値) actionForward:ActionForward.getPath()："abc"<br>
     *         (状態変化) codeListLoader.reload():呼び出し確認<br>
     *
     * <br>
     * codeListLoaderがReloadableCodeListLoaderのインスタンスでmappingから
     * パラメータの取得結果がnot nullの場合、codeListLoader.reload()が
     * 呼び出されたことと、画面遷移処理がされることを確認
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testDoExecute03() throws Exception {
        // 前処理
        // 引数設定
        ReloadableCodeListLoaderImpl01 loader =
            new ReloadableCodeListLoaderImpl01();

        ReloadCodeListAction action = new ReloadCodeListAction();
        UTUtil.setPrivateField(action, "codeListLoader", loader);

        // パラメータの設定
        ActionMappingEx mapping = new ActionMappingEx();

        // path : null
        String path = "abc";
        mapping.setParameter(path);

        ReloadCodeList_ActionFormStub01 form =
            new ReloadCodeList_ActionFormStub01();

        MockHttpServletRequest req = new MockHttpServletRequest();

        // sendErrorの呼び出し確認用
        MockHttpServletResponse res = new MockHttpServletResponse();

        ActionForward forward = null;

        // テスト実施
        forward = action.doExecute(mapping, form, req, res);

        // 判定
        assertEquals("abc", forward.getPath());
        ReloadableCodeListLoaderImpl01 result = (ReloadableCodeListLoaderImpl01)
        UTUtil.getPrivateField(action, "codeListLoader");
        assertTrue(result.isReload);
    }
}
