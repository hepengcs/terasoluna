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
 * �N���X�̃u���b�N�{�b�N�X�e�X�g�B
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4>
 * ��͒���XML�̗v�f����XMLErrorReporterEx�̃X�^�b�N�Ɋi�[���邽�߂ɁAXMLSchemaValidator�̊g�����s�����N���X�B<br>
 * �E�O�����<br>
 * ���̃N���X�̓p�[�T����Ăт�����邱�Ƃ��O��ł���AstartElement�EendElement���\�b�h�̈���element�Aattributes�Aaugs��null���i�[����邱�Ƃ͂Ȃ��B<br>
 * �E�K��startElement�̌��endElement���Ăяo�����̂ŁAendElement���s����XMLErrorReporterEx�̃X�^�b�N����ɂȂ��Ă����Ԃ͑��݂��Ȃ��B
 * <p>
 * 
 * @see jp.terasoluna.fw.oxm.xsd.xerces.XMLSchemaValidatorEx
 */
public class XMLSchemaValidatorExTest extends TestCase {

    /**
     * ���̃e�X�g�P�[�X�����s����ׂ� GUI �A�v���P�[�V�������N������B
     * 
     * @param args java �R�}���h�ɐݒ肳�ꂽ�p�����[�^
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(XMLSchemaValidatorExTest.class);
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
    public XMLSchemaValidatorExTest(String name) {
        super(name);
    }

    /**
     * testXMLSchemaValidatorEx01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA,C
     * <br><br>
     * ���͒l�F(����) reporeter:null<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) this.reporter:null<br>
     *         
     * <br>
     * �����������������ɐݒ肳��邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testXMLSchemaValidatorEx01() throws Exception {
        // �O����
        XMLErrorReporterEx errorReporter = null;

        // �e�X�g���{
        XMLSchemaValidatorEx schemaValidator = new XMLSchemaValidatorEx(
                errorReporter);

        // ����
        assertNull(UTUtil.getPrivateField(schemaValidator,
                "reporter"));
    }

    /**
     * testXMLSchemaValidatorEx02()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) reporeter:not null<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) this.reporter:not null<br>
     *         
     * <br>
     * �����������������ɐݒ肳��邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testXMLSchemaValidatorEx02() throws Exception {
        // �O����
        XMLErrorReporterEx errorReporter = new XMLErrorReporterEx(null);

        // �e�X�g���{
        XMLSchemaValidatorEx schemaValidator = new XMLSchemaValidatorEx(
                errorReporter);

        // ����
        assertSame(errorReporter, UTUtil.getPrivateField(schemaValidator,
                "reporter"));

    }

    /**
     * testStartElement01()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FA,C,G
     * <br><br>
     * ���͒l�F(����) reporter:null<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) ��O:ErrorReporterNotFoundException<br>
     *         (��ԕω�) ���O:���O���x���Ferror<br>
     *                    ErrorReporterEx is not found.<br>
     *         
     * <br>
     * ����reporeter��null�̏ꍇ�A��O���X���[����邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testStartElement01() throws Exception {
        // �O����
        XMLSchemaValidatorEx_XMLErrorReporterExStub01 errorReporter = null;
        XMLSchemaValidatorEx schemaValidator = new XMLSchemaValidatorEx(
                errorReporter);

        // ����
        QName qname = new QName();
        XMLAttributes attributes = new XMLAttributesImpl();
        Augmentations augs = new AugmentationsImpl();

        // �e�X�g���{
        try {
            schemaValidator.startElement(qname, attributes, augs);
            fail();
        } catch (ErrorReporterNotFoundException e) {
            String message = "ErrorReporterEx is not found.";

            // ���O�m�F
            assertTrue(LogUTUtil.checkError(message));
        }

    }

    /**
     * testStartElement02()
     * <br><br>
     * 
     * (����n)
     * <br>A
     * <br><br>
     * ���͒l�F(����) element:not null<br>
     *         (����) attributes:not null<br>
     *         (����) augs:not null<br>
     *         (����) reporter:reporter�C���X�^���X{<br>
     *                  ���tagStack<br>
     *                }<br>
     *         (���) reporter.indexResolve():���\�b�h���Ăяo���ꂽ���Ƃ��m�F����B<br>
     *                �������n���ꂽ���Ƃ��m�F����B<br>
     *                <br>
     *                "sample-dto[0]"��ԋp����<br>
     *         (���) super.startElement():���\�b�h���Ăяo���ꂽ���Ƃ��m�F����B<br>
     *                �������n���ꂽ���Ƃ��m�F����B<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) this.reporter:reporter�C���X�^���X{<br>
     *                      tagStack[0]{"sample-dto[0]"}<br>
     *                    }<br>
     *         
     * <br>
     * reporter.indexResolve()���\�b�h�ŕԋp���ꂽ�����񂪁Areporter�C���X�^���X�̃X�^�b�N�Ƀv�b�V������邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testStartElement02() throws Exception {
        // �O����
        ErrorMessages errorMessages = new ErrorMessages();
        XMLSchemaValidatorEx_XMLErrorReporterExStub01 errorReporter = new XMLSchemaValidatorEx_XMLErrorReporterExStub01(
                errorMessages);
        XMLSchemaValidatorEx schemaValidator = new XMLSchemaValidatorEx(
                errorReporter);
        errorReporter.getTagStack().clear();

        // super.startElement���\�b�h�̌Ăяo���m�F�Ŏg�p����X�^�u
        XMLSchemaValidatorEx_DefaultXMLDocumentHandlerStub01 fDocumentHandler = new XMLSchemaValidatorEx_DefaultXMLDocumentHandlerStub01();
        UTUtil.setPrivateField(schemaValidator, "fDocumentHandler",
                fDocumentHandler);

        // ����
        QName qname = new QName();
        qname.rawname = "abc";
        XMLAttributes attributes = new XMLAttributesImpl();
        Augmentations augs = new AugmentationsImpl();

        // �e�X�g���{
        schemaValidator.startElement(qname, attributes, augs);

        // ����
        // indexResolve���\�b�h�Ɉ������n���ꂽ���Ƃ��m�F����
        assertEquals("abc", errorReporter.element);

        // �X�^�b�N��indexResolve�̖߂�l���i�[����邱�Ƃ��m�F����
        Stack stack = errorReporter.getTagStack();
        assertEquals(1, stack.size());
        assertEquals("sample-dto[0]", stack.pop());

        // super.startElement���\�b�h�̌Ăяo���m�F
        assertTrue(fDocumentHandler.isStartElement);

    }

    /**
     * testEndElement01()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FA,C,G
     * <br><br>
     * ���͒l�F(����) element:not null<br>
     *         (����) augs:not null<br>
     *         (���) this.reporter:null<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) ��O:ErrorReporterNotFoundException<br>
     *         (��ԕω�) ���O:���O���x���Ferror<br>
     *                    ErrorReporterEx is not found.<br>
     *         
     * <br>
     * ����reporeter��null�̏ꍇ�A��O���X���[����邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testEndElement01() throws Exception {
        // �O����
        XMLSchemaValidatorEx_XMLErrorReporterExStub01 errorReporter = null;
        XMLSchemaValidatorEx schemaValidator = new XMLSchemaValidatorEx(
                errorReporter);

        // ����
        QName qname = new QName();
        qname.rawname = "abc";
        Augmentations augs = new AugmentationsImpl();

        // �e�X�g���{
        try {
            schemaValidator.endElement(qname, augs);
            fail();
        } catch (ErrorReporterNotFoundException e) {
            String message = "ErrorReporterEx is not found.";

            // ���O�m�F
            assertTrue(LogUTUtil.checkError(message));
        }

    }

    /**
     * testEndElement02()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA,C
     * <br><br>
     * ���͒l�F(����) element:not null<br>
     *         (����) augs:not null<br>
     *         (���) this.reporter:reporter�C���X�^���X{<br>
     *                  tagStack[]<br>
     *                    "sample-dto[0]"<br>
     *                  }<br>
     *                }<br>
     *         (���) super.endElement():���\�b�h���Ăяo���ꂽ���Ƃ��m�F����B<br>
     *                �������n���ꂽ���Ƃ��m�F����B<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) this.reporter:reporter�C���X�^���X{<br>
     *                      ���tagStack<br>
     *                    }<br>
     *         
     * <br>
     * reporter�C���X�^���X�̃X�^�b�N����A�����񂪃|�b�v����邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testEndElement02() throws Exception {
        // �O����
        ErrorMessages errorMessages = new ErrorMessages();
        XMLErrorReporterEx errorReporter = new XMLErrorReporterEx(errorMessages);
        XMLSchemaValidatorEx schemaValidator = new XMLSchemaValidatorEx(
                errorReporter);
        
        // super.endElement���\�b�h�̌Ăяo���m�F�Ŏg�p����X�^�u
        XMLSchemaValidatorEx_DefaultXMLDocumentHandlerStub01 fDocumentHandler = new XMLSchemaValidatorEx_DefaultXMLDocumentHandlerStub01();
        UTUtil.setPrivateField(schemaValidator, "fDocumentHandler",
                fDocumentHandler);
        
        // �X�^�b�N�ɒl���i�[
        Stack<String> stack = new Stack<String>();
        stack.push("sample-dto[0]");
        UTUtil.setPrivateField(errorReporter, "tagStack", stack);
        
        // ����
        QName qname = new QName();
        qname.rawname = "abc";
        Augmentations augs = new AugmentationsImpl();

        // �e�X�g���{
        schemaValidator.endElement(qname, augs);
        
        // ����
        // �X�^�b�N����ɂȂ��Ă��邱�Ƃ��m�F
        Stack result = errorReporter.getTagStack();
        assertEquals(0, result.size());
        
        // super.endElement���\�b�h�̌Ăяo���m�F
        assertTrue(fDocumentHandler.isEndElement);

    }

}
