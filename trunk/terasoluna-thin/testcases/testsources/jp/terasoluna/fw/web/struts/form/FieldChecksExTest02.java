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
 * {@link jp.terasoluna.fw.web.struts.form.FieldChecksEx} クラスのブラックボックステスト。
 *
 * <p>
 * <h4>【クラスの概要】</h4>
 * Validator追加ルールクラス。
 * <p>
 *
 * @see jp.terasoluna.fw.web.struts.form.FieldChecksEx
 */
public class FieldChecksExTest02 extends PropertyTestCase {

    /**
     * このテストケースを実行する為の
     * GUI アプリケーションを起動する。
     *
     * @param args java コマンドに設定されたパラメータ
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(FieldChecksExTest02.class);
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
    public FieldChecksExTest02(String name) {
        super(name);
    }

    /**
     * testValidateCapAlphaNumericString01()
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
     *                field:var<br>
     *                name:"mask"<br>
     *                value="^[a-z]*$"<br>
     *                jsType="false"<br>
     *         (引数) errors:not null<br>
     *                (空の要素)<br>
     *         (引数) validator:not null<br>
     *         (引数) request:not null<br>
     *         (状態) validateMask:true<br>
     *
     * <br>
     * 期待値：(戻り値) boolean:true<br>
     *         (状態変化) field:var：<br>
     *                    name:"mask"<br>
     *                    value="^[a-z]*$"<br>
     *                    jsType="false"<br>
     *         (状態変化) errors:状態変化なし<br>
     *
     * <br>
     * 引数のbeanがnullのとき、trueが返却され、
     * errorsにメッセージが追加されないことを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testValidateCapAlphaNumericString01() throws Exception {
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
        msg.setResource(false);
        field.addMsg(msg);
        field.addVar("mask", "^[a-z]*$", "false");
        // エラー情報
        ActionMessages errors = new ActionMessages();
        // 擬似HTTPリクエスト
        MockHttpServletRequest request = new MockHttpServletRequest();
        Validator validator = new Validator(new ValidatorResources());

        // テスト実行
        boolean result =
            FieldChecksEx.validateCapAlphaNumericString(
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
        // fieldの中身チェック
        Var var = field.getVar("mask");
        assertNotNull(var);
        assertEquals("mask", var.getName());
        assertEquals("^[a-z]*$", var.getValue());
        assertEquals("false", var.getJsType());
    }

    /**
     * testValidateCapAlphaNumericString02()
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
     *         (状態) validateMask:true<br>
     *
     * <br>
     * 期待値：(戻り値) boolean:true<br>
     *         (状態変化) field:var：<br>
     *                    name="mask"<br>
     *                    がnullであること。<br>
     *         (状態変化) errors:状態変化なし<br>
     *
     * <br>
     * 引数のbeanが空文字のとき、trueが返却され、
     * errorsにメッセージが追加されないことを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testValidateCapAlphaNumericString02() throws Exception {
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
        msg.setResource(false);
        field.addMsg(msg);
        // エラー情報
        ActionMessages errors = new ActionMessages();
        // 擬似HTTPリクエスト
        MockHttpServletRequest request = new MockHttpServletRequest();
        Validator validator = new Validator(new ValidatorResources());

        // テスト実行
        boolean result =
            FieldChecksEx.validateCapAlphaNumericString(
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
        // fieldの中身チェック
        Var var = field.getVar("mask");
        assertNull(var);
    }

    /**
     * testValidateCapAlphaNumericString03()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) bean:"ABC0"<br>
     *         (引数) va:not null<br>
     *         (引数) field:not null<br>
     *                Msg("message","message")<br>
     *         (引数) errors:not null<br>
     *                (空の要素)<br>
     *         (引数) validator:not null<br>
     *         (引数) request:not null<br>
     *         (状態) validateMask:true<br>
     *
     * <br>
     * 期待値：(戻り値) boolean:true<br>
     *         (状態変化) field:var：<br>
     *                    name="mask"<br>
     *                    がnullであること。<br>
     *         (状態変化) errors:状態変化なし<br>
     *
     * <br>
     * 引数のbeanが大文字の半角英数文字のみで構成されている場合、
     * trueが返却され、errorsにメッセージが追加されないことを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testValidateCapAlphaNumericString03() throws Exception {
        //テストデータ設定
        // ++++ beanオブジェクト ++++
        String bean = "ABC0";
        // ++++ 検証設定オブジェクト
        ValidatorAction va = new ValidatorAction();
        va.setName("message");
        // ++++ 検証フィールド情報
        Field field = new Field();
        // メッセージ設定
        Msg msg = new Msg();
        msg.setKey("message");
        msg.setResource(false);
        field.addMsg(msg);

        // エラー情報
        ActionMessages errors = new ActionMessages();

        // 擬似HTTPリクエスト
        MockHttpServletRequest request = new MockHttpServletRequest();
        Validator validator = new Validator(new ValidatorResources());

        // テスト実行
        boolean result =
            FieldChecksEx.validateCapAlphaNumericString(
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
        // fieldの中身チェック
        Var var = field.getVar("mask");
        assertNull(var);
    }

    /**
     * testValidateCapAlphaNumericString04()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) bean:HashMap<br>
     *                ["field1"="Aa0"]<br>
     *         (引数) va:not null<br>
     *         (引数) field:not null<br>
     *                property="field1"<br>
     *                Msg("message","message")<br>
     *         (引数) errors:not null<br>
     *                ActionMessage("testMessage")<br>
     *         (引数) validator:not null<br>
     *         (引数) request:not null<br>
     *         (状態) validateMask:false<br>
     *
     * <br>
     * 期待値：(戻り値) boolean:false<br>
     *         (状態変化) field:var：<br>
     *                    name="mask"<br>
     *                    がnullであること。<br>
     *         (状態変化) errors:ActionMessage("testMessage")<br>
     *                    ActionMessage("message")<br>
     *
     * <br>
     * 引数のbeanがString型ではない場合、
     * fieldから取得したプロパティの値に対してチェックが行われることを確認する。
     * 入力チェックエラーとなったとき、errorsにメッセージが追加されることを
     * 確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testValidateCapAlphaNumericString04() throws Exception {
        //テストデータ設定
        // ++++ beanオブジェクト ++++
        Map<String, String> bean = new HashMap<String, String>();
        bean.put("field1", "Aa0");

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

        // エラー情報（ActionMessageを1件設定）
        ActionMessages errors = new ActionMessages();
        ActionMessage error = new ActionMessage("testMessage");
        errors.add(Globals.ERROR_KEY, error);

        // 擬似HTTPリクエスト
        MockHttpServletRequest request = new MockHttpServletRequest();
        Validator validator = new Validator(new ValidatorResources());

        // テスト実行
        boolean result =
            FieldChecksEx.validateCapAlphaNumericString(
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
        // fieldの中身チェック
        Var var = field.getVar("mask");
        assertNull(var);
    }

    /**
     * testValidateHankakuKanaString01()
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
     * 引数のbeanがnullのとき、
     * エラーログを出力してtrueが返却されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testValidateHankakuKanaString01() throws Exception {
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
        Validator validator = new Validator(new ValidatorResources());

        // テスト実行
        boolean result =
            FieldChecksEx.validateHankakuKanaString(
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
     * testValidateHankakuKanaString02()
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
     * 引数のbeanが空文字のとき、trueが返却されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testValidateHankakuKanaString02() throws Exception {
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
        Validator validator = new Validator(new ValidatorResources());

        // テスト実行
        boolean result =
            FieldChecksEx.validateHankakuKanaString(
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
     * testValidateHankakuKanaString03()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) bean:String:"ﾊﾝｶｸ"<br>
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
     * 引数のbeanが半角カナ文字のみで構成されているとき、
     * trueが返却されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testValidateHankakuKanaString03() throws Exception {
        //テストデータ設定
        // ++++ beanオブジェクト ++++
        String bean = "ﾊﾝｶｸ";
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

        // エラー情報
        ActionMessages errors = new ActionMessages();

        // 擬似HTTPリクエスト
        MockHttpServletRequest request = new MockHttpServletRequest();
        Validator validator = new Validator(new ValidatorResources());

        // テスト実行
        boolean result =
            FieldChecksEx.validateHankakuKanaString(
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
     * testValidateHankakuKanaString04()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) bean:String:"1aあ"<br>
     *         (引数) va:not null<br>
     *         (引数) field:not null<br>
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
     * 引数のbeanに半角カナ以外の文字列が含まれている場合、
     * エラーメッセージを追加し、falseが返却されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testValidateHankakuKanaString04() throws Exception {
        //テストデータ設定
        // ++++ beanオブジェクト ++++
        String bean = "1aあ";
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

        // エラー情報（ActionMessageを1件設定）
        ActionMessages errors = new ActionMessages();
        ActionMessage error = new ActionMessage("testMessage");
        errors.add(Globals.ERROR_KEY, error);

        // 擬似HTTPリクエスト
        MockHttpServletRequest request = new MockHttpServletRequest();
        Validator validator = new Validator(new ValidatorResources());

        // テスト実行
        boolean result =
            FieldChecksEx.validateHankakuKanaString(
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
     * testValidateHankakuKanaString05()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) bean:String:"ﾊﾝｶｸア"<br>
     *         (引数) va:not null<br>
     *         (引数) field:not null<br>
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
     * 引数のbeanに半角カナ以外の文字列が含まれている場合、エラーメッセージを追加し、falseが返却されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testValidateHankakuKanaString05() throws Exception {
        //テストデータ設定
        // ++++ beanオブジェクト ++++
        String bean = "ﾊﾝｶｸア";
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

        // エラー情報
        ActionMessages errors = new ActionMessages();
        // 擬似HTTPリクエスト
        MockHttpServletRequest request = new MockHttpServletRequest();
        Validator validator = new Validator(new ValidatorResources());

        // テスト実行
        boolean result =
            FieldChecksEx.validateHankakuKanaString(
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
        assertFalse(errors.isEmpty());
        // エラー情報に設定されたメッセージが登録されていること。
        Iterator it = errors.get();
        ActionMessage error = (ActionMessage) it.next();
        assertEquals("message", error.getKey());
        // エラーが1件であること
        assertFalse(it.hasNext());
    }

    /**
     * testValidateHankakuKanaString06()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) bean:Map:["field1"="ｧｨｩｪｫ"]<br>
     *         (引数) va:not null<br>
     *         (引数) field:not null<br>
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
     * 引数のbeanがString型ではない場合、
     * fieldから取得した名前のプロパティに対してチェックを行うことを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testValidateHankakuKanaString06() throws Exception {
        //テストデータ設定
        // ++++ beanオブジェクト ++++
        Map<String, String> bean = new HashMap<String, String>();
        bean.put("field1", "ｧｨｩｪｫ");

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

        // エラー情報
        ActionMessages errors = new ActionMessages();
        // 擬似HTTPリクエスト
        MockHttpServletRequest request = new MockHttpServletRequest();
        Validator validator = new Validator(new ValidatorResources());

        // テスト実行
        boolean result =
            FieldChecksEx.validateHankakuKanaString(
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
     * testValidateHankakuKanaString07()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) bean:Map:["field1"="123"]<br>
     *         (引数) va:not null<br>
     *         (引数) field:not null<br>
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
     * 引数のbeanがString型ではない場合、fieldから取得した名前のプロパティに
     * 対してチェックを行うことを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testValidateHankakuKanaString07() throws Exception {
        //テストデータ設定
        // ++++ beanオブジェクト ++++
        Map<String, String> bean = new HashMap<String, String>();
        bean.put("field1", "123");

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

        // エラー情報
        ActionMessages errors = new ActionMessages();
        // 擬似HTTPリクエスト
        MockHttpServletRequest request = new MockHttpServletRequest();
        Validator validator = new Validator(new ValidatorResources());

        // テスト実行
        boolean result =
            FieldChecksEx.validateHankakuKanaString(
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
        assertFalse(errors.isEmpty());
        // エラー情報に設定されたメッセージが登録されていること。
        Iterator it = errors.get();
        ActionMessage error = (ActionMessage) it.next();
        assertEquals("message", error.getKey());
        // エラーが1件であること
        assertFalse(it.hasNext());
    }

    /**
     * testValidateHankakuKanaString08()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) bean:Map:["field1"="123"]<br>
     *         (引数) va:not null<br>
     *         (引数) field:not null<br>
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
     * 引数のbeanがString型ではなく、fieldから取得した名前のプロパティが存在しない場合、trueが取得できることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testValidateHankakuKanaString08() throws Exception {
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

        // エラー情報
        ActionMessages errors = new ActionMessages();
        // 擬似HTTPリクエスト
        MockHttpServletRequest request = new MockHttpServletRequest();
        Validator validator = new Validator(new ValidatorResources());

        // テスト実行
        boolean result =
            FieldChecksEx.validateHankakuKanaString(
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
     * testValidateNumericString01()
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
     *                field:var<br>
     *                name:"mask"<br>
     *                value="^[a-z]*$"<br>
     *                jsType="false"<br>
     *         (引数) errors:not null<br>
     *                (空の要素)<br>
     *         (引数) validator:not null<br>
     *         (引数) request:not null<br>
     *         (状態) validateMask:true<br>
     *
     * <br>
     * 期待値：(戻り値) boolean:true<br>
     *         (状態変化) field:var：<br>
     *                    name:"mask"<br>
     *                    value="^[a-z]*$"<br>
     *                    jsType="false"<br>
     *         (状態変化) errors:状態変化なし<br>
     *
     * <br>
     * 引数のbeanがnullのとき、trueが返却され、
     * errorsにメッセージが追加されないことを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testValidateNumericString01() throws Exception {
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
        field.addVar("mask", "^[a-z]*$", "false");
        // エラー情報
        ActionMessages errors = new ActionMessages();
        // 擬似HTTPリクエスト
        MockHttpServletRequest request = new MockHttpServletRequest();
        Validator validator = new Validator(new ValidatorResources());

        // テスト実行
        boolean result =
            FieldChecksEx.validateNumericString(
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
        // fieldの状態変化確認
        Var var = field.getVar("mask");
        assertNotNull(var);
        assertEquals("mask", var.getName());
        assertEquals("^[a-z]*$", var.getValue());
        assertEquals("false", var.getJsType());
    }

    /**
     * testValidateNumericString02()
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
     *         (状態) validateMask:true<br>
     *
     * <br>
     * 期待値：(戻り値) boolean:true<br>
     *         (状態変化) field:var：<br>
     *                    name="mask"<br>
     *                    がnullであること。<br>
     *         (状態変化) errors:状態変化なし<br>
     *
     * <br>
     * 引数のbeanが空文字のとき、trueが返却され、
     * errorsにメッセージが追加されないことを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testValidateNumericString02() throws Exception {
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
        Validator validator = new Validator(new ValidatorResources());

        // テスト実行
        boolean result =
            FieldChecksEx.validateNumericString(
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
        // fieldの状態変化確認
        Var var = field.getVar("mask");
        assertNull(var);
    }

    /**
     * testValidateNumericString03()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) bean:"9876"<br>
     *         (引数) va:not null<br>
     *         (引数) field:not null<br>
     *                Msg("message","message")<br>
     *         (引数) errors:not null<br>
     *                (空の要素)<br>
     *         (引数) validator:not null<br>
     *         (引数) request:not null<br>
     *         (状態) validateMask:true<br>
     *
     * <br>
     * 期待値：(戻り値) boolean:true<br>
     *         (状態変化) field:var：<br>
     *                    name="mask"<br>
     *                    がnullであること。<br>
     *         (状態変化) errors:状態変化なし<br>
     *
     * <br>
     * 引数のbeanが数文字のみで構成されている場合、trueが返却され、
     * errorsにメッセージが追加されないことを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testValidateNumericString03() throws Exception {
        //テストデータ設定
        // ++++ beanオブジェクト ++++
        String bean = "9876";
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

        // エラー情報
        ActionMessages errors = new ActionMessages();

        // 擬似HTTPリクエスト
        MockHttpServletRequest request = new MockHttpServletRequest();
        Validator validator = new Validator(new ValidatorResources());

        // テスト実行
        boolean result =
            FieldChecksEx.validateNumericString(
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
        // fieldの状態変化確認
        Var var = field.getVar("mask");
        assertNull(var);
    }

    /**
     * testValidateNumericString04()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) bean:HashMap<br>
     *                ["field1"="Aa0"]<br>
     *         (引数) va:not null<br>
     *         (引数) field:not null<br>
     *                property="field1"<br>
     *                Msg("message","message")<br>
     *         (引数) errors:not null<br>
     *                ActionMessage("testMessage")<br>
     *         (引数) validator:not null<br>
     *         (引数) request:not null<br>
     *         (状態) validateMask:false<br>
     *
     * <br>
     * 期待値：(戻り値) boolean:false<br>
     *         (状態変化) field:var：<br>
     *                    name="mask"<br>
     *                    がnullであること。<br>
     *         (状態変化) errors:ActionMessage("testMessage")<br>
     *                    ActionMessage("message")<br>
     *
     * <br>
     * 引数のbeanがString型ではない場合、fieldから取得したプロパティの値に対して
     * チェックが行われることを確認する。
     * 入力チェックエラーとなったとき、errorsにメッセージが追加されることを
     * 確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testValidateNumericString04() throws Exception {
        //テストデータ設定
        // ++++ beanオブジェクト ++++
        Map<String, String> bean = new HashMap<String, String>();
        bean.put("field1", "Aa0");

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

        // エラー情報（ActionMessageを1件設定）
        ActionMessages errors = new ActionMessages();
        ActionMessage error = new ActionMessage("testMessage");
        errors.add(Globals.ERROR_KEY, error);

        // 擬似HTTPリクエスト
        MockHttpServletRequest request = new MockHttpServletRequest();
        Validator validator = new Validator(new ValidatorResources());

        // テスト実行
        boolean result =
            FieldChecksEx.validateNumericString(
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
        // fieldの状態変化確認
        Var var = field.getVar("mask");
        assertNull(var);
    }

}
