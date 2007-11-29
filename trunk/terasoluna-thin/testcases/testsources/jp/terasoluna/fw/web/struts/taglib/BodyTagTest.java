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

import java.io.BufferedReader;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;

import jp.terasoluna.utlib.TagUTUtil;
import jp.terasoluna.utlib.UTUtil;
import jp.terasoluna.utlib.exception.Exception_JspWriterImpl;
import junit.framework.TestCase;

import com.mockrunner.mock.web.MockHttpServletRequest;

/**
 * BodyTag ブラックボックステスト。<br>
 * 前提条件<br>
 * なし<br>
 * <br>
 */
public class BodyTagTest extends TestCase {

    //テスト対象
    BodyTag tag = null;

    /**
     * Constructor for BodyTagTest.
     * @param arg0
     */
    public BodyTagTest(String arg0) {
        super(arg0);
    }

    /*
     * @see TestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        tag = (BodyTag) TagUTUtil.create(BodyTag.class);
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
     * 
     * (正常系)<br>
     * 観点：F<br>
     * 
     * 入力値
     * onLoadBody=Null<br>
     * onload=Null<br>
     * onunload=Null<br>
     * styleClass=Null<br>
     * bgcolor=Null<br>
     * background=Null<br>
     * text=Null<br>
     * link=Null<br>
     * vlink=Null<br>
     * alink=Null<br>
     * out=Not Null<br>
     * ContextPath=*<br>
     * 
     * 期待値
     * 戻り値:int=EVAL_BODY_INCLUDE<br>
     * 出力内容=文字列「"<body>"」および改行<br>
     * 
     * onLoadBody,onload,onunload,styleClass,bgcolor,
     * background,text,link,vlink,alinkのすべてが
     * NULLの場合のテストケース<br>
     */
    public void testDoStartTag01() throws Exception {

        // カスタムタグのインスタンスに関連付けされたPageContextを取得
        PageContext pc = TagUTUtil.getPageContext(tag);

        // PageContextに関連付けされた(Mock)HttpServletRequestを取得
        MockHttpServletRequest req = (MockHttpServletRequest) pc.getRequest();

        // リクエストのON_LOAD属性を設定
        req.setAttribute(BodyTag.ON_LOAD_KEY, null);

        // テストデータ設定
        UTUtil.setPrivateField(tag, "onload", null);
        UTUtil.setPrivateField(tag, "onunload", null);
        UTUtil.setPrivateField(tag, "styleClass", null);
        UTUtil.setPrivateField(tag, "bgcolor", null);
        UTUtil.setPrivateField(tag, "background", null);
        UTUtil.setPrivateField(tag, "text", null);
        UTUtil.setPrivateField(tag, "link", null);
        UTUtil.setPrivateField(tag, "vlink", null);
        UTUtil.setPrivateField(tag, "alink", null);

        // カスタムタグにContextPathを設定
        String contextPath = "contextPath";
        TagUTUtil.setContextPath(tag, contextPath);

        // テスト実行
        int result = tag.doStartTag();

        // テスト結果確認
        assertEquals(result, Tag.EVAL_BODY_INCLUDE);
        BufferedReader reader = TagUTUtil.getOutputReader(tag);
        assertEquals("<body>", reader.readLine());

    } /* testDoStartTag01 End */

    /**
     * testDoStartTag02。<br>
     * 
     * (異常系)<br>
     * 観点：G<br>
     * 
     * 入力値 onLoadBody=*<br>
     * onload=*<br>
     * onunload=*<br>
     * styleClass=*<br>
     * bgcolor=*<br>
     * background=*<br>
     * text=*<br>
     * link=*<br>
     * vlink=*<br>
     * alink=*<br>
     * ContextPath=*<br>
     * 
     * 期待値 戻り値:int=JspTagException<br>
     * 
     * 出力時にIOExceptionが発生した場合のテストケース<br>
     */
    public void testDoStartTag02() throws Exception {

        // カスタムタグのインスタンスに関連付けされたPageContextを取得
        PageContext pc = TagUTUtil.getPageContext(tag);

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
    } /* testDoStartTag02 End */

    /**
     * testDoStartTag03。<br>
     * 
     * (正常系)<br>
     * 観点：F<br>
     * 
     * 入力値
     * onLoadBody="onLoadBody"<br>
     * onload="onload;"<br>
     * onunload="onunload"<br>
     * styleClass="styleClass"<br>
     * bgcolor="bgcolor"<br>
     * background="/background"<br>
     * text="text"<br>
     * link="link"<br>
     * vlink="vlink"<br>
     * alink="alink"<br>
     * out=Not Null<br>
     * ContextPath="/path"<br>
     * 
     * 期待値
     * 戻り値:int=EVAL_BODY_INCLUDE<br>
     * 出力内容=文字列
     * 「<body onLoad="__onLoad__()" onUnLoad="onunload" class="styleClass" 
     * bgcolor="bgcolor" background="contextPath/background" 
     * text="text" link="link" vlink="vlink" alink="alink">」改行
     * 「<script type="text/javascript">」改行
     * 「<!--」改行
     * 「function __onLoad__() {」改行
     * 「  onload;」改行
     * 「onLoadBody}」改行
     * 「//-->」改行
     * 「</script>」改行<br>
     * 
     * onLoadBody,onload,onunload,styleClass,bgcolor,
     * background,text,link,vlink,alinkのすべてに値があり、
     * 且つ、backgroundの値の最初に「/」があり、且つ、
     * onloadの最後に「;」がある場合のテストケース<br>
     */
    public void testDoStartTag03() throws Exception {

        // カスタムタグのインスタンスに関連付けされたPageContextを取得
        PageContext pc = TagUTUtil.getPageContext(tag);

        // PageContextに関連付けされた(Mock)HttpServletRequestを取得
        MockHttpServletRequest req = (MockHttpServletRequest) pc.getRequest();

        // リクエストのON_LOAD属性を設定
        req.setAttribute(BodyTag.ON_LOAD_KEY, "onLoadBody");

        // テストデータ設定
        UTUtil.setPrivateField(tag, "onload", "onload;");
        UTUtil.setPrivateField(tag, "onunload", "onunload");
        UTUtil.setPrivateField(tag, "styleClass", "styleClass");
        UTUtil.setPrivateField(tag, "bgcolor", "bgcolor");
        UTUtil.setPrivateField(tag, "background", "/background");
        UTUtil.setPrivateField(tag, "text", "text");
        UTUtil.setPrivateField(tag, "link", "link");
        UTUtil.setPrivateField(tag, "vlink", "vlink");
        UTUtil.setPrivateField(tag, "alink", "alink");

        // カスタムタグにContextPathを設定
        String contextPath = "contextPath";
        TagUTUtil.setContextPath(tag, contextPath);

        // テスト実行
        int result = tag.doStartTag();

        // テスト結果確認
        assertEquals(result, Tag.EVAL_BODY_INCLUDE);
        BufferedReader reader = TagUTUtil.getOutputReader(tag);
        assertEquals("<body onLoad=\"__onLoad__()\" onUnLoad=\"onunload\" "
                + "class=\"styleClass\" bgcolor=\"bgcolor\" "
                + "background=\"contextPath/background\" "
                + "text=\"text\" link=\"link\" vlink=\"vlink\" "
                + "alink=\"alink\">", reader.readLine());
        assertEquals("<script type=\"text/javascript\">", reader.readLine());
        assertEquals("<!--", reader.readLine());
        assertEquals("function __onLoad__() {", reader.readLine());
        assertEquals("  onload;", reader.readLine());
        assertEquals("onLoadBody}", reader.readLine());
        assertEquals("//-->", reader.readLine());
        assertEquals("</script>", reader.readLine());

    } /* testDoStartTag03 End */

    /**
     * testDoStartTag04。<br>
     * 
     * (正常系)<br>
     * 観点：F<br>
     * 
     * 入力値
     * onLoadBody="onLoadBody"<br>
     * onload="onload"<br>
     * onunload="onunload"<br>
     * styleClass="styleClass"<br>
     * bgcolor="bgcolor"<br>
     * background="background"<br>
     * text="text"<br>
     * link="link"<br>
     * vlink="vlink"<br>
     * alink="alink"<br>
     * out=Not Null<br>
     * ContextPath="/path"<br>
     * 
     * 期待値
     * 戻り値:int=EVAL_BODY_INCLUDE<br>
     * 出力内容=文字列
     * 「<body onLoad="__onLoad__()" onUnLoad="onunload" class="styleClass" 
     * bgcolor="bgcolor" background="/contextPath/background" 
     * text="text" link="link" vlink="vlink" alink="alink">」改行
     * 「<script type="text/javascript">」改行
     * 「<!--」改行
     * 「function __onLoad__() {」改行
     * 「  onload;」改行
     * 「onLoadBody}」改行
     * 「//-->」改行
     * 「</script>」改行<br>
     * 
     * onLoadBody,onload,onunload,styleClass,bgcolor,
     * background,text,link,vlink,alinkのすべてに値があり、
     * 且つ、backgroundの値の最初に「/」がなく、且つ、
     * onloadの最後に「;」がない場合のテストケース<br>
     */
    public void testDoStartTag04() throws Exception {

        // カスタムタグのインスタンスに関連付けされたPageContextを取得
        PageContext pc = TagUTUtil.getPageContext(tag);

        // PageContextに関連付けされた(Mock)HttpServletRequestを取得
        MockHttpServletRequest req = (MockHttpServletRequest) pc.getRequest();

        // リクエストのON_LOAD属性を設定
        req.setAttribute(BodyTag.ON_LOAD_KEY, "onLoadBody");

        // テストデータ設定
        UTUtil.setPrivateField(tag, "onload", "onload");
        UTUtil.setPrivateField(tag, "onunload", "onunload");
        UTUtil.setPrivateField(tag, "styleClass", "styleClass");
        UTUtil.setPrivateField(tag, "bgcolor", "bgcolor");
        UTUtil.setPrivateField(tag, "background", "background");
        UTUtil.setPrivateField(tag, "text", "text");
        UTUtil.setPrivateField(tag, "link", "link");
        UTUtil.setPrivateField(tag, "vlink", "vlink");
        UTUtil.setPrivateField(tag, "alink", "alink");

        // カスタムタグにContextPathを設定
        String contextPath = "contextPath";
        TagUTUtil.setContextPath(tag, contextPath);

        // テスト実行
        int result = tag.doStartTag();

        // テスト結果確認
        assertEquals(result, Tag.EVAL_BODY_INCLUDE);
        BufferedReader reader = TagUTUtil.getOutputReader(tag);
        assertEquals("<body onLoad=\"__onLoad__()\" onUnLoad=\"onunload\" "
                + "class=\"styleClass\" bgcolor=\"bgcolor\" "
                + "background=\"contextPath/background\" "
                + "text=\"text\" link=\"link\" vlink=\"vlink\" "
                + "alink=\"alink\">", reader.readLine());
        assertEquals("<script type=\"text/javascript\">", reader.readLine());
        assertEquals("<!--", reader.readLine());
        assertEquals("function __onLoad__() {", reader.readLine());
        assertEquals("  onload;", reader.readLine());
        assertEquals("onLoadBody}", reader.readLine());
        assertEquals("//-->", reader.readLine());
        assertEquals("</script>", reader.readLine());

    } /* testDoStartTag04 End */

    /**
     * testDoStartTag05。<br>
     * 
     * (正常系)<br>
     * 観点：F<br>
     * 
     * 入力値
     * onLoadBody=Null<br>
     * onload="onload"<br>
     * onunload="onunload"<br>
     * styleClass="styleClass"<br>
     * bgcolor="bgcolor"<br>
     * background="background"<br>
     * text="text"<br>
     * link="link"<br>
     * vlink="vlink"<br>
     * alink="alink"<br>
     * out=Not Null<br>
     * ContextPath="/path"<br>
     * 
     * 期待値
     * 戻り値:int=EVAL_BODY_INCLUDE<br>
     * 出力内容=文字列
     * 「<body onLoad="__onLoad__()" onUnLoad="onunload" class="styleClass" 
     * bgcolor="bgcolor" background="/path/background" 
     * text="text" link="link" vlink="vlink" alink="alink">」改行
     * 「<script type="text/javascript">」改行
     * 「<!--」改行「function __onLoad__() {」改行
     * 「  onload;」改行
     * 「}」改行
     * 「//-->」改行
     * 「</script>」改行<br>
     * 
     * onLoadBodyがNULLで、onload,onunload,styleClass,bgcolor,
     * background,text,link,vlink,alinkのすべてに値がある場合のテストケース<br>
     */
    public void testDoStartTag05() throws Exception {

        // カスタムタグのインスタンスに関連付けされたPageContextを取得
        PageContext pc = TagUTUtil.getPageContext(tag);

        // PageContextに関連付けされた(Mock)HttpServletRequestを取得
        MockHttpServletRequest req = (MockHttpServletRequest) pc.getRequest();

        // リクエストのON_LOAD属性を設定
        req.setAttribute(BodyTag.ON_LOAD_KEY, null);

        // テストデータ設定
        UTUtil.setPrivateField(tag, "onload", "onload");
        UTUtil.setPrivateField(tag, "onunload", "onunload");
        UTUtil.setPrivateField(tag, "styleClass", "styleClass");
        UTUtil.setPrivateField(tag, "bgcolor", "bgcolor");
        UTUtil.setPrivateField(tag, "background", "background");
        UTUtil.setPrivateField(tag, "text", "text");
        UTUtil.setPrivateField(tag, "link", "link");
        UTUtil.setPrivateField(tag, "vlink", "vlink");
        UTUtil.setPrivateField(tag, "alink", "alink");

        // カスタムタグにContextPathを設定
        String contextPath = "contextPath";
        TagUTUtil.setContextPath(tag, contextPath);

        // テスト実行
        int result = tag.doStartTag();

        // テスト結果確認
        assertEquals(result, Tag.EVAL_BODY_INCLUDE);
        BufferedReader reader = TagUTUtil.getOutputReader(tag);
        assertEquals("<body onLoad=\"__onLoad__()\" onUnLoad=\"onunload\" "
                + "class=\"styleClass\" bgcolor=\"bgcolor\" "
                + "background=\"contextPath/background\" "
                + "text=\"text\" link=\"link\" vlink=\"vlink\" "
                + "alink=\"alink\">", reader.readLine());
        assertEquals("<script type=\"text/javascript\">", reader.readLine());
        assertEquals("<!--", reader.readLine());
        assertEquals("function __onLoad__() {", reader.readLine());
        assertEquals("  onload;", reader.readLine());
        assertEquals("}", reader.readLine());
        assertEquals("//-->", reader.readLine());
        assertEquals("</script>", reader.readLine());

    } /* testDoStartTag05 End */

    /**
     * testDoStartTag06。<br>
     * 
     * (正常系)<br>
     * 観点：F<br>
     * 
     * 入力値
     * onLoadBody="onLoadBody"<br>
     * onload=Null<br>
     * onunload="onunload"<br>
     * styleClass="styleClass"<br>
     * bgcolor="bgcolor"<br>
     * background="background"<br>
     * text="text"<br>
     * link="link"<br>
     * vlink="vlink"<br>
     * alink="alink"<br>
     * out=Not Null<br>
     * ContextPath="/path"<br>
     * 
     * 期待値
     * 戻り値:int=EVAL_BODY_INCLUDE<br>
     * 出力内容=文字列
     * 「<body onLoad="__onLoad__()" onUnLoad="onunload" class="styleClass" 
     * bgcolor="bgcolor" background="/path/background" 
     * text="text" link="link" vlink="vlink" alink="alink">」改行
     * 「<script type="text/javascript">」改行
     * 「<!--」改行
     * 「function __onLoad__() {」改行
     * 「  onLoadBody}」改行
     * 「//-->」改行
     * 「</script>」改行<br>
     * 
     * onloadがNULLで、onLoadBody,onunload,styleClass,bgcolor,
     * background,text,link,vlink,alinkのすべてに値がある場合のテストケース<br>
     */
    public void testDoStartTag06() throws Exception {

        // カスタムタグのインスタンスに関連付けされたPageContextを取得
        PageContext pc = TagUTUtil.getPageContext(tag);

        // PageContextに関連付けされた(Mock)HttpServletRequestを取得
        MockHttpServletRequest req = (MockHttpServletRequest) pc.getRequest();

        // リクエストのON_LOAD属性を設定
        req.setAttribute(BodyTag.ON_LOAD_KEY, "onLoadBody");

        // テストデータ設定
        UTUtil.setPrivateField(tag, "onload", null);
        UTUtil.setPrivateField(tag, "onunload", "onunload");
        UTUtil.setPrivateField(tag, "styleClass", "styleClass");
        UTUtil.setPrivateField(tag, "bgcolor", "bgcolor");
        UTUtil.setPrivateField(tag, "background", "background");
        UTUtil.setPrivateField(tag, "text", "text");
        UTUtil.setPrivateField(tag, "link", "link");
        UTUtil.setPrivateField(tag, "vlink", "vlink");
        UTUtil.setPrivateField(tag, "alink", "alink");

        // カスタムタグにContextPathを設定
        String contextPath = "contextPath";
        TagUTUtil.setContextPath(tag, contextPath);

        // テスト実行
        int result = tag.doStartTag();

        // テスト結果確認
        assertEquals(result, Tag.EVAL_BODY_INCLUDE);
        BufferedReader reader = TagUTUtil.getOutputReader(tag);
        assertEquals("<body onLoad=\"__onLoad__()\" onUnLoad=\"onunload\" "
                + "class=\"styleClass\" bgcolor=\"bgcolor\" "
                + "background=\"contextPath/background\" "
                + "text=\"text\" link=\"link\" vlink=\"vlink\" "
                + "alink=\"alink\">", reader.readLine());
        assertEquals("<script type=\"text/javascript\">", reader.readLine());
        assertEquals("<!--", reader.readLine());
        assertEquals("function __onLoad__() {", reader.readLine());
        assertEquals("onLoadBody}", reader.readLine());
        assertEquals("//-->", reader.readLine());
        assertEquals("</script>", reader.readLine());

    } /* testDoStartTag06 End */

    /**
     * testDoEndTag01。<br>
     * 
     * (正常系)<br>
     * 観点：A<br>
     * 
     * 入力値
     * out=Not Null<br>
     * 
     * 期待値
     * 戻り値:int=EVAL_PAGE<br>
     * 出力内容=文字列「"</body>"」<br>
     * 
     * HTMLのBODYタグが正常に出力された場合のテストケース<br>
     */
    public void testDoEndTag01() throws Exception {

        // テスト実行
        int result = tag.doEndTag();

        // テスト結果確認
        assertEquals(result, Tag.EVAL_PAGE);
        BufferedReader reader = TagUTUtil.getOutputReader(tag);
        assertEquals("</body>", reader.readLine());

    } /* testDoEndTag01 End */

    /**
     * testDoEndTag02。<br>
     * 
     * (異常系)<br>
     * 観点：G<br>
     * 
     * 入力値
     * 
     * 期待値
     * 戻り値:int=JspTagException<br>
     * 出力内容=-<br>
     * 
     * 出力時にIOExceptionが発生した場合のテストケース<br>
     */
    public void testDoEndTag02() throws Exception {

        // カスタムタグのインスタンスに関連付けされたPageContextを取得
        PageContext pc = TagUTUtil.getPageContext(tag);

        Exception_JspWriterImpl out = new Exception_JspWriterImpl();
        out.setTrue();
        out.setTiming(1);
        UTUtil.setPrivateField(pc, "jspWriter", out);

        // テスト実行
        try {
            tag.doEndTag();
            fail();
        } catch (JspTagException ex) {
            assertEquals("javax.servlet.jsp.JspTagException:"
                    + " java.io.IOException", ex.toString());
            return;
        }
    } /* testDoEndTag02 End */

    /**
     * testRelease01。<br>
     * 
     * (正常系)<br>
     * 観点：C<br>
     * 
     * 入力値
     * onload=*<br>
     * onunload=*<br>
     * styleClass=*<br>
     * bgcolor=*<br>
     * background=*<br>
     * text=*<br>
     * link=*<br>
     * vlink=*<br>
     * alink=*<br>
     * 
     * 期待値
     * 戻り値:void<br>
     * onload=Null<br>
     * onunload=Null<br>
     * styleClass=Null<br>
     * bgcolor=Null<br>
     * background=Null<br>
     * text=Null<br>
     * link=Null<br>
     * vlink=Null<br>
     * alink=Null<br>
     * 
     * 前提条件として設定した各値が、実行時に各条件値が
     * 初期化されることを確認する<br>
     */
    public void testRelease01() throws Exception {
        // テストデータ設定
        UTUtil.setPrivateField(tag, "onload", "onload");
        UTUtil.setPrivateField(tag, "onunload", "onunload");
        UTUtil.setPrivateField(tag, "styleClass", "styleClass");
        UTUtil.setPrivateField(tag, "bgcolor", "bgcolor");
        UTUtil.setPrivateField(tag, "background", "background");
        UTUtil.setPrivateField(tag, "text", "text");
        UTUtil.setPrivateField(tag, "link", "link");
        UTUtil.setPrivateField(tag, "vlink", "vlink");
        UTUtil.setPrivateField(tag, "alink", "alink");

        // テスト実行
        tag.release();

        // テスト結果確認
        assertNull(UTUtil.getPrivateField(tag, "onload"));
        assertNull(UTUtil.getPrivateField(tag, "onunload"));
        assertNull(UTUtil.getPrivateField(tag, "styleClass"));
        assertNull(UTUtil.getPrivateField(tag, "bgcolor"));
        assertNull(UTUtil.getPrivateField(tag, "background"));
        assertNull(UTUtil.getPrivateField(tag, "text"));
        assertNull(UTUtil.getPrivateField(tag, "link"));
        assertNull(UTUtil.getPrivateField(tag, "vlink"));
        assertNull(UTUtil.getPrivateField(tag, "alink"));

    } /* testRelease01 End */

    /**
     * testSetOnload01。<br>
     * 
     * (正常系)<br>
     * 観点：A<br>
     * 
     * 入力値 onload="onload"<br>
     * 
     * 期待値 戻り値:void<br>
     * onload="onload"<br>
     * 
     * セットした値を確認するテストケース<br>
     */
    public void testSetOnload01() throws Exception {
        // テスト実行
        tag.setOnload("onload");

        // テスト結果確認
        assertEquals("onload", UTUtil.getPrivateField(tag, "onload"));

    } /* testSetOnload01 End */

    /**
     * testSetOnunload01。<br>
     * 
     * (正常系)<br>
     * 観点：A<br>
     * 
     * 入力値
     * onunload="onunload"<br>
     * 
     * 期待値
     * 戻り値:void<br>
     * onunload="onunload"<br>
     * 
     * セットした値を確認するテストケース<br>
     */
    public void testSetOnunload01() throws Exception {
        // テスト実行
        tag.setOnunload("onunload");

        // テスト結果確認
        assertEquals("onunload", UTUtil.getPrivateField(tag, "onunload"));

    } /* testSetOnunload01 End */

    /**
     * testSetStyleClass01。<br>
     * 
     * (正常系)<br>
     * 観点：A<br>
     * 
     * 入力値
     * styleClass="styleClass"<br>
     * 
     * 期待値
     * 戻り値:void<br>
     * styleClass="styleClass"<br>
     * 
     * セットした値を確認するテストケース<br>
     */
    public void testSetStyleClass01() throws Exception {
        // テスト実行
        tag.setStyleClass("styleClass");

        // テスト結果確認
        assertEquals("styleClass", UTUtil.getPrivateField(tag, "styleClass"));

    } /* testSetStyleClass01 End */

    /**
     * testSetBgcolor01。<br>
     * 
     * (正常系)<br>
     * 観点：A<br>
     * 
     * 入力値
     * bgcolor="bgcolor"<br>
     * 
     * 期待値
     * 戻り値:void<br>
     * bgcolor="bgcolor"<br>
     * 
     * セットした値を確認するテストケース<br>
     */
    public void testSetBgcolor01() throws Exception {
        // テスト実行
        tag.setBgcolor("bgcolor");

        // テスト結果確認
        assertEquals("bgcolor", UTUtil.getPrivateField(tag, "bgcolor"));

    } /* testSetBgcolor01 End */

    /**
     * testSetBackground01。<br>
     * 
     * (正常系)<br>
     * 観点：A<br>
     * 
     * 入力値
     * background="background"<br>
     * 
     * 期待値
     * 戻り値:void<br>
     * background="background"<br>
     * 
     * セットした値を確認するテストケース<br>
     */
    public void testSetBackground01() throws Exception {
        // テスト実行
        tag.setBackground("background");

        // テスト結果確認
        assertEquals("background", UTUtil.getPrivateField(tag, "background"));

    } /* testSetBackground01 End */

    /**
     * testSetText01。<br>
     * 
     * (正常系)<br>
     * 観点：A<br>
     * 
     * 入力値
     * text="text"<br>
     * 
     * 期待値
     * 戻り値:void<br>
     * text="text"<br>
     * 
     * セットした値を確認するテストケース<br>
     */
    public void testSetText01() throws Exception {
        // テスト実行
        tag.setText("text");

        // テスト結果確認
        assertEquals("text", UTUtil.getPrivateField(tag, "text"));

    } /* testSetText01 End */

    /**
     * testSetLink01。<br>
     * 
     * (正常系)<br>
     * 観点：A<br>
     * 
     * 入力値
     * link="link"<br>
     * 
     * 期待値
     * 戻り値:void<br>
     * link="link"<br>
     * 
     * セットした値を確認するテストケース<br>
     */
    public void testSetLink01() throws Exception {
        // テスト実行
        tag.setLink("link");

        // テスト結果確認
        assertEquals("link", UTUtil.getPrivateField(tag, "link"));

    } /* testSetLink01 End */

    /**
     * testSetVlink01。<br>
     * 
     * (正常系)<br>
     * 観点：A<br>
     * 
     * 入力値
     * vlink="vlink"<br>
     * 
     * 期待値
     * 戻り値:void<br>
     * vlink="vlink"<br>
     * 
     * セットした値を確認するテストケース<br>
     */
    public void testSetVlink01() throws Exception {
        // テスト実行
        tag.setVlink("vlink");

        // テスト結果確認
        assertEquals("vlink", UTUtil.getPrivateField(tag, "vlink"));

    } /* testSetVlink01 End */

    /**
     * testSetAlink01。<br>
     * 
     * (正常系)<br>
     * 観点：A<br>
     * 
     * 入力値
     * alink="alink"<br>
     * 
     * 期待値
     * 戻り値:void<br>
     * alink="alink"<br>
     * 
     * セットした値を確認するテストケース<br>
     */
    public void testSetAlink01() throws Exception {
        // テスト実行
        tag.setAlink("alink");

        // テスト結果確認
        assertEquals("alink", UTUtil.getPrivateField(tag, "alink"));

    } /* testSetAlink01 End */

} /* BodyTagTest Class End */
