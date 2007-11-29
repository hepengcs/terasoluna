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
import java.util.HashMap;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

import jp.terasoluna.utlib.LogUTUtil;
import jp.terasoluna.utlib.PropertyTestCase;
import jp.terasoluna.utlib.TagUTUtil;
import jp.terasoluna.utlib.UTUtil;
import jp.terasoluna.utlib.exception.Exception_JspWriterImpl;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionMapping;

import com.mockrunner.mock.web.MockPageContext;

/**
 * {@link jp.terasoluna.framework.web.struts.taglib.PageLinksTag} 
 * クラスのブラックボックステスト。
 * 
 * <p>
 * <h4>【クラスの概要】</h4>
 * ページ単位にページを遷移する機能。
 * getInt()のテストの一部はdoStartTag()に包含する。
 * <p>
 * 
 * @see jp.terasoluna.framework.web.struts.taglib.PageLinksTag
 */
public class PageLinksTagTest extends PropertyTestCase {

    /**
     * テスト対象クラス
     */
    private PageLinksTag pageLinks = null;

    /**
     * このテストケースを実行する為の
     * GUI アプリケーションを起動する。
     * 
     * @param args java コマンドに設定されたパラメータ
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(PageLinksTagTest.class);
    }

    /**
     * 初期化処理を行う。
     * 
     * @throws Exception このメソッドで発生した例外
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUpData() throws Exception {
        pageLinks = (PageLinksTag) TagUTUtil.create(PageLinksTag.class);
        clearProperty();
    }

    /**
     * 終了処理を行う。
     * 
     * @throws Exception このメソッドで発生した例外
     * @see junit.framework.TestCase#tearDown()
     */
    @Override
    protected void cleanUpData() throws Exception {
        pageLinks = null;
    }

    /**
     * コンストラクタ。
     * 
     * @param name このテストケースの名前。
     */
    public PageLinksTagTest(String name) {
        super(name);
    }

    /**
     * testGetId01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(状態) id:"abc"<br>
     *         
     * <br>
     * 期待値：(戻り値) String:"abc"<br>
     *         
     * <br>
     * ※正常系一件のみテスト
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetId01() throws Exception {
        // 前処理
        String param = "abc";
        UTUtil.setPrivateField(pageLinks, "id", param);

        // テスト実施
        String value = pageLinks.getId();

        // 判定
        assertEquals(param, value);
    }

    /**
     * testSetId01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) id:"abc"<br>
     *         (状態) id:null<br>
     *         
     * <br>
     * 期待値：(状態変化) id:"abc"<br>
     *         
     * <br>
     * ※正常系一件のみテスト
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetId01() throws Exception {
        // 前処理
        String param = "abc";
        UTUtil.setPrivateField(pageLinks, "id", null);

        // テスト実施
        pageLinks.setId(param);

        // 判定
        assertEquals(param, UTUtil.getPrivateField(pageLinks, "id"));
    }

    /**
     * testGetAction01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(状態) action:"abc"<br>
     *         
     * <br>
     * 期待値：(戻り値) String:"abc"<br>
     *         
     * <br>
     * ※正常系一件のみテスト
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetAction01() throws Exception {
        // 前処理
        String param = "abc";
        UTUtil.setPrivateField(pageLinks, "action", param);

        // テスト実施
        String value = pageLinks.getAction();

        // 判定
        assertEquals(param, value);
    }

    /**
     * testSetAction01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) action:"abc"<br>
     *         (状態) action:null<br>
     *         
     * <br>
     * 期待値：(状態変化) action:"abc"<br>
     *         
     * <br>
     * ※正常系一件のみテスト
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetAction01() throws Exception {
        // 前処理
        String param = "abc";
        UTUtil.setPrivateField(pageLinks, "action", null);

        // テスト実施
        pageLinks.setAction(param);

        // 判定
        assertEquals(param, UTUtil.getPrivateField(pageLinks, "action"));
    }

    /**
     * testGetName01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(状態) name:"abc"<br>
     *         
     * <br>
     * 期待値：(戻り値) String:"abc"<br>
     *         
     * <br>
     * ※正常系一件のみテスト
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetName01() throws Exception {
        // 前処理
        String param = "abc";
        UTUtil.setPrivateField(pageLinks, "name", param);

        // テスト実施
        String value = pageLinks.getName();

        // 判定
        assertEquals(param, value);
    }

    /**
     * testSetName01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) name:"abc"<br>
     *         (状態) name:null<br>
     *         
     * <br>
     * 期待値：(状態変化) name:"abc"<br>
     *         
     * <br>
     * ※正常系一件のみテスト
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetName01() throws Exception {
        // 前処理
        String param = "abc";
        UTUtil.setPrivateField(pageLinks, "name", null);

        // テスト実施
        pageLinks.setName(param);

        // 判定
        assertEquals(param, UTUtil.getPrivateField(pageLinks, "name"));
    }

    /**
     * testGetRowProperty01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(状態) rowProperty:"abc"<br>
     *         
     * <br>
     * 期待値：(戻り値) String:"abc"<br>
     *         
     * <br>
     * ※正常系一件のみテスト
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetRowProperty01() throws Exception {
        // 前処理
        String param = "abc";
        UTUtil.setPrivateField(pageLinks, "rowProperty", param);

        // テスト実施
        String value = pageLinks.getRowProperty();

        // 判定
        assertEquals(param, value);
    }

    /**
     * testSetRowProperty01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) rowProperty:"abc"<br>
     *         (状態) rowProperty:null<br>
     *         
     * <br>
     * 期待値：(状態変化) rowProperty:"abc"<br>
     *         
     * <br>
     * ※正常系一件のみテスト
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetRowProperty01() throws Exception {
        // 前処理
        String param = "abc";
        UTUtil.setPrivateField(pageLinks, "rowProperty", null);

        // テスト実施
        pageLinks.setRowProperty(param);

        // 判定
        assertEquals(param, UTUtil.getPrivateField(pageLinks, "rowProperty"));
    }

    /**
     * testGetIndexProperty01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(状態) indexProperty:"abc"<br>
     *         
     * <br>
     * 期待値：(戻り値) String:"abc"<br>
     *         
     * <br>
     * ※正常系一件のみテスト
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetIndexProperty01() throws Exception {
        // 前処理
        String param = "abc";
        UTUtil.setPrivateField(pageLinks, "indexProperty", param);

        // テスト実施
        String value = pageLinks.getIndexProperty();

        // 判定
        assertEquals(param, value);
    }

    /**
     * testSetIndexProperty01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) indexProperty:"abc"<br>
     *         (状態) indexProperty:null<br>
     *         
     * <br>
     * 期待値：(状態変化) indexProperty:"abc"<br>
     *         
     * <br>
     * ※正常系一件のみテスト
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetIndexProperty01() throws Exception {
        // 前処理
        String param = "abc";
        UTUtil.setPrivateField(pageLinks, "indexProperty", null);

        // テスト実施
        pageLinks.setIndexProperty(param);

        // 判定
        assertEquals(param, UTUtil.getPrivateField(pageLinks, "indexProperty"));
    }

    /**
     * testGetTotalProperty01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(状態) totalProperty:"abc"<br>
     *         
     * <br>
     * 期待値：(戻り値) String:"abc"<br>
     *         
     * <br>
     * ※正常系一件のみテスト
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetTotalProperty01() throws Exception {
        // 前処理
        String param = "abc";
        UTUtil.setPrivateField(pageLinks, "totalProperty", param);

        // テスト実施
        String value = pageLinks.getTotalProperty();

        // 判定
        assertEquals(param, value);
    }

    /**
     * testSetTotalProperty01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) totalProperty:"abc"<br>
     *         (状態) totalProperty:null<br>
     *         
     * <br>
     * 期待値：(状態変化) totalProperty:"abc"<br>
     *         
     * <br>
     * ※正常系一件のみテスト
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetTotalProperty01() throws Exception {
        // 前処理
        String param = "abc";
        UTUtil.setPrivateField(pageLinks, "totalProperty", null);

        // テスト実施
        pageLinks.setTotalProperty(param);

        // 判定
        assertEquals(param, UTUtil.getPrivateField(pageLinks, "totalProperty"));
    }

    /**
     * testGetScope01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(状態) scope:"abc"<br>
     *         
     * <br>
     * 期待値：(戻り値) String:"abc"<br>
     *         
     * <br>
     * ※正常系一件のみテスト
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetScope01() throws Exception {
        // 前処理
        String param = "abc";
        UTUtil.setPrivateField(pageLinks, "scope", param);

        // テスト実施
        String value = pageLinks.getScope();

        // 判定
        assertEquals(param, value);
    }

    /**
     * testSetScope01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) scope:"abc"<br>
     *         (状態) scope:null<br>
     *         
     * <br>
     * 期待値：(状態変化) scope:"abc"<br>
     *         
     * <br>
     * ※正常系一件のみテスト
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetScope01() throws Exception {
        // 前処理
        String param = "abc";
        UTUtil.setPrivateField(pageLinks, "scope", null);

        // テスト実施
        pageLinks.setScope(param);

        // 判定
        assertEquals(param, UTUtil.getPrivateField(pageLinks, "scope"));
    }

    /**
     * testGetSubmit01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(状態) submit:true<br>
     *         
     * <br>
     * 期待値：(戻り値) boolean:true<br>
     *         
     * <br>
     * ※正常系一件のみテスト
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetSubmit01() throws Exception {
        // 前処理
        Boolean param = new Boolean(true);
        UTUtil.setPrivateField(pageLinks, "submit", param);

        // テスト実施
        boolean value = pageLinks.getSubmit();

        // 判定
        assertTrue(value);
    }

    /**
     * testSetSubmit01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) submit:true<br>
     *         (状態) submit:false<br>
     *         
     * <br>
     * 期待値：(状態変化) submit:true<br>
     *         
     * <br>
     * ※正常系一件のみテスト
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetSubmit01() throws Exception {
        // 前処理
        boolean param = true;
        UTUtil.setPrivateField(pageLinks, "submit", new Boolean(false));

        // テスト実施
        pageLinks.setSubmit(param);

        // 判定
        boolean result = ((Boolean) UTUtil.getPrivateField(
                pageLinks, "submit")).booleanValue();
        assertTrue(result);
    }

    /**
     * testGetForward01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(状態) forward:true<br>
     *         
     * <br>
     * 期待値：(戻り値) boolean:true<br>
     *         
     * <br>
     * ※正常系一件のみテスト
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetForward01() throws Exception {
        // 前処理
        Boolean param = new Boolean(true);
        UTUtil.setPrivateField(pageLinks, "forward", param);

        // テスト実施
        boolean value = pageLinks.getForward();

        // 判定
        assertTrue(value);
    }

    /**
     * testSetForward01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) forward:true<br>
     *         (状態) forward:false<br>
     *         
     * <br>
     * 期待値：(状態変化) forward:true<br>
     *         
     * <br>
     * ※正常系一件のみテスト
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetForward01() throws Exception {
        // 前処理
        boolean param = true;
        UTUtil.setPrivateField(pageLinks, "forward", new Boolean(false));

        // テスト実施
        pageLinks.setForward(param);

        // 判定
        boolean result = ((Boolean) UTUtil.getPrivateField(
                pageLinks, "forward")).booleanValue();
        assertTrue(result);
    }

    /**
     * testGetEvent01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(状態) event:"abc"<br>
     *         
     * <br>
     * 期待値：(戻り値) String:"abc"<br>
     *         
     * <br>
     * ※正常系一件のみテスト
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetEvent01() throws Exception {
        // 前処理
        String param = "abc";
        UTUtil.setPrivateField(pageLinks, "event", param);

        // テスト実施
        String value = pageLinks.getEvent();

        // 判定
        assertEquals(param, value);
    }

    /**
     * testSetEvent01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) event:"abc"<br>
     *         (状態) event:null<br>
     *         
     * <br>
     * 期待値：(状態変化) event:"abc"<br>
     *         
     * <br>
     * ※正常系一件のみテスト
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetEvent01() throws Exception {
        // 前処理
        String param = "abc";
        UTUtil.setPrivateField(pageLinks, "event", null);

        // テスト実施
        pageLinks.setEvent(param);

        // 判定
        assertEquals(param, UTUtil.getPrivateField(pageLinks, "event"));
    }

    /**
     * testGetResetIndex01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(状態) resetIndex:true<br>
     *         
     * <br>
     * 期待値：(戻り値) boolean:true<br>
     *         
     * <br>
     * ※正常系一件のみテスト
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetResetIndex01() throws Exception {
        // 前処理
        Boolean param = new Boolean(true);
        UTUtil.setPrivateField(pageLinks, "resetIndex", param);

        // テスト実施
        boolean value = pageLinks.getResetIndex();

        // 判定
        assertTrue(value);
    }

    /**
     * testSetResetIndex01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) resetIndex:true<br>
     *         (状態) resetIndex:false<br>
     *         
     * <br>
     * 期待値：(状態変化) resetIndex:true<br>
     *         
     * <br>
     * ※正常系一件のみテスト
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetResetIndex01() throws Exception {
        // 前処理
        boolean param = true;
        UTUtil.setPrivateField(pageLinks, "resetIndex", new Boolean(false));

        // テスト実施
        pageLinks.setResetIndex(param);

        // 判定
        boolean result = ((Boolean) UTUtil.getPrivateField(
                pageLinks, "resetIndex")).booleanValue();
        assertTrue(result);
    }

    /**
     * testGetCurrentPageIndex01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(状態) currentPageIndex:"abc"<br>
     *         
     * <br>
     * 期待値：(戻り値) String:"abc"<br>
     *         
     * <br>
     * ※正常系一件のみテスト
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetCurrentPageIndex01() throws Exception {
        // 前処理
        String param = "abc";
        UTUtil.setPrivateField(pageLinks, "currentPageIndex", param);

        // テスト実施
        String value = pageLinks.getCurrentPageIndex();

        // 判定
        assertEquals(param, value);
    }

    /**
     * testSetCurrentPageIndex01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) currentPageIndex:"abc"<br>
     *         (状態) currentPageIndex:null<br>
     *         
     * <br>
     * 期待値：(状態変化) currentPageIndex:"abc"<br>
     *         
     * <br>
     * ※正常系一件のみテスト
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetCurrentPageIndex01() throws Exception {
        // 前処理
        String param = "abc";
        UTUtil.setPrivateField(pageLinks, "currentPageIndex", null);

        // テスト実施
        pageLinks.setCurrentPageIndex(param);

        // 判定
        assertEquals(param, UTUtil.getPrivateField(pageLinks, "currentPageIndex"));
    }

    /**
     * testGetTotalPageCount01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(状態) totalPageCount:"abc"<br>
     *         
     * <br>
     * 期待値：(戻り値) String:"abc"<br>
     *         
     * <br>
     * ※正常系一件のみテスト
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetTotalPageCount01() throws Exception {
        // 前処理
        String param = "abc";
        UTUtil.setPrivateField(pageLinks, "totalPageCount", param);

        // テスト実施
        String value = pageLinks.getTotalPageCount();

        // 判定
        assertEquals(param, value);
    }

    /**
     * testSetTotalPageCount01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) totalPageCount:"abc"<br>
     *         (状態) totalPageCount:null<br>
     *         
     * <br>
     * 期待値：(状態変化) totalPageCount:"abc"<br>
     *         
     * <br>
     * ※正常系一件のみテスト
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetTotalPageCount01() throws Exception {
        // 前処理
        String param = "abc";
        UTUtil.setPrivateField(pageLinks, "totalPageCount", null);

        // テスト実施
        pageLinks.setTotalPageCount(param);

        // 判定
        assertEquals(param, UTUtil.getPrivateField(pageLinks, "totalPageCount"));
    }

    /**
     * testDoStartTag01()
     * <br><br>
     * 
     * (異常系)
     * <br>
     * 観点：G
     * <br><br>
     * 入力値：(状態) submit:false<br>
     *         (状態) action:null<br>
     *         
     * <br>
     * 期待値：(状態変化) 例外:クラス：JspException<br>
     *                    メッセージ：「Action attribute is required when submit 
     *                    attribute is “false”.」<br>
     *         (状態変化) ログ:メッセージ：<br>
     *                    「Action attribute is required when submit attribute 
     *                    is "false".」<br>
     *         
     * <br>
     * submit属性がfalseで、action属性がnullの場合
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testDoStartTag01() throws Exception {
        // 前処理
        pageLinks = (PageLinksTag_PageLinksTagStub01) TagUTUtil.create(
                PageLinksTag_PageLinksTagStub01.class);
        UTUtil.setPrivateField(pageLinks, "submit", new Boolean(false));
        UTUtil.setPrivateField(pageLinks, "action", null);

        // テスト実施
        try {
            pageLinks.doStartTag();
            fail();
        } catch (JspException e) {
            // 判定
            assertTrue(LogUTUtil.checkError(
                    PageLinksTag.ERROR_MESSAGE_ACTION_REQUIRED));
            assertEquals(
                    PageLinksTag.ERROR_MESSAGE_ACTION_REQUIRED, e.getMessage());
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
     * 入力値：(状態) submit:false<br>
     *         (状態) action:""<br>
     *         
     * <br>
     * 期待値：(状態変化) 例外:クラス：JspException<br>
     *                    メッセージ：「Action attribute is required when submit 
     *                    attribute is “false”.」<br>
     *         (状態変化) ログ:メッセージ：<br>
     *                    「Action attribute is required when submit attribute 
     *                    is "false".」<br>
     *         
     * <br>
     * submit属性がfalseで、action属性が空白の場合
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testDoStartTag02() throws Exception {
        // 前処理
        pageLinks = (PageLinksTag_PageLinksTagStub01) TagUTUtil.create(
                PageLinksTag_PageLinksTagStub01.class);
        UTUtil.setPrivateField(pageLinks, "submit", new Boolean(false));
        UTUtil.setPrivateField(pageLinks, "action", "");

        // テスト実施
        try {
            pageLinks.doStartTag();
            fail();
        } catch (JspException e) {
            // 判定
            assertTrue(LogUTUtil.checkError(
                    PageLinksTag.ERROR_MESSAGE_ACTION_REQUIRED));
            assertEquals(
                    PageLinksTag.ERROR_MESSAGE_ACTION_REQUIRED, e.getMessage());
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
     * 入力値：(状態) submit:false<br>
     *         (状態) action:"/list"<br>
     *         (状態) pageContext:not null<br>
     *                ["bean"= <br>
     *                new PageLinksTag_BeanStub01(){<br>
     *                row="a", index="0", total="100"}]<br>
     *         (状態) name:"bean"<br>
     *         (状態) rowProperty:"row"<br>
     *         (状態) indexProperty:"index"<br>
     *         (状態) totalProperty:"total"<br>
     *         
     * <br>
     * 期待値：(状態変化) 例外:クラス：JspException<br>
     *                    メッセージ：-<br>
     *                    ラップした例外：NumberFormatException<br>
     *         (状態変化) ログ:メッセージ：
     *         NumberFormatException#getMessage()の値<br>
     *         
     * <br>
     * 取得した表示行数が数値に変換できない場合
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testDoStartTag03() throws Exception {
        // 前処理
        pageLinks = (PageLinksTag_PageLinksTagStub01) TagUTUtil.create(
                PageLinksTag_PageLinksTagStub01.class);
        UTUtil.setPrivateField(pageLinks, "submit", new Boolean(false));
        UTUtil.setPrivateField(pageLinks, "action", "/list");
        UTUtil.setPrivateField(pageLinks, "name", "bean");
        UTUtil.setPrivateField(pageLinks, "rowProperty", "row");
        UTUtil.setPrivateField(pageLinks, "indexProperty", "index");
        UTUtil.setPrivateField(pageLinks, "totalProperty", "total");
        PageLinksTag_BeanStub01 bean = new PageLinksTag_BeanStub01();
        bean.setRow("a");
        bean.setIndex("0");
        bean.setTotal("100");

        // Mockオブジェクトの設定
        MockPageContext pageContext
            = (MockPageContext) TagUTUtil.getPageContext(pageLinks);
        pageContext.setAttribute("bean", bean);

        // テスト実施
        try {
            pageLinks.doStartTag();
            fail();
        } catch (JspException e) {
            // 判定
            assertEquals(NumberFormatException.class.getName(),
                    e.getRootCause().getClass().getName());
            assertTrue(LogUTUtil.checkError(e.getRootCause().getMessage()));
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
     * 入力値：(状態) submit:false<br>
     *         (状態) action:"/list"<br>
     *         (状態) pageContext:not null<br>
     *                ["bean"= <br>
     *                new PageLinksTag_BeanStub01(){<br>
     *                row="10", index="a", total="100"}]<br>
     *         (状態) name:"bean"<br>
     *         (状態) rowProperty:"row"<br>
     *         (状態) indexProperty:"index"<br>
     *         (状態) totalProperty:"total"<br>
     *         
     * <br>
     * 期待値：(状態変化) 例外:クラス：JspException<br>
     *                    メッセージ：-<br>
     *                    ラップした例外：NumberFormatException<br>
     *         (状態変化) ログ:メッセージ：
     *         NumberFormatException#getMessage()の値<br>
     *         
     * <br>
     * 取得した表示開始インデックスが数値に変換できない場合
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testDoStartTag04() throws Exception {
        // 前処理
        pageLinks = (PageLinksTag_PageLinksTagStub01) TagUTUtil.create(
                PageLinksTag_PageLinksTagStub01.class);
        UTUtil.setPrivateField(pageLinks, "submit", new Boolean(false));
        UTUtil.setPrivateField(pageLinks, "action", "/list");
        UTUtil.setPrivateField(pageLinks, "name", "bean");
        UTUtil.setPrivateField(pageLinks, "rowProperty", "row");
        UTUtil.setPrivateField(pageLinks, "indexProperty", "index");
        UTUtil.setPrivateField(pageLinks, "totalProperty", "total");
        PageLinksTag_BeanStub01 bean = new PageLinksTag_BeanStub01();
        bean.setRow("10");
        bean.setIndex("a");
        bean.setTotal("100");

        // Mockオブジェクトの設定
        MockPageContext pageContext
            = (MockPageContext) TagUTUtil.getPageContext(pageLinks);
        pageContext.setAttribute("bean", bean);

        // テスト実施
        try {
            pageLinks.doStartTag();
            fail();
        } catch (JspException e) {
            // 判定
            assertEquals(NumberFormatException.class.getName(),
                    e.getRootCause().getClass().getName());
            assertTrue(LogUTUtil.checkError(e.getRootCause().getMessage()));
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
     * 入力値：(状態) submit:false<br>
     *         (状態) action:"/list"<br>
     *         (状態) pageContext:not null<br>
     *                ["bean"= <br>
     *                new PageLinksTag_BeanStub01(){<br>
     *                row="10", index="0", total="a"}]<br>
     *         (状態) name:"bean"<br>
     *         (状態) rowProperty:"row"<br>
     *         (状態) indexProperty:"index"<br>
     *         (状態) totalProperty:"total"<br>
     *         
     * <br>
     * 期待値：(状態変化) 例外:クラス：JspException<br>
     *                    メッセージ：-<br>
     *                    ラップした例外：NumberFormatException<br>
     *         (状態変化) ログ:メッセージ：
     *         NumberFormatException#getMessage()の値<br>
     *         
     * <br>
     * 取得した全件数が数値に変換できない場合
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testDoStartTag05() throws Exception {
        // 前処理
        pageLinks = (PageLinksTag_PageLinksTagStub01) TagUTUtil.create(
                PageLinksTag_PageLinksTagStub01.class);
        UTUtil.setPrivateField(pageLinks, "submit", new Boolean(false));
        UTUtil.setPrivateField(pageLinks, "action", "/list");
        UTUtil.setPrivateField(pageLinks, "name", "bean");
        UTUtil.setPrivateField(pageLinks, "rowProperty", "row");
        UTUtil.setPrivateField(pageLinks, "indexProperty", "index");
        UTUtil.setPrivateField(pageLinks, "totalProperty", "total");
        PageLinksTag_BeanStub01 bean = new PageLinksTag_BeanStub01();
        bean.setRow("10");
        bean.setIndex("0");
        bean.setTotal("a");

        // Mockオブジェクトの設定
        MockPageContext pageContext
            = (MockPageContext) TagUTUtil.getPageContext(pageLinks);
        pageContext.setAttribute("bean", bean);

        // テスト実施
        try {
            pageLinks.doStartTag();
            fail();
        } catch (JspException e) {
            // 判定
            assertEquals(NumberFormatException.class.getName(),
                    e.getRootCause().getClass().getName());
            assertTrue(LogUTUtil.checkError(e.getRootCause().getMessage()));
        }
    }

    /**
     * testDoStartTag06()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(状態) submit:false<br>
     *         (状態) action:"/list"<br>
     *         (状態) pageContext:not null<br>
     *                ["bean"= <br>
     *                new PageLinksTag_BeanStub01(){<br>
     *                row="10", index=null, total=null}]<br>
     *         (状態) name:"bean"<br>
     *         (状態) rowProperty:"row"<br>
     *         (状態) indexProperty:"index"<br>
     *         (状態) totalProperty:"total"<br>
     *         
     * <br>
     * 期待値：(戻り値) int:EVAL_BODY_INCLUDE<br>
     *         (状態変化) JspWriter:"abc"が出力されることを確認<br>
     *         (状態変化) addPrevLink()の<br>
     *                    呼び確認:引数が<br>
     *                    sb：new StringBuilder()<br>
     *                    row：10<br>
     *                    startIndex：0<br>
     *                    totalCount：0<br>
     *                    で呼び出されることを確認。<br>
     *                    スタブクラスのこのメソッドの最後に、
     *                    sbに文字列"a"を設定する。<br>
     *         (状態変化) addDirectLink()の<br>
     *                    呼び確認:引数が<br>
     *                    sb：new StringBuilder()<br>
     *                    row：10<br>
     *                    startIndex：0<br>
     *                    totalCount：0<br>
     *                    で呼び出されることを確認<br>
     *                    スタブクラスのこのメソッドの最後に、
     *                    sbに文字列"b"を設定する。<br>
     *         (状態変化) addNextLink()の<br>
     *                    呼び確認:引数が<br>
     *                    sb：new StringBuilder()<br>
     *                    row：10<br>
     *                    startIndex：0<br>
     *                    totalCount：0<br>
     *                    で呼び出されることを確認<br>
     *                    スタブクラスのこのメソッドの最後に、
     *                    sbに文字列"c"を設定する。<br>
     *         
     * <br>
     * 指定されたBeanのプロパティの値がすべてnullの場合、
     * デフォルトが適用されてリンクを出力する場合
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testDoStartTag06() throws Exception {
        // 前処理
        PageLinksTag_PageLinksTagStub01 pageLinks
            = (PageLinksTag_PageLinksTagStub01) TagUTUtil.create(
                PageLinksTag_PageLinksTagStub01.class);
        UTUtil.setPrivateField(pageLinks, "submit", new Boolean(false));
        UTUtil.setPrivateField(pageLinks, "action", "/list");
        UTUtil.setPrivateField(pageLinks, "name", "bean");
        UTUtil.setPrivateField(pageLinks, "rowProperty", "row");
        UTUtil.setPrivateField(pageLinks, "indexProperty", "index");
        UTUtil.setPrivateField(pageLinks, "totalProperty", "total");
        PageLinksTag_BeanStub01 bean = new PageLinksTag_BeanStub01();
        bean.setRow("10");

        // Mockオブジェクトの設定
        MockPageContext pageContext
            = (MockPageContext) TagUTUtil.getPageContext(pageLinks);
        pageContext.setAttribute("bean", bean);

        // テスト実施
        int value = pageLinks.doStartTag();

        // 判定
        assertEquals(Tag.EVAL_BODY_INCLUDE, value);
        assertFalse(pageLinks.isDefineHtmlCalled());
        assertFalse(pageLinks.isAddPrevSubmitCalled());
        assertFalse(pageLinks.isAddDirectSubmitCalled());
        assertFalse(pageLinks.isAddNextSubmitCalled());
        assertTrue(pageLinks.isAddPrevLinkCalled());
        assertTrue(pageLinks.isAddDirectLinkCalled());
        assertTrue(pageLinks.isAddNextLinkCalled());
        StringBuilder sb = new StringBuilder();
        assertEquals(sb.toString(), pageLinks.getAddPrevLinkSb().toString());
        assertEquals(10, pageLinks.getAddPrevLinkRow());
        assertEquals(0, pageLinks.getAddPrevLinkStartIndex());
        assertEquals(0, pageLinks.getAddPrevLinkTotalCount());
        sb.append("a");
        assertEquals(sb.toString(), pageLinks.getAddDirectLinkSb().toString());
        assertEquals(10, pageLinks.getAddDirectLinkRow());
        assertEquals(0, pageLinks.getAddDirectLinkStartIndex());
        assertEquals(0, pageLinks.getAddDirectLinkTotalCount());
        sb.append("b");
        assertEquals(sb.toString(), pageLinks.getAddNextLinkSb().toString());
        assertEquals(10, pageLinks.getAddNextLinkRow());
        assertEquals(0, pageLinks.getAddNextLinkStartIndex());
        assertEquals(0, pageLinks.getAddNextLinkTotalCount());
        BufferedReader reader = TagUTUtil.getOutputReader(pageLinks);
        sb.append("c");
        assertEquals(sb.toString(), reader.readLine());
        assertNull(reader.readLine());
    }

    /**
     * testDoStartTag07()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(状態) submit:false<br>
     *         (状態) action:"/list"<br>
     *         (状態) pageContext:not null<br>
     *                ["bean"= <br>
     *                new PageLinksTag_BeanStub01(){<br>
     *                row="10", index="", total=""}]<br>
     *         (状態) name:"bean"<br>
     *         (状態) rowProperty:"row"<br>
     *         (状態) indexProperty:"index"<br>
     *         (状態) totalProperty:"total"<br>
     *         
     * <br>
     * 期待値：(戻り値) int:EVAL_BODY_INCLUDE<br>
     *         (状態変化) JspWriter:"abc"が出力されることを確認<br>
     *         (状態変化) addPrevLink()の<br>
     *                    呼び確認:引数が<br>
     *                    sb：new StringBuilder()<br>
     *                    row：10<br>
     *                    startIndex：0<br>
     *                    totalCount：0<br>
     *                    で呼び出されることを確認。<br>
     *                    スタブクラスのこのメソッドの最後に、
     *                    sbに文字列"a"を設定する。<br>
     *         (状態変化) addDirectLink()の<br>
     *                    呼び確認:引数が<br>
     *                    sb：new StringBuilder()<br>
     *                    row：10<br>
     *                    startIndex：0<br>
     *                    totalCount：0<br>
     *                    で呼び出されることを確認<br>
     *                    スタブクラスのこのメソッドの最後に、
     *                    sbに文字列"b"を設定する。<br>
     *         (状態変化) addNextLink()の<br>
     *                    呼び確認:引数が<br>
     *                    sb：new StringBuilder()<br>
     *                    row：10<br>
     *                    startIndex：0<br>
     *                    totalCount：0<br>
     *                    で呼び出されることを確認<br>
     *                    スタブクラスのこのメソッドの最後に、
     *                    sbに文字列"c"を設定する。<br>
     *         
     * <br>
     * 指定されたBeanのプロパティの値がすべて空白の場合、
     * デフォルトが適用されてリンクを出力する場合
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testDoStartTag07() throws Exception {
        // 前処理
        PageLinksTag_PageLinksTagStub01 pageLinks
            = (PageLinksTag_PageLinksTagStub01) TagUTUtil.create(
                PageLinksTag_PageLinksTagStub01.class);
        UTUtil.setPrivateField(pageLinks, "submit", new Boolean(false));
        UTUtil.setPrivateField(pageLinks, "action", "/list");
        UTUtil.setPrivateField(pageLinks, "name", "bean");
        UTUtil.setPrivateField(pageLinks, "rowProperty", "row");
        UTUtil.setPrivateField(pageLinks, "indexProperty", "index");
        UTUtil.setPrivateField(pageLinks, "totalProperty", "total");
        PageLinksTag_BeanStub01 bean = new PageLinksTag_BeanStub01();
        bean.setRow("10");
        bean.setIndex("");
        bean.setTotal("");

        // Mockオブジェクトの設定
        MockPageContext pageContext
            = (MockPageContext) TagUTUtil.getPageContext(pageLinks);
        pageContext.setAttribute("bean", bean);

        // テスト実施
        int value = pageLinks.doStartTag();

        // 判定
        assertEquals(Tag.EVAL_BODY_INCLUDE, value);
        assertFalse(pageLinks.isDefineHtmlCalled());
        assertFalse(pageLinks.isAddPrevSubmitCalled());
        assertFalse(pageLinks.isAddDirectSubmitCalled());
        assertFalse(pageLinks.isAddNextSubmitCalled());
        assertTrue(pageLinks.isAddPrevLinkCalled());
        assertTrue(pageLinks.isAddDirectLinkCalled());
        assertTrue(pageLinks.isAddNextLinkCalled());
        StringBuilder sb = new StringBuilder();
        assertEquals(sb.toString(), pageLinks.getAddPrevLinkSb().toString());
        assertEquals(10, pageLinks.getAddPrevLinkRow());
        assertEquals(0, pageLinks.getAddPrevLinkStartIndex());
        assertEquals(0, pageLinks.getAddPrevLinkTotalCount());
        sb.append("a");
        assertEquals(sb.toString(), pageLinks.getAddDirectLinkSb().toString());
        assertEquals(10, pageLinks.getAddDirectLinkRow());
        assertEquals(0, pageLinks.getAddDirectLinkStartIndex());
        assertEquals(0, pageLinks.getAddDirectLinkTotalCount());
        sb.append("b");
        assertEquals(sb.toString(), pageLinks.getAddNextLinkSb().toString());
        assertEquals(10, pageLinks.getAddNextLinkRow());
        assertEquals(0, pageLinks.getAddNextLinkStartIndex());
        assertEquals(0, pageLinks.getAddNextLinkTotalCount());
        BufferedReader reader = TagUTUtil.getOutputReader(pageLinks);
        sb.append("c");
        assertEquals(sb.toString(), reader.readLine());
        assertNull(reader.readLine());
    }

    /**
     * testDoStartTag08()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(状態) submit:true<br>
     *         (状態) action:"/list"<br>
     *         (状態) pageContext:not null<br>
     *                ["bean"= <br>
     *                new PageLinksTag_BeanStub01(){<br>
     *                row="10", index="0", total="100"}]<br>
     *         (状態) name:"bean"<br>
     *         (状態) rowProperty:"row"<br>
     *         (状態) indexProperty:"index"<br>
     *         (状態) totalProperty:"total"<br>
     *         
     * <br>
     * 期待値：(戻り値) int:EVAL_BODY_INCLUDE<br>
     *         (状態変化) JspWriter:"abc"が出力されることを確認<br>
     *         (状態変化) defineHtml()の<br>
     *                    呼び確認:引数が<br>
     *                    row：10<br>
     *                    startIndex：0<br>
     *                    で呼び出されることを確認。<br>
     *         (状態変化) addPrevSubmit()の<br>
     *                    呼び確認:引数が<br>
     *                    sb：new StringBuilder()<br>
     *                    row：10<br>
     *                    startIndex：0<br>
     *                    totalCount：100<br>
     *                    で呼び出されることを確認<br>
     *                    スタブクラスのこのメソッドの最後に、
     *                    sbに文字列"a"を設定する。<br>
     *         (状態変化) addDirectSubmit()の<br>
     *                    呼び確認:引数が<br>
     *                    sb：new StringBuilder()<br>
     *                    row：10<br>
     *                    startIndex：0<br>
     *                    totalCount：100<br>
     *                    で呼び出されることを確認<br>
     *                    スタブクラスのこのメソッドの最後に、
     *                    sbに文字列"b"を設定する。<br>
     *         (状態変化) addNextSubmit()の<br>
     *                    呼び確認:引数が<br>
     *                    sb：new StringBuilder()<br>
     *                    row：10<br>
     *                    startIndex：0<br>
     *                    totalCount：100<br>
     *                    で呼び出されることを確認<br>
     *                    スタブクラスのこのメソッドの最後に、
     *                    sbに文字列"c"を設定する。<br>
     *         
     * <br>
     * 値が正常に取得変換でき、サブミットをするリンクを出力する場合
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testDoStartTag08() throws Exception {
        // 前処理
        PageLinksTag_PageLinksTagStub01 pageLinks
            = (PageLinksTag_PageLinksTagStub01) TagUTUtil.create(
                PageLinksTag_PageLinksTagStub01.class);
        UTUtil.setPrivateField(pageLinks, "submit", new Boolean(true));
        UTUtil.setPrivateField(pageLinks, "action", "/list");
        UTUtil.setPrivateField(pageLinks, "name", "bean");
        UTUtil.setPrivateField(pageLinks, "rowProperty", "row");
        UTUtil.setPrivateField(pageLinks, "indexProperty", "index");
        UTUtil.setPrivateField(pageLinks, "totalProperty", "total");
        PageLinksTag_BeanStub01 bean = new PageLinksTag_BeanStub01();
        bean.setRow("10");
        bean.setIndex("0");
        bean.setTotal("100");

        // Mockオブジェクトの設定
        MockPageContext pageContext
            = (MockPageContext) TagUTUtil.getPageContext(pageLinks);
        pageContext.setAttribute("bean", bean);

        // テスト実施
        int value = pageLinks.doStartTag();

        // 判定
        assertEquals(Tag.EVAL_BODY_INCLUDE, value);
        assertTrue(pageLinks.isDefineHtmlCalled());
        assertTrue(pageLinks.isAddPrevSubmitCalled());
        assertTrue(pageLinks.isAddDirectSubmitCalled());
        assertTrue(pageLinks.isAddNextSubmitCalled());
        assertFalse(pageLinks.isAddPrevLinkCalled());
        assertFalse(pageLinks.isAddDirectLinkCalled());
        assertFalse(pageLinks.isAddNextLinkCalled());
        StringBuilder sb = new StringBuilder();
        assertEquals(sb.toString(), pageLinks.getAddPrevSubmitSb().toString());
        assertEquals(10, pageLinks.getAddPrevSubmitRow());
        assertEquals(0, pageLinks.getAddPrevSubmitStartIndex());
        assertEquals(100, pageLinks.getAddPrevSubmitTotalCount());
        sb.append("a");
        assertEquals(sb.toString(),
                pageLinks.getAddDirectSubmitSb().toString());
        assertEquals(10, pageLinks.getAddDirectSubmitRow());
        assertEquals(0, pageLinks.getAddDirectSubmitStartIndex());
        assertEquals(100, pageLinks.getAddDirectSubmitTotalCount());
        sb.append("b");
        assertEquals(sb.toString(), pageLinks.getAddNextSubmitSb().toString());
        assertEquals(10, pageLinks.getAddNextSubmitRow());
        assertEquals(0, pageLinks.getAddNextSubmitStartIndex());
        assertEquals(100, pageLinks.getAddNextSubmitTotalCount());
        BufferedReader reader = TagUTUtil.getOutputReader(pageLinks);
        sb.append("c");
        assertEquals(sb.toString(), reader.readLine());
        assertNull(reader.readLine());
    }

    /**
     * testDoStartTag09()
     * <br><br>
     * 
     * (異常系)
     * <br>
     * 観点：G
     * <br><br>
     * 入力値：(状態) submit:true<br>
     *         (状態) action:"/list"<br>
     *         (状態) pageContext:not null<br>
     *                ["bean"= <br>
     *                new PageLinksTag_BeanStub01(){<br>
     *                row="10", index="0", total="100"}]<br>
     *         (状態) name:"bean"<br>
     *         (状態) rowProperty:"row"<br>
     *         (状態) indexProperty:"index"<br>
     *         (状態) totalProperty:"total"<br>
     *         (状態) JspWriter#println():IOExceptionが発生<br>
     *         
     * <br>
     * 期待値：(状態変化) 例外:クラス：JspTagException<br>
     *                    メッセージ：-<br>
     *                    ラップした例外：-<br>
     *         
     * <br>
     * JspWriterでIOExceptionが発生した場合
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testDoStartTag09() throws Exception {
        // 前処理
        PageLinksTag_PageLinksTagStub01 pageLinks
            = (PageLinksTag_PageLinksTagStub01) TagUTUtil.create(
                PageLinksTag_PageLinksTagStub01.class);
        UTUtil.setPrivateField(pageLinks, "submit", new Boolean(true));
        UTUtil.setPrivateField(pageLinks, "action", "/list");
        UTUtil.setPrivateField(pageLinks, "name", "bean");
        UTUtil.setPrivateField(pageLinks, "rowProperty", "row");
        UTUtil.setPrivateField(pageLinks, "indexProperty", "index");
        UTUtil.setPrivateField(pageLinks, "totalProperty", "total");
        PageLinksTag_BeanStub01 bean = new PageLinksTag_BeanStub01();
        bean.setRow("10");
        bean.setIndex("0");
        bean.setTotal("100");

        // Mockオブジェクトの設定
        MockPageContext pageContext
            = (MockPageContext) TagUTUtil.getPageContext(pageLinks);
        pageContext.setAttribute("bean", bean);

        // テスト用JspWriterの生成
        Exception_JspWriterImpl out =
            new Exception_JspWriterImpl();
        out.setTrue();

        // 生成・設定したテスト用JspWriterをPageContextにセット
        UTUtil.setPrivateField(pageContext, "jspWriter", out);

        // テスト実施
        try {
            pageLinks.doStartTag();
            fail();
        } catch (JspTagException e) {
            // 判定
            if (e.getMessage().indexOf("IOException") == -1) {
                //結果確認
                fail();
            }
        }
    }

    /**
     * testDoStartTag10()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(状態) submit:false<br>
     *         (状態) action:"/list"<br>
     *         (状態) pageContext:not null<br>
     *                ["bean"= <br>
     *                new PageLinksTag_BeanStub01(){<br>
     *                row=null, index=null, total=null}]<br>
     *         (状態) name:"bean"<br>
     *         (状態) rowProperty:"row"<br>
     *         (状態) indexProperty:"index"<br>
     *         (状態) totalProperty:"total"<br>
     *         
     * <br>
     * 期待値：(戻り値) int:EVAL_BODY_INCLUDE<br>
     *         (状態変化) ログ:メッセージ："Row param is illegal."<br>
     *         
     * <br>
     * 指定されたBeanのrowプロパティの値がnullの場合、
     * 警告ログを出力して終了することを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testDoStartTag10() throws Exception {
        // 前処理
        PageLinksTag_PageLinksTagStub01 pageLinks
            = (PageLinksTag_PageLinksTagStub01) TagUTUtil.create(
                PageLinksTag_PageLinksTagStub01.class);
        UTUtil.setPrivateField(pageLinks, "submit", new Boolean(false));
        UTUtil.setPrivateField(pageLinks, "action", "/list");
        UTUtil.setPrivateField(pageLinks, "name", "bean");
        UTUtil.setPrivateField(pageLinks, "rowProperty", "row");
        UTUtil.setPrivateField(pageLinks, "indexProperty", "index");
        UTUtil.setPrivateField(pageLinks, "totalProperty", "total");
        PageLinksTag_BeanStub01 bean = new PageLinksTag_BeanStub01();

        // Mockオブジェクトの設定
        MockPageContext pageContext
            = (MockPageContext) TagUTUtil.getPageContext(pageLinks);
        pageContext.setAttribute("bean", bean);

        // テスト実施
        int value = pageLinks.doStartTag();

        // 判定
        assertEquals(Tag.EVAL_BODY_INCLUDE, value);
        assertTrue(LogUTUtil.checkWarn(PageLinksTag.WARN_MESSAGE_ILLEGAL_ROW));
        BufferedReader reader = TagUTUtil.getOutputReader(pageLinks);
        assertNull(reader.readLine());
    }

    /**
     * testDoStartTag11()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(状態) submit:false<br>
     *         (状態) action:"/list"<br>
     *         (状態) pageContext:not null<br>
     *                ["bean"= <br>
     *                new PageLinksTag_BeanStub01(){<br>
     *                row="", index="", total=""}]<br>
     *         (状態) name:"bean"<br>
     *         (状態) rowProperty:"row"<br>
     *         (状態) indexProperty:"index"<br>
     *         (状態) totalProperty:"total"<br>
     *         
     * <br>
     * 期待値：(戻り値) int:EVAL_BODY_INCLUDE<br>
     * 指定されたBeanのプロパティの値がすべてnullの場合、
     * デフォルトが適用されてリンクを出力する場合
     * 
     * <br>
     * 指定されたBeanのrowプロパティの値が空白の場合、
     * 警告ログを出力して終了することを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testDoStartTag11() throws Exception {
        // 前処理
        PageLinksTag_PageLinksTagStub01 pageLinks
            = (PageLinksTag_PageLinksTagStub01) TagUTUtil.create(
                PageLinksTag_PageLinksTagStub01.class);
        UTUtil.setPrivateField(pageLinks, "submit", new Boolean(false));
        UTUtil.setPrivateField(pageLinks, "action", "/list");
        UTUtil.setPrivateField(pageLinks, "name", "bean");
        UTUtil.setPrivateField(pageLinks, "rowProperty", "row");
        UTUtil.setPrivateField(pageLinks, "indexProperty", "index");
        UTUtil.setPrivateField(pageLinks, "totalProperty", "total");
        PageLinksTag_BeanStub01 bean = new PageLinksTag_BeanStub01();
        bean.setRow("");
        bean.setIndex("");
        bean.setTotal("");

        // Mockオブジェクトの設定
        MockPageContext pageContext
            = (MockPageContext) TagUTUtil.getPageContext(pageLinks);
        pageContext.setAttribute("bean", bean);

        // テスト実施
        int value = pageLinks.doStartTag();

        // 判定
        assertEquals(Tag.EVAL_BODY_INCLUDE, value);
        assertTrue(LogUTUtil.checkWarn(PageLinksTag.WARN_MESSAGE_ILLEGAL_ROW));
        BufferedReader reader = TagUTUtil.getOutputReader(pageLinks);
        assertNull(reader.readLine());
    }

    /**
     * testDoStartTag12()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(状態) id:"listPageLinks"<br>
     *         (状態) submit:true<br>
     *         (状態) action:"/list"<br>
     *         (状態) pageContext:not null<br>
     *                ["bean"= <br>
     *                new PageLinksTag_BeanStub01(){<br>
     *                row="10", index="0", total="100"}]<br>
     *         (状態) name:"bean"<br>
     *         (状態) rowProperty:"row"<br>
     *         (状態) indexProperty:"index"<br>
     *         (状態) totalProperty:"total"<br>
     *         (状態) currentPageIndex:null<br>
     *         (状態) totalPageCount:null<br>
     * 
     * <br>
     * 期待値：(戻り値) int:EVAL_BODY_INCLUDE<br>
     *         (状態変化) JspWriter:何も出力されないこと。<br>
     *         (状態変化) pageContext.getAttribut("listPageLinks"):"abc"<br>
     *         (状態変化) defineHtml()の<br>
     *                    呼び確認:引数が<br>
     *                    sb：new StringBuilder()<br>
     *                    row：10<br>
     *                    startIndex：0<br>
     *                    totalCount：100<br>
     *                    で呼び出されることを確認。<br>
     *         (状態変化) addPrevSubmit()の<br>
     *                    呼び確認:引数が<br>
     *                    sb：new StringBuilder()<br>
     *                    row：10<br>
     *                    startIndex：0<br>
     *                    totalCount：100<br>
     *                    で呼び出されることを確認<br>
     *                    スタブクラスのこのメソッドの最後に、
     *                    sbに文字列"a"を設定する。<br>
     *         (状態変化) addDirectSubmit()の<br>
     *                    呼び確認:引数が<br>
     *                    sb：new StringBuilder()<br>
     *                    row：10<br>
     *                    startIndex：0<br>
     *                    totalCount：100<br>
     *                    で呼び出されることを確認<br>
     *                    スタブクラスのこのメソッドの最後に、
     *                    sbに文字列"b"を設定する。<br>
     *         (状態変化) addNextSubmit()の<br>
     *                    呼び確認:引数が<br>
     *                    sb：new StringBuilder()<br>
     *                    row：10<br>
     *                    startIndex：0<br>
     *                    totalCount：100<br>
     *                    で呼び出されることを確認<br>
     *                    スタブクラスのこのメソッドの最後に、
     *                    sbに文字列"c"を設定する。<br>
     *         
     * <br>
     * 値が正常に取得変換でき、サブミットをするリンクを出力する場合
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testDoStartTag12() throws Exception {
        // 前処理
        PageLinksTag_PageLinksTagStub01 pageLinks
            = (PageLinksTag_PageLinksTagStub01) TagUTUtil.create(
                PageLinksTag_PageLinksTagStub01.class);
        UTUtil.setPrivateField(pageLinks, "id", "listPageLinks");
        UTUtil.setPrivateField(pageLinks, "submit", new Boolean(true));
        UTUtil.setPrivateField(pageLinks, "action", "/list");
        UTUtil.setPrivateField(pageLinks, "name", "bean");
        UTUtil.setPrivateField(pageLinks, "rowProperty", "row");
        UTUtil.setPrivateField(pageLinks, "indexProperty", "index");
        UTUtil.setPrivateField(pageLinks, "totalProperty", "total");
        PageLinksTag_BeanStub01 bean = new PageLinksTag_BeanStub01();
        bean.setRow("10");
        bean.setIndex("0");
        bean.setTotal("100");

        // Mockオブジェクトの設定
        MockPageContext pageContext
            = (MockPageContext) TagUTUtil.getPageContext(pageLinks);
        pageContext.setAttribute("bean", bean);

        // テスト実施
        int value = pageLinks.doStartTag();

        // 判定
        StringBuilder sb = new StringBuilder();
        assertEquals(Tag.EVAL_BODY_INCLUDE, value);
        assertTrue(pageLinks.isDefineHtmlCalled());
        assertTrue(pageLinks.isAddPrevSubmitCalled());
        assertTrue(pageLinks.isAddDirectSubmitCalled());
        assertTrue(pageLinks.isAddNextSubmitCalled());
        assertFalse(pageLinks.isAddPrevLinkCalled());
        assertFalse(pageLinks.isAddDirectLinkCalled());
        assertFalse(pageLinks.isAddNextLinkCalled());
        assertEquals(10, pageLinks.getDefineHtmlRow());
        assertEquals(0, pageLinks.getDefineHtmlStartIndex());
        assertEquals(100, pageLinks.getDefineHtmlTotalCount());
        assertEquals(sb.toString(), pageLinks.getAddPrevSubmitSb().toString());
        assertEquals(10, pageLinks.getAddPrevSubmitRow());
        assertEquals(0, pageLinks.getAddPrevSubmitStartIndex());
        assertEquals(100, pageLinks.getAddPrevSubmitTotalCount());
        sb.append("a");
        assertEquals(sb.toString(),
                pageLinks.getAddDirectSubmitSb().toString());
        assertEquals(10, pageLinks.getAddDirectSubmitRow());
        assertEquals(0, pageLinks.getAddDirectSubmitStartIndex());
        assertEquals(100, pageLinks.getAddDirectSubmitTotalCount());
        sb.append("b");
        assertEquals(sb.toString(), pageLinks.getAddNextSubmitSb().toString());
        assertEquals(10, pageLinks.getAddNextSubmitRow());
        assertEquals(0, pageLinks.getAddNextSubmitStartIndex());
        assertEquals(100, pageLinks.getAddNextSubmitTotalCount());
        sb.append("c");
        String resultValue = (String) pageContext.getAttribute("listPageLinks");
        assertEquals(sb.toString(), resultValue);
        BufferedReader reader = TagUTUtil.getOutputReader(pageLinks);
        assertNull(reader.readLine());
    }

    /**
     * testDoStartTag13()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(状態) id:""<br>
     *         (状態) submit:true<br>
     *         (状態) action:"/list"<br>
     *         (状態) pageContext:not null<br>
     *                ["bean"= <br>
     *                new PageLinksTag_BeanStub01(){<br>
     *                row="10", index="0", total="100"}]<br>
     *         (状態) name:"bean"<br>
     *         (状態) rowProperty:"row"<br>
     *         (状態) indexProperty:"index"<br>
     *         (状態) totalProperty:"total"<br>
     *         
     * <br>
     * 期待値：(戻り値) int:EVAL_BODY_INCLUDE<br>
     *         (状態変化) JspWriter:"abc"が出力されることを確認<br>
     *         (状態変化) defineHtml()の<br>
     *                    呼び確認:引数が<br>
     *                    sb：new StringBuilder()<br>
     *                    row：10<br>
     *                    startIndex：0<br>
     *                    totalCount：100<br>
     *                    で呼び出されることを確認。<br>
     *         (状態変化) addPrevSubmit()の<br>
     *                    呼び確認:引数が<br>
     *                    sb：new StringBuilder()<br>
     *                    row：10<br>
     *                    startIndex：0<br>
     *                    totalCount：100<br>
     *                    で呼び出されることを確認<br>
     *                    スタブクラスのこのメソッドの最後に、
     *                    sbに文字列"a"を設定する。<br>
     *         (状態変化) addDirectSubmit()の<br>
     *                    呼び確認:引数が<br>
     *                    sb：new StringBuilder()<br>
     *                    row：10<br>
     *                    startIndex：0<br>
     *                    totalCount：100<br>
     *                    で呼び出されることを確認<br>
     *                    スタブクラスのこのメソッドの最後に、
     *                    sbに文字列"b"を設定する。<br>
     *         (状態変化) addNextSubmit()の<br>
     *                    呼び確認:引数が<br>
     *                    sb：new StringBuilder()<br>
     *                    row：10<br>
     *                    startIndex：0<br>
     *                    totalCount：100<br>
     *                    で呼び出されることを確認<br>
     *                    スタブクラスのこのメソッドの最後に、
     *                    sbに文字列"c"を設定する。<br>
     *         
     * <br>
     * 値が正常に取得変換でき、サブミットをするリンクを出力する場合
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testDoStartTag13() throws Exception {
        // 前処理
        PageLinksTag_PageLinksTagStub01 pageLinks
            = (PageLinksTag_PageLinksTagStub01) TagUTUtil.create(
                PageLinksTag_PageLinksTagStub01.class);
        UTUtil.setPrivateField(pageLinks, "id", "");
        UTUtil.setPrivateField(pageLinks, "submit", new Boolean(true));
        UTUtil.setPrivateField(pageLinks, "action", "/list");
        UTUtil.setPrivateField(pageLinks, "name", "bean");
        UTUtil.setPrivateField(pageLinks, "rowProperty", "row");
        UTUtil.setPrivateField(pageLinks, "indexProperty", "index");
        UTUtil.setPrivateField(pageLinks, "totalProperty", "total");
        PageLinksTag_BeanStub01 bean = new PageLinksTag_BeanStub01();
        bean.setRow("10");
        bean.setIndex("0");
        bean.setTotal("100");

        // Mockオブジェクトの設定
        MockPageContext pageContext
            = (MockPageContext) TagUTUtil.getPageContext(pageLinks);
        pageContext.setAttribute("bean", bean);

        // テスト実施
        int value = pageLinks.doStartTag();

        // 判定
        StringBuilder sb = new StringBuilder();
        assertEquals(Tag.EVAL_BODY_INCLUDE, value);
        assertTrue(pageLinks.isDefineHtmlCalled());
        assertTrue(pageLinks.isAddPrevSubmitCalled());
        assertTrue(pageLinks.isAddDirectSubmitCalled());
        assertTrue(pageLinks.isAddNextSubmitCalled());
        assertFalse(pageLinks.isAddPrevLinkCalled());
        assertFalse(pageLinks.isAddDirectLinkCalled());
        assertFalse(pageLinks.isAddNextLinkCalled());
        assertEquals(10, pageLinks.getDefineHtmlRow());
        assertEquals(0, pageLinks.getDefineHtmlStartIndex());
        assertEquals(100, pageLinks.getDefineHtmlTotalCount());
        assertEquals(sb.toString(), pageLinks.getAddPrevSubmitSb().toString());
        assertEquals(10, pageLinks.getAddPrevSubmitRow());
        assertEquals(0, pageLinks.getAddPrevSubmitStartIndex());
        assertEquals(100, pageLinks.getAddPrevSubmitTotalCount());
        sb.append("a");
        assertEquals(sb.toString(),
                pageLinks.getAddDirectSubmitSb().toString());
        assertEquals(10, pageLinks.getAddDirectSubmitRow());
        assertEquals(0, pageLinks.getAddDirectSubmitStartIndex());
        assertEquals(100, pageLinks.getAddDirectSubmitTotalCount());
        sb.append("b");
        assertEquals(sb.toString(), pageLinks.getAddNextSubmitSb().toString());
        assertEquals(10, pageLinks.getAddNextSubmitRow());
        assertEquals(0, pageLinks.getAddNextSubmitStartIndex());
        assertEquals(100, pageLinks.getAddNextSubmitTotalCount());
        sb.append("c");
        BufferedReader reader = TagUTUtil.getOutputReader(pageLinks);
        assertEquals(sb.toString(), reader.readLine());
        assertNull(reader.readLine());
    }

    /**
     * testDefineHtml01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) row:10<br>
     *         (引数) startIndex:0<br>
     *         (引数) totalCount:0<br>
     *         (状態) rowProperty:"row"<br>
     *         (状態) indexProperty:"index"<br>
     *         (状態) forward:true<br>
     *         (状態) event:"abs"<br>
     *         (状態) resetIndex:false<br>
     *         (状態) pageContextのPAGELINKS_JAVASCRIPT_KEY ＋eventの値:false<br>
     *         (状態) pageContextのPAGELINKS_JAVASCRIPT_KEY ＋rowPropertyの値:false<br>
     *         (状態) pageContextのPAGELINKS_JAVASCRIPT_KEY ＋indexPropertyの値:false<br>
     *         (状態) pageContextのPAGELINKS_JAVASCRIPT_KEY ＋"resetIndex"の値:false<br>
     *         (状態) pageContextのPAGELINKS_JAVASCRIPT_KEYの値:false<br>
     *         (状態) fromName:"pageLinks"<br>
     *         
     * <br>
     * 期待値：(状態変化) JspWriter:<input type=\"hidden\" name=\"row\" value=\"10\"/><br>
     *                    <input type=\"hidden\" name=\"index\" value=\"0\"/><br>
     *                    <input type=\"hidden\" name=\"abs\" value=\"\"/><br>
     *                    <script type=\"text/javascript\"><br>
     *                    <!--<br>
     *                      function pageLinkSubmit(rowObj, indexObj, row, startIndex){<br>
     *                        rowObj.value = row;<br>
     *                        indexObj.value = startIndex;<br>
     *                        document.pageLinks.abs.value = "forward_pageLinks";<br>
     *                        document.pageLinks.submit();<br>
     *                      }<br>
     *                    // --><br>
     *                    </script><br>
     *         (状態変化) pageContextのPAGELINKS_JAVASCRIPT_KEY ＋eventの値:true<br>
     *         (状態変化) pageContextのPAGELINKS_JAVASCRIPT_KEY ＋rowPropertyの値:true<br>
     *         (状態変化) pageContextのPAGELINKS_JAVASCRIPT_KEY ＋indexPropertyの値:true<br>
     *         (状態変化) pageContextのPAGELINKS_JAVASCRIPT_KEY ＋"resetIndex"の値:false<br>
     *         (状態変化) pageContextのPAGELINKS_JAVASCRIPT_KEYの値:true<br>
     *         
     * <br>
     * ページコンテキストのフラグがすべてfalseの場合
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testDefineHtml01() throws Exception {
        // 前処理
        UTUtil.setPrivateField(pageLinks, "rowProperty", "row");
        UTUtil.setPrivateField(pageLinks, "indexProperty", "index");
        UTUtil.setPrivateField(pageLinks, "forward", true);
        UTUtil.setPrivateField(pageLinks, "event", "abs");
        UTUtil.setPrivateField(pageLinks, "resetIndex", false);

        // Mockオブジェクトの設定
        MockPageContext pageContext
            = (MockPageContext) TagUTUtil.getPageContext(pageLinks);
        pageContext.setAttribute(PageLinksTag.PAGELINKS_JAVASCRIPT_KEY
                + "abs", false);
        pageContext.setAttribute(PageLinksTag.PAGELINKS_JAVASCRIPT_KEY
                + "row", false);
        pageContext.setAttribute(PageLinksTag.PAGELINKS_JAVASCRIPT_KEY
                + "index", false);
        pageContext.setAttribute(PageLinksTag.PAGELINKS_JAVASCRIPT_KEY
                + "resetIndex", false);
        pageContext.setAttribute(PageLinksTag.PAGELINKS_JAVASCRIPT_KEY, false);

        ActionMapping mapping = new ActionMapping();
        mapping.setName("pageLinks");
        pageContext.getRequest().setAttribute(Globals.MAPPING_KEY, mapping);

        // テスト実施
        pageLinks.defineHtml(10, 0, 0);

        // 判定
        BufferedReader reader = TagUTUtil.getOutputReader(pageLinks);
        assertEquals("<input type=\"hidden\" name=\"row\" value=\"10\"/>"
                ,reader.readLine());
        assertEquals("<input type=\"hidden\" name=\"index\" value=\"0\"/>"
                ,reader.readLine());
        assertEquals("<input type=\"hidden\" name=\"abs\"" +
                " value=\"\"/>",reader.readLine());
        assertEquals("<script type=\"text/javascript\">",reader.readLine());
        assertEquals("<!--",reader.readLine());
        assertEquals("  function pageLinkSubmit(rowObj, indexObj, row, " +
                "startIndex){",reader.readLine());
        assertEquals("    rowObj.value = row;",reader.readLine());
        assertEquals("    indexObj.value = startIndex;",reader.readLine());
        assertEquals("    document.pageLinks.abs.value " +
                "= \"forward_pageLinks\";", reader.readLine());
        assertEquals("    document.pageLinks.submit();",reader.readLine());
        assertEquals("  }",reader.readLine());
        assertEquals("// -->",reader.readLine());
        assertEquals("</script>",reader.readLine());
        assertNull(reader.readLine());

        Boolean bol = (Boolean) pageContext.getAttribute(
                PageLinksTag.PAGELINKS_JAVASCRIPT_KEY + "abs");
        assertTrue(bol);
        bol = (Boolean) pageContext.getAttribute(
                PageLinksTag.PAGELINKS_JAVASCRIPT_KEY + "row");
        assertTrue(bol);
        bol = (Boolean) pageContext.getAttribute(
                PageLinksTag.PAGELINKS_JAVASCRIPT_KEY + "index");
        assertTrue(bol);
        bol = (Boolean) pageContext.getAttribute(
                PageLinksTag.PAGELINKS_JAVASCRIPT_KEY + "resetIndex");
        assertFalse(bol);
        bol = (Boolean) pageContext.getAttribute(
                PageLinksTag.PAGELINKS_JAVASCRIPT_KEY);
        assertTrue(bol);
    }

    /**
     * testDefineHtml02()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) row:10<br>
     *         (引数) startIndex:0<br>
     *         (引数) totalCount:0<br>
     *         (状態) forward:true<br>
     *         (状態) event:"abs"<br>
     *         (状態) resetIndex:true<br>
     *         (状態) pageContextのPAGELINKS_JAVASCRIPT_KEY ＋eventの値:true<br>
     *         (状態) pageContextのPAGELINKS_JAVASCRIPT_KEY ＋rowPropertyの値:true<br>
     *         (状態) pageContextのPAGELINKS_JAVASCRIPT_KEY ＋indexPropertyの値:true<br>
     *         (状態) pageContextのPAGELINKS_JAVASCRIPT_KEY ＋"resetIndex"の値:true<br>
     *         (状態) pageContextのPAGELINKS_JAVASCRIPT_KEYの値:true<br>
     *         
     * <br>
     * 期待値：(状態変化) JspWriter:何も出力されないことを確認。<br>
     *         
     * <br>
     * ページコンテキストのフラグがすべてtrueの場合
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testDefineHtml02() throws Exception {
        // 前処理
        UTUtil.setPrivateField(pageLinks, "rowProperty", "row");
        UTUtil.setPrivateField(pageLinks, "indexProperty", "index");
        UTUtil.setPrivateField(pageLinks, "forward", true);
        UTUtil.setPrivateField(pageLinks, "event", "abs");
        UTUtil.setPrivateField(pageLinks, "resetIndex", true);

        // Mockオブジェクトの設定
        MockPageContext pageContext
            = (MockPageContext) TagUTUtil.getPageContext(pageLinks);
        pageContext.setAttribute(PageLinksTag.PAGELINKS_JAVASCRIPT_KEY
                + "abs", true);
        pageContext.setAttribute(PageLinksTag.PAGELINKS_JAVASCRIPT_KEY
                + "row", true);
        pageContext.setAttribute(PageLinksTag.PAGELINKS_JAVASCRIPT_KEY
                + "index", true);
        pageContext.setAttribute(PageLinksTag.PAGELINKS_JAVASCRIPT_KEY
                + "resetIndex", true);
        pageContext.setAttribute(PageLinksTag.PAGELINKS_JAVASCRIPT_KEY, true);

        ActionMapping mapping = new ActionMapping();
        mapping.setName("pageLinks");
        pageContext.getRequest().setAttribute(Globals.MAPPING_KEY, mapping);

        // テスト実施
        pageLinks.defineHtml(10, 0, 0);

        // 判定
        BufferedReader reader = TagUTUtil.getOutputReader(pageLinks);
        assertNull(reader.readLine());

        Boolean bol = (Boolean) pageContext.getAttribute(
                PageLinksTag.PAGELINKS_JAVASCRIPT_KEY + "abs");
        assertTrue(bol);
        bol = (Boolean) pageContext.getAttribute(
                PageLinksTag.PAGELINKS_JAVASCRIPT_KEY + "row");
        assertTrue(bol);
        bol = (Boolean) pageContext.getAttribute(
                PageLinksTag.PAGELINKS_JAVASCRIPT_KEY + "index");
        assertTrue(bol);
        bol = (Boolean) pageContext.getAttribute(
                PageLinksTag.PAGELINKS_JAVASCRIPT_KEY + "resetIndex");
        assertTrue(bol);
        bol = (Boolean) pageContext.getAttribute(
                PageLinksTag.PAGELINKS_JAVASCRIPT_KEY);
        assertTrue(bol);
    }

    /**
     * testDefineHtml03()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) row:10<br>
     *         (引数) startIndex:0<br>
     *         (引数) totalCount:0<br>
     *         (状態) forward:false<br>
     *         (状態) resetIndex:true<br>
     *         (状態) pageContextのPAGELINKS_JAVASCRIPT_KEY ＋eventの値:true<br>
     *         (状態) pageContextのPAGELINKS_JAVASCRIPT_KEY ＋rowPropertyの値:true<br>
     *         (状態) pageContextのPAGELINKS_JAVASCRIPT_KEY ＋indexPropertyの値:true<br>
     *         (状態) pageContextのPAGELINKS_JAVASCRIPT_KEY ＋"resetIndex"の値:true<br>
     *         (状態) pageContextのPAGELINKS_JAVASCRIPT_KEYの値:true<br>
     *         
     * <br>
     * 期待値：(状態変化) JspWriter:何も出力されないことを確認。<br>
     *         
     * <br>
     * ページコンテキストのフラグがすべてtrueの場合
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testDefineHtml03() throws Exception {
        // 前処理
        UTUtil.setPrivateField(pageLinks, "rowProperty", "row");
        UTUtil.setPrivateField(pageLinks, "indexProperty", "index");
        UTUtil.setPrivateField(pageLinks, "forward", false);
        UTUtil.setPrivateField(pageLinks, "resetIndex", true);

        // Mockオブジェクトの設定
        MockPageContext pageContext
            = (MockPageContext) TagUTUtil.getPageContext(pageLinks);
        pageContext.setAttribute(PageLinksTag.PAGELINKS_JAVASCRIPT_KEY
                +PageLinksTag.FORWARD_NAME, true);
        pageContext.setAttribute(PageLinksTag.PAGELINKS_JAVASCRIPT_KEY
                +"row", true);
        pageContext.setAttribute(PageLinksTag.PAGELINKS_JAVASCRIPT_KEY
                +"index", true);
        pageContext.setAttribute(PageLinksTag.PAGELINKS_JAVASCRIPT_KEY
                + "resetIndex", true);
        pageContext.setAttribute(PageLinksTag.PAGELINKS_JAVASCRIPT_KEY, true);

        ActionMapping mapping = new ActionMapping();
        mapping.setName("pageLinks");
        pageContext.getRequest().setAttribute(Globals.MAPPING_KEY, mapping);

        // テスト実施
        pageLinks.defineHtml(10, 0, 0);

        // 判定
        BufferedReader reader = TagUTUtil.getOutputReader(pageLinks);
        assertNull(reader.readLine());

        Boolean bol = (Boolean) pageContext.getAttribute(
                PageLinksTag.PAGELINKS_JAVASCRIPT_KEY
                + PageLinksTag.FORWARD_NAME);
        assertTrue(bol);
        bol = (Boolean) pageContext.getAttribute(
                PageLinksTag.PAGELINKS_JAVASCRIPT_KEY +"row");
        assertTrue(bol);
        bol = (Boolean) pageContext.getAttribute(
                PageLinksTag.PAGELINKS_JAVASCRIPT_KEY +"index");
        assertTrue(bol);
        bol = (Boolean) pageContext.getAttribute(
                PageLinksTag.PAGELINKS_JAVASCRIPT_KEY + "resetIndex");
        assertTrue(bol);
        bol = (Boolean) pageContext.getAttribute(
                PageLinksTag.PAGELINKS_JAVASCRIPT_KEY);
        assertTrue(bol);
    }

    /**
     * testDefineHtml04()
     * <br><br>
     * 
     * (異常系)
     * <br>
     * 観点：G
     * <br><br>
     * 入力値：(引数) row:10<br>
     *         (引数) startIndex:0<br>
     *         (引数) totalCount:0<br>
     *         (状態) rowProperty:"row"<br>
     *         (状態) indexProperty:"index"<br>
     *         (状態) forward:true<br>
     *         (状態) resetIndex:true<br>
     *         (状態) pageContextのPAGELINKS_JAVASCRIPT_KEY ＋eventの値:false<br>
     *         (状態) pageContextのPAGELINKS_JAVASCRIPT_KEY ＋rowPropertyの値:false<br>
     *         (状態) pageContextのPAGELINKS_JAVASCRIPT_KEY ＋indexPropertyの値:true<br>
     *         (状態) pageContextのPAGELINKS_JAVASCRIPT_KEY ＋"resetIndex"の値:true<br>
     *         (状態) pageContextのPAGELINKS_JAVASCRIPT_KEYの値:true<br>
     *         (状態) JspWriter#println():IOExceptionが発生<br>
     *         
     * <br>
     * 期待値：(状態変化) 例外:クラス：JspTagException<br>
     *                    メッセージ：-<br>
     *                    ラップした例外：-<br>
     *         
     * <br>
     * JspWriterでIOExceptionが発生した場合
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testDefineHtml04() throws Exception {
        // 前処理
        UTUtil.setPrivateField(pageLinks, "rowProperty", "row");
        UTUtil.setPrivateField(pageLinks, "indexProperty", "index");
        UTUtil.setPrivateField(pageLinks, "forward", true);
        UTUtil.setPrivateField(pageLinks, "resetIndex", true);

        // Mockオブジェクトの設定
        MockPageContext pageContext
            = (MockPageContext) TagUTUtil.getPageContext(pageLinks);
        pageContext.setAttribute(PageLinksTag.PAGELINKS_JAVASCRIPT_KEY
                +PageLinksTag.FORWARD_NAME, true);
        pageContext.setAttribute(PageLinksTag.PAGELINKS_JAVASCRIPT_KEY
                +"row", false);
        pageContext.setAttribute(PageLinksTag.PAGELINKS_JAVASCRIPT_KEY
                +"index", false);
        pageContext.setAttribute(PageLinksTag.PAGELINKS_JAVASCRIPT_KEY
                + "resetIndex", true);
        pageContext.setAttribute(PageLinksTag.PAGELINKS_JAVASCRIPT_KEY, false);

        ActionMapping mapping = new ActionMapping();
        mapping.setName("pageLinks");
        pageContext.getRequest().setAttribute(Globals.MAPPING_KEY, mapping);

        // テスト用JspWriterの生成
        Exception_JspWriterImpl out =
            new Exception_JspWriterImpl();
        out.setTrue();

        // 生成・設定したテスト用JspWriterをPageContextにセット
        UTUtil.setPrivateField(pageContext, "jspWriter", out);

        // テスト実施
        try {
            pageLinks.defineHtml(10, 0, 0);
            fail();
        } catch (JspTagException e) {
            // 判定
            if (e.getMessage().indexOf("IOException") == -1) {
                //結果確認
                fail();
            }
        }
    }

    /**
     * testDefineHtml05()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) row:10<br>
     *         (引数) startIndex:0<br>
     *         (引数) totalCount:0<br>
     *         (状態) rowProperty:"row"<br>
     *         (状態) indexProperty:"index"<br>
     *         (状態) forward:false<br>
     *         (状態) event:"abs"<br>
     *         (状態) resetIndex:false<br>
     *         (状態) pageContextのPAGELINKS_JAVASCRIPT_KEY ＋eventの値:false<br>
     *         (状態) pageContextのPAGELINKS_JAVASCRIPT_KEY ＋rowPropertyの値:false<br>
     *         (状態) pageContextのPAGELINKS_JAVASCRIPT_KEY ＋indexPropertyの値:false<br>
     *         (状態) pageContextのPAGELINKS_JAVASCRIPT_KEY ＋"resetIndex"の値:false<br>
     *         (状態) pageContextのPAGELINKS_JAVASCRIPT_KEYの値:false<br>
     *         (状態) fromName:"pageLinks"<br>
     *         
     * <br>
     * 期待値：(状態変化) JspWriter:<input type=\"hidden\" name=\"row\" value=\"10\"/><br>
     *                    <input type=\"hidden\" name=\"index\" value=\"0\"/><br>
     *                    <script type=\"text/javascript\"><br>
     *                    <!--<br>
     *                      function pageLinkSubmit(rowObj, indexObj, row, startIndex){<br>
     *                        rowObj.value = row;<br>
     *                        indexObj.value = startIndex;<br>
     *                        document.pageLinks.submit();<br>
     *                      }<br>
     *                    // --><br>
     *                    </script><br>
     *         (状態変化) pageContextのPAGELINKS_JAVASCRIPT_KEY ＋eventの値:false<br>
     *         (状態変化) pageContextのPAGELINKS_JAVASCRIPT_KEY ＋rowPropertyの値:true<br>
     *         (状態変化) pageContextのPAGELINKS_JAVASCRIPT_KEY ＋indexPropertyの値:true<br>
     *         (状態変化) pageContextのPAGELINKS_JAVASCRIPT_KEY ＋"resetIndex"の値:true<br>
     *         (状態変化) pageContextのPAGELINKS_JAVASCRIPT_KEYの値:true<br>
     *         
     * <br>
     * ページコンテキストのフラグがすべてfalseで、forward属性がfalseの場合
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testDefineHtml05() throws Exception {
        // 前処理
        UTUtil.setPrivateField(pageLinks, "rowProperty", "row");
        UTUtil.setPrivateField(pageLinks, "indexProperty", "index");
        UTUtil.setPrivateField(pageLinks, "forward", false);
        UTUtil.setPrivateField(pageLinks, "event", "abs");
        UTUtil.setPrivateField(pageLinks, "resetIndex", false);

        // Mockオブジェクトの設定
        MockPageContext pageContext
            = (MockPageContext) TagUTUtil.getPageContext(pageLinks);
        pageContext.setAttribute(PageLinksTag.PAGELINKS_JAVASCRIPT_KEY
                + "abs", false);
        pageContext.setAttribute(PageLinksTag.PAGELINKS_JAVASCRIPT_KEY
                + "row", false);
        pageContext.setAttribute(PageLinksTag.PAGELINKS_JAVASCRIPT_KEY
                + "index", false);
        pageContext.setAttribute(PageLinksTag.PAGELINKS_JAVASCRIPT_KEY
                + "resetIndex", false);
        pageContext.setAttribute(PageLinksTag.PAGELINKS_JAVASCRIPT_KEY, false);

        ActionMapping mapping = new ActionMapping();
        mapping.setName("pageLinks");
        pageContext.getRequest().setAttribute(Globals.MAPPING_KEY, mapping);

        // テスト実施
        pageLinks.defineHtml(10, 0, 0);

        // 判定
        BufferedReader reader = TagUTUtil.getOutputReader(pageLinks);
        assertEquals("<input type=\"hidden\" name=\"row\" value=\"10\"/>"
                ,reader.readLine());
        assertEquals("<input type=\"hidden\" name=\"index\" value=\"0\"/>"
                ,reader.readLine());
        assertEquals("<script type=\"text/javascript\">",reader.readLine());
        assertEquals("<!--",reader.readLine());
        assertEquals("  function pageLinkSubmit(rowObj, indexObj, row, " +
                "startIndex){",reader.readLine());
        assertEquals("    rowObj.value = row;",reader.readLine());
        assertEquals("    indexObj.value = startIndex;",reader.readLine());
        assertEquals("    document.pageLinks.submit();",reader.readLine());
        assertEquals("  }",reader.readLine());
        assertEquals("// -->",reader.readLine());
        assertEquals("</script>",reader.readLine());
        assertNull(reader.readLine());

        Boolean bol = (Boolean) pageContext.getAttribute(
                PageLinksTag.PAGELINKS_JAVASCRIPT_KEY + "abs");
        assertFalse(bol);
        bol = (Boolean) pageContext.getAttribute(
                PageLinksTag.PAGELINKS_JAVASCRIPT_KEY + "row");
        assertTrue(bol);
        bol = (Boolean) pageContext.getAttribute(
                PageLinksTag.PAGELINKS_JAVASCRIPT_KEY + "index");
        assertTrue(bol);
        bol = (Boolean) pageContext.getAttribute(
                PageLinksTag.PAGELINKS_JAVASCRIPT_KEY + "resetIndex");
        assertFalse(bol);
        bol = (Boolean) pageContext.getAttribute(
                PageLinksTag.PAGELINKS_JAVASCRIPT_KEY);
        assertTrue(bol);
    }

    /**
     * testDefineHtml06()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) row:10<br>
     *         (引数) startIndex:9<br>
     *         (引数) totalCount:15<br>
     *         (状態) rowProperty:"row"<br>
     *         (状態) indexProperty:"index"<br>
     *         (状態) forward:false<br>
     *         (状態) event:"abs"<br>
     *         (状態) resetIndex:true<br>
     *         (状態) pageContextのPAGELINKS_JAVASCRIPT_KEY ＋eventの値:false<br>
     *         (状態) pageContextのPAGELINKS_JAVASCRIPT_KEY ＋rowPropertyの値:false<br>
     *         (状態) pageContextのPAGELINKS_JAVASCRIPT_KEY ＋indexPropertyの値:false<br>
     *         (状態) pageContextのPAGELINKS_JAVASCRIPT_KEY ＋"resetIndex"の値:false<br>
     *         (状態) pageContextのPAGELINKS_JAVASCRIPT_KEYの値:false<br>
     *         (状態) fromName:"pageLinks"<br>
     *         
     * <br>
     * 期待値：(状態変化) JspWriter:<input type=\"hidden\" name=\"row\" value=\"10\"/><br>
     *                    <input type=\"hidden\" name=\"index\" value=\"9\"/><br>
     *                    <input type=\"hidden\" name=\"startIndex\" value=\"9\"/><br>
     *                    <input type=\"hidden\" name=\"endIndex\" value=\"14\"/><br>
     *                    <script type=\"text/javascript\"><br>
     *                    <!--<br>
     *                      function pageLinkSubmit(rowObj, indexObj, row, startIndex){<br>
     *                        rowObj.value = row;<br>
     *                        indexObj.value = startIndex;<br>
     *                        document.pageLinks.submit();<br>
     *                      }<br>
     *                    // --><br>
     *                    </script><br>
     *         (状態変化) pageContextのPAGELINKS_JAVASCRIPT_KEY ＋eventの値:false<br>
     *         (状態変化) pageContextのPAGELINKS_JAVASCRIPT_KEY ＋rowPropertyの値:true<br>
     *         (状態変化) pageContextのPAGELINKS_JAVASCRIPT_KEY ＋indexPropertyの値:true<br>
     *         (状態変化) pageContextのPAGELINKS_JAVASCRIPT_KEY ＋"resetIndex"の値:true<br>
     *         (状態変化) pageContextのPAGELINKS_JAVASCRIPT_KEYの値:true<br>
     *         
     * <br>
     * ページコンテキストのフラグがすべてfalseで、resetIndex属性がtrueで、開始インデックスが、"startIndex"ではない場合
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testDefineHtml06() throws Exception {
        // 前処理
        UTUtil.setPrivateField(pageLinks, "rowProperty", "row");
        UTUtil.setPrivateField(pageLinks, "indexProperty", "index");
        UTUtil.setPrivateField(pageLinks, "forward", false);
        UTUtil.setPrivateField(pageLinks, "event", "abs");
        UTUtil.setPrivateField(pageLinks, "resetIndex", true);

        // Mockオブジェクトの設定
        MockPageContext pageContext
            = (MockPageContext) TagUTUtil.getPageContext(pageLinks);
        pageContext.setAttribute(PageLinksTag.PAGELINKS_JAVASCRIPT_KEY
                + "abs", false);
        pageContext.setAttribute(PageLinksTag.PAGELINKS_JAVASCRIPT_KEY
                + "row", false);
        pageContext.setAttribute(PageLinksTag.PAGELINKS_JAVASCRIPT_KEY
                + "index", false);
        pageContext.setAttribute(PageLinksTag.PAGELINKS_JAVASCRIPT_KEY
                + "resetIndex", false);
        pageContext.setAttribute(PageLinksTag.PAGELINKS_JAVASCRIPT_KEY, false);

        ActionMapping mapping = new ActionMapping();
        mapping.setName("pageLinks");
        pageContext.getRequest().setAttribute(Globals.MAPPING_KEY, mapping);

        // テスト実施
        pageLinks.defineHtml(10, 9, 15);

        // 判定
        BufferedReader reader = TagUTUtil.getOutputReader(pageLinks);
        assertEquals("<input type=\"hidden\" name=\"row\" value=\"10\"/>"
                ,reader.readLine());
        assertEquals("<input type=\"hidden\" name=\"index\" value=\"9\"/>"
                ,reader.readLine());
        assertEquals("<input type=\"hidden\" name=\"startIndex\" value=\"9\"/>"
                ,reader.readLine());
        assertEquals("<input type=\"hidden\" name=\"endIndex\" value=\"14\"/>"
                ,reader.readLine());
        assertEquals("<script type=\"text/javascript\">",reader.readLine());
        assertEquals("<!--",reader.readLine());
        assertEquals("  function pageLinkSubmit(rowObj, indexObj, row, " +
                "startIndex){",reader.readLine());
        assertEquals("    rowObj.value = row;",reader.readLine());
        assertEquals("    indexObj.value = startIndex;",reader.readLine());
        assertEquals("    document.pageLinks.submit();",reader.readLine());
        assertEquals("  }",reader.readLine());
        assertEquals("// -->",reader.readLine());
        assertEquals("</script>",reader.readLine());
        assertNull(reader.readLine());

        Boolean bol = (Boolean) pageContext.getAttribute(
                PageLinksTag.PAGELINKS_JAVASCRIPT_KEY + "abs");
        assertFalse(bol);
        bol = (Boolean) pageContext.getAttribute(
                PageLinksTag.PAGELINKS_JAVASCRIPT_KEY + "row");
        assertTrue(bol);
        bol = (Boolean) pageContext.getAttribute(
                PageLinksTag.PAGELINKS_JAVASCRIPT_KEY + "index");
        assertTrue(bol);
        bol = (Boolean) pageContext.getAttribute(
                PageLinksTag.PAGELINKS_JAVASCRIPT_KEY + "resetIndex");
        assertTrue(bol);
        bol = (Boolean) pageContext.getAttribute(
                PageLinksTag.PAGELINKS_JAVASCRIPT_KEY);
        assertTrue(bol);
    }

    /**
     * testDefineHtml07()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) row:10<br>
     *         (引数) startIndex:9<br>
     *         (引数) totalCount:50<br>
     *         (状態) rowProperty:"row"<br>
     *         (状態) indexProperty:"startIndex"<br>
     *         (状態) forward:false<br>
     *         (状態) event:"abs"<br>
     *         (状態) resetIndex:true<br>
     *         (状態) pageContextのPAGELINKS_JAVASCRIPT_KEY ＋eventの値:false<br>
     *         (状態) pageContextのPAGELINKS_JAVASCRIPT_KEY ＋rowPropertyの値:false<br>
     *         (状態) pageContextのPAGELINKS_JAVASCRIPT_KEY ＋indexPropertyの値:false<br>
     *         (状態) pageContextのPAGELINKS_JAVASCRIPT_KEY ＋"resetIndex"の値:false<br>
     *         (状態) pageContextのPAGELINKS_JAVASCRIPT_KEYの値:false<br>
     *         (状態) fromName:"pageLinks"<br>
     *         
     * <br>
     * 期待値：(状態変化) JspWriter:<input type=\"hidden\" name=\"row\" value=\"10\"/><br>
     *                    <input type=\"hidden\" name=\"startIndex\" value=\"9\"/><br>
     *                    <input type=\"hidden\" name=\"endIndex\" value=\"18\"/><br>
     *                    <script type=\"text/javascript\"><br>
     *                    <!--<br>
     *                      function pageLinkSubmit(rowObj, indexObj, row, startIndex){<br>
     *                        rowObj.value = row;<br>
     *                        indexObj.value = startIndex;<br>
     *                        document.pageLinks.submit();<br>
     *                      }<br>
     *                    // --><br>
     *                    </script><br>
     *         (状態変化) pageContextのPAGELINKS_JAVASCRIPT_KEY ＋eventの値:false<br>
     *         (状態変化) pageContextのPAGELINKS_JAVASCRIPT_KEY ＋rowPropertyの値:true<br>
     *         (状態変化) pageContextのPAGELINKS_JAVASCRIPT_KEY ＋indexPropertyの値:true<br>
     *         (状態変化) pageContextのPAGELINKS_JAVASCRIPT_KEY ＋"resetIndex"の値:true<br>
     *         (状態変化) pageContextのPAGELINKS_JAVASCRIPT_KEYの値:true<br>
     *         
     * <br>
     * ページコンテキストのフラグがすべてfalseで、resetIndex属性がtrueで、開始インデックスが、"startIndex"の場合
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testDefineHtml07() throws Exception {
        // 前処理
        UTUtil.setPrivateField(pageLinks, "rowProperty", "row");
        UTUtil.setPrivateField(pageLinks, "indexProperty", "startIndex");
        UTUtil.setPrivateField(pageLinks, "forward", false);
        UTUtil.setPrivateField(pageLinks, "event", "abs");
        UTUtil.setPrivateField(pageLinks, "resetIndex", true);

        // Mockオブジェクトの設定
        MockPageContext pageContext
            = (MockPageContext) TagUTUtil.getPageContext(pageLinks);
        pageContext.setAttribute(PageLinksTag.PAGELINKS_JAVASCRIPT_KEY
                + "abs", false);
        pageContext.setAttribute(PageLinksTag.PAGELINKS_JAVASCRIPT_KEY
                + "row", false);
        pageContext.setAttribute(PageLinksTag.PAGELINKS_JAVASCRIPT_KEY
                + "index", false);
        pageContext.setAttribute(PageLinksTag.PAGELINKS_JAVASCRIPT_KEY
                + "resetIndex", false);
        pageContext.setAttribute(PageLinksTag.PAGELINKS_JAVASCRIPT_KEY, false);

        ActionMapping mapping = new ActionMapping();
        mapping.setName("pageLinks");
        pageContext.getRequest().setAttribute(Globals.MAPPING_KEY, mapping);

        // テスト実施
        pageLinks.defineHtml(10, 9, 50);

        // 判定
        BufferedReader reader = TagUTUtil.getOutputReader(pageLinks);
        assertEquals("<input type=\"hidden\" name=\"row\" value=\"10\"/>"
                ,reader.readLine());
        assertEquals("<input type=\"hidden\" name=\"startIndex\" value=\"9\"/>"
                ,reader.readLine());
        assertEquals("<input type=\"hidden\" name=\"endIndex\" value=\"18\"/>"
                ,reader.readLine());
        assertEquals("<script type=\"text/javascript\">",reader.readLine());
        assertEquals("<!--",reader.readLine());
        assertEquals("  function pageLinkSubmit(rowObj, indexObj, row, " +
                "startIndex){",reader.readLine());
        assertEquals("    rowObj.value = row;",reader.readLine());
        assertEquals("    indexObj.value = startIndex;",reader.readLine());
        assertEquals("    document.pageLinks.submit();",reader.readLine());
        assertEquals("  }",reader.readLine());
        assertEquals("// -->",reader.readLine());
        assertEquals("</script>",reader.readLine());
        assertNull(reader.readLine());

        Boolean bol = (Boolean) pageContext.getAttribute(
                PageLinksTag.PAGELINKS_JAVASCRIPT_KEY + "abs");
        assertFalse(bol);
        bol = (Boolean) pageContext.getAttribute(
                PageLinksTag.PAGELINKS_JAVASCRIPT_KEY + "row");
        assertTrue(bol);
        bol = (Boolean) pageContext.getAttribute(
                PageLinksTag.PAGELINKS_JAVASCRIPT_KEY + "startIndex");
        assertTrue(bol);
        bol = (Boolean) pageContext.getAttribute(
                PageLinksTag.PAGELINKS_JAVASCRIPT_KEY + "resetIndex");
        assertTrue(bol);
        bol = (Boolean) pageContext.getAttribute(
                PageLinksTag.PAGELINKS_JAVASCRIPT_KEY);
        assertTrue(bol);
    }

    /**
     * testAddPrevSubmit01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) sb:""<br>
     *         (引数) row:10<br>
     *         (引数) startIndex:20<br>
     *         (状態) formName:"pageLinks"<br>
     *         (状態) maxLinkNo:10<br>
     *         (状態) rowProperty:"row"<br>
     *         (状態) indexProperty:"index"<br>
     *         (状態) プロパティの設定<br>
     *                「Map links」:prev1.char=&lt;<br>
     *                prev2.char=<br>
     *                prev5.char=&lt;&lt;<br>
     *                prev10.char=&lt;&lt;&lt;<br>
     *         
     * <br>
     * 期待値：(状態変化) sb:&lt;&lt;&lt;&nbsp;&lt;&lt;&nbsp;<a href=\"#\"
     *  onclick=\"pageLinkSubmit(document.pageLinks.row,document.pageLinks.
     *  index,10,10)\">&lt;</a>&nbsp;<br>
     *         
     * <br>
     * 引数rowが１０、startIndexが２０の場合
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testAddPrevSubmit01() throws Exception {
        // 前処理
        UTUtil.setPrivateField(pageLinks, "rowProperty", "row");
        UTUtil.setPrivateField(pageLinks, "indexProperty", "index");
        UTUtil.setPrivateField(pageLinks, "maxLinkNo", new Integer(10));

        // Mockオブジェクトの設定
        MockPageContext pageContext
            = (MockPageContext) TagUTUtil.getPageContext(pageLinks);

        ActionMapping mapping = new ActionMapping();
        mapping.setName("pageLinks");
        pageContext.getRequest().setAttribute(Globals.MAPPING_KEY, mapping);

        Map<String, String> links = new HashMap<String, String>();
        links.put("prev1.char", "&lt;");
        links.put("prev2.char", "");
        links.put("prev5.char", "&lt;&lt;");
        links.put("prev10.char", "&lt;&lt;&lt;");
        UTUtil.setPrivateField(pageLinks, "links", links);

        StringBuilder sb = new StringBuilder();
        int row = 10;
        int startIndex = 20;
        int totalCount = 100;

        // テスト実施
        pageLinks.addPrevSubmit(sb, row, startIndex, totalCount);
        

        // 判定
        assertEquals("&lt;&lt;&lt;&nbsp;&lt;&lt;&nbsp;<a href=\"#\"" +
                " onclick=\"pageLinkSubmit(document.pageLinks.row," +
                "document.pageLinks.index,10,10)\">&lt;</a>&nbsp;"
                , sb.toString());
    }

    /**
     * testAddPrevSubmit02()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) sb:""<br>
     *         (引数) row:0<br>
     *         (引数) startIndex:0<br>
     *         (状態) formName:"pageLinks"<br>
     *         (状態) maxLinkNo:10<br>
     *         (状態) rowProperty:"row"<br>
     *         (状態) indexProperty:"index"<br>
     *         (状態) プロパティの設定<br>
     *                「Map links」:prev1.char=&lt;<br>
     *                prev2.char=<br>
     *                prev5.char=&lt;&lt;<br>
     *                prev10.char=&lt;&lt;&lt;<br>
     *         
     * <br>
     * 期待値：(状態変化) sb:
     * <a href=\"#\" onclick=\"pageLinkSubmit(document.pageLinks.row,
     * document.pageLinks.index,0,0)\">&lt;&lt;&lt;</a>&nbsp;
     * <a href=\"#\" onclick=\"pageLinkSubmit(document.pageLinks.row,
     * document.pageLinks.index,0,0)\">&lt;&lt;</a>&nbsp;
     * <a href=\"#\" onclick=\"pageLinkSubmit(document.pageLinks.row,
     * document.pageLinks.index,0,0)\">&lt;</a>&nbsp;<br>
     *         
     * <br>
     * 引数rowが０、startIndexが０の場合
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testAddPrevSubmit02() throws Exception {
        // 前処理
        UTUtil.setPrivateField(pageLinks, "rowProperty", "row");
        UTUtil.setPrivateField(pageLinks, "indexProperty", "index");
        UTUtil.setPrivateField(pageLinks, "maxLinkNo", new Integer(10));

        // Mockオブジェクトの設定
        MockPageContext pageContext
            = (MockPageContext) TagUTUtil.getPageContext(pageLinks);

        ActionMapping mapping = new ActionMapping();
        mapping.setName("pageLinks");
        pageContext.getRequest().setAttribute(Globals.MAPPING_KEY, mapping);

        Map<String, String> links = new HashMap<String, String>();
        links.put("prev1.char", "&lt;");
        links.put("prev2.char", "");
        links.put("prev5.char", "&lt;&lt;");
        links.put("prev10.char", "&lt;&lt;&lt;");
        UTUtil.setPrivateField(pageLinks, "links", links);

        StringBuilder sb = new StringBuilder();
        int row = 0;
        int startIndex = 0;
        int totalCount = 0;

        // テスト実施
        pageLinks.addPrevSubmit(sb, row, startIndex, totalCount);

        // 判定
        assertEquals("<a href=\"#\" onclick=\"pageLinkSubmit(" +
                "document.pageLinks.row,document.pageLinks.index,0,0)\">" +
                "&lt;&lt;&lt;</a>&nbsp;<a href=\"#\" " +
                "onclick=\"pageLinkSubmit(document.pageLinks.row," +
                "document.pageLinks.index,0,0)\">&lt;&lt;</a>&nbsp;" +
                "<a href=\"#\" onclick=\"pageLinkSubmit(" +
                "document.pageLinks.row,document.pageLinks.index,0,0)\">" +
                "&lt;</a>&nbsp;", sb.toString());
    }

    /**
     * testAddPrevSubmit03()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) sb:""<br>
     *         (引数) row:-1<br>
     *         (引数) startIndex:-1<br>
     *         (状態) formName:"pageLinks"<br>
     *         (状態) maxLinkNo:10<br>
     *         (状態) rowProperty:"row"<br>
     *         (状態) indexProperty:"index"<br>
     *         (状態) プロパティの設定<br>
     *                「Map links」:prev1.char=&lt;<br>
     *         
     * <br>
     * 期待値：(状態変化) sb:<a href=\"#\" onclick=\"pageLinkSubmit(
     * document.pageLinks.row,document.pageLinks.index,-1,0)\">&lt;</a>&nbsp;
     * <br>
     *         
     * <br>
     * 引数rowが－１、startIndexが－１の場合
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testAddPrevSubmit03() throws Exception {
        // 前処理
        UTUtil.setPrivateField(pageLinks, "rowProperty", "row");
        UTUtil.setPrivateField(pageLinks, "indexProperty", "index");
        UTUtil.setPrivateField(pageLinks, "maxLinkNo", new Integer(10));

        // Mockオブジェクトの設定
        MockPageContext pageContext
            = (MockPageContext) TagUTUtil.getPageContext(pageLinks);

        ActionMapping mapping = new ActionMapping();
        mapping.setName("pageLinks");
        pageContext.getRequest().setAttribute(Globals.MAPPING_KEY, mapping);

        Map<String, String> links = new HashMap<String, String>();
        links.put("prev1.char", "&lt;");
        UTUtil.setPrivateField(pageLinks, "links", links);

        StringBuilder sb = new StringBuilder();
        int row = -1;
        int startIndex = -1;
        int totalCount = -1;

        // テスト実施
        pageLinks.addPrevSubmit(sb, row, startIndex, totalCount);

        // 判定
        assertEquals("<a href=\"#\" onclick=\"pageLinkSubmit(" +
                "document.pageLinks.row,document.pageLinks.index,-1,0)\">" +
                "&lt;</a>&nbsp;", sb.toString());
    }

    /**
     * testAddPrevSubmit04()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) sb:""<br>
     *         (引数) row:10<br>
     *         (引数) startIndex:20<br>
     *         (状態) formName:"pageLinks"<br>
     *         (状態) maxLinkNo:1<br>
     *         (状態) rowProperty:"row"<br>
     *         (状態) indexProperty:"index"<br>
     *         (状態) プロパティの設定<br>
     *                「Map links」:prev1.char=&lt;<br>
     *                prev2.char=<br>
     *                prev5.char=&lt;&lt;<br>
     *                prev10.char=&lt;&lt;&lt;<br>
     *         
     * <br>
     * 期待値：(状態変化) sb:<a href=\"#\"
     *  onclick=\"pageLinkSubmit(document.pageLinks.row,document.pageLinks.
     *  index,10,10)\">&lt;</a>&nbsp;<br>
     *         
     * <br>
     * 引数rowが１０、startIndexが２０で、maxLinkNoが１の場合
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testAddPrevSubmit04() throws Exception {
        // 前処理
        UTUtil.setPrivateField(pageLinks, "rowProperty", "row");
        UTUtil.setPrivateField(pageLinks, "indexProperty", "index");
        UTUtil.setPrivateField(pageLinks, "maxLinkNo", new Integer(1));

        // Mockオブジェクトの設定
        MockPageContext pageContext
            = (MockPageContext) TagUTUtil.getPageContext(pageLinks);

        ActionMapping mapping = new ActionMapping();
        mapping.setName("pageLinks");
        pageContext.getRequest().setAttribute(Globals.MAPPING_KEY, mapping);

        Map<String, String> links = new HashMap<String, String>();
        links.put("prev1.char", "&lt;");
        links.put("prev2.char", "");
        links.put("prev5.char", "&lt;&lt;");
        links.put("prev10.char", "&lt;&lt;&lt;");
        UTUtil.setPrivateField(pageLinks, "links", links);

        StringBuilder sb = new StringBuilder();
        int row = 10;
        int startIndex = 20;
        int totalCount = 100;

        // テスト実施
        pageLinks.addPrevSubmit(sb, row, startIndex, totalCount);

        // 判定
        assertEquals("<a href=\"#\"" +
                " onclick=\"pageLinkSubmit(document.pageLinks.row," +
                "document.pageLinks.index,10,10)\">&lt;</a>&nbsp;"
                , sb.toString());
    }

    /**
     * testAddDirectSubmit01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：B
     * <br><br>
     * 入力値：(引数) sb:""<br>
     *         (引数) row:10<br>
     *         (引数) startIndex:100<br>
     *         (引数) totalCount:130<br>
     *         (状態) formName:"pageLinks"<br>
     *         (状態) rowProperty:"row"<br>
     *         (状態) indexProperty:"index"<br>
     *         (状態) directLinkNo<br>
     *                (maxPageCount):"10"<br>
     *                （プロパティから取得したmaxDirectLinkCountの値）<br>
     *         
     * <br>
     * 期待値：(状態変化) sb:<a href="#" onclick="pageLinkSubmit(document.
     * pageLinks.row,document.pageLinks.index,10,30)">4</a>&nbsp;
     * <a href="#" onclick="pageLinkSubmit(document.pageLinks.row,
     * document.pageLinks.index,10,40)">5</a>&nbsp;<a href="#" 
     * onclick="pageLinkSubmit(document.pageLinks.row,document.pageLinks.index,
     * 10,50)">6</a>&nbsp;<a href="#" onclick="pageLinkSubmit(
     * document.pageLinks.row,document.pageLinks.index,10,60)">7</a>
     * &nbsp;<a href="#" onclick="pageLinkSubmit(document.pageLinks.row,
     * document.pageLinks.index,10,70)">8</a>&nbsp;<a href="#" 
     * onclick="pageLinkSubmit(document.pageLinks.row,document.pageLinks.index,
     * 10,80)">9</a>&nbsp;<a href="#" onclick="pageLinkSubmit(
     * document.pageLinks.row,document.pageLinks.index,10,90)">10</a>
     * &nbsp;<b>11</b>&nbsp;<a href="#" onclick="pageLinkSubmit(
     * document.pageLinks.row,document.pageLinks.index,10,110)">12</a>&nbsp;
     * <a href="#" onclick="pageLinkSubmit(document.pageLinks.row,
     * document.pageLinks.index,10,120)">13</a>&nbsp;<br>
     *         
     * <br>
     * 引数rowが10、startIndexが100、totalCountが130で、
     * directLinkNoに"10"が指定された場合
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testAddDirectSubmit01() throws Exception {
        // 前処理
        UTUtil.setPrivateField(pageLinks, "rowProperty", "row");
        UTUtil.setPrivateField(pageLinks, "indexProperty", "index");

        // Mockオブジェクトの設定
        MockPageContext pageContext
            = (MockPageContext) TagUTUtil.getPageContext(pageLinks);

        ActionMapping mapping = new ActionMapping();
        mapping.setName("pageLinks");
        pageContext.getRequest().setAttribute(Globals.MAPPING_KEY, mapping);

        Map<String, String> links = new HashMap<String, String>();
        links.put(PageLinksTag.MAX_DSP_SIZE, "10");
        UTUtil.setPrivateField(pageLinks, "links", links);

        StringBuilder sb = new StringBuilder();
        int row = 10;
        int startIndex = 100;
        int totalCount = 130;

        // テスト実施
        pageLinks.addDirectSubmit(sb, row, startIndex, totalCount);

        // 判定
        String result = "<a href=\"#\" onclick=\"pageLinkSubmit(" +
                "document.pageLinks.row,document.pageLinks.index,10,30)\">" +
                "4</a>&nbsp;<a href=\"#\" onclick=\"pageLinkSubmit(" +
                "document.pageLinks.row,document.pageLinks.index,10,40)\">" +
                "5</a>&nbsp;<a href=\"#\" onclick=\"pageLinkSubmit(" +
                "document.pageLinks.row,document.pageLinks.index,10,50)\">" +
                "6</a>&nbsp;<a href=\"#\" onclick=\"pageLinkSubmit(" +
                "document.pageLinks.row,document.pageLinks.index,10,60)\">" +
                "7</a>&nbsp;<a href=\"#\" onclick=\"pageLinkSubmit(" +
                "document.pageLinks.row,document.pageLinks.index,10,70)\">" +
                "8</a>&nbsp;<a href=\"#\" onclick=\"pageLinkSubmit(" +
                "document.pageLinks.row,document.pageLinks.index,10,80)\">" +
                "9</a>&nbsp;<a href=\"#\" onclick=\"pageLinkSubmit(" +
                "document.pageLinks.row,document.pageLinks.index,10,90)\">" +
                "10</a>&nbsp;<b>11</b>&nbsp;" +
                "<a href=\"#\" onclick=\"pageLinkSubmit(" +
                "document.pageLinks.row,document.pageLinks.index,10,110)\">" +
                "12</a>&nbsp;<a href=\"#\" onclick=\"pageLinkSubmit(" +
                "document.pageLinks.row,document.pageLinks.index,10,120)\">" +
                "13</a>&nbsp;";
        assertEquals(result, sb.toString());
    }

    /**
     * testAddDirectSubmit02()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：B
     * <br><br>
     * 入力値：(引数) sb:""<br>
     *         (引数) row:10<br>
     *         (引数) startIndex:100<br>
     *         (引数) totalCount:200<br>
     *         (状態) formName:"pageLinks"<br>
     *         (状態) rowProperty:"row"<br>
     *         (状態) indexProperty:"index"<br>
     *         (状態) directLinkNo<br>
     *                (maxPageCount):"a"<br>
     *                （プロパティから取得したmaxDirectLinkCountの値）<br>
     *         
     * <br>
     * 期待値：(状態変化) sb:<a href="#" onclick="pageLinkSubmit(
     * document.pageLinks.row,document.pageLinks.index,10,50)">6</a>
     * &nbsp;<a href="#" onclick="pageLinkSubmit(document.pageLinks.row,
     * document.pageLinks.index,10,60)">7</a>&nbsp;<a href="#" 
     * onclick="pageLinkSubmit(document.pageLinks.row,document.pageLinks.index,
     * 10,70)">8</a>&nbsp;<a href="#" onclick="pageLinkSubmit(
     * document.pageLinks.row,document.pageLinks.index,10,80)">9</a>&nbsp;
     * <a href="#" onclick="pageLinkSubmit(document.pageLinks.row,
     * document.pageLinks.index,10,90)">10</a>&nbsp;<b>11</b>&nbsp;
     * <a href="#" onclick="pageLinkSubmit(document.pageLinks.row,
     * document.pageLinks.index,10,110)">12</a>&nbsp;<a href="#" 
     * onclick="pageLinkSubmit(document.pageLinks.row,document.pageLinks.index,
     * 10,120)">13</a>&nbsp;<a href="#" onclick="pageLinkSubmit(
     * document.pageLinks.row,document.pageLinks.index,10,130)">14</a>&nbsp;
     * <a href="#" onclick="pageLinkSubmit(document.pageLinks.row,
     * document.pageLinks.index,10,140)">15</a>&nbsp;<br>
     *         
     * <br>
     * 引数rowが10、startIndexが100、totalCountが200で、
     * directLinkNoに"a"が指定された場合
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testAddDirectSubmit02() throws Exception {
        // 前処理
        UTUtil.setPrivateField(pageLinks, "rowProperty", "row");
        UTUtil.setPrivateField(pageLinks, "indexProperty", "index");

        // Mockオブジェクトの設定
        MockPageContext pageContext
            = (MockPageContext) TagUTUtil.getPageContext(pageLinks);

        ActionMapping mapping = new ActionMapping();
        mapping.setName("pageLinks");
        pageContext.getRequest().setAttribute(Globals.MAPPING_KEY, mapping);

        Map<String, String> links = new HashMap<String, String>();
        links.put(PageLinksTag.MAX_DSP_SIZE, "a");
        UTUtil.setPrivateField(pageLinks, "links", links);

        StringBuilder sb = new StringBuilder();
        int row = 10;
        int startIndex = 100;
        int totalCount = 200;

        // テスト実施
        pageLinks.addDirectSubmit(sb, row, startIndex, totalCount);

        // 判定
        String result = "<a href=\"#\" onclick=\"pageLinkSubmit(document.page" +
                "Links.row,document.pageLinks.index,10,50)\">6</a>&nbsp;<a hr" +
                "ef=\"#\" onclick=\"pageLinkSubmit(document.pageLinks.row,doc" +
                "ument.pageLinks.index,10,60)\">7</a>&nbsp;<a href=\"#\" oncl" +
                "ick=\"pageLinkSubmit(document.pageLinks.row,document.pageLin" +
                "ks.index,10,70)\">8</a>&nbsp;<a href=\"#\" onclick=\"pageLin" +
                "kSubmit(document.pageLinks.row,document.pageLinks.index,10,8" +
                "0)\">9</a>&nbsp;<a href=\"#\" onclick=\"pageLinkSubmit(docum" +
                "ent.pageLinks.row,document.pageLinks.index,10,90)\">10</a>&n" +
                "bsp;<b>11</b>&nbsp;<a href=\"#\" onclick=\"pageLinkSubmit(do" +
                "cument.pageLinks.row,document.pageLinks.index,10,110)\">12</" +
                "a>&nbsp;<a href=\"#\" onclick=\"pageLinkSubmit(document.page" +
                "Links.row,document.pageLinks.index,10,120)\">13</a>&nbsp;<a " +
                "href=\"#\" onclick=\"pageLinkSubmit(document.pageLinks.row,d" +
                "ocument.pageLinks.index,10,130)\">14</a>&nbsp;<a href=\"#\" " +
                "onclick=\"pageLinkSubmit(document.pageLinks.row,document.pag" +
                "eLinks.index,10,140)\">15</a>&nbsp;";
        assertEquals(result, sb.toString());
    }

    /**
     * testAddDirectSubmit03()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：B
     * <br><br>
     * 入力値：(引数) sb:""<br>
     *         (引数) row:10<br>
     *         (引数) startIndex:2<br>
     *         (引数) totalCount:200<br>
     *         (状態) formName:"pageLinks"<br>
     *         (状態) rowProperty:"row"<br>
     *         (状態) indexProperty:"index"<br>
     *         (状態) directLinkNo<br>
     *                (maxPageCount):10<br>
     *                （プロパティからmaxDirectLinkCountの値の
     *                値が取得できず、デフォルトの値）<br>
     *         
     * <br>
     * 期待値：(状態変化) sb:<a href="#" onclick="pageLinkSubmit(
     * document.pageLinks.row,document.pageLinks.index,10,0)">1</a>&nbsp;
     * <b>2</b>&nbsp;<a href="#" onclick="pageLinkSubmit(document.pageLinks.row,
     * document.pageLinks.index,10,20)">3</a>&nbsp;<a href="#" 
     * onclick="pageLinkSubmit(document.pageLinks.row,document.pageLinks.index,
     * 10,30)">4</a>&nbsp;<a href="#" onclick="pageLinkSubmit(
     * document.pageLinks.row,document.pageLinks.index,10,40)">5</a>&nbsp;
     * <a href="#" onclick="pageLinkSubmit(document.pageLinks.row,
     * document.pageLinks.index,10,50)">6</a>&nbsp;<a href="#" 
     * onclick="pageLinkSubmit(document.pageLinks.row,document.pageLinks.index,
     * 10,60)">7</a>&nbsp;<a href="#" onclick="pageLinkSubmit(
     * document.pageLinks.row,document.pageLinks.index,10,70)">8</a>&nbsp;
     * <a href="#" onclick="pageLinkSubmit(document.pageLinks.row,
     * document.pageLinks.index,10,80)">9</a>&nbsp;<a href="#" 
     * onclick="pageLinkSubmit(document.pageLinks.row,document.pageLinks.index,
     * 10,90)">10</a>&nbsp;<br>
     *         
     * <br>
     * 引数rowが10、startIndexが2、totalCountが200で、directLinkNoがnullの場合
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testAddDirectSubmit03() throws Exception {
        // 前処理
        UTUtil.setPrivateField(pageLinks, "rowProperty", "row");
        UTUtil.setPrivateField(pageLinks, "indexProperty", "index");

        // Mockオブジェクトの設定
        MockPageContext pageContext
            = (MockPageContext) TagUTUtil.getPageContext(pageLinks);

        ActionMapping mapping = new ActionMapping();
        mapping.setName("pageLinks");
        pageContext.getRequest().setAttribute(Globals.MAPPING_KEY, mapping);

        Map<String, String> links = new HashMap<String, String>();
        UTUtil.setPrivateField(pageLinks, "links", links);

        StringBuilder sb = new StringBuilder();
        int row = 10;
        int startIndex = 2;
        int totalCount = 200;

        // テスト実施
        pageLinks.addDirectSubmit(sb, row, startIndex, totalCount);

        // 判定
        String result = "<a href=\"#\" onclick=\"pageLinkSubmit(document.page" +
                "Links.row,document.pageLinks.index,10,0)\">1</a>&nbsp;<b>2</" +
                "b>&nbsp;<a href=\"#\" onclick=\"pageLinkSubmit(document.page" +
                "Links.row,document.pageLinks.index,10,20)\">3</a>&nbsp;<a hr" +
                "ef=\"#\" onclick=\"pageLinkSubmit(document.pageLinks.row,doc" +
                "ument.pageLinks.index,10,30)\">4</a>&nbsp;<a href=\"#\" oncl" +
                "ick=\"pageLinkSubmit(document.pageLinks.row,document.pageLin" +
                "ks.index,10,40)\">5</a>&nbsp;<a href=\"#\" onclick=\"pageLin" +
                "kSubmit(document.pageLinks.row,document.pageLinks.index,10,5" +
                "0)\">6</a>&nbsp;<a href=\"#\" onclick=\"pageLinkSubmit(docum" +
                "ent.pageLinks.row,document.pageLinks.index,10,60)\">7</a>&nb" +
                "sp;<a href=\"#\" onclick=\"pageLinkSubmit(document.pageLinks" +
                ".row,document.pageLinks.index,10,70)\">8</a>&nbsp;<a href=\"" +
                "#\" onclick=\"pageLinkSubmit(document.pageLinks.row,document" +
                ".pageLinks.index,10,80)\">9</a>&nbsp;<a href=\"#\" onclick=" +
                "\"pageLinkSubmit(document.pageLinks.row,document.pageLinks.i" +
                "ndex,10,90)\">10</a>&nbsp;";
        assertEquals(result, sb.toString());
    }

    /**
     * testAddDirectSubmit04()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：B
     * <br><br>
     * 入力値：(引数) sb:""<br>
     *         (引数) row:10<br>
     *         (引数) startIndex:2<br>
     *         (引数) totalCount:50<br>
     *         (状態) formName:"pageLinks"<br>
     *         (状態) rowProperty:"row"<br>
     *         (状態) indexProperty:"index"<br>
     *         (状態) directLinkNo<br>
     *                (maxPageCount):10<br>
     *                （プロパティからmaxDirectLinkCountの値の
     *                値が取得できず、デフォルトの値）<br>
     *         
     * <br>
     * 期待値：(状態変化) sb:<a href="#" onclick="pageLinkSubmit(
     * document.pageLinks.row,document.pageLinks.index,10,0)">1</a>&nbsp;
     * <b>2</b>&nbsp;<a href="#" onclick="pageLinkSubmit(
     * document.pageLinks.row,document.pageLinks.index,10,20)">3</a>&nbsp;
     * <a href="#" onclick="pageLinkSubmit(document.pageLinks.row,
     * document.pageLinks.index,10,30)">4</a>&nbsp;<a href="#" 
     * onclick="pageLinkSubmit(document.pageLinks.row,document.pageLinks.index,
     * 10,40)">5</a>&nbsp;<br>
     *         
     * <br>
     * 引数rowが10、startIndexが2、totalCountが50で、directLinkNoがnullの場合
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testAddDirectSubmit04() throws Exception {
        // 前処理
        UTUtil.setPrivateField(pageLinks, "rowProperty", "row");
        UTUtil.setPrivateField(pageLinks, "indexProperty", "index");

        // Mockオブジェクトの設定
        MockPageContext pageContext
            = (MockPageContext) TagUTUtil.getPageContext(pageLinks);

        ActionMapping mapping = new ActionMapping();
        mapping.setName("pageLinks");
        pageContext.getRequest().setAttribute(Globals.MAPPING_KEY, mapping);

        Map<String, String> links = new HashMap<String, String>();
        UTUtil.setPrivateField(pageLinks, "links", links);

        StringBuilder sb = new StringBuilder();
        int row = 10;
        int startIndex = 2;
        int totalCount = 50;

        // テスト実施
        pageLinks.addDirectSubmit(sb, row, startIndex, totalCount);

        // 判定
        String result = "<a href=\"#\" onclick=\"pageLinkSubmit(document.page" +
                "Links.row,document.pageLinks.index,10,0)\">1</a>&nbsp;<b>2</" +
                "b>&nbsp;<a href=\"#\" onclick=\"pageLinkSubmit(document.page" +
                "Links.row,document.pageLinks.index,10,20)\">3</a>&nbsp;<a hr" +
                "ef=\"#\" onclick=\"pageLinkSubmit(document.pageLinks.row,doc" +
                "ument.pageLinks.index,10,30)\">4</a>&nbsp;<a href=\"#\" oncl" +
                "ick=\"pageLinkSubmit(document.pageLinks.row,document.pageLin" +
                "ks.index,10,40)\">5</a>&nbsp;";
        assertEquals(result, sb.toString());
    }

    /**
     * testAddDirectSubmit05()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：B
     * <br><br>
     * 入力値：(引数) sb:""<br>
     *         (引数) row:0<br>
     *         (引数) startIndex:0<br>
     *         (引数) totalCount:0<br>
     *         (状態) formName:"pageLinks"<br>
     *         (状態) rowProperty:"row"<br>
     *         (状態) indexProperty:"index"<br>
     *         (状態) directLinkNo<br>
     *                (maxPageCount):10<br>
     *                （プロパティからmaxDirectLinkCountの値の
     *                値が取得できず、デフォルトの値）<br>
     *         
     * <br>
     * 期待値：(状態変化) sb:<a href="#" onclick="pageLinkSubmit(
     * document.pageLinks.row,document.pageLinks.index,0,0)">1</a>&nbsp;<br>
     *         
     * <br>
     * 引数rowが0、startIndexが0、totalCountが0で、directLinkNoがnullの場合
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testAddDirectSubmit05() throws Exception {
        // 前処理
        UTUtil.setPrivateField(pageLinks, "rowProperty", "row");
        UTUtil.setPrivateField(pageLinks, "indexProperty", "index");

        // Mockオブジェクトの設定
        MockPageContext pageContext
            = (MockPageContext) TagUTUtil.getPageContext(pageLinks);

        ActionMapping mapping = new ActionMapping();
        mapping.setName("pageLinks");
        pageContext.getRequest().setAttribute(Globals.MAPPING_KEY, mapping);

        Map<String, String> links = new HashMap<String, String>();
        UTUtil.setPrivateField(pageLinks, "links", links);

        StringBuilder sb = new StringBuilder();
        int row = 0;
        int startIndex = 0;
        int totalCount = 0;

        // テスト実施
        pageLinks.addDirectSubmit(sb, row, startIndex, totalCount);

        // 判定
        String result = "<a href=\"#\" onclick=\"pageLinkSubmit(document.page" +
                "Links.row,document.pageLinks.index,0,0)\">1</a>&nbsp;";
        assertEquals(result, sb.toString());
    }

    /**
     * testAddDirectSubmit06()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：B
     * <br><br>
     * 入力値：(引数) sb:""<br>
     *         (引数) row:-1<br>
     *         (引数) startIndex:-1<br>
     *         (引数) totalCount:-1<br>
     *         (状態) formName:"pageLinks"<br>
     *         (状態) rowProperty:"row"<br>
     *         (状態) indexProperty:"index"<br>
     *         (状態) directLinkNo<br>
     *                (maxPageCount):10<br>
     *                （プロパティからmaxDirectLinkCountの値の
     *                値が取得できず、デフォルトの値）<br>
     *         
     * <br>
     * 期待値：(状態変化) sb:<a href="#" onclick="pageLinkSubmit(
     * document.pageLinks.row,document.pageLinks.index,-1,0)">1</a>&nbsp;<br>
     *         
     * <br>
     * 引数rowが-1、startIndexが-1、totalCountが-1で、directLinkNoがnullの場合
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testAddDirectSubmit06() throws Exception {
        // 前処理
        UTUtil.setPrivateField(pageLinks, "rowProperty", "row");
        UTUtil.setPrivateField(pageLinks, "indexProperty", "index");

        // Mockオブジェクトの設定
        MockPageContext pageContext
            = (MockPageContext) TagUTUtil.getPageContext(pageLinks);

        ActionMapping mapping = new ActionMapping();
        mapping.setName("pageLinks");
        pageContext.getRequest().setAttribute(Globals.MAPPING_KEY, mapping);

        Map<String, String> links = new HashMap<String, String>();
        UTUtil.setPrivateField(pageLinks, "links", links);

        StringBuilder sb = new StringBuilder();
        int row = -1;
        int startIndex = -1;
        int totalCount = -1;

        // テスト実施
        pageLinks.addDirectSubmit(sb, row, startIndex, totalCount);

        // 判定
        String result = "<a href=\"#\" onclick=\"pageLinkSubmit(document.page" +
                "Links.row,document.pageLinks.index,-1,0)\">1</a>&nbsp;";
        assertEquals(result, sb.toString());
    }

    /**
     * testAddNextSubmit01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：B
     * <br><br>
     * 入力値：(引数) sb:""<br>
     *         (引数) row:10<br>
     *         (引数) startIndex:20<br>
     *         (引数) totalCount:50<br>
     *         (状態) formName:"pageLinks"<br>
     *         (状態) maxLinkNo:10<br>
     *         (状態) rowProperty:"row"<br>
     *         (状態) indexProperty:"index"<br>
     *         (状態) プロパティの設定<br>
     *                「Map links」:next1.char=&gt;<br>
     *                next2.char=<br>
     *                next5.char=&gt;&gt;<br>
     *                next10.char=&gt;&gt;&gt;<br>
     *         
     * <br>
     * 期待値：(状態変化) sb:<a href="#" onclick="pageLinkSubmit(
     * document.pageLinks.row,document.pageLinks.index,10,30)">
     * &gt;</a>&nbsp;&gt;&gt;&nbsp;&gt;&gt;&gt;&nbsp;<br>
     *         
     * <br>
     * 引数rowが10、startIndexが20、totalCountが50の場合
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testAddNextSubmit01() throws Exception {
        // 前処理
        UTUtil.setPrivateField(pageLinks, "rowProperty", "row");
        UTUtil.setPrivateField(pageLinks, "indexProperty", "index");
        UTUtil.setPrivateField(pageLinks, "maxLinkNo", new Integer(10));

        // Mockオブジェクトの設定
        MockPageContext pageContext
            = (MockPageContext) TagUTUtil.getPageContext(pageLinks);

        ActionMapping mapping = new ActionMapping();
        mapping.setName("pageLinks");
        pageContext.getRequest().setAttribute(Globals.MAPPING_KEY, mapping);

        Map<String, String> links = new HashMap<String, String>();
        links.put("next1.char", "&gt;");
        links.put("next2.char", "");
        links.put("next5.char", "&gt;&gt;");
        links.put("next10.char", "&gt;&gt;&gt;");
        UTUtil.setPrivateField(pageLinks, "links", links);

        StringBuilder sb = new StringBuilder();
        int row = 10;
        int startIndex = 20;
        int totalCount = 50;

        // テスト実施
        pageLinks.addNextSubmit(sb, row, startIndex, totalCount);

        // 判定
        String result = "<a href=\"#\" onclick=\"pageLinkSubmit(document.page" +
                "Links.row,document.pageLinks.index,10,30)\">&gt;</a>&nbsp;&g" +
                "t;&gt;&nbsp;&gt;&gt;&gt;&nbsp;";
        assertEquals(result, sb.toString());
    }

    /**
     * testAddNextSubmit02()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：B
     * <br><br>
     * 入力値：(引数) sb:""<br>
     *         (引数) row:0<br>
     *         (引数) startIndex:0<br>
     *         (引数) totalCount:0<br>
     *         (状態) formName:"pageLinks"<br>
     *         (状態) maxLinkNo:10<br>
     *         (状態) rowProperty:"row"<br>
     *         (状態) indexProperty:"index"<br>
     *         (状態) プロパティの設定<br>
     *                「Map links」:next1.char=&gt;<br>
     *                next2.char=<br>
     *                next5.char=&gt;&gt;<br>
     *                next10.char=&gt;&gt;&gt;<br>
     *         
     * <br>
     * 期待値：(状態変化) sb:&gt;&nbsp;&gt;&gt;&nbsp;&gt;&gt;&gt;&nbsp;<br>
     *         
     * <br>
     * 引数rowが0、startIndexが0、totalCountが0の場合
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testAddNextSubmit02() throws Exception {
        // 前処理
        UTUtil.setPrivateField(pageLinks, "rowProperty", "row");
        UTUtil.setPrivateField(pageLinks, "indexProperty", "index");
        UTUtil.setPrivateField(pageLinks, "maxLinkNo", new Integer(10));

        // Mockオブジェクトの設定
        MockPageContext pageContext
            = (MockPageContext) TagUTUtil.getPageContext(pageLinks);

        ActionMapping mapping = new ActionMapping();
        mapping.setName("pageLinks");
        pageContext.getRequest().setAttribute(Globals.MAPPING_KEY, mapping);

        Map<String, String> links = new HashMap<String, String>();
        links.put("next1.char", "&gt;");
        links.put("next2.char", "");
        links.put("next5.char", "&gt;&gt;");
        links.put("next10.char", "&gt;&gt;&gt;");
        UTUtil.setPrivateField(pageLinks, "links", links);

        StringBuilder sb = new StringBuilder();
        int row = 0;
        int startIndex = 0;
        int totalCount = 0;

        // テスト実施
        pageLinks.addNextSubmit(sb, row, startIndex, totalCount);

        // 判定
        String result = "&gt;&nbsp;&gt;&gt;&nbsp;&gt;&gt;&gt;&nbsp;";
        assertEquals(result, sb.toString());
    }

    /**
     * testAddNextSubmit03()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：B
     * <br><br>
     * 入力値：(引数) sb:""<br>
     *         (引数) row:-1<br>
     *         (引数) startIndex:-1<br>
     *         (引数) totalCount:-1<br>
     *         (状態) formName:"pageLinks"<br>
     *         (状態) maxLinkNo:10<br>
     *         (状態) rowProperty:"row"<br>
     *         (状態) indexProperty:"index"<br>
     *         (状態) プロパティの設定<br>
     *                「Map links」:next1.char=&gt;<br>
     *         
     * <br>
     * 期待値：(状態変化) sb:<a href="#" onclick="pageLinkSubmit(
     * document.pageLinks.row,document.pageLinks.index,-1,-2)">
     * &gt;</a>&nbsp;<br>
     *         
     * <br>
     * 引数rowが-1、startIndexが-1、totalCountが-1の場合
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testAddNextSubmit03() throws Exception {
        // 前処理
        UTUtil.setPrivateField(pageLinks, "rowProperty", "row");
        UTUtil.setPrivateField(pageLinks, "indexProperty", "index");
        UTUtil.setPrivateField(pageLinks, "maxLinkNo", new Integer(10));

        // Mockオブジェクトの設定
        MockPageContext pageContext
            = (MockPageContext) TagUTUtil.getPageContext(pageLinks);

        ActionMapping mapping = new ActionMapping();
        mapping.setName("pageLinks");
        pageContext.getRequest().setAttribute(Globals.MAPPING_KEY, mapping);

        Map<String, String> links = new HashMap<String, String>();
        links.put("next1.char", "&gt;");
        UTUtil.setPrivateField(pageLinks, "links", links);

        StringBuilder sb = new StringBuilder();
        int row = -1;
        int startIndex = -1;
        int totalCount = -1;

        // テスト実施
        pageLinks.addNextSubmit(sb, row, startIndex, totalCount);

        // 判定
        String result = "<a href=\"#\" onclick=\"pageLinkSubmit(document.page" +
                "Links.row,document.pageLinks.index,-1,-2)\">&gt;</a>&nbsp;";
        assertEquals(result, sb.toString());
    }

    /**
     * testAddNextSubmit04()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：B
     * <br><br>
     * 入力値：(引数) sb:""<br>
     *         (引数) row:10<br>
     *         (引数) startIndex:20<br>
     *         (引数) totalCount:50<br>
     *         (状態) formName:"pageLinks"<br>
     *         (状態) maxLinkNo:1<br>
     *         (状態) rowProperty:"row"<br>
     *         (状態) indexProperty:"index"<br>
     *         (状態) プロパティの設定<br>
     *                「Map links」:next1.char=&gt;<br>
     *                next2.char=<br>
     *                next5.char=&gt;&gt;<br>
     *                next10.char=&gt;&gt;&gt;<br>
     *         
     * <br>
     * 期待値：(状態変化) sb:<a href="#" onclick="pageLinkSubmit(
     * document.pageLinks.row,document.pageLinks.index,10,30)">
     * &gt;</a>&nbsp;<br>
     *         
     * <br>
     * 引数rowが10、startIndexが20、totalCountが50で、maxLinksNoが１の場合
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testAddNextSubmit04() throws Exception {
        // 前処理
        UTUtil.setPrivateField(pageLinks, "rowProperty", "row");
        UTUtil.setPrivateField(pageLinks, "indexProperty", "index");
        UTUtil.setPrivateField(pageLinks, "maxLinkNo", new Integer(1));

        // Mockオブジェクトの設定
        MockPageContext pageContext
            = (MockPageContext) TagUTUtil.getPageContext(pageLinks);

        ActionMapping mapping = new ActionMapping();
        mapping.setName("pageLinks");
        pageContext.getRequest().setAttribute(Globals.MAPPING_KEY, mapping);

        Map<String, String> links = new HashMap<String, String>();
        links.put("next1.char", "&gt;");
        links.put("next2.char", "");
        links.put("next5.char", "&gt;&gt;");
        links.put("next10.char", "&gt;&gt;&gt;");
        UTUtil.setPrivateField(pageLinks, "links", links);

        StringBuilder sb = new StringBuilder();
        int row = 10;
        int startIndex = 20;
        int totalCount = 50;

        // テスト実施
        pageLinks.addNextSubmit(sb, row, startIndex, totalCount);

        // 判定
        String result = "<a href=\"#\" onclick=\"pageLinkSubmit(document.page" +
                "Links.row,document.pageLinks.index,10,30)\">&gt;</a>&nbsp;";
        assertEquals(result, sb.toString());
    }

    /**
     * testAddPrevLink01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：B
     * <br><br>
     * 入力値：(引数) sb:""<br>
     *         (引数) row:10<br>
     *         (引数) startIndex:20<br>
     *         (引数) totalCount:100<br>
     *         (状態) action:"/list"<br>
     *         (状態) maxLinkNo:10<br>
     *         (状態) rowProperty:"row"<br>
     *         (状態) indexProperty:"index"<br>
     *         (状態) links:prev1.char=&lt;<br>
     *                prev2.char=<br>
     *                prev5.char=&lt;&lt;<br>
     *                prev10.char=&lt;&lt;&lt;<br>
     *         
     * <br>
     * 期待値：(状態変化) sb:&gt;&gt;&gt;&nbsp;&gt;&gt;&nbsp;
     * <a href="/list?row=10&index=10">&gt;</a>&nbsp;<br>
     *         
     * <br>
     * 引数rowが10、startIndexが20、totalCountが100の場合
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testAddPrevLink01() throws Exception {
        // 前処理
        UTUtil.setPrivateField(pageLinks, "action", "/list");
        UTUtil.setPrivateField(pageLinks, "rowProperty", "row");
        UTUtil.setPrivateField(pageLinks, "indexProperty", "index");
        UTUtil.setPrivateField(pageLinks, "maxLinkNo", new Integer(10));
        TagUTUtil.setContextPath(pageLinks, "");

        Map<String, String> links = new HashMap<String, String>();
        links.put("prev1.char", "&lt;");
        links.put("prev2.char", "");
        links.put("prev5.char", "&lt;&lt;");
        links.put("prev10.char", "&lt;&lt;&lt;");
        UTUtil.setPrivateField(pageLinks, "links", links);

        StringBuilder sb = new StringBuilder();
        int row = 10;
        int startIndex = 20;
        int totalCount = 100;

        // テスト実施
        pageLinks.addPrevLink(sb, row, startIndex, totalCount);
        
        // 判定
        String result = "&lt;&lt;&lt;&nbsp;&lt;&lt;&nbsp;" +
                "<a href=\"/list?row=10&index=10\">&lt;</a>&nbsp;";
        assertEquals(result, sb.toString());
    }

    /**
     * testAddPrevLink02()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：B
     * <br><br>
     * 入力値：(引数) sb:""<br>
     *         (引数) row:0<br>
     *         (引数) startIndex:0<br>
     *         (引数) totalCount:0<br>
     *         (状態) action:"/list?no=1"<br>
     *         (状態) maxLinkNo:10<br>
     *         (状態) rowProperty:"row"<br>
     *         (状態) indexProperty:"index"<br>
     *         (状態) links:prev1.char=&lt;<br>
     *                prev2.char=<br>
     *                prev5.char=&lt;&lt;<br>
     *                prev10.char=&lt;&lt;&lt;<br>
     *         
     * <br>
     * 期待値：(状態変化) sb:<a href="/list?no=1&row=0&index=0">
     * &lt;&lt;&lt;</a>&nbsp;<a href="/list?no=1&row=0&index=0">
     * &lt;&lt;</a>&nbsp;<a href="/list?no=1&row=0&index=0">&lt;</a>&nbsp;<br>
     *         
     * <br>
     * 引数rowが0、startIndexが0、totalCountが0の場合
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testAddPrevLink02() throws Exception {
        // 前処理
        UTUtil.setPrivateField(pageLinks, "action", "/list?no=1");
        UTUtil.setPrivateField(pageLinks, "rowProperty", "row");
        UTUtil.setPrivateField(pageLinks, "indexProperty", "index");
        UTUtil.setPrivateField(pageLinks, "maxLinkNo", new Integer(10));
        TagUTUtil.setContextPath(pageLinks, "");

        Map<String, String> links = new HashMap<String, String>();
        links.put("prev1.char", "&lt;");
        links.put("prev2.char", "");
        links.put("prev5.char", "&lt;&lt;");
        links.put("prev10.char", "&lt;&lt;&lt;");
        UTUtil.setPrivateField(pageLinks, "links", links);

        StringBuilder sb = new StringBuilder();
        int row = 0;
        int startIndex = 0;
        int totalCount = 0;

        // テスト実施
        pageLinks.addPrevLink(sb, row, startIndex, totalCount);
        
        // 判定
        String result = "<a href=\"/list?no=1&row=0&index=0\">&lt;&lt;&lt;</a" +
                ">&nbsp;<a href=\"/list?no=1&row=0&index=0\">&lt;&lt;</a>&nbs" +
                "p;<a href=\"/list?no=1&row=0&index=0\">&lt;</a>&nbsp;";
        assertEquals(result, sb.toString());
    }

    /**
     * testAddPrevLink03()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：B
     * <br><br>
     * 入力値：(引数) sb:""<br>
     *         (引数) row:-1<br>
     *         (引数) startIndex:-1<br>
     *         (引数) totalCount:-1<br>
     *         (状態) action:"/list"<br>
     *         (状態) maxLinkNo:10<br>
     *         (状態) rowProperty:"row"<br>
     *         (状態) indexProperty:"index"<br>
     *         (状態) links:prev1.char=&lt;<br>
     *         
     * <br>
     * 期待値：(状態変化) sb:<a href="/list?row=-1&index=0">&lt;</a>&nbsp;<br>
     *         
     * <br>
     * 引数rowが-1、startIndexが-1、totalCountが-1の場合
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testAddPrevLink03() throws Exception {
        // 前処理
        UTUtil.setPrivateField(pageLinks, "action", "/list");
        UTUtil.setPrivateField(pageLinks, "rowProperty", "row");
        UTUtil.setPrivateField(pageLinks, "indexProperty", "index");
        UTUtil.setPrivateField(pageLinks, "maxLinkNo", new Integer(10));
        TagUTUtil.setContextPath(pageLinks, "");

        Map<String, String> links = new HashMap<String, String>();
        links.put("prev1.char", "&lt;");
        UTUtil.setPrivateField(pageLinks, "links", links);

        StringBuilder sb = new StringBuilder();
        int row = -1;
        int startIndex = -1;
        int totalCount = -1;

        // テスト実施
        pageLinks.addPrevLink(sb, row, startIndex, totalCount);
        
        // 判定
        String result = "<a href=\"/list?row=-1&index=0\">&lt;</a>&nbsp;";
        assertEquals(result, sb.toString());
    }

    /**
     * testAddPrevLink04()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：B
     * <br><br>
     * 入力値：(引数) sb:""<br>
     *         (引数) row:10<br>
     *         (引数) startIndex:20<br>
     *         (引数) totalCount:100<br>
     *         (状態) action:"/list"<br>
     *         (状態) maxLinkNo:1<br>
     *         (状態) rowProperty:"row"<br>
     *         (状態) indexProperty:"index"<br>
     *         (状態) links:prev1.char=&lt;<br>
     *                prev2.char=<br>
     *                prev5.char=&lt;&lt;<br>
     *                prev10.char=&lt;&lt;&lt;<br>
     *         
     * <br>
     * 期待値：(状態変化) sb:
     * <a href="/list?row=10&index=10">&gt;</a>&nbsp;<br>
     *         
     * <br>
     * 引数rowが10、startIndexが20、totalCountが100で、maxLinkNoが１の場合
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testAddPrevLink04() throws Exception {
        // 前処理
        UTUtil.setPrivateField(pageLinks, "action", "/list");
        UTUtil.setPrivateField(pageLinks, "rowProperty", "row");
        UTUtil.setPrivateField(pageLinks, "indexProperty", "index");
        UTUtil.setPrivateField(pageLinks, "maxLinkNo", new Integer(1));
        TagUTUtil.setContextPath(pageLinks, "");

        Map<String, String> links = new HashMap<String, String>();
        links.put("prev1.char", "&lt;");
        links.put("prev2.char", "");
        links.put("prev5.char", "&lt;&lt;");
        links.put("prev10.char", "&lt;&lt;&lt;");
        UTUtil.setPrivateField(pageLinks, "links", links);

        StringBuilder sb = new StringBuilder();
        int row = 10;
        int startIndex = 20;
        int totalCount = 100;

        // テスト実施
        pageLinks.addPrevLink(sb, row, startIndex, totalCount);
        
        // 判定
        String result = "<a href=\"/list?row=10&index=10\">&lt;</a>&nbsp;";
        assertEquals(result, sb.toString());
    }

    /**
     * testAddDirectLink01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：B
     * <br><br>
     * 入力値：(引数) sb:""<br>
     *         (引数) row:10<br>
     *         (引数) startIndex:100<br>
     *         (引数) totalCount:130<br>
     *         (状態) action:"/list?no=1"<br>
     *         (状態) rowProperty:"row"<br>
     *         (状態) indexProperty:"index"<br>
     *         (状態) directLinkNo<br>
     *                (maxPageCount):"10"<br>
     *                （プロパティから取得したmaxDirectLinkCountの値）<br>
     *         
     * <br>
     * 期待値：(状態変化) sb:<a href="/list?no=1&row=10&index=30">4</a>
     * &nbsp;<a href="/list?no=1&row=10&index=40">5</a>&nbsp;
     * <a href="/list?no=1&row=10&index=50">6</a>&nbsp;
     * <a href="/list?no=1&row=10&index=60">7</a>&nbsp;
     * <a href="/list?no=1&row=10&index=70">8</a>&nbsp;
     * <a href="/list?no=1&row=10&index=80">9</a>&nbsp;
     * <a href="/list?no=1&row=10&index=90">10</a>&nbsp;<b>11</b>&nbsp;
     * <a href="/list?no=1&row=10&index=110">12</a>&nbsp;
     * <a href="/list?no=1&row=10&index=120">13</a>&nbsp;<br>
     * <br>
     * 引数rowが10、startIndexが100、totalCountが130で、
     * directLinkNoに"10"が指定された場合
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testAddDirectLink01() throws Exception {
        // 前処理
        UTUtil.setPrivateField(pageLinks, "action", "/list?no=1");
        UTUtil.setPrivateField(pageLinks, "rowProperty", "row");
        UTUtil.setPrivateField(pageLinks, "indexProperty", "index");
        TagUTUtil.setContextPath(pageLinks, "");

        Map<String, String> links = new HashMap<String, String>();
        links.put(PageLinksTag.MAX_DSP_SIZE, "10");
        UTUtil.setPrivateField(pageLinks, "links", links);

        StringBuilder sb = new StringBuilder();
        int row = 10;
        int startIndex = 100;
        int totalCount = 130;

        // テスト実施
        pageLinks.addDirectLink(sb, row, startIndex, totalCount);

        // 判定
        String result = "<a href=\"/list?no=1&row=10&index=30\">4</a>&nbsp;<a" +
                " href=\"/list?no=1&row=10&index=40\">5</a>&nbsp;<a href=\"/l" +
                "ist?no=1&row=10&index=50\">6</a>&nbsp;<a href=\"/list?no=1&r" +
                "ow=10&index=60\">7</a>&nbsp;<a href=\"/list?no=1&row=10&inde" +
                "x=70\">8</a>&nbsp;<a href=\"/list?no=1&row=10&index=80\">9</" +
                "a>&nbsp;<a href=\"/list?no=1&row=10&index=90\">10</a>&nbsp;<" +
                "b>11</b>&nbsp;<a href=\"/list?no=1&row=10&index=110\">12</a>" +
                "&nbsp;<a href=\"/list?no=1&row=10&index=120\">13</a>&nbsp;";
        assertEquals(result, sb.toString());
    }

    /**
     * testAddDirectLink02()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：B
     * <br><br>
     * 入力値：(引数) sb:""<br>
     *         (引数) row:10<br>
     *         (引数) startIndex:100<br>
     *         (引数) totalCount:200<br>
     *         (状態) action:"/list"<br>
     *         (状態) directLinkNo<br>
     *                (maxPageCount):"a"<br>
     *                （プロパティから取得したmaxDirectLinkCountの値）<br>
     *         
     * <br>
     * 期待値：(状態変化) sb:<a href="/list?row=10&index=50">6</a>&nbsp;
     * <a href="/list?row=10&index=60">7</a>&nbsp;
     * <a href="/list?row=10&index=70">8</a>&nbsp;
     * <a href="/list?row=10&index=80">9</a>&nbsp;
     * <a href="/list?row=10&index=90">10</a>&nbsp;<b>11</b>&nbsp;
     * <a href="/list?row=10&index=110">12</a>&nbsp;
     * <a href="/list?row=10&index=120">13</a>&nbsp;
     * <a href="/list?row=10&index=130">14</a>&nbsp;
     * <a href="/list?row=10&index=140">15</a>&nbsp;<br>
     *         
     * <br>
     * 引数rowが10、startIndexが100、totalCountが200で、
     * directLinkNoに"a"が指定された場合
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testAddDirectLink02() throws Exception {
        // 前処理
        UTUtil.setPrivateField(pageLinks, "action", "/list");
        UTUtil.setPrivateField(pageLinks, "rowProperty", "row");
        UTUtil.setPrivateField(pageLinks, "indexProperty", "index");
        TagUTUtil.setContextPath(pageLinks, "");

        Map<String, String> links = new HashMap<String, String>();
        links.put(PageLinksTag.MAX_DSP_SIZE, "a");
        UTUtil.setPrivateField(pageLinks, "links", links);

        StringBuilder sb = new StringBuilder();
        int row = 10;
        int startIndex = 100;
        int totalCount = 200;

        // テスト実施
        pageLinks.addDirectLink(sb, row, startIndex, totalCount);

        // 判定
        String result = "<a href=\"/list?row=10&index=50\">6</a>&nbsp;<a href" +
                "=\"/list?row=10&index=60\">7</a>&nbsp;<a href=\"/list?row=10" +
                "&index=70\">8</a>&nbsp;<a href=\"/list?row=10&index=80\">9</" +
                "a>&nbsp;<a href=\"/list?row=10&index=90\">10</a>&nbsp;<b>11<" +
                "/b>&nbsp;<a href=\"/list?row=10&index=110\">12</a>&nbsp;<a h" +
                "ref=\"/list?row=10&index=120\">13</a>&nbsp;<a href=\"/list?r" +
                "ow=10&index=130\">14</a>&nbsp;<a href=\"/list?row=10&index=1" +
                "40\">15</a>&nbsp;";
        assertEquals(result, sb.toString());
    }

    /**
     * testAddDirectLink03()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：B
     * <br><br>
     * 入力値：(引数) sb:""<br>
     *         (引数) row:10<br>
     *         (引数) startIndex:2<br>
     *         (引数) totalCount:200<br>
     *         (状態) action:"/list"<br>
     *         (状態) directLinkNo<br>
     *                (maxPageCount):10<br>
     *                （プロパティからmaxDirectLinkCountの
     *                値の値が取得できず、デフォルトの値）<br>
     *         
     * <br>
     * 期待値：(状態変化) sb:<a href="/list?row=10&index=0">1</a>
     * &nbsp;<b>2</b>&nbsp;<a href="/list?row=10&index=20">3</a>
     * &nbsp;<a href="/list?row=10&index=30">4</a>&nbsp;
     * <a href="/list?row=10&index=40">5</a>&nbsp;
     * <a href="/list?row=10&index=50">6</a>&nbsp;
     * <a href="/list?row=10&index=60">7</a>&nbsp;
     * <a href="/list?row=10&index=70">8</a>&nbsp;
     * <a href="/list?row=10&index=80">9</a>&nbsp;
     * <a href="/list?row=10&index=90">10</a>&nbsp;<br>
     *         
     * <br>
     * 引数rowが10、startIndexが2、totalCountが200で、directLinkNoがnullの場合
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testAddDirectLink03() throws Exception {
        // 前処理
        UTUtil.setPrivateField(pageLinks, "action", "/list");
        UTUtil.setPrivateField(pageLinks, "rowProperty", "row");
        UTUtil.setPrivateField(pageLinks, "indexProperty", "index");
        TagUTUtil.setContextPath(pageLinks, "");

        Map<String, String> links = new HashMap<String, String>();
        UTUtil.setPrivateField(pageLinks, "links", links);

        StringBuilder sb = new StringBuilder();
        int row = 10;
        int startIndex = 2;
        int totalCount = 200;

        // テスト実施
        pageLinks.addDirectLink(sb, row, startIndex, totalCount);

        // 判定
        String result = "<a href=\"/list?row=10&index=0\">1</a>&nbsp;<b>2</b>" +
                "&nbsp;<a href=\"/list?row=10&index=20\">3</a>&nbsp;<a href=" +
                "\"/list?row=10&index=30\">4</a>&nbsp;<a href=\"/list?row=10&" +
                "index=40\">5</a>&nbsp;<a href=\"/list?row=10&index=50\">6</a" +
                ">&nbsp;<a href=\"/list?row=10&index=60\">7</a>&nbsp;<a href=" +
                "\"/list?row=10&index=70\">8</a>&nbsp;<a href=\"/list?row=10&" +
                "index=80\">9</a>&nbsp;<a href=\"/list?row=10&index=90\">10</" +
                "a>&nbsp;";
        assertEquals(result, sb.toString());
    }

    /**
     * testAddDirectLink04()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：B
     * <br><br>
     * 入力値：(引数) sb:""<br>
     *         (引数) row:10<br>
     *         (引数) startIndex:2<br>
     *         (引数) totalCount:50<br>
     *         (状態) action:"/list"<br>
     *         (状態) directLinkNo<br>
     *                (maxPageCount):10<br>
     *                （プロパティからmaxDirectLinkCountの値の
     *                値が取得できず、デフォルトの値）<br>
     *         
     * <br>
     * 期待値：(状態変化) sb:<a href="/list?row=10&index=0">1</a>&nbsp;
     * <b>2</b>&nbsp;<a href="/list?row=10&index=20">3</a>&nbsp;
     * <a href="/list?row=10&index=30">4</a>&nbsp;
     * <a href="/list?row=10&index=40">5</a>&nbsp;<br>
     *         
     * <br>
     * 引数rowが10、startIndexが2、totalCountが50で、directLinkNoがnullの場合
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testAddDirectLink04() throws Exception {
        // 前処理
        UTUtil.setPrivateField(pageLinks, "action", "/list");
        UTUtil.setPrivateField(pageLinks, "rowProperty", "row");
        UTUtil.setPrivateField(pageLinks, "indexProperty", "index");
        TagUTUtil.setContextPath(pageLinks, "");

        Map<String, String> links = new HashMap<String, String>();
        UTUtil.setPrivateField(pageLinks, "links", links);

        StringBuilder sb = new StringBuilder();
        int row = 10;
        int startIndex = 2;
        int totalCount = 50;

        // テスト実施
        pageLinks.addDirectLink(sb, row, startIndex, totalCount);

        // 判定
        String result = "<a href=\"/list?row=10&index=0\">1</a>&nbsp;<b>2</b>" +
                "&nbsp;<a href=\"/list?row=10&index=20\">3</a>&nbsp;<a href=" +
                "\"/list?row=10&index=30\">4</a>&nbsp;<a href=\"/list?row=10&" +
                "index=40\">5</a>&nbsp;";
        assertEquals(result, sb.toString());
    }

    /**
     * testAddDirectLink05()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：B
     * <br><br>
     * 入力値：(引数) sb:""<br>
     *         (引数) row:0<br>
     *         (引数) startIndex:0<br>
     *         (引数) totalCount:0<br>
     *         (状態) action:"/list"<br>
     *         (状態) directLinkNo<br>
     *                (maxPageCount):10<br>
     *                （プロパティからmaxDirectLinkCountの値
     *                の値が取得できず、デフォルトの値）<br>
     *         
     * <br>
     * 期待値：(状態変化) sb:<a href="/list?row=0&index=0">1</a>&nbsp;<br>
     *         
     * <br>
     * 引数rowが0、startIndexが0、totalCountが0で、directLinkNoがnullの場合
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testAddDirectLink05() throws Exception {
        // 前処理
        UTUtil.setPrivateField(pageLinks, "action", "/list");
        UTUtil.setPrivateField(pageLinks, "rowProperty", "row");
        UTUtil.setPrivateField(pageLinks, "indexProperty", "index");
        TagUTUtil.setContextPath(pageLinks, "");

        Map<String, String> links = new HashMap<String, String>();
        UTUtil.setPrivateField(pageLinks, "links", links);

        StringBuilder sb = new StringBuilder();
        int row = 0;
        int startIndex = 0;
        int totalCount = 0;

        // テスト実施
        pageLinks.addDirectLink(sb, row, startIndex, totalCount);

        // 判定
        String result = "<a href=\"/list?row=0&index=0\">1</a>&nbsp;";
        assertEquals(result, sb.toString());
    }

    /**
     * testAddDirectLink06()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：B
     * <br><br>
     * 入力値：(引数) sb:""<br>
     *         (引数) row:-1<br>
     *         (引数) startIndex:-1<br>
     *         (引数) totalCount:-1<br>
     *         (状態) action:"/list"<br>
     *         (状態) directLinkNo<br>
     *                (maxPageCount):10<br>
     *                （プロパティからmaxDirectLinkCountの値
     *                の値が取得できず、デフォルトの値）<br>
     *         
     * <br>
     * 期待値：(状態変化) sb:<a href="/list?row=-1&index=0">1</a>&nbsp;<br>
     *         
     * <br>
     * 引数rowが-1、startIndexが-1、totalCountが-1で、directLinkNoがnullの場合
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testAddDirectLink06() throws Exception {
        // 前処理
        UTUtil.setPrivateField(pageLinks, "action", "/list");
        UTUtil.setPrivateField(pageLinks, "rowProperty", "row");
        UTUtil.setPrivateField(pageLinks, "indexProperty", "index");
        TagUTUtil.setContextPath(pageLinks, "");

        Map<String, String> links = new HashMap<String, String>();
        UTUtil.setPrivateField(pageLinks, "links", links);

        StringBuilder sb = new StringBuilder();
        int row = -1;
        int startIndex = -1;
        int totalCount = -1;

        // テスト実施
        pageLinks.addDirectLink(sb, row, startIndex, totalCount);

        // 判定
        String result = "<a href=\"/list?row=-1&index=0\">1</a>&nbsp;";
        assertEquals(result, sb.toString());
    }

    /**
     * testAddNextLink01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：B
     * <br><br>
     * 入力値：(引数) sb:""<br>
     *         (引数) row:10<br>
     *         (引数) startIndex:20<br>
     *         (引数) totalCount:50<br>
     *         (状態) action:"/list?no=1"<br>
     *         (状態) maxLinkNo:10<br>
     *         (状態) rowProperty:"row"<br>
     *         (状態) indexProperty:"index"<br>
     *         (状態) プロパティの設定<br>
     *                「Map links」:next1.char=&gt;<br>
     *                next2.char=<br>
     *                next5.char=&gt;&gt;<br>
     *                next10.char=&gt;&gt;&gt;<br>
     *         
     * <br>
     * 期待値：(状態変化) sb:<a href="/list?no=1&row=10&index=30">&gt;
     * </a>&nbsp;&gt;&gt;&nbsp;&gt;&gt;&gt;&nbsp;<br>
     *         
     * <br>
     * 引数rowが10、startIndexが20、totalCountが50の場合
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testAddNextLink01() throws Exception {
        // 前処理
        UTUtil.setPrivateField(pageLinks, "action", "/list?no=1");
        UTUtil.setPrivateField(pageLinks, "rowProperty", "row");
        UTUtil.setPrivateField(pageLinks, "indexProperty", "index");
        UTUtil.setPrivateField(pageLinks, "maxLinkNo", new Integer(10));
        TagUTUtil.setContextPath(pageLinks, "");

        Map<String, String> links = new HashMap<String, String>();
        links.put("next1.char", "&gt;");
        links.put("next2.char", "");
        links.put("next5.char", "&gt;&gt;");
        links.put("next10.char", "&gt;&gt;&gt;");
        UTUtil.setPrivateField(pageLinks, "links", links);

        StringBuilder sb = new StringBuilder();
        int row = 10;
        int startIndex = 20;
        int totalCount = 50;

        // テスト実施
        pageLinks.addNextLink(sb, row, startIndex, totalCount);
        
        // 判定
        String result = "<a href=\"/list?no=1&row=10&index=30\">&gt;</a>" +
                "&nbsp;&gt;&gt;&nbsp;&gt;&gt;&gt;&nbsp;";
        assertEquals(result, sb.toString());
    }

    /**
     * testAddNextLink02()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：B
     * <br><br>
     * 入力値：(引数) sb:""<br>
     *         (引数) row:0<br>
     *         (引数) startIndex:0<br>
     *         (引数) totalCount:0<br>
     *         (状態) action:"/list"<br>
     *         (状態) maxLinkNo:10<br>
     *         (状態) rowProperty:"row"<br>
     *         (状態) indexProperty:"index"<br>
     *         (状態) プロパティの設定<br>
     *                「Map links」:next1.char=&gt;<br>
     *                next2.char=<br>
     *                next5.char=&gt;&gt;<br>
     *                next10.char=&gt;&gt;&gt;<br>
     *         
     * <br>
     * 期待値：(状態変化) sb:&gt;&nbsp;&gt;&gt;&nbsp;&gt;&gt;&gt;&nbsp;<br>
     *         
     * <br>
     * 引数rowが0、startIndexが0、totalCountが0の場合
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testAddNextLink02() throws Exception {
        // 前処理
        UTUtil.setPrivateField(pageLinks, "action", "/list");
        UTUtil.setPrivateField(pageLinks, "rowProperty", "row");
        UTUtil.setPrivateField(pageLinks, "indexProperty", "index");
        UTUtil.setPrivateField(pageLinks, "maxLinkNo", new Integer(10));
        TagUTUtil.setContextPath(pageLinks, "");

        Map<String, String> links = new HashMap<String, String>();
        links.put("next1.char", "&gt;");
        links.put("next2.char", "");
        links.put("next5.char", "&gt;&gt;");
        links.put("next10.char", "&gt;&gt;&gt;");
        UTUtil.setPrivateField(pageLinks, "links", links);

        StringBuilder sb = new StringBuilder();
        int row = 0;
        int startIndex = 0;
        int totalCount = 0;

        // テスト実施
        pageLinks.addNextLink(sb, row, startIndex, totalCount);
        
        // 判定
        String result = "&gt;&nbsp;&gt;&gt;&nbsp;&gt;&gt;&gt;&nbsp;";
        assertEquals(result, sb.toString());
    }

    /**
     * testAddNextLink03()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：B
     * <br><br>
     * 入力値：(引数) sb:""<br>
     *         (引数) row:-1<br>
     *         (引数) startIndex:-1<br>
     *         (引数) totalCount:-1<br>
     *         (状態) action:"/list"<br>
     *         (状態) maxLinkNo:10<br>
     *         (状態) rowProperty:"row"<br>
     *         (状態) indexProperty:"index"<br>
     *         (状態) プロパティの設定<br>
     *                「Map links」:next1.char=&gt;<br>
     *         
     * <br>
     * 期待値：(状態変化) sb:<a href="/list?row=-1&index=-2">&gt;</a>&nbsp;<br>
     *         
     * <br>
     * 引数rowが-1、startIndexが-1、totalCountが-1の場合
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testAddNextLink03() throws Exception {
        // 前処理
        UTUtil.setPrivateField(pageLinks, "action", "/list");
        UTUtil.setPrivateField(pageLinks, "rowProperty", "row");
        UTUtil.setPrivateField(pageLinks, "indexProperty", "index");
        UTUtil.setPrivateField(pageLinks, "maxLinkNo", new Integer(10));
        TagUTUtil.setContextPath(pageLinks, "");

        Map<String, String> links = new HashMap<String, String>();
        links.put("next1.char", "&gt;");
        UTUtil.setPrivateField(pageLinks, "links", links);

        StringBuilder sb = new StringBuilder();
        int row = -1;
        int startIndex = -1;
        int totalCount = -1;

        // テスト実施
        pageLinks.addNextLink(sb, row, startIndex, totalCount);
        
        // 判定
        String result = "<a href=\"/list?row=-1&index=-2\">&gt;</a>&nbsp;";
        assertEquals(result, sb.toString());
    }

    /**
     * testAddNextLink04()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：B
     * <br><br>
     * 入力値：(引数) sb:""<br>
     *         (引数) row:10<br>
     *         (引数) startIndex:20<br>
     *         (引数) totalCount:50<br>
     *         (状態) action:"/list?no=1"<br>
     *         (状態) maxLinkNo:1<br>
     *         (状態) rowProperty:"row"<br>
     *         (状態) indexProperty:"index"<br>
     *         (状態) プロパティの設定<br>
     *                「Map links」:next1.char=&gt;<br>
     *                next2.char=<br>
     *                next5.char=&gt;&gt;<br>
     *                next10.char=&gt;&gt;&gt;<br>
     *         
     * <br>
     * 期待値：(状態変化) sb:<a href="/list?no=1&row=10&index=30">&gt;
     * </a>&nbsp;<br>
     *         
     * <br>
     * 引数rowが10、startIndexが20、totalCountが50で、maxLinkNoが１の場合
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testAddNextLink04() throws Exception {
        // 前処理
        UTUtil.setPrivateField(pageLinks, "action", "/list?no=1");
        UTUtil.setPrivateField(pageLinks, "rowProperty", "row");
        UTUtil.setPrivateField(pageLinks, "indexProperty", "index");
        UTUtil.setPrivateField(pageLinks, "maxLinkNo", new Integer(1));
        TagUTUtil.setContextPath(pageLinks, "");

        Map<String, String> links = new HashMap<String, String>();
        links.put("next1.char", "&gt;");
        links.put("next2.char", "");
        links.put("next5.char", "&gt;&gt;");
        links.put("next10.char", "&gt;&gt;&gt;");
        UTUtil.setPrivateField(pageLinks, "links", links);

        StringBuilder sb = new StringBuilder();
        int row = 10;
        int startIndex = 20;
        int totalCount = 50;

        // テスト実施
        pageLinks.addNextLink(sb, row, startIndex, totalCount);
        
        // 判定
        String result = "<a href=\"/list?no=1&row=10&index=30\">&gt;</a>&nbsp;";
        assertEquals(result, sb.toString());
    }

    /**
     * testGetLinkProperty01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(状態) propertyファイルに設定するPAGE_LINKS_PREFIX:キーなし<br>
     *         
     * <br>
     * 期待値：(状態変化) links:size:0<br>
     *         (状態変化) maxLinkNo:1<br>
     *         
     * <br>
     * propertyファイルにPAGE_LINKS_PREFIXのキーが存在しなかった場合、
     * linksへ値が設定されていないことを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetLinkProperty01() throws Exception {
        //テストデータ設定
        deleteProperty("pageLinks.");

        //テスト実行
        UTUtil.invokePrivate(pageLinks, "getLinkProperty");

        //テスト結果確認
        Map links2 = (Map) UTUtil.getPrivateField(pageLinks, "links");
        Integer maxLinkNo2 =
            (Integer) UTUtil.getPrivateField(pageLinks, "maxLinkNo");
        assertEquals(0, links2.size());
        assertEquals(1, maxLinkNo2.intValue());
    }

    /**
     * testGetLinkProperty02()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(状態) propertyファイルに設定するPAGE_LINKS_PREFIX:
     * pageLinks.=&lt;<br>
     *         
     * <br>
     * 期待値：(状態変化) links:size:0<br>
     *         (状態変化) maxLinkNo:1<br>
     *         
     * <br>
     * propertyファイルにPAGE_LINKS_PREFIXのキーが"pageLinks."の場合、
     * linksへ値が設定されていないことを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetLinkProperty02() throws Exception {
        //テストデータ設定
        addProperty("pageLinks.","&lt;");

        //テスト実行
        UTUtil.invokePrivate(pageLinks, "getLinkProperty");

        //テスト結果確認
        Map links2 = (Map) UTUtil.getPrivateField(pageLinks, "links");
        Integer maxLinkNo2 =
            (Integer) UTUtil.getPrivateField(pageLinks, "maxLinkNo");
        assertEquals(0, links2.size());
        assertEquals(1, maxLinkNo2.intValue());
    }

    /**
     * testGetLinkProperty03()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(状態) propertyファイルに設定するPAGE_LINKS_PREFIX:
     * pageLinks.prev1.char=&lt;<br>
     *         
     * <br>
     * 期待値：(状態変化) links:size:1<br>
     *                    prev1.char=&lt;<br>
     *         (状態変化) maxLinkNo:1<br>
     *         
     * <br>
     * propertyファイルにPAGE_LINKS_PREFIXのキーが1件存在する場合、
     * linksへ値が1件設定され、"maxLinkNo"に「1」が設定されていることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetLinkProperty03() throws Exception {
        //テストデータ設定
        addProperty("pageLinks.prev1.char", "&lt;");

        //テスト実行
        UTUtil.invokePrivate(pageLinks, "getLinkProperty");

        //テスト結果確認
        Map links2 = (Map) UTUtil.getPrivateField(pageLinks, "links");
        assertEquals(1, links2.size());
        Integer maxLinkNo2 =
            (Integer) UTUtil.getPrivateField(pageLinks, "maxLinkNo");
        assertEquals(1, maxLinkNo2.intValue());
        assertEquals("&lt;", (String) links2.get("prev1.char"));
    }

    /**
     * testGetLinkProperty04()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(状態) propertyファイルに設定するPAGE_LINKS_PREFIX:
     * pageLinks.prev2.char=&lt;<br>
     *         
     * <br>
     * 期待値：(状態変化) links:size:1<br>
     *                    prev2.char=&lt;<br>
     *         (状態変化) maxLinkNo:2<br>
     *         
     * <br>
     * propertyファイルにPAGE_LINKS_PREFIXのキーが1件存在する場合、
     * linksへ値が1件設定され、"maxLinkNo"に「2」が設定されていることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetLinkProperty04() throws Exception {
        addProperty("pageLinks.prev2.char", "&lt;");

        //テスト実行
        UTUtil.invokePrivate(pageLinks, "getLinkProperty");

        //テスト結果確認
        Map links2 = (Map) UTUtil.getPrivateField(pageLinks, "links");
        assertEquals(1, links2.size());
        Integer maxLinkNo2 =
            (Integer) UTUtil.getPrivateField(pageLinks, "maxLinkNo");
        assertEquals(2, maxLinkNo2.intValue());
        assertEquals("&lt;", (String) links2.get("prev2.char"));
    }

    /**
     * testGetLinkProperty05()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(状態) propertyファイルに設定するPAGE_LINKS_PREFIX:
     * pageLinks.prev-2.char=&lt;<br>
     *         
     * <br>
     * 期待値：(状態変化) links:size:1<br>
     *                    prev-2.char=&lt;<br>
     *         (状態変化) maxLinkNo:1<br>
     *         
     * <br>
     * propertyファイルにPAGE_LINKS_PREFIXのキーが1件存在する場合、
     * linksへ値が1件設定され、"maxLinkNo"に「1」が設定されていることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetLinkProperty05() throws Exception {
        addProperty("pageLinks.prev-2.char", "&lt;");

        //テスト実行
        UTUtil.invokePrivate(pageLinks, "getLinkProperty");

        //テスト結果確認
        Map links2 = (Map) UTUtil.getPrivateField(pageLinks, "links");
        assertEquals(1, links2.size());
        Integer maxLinkNo2 =
            (Integer) UTUtil.getPrivateField(pageLinks, "maxLinkNo");
        assertEquals(1, maxLinkNo2.intValue());
        assertEquals("&lt;", (String) links2.get("prev-2.char"));
    }

    /**
     * testGetLinkProperty06()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(状態) propertyファイルに設定するPAGE_LINKS_PREFIX:
     * pageLinks.next1.char=&gt;<br>
     *         
     * <br>
     * 期待値：(状態変化) links:size:1<br>
     *                    next1.char=&gt;<br>
     *         (状態変化) maxLinkNo:1<br>
     *         
     * <br>
     * propertyファイルにPAGE_LINKS_PREFIXのキーが1件存在する場合、
     * linksへ値が1件設定され、"maxLinkNo"に「1」が設定されていることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetLinkProperty06() throws Exception {
        addProperty("pageLinks.next1.char", "&gt;");

        //テスト実行
        UTUtil.invokePrivate(pageLinks, "getLinkProperty");

        //テスト結果確認
        Map links2 = (Map) UTUtil.getPrivateField(pageLinks, "links");
        assertEquals(1, links2.size());
        Integer maxLinkNo2 =
            (Integer) UTUtil.getPrivateField(pageLinks, "maxLinkNo");
        assertEquals(1, maxLinkNo2.intValue());
        assertEquals("&gt;", (String) links2.get("next1.char"));
    }

    /**
     * testGetLinkProperty07()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(状態) propertyファイルに設定するPAGE_LINKS_PREFIX:
     * pageLinks.test.char=&lt;<br>
     *         
     * <br>
     * 期待値：(状態変化) links:size:0<br>
     *         (状態変化) maxLinkNo:1<br>
     *         
     * <br>
     * propertyファイルにPAGE_LINKS_PREFIXのキーが1件存在するがキーが
     * 指定外の場合、linksへ値が設定されていないことを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetLinkProperty07() throws Exception {
        addProperty("pageLinks.test.char", "&lt;");

        //テスト実行
        UTUtil.invokePrivate(pageLinks, "getLinkProperty");

        //テスト結果確認
        Map links2 = (Map) UTUtil.getPrivateField(pageLinks, "links");
        assertEquals(0, links2.size());
        Integer maxLinkNo2 =
            (Integer) UTUtil.getPrivateField(pageLinks, "maxLinkNo");
        assertEquals(1, maxLinkNo2.intValue());
    }

    /**
     * testGetLinkProperty08()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(状態) propertyファイルに設定するPAGE_LINKS_PREFIX:
     * pageLinks.nexttest.char=&lt;<br>
     *         
     * <br>
     * 期待値：(状態変化) links:size:0<br>
     *         (状態変化) maxLinkNo:1<br>
     *         
     * <br>
     * propertyファイルにPAGE_LINKS_PREFIXのキーが1件存在するがキーが
     * ページジャンプ数の指定が文字列の場合、
     * linksへ値が設定されていないことを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetLinkProperty08() throws Exception {
        addProperty("pageLinks.nexttest.char", "&lt;");

        //テスト実行
        UTUtil.invokePrivate(pageLinks, "getLinkProperty");

        //テスト結果確認
        Map links2 = (Map) UTUtil.getPrivateField(pageLinks, "links");
        assertEquals(0, links2.size());
        Integer maxLinkNo2 =
            (Integer) UTUtil.getPrivateField(pageLinks, "maxLinkNo");
        assertEquals(1, maxLinkNo2.intValue());
    }

    /**
     * testGetLinkProperty09()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(状態) propertyファイルに設定するPAGE_LINKS_PREFIX:
     * pageLinks.prev1.char=&lt;<br>
     *                pageLinks.prev2.char=&lt;<br>
     *         
     * <br>
     * 期待値：(状態変化) links:size:2<br>
     *                    prev1.char=&lt;<br>
     *                    prev2.char=&lt;<br>
     *         (状態変化) maxLinkNo:2<br>
     *         
     * <br>
     * propertyファイルにPAGE_LINKS_PREFIXのキーが2件存在する場合、
     * linksへ値が2件設定され、"maxLinkNo"に「2」が設定されていることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetLinkProperty09() throws Exception {
        addProperty("pageLinks.prev1.char", "&lt;");
        addProperty("pageLinks.prev2.char", "&lt;");

        //テスト実行
        UTUtil.invokePrivate(pageLinks, "getLinkProperty");

        //テスト結果確認
        Map links2 = (Map) UTUtil.getPrivateField(pageLinks, "links");
        assertEquals(2, links2.size());
        Integer maxLinkNo2 =
            (Integer) UTUtil.getPrivateField(pageLinks, "maxLinkNo");
        assertEquals(2, maxLinkNo2.intValue());
        assertEquals("&lt;", (String) links2.get("prev1.char"));
        assertEquals("&lt;", (String) links2.get("prev2.char"));
    }

    /**
     * testGetLinkProperty10()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(状態) propertyファイルに設定するPAGE_LINKS_PREFIX:
     * pageLinks.maxDirectLinkCount=3<br>
     *         
     * <br>
     * 期待値：(状態変化) links:size:1<br>
     *                    maxDirectLinkCount=3<br>
     *         (状態変化) maxLinkNo:1<br>
     *         
     * <br>
     * propertyファイルにpageLinks.maxDirectLinkCount=3が指定された
     * 場合のテストケース。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetLinkProperty10() throws Exception {
        addProperty("pageLinks.maxDirectLinkCount", "3");

        //テスト実行
        UTUtil.invokePrivate(pageLinks, "getLinkProperty");

        //テスト結果確認
        Map links2 = (Map) UTUtil.getPrivateField(pageLinks, "links");
        assertEquals(1, links2.size());
        Integer maxLinkNo2 =
            (Integer) UTUtil.getPrivateField(pageLinks, "maxLinkNo");
        assertEquals(1, maxLinkNo2.intValue());
        assertEquals("3", (String) links2.get("maxDirectLinkCount"));
    }

    /**
     * testGetLinkProperty11()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(状態) propertyファイルに設定するPAGE_LINKS_PREFIX:
     * pageLinks.maxDirectLinkCount=0<br>
     *         
     * <br>
     * 期待値：(状態変化) links:size:1<br>
     *                    maxDirectLinkCount=0<br>
     *         (状態変化) maxLinkNo:1<br>
     *         
     * <br>
     * propertyファイルにpageLinks.maxDirectLinkCount=0
     * が指定された場合のテストケース。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetLinkProperty11() throws Exception {
        addProperty("pageLinks.maxDirectLinkCount", "0");

        //テスト実行
        UTUtil.invokePrivate(pageLinks, "getLinkProperty");

        //テスト結果確認
        Map links2 = (Map) UTUtil.getPrivateField(pageLinks, "links");
        assertEquals(1, links2.size());
        Integer maxLinkNo2 =
            (Integer) UTUtil.getPrivateField(pageLinks, "maxLinkNo");
        assertEquals(1, maxLinkNo2.intValue());
        assertEquals("0", (String) links2.get("maxDirectLinkCount"));
    }

    /**
     * testGetLinkProperty12()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(状態) propertyファイルに設定するPAGE_LINKS_PREFIX:
     * pageLinks.maxDirectLinkCount=-1<br>
     *         
     * <br>
     * 期待値：(状態変化) links:size:1<br>
     *                    maxDirectLinkCount=-1<br>
     *         (状態変化) maxLinkNo:1<br>
     *         
     * <br>
     * propertyファイルにpageLinks.maxDirectLinkCount=-1が
     * 指定された場合のテストケース。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetLinkProperty12() throws Exception {
        addProperty("pageLinks.maxDirectLinkCount", "-1");

        //テスト実行
        UTUtil.invokePrivate(pageLinks, "getLinkProperty");

        //テスト結果確認
        Map links2 = (Map) UTUtil.getPrivateField(pageLinks, "links");
        assertEquals(1, links2.size());
        Integer maxLinkNo2 =
            (Integer) UTUtil.getPrivateField(pageLinks, "maxLinkNo");
        assertEquals(1, maxLinkNo2.intValue());
        assertEquals("-1", (String) links2.get("maxDirectLinkCount"));
    }

    /**
     * testGetPageIndex01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) row:0<br>
     *         
     * <br>
     * 期待値：(戻り値) int:0<br>
     *         
     * <br>
     * 表示行数が0の場合
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetPageIndex01() throws Exception {
        // 前処理
        int row = 0;
        int startIndex = 0;

        // テスト実施
        int value = pageLinks.getPageIndex(row, startIndex);

        // 判定
        assertEquals(0, value);
    }

    /**
     * testGetPageIndex02()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) row:1<br>
     *         (引数) startIndex:0<br>
     *         
     * <br>
     * 期待値：(戻り値) int:1<br>
     *         
     * <br>
     * 表示行数が1で、開始行数が0の場合
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetPageIndex02() throws Exception {
        // 前処理
        int row = 1;
        int startIndex = 0;

        // テスト実施
        int value = pageLinks.getPageIndex(row, startIndex);

        // 判定
        assertEquals(1, value);
    }

    /**
     * testGetPageIndex03()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) row:-10<br>
     *         
     * <br>
     * 期待値：(戻り値) int:0<br>
     *         
     * <br>
     * 表示件数が-10の場合
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetPageIndex03() throws Exception {
        // 前処理
        int row = -10;
        int startIndex = 0;

        // テスト実施
        int value = pageLinks.getPageIndex(row, startIndex);

        // 判定
        assertEquals(0, value);
    }

    /**
     * testGetPageIndex04()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) row:10<br>
     *         (引数) startIndex:1<br>
     *         
     * <br>
     * 期待値：(戻り値) int:2<br>
     *         
     * <br>
     * 表示件数が10で、開始行数が1の場合
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetPageIndex04() throws Exception {
        // 前処理
        int row = 10;
        int startIndex = 1;

        // テスト実施
        int value = pageLinks.getPageIndex(row, startIndex);

        // 判定
        assertEquals(2, value);
    }

    /**
     * testGetPageIndex05()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) row:10<br>
     *         (引数) startIndex:-10<br>
     *         
     * <br>
     * 期待値：(戻り値) int:0<br>
     *         
     * <br>
     * 表示件数が10で、開始行数が-10の場合
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetPageIndex05() throws Exception {
        // 前処理
        int row = 10;
        int startIndex = -10;

        // テスト実施
        int value = pageLinks.getPageIndex(row, startIndex);

        // 判定
        assertEquals(0, value);
    }

    /**
     * testGetPageIndex06()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) row:10<br>
     *         (引数) startIndex:10<br>
     *         
     * <br>
     * 期待値：(戻り値) int:2<br>
     *         
     * <br>
     * 表示件数が10で、開始行数が10の場合
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetPageIndex06() throws Exception {
        // 前処理
        int row = 10;
        int startIndex = 10;

        // テスト実施
        int value = pageLinks.getPageIndex(row, startIndex);

        // 判定
        assertEquals(2, value);
    }

    /**
     * testGetPageIndex07()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) row:10<br>
     *         (引数) startIndex:12<br>
     *         
     * <br>
     * 期待値：(戻り値) int:3<br>
     *         
     * <br>
     * 表示件数が10で、開始行数が12の場合
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetPageIndex07() throws Exception {
        // 前処理
        int row = 10;
        int startIndex = 12;

        // テスト実施
        int value = pageLinks.getPageIndex(row, startIndex);

        // 判定
        assertEquals(3, value);
    }

    /**
     * testGetPageCount01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) row:0<br>
     *         
     * <br>
     * 期待値：(戻り値) int:1<br>
     *         
     * <br>
     * 表示行数が0の場合
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetPageCount01() throws Exception {
        // 前処理
        int row = 0;
        int totalCount = 0;

        // テスト実施
        int value = pageLinks.getPageCount(row, totalCount);

        // 判定
        assertEquals(1, value);
    }

    /**
     * testGetPageCount02()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) row:1<br>
     *         (引数) totalCount:0<br>
     *         
     * <br>
     * 期待値：(戻り値) int:0<br>
     *         
     * <br>
     * 表示行数が1で、全件数が0の場合
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetPageCount02() throws Exception {
        // 前処理
        int row = 1;
        int totalCount = 0;

        // テスト実施
        int value = pageLinks.getPageCount(row, totalCount);

        // 判定
        assertEquals(0, value);
    }

    /**
     * testGetPageCount03()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) row:-10<br>
     *         
     * <br>
     * 期待値：(戻り値) int:1<br>
     *         
     * <br>
     * 表示件数が-10の場合
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetPageCount03() throws Exception {
        // 前処理
        int row = -10;
        int totalCount = 1;

        // テスト実施
        int value = pageLinks.getPageCount(row, totalCount);

        // 判定
        assertEquals(1, value);
    }

    /**
     * testGetPageCount04()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) row:10<br>
     *         (引数) totalCount:1<br>
     *         
     * <br>
     * 期待値：(戻り値) int:1<br>
     *         
     * <br>
     * 表示件数が10で、全件数が1の場合
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetPageCount04() throws Exception {
        // 前処理
        int row = 10;
        int totalCount = 1;

        // テスト実施
        int value = pageLinks.getPageCount(row, totalCount);

        // 判定
        assertEquals(1, value);
    }

    /**
     * testGetPageCount05()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) row:10<br>
     *         (引数) totalCount:-10<br>
     *         
     * <br>
     * 期待値：(戻り値) int:-1<br>
     *         
     * <br>
     * 表示件数が10で、全件数が-10の場合
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetPageCount05() throws Exception {
        // 前処理
        int row = 10;
        int totalCount = -10;

        // テスト実施
        int value = pageLinks.getPageCount(row, totalCount);

        // 判定
        assertEquals(-1, value);
    }

    /**
     * testGetPageCount06()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) row:10<br>
     *         (引数) totalCount:10<br>
     *         
     * <br>
     * 期待値：(戻り値) int:1<br>
     *         
     * <br>
     * 表示件数が10で、全件数が10の場合
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetPageCount06() throws Exception {
        // 前処理
        int row = 10;
        int totalCount = 10;

        // テスト実施
        int value = pageLinks.getPageCount(row, totalCount);

        // 判定
        assertEquals(1, value);
    }

    /**
     * testGetPageCount07()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) row:10<br>
     *         (引数) totalCount:15<br>
     *         
     * <br>
     * 期待値：(戻り値) int:2<br>
     *         
     * <br>
     * 表示件数が10で、全件数が15の場合
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetPageCount07() throws Exception {
        // 前処理
        int row = 10;
        int totalCount = 15;

        // テスト実施
        int value = pageLinks.getPageCount(row, totalCount);

        // 判定
        assertEquals(2, value);
    }

    /**
     * testGetPageContextFlg01()
     * <br><br>
     * 
     * (異常系)
     * <br>
     * 観点：G
     * <br><br>
     * 入力値：(引数) pageContext:not null<br>
     *         (引数) key:null<br>
     *         
     * <br>
     * 期待値：(状態変化) 例外:IllegalArgumentException<br>
     *         
     * <br>
     * 引数keyがnullの場合
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetPageContextFlg01() throws Exception {
        // 前処理
        String key = null;

        //MockRunnerのMockPageContextを使用すると、
        //getAttaributeメソッドの引数keyがnullでもNullPointerExceptionが
        //発生しないため、SpringのMockPageContextを使用する。
        org.springframework.mock.web.MockPageContext pageContext
            = new org.springframework.mock.web.MockPageContext();
        pageLinks.setPageContext(pageContext);

        // テスト実施
        try {
            pageLinks.getPageContextFlg(pageContext, key);
            fail();
        } catch (IllegalArgumentException e) {
            //例外を確認。
        }
    }

    /**
     * testGetPageContextFlg02()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) pageContext:not null<br>
     *         (引数) key:""<br>
     *         (状態) pageContext内で保持している値:"key"=Boolean(true)<br>
     *         
     * <br>
     * 期待値：(戻り値) boolean:false<br>
     *         
     * <br>
     * 引数keyが空白で、pageContextにBoolean(true)で値が存在する場合
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetPageContextFlg02() throws Exception {
        // 前処理
        String key = "";
        PageContext pageContext = TagUTUtil.getPageContext(pageLinks);
        pageContext.setAttribute("key",new Boolean(true));

        // テスト実施
        boolean value = pageLinks.getPageContextFlg(pageContext, key);

        // 判定
        assertFalse(value);
    }

    /**
     * testGetPageContextFlg03()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) pageContext:not null<br>
     *         (引数) key:"key"<br>
     *         (状態) pageContext内で保持している値:"key"=String("true")<br>
     *         
     * <br>
     * 期待値：(戻り値) boolean:false<br>
     *         
     * <br>
     * pageContextにString("true")で値が存在する場合
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetPageContextFlg03() throws Exception {
        // 前処理
        String key = "key";
        PageContext pageContext = TagUTUtil.getPageContext(pageLinks);
        pageContext.setAttribute("key","true");

        // テスト実施
        boolean value = pageLinks.getPageContextFlg(pageContext, key);

        // 判定
        assertFalse(value);
    }

    /**
     * testGetPageContextFlg04()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) pageContext:not null<br>
     *         (引数) key:"key"<br>
     *         (状態) pageContext内で保持している値:"key"=Boolean(true)<br>
     *         
     * <br>
     * 期待値：(戻り値) boolean:true<br>
     *         
     * <br>
     * pageContextにBoolean(true)で値が存在する場合
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetPageContextFlg04() throws Exception {
        // 前処理
        String key = "key";
        PageContext pageContext = TagUTUtil.getPageContext(pageLinks);
        pageContext.setAttribute("key",new Boolean(true));

        // テスト実施
        boolean value = pageLinks.getPageContextFlg(pageContext, key);

        // 判定
        assertTrue(value);
    }

    /**
     * testGetPageContextFlg05()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) pageContext:not null<br>
     *         (引数) key:"key"<br>
     *         (状態) pageContext内で保持している値:"key"=null<br>
     *         
     * <br>
     * 期待値：(戻り値) boolean:false<br>
     *         
     * <br>
     * pageContextに設定されている値がnullの場合
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetPageContextFlg05() throws Exception {
        // 前処理
        String key = "key";
        PageContext pageContext = TagUTUtil.getPageContext(pageLinks);
        pageContext.setAttribute("key",null);

        // テスト実施
        boolean value = pageLinks.getPageContextFlg(pageContext, key);

        // 判定
        assertFalse(value);
    }

    /**
     * testSetPageContextFlg01()
     * <br><br>
     * 
     * (異常系)
     * <br>
     * 観点：G
     * <br><br>
     * 入力値：(引数) pageContext:not null<br>
     *         (引数) key:null<br>
     *         (状態) pageContext内で保持している値:なし<br>
     *         
     * <br>
     * 期待値：(状態変化) 例外:IllegalArgumentException<br>
     *         
     * <br>
     * 引数keyがnullの場合
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetPageContextFlg01() throws Exception {
        // 前処理
        String key = null;

        //MockRunnerのMockPageContextを使用すると、
        //setAttaributeメソッドの引数keyがnullでもNullPointerExceptionが
        //発生しないため、SpringのMockPageContextを使用する。
        org.springframework.mock.web.MockPageContext pageContext
            = new org.springframework.mock.web.MockPageContext();
        pageLinks.setPageContext(pageContext);

        // テスト実施
        try {
            pageLinks.setPageContextFlg(pageContext, key);
            fail();
        } catch (IllegalArgumentException e) {
            //例外を確認。
        }
    }

    /**
     * testSetPageContextFlg02()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) pageContext:not null<br>
     *         (引数) key:""<br>
     *         (状態) pageContext内で保持している値:なし<br>
     *         
     * <br>
     * 期待値：(状態変化) pageContext内で保持している値:""=Boolean(true)<br>
     *         
     * <br>
     * 引数keyが空白の場合
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetPageContextFlg02() throws Exception {
        // 前処理
        String key = "";
        PageContext pageContext = TagUTUtil.getPageContext(pageLinks);

        // テスト実施
        pageLinks.setPageContextFlg(pageContext, key);

        // 判定
        assertTrue(((Boolean)pageContext.getAttribute(key)).booleanValue());
    }

    /**
     * testSetPageContextFlg03()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) pageContext:not null<br>
     *         (引数) key:"key"<br>
     *         (状態) pageContext内で保持している値:なし<br>
     *         
     * <br>
     * 期待値：(状態変化) pageContext内で保持している値:"key"=Boolean(true)<br>
     *         
     * <br>
     * 引数keyが"key"の場合
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetPageContextFlg03() throws Exception {
        // 前処理
        String key = "key";
        PageContext pageContext = TagUTUtil.getPageContext(pageLinks);

        // テスト実施
        pageLinks.setPageContextFlg(pageContext, key);

        // 判定
        assertTrue(((Boolean)pageContext.getAttribute(key)).booleanValue());
    }

    /**
     * testLookup01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) pageContext:["row"= "a"]<br>
     *         (引数) name:null<br>
     *         (引数) property:"row"<br>
     *         
     * <br>
     * 期待値：(戻り値) Object:"a"<br>
     *         
     * <br>
     * nameがnullの場合
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testLookup01() throws Exception {
        // 前処理
        PageContext pageContext = TagUTUtil.getPageContext(pageLinks);
        pageContext.setAttribute("row", "a");
        String name = null;
        String property = "row";

        // テスト実施
        Object obj = pageLinks.lookup(pageContext, name, property, null);

        // 判定
        assertEquals("a", obj);
    }

    /**
     * testLookup02()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) pageContext:["row"= "b"]<br>
     *         (引数) name:""<br>
     *         (引数) property:"row"<br>
     *         
     * <br>
     * 期待値：(戻り値) Object:"b"<br>
     *         
     * <br>
     * nameが空白の場合
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testLookup02() throws Exception {
        // 前処理
        PageContext pageContext = TagUTUtil.getPageContext(pageLinks);
        pageContext.setAttribute("row", "b");
        String name = "";
        String property = "row";

        // テスト実施
        Object obj = pageLinks.lookup(pageContext, name, property, null);

        // 判定
        assertEquals("b", obj);
    }

    /**
     * testLookup03()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) pageContext:["bean"= <br>
     *                new PageLinksTag_BeanStub01(){<br>
     *                row="c", index="0", total="102"}]<br>
     *         (引数) name:"bean"<br>
     *         (引数) property:"row"<br>
     *         
     * <br>
     * 期待値：(戻り値) Object:"c"<br>
     *         
     * <br>
     * nameがNotNullの場合
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testLookup03() throws Exception {
        // 前処理
        PageContext pageContext = TagUTUtil.getPageContext(pageLinks);
        PageLinksTag_BeanStub01 bean = new PageLinksTag_BeanStub01();
        bean.setRow("c");
        pageContext.setAttribute("bean", bean);
        String name = "bean";
        String property = "row";

        // テスト実施
        Object obj = pageLinks.lookup(pageContext, name, property, null);

        // 判定
        assertEquals("c", obj);
    }

    /**
     * testLookup04()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) pageContext:null<br>
     *         (引数) name:null<br>
     *         (引数) property:null<br>
     *         
     * <br>
     * 期待値：(戻り値) Object:null<br>
     *         
     * <br>
     * propertyがnullの場合
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testLookup04() throws Exception {
        // 前処理
        PageContext pageContext = null;
        String name = null;
        String property = null;

        // テスト実施
        Object obj = pageLinks.lookup(pageContext, name, property, null);

        // 判定
        assertNull(obj);
    }

    /**
     * testLookup05()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) pageContext:null<br>
     *         (引数) name:null<br>
     *         (引数) property:""<br>
     *         
     * <br>
     * 期待値：(戻り値) Object:null<br>
     *         
     * <br>
     * propertyが空白の場合
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testLookup05() throws Exception {
        // 前処理
        PageContext pageContext = null;
        String name = null;
        String property = "";

        // テスト実施
        Object obj = pageLinks.lookup(pageContext, name, property, null);

        // 判定
        assertNull(obj);
    }

    /**
     * testGetInt01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) Object:Integer(1)<br>
     *         
     * <br>
     * 期待値：(戻り値) int:1<br>
     *         
     * <br>
     * 引数がIntegerの場合
     * 異常系はdoStartTag()にてテスト済み。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetInt01() throws Exception {
        // 前処理
        Integer obj = new Integer(1);

        // テスト実施
        int i = pageLinks.getInt(obj);

        // 判定
        assertEquals(1, i);
    }

    /**
     * testGetInt02()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) Object:String("2")<br>
     *         
     * <br>
     * 期待値：(戻り値) int:2<br>
     *         
     * <br>
     * 引数がStringの場合
     * 異常系はdoStartTag()にてテスト済み。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetInt02() throws Exception {
        // 前処理
        String obj = "2";

        // テスト実施
        int i = pageLinks.getInt(obj);

        // 判定
        assertEquals(2, i);
    }

    /**
     * testAttributePageCount01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) now:10<br>
     *         (引数) total:100<br>
     *         (状態) currentPageIndex:null<br>
     *         (状態) totalPageCount:null<br>
     *         
     * <br>
     * 期待値：(状態変化) pageContextに保存されているページカウント:currentPageIndex=10<br>
     *                    totalPageCount=100<br>
     *         
     * <br>
     * nullの場合
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testAttributePageCount01() throws Exception {
        // 前処理
        UTUtil.setPrivateField(pageLinks, "currentPageIndex", null);
        UTUtil.setPrivateField(pageLinks, "totalPageCount", null);

        // Mockオブジェクトの設定
        MockPageContext pageContext
            = (MockPageContext) TagUTUtil.getPageContext(pageLinks);

        // テスト実施
        pageLinks.attributePageCount(10,100);

        // 判定
        assertEquals(10,
                pageContext.getAttribute(PageLinksTag.CURRENT_PAGE_INDEX));
        assertEquals(100,
                pageContext.getAttribute(PageLinksTag.TOTAL_PAGE_COUNT));
    }

    /**
     * testAttributePageCount02()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) now:10<br>
     *         (引数) total:100<br>
     *         (状態) currentPageIndex:""<br>
     *         (状態) totalPageCount:""<br>
     *         
     * <br>
     * 期待値：(状態変化) pageContextに保存されているページカウント:currentPageIndex=10<br>
     *                    totalPageCount=100<br>
     *         
     * <br>
     * 空白の場合
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testAttributePageCount02() throws Exception {
        // 前処理
        UTUtil.setPrivateField(pageLinks, "currentPageIndex", "");
        UTUtil.setPrivateField(pageLinks, "totalPageCount", "");

        // Mockオブジェクトの設定
        MockPageContext pageContext
            = (MockPageContext) TagUTUtil.getPageContext(pageLinks);

        // テスト実施
        pageLinks.attributePageCount(10,100);

        // 判定
        assertEquals(10,
                pageContext.getAttribute(PageLinksTag.CURRENT_PAGE_INDEX));
        assertEquals(100,
                pageContext.getAttribute(PageLinksTag.TOTAL_PAGE_COUNT));
    }

    /**
     * testAttributePageCount03()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) now:10<br>
     *         (引数) total:100<br>
     *         (状態) currentPageIndex:"nownow"<br>
     *         (状態) totalPageCount:"totaltotal"<br>
     *         
     * <br>
     * 期待値：(状態変化) pageContextに保存されているページカウント:nownow=10<br>
     *                    totaltotal=100<br>
     *         
     * <br>
     * 文字列の場合
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testAttributePageCount03() throws Exception {
        // 前処理
        UTUtil.setPrivateField(pageLinks, "currentPageIndex", "nownow");
        UTUtil.setPrivateField(pageLinks, "totalPageCount", "totaltotal");

        // Mockオブジェクトの設定
        MockPageContext pageContext
            = (MockPageContext) TagUTUtil.getPageContext(pageLinks);

        // テスト実施
        pageLinks.attributePageCount(10,100);

        // 判定
        assertEquals(10,
                pageContext.getAttribute("nownow"));
        assertEquals(100,
                pageContext.getAttribute("totaltotal"));
    }

    /**
     * testAttributePageCount04()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) now:1<br>
     *         (引数) total:0<br>
     *         (状態) currentPageIndex:"nownow"<br>
     *         (状態) totalPageCount:"totaltotal"<br>
     *         
     * <br>
     * 期待値：(状態変化) pageContextに保存されているページカウント:nownow=0<br>
     *                    totaltotal=0<br>
     *         
     * <br>
     * 引数のtotalが０の場合
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testAttributePageCount04() throws Exception {
        // 前処理
        UTUtil.setPrivateField(pageLinks, "currentPageIndex", "nownow");
        UTUtil.setPrivateField(pageLinks, "totalPageCount", "totaltotal");

        // Mockオブジェクトの設定
        MockPageContext pageContext
            = (MockPageContext) TagUTUtil.getPageContext(pageLinks);

        // テスト実施
        pageLinks.attributePageCount(1,0);

        // 判定
        assertEquals(0,
                pageContext.getAttribute("nownow"));
        assertEquals(0,
                pageContext.getAttribute("totaltotal"));
    }

    /**
     * testRelease01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(状態) super.parent:new TagSupport()<br>
     *         (状態) action:"abc"<br>
     *         (状態) name:"abc"<br>
     *         (状態) rowProperty:"abc"<br>
     *         (状態) indexProperty:"abc"<br>
     *         (状態) totalProperty:"abc"<br>
     *         (状態) scope:"abc"<br>
     *         (状態) submit:true<br>
     *         (状態) forward:true<br>
     *         (状態) event:"abc"<br>
     *         (状態) resetIndex:true<br>
     *         
     * <br>
     * 期待値：(状態変化) super.parent:null<br>
     *         (状態変化) action:null<br>
     *         (状態変化) name:null<br>
     *         (状態変化) rowProperty:null<br>
     *         (状態変化) indexProperty:null<br>
     *         (状態変化) totalProperty:null<br>
     *         (状態変化) scope:null<br>
     *         (状態変化) submit:false<br>
     *         (状態変化) forward:false<br>
     *         (状態変化) event:PageLinksTag.DEFAULT_EVENT<br>
     *         (状態変化) resetIndex:false<br>
     *         
     * <br>
     * 正常系のみ。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testRelease01() throws Exception {
        // 前処理
        UTUtil.setPrivateField(pageLinks, "parent", new TagSupport());
        UTUtil.setPrivateField(pageLinks, "id", "abc");
        UTUtil.setPrivateField(pageLinks, "action", "abc");
        UTUtil.setPrivateField(pageLinks, "name", "abc");
        UTUtil.setPrivateField(pageLinks, "rowProperty", "abc");
        UTUtil.setPrivateField(pageLinks, "indexProperty", "abc");
        UTUtil.setPrivateField(pageLinks, "totalProperty", "abc");
        UTUtil.setPrivateField(pageLinks, "scope", "abc");
        UTUtil.setPrivateField(pageLinks, "submit", true);
        UTUtil.setPrivateField(pageLinks, "forward", true);
        UTUtil.setPrivateField(pageLinks, "event", "abc");
        UTUtil.setPrivateField(pageLinks, "resetIndex", true);
        UTUtil.setPrivateField(pageLinks, "currentPageIndex", "abc");
        UTUtil.setPrivateField(pageLinks, "totalPageCount", "abc");

        // テスト実施
        pageLinks.release();

        // 判定
        assertNull(UTUtil.getPrivateField(pageLinks, "parent"));
        assertNull(UTUtil.getPrivateField(pageLinks, "id"));
        assertNull(UTUtil.getPrivateField(pageLinks, "action"));
        assertNull(UTUtil.getPrivateField(pageLinks, "name"));
        assertNull(UTUtil.getPrivateField(pageLinks, "rowProperty"));
        assertNull(UTUtil.getPrivateField(pageLinks, "indexProperty"));
        assertNull(UTUtil.getPrivateField(pageLinks, "totalProperty"));
        assertNull(UTUtil.getPrivateField(pageLinks, "scope"));
        assertFalse(((Boolean)UTUtil.getPrivateField(
                pageLinks, "submit")).booleanValue());
        assertFalse(((Boolean)UTUtil.getPrivateField(
                pageLinks, "forward")).booleanValue());
        assertEquals(PageLinksTag.DEFAULT_EVENT, 
                UTUtil.getPrivateField(pageLinks, "event"));
        assertFalse(((Boolean)UTUtil.getPrivateField(
                pageLinks, "resetIndex")).booleanValue());
        assertEquals(PageLinksTag.CURRENT_PAGE_INDEX, 
                UTUtil.getPrivateField(pageLinks, "currentPageIndex"));
        assertEquals(PageLinksTag.TOTAL_PAGE_COUNT, 
                UTUtil.getPrivateField(pageLinks, "totalPageCount"));
    }

}
