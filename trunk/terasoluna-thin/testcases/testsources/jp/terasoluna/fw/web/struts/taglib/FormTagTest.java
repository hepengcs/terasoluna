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

import jp.terasoluna.utlib.TagUTUtil;
import jp.terasoluna.utlib.UTUtil;
import junit.framework.TestCase;

/**
 * FormTag ブラックボックステスト。<br>
 * 前提条件<br>
 * なし<br>
 * <br>
 */
public class FormTagTest extends TestCase {

    //テスト対象
    FormTag tag = null;

    /**
     * Constructor for FormTagTest.
     * @param arg0
     */
    public FormTagTest(String arg0) {
        super(arg0);
    }

    /*
     * @see TestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        tag = (FormTag) TagUTUtil.create(FormTag.class);
    }

    /*
     * @see TestCase#tearDown()
     */
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * renderFormStartElement01。<br>
     * <br>
     * (正常系)<br>
     * 観点：C<br>
     * <br>
     * 入力値<br>
     * beanName=null<br>
     * method=null<br>
     * styleClass=null<br>
     * enctype=null<br>
     * onreset=null<br>
     * onsubmit=null<br>
     * style=null<br>
     * styleId=null<br>
     * target=null<br>
     * <br>
     * 期待値<br>
     * 戻り値:Formタグにname=null,method=post,action=not nullのみが設定される。<br>
     * <br>
     * styleClass, enctype, onreset, onsubmit, style<br>
     * styleId, targetがnullの場合はFormタグには挿入されない。<br>
     */
    public void testRenderFormStartElement01() throws Exception {
        // テスト設定
        TagUTUtil.setContextPath(tag, "contextPath");
        tag.setAction("action");
        
        // テスト実行
        String result = tag.renderFormStartElement();

        // テスト結果確認
        // ランダムIDの前を確認
        int index = result.indexOf("r=");
        String ward = result.substring(0, index + 2);
        assertEquals(
                "<form name=\"null\" method=\"post\" action=\"contextPath/action?r=",
                ward);
    } /* testRenderFormStartElement01 End */

    /**
     * renderFormStartElement02。<br>
     * <br>
     * (正常系)<br>
     * 観点：A<br>
     * <br>
     * 入力値<br>
     * beanName=not null<br>
     * method=not null<br>
     * styleClass=not null<br>
     * enctype=not null<br>
     * onreset=not null<br>
     * onsubmit=not null<br>
     * style=not null<br>
     * styleId=not null<br>
     * target=not null<br>
     * <br>
     * 期待値<br>
     * 戻り値:FormタグにbeanName=not null, method=not null, styleClass=not null<br>
     * enctype=not null, onreset=not null, onsubmit=not null, style=not null<br>
     * styleId=not null, target=not nullとして設定される。<br>
     * <br>
     * styleClass, enctype, onreset, onsubmit, style<br>
     * styleId, targetがnot nullの場合はFormタグに挿入される。<br>
     */
    public void testRenderFormStartElement02() throws Exception {
        // テスト設定
        TagUTUtil.setContextPath(tag, "contextPath");
        tag.setAction("action");

        UTUtil.setPrivateField(tag, "beanName", "beanName");
        UTUtil.setPrivateField(tag, "method", "method");
        UTUtil.setPrivateField(tag, "styleClass", "styleClass");
        UTUtil.setPrivateField(tag, "enctype", "enctype");
        UTUtil.setPrivateField(tag, "onreset", "onreset");
        UTUtil.setPrivateField(tag, "onsubmit", "onsubmit");
        UTUtil.setPrivateField(tag, "style", "style");
        UTUtil.setPrivateField(tag, "styleId", "styleId");
        UTUtil.setPrivateField(tag, "target", "target");

        // テスト実行
        String result = tag.renderFormStartElement();

        // テスト結果確認
        // ランダムIDの前を確認
        int index = result.indexOf("r=");
        String ward = result.substring(0, index + 2);
        assertEquals(
                "<form name=\"beanName\" method=\"method\" action=\"contextPath/action?r=",
                ward);
        // ランダムIDの前を確認
        index = result.indexOf("class=");
        ward = result.substring(index);
        assertEquals(
                "class=\"styleClass\" enctype=\"enctype\" onreset=\"onreset\" onsubmit=\"onsubmit\" style=\"style\" id=\"styleId\" target=\"target\">",
                ward);
    } /* testRenderFormStartElement02 End */

    /**
     * renderFormStartElement03。<br>
     * <br>
     * (正常系)<br>
     * 観点：C<br>
     * <br>
     * 入力値<br>
     * beanName=""<br>
     * method=""<br>
     * styleClass=""<br>
     * enctype=""<br>
     * onreset=""<br>
     * onsubmit=""<br>
     * style=""<br>
     * styleId=""<br>
     * target=""<br>
     * <br>
     * 期待値<br>
     * 戻り値:FormタグにbeanName="", method="", styleClass=""<br>
     * enctype="", onreset="", onsubmit="", style=""<br>
     * styleId="", target=""として設定される。<br>
     * <br>
     * styleClass, enctype, onreset, onsubmit, style<br>
     * styleId, targetが""の場合はFormタグに挿入される。<br>
     */
    public void testRenderFormStartElement03() throws Exception {
        // テスト設定
        TagUTUtil.setContextPath(tag, "contextPath");
        tag.setAction("action");

        UTUtil.setPrivateField(tag, "beanName", "");
        UTUtil.setPrivateField(tag, "method", "");
        UTUtil.setPrivateField(tag, "styleClass", "");
        UTUtil.setPrivateField(tag, "enctype", "");
        UTUtil.setPrivateField(tag, "onreset", "");
        UTUtil.setPrivateField(tag, "onsubmit", "");
        UTUtil.setPrivateField(tag, "style", "");
        UTUtil.setPrivateField(tag, "styleId", "");
        UTUtil.setPrivateField(tag, "target", "");

        // テスト実行
        String result = tag.renderFormStartElement();

        // テスト結果確認
        // ランダムIDの前を確認
        int index = result.indexOf("r=");
        String ward = result.substring(0, index + 2);
        assertEquals(
                "<form name=\"\" method=\"\" action=\"contextPath/action?r=",
                ward);
        // ランダムIDの前を確認
        index = result.indexOf("class=");
        ward = result.substring(index);
        assertEquals(
                "class=\"\" enctype=\"\" onreset=\"\" onsubmit=\"\" style=\"\" id=\"\" target=\"\">",
                ward);
    } /* testRenderFormStartElement03 End */

    /**
     * renderFormStartElement04。<br>
     * <br>
     * (正常系)<br>
     * 観点：A,C<br>
     * <br>
     * 入力値<br>
     * beanName=not null<br>
     * method=not null<br>
     * styleClass=not null<br>
     * enctype=null<br>
     * onreset=""<br>
     * onsubmit=not null<br>
     * style=null<br>
     * styleId=""<br>
     * target=not null<br>
     * <br>
     * 期待値<br>
     * 戻り値:FormタグにbeanName=not null, method=not null, styleClass=not null<br>
     * onreset="", onsubmit=not null<br>
     * styleId="", target=not nullとして設定される。<br>
     * <br>
     * styleClass, onsubmit, targetがnot nullの場合はFormタグに挿入される。<br>
     * onreset, styleIdが""の場合はFormタグに挿入される。<br>
     */
    public void testRenderFormStartElement04() throws Exception {
        // テスト設定
        TagUTUtil.setContextPath(tag, "contextPath");
        tag.setAction("action");

        UTUtil.setPrivateField(tag, "beanName", "beanName");
        UTUtil.setPrivateField(tag, "method", "method");
        UTUtil.setPrivateField(tag, "styleClass", "styleClass");
        UTUtil.setPrivateField(tag, "onreset", "");
        UTUtil.setPrivateField(tag, "onsubmit", "onsubmit");
        UTUtil.setPrivateField(tag, "styleId", "");
        UTUtil.setPrivateField(tag, "target", "target");

        // テスト実行
        String result = tag.renderFormStartElement();

        // テスト結果確認
        // ランダムIDの前を確認
        int index = result.indexOf("r=");
        String ward = result.substring(0, index + 2);
        assertEquals(
                "<form name=\"beanName\" method=\"method\" action=\"contextPath/action?r=",
                ward);
        // ランダムIDの前を確認
        index = result.indexOf("class=");
        ward = result.substring(index);
        assertEquals(
                "class=\"styleClass\" onreset=\"\" onsubmit=\"onsubmit\" id=\"\" target=\"target\">",
                ward);
    } /* testRenderFormStartElement04 End */

    /**
     * getActionMappingURL01。<br>
     * <br>
     * (正常系)<br>
     * 観点：F<br>
     * <br>
     * 入力値<br>
     * url=NotNull("contextPath/action")<br>
     * <br>
     * 期待値<br>
     * 戻り値:String=文字列
     * 「contextPath/action?r=[キャッシュ避け用ランダムID]」<br>
     * <br>
     * urlがNot Null、且つ、文字「？」がurlに
     * ひとつも含まれていない場合のテストケース<br>
     */
    public void testGetActionMappingURL01() throws Exception {

        // テスト設定
        TagUTUtil.setContextPath(tag, "contextPath");
        tag.setAction("action");
        PageContext pc = TagUTUtil.getPageContext(tag);

        // テスト実行
        String result = tag.getActionMappingURL("action", pc);

        // テスト結果確認
        int index = result.indexOf("r=");
        String ward = result.substring(0, index + 2);
        assertEquals("contextPath/action?r=", ward);

    } /* testGetActionMappingURL01 End */

    /**
     * getActionMappingURL02。<br>
     * <br>
     * (正常系)<br>
     * 観点：F<br>
     * <br>
     * 入力値<br>
     * url=NotNull("contextPath/action?cz=75")<br>
     * <br>
     * 期待値<br>
     * 戻り値:String=文字列
     * 「contextPath/action?r=[キャッシュ避け用ランダムID]」<br>
     * <br>
     * urlがNot Null、且つ、文字「？」がurlに
     * 含まれている場合のテストケース<br>
     */
    public void testGetActionMappingURL02() throws Exception {

        // テスト設定
        TagUTUtil.setContextPath(tag, "contextPath");
        tag.setAction("action?cz=75");
        PageContext pc = TagUTUtil.getPageContext(tag);

        // テスト実行
        String result = tag.getActionMappingURL("action?cz=75", pc);

        // テスト結果確認
        int index = result.indexOf("r=");
        String ward = result.substring(0, index + 2);
        assertEquals("contextPath/action?cz=75&r=", ward);

    } /* testGetActionMappingURL02 End */

}
