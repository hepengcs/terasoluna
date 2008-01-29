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

package jp.terasoluna.fw.web.rich;

import java.util.HashSet;
import java.util.Set;
import jp.terasoluna.utlib.UTUtil;
import jp.terasoluna.fw.web.rich.ForbiddenURICheckerImpl;
import junit.framework.TestCase;

/**
 * {@link jp.terasoluna.fw.web.rich.ForbiddenURICheckerImpl}
 * クラスのブラックボックステスト。
 * 
 * <p>
 * <h4>【クラスの概要】</h4>
 * 文字列が許可するURIか判定するチェッカのデフォルト実装クラス。<br>
 * isAllowedURIの引き数はnot nullとする。
 * <p>
 * 
 * @see jp.terasoluna.fw.web.rich.ForbiddenURICheckerImpl
 */
public class ForbiddenURICheckerImplTest extends TestCase {

    /**
     * このテストケースを実行する為の
     * GUI アプリケーションを起動する。
     * 
     * @param args java コマンドに設定されたパラメータ
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(ForbiddenURICheckerImplTest.class);
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
    public ForbiddenURICheckerImplTest(String name) {
        super(name);
    }

    /**
     * testSetAllowedURISet01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：D
     * <br><br>
     * 入力値：(引数) allowedURISet:Setインスタンス<br>
     *         (状態) allowedURISet:null<br>
     *         
     * <br>
     * 期待値：(状態変化) allowedURISet:引き数と同一のインスタンス<br>
     *         
     * <br>
     * 引き数の値を正常に属性に設定すること。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetAllowedURISet01() throws Exception {
        // 前処理
        // allowedURISet
        Set<String> allowedURISet = new HashSet<String>();
        
        ForbiddenURICheckerImpl impl = new ForbiddenURICheckerImpl();
        UTUtil.setPrivateField(impl, "allowedURISet", null);

        // テスト実施
        impl.setAllowedURISet(allowedURISet);

        // 判定
        assertSame(allowedURISet, UTUtil.getPrivateField(impl, "allowedURISet"));
    }

    /**
     * testIsAllowedURI01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) requestURI:"AAA"<br>
     *         (状態) allowedURISet:{"AAA", "BBB", "CCC"}<br>
     *         
     * <br>
     * 期待値：(戻り値) boolean:true<br>
     *         
     * <br>
     * 属性のセットに引き数の文字列が含まれている場合、trueを返却することのテスト。（属性要素複数）
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testIsAllowedURI01() throws Exception {
        // 前処理
        // requestURI : "AAA"
        String requestURI = "AAA";
        
        // allowedURISet : "AAA", "BBB", "CCC"
        Set<String> allowedURISet = new HashSet<String>();
        allowedURISet.add("AAA");
        allowedURISet.add("BBB");
        allowedURISet.add("CCC");
        
        ForbiddenURICheckerImpl impl = new ForbiddenURICheckerImpl();
        UTUtil.setPrivateField(impl, "allowedURISet", allowedURISet);

        // テスト実施
        boolean b = impl.isAllowedURI(requestURI);

        // 判定
        assertTrue(b);
    }

    /**
     * testIsAllowedURI02()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) requestURI:"AAA"<br>
     *         (状態) allowedURISet:{"AAA"}<br>
     *         
     * <br>
     * 期待値：(戻り値) boolean:true<br>
     *         
     * <br>
     * 属性のセットに引き数の文字列が含まれている場合、trueを返却することのテスト。（属性要素単数）
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testIsAllowedURI02() throws Exception {
        // 前処理
        // requestURI : "AAA"
        String requestURI = "AAA";
        
        // allowedURISet : "AAA"
        Set<String> allowedURISet = new HashSet<String>();
        allowedURISet.add("AAA");
        
        ForbiddenURICheckerImpl impl = new ForbiddenURICheckerImpl();
        UTUtil.setPrivateField(impl, "allowedURISet", allowedURISet);

        // テスト実施
        boolean b = impl.isAllowedURI(requestURI);

        // 判定
        assertTrue(b);
    }

    /**
     * testIsAllowedURI03()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) requestURI:"AAA"<br>
     *         (状態) allowedURISet:{}<br>
     *         
     * <br>
     * 期待値：(戻り値) boolean:false<br>
     *         
     * <br>
     * falseを返却することのテスト。（属性がカラ）
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testIsAllowedURI03() throws Exception {
        // 前処理
        // requestURI : "AAA"
        String requestURI = "AAA";
        
        // allowedURISet : {}
        Set<String> allowedURISet = new HashSet<String>();
        
        ForbiddenURICheckerImpl impl = new ForbiddenURICheckerImpl();
        UTUtil.setPrivateField(impl, "allowedURISet", allowedURISet);

        // テスト実施
        boolean b = impl.isAllowedURI(requestURI);

        // 判定
        assertFalse(b);
    }

    /**
     * testIsAllowedURI04()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) requestURI:""<br>
     *         (状態) allowedURISet:{"", "BBB", "CCC"}<br>
     *         
     * <br>
     * 期待値：(戻り値) boolean:true<br>
     *         
     * <br>
     * 属性のセットに引き数の文字列が含まれている場合、trueを返却することのテスト。（引き数空文字）
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testIsAllowedURI04() throws Exception {
        // 前処理
        // requestURI : ""
        String requestURI = "";
        
        // allowedURISet : "", "BBB", "CCC"
        Set<String> allowedURISet = new HashSet<String>();
        allowedURISet.add("");
        allowedURISet.add("BBB");
        allowedURISet.add("CCC");
        
        ForbiddenURICheckerImpl impl = new ForbiddenURICheckerImpl();
        UTUtil.setPrivateField(impl, "allowedURISet", allowedURISet);

        // テスト実施
        boolean b = impl.isAllowedURI(requestURI);

        // 判定
        assertTrue(b);
    }

    /**
     * testIsAllowedURI05()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) requestURI:"AAA"<br>
     *         (状態) allowedURISet:null<br>
     *         
     * <br>
     * 期待値：(戻り値) boolean:false<br>
     *         
     * <br>
     * falseを返却することのテスト。（属性がnull）
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testIsAllowedURI05() throws Exception {
        // 前処理
        // requestURI : "AAA"
        String requestURI = "AAA";
        
        ForbiddenURICheckerImpl impl = new ForbiddenURICheckerImpl();
        UTUtil.setPrivateField(impl, "allowedURISet", null);

        // テスト実施
        boolean b = impl.isAllowedURI(requestURI);

        // 判定
        assertFalse(b);
    }

}
