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

package jp.terasoluna.fw.oxm.xsd.xerces;

import java.io.BufferedReader;
import java.io.StringReader;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

import jp.terasoluna.utlib.LogUTUtil;
import jp.terasoluna.utlib.UTUtil;
import jp.terasoluna.fw.oxm.xsd.exception.IllegalSchemaDefinitionException;
import jp.terasoluna.fw.oxm.xsd.exception.UnknownXMLDataException;
import jp.terasoluna.fw.oxm.xsd.message.ErrorMessage;
import jp.terasoluna.fw.oxm.xsd.message.ErrorMessages;
import jp.terasoluna.fw.oxm.xsd.xerces.XMLErrorReporterEx;
import junit.framework.TestCase;

import org.apache.xerces.impl.xs.util.SimpleLocator;
import org.apache.xerces.util.DefaultErrorHandler;
import org.apache.xerces.xni.XMLLocator;

/**
 * {@link jp.terasoluna.fw.oxm.xsd.xerces.XMLErrorReporterEx} クラスのブラックボックステスト。
 * 
 * <p>
 * <h4>【クラスの概要】</h4>
 * XMLデータのパース時の詳細なエラー情報として、フィールド情報を扱うため、XMLErrorReporterの拡張を行ったクラス。<br>
 * ・前提条件<br>
 * reportErrorメソッドはパーサから呼び出されることが前提であり、引数location、domain、key、severityがnullになることはない。<br>
 * addErrorMessageメソッドはreportErrorメソッドから呼び出されることが前提であり、引数key、options、options[1]がnull、空文字になることはない。tagStackには必ず一つ以上の要素が格納されている。<br>
 * errorLogメソッドはreportErrorメソッドから呼び出されることが前提であり、引数key、optionsがnullになることはない、tagStackには必ず一つ以上の要素が格納されている。<br>
 * indexResolveメソッドは、XMLSchemaValidatorExのstartElementまたはendElementより呼び出されることが前提であり、引数elementがnullになることはない。<br>
 * getKeyメソッドのテストケースは、indexResolveメソッドのテストケースで網羅する。<br>
 * getMessageメソッドのテストケースは、errorLogメソッドで網羅する。
 * </p>
 * 
 * @see jp.terasoluna.fw.oxm.xsd.xerces.XMLErrorReporterEx
 */
public class XMLErrorReporterExTest extends TestCase {

    /**
     * このテストケースを実行する為の GUI アプリケーションを起動する。
     * 
     * @param args java コマンドに設定されたパラメータ
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(XMLErrorReporterExTest.class);
    }

    /**
     * XMLErrorReporterExインスタンス
     */
    private XMLErrorReporterEx errorReporter = null;

    /**
     * 初期化処理を行う。
     * 
     * @throws Exception このメソッドで発生した例外
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();

        // XMLErrorReporterExインスタンス
        ErrorMessages errorMessages = new ErrorMessages();
        this.errorReporter = new XMLErrorReporterEx(errorMessages);

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
    public XMLErrorReporterExTest(String name) {
        super(name);
    }

    /**
     * testXMLErrorReporterEx01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) errorMessages:not null<br>
     *         
     * <br>
     * 期待値：(状態変化) this.errorMessages:not null<br>
     *         
     * <br>
     * 引数のインスタンスが属性に設定されることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testXMLErrorReporterEx01() throws Exception {
        // 前処理
        ErrorMessages errorMessages = new ErrorMessages();

        // テスト実施
        XMLErrorReporterEx reporter = new XMLErrorReporterEx(errorMessages);

        // 判定
        assertSame(errorMessages, UTUtil.getPrivateField(reporter,
                "errorMessages"));
    }

    /**
     * testXMLErrorReporterEx02()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A,C
     * <br><br>
     * 入力値：(引数) errorMessages:null<br>
     *         
     * <br>
     * 期待値：(状態変化) this.errorMessages:null<br>
     *         
     * <br>
     * 引数のインスタンスが属性に設定されることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testXMLErrorReporterEx02() throws Exception {
        // 前処理
        ErrorMessages errorMessages = null;

        // テスト実施
        XMLErrorReporterEx reporter = new XMLErrorReporterEx(errorMessages);

        // 判定
        assertNull(UTUtil.getPrivateField(reporter, "errorMessages"));

    }

    /**
     * testGetErrorMessages01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(状態) this.errorMessages:not null<br>
     *         
     * <br>
     * 期待値：(戻り値) ErrorMessages:not null<br>
     *         
     * <br>
     * 属性の値が正しく返却されることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetErrorMessages01() throws Exception {
        // 前処理
        ErrorMessages errorMessages = new ErrorMessages();
        XMLErrorReporterEx reporter = new XMLErrorReporterEx(errorMessages);

        // テスト実施
        ErrorMessages result = reporter.getErrorMessages();

        // 判定
        assertSame(errorMessages, result);

    }

    /**
     * testReportError01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A,D
     * <br><br>
     * 入力値：(引数) location:not null<br>
     *         (引数) domain:not null<br>
     *         (引数) key:not null<br>
     *         (引数) arguments:空のオブジェクト<br>
     *         (引数) severity:not null<br>
     *         (状態) super.reportError():呼び出されたことを確認する。<br>
     *                引数が渡されたことを確認する。<br>
     *         (状態) errorLog():呼び出されたことを確認する。<br>
     *         (状態) addErrorMessage():呼び出されたことを確認する。<br>
     *                引数が渡されたことを確認する。<br>
     *         
     * <br>
     * 期待値：
     * <br>
     * argumentsが空の場合、super.reportErrorメソッド、errorLogメソッド、addErrorMessagesメソッドが呼び出されたことを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testReportError01() throws Exception {
        // 前処理
        ErrorMessages errorMessages = new ErrorMessages();
        XMLErrorReporterEx_XMLErrorReporterExStub01 reporter = new XMLErrorReporterEx_XMLErrorReporterExStub01(
                errorMessages);

        // super.reportErrorメソッドの呼び出し確認のための前提条件
        UTUtil.setPrivateField(reporter, "fErrorHandler", null);
        DefaultErrorHandler defaultErrorHandler = new DefaultErrorHandler();
        UTUtil.setPrivateField(reporter, "fDefaultErrorHandler",
                defaultErrorHandler);
        
        // 引数
        XMLLocator location = new SimpleLocator();
        String domain = "abc";
        String key = "def";
        Object[] arguments = new Object[] {};
        short severity = 123;

        // テスト実施
        reporter.reportError(location, domain, key, arguments, severity);

        // 判定
        // super.reportErrorメソッドの呼び出し確認
        assertSame(defaultErrorHandler, UTUtil
                .getPrivateField(reporter, "fDefaultErrorHandler"));

        // errorLogメソッドの呼び出し確認
        assertTrue(reporter.isErrorLog);

        // addErrorMessageメソッドに引数が渡されたことの確認
        assertEquals("def", reporter.key);
        assertEquals(0, reporter.options.length);

    }

    /**
     * testReportError02()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A,D
     * <br><br>
     * 入力値：(引数) location:not null<br>
     *         (引数) domain:not null<br>
     *         (引数) key:not null<br>
     *         (引数) arguments:Object[0]{"xyz"}<br>
     *         (引数) severity:not null<br>
     *         (状態) super.reportError():呼び出されたことを確認する。<br>
     *                引数が渡されたことを確認する。<br>
     *         (状態) errorLog():呼び出されたことを確認する。<br>
     *         (状態) addErrorMessage():呼び出されたことを確認する。<br>
     *                引数が渡されたことを確認する。<br>
     *         
     * <br>
     * 期待値：
     * <br>
     * argumentsの要素が１つの場合、super.reportErrorメソッド、errorLogメソッド、addErrorMessagesメソッドが呼び出されたことを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testReportError02() throws Exception {
        // 前処理
        ErrorMessages errorMessages = new ErrorMessages();
        XMLErrorReporterEx_XMLErrorReporterExStub01 reporter = new XMLErrorReporterEx_XMLErrorReporterExStub01(
                errorMessages);

        // super.reportErrorメソッドの呼び出し確認のための前提条件
        UTUtil.setPrivateField(reporter, "fErrorHandler", null);
        DefaultErrorHandler defaultErrorHandler = new DefaultErrorHandler();
        UTUtil.setPrivateField(reporter, "fDefaultErrorHandler",
                defaultErrorHandler);

        // 引数
        XMLLocator location = new SimpleLocator();
        String domain = "abc";
        String key = "def";
        Object[] arguments = new Object[] { "xyz" };
        short severity = 123;

        // テスト実施
        reporter.reportError(location, domain, key, arguments, severity);

        // 判定
        // super.reportErrorの呼び出し確認
        assertSame(defaultErrorHandler, UTUtil
                .getPrivateField(reporter, "fDefaultErrorHandler"));

        // errorLogメソッドの呼び出し確認
        assertTrue(reporter.isErrorLog);

        // addErrorMessageメソッドに引数が渡されたことの確認
        assertEquals("def", reporter.key);
        assertEquals(1, reporter.options.length);
        assertEquals("xyz", reporter.options[0]);


    }

    /**
     * testReportError03()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A,D
     * <br><br>
     * 入力値：(引数) location:not null<br>
     *         (引数) domain:not null<br>
     *         (引数) key:not null<br>
     *         (引数) arguments:Object[0]{"xyz"}<br>
     *                Object[1]{null}<br>
     *                Object[1]{456}<br>
     *         (引数) severity:not null<br>
     *         (状態) super.reportError():呼び出されたことを確認する。<br>
     *                引数が渡されたことを確認する。<br>
     *         (状態) errorLog():呼び出されたことを確認する。<br>
     *         (状態) addErrorMessage():呼び出されたことを確認する。<br>
     *                引数が渡されたことを確認する。<br>
     *         
     * <br>
     * 期待値：
     * <br>
     * argumentsの要素が３つの場合、super.reportErrorメソッド、errorLogメソッド、addErrorMessagesメソッドが呼び出されたことを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testReportError03() throws Exception {
        // 前処理
        ErrorMessages errorMessages = new ErrorMessages();
        XMLErrorReporterEx_XMLErrorReporterExStub01 reporter = new XMLErrorReporterEx_XMLErrorReporterExStub01(
                errorMessages);

        // super.reportErrorメソッドの呼び出し確認のための前提条件
        UTUtil.setPrivateField(reporter, "fErrorHandler", null);
        DefaultErrorHandler defaultErrorHandler = new DefaultErrorHandler();
        UTUtil.setPrivateField(reporter, "fDefaultErrorHandler",
                defaultErrorHandler);

        // 引数
        XMLLocator location = new SimpleLocator();
        String domain = "abc";
        String key = "def";
        Object[] arguments = new Object[] { "xyz", null, 456 };
        short severity = 123;

        // テスト実施
        reporter.reportError(location, domain, key, arguments, severity);

        // 判定
        // super.reportErrorの呼び出し確認        
        assertSame(defaultErrorHandler, UTUtil
                .getPrivateField(reporter, "fDefaultErrorHandler"));

        // errorLogメソッドの呼び出し確認
        assertTrue(reporter.isErrorLog);

        // addErrorMessageメソッドに引数が渡されたことの確認
        assertEquals("def", reporter.key);
        assertEquals(3, reporter.options.length);
        assertEquals("xyz", reporter.options[0]);
        assertNull(reporter.options[1]);
        assertEquals("456", reporter.options[2]);

    }


    /**
     * testReportError01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A,D
     * <br><br>
     * 入力値：(引数) location:not null<br>
     *         (引数) domain:not null<br>
     *         (引数) key:not null<br>
     *         (引数) arguments:null<br>
     *         (引数) severity:not null<br>
     *         (状態) super.reportError():呼び出されたことを確認する。<br>
     *                引数が渡されたことを確認する。<br>
     *         (状態) errorLog():呼び出されたことを確認する。<br>
     *         (状態) addErrorMessage():呼び出されたことを確認する。<br>
     *                引数が渡されたことを確認する。<br>
     *         
     * <br>
     * 期待値：
     * <br>
     * argumentsがnullの場合、super.reportErrorメソッド、errorLogメソッド、addErrorMessagesメソッドが呼び出されたことを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testReportError04() throws Exception {
        // 前処理
        ErrorMessages errorMessages = new ErrorMessages();
        XMLErrorReporterEx_XMLErrorReporterExStub01 reporter = new XMLErrorReporterEx_XMLErrorReporterExStub01(
                errorMessages);

        // super.reportErrorメソッドの呼び出し確認のための前提条件
        UTUtil.setPrivateField(reporter, "fErrorHandler", null);
        DefaultErrorHandler defaultErrorHandler = new DefaultErrorHandler();
        UTUtil.setPrivateField(reporter, "fDefaultErrorHandler",
                defaultErrorHandler);
        
        // 引数
        XMLLocator location = new SimpleLocator();
        String domain = "abc";
        String key = "def";
        Object[] arguments = null;
        short severity = 123;

        // テスト実施
        reporter.reportError(location, domain, key, arguments, severity);

        // 判定
        // super.reportErrorメソッドの呼び出し確認
        assertSame(defaultErrorHandler, UTUtil
                .getPrivateField(reporter, "fDefaultErrorHandler"));

        // errorLogメソッドの呼び出し確認
        assertTrue(reporter.isErrorLog);

        // addErrorMessageメソッドに引数が渡されたことの確認
        assertEquals("def", reporter.key);
        assertEquals(0, reporter.options.length);

    }
    
    /**
     * testIndexResolve01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) element:"abc"<br>
     *         (状態) this.tagIndex:空のHashMap<br>
     *         (状態) this.tagStack:空のStack<br>
     *         
     * <br>
     * 期待値：(戻り値) String:"abc[0]"<br>
     *         (状態変化) this.tagIndex:HashMap{<br>
     *                      "abc"=0<br>
     *                    }<br>
     *         
     * <br>
     * 引数とgetField()の戻り値を連結したキーがtagIndexに存在しない場合、<br>
     * 引数に0のインデックスを付加した文字列が返却されることを確認する。<br>
     * tagIndexにキーが引数、値が0のセットが格納されることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testIndexResolve01() throws Exception {
        // 前処理
        // 引数
        String element = "abc";

        // 前提条件
        HashMap<String, Integer> tagIndex = new HashMap<String, Integer>();
        UTUtil.setPrivateField(this.errorReporter, "tagIndex", tagIndex);
        Stack<String> stack = new Stack<String>();
        UTUtil.setPrivateField(this.errorReporter, "tagStack", stack);

        // テスト実施
        String result = this.errorReporter.indexResolve(element);

        // 判定
        // 戻り値
        assertEquals("abc[0]", result);
        
        // 状態変化
        HashMap map = (HashMap) UTUtil.getPrivateField(this.errorReporter,
                "tagIndex");
        assertEquals(1, map.size());
        assertEquals(0, map.get("abc"));

    }

    /**
     * testIndexResolve02()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A,C
     * <br><br>
     * 入力値：(引数) element:""<br>
     *         (状態) this.tagIndex:空のHashMap<br>
     *         (状態) this.tagStack:空のStack<br>
     *         
     * <br>
     * 期待値：(戻り値) String:"[0]"<br>
     *         (状態変化) this.tagIndex:HashMap{<br>
     *                      ""=0<br>
     *                    }<br>
     *         
     * <br>
     * 引数とgetField()の戻り値を連結したキーがtagIndexに存在しない場合、<br>
     * 引数に0のインデックスを付加した文字列が返却されることを確認する。<br>
     * tagIndexにキーが引数、値が0のセットが格納されることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testIndexResolve02() throws Exception {
        // 前処理
        // 引数
        String element = "";

        // 前提条件
        HashMap<String, Integer> tagIndex = new HashMap<String, Integer>();
        UTUtil.setPrivateField(this.errorReporter, "tagIndex", tagIndex);
        Stack<String> stack = new Stack<String>();
        UTUtil.setPrivateField(this.errorReporter, "tagStack", stack);

        // テスト実施
        String result = this.errorReporter.indexResolve(element);

        // 判定
        // 戻り値
        assertEquals("[0]", result);
        
        // 状態変化
        HashMap resultTagIndex = (HashMap) UTUtil.getPrivateField(this.errorReporter,
                "tagIndex");
        assertEquals(1, resultTagIndex.size());
        assertEquals(0, resultTagIndex.get(""));

    }

    /**
     * testIndexResolve03()
     * <br><br>
     * 
     * (正常系) 
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) element:"def"<br>
     *         (状態) this.tagIndex:HashMap{<br>
     *                  "abc"=0,<br>
     *                  "abc[0].def"=0<br>
     *                }<br>
     *         (状態) this.tagStack:Stack[0]{"abc[0]"}<br>
     *         
     * <br>
     * 期待値：(戻り値) String:"def[1]"<br>
     *         (状態変化) this.tagIndex:HashMap{<br>
     *                      "abc"=0,<br>
     *                      "abc[0].def"=1<br>
     *                    }<br>
     *         
     * <br>
     * 引数とgetField()の戻り値を連結したキーがtagIndexに存在する場合、<br>
     * 引数にインデックスを付加した文字列が返却されることを確認する。<br>
     * 戻り値のインデックスが該当キーの値をインクリメントした値であることを確認する。<br>
     * tagIndexの該当キーの値がインクリメントされることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testIndexResolve03() throws Exception {
        // 前処理
        // 引数
        String element = "def";

        // 前提条件
        HashMap<String, Integer> tagIndex = new HashMap<String, Integer>();
        tagIndex.put("abc", 0);
        tagIndex.put("abc[0].def", 0);
        UTUtil.setPrivateField(this.errorReporter, "tagIndex", tagIndex);
        Stack<String> stack = new Stack<String>();
        stack.push("abc[0]");
        UTUtil.setPrivateField(this.errorReporter, "tagStack", stack);

        // テスト実施
        String result = this.errorReporter.indexResolve(element);

        // 判定
        assertEquals("def[1]", result);
        HashMap map = (HashMap) UTUtil.getPrivateField(this.errorReporter,
                "tagIndex");
        assertEquals(2, map.size());
        assertEquals(0, map.get("abc"));
        assertEquals(1, map.get("abc[0].def"));

        HashMap resultTagIndex = (HashMap) UTUtil.getPrivateField(
                this.errorReporter, "tagIndex");
        assertEquals(2, resultTagIndex.size());
        assertEquals(0, resultTagIndex.get("abc"));
        assertEquals(1, resultTagIndex.get("abc[0].def"));

    }

    /**
     * testIndexResolve04()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) element:"ghi"<br>
     *         (状態) this.tagIndex:HashMap{<br>
     *                  "abc"=0,<br>
     *                  "abc[0].def"=0<br>
     *                  "abc[0].def[0].ghi"=1<br>
     *                }<br>
     *         (状態) this.tagStack:Stack[0]{"abc[0]"}<br>
     *                Stack[1]{"def[0]"}<br>
     *         
     * <br>
     * 期待値：(戻り値) String:"ghi[2]"<br>
     *         (状態変化) this.tagIndex:HashMap{<br>
     *                      "abc"=0,<br>
     *                      "abc[0].def"=0<br>
     *                      "abc[0].def[0].ghi"=2<br>
     *                    }<br>
     *         
     * <br>
     * 引数とgetField()の戻り値を連結したキーがtagIndexに存在する場合、<br>
     * 引数にインデックスを付加した文字列が返却されることを確認する。<br>
     * 戻り値のインデックスが該当キーの値をインクリメントした値であることを確認する。 <br>
     * tagIndexの該当キーの値がインクリメントされることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testIndexResolve04() throws Exception {
        // 前処理
        // 引数
        String element = "ghi";

        // 前提条件
        HashMap<String, Integer> tagIndex = new HashMap<String, Integer>();
        tagIndex.put("abc", 0);
        tagIndex.put("abc[0].def", 1);
        tagIndex.put("abc[0].def[0].ghi", 2);
        UTUtil.setPrivateField(this.errorReporter, "tagIndex", tagIndex);
        Stack<String> stack = new Stack<String>();
        stack.push("abc[0]");
        stack.push("def[0]");
        UTUtil.setPrivateField(this.errorReporter, "tagStack", stack);

        // テスト実施
        String result = this.errorReporter.indexResolve(element);

        // 判定
        assertEquals("ghi[3]", result);
        HashMap map = (HashMap) UTUtil.getPrivateField(this.errorReporter,
                "tagIndex");
        assertEquals(3, map.size());
        assertEquals(0, map.get("abc"));
        assertEquals(1, map.get("abc[0].def"));
        assertEquals(3, map.get("abc[0].def[0].ghi"));

        HashMap resultTagIndex = (HashMap) UTUtil.getPrivateField(
                this.errorReporter, "tagIndex");
        assertEquals(3, resultTagIndex.size());
        assertEquals(0, resultTagIndex.get("abc"));
        assertEquals(1, resultTagIndex.get("abc[0].def"));
        assertEquals(3, resultTagIndex.get("abc[0].def[0].ghi"));
    
    }

    /**
     * testAddErrorMessage01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) key:"cvc-type.3.1.3"<br>
     *         (引数) options:options[0]{"abc"}<br>
     *                options[1]{"def"}<br>
     *                options[2]{"ghi"}<br>
     *         (状態) this.errorMessages:空のErrorMessagesインスタンス<br>
     *         (状態) tmpErrorMessage:not null<br>
     *         (状態) this.tagStack:not null<br>
     *         (状態) this.DATATYPE_DATE:String[]{"date","time","dateTime"}<br>
     *         
     * <br>
     * 期待値：(状態変化) this.errorMessages:空のErrorMessagesインスタンス<br>
     *         
     * <br>
     * 引数keyが"cvc-type.3.1.3"の場合、何も行われないことを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testAddErrorMessage01() throws Exception {
        // 前処理
        // 引数
        String key = "cvc-type.3.1.3";
        String[] options = new String[] { "abc", "def", "ghi" };

        // 前提条件
        ErrorMessage tmpErrorMessage = new ErrorMessage("error", "abc[0]");
        UTUtil.setPrivateField(this.errorReporter, "tmpErrorMessage",
                tmpErrorMessage);

        Stack<String> stack = new Stack<String>();
        stack.push("abc[0]");
        UTUtil.setPrivateField(this.errorReporter, "tagStack", stack);

        // テスト実施
        this.errorReporter.addErrorMessage(key, options);

        // 判定
        ErrorMessages resultErrorMessages = 
            (ErrorMessages)UTUtil.getPrivateField(this.errorReporter, "errorMessages");
        assertFalse(resultErrorMessages.hasErrorMessage());

    }

    /**
     * testAddErrorMessage02()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A,C
     * <br><br>
     * 入力値：(引数) key:"cvc-attribute.3"<br>
     *         (引数) options:options[0]{"abc"}<br>
     *                options[1]{"def"}<br>
     *                options[2]{"ghi"}<br>
     *         (状態) this.errorMessages:空のErrorMessagesインスタンス<br>
     *         (状態) tmpErrorMessage:null<br>
     *         (状態) this.tagStack:not null<br>
     *         (状態) this.DATATYPE_DATE:String[]{"date","time","dateTime"}<br>
     *         
     * <br>
     * 期待値：(状態変化) this.errorMessages:空のErrorMessagesインスタンス<br>
     *         
     * <br>
     * 引数keyが"cvc-attribute.3"の場合、何も行われないことを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testAddErrorMessage02() throws Exception {
        // 前処理
        // 引数
        String key = "cvc-attribute.3";
        String[] options = new String[] { "abc", "def", "ghi" };

        // 前提条件
        ErrorMessage tmpErrorMessage = null;
        UTUtil.setPrivateField(this.errorReporter, "tmpErrorMessage",
                tmpErrorMessage);

        Stack<String> stack = new Stack<String>();
        stack.push("abc[0]");
        UTUtil.setPrivateField(this.errorReporter, "tagStack", stack);

        // テスト実施
        this.errorReporter.addErrorMessage(key, options);

        // 判定
        ErrorMessages resultErrorMessages = 
            (ErrorMessages)UTUtil.getPrivateField(this.errorReporter, "errorMessages");
        assertFalse(resultErrorMessages.hasErrorMessage());

    }

    /**
     * testAddErrorMessage03()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) key:"cvc-attribute.3"<br>
     *         (引数) options:options[0]{"abc"}<br>
     *                options[1]{"def"}<br>
     *                options[2]{"ghi"}<br>
     *         (状態) this.errorMessages:空のErrorMessagesインスタンス<br>
     *         (状態) tmpErrorMessage:ErrorMessageインスタンス{<br>
     *                  field= abc[0]<br>
     *                }<br>
     *         (状態) this.tagStack:not null<br>
     *         (状態) this.DATATYPE_DATE:String[]{"date","time","dateTime"}<br>
     *         
     * <br>
     * 期待値：(状態変化) this.errorMessages:空のErrorMessagesインスタンス<br>
     *         (状態変化) this.tmpErrorMessage:ErrorMessageインスタンス{<br>
     *                      field = "abc[0].def"<br>
     *                    }<br>
     *         
     * <br>
     * 引数keyが"cvc-attribute.3"の場合、何も行われないことを確認する。<br>
     * tmpErrorMessageインスタンスが生成されている場合、tmpErrorMessageのfield属性の末尾にoptions[1]の値が付加されることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testAddErrorMessage03() throws Exception {
        // 前処理
        // 引数
        String key = "cvc-attribute.3";
        String[] options = new String[] { "abc", "def", "ghi" };

        // 前提条件
        ErrorMessage tmpErrorMessage = new ErrorMessage("error", "abc[0]");
        UTUtil.setPrivateField(this.errorReporter, "tmpErrorMessage",
                tmpErrorMessage);

        Stack<String> stack = new Stack<String>();
        stack.push("abc[0]");
        UTUtil.setPrivateField(this.errorReporter, "tagStack", stack);

        // テスト実施
        this.errorReporter.addErrorMessage(key, options);

        // 判定
        // 何も行われていない（エラーが格納されていない）ことの確認
        ErrorMessages resultErrorMessages = 
            (ErrorMessages)UTUtil.getPrivateField(this.errorReporter, "errorMessages");
        assertFalse(resultErrorMessages.hasErrorMessage());

        // tmpErrorMessageの確認
        ErrorMessage resultTmpErrorMessage = (ErrorMessage) UTUtil
                .getPrivateField(this.errorReporter, "tmpErrorMessage");
        assertEquals("abc[0].def", resultTmpErrorMessage.getField());

    }

    /**
     * testAddErrorMessage04()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) key:"cvc-datatype-valid.1.2.1"<br>
     *         (引数) options:options[0]{"abc"}<br>
     *                options[1]{"boolean"}<br>
     *                options[2]{"ghi"}<br>
     *         (状態) this.errorMessages:空のErrorMessagesインスタンス<br>
     *         (状態) tmpErrorMessage:not null<br>
     *         (状態) this.tagStack:Stack[0]{"abc[0]"}<br>
     *         (状態) this.DATATYPE_DATE:String[]{"date","time","dateTime"}<br>
     *         
     * <br>
     * 期待値：(状態変化) this.errorMessages:ErrorMessageインスタンス{<br>
     *                      key = "typeMismatch.boolean"<br>
     *                      field = "abc[0]"<br>
     *                      replaceValues[0]{"abc"}<br>
     *                      replaceValues[1]{"boolean"}<br>
     *                      replaceValues[2]{"ghi"}<br>
     *                    }<br>
     *         
     * <br>
     * 引数keyが"cvc-datatype-valid.1.2.1"の場合、key属性に"typeMismatch.boolean"、replaceValues属性に引数optionsが格納されたErrorMessageインスタンスが生成されることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testAddErrorMessage04() throws Exception {
        // 前処理
        // 引数
        String key = "cvc-datatype-valid.1.2.1";
        String[] options = new String[] { "abc", "boolean", "ghi" };

        // 前提条件
        ErrorMessage tmpErrorMessage = new ErrorMessage("error", "abc[0]");
        UTUtil.setPrivateField(this.errorReporter, "tmpErrorMessage",
                tmpErrorMessage);

        Stack<String> stack = new Stack<String>();
        stack.push("abc[0]");
        UTUtil.setPrivateField(this.errorReporter, "tagStack", stack);

        // テスト実施
        this.errorReporter.addErrorMessage(key, options);

        // 判定
        // ErrorMessageが追加されていることの確認
        ErrorMessages resultErrorMessages = 
            (ErrorMessages)UTUtil.getPrivateField(this.errorReporter, "errorMessages");
        List<ErrorMessage> resultErrorMessageList = resultErrorMessages
                .getErrorMessages();
        assertEquals(1, resultErrorMessageList.size());
        ErrorMessage resultErrorMessage = resultErrorMessageList.get(0);
        assertEquals("typeMismatch.boolean", resultErrorMessage.getKey());
        assertEquals("abc[0]", resultErrorMessage.getField());
        assertEquals(3, resultErrorMessage.getReplaceValues().length);
        assertEquals("abc", resultErrorMessage.getReplaceValues()[0]);
        assertEquals("boolean", resultErrorMessage.getReplaceValues()[1]);
        assertEquals("ghi", resultErrorMessage.getReplaceValues()[2]);

    }

    /**
     * testAddErrorMessage05()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) key:"cvc-datatype-valid.1.2.1"<br>
     *         (引数) options:options[0]{"abc"}<br>
     *                options[1]{"dateTime"}<br>
     *                options[2]{"ghi"}<br>
     *         (状態) this.errorMessages:空のErrorMessagesインスタンス<br>
     *         (状態) tmpErrorMessage:not null<br>
     *         (状態) this.tagStack:Stack[0]{"abc[0]"}<br>
     *         (状態) this.DATATYPE_DATE:String[]{"date","time","dateTime"}<br>
     *         
     * <br>
     * 期待値：(状態変化) this.errorMessages:ErrorMessageインスタンス{<br>
     *                      key = "typeMismatch.date"<br>
     *                      field = "abc[0]"<br>
     *                      replaceValues[0]{"abc"}<br>
     *                      replaceValues[1]{"date"}<br>
     *                      replaceValues[2]{"ghi"}<br>
     *                    }<br>
     *         
     * <br>
     * 引数keyが"cvc-datatype-valid.1.2.1"の場合、key属性に"typeMismatch.date"、replaceValues属性に引数optionsが格納されたErrorMessageインスタンスが生成されることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testAddErrorMessage05() throws Exception {
        // 前処理
        // 引数
        String key = "cvc-datatype-valid.1.2.1";
        String[] options = new String[] { "abc", "dateTime", "ghi" };

        // 前提条件
        ErrorMessage tmpErrorMessage = new ErrorMessage("error", "abc[0]");
        UTUtil.setPrivateField(this.errorReporter, "tmpErrorMessage",
                tmpErrorMessage);

        Stack<String> stack = new Stack<String>();
        stack.push("abc[0]");
        UTUtil.setPrivateField(this.errorReporter, "tagStack", stack);

        // テスト実施
        this.errorReporter.addErrorMessage(key, options);

        // 判定
        // ErrorMessageが追加されていることの確認
        ErrorMessages resultErrorMessages = 
            (ErrorMessages)UTUtil.getPrivateField(this.errorReporter, "errorMessages");
        List<ErrorMessage> resultErrorMessageList = resultErrorMessages
                .getErrorMessages();
        assertEquals(1, resultErrorMessageList.size());
        ErrorMessage resultErrorMessage = resultErrorMessageList.get(0);
        assertEquals("typeMismatch.date", resultErrorMessage.getKey());
        assertEquals("abc[0]", resultErrorMessage.getField());
        assertEquals(3, resultErrorMessage.getReplaceValues().length);
        assertEquals("abc", resultErrorMessage.getReplaceValues()[0]);
        assertEquals("dateTime", resultErrorMessage.getReplaceValues()[1]);
        assertEquals("ghi", resultErrorMessage.getReplaceValues()[2]);

    }

    /**
     * testAddErrorMessage06()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) key:"cvc-datatype-valid.1.2.1"<br>
     *         (引数) options:options[0]{"abc"}<br>
     *                options[1]{"integer"}<br>
     *                options[2]{"ghi"}<br>
     *         (状態) this.errorMessages:空のErrorMessagesインスタンス<br>
     *         (状態) tmpErrorMessage:not null<br>
     *         (状態) this.tagStack:Stack[0]{"abc[0]"}<br>
     *         (状態) this.DATATYPE_DATE:String[]{"date","time","dateTime"}<br>
     *         
     * <br>
     * 期待値：(状態変化) this.errorMessages:ErrorMessageインスタンス{<br>
     *                      key = "typeMismatch.number"<br>
     *                      field = "abc[0]"<br>
     *                      replaceValues[0]{"abc"}<br>
     *                      replaceValues[1]{"integer"}<br>
     *                      replaceValues[2]{"ghi"}<br>
     *                    }<br>
     *         
     * <br>
     * 引数keyが"cvc-datatype-valid.1.2.1"の場合、key属性に"typeMismatch.integer"、replaceValues属性に引数optionsが格納されたErrorMessageインスタンスが生成されることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testAddErrorMessage06() throws Exception {
        // 前処理
        // 引数
        String key = "cvc-datatype-valid.1.2.1";
        String[] options = new String[] { "abc", "integer", "ghi" };

        // 前提条件
        ErrorMessage tmpErrorMessage = new ErrorMessage("error", "abc[0]");
        UTUtil.setPrivateField(this.errorReporter, "tmpErrorMessage",
                tmpErrorMessage);

        Stack<String> stack = new Stack<String>();
        stack.push("abc[0]");
        UTUtil.setPrivateField(this.errorReporter, "tagStack", stack);

        // テスト実施
        this.errorReporter.addErrorMessage(key, options);

        // 判定
        // ErrorMessageが追加されていることの確認
        ErrorMessages resultErrorMessages = 
            (ErrorMessages)UTUtil.getPrivateField(this.errorReporter, "errorMessages");
        List<ErrorMessage> resultErrorMessageList = resultErrorMessages
                .getErrorMessages();
        assertEquals(1, resultErrorMessageList.size());
        ErrorMessage resultErrorMessage = resultErrorMessageList.get(0);
        assertEquals("typeMismatch.number", resultErrorMessage.getKey());
        assertEquals("abc[0]", resultErrorMessage.getField());
        assertEquals(3, resultErrorMessage.getReplaceValues().length);
        assertEquals("abc", resultErrorMessage.getReplaceValues()[0]);
        assertEquals("integer", resultErrorMessage.getReplaceValues()[1]);
        assertEquals("ghi", resultErrorMessage.getReplaceValues()[2]);

    }

    /**
     * testAddErrorMessage07()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) key:"cvc-datatype-valid.1.2.3"<br>
     *         (引数) options:options[0]{"abc"}<br>
     *                options[1]{"integerAllowEmpty"}<br>
     *                options[2]{"ghi"}<br>
     *         (状態) this.errorMessages:空のErrorMessagesインスタンス<br>
     *         (状態) tmpErrorMessage:not null<br>
     *         (状態) this.tagStack:Stack[0]{"abc[0]"}<br>
     *         (状態) this.DATATYPE_DATE:String[]{"date","time","dateTime"}<br>
     *         
     * <br>
     * 期待値：(状態変化) this.errorMessages:ErrorMessageインスタンス{<br>
     *                      key = "typeMismatch.number"<br>
     *                      field = "abc[0]"<br>
     *                      replaceValues[0]{"abc"}<br>
     *                      replaceValues[1]{"integer"}<br>
     *                    }<br>
     *         
     * <br>
     * 引数keyが"cvc-datatype-valid.1.2.3"、options[1]が"integerAllowEmpty"の場合、key属性に"typeMismatch.integer"、replaceValues属性に引数optionsのインデックス2以降が削除された配列が格納された、ErrorMessageインスタンスが生成されることを確認する。<br>
     * ErrorMessagesのreplaceValues配列の個数は2つであることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testAddErrorMessage07() throws Exception {
        // 前処理
        // 引数
        String key = "cvc-datatype-valid.1.2.3";
        String[] options = new String[] { "abc", "integerAllowEmpty", "ghi" };

        // 前提条件
        ErrorMessage tmpErrorMessage = new ErrorMessage("error", "abc[0]");
        UTUtil.setPrivateField(this.errorReporter, "tmpErrorMessage",
                tmpErrorMessage);

        Stack<String> stack = new Stack<String>();
        stack.push("abc[0]");
        UTUtil.setPrivateField(this.errorReporter, "tagStack", stack);

        // テスト実施
        this.errorReporter.addErrorMessage(key, options);

        // 判定
        // ErrorMessageが追加されていることの確認
        ErrorMessages resultErrorMessages = 
            (ErrorMessages)UTUtil.getPrivateField(this.errorReporter, "errorMessages");
        List<ErrorMessage> resultErrorMessageList = resultErrorMessages
                .getErrorMessages();
        assertEquals(1, resultErrorMessageList.size());
        ErrorMessage resultErrorMessage = resultErrorMessageList.get(0);
        assertEquals("typeMismatch.number", resultErrorMessage.getKey());
        assertEquals("abc[0]", resultErrorMessage.getField());
        assertEquals(2, resultErrorMessage.getReplaceValues().length);
        assertEquals("abc", resultErrorMessage.getReplaceValues()[0]);
        assertEquals("integer", resultErrorMessage.getReplaceValues()[1]);

    }

    /**
     * testAddErrorMessage08()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) key:"cvc-maxInclusive-valid"<br>
     *         (引数) options:options[0]{"abc"}<br>
     *                options[1]{"integer"}<br>
     *                options[2]{"ghi"}<br>
     *         (状態) this.errorMessages:空のErrorMessagesインスタンス<br>
     *         (状態) tmpErrorMessage:not null<br>
     *         (状態) this.tagStack:Stack[0]{"abc[0]"}<br>
     *         (状態) this.DATATYPE_DATE:String[]{"date","time","dateTime"}<br>
     *         
     * <br>
     * 期待値：(状態変化) this.errorMessages:ErrorMessageインスタンス{<br>
     *                      key = "typeMismatch.numberMaxRange"<br>
     *                      field = "abc[0]"<br>
     *                      replaceValues[0]{"abc"}<br>
     *                      replaceValues[1]{"integer"}<br>
     *                      replaceValues[2]{"ghi"}<br>
     *                    }<br>
     *         
     * <br>
     * 引数keyが"cvc-maxInclusive-valid"の場合、key属性に"typeMismatch.numberMaxRange"が格納されたErrorMessageインスタンスが生成されることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testAddErrorMessage08() throws Exception {
        // 前処理
        // 引数
        String key = "cvc-maxInclusive-valid";
        String[] options = new String[] { "abc", "integer", "ghi" };

        // 前提条件
        ErrorMessage tmpErrorMessage = new ErrorMessage("error", "abc[0]");
        UTUtil.setPrivateField(this.errorReporter, "tmpErrorMessage",
                tmpErrorMessage);

        Stack<String> stack = new Stack<String>();
        stack.push("abc[0]");
        UTUtil.setPrivateField(this.errorReporter, "tagStack", stack);

        // テスト実施
        this.errorReporter.addErrorMessage(key, options);

        // 判定
        // ErrorMessageが追加されていることの確認
        ErrorMessages resultErrorMessages = 
            (ErrorMessages)UTUtil.getPrivateField(this.errorReporter, "errorMessages");
        List<ErrorMessage> resultErrorMessageList = resultErrorMessages
                .getErrorMessages();
        assertEquals(1, resultErrorMessageList.size());
        ErrorMessage resultErrorMessage = resultErrorMessageList.get(0);
        assertEquals("typeMismatch.numberMaxRange", resultErrorMessage.getKey());
        assertEquals("abc[0]", resultErrorMessage.getField());
        assertEquals(3, resultErrorMessage.getReplaceValues().length);
        assertEquals("abc", resultErrorMessage.getReplaceValues()[0]);
        assertEquals("integer", resultErrorMessage.getReplaceValues()[1]);
        assertEquals("ghi", resultErrorMessage.getReplaceValues()[2]);

    }

    /**
     * testAddErrorMessage09()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) key:"cvc-minInclusive-valid"<br>
     *         (引数) options:options[0]{"abc"}<br>
     *                options[1]{"integer"}<br>
     *                options[2]{"ghi"}<br>
     *         (状態) this.errorMessages:空のErrorMessagesインスタンス<br>
     *         (状態) tmpErrorMessage:not null<br>
     *         (状態) this.tagStack:Stack[0]{"abc[0]"}<br>
     *         (状態) this.DATATYPE_DATE:String[]{"date","time","dateTime"}<br>
     *         
     * <br>
     * 期待値：(状態変化) this.errorMessages:ErrorMessageインスタンス{<br>
     *                      key = "typeMismatch.numberMinRange"<br>
     *                      field = "abc[0]"<br>
     *                      replaceValues[0]{"abc"}<br>
     *                      replaceValues[1]{"integer"}<br>
     *                      replaceValues[2]{"ghi"}<br>
     *                    }<br>
     *         
     * <br>
     * 引数keyが"cvc-minInclusive-valid"の場合、key属性に"typeMismatch.numberMinRange"が格納されたErrorMessageインスタンスが生成されることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testAddErrorMessage09() throws Exception {
        // 前処理
        // 引数
        String key = "cvc-minInclusive-valid";
        String[] options = new String[] { "abc", "integer", "ghi" };

        // 前提条件
        ErrorMessage tmpErrorMessage = new ErrorMessage("error", "abc[0]");
        UTUtil.setPrivateField(this.errorReporter, "tmpErrorMessage",
                tmpErrorMessage);

        Stack<String> stack = new Stack<String>();
        stack.push("abc[0]");
        UTUtil.setPrivateField(this.errorReporter, "tagStack", stack);

        // テスト実施
        this.errorReporter.addErrorMessage(key, options);

        // 判定
        // ErrorMessageが追加されていることの確認
        ErrorMessages resultErrorMessages = 
            (ErrorMessages)UTUtil.getPrivateField(this.errorReporter, "errorMessages");
        List<ErrorMessage> resultErrorMessageList = resultErrorMessages
                .getErrorMessages();
        assertEquals(1, resultErrorMessageList.size());
        ErrorMessage resultErrorMessage = resultErrorMessageList.get(0);
        assertEquals("typeMismatch.numberMinRange", resultErrorMessage.getKey());
        assertEquals("abc[0]", resultErrorMessage.getField());
        assertEquals(3, resultErrorMessage.getReplaceValues().length);
        assertEquals("abc", resultErrorMessage.getReplaceValues()[0]);
        assertEquals("integer", resultErrorMessage.getReplaceValues()[1]);
        assertEquals("ghi", resultErrorMessage.getReplaceValues()[2]);

    }

    /**
     * testAddErrorMessage10()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A,G
     * <br><br>
     * 入力値：(引数) key:"cvc-abc"<br>
     *         (引数) options:not null<br>
     *         (状態) this.errorMessages:空のErrorMessagesインスタンス<br>
     *         (状態) tmpErrorMessage:not null<br>
     *         (状態) this.tagStack:not null<br>
     *         (状態) this.DATATYPE_DATE:String[]{"date","time","dateTime"}<br>
     *         
     * <br>
     * 期待値：(状態変化) 例外:UnknownXMLDataException<br>
     *         (状態変化) ログレベル:ログレベル:error<br>
     *                    xml data is invalid.<br>
     *         
     * <br>
     * 引数keyの文字列が"cvc-"から始まるが、下記の文字列でない場合、例外をスローし、ログを出力することを確認する。<br>
     * ・cvc-datatype-valid.1.2.1<br>
     * ・cvc-datatype-valid.1.2.3<br>
     * ・cvc-maxInclusive-valid<br>
     * ・cvc-minInclusive-valid
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testAddErrorMessage10() throws Exception {
        // 前処理
        // 引数
        String key = "cvc-abc";
        String[] options = new String[] { "abc", "integer", "ghi" };

        // 前提条件
        ErrorMessage tmpErrorMessage = new ErrorMessage("error", "abc[0]");
        UTUtil.setPrivateField(this.errorReporter, "tmpErrorMessage",
                tmpErrorMessage);

        Stack<String> stack = new Stack<String>();
        stack.push("abc[0]");
        UTUtil.setPrivateField(this.errorReporter, "tagStack", stack);

        // テスト実施
        try {
            this.errorReporter.addErrorMessage(key, options);
            fail();
        } catch (UnknownXMLDataException e) {
            // 判定
            // ログ確認
            String message = "xml data is invalid.";
            assertTrue(LogUTUtil.checkError(message));
        }
    }

    /**
     * testAddErrorMessage11()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A,G
     * <br><br>
     * 入力値：(引数) key:"abc"<br>
     *         (引数) options:not null<br>
     *         (状態) this.errorMessages:空のErrorMessagesインスタンス<br>
     *         (状態) tmpErrorMessage:not null<br>
     *         (状態) this.tagStack:not null<br>
     *         (状態) this.DATATYPE_DATE:String[]{"date","time","dateTime"}<br>
     *         
     * <br>
     * 期待値：(状態変化) 例外:IllegalSchemaDefinitionException<br>
     *         (状態変化) ログレベル:ログレベル:error<br>
     *                    schema is invalid.<br>
     *         
     * <br>
     * 引数keyの文字列がハンドリングするエラーコードに該当しない場合、例外をスローし、ログを出力することを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testAddErrorMessage11() throws Exception {
        // 前処理
        // 引数
        String key = "abc";
        String[] options = new String[] { "abc", "integer", "ghi" };

        // 前提条件
        ErrorMessage tmpErrorMessage = new ErrorMessage("error", "abc[0]");
        UTUtil.setPrivateField(this.errorReporter, "tmpErrorMessage",
                tmpErrorMessage);

        Stack<String> stack = new Stack<String>();
        stack.push("abc[0]");
        UTUtil.setPrivateField(this.errorReporter, "tagStack", stack);

        // テスト実施
        try {
            this.errorReporter.addErrorMessage(key, options);
            fail();
        } catch (IllegalSchemaDefinitionException e) {
            // 判定
            // ログ確認
            String message = "schema is invalid.";
            assertTrue(LogUTUtil.checkError(message));
        }
    }

    /**
     * testErrorLog01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A,D
     * <br><br>
     * 入力値：(引数) key:cvc-type.3.1.3<br>
     *         (引数) options:空のObject[]<br>
     *         (状態) XMLSchemaMessages.properties:XMLSchemaMessages.propertiesファイルが取得できる<br>
     *         (状態) this.tagStack:Stack[0]{"abc[0]"}<br>
     *         
     * <br>
     * 期待値：(状態変化) ログ:スキーマエラー[]------------------------<br>
     *                    xpath=abc[0]<br>
     *                    getMessage=cvc-type.3.1.3: The value '00a1' of element abc' is not valid.<br>
     *                    key=cvc-type.3.1.3<br>
     *                    -----------------------------------------<br>
     *         
     * <br>
     * 引数key,optionsと、前提条件tagStackの値が反映されたログが出力されることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testErrorLog01() throws Exception {
        // 前処理
        // 引数
        String key = "cvc-type.3.1.3";
        Object[] arg = new Object[] {};

        // 前提条件
        Stack<String> stack = new Stack<String>();
        String xpath = "abc[0]";
        stack.push(xpath);
        UTUtil.setPrivateField(this.errorReporter, "tagStack", stack);

        // テスト実施
        this.errorReporter.errorLog(key, arg);

        // 判定
        CheckLogObj resultLog = getErrorLog();
        assertNotNull(resultLog);        
        assertEquals(xpath, resultLog.xpath);
        String message = "cvc-type.3.1.3: The value '{1}' of element '{0}' is not valid.";
        assertEquals(message, resultLog.message);
        assertEquals(key, resultLog.key);
        
    }

    /**
     * testErrorLog02()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A,D
     * <br><br>
     * 入力値：(引数) key:cvc-type.3.1.3<br>
     *         (引数) options:Object[0]{"123"}<br>
     *         (状態) XMLSchemaMessages.properties:XMLSchemaMessages.propertiesファイルが取得できる<br>
     *         (状態) this.tagStack:Stack[0]{"abc[0]"}<br>
     *         
     * <br>
     * 期待値：(状態変化) ログ:スキーマエラー[]------------------------<br>
     *                    xpath=abc[0]<br>
     *                    getMessage=cvc-type.3.1.3: The value '123' of element '{1}' is not valid.<br>
     *                    key=cvc-type.3.1.3<br>
     *                    arg[0]=123<br>
     *                    -----------------------------------------<br>
     *         
     * <br>
     * 引数key,optionsと、前提条件tagStackの値が反映されたログが出力されることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testErrorLog02() throws Exception {
        // 前処理
        // 引数
        String key = "cvc-type.3.1.3";
        Object[] arg = new Object[] { "123" };

        // 前提条件
        Stack<String> stack = new Stack<String>();
        String xpath = "abc[0]";
        stack.push(xpath);
        UTUtil.setPrivateField(this.errorReporter, "tagStack", stack);

        // テスト実施
        this.errorReporter.errorLog(key, arg);

        // 判定
        CheckLogObj resultLog = getErrorLog();
        assertNotNull(resultLog);
        assertEquals(xpath, resultLog.xpath);
        String message = "cvc-type.3.1.3: The value '{1}' of element '123' is not valid.";
        assertEquals(message, resultLog.message);
        assertEquals(key, resultLog.key);
        assertEquals(arg[0], resultLog.arg0);

    }

    /**
     * testErrorLog03()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A,D
     * <br><br>
     * 入力値：(引数) key:cvc-type.3.1.3<br>
     *         (引数) options:Object[0]{"123"}<br>
     *                Object[1]{"456"}<br>
     *                Object[2]{"789"}<br>
     *         (状態) XMLSchemaMessages.properties:XMLSchemaMessages.propertiesファイルが取得できる<br>
     *         (状態) this.tagStack:Stack[0]{"abc[0]"}<br>
     *         
     * <br>
     * 期待値：(状態変化) ログ:スキーマエラー[]------------------------<br>
     *                    xpath=abc[0]<br>
     *                    getMessage=cvc-type.3.1.3: The value '123' of element '456' is not valid.<br>
     *                    key=cvc-type.3.1.3<br>
     *                    arg[0]=123<br>
     *                    arg[1]=456<br>
     *                    arg[1]=789<br>
     *                    -----------------------------------------<br>
     *         
     * <br>
     * 引数key,optionsと、前提条件tagStackの値が反映されたログが出力されることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testErrorLog03() throws Exception {
        // 前処理
        // 引数
        String key = "cvc-type.3.1.3";
        Object[] arg = new Object[] { "123", "456", "789" };

        // 前提条件
        Stack<String> stack = new Stack<String>();
        String xpath = "abc[0]";
        stack.push(xpath);
        UTUtil.setPrivateField(this.errorReporter, "tagStack", stack);

        // テスト実施
        this.errorReporter.errorLog(key, arg);

        // 判定
        CheckLogObj resultLog = getErrorLog();
        assertNotNull(resultLog);                
        assertEquals(xpath, resultLog.xpath);
        String message = "cvc-type.3.1.3: The value '456' of element '123' is not valid.";
        assertEquals(message, resultLog.message);
        assertEquals(key, resultLog.key);
        assertEquals(arg[0], resultLog.arg0);
        assertEquals(arg[1], resultLog.arg1);
        assertEquals(arg[2], resultLog.arg2);

    }

    /**
     * testErrorLog04()
     * <br><br>
     * 
     * (異常系)
     * <br>
     * 観点：A,C,G
     * <br><br>
     * 入力値：(引数) key:存在しないコード<br>
     *         (引数) options:Object[0]{null}<br>
     *         (状態) XMLSchemaMessages.properties:XMLSchemaMessages.propertiesファイルが取得できる<br>
     *         (状態) this.tagStack:Stack[0]{"abc[0]"}<br>
     *         
     * <br>
     * 期待値：(状態変化) ログ:スキーマエラー[]------------------------<br>
     *                    xpath=abc[0]<br>
     *                    getMessage=Can't find resource for bundle java.util.PropertyResourceBundle, key abc<br>
     *                    key=abc<br>
     *                    arg[0]=123<br>
     *                    arg[1]=456<br>
     *                    arg[2]=789<br>
     *                    -----------------------------------------<br>
     *         
     * <br>
     * 引数keyがプロパティに存在しない場合、ログにプロパティが見つからなかったことを示すメッセージが出力されることを確認する。<br>
     * 引数key,optionsと、前提条件tagStackの値が反映されたログが出力されることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testErrorLog04() throws Exception {
        // 前処理
        // 引数
        String key = "abc";
        Object[] arg = new Object[] {null};

        // 前提条件
        Stack<String> stack = new Stack<String>();
        String xpath = "abc[0]";
        stack.push(xpath);
        UTUtil.setPrivateField(this.errorReporter, "tagStack", stack);

        // テスト実施
        this.errorReporter.errorLog(key, arg);

        // 判定
        CheckLogObj resultLog = getErrorLog();
        assertNotNull(resultLog);    
        assertEquals(xpath, resultLog.xpath);
        String message = "[[Can't find resource for bundle java.util.PropertyResourceBundle, key abc]]";
        assertEquals(message, resultLog.message);
        assertEquals(key, resultLog.key);
        assertEquals("null", resultLog.arg0);

    }

    /**
     * testErrorLog05()
     * <br><br>
     * 
     * (異常系)
     * <br>
     * 観点：A,G
     * <br><br>
     * 入力値：(引数) key:not null<br>
     *         (引数) options:Object[0]{null}<br>
     *         (状態) XMLSchemaMessages.properties:XMLSchemaMessages.propertiesファイルが取得できない<br>
     *         (状態) this.tagStack:Stack[0]{"abc[0]"}<br>
     *         
     * <br>
     * 期待値：(状態変化) ログ:スキーマエラー[]------------------------<br>
     *                    xpath=abc[0]<br>
     *                    getMessage=Can't find bundle for base name XMLSchemaMessages, locale ja_JP<br>
     *                    key=cvc-type.3.1.3<br>
     *                    arg[0]=123<br>
     *                    arg[1]=456<br>
     *                    arg[2]=789<br>
     *                    -----------------------------------------<br>
     *         
     * <br>
     * プロパティファイルが取得できない場合、ログにリソースが取得できなかったことを示すメッセージが出力されることを確認する。<br>
     * 引数key,optionsと、前提条件tagStackの値が反映されたログが出力されることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testErrorLog05() throws Exception {
        
        // ＊＊＊　このテストケースは不要なため、削除する　＊＊＊
        // xercesで使用するプロパティファイルはxercesで提供しているjarファイルに
        // 同梱されているため、プロパティファイルが取得できないケースは存在しない。
        
        
//        // 前処理
//        // 引数
//        String key = "cvc-type.3.1.3";
//        Object[] arg = new Object[] { "123", "456", "789" };
//
//        // プロパティファイルのファイル名を変更する（プロパティファイルの取得を失敗させるため）
//        URL url = Thread.currentThread().getContextClassLoader().getResource(
//                "XMLSchemaMessages.properties");
//        File xercesProperties = new File(url.getFile());
//        File tmp = new File(url.getFile() + ".tmp");
//        boolean bol = xercesProperties.renameTo(tmp);
//
//        // ResourceBundleのキャッシュを削除する（プロパティファイルの取得を失敗させるため）。
//        ArrayList<Object> deleteCacheKeyList = new ArrayList<Object>();
//        SoftCache cache = (SoftCache) UTUtil.getPrivateField(
//                ResourceBundle.class, "cacheList");
//        for (Object cacheKey : cache.keySet()) {
//            if ("XMLSchemaMessages".equals((String) UTUtil.getPrivateField(
//                    cacheKey, "searchName"))
//                    || "XMLSchemaMessages_ja".equals((String) UTUtil
//                            .getPrivateField(cacheKey, "searchName"))
//                    || "XMLSchemaMessages_ja_JP".equals((String) UTUtil
//                            .getPrivateField(cacheKey, "searchName"))) {
//                deleteCacheKeyList.add(cacheKey);
//            }
//        }
//        for (Object deleteCacheKey : deleteCacheKeyList) {
//            cache.remove(deleteCacheKey);
//        }
//        
//        // 前提条件
//        String xpath = "abc[0]";
//        Stack<String> stack = new Stack<String>();
//        stack.push(xpath);
//        UTUtil.setPrivateField(this.errorReporter, "tagStack", stack);
//
//        // テスト実施
//        this.errorReporter.errorLog(key, arg);
//        
//        // 判定
//        CheckLogObj resultLog = getErrorLog();
//        assertNotNull(resultLog);    
//        assertEquals(xpath, resultLog.xpath);
//        String message = "[[Can't find bundle for base name XMLSchemaMessages, locale ja_JP]]";
//        assertEquals(message, resultLog.message);
//        assertEquals(key, resultLog.key);
//        assertEquals(arg[0], resultLog.arg0);
//        assertEquals(arg[1], resultLog.arg1);
//        assertEquals(arg[2], resultLog.arg2);
//
//        // プロパティファイルのファイル名を元に戻す
//        tmp.renameTo(xercesProperties);
    }

    /**
     * testErrorLog06()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A,D
     * <br><br>
     * 入力値：(引数) key:cvc-type.3.1.3<br>
     *         (引数) options:空のObject[]<br>
     *         (状態) XMLSchemaMessages.properties:XMLSchemaMessages.propertiesファイルが取得できる<br>
     *         (状態) this.tagStack:空のスタック<br>
     *         
     * <br>
     * 期待値：(状態変化) ログ:スキーマエラー[]------------------------<br>
     *                    xpath=<br>
     *                    getMessage=cvc-type.3.1.3: The value '00a1' of element abc' is not valid.<br>
     *                    key=cvc-type.3.1.3<br>
     *                    -----------------------------------------<br>
     *         
     * <br>
     * 引数key,optionsと、前提条件空のtagStackの値が反映されたログが出力されることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testErrorLog06() throws Exception {
        // 前処理
        // 引数
        String key = "cvc-type.3.1.3";
        Object[] arg = new Object[] {};

        // 前提条件
        Stack<String> stack = new Stack<String>();
        UTUtil.setPrivateField(this.errorReporter, "tagStack", stack);

        // テスト実施
        this.errorReporter.errorLog(key, arg);

        // 判定
        CheckLogObj resultLog = getErrorLog();
        assertNotNull(resultLog);        
        assertNull(resultLog.xpath);
        String message = "cvc-type.3.1.3: The value '{1}' of element '{0}' is not valid.";
        assertEquals(message, resultLog.message);
        assertEquals(key, resultLog.key);
        
    }

    /**
     * ログの中身をチェックするメソッド。
     * <br>
     * ログの形式が不正な場合、nulｌを返却する。
     * <br>
     */
    private CheckLogObj getErrorLog() throws Exception{
        //LogUTUtil.checkError("");
        List logObject = (List) UTUtil.getPrivateField(LogUTUtil.class, "__logObjects");
        Class me = this.getClass();
        String me2 = this.getName();
        
        String logStr = null;
        boolean flag = false ;
        for(Object obj : logObject){
            Method method = (Method)UTUtil.getPrivateField(obj, "_calledMethod");
            if(me.equals(method.getClass()) || me2.equals(method.getName())){
                logStr = (String)UTUtil.getPrivateField(obj, "_message");
                flag = true;
                break;
            }
        }
        if(!flag){
            return null;
        }
        
        StringReader reader = new StringReader(logStr);
        BufferedReader b = new BufferedReader(reader);
        
        CheckLogObj co = new CheckLogObj();        
        String st = null;

        while((st = b.readLine()) != null){
            String[] str = st.split("=");
            if(str.length == 1){
               co.otherStr.add(str[0]);
            }else if (str.length == 2){
                if ("xpath".equals(str[0])) {
                    co.xpath = str[1];
                }
                else if ("getMessage".equals(str[0])) {
                    co.message = str[1];
                }
                else if ("key".equals(str[0])) {
                    co.key = str[1];
                }
                else if ("arg[0]".equals(str[0])) {
                    co.arg0 = str[1];
                }
                else if ("arg[1]".equals(str[0])) {
                    co.arg1 = str[1];
                }
                else if ("arg[2]".equals(str[0])) {
                    co.arg2 = str[1];
                }
                else{
                    return null;
                }
            }else{
                return null;
            }
        }
        return co;
    }

    /**
     * ログを解析した結果を保持するオブジェクト
     *
     */
    private class CheckLogObj {
        ArrayList<String> otherStr = new ArrayList<String>();
        protected String xpath = null;
        protected String message = null;
        protected String key = null;
        protected String arg0 = null;
        protected String arg1 = null;
        protected String arg2 = null;
    }
    
    /**
     * testGetTagStack01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(状態) this.tagStack:not null<br>
     *         
     * <br>
     * 期待値：(戻り値) Stack:not null<br>
     *         
     * <br>
     * 属性の値が正しく返却されることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetTagStack01() throws Exception {
        // 前処理 
        Stack<String> tagStack = new Stack<String>();
        UTUtil.setPrivateField(this.errorReporter, "tagStack", tagStack);

        // テスト実施
        Stack result = this.errorReporter.getTagStack();

        // 判定
        assertSame(tagStack, result);

    }

}
