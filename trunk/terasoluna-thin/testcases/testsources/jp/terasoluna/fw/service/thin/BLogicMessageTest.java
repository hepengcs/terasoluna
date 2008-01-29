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
 * {@link jp.terasoluna.fw.service.thin.BLogicMessage} クラスの
 * ブラックボックステスト。
 * 
 * <p>
 * <h4>【クラスの概要】</h4>
 * メッセージ情報クラス。<br>
 * ビジネスロジックの実行結果を受けて、メッセージを設定する際に生成する。
 * <p>
 * 
 * @see jp.terasoluna.fw.service.thin.BLogicMessage
 */
public class BLogicMessageTest extends TestCase {

    /**
     * このテストケースを実行する為の
     * GUI アプリケーションを起動する。
     * 
     * @param args java コマンドに設定されたパラメータ
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(BLogicMessageTest.class);
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
    public BLogicMessageTest(String name) {
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
     * 入力値：(引数) なし:－<br>
     *         (状態) key:"key"<br>
     *         
     * <br>
     * 期待値：(戻り値) String:"key"<br>
     *         
     * <br>
     * BLogicMessageに格納されているkeyを正常に取得すること。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetKey01() throws Exception {
        // BLogic出力情報
        BLogicMessage blogicMessage = new BLogicMessage(null);

        // テスト値設定
        String str = "key";
        
        // key設定
        UTUtil.setPrivateField(blogicMessage, "key", str);

        // テスト実施・判定
        assertEquals("key", blogicMessage.getKey());
    }

    /**
     * testGetValues01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) なし:－<br>
     *         (状態) values:{"value1", "value2", "value3"}<br>
     *         
     * <br>
     * 期待値：(戻り値) Object[]:{"value1", "value2", "value3"}<br>
     *         
     * <br>
     * BLogicMessageに格納されているvaluesを正常に取得すること。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetValues01() throws Exception {
        // BLogic出力情報
        BLogicMessage blogicMessage = new BLogicMessage(null);

        // テスト値設定
        Object[] values = {"value1", "value2", "value3"};
        
        // values設定
        UTUtil.setPrivateField(blogicMessage, "values", values);

        // テスト実施
        Object[] results = blogicMessage.getValues();
        
        // 判定
        for (int i = 0; i < values.length; i++) {
            assertEquals(values[i], results[i]);
        }
        assertEquals(values.length, results.length);
    }

    /**
     * testIsResource01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) なし:－<br>
     *         (状態) resource:true<br>
     *         
     * <br>
     * 期待値：(戻り値) boolean:true<br>
     *         
     * <br>
     * BLogicMessageに格納されているresourceを正常に取得すること。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testIsResource01() throws Exception {
        // テスト値設定
        BLogicMessage blogicMessage = new BLogicMessage(null);
        
        // テスト値設定
        // resource設定
        UTUtil.setPrivateField(blogicMessage, "resource", true);

        // テスト実施・判定
        assertTrue(blogicMessage.isResource());
    }

    /**
     * testBLogicMessageString01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) key:"key"<br>
     *         
     * <br>
     * 期待値：(戻り値) なし:－<br>
     *         (状態変化) key:"key"<br>
     *         (状態変化) values:null<br>
     *         (状態変化) resource:true<br>
     *         
     * <br>
     * 引数keyが"key"の場合、生成されたBLogicMessageのkeyに"key"が格納されること。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testBLogicMessageString01() throws Exception {
        // テスト実施
        BLogicMessage blogicMessage = new BLogicMessage("key");

        // 判定
        assertEquals("key", UTUtil.getPrivateField(blogicMessage, "key"));
        assertNull(UTUtil.getPrivateField(blogicMessage, "values"));
        assertTrue((Boolean) UTUtil.getPrivateField(blogicMessage, "resource"));
    }

    /**
     * testBLogicMessageString02()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) key:""<br>
     *         
     * <br>
     * 期待値：(戻り値) なし:－<br>
     *         (状態変化) key:""<br>
     *         (状態変化) values:null<br>
     *         (状態変化) resource:true<br>
     *         
     * <br>
     * 引数keyが空文字の場合、生成されたBLogicMessageのkeyに空文字が格納されること。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testBLogicMessageString02() throws Exception {
        // テスト実施
        BLogicMessage blogicMessage = new BLogicMessage("");

        // 判定
        assertEquals("", UTUtil.getPrivateField(blogicMessage, "key"));
        assertNull(UTUtil.getPrivateField(blogicMessage, "values"));
        assertTrue((Boolean) UTUtil.getPrivateField(blogicMessage, "resource"));
    }

    /**
     * testBLogicMessageString03()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) key:null<br>
     *         
     * <br>
     * 期待値：(戻り値) なし:－<br>
     *         (状態変化) key:null<br>
     *         (状態変化) values:null<br>
     *         (状態変化) resource:true<br>
     *         
     * <br>
     * 引数keyがnullの場合、生成されたBLogicMessageのkeyにnullが格納されること。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testBLogicMessageString03() throws Exception {
        // テスト実施
        BLogicMessage blogicMessage = new BLogicMessage(null);

        // 判定
        assertNull(UTUtil.getPrivateField(blogicMessage, "key"));
        assertNull(UTUtil.getPrivateField(blogicMessage, "values"));
        assertTrue((Boolean) UTUtil.getPrivateField(blogicMessage, "resource"));
    }

    /**
     * testBLogicMessageStringObject01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) key:"key"<br>
     *         (引数) values:{"value1", "value2", "value3"}<br>
     *         
     * <br>
     * 期待値：(戻り値) なし:－<br>
     *         (状態変化) key:"key"<br>
     *         (状態変化) values:{"value1", "value2", "value3"}<br>
     *         (状態変化) resource:true<br>
     *         
     * <br>
     * 引数keyが"key"、valuesが複数オブジェクトの場合、生成されたBLogicMessageのkeyに"key"、valuesに指定のオブジェクトが格納されること。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testBLogicMessageStringObject01() throws Exception {
        // テスト値設定
        Object[] values = {"value1", "value2", "value3"};
        
        // テスト実施
        BLogicMessage blogicMessage = new BLogicMessage("key", values);

        // 判定
        assertEquals("key", UTUtil.getPrivateField(blogicMessage, "key"));
        Object[] results = (Object[]) UTUtil.getPrivateField(blogicMessage,
                "values");
        for (int i = 0; i < values.length; i++) {
            assertEquals(values[i], results[i]);
        }
        assertEquals(values.length, results.length);
        assertTrue((Boolean) UTUtil.getPrivateField(blogicMessage, "resource"));
        
    }

    /**
     * testBLogicMessageStringObject02()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) key:""<br>
     *         (引数) values:{"value1"}<br>
     *         
     * <br>
     * 期待値：(戻り値) なし:－<br>
     *         (状態変化) key:""<br>
     *         (状態変化) values:{"value1"}<br>
     *         (状態変化) resource:true<br>
     *         
     * <br>
     * 引数keyが空文字、valuesが１つのオブジェクトの場合、生成されたBLogicMessageのkeyに空文字、valuesに指定のオブジェクトが格納されること。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testBLogicMessageStringObject02() throws Exception {
        // テスト値設定
        Object[] values = {"value1"};
        
        // テスト実施
        BLogicMessage blogicMessage = new BLogicMessage("", values);

        // 判定
        assertEquals("", UTUtil.getPrivateField(blogicMessage, "key"));
        Object[] results = ((Object[]) UTUtil.getPrivateField(blogicMessage,
                "values"));
        assertEquals(1, results.length);
        assertEquals("value1", results[0]);
        assertTrue((Boolean) UTUtil.getPrivateField(blogicMessage, "resource"));
    }

    /**
     * testBLogicMessageStringObject03()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) key:null<br>
     *         (引数) values:null<br>
     *         
     * <br>
     * 期待値：(戻り値) なし:－<br>
     *         (状態変化) key:null<br>
     *         (状態変化) values:null<br>
     *         (状態変化) resource:true<br>
     *         
     * <br>
     * 引数keyがnull、valuesがnullの場合、生成されたBLogicMessageのkeyにnull、valuesにnullが格納されること。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testBLogicMessageStringObject03() throws Exception {        
        // テスト実施
        BLogicMessage blogicMessage = new BLogicMessage(null, (Object[]) null);

        // 判定
        assertNull(UTUtil.getPrivateField(blogicMessage, "key"));
        assertNull(UTUtil.getPrivateField(blogicMessage, "values"));
        assertTrue((Boolean) UTUtil.getPrivateField(blogicMessage, "resource"));
    }

    /**
     * testBLogicMessageStringObject04()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) key:"key"<br>
     *         (引数) values:{ }（空の配列）<br>
     *         
     * <br>
     * 期待値：(戻り値) なし:－<br>
     *         (状態変化) key:"key"<br>
     *         (状態変化) values:{ }<br>
     *         (状態変化) resource:true<br>
     *         
     * <br>
     * 引数valuesが空の配列の場合、生成されたBLogicMessageのvaluesに空の配列が格納されること。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testBLogicMessageStringObject04() throws Exception {
        // テスト値設定
        Object[] values = new Object[] {};
        
        // テスト実施
        BLogicMessage blogicMessage = new BLogicMessage("key", values);

        // 判定
        assertEquals("key", UTUtil.getPrivateField(blogicMessage, "key"));
        assertEquals(0, ((Object[]) UTUtil.getPrivateField(blogicMessage,
                "values")).length);
        assertTrue((Boolean) UTUtil.getPrivateField(blogicMessage, "resource"));
    }

    /**
     * testBLogicMessageStringboolean01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) key:"key"<br>
     *         (引数) resource:true<br>
     *         
     * <br>
     * 期待値：(戻り値) なし:－<br>
     *         (状態変化) key:"key"<br>
     *         (状態変化) resource:true<br>
     *         
     * <br>
     * 引数keyが"key"、resourceがtrueの場合、生成されたBLogicMessageのkeyに"key"、resourceにtrueが格納されること。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testBLogicMessageStringboolean01() throws Exception {
        // テスト実施
        BLogicMessage blogicMessage = new BLogicMessage("key", true);

        // 判定
        assertEquals("key", UTUtil.getPrivateField(blogicMessage, "key"));
        assertTrue((Boolean) UTUtil.getPrivateField(blogicMessage, "resource"));
    }

    /**
     * testBLogicMessageStringboolean02()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A、C
     * <br><br>
     * 入力値：(引数) key:""<br>
     *         (引数) resource:false<br>
     *         
     * <br>
     * 期待値：(戻り値) なし:－<br>
     *         (状態変化) key:""<br>
     *         (状態変化) resource:false<br>
     *         
     * <br>
     * 引数keyが空文字、resourceがfalseの場合、生成されたBLogicMessageのkeyに空文字、"、resourceにfalseが格納されること。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testBLogicMessageStringboolean02() throws Exception {
        // テスト実施
        BLogicMessage blogicMessage = new BLogicMessage("", false);

        // 判定
        assertEquals("", UTUtil.getPrivateField(blogicMessage, "key"));
        assertFalse((Boolean) UTUtil.getPrivateField(blogicMessage, "resource"));
    }

    /**
     * testBLogicMessageStringboolean03()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) key:null<br>
     *         (引数) resource:true<br>
     *         
     * <br>
     * 期待値：(戻り値) なし:－<br>
     *         (状態変化) key:null<br>
     *         (状態変化) resource:true<br>
     *         
     * <br>
     * 引数keyがnullの場合、生成されたBLogicMessageのkeyにnullが格納されること。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testBLogicMessageStringboolean03() throws Exception {
        // テスト実施
        BLogicMessage blogicMessage = new BLogicMessage(null, true);

        // 判定
        assertNull(UTUtil.getPrivateField(blogicMessage, "key"));
        assertTrue((Boolean) UTUtil.getPrivateField(blogicMessage, "resource"));
    }

}
