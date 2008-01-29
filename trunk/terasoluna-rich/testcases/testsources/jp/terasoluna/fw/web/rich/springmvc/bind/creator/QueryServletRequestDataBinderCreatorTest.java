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

package jp.terasoluna.fw.web.rich.springmvc.bind.creator;

import java.util.Map;

import org.springframework.beans.BeanWrapper;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;

import jp.terasoluna.utlib.MockHttpServletRequest;
import jp.terasoluna.utlib.UTUtil;
import jp.terasoluna.fw.web.rich.springmvc.bind.creator.QueryServletRequestDataBinderCreator;
import junit.framework.TestCase;

/**
 * {@link jp.terasoluna.fw.web.rich.springmvc.bind.creator.
 * QueryServletRequestDataBinderCreator} クラスのブラックボックステスト。
 * 
 * <p>
 * <h4>【クラスの概要】</h4>
 * クエリ形式のリクエストデータに対応したServletRequestDataBinder実装クラスを
 * 返却する。<br>
 * 前提条件：引数のrequest、command、requestNameはNull値にならない。
 * <p>
 * 
 * @see jp.terasoluna.fw.web.rich.springmvc.bind.creator.
 * QueryServletRequestDataBinderCreator
 */
public class QueryServletRequestDataBinderCreatorTest extends TestCase {

    /**
     * このテストケースを実行する為の
     * GUI アプリケーションを起動する。
     * 
     * @param args java コマンドに設定されたパラメータ
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(
                QueryServletRequestDataBinderCreatorTest.class);
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
    public QueryServletRequestDataBinderCreatorTest(String name) {
        super(name);
    }

    /**
     * testCreate01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) command:not null<br>
     *         (引数) requestName:not null<br>
     *                （"sum"）<br>
     *         
     * <br>
     * 期待値：(戻り値) binder:ServletRequestDataBinderが返される。<br>
     *                  bindResult.target属性にcommandが設定される。<br>
     *                  bindResult.objectName属性にrequestNameが設定される。<br>
     *                  bindResult.beanWrapper属性にカスタムエディターが
     *                  設定される。<br>
     *                  （byte[]、ByteArrayMultipartFileEditor)<br>
     *         
     * <br>
     * クエリ形式のバインダを生成するテスト。リクエスト名は"sum"。
     * ServletRequestDataBinderが生成される。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testCreate01() throws Exception {
        // 前処理
        QueryServletRequestDataBinderCreator creator = 
            new QueryServletRequestDataBinderCreator();
        Object command = new Object();
        String requestName = "sum";
        MockHttpServletRequest request = new MockHttpServletRequest();

        // テスト実施
        ServletRequestDataBinder binder = creator.create(
                request, command, requestName);

        // 判定
        Object target = UTUtil.getPrivateField(binder.getBindingResult(), "target");
        assertSame(command, target);
        Object objectName = UTUtil.getPrivateField(
                binder.getBindingResult(), "objectName");
        assertEquals(requestName, objectName);
        BeanWrapper bw = (BeanWrapper)UTUtil.getPrivateField(
                binder.getBindingResult(), "beanWrapper");
        Map editorMap = (Map)UTUtil.getPrivateField(bw, "customEditors");
        assertEquals((editorMap.get(byte[].class)).getClass().getName(),
                ByteArrayMultipartFileEditor.class.getName());
    }

    /**
     * testCreate02()
     * <br><br>
     * 
     * (正常系) 
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) command:not null<br>
     *         (引数) requestName:not null<br>
     *                （空文字）<br>
     *         
     * <br>
     * 期待値：(戻り値) binder:ServletRequestDataBinderが返される。<br>
     *                  bindResult.target属性にcommandが設定される。<br>
     *                  bindResult.objectName属性にrequestNameが設定される。<br>
     *                  bindResult.beanWrapper属性にカスタムエディターが
     *                  設定される。<br>
     *                  （byte[]、ByteArrayMultipartFileEditor)<br>
     *         
     * <br>
     * クエリ形式のバインダを生成するテスト。リクエスト名が空の場合のテスト。
     * ServletRequestDataBinderが生成される。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testCreate02() throws Exception {
        // 前処理
        QueryServletRequestDataBinderCreator creator = 
            new QueryServletRequestDataBinderCreator();
        Object command = new Object();
        String requestName = "";
        MockHttpServletRequest request = new MockHttpServletRequest();

        // テスト実施
        ServletRequestDataBinder binder = creator.create(
                request, command, requestName);

        // 判定
        Object target = UTUtil.getPrivateField(binder.getBindingResult(), "target");
        assertSame(command, target);
        Object objectName = UTUtil.getPrivateField(
                binder.getBindingResult(), "objectName");
        assertEquals(requestName, objectName);
        BeanWrapper bw = (BeanWrapper)UTUtil.getPrivateField(
                binder.getBindingResult(), "beanWrapper");
        Map editorMap = (Map)UTUtil.getPrivateField(bw, "customEditors");
        assertEquals((editorMap.get(byte[].class)).getClass().getName(),
                ByteArrayMultipartFileEditor.class.getName());
    }

}
