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

package jp.terasoluna.fw.web.struts.form;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import jp.terasoluna.fw.util.ClassLoadException;
import jp.terasoluna.utlib.LogUTUtil;
import jp.terasoluna.utlib.MockHttpServletRequest;
import jp.terasoluna.utlib.PropertyTestCase;

import org.apache.commons.validator.Field;
import org.apache.commons.validator.Msg;
import org.apache.commons.validator.Validator;
import org.apache.commons.validator.ValidatorAction;
import org.apache.commons.validator.ValidatorResources;
import org.apache.commons.validator.Var;
import org.apache.struts.Globals;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

/**
 * {@link jp.terasoluna.fw.web.struts.form.FieldChecksEx}
 * クラスのブラックボックステスト。
 *
 * <p>
 * <h4>【クラスの概要】</h4>
 * Validator追加ルールクラス。
 * <p>
 *
 * @see jp.terasoluna.fw.web.struts.form.FieldChecksEx
 */
public class FieldChecksExTest08 extends PropertyTestCase {

    /**
     * validateMultiFieldの引数に使用するBean。
     */
    FieldChecksEx_BeanStub01 javaBean = new FieldChecksEx_BeanStub01();

    /**
     * このテストケースを実行する為の
     * GUI アプリケーションを起動する。
     *
     * @param args java コマンドに設定されたパラメータ
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(FieldChecksExTest08.class);
    }

    /**
     * 初期化処理を行う。
     *
     * @throws Exception このメソッドで発生した例外
     * @see jp.terasoluna.utlib.spring.PropertyTestCase#setUpData()
     */
    @Override
    protected void setUpData() throws Exception {
        LogUTUtil.flush();
        deleteProperty("validation.hankaku.kana.list");
        deleteProperty("validation.zenkaku.kana.list");
        FieldChecksEx_MultiFieldValidatorImpl01.value = null;
        FieldChecksEx_MultiFieldValidatorImpl01.fields = null;
        FieldChecksEx_MultiFieldValidatorImpl01.validateCalledCount = 0;
        FieldChecksEx_MultiFieldValidatorImpl01.result = false;
    }

    /**
     * 終了処理を行う。
     *
     * @throws Exception このメソッドで発生した例外
     * @see jp.terasoluna.utlib.spring.PropertyTestCase#cleanUpData()
     */
    @Override
    protected void cleanUpData() throws Exception {
    }

    /**
     * コンストラクタ。
     *
     * @param name このテストケースの名前。
     */
    public FieldChecksExTest08(String name) {
        super(name);
        this.javaBean.setField1("abc");
        this.javaBean.setField2("def");
        this.javaBean.setField3("ghi");
        this.javaBean.setField4("jkl");
    }

    /**
     * testValidateByteRange01()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：C,F
     * <br><br>
     * 入力値：(引数) bean:null<br>
     *         (引数) va:not null<br>
     *         (引数) field:not null<br>
     *                var:maxByte=null<br>
     *                var:minByte=null<br>
     *                Msg("message","message")<br>
     *         (引数) errors:not null<br>
     *                (空の要素)<br>
     *         (引数) validator:not null<br>
     *         (引数) request:not null<br>
     *
     * <br>
     * 期待値：(戻り値) boolean:true<br>
     *         (状態変化) ログ:ログレベル：<br>
     *                    エラー<br>
     *                    メッセージ：<br>
     *                    bean is null.<br>
     *         (状態変化) errors:not null<br>
     *                    (空の要素)<br>
     *
     * <br>
     * 引数のbeanがnullのとき、エラーログを出力してtrueが返却されることを
     * 確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testValidateByteRange01() throws Exception {
        //テストデータ設定
        // ++++ beanオブジェクト ++++
        String bean = null;
        // ++++ 検証設定オブジェクト
        ValidatorAction va = new ValidatorAction();
        // ++++ 検証フィールド情報
        Field field = new Field();
        Var var = new Var();
        var.setName("maxByte");
        var.setValue(null);
        field.addVar(var);
        var = new Var();
        var.setName("minByte");
        var.setValue(null);
        field.addVar(var);
        // メッセージ設定
        Msg msg = new Msg();
        msg.setKey("message");
        msg.setName("message");
        msg.setResource(false);
        field.addMsg(msg);
        // エラー情報
        ActionMessages errors = new ActionMessages();
        // 擬似HTTPリクエスト
        MockHttpServletRequest request = new MockHttpServletRequest();
        // ValidatorResourcesインスタンス
        ValidatorResources validatorResources = new ValidatorResources();
        // Validatorインスタンス
        Validator validator = new Validator(validatorResources);

        // テスト実行
        boolean result =
            FieldChecksEx.validateByteRange(
                bean,
                va,
                field,
                errors,
                validator,
                request);
        // テスト結果確認
        // trueが返却されていること。
        assertTrue(result);
        // エラー情報が空であること。
        assertTrue(errors.isEmpty());

        // エラーログ確認
        assertTrue(LogUTUtil.checkError("bean is null."));
    }

    /**
     * testValidateByteRange02()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：C,F
     * <br><br>
     * 入力値：(引数) bean:""<br>
     *         (引数) va:not null<br>
     *         (引数) field:not null<br>
     *                var:maxByte="1"<br>
     *                var:minByte="4"<br>
     *                Msg("message","message")<br>
     *         (引数) errors:not null<br>
     *                (空の要素)<br>
     *         (引数) validator:not null<br>
     *         (引数) request:not null<br>
     *
     * <br>
     * 期待値：(戻り値) boolean:true<br>
     *         (状態変化) errors:not null<br>
     *                    (空の要素)<br>
     *
     * <br>
     * 引数のbeanが空文字のとき、trueが返却されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testValidateByteRange02() throws Exception {
        //テストデータ設定
        // ++++ beanオブジェクト ++++
        String bean = "";
        // ++++ 検証設定オブジェクト
        ValidatorAction va = new ValidatorAction();
        // ++++ 検証フィールド情報
        Field field = new Field();
        Var var = new Var();
        var.setName("maxByte");
        var.setValue("1");
        field.addVar(var);
        var = new Var();
        var.setName("minByte");
        var.setValue("4");
        field.addVar(var);
        // メッセージ設定
        Msg msg = new Msg();
        msg.setKey("message");
        msg.setName("message");
        msg.setResource(false);
        field.addMsg(msg);
        // エラー情報
        ActionMessages errors = new ActionMessages();
        // 擬似HTTPリクエスト
        MockHttpServletRequest request = new MockHttpServletRequest();
        // ValidatorResourcesインスタンス
        ValidatorResources validatorResources = new ValidatorResources();
        // Validatorインスタンス
        Validator validator = new Validator(validatorResources);

        // テスト実行
        boolean result =
            FieldChecksEx.validateByteRange(
                bean,
                va,
                field,
                errors,
                validator,
                request);
        // テスト結果確認
        // trueが返却されていること。
        assertTrue(result);
        // エラー情報が空であること。
        assertTrue(errors.isEmpty());
    }

    /**
     * testValidateByteRange03()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) bean:String:"あ"<br>
     *         (引数) va:not null<br>
     *         (引数) field:not null<br>
     *                var:minByte="1"<br>
     *                var:maxByte="5"<br>
     *                encoding="UTF-8"<br>
     *                Msg("message","message")<br>
     *         (引数) errors:not null<br>
     *                (空の要素)<br>
     *         (引数) validator:not null<br>
     *         (引数) request:not null<br>
     *
     * <br>
     * 期待値：(戻り値) boolean:true<br>
     *         (状態変化) errors:not null<br>
     *                    (空の要素)<br>
     *
     * <br>
     * 引数のbeanのバイト数がfieldのminByte、maxByteの範囲にあるとき、
     * trueが返却されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testValidateByteRange03() throws Exception {
        //テストデータ設定
        // ++++ beanオブジェクト ++++
        String bean = "あ";
        // ++++ 検証設定オブジェクト
        ValidatorAction va = new ValidatorAction();
        // ++++ 検証フィールド情報
        Field field = new Field();
        Var var = new Var();
        var.setName("maxByte");
        var.setValue("5");
        field.addVar(var);
        var = new Var();
        var.setName("minByte");
        var.setValue("1");
        field.addVar(var);
        var = new Var();
        var.setName("encoding");
        var.setValue("UTF-8");
        field.addVar(var);
        // メッセージ設定
        Msg msg = new Msg();
        msg.setKey("message");
        msg.setName("message");
        msg.setResource(false);
        field.addMsg(msg);
        // エラー情報
        ActionMessages errors = new ActionMessages();
        // 擬似HTTPリクエスト
        MockHttpServletRequest request = new MockHttpServletRequest();
        // ValidatorResourcesインスタンス
        ValidatorResources validatorResources = new ValidatorResources();
        // Validatorインスタンス
        Validator validator = new Validator(validatorResources);

        // テスト実行
        boolean result =
            FieldChecksEx.validateByteRange(
                bean,
                va,
                field,
                errors,
                validator,
                request);
        // テスト結果確認
        // trueが返却されていること。
        assertTrue(result);
        // エラー情報が空であること。
        assertTrue(errors.isEmpty());
    }

    /**
     * testValidateByteRange04()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) bean:String:"あ"<br>
     *         (引数) va:not null<br>
     *         (引数) field:not null<br>
     *                var:minByte="4"<br>
     *                var:maxByte="8"<br>
     *                encoding="Windows-31J"<br>
     *                Msg("message","message")<br>
     *         (引数) errors:not null<br>
     *                ActionMessage("testMessage")<br>
     *         (引数) validator:not null<br>
     *         (引数) request:not null<br>
     *
     * <br>
     * 期待値：(戻り値) boolean:false<br>
     *         (状態変化) errors:ActionMessage("testMessage")<br>
     *                    ActionMessage("message")<br>
     *
     * <br>
     * 引数のbeanのバイト数がfieldのminByte、maxByteの範囲外のとき、
     * エラーメッセージを追加し、falseが返却されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testValidateByteRange04() throws Exception {
        //テストデータ設定
        // ++++ beanオブジェクト ++++
        String bean = "あ";
        // ++++ 検証設定オブジェクト
        ValidatorAction va = new ValidatorAction();
        va.setName("message");
        // ++++ 検証フィールド情報
        Field field = new Field();
        Var var = new Var();
        var.setName("maxByte");
        var.setValue("8");
        field.addVar(var);
        var = new Var();
        var.setName("minByte");
        var.setValue("4");
        field.addVar(var);
        var = new Var();
        var.setName("encoding");
        var.setValue("Windows-31J");
        field.addVar(var);
        // メッセージ設定
        Msg msg = new Msg();
        msg.setKey("message");
        msg.setName("message");
        msg.setResource(false);
        field.addMsg(msg);
        // エラー情報
        ActionMessages errors = new ActionMessages();
        ActionMessage error = new ActionMessage("testMessage");
        errors.add(Globals.ERROR_KEY, error);
        // 擬似HTTPリクエスト
        MockHttpServletRequest request = new MockHttpServletRequest();
        // ValidatorResourcesインスタンス
        ValidatorResources validatorResources = new ValidatorResources();
        // Validatorインスタンス
        Validator validator = new Validator(validatorResources);

        // テスト実行
        boolean result =
            FieldChecksEx.validateByteRange(
                bean,
                va,
                field,
                errors,
                validator,
                request);
        // テスト結果確認
        // falseが返却されていること。
        assertFalse(result);
        // エラー情報が2件であること。
        assertEquals(2, errors.size());

        // メッセージオブジェクトの検査
        Iterator it = errors.get();
        List<String> list = new ArrayList<String>();
        while (it.hasNext()) {
            ActionMessage message = (ActionMessage) it.next();
            list.add(message.getKey());
        }
        assertTrue(list.contains("testMessage"));
        assertTrue(list.contains("message"));
    }

    /**
     * testValidateByteRange05()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) bean:String:"あああ"<br>
     *         (引数) va:not null<br>
     *         (引数) field:not null<br>
     *                var:minByte="4"<br>
     *                var:maxByte="8"<br>
     *                encoding="UTF-8"<br>
     *                Msg("message","message")<br>
     *         (引数) errors:not null<br>
     *                ActionMessage("testMessage")<br>
     *         (引数) validator:not null<br>
     *         (引数) request:not null<br>
     *
     * <br>
     * 期待値：(戻り値) boolean:false<br>
     *         (状態変化) errors:ActionMessage("testMessage")<br>
     *                    ActionMessage("message")<br>
     *
     * <br>
     * 引数のbeanのバイト数がfieldのminByte、maxByteの範囲外のとき、
     * エラーメッセージを追加し、falseが返却されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testValidateByteRange05() throws Exception {
        //テストデータ設定
        // ++++ beanオブジェクト ++++
        String bean = "あああ";
        // ++++ 検証設定オブジェクト
        ValidatorAction va = new ValidatorAction();
        va.setName("message");
        // ++++ 検証フィールド情報
        Field field = new Field();
        Var var = new Var();
        var.setName("maxByte");
        var.setValue("8");
        field.addVar(var);
        var = new Var();
        var.setName("minByte");
        var.setValue("4");
        field.addVar(var);
        var = new Var();
        var.setName("encoding");
        var.setValue("UTF-8");
        field.addVar(var);
        // メッセージ設定
        Msg msg = new Msg();
        msg.setKey("message");
        msg.setName("message");
        msg.setResource(false);
        field.addMsg(msg);
        // エラー情報
        ActionMessages errors = new ActionMessages();
        ActionMessage error = new ActionMessage("testMessage");
        errors.add(Globals.ERROR_KEY, error);
        // 擬似HTTPリクエスト
        MockHttpServletRequest request = new MockHttpServletRequest();
        // ValidatorResourcesインスタンス
        ValidatorResources validatorResources = new ValidatorResources();
        // Validatorインスタンス
        Validator validator = new Validator(validatorResources);

        // テスト実行
        boolean result =
            FieldChecksEx.validateByteRange(
                bean,
                va,
                field,
                errors,
                validator,
                request);
        // テスト結果確認
        // falseが返却されていること。
        assertFalse(result);
        // エラー情報が2件であること。
        assertEquals(2, errors.size());

        // メッセージオブジェクトの検査
        Iterator it = errors.get();
        List<String> list = new ArrayList<String>();
        while (it.hasNext()) {
            ActionMessage message = (ActionMessage) it.next();
            list.add(message.getKey());
        }
        assertTrue(list.contains("testMessage"));
        assertTrue(list.contains("message"));
    }

    /**
     * testValidateByteRange06()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) bean:String:"あああ"<br>
     *         (引数) va:not null<br>
     *         (引数) field:not null<br>
     *                var:minByte=null<br>
     *                var:maxByte=null<br>
     *                encoding=null<br>
     *                Msg("message","message")<br>
     *         (引数) errors:not null<br>
     *                ActionMessage("testMessage")<br>
     *         (引数) validator:not null<br>
     *         (引数) request:not null<br>
     *
     * <br>
     * 期待値：(戻り値) boolean:true<br>
     *         (状態変化) ログ:ログレベル：エラー<br>
     *                    メッセージ：""<br>
     *                    例外：NumberFormatException<br>
     *         (状態変化) errors:not null<br>
     *                    (空の要素)<br>
     *
     * <br>
     * fieldのminByte、maxByteがnullのとき、それぞれ、0、Integer.MAX_VALUE
     * として計算が行われることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testValidateByteRange06() throws Exception {
        //テストデータ設定
        // ++++ beanオブジェクト ++++
        String bean = "あああ";
        // ++++ 検証設定オブジェクト
        ValidatorAction va = new ValidatorAction();
        va.setName("message");
        // ++++ 検証フィールド情報
        Field field = new Field();
        Var var = new Var();
        var.setName("maxByte");
        var.setValue(null);
        field.addVar(var);
        var = new Var();
        var.setName("minByte");
        var.setValue(null);
        field.addVar(var);
        var = new Var();
        var.setName("encoding");
        var.setValue(null);
        field.addVar(var);
        // メッセージ設定
        Msg msg = new Msg();
        msg.setKey("message");
        msg.setName("message");
        msg.setResource(false);
        field.addMsg(msg);
        // エラー情報
        ActionMessages errors = new ActionMessages();
        // 擬似HTTPリクエスト
        MockHttpServletRequest request = new MockHttpServletRequest();
        // ValidatorResourcesインスタンス
        ValidatorResources validatorResources = new ValidatorResources();
        // Validatorインスタンス
        Validator validator = new Validator(validatorResources);

        // テスト実行
        boolean result =
            FieldChecksEx.validateByteRange(
                bean,
                va,
                field,
                errors,
                validator,
                request);
        // テスト結果確認
        // trueが返却されていること。
        assertTrue(result);
        // エラー情報が空であること。
        assertTrue(errors.isEmpty());

        // エラーログチェック
        assertTrue(LogUTUtil.checkError("", new NumberFormatException()));
    }

    /**
     * testValidateByteRange07()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) bean:String:"aaaaaa"<br>
     *         (引数) va:not null<br>
     *         (引数) field:not null<br>
     *                var:minByte=""<br>
     *                var:maxByte=""<br>
     *                encoding=""<br>
     *                Msg("message","message")<br>
     *         (引数) errors:not null<br>
     *                ActionMessage("testMessage")<br>
     *         (引数) validator:not null<br>
     *         (引数) request:not null<br>
     *
     * <br>
     * 期待値：(戻り値) boolean:true<br>
     *         (状態変化) ログ:ログレベル：エラー<br>
     *                    メッセージ：""<br>
     *                    例外：NumberFormatException<br>
     *         (状態変化) errors:not null<br>
     *                    (空の要素)<br>
     *
     * <br>
     * fieldのminByte、maxByteが空文字のとき、それぞれ、0、Integer.MAX_VALUE
     * として計算が行われることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testValidateByteRange07() throws Exception {
        //テストデータ設定
        // ++++ beanオブジェクト ++++
        String bean = "aaaaaa";
        // ++++ 検証設定オブジェクト
        ValidatorAction va = new ValidatorAction();
        va.setName("message");
        // ++++ 検証フィールド情報
        Field field = new Field();
        Var var = new Var();
        var.setName("maxByte");
        var.setValue("");
        field.addVar(var);
        var = new Var();
        var.setName("minByte");
        var.setValue("");
        field.addVar(var);
        var = new Var();
        var.setName("encoding");
        var.setValue("");
        field.addVar(var);
        // メッセージ設定
        Msg msg = new Msg();
        msg.setKey("message");
        msg.setName("message");
        msg.setResource(false);
        field.addMsg(msg);
        // エラー情報
        ActionMessages errors = new ActionMessages();
        // 擬似HTTPリクエスト
        MockHttpServletRequest request = new MockHttpServletRequest();
        // ValidatorResourcesインスタンス
        ValidatorResources validatorResources = new ValidatorResources();
        // Validatorインスタンス
        Validator validator = new Validator(validatorResources);

        // テスト実行
        boolean result =
            FieldChecksEx.validateByteRange(
                bean,
                va,
                field,
                errors,
                validator,
                request);
        // テスト結果確認
        // trueが返却されていること。
        assertTrue(result);
        // エラー情報が空であること。
        assertTrue(errors.isEmpty());

        // エラーログチェック
        assertTrue(LogUTUtil.checkError("", new NumberFormatException()));
    }

    /**
     * testValidateByteRange08()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) bean:String:"あ"<br>
     *         (引数) va:not null<br>
     *         (引数) field:not null<br>
     *                var:minByte="abc"<br>
     *                var:maxByte="def"<br>
     *                encoding="test-encoding"<br>
     *                Msg("message","message")<br>
     *         (引数) errors:not null<br>
     *                ActionMessage("testMessage")<br>
     *         (引数) validator:not null<br>
     *         (引数) request:not null<br>
     *
     * <br>
     * 期待値：(戻り値) boolean:true<br>
     *         (状態変化) ログ:ログレベル：エラー<br>
     *                    メッセージ：""<br>
     *                    例外：NumberFormatException<br>
     *                    ログレベル：警告<br>
     *                    メッセージ：<br>
     *                    "test-encoding is not supported."<br>
     *         (状態変化) errors:not null<br>
     *                    (空の要素)<br>
     *
     * <br>
     * fieldのminByte、maxByteが数値に変換できないとき、それぞれ、0、
     * Integer.MAX_VALUEとして計算が行われることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testValidateByteRange08() throws Exception {
        //テストデータ設定
        // ++++ beanオブジェクト ++++
        String bean = "あ";
        // ++++ 検証設定オブジェクト
        ValidatorAction va = new ValidatorAction();
        va.setName("message");
        // ++++ 検証フィールド情報
        Field field = new Field();
        Var var = new Var();
        var.setName("maxByte");
        var.setValue("abc");
        field.addVar(var);
        var = new Var();
        var.setName("minByte");
        var.setValue("def");
        field.addVar(var);
        var = new Var();
        var.setName("encoding");
        var.setValue("test-encoding");
        field.addVar(var);
        // メッセージ設定
        Msg msg = new Msg();
        msg.setKey("message");
        msg.setName("message");
        msg.setResource(false);
        field.addMsg(msg);
        // エラー情報
        ActionMessages errors = new ActionMessages();
        // 擬似HTTPリクエスト
        MockHttpServletRequest request = new MockHttpServletRequest();
        // ValidatorResourcesインスタンス
        ValidatorResources validatorResources = new ValidatorResources();
        // Validatorインスタンス
        Validator validator = new Validator(validatorResources);

        // テスト実行
        boolean result =
            FieldChecksEx.validateByteRange(
                bean,
                va,
                field,
                errors,
                validator,
                request);
        // テスト結果確認
        // trueが返却されていること。
        assertTrue(result);
        // エラー情報が空であること。
        assertTrue(errors.isEmpty());

        // エラーログチェック
        assertTrue(LogUTUtil.checkError("", new NumberFormatException()));

        // 警告ログチェック
        assertTrue(LogUTUtil.checkWarn("test-encoding is not supported."));
    }

    /**
     * testValidateByteRange09()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) bean:String:"ああ"<br>
     *         (引数) va:not null<br>
     *         (引数) field:not null<br>
     *                var:minByte="8"<br>
     *                var:maxByte="4"<br>
     *                encoding="UTF-8"<br>
     *                Msg("message","message")<br>
     *         (引数) errors:not null<br>
     *         (引数) validator:not null<br>
     *         (引数) request:not null<br>
     *
     * <br>
     * 期待値：(戻り値) boolean:false<br>
     *         (状態変化) errors:ActionMessage("message")<br>
     *
     * <br>
     * fieldのminByteの値より、maxByteの値が小さいとき、falseが返却されることを
     * 確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testValidateByteRange09() throws Exception {
        //テストデータ設定
        // ++++ beanオブジェクト ++++
        String bean = "ああ";
        // ++++ 検証設定オブジェクト
        ValidatorAction va = new ValidatorAction();
        va.setName("message");
        // ++++ 検証フィールド情報
        Field field = new Field();
        Var var = new Var();
        var.setName("maxByte");
        var.setValue("4");
        field.addVar(var);
        var = new Var();
        var.setName("minByte");
        var.setValue("8");
        field.addVar(var);
        var = new Var();
        var.setName("encoding");
        var.setValue("UTF-8");
        field.addVar(var);
        // メッセージ設定
        Msg msg = new Msg();
        msg.setKey("message");
        msg.setName("message");
        msg.setResource(false);
        field.addMsg(msg);
        // エラー情報
        ActionMessages errors = new ActionMessages();
        // 擬似HTTPリクエスト
        MockHttpServletRequest request = new MockHttpServletRequest();
        // ValidatorResourcesインスタンス
        ValidatorResources validatorResources = new ValidatorResources();
        // Validatorインスタンス
        Validator validator = new Validator(validatorResources);

        // テスト実行
        boolean result =
            FieldChecksEx.validateByteRange(
                bean,
                va,
                field,
                errors,
                validator,
                request);
        // テスト結果確認
        // falseが返却されていること。
        assertFalse(result);
        // エラー情報が1件であること。
        assertEquals(1, errors.size());

        // エラーオブジェクトのチェック
        Iterator it = errors.get();
        ActionMessage message = (ActionMessage) it.next();
        assertEquals("message", message.getKey());
    }

    /**
     * testValidateByteRange10()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) bean:String:"ああ"<br>
     *         (引数) va:not null<br>
     *         (引数) field:not null<br>
     *                var:minByte="4"<br>
     *                var:maxByte="4"<br>
     *                encoding="Windows-31J"<br>
     *                Msg("message","message")<br>
     *         (引数) errors:not null<br>
     *         (引数) validator:not null<br>
     *         (引数) request:not null<br>
     *
     * <br>
     * 期待値：(戻り値) boolean:true<br>
     *         (状態変化) errors:not null<br>
     *                    (空の要素)<br>
     *
     * <br>
     * fieldのminByteとmaxByteの値が一致し、引数のbeanの桁数もその値と一致する
     * 場合、trueが返却されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testValidateByteRange10() throws Exception {
        //テストデータ設定
        // ++++ beanオブジェクト ++++
        String bean = "ああ";
        // ++++ 検証設定オブジェクト
        ValidatorAction va = new ValidatorAction();
        va.setName("message");
        // ++++ 検証フィールド情報
        Field field = new Field();
        Var var = new Var();
        var.setName("maxByte");
        var.setValue("4");
        field.addVar(var);
        var = new Var();
        var.setName("minByte");
        var.setValue("4");
        field.addVar(var);
        var = new Var();
        var.setName("encoding");
        var.setValue("Windows-31J");
        field.addVar(var);
        // メッセージ設定
        Msg msg = new Msg();
        msg.setKey("message");
        msg.setName("message");
        msg.setResource(false);
        field.addMsg(msg);
        // エラー情報
        ActionMessages errors = new ActionMessages();
        // 擬似HTTPリクエスト
        MockHttpServletRequest request = new MockHttpServletRequest();
        // ValidatorResourcesインスタンス
        ValidatorResources validatorResources = new ValidatorResources();
        // Validatorインスタンス
        Validator validator = new Validator(validatorResources);

        // テスト実行
        boolean result =
            FieldChecksEx.validateByteRange(
                bean,
                va,
                field,
                errors,
                validator,
                request);
        // テスト結果確認
        // trueが返却されていること。
        assertTrue(result);
        // エラー情報が空であること。
        assertTrue(errors.isEmpty());
    }

    /**
     * testValidateByteRange11()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) bean:Map:["field1"="あい"]<br>
     *         (引数) va:not null<br>
     *         (引数) field:not null<br>
     *                var:minByte="1"<br>
     *                var:maxByte="10"<br>
     *                var:encoding="UTF-8"<br>
     *                Msg("message","message")<br>
     *                property属性="field1"<br>
     *         (引数) errors:not null<br>
     *                (空の要素)<br>
     *         (引数) validator:not null<br>
     *         (引数) request:not null<br>
     *
     * <br>
     * 期待値：(戻り値) boolean:true<br>
     *         (状態変化) errors:not null<br>
     *                    (空の要素)<br>
     *
     * <br>
     * 引数のbeanがString型ではない場合、fieldから取得した名前のプロパティに
     * 対してチェックを行うことを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testValidateByteRange11() throws Exception {
        //テストデータ設定
        // ++++ beanオブジェクト ++++
        Map<String, String> bean = new HashMap<String, String>();
        bean.put("field1", "あい");
        // ++++ 検証設定オブジェクト
        ValidatorAction va = new ValidatorAction();
        va.setName("message");
        // ++++ 検証フィールド情報
        Field field = new Field();
        field.setProperty("field1");
        Var var = new Var();
        var.setName("maxByte");
        var.setValue("10");
        field.addVar(var);
        var = new Var();
        var.setName("minByte");
        var.setValue("1");
        field.addVar(var);
        var = new Var();
        var.setName("encoding");
        var.setValue("UTF-8");
        field.addVar(var);
        // メッセージ設定
        Msg msg = new Msg();
        msg.setKey("message");
        msg.setName("message");
        msg.setResource(false);
        field.addMsg(msg);
        // エラー情報
        ActionMessages errors = new ActionMessages();
        // 擬似HTTPリクエスト
        MockHttpServletRequest request = new MockHttpServletRequest();
        // ValidatorResourcesインスタンス
        ValidatorResources validatorResources = new ValidatorResources();
        // Validatorインスタンス
        Validator validator = new Validator(validatorResources);

        // テスト実行
        boolean result =
            FieldChecksEx.validateByteRange(
                bean,
                va,
                field,
                errors,
                validator,
                request);
        // テスト結果確認
        // trueが返却されていること。
        assertTrue(result);
        // エラー情報が空であること。
        assertTrue(errors.isEmpty());
    }

    /**
     * testValidateByteRange12()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) bean:Map:["field1"="1234"]<br>
     *         (引数) va:not null<br>
     *         (引数) field:not null<br>
     *                var:minByte="1"<br>
     *                var:maxByte="3"<br>
     *                var:encoding="Windows-31J"<br>
     *                Msg("message","message")<br>
     *                property属性="field1"<br>
     *         (引数) errors:not null<br>
     *                (空の要素)<br>
     *         (引数) validator:not null<br>
     *         (引数) request:not null<br>
     *
     * <br>
     * 期待値：(戻り値) boolean:false<br>
     *         (状態変化) errors:ActionMessage("message")<br>
     *
     * <br>
     * 引数のbeanがString型ではない場合、fieldから取得した名前のプロパティに
     * 対してチェックを行うことを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testValidateByteRange12() throws Exception {
        //テストデータ設定
        // ++++ beanオブジェクト ++++
        Map<String, String> bean = new HashMap<String, String>();
        bean.put("field1", "1234");
        // ++++ 検証設定オブジェクト
        ValidatorAction va = new ValidatorAction();
        va.setName("message");
        // ++++ 検証フィールド情報
        Field field = new Field();
        field.setProperty("field1");
        Var var = new Var();
        var.setName("maxByte");
        var.setValue("3");
        field.addVar(var);
        var = new Var();
        var.setName("minByte");
        var.setValue("1");
        field.addVar(var);
        var = new Var();
        var.setName("encoding");
        var.setValue("Windows-31J");
        field.addVar(var);
        // メッセージ設定
        Msg msg = new Msg();
        msg.setKey("message");
        msg.setName("message");
        msg.setResource(false);
        field.addMsg(msg);
        // エラー情報
        ActionMessages errors = new ActionMessages();
        // 擬似HTTPリクエスト
        MockHttpServletRequest request = new MockHttpServletRequest();
        // ValidatorResourcesインスタンス
        ValidatorResources validatorResources = new ValidatorResources();
        // Validatorインスタンス
        Validator validator = new Validator(validatorResources);

        // テスト実行
        boolean result =
            FieldChecksEx.validateByteRange(
                bean,
                va,
                field,
                errors,
                validator,
                request);
        // テスト結果確認
        // falseが返却されていること。
        assertFalse(result);
        // エラー情報が1件であること。
        assertEquals(1, errors.size());

        // エラーオブジェクトのチェック
        Iterator it = errors.get();
        ActionMessage message = (ActionMessage) it.next();
        assertEquals("message", message.getKey());
    }

    /**
     * testValidateByteRange13()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) bean:Map:["field1"="123"]<br>
     *         (引数) va:not null<br>
     *         (引数) field:not null<br>
     *                var:minByte="1"<br>
     *                var:maxByte="10"<br>
     *                var:encoding="UTF-8"<br>
     *                Msg("message","message")<br>
     *                property属性="field2"<br>
     *         (引数) errors:not null<br>
     *                (空の要素)<br>
     *         (引数) validator:not null<br>
     *         (引数) request:not null<br>
     *
     * <br>
     * 期待値：(戻り値) boolean:true<br>
     *         (状態変化) errors:not null<br>
     *                    (空の要素)<br>
     *
     * <br>
     * 引数のbeanがString型ではなく、fieldから取得した名前のプロパティが
     * 存在しない場合、trueが取得できることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testValidateByteRange13() throws Exception {
        //テストデータ設定
        // ++++ beanオブジェクト ++++
        Map<String, String> bean = new HashMap<String, String>();
        bean.put("field1", "123");
        // ++++ 検証設定オブジェクト
        ValidatorAction va = new ValidatorAction();
        va.setName("message");
        // ++++ 検証フィールド情報
        Field field = new Field();
        field.setProperty("field2");
        Var var = new Var();
        var.setName("maxByte");
        var.setValue("10");
        field.addVar(var);
        var = new Var();
        var.setName("minByte");
        var.setValue("1");
        field.addVar(var);
        var = new Var();
        var.setName("encoding");
        var.setValue("UTF-8");
        field.addVar(var);
        // メッセージ設定
        Msg msg = new Msg();
        msg.setKey("message");
        msg.setName("message");
        msg.setResource(false);
        field.addMsg(msg);
        // エラー情報
        ActionMessages errors = new ActionMessages();
        // 擬似HTTPリクエスト
        MockHttpServletRequest request = new MockHttpServletRequest();
        // ValidatorResourcesインスタンス
        ValidatorResources validatorResources = new ValidatorResources();
        // Validatorインスタンス
        Validator validator = new Validator(validatorResources);

        // テスト実行
        boolean result =
            FieldChecksEx.validateByteRange(
                bean,
                va,
                field,
                errors,
                validator,
                request);
        // テスト結果確認
        // trueが返却されていること。
        assertTrue(result);
        // エラー情報が空であること。
        assertTrue(errors.isEmpty());
    }

    /**
     * testValidateMultiField01()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：C,F
     * <br><br>
     * 入力値：(引数) bean:null<br>
     *         (引数) va:not null<br>
     *         (引数) field:not null<br>
     *                Msg("message","message")<br>
     *         (引数) errors:not null<br>
     *                (空の要素)<br>
     *         (引数) validator:not null<br>
     *         (引数) request:not null<br>
     *
     * <br>
     * 期待値：(戻り値) boolean:true<br>
     *         (状態変化) MultiFieldValidator:呼び出されないこと<br>
     *         (状態変化) ログ:ログレベル：<br>
     *                    エラー<br>
     *                    メッセージ：<br>
     *                    bean is null.<br>
     *         (状態変化) errors:not null<br>
     *                    (空の要素)<br>
     *
     * <br>
     * 引数のbeanがnullのとき、エラーログを出力してtrueが返却されることを
     * 確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testValidateMultiField01() throws Exception {
        //テストデータ設定
        // ++++ beanオブジェクト ++++
        String bean = null;
        // ++++ 検証設定オブジェクト
        ValidatorAction va = new ValidatorAction();
        // ++++ 検証フィールド情報
        Field field = new Field();
        // メッセージ設定
        Msg msg = new Msg();
        msg.setKey("message");
        msg.setName("message");
        msg.setResource(false);
        field.addMsg(msg);
        // エラー情報
        ActionMessages errors = new ActionMessages();
        // 擬似HTTPリクエスト
        MockHttpServletRequest request = new MockHttpServletRequest();
        // ValidatorResourcesインスタンス
        ValidatorResources validatorResources = new ValidatorResources();
        // Validatorインスタンス
        Validator validator = new Validator(validatorResources);

        // テスト実行
        boolean result =
            FieldChecksEx.validateMultiField(
                bean,
                va,
                field,
                errors,
                validator,
                request);
        // テスト結果確認
        // trueが返却されていること。
        assertTrue(result);
        // エラー情報が空であること。
        assertTrue(errors.isEmpty());

        // エラーログ確認
        assertTrue(LogUTUtil.checkError("bean is null."));
    }

//    /**
//     * testValidateMultiField02()
//     * <br><br>
//     *
//     * (正常系)
//     * <br>
//     * 観点：C,F
//     * <br><br>
//     * 入力値：(引数) bean:""<br>
//     *         (引数) va:not null<br>
//     *         (引数) field:not null<br>
//     *                Msg("message","message")<br>
//     *         (引数) errors:not null<br>
//     *                (空の要素)<br>
//     *         (引数) validator:not null<br>
//     *         (引数) request:not null<br>
//     *
//     * <br>
//     * 期待値：(戻り値) boolean:true<br>
//     *         (状態変化) MultiFieldValidator:呼び出されないこと<br>
//     *         (状態変化) errors:not null<br>
//     *                    (空の要素)<br>
//     *
//     * <br>
//     * 引数のbeanが空文字のとき、trueが返却されることを確認する。
//     * <br>
//     *
//     * @throws Exception このメソッドで発生した例外
//     */
//    public void testValidateMultiField02() throws Exception {
//        // テストケース削除
//    }

    /**
     * testValidateMultiField03()
     * <br><br>
     *
     * (異常系)
     * <br>
     * 観点：C,F
     * <br><br>
     * 入力値：(引数) bean:""<br>
     *         (引数) va:not null<br>
     *         (引数) field:not null<br>
     *                var:multiFieldValidator=null<br>
     *                Msg("message","message")<br>
     *         (引数) errors:not null<br>
     *                (空の要素)<br>
     *         (引数) validator:not null<br>
     *         (引数) request:not null<br>
     *
     * <br>
     * 期待値：(状態変化) MultiFieldValidator:呼び出されないこと<br>
     *         (状態変化) 例外:IllegalArgumentException<br>
     *                    メッセージ：<br>
     *                    "var value[multiFieldValidator] is required."<br>
     *         (状態変化) ログ:ログレベル：エラー<br>
     *                    メッセージ：<br>
     *                    "var value[multiFieldValidator] is required."<br>
     *         (状態変化) errors:not null<br>
     *                    (空の要素)<br>
     *
     * <br>
     * beanが空文字のとき、チェック処理が続行されること。<br>
     * fieldのmultiFieldValidatorがnullのとき、IllegalArgumentExceptionが
     * 発生することを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testValidateMultiField03() throws Exception {
        //テストデータ設定
        // ++++ beanオブジェクト ++++
        String bean = "";
        // ++++ 検証設定オブジェクト
        ValidatorAction va = new ValidatorAction();
        // ++++ 検証フィールド情報
        Field field = new Field();
        Var var = new Var();
        var.setName("multiFieldValidator");
        var.setValue(null);
        field.addVar(var);
        // メッセージ設定
        Msg msg = new Msg();
        msg.setKey("message");
        msg.setName("message");
        msg.setResource(false);
        field.addMsg(msg);
        // エラー情報
        ActionMessages errors = new ActionMessages();
        // 擬似HTTPリクエスト
        MockHttpServletRequest request = new MockHttpServletRequest();
        // ValidatorResourcesインスタンス
        ValidatorResources validatorResources = new ValidatorResources();
        // Validatorインスタンス
        Validator validator = new Validator(validatorResources);

        // テスト実行
        try {
            FieldChecksEx.validateMultiField(
                    bean,
                    va,
                    field,
                    errors,
                    validator,
                    request);
            fail();
        } catch (IllegalArgumentException e) {
            assertTrue(errors.isEmpty());
            assertEquals("var value[multiFieldValidator] is required.",
                    e.getMessage());
            assertTrue(LogUTUtil.checkError(
                    "var value[multiFieldValidator] is required."));
        }
    }

    /**
     * testValidateMultiField04()
     * <br><br>
     *
     * (異常系)
     * <br>
     * 観点：F,G
     * <br><br>
     * 入力値：(引数) bean:"abc"<br>
     *         (引数) va:not null<br>
     *         (引数) field:not null<br>
     *                var:multiFieldValidator=""<br>
     *                Msg("message","message")<br>
     *         (引数) errors:not null<br>
     *                (空の要素)<br>
     *         (引数) validator:not null<br>
     *         (引数) request:not null<br>
     *
     * <br>
     * 期待値：(状態変化) MultiFieldValidator:呼び出されないこと<br>
     *         (状態変化) 例外:IllegalArgumentException<br>
     *                    メッセージ：<br>
     *                    "var value[multiFieldValidator] is required."<br>
     *         (状態変化) ログ:ログレベル：エラー<br>
     *                    メッセージ：<br>
     *                    "var value[multiFieldValidator] is required."<br>
     *         (状態変化) errors:not null<br>
     *                    (空の要素)<br>
     *
     * <br>
     * fieldのmultiFieldValidatorが空文字のとき、
     * IllegalArgumentExceptionが発生することを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testValidateMultiField04() throws Exception {
        //テストデータ設定
        // ++++ beanオブジェクト ++++
        String bean = "abc";
        // ++++ 検証設定オブジェクト
        ValidatorAction va = new ValidatorAction();
        // ++++ 検証フィールド情報
        Field field = new Field();
        Var var = new Var();
        var.setName("multiFieldValidator");
        var.setValue("");
        field.addVar(var);
        // メッセージ設定
        Msg msg = new Msg();
        msg.setKey("message");
        msg.setName("message");
        msg.setResource(false);
        field.addMsg(msg);
        // エラー情報
        ActionMessages errors = new ActionMessages();
        // 擬似HTTPリクエスト
        MockHttpServletRequest request = new MockHttpServletRequest();
        // ValidatorResourcesインスタンス
        ValidatorResources validatorResources = new ValidatorResources();
        // Validatorインスタンス
        Validator validator = new Validator(validatorResources);

        // テスト実行
        try {
            FieldChecksEx.validateMultiField(
                    bean,
                    va,
                    field,
                    errors,
                    validator,
                    request);
            fail();
        } catch (IllegalArgumentException e) {
            assertTrue(errors.isEmpty());
            assertEquals("var value[multiFieldValidator] is required.",
                    e.getMessage());
            assertTrue(LogUTUtil.checkError(
                    "var value[multiFieldValidator] is required."));
        }
    }

    /**
     * testValidateMultiField05()
     * <br><br>
     *
     * (異常系)
     * <br>
     * 観点：F,G
     * <br><br>
     * 入力値：(引数) bean:"abc"<br>
     *         (引数) va:not null<br>
     *         (引数) field:not null<br>
     *                var:multiFieldValidator="abc"<br>
     *                Msg("message","message")<br>
     *         (引数) errors:not null<br>
     *                (空の要素)<br>
     *         (引数) validator:not null<br>
     *         (引数) request:not null<br>
     *
     * <br>
     * 期待値：(状態変化) MultiFieldValidator:呼び出されないこと<br>
     *         (状態変化) 例外:IllegalArgumentException<br>
     *                    メッセージ：<br>
     *                    "var value[multiFieldValidator] is invalid."<br>
     *         (状態変化) ログ:ログレベル：エラー<br>
     *                    メッセージ：<br>
     *                    "var value[multiFieldValidator] is invalid."<br>
     *                    例外：<br>
     *                    ClassLoadException(ClassNotFoundException)
     *         (状態変化) errors:not null<br>
     *                    (空の要素)<br>
     *
     * <br>
     * fieldのmultiFieldValidatorがクラスパス上に存在しないクラス名のとき、
     * IllegalArgumentExceptionが発生することを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testValidateMultiField05() throws Exception {
        //テストデータ設定
        // ++++ beanオブジェクト ++++
        String bean = "abc";
        // ++++ 検証設定オブジェクト
        ValidatorAction va = new ValidatorAction();
        // ++++ 検証フィールド情報
        Field field = new Field();
        Var var = new Var();
        var.setName("multiFieldValidator");
        var.setValue("abc");
        field.addVar(var);
        // メッセージ設定
        Msg msg = new Msg();
        msg.setKey("message");
        msg.setName("message");
        msg.setResource(false);
        field.addMsg(msg);
        // エラー情報
        ActionMessages errors = new ActionMessages();
        // 擬似HTTPリクエスト
        MockHttpServletRequest request = new MockHttpServletRequest();
        // ValidatorResourcesインスタンス
        ValidatorResources validatorResources = new ValidatorResources();
        // Validatorインスタンス
        Validator validator = new Validator(validatorResources);

        // テスト実行
        try {
            FieldChecksEx.validateMultiField(
                    bean,
                    va,
                    field,
                    errors,
                    validator,
                    request);
            fail();
        } catch (IllegalArgumentException e) {
            assertTrue(errors.isEmpty());
            assertEquals("var value[multiFieldValidator] is invalid.",
                    e.getMessage());
            assertTrue(LogUTUtil.checkError(
                    "var value[multiFieldValidator] is invalid.",
                    new ClassLoadException(new ClassNotFoundException())));
        }
    }

    /**
     * testValidateMultiField06()
     * <br><br>
     *
     * (異常系)
     * <br>
     * 観点：F,G
     * <br><br>
     * 入力値：(引数) bean:"abc"<br>
     *         (引数) va:not null<br>
     *         (引数) field:not null<br>
     *                var:multiFieldValidator="java.lang.String"<br>
     *                Msg("message","message")<br>
     *         (引数) errors:not null<br>
     *                (空の要素)<br>
     *         (引数) validator:not null<br>
     *         (引数) request:not null<br>
     *
     * <br>
     * 期待値：(状態変化) MultiFieldValidator:呼び出されないこと<br>
     *         (状態変化) 例外:IllegalArgumentException<br>
     *                    メッセージ：<br>
     *                    "var value[multiFieldValidator] is invalid."<br>
     *         (状態変化) ログ:ログレベル：エラー<br>
     *                    メッセージ：<br>
     *                    "var value[multiFieldValidator] is invalid."<br>
     *                    例外：<br>
     *                    ClassCastException<br>
     *         (状態変化) errors:not null<br>
     *                    (空の要素)<br>
     *
     * <br>
     * fieldのmultiFieldValidatorがMultiFieldValidatorを実装していないクラス名の
     * とき、IllegalArgumentExceptionが発生することを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testValidateMultiField06() throws Exception {
        //テストデータ設定
        // ++++ beanオブジェクト ++++
        String bean = "abc";
        // ++++ 検証設定オブジェクト
        ValidatorAction va = new ValidatorAction();
        // ++++ 検証フィールド情報
        Field field = new Field();
        Var var = new Var();
        var.setName("multiFieldValidator");
        var.setValue("java.lang.String");
        field.addVar(var);
        // メッセージ設定
        Msg msg = new Msg();
        msg.setKey("message");
        msg.setName("message");
        msg.setResource(false);
        field.addMsg(msg);
        // エラー情報
        ActionMessages errors = new ActionMessages();
        // 擬似HTTPリクエスト
        MockHttpServletRequest request = new MockHttpServletRequest();
        // ValidatorResourcesインスタンス
        ValidatorResources validatorResources = new ValidatorResources();
        // Validatorインスタンス
        Validator validator = new Validator(validatorResources);

        // テスト実行
        try {
            FieldChecksEx.validateMultiField(
                    bean,
                    va,
                    field,
                    errors,
                    validator,
                    request);
            fail();
        } catch (IllegalArgumentException e) {
            assertTrue(errors.isEmpty());
            assertEquals("var value[multiFieldValidator] is invalid.",
                    e.getMessage());
            assertTrue(LogUTUtil.checkError(
                    "var value[multiFieldValidator] is invalid.",
                    new ClassCastException()));
        }
    }

    /**
     * testValidateMultiField07()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) bean:"abc"<br>
     *         (引数) va:not null<br>
     *         (引数) field:not null<br>
     *                var:multiFieldValidator=<br>
     *                "jp.terasoluna.fw.web.struts.form.FieldChecksEx_MultiFieldValidatorImpl01"<br>
     *                var:fields=null<br>
     *                Msg("message","message")<br>
     *         (引数) errors:not null<br>
     *                (空の要素)<br>
     *         (引数) validator:not null<br>
     *         (引数) request:not null<br>
     *         (引数) MultiFieldValidator.validate():true<br>
     *
     * <br>
     * 期待値：(戻り値) boolean:true<br>
     *         (状態変化) MultiFieldValidator:引数が"abc",要素0の配列で呼び出されること。<br>
     *         (状態変化) errors:not null<br>
     *                    (空の要素)<br>
     *
     * <br>
     * fieldのfieldsがnullの場合、MultiFieldValidatorの第二引数に空の配列が
     * 渡されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testValidateMultiField07() throws Exception {
        //テストデータ設定
        // ++++ beanオブジェクト ++++
        String bean = "abc";
        // ++++ 検証設定オブジェクト
        ValidatorAction va = new ValidatorAction();
        // ++++ 検証フィールド情報
        Field field = new Field();
        Var var = new Var();
        var.setName("multiFieldValidator");
        var.setValue("jp.terasoluna.fw.web.struts.form." +
                "FieldChecksEx_MultiFieldValidatorImpl01");
        field.addVar(var);
        var = new Var();
        var.setName("fields");
        var.setValue(null);
        field.addVar(var);
        // メッセージ設定
        Msg msg = new Msg();
        msg.setKey("message");
        msg.setName("message");
        msg.setResource(false);
        field.addMsg(msg);
        // エラー情報
        ActionMessages errors = new ActionMessages();
        // 擬似HTTPリクエスト
        MockHttpServletRequest request = new MockHttpServletRequest();
        // ValidatorResourcesインスタンス
        ValidatorResources validatorResources = new ValidatorResources();
        // Validatorインスタンス
        Validator validator = new Validator(validatorResources);

        // MultiFieldValidatorの戻り値を設定
        FieldChecksEx_MultiFieldValidatorImpl01.result = true;

        // テスト実行
        boolean result = FieldChecksEx.validateMultiField(
                bean,
                va,
                field,
                errors,
                validator,
                request);

        // 結果確認
        assertTrue(result);
        assertTrue(errors.isEmpty());

        // MultiFieldValidatorの呼び出し、引数確認
        assertEquals(1,
                FieldChecksEx_MultiFieldValidatorImpl01.validateCalledCount);
        assertEquals("abc", FieldChecksEx_MultiFieldValidatorImpl01.value);
        assertEquals(0, FieldChecksEx_MultiFieldValidatorImpl01.fields.length);

    }

    /**
     * testValidateMultiField08()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) bean:"abc"<br>
     *         (引数) va:not null<br>
     *         (引数) field:not null<br>
     *                var:multiFieldValidator=<br>
     *                "jp.terasoluna.fw.web.struts.form.FieldChecksEx_MultiFieldValidatorImpl01"<br>
     *                var:fields=""<br>
     *                Msg("message","message")<br>
     *         (引数) errors:not null<br>
     *                (空の要素)<br>
     *         (引数) validator:not null<br>
     *         (引数) request:not null<br>
     *         (引数) MultiFieldValidator.validate():true<br>
     *
     * <br>
     * 期待値：(戻り値) boolean:true<br>
     *         (状態変化) MultiFieldValidator:引数が"abc",要素0の配列で呼び出されること。<br>
     *         (状態変化) errors:not null<br>
     *                    (空の要素)<br>
     *
     * <br>
     * fieldのfieldsがnullの場合、MultiFieldValidatorの第二引数に空の配列が
     * 渡されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testValidateMultiField08() throws Exception {
        //テストデータ設定
        // ++++ beanオブジェクト ++++
        String bean = "abc";
        // ++++ 検証設定オブジェクト
        ValidatorAction va = new ValidatorAction();
        // ++++ 検証フィールド情報
        Field field = new Field();
        Var var = new Var();
        var.setName("multiFieldValidator");
        var.setValue("jp.terasoluna.fw.web.struts.form." +
                "FieldChecksEx_MultiFieldValidatorImpl01");
        field.addVar(var);
        var = new Var();
        var.setName("fields");
        var.setValue("");
        field.addVar(var);
        // メッセージ設定
        Msg msg = new Msg();
        msg.setKey("message");
        msg.setName("message");
        msg.setResource(false);
        field.addMsg(msg);
        // エラー情報
        ActionMessages errors = new ActionMessages();
        // 擬似HTTPリクエスト
        MockHttpServletRequest request = new MockHttpServletRequest();
        // ValidatorResourcesインスタンス
        ValidatorResources validatorResources = new ValidatorResources();
        // Validatorインスタンス
        Validator validator = new Validator(validatorResources);

        // MultiFieldValidatorの戻り値を設定
        FieldChecksEx_MultiFieldValidatorImpl01.result = true;

        // テスト実行
        boolean result = FieldChecksEx.validateMultiField(
                bean,
                va,
                field,
                errors,
                validator,
                request);

        // 結果確認
        assertTrue(result);
        assertTrue(errors.isEmpty());

        // MultiFieldValidatorの呼び出し、引数確認
        assertEquals(1,
                FieldChecksEx_MultiFieldValidatorImpl01.validateCalledCount);
        assertEquals("abc", FieldChecksEx_MultiFieldValidatorImpl01.value);
        assertEquals(0, FieldChecksEx_MultiFieldValidatorImpl01.fields.length);

    }

    /**
     * testValidateMultiField09()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) bean:Bean<br>
     *                field1="abc"<br>
     *                field2="def"<br>
     *         (引数) va:not null<br>
     *         (引数) field:not null<br>
     *                var:multiFieldValidator=<br>
     *                "jp.terasoluna.fw.web.struts.form.FieldCheckEx_MultiFieldValidatorImpl01"<br>
     *                var:fields="field2"<br>
     *                var:property="field1"<br>
     *                Msg("message","message")<br>
     *         (引数) errors:not null<br>
     *                (空の要素)<br>
     *         (引数) validator:not null<br>
     *         (引数) request:not null<br>
     *         (引数) MultiFieldValidator.validate():true<br>
     *
     * <br>
     * 期待値：(戻り値) boolean:true<br>
     *         (状態変化) MultiFieldValidator:引数が"abc",<br>
     *                    配列{"def"}<br>
     *                    で呼び出されること。<br>
     *         (状態変化) errors:not null<br>
     *                    (空の要素)<br>
     *
     * <br>
     * fieldsにカンマ区切り無しの文字列が指定されている場合、
     * その名前のプロパティ値を引数のbeanから取得し、
     * 長さ1の配列としてMultiFieldValidatorの第二引数に渡されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testValidateMultiField09() throws Exception {
        //テストデータ設定
        // ++++ 検証設定オブジェクト
        ValidatorAction va = new ValidatorAction();
        // ++++ 検証フィールド情報
        Field field = new Field();
        Var var = new Var();
        var.setName("multiFieldValidator");
        var.setValue("jp.terasoluna.fw.web.struts.form." +
                "FieldChecksEx_MultiFieldValidatorImpl01");
        field.addVar(var);
        var = new Var();
        var.setName("fields");
        var.setValue("field2");
        field.addVar(var);
        field.setProperty("field1");
        // メッセージ設定
        Msg msg = new Msg();
        msg.setKey("message");
        msg.setName("message");
        msg.setResource(false);
        field.addMsg(msg);
        // エラー情報
        ActionMessages errors = new ActionMessages();
        // 擬似HTTPリクエスト
        MockHttpServletRequest request = new MockHttpServletRequest();
        // ValidatorResourcesインスタンス
        ValidatorResources validatorResources = new ValidatorResources();
        // Validatorインスタンス
        Validator validator = new Validator(validatorResources);

        // MultiFieldValidatorの戻り値を設定
        FieldChecksEx_MultiFieldValidatorImpl01.result = true;

        // テスト実行
        boolean result = FieldChecksEx.validateMultiField(
                javaBean,
                va,
                field,
                errors,
                validator,
                request);

        // 結果確認
        assertTrue(result);
        assertTrue(errors.isEmpty());

        // MultiFieldValidatorの呼び出し、引数確認
        assertEquals(1,
                FieldChecksEx_MultiFieldValidatorImpl01.validateCalledCount);
        assertEquals("abc", FieldChecksEx_MultiFieldValidatorImpl01.value);
        assertEquals(1, FieldChecksEx_MultiFieldValidatorImpl01.fields.length);
        assertEquals("def", FieldChecksEx_MultiFieldValidatorImpl01.fields[0]);

    }

    /**
     * testValidateMultiField10()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) bean:Bean<br>
     *                field1="abc"<br>
     *                field2="def"<br>
     *                field3="ghi"<br>
     *                field4="jkl"<br>
     *         (引数) va:not null<br>
     *         (引数) field:not null<br>
     *                var:multiFieldValidator=<br>
     *                "jp.terasoluna.fw.web.struts.form.FieldCheckEx_MultiFieldValidatorImpl01"<br>
     *                var:fields="field2 ,,test,field3,field4"<br>
     *                var:property="field1"<br>
     *                Msg("message","message")<br>
     *         (引数) errors:not null<br>
     *                ActionMessage("testMessage")<br>
     *         (引数) validator:not null<br>
     *         (引数) request:not null<br>
     *         (引数) MultiFieldValidator.validate():false<br>
     *
     * <br>
     * 期待値：(戻り値) boolean:false<br>
     *         (状態変化) MultiFieldValidator:引数が"abc",<br>
     *                    配列{"def",null,"ghi","jkl"}<br>
     *                    で呼び出されること。<br>
     *         (状態変化) errors:ActionMessage("testMessage")<br>
     *                    ActionMessage("message")<br>
     *
     * <br>
     * fieldsにカンマ区切りの文字列が指定されている場合、
     * カンマで区切られた全ての名前のプロパティ値を引数のbeanから取得し、
     * 配列としてMultiFieldValidatorの第二引数に渡されることを確認する。<br>
     * MultiFieldValidatorのvalidateメソッドがfalseを返却する場合、
     * エラーメッセージを追加してfalseが返却されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testValidateMultiField10() throws Exception {
        //テストデータ設定
        // ++++ 検証設定オブジェクト
        ValidatorAction va = new ValidatorAction();
        va.setName("message");
        // ++++ 検証フィールド情報
        Field field = new Field();
        Var var = new Var();
        var.setName("multiFieldValidator");
        var.setValue("jp.terasoluna.fw.web.struts.form." +
                "FieldChecksEx_MultiFieldValidatorImpl01");
        field.addVar(var);
        var = new Var();
        var.setName("fields");
        var.setValue("field2 ,,test,field3,field4");
        field.addVar(var);
        field.setProperty("field1");
        // メッセージ設定
        Msg msg = new Msg();
        msg.setKey("message");
        msg.setName("message");
        msg.setResource(false);
        field.addMsg(msg);
        // エラー情報
        ActionMessages errors = new ActionMessages();
        ActionMessage error = new ActionMessage("testMessage");
        errors.add(Globals.ERROR_KEY, error);
        // 擬似HTTPリクエスト
        MockHttpServletRequest request = new MockHttpServletRequest();
        // ValidatorResourcesインスタンス
        ValidatorResources validatorResources = new ValidatorResources();
        // Validatorインスタンス
        Validator validator = new Validator(validatorResources);

        // MultiFieldValidatorの戻り値を設定
        FieldChecksEx_MultiFieldValidatorImpl01.result = false;

        // テスト実行
        boolean result = FieldChecksEx.validateMultiField(
                javaBean,
                va,
                field,
                errors,
                validator,
                request);

        // 結果確認
        assertFalse(result);
        assertEquals(2, errors.size());

        // メッセージオブジェクトのチェック
        Iterator it = errors.get();
        List<String> list = new ArrayList<String>();
        while (it.hasNext()) {
            ActionMessage message = (ActionMessage) it.next();
            list.add(message.getKey());
        }
        assertTrue(list.contains("testMessage"));
        assertTrue(list.contains("message"));

        // MultiFieldValidatorの呼び出し、引数確認
        assertEquals(1,
                FieldChecksEx_MultiFieldValidatorImpl01.validateCalledCount);
        assertEquals("abc", FieldChecksEx_MultiFieldValidatorImpl01.value);
        assertEquals(4, FieldChecksEx_MultiFieldValidatorImpl01.fields.length);
        assertEquals("def", FieldChecksEx_MultiFieldValidatorImpl01.fields[0]);
        assertNull(FieldChecksEx_MultiFieldValidatorImpl01.fields[1]);
        assertEquals("ghi", FieldChecksEx_MultiFieldValidatorImpl01.fields[2]);
        assertEquals("jkl", FieldChecksEx_MultiFieldValidatorImpl01.fields[3]);

    }

}
