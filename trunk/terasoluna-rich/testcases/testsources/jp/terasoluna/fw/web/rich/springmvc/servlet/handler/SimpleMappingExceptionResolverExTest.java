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

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import jp.terasoluna.utlib.LogUTUtil;
import jp.terasoluna.utlib.MockHttpServletRequest;
import jp.terasoluna.utlib.MockHttpServletResponse;
import jp.terasoluna.utlib.UTUtil;
import jp.terasoluna.fw.web.rich.springmvc.servlet.handler.ExceptionResolveDelegator;
import jp.terasoluna.fw.web.rich.springmvc.servlet.handler.SimpleMappingExceptionResolverEx;
import junit.framework.TestCase;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;
import org.springframework.web.servlet.mvc.Controller;

/**
 * {@link jp.terasoluna.fw.web.rich.springmvc.servlet.handler.
 * SimpleMappingExceptionResolverEx} クラスのブラックボックステスト。
 * 
 * <p>
 * <h4>【クラスの概要】</h4>
 * 例外クラス名とビュー名のマッピングを行うException resolverの拡張クラス。<br>
 * 前提条件：
 * <p>
 * 
 * @see jp.terasoluna.fw.web.rich.springmvc.servlet.handler.
 * SimpleMappingExceptionResolverEx
 */
public class SimpleMappingExceptionResolverExTest extends TestCase {

    /**
     * このテストケースを実行する為の
     * GUI アプリケーションを起動する。
     * 
     * @param args java コマンドに設定されたパラメータ
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(
                SimpleMappingExceptionResolverExTest.class);
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
    public SimpleMappingExceptionResolverExTest(String name) {
        super(name);
    }

    /**
     * testAfterPropertiesSet01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A,C
     * <br><br>
     * 入力値：(状態) this.linkedExceptionMappings:null<br>
     *         (状態) this.exceptionResolveDelegatorClass:not null<br>
     *         (状態) this.exceptionResolveDelegatorMap:空のLinkedHashMap<br>
     *         
     * <br>
     * 期待値：(状態変化) this.exceptionResolveDelegatorMap:空のLinkedHashMap<br>
     *         
     * <br>
     * this.linkedExceptionMappingsがnullの場合のテスト。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testAfterPropertiesSet01() throws Exception {
        // 前処理
        SimpleMappingExceptionResolverEx simpleMappingExceptionResolverEx = new SimpleMappingExceptionResolverEx();
        LinkedHashMap linkedExceptionMappings = null;
        UTUtil.setPrivateField(simpleMappingExceptionResolverEx, "linkedExceptionMappings", linkedExceptionMappings);
        LinkedHashMap exceptionResolveDelegatorMap = (LinkedHashMap)UTUtil.getPrivateField(simpleMappingExceptionResolverEx, "exceptionResolveDelegatorMap");
        // テスト実施
        simpleMappingExceptionResolverEx.afterPropertiesSet();
        
        // 判定
        assertTrue(exceptionResolveDelegatorMap.isEmpty());
    }

    /**
     * testAfterPropertiesSet02()
     * <br><br>
     * 
     * (異常系)
     * <br>
     * 観点：A,C,G
     * <br><br>
     * 入力値：(状態) this.linkedExceptionMappings:not null<br>
     *         (状態) this.exceptionResolveDelegatorClass:null<br>
     *         (状態) this.exceptionResolveDelegatorMap:空のLinkedHashMap<br>
     *         
     * <br>
     * 期待値：(状態変化) 例外:IllegalStateException{<br>
     *                      detailMessage= "SimpleMappingExceptionResolverEx must be set exceptionResolveDelegatorClass. Check Spring Bean definition file."<br>
     *                    }<br>
     *         (状態変化) ログ:ログレベル：エラー<br>
     *                    メッセージ："SimpleMappingExceptionResolverEx must be set exceptionResolveDelegatorClass. Check Spring Bean definition file."<br>
     *         (状態変化) this.exceptionResolveDelegatorMap:空のLinkedHashMap<br>
     *         
     * <br>
     * this.exceptionResolveDelegatorClassがnullの場合のテスト。例外をスローする。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testAfterPropertiesSet02() throws Exception {
        // 前処理
        SimpleMappingExceptionResolverEx simpleMappingExceptionResolverEx = new SimpleMappingExceptionResolverEx();
        LinkedHashMap<String, Object> linkedExceptionMappings = new LinkedHashMap<String, Object>();
        UTUtil.setPrivateField(simpleMappingExceptionResolverEx, "linkedExceptionMappings", linkedExceptionMappings);
        
        Class cl = null;
        UTUtil.setPrivateField(simpleMappingExceptionResolverEx, "exceptionResolveDelegatorClass", cl);
        
        // テスト実施
        try {
            simpleMappingExceptionResolverEx.afterPropertiesSet();
            fail();
        } catch (IllegalStateException e) {

            // 判定
            String message = "SimpleMappingExceptionResolverEx must be set exceptionResolveDelegatorClass. Check Spring Bean definition file.";
            assertEquals(message, e.getMessage());

            // ログ確認
            assertTrue(LogUTUtil.checkError(message));
        }
    }

    /**
     * testAfterPropertiesSet03()
     * <br><br>
     * 
     * (異常系)
     * <br>
     * 観点：A,E,G
     * <br><br>
     * 入力値：(状態) this.linkedExceptionMappings:not null<br>
     *         (状態) this.exceptionResolveDelegatorClass:java.util.Dateクラス<br>
     *         (状態) this.exceptionResolveDelegatorMap:空のLinkedHashMap<br>
     *         
     * <br>
     * 期待値：(状態変化) 例外:IllegalStateException{<br>
     *                      detailMessage= "java.util.Date is not ExceptionResolveDelegator type. Check Spring Bean definition file."<br>
     *                    }<br>
     *         (状態変化) ログ:ログレベル：エラー<br>
     *                    メッセージ："java.util.Date is not ExceptionResolveDelegator type. Check Spring Bean definition file."<br>
     *         (状態変化) this.exceptionResolveDelegatorMap:空のLinkedHashMap<br>
     *         
     * <br>
     * this.exceptionResolveDelegatorClassの型がExceptionResolveDelegator型でない場合のテスト。例外をスローする。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testAfterPropertiesSet03() throws Exception {
        // 前処理
        SimpleMappingExceptionResolverEx simpleMappingExceptionResolverEx = new SimpleMappingExceptionResolverEx();
        LinkedHashMap<String, Object> linkedExceptionMappings = new LinkedHashMap<String, Object>();
        linkedExceptionMappings.put("abc","123");
        UTUtil.setPrivateField(simpleMappingExceptionResolverEx, "linkedExceptionMappings", linkedExceptionMappings);
        
        Class cl = java.util.Date.class;
        UTUtil.setPrivateField(simpleMappingExceptionResolverEx, "exceptionResolveDelegatorClass", cl);
        
        // テスト実施
        try {
            simpleMappingExceptionResolverEx.afterPropertiesSet();
            fail();
        } catch (IllegalStateException e) {

            // 判定
            String message = cl.getName() + " is not ExceptionResolveDelegator type. Check Spring Bean definition file.";
            assertEquals(message, e.getMessage());

            // ログ確認
            assertTrue(LogUTUtil.checkError(message));
        }        
    }

    /**
     * testAfterPropertiesSet04()
     * <br><br>
     * 
     * (異常系)
     * <br>
     * 観点：A,G
     * <br><br>
     * 入力値：(状態) this.linkedExceptionMappings:not null<br>
     *         (状態) this.exceptionResolveDelegatorClass:引数があるコンストラクを持つExceptionResolveDelegator実装クラス<br>
     *         (状態) this.exceptionResolveDelegatorMap:空のLinkedHashMap<br>
     *         
     * <br>
     * 期待値：(状態変化) 例外:IllegalStateException{<br>
     *                      detailMessage= this.exceptionResolveDelegatorClass.getName() + " cannot be instantiated. Check Spring Bean definition file.",<br>
     *                      cause= InstantiationException<br>
     *                    }<br>
     *         (状態変化) ログ:ログレベル：エラー<br>
     *                    メッセージ：this.exceptionResolveDelegatorClass.getName() + " cannot be instantiated. Check Spring Bean definition file.<br>
     *                    例外：InstantiationException<br>
     *         (状態変化) this.exceptionResolveDelegatorMap:空のLinkedHashMap<br>
     *         
     * <br>
     * this.exceptionResolveDelegatorClassのインスタンス化に失敗した場合のテスト。例外をスローする。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testAfterPropertiesSet04() throws Exception {
        // 前処理
        SimpleMappingExceptionResolverEx simpleMappingExceptionResolverEx = new SimpleMappingExceptionResolverEx();
        LinkedHashMap<String, Object> linkedExceptionMappings = new LinkedHashMap<String, Object>();
        linkedExceptionMappings.put("abc","123");
        UTUtil.setPrivateField(simpleMappingExceptionResolverEx, "linkedExceptionMappings", linkedExceptionMappings);
        
        // exceptionResolveDelegatorMap
        LinkedHashMap<String, ExceptionResolveDelegator>exceptionResolveDelegatorMap = new LinkedHashMap<String, ExceptionResolveDelegator>();
        UTUtil.setPrivateField(simpleMappingExceptionResolverEx, "exceptionResolveDelegatorMap", exceptionResolveDelegatorMap);

        Class cl = SimpleMappingExceptionResolverEx_ExceptionResolveDelegatorImplStub02.class;
        UTUtil.setPrivateField(simpleMappingExceptionResolverEx, "exceptionResolveDelegatorClass", cl);
        
        // テスト実施
        try {
            simpleMappingExceptionResolverEx.afterPropertiesSet();
            fail();
        } catch (IllegalStateException e) {

            // 判定
            String message = cl.getName() + " cannot be instantiated. Check Spring Bean definition file.";
            assertEquals(message, e.getMessage());

            // ログ確認
            assertTrue(LogUTUtil.checkError(message, e.getCause()));
        }        
    }

    /**
     * testAfterPropertiesSet05()
     * <br><br>
     * 
     * (異常系)
     * <br>
     * 観点：A,G
     * <br><br>
     * 入力値：(状態) this.linkedExceptionMappings:not null<br>
     *         (状態) this.exceptionResolveDelegatorClass:privateなコンストラクタを持つExceptionResolveDelegator実装クラス<br>
     *         (状態) this.exceptionResolveDelegatorMap:空のLinkedHashMap<br>
     *         
     * <br>
     * 期待値：(状態変化) 例外:IllegalStateException{<br>
     *                      detailMessage= this.exceptionResolveDelegatorClass.getName() + " cannot be instantiated. Check Spring Bean definition file.",<br>
     *                      cause= IllegalAccessException<br>
     *                    }<br>
     *         (状態変化) ログ:ログレベル：エラー<br>
     *                    メッセージ：this.exceptionResolveDelegatorClass.getName() + " cannot be instantiated. Check Spring Bean definition file.<br>
     *                    例外：InstantiationException<br>
     *         (状態変化) this.exceptionResolveDelegatorMap:空のLinkedHashMap<br>
     *         
     * <br>
     * this.exceptionResolveDelegatorClassのインスタンス化に失敗した場合のテスト。例外をスローする。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testAfterPropertiesSet05() throws Exception {
        // 前処理
        SimpleMappingExceptionResolverEx simpleMappingExceptionResolverEx = new SimpleMappingExceptionResolverEx();
        LinkedHashMap<String, Object> linkedExceptionMappings = new LinkedHashMap<String, Object>();
        linkedExceptionMappings.put("abc","def");
        UTUtil.setPrivateField(simpleMappingExceptionResolverEx, "linkedExceptionMappings", linkedExceptionMappings);
        
        // exceptionResolveDelegatorMap
        LinkedHashMap<String, ExceptionResolveDelegator>exceptionResolveDelegatorMap = new LinkedHashMap<String, ExceptionResolveDelegator>();
        UTUtil.setPrivateField(simpleMappingExceptionResolverEx, "exceptionResolveDelegatorMap", exceptionResolveDelegatorMap);

        Class cl = SimpleMappingExceptionResolverEx_ExceptionResolveDelegatorImplStub03.class;
        UTUtil.setPrivateField(simpleMappingExceptionResolverEx, "exceptionResolveDelegatorClass", cl);
        
        // テスト実施
        try {
            simpleMappingExceptionResolverEx.afterPropertiesSet();
            fail();
        } catch (IllegalStateException e) {
            // 判定
            String message = cl.getName() + " cannot be instantiated. "
                        + "Check Spring Bean definition file.";
            assertEquals(message, e.getMessage());

            // ログ確認
            assertTrue(LogUTUtil.checkError(message, e.getCause()));
        }        
    }

    /**
     * testAfterPropertiesSet06()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A,D
     * <br><br>
     * 入力値：(状態) this.linkedExceptionMappings:空のLinkedHashMap<br>
     *         (状態) this.exceptionResolveDelegatorClass:引数なしのコンストラクタを持つExceptionResolveDelegator実装クラス<br>
     *         (状態) this.exceptionResolveDelegatorMap:空のLinkedHashMap<br>
     *         
     * <br>
     * 期待値：(状態変化) this.exceptionResolveDelegatorMap:空のLinkedHashMap<br>
     *         
     * <br>
     * this.linkedExceptionMappingsが空の場合のテスト。何も行われないことを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testAfterPropertiesSet06() throws Exception {
        // 前処理
        SimpleMappingExceptionResolverEx simpleMappingExceptionResolverEx = new SimpleMappingExceptionResolverEx();
        LinkedHashMap<String, Object> linkedExceptionMappings = new LinkedHashMap<String, Object>();
        UTUtil.setPrivateField(simpleMappingExceptionResolverEx, "linkedExceptionMappings", linkedExceptionMappings);
        
        // exceptionResolveDelegatorMap
        LinkedHashMap<String, ExceptionResolveDelegator>exceptionResolveDelegatorMap = new LinkedHashMap<String, ExceptionResolveDelegator>();
        UTUtil.setPrivateField(simpleMappingExceptionResolverEx, "exceptionResolveDelegatorMap", exceptionResolveDelegatorMap);

        Class cl = jp.terasoluna.fw.web.rich.springmvc.servlet.handler.ExceptionResolveDelegatorImpl.class;
        UTUtil.setPrivateField(simpleMappingExceptionResolverEx, "exceptionResolveDelegatorClass", cl);
        
        // テスト実施
        simpleMappingExceptionResolverEx.afterPropertiesSet();
        
        // 判定
        assertTrue(exceptionResolveDelegatorMap.isEmpty());
    }

    /**
     * testAfterPropertiesSet07()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A,D
     * <br><br>
     * 入力値：(状態) this.linkedExceptionMappings:LinkedHashMap {<br>
     *                  "abc"= "123"<br>
     *                }<br>
     *         (状態) this.exceptionResolveDelegatorClass:引数なしのコンストラクタを持つExceptionResolveDelegator実装クラス<br>
     *         (状態) this.exceptionResolveDelegatorMap:空のLinkedHashMap<br>
     *         (状態) this.exceptionResolveDelegatorParams:HashMap{<br>
     *                 "errorTypeHeaderName"="errorType"<br>
     *                }<br>
     *         
     * <br>
     * 期待値：(状態変化) this.exceptionResolveDelegatorMap:LinkedHashMap{<br>
     *                      "abc"= ExceptionResolveDelegatorインスタンス{<br>
     *                        mappingKey= "abc"<br>
     *                        mappingValues= "123"<br>
     *                        paramsMap=HashMap{<br>
     *                          "errorTypeHeaderName"="errorType"<br>
     *                        }<br>
     *                      }<br>
     *                    }<br>
     *         
     * <br>
     * this.linkedExceptionMappingsの要素が１つの場合のテスト。this.exceptionResolveDelegatorMapに要素が1つ追加されることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testAfterPropertiesSet07() throws Exception {
        // 前処理
        SimpleMappingExceptionResolverEx simpleMappingExceptionResolverEx = new SimpleMappingExceptionResolverEx();
        LinkedHashMap<String, Object> linkedExceptionMappings = new LinkedHashMap<String, Object>();
        linkedExceptionMappings.put("abc", "123");
        UTUtil.setPrivateField(simpleMappingExceptionResolverEx, "linkedExceptionMappings", linkedExceptionMappings);
        
        // exceptionResolveDelegatorMap
        LinkedHashMap<String, ExceptionResolveDelegator>exceptionResolveDelegatorMap = new LinkedHashMap<String, ExceptionResolveDelegator>();
        UTUtil.setPrivateField(simpleMappingExceptionResolverEx, "exceptionResolveDelegatorMap", exceptionResolveDelegatorMap);

        Class cl = SimpleMappingExceptionResolverEx_ExceptionResolveDelegatorImplStub01.class;
        UTUtil.setPrivateField(simpleMappingExceptionResolverEx, "exceptionResolveDelegatorClass", cl);

        Map<String, String> map = new HashMap<String, String>();
        map.put("errorTypeHeaderName","errorType");
        UTUtil.setPrivateField(simpleMappingExceptionResolverEx, "exceptionResolveDelegatorParams", map);
        
        // テスト実施
        simpleMappingExceptionResolverEx.afterPropertiesSet();
        
        // 判定
        SimpleMappingExceptionResolverEx_ExceptionResolveDelegatorImplStub01 result = (SimpleMappingExceptionResolverEx_ExceptionResolveDelegatorImplStub01)exceptionResolveDelegatorMap.get("abc");
        assertEquals("abc", result.mappingKey);
        assertEquals("123", result.mappingValues);
        assertSame(map, result.paramsMap);
    }

    /**
     * testAfterPropertiesSet08()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A,D
     * <br><br>
     * 入力値：(状態) this.linkedExceptionMappings:LinkedHashMap {<br>
     *                  "abc"= "123",<br>
     *                  "def"= "456",<br>
     *                  "ghi"= "789"<br>
     *                }<br>
     *         (状態) this.exceptionResolveDelegatorClass:引数なしのコンストラクタを持つExceptionResolveDelegator実装クラス<br>
     *         (状態) this.exceptionResolveDelegatorMap:空のLinkedHashMap<br>
     *         (状態) this.exceptionResolveDelegatorParams:HashMap{<br>
     *                 "errorTypeHeaderName"="errorType"<br>
     *                }<br>
     *                         
     * <br>
     * 期待値：(状態変化) this.exceptionResolveDelegatorMap:LinkedHashMap{<br>
     *                      "abc"= ExceptionResolveDelegator実装クラス{<br>
     *                        mappingKey= "abc"<br>
     *                        mappingValues= "123"<br>
     *                        paramsMap=HashMap{<br>
     *                          "errorTypeHeaderName"="errorType"<br>
     *                        }<br>
     *                      }<br>
     *                      "abc"= ExceptionResolveDelegator実装クラス{<br>
     *                        mappingKey= "def"<br>
     *                        mappingValues= "456"<br>
     *                        paramsMap=HashMap{<br>
     *                          "errorTypeHeaderName"="errorType"<br>
     *                        }<br>                        
     *                      }<br>
     *                      "abc"= ExceptionResolveDelegator実装クラス{<br>
     *                        mappingKey= "ghi"<br>
     *                        mappingValues= "789"<br>
     *                        paramsMap=HashMap{<br>
     *                          "errorTypeHeaderName"="errorType"<br>
     *                        }<br>
     *                      }<br>
     *                    }<br>
     *         
     * <br>
     * this.linkedExceptionMappingsの要素が１つの場合のテスト。this.exceptionResolveDelegatorMapに要素が3つ追加されることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testAfterPropertiesSet08() throws Exception {
        // 前処理
        SimpleMappingExceptionResolverEx simpleMappingExceptionResolverEx = new SimpleMappingExceptionResolverEx();
        LinkedHashMap<String, Object> linkedExceptionMappings = new LinkedHashMap<String, Object>();
        linkedExceptionMappings.put("abc", "123");
        linkedExceptionMappings.put("def", "456");
        linkedExceptionMappings.put("ghi", "789");
        UTUtil.setPrivateField(simpleMappingExceptionResolverEx, "linkedExceptionMappings", linkedExceptionMappings);
        
        // exceptionResolveDelegatorMap
        LinkedHashMap<String, ExceptionResolveDelegator>exceptionResolveDelegatorMap = new LinkedHashMap<String, ExceptionResolveDelegator>();
        UTUtil.setPrivateField(simpleMappingExceptionResolverEx, "exceptionResolveDelegatorMap", exceptionResolveDelegatorMap);

        Class cl = SimpleMappingExceptionResolverEx_ExceptionResolveDelegatorImplStub01.class;
        UTUtil.setPrivateField(simpleMappingExceptionResolverEx, "exceptionResolveDelegatorClass", cl);
        
        Map<String, String> map = new HashMap<String, String>();
        map.put("errorTypeHeaderName","errorType");
        UTUtil.setPrivateField(simpleMappingExceptionResolverEx, "exceptionResolveDelegatorParams", map);
        
        // テスト実施
        simpleMappingExceptionResolverEx.afterPropertiesSet();
        
        // 判定
        SimpleMappingExceptionResolverEx_ExceptionResolveDelegatorImplStub01 result01 = (SimpleMappingExceptionResolverEx_ExceptionResolveDelegatorImplStub01)exceptionResolveDelegatorMap.get("abc");
        assertEquals("abc", result01.mappingKey);
        assertEquals("123", result01.mappingValues);
        assertSame(map, result01.paramsMap);

        SimpleMappingExceptionResolverEx_ExceptionResolveDelegatorImplStub01 result02 = (SimpleMappingExceptionResolverEx_ExceptionResolveDelegatorImplStub01)exceptionResolveDelegatorMap.get("def");
        assertEquals("def", result02.mappingKey);
        assertEquals("456", result02.mappingValues);
        assertSame(map, result02.paramsMap);

        SimpleMappingExceptionResolverEx_ExceptionResolveDelegatorImplStub01 result03 = (SimpleMappingExceptionResolverEx_ExceptionResolveDelegatorImplStub01)exceptionResolveDelegatorMap.get("ghi");
        assertEquals("ghi", result03.mappingKey);
        assertEquals("789", result03.mappingValues);
        assertSame(map, result03.paramsMap);

    }

    /**
     * testSetExceptionResolveDelegatorClass01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) exceptionResolveDelegatorClass:exceptionResolveDelegatorClassクラス<br>
     *         (状態) this.exceptionResolveDelegatorClass:null<br>
     *         
     * <br>
     * 期待値：(状態変化) this.exceptionResolveDelegatorClass:exceptionResolveDelegatorClassクラス<br>
     *         
     * <br>
     * 引数の値が正しく属性に設定されることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetExceptionResolveDelegatorClass01() throws Exception {
        // 前処理
        SimpleMappingExceptionResolverEx simpleMappingExceptionResolverEx = new SimpleMappingExceptionResolverEx();
        UTUtil.setPrivateField(simpleMappingExceptionResolverEx, "exceptionResolveDelegatorClass", null);

        // 引数
        Class<? extends ExceptionResolveDelegator> cl = jp.terasoluna.fw.web.rich.springmvc.servlet.handler.ExceptionResolveDelegatorImpl.class;
        
        // テスト実施
        simpleMappingExceptionResolverEx.setExceptionResolveDelegatorClass(cl);

        // 判定
        assertEquals(cl, UTUtil.getPrivateField(simpleMappingExceptionResolverEx, "exceptionResolveDelegatorClass"));

    }
    
    /**
     * testSetExceptionResolveDelegatorParams01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) exceptionResolveDelegatorParams:not null<br>
     *         (状態) this.exceptionResolveDelegatorParams:null<br>
     *         
     * <br>
     * 期待値：(状態変化) this.exceptionResolveDelegatorParams:引数で設定された値が設定されている。<br>
     *         
     * <br>
     * 引数の値が正しく属性に設定されることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetExceptionResolveDelegatorParams01() throws Exception {
        // 前処理
        SimpleMappingExceptionResolverEx simpleMappingExceptionResolverEx = new SimpleMappingExceptionResolverEx();
        UTUtil.setPrivateField(simpleMappingExceptionResolverEx, "exceptionResolveDelegatorParams", null);

        // 引数
        Map<String, String> map = new HashMap<String, String>();
        
        // テスト実施
        simpleMappingExceptionResolverEx.setExceptionResolveDelegatorParams(map);

        // 判定
        assertEquals(map, 
                UTUtil.getPrivateField(simpleMappingExceptionResolverEx, "exceptionResolveDelegatorParams"));

    }
    
    /**
     * testSetDefaultErrorView01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) defaultErrorView:null<br>
     *         (状態) super.setDefaultErrorView（）:
     *         superクラスのdefaultErrorView属性に
     *         引数defaultErrorViewを設定する。<br>
     *         (状態) logger.isInfoEnabled():true<br>
     *         
     * <br>
     * 期待値：(状態変化) this.defaultErrorView:null<br>
     *         (状態変化) ログ:Default error view is ' 
     *         + this.defaultErrorView + "'"<br>
     *         
     * <br>
     * defaultErrorView属性のsetterメソッドのテスト。
     * 引数がNullの場合、Nullをセットする。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetDefaultErrorView01() throws Exception {
        // 前処理
        SimpleMappingExceptionResolverEx exceptionResolver = 
            new SimpleMappingExceptionResolverEx();
        UTUtil.setPrivateField(exceptionResolver, "defaultErrorView", "");
        setField(SimpleMappingExceptionResolver.class, "defaultErrorView", 
                exceptionResolver, "");

        // テスト実施
        exceptionResolver.setDefaultErrorView(null);

        // 判定
        Object result = UTUtil.getPrivateField(
                exceptionResolver, "defaultErrorView");
        Object superResult = getField(
                SimpleMappingExceptionResolver.class, 
                "defaultErrorView", exceptionResolver);
        assertNull(result);
        assertNull(superResult);
        assertTrue(LogUTUtil.checkInfo("Default error view is 'null'"));
    }

    /**
     * testSetDefaultErrorView02()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) defaultErrorView:空文字<br>
     *         (状態) super.setDefaultErrorView（）:
     *         superクラスのdefaultErrorView属性に
     *         引数defaultErrorViewを設定する。<br>
     *         (状態) logger.isInfoEnabled():true<br>
     *         
     * <br>
     * 期待値：(状態変化) this.defaultErrorView:空文字<br>
     *         (状態変化) ログ:Default error view is ' 
     *         + this.defaultErrorView + "'"<br>
     *         
     * <br>
     * defaultErrorView属性のsetterメソッドのテスト。
     * 引数が空文字の場合、空文字　をセットする。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetDefaultErrorView02() throws Exception {
        // 前処理
        SimpleMappingExceptionResolverEx exceptionResolver = 
            new SimpleMappingExceptionResolverEx();
        UTUtil.setPrivateField(exceptionResolver, "defaultErrorView", null);
        setField(SimpleMappingExceptionResolver.class, "defaultErrorView", 
                exceptionResolver, null);

        // テスト実施
        String value = "";
        exceptionResolver.setDefaultErrorView(value);

        // 判定
        Object result = UTUtil.getPrivateField(
                exceptionResolver, "defaultErrorView");
        Object superResult = getField(
                SimpleMappingExceptionResolver.class, 
                "defaultErrorView", exceptionResolver);
        assertSame(value, result);
        assertSame(value, superResult);
        assertTrue(LogUTUtil.checkInfo("Default error view is ''"));
    }

    /**
     * testSetDefaultErrorView03()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) defaultErrorView:not null<br>
     *         (状態) super.setDefaultErrorView（）:
     *         superクラスのdefaultErrorView属性に
     *         引数defaultErrorViewを設定する。<br>
     *         (状態) logger.isInfoEnabled():true<br>
     *         
     * <br>
     * 期待値：(状態変化) this.defaultErrorView:not null<br>
     *         (状態変化) ログ:Default error view is ' 
     *         + this.defaultErrorView + "'"<br>
     *         
     * <br>
     * defaultErrorView属性のsetterメソッドのテスト。
     * 引数がNotNullの場合、引数をセットする。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetDefaultErrorView03() throws Exception {
        // 前処理
        SimpleMappingExceptionResolverEx exceptionResolver = 
            new SimpleMappingExceptionResolverEx();
        UTUtil.setPrivateField(exceptionResolver, "defaultErrorView", null);
        setField(SimpleMappingExceptionResolver.class, "defaultErrorView", 
                exceptionResolver, null);

        // テスト実施
        String value = "test";
        exceptionResolver.setDefaultErrorView(value);

        // 判定
        Object result = UTUtil.getPrivateField(
                exceptionResolver, "defaultErrorView");
        Object superResult = getField(
                SimpleMappingExceptionResolver.class, 
                "defaultErrorView", exceptionResolver);
        assertSame(value, result);
        assertSame(value, superResult);
        assertTrue(LogUTUtil.checkInfo("Default error view is 'test'"));
    }

    /**
     * testSetMappedHandlers01()
     * <br><br>
     * 
     * (正常系) 
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) mappedHandlers:null<br>
     *         (状態) super.setMappedHandlers（）:
     *         superクラスのmappedHandlers属性に引数mappedHandlersを設定する。<br>
     *         
     * <br>
     * 期待値：(状態変化) this.mappedHandlers:null<br>
     *         (状態変化) super.mappedHandlers:null<br>
     *         
     * <br>
     * mappedHandlers属性のsetメソッドのテスト。
     * NullのSetインスタンスを引数とする。指定した値がセットされている。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetMappedHandlers01() throws Exception {
        // 前処理
        SimpleMappingExceptionResolverEx exceptionResolver = 
            new SimpleMappingExceptionResolverEx();
        UTUtil.setPrivateField(
                exceptionResolver, "mappedHandlers", new HashSet());
        setField(SimpleMappingExceptionResolver.class, "mappedHandlers", 
                exceptionResolver, new HashSet());

        // テスト実施
        exceptionResolver.setMappedHandlers(null);

        // 判定
        Object result = UTUtil.getPrivateField(
                exceptionResolver, "mappedHandlers");
        Object superResult = getField(
                SimpleMappingExceptionResolver.class, 
                "mappedHandlers", exceptionResolver);
        assertNull(result);
        assertNull(superResult);
    }

    /**
     * testSetMappedHandlers02()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) mappedHandlers:not null<br>
     *                (要素３）<br>
     *         (状態) super.setMappedHandlers（）:
     *         superクラスのmappedHandlers属性に
     *         引数mappedHandlersを設定する。<br>
     *         
     * <br>
     * 期待値：(状態変化) this.mappedHandlers:not null<br>
     *                    (要素３）<br>
     *         (状態変化) super.mappedHandlers:not null<br>
     *                    (要素３）<br>
     *         
     * <br>
     * mappedHandlers属性のsetメソッドのテスト。
     * 要素が３つのSetインスタンスを引数とする。指定した値がセットされている。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetMappedHandlers02() throws Exception {
        // 前処理
        SimpleMappingExceptionResolverEx exceptionResolver = 
            new SimpleMappingExceptionResolverEx();
        UTUtil.setPrivateField(exceptionResolver, "mappedHandlers", null);
        setField(SimpleMappingExceptionResolver.class, "mappedHandlers", 
                exceptionResolver, null);
        Set<Object> set = new HashSet<Object>();
        Object obj1 = new Object();
        Object obj2 = new Object();
        Object obj3 = new Object();
        set.add(obj1);
        set.add(obj2);
        set.add(obj3);

        // テスト実施
        exceptionResolver.setMappedHandlers(set);

        // 判定
        Object result = UTUtil.getPrivateField(
                exceptionResolver, "mappedHandlers");
        Object superResult = getField(
                SimpleMappingExceptionResolver.class, 
                "mappedHandlers", exceptionResolver);
        assertSame(set, result);
        assertSame(set, superResult);
    }

    /**
     * testSetDefaultStatusCode01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) defaultStatusCode:not null<br>
     *                (２００）<br>
     *         (状態) super.setDefaultStatusCode(）:
     *         superクラスのdefaultStatusCode属性に
     *         引数defaultStatusCodeを設定する。<br>
     *         
     * <br>
     * 期待値：(状態変化) this.defaultStatusCode:not null<br>
     *                    (２００）<br>
     *         (状態変化) super.defaultStatusCode:not null<br>
     *                    (２００）<br>
     *         
     * <br>
     * defaultStatusCode属性のsetメソッドのテスト。
     * 数値２００のIntegerインスタンスを引数とする。
     * 指定した値がセットされている。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetDefaultStatusCode01() throws Exception {
        // 前処理
        SimpleMappingExceptionResolverEx exceptionResolver = 
            new SimpleMappingExceptionResolverEx();
        UTUtil.setPrivateField(exceptionResolver, "defaultStatusCode", 0);
        setField(SimpleMappingExceptionResolver.class, "defaultStatusCode", 
                exceptionResolver, 0);

        // テスト実施
        int value = 200;
        exceptionResolver.setDefaultStatusCode(value);
        
        // 判定
        Object result = UTUtil.getPrivateField(
                exceptionResolver, "defaultStatusCode");
        Object superResult = getField(
                SimpleMappingExceptionResolver.class, 
                "defaultStatusCode", exceptionResolver);
        assertEquals(value, result);
        assertEquals(value, superResult);
    }

    /**
     * testSetExceptionMappings01()
     * <br><br>
     * 
     * (正常系) 
     * <br>
     * 観点：G
     * <br><br>
     * 入力値：(引数) mappings:not null<br>
     *         
     * <br>
     * 期待値：(状態変化) 例外:UnsupportedOperationException<br>
     *         
     * <br>
     * サポートしていないメソッドのテスト。例外を返す。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    @SuppressWarnings("deprecation")
    public void testSetExceptionMappings01() throws Exception {
        // 前処理
        SimpleMappingExceptionResolverEx exceptionResolver = 
            new SimpleMappingExceptionResolverEx();

        // テスト実施
        try {
            exceptionResolver.setExceptionMappings(new Properties());
            fail();
        } catch (UnsupportedOperationException e) {
            // 結果確認
        	return;
        }
    }

    /**
     * testSetLinkedExceptionMappings01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) linkedExceptionMappings:not null<br>
     *         (状態) this.linkedExceptionMappings:null<br>
     *         
     * <br>
     * 期待値：(状態変化) this.linkedExceptionMappings:
     * 引数で設定された値が設定されている。<br>
     *         
     * <br>
     * linkedExceptionMappings属性のsetterメソッドのテスト。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetLinkedExceptionMappings01() throws Exception {
        // 前処理
        SimpleMappingExceptionResolverEx exceptionResolver = 
            new SimpleMappingExceptionResolverEx();
        UTUtil.setPrivateField(
                exceptionResolver, "linkedExceptionMappings", null);

        // テスト実施
        Map<String, Object> map = new HashMap<String, Object>();
        exceptionResolver.setLinkedExceptionMappings(map);
        
        // 判定
        Object result = UTUtil.getPrivateField(
                exceptionResolver, "linkedExceptionMappings");
        assertSame(map, result);
    }

    /**
     * testResolveException01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A,C
     * <br><br>
     * 入力値：(引数) request:使用しない<br>
     *         (引数) response:not null<br>
     *         (引数) handler:not null<br>
     *                （コントローラのインスタンス）<br>
     *         (引数) ex:Exceptionインスタンス<br>
     *         (状態) this.mappedHandlers:引数のhandlerと同じオブジェクトを要素に持たないSet<br>
     *         
     * <br>
     * 期待値：(戻り値) ModelAndView:null<br>
     *         
     * <br>
     * 本クラスで処理しないハンドラ（コントローラ）のテスト。Nullを返す。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testResolveException01() throws Exception {
        // 前処理
        SimpleMappingExceptionResolverEx exceptionResolver = 
            new SimpleMappingExceptionResolverEx();
        Set<Object> set = new HashSet<Object>();
        set.add(new SimpleMappingExceptionResolverEx_ControllerImplStub01());
        UTUtil.setPrivateField(
                exceptionResolver, "mappedHandlers", set);
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        // テスト実施
        Controller controller = 
            new SimpleMappingExceptionResolverEx_ControllerImplStub01();
        ModelAndView mv = exceptionResolver.resolveException(
                request, response, controller, new Exception());

        // 判定
        assertNull(mv);
    }

    /**
     * testResolveException02()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A,C
     * <br><br>
     * 入力値：(引数) request:使用しない<br>
     *         (引数) response:not null<br>
     *         (引数) handler:null<br>
     *         (引数) ex:Exceptionインスタンス<br>
     *         (状態) this.mappedHandlers:null<br>
     *         (状態) exceptionResolveDelegatorMap:要素が空のMap<br>
     *         (状態) this.defaultErrorView:null<br>
     *         (状態) this.defaultStatusCode:not null<br>
     *                (200)<br>
     *         
     * <br>
     * 期待値：(戻り値) ModelAndView:null<br>
     *         
     * <br>
     * ハンドリングする例外を記述していないパターンのテスト。Nullを返す。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testResolveException02() throws Exception {
        // 前処理
        SimpleMappingExceptionResolverEx exceptionResolver = 
            new SimpleMappingExceptionResolverEx();
        UTUtil.setPrivateField(exceptionResolver, "mappedHandlers", null);
        LinkedHashMap exceptionResolveDelegatorMap = new LinkedHashMap();
        UTUtil.setPrivateField(
                exceptionResolver, "exceptionResolveDelegatorMap", exceptionResolveDelegatorMap);
        String defaultErrorViewName = null;
        UTUtil.setPrivateField(exceptionResolver, "defaultErrorView", defaultErrorViewName);
        int statusCode = 200;
        UTUtil.setPrivateField(
                exceptionResolver, "defaultStatusCode", statusCode);
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        Exception exception = new Exception();

        // テスト実施
        ModelAndView mv = exceptionResolver.resolveException(
                request, response, null, exception);

        // 判定
        assertNull(mv);
        
    }

    /**
     * testResolveException03()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A,C
     * <br><br>
     * 入力値：(引数) request:使用しない<br>
     *         (引数) response:not null<br>
     *         (引数) handler:null<br>
     *         (引数) ex:Exceptionインスタンス<br>
     *         (状態) this.mappedHandlers:null<br>
     *         (状態) exceptionResolveDelegatorMap:要素が１つあるMap<br>
     *                Exception= ExceptionResolveDelegatorインスタンス{ viewName= null}<br>
     *         (状態) getDepth（）:「０」<br>
     *         (状態) this.defaultErrorView:"default"<br>
     *         (状態) this.defaultStatusCode:not null<br>
     *                (200)<br>
     *         (状態) getModelAndView():getModelAndView()メソッドが返すビュー<br>
     *         (状態) exceptionResolveDelegator.setHeader():呼び出されたことを確認する。<br>
     *         (状態) exceptionResolveDelegator.addObjectToModel():呼び出されたことを確認する。<br>
     *         
     * <br>
     * 期待値：(戻り値) ModelAndView:ModelAndView<br>
     *                  ・viewName：default<br>
     *                  ・model（Map型）<br>
     *                  exception=引数のex<br>
     *         (状態変化) response:ステータスコードが設定される。<br>
     *         
     * <br>
     * ハンドリングする例外を１つ記述しており、かつ例外に対応するExceptionResolveDelegatorのviewName属性にnullが設定されているパターンのテスト。デフォルトエラービューが設定されたModelAndViewを返す。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testResolveException03() throws Exception {
        // 前処理
        // Delegator生成
        SimpleMappingExceptionResolverEx_ExceptionResolveDelegatorImplStub01 exceptionResolveDelegatorImpl = new SimpleMappingExceptionResolverEx_ExceptionResolveDelegatorImplStub01();
        exceptionResolveDelegatorImpl.viewName = null;
        
        SimpleMappingExceptionResolverEx exceptionResolver = 
            new SimpleMappingExceptionResolverEx();
        UTUtil.setPrivateField(exceptionResolver, "mappedHandlers", null);
        LinkedHashMap<String, Object> exceptionResolveDelegatorMap = new LinkedHashMap<String, Object>();
        UTUtil.setPrivateField(
                exceptionResolver, "exceptionResolveDelegatorMap", exceptionResolveDelegatorMap);
        exceptionResolveDelegatorMap.put("java.lang.Exception",exceptionResolveDelegatorImpl);
        String defaultErrorViewName = "default";
        UTUtil.setPrivateField(exceptionResolver, "defaultErrorView", defaultErrorViewName);
        int statusCode = 200;
        UTUtil.setPrivateField(
                exceptionResolver, "defaultStatusCode", statusCode);
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        Exception exception = new Exception();
        
        // テスト実施
        ModelAndView mv = exceptionResolver.resolveException(
                request, response, null, exception);

        // 判定
        assertEquals(defaultErrorViewName, mv.getViewName());
        assertSame(exception, mv.getModel().get("exception"));
        
        // スタブの呼び出し確認
        assertTrue(exceptionResolveDelegatorImpl.isAddObjectToModel);
        assertTrue(exceptionResolveDelegatorImpl.isSetHeader);

        // ログ 確認
        assertTrue(LogUTUtil.checkError("Handled the following exception.",
                exception));
    }

    /**
     * testResolveException04()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A,D
     * <br><br>
     * 入力値：(引数) request:使用しない<br>
     *         (引数) response:not null<br>
     *         (引数) handler:not null<br>
     *                （コントローラのインスタンス）<br>
     *         (引数) ex:Exceptionインスタンス<br>
     *         (状態) this.mappedHandlers:引数のhandlerと同じオブジェクトを要素に持つSet<br>
     *         (状態) exceptionResolveDelegatorMap:要素が１つあるMap<br>
     *                Exception= ExceptionResolveDelegatorインスタンス{ viewName= "exception"}<br>
     *         (状態) getDepth（）:「０」<br>
     *         (状態) this.defaultErrorView:null<br>
     *         (状態) this.defaultStatusCode:not null<br>
     *                (200)<br>
     *         (状態) getModelAndView():getModelAndView()メソッドが返すビュー<br>
     *         (状態) exceptionResolveDelegator.setHeader():呼び出されたことを確認する。<br>
     *         (状態) exceptionResolveDelegator.addObjectToModel():呼び出されたことを確認する。<br>
     *         
     * <br>
     * 期待値：(戻り値) ModelAndView:ModelAndView<br>
     *                  ・viewName：exception<br>
     *                  ・model（Map型）<br>
     *                  exception=引数のex<br>
     *         (状態変化) response:ステータスコードが設定される。<br>
     *         
     * <br>
     * ハンドリングする例外を１つ記述しており、かつ例外に対応するExceptionResolveDelegatorのviewName属性に値が設定されているパターンのテスト。正常にModelAndViewを返す。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testResolveException04() throws Exception {
        // 前処理
        // Delegator生成
        SimpleMappingExceptionResolverEx_ExceptionResolveDelegatorImplStub01 exceptionResolveDelegatorImpl = new SimpleMappingExceptionResolverEx_ExceptionResolveDelegatorImplStub01();
        exceptionResolveDelegatorImpl.viewName = "exception";
        
        SimpleMappingExceptionResolverEx exceptionResolver = 
            new SimpleMappingExceptionResolverEx();
        UTUtil.setPrivateField(exceptionResolver, "mappedHandlers", null);
        LinkedHashMap<String, ExceptionResolveDelegator> exceptionResolveDelegatorMap = new LinkedHashMap<String, ExceptionResolveDelegator>();
        UTUtil.setPrivateField(
                exceptionResolver, "exceptionResolveDelegatorMap", exceptionResolveDelegatorMap);
        exceptionResolveDelegatorMap.put("java.lang.Exception",exceptionResolveDelegatorImpl);
        String defaultErrorViewName = null;
        UTUtil.setPrivateField(exceptionResolver, "defaultErrorView", defaultErrorViewName);
        int statusCode = 200;
        UTUtil.setPrivateField(
                exceptionResolver, "defaultStatusCode", statusCode);
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        Exception exception = new Exception();
        
        // テスト実施
        ModelAndView mv = exceptionResolver.resolveException(
                request, response, null, exception);

        // 判定
        assertEquals(exceptionResolveDelegatorImpl.viewName, mv.getViewName());
        assertSame(exception, mv.getModel().get("exception"));
        
        // スタブの呼び出し確認
        assertTrue(exceptionResolveDelegatorImpl.isAddObjectToModel);
        assertTrue(exceptionResolveDelegatorImpl.isSetHeader);

        // ログ 確認
        assertTrue(LogUTUtil.checkError("Handled the following exception.",
                exception));
    }

    /**
     * testResolveException05()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A,D
     * <br><br>
     * 入力値：(引数) request:使用しない<br>
     *         (引数) response:not null<br>
     *         (引数) handler:null<br>
     *         (引数) ex:BindExceptionインスタンス<br>
     *         (状態) this.mappedHandlers:null<br>
     *         (状態) exceptionResolveDelegatorMap:要素が３つあるMap<br>
     *                OXMappingException= ExceptionResolveDelegatorインスタンス{ viewName= "oxmException"},<br>
     *                BindException= ExceptionResolveDelegatorインスタンス{ viewName= "bindException"},<br>
     *                Exception= ExceptionResolveDelegatorインスタンス{ viewName= "exception"}<br>
     *         (状態) getDepth（）:「－１」<br>
     *                「０」<br>
     *         (状態) this.defaultErrorView:null<br>
     *         (状態) this.defaultStatusCode:not null<br>
     *                (200)<br>
     *         (状態) getModelAndView():getModelAndView()メソッドが返すビュー<br>
     *         (状態) exceptionResolveDelegator.setHeader():呼び出されたことを確認する。<br>
     *         (状態) exceptionResolveDelegator.addObjectToModel():呼び出されたことを確認する。<br>
     *         
     * <br>
     * 期待値：(戻り値) ModelAndView:ModelAndView<br>
     *                  ・viewName：bindException<br>
     *                  ・model（Map型）<br>
     *                  exception=引数のex<br>
     *         (状態変化) response:ステータスコードが設定される。<br>
     *         
     * <br>
     * ハンドリングする例外を３つ記述しているパターンのテスト。正常にModelAndViewを返す。発生した例外が記述した例外の２つ目以降で見つかるパターン。正常にModelAndViewを返す。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testResolveException05() throws Exception {
        // 前処理 --------------------------------------------------------------
        // Delegator生成
        SimpleMappingExceptionResolverEx_ExceptionResolveDelegatorImplStub01 exceptionResolveDelegatorImpl01 = new SimpleMappingExceptionResolverEx_ExceptionResolveDelegatorImplStub01();
        exceptionResolveDelegatorImpl01.viewName = "oxmException";
        SimpleMappingExceptionResolverEx_ExceptionResolveDelegatorImplStub01 exceptionResolveDelegatorImpl02 = new SimpleMappingExceptionResolverEx_ExceptionResolveDelegatorImplStub01();
        exceptionResolveDelegatorImpl02.viewName = "bindException";
        SimpleMappingExceptionResolverEx_ExceptionResolveDelegatorImplStub01 exceptionResolveDelegatorImpl03 = new SimpleMappingExceptionResolverEx_ExceptionResolveDelegatorImplStub01();
        exceptionResolveDelegatorImpl03.viewName = "exception";        
        
        SimpleMappingExceptionResolverEx exceptionResolver = 
            new SimpleMappingExceptionResolverEx();
        // 属性
        Controller controller =
            new SimpleMappingExceptionResolverEx_ControllerImplStub01(); 
        Set<Object> set = new HashSet<Object>();
        set.add(controller);
        UTUtil.setPrivateField(exceptionResolver, "mappedHandlers", set);
        
        LinkedHashMap<String, ExceptionResolveDelegator> exceptionResolveDelegatorMap = new LinkedHashMap<String, ExceptionResolveDelegator>();
        UTUtil.setPrivateField(
                exceptionResolver, "exceptionResolveDelegatorMap", exceptionResolveDelegatorMap);
        exceptionResolveDelegatorMap.put("jp.terasoluna.fw.oxm.exception.OXMappingException", exceptionResolveDelegatorImpl01);
        exceptionResolveDelegatorMap.put("org.springframework.validation.BindException", exceptionResolveDelegatorImpl02);
        exceptionResolveDelegatorMap.put("java.lang.Exception", exceptionResolveDelegatorImpl03);
        String defaultErrorViewName = null;
        UTUtil.setPrivateField(exceptionResolver, "defaultErrorView", defaultErrorViewName);

        int statusCode = 200;
        UTUtil.setPrivateField(
                exceptionResolver, "defaultStatusCode", statusCode);
        
        // メソッド引数 
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        Exception exception = new BindException(new Object(), "name");
        
        // テスト実施 ----------------------------------------------------------
        ModelAndView mv = exceptionResolver.resolveException(
                request, response, controller, exception);

        // 判定
        assertEquals(exceptionResolveDelegatorImpl02.viewName, mv.getViewName());
        assertSame(exception, mv.getModel().get("exception"));
        
        // スタブの呼び出し確認
        assertTrue(exceptionResolveDelegatorImpl02.isAddObjectToModel);
        assertTrue(exceptionResolveDelegatorImpl02.isSetHeader);

        // ログ 確認
        assertTrue(LogUTUtil.checkError("Handled the following exception.",
                exception));
    }

    /**
     * testResolveException06()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A,D
     * <br><br>
     * 入力値：(引数) request:使用しない<br>
     *         (引数) response:not null<br>
     *         (引数) handler:null<br>
     *         (引数) ex:BindExceptionインスタンス<br>
     *         (状態) this.mappedHandlers:null<br>
     *         (状態) exceptionResolveDelegatorMap:要素が１つあるMap<br>
     *                Exception= ExceptionResolveDelegatorインスタンス{ viewName= "exception"}<br>
     *         (状態) getDepth（）:「１」<br>
     *                （linkedExceptionMappingsで定義した例外の親クラスの例外をハンドリングする）<br>
     *         (状態) this.defaultErrorView:null<br>
     *         (状態) this.defaultStatusCode:not null<br>
     *                (200)<br>
     *         (状態) getModelAndView():getModelAndView()メソッドが返すビュー<br>
     *         (状態) exceptionResolveDelegator.setHeader():呼び出されたことを確認する。<br>
     *         (状態) exceptionResolveDelegator.addObjectToModel():呼び出されたことを確認する。<br>
     *         
     * <br>
     * 期待値：(戻り値) ModelAndView:ModelAndView<br>
     *                  ・viewName：exception<br>
     *                  ・model（Map型）<br>
     *                  exception=引数のex<br>
     *         (状態変化) response:ステータスコードが設定される。<br>
     *         
     * <br>
     * ハンドリングする例外が引数exの親クラスである場合のパターンのテスト。正常にModelAndViewを返す。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testResolveException06() throws Exception {
        // 前処理
        // Delegator生成
        SimpleMappingExceptionResolverEx_ExceptionResolveDelegatorImplStub01 exceptionResolveDelegatorImpl = new SimpleMappingExceptionResolverEx_ExceptionResolveDelegatorImplStub01();
        exceptionResolveDelegatorImpl.viewName = "exception";
        
        SimpleMappingExceptionResolverEx exceptionResolver = 
            new SimpleMappingExceptionResolverEx();
        UTUtil.setPrivateField(exceptionResolver, "mappedHandlers", null);
        LinkedHashMap<String, ExceptionResolveDelegator> exceptionResolveDelegatorMap = new LinkedHashMap<String, ExceptionResolveDelegator>();
        UTUtil.setPrivateField(
                exceptionResolver, "exceptionResolveDelegatorMap", exceptionResolveDelegatorMap);
        exceptionResolveDelegatorMap.put("java.lang.Exception",exceptionResolveDelegatorImpl);
        String defaultErrorViewName = null;
        UTUtil.setPrivateField(exceptionResolver, "defaultErrorView", defaultErrorViewName);
        int statusCode = 200;
        UTUtil.setPrivateField(
                exceptionResolver, "defaultStatusCode", statusCode);
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        Exception exception = new BindException(new Object(), "name");
        
        // テスト実施
        ModelAndView mv = exceptionResolver.resolveException(
                request, response, null, exception);

        // 判定
        assertEquals(exceptionResolveDelegatorImpl.viewName, mv.getViewName());
        assertSame(exception, mv.getModel().get("exception"));
        
        // スタブの呼び出し確認
        assertTrue(exceptionResolveDelegatorImpl.isAddObjectToModel);
        assertTrue(exceptionResolveDelegatorImpl.isSetHeader);

        // ログ 確認
        assertTrue(LogUTUtil.checkError("Handled the following exception.",
                exception));
    }

    /**
     * testResolveException07()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A,D
     * <br><br>
     * 入力値：(引数) request:使用しない<br>
     *         (引数) response:not null<br>
     *         (引数) handler:null<br>
     *         (引数) ex:BindExceptionインスタンス<br>
     *         (状態) this.mappedHandlers:null<br>
     *         (状態) exceptionResolveDelegatorMap:要素が１つあるMap<br>
     *                OXMappingException= ExceptionResolveDelegatorインスタンス{ viewName= "oxmException"}<br>
     *         (状態) getDepth（）:「－１」<br>
     *         (状態) this.defaultErrorView:null<br>
     *         (状態) this.defaultStatusCode:not null<br>
     *                (200)<br>
     *         
     * <br>
     * 期待値：(戻り値) ModelAndView:null<br>
     *         (状態変化) response:ステータスコードが設定される。<br>
     *         
     * <br>
     * ハンドリングする例外を１つ記述しているが、発生した例外と一致しないパターン。Nullを返す。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testResolveException07() throws Exception {
        // 前処理
        // Delegator生成
        SimpleMappingExceptionResolverEx_ExceptionResolveDelegatorImplStub01 exceptionResolveDelegatorImpl = new SimpleMappingExceptionResolverEx_ExceptionResolveDelegatorImplStub01();
        exceptionResolveDelegatorImpl.viewName = "oxmException";
        
        SimpleMappingExceptionResolverEx exceptionResolver = 
            new SimpleMappingExceptionResolverEx();
        UTUtil.setPrivateField(exceptionResolver, "mappedHandlers", null);
        LinkedHashMap<String, ExceptionResolveDelegator> exceptionResolveDelegatorMap = new LinkedHashMap<String, ExceptionResolveDelegator>();
        UTUtil.setPrivateField(
                exceptionResolver, "exceptionResolveDelegatorMap", exceptionResolveDelegatorMap);
        exceptionResolveDelegatorMap.put("jp.terasoluna.fw.oxm.exception.OXMappingException",exceptionResolveDelegatorImpl);
        String defaultErrorViewName = null;
        UTUtil.setPrivateField(exceptionResolver, "defaultErrorView", defaultErrorViewName);
        int statusCode = 200;
        UTUtil.setPrivateField(
                exceptionResolver, "defaultStatusCode", statusCode);
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        Exception exception = new BindException(new Object(), "name");
        
        // テスト実施
        ModelAndView mv = exceptionResolver.resolveException(
                request, response, null, exception);

        // 判定
        assertNull(mv);
    }
    
    /**
     * testResolveException03()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A,C
     * <br><br>
     * 入力値：(引数) request:使用しない<br>
     *         (引数) response:not null<br>
     *         (引数) handler:null<br>
     *         (引数) ex:Exceptionインスタンス<br>
     *         (状態) this.mappedHandlers:null<br>
     *         (状態) exceptionResolveDelegatorMap:要素が１つあるMap<br>
     *                Exception= ExceptionResolveDelegatorインスタンス{ viewName= null}<br>
     *         (状態) getDepth（）:「０」<br>
     *         (状態) this.defaultErrorView:"default"<br>
     *         (状態) this.defaultStatusCode:not null<br>
     *                (200)<br>
     *         (状態) getModelAndView():getModelAndView()メソッドが返すビュー<br>
     *         (状態) exceptionResolveDelegator.setHeader():呼び出されたことを確認する。<br>
     *         (状態) exceptionResolveDelegator.addObjectToModel():呼び出されたことを確認する。<br>
     *         (状態) ethis.outputErrorLogHandledException:false<br>
     *         
     * <br>
     * 期待値：(戻り値) ModelAndView:ModelAndView<br>
     *                  ・viewName：default<br>
     *                  ・model（Map型）<br>
     *                  exception=引数のex<br>
     *         (状態変化) response:ステータスコードが設定される。<br>
     *         
     * <br>
     * ハンドリングした例外の情報をエラーログ出力しない設定にした場合、
     * エラーログが出力されないことを確認するパターン。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testResolveException08() throws Exception {
        // 前処理
        // Delegator生成
        SimpleMappingExceptionResolverEx_ExceptionResolveDelegatorImplStub01 exceptionResolveDelegatorImpl = new SimpleMappingExceptionResolverEx_ExceptionResolveDelegatorImplStub01();
        exceptionResolveDelegatorImpl.viewName = null;
        
        SimpleMappingExceptionResolverEx exceptionResolver = 
            new SimpleMappingExceptionResolverEx();
        UTUtil.setPrivateField(exceptionResolver, "mappedHandlers", null);
        LinkedHashMap<String, Object> exceptionResolveDelegatorMap = new LinkedHashMap<String, Object>();
        UTUtil.setPrivateField(
                exceptionResolver, "exceptionResolveDelegatorMap", exceptionResolveDelegatorMap);
        exceptionResolveDelegatorMap.put("java.lang.Exception",exceptionResolveDelegatorImpl);
        String defaultErrorViewName = "default";
        UTUtil.setPrivateField(exceptionResolver, "defaultErrorView", defaultErrorViewName);
        int statusCode = 200;
        UTUtil.setPrivateField(
                exceptionResolver, "defaultStatusCode", statusCode);
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        Exception exception = new Exception();
        
        exceptionResolver.setOutputErrorLogHandledException(false);
        
        // テスト実施
        ModelAndView mv = exceptionResolver.resolveException(
                request, response, null, exception);

        // 判定
        assertEquals(defaultErrorViewName, mv.getViewName());
        assertSame(exception, mv.getModel().get("exception"));
        
        // スタブの呼び出し確認
        assertTrue(exceptionResolveDelegatorImpl.isAddObjectToModel);
        assertTrue(exceptionResolveDelegatorImpl.isSetHeader);

        // ログ 確認
        assertFalse(LogUTUtil.checkError("Handled the following exception.",
                exception));
    }

    /**
     * testResolveException09()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A,C
     * <br><br>
     * 入力値：(引数) request:使用しない<br>
     *         (引数) response:not null<br>
     *         (引数) handler:null<br>
     *         (引数) ex:Exceptionインスタンス<br>
     *         (状態) this.mappedHandlers:null<br>
     *         (状態) exceptionResolveDelegatorMap:要素が１つあるMap<br>
     *                Exception= ExceptionResolveDelegatorインスタンス{ viewName= null}<br>
     *         (状態) getDepth（）:「０」<br>
     *         (状態) this.defaultErrorView:null<br>
     *         (状態) this.defaultStatusCode:not null<br>
     *                (200)<br>
     *         
     * <br>
     * 期待値：(戻り値) ModelAndView:null<br>
     *         
     * <br>
     * ハンドリングする例外を１つ記述しており、かつ例外に対応するExceptionResolveDelegatorのviewName属性にnullが設定されているパターンのテスト。デフォルトエラービューが設定されていないのでNullを返す。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testResolveException09() throws Exception {
        // 前処理
        // Delegator生成
        SimpleMappingExceptionResolverEx_ExceptionResolveDelegatorImplStub01 exceptionResolveDelegatorImpl = new SimpleMappingExceptionResolverEx_ExceptionResolveDelegatorImplStub01();
        exceptionResolveDelegatorImpl.viewName = null;
        
        SimpleMappingExceptionResolverEx exceptionResolver = 
            new SimpleMappingExceptionResolverEx();
        UTUtil.setPrivateField(exceptionResolver, "mappedHandlers", null);
        LinkedHashMap<String, Object> exceptionResolveDelegatorMap = new LinkedHashMap<String, Object>();
        UTUtil.setPrivateField(
                exceptionResolver, "exceptionResolveDelegatorMap", exceptionResolveDelegatorMap);
        exceptionResolveDelegatorMap.put("java.lang.Exception",exceptionResolveDelegatorImpl);
        String defaultErrorViewName = null;
        UTUtil.setPrivateField(exceptionResolver, "defaultErrorView", defaultErrorViewName);
        int statusCode = 200;
        UTUtil.setPrivateField(
                exceptionResolver, "defaultStatusCode", statusCode);
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        Exception exception = new Exception();
        
        // テスト実施
        ModelAndView mv = exceptionResolver.resolveException(
                request, response, null, exception);

        // 判定
        assertNull(mv);
    }
    
    /**
     * リフレクションを使用してフィールドにアクセスするメソッド。
     * @param cls アクセスするクラス。
     * @param fieldName アクセスするフィールド名。
     * @param target アクセスするインスタンス。
     * @return フィールド値。
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    private Object getField(Class cls, String fieldName, Object target) 
            throws NoSuchFieldException, IllegalAccessException {
        Field field = cls.getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(target);
    }
    
    /**
     * リフレクションを使用してフィールドにアクセスするメソッド。
     * @param cls アクセスするクラス。
     * @param fieldName アクセスするフィールド名。
     * @param target アクセスするインスタンス。
     * @param value 設定する値。
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    private void setField(
            Class cls, String fieldName, Object target, Object value) 
            throws NoSuchFieldException, IllegalAccessException {
        Field field = cls.getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(target, value);
    }

}
