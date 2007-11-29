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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;

import jp.terasoluna.utlib.TagUTUtil;
import jp.terasoluna.utlib.UTUtil;
import jp.terasoluna.utlib.exception.Exception_JspWriterImpl;
import junit.framework.TestCase;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

/**
 * ChangeStyleClassTag ブラックボックステスト。<br>
 * 前提条件<br>
 * なし<br>
 * <br>
 */
public class ChangeStyleClassTagTest extends TestCase {

    //テスト対象
    ChangeStyleClassTag tag = null;

    /**
     * Constructor for ClassTagTest.
     * @param arg0
     */
    public ChangeStyleClassTagTest(String arg0) {
        super(arg0);
    }

    /**
     * @see TestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        tag = (ChangeStyleClassTag) TagUTUtil.create(ChangeStyleClassTag.class);
    }

    /**
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
     * req=null<br>
     * name=null<br>
     * defaultValue=null<br>
     * errorValue=null<br>
     * <br>
     * 期待値<br>
     * 戻り値:int=SKIP_BODY<br>
     * 出力内容=-<br>
     * <br>
     * リクエストがNullの場合のテストケース<br>
     * 
     * @throws Exception 例外<br>
     */
    public void testDoStartTag01() throws Exception {

        // テスト設定
        PageContext pc = TagUTUtil.getPageContext(tag);
        UTUtil.setPrivateField(pc, "request", null);
        tag.setPageContext(pc);

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
     * req=Not Null<br>
     * name="name"<br>
     * defaultValue="defaultValue"<br>
     * errorValue="errorValue"<br>
     * errors=Not Null(エラーなし)
     * <br>
     * 期待値<br>
     * 戻り値:int=EVAL_BODY_INCLUDE<br>
     * 出力内容="defaultValue"<br>
     * <br>
     * name,defaultValue,errorValue,errorsのすべてNotNull
     * の場合のテストケース<br>
     * 
     * @throws Exception 例外<br>
     */
    public void testDoStartTag02() throws Exception {

        // テスト設定
        PageContext pc = TagUTUtil.getPageContext(tag);
        ServletRequest rq = pc.getRequest();

        // テスト対象クラス変数へセットする値
        String obj1 = "name";
        String obj2 = "defaultValue";
        String obj3 = "errorValue";

        // テスト対象のクラス変数へセット
        UTUtil.setPrivateField(tag, "name", obj1);
        UTUtil.setPrivateField(tag, "defaultValue", obj2);
        UTUtil.setPrivateField(tag, "errorValue", obj3);

        // エラーを生成し、リクエストにセットする。
        ActionMessages errors = new ActionMessages();
        rq.setAttribute(Globals.ERROR_KEY, errors);

        // テスト実行
        int result = tag.doStartTag();

        // テスト結果確認
        assertEquals(Tag.EVAL_BODY_INCLUDE, result);
        String reader = TagUTUtil.getOutputString(tag);
        assertEquals(obj2, reader);

    } /* testDoStartTag02 End */

    /**
     * testDoStartTag03。<br>
     * <br>
     * (異常系)<br>
     * 観点：G<br>
     * <br>
     * 入力値<br>
     * req=Not Null<br>
     * name="name"<br>
     * defaultValue="defaultValue"<br>
     * errorValue="errorValue"<br>
     * errors=Not Null(エラーなし)
     * <br>
     * 期待値<br>
     * 戻り値:int=JspException<br>
     * 出力内容=-<br>
     * <br>
     * 出力時に、IOExceptionが発生した場合のテストケース<br>
     * 
     * @throws Exception 例外<br>
     */
    public void testDoStartTag03() throws Exception {

        // テスト設定
        PageContext pc = TagUTUtil.getPageContext(tag);
        ServletRequest rq = pc.getRequest();

        // テスト対象クラス変数へセットする値
        String obj1 = "name";
        String obj2 = "defaultValue";
        String obj3 = "errorValue";

        // テスト対象のクラス変数へセット
        UTUtil.setPrivateField(tag, "name", obj1);
        UTUtil.setPrivateField(tag, "defaultValue", obj2);
        UTUtil.setPrivateField(tag, "errorValue", obj3);

        // エラーを生成し、リクエストにセットする。
        ActionMessages errors = new ActionMessages();
        rq.setAttribute(Globals.ERROR_KEY, errors);

        // テスト用JspWriterの生成
        Exception_JspWriterImpl out = new Exception_JspWriterImpl();
        out.setTrue();
        out.setTiming(1);

        // 生成・設定したテスト用JspWriterをPageContextにセット
        UTUtil.setPrivateField(pc, "jspWriter", out);

        // テスト実行
        try {
            tag.doStartTag();
            fail();
        } catch (JspTagException ex) {

            // テスト結果確認
            assertEquals("javax.servlet.jsp.JspTagException:"
                    + " java.io.IOException", ex.toString());
            return;
        }
    } /* testDoStartTag03 End */

    /**
     * testChooseClass01。<br>
     * <br>
     * (正常系)<br>
     * 観点：F<br>
     * <br>
     * 入力値<br>
     * req=Not Null<br>
     * fieldname="fieldname"<br>
     * ifNormal="ifNormal"<br>
     * ifError="ifError"<br>
     * requestErrors=Null<br>
     * sessionErrors=Null<br>
     * <br>
     * 期待値<br>
     * 戻り値:String="ifNormal"<br>
     * <br>
     * リクエストとセッションから取得したエラーメッセージが
     * Nullの場合のテストケース<br>
     * 
     * @throws Exception 例外<br>
     */
    public void testChooseClass01() throws Exception {

        // テスト設定
        PageContext pc = TagUTUtil.getPageContext(tag);
        ServletRequest rq = pc.getRequest();

        String obj1 = "fieldname";
        String obj2 = "ifNormal";
        String obj3 = "ifError";

        Class[] obj4 = { HttpServletRequest.class, String.class, String.class,
                String.class };
        Object[] obj5 = { rq, obj1, obj2, obj3 };

        // テスト実行
        String result = (String) UTUtil.invokePrivate(tag, "chooseClass", obj4,
                obj5);

        // テスト結果確認
        assertEquals(obj2, result);

    } /* testChooseClass01 End */

    /**
     * testChooseClass02。<br>
     * <br>
     * (正常系)<br>
     * 観点：F<br>
     * <br>
     * 入力値<br>
     * req=Not Null<br>
     * fieldname="fieldname"<br>
     * ifNormal="ifNormal"<br>
     * ifError="ifError"<br>
     * requestErrors=Not Null(エラー有り)<br>
     * sessionErrors=Null<br>
     * 期待値<br>
     * 戻り値:String="ifError"<br>
     * <br>
     * リクエストからActionMessagesを取得し、 かつエラーメッセージが一件以上、また、
     * セッションから取得したActionMessagesがnullの場合のテストケース<br>
     * 
     * @throws Exception
     *             例外<br>
     */
    public void testChooseClass02() throws Exception {

        // テスト設定
        PageContext pc = TagUTUtil.getPageContext(tag);
        HttpServletRequest rq = (HttpServletRequest) pc.getRequest();

        String obj1 = "fieldname";
        String obj2 = "ifNormal";
        String obj3 = "ifError";

        // エラーを生成し、リクエストにセットする。
        ActionMessages errors = new ActionMessages();
        errors.add(obj1, new ActionMessage("errorstagtest1"));
        rq.setAttribute(Globals.ERROR_KEY, errors);
        HttpSession session = rq.getSession(true);
        session.setAttribute(Globals.ERROR_KEY, null);

        Class[] obj4 = { HttpServletRequest.class, String.class, String.class,
                String.class };
        Object[] obj5 = { rq, obj1, obj2, obj3 };

        // テスト実行
        String result = (String) UTUtil.invokePrivate(tag, "chooseClass", obj4,
                obj5);

        // テスト結果確認
        assertEquals(obj3, result);

    } /* testChooseClass02 End */

    /**
     * testChooseClass03。<br>
     * <br>
     * (正常系)<br>
     * 観点：F<br>
     * <br>
     * 入力値<br>
     * req=Not Null<br>
     * fieldname="fieldname"<br>
     * ifNormal="ifNormal"<br>
     * ifError="ifError"<br>
     * requestErrors=Null<br>
     * sessionErrors=Not Null(エラー有り)<br>
     * 期待値<br>
     * 戻り値:String="ifError"<br>
     * <br>
     * セッションからActionMessagesを取得し、 かつエラーメッセージが一件以上、また、
     * リクエストから取得したActionMessagesがnullの場合のテストケース<br>
     * 
     * @throws Exception
     *             例外<br>
     */
    public void testChooseClass03() throws Exception {

        // テスト設定
        PageContext pc = TagUTUtil.getPageContext(tag);
        HttpServletRequest rq = (HttpServletRequest) pc.getRequest();

        String obj1 = "fieldname";
        String obj2 = "ifNormal";
        String obj3 = "ifError";

        // エラーを生成し、リクエストにセットする。
        ActionMessages errors = new ActionMessages();
        errors.add(obj1, new ActionMessage("errorstagtest1"));
        rq.setAttribute(Globals.ERROR_KEY, null);
        HttpSession session = rq.getSession(true);
        session.setAttribute(Globals.ERROR_KEY, errors);

        Class[] obj4 = { HttpServletRequest.class, String.class, String.class,
                String.class };
        Object[] obj5 = { rq, obj1, obj2, obj3 };

        // テスト実行
        String result = (String) UTUtil.invokePrivate(tag, "chooseClass", obj4,
                obj5);

        // テスト結果確認
        assertEquals(obj3, result);

    } /* testChooseClass03 End */

    /**
     * testChooseClass04。<br>
     * <br>
     * (正常系)<br>
     * 観点：F<br>
     * <br>
     * 入力値<br>
     * req=Not Null<br>
     * fieldname="fieldname"<br>
     * ifNormal="ifNormal"<br>
     * ifError="ifError"<br>
     * requestErrors=Not Null(エラーなし)<br>
     * sessionErrors=Null<br>
     * <br>
     * 期待値<br>
     * 戻り値:String="ifNormal"<br>
     * <br>
     * リクエストからActionMessagesを取得し、 かつエラーメッセージが一件もない、また、
     * セッションから取得したエラー用ActionMessagesがnullの場合のテストケース<br>
     * 
     * @throws Exception
     *             例外<br>
     */
    public void testchooseClass04() throws Exception {

        // テスト設定
        PageContext pc = TagUTUtil.getPageContext(tag);
        HttpServletRequest rq = (HttpServletRequest) pc.getRequest();

        String obj1 = "fieldname";
        String obj2 = "ifNormal";
        String obj3 = "ifError";

        // エラーを生成し、リクエストにセットする。
        ActionMessages errors = new ActionMessages();
        rq.setAttribute(Globals.ERROR_KEY, errors);
        HttpSession session = rq.getSession(true);
        session.setAttribute(Globals.ERROR_KEY, null);

        Class[] obj4 = { HttpServletRequest.class, String.class, String.class,
                String.class };
        Object[] obj5 = { rq, obj1, obj2, obj3 };

        // テスト実行
        String result = (String) UTUtil.invokePrivate(tag, "chooseClass", obj4,
                obj5);

        // テスト結果確認
        assertNotNull(rq.getAttribute(Globals.ERROR_KEY));
        assertEquals(obj2, result);

    } /* testChooseClass04 End */

    /**
     * testChooseClass05。<br>
     * <br>
     * (正常系)<br>
     * 観点：F<br>
     * <br>
     * 入力値<br>
     * req=Not Null<br>
     * fieldname="fieldname"<br>
     * ifNormal="ifNormal"<br>
     * ifError="ifError"<br>
     * requestErrors=Null<br>
     * sessionErrors=Not Null(エラーなし)<br>
     * <br>
     * 期待値<br>
     * 戻り値:String="ifNormal"<br>
     * <br>
     * セッションからActionMessagesを取得し、 かつエラーメッセージが一件もない、また、
     * リクエストから取得したエラー用ActionMessagesがnullの場合のテストケース<br>
     * 
     * @throws Exception
     *             例外<br>
     */
    public void testChooseClass05() throws Exception {

        // テスト設定
        PageContext pc = TagUTUtil.getPageContext(tag);
        HttpServletRequest rq = (HttpServletRequest) pc.getRequest();

        String obj1 = "fieldname";
        String obj2 = "ifNormal";
        String obj3 = "ifError";

        // エラーを生成し、リクエストにセットする。
        ActionMessages errors = new ActionMessages();
        rq.setAttribute(Globals.ERROR_KEY, null);
        HttpSession session = rq.getSession(true);
        session.setAttribute(Globals.ERROR_KEY, errors);

        Class[] obj4 = { HttpServletRequest.class, String.class, String.class,
                String.class };
        Object[] obj5 = { rq, obj1, obj2, obj3 };

        // テスト実行
        String result = (String) UTUtil.invokePrivate(tag, "chooseClass", obj4,
                obj5);

        // テスト結果確認
        assertEquals(obj2, result);

    } /* testChooseClass05 End */

    /**
     * testRelease01。<br>
     * 
     * (正常系)<br>
     * 観点：C<br>
     * 
     * 入力値 name=*<br>
     * defaultValue=*<br>
     * errorValue=*<br>
     * 
     * 期待値 戻り値:void<br>
     * name=Null<br>
     * defaultValue=Null<br>
     * errorValue=Null<br>
     * 
     * 前提条件として設定した各値が、実行時に各条件値が初期化されることを確認する<br>
     * 
     * @throws Exception
     *             例外<br>
     */
    public void testRelease01() throws Exception {

        // テスト設定
        UTUtil.setPrivateField(tag, "name", "name");
        UTUtil.setPrivateField(tag, "defaultValue", "defaultValue");
        UTUtil.setPrivateField(tag, "errorValue", "errorValue");

        // テスト実行
        tag.release();

        // テスト結果確認
        assertNull(UTUtil.getPrivateField(tag, "name"));
        assertNull(UTUtil.getPrivateField(tag, "defaultValue"));
        assertNull(UTUtil.getPrivateField(tag, "errorValue"));

    } /* testRelease1 End */

    /**
     * testSetName01。<br>
     * 
     * (正常系)<br>
     * 観点：A<br>
     * 
     * 入力値
     * name="name"<br>
     * 
     * 期待値
     * 戻り値:void<br>
     * name="name"<br>
     * 
     * セットした値を確認するテストケース<br>
     * 
     * @throws Exception 例外<br>
     */
    public void testSetName01() throws Exception {
        // テスト実行
        tag.setName("name");

        // テスト結果確認
        assertEquals("name", UTUtil.getPrivateField(tag, "name"));

    } /* testSetName End */

    /**
     * testSetDefault01。<br>
     * 
     * (正常系)<br>
     * 観点：A<br>
     * 
     * 入力値
     * default="default"<br>
     * 
     * 期待値
     * 戻り値:void<br>
     * default="default"<br>
     * 
     * セットした値を確認するテストケース<br>
     * 
     * @throws Exception 例外<br>
     */
    public void testSetDefault01() throws Exception {
        // テスト実行
        tag.setDefault("default");

        // テスト結果確認
        assertEquals("default", UTUtil.getPrivateField(tag, "defaultValue"));

    } /* testSetDefault End */

    /**
     * testSetError01。<br>
     * 
     * (正常系)<br>
     * 観点：A<br>
     * 
     * 入力値
     * error="error"<br>
     * 
     * 期待値
     * 戻り値:void<br>
     * error="error"<br>
     * 
     * セットした値を確認するテストケース<br>
     * 
     * @throws Exception 例外<br>
     */
    public void testSetError01() throws Exception {
        // テスト実行
        tag.setError("error");

        // テスト結果確認
        assertEquals("error", UTUtil.getPrivateField(tag, "errorValue"));

    } /* testSetError01 End */

    /**
     * testDoEndTag01。<br>
     * 
     * (正常系)<br>
     * 観点：A<br>
     *   
     * 入力値:なし<br>
     * 
     * 期待値
     * 戻り値:int=EVAL_PAGE<br>
     * 
     * 常にEVAL_PAGEが返却される。<br>
     * 
     * @throws Exception 例外<br>
     */
    public void testDoEndTag01() throws Exception {

        int result = 0;
        // テスト実行
        result = tag.doEndTag();

        // テスト結果確認
        assertEquals(Tag.EVAL_PAGE, result);
    } /* testDoEndTag01 End */

} /* ClassTagTest Class End */
