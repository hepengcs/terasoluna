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

import javax.servlet.jsp.tagext.TagData;
import javax.servlet.jsp.tagext.VariableInfo;

import junit.framework.TestCase;

/**
 * DateFormatterTeiBase ブラックボックステスト。<br>
 * 前提条件<br>
 * なし<br>
 * <br>
 */
public class DateFormatterTeiBaseTest extends TestCase {

    // テスト対象
    DateFormatterTeiBaseImpl01 tag = null;

    /**
     * Constructor for DateFormatterTeiBaseTest.
     * @param arg0
     */
    public DateFormatterTeiBaseTest(String arg0) {
        super(arg0);
    }

    /*
     * @see TestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        tag = new DateFormatterTeiBaseImpl01();
    }

    /*
     * @see TestCase#tearDown()
     */
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * testGetVariableInfo01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：EF
     * <br><br>
     * 入力値：(引数) data:new TagData({{"idon", "id"}})<br>
     *         
     * <br>
     * 期待値：(戻り値) variableInfo:空の配列<br>
     *         (状態変化) variableInfo配列数:0<br>
     *         
     * <br>
     * 引数TagData内に保持されている”id”の値がNULLで、VariableInfo配列(要素数0件)が返却されること場合のテストケース
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetVariableInfo01() throws Exception {

        // テストデータ設定
        Object[][] att = {{"idon", "id"}};
        TagData data = new TagData(att);

        // テスト実行
        VariableInfo[] vInfo = tag.getVariableInfo(data);

        // テスト結果確認
        assertEquals(0, vInfo.length);

    } /* testGetVariableInfo01 End */

    /**
     * testGetVariableInfo02()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：EF
     * <br><br>
     * 入力値：(引数) data:new TagData({{"id", "id"}})<br>
     *         
     * <br>
     * 期待値：(戻り値) variableInfo:Not Null<br>
     *         (状態変化) variableInfo[0].varName:"id"<br>
     *         (状態変化) variableInfo[0].className:"java.lang.String"<br>
     *         (状態変化) variableInfo[0].declare:true<br>
     *         (状態変化) variableInfo[0].scope:VariableInfo.AT_BEGIN<br>
     *         (状態変化) variableInfo配列数:1<br>
     *         
     * <br>
     * 引数TagData内に保持されている”id”の値がNULLではなく、VariableInfo配列(要素数1件)が返却されること場合のテストケース
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetVariableInfo02() throws Exception {

        // テストデータ設定
        Object[][] att = {{"id", "id"}};
        TagData data = new TagData(att);

        // テスト実行
        VariableInfo[] vInfo = tag.getVariableInfo(data);

        // テスト結果確認
        assertEquals(1, vInfo.length);
        assertEquals("id", vInfo[0].getVarName());
        assertEquals("java.lang.String", vInfo[0].getClassName());
        assertTrue(vInfo[0].getDeclare());
        assertEquals(VariableInfo.AT_BEGIN, vInfo[0].getScope());

    } /* testGetVariableInfo02 End */

} /* DateFormatterTeiBaseTest */
