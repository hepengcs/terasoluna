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

package jp.terasoluna.fw.web.struts.reset;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import jp.terasoluna.fw.web.struts.action.ActionMappingEx;
import jp.terasoluna.fw.web.struts.form.DynaValidatorActionFormEx;
import jp.terasoluna.fw.web.struts.form.ValidatorActionFormEx;
import jp.terasoluna.fw.util.PropertyAccessException;
import jp.terasoluna.utlib.DynaActionFormCreator;
import jp.terasoluna.utlib.LogUTUtil;
import jp.terasoluna.utlib.MockHttpServletRequest;
import jp.terasoluna.utlib.MockHttpSession;
import jp.terasoluna.utlib.MockServletContext;
import junit.framework.TestCase;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.config.ModuleConfig;
import org.apache.struts.config.impl.ModuleConfigImpl;

/**
 * {@link jp.terasoluna.fw.web.struts.reset.ResetterImpl} クラスのブラックボックステスト。
 *
 * <p>
 * <h4>【クラスの概要】</h4>
 * デフォルトのリセット実装クラス。
 * <p>
 *
 * @see jp.terasoluna.fw.web.struts.reset.ResetterImpl
 */
public class ResetterImplTest extends TestCase {

    /**
     * DynaValidatorActionFormExのプロパティ設定ファイル
     */
    private static final String CONFIG_FILE_PATH =
        ResetterImplTest.class.getResource(
                "ResetterImplTest.xml").getPath();

    /**
     * DynaValidatorActionFormEx生成ルール設定ファイル
     */
    private final static String RULES_FILE_PATH =
        ResetterImplTest.class.getResource(
                "ResetterImplTest-rules.xml").getPath();
    /**
     * DynaValidatorActionFormExを生成するクラス。
     */
    private static final DynaActionFormCreator creator
        = new DynaActionFormCreator(RULES_FILE_PATH);

    /**
     * テスト対象のインスタンス
     */
    private DynaValidatorActionFormEx formEx = null;

    /**
     * このテストケースを実行する為の
     * GUI アプリケーションを起動する。
     *
     * @param args java コマンドに設定されたパラメータ
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(ResetterImplTest.class);
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
        // テストで使用するDynaValidatorActionFormExインスタンスを生成
        this.formEx = (DynaValidatorActionFormEx) creator
        .create(CONFIG_FILE_PATH);
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
    public ResetterImplTest(String name) {
        super(name);
    }

    /**
     * testReset01()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：C,F
     * <br><br>
     * 入力値：(引数) form:not null<br>
     *         (引数) mapping:not null<br>
     *         (引数) request:not null<br>
     *         (状態) getActionResetの結果:null<br>
     *
     * <br>
     * 期待値：(状態変化) resetSelectField:呼び出されない<br>
     *         (状態変化) resetValue:呼び出されない<br>
     *
     * <br>
     * getActionResetがnullを返却する場合、何も処理が行われず、正常終了することを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testReset01() throws Exception {
        // 前処理
        ResetterImplStub01 stub01 = new ResetterImplStub01();
        ValidatorActionFormEx form = new ResetterImpl_ValidatorActionFormExStub01();
        ActionMapping mapping = new ActionMapping();
        HttpServletRequest req = new MockHttpServletRequest();

        // テスト実施
        stub01.reset(form , mapping ,req);

        // 判定
        assertEquals(0, stub01.resetSelectFieldCount);
        assertEquals(0, stub01.resetValueCount);
    }

    /**
     * testReset02()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) form:not null<br>
     *         (引数) mapping:not null<br>
     *         (引数) request:not null<br>
     *         (状態) getActionResetの結果:{}<br>
     *
     * <br>
     * 期待値：(状態変化) resetSelectField:呼び出されない<br>
     *         (状態変化) resetValue:呼び出されない<br>
     *
     * <br>
     * getActionResetで取得したActionResetが空のIteratorを返却する場合、何も処理が行われず、正常終了することを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testReset02() throws Exception {
        // 前処理
        ActionReset actionReset = new ActionReset();
        ResetterImplStub01 stub01 = new ResetterImplStub01();
        stub01.actionReset = actionReset;

        ValidatorActionFormEx form = new ResetterImpl_ValidatorActionFormExStub01();
        ActionMapping mapping = new ActionMapping();
        HttpServletRequest req = new MockHttpServletRequest();

        // テスト実施
        stub01.reset(form , mapping ,req);

        // 判定
        assertEquals(0, stub01.resetSelectFieldCount);
        assertEquals(0, stub01.resetValueCount);
    }

    /**
     * testReset03()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) form:ValidatorActionFormExインスタンス<br>
     *                String[] values = {"a",null,"c"}<br>
     *         (引数) mapping:not null<br>
     *         (引数) request:not null<br>
     *         (状態) getActionResetの結果:{FieldReset(field="values",select=true)}<br>
     *
     * <br>
     * 期待値：(状態変化) resetSelectField:form=引数のform、<br>
     *                    propMap={<br>
     *                      values[0]="a",<br>
     *                      values[1]=null,<br>
     *                      values[2]="c"}、<br>
     *                    request=引数のrequest<br>
     *                    で呼び出されること。<br>
     *         (状態変化) resetValue:呼び出されない<br>
     *
     * <br>
     * getActionResetで取得したActionResetがselect指定されており、該当フィールドが複数件ある場合、resetSelectFieldが呼び出されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testReset03() throws Exception {
        // 前処理
        ResetterImplStub01 stub01 = new ResetterImplStub01();
        ResetterImpl_ValidatorActionFormExStub01 form =
            new ResetterImpl_ValidatorActionFormExStub01();
        String[] values = {
            "a",
            null,
            "c"
        };
        form.values = values;
        ActionReset actionReset = new ActionReset();
        FieldReset fieldReset = new FieldReset();
        fieldReset.setFieldName("values");
        fieldReset.setSelect(true);
        actionReset.setFieldReset(fieldReset);
        stub01.actionReset = actionReset;

        ActionMapping mapping = new ActionMapping();
        HttpServletRequest req = new MockHttpServletRequest();

        // テスト実施
        stub01.reset(form, mapping, req);

        // 判定
        assertEquals(0, stub01.resetValueCount);
        assertEquals(1, stub01.resetSelectFieldCount);
        assertSame(form, stub01.resetSelectFieldArg0.get(0));
        assertSame(req, stub01.resetSelectFieldArg2.get(0));
        Map map = stub01.resetSelectFieldArg1.get(0);
        assertEquals(3, map.size());
        assertEquals("a", map.get("values[0]"));
        assertNull(map.get("values[1]"));
        assertEquals("c", map.get("values[2]"));
    }

    /**
     * testReset04()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) form:ValidatorActionFormExインスタンス<br>
     *                String value = "a"<br>
     *         (引数) mapping:not null<br>
     *         (引数) request:not null<br>
     *         (状態) getActionResetの結果:{FieldReset(field="value",select=true)}<br>
     *
     * <br>
     * 期待値：(状態変化) resetSelectField:呼び出されない<br>
     *         (状態変化) resetValue:form=引数のform、<br>
     *                    entry={value="a"}<br>
     *                    で呼び出されること。<br>
     *
     * <br>
     * getActionResetで取得したActionResetがselect指定されているが、該当フィールドが1件しかない場合、resetValueが呼び出されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testReset04() throws Exception {
        // 前処理
        ResetterImplStub01 stub01 = new ResetterImplStub01();
        ResetterImpl_ValidatorActionFormExStub01 form =
            new ResetterImpl_ValidatorActionFormExStub01();
        form.value = "a";
        ActionReset actionReset = new ActionReset();
        FieldReset fieldReset = new FieldReset();
        fieldReset.setFieldName("value");
        fieldReset.setSelect(true);
        actionReset.setFieldReset(fieldReset);
        stub01.actionReset = actionReset;

        ActionMapping mapping = new ActionMapping();
        HttpServletRequest req = new MockHttpServletRequest();

        // テスト実施
        stub01.reset(form, mapping, req);

        // 判定
        assertEquals(0, stub01.resetSelectFieldCount);
        assertEquals(1, stub01.resetValueCount);
        assertSame(form, stub01.resetValueArg0.get(0));
        Entry entry = stub01.resetValueArg1.get(0);
        assertEquals("value", entry.getKey());
        assertEquals("a", entry.getValue());
    }

    /**
     * testReset05()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) form:ValidatorActionFormExインスタンス<br>
     *                String[] values = {"a",null,"c"}<br>
     *         (引数) mapping:not null<br>
     *         (引数) request:not null<br>
     *         (状態) getActionResetの結果:{FieldReset(field="values",select=false)}<br>
     *
     * <br>
     * 期待値：(状態変化) resetSelectField:呼び出されない<br>
     *         (状態変化) resetValue:１：entry={values[0]="a"}<br>
     *                    ２：entry={values[1]=null}<br>
     *                    ３：entry={values[2]="c"}<br>
     *                    で呼び出されること。<br>
     *                    ※formは全てについて引数のform<br>
     *
     * <br>
     * getActionResetで取得したActionResetがselect指定されてなく、該当フィールドが配列型の場合、要素数分、resetValueが呼び出されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testReset05() throws Exception {
        // 前処理
        ResetterImplStub01 stub01 = new ResetterImplStub01();
        ResetterImpl_ValidatorActionFormExStub01 form =
            new ResetterImpl_ValidatorActionFormExStub01();
        String[] values = {
            "a",
            null,
            "c"
        };
        form.values = values;
        ActionReset actionReset = new ActionReset();
        FieldReset fieldReset = new FieldReset();
        fieldReset.setFieldName("values");
        fieldReset.setSelect(false);
        actionReset.setFieldReset(fieldReset);
        stub01.actionReset = actionReset;

        ActionMapping mapping = new ActionMapping();
        HttpServletRequest req = new MockHttpServletRequest();

        // テスト実施
        stub01.reset(form, mapping, req);

        // 判定
        assertEquals(0, stub01.resetSelectFieldCount);
        assertEquals(3, stub01.resetValueCount);
        // 1回目の呼び出し。
        assertSame(form, stub01.resetValueArg0.get(0));
        Entry entry = stub01.resetValueArg1.get(0);
        assertEquals("values[0]", entry.getKey());
        assertEquals("a", entry.getValue());
        // 2回目の呼び出し。
        assertSame(form, stub01.resetValueArg0.get(1));
        entry = stub01.resetValueArg1.get(1);
        assertEquals("values[1]", entry.getKey());
        assertNull(entry.getValue());
        // 3回目の呼び出し。
        assertSame(form, stub01.resetValueArg0.get(2));
        entry = stub01.resetValueArg1.get(2);
        assertEquals("values[2]", entry.getKey());
        assertEquals("c", entry.getValue());

    }

    /**
     * testReset06()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) form:ValidatorActionFormExインスタンス<br>
     *                ArrayList valueList = {"a",null,"c"}<br>
     *         (引数) mapping:not null<br>
     *         (引数) request:not null<br>
     *         (状態) getActionResetの結果:{FieldReset(field="valueList",select=false)}<br>
     *
     * <br>
     * 期待値：(状態変化) resetSelectField:呼び出されない<br>
     *         (状態変化) resetValue:１：entry={valueList[0]="a"}<br>
     *                    ２：entry={valueList[1]=null}<br>
     *                    ３：entry={valueList[2]="c"}<br>
     *                    で呼び出されること。<br>
     *                    ※formは全てについて引数のform<br>
     *
     * <br>
     * getActionResetで取得したActionResetがselect指定されてなく、該当フィールドがCollection型の場合、要素数分、resetValueが呼び出されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    @SuppressWarnings("unchecked")
    public void testReset06() throws Exception {
        // 前処理
        ResetterImplStub01 stub01 = new ResetterImplStub01();
        ResetterImpl_ValidatorActionFormExStub01 form =
            new ResetterImpl_ValidatorActionFormExStub01();
        List valueList = new ArrayList();
        valueList.add("a");
        valueList.add(null);
        valueList.add("c");
        form.valueList = valueList;
        ActionReset actionReset = new ActionReset();
        FieldReset fieldReset = new FieldReset();
        fieldReset.setFieldName("valueList");
        fieldReset.setSelect(false);
        actionReset.setFieldReset(fieldReset);
        stub01.actionReset = actionReset;

        ActionMapping mapping = new ActionMapping();
        HttpServletRequest req = new MockHttpServletRequest();

        // テスト実施
        stub01.reset(form, mapping, req);

        // 判定
        assertEquals(0, stub01.resetSelectFieldCount);
        assertEquals(3, stub01.resetValueCount);
        // 1回目の呼び出し。
        assertSame(form, stub01.resetValueArg0.get(0));
        Entry entry = stub01.resetValueArg1.get(0);
        assertEquals("valueList[0]", entry.getKey());
        assertEquals("a", entry.getValue());
        // 2回目の呼び出し。
        assertSame(form, stub01.resetValueArg0.get(1));
        entry = stub01.resetValueArg1.get(1);
        assertEquals("valueList[1]", entry.getKey());
        assertNull(entry.getValue());
        // 3回目の呼び出し。
        assertSame(form, stub01.resetValueArg0.get(2));
        entry = stub01.resetValueArg1.get(2);
        assertEquals("valueList[2]", entry.getKey());
        assertEquals("c", entry.getValue());

    }

    /**
     * testReset07()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) form:ValidatorActionFormExインスタンス<br>
     *                HashMap map = {key="test"}<br>
     *         (引数) mapping:not null<br>
     *         (引数) request:not null<br>
     *         (状態) getActionResetの結果:{FieldReset(field="map(key)",select=false)}<br>
     *
     * <br>
     * 期待値：(状態変化) resetSelectField:呼び出されない<br>
     *         (状態変化) resetValue:form=引数のform、<br>
     *                    entry={map(key)="test"}<br>
     *                    で呼び出されること。<br>
     *
     * <br>
     * getActionResetで取得したActionResetがselect指定されてなく、該当フィールドがMap型の場合、resetValueが呼び出されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    @SuppressWarnings("unchecked")
    public void testReset07() throws Exception {
        // 前処理
        ResetterImplStub01 stub01 = new ResetterImplStub01();
        ResetterImpl_ValidatorActionFormExStub01 form =
            new ResetterImpl_ValidatorActionFormExStub01();
        Map map = new HashMap();
        map.put("key", "test");
        form.map = map;
        ActionReset actionReset = new ActionReset();
        FieldReset fieldReset = new FieldReset();
        fieldReset.setFieldName("map(key)");
        fieldReset.setSelect(false);
        actionReset.setFieldReset(fieldReset);
        stub01.actionReset = actionReset;

        ActionMapping mapping = new ActionMapping();
        HttpServletRequest req = new MockHttpServletRequest();

        // テスト実施
        stub01.reset(form, mapping, req);

        // 判定
        assertEquals(0, stub01.resetSelectFieldCount);
        assertEquals(1, stub01.resetValueCount);
        // 1回目の呼び出し。
        assertSame(form, stub01.resetValueArg0.get(0));
        Entry entry = stub01.resetValueArg1.get(0);
        assertEquals("map(key)", entry.getKey());
        assertEquals("test", entry.getValue());

    }

    /**
     * testReset08()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) form:ValidatorActionFormExインスタンス<br>
     *                JavaBean row<br>
     *                  ＋String value = "a"<br>
     *         (引数) mapping:not null<br>
     *         (引数) request:not null<br>
     *         (状態) getActionResetの結果:{FieldReset(field="row.value",select=false)}<br>
     *
     * <br>
     * 期待値：(状態変化) resetSelectField:呼び出されない<br>
     *         (状態変化) resetValue:form=引数のform、<br>
     *                    entry={row.value="a"}<br>
     *                    で呼び出されること。<br>
     *
     * <br>
     * リセット対象のフィールドがネストしている場合、resetValueが呼び出されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testReset08() throws Exception {
        // 前処理
        ResetterImplStub01 stub01 = new ResetterImplStub01();
        ResetterImpl_ValidatorActionFormExStub01 form =
            new ResetterImpl_ValidatorActionFormExStub01();
        ResetterImpl_JavaBeanStub01 row =
            new ResetterImpl_JavaBeanStub01();
        row.value = "a";
        form.row = row;
        ActionReset actionReset = new ActionReset();
        FieldReset fieldReset = new FieldReset();
        fieldReset.setFieldName("row.value");
        fieldReset.setSelect(false);
        actionReset.setFieldReset(fieldReset);
        stub01.actionReset = actionReset;

        ActionMapping mapping = new ActionMapping();
        HttpServletRequest req = new MockHttpServletRequest();

        // テスト実施
        stub01.reset(form, mapping, req);

        // 判定
        assertEquals(0, stub01.resetSelectFieldCount);
        assertEquals(1, stub01.resetValueCount);
        // 1回目の呼び出し。
        assertSame(form, stub01.resetValueArg0.get(0));
        Entry entry = stub01.resetValueArg1.get(0);
        assertEquals("row.value", entry.getKey());
        assertEquals("a", entry.getValue());

    }

    /**
     * testReset09()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) form:ValidatorActionFormExインスタンス<br>
     *                JavaBean row<br>
     *                  ＋String[] values = {"a",null,"c"}<br>
     *         (引数) mapping:not null<br>
     *         (引数) request:not null<br>
     *         (状態) getActionResetの結果:{FieldReset(field="row.values",select=false)}<br>
     *
     * <br>
     * 期待値：(状態変化) resetSelectField:呼び出されない<br>
     *         (状態変化) resetValue:１：entry={row.values[0]="a"}<br>
     *                    ２：entry={row.values[1]=null}<br>
     *                    ３：entry={row.values[2]="c"}<br>
     *                    で呼び出されること。<br>
     *                    ※formは全てについて引数のform<br>
     *
     * <br>
     * リセット対象のフィールドがネストした配列の場合、全ての要素についてresetValueが呼び出されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testReset09() throws Exception {
        // 前処理
        ResetterImplStub01 stub01 = new ResetterImplStub01();
        ResetterImpl_ValidatorActionFormExStub01 form =
            new ResetterImpl_ValidatorActionFormExStub01();
        ResetterImpl_JavaBeanStub01 row =
            new ResetterImpl_JavaBeanStub01();
        String[] values = {
            "a",
            null,
            "c"
        };
        row.values = values;
        form.row = row;
        ActionReset actionReset = new ActionReset();
        FieldReset fieldReset = new FieldReset();
        fieldReset.setFieldName("row.values");
        fieldReset.setSelect(false);
        actionReset.setFieldReset(fieldReset);
        stub01.actionReset = actionReset;

        ActionMapping mapping = new ActionMapping();
        HttpServletRequest req = new MockHttpServletRequest();

        // テスト実施
        stub01.reset(form, mapping, req);

        // 判定
        assertEquals(0, stub01.resetSelectFieldCount);
        assertEquals(3, stub01.resetValueCount);
        // 1回目の呼び出し。
        assertSame(form, stub01.resetValueArg0.get(0));
        Entry entry = stub01.resetValueArg1.get(0);
        assertEquals("row.values[0]", entry.getKey());
        assertEquals("a", entry.getValue());
        // 2回目の呼び出し。
        assertSame(form, stub01.resetValueArg0.get(1));
        entry = stub01.resetValueArg1.get(1);
        assertEquals("row.values[1]", entry.getKey());
        assertNull(entry.getValue());
        // 3回目の呼び出し。
        assertSame(form, stub01.resetValueArg0.get(2));
        entry = stub01.resetValueArg1.get(2);
        assertEquals("row.values[2]", entry.getKey());
        assertEquals("c", entry.getValue());

    }

    /**
     * testReset10()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) form:ValidatorActionFormExインスタンス<br>
     *                JavaBean row<br>
     *                  ＋ArrayList valueList = {"a",null,"c"}<br>
     *         (引数) mapping:not null<br>
     *         (引数) request:not null<br>
     *         (状態) getActionResetの結果:{FieldReset(field="row.values",select=false)}<br>
     *
     * <br>
     * 期待値：(状態変化) resetSelectField:呼び出されない<br>
     *         (状態変化) resetValue:１：entry={row.values[0]="a"}<br>
     *                    ２：entry={row.values[1]=null}<br>
     *                    ３：entry={row.values[2]="c"}<br>
     *                    で呼び出されること。<br>
     *                    ※formは全てについて引数のform<br>
     *
     * <br>
     * リセット対象のフィールドがネストしたCollection型の場合、全ての要素についてresetValueが呼び出されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    @SuppressWarnings("unchecked")
    public void testReset10() throws Exception {
        // 前処理
        ResetterImplStub01 stub01 = new ResetterImplStub01();
        ResetterImpl_ValidatorActionFormExStub01 form =
            new ResetterImpl_ValidatorActionFormExStub01();
        ResetterImpl_JavaBeanStub01 row =
            new ResetterImpl_JavaBeanStub01();
        List valueList = new ArrayList();
        valueList.add("a");
        valueList.add(null);
        valueList.add("c");
        row.valueList = valueList;
        form.row = row;
        ActionReset actionReset = new ActionReset();
        FieldReset fieldReset = new FieldReset();
        fieldReset.setFieldName("row.valueList");
        fieldReset.setSelect(false);
        actionReset.setFieldReset(fieldReset);
        stub01.actionReset = actionReset;

        ActionMapping mapping = new ActionMapping();
        HttpServletRequest req = new MockHttpServletRequest();

        // テスト実施
        stub01.reset(form, mapping, req);

        // 判定
        assertEquals(0, stub01.resetSelectFieldCount);
        assertEquals(3, stub01.resetValueCount);
        // 1回目の呼び出し。
        assertSame(form, stub01.resetValueArg0.get(0));
        Entry entry = stub01.resetValueArg1.get(0);
        assertEquals("row.valueList[0]", entry.getKey());
        assertEquals("a", entry.getValue());
        // 2回目の呼び出し。
        assertSame(form, stub01.resetValueArg0.get(1));
        entry = stub01.resetValueArg1.get(1);
        assertEquals("row.valueList[1]", entry.getKey());
        assertNull(entry.getValue());
        // 3回目の呼び出し。
        assertSame(form, stub01.resetValueArg0.get(2));
        entry = stub01.resetValueArg1.get(2);
        assertEquals("row.valueList[2]", entry.getKey());
        assertEquals("c", entry.getValue());

    }

    /**
     * testReset11()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) form:ValidatorActionFormExインスタンス<br>
     *                JavaBean row<br>
     *                  ＋HashMap map = {key="test"}<br>
     *         (引数) mapping:not null<br>
     *         (引数) request:not null<br>
     *         (状態) getActionResetの結果:{FieldReset(field="row.map(key)",select=false)}<br>
     *
     * <br>
     * 期待値：(状態変化) resetSelectField:呼び出されない<br>
     *         (状態変化) resetValue:form=引数のform、<br>
     *                    entry={row.map(key)="test"}<br>
     *                    で呼び出されること。<br>
     *
     * <br>
     * リセット対象のフィールドがネストしたMapの場合、resetValueが呼び出されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    @SuppressWarnings("unchecked")
    public void testReset11() throws Exception {
        // 前処理
        ResetterImplStub01 stub01 = new ResetterImplStub01();
        ResetterImpl_ValidatorActionFormExStub01 form =
            new ResetterImpl_ValidatorActionFormExStub01();
        ResetterImpl_JavaBeanStub01 row =
            new ResetterImpl_JavaBeanStub01();
        Map map = new HashMap();
        map.put("key", "test");
        row.map = map;
        form.row = row;
        ActionReset actionReset = new ActionReset();
        FieldReset fieldReset = new FieldReset();
        fieldReset.setFieldName("row.map(key)");
        fieldReset.setSelect(false);
        actionReset.setFieldReset(fieldReset);
        stub01.actionReset = actionReset;

        ActionMapping mapping = new ActionMapping();
        HttpServletRequest req = new MockHttpServletRequest();

        // テスト実施
        stub01.reset(form, mapping, req);

        // 判定
        assertEquals(0, stub01.resetSelectFieldCount);
        assertEquals(1, stub01.resetValueCount);
        // 1回目の呼び出し。
        assertSame(form, stub01.resetValueArg0.get(0));
        Entry entry = stub01.resetValueArg1.get(0);
        assertEquals("row.map(key)", entry.getKey());
        assertEquals("test", entry.getValue());

    }

    /**
     * testReset12()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) form:ValidatorActionFormExインスタンス<br>
     *                JavaBean[] rows[0]<br>
     *                  ＋String value="a"<br>
     *                JavaBean[] rows[1]<br>
     *                  ＋String value=null<br>
     *                JavaBean[] rows[2]<br>
     *                  ＋String value="c"<br>
     *         (引数) mapping:not null<br>
     *         (引数) request:not null<br>
     *         (状態) getActionResetの結果:{FieldReset(field="rows.value",select=false)}<br>
     *
     * <br>
     * 期待値：(状態変化) resetSelectField:呼び出されない<br>
     *         (状態変化) resetValue:１：entry={rows[0].value="a"}<br>
     *                    ２：entry={rows[1].value=null}<br>
     *                    ３：entry={rows[2].value="c"}<br>
     *                    で呼び出されること。<br>
     *                    ※formは全てについて引数のform<br>
     *
     * <br>
     * リセット対象のフィールドが配列のネストした属性の場合、すべての要素についてresetValueが呼び出されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testReset12() throws Exception {
        // 前処理
        ResetterImplStub01 stub01 = new ResetterImplStub01();
        ResetterImpl_ValidatorActionFormExStub01 form =
            new ResetterImpl_ValidatorActionFormExStub01();
        ResetterImpl_JavaBeanStub01[] rows = {
            new ResetterImpl_JavaBeanStub01(),
            new ResetterImpl_JavaBeanStub01(),
            new ResetterImpl_JavaBeanStub01()
        };
        rows[0].value = "a";
        rows[1].value = null;
        rows[2].value = "c";
        form.rows = rows;
        ActionReset actionReset = new ActionReset();
        FieldReset fieldReset = new FieldReset();
        fieldReset.setFieldName("rows.value");
        fieldReset.setSelect(false);
        actionReset.setFieldReset(fieldReset);
        stub01.actionReset = actionReset;

        ActionMapping mapping = new ActionMapping();
        HttpServletRequest req = new MockHttpServletRequest();

        // テスト実施
        stub01.reset(form, mapping, req);

        // 判定
        assertEquals(0, stub01.resetSelectFieldCount);
        assertEquals(3, stub01.resetValueCount);
        // 1回目の呼び出し。
        assertSame(form, stub01.resetValueArg0.get(0));
        Entry entry = stub01.resetValueArg1.get(0);
        assertEquals("rows[0].value", entry.getKey());
        assertEquals("a", entry.getValue());
        // 2回目の呼び出し。
        assertSame(form, stub01.resetValueArg0.get(1));
        entry = stub01.resetValueArg1.get(1);
        assertEquals("rows[1].value", entry.getKey());
        assertNull(entry.getValue());
        // 3回目の呼び出し。
        assertSame(form, stub01.resetValueArg0.get(2));
        entry = stub01.resetValueArg1.get(2);
        assertEquals("rows[2].value", entry.getKey());
        assertEquals("c", entry.getValue());

    }

    /**
     * testReset13()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) form:ValidatorActionFormExインスタンス<br>
     *                ArrayList<JavaBean> rowList[0]<br>
     *                  ＋String value="a"<br>
     *                ArrayList<JavaBean> rowList[1]<br>
     *                  ＋String value=null<br>
     *                ArrayList<JavaBean> rowList[2]<br>
     *                  ＋String value="c"<br>
     *         (引数) mapping:not null<br>
     *         (引数) request:not null<br>
     *         (状態) getActionResetの結果:{FieldReset(field="rowList.value",select=false)}<br>
     *
     * <br>
     * 期待値：(状態変化) resetSelectField:呼び出されない<br>
     *         (状態変化) resetValue:１：entry={rowList[0].value="a"}<br>
     *                    ２：entry={rowList[1].value=null}<br>
     *                    ３：entry={rowList[2].value="c"}<br>
     *                    で呼び出されること。<br>
     *                    ※formは全てについて引数のform<br>
     *
     * <br>
     * リセット対象のフィールドがCollection型のネストした属性の場合、すべての要素についてresetValueが呼び出されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    @SuppressWarnings("unchecked")
    public void testReset13() throws Exception {
        // 前処理
        ResetterImplStub01 stub01 = new ResetterImplStub01();
        ResetterImpl_ValidatorActionFormExStub01 form =
            new ResetterImpl_ValidatorActionFormExStub01();
        List rowList = new ArrayList();
        ResetterImpl_JavaBeanStub01 row1 = new ResetterImpl_JavaBeanStub01();
        row1.value = "a";
        ResetterImpl_JavaBeanStub01 row2 = new ResetterImpl_JavaBeanStub01();
        row2.value = null;
        ResetterImpl_JavaBeanStub01 row3 = new ResetterImpl_JavaBeanStub01();
        row3.value = "c";
        rowList.add(row1);
        rowList.add(row2);
        rowList.add(row3);
        form.rowList = rowList;
        ActionReset actionReset = new ActionReset();
        FieldReset fieldReset = new FieldReset();
        fieldReset.setFieldName("rowList.value");
        fieldReset.setSelect(false);
        actionReset.setFieldReset(fieldReset);
        stub01.actionReset = actionReset;

        ActionMapping mapping = new ActionMapping();
        HttpServletRequest req = new MockHttpServletRequest();

        // テスト実施
        stub01.reset(form, mapping, req);

        // 判定
        assertEquals(0, stub01.resetSelectFieldCount);
        assertEquals(3, stub01.resetValueCount);
        // 1回目の呼び出し。
        assertSame(form, stub01.resetValueArg0.get(0));
        Entry entry = stub01.resetValueArg1.get(0);
        assertEquals("rowList[0].value", entry.getKey());
        assertEquals("a", entry.getValue());
        // 2回目の呼び出し。
        assertSame(form, stub01.resetValueArg0.get(1));
        entry = stub01.resetValueArg1.get(1);
        assertEquals("rowList[1].value", entry.getKey());
        assertNull(entry.getValue());
        // 3回目の呼び出し。
        assertSame(form, stub01.resetValueArg0.get(2));
        entry = stub01.resetValueArg1.get(2);
        assertEquals("rowList[2].value", entry.getKey());
        assertEquals("c", entry.getValue());

    }

    /**
     * testReset14()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) form:ValidatorActionFormExインスタンス<br>
     *                JavaBean[] rows[0]<br>
     *                  ＋Map map={key="a"}<br>
     *                JavaBean[] rows[1]<br>
     *                  ＋Map map={key=null}<br>
     *                JavaBean[] rows[2]<br>
     *                  ＋Map map={key="c"}<br>
     *         (引数) mapping:not null<br>
     *         (引数) request:not null<br>
     *         (状態) getActionResetの結果:{FieldReset(field="rows.map(key)",select=false)}<br>
     *
     * <br>
     * 期待値：(状態変化) resetSelectField:呼び出されない<br>
     *         (状態変化) resetValue:１：entry={rows[0]map(key)="a"}<br>
     *                    ２：entry={rows[1].map(key)=null}<br>
     *                    ３：entry={rows[2].map(key)="c"}<br>
     *                    で呼び出されること。<br>
     *                    ※formは全てについて引数のform<br>
     *
     * <br>
     * リセット対象のフィールドが配列のネストしたMap型属性の場合、すべての要素についてresetValueが呼び出されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    @SuppressWarnings("unchecked")
    public void testReset14() throws Exception {
        // 前処理
        ResetterImplStub01 stub01 = new ResetterImplStub01();
        ResetterImpl_ValidatorActionFormExStub01 form =
            new ResetterImpl_ValidatorActionFormExStub01();
        ResetterImpl_JavaBeanStub01[] rows = {
            new ResetterImpl_JavaBeanStub01(),
            new ResetterImpl_JavaBeanStub01(),
            new ResetterImpl_JavaBeanStub01()
        };
        Map map = new HashMap();
        map.put("key", "a");
        rows[0].map = map;
        map = new HashMap();
        map.put("key", null);
        rows[1].map = map;
        map = new HashMap();
        map.put("key", "c");
        rows[2].map = map;
        form.rows = rows;
        ActionReset actionReset = new ActionReset();
        FieldReset fieldReset = new FieldReset();
        fieldReset.setFieldName("rows.map(key)");
        fieldReset.setSelect(false);
        actionReset.setFieldReset(fieldReset);
        stub01.actionReset = actionReset;

        ActionMapping mapping = new ActionMapping();
        HttpServletRequest req = new MockHttpServletRequest();

        // テスト実施
        stub01.reset(form, mapping, req);

        // 判定
        // TODO JXPathIndexedBeanWrapperImpl見直し後再テスト
        assertEquals(0, stub01.resetSelectFieldCount);
        assertEquals(3, stub01.resetValueCount);
        // 1回目の呼び出し。
        assertSame(form, stub01.resetValueArg0.get(0));
        Entry entry = stub01.resetValueArg1.get(0);
        assertEquals("rows[0].map(key)", entry.getKey());
        assertEquals("a", entry.getValue());
        // 2回目の呼び出し。
        assertSame(form, stub01.resetValueArg0.get(1));
        entry = stub01.resetValueArg1.get(1);
        assertEquals("rows[1].map(key)", entry.getKey());
        assertNull(entry.getValue());
        // 3回目の呼び出し。
        assertSame(form, stub01.resetValueArg0.get(2));
        entry = stub01.resetValueArg1.get(2);
        assertEquals("rows[2].map(key)", entry.getKey());
        assertEquals("c", entry.getValue());

    }

    /**
     * testReset15()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) form:ValidatorActionFormExインスタンス<br>
     *                ArrayList<JavaBean> rowList[0]<br>
     *                  ＋Map map={key="a"}<br>
     *                ArrayList<JavaBean> rowList[1]<br>
     *                  ＋Map map={key=null}<br>
     *                ArrayList<JavaBean> rowList[2]<br>
     *                  ＋Map map={key="c"}<br>
     *         (引数) mapping:not null<br>
     *         (引数) request:not null<br>
     *         (状態) getActionResetの結果:{FieldReset(field="rowList.map(key)",select=false)}<br>
     *
     * <br>
     * 期待値：(状態変化) resetSelectField:呼び出されない<br>
     *         (状態変化) resetValue:１：entry={rowList[0].value="a"}<br>
     *                    ２：entry={rowList[1].value=null}<br>
     *                    ３：entry={rowList[2].value="c"}<br>
     *                    で呼び出されること。<br>
     *                    ※formは全てについて引数のform<br>
     *
     * <br>
     * リセット対象のフィールドが配列のネストしたMap型属性の場合、すべての要素についてresetValueが呼び出されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    @SuppressWarnings("unchecked")
    public void testReset15() throws Exception {
        // 前処理
        ResetterImplStub01 stub01 = new ResetterImplStub01();
        ResetterImpl_ValidatorActionFormExStub01 form =
            new ResetterImpl_ValidatorActionFormExStub01();
        List rowList = new ArrayList();
        Map map = new HashMap();
        map.put("key", "a");
        ResetterImpl_JavaBeanStub01 row = new ResetterImpl_JavaBeanStub01();
        row.map = map;
        rowList.add(row);
        map = new HashMap();
        map.put("key", null);
        row = new ResetterImpl_JavaBeanStub01();
        row.map = map;
        rowList.add(row);
        map = new HashMap();
        map.put("key", "c");
        row = new ResetterImpl_JavaBeanStub01();
        row.map = map;
        rowList.add(row);
        form.rowList = rowList;
        ActionReset actionReset = new ActionReset();
        FieldReset fieldReset = new FieldReset();
        fieldReset.setFieldName("rowList.map(key)");
        fieldReset.setSelect(false);
        actionReset.setFieldReset(fieldReset);
        stub01.actionReset = actionReset;

        ActionMapping mapping = new ActionMapping();
        HttpServletRequest req = new MockHttpServletRequest();

        // テスト実施
        stub01.reset(form, mapping, req);

        // 判定
        // TODO JXPathIndexedBeanWrapperImpl見直し後再テスト
        assertEquals(0, stub01.resetSelectFieldCount);
        assertEquals(3, stub01.resetValueCount);
        // 1回目の呼び出し。
        assertSame(form, stub01.resetValueArg0.get(0));
        Entry entry = stub01.resetValueArg1.get(0);
        assertEquals("rowList[0].map(key)", entry.getKey());
        assertEquals("a", entry.getValue());
        // 2回目の呼び出し。
        assertSame(form, stub01.resetValueArg0.get(1));
        entry = stub01.resetValueArg1.get(1);
        assertEquals("rowList[1].map(key)", entry.getKey());
        assertNull(entry.getValue());
        // 3回目の呼び出し。
        assertSame(form, stub01.resetValueArg0.get(2));
        entry = stub01.resetValueArg1.get(2);
        assertEquals("rowList[2].map(key)", entry.getKey());
        assertEquals("c", entry.getValue());

    }

    /**
     * testReset16()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) form:ValidatorActionFormExインスタンス<br>
     *                JavaBean row<br>
     *                  ＋JavaBean nestedRow<br>
     *                       ＋String value="test"<br>
     *         (引数) mapping:not null<br>
     *         (引数) request:not null<br>
     *         (状態) getActionResetの結果:{FieldReset(field="row.nestedRow.value",<br>
     *                select=false)}<br>
     *
     * <br>
     * 期待値：(状態変化) resetSelectField:呼び出されない<br>
     *         (状態変化) resetValue:form=引数のform、<br>
     *                    entry={row.nestedRow.value="test"}<br>
     *                    で呼び出されること。<br>
     *
     * <br>
     * リセット対象のフィールドが複数回想ネストしている場合、resetValueが呼び出されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testReset16() throws Exception {
        // 前処理
        ResetterImplStub01 stub01 = new ResetterImplStub01();
        ResetterImpl_ValidatorActionFormExStub01 form =
            new ResetterImpl_ValidatorActionFormExStub01();
        ResetterImpl_JavaBeanStub01 row = new ResetterImpl_JavaBeanStub01();
        ResetterImpl_JavaBeanStub01 nestedRow =
            new ResetterImpl_JavaBeanStub01();
        nestedRow.value = "test";
        row.nestedRow = nestedRow;
        form.row = row;
        ActionReset actionReset = new ActionReset();
        FieldReset fieldReset = new FieldReset();
        fieldReset.setFieldName("row.nestedRow.value");
        fieldReset.setSelect(false);
        actionReset.setFieldReset(fieldReset);
        stub01.actionReset = actionReset;

        ActionMapping mapping = new ActionMapping();
        HttpServletRequest req = new MockHttpServletRequest();

        // テスト実施
        stub01.reset(form, mapping, req);

        // 判定
        assertEquals(0, stub01.resetSelectFieldCount);
        assertEquals(1, stub01.resetValueCount);
        // 1回目の呼び出し。
        assertSame(form, stub01.resetValueArg0.get(0));
        Entry entry = stub01.resetValueArg1.get(0);
        assertEquals("row.nestedRow.value", entry.getKey());
        assertEquals("test", entry.getValue());

    }

    /**
     * testReset17()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) form:ValidatorActionFormExインスタンス<br>
     *                JavaBean[] rows[0]<br>
     *                  ＋ArrayList<JavaBean> nestedRows[0]<br>
     *                      ＋String value="test0_0"<br>
     *                  ＋ArrayList<JavaBean> nestedRows[1]<br>
     *                      ＋String value="test0_1"<br>
     *                  ＋ArrayList<JavaBean> nestedRows[2]<br>
     *                      ＋String value="test0_2"<br>
     *                JavaBean[] rows[1]<br>
     *                  ＋ArrayList<JavaBean> nestedRows[0]<br>
     *                      ＋String value="test1_0"<br>
     *                  ＋ArrayList<JavaBean> nestedRows[1]<br>
     *                      ＋String value=null<br>
     *                  ＋ArrayList<JavaBean> nestedRows[2]<br>
     *                      ＋String value="test1_2"<br>
     *         (引数) mapping:not null<br>
     *         (引数) request:not null<br>
     *         (状態) getActionResetの結果:{FieldReset(field="rows.nestedRows.value",<br>
     *                select=false)}<br>
     *
     * <br>
     * 期待値：(状態変化) resetSelectField:呼び出されない<br>
     *         (状態変化) resetValue:１：entry={rows[0].nestedRows[0].value="test0_0"}<br>
     *                    ２：entry={rows[0].nestedRows[1].value="test0_1"}<br>
     *                    ３：entry={rows[0].nestedRows[2].value="test0_2"}<br>
     *                    ４：entry={rows[1].nestedRows[0].value="test1_0"}<br>
     *                    ５：entry={rows[1].nestedRows[1].value=null}<br>
     *                    ６：entry={rows[1].nestedRows[2].value="test1_2"}<br>
     *                    で呼び出されること。<br>
     *                    ※formは全てについて引数のform<br>
     *
     * <br>
     * リセット対象のフィールドが複数回想ネストしている場合、resetValueが呼び出されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    @SuppressWarnings("unchecked")
    public void testReset17() throws Exception {
        // 前処理
        ResetterImplStub01 stub01 = new ResetterImplStub01();
        ResetterImpl_ValidatorActionFormExStub01 form =
            new ResetterImpl_ValidatorActionFormExStub01();
        ResetterImpl_JavaBeanStub01[] rows = {
            new ResetterImpl_JavaBeanStub01(),
            new ResetterImpl_JavaBeanStub01()
        };
        List nestedRows = new ArrayList();
        ResetterImpl_JavaBeanStub01 nestedRow =
            new ResetterImpl_JavaBeanStub01();
        nestedRow.value = "test0_0";
        nestedRows.add(nestedRow);
        nestedRow = new ResetterImpl_JavaBeanStub01();
        nestedRow.value = "test0_1";
        nestedRows.add(nestedRow);
        nestedRow = new ResetterImpl_JavaBeanStub01();
        nestedRow.value = "test0_2";
        nestedRows.add(nestedRow);
        rows[0].nestedRows = nestedRows;
        nestedRow = new ResetterImpl_JavaBeanStub01();
        nestedRows = new ArrayList();
        nestedRow.value = "test1_0";
        nestedRows.add(nestedRow);
        nestedRow = new ResetterImpl_JavaBeanStub01();
        nestedRow.value = null;
        nestedRows.add(nestedRow);
        nestedRow = new ResetterImpl_JavaBeanStub01();
        nestedRow.value = "test1_2";
        nestedRows.add(nestedRow);
        rows[1].nestedRows = nestedRows;
        form.rows = rows;

        ActionReset actionReset = new ActionReset();
        FieldReset fieldReset = new FieldReset();
        fieldReset.setFieldName("rows.nestedRows.value");
        fieldReset.setSelect(false);
        actionReset.setFieldReset(fieldReset);
        stub01.actionReset = actionReset;

        ActionMapping mapping = new ActionMapping();
        HttpServletRequest req = new MockHttpServletRequest();

        // テスト実施
        stub01.reset(form, mapping, req);

        // 判定
        assertEquals(0, stub01.resetSelectFieldCount);
        assertEquals(6, stub01.resetValueCount);
        // 1回目の呼び出し。
        assertSame(form, stub01.resetValueArg0.get(0));
        Entry entry = stub01.resetValueArg1.get(0);
        assertEquals("rows[0].nestedRows[0].value", entry.getKey());
        assertEquals("test0_0", entry.getValue());
        // 2回目の呼び出し。
        assertSame(form, stub01.resetValueArg0.get(1));
        entry = stub01.resetValueArg1.get(1);
        assertEquals("rows[0].nestedRows[1].value", entry.getKey());
        assertEquals("test0_1", entry.getValue());
        // 3回目の呼び出し。
        assertSame(form, stub01.resetValueArg0.get(2));
        entry = stub01.resetValueArg1.get(2);
        assertEquals("rows[0].nestedRows[2].value", entry.getKey());
        assertEquals("test0_2", entry.getValue());
        // 4回目の呼び出し。
        assertSame(form, stub01.resetValueArg0.get(3));
        entry = stub01.resetValueArg1.get(3);
        assertEquals("rows[1].nestedRows[0].value", entry.getKey());
        assertEquals("test1_0", entry.getValue());
        // 5回目の呼び出し。
        assertSame(form, stub01.resetValueArg0.get(4));
        entry = stub01.resetValueArg1.get(4);
        assertEquals("rows[1].nestedRows[1].value", entry.getKey());
        assertNull(entry.getValue());
        // 6回目の呼び出し。
        assertSame(form, stub01.resetValueArg0.get(5));
        entry = stub01.resetValueArg1.get(5);
        assertEquals("rows[1].nestedRows[2].value", entry.getKey());
        assertEquals("test1_2", entry.getValue());

    }

    /**
     * testReset18()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) form:DynaValidatorActionFormExインスタンス<br>
     *                String[] values = {"a",null,"c"}<br>
     *         (引数) mapping:not null<br>
     *         (引数) request:not null<br>
     *         (状態) getActionResetの結果:{FieldReset(field="values",select=true)}<br>
     *
     * <br>
     * 期待値：(状態変化) resetSelectField:form=引数のform、<br>
     *                    propMap={<br>
     *                      values[0]="a",<br>
     *                      values[1]=null,<br>
     *                      values[2]="c"}、<br>
     *                    request=引数のrequest<br>
     *                    で呼び出されること。<br>
     *         (状態変化) resetValue:呼び出されない<br>
     *
     * <br>
     * getActionResetで取得したActionResetがselect指定されており、該当フィールドが複数件ある場合、resetSelectFieldが呼び出されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testReset18() throws Exception {
        // 前処理
        ResetterImplStub01 stub01 = new ResetterImplStub01();
        String[] values = {
            "a",
            null,
            "c"
        };
        formEx.set("values", values);

        ActionReset actionReset = new ActionReset();
        FieldReset fieldReset = new FieldReset();
        fieldReset.setFieldName("values");
        fieldReset.setSelect(true);
        actionReset.setFieldReset(fieldReset);
        stub01.actionReset = actionReset;

        ActionMapping mapping = new ActionMapping();
        HttpServletRequest req = new MockHttpServletRequest();

        // テスト実施
        stub01.reset(formEx, mapping, req);

        // 判定
        assertEquals(1, stub01.resetSelectFieldCount);
        assertEquals(0, stub01.resetValueCount);
        // 1回目の呼び出し。
        assertSame(formEx, stub01.resetSelectFieldArg0.get(0));
        Map propMap = stub01.resetSelectFieldArg1.get(0);
        assertEquals(3, propMap.size());
        assertEquals("a", propMap.get("values[0]"));
        assertNull(propMap.get("values[1]"));
        assertEquals("c", propMap.get("values[2]"));
        assertSame(req, stub01.resetSelectFieldArg2.get(0));
    }

    /**
     * testReset19()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) form:DynaValidatorActionFormExインスタンス<br>
     *                String value = "a"<br>
     *         (引数) mapping:not null<br>
     *         (引数) request:not null<br>
     *         (状態) getActionResetの結果:{FieldReset(field="value",select=true)}<br>
     *
     * <br>
     * 期待値：(状態変化) resetSelectField:呼び出されない<br>
     *         (状態変化) resetValue:form=引数のform、<br>
     *                    entry={value="a"}<br>
     *                    で呼び出されること。<br>
     *
     * <br>
     * getActionResetで取得したActionResetがselect指定されているが、該当フィールドが1件しかない場合、resetValueが呼び出されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testReset19() throws Exception {
        // 前処理
        ResetterImplStub01 stub01 = new ResetterImplStub01();
        formEx.set("value", "a");

        ActionReset actionReset = new ActionReset();
        FieldReset fieldReset = new FieldReset();
        fieldReset.setFieldName("value");
        fieldReset.setSelect(true);
        actionReset.setFieldReset(fieldReset);
        stub01.actionReset = actionReset;

        ActionMapping mapping = new ActionMapping();
        HttpServletRequest req = new MockHttpServletRequest();

        // テスト実施
        stub01.reset(formEx, mapping, req);

        // 判定
        assertEquals(0, stub01.resetSelectFieldCount);
        assertEquals(1, stub01.resetValueCount);
        // 1回目の呼び出し。
        assertEquals(1, stub01.resetValueCount);
        assertSame(formEx, stub01.resetValueArg0.get(0));
        Entry entry = stub01.resetValueArg1.get(0);
        assertEquals("value", entry.getKey());
        assertEquals("a", entry.getValue());
    }

    /**
     * testReset20()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) form:DynaValidatorActionFormExインスタンス<br>
     *                String[] values = {"a",null,"c"}<br>
     *         (引数) mapping:not null<br>
     *         (引数) request:not null<br>
     *         (状態) getActionResetの結果:{FieldReset(field="values",select=false)}<br>
     *
     * <br>
     * 期待値：(状態変化) resetSelectField:呼び出されない<br>
     *         (状態変化) resetValue:１：entry={values[0]="a"}<br>
     *                    ２：entry={values[1]=null}<br>
     *                    ３：entry={values[2]="c"}<br>
     *                    で呼び出されること。<br>
     *                    ※formは全てについて引数のform<br>
     *
     * <br>
     * getActionResetで取得したActionResetがselect指定されてなく、該当フィールドが配列型の場合、要素数分、resetValueが呼び出されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testReset20() throws Exception {
        // 前処理
        ResetterImplStub01 stub01 = new ResetterImplStub01();
        String[] values = {
            "a",
            null,
            "c"
        };
        formEx.set("values", values);

        ActionReset actionReset = new ActionReset();
        FieldReset fieldReset = new FieldReset();
        fieldReset.setFieldName("values");
        fieldReset.setSelect(false);
        actionReset.setFieldReset(fieldReset);
        stub01.actionReset = actionReset;

        ActionMapping mapping = new ActionMapping();
        HttpServletRequest req = new MockHttpServletRequest();

        // テスト実施
        stub01.reset(formEx, mapping, req);

        // 判定
        assertEquals(0, stub01.resetSelectFieldCount);
        assertEquals(3, stub01.resetValueCount);
        // 1回目の呼び出し。
        assertSame(formEx, stub01.resetValueArg0.get(0));
        Entry entry = stub01.resetValueArg1.get(0);
        assertEquals("values[0]", entry.getKey());
        assertEquals("a", entry.getValue());
        // 2回目の呼び出し。
        assertSame(formEx, stub01.resetValueArg0.get(1));
        entry = stub01.resetValueArg1.get(1);
        assertEquals("values[1]", entry.getKey());
        assertNull(entry.getValue());
        // 1回目の呼び出し。
        assertSame(formEx, stub01.resetValueArg0.get(2));
        entry = stub01.resetValueArg1.get(2);
        assertEquals("values[2]", entry.getKey());
        assertEquals("c", entry.getValue());
    }

    /**
     * testReset21()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) form:DynaValidatorActionFormExインスタンス<br>
     *                ArrayList valueList = {"a",null,"c"}<br>
     *         (引数) mapping:not null<br>
     *         (引数) request:not null<br>
     *         (状態) getActionResetの結果:{FieldReset(field="valueList",select=false)}<br>
     *
     * <br>
     * 期待値：(状態変化) resetSelectField:呼び出されない<br>
     *         (状態変化) resetValue:１：entry={valueList[0]="a"}<br>
     *                    ２：entry={valueList[1]=null}<br>
     *                    ３：entry={valueList[2]="c"}<br>
     *                    で呼び出されること。<br>
     *                    ※formは全てについて引数のform<br>
     *
     * <br>
     * getActionResetで取得したActionResetがselect指定されてなく、該当フィールドがCollection型の場合、要素数分、resetValueが呼び出されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    @SuppressWarnings("unchecked")
    public void testReset21() throws Exception {
        // 前処理
        ResetterImplStub01 stub01 = new ResetterImplStub01();
        List valueList = new ArrayList();
        valueList.add("a");
        valueList.add(null);
        valueList.add("c");
        formEx.set("valueList", valueList);

        ActionReset actionReset = new ActionReset();
        FieldReset fieldReset = new FieldReset();
        fieldReset.setFieldName("valueList");
        fieldReset.setSelect(false);
        actionReset.setFieldReset(fieldReset);
        stub01.actionReset = actionReset;

        ActionMapping mapping = new ActionMapping();
        HttpServletRequest req = new MockHttpServletRequest();

        // テスト実施
        stub01.reset(formEx, mapping, req);

        // 判定
        assertEquals(0, stub01.resetSelectFieldCount);
        assertEquals(3, stub01.resetValueCount);
        // 1回目の呼び出し。
        assertSame(formEx, stub01.resetValueArg0.get(0));
        Entry entry = stub01.resetValueArg1.get(0);
        assertEquals("valueList[0]", entry.getKey());
        assertEquals("a", entry.getValue());
        // 2回目の呼び出し。
        assertSame(formEx, stub01.resetValueArg0.get(1));
        entry = stub01.resetValueArg1.get(1);
        assertEquals("valueList[1]", entry.getKey());
        assertNull(entry.getValue());
        // 3回目の呼び出し。
        assertSame(formEx, stub01.resetValueArg0.get(2));
        entry = stub01.resetValueArg1.get(2);
        assertEquals("valueList[2]", entry.getKey());
        assertEquals("c", entry.getValue());
    }

    /**
     * testReset22()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) form:DynaValidatorActionFormExインスタンス<br>
     *                HashMap map = {key="test"}<br>
     *         (引数) mapping:not null<br>
     *         (引数) request:not null<br>
     *         (状態) getActionResetの結果:{FieldReset(field="map(key)",select=false)}<br>
     *
     * <br>
     * 期待値：(状態変化) resetSelectField:呼び出されない<br>
     *         (状態変化) resetValue:form=引数のform、<br>
     *                    entry={map(key)="test"}<br>
     *                    で呼び出されること。<br>
     *
     * <br>
     * getActionResetで取得したActionResetがselect指定されてなく、該当フィールドがMap型の場合、resetValueが呼び出されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    @SuppressWarnings("unchecked")
    public void testReset22() throws Exception {
        // 前処理
        ResetterImplStub01 stub01 = new ResetterImplStub01();
        Map map = new HashMap();
        map.put("key", "test");
        formEx.set("map", map);

        ActionReset actionReset = new ActionReset();
        FieldReset fieldReset = new FieldReset();
        fieldReset.setFieldName("map(key)");
        fieldReset.setSelect(false);
        actionReset.setFieldReset(fieldReset);
        stub01.actionReset = actionReset;

        ActionMapping mapping = new ActionMapping();
        HttpServletRequest req = new MockHttpServletRequest();

        // テスト実施
        stub01.reset(formEx, mapping, req);

        // 判定
        assertEquals(0, stub01.resetSelectFieldCount);
        assertEquals(1, stub01.resetValueCount);
        // 1回目の呼び出し。
        assertSame(formEx, stub01.resetValueArg0.get(0));
        Entry entry = stub01.resetValueArg1.get(0);
        assertEquals("map(key)", entry.getKey());
        assertEquals("test", entry.getValue());
    }

    /**
     * testReset23()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) form:DynaValidatorActionFormExインスタンス<br>
     *                JavaBean row<br>
     *                  ＋String value = "a"<br>
     *         (引数) mapping:not null<br>
     *         (引数) request:not null<br>
     *         (状態) getActionResetの結果:{FieldReset(field="row.value",select=false)}<br>
     *
     * <br>
     * 期待値：(状態変化) resetSelectField:呼び出されない<br>
     *         (状態変化) resetValue:form=引数のform、<br>
     *                    entry={row.value="a"}<br>
     *                    で呼び出されること。<br>
     *
     * <br>
     * リセット対象のフィールドがネストしている場合、resetValueが呼び出されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testReset23() throws Exception {
        // 前処理
        ResetterImplStub01 stub01 = new ResetterImplStub01();
        ResetterImpl_JavaBeanStub01 row = new ResetterImpl_JavaBeanStub01();
        row.value = "a";
        formEx.set("row", row);

        ActionReset actionReset = new ActionReset();
        FieldReset fieldReset = new FieldReset();
        fieldReset.setFieldName("row.value");
        fieldReset.setSelect(false);
        actionReset.setFieldReset(fieldReset);
        stub01.actionReset = actionReset;

        ActionMapping mapping = new ActionMapping();
        HttpServletRequest req = new MockHttpServletRequest();

        // テスト実施
        stub01.reset(formEx, mapping, req);

        // 判定
        assertEquals(0, stub01.resetSelectFieldCount);
        assertEquals(1, stub01.resetValueCount);
        // 1回目の呼び出し。
        assertSame(formEx, stub01.resetValueArg0.get(0));
        Entry entry = stub01.resetValueArg1.get(0);
        assertEquals("row.value", entry.getKey());
        assertEquals("a", entry.getValue());
    }

    /**
     * testReset24()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) form:DynaValidatorActionFormExインスタンス<br>
     *                JavaBean row<br>
     *                  ＋String[] values = {"a",null,"c"}<br>
     *         (引数) mapping:not null<br>
     *         (引数) request:not null<br>
     *         (状態) getActionResetの結果:{FieldReset(field="row.values",select=false)}<br>
     *
     * <br>
     * 期待値：(状態変化) resetSelectField:呼び出されない<br>
     *         (状態変化) resetValue:１：entry={row.values[0]="a"}<br>
     *                    ２：entry={row.values[1]=null}<br>
     *                    ３：entry={row.values[2]="c"}<br>
     *                    で呼び出されること。<br>
     *                    ※formは全てについて引数のform<br>
     *
     * <br>
     * リセット対象のフィールドがネストした配列の場合、全ての要素についてresetValueが呼び出されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testReset24() throws Exception {
        // 前処理
        ResetterImplStub01 stub01 = new ResetterImplStub01();
        ResetterImpl_JavaBeanStub01 row = new ResetterImpl_JavaBeanStub01();
        String[] values = {
            "a",
            null,
            "c"
        };
        row.values = values;
        formEx.set("row", row);

        ActionReset actionReset = new ActionReset();
        FieldReset fieldReset = new FieldReset();
        fieldReset.setFieldName("row.values");
        fieldReset.setSelect(false);
        actionReset.setFieldReset(fieldReset);
        stub01.actionReset = actionReset;

        ActionMapping mapping = new ActionMapping();
        HttpServletRequest req = new MockHttpServletRequest();

        // テスト実施
        stub01.reset(formEx, mapping, req);

        // 判定
        assertEquals(0, stub01.resetSelectFieldCount);
        assertEquals(3, stub01.resetValueCount);
        // 1回目の呼び出し。
        assertSame(formEx, stub01.resetValueArg0.get(0));
        Entry entry = stub01.resetValueArg1.get(0);
        assertEquals("row.values[0]", entry.getKey());
        assertEquals("a", entry.getValue());
        // 2回目の呼び出し。
        assertSame(formEx, stub01.resetValueArg0.get(1));
        entry = stub01.resetValueArg1.get(1);
        assertEquals("row.values[1]", entry.getKey());
        assertNull(entry.getValue());
        // 3回目の呼び出し。
        assertSame(formEx, stub01.resetValueArg0.get(2));
        entry = stub01.resetValueArg1.get(2);
        assertEquals("row.values[2]", entry.getKey());
        assertEquals("c", entry.getValue());
    }

    /**
     * testReset25()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) form:DynaValidatorActionFormExインスタンス<br>
     *                JavaBean row<br>
     *                  ＋ArrayList valueList = {"a",null,"c"}<br>
     *         (引数) mapping:not null<br>
     *         (引数) request:not null<br>
     *         (状態) getActionResetの結果:{FieldReset(field="row.values",select=false)}<br>
     *
     * <br>
     * 期待値：(状態変化) resetSelectField:呼び出されない<br>
     *         (状態変化) resetValue:１：entry={row.values[0]="a"}<br>
     *                    ２：entry={row.values[1]=null}<br>
     *                    ３：entry={row.values[2]="c"}<br>
     *                    で呼び出されること。<br>
     *                    ※formは全てについて引数のform<br>
     *
     * <br>
     * リセット対象のフィールドがネストしたCollection型の場合、全ての要素についてresetValueが呼び出されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    @SuppressWarnings("unchecked")
    public void testReset25() throws Exception {
        // 前処理
        ResetterImplStub01 stub01 = new ResetterImplStub01();
        ResetterImpl_JavaBeanStub01 row = new ResetterImpl_JavaBeanStub01();
        List valueList = new ArrayList();
        valueList.add("a");
        valueList.add(null);
        valueList.add("c");
        row.valueList = valueList;
        formEx.set("row", row);

        ActionReset actionReset = new ActionReset();
        FieldReset fieldReset = new FieldReset();
        fieldReset.setFieldName("row.valueList");
        fieldReset.setSelect(false);
        actionReset.setFieldReset(fieldReset);
        stub01.actionReset = actionReset;

        ActionMapping mapping = new ActionMapping();
        HttpServletRequest req = new MockHttpServletRequest();

        // テスト実施
        stub01.reset(formEx, mapping, req);

        // 判定
        assertEquals(0, stub01.resetSelectFieldCount);
        assertEquals(3, stub01.resetValueCount);
        // 1回目の呼び出し。
        assertSame(formEx, stub01.resetValueArg0.get(0));
        Entry entry = stub01.resetValueArg1.get(0);
        assertEquals("row.valueList[0]", entry.getKey());
        assertEquals("a", entry.getValue());
        // 2回目の呼び出し。
        assertSame(formEx, stub01.resetValueArg0.get(1));
        entry = stub01.resetValueArg1.get(1);
        assertEquals("row.valueList[1]", entry.getKey());
        assertNull(entry.getValue());
        // 3回目の呼び出し。
        assertSame(formEx, stub01.resetValueArg0.get(2));
        entry = stub01.resetValueArg1.get(2);
        assertEquals("row.valueList[2]", entry.getKey());
        assertEquals("c", entry.getValue());
    }

    /**
     * testReset26()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) form:DynaValidatorActionFormExインスタンス<br>
     *                JavaBean row<br>
     *                  ＋HashMap map = {key="test"}<br>
     *         (引数) mapping:not null<br>
     *         (引数) request:not null<br>
     *         (状態) getActionResetの結果:{FieldReset(field="row.map(key)",select=false)}<br>
     *
     * <br>
     * 期待値：(状態変化) resetSelectField:呼び出されない<br>
     *         (状態変化) resetValue:form=引数のform、<br>
     *                    entry={row.map(key)="test"}<br>
     *                    で呼び出されること。<br>
     *
     * <br>
     * リセット対象のフィールドがネストしたMapの場合、resetValueが呼び出されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    @SuppressWarnings("unchecked")
    public void testReset26() throws Exception {
        // 前処理
        ResetterImplStub01 stub01 = new ResetterImplStub01();
        ResetterImpl_JavaBeanStub01 row = new ResetterImpl_JavaBeanStub01();
        Map map = new HashMap();
        map.put("key", "test");
        row.map = map;
        formEx.set("row", row);

        ActionReset actionReset = new ActionReset();
        FieldReset fieldReset = new FieldReset();
        fieldReset.setFieldName("row.map(key)");
        fieldReset.setSelect(false);
        actionReset.setFieldReset(fieldReset);
        stub01.actionReset = actionReset;

        ActionMapping mapping = new ActionMapping();
        HttpServletRequest req = new MockHttpServletRequest();

        // テスト実施
        stub01.reset(formEx, mapping, req);

        // 判定
        assertEquals(0, stub01.resetSelectFieldCount);
        assertEquals(1, stub01.resetValueCount);
        // 1回目の呼び出し。
        assertSame(formEx, stub01.resetValueArg0.get(0));
        Entry entry = stub01.resetValueArg1.get(0);
        assertEquals("row.map(key)", entry.getKey());
        assertEquals("test", entry.getValue());
    }

    /**
     * testReset27()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) form:DynaValidatorActionFormExインスタンス<br>
     *                JavaBean[] rows[0]<br>
     *                  ＋String value="a"<br>
     *                JavaBean[] rows[1]<br>
     *                  ＋String value=null<br>
     *                JavaBean[] rows[2]<br>
     *                  ＋String value="c"<br>
     *         (引数) mapping:not null<br>
     *         (引数) request:not null<br>
     *         (状態) getActionResetの結果:{FieldReset(field="rows.value",select=false)}<br>
     *
     * <br>
     * 期待値：(状態変化) resetSelectField:呼び出されない<br>
     *         (状態変化) resetValue:１：entry={rows[0].value="a"}<br>
     *                    ２：entry={rows[1].value=null}<br>
     *                    ３：entry={rows[2].value="c"}<br>
     *                    で呼び出されること。<br>
     *                    ※formは全てについて引数のform<br>
     *
     * <br>
     * リセット対象のフィールドが配列のネストした属性の場合、すべての要素についてresetValueが呼び出されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testReset27() throws Exception {
        // 前処理
        ResetterImplStub01 stub01 = new ResetterImplStub01();
        ResetterImpl_JavaBeanStub01[] rows = {
            new ResetterImpl_JavaBeanStub01(),
            new ResetterImpl_JavaBeanStub01(),
            new ResetterImpl_JavaBeanStub01()
        };
        rows[0].value = "a";
        rows[1].value = null;
        rows[2].value = "c";
        formEx.set("rows", rows);


        ActionReset actionReset = new ActionReset();
        FieldReset fieldReset = new FieldReset();
        fieldReset.setFieldName("rows.value");
        fieldReset.setSelect(false);
        actionReset.setFieldReset(fieldReset);
        stub01.actionReset = actionReset;

        ActionMapping mapping = new ActionMapping();
        HttpServletRequest req = new MockHttpServletRequest();

        // テスト実施
        stub01.reset(formEx, mapping, req);

        // 判定
        assertEquals(0, stub01.resetSelectFieldCount);
        assertEquals(3, stub01.resetValueCount);
        // 1回目の呼び出し。
        assertSame(formEx, stub01.resetValueArg0.get(0));
        Entry entry = stub01.resetValueArg1.get(0);
        assertEquals("rows[0].value", entry.getKey());
        assertEquals("a", entry.getValue());
        // 2回目の呼び出し。
        assertSame(formEx, stub01.resetValueArg0.get(1));
        entry = stub01.resetValueArg1.get(1);
        assertEquals("rows[1].value", entry.getKey());
        assertNull(entry.getValue());
        // 3回目の呼び出し。
        assertSame(formEx, stub01.resetValueArg0.get(2));
        entry = stub01.resetValueArg1.get(2);
        assertEquals("rows[2].value", entry.getKey());
        assertEquals("c", entry.getValue());
    }

    /**
     * testReset28()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) form:DynaValidatorActionFormExインスタンス<br>
     *                ArrayList<JavaBean> rowList[0]<br>
     *                  ＋String value="a"<br>
     *                ArrayList<JavaBean> rowList[1]<br>
     *                  ＋String value=null<br>
     *                ArrayList<JavaBean> rowList[2]<br>
     *                  ＋String value="c"<br>
     *         (引数) mapping:not null<br>
     *         (引数) request:not null<br>
     *         (状態) getActionResetの結果:{FieldReset(field="rowList.value",select=false)}<br>
     *
     * <br>
     * 期待値：(状態変化) resetSelectField:呼び出されない<br>
     *         (状態変化) resetValue:１：entry={rowList[0].value="a"}<br>
     *                    ２：entry={rowList[1].value=null}<br>
     *                    ３：entry={rowList[2].value="c"}<br>
     *                    で呼び出されること。<br>
     *                    ※formは全てについて引数のform<br>
     *
     * <br>
     * リセット対象のフィールドがCollection型のネストした属性の場合、すべての要素についてresetValueが呼び出されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    @SuppressWarnings("unchecked")
    public void testReset28() throws Exception {
        // 前処理
        ResetterImplStub01 stub01 = new ResetterImplStub01();
        List rowList = new ArrayList();
        ResetterImpl_JavaBeanStub01 row = new ResetterImpl_JavaBeanStub01();
        row.value = "a";
        rowList.add(row);
        row = new ResetterImpl_JavaBeanStub01();
        row.value = null;
        rowList.add(row);
        row = new ResetterImpl_JavaBeanStub01();
        row.value = "c";
        rowList.add(row);
        formEx.set("rowList", rowList);

        ActionReset actionReset = new ActionReset();
        FieldReset fieldReset = new FieldReset();
        fieldReset.setFieldName("rowList.value");
        fieldReset.setSelect(false);
        actionReset.setFieldReset(fieldReset);
        stub01.actionReset = actionReset;

        ActionMapping mapping = new ActionMapping();
        HttpServletRequest req = new MockHttpServletRequest();

        // テスト実施
        stub01.reset(formEx, mapping, req);

        // 判定
        assertEquals(0, stub01.resetSelectFieldCount);
        assertEquals(3, stub01.resetValueCount);
        // 1回目の呼び出し。
        assertSame(formEx, stub01.resetValueArg0.get(0));
        Entry entry = stub01.resetValueArg1.get(0);
        assertEquals("rowList[0].value", entry.getKey());
        assertEquals("a", entry.getValue());
        // 2回目の呼び出し。
        assertSame(formEx, stub01.resetValueArg0.get(1));
        entry = stub01.resetValueArg1.get(1);
        assertEquals("rowList[1].value", entry.getKey());
        assertNull(entry.getValue());
        // 3回目の呼び出し。
        assertSame(formEx, stub01.resetValueArg0.get(2));
        entry = stub01.resetValueArg1.get(2);
        assertEquals("rowList[2].value", entry.getKey());
        assertEquals("c", entry.getValue());
    }

    /**
     * testReset29()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) form:DynaValidatorActionFormExインスタンス<br>
     *                JavaBean[] rows[0]<br>
     *                  ＋Map map={key="a"}<br>
     *                JavaBean[] rows[1]<br>
     *                  ＋Map map={key=null}<br>
     *                JavaBean[] rows[2]<br>
     *                  ＋Map map={key="c"}<br>
     *         (引数) mapping:not null<br>
     *         (引数) request:not null<br>
     *         (状態) getActionResetの結果:{FieldReset(field="rows.map(key)",select=false)}<br>
     *
     * <br>
     * 期待値：(状態変化) resetSelectField:呼び出されない<br>
     *         (状態変化) resetValue:１：entry={rows[0]map(key)="a"}<br>
     *                    ２：entry={rows[1].map(key)=null}<br>
     *                    ３：entry={rows[2].map(key)="c"}<br>
     *                    で呼び出されること。<br>
     *                    ※formは全てについて引数のform<br>
     *
     * <br>
     * リセット対象のフィールドが配列のネストしたMap型属性の場合、すべての要素についてresetValueが呼び出されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    @SuppressWarnings("unchecked")
    public void testReset29() throws Exception {
        // 前処理
        ResetterImplStub01 stub01 = new ResetterImplStub01();
        ResetterImpl_JavaBeanStub01[] rows = {
            new ResetterImpl_JavaBeanStub01(),
            new ResetterImpl_JavaBeanStub01(),
            new ResetterImpl_JavaBeanStub01()
        };
        Map map = new HashMap();
        map.put("key", "a");
        rows[0].map = map;
        map = new HashMap();
        map.put("key", null);
        rows[1].map = map;
        map = new HashMap();
        map.put("key", "c");
        rows[2].map = map;
        formEx.set("rows", rows);


        ActionReset actionReset = new ActionReset();
        FieldReset fieldReset = new FieldReset();
        fieldReset.setFieldName("rows.map(key)");
        fieldReset.setSelect(false);
        actionReset.setFieldReset(fieldReset);
        stub01.actionReset = actionReset;

        ActionMapping mapping = new ActionMapping();
        HttpServletRequest req = new MockHttpServletRequest();

        // テスト実施
        // TODO JXPathIndexedBeanWrapperImpl見直し後再テスト
        stub01.reset(formEx, mapping, req);

        // 判定
        assertEquals(0, stub01.resetSelectFieldCount);
        assertEquals(3, stub01.resetValueCount);
        // 1回目の呼び出し。
        assertSame(formEx, stub01.resetValueArg0.get(0));
        Entry entry = stub01.resetValueArg1.get(0);
        assertEquals("rows[0].map(key)", entry.getKey());
        assertEquals("a", entry.getValue());
        // 2回目の呼び出し。
        assertSame(formEx, stub01.resetValueArg0.get(1));
        entry = stub01.resetValueArg1.get(1);
        assertEquals("rows[1].map(key)", entry.getKey());
        assertNull(entry.getValue());
        // 3回目の呼び出し。
        assertSame(formEx, stub01.resetValueArg0.get(2));
        entry = stub01.resetValueArg1.get(2);
        assertEquals("rows[2].map(key)", entry.getKey());
        assertEquals("c", entry.getValue());
    }

    /**
     * testReset30()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) form:DynaValidatorActionFormExインスタンス<br>
     *                ArrayList<JavaBean> rowList[0]<br>
     *                  ＋Map map={key="a"}<br>
     *                ArrayList<JavaBean> rowList[1]<br>
     *                  ＋Map map={key=null}<br>
     *                ArrayList<JavaBean> rowList[2]<br>
     *                  ＋Map map={key="c"}<br>
     *         (引数) mapping:not null<br>
     *         (引数) request:not null<br>
     *         (状態) getActionResetの結果:{FieldReset(field="rowList.map(key)",select=false)}<br>
     *
     * <br>
     * 期待値：(状態変化) resetSelectField:呼び出されない<br>
     *         (状態変化) resetValue:１：entry={rowList[0].value="a"}<br>
     *                    ２：entry={rowList[1].value=null}<br>
     *                    ３：entry={rowList[2].value="c"}<br>
     *                    で呼び出されること。<br>
     *                    ※formは全てについて引数のform<br>
     *
     * <br>
     * リセット対象のフィールドが配列のネストしたMap型属性の場合、すべての要素についてresetValueが呼び出されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    @SuppressWarnings("unchecked")
    public void testReset30() throws Exception {
        // 前処理
        ResetterImplStub01 stub01 = new ResetterImplStub01();
        List rowList = new ArrayList();
        ResetterImpl_JavaBeanStub01 row = new ResetterImpl_JavaBeanStub01();
        Map map = new HashMap();
        map.put("key", "a");
        row.map = map;
        rowList.add(row);
        row = new ResetterImpl_JavaBeanStub01();
        map = new HashMap();
        map.put("key", null);
        row.map = map;
        rowList.add(row);
        row = new ResetterImpl_JavaBeanStub01();
        map = new HashMap();
        map.put("key", "c");
        row.map = map;
        rowList.add(row);
        formEx.set("rowList", rowList);

        ActionReset actionReset = new ActionReset();
        FieldReset fieldReset = new FieldReset();
        fieldReset.setFieldName("rowList.map(key)");
        fieldReset.setSelect(false);
        actionReset.setFieldReset(fieldReset);
        stub01.actionReset = actionReset;

        ActionMapping mapping = new ActionMapping();
        HttpServletRequest req = new MockHttpServletRequest();

        // テスト実施
        // TODO JXPathIndexedBeanWrapperImpl見直し後再テスト
        stub01.reset(formEx, mapping, req);

        // 判定
        assertEquals(0, stub01.resetSelectFieldCount);
        assertEquals(3, stub01.resetValueCount);
        // 1回目の呼び出し。
        assertSame(formEx, stub01.resetValueArg0.get(0));
        Entry entry = stub01.resetValueArg1.get(0);
        assertEquals("rowList[0].map(key)", entry.getKey());
        assertEquals("a", entry.getValue());
        // 2回目の呼び出し。
        assertSame(formEx, stub01.resetValueArg0.get(1));
        entry = stub01.resetValueArg1.get(1);
        assertEquals("rowList[1].map(key)", entry.getKey());
        assertNull(entry.getValue());
        // 3回目の呼び出し。
        assertSame(formEx, stub01.resetValueArg0.get(2));
        entry = stub01.resetValueArg1.get(2);
        assertEquals("rowList[2].map(key)", entry.getKey());
        assertEquals("c", entry.getValue());
    }

    /**
     * testReset31()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) form:DynaValidatorActionFormExインスタンス<br>
     *                JavaBean row<br>
     *                  ＋JavaBean nestedRow<br>
     *                       ＋String value="test"<br>
     *         (引数) mapping:not null<br>
     *         (引数) request:not null<br>
     *         (状態) getActionResetの結果:{FieldReset(field="row.nestedRow.value",<br>
     *                select=false)}<br>
     *
     * <br>
     * 期待値：(状態変化) resetSelectField:呼び出されない<br>
     *         (状態変化) resetValue:form=引数のform、<br>
     *                    entry={row.nestedRow.value="test"}<br>
     *                    で呼び出されること。<br>
     *
     * <br>
     * リセット対象のフィールドが複数回想ネストしている場合、resetValueが呼び出されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testReset31() throws Exception {
        // 前処理
        ResetterImplStub01 stub01 = new ResetterImplStub01();
        ResetterImpl_JavaBeanStub01 row = new ResetterImpl_JavaBeanStub01();
        ResetterImpl_JavaBeanStub01 nestedRow =
            new ResetterImpl_JavaBeanStub01();
        nestedRow.value = "test";
        row.nestedRow = nestedRow;
        formEx.set("row", row);

        ActionReset actionReset = new ActionReset();
        FieldReset fieldReset = new FieldReset();
        fieldReset.setFieldName("row.nestedRow.value");
        fieldReset.setSelect(false);
        actionReset.setFieldReset(fieldReset);
        stub01.actionReset = actionReset;

        ActionMapping mapping = new ActionMapping();
        HttpServletRequest req = new MockHttpServletRequest();

        // テスト実施
        stub01.reset(formEx, mapping, req);

        // 判定
        assertEquals(0, stub01.resetSelectFieldCount);
        assertEquals(1, stub01.resetValueCount);
        // 1回目の呼び出し。
        assertSame(formEx, stub01.resetValueArg0.get(0));
        Entry entry = stub01.resetValueArg1.get(0);
        assertEquals("row.nestedRow.value", entry.getKey());
        assertEquals("test", entry.getValue());
    }

    /**
     * testReset32()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) form:DynaValidatorActionFormExインスタンス<br>
     *                JavaBean[] rows[0]<br>
     *                  ＋ArrayList<JavaBean> nestedRows[0]<br>
     *                      ＋String value="test0_0"<br>
     *                  ＋ArrayList<JavaBean> nestedRows[1]<br>
     *                      ＋String value="test0_1"<br>
     *                  ＋ArrayList<JavaBean> nestedRows[2]<br>
     *                      ＋String value="test0_2"<br>
     *                JavaBean[] rows[1]<br>
     *                  ＋ArrayList<JavaBean> nestedRows[0]<br>
     *                      ＋String value="test1_0"<br>
     *                  ＋ArrayList<JavaBean> nestedRows[1]<br>
     *                      ＋String value=null<br>
     *                  ＋ArrayList<JavaBean> nestedRows[2]<br>
     *                      ＋String value="test1_2"<br>
     *         (引数) mapping:not null<br>
     *         (引数) request:not null<br>
     *         (状態) getActionResetの結果:{FieldReset(field="rows.nestedRows.value",<br>
     *                select=false)}<br>
     *
     * <br>
     * 期待値：(状態変化) resetSelectField:呼び出されない<br>
     *         (状態変化) resetValue:１：entry={rows[0].nestedRows[0].value="test0_0"}<br>
     *                    ２：entry={rows[0].nestedRows[1].value="test0_1"}<br>
     *                    ３：entry={rows[0].nestedRows[2].value="test0_2"}<br>
     *                    ４：entry={rows[1].nestedRows[0].value="test1_0"}<br>
     *                    ５：entry={rows[1].nestedRows[1].value=null}<br>
     *                    ６：entry={rows[1].nestedRows[2].value="test1_2"}<br>
     *                    で呼び出されること。<br>
     *                    ※formは全てについて引数のform<br>
     *
     * <br>
     * リセット対象のフィールドが複数回想ネストしている場合、resetValueが呼び出されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    @SuppressWarnings("unchecked")
    public void testReset32() throws Exception {
        // 前処理
        ResetterImplStub01 stub01 = new ResetterImplStub01();
        ResetterImpl_JavaBeanStub01[] rows = {
            new ResetterImpl_JavaBeanStub01(),
            new ResetterImpl_JavaBeanStub01()
        };
        List nestedRows1 = new ArrayList();
        ResetterImpl_JavaBeanStub01 nestedRow =
            new ResetterImpl_JavaBeanStub01();
        nestedRow.value = "test0_0";
        nestedRows1.add(nestedRow);
        nestedRow = new ResetterImpl_JavaBeanStub01();
        nestedRow.value = "test0_1";
        nestedRows1.add(nestedRow);
        nestedRow = new ResetterImpl_JavaBeanStub01();
        nestedRow.value = "test0_2";
        nestedRows1.add(nestedRow);
        rows[0].nestedRows = nestedRows1;

        List nestedRows2 = new ArrayList();
        nestedRow =
            new ResetterImpl_JavaBeanStub01();
        nestedRow.value = "test1_0";
        nestedRows2.add(nestedRow);
        nestedRow = new ResetterImpl_JavaBeanStub01();
        nestedRow.value = null;
        nestedRows2.add(nestedRow);
        nestedRow = new ResetterImpl_JavaBeanStub01();
        nestedRow.value = "test1_2";
        nestedRows2.add(nestedRow);
        rows[1].nestedRows = nestedRows2;

        formEx.set("rows", rows);

        ActionReset actionReset = new ActionReset();
        FieldReset fieldReset = new FieldReset();
        fieldReset.setFieldName("rows.nestedRows.value");
        fieldReset.setSelect(false);
        actionReset.setFieldReset(fieldReset);
        stub01.actionReset = actionReset;

        ActionMapping mapping = new ActionMapping();
        HttpServletRequest req = new MockHttpServletRequest();

        // テスト実施
        stub01.reset(formEx, mapping, req);

        // 判定
        assertEquals(0, stub01.resetSelectFieldCount);
        assertEquals(6, stub01.resetValueCount);
        // 1回目の呼び出し。
        assertSame(formEx, stub01.resetValueArg0.get(0));
        Entry entry = stub01.resetValueArg1.get(0);
        assertEquals("rows[0].nestedRows[0].value", entry.getKey());
        assertEquals("test0_0", entry.getValue());
        // 2回目の呼び出し。
        assertSame(formEx, stub01.resetValueArg0.get(1));
        entry = stub01.resetValueArg1.get(1);
        assertEquals("rows[0].nestedRows[1].value", entry.getKey());
        assertEquals("test0_1", entry.getValue());
        // 3回目の呼び出し。
        assertSame(formEx, stub01.resetValueArg0.get(2));
        entry = stub01.resetValueArg1.get(2);
        assertEquals("rows[0].nestedRows[2].value", entry.getKey());
        assertEquals("test0_2", entry.getValue());
        // 4回目の呼び出し。
        assertSame(formEx, stub01.resetValueArg0.get(3));
        entry = stub01.resetValueArg1.get(3);
        assertEquals("rows[1].nestedRows[0].value", entry.getKey());
        assertEquals("test1_0", entry.getValue());
        // 5回目の呼び出し。
        assertSame(formEx, stub01.resetValueArg0.get(4));
        entry = stub01.resetValueArg1.get(4);
        assertEquals("rows[1].nestedRows[1].value", entry.getKey());
        assertNull(entry.getValue());
        // 6回目の呼び出し。
        assertSame(formEx, stub01.resetValueArg0.get(5));
        entry = stub01.resetValueArg1.get(5);
        assertEquals("rows[1].nestedRows[2].value", entry.getKey());
        assertEquals("test1_2", entry.getValue());
    }

    /**
     * testResetValue01()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：C,F
     * <br><br>
     * 入力値：(引数) form:ValidatorActionFormExインスタンス<br>
     *                hogeプロパティが存在しない<br>
     *         (引数) entry:hoge=null<br>
     *
     * <br>
     * 期待値：
     * <br>
     * entryの値がnullの場合、何もせず処理が終了することを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    @SuppressWarnings("unchecked")
    public void testResetValue01() throws Exception {
        // 前処理
        ResetterImpl test = new ResetterImpl();
        ResetterImpl_ValidatorActionFormExStub01 form =
            new ResetterImpl_ValidatorActionFormExStub01();
        ResetterImpl_EntryStub01 entry = new ResetterImpl_EntryStub01();
        entry.key = "hoge";
        entry.value = null;

        // テスト実施
        test.resetValue(form, entry);

    }

    /**
     * testResetValue02()
     * <br><br>
     *
     * (異常系)
     * <br>
     * 観点：G
     * <br><br>
     * 入力値：(引数) form:ValidatorActionFormExインスタンス<br>
     *                hogeプロパティが存在しない<br>
     *         (引数) entry:hoge="hoge"<br>
     *
     * <br>
     * 期待値：(状態変化) ログ:ログレベル：エラー<br>
     *                    メッセージ："cannot access property " + form + ".hoge"<br>
     *                    例外：PropertyAccessException<br>
     *
     * <br>
     * プロパティの初期化に失敗した場合、エラーレベルのログを出力することを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    @SuppressWarnings("unchecked")
    public void testResetValue02() throws Exception {
        // 前処理
        ResetterImpl test = new ResetterImpl();
        ResetterImpl_ValidatorActionFormExStub01 form =
            new ResetterImpl_ValidatorActionFormExStub01();
        ResetterImpl_EntryStub01 entry = new ResetterImpl_EntryStub01();
        entry.key = "hoge";
        entry.value = "hoge";

        // テスト実施
        test.resetValue(form, entry);

        // 判定
        assertTrue(LogUTUtil.checkError("cannot access property " + form
                + ".hoge", new PropertyAccessException(new NoSuchMethodException())));

    }

    /**
     * testResetValue03()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) form:ValidatorActionFormExインスタンス<br>
     *                Boolean booleanValue1 = Boolean.TRUE<br>
     *         (引数) entry:booleanValue1=Boolean.TRUE<br>
     *
     * <br>
     * 期待値：(状態変化) form:booleanValue1 = Boolean.FALSE<br>
     *
     * <br>
     * プロパティの型がBoolean型の場合、値がBoolean.FALSEに設定されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    @SuppressWarnings("unchecked")
    public void testResetValue03() throws Exception {
        // 前処理
        ResetterImpl test = new ResetterImpl();
        ResetterImpl_ValidatorActionFormExStub01 form =
            new ResetterImpl_ValidatorActionFormExStub01();
        form.booleanValue1 = Boolean.TRUE;
        ResetterImpl_EntryStub01 entry = new ResetterImpl_EntryStub01();
        entry.key = "booleanValue1";
        entry.value = Boolean.TRUE;

        // テスト実施
        test.resetValue(form, entry);

        // 判定
        assertEquals(Boolean.FALSE, form.getBooleanValue1());

    }

    /**
     * testResetValue04()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) form:DynaValidatorActionFormExインスタンス<br>
     *                boolean booleanValue2 = true;<br>
     *         (引数) entry:booleanValue2=Boolean.TRUE<br>
     *
     * <br>
     * 期待値：(状態変化) form:booleanValue2=false<br>
     *
     * <br>
     * プロパティの型がboolean型の場合、値がBoolean.FALSEに設定されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    @SuppressWarnings("unchecked")
    public void testResetValue04() throws Exception {
        // 前処理
        ResetterImpl test = new ResetterImpl();
        formEx.set("booleanValue2", true);
        ResetterImpl_EntryStub01 entry = new ResetterImpl_EntryStub01();
        entry.key = "booleanValue2";
        entry.value = Boolean.TRUE;

        // テスト実施
        test.resetValue(formEx, entry);

        // 判定
        assertEquals(Boolean.FALSE, formEx.get("booleanValue2"));

    }

    /**
     * testResetValue05()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) form:ValidatorActionFormExインスタンス<br>
     *                Byte[] byteArray1 = {<br>
     *                 0, 1, 2<br>
     *                }<br>
     *         (引数) entry:byteArray1[1]=1<br>
     *
     * <br>
     * 期待値：(状態変化) form:byteArray1={<br>
     *                     0, 0, 2<br>
     *                    }<br>
     *
     * <br>
     * プロパティの型がByte型の場合、値が0に設定されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    @SuppressWarnings("unchecked")
    public void testResetValue05() throws Exception {
        // 前処理
        ResetterImpl test = new ResetterImpl();
        ResetterImpl_ValidatorActionFormExStub01 form =
            new ResetterImpl_ValidatorActionFormExStub01();
        Byte[] byteArray1 = {
            new Byte((byte) 0),
            new Byte((byte) 1),
            new Byte((byte) 2)
        };
        form.byteArray1 = byteArray1;
        ResetterImpl_EntryStub01 entry = new ResetterImpl_EntryStub01();
        entry.key = "byteArray1[1]";
        entry.value = new Byte((byte) 1);

        // テスト実施
        test.resetValue(form, entry);

        // 判定
        Byte[] result = form.getByteArray1();
        assertEquals((byte) 0, result[0].byteValue());
        assertEquals((byte) 0, result[1].byteValue());
        assertEquals((byte) 2, result[2].byteValue());

    }

    /**
     * testResetValue06()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) form:DynaValidatorActionFormExインスタンス<br>
     *                byte[] byteArray2 = {<br>
     *                 0, 1, 2<br>
     *                }<br>
     *         (引数) entry:byteArray2[1]=1<br>
     *
     * <br>
     * 期待値：(状態変化) form:byteArray2={<br>
     *                     0, 0, 2<br>
     *                    }<br>
     *
     * <br>
     * プロパティの型がbyte型の場合、値が0に設定されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    @SuppressWarnings("unchecked")
    public void testResetValue06() throws Exception {
        // 前処理
        ResetterImpl test = new ResetterImpl();
        byte[] byteArray2 = {
            (byte) 0,
            (byte) 1,
            (byte) 2
        };
        formEx.set("byteArray2", byteArray2);
        ResetterImpl_EntryStub01 entry = new ResetterImpl_EntryStub01();
        entry.key = "byteArray2[1]";
        entry.value = new Byte((byte) 1);

        // テスト実施
        test.resetValue(formEx, entry);

        // 判定
        byte[] result = (byte[]) formEx.get("byteArray2");
        assertEquals((byte) 0, result[0]);
        assertEquals((byte) 0, result[1]);
        assertEquals((byte) 2, result[2]);

    }

    /**
     * testResetValue07()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) form:VlidatorActionFormExインスタンス<br>
     *                ArrayList charList1 = {<br>
     *                 Character('a'),<br>
     *                 Character('b'),<br>
     *                 Character('c')<br>
     *                }<br>
     *         (引数) entry:charList1[1]='b'<br>
     *
     * <br>
     * 期待値：(状態変化) form:ArrayList charList1 = {<br>
     *                     Character('a'),<br>
     *                     (Character) 0,<br>
     *                     Character('c')<br>
     *                    }<br>
     *
     * <br>
     * プロパティの型がCharacter型の場合、値が0に設定されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    @SuppressWarnings("unchecked")
    public void testResetValue07() throws Exception {
        // 前処理
        ResetterImpl test = new ResetterImpl();
        ResetterImpl_ValidatorActionFormExStub01 form =
            new ResetterImpl_ValidatorActionFormExStub01();
        List charList1 = new ArrayList();
        charList1.add(new Character('a'));
        charList1.add(new Character('b'));
        charList1.add(new Character('c'));
        form.charList1 = charList1;
        ResetterImpl_EntryStub01 entry = new ResetterImpl_EntryStub01();
        entry.key = "charList1[1]";
        entry.value = new Character((char) 1);

        // テスト実施
        test.resetValue(form, entry);

        // 判定
        List<Character> result = form.charList1;
        assertEquals('a', result.get(0).charValue());
        assertEquals((char) 0, result.get(1).charValue());
        assertEquals('c', result.get(2).charValue());

    }

    /**
     * testResetValue08()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) form:DynaValidatorActionFormExインスタンス<br>
     *                char charValue1 = 'a'<br>
     *         (引数) entry:charValue1='a'<br>
     *
     * <br>
     * 期待値：(状態変化) form:charValue1=0<br>
     *
     * <br>
     * プロパティの型がchar型の場合、値が0に設定されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    @SuppressWarnings("unchecked")
    public void testResetValue08() throws Exception {
        // 前処理
        ResetterImpl test = new ResetterImpl();
        formEx.set("charValue1", new Character('a'));
        ResetterImpl_EntryStub01 entry = new ResetterImpl_EntryStub01();
        entry.key = "charValue1";
        entry.value = new Character('a');

        // テスト実施
        test.resetValue(formEx, entry);

        // 判定
        assertEquals(new Character((char) 0), formEx.get("charValue1"));

    }

    /**
     * testResetValue09()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) form:ValidatorActionFormExインスタンス<br>
     *                Map map = {<br>
     *                 Double doubleValue1=4.9E-324<br>
     *                }<br>
     *         (引数) entry:map(doubleValue1)=4.9E-324<br>
     *
     * <br>
     * 期待値：(状態変化) form:Map map = {<br>
     *                     doubleValue1=0.0<br>
     *                    }<br>
     *
     * <br>
     * プロパティの型がDouble型の場合、値が0に設定されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    @SuppressWarnings("unchecked")
    public void testResetValue09() throws Exception {
        // 前処理
        ResetterImpl test = new ResetterImpl();
        ResetterImpl_ValidatorActionFormExStub01 form =
            new ResetterImpl_ValidatorActionFormExStub01();
        Map map = new HashMap();
        map.put("doubleValue1", new Double(4.9E-324));
        form.map = map;
        ResetterImpl_EntryStub01 entry = new ResetterImpl_EntryStub01();
        entry.key = "map.doubleValue1";
        entry.value = new Double(4.9E-324);

        // テスト実施
        test.resetValue(form, entry);

        // 判定
        Map result = form.map;
        assertEquals(0.0, result.get("doubleValue1"));

    }

    /**
     * testResetValue10()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) form:DynaValidatorActionFormExインスタンス<br>
     *                double doubleValue2 = 4.9E-324<br>
     *         (引数) entry:doubleValue2=4.9E-324<br>
     *
     * <br>
     * 期待値：(状態変化) form:doubleValue2=0.0<br>
     *
     * <br>
     * プロパティの型がdouble型の場合、値が0に設定されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    @SuppressWarnings("unchecked")
    public void testResetValue10() throws Exception {
        // 前処理
        ResetterImpl test = new ResetterImpl();
        formEx.set("doubleValue2", new Double(4.9E-324));
        ResetterImpl_EntryStub01 entry = new ResetterImpl_EntryStub01();
        entry.key = "doubleValue2";
        entry.value = new Double(4.9E-324);

        // テスト実施
        test.resetValue(formEx, entry);

        // 判定
        assertEquals(0.0,
                formEx.get("doubleValue2"));

    }

    /**
     * testResetValue11()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) form:ValidatorActionFormExインスタンス<br>
     *                JavaBean row = new JavaBean()<br>
     *                 ＋Float floatValue1 = 3.4028235E38<br>
     *         (引数) entry:row.floatValue1=3.4028235E38<br>
     *
     * <br>
     * 期待値：(状態変化) form:row.floatValue1=0.0<br>
     *
     * <br>
     * プロパティの型がFloat型の場合、値が0に設定されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    @SuppressWarnings("unchecked")
    public void testResetValue11() throws Exception {
        // 前処理
        ResetterImpl test = new ResetterImpl();
        ResetterImpl_ValidatorActionFormExStub01 form =
            new ResetterImpl_ValidatorActionFormExStub01();
        ResetterImpl_JavaBeanStub01 row = new ResetterImpl_JavaBeanStub01();
        row.floatValue1 = new Float(3.4028235E38);
        form.row = row;
        ResetterImpl_EntryStub01 entry = new ResetterImpl_EntryStub01();
        entry.key = "row.floatValue1";
        entry.value = new Float(3.4028235E38);

        // テスト実施
        test.resetValue(form, entry);

        // 判定
        assertEquals((float) 0.0, form.row.floatValue1);

    }

    /**
     * testResetValue12()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) form:DynaValidatorActionFormExインスタンス<br>
     *                JavaBean row = new JavaBean()<br>
     *                 ＋float floatValue2 = 3.4028235E38<br>
     *         (引数) entry:row.floatValue2=3.4028235E38<br>
     *
     * <br>
     * 期待値：(状態変化) form:row.floatValue2=0.0<br>
     *
     * <br>
     * プロパティの型がfloat型の場合、値が0に設定されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    @SuppressWarnings("unchecked")
    public void testResetValue12() throws Exception {
        // 前処理
        ResetterImpl test = new ResetterImpl();
        formEx.set("floatValue2", new Float(3.4028235E38));
        ResetterImpl_EntryStub01 entry = new ResetterImpl_EntryStub01();
        entry.key = "floatValue2";
        entry.value = new Float(3.4028235E38);

        // テスト実施
        test.resetValue(formEx, entry);

        // 判定
        assertEquals((float) 0.0,
                formEx.get("floatValue2"));

    }

    /**
     * testResetValue13()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) form:ValidatorActionFormExインスタンス<br>
     *                JavaBean[] rows = {<br>
     *                 new JavaBean()<br>
     *                  ＋Integer intValue1 = 1,<br>
     *                 new JavaBean()<br>
     *                  ＋Integer intValue1 = 2,<br>
     *                 new JavaBean()<br>
     *                  ＋Integer intValue1 = 3,<br>
     *                }<br>
     *         (引数) entry:rows[1].intValue1=2<br>
     *
     * <br>
     * 期待値：(状態変化) form:row[1].intValue1=0<br>
     *
     * <br>
     * プロパティの型がInteger型の場合、値が0に設定されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    @SuppressWarnings("unchecked")
    public void testResetValue13() throws Exception {
        // 前処理
        ResetterImpl test = new ResetterImpl();
        ResetterImpl_ValidatorActionFormExStub01 form =
            new ResetterImpl_ValidatorActionFormExStub01();
        ResetterImpl_JavaBeanStub01[] rows = {
            new ResetterImpl_JavaBeanStub01(),
            new ResetterImpl_JavaBeanStub01(),
            new ResetterImpl_JavaBeanStub01()
        };
        rows[0].intValue1 = 1;
        rows[1].intValue1 = 2;
        rows[2].intValue1 = 3;
        form.rows = rows;
        ResetterImpl_EntryStub01 entry = new ResetterImpl_EntryStub01();
        entry.key = "rows[1].intValue1";
        entry.value = new Integer(2);

        // テスト実施
        test.resetValue(form, entry);

        // 判定
        assertEquals(new Integer(0), form.rows[1].intValue1);

    }

    /**
     * testResetValue14()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) form:DynaValidatorActionFormExインスタンス<br>
     *                JavaBean[] rows = {<br>
     *                 new JavaBean()<br>
     *                  ＋int intValue2 = 1,<br>
     *                 new JavaBean()<br>
     *                  ＋int intValue2 = 2,<br>
     *                 new JavaBean()<br>
     *                  ＋int intValue2 = 3,<br>
     *                }<br>
     *         (引数) entry:rows[1].intValue2=2<br>
     *
     * <br>
     * 期待値：(状態変化) form:row[1].intValue2=0<br>
     *
     * <br>
     * プロパティの型がint型の場合、値が0に設定されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    @SuppressWarnings("unchecked")
    public void testResetValue14() throws Exception {
        // 前処理
        ResetterImpl test = new ResetterImpl();
        ResetterImpl_JavaBeanStub01[] rows = {
            new ResetterImpl_JavaBeanStub01(),
            new ResetterImpl_JavaBeanStub01(),
            new ResetterImpl_JavaBeanStub01()
        };
        rows[0].intValue2 = 1;
        rows[1].intValue2 = 2;
        rows[2].intValue2 = 3;
        formEx.set("rows", rows);
        ResetterImpl_EntryStub01 entry = new ResetterImpl_EntryStub01();
        entry.key = "rows[1].intValue2";
        entry.value = new Integer(2);

        // テスト実施
        test.resetValue(formEx, entry);

        // 判定
        ResetterImpl_JavaBeanStub01[] result =
            (ResetterImpl_JavaBeanStub01[]) formEx.get("rows");
        assertEquals(0, result[1].intValue2);

    }

    /**
     * testResetValue15()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) form:ValidatorActionFormExインスタンス<br>
     *                ArrayList rowList = {<br>
     *                 new JavaBean()<br>
     *                  ＋Long longValue1 = 1,<br>
     *                 new JavaBean()<br>
     *                  ＋Long longValue1 = 2,<br>
     *                 new JavaBean()<br>
     *                  ＋Long longValue1 = 3,<br>
     *                }<br>
     *         (引数) entry:rowList[1].longValue1=2<br>
     *
     * <br>
     * 期待値：(状態変化) form:rowList[1].longValue1=0<br>
     *
     * <br>
     * プロパティの型がLong型の場合、値が0に設定されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    @SuppressWarnings("unchecked")
    public void testResetValue15() throws Exception {
        // 前処理
        ResetterImpl test = new ResetterImpl();
        ResetterImpl_ValidatorActionFormExStub01 form =
            new ResetterImpl_ValidatorActionFormExStub01();
        List rowList = new ArrayList();
        ResetterImpl_JavaBeanStub01 row = new ResetterImpl_JavaBeanStub01();
        row.longValue1 = new Long(1);
        rowList.add(row);
        row = new ResetterImpl_JavaBeanStub01();
        row.longValue1 = new Long(2);
        rowList.add(row);
        row = new ResetterImpl_JavaBeanStub01();
        row.longValue1 = new Long(3);
        rowList.add(row);
        form.rowList = rowList;
        ResetterImpl_EntryStub01 entry = new ResetterImpl_EntryStub01();
        entry.key = "rowList[1].longValue1";
        entry.value = new Long(2);

        // テスト実施
        test.resetValue(form, entry);

        // 判定
        assertEquals(new Long(0), form.rowList.get(1).longValue1);

    }

    /**
     * testResetValue16()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) form:DynaValidatorActionFormExインスタンス<br>
     *                ArrayList rowList = {<br>
     *                 new JavaBean()<br>
     *                  ＋long longValue2 = 1,<br>
     *                 new JavaBean()<br>
     *                  ＋long longValue2 = 2,<br>
     *                 new JavaBean()<br>
     *                  ＋long longValue2 = 3,<br>
     *                }<br>
     *         (引数) entry:rowList[1].longValue2=2<br>
     *
     * <br>
     * 期待値：(状態変化) form:rowList[1].longValue2=0<br>
     *
     * <br>
     * プロパティの型がlong型の場合、値が0に設定されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    @SuppressWarnings("unchecked")
    public void testResetValue16() throws Exception {
        // 前処理
        ResetterImpl test = new ResetterImpl();
        List rowList = new ArrayList();
        ResetterImpl_JavaBeanStub01 row = new ResetterImpl_JavaBeanStub01();
        row.longValue2 = 1;
        rowList.add(row);
        row = new ResetterImpl_JavaBeanStub01();
        row.longValue2 = 2;
        rowList.add(row);
        row = new ResetterImpl_JavaBeanStub01();
        row.longValue2 = 3;
        rowList.add(row);
        formEx.set("rowList", rowList);
        ResetterImpl_EntryStub01 entry = new ResetterImpl_EntryStub01();
        entry.key = "rowList[1].longValue2";
        entry.value = new Long(2);

        // テスト実施
        test.resetValue(formEx, entry);

        // 判定
        List<ResetterImpl_JavaBeanStub01> result = (List) formEx.get("rowList");
        assertEquals(0, result.get(1).longValue2);

    }

    /**
     * testResetValue17()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) form:ValidatorActionFormExインスタンス<br>
     *                ArrayList rows = {<br>
     *                 new JavaBean()<br>
     *                  ＋Map map = Short shortValue1 = 1,<br>
     *                 new JavaBean()<br>
     *                  ＋Map map = Short shortValue1 = 2,<br>
     *                 new JavaBean()<br>
     *                  ＋Map map = Short shortValue1 = 3,<br>
     *                }<br>
     *         (引数) entry:rows[1].map(shortValue1)=2<br>
     *
     * <br>
     * 期待値：(状態変化) form:rows[1].map(shortValue1)=0<br>
     *
     * <br>
     * プロパティの型がShort型の場合、値が0に設定されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    @SuppressWarnings("unchecked")
    public void testResetValue17() throws Exception {
        // 前処理
        ResetterImpl test = new ResetterImpl();
        ResetterImpl_ValidatorActionFormExStub01 form =
            new ResetterImpl_ValidatorActionFormExStub01();
        List rowList = new ArrayList();
        ResetterImpl_JavaBeanStub01 row = new ResetterImpl_JavaBeanStub01();
        Map map = new HashMap();
        map.put("shortValue1", new Short((short) 1));
        row.map = map;
        rowList.add(row);
        row = new ResetterImpl_JavaBeanStub01();
        map = new HashMap();
        map.put("shortValue1", new Short((short) 2));
        row.map = map;
        rowList.add(row);
        row = new ResetterImpl_JavaBeanStub01();
        map = new HashMap();
        map.put("shortValue1", new Short((short) 3));
        row.map = map;
        rowList.add(row);
        form.rowList = rowList;
        ResetterImpl_EntryStub01 entry = new ResetterImpl_EntryStub01();
        entry.key = "rowList[1].map(shortValue1)";
        entry.value = new Short((short) 2);

        // テスト実施
        test.resetValue(form, entry);

        // 判定
        ResetterImpl_JavaBeanStub01 result = form.rowList.get(1);
        assertEquals(new Short((short) 0), result.map.get("shortValue1"));

    }

    /**
     * testResetValue18()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) form:DynaValidatorActionFormExインスタンス<br>
     *                 short shortValue2 = 1<br>
     *         (引数) entry:shortValue2=1<br>
     *
     * <br>
     * 期待値：(状態変化) form:shortValue1 = 0<br>
     *
     * <br>
     * プロパティの型がshort型の場合、値が0に設定されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    @SuppressWarnings("unchecked")
    public void testResetValue18() throws Exception {
        // 前処理
        ResetterImpl test = new ResetterImpl();
        formEx.set("shortValue2", new Short((short) 2));
        ResetterImpl_EntryStub01 entry = new ResetterImpl_EntryStub01();
        entry.key = "shortValue2";
        entry.value = new Short((short) 2);

        // テスト実施
        test.resetValue(formEx, entry);

        // 判定
        assertEquals((short) 0, formEx.get("shortValue2"));

    }

    /**
     * testResetValue19()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) form:ValidatorActionFormEx<br>
     *                 String value="hoge"<br>
     *         (引数) entry:value="hoge"<br>
     *
     * <br>
     * 期待値：(状態変化) form:value = null<br>
     *
     * <br>
     * プロパティの型がプリミティブ型、ラッパー型ではない場合、nullが設定されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    @SuppressWarnings("unchecked")
    public void testResetValue19() throws Exception {
        // 前処理
        ResetterImpl test = new ResetterImpl();
        ResetterImpl_ValidatorActionFormExStub01 form =
            new ResetterImpl_ValidatorActionFormExStub01();
        form.value = "hoge";
        ResetterImpl_EntryStub01 entry = new ResetterImpl_EntryStub01();
        entry.key = "value";
        entry.value = "hoge";

        // テスト実施
        test.resetValue(form, entry);

        // 判定
        assertNull(form.value);

    }

    /**
     * testResetValue20()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) form:DynaValidatorActionFormEx<br>
     *                 String value="hoge"<br>
     *         (引数) entry:value="hoge"<br>
     *
     * <br>
     * 期待値：(状態変化) form:value = null<br>
     *
     * <br>
     * プロパティの型がプリミティブ型、ラッパー型ではない場合、nullが設定されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    @SuppressWarnings("unchecked")
    public void testResetValue20() throws Exception {
        // 前処理
        ResetterImpl test = new ResetterImpl();
        formEx.set("value", "hoge");
        ResetterImpl_EntryStub01 entry = new ResetterImpl_EntryStub01();
        entry.key = "value";
        entry.value = "hoge";

        // テスト実施
        test.resetValue(formEx, entry);

        // 判定
        assertNull(formEx.get("value"));

    }

    /**
     * testGetResetterResources01()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：C,F
     * <br><br>
     * 入力値：(引数) request:Globals.MODULE_KEY<br>
     *                =ModuleConfig(path="/module")<br>
     *         (状態) ServletContext:RESETTER_RESOURCES/module<br>
     *                =ResetterResourcesインスタンス<br>
     *
     * <br>
     * 期待値：(戻り値) ResetterResources:ResetterResourcesインスタンス<br>
     *
     * <br>
     * ServletContextにRESETTER_RESOURCES/<モジュール名>で登録されたResetterResourcesインスタンスが取得できることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetResetterResources01() throws Exception {
        //初期設定
        ResetterImpl resetter = new ResetterImpl();
        MockHttpServletRequest req = new MockHttpServletRequest();
        MockHttpSession session = new MockHttpSession();

        ResetterResources rr = new ResetterResources();

        // リクエストにモジュールコンフィグを登録
        String module = "/module";
        ModuleConfig reqConfig = new ModuleConfigImpl(module);
        req.setAttribute(Globals.MODULE_KEY, reqConfig);

        // アクションサーブレット作成
        MockServletContext ctx = new MockServletContext();

        ctx.setAttribute("RESETTER_RESOURCES/module", rr);
        session.setServletContext(ctx);

        //リクエストにセッションを登録
        req.setSession(session);

        //テスト実行
        ResetterResources result = resetter.getResetterResources(req);

        //結果確認
        assertSame(rr, result);
    }

    /**
     * testGetResetterResources02()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：C,F
     * <br><br>
     * 入力値：(引数) request:Globals.MODULE_KEY<br>
     *                =ModuleConfig(path="/module")<br>
     *         (状態) ServletContext:RESETTER_RESOURCES/module<br>
     *                =null<br>
     *
     * <br>
     * 期待値：(戻り値) ResetterResources:null<br>
     *
     * <br>
     * ServletContextにRESETTER_RESOURCES/<モジュール名>でResetterResourcesが存在しない場合、nullが取得できることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetResetterResources02() throws Exception {
        //初期設定
        ResetterImpl resetter = new ResetterImpl();
        MockHttpServletRequest req = new MockHttpServletRequest();
        MockHttpSession session = new MockHttpSession();

        // リクエストにモジュールコンフィグを登録
        String module = "/module";
        ModuleConfig reqConfig = new ModuleConfigImpl(module);
        req.setAttribute(Globals.MODULE_KEY, reqConfig);

        // アクションサーブレット作成
        MockServletContext ctx = new MockServletContext();

        ctx.setAttribute("RESETTER_RESOURCES/module", null);
        session.setServletContext(ctx);

        //リクエストにセッションを登録
        req.setSession(session);

        //テスト実行
        ResetterResources result = resetter.getResetterResources(req);

        //結果確認
        assertNull(result);
    }

    /**
     * testGetActionReset01()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：C,F
     * <br><br>
     * 入力値：(引数) mapping:not null<br>
     *         (引数) request:not null<br>
     *         (状態) getResetterResources:null<br>
     *
     * <br>
     * 期待値：(戻り値) ActionReset:null<br>
     *
     * <br>
     * getResetterResourcesメソッドがnullを返却する場合、nullが取得できることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetActionReset01() throws Exception {
        ResetterImplStub02 resetter = new ResetterImplStub02();
        resetter.resetterResources = null;

        ActionMappingEx mapping = new ActionMappingEx();
        MockHttpServletRequest request = new MockHttpServletRequest();

        Object result = resetter.getActionReset(mapping, request);

        assertNull(result);
    }

    /**
     * testGetActionReset02()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：C,F
     * <br><br>
     * 入力値：(引数) mapping:not null<br>
     *                path="test"<br>
     *         (引数) request:not null<br>
     *         (状態) getResetterResources:not null<br>
     *                hoge=ActionReset<br>
     *
     * <br>
     * 期待値：(戻り値) ActionReset:null<br>
     *
     * <br>
     * mappingから取得したpathの値に該当するActionResetがResetterResourcesから取得できない場合、nullが取得できることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetActionReset02() throws Exception {
        // 前処理
        // hoge=ActionResetをResetterResourcesに設定
        ResetterResources resources = new ResetterResources();
        ActionReset actionReset = new ActionReset();
        actionReset.setPath("hoge");
        resources.setActionReset(actionReset);

        // テスト対象スタブ
        ResetterImplStub02 resetter = new ResetterImplStub02();
        resetter.resetterResources = resources;
        MockServletContext ctx = new MockServletContext();
        ctx.setAttribute(ResetterResources.RESETTER_RESOURCES_KEY, resources);

        // 引数
        ActionMappingEx mapping = new ActionMappingEx();
        mapping.setPath("test");
        MockHttpServletRequest request = new MockHttpServletRequest();

        // テスト実施
        Object result = resetter.getActionReset(mapping, request);

        // 判定
        assertNull(result);
    }

    /**
     * testGetActionReset03()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：C,F
     * <br><br>
     * 入力値：(引数) mapping:not null<br>
     *                path="test"<br>
     *         (引数) request:not null<br>
     *         (状態) getResetterResources:not null<br>
     *                hoge=ActionResetインスタンス０１<br>
     *                test=ActionResetインスタンス０２<br>
     *
     * <br>
     * 期待値：(戻り値) ActionReset:ActionResetインスタンス０２<br>
     *
     * <br>
     * mappingから取得したpathの値に該当するActionResetがResetterResourcesに存在する場合、そのインスタンスが取得できることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetActionReset03() throws Exception {
        // 前処理
        // hoge=ActionResetをResetterResourcesに設定
        ResetterResources resources = new ResetterResources();
        ActionReset actionReset1 = new ActionReset();
        actionReset1.setPath("hoge");
        resources.setActionReset(actionReset1);
        ActionReset actionReset2 = new ActionReset();
        actionReset2.setPath("test");
        resources.setActionReset(actionReset2);

        // テスト対象スタブ
        ResetterImplStub02 resetter = new ResetterImplStub02();
        resetter.resetterResources = resources;
        MockServletContext ctx = new MockServletContext();
        ctx.setAttribute(ResetterResources.RESETTER_RESOURCES_KEY, resources);

        // 引数
        ActionMappingEx mapping = new ActionMappingEx();
        mapping.setPath("test");
        MockHttpServletRequest request = new MockHttpServletRequest();

        // テスト実施
        Object result = resetter.getActionReset(mapping, request);

        // 判定
        assertSame(actionReset2, result);
    }

    /**
     * testResetSelectField01()
     * <br><br>
     *
     * (異常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) propMap:空のMap<br>
     *         (引数) request:パラメータ<br>
     *                startIndex="hoge"<br>
     *
     * <br>
     * 期待値：(状態変化) resetValue:呼び出されない<br>
     *         (状態変化) ログ:ログレベル：エラー<br>
     *                    startIndex or endIndex is not Number.<br>
     *                    例外：NumberFormatException<br>
     *
     * <br>
     * リクエストパラメータから取得したstartIndexが数値に変換できない場合、エラーログを出力し、処理終了することを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    @SuppressWarnings("unchecked")
    public void testResetSelectField01() throws Exception {
        // 前処理
        ResetterImplStub03 test = new ResetterImplStub03();
        ResetterImpl_ValidatorActionFormExStub01 form =
            new ResetterImpl_ValidatorActionFormExStub01();
        Map propMap = new TreeMap();
        MockHttpServletRequest req = new MockHttpServletRequest();
        req.setParameter("startIndex", "hoge");

        // テスト実施
        test.resetSelectField(form ,propMap, req);

        // 判定
        assertTrue(
            LogUTUtil.checkError("startIndex or endIndex is not Number.",
                    new NumberFormatException()));
        assertEquals(0, test.resetValueCount);
    }

    /**
     * testResetSelectField02()
     * <br><br>
     *
     * (異常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) propMap:空のMap<br>
     *         (引数) request:パラメータ<br>
     *                endIndex="hoge"<br>
     *
     * <br>
     * 期待値：(状態変化) resetValue:呼び出されない<br>
     *         (状態変化) ログ:ログレベル：エラー<br>
     *                    startIndex or endIndex is not Number.<br>
     *                    例外：NumberFormatException<br>
     *
     * <br>
     * リクエストパラメータから取得したendIndexが数値に変換できない場合、エラーログを出力し、処理終了することを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    @SuppressWarnings("unchecked")
    public void testResetSelectField02() throws Exception {
        // 前処理
        ResetterImplStub03 test = new ResetterImplStub03();
        ResetterImpl_ValidatorActionFormExStub01 form =
            new ResetterImpl_ValidatorActionFormExStub01();
        Map propMap = new TreeMap();
        MockHttpServletRequest req = new MockHttpServletRequest();
        req.setParameter("endIndex", "hoge");

        // テスト実施
        test.resetSelectField(form ,propMap, req);

        // 判定
        assertTrue(
            LogUTUtil.checkError("startIndex or endIndex is not Number.",
                    new NumberFormatException()));
        assertEquals(0, test.resetValueCount);
    }

    /**
     * testResetSelectField03()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) propMap:空のMap<br>
     *         (引数) request:パラメータ<br>
     *                startIndex="0"<br>
     *                endIndex="100"<br>
     *
     * <br>
     * 期待値：(状態変化) resetValue:呼び出されない<br>
     *
     * <br>
     * 引数のpropMapに要素が存在しない場合、resetValueが呼び出されないことを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    @SuppressWarnings("unchecked")
    public void testResetSelectField03() throws Exception {
        // 前処理
        ResetterImplStub03 test = new ResetterImplStub03();
        ResetterImpl_ValidatorActionFormExStub01 form =
            new ResetterImpl_ValidatorActionFormExStub01();
        Map propMap = new TreeMap();
        MockHttpServletRequest req = new MockHttpServletRequest();
        req.setParameter("startIndex", "0");
        req.setParameter("endIndex", "100");

        // テスト実施
        test.resetSelectField(form ,propMap, req);

        // 判定
        assertEquals(0, test.resetValueCount);
    }

    /**
     * testResetSelectField04()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) propMap:TreeMap {<br>
     *                 "key01" = "value01",<br>
     *                       ～<br>
     *                 "key10" = "value10"<br>
     *                }<br>
     *         (引数) request:パラメータ<br>
     *                startIndex="0"<br>
     *                endIndex="4"<br>
     *
     * <br>
     * 期待値：(状態変化) resetValue:"key01" = "value01",<br>
     *                           ～<br>
     *                     "key05" = "value05"<br>
     *
     * <br>
     * 引数のpropMapの要素のうち、startIndex～endIndexの要素を引数として、resetValueが呼び出されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    @SuppressWarnings("unchecked")
    public void testResetSelectField04() throws Exception {
        // 前処理
        ResetterImplStub03 test = new ResetterImplStub03();
        ResetterImpl_ValidatorActionFormExStub01 form =
            new ResetterImpl_ValidatorActionFormExStub01();
        Map propMap = new TreeMap();
        propMap.put("key01", "value01");
        propMap.put("key02", "value02");
        propMap.put("key03", "value03");
        propMap.put("key04", "value04");
        propMap.put("key05", "value05");
        propMap.put("key06", "value06");
        propMap.put("key07", "value07");
        propMap.put("key08", "value08");
        propMap.put("key09", "value09");
        propMap.put("key10", "value10");
        MockHttpServletRequest req = new MockHttpServletRequest();
        req.setParameter("startIndex", "0");
        req.setParameter("endIndex", "4");

        // テスト実施
        test.resetSelectField(form ,propMap, req);

        // 判定
        assertEquals(5, test.resetValueCount);
        assertSame(form, test.resetValueArg0.get(0));
        for (int i = 0; i < 5; i++) {
            String key = "key0" + (i + 1);
            String value = "value0" + (i + 1);
            assertEquals(key, test.resetValueArg1.get(i).getKey());
            assertEquals(value, test.resetValueArg1.get(i).getValue());
        }
    }

    /**
     * testResetSelectField05()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) propMap:TreeMap {<br>
     *                 "key01" = "value01",<br>
     *                       ～<br>
     *                 "key10" = "value10"<br>
     *                }<br>
     *         (引数) request:パラメータ<br>
     *                startIndex、endIndexが存在しない<br>
     *
     * <br>
     * 期待値：(状態変化) resetValue:"key01" = "value01",<br>
     *                           ～<br>
     *                     "key10" = "value10"<br>
     *
     * <br>
     * リクエストパラメータにstartIndex、endIndexが存在しない場合、全ての要素についてresetValueが呼び出されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    @SuppressWarnings("unchecked")
    public void testResetSelectField05() throws Exception {
        // 前処理
        ResetterImplStub03 test = new ResetterImplStub03();
        ResetterImpl_ValidatorActionFormExStub01 form =
            new ResetterImpl_ValidatorActionFormExStub01();
        Map propMap = new TreeMap();
        propMap.put("key01", "value01");
        propMap.put("key02", "value02");
        propMap.put("key03", "value03");
        propMap.put("key04", "value04");
        propMap.put("key05", "value05");
        propMap.put("key06", "value06");
        propMap.put("key07", "value07");
        propMap.put("key08", "value08");
        propMap.put("key09", "value09");
        propMap.put("key10", "value10");
        MockHttpServletRequest req = new MockHttpServletRequest();

        // テスト実施
        test.resetSelectField(form ,propMap, req);

        // 判定
        assertEquals(10, test.resetValueCount);
        assertSame(form, test.resetValueArg0.get(0));
        for (int i = 0; i < 10; i++) {
            String key = null;
            String value = null;
            if (i < 9) {
                key = "key0" + (i + 1);
                value = "value0" + (i + 1);
            } else {
                key = "key10";
                value = "value10";
            }
            assertEquals(key, test.resetValueArg1.get(i).getKey());
            assertEquals(value, test.resetValueArg1.get(i).getValue());
        }
    }

    /**
     * testResetSelectField06()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) propMap:TreeMap {<br>
     *                 "key01" = "value01",<br>
     *                       ～<br>
     *                 "key10" = "value10"<br>
     *                }<br>
     *         (引数) request:パラメータ<br>
     *                startIndex="5"<br>
     *                endIndex="20"<br>
     *
     * <br>
     * 期待値：(状態変化) resetValue:"key05" = "value05",<br>
     *                           ～<br>
     *                     "key10" = "value10"<br>
     *
     * <br>
     * リクエストパラメータから取得したendIndexがpropMapの要素数より大きい場合、最後の要素までがresetValueに渡されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    @SuppressWarnings("unchecked")
    public void testResetSelectField06() throws Exception {
        // 前処理
        ResetterImplStub03 test = new ResetterImplStub03();
        ResetterImpl_ValidatorActionFormExStub01 form =
            new ResetterImpl_ValidatorActionFormExStub01();
        Map propMap = new TreeMap();
        propMap.put("key01", "value01");
        propMap.put("key02", "value02");
        propMap.put("key03", "value03");
        propMap.put("key04", "value04");
        propMap.put("key05", "value05");
        propMap.put("key06", "value06");
        propMap.put("key07", "value07");
        propMap.put("key08", "value08");
        propMap.put("key09", "value09");
        propMap.put("key10", "value10");
        MockHttpServletRequest req = new MockHttpServletRequest();
        req.setParameter("startIndex", "5");
        req.setParameter("endIndex", "20");

        // テスト実施
        test.resetSelectField(form ,propMap, req);

        // 判定
        assertEquals(5, test.resetValueCount);
        assertSame(form, test.resetValueArg0.get(0));
        int index = 0;
        for (int i = 5; i < 10; i++) {
            String key = null;
            String value = null;
            if (i < 9) {
                key = "key0" + (i + 1);
                value = "value0" + (i + 1);
            } else {
                key = "key10";
                value = "value10";
            }
            assertEquals(key, test.resetValueArg1.get(index).getKey());
            assertEquals(value, test.resetValueArg1.get(index).getValue());
            index++;
        }
    }

    /**
     * testResetSelectField07()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) propMap:TreeMap {<br>
     *                 "key01" = "value01",<br>
     *                       ～<br>
     *                 "key10" = "value10"<br>
     *                }<br>
     *         (引数) request:パラメータ<br>
     *                startIndex="5"<br>
     *                endIndex="0"<br>
     *
     * <br>
     * 期待値：(状態変化) resetValue:呼び出されない<br>
     *
     * <br>
     * startIndexの値がendIndexの値より大きい場合、resetValueが呼び出されないことを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    @SuppressWarnings("unchecked")
    public void testResetSelectField07() throws Exception {
        // 前処理
        ResetterImplStub03 test = new ResetterImplStub03();
        ResetterImpl_ValidatorActionFormExStub01 form =
            new ResetterImpl_ValidatorActionFormExStub01();
        Map propMap = new TreeMap();
        propMap.put("key01", "value01");
        propMap.put("key02", "value02");
        propMap.put("key03", "value03");
        propMap.put("key04", "value04");
        propMap.put("key05", "value05");
        propMap.put("key06", "value06");
        propMap.put("key07", "value07");
        propMap.put("key08", "value08");
        propMap.put("key09", "value09");
        propMap.put("key10", "value10");
        MockHttpServletRequest req = new MockHttpServletRequest();
        req.setParameter("startIndex", "5");
        req.setParameter("endIndex", "0");

        // テスト実施
        test.resetSelectField(form ,propMap, req);

        // 判定
        assertEquals(0, test.resetValueCount);
    }

    /**
     * testResetSelectField08()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) propMap:TreeMap {<br>
     *                 "key01" = "value01",<br>
     *                       ～<br>
     *                 "key10" = "value10"<br>
     *                }<br>
     *         (引数) request:パラメータ<br>
     *                startIndex="5"<br>
     *                endIndex="5"<br>
     *
     * <br>
     * 期待値：(状態変化) resetValue:"key06" = "value06"<br>
     *
     * <br>
     * startIndexとendIndexの値が一致する場合、その要素だけがresetValueに渡されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    @SuppressWarnings("unchecked")
    public void testResetSelectField08() throws Exception {
        // 前処理
        ResetterImplStub03 test = new ResetterImplStub03();
        ResetterImpl_ValidatorActionFormExStub01 form =
            new ResetterImpl_ValidatorActionFormExStub01();
        Map propMap = new TreeMap();
        propMap.put("key01", "value01");
        propMap.put("key02", "value02");
        propMap.put("key03", "value03");
        propMap.put("key04", "value04");
        propMap.put("key05", "value05");
        propMap.put("key06", "value06");
        propMap.put("key07", "value07");
        propMap.put("key08", "value08");
        propMap.put("key09", "value09");
        propMap.put("key10", "value10");
        MockHttpServletRequest req = new MockHttpServletRequest();
        req.setParameter("startIndex", "5");
        req.setParameter("endIndex", "5");

        // テスト実施
        test.resetSelectField(form ,propMap, req);

        // 判定
        assertEquals(1, test.resetValueCount);
        assertEquals("key06", test.resetValueArg1.get(0).getKey());
        assertEquals("value06", test.resetValueArg1.get(0).getValue());
    }

}
