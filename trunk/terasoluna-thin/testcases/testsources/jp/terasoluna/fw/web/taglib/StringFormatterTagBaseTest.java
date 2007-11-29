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

package jp.terasoluna.fw.web.taglib;


import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;

import jp.terasoluna.utlib.TagUTUtil;
import jp.terasoluna.utlib.UTUtil;
import jp.terasoluna.utlib.exception.Exception_JspWriterImpl;
import jp.terasoluna.utlib.exception.Exception_PageContextImpl;
import junit.framework.TestCase;

/**
 * StringFormatterTagBase ブラックボックステスト。<br>
 * 
 */
public class StringFormatterTagBaseTest extends TestCase {

    //テスト対象
    StringFormatterTagBaseImpl01 tag = null;

    /**
     * Constructor for StringFormatterTagBaseTest.
     * @param arg0
     */
    public StringFormatterTagBaseTest(String arg0) {
        super(arg0);
    }

    /*
     * @see TestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        tag =
            ( StringFormatterTagBaseImpl01) TagUTUtil.create(
                 StringFormatterTagBaseImpl01.class);
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
     * value=Null<br>
     * ignore=true<br>
     * name=Not Null<br>
     * property=*<br>
     * scope=正常値<br>
     * bean=null<br>
     * valu2=*<br>
     * id=*<br>
     * filter=*<br>
     * output=*<br>
     * 
     * 期待値
     * 戻り値:int=SKIP_BODY<br>
     * pageContextのid=-<br>
     * output=-<br>
     * 
     * 取得した「Bean」がNULLの場合のテストケース<br>
     */
    public void testDoStartTag01() throws Exception {

        //テストデータ設定
        UTUtil.setPrivateField(tag, "value", null);
        UTUtil.setPrivateField(tag, "ignore", new Boolean(true));
        UTUtil.setPrivateField(tag, "name", "NoBeanKey");
        UTUtil.setPrivateField(tag, "property", "testField");
        UTUtil.setPrivateField(tag, "scope", "page");
        UTUtil.setPrivateField(tag, "id", "id");
        UTUtil.setPrivateField(tag, "filter", new Boolean(true));

        StringFormatterTagBase_BeanStub01 bean = new StringFormatterTagBase_BeanStub01();
        bean.setTestField("testtesttest");

        PageContext pc = TagUTUtil.getPageContext(tag);
        pc.setAttribute("BeanKey", bean);

        //テスト実行
        int result = tag.doStartTag();

        //テスト結果確認
        assertEquals( Tag.SKIP_BODY, result);

    } /* testDoStartTag01 End */

    /**
     * testDoStartTag02。<br>
     * 
     * (正常系)<br>
     * 観点：F<br>
     * 
     * 入力値
     * value=Null<br>
     * ignore=true<br>
     * name=Not Null<br>
     * property=Not Null<br>
     * scope=正常値<br>
     * bean=Not Null<br>
     * valu2=Null<br>
     * id=*<br>
     * filter=*<br>
     * output=*<br>
     * 
     * 期待値
     * 戻り値:int=SKIP_BODY<br>
     * pageContextのid=-<br>
     * output=-<br>
     * 
     * 取得した「value2」がNULLの場合のテストケース<br>
     */
    public void testDoStartTag02() throws Exception {

        //テストデータ設定
        UTUtil.setPrivateField(tag, "value", null);
        UTUtil.setPrivateField(tag, "ignore", new Boolean(true));
        UTUtil.setPrivateField(tag, "name", "BeanKey");
        UTUtil.setPrivateField(tag, "property", "testField");
        UTUtil.setPrivateField(tag, "scope", "page");
        UTUtil.setPrivateField(tag, "id", "id");
        UTUtil.setPrivateField(tag, "filter", new Boolean(true));

        StringFormatterTagBase_BeanStub01 bean = new StringFormatterTagBase_BeanStub01();
        bean.setTestField(null);

        PageContext pc = TagUTUtil.getPageContext(tag);
        pc.setAttribute("BeanKey", bean);

        //テスト実行
        int result = tag.doStartTag();

        //テスト結果確認
        assertEquals( Tag.SKIP_BODY, result);

    } /* testDoStartTag02 End */

    /**
     * testDoStartTag03。<br>
     * 
     * (正常系)<br>
     * 観点：F<br>
     * 
     * 入力値
     * value=Null<br>
     * ignore=false<br>
     * name=Not Null<br>
     * property=Not Null<br>
     * scope=正常値<br>
     * bean=Not Null<br>
     * valu2=Not Null<br>
     * id=Not Null<br>
     * filter=*<br>
     * output="testtesttest"<br>
     * 
     * 期待値
     * 戻り値:int=SKIP_BODY<br>
     * pageContextのid="testtesttest"<br>
     * output=-<br>
     * 
     * StringFormatterTagBaseクラス変数の「id」をキーに
     * ページコンテキストにフォーマット後の値がセットされる場合のテストケース<br>
     */
    public void testDoStartTag03() throws Exception {

        //テストデータ設定
        UTUtil.setPrivateField(tag, "value", null);
        UTUtil.setPrivateField(tag, "ignore", new Boolean(false));
        UTUtil.setPrivateField(tag, "name", "BeanKey");
        UTUtil.setPrivateField(tag, "property", "testField");
        UTUtil.setPrivateField(tag, "scope", "page");
        UTUtil.setPrivateField(tag, "id", "id");
        UTUtil.setPrivateField(tag, "filter", new Boolean(true));

        String testValue = "testtesttest";

        StringFormatterTagBase_BeanStub01 bean = new StringFormatterTagBase_BeanStub01();
        bean.setTestField("testtesttest");

        PageContext pc = TagUTUtil.getPageContext(tag);
        pc.setAttribute("BeanKey", bean);

        //テスト実行
        int result = tag.doStartTag();

        //テスト結果確認
        assertEquals(Tag.SKIP_BODY, result);
        assertEquals(testValue, pc.getAttribute("id"));

    } /* testDoStartTag03 End */

    /**
     * testDoStartTag04。<br>
     * 
     * (正常系)<br>
     * 観点：F<br>
     * 
     * 入力値
     * value=Not Null<br>
     * ignore=*<br>
     * name=*<br>
     * property=*<br>
     * scope=*<br>
     * bean=*<br>
     * valu2=*<br>
     * id=Null<br>
     * filter=true<br>
     * output="test<test>test"<br>
     * 
     * 期待値
     * 戻り値:int=SKIP_BODY<br>
     * pageContextのid=-<br>
     * output="test&lt;test&gt;test"<br>
     * 
     * StringFormatterTagBaseクラス変数の「id」が指定されていないため、
     * フォーマット後の値をフィルターをかけ、ページに関連付けられたライタに
     * 書き込む場合のテストケース<br>
     */
    public void testDoStartTag04() throws Exception {

        //テストデータ設定
        String testValue = "test<test>test";
        UTUtil.setPrivateField(tag, "value", testValue);
        UTUtil.setPrivateField(tag, "id", null);
        UTUtil.setPrivateField(tag, "filter", new Boolean(true));

        //テスト実行
        int result = tag.doStartTag();

        //テスト結果確認
        String reader = TagUTUtil.getOutputString(tag);
        assertEquals( Tag.SKIP_BODY, result);
        assertEquals("test&lt;test&gt;test", reader);

    } /* testDoStartTag04 End */

    /**
     * testDoStartTag05。<br>
     * 
     * (正常系)<br>
     * 観点：F<br>
     * 
     * 入力値
     * value=Not Null<br>
     * ignore=*<br>
     * name=*<br>
     * property=*<br>
     * scope=*<br>
     * bean=*<br>
     * valu2=*<br>
     * id=Null<br>
     * filter=false<br>
     * output="test<test>test"<br>
     * 
     * 期待値
     * 戻り値:int=SKIP_BODY<br>
     * pageContextのid=-<br>
     * output="test<test>test"<br>
     * 
     * StringFormatterTagBaseクラス変数の「id」が指定されていないため、
     * フォーマット後の値をフィルターをかけずに、ページに関連付けられたライタに
     * 書き込む場合のテストケース<br>
     */
    public void testDoStartTag05() throws Exception {

        //テストデータ設定
        String testValue = "test<test>test";
        UTUtil.setPrivateField(tag, "value", testValue);
        UTUtil.setPrivateField(tag, "id", null);
        UTUtil.setPrivateField(tag, "filter", new Boolean(false));

        //テスト実行
        int result = tag.doStartTag();

        //テスト結果確認
        String reader = TagUTUtil.getOutputString(tag);
        assertEquals( Tag.SKIP_BODY, result);
        assertEquals(testValue, reader);

    } /* testDoStartTag05 End */

    /**
     * testDoStartTag06。<br>
     * 
     * (異常系)<br>
     * 観点：G<br>
     * 
     * 入力値
     * value=Null<br>
     * ignore=true<br>
     * name=Not Null<br>
     * property=Not Null<br>
     * scope=異常値<br>
     * bean=*<br>
     * valu2=*<br>
     * id=*<br>
     * filter=*<br>
     * output=*<br>
     * 
     * 期待値
     * 戻り値:int=JspException<br>
     * pageContextのid=-<br>
     * output=-<br>
     * 
     * 「Bean」取得時にJspExceptionが発生した場合のテストケース<br>
     */
    public void testDoStartTag06() throws Exception {

        //テストデータ設定
        UTUtil.setPrivateField(tag, "value", null);
        UTUtil.setPrivateField(tag, "ignore", new Boolean(true));
        UTUtil.setPrivateField(tag, "name", "NoBeanKey");
        UTUtil.setPrivateField(tag, "property", "testField");
        UTUtil.setPrivateField(tag, "scope", "Not Scope");
        UTUtil.setPrivateField(tag, "id", "id");
        UTUtil.setPrivateField(tag, "filter", new Boolean(true));
        
        StringFormatterTagBase_BeanStub01 bean = new StringFormatterTagBase_BeanStub01();
        
        bean.setTestField("testtesttest");

        PageContext pc = TagUTUtil.getPageContext(tag);
        pc.setAttribute("BeanKey", bean);

        //テスト実行
        try {
            tag.doStartTag();
            fail();
        } catch (JspException ex) {
            //テスト結果確認
            assertEquals(JspException.class.getName(), ex.getClass().getName());
            return;
        }
    } /* testDoStartTag06 End */

    /**
     * testDoStartTag07。<br>
     * 
     * (異常系)<br>
     * 観点：G<br>
     * 
     * 入力値
     * value=Null<br>
     * ignore=false<br>
     * name=Not Null<br>
     * property=想定しうる文字列<br>
     * scope=正常値<br>
     * bean=*<br>
     * valu2=*<br>
     * id=*<br>
     * filter=*<br>
     * output=*<br>
     * 
     * 期待値
     * 戻り値:int=JspException<br>
     * pageContextのid=-<br>
     * output=-<br>
     * 
     * 「value2」取得時にJspExceptionが発生した場合のテストケース<br>
     */
    public void testDoStartTag07() throws Exception {

        //テストデータ設定
        UTUtil.setPrivateField(tag, "value", null);
        UTUtil.setPrivateField(tag, "ignore", new Boolean(false));
        UTUtil.setPrivateField(tag, "name", "BeanKey");
        UTUtil.setPrivateField(tag, "property", "NotestField");
        UTUtil.setPrivateField(tag, "scope", "page");
        UTUtil.setPrivateField(tag, "id", "id");
        UTUtil.setPrivateField(tag, "filter", new Boolean(true));

        StringFormatterTagBase_BeanStub01 bean = new StringFormatterTagBase_BeanStub01();
        
        bean.setTestField("testtesttest");

        PageContext pc = TagUTUtil.getPageContext(tag);
        pc.setAttribute("BeanKey", bean);

        //テスト実行
        try {
            tag.doStartTag();
            fail();
        } catch (JspException ex) {
            //テスト結果確認
            assertEquals("Unknown property \'NotestField\'", ex.getMessage());
            return;
        }
    } /* testDoStartTag07 End */

    /**
     * testDoStartTag08。<br>
     * 
     * (異常系)<br>
     * 観点：G<br>
     * 
     * 入力値
     * value=Not Null<br>
     * ignore=*<br>
     * name=*<br>
     * property=*<br>
     * scope=*<br>
     * bean=*<br>
     * valu2=*<br>
     * id=Null<br>
     * filter=true<br>
     * output=" output"<br>
     * 
     * 期待値
     * 戻り値:int=JspException<br>
     * pageContextのid=-<br>
     * output=-<br>
     * 
     * フィルター済みの値をページに関連付けられたライタに
     * 書き込むときにJspExceptionが発生した場合のテストケース<br>
     */
    public void testDoStartTag08() throws Exception {

        //テストデータ設定
        String testValue = "test<test>test";
        UTUtil.setPrivateField(tag, "value", testValue);
        UTUtil.setPrivateField(tag, "id", null);
        UTUtil.setPrivateField(tag, "filter", new Boolean(true));

        PageContext pc = TagUTUtil.getPageContext(tag);
        Exception_JspWriterImpl out = new Exception_JspWriterImpl();
        out.setTrue();
        out.setTiming(1);
        UTUtil.setPrivateField(pc, "jspWriter", out);

        //テスト実行
        try {
            tag.doStartTag();
            fail();
        } catch (JspException ex) {
            //テスト結果確認
            assertEquals(IOException.class.getName(), ex.getRootCause()
                    .getClass().getName());
            return;
        }
    } /* testDoStartTag08 End */

    /**
     * testDoStartTag09。<br>
     * 
     * (異常系)<br>
     * 観点：G<br>
     * 
     * 入力値
     * value=Not Null<br>
     * ignore=*<br>
     * name=*<br>
     * property=*<br>
     * scope=*<br>
     * bean=*<br>
     * valu2=*<br>
     * id=Null<br>
     * filter=false<br>
     * output=" output"<br>
     * 
     * 期待値
     * 戻り値:int=JspException<br>
     * pageContextのid=-<br>
     * output=-<br>
     * 
     * 値をページに関連付けられたライタに書き込むときに
     * JspExceptionが発生した場合のテストケース<br>
     */
    public void testDoStartTag09() throws Exception {

        //テストデータ設定
        String testValue = "test<test>test";
        UTUtil.setPrivateField(tag, "value", testValue);
        UTUtil.setPrivateField(tag, "id", null);
        UTUtil.setPrivateField(tag, "filter", new Boolean(false));

        PageContext pc = TagUTUtil.getPageContext(tag);
        Exception_JspWriterImpl out = new Exception_JspWriterImpl();
        out.setTrue();
        out.setTiming(1);
        UTUtil.setPrivateField(pc, "jspWriter", out);

        //テスト実行
        try {
            tag.doStartTag();
            fail();
        } catch (JspException ex) {
            //テスト結果確認
            assertEquals(IOException.class.getName(), ex.getRootCause()
                    .getClass().getName());
            return;
        }
    } /* testDoStartTag09 End */

    /**
     * testDoStartTag10。<br>
     * 
     * (異常系)<br>
     * 観点：G<br>
     * 
     * 入力値
     * value=Null<br>
     * ignore=false<br>
     * name=Not Null<br>
     * property=Not Null<br>
     * scope=正常値<br>
     * bean=*<br>
     * valu2=Not Null<br>
     * id=Not Null<br>
     * filter=*<br>
     * output="output"<br>
     * 
     * 期待値
     * 戻り値:int=NullPointerException<br>
     * pageContextのid=-<br>
     * output=-<br>
     * 
     * ページコンテキストにセットする時に
     * NullPointerExceptionが発生した場合<br>
     */
    public void testDoStartTag10() throws Exception {

        //テストデータ設定
        UTUtil.setPrivateField(tag, "value", "value");
        UTUtil.setPrivateField(tag, "id", "id");
        UTUtil.setPrivateField(tag, "filter", new Boolean(true));

        PageContext pc = TagUTUtil.getPageContext(tag);
        Exception_PageContextImpl pc2 =
            new Exception_PageContextImpl(
                pc.getServletConfig(),
                pc.getRequest(),
                pc.getResponse());
        pc2.setNullPointerEx();
        pc2.setTiming(1);
        tag.setPageContext(pc2);

        //テスト実行
        try {
            tag.doStartTag();
            fail();
        } catch (NullPointerException ex) {

            //テスト結果確認
            assertEquals(NullPointerException.class.getName(), ex.getClass()
                    .getName());
            return;
        }
    } /* testDoStartTag10 End */

    /**
     * testDoStartTag11。<br>
     * 
     * (異常系)<br>
     * 観点：G<br>
     * 
     * 入力値
     * value=Null<br>
     * ignore=false<br>
     * name=Not Null<br>
     * property=Not Null<br>
     * scope=正常値<br>
     * bean=*<br>
     * valu2=Not Null<br>
     * id=Not Null<br>
     * filter=*<br>
     * output="output"<br>
     * 
     * 期待値
     * 戻り値:int=IllegalArgumentException<br>
     * pageContextのid=-<br>
     * output=-<br>
     * 
     * ページコンテキストにセットする時に
     * IllegalArgumentExceptionが発生した場合<br>
     */
    public void testDoStartTag11() throws Exception {

        //テストデータ設定
        UTUtil.setPrivateField(tag, "value", "value");
        UTUtil.setPrivateField(tag, "id", "id");
        UTUtil.setPrivateField(tag, "filter", new Boolean(true));

        PageContext pc = TagUTUtil.getPageContext(tag);
        Exception_PageContextImpl pc2 =
            new Exception_PageContextImpl(
                pc.getServletConfig(),
                pc.getRequest(),
                pc.getResponse());
        pc2.setIllegalArgumentEx();
        pc2.setTiming(1);
        tag.setPageContext(pc2);

        //テスト実行
        try {
            tag.doStartTag();
            fail();
        } catch (IllegalArgumentException ex) {

            //テスト結果確認
            assertEquals(IllegalArgumentException.class.getName(),
                    ex.getClass().getName());
            return;
        }
    } /* testDoStartTag11 End */

    /**
     * testDoStartTag12。<br>
     * 
     * (正常系)<br>
     * 観点：F<br>
     * 
     * 入力値
     * value=Null<br>
     * ignore=false<br>
     * name=Not Null<br>
     * property=Not Null<br>
     * scope=正常値<br>
     * bean=Not Null<br>
     * valu2=Not Null<br>
     * id=Null<br>
     * filter=*<br>
     * output="test  TEST"(半角スペース×2)<br>
     * replaceSpToNbsp=true<br>
     * 
     * 期待値
     * 戻り値:int=SKIP_BODY<br>
     * pageContextのid="test&nbsp;&nbsp;TEST"<br>
     * output=-<br>
     * 
     * 半角スペースを含む文字列で、StringFormatterTagBaseクラス変数の「id」をキーに
     * ページコンテキストにフォーマット後の値がセットされる場合のテストケース
     * (replaceSpToNbspがtrueなので半角スペースが置換されている)<br>
     */
    public void testDoStartTag12() throws Exception {

        //テストデータ設定
        UTUtil.setPrivateField(tag, "value", null);
        UTUtil.setPrivateField(tag, "ignore", new Boolean(false));
        UTUtil.setPrivateField(tag, "name", "BeanKey");
        UTUtil.setPrivateField(tag, "property", "testField");
        UTUtil.setPrivateField(tag, "scope", "page");
        UTUtil.setPrivateField(tag, "id", null);
        UTUtil.setPrivateField(tag, "filter", new Boolean(false));
        UTUtil.setPrivateField(tag, "replaceSpToNbsp", new Boolean(true));

        String testValue = "test  TEST";

        StringFormatterTagBase_BeanStub01 bean = new StringFormatterTagBase_BeanStub01();
        
        bean.setTestField(testValue);

        PageContext pc = TagUTUtil.getPageContext(tag);
        pc.setAttribute("BeanKey", bean);
        //テスト実行
        int result = tag.doStartTag();

        //テスト結果確認
        assertEquals( Tag.SKIP_BODY, result);
        String reader = TagUTUtil.getOutputString(tag);
        assertEquals("test&nbsp;&nbsp;TEST", reader);
        
    } /* testDoStartTag12 End */

    /**
     * testDoStartTag13。<br>
     * 
     * (正常系)<br>
     * 観点：F<br>
     * 
     * 入力値
     * value=Null<br>
     * ignore=false<br>
     * name=Not Null<br>
     * property=Not Null<br>
     * scope=正常値<br>
     * bean=Not Null<br>
     * valu2=Not Null<br>
     * id=Null<br>
     * filter=*<br>
     * output="test　　TEST"(全角スペース×2)<br>
     * replaceSpToNbsp=true<br>
     * 
     * 期待値
     * 戻り値:int=SKIP_BODY<br>
     * pageContextのid="testTEST"<br>
     * output=-<br>
     * 
     * 全角スペースを含む文字列で、StringFormatterTagBaseクラス変数の「id」をキーに
     * ページコンテキストにフォーマット後の値がセットされる場合のテストケース
     * (replaceSpToNbspがtrueだが全角スペースなので置換されてない)<br>
     */
    public void testDoStartTag13() throws Exception {

        //テストデータ設定
        UTUtil.setPrivateField(tag, "value", null);
        UTUtil.setPrivateField(tag, "ignore", new Boolean(false));
        UTUtil.setPrivateField(tag, "name", "BeanKey");
        UTUtil.setPrivateField(tag, "property", "testField");
        UTUtil.setPrivateField(tag, "scope", "page");
        UTUtil.setPrivateField(tag, "id", null);
        UTUtil.setPrivateField(tag, "filter", new Boolean(true));
        UTUtil.setPrivateField(tag, "replaceSpToNbsp", new Boolean(true));

        String testValue = "test　　TEST";

        StringFormatterTagBase_BeanStub01 bean = new StringFormatterTagBase_BeanStub01();
        
        bean.setTestField(testValue);

        PageContext pc = TagUTUtil.getPageContext(tag);
        pc.setAttribute("BeanKey", bean);

        //テスト実行
        int result = tag.doStartTag();

        //テスト結果確認
        assertEquals( Tag.SKIP_BODY, result);
        BufferedReader reader = TagUTUtil.getOutputReader(tag);
        assertEquals(testValue, reader.readLine());

    } /* testDoStartTag13 End */

    /**
     * testDoStartTag14。<br>
     * 
     * (正常系)<br>
     * 観点：F<br>
     * 
     * 入力値
     * value=Null<br>
     * ignore=false<br>
     * name=Not Null<br>
     * property=Not Null<br>
     * scope=正常値<br>
     * bean=Not Null<br>
     * valu2=Not Null<br>
     * id=Null<br>
     * filter=*<br>
     * output="test  TEST"(半角スペース×2)<br>
     * replaceSpToNbsp=false<br>
     * 
     * 期待値
     * 戻り値:int=SKIP_BODY<br>
     * pageContextのid="testTEST"<br>
     * output=-<br>
     * 
     * 半角スペースを含む文字列で、StringFormatterTagBaseクラス変数の「id」をキーに
     * ページコンテキストにフォーマット後の値がセットされる場合のテストケース
     * (replaceSpToNbspがfalseなので半角スペースが置換されてない)<br>
     */
    public void testDoStartTag14() throws Exception {

        //テストデータ設定
        UTUtil.setPrivateField(tag, "value", null);
        UTUtil.setPrivateField(tag, "ignore", new Boolean(false));
        UTUtil.setPrivateField(tag, "name", "BeanKey");
        UTUtil.setPrivateField(tag, "property", "testField");
        UTUtil.setPrivateField(tag, "scope", "page");
        UTUtil.setPrivateField(tag, "id", null);
        UTUtil.setPrivateField(tag, "filter", new Boolean(false));
        UTUtil.setPrivateField(tag, "replaceSpToNbsp", new Boolean(false));

        String testValue = "test  TEST";

        StringFormatterTagBase_BeanStub01 bean = new StringFormatterTagBase_BeanStub01();
        
        bean.setTestField(testValue);

        PageContext pc = TagUTUtil.getPageContext(tag);
        pc.setAttribute("BeanKey", bean);

        //テスト実行
        int result = tag.doStartTag();

        //テスト結果確認
        assertEquals( Tag.SKIP_BODY, result);
        BufferedReader reader = TagUTUtil.getOutputReader(tag);
        assertEquals(testValue, reader.readLine());

    } /* testDoStartTag14 End */

    /**
     * testDoStartTag15。<br>
     * 
     * (正常系)<br>
     * 観点：F<br>
     * 
     * 入力値
     * value=Null<br>
     * ignore=false<br>
     * name=Not Null<br>
     * property=Not Null<br>
     * scope=正常値<br>
     * bean=Not Null<br>
     * valu2=Not Null<br>
     * id=Not Null<br>
     * filter=*<br>
     * output="test  TEST"(半角スペース×2)<br>
     * replaceSpToNbsp=true<br>
     * 
     * 期待値
     * 戻り値:int=SKIP_BODY<br>
     * pageContextのid="testTEST"<br>
     * output=-<br>
     * 
     * 半角スペースを含む文字列で、StringFormatterTagBaseクラス変数の「id」をキーに
     * ページコンテキストにフォーマット後の値がセットされる場合のテストケース
     * (replaceSpToNbsp属性は無視される)<br>
     */
    public void testDoStartTag15() throws Exception {

        //テストデータ設定
        UTUtil.setPrivateField(tag, "value", null);
        UTUtil.setPrivateField(tag, "ignore", new Boolean(false));
        UTUtil.setPrivateField(tag, "name", "BeanKey");
        UTUtil.setPrivateField(tag, "property", "testField");
        UTUtil.setPrivateField(tag, "scope", "page");
        UTUtil.setPrivateField(tag, "id", "id");
        UTUtil.setPrivateField(tag, "filter", new Boolean(true));
        UTUtil.setPrivateField(tag, "replaceSpToNbsp", new Boolean(true));

        String testValue = "test  TEST";

        StringFormatterTagBase_BeanStub01 bean = new StringFormatterTagBase_BeanStub01();
        
        bean.setTestField(testValue);

        PageContext pc = TagUTUtil.getPageContext(tag);
        pc.setAttribute("BeanKey", bean);


        //テスト実行
        int result = tag.doStartTag();

        //テスト結果確認
        assertEquals( Tag.SKIP_BODY, result);
        assertEquals(testValue, pc.getAttribute("id"));

    } /* testDoStartTag15 End */
    
    /**
     * testRelease01。<br>
     * 
     * (正常系)<br>
     * 観点：C<br>
     * 
     * 入力値
     * id=*<br>
     * filter=false<br>
     * ignore=true<br>
     * name="name"<br>
     * property="property"<br>
     * scope="scope"<br>
     * value="value"<br>
     * replaceSpToNbsp=false<br>
     * 
     * 期待値
     * 戻り値:void<br>
     * id=Null<br>
     * filter=true<br>
     * ignore=false<br>
     * name=Null<br>
     * property=Null<br>
     * scope=Null<br>
     * value=Null<br>
     * replaceSpToNbsp=true<br>
     * 
     * 前提条件として設定した各値が、実行時に各条件値が初期化されることを確認する<br>
     */
    public void testRelease01() throws Exception {
        //テストデータ設定
        UTUtil.setPrivateField(tag, "id", "id");
        UTUtil.setPrivateField(tag, "filter", new Boolean(false));
        UTUtil.setPrivateField(tag, "ignore", new Boolean(true));
        UTUtil.setPrivateField(tag, "name", "name");
        UTUtil.setPrivateField(tag, "property", "property");
        UTUtil.setPrivateField(tag, "scope", "scope");
        UTUtil.setPrivateField(tag, "value", "value");
        UTUtil.setPrivateField(tag, "replaceSpToNbsp", new Boolean(false));

        //テスト実行
        tag.release();

        //テスト結果確認
        assertNull(UTUtil.getPrivateField(tag, "id"));
        assertTrue((Boolean) UTUtil.getPrivateField(tag, "filter"));
        assertFalse((Boolean) UTUtil.getPrivateField(tag, "ignore"));
        assertNull(UTUtil.getPrivateField(tag, "name"));
        assertNull(UTUtil.getPrivateField(tag, "property"));
        assertNull(UTUtil.getPrivateField(tag, "scope"));
        assertNull(UTUtil.getPrivateField(tag, "value"));
        assertTrue((Boolean) UTUtil.getPrivateField(tag, "replaceSpToNbsp"));

    } /* testRelease01 End */

    /**
     * testSetId01。<br>
     * 
     * (正常系)<br>
     * 観点：A<br>
     * 
     * 入力値
     * id="id"<br>
     * 
     * 期待値
     * 戻り値:void<br>
     * id="id"<br>
     * 
     * セットした値を確認するテストケース<br>
     */
    public void testSetId01() throws Exception {
        //テスト実行
        tag.setId("id");

        //テスト結果確認
        assertEquals("id", UTUtil.getPrivateField(tag, "id"));

    } /* testSetId01 End */
    /**
     * testSetFilter01。<br>
     * 
     * (正常系)<br>
     * 観点：A<br>
     * 
     * 入力値
     * filter=false<br>
     * 
     * 期待値
     * 戻り値:void<br>
     * filter=false<br>
     * 
     * セットした値を確認するテストケース<br>
     */
    public void testSetFilter01() throws Exception {
        //テスト実行
        tag.setFilter(false);

        //テスト結果確認
        assertFalse((Boolean) UTUtil.getPrivateField(tag, "filter"));

    } /* testSetFilter01 End */
    /**
     * testSetIgnore01。<br>
     * 
     * (正常系)<br>
     * 観点：A<br>
     * 
     * 入力値
     * ignore=true<br>
     * 
     * 期待値
     * 戻り値:void<br>
     * ignore=true<br>
     * 
     * セットした値を確認するテストケース<br>
     */
    public void testSetIgnore01() throws Exception {
        //テスト実行
        tag.setIgnore(true);

        //テスト結果確認
        assertTrue((Boolean) UTUtil.getPrivateField(tag, "ignore"));

    } /* testSetIgnore01 End */
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
     */
    public void testSetName01() throws Exception {
        //テスト実行
        tag.setName("name");

        //テスト結果確認
        assertEquals("name", UTUtil.getPrivateField(tag, "name"));

    } /* testSetNam01 End */
    /**
     * testSetProperty01。<br>
     * 
     * (正常系)<br>
     * 観点：A<br>
     * 
     * 入力値
     * property="property"<br>
     * 
     * 期待値
     * 戻り値:void<br>
     * property="property"<br>
     * 
     * セットした値を確認するテストケース<br>
     */
    public void testSetProperty01() throws Exception {
        //テスト実行
        tag.setProperty("property");

        //テスト結果確認
        assertEquals("property", UTUtil.getPrivateField(tag, "property"));

    } /* testSetProperty01 End */
    /**
     * testSetScope01。<br>
     * 
     * (正常系)<br>
     * 観点：A<br>
     * 
     * 入力値
     * scope="scope"<br>
     * 
     * 期待値
     * 戻り値:void<br>
     * scope="scope"<br>
     * 
     * セットした値を確認するテストケース<br>
     */
    public void testSetScope01() throws Exception {
        //テスト実行
        tag.setScope("scope");

        //テスト結果確認
        assertEquals("scope", UTUtil.getPrivateField(tag, "scope"));

    } /* testSetScope01 End */
    /**
     * testSetValue01。<br>
     * 
     * (正常系)<br>
     * 観点：A<br>
     * 
     * 入力値
     * value="value"<br>
     * 
     * 期待値
     * 戻り値:void<br>
     * value="value"<br>
     * 
     * セットした値を確認するテストケース<br>
     */
    public void testSetValue01() throws Exception {
        //テスト実行
        tag.setValue("value");

        //テスト結果確認
        assertEquals("value", UTUtil.getPrivateField(tag, "value"));

    } /* testSetValue01 End */
    
    /**
     * testSetReplaceSpToNbsp01。<br>
     * 
     * (正常系)<br>
     * 観点：A<br>
     * 
     * 入力値
     * value=false<br>
     * 
     * 期待値
     * 戻り値:void<br>
     * value=false<br>
     * 
     * セットした値を確認するテストケース<br>
     */
    public void testSetReplaceSpToNbsp01() throws Exception {
        //テスト実行
        tag.setReplaceSpToNbsp(false);

        assertFalse((Boolean) UTUtil.getPrivateField(tag, "replaceSpToNbsp"));
    } /* testSetReplaceSpToNbsp01 End */

    /**
     * testGetId01。<br>
     * 
     * (正常系)<br>
     * 観点：A<br>
     * 
     * 入力値
     * id="id"<br>
     * 
     * 期待値
     * 戻り値:String="id"<br>
     * 
     * ゲットした値を確認するテストケース<br>
     */
    public void testGetId01() throws Exception {
        //テスト設定
        String value = "id";
        UTUtil.setPrivateField(tag, "id", value);

        //テスト実行
        String result = tag.getId();

        //テスト結果確認
        assertEquals(value, result);

    } /* testGetId01 End */
    /**
     * testGetFilter01。<br>
     * 
     * (正常系)<br>
     * 観点：A<br>
     * 
     * 入力値
     * filter=false<br>
     * 
     * 期待値
     * 戻り値:Boolean=false<br>
     * 
     * ゲットした値を確認するテストケース<br>
     */
    public void testGetFilter01() throws Exception {
        //テスト設定
        boolean value = false;
        UTUtil.setPrivateField(tag, "filter", new Boolean(value));

        //テスト実行
        boolean result = tag.getFilter();

        //テスト結果確認
        assertFalse(result);

    } /* testGetFilter01 End */
    /**
     * testGetIgnore01。<br>
     * 
     * (正常系)<br>
     * 観点：A<br>
     * 
     * 入力値
     * ignore=true<br>
     * 
     * 期待値
     * 戻り値:Boolean=true<br>
     * 
     * ゲットした値を確認するテストケース<br>
     */
    public void testGetIgnore01() throws Exception {
        //テスト設定
        boolean value = true;
        UTUtil.setPrivateField(tag, "ignore", new Boolean(value));

        //テスト実行
        boolean result = tag.getIgnore();

        //テスト結果確認
        assertTrue(result);

    } /* testGetIgnore01 End */
    /**
     * testGetName01。<br>
     * 
     * (正常系)<br>
     * 観点：A<br>
     * 
     * 入力値
     * name="name"<br>
     * 
     * 期待値
     * 戻り値:String="name"<br>
     * 
     * ゲットした値を確認するテストケース<br>
     */
    public void testGetName01() throws Exception {
        //テスト設定
        String value = "name";
        UTUtil.setPrivateField(tag, "name", value);

        //テスト実行
        String result = tag.getName();

        //テスト結果確認
        assertEquals(value, result);

    } /* testGetName01 End */
    /**
     * testGetProperty01。<br>
     * 
     * (正常系)<br>
     * 観点：A<br>
     * 
     * 入力値
     * property="property"<br>
     * 
     * 期待値
     * 戻り値:String="property"<br>
     * 
     * ゲットした値を確認するテストケース<br>
     */
    public void testGetProperty01() throws Exception {
        //テスト設定
        String value = "property";
        UTUtil.setPrivateField(tag, "property", value);

        //テスト実行
        String result = tag.getProperty();

        //テスト結果確認
        assertEquals(value, result);

    } /* testGetProperty01 End */
    /**
     * testGetScope01。<br>
     * 
     * (正常系)<br>
     * 観点：A<br>
     * 
     * 入力値
     * scope="scope"<br>
     * 
     * 期待値
     * 戻り値:String="scope"<br>
     * 
     * ゲットした値を確認するテストケース<br>
     */
    public void testGetScope01() throws Exception {
        //テスト設定
        String value = "scope";
        UTUtil.setPrivateField(tag, "scope", value);

        //テスト実行
        String result = tag.getScope();

        //テスト結果確認
        assertEquals(value, result);

    } /* testGetScope01 End */

    /**
     * testGetValue01。<br>
     * 
     * (正常系)<br>
     * 観点：A<br>
     * 
     * 入力値
     * value="value"<br>
     * 
     * 期待値
     * 戻り値:String="value"<br>
     * 
     * ゲットした値を確認するテストケース<br>
     */
    public void testGetValue01() throws Exception {
        //テスト設定
        String value = "value";
        UTUtil.setPrivateField(tag, "value", value);

        //テスト実行
        String result = tag.getValue();

        //テスト結果確認
        assertEquals(value, result);

    } /* testGetValue01 End */
    
    /**
     * testGetReplaceSpToNbsp01。<br>
     * 
     * (正常系)<br>
     * 観点：A<br>
     * 
     * 入力値
     * value="(false);"<br>
     * 
     * 期待値
     * 戻り値:String="(false);"<br>
     * 
     * ゲットした値を確認するテストケース<br>
     */
    public void testGetReplaceSpToNbsp01() throws Exception {
        //テスト設定
        UTUtil.setPrivateField(tag, "replaceSpToNbsp", new Boolean(false));

        //テスト実行
        boolean result = tag.getReplaceSpToNbsp();

        //テスト結果確認
        assertFalse(result);

    } /* testGetReplaceSpToNbsp01 End */
    
} /* StringFormatterTagBaseTest Class End */
