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

import java.util.Enumeration;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;

import jp.terasoluna.utlib.PropertyTestCase;
import jp.terasoluna.utlib.TagUTUtil;
import jp.terasoluna.utlib.UTUtil;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.mockrunner.mock.web.MockHttpServletRequest;

/**
 * MessagesPopupTag ブラックボックステスト。<br>
 * 前提条件<br>
 * なし<br>
 * <br>
 */
public class MessagesPopupTagTest extends PropertyTestCase {

    //テスト対象
    MessagesPopupTag tag = null;

    /**
     * Constructor for MessagesPopupTagTest.
     * @param arg0
     */
    public MessagesPopupTagTest(String arg0) {
        super(arg0);
    }

    /*
     * @see TestCase#setUp()
     */
    @Override
    protected void setUpData() throws Exception {
        Map<String, String> overProps = new TreeMap<String, String>();
        overProps.put("messages.popup.param.paramType", "religion");
        addPropertyAll(overProps);
        tag = (MessagesPopupTag) TagUTUtil.create(MessagesPopupTag.class);
    }

    /*
     * @see TestCase#tearDown()
     */
    @Override
    protected void cleanUpData() throws Exception {
    }

    /**
     * testDoStartTag01。<br>
     * 
     * (正常系)<br>
     * 観点：C<br>
     * 
     * 入力値
     * リクエスト上のエラー情報：null
     * リクエスト上のメッセージ情報：null
     * 
     * 
     * 期待値
     * 戻り値:int=EVAL_BODY_INCLUDE<br>
     * リクエスト上のON_LOAD_KEY=null
     * 
     * リクエストにエラー・メッセージ情報が共に存在しない時、
     * リクエストにポップアップスクリプトが登録されていない
     * ことと、リクエスト上にスクリプトが登録されていないことを
     * 確認する。
     */
    public void testDoStartTag01() throws Exception {

        // カスタムタグのインスタンスに関連付けされたPageContextを取得
        PageContext pc = TagUTUtil.getPageContext(tag);

        // PageContextに関連付けされた(Mock)HttpServletRequestを取得
        MockHttpServletRequest req = (MockHttpServletRequest) pc.getRequest();
        // ポップアップ先URL
        UTUtil.setPrivateField(tag, "popup", "/popup.do");
        // コンテキストパス指定
        TagUTUtil.setContextPath(tag, "contextPath");

        // テスト実行
        int result = tag.doStartTag();

        // テスト結果確認
        assertEquals(result, Tag.EVAL_BODY_INCLUDE);
        // リクエスト上にスクリプトが登録されていないこと。
        assertNull(req.getAttribute(MessagesPopupTag.ON_LOAD_KEY));
        // セッション上に、エラー・メッセージ情報が登録されていないこと。
        HttpSession session = req.getSession(true);
        Enumeration enumeration = session.getAttributeNames();
        assertFalse(enumeration.hasMoreElements());
    }

    /**
     * testDoStartTag02。<br>
     * 
     * (正常系)<br>
     * 観点：C<br>
     * 
     * 入力値
     * リクエスト上のエラー情報：エラーが空
     * リクエスト上のメッセージ情報：null
     * 
     * 
     * 期待値
     * 戻り値:int=EVAL_BODY_INCLUDE<br>
     * リクエスト上のON_LOAD_KEY=null
     * 
     * リクエストにエラー情報が空で登録されているとき
     * セッション上にエラー情報が登録されず、リクエスト
     * 上にスクリプトが登録されていないことを確認する。
     */
    public void testDoStartTag02() throws Exception {

        // カスタムタグのインスタンスに関連付けされたPageContextを取得
        PageContext pc = TagUTUtil.getPageContext(tag);

        // PageContextに関連付けされた(Mock)HttpServletRequestを取得
        MockHttpServletRequest req = (MockHttpServletRequest) pc.getRequest();

        // テストデータ設定
        // エラー情報は空
        ActionMessages messages = new ActionMessages();
        req.setAttribute(Globals.ERROR_KEY, messages);
        // ポップアップ先URL
        UTUtil.setPrivateField(tag, "popup", "/popup.do");
        // コンテキストパス指定
        TagUTUtil.setContextPath(tag, "contextPath");

        // テスト実行
        int result = tag.doStartTag();

        // テスト結果確認
        assertEquals(result, Tag.EVAL_BODY_INCLUDE);
        // リクエスト上にスクリプトが登録されていないこと。
        assertNull(req.getAttribute(MessagesPopupTag.ON_LOAD_KEY));
        // セッション上に、エラー・メッセージ情報が登録されていないこと。
        HttpSession session = req.getSession(true);
        Enumeration enumeration = session.getAttributeNames();
        assertFalse(enumeration.hasMoreElements());
    }

    /**
     * testDoStartTag03。<br>
     * 
     * (正常系)<br>
     * 観点：C<br>
     * 
     * 入力値
     * リクエスト上のエラー情報：エラー3件
     * リクエスト上のメッセージ情報：null
     * 
     * 期待値
     * 戻り値:int=EVAL_BODY_INCLUDE<br>
     * リクエスト上のON_LOAD_KEY=
     *   window.open("contextPath/popup.do?popup_error_key=XXXXXXX",
     *       "popup",
     *       "");
     * 
     * リクエストにエラー情報が3件登録されているとき
     * 同一のエラー情報がセッションに格納され、リクエスト上
     * 上にスクリプトが登録されることを確認する。
     */
    public void testDoStartTag03() throws Exception {

        // カスタムタグのインスタンスに関連付けされたPageContextを取得
        PageContext pc = TagUTUtil.getPageContext(tag);

        // PageContextに関連付けされた(Mock)HttpServletRequestを取得
        MockHttpServletRequest req = (MockHttpServletRequest) pc.getRequest();

        // テストデータ設定
        // エラー情報3件
        ActionMessages errors = new ActionMessages();
        ActionMessage error1 = new ActionMessage("errors.error1");
        ActionMessage error2 = new ActionMessage("errors.error2");
        ActionMessage error3 = new ActionMessage("errors.error3");
        errors.add(Globals.ERROR_KEY, error1);
        errors.add(Globals.ERROR_KEY, error2);
        errors.add(Globals.ERROR_KEY, error3);
        req.setAttribute(Globals.ERROR_KEY, errors);
        // ポップアップ先URL
        UTUtil.setPrivateField(tag, "popup", "/popup.do");
        // コンテキストパス指定
        TagUTUtil.setContextPath(tag, "contextPath");

        // テスト実行
        int result = tag.doStartTag();

        // テスト結果確認
        assertEquals(result, Tag.EVAL_BODY_INCLUDE);
        // リクエスト上にスクリプトが登録されていること。
        // スクリプトのランダムキー部分以外が一致していること。
        String script = (String) req.getAttribute(MessagesPopupTag.ON_LOAD_KEY);
        assertTrue(
            script.startsWith(
                "  window.open(\"contextPath/popup.do?popup_error_key="));
        assertTrue(
            script.endsWith(
                "\", \"popup\", \"\");"
                    + System.getProperty("line.separator")));

        // セッション上に、エラー情報が登録されていること。
        HttpSession session = req.getSession(true);
        Enumeration enumeration = session.getAttributeNames();
        int cnt = 0;
        while (enumeration.hasMoreElements()) {
            Object obj = session.getAttribute((String) enumeration
                    .nextElement());
            assertEquals(ActionMessages.class.getName(), obj.getClass()
                    .getName());
            assertEquals(3, ((ActionMessages) obj).size(Globals.ERROR_KEY));
            cnt++;
        }
        assertEquals(1, cnt);
    }

    /**
     * testDoStartTag04。<br>
     * 
     * (正常系)<br>
     * 観点：C<br>
     * 
     * 入力値
     * リクエスト上のエラー情報：null
     * リクエスト上のメッセージ情報：メッセージ情報が空
     * 
     * 
     * 期待値
     * 戻り値:int=EVAL_BODY_INCLUDE<br>
     * リクエスト上のON_LOAD_KEY=null
     * 
     * リクエストにメッセージ情報が空で登録されているとき
     * セッション上にメッセージ情報が登録されず、リクエスト
     * 上にスクリプトが登録されていないことを確認する。
     */
    public void testDoStartTag04() throws Exception {

        // カスタムタグのインスタンスに関連付けされたPageContextを取得
        PageContext pc = TagUTUtil.getPageContext(tag);

        // PageContextに関連付けされた(Mock)HttpServletRequestを取得
        MockHttpServletRequest req = (MockHttpServletRequest) pc.getRequest();

        // テストデータ設定
        // エラー情報は空
        ActionMessages messages = new ActionMessages();
        req.setAttribute(Globals.MESSAGE_KEY, messages);
        // ポップアップ先URL
        UTUtil.setPrivateField(tag, "popup", "/popup.do");
        // コンテキストパス指定
        TagUTUtil.setContextPath(tag, "contextPath");

        // テスト実行
        int result = tag.doStartTag();

        // テスト結果確認
        assertEquals(result, Tag.EVAL_BODY_INCLUDE);
        // リクエスト上にスクリプトが登録されていないこと。
        assertNull(req.getAttribute(MessagesPopupTag.ON_LOAD_KEY));
        // セッション上に、エラー・メッセージ情報が登録されていないこと。
        HttpSession session = req.getSession(true);
        Enumeration enumeration = session.getAttributeNames();
        assertFalse(enumeration.hasMoreElements());
    }

    /**
     * testDoStartTag05。<br>
     * 
     * (正常系)<br>
     * 観点：C<br>
     * 
     * 入力値
     * リクエスト上のエラー情報：null
     * リクエスト上のメッセージ情報：3件
     * 
     * 期待値
     * 戻り値:int=EVAL_BODY_INCLUDE<br>
     * リクエスト上のON_LOAD_KEY=
     *   window.open("contextPath/popup.do?popup_message_key=XXXXXXX",
     *       "popup",
     *       "");
     * 
     * リクエストにメッセージ情報が3件登録されているとき
     * 同一のメッセージ情報がセッションに格納され、リクエスト上
     * 上にスクリプトが登録されることを確認する。
     */
    public void testDoStartTag05() throws Exception {

        // カスタムタグのインスタンスに関連付けされたPageContextを取得
        PageContext pc = TagUTUtil.getPageContext(tag);

        // PageContextに関連付けされた(Mock)HttpServletRequestを取得
        MockHttpServletRequest req = (MockHttpServletRequest) pc.getRequest();

        // テストデータ設定
        // メッセージ情報3件
        ActionMessages messages = new ActionMessages();
        ActionMessage message1 = new ActionMessage("messages.message1");
        ActionMessage message2 = new ActionMessage("messages.message2");
        ActionMessage message3 = new ActionMessage("messages.message3");
        messages.add(Globals.MESSAGE_KEY, message1);
        messages.add(Globals.MESSAGE_KEY, message2);
        messages.add(Globals.MESSAGE_KEY, message3);
        req.setAttribute(Globals.MESSAGE_KEY, messages);
        // ポップアップ先URL
        UTUtil.setPrivateField(tag, "popup", "/popup.do");
        // コンテキストパス指定
        TagUTUtil.setContextPath(tag, "contextPath");

        // テスト実行
        int result = tag.doStartTag();

        // テスト結果確認
        assertEquals(result, Tag.EVAL_BODY_INCLUDE);
        // リクエスト上にスクリプトが登録されていること。
        // スクリプトのランダムキー部分以外が一致していること。
        String script = (String) req.getAttribute(MessagesPopupTag.ON_LOAD_KEY);
        assertTrue(
            script.startsWith(
                "  window.open(\"contextPath/popup.do?popup_message_key="));
        assertTrue(
            script.endsWith(
                "\", \"popup\", \"\");"
                    + System.getProperty("line.separator")));

        // セッション上に、メッセージ情報が登録されていること。
        HttpSession session = req.getSession(true);
        Enumeration enumeration = session.getAttributeNames();
        int cnt = 0;
        while (enumeration.hasMoreElements()) {
            Object obj = session.getAttribute((String) enumeration
                    .nextElement());
            assertEquals(ActionMessages.class.getName(), obj.getClass()
                    .getName());
            assertEquals(3, ((ActionMessages) obj).size(Globals.MESSAGE_KEY));
            cnt++;
        }
        assertEquals(1, cnt);
    }

    /**
     * testDoStartTag06。<br>
     * 
     * (正常系)<br>
     * 観点：C<br>
     * 
     * 入力値 リクエスト上のエラー情報：2件 リクエスト上のメッセージ情報：3件
     * 
     * 期待値 戻り値:int=EVAL_BODY_INCLUDE<br>
     * リクエスト上のON_LOAD_KEY=
     * window.open("contextPath/popup.do?popup_error_key=XXXXXXX&popup_message_key=YYYYYY",
     * "popup", "");
     * 
     * リクエストにエラー情報が2件、メッセージ情報が3件登録 されているとき同一のエラー・メッセージ情報がセッションに
     * 格納され、リクエスト上にスクリプトが登録されることを確認する。
     */
    public void testDoStartTag06() throws Exception {

        // カスタムタグのインスタンスに関連付けされたPageContextを取得
        PageContext pc = TagUTUtil.getPageContext(tag);

        // PageContextに関連付けされた(Mock)HttpServletRequestを取得
        MockHttpServletRequest req = (MockHttpServletRequest) pc.getRequest();

        // テストデータ設定
        // エラー情報2件
        ActionMessages errors = new ActionMessages();
        ActionMessage error1 = new ActionMessage("errors.error1");
        ActionMessage error2 = new ActionMessage("errors.error2");
        errors.add(Globals.ERROR_KEY, error1);
        errors.add(Globals.ERROR_KEY, error2);
        req.setAttribute(Globals.ERROR_KEY, errors);

        // メッセージ情報3件
        ActionMessages messages = new ActionMessages();
        ActionMessage message1 = new ActionMessage("messages.message1");
        ActionMessage message2 = new ActionMessage("messages.message2");
        ActionMessage message3 = new ActionMessage("messages.message3");
        messages.add(Globals.MESSAGE_KEY, message1);
        messages.add(Globals.MESSAGE_KEY, message2);
        messages.add(Globals.MESSAGE_KEY, message3);
        req.setAttribute(Globals.MESSAGE_KEY, messages);
        // ポップアップ先URL
        UTUtil.setPrivateField(tag, "popup", "/popup.do");
        // コンテキストパス指定
        TagUTUtil.setContextPath(tag, "contextPath");

        // テスト実行
        int result = tag.doStartTag();

        // テスト結果確認
        assertEquals(result, Tag.EVAL_BODY_INCLUDE);
        // リクエスト上にスクリプトが登録されていること。
        // スクリプトのランダムキー部分以外が一致していること。
        String script = (String) req.getAttribute(MessagesPopupTag.ON_LOAD_KEY);
        assertTrue(
            script.startsWith(
                "  window.open(\"contextPath/popup.do?popup_error_key="));
        assertTrue(
            script.indexOf(
                "&popup_message_key=",
                "  window.open(\"contextPath/popup.do?popup_error_key="
                    .length())
                != -1);
        assertTrue(
            script.endsWith(
                "\", \"popup\", \"\");"
                    + System.getProperty("line.separator")));

        // セッション上に、メッセージ情報が登録されていること。
        HttpSession session = req.getSession(true);
        Enumeration enu = session.getAttributeNames();
        // エラー・メッセージ情報のオブジェクト存在件数
        int errorCount = 0;
        int messageCount = 0;
        int cnt = 0;
        while (enu.hasMoreElements()) {
            Object obj = session.getAttribute((String) enu.nextElement());
            assertEquals(ActionMessages.class.getName(), obj.getClass()
                    .getName());
            if (((ActionMessages) obj).size(Globals.ERROR_KEY) == 2) {
                // エラーが2件存在
                errorCount++;
            }
            if (((ActionMessages) obj).size(Globals.MESSAGE_KEY) == 3) {
                // メッセージが3件存在
                messageCount++;
            }
            cnt++;
        }
        // あわせて2件であること。
        assertEquals(2, cnt);
        // セッション上のエラー・メッセージ情報の
        // 存在件数が1件ずつであること。
        assertEquals(1, errorCount);
        assertEquals(1, messageCount);
    }

    /**
     * testGetOnLoadScript01()。<br>
     * 
     * (正常系)<br>
     * 観点：C<br>
     * 
     * 入力値
     * req=擬似リクエスト
     * errorKey=null
     * messageKey=null
     * popup="/popup.do"
     * windowId=null
     * title=null
     * paramFunc=null
     * param=null
     * paramType=null
     * 
     * 期待値
     * 戻り値:スクリプト文字列null<br>
     * 
     * セッションに格納されるエラーキー、メッセージキーが共に
     * nullであるとき、スクリプト文字列はnullで返却されること。
     */
    public void testGetOnLoadScript01() throws Exception {

        // カスタムタグのインスタンスに関連付けされたPageContextを取得
        PageContext pc = TagUTUtil.getPageContext(tag);

        // PageContextに関連付けされた(Mock)HttpServletRequestを取得
        MockHttpServletRequest req = (MockHttpServletRequest) pc.getRequest();
        // ポップアップ先URL
        UTUtil.setPrivateField(tag, "popup", "/popup.do");
        // コンテキストパス指定
        TagUTUtil.setContextPath(tag, "contextPath");
        
        Class[] argClazz =
            new Class[] {
                HttpServletRequest.class,
                String.class,
                String.class };

        Object[] argObj = new Object[] { req, null, null };

        // テスト実行
        Object retObj =
            UTUtil.invokePrivate(tag, "getOnLoadScript", argClazz, argObj);

        // テスト結果確認
        assertNull(retObj);

    }

    /**
     * testGetOnLoadScript02()。<br>
     * 
     * (正常系)<br>
     * 観点：C<br>
     * 
     * 入力値
     * req=擬似リクエスト
     * errorKey="errorKey"
     * messageKey=null
     * popup="/popup.do"
     * windowId=null
     * title=null
     * paramFunc=null
     * param=null
     * paramType=null
     * 
     * 期待値
     * 戻り値:"  window.open("contextPath/popup.do?popup_error_key=errorKey",
     *  "popup", "");<br>
     * <br>
     * セッションに格納されるエラーキーの文字列が設定され、
     * メッセージキーがnullであるとき、エラーキーがポップアップ先の
     * リクエストパラメータとしてスクリプトに出力されること。
     */
    public void testGetOnLoadScript02() throws Exception {

        // カスタムタグのインスタンスに関連付けされたPageContextを取得
        PageContext pc = TagUTUtil.getPageContext(tag);

        // PageContextに関連付けされた(Mock)HttpServletRequestを取得
        MockHttpServletRequest req = (MockHttpServletRequest) pc.getRequest();
        // ポップアップ先URL
        UTUtil.setPrivateField(tag, "popup", "/popup.do");
        // コンテキストパス指定
        TagUTUtil.setContextPath(tag, "contextPath");

        Class[] argClazz =
            new Class[] {
                HttpServletRequest.class,
                String.class,
                String.class };

        Object[] argObj = new Object[] { req, "errorKey", null };

        // テスト実行
        Object retObj =
            UTUtil.invokePrivate(tag, "getOnLoadScript", argClazz, argObj);

        // テスト結果確認
        assertEquals(
            "  window.open(\"contextPath/popup.do?popup_error_key=errorKey\", \"popup\", \"\");"
                + System.getProperty("line.separator"),
            retObj);

    }

    /**
     * testGetOnLoadScript03()。<br>
     * 
     * (正常系)<br>
     * 観点：C<br>
     * 
     * 入力値
     * req=擬似リクエスト
     * errorKey=null
     * messageKey="messageKey"
     * popup="/popup.do"
     * windowId=null
     * title=null
     * paramFunc=null
     * param=null
     * paramType=null
     * 
     * 期待値
     * 戻り値:"  window.open("contextPath/popup.do?popup_message_key=messageKey",
     *  "popup", "");<br>
     * <br>
     * セッションに格納されるエラーキーがnullであり、
     * メッセージキーに文字列が設定されているとき、メッセージキーが
     * ポップアップ先のリクエストパラメータとしてスクリプトに
     * 出力されること。
     */
    public void testGetOnLoadScript03() throws Exception {

        // カスタムタグのインスタンスに関連付けされたPageContextを取得
        PageContext pc = TagUTUtil.getPageContext(tag);

        // PageContextに関連付けされた(Mock)HttpServletRequestを取得
        MockHttpServletRequest req = (MockHttpServletRequest) pc.getRequest();
        // ポップアップ先URL
        UTUtil.setPrivateField(tag, "popup", "/popup.do");
        // コンテキストパス指定
        TagUTUtil.setContextPath(tag, "contextPath");

        Class[] argClazz =
            new Class[] {
                HttpServletRequest.class,
                String.class,
                String.class };

        Object[] argObj = new Object[] { req, null, "messageKey" };

        // テスト実行
        Object retObj =
            UTUtil.invokePrivate(tag, "getOnLoadScript", argClazz, argObj);

        // テスト結果確認
        assertEquals(
            "  window.open(\"contextPath/popup.do?popup_message_key=messageKey\", \"popup\", \"\");"
                + System.getProperty("line.separator"),
            retObj);

    }

    /**
     * testGetOnLoadScript04()。<br>
     * 
     * (正常系)<br>
     * 観点：C<br>
     * 
     * 入力値
     * req=擬似リクエスト
     * errorKey="errorKey"
     * messageKey="messageKey"
     * popup="/popup.do"
     * windowId=null
     * title=null
     * paramFunc=null
     * param=null
     * paramType=null
     * 
     * 期待値
     * 戻り値:"  window.open("contextPath/popup.do?popup_error_key=errorKey&
     *  popup_message_key=messageKey","popup", "");<br>
     * <br>
     * セッションに格納されるエラー情報、メッセージ情報が
     * 共に存在する時両方のキーがポップアップスクリプトに
     * 出力され、&により結合されていること。
     */
    public void testGetOnLoadScript04() throws Exception {

        // カスタムタグのインスタンスに関連付けされたPageContextを取得
        PageContext pc = TagUTUtil.getPageContext(tag);

        // PageContextに関連付けされた(Mock)HttpServletRequestを取得
        MockHttpServletRequest req = (MockHttpServletRequest) pc.getRequest();
        // ポップアップ先URL
        UTUtil.setPrivateField(tag, "popup", "/popup.do");
        // コンテキストパス指定
        TagUTUtil.setContextPath(tag, "contextPath");

        Class[] argClazz =
            new Class[] {
                HttpServletRequest.class,
                String.class,
                String.class };

        Object[] argObj = new Object[] { req, "errorKey", "messageKey" };

        // テスト実行
        Object retObj =
            UTUtil.invokePrivate(tag, "getOnLoadScript", argClazz, argObj);

        // テスト結果確認
        assertEquals(
            "  window.open(\"contextPath/popup.do?popup_error_key=errorKey&"
                + "popup_message_key=messageKey\", \"popup\", \"\");"
                + System.getProperty("line.separator"),
            retObj);

    }

    /**
     * testGetOnLoadScript05()。<br>
     * 
     * (正常系)<br>
     * 観点：C<br>
     * 
     * 入力値
     * req=擬似リクエスト
     * errorKey="errorKey"
     * messageKey=null
     * popup="/popup.do"
     * windowId="windowId"
     * title=null
     * paramFunc=null
     * param=null
     * paramType=null
     * 
     * 期待値
     * 戻り値:"  windowId = window.open("contextPath/popup.do?
     *  popup_error_key=errorKey","popup", "");<br>
     * <br>
     * 
     * MessagePopupTag#setWindowId()により、ウィンドウIDが指定されて
     * いる場合、スクリプトの戻り値として変数が定義されること。
     */
    public void testGetOnLoadScript05() throws Exception {

        // カスタムタグのインスタンスに関連付けされたPageContextを取得
        PageContext pc = TagUTUtil.getPageContext(tag);

        // PageContextに関連付けされた(Mock)HttpServletRequestを取得
        MockHttpServletRequest req = (MockHttpServletRequest) pc.getRequest();
        // ポップアップ先URL
        UTUtil.setPrivateField(tag, "popup", "/popup.do");
        // コンテキストパス指定
        TagUTUtil.setContextPath(tag, "contextPath");

        Class[] argClazz =
            new Class[] {
                HttpServletRequest.class,
                String.class,
                String.class };

        Object[] argObj = new Object[] { req, "errorKey", null };

        // テスト実行
        UTUtil.setPrivateField(tag, "windowId", "windowId");
        Object retObj =
            UTUtil.invokePrivate(tag, "getOnLoadScript", argClazz, argObj);

        // テスト結果確認
        assertEquals(
            "  windowId = window.open(\"contextPath/popup.do?popup_error_key=errorKey\", \"popup\", \"\");"
                + System.getProperty("line.separator"),
            retObj);
    }

    /**
     * testGetOnLoadScript06()。<br>
     * 
     * (正常系)<br>
     * 観点：C<br>
     * 
     * 入力値
     * req=擬似リクエスト
     * errorKey="errorKey"
     * messageKey=null
     * popup="/popup.do"
     * windowId=null
     * title="title"
     * paramFunc=null
     * param=null
     * paramType=null
     * 
     * 期待値
     * 戻り値:"  window.open("contextPath/popup.do?
     *  popup_error_key=errorKey","title", "");<br>
     * <br>
     * 
     * MessagePopupTag#setTitle()により、ウィンドウタイトルが
     * 指定されている場合、スクリプトの引数として出力されること。
     */
    public void testGetOnLoadScript06() throws Exception {

        // カスタムタグのインスタンスに関連付けされたPageContextを取得
        PageContext pc = TagUTUtil.getPageContext(tag);

        // PageContextに関連付けされた(Mock)HttpServletRequestを取得
        MockHttpServletRequest req = (MockHttpServletRequest) pc.getRequest();
        // ポップアップ先URL
        UTUtil.setPrivateField(tag, "popup", "/popup.do");
        // コンテキストパス指定
        TagUTUtil.setContextPath(tag, "contextPath");

        Class[] argClazz =
            new Class[] {
                HttpServletRequest.class,
                String.class,
                String.class };

        Object[] argObj = new Object[] { req, "errorKey", null };

        // テスト実行
        UTUtil.setPrivateField(tag, "title", "title");
        Object retObj =
            UTUtil.invokePrivate(tag, "getOnLoadScript", argClazz, argObj);

        // テスト結果確認
        assertEquals(
            "  window.open(\"contextPath/popup.do?popup_error_key=errorKey\", \"title\", \"\");"
                + System.getProperty("line.separator"),
            retObj);
    }

    /**
     * testGetOnLoadScript07()。<br>
     * 
     * (正常系)<br>
     * 観点：C<br>
     * 
     * 入力値
     * req=擬似リクエスト
     * errorKey="errorKey"
     * messageKey=null
     * popup="/popup.do"
     * windowId=null
     * title=null
     * paramFunc="paramFunc()"
     * param=null
     * paramType=null
     * 
     * 期待値
     * 戻り値:"  window.open("contextPath/popup.do?
     *  popup_error_key=errorKey","popup", paramFunc());<br>
     * <br>
     * 
     * MessagePopupTag#setParamFunc()により、パラメータ文字列を取得する
     * JavaScript関数名が出力されること。
     */
    public void testGetOnLoadScript07() throws Exception {

        // カスタムタグのインスタンスに関連付けされたPageContextを取得
        PageContext pc = TagUTUtil.getPageContext(tag);

        // PageContextに関連付けされた(Mock)HttpServletRequestを取得
        MockHttpServletRequest req = (MockHttpServletRequest) pc.getRequest();
        // ポップアップ先URL
        UTUtil.setPrivateField(tag, "popup", "/popup.do");
        // コンテキストパス指定
        TagUTUtil.setContextPath(tag, "contextPath");

        Class[] argClazz =
            new Class[] {
                HttpServletRequest.class,
                String.class,
                String.class };

        Object[] argObj = new Object[] { req, "errorKey", null };

        // テスト実行
        UTUtil.setPrivateField(tag, "paramFunc", "paramFunc()");
        Object retObj =
            UTUtil.invokePrivate(tag, "getOnLoadScript", argClazz, argObj);

        // テスト結果確認
        assertEquals(
            "  window.open(\"contextPath/popup.do?popup_error_key=errorKey\", \"popup\", paramFunc());"
                + System.getProperty("line.separator"),
            retObj);
    }

    /**
     * testGetOnLoadScript08()。<br>
     * 
     * (正常系)<br>
     * 観点：C<br>
     * 
     * 入力値
     * req=擬似リクエスト
     * errorKey="errorKey"
     * messageKey=null
     * popup="/popup.do"
     * windowId=null
     * title=null
     * paramFunc=null
     * param="param"
     * paramType=null
     * 
     * 期待値
     * 戻り値:"  window.open("contextPath/popup.do?
     *  popup_error_key=errorKey","popup", "param");<br>
     * <br>
     * 
     * MessagePopupTag#setParam()により、ポップアップ後の位置、サイズ
     * 等を決定するパラメータ文字列がスクリプト引数として渡されること。
     */
    public void testGetOnLoadScript08() throws Exception {

        // カスタムタグのインスタンスに関連付けされたPageContextを取得
        PageContext pc = TagUTUtil.getPageContext(tag);

        // PageContextに関連付けされた(Mock)HttpServletRequestを取得
        MockHttpServletRequest req = (MockHttpServletRequest) pc.getRequest();
        // ポップアップ先URL
        UTUtil.setPrivateField(tag, "popup", "/popup.do");
        // コンテキストパス指定
        TagUTUtil.setContextPath(tag, "contextPath");

        Class[] argClazz =
            new Class[] {
                HttpServletRequest.class,
                String.class,
                String.class };

        Object[] argObj = new Object[] { req, "errorKey", null };

        // テスト実行
        UTUtil.setPrivateField(tag, "param", "param");
        Object retObj =
            UTUtil.invokePrivate(tag, "getOnLoadScript", argClazz, argObj);

        // テスト結果確認
        assertEquals(
            "  window.open(\"contextPath/popup.do?popup_error_key=errorKey\", \"popup\", \"param\");"
                + System.getProperty("line.separator"),
            retObj);
    }

    /**
     * testGetRequestParameterKey01()。<br>
     * 
     * (正常系)<br>
     * 観点：A<br>
     * 
     * 
     * セッションに格納されているエラー・メッセージ情報のキーを
     * 連結したクエリ文字列を取得する。
     */
    public void testGetRequestParameterKey01() throws Exception {
        // 本試験は、testGetOnLoadScriptXX()に内包される。
    }

    /**
     * testSetPopup01()。<br>
     * 
     * (正常系)<br>
     * 観点：A<br>
     * 
     * popupフィールドのセッター確認。
     */
    public void testSetPopup01() throws Exception {

        // テスト実行
        tag.setPopup("popup");

        // テスト結果確認
        assertEquals("popup", UTUtil.getPrivateField(tag, "popup"));
    }

    /**
     * testSetTitle01()。<br>
     * 
     * (正常系)<br>
     * 観点：A<br>
     * 
     * titleフィールドのセッター確認。
     * デフォルトでDEFAULT_TITLEが設定されており、
     * セッターを実行することにより上書きが行えること。
     */
    public void testSetTitle01() throws Exception {

        // テスト実行・結果確認
        // デフォルトでDEFAULT_TITLEが設定されていること。
        assertEquals(
            UTUtil.getPrivateField(MessagesPopupTag.class, "DEFAULT_TITLE"),
            UTUtil.getPrivateField(tag, "title"));

        tag.setTitle("title");
        
        // titleフィールドが上書きされていること。
        assertEquals("title", UTUtil.getPrivateField(tag, "title"));
    }

    /**
     * testSetParam01()。<br>
     * 
     * (正常系)<br>
     * 観点：A<br>
     * 
     * paramフィールドのセッター確認。
     */
    public void testSetParam01() throws Exception {
 
        // テスト実行
        tag.setParam("param");
        
        // テスト結果確認
        assertEquals("param", UTUtil.getPrivateField(tag, "param"));
    }

    /**
     * testSetParamType01()。<br>
     * 
     * (正常系)<br>
     * 観点：A<br>
     * 
     * paramのセッター確認。
     * プロパティファイルからmessages.popup.param. + [セッター引数]を
     * 参照することにより、paramフィールドに、この値が設定されていること。
     */
    public void testSetParamType01() throws Exception {

        // テスト実行
        tag.setParamType("paramType");

        // テスト結果確認
        assertEquals("religion", UTUtil.getPrivateField(tag,"param"));
    }

    /**
     * testSetParamFunc01()。<br>
     * 
     * (正常系)<br>
     * 観点：A<br>
     * 
     * paramFuncフィールドのセッター確認。
     */
    public void testSetParamFunc01() throws Exception {
 
        // テスト実行
        tag.setParamFunc("paramFunc()");
        
        // テスト結果確認
        assertEquals("paramFunc()", UTUtil.getPrivateField(tag, "paramFunc"));
    }

    /**
     * testSetWindowId01()。<br>
     * 
     * (正常系)<br>
     * 観点：A<br>
     * 
     * windowIdフィールドのセッター確認。
     */
    public void testSetWindowId01() throws Exception {
 
        // テスト実行
        tag.setWindowId("windowId");

        // テスト結果確認
        assertEquals("windowId", UTUtil.getPrivateField(tag, "windowId"));
    }

    /**
     * testDoEndTag01()。<br>
     * 
     * (正常系)<br>
     * 観点：B<br>
     * 
     * EVAL_PAGEを返却すること。
     */
    public void testDoEndTag01() throws Exception {
        // テスト実行
        assertEquals(Tag.EVAL_PAGE, tag.doEndTag());
    }

    /**
     * testRelease01()。<br>
     * 
     * (正常系)<br>
     * 観点：B<br>
     * 
     * MessagesPopupTag内で使用したインスタンス変数が開放されていること。
     * 
     */
    public void testRelease01() throws Exception {

        UTUtil.setPrivateField(tag, "popup", "popup");
        UTUtil.setPrivateField(tag, "title", "title");
        UTUtil.setPrivateField(tag, "param", "param");
        UTUtil.setPrivateField(tag, "paramFunc", "paramFunc()");
        UTUtil.setPrivateField(tag, "windowId", "windowId");

        // テスト実行
        tag.release();

        // テスト結果確認
        assertNull(UTUtil.getPrivateField(tag, "popup"));
        assertEquals(
            UTUtil.getPrivateField(MessagesPopupTag.class, "DEFAULT_TITLE"),
            UTUtil.getPrivateField(tag, "title"));
        assertNull(UTUtil.getPrivateField(tag, "param"));
        assertNull(UTUtil.getPrivateField(tag, "paramFunc"));
        assertNull(UTUtil.getPrivateField(tag, "windowId"));
    }

}