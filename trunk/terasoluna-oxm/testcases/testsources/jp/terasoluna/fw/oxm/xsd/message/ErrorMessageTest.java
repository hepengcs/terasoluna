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

import jp.terasoluna.utlib.UTUtil;
import jp.terasoluna.fw.oxm.xsd.message.ErrorMessage;
import junit.framework.TestCase;

/**
 * {@link jp.terasoluna.fw.oxm.xsd.message.ErrorMessage} クラスのブラックボックステスト。
 * 
 * <p>
 * <h4>【クラスの概要】</h4>
 * エラーメッセージを保持するクラス。
 * <p>
 * 
 * @see jp.terasoluna.fw.oxm.xsd.message.ErrorMessage
 */
public class ErrorMessageTest extends TestCase {

    /**
     * テスト用のErrorMessage
     */
    ErrorMessage testErrorMessage = null;
    
    /**
     * このテストケースを実行する為の
     * GUI アプリケーションを起動する。
     * 
     * @param args java コマンドに設定されたパラメータ
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(ErrorMessageTest.class);
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
        
        // 初期化
        testErrorMessage = new ErrorMessage(null, null, (String[]) null);        
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

        testErrorMessage = null;        
        
    }

    /**
     * コンストラクタ。
     * 
     * @param name このテストケースの名前。
     */
    public ErrorMessageTest(String name) {
        super(name);
    }

    /**
     * testErrorMessageStringStringStringArray01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A,D
     * <br><br>
     * 入力値：(引数) key:"abc"<br>
     *         (引数) field:"123"<br>
     *         (引数) values:要素数0<br>
     *                String[]{}<br>
     *         
     * <br>
     * 期待値：(状態変化) key:"abc"<br>
     *         (状態変化) replaceValues:要素数0<br>
     *                    String[]{}<br>
     *         (状態変化) field:"123"<br>
     *         
     * <br>
     * key、field、valuesが正しく属性に設定されることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testErrorMessageStringStringStringArray01() throws Exception {
        
        // 前処理の設定。
        String key = "abc";
        String field = "123";
        String[] values = new String[]{};
        
        // テスト実施。
        ErrorMessage message = new ErrorMessage(key, field, values);

        // 判定。
        assertEquals("abc", UTUtil.getPrivateField(message, "key"));
        assertEquals("123", UTUtil.getPrivateField(message, "field"));
        
        String[] replaceValues = (String[]) UTUtil.getPrivateField(message, "replaceValues");
        assertEquals(0, replaceValues.length);

    }

    /**
     * testErrorMessageStringStringStringArray02()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A,C,D
     * <br><br>
     * 入力値：(引数) key:""<br>
     *         (引数) field:not null<br>
     *         (引数) values:要素数1<br>
     *                String[]{"a"}<br>
     *         
     * <br>
     * 期待値：(状態変化) key:""<br>
     *         (状態変化) replaceValues:要素数1<br>
     *                    String[]{"a"}<br>
     *         (状態変化) field:not null<br>
     *         
     * <br>
     * keyが空文字の場合、属性に空文字が設定されることを確認する<br>
     * valuesが正しく属性に設定されることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testErrorMessageStringStringStringArray02() throws Exception {
        
        // 前処理の設定。
        String key = "";
        String field = "123";
        String[] values = new String[]{"a"};
        
        // テスト実施。
        ErrorMessage message = new ErrorMessage(key, field, values);

        // 判定。
        assertEquals("", UTUtil.getPrivateField(message, "key"));
        assertEquals("123", UTUtil.getPrivateField(message, "field"));
        
        String[] replaceValues = (String[]) UTUtil.getPrivateField(message, "replaceValues");
        assertEquals("a", replaceValues[0]);
        assertEquals(1, replaceValues.length);

    }

    /**
     * testErrorMessageStringStringStringArray03()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A,C,D
     * <br><br>
     * 入力値：(引数) key:null<br>
     *         (引数) field:not null<br>
     *         (引数) values:要素数3<br>
     *                {"a","b","c"}<br>
     *         
     * <br>
     * 期待値：(状態変化) key:null<br>
     *         (状態変化) replaceValues:要素数3<br>
     *                    {"a","b","c"}<br>
     *         (状態変化) field:not null<br>
     *         
     * <br>
     * keyがnullの場合、属性にnullが設定されることを確認する。<br>
     * valuesが正しく属性に設定されることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testErrorMessageStringStringStringArray03() throws Exception {
        
        // 前処理の設定。
        String key = null;
        String field = "123";
        String[] values = new String[]{"a","b","c"};
        
        // テスト実施。
        ErrorMessage message = new ErrorMessage(key, field, values);

        // 判定。
        assertNull(UTUtil.getPrivateField(message, "key"));
        assertEquals("123", UTUtil.getPrivateField(message, "field"));
        
        String[] replaceValues = (String[]) UTUtil.getPrivateField(message, "replaceValues");
        assertEquals("a", replaceValues[0]);
        assertEquals("b", replaceValues[1]);
        assertEquals("c", replaceValues[2]);
        assertEquals(3, replaceValues.length);

    }

    /**
     * testErrorMessageStringStringStringArray04()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A,C
     * <br><br>
     * 入力値：(引数) key:not null<br>
     *         (引数) field:""<br>
     *         (引数) values:null<br>
     *         
     * <br>
     * 期待値：(状態変化) key:not null<br>
     *         (状態変化) replaceValues:null<br>
     *         (状態変化) field:""<br>
     *         
     * <br>
     * fieldが空文字の場合、属性に空文字が設定されることを確認する<br>
     * valuesが正しく属性に設定されることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testErrorMessageStringStringStringArray04() throws Exception {
        
        // 前処理の設定。
        String key = "abc";
        String field = "";
        String[] values = null;
        
        // テスト実施。
        ErrorMessage message = new ErrorMessage(key, field, values);

        // 判定。
        assertEquals("abc", UTUtil.getPrivateField(message, "key"));
        assertEquals("", UTUtil.getPrivateField(message, "field"));
        
        String[] replaceValues = (String[]) UTUtil.getPrivateField(message, "replaceValues");
        assertNull(replaceValues);

    }

    /**
     * testErrorMessageStringStringStringArray05()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A,C,D
     * <br><br>
     * 入力値：(引数) key:not null<br>
     *         (引数) field:null<br>
     *         (引数) values:要素なし（可変長引数の個数が0）<br>
     *         
     * <br>
     * 期待値：(状態変化) key:not null<br>
     *         (状態変化) replaceValues:要素数0<br>
     *                    String[]{}<br>
     *         (状態変化) field:null<br>
     *         
     * <br>
     * fieldがnullの場合、属性にnullが設定されることを確認する。
     * valuesの引数がない場合、replaceValueに空のリストが格納されることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testErrorMessageStringStringStringArray05() throws Exception {
        
        // 前処理の設定。
        String key = "abc";
        String field = null;
        
        // テスト実施。
        ErrorMessage message = new ErrorMessage(key, field);

        // 判定。
        assertEquals("abc", UTUtil.getPrivateField(message, "key"));
        assertNull(UTUtil.getPrivateField(message, "field"));
        
        String[] replaceValues = (String[]) UTUtil.getPrivateField(message, "replaceValues");
        assertEquals(0, replaceValues.length);

    }

    /**
     * testGetKey01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(状態) key:"abc"<br>
     *         
     * <br>
     * 期待値：(戻り値) this.key:"abc"<br>
     *         
     * <br>
     * 属性に設定されている文字列が正しく返却されることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetKey01() throws Exception {
        
        // 前処理
        String key = "abc";
        UTUtil.setPrivateField(testErrorMessage, "key", key);
        
        // テスト実施
        String result = testErrorMessage.getKey();
        
        // 判定
        assertEquals("abc", result);
    }

    /**
     * testSetKey01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) key:"key"<br>
     *         (引数) this.key:null<br>
     *         
     * <br>
     * 期待値：(状態変化) this.key:"key"<br>
     *         
     * <br>
     * 引数の値が属性に正しく設定されることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetKey01() throws Exception {
        
        // 前処理
        
        // テスト実施
        testErrorMessage.setKey("abc");
        
        // 判定
        assertEquals("abc", UTUtil.getPrivateField(testErrorMessage, "key"));
        
    }

    /**
     * testGetReplaceValues01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(状態) this.replaceValues:要素数3<br>
     *                String[]{"a","b","c"}<br>
     *         
     * <br>
     * 期待値：(戻り値) this.replaceValues:要素数3<br>
     *                  String[]{"a","b","c"}<br>
     *         
     * <br>
     * 属性に設定された配列が正しく返却されることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetReplaceValues01() throws Exception {
        
        // 前処理
        String[] replaceValues = new String[]{"a","b","c"};
        UTUtil.setPrivateField(testErrorMessage, "replaceValues", replaceValues);
        
        // テスト実施
        String[] result = testErrorMessage.getReplaceValues();
        
        // 判定
        assertEquals(3, result.length);
        assertEquals("a", result[0]);
        assertEquals("b", result[1]);
        assertEquals("c", result[2]);
    }

    /**
     * testSetReplaceValues01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) replaceValues:要素数3<br>
     *                String[]{"a","b","c"}<br>
     *         (状態) replaceValues:null<br>
     *         
     * <br>
     * 期待値：(状態変化) this.replaceValues:要素数3<br>
     *                    String[]{"a","b","c"}<br>
     *         
     * <br>
     * 引数の値が属性に正しく設定されることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetReplaceValues01() throws Exception {
        
        // 前処理
        
        // テスト実施
        testErrorMessage.setReplaceValues(new String[]{"a","b","c"});
        
        // 判定
        String[] replaceValues = (String[]) UTUtil.getPrivateField(testErrorMessage, "replaceValues");
        assertEquals("a", replaceValues[0]);
        assertEquals("b", replaceValues[1]);
        assertEquals("c", replaceValues[2]);
        assertEquals(3, replaceValues.length);
        
    }

    /**
     * testGetField01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(状態) this.field:"123"<br>
     *         
     * <br>
     * 期待値：(戻り値) field:"123"<br>
     *         
     * <br>
     * 属性に設定された配列が正しく返却されることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetField01() throws Exception {
        
        // 前処理
        String inputField = "123";
        UTUtil.setPrivateField(testErrorMessage, "field", inputField);
        
        // テスト実施
        String result = testErrorMessage.getField();
        
        // 判定
        assertEquals("123", result);
    }

    /**
     * testSetField01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) field:"123"<br>
     *         (状態) this.field:null<br>
     *         
     * <br>
     * 期待値：(状態変化) this.field:"123"<br>
     *         
     * <br>
     * 引数の値が属性に正しく設定されることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetField01() throws Exception {
        
        // 前処理
        
        // テスト実施
        testErrorMessage.setField("123");
        
        // 判定
        assertEquals("123", UTUtil.getPrivateField(testErrorMessage, "field"));
        
    }

}
