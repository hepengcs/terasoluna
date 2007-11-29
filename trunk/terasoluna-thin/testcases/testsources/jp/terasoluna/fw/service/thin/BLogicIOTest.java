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

import java.util.ArrayList;
import java.util.List;

import jp.terasoluna.utlib.UTUtil;
import junit.framework.TestCase;

/**
 * {@link jp.terasoluna.fw.service.thin.BLogicIO} クラスの
 * ブラックボックステスト。
 * 
 * <p>
 * <h4>【クラスの概要】</h4>
 * ビジネスロジック入出力情報を保持するクラス。<br>
 * BLogicIOPlugInでビジネスロジック入出力情報定義ファイルであるblogic-io.xmlから、
 * アクションパス名とそのアクションが起動された時の入力情報、出力情報を保持する。
 * <p>
 * 
 * @see jp.terasoluna.fw.service.thin.BLogicIO
 */
public class BLogicIOTest extends TestCase {

    /**
     * このテストケースを実行する為の
     * GUI アプリケーションを起動する。
     * 
     * @param args java コマンドに設定されたパラメータ
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(BLogicIOTest.class);
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
    public BLogicIOTest(String name) {
        super(name);
    }

    /**
     * testGetInputBeanName01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) なし:－<br>
     *         (状態) inputBeanName:"inputBeanName"<br>
     *         
     * <br>
     * 期待値：(戻り値) String:"inputBeanName"<br>
     *         
     * <br>
     * BLogicIOに格納されているinputBeanNameを正常に取得すること。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetInputBeanName01() throws Exception {
        // ビジネスロジック入出力情報
        BLogicIO blogicIO = new BLogicIO();

        // path設定
        UTUtil.setPrivateField(blogicIO, "inputBeanName", "inputBeanName");

        // テスト実行・結果確認
        assertEquals("inputBeanName", blogicIO.getInputBeanName());
    }

    /**
     * testSetInputBeanName01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) inputBeanName:"inputBeanName"<br>
     *         (状態) inputBeanName:null<br>
     *         
     * <br>
     * 期待値：(戻り値) void:－<br>
     *         (状態変化) inputBeanName:"inputBeanName"<br>
     *         
     * <br>
     * 引数に指定した値がBLogicIOのinputBeanNameに正常に格納されること。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetInputBeanName01() throws Exception {
        // ビジネスロジック入出力情報
        BLogicIO blogicIO = new BLogicIO();

        // テスト実行
        blogicIO.setInputBeanName("inputBeanName");

        // テスト結果確認
        assertEquals("inputBeanName", UTUtil.getPrivateField(blogicIO,
                "inputBeanName"));
    }

    /**
     * testGetPath01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) なし:－<br>
     *         (状態) path:"path"<br>
     *         
     * <br>
     * 期待値：(戻り値) String:"path"<br>
     *         
     * <br>
     * BLogicIOに格納されているpathを正常に取得すること。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetPath01() throws Exception {
        // ビジネスロジック入出力情報
        BLogicIO blogicIO = new BLogicIO();

        // path設定
        UTUtil.setPrivateField(blogicIO, "path", "path");

        // テスト実行・結果確認
        assertEquals("path", blogicIO.getPath());
    }

    /**
     * testSetPath01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) path:"path"<br>
     *         (状態) path:null<br>
     *         
     * <br>
     * 期待値：(戻り値) void:－<br>
     *         (状態変化) path:"path"<br>
     *         
     * <br>
     * 引数に指定した値がBLogicIOのpathに正常に格納されること。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetPath01() throws Exception {
        // ビジネスロジック入出力情報
        BLogicIO blogicIO = new BLogicIO();

        // テスト実行
        blogicIO.setPath("path");

        // テスト結果確認
        assertEquals("path", UTUtil.getPrivateField(blogicIO, "path"));
    }

    /**
     * testGetBLogicParams01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) なし:－<br>
     *         (状態) blogicParams:List<BLogicProperty>オブジェクト<br>
     *         
     * <br>
     * 期待値：(戻り値) List:前提条件と同一のList<BLogicProperty>オブジェクト<br>
     *         
     * <br>
     * BLogicIOに格納されているblogicParamsを正常に取得すること。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetBLogicParams01() throws Exception {
        // ビジネスロジック入出力情報
        BLogicIO blogicIO = new BLogicIO();

        // blogicParams設定
        List<BLogicProperty> list = new ArrayList<BLogicProperty>();
        BLogicProperty blogicProperty = new BLogicProperty();
        list.add(blogicProperty);
        UTUtil.setPrivateField(blogicIO, "blogicParams", list);

        // テスト実行・結果確認
        assertSame(list, blogicIO.getBLogicParams());
    }

    /**
     * testGetBLogicResults01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) なし:－<br>
     *         (状態) blogicResults:List<BLogicProperty>オブジェクト<br>
     *         
     * <br>
     * 期待値：(戻り値) List:前提条件と同一のList<BLogicProperty>オブジェクト<br>
     *         
     * <br>
     * BLogicIOに格納されているblogicResultsを正常に取得すること。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetBLogicResults01() throws Exception {
        // ビジネスロジック入出力情報
        BLogicIO blogicIO = new BLogicIO();

        // blogicResults設定
        List<BLogicProperty> list = new ArrayList<BLogicProperty>();
        BLogicProperty blogicProperty = new BLogicProperty();
        list.add(blogicProperty);
        UTUtil.setPrivateField(blogicIO, "blogicResults", list);

        // テスト実行・結果確認
        assertSame(list, blogicIO.getBLogicResults());
    }

    /**
     * testSetBLogicParam01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) blogicParam:BlogicProperty[property="property"]<br>
     *         (状態) blogicParams:空のリスト<br>
     *         
     * <br>
     * 期待値：(戻り値) void:－<br>
     *         (状態変化) blogicParams:指定したBLogicPropertyインスタンスが1番目に格納されている<br>
     *         
     * <br>
     * 引数に指定した値がBLogicIOのblogicParamsに正常に格納されること。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetBLogicParam01() throws Exception {
        // ビジネスロジック入出力情報
        BLogicIO blogicIO = new BLogicIO();

        // BlogicProperty作成
        BLogicProperty blogicParam = new BLogicProperty();
        UTUtil.setPrivateField(blogicParam, "blogicProperty", "BlogicProperty");

        // テスト実行
        blogicIO.setBLogicParam(blogicParam);

        // テスト結果確認
        List resultList = (List) UTUtil.getPrivateField(blogicIO,
                "blogicParams");
        assertEquals(1, resultList.size());
        BLogicProperty resultBlogicProperty = (BLogicProperty) resultList
                .get(0);
        assertEquals("BlogicProperty", UTUtil.getPrivateField(
                resultBlogicProperty, "blogicProperty"));
    }

    /**
     * testSetBLogicParam02()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) blogicParam:null<br>
     *         (状態) blogicParams:1件設定されたリスト<br>
     *         
     * <br>
     * 期待値：(戻り値) void:－<br>
     *         (状態変化) blogicParams:nullが2番目に格納されている<br>
     *         
     * <br>
     * 引数に指定した値がBLogicIOのblogicParamsに正常に格納されること。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetBLogicParam02() throws Exception {
        // ビジネスロジック入出力情報
        BLogicIO blogicIO = new BLogicIO();

        // blogicParams設定
        List<BLogicProperty> list = new ArrayList<BLogicProperty>();
        BLogicProperty blogicProperty = new BLogicProperty();
        list.add(blogicProperty);
        UTUtil.setPrivateField(blogicIO, "blogicParams", list);
        
        // テスト実行
        blogicIO.setBLogicParam(null);

        // テスト結果確認
        List resultList = (List) UTUtil.getPrivateField(blogicIO,
                "blogicParams");
        assertEquals(2, resultList.size());
        assertNull(((List) UTUtil.getPrivateField(blogicIO, "blogicParams"))
                .get(1));

    }

    /**
     * testSetBLogicResult01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) blogicResult:BlogicProperty[property="property"]<br>
     *         (状態) blogicResult:空のリスト<br>
     *         
     * <br>
     * 期待値：(戻り値) void:－<br>
     *         (状態変化) blogicResults:指定したBLogicPropertyインスタンスが1番目に格納されている<br>
     *         
     * <br>
     * 引数に指定した値がBLogicIOのblogicResultsに正常に格納されること。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetBLogicResult01() throws Exception {
        // ビジネスロジック入出力情報
        BLogicIO blogicIO = new BLogicIO();

        // BlogicProperty作成
        BLogicProperty blogicParam = new BLogicProperty();
        UTUtil.setPrivateField(blogicParam, "blogicProperty", "BlogicProperty");

        // テスト実行
        blogicIO.setBLogicResult(blogicParam);

        // テスト結果確認
        List resultList = (List) UTUtil.getPrivateField(blogicIO,
                "blogicResults");
        assertEquals(1, resultList.size());
        BLogicProperty resultBlogicProperty = (BLogicProperty) resultList
                .get(0);
        assertEquals("BlogicProperty", UTUtil.getPrivateField(
                resultBlogicProperty, "blogicProperty"));
    }

    /**
     * testSetBLogicResult02()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) blogicResult:null<br>
     *         (状態) blogicResult:1件設定されたリスト<br>
     *         
     * <br>
     * 期待値：(戻り値) void:－<br>
     *         (状態変化) blogicResults:nullが2番目に格納されている<br>
     *         
     * <br>
     * 引数に指定した値がBLogicIOのblogicResultsに正常に格納されること。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetBLogicResult02() throws Exception {
        // ビジネスロジック入出力情報
        BLogicIO blogicIO = new BLogicIO();

        // blogicResults設定
        List<BLogicProperty> list = new ArrayList<BLogicProperty>();
        BLogicProperty blogicProperty = new BLogicProperty();
        list.add(blogicProperty);
        UTUtil.setPrivateField(blogicIO, "blogicResults", list);
        
        // テスト実行
        blogicIO.setBLogicResult(null);

        // テスト結果確認
        List resultList = (List) UTUtil.getPrivateField(blogicIO,
                "blogicResults");
        assertEquals(2, resultList.size());
        assertNull(((List) UTUtil.getPrivateField(blogicIO, "blogicResults"))
                .get(1));

    }

}
