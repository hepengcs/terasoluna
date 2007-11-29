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
import java.util.List;
import java.util.Map;

import jp.terasoluna.fw.web.struts.reset.Resetter;
import jp.terasoluna.fw.web.struts.reset.ResetterImpl;
import jp.terasoluna.utlib.DynaActionFormCreator;
import jp.terasoluna.utlib.LogUTUtil;
import jp.terasoluna.utlib.MockHttpServletRequest;
import jp.terasoluna.utlib.MockHttpSession;
import jp.terasoluna.utlib.MockServletContext;
import jp.terasoluna.utlib.UTUtil;
import junit.framework.TestCase;

import org.apache.commons.beanutils.DynaProperty;
import org.apache.struts.Globals;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionFormClass;
import org.apache.struts.config.ModuleConfig;
import org.apache.struts.config.impl.ModuleConfigImpl;

/**
 * {@link jp.terasoluna.fw.web.struts.form.DynaValidatorActionFormEx} クラスのブラックボックステスト。
 *
 * <p>
 * <h4>【クラスの概要】</h4>
 * 動的アクションフォーム基底クラス。
 * <p>
 *
 * @see jp.terasoluna.fw.web.struts.form.DynaValidatorActionFormEx
 */
public class DynaValidatorActionFormExTest extends TestCase {

    /**
     * DynaValidatorActionFormExのプロパティ設定ファイル
     */
    private static final String CONFIG_FILE_PATH =
        DynaValidatorActionFormExTest.class.getResource(
                "DynaValidatorActionFormExTest.xml").getPath();

    /**
     * DynaValidatorActionFormEx生成ルール設定ファイル
     */
    private final static String RULES_FILE_PATH =
        DynaValidatorActionFormExTest.class.getResource(
                "DynaValidatorActionFormExTest-rules.xml").getPath();
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
        junit.swingui.TestRunner.run(DynaValidatorActionFormExTest.class);
    }

    /**
     * 初期化処理を行う。
     *
     * @throws Exception このメソッドで発生した例外
     * @see junit.framework.TestCase#setUp()
     */
    @SuppressWarnings("unchecked")
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        // create formEx
        // 試験対象であるsetメソッドを呼び出さないように、プロパティ設定の記述がないファイルを指定
        this.formEx = (DynaValidatorActionFormEx) creator
                .create(CONFIG_FILE_PATH);

        // 格納値の作成
        int hogeInt = 123;
        String hogeString = "data1";
        int[] hogeIntArray = {-100, 0, 10, 111};
        String[] hogeStringArray = new String[4];
        Object[] hogeObjectArray = new Object[4];
        List<Object> hogeList = new ArrayList<Object>();
        Map<String, Object> hogeMap = new HashMap<String, Object>();
        Runnable hogeRunnable = new Runnable() {
            public void run() {
            }
        };
        boolean hogeBoolean = true;
        byte hogeByte = 1;
        char hogeChar = 'A';
        double hogeDouble = 999.9;
        float hogeFloat = 999;
        short hogeShort = 9;
        long hogeLong = 9;

        for (int i = 0; i < 4; i++) {
            hogeStringArray[i] = "data" + (i + 1);
            hogeObjectArray[i] = new Integer(i + 1);
            hogeList.add(i, "data" + (i + 1));
            hogeMap.put("field" + (i + 1), "data" + (i + 1));
        }

        String[] fields = {
                "hogeInt",
                "hogeString",
                "hogeIntArray",
                "hogeStringArray",
                "hogeObjectArray",
                "hogeList",
                "hogeMap",
                "hogeRunnable",
                "hogeBoolean",
                "hogeByte",
                "hogeChar",
                "hogeDouble",
                "hogeFloat",
                "hogeShort",
                "hogeLong"
                };
        Class[] fieldClasses = {
                int.class,
                hogeString.getClass(),
                hogeIntArray.getClass(),
                hogeStringArray.getClass(),
                hogeObjectArray.getClass(),
                hogeList.getClass(),
                hogeMap.getClass(),
                hogeRunnable.getClass(),
                boolean.class,
                byte.class,
                char.class,
                double.class,
                float.class,
                short.class,
                long.class
                };
        DynaProperty[] props = new DynaProperty[fields.length];
        HashMap<String, DynaProperty> propsMap = new HashMap<String, DynaProperty>();
        for (int i = 0;i < fields.length; i++) {
            props[i] = new DynaProperty(fields[i], fieldClasses[i]);
            propsMap.put(props[i].getName(), props[i]);
        }
        DynaActionFormClass dynaActionFormClass = (DynaActionFormClass) UTUtil
                .getPrivateField(this.formEx, "dynaClass");
        UTUtil.setPrivateField(dynaActionFormClass, "properties", props);
        UTUtil.setPrivateField(dynaActionFormClass, "propertiesMap", propsMap);

        Map<String, Object> map = (Map) UTUtil.getPrivateField(this.formEx,
                "dynaValues");
        map.put("hogeInt", hogeInt);
        map.put("hogeString", hogeString);
        map.put("hogeIntArray", hogeIntArray);
        map.put("hogeStringArray", hogeStringArray);
        map.put("hogeObjectArray", hogeObjectArray);
        map.put("hogeList", hogeList);
        map.put("hogeMap", hogeMap);
        map.put("hogeRunnable", hogeRunnable);
        map.put("hogeBoolean", hogeBoolean);
        map.put("hogeByte", hogeByte);
        map.put("hogeChar", hogeChar);
        map.put("hogeDouble", hogeDouble);
        map.put("hogeFloat", hogeFloat);
        map.put("hogeShort", hogeShort);
        map.put("hogeLong", hogeLong);
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
    public DynaValidatorActionFormExTest(String name) {
        super(name);
    }

    /**
     * testGet01()
     * <br><br>
     *
     * (異常系)
     * <br>
     * 観点：G
     * <br><br>
     * 入力値：(引数) name:null<br>
     *         (引数) index:0<br>
     *
     * <br>
     * 期待値：(状態変化) 例外:NullPointerException：<br>
     *                    メッセージ："No indexed value for 'null[0]'"<br>
     *
     * <br>
     * nameにnullを設定し、NullPointerExceptionがスローされることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testGet01() throws Exception {
        // テスト実行
        try {
            this.formEx.get(null, 0);
            fail();
        } catch (NullPointerException e) {
            // テスト結果確認
            assertEquals("No indexed value for 'null[0]'", e.getMessage());
        }
    }

    /**
     * testGet02()
     * <br><br>
     *
     * (異常系)
     * <br>
     * 観点：G
     * <br><br>
     * 入力値：(引数) name:空文字<br>
     *         (引数) index:0<br>
     *
     * <br>
     * 期待値：(状態変化) 例外:NullPointerException：<br>
     *                    メッセージ："No indexed value for '[0]'"<br>
     *
     * <br>
     * nameに空文字を設定し、NullPointerExceptionがスローされることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testGet02() throws Exception {
        // テスト実行
        try {
            this.formEx.get("", 0);
            fail();
        } catch (NullPointerException e) {
            // テスト結果確認
            assertEquals("No indexed value for '[0]'", e.getMessage());
        }
    }

    /**
     * testGet03()
     * <br><br>
     *
     * (異常系)
     * <br>
     * 観点：G
     * <br><br>
     * 入力値：(引数) name:"foo"（存在しないフィールド名）<br>
     *         (引数) index:0<br>
     *
     * <br>
     * 期待値：(状態変化) 例外:NullPointerException：<br>
     *                    メッセージ："No indexed value for 'foo[0]'"<br>
     *
     * <br>
     * nameに存在しないフィールド名を設定し、NullPointerExceptionがスローされることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testGet03() throws Exception {
        // テスト実行
        try {
            this.formEx.get("foo", 0);
            fail();
        } catch (NullPointerException e) {
            // テスト結果確認
            assertEquals("No indexed value for 'foo[0]'", e.getMessage());
        }
    }

    /**
     * testGet04()
     * <br><br>
     *
     * (異常系)
     * <br>
     * 観点：G
     * <br><br>
     * 入力値：(引数) name:"hogeString"<br>
     *         (引数) index:0<br>
     *
     * <br>
     * 期待値：(状態変化) 例外:IllegalArgumentException：<br>
     *                    メッセージ："Non-indexed property for 'hogeString[0]'"<br>
     *
     * <br>
     * fieldNameに配列でもList無いフィールド名を設定し、IllegalArgumentExceptionがスローされることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testGet04() throws Exception {
        // テスト実行
        try {
            this.formEx.get("hogeString", 0);
            fail();
        } catch (IllegalArgumentException e) {
            // テスト結果確認
            assertEquals("Non-indexed property for 'hogeString[0]'", e
                    .getMessage());
        }
    }

    /**
     * testGet05()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) name:"hogeStringArray"<br>
     *         (引数) index:-1<br>
     *         (状態) fieldNameで指定されたフィールド:String配列型:["data1", "data2", "data3", "data4"]<br>
     *
     * <br>
     * 期待値：(戻り値) Object:null<br>
     *
     * <br>
     * nameに配列型のフィールドを設定し、かつIndexに-1を設定し、nullが返る事を確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testGet05() throws Exception {
        // テスト実行
        Object object = this.formEx.get("hogeStringArray", -1);

        // テスト結果確認
        assertNull(object);
    }

    /**
     * testGet06()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) name:"hogeStringArray"<br>
     *         (引数) index:0<br>
     *         (状態) fieldNameで指定されたフィールド:String配列型:["data1", "data2", "data3", "data4"]<br>
     *
     * <br>
     * 期待値：(戻り値) Object:"data1"<br>
     *
     * <br>
     * nameに配列型のフィールドを設定し、かつIndexに0を設定し、値が返る事を確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testGet06() throws Exception {
        // テスト実行
        Object object = this.formEx.get("hogeStringArray", 0);

        // テスト結果確認
        assertEquals("data1", object);
    }

    /**
     * testGet07()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) name:"hogeStringArray"<br>
     *         (引数) index:10<br>
     *         (状態) fieldNameで指定されたフィールド:String配列型:["data1", "data2", "data3", "data4"]<br>
     *
     * <br>
     * 期待値：(戻り値) Object:null<br>
     *
     * <br>
     * nameに配列型のフィールドを設定し、かつIndexに10を設定し、nullが返る事を確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testGet07() throws Exception {
        // テスト実行
        Object object = this.formEx.get("hogeStringArray", 10);

        // テスト結果確認
        assertNull(object);
    }

    /**
     * testGet08()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) name:"hogeList"<br>
     *         (引数) index:-1<br>
     *         (状態) fieldNameで指定されたフィールド:List型:["data1","data2", "data3", "data4"]<br>
     *
     * <br>
     * 期待値：(戻り値) Object:null<br>
     *
     * <br>
     * nameにList型のフィールドを設定し、かつIndexに-1を設定し、nullが返る事を確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testGet08() throws Exception {
        // テスト実行
        Object object = this.formEx.get("hogeList", -1);

        // テスト結果確認
        assertNull(object);
    }

    /**
     * testGet09() <br>
     * <br>
     *
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) name:"hogeList"<br>
     *         (引数) index:0<br>
     *         (状態) fieldNameで指定されたフィールド:List型:["data1","data2", "data3", "data4"]<br>
     *
     * <br>
     * 期待値：(戻り値) Object:"data1"<br>
     *
     * <br>
     * nameにList型のフィールドを設定し、かつIndexに0を設定し、値が返る事を確認する。 <br>
     *
     * @throws Exception
     *             このメソッドで発生した例外
     */
    public void testGet09() throws Exception {
        // テスト実行
        Object object = this.formEx.get("hogeList", 0);

        // テスト結果確認
        assertEquals("data1", object);
    }

    /**
     * testGet10()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) name:"hogeList"<br>
     *         (引数) index:10<br>
     *         (状態) fieldNameで指定されたフィールド:List型:["data1","data2", "data3", "data4"]<br>
     *
     * <br>
     * 期待値：(戻り値) Object:null<br>
     *
     * <br>
     * nameにList型のフィールドを設定し、かつIndexに10を設定し、nullが返る事を確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testGet10() throws Exception {
        // テスト実行
        Object object = this.formEx.get("hogeList", 10);

        // テスト結果確認
        assertNull(object);
    }

    /**
     * testGet11()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) name:"hogeIntArray"<br>
     *         (引数) index:-1<br>
     *         (状態) fieldNameで指定されたフィールド:int配列型:[-100, 0, 10, 111]<br>
     *
     * <br>
     * 期待値：(戻り値) Object:null<br>
     *
     * <br>
     * nameに配列型のフィールドを設定し、かつIndexに-1を設定し、nullが返る事を確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testGet11() throws Exception {
        // テスト実行
        Object object = this.formEx.get("hogeIntArray", -1);

        // テスト結果確認
        assertNull(object);
    }

    /**
     * testGet12()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) name:"hogeIntArray"<br>
     *         (引数) index:0<br>
     *         (状態) fieldNameで指定されたフィールド:int配列型:[-100, 0, 10, 111]<br>
     *
     * <br>
     * 期待値：(戻り値) Object:-100<br>
     *
     * <br>
     * nameに配列型のフィールドを設定し、かつIndexに0を設定し、値が返る事を確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testGet12() throws Exception {
        // テスト実行
        Object object = this.formEx.get("hogeIntArray", 0);

        // テスト結果確認
        assertEquals(-100, object);
    }

    /**
     * testGet13()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) name:"hogeIntArray"<br>
     *         (引数) index:10<br>
     *         (状態) fieldNameで指定されたフィールド:int配列型:[-100, 0, 10, 111]<br>
     *
     * <br>
     * 期待値：(戻り値) Object:null<br>
     *
     * <br>
     * nameに配列型のフィールドを設定し、かつIndexに10を設定し、nullが返る事を確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testGet13() throws Exception {
        // テスト実行
        Object object = this.formEx.get("hogeIntArray", 10);

        // テスト結果確認
        assertNull(object);
    }

    /**
     * testGetIndexCount01()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) fieldName:null<br>
     *
     * <br>
     * 期待値：(戻り値) int:0<br>
     *
     * <br>
     * fieldNameにnullを設定し、0が返る事を確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetIndexCount01() throws Exception {
        // テスト実行
        int i = this.formEx.getIndexCount(null);

        // テスト結果確認
        assertEquals(0, i);
    }

    /**
     * testGetIndexCount02()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) fieldName:空文字<br>
     *
     * <br>
     * 期待値：(戻り値) int:0<br>
     *
     * <br>
     * fieldNameに空文字を設定し、0が返る事を確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetIndexCount02() throws Exception {
        // テスト実行
        int i = this.formEx.getIndexCount("");

        // テスト結果確認
        assertEquals(0, i);
    }

    /**
     * testGetIndexCount03()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) fieldName:"foo"（存在しないフィールド名）<br>
     *
     * <br>
     * 期待値：(戻り値) int:0<br>
     *
     * <br>
     * fieldNmaeに存在しないフィールド名を設定し、0が返る事を確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetIndexCount03() throws Exception {
        // テスト実行
        int i = this.formEx.getIndexCount("foo");

        // テスト結果確認
        assertEquals(0, i);
    }

    /**
     * testGetIndexCount04()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) fieldName:"hogeString"<br>
     *         (状態) fieldNameで指定されたフィールド:String型:"data0"<br>
     *
     * <br>
     * 期待値：(戻り値) int:1<br>
     *
     * <br>
     * fieldNameに配列でもList無いフィールド名を設定し、1が返る事を確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetIndexCount04() throws Exception {
        // テスト実行
        int i = this.formEx.getIndexCount("hogeString");

        // テスト結果確認
        assertEquals(1, i);
    }

    /**
     * testGetIndexCount05()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) fieldName:"hogeStringArray"<br>
     *         (状態) fieldNameで指定されたフィールド:String配列型:["data1", "data2", "data3", "data4"]<br>
     *
     * <br>
     * 期待値：(戻り値) int:4<br>
     *
     * <br>
     * fieldNameに配列型のフィールドを設定し、正しい値が返る事を確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetIndexCount05() throws Exception {
        // テスト実行
        int i = this.formEx.getIndexCount("hogeStringArray");

        // テスト結果確認
        assertEquals(4, i);
    }

    /**
     * testGetIndexCount06()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) fieldName:"hogeList"<br>
     *         (状態) fieldNameで指定されたフィールド:List型:["data1","data2", "data3", "data4"]<br>
     *
     * <br>
     * 期待値：(戻り値) int:4<br>
     *
     * <br>
     * fieldNameにList型のフィールドを設定し、正しい値が返る事を確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetIndexCount06() throws Exception {
        // テスト実行
        int i = this.formEx.getIndexCount("hogeList");

        // テスト結果確認
        assertEquals(4, i);
    }

    /**
     * testGetIndexCount07()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) fieldName:"hogeMap"<br>
     *         (状態) fieldNameで指定されたフィールド:Map型:[field1="data1", field2="data2", field3="data3", field4=data4"]<br>
     *
     * <br>
     * 期待値：(戻り値) int:4<br>
     *
     * <br>
     * fieldNameにMap型のフィールドを設定し、正しい値が返る事を確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetIndexCount07() throws Exception {
        // テスト実行
        int i = this.formEx.getIndexCount("hogeMap");

        // テスト結果確認
        assertEquals(4, i);
    }

    /**
     * testReset01()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) mapping:not null<br>
     *         (引数) request:デフォルトモジュールを格納したリクエストオブジェクト<br>
     *         (状態) resetter:null<br>
     *
     * <br>
     * 期待値：(戻り値) void:－<br>
     *         (状態変化) DynaValidatorActionFormEx_ResetterStub01#reset():未実行<br>
     *         (状態変化) ログ:－<br>
     *
     * <br>
     * デフォルトモジュールを格納したリクエストオブジェクトを設定し、Resetterがnullだった場合、変化せずに終了することを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testReset01() throws Exception {
        // リクエストの生成
        MockHttpServletRequest request = new MockHttpServletRequest();
        // セッションの生成
        MockHttpSession session = new MockHttpSession();
        // コンテキストの生成
        MockServletContext context = new MockServletContext();
        // アクションサーブレットの生成
        DynaValidatorActionFormEx_ActionServletStub01 servlet =
            new DynaValidatorActionFormEx_ActionServletStub01();
        // プレフィックスの生成
        String prefix = "";
        // モジュール設定情報の生成
        ModuleConfig moduleConfig = new ModuleConfigImpl(prefix);
        // マッピング情報の生成
        ActionMapping mapping = new ActionMapping();

        // 設定
        request.setSession(session);
        request.setAttribute(Globals.MODULE_KEY, moduleConfig);
        session.setServletContext(context);
        context.setAttribute(Resetter.RESETTER_KEY + prefix, null);
        context.setAttribute(Globals.MODULE_KEY, moduleConfig);
        this.formEx.setServlet(servlet);
        servlet.setContext(context);

        // テスト実行
        this.formEx.reset(mapping, request);
    }

    /**
     * testReset02() <br>
     * <br>
     *
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) mapping:not null<br>
     *         (引数) request:デフォルトモジュールを格納したリクエストオブジェクト<br>
     *         (状態) resetter:Resetter以外のオブジェクト<br>
     *
     * <br>
     * 期待値：(戻り値) void:－<br>
     *         (状態変化) DynaValidatorActionFormEx_ResetterStub01#reset():未実行<br>
     *         (状態変化) ログ:＜ログ＞<br>
     *                    エラーログ：""<br>
     *                    ＜例外＞<br>
     *                    ClassCastException<br>
     *
     * <br>
     * デフォルトモジュールを格納したリクエストオブジェクトを設定し、Resetter以外のオブジェクトを取得した場合、ログを出力し、終了することを確認する。
     * <br>
     *
     * @throws Exception
     *             このメソッドで発生した例外
     */
    public void testReset02() throws Exception {
        // リクエストの生成
        MockHttpServletRequest request = new MockHttpServletRequest();
        // セッションの生成
        MockHttpSession session = new MockHttpSession();
        // コンテキストの生成
        MockServletContext context = new MockServletContext();
        // アクションサーブレットの生成
        DynaValidatorActionFormEx_ActionServletStub01 servlet = new DynaValidatorActionFormEx_ActionServletStub01();
        // プレフィックスの生成
        String prefix = "";
        // モジュール設定情報の生成
        ModuleConfig moduleConfig = new ModuleConfigImpl(prefix);
        // マッピング情報の生成
        ActionMapping mapping = new ActionMapping();

        // 設定
        request.setSession(session);
        request.setAttribute(Globals.MODULE_KEY, moduleConfig);
        session.setServletContext(context);
        context.setAttribute(Resetter.RESETTER_KEY + prefix, new Object());
        context.setAttribute(Globals.MODULE_KEY, moduleConfig);
        this.formEx.setServlet(servlet);
        servlet.setContext(context);

        // テスト実行
        this.formEx.reset(mapping, request);

        // テスト結果確認
        assertTrue(LogUTUtil.checkError("", new ClassCastException()));
    }

    /**
     * testReset03() <br>
     * <br>
     *
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) mapping:not null<br>
     *         (引数) request:デフォルトモジュールを格納したリクエストオブジェクト<br>
     *         (状態) resetter:Resetterオブジェクト（DynaValidatorActionFormEx_ResetterStub01）<br>
     *
     * <br>
     * 期待値：(戻り値) void:－<br>
     *         (状態変化) DynaValidatorActionFormEx_ResetterStub01#reset():実行<br>
     *         (状態変化) ログ:－<br>
     *
     * <br>
     * デフォルトモジュールを格納したリクエストオブジェクトを設定し、resetterがResetterオブジェクトの場合、reset()メソッドが呼ばれることを確認する。
     * <br>
     *
     * @throws Exception
     *             このメソッドで発生した例外
     */
    public void testReset03() throws Exception {
        // リクエストの生成
        MockHttpServletRequest request = new MockHttpServletRequest();
        // セッションの生成
        MockHttpSession session = new MockHttpSession();
        // コンテキストの生成
        MockServletContext context = new MockServletContext();
        // アクションサーブレットの生成
        DynaValidatorActionFormEx_ActionServletStub01 servlet = new DynaValidatorActionFormEx_ActionServletStub01();
        // プレフィックスの生成
        String prefix = "";
        // モジュール設定情報の生成
        ModuleConfig moduleConfig = new ModuleConfigImpl(prefix);
        // マッピング情報の生成
        ActionMapping mapping = new ActionMapping();
        // リセッターの生成
        ResetterImpl resetter = new DynaValidatorActionFormEx_ResetterStub01();

        // 設定
        request.setSession(session);
        request.setAttribute(Globals.MODULE_KEY, moduleConfig);
        session.setServletContext(context);
        context.setAttribute(Resetter.RESETTER_KEY + prefix, resetter);
        context.setAttribute(Globals.MODULE_KEY, moduleConfig);
        this.formEx.setServlet(servlet);
        servlet.setContext(context);

        // テスト実行
        this.formEx.reset(mapping, request);

        // テスト結果確認
        assertNotNull(request.getAttribute("SUCCESS"));
    }

    /**
     * testReset04() <br>
     * <br>
     *
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) mapping:not null<br>
     *         (引数) request:サブモジュールを格納したリクエストオブジェクト<br>
     *         (状態) resetter:null<br>
     *
     * <br>
     * 期待値：(戻り値) void:－<br>
     *         (状態変化) DynaValidatorActionFormEx_ResetterStub01#reset():未実行<br>
     *         (状態変化) ログ:－<br>
     *
     * <br>
     * サブモジュールを格納したリクエストオブジェクトを設定し、Resetterがnullだった場合、変化せずに終了することを確認する。 <br>
     *
     * @throws Exception
     *             このメソッドで発生した例外
     */
    public void testReset04() throws Exception {
        // リクエストの生成
        MockHttpServletRequest request = new MockHttpServletRequest();
        // セッションの生成
        MockHttpSession session = new MockHttpSession();
        // コンテキストの生成
        MockServletContext context = new MockServletContext();
        // アクションサーブレットの生成
        DynaValidatorActionFormEx_ActionServletStub01 servlet = new DynaValidatorActionFormEx_ActionServletStub01();
        // プレフィックスの生成
        String prefix = "sub1";
        // モジュール設定情報の生成
        ModuleConfig moduleConfig = new ModuleConfigImpl(prefix);
        // マッピング情報の生成
        ActionMapping mapping = new ActionMapping();

        // 設定
        request.setSession(session);
        request.setAttribute(Globals.MODULE_KEY, moduleConfig);
        session.setServletContext(context);
        context.setAttribute(Resetter.RESETTER_KEY + prefix, null);
        context.setAttribute(Globals.MODULE_KEY, moduleConfig);
        this.formEx.setServlet(servlet);
        servlet.setContext(context);

        // テスト実行
        this.formEx.reset(mapping, request);
    }

    /**
     * testReset05() <br>
     * <br>
     *
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) mapping:not null<br>
     *         (引数) request:サブモジュールを格納したリクエストオブジェクト<br>
     *         (状態) resetter:Resetter以外のオブジェクト<br>
     *
     * <br>
     * 期待値：(戻り値) void:－<br>
     *         (状態変化) DynaValidatorActionFormEx_ResetterStub01#reset():未実行<br>
     *         (状態変化) ログ:＜ログ＞<br>
     *                    エラーログ：""<br>
     *                    ＜例外＞<br>
     *                    ClassCastException<br>
     *
     * <br>
     * サブモジュールを格納したリクエストオブジェクトを設定し、Resetter以外のオブジェクトを取得した場合、ログを出力し、終了することを確認する。
     * <br>
     *
     * @throws Exception
     *             このメソッドで発生した例外
     */
    public void testReset05() throws Exception {
        // リクエストの生成
        MockHttpServletRequest request = new MockHttpServletRequest();
        // セッションの生成
        MockHttpSession session = new MockHttpSession();
        // コンテキストの生成
        MockServletContext context = new MockServletContext();
        // アクションサーブレットの生成
        DynaValidatorActionFormEx_ActionServletStub01 servlet = new DynaValidatorActionFormEx_ActionServletStub01();
        // プレフィックスの生成
        String prefix = "sub1";
        // モジュール設定情報の生成
        ModuleConfig moduleConfig = new ModuleConfigImpl(prefix);
        // マッピング情報の生成
        ActionMapping mapping = new ActionMapping();

        // 設定
        request.setSession(session);
        request.setAttribute(Globals.MODULE_KEY, moduleConfig);
        session.setServletContext(context);
        context.setAttribute(Resetter.RESETTER_KEY + prefix, new Object());
        context.setAttribute(Globals.MODULE_KEY, moduleConfig);
        this.formEx.setServlet(servlet);
        servlet.setContext(context);

        // テスト実行
        this.formEx.reset(mapping, request);

        // テスト結果確認
        assertTrue(LogUTUtil.checkError("", new ClassCastException()));
    }

    /**
     * testReset06() <br>
     * <br>
     *
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) mapping:not null<br>
     *         (引数) request:サブモジュールを格納したリクエストオブジェクト<br>
     *         (状態) resetter:Resetterオブジェクト（DynaValidatorActionFormEx_ResetterStub01）<br>
     *
     * <br>
     * 期待値：(戻り値) void:－<br>
     *         (状態変化) DynaValidatorActionFormEx_ResetterStub01#reset():実行<br>
     *         (状態変化) ログ:－<br>
     *
     * <br>
     * サブモジュールを格納したリクエストオブジェクトを設定し、resetterがResetterオブジェクトの場合、reset()メソッドが呼ばれることを確認する。。
     * <br>
     *
     * @throws Exception
     *             このメソッドで発生した例外
     */
    public void testReset06() throws Exception {
        // リクエストの生成
        MockHttpServletRequest request = new MockHttpServletRequest();
        // セッションの生成
        MockHttpSession session = new MockHttpSession();
        // コンテキストの生成
        MockServletContext context = new MockServletContext();
        // アクションサーブレットの生成
        DynaValidatorActionFormEx_ActionServletStub01 servlet = new DynaValidatorActionFormEx_ActionServletStub01();
        // プレフィックスの生成
        String prefix = "sub1";
        // モジュール設定情報の生成
        ModuleConfig moduleConfig = new ModuleConfigImpl(prefix);
        // マッピング情報の生成
        ActionMapping mapping = new ActionMapping();
        // リセッターの生成
        ResetterImpl resetter = new DynaValidatorActionFormEx_ResetterStub01();

        // 設定
        request.setSession(session);
        request.setAttribute(Globals.MODULE_KEY, moduleConfig);
        session.setServletContext(context);
        context.setAttribute(Resetter.RESETTER_KEY + prefix, resetter);
        context.setAttribute(Globals.MODULE_KEY, moduleConfig);
        this.formEx.setServlet(servlet);
        servlet.setContext(context);

        // テスト実行
        this.formEx.reset(mapping, request);

        // テスト結果確認
        assertNotNull(request.getAttribute("SUCCESS"));
    }

    /**
     * testSetStringintObject01()
     * <br><br>
     *
     * (異常系)
     * <br>
     * 観点：G
     * <br><br>
     * 入力値：(引数) name:null<br>
     *         (引数) index:0<br>
     *         (引数) value:"hello"<br>
     *
     * <br>
     * 期待値：(状態変化) 例外:NullPointerException：<br>
     *                    メッセージ："No indexed value for 'null[0]'"<br>
     *         (状態変化) nameで指定されたフィールド:－<br>
     *
     * <br>
     * nameにnullを設定し、NullPointerExceptionがスローされることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetStringintObject01() throws Exception {
        //テスト実行
        try {
            this.formEx.set(null, 0, "hello");
            fail();
        } catch (NullPointerException e) {
            //テスト結果確認
            assertEquals("No indexed value for 'null[0]'", e.getMessage());
        }
    }

    /**
     * testSetStringintObject02()
     * <br><br>
     *
     * (異常系)
     * <br>
     * 観点：G
     * <br><br>
     * 入力値：(引数) name:空文字<br>
     *         (引数) index:0<br>
     *         (引数) value:"hello"<br>
     *
     * <br>
     * 期待値：(状態変化) 例外:NullPointerException：<br>
     *                    メッセージ："No indexed value for '[0]'"<br>
     *         (状態変化) nameで指定されたフィールド:－<br>
     *
     * <br>
     * nameに空文字を設定し、NullPointerExceptionがスローされることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetStringintObject02() throws Exception {
        //テスト実行
        try {
            this.formEx.set("", 0, "hello");
            fail();
        } catch (NullPointerException e) {
            //テスト結果確認
            assertEquals("No indexed value for '[0]'", e.getMessage());
        }
    }

    /**
     * testSetStringintObject03()
     * <br><br>
     *
     * (異常系)
     * <br>
     * 観点：G
     * <br><br>
     * 入力値：(引数) name:"foo"（存在しないフィールド名）<br>
     *         (引数) index:0<br>
     *         (引数) value:"hello"<br>
     *
     * <br>
     * 期待値：(状態変化) 例外:NullPointerException：<br>
     *                    メッセージ："No indexed value for 'foo[0]'"<br>
     *         (状態変化) nameで指定されたフィールド:－<br>
     *
     * <br>
     * nameに存在しないフィールド名を設定し、NullPointerExceptionがスローされることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetStringintObject03() throws Exception {
        //テスト実行
        try {
            this.formEx.set("foo", 0, "hello");
            fail();
        } catch (NullPointerException e) {
            //テスト結果確認
            assertEquals("No indexed value for 'foo[0]'", e.getMessage());
        }
    }

    /**
     * testSetStringintObject04()
     * <br><br>
     *
     * (異常系)
     * <br>
     * 観点：G
     * <br><br>
     * 入力値：(引数) name:"hogeString"<br>
     *         (引数) index:0<br>
     *         (引数) value:"hello"<br>
     *
     * <br>
     * 期待値：(状態変化) 例外:IllegalArgumentException：<br>
     *                    メッセージ："Non-indexed property for 'hogeString[0]'"<br>
     *         (状態変化) nameで指定されたフィールド:－<br>
     *
     * <br>
     * nameにString型のフィールド名を設定し、IllegalArgumentExceptionがスローされることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetStringintObject04() throws Exception {
        //テスト実行
        try {
            this.formEx.set("hogeString", 0, "hello");
            fail();
        } catch (IllegalArgumentException e) {
            //テスト結果確認
            assertEquals("Non-indexed property for 'hogeString[0]'", e
                    .getMessage());
        }
    }

    /**
     * testSetStringintObject05()
     * <br><br>
     *
     * (異常系)
     * <br>
     * 観点：G
     * <br><br>
     * 入力値：(引数) name:"hogeStringArray"<br>
     *         (引数) index:-1<br>
     *         (引数) value:"hello"<br>
     *         (状態) nameで指定されたフィールド:String配列型:["data1", "data2", "data3", "data4"]<br>
     *
     * <br>
     * 期待値：(状態変化) 例外:ArrayIndexOutOfBoundsException<br>
     *         (状態変化) nameで指定されたフィールド:－<br>
     *
     * <br>
     * nameにString[]型のフィールド名を設定し、かつIndexに-1を設定し、ArrayIndexOutOfBoundsExceptionがスローされることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetStringintObject05() throws Exception {
        //テスト実行
        try {
            this.formEx.set("hogeStringArray", -1, "hello");
            fail();
        } catch (ArrayIndexOutOfBoundsException e) {
            //テスト結果確認
        }
    }

    /**
     * testSetStringintObject06()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) name:"hogeStringArray"<br>
     *         (引数) index:0<br>
     *         (引数) value:null<br>
     *         (状態) nameで指定されたフィールド:String配列型:["data1", "data2", "data3", "data4"]<br>
     *
     * <br>
     * 期待値：(状態変化) nameで指定されたフィールド:hogeStringArray[0] = null<br>
     *
     * <br>
     * nameにString[]型のフィールド名を設定し、かつIndexに0を設定し、添字0に引数valueが格納されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetStringintObject06() throws Exception {

        //テスト実行
        this.formEx.set("hogeStringArray", 0, null);

        //テスト結果確認
        String[] resultArray = (String[]) ((HashMap) UTUtil.getPrivateField(
                this.formEx, "dynaValues")).get("hogeStringArray");
        assertNull(resultArray[0]);
    }

    /**
     * testSetStringintObject07()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) name:"hogeStringArray"<br>
     *         (引数) index:1<br>
     *         (引数) value:"hello"<br>
     *         (状態) nameで指定されたフィールド:String配列型:["data1", "data2", "data3", "data4"]<br>
     *
     * <br>
     * 期待値：(状態変化) nameで指定されたフィールド:hogeStringArray[1] = "hello"<br>
     *
     * <br>
     * nameにString[]型のフィールド名を設定し、かつIndexに1を設定し、添字1に引数valueが格納されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetStringintObject07() throws Exception {
        //テスト実行
        this.formEx.set("hogeStringArray", 1, "hello");

        //テスト結果確認
        String[] resultArray = (String[]) ((HashMap) UTUtil.getPrivateField(
                this.formEx, "dynaValues")).get("hogeStringArray");
        assertEquals("hello", resultArray[1]);
    }

    /**
     * testSetStringintObject08()
     * <br><br>
     *
     * (異常系)
     * <br>
     * 観点：G
     * <br><br>
     * 入力値：(引数) name:"hogeStringArray"<br>
     *         (引数) index:2<br>
     *         (引数) value:Integer(5)<br>
     *         (状態) nameで指定されたフィールド:String配列型:["data1", "data2", "data3", "data4"]<br>
     *
     * <br>
     * 期待値：(状態変化) 例外:IllegalArgumentException<br>
     *         (状態変化) nameで指定されたフィールド:－<br>
     *
     * <br>
     * nameにString[]型のフィールド名を設定し、かつIndexに2を設定し、valueが不適切な型の場合、IllegalArgumentExceptionがスローされることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetStringintObject08() throws Exception {
        //テスト実行
        try {
            this.formEx.set("hogeStringArray", 2, new Integer(5));
            fail();
        } catch (IllegalArgumentException e) {
            //テスト結果確認;
        }
    }

    /**
     * testSetStringintObject09()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) name:"hogeStringArray"<br>
     *         (引数) index:10<br>
     *         (引数) value:null<br>
     *         (状態) nameで指定されたフィールド:String配列型:["data1", "data2", "data3", "data4"]<br>
     *
     * <br>
     * 期待値：(状態変化) nameで指定されたフィールド:hogeStringArray[10] = null<br>
     *
     * <br>
     * nameにサイズ4のString[]型のフィールド名を設定し、かつIndexに10を設定し、添字10に引数valueが格納されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetStringintObject09() throws Exception {
        //テスト実行
        this.formEx.set("hogeStringArray", 10, null);

        //テスト結果確認
        String[] resultArray = (String[]) ((HashMap) UTUtil.getPrivateField(
                this.formEx, "dynaValues")).get("hogeStringArray");
        assertNull(resultArray[10]);
    }

    /**
     * testSetStringintObject10()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) name:"hogeStringArray"<br>
     *         (引数) index:11<br>
     *         (引数) value:"hello"<br>
     *         (状態) nameで指定されたフィールド:String配列型:["data1", "data2", "data3", "data4"]<br>
     *
     * <br>
     * 期待値：(状態変化) nameで指定されたフィールド:hogeStringArray[11] = "hello"<br>
     *
     * <br>
     * nameにサイズ4のString[]型のフィールド名を設定し、かつIndexに11を設定し、添字11に引数valueが格納されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetStringintObject10() throws Exception {
        //テスト実行
        this.formEx.set("hogeStringArray", 11, "hello");

        //テスト結果確認
        String[] resultArray = (String[]) ((HashMap) UTUtil.getPrivateField(
                this.formEx, "dynaValues")).get("hogeStringArray");
        assertEquals("hello", resultArray[11]);
    }

    /**
     * testSetStringintObject11()
     * <br><br>
     *
     * (異常系)
     * <br>
     * 観点：G
     * <br><br>
     * 入力値：(引数) name:"hogeStringArray"<br>
     *         (引数) index:12<br>
     *         (引数) value:Integer(5)<br>
     *         (状態) nameで指定されたフィールド:String配列型:["data1", "data2", "data3", "data4"]<br>
     *
     * <br>
     * 期待値：(状態変化) 例外:IllegalArgumentException<br>
     *         (状態変化) nameで指定されたフィールド:－<br>
     *
     * <br>
     * nameにサイズ4のString[]型のフィールド名を設定し、かつIndexに12を設定し、valueが不適切な型の場合、IllegalArgumentExceptionがスローされることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetStringintObject11() throws Exception {
        //テスト実行
        try {
            this.formEx.set("hogeStringArray", 12, new Integer(5));
            fail();
        } catch (IllegalArgumentException e) {
            //テスト結果確認
        }
    }

    /**
     * testSetStringintObject12()
     * <br><br>
     *
     * (異常系)
     * <br>
     * 観点：G
     * <br><br>
     * 入力値：(引数) name:"hogeList"<br>
     *         (引数) index:-1<br>
     *         (引数) value:"hello"<br>
     *         (状態) nameで指定されたフィールド:List型:["data1","data2", "data3", "data4"]<br>
     *
     * <br>
     * 期待値：(状態変化) 例外:IndexOutOfBoundsException<br>
     *         (状態変化) nameで指定されたフィールド:－<br>
     *
     * <br>
     * nameにList型のフィールド名を設定し、かつIndexに-1を設定し、IndexOutOfBoundsExceptionがスローされることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetStringintObject12() throws Exception {
        //テスト実行
        try {
            this.formEx.set("hogeList", -1, "hello");
            fail();
        } catch (IndexOutOfBoundsException e) {
            //テスト結果確認
        }
    }

    /**
     * testSetStringintObject13()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) name:"hogeList"<br>
     *         (引数) index:0<br>
     *         (引数) value:null<br>
     *         (状態) nameで指定されたフィールド:List型:["data1","data2", "data3", "data4"]<br>
     *
     * <br>
     * 期待値：(状態変化) nameで指定されたフィールド:hogeList.get(0) = null<br>
     *
     * <br>
     * nameにList型のフィールド名を設定し、かつIndexに0を設定し、添字0に引数valueが格納されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetStringintObject13() throws Exception {
        // 前提条件の確認
        assertNotNull(this.formEx.get("hogeList", 0));

        //テスト実行
        this.formEx.set("hogeList", 0, null);

        //テスト結果確認
        List resultList = (List) ((HashMap) UTUtil.getPrivateField(
                this.formEx, "dynaValues")).get("hogeList");
        assertNull(resultList.get(0));
    }

    /**
     * testSetStringintObject14()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) name:"hogeList"<br>
     *         (引数) index:1<br>
     *         (引数) value:"hello"<br>
     *         (状態) nameで指定されたフィールド:List型:["data1","data2", "data3", "data4"]<br>
     *
     * <br>
     * 期待値：(状態変化) nameで指定されたフィールド:hogeList.get(1) = "hello"<br>
     *
     * <br>
     * nameにList型のフィールド名を設定し、かつIndexに1を設定し、添字1に引数valueが格納されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetStringintObject14() throws Exception {
        //テスト実行
        this.formEx.set("hogeList", 1, "hello");

        //テスト結果確認
        List resultList = (List) ((HashMap) UTUtil.getPrivateField(
                this.formEx, "dynaValues")).get("hogeList");
        assertEquals("hello", resultList.get(1));
    }

    /**
     * testSetStringintObject15()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) name:"hogeList"<br>
     *         (引数) index:2<br>
     *         (引数) value:Integer(5)<br>
     *         (状態) nameで指定されたフィールド:List型:["data1","data2", "data3", "data4"]<br>
     *
     * <br>
     * 期待値：(状態変化) nameで指定されたフィールド:hogeList.get(2) = Integer(5)<br>
     *
     * <br>
     * nameにList型のフィールド名を設定し、かつIndexに2を設定し、添字2に引数valueが格納されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetStringintObject15() throws Exception {
        //テスト実行
        this.formEx.set("hogeList", 2, new Integer(5));

        //テスト結果確認
        List resultList = (List) ((HashMap) UTUtil.getPrivateField(
                this.formEx, "dynaValues")).get("hogeList");
        assertEquals(new Integer(5), resultList.get(2));
    }

    /**
     * testSetStringintObject16()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) name:"hogeList"<br>
     *         (引数) index:10<br>
     *         (引数) value:null<br>
     *         (状態) nameで指定されたフィールド:List型:["data1","data2", "data3", "data4"]<br>
     *
     * <br>
     * 期待値：(状態変化) nameで指定されたフィールド:hogeList.get(10) = null<br>
     *
     * <br>
     * nameにサイズ4のList型のフィールド名を設定し、かつIndexに10を設定し、添字10に引数valueが格納されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetStringintObject16() throws Exception {
        //テスト実行
        this.formEx.set("hogeList", 10, null);

        //テスト結果確認
        List resultList = (List) ((HashMap) UTUtil.getPrivateField(
                this.formEx, "dynaValues")).get("hogeList");
        assertNull(resultList.get(10));
    }

    /**
     * testSetStringintObject17()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) name:"hogeList"<br>
     *         (引数) index:11<br>
     *         (引数) value:"hello"<br>
     *         (状態) nameで指定されたフィールド:List型:["data1","data2", "data3", "data4"]<br>
     *
     * <br>
     * 期待値：(状態変化) nameで指定されたフィールド:hogeList.get(11) = "hello"<br>
     *
     * <br>
     * nameにサイズ4のList型のフィールド名を設定し、かつIndexに11を設定し、添字11に引数valueが格納されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetStringintObject17() throws Exception {
        //テスト実行
        this.formEx.set("hogeList", 11, "hello");

        //テスト結果確認
        List resultList = (List) ((HashMap) UTUtil.getPrivateField(
                this.formEx, "dynaValues")).get("hogeList");
        assertEquals("hello", resultList.get(11));
    }

    /**
     * testSetStringintObject18()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) name:"hogeList"<br>
     *         (引数) index:12<br>
     *         (引数) value:Integer(5)<br>
     *         (状態) nameで指定されたフィールド:List型:["data1","data2", "data3", "data4"]<br>
     *
     * <br>
     * 期待値：(状態変化) nameで指定されたフィールド:hogeList.get(12) = Integer(5)<br>
     *
     * <br>
     * nameにサイズ4のList型のフィールド名を設定し、かつIndexに12を設定し、添字12に引数valueが格納されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetStringintObject18() throws Exception {
        //テスト実行
        this.formEx.set("hogeList", 12, new Integer(5));

        //テスト結果確認
        List resultList = (List) ((HashMap) UTUtil.getPrivateField(
                this.formEx, "dynaValues")).get("hogeList");
        assertEquals(new Integer(5), resultList.get(12));
    }

    /**
     * testSetStringintObject19()
     * <br><br>
     *
     * (異常系)
     * <br>
     * 観点：G
     * <br><br>
     * 入力値：(引数) name:"hogeIntArray"<br>
     *         (引数) index:-1<br>
     *         (引数) value:"hello"<br>
     *         (状態) nameで指定されたフィールド:int配列型:[-100, 0, 10, 111]<br>
     *
     * <br>
     * 期待値：(状態変化) 例外:ArrayIndexOutOfBoundsException<br>
     *         (状態変化) nameで指定されたフィールド:－<br>
     *
     * <br>
     * nameにint[]型のフィールド名を設定し、ArrayIndexOutOfBoundsExceptionがスローされることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetStringintObject19() throws Exception {
        //テスト実行
        try {
            this.formEx.set("hogeIntArray", -1, "hello");
            fail();
        } catch (ArrayIndexOutOfBoundsException e) {
            //テスト結果確認
        }
    }

    /**
     * testSetStringintObject20()
     * <br><br>
     *
     * (異常系)
     * <br>
     * 観点：G
     * <br><br>
     * 入力値：(引数) name:"hogeIntArray"<br>
     *         (引数) index:0<br>
     *         (引数) value:null<br>
     *         (状態) nameで指定されたフィールド:int配列型:[-100, 0, 10, 111]<br>
     *
     * <br>
     * 期待値：(状態変化) 例外:IllegalArgumentException<br>
     *         (状態変化) nameで指定されたフィールド:－<br>
     *
     * <br>
     * nameにint[]型のフィールド名を設定し、IllegalArgumentExceptionがスローされることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetStringintObject20() throws Exception {
        //テスト実行
        try {
            this.formEx.set("hogeIntArray", 0, null);
            fail();
        } catch (IllegalArgumentException e) {
            //テスト結果確認
        }
    }

    /**
     * testSetStringintObject21()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) name:"hogeIntArray"<br>
     *         (引数) index:1<br>
     *         (引数) value:Integer(5)<br>
     *         (状態) nameで指定されたフィールド:int配列型:[-100, 0, 10, 111]<br>
     *
     * <br>
     * 期待値：(状態変化) nameで指定されたフィールド:hogeStringArray[1] = Integer(5)<br>
     *
     * <br>
     * nameにint[]型のフィールド名を設定し、かつIndexに"1"を設定し、添字"1"にvalueが格納されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetStringintObject21() throws Exception {
        //テスト実行
        this.formEx.set("hogeIntArray", 1, new Integer(5));

        //テスト結果確認
        int[] resultArray = (int[]) ((HashMap) UTUtil.getPrivateField(
                this.formEx, "dynaValues")).get("hogeIntArray");
        assertEquals(new Integer(5), (Integer) resultArray[1]);
    }

    /**
     * testSetStringintObject22()
     * <br><br>
     *
     * (異常系)
     * <br>
     * 観点：G
     * <br><br>
     * 入力値：(引数) name:"hogeIntArray"<br>
     *         (引数) index:2<br>
     *         (引数) value:"hello"<br>
     *         (状態) nameで指定されたフィールド:int配列型:[-100, 0, 10, 111]<br>
     *
     * <br>
     * 期待値：(状態変化) 例外:IllegalArgumentException<br>
     *         (状態変化) nameで指定されたフィールド:－<br>
     *
     * <br>
     * nameにint[]型のフィールド名を設定し、かつIndexに"2"を設定し、valueが不適切な型の場合、IllegalArgumentExceptionがスローされることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetStringintObject22() throws Exception {
        //テスト実行
        try {
            this.formEx.set("hogeIntArray", 2, "hello");
            fail();
        } catch (IllegalArgumentException e) {
            //テスト結果確認
        }
    }

    /**
     * testSetStringintObject23()
     * <br><br>
     *
     * (異常系)
     * <br>
     * 観点：G
     * <br><br>
     * 入力値：(引数) name:"hogeIntArray"<br>
     *         (引数) index:10<br>
     *         (引数) value:null<br>
     *         (状態) nameで指定されたフィールド:int配列型:[-100, 0, 10, 111]<br>
     *
     * <br>
     * 期待値：(状態変化) 例外:IllegalArgumentException<br>
     *         (状態変化) nameで指定されたフィールド:－<br>
     *
     * <br>
     * nameにサイズ"4"のint[]型のフィールド名を設定し、かつIndexに"10"を設定し、IllegalArgumentExceptionがスローされることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetStringintObject23() throws Exception {
        //テスト実行
        try {
            this.formEx.set("hogeIntArray", 10, null);
            fail();
        } catch (IllegalArgumentException e) {
            //テスト結果確認
        }
    }

    /**
     * testSetStringintObject24()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) name:"hogeIntArray"<br>
     *         (引数) index:11<br>
     *         (引数) value:Integer(5)<br>
     *         (状態) nameで指定されたフィールド:int配列型:[-100, 0, 10, 111]<br>
     *
     * <br>
     * 期待値：(状態変化) nameで指定されたフィールド:hogeStringArray[11] = Integer(5)<br>
     *
     * <br>
     * nameにサイズ"4"のint[]型のフィールド名を設定し、かつIndexに"11"を設定し、valueが格納されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetStringintObject24() throws Exception {
        //テスト実行
        this.formEx.set("hogeIntArray", 11, new Integer(5));

        //テスト結果確認
        int[] resultArray = (int[]) ((HashMap) UTUtil.getPrivateField(
                this.formEx, "dynaValues")).get("hogeIntArray");
        assertEquals(new Integer(5), (Integer) resultArray[11]);
    }

    /**
     * testSetStringintObject25()
     * <br><br>
     *
     * (異常系)
     * <br>
     * 観点：G
     * <br><br>
     * 入力値：(引数) name:"hogeIntArray"<br>
     *         (引数) index:12<br>
     *         (引数) value:"hello"<br>
     *         (状態) nameで指定されたフィールド:int配列型:[-100, 0, 10, 111]<br>
     *
     * <br>
     * 期待値：(状態変化) 例外:IllegalArgumentException<br>
     *         (状態変化) nameで指定されたフィールド:－<br>
     *
     * <br>
     * nameにサイズ"4"のint[]型のフィールド名を設定し、かつIndexに"12"を設定し、valueが不適切な型の場合、IllegalArgumentExceptionがスローされることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetStringintObject25() throws Exception {
        //テスト実行
        try {
            this.formEx.set("hogeIntArray", 12, "hello");
        } catch (IllegalArgumentException e) {
            //テスト結果確認
        }
    }

    /**
     * testIsModified01()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) なし:－<br>
     *         (状態) modified:true<br>
     *
     * <br>
     * 期待値：(戻り値) boolean:true<br>
     *
     * <br>
     * 格納されているmodifiedを正常に取得すること。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testIsModified01() throws Exception {
        // 前処理
        UTUtil.setPrivateField(this.formEx, "modified", true);

        // テスト実施・判定
        assertTrue(this.formEx.isModified());
    }

    /**
     * testSetModified01()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) modified:true<br>
     *         (状態) modified:false<br>
     *
     * <br>
     * 期待値：(戻り値) void:－<br>
     *         (状態変化) modified:true<br>
     *
     * <br>
     * 引数に指定した値がmodifiedに正常に格納されること。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetModified01() throws Exception {
        // テスト実行
        this.formEx.setModified(true);

        // テスト結果
        assertTrue((Boolean) UTUtil.getPrivateField(formEx, "modified"));
    }

    /**
     * testSetIndexedValue01()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) name:hoge<br>
     *                index:100<br>
     *                value:hoge<br>
     *
     * <br>
     * 期待値：(戻り値) void:－<br>
     *         (状態変化) 指定した引数でsetメソッドが呼び出されること<br>
     *
     * <br>
     * 引数に指定した値でsetメソッドを呼び出すことを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetIndexedValue01() throws Exception {
        DynaValidatorActionFormExStub01 stub =
            new DynaValidatorActionFormExStub01();
        stub.setIndexedValue("hoge", 100, "hoge");

        assertEquals("hoge", stub.name);
        assertEquals(100, stub.index);
        assertEquals("hoge", stub.value);
    }

    /**
     * testGetIndexedValue01()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) name:hoge<br>
     *                index:100<br>
     *
     * <br>
     * 期待値：(戻り値) Object:"hoge"<br>
     *         (状態変化) 指定した引数でgetメソッドが呼び出されること<br>
     *
     * <br>
     * 引数に指定した値でgetメソッドを呼び出すことを確認する。。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetIndexedValue01() throws Exception {
        DynaValidatorActionFormExStub01 stub =
            new DynaValidatorActionFormExStub01();
        Object result = stub.getIndexedValue("hoge", 100);

        assertEquals("hoge", stub.name);
        assertEquals(100, stub.index);
        assertEquals("called", result);
    }

}
