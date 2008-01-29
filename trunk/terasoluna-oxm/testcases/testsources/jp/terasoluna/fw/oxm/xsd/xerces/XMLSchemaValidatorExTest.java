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

import java.util.Stack;

import jp.terasoluna.utlib.LogUTUtil;
import jp.terasoluna.utlib.UTUtil;
import jp.terasoluna.fw.oxm.xsd.exception.ErrorReporterNotFoundException;
import jp.terasoluna.fw.oxm.xsd.message.ErrorMessages;
import jp.terasoluna.fw.oxm.xsd.xerces.XMLErrorReporterEx;
import jp.terasoluna.fw.oxm.xsd.xerces.XMLSchemaValidatorEx;
import junit.framework.TestCase;

import org.apache.xerces.util.AugmentationsImpl;
import org.apache.xerces.util.XMLAttributesImpl;
import org.apache.xerces.xni.Augmentations;
import org.apache.xerces.xni.QName;
import org.apache.xerces.xni.XMLAttributes;

/**
 * {@link jp.terasoluna.fw.oxm.xsd.xerces.XMLSchemaValidatorEx}
 * クラスのブラックボックステスト。
 * <p>
 * <h4>【クラスの概要】</h4>
 * 解析中のXMLの要素情報をXMLErrorReporterExのスタックに格納するために、XMLSchemaValidatorの拡張を行ったクラス。<br>
 * ・前提条件<br>
 * このクラスはパーサから呼びだされることが前提であり、startElement・endElementメソッドの引数element、attributes、augsにnullが格納されることはない。<br>
 * ・必ずstartElementの後にendElementが呼び出されるので、endElement実行時にXMLErrorReporterExのスタックが空になっている状態は存在しない。
 * <p>
 * 
 * @see jp.terasoluna.fw.oxm.xsd.xerces.XMLSchemaValidatorEx
 */
public class XMLSchemaValidatorExTest extends TestCase {

    /**
     * このテストケースを実行する為の GUI アプリケーションを起動する。
     * 
     * @param args java コマンドに設定されたパラメータ
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(XMLSchemaValidatorExTest.class);
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
    public XMLSchemaValidatorExTest(String name) {
        super(name);
    }

    /**
     * testXMLSchemaValidatorEx01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A,C
     * <br><br>
     * 入力値：(引数) reporeter:null<br>
     *         
     * <br>
     * 期待値：(状態変化) this.reporter:null<br>
     *         
     * <br>
     * 引数が正しく属性に設定されることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testXMLSchemaValidatorEx01() throws Exception {
        // 前処理
        XMLErrorReporterEx errorReporter = null;

        // テスト実施
        XMLSchemaValidatorEx schemaValidator = new XMLSchemaValidatorEx(
                errorReporter);

        // 判定
        assertNull(UTUtil.getPrivateField(schemaValidator,
                "reporter"));
    }

    /**
     * testXMLSchemaValidatorEx02()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) reporeter:not null<br>
     *         
     * <br>
     * 期待値：(状態変化) this.reporter:not null<br>
     *         
     * <br>
     * 引数が正しく属性に設定されることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testXMLSchemaValidatorEx02() throws Exception {
        // 前処理
        XMLErrorReporterEx errorReporter = new XMLErrorReporterEx(null);

        // テスト実施
        XMLSchemaValidatorEx schemaValidator = new XMLSchemaValidatorEx(
                errorReporter);

        // 判定
        assertSame(errorReporter, UTUtil.getPrivateField(schemaValidator,
                "reporter"));

    }

    /**
     * testStartElement01()
     * <br><br>
     * 
     * (異常系)
     * <br>
     * 観点：A,C,G
     * <br><br>
     * 入力値：(引数) reporter:null<br>
     *         
     * <br>
     * 期待値：(状態変化) 例外:ErrorReporterNotFoundException<br>
     *         (状態変化) ログ:ログレベル：error<br>
     *                    ErrorReporterEx is not found.<br>
     *         
     * <br>
     * 属性reporeterがnullの場合、例外がスローされることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testStartElement01() throws Exception {
        // 前処理
        XMLSchemaValidatorEx_XMLErrorReporterExStub01 errorReporter = null;
        XMLSchemaValidatorEx schemaValidator = new XMLSchemaValidatorEx(
                errorReporter);

        // 引数
        QName qname = new QName();
        XMLAttributes attributes = new XMLAttributesImpl();
        Augmentations augs = new AugmentationsImpl();

        // テスト実施
        try {
            schemaValidator.startElement(qname, attributes, augs);
            fail();
        } catch (ErrorReporterNotFoundException e) {
            String message = "ErrorReporterEx is not found.";

            // ログ確認
            assertTrue(LogUTUtil.checkError(message));
        }

    }

    /**
     * testStartElement02()
     * <br><br>
     * 
     * (正常系)
     * <br>A
     * <br><br>
     * 入力値：(引数) element:not null<br>
     *         (引数) attributes:not null<br>
     *         (引数) augs:not null<br>
     *         (引数) reporter:reporterインスタンス{<br>
     *                  空のtagStack<br>
     *                }<br>
     *         (状態) reporter.indexResolve():メソッドが呼び出されたことを確認する。<br>
     *                引数が渡されたことを確認する。<br>
     *                <br>
     *                "sample-dto[0]"を返却する<br>
     *         (状態) super.startElement():メソッドが呼び出されたことを確認する。<br>
     *                引数が渡されたことを確認する。<br>
     *         
     * <br>
     * 期待値：(状態変化) this.reporter:reporterインスタンス{<br>
     *                      tagStack[0]{"sample-dto[0]"}<br>
     *                    }<br>
     *         
     * <br>
     * reporter.indexResolve()メソッドで返却された文字列が、reporterインスタンスのスタックにプッシュされることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testStartElement02() throws Exception {
        // 前処理
        ErrorMessages errorMessages = new ErrorMessages();
        XMLSchemaValidatorEx_XMLErrorReporterExStub01 errorReporter = new XMLSchemaValidatorEx_XMLErrorReporterExStub01(
                errorMessages);
        XMLSchemaValidatorEx schemaValidator = new XMLSchemaValidatorEx(
                errorReporter);
        errorReporter.getTagStack().clear();

        // super.startElementメソッドの呼び出し確認で使用するスタブ
        XMLSchemaValidatorEx_DefaultXMLDocumentHandlerStub01 fDocumentHandler = new XMLSchemaValidatorEx_DefaultXMLDocumentHandlerStub01();
        UTUtil.setPrivateField(schemaValidator, "fDocumentHandler",
                fDocumentHandler);

        // 引数
        QName qname = new QName();
        qname.rawname = "abc";
        XMLAttributes attributes = new XMLAttributesImpl();
        Augmentations augs = new AugmentationsImpl();

        // テスト実施
        schemaValidator.startElement(qname, attributes, augs);

        // 判定
        // indexResolveメソッドに引数が渡されたことを確認する
        assertEquals("abc", errorReporter.element);

        // スタックにindexResolveの戻り値が格納されることを確認する
        Stack stack = errorReporter.getTagStack();
        assertEquals(1, stack.size());
        assertEquals("sample-dto[0]", stack.pop());

        // super.startElementメソッドの呼び出し確認
        assertTrue(fDocumentHandler.isStartElement);

    }

    /**
     * testEndElement01()
     * <br><br>
     * 
     * (異常系)
     * <br>
     * 観点：A,C,G
     * <br><br>
     * 入力値：(引数) element:not null<br>
     *         (引数) augs:not null<br>
     *         (状態) this.reporter:null<br>
     *         
     * <br>
     * 期待値：(状態変化) 例外:ErrorReporterNotFoundException<br>
     *         (状態変化) ログ:ログレベル：error<br>
     *                    ErrorReporterEx is not found.<br>
     *         
     * <br>
     * 属性reporeterがnullの場合、例外がスローされることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testEndElement01() throws Exception {
        // 前処理
        XMLSchemaValidatorEx_XMLErrorReporterExStub01 errorReporter = null;
        XMLSchemaValidatorEx schemaValidator = new XMLSchemaValidatorEx(
                errorReporter);

        // 引数
        QName qname = new QName();
        qname.rawname = "abc";
        Augmentations augs = new AugmentationsImpl();

        // テスト実施
        try {
            schemaValidator.endElement(qname, augs);
            fail();
        } catch (ErrorReporterNotFoundException e) {
            String message = "ErrorReporterEx is not found.";

            // ログ確認
            assertTrue(LogUTUtil.checkError(message));
        }

    }

    /**
     * testEndElement02()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A,C
     * <br><br>
     * 入力値：(引数) element:not null<br>
     *         (引数) augs:not null<br>
     *         (状態) this.reporter:reporterインスタンス{<br>
     *                  tagStack[]<br>
     *                    "sample-dto[0]"<br>
     *                  }<br>
     *                }<br>
     *         (状態) super.endElement():メソッドが呼び出されたことを確認する。<br>
     *                引数が渡されたことを確認する。<br>
     *         
     * <br>
     * 期待値：(状態変化) this.reporter:reporterインスタンス{<br>
     *                      空のtagStack<br>
     *                    }<br>
     *         
     * <br>
     * reporterインスタンスのスタックから、文字列がポップされることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testEndElement02() throws Exception {
        // 前処理
        ErrorMessages errorMessages = new ErrorMessages();
        XMLErrorReporterEx errorReporter = new XMLErrorReporterEx(errorMessages);
        XMLSchemaValidatorEx schemaValidator = new XMLSchemaValidatorEx(
                errorReporter);
        
        // super.endElementメソッドの呼び出し確認で使用するスタブ
        XMLSchemaValidatorEx_DefaultXMLDocumentHandlerStub01 fDocumentHandler = new XMLSchemaValidatorEx_DefaultXMLDocumentHandlerStub01();
        UTUtil.setPrivateField(schemaValidator, "fDocumentHandler",
                fDocumentHandler);
        
        // スタックに値を格納
        Stack<String> stack = new Stack<String>();
        stack.push("sample-dto[0]");
        UTUtil.setPrivateField(errorReporter, "tagStack", stack);
        
        // 引数
        QName qname = new QName();
        qname.rawname = "abc";
        Augmentations augs = new AugmentationsImpl();

        // テスト実施
        schemaValidator.endElement(qname, augs);
        
        // 判定
        // スタックが空になっていることを確認
        Stack result = errorReporter.getTagStack();
        assertEquals(0, result.size());
        
        // super.endElementメソッドの呼び出し確認
        assertTrue(fDocumentHandler.isEndElement);

    }

}
