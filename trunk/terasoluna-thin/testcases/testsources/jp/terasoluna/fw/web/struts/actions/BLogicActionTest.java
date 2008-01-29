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

package jp.terasoluna.fw.web.struts.actions;

import java.util.HashMap;
import java.util.Map;

import jp.terasoluna.fw.service.thin.BLogic;
import jp.terasoluna.fw.service.thin.BLogicResult;
import jp.terasoluna.utlib.UTUtil;
import junit.framework.TestCase;

/**
 * {@link jp.terasoluna.fw.web.struts.actions.BLogicAction} クラスのブラックボックステスト。
 * 
 * <p>
 * <h4>【クラスの概要】</h4>
 * フレームワーク内部でビジネスロジックを実行する標準アクションクラス
 * <p>
 * 
 * @see jp.terasoluna.fw.web.struts.actions.BLogicAction
 */
@SuppressWarnings("unchecked")
public class BLogicActionTest extends TestCase {
    
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
    public BLogicActionTest(String name) {
        super(name);
    }

    /**
     * testSetBusinessLogic01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) businessLogic:Blogicオブジェクト<br>
     *         (状態) businessLogic:null<br>
     *         
     * <br>
     * 期待値：(状態変化) businessLogic:引数と同一のBlogicオブジェクト<br>
     *         
     * <br>
     * 引数に指定した値がbusinessLogicに正常に格納されることを確認
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetBusinessLogic01() throws Exception {
        // 前処理
        BLogicAction action = new BLogicAction();
        UTUtil.setPrivateField(action, "businessLogic", null);
        BLogicImpl01 blogic = new BLogicImpl01();

        // テスト実施
        action.setBusinessLogic(blogic);

        // 判定
        assertSame(blogic, UTUtil.getPrivateField(action, "businessLogic"));
    }

    /**
     * testGetBusinessLogic01()。<br>
     * 
     * （正常系）<br>
     * 観点：A<br>
     * <br>
     * 入力値 :引数なし<br>
     *         (状態) businessLogic:blogic<br>
     * 期待値 :businessLogic:blogic<br>
     *
     * businessLogicの値が取得できることを確認する。<br>
     *
     */
    public void testGetBusinessLogic01() throws Exception {
        // 前処理
        BLogicAction action = new BLogicAction();
        BLogicImpl01 blogic = new BLogicImpl01();        
        UTUtil.setPrivateField(action, "businessLogic", blogic);

        // テスト実施
        BLogic businessLogic = action.getBusinessLogic();

        // 判定
        assertSame(blogic, businessLogic);
    }

    /**
     * testDoExecuteBLogic01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) param:null<br>
     *         (状態) businessLogic:not null<br>
     *         (状態) businessLogic.execute():"businessLogic"<br>
     *         
     * <br>
     * 期待値：(戻り値) BLogicResult:"businessLogic"<br>
     *         
     * <br>
     * paramがnullであり、businessLogicがnot nullの場合、
     * businessLogic.executeメソッドの実行結果を返すことを確認
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testDoExecuteBLogic01() throws Exception {
        // 前処理
        BLogicAction action = new BLogicAction();
        // execute() : "businessLogic"
        BLogicImpl02 blogic = new BLogicImpl02();
        UTUtil.setPrivateField(action, "businessLogic", blogic);
        Map param = null;

        // テスト実施
        BLogicResult result = action.doExecuteBLogic(param);

        // 判定
        assertEquals("businessLogic", result.getResultString());
    }

    /**
     * testDoExecuteBLogic02()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) param:not null<br>
     *         (状態) businessLogic:null<br>
     *         
     * <br>
     * 期待値：(戻り値) BLogicResult:null<br>
     *         
     * <br>
     * フィールドbusinessLogicがnullだった場合、nullを返すことを確認
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testDoExecuteBLogic02() throws Exception {
        // 前処理
        BLogicAction action = new BLogicAction();
        
        // businessLogic : null
        UTUtil.setPrivateField(action, "businessLogic", null);
        Map param = new HashMap();

        // テスト実施
        BLogicResult result = action.doExecuteBLogic(param);

        // 判定
        assertNull(result);
    }

    /**
     * testDoExecuteBLogic03()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) param:not null<br>
     *         (状態) businessLogic:not null<br>
     *         (状態) businessLogic.execute():null<br>
     *         
     * <br>
     * 期待値：(戻り値) BLogicResult:null<br>
     *         
     * <br>
     * businessLogic.execute()の結果がnullだった場合、
     * 戻り値はnullであることを確認
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testDoExecuteBLogic03() throws Exception {
        // 前処理
        BLogicAction action = new BLogicAction();
        // execute() : null
        BLogicImpl01 blogic = new BLogicImpl01();
        UTUtil.setPrivateField(action, "businessLogic", blogic);
        Map param = new HashMap();

        // テスト実施
        BLogicResult result = action.doExecuteBLogic(param);

        // 判定
        assertNull(result);
    }

    /**
     * testDoExecuteBLogic04()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) param:not null<br>
     *         (状態) businessLogic:not null<br>
     *         (状態) businessLogic.execute():"businessLogic"<br>
     *         
     * <br>
     * 期待値：(戻り値) BLogicResult:"businessLogic"<br>
     *         
     * <br>
     * paramがnot nullであり、businessLogicがnot nullの場合、
     * businessLogic.executeメソッドの実行結果を返すことを確認
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testDoExecuteBLogic04() throws Exception {
        // 前処理
        BLogicAction action = new BLogicAction();
        // execute() : "businessLogic"
        BLogicImpl02 blogic = new BLogicImpl02();
        UTUtil.setPrivateField(action, "businessLogic", blogic);
        Map param = new HashMap();

        // テスト実施
        BLogicResult result = action.doExecuteBLogic(param);

        // 判定
        assertEquals("businessLogic", result.getResultString());
    }
}
