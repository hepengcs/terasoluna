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

package jp.terasoluna.fw.web.codelist;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import jp.terasoluna.utlib.UTUtil;
import junit.framework.TestCase;

/**
 * {@link jp.terasoluna.fw.web.codelist.MappedCodeListLoader} クラス
 * のブラックボックステスト。
 *
 * <p>
 * <h4>【クラスの概要】</h4>
 * コードリスト情報の初期化をMapで行う、
 * jp.terasoluna.fw.web.codelist.CodeListLoader実装クラス。
 * <p>
 *
 * @see jp.terasoluna.fw.web.codelist.MappedCodeListLoader
 */
public class MappedCodeListLoaderTest extends TestCase {

    /**
     * テスト対象。
     */
    private MappedCodeListLoader test = null;

    /**
     * このテストケースを実行する為の
     * GUI アプリケーションを起動する。
     *
     * @param args java コマンドに設定されたパラメータ
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(MappedCodeListLoaderTest.class);
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
        test = new MappedCodeListLoader();
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
    public MappedCodeListLoaderTest(String name) {
        super(name);
    }

    /**
     * testGetCodeListMap01()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(状態) codeListMap:not null<br>
     *
     * <br>
     * 期待値：(戻り値) Map:インスタンス変数のcodeListMap<br>
     *
     * <br>
     * インスタンス変数のcodeListMapが取得できることを確認する。<br>
     * ※正常系1件のみ確認。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetCodeListMap01() throws Exception {
        // 前処理
        Map map = new HashMap();
        UTUtil.setPrivateField(test, "codeListMap", map);

        // テスト実施
        Object result = test.getCodeListMap();

        // 判定
        assertSame(map, result);
    }

    /**
     * testSetCodeListMap01()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) codeListMap:not null<br>
     *         (状態) codeListMap:null<br>
     *
     * <br>
     * 期待値：(状態変化) codeListMap:not null（引数に指定したMap）<br>
     *
     * <br>
     * 引数に指定したMapがインスタンス変数codeListMapに設定されることを確認する。<br>
     * ※正常系1件のみ確認。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetCodeListMap01() throws Exception {
        // 前処理
        UTUtil.setPrivateField(test, "codeListMap", null);

        // テスト実施
        Map<String, String> map = new HashMap<String, String>();
        test.setCodeListMap(map);

        // 判定
        Object result = UTUtil.getPrivateField(test, "codeListMap");
        assertSame(map, result);
    }

    /**
     * testLoad01()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(状態) codeLists:not null<br>
     *
     * <br>
     * 期待値：(状態変化) codeLists:変化なし<br>
     *
     * <br>
     * インスタンス変数のcodeListsがnot nullの場合、何も行われず処理が終了することを確認する。codeLists変数に変化がないことを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testLoad01() throws Exception {
        // 前処理
        CodeBean cb = new CodeBean();
        cb.setId("id");
        cb.setName("name");
        List<CodeBean> list = new ArrayList<CodeBean>();
        list.add(cb);
        UTUtil.setPrivateField(test, "codeLists", list);

        // テスト実施
        test.load();

        // 判定
        List codeLists = (List) UTUtil.getPrivateField(test, "codeLists");
        assertSame(list, codeLists);
        assertEquals(1, codeLists.size());
        CodeBean codebean = (CodeBean) codeLists.get(0);
        assertEquals("id", codebean.getId());
        assertEquals("name", codebean.getName());
    }

    /**
     * testLoad02()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(状態) codeLists:null<br>
     *         (状態) codeListMap:null<br>
     *
     * <br>
     * 期待値：(状態変化) codeLists:要素数が0のList<br>
     *
     * <br>
     * codeListMapがnullの場合、codeListsが要素数0のListとして設定されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testLoad02() throws Exception {
        // 前処理
        UTUtil.setPrivateField(test, "codeLists", null);
        UTUtil.setPrivateField(test, "codeListMap", null);

        // テスト実施
        test.load();

        // 判定
        List result = (List) UTUtil.getPrivateField(test, "codeLists");
        assertTrue(result.isEmpty());
    }

    /**
     * testLoad03()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：D
     * <br><br>
     * 入力値：(状態) codeLists:null<br>
     *         (状態) codeListMap:要素数0のMap<br>
     *
     * <br>
     * 期待値：(状態変化) codeLists:要素数が0のList<br>
     *
     * <br>
     * codeListMapの要素が空の場合、codeListsが要素数0のListとして設定されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testLoad03() throws Exception {
        // 前処理
        UTUtil.setPrivateField(test, "codeLists", null);
        Map<String, String> map = new HashMap<String, String>();
        UTUtil.setPrivateField(test, "codeListMap", map);

        // テスト実施
        test.load();

        // 判定
        List result = (List) UTUtil.getPrivateField(test, "codeLists");
        assertTrue(result.isEmpty());
    }

    /**
     * testLoad04()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：D
     * <br><br>
     * 入力値：(状態) codeLists:null<br>
     *         (状態) codeListMap:要素数1のMap<br>
     *                {"id","name"}<br>
     *
     * <br>
     * 期待値：(状態変化) codeLists:要素数が1のList<br>
     *                    CodeBean{"id","name"}<br>
     *
     * <br>
     * codeListMapに1件の要素がある場合、そのMapの内容でcodeListsが初期化されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testLoad04() throws Exception {
        // 前処理
        UTUtil.setPrivateField(test, "codeLists", null);
        Map<String, String> map = new HashMap<String, String>();
        map.put("id", "name");
        UTUtil.setPrivateField(test, "codeListMap", map);

        // テスト実施
        test.load();

        // 判定
        List result = (List) UTUtil.getPrivateField(test, "codeLists");
        assertEquals(1, result.size());
        CodeBean codebean = (CodeBean) result.get(0);
        assertEquals("id", codebean.getId());
        assertEquals("name", codebean.getName());
    }

    /**
     * testLoad05()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：D
     * <br><br>
     * 入力値：(状態) codeLists:null<br>
     *         (状態) codeListMap:要素数3のMap<br>
     *                {"id1","name1"},<br>
     *                {"id2","name2"},<br>
     *                {"id3","name3"}<br>
     *
     * <br>
     * 期待値：(状態変化) codeLists:要素数が1のList<br>
     *                    CodeBean{"id1","name1"},<br>
     *                    CodeBean{"id2","name2"},<br>
     *                    CodeBean{"id3","name3"},<br>
     *
     * <br>
     * codeListMapに複数件の要素がある場合、そのMapの内容でcodeListsが初期化されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testLoad05() throws Exception {
        // 前処理
        UTUtil.setPrivateField(test, "codeLists", null);
        Map<String, String> map = new TreeMap<String, String>();
        map.put("id1", "name1");
        map.put("id2", "name2");
        map.put("id3", "name3");
        UTUtil.setPrivateField(test, "codeListMap", map);

        // テスト実施
        test.load();

        // 判定
        List result = (List) UTUtil.getPrivateField(test, "codeLists");
        assertEquals(3, result.size());

        // 1件目
        CodeBean codebean = (CodeBean) result.get(0);
        assertEquals("id1", codebean.getId());
        assertEquals("name1", codebean.getName());

        // 2件目
        codebean = (CodeBean) result.get(1);
        assertEquals("id2", codebean.getId());
        assertEquals("name2", codebean.getName());

        // 3件目
        codebean = (CodeBean) result.get(2);
        assertEquals("id3", codebean.getId());
        assertEquals("name3", codebean.getName());
    }

    /**
     * testGetCodeBeans01()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(状態) codeLists:null<br>
     *
     * <br>
     * 期待値：(戻り値) CodeBean[]:要素0のCodeBean[]<br>
     *
     * <br>
     * インスタンス変数codeListsがnullの場合、長さ0のCodeBean配列が取得できることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetCodeBeans01() throws Exception {
        // 前処理
        UTUtil.setPrivateField(test, "codeLists", null);

        // テスト実施
        CodeBean[] result = test.getCodeBeans();

        // 判定
        assertEquals(0, result.length);
    }

    /**
     * testGetCodeBeans02()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：D
     * <br><br>
     * 入力値：(状態) codeLists:要素数0のList<br>
     *
     * <br>
     * 期待値：(戻り値) CodeBean[]:要素0のCodeBean[]<br>
     *
     * <br>
     * インスタンス変数codeListsが長さ0のListの場合、長さ0のCodeBean配列が取得できることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetCodeBeans02() throws Exception {
        // 前処理
        List<CodeBean> list = new ArrayList<CodeBean>();
        UTUtil.setPrivateField(test, "codeLists", list);

        // テスト実施
        CodeBean[] result = test.getCodeBeans();

        // 判定
        assertEquals(0, result.length);
    }

    /**
     * testGetCodeBeans03()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：D
     * <br><br>
     * 入力値：(状態) codeLists:要素数1<br>
     *                CodeBean{"id","name"}<br>
     *
     * <br>
     * 期待値：(戻り値) CodeBean[]:要素1のCodeBean[]<br>
     *                  CodeBean{"id","name"}<br>
     *
     * <br>
     * インスタンス変数codeListsが長さ1のListの場合、その要素のCodeBean配列が取得できることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetCodeBeans03() throws Exception {
        // 前処理
        List<CodeBean> list = new ArrayList<CodeBean>();
        CodeBean cb = new CodeBean();
        cb.setId("id");
        cb.setName("name");
        list.add(cb);
        UTUtil.setPrivateField(test, "codeLists", list);

        // テスト実施
        CodeBean[] result = test.getCodeBeans();

        // 判定
        assertEquals(1, result.length);
        assertEquals("id", result[0].getId());
        assertEquals("name", result[0].getName());
    }

    /**
     * testGetCodeBeans04()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：D
     * <br><br>
     * 入力値：(状態) codeLists:要素数3<br>
     *                CodeBean{"id1","name1"},<br>
     *                CodeBean{"id2","name2"},<br>
     *                CodeBean{"id3","name3"}<br>
     *
     * <br>
     * 期待値：(戻り値) CodeBean[]:要素3のCodeBean[]<br>
     *                  CodeBean{"id1","name1"},<br>
     *                  CodeBean{"id2","name2"},<br>
     *                  CodeBean{"id3","name3"}<br>
     *
     * <br>
     * インスタンス変数codeListsが複数の要素を持つ場合、その要素のCodeBean配列が取得できることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetCodeBeans04() throws Exception {
        // 前処理
        List<CodeBean> list = new ArrayList<CodeBean>();
        CodeBean cb = new CodeBean();
        cb.setId("id1");
        cb.setName("name1");
        list.add(cb);
        cb = new CodeBean();
        cb.setId("id2");
        cb.setName("name2");
        list.add(cb);
        cb = new CodeBean();
        cb.setId("id3");
        cb.setName("name3");
        list.add(cb);
        UTUtil.setPrivateField(test, "codeLists", list);

        // テスト実施
        CodeBean[] result = test.getCodeBeans();

        // 判定
        assertEquals(3, result.length);

        //1件目
        assertEquals("id1", result[0].getId());
        assertEquals("name1", result[0].getName());

        //2件目
        assertEquals("id2", result[1].getId());
        assertEquals("name2", result[1].getName());

        //3件目
        assertEquals("id3", result[2].getId());
        assertEquals("name3", result[2].getName());
    }

}
