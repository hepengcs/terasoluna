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

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyTag;
import javax.servlet.jsp.tagext.Tag;

import jp.terasoluna.utlib.LogUTUtil;
import jp.terasoluna.utlib.TagUTUtil;
import jp.terasoluna.utlib.UTUtil;
import jp.terasoluna.utlib.exception.Exception_BodyContentImpl;
import jp.terasoluna.utlib.exception.Exception_JspWriterImpl;
import junit.framework.TestCase;

/**
 * IfAuthorizedBlockTag ブラックボックステスト。<br>
 * 前提条件<br>
 * -<br>
 */
public class IfAuthorizedBlockTagTest extends TestCase {

    // テスト対象クラス生成
    IfAuthorizedBlockTag tag = null;
    
    /**
     * Constructor for IterateRowSetTeiTest.
     * @param arg0
     */
    public IfAuthorizedBlockTagTest(String arg0) {
        super(arg0);
    }

    /**
     * @see TestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        tag = (IfAuthorizedBlockTag) TagUTUtil.create(
                IfAuthorizedBlockTag.class);
        LogUTUtil.flush();

    }

    /**
     * @see TestCase#tearDown()
     */
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * testSetBlockId01()。<br>
     *
     * (正常系)<br>
     * 観点：A<br>
     *
     * 入力値："blockId"
     * 
     * 期待値：引数にて設定した値
     *
     * 引数で設定した値が設定されていることを確認する。
     *
     * @throws Exception テストコードの本質とかかわりの無い例外
     */
    public void testSetBlockId01() throws Exception {
        // テストデータ設定
        String value = "blockId";

        // テスト実行
        tag.setBlockId(value);

        // 結果確認
        String result = (String) UTUtil.getPrivateField(tag, "blockId");
        assertEquals(value, result);
    }

    /**
     * testSetParentBlockId01()。<br>
     *
     * (正常系)<br>
     * 観点：A<br>
     *
     * 入力値："parentBlockId"
     * 
     * 期待値：引数にて設定した値
     *
     * 引数で設定した値が設定されていることを確認する。
     *
     * @throws Exception テストコードの本質とかかわりの無い例外
     */
    public void testSetParentBlockId01() throws Exception {
        // テストデータ設定
        String value = "parentBlockId";

        // テスト実行
        tag.setParentBlockId(value);

        // 結果確認
        String result = (String) UTUtil.getPrivateField(tag, "parentBlockId");
        assertEquals(value, result);
    }

    /**
     * testRelease01。<br>
     *
     * (正常系)<br>
     * 観点：A<br>
     *
     * 入力値
     * this.blockId=*<br>
     * this.parentBlockId=*<br>
     *
     * 戻り値:void<br>
     * this.blockId=null<br>
     * this.parentBlockId=null<br>
     *
     * 前提条件として設定した各値が、実行時に各条件値が
     * 初期化されることを確認する<br>
     */
    public void testRelease01() throws Exception {
        // テストデータ設定
        String blockId = "あ";
        String parentBlockId = "い";

        UTUtil.setPrivateField(tag, "blockId", blockId);
        UTUtil.setPrivateField(tag, "parentBlockId", parentBlockId);

        // テスト実行
        tag.release();

        // テスト結果確認

        String blockId2 = (String) UTUtil.getPrivateField(tag, "blockId");
        String parentBlockId2 =
            (String) UTUtil.getPrivateField(tag, "parentBlockId");

        assertNull(blockId2);
        assertNull(parentBlockId2);

    }

    /**
     * testDoStartTag01。<br>
     *
     * (正常系)<br>
     * 観点：A<br>
     *
     * 入力値
     * this.blockId=not null<br>
     *
     * 戻り値:int=EVAL_BODY_BUFFERED<br>
     *
     * "this.blockId"がnot nullの場合、EVAL_BODY_BUFFEREDか
     * 返ることを確認する<br>
     */
    public void testDoStartTag01() throws Exception {
        // テストデータ設定
        // ブロックIDの設定
        UTUtil.setPrivateField(tag, "blockId", "blockId");

        //テスト実行
        int result = tag.doStartTag();

        //テスト結果確認
        assertEquals(BodyTag.EVAL_BODY_BUFFERED, result);

    }

    /**
     * testDoStartTag02。<br>
     *
     * (異常系)<br>
     * 観点：G<br>
     *
     * 入力値
     * this.blockId=null<br>
     *
     * 戻り値:int=JspException<br>
     *
     * "this.blockId"がnullの場合、JspExceptionが発生することを確認する<br>
     */
    public void testDoStartTag02() throws Exception {
        // テストデータ設定
        // ブロックIDの設定
        UTUtil.setPrivateField(tag, "blockId", null);

        // テスト実行
        try {
            tag.doStartTag();
            fail();
        } catch (JspException e) {
            // テスト結果確認
            assertEquals("blockId is required.", e.getMessage());
            return;
        }

    }

    /**
     * testDoAfterBody01。<br>
     *
     * (正常系)<br>
     * 観点：C<br>
     *
     * 入力値
     * blockId=null<br>
     * parentBlockId=null<br>
     *
     * 戻り値:int=SKIP_BODY<br>
     *        parentBlockId=null<br>
     *
     *・blockIdがnullの場合、SKIP_BODYが返ることを確認する。<br>
     *・アクセス権限チェック結果が保存されていないことを確認<br>
     */
    public void testDoAfterBody01() throws Exception {
        // テストデータ設定
        // ブロックIDの設定
        UTUtil.setPrivateField(tag, "blockId", null);
        // parentBlockIDの設定
        UTUtil.setPrivateField(tag, "parentBlockId", null);
        // テスト実行
        int result = tag.doAfterBody();

        // テスト結果確認
        assertEquals(Tag.SKIP_BODY, result);

        // PageContext
        PageContext pc = TagUTUtil.getPageContext(tag);
        Boolean parentBlockId = (Boolean) pc.getAttribute("parentBlockId");
        assertNull(parentBlockId);
    }

    /**
     * testDoAfterBody02。<br>
     *
     * (正常系)<br>
     * 観点：A,F<br>
     *
     * 入力値
     * blockId= not null<br>
     * blockInfo= false<br>
     * parentBlockId=null<br>
     *
     * 戻り値:int=SKIP_BODY<br>
     *        parentBlockId=null<br>
     *
     * blockIdがnot nullの場合、SKIP_BODYが返ることを確認する。<br>
     * ・アクセス権限チェック結果が保存されていないことを確認<br>
     */
    public void testDoAfterBody02() throws Exception {
        // テストデータ設定
        // ブロックIDの設定
        UTUtil.setPrivateField(tag, "blockId", "blockId");
        // 親ブロックIDの設定
        UTUtil.setPrivateField(tag, "parentBlockId", null);
        // blockInfoの設定
        PageContext pc = TagUTUtil.getPageContext(tag);
        pc.setAttribute(
            "blockId",
            new Boolean("false"),
            PageContext.PAGE_SCOPE);

        // テスト実行
        int result = tag.doAfterBody();

        // テスト結果確認
        assertEquals(Tag.SKIP_BODY, result);

        // PageContext
        pc = TagUTUtil.getPageContext(tag);
        Boolean parentBlockId = (Boolean) pc.getAttribute("parentBlockId");
        assertNull(parentBlockId);

    }

    /**
     * testDoAfterBody03。<br>
     *
     * (正常系)<br>
     * 観点：A,F<br>
     *
     * 入力値
     * blockId= not null<br>
     * blockInfo= false<br>
     * parentBlockId=not null<br>
     *
     * 戻り値:int=SKIP_BODY<br>
     *        parentBlockId=false<br>
     *
     * blockIdがnot nullの場合、SKIP_BODYが返ることを確認する。<br>
     * ・アクセス権限チェック結果が保存されていることを確認<br>
     */
    public void testDoAfterBody03() throws Exception {
        // テストデータ設定
        // ブロックIDの設定
        UTUtil.setPrivateField(tag, "blockId", "blockId");
        // 親ブロックIDの設定
        UTUtil.setPrivateField(tag, "parentBlockId", "parentBlockId");
        //blockInfoの設定
        PageContext pc = TagUTUtil.getPageContext(tag);
        pc.setAttribute(
            "blockId",
            new Boolean("false"),
            PageContext.PAGE_SCOPE);

        // テスト実行
        int result = tag.doAfterBody();

        // テスト結果確認
        assertEquals(Tag.SKIP_BODY, result);

        // PageContext
        pc = TagUTUtil.getPageContext(tag);
        Boolean parentBlockId = (Boolean) pc.getAttribute("parentBlockId");
        assertFalse(parentBlockId.booleanValue());

    }

    /**
     * testDoAfterBody04。<br>
     *
     * (正常系)<br>
     * 観点：A,F<br>
     *
     * 入力値
     * blockId= not null<br>
     * blockInfo= true<br>
     * parentBlockId=not null<br>
     *
     * 戻り値:int=SKIP_BODY<br>
     *        parentBlockId=true<br>
     *
     * blockIdがnot nullの場合、SKIP_BODYが返ることを確認する。<br>
     * ・アクセス権限チェック結果が保存されていることを確認<br>
     */
    public void testDoAfterBody04() throws Exception {
        // テストデータ設定
        // ブロックIDの設定
        UTUtil.setPrivateField(tag, "blockId", "blockId");
        // 親ブロックIDの設定
        UTUtil.setPrivateField(tag, "parentBlockId", "parentBlockId");
        // blockInfoの設定
        PageContext pc = TagUTUtil.getPageContext(tag);
        pc.setAttribute("blockId", new Boolean("True"), PageContext.PAGE_SCOPE);

        // bodyContent
        UTUtil.setPrivateField(tag, "bodyContent", pc.pushBody());

        // テスト実行
        int result = tag.doAfterBody();

        // テスト結果確認
        assertEquals(Tag.SKIP_BODY, result);

        // PageContext
        pc = TagUTUtil.getPageContext(tag);
        Boolean parentBlockId = (Boolean) pc.getAttribute("parentBlockId");
        assertTrue(parentBlockId.booleanValue());

    }

    /**
     * testDoAfterBody05。<br>
     *
     * (正常系)<br>
     * 観点：A<br>
     *
     * 入力値
     * blockId= not null<br>
     * blockInfo= Boolean型以外<br>
     * parentBlockId=not null<br>
     *
     * 戻り値:ClassCastExceptionが発生しているログを表示<br>
     *       int=SKIP_BODYが返却される。<br>
     *
     * pageContext からブロック情報をCastする際に、<br>
     * ClassCastExceptionが発生することを確認する。<br>
     * ClassCastExceptionが発生しても、処理は実行され、SKIP_BODYが返される。<br>
     */
    public void testDoAfterBody05() throws Exception {
        // テストデータ設定
        // ブロックIDの設定
        UTUtil.setPrivateField(tag, "blockId", "blockId");
        // 親ブロックIDの設定
        UTUtil.setPrivateField(tag, "parentBlockId", "parentBlockId");
        // blockInfoの設定
        PageContext pc = TagUTUtil.getPageContext(tag);
        Object obj = "a";
        pc.setAttribute("blockId", obj, PageContext.PAGE_SCOPE);

        // bodyContent
        UTUtil.setPrivateField(
            tag,
            "bodyContent",
            pc.pushBody());

        // テスト実行
        int result = tag.doAfterBody();

        // 結果の確認
        // ClassCastExceptionが起こったかどうかlogから確認。
        boolean logCheck = LogUTUtil.checkWarn("Class cast error.",
                new ClassCastException());
        assertTrue(logCheck);
        assertEquals(Tag.SKIP_BODY, result);
    }

    /**
     * testDoAfterBody06。<br>
     *
     * (異常系)<br>
     * 観点：G<br>
     *
     * 入力値
     * blockId= not null<br>
     * blockInfo= true<br>
     * parentBlockId=not null<br>
     *
     * 戻り値:IOExceptionが発生<br>
     *
     * bodyContent.writeOut()を行う際に、<br>
     * IOExceptionが返ることを確認する。<br>
     */
    public void testDoAfterBody06() throws Exception {
        // テストデータ設定
        // ブロックIDの設定
        UTUtil.setPrivateField(tag, "blockId", "blockId");
        // 親ブロックIDの設定
        UTUtil.setPrivateField(tag, "parentBlockId", "parentBlockId");
        // blockInfoの設定
        PageContext pc = TagUTUtil.getPageContext(tag);
        pc.setAttribute("blockId", new Boolean("True"), PageContext.PAGE_SCOPE);

        // bodyContent
        UTUtil.setPrivateField(tag, "bodyContent", pc
                .pushBody());
        // IOException発生設定
        Exception_JspWriterImpl jsp = new Exception_JspWriterImpl();
        Exception_BodyContentImpl exception = new Exception_BodyContentImpl(jsp);
        exception.setTrue();
        exception.setTiming(1);
        UTUtil.setPrivateField(tag, "bodyContent", exception);
        // テスト実行
        try {
            tag.doAfterBody();
            fail();
        } catch (JspException ex) {
            // テスト結果確認
            assertEquals(IOException.class.getName(), ex.getRootCause()
                    .getClass().getName());
            return;
        }
    }

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
     */
    public void testDoEndTag01() throws Exception {

        int result = 0;
        // テスト実行
        result = tag.doEndTag();

        // テスト結果確認
        assertEquals(Tag.EVAL_PAGE, result);
    }
}
