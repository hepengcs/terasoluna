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

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;

import jp.terasoluna.utlib.TagUTUtil;
import jp.terasoluna.utlib.UTUtil;

import junit.framework.TestCase;

/**
 * WriteTag ブラックボックステスト。<br>
 * 前提条件<br>
 * <br>
 */
public class WriteTagTest extends TestCase {

    // テスト対象
    WriteTag tag = null;

    // PageContextにBeanを格納する際のキー名
    private static final String LOOKUP_BEAN = "lookup_bean";

    /**
     * Constructor for BodyTagTest.
     * @param arg0
     */
    public WriteTagTest(String arg0) {
        super(arg0);
    }

    /*
     * @see TestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        tag = (WriteTag) TagUTUtil.create(WriteTag.class);
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
     * 観点：A,C<br>
     * 
     * 入力値
     * ignore=true<br>
     * RequestUtils.lookup(PageContext, name, scope)=null<br>
     * value=ー<br>
     * replaceNullToNbsp=ー<br>
     * filter=ー<br>
     * replaceSpToNbsp=ー<br>
     * fillColumn=ー<br>
     * replaceLTtoBR=ー<br>
     * 
     * 期待値
     * 戻り値:int=SKIP_BODY<br>
     * 出力内容=－<br>
     * 
     * beanプロパティに改行コード（もしくは復帰文字）が含まれていたとき、
     * <br>に置換する処理を無効にするための属性がtrueでかつ指定したbeanが
     * nullで取得される場合のテストケース<br>
     */
    public void testDoStartTag01() throws Exception {

        // カスタムタグのインスタンスに関連付けされたPageContextを取得
        PageContext pc = TagUTUtil.getPageContext(tag);

        // PageContextにテストデータとして格納するBeanの設定
        WriteTag_BeanStub01 bean = null;

        // PageContextにポップアップ表示用スクリプト定義部出力判定変数を設定。
        pc.setAttribute(LOOKUP_BEAN, bean, PageContext.SESSION_SCOPE);

        // テストデータ設定
        UTUtil.setPrivateField(tag, "ignore", new Boolean(true));
        UTUtil.setPrivateField(tag, "name", LOOKUP_BEAN);
        UTUtil.setPrivateField(tag, "scope", "session");

        // テスト実行
        int result = tag.doStartTag();

        // テスト結果確認
        assertEquals(Tag.SKIP_BODY, result);
        BufferedReader reader = TagUTUtil.getOutputReader(tag);
        assertNull(reader.readLine());

    }

    /**
     * testDoStartTag02。<br>
     * 
     * (正常系)<br>
     * 観点：A,C<br>
     * 
     * 入力値
     * ignore=true<br>
     * RequestUtils.lookup(PageContext, name, scope)=not null<br>
     * value=null<br>
     * replaceNullToNbsp=true<br>
     * filter=ー<br>
     * replaceSpToNbsp=ー<br>
     * fillColumn=ー<br>
     * replaceLTtoBR=ー<br>
     * 
     * 期待値
     * 戻り値:int=SKIP_BODY<br>
     * 出力内容=文字列「&nbsp;」<br>
     * 
     * beanプロパティに改行コード（もしくは復帰文字）が含まれていたとき、
     * <br>に置換する処理を無効にするための属性がtrueでかつ指定したbeanが
     * 正常に取得できる場合のテストケース<br>
     */
    public void testDoStartTag02() throws Exception {

        // カスタムタグのインスタンスに関連付けされたPageContextを取得
        PageContext pc = TagUTUtil.getPageContext(tag);

        // PageContextにテストデータとして格納するBeanの設定
        WriteTag_BeanStub01 bean = new WriteTag_BeanStub01();
        bean.setValue(null);

        // PageContextにポップアップ表示用スクリプト定義部出力判定変数を設定。
        pc.setAttribute(LOOKUP_BEAN, bean, PageContext.SESSION_SCOPE);

        // テストデータ設定
        UTUtil.setPrivateField(tag, "ignore", new Boolean(true));
        UTUtil.setPrivateField(tag, "name", LOOKUP_BEAN);
        UTUtil.setPrivateField(tag, "property", "value");
        UTUtil.setPrivateField(tag, "scope", "session");

        // テスト実行
        int result = tag.doStartTag();

        // テスト結果確認
        assertEquals(result, Tag.SKIP_BODY);
        BufferedReader reader = TagUTUtil.getOutputReader(tag);
        assertEquals("&nbsp;", reader.readLine());

    }

    /**
     * testDoStartTag03。<br>
     * 
     * (正常系)<br>
     * 観点：A,C<br>
     * 
     * 入力値
     * ignore=false<br>
     * RequestUtils.lookup(PageContext, name, scope)=null<br>
     * value=null<br>
     * replaceNullToNbsp=false<br>
     * filter=ー<br>
     * replaceSpToNbsp=ー<br>
     * fillColumn=ー<br>
     * replaceLTtoBR=ー<br>
     * 
     * 期待値
     * 戻り値:int=SKIP_BODY<br>
     * 出力内容=－<br>
     * 
     * beanプロパティに改行コード（もしくは復帰文字）が含まれていたとき、
     * <br>に置換する処理を無効にするための属性がfalseでかつ指定したbean
     * がnullの場合のテストケース
     * ※なおこのテストケースではnullもしくは空文字を&nbsp;と置換するかどうか
     * 判定するための属性がfalseであった場合のテストケースも含む。<br>
     */
    public void testDoStartTag03() throws Exception {

        // カスタムタグのインスタンスに関連付けされたPageContextを取得
        PageContext pc = TagUTUtil.getPageContext(tag);

        // PageContextにテストデータとして格納するBeanの設定
        WriteTag_BeanStub01 bean = new WriteTag_BeanStub01();
        bean.setValue(null);

        // PageContextにポップアップ表示用スクリプト定義部出力判定変数を設定。
        pc.setAttribute(LOOKUP_BEAN, bean, PageContext.SESSION_SCOPE);

        // テストデータ設定
        UTUtil.setPrivateField(tag, "ignore", new Boolean(false));
        UTUtil.setPrivateField(tag, "name", LOOKUP_BEAN);
        UTUtil.setPrivateField(tag, "property", "value");
        UTUtil.setPrivateField(tag, "scope", "session");
        UTUtil.setPrivateField(tag, "replaceNullToNbsp", new Boolean(false));

        // テスト実行
        int result = tag.doStartTag();

        // テスト結果確認
        assertEquals(result, Tag.SKIP_BODY);
        BufferedReader reader = TagUTUtil.getOutputReader(tag);
        assertNull(reader.readLine());

    }

    /**
     * testDoStartTag04。<br>
     * 
     * (正常系)<br>
     * 観点：A,C<br>
     * 
     * 入力値
     * ignore=false<br>
     * RequestUtils.lookup(PageContext, name, scope)=null<br>
     * value=null<br>
     * replaceNullToNbsp=true<br>
     * filter=ー<br>
     * replaceSpToNbsp=ー<br>
     * fillColumn=ー<br>
     * replaceLTtoBR=ー<br>
     * 
     * 期待値
     * 戻り値:int=SKIP_BODY<br>
     * 出力内容=－<br>
     * 
     * beanプロパティに改行コード（もしくは復帰文字）が含まれていたとき、
     * <br>に置換する処理を無効にするための属性がfalseでかつ指定したbeanが
     * nullの場合のテストケース
     * ※なおこのテストケースではnullもしくは空文字を&nbsp;と置換するかどうか
     * 判定するための属性がtrueであった場合のテストケースも含む。<br>
     */
    public void testDoStartTag04() throws Exception {

        // カスタムタグのインスタンスに関連付けされたPageContextを取得
        PageContext pc = TagUTUtil.getPageContext(tag);

        // PageContextにテストデータとして格納するBeanの設定
        WriteTag_BeanStub01 bean = null;

        // PageContextにポップアップ表示用スクリプト定義部出力判定変数を設定。
        pc.setAttribute(LOOKUP_BEAN, bean, PageContext.SESSION_SCOPE);

        // テストデータ設定
        UTUtil.setPrivateField(tag, "ignore", new Boolean(true));
        UTUtil.setPrivateField(tag, "name", LOOKUP_BEAN);
        UTUtil.setPrivateField(tag, "scope", "session");
        UTUtil.setPrivateField(tag, "property", "value");
        UTUtil.setPrivateField(tag, "scope", "session");
        UTUtil.setPrivateField(tag, "replaceNullToNbsp", new Boolean(true));

        // テスト実行
        int result = tag.doStartTag();

        // テスト結果確認
        assertEquals(result, Tag.SKIP_BODY);
        BufferedReader reader = TagUTUtil.getOutputReader(tag);
        assertNull(reader.readLine());
    }

    /**
     * testDoStartTag05。<br>
     * 
     * (正常系)<br>
     * 観点：A,C<br>
     * 
     * 入力値
     * ignore=false<br>
     * RequestUtils.lookup(PageContext, name, scope)=not null<br>
     * value=""（空文字）<br>
     * replaceNullToNbsp=false<br>
     * filter=false<br>
     * replaceSpToNbsp=false<br>
     * fillColumn=10<br>
     * replaceLTtoBR=false<br>
     * 
     * 期待値
     * 戻り値:int=SKIP_BODY<br>
     * 出力内容=－<br>
     * 
     * beanから取得したプロパティ値が空文字で、かつnullもしくは空文字を&nbsp;
     * と置換するかどうか判定するための属性がfalseの場合のテストケース<br>
     */
    public void testDoStartTag05() throws Exception {

        // カスタムタグのインスタンスに関連付けされたPageContextを取得
        PageContext pc = TagUTUtil.getPageContext(tag);

        // PageContextにテストデータとして格納するBeanの設定
        WriteTag_BeanStub01 bean = new WriteTag_BeanStub01();
        bean.setValue("");

        // PageContextにポップアップ表示用スクリプト定義部出力判定変数を設定。
        pc.setAttribute(LOOKUP_BEAN, bean, PageContext.SESSION_SCOPE);

        // テストデータ設定
        UTUtil.setPrivateField(tag, "ignore", new Boolean(false));
        UTUtil.setPrivateField(tag, "name", LOOKUP_BEAN);
        UTUtil.setPrivateField(tag, "property", "value");
        UTUtil.setPrivateField(tag, "scope", "session");
        UTUtil.setPrivateField(tag, "replaceNullToNbsp", new Boolean(false));
        UTUtil.setPrivateField(tag, "filter", new Boolean(false));
        UTUtil.setPrivateField(tag, "replaceSpToNbsp", new Boolean(false));
        UTUtil.setPrivateField(tag, "fillColumn", new Integer(10));
        UTUtil.setPrivateField(tag, "replaceLFtoBR", new Boolean(false));

        // テスト実行
        int result = tag.doStartTag();

        // テスト結果確認
        assertEquals(result, Tag.SKIP_BODY);
        BufferedReader reader = TagUTUtil.getOutputReader(tag);
        assertNull(reader.readLine());

    }

    /**
     * testDoStartTag06。<br>
     * 
     * (正常系)<br>
     * 観点：A,C<br>
     * 
     * 入力値
     * ignore=false<br>
     * RequestUtils.lookup(PageContext, name, scope)=not null<br>
     * value=""（空文字）<br>
     * replaceNullToNbsp=true<br>
     * filter=false<br>
     * replaceSpToNbsp=false<br>
     * fillColumn=10<br>
     * replaceLTtoBR=false<br>
     * 
     * 期待値
     * 戻り値:int=SKIP_BODY<br>
     * 出力内容=文字列「&nbsp;」<br>
     * 
     * beanから取得したプロパティ値が空文字で、かつnullもしくは空文字を&nbsp;
     * と置換するかどうか判定するための属性がtrueの場合のテストケース<br>
     */
    public void testDoStartTag06() throws Exception {

        // カスタムタグのインスタンスに関連付けされたPageContextを取得
        PageContext pc = TagUTUtil.getPageContext(tag);

        // PageContextにテストデータとして格納するBeanの設定
        WriteTag_BeanStub01 bean = new WriteTag_BeanStub01();
        bean.setValue("");

        // PageContextにポップアップ表示用スクリプト定義部出力判定変数を設定。
        pc.setAttribute(LOOKUP_BEAN, bean, PageContext.SESSION_SCOPE);

        // テストデータ設定
        UTUtil.setPrivateField(tag, "ignore", new Boolean(false));
        UTUtil.setPrivateField(tag, "name", LOOKUP_BEAN);
        UTUtil.setPrivateField(tag, "property", "value");
        UTUtil.setPrivateField(tag, "scope", "session");
        UTUtil.setPrivateField(tag, "replaceNullToNbsp", new Boolean(true));
        UTUtil.setPrivateField(tag, "filter", new Boolean(false));
        UTUtil.setPrivateField(tag, "replaceSpToNbsp", new Boolean(false));
        UTUtil.setPrivateField(tag, "fillColumn", new Integer(10));
        UTUtil.setPrivateField(tag, "replaceLFtoBR", new Boolean(false));

        // テスト実行
        int result = tag.doStartTag();

        // テスト結果確認
        assertEquals(result, Tag.SKIP_BODY);
        BufferedReader reader = TagUTUtil.getOutputReader(tag);
        assertEquals("&nbsp;", reader.readLine());

    }

    /**
     * testDoStartTag07。<br>
     * 
     * (正常系)<br>
     * 観点：A<br>
     * 
     * 入力値
     * ignore=false<br>
     * RequestUtils.lookup(PageContext, name, scope)=not null<br>
     * value="<>&"'"<br>
     * replaceNullToNbsp=false<br>
     * filter=true<br>
     * replaceSpToNbsp=false<br>
     * fillColumn=10<br>
     * replaceLTtoBR=false<br>
     * 
     * 期待値
     * 戻り値:int=SKIP_BODY<br>
     * 出力内容=文字列「&lt;&gt;&amp;&quat;&#39」<br>
     * 
     * beanから取得したプロパティ値にHTML上での特殊文字が含まれ、
     * かつ特殊文字をHTMLに対応した文字に置き換えるための属性が
     * trueの場合のテストケース<br>
     */
    public void testDoStartTag07() throws Exception {

        // カスタムタグのインスタンスに関連付けされたPageContextを取得
        PageContext pc = TagUTUtil.getPageContext(tag);

        // PageContextにテストデータとして格納するBeanの設定
        WriteTag_BeanStub01 bean = new WriteTag_BeanStub01();
        bean.setValue("<>&\"");

        // PageContextにポップアップ表示用スクリプト定義部出力判定変数を設定。
        pc.setAttribute(LOOKUP_BEAN, bean, PageContext.SESSION_SCOPE);

        // テストデータ設定
        UTUtil.setPrivateField(tag, "ignore", new Boolean(false));
        UTUtil.setPrivateField(tag, "name", LOOKUP_BEAN);
        UTUtil.setPrivateField(tag, "property", "value");
        UTUtil.setPrivateField(tag, "scope", "session");
        UTUtil.setPrivateField(tag, "replaceNullToNbsp", new Boolean(false));
        UTUtil.setPrivateField(tag, "filter", new Boolean(true));
        UTUtil.setPrivateField(tag, "replaceSpToNbsp", new Boolean(false));
        UTUtil.setPrivateField(tag, "fillColumn", new Integer(10));
        UTUtil.setPrivateField(tag, "replaceLFtoBR", new Boolean(false));

        // テスト実行
        int result = tag.doStartTag();

        // テスト結果確認
        assertEquals(result, Tag.SKIP_BODY);
        BufferedReader reader = TagUTUtil.getOutputReader(tag);
        assertEquals("&lt;&gt;&amp;&quot;", reader.readLine());

    }

    /**
     * testDoStartTag08。<br>
     * 
     * (正常系)<br>
     * 観点：A<br>
     * 
     * 入力値
     * ignore=false<br>
     * RequestUtils.lookup(PageContext, name, scope)=not null<br>
     * value="a b c"<br>
     * replaceNullToNbsp=false<br>
     * filter=false<br>
     * replaceSpToNbsp=true<br>
     * fillColumn=10<br>
     * replaceLTtoBR=false<br>
     * 
     * 期待値
     * 戻り値:int=SKIP_BODY<br>
     * 出力内容=文字列「a&nbsp;b&nbsp;c」<br>
     * 
     * beanから取得したプロパティ値に半角スペースが含まれ、かつ特殊文字を
     * HTMLに対応した文字に置き換えるための属性がtrueの場合のテストケース<br>
     */
    public void testDoStartTag08() throws Exception {

        // カスタムタグのインスタンスに関連付けされたPageContextを取得
        PageContext pc = TagUTUtil.getPageContext(tag);

        // PageContextにテストデータとして格納するBeanの設定
        WriteTag_BeanStub01 bean = new WriteTag_BeanStub01();
        bean.setValue("a b c");

        // PageContextにポップアップ表示用スクリプト定義部出力判定変数を設定。
        pc.setAttribute(LOOKUP_BEAN, bean, PageContext.SESSION_SCOPE);

        // テストデータ設定
        UTUtil.setPrivateField(tag, "ignore", new Boolean(false));
        UTUtil.setPrivateField(tag, "name", LOOKUP_BEAN);
        UTUtil.setPrivateField(tag, "property", "value");
        UTUtil.setPrivateField(tag, "scope", "session");
        UTUtil.setPrivateField(tag, "replaceNullToNbsp", new Boolean(false));
        UTUtil.setPrivateField(tag, "filter", new Boolean(false));
        UTUtil.setPrivateField(tag, "replaceSpToNbsp", new Boolean(true));
        UTUtil.setPrivateField(tag, "fillColumn", new Integer(10));
        UTUtil.setPrivateField(tag, "replaceLFtoBR", new Boolean(false));

        // テスト実行
        int result = tag.doStartTag();

        // テスト結果確認
        assertEquals(result, Tag.SKIP_BODY);
        BufferedReader reader = TagUTUtil.getOutputReader(tag);
        assertEquals("a&nbsp;b&nbsp;c", reader.readLine());

    }

    /**
     * testDoStartTag09。<br>
     * 
     * (正常系)<br>
     * 観点：A<br>
     * 
     * 入力値
     * ignore=false<br>
     * RequestUtils.lookup(PageContext, name, scope)=not null<br>
     * value="1234567"<br>
     * replaceNullToNbsp=false<br>
     * filter=false<br>
     * replaceSpToNbsp=true<br>
     * fillColumn=3<br>
     * replaceLTtoBR=false<br>
     * 
     * 期待値
     * 戻り値:int=SKIP_BODY<br>
     * 出力内容=文字列「123<br>456<br>7」<br>
     * 
     * beanから取得したプロパティ値がbeanプロパティから取得した値を、
     * 1カラムにて表示するサイズよりも大きかった場合のテストケース<br>
     */
    public void testDoStartTag09() throws Exception {

        // カスタムタグのインスタンスに関連付けされたPageContextを取得
        PageContext pc = TagUTUtil.getPageContext(tag);

        // PageContextにテストデータとして格納するBeanの設定
        WriteTag_BeanStub01 bean = new WriteTag_BeanStub01();
        bean.setValue("1234567");

        // PageContextにポップアップ表示用スクリプト定義部出力判定変数を設定。
        pc.setAttribute(LOOKUP_BEAN, bean, PageContext.SESSION_SCOPE);

        // テストデータ設定
        UTUtil.setPrivateField(tag, "ignore", new Boolean(false));
        UTUtil.setPrivateField(tag, "name", LOOKUP_BEAN);
        UTUtil.setPrivateField(tag, "property", "value");
        UTUtil.setPrivateField(tag, "scope", "session");
        UTUtil.setPrivateField(tag, "replaceNullToNbsp", new Boolean(false));
        UTUtil.setPrivateField(tag, "filter", new Boolean(false));
        UTUtil.setPrivateField(tag, "replaceSpToNbsp", new Boolean(true));
        UTUtil.setPrivateField(tag, "fillColumn", new Integer(3));
        UTUtil.setPrivateField(tag, "replaceLFtoBR", new Boolean(false));

        // テスト実行
        int result = tag.doStartTag();

        // テスト結果確認
        assertEquals(result, Tag.SKIP_BODY);
        BufferedReader reader = TagUTUtil.getOutputReader(tag);
        assertEquals("123<br>456<br>7", reader.readLine());

    }

    /**
     * testDoStartTag10。<br>
     * 
     * (正常系)<br>
     * 観点：A<br>
     * 
     * 入力値
     * ignore=false<br>
     * RequestUtils.lookup(PageContext, name, scope)=not null<br>
     * value="a（改行コード\n）（改行コード\r）b"<br>
     * replaceNullToNbsp=false<br>
     * filter=false<br>
     * replaceSpToNbsp=false<br>
     * fillColumn=10<br>
     * replaceLTtoBR=true<br>
     * 
     * 期待値
     * 戻り値:int=SKIP_BODY<br>
     * 出力内容=文字列「a<br><br>b」<br>
     * 
     * beanから取得したプロパティ値がbeanプロパティから取得した値に改行コード
     * が含まれ、かつ改行コードを<br>と置換し、バッファに格納かどうか判定する
     * ための属性がtrueだった場合のテストケース<br>
     */
    public void testDoStartTag10() throws Exception {

        // カスタムタグのインスタンスに関連付けされたPageContextを取得
        PageContext pc = TagUTUtil.getPageContext(tag);

        // PageContextにテストデータとして格納するBeanの設定
        WriteTag_BeanStub01 bean = new WriteTag_BeanStub01();
        bean.setValue("a\n\rb");

        // PageContextにポップアップ表示用スクリプト定義部出力判定変数を設定。
        pc.setAttribute(LOOKUP_BEAN, bean, PageContext.SESSION_SCOPE);

        // テストデータ設定
        UTUtil.setPrivateField(tag, "ignore", new Boolean(false));
        UTUtil.setPrivateField(tag, "name", LOOKUP_BEAN);
        UTUtil.setPrivateField(tag, "property", "value");
        UTUtil.setPrivateField(tag, "scope", "session");
        UTUtil.setPrivateField(tag, "replaceNullToNbsp", new Boolean(false));
        UTUtil.setPrivateField(tag, "filter", new Boolean(false));
        UTUtil.setPrivateField(tag, "replaceSpToNbsp", new Boolean(false));
        UTUtil.setPrivateField(tag, "fillColumn", new Integer(10));
        UTUtil.setPrivateField(tag, "replaceLFtoBR", new Boolean(true));

        // テスト実行
        int result = tag.doStartTag();

        // テスト結果確認
        assertEquals(result, Tag.SKIP_BODY);
        BufferedReader reader = TagUTUtil.getOutputReader(tag);
        assertEquals("a<br><br>b", reader.readLine());

    }

    /**
     * testDoStartTag11。<br>
     * 
     * (正常系)<br>
     * 観点：A<br>
     * 
     * 入力値
     * ignore=false<br>
     * RequestUtils.lookup(PageContext, name, scope)=not null<br>
     * value="a（改行コード）b"<br>
     * replaceNullToNbsp=false<br>
     * filter=false<br>
     * replaceSpToNbsp=false<br>
     * fillColumn=10<br>
     * replaceLTtoBR=false<br>
     * 
     * 期待値
     * 戻り値:int=SKIP_BODY<br>
     * 出力内容=文字列「ab」<br>
     * 
     * beanから取得したプロパティ値がbeanプロパティから取得した値に改行コード
     * が含まれ、かつ改行コードを無視し、バッファに格納するかどうか判定するため
     * の属性がtrueだった場合のテストケース<br>
     */
    public void testDoStartTag11() throws Exception {

        // カスタムタグのインスタンスに関連付けされたPageContextを取得
        PageContext pc = TagUTUtil.getPageContext(tag);

        // PageContextにテストデータとして格納するBeanの設定
        WriteTag_BeanStub01 bean = new WriteTag_BeanStub01();
        bean.setValue("a\nb");

        // PageContextにポップアップ表示用スクリプト定義部出力判定変数を設定。
        pc.setAttribute(LOOKUP_BEAN, bean, PageContext.SESSION_SCOPE);

        // テストデータ設定
        UTUtil.setPrivateField(tag, "ignore", new Boolean(false));
        UTUtil.setPrivateField(tag, "name", LOOKUP_BEAN);
        UTUtil.setPrivateField(tag, "property", "value");
        UTUtil.setPrivateField(tag, "scope", "session");
        UTUtil.setPrivateField(tag, "replaceNullToNbsp", new Boolean(false));
        UTUtil.setPrivateField(tag, "filter", new Boolean(false));
        UTUtil.setPrivateField(tag, "replaceSpToNbsp", new Boolean(false));
        UTUtil.setPrivateField(tag, "fillColumn", new Integer(10));
        UTUtil.setPrivateField(tag, "replaceLFtoBR", new Boolean(false));

        // テスト実行
        int result = tag.doStartTag();

        // テスト結果確認
        assertEquals(result, Tag.SKIP_BODY);
        BufferedReader reader = TagUTUtil.getOutputReader(tag);
        assertEquals("ab", reader.readLine());

    }
    /**
     * testDoStartTag12。<br>
     * 
     * (正常系)<br>
     * 観点：A<br>
     * 
     * 入力値
     * ignore=false<br>
     * RequestUtils.lookup(PageContext, name, scope)=not null<br>
     * value="a b c"<br>
     * replaceNullToNbsp=false<br>
     * filter=false<br>
     * replaceSpToNbsp=true<br>
     * fillColumn=0<br>
     * replaceLTtoBR=false<br>
     * 
     * 期待値
     * 戻り値:int=SKIP_BODY<br>
     * 出力内容=null<br>
     * 
     * 1カラムにて表示するサイズが0だった場合、
     * <br>が付与されないことを確認する。<br>
     */
    public void testDoStartTag12() throws Exception {

        // カスタムタグのインスタンスに関連付けされたPageContextを取得
        PageContext pc = TagUTUtil.getPageContext(tag);

        // PageContextにテストデータとして格納するBeanの設定
        WriteTag_BeanStub01 bean = new WriteTag_BeanStub01();
        bean.setValue("a b c");

        // PageContextにポップアップ表示用スクリプト定義部出力判定変数を設定。
        pc.setAttribute(LOOKUP_BEAN, bean, PageContext.SESSION_SCOPE);

        // テストデータ設定
        UTUtil.setPrivateField(tag, "ignore", new Boolean(false));
        UTUtil.setPrivateField(tag, "name", LOOKUP_BEAN);
        UTUtil.setPrivateField(tag, "property", "value");
        UTUtil.setPrivateField(tag, "scope", "session");
        UTUtil.setPrivateField(tag, "replaceNullToNbsp", new Boolean(true));
        UTUtil.setPrivateField(tag, "filter", new Boolean(false));
        UTUtil.setPrivateField(tag, "replaceSpToNbsp", new Boolean(true));
        UTUtil.setPrivateField(tag, "fillColumn", new Integer(0));
        UTUtil.setPrivateField(tag, "replaceLFtoBR", new Boolean(false));

        // テスト実行
        int result = tag.doStartTag();

        // テスト結果確認
        assertEquals(result, Tag.SKIP_BODY);
        BufferedReader reader = TagUTUtil.getOutputReader(tag);
        assertEquals("a&nbsp;b&nbsp;c", reader.readLine());

    }
    /**
     * testDoStartTag13。<br>
     * 
     * (異常系)<br>
     * 観点：G<br>
     * 
     * 入力値
     * ignore=false<br>
     * bean=null<br>
     * value=ー<br>
     * replaceNullToNbsp=true<br>
     * filter=ー<br>
     * replaceSpToNbsp=ー<br>
     * fillColumn=ー<br>
     * replaceLTtoBR=ー<br>
     * 
     * 期待値
     * 戻り値:JspException<br>
     * 出力内容=-<br>
     * 
     * 指定したbeanがnullの場合、JspExceptionが発生することを確認する。<br>
     * ※なおこのテストケースではnullもしくは空文字を&nbsp;と置換するかどうか
     * 判定するための属性がtrueであった場合のテストケースも含む。<br>
     */
    public void testDoStartTag13() throws Exception {

        // カスタムタグのインスタンスに関連付けされたPageContextを取得
        PageContext pc = TagUTUtil.getPageContext(tag);

        // PageContextにテストデータとして格納するBeanの設定
        WriteTag_BeanStub01 bean = null;

        // PageContextにポップアップ表示用スクリプト定義部出力判定変数を設定。
        pc.setAttribute(LOOKUP_BEAN, bean, PageContext.SESSION_SCOPE);

        // テストデータ設定
        UTUtil.setPrivateField(tag, "ignore", new Boolean(false));
        UTUtil.setPrivateField(tag, "name", LOOKUP_BEAN);
        UTUtil.setPrivateField(tag, "scope", "session");
        UTUtil.setPrivateField(tag, "property", "value");

        try {
            // テスト実行
            tag.doStartTag();
            fail();
        } catch (JspException e) {
            return;
        }
    }

    /**
     * testDoStartTag14。<br>
     * 
     * (正常系)<br>
     * 観点：A<br>
     * 
     * 入力値
     * ignore=false<br>
     * RequestUtils.lookup(PageContext, name, scope)=not null<br>
     * value="ab"<br>
     * replaceNullToNbsp=false<br>
     * filter=false<br>
     * replaceSpToNbsp=false<br>
     * fillColumn=10<br>
     * replaceLTtoBR=true<br>
     * addBR=true<br>
     * 
     * 期待値
     * 戻り値:int=SKIP_BODY<br>
     * 出力内容=文字列「ab<br>」<br>
     * 
     * addBR属性がtrueの場合、プロパティ値の末尾に改行コードが付与されること。<br>
     */
    public void testDoStartTag14() throws Exception {

        // カスタムタグのインスタンスに関連付けされたPageContextを取得
        PageContext pc = TagUTUtil.getPageContext(tag);

        // PageContextにテストデータとして格納するBeanの設定
        WriteTag_BeanStub01 bean = new WriteTag_BeanStub01();
        bean.setValue("ab");

        // PageContextにポップアップ表示用スクリプト定義部出力判定変数を設定。
        pc.setAttribute(LOOKUP_BEAN, bean, PageContext.SESSION_SCOPE);

        // テストデータ設定
        UTUtil.setPrivateField(tag, "ignore", new Boolean(false));
        UTUtil.setPrivateField(tag, "name", LOOKUP_BEAN);
        UTUtil.setPrivateField(tag, "property", "value");
        UTUtil.setPrivateField(tag, "scope", "session");
        UTUtil.setPrivateField(tag, "replaceNullToNbsp", new Boolean(false));
        UTUtil.setPrivateField(tag, "filter", new Boolean(false));
        UTUtil.setPrivateField(tag, "replaceSpToNbsp", new Boolean(false));
        UTUtil.setPrivateField(tag, "fillColumn", new Integer(10));
        UTUtil.setPrivateField(tag, "replaceLFtoBR", new Boolean(true));
        UTUtil.setPrivateField(tag, "addBR", new Boolean(true));

        // テスト実行
        int result = tag.doStartTag();

        // テスト結果確認
        assertEquals(result, Tag.SKIP_BODY);
        BufferedReader reader = TagUTUtil.getOutputReader(tag);
        assertEquals("ab<br>", reader.readLine());
    }
    
    /**
     * testDoStartTag15。<br>
     * 
     * (正常系)<br>
     * 観点：A<br>
     * 
     * 入力値
     * ignore=false<br>
     * RequestUtils.lookup(PageContext, name, scope)=not null<br>
     * value="123456"<br>
     * replaceNullToNbsp=false<br>
     * filter=false<br>
     * replaceSpToNbsp=true<br>
     * fillColumn=3<br>
     * replaceLFtoBR=false<br>
     * 
     * 期待値
     * 戻り値:int=SKIP_BODY<br>
     * 出力内容=文字列「123<br>456」<br>
     * 
     * beanから取得したプロパティ値がbeanプロパティから取得した値を、
     * 1カラムにて表示するサイズよりも大きかった場合のテストケース、ただし
     * fillColumnがvalueの倍数でない場合。<br>
     */
    public void testDoStartTag15() throws Exception {

        // カスタムタグのインスタンスに関連付けされたPageContextを取得
        PageContext pc = TagUTUtil.getPageContext(tag);

        // PageContextにテストデータとして格納するBeanの設定
        WriteTag_BeanStub01 bean = new WriteTag_BeanStub01();
        bean.setValue("123456");

        // PageContextにポップアップ表示用スクリプト定義部出力判定変数を設定。
        pc.setAttribute(LOOKUP_BEAN, bean, PageContext.SESSION_SCOPE);

        // テストデータ設定
        UTUtil.setPrivateField(tag, "ignore", new Boolean(false));
        UTUtil.setPrivateField(tag, "name", LOOKUP_BEAN);
        UTUtil.setPrivateField(tag, "property", "value");
        UTUtil.setPrivateField(tag, "scope", "session");
        UTUtil.setPrivateField(tag, "replaceNullToNbsp", new Boolean(false));
        UTUtil.setPrivateField(tag, "filter", new Boolean(false));
        UTUtil.setPrivateField(tag, "replaceSpToNbsp", new Boolean(true));
        UTUtil.setPrivateField(tag, "fillColumn", new Integer(3));
        UTUtil.setPrivateField(tag, "replaceLFtoBR", new Boolean(false));

        // テスト実行
        int result = tag.doStartTag();

        // テスト結果確認
        assertEquals(result, Tag.SKIP_BODY);
        BufferedReader reader = TagUTUtil.getOutputReader(tag);
        assertEquals("123<br>456", reader.readLine());

    }
    
    /**
     * testRelease01。<br>
     * 
     * (正常系)<br>
     * 観点：C<br>
     * 
     * 入力値
     * filter=false<br>
     * replaceNullToNbsp=false<br>
     * replaceSpToNbsp=false<br>
     * replaceLFtoBR=false<br>
     * ignore=true<br>
     * name="name"<br>
     * property="property"<br>
     * scope="scope"<br>
     * fillColumn=100<br>
     * 
     * 期待値
     * 戻り値:void<br>
     * filter=true<br>
     * replaceNullToNbsp=true<br>
     * replaceSpToNbsp=true<br>
     * replaceLFtoBR=true<br>
     * ignore=false<br>
     * name=Null<br>
     * property=Null<br>
     * scope=Null<br>
     * fillColumn=-1<br>
     * 
     * 前提条件として設定した各値が、実行時に各条件値が初期化されることを確認する<br>
     */
    public void testRelease01() throws Exception {

        //テストデータ設定
        UTUtil.setPrivateField(tag, "filter", new Boolean(false));
        UTUtil.setPrivateField(tag, "replaceNullToNbsp", new Boolean(false));
        UTUtil.setPrivateField(tag, "replaceSpToNbsp", new Boolean(false));
        UTUtil.setPrivateField(tag, "replaceLFtoBR", new Boolean(false));
        UTUtil.setPrivateField(tag, "ignore", new Boolean(true));
        UTUtil.setPrivateField(tag, "name", "name");
        UTUtil.setPrivateField(tag, "property", "property");
        UTUtil.setPrivateField(tag, "scope", "scope");
        UTUtil.setPrivateField(tag, "fillColumn", new Integer(100));

        //テスト実行
        tag.release();

        //テスト結果確認
        assertTrue((Boolean) UTUtil.getPrivateField(tag, "filter"));
        assertTrue((Boolean) UTUtil.getPrivateField(tag, "replaceNullToNbsp"));
        assertTrue((Boolean) UTUtil.getPrivateField(tag, "replaceSpToNbsp"));
        assertTrue((Boolean) UTUtil.getPrivateField(tag, "replaceLFtoBR"));
        assertFalse((Boolean) UTUtil.getPrivateField(tag, "ignore"));
        assertNull(UTUtil.getPrivateField(tag, "name"));
        assertNull(UTUtil.getPrivateField(tag, "property"));
        assertNull(UTUtil.getPrivateField(tag, "scope"));
        assertEquals(-1, UTUtil.getPrivateField(tag, "fillColumn"));

    }

    /**
     * testSetFilter01。<br>
     * 
     * (正常系)<br>
     * 観点：A<br>
     * 
     * 入力値 Filter="filter"<br>
     * 
     * 期待値 戻り値:void<br>
     * 状態変化:filter=false<br>
     * 
     * セットした値を確認するテストケース<br>
     */
    public void testSetFilter01() throws Exception {

        //テスト実行
        tag.setFilter(false);

        //テスト結果確認
        assertFalse((Boolean) UTUtil.getPrivateField(tag, "filter"));

    }

    /**
     * testGetFilter01。<br>
     * 
     * (正常系)<br>
     * 観点：A<br>
     * 
     * 入力値
     * filter=false
     * 
     * 期待値
     * 戻り値:boolean<br>
     * filter=false<br>
     * 
     * セットした値を確認するテストケース<br>
     */
    public void testGetFilter01() throws Exception {

        // テストデータ設定
        UTUtil.setPrivateField(tag, "filter", new Boolean(false));

        //テスト結果確認
        assertFalse(tag.getFilter());

    }

    /**
     * testSetReplaceNullToNbsp01。<br>
     * 
     * (正常系)<br>
     * 観点：A<br>
     * 
     * 入力値
     * replaceNullToNbsp="replaceNullToNbsp"<br>
     * 
     * 期待値
     * 戻り値:void<br>
     * 状態変化:replaceNullToNbsp=false<br>
     * 
     * セットした値を確認するテストケース<br>
     */
    public void testSetReplaceNullToNbsp01() throws Exception {

        //テスト実行
        tag.setReplaceNullToNbsp(false);

        //テスト結果確認
        assertFalse((Boolean) UTUtil.getPrivateField(tag, "replaceNullToNbsp"));

    }

    /**
     * testGetReplaceNullToNbsp01。<br>
     * 
     * (正常系)<br>
     * 観点：A<br>
     * 
     * 入力値
     * replaceNullToNbsp=false
     * 
     * 期待値
     * 戻り値:boolean<br>
     * replaceNullToNbsp=false<br>
     * 
     * セットした値を確認するテストケース<br>
     */
    public void testGetReplaceNullToNbsp01() throws Exception {

        // テストデータ設定
        UTUtil.setPrivateField(tag, "replaceNullToNbsp", new Boolean(false));

        //テスト結果確認
        assertFalse(tag.getReplaceNullToNbsp());

    }

    /**
     * testSetReplaceSpToNbsp01。<br>
     * 
     * (正常系)<br>
     * 観点：A<br>
     * 
     * 入力値
     * replaceSpToNbsp="replaceSpToNbsp"<br>
     * 
     * 期待値
     * 戻り値:void<br>
     * 状態変化:replaceSpToNbsp=false<br>
     * 
     * セットした値を確認するテストケース<br>
     */
    public void testSetReplaceSpToNbsp01() throws Exception {

        //テスト実行
        tag.setReplaceSpToNbsp(false);

        //テスト結果確認
        assertFalse((Boolean) UTUtil.getPrivateField(tag, "replaceSpToNbsp"));

    }

    /**
     * testGetReplaceSpToNbsp01。<br>
     * 
     * (正常系)<br>
     * 観点：A<br>
     * 
     * 入力値
     * replaceSpToNbsp=false
     * 
     * 期待値
     * 戻り値:boolean<br>
     * replaceSpToNbsp=false<br>
     * 
     * セットした値を確認するテストケース<br>
     */
    public void testGetReplaceSpToNbsp01() throws Exception {

        // テストデータ設定
        UTUtil.setPrivateField(tag, "replaceSpToNbsp", new Boolean(false));

        //テスト結果確認
        assertFalse(tag.getReplaceSpToNbsp());

    }

    /**
     * testSetReplaceLFtoBR01。<br>
     * 
     * (正常系)<br>
     * 観点：A<br>
     * 
     * 入力値
     * replaceLFtoBR="replaceLFtoBR"<br>
     * 
     * 期待値
     * 戻り値:void<br>
     * 状態変化:replaceLFtoBR=false<br>
     * 
     * セットした値を確認するテストケース<br>
     */
    public void testSetReplaceLFtoBR01() throws Exception {

        //テスト実行
        tag.setReplaceLFtoBR(false);

        //テスト結果確認
        assertFalse((Boolean) UTUtil.getPrivateField(tag, "replaceLFtoBR"));

    }

    /**
     * testGetReplaceLFtoBR01。<br>
     * 
     * (正常系)<br>
     * 観点：A<br>
     * 
     * 入力値
     * replaceLFtoBR=false
     * 
     * 期待値
     * 戻り値:boolean<br>
     * replaceLFtoBR=false<br>
     * 
     * セットした値を確認するテストケース<br>
     */
    public void testGetReplaceLFtoBR01() throws Exception {

        // テストデータ設定
        UTUtil.setPrivateField(tag, "replaceLFtoBR", new Boolean(false));

        //テスト結果確認
        assertFalse(tag.getReplaceLFtoBR());

    }

    /**
     * testSetIgnore01。<br>
     * 
     * (正常系)<br>
     * 観点：A<br>
     * 
     * 入力値
     * ignore="ignore"<br>
     * 
     * 期待値
     * 戻り値:void<br>
     * 状態変化:ignore=true<br>
     * 
     * セットした値を確認するテストケース<br>
     */
    public void testSetIgnore01() throws Exception {

        //テスト実行
        tag.setIgnore(true);

        //テスト結果確認
        assertTrue((Boolean) UTUtil.getPrivateField(tag, "ignore"));

    }

    /**
     * testGetignore01。<br>
     * 
     * (正常系)<br>
     * 観点：A<br>
     * 
     * 入力値
     * ignore=true
     * 
     * 期待値
     * 戻り値:boolean<br>
     * ignore=true<br>
     * 
     * セットした値を確認するテストケース<br>
     */
    public void testGetIgnore01() throws Exception {

        // テストデータ設定
        UTUtil.setPrivateField(tag, "ignore", new Boolean(true));

        //テスト結果確認
        assertTrue(tag.getIgnore());

    }

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
     * 状態変化:name="name"<br>
     * 
     * セットした値を確認するテストケース<br>
     */
    public void testSetName01() throws Exception {

        //テスト実行
        tag.setName("name");

        //テスト結果確認
        assertEquals("name", UTUtil.getPrivateField(tag, "name"));

    }

    /**
     * testGetName01。<br>0
     * 
     * (正常系)<br>
     * 観点：A<br>
     * 
     * 入力値
     * name="name"
     * 
     * 期待値
     * 戻り値:String<br>
     * name="name"<br>
     * 
     * セットした値を確認するテストケース<br>
     */
    public void testGetName01() throws Exception {

        // テストデータ設定
        UTUtil.setPrivateField(tag, "name", "name");

        //テスト結果確認
        assertEquals("name", tag.getName());

    }

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
     * 状態変化:property="property"<br>
     * 
     * セットした値を確認するテストケース<br>
     */
    public void testSetProperty01() throws Exception {

        //テスト実行
        tag.setProperty("property");

        //テスト結果確認
        assertEquals("property", UTUtil.getPrivateField(tag, "property"));

    }

    /**
     * testGetProperty01。<br>
     * 
     * (正常系)<br>
     * 観点:A<br>
     * 
     * 入力値
     * property="property"
     * 
     * 期待値
     * 戻り値:String<br>
     * property="property"<br>
     * 
     * セットした値を確認するテストケース<br>
     */
    public void testGetProperty01() throws Exception {

        // テストデータ設定
        UTUtil.setPrivateField(tag, "property", "property");

        //テスト結果確認
        assertEquals("property", tag.getProperty());

    }

    /**
     * testSetScope01。<br>
     * 
     * (正常系)<br>
     * 観点:A<br>
     * 
     * 入力値
     * scope="scope"<br>
     * 
     * 期待値
     * 戻り値:void<br>
     * 状態変化:scope="scope"<br>
     * 
     * セットした値を確認するテストケース<br>
     */
    public void testSetScope01() throws Exception {

        //テスト実行
        tag.setScope("scope");

        //テスト結果確認
        assertEquals("scope", UTUtil.getPrivateField(tag, "scope"));

    }

    /**
     * testGetScope01。<br>
     * 
     * (正常系)<br>
     * 観点：A<br>
     * 
     * 入力値
     * scope="scope"
     * 
     * 期待値
     * 戻り値:String<br>
     * 状態変化:scope="scope"<br>
     * 
     * セットした値を確認するテストケース<br>
     */
    public void testGetScope01() throws Exception {

        // テストデータ設定
        UTUtil.setPrivateField(tag, "scope", "scope");

        //テスト結果確認
        assertEquals("scope", tag.getScope());

    }

    /**
     * testSetFillColumn01。<br>
     * 
     * (正常系)<br>
     * 観点：A<br>
     * 
     * 入力値
     * fillColumn="fillColumn"<br>
     * 
     * 期待値
     * 戻り値:void<br>
     * 状態変化:fillColumn=100<br>
     * 
     * セットした値を確認するテストケース<br>
     */
    public void testSetFillColumn01() throws Exception {

        //テスト実行
        tag.setFillColumn(100);

        //テスト結果確認
        assertEquals(100, UTUtil.getPrivateField(tag, "fillColumn"));

    }

    /**
     * testGetFillColumn01。<br>
     * 
     * (正常系)<br>
     * 観点：A<br>
     * 
     * 入力値
     * fillColumn=false
     * 
     * 期待値
     * 戻り値:boolean<br>
     * fillColumn=100<br>
     * 
     * セットした値を確認するテストケース<br>
     */
    public void testGetFillColumn01() throws Exception {

        // テストデータ設定
        UTUtil.setPrivateField(tag, "fillColumn", new Integer(100));

        //テスト結果確認
        assertEquals(100, tag.getFillColumn());

    }

    /**
     * testSetAddBR01。<br>
     * 
     * (正常系)<br>
     * 観点：A<br>
     * 
     * 入力値
     * addBR="true"<br>
     * 
     * 期待値
     * 戻り値:void<br>
     * 状態変化:addBR="true"<br>
     * 
     * セットした値を確認するテストケース<br>
     */
    public void testSetAddBR01() throws Exception {

        //テスト実行
        tag.setAddBR(true);

        //テスト結果確認
        assertTrue((Boolean) UTUtil.getPrivateField(tag, "addBR"));

    }

    /**
     * testGetAddBR01。<br>
     * 
     * (正常系)<br>
     * 観点：A<br>
     * 
     * 入力値
     * addBR="true"
     * 
     * 期待値
     * 戻り値:boolean<br>
     * addBR="true"<br>
     * 
     * ゲットした値を確認するテストケース<br>
     */
    public void testGetAddBR01() throws Exception {

        // テストデータ設定
        UTUtil.setPrivateField(tag, "addBR", new Boolean(true));

        //テスト結果確認
        assertTrue(tag.getAddBR());

    }
    
}
