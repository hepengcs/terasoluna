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
 * {@link jp.terasoluna.fw.service.thin.BLogicResult} クラスの
 * ブラックボックステスト。
 * 
 * <p>
 * <h4>【クラスの概要】</h4>
 * ビジネスロジック出力情報クラス。
 * <p>
 * 
 * @see jp.terasoluna.fw.service.thin.BLogicResult
 */
public class BLogicResultTest extends TestCase {

    /**
     * このテストケースを実行する為の GUI アプリケーションを起動する。
     * 
     * @param args
     *            java コマンドに設定されたパラメータ
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(BLogicResultTest.class);
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
    public BLogicResultTest(String name) {
        super(name);
    }

    /**
     * testGetResultObject01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) なし:－<br>
     *         (状態) resultObject:"resultObject"<br>
     *         
     * <br>
     * 期待値：(戻り値) Object:"resultObject"<br>
     *         
     * <br>
     * BLogicResultに格納されているresultObjectを正常に取得すること。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetResultObject01() throws Exception {
        // BLogic出力情報
        BLogicResult blogicResult = new BLogicResult();

        // テスト値設定
        String str = "resultObject";

        // resultObject設定
        UTUtil.setPrivateField(blogicResult, "resultObject", str);

        // テスト実施・判定
        assertEquals("resultObject", blogicResult.getResultObject());
    }

    /**
     * testSetResultObject01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) resultObject:"resultObject"<br>
     *         (状態) resultObject:null<br>
     *         
     * <br>
     * 期待値：(戻り値) void:－<br>
     *         (状態変化) resultObject:"resultObject"<br>
     *         
     * <br>
     * 引数に指定した値がBLogicResultのresultObjectに正常に格納されること。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetResultObject01() throws Exception {
        // BLogic出力情報
        BLogicResult blogicResult = new BLogicResult();

        // テスト値設定
        String str = "resultObject";

        // テスト実施
        blogicResult.setResultObject(str);

        // 判定
        assertEquals("resultObject", UTUtil.getPrivateField(blogicResult,
                "resultObject"));
    }

    /**
     * testGetResultString01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) なし:－<br>
     *         (状態) resultString:"resultString"<br>
     *         
     * <br>
     * 期待値：(戻り値) String:"resultString"<br>
     *         
     * <br>
     * BLogicResultに格納されているresultStringを正常に取得すること。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetResultString01() throws Exception {
        // BLogic出力情報
        BLogicResult blogicResult = new BLogicResult();

        // テスト値設定
        String str = "resultString";

        // resultString設定
        UTUtil.setPrivateField(blogicResult, "resultString", str);

        // テスト実施・判定
        assertEquals("resultString", blogicResult.getResultString());
    }

    /**
     * testSetResultString01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) resultString:"resultString"<br>
     *         (状態) resultString:null<br>
     *         
     * <br>
     * 期待値：(戻り値) void:－<br>
     *         (状態変化) resultString:"resultString"<br>
     *         
     * <br>
     * 引数に指定した値がBLogicResultのresultStringに正常に格納されること。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetResultString01() throws Exception {
        // BLogic出力情報
        BLogicResult blogicResult = new BLogicResult();

        // テスト値設定
        String str = "resultString";

        // テスト実施
        blogicResult.setResultString(str);

        // 判定
        assertEquals("resultString", UTUtil.getPrivateField(blogicResult,
                "resultString"));

    }

    /**
     * testGetErrors01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) なし:－<br>
     *         (状態) errors:("property",BLogicMessage("key","value"))<br>
     *         
     * <br>
     * 期待値：(戻り値) BLogicMessages:("property",BLogicMessage("key","value"))<br>
     *         
     * <br>
     * BLogicResultに格納されているerrorsを正常に取得すること。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetErrors01() throws Exception {
        // BLogic出力情報
        BLogicResult blogicResult = new BLogicResult();

        // テスト値設定
        // BLogicMessage生成
        BLogicMessage error = new BLogicMessage("key", "value");
        
        // BLogicMessages生成
        BLogicMessages errors = new BLogicMessages();
        errors.add("property", error);
        
        // messages設定
        UTUtil.setPrivateField(blogicResult, "errors", errors);

        // テスト実施・判定
        assertSame(errors, blogicResult.getErrors());
    }

    /**
     * testSetErrors01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) paramErrors:("property",BLogicMessage("key","value"))<br>
     *         (状態) paramErrors:null<br>
     *         
     * <br>
     * 期待値：(戻り値) void:－<br>
     *         (状態変化) errors:("property",BLogicMessage("key","value"))<br>
     *         
     * <br>
     * 引数に指定した値がBLogicResultのerrorsに正常に格納されること。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetErrors01() throws Exception {
        // BLogic出力情報
        BLogicResult blogicResult = new BLogicResult();

        // テスト値設定
        // BLogicMessage生成
        BLogicMessage error = new BLogicMessage("key", "value");
        
        // BLogicMessages生成
        BLogicMessages errors = new BLogicMessages();
        errors.add("property", error);

        // テスト実施
        blogicResult.setErrors(errors);

        // 判定
        assertSame(errors, UTUtil.getPrivateField(blogicResult, "errors"));

    }

    /**
     * testGetMessages01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) なし:－<br>
     *         (状態) messages:("property",BLogicMessage("key","value"))<br>
     *         
     * <br>
     * 期待値：(戻り値) BLogicMessages:("property",BLogicMessage("key","value"))<br>
     *         
     * <br>
     * BLogicResultに格納されているmessagesを正常に取得すること。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetMessages01() throws Exception {
        // BLogic出力情報
        BLogicResult blogicResult = new BLogicResult();

        // テスト値設定
        // BLogicMessage生成
        BLogicMessage message = new BLogicMessage("key", "value");
        
        // BLogicMessages生成
        BLogicMessages messages = new BLogicMessages();
        messages.add("property", message);

        // messages設定
        UTUtil.setPrivateField(blogicResult, "messages", messages);

        // テスト実施・判定
        assertSame(messages, blogicResult.getMessages());
    }

    /**
     * testSetMessages01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) paramMessages:("property",BLogicMessage("key","value"))<br>
     *         (状態) paramMessages:null<br>
     *         
     * <br>
     * 期待値：(戻り値) void:－<br>
     *         (状態変化) messages:("property",BLogicMessage("key","value"))<br>
     *         
     * <br>
     * 引数に指定した値がBLogicResultのmessagesに正常に格納されること。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetMessages01() throws Exception {
        // 前処理
        // BLogic出力情報
        BLogicResult blogicResult = new BLogicResult();

        // テスト値設定
        // BLogicMessage生成
        BLogicMessage message = new BLogicMessage("key", "value");
        
        // BLogicMessages生成
        BLogicMessages messages = new BLogicMessages();
        messages.add("property", message);

        // テスト実施
        blogicResult.setMessages(messages);

        // 判定
        assertSame(messages, UTUtil.getPrivateField(blogicResult, "messages"));
    }

}
