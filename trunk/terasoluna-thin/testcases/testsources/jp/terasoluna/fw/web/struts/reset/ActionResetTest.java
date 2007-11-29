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

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import jp.terasoluna.utlib.UTUtil;
import junit.framework.TestCase;

/**
 * {@link jp.terasoluna.fw.web.struts.reset.ActionReset} クラスのブラックボックステスト。
 *
 * <p>
 * <h4>【クラスの概要】</h4>
 * アクションパス単位のリセット用の設定情報を保持する Bean クラス。
 * <p>
 *
 * @see jp.terasoluna.fw.web.struts.reset.ActionReset
 */
public class ActionResetTest extends TestCase {

    /**
     * このテストケースを実行する為の
     * GUI アプリケーションを起動する。
     *
     * @param args java コマンドに設定されたパラメータ
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(ActionResetTest.class);
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
    public ActionResetTest(String name) {
        super(name);
    }

    /**
     * testSetFieldReset01()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) fieldReset:name="name"<br>
     *         (状態) fieldResets:{}<br>
     *
     * <br>
     * 期待値：(状態変化) fieldResets:{"name"=FieldReset(name="name")}<br>
     *
     * <br>
     * 引数のFieldResetインスタンスのname属性をキー、インスタンスを値として、fieldResetsに追加されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetFieldReset01() throws Exception {
        //初期設定
        ActionReset ar = new ActionReset();
        FieldReset fr = new FieldReset();
        fr.setFieldName("name");

        //テスト実行
        ar.setFieldReset(fr);
        //結果確認
        Map result = (HashMap) UTUtil.getPrivateField(ar, "fieldResets");
        assertTrue(result.containsKey("name"));
        assertTrue(result.containsValue(fr));
    }

    /**
     * testGetPath01()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(状態) path:"path"<br>
     *
     * <br>
     * 期待値：(戻り値) String:"path"<br>
     *
     * <br>
     * インスタンス変数pathの値が取得できることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetPath01() throws Exception {
        //初期設定
        ActionReset ar = new ActionReset();
        UTUtil.setPrivateField(ar, "path", "path");

        //テスト実行
        //結果確認
        assertEquals("path", ar.getPath());
    }

    /**
     * testSetPath01()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) path:"path1"<br>
     *         (状態) path:null<br>
     *
     * <br>
     * 期待値：(状態変化) path:"path1"<br>
     *
     * <br>
     * 引数の値がインスタンス変数pathに設定されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetPath01() throws Exception {
        //初期設定
        ActionReset ar = new ActionReset();
        //テスト実行
        ar.setPath("path1");
        //結果確認
        assertEquals("path1", UTUtil.getPrivateField(ar, "path"));
    }

    /**
     * testGetFieldNames01()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：D
     * <br><br>
     * 入力値：(状態) fieldResets:{"key1"=FieldReset,<br>
     *                 "key2"=FieldReset,<br>
     *                 "key3"=FieldReset}<br>
     *
     * <br>
     * 期待値：(戻り値) Iterator:{"key1","key2","key3"}(順不同)<br>
     *
     * <br>
     * fieldResetsに複数の要素が存在する場合、全てのキーの値が取得できることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetFieldNames01() throws Exception {
        //初期設定
        ActionReset ar = new ActionReset();
        FieldReset fr = new FieldReset();

        Map<String, FieldReset> map = new TreeMap<String, FieldReset>();
        map.put("key1", fr);
        map.put("key2", fr);
        map.put("key3", fr);

        UTUtil.setPrivateField(ar, "fieldResets", map);

        //テスト実行
        Iterator result = ar.getFieldNames();

        //結果確認
        assertEquals("key1", (String) result.next());
        assertEquals("key2", (String) result.next());
        assertEquals("key3", (String) result.next());
        assertFalse(result.hasNext());
    }

    /**
     * testGetFieldNames02()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：D
     * <br><br>
     * 入力値：(状態) fieldResets:{"key1"=FieldReset}<br>
     *
     * <br>
     * 期待値：(戻り値) Iterator:{"key1"}<br>
     *
     * <br>
     * fieldResetsに1件の要素が存在する場合、そのキーの値が取得できることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetFieldNames02() throws Exception {
        //初期設定
        ActionReset ar = new ActionReset();
        FieldReset fr = new FieldReset();

        Map<String, FieldReset> map = new HashMap<String, FieldReset>();
        map.put("key1", fr);

        UTUtil.setPrivateField(ar, "fieldResets", map);

        //テスト実行
        Iterator result = ar.getFieldNames();

        //結果確認
        assertEquals("key1", (String) result.next());
        assertFalse(result.hasNext());
    }

    /**
     * testGetFieldNames03()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：D
     * <br><br>
     * 入力値：(状態) fieldResets:{}<br>
     *
     * <br>
     * 期待値：(戻り値) Iterator:{}<br>
     *
     * <br>
     * fieldResetsに要素が存在しない場合、空のIteratorが取得できることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetFieldNames03() throws Exception {
        //初期設定
        ActionReset ar = new ActionReset();

        Map map = new HashMap();

        UTUtil.setPrivateField(ar, "fieldResets", map);
        //テスト実行
        Iterator result = ar.getFieldNames();
        //結果確認
        assertFalse(result.hasNext());
    }

    /**
     * testIsSelectedField01()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) fieldName:"aaa"<br>
     *         (状態) fieldResets:{"name"=FieldReset(select = true)}<br>
     *
     * <br>
     * 期待値：(戻り値) boolean:false<br>
     *
     * <br>
     * 引数のfieldNameに指定した名前のFieldResetが存在しない場合、falseが取得できることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testIsSelectedField01() throws Exception {
        //初期設定
        ActionReset ar = new ActionReset();
        FieldReset fr = new FieldReset();
        fr.setFieldName("name");
        fr.setSelect(true);
        Map<String, FieldReset> map = new HashMap<String, FieldReset>();
        map.put("name", fr);
        UTUtil.setPrivateField(ar, "fieldResets", map);

        //テスト実行
        //結果確認
        assertFalse(ar.isSelectField("aaa"));
    }

    /**
     * testIsSelectedField02()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) fieldName:"name"<br>
     *         (状態) fieldResets:{"name"=FieldReset(select = true)}<br>
     *
     * <br>
     * 期待値：(戻り値) boolean:true<br>
     *
     * <br>
     * 引数のfieldNameに指定した名前のFieldResetが存在する場合、そのFieldResetのインスタンス変数selectの値が取得できることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testIsSelectedField02() throws Exception {
        //初期設定
        ActionReset ar = new ActionReset();
        FieldReset fr = new FieldReset();
        fr.setSelect(true);

        Map<String, FieldReset> map = new HashMap<String, FieldReset>();
        map.put("name", fr);

        UTUtil.setPrivateField(ar, "fieldResets", map);

        //テスト実行
        //結果確認
        assertTrue(ar.isSelectField("name"));
    }

    /**
     * testIsSelectedField03()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) fieldName:null<br>
     *         (状態) fieldResets:{}<br>
     *
     * <br>
     * 期待値：(戻り値) boolean:false<br>
     *
     * <br>
     * 引数のfieldNameに指定した値がnullの場合、falseが取得できることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testIsSelectedField03() throws Exception {
        //初期設定
        ActionReset ar = new ActionReset();

        //テスト実行
        //結果確認
        assertFalse(ar.isSelectField(null));
    }

    /**
     * testIsSelectedField04()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) fieldName:""<br>
     *         (状態) fieldResets:{}<br>
     *
     * <br>
     * 期待値：(戻り値) boolean:false<br>
     *
     * <br>
     * 引数のfieldNameに指定した値が空文字の場合、falseが取得できることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testIsSelectedField04() throws Exception {
        //初期設定
        ActionReset ar = new ActionReset();

        //テスト実行
        //結果確認
        assertFalse(ar.isSelectField(""));
    }

}
