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

import javax.servlet.ServletRequest;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;

import jp.terasoluna.utlib.TagUTUtil;
import junit.framework.TestCase;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

/**
 * IfNotErrorsTag ブラックボックステスト。<br>
 * 前提条件<br>
 * なし<br>
 * <br>
 */
public class IfNotErrorsTagTest extends TestCase {

    // テスト対象
    IfNotErrorsTag tag = null;

    /**
     * Constructor for IfNotErrorsTagTest.
     * @param arg0
     */
    public IfNotErrorsTagTest(String arg0) {
        super(arg0);
    }

    /*
     * @see TestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        tag = (IfNotErrorsTag) TagUTUtil.create(IfNotErrorsTag.class);
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
     * errors=Not Null<br>
     * <br>
     * 期待値<br>
     * 戻り値:int=SKIP_BODY<br>
     * <br>
     * リクエストから取得したエラーがNullではない場合のテストケース<br>
     */
    public void testDoStartTag01() throws Exception {

        // テスト設定
        PageContext pc = TagUTUtil.getPageContext(tag);
        ServletRequest rq = pc.getRequest();
        ActionMessages errors = new ActionMessages();
        errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(""));
        rq.setAttribute(Globals.ERROR_KEY, errors);

        // テスト実行
        int result = tag.doStartTag();

        // テスト結果確認
        assertEquals(Tag.SKIP_BODY, result);

    } /* testDoStartTag01 End */

    /**
     * testDoStartTag02。<br>
     * <br>
     * (正常系)<br>
     * 観点：F<br>
     * <br>
     * 入力値<br>
     * errors=Null<br>
     * <br>
     * 期待値<br>
     * 戻り値:int=EVAL_BODY_INCLUDE<br>
     * <br>
     * リクエストから取得したエラーがNullの場合のテストケース<br>
     */
    public void testDoStartTag02() throws Exception {

        // テスト実行
        int result = tag.doStartTag();

        // テスト結果確認
        assertEquals(Tag.EVAL_BODY_INCLUDE, result);

    } /* testDoStartTag02 End */

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
     */
    public void testDoEndTag01() throws Exception {
        // テスト実行
        int result = tag.doEndTag();

        // テスト結果確認
        assertEquals(Tag.EVAL_PAGE, result);
    }

} /* IfNotErrorsTagTest Class End */
