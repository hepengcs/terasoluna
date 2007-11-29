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

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;

import jp.terasoluna.utlib.TagUTUtil;
import jp.terasoluna.utlib.UTUtil;
import jp.terasoluna.utlib.exception.Exception_JspWriterImpl;
import jp.terasoluna.utlib.exception.Exception_PageContextImpl;
import junit.framework.TestCase;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.taglib.logic.IterateTag;

import com.mockrunner.mock.web.MockHttpServletRequest;

/**
 * SubmitTag ブラックボックステスト。<br>
 * 前提条件<br>
 * なし<br>
 * <br>
 */
public class SubmitTagTest extends TestCase {

    //テスト対象
    SubmitTag tag = null;

    /**
     * Constructor for SubmitTagTest.
     * @param arg0
     */
    public SubmitTagTest(String arg0) {
        super(arg0);
    }

    /*
     * @see TestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        tag = (SubmitTag) TagUTUtil.create(SubmitTag.class);
    }

    /*
     * @see TestCase#tearDown()
     */
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * testDoEndTag01。<br>
     * 
     * (正常系)<br>
     * 観点：F<br>
     * 
     * 入力値
     * value(label)=Null<br>
     * text="text"<br>
     * target=Not Null<br>
     * frameScript=Null<br>
     * accesskey=Not Null<br>
     * tabindex=Not Null<br>
     * onClick=Null<br>
     * property= Not Null<br>
     * indexed = false<br>
     * 
     * 期待値
     * 戻り値:int=EVAL_BODY_INCLUDE<br>
     * 
     * サブミットタグのボタンラベルにスーパークラスの”text”を使用、
     * ターゲットの指定処理を行い、スーパークラスの”accesskey”および
     * ”tabindex”の指定を行い、ページに関連付けられたライタに
     * 書き込む場合のテストクラス<br>
     * indexed=falseの場合、nameの値が配列型で出力されないことの確認<br>
     */
    public void testDoEndTag01() throws Exception {

        //テストデータ設定
        PageContext pc = TagUTUtil.getPageContext(tag);
        UTUtil.setPrivateField(tag, "value", null);
        UTUtil.setPrivateField(tag, "text", "text");
        UTUtil.setPrivateField(tag, "target", "target");
        UTUtil.setPrivateField(tag, "accesskey", "accesskey");
        UTUtil.setPrivateField(tag, "tabindex", "tabindex");
        UTUtil.setPrivateField(tag, "property", "submit");

        ActionMapping mapping = new ActionMapping();
        mapping.setName("MAPPING_TEST_FORM");
        MockHttpServletRequest req = (MockHttpServletRequest) pc.getRequest();
        req.setAttribute(Globals.MAPPING_KEY, mapping);

        //テスト実行
        int result = tag.doEndTag();

        //テスト結果確認
        assertEquals(Tag.EVAL_PAGE, result);
        BufferedReader reader = TagUTUtil.getOutputReader(tag);
        assertEquals("", reader.readLine());
        assertEquals("<script type=\"text/javascript\">", reader.readLine());
        assertEquals("<!--", reader.readLine());
        assertEquals(
            "  function __setFrameTarget(__frTarget) {",
            reader.readLine());
        assertEquals(
            "    document.MAPPING_TEST_FORM.target = __frTarget;",
            reader.readLine());
        assertEquals("  }", reader.readLine());
        assertEquals("//-->", reader.readLine());
        assertEquals("</script>", reader.readLine());
        assertEquals("", reader.readLine());
        assertEquals(
            "<input type=\"submit\" name=\"submit\" "
                + "accesskey=\"accesskey\" "
                + "tabindex=\"tabindex\" value=\"text\" "
                + "onclick=\"__setFrameTarget('target')\">",
            reader.readLine());
        assertEquals("frameScript", (String) pc.getAttribute("frameScript"));

    } /* testDoEndTag01 End */

    /**
     * testDoEndTag02。<br>
     * 
     * (正常系)<br>
     * 観点：F<br>
     * 
     * 入力値
     * value(label)="label"<br>
     * text=*<br>
     * target=Not Null<br>
     * frameScript=Not Null<br>
     * accesskey=Null<br>
     * tabindex=Null<br>
     * onClick=Not Null<br>
     * property= Not Null<br>
     * indexed=true<br>
     * 
     * 期待値
     * 戻り値:int=EVAL_BODY_INCLUDE<br>
     * 出力内容=文字列
     * 「<input type="submit" name="[property]" 
     * value="[label]" [EventHandlers] [Styles] >」<br>
     * 
     * サブミットタグのボタンラベルは指定された文字列を使用、
     * スーパークラスのgetOnclickメソッドより取得した値を付与し、
     * ページに関連付けられたライタに書き込む場合のテストクラス<br>
     * indexed=trueの場合、nameの値が配列型で出力されることの確認<br>
     */
    public void testDoEndTag02() throws Exception {

        //テストデータ設定
        PageContext pc = TagUTUtil.getPageContext(tag);
        UTUtil.setPrivateField(tag, "value", "label");
        UTUtil.setPrivateField(tag, "text", "text");
        UTUtil.setPrivateField(tag, "target", "target");
        pc.setAttribute("frameScript", "frameScript");
        UTUtil.setPrivateField(tag, "accesskey", null);
        UTUtil.setPrivateField(tag, "tabindex", null);
        tag.setOnclick("onclick");
        UTUtil.setPrivateField(tag, "property", "submit");
        tag.setIndexed(true);
        tag.setParent(new IterateTag());

        ActionMapping mapping = new ActionMapping();
        mapping.setName("MAPPING_TEST_FORM");
        MockHttpServletRequest req = (MockHttpServletRequest) pc.getRequest();
        req.setAttribute(Globals.MAPPING_KEY, mapping);

        //テスト実行
        int result = tag.doEndTag();

        //テスト結果確認
        assertEquals(Tag.EVAL_PAGE, result);
        BufferedReader reader = TagUTUtil.getOutputReader(tag);
        assertEquals(
            "<input type=\"submit\" name=\"submit[0]\" "
                + "value=\"label\" "
                + "onclick=\"__setFrameTarget('target');onclick\">",
            reader.readLine());

    } /* testDoEndTag02 End */

    /**
     * testDoEndTag03。<br>
     * 
     * (正常系)<br>
     * 観点：F<br>
     * 
     * 入力値
     * value(label)=Null<br>
     * text=Null<br>
     * target=Null<br>
     * frameScript=Not Null<br>
     * accesskey=Null<br>
     * tabindex=Null<br>
     * onClick=*<br>
     * property= Not Null<br>
     * indexed=false<br>
     * 
     * 期待値
     * 戻り値:int=EVAL_BODY_INCLUDE<br>
     * 出力内容=文字列「<input type="submit" name="[property]" 
     * accesskey="[accesskey]" tabindex="[tabindex]" 
     * value="[label]" [EventHandlers] [Styles] >」<br>
     * 
     * サブミットタグのボタンラベルは、
     * 何も指定がないため文字列”submit”を使用し、
     * サブミットボタンタグの文字列を生成し、
     * ページに関連付けられたライタに書き込む場合のテストクラス<br>
     * indexed=falseの場合、nameの値が配列型で出力されないことの確認<br>
     */
    public void testDoEndTag03() throws Exception {

        //テストデータ設定
        PageContext pc = TagUTUtil.getPageContext(tag);
        UTUtil.setPrivateField(tag, "value", null);
        UTUtil.setPrivateField(tag, "text", null);
        UTUtil.setPrivateField(tag, "target", null);
        pc.setAttribute("frameScript", "frameScript");
        UTUtil.setPrivateField(tag, "accesskey", null);
        UTUtil.setPrivateField(tag, "tabindex", null);
        UTUtil.setPrivateField(tag, "property", "submit");

        ActionMapping mapping = new ActionMapping();
        mapping.setName("MAPPING_TEST_FORM");
        MockHttpServletRequest req = (MockHttpServletRequest) pc.getRequest();
        req.setAttribute(Globals.MAPPING_KEY, mapping);

        //テスト実行
        int result = tag.doEndTag();

        //テスト結果確認
        assertEquals(Tag.EVAL_PAGE, result);
        BufferedReader reader = TagUTUtil.getOutputReader(tag);
        assertEquals(
            "<input type=\"submit\" name=\"submit\" " + "value=\"Submit\">",
            reader.readLine());

    } /* testDoEndTag03 End */

    /**
     * testDoEndTag04。<br>
     * 
     * (正常系)<br>
     * 観点：F<br>
     * 
     * 入力値
     * value(label)=Null<br>
     * text=""<br>
     * target=Null<br>
     * frameScript=Not Null<br>
     * accesskey=Null<br>
     * tabindex=Null<br>
     * onClick=*<br>
     * property= Not Null<br>
     * indexed=true<br>
     * 
     * 期待値
     * 戻り値:int=EVAL_BODY_INCLUDE<br>
     * 出力内容=文字列「<input type="submit" 
     * name="[property]" accesskey="[accesskey]" 
     * tabindex="[tabindex]" value="[label]" 
     * [EventHandlers] [Styles] >」<br>
     * 
     * 指定されたサブミットタグのボタンラベルが空白文字列のため、
     * ラベルには文字列”submit”を使用し、
     * サブミットボタンタグの文字列を生成し、
     * ページに関連付けられたライタに書き込む場合のテストクラス<br>
     * indexed=trueの場合、nameの値が配列型で出力されることの確認<br>
     */
    public void testDoEndTag04() throws Exception {

        //テストデータ設定
        PageContext pc = TagUTUtil.getPageContext(tag);
        UTUtil.setPrivateField(tag, "value", null);
        UTUtil.setPrivateField(tag, "text", "");
        UTUtil.setPrivateField(tag, "target", null);
        pc.setAttribute("frameScript", "frameScript");
        UTUtil.setPrivateField(tag, "accesskey", null);
        UTUtil.setPrivateField(tag, "tabindex", null);
        UTUtil.setPrivateField(tag, "property", "submit");
        tag.setIndexed(true);
        tag.setParent(new IterateTag());

        ActionMapping mapping = new ActionMapping();
        mapping.setName("MAPPING_TEST_FORM");
        MockHttpServletRequest req = (MockHttpServletRequest) pc.getRequest();
        req.setAttribute(Globals.MAPPING_KEY, mapping);

        //テスト実行
        int result = tag.doEndTag();

        //テスト結果確認
        assertEquals(Tag.EVAL_PAGE, result);
        BufferedReader reader = TagUTUtil.getOutputReader(tag);
        assertEquals(
            "<input type=\"submit\" name=\"submit[0]\" " + "value=\"Submit\">",
            reader.readLine());

    } /* testDoEndTag04 End */

    /**
     * testDoEndTag05。<br>
     * 
     * (正常系)<br>
     * 観点：F<br>
     * 
     * 入力値
     * value(label)=""<br>
     * text=*<br>
     * target=Null<br>
     * frameScript=Not Null<br>
     * accesskey=Null<br>
     * tabindex=Null<br>
     * onClick=*<br>
     * property= Not Null<br>
     * indexed=false<br>
     * 
     * 期待値
     * 戻り値:int=EVAL_BODY_INCLUDE<br>
     * 出力内容=文字列「<input type="submit" name="[property]" 
     * accesskey="[accesskey]" tabindex="[tabindex]" 
     * value="[label]" [EventHandlers] [Styles] >」<br>
     * 
     * 指定されたサブミットタグのボタンラベルが空白文字列のため、
     * ラベルには文字列”submit”を使用し、
     * サブミットボタンタグの文字列を生成し、
     * ページに関連付けられたライタに書き込む場合のテストクラス<br>
     * indexed=falseの場合、nameの値が配列型で出力されないことの確認<br>
     */
    public void testDoEndTag05() throws Exception {

        //テストデータ設定
        PageContext pc = TagUTUtil.getPageContext(tag);
        UTUtil.setPrivateField(tag, "value", "");
        UTUtil.setPrivateField(tag, "text", "CLICK!!");
        UTUtil.setPrivateField(tag, "target", null);
        pc.setAttribute("frameScript", "frameScript");
        UTUtil.setPrivateField(tag, "accesskey", null);
        UTUtil.setPrivateField(tag, "tabindex", null);
        UTUtil.setPrivateField(tag, "property", "submit");

        ActionMapping mapping = new ActionMapping();
        mapping.setName("MAPPING_TEST_FORM");
        MockHttpServletRequest req = (MockHttpServletRequest) pc.getRequest();
        req.setAttribute(Globals.MAPPING_KEY, mapping);

        //テスト実行
        int result = tag.doEndTag();

        //テスト結果確認
        assertEquals(Tag.EVAL_PAGE, result);
        BufferedReader reader = TagUTUtil.getOutputReader(tag);
        assertEquals(
            "<input type=\"submit\" name=\"submit\" " + "value=\"Submit\">",
            reader.readLine());

    } /* testDoEndTag05 End */

    /**
     * testDoEndTag06。<br>
     * 
     * (異常系)<br>
     * 観点：G<br>
     * 
     * 入力値
     * value(label)=Null<br>
     * text=Null<br>
     * target=Not Null<br>
     * frameScript=*<br>
     * accesskey=*<br>
     * tabindex=*<br>
     * onClick=Null<br>
     * property= Not Null<br>
     * 
     * 期待値
     * 戻り値:int=NullPointerException<br>
     * 出力内容=-<br>
     * 
     * ページコンテキストから”frameScript”取得時に
     * NullPointerExceptionが発生した場合のテストケース<br>
     */
    public void testDoEndTag06() throws Exception {

        //テストデータ設定
        PageContext pc = TagUTUtil.getPageContext(tag);
        UTUtil.setPrivateField(tag, "value", null);
        UTUtil.setPrivateField(tag, "text", null);
        UTUtil.setPrivateField(tag, "target", "target");
        UTUtil.setPrivateField(tag, "accesskey", null);
        UTUtil.setPrivateField(tag, "tabindex", null);
        UTUtil.setPrivateField(tag, "property", "submit");

        ActionMapping mapping = new ActionMapping();
        mapping.setName("MAPPING_TEST_FORM");
        MockHttpServletRequest req = (MockHttpServletRequest) pc.getRequest();
        req.setAttribute(Globals.MAPPING_KEY, mapping);

        Exception_PageContextImpl pc2 =
            new Exception_PageContextImpl(
                pc.getServletConfig(),
                pc.getRequest(),
                pc.getResponse());
        pc2.setNullPointerEx();
        //pc2.setIllegalArgumentEx();
        pc2.setTiming(1);
        tag.setPageContext(pc2);

        //テスト実行
        try {
            tag.doEndTag();
            fail();
        } catch (NullPointerException ex) {
            //テスト結果確認
            assertEquals(NullPointerException.class.getName(), ex.getClass()
                    .getName());
            return;
        }
    } /* testDoEndTag06 End */

    /**
     * testDoEndTag07。<br>
     * 
     * (異常系)<br>
     * 観点：G<br>
     * 
     * 入力値
     * value(label)=Null<br>
     * text=Null<br>
     * target=Not Null<br>
     * frameScript=*<br>
     * accesskey=*<br>
     * tabindex=*<br>
     * onClick=Null<br>
     * property= Not Null<br>
     * 
     * 期待値
     * 戻り値:int=IllegalArgumentException<br>
     * 出力内容=-<br>
     * 
     * ページコンテキストから”frameScript”取得時に
     * IllegalArgumentExceptionが発生した場合のテストケース<br>
     */
    public void testDoEndTag07() throws Exception {

        //テストデータ設定
        PageContext pc = TagUTUtil.getPageContext(tag);
        UTUtil.setPrivateField(tag, "value", null);
        UTUtil.setPrivateField(tag, "text", null);
        UTUtil.setPrivateField(tag, "target", "target");
        UTUtil.setPrivateField(tag, "accesskey", null);
        UTUtil.setPrivateField(tag, "tabindex", null);
        UTUtil.setPrivateField(tag, "property", "submit");

        ActionMapping mapping = new ActionMapping();
        mapping.setName("MAPPING_TEST_FORM");
        MockHttpServletRequest req = (MockHttpServletRequest) pc.getRequest();
        req.setAttribute(Globals.MAPPING_KEY, mapping);

        Exception_PageContextImpl pc2 =
            new Exception_PageContextImpl(
                pc.getServletConfig(),
                pc.getRequest(),
                pc.getResponse());
        //pc2.setNullPointerEx();
        pc2.setIllegalArgumentEx();
        pc2.setTiming(1);
        tag.setPageContext(pc2);

        //テスト実行
        try {
            tag.doEndTag();
            fail();
        } catch (IllegalArgumentException ex) {

            //テスト結果確認
            assertEquals(IllegalArgumentException.class.getName(), ex
                    .getClass().getName());
            return;
        }
    } /* testDoEndTag07 End */

    /**
     * testDoEndTag08。<br>
     * 
     * (異常系)<br>
     * 観点：G<br>
     * 
     * 入力値
     * value(label)=Null<br>
     * text="text"<br>
     * target=Not Null<br>
     * frameScript=Null<br>
     * accesskey=Not Null<br>
     * tabindex=Not Null<br>
     * onClick=Null<br>
     * property= Not Null<br>
     * 
     * 期待値
     * 戻り値:int=NullPointerException<br>
     * 出力内容=-<br>
     * 
     * ページコンテキストへ”frameScript”のセット時に
     * NullPointerExceptionが発生した場合のテストケース<br>
     */
    public void testDoEndTag08() throws Exception {

        //テストデータ設定
        PageContext pc = TagUTUtil.getPageContext(tag);
        UTUtil.setPrivateField(tag, "value", null);
        UTUtil.setPrivateField(tag, "text", null);
        UTUtil.setPrivateField(tag, "target", "target");
        UTUtil.setPrivateField(tag, "accesskey", null);
        UTUtil.setPrivateField(tag, "tabindex", null);
        UTUtil.setPrivateField(tag, "property", "submit");

        ActionMapping mapping = new ActionMapping();
        mapping.setName("MAPPING_TEST_FORM");
        MockHttpServletRequest req = (MockHttpServletRequest) pc.getRequest();
        req.setAttribute(Globals.MAPPING_KEY, mapping);

        Exception_PageContextImpl pc2 =
            new Exception_PageContextImpl(
                pc.getServletConfig(),
                pc.getRequest(),
                pc.getResponse());
        pc2.setNullPointerEx();
        pc2.setTiming(2);
        tag.setPageContext(pc2);

        //テスト実行
        try {
            tag.doEndTag();
            fail();
        } catch (NullPointerException ex) {

            //テスト結果確認
            assertEquals(NullPointerException.class.getName(), ex.getClass()
                    .getName());
            return;
        }
    } /* testDoEndTag08 End */

    /**
     * testDoEndTag09。<br>
     * 
     * (異常系)<br>
     * 観点：G<br>
     * 
     * 入力値
     * value(label)=Null<br>
     * text="text"<br>
     * target=Not Null<br>
     * frameScript=Null<br>
     * accesskey=Not Null<br>
     * tabindex=Not Null<br>
     * onClick=Null<br>
     * property= Not Null<br>
     * 
     * 期待値
     * 戻り値:int=IllegalArgumentException<br>
     * 出力内容=-<br>
     * 
     * ページコンテキストへ”frameScript”のセット時に
     * IllegalArgumentExceptionが発生した場合のテストケース<br>
     */
    public void testDoEndTag09() throws Exception {

        //テストデータ設定
        PageContext pc = TagUTUtil.getPageContext(tag);
        UTUtil.setPrivateField(tag, "value", null);
        UTUtil.setPrivateField(tag, "text", null);
        UTUtil.setPrivateField(tag, "target", "target");
        UTUtil.setPrivateField(tag, "accesskey", null);
        UTUtil.setPrivateField(tag, "tabindex", null);
        UTUtil.setPrivateField(tag, "property", "submit");

        ActionMapping mapping = new ActionMapping();
        mapping.setName("MAPPING_TEST_FORM");
        MockHttpServletRequest req = (MockHttpServletRequest) pc.getRequest();
        req.setAttribute(Globals.MAPPING_KEY, mapping);

        Exception_PageContextImpl pc2 =
            new Exception_PageContextImpl(
                pc.getServletConfig(),
                pc.getRequest(),
                pc.getResponse());
        pc2.setIllegalArgumentEx();
        pc2.setTiming(2);
        tag.setPageContext(pc2);

        //テスト実行
        try {
            tag.doEndTag();
            fail();
        } catch (IllegalArgumentException ex) {

            //テスト結果確認
            assertEquals(IllegalArgumentException.class.getName(), ex
                    .getClass().getName());
            return;
        }
    } /* testDoEndTag09 End */

    /**
     * testDoEndTag10。<br>
     * 
     * (異常系)<br>
     * 観点：G<br>
     * 
     * 入力値
     * value(label)=Null<br>
     * text=Null<br>
     * target=Null<br>
     * frameScript=*<br>
     * accesskey=Null<br>
     * tabindex=Null<br>
     * onClick=Null<br>
     * property= Not Null<br>
     * 
     * 期待値
     * 戻り値:int=JapException<br>
     * 出力内容=-<br>
     * 
     * ライタに書き込むときにExceptionが発生した場合のテストケース<br>
     */
    public void testDoEndTag10() throws Exception {

        //テストデータ設定
        PageContext pc = TagUTUtil.getPageContext(tag);
        UTUtil.setPrivateField(tag, "value", null);
        UTUtil.setPrivateField(tag, "text", null);
        UTUtil.setPrivateField(tag, "target", null);
        UTUtil.setPrivateField(tag, "accesskey", null);
        UTUtil.setPrivateField(tag, "tabindex", null);
        UTUtil.setPrivateField(tag, "property", "submit");

        ActionMapping mapping = new ActionMapping();
        mapping.setName("MAPPING_TEST_FORM");
        MockHttpServletRequest req = (MockHttpServletRequest) pc.getRequest();
        req.setAttribute(Globals.MAPPING_KEY, mapping);

        Exception_JspWriterImpl out = new Exception_JspWriterImpl();
        out.setTrue();
        out.setTiming(1);
        UTUtil.setPrivateField(pc, "jspWriter", out);

        //テスト実行
        try {
            tag.doEndTag();
            fail();
        } catch (JspException ex) {

            //テスト結果確認
            assertEquals(JspException.class.getName(), ex.getClass().getName());
            assertEquals("入出力エラー: java.io.IOException", ex.getMessage());
            return;
        }
    } /* testDoEndTag10 End */

    /**
     * testDoEndTag11。<br>
     * 
     * (正常系)<br>
     * 観点：F<br>
     * 
     * 入力値
     * value(label)=Null<br>
     * text="text"<br>
     * target=Not Null<br>
     * frameScript=Null<br>
     * accesskey=Not Null<br>
     * tabindex=Not Null<br>
     * onClick=Null<br>
     * property= Null<br>
     * 
     * 期待値
     * 戻り値:int=EVAL_BODY_INCLUDE<br>
     * 
     * サブミットタグのボタンラベルにスーパークラスの”text”を使用、
     * ターゲットの指定処理を行い、スーパークラスの”accesskey”および
     * ”tabindex”の指定を行い、ページに関連付けられたライタに
     * 書き込む場合のテストクラス<br>
     * property= Nullの場合は property属性を出力しない。<br>
     */
    public void testDoEndTag11() throws Exception {

        //テストデータ設定
        PageContext pc = TagUTUtil.getPageContext(tag);
        UTUtil.setPrivateField(tag, "value", null);
        UTUtil.setPrivateField(tag, "text", "text");
        UTUtil.setPrivateField(tag, "target", "target");
        UTUtil.setPrivateField(tag, "accesskey", "accesskey");
        UTUtil.setPrivateField(tag, "tabindex", "tabindex");
        UTUtil.setPrivateField(tag, "property", null);

        ActionMapping mapping = new ActionMapping();
        mapping.setName("MAPPING_TEST_FORM");
        MockHttpServletRequest req = (MockHttpServletRequest) pc.getRequest();
        req.setAttribute(Globals.MAPPING_KEY, mapping);

        //テスト実行
        int result = tag.doEndTag();

        //テスト結果確認
        assertEquals(Tag.EVAL_PAGE, result);
        BufferedReader reader = TagUTUtil.getOutputReader(tag);
        assertEquals("", reader.readLine());
        assertEquals("<script type=\"text/javascript\">", reader.readLine());
        assertEquals("<!--", reader.readLine());
        assertEquals(
            "  function __setFrameTarget(__frTarget) {",
            reader.readLine());
        assertEquals(
            "    document.MAPPING_TEST_FORM.target = __frTarget;",
            reader.readLine());
        assertEquals("  }", reader.readLine());
        assertEquals("//-->", reader.readLine());
        assertEquals("</script>", reader.readLine());
        assertEquals("", reader.readLine());
        assertEquals(
            "<input type=\"submit\" "
                + "accesskey=\"accesskey\" "
                + "tabindex=\"tabindex\" value=\"text\" "
                + "onclick=\"__setFrameTarget('target')\">",
            reader.readLine());
        assertEquals("frameScript", (String) pc.getAttribute("frameScript"));
        //property属性"name="を出力しない。    
        reader = TagUTUtil.getOutputReader(tag);
        assertEquals(-1, reader.readLine().indexOf("name="));

    } /* testDoEndTag11 End */

    /**
     * testRelease01。<br>
     * 
     * (正常系)<br>
     * 観点：C<br>
     * 
     * 入力値
     * target=*<br>
     * 
     * 期待値
     * 戻り値:void<br>
     * target=Null<br>
     * 
     * 前提条件として設定した各値が、
     * 実行時に各条件値が初期化されることを確認する<br>
     */
    public void testRelease01() throws Exception {

        //テストデータ設定
        UTUtil.setPrivateField(tag, "target", "Jesus!!");

        //テスト実行
        tag.release();

        //テスト結果確認
        assertNull(UTUtil.getPrivateField(tag, "target"));

    } /* testRelease01 End */

    /**
     * testSetTarget01。<br>
     * 
     * (正常系)<br>
     * 観点：A<br>
     * 
     * 入力値
     * target="("target");"<br>
     * 
     * 期待値
     * 戻り値:void<br>
     * target="("target");"<br>
     * 
     * セットした値を確認するテストケース<br>
     */
    public void testSetTarget01() throws Exception {
        //テスト実行
        tag.setTarget("target");

        //テスト結果確認
        assertEquals("target", UTUtil.getPrivateField(tag, "target"));

    } /* testSetTarget01 End */

    /**
     * testGetTarget01。<br>
     * 
     * (正常系)<br>
     * 観点：A<br>
     * 
     * 入力値
     * target="("target");"<br>
     * 
     * 期待値
     * 戻り値:String="("target");"<br>
     * 
     * ゲットした値を確認するテストケース<br>
     */
    public void testGetTarget01() throws Exception {
        //テスト設定
        String value = "target";
        UTUtil.setPrivateField(tag, "target", value);

        //テスト実行
        String result = tag.getTarget();

        //テスト結果確認
        assertEquals(value, result);

    } /* testGetTarget01 End */

} /* SubmitTagTest Class End */
