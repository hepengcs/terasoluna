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

package jp.terasoluna.fw.web.thin;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import jp.terasoluna.utlib.MockFilterConfig;
import jp.terasoluna.utlib.MockHttpServletRequest;
import jp.terasoluna.utlib.PropertyTestCase;
import jp.terasoluna.utlib.UTUtil;

/**
 * {@link jp.terasoluna.fw.web.thin.ExtentionFilter} クラスのブラックボックステスト。
 * 
 * <p>
 * <h4>【クラスの概要】</h4>
 * 拡張子チェックを行う。<br>
 * 指定された禁止拡張子をもつパスへのアクセス要求に対しては、SC_NOT_FOUND(404)エラーを返す。これにより、ファイルへの直接アクセスを禁止する。禁止拡張子へのアクセス制限を行う場合でそのチェック対象からはずしたい特別なパスがあれば、プロパティファイルにrestrictionEscape.というプレフィクスをつけた数字をキーとして定義することでチェック非対象のパスを1から複数定義できる。
 * <p>
 * 
 * @see jp.terasoluna.fw.web.thin.ExtensionFilter
 */
public class ExtensionFilterTest extends PropertyTestCase {

    /**
     * このテストケースを実行する為の
     * GUI アプリケーションを起動する。
     * 
     * @param args java コマンドに設定されたパラメータ
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(ExtensionFilterTest.class);
    }

    /**
     * 初期化処理を行う。
     * 
     * @throws Exception このメソッドで発生した例外
     * @see jp.terasoluna.utlib.spring.PropertyTestCase#setUpData()
     */
    @Override
    protected void setUpData() throws Exception {
        
    }

    /**
     * 終了処理を行う。
     * 
     * @throws Exception このメソッドで発生した例外
     * @see jp.terasoluna.utlib.spring.PropertyTestCase#cleanUpData()
     */
    @Override
    protected void cleanUpData() throws Exception {
        UTUtil.setPrivateField(ExtensionFilter.class, 
                               "prohibitedExtensionList",
                               new ArrayList()); 
        
        UTUtil.setPrivateField(ExtensionFilter.class, 
                "restrictionEscapePaths",
                new ArrayList()); 
    }

    /**
     * コンストラクタ。
     * 
     * @param name このテストケースの名前。
     */
    public ExtensionFilterTest(String name) {
        super(name);
    }

    /**
     * testInit01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) config:not null<br>
     *         (状態) プロパティファイルの関連するプロパティ:access.control.prohibited.extension.1=.test1<br>
     *                restrictedEscape.1=./test/test1<br>
     *         
     * <br>
     * 期待値：(状態変化) prohibitedExtensionList:".test1"<br>
     *         (状態変化) restrictionEscapePaths:"./test/test1"<br>
     *         
     * <br>
     * １）プロパティのaccess.control.prohibited.extension.nが1件正常に設定されている場合、prohibitedExtensionListに設定値が追加されることを確認する。<br>
     * ２）プロパティのrestrictionEscape.nが1件正常に設定されている場合、restrictionEscapePathsに設定値が追加されることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testInit01() throws Exception {
        // 前処理
        addProperty("access.control.prohibited.extension.1", ".test1");
        addProperty("restrictionEscape.1", "./test/test1");

        // テスト実施
        ExtensionFilter filter = new ExtensionFilter();
        MockFilterConfig config = new MockFilterConfig();
        filter.init(config);
        
        // 判定
        List prohibitedExtensionList = (List) UTUtil.getPrivateField(
                ExtensionFilter.class, 
                "prohibitedExtensionList");
        
        List restrictionEscapePaths = (List) UTUtil.getPrivateField(
                ExtensionFilter.class, 
                "restrictionEscapePaths");
        
        // prohibitedExtensionListを確認
        assertEquals(1, prohibitedExtensionList.size());
        assertEquals(".test1", prohibitedExtensionList.get(0));
        // restrictionEscapePathsを確認
        assertEquals(1, restrictionEscapePaths.size());
        assertEquals("./test/test1", restrictionEscapePaths.get(0));        
        
        
    }

    /**
     * testInit02()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) config:not null<br>
     *         (状態) プロパティファイルの関連するプロパティ:access.control.prohibited.extension.1=.test1<br>
     *                access.control.prohibited.extension.2=.test2<br>
     *                access.control.prohibited.extension.3=.test3<br>
     *                restrictedEscape.1=./test/test1<br>
     *                restrictedEscape.2=./test/test2<br>
     *                restrictedEscape.3=./test/test3<br>
     *         
     * <br>
     * 期待値：(状態変化) prohibitedExtensionList:".test1"<br>
     *                    ".test2"<br>
     *                    ".test3<br>
     *         (状態変化) restrictionEscapePaths:"./test/test1"<br>
     *                    "./test/test2"<br>
     *                    "./test/test3"<br>
     *         
     * <br>
     * １）プロパティのaccess.control.prohibited.extension.nが複数件正常に設定されている場合、prohibitedExtensionListに設定値が追加されることを確認する。<br>
     * ２）プロパティのaccess.control.prohibited.extension.nに"."から始まらない文字が設定されている場合、prohibitedExtensionListに"." + 設定値が追加されることを確認する。<br>
     * ３）プロパティのrestrictionEscape.nが複数件正常に設定されている場合、restrictionEscapePathsに設定値が追加されることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testInit02() throws Exception {
        // 前処理
        addProperty("access.control.prohibited.extension.1", ".test1");
        addProperty("access.control.prohibited.extension.2", "test2");
        addProperty("access.control.prohibited.extension.3", ".test3");
        addProperty("restrictionEscape.1", "./test/test1");
        addProperty("restrictionEscape.2", "./test/test2");
        addProperty("restrictionEscape.3", "./test/test3");

        // テスト実施
        ExtensionFilter filter = new ExtensionFilter();
        MockFilterConfig config = new MockFilterConfig();
        filter.init(config);
        
        // 判定
        List prohibitedExtensionList = (List) UTUtil.getPrivateField(ExtensionFilter.class, "prohibitedExtensionList");
        List restrictionEscapePaths = (List) UTUtil.getPrivateField(ExtensionFilter.class, "restrictionEscapePaths");
        // prohibitedExtensionListを確認
        assertEquals(3, prohibitedExtensionList.size());
        for (int i = 0; i < 3; i++) {
            assertEquals(".test" + (i + 1), prohibitedExtensionList.get(i));
        }
        
        // restrictionEscapePathsを確認
        assertEquals(3, restrictionEscapePaths.size());
        for (int i = 0; i < 3; i++) {
            assertEquals("./test/test" + (i + 1), restrictionEscapePaths.get(i));
        }
    }

    /**
     * testInit03()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) config:not null<br>
     *         (状態) プロパティファイルの関連するプロパティ:access.control.prohibited.extension.1=<br>
     *                restrictedEscape.1=<br>
     *         
     * <br>
     * 期待値：(状態変化) prohibitedExtensionList:"."<br>
     *         (状態変化) restrictionEscapePaths:""<br>
     *         
     * <br>
     * １）プロパティのaccess.control.prohibited.extension.nに空文字列が設定されている場合、prohibitedExtensionListに"."が追加されることを確認する。<br>
     * ２）プロパティのrestrictionEscape.nに空文字列が設定されている場合、restrictionEscapePathsに空文字列が追加されることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testInit03() throws Exception {
        // 前処理
        addProperty("access.control.prohibited.extension.1", "");
        addProperty("restrictionEscape.1", "");

        // テスト実施
        ExtensionFilter filter = new ExtensionFilter();
        MockFilterConfig config = new MockFilterConfig();
        filter.init(config);
        
        // 判定
        List prohibitedExtensionList = (List) UTUtil.getPrivateField(ExtensionFilter.class, "prohibitedExtensionList");
        List restrictionEscapePaths = (List) UTUtil.getPrivateField(ExtensionFilter.class, "restrictionEscapePaths");
        // prohibitedExtensionListを確認
        assertEquals(1, prohibitedExtensionList.size());
        assertEquals(".", prohibitedExtensionList.get(0));
        // restrictionEscapePathsを確認
        assertEquals(1, restrictionEscapePaths.size());
        assertEquals("", restrictionEscapePaths.get(0));    
    }

    /**
     * testInit04()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) config:not null<br>
     *         (状態) プロパティファイルの関連するプロパティ:（access.control.prohibited.extension.nが存在しない）<br>
     *                （restrictedEscape.nが存在しない）<br>
     *         
     * <br>
     * 期待値：(状態変化) prohibitedExtensionList:空のList<br>
     *         (状態変化) restrictionEscapePaths:空のList<br>
     *         
     * <br>
     * １）プロパティにaccess.control.prohibited.extension.nが存在しない場合、prohibitedExtensionListに何も設定されていないことを確認する。<br>
     * ２）プロパティのrestrictedEscape.nが存在しない場合、restrictedEscapePathsに何も設定されていないことを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testInit04() throws Exception {
        // 前処理
        // （access.control.prohibited.extension.nが存在しない）
        // （restrictionEscapePaths.nが存在しない）

        // テスト実施
        ExtensionFilter filter = new ExtensionFilter();
        MockFilterConfig config = new MockFilterConfig();
        filter.init(config);
        
        // 判定
        List prohibitedExtensionList = (List) UTUtil.getPrivateField(ExtensionFilter.class, "prohibitedExtensionList");
        List restrictionEscapePaths = (List) UTUtil.getPrivateField(ExtensionFilter.class, "restrictionEscapePaths");
        // prohibitedExtensionListを確認
        assertEquals(0, prohibitedExtensionList.size());
        // restrictionEscapePathsを確認
        assertEquals(0, restrictionEscapePaths.size());
    }

    /**
     * testInit05()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) config:not null<br>
     *         (状態) プロパティファイルの関連するプロパティ:access.control.prohibited.extension.1=.test1<br>
     *                access.control.prohibited.extension.3=.test3<br>
     *                restrictedEscape.1=./test/test1<br>
     *                restrictedEscape.3=./test/test3<br>
     *         
     * <br>
     * 期待値：(状態変化) prohibitedExtensionList:".test1"<br>
     *         (状態変化) restrictionEscapePaths:"./test/test1"<br>
     *         
     * <br>
     * １）プロパティのaccess.control.prohibited.extension.nのnに抜けがある場合、prohibitedExtensionListに抜けたインデックスまでの値が追加されることを確認する。<br>
     * ２）プロパティのrestrictedEscape.nのnに抜けがある場合、restrictedEscapePathsに抜けたインデックスまでの値が追加されることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testInit05() throws Exception {
        // 前処理
        addProperty("access.control.prohibited.extension.1", ".test1");
        addProperty("access.control.prohibited.extension.3", ".test3");
        addProperty("restrictionEscape.1", "./test/test1");
        addProperty("restrictionEscape.3", "./test/test3");

        // テスト実施
        ExtensionFilter filter = new ExtensionFilter();
        MockFilterConfig config = new MockFilterConfig();
        filter.init(config);
        
        // 判定
        List prohibitedExtensionList = (List) UTUtil.getPrivateField(ExtensionFilter.class, "prohibitedExtensionList");
        List restrictionEscapePaths = (List) UTUtil.getPrivateField(ExtensionFilter.class, "restrictionEscapePaths");
        // prohibitedExtensionListを確認
        assertEquals(1, prohibitedExtensionList.size());
        assertEquals(".test1", prohibitedExtensionList.get(0));
        // restrictionEscapePathsを確認
        assertEquals(1, restrictionEscapePaths.size());
        assertEquals("./test/test1", restrictionEscapePaths.get(0));    
    }

    /**
     * testInit06()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) config:not null<br>
     *         (状態) プロパティファイルの関連するプロパティ:access.control.prohibited.extension.0=.test0<br>
     *                access.control.prohibited.extension.1=.test1<br>
     *                access.control.prohibited.extension.2=.test2<br>
     *                restrictedEscape.0=./test/test0<br>
     *                restrictedEscape.1=./test/test1<br>
     *                restrictedEscape.2=./test/test2<br>
     *         
     * <br>
     * 期待値：(状態変化) prohibitedExtensionList:".test1"<br>
     *                    ".test2"<br>
     *         (状態変化) restrictionEscapePaths:"./test/test1"<br>
     *                    "./test/test2"<br>
     *         
     * <br>
     * １）プロパティのaccess.control.prohibited.extension.nのnが0から始まる場合、prohibitedExtensionListにnが0の値は追加されないことを確認する。<br>
     * ２）プロパティのrestrictedEscapenのnが0から始まる場合、restrictedEscapePathsにnが0の値は追加されないことを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testInit06() throws Exception {
        // 前処理
        addProperty("access.control.prohibited.extension.0", ".test0");
        addProperty("access.control.prohibited.extension.1", ".test1");
        addProperty("access.control.prohibited.extension.2", ".test2");
        addProperty("restrictionEscape.0", "./test/test0");
        addProperty("restrictionEscape.1", "./test/test1");
        addProperty("restrictionEscape.2", "./test/test2");

        // テスト実施
        ExtensionFilter filter = new ExtensionFilter();
        MockFilterConfig config = new MockFilterConfig();
        filter.init(config);
        
        // 判定
        List prohibitedExtensionList = (List) UTUtil.getPrivateField(ExtensionFilter.class, "prohibitedExtensionList");
        List restrictionEscapePaths = (List) UTUtil.getPrivateField(ExtensionFilter.class, "restrictionEscapePaths");
        // prohibitedExtensionListを確認
        assertEquals(2, prohibitedExtensionList.size());
        for (int i = 0; i < 2; i++) {
            assertEquals(".test" + (i + 1), prohibitedExtensionList.get(i));
        }
        
        // restrictionEscapePathsを確認
        assertEquals(2, restrictionEscapePaths.size());
        for (int i = 0; i < 2; i++) {
            assertEquals("./test/test" + (i + 1), restrictionEscapePaths.get(i));
        }
    }

    /**
     * testInit07()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) config:not null<br>
     *         (状態) プロパティファイルの関連するプロパティ:access.control.prohibited.extension.1=.test1<br>
     *                access.control.prohibited.extension.a=.testA<br>
     *                restrictedEscape.1=./test/test1<br>
     *                restrictedEscape.A=./test/testA<br>
     *         
     * <br>
     * 期待値：(状態変化) prohibitedExtensionList:".test1"<br>
     *         (状態変化) restrictionEscapePaths:"./test/test1"<br>
     *         
     * <br>
     * １）プロパティのaccess.control.prohibited.extension.nのnが数字でない場合、prohibitedExtensionListにnが数字でない値は追加されないことを確認する。<br>
     * ２）プロパティのrestrictedEscape.nのnが数字でない場合、restrictedEscapePathsにnが数字でない値は追加されないことを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testInit07() throws Exception {
        // 前処理
        addProperty("access.control.prohibited.extension.1", ".test1");
        addProperty("access.control.prohibited.extension.a", ".testA");
        addProperty("restrictionEscape.1", "./test/test1");
        addProperty("restrictionEscape.A", "./test/testA");

        // テスト実施
        ExtensionFilter filter = new ExtensionFilter();
        MockFilterConfig config = new MockFilterConfig();
        filter.init(config);
        
        // 判定
        List prohibitedExtensionList = (List) UTUtil.getPrivateField(ExtensionFilter.class, "prohibitedExtensionList");
        List restrictionEscapePaths = (List) UTUtil.getPrivateField(ExtensionFilter.class, "restrictionEscapePaths");
        // prohibitedExtensionListを確認
        assertEquals(1, prohibitedExtensionList.size());
        assertEquals(".test1", prohibitedExtensionList.get(0));
        // restrictionEscapePathsを確認
        assertEquals(1, restrictionEscapePaths.size());
        assertEquals("./test/test1", restrictionEscapePaths.get(0));    
    }

    /**
     * testDoFilter01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) req:not null<br>
     *         (引数) res:not null<br>
     *         (引数) chain:not null<br>
     *         (状態) EXTENSION_THRU_KEY:"aaaaa"<br>
     *         
     * <br>
     * 期待値：(状態変化) EXTENSION_THRU_KEY:前提条件で与えた値と同一<br>
     *         (状態変化) sendError:呼ばれない<br>
     *         (状態変化) doFilter:実行する<br>
     *         
     * <br>
     * requestの属性値であるEXTENSION_THRU_KEYがnullでない場合、拡張子チェックを行わずに終了すること。<br>
     * FilterChain#doFilter()メソッドを実行すること。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testDoFilter01() throws Exception {
        // 前処理
        ExtensionFilter filter = new ExtensionFilter();
        MockHttpServletRequest request = new MockHttpServletRequest();
        ExtensionFilter_ServletResponseStub01 response = new ExtensionFilter_ServletResponseStub01();
        // EXTENSION_THRU_KEY:"aaaaa"
        request.setAttribute("EXTENSION_THRU_KEY", "aaaaa");
        
        ExtensionFilter_FilterChainStub01 filterChain =
            new ExtensionFilter_FilterChainStub01();
        
        // テスト実施
        filter.doFilter(request, response, filterChain);
        // 判定
        assertEquals("aaaaa", request.getAttribute("EXTENSION_THRU_KEY"));
        assertEquals(-1, UTUtil.getPrivateField(response, "errorCode"));
        assertTrue(filterChain.isCalled);
    }

    /**
     * testDoFilter02()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：G
     * <br><br>
     * 入力値：(引数) req:not null<br>
     *         (引数) res:not null<br>
     *         (引数) chain:not null<br>
     *         (状態) EXTENSION_THRU_KEY:null<br>
     *         (状態) requestURI:null<br>
     *         (状態) contextPath:null<br>
     *         
     * <br>
     * 期待値：(状態変化) EXTENSION_THRU_KEY:"true"<br>
     *         (状態変化) sendError:呼ばれない<br>
     *         (状態変化) doFilter:実行する<br>
     *         
     * <br>
     * requestURIがnullの場合、NullPointerExceptionが発生すること。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testDoFilter02() throws Exception {
        // 前処理
        ExtensionFilter filter = new ExtensionFilter();
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setContextPath(null);
        request.setRequestURI(null);
        
        ExtensionFilter_ServletResponseStub01 response =
            new ExtensionFilter_ServletResponseStub01();
        ExtensionFilter_FilterChainStub01 filterChain =
            new ExtensionFilter_FilterChainStub01();
        
        try {
            // テスト実施
            filter.doFilter(request, response, filterChain);
            fail();
        } catch (NullPointerException e) {
            // 判定
            assertEquals(NullPointerException.class.getName(),
                         e.getClass().getName());
        }
    }

    /**
     * testDoFilter03()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) req:not null<br>
     *         (引数) res:not null<br>
     *         (引数) chain:not null<br>
     *         (状態) EXTENSION_THRU_KEY:null<br>
     *         (状態) requestURI:"/test/test"<br>
     *         (状態) contextPath:"/test"<br>
     *         (状態) restrictionEscapePaths:"/test"<br>
     *         (状態) prohibitedExtensionList:""<br>
     *         
     * <br>
     * 期待値：(状態変化) EXTENSION_THRU_KEY:"true"<br>
     *         (状態変化) sendError:呼ばれない<br>
     *         (状態変化) doFilter:実行する<br>
     *         
     * <br>
     * 拡張子チェック対象外リストにrequestURIの値が含まれる場合、拡張子禁止リストにrequestURIから取得した拡張子（空文字）が含まれていても、拡張子チェックを行わず、requestの属性値EXTENSION_THRU_KEYにtrueを設定して処理を終了すること。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testDoFilter03() throws Exception {
        // 前処理
        ExtensionFilter filter = new ExtensionFilter();
        MockHttpServletRequest request = new MockHttpServletRequest();
        ExtensionFilter_ServletResponseStub01 response =
            new ExtensionFilter_ServletResponseStub01();
        
        ExtensionFilter_FilterChainStub01 filterChain =
            new ExtensionFilter_FilterChainStub01();
       
        // prohibitedExtensionList:"" 
        ArrayList<String> prohibitedExtensionList = new ArrayList<String>();
        prohibitedExtensionList.add("");
        // restrictionEscapePaths:"/test/test"
        ArrayList<String> restrictionEscapePaths = new ArrayList<String>();
        restrictionEscapePaths.add("/test");
        
        // prohibitedExtensionListを設定
        UTUtil.setPrivateField(filter,
                "prohibitedExtensionList", 
                prohibitedExtensionList);
        // restrictionEscapePathsを設定
        UTUtil.setPrivateField(filter, 
                "restrictionEscapePaths",
                restrictionEscapePaths); 
        // requestURIを設定
        request.setRequestURI("/test/test");
        request.setContextPath("/test");

        
        // テスト実施
        filter.doFilter(request, response, filterChain);
        // 判定
        assertEquals("true", request.getAttribute("EXTENSION_THRU_KEY"));
        assertEquals(-1, UTUtil.getPrivateField(response, "errorCode"));
        assertTrue(filterChain.isCalled);
    }

    /**
     * testDoFilter04()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) req:not null<br>
     *         (引数) res:not null<br>
     *         (引数) chain:not null<br>
     *         (状態) EXTENSION_THRU_KEY:null<br>
     *         (状態) requestURI:"/test/test"<br>
     *         (状態) contextPath:"/test"<br>
     *         (状態) restrictionEscapePaths:空のList<br>
     *         (状態) prohibitedExtensionList:空のList<br>
     *         
     * <br>
     * 期待値：(状態変化) EXTENSION_THRU_KEY:"true"<br>
     *         (状態変化) sendError:呼ばれない<br>
     *         (状態変化) doFilter:実行する<br>
     *         
     * <br>
     * 拡張子チェック対象外リストにrequestURIの値が含まれず、拡張子禁止リストにrequestURIから取得した拡張子（空文字）が含まれない場合、requestの属性値EXTENSION_THRU_KEYにtrueを設定して処理を終了すること。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testDoFilter04() throws Exception {
        // 前処理
        ExtensionFilter filter = new ExtensionFilter();
        MockHttpServletRequest request = new MockHttpServletRequest();
        ExtensionFilter_ServletResponseStub01 response =
            new ExtensionFilter_ServletResponseStub01();
        
        ExtensionFilter_FilterChainStub01 filterChain =
            new ExtensionFilter_FilterChainStub01();
       
        // prohibitedExtensionList:空のList 
        ArrayList prohibitedExtensionList = new ArrayList();
        // restrictionEscapePaths:空のList
        ArrayList restrictionEscapePaths = new ArrayList();
        
        // prohibitedExtensionListを設定
        UTUtil.setPrivateField(filter,
                                "prohibitedExtensionList", 
                                prohibitedExtensionList);
        
        // restrictionEscapePathsを設定
        UTUtil.setPrivateField(filter, 
                                "restrictionEscapePaths",
                                restrictionEscapePaths); 
        // requestURIを設定
        request.setRequestURI("/test/test");
        request.setContextPath("/test");
        
        // テスト実施
        filter.doFilter(request, response, filterChain);
        // 判定
        assertEquals("true", request.getAttribute("EXTENSION_THRU_KEY"));
        assertEquals(-1, UTUtil.getPrivateField(response, "errorCode"));
        assertTrue(filterChain.isCalled);
    }

    /**
     * testDoFilter05()
     * <br><br>
     * 
     * (異常系)
     * <br>
     * 観点：G
     * <br><br>
     * 入力値：(引数) req:not null<br>
     *         (引数) res:not null<br>
     *         (引数) chain:not null<br>
     *         (状態) EXTENSION_THRU_KEY:null<br>
     *         (状態) requestURI:"/test/test"<br>
     *         (状態) contextPath:"/test"<br>
     *         (状態) restrictionEscapePaths:空のList<br>
     *         (状態) prohibitedExtensionList:""<br>
     *         
     * <br>
     * 期待値：(状態変化) EXTENSION_THRU_KEY:"true"<br>
     *         (状態変化) sendError:HttpServletResponse.SC_NOT_FOUNDが与えられる<br>
     *         (状態変化) doFilter:実行しない<br>
     *         
     * <br>
     * 拡張子チェック対象外リストにrequestURIの値が含まれず、拡張子禁止リストにrequestURIから取得した拡張子（空文字）が含まれる場合、requestの属性値EXTENSION_THRU_KEYにtrueを設定し、requestの状態コードに"404"を設定して処理を終了すること。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testDoFilter05() throws Exception {
           // 前処理
        ExtensionFilter filter = new ExtensionFilter();
        MockHttpServletRequest request = new MockHttpServletRequest();
        ExtensionFilter_ServletResponseStub01 response =
            new ExtensionFilter_ServletResponseStub01();
        
        ExtensionFilter_FilterChainStub01 filterChain =
            new ExtensionFilter_FilterChainStub01();
       
        // prohibitedExtensionList:空のList 
        ArrayList<String> prohibitedExtensionList = new ArrayList<String>();
        prohibitedExtensionList.add("");
        // restrictionEscapePaths:空のList
        ArrayList restrictionEscapePaths = new ArrayList();
        
        // prohibitedExtensionListを設定
        UTUtil.setPrivateField(filter,
                "prohibitedExtensionList", 
                prohibitedExtensionList);
        // restrictionEscapePathsを設定
        UTUtil.setPrivateField(filter, 
                "restrictionEscapePaths",
                restrictionEscapePaths); 
        // requestURIを設定
        request.setRequestURI("/test/test");
        request.setContextPath("/test");
        
        // テスト実施
        filter.doFilter(request, response, filterChain);
        // 判定
        assertEquals("true", request.getAttribute("EXTENSION_THRU_KEY"));
        assertEquals(HttpServletResponse.SC_NOT_FOUND, UTUtil.getPrivateField(response, "errorCode"));
        assertFalse(filterChain.isCalled);
    }

    /**
     * testDoFilter06()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) req:not null<br>
     *         (引数) res:not null<br>
     *         (引数) chain:not null<br>
     *         (状態) EXTENSION_THRU_KEY:null<br>
     *         (状態) requestURI:"/test/test.ext2"<br>
     *         (状態) contextPath:"/test"<br>
     *         (状態) restrictionEscapePaths:"/test.ext2"<br>
     *                "/test.ext3"<br>
     *         (状態) prohibitedExtensionList:".ext1"<br>
     *                ".ext2"<br>
     *                ".ext3"<br>
     *         
     * <br>
     * 期待値：(状態変化) EXTENSION_THRU_KEY:"true"<br>
     *         (状態変化) sendError:呼ばれない<br>
     *         (状態変化) doFilter:実行する<br>
     *         
     * <br>
     * 複数登録されている拡張子チェック対象外リストにrequestURIの値が含まれる場合、複数登録されている拡張子禁止リストにrequestURIから取得した拡張子（".ext2"）が含まれていても、拡張子チェックを行わず、requestの属性値EXTENSION_THRU_KEYにtrueを設定して処理を終了すること。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testDoFilter06() throws Exception {
        // 前処理
        ExtensionFilter filter = new ExtensionFilter();
        MockHttpServletRequest request = new MockHttpServletRequest();
        ExtensionFilter_ServletResponseStub01 response =
            new ExtensionFilter_ServletResponseStub01();
        
        ExtensionFilter_FilterChainStub01 filterChain =
            new ExtensionFilter_FilterChainStub01();
       
        // prohibitedExtensionList:"" 
        ArrayList<String> prohibitedExtensionList = new ArrayList<String>();
        prohibitedExtensionList.add(".ext1");
        prohibitedExtensionList.add(".ext2");
        prohibitedExtensionList.add(".ext3");
        // restrictionEscapePaths:"/test/test"
        ArrayList<String> restrictionEscapePaths = new ArrayList<String>();
        restrictionEscapePaths.add("/test.ext2");
        restrictionEscapePaths.add("/test.ext3");
        
        // prohibitedExtensionListを設定
        UTUtil.setPrivateField(filter,
                "prohibitedExtensionList", 
                prohibitedExtensionList);
        // restrictionEscapePathsを設定
        UTUtil.setPrivateField(filter, 
                "restrictionEscapePaths",
                restrictionEscapePaths); 
        
        // requestURIを設定
        request.setRequestURI("/test/test.ext2");
        request.setContextPath("/test");
        
        // テスト実施
        filter.doFilter(request, response, filterChain);
        // 判定
        assertEquals("true", request.getAttribute("EXTENSION_THRU_KEY"));
        assertEquals(-1, UTUtil.getPrivateField(response, "errorCode"));
        assertTrue(filterChain.isCalled);
    }

    /**
     * testDoFilter07()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) req:not null<br>
     *         (引数) res:not null<br>
     *         (引数) chain:not null<br>
     *         (状態) EXTENSION_THRU_KEY:null<br>
     *         (状態) requestURI:"/test/test.ext"<br>
     *         (状態) contextPath:"/test"<br>
     *         (状態) restrictionEscapePaths:空のList<br>
     *         (状態) prohibitedExtensionList:空のList<br>
     *         
     * <br>
     * 期待値：(状態変化) EXTENSION_THRU_KEY:"true"<br>
     *         (状態変化) sendError:呼ばれない<br>
     *         (状態変化) doFilter:実行する<br>
     *         
     * <br>
     * 拡張子チェック対象外リストにrequestURIの値が含まれず、拡張子禁止リストにrequestURIから取得した拡張子（".ext"）が含まれない場合、requestの属性値EXTENSION_THRU_KEYにtrueを設定して処理を終了すること。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testDoFilter07() throws Exception {
        // 前処理
        ExtensionFilter filter = new ExtensionFilter();
        MockHttpServletRequest request = new MockHttpServletRequest();
        ExtensionFilter_ServletResponseStub01 response =
            new ExtensionFilter_ServletResponseStub01();
        
        ExtensionFilter_FilterChainStub01 filterChain =
            new ExtensionFilter_FilterChainStub01();
       
        // prohibitedExtensionList:空のList 
        ArrayList prohibitedExtensionList = new ArrayList();
        // restrictionEscapePaths:空のList
        ArrayList restrictionEscapePaths = new ArrayList();
        
        // prohibitedExtensionListを設定
        UTUtil.setPrivateField(filter,
                "prohibitedExtensionList", 
                prohibitedExtensionList);
        // restrictionEscapePathsを設定
        UTUtil.setPrivateField(filter, 
                "restrictionEscapePaths",
                restrictionEscapePaths); 
        // requestURIを設定
        request.setRequestURI("/test/test.ext");
        request.setContextPath("/test");
        
        // テスト実施
        filter.doFilter(request, response, filterChain);
        // 判定
        assertEquals("true", request.getAttribute("EXTENSION_THRU_KEY"));
        assertEquals(-1, UTUtil.getPrivateField(response, "errorCode"));
        assertTrue(filterChain.isCalled);
    }

    /**
     * testDoFilter08()
     * <br><br>
     * 
     * (異常系)
     * <br>
     * 観点：G
     * <br><br>
     * 入力値：(引数) req:not null<br>
     *         (引数) res:not null<br>
     *         (引数) chain:not null<br>
     *         (状態) EXTENSION_THRU_KEY:null<br>
     *         (状態) requestURI:"/test/test.ext1"<br>
     *         (状態) contextPath:"/test"<br>
     *         (状態) restrictionEscapePaths:空のList<br>
     *         (状態) prohibitedExtensionList:".ext1"<br>
     *                ".ext2"<br>
     *                ".ext3"<br>
     *         
     * <br>
     * 期待値：(状態変化) EXTENSION_THRU_KEY:"true"<br>
     *         (状態変化) sendError:HttpServletResponse.SC_NOT_FOUNDが与えられる<br>
     *         (状態変化) doFilter:実行しない<br>
     *         
     * <br>
     * 拡張子チェック対象外リストにrequestURIの値が含まれず、複数登録されている拡張子禁止リストにrequestURIから取得した拡張子（".ext1"）が含まれる場合、requestの属性値EXTENSION_THRU_KEYにtrueを設定し、requestの状態コードに"404"を設定して処理を終了すること。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testDoFilter08() throws Exception {
           // 前処理
        ExtensionFilter filter = new ExtensionFilter();
        MockHttpServletRequest request = new MockHttpServletRequest();
        ExtensionFilter_ServletResponseStub01 response =
            new ExtensionFilter_ServletResponseStub01();
        
        ExtensionFilter_FilterChainStub01 filterChain =
            new ExtensionFilter_FilterChainStub01();
       
        // prohibitedExtensionList:空のList 
        ArrayList<String> prohibitedExtensionList = new ArrayList<String>();
        prohibitedExtensionList.add(".ext1");
        prohibitedExtensionList.add(".ext2");
        prohibitedExtensionList.add(".ext3");
        // restrictionEscapePaths:空のList
        ArrayList restrictionEscapePaths = new ArrayList();
        
        // prohibitedExtensionListを設定
        UTUtil.setPrivateField(filter,
                "prohibitedExtensionList", 
                prohibitedExtensionList);
        // restrictionEscapePathsを設定
        UTUtil.setPrivateField(filter, 
                "restrictionEscapePaths",
                restrictionEscapePaths); 
        // requestURIを設定
        request.setRequestURI("/test/test.ext1");
        request.setContextPath("/test");
        
        // テスト実施
        filter.doFilter(request, response, filterChain);
        // 判定
        assertEquals("true", request.getAttribute("EXTENSION_THRU_KEY"));
        assertEquals(HttpServletResponse.SC_NOT_FOUND, UTUtil.getPrivateField(response, "errorCode"));
        assertFalse(filterChain.isCalled);
    }

}
