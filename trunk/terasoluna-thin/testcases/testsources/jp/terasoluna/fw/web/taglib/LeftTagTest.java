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
import jp.terasoluna.utlib.UTUtil;

import junit.framework.TestCase;

/**
 * LeftTag ブラックボックステスト。<br>
 * 前提条件<br>
 * なし<br>
 * <br>
 */
public class LeftTagTest extends TestCase {

    // テスト対象
    LeftTag tag = null;

    /**
     * Constructor for LeftTagTest.
     * @param arg0
     */
    public LeftTagTest(String arg0) {
        super(arg0);
    }

    /*
     * @see TestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        tag = (LeftTag) TagUTUtil.create(LeftTag.class);
    }

    /*
     * @see TestCase#tearDown()
     */
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * testDoFormat01。<br>
     * <br>
     * (正常系)<br>
     * 観点：F<br>
     * <br>
     * 入力値<br>
     * string="Java write once, run anywhere."<br>
     * length=15<br>
     * <br>
     * 期待値<br>
     * 戻り値:String="Java write once"<br>
     * <br>
     * 引数stringがNullではない、かつlengthより
     * 引数stringの長さが長い場合のテストケース<br>
     */
    public void testDoFormat01() throws Exception {

        // テスト設定
        UTUtil.setPrivateField(tag, "length", 15);

        // テスト実行
        String result = tag.doFormat("Java write once, run anywhere.");

        // テスト結果確認
        assertEquals("Java write once", result);

    } /* testDoFormat1 End */

    /**
     * testDoFormat02。<br>
     * <br>
     * (正常系)<br>
     * 観点：F<br>
     * <br>
     * 入力値<br>
     * string="Java write once, run anywhere."<br>
     * length=50<br>
     * <br>
     * 期待値<br>
     * 戻り値:String="Java write once, run anywhere."<br>
     * <br>
     * 引数stringがNullではない、かつlengthより
     * 引数stringの長さが短い場合のテストケース<br>
     */
    public void testDoFormat02() throws Exception {

        // テスト設定
        UTUtil.setPrivateField(tag, "length", 50);

        // テスト実行
        String result = tag.doFormat("Java write once, run anywhere.");

        // テスト結果確認
        assertEquals("Java write once, run anywhere.", result);

    } /* testDoFormat2 End */

    /**
     * testDoFormat03。<br>
     * <br>
     * (正常系)<br>
     * 観点：F<br>
     * <br>
     * 入力値<br>
     * string=null<br>
     * length=10<br>
     * <br>
     * 期待値<br>
     * 戻り値:String=NullPointerException<br>
     * <br>
     * 引数stringがNullの場合のテストケース<br>
     */
    public void testDoFormat03() throws Exception {

        // テスト設定
        UTUtil.setPrivateField(tag, "length", 10);

        // テスト実行
        try {
            tag.doFormat(null);
            fail();
        } catch (NullPointerException ex) {
            // テスト結果確認
            assertEquals(NullPointerException.class.getName(), ex.getClass()
                    .getName());
            return;
        }
    } /* testDoFormat3 End */

    /**
     * testDoFormat04。<br>
     * <br>
     * (正常系)<br>
     * 観点：F<br>
     * <br>
     * 入力値<br>
     * string="Java write once, run anywhere."<br>
     * length=-<br>
     * <br>
     * 期待値<br>
     * 戻り値:String=空白文字列<br>
     * <br>
     * 引数stringがNullではなく、lengthの設定をしなかった場合のテストケース<br>
     */
    public void testDoFormat04() {

        // テスト実行
        String result = tag.doFormat("Java write once, run anywhere.");

        // テスト結果確認
        assertEquals("", result);

    } /* testDoFormat4 End */

    /**
     * testRelease01。<br>
     * 
     * (正常系)<br>
     * 観点：C<br>
     * 
     * 入力値
     * length=24<br>
     * 
     * 期待値
     * 戻り値:void<br>
     * length=0<br>
     * 
     * 前提条件として設定した各値が、
     * 実行時に各条件値が初期化されることを確認する<br>
     */
    public void testRelease01() throws Exception {

        // テストデータ設定
        UTUtil.setPrivateField(tag, "length", 24);

        // テスト実行
        tag.release();

        // テスト結果確認
        assertEquals(new Integer(0), UTUtil.getPrivateField(tag, "length"));

    } /* testRelease1 End */

    /**
     * testSetLength01。<br>
     * 
     * (正常系)<br>
     * 観点：A<br>
     * 
     * 入力値
     * length="99"<br>
     * 
     * 期待値
     * 戻り値:void<br>
     * length="99"<br>
     * 
     * セットした値を確認するテストケース<br>
     */
    public void testSetLength01() throws Exception {
        // テスト実行
        tag.setLength(99);

        // テスト結果確認
        assertEquals(new Integer(99), UTUtil.getPrivateField(tag, "length"));

    } /* testSetLength End */

    /**
     * testGetLength01()。<br>
     * 
     * （正常系）<br>
     * 観点：A<br>
     * <br>
     * 入力値 :なし<br>
     * 期待値 :length=30<br>
     *
     * セットしてある値を取得できることを確認する。<br>
     *
     */
    public void testGetLength01() throws Exception {
        // テストデータ設定
        UTUtil.setPrivateField(tag, "length", 30);

        // テスト実行
        // テスト結果確認
        assertEquals(30, tag.getLength());
    }

} /* LeftTagTest Class End */
