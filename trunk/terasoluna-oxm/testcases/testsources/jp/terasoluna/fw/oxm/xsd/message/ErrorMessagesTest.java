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

package jp.terasoluna.fw.oxm.xsd.message;

import java.util.ArrayList;
import java.util.List;

import jp.terasoluna.utlib.LogUTUtil;
import jp.terasoluna.utlib.UTUtil;
import jp.terasoluna.fw.oxm.xsd.message.ErrorMessage;
import jp.terasoluna.fw.oxm.xsd.message.ErrorMessages;
import junit.framework.TestCase;

/**
 * {@link jp.terasoluna.fw.oxm.xsd.message.ErrorMessages} クラスの
 * ブラックボックステスト。
 * 
 * <p>
 * <h4>【クラスの概要】</h4>
 * エラーメッセージのリストを保持するクラス。<br>
 * 前提条件：this.errorMessages属性はNull値にならない
 * <p>
 * 
 * @see jp.terasoluna.fw.oxm.xsd.message.ErrorMessages
 */
public class ErrorMessagesTest extends TestCase {

    /**
     * このテストケースを実行する為の
     * GUI アプリケーションを起動する。
     * 
     * @param args java コマンドに設定されたパラメータ
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(ErrorMessagesTest.class);
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
    public ErrorMessagesTest(String name) {
        super(name);
    }

    /**
     * testAdd01()
     * <br><br>
     * 
     * (異常系)
     * <br>
     * 観点：A,G
     * <br><br>
     * 入力値：(引数) errorMessage:null<br>
     *         (状態) this.errorMessage:not null<br>
     *                (空のリスト)<br>
     *         
     * <br>
     * 期待値：(状態変化) 例外:IllegalArgumentException(
     *          "ErrorMessages instance cannot add null object.")<br>
     *         (状態変化) ログ:ログレベル：エラー<br>
     *                    ErrorMessages instance cannot add null object.<br>
     *         (状態変化) this.errorMessages:not null<br>
     *                    (nullの要素)<br>
     *         
     * <br>
     * errorMessagesに要素が空の状態で、Nullのオブジェクトを追加するテスト。
     * 例外を投げる。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testAdd01() throws Exception {
        // 前処理
        ErrorMessages errorMessages = new ErrorMessages();
        UTUtil.setPrivateField(
                errorMessages, "errorMessages", new ArrayList<ErrorMessage>());

        // テスト実施
        try {
            errorMessages.add(null);
            fail();
        } catch (IllegalArgumentException e) {
            // OK
            String expect = "ErrorMessages instance cannot add null object.";
            assertEquals(expect, e.getMessage());
            assertTrue(LogUTUtil.checkError(expect));
        }
    }

    /**
     * testAdd02()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) errorMessage:not null<br>
     *                (key="key"<br>
     *                field="field"<br>
     *                replaceValues=<br>
     *                {"rv1","rv2","rv3"})<br>
     *         (状態) this.errorMessage:not null<br>
     *                (空のリスト)<br>
     *         
     * <br>
     * 期待値：(状態変化) this.errorMessages:not null<br>
     *                    (key="key"<br>
     *                    field="field"<br>
     *                    replaceValues=<br>
     *                    {"rv1","rv2","rv3"},<br>
     *                    nullの要素)<br>
     *         
     * <br>
     * errorMessagesに要素が空の状態で、NotNullのオブジェクトを追加するテスト。
     * NotNullのオブジェクトがerrorMessagesに追加される。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testAdd02() throws Exception {
        // 前処理
        ErrorMessages errorMessages = new ErrorMessages();
        UTUtil.setPrivateField(
                errorMessages, "errorMessages", new ArrayList<ErrorMessage>());

        ErrorMessage errorMessage = new ErrorMessage(
                "key", "field", new String[]{"rv1", "rv2", "rv3"});
        
        // テスト実施
        errorMessages.add(errorMessage);

        // 判定
        List results = (List) UTUtil.getPrivateField(
                errorMessages, "errorMessages");
        ErrorMessage result = (ErrorMessage) results.get(0);
        assertEquals(1, results.size());
        assertEquals("key", result.getKey());
        assertEquals("field", result.getField());
        assertEquals(3, result.getReplaceValues().length);
        assertEquals("rv1", result.getReplaceValues()[0]);
        assertEquals("rv2", result.getReplaceValues()[1]);
        assertEquals("rv3", result.getReplaceValues()[2]);
    }

    /**
     * testAdd03()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) errorMessage:not null<br>
     *                (key="key2"<br>
     *                field="field2"<br>
     *                replaceValues=<br>
     *                {"2rv1","2rv2","2rv3"})<br>
     *         (状態) this.errorMessage:not null<br>
     *                ({key="key1", field="field1", 
     *                replaceValues={"1rv1","1rv2","1rv3"}})<br>
     *         
     * <br>
     * 期待値：(状態変化) this.errorMessages:not null<br>
     *                    ({key="key1", field="field1", 
     *                    replaceValues={"1rv1","1rv2","1rv3"}},<br>
     *                    {key="key2", field="field2", 
     *                    replaceValues={"2rv1","2rv2","2rv3"}}<br>
     *         
     * <br>
     * errorMessagesに要素が１つある状態で、NotNullのオブジェクトを
     * 追加するテスト。
     * NotNullのオブジェクトがerrorMessagesの最後の要素に追加される。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testAdd03() throws Exception {
        // 前処理 --------------------------------------------------------------
        ErrorMessages errorMessages = new ErrorMessages();
        
        // 属性
        List<ErrorMessage> errorList = new ArrayList<ErrorMessage>();
        errorList.add(new ErrorMessage(
                "key1", "field1", new String[]{"1rv1", "1rv2", "1rv3"}));
        UTUtil.setPrivateField(
                errorMessages, "errorMessages", errorList);

        // メソッドの引数
        ErrorMessage errorMessage = new ErrorMessage(
                "key2", "field2", new String[]{"2rv1", "2rv2", "2rv3"});
        
        // テスト実施 ----------------------------------------------------------
        errorMessages.add(errorMessage);

        // 判定 ----------------------------------------------------------------
        List results = (List) UTUtil.getPrivateField(
                errorMessages, "errorMessages");
        assertEquals(2, results.size());
        ErrorMessage result1 = (ErrorMessage) results.get(0);
        assertEquals("key1", result1.getKey());
        assertEquals("field1", result1.getField());
        assertEquals(3, result1.getReplaceValues().length);
        assertEquals("1rv1", result1.getReplaceValues()[0]);
        assertEquals("1rv2", result1.getReplaceValues()[1]);
        assertEquals("1rv3", result1.getReplaceValues()[2]);
        
        ErrorMessage result2 = (ErrorMessage) results.get(1);
        assertEquals("key2", result2.getKey());
        assertEquals("field2", result2.getField());
        assertEquals(3, result2.getReplaceValues().length);
        assertEquals("2rv1", result2.getReplaceValues()[0]);
        assertEquals("2rv2", result2.getReplaceValues()[1]);
        assertEquals("2rv3", result2.getReplaceValues()[2]);
    }

    /**
     * testAdd04()
     * <br><br>
     * 
     * (正常系) 
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) errorMessage:not null<br>
     *                (key="key4"<br>
     *                field="field4"<br>
     *                replaceValues=<br>
     *                {"4rv1","4rv2","4rv3"})<br>
     *         (状態) this.errorMessage:not null<br>
     *                ({key="key1", field="field1", 
     *                replaceValues={"1rv1","1rv2","1rv3"}},
     *                {key="key2", field="field2", 
     *                replaceValues={"2rv1","2rv2","2rv3"}},<br>
     *                {key="key3", field="field3", 
     *                replaceValues={"3rv1","3rv2","3rv3"}}<br>
     *                )<br>
     *         
     * <br>
     * 期待値：(状態変化) this.errorMessages:not null<br>
     *                    ({key="key1", field="field1", 
     *                    replaceValues={"1rv1","1rv2","1rv3"},},<br>
     *                    {key="key2", field="field2", 
     *                    replaceValues={"2rv1","2rv2","2rv3"},},<br>
     *                    {key="key3", field="field3", 
     *                    replaceValues={"3rv1","3rv2","3rv3"}},<br>
     *                    {key="key4", field="field4", 
     *                    replaceValues={"4rv1","4rv2","4rv3"}}<br>
     *         
     * <br>
     * errorMessagesに要素が３つある状態で、
     * NotNullのオブジェクトを追加するテスト。
     * NotNullのオブジェクトがerrorMessagesの最後の要素に追加される。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testAdd04() throws Exception {
        // 前処理 --------------------------------------------------------------
        ErrorMessages errorMessages = new ErrorMessages();
        
        // 属性
        List<ErrorMessage> errorList = new ArrayList<ErrorMessage>();
        errorList.add(new ErrorMessage(
                "key1", "field1", new String[]{"1rv1", "1rv2", "1rv3"}));
        errorList.add(new ErrorMessage(
                "key2", "field2", new String[]{"2rv1", "2rv2", "2rv3"}));
        errorList.add(new ErrorMessage(
                "key3", "field3", new String[]{"3rv1", "3rv2", "3rv3"}));
        UTUtil.setPrivateField(
                errorMessages, "errorMessages", errorList);

        // メソッドの引数
        ErrorMessage errorMessage = new ErrorMessage(
                "key4", "field4", new String[]{"4rv1", "4rv2", "4rv3"});
        
        // テスト実施 ----------------------------------------------------------
        errorMessages.add(errorMessage);

        // 判定 ----------------------------------------------------------------
        List results = (List) UTUtil.getPrivateField(
                errorMessages, "errorMessages");
        assertEquals(4, results.size());
        ErrorMessage result1 = (ErrorMessage) results.get(0);
        assertEquals("key1", result1.getKey());
        assertEquals("field1", result1.getField());
        assertEquals(3, result1.getReplaceValues().length);
        assertEquals("1rv1", result1.getReplaceValues()[0]);
        assertEquals("1rv2", result1.getReplaceValues()[1]);
        assertEquals("1rv3", result1.getReplaceValues()[2]);
        
        ErrorMessage result2 = (ErrorMessage) results.get(1);
        assertEquals("key2", result2.getKey());
        assertEquals("field2", result2.getField());
        assertEquals(3, result2.getReplaceValues().length);
        assertEquals("2rv1", result2.getReplaceValues()[0]);
        assertEquals("2rv2", result2.getReplaceValues()[1]);
        assertEquals("2rv3", result2.getReplaceValues()[2]);
        
        ErrorMessage result3 = (ErrorMessage) results.get(2);
        assertEquals("key3", result3.getKey());
        assertEquals("field3", result3.getField());
        assertEquals(3, result3.getReplaceValues().length);
        assertEquals("3rv1", result3.getReplaceValues()[0]);
        assertEquals("3rv2", result3.getReplaceValues()[1]);
        assertEquals("3rv3", result3.getReplaceValues()[2]);
        
        ErrorMessage result4 = (ErrorMessage) results.get(3);
        assertEquals("key4", result4.getKey());
        assertEquals("field4", result4.getField());
        assertEquals(3, result4.getReplaceValues().length);
        assertEquals("4rv1", result4.getReplaceValues()[0]);
        assertEquals("4rv2", result4.getReplaceValues()[1]);
        assertEquals("4rv3", result4.getReplaceValues()[2]);
    }

    /**
     * testGetErrorMessages01()
     * <br><br>
     * 
     * (正常系) 
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(状態) this.errorMessages:not null<br>
     *                ({key="key", field="field", 
     *                replaceValues={"rv1","rv2","rv3"}},<br>
     *                nullの要素)<br>
     *         
     * <br>
     * 期待値：(戻り値) List:not null<br>
     *                  ({key="key", field="field", 
     *                  replaceValues={"rv1","rv2","rv3"}},<br>
     *                  nullの要素)<br>
     *         
     * <br>
     * errorMessage属性のgetメソッドのテスト。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetErrorMessages01() throws Exception {
        // 前処理
        ErrorMessages errorMessages = new ErrorMessages();
        List<ErrorMessage> errorList = new ArrayList<ErrorMessage>();
        errorList.add(new ErrorMessage(
                "key", "field", new String[]{"rv1", "rv2", "rv3"}));
        UTUtil.setPrivateField(
                errorMessages, "errorMessages", errorList);
        
        // テスト実施
        List<ErrorMessage> results = errorMessages.getErrorMessages();

        // 判定
        assertEquals(1, results.size());
        ErrorMessage result = results.get(0);
        assertEquals("key", result.getKey());
        assertEquals("field", result.getField());
        assertEquals(3, result.getReplaceValues().length);
        assertEquals("rv1", result.getReplaceValues()[0]);
        assertEquals("rv2", result.getReplaceValues()[1]);
        assertEquals("rv3", result.getReplaceValues()[2]);
    }

    /**
     * testHasErrorMessage01()
     * <br><br>
     * 
     * (正常系) 
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(状態) this.errorMessages:not null<br>
     *                (空のリスト)<br>
     *         
     * <br>
     * 期待値：(戻り値) boolean:false<br>
     *         
     * <br>
     * 空のerrorMessages属性はエラー状態になるか確認するテスト。<br>
     * FALSEが返される。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testHasErrorMessage01() throws Exception {
        // 前処理
        ErrorMessages errorMessages = new ErrorMessages();
        UTUtil.setPrivateField(
                errorMessages, "errorMessages", new ArrayList<ErrorMessage>());

        // テスト実施
        boolean hasError = errorMessages.hasErrorMessage();

        // 判定
        assertFalse(hasError);
    }

    /**
     * testHasErrorMessage02()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(状態) this.errorMessages:not null<br>
     *                ({key="key", field="field", 
     *                replaceValues={"rv1","rv2","rv3"}})<br>
     *         
     * <br>
     * 期待値：(戻り値) boolean:true<br>
     *         
     * <br>
     * 要素を1つ持つerrorMessages属性はエラー状態になるか確認するテスト。
     * TRUEが返される。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testHasErrorMessage02() throws Exception {
        // 前処理
        ErrorMessages errorMessages = new ErrorMessages();
        List<ErrorMessage> errorList = new ArrayList<ErrorMessage>();
        errorList.add(new ErrorMessage(
                "key", "field", new String[]{"rv1", "rv2", "rv3"}));
        UTUtil.setPrivateField(
                errorMessages, "errorMessages", errorList);

        // テスト実施
        boolean hasError = errorMessages.hasErrorMessage();

        // 判定
        assertTrue(hasError);
    }

    /**
     * testHasErrorMessage03()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(状態) this.errorMessages:not null<br>
     *                ({key="key1", field="field1", 
     *                replaceValues={"1rv1","1rv2","1rv3"}},<br>
     *                {key="key2", field="field2", 
     *                replaceValues={"2rv1","2rv2","2rv3"}},<br>
     *                {key="key3", field="field3", 
     *                replaceValues={"3rv1","3rv2","3rv3"}})<br>
     *         
     * <br>
     * 期待値：(戻り値) boolean:true<br>
     *         
     * <br>
     * 要素を３つ持つerrorMessages属性はエラー状態になるか確認するテスト。
     * TRUEが返される。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testHasErrorMessage03() throws Exception {
        // 前処理
        ErrorMessages errorMessages = new ErrorMessages();
        List<ErrorMessage> errorList = new ArrayList<ErrorMessage>();
        errorList.add(new ErrorMessage(
                "key1", "field1", new String[]{"1rv1", "1rv2", "1rv3"}));
        errorList.add(new ErrorMessage(
                "key2", "field2", new String[]{"2rv1", "2rv2", "2rv3"}));
        errorList.add(new ErrorMessage(
                "key3", "field3", new String[]{"3rv1", "3rv2", "3rv3"}));
        UTUtil.setPrivateField(
                errorMessages, "errorMessages", errorList);

        // テスト実施
        boolean hasError = errorMessages.hasErrorMessage();

        // 判定
        assertTrue(hasError);
    }
}
