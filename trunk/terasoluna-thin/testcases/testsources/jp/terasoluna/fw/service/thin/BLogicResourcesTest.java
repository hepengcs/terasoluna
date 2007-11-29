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

import java.util.HashMap;
import java.util.Map;

import jp.terasoluna.utlib.UTUtil;
import junit.framework.TestCase;

/**
 * {@link jp.terasoluna.fw.service.thin.BLogicResources} クラスの
 * ブラックボックステスト。
 * 
 * <p>
 * <h4>【クラスの概要】</h4>
 * ビジネスロジック入出力情報を保持するクラス。<br>
 * blogic-io.xmlから読み込まれた設定情報はこのクラスが親となり保持する。
 * 個々の設定情報はBLogicIO、BLogicPropertyのインスタンスとして保持する。
 * <p>
 * 
 * @see jp.terasoluna.fw.service.thin.BLogicResources
 */
public class BLogicResourcesTest extends TestCase {

    /**
     * このテストケースを実行する為の
     * GUI アプリケーションを起動する。
     * 
     * @param args java コマンドに設定されたパラメータ
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(BLogicResourcesTest.class);
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
    public BLogicResourcesTest(String name) {
        super(name);
    }

    /**
     * testGetBLogicIO01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) path:"path"<br>
     *         (状態) bLogicIO:key："path"、value：BLogicIO（[path="path"]）<br>
     *         
     * <br>
     * 期待値：(戻り値) BLogicIO:[path="path"]<br>
     *         
     * <br>
     * BLogicResourcesに格納されているblogicIOから指定されたKeyの内容を正常に取得すること。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetBLogicIO01() throws Exception {
        //ビジネスロジック情報の保持
        BLogicResources blogicResources = new BLogicResources();

        //テスト値設定
        Map<String, BLogicIO> map = new HashMap<String, BLogicIO>();
        BLogicIO bLogicIO = new BLogicIO();
        UTUtil.setPrivateField(bLogicIO, "path", "path");
        map.put("path", bLogicIO);

        //blogicIO設定
        UTUtil.setPrivateField(blogicResources, "blogicIO", map);

        //テスト実行・結果確認
        BLogicIO resultBLogicIO = blogicResources.getBLogicIO("path");
        assertEquals("path", UTUtil.getPrivateField(resultBLogicIO, "path"));
    }

    /**
     * testGetBLogicIO02()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) path:"path"<br>
     *         (状態) bLogicIO:key："path"、value：null<br>
     *         
     * <br>
     * 期待値：(戻り値) BLogicIO:null<br>
     *         
     * <br>
     * BLogicResourcesに格納されているblogicIOから指定されたKeyの内容がnullの場合、
     * nullを正常に取得すること。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetBLogicIO02() throws Exception {
        //ビジネスロジック情報の保持
        BLogicResources blogicResources = new BLogicResources();

        //テスト値設定
        Map<String, BLogicIO> map = new HashMap<String, BLogicIO>();
        map.put("path", null);

        //blogicIO設定
        UTUtil.setPrivateField(blogicResources, "blogicIO", map);

        //テスト実行・結果確認
        assertNull(blogicResources.getBLogicIO("path"));
    }

    /**
     * testGetBLogicIO03()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) path:null<br>
     *         (状態) bLogicIO:key："path"、value：BLogicIO（[path="path"]）<br>
     *         
     * <br>
     * 期待値：(戻り値) BLogicIO:null<br>
     *         
     * <br>
     * 引数がnullの場合、正常に終了すること。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetBLogicIO03() throws Exception {
        //ビジネスロジック情報の保持
        BLogicResources blogicResources = new BLogicResources();

        //テスト値設定
        Map<String, BLogicIO> map = new HashMap<String, BLogicIO>();
        BLogicIO bLogicIO = new BLogicIO();
        UTUtil.setPrivateField(bLogicIO, "path", "path");
        map.put("path", bLogicIO);

        //blogicIO設定
        UTUtil.setPrivateField(blogicResources, "blogicIO", map);
        
        //テスト実行・結果確認
        assertNull(blogicResources.getBLogicIO(null));
    }

    /**
     * testGetBLogicIO04()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) path:""<br>
     *         (状態) bLogicIO:key："path"、value：BLogicIO（[path="path"]）<br>
     *         
     * <br>
     * 期待値：(戻り値) BLogicIO:null<br>
     *         
     * <br>
     * 引数が空文字の場合、正常に終了すること。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetBLogicIO04() throws Exception {
        //ビジネスロジック情報の保持
        BLogicResources blogicResources = new BLogicResources();
        
        //テスト値設定
        Map<String, BLogicIO> map = new HashMap<String, BLogicIO>();
        BLogicIO bLogicIO = new BLogicIO();
        UTUtil.setPrivateField(bLogicIO, "path", "path");
        map.put("path", bLogicIO);

        //blogicIO設定
        UTUtil.setPrivateField(blogicResources, "blogicIO", map);

        //テスト実行・結果確認
        assertNull(blogicResources.getBLogicIO(""));
    }

    /**
     * testSetBLogicIO01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) blogicIO:not null（[path="path"])<br>
     *         (状態) blogicIO:null<br>
     *         
     * <br>
     * 期待値：(戻り値) void:－<br>
     *         (状態変化) 例外:－<br>
     *         (状態変化) blogicIO:key："path"、value：BLogicIO([path="path"])が設定される。<br>
     *         
     * <br>
     * 引数に指定したBlogicIOのpathが"path"の時、BLogicResourcesのblogicIOにKeyが"path"として正常に格納されること。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetBLogicIO01() throws Exception {
        //ビジネスロジック情報の保持
        BLogicResources blogicResources = new BLogicResources();

        //テスト値設定
        BLogicIO bLogicIO = new BLogicIO();
        UTUtil.setPrivateField(bLogicIO, "path", "path");

        //テスト実行
        blogicResources.setBLogicIO(bLogicIO);

        //テスト結果確認
        BLogicIO resultBLogicIO =
            (BLogicIO)
                ((Map) UTUtil.getPrivateField(blogicResources, "blogicIO")).get(
                "path");
        assertEquals("path", UTUtil.getPrivateField(resultBLogicIO, "path"));
    }

    /**
     * testSetBLogicIO02()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) blogicIO:not null（[path=null])<br>
     *         (状態) blogicIO:null<br>
     *         
     * <br>
     * 期待値：(戻り値) void:－<br>
     *         (状態変化) 例外:－<br>
     *         (状態変化) blogicIO:key：null、BLogicIO([path=null])が設定される。<br>
     *         
     * <br>
     * 引数に指定したBlogicIOのpathがnullの時、BLogicResourcesのblogicIOにKeyがnullとして正常に格納されること。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetBLogicIO02() throws Exception {
        //ビジネスロジック情報の保持
        BLogicResources blogicResources = new BLogicResources();

        //テスト値設定
        BLogicIO bLogicIO = new BLogicIO();
        UTUtil.setPrivateField(bLogicIO, "path", null);

        //テスト実行
        blogicResources.setBLogicIO(bLogicIO);

        //テスト結果確認
        Map resultMap = (Map) UTUtil.getPrivateField(blogicResources,
                "blogicIO");
        assertTrue(resultMap.containsKey(null));

        BLogicIO resultBLogicIO = (BLogicIO) resultMap.get(null);
        assertNull(UTUtil.getPrivateField(resultBLogicIO, "path"));
    }

    /**
     * testSetBLogicIO03()
     * <br><br>
     * 
     * (異常系)
     * <br>
     * 観点：C、G
     * <br><br>
     * 入力値：(引数) blogicIO:null<br>
     *         (状態) blogicIO:null<br>
     *         
     * <br>
     * 期待値：(戻り値) void:－<br>
     *         (状態変化) 例外:NullPointerException<br>
     *         (状態変化) blogicIO:－<br>
     *         
     * <br>
     * 引数がnullの場合、NullPointerExceptionが発生すること。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetBLogicIO03() throws Exception {
        //ビジネスロジック情報の保持
        BLogicResources blogicResources = new BLogicResources();

        //テスト実行
        try {
            blogicResources.setBLogicIO(null);
            fail();
        } catch (NullPointerException e) {
            // テスト結果確認
        	return;
        }
    }

}
