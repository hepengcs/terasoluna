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

import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;

import jp.terasoluna.fw.web.thin.AuthorizationControlFilter;
import jp.terasoluna.utlib.TagUTUtil;
import jp.terasoluna.utlib.UTUtil;
import junit.framework.TestCase;

/**
 * IfAuthorizedTag ブラックボックステスト。<br>
 * 前提条件<br>
 * -<br>
 */
public class IfAuthorizedTagTest extends TestCase {

    // テスト対象クラス生成
    IfAuthorizedTag tag = null;

    /**
     * コンストラクタ<br>
     * @param arg0
     */
    public IfAuthorizedTagTest(String arg0) {
        super(arg0);
    }

    /*
     * @see TestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        tag = (IfAuthorizedTag) TagUTUtil.create(IfAuthorizedTag.class);
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
     * 観点：A、F<br>
     * 
     * 入力値
     * blockId=not null<br>
     * isAuthorized=true<br>
     * AccessController= not null<br>
     * 
     * 期待値
     * 戻り値:int=EVAL_BODY_INCLUDE<br>
     * pageContextのgetAttribute("blockId") = true<br>
     * 
     * ・blockIdがnot null、isAuthorizedがtrueの場合、
     * 　EVAL_BODY_INCLUDEが返ることを確認する。<br>
     * ・また、PageContextのブロック情報にtrueが保存されている<br>
     * ・IfAuthorizedTag_AccessControllerStub01の場合、
     *   isAuthorized()にてtrueを返却<br>
     */
    public void testDoStartTag01() throws Exception {
        // テストデータ設定
        // ブロックIDの設定
        UTUtil.setPrivateField(tag, "blockId", "blockId");
        // AccessControlFilter、AccessControllerの生成
        IfAuthorizedTag_AccessControllerStub01 ac =
            new IfAuthorizedTag_AccessControllerStub01();
        UTUtil.setPrivateField(AuthorizationControlFilter.class,
                "controller", ac);

        // テスト実行
        int result = tag.doStartTag();

        // テスト結果確認
        assertEquals(Tag.EVAL_BODY_INCLUDE, result);

        // PageContext
        PageContext pc = TagUTUtil.getPageContext(tag);
        Boolean bId = (Boolean) pc.getAttribute("blockId");
        assertTrue(bId.booleanValue());
    }

    /**
     * testDoStartTag02。<br>
     * 
     * (正常系)<br>
     * 観点：C、F<br>
     * 
     * 入力値
     * blockId=null<br>
     * isAuthorized=true<br>
     * AccessController= not null<br>
     * 
     * 期待値
     * 戻り値:int=EVAL_BODY_INCLUDE<br>
     * pageContextのgetAttribute("blockId") = null<br>
     * 
     * ・blockIdがnull、isAuthorizedがtrueの場合、
     * 　EVAL_BODY_INCLUDEが返ることを確認する。<br>
     * ・IfAuthorizedTag_AccessControllerStub01の場合、
     *   isAuthorized()にてtrueを返却<br>
     */
    public void testDoStartTag02() throws Exception {
        // テストデータ設定
        // ブロックIDの設定
        UTUtil.setPrivateField(tag, "blockId", null);
        // AccessControlFilter、AccessControllerの生成
        IfAuthorizedTag_AccessControllerStub01 ac =
            new IfAuthorizedTag_AccessControllerStub01();
        UTUtil.setPrivateField(AuthorizationControlFilter.class,
                "controller", ac);

        // テスト実行
        int result = tag.doStartTag();

        // テスト結果確認
        assertEquals(Tag.EVAL_BODY_INCLUDE, result);

        // PageContext
        PageContext pc = TagUTUtil.getPageContext(tag);
        Boolean bId = (Boolean) pc.getAttribute("blockId");
        assertNull(bId);

    }

    /**
     * testDoStartTag03。<br>
     * 
     * (正常系)<br>
     * 観点：A、F<br>
     * 
     * 入力値
     * blockId=not null<br>
     * isAuthorized=false<br>
     * AccessController= not null<br>
     * 
     * 期待値
     * 戻り値:int=SKIP_BODY<br>
     * pageContextのgetAttribute("blockId") = null<br>
     * 
     * ・blockIdがnot null、isAuthorizedがfalseの場合、
     * 　SKIP_BODYが返ることを確認する。<br>
     * ・IfAuthorizedTag_AccessControllerStub02の場合、
     *   isAuthorized()にてfalseを返却<br>
     */
    public void testDoStartTag03() throws Exception {
        // テストデータ設定
        // ブロックIDの設定
        UTUtil.setPrivateField(tag, "blockId", "blockId");
        // AccessControlFilter、AccessControllerの生成
        IfAuthorizedTag_AccessControllerStub02 ac =
            new IfAuthorizedTag_AccessControllerStub02();
        UTUtil.setPrivateField(AuthorizationControlFilter.class,
                "controller", ac);

        // テスト実行
        int result = tag.doStartTag();

        // テスト結果確認
        assertEquals(Tag.SKIP_BODY, result);

        // PageContext
        PageContext pc = TagUTUtil.getPageContext(tag);
        Boolean bId = (Boolean) pc.getAttribute("blockId");
        assertNull(bId);

    }

    /**
     * testDoStartTag04。<br>
     * 
     * (異常系)<br>
     * 観点：G<br>
     * 
     * 入力値
     * blockId=not null<br>
     * AccessController= null<br>
     * 
     * 期待値
     * 戻り値:NullPointerExceptionが発生する<br>
     * 
     * ・AccessController= nullの場合、<br>
     * 　NullPointerExceptionが返ることを確認する。<br>
     */
    public void testDoStartTag04() throws Exception {
        // テストデータ設定
        // ブロックIDの設定
        UTUtil.setPrivateField(tag, "blockId", "blockId");
        // AccessControlFilter、AccessControllerの生成
        UTUtil.setPrivateField(AuthorizationControlFilter.class,
                "controller", null);

        // テスト実行
        try {
            tag.doStartTag();
            fail();
        } catch (NullPointerException e) {
            return;
        }

    }

    /**
     * testRelease01。<br>
     * 
     * (正常系)<br>
     * 観点：A<br>
     * 
     * 入力値
     * this.path=*<br>
     * this.blockId=*<br>
     * 
     * 期待値
     * 戻り値:void<br>
     * this.path=null<br>
     * this.blockId=null<br>
     * 
     * 前提条件として設定した各値が、実行時に各条件値が
     * 初期化されることを確認する<br>
     */
    public void testRelease01() throws Exception {
        //テストデータ設定
        String path = "あ";
        String blockId = "い";

        UTUtil.setPrivateField(tag, "path", path);
        UTUtil.setPrivateField(tag, "blockId", blockId);

        //テスト実行
        tag.release();

        //テスト結果確認

        String path2 = (String) UTUtil.getPrivateField(tag, "path");
        String blockId2 = (String) UTUtil.getPrivateField(tag, "blockId");

        assertNull(path2);
        assertNull(blockId2);

    }

    /**
     * testSetBlockId01()。<br>
     *
     * (正常系)<br>
     * 観点：A<br>
     *
     * 入力値：
     *   引数："blockId"
     * 
     * 期待値：引数にて設定した値
     *
     * 引数で設定した値が設定されていることを確認する。
     *
     * @throws Exception テストコードの本質とかかわりの無い例外
     */
    public void testSetBlockId01() throws Exception {
        //テストデータ設定
        String value = "blockId";

        //テスト実行
        tag.setBlockId(value);

        //結果確認
        String result = (String) UTUtil.getPrivateField(tag, "blockId");
        assertEquals(value, result);
    }

    /**
     * testSetPath01()。<br>
     *
     * (正常系)<br>
     * 観点：A<br>
     *
     * 入力値：
     *   引数："path"
     * 
     * 期待値：引数にて設定した値
     *
     * 引数で設定した値が設定されていることを確認する。
     *
     * @throws Exception テストコードの本質とかかわりの無い例外
     */
    public void testSetPath01() throws Exception {
        //テストデータ設定
        String value = "path";

        //テスト実行
        tag.setPath(value);

        //結果確認
        String result = (String) UTUtil.getPrivateField(tag, "path");
        assertEquals(value, result);
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
        //テスト実行
        result = tag.doEndTag();

        //テスト結果確認
        assertEquals(Tag.EVAL_PAGE, result);
    }
}
