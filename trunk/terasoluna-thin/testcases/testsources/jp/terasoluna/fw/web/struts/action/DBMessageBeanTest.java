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
 * {@link jp.terasoluna.fw.web.struts.action.DBMessageBean} クラスの
 * ブラックボックステスト。
 * 
 * <p>
 * <h4>【クラスの概要】</h4>
 * DBから取得した行（メッセージリソース）を一時的にオブジェクトの形式で
 * 保管するためにあるクラス。
 * <p>
 * 
 * @see jp.terasoluna.fw.web.struts.action.DBMessageBean
 */
public class DBMessageBeanTest extends TestCase {

    /**
     * テスト用インスタンス。
     */
    DBMessageBean dbmBean = null;
    
    
    /**
     * このテストケースを実行する為の
     * GUI アプリケーションを起動する。
     * 
     * @param args java コマンドに設定されたパラメータ
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(DBMessageBeanTest.class);
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
        dbmBean = new DBMessageBean();
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
        dbmBean = null;
    }

    /**
     * コンストラクタ。
     * 
     * @param name このテストケースの名前。
     */
    public DBMessageBeanTest(String name) {
        super(name);
    }

    /**
     * testGetKey01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(状態) key:"test01"<br>
     *         
     * <br>
     * 期待値：(戻り値) String:"test01"<br>
     *         
     * <br>
     * getterの呼び出しを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetKey01() throws Exception {
        //  前処理
        UTUtil.setPrivateField(dbmBean, "key", "test01");
        
        //  テスト実施
        String actual = dbmBean.getKey();

        //  判定
        assertEquals("test01", actual);
    }

    /**
     * testSetKey01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) key:"test01"<br>
     *         (状態) key:null<br>
     *         
     * <br>
     * 期待値：(状態変化) key:"test01"<br>
     *         
     * <br>
     * setterの呼び出しを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetKey01() throws Exception {
        //  前処理
        UTUtil.setPrivateField(dbmBean, "key", null);

        //  テスト実施
        dbmBean.setKey("test01");

        //  判定
        assertEquals("test01", UTUtil.getPrivateField(dbmBean, "key"));
    }

    /**
     * testGetValue01()
     * <br><br>
     * 
     *  (正常系) 
     *  <br>
     * 観点：A
     * <br><br>
     * 入力値：(状態) value:"テストメッセージ０１"<br>
     *         
     * <br>
     * 期待値：(戻り値) String:"テストメッセージ０１"<br>
     *         
     * <br>
     * getterの呼び出しを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetValue01() throws Exception {
        //  前処理
        UTUtil.setPrivateField(dbmBean, "value", "テストメッセージ０１");
        
        //  テスト実施
        String actual = dbmBean.getValue();

        //  判定
        assertEquals("テストメッセージ０１", actual);
    }

    /**
     * testSetValue01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) value:"テストメッセージ０１"<br>
     *         (状態) value:null<br>
     *         
     * <br>
     * 期待値：(状態変化) value:"テストメッセージ０１"<br>
     *         
     * <br>
     * setterの呼び出しを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetValue01() throws Exception {
        //  前処理
        UTUtil.setPrivateField(dbmBean, "value", null);

        //  テスト実施
        dbmBean.setValue("テストメッセージ０１");

        //  判定
        assertEquals("テストメッセージ０１", 
                     UTUtil.getPrivateField(dbmBean, "value"));
    }

}
