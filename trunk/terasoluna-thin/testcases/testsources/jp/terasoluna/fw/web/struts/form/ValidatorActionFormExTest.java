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

package jp.terasoluna.fw.web.struts.form;

import java.util.List;

import jp.terasoluna.fw.web.struts.reset.Resetter;
import jp.terasoluna.fw.web.struts.reset.ResetterImpl;
import jp.terasoluna.utlib.LogUTUtil;
import jp.terasoluna.utlib.MockHttpServletRequest;
import jp.terasoluna.utlib.MockHttpSession;
import jp.terasoluna.utlib.MockServletContext;
import jp.terasoluna.utlib.UTUtil;
import junit.framework.TestCase;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.config.ModuleConfig;
import org.apache.struts.config.impl.ModuleConfigImpl;

/**
 * {@link jp.terasoluna.fw.web.struts.form.ValidatorActionFormEx}
 * クラスのブラックボックステスト。
 *
 * <p>
 * <h4>【クラスの概要】</h4>
 * Validatorフレームワーク対応アクションフォーム基底クラス。<br>
 * <br>
 * set(String, Object)のテストケースは、異常系一件のみテストを行う。
 * 正常系のテストはset(String, int, Object)にて包含する。<br>
 * <br>
 * 【前提条件】<br>
 * ValidatorActionFormEx_ValidatorActionFormExStub01クラスを
 * 作成し以下のデータを保持する。<br>
 * int hogeInt = 123<br>
 * String hogeString = "data1"<br>
 * int[] hogeIntArray = {-100,0,10,111}<br>
 * String[] hogeStringArray = {"data1","data2","data3","data4"}<br>
 * Object[] hogeObjectArray = {new Integer(1),new Integer(2),new Integer(3),
 * new Integer(4)}<br>
 * List hogeList = {"data1","data2","data3","data4"}<br>
 * Map hogeMap = {key="field1" value="data1"}<br>
 *               {key="field2" value="data2"}<br>
 *               {key="field3" value="data3"}<br>
 *               {key="field4" value="data4"}<br>
 * Runnable hogeRunnable = new Runnable()
 * <p>
 *
 * @see jp.terasoluna.fw.web.struts.form.ValidatorActionFormEx
 */
public class ValidatorActionFormExTest extends TestCase {

    /**
     * テスト対象のインスタンス
     */
    private ValidatorActionFormEx formEx;

    /**
     * このテストケースを実行する為の
     * GUI アプリケーションを起動する。
     *
     * @param args java コマンドに設定されたパラメータ
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(ValidatorActionFormExTest.class);
    }

    /**
     * 初期化処理を行う。
     *
     * @throws Exception このメソッドで発生した例外
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        // create formEx
        this.formEx = new ValidatorActionFormEx_ValidatorActionFormExStub01();
    }

    /**
     * 終了処理を行う。
     *
     * @throws Exception このメソッドで発生した例外
     * @see junit.framework.TestCase#tearDown()
     */
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * コンストラクタ。
     *
     * @param name このテストケースの名前。
     */
    public ValidatorActionFormExTest(String name) {
        super(name);
    }

    /**
     * testGetIndexedValue01()
     * <br><br>
     *
     * (異常系)
     * <br>
     * 観点：G
     * <br><br>
     * 入力値：(引数) fieldName:null<br>
     *
     * <br>
     * 期待値：(状態変化) 例外:NullPointerException<br>
     *                    ＜メッセージ＞<br>
     *                    "No indexed value for 'null[0]'"<br>
     *
     * <br>
     * fieldNameにnullを設定し、NullPointerExceptionがスローされることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetIndexedValue01() throws Exception {

        //テスト実行
        try {
            this.formEx.getIndexedValue(null, 0);
            fail();
        } catch (NullPointerException e) {
            //テスト結果確認
            assertEquals("No indexed value for 'null[0]'",
                    e.getMessage());
        }
    }

    /**
     * testGetIndexedValue02()
     * <br><br>
     *
     * (異常系)
     * <br>
     * 観点：G
     * <br><br>
     * 入力値：(引数) fieldName:空文字<br>
     *
     * <br>
     * 期待値：(状態変化) 例外:NullPointerException<br>
     *                    ＜メッセージ＞<br>
     *                    "No mapped value for '(hello world!)'"<br>
     *
     * <br>
     * fieldNameに空文字を設定し、NullPointerExceptionがスローされることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetIndexedValue02() throws Exception {

        //テスト実行
        try {
            this.formEx.getIndexedValue("", 0);
            fail();
        } catch (NullPointerException e) {
            //テスト結果確認
            assertEquals("No indexed value for '[0]'",
                    e.getMessage());
        }
    }

    /**
     * testGetIndexedValue03()
     * <br><br>
     *
     * (異常系)
     * <br>
     * 観点：G
     * <br><br>
     * 入力値：(引数) fieldName:"foo"<br>
     *                (存在しないフィールド)<br>
     *
     * <br>
     * 期待値：(状態変化) 例外:NullPointerException<br>
     *                    ＜メッセージ＞<br>
     *                    "No indexed value for 'foo[0]'"<br>
     *
     * <br>
     * fieldNmaeに存在しないフィールド名を設定し、
     * NullPointerExceptionがスローされることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetIndexedValue03() throws Exception {

        //テスト実行
        try {
            this.formEx.getIndexedValue("foo", 0);
            fail();
        } catch (NullPointerException e) {
            //テスト結果確認
            assertEquals("No indexed value for 'foo[0]'",
                    e.getMessage());
        }
    }

    /**
     * testGetIndexedValue04()
     * <br><br>
     *
     * (異常系)
     * <br>
     * 観点：G
     * <br><br>
     * 入力値：(引数) fieldName:"hogeString"<br>
     *
     * <br>
     * 期待値：(状態変化) 例外:IllegalArgumentException<br>
     *                    ＜メッセージ＞<br>
     *                    "Non-indexed property for 'hogeString[0]'"<br>
     *
     * <br>
     * fieldNameに配列でもList無いフィールド名を設定し、
     * IllegalArgumentExceptionがスローされることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetIndexedValue04() throws Exception {

        //テスト実行
        try {
            this.formEx.getIndexedValue("hogeString", 0);
            fail();
        } catch (IllegalArgumentException e) {
            //テスト結果確認
            assertEquals("Non-indexed property for 'hogeString[0]'",
                    e.getMessage());
        }
    }

    /**
     * testGetIndexedValue05()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) fieldName:"hogeStringArray"<br>
     *         (引数) index:-1<br>
     *
     * <br>
     * 期待値：(戻り値) fieldNameで指定されたフィールド:null<br>
     *
     * <br>
     * fieldNameにString配列型のフィールドを設定し、かつIndexに"-1"を設定し、
     * "null"が返る事を確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetIndexedValue05() throws Exception {

        //テスト実行
        Object object = this.formEx.getIndexedValue("hogeStringArray", -1);

        //テスト結果確認
        assertNull(object);
    }

    /**
     * testGetIndexedValue06()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) fieldName:"hogeStringArray"<br>
     *         (引数) index:0<br>
     *
     * <br>
     * 期待値：(戻り値) fieldNameで指定されたフィールド:"data1"<br>
     *
     * <br>
     * fieldNameにString配列型のフィールドを設定し、かつIndexに"0"を設定し、
     * 値が返る事を確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetIndexedValue06() throws Exception {

        //テスト実行
        Object object = this.formEx.getIndexedValue("hogeStringArray", 0);

        //テスト結果確認
        assertEquals("data1", object);
    }

    /**
     * testGetIndexedValue07()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) fieldName:"hogeStringArray"<br>
     *         (引数) index:10<br>
     *
     * <br>
     * 期待値：(戻り値) fieldNameで指定されたフィールド:null<br>
     *
     * <br>
     * fieldNameにString配列型のフィールドを設定し、かつIndexに"10"を設定し、
     * "null"が返る事を確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetIndexedValue07() throws Exception {

        //テスト実行
        Object object = this.formEx.getIndexedValue("hogeStringArray", 10);

        //テスト結果確認
        assertNull(object);
    }

    /**
     * testGetIndexedValue08()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) fieldName:"hogeList"<br>
     *         (引数) index:-1<br>
     *
     * <br>
     * 期待値：(戻り値) fieldNameで指定されたフィールド:null<br>
     *
     * <br>
     * fieldNameにList型のフィールドを設定し、かつIndexに"-1"を設定し、
     * "null"が返る事を確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetIndexedValue08() throws Exception {

        //テスト実行
        Object object = this.formEx.getIndexedValue("hogeList", -1);

        //テスト結果確認
        assertNull(object);
    }

    /**
     * testGetIndexedValue09()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) fieldName:"hogeList"<br>
     *         (引数) index:0<br>
     *
     * <br>
     * 期待値：(戻り値) fieldNameで指定されたフィールド:"data1"<br>
     *
     * <br>
     * fieldNameにList型のフィールドを設定し、かつIndexに"0"を設定し、
     * 値が返る事を確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetIndexedValue09() throws Exception {

        //テスト実行
        Object object = this.formEx.getIndexedValue("hogeList", 0);

        //テスト結果確認
        assertEquals("data1", object);
    }

    /**
     * testGetIndexedValue10()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) fieldName:"hogeList"<br>
     *         (引数) index:10<br>
     *
     * <br>
     * 期待値：(戻り値) fieldNameで指定されたフィールド:null<br>
     *
     * <br>
     * fieldNameにList型のフィールドを設定し、かつIndexに"10"を設定し、
     * "null"が返る事を確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetIndexedValue10() throws Exception {

        //テスト実行
        Object object = this.formEx.getIndexedValue("hogeList", 10);

        //テスト結果確認
        assertNull(object);
    }

    /**
     * testGetIndexedValue11()
     * <br><br>
     *
     * (異常系)
     * <br>
     * 観点：G
     * <br><br>
     * 入力値：(引数) fieldName:"hogeInt"<br>
     *
     * <br>
     * 期待値：(状態変化) 例外:IllegalArgumentException<br>
     *                    ＜メッセージ＞<br>
     *                    "Non-indexed property for 'hogeInt[0]'"<br>
     *
     * <br>
     * fieldNameにint型のフィールド名を設定し、IllegalArgumentExceptionが
     * スローされることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetIndexedValue11() throws Exception {

        //テスト実行
        try {
            this.formEx.getIndexedValue("hogeInt", 0);
            fail();
        } catch (IllegalArgumentException e) {
            //テスト結果確認
            assertEquals("Non-indexed property for 'hogeInt[0]'",
                    e.getMessage());
        }
    }

    /**
     * testGetIndexedValue12()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) fieldName:"hogeIntArray"<br>
     *         (引数) index:-1<br>
     *
     * <br>
     * 期待値：(戻り値) fieldNameで指定されたフィールド:null<br>
     *
     * <br>
     * fieldNameにint[]型のフィールドを設定し、かつIndexに"-1"を設定し、
     * "null"が返る事を確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetIndexedValue12() throws Exception {

        //テスト実行
        Object object = this.formEx.getIndexedValue("hogeIntArray", -1);

        //テスト結果確認
        assertNull(object);
    }

    /**
     * testGetIndexedValue13()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) fieldName:"hogeIntArray"<br>
     *         (引数) index:0<br>
     *
     * <br>
     * 期待値：(戻り値) fieldNameで指定されたフィールド:Integer(-100)<br>
     *
     * <br>
     * fieldNameにint[]型のフィールドを設定し、かつIndexに"0"を設定し、
     * 値が返る事を確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetIndexedValue13() throws Exception {

        //テスト実行
        Object object = this.formEx.getIndexedValue("hogeIntArray", 0);

        //テスト結果確認
        assertEquals(new Integer(-100), object);
    }

    /**
     * testGetIndexedValue14()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) fieldName:"hogeIntArray"<br>
     *         (引数) index:10<br>
     *
     * <br>
     * 期待値：(戻り値) fieldNameで指定されたフィールド:null<br>
     *
     * <br>
     * fieldNameにint[]型のフィールドを設定し、かつIndexに"10"を設定し、
     * "null"が返る事を確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetIndexedValue14() throws Exception {

        //テスト実行
        Object object = this.formEx.getIndexedValue("hogeIntArray", 10);

        //テスト結果確認
        assertNull(object);
    }

    /**
     * testGetIndexCount01()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) fieldName:null<br>
     *
     * <br>
     * 期待値：(戻り値) fieldNameで指定されたフィールド:0<br>
     *
     * <br>
     * fieldNameに"null"を設定し、"0"が返る事を確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetIndexCount01() throws Exception {

        //テスト実行
        int i = this.formEx.getIndexCount(null);

        //テスト結果確認
        assertEquals(0, i);
    }

    /**
     * testGetIndexCount02()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) fieldName:空文字<br>
     *
     * <br>
     * 期待値：(戻り値) fieldNameで指定されたフィールド:0<br>
     *
     * <br>
     * fieldNameに空文字を設定し、"0"が返る事を確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetIndexCount02() throws Exception {

        //テスト実行
        int i = this.formEx.getIndexCount("");

        //テスト結果確認
        assertEquals(0, i);
    }

    /**
     * testGetIndexCount03()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) fieldName:"foo"(存在しないフィールド)<br>
     *
     * <br>
     * 期待値：(戻り値) fieldNameで指定されたフィールド:0<br>
     *
     * <br>
     * fieldNmaeに存在しないフィールド名を設定し、"0"が返る事を確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetIndexCount03() throws Exception {

        //テスト実行
        int i = this.formEx.getIndexCount("foo");

        //テスト結果確認
        assertEquals(0, i);
    }

    /**
     * testGetIndexCount04()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) fieldName:"hogeString"<br>
     *
     * <br>
     * 期待値：(戻り値) fieldNameで指定されたフィールド:1<br>
     *
     * <br>
     * fieldNameにString型フィールド名を設定し、正しい値が返る事を確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetIndexCount04() throws Exception {

        //テスト実行
        int i = this.formEx.getIndexCount("hogeString");

        //テスト結果確認
        assertEquals(1, i);
    }

    /**
     * testGetIndexCount05()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) fieldName:"hogeStringArray"<br>
     *
     * <br>
     * 期待値：(戻り値) fieldNameで指定されたフィールド:4<br>
     *
     * <br>
     * fieldNameにString[]型のフィールドを設定し、正しい値が返る事を確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetIndexCount05() throws Exception {

        //テスト実行
        int i = this.formEx.getIndexCount("hogeStringArray");

        //テスト結果確認
        assertEquals(4, i);
    }

    /**
     * testGetIndexCount06()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) fieldName:"hogeList"<br>
     *
     * <br>
     * 期待値：(戻り値) fieldNameで指定されたフィールド:4<br>
     *
     * <br>
     * fieldNameにList型のフィールドを設定し、正しい値が返る事を確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetIndexCount06() throws Exception {

        //テスト実行
        int i = this.formEx.getIndexCount("hogeList");

        //テスト結果確認
        assertEquals(4, i);
    }

    /**
     * testGetIndexCount07()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) fieldName:"hogeMap"<br>
     *
     * <br>
     * 期待値：(戻り値) fieldNameで指定されたフィールド:4<br>
     *
     * <br>
     * fieldNameにMap型のフィールドを設定し、正しい値が返る事を確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetIndexCount07() throws Exception {

        //テスト実行
        int i = this.formEx.getIndexCount("hogeMap");

        //テスト結果確認
        assertEquals(4, i);
    }

    /**
     * testSetStringintObject01()
     * <br><br>
     *
     * (異常系)
     * <br>
     * 観点：G
     * <br><br>
     * 入力値：(引数) name:null<br>
     *
     * <br>
     * 期待値：(状態変化) 例外:NullPointerException<br>
     *                    ＜メッセージ＞<br>
     *                    "No indexed value for 'null[0]'"<br>
     *
     * <br>
     * nameに"null"を設定し、NullPointerExceptionがスローされることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetIndexedValue01() throws Exception {

        //テスト実行
        try {
            this.formEx.setIndexedValue(null, 0, "hello");
            fail();
        } catch (NullPointerException e) {
            //テスト結果確認
            assertEquals("No indexed value for 'null[0]'",
                    e.getMessage());
        }
    }

    /**
     * testSetIndexedValue02()
     * <br><br>
     *
     * (異常系)
     * <br>
     * 観点：G
     * <br><br>
     * 入力値：(引数) name:空文字<br>
     *
     * <br>
     * 期待値：(状態変化) 例外:NullPointerException<br>
     *                    ＜メッセージ＞<br>
     *                    "No indexed value for '[0]'"<br>
     *
     * <br>
     * nameに空文字を設定し、NullPointerExceptionがスローされることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetIndexedValue02() throws Exception {

        //テスト実行
        try {
            this.formEx.setIndexedValue("", 0, "hello");
            fail();
        } catch (NullPointerException e) {
            //テスト結果確認
            assertEquals("No indexed value for '[0]'",
                    e.getMessage());
        }
    }

    /**
     * testSetIndexedValue03()
     * <br><br>
     *
     * (異常系)
     * <br>
     * 観点：G
     * <br><br>
     * 入力値：(引数) name:"foo"<br>
     *
     * <br>
     * 期待値：(状態変化) 例外:NullPointerException<br>
     *                    ＜メッセージ＞<br>
     *                    "No indexed value for 'foo[0]'"<br>
     *
     * <br>
     * nameに存在しないフィールド名を設定し、
     * NullPointerExceptionがスローされることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetIndexedValue03() throws Exception {

        //テスト実行
        try {
            this.formEx.setIndexedValue("foo", 0, "hello");
            fail();
        } catch (NullPointerException e) {
            //テスト結果確認
            assertEquals("No indexed value for 'foo[0]'",
                    e.getMessage());
        }
    }

    /**
     * testSetIndexedValue04()
     * <br><br>
     *
     * (異常系)
     * <br>
     * 観点：G
     * <br><br>
     * 入力値：(引数) name:"hogeString"<br>
     *
     * <br>
     * 期待値：(状態変化) 例外:IllegalArgumentException<br>
     *                    ＜メッセージ＞<br>
     *                    "Non-indexed property for 'hogeString[0]'"<br>
     *
     * <br>
     * nameにString型のフィールド名を設定し、
     * IllegalArgumentExceptionがスローされることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetIndexedValue04() throws Exception {

        //テスト実行
        try {
            this.formEx.setIndexedValue("hogeString", 0, "hello");
            fail();
        } catch (IllegalArgumentException e) {
            //テスト結果確認
            assertEquals("Non-indexed property for 'hogeString[0]'",
                    e.getMessage());
        }
    }

    /**
     * testSetIndexedValue05()
     * <br><br>
     *
     * (異常系)
     * <br>
     * 観点：G
     * <br><br>
     * 入力値：(引数) name:"hogeStringArray"<br>
     *         (引数) index:-1<br>
     *
     * <br>
     * 期待値：(状態変化) 例外:ArrayIndexOutOfBoundsException<br>
     *
     * <br>
     * nameにString[]型のフィールド名を設定し、かつIndexに"-1"を設定し、
     * ArrayIndexOutOfBoundsExceptionがスローされることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetIndexedValue05() throws Exception {

        //テスト実行
        try {
            this.formEx.setIndexedValue("hogeStringArray", -1, "hello");
            fail();
        } catch (ArrayIndexOutOfBoundsException e) {
        }
    }

    /**
     * testSetIndexedValue06()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) name:"hogeStringArray"<br>
     *         (引数) index:0<br>
     *         (引数) value:null<br>
     *
     * <br>
     * 期待値：(状態変化) nameで指定されたフィールド:Index="0"にvalueが格納される<br>
     *
     * <br>
     * nameにString[]型のフィールド名を設定し、かつIndexに"0"を設定し、
     * 添字"0"に引数にとった"null"が格納されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetIndexedValue06() throws Exception {

        //テスト実行
        this.formEx.setIndexedValue("hogeStringArray", 0, null);

        //テスト結果確認
        String[] str = (String[]) UTUtil.getPrivateField(
                formEx, "hogeStringArray");
        assertNull(str[0]);
    }

    /**
     * testSetIndexedValue07()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) name:"hogeStringArray"<br>
     *         (引数) index:1<br>
     *         (引数) value:"hello"<br>
     *
     * <br>
     * 期待値：(状態変化) nameで指定されたフィールド:
     *                      Index="1"にvalueが格納される<br>
     *
     * <br>
     * nameにString[]型のフィールド名を設定し、かつIndexに"1"を設定し、
     * 添字"1"に引数にとった"hello"が格納されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetIndexedValue07() throws Exception {

        //テスト実行
        this.formEx.setIndexedValue("hogeStringArray", 1, "hello");

        //テスト結果確認
        String[] str = (String[]) UTUtil.getPrivateField(
                formEx, "hogeStringArray");
        assertEquals("hello", str[1]);
    }

    /**
     * testSetIndexedValue08()
     * <br><br>
     *
     * (異常系)
     * <br>
     * 観点：G
     * <br><br>
     * 入力値：(引数) name:"hogeStringArray"<br>
     *         (引数) index:2<br>
     *         (引数) value:Integer(5)<br>
     *
     * <br>
     * 期待値：(状態変化) 例外:IllegalArgumentException<br>
     *
     * <br>
     * nameにString[]型のフィールド名を設定し、かつIndexに"2"を設定し、
     * IllegalArgumentExceptionがスローされることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetIndexedValue08() throws Exception {

        //テスト実行
        try {
            this.formEx.setIndexedValue("hogeStringArray", 2, new Integer(5));
            fail();
        } catch (IllegalArgumentException e) {
            //テスト結果確認
        }
    }

    /**
     * testSetIndexedValue09()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) name:"hogeStringArray"<br>
     *         (引数) index:10<br>
     *         (引数) value:null<br>
     *
     * <br>
     * 期待値：(状態変化) nameで指定されたフィールド:
     *                         Index="10"にvalueが格納される<br>
     *
     * <br>
     * nameにサイズ"4"のString[]型のフィールド名を設定し、かつIndexに"10"を設定し、
     * 添字"10"に引数にとった"null"が格納されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetIndexedValue09() throws Exception {

        //テスト実行
        this.formEx.setIndexedValue("hogeStringArray", 10, null);

        //テスト結果確認
        String[] str = (String[]) UTUtil.getPrivateField(
                formEx, "hogeStringArray");
        assertNull(str[10]);
    }

    /**
     * testSetIndexedValue10()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) name:"hogeStringArray"<br>
     *         (引数) index:11<br>
     *         (引数) value:"hello"<br>
     *
     * <br>
     * 期待値：(状態変化) nameで指定されたフィールド:
     *                        Index="11"にvalueが格納される<br>
     *
     * <br>
     * nameにサイズ"4"のString[]型のフィールド名を設定し、かつIndexに"11"を設定し、
     * 添字"11"に引数にとった"hello"が格納されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetIndexedValue10() throws Exception {

        //テスト実行
        this.formEx.setIndexedValue("hogeStringArray", 11, "hello");

        //テスト結果確認
        String[] str = (String[]) UTUtil.getPrivateField(
                formEx, "hogeStringArray");
        assertEquals("hello", str[11]);
    }

    /**
     * testSetIndexedValue11()
     * <br><br>
     *
     * (異常系)
     * <br>
     * 観点：G
     * <br><br>
     * 入力値：(引数) name:"hogeStringArray"<br>
     *         (引数) index:12<br>
     *         (引数) value:Integer(5)<br>
     *
     * <br>
     * 期待値：(状態変化) 例外:IllegalArgumentException<br>
     *
     * <br>
     * nameにサイズ"4"のString[]型のフィールド名を設定し、かつIndexに"12"を設定し、
     * IllegalArgumentExceptionがスローされることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetIndexedValue11() throws Exception {

        //テスト実行
        try {
            this.formEx.setIndexedValue("hogeStringArray", 12, new Integer(5));
            fail();
        } catch (IllegalArgumentException e) {
            //テスト結果確認
        }
    }

    /**
     * testSetIndexedValue12()
     * <br><br>
     *
     * (異常系)
     * <br>
     * 観点：G
     * <br><br>
     * 入力値：(引数) name:"hogeList"<br>
     *         (引数) index:-1<br>
     *
     * <br>
     * 期待値：(状態変化) 例外:IndexOutOfBoundsException<br>
     *
     * <br>
     * nameにList型のフィールド名を設定し、かつIndexに"-1"を設定し、
     * IndexOutOfBoundsExceptionがスローされることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetIndexedValue12() throws Exception {

        //テスト実行
        try {
            this.formEx.setIndexedValue("hogeList", -1, "hello");
            fail();
        } catch (IndexOutOfBoundsException e) {
            //テスト結果確認
        }
    }

    /**
     * testSetIndexedValue13()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) name:"hogeList"<br>
     *         (引数) index:0<br>
     *         (引数) value:null<br>
     *
     * <br>
     * 期待値：(状態変化) nameで指定されたフィールド:
     *                          Index="0"にvalueが格納される<br>
     *
     * <br>
     * nameにList型のフィールド名を設定し、かつIndexに"0"を設定し、
     * 添字"0"に引数にとった"null"が格納されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetIndexedValue13() throws Exception {

        //テスト実行
        this.formEx.setIndexedValue("hogeList", 0, null);

        //テスト結果確認
        List list = (List) UTUtil.getPrivateField(formEx, "hogeList");
        assertNull(list.get(0));
    }

    /**
     * testSetIndexedValue14()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) name:"hogeList"<br>
     *         (引数) index:1<br>
     *         (引数) value:"hello"<br>
     *
     * <br>
     * 期待値：(状態変化) nameで指定されたフィールド:
     *                         Index="1"にvalueが格納される<br>
     *
     * <br>
     * nameにList型のフィールド名を設定し、かつIndexに"1"を設定し、
     * 添字"1"に引数にとった"hello"が格納されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetIndexedValue14() throws Exception {

        //テスト実行
        this.formEx.setIndexedValue("hogeList", 1, "hello");

        //テスト結果確認
        List list = (List) UTUtil.getPrivateField(formEx, "hogeList");
        assertEquals("hello", list.get(1));
    }

    /**
     * testSetIndexedValue15()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) name:"hogeList"<br>
     *         (引数) index:2<br>
     *         (引数) value:Integer(5)<br>
     *
     * <br>
     * 期待値：(状態変化) nameで指定されたフィールド:
     *                            Index="2"にvalueが格納される<br>
     *
     * <br>
     * nameにList型のフィールド名を設定し、かつIndexに"2"を設定し、
     * 添字"2"に引数にとったIntegerが格納されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetIndexedValue15() throws Exception {

        //テスト実行
        this.formEx.setIndexedValue("hogeList", 2, new Integer(5));

        //テスト結果確認
        List list = (List) UTUtil.getPrivateField(formEx, "hogeList");
        assertEquals(5, ((Integer)list.get(2)).intValue());
    }

    /**
     * testSetIndexedValue16()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) name:"hogeList"<br>
     *         (引数) index:10<br>
     *         (引数) value:null<br>
     *
     * <br>
     * 期待値：(状態変化) nameで指定されたフィールド:
     *                         Index="10"にvalueが格納される<br>
     *
     * <br>
     * nameにサイズ"4"のList型のフィールド名を設定し、かつIndexに"10"を設定し、
     * 添字"10"に引数にとった"null"が格納されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetIndexedValue16() throws Exception {

        //テスト実行
        this.formEx.setIndexedValue("hogeList", 10, null);

        //テスト結果確認
        List list = (List) UTUtil.getPrivateField(formEx, "hogeList");
        assertNull(list.get(10));
    }

    /**
     * testSetIndexedValue17()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) name:"hogeList"<br>
     *         (引数) index:11<br>
     *         (引数) value:"hello"<br>
     *
     * <br>
     * 期待値：(状態変化) nameで指定されたフィールド:
     *                         Index="11"にvalueが格納される<br>
     *
     * <br>
     * nameにサイズ"4"のList型のフィールド名を設定し、かつIndexに"11"を設定し、
     * 添字"11"に引数にとった"helloEが格納されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetIndexedValue17() throws Exception {

        //テスト実行
        this.formEx.setIndexedValue("hogeList", 11, "hello");

        //テスト結果確認
        List list = (List) UTUtil.getPrivateField(formEx, "hogeList");
        assertEquals("hello", list.get(11));
    }

    /**
     * testSetIndexedValue18()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) name:"hogeList"<br>
     *         (引数) index:12<br>
     *         (引数) value:Integer(5)<br>
     *
     * <br>
     * 期待値：(状態変化) nameで指定されたフィールド:
     *                          Index="12"にvalueが格納される<br>
     *
     * <br>
     * nameにサイズ"4"のList型のフィールド名を設定し、かつIndexに"12"を設定し、
     * 添字"12"に引数にとったIntegerが格納されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetIndexedValue18() throws Exception {

        //テスト実行
        this.formEx.setIndexedValue("hogeList", 12, new Integer(5));

        //テスト結果確認
        List list = (List) UTUtil.getPrivateField(formEx, "hogeList");
        assertEquals(5, ((Integer)list.get(12)).intValue());
    }

    /**
     * testSetIndexedValue19()
     * <br><br>
     *
     * (異常系)
     * <br>
     * 観点：G
     * <br><br>
     * 入力値：(引数) name:"hogeInt"<br>
     *
     * <br>
     * 期待値：(状態変化) 例外:IllegalArgumentException<br>
     *                    ＜メッセージ＞<br>
     *                    "Non-indexed property for 'hogeInt[2]'"<br>
     *
     * <br>
     * nameにint型のフィールド名を設定し、IllegalArgumentExceptionが
     * スローされることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetIndexedValue19() throws Exception {

        //テスト実行
        try {
            this.formEx.setIndexedValue("hogeInt", 2, new Integer(5));
            fail();
        } catch (IllegalArgumentException e) {
            //テスト結果確認
            assertEquals("Non-indexed property for 'hogeInt[2]'",
                    e.getMessage());
        }
    }

    /**
     * testSetIndexedValue20()
     * <br><br>
     *
     * (異常系)
     * <br>
     * 観点：G
     * <br><br>
     * 入力値：(引数) name:"hogeIntArray"<br>
     *         (引数) index:-1<br>
     *
     * <br>
     * 期待値：(状態変化) 例外:ArrayIndexOutOfBoundsException<br>
     *
     * <br>
     * nameにint[]型のフィールド名を設定し、ArrayIndexOutOfBoundsExceptionが
     * スローされることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetIndexedValue20() throws Exception {

        //テスト実行
        try {
            this.formEx.setIndexedValue("hogeIntArray", -1, "hello");
            fail();
        } catch (ArrayIndexOutOfBoundsException e) {
            //テスト結果確認
        }
    }

    /**
     * testSetIndexedValue21()
     * <br><br>
     *
     * (異常系)
     * <br>
     * 観点：G
     * <br><br>
     * 入力値：(引数) name:"hogeIntArray"<br>
     *         (引数) index:0<br>
     *         (引数) value:null<br>
     *
     * <br>
     * 期待値：(状態変化) 例外:IllegalArgumentException<br>
     *
     * <br>
     * nameにint[]型のフィールド名を設定し、IllegalArgumentExceptionが
     * スローされることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetIndexedValue21() throws Exception {
        try {
            //テスト実行
            this.formEx.setIndexedValue("hogeIntArray", 0, null);
            fail();
        } catch (IllegalArgumentException e) {
            //テスト結果確認
        }
    }

    /**
     * testSetIndexedValue22()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) name:"hogeIntArray"<br>
     *         (引数) index:1<br>
     *         (引数) value:Integer(5)<br>
     *
     * <br>
     * 期待値：(状態変化) nameで指定されたフィールド:
     *                       Index="1"にvalueが格納される<br>
     *
     * <br>
     * nameにint[]型のフィールド名を設定し、かつIndexに"1"を設定し、
     * 添字"1"にvalueが格納されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetIndexedValue22() throws Exception {

        //テスト実行
        this.formEx.setIndexedValue("hogeIntArray", 1, new Integer(5));

        //テスト結果確認
        int[] in = (int[]) UTUtil.getPrivateField(formEx, "hogeIntArray");
        assertEquals(5, in[1]);
    }

    /**
     * testSetIndexedValue23()
     * <br><br>
     *
     * (異常系)
     * <br>
     * 観点：G
     * <br><br>
     * 入力値：(引数) name:"hogeIntArray"<br>
     *         (引数) index:2<br>
     *         (引数) value:"hello"<br>
     *
     * <br>
     * 期待値：(状態変化) 例外:IllegalArgumentException<br>
     *
     * <br>
     * nameにint[]型のフィールド名を設定し、かつIndexに"2"を設定し、
     * IllegalArgumentExceptionがスローされることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetIndexedValue23() throws Exception {

        //テスト実行
        try {
            this.formEx.setIndexedValue("hogeIntArray", 2, "hello");
            fail();
        } catch (IllegalArgumentException e) {
            //テスト結果確認
        }
    }

    /**
     * testSetIndexedValue24()
     * <br><br>
     *
     * (異常系)
     * <br>
     * 観点：G
     * <br><br>
     * 入力値：(引数) name:"hogeIntArray"<br>
     *         (引数) index:10<br>
     *         (引数) value:null<br>
     *
     * <br>
     * 期待値：(状態変化) 例外:IllegalArgumentException<br>
     *
     * <br>
     * nameにサイズ"4"のint[]型のフィールド名を設定し、
     * かつIndexに"10"を設定し、NullPointerExceptionがスローされることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetIndexedValue24() throws Exception {

        //テスト実行
        try {
            this.formEx.setIndexedValue("hogeIntArray", 10, null);
            fail();
        } catch (IllegalArgumentException e) {
            //テスト結果確認
        }
    }

    /**
     * testSetIndexedValue25()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) name:"hogeIntArray"<br>
     *         (引数) index:11<br>
     *         (引数) value:Integer(5)<br>
     *
     * <br>
     * 期待値：(状態変化) nameで指定されたフィールド:Index="11"に
     *                   valueが格納される<br>
     *
     * <br>
     * nameにサイズ"4"のint[]型のフィールド名を設定し、
     * かつIndexに"11"を設定し、valueが格納されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetIndexedValue25() throws Exception {

        //テスト実行
        this.formEx.setIndexedValue("hogeIntArray", 11, new Integer(5));

        //テスト結果確認
        int[] in = (int[]) UTUtil.getPrivateField(formEx, "hogeIntArray");
        assertEquals(5, in[11]);
    }

    /**
     * testSetIndexedValue26()
     * <br><br>
     *
     * (異常系)
     * <br>
     * 観点：G
     * <br><br>
     * 入力値：(引数) name:"hogeIntArray"<br>
     *         (引数) index:12<br>
     *         (引数) value:"hello"<br>
     *
     * <br>
     * 期待値：(状態変化) 例外:IllegalArgumentException<br>
     *
     * <br>
     * nameにサイズ"4"のint[]型のフィールド名を設定し、かつIndexに"12"を設定し、
     * IllegalArgumentExceptionがスローされることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetIndexedValue26() throws Exception {

        //テスト実行
        try {
            this.formEx.setIndexedValue("hogeIntArray", 12, "hello");
            fail();
        } catch (IllegalArgumentException e) {
            //テスト結果確認
        }
    }

    /**
     * testSetIndexedValue27()
     * <br><br>
     *
     * (異常系)
     * <br>
     * 観点：G
     * <br><br>
     * 入力値：(引数) name:"stringValue"<br>
     *         (引数) index:0<br>
     *         (引数) value:"hello"<br>
     * 前提条件：(ValidatorActionFormExサブクラス)
     *            setStringValuesで例外が発生する。
     *
     * <br>
     * 期待値：(状態変化) 例外:IllegalArgumentException<br>
     *                         メッセージ：Cannot set property for 'stringValues[0]'<br>
     *
     * <br>
     * 値の設定時に例外が発生する場合、IllegalArgumentExceptionが発生することを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetIndexedValue27() throws Exception {

        ValidatorActionFormEx test =
            new ValidatorActionFormEx_ValidatorActionFormExStub02();

        //テスト実行
        try {
            test.setIndexedValue("stringValues", 0, "hello");
            fail();
        } catch (IllegalArgumentException e) {
            //テスト結果確認
            assertEquals("Cannot set property for 'stringValues[0]'",
                    e.getMessage());
        }
    }

    /**
     * testSetIndexedValue28()
     * <br><br>
     *
     * (異常系)
     * <br>
     * 観点：G
     * <br><br>
     * 入力値：(引数) name:"valueList"<br>
     *         (引数) index:0<br>
     *         (引数) value:"hello"<br>
     * 前提条件：(ValidatorActionFormExサブクラス)
     *            setValueListで例外が発生する。
     *
     * <br>
     * 期待値：(状態変化) 例外:IllegalArgumentException<br>
     *                         メッセージ：Cannot set property for 'valueList[0]'<br>
     *
     * <br>
     * 値の設定時に例外が発生する場合、IllegalArgumentExceptionが発生することを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetIndexedValue28() throws Exception {

        ValidatorActionFormEx test =
            new ValidatorActionFormEx_ValidatorActionFormExStub02();

        //テスト実行
        try {
            test.setIndexedValue("valueList", 0, "hello");
            fail();
        } catch (IllegalArgumentException e) {
            //テスト結果確認
            assertEquals("Cannot set property for 'valueList[0]'",
                    e.getMessage());
        }
    }

    /**
     * testIsModified01()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(状態) modified:true<br>
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
    public void testIsModified01() throws Exception {
        // 前処理
        UTUtil.setPrivateField(formEx, "modified", true);

        // テスト実施
        boolean result = formEx.isModified();

        // 判定
        assertTrue(result);
    }

    /**
     * testSetModified01()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) modified:true<br>
     *         (状態) modified:false<br>
     *
     * <br>
     * 期待値：(状態変化) modified:true<br>
     *
     * <br>
     * ※正常系一件のみテスト
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetModified01() throws Exception {
        // テストデータ設定
        boolean modifiedFlag = true;

        // テスト実行
        formEx.setModified(modifiedFlag);

        // テスト結果
        // 設定したActionMessageと同一であること。
        Boolean result = (Boolean) UTUtil.getPrivateField(formEx, "modified");
        assertEquals(modifiedFlag, result.booleanValue());
    }

    /**
     * testReset01()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) mapping:not null<br>
     *         (引数) request:
     *                  デフォルトモジュールを格納したリクエストオブジェクト<br>
     *         (状態) resetter:null<br>
     *
     * <br>
     * 期待値：(状態変化) ResetterImpl#reset()の呼び出し:-<br>
     *
     * <br>
     * デフォルトモジュールを格納したリクエストオブジェクトを設定し、
     * Resetterが"null"だった場合、何もせずに終了することを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testReset01() throws Exception {

        // リクエストの生成
        MockHttpServletRequest request = new MockHttpServletRequest();
        // セッションの生成
        MockHttpSession session = new MockHttpSession();
        // コンテキストの生成
        MockServletContext context = new MockServletContext();
        // アクションサーブレットの生成
        ValidatorActionFormEx_ActionServlet_Stub01 servlet =
            new ValidatorActionFormEx_ActionServlet_Stub01();
        // プレフィックスの生成
        String prefix = "";
        // モジュール設定情報の生成
        ModuleConfig moduleConfig = new ModuleConfigImpl(prefix);
        // マッピング情報の生成
        ActionMapping mapping = new ActionMapping();

        // 設定
        request.setSession(session);
        request.setAttribute(Globals.MODULE_KEY, moduleConfig);
        session.setServletContext(context);
        context.setAttribute(Resetter.RESETTER_KEY + prefix, null);
        context.setAttribute(Globals.MODULE_KEY, moduleConfig);
        this.formEx.setServlet(servlet);
        servlet.setContext(context);

        //テスト実行
        this.formEx.reset(mapping, request);
    }

    /**
     * testReset02()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) mapping:not null<br>
     *         (引数) request:
     *                  デフォルトモジュールを格納したリクエストオブジェクト<br>
     *         (状態) resetter:Resetter以外のオブジェクト<br>
     *
     * <br>
     * 期待値：(状態変化) ResetterImpl#reset()の呼び出し:-<br>
     *                   ログ：＜ログ＞<br>
     *                         エラーログ：""<br>
     *                         ＜例外＞<br>
     *                         ClassCastException<br>
     *
     * <br>
     * デフォルトモジュールを格納したリクエストオブジェクトを設定し、
     * Resetter以外のオブジェクトを取得した場合、ClassCastExceptionが発生して、
     * ログを出力して、終了することを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testReset02() throws Exception {

        // リクエストの生成
        MockHttpServletRequest request = new MockHttpServletRequest();
        // セッションの生成
        MockHttpSession session = new MockHttpSession();
        // コンテキストの生成
        MockServletContext context = new MockServletContext();
        // アクションサーブレットの生成
        ValidatorActionFormEx_ActionServlet_Stub01 servlet =
            new ValidatorActionFormEx_ActionServlet_Stub01();
        // プレフィックスの生成
        String prefix = "";
        // モジュール設定情報の生成
        ModuleConfig moduleConfig = new ModuleConfigImpl(prefix);
        // マッピング情報の生成
        ActionMapping mapping = new ActionMapping();

        // 設定
        request.setSession(session);
        request.setAttribute(Globals.MODULE_KEY, moduleConfig);
        session.setServletContext(context);
        context.setAttribute(Resetter.RESETTER_KEY + prefix, new Object());
        context.setAttribute(Globals.MODULE_KEY, moduleConfig);
        this.formEx.setServlet(servlet);
        servlet.setContext(context);

        //テスト実行
        this.formEx.reset(mapping, request);

        //テスト結果確認
        assertTrue(
                LogUTUtil.checkError("",new ClassCastException()));
    }

    /**
     * testReset03()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) mapping:not null<br>
     *         (引数) request:
     *                   デフォルトモジュールを格納したリクエストオブジェクト<br>
     *         (状態) resetter:Resetterオブジェクト<br>
     *
     * <br>
     * 期待値：(状態変化) ResetterImpl#reset()の呼び出し:呼び出される<br>
     *                    requestに"SUCCESS"がキーの値が設定されることを確認。
     *
     * <br>
     * デフォルトモジュールを格納したリクエストオブジェクトを設定し、
     * Resetterが、フィールドを初期化することを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testReset03() throws Exception {

        // リクエストの生成
        MockHttpServletRequest request = new MockHttpServletRequest();
        // セッションの生成
        MockHttpSession session = new MockHttpSession();
        // コンテキストの生成
        MockServletContext context = new MockServletContext();
        // アクションサーブレットの生成
        ValidatorActionFormEx_ActionServlet_Stub01 servlet =
            new ValidatorActionFormEx_ActionServlet_Stub01();
        // プレフィックスの生成
        String prefix = "";
        // モジュール設定情報の生成
        ModuleConfig moduleConfig = new ModuleConfigImpl(prefix);
        // マッピング情報の生成
        ActionMapping mapping = new ActionMapping();
        // リセッターの生成
        ResetterImpl resetter = new ValidatorActionFormEx_ResetterStub01();

        // 設定
        request.setSession(session);
        request.setAttribute(Globals.MODULE_KEY, moduleConfig);
        session.setServletContext(context);
        context.setAttribute(Resetter.RESETTER_KEY + prefix, resetter);
        context.setAttribute(Globals.MODULE_KEY, moduleConfig);
        this.formEx.setServlet(servlet);
        servlet.setContext(context);

        //テスト実行
        this.formEx.reset(mapping, request);

        //テスト結果確認
        assertNotNull(request.getAttribute("SUCCESS"));
    }

    /**
     * testReset04()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) mapping:not null<br>
     *         (引数) request:サブモジュールを格納したリクエストオブジェクト<br>
     *         (状態) resetter:null<br>
     *
     * <br>
     * 期待値：(状態変化) ResetterImpl#reset()の呼び出し:-<br>
     *
     * <br>
     * サブモジュールを格納したリクエストオブジェクトを設定し、
     * Resetterが"null"だった場合、何もせずに終了することを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testReset04() throws Exception {

        // リクエストの生成
        MockHttpServletRequest request = new MockHttpServletRequest();
        // セッションの生成
        MockHttpSession session = new MockHttpSession();
        // コンテキストの生成
        MockServletContext context = new MockServletContext();
        // アクションサーブレットの生成
        ValidatorActionFormEx_ActionServlet_Stub01 servlet =
            new ValidatorActionFormEx_ActionServlet_Stub01();
        // プレフィックスの生成
        String prefix = "sub1";
        // モジュール設定情報の生成
        ModuleConfig moduleConfig = new ModuleConfigImpl(prefix);
        // マッピング情報の生成
        ActionMapping mapping = new ActionMapping();

        // 設定
        request.setSession(session);
        request.setAttribute(Globals.MODULE_KEY, moduleConfig);
        session.setServletContext(context);
        context.setAttribute(Resetter.RESETTER_KEY + prefix, null);
        context.setAttribute(Globals.MODULE_KEY, moduleConfig);
        this.formEx.setServlet(servlet);
        servlet.setContext(context);

        //テスト実行
        this.formEx.reset(mapping, request);
    }

    /**
     * testReset05()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) mapping:not null<br>
     *         (引数) request:サブモジュールを格納したリクエストオブジェクト<br>
     *         (状態) resetter:Resetter以外のオブジェクト<br>
     *
     * <br>
     * 期待値：(状態変化) ResetterImpl#reset()の呼び出し:-<br>
     *                   ログ：＜ログ＞<br>
     *                         エラーログ：""<br>
     *                         ＜例外＞<br>
     *                         ClassCastException<br>
     *
     * <br>
     * サブモジュールを格納したリクエストオブジェクトを設定し、
     * Resetter以外のオブジェクトを取得した場合、何もせずに終了することを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testReset05() throws Exception {

        // リクエストの生成
        MockHttpServletRequest request = new MockHttpServletRequest();
        // セッションの生成
        MockHttpSession session = new MockHttpSession();
        // コンテキストの生成
        MockServletContext context = new MockServletContext();
        // アクションサーブレットの生成
        ValidatorActionFormEx_ActionServlet_Stub01 servlet =
            new ValidatorActionFormEx_ActionServlet_Stub01();
        // プレフィックスの生成
        String prefix = "sub1";
        // モジュール設定情報の生成
        ModuleConfig moduleConfig = new ModuleConfigImpl(prefix);
        // マッピング情報の生成
        ActionMapping mapping = new ActionMapping();

        // 設定
        request.setSession(session);
        request.setAttribute(Globals.MODULE_KEY, moduleConfig);
        session.setServletContext(context);
        context.setAttribute(Resetter.RESETTER_KEY + prefix, new Object());
        context.setAttribute(Globals.MODULE_KEY, moduleConfig);
        this.formEx.setServlet(servlet);
        servlet.setContext(context);

        //テスト実行
        this.formEx.reset(mapping, request);

        //テスト結果確認
        assertTrue(
                LogUTUtil.checkError("",new ClassCastException()));
    }

    /**
     * testReset06()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) mapping:not null<br>
     *         (引数) request:サブモジュールを格納したリクエストオブジェクト<br>
     *         (状態) resetter:Resetterオブジェクト<br>
     *
     * <br>
     * 期待値：(状態変化) ResetterImpl#reset()の呼び出し:呼び出される<br>
     *                    requestに"SUCCESS"がキーの値が設定されることを確認。
     *
     * <br>
     * サブモジュールを格納したリクエストオブジェクトを設定し、
     * Resetterが、フィールドを初期化することを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testReset06() throws Exception {

        // リクエストの生成
        MockHttpServletRequest request = new MockHttpServletRequest();
        // セッションの生成
        MockHttpSession session = new MockHttpSession();
        // コンテキストの生成
        MockServletContext context = new MockServletContext();
        // アクションサーブレットの生成
        ValidatorActionFormEx_ActionServlet_Stub01 servlet =
            new ValidatorActionFormEx_ActionServlet_Stub01();
        // プレフィックスの生成
        String prefix = "sub1";
        // モジュール設定情報の生成
        ModuleConfig moduleConfig = new ModuleConfigImpl(prefix);
        // マッピング情報の生成
        ActionMapping mapping = new ActionMapping();
        // リセッターの生成
        ResetterImpl resetter = new ValidatorActionFormEx_ResetterStub01();

        // 設定
        request.setSession(session);
        request.setAttribute(Globals.MODULE_KEY, moduleConfig);
        session.setServletContext(context);
        context.setAttribute(Resetter.RESETTER_KEY + prefix, resetter);
        context.setAttribute(Globals.MODULE_KEY, moduleConfig);
        this.formEx.setServlet(servlet);
        servlet.setContext(context);

        //テスト実行
        this.formEx.reset(mapping, request);

        //テスト結果確認
        assertNotNull(request.getAttribute("SUCCESS"));
    }

}
