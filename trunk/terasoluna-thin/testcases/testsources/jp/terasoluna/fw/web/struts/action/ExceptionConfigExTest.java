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
 * {@link jp.terasoluna.fw.web.struts.action.ExceptionConfigEx} 
 * クラスのブラックボックステスト。
 * 
 * <p>
 * <h4>【クラスの概要】</h4>
 * システム例外のマッピング情報クラス。<br>
 * ExceptionConfigを継承し、モジュール名のフィールドを追加する。
 * <p>
 * 
 * @see jp.terasoluna.fw.web.struts.action.ExceptionConfigEx
 */
public class ExceptionConfigExTest extends TestCase {

    /**
     * このテストケースを実行する為の
     * GUI アプリケーションを起動する。
     * 
     * @param args java コマンドに設定されたパラメータ
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(ExceptionConfigExTest.class);
    }

    /**
     * コンストラクタ。
     * 
     * @param name このテストケースの名前。
     */
    public ExceptionConfigExTest(String name) {
        super(name);
    }

    /**
     * testGetModule01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(状態) module:"test01"<br>
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
    public void testGetModule01() throws Exception {
        // 前処理
        String expected = "test01";
        ExceptionConfigEx ec = new ExceptionConfigEx();
        UTUtil.setPrivateField(ec, "module", expected);

        // テスト実施
        String actual = ec.getModule();

        // 判定
        assertEquals(expected, actual);
    }

    /**
     * testSetModule01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) module:"test01"<br>
     *         (状態) module:null<br>
     *         
     * <br>
     * 期待値：(状態変化) module:"test01"<br>
     *         
     * <br>
     * setterの呼び出しを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetModule01() throws Exception {
        // 前処理
        String expected = "test01";
        ExceptionConfigEx ec = new ExceptionConfigEx();
        UTUtil.setPrivateField(ec, "module", null);

        // テスト実施
        ec.setModule(expected);

        // 判定
        assertEquals(expected, UTUtil.getPrivateField(ec, "module"));
    }

}
