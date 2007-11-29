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

package jp.terasoluna.fw.web.struts.reset;

import jp.terasoluna.utlib.UTUtil;
import junit.framework.TestCase;

/**
 * {@link jp.terasoluna.fw.web.struts.reset.FieldReset} クラスのブラックボックステスト。
 *
 * <p>
 * <h4>【クラスの概要】</h4>
 * フィールドのリセット情報を保持する Bean クラス。
 * <p>
 *
 * @see jp.terasoluna.fw.web.struts.reset.FieldReset
 */
public class FieldResetTest extends TestCase {

    /**
     * このテストケースを実行する為の
     * GUI アプリケーションを起動する。
     *
     * @param args java コマンドに設定されたパラメータ
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(FieldResetTest.class);
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
    public FieldResetTest(String name) {
        super(name);
    }

    /**
     * testGetFieldName01()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(状態) fieldName:"field4"<br>
     *
     * <br>
     * 期待値：(戻り値) String:"field4"<br>
     *
     * <br>
     * インスタンス変数fieldNameの値が取得できることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetFieldName01() throws Exception {
        //初期設定
        FieldReset fr = new FieldReset();
        UTUtil.setPrivateField(fr, "fieldName", "field4");
        //テスト実行
        //結果確認
        assertEquals("field4", fr.getFieldName());
    }

    /**
     * testSetFieldName01()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) fieldName:"field5"<br>
     *         (状態) fieldName:null<br>
     *
     * <br>
     * 期待値：(状態変化) fieldName:"field5"<br>
     *
     * <br>
     * 引数の値がインスタンス変数fieldNameに設定されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetFieldName01() throws Exception {
        //初期設定
        FieldReset fr = new FieldReset();
        //テスト実行
        fr.setFieldName("field5");
        //結果確認
        assertEquals("field5", UTUtil.getPrivateField(fr, "fieldName"));
    }

    /**
     * testIsSelect01()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(状態) select:true<br>
     *
     * <br>
     * 期待値：(戻り値) boolean:true<br>
     *
     * <br>
     * インスタンス変数selectの値が取得できることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testIsSelect01() throws Exception {
        //初期設定
        FieldReset fr = new FieldReset();
        Boolean select = new Boolean(true);
        UTUtil.setPrivateField(fr, "select", select);
        //テスト実行
        assertTrue(fr.isSelect());
    }

    /**
     * testSetSelect01()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) select:true<br>
     *         (状態) select:false<br>
     *
     * <br>
     * 期待値：(状態変化) select:true<br>
     *
     * <br>
     * 引数の値がインスタンス変数selectに設定されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetSelect01() throws Exception {
        //初期設定
        FieldReset fr = new FieldReset();
        //テスト実行
        fr.setSelect(true);
        //結果確認
        Boolean result = (Boolean) UTUtil.getPrivateField(fr, "select");
        assertTrue(result.booleanValue());
    }

}
