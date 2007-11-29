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

import org.apache.struts.Globals;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;

import jp.terasoluna.utlib.TagUTUtil;

import junit.framework.TestCase;

/**
 * IfErrorsTag ブラックボックステスト。<br>
 * 前提条件<br>
 * なし<br>
 * <br>
 */
public class IfErrorsTagTest extends TestCase {

    //テスト対象
    IfErrorsTag tag = null;

    /**
     * Constructor for IfErrorsTagTest.
     * @param arg0
     */
    public IfErrorsTagTest(String arg0) {
        super(arg0);
    }

    /*
     * @see TestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        tag = (IfErrorsTag) TagUTUtil.create(IfErrorsTag.class);
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
     * 観点：F<br>
     * <br>
     * 入力値<br>
     * requestErrors=Not Null<br>
     * sessionErrors=Not Null<br>
     * <br>
     * 期待値<br>
     * 戻り値:int=EVAL_BODY_INCLUDE<br>
     * <br>
     * リクエストから取得したエラーがNullではないかつ
     * セッションから取得したエラーがNullではない場合のテストケース<br>
     * 
     * @throws Exception 例外<br>
     */
    public void testDoStartTag01() throws Exception {

        // テスト設定
        PageContext pc = TagUTUtil.getPageContext(tag);
        HttpServletRequest rq = (HttpServletRequest) pc.getRequest();
        ActionMessages errors = new ActionMessages();
        errors.add(Globals.ERROR_KEY, new ActionMessage(""));
        rq.setAttribute(Globals.ERROR_KEY, errors);

        HttpSession session = rq.getSession(true);
        session.setAttribute(Globals.ERROR_KEY, errors);

        // テスト実行
        int result = tag.doStartTag();

        // テスト結果確認
        assertEquals(Tag.EVAL_BODY_INCLUDE, result);

    } /* testDoStartTag1 End */

    /**
     * testDoStartTag02。<br>
     * <br>
     * (正常系)<br>
     * 観点：F<br>
     * <br>
     * 入力値<br>
     * requestErrors=Null<br>
     * sessionErrors=Not Null<br>
     * <br>
     * 期待値<br>
     * 戻り値:int=EVAL_BODY_INCLUDE<br>
     * <br>
     * リクエストから取得したエラーがNullかつ
     * セッションから取得したエラーがNullではない場合のテストケース<br>
     * 
     * @throws Exception 例外<br>
     */
    public void testDoStartTag02() throws Exception {

        // テスト設定
        PageContext pc = TagUTUtil.getPageContext(tag);
        HttpServletRequest rq = (HttpServletRequest) pc.getRequest();
        ActionMessages errors = new ActionMessages();
        errors.add(Globals.ERROR_KEY, new ActionMessage(""));
        HttpSession session = rq.getSession(true);
        session.setAttribute(Globals.ERROR_KEY, errors);

        // テスト実行
        int result = tag.doStartTag();

        // テスト結果確認
        assertEquals(Tag.EVAL_BODY_INCLUDE, result);

    } /* testDoStartTag2 End */

    /**
     * testDoStartTag03。<br>
     * <br>
     * (正常系)<br>
     * 観点：F<br>
     * <br>
     * 入力値<br>
     * requestErrors=Not Null<br>
     * sessionErrors=Null<br>
     * <br>
     * 期待値<br>
     * 戻り値:int=EVAL_BODY_INCLUDE<br>
     * <br>
     * リクエストから取得したエラーがNullではないかつ
     * セッションから取得したエラーがNull場合のテストケース<br>
     * 
     * @throws Exception 例外<br>
     */
    public void testDoStartTag03() throws Exception {

        // テスト設定
        PageContext pc = TagUTUtil.getPageContext(tag);
        HttpServletRequest rq = (HttpServletRequest) pc.getRequest();
        ActionMessages errors = new ActionMessages();
        errors.add(Globals.ERROR_KEY, new ActionMessage(""));
        rq.setAttribute(Globals.ERROR_KEY, errors);

        // テスト実行
        int result = tag.doStartTag();

        // テスト結果確認
        assertEquals(Tag.EVAL_BODY_INCLUDE, result);

    } /* testDoStartTag3 End */

    /**
     * testDoStartTag04。<br>
     * <br>
     * (正常系)<br>
     * 観点：F<br>
     * <br>
     * 入力値<br>
     * requestErrors=Null<br>
     * sessionErrors=Null<br>
     * <br>
     * 期待値<br>
     * 戻り値:int=SKIP_BODY<br>
     * <br>
     * リクエストから取得したエラーがNullかつ
     * セッションから取得したエラーがNull場合のテストケース<br>
     * 
     * @throws Exception 例外<br>
     */
    public void testDoStartTag04() throws Exception {

        // テスト実行
        int result = tag.doStartTag();

        // テスト結果確認
        assertEquals(Tag.SKIP_BODY, result);

    } /* testDoStartTag4 End */

    /**
     * testDoEndTag01。<br>
     * 
     * (正常系)<br>
     * 観点：A<br>
     * 
     * 入力値
     * なし<br>
     * 
     * 期待値
     * 戻り値:int=EVAL_PAGE<br>
     * 
     * EVAL_PAGEが返却されることを確認する<br>
     * 
     * @throws Exception 例外<br>
     */
    public void testDoEndTag01() throws Exception {
        // テスト実行
        int result = tag.doEndTag();

        // テスト結果確認
        assertEquals(Tag.EVAL_PAGE, result);
    }

} /* IfErrorsTagTest Class End */
