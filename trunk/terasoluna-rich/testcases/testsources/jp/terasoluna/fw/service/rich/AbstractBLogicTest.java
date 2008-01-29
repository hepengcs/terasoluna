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

package jp.terasoluna.fw.service.rich;

import jp.terasoluna.fw.dao.QueryDAO;
import jp.terasoluna.fw.dao.StoredProcedureDAO;
import jp.terasoluna.fw.dao.UpdateDAO;
import jp.terasoluna.utlib.UTUtil;
import jp.terasoluna.fw.service.rich.AbstractBLogic;
import junit.framework.TestCase;

/**
 * {@link jp.terasoluna.fw.service.rich.AbstractBLogic}
 * クラスのブラックボックステスト。
 * 
 * <p>
 * <h4>【クラスの概要】</h4>
 * DAOのアクセサメソッドを持つBLogicの抽象クラス。
 * <p>
 * 
 * @see jp.terasoluna.fw.service.rich.AbstractBLogic
 */
public class AbstractBLogicTest extends TestCase {

    /**
     * このテストケースを実行する為の
     * GUI アプリケーションを起動する。
     * 
     * @param args java コマンドに設定されたパラメータ
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(AbstractBLogicTest.class);
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
    public AbstractBLogicTest(String name) {
        super(name);
    }

    /**
     * testSetQueryDAO01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) queryDAO:QueryDAOインスタンス<br>
     *         (状態) queryDAO:null<br>
     *         
     * <br>
     * 期待値：(状態変化) queryDAO:引き数と同一のインスタンス<br>
     *         
     * <br>
     * 引き数の値を正常に属性に設定すること。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetQueryDAO01() throws Exception {
        // 前処理
        // QueryDAO実装クラス
        QueryDAO dao = new QueryDAOImpl01();
        
        // AbstractBLogic拡張クラス
        AbstractBLogic blogic = new AbstractBLogic_AbstractBLogicStub01();
        UTUtil.setPrivateField(blogic, "queryDAO", null);
        
        // テスト実施
        blogic.setQueryDAO(dao);

        // 判定
        assertSame(dao, UTUtil.getPrivateField(blogic, "queryDAO"));
    }

    /**
     * testGetQueryDAO01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) なし:－<br>
     *         (状態) queryDAO:QueryDAOインスタンス<br>
     *         
     * <br>
     * 期待値：(戻り値) QueryDAO:属性と同一のインスタンス<br>
     *         
     * <br>
     * 属性に設定されている値を正常に取得すること。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetQueryDAO01() throws Exception {
        // 前処理
        // QueryDAO実装クラス
        QueryDAO dao = new QueryDAOImpl01();
        
        // AbstractBLogic拡張クラス
        AbstractBLogic blogic = new AbstractBLogic_AbstractBLogicStub01();
        UTUtil.setPrivateField(blogic, "queryDAO", dao);
        
        // テスト実施
        QueryDAO getDao = blogic.getQueryDAO();

        // 判定
        assertSame(dao, getDao);
    }

    /**
     * testSetUpdateDAO01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) updateDAO:UpdateDAOインスタンス<br>
     *         (状態) updateDAO:null<br>
     *         
     * <br>
     * 期待値：(状態変化) updateDAO:引き数と同一のインスタンス<br>
     *         
     * <br>
     * 引き数の値を正常に属性に設定すること。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetUpdateDAO01() throws Exception {
        // 前処理
        // UpdateDAO実装クラス
        UpdateDAO dao = new UpdateDAOImpl01();
        
        // AbstractBLogic拡張クラス
        AbstractBLogic blogic = new AbstractBLogic_AbstractBLogicStub01();
        UTUtil.setPrivateField(blogic, "updateDAO", null);
        
        // テスト実施
        blogic.setUpdateDAO(dao);

        // 判定
        assertSame(dao, UTUtil.getPrivateField(blogic, "updateDAO"));
    }

    /**
     * testGetUpdateDAO01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) なし:－<br>
     *         (状態) updateDAO:UpdateDAOインスタンス<br>
     *         
     * <br>
     * 期待値：(戻り値) UpdateDAO:属性と同一のインスタンス<br>
     *         
     * <br>
     * 属性に設定されている値を正常に取得すること。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetUpdateDAO01() throws Exception {
        // 前処理
        // UpdateDAO実装クラス
        UpdateDAO dao = new UpdateDAOImpl01();
        
        // AbstractBLogic拡張クラス
        AbstractBLogic blogic = new AbstractBLogic_AbstractBLogicStub01();
        UTUtil.setPrivateField(blogic, "updateDAO", dao);
        
        // テスト実施
        UpdateDAO getDao = blogic.getUpdateDAO();

        // 判定
        assertSame(dao, getDao);
    }

    /**
     * testSetStoredProcedureDAO01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) storedProcedureDAO:storedProcedureDAOインスタンス<br>
     *         (状態) storedProcedureDAO:null<br>
     *         
     * <br>
     * 期待値：(状態変化) storedProcedureDAO:引き数と同一のインスタンス<br>
     *         
     * <br>
     * 引き数の値を正常に属性に設定すること。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetStoredProcedureDAO01() throws Exception {
        // 前処理
        // StoredProcedureDAO実装クラス
        StoredProcedureDAO dao = new StoredProcedureDAOImpl01();
        
        // AbstractBLogic拡張クラス
        AbstractBLogic blogic = new AbstractBLogic_AbstractBLogicStub01();
        UTUtil.setPrivateField(blogic, "storedProcedureDAO", null);
        
        // テスト実施
        blogic.setStoredProcedureDAO(dao);

        // 判定
        assertSame(dao, UTUtil.getPrivateField(blogic, "storedProcedureDAO"));
    }

    /**
     * testGetStoredProcedureDAO01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) なし:－<br>
     *         (状態) storedProcedureDAO:storedProcedureDAOインスタンス<br>
     *         
     * <br>
     * 期待値：(戻り値) StoredProcedureDAO:属性と同一のインスタンス<br>
     *         
     * <br>
     * 属性に設定されている値を正常に取得すること。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetStoredProcedureDAO01() throws Exception {
        // 前処理
        // StoredProcedureDAO実装クラス
        StoredProcedureDAO dao = new StoredProcedureDAOImpl01();
        
        // AbstractBLogic拡張クラス
        AbstractBLogic blogic = new AbstractBLogic_AbstractBLogicStub01();
        UTUtil.setPrivateField(blogic, "storedProcedureDAO", dao);
        
        // テスト実施
        StoredProcedureDAO getDao = blogic.getStoredProcedureDAO();

        // 判定
        assertSame(dao, getDao);
    }
}