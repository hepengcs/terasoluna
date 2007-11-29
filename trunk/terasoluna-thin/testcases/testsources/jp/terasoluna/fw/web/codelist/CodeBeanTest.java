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

package jp.terasoluna.fw.web.codelist;

import jp.terasoluna.utlib.UTUtil;
import junit.framework.TestCase;

/**
 * {@link jp.terasoluna.fw.web.codelist.CodeBean} クラスのブラックボックステスト。
 * 
 * <p>
 * <h4>【クラスの概要】</h4>
 * コードとコード値(表示文字列)を保持するBeanクラス。
 * <p>
 * 
 * @see jp.terasoluna.fw.web.codelist.CodeBean
 * 
 */
public class CodeBeanTest extends TestCase {

    /**
     * このテストケースを実行する為の GUI アプリケーションを起動する。
     * 
     * @param args
     *            java コマンドに設定されたパラメータ
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(CodeBeanTest.class);
    }

    /**
     * 初期化処理を行う。
     * 
     * @throws Exception
     *             このメソッドで発生した例外
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    /**
     * 終了処理を行う。
     * 
     * @throws Exception
     *             このメソッドで発生した例外
     * @see junit.framework.TestCase#tearDown()
     */
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * コンストラクタ。
     * 
     * @param name
     *            このテストケースの名前。
     */
    public CodeBeanTest(String name) {
        super(name);
    }

    /**
     * testSetId01() <br>
     * <br>
     * 
     * (正常系)<br>
     * 観点：A <br>
     * <br>
     * 入力値：(引数) id:"id"<br>
     * (状態) id:null<br>
     * 
     * <br>
     * 期待値：(状態変化) id:"id"<br>
     * 
     * <br>
     * 引数に指定された文字列がインスタンス変数idに設定されることを確認する。 <br>
     * 
     * @throws Exception
     *             このメソッドで発生した例外
     */
    public void testSetId01() throws Exception {
        // 前処理
        CodeBean codeBean = new CodeBean();

        // テスト実施
        codeBean.setId("id");
        // 判定
        assertEquals("id", UTUtil.getPrivateField(codeBean, "id"));
    }

    /**
     * testSetId02() <br>
     * <br>
     * 
     * (正常系)<br>
     * 観点：A <br>
     * <br>
     * 入力値：(引数) id:null<br>
     * (状態) id:null<br>
     * 
     * <br>
     * 期待値：(状態変化) id:""<br>
     * 
     * <br>
     * 引数のidがnullの場合、空文字が設定されることを確認する。 <br>
     * 
     * @throws Exception
     *             このメソッドで発生した例外
     */
    public void testSetId02() throws Exception {
        // 前処理
        CodeBean codeBean = new CodeBean();
        // テスト実施
        codeBean.setId(null);
        // 判定
        assertEquals("", UTUtil.getPrivateField(codeBean, "id"));
    }

    /**
     * testGetId01() <br>
     * <br>
     * 
     * (正常系)<br>
     * 観点：A <br>
     * <br>
     * 入力値：(状態) id:"id"<br>
     * 
     * <br>
     * 期待値：(戻り値) String:"id"<br>
     * 
     * <br>
     * インスタンス変数idの値が取得できることを確認する。 <br>
     * 
     * @throws Exception
     *             このメソッドで発生した例外
     */
    public void testGetId01() throws Exception {
        // 前処理
        CodeBean codeBean = new CodeBean();
        UTUtil.setPrivateField(codeBean, "id", "id");
        
        // テスト実施・判定
        assertEquals("id", codeBean.getId());
    }

    /**
     * testSetName01() <br>
     * <br>
     * 
     * (正常系)<br>
     * 観点：A <br>
     * <br>
     * 入力値：(引数) name:"name"<br>
     * (状態) name:null<br>
     * 
     * <br>
     * 期待値：(状態変化) name:"name"<br>
     * 
     * <br>
     * 引数に指定された文字列がインスタンス変数nameに設定されることを確認する。 <br>
     * 
     * @throws Exception
     *             このメソッドで発生した例外
     */
    public void testSetName01() throws Exception {
        // 前処理
        CodeBean codeBean = new CodeBean();
        // テスト実施
        codeBean.setName("name");
        // 判定
        assertEquals("name", UTUtil.getPrivateField(codeBean, "name"));
    }

    /**
     * testSetName02() <br>
     * <br>
     * 
     * (正常系)<br>
     * 観点：A <br>
     * <br>
     * 入力値：(引数) name:null<br>
     * (状態) name:null<br>
     * 
     * <br>
     * 期待値：(状態変化) name:""<br>
     * 
     * <br>
     * 引数のnameがnullの場合、空文字が設定されることを確認する。 <br>
     * 
     * @throws Exception
     *             このメソッドで発生した例外
     */
    public void testSetName02() throws Exception {
        // 前処理
        CodeBean codeBean = new CodeBean();
        // テスト実施
        codeBean.setName(null);
        // 判定
        assertEquals("", UTUtil.getPrivateField(codeBean, "name"));
    }

    /**
     * testGetName01() <br>
     * <br>
     * 
     * (正常系)<br>
     * 観点：A <br>
     * <br>
     * 入力値：(状態) name:"name"<br>
     * 
     * <br>
     * 期待値：(戻り値) String:"name"<br>
     * 
     * <br>
     * インスタンス変数nameの値が取得できることを確認する。 <br>
     * 
     * @throws Exception
     *             このメソッドで発生した例外
     */
    public void testGetName01() throws Exception {
        // 前処理
        CodeBean codeBean = new CodeBean();
        UTUtil.setPrivateField(codeBean, "name", "name");
        
        // テスト実施・判定
        assertEquals("name", codeBean.getName());
    }

    /**
     * testGetLabel01() <br>
     * <br>
     * 
     * (正常系)<br>
     * 観点：A <br>
     * <br>
     * 入力値：(状態) id:null<br>
     * (状態) name:null<br>
     * 
     * <br>
     * 期待値：(戻り値) String:"null null"<br>
     * 
     * <br>
     * 変数idとnameがnullの場合、「null null」が取得できることを確認する。 <br>
     * 
     * @throws Exception
     *             このメソッドで発生した例外
     */
    public void testGetLabel01() throws Exception {
        // 前処理
        CodeBean codeBean = new CodeBean();
        UTUtil.setPrivateField(codeBean, "id", null);
        UTUtil.setPrivateField(codeBean, "name", null);
        
        // テスト実施・判定
        assertEquals("null null", codeBean.getLabel());
    }

    /**
     * testGetLabel02() <br>
     * <br>
     * 
     * (正常系)<br>
     * 観点：A <br>
     * <br>
     * 入力値：(状態) id:""<br>
     * (状態) name:""<br>
     * 
     * <br>
     * 期待値：(戻り値) String:" "<br>
     * 
     * <br>
     * 変数idとnameが空文字の場合、「 」（半角スペース）が取得できることを確認する。 <br>
     * 
     * @throws Exception
     *             このメソッドで発生した例外
     */
    public void testGetLabel02() throws Exception {
        // 前処理
        CodeBean codeBean = new CodeBean();
        UTUtil.setPrivateField(codeBean, "id", "");
        UTUtil.setPrivateField(codeBean, "name", "");
        
        // テスト実施・判定
        assertEquals(" ", codeBean.getLabel());
    }

    /**
     * testGetLabel03() <br>
     * <br>
     * 
     * (正常系)<br>
     * 観点：A <br>
     * <br>
     * 入力値：(状態) id:"id"<br>
     * (状態) name:"name"<br>
     * 
     * <br>
     * 期待値：(戻り値) String:"id name"<br>
     * 
     * <br>
     * 変数idとnameに文字列が設定されている場合、
     * idの文字列とnameの文字列を半角スペースで連結した文字列が取得できることを確認する。 <br>
     * 
     * @throws Exception
     *             このメソッドで発生した例外
     */
    public void testGetLabel03() throws Exception {
        // 前処理
        CodeBean codeBean = new CodeBean();
        UTUtil.setPrivateField(codeBean, "id", "id");
        UTUtil.setPrivateField(codeBean, "name", "name");
        
        // テスト実施・判定
        assertEquals("id name", codeBean.getLabel());
    }

    /**
     * testGetCodeCommaName01() <br>
     * <br>
     * 
     * (正常系)<br>
     * 観点：A <br>
     * <br>
     * 入力値：(状態) id:null<br>
     * (状態) name:null<br>
     * 
     * <br>
     * 期待値：(戻り値) String:"null,null"<br>
     * 
     * <br>
     * 変数idとnameがnullの場合、「null,null」が取得できることを確認する。 <br>
     * 
     * @throws Exception
     *             このメソッドで発生した例外
     */
    public void testGetCodeCommaName01() throws Exception {
        // 前処理
        CodeBean codeBean = new CodeBean();
        UTUtil.setPrivateField(codeBean, "id", null);
        UTUtil.setPrivateField(codeBean, "name", null);
        
        // テスト実施・判定
        assertEquals("null,null", codeBean.getCodeCommaName());
    }

    /**
     * testGetCodeCommaName02() <br>
     * <br>
     * 
     * (正常系)<br>
     * 観点：A <br>
     * <br>
     * 入力値：(状態) id:""<br>
     * (状態) name:""<br>
     * 
     * <br>
     * 期待値：(戻り値) String:","<br>
     * 
     * <br>
     * 変数idとnameが空文字の場合、「,」が取得できることを確認する。 <br>
     * 
     * @throws Exception
     *             このメソッドで発生した例外
     */
    public void testGetCodeCommaName02() throws Exception {
        // 前処理
        CodeBean codeBean = new CodeBean();
        UTUtil.setPrivateField(codeBean, "id", "");
        UTUtil.setPrivateField(codeBean, "name", "");
        
        // テスト実施・判定
        assertEquals(",", codeBean.getCodeCommaName());

    }

    /**
     * testGetCodeCommaName03() <br>
     * <br>
     * 
     * (正常系)<br>
     * 観点：A <br>
     * <br>
     * 入力値：(状態) id:"id"<br>
     * (状態) name:"name"<br>
     * 
     * <br>
     * 期待値：(戻り値) String:"id,name"<br>
     * 
     * <br>
     * 変数idとnameに文字列が設定されている場合、
     * idの文字列とnameの文字列をカンマで連結した文字列が取得できることを確認する。 <br>
     * 
     * @throws Exception
     *             このメソッドで発生した例外
     */
    public void testGetCodeCommaName03() throws Exception {
        // 前処理
        CodeBean codeBean = new CodeBean();
        UTUtil.setPrivateField(codeBean, "id", "id");
        UTUtil.setPrivateField(codeBean, "name", "name");
        
        // テスト実施・判定
        assertEquals("id,name", codeBean.getCodeCommaName());
    }

}
