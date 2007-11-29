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

package jp.terasoluna.fw.web.struts.action;

import jp.terasoluna.utlib.UTUtil;
import junit.framework.TestCase;

/**
 * ActionMappingEx ブラックボックステスト。<br>
 * <br>
 * (前提条件)<br>
 *  　なし
 * <br>
 */
public class ActionMappingExTest extends TestCase {

    /**
     * Constructor for ActionMappingExTest.
     * @param arg0
     */
    public ActionMappingExTest(String arg0) {
        super(arg0);
    }

    /*
     * @see TestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    /*
     * @see TestCase#tearDown()
     */
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * testSetClearForm01()。<br>
     * 
     * （正常系）<br>
     * 観点：A<br>
     * <br>
     * 入力値 :アクションフォームクリアフラグ(true)<br>
     * 期待値 :セットされること<br>
     *
     * アクションフォームクリアフラグが設定されることを確認する。<br>
     *
     */
    public void testSetClearForm01() throws Exception {
        //初期設定
        ActionMappingEx ame = new ActionMappingEx();
        //テスト実行
        ame.setClearForm(true);
        //結果確認
        Boolean result = (Boolean) UTUtil.getPrivateField(ame, "clearForm");
        assertTrue(result.booleanValue());
    }

    /**
     * testGetClearForm01()。<br>
     * 
     * （正常系）<br>
     * 観点：A<br>
     * <br>
     * 入力値 :引数なし<br>
     * 期待値 :アクションフォームクリアフラグ<br>
     *
     * アクションフォームクリアフラグが取得できることを確認する。<br>
     *
     */
    public void testGetClearForm01() throws Exception {
        //初期設定
        ActionMappingEx ame = new ActionMappingEx();
        Boolean clearForm = new Boolean(true);
        UTUtil.setPrivateField(ame, "clearForm", clearForm);

        //テスト実行
        //結果確認
        assertTrue(ame.getClearForm());
    }

    /**
     * testSetCancelPopulate01()。<br>
     * 
     * （正常系）<br>
     * 観点：A<br>
     * <br>
     * 入力値 :ポピュレーションキャンセルフラグ(true)<br>
     * 期待値 :セットされること<br>
     *
     * ポピュレーションキャンセルフラグが設定されることを確認する。<br>
     *
     */
    public void testSetCancelPopulate01() throws Exception {
        //初期設定
        ActionMappingEx ame = new ActionMappingEx();
        //テスト実行
        ame.setCancelPopulate(true);
        //結果確認
        Boolean result = (Boolean) UTUtil.getPrivateField(ame, "cancelPopulate");
        assertTrue(result.booleanValue());

    }

    /**
     * testGetCancelPopulate01()。<br>
     * 
     * （正常系）<br>
     * 観点：A<br>
     * <br>
     * 入力値 :引数なし<br>
     * 期待値 :ポピュレーションキャンセルフラグ<br>
     *
     * ポピュレーションキャンセルフラグが真で取得されることを確認する。<br>
     *
     */
    public void testGetCancelPopulate01() throws Exception {
        //初期設定
        ActionMappingEx ame = new ActionMappingEx();
        Boolean cancelPopulate = new Boolean(true);
        UTUtil.setPrivateField(ame, "cancelPopulate", cancelPopulate);
        //テスト実行
        //結果確認
        assertTrue(ame.getCancelPopulate());
    }

}
