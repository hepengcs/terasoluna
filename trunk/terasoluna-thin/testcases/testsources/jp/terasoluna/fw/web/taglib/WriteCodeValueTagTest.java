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

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.Tag;

import jp.terasoluna.utlib.LogUTUtil;
import jp.terasoluna.utlib.MockHttpServletRequest;
import jp.terasoluna.utlib.UTUtil;
import jp.terasoluna.utlib.spring.SpringTestCase;

/**
 * {@link jp.terasoluna.fw.web.taglib.WriteCodeValue} クラスのブラックボックステスト。
 *
 * <p>
 * <h4>【クラスの概要】</h4>
 * writeCodeValue タグの実装クラス。
 * <p>
 *
 * @see jp.terasoluna.fw.web.taglib.WriteCodeValue
 */
public class WriteCodeValueTagTest extends SpringTestCase {

    /**
     * テスト対象インスタンス。
     */
    private WriteCodeValueTag tag = null;


    @Override
    protected void doOnSetUp() throws Exception {
        this.tag = new WriteCodeValueTag();
    }

    @Override
    protected String[] getConfigLocations() {
        return new String[] { "jp/terasoluna/fw/web/taglib/WriteCodeValueTagTest.xml" };
    }

    /**
     * testDoStartTag01()
     * <br><br>
     *
     * (異常系)
     * <br>
     * 観点：G
     * <br><br>
     * 入力値：(状態) codeList:""<br>
     *
     * <br>
     * 期待値：(状態変化) out.print:呼び出されない<br>
     *         (状態変化) 例外:JspTagException<br>
     *                    メッセージ："codeList is required."<br>
     *         (状態変化) ログ:ログレベル：エラー<br>
     *                    "codeList is required."<br>
     *
     * <br>
     * codeList属性の値が空文字のとき、JspTagExceptionが発生することを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testDoStartTag01() throws Exception {
        // 前処理
        UTUtil.setPrivateField(tag, "codeList", "");
        WriteCodeValueTag_PageContext context =
            new WriteCodeValueTag_PageContext();
        WriteCodeValueTag_JspWriterStub01 out =
            new WriteCodeValueTag_JspWriterStub01(0, false);
        context.out = out;
        tag.setPageContext(context);

        // テスト実施
        // 判定
        try {
            tag.doStartTag();
            fail();
        } catch (JspTagException e) {
            String message = "codeList is required.";
            assertEquals(message, e.getMessage());
            assertTrue(LogUTUtil.checkError(message));
            assertFalse(out.isCalled);
        }

    }

    /**
     * testDoStartTag02()
     * <br><br>
     *
     * (異常系)
     * <br>
     * 観点：G
     * <br><br>
     * 入力値：(状態) codeList:null<br>
     *
     * <br>
     * 期待値：(状態変化) out.print:呼び出されない<br>
     *         (状態変化) 例外:JspTagException<br>
     *                    メッセージ："codeList is required."<br>
     *         (状態変化) ログ:ログレベル：エラー<br>
     *                    "codeList is required."<br>
     *
     * <br>
     * codeList属性の値がnullのとき、JspTagExceptionが発生することを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testDoStartTag02() throws Exception {
        // 前処理
        UTUtil.setPrivateField(tag, "codeList", null);
        WriteCodeValueTag_PageContext context =
            new WriteCodeValueTag_PageContext();
        WriteCodeValueTag_JspWriterStub01 out =
            new WriteCodeValueTag_JspWriterStub01(0, false);
        context.out = out;
        tag.setPageContext(context);

        // テスト実施
        // 判定
        try {
            tag.doStartTag();
            fail();
        } catch (JspTagException e) {
            String message = "codeList is required.";
            assertEquals(message, e.getMessage());
            assertTrue(LogUTUtil.checkError(message));
            assertFalse(out.isCalled);
        }

    }

    /**
     * testDoStartTag03()
     * <br><br>
     *
     * (異常系)
     * <br>
     * 観点：G
     * <br><br>
     * 入力値：(状態) codeList:"hoge"<br>
     *         (状態) key:null<br>
     *         (状態) name:null<br>
     *
     * <br>
     * 期待値：(状態変化) out.print:呼び出されない<br>
     *         (状態変化) 例外:JspTagException<br>
     *                    メッセージ："key and name is required."<br>
     *         (状態変化) ログ:ログレベル：エラー<br>
     *                    "key and name is required."<br>
     *
     * <br>
     * key属性、name属性がともにnullの場合、JspTagExceptionが発生することを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testDoStartTag03() throws Exception {
        // 前処理
        UTUtil.setPrivateField(tag, "codeList", "hoge");
        UTUtil.setPrivateField(tag, "key", null);
        UTUtil.setPrivateField(tag, "name", null);
        WriteCodeValueTag_PageContext context =
            new WriteCodeValueTag_PageContext();
        WriteCodeValueTag_JspWriterStub01 out =
            new WriteCodeValueTag_JspWriterStub01(0, false);
        context.out = out;
        tag.setPageContext(context);

        // テスト実施
        // 判定
        try {
            tag.doStartTag();
            fail();
        } catch (JspTagException e) {
            String message = "key and name is required.";
            assertEquals(message, e.getMessage());
            assertTrue(LogUTUtil.checkError(message));
            assertFalse(out.isCalled);
        }

    }

    /**
     * testDoStartTag04()
     * <br><br>
     *
     * (異常系)
     * <br>
     * 観点：G
     * <br><br>
     * 入力値：(状態) codeList:"hoge"<br>
     *         (状態) key:null<br>
     *         (状態) name:"hogeBean"<br>
     *         (状態) scope:session<br>
     *         (状態) scope属性で指定されたスコープ:sessionスコープに"hogeBean"という名前のオブジェクトが存在しない。<br>
     *
     * <br>
     * 期待値：(状態変化) out.print:呼び出されない<br>
     *         (状態変化) 例外:JspTagException<br>
     *                    メッセージ："bean id:hogeBean is not defined."<br>
     *         (状態変化) ログ:ログレベル：エラー<br>
     *                    "bean id:hogeBean is not defined."<br>
     *
     * <br>
     * name属性に指定されたBeanが存在しない場合、JspTagExceptionが発生することを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testDoStartTag04() throws Exception {
        // 前処理
        UTUtil.setPrivateField(tag, "codeList", "hoge");
        UTUtil.setPrivateField(tag, "key", null);
        UTUtil.setPrivateField(tag, "name", "hogeBean");
        UTUtil.setPrivateField(tag, "scope", "session");
        WriteCodeValueTag_PageContext context =
            new WriteCodeValueTag_PageContext();
        WriteCodeValueTag_JspWriterStub01 out =
            new WriteCodeValueTag_JspWriterStub01(0, false);
        context.out = out;
        tag.setPageContext(context);

        // テスト実施
        // 判定
        try {
            tag.doStartTag();
            fail();
        } catch (JspTagException e) {
            String message = "bean id:hogeBean is not defined.";
            assertEquals(message, e.getMessage());
            assertTrue(LogUTUtil.checkError(message));
            assertFalse(out.isCalled);
        }

    }

    /**
     * testDoStartTag05()
     * <br><br>
     *
     * (異常系)
     * <br>
     * 観点：G
     * <br><br>
     * 入力値：(状態) codeList:"hoge"<br>
     *         (状態) key:null<br>
     *         (状態) name:"hogeBean"<br>
     *         (状態) property:"hogeProperty"<br>
     *         (状態) scope:request<br>
     *         (状態) scope属性で指定されたスコープ:requestスコープに"hogeBean"が存在するが、hogeBeanにhogePropertyが存在しない。<br>
     *
     * <br>
     * 期待値：(状態変化) out.print:呼び出されない<br>
     *         (状態変化) 例外:JspTagException<br>
     *                    メッセージ："Cannot get property[hogeBean.hogeProperty]."<br>
     *         (状態変化) ログ:ログレベル：エラー<br>
     *                    "Cannot get property[hogeBean.hogeProperty]."<br>
     *
     * <br>
     * property属性に指定されたプロパティが取得できない場合、JspTagExceptionが発生することを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testDoStartTag05() throws Exception {
        // 前処理
        UTUtil.setPrivateField(tag, "codeList", "hoge");
        UTUtil.setPrivateField(tag, "key", null);
        UTUtil.setPrivateField(tag, "name", "hogeBean");
        UTUtil.setPrivateField(tag, "property", "hogeProperty");
        UTUtil.setPrivateField(tag, "scope", "request");
        HttpServletRequest req = new MockHttpServletRequest();
        req.setAttribute("hogeBean", "hogeBeanString");
        WriteCodeValueTag_PageContext context =
            new WriteCodeValueTag_PageContext(null, req);
        WriteCodeValueTag_JspWriterStub01 out =
            new WriteCodeValueTag_JspWriterStub01(0, false);
        context.out = out;
        tag.setPageContext(context);

        // テスト実施
        // 判定
        try {
            tag.doStartTag();
            fail();
        } catch (JspTagException e) {
            String message = "Cannot get property[hogeBean.hogeProperty].";
            assertEquals(message, e.getMessage());
            assertTrue(LogUTUtil.checkError(message));
            assertFalse(out.isCalled);
        }

    }

    /**
     * testDoStartTag06()
     * <br><br>
     *
     * (異常系)
     * <br>
     * 観点：G
     * <br><br>
     * 入力値：(状態) codeList:"hoge"<br>
     *         (状態) key:null<br>
     *         (状態) name:"hogeBean"<br>
     *         (状態) property:"hogeProperty"<br>
     *         (状態) scope:page<br>
     *         (状態) scope属性で指定されたスコープ:pageスコープの"hogeBean"のhogePropertyの値が"001"<br>
     *         (状態) codeList属性で指定された名前のcodeListLoader:ApplicationContextから取得できない<br>
     *
     * <br>
     * 期待値：(状態変化) out.print:呼び出されない<br>
     *         (状態変化) 例外:JspTagException<br>
     *                    メッセージ："CodeListLoader:hoge is not defined."<br>
     *         (状態変化) ログ:ログレベル：エラー<br>
     *                    "CodeListLoader:hoge is not defined."<br>
     *
     * <br>
     * codeListLoaderインスタンスが取得できない場合、JspTagExceptionが発生することを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testDoStartTag06() throws Exception {
        // 前処理
        UTUtil.setPrivateField(tag, "codeList", "hoge");
        UTUtil.setPrivateField(tag, "key", null);
        UTUtil.setPrivateField(tag, "name", "hogeBean");
        UTUtil.setPrivateField(tag, "property", "hogeProperty");
        UTUtil.setPrivateField(tag, "scope", "page");
        WriteCodeValueTag_PageContext context =
            new WriteCodeValueTag_PageContext();
        WriteCodeValueTag_JspWriterStub01 out =
            new WriteCodeValueTag_JspWriterStub01(0, false);
        context.out = out;
        tag.setPageContext(context);
        WriteCodeValueTag_JavaBeanStub01 hogeBean =
            new WriteCodeValueTag_JavaBeanStub01();
        hogeBean.setHogeProperty("001");
        context.setAttribute("hogeBean", hogeBean);
        UTUtil.setPrivateField(context, "servletContext", servletContext);

        // テスト実施
        // 判定
        try {
            tag.doStartTag();
            fail();
        } catch (JspTagException e) {
            String message = "CodeListLoader:hoge is not defined.";
            assertEquals(message, e.getMessage());
            assertTrue(LogUTUtil.checkError(message));
            assertFalse(out.isCalled);
        }

    }

    /**
     * testDoStartTag07()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：C,F
     * <br><br>
     * 入力値：(状態) codeList:"hoge1"<br>
     *         (状態) key:null<br>
     *         (状態) name:"hogeBean"<br>
     *         (状態) property:"hogeProperty"<br>
     *         (状態) scope:page<br>
     *         (状態) scope属性で指定されたスコープ:pageスコープの"hogeBean"のhogePropertyの値が"001"<br>
     *         (状態) codeList属性で指定された名前のcodeListLoader:getCodeBeansの結果がnull<br>
     *
     * <br>
     * 期待値：(戻り値) int:1<br>
     *         (状態変化) out.print:呼び出されない<br>
     *         (状態変化) ログ:ログレベル：警告<br>
     *                    "Codebean is null. CodeListLoader(bean id:hoge1)"<br>
     *
     * <br>
     * codeListLoaderからCodeBeanが取得できない場合、警告ログを出力して処理終了することを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testDoStartTag07() throws Exception {
        // 前処理
        UTUtil.setPrivateField(tag, "codeList", "hoge1");
        UTUtil.setPrivateField(tag, "key", null);
        UTUtil.setPrivateField(tag, "name", "hogeBean");
        UTUtil.setPrivateField(tag, "property", "hogeProperty");
        UTUtil.setPrivateField(tag, "scope", "page");
        WriteCodeValueTag_PageContext context =
            new WriteCodeValueTag_PageContext();
        WriteCodeValueTag_JspWriterStub01 out =
            new WriteCodeValueTag_JspWriterStub01(0, false);
        context.out = out;
        tag.setPageContext(context);
        WriteCodeValueTag_JavaBeanStub01 hogeBean =
            new WriteCodeValueTag_JavaBeanStub01();
        hogeBean.setHogeProperty("001");
        context.setAttribute("hogeBean", hogeBean);
        UTUtil.setPrivateField(context, "servletContext", servletContext);

        // テスト実施
        // 判定
        int result = tag.doStartTag();
        assertEquals(1, result);
        String message = "Codebean is null. CodeListLoader(bean id:hoge1)";
        assertTrue(LogUTUtil.checkWarn(message));
        assertFalse(out.isCalled);

    }

    /**
     * testDoStartTag08()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(状態) codeList:"hoge2"<br>
     *         (状態) key:null<br>
     *         (状態) name:"hogeBean"<br>
     *         (状態) property:"hogeProperty"<br>
     *         (状態) scope:page<br>
     *         (状態) scope属性で指定されたスコープ:pageスコープの"hogeBean"のhogePropertyの値が"001"<br>
     *         (状態) codeList属性で指定された名前のcodeListLoader:CodeBean[] {<br>
     *                 "000"="コード0"<br>
     *                }<br>
     *
     * <br>
     * 期待値：(戻り値) int:1<br>
     *         (状態変化) out.print:呼び出されない<br>
     *
     * <br>
     * コード値に一致するCodeBeanが存在しない場合、何も出力せずに処理終了することを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testDoStartTag08() throws Exception {
        // 前処理
        UTUtil.setPrivateField(tag, "codeList", "hoge2");
        UTUtil.setPrivateField(tag, "key", null);
        UTUtil.setPrivateField(tag, "name", "hogeBean");
        UTUtil.setPrivateField(tag, "property", "hogeProperty");
        UTUtil.setPrivateField(tag, "scope", "page");
        WriteCodeValueTag_PageContext context =
            new WriteCodeValueTag_PageContext();
        WriteCodeValueTag_JspWriterStub01 out =
            new WriteCodeValueTag_JspWriterStub01(0, false);
        context.out = out;
        tag.setPageContext(context);
        WriteCodeValueTag_JavaBeanStub01 hogeBean =
            new WriteCodeValueTag_JavaBeanStub01();
        hogeBean.setHogeProperty("001");
        context.setAttribute("hogeBean", hogeBean);
        UTUtil.setPrivateField(context, "servletContext", servletContext);

        // テスト実施
        // 判定
        int result = tag.doStartTag();
        assertEquals(1, result);
        assertFalse(out.isCalled);

    }

    /**
     * testDoStartTag09()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(状態) codeList:"hoge3"<br>
     *         (状態) key:"001"<br>
     *         (状態) codeList属性で指定された名前のcodeListLoader:CodeBean[] {<br>
     *                 "000"="コード0",<br>
     *                 "002"="コード2",<br>
     *                 "003"="コード3"<br>
     *                }<br>
     *
     * <br>
     * 期待値：(戻り値) int:1<br>
     *         (状態変化) out.print:呼び出されない<br>
     *
     * <br>
     * コード値に一致するCodeBeanが存在しない場合、何も出力せずに処理終了することを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testDoStartTag09() throws Exception {
        // 前処理
        UTUtil.setPrivateField(tag, "codeList", "hoge3");
        UTUtil.setPrivateField(tag, "key", "001");
        WriteCodeValueTag_PageContext context =
            new WriteCodeValueTag_PageContext();
        WriteCodeValueTag_JspWriterStub01 out =
            new WriteCodeValueTag_JspWriterStub01(0, false);
        context.out = out;
        tag.setPageContext(context);
        UTUtil.setPrivateField(context, "servletContext", servletContext);

        // テスト実施
        // 判定
        int result = tag.doStartTag();
        assertEquals(1, result);
        assertFalse(out.isCalled);

    }

    /**
     * testDoStartTag10()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(状態) codeList:"hoge4"<br>
     *         (状態) key:null<br>
     *         (状態) name:"hogeBean"<br>
     *         (状態) property:"hogeProperty"<br>
     *         (状態) scope:page<br>
     *         (状態) scope属性で指定されたスコープ:pageスコープの"hogeBean"のhogePropertyの値が"001"<br>
     *         (状態) codeList属性で指定された名前のcodeListLoader:CodeBean[] {<br>
     *                 "001"="コード1"<br>
     *                }<br>
     *
     * <br>
     * 期待値：(戻り値) int:1<br>
     *         (状態変化) out.print:引数"コード1"で呼び出される。<br>
     *
     * <br>
     * コード値に一致するCodeBeanが存在する場合、その値がページに出力されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testDoStartTag10() throws Exception {
        // 前処理
        UTUtil.setPrivateField(tag, "codeList", "hoge4");
        UTUtil.setPrivateField(tag, "key", null);
        UTUtil.setPrivateField(tag, "name", "hogeBean");
        UTUtil.setPrivateField(tag, "property", "hogeProperty");
        UTUtil.setPrivateField(tag, "scope", "page");
        WriteCodeValueTag_PageContext context =
            new WriteCodeValueTag_PageContext();
        WriteCodeValueTag_JspWriterStub01 out =
            new WriteCodeValueTag_JspWriterStub01(0, false);
        context.out = out;
        tag.setPageContext(context);
        WriteCodeValueTag_JavaBeanStub01 hogeBean =
            new WriteCodeValueTag_JavaBeanStub01();
        hogeBean.setHogeProperty("001");
        context.setAttribute("hogeBean", hogeBean);
        UTUtil.setPrivateField(context, "servletContext", servletContext);

        // テスト実施
        // 判定
        int result = tag.doStartTag();
        assertEquals(1, result);
        assertTrue(out.isCalled);
        assertEquals("コード1", out.result);

    }

    /**
     * testDoStartTag11()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(状態) codeList:"hoge5"<br>
     *         (状態) key:"001"<br>
     *         (状態) codeList属性で指定された名前のcodeListLoader:CodeBean[] {<br>
     *                 "000"="コード0",<br>
     *                 "001"="コード1",<br>
     *                 "002"="コード2"<br>
     *                }<br>
     *
     * <br>
     * 期待値：(戻り値) int:1<br>
     *         (状態変化) out.print:引数"コード1"で呼び出される。<br>
     *
     * <br>
     * コード値に一致するCodeBeanが存在する場合、その値がページに出力されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testDoStartTag11() throws Exception {
        // 前処理
        UTUtil.setPrivateField(tag, "codeList", "hoge5");
        UTUtil.setPrivateField(tag, "key", "001");
        WriteCodeValueTag_PageContext context =
            new WriteCodeValueTag_PageContext();
        WriteCodeValueTag_JspWriterStub01 out =
            new WriteCodeValueTag_JspWriterStub01(0, false);
        context.out = out;
        tag.setPageContext(context);
        UTUtil.setPrivateField(context, "servletContext", servletContext);

        // テスト実施
        // 判定
        int result = tag.doStartTag();
        assertEquals(1, result);
        assertTrue(out.isCalled);
        assertEquals("コード1", out.result);

    }

    /**
     * testDoStartTag12()
     * <br><br>
     *
     * (異常系)
     * <br>
     * 観点：G
     * <br><br>
     * 入力値：(状態) codeList:"hoge5"<br>
     *         (状態) key:"001"<br>
     *         (状態) codeList属性で指定された名前のcodeListLoader:CodeBean[] {<br>
     *                 "000"="コード0",<br>
     *                 "001"="コード1",<br>
     *                 "002"="コード2"<br>
     *                }<br>
     *         (状態) out.print:IOException発生<br>
     *
     * <br>
     * 期待値：(状態変化) out.print:引数"コード1"で呼び出される。<br>
     *         (状態変化) 例外:JspTagException<br>
     *                    ラップした例外：IOException<br>
     *         (状態変化) ログ:ログレベル：エラー<br>
     *                    ""<br>
     *                    例外：IOException<br>
     *
     * <br>
     * out.printでIOExceptionが発生する場合、JspTagExceptionがスローされることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testDoStartTag12() throws Exception {
        // 前処理
        UTUtil.setPrivateField(tag, "codeList", "hoge5");
        UTUtil.setPrivateField(tag, "key", "001");
        WriteCodeValueTag_PageContext context =
            new WriteCodeValueTag_PageContext();
        WriteCodeValueTag_JspWriterStub02 out =
            new WriteCodeValueTag_JspWriterStub02(0, false);
        context.out = out;
        tag.setPageContext(context);
        UTUtil.setPrivateField(context, "servletContext", servletContext);

        // テスト実施
        // 判定
        try {
            tag.doStartTag();
            fail();
        } catch (JspTagException e) {
            assertTrue(e.getRootCause() instanceof IOException);
            assertTrue(out.isCalled);
            assertEquals("コード1", out.result);
            assertTrue(LogUTUtil.checkError("", e.getRootCause()));
        }

    }

    /**
     * testDoStartTag13()
     * <br><br>
     *
     * (異常系)
     * <br>
     * 観点：G
     * <br><br>
     * 入力値：(状態) codeList:"hoge6"<br>
     *         (状態) key:"001"<br>
     *         (状態) codeList属性で指定された名前のcodeListLoader:codeListLoaderインスタンスではない<br>
     *         (状態) out.print:-<br>
     *
     * <br>
     * 期待値：(状態変化) out.print:呼び出されない。<br>
     *         (状態変化) 例外:JspTagException<br>
     *                    メッセージ："bean id:hoge6 is not instance of CodeListLoader."
     *                    ラップした例外：ClassCastException<br>
     *         (状態変化) ログ:ログレベル：エラー<br>
     *                    メッセージ："bean id:hoge6 is not instance of CodeListLoader."
     *
     * <br>
     * 指定された名前で取得したcodeListLoaderがCodeListLoaderインスタンスではない場合
     * JspTagExceptionがスローされることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testDoStartTag13() throws Exception {
        // 前処理
        UTUtil.setPrivateField(tag, "codeList", "hoge6");
        UTUtil.setPrivateField(tag, "key", "001");
        WriteCodeValueTag_PageContext context =
            new WriteCodeValueTag_PageContext();
        WriteCodeValueTag_JspWriterStub02 out =
            new WriteCodeValueTag_JspWriterStub02(0, false);
        context.out = out;
        tag.setPageContext(context);
        UTUtil.setPrivateField(context, "servletContext", servletContext);

        // テスト実施
        // 判定
        try {
            tag.doStartTag();
            fail();
        } catch (JspTagException e) {
            String message = "bean id:hoge6 is not instance of CodeListLoader.";
            assertTrue(e.getRootCause() instanceof ClassCastException);
            assertEquals(message, e.getMessage());
            assertFalse(out.isCalled);
            assertTrue(LogUTUtil.checkError(message));
        }

    }

    /**
     * testDoEndTag01()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：
     * <br>
     * 期待値：(戻り値) int:TagSupport.EVAL_PAGE<br>
     *
     * <br>
     * TagSupport.EVAL_PAGEが返却されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testDoEndTag01() throws Exception {
        assertEquals(Tag.EVAL_PAGE, tag.doEndTag());
    }

    /**
     * testRelease01()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(状態) codeList:"codeList"<br>
     *         (状態) key:"key"<br>
     *         (状態) name:"name"<br>
     *         (状態) property:"property"<br>
     *         (状態) scope:"scope"<br>
     *
     * <br>
     * 期待値：(状態変化) codeList:null<br>
     *         (状態変化) key:null<br>
     *         (状態変化) name:null<br>
     *         (状態変化) property:null<br>
     *         (状態変化) scope:null<br>
     *
     * <br>
     * 全ての属性値がnullに初期化されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testRelease01() throws Exception {
        // 前処理
        UTUtil.setPrivateField(tag, "codeList", "codeList");
        UTUtil.setPrivateField(tag, "key", "key");
        UTUtil.setPrivateField(tag, "name", "name");
        UTUtil.setPrivateField(tag, "property", "property");
        UTUtil.setPrivateField(tag, "scope", "scope");

        // テスト実施
        tag.release();

        // 判定
        assertNull(UTUtil.getPrivateField(tag, "codeList"));
        assertNull(UTUtil.getPrivateField(tag, "key"));
        assertNull(UTUtil.getPrivateField(tag, "name"));
        assertNull(UTUtil.getPrivateField(tag, "property"));
        assertNull(UTUtil.getPrivateField(tag, "scope"));
    }

    /**
     * testSetCodeList01()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) codeList:"codeList"<br>
     *         (状態) codeList:null<br>
     *
     * <br>
     * 期待値：(状態変化) codeList:"codeList"<br>
     *
     * <br>
     * 引数の値がcodeListに格納されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetCodeList01() throws Exception {
        // 前処理
        UTUtil.setPrivateField(tag, "codeList", null);

        // テスト実施
        tag.setCodeList("codeList");

        // 判定
        assertEquals("codeList", UTUtil.getPrivateField(tag, "codeList"));
    }

    /**
     * testSetName01()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) name:"name"<br>
     *         (状態) name:null<br>
     *
     * <br>
     * 期待値：(状態変化) name:"name"<br>
     *
     * <br>
     * 引数の値がnameに格納されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetName01() throws Exception {
        // 前処理
        UTUtil.setPrivateField(tag, "name", null);

        // テスト実施
        tag.setName("name");

        // 判定
        assertEquals("name", UTUtil.getPrivateField(tag, "name"));
    }

    /**
     * testSetProperty01()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) property:"property"<br>
     *         (状態) property:null<br>
     *
     * <br>
     * 期待値：(状態変化) property:"property"<br>
     *
     * <br>
     * 引数の値がpropertyに格納されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetProperty01() throws Exception {
        // 前処理
        UTUtil.setPrivateField(tag, "property", null);

        // テスト実施
        tag.setProperty("property");

        // 判定
        assertEquals("property", UTUtil.getPrivateField(tag, "property"));
    }

    /**
     * testSetScope01()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) scope:"scope"<br>
     *         (状態) scope:null<br>
     *
     * <br>
     * 期待値：(状態変化) scope:"scope"<br>
     *
     * <br>
     * 引数の値がscopeに格納されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetScope01() throws Exception {
        // 前処理
        UTUtil.setPrivateField(tag, "scope", null);

        // テスト実施
        tag.setScope("scope");

        // 判定
        assertEquals("scope", UTUtil.getPrivateField(tag, "scope"));
    }

    /**
     * testSetKey01()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) key:"key"<br>
     *         (状態) key:null<br>
     *
     * <br>
     * 期待値：(状態変化) key:"key"<br>
     *
     * <br>
     * 引数の値がkeyに格納されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetKey01() throws Exception {
        // 前処理
        UTUtil.setPrivateField(tag, "key", null);

        // テスト実施
        tag.setKey("key");

        // 判定
        assertEquals("key", UTUtil.getPrivateField(tag, "key"));
    }

}
