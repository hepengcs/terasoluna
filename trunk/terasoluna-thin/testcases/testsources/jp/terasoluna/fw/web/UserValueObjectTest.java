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

package jp.terasoluna.fw.web;

import jp.terasoluna.fw.exception.SystemException;
import jp.terasoluna.fw.util.ClassLoadException;
import jp.terasoluna.utlib.LogUTUtil;
import jp.terasoluna.utlib.PropertyTestCase;

/**
 * {@link jp.terasoluna.fw.web.UserValueObject} クラスのブラックボックステスト。
 * 
 * <p>
 * <h4>【クラスの概要】</h4>
 * ログオンユーザ情報抽象クラス。
 * <p>
 * 
 * @see jp.terasoluna.fw.web.UserValueObject
 * 
 */
@SuppressWarnings("unused")
public class UserValueObjectTest extends PropertyTestCase {

    /**
     * このテストケースを実行する為の GUI アプリケーションを起動する。
     * 
     * @param args
     *            java コマンドに設定されたパラメータ
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(UserValueObjectTest.class);
    }

    /**
     * 初期化処理を行う。
     * 
     * @throws Exception
     *             このメソッドで発生した例外
     * @see jp.terasoluna.utlib.spring.PropertyTestCase#setUpData()
     */
    @Override
    protected void setUpData() throws Exception {
    }

    /**
     * 終了処理を行う。
     * 
     * @throws Exception
     *             このメソッドで発生した例外
     * @see jp.terasoluna.utlib.spring.PropertyTestCase#cleanUpData()
     */
    @Override
    protected void cleanUpData() throws Exception {
    }

    /**
     * コンストラクタ。
     * 
     * @param name
     *            このテストケースの名前。
     */
    public UserValueObjectTest(String name) {
        super(name);
    }

    /**
     * testCreateUserValueObject01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(状態) プロパティファイル:user.value.object=<br>
     *                jp.terasoluna.fw.web.UserValueObjectImpl01<br>
     *         
     * <br>
     * 期待値：(戻り値) UserValueObject:UserValueObjectImpl01インスタンス<br>
     *         
     * <br>
     * プロパティファイルにuser.value.objectをキーとして、UserValueObject実装クラス名が記述されている場合、そのクラスのインスタンスが取得できることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testCreateUserValueObject01() throws Exception {
        // 前処理
        addProperty("user.value.object",
                "jp.terasoluna.fw.web.UserValueObjectImpl01");

        // テスト実施
        UserValueObject userObject = UserValueObject.createUserValueObject();

        // 判定
        assertEquals(UserValueObjectImpl01.class.getName(), userObject
                .getClass().getName());
    }

    /**
     * testCreateUserValueObject02()
     * <br><br>
     * 
     * (異常系)
     * <br>
     * 観点：G
     * <br><br>
     * 入力値：(状態) プロパティファイル:user.value.object=java.lang.String<br>
     *         
     * <br>
     * 期待値：(状態変化) 例外:SystemException：<br>
     *                    メッセージキー："errors.uvo.class"<br>
     *                    置換文字列１：<br>
     *                    "java.lang.String"<br>
     *                    ラップした例外：ClassCastException<br>
     *         (状態変化) ログ:ログレベル：エラー<br>
     *                    メッセージ：<br>
     *                    illegal uvo class:java.lang.String<br>
     *                    例外：ClassCastException<br>
     *         
     * <br>
     * プロパティファイルにuser.value.objectをキーとして、UserValueObjectを実装していないクラス名が記述されている場合、ClassCastExceptionをラップしたSystemExceptionがスローされることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testCreateUserValueObject02() throws Exception {
        // 前処理
        addProperty("user.value.object", "java.lang.String");
        try {
            // テスト実施
            UserValueObject userObject = 
                UserValueObject.createUserValueObject();
            fail();

        } catch (SystemException sysEx) {
            // 判定
            // SystemException の中身をチェック
            // メッセージキーチェック
            assertEquals("errors.uvo.class", sysEx.getErrorCode());
            // ラップした例外チェック
            assertEquals(ClassCastException.class.getName(), 
                    sysEx.getCause().getClass().getName());
            
            // 置換文字列チェック
            assertEquals("java.lang.String", sysEx.getOptions()[0]);
            // ログチェック
            assertTrue(LogUTUtil.checkError(
                    "illegal uvo class:java.lang.String", sysEx.getCause()));
        }
    }

    /**
     * testCreateUserValueObject03()
     * <br><br>
     * 
     * (異常系)
     * <br>
     * 観点：G
     * <br><br>
     * 入力値：(状態) プロパティファイル:user.value.object=uvo<br>
     *         
     * <br>
     * 期待値：(状態変化) 例外:SystemException：<br>
     *                    メッセージキー："errors.uvo.class"<br>
     *                    置換文字列１：<br>
     *                    "uvo"<br>
     *                    ラップした例外：ClassLoadException<br>
     *         (状態変化) ログ:ログレベル：エラー<br>
     *                    メッセージ：<br>
     *                    illegal uvo class:uvo<br>
     *                    例外：ClassLoadException<br>
     *         
     * <br>
     * プロパティファイルにuser.value.objectをキーとして、クラスパス上に存在しないクラス名が記述されている場合、ClassLoadExceptionをラップしたSystemExceptionがスローされることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testCreateUserValueObject03() throws Exception {
        // 前処理
        addProperty("user.value.object", "uvo");
        try {
            // テスト実施
            UserValueObject userObject = 
                UserValueObject.createUserValueObject();
            fail();

        } catch (SystemException sysEx) {
            // 判定
            // SystemException の中身をチェック
            // メッセージキーチェック
            assertEquals("errors.uvo.class", sysEx.getErrorCode());
            // ラップした例外チェック
            assertEquals(ClassLoadException.class.getName(),
                    sysEx.getCause().getClass().getName());
            // 置換文字列チェック
            assertEquals("uvo", sysEx.getOptions()[0]);
            // ログチェック
            assertTrue(LogUTUtil.checkError("illegal uvo class:uvo", 
                    sysEx.getCause()));

        }
    }

    /**
     * testCreateUserValueObject04()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(状態) プロパティファイル:user.value.objectの設定なし<br>
     *         
     * <br>
     * 期待値：(戻り値) UserValueObject:null<br>
     *         (状態変化) ログ:ログレベル：エラー<br>
     *                    メッセージ：specify user.value.object.<br>
     *         
     * <br>
     * プロパティファイルにuser.value.objectのキーが存在しない場合、エラーログを出力し、nullを返却することを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testCreateUserValueObject04() throws Exception {
        // 前処理
        // user.value.objectの設定なし

        // テスト実施
        UserValueObject userObject = UserValueObject.createUserValueObject();

        // テスト判定
        assertNull(userObject);
        
        // ログチェック
        assertTrue(LogUTUtil.checkError("specify user.value.object."));        

    }
}
