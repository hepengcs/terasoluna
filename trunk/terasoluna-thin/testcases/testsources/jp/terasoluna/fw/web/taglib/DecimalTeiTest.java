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
 * DecimalTei ブラックボックステスト。<br>
 * 前提条件<br>
 * -<br>
 */
public class DecimalTeiTest extends TestCase {

    // テスト対象クラス生成
    DecimalTei tag = null;

    /**
     * Constructor for IterateRowSetTeiTest.
     * @param arg0
     */
    public DecimalTeiTest(String arg0) {
        super(arg0);
    }

    /*
     * @see TestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        tag = new DecimalTei();
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
     * 観点：FE
     * <br><br>
     * 入力値：(引数) data:new TagData({"id", "1"})<br>
     *         
     * <br>
     * 期待値：(戻り値) VariableInfo[]:Not Null<br>
     *         (状態変化) variableInfo[0].varName:dataのid属性<br>
     *         (状態変化) variableInfo[0].className:"java.lang.String"<br>
     *         (状態変化) variableInfo[0].declare:true<br>
     *         (状態変化) variableInfo[0].scope:VariableInfo.AT_BEGIN<br>
     *         (状態変化) variableInfoの配列数:1<br>
     *         
     * <br>
     * 引数TagData内に保持されているid属性がNot NULLで、VariableInfo配列にdataのid属性(VariableInfoコンストラクタの第一引数＝NotNull)がセットされ返却されることを確認するテストケース
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetVariableInfo01() throws Exception {
        //テストデータ設定
        Object[][] att = {{"id", "1"}};
        TagData data = new TagData(att);

        //テスト実行
        VariableInfo[] vInfo = tag.getVariableInfo(data);

        //テスト結果確認
        assertEquals(1, vInfo.length);
        assertEquals("1", vInfo[0].getVarName());
        assertEquals("java.lang.String", vInfo[0].getClassName());
        assertTrue(vInfo[0].getDeclare());
        assertEquals(VariableInfo.AT_BEGIN, vInfo[0].getScope());

    }

    /**
     * testGetVariableInfo02()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：FE
     * <br><br>
     * 入力値：(引数) data:new TagData({})<br>
     *         
     * <br>
     * 期待値：(戻り値) VariableInfo[]:空の配列<br>
     *         (状態変化) variableInfoの配列数:0<br>
     *         
     * <br>
     * 引数TagData内に保持されているid属性がNULLで、VariableInfo配列のインスタンスが返却されることを確認するテストケース
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetVariableInfo02() throws Exception {
        //テストデータ設定
        Object[][] att = {};
        TagData data = new TagData(att);

        //テスト実行
        VariableInfo[] vInfo = tag.getVariableInfo(data);

        //テスト結果確認
        assertEquals(0, vInfo.length);

    }

    /**
     * testGetVariableInfo03()
     * <br><br>
     * 
     * (異常系)
     * <br>
     * 観点：G
     * <br><br>
     * 入力値：(引数) data:Null<br>
     *         
     * <br>
     * 期待値：(状態変化) 例外:NullPointerException<br>
     *         
     * <br>
     * 引数TagDataがNULLで、NullPointerExceptionが発生することを確認するテストケース
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetVariableInfo03() throws Exception {
        // テストデータ設定
        TagData data = null;
        // テスト実行
        try {
            tag.getVariableInfo(data);
            fail();
        } catch (NullPointerException e) {
            // テスト結果確認
            return;
        }
    }

}
