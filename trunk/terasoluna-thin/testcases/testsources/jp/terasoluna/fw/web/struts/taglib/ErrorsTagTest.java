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

package jp.terasoluna.fw.web.struts.taglib;

import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;

import jp.terasoluna.utlib.TagUTUtil;
import junit.framework.TestCase;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.util.MessageResources;
import org.apache.struts.util.PropertyMessageResources;
import org.apache.struts.util.PropertyMessageResourcesFactory;

import com.mockrunner.mock.web.MockHttpServletRequest;
import com.mockrunner.mock.web.MockHttpSession;

/**
 * ErrorsTag ブラックボックステスト。<br>
 * 前提条件<br>
 * なし<br>
 * <br>
 */
public class ErrorsTagTest extends TestCase {

    /**
     * テスト対象
     */
    private ErrorsTag tag = null;

    /**
     * テストケース開始前処理。
     * テスト対象タグ、フォームインスタンスの生成を行なう。
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        // テスト対象タグの生成
        tag = (ErrorsTag) TagUTUtil.create(ErrorsTag.class);
    }

    /*
     * @see TestCase#tearDown()
     */
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * testDoStartTag01。<br>
     * <br>
     * (正常系)<br>
     * 観点：C<br>
     * <br>
     * 入力値<br>
     * リクエストパラメータ=errorKeyが存在しない。
     *                      messageKeyが存在する。
     * <br>
     * 期待値:
     * 戻り値:EVAL_BODY_INCLUDE<br>
     * <br>
     * リクエストパラメータにerrorKeyが存在しない場合、
     * EVAL_BODY_INCLUDEが返却され、終了すること。
     */
    public void testDoStartTag01() throws Exception {
        // テスト設定
        PageContext pc = TagUTUtil.getPageContext(tag);
        MockHttpServletRequest req = (MockHttpServletRequest) pc.getRequest();
        // リクエストパラメータ設定（メッセージのみ）
        req.setupAddParameter(MessagesPopupTag.POPUP_MESSAGE_KEY, "messageKey");

        // テスト実行・結果確認
        assertEquals(Tag.EVAL_BODY_INCLUDE, tag.doStartTag());
    }

    /**
     * testDoStartTag02。<br>
     * <br>
     * (正常系)<br>
     * 観点：C<br>
     * <br>
     * 入力値<br>
     * リクエストパラメータ=errorKeyが存在する。
     *                      messageKeyが存在する。
     * <br>
     * 期待値:
     * 戻り値:EVAL_BODY_INCLUDE<br>
     * <br>
     * リクエストパラメータにerrorKeyが存在するとき、
     * セッション上のエラー情報がリクエスト属性にコピーされ、
     * セッションからは削除されることを確認する。
     */
    public void testDoStartTag02() throws Exception {
        // テスト設定
        PageContext pc = TagUTUtil.getPageContext(tag);
        MockHttpServletRequest req = (MockHttpServletRequest) pc.getRequest();
        // メッセージリソース定義（テスト通過のため必要）
        MessageResources resources = new PropertyMessageResources(
        new PropertyMessageResourcesFactory(), "");
        pc.setAttribute(
            Globals.MESSAGES_KEY,
            resources,
            PageContext.REQUEST_SCOPE);

        // リクエストパラメータ設定
        req.setupAddParameter(MessagesPopupTag.POPUP_ERROR_KEY, "errorKey");
        req.setupAddParameter(MessagesPopupTag.POPUP_MESSAGE_KEY, "messageKey");
        MockHttpSession session = (MockHttpSession) pc.getSession();

        ActionMessages errors = new ActionMessages();
        ActionMessage error1 = new ActionMessage("errors.error1");
        ActionMessage error2 = new ActionMessage("errors.error2");
        errors.add(Globals.ERROR_KEY, error1);
        errors.add(Globals.ERROR_KEY, error2);
        // エラー情報をセッション属性として設定
        session.setAttribute("errorKey", errors);

        ActionMessages messages = new ActionMessages();
        ActionMessage message1 = new ActionMessage("messages.message1");
        ActionMessage message2 = new ActionMessage("messages.message2");
        messages.add(Globals.MESSAGE_KEY, message1);
        messages.add(Globals.MESSAGE_KEY, message2);
        // メッセージ情報をセッション属性として設定
        session.setAttribute("messageKey", messages);

        // テスト実行
        assertEquals(Tag.EVAL_BODY_INCLUDE, tag.doStartTag());
        // リクエスト属性にGlobals.ERROR_KEYをキーとして
        // エラー情報が設定されていること。
        assertSame(errors, req.getAttribute(Globals.ERROR_KEY));
        // メッセージ情報はリクエストに格納されていないこと。
        assertNull(req.getAttribute(Globals.MESSAGE_KEY));
        // セッションからエラー情報が削除されていること。
        assertNull(session.getAttribute("errorKey"));
        // セッションからメッセージ情報が削除されていないこと。
        assertNotNull(session.getAttribute("messageKey"));
    }
}
