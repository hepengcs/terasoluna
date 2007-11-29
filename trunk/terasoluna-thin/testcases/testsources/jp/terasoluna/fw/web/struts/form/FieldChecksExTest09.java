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
import java.util.Locale;
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
public class FieldChecksExTest09 extends PropertyTestCase {

    /**
     * このテストケースを実行する為の
     * GUI アプリケーションを起動する。
     *
     * @param args java コマンドに設定されたパラメータ
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(FieldChecksExTest09.class);
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
    public FieldChecksExTest09(String name) {
        super(name);
    }

    /**
     * testValidateDateRange01()
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
     *                Locale=JAPANESE<br>
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
    public void testValidateDateRange01() throws Exception {
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
        request.setLocale(Locale.JAPANESE);
        // ValidatorResourcesインスタンス
        ValidatorResources validatorResources = new ValidatorResources();
        // Validatorインスタンス
        Validator validator = new Validator(validatorResources);

        // テスト実行
        boolean result =
            FieldChecksEx.validateDateRange(
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
     * testValidateDateRange02()
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
     *                Locale=JAPANESE<br>
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
    public void testValidateDateRange02() throws Exception {
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
        request.setLocale(Locale.JAPANESE);
        // ValidatorResourcesインスタンス
        ValidatorResources validatorResources = new ValidatorResources();
        // Validatorインスタンス
        Validator validator = new Validator(validatorResources);

        // テスト実行
        boolean result =
            FieldChecksEx.validateDateRange(
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
     * testValidateDateRange03()
     * <br><br>
     *
     * (異常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) bean:"abc"<br>
     *         (引数) va:not null<br>
     *         (引数) field:not null<br>
     *                var:datePattern=null<br>
     *                var:datePatternStrict=null<br>
     *                Msg("message","message")<br>
     *         (引数) errors:not null<br>
     *                ActionMessage("testMessage")<br>
     *         (引数) validator:not null<br>
     *         (引数) request:not null<br>
     *                Locale=JAPANESE<br>
     *
     * <br>
     * 期待値：(戻り値) boolean:false<br>
     *         (状態変化) errors:ActionMessage("testMessage")<br>
     *                    ActionMessage("message")<br>
     *
     * <br>
     * datePattern、datePatternStrictが指定しておらず、
     * 引数のbeanがDateに変換できない場合、
     * エラーメッセージを追加してfalseが返却されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testValidateDateRange03() throws Exception {
        //テストデータ設定
        // ++++ beanオブジェクト ++++
        String bean = "abc";
        // ++++ 検証設定オブジェクト
        ValidatorAction va = new ValidatorAction();
        va.setName("message");
        // ++++ 検証フィールド情報
        Field field = new Field();
        Var var = new Var();
        var.setName("datePattern");
        var.setValue(null);
        field.addVar(var);
        var = new Var();
        var.setName("datePatternStrict");
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
        ActionMessage error = new ActionMessage("testMessage");
        errors.add(Globals.ERROR_KEY, error);
        // 擬似HTTPリクエスト
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setLocale(Locale.JAPANESE);
        // ValidatorResourcesインスタンス
        ValidatorResources validatorResources = new ValidatorResources();
        // Validatorインスタンス
        Validator validator = new Validator(validatorResources);

        // テスト実行
        boolean result =
            FieldChecksEx.validateDateRange(
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

        // メッセージオブジェクト確認
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
     * testValidateDateRange04()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) bean:"abc"<br>
     *         (引数) va:not null<br>
     *         (引数) field:not null<br>
     *                var:datePattern=""<br>
     *                var:datePatternStrict=""<br>
     *                Msg("message","message")<br>
     *         (引数) errors:not null<br>
     *                (空の要素)<br>
     *         (引数) validator:not null<br>
     *         (引数) request:not null<br>
     *                Locale=JAPANESE<br>
     *
     * <br>
     * 期待値：(戻り値) boolean:false<br>
     *         (状態変化) errors:ActionMessage("message")<br>
     *
     * <br>
     * datePattern、datePatternStrictに空文字が指定されていて、
     * 引数のbeanがDateに変換できない場合、
     * エラーメッセージを追加してfalseが返却されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testValidateDateRange04() throws Exception {
        //テストデータ設定
        // ++++ beanオブジェクト ++++
        String bean = "abc";
        // ++++ 検証設定オブジェクト
        ValidatorAction va = new ValidatorAction();
        va.setName("message");
        // ++++ 検証フィールド情報
        Field field = new Field();
        Var var = new Var();
        var.setName("datePattern");
        var.setValue("");
        field.addVar(var);
        var = new Var();
        var.setName("datePatternStrict");
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
        request.setLocale(Locale.JAPANESE);
        // ValidatorResourcesインスタンス
        ValidatorResources validatorResources = new ValidatorResources();
        // Validatorインスタンス
        Validator validator = new Validator(validatorResources);

        // テスト実行
        boolean result =
            FieldChecksEx.validateDateRange(
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

        // メッセージオブジェクト確認
        Iterator it = errors.get();
        ActionMessage message = (ActionMessage) it.next();
        assertEquals("message", message.getKey());
    }

    /**
     * testValidateDateRange05()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) bean:"abc"<br>
     *         (引数) va:not null<br>
     *         (引数) field:not null<br>
     *                var:datePattern=null<br>
     *                var:datePatternStrict="yyyy/MM/dd"<br>
     *                Msg("message","message")<br>
     *         (引数) errors:not null<br>
     *                (空の要素)<br>
     *         (引数) validator:not null<br>
     *         (引数) request:not null<br>
     *                Locale=JAPANESE<br>
     *
     * <br>
     * 期待値：(戻り値) boolean:false<br>
     *         (状態変化) errors:ActionMessage("message")<br>
     *
     * <br>
     * datePatternが指定されておらず、datePatternStrictに正常な日付フォーマット
     * が指定されていて、引数のbeanがDateに変換できない場合、
     * エラーメッセージを追加してfalseが返却されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testValidateDateRange05() throws Exception {
        //テストデータ設定
        // ++++ beanオブジェクト ++++
        String bean = "abc";
        // ++++ 検証設定オブジェクト
        ValidatorAction va = new ValidatorAction();
        va.setName("message");
        // ++++ 検証フィールド情報
        Field field = new Field();
        Var var = new Var();
        var.setName("datePattern");
        var.setValue(null);
        field.addVar(var);
        var = new Var();
        var.setName("datePatternStrict");
        var.setValue("yyyy/MM/dd");
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
        request.setLocale(Locale.JAPANESE);
        // ValidatorResourcesインスタンス
        ValidatorResources validatorResources = new ValidatorResources();
        // Validatorインスタンス
        Validator validator = new Validator(validatorResources);

        // テスト実行
        boolean result =
            FieldChecksEx.validateDateRange(
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

        // メッセージオブジェクト確認
        Iterator it = errors.get();
        ActionMessage message = (ActionMessage) it.next();
        assertEquals("message", message.getKey());
    }

    /**
     * testValidateDateRange06()
     * <br><br>
     *
     * (異常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) bean:"2005/10/24"<br>
     *         (引数) va:not null<br>
     *         (引数) field:not null<br>
     *                var:datePattern=null<br>
     *                var:datePatternStrict="abc"<br>
     *                Msg("message","message")<br>
     *         (引数) errors:not null<br>
     *                (空の要素)<br>
     *         (引数) validator:not null<br>
     *         (引数) request:not null<br>
     *                Locale=JAPANESE<br>
     *
     * <br>
     * 期待値：(戻り値) boolean:false<br>
     *         (状態変化) ログ:ログレベル：<br>
     *                    エラー<br>
     *                    メッセージ：<br>
     *                    Illegal pattern character 'b'<br>
     *                    例外：<br>
     *                    IllegalArgumentException<br>
     *         (状態変化) errors:ActionMessage("message")<br>
     *
     * <br>
     * 引数のbeanが正常な日付を表す文字列で、datePatternが指定されておらず、
     * datePatternStrictに不正な日付フォーマットが指定されている場合、
     * エラーメッセージを追加してfalseが返却されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testValidateDateRange06() throws Exception {
        //テストデータ設定
        // ++++ beanオブジェクト ++++
        String bean = "2005/10/24";
        // ++++ 検証設定オブジェクト
        ValidatorAction va = new ValidatorAction();
        va.setName("message");
        // ++++ 検証フィールド情報
        Field field = new Field();
        Var var = new Var();
        var.setName("datePattern");
        var.setValue(null);
        field.addVar(var);
        var = new Var();
        var.setName("datePatternStrict");
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
        request.setLocale(Locale.JAPANESE);
        // ValidatorResourcesインスタンス
        ValidatorResources validatorResources = new ValidatorResources();
        // Validatorインスタンス
        Validator validator = new Validator(validatorResources);

        // テスト実行
        boolean result =
            FieldChecksEx.validateDateRange(
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

        // メッセージオブジェクト確認
        Iterator it = errors.get();
        ActionMessage message = (ActionMessage) it.next();
        assertEquals("message", message.getKey());

        // エラーログチェック
        assertTrue(LogUTUtil.checkError("Illegal pattern character 'b'",
                new IllegalArgumentException()));
    }

    /**
     * testValidateDateRange07()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) bean:"2005/02/29"<br>
     *         (引数) va:not null<br>
     *         (引数) field:not null<br>
     *                var:datePattern=null<br>
     *                var:datePatternStrict="yyyy/MM/dd"<br>
     *                Msg("message","message")<br>
     *         (引数) errors:not null<br>
     *                (空の要素)<br>
     *         (引数) validator:not null<br>
     *         (引数) request:not null<br>
     *                Locale=JAPANESE<br>
     *
     * <br>
     * 期待値：(戻り値) boolean:false<br>
     *         (状態変化) errors:ActionMessage("message")<br>
     *
     * <br>
     * 引数のbeanが存在しない日付を表す文字列で、datePatternが指定されておらず、
     * datePatternStrictに不正な日付フォーマットが指定されている場合、
     * エラーメッセージを追加してfalseが返却されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testValidateDateRange07() throws Exception {
        //テストデータ設定
        // ++++ beanオブジェクト ++++
        String bean = "2005/02/29";
        // ++++ 検証設定オブジェクト
        ValidatorAction va = new ValidatorAction();
        va.setName("message");
        // ++++ 検証フィールド情報
        Field field = new Field();
        Var var = new Var();
        var.setName("datePattern");
        var.setValue(null);
        field.addVar(var);
        var = new Var();
        var.setName("datePatternStrict");
        var.setValue("yyyy/MM/dd");
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
        request.setLocale(Locale.JAPANESE);
        // ValidatorResourcesインスタンス
        ValidatorResources validatorResources = new ValidatorResources();
        // Validatorインスタンス
        Validator validator = new Validator(validatorResources);

        // テスト実行
        boolean result =
            FieldChecksEx.validateDateRange(
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

        // メッセージオブジェクト確認
        Iterator it = errors.get();
        ActionMessage message = (ActionMessage) it.next();
        assertEquals("message", message.getKey());
    }

    /**
     * testValidateDateRange08()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) bean:"2005/2/28"<br>
     *         (引数) va:not null<br>
     *         (引数) field:not null<br>
     *                var:datePattern=null<br>
     *                var:datePatternStrict="yyyy/MM/dd"<br>
     *                Msg("message","message")<br>
     *         (引数) errors:not null<br>
     *                (空の要素)<br>
     *         (引数) validator:not null<br>
     *         (引数) request:not null<br>
     *                Locale=JAPANESE<br>
     *
     * <br>
     * 期待値：(戻り値) boolean:false<br>
     *         (状態変化) errors:ActionMessage("message")<br>
     *
     * <br>
     * 引数のbeanが正常な日付を表す文字列で、datePatternが指定されておらず、
     * datePatternStrictに指定された日付フォーマットとbeanのフォーマットが
     * 完全に一致しない場合、エラーメッセージを追加してfalseが返却されることを
     * 確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testValidateDateRange08() throws Exception {
        //テストデータ設定
        // ++++ beanオブジェクト ++++
        String bean = "2005/2/28";
        // ++++ 検証設定オブジェクト
        ValidatorAction va = new ValidatorAction();
        va.setName("message");
        // ++++ 検証フィールド情報
        Field field = new Field();
        Var var = new Var();
        var.setName("datePattern");
        var.setValue(null);
        field.addVar(var);
        var = new Var();
        var.setName("datePatternStrict");
        var.setValue("yyyy/MM/dd");
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
        request.setLocale(Locale.JAPANESE);
        // ValidatorResourcesインスタンス
        ValidatorResources validatorResources = new ValidatorResources();
        // Validatorインスタンス
        Validator validator = new Validator(validatorResources);

        // テスト実行
        boolean result =
            FieldChecksEx.validateDateRange(
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

        // メッセージオブジェクト確認
        Iterator it = errors.get();
        ActionMessage message = (ActionMessage) it.next();
        assertEquals("message", message.getKey());
    }

    /**
     * testValidateDateRange09()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) bean:"abc"<br>
     *         (引数) va:not null<br>
     *         (引数) field:not null<br>
     *                var:datePattern="yyyy/MM/dd"<br>
     *                var:datePatternStrict=null<br>
     *                Msg("message","message")<br>
     *         (引数) errors:not null<br>
     *                (空の要素)<br>
     *         (引数) validator:not null<br>
     *         (引数) request:not null<br>
     *                Locale=JAPANESE<br>
     *
     * <br>
     * 期待値：(戻り値) boolean:false<br>
     *         (状態変化) errors:ActionMessage("message")<br>
     *
     * <br>
     * datePatternに正常な日付フォーマットが指定されていて、
     * 引数のbeanがDateに変換できない場合、エラーメッセージを追加してfalseが
     * 返却されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testValidateDateRange09() throws Exception {
        //テストデータ設定
        // ++++ beanオブジェクト ++++
        String bean = "abc";
        // ++++ 検証設定オブジェクト
        ValidatorAction va = new ValidatorAction();
        va.setName("message");
        // ++++ 検証フィールド情報
        Field field = new Field();
        Var var = new Var();
        var.setName("datePattern");
        var.setValue("yyyy/MM/dd");
        field.addVar(var);
        var = new Var();
        var.setName("datePatternStrict");
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
        request.setLocale(Locale.JAPANESE);
        // ValidatorResourcesインスタンス
        ValidatorResources validatorResources = new ValidatorResources();
        // Validatorインスタンス
        Validator validator = new Validator(validatorResources);

        // テスト実行
        boolean result =
            FieldChecksEx.validateDateRange(
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

        // メッセージオブジェクト確認
        Iterator it = errors.get();
        ActionMessage message = (ActionMessage) it.next();
        assertEquals("message", message.getKey());
    }

    /**
     * testValidateDateRange10()
     * <br><br>
     *
     * (異常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) bean:"2005/10/24"<br>
     *         (引数) va:not null<br>
     *         (引数) field:not null<br>
     *                var:datePattern="abc"<br>
     *                var:datePatternStrict=null<br>
     *                Msg("message","message")<br>
     *         (引数) errors:not null<br>
     *                (空の要素)<br>
     *         (引数) validator:not null<br>
     *         (引数) request:not null<br>
     *                Locale=JAPANESE<br>
     *
     * <br>
     * 期待値：(戻り値) boolean:false<br>
     *         (状態変化) ログ:ログレベル：<br>
     *                    エラー<br>
     *                    メッセージ：<br>
     *                    Illegal pattern character 'b'<br>
     *                    例外：<br>
     *                    IllegalArgumentException<br>
     *         (状態変化) errors:ActionMessage("message")<br>
     *
     * <br>
     * 引数のbeanが正常な日付を表す文字列で、datePatternに不正な
     * 日付フォーマットが指定されている場合、エラーメッセージを追加してfalseが
     * 返却されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testValidateDateRange10() throws Exception {
        //テストデータ設定
        // ++++ beanオブジェクト ++++
        String bean = "2005/10/24";
        // ++++ 検証設定オブジェクト
        ValidatorAction va = new ValidatorAction();
        va.setName("message");
        // ++++ 検証フィールド情報
        Field field = new Field();
        Var var = new Var();
        var.setName("datePattern");
        var.setValue("abc");
        field.addVar(var);
        var = new Var();
        var.setName("datePatternStrict");
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
        request.setLocale(Locale.JAPANESE);
        // ValidatorResourcesインスタンス
        ValidatorResources validatorResources = new ValidatorResources();
        // Validatorインスタンス
        Validator validator = new Validator(validatorResources);

        // テスト実行
        boolean result =
            FieldChecksEx.validateDateRange(
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

        // メッセージオブジェクト確認
        Iterator it = errors.get();
        ActionMessage message = (ActionMessage) it.next();
        assertEquals("message", message.getKey());

        // エラーログチェック
        assertTrue(LogUTUtil.checkError("Illegal pattern character 'b'",
                new IllegalArgumentException()));
    }

    /**
     * testValidateDateRange11()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) bean:"2005/02/29"<br>
     *         (引数) va:not null<br>
     *         (引数) field:not null<br>
     *                var:datePattern="yyyy/MM/dd"<br>
     *                var:datePatternStrict=null<br>
     *                Msg("message","message")<br>
     *         (引数) errors:not null<br>
     *                (空の要素)<br>
     *         (引数) validator:not null<br>
     *         (引数) request:not null<br>
     *                Locale=JAPANESE<br>
     *
     * <br>
     * 期待値：(戻り値) boolean:false<br>
     *         (状態変化) errors:ActionMessage("message")<br>
     *
     * <br>
     * 引数のbeanが存在しない日付を表す文字列で、datePatternに不正な
     * 日付フォーマットが指定されている場合、エラーメッセージを追加して
     * falseが返却されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testValidateDateRange11() throws Exception {
        //テストデータ設定
        // ++++ beanオブジェクト ++++
        String bean = "2005/02/29";
        // ++++ 検証設定オブジェクト
        ValidatorAction va = new ValidatorAction();
        va.setName("message");
        // ++++ 検証フィールド情報
        Field field = new Field();
        Var var = new Var();
        var.setName("datePattern");
        var.setValue("yyyy/MM/dd");
        field.addVar(var);
        var = new Var();
        var.setName("datePatternStrict");
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
        request.setLocale(Locale.JAPANESE);
        // ValidatorResourcesインスタンス
        ValidatorResources validatorResources = new ValidatorResources();
        // Validatorインスタンス
        Validator validator = new Validator(validatorResources);

        // テスト実行
        boolean result =
            FieldChecksEx.validateDateRange(
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

        // メッセージオブジェクト確認
        Iterator it = errors.get();
        ActionMessage message = (ActionMessage) it.next();
        assertEquals("message", message.getKey());
    }

    /**
     * testValidateDateRange12()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) bean:"2005/2/28"<br>
     *         (引数) va:not null<br>
     *         (引数) field:not null<br>
     *                var:datePattern="yyyy/MM/dd"<br>
     *                var:datePatternStrict="yyyy/MM/dd"<br>
     *                var:startDate=null<br>
     *                var:endDate=null<br>
     *                Msg("message","message")<br>
     *         (引数) errors:not null<br>
     *                (空の要素)<br>
     *         (引数) validator:not null<br>
     *         (引数) request:not null<br>
     *                Locale=JAPANESE<br>
     *
     * <br>
     * 期待値：(戻り値) boolean:true<br>
     *         (状態変化) errors:not null<br>
     *                    (空の要素)<br>
     *
     * <br>
     * datePatternとdatePatternStrictが両方指定されている場合、
     * datePatternに指定したフォーマットが優先されること、<br>
     * fieldのstartDateとendDateがnullの場合、trueが取得できること、を確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testValidateDateRange12() throws Exception {
        //テストデータ設定
        // ++++ beanオブジェクト ++++
        String bean = "2005/2/28";
        // ++++ 検証設定オブジェクト
        ValidatorAction va = new ValidatorAction();
        va.setName("message");
        // ++++ 検証フィールド情報
        Field field = new Field();
        Var var = new Var();
        var.setName("datePattern");
        var.setValue("yyyy/MM/dd");
        field.addVar(var);
        var = new Var();
        var.setName("datePatternStrict");
        var.setValue("yyyy/MM/dd");
        field.addVar(var);
        var = new Var();
        var.setName("startDate");
        var.setValue(null);
        field.addVar(var);
        var = new Var();
        var.setName("endDate");
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
        request.setLocale(Locale.JAPANESE);
        // ValidatorResourcesインスタンス
        ValidatorResources validatorResources = new ValidatorResources();
        // Validatorインスタンス
        Validator validator = new Validator(validatorResources);

        // テスト実行
        boolean result =
            FieldChecksEx.validateDateRange(
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
     * testValidateDateRange13()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) bean:"2005/2/28"<br>
     *         (引数) va:not null<br>
     *         (引数) field:not null<br>
     *                var:datePattern="yyyy/MM/dd"<br>
     *                var:datePatternStrict="yyyy/MM/dd"<br>
     *                var:startDate=""<br>
     *                var:endDate=""<br>
     *                Msg("message","message")<br>
     *         (引数) errors:not null<br>
     *                (空の要素)<br>
     *         (引数) validator:not null<br>
     *         (引数) request:not null<br>
     *                Locale=JAPANESE<br>
     *
     * <br>
     * 期待値：(戻り値) boolean:true<br>
     *         (状態変化) errors:not null<br>
     *                    (空の要素)<br>
     *
     * <br>
     * datePatternとdatePatternStrictが両方指定されている場合、
     * datePatternに指定したフォーマットが優先されること、<br>
     * fieldのstartDateとendDateが空文字の場合、trueが取得できること、
     * を確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testValidateDateRange13() throws Exception {
        //テストデータ設定
        // ++++ beanオブジェクト ++++
        String bean = "2005/2/28";
        // ++++ 検証設定オブジェクト
        ValidatorAction va = new ValidatorAction();
        va.setName("message");
        // ++++ 検証フィールド情報
        Field field = new Field();
        Var var = new Var();
        var.setName("datePattern");
        var.setValue("yyyy/MM/dd");
        field.addVar(var);
        var = new Var();
        var.setName("datePatternStrict");
        var.setValue("yyyy/MM/dd");
        field.addVar(var);
        var = new Var();
        var.setName("startDate");
        var.setValue("");
        field.addVar(var);
        var = new Var();
        var.setName("endDate");
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
        request.setLocale(Locale.JAPANESE);
        // ValidatorResourcesインスタンス
        ValidatorResources validatorResources = new ValidatorResources();
        // Validatorインスタンス
        Validator validator = new Validator(validatorResources);

        // テスト実行
        boolean result =
            FieldChecksEx.validateDateRange(
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
     * testValidateDateRange14()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) bean:"2005/01/01"<br>
     *         (引数) va:not null<br>
     *         (引数) field:not null<br>
     *                var:datePattern=null<br>
     *                var:datePatternStrict=null<br>
     *                var:startDate="abc"<br>
     *                Msg("message","message")<br>
     *         (引数) errors:not null<br>
     *                (空の要素)<br>
     *         (引数) validator:not null<br>
     *         (引数) request:not null<br>
     *                Locale=JAPANESE<br>
     *
     * <br>
     * 期待値：(戻り値) boolean:false<br>
     *         (状態変化) errors:ActionMessage("message")<br>
     *
     * <br>
     * datePattern、datePatternStrictが指定しておらず、
     * 引数のbeanがDateに変換でき、fieldのstartDateが日付に変換できない場合、
     * エラーメッセージを追加し、falseが返却されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testValidateDateRange14() throws Exception {
        //テストデータ設定
        // ++++ beanオブジェクト ++++
        String bean = "2005/01/01";
        // ++++ 検証設定オブジェクト
        ValidatorAction va = new ValidatorAction();
        va.setName("message");
        // ++++ 検証フィールド情報
        Field field = new Field();
        Var var = new Var();
        var.setName("datePattern");
        var.setValue(null);
        field.addVar(var);
        var = new Var();
        var.setName("datePatternStrict");
        var.setValue(null);
        field.addVar(var);
        var = new Var();
        var.setName("startDate");
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
        request.setLocale(Locale.JAPANESE);
        // ValidatorResourcesインスタンス
        ValidatorResources validatorResources = new ValidatorResources();
        // Validatorインスタンス
        Validator validator = new Validator(validatorResources);

        // テスト実行
        boolean result =
            FieldChecksEx.validateDateRange(
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

        // メッセージオブジェクト確認
        Iterator it = errors.get();
        ActionMessage message = (ActionMessage) it.next();
        assertEquals("message", message.getKey());
    }

    /**
     * testValidateDateRange15()
     * <br><br>
     *
     * (異常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) bean:"2005/01/01"<br>
     *         (引数) va:not null<br>
     *         (引数) field:not null<br>
     *                var:datePattern=null<br>
     *                var:datePatternStrict="yyyy/MM/dd"<br>
     *                var:startDate="2005/02/29"<br>
     *                Msg("message","message")<br>
     *         (引数) errors:not null<br>
     *                (空の要素)<br>
     *         (引数) validator:not null<br>
     *         (引数) request:not null<br>
     *                Locale=JAPANESE<br>
     *
     * <br>
     * 期待値：(戻り値) boolean:false<br>
     *         (状態変化) errors:ActionMessage("message")<br>
     *
     * <br>
     * datePatternStrictのみに正常な日付フォーマットが指定され、
     * 引数のbeanがDateに変換でき、fieldのstartDateが日付に変換できない場合、
     * エラーメッセージを追加し、falseが返却されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testValidateDateRange15() throws Exception {
        //テストデータ設定
        // ++++ beanオブジェクト ++++
        String bean = "2005/01/01";
        // ++++ 検証設定オブジェクト
        ValidatorAction va = new ValidatorAction();
        va.setName("message");
        // ++++ 検証フィールド情報
        Field field = new Field();
        Var var = new Var();
        var.setName("datePattern");
        var.setValue(null);
        field.addVar(var);
        var = new Var();
        var.setName("datePatternStrict");
        var.setValue("yyyy/MM/dd");
        field.addVar(var);
        var = new Var();
        var.setName("startDate");
        var.setValue("2005/02/29");
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
        request.setLocale(Locale.JAPANESE);
        // ValidatorResourcesインスタンス
        ValidatorResources validatorResources = new ValidatorResources();
        // Validatorインスタンス
        Validator validator = new Validator(validatorResources);

        // テスト実行
        boolean result =
            FieldChecksEx.validateDateRange(
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

        // メッセージオブジェクト確認
        Iterator it = errors.get();
        ActionMessage message = (ActionMessage) it.next();
        assertEquals("message", message.getKey());
    }

    /**
     * testValidateDateRange16()
     * <br><br>
     *
     * (異常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) bean:"2005/01/01"<br>
     *         (引数) va:not null<br>
     *         (引数) field:not null<br>
     *                var:datePattern=null<br>
     *                var:datePatternStrict="yyyy/MM/dd"<br>
     *                var:startDate="2005/2/28"<br>
     *                Msg("message","message")<br>
     *         (引数) errors:not null<br>
     *                (空の要素)<br>
     *         (引数) validator:not null<br>
     *         (引数) request:not null<br>
     *                Locale=JAPANESE<br>
     *
     * <br>
     * 期待値：(戻り値) boolean:false<br>
     *         (状態変化) errors:ActionMessage("message")<br>
     *
     * <br>
     * datePatternStrictのみに正常な日付フォーマットが指定され、
     * 引数のbeanがDateに変換でき、fieldのstartDateが日付に変換できない場合、
     * エラーメッセージを追加し、falseが返却されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testValidateDateRange16() throws Exception {
        //テストデータ設定
        // ++++ beanオブジェクト ++++
        String bean = "2005/01/01";
        // ++++ 検証設定オブジェクト
        ValidatorAction va = new ValidatorAction();
        va.setName("message");
        // ++++ 検証フィールド情報
        Field field = new Field();
        Var var = new Var();
        var.setName("datePattern");
        var.setValue(null);
        field.addVar(var);
        var = new Var();
        var.setName("datePatternStrict");
        var.setValue("yyyy/MM/dd");
        field.addVar(var);
        var = new Var();
        var.setName("startDate");
        var.setValue("2005/2/28");
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
        request.setLocale(Locale.JAPANESE);
        // ValidatorResourcesインスタンス
        ValidatorResources validatorResources = new ValidatorResources();
        // Validatorインスタンス
        Validator validator = new Validator(validatorResources);

        // テスト実行
        boolean result =
            FieldChecksEx.validateDateRange(
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

        // メッセージオブジェクト確認
        Iterator it = errors.get();
        ActionMessage message = (ActionMessage) it.next();
        assertEquals("message", message.getKey());
    }

    /**
     * testValidateDateRange17()
     * <br><br>
     *
     * (異常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) bean:"2005/01/01"<br>
     *         (引数) va:not null<br>
     *         (引数) field:not null<br>
     *                var:datePattern=null<br>
     *                var:datePatternStrict="yyyy/MM/dd"<br>
     *                var:startDate="abc"<br>
     *                Msg("message","message")<br>
     *         (引数) errors:not null<br>
     *                (空の要素)<br>
     *         (引数) validator:not null<br>
     *         (引数) request:not null<br>
     *                Locale=JAPANESE<br>
     *
     * <br>
     * 期待値：(戻り値) boolean:false<br>
     *         (状態変化) errors:ActionMessage("message")<br>
     *
     * <br>
     * datePatternStrictのみに正常な日付フォーマットが指定され、
     * 引数のbeanがDateに変換でき、fieldのstartDateが日付に変換できない場合、
     * エラーメッセージを追加し、falseが返却されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testValidateDateRange17() throws Exception {
        //テストデータ設定
        // ++++ beanオブジェクト ++++
        String bean = "2005/01/01";
        // ++++ 検証設定オブジェクト
        ValidatorAction va = new ValidatorAction();
        va.setName("message");
        // ++++ 検証フィールド情報
        Field field = new Field();
        Var var = new Var();
        var.setName("datePattern");
        var.setValue(null);
        field.addVar(var);
        var = new Var();
        var.setName("datePatternStrict");
        var.setValue("yyyy/MM/dd");
        field.addVar(var);
        var = new Var();
        var.setName("startDate");
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
        request.setLocale(Locale.JAPANESE);
        // ValidatorResourcesインスタンス
        ValidatorResources validatorResources = new ValidatorResources();
        // Validatorインスタンス
        Validator validator = new Validator(validatorResources);

        // テスト実行
        boolean result =
            FieldChecksEx.validateDateRange(
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

        // メッセージオブジェクト確認
        Iterator it = errors.get();
        ActionMessage message = (ActionMessage) it.next();
        assertEquals("message", message.getKey());
    }

    /**
     * testValidateDateRange18()
     * <br><br>
     *
     * (異常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) bean:"2005/01/01"<br>
     *         (引数) va:not null<br>
     *         (引数) field:not null<br>
     *                var:datePattern="yyyy/MM/dd"<br>
     *                var:datePatternStrict="yyyy/MM/dd"<br>
     *                var:startDate="abc"<br>
     *                Msg("message","message")<br>
     *         (引数) errors:not null<br>
     *                (空の要素)<br>
     *         (引数) validator:not null<br>
     *         (引数) request:not null<br>
     *                Locale=JAPANESE<br>
     *
     * <br>
     * 期待値：(戻り値) boolean:false<br>
     *         (状態変化) errors:ActionMessage("message")<br>
     *
     * <br>
     * datePattern、datePatternStrictに正常な日付フォーマットが指定され、
     * 引数のbeanがDateに変換でき、fieldのstartDateが日付に変換できない場合、
     * エラーメッセージを追加し、falseが返却されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testValidateDateRange18() throws Exception {
        //テストデータ設定
        // ++++ beanオブジェクト ++++
        String bean = "2005/01/01";
        // ++++ 検証設定オブジェクト
        ValidatorAction va = new ValidatorAction();
        va.setName("message");
        // ++++ 検証フィールド情報
        Field field = new Field();
        Var var = new Var();
        var.setName("datePattern");
        var.setValue("yyyy/MM/dd");
        field.addVar(var);
        var = new Var();
        var.setName("datePatternStrict");
        var.setValue("yyyy/MM/dd");
        field.addVar(var);
        var = new Var();
        var.setName("startDate");
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
        request.setLocale(Locale.JAPANESE);
        // ValidatorResourcesインスタンス
        ValidatorResources validatorResources = new ValidatorResources();
        // Validatorインスタンス
        Validator validator = new Validator(validatorResources);

        // テスト実行
        boolean result =
            FieldChecksEx.validateDateRange(
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

        // メッセージオブジェクト確認
        Iterator it = errors.get();
        ActionMessage message = (ActionMessage) it.next();
        assertEquals("message", message.getKey());
    }

    /**
     * testValidateDateRange19()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) bean:"2005/01/01"<br>
     *         (引数) va:not null<br>
     *         (引数) field:not null<br>
     *                var:datePattern="yyyy/MM/dd"<br>
     *                var:datePatternStrict="yyyy/MM/dd"<br>
     *                var:startDate="2005/1/2"<br>
     *                Msg("message","message")<br>
     *         (引数) errors:not null<br>
     *                (空の要素)<br>
     *         (引数) validator:not null<br>
     *         (引数) request:not null<br>
     *                Locale=JAPANESE<br>
     *
     * <br>
     * 期待値：(戻り値) boolean:false<br>
     *         (状態変化) errors:ActionMessage("message")<br>
     *
     * <br>
     * beanの日付が、startDateで指定された日付より前の場合、
     * エラーメッセージを追加して、falseを返却することを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testValidateDateRange19() throws Exception {
        //テストデータ設定
        // ++++ beanオブジェクト ++++
        String bean = "2005/01/01";
        // ++++ 検証設定オブジェクト
        ValidatorAction va = new ValidatorAction();
        va.setName("message");
        // ++++ 検証フィールド情報
        Field field = new Field();
        Var var = new Var();
        var.setName("datePattern");
        var.setValue("yyyy/MM/dd");
        field.addVar(var);
        var = new Var();
        var.setName("datePatternStrict");
        var.setValue("yyyy/MM/dd");
        field.addVar(var);
        var = new Var();
        var.setName("startDate");
        var.setValue("2005/1/2");
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
        request.setLocale(Locale.JAPANESE);
        // ValidatorResourcesインスタンス
        ValidatorResources validatorResources = new ValidatorResources();
        // Validatorインスタンス
        Validator validator = new Validator(validatorResources);

        // テスト実行
        boolean result =
            FieldChecksEx.validateDateRange(
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

        // メッセージオブジェクト確認
        Iterator it = errors.get();
        ActionMessage message = (ActionMessage) it.next();
        assertEquals("message", message.getKey());
    }

    /**
     * testValidateDateRange20()
     * <br><br>
     *
     * (異常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) bean:"2005/01/01"<br>
     *         (引数) va:not null<br>
     *         (引数) field:not null<br>
     *                var:datePattern=null<br>
     *                var:datePatternStrict=null<br>
     *                var:startDate="2004/01/01"<br>
     *                var.endDate="abc"<br>
     *                Msg("message","message")<br>
     *         (引数) errors:not null<br>
     *                (空の要素)<br>
     *         (引数) validator:not null<br>
     *         (引数) request:not null<br>
     *                Locale=JAPANESE<br>
     *
     * <br>
     * 期待値：(戻り値) boolean:false<br>
     *         (状態変化) errors:ActionMessage("message")<br>
     *
     * <br>
     * datePattern、datePatternStrictがnullでendDateが日付に変換できない場合、
     * エラーメッセージを追加してfalseが返却されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testValidateDateRange20() throws Exception {
        //テストデータ設定
        // ++++ beanオブジェクト ++++
        String bean = "2005/01/01";
        // ++++ 検証設定オブジェクト
        ValidatorAction va = new ValidatorAction();
        va.setName("message");
        // ++++ 検証フィールド情報
        Field field = new Field();
        Var var = new Var();
        var.setName("datePattern");
        var.setValue(null);
        field.addVar(var);
        var = new Var();
        var.setName("datePatternStrict");
        var.setValue(null);
        field.addVar(var);
        var = new Var();
        var.setName("startDate");
        var.setValue("2004/01/01");
        field.addVar(var);
        var = new Var();
        var.setName("endDate");
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
        request.setLocale(Locale.JAPANESE);
        // ValidatorResourcesインスタンス
        ValidatorResources validatorResources = new ValidatorResources();
        // Validatorインスタンス
        Validator validator = new Validator(validatorResources);

        // テスト実行
        boolean result =
            FieldChecksEx.validateDateRange(
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

        // メッセージオブジェクト確認
        Iterator it = errors.get();
        ActionMessage message = (ActionMessage) it.next();
        assertEquals("message", message.getKey());
    }

    /**
     * testValidateDateRange21()
     * <br><br>
     *
     * (異常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) bean:"2005/01/01"<br>
     *         (引数) va:not null<br>
     *         (引数) field:not null<br>
     *                var:datePattern=null<br>
     *                var:datePatternStrict="yyyy/MM/dd"<br>
     *                var:startDate="2004/01/01"<br>
     *                var:endDate="2005/02/29"<br>
     *                Msg("message","message")<br>
     *         (引数) errors:not null<br>
     *                (空の要素)<br>
     *         (引数) validator:not null<br>
     *         (引数) request:not null<br>
     *                Locale=JAPANESE<br>
     *
     * <br>
     * 期待値：(戻り値) boolean:false<br>
     *         (状態変化) errors:ActionMessage("message")<br>
     *
     * <br>
     * datePatternがnull、datePatternStrictが正常な日付フォーマットで、
     * endDateが存在しない日付の場合、エラーメッセージを追加してfalseが
     * 返却されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testValidateDateRange21() throws Exception {
        //テストデータ設定
        // ++++ beanオブジェクト ++++
        String bean = "2005/01/01";
        // ++++ 検証設定オブジェクト
        ValidatorAction va = new ValidatorAction();
        va.setName("message");
        // ++++ 検証フィールド情報
        Field field = new Field();
        Var var = new Var();
        var.setName("datePattern");
        var.setValue(null);
        field.addVar(var);
        var = new Var();
        var.setName("datePatternStrict");
        var.setValue("yyyy/MM/dd");
        field.addVar(var);
        var = new Var();
        var.setName("startDate");
        var.setValue("2004/01/01");
        field.addVar(var);
        var = new Var();
        var.setName("endDate");
        var.setValue("2005/02/29");
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
        request.setLocale(Locale.JAPANESE);
        // ValidatorResourcesインスタンス
        ValidatorResources validatorResources = new ValidatorResources();
        // Validatorインスタンス
        Validator validator = new Validator(validatorResources);

        // テスト実行
        boolean result =
            FieldChecksEx.validateDateRange(
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

        // メッセージオブジェクト確認
        Iterator it = errors.get();
        ActionMessage message = (ActionMessage) it.next();
        assertEquals("message", message.getKey());
    }

    /**
     * testValidateDateRange22()
     * <br><br>
     *
     * (異常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) bean:"2005/01/01"<br>
     *         (引数) va:not null<br>
     *         (引数) field:not null<br>
     *                var:datePattern=null<br>
     *                var:datePatternStrict="yyyy/MM/dd"<br>
     *                var:startDate="2004/01/01"<br>
     *                var:endDate="2005/2/28"<br>
     *                Msg("message","message")<br>
     *         (引数) errors:not null<br>
     *                (空の要素)<br>
     *         (引数) validator:not null<br>
     *         (引数) request:not null<br>
     *                Locale=JAPANESE<br>
     *
     * <br>
     * 期待値：(戻り値) boolean:false<br>
     *         (状態変化) errors:ActionMessage("message")<br>
     *
     * <br>
     * datePatternがnull、datePatternStrictが正常な日付フォーマットで、
     * endDateの日付フォーマットと完全に一致しない場合、
     * エラーメッセージを追加してfalseが返却されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testValidateDateRange22() throws Exception {
        //テストデータ設定
        // ++++ beanオブジェクト ++++
        String bean = "2005/01/01";
        // ++++ 検証設定オブジェクト
        ValidatorAction va = new ValidatorAction();
        va.setName("message");
        // ++++ 検証フィールド情報
        Field field = new Field();
        Var var = new Var();
        var.setName("datePattern");
        var.setValue(null);
        field.addVar(var);
        var = new Var();
        var.setName("datePatternStrict");
        var.setValue("yyyy/MM/dd");
        field.addVar(var);
        var = new Var();
        var.setName("startDate");
        var.setValue("2004/01/01");
        field.addVar(var);
        var = new Var();
        var.setName("endDate");
        var.setValue("2005/2/28");
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
        request.setLocale(Locale.JAPANESE);
        // ValidatorResourcesインスタンス
        ValidatorResources validatorResources = new ValidatorResources();
        // Validatorインスタンス
        Validator validator = new Validator(validatorResources);

        // テスト実行
        boolean result =
            FieldChecksEx.validateDateRange(
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

        // メッセージオブジェクト確認
        Iterator it = errors.get();
        ActionMessage message = (ActionMessage) it.next();
        assertEquals("message", message.getKey());
    }

    /**
     * testValidateDateRange23()
     * <br><br>
     *
     * (異常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) bean:"2005/01/01"<br>
     *         (引数) va:not null<br>
     *         (引数) field:not null<br>
     *                var:datePattern=null<br>
     *                var:datePatternStrict="yyyy/MM/dd"<br>
     *                var:startDate="2004/01/01"<br>
     *                var:endDate="abc"<br>
     *                Msg("message","message")<br>
     *         (引数) errors:not null<br>
     *                (空の要素)<br>
     *         (引数) validator:not null<br>
     *         (引数) request:not null<br>
     *                Locale=JAPANESE<br>
     *
     * <br>
     * 期待値：(戻り値) boolean:false<br>
     *         (状態変化) errors:ActionMessage("message")<br>
     *
     * <br>
     * datePatternがnull、datePatternStrictが正常な日付フォーマットで、
     * endDateが日付ではない場合、エラーメッセージを追加してfalseが返却される
     * ことを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testValidateDateRange23() throws Exception {
        //テストデータ設定
        // ++++ beanオブジェクト ++++
        String bean = "2005/01/01";
        // ++++ 検証設定オブジェクト
        ValidatorAction va = new ValidatorAction();
        va.setName("message");
        // ++++ 検証フィールド情報
        Field field = new Field();
        Var var = new Var();
        var.setName("datePattern");
        var.setValue(null);
        field.addVar(var);
        var = new Var();
        var.setName("datePatternStrict");
        var.setValue("yyyy/MM/dd");
        field.addVar(var);
        var = new Var();
        var.setName("startDate");
        var.setValue("2004/01/01");
        field.addVar(var);
        var = new Var();
        var.setName("endDate");
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
        request.setLocale(Locale.JAPANESE);
        // ValidatorResourcesインスタンス
        ValidatorResources validatorResources = new ValidatorResources();
        // Validatorインスタンス
        Validator validator = new Validator(validatorResources);

        // テスト実行
        boolean result =
            FieldChecksEx.validateDateRange(
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

        // メッセージオブジェクト確認
        Iterator it = errors.get();
        ActionMessage message = (ActionMessage) it.next();
        assertEquals("message", message.getKey());
    }

    /**
     * testValidateDateRange24()
     * <br><br>
     *
     * (異常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) bean:"2005/01/01"<br>
     *         (引数) va:not null<br>
     *         (引数) field:not null<br>
     *                var:datePattern="yyyy/MM/dd"<br>
     *                var:datePatternStrict="yyyy/MM/dd"<br>
     *                var:startDate="2004/01/01"<br>
     *                var:endDate="abc"<br>
     *                Msg("message","message")<br>
     *         (引数) errors:not null<br>
     *                (空の要素)<br>
     *         (引数) validator:not null<br>
     *         (引数) request:not null<br>
     *                Locale=JAPANESE<br>
     *
     * <br>
     * 期待値：(戻り値) boolean:false<br>
     *         (状態変化) errors:ActionMessage("message")<br>
     *
     * <br>
     * datePattern、datePatternStrictに正常な日付フォーマットが指定され、
     * fieldのendDateが日付に変換できない場合、エラーメッセージを追加し、
     * falseが返却されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testValidateDateRange24() throws Exception {
        //テストデータ設定
        // ++++ beanオブジェクト ++++
        String bean = "2005/01/01";
        // ++++ 検証設定オブジェクト
        ValidatorAction va = new ValidatorAction();
        va.setName("message");
        // ++++ 検証フィールド情報
        Field field = new Field();
        Var var = new Var();
        var.setName("datePattern");
        var.setValue("yyyy/MM/dd");
        field.addVar(var);
        var = new Var();
        var.setName("datePatternStrict");
        var.setValue("yyyy/MM/dd");
        field.addVar(var);
        var = new Var();
        var.setName("startDate");
        var.setValue("2004/01/01");
        field.addVar(var);
        var = new Var();
        var.setName("endDate");
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
        request.setLocale(Locale.JAPANESE);
        // ValidatorResourcesインスタンス
        ValidatorResources validatorResources = new ValidatorResources();
        // Validatorインスタンス
        Validator validator = new Validator(validatorResources);

        // テスト実行
        boolean result =
            FieldChecksEx.validateDateRange(
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

        // メッセージオブジェクト確認
        Iterator it = errors.get();
        ActionMessage message = (ActionMessage) it.next();
        assertEquals("message", message.getKey());
    }

    /**
     * testValidateDateRange25()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) bean:"2005/01/02"<br>
     *         (引数) va:not null<br>
     *         (引数) field:not null<br>
     *                var:datePattern="yyyy/MM/dd"<br>
     *                var:datePatternStrict="yyyy/MM/dd"<br>
     *                var:startDate="2004/01/01"<br>
     *                var:endDate="2005/1/1"<br>
     *                Msg("message","message")<br>
     *         (引数) errors:not null<br>
     *                (空の要素)<br>
     *         (引数) validator:not null<br>
     *         (引数) request:not null<br>
     *                Locale=JAPANESE<br>
     *
     * <br>
     * 期待値：(戻り値) boolean:false<br>
     *         (状態変化) errors:ActionMessage("message")<br>
     *
     * <br>
     * beanの日付が、endDateで指定された日付より後の場合、
     * エラーメッセージを追加して、falseを返却することを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testValidateDateRange25() throws Exception {
        //テストデータ設定
        // ++++ beanオブジェクト ++++
        String bean = "2005/01/02";
        // ++++ 検証設定オブジェクト
        ValidatorAction va = new ValidatorAction();
        va.setName("message");
        // ++++ 検証フィールド情報
        Field field = new Field();
        Var var = new Var();
        var.setName("datePattern");
        var.setValue("yyyy/MM/dd");
        field.addVar(var);
        var = new Var();
        var.setName("datePatternStrict");
        var.setValue("yyyy/MM/dd");
        field.addVar(var);
        var = new Var();
        var.setName("startDate");
        var.setValue("2004/01/01");
        field.addVar(var);
        var = new Var();
        var.setName("endDate");
        var.setValue("2005/1/1");
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
        request.setLocale(Locale.JAPANESE);
        // ValidatorResourcesインスタンス
        ValidatorResources validatorResources = new ValidatorResources();
        // Validatorインスタンス
        Validator validator = new Validator(validatorResources);

        // テスト実行
        boolean result =
            FieldChecksEx.validateDateRange(
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

        // メッセージオブジェクト確認
        Iterator it = errors.get();
        ActionMessage message = (ActionMessage) it.next();
        assertEquals("message", message.getKey());
    }

    /**
     * testValidateDateRange26()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) bean:"2005/01/02"<br>
     *         (引数) va:not null<br>
     *         (引数) field:not null<br>
     *                var:datePattern=null<br>
     *                var:datePatternStrict=null<br>
     *                var:startDate="2005/1/1"<br>
     *                var:endDate="2005/1/3"<br>
     *                Msg("message","message")<br>
     *         (引数) errors:not null<br>
     *                (空の要素)<br>
     *         (引数) validator:not null<br>
     *         (引数) request:not null<br>
     *                Locale=JAPANESE<br>
     *
     * <br>
     * 期待値：(戻り値) boolean:true<br>
     *         (状態変化) errors:not null<br>
     *                    (空の要素)<br>
     *
     * <br>
     * datePattern、datePatternStrictがnullでbeanの日付がstartDateとendDateの
     * 間にある場合、trueが取得できることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testValidateDateRange26() throws Exception {
        //テストデータ設定
        // ++++ beanオブジェクト ++++
        String bean = "2005/01/02";
        // ++++ 検証設定オブジェクト
        ValidatorAction va = new ValidatorAction();
        va.setName("message");
        // ++++ 検証フィールド情報
        Field field = new Field();
        Var var = new Var();
        var.setName("datePattern");
        var.setValue(null);
        field.addVar(var);
        var = new Var();
        var.setName("datePatternStrict");
        var.setValue(null);
        field.addVar(var);
        var = new Var();
        var.setName("startDate");
        var.setValue("2005/1/1");
        field.addVar(var);
        var = new Var();
        var.setName("endDate");
        var.setValue("2005/1/3");
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
        request.setLocale(Locale.JAPANESE);
        // ValidatorResourcesインスタンス
        ValidatorResources validatorResources = new ValidatorResources();
        // Validatorインスタンス
        Validator validator = new Validator(validatorResources);

        // テスト実行
        boolean result =
            FieldChecksEx.validateDateRange(
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
     * testValidateDateRange27()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) bean:"2005/1/2"<br>
     *         (引数) va:not null<br>
     *         (引数) field:not null<br>
     *                var:datePattern="yyyy/MM/dd"<br>
     *                var:datePatternStrict="yyyy.MM.dd"<br>
     *                var:startDate="2005/1/1"<br>
     *                var:endDate="2005/1/3"<br>
     *                Msg("message","message")<br>
     *         (引数) errors:not null<br>
     *                (空の要素)<br>
     *         (引数) validator:not null<br>
     *         (引数) request:not null<br>
     *                Locale=JAPANESE<br>
     *
     * <br>
     * 期待値：(戻り値) boolean:true<br>
     *         (状態変化) errors:not null<br>
     *                    (空の要素)<br>
     *
     * <br>
     * datePattern、datePatternStrictともにフォーマットが指定されている場合、
     * datePatternのフォーマットが適用されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testValidateDateRange27() throws Exception {
        //テストデータ設定
        // ++++ beanオブジェクト ++++
        String bean = "2005/1/2";
        // ++++ 検証設定オブジェクト
        ValidatorAction va = new ValidatorAction();
        va.setName("message");
        // ++++ 検証フィールド情報
        Field field = new Field();
        Var var = new Var();
        var.setName("datePattern");
        var.setValue("yyyy/MM/dd");
        field.addVar(var);
        var = new Var();
        var.setName("datePatternStrict");
        var.setValue("yyyy.MM.dd");
        field.addVar(var);
        var = new Var();
        var.setName("startDate");
        var.setValue("2005/1/1");
        field.addVar(var);
        var = new Var();
        var.setName("endDate");
        var.setValue("2005/1/3");
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
        request.setLocale(Locale.JAPANESE);
        // ValidatorResourcesインスタンス
        ValidatorResources validatorResources = new ValidatorResources();
        // Validatorインスタンス
        Validator validator = new Validator(validatorResources);

        // テスト実行
        boolean result =
            FieldChecksEx.validateDateRange(
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
     * testValidateDateRange28()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) bean:"2005/01/02"<br>
     *         (引数) va:not null<br>
     *         (引数) field:not null<br>
     *                var:datePattern=null<br>
     *                var:datePatternStrict="yyyy/MM/dd"<br>
     *                var:startDate="2005/01/01"<br>
     *                var:endDate="2005/01/03"<br>
     *                Msg("message","message")<br>
     *         (引数) errors:not null<br>
     *                (空の要素)<br>
     *         (引数) validator:not null<br>
     *         (引数) request:not null<br>
     *                Locale=JAPANESE<br>
     *
     * <br>
     * 期待値：(戻り値) boolean:true<br>
     *         (状態変化) errors:not null<br>
     *                    (空の要素)<br>
     *
     * <br>
     * datePatternがnull、datePatternStrictに正常なフォーマットが指定されている
     * 場合で、beanの日付がstartDateとendDateの間にある場合、
     * trueが取得できることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testValidateDateRange28() throws Exception {
        //テストデータ設定
        // ++++ beanオブジェクト ++++
        String bean = "2005/01/02";
        // ++++ 検証設定オブジェクト
        ValidatorAction va = new ValidatorAction();
        va.setName("message");
        // ++++ 検証フィールド情報
        Field field = new Field();
        Var var = new Var();
        var.setName("datePattern");
        var.setValue(null);
        field.addVar(var);
        var = new Var();
        var.setName("datePatternStrict");
        var.setValue("yyyy/MM/dd");
        field.addVar(var);
        var = new Var();
        var.setName("startDate");
        var.setValue("2005/01/01");
        field.addVar(var);
        var = new Var();
        var.setName("endDate");
        var.setValue("2005/01/03");
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
        request.setLocale(Locale.JAPANESE);
        // ValidatorResourcesインスタンス
        ValidatorResources validatorResources = new ValidatorResources();
        // Validatorインスタンス
        Validator validator = new Validator(validatorResources);

        // テスト実行
        boolean result =
            FieldChecksEx.validateDateRange(
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
     * testValidateDateRange29()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) bean:"2005/01/01"<br>
     *         (引数) va:not null<br>
     *         (引数) field:not null<br>
     *                var:datePattern=null<br>
     *                var:datePatternStrict=null<br>
     *                var:startDate="2005/1/1"<br>
     *                var:end]Date="2005/1/1"<br>
     *                Msg("message","message")<br>
     *         (引数) errors:not null<br>
     *                (空の要素)<br>
     *         (引数) validator:not null<br>
     *         (引数) request:not null<br>
     *                Locale=JAPANESE<br>
     *
     * <br>
     * 期待値：(戻り値) boolean:true<br>
     *         (状態変化) errors:not null<br>
     *                    (空の要素)<br>
     *
     * <br>
     * beanの日付と、startDate、endDateが一致する場合、
     * trueが取得できることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testValidateDateRange29() throws Exception {
        //テストデータ設定
        // ++++ beanオブジェクト ++++
        String bean = "2005/01/01";
        // ++++ 検証設定オブジェクト
        ValidatorAction va = new ValidatorAction();
        va.setName("message");
        // ++++ 検証フィールド情報
        Field field = new Field();
        Var var = new Var();
        var.setName("datePattern");
        var.setValue(null);
        field.addVar(var);
        var = new Var();
        var.setName("datePatternStrict");
        var.setValue(null);
        field.addVar(var);
        var = new Var();
        var.setName("startDate");
        var.setValue("2005/1/1");
        field.addVar(var);
        var = new Var();
        var.setName("endDate");
        var.setValue("2005/1/1");
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
        request.setLocale(Locale.JAPANESE);
        // ValidatorResourcesインスタンス
        ValidatorResources validatorResources = new ValidatorResources();
        // Validatorインスタンス
        Validator validator = new Validator(validatorResources);

        // テスト実行
        boolean result =
            FieldChecksEx.validateDateRange(
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
     * testValidateDateRange30()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) bean:Map:<br>
     *                ["field1"=<br>
     *                　"2005/01/02"]<br>
     *         (引数) va:not null<br>
     *         (引数) field:not null<br>
     *                var:datePattern=null<br>
     *                var:datePatternStrict="yyyy/MM/dd"<br>
     *                var:startDate="2005/01/01"<br>
     *                var:endDate="2005/01/03"<br>
     *                Msg("message","message")<br>
     *                property="field1"<br>
     *         (引数) errors:not null<br>
     *                (空の要素)<br>
     *         (引数) validator:not null<br>
     *         (引数) request:not null<br>
     *                Locale=JAPANESE<br>
     *
     * <br>
     * 期待値：(戻り値) boolean:true<br>
     *         (状態変化) errors:not null<br>
     *                    (空の要素)<br>
     *
     * <br>
     * 引数のbeanがString型ではない場合、fieldから取得した名前のプロパティに対してチェックを行うことを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testValidateDateRange30() throws Exception {
        //テストデータ設定
        // ++++ beanオブジェクト ++++
        Map<String, String> bean = new HashMap<String, String>();
        bean.put("field1", "2005/01/02");
        // ++++ 検証設定オブジェクト
        ValidatorAction va = new ValidatorAction();
        va.setName("message");
        // ++++ 検証フィールド情報
        Field field = new Field();
        field.setProperty("field1");
        Var var = new Var();
        var.setName("datePattern");
        var.setValue(null);
        field.addVar(var);
        var = new Var();
        var.setName("datePatternStrict");
        var.setValue("yyyy/MM/dd");
        field.addVar(var);
        var = new Var();
        var.setName("startDate");
        var.setValue("2005/01/01");
        field.addVar(var);
        var = new Var();
        var.setName("endDate");
        var.setValue("2005/01/03");
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
        request.setLocale(Locale.JAPANESE);
        // ValidatorResourcesインスタンス
        ValidatorResources validatorResources = new ValidatorResources();
        // Validatorインスタンス
        Validator validator = new Validator(validatorResources);

        // テスト実行
        boolean result =
            FieldChecksEx.validateDateRange(
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
     * testValidateDateRange31()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) bean:Map:<br>
     *                ["field1"=<br>
     *                　"2005/01/04"]<br>
     *         (引数) va:not null<br>
     *         (引数) field:not null<br>
     *                var:datePattern=null<br>
     *                var:datePatternStrict="yyyy/MM/dd"<br>
     *                var:startDate="2005/01/01"<br>
     *                var:endDate="2005/01/03"<br>
     *                Msg("message","message")<br>
     *                property="field1"<br>
     *         (引数) errors:not null<br>
     *                (空の要素)<br>
     *         (引数) validator:not null<br>
     *         (引数) request:not null<br>
     *                Locale=JAPANESE<br>
     *
     * <br>
     * 期待値：(戻り値) boolean:false<br>
     *         (状態変化) errors:ActionMessage("message")<br>
     *
     * <br>
     * 引数のbeanがString型ではない場合、fieldから取得した名前のプロパティに対してチェックを行うことを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testValidateDateRange31() throws Exception {
        //テストデータ設定
        // ++++ beanオブジェクト ++++
        Map<String, String> bean = new HashMap<String, String>();
        bean.put("field1", "2005/01/04");
        // ++++ 検証設定オブジェクト
        ValidatorAction va = new ValidatorAction();
        va.setName("message");
        // ++++ 検証フィールド情報
        Field field = new Field();
        field.setProperty("field1");
        Var var = new Var();
        var.setName("datePattern");
        var.setValue(null);
        field.addVar(var);
        var = new Var();
        var.setName("datePatternStrict");
        var.setValue("yyyy/MM/dd");
        field.addVar(var);
        var = new Var();
        var.setName("startDate");
        var.setValue("2005/01/01");
        field.addVar(var);
        var = new Var();
        var.setName("endDate");
        var.setValue("2005/01/03");
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
        request.setLocale(Locale.JAPANESE);
        // ValidatorResourcesインスタンス
        ValidatorResources validatorResources = new ValidatorResources();
        // Validatorインスタンス
        Validator validator = new Validator(validatorResources);

        // テスト実行
        boolean result =
            FieldChecksEx.validateDateRange(
                bean,
                va,
                field,
                errors,
                validator,
                request);
        // テスト結果確認
        // trueが返却されていること。
        assertFalse(result);
        // エラー情報が設定されていること。
        assertEquals(1, errors.size());

        Iterator it = errors.get();
        ActionMessage message = (ActionMessage) it.next();
        assertEquals("message", message.getKey());
    }

    /**
     * testValidateDateRange32()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) bean:Map:<br>
     *                ["field1"=<br>
     *                　"2005/01/05"]<br>
     *         (引数) va:not null<br>
     *         (引数) field:not null<br>
     *                var:datePattern=null<br>
     *                var:datePatternStrict="yyyy/MM/dd"<br>
     *                var:startDate="2005/01/01"<br>
     *                var:endDate="2005/01/03"<br>
     *                Msg("message","message")<br>
     *                property="field2"<br>
     *         (引数) errors:not null<br>
     *                (空の要素)<br>
     *         (引数) validator:not null<br>
     *         (引数) request:not null<br>
     *                Locale=JAPANESE<br>
     *
     * <br>
     * 期待値：(戻り値) boolean:true<br>
     *         (状態変化) errors:not null<br>
     *                    (空の要素)<br>
     *
     * <br>
     * 引数のbeanがString型ではなく、fieldから取得した名前のプロパティが存在しない場合、trueが取得できることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testValidateDateRange32() throws Exception {
        //テストデータ設定
        // ++++ beanオブジェクト ++++
        Map<String, String> bean = new HashMap<String, String>();
        bean.put("field1", "2005/01/05");
        // ++++ 検証設定オブジェクト
        ValidatorAction va = new ValidatorAction();
        va.setName("message");
        // ++++ 検証フィールド情報
        Field field = new Field();
        field.setProperty("field2");
        Var var = new Var();
        var.setName("datePattern");
        var.setValue(null);
        field.addVar(var);
        var = new Var();
        var.setName("datePatternStrict");
        var.setValue("yyyy/MM/dd");
        field.addVar(var);
        var = new Var();
        var.setName("startDate");
        var.setValue("2005/01/01");
        field.addVar(var);
        var = new Var();
        var.setName("endDate");
        var.setValue("2005/01/03");
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
        request.setLocale(Locale.JAPANESE);
        // ValidatorResourcesインスタンス
        ValidatorResources validatorResources = new ValidatorResources();
        // Validatorインスタンス
        Validator validator = new Validator(validatorResources);

        // テスト実行
        boolean result =
            FieldChecksEx.validateDateRange(
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

}
