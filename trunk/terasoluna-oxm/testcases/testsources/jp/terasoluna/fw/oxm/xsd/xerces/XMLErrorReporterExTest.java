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
 * {@link jp.terasoluna.fw.oxm.xsd.xerces.XMLErrorReporterEx} �N���X�̃u���b�N�{�b�N�X�e�X�g�B
 * 
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4>
 * XML�f�[�^�̃p�[�X���̏ڍׂȃG���[���Ƃ��āA�t�B�[���h�����������߁AXMLErrorReporter�̊g�����s�����N���X�B<br>
 * �E�O�����<br>
 * reportError���\�b�h�̓p�[�T����Ăяo����邱�Ƃ��O��ł���A����location�Adomain�Akey�Aseverity��null�ɂȂ邱�Ƃ͂Ȃ��B<br>
 * addErrorMessage���\�b�h��reportError���\�b�h����Ăяo����邱�Ƃ��O��ł���A����key�Aoptions�Aoptions[1]��null�A�󕶎��ɂȂ邱�Ƃ͂Ȃ��BtagStack�ɂ͕K����ȏ�̗v�f���i�[����Ă���B<br>
 * errorLog���\�b�h��reportError���\�b�h����Ăяo����邱�Ƃ��O��ł���A����key�Aoptions��null�ɂȂ邱�Ƃ͂Ȃ��AtagStack�ɂ͕K����ȏ�̗v�f���i�[����Ă���B<br>
 * indexResolve���\�b�h�́AXMLSchemaValidatorEx��startElement�܂���endElement���Ăяo����邱�Ƃ��O��ł���A����element��null�ɂȂ邱�Ƃ͂Ȃ��B<br>
 * getKey���\�b�h�̃e�X�g�P�[�X�́AindexResolve���\�b�h�̃e�X�g�P�[�X�Ŗԗ�����B<br>
 * getMessage���\�b�h�̃e�X�g�P�[�X�́AerrorLog���\�b�h�Ŗԗ�����B
 * </p>
 * 
 * @see jp.terasoluna.fw.oxm.xsd.xerces.XMLErrorReporterEx
 */
public class XMLErrorReporterExTest extends TestCase {

    /**
     * ���̃e�X�g�P�[�X�����s����ׂ� GUI �A�v���P�[�V�������N������B
     * 
     * @param args java �R�}���h�ɐݒ肳�ꂽ�p�����[�^
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(XMLErrorReporterExTest.class);
    }

    /**
     * XMLErrorReporterEx�C���X�^���X
     */
    private XMLErrorReporterEx errorReporter = null;

    /**
     * �������������s���B
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();

        // XMLErrorReporterEx�C���X�^���X
        ErrorMessages errorMessages = new ErrorMessages();
        this.errorReporter = new XMLErrorReporterEx(errorMessages);

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
    public XMLErrorReporterExTest(String name) {
        super(name);
    }

    /**
     * testXMLErrorReporterEx01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) errorMessages:not null<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) this.errorMessages:not null<br>
     *         
     * <br>
     * �����̃C���X�^���X�������ɐݒ肳��邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testXMLErrorReporterEx01() throws Exception {
        // �O����
        ErrorMessages errorMessages = new ErrorMessages();

        // �e�X�g���{
        XMLErrorReporterEx reporter = new XMLErrorReporterEx(errorMessages);

        // ����
        assertSame(errorMessages, UTUtil.getPrivateField(reporter,
                "errorMessages"));
    }

    /**
     * testXMLErrorReporterEx02()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA,C
     * <br><br>
     * ���͒l�F(����) errorMessages:null<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) this.errorMessages:null<br>
     *         
     * <br>
     * �����̃C���X�^���X�������ɐݒ肳��邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testXMLErrorReporterEx02() throws Exception {
        // �O����
        ErrorMessages errorMessages = null;

        // �e�X�g���{
        XMLErrorReporterEx reporter = new XMLErrorReporterEx(errorMessages);

        // ����
        assertNull(UTUtil.getPrivateField(reporter, "errorMessages"));

    }

    /**
     * testGetErrorMessages01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(���) this.errorMessages:not null<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) ErrorMessages:not null<br>
     *         
     * <br>
     * �����̒l���������ԋp����邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetErrorMessages01() throws Exception {
        // �O����
        ErrorMessages errorMessages = new ErrorMessages();
        XMLErrorReporterEx reporter = new XMLErrorReporterEx(errorMessages);

        // �e�X�g���{
        ErrorMessages result = reporter.getErrorMessages();

        // ����
        assertSame(errorMessages, result);

    }

    /**
     * testReportError01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA,D
     * <br><br>
     * ���͒l�F(����) location:not null<br>
     *         (����) domain:not null<br>
     *         (����) key:not null<br>
     *         (����) arguments:��̃I�u�W�F�N�g<br>
     *         (����) severity:not null<br>
     *         (���) super.reportError():�Ăяo���ꂽ���Ƃ��m�F����B<br>
     *                �������n���ꂽ���Ƃ��m�F����B<br>
     *         (���) errorLog():�Ăяo���ꂽ���Ƃ��m�F����B<br>
     *         (���) addErrorMessage():�Ăяo���ꂽ���Ƃ��m�F����B<br>
     *                �������n���ꂽ���Ƃ��m�F����B<br>
     *         
     * <br>
     * ���Ғl�F
     * <br>
     * arguments����̏ꍇ�Asuper.reportError���\�b�h�AerrorLog���\�b�h�AaddErrorMessages���\�b�h���Ăяo���ꂽ���Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testReportError01() throws Exception {
        // �O����
        ErrorMessages errorMessages = new ErrorMessages();
        XMLErrorReporterEx_XMLErrorReporterExStub01 reporter = new XMLErrorReporterEx_XMLErrorReporterExStub01(
                errorMessages);

        // super.reportError���\�b�h�̌Ăяo���m�F�̂��߂̑O�����
        UTUtil.setPrivateField(reporter, "fErrorHandler", null);
        DefaultErrorHandler defaultErrorHandler = new DefaultErrorHandler();
        UTUtil.setPrivateField(reporter, "fDefaultErrorHandler",
                defaultErrorHandler);
        
        // ����
        XMLLocator location = new SimpleLocator();
        String domain = "abc";
        String key = "def";
        Object[] arguments = new Object[] {};
        short severity = 123;

        // �e�X�g���{
        reporter.reportError(location, domain, key, arguments, severity);

        // ����
        // super.reportError���\�b�h�̌Ăяo���m�F
        assertSame(defaultErrorHandler, UTUtil
                .getPrivateField(reporter, "fDefaultErrorHandler"));

        // errorLog���\�b�h�̌Ăяo���m�F
        assertTrue(reporter.isErrorLog);

        // addErrorMessage���\�b�h�Ɉ������n���ꂽ���Ƃ̊m�F
        assertEquals("def", reporter.key);
        assertEquals(0, reporter.options.length);

    }

    /**
     * testReportError02()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA,D
     * <br><br>
     * ���͒l�F(����) location:not null<br>
     *         (����) domain:not null<br>
     *         (����) key:not null<br>
     *         (����) arguments:Object[0]{"xyz"}<br>
     *         (����) severity:not null<br>
     *         (���) super.reportError():�Ăяo���ꂽ���Ƃ��m�F����B<br>
     *                �������n���ꂽ���Ƃ��m�F����B<br>
     *         (���) errorLog():�Ăяo���ꂽ���Ƃ��m�F����B<br>
     *         (���) addErrorMessage():�Ăяo���ꂽ���Ƃ��m�F����B<br>
     *                �������n���ꂽ���Ƃ��m�F����B<br>
     *         
     * <br>
     * ���Ғl�F
     * <br>
     * arguments�̗v�f���P�̏ꍇ�Asuper.reportError���\�b�h�AerrorLog���\�b�h�AaddErrorMessages���\�b�h���Ăяo���ꂽ���Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testReportError02() throws Exception {
        // �O����
        ErrorMessages errorMessages = new ErrorMessages();
        XMLErrorReporterEx_XMLErrorReporterExStub01 reporter = new XMLErrorReporterEx_XMLErrorReporterExStub01(
                errorMessages);

        // super.reportError���\�b�h�̌Ăяo���m�F�̂��߂̑O�����
        UTUtil.setPrivateField(reporter, "fErrorHandler", null);
        DefaultErrorHandler defaultErrorHandler = new DefaultErrorHandler();
        UTUtil.setPrivateField(reporter, "fDefaultErrorHandler",
                defaultErrorHandler);

        // ����
        XMLLocator location = new SimpleLocator();
        String domain = "abc";
        String key = "def";
        Object[] arguments = new Object[] { "xyz" };
        short severity = 123;

        // �e�X�g���{
        reporter.reportError(location, domain, key, arguments, severity);

        // ����
        // super.reportError�̌Ăяo���m�F
        assertSame(defaultErrorHandler, UTUtil
                .getPrivateField(reporter, "fDefaultErrorHandler"));

        // errorLog���\�b�h�̌Ăяo���m�F
        assertTrue(reporter.isErrorLog);

        // addErrorMessage���\�b�h�Ɉ������n���ꂽ���Ƃ̊m�F
        assertEquals("def", reporter.key);
        assertEquals(1, reporter.options.length);
        assertEquals("xyz", reporter.options[0]);


    }

    /**
     * testReportError03()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA,D
     * <br><br>
     * ���͒l�F(����) location:not null<br>
     *         (����) domain:not null<br>
     *         (����) key:not null<br>
     *         (����) arguments:Object[0]{"xyz"}<br>
     *                Object[1]{null}<br>
     *                Object[1]{456}<br>
     *         (����) severity:not null<br>
     *         (���) super.reportError():�Ăяo���ꂽ���Ƃ��m�F����B<br>
     *                �������n���ꂽ���Ƃ��m�F����B<br>
     *         (���) errorLog():�Ăяo���ꂽ���Ƃ��m�F����B<br>
     *         (���) addErrorMessage():�Ăяo���ꂽ���Ƃ��m�F����B<br>
     *                �������n���ꂽ���Ƃ��m�F����B<br>
     *         
     * <br>
     * ���Ғl�F
     * <br>
     * arguments�̗v�f���R�̏ꍇ�Asuper.reportError���\�b�h�AerrorLog���\�b�h�AaddErrorMessages���\�b�h���Ăяo���ꂽ���Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testReportError03() throws Exception {
        // �O����
        ErrorMessages errorMessages = new ErrorMessages();
        XMLErrorReporterEx_XMLErrorReporterExStub01 reporter = new XMLErrorReporterEx_XMLErrorReporterExStub01(
                errorMessages);

        // super.reportError���\�b�h�̌Ăяo���m�F�̂��߂̑O�����
        UTUtil.setPrivateField(reporter, "fErrorHandler", null);
        DefaultErrorHandler defaultErrorHandler = new DefaultErrorHandler();
        UTUtil.setPrivateField(reporter, "fDefaultErrorHandler",
                defaultErrorHandler);

        // ����
        XMLLocator location = new SimpleLocator();
        String domain = "abc";
        String key = "def";
        Object[] arguments = new Object[] { "xyz", null, 456 };
        short severity = 123;

        // �e�X�g���{
        reporter.reportError(location, domain, key, arguments, severity);

        // ����
        // super.reportError�̌Ăяo���m�F        
        assertSame(defaultErrorHandler, UTUtil
                .getPrivateField(reporter, "fDefaultErrorHandler"));

        // errorLog���\�b�h�̌Ăяo���m�F
        assertTrue(reporter.isErrorLog);

        // addErrorMessage���\�b�h�Ɉ������n���ꂽ���Ƃ̊m�F
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
     * (����n)
     * <br>
     * �ϓ_�FA,D
     * <br><br>
     * ���͒l�F(����) location:not null<br>
     *         (����) domain:not null<br>
     *         (����) key:not null<br>
     *         (����) arguments:null<br>
     *         (����) severity:not null<br>
     *         (���) super.reportError():�Ăяo���ꂽ���Ƃ��m�F����B<br>
     *                �������n���ꂽ���Ƃ��m�F����B<br>
     *         (���) errorLog():�Ăяo���ꂽ���Ƃ��m�F����B<br>
     *         (���) addErrorMessage():�Ăяo���ꂽ���Ƃ��m�F����B<br>
     *                �������n���ꂽ���Ƃ��m�F����B<br>
     *         
     * <br>
     * ���Ғl�F
     * <br>
     * arguments��null�̏ꍇ�Asuper.reportError���\�b�h�AerrorLog���\�b�h�AaddErrorMessages���\�b�h���Ăяo���ꂽ���Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testReportError04() throws Exception {
        // �O����
        ErrorMessages errorMessages = new ErrorMessages();
        XMLErrorReporterEx_XMLErrorReporterExStub01 reporter = new XMLErrorReporterEx_XMLErrorReporterExStub01(
                errorMessages);

        // super.reportError���\�b�h�̌Ăяo���m�F�̂��߂̑O�����
        UTUtil.setPrivateField(reporter, "fErrorHandler", null);
        DefaultErrorHandler defaultErrorHandler = new DefaultErrorHandler();
        UTUtil.setPrivateField(reporter, "fDefaultErrorHandler",
                defaultErrorHandler);
        
        // ����
        XMLLocator location = new SimpleLocator();
        String domain = "abc";
        String key = "def";
        Object[] arguments = null;
        short severity = 123;

        // �e�X�g���{
        reporter.reportError(location, domain, key, arguments, severity);

        // ����
        // super.reportError���\�b�h�̌Ăяo���m�F
        assertSame(defaultErrorHandler, UTUtil
                .getPrivateField(reporter, "fDefaultErrorHandler"));

        // errorLog���\�b�h�̌Ăяo���m�F
        assertTrue(reporter.isErrorLog);

        // addErrorMessage���\�b�h�Ɉ������n���ꂽ���Ƃ̊m�F
        assertEquals("def", reporter.key);
        assertEquals(0, reporter.options.length);

    }
    
    /**
     * testIndexResolve01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) element:"abc"<br>
     *         (���) this.tagIndex:���HashMap<br>
     *         (���) this.tagStack:���Stack<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) String:"abc[0]"<br>
     *         (��ԕω�) this.tagIndex:HashMap{<br>
     *                      "abc"=0<br>
     *                    }<br>
     *         
     * <br>
     * ������getField()�̖߂�l��A�������L�[��tagIndex�ɑ��݂��Ȃ��ꍇ�A<br>
     * ������0�̃C���f�b�N�X��t�����������񂪕ԋp����邱�Ƃ��m�F����B<br>
     * tagIndex�ɃL�[�������A�l��0�̃Z�b�g���i�[����邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testIndexResolve01() throws Exception {
        // �O����
        // ����
        String element = "abc";

        // �O�����
        HashMap<String, Integer> tagIndex = new HashMap<String, Integer>();
        UTUtil.setPrivateField(this.errorReporter, "tagIndex", tagIndex);
        Stack<String> stack = new Stack<String>();
        UTUtil.setPrivateField(this.errorReporter, "tagStack", stack);

        // �e�X�g���{
        String result = this.errorReporter.indexResolve(element);

        // ����
        // �߂�l
        assertEquals("abc[0]", result);
        
        // ��ԕω�
        HashMap map = (HashMap) UTUtil.getPrivateField(this.errorReporter,
                "tagIndex");
        assertEquals(1, map.size());
        assertEquals(0, map.get("abc"));

    }

    /**
     * testIndexResolve02()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA,C
     * <br><br>
     * ���͒l�F(����) element:""<br>
     *         (���) this.tagIndex:���HashMap<br>
     *         (���) this.tagStack:���Stack<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) String:"[0]"<br>
     *         (��ԕω�) this.tagIndex:HashMap{<br>
     *                      ""=0<br>
     *                    }<br>
     *         
     * <br>
     * ������getField()�̖߂�l��A�������L�[��tagIndex�ɑ��݂��Ȃ��ꍇ�A<br>
     * ������0�̃C���f�b�N�X��t�����������񂪕ԋp����邱�Ƃ��m�F����B<br>
     * tagIndex�ɃL�[�������A�l��0�̃Z�b�g���i�[����邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testIndexResolve02() throws Exception {
        // �O����
        // ����
        String element = "";

        // �O�����
        HashMap<String, Integer> tagIndex = new HashMap<String, Integer>();
        UTUtil.setPrivateField(this.errorReporter, "tagIndex", tagIndex);
        Stack<String> stack = new Stack<String>();
        UTUtil.setPrivateField(this.errorReporter, "tagStack", stack);

        // �e�X�g���{
        String result = this.errorReporter.indexResolve(element);

        // ����
        // �߂�l
        assertEquals("[0]", result);
        
        // ��ԕω�
        HashMap resultTagIndex = (HashMap) UTUtil.getPrivateField(this.errorReporter,
                "tagIndex");
        assertEquals(1, resultTagIndex.size());
        assertEquals(0, resultTagIndex.get(""));

    }

    /**
     * testIndexResolve03()
     * <br><br>
     * 
     * (����n) 
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) element:"def"<br>
     *         (���) this.tagIndex:HashMap{<br>
     *                  "abc"=0,<br>
     *                  "abc[0].def"=0<br>
     *                }<br>
     *         (���) this.tagStack:Stack[0]{"abc[0]"}<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) String:"def[1]"<br>
     *         (��ԕω�) this.tagIndex:HashMap{<br>
     *                      "abc"=0,<br>
     *                      "abc[0].def"=1<br>
     *                    }<br>
     *         
     * <br>
     * ������getField()�̖߂�l��A�������L�[��tagIndex�ɑ��݂���ꍇ�A<br>
     * �����ɃC���f�b�N�X��t�����������񂪕ԋp����邱�Ƃ��m�F����B<br>
     * �߂�l�̃C���f�b�N�X���Y���L�[�̒l���C���N�������g�����l�ł��邱�Ƃ��m�F����B<br>
     * tagIndex�̊Y���L�[�̒l���C���N�������g����邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testIndexResolve03() throws Exception {
        // �O����
        // ����
        String element = "def";

        // �O�����
        HashMap<String, Integer> tagIndex = new HashMap<String, Integer>();
        tagIndex.put("abc", 0);
        tagIndex.put("abc[0].def", 0);
        UTUtil.setPrivateField(this.errorReporter, "tagIndex", tagIndex);
        Stack<String> stack = new Stack<String>();
        stack.push("abc[0]");
        UTUtil.setPrivateField(this.errorReporter, "tagStack", stack);

        // �e�X�g���{
        String result = this.errorReporter.indexResolve(element);

        // ����
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
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) element:"ghi"<br>
     *         (���) this.tagIndex:HashMap{<br>
     *                  "abc"=0,<br>
     *                  "abc[0].def"=0<br>
     *                  "abc[0].def[0].ghi"=1<br>
     *                }<br>
     *         (���) this.tagStack:Stack[0]{"abc[0]"}<br>
     *                Stack[1]{"def[0]"}<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) String:"ghi[2]"<br>
     *         (��ԕω�) this.tagIndex:HashMap{<br>
     *                      "abc"=0,<br>
     *                      "abc[0].def"=0<br>
     *                      "abc[0].def[0].ghi"=2<br>
     *                    }<br>
     *         
     * <br>
     * ������getField()�̖߂�l��A�������L�[��tagIndex�ɑ��݂���ꍇ�A<br>
     * �����ɃC���f�b�N�X��t�����������񂪕ԋp����邱�Ƃ��m�F����B<br>
     * �߂�l�̃C���f�b�N�X���Y���L�[�̒l���C���N�������g�����l�ł��邱�Ƃ��m�F����B <br>
     * tagIndex�̊Y���L�[�̒l���C���N�������g����邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testIndexResolve04() throws Exception {
        // �O����
        // ����
        String element = "ghi";

        // �O�����
        HashMap<String, Integer> tagIndex = new HashMap<String, Integer>();
        tagIndex.put("abc", 0);
        tagIndex.put("abc[0].def", 1);
        tagIndex.put("abc[0].def[0].ghi", 2);
        UTUtil.setPrivateField(this.errorReporter, "tagIndex", tagIndex);
        Stack<String> stack = new Stack<String>();
        stack.push("abc[0]");
        stack.push("def[0]");
        UTUtil.setPrivateField(this.errorReporter, "tagStack", stack);

        // �e�X�g���{
        String result = this.errorReporter.indexResolve(element);

        // ����
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
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) key:"cvc-type.3.1.3"<br>
     *         (����) options:options[0]{"abc"}<br>
     *                options[1]{"def"}<br>
     *                options[2]{"ghi"}<br>
     *         (���) this.errorMessages:���ErrorMessages�C���X�^���X<br>
     *         (���) tmpErrorMessage:not null<br>
     *         (���) this.tagStack:not null<br>
     *         (���) this.DATATYPE_DATE:String[]{"date","time","dateTime"}<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) this.errorMessages:���ErrorMessages�C���X�^���X<br>
     *         
     * <br>
     * ����key��"cvc-type.3.1.3"�̏ꍇ�A�����s���Ȃ����Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testAddErrorMessage01() throws Exception {
        // �O����
        // ����
        String key = "cvc-type.3.1.3";
        String[] options = new String[] { "abc", "def", "ghi" };

        // �O�����
        ErrorMessage tmpErrorMessage = new ErrorMessage("error", "abc[0]");
        UTUtil.setPrivateField(this.errorReporter, "tmpErrorMessage",
                tmpErrorMessage);

        Stack<String> stack = new Stack<String>();
        stack.push("abc[0]");
        UTUtil.setPrivateField(this.errorReporter, "tagStack", stack);

        // �e�X�g���{
        this.errorReporter.addErrorMessage(key, options);

        // ����
        ErrorMessages resultErrorMessages = 
            (ErrorMessages)UTUtil.getPrivateField(this.errorReporter, "errorMessages");
        assertFalse(resultErrorMessages.hasErrorMessage());

    }

    /**
     * testAddErrorMessage02()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA,C
     * <br><br>
     * ���͒l�F(����) key:"cvc-attribute.3"<br>
     *         (����) options:options[0]{"abc"}<br>
     *                options[1]{"def"}<br>
     *                options[2]{"ghi"}<br>
     *         (���) this.errorMessages:���ErrorMessages�C���X�^���X<br>
     *         (���) tmpErrorMessage:null<br>
     *         (���) this.tagStack:not null<br>
     *         (���) this.DATATYPE_DATE:String[]{"date","time","dateTime"}<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) this.errorMessages:���ErrorMessages�C���X�^���X<br>
     *         
     * <br>
     * ����key��"cvc-attribute.3"�̏ꍇ�A�����s���Ȃ����Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testAddErrorMessage02() throws Exception {
        // �O����
        // ����
        String key = "cvc-attribute.3";
        String[] options = new String[] { "abc", "def", "ghi" };

        // �O�����
        ErrorMessage tmpErrorMessage = null;
        UTUtil.setPrivateField(this.errorReporter, "tmpErrorMessage",
                tmpErrorMessage);

        Stack<String> stack = new Stack<String>();
        stack.push("abc[0]");
        UTUtil.setPrivateField(this.errorReporter, "tagStack", stack);

        // �e�X�g���{
        this.errorReporter.addErrorMessage(key, options);

        // ����
        ErrorMessages resultErrorMessages = 
            (ErrorMessages)UTUtil.getPrivateField(this.errorReporter, "errorMessages");
        assertFalse(resultErrorMessages.hasErrorMessage());

    }

    /**
     * testAddErrorMessage03()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) key:"cvc-attribute.3"<br>
     *         (����) options:options[0]{"abc"}<br>
     *                options[1]{"def"}<br>
     *                options[2]{"ghi"}<br>
     *         (���) this.errorMessages:���ErrorMessages�C���X�^���X<br>
     *         (���) tmpErrorMessage:ErrorMessage�C���X�^���X{<br>
     *                  field= abc[0]<br>
     *                }<br>
     *         (���) this.tagStack:not null<br>
     *         (���) this.DATATYPE_DATE:String[]{"date","time","dateTime"}<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) this.errorMessages:���ErrorMessages�C���X�^���X<br>
     *         (��ԕω�) this.tmpErrorMessage:ErrorMessage�C���X�^���X{<br>
     *                      field = "abc[0].def"<br>
     *                    }<br>
     *         
     * <br>
     * ����key��"cvc-attribute.3"�̏ꍇ�A�����s���Ȃ����Ƃ��m�F����B<br>
     * tmpErrorMessage�C���X�^���X����������Ă���ꍇ�AtmpErrorMessage��field�����̖�����options[1]�̒l���t������邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testAddErrorMessage03() throws Exception {
        // �O����
        // ����
        String key = "cvc-attribute.3";
        String[] options = new String[] { "abc", "def", "ghi" };

        // �O�����
        ErrorMessage tmpErrorMessage = new ErrorMessage("error", "abc[0]");
        UTUtil.setPrivateField(this.errorReporter, "tmpErrorMessage",
                tmpErrorMessage);

        Stack<String> stack = new Stack<String>();
        stack.push("abc[0]");
        UTUtil.setPrivateField(this.errorReporter, "tagStack", stack);

        // �e�X�g���{
        this.errorReporter.addErrorMessage(key, options);

        // ����
        // �����s���Ă��Ȃ��i�G���[���i�[����Ă��Ȃ��j���Ƃ̊m�F
        ErrorMessages resultErrorMessages = 
            (ErrorMessages)UTUtil.getPrivateField(this.errorReporter, "errorMessages");
        assertFalse(resultErrorMessages.hasErrorMessage());

        // tmpErrorMessage�̊m�F
        ErrorMessage resultTmpErrorMessage = (ErrorMessage) UTUtil
                .getPrivateField(this.errorReporter, "tmpErrorMessage");
        assertEquals("abc[0].def", resultTmpErrorMessage.getField());

    }

    /**
     * testAddErrorMessage04()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) key:"cvc-datatype-valid.1.2.1"<br>
     *         (����) options:options[0]{"abc"}<br>
     *                options[1]{"boolean"}<br>
     *                options[2]{"ghi"}<br>
     *         (���) this.errorMessages:���ErrorMessages�C���X�^���X<br>
     *         (���) tmpErrorMessage:not null<br>
     *         (���) this.tagStack:Stack[0]{"abc[0]"}<br>
     *         (���) this.DATATYPE_DATE:String[]{"date","time","dateTime"}<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) this.errorMessages:ErrorMessage�C���X�^���X{<br>
     *                      key = "typeMismatch.boolean"<br>
     *                      field = "abc[0]"<br>
     *                      replaceValues[0]{"abc"}<br>
     *                      replaceValues[1]{"boolean"}<br>
     *                      replaceValues[2]{"ghi"}<br>
     *                    }<br>
     *         
     * <br>
     * ����key��"cvc-datatype-valid.1.2.1"�̏ꍇ�Akey������"typeMismatch.boolean"�AreplaceValues�����Ɉ���options���i�[���ꂽErrorMessage�C���X�^���X����������邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testAddErrorMessage04() throws Exception {
        // �O����
        // ����
        String key = "cvc-datatype-valid.1.2.1";
        String[] options = new String[] { "abc", "boolean", "ghi" };

        // �O�����
        ErrorMessage tmpErrorMessage = new ErrorMessage("error", "abc[0]");
        UTUtil.setPrivateField(this.errorReporter, "tmpErrorMessage",
                tmpErrorMessage);

        Stack<String> stack = new Stack<String>();
        stack.push("abc[0]");
        UTUtil.setPrivateField(this.errorReporter, "tagStack", stack);

        // �e�X�g���{
        this.errorReporter.addErrorMessage(key, options);

        // ����
        // ErrorMessage���ǉ�����Ă��邱�Ƃ̊m�F
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
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) key:"cvc-datatype-valid.1.2.1"<br>
     *         (����) options:options[0]{"abc"}<br>
     *                options[1]{"dateTime"}<br>
     *                options[2]{"ghi"}<br>
     *         (���) this.errorMessages:���ErrorMessages�C���X�^���X<br>
     *         (���) tmpErrorMessage:not null<br>
     *         (���) this.tagStack:Stack[0]{"abc[0]"}<br>
     *         (���) this.DATATYPE_DATE:String[]{"date","time","dateTime"}<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) this.errorMessages:ErrorMessage�C���X�^���X{<br>
     *                      key = "typeMismatch.date"<br>
     *                      field = "abc[0]"<br>
     *                      replaceValues[0]{"abc"}<br>
     *                      replaceValues[1]{"date"}<br>
     *                      replaceValues[2]{"ghi"}<br>
     *                    }<br>
     *         
     * <br>
     * ����key��"cvc-datatype-valid.1.2.1"�̏ꍇ�Akey������"typeMismatch.date"�AreplaceValues�����Ɉ���options���i�[���ꂽErrorMessage�C���X�^���X����������邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testAddErrorMessage05() throws Exception {
        // �O����
        // ����
        String key = "cvc-datatype-valid.1.2.1";
        String[] options = new String[] { "abc", "dateTime", "ghi" };

        // �O�����
        ErrorMessage tmpErrorMessage = new ErrorMessage("error", "abc[0]");
        UTUtil.setPrivateField(this.errorReporter, "tmpErrorMessage",
                tmpErrorMessage);

        Stack<String> stack = new Stack<String>();
        stack.push("abc[0]");
        UTUtil.setPrivateField(this.errorReporter, "tagStack", stack);

        // �e�X�g���{
        this.errorReporter.addErrorMessage(key, options);

        // ����
        // ErrorMessage���ǉ�����Ă��邱�Ƃ̊m�F
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
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) key:"cvc-datatype-valid.1.2.1"<br>
     *         (����) options:options[0]{"abc"}<br>
     *                options[1]{"integer"}<br>
     *                options[2]{"ghi"}<br>
     *         (���) this.errorMessages:���ErrorMessages�C���X�^���X<br>
     *         (���) tmpErrorMessage:not null<br>
     *         (���) this.tagStack:Stack[0]{"abc[0]"}<br>
     *         (���) this.DATATYPE_DATE:String[]{"date","time","dateTime"}<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) this.errorMessages:ErrorMessage�C���X�^���X{<br>
     *                      key = "typeMismatch.number"<br>
     *                      field = "abc[0]"<br>
     *                      replaceValues[0]{"abc"}<br>
     *                      replaceValues[1]{"integer"}<br>
     *                      replaceValues[2]{"ghi"}<br>
     *                    }<br>
     *         
     * <br>
     * ����key��"cvc-datatype-valid.1.2.1"�̏ꍇ�Akey������"typeMismatch.integer"�AreplaceValues�����Ɉ���options���i�[���ꂽErrorMessage�C���X�^���X����������邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testAddErrorMessage06() throws Exception {
        // �O����
        // ����
        String key = "cvc-datatype-valid.1.2.1";
        String[] options = new String[] { "abc", "integer", "ghi" };

        // �O�����
        ErrorMessage tmpErrorMessage = new ErrorMessage("error", "abc[0]");
        UTUtil.setPrivateField(this.errorReporter, "tmpErrorMessage",
                tmpErrorMessage);

        Stack<String> stack = new Stack<String>();
        stack.push("abc[0]");
        UTUtil.setPrivateField(this.errorReporter, "tagStack", stack);

        // �e�X�g���{
        this.errorReporter.addErrorMessage(key, options);

        // ����
        // ErrorMessage���ǉ�����Ă��邱�Ƃ̊m�F
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
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) key:"cvc-datatype-valid.1.2.3"<br>
     *         (����) options:options[0]{"abc"}<br>
     *                options[1]{"integerAllowEmpty"}<br>
     *                options[2]{"ghi"}<br>
     *         (���) this.errorMessages:���ErrorMessages�C���X�^���X<br>
     *         (���) tmpErrorMessage:not null<br>
     *         (���) this.tagStack:Stack[0]{"abc[0]"}<br>
     *         (���) this.DATATYPE_DATE:String[]{"date","time","dateTime"}<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) this.errorMessages:ErrorMessage�C���X�^���X{<br>
     *                      key = "typeMismatch.number"<br>
     *                      field = "abc[0]"<br>
     *                      replaceValues[0]{"abc"}<br>
     *                      replaceValues[1]{"integer"}<br>
     *                    }<br>
     *         
     * <br>
     * ����key��"cvc-datatype-valid.1.2.3"�Aoptions[1]��"integerAllowEmpty"�̏ꍇ�Akey������"typeMismatch.integer"�AreplaceValues�����Ɉ���options�̃C���f�b�N�X2�ȍ~���폜���ꂽ�z�񂪊i�[���ꂽ�AErrorMessage�C���X�^���X����������邱�Ƃ��m�F����B<br>
     * ErrorMessages��replaceValues�z��̌���2�ł��邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testAddErrorMessage07() throws Exception {
        // �O����
        // ����
        String key = "cvc-datatype-valid.1.2.3";
        String[] options = new String[] { "abc", "integerAllowEmpty", "ghi" };

        // �O�����
        ErrorMessage tmpErrorMessage = new ErrorMessage("error", "abc[0]");
        UTUtil.setPrivateField(this.errorReporter, "tmpErrorMessage",
                tmpErrorMessage);

        Stack<String> stack = new Stack<String>();
        stack.push("abc[0]");
        UTUtil.setPrivateField(this.errorReporter, "tagStack", stack);

        // �e�X�g���{
        this.errorReporter.addErrorMessage(key, options);

        // ����
        // ErrorMessage���ǉ�����Ă��邱�Ƃ̊m�F
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
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) key:"cvc-maxInclusive-valid"<br>
     *         (����) options:options[0]{"abc"}<br>
     *                options[1]{"integer"}<br>
     *                options[2]{"ghi"}<br>
     *         (���) this.errorMessages:���ErrorMessages�C���X�^���X<br>
     *         (���) tmpErrorMessage:not null<br>
     *         (���) this.tagStack:Stack[0]{"abc[0]"}<br>
     *         (���) this.DATATYPE_DATE:String[]{"date","time","dateTime"}<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) this.errorMessages:ErrorMessage�C���X�^���X{<br>
     *                      key = "typeMismatch.numberMaxRange"<br>
     *                      field = "abc[0]"<br>
     *                      replaceValues[0]{"abc"}<br>
     *                      replaceValues[1]{"integer"}<br>
     *                      replaceValues[2]{"ghi"}<br>
     *                    }<br>
     *         
     * <br>
     * ����key��"cvc-maxInclusive-valid"�̏ꍇ�Akey������"typeMismatch.numberMaxRange"���i�[���ꂽErrorMessage�C���X�^���X����������邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testAddErrorMessage08() throws Exception {
        // �O����
        // ����
        String key = "cvc-maxInclusive-valid";
        String[] options = new String[] { "abc", "integer", "ghi" };

        // �O�����
        ErrorMessage tmpErrorMessage = new ErrorMessage("error", "abc[0]");
        UTUtil.setPrivateField(this.errorReporter, "tmpErrorMessage",
                tmpErrorMessage);

        Stack<String> stack = new Stack<String>();
        stack.push("abc[0]");
        UTUtil.setPrivateField(this.errorReporter, "tagStack", stack);

        // �e�X�g���{
        this.errorReporter.addErrorMessage(key, options);

        // ����
        // ErrorMessage���ǉ�����Ă��邱�Ƃ̊m�F
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
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) key:"cvc-minInclusive-valid"<br>
     *         (����) options:options[0]{"abc"}<br>
     *                options[1]{"integer"}<br>
     *                options[2]{"ghi"}<br>
     *         (���) this.errorMessages:���ErrorMessages�C���X�^���X<br>
     *         (���) tmpErrorMessage:not null<br>
     *         (���) this.tagStack:Stack[0]{"abc[0]"}<br>
     *         (���) this.DATATYPE_DATE:String[]{"date","time","dateTime"}<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) this.errorMessages:ErrorMessage�C���X�^���X{<br>
     *                      key = "typeMismatch.numberMinRange"<br>
     *                      field = "abc[0]"<br>
     *                      replaceValues[0]{"abc"}<br>
     *                      replaceValues[1]{"integer"}<br>
     *                      replaceValues[2]{"ghi"}<br>
     *                    }<br>
     *         
     * <br>
     * ����key��"cvc-minInclusive-valid"�̏ꍇ�Akey������"typeMismatch.numberMinRange"���i�[���ꂽErrorMessage�C���X�^���X����������邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testAddErrorMessage09() throws Exception {
        // �O����
        // ����
        String key = "cvc-minInclusive-valid";
        String[] options = new String[] { "abc", "integer", "ghi" };

        // �O�����
        ErrorMessage tmpErrorMessage = new ErrorMessage("error", "abc[0]");
        UTUtil.setPrivateField(this.errorReporter, "tmpErrorMessage",
                tmpErrorMessage);

        Stack<String> stack = new Stack<String>();
        stack.push("abc[0]");
        UTUtil.setPrivateField(this.errorReporter, "tagStack", stack);

        // �e�X�g���{
        this.errorReporter.addErrorMessage(key, options);

        // ����
        // ErrorMessage���ǉ�����Ă��邱�Ƃ̊m�F
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
     * (����n)
     * <br>
     * �ϓ_�FA,G
     * <br><br>
     * ���͒l�F(����) key:"cvc-abc"<br>
     *         (����) options:not null<br>
     *         (���) this.errorMessages:���ErrorMessages�C���X�^���X<br>
     *         (���) tmpErrorMessage:not null<br>
     *         (���) this.tagStack:not null<br>
     *         (���) this.DATATYPE_DATE:String[]{"date","time","dateTime"}<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) ��O:UnknownXMLDataException<br>
     *         (��ԕω�) ���O���x��:���O���x��:error<br>
     *                    xml data is invalid.<br>
     *         
     * <br>
     * ����key�̕�����"cvc-"����n�܂邪�A���L�̕�����łȂ��ꍇ�A��O���X���[���A���O���o�͂��邱�Ƃ��m�F����B<br>
     * �Ecvc-datatype-valid.1.2.1<br>
     * �Ecvc-datatype-valid.1.2.3<br>
     * �Ecvc-maxInclusive-valid<br>
     * �Ecvc-minInclusive-valid
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testAddErrorMessage10() throws Exception {
        // �O����
        // ����
        String key = "cvc-abc";
        String[] options = new String[] { "abc", "integer", "ghi" };

        // �O�����
        ErrorMessage tmpErrorMessage = new ErrorMessage("error", "abc[0]");
        UTUtil.setPrivateField(this.errorReporter, "tmpErrorMessage",
                tmpErrorMessage);

        Stack<String> stack = new Stack<String>();
        stack.push("abc[0]");
        UTUtil.setPrivateField(this.errorReporter, "tagStack", stack);

        // �e�X�g���{
        try {
            this.errorReporter.addErrorMessage(key, options);
            fail();
        } catch (UnknownXMLDataException e) {
            // ����
            // ���O�m�F
            String message = "xml data is invalid.";
            assertTrue(LogUTUtil.checkError(message));
        }
    }

    /**
     * testAddErrorMessage11()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA,G
     * <br><br>
     * ���͒l�F(����) key:"abc"<br>
     *         (����) options:not null<br>
     *         (���) this.errorMessages:���ErrorMessages�C���X�^���X<br>
     *         (���) tmpErrorMessage:not null<br>
     *         (���) this.tagStack:not null<br>
     *         (���) this.DATATYPE_DATE:String[]{"date","time","dateTime"}<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalSchemaDefinitionException<br>
     *         (��ԕω�) ���O���x��:���O���x��:error<br>
     *                    schema is invalid.<br>
     *         
     * <br>
     * ����key�̕����񂪃n���h�����O����G���[�R�[�h�ɊY�����Ȃ��ꍇ�A��O���X���[���A���O���o�͂��邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testAddErrorMessage11() throws Exception {
        // �O����
        // ����
        String key = "abc";
        String[] options = new String[] { "abc", "integer", "ghi" };

        // �O�����
        ErrorMessage tmpErrorMessage = new ErrorMessage("error", "abc[0]");
        UTUtil.setPrivateField(this.errorReporter, "tmpErrorMessage",
                tmpErrorMessage);

        Stack<String> stack = new Stack<String>();
        stack.push("abc[0]");
        UTUtil.setPrivateField(this.errorReporter, "tagStack", stack);

        // �e�X�g���{
        try {
            this.errorReporter.addErrorMessage(key, options);
            fail();
        } catch (IllegalSchemaDefinitionException e) {
            // ����
            // ���O�m�F
            String message = "schema is invalid.";
            assertTrue(LogUTUtil.checkError(message));
        }
    }

    /**
     * testErrorLog01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA,D
     * <br><br>
     * ���͒l�F(����) key:cvc-type.3.1.3<br>
     *         (����) options:���Object[]<br>
     *         (���) XMLSchemaMessages.properties:XMLSchemaMessages.properties�t�@�C�����擾�ł���<br>
     *         (���) this.tagStack:Stack[0]{"abc[0]"}<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) ���O:�X�L�[�}�G���[[]------------------------<br>
     *                    xpath=abc[0]<br>
     *                    getMessage=cvc-type.3.1.3: The value '00a1' of element abc' is not valid.<br>
     *                    key=cvc-type.3.1.3<br>
     *                    -----------------------------------------<br>
     *         
     * <br>
     * ����key,options�ƁA�O�����tagStack�̒l�����f���ꂽ���O���o�͂���邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testErrorLog01() throws Exception {
        // �O����
        // ����
        String key = "cvc-type.3.1.3";
        Object[] arg = new Object[] {};

        // �O�����
        Stack<String> stack = new Stack<String>();
        String xpath = "abc[0]";
        stack.push(xpath);
        UTUtil.setPrivateField(this.errorReporter, "tagStack", stack);

        // �e�X�g���{
        this.errorReporter.errorLog(key, arg);

        // ����
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
     * (����n)
     * <br>
     * �ϓ_�FA,D
     * <br><br>
     * ���͒l�F(����) key:cvc-type.3.1.3<br>
     *         (����) options:Object[0]{"123"}<br>
     *         (���) XMLSchemaMessages.properties:XMLSchemaMessages.properties�t�@�C�����擾�ł���<br>
     *         (���) this.tagStack:Stack[0]{"abc[0]"}<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) ���O:�X�L�[�}�G���[[]------------------------<br>
     *                    xpath=abc[0]<br>
     *                    getMessage=cvc-type.3.1.3: The value '123' of element '{1}' is not valid.<br>
     *                    key=cvc-type.3.1.3<br>
     *                    arg[0]=123<br>
     *                    -----------------------------------------<br>
     *         
     * <br>
     * ����key,options�ƁA�O�����tagStack�̒l�����f���ꂽ���O���o�͂���邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testErrorLog02() throws Exception {
        // �O����
        // ����
        String key = "cvc-type.3.1.3";
        Object[] arg = new Object[] { "123" };

        // �O�����
        Stack<String> stack = new Stack<String>();
        String xpath = "abc[0]";
        stack.push(xpath);
        UTUtil.setPrivateField(this.errorReporter, "tagStack", stack);

        // �e�X�g���{
        this.errorReporter.errorLog(key, arg);

        // ����
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
     * (����n)
     * <br>
     * �ϓ_�FA,D
     * <br><br>
     * ���͒l�F(����) key:cvc-type.3.1.3<br>
     *         (����) options:Object[0]{"123"}<br>
     *                Object[1]{"456"}<br>
     *                Object[2]{"789"}<br>
     *         (���) XMLSchemaMessages.properties:XMLSchemaMessages.properties�t�@�C�����擾�ł���<br>
     *         (���) this.tagStack:Stack[0]{"abc[0]"}<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) ���O:�X�L�[�}�G���[[]------------------------<br>
     *                    xpath=abc[0]<br>
     *                    getMessage=cvc-type.3.1.3: The value '123' of element '456' is not valid.<br>
     *                    key=cvc-type.3.1.3<br>
     *                    arg[0]=123<br>
     *                    arg[1]=456<br>
     *                    arg[1]=789<br>
     *                    -----------------------------------------<br>
     *         
     * <br>
     * ����key,options�ƁA�O�����tagStack�̒l�����f���ꂽ���O���o�͂���邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testErrorLog03() throws Exception {
        // �O����
        // ����
        String key = "cvc-type.3.1.3";
        Object[] arg = new Object[] { "123", "456", "789" };

        // �O�����
        Stack<String> stack = new Stack<String>();
        String xpath = "abc[0]";
        stack.push(xpath);
        UTUtil.setPrivateField(this.errorReporter, "tagStack", stack);

        // �e�X�g���{
        this.errorReporter.errorLog(key, arg);

        // ����
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
     * (�ُ�n)
     * <br>
     * �ϓ_�FA,C,G
     * <br><br>
     * ���͒l�F(����) key:���݂��Ȃ��R�[�h<br>
     *         (����) options:Object[0]{null}<br>
     *         (���) XMLSchemaMessages.properties:XMLSchemaMessages.properties�t�@�C�����擾�ł���<br>
     *         (���) this.tagStack:Stack[0]{"abc[0]"}<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) ���O:�X�L�[�}�G���[[]------------------------<br>
     *                    xpath=abc[0]<br>
     *                    getMessage=Can't find resource for bundle java.util.PropertyResourceBundle, key abc<br>
     *                    key=abc<br>
     *                    arg[0]=123<br>
     *                    arg[1]=456<br>
     *                    arg[2]=789<br>
     *                    -----------------------------------------<br>
     *         
     * <br>
     * ����key���v���p�e�B�ɑ��݂��Ȃ��ꍇ�A���O�Ƀv���p�e�B��������Ȃ��������Ƃ��������b�Z�[�W���o�͂���邱�Ƃ��m�F����B<br>
     * ����key,options�ƁA�O�����tagStack�̒l�����f���ꂽ���O���o�͂���邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testErrorLog04() throws Exception {
        // �O����
        // ����
        String key = "abc";
        Object[] arg = new Object[] {null};

        // �O�����
        Stack<String> stack = new Stack<String>();
        String xpath = "abc[0]";
        stack.push(xpath);
        UTUtil.setPrivateField(this.errorReporter, "tagStack", stack);

        // �e�X�g���{
        this.errorReporter.errorLog(key, arg);

        // ����
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
     * (�ُ�n)
     * <br>
     * �ϓ_�FA,G
     * <br><br>
     * ���͒l�F(����) key:not null<br>
     *         (����) options:Object[0]{null}<br>
     *         (���) XMLSchemaMessages.properties:XMLSchemaMessages.properties�t�@�C�����擾�ł��Ȃ�<br>
     *         (���) this.tagStack:Stack[0]{"abc[0]"}<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) ���O:�X�L�[�}�G���[[]------------------------<br>
     *                    xpath=abc[0]<br>
     *                    getMessage=Can't find bundle for base name XMLSchemaMessages, locale ja_JP<br>
     *                    key=cvc-type.3.1.3<br>
     *                    arg[0]=123<br>
     *                    arg[1]=456<br>
     *                    arg[2]=789<br>
     *                    -----------------------------------------<br>
     *         
     * <br>
     * �v���p�e�B�t�@�C�����擾�ł��Ȃ��ꍇ�A���O�Ƀ��\�[�X���擾�ł��Ȃ��������Ƃ��������b�Z�[�W���o�͂���邱�Ƃ��m�F����B<br>
     * ����key,options�ƁA�O�����tagStack�̒l�����f���ꂽ���O���o�͂���邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testErrorLog05() throws Exception {
        
        // �������@���̃e�X�g�P�[�X�͕s�v�Ȃ��߁A�폜����@������
        // xerces�Ŏg�p����v���p�e�B�t�@�C����xerces�Œ񋟂��Ă���jar�t�@�C����
        // ��������Ă��邽�߁A�v���p�e�B�t�@�C�����擾�ł��Ȃ��P�[�X�͑��݂��Ȃ��B
        
        
//        // �O����
//        // ����
//        String key = "cvc-type.3.1.3";
//        Object[] arg = new Object[] { "123", "456", "789" };
//
//        // �v���p�e�B�t�@�C���̃t�@�C������ύX����i�v���p�e�B�t�@�C���̎擾�����s�����邽�߁j
//        URL url = Thread.currentThread().getContextClassLoader().getResource(
//                "XMLSchemaMessages.properties");
//        File xercesProperties = new File(url.getFile());
//        File tmp = new File(url.getFile() + ".tmp");
//        boolean bol = xercesProperties.renameTo(tmp);
//
//        // ResourceBundle�̃L���b�V�����폜����i�v���p�e�B�t�@�C���̎擾�����s�����邽�߁j�B
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
//        // �O�����
//        String xpath = "abc[0]";
//        Stack<String> stack = new Stack<String>();
//        stack.push(xpath);
//        UTUtil.setPrivateField(this.errorReporter, "tagStack", stack);
//
//        // �e�X�g���{
//        this.errorReporter.errorLog(key, arg);
//        
//        // ����
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
//        // �v���p�e�B�t�@�C���̃t�@�C���������ɖ߂�
//        tmp.renameTo(xercesProperties);
    }

    /**
     * testErrorLog06()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA,D
     * <br><br>
     * ���͒l�F(����) key:cvc-type.3.1.3<br>
     *         (����) options:���Object[]<br>
     *         (���) XMLSchemaMessages.properties:XMLSchemaMessages.properties�t�@�C�����擾�ł���<br>
     *         (���) this.tagStack:��̃X�^�b�N<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) ���O:�X�L�[�}�G���[[]------------------------<br>
     *                    xpath=<br>
     *                    getMessage=cvc-type.3.1.3: The value '00a1' of element abc' is not valid.<br>
     *                    key=cvc-type.3.1.3<br>
     *                    -----------------------------------------<br>
     *         
     * <br>
     * ����key,options�ƁA�O��������tagStack�̒l�����f���ꂽ���O���o�͂���邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testErrorLog06() throws Exception {
        // �O����
        // ����
        String key = "cvc-type.3.1.3";
        Object[] arg = new Object[] {};

        // �O�����
        Stack<String> stack = new Stack<String>();
        UTUtil.setPrivateField(this.errorReporter, "tagStack", stack);

        // �e�X�g���{
        this.errorReporter.errorLog(key, arg);

        // ����
        CheckLogObj resultLog = getErrorLog();
        assertNotNull(resultLog);        
        assertNull(resultLog.xpath);
        String message = "cvc-type.3.1.3: The value '{1}' of element '{0}' is not valid.";
        assertEquals(message, resultLog.message);
        assertEquals(key, resultLog.key);
        
    }

    /**
     * ���O�̒��g���`�F�b�N���郁�\�b�h�B
     * <br>
     * ���O�̌`�����s���ȏꍇ�Anul����ԋp����B
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
     * ���O����͂������ʂ�ێ�����I�u�W�F�N�g
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
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(���) this.tagStack:not null<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) Stack:not null<br>
     *         
     * <br>
     * �����̒l���������ԋp����邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetTagStack01() throws Exception {
        // �O���� 
        Stack<String> tagStack = new Stack<String>();
        UTUtil.setPrivateField(this.errorReporter, "tagStack", tagStack);

        // �e�X�g���{
        Stack result = this.errorReporter.getTagStack();

        // ����
        assertSame(tagStack, result);

    }

}
