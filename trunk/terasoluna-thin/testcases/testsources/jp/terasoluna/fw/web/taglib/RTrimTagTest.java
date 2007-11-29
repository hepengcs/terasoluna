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

import jp.terasoluna.utlib.TagUTUtil;

import junit.framework.TestCase;

/**
 * RTrimTag ブラックボックステスト。<br>
 * 前提条件<br>
 * なし<br>
 * <br>
 */
public class RTrimTagTest extends TestCase {

    // テスト対象
    RTrimTag tag = null;

    /**
     * Constructor for RTrimTagTest.
     * @param arg0
     */
    public RTrimTagTest(String arg0) {
        super(arg0);
    }

    /*
     * @see TestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        tag = (RTrimTag) TagUTUtil.create(RTrimTag.class);
    }

    /*
     * @see TestCase#tearDown()
     */
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * testDoFormat01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) str:" test test test "<br>
     *         
     * <br>
     * 期待値：(戻り値) String:" test test test"<br>
     *         
     * <br>
     * 引数strがNullではない場合のテストケース
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testDoFormat01() throws Exception {

        // テスト設定
        String str = " test test test ";

        // テスト実行
        String result = tag.doFormat(str);

        // テスト結果確認
        assertEquals(" test test test", result);

    } /* testDoFormat1 End */

    /**
     * testDoFormat02()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) str:Null<br>
     *         
     * <br>
     * 期待値：(戻り値) String:Null<br>
     *         
     * <br>
     * 引数strがNullの場合のテストケース
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testDoFormat02() throws Exception {

        // テスト設定
        String str = null;

        // テスト実行
        String result = tag.doFormat(str);

        // テスト結果確認
        assertNull(result);

    } /* testDoFormat2 End */

    /**
     * testDoFormat03()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) str:" 　test test　test 　"<br>
     *         (状態) なし:true<br>
     *         
     * <br>
     * 期待値：(戻り値) String:" 　test test　test"<br>
     *         
     * <br>
     * 引数strがNullではなく、zenkaku属性がtrueの場合
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testDoFormat03() throws Exception {

        // テスト設定
        String str = " 　test test　test 　";
        tag.setZenkaku(true);

        // テスト実行
        String result = tag.doFormat(str);

        // テスト結果確認
        assertEquals(" 　test test　test", result);

    }

    /**
     * testGetZenkaku01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(状態) zenkaku:false<br>
     *         
     * <br>
     * 期待値：(戻り値) boolean:false<br>
     *         
     * <br>
     * セットしてある値を取得できること
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetZenkaku01() throws Exception {

        // テスト設定
        tag.zenkaku = false;

        //テスト実行
        boolean result = tag.getZenkaku();

        //テスト結果確認
        assertFalse(result);

    }

    /**
     * testSetZenkaku01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) zenkaku:true<br>
     *         (状態) zenkaku:false<br>
     *         
     * <br>
     * 期待値：(状態変化) zenkaku:true<br>
     *         
     * <br>
     * 引数のzenkakuが正しく格納されること
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetZenkaku01() throws Exception {

        // テスト設定
        tag.zenkaku = false;

        //テスト実行
        tag.setZenkaku(true);

        //テスト結果確認
        assertTrue(tag.zenkaku);

    }

} /* RTrimTagTest Class End */
