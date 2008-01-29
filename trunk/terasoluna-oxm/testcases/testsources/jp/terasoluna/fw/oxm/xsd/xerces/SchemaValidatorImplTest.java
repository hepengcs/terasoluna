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

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

import jp.terasoluna.utlib.LogUTUtil;
import jp.terasoluna.utlib.UTUtil;
import jp.terasoluna.fw.oxm.xsd.exception.NamespaceNotFoundException;
import jp.terasoluna.fw.oxm.xsd.exception.NamespaceNotUniqueException;
import jp.terasoluna.fw.oxm.xsd.exception.ParserIOException;
import jp.terasoluna.fw.oxm.xsd.exception.ParserNotSupportedException;
import jp.terasoluna.fw.oxm.xsd.exception.ParserSAXException;
import jp.terasoluna.fw.oxm.xsd.exception.SchemaFileNotFoundException;
import jp.terasoluna.fw.oxm.xsd.message.ErrorMessage;
import jp.terasoluna.fw.oxm.xsd.message.ErrorMessages;
import jp.terasoluna.fw.oxm.xsd.xerces.SchemaValidatorImpl;
import jp.terasoluna.fw.oxm.xsd.xerces.XMLErrorReporterEx;
import junit.framework.TestCase;

import org.apache.xerces.impl.Constants;
import org.apache.xerces.parsers.DOMParser;
import org.apache.xerces.parsers.XML11Configuration;
import org.apache.xerces.xni.parser.XMLParserConfiguration;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;

/**
 * {@link jp.terasoluna.fw.oxm.xsd.xerces.SchemaValidatorImpl} クラスのブラックボックステスト。
 * 
 * <p>
 * <h4>【クラスの概要】</h4>
 * XMLデータの形式チェックを行うSchemaValidator実装クラス。<br>
 * ・前提条件<br>
 * setCommonParserPropertyメソッドの引数errorMessagesがnullになることはない。<br>
 * setCommonParserPropertyメソッドの引数parserがnullになることはない。<br>
 * setCommonParserFeatureメソッドの引数parserがnullになることはない。<br>
 * <p>
 * 
 * @see jp.terasoluna.fw.oxm.xsd.xerces.SchemaValidatorImpl
 */
public class SchemaValidatorImplTest extends TestCase {

    /**
     * setUpメソッドにより、毎回生成されるインスタンス
     */
    private SchemaValidatorImpl setUpValidator = null;
    
    /**
     * このテストケースを実行する為の
     * GUI アプリケーションを起動する。
     * 
     * @param args java コマンドに設定されたパラメータ
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(SchemaValidatorImplTest.class);
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
        this.setUpValidator = new SchemaValidatorImpl();
        
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
    public SchemaValidatorImplTest(String name) {
        super(name);
    }

    /**
     * testSetCache01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) cache:true<br>
     *         (状態) this.cache:false<br>
     *         
     * <br>
     * 期待値：(状態変化) this.cache:true<br>
     *         
     * <br>
     * 引数が正しく属性に設定されることを確認にする。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetCache01() throws Exception {
        // 前処理
        setUpValidator.cache = false;

        // テスト実施
        setUpValidator.setCache(true);
        
        // 判定
        assertTrue(setUpValidator.cache);        

    }

    /**
     * testSetNamespace01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) namespaces:true<br>
     *         (状態) this.namespaces:false<br>
     *         
     * <br>
     * 期待値：(状態変化) this.namespaces:true<br>
     *         
     * <br>
     * 引数が正しく属性に設定されることを確認にする。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetNamespace01() throws Exception {
        // 前処理
        setUpValidator.namespace = false;
        
        // テスト実施
        setUpValidator.setNamespace(true);
        
        // 判定
        assertTrue(setUpValidator.namespace);        

    }

    /**
     * testSetNamespaceProperties01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) namespaceProperties:not nul<br>
     *         
     * <br>
     * 期待値：(状態変化) this.namespaceproperties:not null<br>
     *         
     * <br>
     * 引数が正しく属性に設定されることを確認にする。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetNamespaceProperties01() throws Exception {
        // 前処理
        setUpValidator.namespaceProperties = null;
        Properties prop = new Properties();
        
        // テスト実施
        setUpValidator.setNamespaceProperties(prop);
        
        // 判定
        assertSame(prop, setUpValidator.namespaceProperties);        

    }

    /**
     * testSetNamespacePropertyFileName01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) namespacePropertyFileName:"abc"<br>
     *         (状態) this.namespacePropertyFileName:null<br>
     *         
     * <br>
     * 期待値：(状態変化) this.namespacePropertyFileName:"abc"<br>
     *         
     * <br>
     * 引数objectがnullの場合、例外がスローされることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetNamespacePropertyFileName01() throws Exception {
        // 前処理
        setUpValidator.namespacePropertyFileName = null;
        
        // テスト実施
        setUpValidator.setNamespacePropertyFileName("abc");
        
        // 判定
        assertEquals("abc", setUpValidator.namespacePropertyFileName);        

    }

    /**
     * testInitNamespaceProperties01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(状態) this.namespaceCheck:true<br>
     *         (状態) loadNamespaceProperties():呼び出されたことを確認する<br>
     *         (状態) checkNamespaceProperties():呼び出されたことを確認する<br>
     *         
     * <br>
     * 期待値：
     * <br>
     * this.namespaceCheckがtrueの場合、各メソッドが実行されることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testInitNamespaceProperties01() throws Exception {
        // 前処理
        SchemaValidatorImpl_SchemaValidatorImplStub04 schemaValidator = new SchemaValidatorImpl_SchemaValidatorImplStub04();
        schemaValidator.setNamespaceCheck(true);

        // テスト実施
        schemaValidator.initNamespaceProperties();

        // 判定
        assertTrue(schemaValidator.isLoadNamespaceProperties);
        assertTrue(schemaValidator.isCheckNamespaceProperties);
    }

    /**
     * testInitNamespaceProperties02()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(状態) this.namespaceCheck:false<br>
     *         (状態) loadNamespaceProperties():呼び出されたことを確認する<br>
     *         (状態) checkNamespaceProperties():呼び出されなかったことを確認する<br>
     *         
     * <br>
     * 期待値：
     * <br>
     * this.namespaceCheckがfalseの場合、checkNamespacePropertiesメソッドが実行されないことを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testInitNamespaceProperties02() throws Exception {
        // 前処理
        SchemaValidatorImpl_SchemaValidatorImplStub04 schemaValidator = new SchemaValidatorImpl_SchemaValidatorImplStub04();
        schemaValidator.setNamespaceCheck(false);
        
        // テスト実施
        schemaValidator.initNamespaceProperties();

        // 判定
        assertTrue(schemaValidator.isLoadNamespaceProperties);
        assertFalse(schemaValidator.isCheckNamespaceProperties);
    }

    /**
     * testLoadNamespaceProperties01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A,D
     * <br><br>
     * 入力値：(状態) this.namespacePropertyFileName:"test_message_01_en_US.properties"<br>
     *         (状態) プロパティファイル:以下のプロパティが存在すること<br>
     *                test_message_01_en_US.properties<br>
     *                　SystemExceptionHandlerTest.key={0}message<br>
     *         
     * <br>
     * 期待値：(状態変化) this.namespaceProperties:test_message_01_en_US.propertiesの値をもつPropertiesオブジェクト<br>
     *         
     * <br>
     * プロパティオブジェクトの中身が１つの時、指定されたプロパティファイルがロードされていることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testLoadNamespaceProperties01() throws Exception {
        // 前処理
        setUpValidator.namespacePropertyFileName = "test_message_01_en_US.properties";
        
        // テスト対象の実行
        setUpValidator.initNamespaceProperties();

        // 結果確認
        Properties result = setUpValidator.namespaceProperties;
        
        assertTrue(result.containsKey("SystemExceptionHandlerTest.key"));
        assertTrue(result.containsValue("{0}message"));
    }

    /**
     * testLoadNamespaceProperties02()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A,D
     * <br><br>
     * 入力値：(状態) this.namespacePropertyFileName:"test_message_01.properties"<br>
     *         (状態) プロパティファイル:以下のプロパティが存在すること<br>
     *                test_message_01.properties<br>
     *                　SystemExceptionHandlerTest.error.message=例外メッセージ<br>
     *                　SystemExceptionHandlerTest.error.message.null=<br>
     *                　SystemExceptionHandlerTest.key={0}デフォルトメッセージ<br>
     *         
     * <br>
     * 期待値：(状態変化) this.namespaceProperties:test_message_01.propertiesの値をもつPropertiesオブジェクト<br>
     *         
     * <br>
     * 指定されたプロパティファイルの中身の個数分ロードされていることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testLoadNamespaceProperties02() throws Exception {
        // 前処理
        setUpValidator.namespacePropertyFileName = "test_message_01.properties";
        
        // テスト対象の実行
        setUpValidator.initNamespaceProperties();

        // 結果確認
        Properties result = setUpValidator.namespaceProperties;

        assertEquals("\u4f8b\u5916\u30e1\u30c3\u30bb\u30fc\u30b8", result.getProperty("SystemExceptionHandlerTest.error.message"));
        assertEquals("", result.getProperty("SystemExceptionHandlerTest.error.message.null"));
        assertEquals("{0}\u30c7\u30d5\u30a9\u30eb\u30c8\u30e1\u30c3\u30bb\u30fc\u30b8", result.getProperty("SystemExceptionHandlerTest.key"));
        
    }

    /**
     * testLoadNamespaceProperties03()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A,C
     * <br><br>
     * 入力値：(状態) this.namespacePropertyFileName:null<br>
     *         
     * <br>
     * 期待値：(状態変化) this.namespaceProperties:null<br>
     *         
     * <br>
     * プロパティファイル名が存在しない場合、処理を終了することを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testLoadNamespaceProperties03() throws Exception {
        // 前処理
        setUpValidator.namespacePropertyFileName = null;
        
        // テスト対象の実行
        setUpValidator.initNamespaceProperties();

        // 結果確認
        assertNull(setUpValidator.namespaceProperties);

    }

    /**
     * testLoadNamespaceProperties04()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A,C
     * <br><br>
     * 入力値：(状態) this.namespacePropertyFileName:""<br>
     *         
     * <br>
     * 期待値：(状態変化) this.namespaceProperties:null<br>
     *         
     * <br>
     * プロパティファイル名が存在しない場合、処理を終了することを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testLoadNamespaceProperties04() throws Exception {
        // 前処理
        setUpValidator.namespacePropertyFileName = "";
        
        // テスト対象の実行
        setUpValidator.initNamespaceProperties();

        // 結果確認
        assertNull(setUpValidator.namespaceProperties);

    }

    /**
     * testLoadNamespaceProperties05()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A,D
     * <br><br>
     * 入力値：(状態) this.namespacePropertyFileName:"test_message_10.properties"<br>
     *         (状態) プロパティファイル:以下のプロパティが存在すること<br>
     *                test_message_10.properties（中身なし）<br>
     *         
     * <br>
     * 期待値：(状態変化) this.namespaceProperties:空のPropertiesオブジェクト<br>
     *         
     * <br>
     * 指定されたプロパティファイルの中身が空の時、空のプロパティオブジェクトが取り出されていることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testLoadNamespaceProperties05() throws Exception {
        // 前処理
        setUpValidator.namespacePropertyFileName = "test_message_10.properties";
        
        // テスト対象の実行
        setUpValidator.initNamespaceProperties();

        // 結果確認
        Properties result = setUpValidator.namespaceProperties;
        assertTrue(result.isEmpty());
    }

    /**
     * testLoadNamespaceProperties06()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A,C
     * <br><br>
     * 入力値：(状態) this.namespacePropertyFileName:"test_me.properties"<br>
     *         (状態) プロパティファイル:test_me.propertiesが存在しないこと<br>
     *         
     * <br>
     * 期待値：(状態変化) this.namespaceProperties:null<br>
     *         
     * <br>
     * 存在しないファイル名が指定された時、nullを戻り値として処理を終了することを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testLoadNamespaceProperties06() throws Exception {
        // 前処理
        setUpValidator.namespacePropertyFileName = "test_me.properties";
        
        // テスト対象の実行
        setUpValidator.initNamespaceProperties();

        // 結果確認
        Properties result = setUpValidator.namespaceProperties;
        LogUTUtil.checkWarn("*** Can not find property-file" +
                " [test_me.properties] ***");
        assertNull(result);
    }

    /**
     * testCheckNamespaceProperties01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A,C
     * <br><br>
     * 入力値：(状態) namespaceProperties:null<br>
     *         
     * <br>
     * 期待値：
     * <br>
     * namespacePropertiesがnullの場合、何も行われないことを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testCheckNamespaceProperties01() throws Exception {
        // 前処理
        setUpValidator.setNamespaceProperties(null);
        
        // テスト実施
        setUpValidator.checkNamespaceProperties();

    }

    /**
     * testCheckNamespaceProperties02()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A,D
     * <br><br>
     * 入力値：(状態) namespaceProperties:空のProperties<br>
     *         
     * <br>
     * 期待値：
     * <br>
     * namespacePropertiesが空の場合、何も行われないことを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testCheckNamespaceProperties02() throws Exception {
        // 前処理
        Properties properties = new Properties();
        setUpValidator.setNamespaceProperties(properties);
        
        // テスト実施
        setUpValidator.checkNamespaceProperties();

    }

    /**
     * testCheckNamespaceProperties03()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A,D
     * <br><br>
     * 入力値：(状態) namespaceProperties:Properties{<br>
     *                  "abc", "123"<br>
     *                }<br>
     *         
     * <br>
     * 期待値：
     * <br>
     * プロパティ名が重複していない場合、何も行われないことを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testCheckNamespaceProperties03() throws Exception {
        // 前処理
        Properties properties = new Properties();
        properties.put("abc", "123");
        setUpValidator.setNamespaceProperties(properties);
        
        // テスト実施
        setUpValidator.checkNamespaceProperties();

    }

    /**
     * testCheckNamespaceProperties04()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A,D
     * <br><br>
     * 入力値：(状態) namespaceProperties:Properties{<br>
     *                  "abc", "123"<br>
     *                  "def", "456"<br>
     *                  "ghi", "789"<br>
     *                }<br>
     *         
     * <br>
     * 期待値：
     * <br>
     * プロパティ名が重複していない場合、何も行われないことを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testCheckNamespaceProperties04() throws Exception {
        // 前処理
        Properties properties = new Properties();
        properties.put("abc", "123");
        properties.put("def", "456");
        properties.put("ghi", "789");
        setUpValidator.setNamespaceProperties(properties);
        
        // テスト実施
        setUpValidator.checkNamespaceProperties();

    }

    /**
     * testCheckNamespaceProperties05()
     * <br><br>
     * 
     * (異常系)
     * <br>
     * 観点：A,D,G
     * <br><br>
     * 入力値：(状態) namespaceProperties:Properties{<br>
     *                  "abc", "123"<br>
     *                  "def", "456"<br>
     *                  "ghi", "456"<br>
     *                }<br>
     *         
     * <br>
     * 期待値：(状態変化) 例外:PropertyNotUniqueException<br>
     *         (状態変化) ログ:ログレベル：ERROR<br>
     *                    Namespace name [456] is not unique. Namespace must be unique. (key = [ghi])<br>
     *         
     * <br>
     * プロパティ名が重複している場合、例外がスローされることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testCheckNamespaceProperties05() throws Exception {
        // 前処理
        Properties properties = new Properties();
        properties.put("abc", "123");
        properties.put("def", "456");
        properties.put("ghi", "456");
        setUpValidator.setNamespaceProperties(properties);
        
        // テスト実施
        try {
            setUpValidator.checkNamespaceProperties();
            fail();
        }catch (NamespaceNotUniqueException e) {
            // 判定
            String message = "Namespace name [456] is not unique. Namespace must be unique. (key = [ghi])";

            // ログ確認
            assertTrue(LogUTUtil.checkError(message));
        }

    }

    /**
     * testValidate01()
     * <br><br>
     * 
     * (異常系)
     * <br>
     * 観点：A,C,G
     * <br><br>
     * 入力値：(引数) in:null<br>
     *         (引数) object:not null<br>
     *         (引数) errorMessages:not null<br>
     *         (状態) this.namespace:false<br>
     *         
     * <br>
     * 期待値：(状態変化) 例外:IllegalArgumentException{<br>
     *                      message = "InputStream is null."<br>
     *                    }<br>
     *         (状態変化) ログ:ログレベル:error<br>
     *                    InputStream is null.<br>
     *         
     * <br>
     * 引数inがnullの場合、例外がスローされ、ログ出力が行われることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testValidate01() throws Exception {

        // テスト実施
        try{
            setUpValidator.validate(null, new Object(), new ErrorMessages());
            fail();
        }catch (IllegalArgumentException e) {
            // 判定
            String message = "InputStream is null.";
            assertSame(message, e.getMessage());

            // ログ確認
            assertTrue(LogUTUtil.checkError(message));
        }
        
    }

    /**
     * testValidate02()
     * <br><br>
     * 
     * (異常系)
     * <br>
     * 観点：A,C,G
     * <br><br>
     * 入力値：(引数) in:not null<br>
     *         (引数) object:not null<br>
     *         (引数) errorMessages:null<br>
     *         (状態) this.namespace:false<br>
     *         
     * <br>
     * 期待値：(状態変化) 例外:IllegalArgumentException{<br>
     *                      message = "ErrorMessages is null."<br>
     *                    }<br>
     *         (状態変化) ログ:ログレベル：error<br>
     *                    ErrorMessages is null.<br>
     *         
     * <br>
     * 引数errorMessagesがnullの場合、例外がスローされ、ログが出力されることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testValidate02() throws Exception {
        // 前処理
        ByteArrayInputStream inputStream = new ByteArrayInputStream(
                new byte[] {});
        
        // テスト実施
        try{
            setUpValidator.validate(inputStream, new Object(), null);
            fail();
        }catch (IllegalArgumentException e) {
            // 判定
            String message = "ErrorMessages is null.";
            assertEquals(message, e.getMessage());

            // ログ確認
            assertTrue(LogUTUtil.checkError(message));
        }
        
    }

    /**
     * testValidate03()
     * <br><br>
     * 
     * (異常系)
     * <br>
     * 観点：A,G
     * <br><br>
     * 入力値：(引数) in:not null<br>
     *         (引数) object:not null<br>
     *         (引数) errorMessages:not null<br>
     *         (状態) getUrl():呼び出したことを確認する。<br>
     *         (状態) createNonNamespaceDomParser():SAXNotRecognizedExceptionをスローする。<br>
     *         (状態) this.namespace:false<br>
     *         
     * <br>
     * 期待値：(状態変化) 例外:ParserNotSupportedException｛<br>
     *                      cause=  SAXNotRecognizedException<br>
     *                    }<br>
     *         (状態変化) ログ:ログレベル：error<br>
     *                    Schema property error.<br>
     *         
     * <br>
     * DOMParse生成時に、SAXNotRecognizedExceptionがスローされた場合、SAXNotRecognizedExceptionをラップしたParserNotSupportedExceptionがスローされ、ログが出力されることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testValidate03() throws Exception {
        // 前処理
        ByteArrayInputStream inputStream = new ByteArrayInputStream(
                new byte[] {});
        SchemaValidatorImpl_SchemaValidatorImplStub02 validator 
        	= new SchemaValidatorImpl_SchemaValidatorImplStub02();
        
        // テスト実施
        try{
            validator.validate(inputStream, new Object(), new ErrorMessages());
            fail();
        }catch (ParserNotSupportedException e) {
            // 判定
            String message = "Schema property error.";
            assertSame(SAXNotRecognizedException.class, e.getCause().getClass());

            // ログ確認
            assertTrue(LogUTUtil.checkError(message, e.getCause()));
        }
    }

    /**
     * testValidate04()
     * <br><br>
     * 
     * (異常系)
     * <br>
     * 観点：A,G
     * <br><br>
     * 入力値：(引数) in:not null<br>
     *         (引数) object:not null<br>
     *         (引数) errorMessages:not null<br>
     *         (状態) getUrl():呼び出したことを確認する。<br>
     *         (状態) createNonNamespaceDomParser():SAXNotSupportedExceptionをスローする。<br>
     *         (状態) this.namespace:false<br>
     *         
     * <br>
     * 期待値：(状態変化) 例外:ParserNotSupportedException｛<br>
     *                      cause= SAXNotSupportedException<br>
     *                    }<br>
     *         (状態変化) ログ:ログレベル：error<br>
     *                    Schema property error.<br>
     *         
     * <br>
     * DOMParse生成時に、SAXNotSupportedExceptionがスローされた場合、SAXNotSupportedExceptionをラップしたParserNotSupportedExceptionがスローされ、ログが出力されることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testValidate04() throws Exception {
        // 前処理
        ByteArrayInputStream inputStream = new ByteArrayInputStream(
                new byte[] {});
        SchemaValidatorImpl_SchemaValidatorImplStub03 validator 
        	= new SchemaValidatorImpl_SchemaValidatorImplStub03();
        
        // テスト実施
        try{
            validator.validate(inputStream, new Object(), new ErrorMessages());
            fail();
        }catch (ParserNotSupportedException e) {
            // 判定
            String message = "Schema property error.";
            assertSame(SAXNotSupportedException.class, e.getCause().getClass());

            // ログ確認
            assertTrue(LogUTUtil.checkError(message, e.getCause()));
        }
    }

    /**
     * testValidate05()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A,D
     * <br><br>
     * 入力値：(引数) in:not null<br>
     *         (引数) object:null<br>
     *         (引数) errorMessages:not null<br>
     *         (状態) getUrl():呼び出したことを確認する。<br>
     *         (状態) createNonNamespaceDomParser():呼び出したことを確認する。<br>
     *                <br>
     *                errorMessagesにエラーを詰める<br>
     *         (状態) parser.perse():呼び出したことを確認する。<br>
     *                引数が渡されたことを確認する。<br>
     *         (状態) this.namespace:false<br>
     *         
     * <br>
     * 期待値：(戻り値) Document:null<br>
     *         
     * <br>
     * 形式チェックエラーが発生した場合、Nullが返却されることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testValidate05() throws Exception {
        
        // 前処理
        ByteArrayInputStream inputStream = new ByteArrayInputStream(
                new byte[] {});
        SchemaValidatorImpl_SchemaValidatorImplStub01 validator = new SchemaValidatorImpl_SchemaValidatorImplStub01();
        validator.domParser = new SchemaValidatorImpl_DOMParserStub01();

        // 形式チェックエラーを発生させるため、実行前からエラーを格納しておく。
        ErrorMessages errorMessages = new ErrorMessages();
        errorMessages.add(new ErrorMessage(null,null));
        
        // テスト実施
        validator.validate(inputStream, new Object(), errorMessages);

        assertTrue(validator.isCreateDomParser);
        assertTrue(validator.isSetCommonParserProperty);
        assertTrue(validator.isSetCommonParserFeature);
        
        SchemaValidatorImpl_DOMParserStub01 parser 
        	= (SchemaValidatorImpl_DOMParserStub01) 
        	UTUtil.getPrivateField(validator, "domParser");
        assertTrue(parser.isParse);
        assertFalse(parser.isGetDocument);
        
    }

    /**
     * testValidate06()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A,D
     * <br><br>
     * 入力値：(引数) in:not null<br>
     *         (引数) object:null<br>
     *         (引数) errorMessages:not null<br>
     *         (状態) getUrl():呼び出したことを確認する。<br>
     *         (状態) createNonNamespaceDomParser():呼び出したことを確認する。<br>
     *         (状態) parser.perse():呼び出したことを確認する。<br>
     *                引数が渡されたことを確認する。<br>
     *         (状態) this.namespace:false<br>
     *         
     * <br>
     * 期待値：(戻り値) Document:Documentインスタンス<br>
     *                  （DOMツリー）<br>
     *         
     * <br>
     * 形式チェックが実行され、Documentが生成されることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testValidate06() throws Exception {
        
        // 前処理
        ByteArrayInputStream inputStream = new ByteArrayInputStream(
                new byte[] {});
        SchemaValidatorImpl_SchemaValidatorImplStub01 validator = new SchemaValidatorImpl_SchemaValidatorImplStub01();
        validator.domParser = new SchemaValidatorImpl_DOMParserStub01();
        
        // テスト実施
        validator.validate(inputStream, new Object(), new ErrorMessages());

        assertTrue(validator.isCreateDomParser);
        assertTrue(validator.isSetCommonParserProperty);
        assertTrue(validator.isSetCommonParserFeature);
        
        SchemaValidatorImpl_DOMParserStub01 parser 
        	= (SchemaValidatorImpl_DOMParserStub01) 
        	UTUtil.getPrivateField(validator, "domParser");
        assertTrue(parser.isParse);
        assertTrue(parser.isGetDocument);
    }

    /**
     * testValidate07()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) in:not null<br>
     *         (引数) object:null<br>
     *         (引数) errorMessages:not null<br>
     *         (状態) getUrl():呼び出したことを確認する。<br>
     *         (状態) getNamespaceName():呼び出したことを確認する。<br>
     *         (状態) creatNamespaceDomParser():呼び出したことを確認する。<br>
     *         (状態) setCommonParserProperty():呼び出したことを確認する。<br>
     *         (状態) setCommonParserFeature:呼び出したことを確認する。<br>
     *         (状態) parser.perse():呼び出したことを確認する。<br>
     *                引数が渡されたことを確認する。<br>
     *         (状態) this.namespace:true<br>
     *         
     * <br>
     * 期待値：(戻り値) Document:Documentインスタンス<br>
     *                  （DOMツリー）<br>
     *         
     * <br>
     * this.namespaceがtrueの場合、形式チェックが実行され、Documentが生成されることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testValidate07() throws Exception {
        
        // 前処理
        ByteArrayInputStream inputStream = new ByteArrayInputStream(
                new byte[] {});
        SchemaValidatorImpl_SchemaValidatorImplStub01 validator = new SchemaValidatorImpl_SchemaValidatorImplStub01();
        validator.domParser = new SchemaValidatorImpl_DOMParserStub01();
        validator.namespace = true;
        
        // テスト実施
        validator.validate(inputStream, new Object(), new ErrorMessages());

        assertTrue(validator.isCreateDomParser);
        assertTrue(validator.isSetCommonParserProperty);
        assertTrue(validator.isSetCommonParserFeature);
        
        SchemaValidatorImpl_DOMParserStub01 parser 
        	= (SchemaValidatorImpl_DOMParserStub01) 
        	UTUtil.getPrivateField(validator, "domParser");
        assertTrue(parser.isParse);
        assertTrue(parser.isGetDocument);
        
    }

    /**
     * testValidate08()
     * <br><br>
     * 
     * (異常系)
     * <br>
     * 観点：A,G
     * <br><br>
     * 入力値：(引数) in:not null<br>
     *         (引数) object:not null<br>
     *         (引数) errorMessages:not null<br>
     *         (状態) getUrl():呼び出したことを確認する。<br>
     *         (状態) createNonNamespaceDomParser():呼び出したことを確認する。<br>
     *         (状態) setCommonParserProperty():呼び出したことを確認する。<br>
     *         (状態) setCommonParserFeature:呼び出したことを確認する。<br>
     *         (状態) parser.perse():SAXExceptionをスローする<br>
     *         (状態) this.namespace:false<br>
     *         
     * <br>
     * 期待値：(状態変化) 例外:ParserSAXException{<br>
     *                      cause = SAXException<br>
     *                    }<br>
     *         (状態変化) ログ:ログレベル：error<br>
     *                    Schema parse error.<br>
     *         
     * <br>
     * parse()メソッド実行時に、SAXExceptionがスローされた場合、SAXExceptionをラップしたParserSAXExceptionがスローされ、ログが出力されることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testValidate08() throws Exception {
        
        // 前処理
        ByteArrayInputStream inputStream = new ByteArrayInputStream(
                new byte[] {});
        SchemaValidatorImpl_SchemaValidatorImplStub01 validator = new SchemaValidatorImpl_SchemaValidatorImplStub01();
        validator.domParser = new SchemaValidatorImpl_DOMParserStub02();
        validator.namespace = true;
        
        // テスト実施
        try{
            validator.validate(inputStream, new Object(), new ErrorMessages());
            fail();
        }catch (ParserSAXException e) {
            // 判定
            String message = "Schema parse error.";
            assertSame(SAXException.class, e.getCause().getClass());

            // ログ確認
            assertTrue(LogUTUtil.checkError(message, e.getCause()));
        }
        assertTrue(validator.isCreateDomParser);
        assertTrue(validator.isSetCommonParserProperty);
        assertTrue(validator.isSetCommonParserFeature);
        
    }

    /**
     * testValidate09()
     * <br><br>
     * 
     * (異常系)
     * <br>
     * 観点：A,G
     * <br><br>
     * 入力値：(引数) in:not null<br>
     *         (引数) object:not null<br>
     *         (引数) errorMessages:not null<br>
     *         (状態) getUrl():呼び出したことを確認する。<br>
     *         (状態) createNonNamespaceDomParser():呼び出したことを確認する。<br>
     *         (状態) setCommonParserProperty():呼び出したことを確認する。<br>
     *         (状態) setCommonParserFeature:呼び出したことを確認する。<br>
     *         (状態) parser.perse():IOExceptionをスローする<br>
     *         (状態) this.namespace:false<br>
     *         
     * <br>
     * 期待値：(状態変化) 例外:ParserIOException{<br>
     *                      cause = IOException<br>
     *                    }<br>
     *         (状態変化) ログ:ログレベル：error<br>
     *                    Schema io error.<br>
     *         
     * <br>
     * parse()メソッド実行時にIOExceptionがスローされた場合、IOExceptionをラップしたParserIOExceptionがスローされ、ログが出力されることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testValidate09() throws Exception {
        
        // 前処理
        ByteArrayInputStream inputStream = new ByteArrayInputStream(
                new byte[] {});
        SchemaValidatorImpl_SchemaValidatorImplStub01 validator 
        	= new SchemaValidatorImpl_SchemaValidatorImplStub01();
        validator.domParser = new SchemaValidatorImpl_DOMParserStub03();
        validator.namespace = true;
        
        // テスト実施
        try{
            validator.validate(inputStream, new Object(), new ErrorMessages());
            fail();
        }catch (ParserIOException e) {
            // 判定
            String message = "Schema io error.";
            assertSame(IOException.class, e.getCause().getClass());

            // ログ確認
            assertTrue(LogUTUtil.checkError(message, e.getCause()));
        }
        assertTrue(validator.isCreateDomParser);
        assertTrue(validator.isSetCommonParserProperty);
        assertTrue(validator.isSetCommonParserFeature);
        
    }

    /**
     * testCreateDomParser01()
     * <br><br>
     * 
     * (異常系)
     * <br>
     * 観点：G
     * <br><br>
     * 入力値：(引数) object:not null<br>
     *         (状態) getUrl():null<br>
     *         
     * <br>
     * 期待値：(状態変化) 例外:SchemaFileNotFoundException<br>
     * 				Schema file is not found. Set schema file in [root-classpath]/java/lang/Object.xsd
     *         (状態変化) ログ:ログレベル：error<br>
     *                    Schema file is not found.<br>
     *         
     * <br>
     * スキーマファイルが見つからない場合のテスト。例外を投げる。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testCreateDomParser01() throws Exception {
        // 前処理
    	SchemaValidatorImpl_SchemaValidatorImplStub05 validator 
    		= new SchemaValidatorImpl_SchemaValidatorImplStub05();
    	validator.config = new XML11Configuration();
    	validator.url = null;
    	Object object = new Object();

        // テスト実施
    	try {
    		validator.createDomParser(object);
    		fail();
    	} catch (SchemaFileNotFoundException e) {
    		assertTrue(validator.isGetUrl);
    		assertTrue(validator.isCreateXmlParserConfiguration);
    		assertTrue(LogUTUtil.checkError("Schema file is not found. " 
    			+ "Set schema file in [root-classpath]/java/lang/Object.xsd"));
    	}
    }

    /**
     * testCreateDomParser02()
     * <br><br>
     * 
     * (正常系) 
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) object:not null<br>
     *         (状態) getUrl():http://www.nttdata.co.jp/<br>
     *         (状態) this.namespace:true<br>
     *         
     * <br>
     * 期待値：(戻り値) DOMParser:DOMParser<br>
     *         
     * <br>
     * 名前空間使用する場合のテスト。DOMパーサを返す。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testCreateDomParser02() throws Exception {
    	// 前処理
    	SchemaValidatorImpl_SchemaValidatorImplStub05 validator 
    		= new SchemaValidatorImpl_SchemaValidatorImplStub05();
    	validator.config = new XML11Configuration();
    	validator.url = new URL("http://www.nttdata.co.jp/");
    	validator.namespace = true;
    	validator.namespaceName = "NamespaceTest";
    	Object object = new Object();

        // テスト実施
    	DOMParser result = validator.createDomParser(object);
    	Object location = result.getProperty(
    		"http://apache.org/xml/properties/schema/external-schemaLocation");
    	assertEquals("NamespaceTest http://www.nttdata.co.jp/", location);
    	assertTrue(validator.isGetUrl);
    	assertTrue(validator.isCreateXmlParserConfiguration);
    }

    /**
     * testCreateDomParser03()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) object:not null<br>
     *         (状態) getUrl():http://www.nttdata.co.jp/<br>
     *         (状態) this.namespace:false<br>
     *         
     * <br>
     * 期待値：(戻り値) DOMParser:DOMParser<br>
     *         
     * <br>
     * 名前空間使用しない場合のテスト。DOMパーサを返す。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testCreateDomParser03() throws Exception {
    	// 前処理
    	SchemaValidatorImpl_SchemaValidatorImplStub05 validator 
    		= new SchemaValidatorImpl_SchemaValidatorImplStub05();
    	validator.config = new XML11Configuration();
    	validator.url = new URL("http://www.nttdata.co.jp/");
    	validator.namespace = false;
    	Object object = new Object();

        // テスト実施
    	DOMParser result = validator.createDomParser(object);
    	Object location = result.getProperty(
    		"http://apache.org/xml/properties/schema/external-noNamespaceSchemaLocation");
    	assertEquals("http://www.nttdata.co.jp/", location);
    	assertTrue(validator.isGetUrl);
    	assertTrue(validator.isCreateXmlParserConfiguration);
    }
    
    /**
     * testCreateXmlParserConfiguration01()
     * <br><br>
     * 
     * (正常系) 
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(状態) ths.namespace:true<br>
     *         (状態) this.cache:true<br>
     *         
     * <br>
     * 期待値：(戻り値) XMLParserConfiguration:XMLParserConfiguration<br>
     *                  (内部にgrammarPoolを保持)<br>
     *         
     * <br>
     * 名前空間とキャッシュが有効になった場合のテスト。文法プールを保持するConfigurationを返す。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testCreateXmlParserConfiguration01() throws Exception {
        // 前処理
    	SchemaValidatorImpl validator = new SchemaValidatorImpl();
    	validator.namespace = true;
    	validator.cache = true;

        // テスト実施
    	XMLParserConfiguration result = validator.createXmlParserConfiguration();

        // 判定
    	assertSame(validator.grammarPool, UTUtil.getPrivateField(result, "fGrammarPool"));
    }

    /**
     * testCreateXmlParserConfiguration02()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(状態) ths.namespace:true<br>
     *         (状態) this.cache:false<br>
     *         
     * <br>
     * 期待値：(戻り値) XMLParserConfiguration:XMLParserConfiguration<br>
     *         
     * <br>
     * 名前空間のみ有効になった場合のテスト。通常のConfigurationを返す。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testCreateXmlParserConfiguration02() throws Exception {
    	SchemaValidatorImpl validator = new SchemaValidatorImpl();
    	validator.namespace = true;
    	validator.cache = false;

        // テスト実施
    	XMLParserConfiguration result = validator.createXmlParserConfiguration();

        // 判定
    	assertNotSame(validator.grammarPool, UTUtil.getPrivateField(result, "fGrammarPool"));
    }

    /**
     * testCreateXmlParserConfiguration03()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(状態) ths.namespace:false<br>
     *         (状態) this.cache:true<br>
     *         
     * <br>
     * 期待値：(戻り値) XMLParserConfiguration:XMLParserConfiguration<br>
     *         
     * <br>
     * キャッシュのみ有効になった場合のテスト。通常のConfigurationを返す。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testCreateXmlParserConfiguration03() throws Exception {
    	SchemaValidatorImpl validator = new SchemaValidatorImpl();
    	validator.namespace = false;
    	validator.cache = true;

        // テスト実施
    	XMLParserConfiguration result = validator.createXmlParserConfiguration();

        // 判定
    	assertNotSame(validator.grammarPool, UTUtil.getPrivateField(result, "fGrammarPool"));
    }
    
    /**
     * testSetCommonParserProperty01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) parser:not null<br>
     *         (引数) errorMessages:not null<br>
     *         
     * <br>
     * 期待値：(戻り値) DOMParser:（引数の）DOMParserインスタンス{<br>
     *                    fProperties{<br>
     *                      Constants.XERCES_PROPERTY_PREFIX<br>
     *                      + Constants.ERROR_REPORTER_PROPERTY= <br>
     *                         XMLErrorReporterExインスタンス= {<br>
     *                           errorMessages= 引数のErrorMessagesインスタンス<br>
     *                         }<br>
     *                    }<br>
     *                  }<br>
     *         
     * <br>
     * DOMParserのプロパティにXMLErrorReporterExインスタンスが設定されることを確認する。<br>
     * XMLErrorReporterExインスタンスに引数のErrorMessagesインスタンスが設定されることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetCommonParserProperty01() throws Exception {

        // 前処理
        DOMParser parser = new DOMParser();
        ErrorMessages messages = new ErrorMessages();
        
        // テスト実施
        setUpValidator.setCommonParserProperty(parser, messages);

        // 判定
        XMLErrorReporterEx reporter = (XMLErrorReporterEx) parser.getProperty(Constants.XERCES_PROPERTY_PREFIX
                + Constants.ERROR_REPORTER_PROPERTY);
        assertEquals(messages, reporter.getErrorMessages());
    }

    /**
     * testSetCommonParserFeature01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) parser:not null<br>
     *         
     * <br>
     * 期待値：(戻り値) DOMParser:（引数の）DOMParserインスタンス{<br>
     *                    fProperties{<br>
     *                      Constants.SAX_FEATURE_PREFIX<br>
     *                      + Constants.VALIDATION_FEATURE= true,<br>
     *                      Constants.XERCES_FEATURE_PREFIX<br>
     *                                  + Constants.SCHEMA_VALIDATION_FEATURE= true<br>
     *                    }<br>
     *                  }<br>
     *         
     * <br>
     * DOMParserのプロパティに各プロパティが設定されることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetCommonParserFeature01() throws Exception {

        // 前処理
        DOMParser parser = new DOMParser();
        
        // テスト実施
        setUpValidator.setCommonParserFeature(parser);

        // 判定
        assertTrue(((Boolean) parser.getFeature(Constants.SAX_FEATURE_PREFIX
                + Constants.VALIDATION_FEATURE)).booleanValue());
        assertTrue(((Boolean)(parser.getFeature(Constants.XERCES_FEATURE_PREFIX
                + Constants.SCHEMA_VALIDATION_FEATURE))).booleanValue());
        
    }

    /**
     * testGetUrl01()
     * <br><br>
     * 
     * (異常系)
     * <br>
     * 観点：A,C,G
     * <br><br>
     * 入力値：(引数) object:null<br>
     *         
     * <br>
     * 期待値：(状態変化) 例外:IllegalArgumentException{<br>
     *                      message = Argument object is null.<br>
     *                    }<br>
     *         (状態変化) ログ:ログレベル:error<br>
     *                    Argument object is null.<br>
     *         
     * <br>
     * 引数objectがnullの場合、例外がスローされることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetUrl01() throws Exception {
        // 前処理
        Object object = null;

        // テスト実施
        try {
            setUpValidator.getUrl(object);
            fail();
        } catch (IllegalArgumentException e) {
            // 判定
            String message = "Argument is null.";
            assertEquals(message, e.getMessage());

            // ログ確認
            assertTrue(LogUTUtil.checkError(message));
        }
    }

    /**
     * testGetUrl02()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) object:not null<br>
     *                （ファイルが存在するパス）<br>
     *         
     * <br>
     * 期待値：(戻り値) URL:URLインスタンス<br>
     *         
     * <br>
     * 指定パス上にファイルが存在する場合、URLインスタンスを返却することを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetUrl02() throws Exception {
        // 引数
        // 存在するパス（SchemaValidatorImplTest.xsd）
        Object object = this;

        // テスト実施
        URL result = setUpValidator.getUrl(object);
        
        // 判定
        String fileName = this.getClass().getName().replace(
                SchemaValidatorImpl.NESTED_PACKAGE_SEPARATOR,
                SchemaValidatorImpl.NESTED_FOLDER_SEPARATOR)
                + SchemaValidatorImpl.XSD_FILE_SUFFIX;
        URL url = Thread.currentThread().getContextClassLoader().getResource(
                fileName);
        assertEquals(url.toExternalForm(), result.toExternalForm());        

    }

    /**
     * testGetUrl03()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) object:not null<br>
     *                （ファイルが存在しないパス）<br>
     *         
     * <br>
     * 期待値：(戻り値) URL:null<br>
     *         
     * <br>
     * 指定パス上にファイルが存在しない場合、nullを返却することを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetUrl03() throws Exception {
        // 引数
        // 存在しないパス
        Object object = setUpValidator;

        // テスト実施
        URL result = setUpValidator.getUrl(object);
        
        // 判定
        assertNull(result);        

    }

    /**
     * testGetNamespaceName01()
     * <br><br>
     * 
     * (異常系)
     * <br>
     * 観点：C,G
     * <br><br>
     * 入力値：(引数) object:null<br>
     *         (状態) this.namespaceProperties:not null<br>
     *         (状態) this.NAME_SPACE_SUFFIX:".Namespace"<br>
     *         
     * <br>
     * 期待値：(状態変化) 例外:IllegalArgumentException{<br>
     *                      message = Argument is null.<br>
     *                    }<br>
     *         (状態変化) ログ:ログレベル:error<br>
     *                    Argument is null.<br>
     *         
     * <br>
     * 引数objectがnullの場合、例外がスローされることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetNamespaceName01() throws Exception {

        // テスト実施
        try {
            setUpValidator.getNamespaceName(null);
            fail();
        } catch (IllegalArgumentException e) {
            // 判定
            String message = "Argument is null.";
            assertEquals(message, e.getMessage());

            // ログ確認
            assertTrue(LogUTUtil.checkError(message));
        }

    }

    /**
     * testGetNamespaceName02()
     * <br><br>
     * 
     *  (異常系)
     * <br>
     * 観点：C,G
     * <br><br>
     * 入力値：(引数) not null<br>
     *         (状態) this.namespaceProperties:null<br>
     *         (状態) this.NAME_SPACE_SUFFIX:".Namespace"<br>
     *         
     * <br>
     * 期待値：(状態変化) 例外:IllegalStateException{<br>
     *                      message= Namespace properties is null.<br>
     *                    }<br>
     *         (状態変化) ログ:ログレベル:error<br>
     *                    Namespace properties is null.<br>
     *         
     * <br>
     * this.namespacePropertiesがnullの場合、例外がスローされることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetNamespaceName02() throws Exception {
        // 前処理
        setUpValidator.namespaceProperties = null;
        setUpValidator.namespace = true;

        // テスト実施
        try {
            setUpValidator.getNamespaceName(new Object());
            fail();
        } catch (IllegalStateException e) {
            // 判定
            String message = "Namespace property is not set. "
            	+ "Put namespaces.properties file on your classpath, and call SchemaValidatorImpl initNamespaceProperties() after instanciate.";
            assertEquals(message, e.getMessage());

            // ログ確認
            assertTrue(LogUTUtil.checkError(message));
        }

    }

    /**
     * testGetNamespaceName03()
     * <br><br>
     * 
     * (異常系)
     * <br>
     * 観点：A,C
     * <br><br>
     * 入力値：(引数) object:jp.terasoluna.fw.oxm.xsd.xerces.SchemaValidatorImplインスタンス<br>
     *         (状態) this.namespaceProperties:空のPropertiesインスタンス<br>
     *         (状態) this.NAME_SPACE_SUFFIX:".Namespace"<br>
     *         
     * <br>
     * 期待値：(戻り値) NamespaceNotFoundException<br>
     *         
     * <br>
     * objectのクラス名に合致するプロパティが存在しなかった場合、
     * 例外がスローされることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetNamespaceName03() throws Exception {
        // 前処理
        Properties prop = new Properties();
        setUpValidator.namespaceProperties = prop;
        setUpValidator.namespace = true;

        // テスト実施＆判定
        try {
        	setUpValidator.getNamespaceName("dummy");
        	fail();
        } catch (NamespaceNotFoundException e) {
        	String message = "Schema namespace is not found. Set namespace key " +
        		"- java.lang.String.Namespace in namespaces.properties file.";
        	assertTrue(LogUTUtil.checkError(message));
        }
    }

    /**
     * testGetNamespaceName04()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A,C,D
     * <br><br>
     * 入力値：(引数) object:jp.terasoluna.fw.oxm.xsd.xerces.SchemaValidatorImplインスタンス<br>
     *         (状態) this.namespaceProperties:Propertiesインスタンス{<br>
     *                  "jp.terasoluna.fw.oxm.xsd.xerces.SchemaValidatorImpl"= "123"<br>
     *                }<br>
     *         (状態) this.NAME_SPACE_SUFFIX:".Namespace"<br>
     *         
     * <br>
     * 期待値：(戻り値) String:"123"<br>
     *         
     * <br>
     * objectのクラス名に合致するプロパティが存在した場合、値を返却することを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetNamespaceName04() throws Exception {
        // 前処理
        Properties prop = new Properties();
        prop.setProperty(setUpValidator.getClass().getName() + ".Namespace", "123");
        setUpValidator.namespace = true;
        setUpValidator.namespaceProperties = prop;
        
        // テスト実施＆判定
        assertEquals("123", setUpValidator.getNamespaceName(setUpValidator));
    }

    /**
     * testGetNamespaceName05()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) object:not null<br>
     *         (状態) this.namespaceProperties:-<br>
     *         (状態) this.namespace:false<br>
     *         (状態) this.NAME_SPACE_SUFFIX:-<br>
     *         
     * <br>
     * 期待値：(戻り値) null<br>
     *         
     * <br>
     * 名前空間を使わない場合のテスト。Nullを返す。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetNamespaceName05() throws Exception {
        // 前処理
        setUpValidator.namespace = false;
        
        // テスト実施＆判定
        assertNull(setUpValidator.getNamespaceName(new Object()));
    }
}
