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

import java.util.Hashtable;

import jp.terasoluna.utlib.UTUtil;
import jp.terasoluna.fw.oxm.xsd.message.ErrorMessages;
import jp.terasoluna.fw.oxm.xsd.xerces.XML11ConfigurationEx;
import jp.terasoluna.fw.oxm.xsd.xerces.XMLErrorReporterEx;
import jp.terasoluna.fw.oxm.xsd.xerces.XMLSchemaValidatorEx;
import junit.framework.TestCase;

import org.apache.xerces.impl.Constants;
import org.apache.xerces.impl.dtd.XMLDTDProcessor;
import org.apache.xerces.impl.xs.XMLSchemaValidator;
import org.apache.xerces.impl.xs.XSMessageFormatter;
import org.apache.xerces.impl.xs.models.CMNodeFactory;
import org.apache.xerces.util.XMLGrammarPoolImpl;
import org.apache.xerces.xni.parser.XMLDTDScanner;

/**
 * {@link jp.terasoluna.fw.oxm.xsd.xerces.XML11ConfigurationEx} クラスのブラックボックステスト。
 * 
 * <p>
 * <h4>【クラスの概要】</h4>
 * XMLSchemaValidatorExインスタンスをパーサに設定するために、XML11Configurationを拡張したクラス。<br>
 * 前提条件：<br>
 * このクラスはSchemaValidatorImplで生成されることが前提であり、configurePipelineメソッドの前提条件this.fPropertiesには必ずXMLErrorReporterExインスタンスが設定されている。
 * </p>
 * 
 * @see jp.terasoluna.fw.oxm.xsd.xerces.XML11ConfigurationEx
 */
public class XML11ConfigurationExTest extends TestCase {

    /**
     * このテストケースを実行する為の GUI アプリケーションを起動する。
     * 
     * @param args java コマンドに設定されたパラメータ
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(XML11ConfigurationExTest.class);
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
    public XML11ConfigurationExTest(String name) {
        super(name);
    }

    /**
     * testXML11ConfigurationExXMLGrammarPool01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A,C
     * <br><br>
     * 入力値：(引数) grammarPool:null<br>
     *         
     * <br>
     * 期待値：(状態変化) this.fGrammarPool:null<br>
     *         
     * <br>
     * 引数の値が正しく属性に設定されることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testXML11ConfigurationExXMLGrammarPool01() throws Exception {

        // XMLGrammarPoolが設定されていることの確認
        XML11ConfigurationEx config = new XML11ConfigurationEx(null);
        
        // 判定
        assertNull(UTUtil.getPrivateField(config, "fGrammarPool"));
        
    }

    /**
     * testXML11ConfigurationExXMLGrammarPool02()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) grammarPool:not null<br>
     *         
     * <br>
     * 期待値：(状態変化) this.fGrammarPool:not null<br>
     *         
     * <br>
     * 引数の値が正しく属性に設定されることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testXML11ConfigurationExXMLGrammarPool02() throws Exception {
        // 前処理
        XMLGrammarPoolImpl grammarPool = new XMLGrammarPoolImpl();
        
        // XMLGrammarPoolが設定されていることの確認
        XML11ConfigurationEx config = new XML11ConfigurationEx(grammarPool);
        
        // 判定
        assertEquals(grammarPool, UTUtil.getPrivateField(config, "fGrammarPool"));
    }
    
    /**
     * testConfigurePipeline01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(状態) this.fFeatures:HashMap{<br>
     *                 Constants.SAX_FEATURE_PREFIX<br>
     *                                    + Constants.VALIDATION_FEATURE<br>
     *                  = false<br>
     *                }<br>
     *         (状態) this.fProperties:HashMap{<br>
     *                  Constants.XERCES_PROPERTY_PREFIX<br>
     *                                + Constants.ERROR_REPORTER_PROPERTY<br>
     *                  = XMLErrorReporterExインスタンス<br>
     *                }<br>
     *         (状態) setProperty():ー<br>
     *         (状態) addCommonComponent():呼び出されなかったことを確認する。<br>
     *         (状態) fSchemaValidator.reset():ー<br>
     *         (状態) super.configurePipeline():呼び出されたことのみ確認する。<br>
     *         
     * <br>
     * 期待値：
     * <br>
     * this.fFeaturesのXMLSCEHMA_VALIDATIONキーがfalseの場合、super.configurePipeline()メソッドが呼び出されたこと、引数が渡されたことを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testConfigurePipeline01() throws Exception {
        XML11ConfigurationEx_XML11ConfigurationExStub01 xmlConfiguration = new XML11ConfigurationEx_XML11ConfigurationExStub01();
        xmlConfiguration.setFeature(Constants.SAX_FEATURE_PREFIX
                + Constants.VALIDATION_FEATURE, false);

        XMLDTDProcessor dtdProcessor = new XMLDTDProcessor();

        UTUtil.setPrivateField(xmlConfiguration, "fDTDProcessor", dtdProcessor);
        xmlConfiguration.configurePipeline();

        // if文が実行されていないことの確認
        assertFalse(xmlConfiguration.isAddCommonComponent);
        
        // super.configurePipeline()の呼び出し確認
        XMLDTDScanner fDTDScanner = (XMLDTDScanner) UTUtil.getPrivateField(
                xmlConfiguration, "fDTDScanner");
        assertSame(dtdProcessor, fDTDScanner.getDTDHandler());
    }

    /**
     * testConfigurePipeline02()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(状態) this.fFeatures:HashMap{<br>
     *                  Constants.SAX_FEATURE_PREFIX<br>
     *                                    + Constants.VALIDATION_FEATURE<br>
     *                  = true<br>
     *                }<br>
     *         (状態) this.fProperties:HashMap{<br>
     *                  Constants.XERCES_PROPERTY_PREFIX<br>
     *                                + Constants.ERROR_REPORTER_PROPERTY<br>
     *                  = XMLErrorReporterExインスタンス<br>
     *                }<br>
     *         (状態) fErrorReporter.fMessageFormatters:HashMapのキー『XSMessageFormatter.SCHEMA_DOMAIN』が存在する<br>
     *         (状態) setProperty():呼び出されたことを確認する。<br>
     *                引数が設定されたことを確認する。<br>
     *         (状態) addCommonComponent():呼び出されたことを確認する。<br>
     *                引数が設定されたことを確認する。<br>
     *         (状態) fSchemaValidator.reset():呼び出されたことを確認する。<br>
     *                引数が設定されたことを確認する。<br>
     *         (状態) fErrorReporter.putMessageFormatter():呼び出されなかったことを確認する。<br>
     *         (状態) super.configurePipeline():呼び出されたことのみ確認する。<br>
     *         
     * <br>
     * 期待値：
     * <br>
     * this.fFeaturesのXMLSCEHMA_VALIDATIONキーがtrue、<br>
     * fErrorReporter.fMessageFormattersのキー『XSMessageFormatter.SCHEMA_DOMAIN』が存在する場合、各メソッドが呼び出されたこと、引数が渡されたことを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    @SuppressWarnings("unchecked")
    public void testConfigurePipeline02() throws Exception {
        XML11ConfigurationEx_XML11ConfigurationExStub01 xmlConfiguration = new XML11ConfigurationEx_XML11ConfigurationExStub01();
        xmlConfiguration.setFeature(Constants.XERCES_FEATURE_PREFIX
                + Constants.SCHEMA_VALIDATION_FEATURE, true);

        // ErrorReporterExをセットする
        XMLErrorReporterEx errorReporterEx = new XMLErrorReporterEx(new ErrorMessages());
        xmlConfiguration.setProperty(Constants.XERCES_PROPERTY_PREFIX
                + Constants.ERROR_REPORTER_PROPERTY, errorReporterEx);

        // fErrorReporterにMessageFormatterをセットする
        XML11ConfigurationEx_XMLErrorReporterStub01 errorReporter = new XML11ConfigurationEx_XMLErrorReporterStub01();
        UTUtil.setPrivateField(xmlConfiguration, "fErrorReporter", errorReporter);
        Hashtable fMessageFormatters = (Hashtable) UTUtil.getPrivateField(
                errorReporter, "fMessageFormatters");
        XSMessageFormatter xsMessageFormatter = new XSMessageFormatter();
        fMessageFormatters.put(XSMessageFormatter.SCHEMA_DOMAIN,
                xsMessageFormatter);

        XMLDTDProcessor dtdProcessor = new XMLDTDProcessor();
        UTUtil.setPrivateField(xmlConfiguration, "fDTDProcessor", dtdProcessor);

        // テスト実施
        xmlConfiguration.configurePipeline();

        // 判定
        // setPropertyメソッドの呼び出し確認
        XMLSchemaValidator xmlSchemaValidator = (XMLSchemaValidator) xmlConfiguration
        .getProperty((String) UTUtil.getPrivateField(xmlConfiguration,
                "SCHEMA_VALIDATOR"));
        assertSame(XMLSchemaValidatorEx.class, xmlSchemaValidator.getClass());
        assertSame(errorReporterEx, UTUtil.getPrivateField(xmlSchemaValidator,
                "reporter"));

        // addCommonComponentメソッドの呼び出し確認
        assertTrue(xmlConfiguration.isAddCommonComponent);

        // fSchemaValidator.resetメソッドの呼び出し確認
        CMNodeFactory cmNodeFactory = (CMNodeFactory) UTUtil.getPrivateField(
                xmlSchemaValidator, "nodeFactory");
        assertSame(errorReporterEx, UTUtil.getPrivateField(cmNodeFactory,
                "fErrorReporter"));
        
        // if文が実行されていないことの確認
        assertFalse(errorReporter.isPutMessageFormatter);

        // super.configurePipelineメソッドの呼び出し確認
        XMLDTDScanner fDTDScanner = (XMLDTDScanner) UTUtil.getPrivateField(
                xmlConfiguration, "fDTDScanner");
        assertSame(dtdProcessor, fDTDScanner.getDTDHandler());
    }

    /**
     * testConfigurePipeline03()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(状態) this.fFeatures:HashMap{<br>
     *                  Constants.SAX_FEATURE_PREFIX<br>
     *                                    + Constants.VALIDATION_FEATURE<br>
     *                  = true<br>
     *                }<br>
     *         (状態) this.fProperties:HashMap{<br>
     *                  Constants.XERCES_PROPERTY_PREFIX<br>
     *                                + Constants.ERROR_REPORTER_PROPERTY<br>
     *                  = XMLErrorReporterExインスタンス<br>
     *                }<br>
     *         (状態) fErrorReporter.fMessageFormatters:HashMapのキー『XSMessageFormatter.SCHEMA_DOMAIN』が存在しない<br>
     *         (状態) setProperty():呼び出されたことを確認する。<br>
     *                引数が設定されたことを確認する。<br>
     *         (状態) addCommonComponent():呼び出されたことを確認する。<br>
     *                引数が設定されたことを確認する。<br>
     *         (状態) fSchemaValidator.reset():呼び出されたことを確認する。<br>
     *                引数が設定されたことを確認する。<br>
     *         (状態) fErrorReporter.putMessageFormatter():呼び出されたことを確認する。<br>
     *                引数が設定されたことを確認する。<br>
     *         (状態) super.configurePipeline():呼び出されたことのみ確認する。<br>
     *         
     * <br>
     * 期待値：
     * <br>
     * this.fFeaturesのXMLSCEHMA_VALIDATIONキーがtrue、<br>
     * fErrorReporter.fMessageFormattersのキー『XSMessageFormatter.SCHEMA_DOMAIN』が存在しない場合、各メソッドが呼び出されたこと、引数が渡されたことを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testConfigurePipeline03() throws Exception {
        XML11ConfigurationEx_XML11ConfigurationExStub01 xmlConfiguration = new XML11ConfigurationEx_XML11ConfigurationExStub01();
        xmlConfiguration.setFeature(Constants.XERCES_FEATURE_PREFIX
                + Constants.SCHEMA_VALIDATION_FEATURE, true);

        // ErrorReporterExをセットする
        XMLErrorReporterEx errorReporterEx = new XMLErrorReporterEx(
                new ErrorMessages());
        xmlConfiguration.setProperty(Constants.XERCES_PROPERTY_PREFIX
                + Constants.ERROR_REPORTER_PROPERTY, errorReporterEx);

        // fErrorReporter.fMessageFormattersのHashtableからXSMessageFormatter.SCHEMA_DOMAINの値を削除
        XML11ConfigurationEx_XMLErrorReporterStub01 errorReporter = new XML11ConfigurationEx_XMLErrorReporterStub01();
        UTUtil.setPrivateField(xmlConfiguration, "fErrorReporter", errorReporter);
        Hashtable fMessageFormatters = (Hashtable) UTUtil.getPrivateField(
                errorReporter, "fMessageFormatters");

        fMessageFormatters.remove(XSMessageFormatter.SCHEMA_DOMAIN);

        // super.configurePipelineメソッドの呼び出し確認で使用
        XMLDTDProcessor dtdProcessor = new XMLDTDProcessor();
        UTUtil.setPrivateField(xmlConfiguration, "fDTDProcessor", dtdProcessor);

        // テスト実施
        xmlConfiguration.configurePipeline();

        // 判定
        XMLSchemaValidator xmlSchemaValidator = (XMLSchemaValidator) xmlConfiguration
                .getProperty((String) UTUtil.getPrivateField(xmlConfiguration,
                        "SCHEMA_VALIDATOR"));

        // setPropertyメソッドの呼び出し確認
        assertSame(XMLSchemaValidatorEx.class, xmlSchemaValidator.getClass());

        // getPropertyメソッドの呼び出し確認
        assertSame(errorReporterEx, UTUtil.getPrivateField(xmlSchemaValidator,
                "reporter"));

        // addCommonComponentメソッドの呼び出し確認
        assertTrue(xmlConfiguration.isAddCommonComponent);

        // fSchemaValidator.resetメソッドの呼び出し確認
        CMNodeFactory cmNodeFactory = (CMNodeFactory) UTUtil.getPrivateField(
                xmlSchemaValidator, "nodeFactory");
        assertSame(errorReporterEx, UTUtil.getPrivateField(cmNodeFactory,
                "fErrorReporter"));

        // fErrorReporter.putMessageFormatterメソッドの呼び出し確認
        assertTrue(errorReporter.isPutMessageFormatter);

        // super.configurePipelineメソッドの呼び出し確認
        XMLDTDScanner fDTDScanner = (XMLDTDScanner) UTUtil.getPrivateField(
                xmlConfiguration, "fDTDScanner");
        assertSame(dtdProcessor, fDTDScanner.getDTDHandler());

    }

}
