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

package jp.terasoluna.fw.web.rich.springmvc.servlet.handler;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import jp.terasoluna.utlib.LogUTUtil;
import jp.terasoluna.utlib.MockHttpServletResponse;
import jp.terasoluna.utlib.UTUtil;
import jp.terasoluna.fw.web.rich.springmvc.Constants;
import jp.terasoluna.fw.web.rich.springmvc.servlet.handler.ExceptionResolveDelegator;
import jp.terasoluna.fw.web.rich.springmvc.servlet.handler.ExceptionResolveDelegatorImpl;
import junit.framework.TestCase;

import org.springframework.web.servlet.ModelAndView;

/**
 * {@link jp.terasoluna.fw.web.rich.springmvc.servlet.handler.ExceptionResolveDelegatorImpl} クラスのブラックボックステスト。
 * 
 * <p>
 * <h4>【クラスの概要】</h4>
 * ExceptionResolveDelegatorのデフォルト実装クラス。<br>
 * 前提条件：<br>
 * initMappingクラスの引数mappingKeyがnullになることはない。<br>
 * setHeaderメソッドの引数responseがnullになることはない。<br>
 * addObjectToModelメソッドの引数mvにnullになることはない。
 * <p>
 * 
 * @see jp.terasoluna.fw.web.rich.springmvc.servlet.handler.ExceptionResolveDelegatorImpl
 */
public class ExceptionResolveDelegatorImplTest extends TestCase {

    /**
     * initMappingメソッドのmappingKey（固定）
     */
    private static final String MAPPING_KEY = "mkey";
    
    /**
     * このテストケースを実行する為の
     * GUI アプリケーションを起動する。
     * 
     * @param args java コマンドに設定されたパラメータ
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(ExceptionResolveDelegatorImplTest.class);
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
    public ExceptionResolveDelegatorImplTest(String name) {
        super(name);
    }

    /**
     * testInitMapping01()
     * <br><br>
     * 
     * (異常系)
     * <br>
     * 観点：A,C,G
     * <br><br>
     * 入力値：(引数) mappingKey:"mkey"<br>
     *         (引数) mappingValues:null<br>
     *         (状態) mappingKey:null<br>
     *         (状態) mappingValues:null<br>
     *         (状態) viewName:null<br>
     *         (状態) errorType:null<br>
     *         (状態) errorCode:null<br>
     *         
     * <br>
     * 期待値：(状態変化) 例外:IllegalStateException{<br>
     *                      message= "linkedExceptionMappings[mkey] value is null. Check Spring Bean definition file."<br>
     *                    }<br>
     *         (状態変化) ログ:エラーレベル：エラー<br>
     *                    linkedExceptionMappings[mkey] value is null. Check Spring Bean definition file.<br>
     *         (状態変化) mappingKey:引数のmappingKey<br>
     *         (状態変化) mappingValues:引数のmappingValues<br>
     *         (状態変化) viewName:null<br>
     *         (状態変化) errorType:null<br>
     *         (状態変化) errorCode:null<br>
     *         
     * <br>
     * 引数mappingValuesがnullの場合のテスト。<br>
     * 例外をスローする。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testInitMapping01() throws Exception {
        // 前処理
        ExceptionResolveDelegator exceptionResolveDelegator = new ExceptionResolveDelegatorImpl();

        // 引数
        String mappingValues = null;

        // テスト実施
        try{
            exceptionResolveDelegator.initMapping(MAPPING_KEY, mappingValues, null);
            fail();
        } catch (IllegalStateException e) {
            // 判定
            String message = "linkedExceptionMappings[" + MAPPING_KEY
                                                     + "] value is null. "
                                                     + "Check Spring Bean definition file.";
            assertEquals(message, e.getMessage());

            // ログ確認
            assertTrue(LogUTUtil.checkError(message));
        }        
        
        

    }

    /**
     * testInitMapping02()
     * <br><br>
     * 
     * (異常系)
     * <br>
     * 観点：A,E,G
     * <br><br>
     * 入力値：(引数) mappingKey:"mkey"<br>
     *         (引数) mappingValues:java.util.Date型のインスタンス<br>
     *         (状態) mappingKey:null<br>
     *         (状態) mappingValues:null<br>
     *         (状態) viewName:null<br>
     *         (状態) errorType:null<br>
     *         (状態) errorCode:null<br>
     *         
     * <br>
     * 期待値：(状態変化) 例外:IllegalStateException{<br>
     *                      message= "linkedExceptionMappings[mkey] value is not String type. Check Spring Bean definition file."<br>
     *                    }<br>
     *         (状態変化) ログ:エラーレベル：エラー<br>
     *                    linkedExceptionMappings[mkey] value is not String type. Check Spring Bean definition file.<br>
     *         (状態変化) mappingKey:引数のmappingKey<br>
     *         (状態変化) mappingValues:引数のmappingValues<br>
     *         (状態変化) viewName:null<br>
     *         (状態変化) errorType:null<br>
     *         (状態変化) errorCode:null<br>
     *         
     * <br>
     * 引数mappingValuesがString型でない場合のテスト。<br>
     * 例外をスローする。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testInitMapping02() throws Exception {
        // 前処理
        ExceptionResolveDelegator exceptionResolveDelegator = new ExceptionResolveDelegatorImpl();

        // 引数
        Date mappingValues = new Date();

        // テスト実施
        try {
            exceptionResolveDelegator.initMapping(MAPPING_KEY, mappingValues, null);
            fail();
        } catch (IllegalStateException e) {

            // 判定
            String message = "linkedExceptionMappings[mkey] value is not String type. Check Spring Bean definition file.";
            assertEquals(message, e.getMessage());

            // ログ確認
            assertTrue(LogUTUtil.checkError(message));
        }

    }

    /**
     * testInitMapping03()
     * <br><br>
     * 
     * (異常系)
     * <br>
     * 観点：A,B,C,G
     * <br><br>
     * 入力値：(引数) mappingKey:"mkey"<br>
     *         (引数) mappingValues:""<br>
     *         (状態) mappingKey:null<br>
     *         (状態) mappingValues:null<br>
     *         (状態) viewName:null<br>
     *         (状態) errorType:null<br>
     *         (状態) errorCode:null<br>
     *         
     * <br>
     * 期待値：(状態変化) 例外:IllegalStateException{<br>
     *                      message= "linkedExceptionMappings[mkey] value is insufficient. Two values are necessary. Check Spring Bean definition file."<br>
     *                    }<br>
     *         (状態変化) ログ:エラーレベル：エラー<br>
     *                    linkedExceptionMappings[mkey] value is insufficient. Two values are necessary. Check Spring Bean definition file.<br>
     *         (状態変化) mappingKey:引数のmappingKey<br>
     *         (状態変化) mappingValues:引数のmappingValues<br>
     *         (状態変化) viewName:null<br>
     *         (状態変化) errorType:null<br>
     *         (状態変化) errorCode:null<br>
     *         
     * <br>
     * 引数mappingValuesが空文字の場合のテスト。<br>
     * 例外をスローする。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testInitMapping03() throws Exception {
        // 前処理
        ExceptionResolveDelegator exceptionResolveDelegator = new ExceptionResolveDelegatorImpl();

        // 引数
        String mappingValues = "";

        // テスト実施
        try {
            exceptionResolveDelegator.initMapping(MAPPING_KEY, mappingValues, null);
            fail();
        } catch (IllegalStateException e) {

            // 判定
            String message = "linkedExceptionMappings[mkey] value is insufficient. Two values are necessary. Check Spring Bean definition file.";
            assertEquals(message, e.getMessage());

            // ログ確認
            assertTrue(LogUTUtil.checkError(message));
        }

    }

    /**
     * testInitMapping04()
     * <br><br>
     * 
     * (異常系)
     * <br>
     * 観点：A,B,G
     * <br><br>
     * 入力値：(引数) mappingKey:"mkey"<br>
     *         (引数) mappingValues:"abc"<br>
     *         (状態) mappingKey:null<br>
     *         (状態) mappingValues:null<br>
     *         (状態) viewName:null<br>
     *         (状態) errorType:null<br>
     *         (状態) errorCode:null<br>
     *         
     * <br>
     * 期待値：(状態変化) 例外:IllegalStateException{<br>
     *                      message= "linkedExceptionMappings[mkey] value is insufficient. Two values are necessary. Check Spring Bean definition file."<br>
     *                    }<br>
     *         (状態変化) ログ:エラーレベル：エラー<br>
     *                    linkedExceptionMappings[mkey] value is insufficient. Two values are necessary. Check Spring Bean definition file.<br>
     *         (状態変化) mappingKey:引数のmappingKey<br>
     *         (状態変化) mappingValues:引数のmappingValues<br>
     *         (状態変化) viewName:null<br>
     *         (状態変化) errorType:null<br>
     *         (状態変化) errorCode:null<br>
     *         
     * <br>
     * 引数mappingValuesをカンマで分割した値が1つしかない場合のテスト。<br>
     * 例外をスローする。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testInitMapping04() throws Exception {
        // 前処理
        ExceptionResolveDelegator exceptionResolveDelegator = new ExceptionResolveDelegatorImpl();

        // 引数
        String mappingValues = "abc";

        // テスト実施
        try {
            exceptionResolveDelegator.initMapping(MAPPING_KEY, mappingValues, null);
            fail();
        } catch (IllegalStateException e) {

            // 判定
            String message = "linkedExceptionMappings[mkey] value is insufficient. Two values are necessary. Check Spring Bean definition file.";
            assertEquals(message, e.getMessage());

            // ログ確認
            assertTrue(LogUTUtil.checkError(message));
        }

    }

    /**
     * testInitMapping05()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A,B
     * <br><br>
     * 入力値：(引数) mappingKey:"mkey"<br>
     *         (引数) mappingValues:"abc,def"<br>
     *         (状態) mappingKey:null<br>
     *         (状態) mappingValues:null<br>
     *         (状態) viewName:null<br>
     *         (状態) errorType:null<br>
     *         (状態) errorCode:null<br>
     *         
     * <br>
     * 期待値：(状態変化) mappingKey:引数のmappingKey<br>
     *         (状態変化) mappingValues:引数のmappingValues<br>
     *         (状態変化) viewName:"abc"<br>
     *         (状態変化) errorType:"def"<br>
     *         (状態変化) errorCode:null<br>
     *         
     * <br>
     * 引数mappingValuesをカンマで分割した値が2つある場合のテスト。<br>
     * 正常に引数の値が属性にマッピングされる。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testInitMapping05() throws Exception {
        // 前処理
        ExceptionResolveDelegator exceptionResolveDelegator = new ExceptionResolveDelegatorImpl();

        // 引数
        String mappingValues = "abc,def";

        // テスト実施
        exceptionResolveDelegator.initMapping(MAPPING_KEY, mappingValues, null);
        
        // 判定
        assertEquals(MAPPING_KEY, UTUtil.getPrivateField(exceptionResolveDelegator, "mappingKey"));
        assertEquals(mappingValues, UTUtil.getPrivateField(exceptionResolveDelegator, "mappingValues"));
        assertEquals("abc", UTUtil.getPrivateField(exceptionResolveDelegator, "viewName"));
        assertEquals("def", UTUtil.getPrivateField(exceptionResolveDelegator, "errorType"));

    }

    /**
     * testInitMapping06()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A,B
     * <br><br>
     * 入力値：(引数) mappingKey:"mkey"<br>
     *         (引数) mappingValues:"abc , def,ghi"<br>
     *         (状態) mappingKey:null<br>
     *         (状態) mappingValues:null<br>
     *         (状態) viewName:null<br>
     *         (状態) errorType:null<br>
     *         (状態) errorCode:null<br>
     *         
     * <br>
     * 期待値：(状態変化) mappingKey:引数のmappingKey<br>
     *         (状態変化) mappingValues:引数のmappingValues<br>
     *         (状態変化) viewName:"abc"<br>
     *         (状態変化) errorType:"def"<br>
     *         (状態変化) errorCode:"ghi"<br>
     *         
     * <br>
     * 引数mappingValuesをカンマで分割した値が3つある場合のテスト。<br>
     * 正常に引数の値が属性にマッピングされる。エラーコードが設定される。<br>
     * カンマで分割した値の前後に空白がある場合、削除される。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testInitMapping06() throws Exception {
        // 前処理
        ExceptionResolveDelegator exceptionResolveDelegator = new ExceptionResolveDelegatorImpl();

        // 引数
        String mappingValues = "abc , def,ghi";

        // テスト実施
        exceptionResolveDelegator.initMapping(MAPPING_KEY, mappingValues, null);
        
        // 判定
        assertEquals(MAPPING_KEY, UTUtil.getPrivateField(exceptionResolveDelegator, "mappingKey"));
        assertEquals(mappingValues, UTUtil.getPrivateField(exceptionResolveDelegator, "mappingValues"));
        assertEquals("abc", UTUtil.getPrivateField(exceptionResolveDelegator, "viewName"));
        assertEquals("def", UTUtil.getPrivateField(exceptionResolveDelegator, "errorType"));
        assertEquals("ghi", UTUtil.getPrivateField(exceptionResolveDelegator, "errorCode"));
    }

    /**
     * testInitMapping07()
     * <br><br>
     * 
     * (異常系)
     * <br>
     * 観点：A,C,G
     * <br><br>
     * 入力値：(引数) mappingKey:"mkey"<br>
     *         (引数) mappingValues:",def,ghi"<br>
     *         (状態) mappingKey:null<br>
     *         (状態) mappingValues:null<br>
     *         (状態) viewName:null<br>
     *         (状態) errorType:null<br>
     *         (状態) errorCode:null<br>
     *         
     * <br>
     * 期待値：(状態変化) 例外:IllegalStateException{<br>
     *                      message= "linkedExceptionMappings[mkey] value[0] is empty. Check Spring Bean definition file."<br>
     *                    }<br>
     *         (状態変化) ログ:ログレベル：エラー<br>
     *                    linkedExceptionMappings[mkey] value[0] is empty. Check Spring Bean definition file.<br>
     *         (状態変化) mappingKey:引数のmappingKey<br>
     *         (状態変化) mappingValues:引数のmappingValues<br>
     *         (状態変化) viewName:null<br>
     *         (状態変化) errorType:null<br>
     *         (状態変化) errorCode:null<br>
     *         
     * <br>
     * 引数mappingValuesをカンマで分割した1つ目の値に空文字が設定されている場合のテスト。例外をスローする。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testInitMapping07() throws Exception {
        // 前処理
        ExceptionResolveDelegator exceptionResolveDelegator = new ExceptionResolveDelegatorImpl();

        // 引数
        String mappingValues = ",def,ghi";

        // テスト実施
        try {
            exceptionResolveDelegator.initMapping(MAPPING_KEY, mappingValues, null);
            fail();
        } catch (IllegalStateException e) {

            // 判定
            String message = "linkedExceptionMappings[mkey] value[0] is empty. Check Spring Bean definition file.";
            assertEquals(message, e.getMessage());

            // ログ確認
            assertTrue(LogUTUtil.checkError(message));
        }
    }

    /**
     * testInitMapping08()
     * <br><br>
     * 
     * (異常系)
     * <br>
     * 観点：A,C,G
     * <br><br>
     * 入力値：(引数) mappingKey:"mkey"<br>
     *         (引数) mappingValues:"abc, ,ghi"<br>
     *         (状態) mappingKey:null<br>
     *         (状態) mappingValues:null<br>
     *         (状態) viewName:null<br>
     *         (状態) errorType:null<br>
     *         (状態) errorCode:null<br>
     *         
     * <br>
     * 期待値：(状態変化) 例外:IllegalStateException{<br>
     *                      message= "linkedExceptionMappings[mkey] value[1] is empty. Check Spring Bean definition file."<br>
     *                    }<br>
     *         (状態変化) ログ:ログレベル：エラー<br>
     *                    linkedExceptionMappings[mkey] value[1] is empty. Check Spring Bean definition file.<br>
     *         (状態変化) mappingKey:引数のmappingKey<br>
     *         (状態変化) mappingValues:引数のmappingValues<br>
     *         (状態変化) viewName:"abc"<br>
     *         (状態変化) errorType:"def"<br>
     *         (状態変化) errorCode:"ghi"<br>
     *         
     * <br>
     * 引数mappingValuesをカンマで分割した2つ目の値に空文字（スペースだけの値）が設定されている場合のテスト。例外をスローする。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testInitMapping08() throws Exception {
        // 前処理
        ExceptionResolveDelegator exceptionResolveDelegator = new ExceptionResolveDelegatorImpl();

        // 引数
        String mappingValues = "abc, ,ghi";

        // テスト実施
        try {
            exceptionResolveDelegator.initMapping(MAPPING_KEY, mappingValues, null);
            fail();
        } catch (IllegalStateException e) {

            // 判定
            String message = "linkedExceptionMappings[mkey] value[1] is empty. Check Spring Bean definition file.";
            assertEquals(message, e.getMessage());

            // ログ確認
            assertTrue(LogUTUtil.checkError(message));
        }
    }

    /**
     * testInitMapping09()
     * <br><br>
     * 
     * (異常系)
     * <br>
     * 観点：A,C,G
     * <br><br>
     * 入力値：(引数) mappingKey:"mkey"<br>
     *         (引数) mappingValues:"abc,def,"<br>
     *         (状態) mappingKey:null<br>
     *         (状態) mappingValues:null<br>
     *         (状態) viewName:null<br>
     *         (状態) errorType:null<br>
     *         (状態) errorCode:null<br>
     *         
     * <br>
     * 期待値：(状態変化) 例外:IllegalStateException{<br>
     *                      message= "linkedExceptionMappings[mkey] value[2] is empty. Check Spring Bean definition file."<br>
     *                    }<br>
     *         (状態変化) ログ:ログレベル：エラー<br>
     *                    linkedExceptionMappings[mkey] value[2] is empty. Check Spring Bean definition file.<br>
     *         (状態変化) mappingKey:引数のmappingKey<br>
     *         (状態変化) mappingValues:引数のmappingValues<br>
     *         (状態変化) viewName:"abc"<br>
     *         (状態変化) errorType:"def"<br>
     *         (状態変化) errorCode:"ghi"<br>
     *         
     * <br>
     * 引数mappingValuesをカンマで分割した3つ目の値に空文字が設定されている場合のテスト。例外をスローする。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testInitMapping09() throws Exception {
        // 前処理
        ExceptionResolveDelegator exceptionResolveDelegator = new ExceptionResolveDelegatorImpl();

        // 引数
        String mappingValues = "abc,def,";

        // テスト実施
        try {
            exceptionResolveDelegator.initMapping(MAPPING_KEY, mappingValues, null);
            fail();
        } catch (IllegalStateException e) {

            // 判定
            String message = "linkedExceptionMappings[mkey] value[2] is empty. Check Spring Bean definition file.";
            assertEquals(message, e.getMessage());

            // ログ確認
            assertTrue(LogUTUtil.checkError(message));
        }
    }

    /**
     * testInitMapping10()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A,B
     * <br><br>
     * 入力値：(引数) mappingKey:"mkey"<br>
     *         (引数) mappingValues:"abc,def"<br>
     *         (引数) params:null<br>
     *         (状態) mappingKey:null<br>
     *         (状態) mappingValues:null<br>
     *         (状態) viewName:null<br>
     *         (状態) errorType:null<br>
     *         (状態) errorCode:null<br>
     *         (状態) errorTypeHeaderName:デフォルト値("exception")<br>
     *         
     * <br>
     * 期待値：(状態変化) mappingKey:引数のmappingKey<br>
     *         (状態変化) mappingValues:引数のmappingValues<br>
     *         (状態変化) viewName:"abc"<br>
     *         (状態変化) errorType:"def"<br>
     *         (状態変化) errorCode:null<br>
     *         (状態変化) errorTypeHeaderName:"exception"<br>
     *         
     * <br>
     * 引数paramsがnull場合のテスト。<br>
     * 属性errorTypeHeaderNameはデフォルト値"exception"であること確認。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testInitMapping10() throws Exception {
        // 前処理
        ExceptionResolveDelegator exceptionResolveDelegator = new ExceptionResolveDelegatorImpl();
        Map<String,String> params = null;

        // 引数
        String mappingValues = "abc,def";

        // テスト実施
        exceptionResolveDelegator.initMapping(MAPPING_KEY, mappingValues, params);
        
        // 判定
        assertEquals(MAPPING_KEY, UTUtil.getPrivateField(exceptionResolveDelegator, "mappingKey"));
        assertEquals(mappingValues, UTUtil.getPrivateField(exceptionResolveDelegator, "mappingValues"));
        assertEquals("abc", UTUtil.getPrivateField(exceptionResolveDelegator, "viewName"));
        assertEquals("def", UTUtil.getPrivateField(exceptionResolveDelegator, "errorType"));
        assertEquals("exception", UTUtil.getPrivateField(exceptionResolveDelegator, "errorTypeHeaderName"));

    }

    /**
     * testInitMapping11()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A,B
     * <br><br>
     * 入力値：(引数) mappingKey:"mkey"<br>
     *         (引数) mappingValues:"abc,def"<br>
     *         (引数) params:{"key","value"}<br>
     *         (状態) mappingKey:null<br>
     *         (状態) mappingValues:null<br>
     *         (状態) viewName:null<br>
     *         (状態) errorType:null<br>
     *         (状態) errorCode:null<br>
     *         (状態) errorTypeHeaderName:デフォルト値("exception")<br>
     *         
     * <br>
     * 期待値：(状態変化) mappingKey:引数のmappingKey<br>
     *         (状態変化) mappingValues:引数のmappingValues<br>
     *         (状態変化) viewName:"abc"<br>
     *         (状態変化) errorType:"def"<br>
     *         (状態変化) errorCode:null<br>
     *         (状態変化) errorTypeHeaderName:"exceptionName"<br>
     *         
     * <br>
     * 引数paramsにキー"errorTypeHeaderName"が存在しない場合のテスト。<br>
     * 属性errorTypeHeaderNameはデフォルト値"exception"であること確認。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testInitMapping11() throws Exception {
        // 前処理
        ExceptionResolveDelegator exceptionResolveDelegator = new ExceptionResolveDelegatorImpl();
        Map<String,String> params = new HashMap<String,String>();
        params.put("key","value");

        // 引数
        String mappingValues = "abc,def";

        // テスト実施
        exceptionResolveDelegator.initMapping(MAPPING_KEY, mappingValues, params);
        
        // 判定
        assertEquals(MAPPING_KEY, UTUtil.getPrivateField(exceptionResolveDelegator, "mappingKey"));
        assertEquals(mappingValues, UTUtil.getPrivateField(exceptionResolveDelegator, "mappingValues"));
        assertEquals("abc", UTUtil.getPrivateField(exceptionResolveDelegator, "viewName"));
        assertEquals("def", UTUtil.getPrivateField(exceptionResolveDelegator, "errorType"));
        assertEquals("exception", UTUtil.getPrivateField(exceptionResolveDelegator, "errorTypeHeaderName"));

    }
    

    /**
     * testInitMapping12()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A,C
     * <br><br>
     * 入力値：(引数) mappingKey:"mkey"<br>
     *         (引数) mappingValues:"abc,def"<br>
     *         (引数) params:{"errorTypeHeaderName",null}<br>
     *         (状態) mappingKey:null<br>
     *         (状態) mappingValues:null<br>
     *         (状態) viewName:null<br>
     *         (状態) errorType:null<br>
     *         (状態) errorCode:null<br>
     *         (状態) errorTypeHeaderName:デフォルト値("exception")<br>
     *         
     * <br>
     * 期待値：(状態変化) mappingKey:引数のmappingKey<br>
     *         (状態変化) mappingValues:引数のmappingValues<br>
     *         (状態変化) viewName:"abc"<br>
     *         (状態変化) errorType:"def"<br>
     *         (状態変化) errorCode:null<br>
     *         (状態変化) errorTypeHeaderName:"exception"<br>
     *         
     * <br>
     * 引数paramsにキー"errorTypeHeaderName"が存在し,その値がnull場合のテスト。<br>
     * 属性errorTypeHeaderNameはデフォルト値"exception"であること確認。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testInitMapping12() throws Exception {
        // 前処理
        ExceptionResolveDelegator exceptionResolveDelegator = new ExceptionResolveDelegatorImpl();
        Map<String,String> params = new HashMap<String,String>();
        params.put("errorTypeHeaderName", null);

        // 引数
        String mappingValues = "abc,def";

        // テスト実施
        exceptionResolveDelegator.initMapping(MAPPING_KEY, mappingValues, params);
        
        // 判定
        assertEquals(MAPPING_KEY, UTUtil.getPrivateField(exceptionResolveDelegator, "mappingKey"));
        assertEquals(mappingValues, UTUtil.getPrivateField(exceptionResolveDelegator, "mappingValues"));
        assertEquals("abc", UTUtil.getPrivateField(exceptionResolveDelegator, "viewName"));
        assertEquals("def", UTUtil.getPrivateField(exceptionResolveDelegator, "errorType"));
        assertEquals("exception", UTUtil.getPrivateField(exceptionResolveDelegator, "errorTypeHeaderName"));

    }
    
    /**
     * testInitMapping13()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A,C
     * <br><br>
     * 入力値：(引数) mappingKey:"mkey"<br>
     *         (引数) mappingValues:"abc,def"<br>
     *         (引数) params:{"errorTypeHeaderName",""}<br>
     *         (状態) mappingKey:null<br>
     *         (状態) mappingValues:null<br>
     *         (状態) viewName:null<br>
     *         (状態) errorType:null<br>
     *         (状態) errorCode:null<br>
     *         (状態) errorTypeHeaderName:デフォルト値("exception")<br>
     *         
     * <br>
     * 期待値：(状態変化) mappingKey:引数のmappingKey<br>
     *         (状態変化) mappingValues:引数のmappingValues<br>
     *         (状態変化) viewName:"abc"<br>
     *         (状態変化) errorType:"def"<br>
     *         (状態変化) errorCode:null<br>
     *         (状態変化) errorTypeHeaderName:"exception"<br>
     *         
     * <br>
     * 引数paramsにキー"errorTypeHeaderName"が存在し,その値が空文字場合のテスト。<br>
     * 属性errorTypeHeaderNameはデフォルト値"exception"であること確認。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testInitMapping13() throws Exception {
        // 前処理
        ExceptionResolveDelegator exceptionResolveDelegator = new ExceptionResolveDelegatorImpl();
        Map<String,String> params = new HashMap<String,String>();
        params.put("errorTypeHeaderName", "");

        // 引数
        String mappingValues = "abc,def";

        // テスト実施
        exceptionResolveDelegator.initMapping(MAPPING_KEY, mappingValues, params);
        
        // 判定
        assertEquals(MAPPING_KEY, UTUtil.getPrivateField(exceptionResolveDelegator, "mappingKey"));
        assertEquals(mappingValues, UTUtil.getPrivateField(exceptionResolveDelegator, "mappingValues"));
        assertEquals("abc", UTUtil.getPrivateField(exceptionResolveDelegator, "viewName"));
        assertEquals("def", UTUtil.getPrivateField(exceptionResolveDelegator, "errorType"));
        assertEquals("exception", UTUtil.getPrivateField(exceptionResolveDelegator, "errorTypeHeaderName"));

    }
    
    /**
     * testInitMapping14()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A,B
     * <br><br>
     * 入力値：(引数) mappingKey:"mkey"<br>
     *         (引数) mappingValues:"abc,def"<br>
     *         (引数) params:{"errorTypeHeaderName","exceptionName"}<br>
     *         (状態) mappingKey:null<br>
     *         (状態) mappingValues:null<br>
     *         (状態) viewName:null<br>
     *         (状態) errorType:null<br>
     *         (状態) errorCode:null<br>
     *         (状態) errorTypeHeaderName:デフォルト値("exception")<br>
     *         
     * <br>
     * 期待値：(状態変化) mappingKey:引数のmappingKey<br>
     *         (状態変化) mappingValues:引数のmappingValues<br>
     *         (状態変化) viewName:"abc"<br>
     *         (状態変化) errorType:"def"<br>
     *         (状態変化) errorCode:null<br>
     *         (状態変化) errorTypeHeaderName:"exceptionName"<br>
     *         
     * <br>
     * 引数paramsにキー"errorTypeHeaderName"が存在し,その値がnullと空文字以外場合のテスト。<br>
     * 属性errorTypeHeaderNameにその値を設定すること確認。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testInitMapping14() throws Exception {
        // 前処理
        ExceptionResolveDelegator exceptionResolveDelegator = new ExceptionResolveDelegatorImpl();
        Map<String,String> params = new HashMap<String,String>();
        params.put("errorTypeHeaderName", "exceptionName");

        // 引数
        String mappingValues = "abc,def";

        // テスト実施
        exceptionResolveDelegator.initMapping(MAPPING_KEY, mappingValues, params);
        
        // 判定
        assertEquals(MAPPING_KEY, UTUtil.getPrivateField(exceptionResolveDelegator, "mappingKey"));
        assertEquals(mappingValues, UTUtil.getPrivateField(exceptionResolveDelegator, "mappingValues"));
        assertEquals("abc", UTUtil.getPrivateField(exceptionResolveDelegator, "viewName"));
        assertEquals("def", UTUtil.getPrivateField(exceptionResolveDelegator, "errorType"));
        assertEquals("exceptionName", UTUtil.getPrivateField(exceptionResolveDelegator, "errorTypeHeaderName"));

    }
    
    /**
     * testSetHeader01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) response:HttpResponseインスタンス<br>
     *         (状態) this.exceptionKey:EXCEPTION_KEY = "exception"<br>
     *         (状態) this.errorType:"validation"<br>
     *         
     * <br>
     * 期待値：(状態変化) response:ヘッダの exception属性に"validation"が設定されることを確認する。<br>
     *         
     * <br>
     * 引数のレスポンスにヘッダが設定されることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetHeader01() throws Exception {
        // 前処理
        ExceptionResolveDelegator exceptionResolveDelegator = new ExceptionResolveDelegatorImpl();
        UTUtil.setPrivateField(exceptionResolveDelegator, "errorType", "validation");

        // 引数
        MockHttpServletResponse response = new MockHttpServletResponse();

        // テスト実施
        exceptionResolveDelegator.setHeader(response);

        // 判定
        assertEquals("validation", ((Map)UTUtil.getPrivateField(response, "httpHeaderMap")).get(ExceptionResolveDelegatorImpl.EXCEPTION_KEY));            
    }

    /**
     * testAddObjectToModel01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A,C
     * <br><br>
     * 入力値：(引数) mv:ModelAndViewインスタンス<br>
     *         (状態) this.errorCode:null<br>
     *         (状態) Constants.ERRORCODE_KEY:"errorCode"<br>
     *         
     * <br>
     * 期待値：(状態変化) mv:errorCode= null<br>
     *         
     * <br>
     * this.errorCode属性の値がnullの場合、ModelAndViewのerrorCode属性にエラーコードが設定されないことを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testAddObjectToModel01() throws Exception {
        // 前処理
        ExceptionResolveDelegator exceptionResolveDelegator = new ExceptionResolveDelegatorImpl();
        UTUtil.setPrivateField(exceptionResolveDelegator, "errorCode", null);

        // 引数
        ModelAndView mv = new ModelAndView();

        
        // テスト実施
        exceptionResolveDelegator.addObjectToModel(mv);

        // 判定
        assertNull(mv.getModel().get(Constants.ERRORCODE_KEY));            
            
    }

    /**
     * testAddObjectToModel02()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) mv:ModelAndViewインスタンス<br>
     *         (状態) this.errorCode:"abc"<br>
     *         (状態) Constants.ERRORCODE_KEY:errorCode<br>
     *         
     * <br>
     * 期待値：(状態変化) mv:errorCode= "abc"<br>
     *         
     * <br>
     * this.errorCode属性の値が"abc"の場合、ModelAndViewのerrorCode属性にエラーコード"abc"が設定されることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testAddObjectToModel02() throws Exception {
        // 前処理
        ExceptionResolveDelegator exceptionResolveDelegator = new ExceptionResolveDelegatorImpl();
        UTUtil.setPrivateField(exceptionResolveDelegator, "errorCode", "abc");

        // 引数
        ModelAndView mv = new ModelAndView();

        
        // テスト実施
        exceptionResolveDelegator.addObjectToModel(mv);

        // 判定
        assertEquals("abc", mv.getModel().get(Constants.ERRORCODE_KEY));            
            
    }

    /**
     * testGetViewName01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(状態) this.viewName:"abc"<br>
     *         
     * <br>
     * 期待値：(戻り値) String:"abc"<br>
     *         
     * <br>
     * viewName属性の値が正しく返却されることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetViewName01() throws Exception {
        // 前処理
        ExceptionResolveDelegator exceptionResolveDelegator = new ExceptionResolveDelegatorImpl();
        UTUtil.setPrivateField(exceptionResolveDelegator, "viewName", "abc");
        
        // テスト実施＆判定
        assertEquals("abc", exceptionResolveDelegator.getViewName());
            
    }

}
