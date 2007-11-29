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

package jp.terasoluna.fw.service.thin;

import jp.terasoluna.utlib.UTUtil;
import junit.framework.TestCase;

/**
 * {@link jp.terasoluna.fw.service.thin.BLogicProperty} クラスの
 * ブラックボックステスト。
 * 
 * <p>
 * <h4>【クラスの概要】</h4>
 * ビジネスロジック入出力情報の個々のプロパティ情報を保持するクラス。<br>
 * BLogicIOPlugInでビジネスロジック入出力情報定義ファイルであるblogic-io.xmlから、
 * ビジネスロジックに対する入力JavaBean及び出力JavaBeanのプロパティ設定を保持する。
 * <p>
 * 
 * @see jp.terasoluna.fw.service.thin.BLogicProperty
 */
public class BLogicPropertyTest extends TestCase {

    /**
     * このテストケースを実行する為の
     * GUI アプリケーションを起動する。
     * 
     * @param args java コマンドに設定されたパラメータ
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(BLogicPropertyTest.class);
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
    public BLogicPropertyTest(String name) {
        super(name);
    }

    /**
     * testGetProperty01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) なし:－<br>
     *         (状態) property:"property"<br>
     *         
     * <br>
     * 期待値：(戻り値) String:"property"<br>
     *         
     * <br>
     * BLogicPropertyに格納されているpropertyを正常に取得すること。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetProperty01() throws Exception {
        //ビジネスロジックプロパティ情報
        BLogicProperty blogicProperty = new BLogicProperty();

        //property設定
        UTUtil.setPrivateField(blogicProperty, "property", "property");

        //テスト実行・結果確認
        assertEquals("property", blogicProperty.getProperty());
    }

    /**
     * testSetProperty01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) property:"property"<br>
     *         (状態) property:null<br>
     *         
     * <br>
     * 期待値：(戻り値) void:－<br>
     *         (状態変化) property:"property"<br>
     *         
     * <br>
     * 引数に指定した値がBLogicPropertyのpropertyに正常に格納されること。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetProperty01() throws Exception {
        //ビジネスロジックプロパティ情報
        BLogicProperty blogicProperty = new BLogicProperty();

        //テスト実行
        blogicProperty.setProperty("property");

        //テスト結果確認
        assertEquals("property", UTUtil.getPrivateField(blogicProperty,
                "property"));
    }

    /**
     * testGetBLogicProperty01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) なし:－<br>
     *         (状態) blogicProperty:"blogicProperty"<br>
     *         
     * <br>
     * 期待値：(戻り値) String:"blogicProperty"<br>
     *         
     * <br>
     * BLogicPropertyに格納されているblogicPropertyを正常に取得すること。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetBLogicProperty01() throws Exception {
        //ビジネスロジックプロパティ情報
        BLogicProperty blogicProperty = new BLogicProperty();

        //blogicProperty設定
        UTUtil.setPrivateField(blogicProperty, "blogicProperty",
                "blogicProperty");

        //テスト実行・結果確認
        assertEquals("blogicProperty", blogicProperty.getBLogicProperty());
    }

    /**
     * testSetBLogicProperty01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) blogicProperty:"blogicProperty"<br>
     *         (状態) blogicProperty:null<br>
     *         
     * <br>
     * 期待値：(戻り値) void:－<br>
     *         (状態変化) blogicProperty:"blogicProperty"<br>
     *         
     * <br>
     * 引数に指定した値がBLogicPropertyのblogicPropertyに正常に格納されること。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetBLogicProperty01() throws Exception {
        //ビジネスロジックプロパティ情報
        BLogicProperty blogicProperty = new BLogicProperty();

        //テスト実行
        blogicProperty.setBLogicProperty("blogicProperty");

        //テスト結果確認
        assertEquals(
            "blogicProperty",
            UTUtil.getPrivateField(blogicProperty, "blogicProperty"));
    }

    /**
     * testGetSource01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) なし:－<br>
     *         (状態) sourceOrDest:"source"<br>
     *         
     * <br>
     * 期待値：(戻り値) String:"source"<br>
     *         
     * <br>
     * BLogicPropertyに格納されているsourceOrDestを正常に取得すること。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetSource01() throws Exception {
        //ビジネスロジックプロパティ情報
        BLogicProperty blogicProperty = new BLogicProperty();

        //sourceOrDest設定
        UTUtil.setPrivateField(blogicProperty, "sourceOrDest", "source");

        //テスト実行・結果確認
        assertEquals("source", blogicProperty.getSource());
    }

    /**
     * testSetSource01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) source:"source"<br>
     *         (状態) sourceOrDest:null<br>
     *         
     * <br>
     * 期待値：(戻り値) void:－<br>
     *         (状態変化) sourceOrDest:"source"<br>
     *         
     * <br>
     * 引数に指定した値がBLogicPropertyのsourceOrDestに正常に格納されること。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetSource01() throws Exception {
        //ビジネスロジックプロパティ情報
        BLogicProperty blogicProperty = new BLogicProperty();

        //テスト実行
        blogicProperty.setSource("source");

        //テスト結果確認
        assertEquals("source", UTUtil.getPrivateField(blogicProperty,
                "sourceOrDest"));
    }

    /**
     * testGetDest01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) なし:－<br>
     *         (状態) sourceOrDest:"dest"<br>
     *         
     * <br>
     * 期待値：(戻り値) String:"dest"<br>
     *         
     * <br>
     * BLogicPropertyに格納されているsourceOrDestを正常に取得すること。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetDest01() throws Exception {
        //ビジネスロジックプロパティ情報
        BLogicProperty blogicProperty = new BLogicProperty();

        //sourceOrDest設定
        UTUtil.setPrivateField(blogicProperty, "sourceOrDest", "dest");

        //テスト実行・結果確認
        assertEquals("dest", blogicProperty.getDest());
    }

    /**
     * testSetDest01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) dest:"dest"<br>
     *         (状態) sourceOrDest:null<br>
     *         
     * <br>
     * 期待値：(戻り値) void:－<br>
     *         (状態変化) sourceOrDest:"dest"<br>
     *         
     * <br>
     * 引数に指定した値がBLogicPropertyのsourceOrDestに正常に格納されること。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetDest01() throws Exception {
        //ビジネスロジックプロパティ情報
        BLogicProperty blogicProperty = new BLogicProperty();

        //テスト実行
        blogicProperty.setDest("dest");

        //テスト結果確認
        assertEquals("dest", UTUtil.getPrivateField(blogicProperty,
                "sourceOrDest"));
    }

}
