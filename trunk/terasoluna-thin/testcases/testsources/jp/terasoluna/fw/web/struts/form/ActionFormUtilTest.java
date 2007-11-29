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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import jp.terasoluna.utlib.DynaActionFormCreator;
import jp.terasoluna.utlib.MockHttpServletRequest;
import jp.terasoluna.utlib.MockHttpSession;
import junit.framework.TestCase;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.config.FormBeanConfig;
import org.apache.struts.config.FormPropertyConfig;
import org.apache.struts.config.ModuleConfig;
import org.apache.struts.config.impl.ModuleConfigImpl;

/**
 * {@link jp.terasoluna.fw.web.struts.form.ActionFormUtil} クラスのブラックボックステスト。
 *
 * <p>
 * <h4>【クラスの概要】</h4>
 * アクションフォーム関連のユーティリティクラス。
 * <p>
 *
 * @see jp.terasoluna.fw.web.struts.form.ActionFormUtil
 */
public class ActionFormUtilTest extends TestCase {

    /**
     * このクラスのテストで使用するDynaValidatorActionFormExの設定ファイル。
     */
    private static final String CONFIG_FILE_NAME =
        ActionFormUtil.class.getResource(
                "ActionFormUtilTest.xml").getPath();

    /**
     * DynaValidatorActionFormExを生成するdigesterのルールファイル。
     */
    private final static String RULES_FILE_NAME =
        ActionFormUtil.class.getResource(
                "ActionFormUtilTest-rules.xml").getPath();

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
     * このテストケースを実行する為の
     * GUI アプリケーションを起動する。
     *
     * @param args java コマンドに設定されたパラメータ
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(ActionFormUtilTest.class);
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
        this.formEx =
            (DynaValidatorActionFormEx) creator.create(CONFIG_FILE_NAME);
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
    public ActionFormUtilTest(String name) {
        super(name);
    }

    /**
     * testGetPropertyConfig01()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) fieldName:null<br>
     *         (引数) mapping:not null<br>
     *         (状態) mapping.getName:"abc"<br>
     *         (状態) config:not null<br>
     *                {FormPropertyConfig("aaa", "java.lang.String",null,0)}<br>
     *
     * <br>
     * 期待値：(戻り値) 型:null<br>
     *
     * <br>
     * config.fingFormPropertyConfig(fieldName)の結果が取得できることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetPropertyConfig01() throws Exception {
        //テストデータ設定
        String fieldName = null;
        ActionMapping mapping = new ActionMapping();
        ModuleConfig moduleConfig = new ModuleConfigImpl("");
        FormBeanConfig fBConfig = creator.parse(CONFIG_FILE_NAME);
        mapping.setName("abc");
        mapping.setModuleConfig(moduleConfig);
        moduleConfig.addFormBeanConfig(fBConfig);

        //テスト実行
        assertNull(ActionFormUtil.getPropertyConfig(fieldName, mapping));
    }

    /**
     * testGetPropertyConfig02()
     * <br><br>
     *
     * (異常系)
     * <br>
     * 観点：C,G
     * <br><br>
     * 入力値：(引数) fieldName:not null<br>
     *         (引数) mapping:null<br>
     *
     * <br>
     * 期待値：(戻り値) 型:NullPointerException<br>
     *
     * <br>
     * 引数のmappingがnullの場合、NullPointerExceptionが発生することを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetPropertyConfig02() throws Exception {
        //テストデータ設定
        //mappingにnullを設定
        String fieldName = "hoge";
        ActionMapping mapping = new ActionMapping();
        mapping = null;

        try {
            //テスト実行
            ActionFormUtil.getPropertyConfig(fieldName, mapping);
            fail();
        } catch (NullPointerException e) {
            //テスト成功
        }
    }

    /**
     * testGetPropertyConfig03()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) mapping:not null<br>
     *         (状態) mapping.getName:null<br>
     *
     * <br>
     * 期待値：(戻り値) 型:null<br>
     *
     * <br>
     * mappingからnameが取得できない場合、nullが取得できることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetPropertyConfig03() throws Exception {

        //テストデータ設定
        String fieldName = "field1";
        ActionMapping mapping = new ActionMapping();
        mapping.setName(null);

        //テスト実行
        //テスト結果確認
        assertNull(ActionFormUtil.getPropertyConfig(fieldName, mapping));
    }

    /**
     * testGetPropertyConfig04()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) mapping:not null<br>
     *         (状態) mapping.getName:"abc"<br>
     *         (状態) config:null<br>
     *
     * <br>
     * 期待値：(戻り値) 型:null<br>
     *
     * <br>
     * ModuleConfigからFormBeanConfigが取得できない場合、nullが取得できることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetPropertyConfig04() throws Exception {

        //テストデータ設定
        String fieldName = "field1";
        ActionMapping mapping = new ActionMapping();
        ModuleConfig moduleConfig = new ModuleConfigImpl("");
        mapping.setName("abc");
        mapping.setModuleConfig(moduleConfig);

        //テスト実行
        //テスト結果確認
        assertNull(ActionFormUtil.getPropertyConfig(fieldName, mapping));
    }

    /**
     * testGetPropertyConfig05()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) fieldName:"aaa"<br>
     *         (引数) mapping:not null<br>
     *         (状態) mapping.getName:"abc"<br>
     *         (状態) config:not null<br>
     *                {FormPropertyConfig("aaa", "java.lang.String",null,0)}<br>
     *
     * <br>
     * 期待値：(戻り値) 型:{FormPropertyConfig(<br>
     *                  "aaa", <br>
     *                  "java.lang.String",<br>
     *                  null,0)}<br>
     *
     * <br>
     * config.fingFormPropertyConfig(fieldName)の結果が取得できることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetPropertyConfig05() throws Exception {
        //テストデータ設定
        String fieldName = "aaa";
        ActionMapping mapping = new ActionMapping();
        ModuleConfig moduleConfig = new ModuleConfigImpl("");
        FormBeanConfig fBConfig = creator.parse(CONFIG_FILE_NAME);
        mapping.setName("abc");
        mapping.setModuleConfig(moduleConfig);
        moduleConfig.addFormBeanConfig(fBConfig);

        //テスト実行
        FormPropertyConfig result =
            ActionFormUtil.getPropertyConfig(fieldName, mapping);
        assertEquals("aaa", result.getName());
        assertEquals("java.lang.String", result.getType());
        assertEquals("null", result.getInitial());
        assertEquals(0, result.getSize());

    }

    /**
     * testGetPropertyConfig06()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) fieldName:""<br>
     *         (引数) mapping:not null<br>
     *         (状態) mapping.getName:"abc"<br>
     *         (状態) config:not null<br>
     *                {FormPropertyConfig("aaa", "java.lang.String",null,0)}<br>
     *
     * <br>
     * 期待値：(戻り値) 型:null<br>
     *
     * <br>
     * config.fingFormPropertyConfig(fieldName)の結果が取得できることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetPropertyConfig06() throws Exception {
        //テストデータ設定
        String fieldName = "";
        ActionMapping mapping = new ActionMapping();
        ModuleConfig moduleConfig = new ModuleConfigImpl("");
        FormBeanConfig fBConfig = creator.parse(CONFIG_FILE_NAME);
        mapping.setName("abc");
        mapping.setModuleConfig(moduleConfig);
        moduleConfig.addFormBeanConfig(fBConfig);

        //テスト実行
        assertNull(ActionFormUtil.getPropertyConfig(fieldName, mapping));

    }

    /**
     * testInitialize01()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) form:null<br>
     *
     * <br>
     * 期待値：
     * <br>
     * 何も処理が行われないこと
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testInitialize01() throws Exception {
        //テストデータ設定
        String fieldName = "hoge";
        // アクションマッピング
        ActionMapping mapping = new ActionMapping();
        mapping.setName("name");

        ActionFormUtil.initialize(null, fieldName, mapping);
    }

    /**
     * testInitialize02()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) form:not null<br>
     *         (引数) fileldName:null<br>
     *
     * <br>
     * 期待値：
     * <br>
     * 何も処理が行われないこと
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testInitialize02() throws Exception {
        //テストデータ設定
        // アクションマッピング
        ActionMapping mapping = new ActionMapping();
        mapping.setName("name");
        String fieldName = null;

        //テスト実行
        //テスト結果確認
        //例外が発生せずに正常終了することを確認
        ActionFormUtil.initialize(
                new DynaValidatorActionFormEx(), fieldName, mapping);
    }

    /**
     * testInitialize03()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) form:not null<br>
     *         (引数) fileldName:空文字<br>
     *
     * <br>
     * 期待値：
     * <br>
     * 何も処理が行われないこと
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testInitialize03() throws Exception {
        //テストデータ設定
        // アクションマッピング
        ActionMapping mapping = new ActionMapping();
        mapping.setName("formName");
        String fieldName = "";

        //テスト実行
        //テスト結果確認
        //例外が発生せずに正常終了することを確認
        ActionFormUtil.initialize(
                new DynaValidatorActionFormEx(), fieldName, mapping);
    }

    /**
     * testInitialize04()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) form:not null<br>
     *         (引数) fileldName:not null<br>
     *         (引数) mapping:null<br>
     *
     * <br>
     * 期待値：
     * <br>
     * 何も処理が行われないこと
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testInitialize04() throws Exception {
        //テストデータ設定
        String fieldName = "field2";

        //テスト実行
        ActionFormUtil.initialize(
                new DynaValidatorActionFormEx(), fieldName, null);
    }

    /**
     * testInitialize05()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) form:not null<br>
     *         (引数) fileldName:not null<br>
     *         (引数) mapping:not null<br>
     *         (状態) config:null<br>
     *
     * <br>
     * 期待値：
     * <br>
     * 何も処理が行われないこと
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testInitialize05() throws Exception {

        //テストデータ設定
        String fieldName = "field2";
        ActionMapping mapping = new ActionMapping();
        ModuleConfig moduleConfig = new ModuleConfigImpl("");
        mapping.setName("abc");
        mapping.setModuleConfig(moduleConfig);

        //テスト実行
        ActionFormUtil.initialize(
                new DynaValidatorActionFormEx(), fieldName, mapping);
    }

    /**
     * testInitialize06()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) form:not null<br>
     *                field="AAA"<br>
     *         (引数) fileldName:"field"<br>
     *         (引数) mapping:not null<br>
     *         (状態) config:not null<br>
     *
     * <br>
     * 期待値：(状態変化) fieldNameで指定したプロパティ値:""<br>
     *
     * <br>
     * 第2引数のfieldNameで指定したフィールド値が初期化されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testInitialize06() throws Exception {
        //テストデータ設定
        String fieldName = "field";
        ActionMapping mapping = new ActionMapping();
        ModuleConfig moduleConfig = new ModuleConfigImpl("");
        FormBeanConfig fBConfig = creator.parse(CONFIG_FILE_NAME);
        mapping.setName("abc");
        mapping.setModuleConfig(moduleConfig);
        moduleConfig.addFormBeanConfig(fBConfig);
        formEx.set("field", "AAA");

        //テスト実行
        ActionFormUtil.initialize(formEx, fieldName, mapping);
        assertEquals("", formEx.get("field"));

    }

    /**
     * testClearActionForm01()
     * <br><br>
     *
     * (異常系)
     * <br>
     * 観点：C,G
     * <br><br>
     * 入力値：(引数) session:null<br>
     *         (引数) exclude:_hoge<br>
     *
     * <br>
     * 期待値：(状態変化) 例外:NullPointerException<br>
     *
     * <br>
     * 引数のsessionがnullの場合、NullPointerExcpetionが発生することを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testClearActionForm01() throws Exception {

        try {
            //テスト実行
            ActionFormUtil.clearActionForm(null, "_hoge");
            fail();
        } catch (NullPointerException e) {
            //テスト結果確認
            // NullPointerExceptionがスローされた場合のみテスト成功。
        }
    }

    /**
     * testClearActionForm02()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) session:not null<br>
     *                {}<br>
     *         (引数) exclude:_hoge<br>
     *
     * <br>
     * 期待値：
     * <br>
     * sessionに値が格納されていなかった場合は、状態変化は起こらず、かつ正常に終了することを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testClearActionForm02() throws Exception {
        //テストデータ設定
        // 擬似セッション
        HttpSession session = new MockHttpSession();

        //テスト実行
        ActionFormUtil.clearActionForm(session, "_hoge");

        //テスト結果確認
        if ((session.getAttributeNames()).hasMoreElements()) {
            //要素が増えている場合はテスト失敗。
            fail();
        }
    }

    /**
     * testClearActionForm03()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) session:not null<br>
     *                {"hoge"= "value"}<br>
     *         (引数) exclude:_hoge<br>
     *
     * <br>
     * 期待値：(状態変化) session:{"hoge"= "value"}<br>
     *
     * <br>
     * session中にActionFormインスタンスが含まれない場合、状態変化がないことを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testClearActionForm03() throws Exception {
        //テストデータ設定
        // 擬似セッション
        HttpSession session = new MockHttpSession();
        String key = "hoge";
        Object value = "value";
        session.setAttribute(key, value);

        //テスト実行
        ActionFormUtil.clearActionForm(session, "_hoge");

        //テスト結果確認
        assertEquals(value, session.getAttribute(key));
    }

    /**
     * testClearActionForm04()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) session:not null<br>
     *                {"_hoge"= "value"}<br>
     *         (引数) exclude:_hoge<br>
     *
     * <br>
     * 期待値：(状態変化) session:{"_hoge"="value"}<br>
     *
     * <br>
     * session中にActionFormインスタンスが含まれない場合、状態変化がないことを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testClearActionForm04() throws Exception {
        //テストデータ設定
        // 擬似セッション
        HttpSession session = new MockHttpSession();
        String key = "_hoge";
        Object value = "value";
        session.setAttribute(key, value);

        //テスト実行
        ActionFormUtil.clearActionForm(session, "_hoge");

        //テスト結果確認
        assertEquals(value, session.getAttribute(key));
    }

    /**
     * testClearActionForm05()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) session:not null<br>
     *                {"hoge"=ActionFormインスタンス}<br>
     *         (引数) exclude:_hoge<br>
     *
     * <br>
     * 期待値：(状態変化) session:{"hoge"=ActionFormインスタンス}<br>
     *
     * <br>
     * session中にActionFormが含まれ、属性名が"_"から始まらない場合、状態変化がないことを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testClearActionForm05() throws Exception {
        //テストデータ設定
        // 擬似セッション
        HttpSession session = new MockHttpSession();
        String key = "hoge";
        ActionFormUtil_ActionFormStub01 form =
            new ActionFormUtil_ActionFormStub01();
        session.setAttribute(key, form);

        //テスト実行
        ActionFormUtil.clearActionForm(session, "_hoge");

        //テスト結果確認
        assertEquals(form, session.getAttribute(key));
    }

    /**
     * testClearActionForm06()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) session:not null<br>
     *                {"_hoge"=ActionFormインスタンス}<br>
     *         (引数) exclude:_hoge<br>
     *
     * <br>
     * 期待値：(状態変化) session:{"_hoge"=ActionFormインスタンス}<br>
     *
     * <br>
     * session中に属性名が第二引数のexcludeと一致するActionFormが含まれる場合、状態変化がないことを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testClearActionForm06() throws Exception {
        //テストデータ設定
        // 擬似セッション
        HttpSession session = new MockHttpSession();
        String key = "_hoge";
        ActionFormUtil_ActionFormStub01 form =
            new ActionFormUtil_ActionFormStub01();
        session.setAttribute(key, form);

        //テスト実行
        ActionFormUtil.clearActionForm(session, "_hoge");

        //テスト結果確認
        assertEquals(form, session.getAttribute(key));
    }

    /**
     * testClearActionForm07()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) session:not null<br>
     *                {"hoge"=ActionFormインスタンス,<br>
     *                 "foo"=ActionFormインスタンス,<br>
     *                 "world"=ActionFormインスタンス}<br>
     *         (引数) exclude:_hoge<br>
     *
     * <br>
     * 期待値：(状態変化) session:{"hoge"=ActionFormインスタンス,<br>
     *                     "foo"=ActionFormインスタンス,<br>
     *                     "world"=ActionFormインスタンス}<br>
     *
     * <br>
     * session中に属性名が"_"から始まらないActionFormが複数設定されている場合、状態変化がないことを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testClearActionForm07() throws Exception {
        //テストデータ設定
        // 擬似セッション
        HttpSession session = new MockHttpSession();
        String key1 = "hoge";
        String key2 = "foo";
        String key3 = "world";
        ActionFormUtil_ActionFormStub01 form1 =
            new ActionFormUtil_ActionFormStub01();
        ActionFormUtil_ActionFormStub01 form2 =
            new ActionFormUtil_ActionFormStub01();
        ActionFormUtil_ActionFormStub01 form3 =
            new ActionFormUtil_ActionFormStub01();
        session.setAttribute(key1, form1);
        session.setAttribute(key2, form2);
        session.setAttribute(key3, form3);

        //テスト実行
        ActionFormUtil.clearActionForm(session, "_hoge");

        //テスト結果確認
        assertEquals(form1, session.getAttribute(key1));
        assertEquals(form2, session.getAttribute(key2));
        assertEquals(form3, session.getAttribute(key3));
    }

    /**
     * testClearActionForm08()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) session:not null<br>
     *                {"_hoge"=ActionFormインスタンス,<br>
     *                 "foo"=ActionFormインスタンス,<br>
     *                 "world"=ActionFormインスタンス}<br>
     *         (引数) exclude:_hoge<br>
     *
     * <br>
     * 期待値：(状態変化) session:{"_hoge"=ActionFormインスタンス,<br>
     *                     "foo"=ActionFormインスタンス,<br>
     *                     "world"=ActionFormインスタンス}<br>
     *
     * <br>
     * session中に属性名が第二引数のexcludeと一致するActionFormを含む複数のActionForm(但し、その他は属性名が"_"から始まらない)が存在する場合、状態変化がないことを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testClearActionForm08() throws Exception {
        //テストデータ設定
        // 擬似セッション
        HttpSession session = new MockHttpSession();
        String key1 = "_hoge";
        String key2 = "foo";
        String key3 = "world";
        ActionFormUtil_ActionFormStub01 form1 =
            new ActionFormUtil_ActionFormStub01();
        ActionFormUtil_ActionFormStub01 form2 =
            new ActionFormUtil_ActionFormStub01();
        ActionFormUtil_ActionFormStub01 form3 =
            new ActionFormUtil_ActionFormStub01();
        session.setAttribute(key1, form1);
        session.setAttribute(key2, form2);
        session.setAttribute(key3, form3);

        //テスト実行
        ActionFormUtil.clearActionForm(session, "_hoge");

        //テスト結果確認
        assertEquals(form1, session.getAttribute(key1));
        assertEquals(form2, session.getAttribute(key2));
        assertEquals(form3, session.getAttribute(key3));
    }

    /**
     * testClearActionForm09()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) session:not null<br>
     *                {"_hoge"=ActionFormインスタンス,<br>
     *                 "_foo"=ActionFormインスタンス,<br>
     *                 "_world"=ActionFormインスタンス}<br>
     *         (引数) exclude:_hoge<br>
     *
     * <br>
     * 期待値：(状態変化) session:{"_hoge"=ActionFormインスタンス}<br>
     *
     * <br>
     * session中に属性名が"_"から始まる複数のActionFormが存在する場合し、excludeで指定した値と一致した属性名のActionFormが存在する場合、excludeで指定した属性名以外のActionFormが削除されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testClearActionForm09() throws Exception {
        //テストデータ設定
        // 擬似セッション
        HttpSession session = new MockHttpSession();
        String key1 = "_hoge";
        String key2 = "_foo";
        String key3 = "_world";
        ActionFormUtil_ActionFormStub01 form1 =
            new ActionFormUtil_ActionFormStub01();
        ActionFormUtil_ActionFormStub01 form2 =
            new ActionFormUtil_ActionFormStub01();
        ActionFormUtil_ActionFormStub01 form3 =
            new ActionFormUtil_ActionFormStub01();
        session.setAttribute(key1, form1);
        session.setAttribute(key2, form2);
        session.setAttribute(key3, form3);

        //テスト実行
        ActionFormUtil.clearActionForm(session, "_hoge");

        //テスト結果確認
        assertEquals(form1, session.getAttribute(key1));
        assertNull(session.getAttribute(key2));
        assertNull(session.getAttribute(key3));
    }

    /**
     * testClearActionForm10()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：C,F
     * <br><br>
     * 入力値：(引数) session:not null<br>
     *                {"_hoge"=ActionFormインスタンス,<br>
     *                 "_foo"=ActionFormインスタンス,<br>
     *                 "_world"=ActionFormインスタンス}<br>
     *         (引数) exclude:null<br>
     *
     * <br>
     * 期待値：(状態変化) session:{}<br>
     *
     * <br>
     * session中に属性名が"_"から始まる複数のActionFormが存在する場合し、excludeがnullの場合、全てのActionFormが削除されることを確認する。<br>
     * ※clearActionFrom(HttpSession)のテストケースを含む
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testClearActionForm10() throws Exception {
        //テストデータ設定
        // 擬似セッション
        HttpSession session = new MockHttpSession();
        String key1 = "_hoge";
        String key2 = "_foo";
        String key3 = "_world";
        ActionFormUtil_ActionFormStub01 form1 =
            new ActionFormUtil_ActionFormStub01();
        ActionFormUtil_ActionFormStub01 form2 =
            new ActionFormUtil_ActionFormStub01();
        ActionFormUtil_ActionFormStub01 form3 =
            new ActionFormUtil_ActionFormStub01();
        session.setAttribute(key1, form1);
        session.setAttribute(key2, form2);
        session.setAttribute(key3, form3);

        //テスト実行
        ActionFormUtil.clearActionForm(session, null);

        //テスト結果確認
        assertNull(session.getAttribute(key1));
        assertNull(session.getAttribute(key2));
        assertNull(session.getAttribute(key3));
    }

    /**
     * testGetActionFormName01()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：C,G
     * <br><br>
     * 入力値：(引数) req:null<br>
     *
     * <br>
     * 期待値：(状態変化) 例外:NullPointerException<br>
     *
     * <br>
     * 引数のreqがnullの場合、NullPointerExceptionが発生することを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetActionFormName01() throws Exception {

        try {
            //テスト実行
            ActionFormUtil.getActionFormName(null);
            fail();
        } catch (NullPointerException e) {
            //テスト結果確認
            // NullPointerExceptionがスローされた場合のみテスト成功。
        }
    }

    /**
     * testGetActionFormName02()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) req:not null<br>
     *         (状態) req.getAttribute(Globals.MAPPING_KEY):null<br>
     *
     * <br>
     * 期待値：(戻り値) String:null<br>
     *
     * <br>
     * リクエストからActionMappingインスタンスが取得できない場合、nullが取得できることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetActionFormName02() throws Exception {
        //テストデータ設定
        // 擬似リクエスト
        HttpServletRequest req = new MockHttpServletRequest();
        req.setAttribute("key", "value");

        //テスト実行
        //テスト結果確認
        assertNull(ActionFormUtil.getActionFormName(req));    }

    /**
     * testGetActionFormName03()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) req:not null<br>
     *         (状態) req.getAttribute(Globals.MAPPING_KEY):not null<br>
     *                name="abc"<br>
     *
     * <br>
     * 期待値：(戻り値) String:"abc"<br>
     *
     * <br>
     * AcionMappingのname属性の値が取得できることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetActionFormName03() throws Exception {
        //テストデータ設定
        // 擬似リクエスト
        HttpServletRequest req = new MockHttpServletRequest();

        // アクションマッピング
        ActionMapping mapping = new ActionMapping();
        mapping.setName("name");

        req.setAttribute(Globals.MAPPING_KEY, mapping);

        //テスト実行
        //テスト結果確認
        assertEquals("name", ActionFormUtil.getActionFormName(req));
    }

}
