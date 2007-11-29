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
public class FieldChecksExTest05 extends PropertyTestCase {

    /**
     * このテストケースを実行する為の
     * GUI アプリケーションを起動する。
     *
     * @param args java コマンドに設定されたパラメータ
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(FieldChecksExTest05.class);
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
    public FieldChecksExTest05(String name) {
        super(name);
    }

    /**
     * testValidateStringLength01()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：C,F
     * <br><br>
     * 入力値：(引数) bean:null<br>
     *         (引数) va:not null<br>
     *         (引数) field:not null<br>
     *                var:stringLength=null<br>
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
    public void testValidateStringLength01() throws Exception {
        //テストデータ設定
        // ++++ beanオブジェクト ++++
        String bean = null;
        // ++++ 検証設定オブジェクト
        ValidatorAction va = new ValidatorAction();
        // ++++ 検証フィールド情報
        Field field = new Field();
        Var var = new Var();
        var.setName("stringLength");
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
            FieldChecksEx.validateStringLength(
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
     * testValidateStringLength02()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：C,F
     * <br><br>
     * 入力値：(引数) bean:""<br>
     *         (引数) va:not null<br>
     *         (引数) field:not null<br>
     *                var:stringLength="4"<br>
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
    public void testValidateStringLength02() throws Exception {
        //テストデータ設定
        // ++++ beanオブジェクト ++++
        String bean = "";
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
        var.setName("stringLength");
        var.setValue("4");
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
            FieldChecksEx.validateStringLength(
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
     * testValidateStringLength03()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) bean:String:"abc0#"<br>
     *         (引数) va:not null<br>
     *         (引数) field:not null<br>
     *                var:stringLength=null<br>
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
     * fieldのstringLengthの値がnullのとき、trueが取得できることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testValidateStringLength03() throws Exception {
        //テストデータ設定
        // ++++ beanオブジェクト ++++
        String bean = "abc0#";
        // ++++ 検証設定オブジェクト
        ValidatorAction va = new ValidatorAction();
        va.setName("message");
        // ++++ 検証フィールド情報
        Field field = new Field();
        Var var = new Var();
        var.setName("stringLength");
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
            FieldChecksEx.validateStringLength(
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
     * testValidateStringLength04()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) bean:String:"abc0#"<br>
     *         (引数) va:not null<br>
     *         (引数) field:not null<br>
     *                var:stringLength=""<br>
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
     * fieldのstringLengthの値が空文字のとき、trueが取得できることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testValidateStringLength04() throws Exception {
        //テストデータ設定
        // ++++ beanオブジェクト ++++
        String bean = "abc0#";
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
        var.setName("stringLength");
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
            FieldChecksEx.validateStringLength(
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
     * testValidateStringLength05()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) bean:String:"abc0#"<br>
     *         (引数) va:not null<br>
     *         (引数) field:not null<br>
     *                var:stringLength="A"<br>
     *                Msg("message","message")<br>
     *         (引数) errors:not null<br>
     *                (空の要素)<br>
     *         (引数) validator:not null<br>
     *         (引数) request:not null<br>
     *
     * <br>
     * 期待値：(戻り値) boolean:true<br>
     *         (状態変化) ログ:ログレベル：エラー<br>
     *                    メッセージ：<br>
     *                    "stringLength is not numeric(integer)."<br>
     *                    例外：NumberFormatException<br>
     *         (状態変化) errors:not null<br>
     *                    (空の要素)<br>
     *
     * <br>
     * fieldのstringLengthの値が数値に変換できないとき、trueが取得できることを
     * 確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testValidateStringLength05() throws Exception {
        //テストデータ設定
        // ++++ beanオブジェクト ++++
        String bean = "abc0#";
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
        var.setName("stringLength");
        var.setValue("A");
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
            FieldChecksEx.validateStringLength(
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
        assertTrue(
            LogUTUtil.checkError("stringLength is not numeric(integer).",
                    new NumberFormatException()));
    }

    /**
     * testValidateStringLength06()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) bean:String:"abcあ"<br>
     *         (引数) va:not null<br>
     *         (引数) field:not null<br>
     *                var:stringLength="4"<br>
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
     * fieldのstringLengthで指定された値と、引数のbeanの桁数が一致する場合、
     * trueが返却されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testValidateStringLength06() throws Exception {
        //テストデータ設定
        // ++++ beanオブジェクト ++++
        String bean = "abcあ";
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
        var.setName("stringLength");
        var.setValue("4");
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
            FieldChecksEx.validateStringLength(
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
     * testValidateStringLength07()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) bean:String:"abc0#"<br>
     *         (引数) va:not null<br>
     *         (引数) field:not null<br>
     *                var:stringLength="4"<br>
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
     * fieldのstringLengthで指定された値と、引数のbeanの桁数が一致しない場合、
     * falseが返却されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testValidateStringLength07() throws Exception {
        //テストデータ設定
        // ++++ beanオブジェクト ++++
        String bean = "abc0#";
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
        var.setName("stringLength");
        var.setValue("4");
        field.addVar(var);

        // エラー情報（ActionMessageを1件設定）
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
            FieldChecksEx.validateStringLength(
                bean,
                va,
                field,
                errors,
                validator,
                request);

        // テスト結果確認
        // falseが返却されていること。
        assertFalse(result);
        // エラー情報にエラーオブジェクトが2つ登録されていること。
        assertEquals(2, errors.size());
        // エラー情報に設定されたメッセージが登録されていること。
        // 既存のActionMessageが上書きされないこと。
        Iterator it = errors.get();
        List<String> list = new ArrayList<String>();
        while (it.hasNext()) {
            ActionMessage retError = (ActionMessage) it.next();
            list.add(retError.getKey());
        }
        assertTrue(list.contains("testMessage"));
        assertTrue(list.contains("message"));
    }

    /**
     * testValidateStringLength08()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) bean:Map:["field1"="a 1 あ"]<br>
     *         (引数) va:not null<br>
     *         (引数) field:not null<br>
     *                var:stringLength="5"<br>
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
    public void testValidateStringLength08() throws Exception {
        //テストデータ設定
        // ++++ beanオブジェクト ++++
        Map<String, String> bean = new HashMap<String, String>();
        bean.put("field1", "a 1 あ");

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
        Var var = new Var();
        var.setName("stringLength");
        var.setValue("5");
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
            FieldChecksEx.validateStringLength(
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
     * testValidateStringLength09()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) bean:Map:["field1"="1234"]<br>
     *         (引数) va:not null<br>
     *         (引数) field:not null<br>
     *                var:stringLength="5"<br>
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
    public void testValidateStringLength09() throws Exception {
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
        // メッセージ設定
        Msg msg = new Msg();
        msg.setKey("message");
        msg.setName("message");
        msg.setResource(false);
        field.addMsg(msg);
        Var var = new Var();
        var.setName("stringLength");
        var.setValue("5");
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
            FieldChecksEx.validateStringLength(
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
        ActionMessage error = (ActionMessage) it.next();
        assertEquals("message", error.getKey());
    }

    /**
     * testValidateStringLength10()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) bean:Map:["field1"="123"]<br>
     *         (引数) va:not null<br>
     *         (引数) field:not null<br>
     *                var:stringLength="5"<br>
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
    public void testValidateStringLength10() throws Exception {
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
        // メッセージ設定
        Msg msg = new Msg();
        msg.setKey("message");
        msg.setName("message");
        msg.setResource(false);
        field.addMsg(msg);
        Var var = new Var();
        var.setName("stringLength");
        var.setValue("5");
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
            FieldChecksEx.validateStringLength(
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
     * testValidateByteLength01()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：C,F
     * <br><br>
     * 入力値：(引数) bean:null<br>
     *         (引数) va:not null<br>
     *         (引数) field:not null<br>
     *                var:byteLength=null<br>
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
    public void testValidateByteLength01() throws Exception {
        //テストデータ設定
        // ++++ beanオブジェクト ++++
        String bean = null;
        // ++++ 検証設定オブジェクト
        ValidatorAction va = new ValidatorAction();
        va.setName("message");
        // ++++ 検証フィールド情報
        Field field = new Field();
        Var var = new Var();
        var.setName("byteLength");
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
            FieldChecksEx.validateByteLength(
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
     * testValidateByteLength02()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：C,F
     * <br><br>
     * 入力値：(引数) bean:""<br>
     *         (引数) va:not null<br>
     *         (引数) field:not null<br>
     *                var:byteLength="4"<br>
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
    public void testValidateByteLength02() throws Exception {
        //テストデータ設定
        // ++++ beanオブジェクト ++++
        String bean = "";
        // ++++ 検証設定オブジェクト
        ValidatorAction va = new ValidatorAction();
        va.setName("message");
        // ++++ 検証フィールド情報
        Field field = new Field();
        Var var = new Var();
        var.setName("byteLength");
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
            FieldChecksEx.validateByteLength(
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
     * testValidateByteLength03()
     * <br><br>
     *
     * (異常系)
     * <br>
     * 観点：C,F
     * <br><br>
     * 入力値：(引数) bean:String:"abc0#"<br>
     *         (引数) va:not null<br>
     *         (引数) field:not null<br>
     *                var:byteLength=null<br>
     *                Msg("message","message")<br>
     *         (引数) errors:not null<br>
     *                (空の要素)<br>
     *         (引数) validator:not null<br>
     *         (引数) request:not null<br>
     *
     * <br>
     * 期待値：(戻り値) boolean:true<br>
     *         (状態変化) ログ:ログレベル：情報<br>
     *                    メッセージ：<br>
     *                    "length is not specified."<br>
     *         (状態変化) errors:not null<br>
     *                    (空の要素)<br>
     *
     * <br>
     * fieldのbyteLengthの値がnullのとき、trueが取得できることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testValidateByteLength03() throws Exception {
        //テストデータ設定
        // ++++ beanオブジェクト ++++
        String bean = "abc0#";
        // ++++ 検証設定オブジェクト
        ValidatorAction va = new ValidatorAction();
        va.setName("message");
        // ++++ 検証フィールド情報
        Field field = new Field();
        Var var = new Var();
        var.setName("byteLength");
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
            FieldChecksEx.validateByteLength(
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

        // 情報ログチェック
        assertTrue(LogUTUtil.checkInfo("length is not specified."));
    }

    /**
     * testValidateByteLength04()
     * <br><br>
     *
     * (異常系)
     * <br>
     * 観点：C,F
     * <br><br>
     * 入力値：(引数) bean:String:"abc0#"<br>
     *         (引数) va:not null<br>
     *         (引数) field:not null<br>
     *                var:byteLength=""<br>
     *                Msg("message","message")<br>
     *         (引数) errors:not null<br>
     *                (空の要素)<br>
     *         (引数) validator:not null<br>
     *         (引数) request:not null<br>
     *
     * <br>
     * 期待値：(戻り値) boolean:true<br>
     *         (状態変化) ログ:ログレベル：情報<br>
     *                    メッセージ：<br>
     *                    "length is not specified."<br>
     *         (状態変化) errors:not null<br>
     *                    (空の要素)<br>
     *
     * <br>
     * fieldのbyteLengthの値が空文字のとき、trueが取得できることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testValidateByteLength04() throws Exception {
        //テストデータ設定
        // ++++ beanオブジェクト ++++
        String bean = "abc0#";
        // ++++ 検証設定オブジェクト
        ValidatorAction va = new ValidatorAction();
        va.setName("message");
        // ++++ 検証フィールド情報
        Field field = new Field();
        Var var = new Var();
        var.setName("byteLength");
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
            FieldChecksEx.validateByteLength(
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

        // 情報ログチェック
        assertTrue(LogUTUtil.checkInfo("length is not specified."));
    }

    /**
     * testValidateByteLength05()
     * <br><br>
     *
     * (異常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) bean:String:"abc0#"<br>
     *         (引数) va:not null<br>
     *         (引数) field:not null<br>
     *                var:byteLength="A"<br>
     *                Msg("message","message")<br>
     *         (引数) errors:not null<br>
     *                (空の要素)<br>
     *         (引数) validator:not null<br>
     *         (引数) request:not null<br>
     *
     * <br>
     * 期待値：(戻り値) boolean:true<br>
     *         (状態変化) ログ:ログレベル：エラー<br>
     *                    メッセージ：<br>
     *                    "byteLength is not numeric(integer)."<br>
     *                    例外：NumberFormatException<br>
     *         (状態変化) errors:not null<br>
     *                    (空の要素)<br>
     *
     * <br>
     * fieldのbyteLengthの値が数値に変換できないとき、
     * trueが取得できることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testValidateByteLength05() throws Exception {
        //テストデータ設定
        // ++++ beanオブジェクト ++++
        String bean = "abc0#";
        // ++++ 検証設定オブジェクト
        ValidatorAction va = new ValidatorAction();
        va.setName("message");
        // ++++ 検証フィールド情報
        Field field = new Field();
        Var var = new Var();
        var.setName("byteLength");
        var.setValue("A");
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
            FieldChecksEx.validateByteLength(
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

        // 情報ログチェック
        assertTrue(LogUTUtil.checkError("byteLength is not numeric(integer).",
                new NumberFormatException()));
    }

    /**
     * testValidateByteLength06()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) bean:String:"あ"<br>
     *         (引数) va:not null<br>
     *         (引数) field:not null<br>
     *                var:byteLength="3"<br>
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
     * fieldのbyteLengthで指定された値と、引数のbeanのバイト数が一致する場合、
     * trueが返却されることを確認する。<br>
     * ※getByteLengthメソッドのテストも包含する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testValidateByteLength06() throws Exception {
        //テストデータ設定
        // ++++ beanオブジェクト ++++
        String bean = "あ";
        // ++++ 検証設定オブジェクト
        ValidatorAction va = new ValidatorAction();
        va.setName("message");
        // ++++ 検証フィールド情報
        Field field = new Field();
        Var var = new Var();
        var.setName("byteLength");
        var.setValue("3");
        field.addVar(var);
        // エンコーディングの設定
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
            FieldChecksEx.validateByteLength(
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
     * testValidateByteLength07()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) bean:String:"あ"<br>
     *         (引数) va:not null<br>
     *         (引数) field:not null<br>
     *                var:byteLength="3"<br>
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
     * fieldのbyteLengthで指定された値と、引数のbeanの桁数が一致しない場合、
     * falseが返却されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testValidateByteLength07() throws Exception {
        //テストデータ設定
        // ++++ beanオブジェクト ++++
        String bean = "あ";
        // ++++ 検証設定オブジェクト
        ValidatorAction va = new ValidatorAction();
        va.setName("message");
        // ++++ 検証フィールド情報
        Field field = new Field();
        Var var = new Var();
        var.setName("byteLength");
        var.setValue("3");
        field.addVar(var);
        // エンコーディングの設定
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

        // エラー情報（ActionMessageを1件設定）
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
            FieldChecksEx.validateByteLength(
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
        assertEquals(2, errors.size());
        // メッセージオブジェクトの検査
        Iterator it = errors.get();
        List<String> list = new ArrayList<String>();
        while (it.hasNext()) {
            ActionMessage retError = (ActionMessage) it.next();
            list.add(retError.getKey());
        }
        assertTrue(list.contains("testMessage"));
        assertTrue(list.contains("message"));
    }

    /**
     * testValidateByteLength08()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) bean:String:"あ"<br>
     *         (引数) va:not null<br>
     *         (引数) field:not null<br>
     *                var:byteLength="2"<br>
     *                encoding=null<br>
     *                Msg("message","message")<br>
     *         (引数) errors:not null<br>
     *                ActionMessage("testMessage")<br>
     *         (引数) validator:not null<br>
     *         (引数) request:not null<br>
     *
     * <br>
     * 期待値：(戻り値) boolean:true<br>
     *         (状態変化) errors:not null<br>
     *                    (空の要素)<br>
     *
     * <br>
     * fieldのencodingがnullの場合、VMのデフォルトのエンコーディングで
     * 入力文字のバイト数を計算することを確認する。<br>
     * ※getByteLengthメソッドのテストも包含する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testValidateByteLength08() throws Exception {
        //テストデータ設定
        // ++++ beanオブジェクト ++++
        String bean = "あ";
        // ++++ 検証設定オブジェクト
        ValidatorAction va = new ValidatorAction();
        va.setName("message");
        // ++++ 検証フィールド情報
        Field field = new Field();
        Var var = new Var();
        var.setName("byteLength");
        var.setValue("2");
        field.addVar(var);
        // エンコーディングの設定
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
            FieldChecksEx.validateByteLength(
                bean,
                va,
                field,
                errors,
                validator,
                request);
        // テスト結果確認
        // trueが返却されていること。
        assertTrue(result);
        // エラー情報がないこと。
        assertTrue(errors.isEmpty());
    }

    /**
     * testValidateByteLength09()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) bean:String:"あ"<br>
     *         (引数) va:not null<br>
     *         (引数) field:not null<br>
     *                var:byteLength="2"<br>
     *                encoding=""<br>
     *                Msg("message","message")<br>
     *         (引数) errors:not null<br>
     *                ActionMessage("testMessage")<br>
     *         (引数) validator:not null<br>
     *         (引数) request:not null<br>
     *
     * <br>
     * 期待値：(戻り値) boolean:true<br>
     *         (状態変化) errors:not null<br>
     *                    (空の要素)<br>
     *
     * <br>
     * fieldのencodingが空文字の場合、VMのデフォルトのエンコーディングで
     * 入力文字のバイト数を計算することを確認する。<br>
     * ※getByteLengthメソッドのテストも包含する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testValidateByteLength09() throws Exception {
        //テストデータ設定
        // ++++ beanオブジェクト ++++
        String bean = "あ";
        // ++++ 検証設定オブジェクト
        ValidatorAction va = new ValidatorAction();
        va.setName("message");
        // ++++ 検証フィールド情報
        Field field = new Field();
        Var var = new Var();
        var.setName("byteLength");
        var.setValue("2");
        field.addVar(var);
        // エンコーディングの設定
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
            FieldChecksEx.validateByteLength(
                bean,
                va,
                field,
                errors,
                validator,
                request);
        // テスト結果確認
        // trueが返却されていること。
        assertTrue(result);
        // エラー情報がないこと。
        assertTrue(errors.isEmpty());
    }

    /**
     * testValidateByteLength10()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) bean:String:"あ"<br>
     *         (引数) va:not null<br>
     *         (引数) field:not null<br>
     *                var:byteLength="2"<br>
     *                encoding="test-encoding"<br>
     *                Msg("message","message")<br>
     *         (引数) errors:not null<br>
     *                ActionMessage("testMessage")<br>
     *         (引数) validator:not null<br>
     *         (引数) request:not null<br>
     *
     * <br>
     * 期待値：(戻り値) boolean:true<br>
     *         (状態変化) ログ:ログレベル：警告<br>
     *                    メッセージ：<br>
     *                    "test-encoding is not supported."<br>
     *         (状態変化) errors:not null<br>
     *                    (空の要素)<br>
     *
     * <br>
     * fieldのencodingがサポートされないエンコーディングの場合、
     * VMのデフォルトのエンコーディングで入力文字のバイト数を計算することを確認する。<br>
     * ※getByteLengthメソッドのテストも包含する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testValidateByteLength10() throws Exception {
        //テストデータ設定
        // ++++ beanオブジェクト ++++
        String bean = "あ";
        // ++++ 検証設定オブジェクト
        ValidatorAction va = new ValidatorAction();
        va.setName("message");
        // ++++ 検証フィールド情報
        Field field = new Field();
        Var var = new Var();
        var.setName("byteLength");
        var.setValue("2");
        field.addVar(var);
        // エンコーディングの設定
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
            FieldChecksEx.validateByteLength(
                bean,
                va,
                field,
                errors,
                validator,
                request);
        // テスト結果確認
        // trueが返却されていること。
        assertTrue(result);
        // エラー情報がないこと。
        assertTrue(errors.isEmpty());

        // 警告ログのチェック
        assertTrue(LogUTUtil.checkWarn("test-encoding is not supported."));
    }

    /**
     * testValidateByteLength11()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) bean:Map:["field1"="あいう"]<br>
     *         (引数) va:not null<br>
     *         (引数) field:not null<br>
     *                var:byteLength="9"<br>
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
     * 引数のbeanがString型ではない場合、fieldから取得した名前のプロパティ
     * に対してチェックを行うことを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testValidateByteLength11() throws Exception {
        //テストデータ設定
        // ++++ beanオブジェクト ++++
        Map<String, String> bean = new HashMap<String, String>();
        bean.put("field1", "あいう");
        // ++++ 検証設定オブジェクト
        ValidatorAction va = new ValidatorAction();
        va.setName("message");
        // ++++ 検証フィールド情報
        Field field = new Field();
        field.setProperty("field1");
        Var var = new Var();
        var.setName("byteLength");
        var.setValue("9");
        field.addVar(var);
        // エンコーディングの設定
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
            FieldChecksEx.validateByteLength(
                bean,
                va,
                field,
                errors,
                validator,
                request);
        // テスト結果確認
        // trueが返却されていること。
        assertTrue(result);
        // エラー情報がないこと。
        assertTrue(errors.isEmpty());
    }

    /**
     * testValidateByteLength12()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) bean:Map:["field1"="1234"]<br>
     *         (引数) va:not null<br>
     *         (引数) field:not null<br>
     *                var:byteLength="5"<br>
     *                encoding="Windows-31J"<br>
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
     * 引数のbeanがString型ではない場合、fieldから取得した名前のプロパティ
     * に対してチェックを行うことを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testValidateByteLength12() throws Exception {
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
        var.setName("byteLength");
        var.setValue("5");
        field.addVar(var);
        // エンコーディングの設定
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
            FieldChecksEx.validateByteLength(
                bean,
                va,
                field,
                errors,
                validator,
                request);
        // テスト結果確認
        // falseが返却されていること。
        assertFalse(result);
        // エラー情報が1件あること。
        assertEquals(1, errors.size());

        // メッセージオブジェクトの検査
        Iterator it = errors.get();
        ActionMessage message = (ActionMessage) it.next();
        assertEquals("message", message.getKey());
    }

    /**
     * testValidateByteLength13()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) bean:Map:["field1"="123"]<br>
     *         (引数) va:not null<br>
     *         (引数) field:not null<br>
     *                var:byteLength="5"<br>
     *                encoding="UTF-8"<br>
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
    public void testValidateByteLength13() throws Exception {
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
        var.setName("byteLength");
        var.setValue("5");
        field.addVar(var);
        // エンコーディングの設定
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
            FieldChecksEx.validateByteLength(
                bean,
                va,
                field,
                errors,
                validator,
                request);
        // テスト結果確認
        // trueが返却されていること。
        assertTrue(result);
        // エラー情報がないこと。
        assertTrue(errors.isEmpty());
    }

}
