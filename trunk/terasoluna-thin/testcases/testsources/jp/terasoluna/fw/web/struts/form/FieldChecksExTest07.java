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
public class FieldChecksExTest07 extends PropertyTestCase {

    /**
     * このテストケースを実行する為の
     * GUI アプリケーションを起動する。
     *
     * @param args java コマンドに設定されたパラメータ
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(FieldChecksExTest07.class);
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
    public FieldChecksExTest07(String name) {
        super(name);
    }

    /**
     * testValidateNumber01()
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
     *         (状態変化) ログ:ログレベル：<br>
     *                    エラー<br>
     *                    メッセージ：<br>
     *                    bean is null.<br>
     *         (状態変化) errors:not null<br>
     *                    (空の要素)<br>
     *
     * <br>
     * 引数のbeanがnullの場合、trueが返却され、
     * エラーメッセージが追加されないことを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testValidateNumber01() throws Exception {
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
            FieldChecksEx.validateNumber(
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
     * testValidateNumber02()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：C,F
     * <br><br>
     * 入力値：(引数) bean:""<br>
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
     *         (状態変化) errors:not null<br>
     *                    (空の要素)<br>
     *
     * <br>
     * 引数のbeanが空文字の場合、trueが返却され、
     * エラーメッセージが追加されないことを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testValidateNumber02() throws Exception {
        //テストデータ設定
        // ++++ beanオブジェクト ++++
        String bean = "";
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
            FieldChecksEx.validateNumber(
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
     * testValidateNumber03()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) bean:String:"123.12"<br>
     *         (引数) va:not null<br>
     *         (引数) field:not null<br>
     *                var:integerLength=null<br>
     *                var:scale=null<br>
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
     * FieldにintegerLength,scaleが指定されていない場合、
     * trueが返却されることを確認する。
     * エラーメッセージが追加されないことを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testValidateNumber03() throws Exception {
        //テストデータ設定
        // ++++ beanオブジェクト ++++
        String bean = "123.12";
        // ++++ 検証設定オブジェクト
        ValidatorAction va = new ValidatorAction();
        va.setName("message");
        // ++++ 検証フィールド情報
        Field field = new Field();
        // メッセージ設定
        Msg msg = new Msg();
        msg.setKey("message");
        msg.setName("message");
        msg.setResource(false);
        field.addMsg(msg);
        Var var = new Var();
        var.setName("integerLength");
        var.setValue(null);
        field.addVar(var);
        var = new Var();
        var.setName("scale");
        var.setValue(null);
        field.addVar(var);

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
            FieldChecksEx.validateNumber(
                    bean,
                    va,
                    field,
                    errors,
                    validator,
                    request);

        // テスト結果確認
        // falseが返却されていること。
        assertTrue(result);
        // 2つのエラー情報が登録されていること
        assertTrue(errors.isEmpty());
    }

    /**
     * testValidateNumber04()
     * <br><br>
     *
     * (異常系)
     * <br>
     * 観点：C,F
     * <br><br>
     * 入力値：(引数) bean:String:"123.12"<br>
     *         (引数) va:not null<br>
     *         (引数) field:not null<br>
     *                var:integerLength=""<br>
     *                var:scale=""<br>
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
     * FieldのintegerLength,scaleに空文字が指定されている場合、
     * trueが返却されることを確認する。
     * エラーメッセージが追加されないことを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testValidateNumber04() throws Exception {

        //テストデータ設定
        // ++++ beanオブジェクト ++++
        String bean = "123.12";
        // ++++ 検証設定オブジェクト
        ValidatorAction va = new ValidatorAction();
        va.setName("message");
        // ++++ 検証フィールド情報
        Field field = new Field();
        // メッセージ設定
        Msg msg = new Msg();
        msg.setKey("message");
        msg.setName("message");
        msg.setResource(false);
        field.addMsg(msg);
        Var var = new Var();
        var.setName("integerLength");
        var.setValue("");
        field.addVar(var);
        var = new Var();
        var.setName("scale");
        var.setValue("");
        field.addVar(var);

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
            FieldChecksEx.validateNumber(
                    bean,
                    va,
                    field,
                    errors,
                    validator,
                    request);

        // テスト結果確認
        // trueが返却されていること。
        assertTrue(result);
        // エラー情報にエラーオブジェクトが登録されていないこと。
        assertTrue(errors.isEmpty());
    }

    /**
     * testValidateNumber05()
     * <br><br>
     *
     * (異常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) bean:String:"123.12"<br>
     *         (引数) va:not null<br>
     *         (引数) field:not null<br>
     *                var:integerLength="abc"<br>
     *                var:scale="def"<br>
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
     * FieldのintegerLength,scaleに数値に変換できない文字列が
     * 指定されている場合、trueが返却されることを確認する。
     * エラーメッセージが追加されないことを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testValidateNumber05() throws Exception {

        //テストデータ設定
        // ++++ beanオブジェクト ++++
        String bean = "123.12";
        // ++++ 検証設定オブジェクト
        ValidatorAction va = new ValidatorAction();
        va.setName("message");
        // ++++ 検証フィールド情報
        Field field = new Field();
        // メッセージ設定
        Msg msg = new Msg();
        msg.setKey("message");
        msg.setName("message");
        msg.setResource(false);
        field.addMsg(msg);
        Var var = new Var();
        var.setName("integerLength");
        var.setValue("abc");
        field.addVar(var);
        var = new Var();
        var.setName("scale");
        var.setValue("def");
        field.addVar(var);

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
            FieldChecksEx.validateNumber(
                    bean,
                    va,
                    field,
                    errors,
                    validator,
                    request);

        // テスト結果確認
        // trueが返却されていること。
        assertTrue(result);
        // エラー情報にエラーオブジェクトが登録されていないこと。
        assertTrue(errors.isEmpty());
    }

    /**
     * testValidateNumber06()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) bean:String:"123.12"<br>
     *         (引数) va:not null<br>
     *         (引数) field:not null<br>
     *                var:integerLength=null<br>
     *                var:scale="1"<br>
     *                Msg("message","message")<br>
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
     * scaleに数値が指定されており、scaleの数値より、
     * beanの小数点以下の桁が大きいとき、falseが返却されること、
     * エラーメッセージが追加されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testValidateNumber06() throws Exception {

        //テストデータ設定
        // ++++ beanオブジェクト ++++
        String bean = "123.12";
        // ++++ 検証設定オブジェクト
        ValidatorAction va = new ValidatorAction();
        va.setName("message");
        // ++++ 検証フィールド情報
        Field field = new Field();
        // メッセージ設定
        Msg msg = new Msg();
        msg.setKey("message");
        msg.setName("message");
        msg.setResource(false);
        field.addMsg(msg);
        // 整数部設定
        Var varInteger = new Var();
        varInteger.setName("integerLength");
        varInteger.setValue(null);
        field.addVar(varInteger);
        // 小数部設定
        Var varScale = new Var();
        varScale.setName("scale");
        varScale.setValue("1");
        field.addVar(varScale);

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
            FieldChecksEx.validateNumber(
                    bean,
                    va,
                    field,
                    errors,
                    validator,
                    request);

        // テスト結果確認
        // falseが返却されていること。
        assertFalse(result);
        // エラー情報にエラーオブジェクトが登録されていること。
        assertEquals(1, errors.size());

        // メッセージオブジェクトの検査
        Iterator it = errors.get();
        ActionMessage message = (ActionMessage) it.next();
        assertEquals("message", message.getKey());
    }

    /**
     * testValidateNumber07()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) bean:String:"123.12"<br>
     *         (引数) va:not null<br>
     *         (引数) field:not null<br>
     *                var:integerLength=null<br>
     *                var:scale="3"<br>
     *                var:isAccordedScale="true"<br>
     *                Msg("message","message")<br>
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
     * scaleに数値、isAccordedScaleにtrueが指定されており、
     * scaleの数値よりbeanの小数点以下の桁が小さいとき、
     * falseが返却されること、エラーメッセージが追加されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testValidateNumber07() throws Exception {
        //テストデータ設定
        // ++++ beanオブジェクト ++++
        String bean = "123.12";
        // ++++ 検証設定オブジェクト
        ValidatorAction va = new ValidatorAction();
        va.setName("message");
        // ++++ 検証フィールド情報
        Field field = new Field();
        // メッセージ設定
        Msg msg = new Msg();
        msg.setKey("message");
        msg.setName("message");
        msg.setResource(false);
        field.addMsg(msg);
        // 整数部設定
        Var varInteger = new Var();
        varInteger.setName("integerLength");
        varInteger.setValue(null);
        field.addVar(varInteger);
        // 小数部設定
        Var varScale = new Var();
        varScale.setName("scale");
        varScale.setValue("3");
        field.addVar(varScale);
        // 小数部桁数一致指定
        Var varAccorded = new Var();
        varAccorded.setName("isAccordedScale");
        varAccorded.setValue("true");
        field.addVar(varAccorded);

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
            FieldChecksEx.validateNumber(
                    bean,
                    va,
                    field,
                    errors,
                    validator,
                    request);

        // テスト結果確認
        // falseが返却されていること。
        assertFalse(result);
        // エラー情報にエラーオブジェクトが登録されていること。
        assertEquals(1, errors.size());
        // エラー情報に設定されたメッセージが登録されていること。
        Iterator it = errors.get();
        while (it.hasNext()) {
            ActionMessage error = (ActionMessage) it.next();
            assertEquals("message", error.getKey());
        }
    }

    /**
     * testValidateNumber08()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) bean:String:"123.123"<br>
     *         (引数) va:not null<br>
     *         (引数) field:not null<br>
     *                var:integerLength=null<br>
     *                var:scale="3"<br>
     *                var:isAccordedScale="true"<br>
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
     * scaleに数値、isAccordedScaleにtrueが指定されており、scaleの数値と、
     * beanの小数点以下の桁が等しいとき、trueが返却されること、
     * エラーメッセージが追加されないことを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testValidateNumber08() throws Exception {
        //テストデータ設定
        // ++++ beanオブジェクト ++++
        String bean = "123.123";
        // ++++ 検証設定オブジェクト
        ValidatorAction va = new ValidatorAction();
        va.setName("message");
        // ++++ 検証フィールド情報
        Field field = new Field();
        // メッセージ設定
        Msg msg = new Msg();
        msg.setKey("message");
        msg.setName("message");
        msg.setResource(false);
        field.addMsg(msg);
        // 整数部設定
        Var varInteger = new Var();
        varInteger.setName("integerLength");
        varInteger.setValue(null);
        field.addVar(varInteger);
        // 小数部設定
        Var varScale = new Var();
        varScale.setName("scale");
        varScale.setValue("3");
        field.addVar(varScale);
        // 小数部桁数一致指定
        Var varAccorded = new Var();
        varAccorded.setName("isAccordedScale");
        varAccorded.setValue("true");
        field.addVar(varAccorded);

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
            FieldChecksEx.validateNumber(
                    bean,
                    va,
                    field,
                    errors,
                    validator,
                    request);

        // テスト結果確認
        // trueが返却されていること。
        assertTrue(result);
        // エラー情報にエラーオブジェクトが登録されていないこと。
        assertTrue(errors.isEmpty());
    }

    /**
     * testValidateNumber09()
     * <br><br>
     *
     * (異常系)
     * <br>
     * 観点：C,F
     * <br><br>
     * 入力値：(引数) bean:String:"123.12"<br>
     *         (引数) va:not null<br>
     *         (引数) field:not null<br>
     *                var:integerLength=null<br>
     *                var:scale="3"<br>
     *                var:isAccordedScale=""<br>
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
     * scaleに数値、isAccordedScaleに空文字が指定されており、
     * scaleの数値より、beanの小数点以下の桁が小さいとき、
     * trueが返却されること、エラーメッセージが追加されないことを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testValidateNumber09() throws Exception {
        //テストデータ設定
        // ++++ beanオブジェクト ++++
        String bean = "123.12";
        // ++++ 検証設定オブジェクト
        ValidatorAction va = new ValidatorAction();
        va.setName("message");
        // ++++ 検証フィールド情報
        Field field = new Field();
        // メッセージ設定
        Msg msg = new Msg();
        msg.setKey("message");
        msg.setName("message");
        msg.setResource(false);
        field.addMsg(msg);
        // 整数部設定
        Var varInteger = new Var();
        varInteger.setName("integerLength");
        varInteger.setValue(null);
        field.addVar(varInteger);
        // 小数部設定
        Var varScale = new Var();
        varScale.setName("scale");
        varScale.setValue("3");
        field.addVar(varScale);
        // 小数部桁数一致指定
        Var varAccorded = new Var();
        varAccorded.setName("isAccordedScale");
        varAccorded.setValue("");
        field.addVar(varAccorded);

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
            FieldChecksEx.validateNumber(
                    bean,
                    va,
                    field,
                    errors,
                    validator,
                    request);

        // テスト結果確認
        // trueが返却されていること。
        assertTrue(result);
        // エラー情報にエラーオブジェクトが登録されていないこと。
        assertTrue(errors.isEmpty());
    }

    /**
     * testValidateNumber10()
     * <br><br>
     *
     * (異常系)
     * <br>
     * 観点：C,F
     * <br><br>
     * 入力値：(引数) bean:String:"123.12"<br>
     *         (引数) va:not null<br>
     *         (引数) field:not null<br>
     *                var:integerLength=null<br>
     *                var:scale="3"<br>
     *                var:isAccordedScale=null<br>
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
     * scaleに数値、isAccordedScaleにnullが指定されており、scaleの数値より、
     * beanの小数点以下の桁が小さいとき、trueが返却されること、
     * エラーメッセージが追加されないことを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testValidateNumber10() throws Exception {
        //テストデータ設定
        // ++++ beanオブジェクト ++++
        String bean = "123.12";
        // ++++ 検証設定オブジェクト
        ValidatorAction va = new ValidatorAction();
        va.setName("message");
        // ++++ 検証フィールド情報
        Field field = new Field();
        // メッセージ設定
        Msg msg = new Msg();
        msg.setKey("message");
        msg.setName("message");
        msg.setResource(false);
        field.addMsg(msg);
        // 整数部設定
        Var varInteger = new Var();
        varInteger.setName("integerLength");
        varInteger.setValue(null);
        field.addVar(varInteger);
        // 小数部設定
        Var varScale = new Var();
        varScale.setName("scale");
        varScale.setValue("3");
        field.addVar(varScale);
        // 小数部桁数一致指定
        Var varAccorded = new Var();
        varAccorded.setName("isAccordedScale");
        varAccorded.setValue(null);
        field.addVar(varAccorded);

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
            FieldChecksEx.validateNumber(
                    bean,
                    va,
                    field,
                    errors,
                    validator,
                    request);

        // テスト結果確認
        // trueが返却されていること。
        assertTrue(result);
        // エラー情報にエラーオブジェクトが登録されていないこと。
        assertTrue(errors.isEmpty());
    }

    /**
     * testValidateNumber11()
     * <br><br>
     *
     * (異常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) bean:String:"123.12"<br>
     *         (引数) va:not null<br>
     *         (引数) field:not null<br>
     *                var:integerLength=null<br>
     *                var:scale="3"<br>
     *                var:isAccordedScale="abc"<br>
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
     * scaleに数値、isAccordedScaleにtrueではない文字列が指定されており、
     * scaleの数値より、beanの小数点以下の桁が小さいとき、trueが返却されること、
     * エラーメッセージが追加されないことを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testValidateNumber11() throws Exception {
        //テストデータ設定
        // ++++ beanオブジェクト ++++
        String bean = "123.12";
        // ++++ 検証設定オブジェクト
        ValidatorAction va = new ValidatorAction();
        va.setName("message");
        // ++++ 検証フィールド情報
        Field field = new Field();
        // メッセージ設定
        Msg msg = new Msg();
        msg.setKey("message");
        msg.setName("message");
        msg.setResource(false);
        field.addMsg(msg);
        // 整数部設定
        Var varInteger = new Var();
        varInteger.setName("integerLength");
        varInteger.setValue(null);
        field.addVar(varInteger);
        // 小数部設定
        Var varScale = new Var();
        varScale.setName("scale");
        varScale.setValue("3");
        field.addVar(varScale);
        // 小数部桁数一致指定
        Var varAccorded = new Var();
        varAccorded.setName("isAccordedScale");
        varAccorded.setValue("abc");
        field.addVar(varAccorded);

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
            FieldChecksEx.validateNumber(
                    bean,
                    va,
                    field,
                    errors,
                    validator,
                    request);

        // テスト結果確認
        // trueが返却されていること。
        assertTrue(result);
        // エラー情報にエラーオブジェクトが登録されていないこと。
        assertTrue(errors.isEmpty());
    }

    /**
     * testValidateNumber12()
     * <br><br>
     *
     * (異常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) bean:String:"abc"<br>
     *         (引数) va:not null<br>
     *         (引数) field:not null<br>
     *                var:integerLength=null<br>
     *                var:scale="2"<br>
     *                Msg("message","message")<br>
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
     * scaleに数値が指定されており、引数のbeanが数値に変換できない場合、
     * falseが返却されること。エラーメッセージが追加されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testValidateNumber12() throws Exception {
        //テストデータ設定
        // ++++ beanオブジェクト ++++
        String bean = "abc";
        // ++++ 検証設定オブジェクト
        ValidatorAction va = new ValidatorAction();
        va.setName("message");
        // ++++ 検証フィールド情報
        Field field = new Field();
        // メッセージ設定
        Msg msg = new Msg();
        msg.setKey("message");
        msg.setName("message");
        msg.setResource(false);
        field.addMsg(msg);
        // 整数部設定
        Var varInteger = new Var();
        varInteger.setName("integerLength");
        varInteger.setValue(null);
        field.addVar(varInteger);
        // 小数部設定
        Var varScale = new Var();
        varScale.setName("scale");
        varScale.setValue("2");
        field.addVar(varScale);

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
            FieldChecksEx.validateNumber(
                    bean,
                    va,
                    field,
                    errors,
                    validator,
                    request);

        // テスト結果確認
        // falseが返却されていること。
        assertFalse(result);
        // エラー情報にエラーオブジェクトが登録されていないこと。
        assertEquals(1, errors.size());

        // エラーオブジェクトの検査。
        Iterator it = errors.get();
        ActionMessage message = (ActionMessage) it.next();
        assertEquals("message", message.getKey());
    }

    /**
     * testValidateNumber13()
     * <br><br>
     *
     * (異常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) bean:String:"abc.de"<br>
     *         (引数) va:not null<br>
     *         (引数) field:not null<br>
     *                var:integerLength="1"<br>
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
     * 引数のbeanが数値ではない場合、falseが返却されること。
     * エラーメッセージが追加されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testValidateNumber13() throws Exception {
        //テストデータ設定
        // ++++ beanオブジェクト ++++
        String bean = "abc.de";
        // ++++ 検証設定オブジェクト
        ValidatorAction va = new ValidatorAction();
        va.setName("message");
        // ++++ 検証フィールド情報
        Field field = new Field();
        // メッセージ設定
        Msg msg = new Msg();
        msg.setKey("message");
        msg.setName("message");
        msg.setResource(false);
        field.addMsg(msg);
        // 整数部設定
        Var varInteger = new Var();
        varInteger.setName("integerLength");
        varInteger.setValue("1");
        field.addVar(varInteger);

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
            FieldChecksEx.validateNumber(
                    bean,
                    va,
                    field,
                    errors,
                    validator,
                    request);

        // テスト結果確認
        // falseが返却されていること。
        assertFalse(result);
        // エラー情報にエラーオブジェクトが登録されていること。
        assertEquals(2, errors.size());

        // エラーオブジェクトの検査
        Iterator it = errors.get();
        List<String> list = new ArrayList<String>();
        while (it.hasNext()) {
            ActionMessage message = (ActionMessage) it.next();
            list.add(message.getKey());
        }
        assertTrue(list.contains("message"));
        assertTrue(list.contains("testMessage"));
    }

    /**
     * testValidateNumber14()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) bean:String:"123.12"<br>
     *         (引数) va:not null<br>
     *         (引数) field:not null<br>
     *                var:integerLength="1"<br>
     *                var:scale=null<br>
     *                Msg("message","message")<br>
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
     * integerLengthに数値が指定されており、integerLengthの数値より、
     * beanの整数部の桁が大きいとき、falseが返却されること、
     * エラーメッセージが追加されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testValidateNumber14() throws Exception {
        //テストデータ設定
        // ++++ beanオブジェクト ++++
        String bean = "123.12";
        // ++++ 検証設定オブジェクト
        ValidatorAction va = new ValidatorAction();
        va.setName("message");
        // ++++ 検証フィールド情報
        Field field = new Field();
        // メッセージ設定
        Msg msg = new Msg();
        msg.setKey("message");
        msg.setName("message");
        msg.setResource(false);
        field.addMsg(msg);
        // 整数部設定
        Var varInteger = new Var();
        varInteger.setName("integerLength");
        varInteger.setValue("1");
        field.addVar(varInteger);
        // 小数部設定
        Var varScale = new Var();
        varScale.setName("scale");
        varScale.setValue(null);
        field.addVar(varScale);

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
            FieldChecksEx.validateNumber(
                    bean,
                    va,
                    field,
                    errors,
                    validator,
                    request);

        // テスト結果確認
        // falseが返却されていること。
        assertFalse(result);
        // エラー情報にエラーオブジェクトが登録されていないこと。
        assertEquals(1, errors.size());

        // エラーオブジェクトの検査
        Iterator it = errors.get();
        ActionMessage message = (ActionMessage) it.next();
        assertEquals("message", message.getKey());
    }

    /**
     * testValidateNumber15()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) bean:String:"12.12"<br>
     *         (引数) va:not null<br>
     *         (引数) field:not null<br>
     *                var:integerLength="3"<br>
     *                var:isAccordedInteger="true"<br>
     *                var:scale=null<br>
     *                Msg("message","message")<br>
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
     * integerLengthに数値、isAccordedIntegerにtrueが指定されており、
     * integerLengthの数値よりbeanの整数部の桁が小さいとき、
     * falseが返却されること、エラーメッセージが追加されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testValidateNumber15() throws Exception {
        //テストデータ設定
        // ++++ beanオブジェクト ++++
        String bean = "12.12";
        // ++++ 検証設定オブジェクト
        ValidatorAction va = new ValidatorAction();
        va.setName("message");
        // ++++ 検証フィールド情報
        Field field = new Field();
        // メッセージ設定
        Msg msg = new Msg();
        msg.setKey("message");
        msg.setName("message");
        msg.setResource(false);
        field.addMsg(msg);
        // 整数部設定
        Var varInteger = new Var();
        varInteger.setName("integerLength");
        varInteger.setValue("3");
        field.addVar(varInteger);
        // 整数部桁数一致指定
        Var varIsAccordedInteger = new Var();
        varIsAccordedInteger.setName("isAccordedInteger");
        varIsAccordedInteger.setValue("true");
        field.addVar(varIsAccordedInteger);
        // 小数部設定
        Var varScale = new Var();
        varScale.setName("scale");
        varScale.setValue(null);
        field.addVar(varScale);

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
            FieldChecksEx.validateNumber(
                    bean,
                    va,
                    field,
                    errors,
                    validator,
                    request);

        // テスト結果確認
        // falseが返却されていること。
        assertFalse(result);
        // エラー情報にエラーオブジェクトが登録されていないこと。
        assertEquals(1, errors.size());

        // エラーオブジェクトの検査
        Iterator it = errors.get();
        ActionMessage message = (ActionMessage) it.next();
        assertEquals("message", message.getKey());
    }

    /**
     * testValidateNumber16()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) bean:String:"123.123"<br>
     *         (引数) va:not null<br>
     *         (引数) field:not null<br>
     *                var:integerLength="3"<br>
     *                var:isAccordedInteger="true"<br>
     *                var:scale=null<br>
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
     * integerLengthに数値、isAccordedIntegerにtrueが指定されており、
     * integerLengthの数値と、beanの整数部の桁が等しいとき、
     * trueが返却されること、エラーメッセージが追加されないことを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testValidateNumber16() throws Exception {
        //テストデータ設定
        // ++++ beanオブジェクト ++++
        String bean = "123.123";
        // ++++ 検証設定オブジェクト
        ValidatorAction va = new ValidatorAction();
        va.setName("message");
        // ++++ 検証フィールド情報
        Field field = new Field();
        // メッセージ設定
        Msg msg = new Msg();
        msg.setKey("message");
        msg.setName("message");
        msg.setResource(false);
        field.addMsg(msg);
        // 整数部設定
        Var varInteger = new Var();
        varInteger.setName("integerLength");
        varInteger.setValue("3");
        field.addVar(varInteger);
        // 整数部桁数一致指定
        Var varIsAccordedInteger = new Var();
        varIsAccordedInteger.setName("isAccordedInteger");
        varIsAccordedInteger.setValue("true");
        field.addVar(varIsAccordedInteger);
        // 小数部設定
        Var varScale = new Var();
        varScale.setName("scale");
        varScale.setValue(null);
        field.addVar(varScale);

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
            FieldChecksEx.validateNumber(
                    bean,
                    va,
                    field,
                    errors,
                    validator,
                    request);

        // テスト結果確認
        // trueが返却されていること。
        assertTrue(result);
        // エラー情報にエラーオブジェクトが登録されていないこと。
        assertTrue(errors.isEmpty());
    }

    /**
     * testValidateNumber17()
     * <br><br>
     *
     * (異常系)
     * <br>
     * 観点：C,F
     * <br><br>
     * 入力値：(引数) bean:String:"12.12"<br>
     *         (引数) va:not null<br>
     *         (引数) field:not null<br>
     *                var:integerLength="3"<br>
     *                var:isAccordedInteger=""<br>
     *                var:scale=null<br>
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
     * integerLengthに数値、isAccordedIntegerに空文字が指定されており、
     * integerLengthの数値より、beanの整数部の桁が小さいとき、
     * trueが返却されること、エラーメッセージが追加されないことを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testValidateNumber17() throws Exception {
        //テストデータ設定
        // ++++ beanオブジェクト ++++
        String bean = "12.12";
        // ++++ 検証設定オブジェクト
        ValidatorAction va = new ValidatorAction();
        va.setName("message");
        // ++++ 検証フィールド情報
        Field field = new Field();
        // メッセージ設定
        Msg msg = new Msg();
        msg.setKey("message");
        msg.setName("message");
        msg.setResource(false);
        field.addMsg(msg);
        // 整数部設定
        Var varInteger = new Var();
        varInteger.setName("integerLength");
        varInteger.setValue("3");
        field.addVar(varInteger);
        // 整数部桁数一致指定
        Var varIsAccordedInteger = new Var();
        varIsAccordedInteger.setName("isAccordedInteger");
        varIsAccordedInteger.setValue("");
        field.addVar(varIsAccordedInteger);
        // 小数部設定
        Var varScale = new Var();
        varScale.setName("scale");
        varScale.setValue(null);
        field.addVar(varScale);

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
            FieldChecksEx.validateNumber(
                    bean,
                    va,
                    field,
                    errors,
                    validator,
                    request);

        // テスト結果確認
        // trueが返却されていること。
        assertTrue(result);
        // エラー情報にエラーオブジェクトが登録されていないこと。
        assertTrue(errors.isEmpty());
    }

    /**
     * testValidateNumber18()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) bean:String:"12.12"<br>
     *         (引数) va:not null<br>
     *         (引数) field:not null<br>
     *                var:integerLength="3"<br>
     *                var:isAccordedInteger=null<br>
     *                var:scale=null<br>
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
     * integerLengthに数値、isAccordedIntegerにnullが指定されており、
     * integerLengthの数値より、beanの整数部の桁が小さいとき、
     * trueが返却されること、エラーメッセージが追加されないことを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testValidateNumber18() throws Exception {
        //テストデータ設定
        // ++++ beanオブジェクト ++++
        String bean = "12.12";
        // ++++ 検証設定オブジェクト
        ValidatorAction va = new ValidatorAction();
        va.setName("message");
        // ++++ 検証フィールド情報
        Field field = new Field();
        // メッセージ設定
        Msg msg = new Msg();
        msg.setKey("message");
        msg.setName("message");
        msg.setResource(false);
        field.addMsg(msg);
        // 整数部設定
        Var varInteger = new Var();
        varInteger.setName("integerLength");
        varInteger.setValue("3");
        field.addVar(varInteger);
        // 整数部桁数一致指定
        Var varIsAccordedInteger = new Var();
        varIsAccordedInteger.setName("isAccordedInteger");
        varIsAccordedInteger.setValue(null);
        field.addVar(varIsAccordedInteger);
        // 小数部設定
        Var varScale = new Var();
        varScale.setName("scale");
        varScale.setValue(null);
        field.addVar(varScale);

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
            FieldChecksEx.validateNumber(
                    bean,
                    va,
                    field,
                    errors,
                    validator,
                    request);

        // テスト結果確認
        // trueが返却されていること。
        assertTrue(result);
        // エラー情報にエラーオブジェクトが登録されていないこと。
        assertTrue(errors.isEmpty());
    }

    /**
     * testValidateNumber19()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) bean:String:"12.12"<br>
     *         (引数) va:not null<br>
     *         (引数) field:not null<br>
     *                var:integerLength="3"<br>
     *                var:isAccordedInteger="abc"<br>
     *                var:scale=null<br>
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
     * integerLengthに数値、isAccordedIntegerにtrueではない文字列が指定
     * されており、integerLengthの数値より、beanの整数部の桁が小さいとき、
     * trueが返却されること、エラーメッセージが追加されないことを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testValidateNumber19() throws Exception {
        //テストデータ設定
        // ++++ beanオブジェクト ++++
        String bean = "12.12";
        // ++++ 検証設定オブジェクト
        ValidatorAction va = new ValidatorAction();
        va.setName("message");
        // ++++ 検証フィールド情報
        Field field = new Field();
        // メッセージ設定
        Msg msg = new Msg();
        msg.setKey("message");
        msg.setName("message");
        msg.setResource(false);
        field.addMsg(msg);
        // 整数部設定
        Var varInteger = new Var();
        varInteger.setName("integerLength");
        varInteger.setValue("3");
        field.addVar(varInteger);
        // 整数部桁数一致指定
        Var varIsAccordedInteger = new Var();
        varIsAccordedInteger.setName("isAccordedInteger");
        varIsAccordedInteger.setValue("abc");
        field.addVar(varIsAccordedInteger);
        // 小数部設定
        Var varScale = new Var();
        varScale.setName("scale");
        varScale.setValue(null);
        field.addVar(varScale);

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
            FieldChecksEx.validateNumber(
                    bean,
                    va,
                    field,
                    errors,
                    validator,
                    request);

        // テスト結果確認
        // trueが返却されていること。
        assertTrue(result);
        // エラー情報にエラーオブジェクトが登録されていること。
        assertTrue(errors.isEmpty());
    }

//    /**
//     * testValidateNumber20()
//     * <br><br>
//     *
//     * (異常系)
//     * <br>
//     * 観点：F
//     * <br><br>
//     * 入力値：(引数) bean:String:"abc"<br>
//     *         (引数) va:not null<br>
//     *         (引数) field:not null<br>
//     *                var:integerLength="2"<br>
//     *                var:scale=null<br>
//     *                Msg("message","message")<br>
//     *         (引数) errors:not null<br>
//     *                (空の要素)<br>
//     *         (引数) validator:not null<br>
//     *         (引数) request:not null<br>
//     *
//     * <br>
//     * 期待値：(戻り値) boolean:true<br>
//     *         (状態変化) errors:ActionMessage("message")<br>
//     *
//     * <br>
//     * integerLengthに数値が指定されており、引数のbeanが数値に変換できない場合、falseが返却されること。エラーメッセージが追加されることを確認する。
//     * <br>
//     *
//     * @throws Exception このメソッドで発生した例外
//     */
//    public void testValidateNumber20() throws Exception {
//        //テストケース削除
//    }

    /**
     * testValidateNumber21()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) bean:String:"100.01"<br>
     *         (引数) va:not null<br>
     *         (引数) field:not null<br>
     *                var:integerLength="3"<br>
     *                var:isAccordedInteger="true"<br>
     *                var:scale="2"<br>
     *                var:isAccordedScale="true"<br>
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
     * isAccordedInteger,isAccordedScaleがともにtrueのとき、
     * integerLengthと整数部、scaleと小数部の桁数が一致していたら
     * trueが取得できることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testValidateNumber21() throws Exception {

        //テストデータ設定
        // ++++ beanオブジェクト ++++
        String bean = "100.01";
        // ++++ 検証設定オブジェクト
        ValidatorAction va = new ValidatorAction();
        va.setName("message");
        // ++++ 検証フィールド情報
        Field field = new Field();
        // メッセージ設定
        Msg msg = new Msg();
        msg.setKey("message");
        msg.setName("message");
        msg.setResource(false);
        field.addMsg(msg);
        // 整数部設定
        Var varInteger = new Var();
        varInteger.setName("integerLength");
        varInteger.setValue("3");
        field.addVar(varInteger);
        // 整数部桁数一致指定
        Var varIsAccordedInteger = new Var();
        varIsAccordedInteger.setName("isAccordedInteger");
        varIsAccordedInteger.setValue("true");
        field.addVar(varIsAccordedInteger);
        // 小数部設定
        Var varScale = new Var();
        varScale.setName("scale");
        varScale.setValue("2");
        field.addVar(varScale);
        // 小数部桁数一致設定
        Var varIsAccordedScale = new Var();
        varIsAccordedScale.setName("isAccordedScale");
        varIsAccordedScale.setValue("true");
        field.addVar(varIsAccordedScale);

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
            FieldChecksEx.validateNumber(
                    bean,
                    va,
                    field,
                    errors,
                    validator,
                    request);

        // テスト結果確認
        // trueが返却されていること。
        assertTrue(result);
        // エラー情報にエラーオブジェクトが登録されていないこと。
        assertTrue(errors.isEmpty());
    }

    /**
     * testValidateNumber22()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) bean:String:"100.01"<br>
     *         (引数) va:not null<br>
     *         (引数) field:not null<br>
     *                var:integerLength="4"<br>
     *                var:scale="3"<br>
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
     * 整数部の桁がintegerLengthで指定された値より小さく、
     * 小数部がscaleで指定された値より小さい場合、
     * trueが取得できることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testValidateNumber22() throws Exception {

        //テストデータ設定
        // ++++ beanオブジェクト ++++
        String bean = "100.01";
        // ++++ 検証設定オブジェクト
        ValidatorAction va = new ValidatorAction();
        va.setName("message");
        // ++++ 検証フィールド情報
        Field field = new Field();
        // メッセージ設定
        Msg msg = new Msg();
        msg.setKey("message");
        msg.setName("message");
        msg.setResource(false);
        field.addMsg(msg);
        // 整数部設定
        Var varInteger = new Var();
        varInteger.setName("integerLength");
        varInteger.setValue("4");
        field.addVar(varInteger);
        // 小数部設定
        Var varScale = new Var();
        varScale.setName("scale");
        varScale.setValue("3");
        field.addVar(varScale);

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
            FieldChecksEx.validateNumber(
                    bean,
                    va,
                    field,
                    errors,
                    validator,
                    request);

        // テスト結果確認
        // trueが返却されていること。
        assertTrue(result);
        // エラー情報にエラーオブジェクトが登録されていないこと。
        assertTrue(errors.isEmpty());
    }

    /**
     * testValidateNumber23()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) bean:Map<br>
     *                ["field1"="100.01"]<br>
     *         (引数) va:not null<br>
     *         (引数) field:not null<br>
     *                var:integerLength="4"<br>
     *                var:scale="4"<br>
     *                property="field1"<br>
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
     * beanがString型ではないとき、fieldのproperty属性で指定された
     * プロパティに対して検証が行われることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testValidateNumber23() throws Exception {
        //テストデータ設定
        // ++++ beanオブジェクト ++++
        Map<String, String> bean = new HashMap<String, String>();
        bean.put("field1", "100.01");

        // ++++ 検証設定オブジェクト
        ValidatorAction va = new ValidatorAction();
        va.setName("message");
        // ++++ 検証フィールド情報
        Field field = new Field();
        field.setProperty("field1");
        // メッセージ設定
        Msg msg = new Msg();
        msg.setKey("message");
        msg.setName("message");
        msg.setResource(false);
        field.addMsg(msg);
        // 整数部設定
        Var varInteger = new Var();
        varInteger.setName("integerLength");
        varInteger.setValue("4");
        field.addVar(varInteger);
        // 小数部設定
        Var varScale = new Var();
        varScale.setName("scale");
        varScale.setValue("4");
        field.addVar(varScale);

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
            FieldChecksEx.validateNumber(
                    bean,
                    va,
                    field,
                    errors,
                    validator,
                    request);

        // テスト結果確認
        // trueが返却されていること。
        assertTrue(result);
        // エラー情報が追加されていないこと
        assertTrue(errors.isEmpty());
    }

    /**
     * testValidateNumber24()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) bean:Map<br>
     *                ["field1"="100.01"]<br>
     *         (引数) va:not null<br>
     *         (引数) field:not null<br>
     *                var:integerLength="2"<br>
     *                var:scale="1"<br>
     *                property="field1"<br>
     *                Msg("message","message")<br>
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
     * beanがString型ではないとき、fieldのproperty属性で指定された
     * プロパティに対して検証が行われることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testValidateNumber24() throws Exception {
        //テストデータ設定
        // ++++ beanオブジェクト ++++
        Map<String, String> bean = new HashMap<String, String>();
        bean.put("field1", "100.01");

        // ++++ 検証設定オブジェクト
        ValidatorAction va = new ValidatorAction();
        va.setName("message");
        // ++++ 検証フィールド情報
        Field field = new Field();
        field.setProperty("field1");
        // メッセージ設定
        Msg msg = new Msg();
        msg.setKey("message");
        msg.setName("message");
        msg.setResource(false);
        field.addMsg(msg);
        // 整数部設定
        Var varInteger = new Var();
        varInteger.setName("integerLength");
        varInteger.setValue("2");
        field.addVar(varInteger);
        // 小数部設定
        Var varScale = new Var();
        varScale.setName("scale");
        varScale.setValue("1");
        field.addVar(varScale);

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
            FieldChecksEx.validateNumber(
                    bean,
                    va,
                    field,
                    errors,
                    validator,
                    request);

        // テスト結果確認
        // falseが返却されていること。
        assertFalse(result);
        // エラー情報が追加されていること
        assertEquals(1, errors.size());

        // エラーオブジェクトの検査
        Iterator it = errors.get();
        ActionMessage message = (ActionMessage) it.next();
        assertEquals("message", message.getKey());
    }

    /**
     * testValidateNumber25()
     * <br><br>
     *
     * (異常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) bean:Map<br>
     *                ["field1"="100.01"]<br>
     *         (引数) va:not null<br>
     *         (引数) field:not null<br>
     *                var:integerLength="2"<br>
     *                var:scale="1"<br>
     *                property="field2"<br>
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
     * beanがString型ではないとき、fieldのproperty属性で指定された
     * プロパティにが存在しない場合は、trueが取得できることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testValidateNumber25() throws Exception {
        //テストデータ設定
        // ++++ beanオブジェクト ++++
        Map<String, String> bean = new HashMap<String, String>();
        bean.put("field1", "100.01");

        // ++++ 検証設定オブジェクト
        ValidatorAction va = new ValidatorAction();
        va.setName("message");
        // ++++ 検証フィールド情報
        Field field = new Field();
        field.setProperty("field2");
        // メッセージ設定
        Msg msg = new Msg();
        msg.setKey("message");
        msg.setName("message");
        msg.setResource(false);
        field.addMsg(msg);
        // 整数部設定
        Var varInteger = new Var();
        varInteger.setName("integerLength");
        varInteger.setValue("2");
        field.addVar(varInteger);
        // 小数部設定
        Var varScale = new Var();
        varScale.setName("scale");
        varScale.setValue("1");
        field.addVar(varScale);

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
            FieldChecksEx.validateNumber(
                    bean,
                    va,
                    field,
                    errors,
                    validator,
                    request);

        // テスト結果確認
        // trueが返却されていること。
        assertTrue(result);
        // エラー情報が追加されていること
        assertTrue(errors.isEmpty());
    }

    /**
     * testValidateNumber03()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) bean:String:"123.12"<br>
     *         (引数) va:not null<br>
     *         (引数) field:not null<br>
     *                var:integerLength=null<br>
     *                var:scale=null<br>
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
     * 全角の数値が入力された場合、
     * エラーメッセージを追加してfalseが返却されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testValidateNumber26() throws Exception {
        //テストデータ設定
        // ++++ beanオブジェクト ++++
        String bean = "１２３";
        // ++++ 検証設定オブジェクト
        ValidatorAction va = new ValidatorAction();
        va.setName("message");
        // ++++ 検証フィールド情報
        Field field = new Field();
        // メッセージ設定
        Msg msg = new Msg();
        msg.setKey("message");
        msg.setName("message");
        msg.setResource(false);
        field.addMsg(msg);
        Var var = new Var();
        var.setName("integerLength");
        var.setValue(null);
        field.addVar(var);
        var = new Var();
        var.setName("scale");
        var.setValue(null);
        field.addVar(var);

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
            FieldChecksEx.validateNumber(
                    bean,
                    va,
                    field,
                    errors,
                    validator,
                    request);

        // テスト結果確認
        // falseが返却されていること。
        assertFalse(result);
        // エラー情報が追加されていること
        assertEquals(1, errors.size());

        // エラーオブジェクトの検査
        Iterator it = errors.get();
        ActionMessage message = (ActionMessage) it.next();
        assertEquals("message", message.getKey());
    }

}
