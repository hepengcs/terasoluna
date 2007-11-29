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

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.Tag;

import jp.terasoluna.utlib.LogUTUtil;
import jp.terasoluna.utlib.TagUTUtil;
import jp.terasoluna.utlib.UTUtil;
import jp.terasoluna.utlib.exception.Exception_JspWriterImpl;
import jp.terasoluna.utlib.spring.SpringTestCase;

import com.mockrunner.mock.web.MockPageContext;
import com.mockrunner.mock.web.MockServletConfig;

/**
 * {@link jp.terasoluna.fw.web.taglib.WriteCodeCountTag}
 * クラスのブラックボックステスト。
 *
 * <p>
 * <h4>【クラスの概要】</h4>
 * writeCodeCount タグの実装クラス。
 * <p>
 *
 * @see jp.terasoluna.fw.web.taglib.WriteCodeCountTag
 */
@SuppressWarnings("unused")
public class WriteCodeCountTagTest extends SpringTestCase {

    /**
     * このテストケースを実行する為の GUI アプリケーションを起動する。
     *
     * @param args
     *            java コマンドに設定されたパラメータ
     */
    public static void main(String[] args) {
    }

    @Override
    protected void doOnSetUp() throws Exception {

    }

    @Override
    protected String[] getConfigLocations() {
        return new String[] { "jp/terasoluna/fw/web/taglib/WriteCodeCountTagTest.xml" };
    }

    /**
     * testDoStartTag01()
     * <br><br>
     *
     * (異常系)
     * <br>
     * 観点：G
     * <br><br>
     * 入力値：(状態) id:""<br>
     *         (状態) out.print:正常実行<br>
     *
     * <br>
     * 期待値：(状態変化) 例外:JspTagException<br>
     *                    ラップした例外：null<br>
     *                    メッセージ：id is required<br>
     *         (状態変化) ログ:ログレベル：error<br>
     *                    メッセージ：id is required<br>
     *
     * <br>
     * インスタンス変数idが空文字の場合、JspTagExceptionが発生することを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testDoStartTag01() throws Exception {
        // 前処理
        WriteCodeCountTag tag = null;
        tag = (WriteCodeCountTag) TagUTUtil.create(WriteCodeCountTag.class);
        // id=""
        UTUtil.setPrivateField(tag, "id", "");

        try {
            // テスト実行
            int result = tag.doStartTag();
            // 判定
            fail();

        } catch (JspTagException ex) {
            // 判定
            // メッセージチェック
            assertEquals("id is required.", ex.getMessage());
            // ラップした例外チェック
            assertNull(ex.getCause());
            // ログチェック
            assertTrue(LogUTUtil.checkError("id is required."));
        }

    }

    /**
     * testDoStartTag02()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(状態) id:testLoader02<br>
     *         (状態) WebApplicationContext.getBean(id)<br>
     *                （CodeListLoader）:not null<br>
     *                getCodeBeans=null<br>
     *         (状態) out.print:正常実行<br>
     *
     * <br>
     * 期待値：(戻り値) int:TagSupport.EVAL_BODY_INCLUDE<br>
     *         (状態変化) 出力内容:"0"<br>
     *         (状態変化) ログ:ログレベル：warn<br>
     *                    メッセージ："Codebean is null. CodeListLoader(bean id:testLoader)"<br>
     *
     * <br>
     * DIコンテナから取得したCodeListLoaderのgetCodeBeansがnullを返却する場合、ページに0を出力することを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testDoStartTag02() throws Exception {
        // 前処理
        WriteCodeCountTag tag = null;
        tag = (WriteCodeCountTag) TagUTUtil.create(WriteCodeCountTag.class);
        MockPageContext pageContext =
            (MockPageContext) TagUTUtil.getPageContext(tag);
        MockServletConfig config = new MockServletConfig();
        config.setServletContext(servletContext);
        UTUtil.setPrivateField(pageContext, "config", config);

        // getCodeBeans=null
        UTUtil.setPrivateField(tag, "id", "testLoader02");

        // テスト実行
        int result = tag.doStartTag();
        // 判定
        // 戻り値
        assertEquals(Tag.EVAL_BODY_INCLUDE, result);
        // ログチェック
        assertTrue(LogUTUtil
                .checkWarn("Codebean is null. CodeListLoader(bean id:testLoader02)"));
        // 出力内容チェック
        String reader = TagUTUtil.getOutputString(tag);
        assertEquals("0", reader);
    }

    /**
     * testDoStartTag03()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(状態) id:testLoader03<br>
     *         (状態) WebApplicationContext.getBean(id)<br>
     *                （CodeListLoader）:not null<br>
     *                getCodeBeans={}（空の配列）<br>
     *         (状態) out.print:正常実行<br>
     *
     * <br>
     * 期待値：(戻り値) int:TagSupport.EVAL_BODY_INCLUDE<br>
     *         (状態変化) 出力内容:"0"<br>
     *
     * <br>
     * DIコンテナから取得したCodeListLoaderのgetCodeBeansが空の配列を返却する場合、ページに0を出力することを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testDoStartTag03() throws Exception {
        // 前処理
        WriteCodeCountTag tag = null;
        tag = (WriteCodeCountTag) TagUTUtil.create(WriteCodeCountTag.class);
        MockPageContext pageContext = (MockPageContext) TagUTUtil
                .getPageContext(tag);

        MockServletConfig config = new MockServletConfig();
        config.setServletContext(servletContext);
        UTUtil.setPrivateField(pageContext, "config", config);
        // getCodeBeans={}（空の配列）
        UTUtil.setPrivateField(tag, "id", "testLoader03");

        // テスト実行
        int result = tag.doStartTag();
        // 判定
        assertEquals(Tag.EVAL_BODY_INCLUDE, result);
        // 出力内容チェック
        String reader = TagUTUtil.getOutputString(tag);
        assertEquals("0", reader);
    }

    /**
     * testDoStartTag04()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(状態) id:testLoader04<br>
     *         (状態) WebApplicationContext.getBean(id)<br>
     *                （CodeListLoader）:not null<br>
     *                getCodeBeans={<br>
     *                CodeBean("id","name")<br>
     *                }<br>
     *         (状態) out.print:正常実行<br>
     *
     * <br>
     * 期待値：(戻り値) int:TagSupport.EVAL_BODY_INCLUDE<br>
     *         (状態変化) 出力内容:"1"<br>
     *
     * <br>
     * DIコンテナから取得したCodeListLoaderのgetCodeBeansが要素数1の配列を返却する場合、ページに1を出力することを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testDoStartTag04() throws Exception {
        // 前処理
        WriteCodeCountTag tag = null;
        tag = (WriteCodeCountTag) TagUTUtil.create(WriteCodeCountTag.class);
        MockPageContext pageContext = (MockPageContext) TagUTUtil
                .getPageContext(tag);
        MockServletConfig config = new MockServletConfig();
        config.setServletContext(servletContext);
        UTUtil.setPrivateField(pageContext, "config", config);

        // getCodeBeans={CodeBean("id","name")}
        UTUtil.setPrivateField(tag, "id", "testLoader04");

        // テスト実行
        int result = tag.doStartTag();
        // 判定
        assertEquals(Tag.EVAL_BODY_INCLUDE, result);
        // 出力内容チェック
        String reader = TagUTUtil.getOutputString(tag);
        assertEquals("1", reader);
    }

    /**
     * testDoStartTag05()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(状態) id:testLoader05<br>
     *         (状態) WebApplicationContext.getBean(id)<br>
     *                （CodeListLoader）:not null<br>
     *                getCodeBeans={<br>
     *                CodeBean("id1","name1"),<br>
     *                CodeBean("id2","name2"),<br>
     *                CodeBean("id3","name3"),<br>
     *                }<br>
     *         (状態) out.print:正常実行<br>
     *
     * <br>
     * 期待値：(戻り値) int:TagSupport.EVAL_BODY_INCLUDE<br>
     *         (状態変化) 出力内容:"3"<br>
     *
     * <br>
     * DIコンテナから取得したCodeListLoaderのgetCodeBeansが複数の要素数の配列を返却する場合、ページに要素数を出力することを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testDoStartTag05() throws Exception {
        // 前処理
        WriteCodeCountTag tag = null;
        tag = (WriteCodeCountTag) TagUTUtil.create(WriteCodeCountTag.class);
        MockPageContext pageContext = (MockPageContext) TagUTUtil
                .getPageContext(tag);
        MockServletConfig config = new MockServletConfig();
        config.setServletContext(servletContext);
        UTUtil.setPrivateField(pageContext, "config", config);

        // getCodeBeans={
        //     CodeBean("id1","name1"),
        //     CodeBean("id2","name2"),
        //     CodeBean("id3","name3"),
        // }
        UTUtil.setPrivateField(tag, "id", "testLoader05");

        // テスト実行
        int result = tag.doStartTag();
        // 判定
        assertEquals(Tag.EVAL_BODY_INCLUDE, result);
        // 出力内容チェック
        String reader = TagUTUtil.getOutputString(tag);
        assertEquals("3", reader);
    }

    /**
     * testDoStartTag06()
     * <br><br>
     *
     * (異常系)
     * <br>
     * 観点：G
     * <br><br>
     * 入力値：(状態) id:testLoader06<br>
     *         (状態) WebApplicationContext.getBean(id)<br>
     *                （CodeListLoader）:not null<br>
     *                CodeListLoaderを実装しないクラスのインスタンス。<br>
     *         (状態) out.print:正常実行<br>
     *
     * <br>
     * 期待値：(状態変化) 例外:JspTagException<br>
     *                    ラップした例外：ClassCastException<br>
     *                    メッセージ：bean id:testLoader is not instance of CodeListLoader<br>
     *         (状態変化) ログ:ログレベル：error<br>
     *                    メッセージ：bean id:testLoader is not instance of CodeListLoader<br>
     *
     * <br>
     * DIコンテナから取得したインスタンスがCodeListLoaderを実装したクラスのインスタンスではない場合、JspTagExceptionが発生することを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testDoStartTag06() throws Exception {
        // 前処理
        WriteCodeCountTag tag = null;
        tag = (WriteCodeCountTag) TagUTUtil.create(WriteCodeCountTag.class);
        MockPageContext pageContext = (MockPageContext) TagUTUtil
                .getPageContext(tag);
        MockServletConfig config = new MockServletConfig();
        config.setServletContext(servletContext);
        UTUtil.setPrivateField(pageContext, "config", config);

        // 実行結果
        int result = -1;
        // CodeListLoaderを実装しないクラスのインスタンス。
        UTUtil.setPrivateField(tag, "id", "testLoader06");
        try {
            // テスト実行
            result = tag.doStartTag();
            fail();
        } catch (JspTagException ex) {
            // 判定
            // ラップした例外チェック
            assertEquals(ClassCastException.class.getName(),
                    ex.getRootCause().getClass().getName());

            // メッセージキーチェック
            assertEquals(
                    "bean id:testLoader06 is not instance of CodeListLoader.",
                    ex.getMessage());
            // ログチェック
            assertTrue(LogUTUtil
                    .checkError("bean id:testLoader06 is not instance of CodeListLoader."));
        }

    }

    /**
     * testDoStartTag07()
     * <br><br>
     *
     * (異常系)
     * <br>
     * 観点：G
     * <br><br>
     * 入力値：(状態) id:testLoader07<br>
     *         (状態) WebApplicationContext.getBean(id)<br>
     *                （CodeListLoader）:not null<br>
     *                getCodeBeans={<br>
     *                CodeBean("id","name")<br>
     *                }<br>
     *         (状態) out.print:IOException発生<br>
     *
     * <br>
     * 期待値：(状態変化) 例外:JspTagException<br>
     *                    ラップした例外：null<br>
     *         (状態変化) ログ:ログレベル：error<br>
     *                    メッセージ：IOException caused.<br>
     *
     * <br>
     * JspWriterからIOExceptionが発生した場合、JspTagExceptionが発生することを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testDoStartTag07() throws Exception {
        // 前処理
        WriteCodeCountTag tag = null;
        tag = (WriteCodeCountTag) TagUTUtil.create(WriteCodeCountTag.class);
        MockPageContext pageContext = (MockPageContext) TagUTUtil
                .getPageContext(tag);
        MockServletConfig config = new MockServletConfig();
        config.setServletContext(servletContext);
        UTUtil.setPrivateField(pageContext, "config", config);

        Exception_JspWriterImpl out = new Exception_JspWriterImpl();
        out.setTrue();
        out.setTiming(1);
        UTUtil.setPrivateField(pageContext, "jspWriter", out);


        // 実行結果
        int result = -1;
        // CodeListLoaderを実装しないクラスのインスタンス。
        UTUtil.setPrivateField(tag, "id", "testLoader07");

        try {
            // テスト実行
            result = tag.doStartTag();
            fail();
        } catch (JspTagException ex) {
            // 判定
            // ラップした例外チェック
            assertNull(ex.getRootCause());
            // ログチェック
            assertTrue(LogUTUtil
                    .checkError("IOException caused."));
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
        // 前処理
        WriteCodeCountTag tag = null;
        tag = (WriteCodeCountTag) TagUTUtil.create(WriteCodeCountTag.class);

        // テスト実施
        int result = tag.doEndTag();
        // テスト結果確認
        assertEquals(Tag.EVAL_PAGE, result);
    }

    /**
     * testRelease01()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(状態) id:"id"<br>
     *
     * <br>
     * 期待値：(状態変化) id:null<br>
     *
     * <br>
     * idがnullになることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testRelease01() throws Exception {
        // 前処理
        WriteCodeCountTag tag = null;
        tag = (WriteCodeCountTag) TagUTUtil.create(WriteCodeCountTag.class);

        UTUtil.setPrivateField(tag, "id", "id");

        // テスト実施
        tag.release();
        // テスト結果確認
        assertNull(UTUtil.getPrivateField(tag, "id"));
    }
}
