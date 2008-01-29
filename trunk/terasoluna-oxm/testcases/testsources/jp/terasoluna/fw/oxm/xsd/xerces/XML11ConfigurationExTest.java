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
 * {@link jp.terasoluna.fw.oxm.xsd.xerces.XML11ConfigurationEx} �N���X�̃u���b�N�{�b�N�X�e�X�g�B
 * 
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4>
 * XMLSchemaValidatorEx�C���X�^���X���p�[�T�ɐݒ肷�邽�߂ɁAXML11Configuration���g�������N���X�B<br>
 * �O������F<br>
 * ���̃N���X��SchemaValidatorImpl�Ő�������邱�Ƃ��O��ł���AconfigurePipeline���\�b�h�̑O�����this.fProperties�ɂ͕K��XMLErrorReporterEx�C���X�^���X���ݒ肳��Ă���B
 * </p>
 * 
 * @see jp.terasoluna.fw.oxm.xsd.xerces.XML11ConfigurationEx
 */
public class XML11ConfigurationExTest extends TestCase {

    /**
     * ���̃e�X�g�P�[�X�����s����ׂ� GUI �A�v���P�[�V�������N������B
     * 
     * @param args java �R�}���h�ɐݒ肳�ꂽ�p�����[�^
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(XML11ConfigurationExTest.class);
    }

    /**
     * �������������s���B
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    /**
     * �I���������s���B
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     * @see junit.framework.TestCase#tearDown()
     */
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * �R���X�g���N�^�B
     * 
     * @param name ���̃e�X�g�P�[�X�̖��O�B
     */
    public XML11ConfigurationExTest(String name) {
        super(name);
    }

    /**
     * testXML11ConfigurationExXMLGrammarPool01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA,C
     * <br><br>
     * ���͒l�F(����) grammarPool:null<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) this.fGrammarPool:null<br>
     *         
     * <br>
     * �����̒l�������������ɐݒ肳��邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testXML11ConfigurationExXMLGrammarPool01() throws Exception {

        // XMLGrammarPool���ݒ肳��Ă��邱�Ƃ̊m�F
        XML11ConfigurationEx config = new XML11ConfigurationEx(null);
        
        // ����
        assertNull(UTUtil.getPrivateField(config, "fGrammarPool"));
        
    }

    /**
     * testXML11ConfigurationExXMLGrammarPool02()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) grammarPool:not null<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) this.fGrammarPool:not null<br>
     *         
     * <br>
     * �����̒l�������������ɐݒ肳��邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testXML11ConfigurationExXMLGrammarPool02() throws Exception {
        // �O����
        XMLGrammarPoolImpl grammarPool = new XMLGrammarPoolImpl();
        
        // XMLGrammarPool���ݒ肳��Ă��邱�Ƃ̊m�F
        XML11ConfigurationEx config = new XML11ConfigurationEx(grammarPool);
        
        // ����
        assertEquals(grammarPool, UTUtil.getPrivateField(config, "fGrammarPool"));
    }
    
    /**
     * testConfigurePipeline01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(���) this.fFeatures:HashMap{<br>
     *                 Constants.SAX_FEATURE_PREFIX<br>
     *                                    + Constants.VALIDATION_FEATURE<br>
     *                  = false<br>
     *                }<br>
     *         (���) this.fProperties:HashMap{<br>
     *                  Constants.XERCES_PROPERTY_PREFIX<br>
     *                                + Constants.ERROR_REPORTER_PROPERTY<br>
     *                  = XMLErrorReporterEx�C���X�^���X<br>
     *                }<br>
     *         (���) setProperty():�[<br>
     *         (���) addCommonComponent():�Ăяo����Ȃ��������Ƃ��m�F����B<br>
     *         (���) fSchemaValidator.reset():�[<br>
     *         (���) super.configurePipeline():�Ăяo���ꂽ���Ƃ̂݊m�F����B<br>
     *         
     * <br>
     * ���Ғl�F
     * <br>
     * this.fFeatures��XMLSCEHMA_VALIDATION�L�[��false�̏ꍇ�Asuper.configurePipeline()���\�b�h���Ăяo���ꂽ���ƁA�������n���ꂽ���Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testConfigurePipeline01() throws Exception {
        XML11ConfigurationEx_XML11ConfigurationExStub01 xmlConfiguration = new XML11ConfigurationEx_XML11ConfigurationExStub01();
        xmlConfiguration.setFeature(Constants.SAX_FEATURE_PREFIX
                + Constants.VALIDATION_FEATURE, false);

        XMLDTDProcessor dtdProcessor = new XMLDTDProcessor();

        UTUtil.setPrivateField(xmlConfiguration, "fDTDProcessor", dtdProcessor);
        xmlConfiguration.configurePipeline();

        // if�������s����Ă��Ȃ����Ƃ̊m�F
        assertFalse(xmlConfiguration.isAddCommonComponent);
        
        // super.configurePipeline()�̌Ăяo���m�F
        XMLDTDScanner fDTDScanner = (XMLDTDScanner) UTUtil.getPrivateField(
                xmlConfiguration, "fDTDScanner");
        assertSame(dtdProcessor, fDTDScanner.getDTDHandler());
    }

    /**
     * testConfigurePipeline02()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(���) this.fFeatures:HashMap{<br>
     *                  Constants.SAX_FEATURE_PREFIX<br>
     *                                    + Constants.VALIDATION_FEATURE<br>
     *                  = true<br>
     *                }<br>
     *         (���) this.fProperties:HashMap{<br>
     *                  Constants.XERCES_PROPERTY_PREFIX<br>
     *                                + Constants.ERROR_REPORTER_PROPERTY<br>
     *                  = XMLErrorReporterEx�C���X�^���X<br>
     *                }<br>
     *         (���) fErrorReporter.fMessageFormatters:HashMap�̃L�[�wXSMessageFormatter.SCHEMA_DOMAIN�x�����݂���<br>
     *         (���) setProperty():�Ăяo���ꂽ���Ƃ��m�F����B<br>
     *                �������ݒ肳�ꂽ���Ƃ��m�F����B<br>
     *         (���) addCommonComponent():�Ăяo���ꂽ���Ƃ��m�F����B<br>
     *                �������ݒ肳�ꂽ���Ƃ��m�F����B<br>
     *         (���) fSchemaValidator.reset():�Ăяo���ꂽ���Ƃ��m�F����B<br>
     *                �������ݒ肳�ꂽ���Ƃ��m�F����B<br>
     *         (���) fErrorReporter.putMessageFormatter():�Ăяo����Ȃ��������Ƃ��m�F����B<br>
     *         (���) super.configurePipeline():�Ăяo���ꂽ���Ƃ̂݊m�F����B<br>
     *         
     * <br>
     * ���Ғl�F
     * <br>
     * this.fFeatures��XMLSCEHMA_VALIDATION�L�[��true�A<br>
     * fErrorReporter.fMessageFormatters�̃L�[�wXSMessageFormatter.SCHEMA_DOMAIN�x�����݂���ꍇ�A�e���\�b�h���Ăяo���ꂽ���ƁA�������n���ꂽ���Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testConfigurePipeline02() throws Exception {
        XML11ConfigurationEx_XML11ConfigurationExStub01 xmlConfiguration = new XML11ConfigurationEx_XML11ConfigurationExStub01();
        xmlConfiguration.setFeature(Constants.XERCES_FEATURE_PREFIX
                + Constants.SCHEMA_VALIDATION_FEATURE, true);

        // ErrorReporterEx���Z�b�g����
        XMLErrorReporterEx errorReporterEx = new XMLErrorReporterEx(new ErrorMessages());
        xmlConfiguration.setProperty(Constants.XERCES_PROPERTY_PREFIX
                + Constants.ERROR_REPORTER_PROPERTY, errorReporterEx);

        // fErrorReporter��MessageFormatter���Z�b�g����
        XML11ConfigurationEx_XMLErrorReporterStub01 errorReporter = new XML11ConfigurationEx_XMLErrorReporterStub01();
        UTUtil.setPrivateField(xmlConfiguration, "fErrorReporter", errorReporter);
        Hashtable fMessageFormatters = (Hashtable) UTUtil.getPrivateField(
                errorReporter, "fMessageFormatters");
        XSMessageFormatter xsMessageFormatter = new XSMessageFormatter();
        fMessageFormatters.put(XSMessageFormatter.SCHEMA_DOMAIN,
                xsMessageFormatter);

        XMLDTDProcessor dtdProcessor = new XMLDTDProcessor();
        UTUtil.setPrivateField(xmlConfiguration, "fDTDProcessor", dtdProcessor);

        // �e�X�g���{
        xmlConfiguration.configurePipeline();

        // ����
        // setProperty���\�b�h�̌Ăяo���m�F
        XMLSchemaValidator xmlSchemaValidator = (XMLSchemaValidator) xmlConfiguration
        .getProperty((String) UTUtil.getPrivateField(xmlConfiguration,
                "SCHEMA_VALIDATOR"));
        assertSame(XMLSchemaValidatorEx.class, xmlSchemaValidator.getClass());
        assertSame(errorReporterEx, UTUtil.getPrivateField(xmlSchemaValidator,
                "reporter"));

        // addCommonComponent���\�b�h�̌Ăяo���m�F
        assertTrue(xmlConfiguration.isAddCommonComponent);

        // fSchemaValidator.reset���\�b�h�̌Ăяo���m�F
        CMNodeFactory cmNodeFactory = (CMNodeFactory) UTUtil.getPrivateField(
                xmlSchemaValidator, "nodeFactory");
        assertSame(errorReporterEx, UTUtil.getPrivateField(cmNodeFactory,
                "fErrorReporter"));
        
        // if�������s����Ă��Ȃ����Ƃ̊m�F
        assertFalse(errorReporter.isPutMessageFormatter);

        // super.configurePipeline���\�b�h�̌Ăяo���m�F
        XMLDTDScanner fDTDScanner = (XMLDTDScanner) UTUtil.getPrivateField(
                xmlConfiguration, "fDTDScanner");
        assertSame(dtdProcessor, fDTDScanner.getDTDHandler());
    }

    /**
     * testConfigurePipeline03()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(���) this.fFeatures:HashMap{<br>
     *                  Constants.SAX_FEATURE_PREFIX<br>
     *                                    + Constants.VALIDATION_FEATURE<br>
     *                  = true<br>
     *                }<br>
     *         (���) this.fProperties:HashMap{<br>
     *                  Constants.XERCES_PROPERTY_PREFIX<br>
     *                                + Constants.ERROR_REPORTER_PROPERTY<br>
     *                  = XMLErrorReporterEx�C���X�^���X<br>
     *                }<br>
     *         (���) fErrorReporter.fMessageFormatters:HashMap�̃L�[�wXSMessageFormatter.SCHEMA_DOMAIN�x�����݂��Ȃ�<br>
     *         (���) setProperty():�Ăяo���ꂽ���Ƃ��m�F����B<br>
     *                �������ݒ肳�ꂽ���Ƃ��m�F����B<br>
     *         (���) addCommonComponent():�Ăяo���ꂽ���Ƃ��m�F����B<br>
     *                �������ݒ肳�ꂽ���Ƃ��m�F����B<br>
     *         (���) fSchemaValidator.reset():�Ăяo���ꂽ���Ƃ��m�F����B<br>
     *                �������ݒ肳�ꂽ���Ƃ��m�F����B<br>
     *         (���) fErrorReporter.putMessageFormatter():�Ăяo���ꂽ���Ƃ��m�F����B<br>
     *                �������ݒ肳�ꂽ���Ƃ��m�F����B<br>
     *         (���) super.configurePipeline():�Ăяo���ꂽ���Ƃ̂݊m�F����B<br>
     *         
     * <br>
     * ���Ғl�F
     * <br>
     * this.fFeatures��XMLSCEHMA_VALIDATION�L�[��true�A<br>
     * fErrorReporter.fMessageFormatters�̃L�[�wXSMessageFormatter.SCHEMA_DOMAIN�x�����݂��Ȃ��ꍇ�A�e���\�b�h���Ăяo���ꂽ���ƁA�������n���ꂽ���Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testConfigurePipeline03() throws Exception {
        XML11ConfigurationEx_XML11ConfigurationExStub01 xmlConfiguration = new XML11ConfigurationEx_XML11ConfigurationExStub01();
        xmlConfiguration.setFeature(Constants.XERCES_FEATURE_PREFIX
                + Constants.SCHEMA_VALIDATION_FEATURE, true);

        // ErrorReporterEx���Z�b�g����
        XMLErrorReporterEx errorReporterEx = new XMLErrorReporterEx(
                new ErrorMessages());
        xmlConfiguration.setProperty(Constants.XERCES_PROPERTY_PREFIX
                + Constants.ERROR_REPORTER_PROPERTY, errorReporterEx);

        // fErrorReporter.fMessageFormatters��Hashtable����XSMessageFormatter.SCHEMA_DOMAIN�̒l���폜
        XML11ConfigurationEx_XMLErrorReporterStub01 errorReporter = new XML11ConfigurationEx_XMLErrorReporterStub01();
        UTUtil.setPrivateField(xmlConfiguration, "fErrorReporter", errorReporter);
        Hashtable fMessageFormatters = (Hashtable) UTUtil.getPrivateField(
                errorReporter, "fMessageFormatters");

        fMessageFormatters.remove(XSMessageFormatter.SCHEMA_DOMAIN);

        // super.configurePipeline���\�b�h�̌Ăяo���m�F�Ŏg�p
        XMLDTDProcessor dtdProcessor = new XMLDTDProcessor();
        UTUtil.setPrivateField(xmlConfiguration, "fDTDProcessor", dtdProcessor);

        // �e�X�g���{
        xmlConfiguration.configurePipeline();

        // ����
        XMLSchemaValidator xmlSchemaValidator = (XMLSchemaValidator) xmlConfiguration
                .getProperty((String) UTUtil.getPrivateField(xmlConfiguration,
                        "SCHEMA_VALIDATOR"));

        // setProperty���\�b�h�̌Ăяo���m�F
        assertSame(XMLSchemaValidatorEx.class, xmlSchemaValidator.getClass());

        // getProperty���\�b�h�̌Ăяo���m�F
        assertSame(errorReporterEx, UTUtil.getPrivateField(xmlSchemaValidator,
                "reporter"));

        // addCommonComponent���\�b�h�̌Ăяo���m�F
        assertTrue(xmlConfiguration.isAddCommonComponent);

        // fSchemaValidator.reset���\�b�h�̌Ăяo���m�F
        CMNodeFactory cmNodeFactory = (CMNodeFactory) UTUtil.getPrivateField(
                xmlSchemaValidator, "nodeFactory");
        assertSame(errorReporterEx, UTUtil.getPrivateField(cmNodeFactory,
                "fErrorReporter"));

        // fErrorReporter.putMessageFormatter���\�b�h�̌Ăяo���m�F
        assertTrue(errorReporter.isPutMessageFormatter);

        // super.configurePipeline���\�b�h�̌Ăяo���m�F
        XMLDTDScanner fDTDScanner = (XMLDTDScanner) UTUtil.getPrivateField(
                xmlConfiguration, "fDTDScanner");
        assertSame(dtdProcessor, fDTDScanner.getDTDHandler());

    }

}
