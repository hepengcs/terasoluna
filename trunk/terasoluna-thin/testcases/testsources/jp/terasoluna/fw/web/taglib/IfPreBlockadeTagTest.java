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

import jp.terasoluna.fw.web.thin.ServerBlockageControlFilter;
import jp.terasoluna.utlib.TagUTUtil;
import jp.terasoluna.utlib.UTUtil;
import junit.framework.TestCase;

import com.mockrunner.mock.web.MockHttpServletRequest;

/**
 * IfPreBlockadeTag ブラックボックステスト。<br>
 * 前提条件<br>
 * WebLogicサーバが起動していること<br>
 * <br>
 */
public class IfPreBlockadeTagTest extends TestCase {

    // テスト対象
    IfPreBlockadeTag tag = null;

    /**
     * Constructor for IfPreBlockadeTagTest.
     * @param arg0
     */
    public IfPreBlockadeTagTest(String arg0) {
        super(arg0);
    }

    /*
     * @see TestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        tag = (IfPreBlockadeTag) TagUTUtil.create(IfPreBlockadeTag.class);
    }

    /*
     * @see TestCase#tearDown()
     */
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        tag = null;
    }

    /**
     * testDoStartTag01。<br>
     * <br>
     * (正常系)<br>
     * 観点：F<br>
     * <br>
     * 入力値<br>
     * sbc=Not Null<br>
     * isPreBlockaded=false<br>
     * isBlockaded=false<br>
     * req=Not Null(requestURI,contextPathあり)<br>
     * <br>
     * 期待値<br>
     * 戻り値:int=SKIP_BODY<br>
     * <br>
     * isPreBlockaded、isBlockaded共にfalseの場合のテストケース<br>
     */
    public void testDoStartTag01() throws Exception {
        IfPreBlockageTag_ServerBlockageControllerStub01 sbc = 
                new IfPreBlockageTag_ServerBlockageControllerStub01();
        // テスト設定
        sbc.open();
        UTUtil.setPrivateField(
            ServerBlockageControlFilter.class,
            "controller",
            sbc);
        // 擬似リクエスト
        PageContext pc = TagUTUtil.getPageContext(tag);
        MockHttpServletRequest req = (MockHttpServletRequest) pc.getRequest();
        req.setRequestURI("/sample1/logon.do");
        req.setContextPath("/sample1");

        // テスト実行
        int result = tag.doStartTag();

        // テスト結果確認
        assertEquals(Tag.SKIP_BODY, result);

    } /* testDoStartTag01 End */

    /**
     * testDoStartTag02。<br>
     * <br>
     * (正常系)<br>
     * 観点：F<br>
     * <br>
     * 入力値<br>
     * sbc=Not Null<br>
     * isPreBlockaded=true<br>
     * isBlockaded=false<br>
     * req=Not Null(requestURI,contextPathあり)<br>
     * <br>
     * 期待値<br>
     * 戻り値:int=EVAL_BODY_INCLUDE<br>
     * <br>
     * isPreBlockadedがtrue、isBlockadedがfalseの場合のテストケース<br>
     */
    public void testDoStartTag02() throws Exception {
        IfPreBlockageTag_ServerBlockageControllerStub02 sbc = 
            new IfPreBlockageTag_ServerBlockageControllerStub02();
        // テスト設定
        sbc.preBlockade();
        UTUtil.setPrivateField(
            ServerBlockageControlFilter.class,
            "controller",
            sbc);
        // 擬似リクエスト
        PageContext pc = TagUTUtil.getPageContext(tag);
        MockHttpServletRequest req = (MockHttpServletRequest) pc.getRequest();
        req.setRequestURI("/sample1/logon.do");
        req.setContextPath("/sample1");

        // テスト実行
        int result = tag.doStartTag();

        // テスト結果確認
        assertEquals(Tag.EVAL_BODY_INCLUDE, result);

    } /* testDoStartTag02 End */

    /**
     * testDoStartTag03。<br>
     * <br>
     * (正常系)<br>
     * 観点：F<br>
     * <br>
     * 入力値<br>
     * sbc=Not Null<br>
     * isPreBlockaded=false<br>
     * isBlockaded=true<br>
     * req=Not Null(requestURI,contextPathあり)<br>
     * <br>
     * 期待値<br>
     * 戻り値:int=EVAL_BODY_INCLUDE<br>
     * <br>
     * isPreBlockadedがfalse、isBlockadedがtrueの場合のテストケース<br>
     */
    public void testDoStartTag03() throws Exception {
        IfPreBlockageTag_ServerBlockageControllerStub03 sbc = 
            new IfPreBlockageTag_ServerBlockageControllerStub03();
        // テスト設定
        sbc.blockade();
        UTUtil.setPrivateField(
            ServerBlockageControlFilter.class,
            "controller",
            sbc);
        // 擬似リクエスト
        PageContext pc = TagUTUtil.getPageContext(tag);
        MockHttpServletRequest req = (MockHttpServletRequest) pc.getRequest();
        req.setRequestURI("/sample1/logon.do");
        req.setContextPath("/sample1");

        // テスト実行
        int result = tag.doStartTag();

        // テスト結果確認
        assertEquals(Tag.EVAL_BODY_INCLUDE, result);

    } /* testDoStartTag03 End */

    /**
     * testDoStartTag04。<br>
     * <br>
     * (正常系)<br>
     * 観点：F<br>
     * <br>
     * 入力値<br>
     * sbc=Not Null<br>
     * isPreBlockaded=true<br>
     * isBlockaded=true<br>
     * req=Not Null(requestURI,contextPathあり)<br>
     * <br>
     * 期待値<br>
     * 戻り値:int=EVAL_BODY_INCLUDE<br>
     * <br>
     * isPreBlockadedがtrue、isBlockadedがtrueの場合のテストケース<br>
     */
    public void testDoStartTag04() throws Exception {
        IfPreBlockageTag_ServerBlockageControllerStub04 sbc = 
            new IfPreBlockageTag_ServerBlockageControllerStub04();
        // テスト設定
        sbc.preBlockade();
        UTUtil.setPrivateField(
            ServerBlockageControlFilter.class,
            "controller",
            sbc);
        // 擬似リクエスト
        PageContext pc = TagUTUtil.getPageContext(tag);
        MockHttpServletRequest req = (MockHttpServletRequest) pc.getRequest();
        req.setRequestURI("/sample1/logon.do");
        req.setContextPath("/sample1");

        // テスト実行
        int result = tag.doStartTag();

        // テスト結果確認
        assertEquals(Tag.EVAL_BODY_INCLUDE, result);

    } /* testDoStartTag04 End */

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
    } /* testDoEndTag01 End */

}
