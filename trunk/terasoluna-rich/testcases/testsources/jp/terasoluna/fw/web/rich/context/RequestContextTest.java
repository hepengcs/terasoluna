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

package jp.terasoluna.fw.web.rich.context;

import java.util.HashMap;
import java.util.Map;

import jp.terasoluna.utlib.UTUtil;
import jp.terasoluna.fw.web.rich.context.RequestContext;

import junit.framework.TestCase;

/**
 * {@link jp.terasoluna.fw.web.rich.context.RequestContext}
 * クラスのブラックボックステスト。
 *
 * <p>
 * <h4>【クラスの概要】</h4>
 * リクエスト名・業務プロパティを保持するためのクラス。<br>
 * 前提条件：
 * <p>
 *
 * @see jp.terasoluna.fw.web.rich.context.RequestContext
 */
public class RequestContextTest extends TestCase {

    private RequestContext context = null;

    /**
     * このテストケースを実行する為の
     * GUI アプリケーションを起動する。
     *
     * @param args java コマンドに設定されたパラメータ
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(RequestContextTest.class);
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
        context = new RequestContext();
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
    public RequestContextTest(String name) {
        super(name);
    }

    /**
     * testGetRequestName01()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(状態) this.requestName:"sum"<br>
     *
     * <br>
     * 期待値：(戻り値) String:this.requestName<br>
     *
     * <br>
     * requestName属性のgetterメソッドのテスト。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetRequestName01() throws Exception {
        // 前処理
        UTUtil.setPrivateField(context, "requestName", "sum");

        // テスト実施
        Object result = context.getRequestName();

        // 判定
        assertEquals("sum", result);
    }

    /**
     * testSetRequestName01()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) requestName:"sum"<br>
     *         (状態) this.requestName:null<br>
     *
     * <br>
     * 期待値：(状態変化) this.requestName:引数で設定した値<br>
     *
     * <br>
     * requestName属性のsetterメソッドのテスト。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetRequestName01() throws Exception {
        // 前処理
        UTUtil.setPrivateField(context, "requestName", null);

        // テスト実施
        context.setRequestName("sum");

        // 判定
        assertEquals("sum", UTUtil.getPrivateField(context, "requestName"));
    }

    /**
     * testGetProperty01()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) key:"sampleKey"<br>
     *         (状態) this.propertyMap:HashMap<br>
     *                (sampleKey=sampleValue)<br>
     *
     * <br>
     * 期待値：(戻り値) String:"sampleValue"<br>
     *
     * <br>
     * properties属性のgetterメソッドのテスト。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetProperty01() throws Exception {
        // 前処理
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("sampleKey", "sampleValue");
        UTUtil.setPrivateField(context, "propertyMap", map);

        // テスト実施
        // 判定
        assertEquals("sampleValue", context.getProperty("sampleKey"));
    }

    /**
     * testSetProperty01()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) key:"sampleKey"<br>
     *         (引数) value:new Object()<br>
     *         (状態) this.propertyMap:HashMap<br>
     *                ()<br>
     *
     * <br>
     * 期待値：(状態変化) this.propertyMap:HashMap<br>
     *                    (sampleKey=sampleValue)<br>
     *
     * <br>
     * properties属性のsetterメソッドのテスト。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetProperty01() throws Exception {
        // 前処理
    	Map<String, Object> map = new HashMap<String, Object>();
        UTUtil.setPrivateField(context, "propertyMap", map);
        Object object = new Object();

        // テスト実施
        context.setProperty("sampleKey", object);

        // 判定
        Map result =
            (Map) UTUtil.getPrivateField(context, "propertyMap");
        assertSame(object, result.get("sampleKey"));
    }

    /**
     * testGetPropertyString01()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) key:"sampleKey"<br>
     *         (状態) getProperty(String):"sampleValue"<br>
     *
     * <br>
     * 期待値：(戻り値) String:"sampleValue"<br>
     * 　　　　(状態変化) key:"sampleKey"が渡されたことを確認する。<br>
     *
     * <br>
     * getProperty(String)呼び出しと値の受け取りのテスト。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetPropertyString01() throws Exception {
        // 前処理
    	RequestContextStub01 target = new RequestContextStub01();
    	
        // スタブ返却値の設定
    	target.value = "sampleValue";
    	
        // テスト実施
        String resulet = target.getPropertyString("sampleKey");

        // 判定
        assertEquals("sampleKey",target.key);
        assertEquals("sampleValue",resulet);
    }
    
    /**
     * testGetPropertyString02()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) key:"sampleKey"<br>
     *         (状態) getProperty(String):new Object()<br>
     *
     * <br>
     * 期待値：(戻り値) String:null<br>
     * 　　　　(状態変化) key:"sampleKey"が渡されたことを確認する。<br>
     * <br>
     * プロパティオブジェクトがString型でない場合のテスト。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetPropertyString02() throws Exception {
        // 前処理
    	RequestContextStub01 target = new RequestContextStub01();
    	
        // スタブ返却値の設定
    	target.value = new Object();
    	
        // テスト実施
        String resulet = target.getPropertyString("sampleKey");

        // 判定
        assertEquals("sampleKey",target.key);
        assertNull(resulet);
    }
    
    /**
     * testSetPropertyString01()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) key:"sampleKey"<br>
     *         (引数) value:"sampleValue"<br>
     *
     * <br>
     * 期待値：(状態変化) key:"sampleKey"が渡されたことを確認する。<br>
     *                    value:"sampleValue"が渡されたことを確認する。<br>
     * <br>
     * setProperty(String,String)呼び出しのテスト。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetPropertyString01() throws Exception {
        // 前処理
    	RequestContextStub01 target = new RequestContextStub01();

        // テスト実施
        target.setPropertyString("sampleKey", "sampleValue");

        // 判定
        assertEquals("sampleKey",target.key);
        assertEquals("sampleValue",target.value);
    }
    
    /**
     * testToString01()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(状態) this.requestName:"command"<br>
     *         (状態) this.properties:Properties<br>
     *                (sampleKey1=sampleValue1<br>
     *
     * <br>
     * 期待値：(戻り値) String:"requestName:command（改行）<br>
     *                  properties:{sampleKey1=sampleValue1}"<br>
     *
     * <br>
     * toStringのテスト。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testToString01() throws Exception {
        // 前処理
        UTUtil.setPrivateField(context, "requestName", "command");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("sampleKey1", "sampleValue1");
        UTUtil.setPrivateField(context, "propertyMap", map);

        // テスト実施
        String result = context.toString();

        // 判定
        String expect = "requestName:command" +
                ",properties:{sampleKey1=sampleValue1}";
        assertEquals(expect, result);
    }

}
