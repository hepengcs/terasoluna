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

import java.util.Date;
import java.sql.Timestamp;

import jp.terasoluna.utlib.TagUTUtil;

import junit.framework.TestCase;

/**
 * DateTag ブラックボックステスト。<br>
 * 前提条件<br>
 * なし<br>
 * <br>
 */
public class DateTagTest extends TestCase {

    // テスト対象
    DateTag tag = null;

    /**
     * Constructor for DateTagTest.
     * @param arg0
     */
    public DateTagTest(String arg0) {
        super(arg0);
    }

    /*
     * @see TestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        tag = (DateTag) TagUTUtil.create(DateTag.class);
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
     * 観点：E<br>
     * <br>
     * 入力値<br>
     * date="2004-11-24 10:31:00.000000000"<br>
     * pattern="yyyy.MM.dd"<br>
     * <br>
     * 期待値<br>
     * 戻り値:String="2004.11.24"<br>
     * <br>
     * 引数および、フォーマットパターンが正常な場合のテストケース<br>
     */
    public void testDoFormat01() throws Exception {

        // テストデータ設定
        Timestamp time = Timestamp.valueOf("2004-11-24 10:31:00.000000000");
        Date date = new Date(time.getTime());
        tag.pattern = "yyyy.MM.dd";

        // テスト実行
        String result = tag.doFormat(date);

        // テスト結果確認
        assertEquals("2004.11.24", result);

    } /* testDoFormat01 End */

    /**
     * testDoFormat02。<br>
     * <br>
     * (異常系)<br>
     * 観点：G<br>
     * <br>
     * 入力値<br>
     * date=Null<br>
     * pattern="yyyy.MM.dd"<br>
     * <br>
     * 期待値<br>
     * 戻り値:String=NullPointerException<br>
     * <br>
     * 引数がNullの場合のテストケース<br>
     */
    public void testDoFormat02() throws Exception {

        // テストデータ設定
        tag.pattern = "yyyy.MM.dd";

        // テスト実行
        try{
            tag.doFormat(null);
        }catch(NullPointerException ex){

            // テスト結果確認
            assertEquals(NullPointerException.class.getName(), ex.getClass()
                    .getName());
            return;
        }

        fail();

    } /* testDoFormat02 End */

    /**
     * testDoFormat03。<br>
     * <br>
     * (異常系)<br>
     * 観点：G<br>
     * <br>
     * 入力値<br>
     * date="2004-11-24 10:31:00.000000000"<br>
     * pattern=Null<br>
     * <br>
     * 期待値<br>
     * 戻り値:String=NullPointerException<br>
     * <br>
     * パターンがNullの場合のテストケース<br>
     */
    public void testDoFormat03() {

        // テストデータ設定
        Timestamp time = Timestamp.valueOf("2004-11-24 10:31:00.000000000");
        Date date = new Date(time.getTime());
        tag.pattern = null;

        // テスト実行
        try{
            tag.doFormat(date);
        }catch(NullPointerException ex){

            // テスト結果確認
            assertEquals(NullPointerException.class.getName(), ex.getClass()
                    .getName());
            return;
        }

        fail();

    } /* testDoFormat03 End */

} /* DateTagTest Class End */
