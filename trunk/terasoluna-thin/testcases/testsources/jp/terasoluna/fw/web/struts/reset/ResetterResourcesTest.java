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
import java.util.Map;

import jp.terasoluna.utlib.UTUtil;
import junit.framework.TestCase;

/**
 * {@link jp.terasoluna.fw.web.struts.reset.ResetterResources} クラスのブラックボックステスト。
 *
 * <p>
 * <h4>【クラスの概要】</h4>
 * リセット用の設定情報を保持するクラス。
 * <p>
 *
 * @see jp.terasoluna.fw.web.struts.reset.ResetterResources
 */
public class ResetterResourcesTest extends TestCase {

    /**
     * このテストケースを実行する為の
     * GUI アプリケーションを起動する。
     *
     * @param args java コマンドに設定されたパラメータ
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(ResetterResourcesTest.class);
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
    public ResetterResourcesTest(String name) {
        super(name);
    }

    /**
     * testSetActionReset01()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) actionReset:ActionReset<br>
     *                path="path"<br>
     *         (状態) actionResets:{}<br>
     *
     * <br>
     * 期待値：(状態変化) actionResets:{"path"=ActionReset}<br>
     *
     * <br>
     * 引数に指定したActionResetのpath属性をキーとして、actionResetsに引数のactionResetが設定されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetActionReset01() throws Exception {
        //初期設定
        ResetterResources rr = new ResetterResources();
        ActionReset ar = new ActionReset();
        ar.setPath("path");
        //テスト実行
        rr.setActionReset(ar);

        //結果確認
        Map map = (Map) UTUtil.getPrivateField(rr, "actionResets");
        ActionReset obj = (ActionReset) map.get("path");
        assertEquals("path", obj.getPath());
        //キーの確認
        assertTrue(map.containsKey("path"));
    }

    /**
     * testSetActionReset02()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：C,F
     * <br><br>
     * 入力値：(引数) actionReset:ActionReset<br>
     *                path=null<br>
     *         (状態) actionResets:{}<br>
     *
     * <br>
     * 期待値：(状態変化) actionResets:{null=ActionReset}<br>
     *
     * <br>
     * 引数に指定したActionResetのpath属性をキーとして、actionResetsに引数のactionResetが設定されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetActionReset02() throws Exception {
        //初期設定
        ResetterResources rr = new ResetterResources();
        ActionReset ar = new ActionReset();
        ar.setPath(null);
        //テスト実行
        rr.setActionReset(ar);
        //結果確認
        Map map = (Map) UTUtil.getPrivateField(rr, "actionResets");
        //キーの確認
        assertTrue(map.containsKey(null));
        ActionReset obj = (ActionReset) map.get(null);
        assertNull(obj.getPath());
    }

    /**
     * testSetActionReset03()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：C,F
     * <br><br>
     * 入力値：(引数) actionReset:ActionReset<br>
     *                path=""<br>
     *         (状態) actionResets:{}<br>
     *
     * <br>
     * 期待値：(状態変化) actionResets:{""=ActionReset}<br>
     *
     * <br>
     * 引数に指定したActionResetのpath属性をキーとして、actionResetsに引数のactionResetが設定されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetActionReset03() throws Exception {
        //初期設定
        ResetterResources rr = new ResetterResources();
        ActionReset ar = new ActionReset();
        ar.setPath("");
        //テスト実行
        rr.setActionReset(ar);
        //結果確認
        Map map = (Map) UTUtil.getPrivateField(rr, "actionResets");
        //キーの確認
        assertTrue(map.containsKey(""));
        ActionReset obj = (ActionReset) map.get("");
        assertEquals("", obj.getPath());
    }

    /**
     * testGet01()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) path:"path"<br>
     *         (状態) actionResets:{"path"=ActionReset}<br>
     *
     * <br>
     * 期待値：(戻り値) ActionReset:ActonReset<br>
     *                   path="path"<br>
     *
     * <br>
     * 引数に指定した値をキーとしてactionResetsにActionResetが存在する場合、そのActionResetインスタンスが取得できることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testGet01() throws Exception {
        //初期設定
        ResetterResources rr = new ResetterResources();
        ActionReset ar = new ActionReset();
        ar.setPath("path");

        Map<String, ActionReset> actionResets = new HashMap<String, ActionReset>();
        actionResets.put(ar.getPath(), ar);

        UTUtil.setPrivateField(rr, "actionResets", actionResets);

        //テスト実行
        ActionReset result = rr.get("path");
        //結果確認
        assertEquals("path", result.getPath());
    }

    /**
     * testGet02()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) path:"path0"<br>
     *         (状態) actionResets:{"path"=ActionReset}<br>
     *
     * <br>
     * 期待値：(戻り値) ActionReset:null<br>
     *
     * <br>
     * 引数に指定した値をキーとしてactionResetsにActionResetが存在しない場合、nullが取得できることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testGet02() throws Exception {
        //初期設定
        ResetterResources rr = new ResetterResources();
        ActionReset ar = new ActionReset();
        ar.setPath("path");

        Map<String, ActionReset> actionResets = new HashMap<String, ActionReset>();
        actionResets.put(ar.getPath(), ar);

        UTUtil.setPrivateField(rr, "actionResets", actionResets);

        //テスト実行
        //結果確認
        assertNull(rr.get("path0"));
    }

    /**
     * testGet03()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) path:null<br>
     *         (状態) actionResets:{null=ActionReset}<br>
     *
     * <br>
     * 期待値：(戻り値) ActionReset:ActionReset<br>
     *                   path=null<br>
     *
     * <br>
     * 引数に指定した値をキーとしてactionResetsにActionResetが存在する場合、そのActionResetインスタンスが取得できることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testGet03() throws Exception {
        //初期設定
        ResetterResources rr = new ResetterResources();
        ActionReset ar = new ActionReset();
        ar.setPath(null);

        Map<String, ActionReset> actionResets = new HashMap<String, ActionReset>();
        actionResets.put(ar.getPath(), ar);

        UTUtil.setPrivateField(rr, "actionResets", actionResets);

        //テスト実行
        ActionReset result = rr.get(null);
        //結果確認
        assertNull(result.getPath());
    }

    /**
     * testGet04()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) path:""<br>
     *         (状態) actionResets:{""=ActionReset}<br>
     *
     * <br>
     * 期待値：(戻り値) ActionReset:ActionReset<br>
     *                   path=""<br>
     *
     * <br>
     * 引数に指定した値をキーとしてactionResetsにActionResetが存在する場合、そのActionResetインスタンスが取得できることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testGet04() throws Exception {
        //初期設定
        ResetterResources rr = new ResetterResources();
        ActionReset ar = new ActionReset();
        ar.setPath("");

        Map<String, ActionReset> actionResets = new HashMap<String, ActionReset>();
        actionResets.put(ar.getPath(), ar);

        UTUtil.setPrivateField(rr, "actionResets", actionResets);

        //テスト実行
        ActionReset result = rr.get("");
        //結果確認
        assertEquals("", result.getPath());
    }

}
