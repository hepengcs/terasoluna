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
import java.math.BigDecimal;

import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;

import jp.terasoluna.utlib.LogUTUtil;
import jp.terasoluna.utlib.TagUTUtil;
import jp.terasoluna.utlib.UTUtil;
import jp.terasoluna.utlib.exception.Exception_PageContextImpl;
import junit.framework.TestCase;

/**
 * DecimalTag ブラックボックステスト。<br>
 * 
 */
public class DecimalTagTest extends TestCase {

    //テスト対象クラス生成
    DecimalTag tag = null;

    /**
     * Constructor for IterateRowSetTeiTest.
     * @param arg0
     */
    public DecimalTagTest(String arg0) {
        super(arg0);
    }

    /**
     * @see TestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        tag = (DecimalTag) TagUTUtil.create(DecimalTag.class);
    }

    /*
     * @see TestCase#tearDown()
     */
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * testGetId01。<br>
     * 
     * (正常系)<br>
     * 観点：A<br>
     * 
     * 入力値
     * value="id"<br>
     * 
     * 期待値
     * 戻り値:String="id"<br>
     * 
     * "id"が取得出来ることを確認する。<br>
     */
    public void testGetId01() throws Exception {
        //テストデータ設定
        String value = "id";
        UTUtil.setPrivateField(tag, "id", value);

        //テスト実行
        String result = tag.getId();

        //テスト結果確認
        assertEquals(value, result);

    }

    /**
     * testSetId01()。<br>
     *
     * (正常系)<br>
     * 観点：A<br>
     *
     * 入力値：
     *   引数："id"
     * 
     * 期待値：引数にて設定した値
     *
     * 引数で設定した値が設定されていることを確認する。
     *
     * @throws Exception テストコードの本質とかかわりの無い例外
     */
    public void testSetId01() throws Exception {
        //テストデータ設定
        String value = "id";

        //テスト実行
        tag.setId(value);

        //結果確認
        String result = (String) UTUtil.getPrivateField(tag, "id");
        assertEquals(value, result);
    }

    /**
     * testGetFilter01。<br>
     * 
     * (正常系)<br>
     * 観点：A<br>
     * 
     * 入力値
     * value=false<br>
     * 
     * 期待値
     * 戻り値:boolean=false<br>
     * 
     * <code>false</code> が返ることを確認する。<br>
     */
    public void testGetFilter01() throws Exception {
        //テストデータ設定
        Boolean value = new Boolean(false);
        UTUtil.setPrivateField(tag, "filter", value);

        //テスト実行
        boolean result = tag.getFilter();

        //テスト結果確認
        assertFalse(result);

    }

    /**
     * testSetFilter01()。<br>
     *
     * (正常系)<br>
     * 観点：A<br>
     *
     * 入力値：
     *   引数：false
     * 
     * 期待値：引数にて設定した値
     *
     * 引数で設定した値が設定されていることを確認する。
     *
     * @throws Exception テストコードの本質とかかわりの無い例外
     */
    public void testSetFilter01() throws Exception {
        //テストデータ設定
        boolean value = false;

        //テスト実行
        tag.setFilter(value);

        //結果確認
        Boolean result = (Boolean) UTUtil.getPrivateField(tag, "filter");
        assertFalse(result.booleanValue());
    }

    /**
     * testGetIgnore01。<br>
     * 
     * (正常系)<br>
     * 観点：A<br>
     * 
     * 入力値
     * value=true<br>
     * 
     * 期待値
     * 戻り値:boolean=true<br>
     * 
     * <code>true</code> が返ることを確認する。<br>
     */
    public void testGetIgnore01() throws Exception {
        //テストデータ設定
        Boolean value = new Boolean(true);
        UTUtil.setPrivateField(tag, "ignore", value);

        //テスト実行
        boolean result = tag.getIgnore();

        //テスト結果確認
        assertTrue(result);

    }

    /**
     * testSetIgnore01()。<br>
     *
     * (正常系)<br>
     * 観点：A<br>
     *
     * 入力値：
     *   引数：true
     * 
     * 期待値：引数にて設定した値
     *
     * 引数で設定した値が設定されていることを確認する。
     *
     * @throws Exception テストコードの本質とかかわりの無い例外
     */
    public void testSetIgnore01() throws Exception {
        //テストデータ設定
        boolean value = true;

        //テスト実行
        tag.setIgnore(value);

        //結果確認
        Boolean result = (Boolean) UTUtil.getPrivateField(tag, "ignore");
        assertTrue(result.booleanValue());
    }

    /**
     * testGetName01。<br>
     * 
     * (正常系)<br>
     * 観点：A<br>
     * 
     * 入力値
     * value="name"<br>
     * 
     * 期待値
     * 戻り値:String="name"<br>
     * 
     * "name"が取得出来ることを確認する。<br>
     */
    public void testGetName01() throws Exception {
        //テストデータ設定
        String value = "name";
        UTUtil.setPrivateField(tag, "name", value);

        //テスト実行
        String result = tag.getName();

        //テスト結果確認
        assertEquals(value, result);

    }

    /**
     * testSetName01()。<br>
     *
     * (正常系)<br>
     * 観点：A<br>
     *
     * 入力値：
     *   引数："name"
     * 
     * 期待値：引数にて設定した値
     *
     * 引数で設定した値が設定されていることを確認する。
     *
     * @throws Exception テストコードの本質とかかわりの無い例外
     */
    public void testSetName01() throws Exception {
        //テストデータ設定
        String value = "name";

        //テスト実行
        tag.setName(value);

        //結果確認
        String result = (String) UTUtil.getPrivateField(tag, "name");
        assertEquals(value, result);
    }

    /**
     * testGetProperty01。<br>
     * 
     * (正常系)<br>
     * 観点：A<br>
     * 
     * 入力値
     * value="property"<br>
     * 
     * 期待値
     * 戻り値:String="property"<br>
     * 
     * "property"が取得出来ることを確認する。<br>
     */
    public void testGetProperty01() throws Exception {
        //テストデータ設定
        String value = "property";
        UTUtil.setPrivateField(tag, "property", value);

        //テスト実行
        String result = tag.getProperty();

        //テスト結果確認
        assertEquals(value, result);

    }

    /**
     * testSetProperty01()。<br>
     *
     * (正常系)<br>
     * 観点：A<br>
     *
     * 入力値：
     *   引数："property"
     * 
     * 期待値：引数にて設定した値
     *
     * 引数で設定した値が設定されていることを確認する。
     *
     * @throws Exception テストコードの本質とかかわりの無い例外
     */
    public void testSetProperty01() throws Exception {
        //テストデータ設定
        String value = "property";

        //テスト実行
        tag.setProperty(value);

        //結果確認
        String result = (String) UTUtil.getPrivateField(tag, "property");
        assertEquals(value, result);
    }

    /**
     * testGetScope01。<br>
     * 
     * (正常系)<br>
     * 観点：A<br>
     * 
     * 入力値
     * value="scope"<br>
     * 
     * 期待値
     * 戻り値:String="scope"<br>
     * 
     * "scope"が取得出来ることを確認する。<br>
     */
    public void testGetScope01() throws Exception {
        //テストデータ設定
        String value = "scope";
        UTUtil.setPrivateField(tag, "scope", value);

        //テスト実行
        String result = tag.getScope();

        //テスト結果確認
        assertEquals(value, result);

    }

    /**
     * testSetScope01()。<br>
     *
     * (正常系)<br>
     * 観点：A<br>
     *
     * 入力値：
     *   引数："scope"
     * 
     * 期待値：引数にて設定した値
     *
     * 引数で設定した値が設定されていることを確認する。
     *
     * @throws Exception テストコードの本質とかかわりの無い例外
     */
    public void testSetScope01() throws Exception {
        //テストデータ設定
        String value = "scope";

        //テスト実行
        tag.setScope(value);

        //結果確認
        String result = (String) UTUtil.getPrivateField(tag, "scope");
        assertEquals(value, result);
    }

    /**
     * testGetPattern01。<br>
     * 
     * (正常系)<br>
     * 観点：A<br>
     * 
     * 入力値
     * value="pattern"<br>
     * 
     * 期待値
     * 戻り値:String="pattern"<br>
     * 
     * "pattern"が取得出来ることを確認する。<br>
     */
    public void testGetPattern01() throws Exception {
        //テストデータ設定
        String value = "pattern";
        UTUtil.setPrivateField(tag, "pattern", value);

        //テスト実行
        String result = tag.getPattern();

        //テスト結果確認
        assertEquals(value, result);

    }

    /**
     * testSetPattern01()。<br>
     *
     * (正常系)<br>
     * 観点：A<br>
     *
     * 入力値：
     *   引数："pattern"
     * 
     * 期待値：引数にて設定した値
     *
     * 引数で設定した値が設定されていることを確認する。
     *
     * @throws Exception テストコードの本質とかかわりの無い例外
     */
    public void testSetPattern01() throws Exception {
        //テストデータ設定
        String value = "pattern";

        //テスト実行
        tag.setPattern(value);

        //結果確認
        String result = (String) UTUtil.getPrivateField(tag, "pattern");
        assertEquals(value, result);
    }

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
     * "value"が取得出来ることを確認する。<br>
     */
    public void testGetValue01() throws Exception {
        //テストデータ設定
        String value = "value";
        UTUtil.setPrivateField(tag, "value", value);

        //テスト実行
        String result = tag.getValue();

        //テスト結果確認
        assertEquals(value, result);

    }

    /**
     * testSetValueString01()。<br>
     *
     * (正常系)<br>
     * 観点：A<br>
     *
     * 入力値：
     *   引数："value"
     * 
     * 期待値：引数にて設定した値
     *
     * 引数で設定した値が設定されていることを確認する。
     *
     * @throws Exception テストコードの本質とかかわりの無い例外
     */
    public void testSetValueString01() throws Exception {
        //テストデータ設定
        String value = "value";

        //テスト実行
        tag.setValue(value);

        //結果確認
        String result = (String) UTUtil.getPrivateField(tag, "value");
        assertEquals(value, result);
    }

    /**
     * testGetScale01。<br>
     * 
     * (正常系)<br>
     * 観点：A<br>
     * 
     * 入力値
     * value=1<br>
     * 
     * 期待値
     * 戻り値:int=1<br>
     * 
     * 1が取得出来ることを確認する。<br>
     */
    public void testGetScale01() throws Exception {
        //テストデータ設定
        Integer value = new Integer(1);
        UTUtil.setPrivateField(tag, "scale", value);

        //テスト実行
        int result = tag.getScale();

        //テスト結果確認
        assertEquals(value.intValue(), result);

    }

    /**
     * testSetScale01()。<br>
     *
     * (正常系)<br>
     * 観点：A<br>
     *
     * 入力値：
     *   引数：1
     * 
     * 期待値：引数にて設定した値
     *
     * 引数で設定した値が設定されていることを確認する。
     *
     * @throws Exception テストコードの本質とかかわりの無い例外
     */
    public void testSetScale01() throws Exception {
        //テストデータ設定
        int value = 1;

        //テスト実行
        tag.setScale(value);

        //結果確認
        Integer result = (Integer) UTUtil.getPrivateField(tag, "scale");
        assertEquals(value, result.intValue());
    }

    /**
     * testGetRound01。<br>
     * 
     * (正常系)<br>
     * 観点：A<br>
     * 
     * 入力値
     * value="ROUND_HALF_UP"<br>
     * 
     * 期待値
     * 戻り値:"ROUND_HALF_UP"<br>
     * 
     * "ROUND_HALF_UP"が取得出来ることを確認する。<br>
     */
    public void testGetRound01() throws Exception {
        //テストデータ設定
        String value = "ROUND_HALF_UP";
        UTUtil.setPrivateField(tag, "round", value);

        //テスト実行
        String result = tag.getRound();

        //テスト結果確認
        assertEquals(value, result);

    }

    /**
     * testSetRound01()。<br>
     *
     * (正常系)<br>
     * 観点：A<br>
     *
     * 入力値：
     *   引数："ROUND_HALF_UP"
     * 
     * 期待値：引数にて設定した値
     *
     * 引数で設定した値が設定されていることを確認する。
     *
     * @throws Exception テストコードの本質とかかわりの無い例外
     */
    public void testSetRound01() throws Exception {
        //テストデータ設定
        String value = "ROUND_HALF_UP";

        //テスト実行
        tag.setRound(value);

        //結果確認
        String result = (String) UTUtil.getPrivateField(tag, "round");
        assertEquals(value, result);
    }

    /**
     * testDoStartTag01。<br>
     * 
     * (正常系)<br>
     * 観点：A、F<br>
     * 
     * 入力値
     * value=null<br>
     * ignore=true<br>
     * RequestUtils.lookup(pageContext, name, scope)=null<br>
     * 
     * 期待値
     * 戻り値:int=SKIP_BODY<br>
     * 
     * フォーマット対象の値(value)がnull、beenは存在しない場合、
     * SKIP_BODYが返ることを確認する。<br>
     */
    public void testDoStartTag01() throws Exception {
        //テストデータ設定
        // PageContext
        PageContext pc = TagUTUtil.getPageContext(tag);
        String name = "aa";
        pc.setAttribute(name, null);
        // クラス変数設定
        String value = null;
        UTUtil.setPrivateField(tag, "value", value);
        Boolean ignore = new Boolean(true);
        UTUtil.setPrivateField(tag, "ignore", ignore);
        UTUtil.setPrivateField(tag, "name", name);

        //テスト実行
        int result = tag.doStartTag();

        //テスト結果確認
        assertEquals(Tag.SKIP_BODY, result);

    }

    /**
     * testDoStartTag02。<br>
     * 
     * (正常系)<br>
     * 観点：A、F<br>
     * 
     * 入力値
     * value=null<br>
     * ignore=false<br>
     * RequestUtils.lookup(pageContext, name, property, scope)=not null
     * [String（"1.1 "）]<br>
     * scale=0<br>
     * id=not null<br>
     * 
     * 期待値
     * 戻り値:int=SKIP_BODY<br>
     * pageContext=1<br>
     * 
     * フォーマット対象の値(value)がnull、beenは存在し、
     * 要求したプロパティの値が存在する場合、
     * SKIP_BODYが返ることを確認する。<br>
     */
    public void testDoStartTag02() throws Exception {
        //テストデータ設定
        // DecimalTag_BeanStub01インスタンスの生成
        DecimalTag_BeanStub01 bean = new DecimalTag_BeanStub01();
        bean.setField1("1.1 ");

        // PageContext
        PageContext pc = TagUTUtil.getPageContext(tag);
        String name = "aa";
        pc.setAttribute(name, bean);
        // クラス変数設定
        UTUtil.setPrivateField(tag, "value", null);
        UTUtil.setPrivateField(tag, "ignore", new Boolean(false));
        UTUtil.setPrivateField(tag, "name", name);
        UTUtil.setPrivateField(tag, "pattern", "##.##");
        UTUtil.setPrivateField(tag, "id", "id1");
        UTUtil.setPrivateField(tag, "scale", new Integer(0));

        String property = "field1";
        UTUtil.setPrivateField(tag, "property", property);

        //テスト実行
        int result = tag.doStartTag();

        //テスト結果確認
        assertEquals(Tag.SKIP_BODY, result);
        // PageControlInfo
        String id = (String) pc.getAttribute("id1");
        assertEquals("1", id);

    }

    /**
     * testDoStartTag03。<br>
     * 
     * (正常系)<br>
     * 観点：A、F<br>
     * 
     * 入力値
     * value=null<br>
     * ignore=true<br>
     * RequestUtils.lookup(pageContext, name, scope)=not null<br>
     * RequestUtils.lookup(pageContext, name, property, scope)=null<br>
     * 
     * 期待値
     * 戻り値:int=SKIP_BODY<br>
     * 
     * フォーマット対象の値(value)がnull、beenは存在し、
     * 要求したプロパティの値が存在しない場合、
     * SKIP_BODYが返ることを確認する。<br>
     */
    public void testDoStartTag03() throws Exception {
        //テストデータ設定
        //DecimalTag_BeanStub01インスタンスの生成
        DecimalTag_BeanStub01 bean = new DecimalTag_BeanStub01();
        bean.setField1(null);

        // PageContext
        PageContext pc = TagUTUtil.getPageContext(tag);
        String name = "aa";
        pc.setAttribute(name, bean);
        // クラス変数設定
        UTUtil.setPrivateField(tag, "value", null);
        UTUtil.setPrivateField(tag, "ignore", new Boolean(true));
        UTUtil.setPrivateField(tag, "name", name);
        String property = "field1";
        UTUtil.setPrivateField(tag, "property", property);

        //テスト実行
        int result = tag.doStartTag();

        //テスト結果確認
        assertEquals(Tag.SKIP_BODY, result);

    }

    /**
     * testDoStartTag04。<br>
     * 
     * (正常系)<br>
     * 観点：A、F<br>
     * 
     * 入力値
     * value=not null[String（"1.15"）]<br>
     * RequestUtils.lookup(pageContext, name, scope)=not null<br>
     * RequestUtils.lookup(pageContext, name, property, scope)=not null
     * [String（"1.00"）]<br>
     * scale=1<br>
     * id=not null<br>
     * 
     * 期待値
     * 戻り値:int=SKIP_BODY<br>
     * pageContext=idキーとした値が存在する<br>
     * 
     * フォーマット対象の値(value)がnot null（String（"1.15"））、
     * 小数点以下が指定され、idが指定されていた場合、PageContextへ
     * 設定されSKIP_BODYが返ることを確認する。
     * また、beanよりもvalueを優先することを確認する。<br>
     */
    public void testDoStartTag04() throws Exception {
        //テストデータ設定
        // DecimalTag_BeanStub01インスタンスの生成
        DecimalTag_BeanStub01 bean = new DecimalTag_BeanStub01();
        bean.setField1("1.00");

        // PageContext
        PageContext pc = TagUTUtil.getPageContext(tag);
        String name = "aa";
        pc.setAttribute(name, bean);
        // クラス変数設定
        UTUtil.setPrivateField(tag, "value", "1.15");
        UTUtil.setPrivateField(tag, "pattern", "##.##");
        UTUtil.setPrivateField(tag, "id", "id1");
        UTUtil.setPrivateField(tag, "scale", new Integer(1));

        String property = "field1";
        UTUtil.setPrivateField(tag, "property", property);

        //テスト実行
        int result = tag.doStartTag();

        //テスト結果確認
        assertEquals(Tag.SKIP_BODY, result);
        // PageControlInfo
        String id = (String) pc.getAttribute("id1");
        assertEquals("1.2", id);

    }

    /**
     * testDoStartTag05。<br>
     * 
     * (正常系)<br>
     * 観点：A、F<br>
     * 
     * 入力値
     * value=not null[String（"1.15"）]<br>
     * RequestUtils.lookup(pageContext, name, scope)=null<br>
     * scale=-1<br>
     * id=not null<br>
     * 
     * 期待値
     * 戻り値:int=SKIP_BODY<br>
     * pageContext=idキーとした値が存在する<br>
     * 
     * フォーマット対象の値(value)がnot null（String（"1.15"））、
     * 小数点以下が指定され、idが指定されていた場合、PageContextへ
     * 設定されSKIP_BODYが返ることを確認する。<br>
     */
    public void testDoStartTag05() throws Exception {
        // テストデータ設定
        // PageContext
        PageContext pc = TagUTUtil.getPageContext(tag);

        // クラス変数設定
        UTUtil.setPrivateField(tag, "value", "1.15");
        UTUtil.setPrivateField(tag, "pattern", "##.##");
        UTUtil.setPrivateField(tag, "id", "id1");
        UTUtil.setPrivateField(tag, "scale", new Integer(-1));

        String property = "field1";
        UTUtil.setPrivateField(tag, "property", property);

        //テスト実行
        int result = tag.doStartTag();

        //テスト結果確認
        assertEquals(Tag.SKIP_BODY, result);
        // PageControlInfo
        String id = (String) pc.getAttribute("id1");
        assertEquals("1.15", id);

    }

    /**
     * testDoStartTag06。<br>
     * 
     * (正常系)<br>
     * 観点：A、F<br>
     * 
     * 入力値
     * value=not null[String（"1.1 "）]<br>
     * scale=-1<br>
     * id=null<br>
     * filter=true<br>
     * 
     * 期待値
     * 戻り値:int=SKIP_BODY<br>
     * pageContext=レスポンスへoutputがフィルタされ、書き込まれている<br>
     * 
     * フォーマット対象の値(value)がnot null（String（"1.1 "））、
     * 小数点以下が指定され、idが指定されていなく、
     * filterがtrueの場合、レスポンスへフィルタした値が設定され
     * SKIP_BODYが返ることを確認する。<br>
     */
    public void testDoStartTag06() throws Exception {
        // テストデータ設定
        // DecimalTag_BeanStub01インスタンスの生成
        DecimalTag_BeanStub01 bean = new DecimalTag_BeanStub01();
        bean.setField1("1.1 ");

        // PageContext
        PageContext pc = TagUTUtil.getPageContext(tag);
        String name = "aa";
        pc.setAttribute(name, bean);
        // クラス変数設定
        UTUtil.setPrivateField(tag, "value", "1.1 ");
        UTUtil.setPrivateField(tag, "pattern", "##.##");
        UTUtil.setPrivateField(tag, "id", null);
        UTUtil.setPrivateField(tag, "scale", new Integer(-1));

        String property = "field1";
        UTUtil.setPrivateField(tag, "property", property);

        //テスト実行
        int result = tag.doStartTag();

        //テスト結果確認
        assertEquals(Tag.SKIP_BODY, result);

        BufferedReader reader = TagUTUtil.getOutputReader(tag);
        assertEquals("1.1", reader.readLine());

    }

    /**
     * testDoStartTag07。<br>
     * 
     * (正常系)<br>
     * 観点：A、F<br>
     * 
     * 入力値
     * value=not null[String（"1.1 "）]<br>
     * scale=-1<br>
     * id=null<br>
     * filter=false<br>
     * 
     * 期待値
     * 戻り値:int=SKIP_BODY<br>
     * pageContext=レスポンスへoutputが書き込まれている<br>
     * 
     * フォーマット対象の値(value)がnot null（String（"1.1 "））、
     * 小数点以下が指定され、idが指定され、filterがfalseの場合、
     * レスポンスへ値が設定されSKIP_BODYが返ることを確認する。<br>
     */
    public void testDoStartTag07() throws Exception {
        // テストデータ設定
        // DecimalTag_BeanStub01インスタンスの生成
        DecimalTag_BeanStub01 bean = new DecimalTag_BeanStub01();
        bean.setField1("1.1 ");

        // PageContext
        PageContext pc = TagUTUtil.getPageContext(tag);
        String name = "aa";
        pc.setAttribute(name, bean);
        // クラス変数設定
        UTUtil.setPrivateField(tag, "value", "1.1 ");

        UTUtil.setPrivateField(tag, "filter", new Boolean(false));
        UTUtil.setPrivateField(tag, "pattern", "##.##");
        UTUtil.setPrivateField(tag, "id", null);
        UTUtil.setPrivateField(tag, "scale", new Integer(-1));

        String property = "field1";
        UTUtil.setPrivateField(tag, "property", property);

        //テスト実行
        int result = tag.doStartTag();

        //テスト結果確認
        assertEquals(Tag.SKIP_BODY, result);

        BufferedReader reader = TagUTUtil.getOutputReader(tag);
        assertEquals("1.1", reader.readLine());

    }

    /**
     * testDoStartTag08。<br>
     * 
     * (正常系)<br>
     * 観点：A、F<br>
     * 
     * 入力値
     * value=not null[String（" "）]<br>
     * 
     * 期待値
     * 戻り値:int=SKIP_BODY<br>
     * 
     * フォーマット対象の値(value)がnot null（String（" "））の
     * 場合、SKIP_BODYが返ることを確認する。<br>
     */
    public void testDoStartTag08() throws Exception {
        //テストデータ設定
        UTUtil.setPrivateField(tag, "value", "");

        //テスト実行
        int result = tag.doStartTag();

        //テスト結果確認
        assertEquals(Tag.SKIP_BODY, result);

    }

    /**
     * testDoStartTag09。<br>
     * 
     * (異常系)<br>
     * 観点：G<br>
     * 
     * 入力値
     * value=not null[String（"aa "）]<br>
     * scale=1<br>
     * id=not null<br>
     * 
     * 期待値
     * 戻り値:int=Exception<br>
     * 
     * フォーマット対象の値(value)がnot null（String（"aa "））の
     * 場合、NumberFormatExceptionが発生することを確認する。<br>
     */
    public void testDoStartTag09() throws Exception {
        //テストデータ設定
        UTUtil.setPrivateField(tag, "value", "aa");
        UTUtil.setPrivateField(tag, "id", "id1");
        UTUtil.setPrivateField(tag, "scale", new Integer(1));

        //テスト実行
        try {
            tag.doStartTag();
            fail();
        } catch (NumberFormatException e) {
            //テスト結果確認
            return;
        }
    }

    /**
     * testDoStartTag10。<br>
     * 
     * (正常系)<br>
     * 観点：A、F<br>
     * 
     * 入力値
     * value=not null[BigDecimal]<br>
     * scale=*(0)<br>
     * id=*(not null)<br>
     * 
     * 期待値
     * 戻り値:int=SKIP_BODY<br>
     * pageContext=idキーとした値が存在する<br>
     * 
     * フォーマット対象の値(value)がnot null（BigDecimal）の場合、
     * 小数点以下が指定され、idが指定されていない場合、
     * レスポンスへフィルタした値が設定され
     * SKIP_BODYが返ることを確認する。<br>
     */
    public void testDoStartTag10() throws Exception {
        //テストデータ設定
        // DecimalTag_BeanStub01インスタンスの生成
        DecimalTag_BeanStub01 bean = new DecimalTag_BeanStub01();
        BigDecimal value = new BigDecimal("1.01");
        bean.setField2(value);

        // PageContext
        PageContext pc = TagUTUtil.getPageContext(tag);
        String name = "aa";
        pc.setAttribute(name, bean);
        // クラス変数設定
        UTUtil.setPrivateField(tag, "value", null);
        UTUtil.setPrivateField(tag, "ignore", new Boolean(false));
        UTUtil.setPrivateField(tag, "name", name);
        UTUtil.setPrivateField(tag, "pattern", "##.##");
        UTUtil.setPrivateField(tag, "id", "id1");
        UTUtil.setPrivateField(tag, "scale", new Integer(0));

        String property = "field2";
        UTUtil.setPrivateField(tag, "property", property);

        //テスト実行
        int result = tag.doStartTag();

        //テスト結果確認
        assertEquals(Tag.SKIP_BODY, result);
        // PageControlInfo
        String id = (String) pc.getAttribute("id1");
        assertEquals("1", id);

    }

    /**
     * testDoStartTag11。<br>
     * 
     * (正常系)<br>
     * 観点：A、F<br>
     * 
     * 入力値
     * value=not null[int(String、BigDecimal以外)]<br>
     * 
     * 期待値
     * 戻り値:int=SKIP_BODY<br>
     * 
     * フォーマット対象の値(value)がnot null（int（String、BigDecimal以外））の場合、SKIP_BODYが返ることを確認する。<br>
     */
    public void testDoStartTag11() throws Exception {
        //テストデータ設定
        // DecimalTag_BeanStub01インスタンスの生成
        DecimalTag_BeanStub01 bean = new DecimalTag_BeanStub01();
        
        Integer value = new Integer("1");
        bean.setField3(value);

        // PageContext
        PageContext pc = TagUTUtil.getPageContext(tag);
        String name = "aa";
        pc.setAttribute(name, bean);
        // クラス変数設定
        UTUtil.setPrivateField(tag, "value", null);
        UTUtil.setPrivateField(tag, "ignore", new Boolean(false));
        UTUtil.setPrivateField(tag, "name", name);
        UTUtil.setPrivateField(tag, "pattern", "##.##");
        UTUtil.setPrivateField(tag, "id", "id1");
        UTUtil.setPrivateField(tag, "scale", new Integer(0));

        String property = "field3";
        UTUtil.setPrivateField(tag, "property", property);

        //テスト実行
        int result = tag.doStartTag();

        //テスト結果確認
        assertEquals(Tag.SKIP_BODY, result);

    }

    /**
     * testDoStartTag12。<br>
     * 
     * (正常系)<br>
     * 観点：A、F<br>
     * 
     * 入力値
     * value=not null[String（"1.1"）]<br>
     * scale=1<br>
     * id=not null<br>
     * 
     * 期待値
     * 戻り値:void<br>
     *
     * pageContext#setAttribute()でNullPointerExceptionが発生した場合、
     * それがスローされることを確認する。<br>
     */
    public void testDoStartTag12() throws Exception {
        //テストデータ設定
        // DecimalTag_BeanStub01インスタンスの生成
        DecimalTag_BeanStub01 bean = new DecimalTag_BeanStub01();
        bean.setField1("1.1");

        // PageContext
        PageContext pc = TagUTUtil.getPageContext(tag);
        // テスト用ページコンテキストの生成
        Exception_PageContextImpl pc2 =
            new Exception_PageContextImpl(
                pc.getServletConfig(),
                pc.getRequest(),
                pc.getResponse());

        pc2.setNullPointerEx();
        pc2.setTiming(1);
        // 設定を行ったテスト用ページコンテキストをテスト対象タグにセットする。
        tag.setPageContext(pc2);

        String name = "aa";
        pc.setAttribute(name, bean);
        // クラス変数設定
        UTUtil.setPrivateField(tag, "value", "1.1");
        UTUtil.setPrivateField(tag, "pattern", "##.##");
        UTUtil.setPrivateField(tag, "id", "id1");
        UTUtil.setPrivateField(tag, "scale", new Integer(1));

        String property = "field1";
        UTUtil.setPrivateField(tag, "property", property);

        //テスト実行
        try {
            tag.doStartTag();
            fail();
        } catch (NullPointerException e) {
            //テスト結果確認
            return;
        }

    }

    /**
     * testDoStartTag13。<br>
     * 
     * (正常系)<br>
     * 観点：A、F<br>
     * 
     * 入力値
     * value=not null[String（"1.1"）]<br>
     * scale=1<br>
     * id=not null<br>
     * 
     * 期待値
     * 戻り値:void<br>
     *
     * pageContext#setAttribute()でIllegalArgumentExceptionが発生した場合、
     * それがスローされることを確認する。<br>
     */
    public void testDoStartTag13() throws Exception {
        //テストデータ設定
        // DecimalTag_BeanStub01インスタンスの生成
        DecimalTag_BeanStub01 bean = new DecimalTag_BeanStub01();
        bean.setField1("1.1");

        // PageContext
        PageContext pc = TagUTUtil.getPageContext(tag);
        // テスト用ページコンテキストの生成
        Exception_PageContextImpl pc2 =
            new Exception_PageContextImpl(
                pc.getServletConfig(),
                pc.getRequest(),
                pc.getResponse());

        pc2.setIllegalArgumentEx();
        pc2.setTiming(1);
        // 設定を行ったテスト用ページコンテキストをテスト対象タグにセットする。
        tag.setPageContext(pc2);

        String name = "aa";
        pc.setAttribute(name, bean);
        // クラス変数設定
        UTUtil.setPrivateField(tag, "value", "1.1");
        UTUtil.setPrivateField(tag, "pattern", "##.##");
        UTUtil.setPrivateField(tag, "id", "id1");
        UTUtil.setPrivateField(tag, "scale", new Integer(1));

        String property = "field1";
        UTUtil.setPrivateField(tag, "property", property);

        //テスト実行
        try {
            tag.doStartTag();
            fail();
        } catch (IllegalArgumentException e) {
            //テスト結果確認
            return;
        }

    }

    /**
     * testDoStartTag14。<br>
     * 
     * (正常系)<br>
     * 観点：A、F<br>
     * 
     * 入力値
     * value=not null[String（"1.25"）]<br>
     * filter=false
     * pattern=#.###
     * id=null
     * scale=1
     * round=null
     * 
     * 期待値
     * 戻り値:int=SKIP_BODY<br>
     * pageContext=レスポンスへoutputが書き込まれている<br>
     * 
     * フォーマット対象の値(value)がnot null（String（"1.25"））、
     * scale属性が1、round属性が設定されていない場合、
     * レスポンスへ指定した小数点を四捨五入した値が設定され、
     * SKIP_BODYが返ることを確認する。<br>
     */
    public void testDoStartTag14() throws Exception {
        //テストデータ設定
        // DecimalTag_BeanStub01インスタンスの生成
        DecimalTag_BeanStub01 bean = new DecimalTag_BeanStub01();
        bean.setField1("1.25");

        // PageContext
        PageContext pc = TagUTUtil.getPageContext(tag);
        String name = "aa";
        pc.setAttribute(name, bean);
        // クラス変数設定
        UTUtil.setPrivateField(tag, "value", "1.25");

        UTUtil.setPrivateField(tag, "filter", new Boolean(false));
        UTUtil.setPrivateField(tag, "pattern", "##.##");
        UTUtil.setPrivateField(tag, "id", null);
        UTUtil.setPrivateField(tag, "scale", new Integer(1));

        String property = "field1";
        UTUtil.setPrivateField(tag, "property", property);

        //テスト実行
        int result = tag.doStartTag();

        //テスト結果確認
        assertEquals(Tag.SKIP_BODY, result);

        BufferedReader reader = TagUTUtil.getOutputReader(tag);
        assertEquals("1.3", reader.readLine());

    }
    
    /**
     * testDoStartTag15。<br>
     * 
     * (正常系)<br>
     * 観点：A、F<br>
     * 
     * 入力値
     * value=not null[String（"1.23"）]<br>
     * filter=false
     * pattern=##.##
     * id=null
     * scale=1
     * round="rounding test"
     * 
     * 期待値
     * 戻り値:
     * IllegalArgumentException
     * 
     * フォーマット対象の値(value)がnot null（String（"1.23"））、
     * scale属性が1、round属性に丸めモード以外が設定されている場合、
     * IllegalArgumentExceptionが発生することを確認する。
     * 確認する。<br>
     */
    public void testDoStartTag15() throws Exception {
        //テストデータ設定
        // DecimalTag_BeanStub01インスタンスの生成
        DecimalTag_BeanStub01 bean = new DecimalTag_BeanStub01();
        bean.setField1("1.23");

        // PageContext
        PageContext pc = TagUTUtil.getPageContext(tag);
        String name = "aa";
        pc.setAttribute(name, bean);
        // クラス変数設定
        UTUtil.setPrivateField(tag, "value", "1.23");

        UTUtil.setPrivateField(tag, "filter", new Boolean(false));
        UTUtil.setPrivateField(tag, "pattern", "##.##");
        UTUtil.setPrivateField(tag, "id", null);
        UTUtil.setPrivateField(tag, "scale", new Integer(1));
        UTUtil.setPrivateField(tag, "round", "rounding test");

        String property = "field1";
        UTUtil.setPrivateField(tag, "property", property);

        //テスト実行
        try {
            tag.doStartTag();
            fail();
        } catch (IllegalArgumentException e) {
            assertTrue(LogUTUtil.checkError("Please set a rounding mode"));
            assertEquals("Please set a rounding mode", e.getMessage());
        }
    }
    
    /**
     * testDoStartTag16。<br>
     * 
     * (正常系)<br>
     * 観点：A、F<br>
     * 
     * 入力値
     * value=not null[String（"1.29"）]<br>
     * filter=false
     * pattern=##.##
     * id=null
     * scale=1
     * round="ROUND_FLOOR"
     * 
     * 期待値
     * 戻り値:int=SKIP_BODY<br>
     * pageContext=レスポンスへoutputが書き込まれている<br>
     * 
     * フォーマット対象の値(value)がnot null（String（"1.29"））、
     * scale属性が1、round属性に"ROUND_FLOOR"が設定されている場合、
     * レスポンスへ指定した小数点が切り捨てされた値が設定され、
     * SKIP_BODYが返ることを確認する。<br>
     */
    public void testDoStartTag16() throws Exception {
        //テストデータ設定
        // DecimalTag_BeanStub01インスタンスの生成
        DecimalTag_BeanStub01 bean = new DecimalTag_BeanStub01();
        bean.setField1("1.29");

        // PageContext
        PageContext pc = TagUTUtil.getPageContext(tag);
        String name = "aa";
        pc.setAttribute(name, bean);
        // クラス変数設定
        UTUtil.setPrivateField(tag, "value", "1.29");

        UTUtil.setPrivateField(tag, "filter", new Boolean(false));
        UTUtil.setPrivateField(tag, "pattern", "##.##");
        UTUtil.setPrivateField(tag, "id", null);
        UTUtil.setPrivateField(tag, "scale", new Integer(1));
        UTUtil.setPrivateField(tag, "round", "round_floor");

        String property = "field1";
        UTUtil.setPrivateField(tag, "property", property);

        //テスト実行
        int result = tag.doStartTag();

        //テスト結果確認
        assertEquals(Tag.SKIP_BODY, result);

        BufferedReader reader = TagUTUtil.getOutputReader(tag);
        assertEquals("1.2", reader.readLine());
    }
    
    /**
     * testDoStartTag17。<br>
     * 
     * (正常系)<br>
     * 観点：A、F<br>
     * 
     * 入力値
     * value=not null[String（"1.21"）]<br>
     * filter=false
     * pattern=##.##
     * id=null
     * scale=1
     * round=ROUND_CEILING
     * 
     * 期待値
     * 戻り値:int=SKIP_BODY<br>
     * pageContext=レスポンスへoutputが書き込まれている<br>
     * 
     * フォーマット対象の値(value)がnot null（String（"1.21"））、
     * scale属性が1、round属性に"ROUND_CEILING"が設定されている場合、
     * レスポンスへ指定した小数点が切り上げされた値が設定され、
     * SKIP_BODYが返ることを確認する。<br>
     */
    public void testDoStartTag17() throws Exception {
        //テストデータ設定
        // DecimalTag_BeanStub01インスタンスの生成
        DecimalTag_BeanStub01 bean = new DecimalTag_BeanStub01();
        bean.setField1("1.21");

        // PageContext
        PageContext pc = TagUTUtil.getPageContext(tag);
        String name = "aa";
        pc.setAttribute(name, bean);
        // クラス変数設定
        UTUtil.setPrivateField(tag, "value", "1.21");

        UTUtil.setPrivateField(tag, "filter", new Boolean(false));
        UTUtil.setPrivateField(tag, "pattern", "##.##");
        UTUtil.setPrivateField(tag, "id", null);
        UTUtil.setPrivateField(tag, "scale", new Integer(1));
        UTUtil.setPrivateField(tag, "round", "round_ceiling");

        String property = "field1";
        UTUtil.setPrivateField(tag, "property", property);

        //テスト実行
        int result = tag.doStartTag();

        //テスト結果確認
        assertEquals(Tag.SKIP_BODY, result);

        BufferedReader reader = TagUTUtil.getOutputReader(tag);
        assertEquals("1.3", reader.readLine());
    }
    
    /**
     * testDoStartTag18。<br>
     * 
     * (正常系)<br>
     * 観点：A、F<br>
     * 
     * 入力値
     * value=not null[String（"1.24"）]<br>
     * filter=false
     * pattern=##.##
     * id=null
     * scale=1
     * round="ROUND_HALF_UP"
     * 
     * 期待値
     * 戻り値:int=SKIP_BODY<br>
     * pageContext=レスポンスへoutputが書き込まれている<br>
     * 
     * フォーマット対象の値(value)がnot null（String（"1.24"））、
     * scale属性が1、round属性に"ROUND_HALF_UP"が設定されている場合、
     * レスポンスへ指定した小数点を四捨五入した値が
     * 設定されSKIP_BODYが返ることを確認する。<br>
     */
    public void testDoStartTag18() throws Exception {
        //テストデータ設定
        // DecimalTag_BeanStub01インスタンスの生成
        DecimalTag_BeanStub01 bean = new DecimalTag_BeanStub01();
        bean.setField1("1.24");

        // PageContext
        PageContext pc = TagUTUtil.getPageContext(tag);
        String name = "aa";
        pc.setAttribute(name, bean);
        // クラス変数設定
        UTUtil.setPrivateField(tag, "value", "1.24");

        UTUtil.setPrivateField(tag, "filter", new Boolean(false));
        UTUtil.setPrivateField(tag, "pattern", "##.##");
        UTUtil.setPrivateField(tag, "id", null);
        UTUtil.setPrivateField(tag, "scale", new Integer(1));
        UTUtil.setPrivateField(tag, "round", "round_half_up");

        String property = "field1";
        UTUtil.setPrivateField(tag, "property", property);

        //テスト実行
        int result = tag.doStartTag();

        //テスト結果確認
        assertEquals(Tag.SKIP_BODY, result);

        BufferedReader reader = TagUTUtil.getOutputReader(tag);
        assertEquals("1.2", reader.readLine());
    }
    
    /**
     * testRelease01。<br>
     * 
     * (正常系)<br>
     * 観点：A<br>
     * 
     * 入力値
     * id=*<br>
     * filter=*<br>
     * ignore=*<br>
     * name=*<br>
     * property=*<br>
     * scope=*<br>
     * pattern=*<br>
     * value=*<br>
     * scale=*<br>
     * 
     * 期待値
     * 戻り値:void<br>
     * id=null<br>
     * filter=true<br>
     * ignore=false<br>
     * name=null<br>
     * property=null<br>
     * scope=null<br>
     * pattern=null<br>
     * value=null<br>
     * scale=-1<br>
     * 
     * 前提条件として設定した各値が、実行時に各条件値が初期化されることを確認する<br>
     */
    public void testRelease01() throws Exception {
        //テストデータ設定
        UTUtil.setPrivateField(tag, "id", "id1");
        UTUtil.setPrivateField(tag, "filter", new Boolean(false));
        UTUtil.setPrivateField(tag, "ignore", new Boolean(false));
        UTUtil.setPrivateField(tag, "name", "aa");
        UTUtil.setPrivateField(tag, "property", "field1");
        UTUtil.setPrivateField(tag, "scope", "scope");
        UTUtil.setPrivateField(tag, "pattern", "##.##");
        UTUtil.setPrivateField(tag, "value", "aa");
        UTUtil.setPrivateField(tag, "scale", new Integer(0));

        //テスト実行
        tag.release();

        //テスト結果確認
        String id2 = (String) UTUtil.getPrivateField(tag, "id");
        Boolean filter2 = (Boolean) UTUtil.getPrivateField(tag, "filter");
        Boolean ignore2 = (Boolean) UTUtil.getPrivateField(tag, "ignore");
        String name2 = (String) UTUtil.getPrivateField(tag, "name");
        String property2 = (String) UTUtil.getPrivateField(tag, "property");
        String scope2 = (String) UTUtil.getPrivateField(tag, "scope");
        String pattern2 = (String) UTUtil.getPrivateField(tag, "pattern");
        String value2 = (String) UTUtil.getPrivateField(tag, "value");
        Integer scale2 = (Integer) UTUtil.getPrivateField(tag, "scale");

        assertNull(id2);
        assertTrue(filter2.booleanValue());
        assertFalse(ignore2.booleanValue());
        assertNull(name2);
        assertNull(property2);
        assertNull(scope2);
        assertNull(pattern2);
        assertNull(value2);
        assertEquals(new Integer(-1), scale2);

    }

}
