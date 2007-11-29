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

import javax.servlet.http.HttpServletRequest;

import jp.terasoluna.fw.web.struts.action.ActionMappingEx;
import jp.terasoluna.utlib.DynaActionFormCreator;
import jp.terasoluna.utlib.LogUTUtil;
import jp.terasoluna.utlib.MockHttpServletRequest;
import jp.terasoluna.utlib.MockHttpSession;
import jp.terasoluna.utlib.PropertyTestCase;
import jp.terasoluna.utlib.UTUtil;

import org.apache.commons.validator.Arg;
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
public class FieldChecksExTest06 extends PropertyTestCase {

    /**
     * このクラスのテストで使用するDynaValidatorActionFormExの設定ファイル。
     */
    private static final String CONFIG_FILE_NAME =
        FieldChecksExTest06.class.getResource(
                "FieldChecksExTest.xml").getPath();

    /**
     * DynaValidatorActionFormExを生成するdigesterのルールファイル。
     */
    private final static String RULES_FILE_NAME =
        FieldChecksExTest06.class.getResource(
                "FieldChecksExTest-rules.xml").getPath();

    /**
     * DynaValidatorActionFormExを生成するクラス。
     */
    private static final DynaActionFormCreator creator
        = new DynaActionFormCreator(RULES_FILE_NAME);

    /**
     * テストに使用するDynaValidatorActionFormExインスタンス。
     */
    private DynaValidatorActionFormEx formEx = null;

    /**
     * 正常な引数の型リスト。
     */
    public Class[] validClassList = {
        Object.class,
        ValidatorAction.class,
        Field.class,
        ActionMessages.class,
        Validator.class,
        HttpServletRequest.class
    };

    /**
     * 正常な引数の文字列。
     */
    public String validClassStr = "java.lang.Object," +
            "org.apache.commons.validator.ValidatorAction," +
            "org.apache.commons.validator.Field," +
            "org.apache.struts.action.ActionMessages," +
            "org.apache.commons.validator.Validator," +
            "javax.servlet.http.HttpServletRequest";

    /**
     * テストで使用するアクションフォーム。
     */
    FieldChecksEx_ValidatorActionFormExStub01 form = null;

    /**
     * このテストケースを実行する為の
     * GUI アプリケーションを起動する。
     *
     * @param args java コマンドに設定されたパラメータ
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(FieldChecksExTest06.class);
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
        form = new FieldChecksEx_ValidatorActionFormExStub01();
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
    public FieldChecksExTest06(String name) {
        super(name);
    }

    /**
     * testValidateArraysIndex01()
     * <br><br>
     *
     * (異常系)
     * <br>
     * 観点：C,F
     * <br><br>
     * 入力値：(引数) bean:*<br>
     *         (引数) va:*<br>
     *         (引数) field:var:indexedListProperty=null<br>
     *         (引数) errors:not null<br>
     *                (空の要素)<br>
     *         (引数) validator:not null<br>
     *         (引数) request:not null<br>
     *         (引数) session:not null<br>
     *         (引数) ActionForm:not null<br>
     *
     * <br>
     * 期待値：(戻り値) boolean:true<br>
     *         (状態変化) errors:not null<br>
     *                    (空の要素)<br>
     *
     * <br>
     * fieldのindexedListPropertyがnullのとき、trueが取得できることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
//    public void testValidateArraysIndex01() throws Exception {
//        //テストデータ設定
//        // ++++ beanオブジェクト ++++
//        String bean = null;
//        // ++++ 検証設定オブジェクト
//        ValidatorAction va = new ValidatorAction();
//        // ++++ 検証フィールド情報
//        Field field = new Field();
//        field.setIndexedListProperty(null);
//        // エラー情報
//        ActionMessages errors = new ActionMessages();
//        // 擬似HTTPリクエスト
//        HttpServletRequest request = new MockHttpServletRequest();
//
//        // ValidatorResourcesインスタンス
//        ValidatorResources validatorResources = new ValidatorResources();
//        // Validatorインスタンス
//        Validator validator = new Validator(validatorResources);
//
//        // テスト実行
//        boolean result =
//            FieldChecksEx.validateArraysIndex(
//                    bean,
//                    va,
//                    field,
//                    errors,
//                    validator,
//                    request);
//        // テスト結果確認
//        // trueが返却されていること。
//        assertTrue(result);
//        // エラー情報が空であること。
//        assertTrue(errors.isEmpty());
//    }

    /**
     * testValidateArraysIndex02()
     * <br><br>
     *
     * (異常系)
     * <br>
     * 観点：C,F
     * <br><br>
     * 入力値：(引数) bean:*<br>
     *         (引数) va:*<br>
     *         (引数) field:var:indexedListProperty=""<br>
     *         (引数) errors:not null<br>
     *                (空の要素)<br>
     *         (引数) validator:not null<br>
     *         (引数) request:not null<br>
     *         (引数) session:not null<br>
     *         (引数) ActionForm:not null<br>
     *
     * <br>
     * 期待値：(戻り値) boolean:true<br>
     *         (状態変化) errors:not null<br>
     *                    (空の要素)<br>
     *
     * <br>
     * fieldのindexedListPropertyが空文字のとき、
     * trueが取得できることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
//    public void testValidateArraysIndex02() throws Exception {
//        //テストデータ設定
//        // ++++ beanオブジェクト ++++
//        String bean = null;
//
//        // ++++ 検証設定オブジェクト
//        ValidatorAction va = new ValidatorAction();
//
//        // ++++ 検証フィールド情報
//        Field field = new Field();
//        field.setIndexedListProperty("");
//
//        // エラー情報
//        ActionMessages errors = new ActionMessages();
//        // 擬似HTTPリクエスト
//        HttpServletRequest request = new MockHttpServletRequest();
//
//        // ValidatorResourcesインスタンス
//        ValidatorResources validatorResources = new ValidatorResources();
//        // Validatorインスタンス
//        Validator validator = new Validator(validatorResources);
//
//        // テスト実行
//        boolean result =
//            FieldChecksEx.validateArraysIndex(
//                    bean,
//                    va,
//                    field,
//                    errors,
//                    validator,
//                    request);
//        // テスト結果確認
//        // trueが返却されていること。
//        assertTrue(result);
//        // エラー情報が空であること。
//        assertTrue(errors.isEmpty());
//    }

    /**
     * testValidateArraysIndex03()
     * <br><br>
     *
     * (異常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) bean:*<br>
     *         (引数) va:methodParams：空のリスト<br>
     *                name：not null<br>
     *         (引数) field:not null<br>
     *         (引数) errors:not null<br>
     *                (空の要素)<br>
     *         (引数) validator:not null<br>
     *         (引数) request:not null<br>
     *         (引数) session:not null<br>
     *         (引数) ActionForm:not null<br>
     *
     * <br>
     * 期待値：(戻り値) boolean:true<br>
     *         (状態変化) ログ:ログレベル：エラー<br>
     *                    Class pattern length is zero.<br>
     *         (状態変化) errors:not null<br>
     *                    (空の要素)<br>
     *
     * <br>
     * vaのmethodParamsが空のリストの場合、trueが取得できることを確認する。<br>
     * ※getParamClassのテストを包含する
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testValidateArraysIndex03() throws Exception {
        //テストデータ設定
        // ++++ beanオブジェクト ++++
        String bean = null;

        // ++++ 検証設定オブジェクト
        ValidatorAction va = new ValidatorAction();
        va.setMethodParams("");

        // ++++ 検証フィールド情報
        Field field = new Field();

        // エラー情報
        ActionMessages errors = new ActionMessages();
        // 擬似HTTPリクエスト
        HttpServletRequest request = new MockHttpServletRequest();

        // ValidatorResourcesインスタンス
        ValidatorResources validatorResources = new ValidatorResources();
        // Validatorインスタンス
        Validator validator = new Validator(validatorResources);

        // テスト実行
        boolean result =
            FieldChecksEx.validateArraysIndex(
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
        assertTrue(LogUTUtil.checkError("Class pattern length is zero."));
    }

    /**
     * testValidateArraysIndex04()
     * <br><br>
     *
     * (異常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) bean:*<br>
     *         (引数) va:methodParams：testClass<br>
     *                存在しないクラス名<br>
     *         (引数) field:not null<br>
     *         (引数) errors:not null<br>
     *                (空の要素)<br>
     *         (引数) validator:not null<br>
     *         (引数) request:not null<br>
     *         (引数) session:not null<br>
     *         (引数) ActionForm:not null<br>
     *
     * <br>
     * 期待値：(戻り値) boolean:true<br>
     *         (状態変化) ログ:ログレベル：エラー<br>
     *                    例外クラス：ClassNotFoundException<br>
     *                    ログレベル：エラー<br>
     *                    メッセージ：<br>
     *                    "Can not get class pattern."<br>
     *         (状態変化) errors:not null<br>
     *                    (空の要素)<br>
     *
     * <br>
     * vaのmethodParamsに存在しないクラス名がある場合、
     * trueが取得できることを確認する。<br>
     * ※getParamClassのテストを包含する
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testValidateArraysIndex04() throws Exception {
        //テストデータ設定
        // ++++ beanオブジェクト ++++
        String bean = null;

        // ++++ 検証設定オブジェクト
        ValidatorAction va = new ValidatorAction();
        va.setMethodParams("testClass");

        // ++++ 検証フィールド情報
        Field field = new Field();

        // エラー情報
        ActionMessages errors = new ActionMessages();
        // 擬似HTTPリクエスト
        HttpServletRequest request = new MockHttpServletRequest();

        // ValidatorResourcesインスタンス
        ValidatorResources validatorResources = new ValidatorResources();
        // Validatorインスタンス
        Validator validator = new Validator(validatorResources);

        // テスト実行
        boolean result =
            FieldChecksEx.validateArraysIndex(
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
        assertTrue(LogUTUtil.checkError("", new ClassNotFoundException()));
        assertTrue(LogUTUtil.checkError("Can not get class pattern."));
    }

    /**
     * testValidateArraysIndex05()
     * <br><br>
     *
     * (異常系)
     * <br>
     * 観点：C,F
     * <br><br>
     * 入力値：(引数) bean:*<br>
     *         (引数) va:methodParams：6つ全て正常<br>
     *                name：null<br>
     *         (引数) field:not null<br>
     *         (引数) errors:not null<br>
     *                (空の要素)<br>
     *         (引数) validator:not null<br>
     *         (引数) request:not null<br>
     *         (引数) session:not null<br>
     *         (引数) ActionForm:not null<br>
     *
     * <br>
     * 期待値：(戻り値) boolean:true<br>
     *         (状態変化) ログ:ログレベル：エラー<br>
     *                    メッセージ：<br>
     *                    "Can not get validateMethod."<br>
     *         (状態変化) errors:not null<br>
     *                    (空の要素)<br>
     *
     * <br>
     * vaのnameがnullのとき、trueが取得できることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testValidateArraysIndex05() throws Exception {
        //テストデータ設定
        // ++++ beanオブジェクト ++++
        String bean = null;

        // ++++ 検証設定オブジェクト
        ValidatorAction va = new ValidatorAction();
        va.setMethodParams(this.validClassStr);
        va.setName(null);

        // ++++ 検証フィールド情報
        Field field = new Field();

        // エラー情報
        ActionMessages errors = new ActionMessages();
        // 擬似HTTPリクエスト
        HttpServletRequest request = new MockHttpServletRequest();

        // ValidatorResourcesインスタンス
        ValidatorResources validatorResources = new ValidatorResources();
        // Validatorインスタンス
        Validator validator = new Validator(validatorResources);

        // テスト実行
        boolean result =
            FieldChecksEx.validateArraysIndex(
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
        assertTrue(LogUTUtil.checkError("Can not get validateMethod."));
    }

    /**
     * testValidateArraysIndex06()
     * <br><br>
     *
     * (異常系)
     * <br>
     * 観点：C,F
     * <br><br>
     * 入力値：(引数) bean:*<br>
     *         (引数) va:methodParams：6つ全て正常<br>
     *                name：""<br>
     *         (引数) field:not null<br>
     *         (引数) errors:not null<br>
     *                (空の要素)<br>
     *         (引数) validator:not null<br>
     *         (引数) request:not null<br>
     *         (引数) session:not null<br>
     *         (引数) ActionForm:not null<br>
     *
     * <br>
     * 期待値：(戻り値) boolean:true<br>
     *         (状態変化) ログ:ログレベル：エラー<br>
     *                    メッセージ：<br>
     *                    "Can not get validateMethod."<br>
     *         (状態変化) errors:not null<br>
     *                    (空の要素)<br>
     *
     * <br>
     * vaのnameが空文字のとき、trueが取得できることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testValidateArraysIndex06() throws Exception {
        //テストデータ設定
        // ++++ beanオブジェクト ++++
        String bean = null;

        // ++++ 検証設定オブジェクト
        ValidatorAction va = new ValidatorAction();
        va.setMethodParams(this.validClassStr);
        va.setName("");

        // ++++ 検証フィールド情報
        Field field = new Field();

        // エラー情報
        ActionMessages errors = new ActionMessages();
        // 擬似HTTPリクエスト
        HttpServletRequest request = new MockHttpServletRequest();

        // ValidatorResourcesインスタンス
        ValidatorResources validatorResources = new ValidatorResources();
        // Validatorインスタンス
        Validator validator = new Validator(validatorResources);

        // テスト実行
        boolean result =
            FieldChecksEx.validateArraysIndex(
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
        assertTrue(LogUTUtil.checkError("Can not get validateMethod."));
    }

    /**
     * testValidateArraysIndex07()
     * <br><br>
     *
     * (異常系)
     * <br>
     * 観点：C,F
     * <br><br>
     * 入力値：(引数) bean:*<br>
     *         (引数) va:methodParams：6つ全て正常<br>
     *                name："testArray"<br>
     *         (引数) field:not null<br>
     *         (引数) errors:not null<br>
     *                (空の要素)<br>
     *         (引数) validator:not null<br>
     *         (引数) request:not null<br>
     *         (引数) session:not null<br>
     *         (引数) ActionForm:not null<br>
     *
     * <br>
     * 期待値：(戻り値) boolean:true<br>
     *         (状態変化) ログ:ログレベル：エラー<br>
     *                    例外クラス：<br>
     *                    NoSuchMethodException<br>
     *                    ログレベル：エラー<br>
     *                    メッセージ：<br>
     *                    "Can not get validateMethod."<br>
     *         (状態変化) errors:not null<br>
     *                    (空の要素)<br>
     *
     * <br>
     * vaのnameから取得したメソッド名がFieldChecksEx、
     * ValidWhenに存在しない場合、trueが取得できることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testValidateArraysIndex07() throws Exception {
        //テストデータ設定
        // ++++ beanオブジェクト ++++
        String bean = null;

        // ++++ 検証設定オブジェクト
        ValidatorAction va = new ValidatorAction();
        va.setMethodParams(this.validClassStr);
        va.setName("testArray");

        // ++++ 検証フィールド情報
        Field field = new Field();

        // エラー情報
        ActionMessages errors = new ActionMessages();
        // 擬似HTTPリクエスト
        HttpServletRequest request = new MockHttpServletRequest();

        // ValidatorResourcesインスタンス
        ValidatorResources validatorResources = new ValidatorResources();
        // Validatorインスタンス
        Validator validator = new Validator(validatorResources);

        // テスト実行
        boolean result =
            FieldChecksEx.validateArraysIndex(
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
        assertTrue(LogUTUtil.checkError("", new NoSuchMethodException()));
        assertTrue(LogUTUtil.checkError("Can not get validateMethod."));
    }

    /**
     * testValidateArraysIndex08()
     * <br><br>
     *
     * (異常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) bean:*<br>
     *         (引数) va:methodParams："java.lang.String"<br>
     *                name："stringLengthArray"<br>
     *         (引数) field:not null<br>
     *         (引数) errors:not null<br>
     *                (空の要素)<br>
     *         (引数) validator:not null<br>
     *         (引数) request:not null<br>
     *         (引数) session:not null<br>
     *         (引数) ActionForm:not null<br>
     *
     * <br>
     * 期待値：(戻り値) boolean:true<br>
     *         (状態変化) ログ:ログレベル：エラー<br>
     *                    例外クラス：<br>
     *                    NoSuchMethodException<br>
     *                    ログレベル：エラー<br>
     *                    メッセージ：<br>
     *                    "Can not get validateMethod."<br>
     *         (状態変化) errors:not null<br>
     *                    (空の要素)<br>
     *
     * <br>
     * vaに設定されたチェックメソッドの引数と、
     * methodParamsで指定された引数が一致しない場合、
     * trueが取得できることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testValidateArraysIndex08() throws Exception {
        //テストデータ設定
        // ++++ beanオブジェクト ++++
        String bean = null;

        // ++++ 検証設定オブジェクト
        ValidatorAction va = new ValidatorAction();
        va.setMethodParams("java.lang.String");
        va.setName("stringLengthArray");

        // ++++ 検証フィールド情報
        Field field = new Field();

        // エラー情報
        ActionMessages errors = new ActionMessages();
        // 擬似HTTPリクエスト
        HttpServletRequest request = new MockHttpServletRequest();

        // ValidatorResourcesインスタンス
        ValidatorResources validatorResources = new ValidatorResources();
        // Validatorインスタンス
        Validator validator = new Validator(validatorResources);

        // テスト実行
        boolean result =
            FieldChecksEx.validateArraysIndex(
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
        assertTrue(LogUTUtil.checkError("", new NoSuchMethodException()));
        assertTrue(LogUTUtil.checkError("Can not get validateMethod."));
    }

    /**
     * testValidateArraysIndex09()
     * <br><br>
     *
     * (異常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) bean:*<br>
     *         (引数) va:methodParams：6つの順序が異なる<br>
     *                name："stringLengthArray"<br>
     *         (引数) field:not null<br>
     *         (引数) errors:not null<br>
     *                (空の要素)<br>
     *         (引数) validator:not null<br>
     *         (引数) request:not null<br>
     *         (引数) session:not null<br>
     *         (引数) ActionForm:not null<br>
     *
     * <br>
     * 期待値：(戻り値) boolean:true<br>
     *         (状態変化) ログ:ログレベル：エラー<br>
     *                    例外クラス：<br>
     *                    NoSuchMethodException<br>
     *                    ログレベル：エラー<br>
     *                    メッセージ：<br>
     *                    "Can not get validateMethod."<br>
     *         (状態変化) errors:not null<br>
     *                    (空の要素)<br>
     *
     * <br>
     * vaに設定されたチェックメソッドの引数と、
     * methodParamsで指定された引数が一致しない場合、
     * trueが取得できることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testValidateArraysIndex09() throws Exception {
        //テストデータ設定
        // ++++ beanオブジェクト ++++
        String bean = null;

        // ++++ 検証設定オブジェクト
        ValidatorAction va = new ValidatorAction();
        String argStr = "javax.servlet.http.HttpServletRequest," +
            "org.apache.commons.validator.ValidatorAction," +
            "org.apache.commons.validator.Field," +
            "org.apache.struts.action.ActionMessages," +
            "org.apache.commons.validator.Validator," +
            "java.lang.Object";
        va.setMethodParams(argStr);
        va.setName("stringLengthArray");

        // ++++ 検証フィールド情報
        Field field = new Field();

        // エラー情報
        ActionMessages errors = new ActionMessages();
        // 擬似HTTPリクエスト
        HttpServletRequest request = new MockHttpServletRequest();

        // ValidatorResourcesインスタンス
        ValidatorResources validatorResources = new ValidatorResources();
        // Validatorインスタンス
        Validator validator = new Validator(validatorResources);

        // テスト実行
        boolean result =
            FieldChecksEx.validateArraysIndex(
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
        assertTrue(LogUTUtil.checkError("", new NoSuchMethodException()));
        assertTrue(LogUTUtil.checkError("Can not get validateMethod."));
    }

    /**
     * testValidateArraysIndex10()
     * <br><br>
     *
     * (異常系)
     * <br>
     * 観点：C,F
     * <br><br>
     * 入力値：(引数) bean:*<br>
     *         (引数) va:methodParams：6つ全て正常<br>
     *                name："stringLengthArray"<br>
     *         (引数) field:not null<br>
     *         (引数) errors:not null<br>
     *                (空の要素)<br>
     *         (引数) validator:not null<br>
     *         (引数) request:ActionMapping：null<br>
     *         (引数) session:not null<br>
     *         (引数) ActionForm:not null<br>
     *
     * <br>
     * 期待値：(戻り値) boolean:true<br>
     *         (状態変化) ログ:ログレベル：エラー<br>
     *                    メッセージ：<br>
     *                    "Can not get ActionForm."<br>
     *         (状態変化) errors:not null<br>
     *                    (空の要素)<br>
     *
     * <br>
     * リクエストからActionMappingが取得できない場合、
     * trueが取得できることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testValidateArraysIndex10() throws Exception {
        //テストデータ設定
        // ++++ beanオブジェクト ++++
        String bean = null;

        // ++++ 検証設定オブジェクト
        ValidatorAction va = new ValidatorAction();
        va.setMethodParams(this.validClassStr);
        va.setName("stringLengthArray");

        // ++++ 検証フィールド情報
        Field field = new Field();

        // エラー情報
        ActionMessages errors = new ActionMessages();
        // 擬似HTTPリクエスト
        HttpServletRequest request = new MockHttpServletRequest();

        // ValidatorResourcesインスタンス
        ValidatorResources validatorResources = new ValidatorResources();
        // Validatorインスタンス
        Validator validator = new Validator(validatorResources);

        // テスト実行
        boolean result =
            FieldChecksEx.validateArraysIndex(
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
        assertTrue(LogUTUtil.checkError("Can not get ActionForm."));
    }

    /**
     * testValidateArraysIndex11()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) bean:*<br>
     *         (引数) va:methodParams：6つ全て正常<br>
     *                name："stringLengthArray"<br>
     *         (引数) field:not null<br>
     *         (引数) errors:not null<br>
     *                (空の要素)<br>
     *         (引数) validator:not null<br>
     *         (引数) request:アクションフォーム名："logon"<br>
     *         (引数) session:キー："logon"に対する値が<br>
     *                ActionForm実装オブジェクト以外<br>
     *         (引数) ActionForm:null<br>
     *
     * <br>
     * 期待値：(戻り値) boolean:true<br>
     *         (状態変化) ログ:ログレベル：エラー<br>
     *                    メッセージ：<br>
     *                    "Can not get ActionForm."<br>
     *         (状態変化) errors:not null<br>
     *                    (空の要素)<br>
     *
     * <br>
     * セッションから取得したアクションフォームがActionForm実装クラスではない場合、
     * trueが取得できることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testValidateArraysIndex11() throws Exception {
        //テストデータ設定
        // ++++ beanオブジェクト ++++
        String bean = null;

        // ++++ 検証設定オブジェクト
        ValidatorAction va = new ValidatorAction();
        va.setMethodParams(this.validClassStr);
        va.setName("stringLengthArray");

        // ++++ 検証フィールド情報
        Field field = new Field();

        // エラー情報
        ActionMessages errors = new ActionMessages();
        // 擬似HTTPリクエスト
        FieldChecksEx_HttpServletRequestImpl01 request =
            new FieldChecksEx_HttpServletRequestImpl01();
        ActionMappingEx mapping = new ActionMappingEx();
        mapping.setName("logon");
        request.setAttribute(Globals.MAPPING_KEY, mapping);
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("logon", "hoge");
        request.setSession(session);

        // ValidatorResourcesインスタンス
        ValidatorResources validatorResources = new ValidatorResources();
        // Validatorインスタンス
        Validator validator = new Validator(validatorResources);

        // テスト実行
        boolean result =
            FieldChecksEx.validateArraysIndex(
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
        assertTrue(LogUTUtil.checkError("Can not get ActionForm."));
    }

    /**
     * testValidateArraysIndex12()
     * <br><br>
     *
     * (異常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) bean:*<br>
     *         (引数) va:methodParams：6つ全て正常<br>
     *                name："stringLengthArray"<br>
     *         (引数) field:not null<br>
     *         (引数) errors:not null<br>
     *                (空の要素)<br>
     *         (引数) validator:not null<br>
     *         (引数) request:アクションフォーム名："logon"<br>
     *                キー："logon"に対する値が<br>
     *                ActionForm実装オブジェクト以外<br>
     *         (引数) session:キー："logon"に対する値が存在しない<br>
     *         (引数) ActionForm:null<br>
     *
     * <br>
     * 期待値：(戻り値) boolean:true<br>
     *         (状態変化) ログ:ログレベル：エラー<br>
     *                    メッセージ：<br>
     *                    "Can not get ActionForm."<br>
     *         (状態変化) errors:not null<br>
     *                    (空の要素)<br>
     *
     * <br>
     * リクエストから取得したアクションフォームがActionForm実装クラスではない場合、
     * trueが取得できることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testValidateArraysIndex12() throws Exception {
        //テストデータ設定
        // ++++ beanオブジェクト ++++
        String bean = null;

        // ++++ 検証設定オブジェクト
        ValidatorAction va = new ValidatorAction();
        va.setMethodParams(this.validClassStr);
        va.setName("stringLengthArray");

        // ++++ 検証フィールド情報
        Field field = new Field();

        // エラー情報
        ActionMessages errors = new ActionMessages();
        // 擬似HTTPリクエスト
        FieldChecksEx_HttpServletRequestImpl01 request =
            new FieldChecksEx_HttpServletRequestImpl01();
        ActionMappingEx mapping = new ActionMappingEx();
        mapping.setName("logon");
        request.setAttribute(Globals.MAPPING_KEY, mapping);
        request.setAttribute("logon", "hoge");


        // ValidatorResourcesインスタンス
        ValidatorResources validatorResources = new ValidatorResources();
        // Validatorインスタンス
        Validator validator = new Validator(validatorResources);

        // テスト実行
        boolean result =
            FieldChecksEx.validateArraysIndex(
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
        assertTrue(LogUTUtil.checkError("Can not get ActionForm."));
    }

    /**
     * testValidateArraysIndex13()
     * <br><br>
     *
     * (異常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) bean:*<br>
     *         (引数) va:methodParams：6つ全て正常<br>
     *                name："stringLengthArray"<br>
     *         (引数) field:not null<br>
     *         (引数) errors:not null<br>
     *                (空の要素)<br>
     *         (引数) validator:not null<br>
     *         (引数) request:アクションフォーム名："logon"<br>
     *                キー："logon"に対する値が存在しない<br>
     *         (引数) session:キー："logon"に対する値が存在しない<br>
     *         (引数) ActionForm:not null<br>
     *
     * <br>
     * 期待値：(戻り値) boolean:true<br>
     *         (状態変化) ログ:ログレベル：エラー<br>
     *                    メッセージ：<br>
     *                    "Can not get ActionForm."<br>
     *         (状態変化) errors:not null<br>
     *                    (空の要素)<br>
     *
     * <br>
     * アクションフォームが取得できない場合、trueが取得できることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testValidateArraysIndex13() throws Exception {
        //テストデータ設定
        // ++++ beanオブジェクト ++++
        String bean = null;

        // ++++ 検証設定オブジェクト
        ValidatorAction va = new ValidatorAction();
        va.setMethodParams(this.validClassStr);
        va.setName("stringLengthArray");

        // ++++ 検証フィールド情報
        Field field = new Field();

        // エラー情報
        ActionMessages errors = new ActionMessages();
        // 擬似HTTPリクエスト
        FieldChecksEx_HttpServletRequestImpl01 request =
            new FieldChecksEx_HttpServletRequestImpl01();
        ActionMappingEx mapping = new ActionMappingEx();
        mapping.setName("logon");
        request.setAttribute(Globals.MAPPING_KEY, mapping);


        // ValidatorResourcesインスタンス
        ValidatorResources validatorResources = new ValidatorResources();
        // Validatorインスタンス
        Validator validator = new Validator(validatorResources);

        // テスト実行
        boolean result =
            FieldChecksEx.validateArraysIndex(
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
        assertTrue(LogUTUtil.checkError("Can not get ActionForm."));
    }

    /**
     * testValidateArraysIndex14()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) bean:*<br>
     *         (引数) va:methodParams：6つ全て正常<br>
     *                name："stringLengthArray"<br>
     *                msg:key="message",name="stringLengthArray",resource="false"<br>
     *         (引数) field:property=testArray<br>
     *         (引数) errors:not null<br>
     *                (空の要素)<br>
     *         (引数) validator:not null<br>
     *         (引数) request:アクションフォーム名："logon"<br>
     *         (引数) session:キー："logon"に対する値が<br>
     *                ActionForm実装オブジェクト<br>
     *         (引数) ActionForm:String testArray="String"<br>
     *
     * <br>
     * 期待値：(戻り値) boolean:false<br>
     *         (状態変化) errors:ActionMessage("testMessage")<br>
     *
     * <br>
     * 検証対象のプロパティが配列、Collection型ではない場合、
     * trueが取得できることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testValidateArraysIndex14() throws Exception {
        //テストデータ設定
        // ++++ beanオブジェクト ++++
        String bean = null;

        // ++++ 検証設定オブジェクト
        ValidatorAction va = new ValidatorAction();
        va.setMethodParams(this.validClassStr);
        va.setName("stringLengthArray");

        // ++++ 検証フィールド情報
        Field field = new Field();
        field.setProperty("testArray");
        Msg msg = new Msg();
        msg.setKey("message");
        msg.setName("stringLengthArray");
        msg.setResource(false);
        field.addMsg(msg);
        Var var = new Var();
        var.setName("stringLength");
        var.setValue("3");
        field.addVar(var);

        // エラー情報
        ActionMessages errors = new ActionMessages();
        // 擬似HTTPリクエスト
        FieldChecksEx_HttpServletRequestImpl01 request =
            new FieldChecksEx_HttpServletRequestImpl01();
        ActionMappingEx mapping = new ActionMappingEx();
        mapping.setName("logon");
        request.setAttribute(Globals.MAPPING_KEY, mapping);
        MockHttpSession session = new MockHttpSession();
        form.setTestArray("String");
        session.setAttribute("logon", form);
        request.setSession(session);


        // ValidatorResourcesインスタンス
        ValidatorResources validatorResources = new ValidatorResources();
        // Validatorインスタンス
        Validator validator = new Validator(validatorResources);

        // テスト実行
        boolean result =
            FieldChecksEx.validateArraysIndex(
                    bean,
                    va,
                    field,
                    errors,
                    validator,
                    request);
        // テスト結果確認
        // falseが返却されていること。
        assertFalse(result);
        // エラー情報が1件追加されていること。
        assertEquals(1, errors.size());
        Iterator it = errors.get();
        ActionMessage message = (ActionMessage) it.next();
        assertEquals("message", message.getKey());

    }

    /**
     * testValidateArraysIndex15()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) bean:*<br>
     *         (引数) va:methodParams：6つ全て正常<br>
     *                name："stringLengthArray"<br>
     *         (引数) field:property="codeArray"<br>
     *                var:stringLength=3<br>
     *                arg:position="0",key="##INDEX",resource="false"<br>
     *                arg:position="1",key="${var:length}",resource="false"<br>
     *         (引数) errors:not null<br>
     *                (空の要素)<br>
     *         (引数) validator:not null<br>
     *         (引数) request:アクションフォーム名："logon"<br>
     *                キー："logon"に対する値が<br>
     *                ActionForm実装オブジェクト<br>
     *         (引数) session:キー："logon"に対する値が存在しない<br>
     *         (引数) ActionForm:ArrayList codeArray(空のList)<br>
     *
     * <br>
     * 期待値：(戻り値) boolean:true<br>
     *         (状態変化) errors:not null<br>
     *                    (空の要素)<br>
     *
     * <br>
     * 検証対象のプロパティがList型で要素が空の場合、
     * trueが取得できることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testValidateArraysIndex15() throws Exception {
        //テストデータ設定
        // ++++ beanオブジェクト ++++
        String bean = null;

        // ++++ 検証設定オブジェクト
        ValidatorAction va = new ValidatorAction();
        va.setMethodParams(this.validClassStr);
        va.setName("stringLengthArray");

        // ++++ 検証フィールド情報
        Field field = new Field();
        field.setProperty("codeArray");
        Var var = new Var();
        var.setName("stringLength");
        var.setValue("3");
        field.addVar(var);

        Arg arg0 = new Arg();
        arg0.setKey("##INDEX");
        arg0.setPosition(0);
        arg0.setResource(false);
        field.addArg(arg0);
        Arg arg1 = new Arg();
        arg1.setKey("${var:stringLength}");
        arg1.setPosition(1);
        arg1.setResource(false);
        field.addArg(arg1);

        // エラー情報
        ActionMessages errors = new ActionMessages();
        // 擬似HTTPリクエスト
        FieldChecksEx_HttpServletRequestImpl01 request =
            new FieldChecksEx_HttpServletRequestImpl01();
        ActionMappingEx mapping = new ActionMappingEx();
        mapping.setName("logon");
        request.setAttribute(Globals.MAPPING_KEY, mapping);
        form.setCodeArray(new ArrayList());
        request.setAttribute("logon", form);


        // ValidatorResourcesインスタンス
        ValidatorResources validatorResources = new ValidatorResources();
        // Validatorインスタンス
        Validator validator = new Validator(validatorResources);

        // テスト実行
        boolean result =
            FieldChecksEx.validateArraysIndex(
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
     * testValidateArraysIndex16()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) bean:*<br>
     *         (引数) va:methodParams：6つ全て正常<br>
     *                name："hankakuString12345"<br>
     *         (引数) field:property="codeArray"<br>
     *                arg:position="0",key="##INDEX",resource="false"<br>
     *         (引数) errors:not null<br>
     *                (空の要素)<br>
     *         (引数) validator:not null<br>
     *         (引数) request:アクションフォーム名："logon"<br>
     *         (引数) session:キー："logon"に対する値が<br>
     *                ActionForm実装オブジェクト<br>
     *         (引数) ActionForm:ArrayList codeArray = {<br>
     *                    "ﾊﾝｶｸ"<br>
     *                }<br>
     *
     * <br>
     * 期待値：(戻り値) boolean:true<br>
     *         (状態変化) errors:not null<br>
     *                    (空の要素)<br>
     *
     * <br>
     * 検証ルール名が存在するルール名（インデックスチェック以外）
     * +5文字のルールのとき、検証が実行されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testValidateArraysIndex16() throws Exception {
        //テストデータ設定
        // ++++ beanオブジェクト ++++
        String bean = null;

        // ++++ 検証設定オブジェクト
        ValidatorAction va = new ValidatorAction();
        va.setMethodParams(this.validClassStr);
        va.setName("hankakuString12345");

        // ++++ 検証フィールド情報
        Field field = new Field();
        field.setProperty("codeArray");

        Arg arg0 = new Arg();
        arg0.setKey("##INDEX");
        arg0.setPosition(0);
        arg0.setResource(false);
        field.addArg(arg0);

        // エラー情報
        ActionMessages errors = new ActionMessages();
        // 擬似HTTPリクエスト
        FieldChecksEx_HttpServletRequestImpl01 request =
            new FieldChecksEx_HttpServletRequestImpl01();
        ActionMappingEx mapping = new ActionMappingEx();
        mapping.setName("logon");
        request.setAttribute(Globals.MAPPING_KEY, mapping);
        MockHttpSession session = new MockHttpSession();
        ArrayList<String> testList = new ArrayList<String>();
        testList.add("ﾊﾝｶｸ");
        form.setCodeArray(testList);
        session.setAttribute("logon", form);
        request.setSession(session);


        // ValidatorResourcesインスタンス
        ValidatorResources validatorResources = new ValidatorResources();
        // Validatorインスタンス
        Validator validator = new Validator(validatorResources);

        // テスト実行
        boolean result =
            FieldChecksEx.validateArraysIndex(
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
     * testValidateArraysIndex17()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) bean:*<br>
     *         (引数) va:methodParams：6つ全て正常<br>
     *                name："hankakuStringArray"<br>
     *         (引数) field:property="codeArray"<br>
     *                arg:position="0",key="##INDEX",resource="false"<br>
     *         (引数) errors:not null<br>
     *                (空の要素)<br>
     *         (引数) validator:not null<br>
     *         (引数) request:アクションフォーム名："logon"<br>
     *         (引数) session:キー："logon"に対する値が<br>
     *                ActionForm実装オブジェクト<br>
     *         (引数) ActionForm:Arraylist codeArray = {<br>
     *                    "abc",<br>
     *                    ":ﾊﾝｶｸ",<br>
     *                    "123",<br>
     *                    "*!"",<br>
     *                    ""<br>
     *                }<br>
     *
     * <br>
     * 期待値：(戻り値) boolean:true<br>
     *         (状態変化) errors:not null<br>
     *                    (空の要素)<br>
     *
     * <br>
     * 検証対象のプロパティが複数要素のあるListで検証エラーになる要素が
     * 存在しない場合、trueが返却されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testValidateArraysIndex17() throws Exception {
        //テストデータ設定
        // ++++ beanオブジェクト ++++
        String bean = null;

        // ++++ 検証設定オブジェクト
        ValidatorAction va = new ValidatorAction();
        va.setMethodParams(this.validClassStr);
        va.setName("hankakuStringArray");

        // ++++ 検証フィールド情報
        Field field = new Field();
        field.setProperty("codeArray");

        Arg arg0 = new Arg();
        arg0.setKey("##INDEX");
        arg0.setPosition(0);
        arg0.setResource(false);
        field.addArg(arg0);

        // エラー情報
        ActionMessages errors = new ActionMessages();
        // 擬似HTTPリクエスト
        FieldChecksEx_HttpServletRequestImpl01 request =
            new FieldChecksEx_HttpServletRequestImpl01();
        ActionMappingEx mapping = new ActionMappingEx();
        mapping.setName("logon");
        request.setAttribute(Globals.MAPPING_KEY, mapping);
        MockHttpSession session = new MockHttpSession();
        ArrayList<String> testList = new ArrayList<String>();
        testList.add("abc");
        testList.add(":ﾊﾝｶｸ");
        testList.add("123");
        testList.add("*!\"");
        testList.add("");
        form.setCodeArray(testList);
        session.setAttribute("logon", form);
        request.setSession(session);


        // ValidatorResourcesインスタンス
        ValidatorResources validatorResources = new ValidatorResources();
        // Validatorインスタンス
        Validator validator = new Validator(validatorResources);

        // テスト実行
        boolean result =
            FieldChecksEx.validateArraysIndex(
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
     * testValidateArraysIndex18()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) bean:*<br>
     *         (引数) va:methodParams：6つ全て正常<br>
     *                name："stringLengthArray"<br>
     *         (引数) field:property="stringArray"<br>
     *                var:stringLength="3"<br>
     *                arg:position="0",key="##INDEX",resource="false"<br>
     *                arg:position="1",key="${var:stringLength}",resource="false"<br>
     *         (引数) errors:not null<br>
     *                (空の要素)<br>
     *         (引数) validator:not null<br>
     *         (引数) request:アクションフォーム名："logon"<br>
     *         (引数) session:キー："logon"に対する値が<br>
     *                ActionForm実装オブジェクト<br>
     *         (引数) ActionForm:String[] stringArray<br>
     *                (空の要素)<br>
     *
     * <br>
     * 期待値：(戻り値) boolean:true<br>
     *         (状態変化) errors:not null<br>
     *                    (空の要素)<br>
     *
     * <br>
     * 検証対象のプロパティが配列型で要素が空の場合、
     * trueが取得できることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testValidateArraysIndex18() throws Exception {
        //テストデータ設定
        // ++++ beanオブジェクト ++++
        String bean = null;

        // ++++ 検証設定オブジェクト
        ValidatorAction va = new ValidatorAction();
        va.setMethodParams(this.validClassStr);
        va.setName("stringLengthArray");

        // ++++ 検証フィールド情報
        Field field = new Field();
        field.setProperty("stringArray");
        Var var = new Var();
        var.setName("stringLength");
        var.setValue("3");
        field.addVar(var);

        Arg arg0 = new Arg();
        arg0.setKey("##INDEX");
        arg0.setPosition(0);
        arg0.setResource(false);
        field.addArg(arg0);
        Arg arg1 = new Arg();
        arg1.setKey("${var:stringLength}");
        arg1.setPosition(1);
        arg1.setResource(false);
        field.addArg(arg1);

        // エラー情報
        ActionMessages errors = new ActionMessages();
        // 擬似HTTPリクエスト
        FieldChecksEx_HttpServletRequestImpl01 request =
            new FieldChecksEx_HttpServletRequestImpl01();
        ActionMappingEx mapping = new ActionMappingEx();
        mapping.setName("logon");
        request.setAttribute(Globals.MAPPING_KEY, mapping);
        MockHttpSession session = new MockHttpSession();
        form.setStringArray(new String[]{});
        session.setAttribute("logon", form);
        request.setSession(session);


        // ValidatorResourcesインスタンス
        ValidatorResources validatorResources = new ValidatorResources();
        // Validatorインスタンス
        Validator validator = new Validator(validatorResources);

        // テスト実行
        boolean result =
            FieldChecksEx.validateArraysIndex(
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
     * testValidateArraysIndex19()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) bean:*<br>
     *         (引数) va:methodParams：6つ全て正常<br>
     *                name："requiredArray"<br>
     *         (引数) field:property="stringArray"<br>
     *         (引数) errors:not null<br>
     *                ActionMessage(<br>
     *                  "testMessage")<br>
     *         (引数) validator:not null<br>
     *         (引数) request:アクションフォーム名："logon"<br>
     *         (引数) session:キー："logon"に対する値が<br>
     *                ActionForm実装オブジェクト<br>
     *         (引数) ActionForm:String[] stringArray = {<br>
     *                    null<br>
     *                }<br>
     *
     * <br>
     * 期待値：(戻り値) boolean:false<br>
     *         (状態変化) errors:ActionMessage(<br>
     *                      "testMessage")<br>
     *                    ActionMessage(<br>
     *                      "message")<br>
     *
     * <br>
     * 検証対象のプロパティが要素1件の配列型で、検証エラーが発生する場合、
     * エラーメッセージを追加し、falseが返却されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testValidateArraysIndex19() throws Exception {
        //テストデータ設定
        // ++++ beanオブジェクト ++++
        String bean = null;

        // ++++ 検証設定オブジェクト
        ValidatorAction va = new ValidatorAction();
        va.setMethodParams(this.validClassStr);
        va.setName("requiredArray");

        // ++++ 検証フィールド情報
        Field field = new Field();
        field.setProperty("stringArray");

        Msg msg = new Msg();
        msg.setKey("message");
        msg.setName("requiredArray");
        msg.setResource(false);
        field.addMsg(msg);

        // エラー情報
        ActionMessages errors = new ActionMessages();
        ActionMessage error = new ActionMessage("testMessage");
        errors.add(Globals.ERROR_KEY, error);
        // 擬似HTTPリクエスト
        FieldChecksEx_HttpServletRequestImpl01 request =
            new FieldChecksEx_HttpServletRequestImpl01();
        ActionMappingEx mapping = new ActionMappingEx();
        mapping.setName("logon");
        request.setAttribute(Globals.MAPPING_KEY, mapping);
        MockHttpSession session = new MockHttpSession();
        String[] testArray = {
            null
        };
        form.setStringArray(testArray);
        session.setAttribute("logon", form);
        request.setSession(session);


        // ValidatorResourcesインスタンス
        ValidatorResources validatorResources = new ValidatorResources();
        // Validatorインスタンス
        Validator validator = new Validator(validatorResources);

        // テスト実行
        boolean result =
            FieldChecksEx.validateArraysIndex(
                    bean,
                    va,
                    field,
                    errors,
                    validator,
                    request);
        // テスト結果確認
        // trueが返却されていること。
        assertFalse(result);
        // エラー情報が空であること。
        assertEquals(2, errors.size());

        Iterator it = errors.get();
        List<String> nameList = new ArrayList<String>();
        while (it.hasNext()) {
            ActionMessage message = (ActionMessage) it.next();
            nameList.add(message.getKey());
        }
        assertTrue(nameList.contains("testMessage"));
        assertTrue(nameList.contains("message"));
    }

    /**
     * testValidateArraysIndex20()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) bean:*<br>
     *         (引数) va:methodParams：6つ全て正常<br>
     *                name："requiredArray"<br>
     *         (引数) field:property="stringArray"<br>
     *                arg:position="0",key="##INDEX",resource="false"<br>
     *                arg:position="1",key="##INDEX",resource="false"<br>
     *                arg:position="3",key="##INDEX",resource="false"<br>
     *         (引数) errors:not null<br>
     *                (空の要素)<br>
     *         (引数) validator:not null<br>
     *         (引数) request:アクションフォーム名："logon"<br>
     *         (引数) session:キー："logon"に対する値が<br>
     *                ActionForm実装オブジェクト<br>
     *         (引数) ActionForm:検証対象：String[1]<br>
     *                検証結果：<br>
     *                [0]:null(false)<br>
     *
     * <br>
     * 期待値：(戻り値) boolean:false<br>
     *         (状態変化) errors:ActionMessage("message"<br>
     *                      args{<br>
     *                        "1", "1", "1"<br>
     *                      })<br>
     *
     * <br>
     * arg要素に##INDEX属性が複数あるときに、正常にチェックが実行されることを
     * 確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testValidateArraysIndex20() throws Exception {
        //テストデータ設定
        // ++++ beanオブジェクト ++++
        String bean = null;

        // ++++ 検証設定オブジェクト
        ValidatorAction va = new ValidatorAction();
        va.setMethodParams(this.validClassStr);
        va.setName("requiredArray");

        // ++++ 検証フィールド情報
        Field field = new Field();
        field.setProperty("stringArray");

        Arg arg0 = new Arg();
        arg0.setKey("##INDEX");
        arg0.setPosition(0);
        arg0.setResource(false);
        field.addArg(arg0);

        Arg arg1 = new Arg();
        arg1.setKey("##INDEX");
        arg1.setPosition(1);
        arg1.setResource(false);
        field.addArg(arg1);

        Arg arg2 = new Arg();
        arg2.setKey("##INDEX");
        arg2.setPosition(3);
        arg2.setResource(false);
        field.addArg(arg2);

        Msg msg = new Msg();
        msg.setKey("message");
        msg.setName("requiredArray");
        field.addMsg(msg);

        // エラー情報
        ActionMessages errors = new ActionMessages();
        // 擬似HTTPリクエスト
        FieldChecksEx_HttpServletRequestImpl01 request =
            new FieldChecksEx_HttpServletRequestImpl01();
        ActionMappingEx mapping = new ActionMappingEx();
        mapping.setName("logon");
        request.setAttribute(Globals.MAPPING_KEY, mapping);
        MockHttpSession session = new MockHttpSession();
        String[] testArray = {
            null
        };
        form.setStringArray(testArray);
        session.setAttribute("logon", form);
        request.setSession(session);

        FieldChecksEx_MessageResourcesImpl01 messageResources =
            new FieldChecksEx_MessageResourcesImpl01(null ,null);
        messageResources.message = "{0},{1},{2},{3}";
        request.setAttribute(Globals.MESSAGES_KEY, messageResources);

        // ValidatorResourcesインスタンス
        ValidatorResources validatorResources = new ValidatorResources();
        // Validatorインスタンス
        Validator validator = new Validator(validatorResources);

        // テスト実行
        boolean result =
            FieldChecksEx.validateArraysIndex(
                    bean,
                    va,
                    field,
                    errors,
                    validator,
                    request);
        // テスト結果確認
        // trueが返却されていること。
        assertFalse(result);
        // エラー情報が1件であること。
        assertEquals(1, errors.size());

        Iterator it = errors.get();
        ActionMessage message = (ActionMessage) it.next();
        assertEquals("message", message.getKey());
        Object values[] = message.getValues();
        assertEquals("1", values[0]);
        assertEquals("1", values[1]);
        assertNull(values[2]);
        assertEquals("1", values[3]);
    }

    /**
     * testValidateArraysIndex21()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) bean:*<br>
     *         (引数) va:methodParams：6つ全て正常<br>
     *                name："numberArray"<br>
     *         (引数) field:property="stringArray"<br>
     *                var:integerLength="2"<br>
     *                var:scale="1"<br>
     *                var:isAccordedScale="true"<br>
     *                arg:position="0",key="${var:integerLength}",<br>
     *                  resource="false"<br>
     *                arg:position="1",key="${var:scale}",<br>
     *                  resource="false"<br>
     *                arg:position="2",key="${var:isAccordedInteger}",<br>
     *                  resource="false"<br>
     *                arg:position="3",key="##INDEX",<br>
     *                  resource="false"<br>
     *                msg:key="numberArray",name="numberArray"<br>
     *         (引数) errors:not null<br>
     *                (空の要素)<br>
     *         (引数) validator:not null<br>
     *         (引数) request:アクションフォーム名："logon"<br>
     *         (引数) session:キー："logon"に対する値が<br>
     *                ActionForm実装オブジェクト<br>
     *         (引数) ActionForm:検証対象：String[5]<br>
     *                検証結果：<br>
     *                [0]:10.1(true)<br>
     *                [1]:1.11(false)<br>
     *                [2]:100.1(false)<br>
     *                [3]:100(false)<br>
     *                [4]:10.0(true)<br>
     *
     * <br>
     * 期待値：(戻り値) boolean:false<br>
     *         (状態変化) errors:ActionMessage(<br>
     *                      "numberArray",<br>
     *                      arg{"2","1","true","2"})<br>
     *                    ActionMessage(<br>
     *                      "numberArray",<br>
     *                      arg{"2","1","true","3"})<br>
     *                    ActionMessage(<br>
     *                      "numberArray",<br>
     *                      arg{"2","1","true","4"})<br>
     *
     * <br>
     * 検証対象のプロパティが複数要素の配列で、複数のエラーが発生する場合、
     * 置換文字列の##INDEXの値がエラーが発生したインデックスに置換されることを
     * 確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    @SuppressWarnings("deprecation")
    public void testValidateArraysIndex21() throws Exception {
        //テストデータ設定
        // ++++ beanオブジェクト ++++
        String bean = null;

        // ++++ 検証設定オブジェクト
        ValidatorAction va = new ValidatorAction();
        va.setMethodParams(this.validClassStr);
        va.setName("numberArray");

        // ++++ 検証フィールド情報
        Field field = new Field();
        field.setProperty("stringArray");

        Var var = new Var();
        var.setName("integerLength");
        var.setValue("2");
        field.addVar(var);

        var = new Var();
        var.setName("scale");
        var.setValue("1");
        field.addVar(var);

        var = new Var();
        var.setName("isAccordedScale");
        var.setValue("true");
        field.addVar(var);

        Arg arg0 = new Arg();
        arg0.setKey("${var:integerLength}");
        arg0.setPosition(0);
        arg0.setResource(false);
        field.addArg(arg0);

        Arg arg1 = new Arg();
        arg1.setKey("${var:scale}");
        arg1.setPosition(1);
        arg1.setResource(false);
        field.addArg(arg1);

        Arg arg2 = new Arg();
        arg2.setKey("${var:isAccordedScale}");
        arg2.setPosition(2);
        arg2.setResource(false);
        field.addArg(arg2);

        Arg arg3 = new Arg();
        arg3.setKey("##INDEX");
        arg3.setPosition(3);
        arg3.setResource(false);
        field.addArg(arg3);

        Msg msg = new Msg();
        msg.setKey("numberArray");
        msg.setName("numberArray");
        field.addMsg(msg);
        UTUtil.invokePrivate(field, "process", Map.class, Map.class, 
        		new HashMap(), new HashMap());

        // エラー情報
        ActionMessages errors = new ActionMessages();
        // 擬似HTTPリクエスト
        FieldChecksEx_HttpServletRequestImpl01 request =
            new FieldChecksEx_HttpServletRequestImpl01();
        ActionMappingEx mapping = new ActionMappingEx();
        mapping.setName("logon");
        request.setAttribute(Globals.MAPPING_KEY, mapping);
        MockHttpSession session = new MockHttpSession();
        String[] testArray = {
            "10.1",
            "1.11",
            "100.1",
            "100",
            "10.0"
        };
        form.setStringArray(testArray);
        session.setAttribute("logon", form);
        request.setSession(session);

        FieldChecksEx_MessageResourcesImpl01 messageResources =
            new FieldChecksEx_MessageResourcesImpl01(null ,null);
        messageResources.message = "{0},{1},{2},{3}";
        request.setAttribute(Globals.MESSAGES_KEY, messageResources);

        // ValidatorResourcesインスタンス
        ValidatorResources validatorResources = new ValidatorResources();
        // Validatorインスタンス
        Validator validator = new Validator(validatorResources);

        // テスト実行
        boolean result =
            FieldChecksEx.validateArraysIndex(
                    bean,
                    va,
                    field,
                    errors,
                    validator,
                    request);
        // テスト結果確認
        // trueが返却されていること。
        assertFalse(result);
        // エラー情報が1件であること。
        assertEquals(3, errors.size());

        Iterator it = errors.get();
        int counter = 2;
        while (it.hasNext()) {
            ActionMessage message = (ActionMessage) it.next();
            assertEquals("numberArray", message.getKey());
            assertEquals("2", message.getValues()[0]);
            assertEquals("1", message.getValues()[1]);
            assertEquals("true", message.getValues()[2]);
            assertEquals(String.valueOf(counter++), message.getValues()[3]);
        }
    }

    /**
     * testValidateArraysIndex22()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) bean:*<br>
     *         (引数) va:methodParams：6つ全て正常<br>
     *                name："stringLengthArray"<br>
     *         (引数) field:property："codeArray"<br>
     *         (引数) errors:not null<br>
     *                (空の要素)<br>
     *         (引数) validator:not null<br>
     *         (引数) request:アクションフォーム名"logon"<br>
     *         (引数) session:キー："logon"に対する値が<br>
     *                ActionForm実装オブジェクト<br>
     *         (引数) ActionForm:ArrayList codeArray=null<br>
     *
     * <br>
     * 期待値：(戻り値) boolean:true<br>
     *         (状態変化) errors:not null<br>
     *                    (空の要素)<br>
     *
     * <br>
     * 検証対象のプロパティの値がnullの場合、trueが取得できることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testValidateArraysIndex22() throws Exception {
        //テストデータ設定
        // ++++ beanオブジェクト ++++
        String bean = null;

        // ++++ 検証設定オブジェクト
        ValidatorAction va = new ValidatorAction();
        va.setMethodParams(this.validClassStr);
        va.setName("stringLengthArray");

        // ++++ 検証フィールド情報
        Field field = new Field();
        field.setProperty("codeArray");

        // エラー情報
        ActionMessages errors = new ActionMessages();
        // 擬似HTTPリクエスト
        FieldChecksEx_HttpServletRequestImpl01 request =
            new FieldChecksEx_HttpServletRequestImpl01();
        ActionMappingEx mapping = new ActionMappingEx();
        mapping.setName("logon");
        request.setAttribute(Globals.MAPPING_KEY, mapping);
        MockHttpSession session = new MockHttpSession();
        form.setCodeArray(null);
        session.setAttribute("logon", form);
        request.setSession(session);


        // ValidatorResourcesインスタンス
        ValidatorResources validatorResources = new ValidatorResources();
        // Validatorインスタンス
        Validator validator = new Validator(validatorResources);

        // テスト実行
        boolean result =
            FieldChecksEx.validateArraysIndex(
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
     * testValidateArraysIndex23()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) bean:*<br>
     *         (引数) va:methodParams：null<br>
     *         (引数) field:*<br>
     *         (引数) errors:not null<br>
     *                (空の要素)<br>
     *         (引数) validator:not null<br>
     *         (引数) request:not null<br>
     *         (引数) session:not null<br>
     *         (引数) ActionForm:not null<br>
     *
     * <br>
     * 期待値：(戻り値) boolean:true<br>
     *         (状態変化) ログ:ログレベル：エラー<br>
     *                    Can not get class pattern.<br>
     *         (状態変化) errors:not null<br>
     *                    (空の要素)<br>
     *
     * <br>
     * vaのmethodParamsが空のnullの場合、trueが取得できることを確認する。<br>
     * ※getParamClassのテストを包含する
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testValidateArraysIndex23() throws Exception {
        //テストデータ設定
        // ++++ beanオブジェクト ++++
        String bean = null;

        // ++++ 検証設定オブジェクト
        ValidatorAction va = new ValidatorAction();
        UTUtil.setPrivateField(va, "methodParams", null);

        // ++++ 検証フィールド情報
        Field field = new Field();

        // エラー情報
        ActionMessages errors = new ActionMessages();
        // 擬似HTTPリクエスト
        HttpServletRequest request = new MockHttpServletRequest();

        // ValidatorResourcesインスタンス
        ValidatorResources validatorResources = new ValidatorResources();
        // Validatorインスタンス
        Validator validator = new Validator(validatorResources);

        // テスト実行
        boolean result =
            FieldChecksEx.validateArraysIndex(
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
        assertTrue(LogUTUtil.checkError("Can not get class pattern."));
    }

    /**
     * testValidateArraysIndex24()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) bean:*<br>
     *         (引数) va:6つ全て正常<br>
     *                name："numberArray"<br>
     *         (引数) field:property：noIndexedListProperty<br>
     *         (引数) errors:not null<br>
     *                (空の要素)<br>
     *         (引数) validator:not null<br>
     *         (引数) request:not null<br>
     *         (引数) session:not null<br>
     *         (引数) ActionForm:noIndexedListPropertyフィールドが<br>
     *                存在しない<br>
     *
     * <br>
     * 期待値：(戻り値) boolean:true<br>
     *         (状態変化) errors:not null<br>
     *                    (空の要素)<br>
     *
     * <br>
     * 検証対象のフィールドが存在しない場合、trueが取得できることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testValidateArraysIndex24() throws Exception {
        //テストデータ設定
        // ++++ beanオブジェクト ++++
        String bean = null;

        // ++++ 検証設定オブジェクト
        ValidatorAction va = new ValidatorAction();
        va.setMethodParams(this.validClassStr);
        va.setName("numberArray");

        // ++++ 検証フィールド情報
        Field field = new Field();
        field.setProperty("noIndexedListProperty");

        // エラー情報
        ActionMessages errors = new ActionMessages();
        // 擬似HTTPリクエスト
        FieldChecksEx_HttpServletRequestImpl01 request =
            new FieldChecksEx_HttpServletRequestImpl01();
        ActionMappingEx mapping = new ActionMappingEx();
        mapping.setName("logon");
        request.setAttribute(Globals.MAPPING_KEY, mapping);
        MockHttpSession session = new MockHttpSession();
        String[] testArray = {
            null
        };
        form.setStringArray(testArray);
        session.setAttribute("logon", form);
        request.setSession(session);


        // ValidatorResourcesインスタンス
        ValidatorResources validatorResources = new ValidatorResources();
        // Validatorインスタンス
        Validator validator = new Validator(validatorResources);

        // テスト実行
        boolean result =
            FieldChecksEx.validateArraysIndex(
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
     * testValidateArraysIndex25()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) bean:*<br>
     *         (引数) va:methodParams：6つ全て正常<br>
     *                name："minLengthArray"<br>
     *         (引数) field:property="row.values"<br>
     *                var:minlength="3"<br>
     *                arg:position="0",key="##INDEX",<br>
     *                  resource="false"<br>
     *                arg:position="1",key="${var:minlength}",<br>
     *                  resource="false"<br>
     *                msg:key="minLengthArray",name="minLengthArray"<br>
     *         (引数) errors:not null<br>
     *                (空の要素)<br>
     *         (引数) validator:not null<br>
     *         (引数) request:アクションフォーム名："logon"<br>
     *         (引数) session:キー："logon"に対する値が<br>
     *                ActionForm実装オブジェクト<br>
     *         (引数) ActionForm:JavaBean row<br>
     *                 + String[] values = {<br>
     *                     "a", "bbbb","cc"<br>
     *                }<br>
     *         (引数) メッセージリソース:message={0},{1}<br>
     *
     * <br>
     * 期待値：(戻り値) boolean:false<br>
     *         (状態変化) errors:ActionMessage(<br>
     *                      "minLengthArray",<br>
     *                      arg{"2","3"})<br>
     *
     * <br>
     * 検証対象の値がアクションフォームのネストしたプロパティの場合、正常に検証が行われることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    @SuppressWarnings("deprecation")
    public void testValidateArraysIndex25() throws Exception {
        //テストデータ設定
        // ++++ beanオブジェクト ++++
        String bean = null;

        // ++++ 検証設定オブジェクト
        ValidatorAction va = new ValidatorAction();
        va.setMethodParams(this.validClassStr);
        va.setName("minLengthArray");

        // ++++ 検証フィールド情報
        Field field = new Field();
        field.setProperty("row.values");
        Var var = new Var();
        var.setName("minlength");
        var.setValue("3");
        field.addVar(var);
        Arg arg0 = new Arg();
        arg0.setPosition(0);
        arg0.setKey("##INDEX");
        arg0.setResource(false);
        //arg0.setName("minLengthArray");
        field.addArg(arg0);
        Arg arg1 = new Arg();
        arg1.setPosition(1);
        arg1.setKey("${var:minlength}");
        arg1.setResource(false);
        //arg1.setName("minLength");
        field.addArg(arg1);
        Msg msg = new Msg();
        msg.setKey("minLengthArray");
        msg.setName("minLengthArray");
        field.addMsg(msg);
        UTUtil.invokePrivate(field, "process", Map.class, Map.class, 
        		new HashMap(), new HashMap());

        // エラー情報
        ActionMessages errors = new ActionMessages();
        // 擬似HTTPリクエスト
        FieldChecksEx_HttpServletRequestImpl01 request =
            new FieldChecksEx_HttpServletRequestImpl01();
        ActionMappingEx mapping = new ActionMappingEx();
        mapping.setName("logon");
        request.setAttribute(Globals.MAPPING_KEY, mapping);
        MockHttpSession session = new MockHttpSession();
        FieldChecksEx_JavaBeanStub01 row = new FieldChecksEx_JavaBeanStub01();
        String[] values = {
            "a", "bbbb", "cc"
        };
        row.values = values;
        form.setRow(row);
        session.setAttribute("logon", form);
        request.setSession(session);

        FieldChecksEx_MessageResourcesImpl01 messageResources =
            new FieldChecksEx_MessageResourcesImpl01(null ,null);
        messageResources.message = "{0},{1}";
        request.setAttribute(Globals.MESSAGES_KEY, messageResources);

        // ValidatorResourcesインスタンス
        ValidatorResources validatorResources = new ValidatorResources();
        // Validatorインスタンス
        Validator validator = new Validator(validatorResources);

        // テスト実行
        boolean result =
            FieldChecksEx.validateArraysIndex(
                    bean,
                    va,
                    field,
                    errors,
                    validator,
                    request);
        // テスト結果確認
        // falseが返却されていること。
        assertFalse(result);
        // エラー情報が1件追加されていること。
        assertEquals(2, errors.size());
        Iterator it = errors.get();
        ActionMessage message = (ActionMessage) it.next();
        assertEquals("minLengthArray", message.getKey());
        assertEquals("1", message.getValues()[0]);
        assertEquals("3", message.getValues()[1]);
        message = (ActionMessage) it.next();
        assertEquals("minLengthArray", message.getKey());
        assertEquals("3", message.getValues()[0]);
        assertEquals("3", message.getValues()[1]);


    }

    /**
     * testValidateArraysIndex26()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) bean:*<br>
     *         (引数) va:methodParams：6つ全て正常<br>
     *                name："alphaNumericStringArray"<br>
     *         (引数) field:property="rows.value"<br>
     *                arg:position="0",key="##INDEX",<br>
     *                  resource="false"<br>
     *                msg:key="alphaNumericStringArray",name="alphaNumericStringArray"<br>
     *         (引数) errors:not null<br>
     *                (空の要素)<br>
     *         (引数) validator:not null<br>
     *         (引数) request:アクションフォーム名："logon"<br>
     *         (引数) session:キー："logon"に対する値が<br>
     *                ActionForm実装オブジェクト<br>
     *         (引数) ActionForm:<br>
     *                JavaBean[] rows<br>
     *                  +rows[0]<br>
     *                     + String value = "てすと"<br>
     *                  +rows[1]<br>
     *                     + String value = "test"<br>
     *                  +rows[2]<br>
     *                     + String value = "てすと"<br>
     *         (引数) メッセージリソース:message={0}<br>
     *
     * <br>
     * 期待値：(戻り値) boolean:false<br>
     *         (状態変化) errors:ActionMessage(<br>
     *                           "alphaNumericStringArray",<br>
     *                           arg{"2"})<br>
     *
     * <br>
     * 検証対象の値がアクションフォームのネストしたプロパティの場合、正常に検証が行われることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    @SuppressWarnings("deprecation")
    public void testValidateArraysIndex26() throws Exception {
        //テストデータ設定
        // ++++ beanオブジェクト ++++
        String bean = null;

        // ++++ 検証設定オブジェクト
        ValidatorAction va = new ValidatorAction();
        va.setMethodParams(this.validClassStr);
        va.setName("alphaNumericStringArray");

        // ++++ 検証フィールド情報
        Field field = new Field();
        field.setProperty("rows.value");
        Var var = new Var();
        field.addVar(var);
        Arg arg0 = new Arg();
        arg0.setPosition(0);
        arg0.setKey("##INDEX");
        arg0.setResource(false);
        field.addArg(arg0);
        Msg msg = new Msg();
        msg.setKey("alphaNumericStringArray");
        msg.setName("alphaNumericStringArray");
        field.addMsg(msg);
        UTUtil.invokePrivate(field, "process", Map.class, Map.class, 
        		new HashMap(), new HashMap());

        // エラー情報
        ActionMessages errors = new ActionMessages();
        // 擬似HTTPリクエスト
        FieldChecksEx_HttpServletRequestImpl01 request =
            new FieldChecksEx_HttpServletRequestImpl01();
        ActionMappingEx mapping = new ActionMappingEx();
        mapping.setName("logon");
        request.setAttribute(Globals.MAPPING_KEY, mapping);
        MockHttpSession session = new MockHttpSession();
        FieldChecksEx_JavaBeanStub01[] rows = {
            new FieldChecksEx_JavaBeanStub01(),
            new FieldChecksEx_JavaBeanStub01(),
            new FieldChecksEx_JavaBeanStub01()
        };
        rows[0].value = "てすと";
        rows[1].value = "test";
        rows[2].value = "てすと";
        form.rows = rows;

        session.setAttribute("logon", form);
        request.setSession(session);

        FieldChecksEx_MessageResourcesImpl01 messageResources =
            new FieldChecksEx_MessageResourcesImpl01(null ,null);
        messageResources.message = "{0}";
        request.setAttribute(Globals.MESSAGES_KEY, messageResources);

        // ValidatorResourcesインスタンス
        ValidatorResources validatorResources = new ValidatorResources();
        // Validatorインスタンス
        Validator validator = new Validator(validatorResources);

        // テスト実行
        boolean result =
            FieldChecksEx.validateArraysIndex(
                    bean,
                    va,
                    field,
                    errors,
                    validator,
                    request);
        // テスト結果確認
        // falseが返却されていること。
        assertFalse(result);
        // エラー情報が2件追加されていること。
        assertEquals(2, errors.size());
        Iterator it = errors.get();
        ActionMessage message = (ActionMessage) it.next();
        assertEquals("alphaNumericStringArray", message.getKey());
        assertEquals("1", message.getValues()[0]);
        message = (ActionMessage) it.next();
        assertEquals("alphaNumericStringArray", message.getKey());
        assertEquals("3", message.getValues()[0]);


    }

    /**
     * testValidateArraysIndex27()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) bean:*<br>
     *         (引数) va:methodParams：6つ全て正常<br>
     *                name："requiredArray"<br>
     *         (引数) field:property="rowList.map(key)"<br>
     *                arg:position="0",key="##INDEX",<br>
     *                  resource="false"<br>
     *                msg:key="requiredArray",name="requiredArray"<br>
     *         (引数) errors:not null<br>
     *                (空の要素)<br>
     *         (引数) validator:not null<br>
     *         (引数) request:アクションフォーム名："logon"<br>
     *         (引数) session:キー："logon"に対する値が<br>
     *                ActionForm実装オブジェクト<br>
     *         (引数) ActionForm:ArrayList<JavaBean> rowList<br>
     *                 + rowList[0]<br>
     *                     + Map map = { key = null }<br>
     *                 + rowList[1]<br>
     *                     + Map map = { key = null }<br>
     *                 + rowList[2]<br>
     *                     + Map map = { key = null }<br>
     *         (引数) メッセージリソース:message={0}<br>
     *
     * <br>
     * 期待値：(戻り値) boolean:false<br>
     *         (状態変化) errors:ActionMessage(<br>
     *                      "minLengthArray",<br>
     *                      arg{"1"})<br>
     *                    ActionMessage(<br>
     *                      "minLengthArray",<br>
     *                      arg{"2"})<br>
     *                    ActionMessage(<br>
     *                      "minLengthArray",<br>
     *                      arg{"3"})<br>
     *
     * <br>
     * 検証対象の値がアクションフォームのネストしたプロパティの場合、正常に検証が行われることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    @SuppressWarnings({"deprecation","unchecked"})
    public void testValidateArraysIndex27() throws Exception {
        //テストデータ設定
        // ++++ beanオブジェクト ++++
        String bean = null;

        // ++++ 検証設定オブジェクト
        ValidatorAction va = new ValidatorAction();
        va.setMethodParams(this.validClassStr);
        va.setName("requiredArray");

        // ++++ 検証フィールド情報
        Field field = new Field();
        field.setProperty("rowList.map(key)");
        Var var = new Var();
        field.addVar(var);
        Arg arg0 = new Arg();
        arg0.setPosition(0);
        arg0.setKey("##INDEX");
        arg0.setResource(false);
        field.addArg(arg0);
        Msg msg = new Msg();
        msg.setKey("requiredArray");
        msg.setName("requiredArray");
        field.addMsg(msg);
        UTUtil.invokePrivate(field, "process", Map.class, Map.class, 
        		new HashMap(), new HashMap());

        // エラー情報
        ActionMessages errors = new ActionMessages();
        // 擬似HTTPリクエスト
        FieldChecksEx_HttpServletRequestImpl01 request =
            new FieldChecksEx_HttpServletRequestImpl01();
        ActionMappingEx mapping = new ActionMappingEx();
        mapping.setName("logon");
        request.setAttribute(Globals.MAPPING_KEY, mapping);
        MockHttpSession session = new MockHttpSession();
        List rowList = new ArrayList();
        FieldChecksEx_JavaBeanStub01 row = new FieldChecksEx_JavaBeanStub01();
        Map map = new HashMap();
        map.put("key", null);
        row.map = map;
        rowList.add(row);
        row = new FieldChecksEx_JavaBeanStub01();
        map = new HashMap();
        map.put("key", null);
        row.map = map;
        rowList.add(row);
        row = new FieldChecksEx_JavaBeanStub01();
        map = new HashMap();
        map.put("key", null);
        row.map = map;
        rowList.add(row);
        form.rowList = rowList;

        session.setAttribute("logon", form);
        request.setSession(session);

        FieldChecksEx_MessageResourcesImpl01 messageResources =
            new FieldChecksEx_MessageResourcesImpl01(null ,null);
        messageResources.message = "{0}";
        request.setAttribute(Globals.MESSAGES_KEY, messageResources);

        // ValidatorResourcesインスタンス
        ValidatorResources validatorResources = new ValidatorResources();
        // Validatorインスタンス
        Validator validator = new Validator(validatorResources);

        // テスト実行
        boolean result =
            FieldChecksEx.validateArraysIndex(
                    bean,
                    va,
                    field,
                    errors,
                    validator,
                    request);
        // テスト結果確認
        // falseが返却されていること。
        assertFalse(result);
        // エラー情報が2件追加されていること。
        assertEquals(3, errors.size());
        Iterator it = errors.get();
        ActionMessage message = (ActionMessage) it.next();
        assertEquals("requiredArray", message.getKey());
        assertEquals("1", message.getValues()[0]);
        message = (ActionMessage) it.next();
        assertEquals("requiredArray", message.getKey());
        assertEquals("2", message.getValues()[0]);
        message = (ActionMessage) it.next();
        assertEquals("requiredArray", message.getKey());
        assertEquals("3", message.getValues()[0]);


    }

    /**
     * testValidateArraysIndex28()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) bean:*<br>
     *         (引数) va:methodParams：6つ全て正常<br>
     *                name："minLengthArray"<br>
     *         (引数) field:property="row.values"<br>
     *                var:minlength="3"<br>
     *                arg:position="0",key="##INDEX",<br>
     *                  resource="false"<br>
     *                arg:position="1",key="${var:minlength}",<br>
     *                  resource="false"<br>
     *                msg:key="minLengthArray",name="minLengthArray"<br>
     *         (引数) errors:not null<br>
     *                (空の要素)<br>
     *         (引数) validator:not null<br>
     *         (引数) request:アクションフォーム名："logon"<br>
     *         (引数) session:キー："logon"に対する値が<br>
     *                ActionForm実装オブジェクト<br>
     *         (引数) ActionForm:DynaValidatorActionFormExインスタンス<br>
     *                JavaBean row<br>
     *                 + String[] values = {<br>
     *                     "a", "bbbb","cc"<br>
     *                }<br>
     *         (引数) メッセージリソース:message={0},{1}<br>
     *
     * <br>
     * 期待値：(戻り値) boolean:false<br>
     *         (状態変化) errors:ActionMessage(<br>
     *                      "minLengthArray",<br>
     *                      arg{"2","3"})<br>
     *
     * <br>
     * 検証対象の値がアクションフォームのネストしたプロパティの場合、正常に検証が行われることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    @SuppressWarnings("deprecation")
    public void testValidateArraysIndex28() throws Exception {
        //テストデータ設定
        // ++++ beanオブジェクト ++++
        this.formEx =
            (DynaValidatorActionFormEx) creator.create(CONFIG_FILE_NAME);
        String bean = null;

        // ++++ 検証設定オブジェクト
        ValidatorAction va = new ValidatorAction();
        va.setMethodParams(this.validClassStr);
        va.setName("minLengthArray");

        // ++++ 検証フィールド情報
        Field field = new Field();
        field.setProperty("row.values");
        Var var = new Var();
        var.setName("minlength");
        var.setValue("3");
        field.addVar(var);
        Arg arg0 = new Arg();
        arg0.setPosition(0);
        arg0.setKey("##INDEX");
        arg0.setResource(false);
        //arg0.setName("minLengthArray");
        field.addArg(arg0);
        Arg arg1 = new Arg();
        arg1.setPosition(1);
        arg1.setKey("${var:minlength}");
        arg1.setResource(false);
        //arg1.setName("minLength");
        field.addArg(arg1);
        Msg msg = new Msg();
        msg.setKey("minLengthArray");
        msg.setName("minLengthArray");
        field.addMsg(msg);
        UTUtil.invokePrivate(field, "process", Map.class, Map.class, 
        		new HashMap(), new HashMap());

        // エラー情報
        ActionMessages errors = new ActionMessages();
        // 擬似HTTPリクエスト
        FieldChecksEx_HttpServletRequestImpl01 request =
            new FieldChecksEx_HttpServletRequestImpl01();
        ActionMappingEx mapping = new ActionMappingEx();
        mapping.setName("logon");
        request.setAttribute(Globals.MAPPING_KEY, mapping);
        MockHttpSession session = new MockHttpSession();
        FieldChecksEx_JavaBeanStub01 row = new FieldChecksEx_JavaBeanStub01();
        String[] values = {
            "a", "bbbb", "cc"
        };
        row.values = values;
        formEx.set("row", row);
        session.setAttribute("logon", formEx);
        request.setSession(session);

        FieldChecksEx_MessageResourcesImpl01 messageResources =
            new FieldChecksEx_MessageResourcesImpl01(null ,null);
        messageResources.message = "{0},{1}";
        request.setAttribute(Globals.MESSAGES_KEY, messageResources);

        // ValidatorResourcesインスタンス
        ValidatorResources validatorResources = new ValidatorResources();
        // Validatorインスタンス
        Validator validator = new Validator(validatorResources);

        // テスト実行
        boolean result =
            FieldChecksEx.validateArraysIndex(
                    bean,
                    va,
                    field,
                    errors,
                    validator,
                    request);
        // テスト結果確認
        // falseが返却されていること。
        assertFalse(result);
        // エラー情報が2件追加されていること。
        assertEquals(2, errors.size());
        Iterator it = errors.get();
        ActionMessage message = (ActionMessage) it.next();
        assertEquals("minLengthArray", message.getKey());
        assertEquals("1", message.getValues()[0]);
        assertEquals("3", message.getValues()[1]);
        message = (ActionMessage) it.next();
        assertEquals("minLengthArray", message.getKey());
        assertEquals("3", message.getValues()[0]);
        assertEquals("3", message.getValues()[1]);


    }

    /**
     * testValidateArraysIndex29()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) bean:*<br>
     *         (引数) va:methodParams：6つ全て正常<br>
     *                name："alphaNumericStringArray"<br>
     *         (引数) field:property="rows.value"<br>
     *                arg:position="0",key="##INDEX",<br>
     *                  resource="false"<br>
     *                msg:key="alphaNumericStringArray",name="alphaNumericStringArray"<br>
     *         (引数) errors:not null<br>
     *                (空の要素)<br>
     *         (引数) validator:not null<br>
     *         (引数) request:アクションフォーム名："logon"<br>
     *         (引数) session:キー："logon"に対する値が<br>
     *                ActionForm実装オブジェクト<br>
     *         (引数) ActionForm:DynaValidatorActionFormExインスタンス<br>
     *                JavaBean[] rows<br>
     *                  +rows[0]<br>
     *                     + String value = "てすと"<br>
     *                  +rows[1]<br>
     *                     + String value = "test"<br>
     *                  +rows[2]<br>
     *                     + String value = "てすと"<br>
     *         (引数) メッセージリソース:message={0}<br>
     *
     * <br>
     * 期待値：(戻り値) boolean:false<br>
     *         (状態変化) errors:ActionMessage(<br>
     *                           "alphaNumericStringArray",<br>
     *                           arg{"2"})<br>
     *
     * <br>
     * 検証対象の値がアクションフォームのネストしたプロパティの場合、正常に検証が行われることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    @SuppressWarnings("deprecation")
    public void testValidateArraysIndex29() throws Exception {

        //テストデータ設定
        this.formEx =
            (DynaValidatorActionFormEx) creator.create(CONFIG_FILE_NAME);
        // ++++ beanオブジェクト ++++
        String bean = null;

        // ++++ 検証設定オブジェクト
        ValidatorAction va = new ValidatorAction();
        va.setMethodParams(this.validClassStr);
        va.setName("alphaNumericStringArray");

        // ++++ 検証フィールド情報
        Field field = new Field();
        field.setProperty("rows.value");
        Var var = new Var();
        field.addVar(var);
        Arg arg0 = new Arg();
        arg0.setPosition(0);
        arg0.setKey("##INDEX");
        arg0.setResource(false);
        field.addArg(arg0);
        Msg msg = new Msg();
        msg.setKey("alphaNumericStringArray");
        msg.setName("alphaNumericStringArray");
        field.addMsg(msg);
        UTUtil.invokePrivate(field, "process", Map.class, Map.class, 
        		new HashMap(), new HashMap());

        // エラー情報
        ActionMessages errors = new ActionMessages();
        // 擬似HTTPリクエスト
        FieldChecksEx_HttpServletRequestImpl01 request =
            new FieldChecksEx_HttpServletRequestImpl01();
        ActionMappingEx mapping = new ActionMappingEx();
        mapping.setName("logon");
        request.setAttribute(Globals.MAPPING_KEY, mapping);
        MockHttpSession session = new MockHttpSession();
        FieldChecksEx_JavaBeanStub01[] rows = {
            new FieldChecksEx_JavaBeanStub01(),
            new FieldChecksEx_JavaBeanStub01(),
            new FieldChecksEx_JavaBeanStub01()
        };
        rows[0].value = "てすと";
        rows[1].value = "test";
        rows[2].value = "てすと";
        formEx.set("rows", rows);

        session.setAttribute("logon", formEx);
        request.setSession(session);

        FieldChecksEx_MessageResourcesImpl01 messageResources =
            new FieldChecksEx_MessageResourcesImpl01(null ,null);
        messageResources.message = "{0}";
        request.setAttribute(Globals.MESSAGES_KEY, messageResources);

        // ValidatorResourcesインスタンス
        ValidatorResources validatorResources = new ValidatorResources();
        // Validatorインスタンス
        Validator validator = new Validator(validatorResources);

        // テスト実行
        boolean result =
            FieldChecksEx.validateArraysIndex(
                    bean,
                    va,
                    field,
                    errors,
                    validator,
                    request);
        // テスト結果確認
        // falseが返却されていること。
        assertFalse(result);
        // エラー情報が2件追加されていること。
        assertEquals(2, errors.size());
        Iterator it = errors.get();
        ActionMessage message = (ActionMessage) it.next();
        assertEquals("alphaNumericStringArray", message.getKey());
        assertEquals("1", message.getValues()[0]);
        message = (ActionMessage) it.next();
        assertEquals("alphaNumericStringArray", message.getKey());
        assertEquals("3", message.getValues()[0]);



    }

    /**
     * testValidateArraysIndex30()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) bean:*<br>
     *         (引数) va:methodParams：6つ全て正常<br>
     *                name："requiredArray"<br>
     *         (引数) field:property="rowList.map(key)"<br>
     *                arg:position="0",key="##INDEX",<br>
     *                  resource="false"<br>
     *                msg:key="requiredArray",name="requiredArray"<br>
     *         (引数) errors:not null<br>
     *                (空の要素)<br>
     *         (引数) validator:not null<br>
     *         (引数) request:アクションフォーム名："logon"<br>
     *         (引数) session:キー："logon"に対する値が<br>
     *                ActionForm実装オブジェクト<br>
     *         (引数) ActionForm:DynaValidatorActionFormExインスタンス<br>
     *                ArrayList<JavaBean> rowList<br>
     *                 + rowList[0]<br>
     *                     + Map map = { key = null }<br>
     *                 + rowList[1]<br>
     *                     + Map map = { key = null }<br>
     *                 + rowList[2]<br>
     *                     + Map map = { key = null }<br>
     *         (引数) メッセージリソース:message={0}<br>
     *
     * <br>
     * 期待値：(戻り値) boolean:false<br>
     *         (状態変化) errors:ActionMessage(<br>
     *                      "minLengthArray",<br>
     *                      arg{"1"})<br>
     *                    ActionMessage(<br>
     *                      "minLengthArray",<br>
     *                      arg{"2"})<br>
     *                    ActionMessage(<br>
     *                      "minLengthArray",<br>
     *                      arg{"2"})<br>
     *
     * <br>
     * 検証対象の値がアクションフォームのネストしたプロパティの場合、正常に検証が行われることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    @SuppressWarnings({"deprecation","unchecked"})
    public void testValidateArraysIndex30() throws Exception {
        //テストデータ設定
        this.formEx =
            (DynaValidatorActionFormEx) creator.create(CONFIG_FILE_NAME);
        // ++++ beanオブジェクト ++++
        String bean = null;

        // ++++ 検証設定オブジェクト
        ValidatorAction va = new ValidatorAction();
        va.setMethodParams(this.validClassStr);
        va.setName("requiredArray");

        // ++++ 検証フィールド情報
        Field field = new Field();
        field.setProperty("rowList.map(key)");
        Var var = new Var();
        field.addVar(var);
        Arg arg0 = new Arg();
        arg0.setPosition(0);
        arg0.setKey("##INDEX");
        arg0.setResource(false);
        field.addArg(arg0);
        Msg msg = new Msg();
        msg.setKey("requiredArray");
        msg.setName("requiredArray");
        field.addMsg(msg);
        UTUtil.invokePrivate(field, "process", Map.class, Map.class, 
        		new HashMap(), new HashMap());

        // エラー情報
        ActionMessages errors = new ActionMessages();
        // 擬似HTTPリクエスト
        FieldChecksEx_HttpServletRequestImpl01 request =
            new FieldChecksEx_HttpServletRequestImpl01();
        ActionMappingEx mapping = new ActionMappingEx();
        mapping.setName("logon");
        request.setAttribute(Globals.MAPPING_KEY, mapping);
        MockHttpSession session = new MockHttpSession();
        List rowList = new ArrayList();
        FieldChecksEx_JavaBeanStub01 row = new FieldChecksEx_JavaBeanStub01();
        Map map = new HashMap();
        map.put("key", null);
        row.map = map;
        rowList.add(row);
        row = new FieldChecksEx_JavaBeanStub01();
        map = new HashMap();
        map.put("key", null);
        row.map = map;
        rowList.add(row);
        row = new FieldChecksEx_JavaBeanStub01();
        map = new HashMap();
        map.put("key", null);
        row.map = map;
        rowList.add(row);
        formEx.set("rowList", rowList);

        session.setAttribute("logon", formEx);
        request.setSession(session);

        FieldChecksEx_MessageResourcesImpl01 messageResources =
            new FieldChecksEx_MessageResourcesImpl01(null ,null);
        messageResources.message = "{0}";
        request.setAttribute(Globals.MESSAGES_KEY, messageResources);

        // ValidatorResourcesインスタンス
        ValidatorResources validatorResources = new ValidatorResources();
        // Validatorインスタンス
        Validator validator = new Validator(validatorResources);

        // テスト実行
        boolean result =
            FieldChecksEx.validateArraysIndex(
                    bean,
                    va,
                    field,
                    errors,
                    validator,
                    request);
        // テスト結果確認
        // falseが返却されていること。
        assertFalse(result);
        // エラー情報が2件追加されていること。
        assertEquals(3, errors.size());
        Iterator it = errors.get();
        ActionMessage message = (ActionMessage) it.next();
        assertEquals("requiredArray", message.getKey());
        assertEquals("1", message.getValues()[0]);
        message = (ActionMessage) it.next();
        assertEquals("requiredArray", message.getKey());
        assertEquals("2", message.getValues()[0]);
        message = (ActionMessage) it.next();
        assertEquals("requiredArray", message.getKey());
        assertEquals("3", message.getValues()[0]);


    }

    /**
     * testValidateArraysIndex31()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) bean:*<br>
     *         (引数) va:methodParams：6つ全て正常<br>
     *                name："requiredArray"<br>
     *         (引数) field:property="/"<br>
     *                arg:position="0",key="##INDEX",<br>
     *                  resource="false"<br>
     *                msg:key="requiredArray",name="requiredArray"<br>
     *         (引数) errors:not null<br>
     *                (空の要素)<br>
     *         (引数) validator:not null<br>
     *         (引数) request:アクションフォーム名："logon"<br>
     *         (引数) session:キー："logon"に対する値が<br>
     *                ActionForm実装オブジェクト<br>
     *         (引数) ActionForm:DynaValidatorActionFormExインスタンス<br>
     *                ArrayList<JavaBean> rowList<br>
     *                 + rowList[0]<br>
     *                     + Map map = { key = null }<br>
     *                 + rowList[1]<br>
     *                     + Map map = { key = null }<br>
     *                 + rowList[2]<br>
     *                     + Map map = { key = null }<br>
     *         (引数) メッセージリソース:message={0}<br>
     *
     * <br>
     * 期待値：(戻り値) boolean:true<br>
     *         (状態変化) ログ:ログレベル：エラー
     *                         例外：IllegalArgumentException
     *                               "Invalid character has found within property name. '/' Cannot use [ / \" ' ]"
     *
     * <br>
     * 不正なプロパティ名が指定された場合、エラーログを出力し、trueが取得できることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    @SuppressWarnings({"deprecation","unchecked"})
    public void testValidateArraysIndex31() throws Exception {
        //テストデータ設定
        this.formEx =
            (DynaValidatorActionFormEx) creator.create(CONFIG_FILE_NAME);
        // ++++ beanオブジェクト ++++
        String bean = null;

        // ++++ 検証設定オブジェクト
        ValidatorAction va = new ValidatorAction();
        va.setMethodParams(this.validClassStr);
        va.setName("requiredArray");

        // ++++ 検証フィールド情報
        Field field = new Field();
        field.setProperty("/");
        Var var = new Var();
        field.addVar(var);
        Arg arg0 = new Arg();
        arg0.setPosition(0);
        arg0.setKey("##INDEX");
        arg0.setResource(false);
        field.addArg(arg0);
        Msg msg = new Msg();
        msg.setKey("requiredArray");
        msg.setName("requiredArray");
        field.addMsg(msg);
        UTUtil.invokePrivate(field, "process", Map.class, Map.class, 
        		new HashMap(), new HashMap());

        // エラー情報
        ActionMessages errors = new ActionMessages();
        // 擬似HTTPリクエスト
        FieldChecksEx_HttpServletRequestImpl01 request =
            new FieldChecksEx_HttpServletRequestImpl01();
        ActionMappingEx mapping = new ActionMappingEx();
        mapping.setName("logon");
        request.setAttribute(Globals.MAPPING_KEY, mapping);
        MockHttpSession session = new MockHttpSession();
        List rowList = new ArrayList();
        FieldChecksEx_JavaBeanStub01 row = new FieldChecksEx_JavaBeanStub01();
        Map map = new HashMap();
        map.put("key", null);
        row.map = map;
        rowList.add(row);
        row = new FieldChecksEx_JavaBeanStub01();
        map = new HashMap();
        map.put("key", null);
        row.map = map;
        rowList.add(row);
        row = new FieldChecksEx_JavaBeanStub01();
        map = new HashMap();
        map.put("key", null);
        row.map = map;
        rowList.add(row);
        formEx.set("rowList", rowList);

        session.setAttribute("logon", formEx);
        request.setSession(session);

        FieldChecksEx_MessageResourcesImpl01 messageResources =
            new FieldChecksEx_MessageResourcesImpl01(null ,null);
        messageResources.message = "{0}";
        request.setAttribute(Globals.MESSAGES_KEY, messageResources);

        // ValidatorResourcesインスタンス
        ValidatorResources validatorResources = new ValidatorResources();
        // Validatorインスタンス
        Validator validator = new Validator(validatorResources);

        // テスト実行
        boolean result =
            FieldChecksEx.validateArraysIndex(
                    bean,
                    va,
                    field,
                    errors,
                    validator,
                    request);
        // テスト結果確認
        // trueが返却されていること。
        assertTrue(result);
        // エラーログ
        assertTrue(LogUTUtil.checkError("",
                new IllegalArgumentException(
                        "Invalid character has found " +
                        "within property name. '/' Cannot use [ / \" ' ]")));

    }

    /**
     * testGetHankakuKanaList01()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(状態) hankakuKanaList:ｱｲｳｴｵｧｨｩｪｫｶｷｸｹｺｻｼｽｾｿﾀﾁﾂｯﾃﾄﾅﾆﾇﾈﾉﾊﾋﾌﾍﾎﾏﾐﾑﾒﾓ
     *                                ﾔﾕﾖｬｭｮﾗﾘﾙﾚﾛﾜｦﾝﾟﾞｰ･､｡｢｣<br>
     *         (状態) プロパティファイル:validation.hankaku.kana.list<br>
     *                が存在しないこと。<br>
     *
     * <br>
     * 期待値：(戻り値) String:ｱｲｳｴｵｧｨｩｪｫｶｷｸｹｺｻｼｽｾｿﾀﾁﾂｯﾃﾄﾅﾆﾇﾈﾉﾊﾋﾌﾍﾎﾏﾐﾑﾒﾓﾔﾕﾖｬｭｮ
     *                         ﾗﾘﾙﾚﾛﾜｦﾝﾟﾞｰ･､｡｢｣<br>
     *
     * <br>
     * クラス変数hankakuKanaListの値が取得できることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetHankakuKanaList01() throws Exception {
        assertEquals("ｱｲｳｴｵｧｨｩｪｫｶｷｸｹｺｻｼｽｾｿﾀﾁﾂｯﾃﾄ" +
                "ﾅﾆﾇﾈﾉﾊﾋﾌﾍﾎﾏﾐﾑﾒﾓﾔﾕﾖｬｭｮﾗﾘﾙﾚﾛﾜｦﾝﾟﾞｰ･､｡｢｣",
                FieldChecksEx.getHankakuKanaList());
    }

    /**
     * testGetZenkakuKanaList01()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(状態) zenkakuKanaList:アイウヴエオァィゥェォカキクケコヵヶ
     *                                ガギグゲゴサシスセソザジズゼゾタチツテト
     *                                ダヂヅデドナニヌネノハヒフヘホバビブベボ
     *                                パピプペポマミムメモヤユヨャュョラリルレロ
     *                                ワヮヰヱヲッンー<br>
     *         (状態) プロパティファイル:validation.zenkaku.kana.list<br>
     *                が存在しないこと。<br>
     *
     * <br>
     * 期待値：(戻り値) String:アイウヴエオァィゥェォカキクケコヵヶガギグゲゴ
     *                         サシスセソザジズゼゾタチツテトダヂヅデド
     *                         ナニヌネノハヒフヘホバビブベボパピプペポ
     *                         マミムメモヤユヨャュョラリルレロワヮヰヱ
     *                         ヲッンー<br>
     *
     * <br>
     * クラス変数zenkakuKanaListの値が取得できることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetZenkakuKanaList01() throws Exception {
        assertEquals("アイウヴエオァィゥェォカキクケコヵヶガギグゲゴ" +
                "サシスセソザジズゼゾタチツテトダヂヅデドナニヌネノ" +
                "ハヒフヘホバビブベボパピプペポマミムメモヤユヨ" +
                "ャュョラリルレロワヮヰヱヲッンー",
                FieldChecksEx.getZenkakuKanaList());
    }

}
