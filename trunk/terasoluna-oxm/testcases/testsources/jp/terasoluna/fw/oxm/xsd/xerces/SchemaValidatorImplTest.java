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
 * {@link jp.terasoluna.fw.oxm.xsd.xerces.SchemaValidatorImpl} �N���X�̃u���b�N�{�b�N�X�e�X�g�B
 * 
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4>
 * XML�f�[�^�̌`���`�F�b�N���s��SchemaValidator�����N���X�B<br>
 * �E�O�����<br>
 * setCommonParserProperty���\�b�h�̈���errorMessages��null�ɂȂ邱�Ƃ͂Ȃ��B<br>
 * setCommonParserProperty���\�b�h�̈���parser��null�ɂȂ邱�Ƃ͂Ȃ��B<br>
 * setCommonParserFeature���\�b�h�̈���parser��null�ɂȂ邱�Ƃ͂Ȃ��B<br>
 * <p>
 * 
 * @see jp.terasoluna.fw.oxm.xsd.xerces.SchemaValidatorImpl
 */
public class SchemaValidatorImplTest extends TestCase {

    /**
     * setUp���\�b�h�ɂ��A���񐶐������C���X�^���X
     */
    private SchemaValidatorImpl setUpValidator = null;
    
    /**
     * ���̃e�X�g�P�[�X�����s����ׂ�
     * GUI �A�v���P�[�V�������N������B
     * 
     * @param args java �R�}���h�ɐݒ肳�ꂽ�p�����[�^
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(SchemaValidatorImplTest.class);
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
        this.setUpValidator = new SchemaValidatorImpl();
        
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
    public SchemaValidatorImplTest(String name) {
        super(name);
    }

    /**
     * testSetCache01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) cache:true<br>
     *         (���) this.cache:false<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) this.cache:true<br>
     *         
     * <br>
     * �����������������ɐݒ肳��邱�Ƃ��m�F�ɂ���B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetCache01() throws Exception {
        // �O����
        setUpValidator.cache = false;

        // �e�X�g���{
        setUpValidator.setCache(true);
        
        // ����
        assertTrue(setUpValidator.cache);        

    }

    /**
     * testSetNamespace01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) namespaces:true<br>
     *         (���) this.namespaces:false<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) this.namespaces:true<br>
     *         
     * <br>
     * �����������������ɐݒ肳��邱�Ƃ��m�F�ɂ���B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetNamespace01() throws Exception {
        // �O����
        setUpValidator.namespace = false;
        
        // �e�X�g���{
        setUpValidator.setNamespace(true);
        
        // ����
        assertTrue(setUpValidator.namespace);        

    }

    /**
     * testSetNamespaceProperties01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) namespaceProperties:not nul<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) this.namespaceproperties:not null<br>
     *         
     * <br>
     * �����������������ɐݒ肳��邱�Ƃ��m�F�ɂ���B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetNamespaceProperties01() throws Exception {
        // �O����
        setUpValidator.namespaceProperties = null;
        Properties prop = new Properties();
        
        // �e�X�g���{
        setUpValidator.setNamespaceProperties(prop);
        
        // ����
        assertSame(prop, setUpValidator.namespaceProperties);        

    }

    /**
     * testSetNamespacePropertyFileName01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) namespacePropertyFileName:"abc"<br>
     *         (���) this.namespacePropertyFileName:null<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) this.namespacePropertyFileName:"abc"<br>
     *         
     * <br>
     * ����object��null�̏ꍇ�A��O���X���[����邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetNamespacePropertyFileName01() throws Exception {
        // �O����
        setUpValidator.namespacePropertyFileName = null;
        
        // �e�X�g���{
        setUpValidator.setNamespacePropertyFileName("abc");
        
        // ����
        assertEquals("abc", setUpValidator.namespacePropertyFileName);        

    }

    /**
     * testInitNamespaceProperties01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(���) this.namespaceCheck:true<br>
     *         (���) loadNamespaceProperties():�Ăяo���ꂽ���Ƃ��m�F����<br>
     *         (���) checkNamespaceProperties():�Ăяo���ꂽ���Ƃ��m�F����<br>
     *         
     * <br>
     * ���Ғl�F
     * <br>
     * this.namespaceCheck��true�̏ꍇ�A�e���\�b�h�����s����邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testInitNamespaceProperties01() throws Exception {
        // �O����
        SchemaValidatorImpl_SchemaValidatorImplStub04 schemaValidator = new SchemaValidatorImpl_SchemaValidatorImplStub04();
        schemaValidator.setNamespaceCheck(true);

        // �e�X�g���{
        schemaValidator.initNamespaceProperties();

        // ����
        assertTrue(schemaValidator.isLoadNamespaceProperties);
        assertTrue(schemaValidator.isCheckNamespaceProperties);
    }

    /**
     * testInitNamespaceProperties02()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(���) this.namespaceCheck:false<br>
     *         (���) loadNamespaceProperties():�Ăяo���ꂽ���Ƃ��m�F����<br>
     *         (���) checkNamespaceProperties():�Ăяo����Ȃ��������Ƃ��m�F����<br>
     *         
     * <br>
     * ���Ғl�F
     * <br>
     * this.namespaceCheck��false�̏ꍇ�AcheckNamespaceProperties���\�b�h�����s����Ȃ����Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testInitNamespaceProperties02() throws Exception {
        // �O����
        SchemaValidatorImpl_SchemaValidatorImplStub04 schemaValidator = new SchemaValidatorImpl_SchemaValidatorImplStub04();
        schemaValidator.setNamespaceCheck(false);
        
        // �e�X�g���{
        schemaValidator.initNamespaceProperties();

        // ����
        assertTrue(schemaValidator.isLoadNamespaceProperties);
        assertFalse(schemaValidator.isCheckNamespaceProperties);
    }

    /**
     * testLoadNamespaceProperties01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA,D
     * <br><br>
     * ���͒l�F(���) this.namespacePropertyFileName:"test_message_01_en_US.properties"<br>
     *         (���) �v���p�e�B�t�@�C��:�ȉ��̃v���p�e�B�����݂��邱��<br>
     *                test_message_01_en_US.properties<br>
     *                �@SystemExceptionHandlerTest.key={0}message<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) this.namespaceProperties:test_message_01_en_US.properties�̒l������Properties�I�u�W�F�N�g<br>
     *         
     * <br>
     * �v���p�e�B�I�u�W�F�N�g�̒��g���P�̎��A�w�肳�ꂽ�v���p�e�B�t�@�C�������[�h����Ă��邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testLoadNamespaceProperties01() throws Exception {
        // �O����
        setUpValidator.namespacePropertyFileName = "test_message_01_en_US.properties";
        
        // �e�X�g�Ώۂ̎��s
        setUpValidator.initNamespaceProperties();

        // ���ʊm�F
        Properties result = setUpValidator.namespaceProperties;
        
        assertTrue(result.containsKey("SystemExceptionHandlerTest.key"));
        assertTrue(result.containsValue("{0}message"));
    }

    /**
     * testLoadNamespaceProperties02()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA,D
     * <br><br>
     * ���͒l�F(���) this.namespacePropertyFileName:"test_message_01.properties"<br>
     *         (���) �v���p�e�B�t�@�C��:�ȉ��̃v���p�e�B�����݂��邱��<br>
     *                test_message_01.properties<br>
     *                �@SystemExceptionHandlerTest.error.message=��O���b�Z�[�W<br>
     *                �@SystemExceptionHandlerTest.error.message.null=<br>
     *                �@SystemExceptionHandlerTest.key={0}�f�t�H���g���b�Z�[�W<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) this.namespaceProperties:test_message_01.properties�̒l������Properties�I�u�W�F�N�g<br>
     *         
     * <br>
     * �w�肳�ꂽ�v���p�e�B�t�@�C���̒��g�̌������[�h����Ă��邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testLoadNamespaceProperties02() throws Exception {
        // �O����
        setUpValidator.namespacePropertyFileName = "test_message_01.properties";
        
        // �e�X�g�Ώۂ̎��s
        setUpValidator.initNamespaceProperties();

        // ���ʊm�F
        Properties result = setUpValidator.namespaceProperties;

        assertEquals("\u4f8b\u5916\u30e1\u30c3\u30bb\u30fc\u30b8", result.getProperty("SystemExceptionHandlerTest.error.message"));
        assertEquals("", result.getProperty("SystemExceptionHandlerTest.error.message.null"));
        assertEquals("{0}\u30c7\u30d5\u30a9\u30eb\u30c8\u30e1\u30c3\u30bb\u30fc\u30b8", result.getProperty("SystemExceptionHandlerTest.key"));
        
    }

    /**
     * testLoadNamespaceProperties03()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA,C
     * <br><br>
     * ���͒l�F(���) this.namespacePropertyFileName:null<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) this.namespaceProperties:null<br>
     *         
     * <br>
     * �v���p�e�B�t�@�C���������݂��Ȃ��ꍇ�A�������I�����邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testLoadNamespaceProperties03() throws Exception {
        // �O����
        setUpValidator.namespacePropertyFileName = null;
        
        // �e�X�g�Ώۂ̎��s
        setUpValidator.initNamespaceProperties();

        // ���ʊm�F
        assertNull(setUpValidator.namespaceProperties);

    }

    /**
     * testLoadNamespaceProperties04()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA,C
     * <br><br>
     * ���͒l�F(���) this.namespacePropertyFileName:""<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) this.namespaceProperties:null<br>
     *         
     * <br>
     * �v���p�e�B�t�@�C���������݂��Ȃ��ꍇ�A�������I�����邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testLoadNamespaceProperties04() throws Exception {
        // �O����
        setUpValidator.namespacePropertyFileName = "";
        
        // �e�X�g�Ώۂ̎��s
        setUpValidator.initNamespaceProperties();

        // ���ʊm�F
        assertNull(setUpValidator.namespaceProperties);

    }

    /**
     * testLoadNamespaceProperties05()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA,D
     * <br><br>
     * ���͒l�F(���) this.namespacePropertyFileName:"test_message_10.properties"<br>
     *         (���) �v���p�e�B�t�@�C��:�ȉ��̃v���p�e�B�����݂��邱��<br>
     *                test_message_10.properties�i���g�Ȃ��j<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) this.namespaceProperties:���Properties�I�u�W�F�N�g<br>
     *         
     * <br>
     * �w�肳�ꂽ�v���p�e�B�t�@�C���̒��g����̎��A��̃v���p�e�B�I�u�W�F�N�g�����o����Ă��邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testLoadNamespaceProperties05() throws Exception {
        // �O����
        setUpValidator.namespacePropertyFileName = "test_message_10.properties";
        
        // �e�X�g�Ώۂ̎��s
        setUpValidator.initNamespaceProperties();

        // ���ʊm�F
        Properties result = setUpValidator.namespaceProperties;
        assertTrue(result.isEmpty());
    }

    /**
     * testLoadNamespaceProperties06()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA,C
     * <br><br>
     * ���͒l�F(���) this.namespacePropertyFileName:"test_me.properties"<br>
     *         (���) �v���p�e�B�t�@�C��:test_me.properties�����݂��Ȃ�����<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) this.namespaceProperties:null<br>
     *         
     * <br>
     * ���݂��Ȃ��t�@�C�������w�肳�ꂽ���Anull��߂�l�Ƃ��ď������I�����邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testLoadNamespaceProperties06() throws Exception {
        // �O����
        setUpValidator.namespacePropertyFileName = "test_me.properties";
        
        // �e�X�g�Ώۂ̎��s
        setUpValidator.initNamespaceProperties();

        // ���ʊm�F
        Properties result = setUpValidator.namespaceProperties;
        LogUTUtil.checkWarn("*** Can not find property-file" +
                " [test_me.properties] ***");
        assertNull(result);
    }

    /**
     * testCheckNamespaceProperties01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA,C
     * <br><br>
     * ���͒l�F(���) namespaceProperties:null<br>
     *         
     * <br>
     * ���Ғl�F
     * <br>
     * namespaceProperties��null�̏ꍇ�A�����s���Ȃ����Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testCheckNamespaceProperties01() throws Exception {
        // �O����
        setUpValidator.setNamespaceProperties(null);
        
        // �e�X�g���{
        setUpValidator.checkNamespaceProperties();

    }

    /**
     * testCheckNamespaceProperties02()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA,D
     * <br><br>
     * ���͒l�F(���) namespaceProperties:���Properties<br>
     *         
     * <br>
     * ���Ғl�F
     * <br>
     * namespaceProperties����̏ꍇ�A�����s���Ȃ����Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testCheckNamespaceProperties02() throws Exception {
        // �O����
        Properties properties = new Properties();
        setUpValidator.setNamespaceProperties(properties);
        
        // �e�X�g���{
        setUpValidator.checkNamespaceProperties();

    }

    /**
     * testCheckNamespaceProperties03()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA,D
     * <br><br>
     * ���͒l�F(���) namespaceProperties:Properties{<br>
     *                  "abc", "123"<br>
     *                }<br>
     *         
     * <br>
     * ���Ғl�F
     * <br>
     * �v���p�e�B�����d�����Ă��Ȃ��ꍇ�A�����s���Ȃ����Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testCheckNamespaceProperties03() throws Exception {
        // �O����
        Properties properties = new Properties();
        properties.put("abc", "123");
        setUpValidator.setNamespaceProperties(properties);
        
        // �e�X�g���{
        setUpValidator.checkNamespaceProperties();

    }

    /**
     * testCheckNamespaceProperties04()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA,D
     * <br><br>
     * ���͒l�F(���) namespaceProperties:Properties{<br>
     *                  "abc", "123"<br>
     *                  "def", "456"<br>
     *                  "ghi", "789"<br>
     *                }<br>
     *         
     * <br>
     * ���Ғl�F
     * <br>
     * �v���p�e�B�����d�����Ă��Ȃ��ꍇ�A�����s���Ȃ����Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testCheckNamespaceProperties04() throws Exception {
        // �O����
        Properties properties = new Properties();
        properties.put("abc", "123");
        properties.put("def", "456");
        properties.put("ghi", "789");
        setUpValidator.setNamespaceProperties(properties);
        
        // �e�X�g���{
        setUpValidator.checkNamespaceProperties();

    }

    /**
     * testCheckNamespaceProperties05()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FA,D,G
     * <br><br>
     * ���͒l�F(���) namespaceProperties:Properties{<br>
     *                  "abc", "123"<br>
     *                  "def", "456"<br>
     *                  "ghi", "456"<br>
     *                }<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) ��O:PropertyNotUniqueException<br>
     *         (��ԕω�) ���O:���O���x���FERROR<br>
     *                    Namespace name [456] is not unique. Namespace must be unique. (key = [ghi])<br>
     *         
     * <br>
     * �v���p�e�B�����d�����Ă���ꍇ�A��O���X���[����邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testCheckNamespaceProperties05() throws Exception {
        // �O����
        Properties properties = new Properties();
        properties.put("abc", "123");
        properties.put("def", "456");
        properties.put("ghi", "456");
        setUpValidator.setNamespaceProperties(properties);
        
        // �e�X�g���{
        try {
            setUpValidator.checkNamespaceProperties();
            fail();
        }catch (NamespaceNotUniqueException e) {
            // ����
            String message = "Namespace name [456] is not unique. Namespace must be unique. (key = [ghi])";

            // ���O�m�F
            assertTrue(LogUTUtil.checkError(message));
        }

    }

    /**
     * testValidate01()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FA,C,G
     * <br><br>
     * ���͒l�F(����) in:null<br>
     *         (����) object:not null<br>
     *         (����) errorMessages:not null<br>
     *         (���) this.namespace:false<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalArgumentException{<br>
     *                      message = "InputStream is null."<br>
     *                    }<br>
     *         (��ԕω�) ���O:���O���x��:error<br>
     *                    InputStream is null.<br>
     *         
     * <br>
     * ����in��null�̏ꍇ�A��O���X���[����A���O�o�͂��s���邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidate01() throws Exception {

        // �e�X�g���{
        try{
            setUpValidator.validate(null, new Object(), new ErrorMessages());
            fail();
        }catch (IllegalArgumentException e) {
            // ����
            String message = "InputStream is null.";
            assertSame(message, e.getMessage());

            // ���O�m�F
            assertTrue(LogUTUtil.checkError(message));
        }
        
    }

    /**
     * testValidate02()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FA,C,G
     * <br><br>
     * ���͒l�F(����) in:not null<br>
     *         (����) object:not null<br>
     *         (����) errorMessages:null<br>
     *         (���) this.namespace:false<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalArgumentException{<br>
     *                      message = "ErrorMessages is null."<br>
     *                    }<br>
     *         (��ԕω�) ���O:���O���x���Ferror<br>
     *                    ErrorMessages is null.<br>
     *         
     * <br>
     * ����errorMessages��null�̏ꍇ�A��O���X���[����A���O���o�͂���邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidate02() throws Exception {
        // �O����
        ByteArrayInputStream inputStream = new ByteArrayInputStream(
                new byte[] {});
        
        // �e�X�g���{
        try{
            setUpValidator.validate(inputStream, new Object(), null);
            fail();
        }catch (IllegalArgumentException e) {
            // ����
            String message = "ErrorMessages is null.";
            assertEquals(message, e.getMessage());

            // ���O�m�F
            assertTrue(LogUTUtil.checkError(message));
        }
        
    }

    /**
     * testValidate03()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FA,G
     * <br><br>
     * ���͒l�F(����) in:not null<br>
     *         (����) object:not null<br>
     *         (����) errorMessages:not null<br>
     *         (���) getUrl():�Ăяo�������Ƃ��m�F����B<br>
     *         (���) createNonNamespaceDomParser():SAXNotRecognizedException���X���[����B<br>
     *         (���) this.namespace:false<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) ��O:ParserNotSupportedException�o<br>
     *                      cause=  SAXNotRecognizedException<br>
     *                    }<br>
     *         (��ԕω�) ���O:���O���x���Ferror<br>
     *                    Schema property error.<br>
     *         
     * <br>
     * DOMParse�������ɁASAXNotRecognizedException���X���[���ꂽ�ꍇ�ASAXNotRecognizedException�����b�v����ParserNotSupportedException���X���[����A���O���o�͂���邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidate03() throws Exception {
        // �O����
        ByteArrayInputStream inputStream = new ByteArrayInputStream(
                new byte[] {});
        SchemaValidatorImpl_SchemaValidatorImplStub02 validator 
        	= new SchemaValidatorImpl_SchemaValidatorImplStub02();
        
        // �e�X�g���{
        try{
            validator.validate(inputStream, new Object(), new ErrorMessages());
            fail();
        }catch (ParserNotSupportedException e) {
            // ����
            String message = "Schema property error.";
            assertSame(SAXNotRecognizedException.class, e.getCause().getClass());

            // ���O�m�F
            assertTrue(LogUTUtil.checkError(message, e.getCause()));
        }
    }

    /**
     * testValidate04()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FA,G
     * <br><br>
     * ���͒l�F(����) in:not null<br>
     *         (����) object:not null<br>
     *         (����) errorMessages:not null<br>
     *         (���) getUrl():�Ăяo�������Ƃ��m�F����B<br>
     *         (���) createNonNamespaceDomParser():SAXNotSupportedException���X���[����B<br>
     *         (���) this.namespace:false<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) ��O:ParserNotSupportedException�o<br>
     *                      cause= SAXNotSupportedException<br>
     *                    }<br>
     *         (��ԕω�) ���O:���O���x���Ferror<br>
     *                    Schema property error.<br>
     *         
     * <br>
     * DOMParse�������ɁASAXNotSupportedException���X���[���ꂽ�ꍇ�ASAXNotSupportedException�����b�v����ParserNotSupportedException���X���[����A���O���o�͂���邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidate04() throws Exception {
        // �O����
        ByteArrayInputStream inputStream = new ByteArrayInputStream(
                new byte[] {});
        SchemaValidatorImpl_SchemaValidatorImplStub03 validator 
        	= new SchemaValidatorImpl_SchemaValidatorImplStub03();
        
        // �e�X�g���{
        try{
            validator.validate(inputStream, new Object(), new ErrorMessages());
            fail();
        }catch (ParserNotSupportedException e) {
            // ����
            String message = "Schema property error.";
            assertSame(SAXNotSupportedException.class, e.getCause().getClass());

            // ���O�m�F
            assertTrue(LogUTUtil.checkError(message, e.getCause()));
        }
    }

    /**
     * testValidate05()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA,D
     * <br><br>
     * ���͒l�F(����) in:not null<br>
     *         (����) object:null<br>
     *         (����) errorMessages:not null<br>
     *         (���) getUrl():�Ăяo�������Ƃ��m�F����B<br>
     *         (���) createNonNamespaceDomParser():�Ăяo�������Ƃ��m�F����B<br>
     *                <br>
     *                errorMessages�ɃG���[���l�߂�<br>
     *         (���) parser.perse():�Ăяo�������Ƃ��m�F����B<br>
     *                �������n���ꂽ���Ƃ��m�F����B<br>
     *         (���) this.namespace:false<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) Document:null<br>
     *         
     * <br>
     * �`���`�F�b�N�G���[�����������ꍇ�ANull���ԋp����邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidate05() throws Exception {
        
        // �O����
        ByteArrayInputStream inputStream = new ByteArrayInputStream(
                new byte[] {});
        SchemaValidatorImpl_SchemaValidatorImplStub01 validator = new SchemaValidatorImpl_SchemaValidatorImplStub01();
        validator.domParser = new SchemaValidatorImpl_DOMParserStub01();

        // �`���`�F�b�N�G���[�𔭐������邽�߁A���s�O����G���[���i�[���Ă����B
        ErrorMessages errorMessages = new ErrorMessages();
        errorMessages.add(new ErrorMessage(null,null));
        
        // �e�X�g���{
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
     * (����n)
     * <br>
     * �ϓ_�FA,D
     * <br><br>
     * ���͒l�F(����) in:not null<br>
     *         (����) object:null<br>
     *         (����) errorMessages:not null<br>
     *         (���) getUrl():�Ăяo�������Ƃ��m�F����B<br>
     *         (���) createNonNamespaceDomParser():�Ăяo�������Ƃ��m�F����B<br>
     *         (���) parser.perse():�Ăяo�������Ƃ��m�F����B<br>
     *                �������n���ꂽ���Ƃ��m�F����B<br>
     *         (���) this.namespace:false<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) Document:Document�C���X�^���X<br>
     *                  �iDOM�c���[�j<br>
     *         
     * <br>
     * �`���`�F�b�N�����s����ADocument����������邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidate06() throws Exception {
        
        // �O����
        ByteArrayInputStream inputStream = new ByteArrayInputStream(
                new byte[] {});
        SchemaValidatorImpl_SchemaValidatorImplStub01 validator = new SchemaValidatorImpl_SchemaValidatorImplStub01();
        validator.domParser = new SchemaValidatorImpl_DOMParserStub01();
        
        // �e�X�g���{
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
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) in:not null<br>
     *         (����) object:null<br>
     *         (����) errorMessages:not null<br>
     *         (���) getUrl():�Ăяo�������Ƃ��m�F����B<br>
     *         (���) getNamespaceName():�Ăяo�������Ƃ��m�F����B<br>
     *         (���) creatNamespaceDomParser():�Ăяo�������Ƃ��m�F����B<br>
     *         (���) setCommonParserProperty():�Ăяo�������Ƃ��m�F����B<br>
     *         (���) setCommonParserFeature:�Ăяo�������Ƃ��m�F����B<br>
     *         (���) parser.perse():�Ăяo�������Ƃ��m�F����B<br>
     *                �������n���ꂽ���Ƃ��m�F����B<br>
     *         (���) this.namespace:true<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) Document:Document�C���X�^���X<br>
     *                  �iDOM�c���[�j<br>
     *         
     * <br>
     * this.namespace��true�̏ꍇ�A�`���`�F�b�N�����s����ADocument����������邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidate07() throws Exception {
        
        // �O����
        ByteArrayInputStream inputStream = new ByteArrayInputStream(
                new byte[] {});
        SchemaValidatorImpl_SchemaValidatorImplStub01 validator = new SchemaValidatorImpl_SchemaValidatorImplStub01();
        validator.domParser = new SchemaValidatorImpl_DOMParserStub01();
        validator.namespace = true;
        
        // �e�X�g���{
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
     * (�ُ�n)
     * <br>
     * �ϓ_�FA,G
     * <br><br>
     * ���͒l�F(����) in:not null<br>
     *         (����) object:not null<br>
     *         (����) errorMessages:not null<br>
     *         (���) getUrl():�Ăяo�������Ƃ��m�F����B<br>
     *         (���) createNonNamespaceDomParser():�Ăяo�������Ƃ��m�F����B<br>
     *         (���) setCommonParserProperty():�Ăяo�������Ƃ��m�F����B<br>
     *         (���) setCommonParserFeature:�Ăяo�������Ƃ��m�F����B<br>
     *         (���) parser.perse():SAXException���X���[����<br>
     *         (���) this.namespace:false<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) ��O:ParserSAXException{<br>
     *                      cause = SAXException<br>
     *                    }<br>
     *         (��ԕω�) ���O:���O���x���Ferror<br>
     *                    Schema parse error.<br>
     *         
     * <br>
     * parse()���\�b�h���s���ɁASAXException���X���[���ꂽ�ꍇ�ASAXException�����b�v����ParserSAXException���X���[����A���O���o�͂���邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidate08() throws Exception {
        
        // �O����
        ByteArrayInputStream inputStream = new ByteArrayInputStream(
                new byte[] {});
        SchemaValidatorImpl_SchemaValidatorImplStub01 validator = new SchemaValidatorImpl_SchemaValidatorImplStub01();
        validator.domParser = new SchemaValidatorImpl_DOMParserStub02();
        validator.namespace = true;
        
        // �e�X�g���{
        try{
            validator.validate(inputStream, new Object(), new ErrorMessages());
            fail();
        }catch (ParserSAXException e) {
            // ����
            String message = "Schema parse error.";
            assertSame(SAXException.class, e.getCause().getClass());

            // ���O�m�F
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
     * (�ُ�n)
     * <br>
     * �ϓ_�FA,G
     * <br><br>
     * ���͒l�F(����) in:not null<br>
     *         (����) object:not null<br>
     *         (����) errorMessages:not null<br>
     *         (���) getUrl():�Ăяo�������Ƃ��m�F����B<br>
     *         (���) createNonNamespaceDomParser():�Ăяo�������Ƃ��m�F����B<br>
     *         (���) setCommonParserProperty():�Ăяo�������Ƃ��m�F����B<br>
     *         (���) setCommonParserFeature:�Ăяo�������Ƃ��m�F����B<br>
     *         (���) parser.perse():IOException���X���[����<br>
     *         (���) this.namespace:false<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) ��O:ParserIOException{<br>
     *                      cause = IOException<br>
     *                    }<br>
     *         (��ԕω�) ���O:���O���x���Ferror<br>
     *                    Schema io error.<br>
     *         
     * <br>
     * parse()���\�b�h���s����IOException���X���[���ꂽ�ꍇ�AIOException�����b�v����ParserIOException���X���[����A���O���o�͂���邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidate09() throws Exception {
        
        // �O����
        ByteArrayInputStream inputStream = new ByteArrayInputStream(
                new byte[] {});
        SchemaValidatorImpl_SchemaValidatorImplStub01 validator 
        	= new SchemaValidatorImpl_SchemaValidatorImplStub01();
        validator.domParser = new SchemaValidatorImpl_DOMParserStub03();
        validator.namespace = true;
        
        // �e�X�g���{
        try{
            validator.validate(inputStream, new Object(), new ErrorMessages());
            fail();
        }catch (ParserIOException e) {
            // ����
            String message = "Schema io error.";
            assertSame(IOException.class, e.getCause().getClass());

            // ���O�m�F
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
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) object:not null<br>
     *         (���) getUrl():null<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) ��O:SchemaFileNotFoundException<br>
     * 				Schema file is not found. Set schema file in [root-classpath]/java/lang/Object.xsd
     *         (��ԕω�) ���O:���O���x���Ferror<br>
     *                    Schema file is not found.<br>
     *         
     * <br>
     * �X�L�[�}�t�@�C����������Ȃ��ꍇ�̃e�X�g�B��O�𓊂���B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testCreateDomParser01() throws Exception {
        // �O����
    	SchemaValidatorImpl_SchemaValidatorImplStub05 validator 
    		= new SchemaValidatorImpl_SchemaValidatorImplStub05();
    	validator.config = new XML11Configuration();
    	validator.url = null;
    	Object object = new Object();

        // �e�X�g���{
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
     * (����n) 
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) object:not null<br>
     *         (���) getUrl():http://www.nttdata.co.jp/<br>
     *         (���) this.namespace:true<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) DOMParser:DOMParser<br>
     *         
     * <br>
     * ���O��Ԏg�p����ꍇ�̃e�X�g�BDOM�p�[�T��Ԃ��B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testCreateDomParser02() throws Exception {
    	// �O����
    	SchemaValidatorImpl_SchemaValidatorImplStub05 validator 
    		= new SchemaValidatorImpl_SchemaValidatorImplStub05();
    	validator.config = new XML11Configuration();
    	validator.url = new URL("http://www.nttdata.co.jp/");
    	validator.namespace = true;
    	validator.namespaceName = "NamespaceTest";
    	Object object = new Object();

        // �e�X�g���{
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
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) object:not null<br>
     *         (���) getUrl():http://www.nttdata.co.jp/<br>
     *         (���) this.namespace:false<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) DOMParser:DOMParser<br>
     *         
     * <br>
     * ���O��Ԏg�p���Ȃ��ꍇ�̃e�X�g�BDOM�p�[�T��Ԃ��B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testCreateDomParser03() throws Exception {
    	// �O����
    	SchemaValidatorImpl_SchemaValidatorImplStub05 validator 
    		= new SchemaValidatorImpl_SchemaValidatorImplStub05();
    	validator.config = new XML11Configuration();
    	validator.url = new URL("http://www.nttdata.co.jp/");
    	validator.namespace = false;
    	Object object = new Object();

        // �e�X�g���{
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
     * (����n) 
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(���) ths.namespace:true<br>
     *         (���) this.cache:true<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) XMLParserConfiguration:XMLParserConfiguration<br>
     *                  (������grammarPool��ێ�)<br>
     *         
     * <br>
     * ���O��ԂƃL���b�V�����L���ɂȂ����ꍇ�̃e�X�g�B���@�v�[����ێ�����Configuration��Ԃ��B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testCreateXmlParserConfiguration01() throws Exception {
        // �O����
    	SchemaValidatorImpl validator = new SchemaValidatorImpl();
    	validator.namespace = true;
    	validator.cache = true;

        // �e�X�g���{
    	XMLParserConfiguration result = validator.createXmlParserConfiguration();

        // ����
    	assertSame(validator.grammarPool, UTUtil.getPrivateField(result, "fGrammarPool"));
    }

    /**
     * testCreateXmlParserConfiguration02()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(���) ths.namespace:true<br>
     *         (���) this.cache:false<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) XMLParserConfiguration:XMLParserConfiguration<br>
     *         
     * <br>
     * ���O��Ԃ̂ݗL���ɂȂ����ꍇ�̃e�X�g�B�ʏ��Configuration��Ԃ��B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testCreateXmlParserConfiguration02() throws Exception {
    	SchemaValidatorImpl validator = new SchemaValidatorImpl();
    	validator.namespace = true;
    	validator.cache = false;

        // �e�X�g���{
    	XMLParserConfiguration result = validator.createXmlParserConfiguration();

        // ����
    	assertNotSame(validator.grammarPool, UTUtil.getPrivateField(result, "fGrammarPool"));
    }

    /**
     * testCreateXmlParserConfiguration03()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(���) ths.namespace:false<br>
     *         (���) this.cache:true<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) XMLParserConfiguration:XMLParserConfiguration<br>
     *         
     * <br>
     * �L���b�V���̂ݗL���ɂȂ����ꍇ�̃e�X�g�B�ʏ��Configuration��Ԃ��B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testCreateXmlParserConfiguration03() throws Exception {
    	SchemaValidatorImpl validator = new SchemaValidatorImpl();
    	validator.namespace = false;
    	validator.cache = true;

        // �e�X�g���{
    	XMLParserConfiguration result = validator.createXmlParserConfiguration();

        // ����
    	assertNotSame(validator.grammarPool, UTUtil.getPrivateField(result, "fGrammarPool"));
    }
    
    /**
     * testSetCommonParserProperty01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) parser:not null<br>
     *         (����) errorMessages:not null<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) DOMParser:�i�����́jDOMParser�C���X�^���X{<br>
     *                    fProperties{<br>
     *                      Constants.XERCES_PROPERTY_PREFIX<br>
     *                      + Constants.ERROR_REPORTER_PROPERTY= <br>
     *                         XMLErrorReporterEx�C���X�^���X= {<br>
     *                           errorMessages= ������ErrorMessages�C���X�^���X<br>
     *                         }<br>
     *                    }<br>
     *                  }<br>
     *         
     * <br>
     * DOMParser�̃v���p�e�B��XMLErrorReporterEx�C���X�^���X���ݒ肳��邱�Ƃ��m�F����B<br>
     * XMLErrorReporterEx�C���X�^���X�Ɉ�����ErrorMessages�C���X�^���X���ݒ肳��邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetCommonParserProperty01() throws Exception {

        // �O����
        DOMParser parser = new DOMParser();
        ErrorMessages messages = new ErrorMessages();
        
        // �e�X�g���{
        setUpValidator.setCommonParserProperty(parser, messages);

        // ����
        XMLErrorReporterEx reporter = (XMLErrorReporterEx) parser.getProperty(Constants.XERCES_PROPERTY_PREFIX
                + Constants.ERROR_REPORTER_PROPERTY);
        assertEquals(messages, reporter.getErrorMessages());
    }

    /**
     * testSetCommonParserFeature01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) parser:not null<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) DOMParser:�i�����́jDOMParser�C���X�^���X{<br>
     *                    fProperties{<br>
     *                      Constants.SAX_FEATURE_PREFIX<br>
     *                      + Constants.VALIDATION_FEATURE= true,<br>
     *                      Constants.XERCES_FEATURE_PREFIX<br>
     *                                  + Constants.SCHEMA_VALIDATION_FEATURE= true<br>
     *                    }<br>
     *                  }<br>
     *         
     * <br>
     * DOMParser�̃v���p�e�B�Ɋe�v���p�e�B���ݒ肳��邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetCommonParserFeature01() throws Exception {

        // �O����
        DOMParser parser = new DOMParser();
        
        // �e�X�g���{
        setUpValidator.setCommonParserFeature(parser);

        // ����
        assertTrue(((Boolean) parser.getFeature(Constants.SAX_FEATURE_PREFIX
                + Constants.VALIDATION_FEATURE)).booleanValue());
        assertTrue(((Boolean)(parser.getFeature(Constants.XERCES_FEATURE_PREFIX
                + Constants.SCHEMA_VALIDATION_FEATURE))).booleanValue());
        
    }

    /**
     * testGetUrl01()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FA,C,G
     * <br><br>
     * ���͒l�F(����) object:null<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalArgumentException{<br>
     *                      message = Argument object is null.<br>
     *                    }<br>
     *         (��ԕω�) ���O:���O���x��:error<br>
     *                    Argument object is null.<br>
     *         
     * <br>
     * ����object��null�̏ꍇ�A��O���X���[����邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetUrl01() throws Exception {
        // �O����
        Object object = null;

        // �e�X�g���{
        try {
            setUpValidator.getUrl(object);
            fail();
        } catch (IllegalArgumentException e) {
            // ����
            String message = "Argument is null.";
            assertEquals(message, e.getMessage());

            // ���O�m�F
            assertTrue(LogUTUtil.checkError(message));
        }
    }

    /**
     * testGetUrl02()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) object:not null<br>
     *                �i�t�@�C�������݂���p�X�j<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) URL:URL�C���X�^���X<br>
     *         
     * <br>
     * �w��p�X��Ƀt�@�C�������݂���ꍇ�AURL�C���X�^���X��ԋp���邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetUrl02() throws Exception {
        // ����
        // ���݂���p�X�iSchemaValidatorImplTest.xsd�j
        Object object = this;

        // �e�X�g���{
        URL result = setUpValidator.getUrl(object);
        
        // ����
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
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) object:not null<br>
     *                �i�t�@�C�������݂��Ȃ��p�X�j<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) URL:null<br>
     *         
     * <br>
     * �w��p�X��Ƀt�@�C�������݂��Ȃ��ꍇ�Anull��ԋp���邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetUrl03() throws Exception {
        // ����
        // ���݂��Ȃ��p�X
        Object object = setUpValidator;

        // �e�X�g���{
        URL result = setUpValidator.getUrl(object);
        
        // ����
        assertNull(result);        

    }

    /**
     * testGetNamespaceName01()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FC,G
     * <br><br>
     * ���͒l�F(����) object:null<br>
     *         (���) this.namespaceProperties:not null<br>
     *         (���) this.NAME_SPACE_SUFFIX:".Namespace"<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalArgumentException{<br>
     *                      message = Argument is null.<br>
     *                    }<br>
     *         (��ԕω�) ���O:���O���x��:error<br>
     *                    Argument is null.<br>
     *         
     * <br>
     * ����object��null�̏ꍇ�A��O���X���[����邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetNamespaceName01() throws Exception {

        // �e�X�g���{
        try {
            setUpValidator.getNamespaceName(null);
            fail();
        } catch (IllegalArgumentException e) {
            // ����
            String message = "Argument is null.";
            assertEquals(message, e.getMessage());

            // ���O�m�F
            assertTrue(LogUTUtil.checkError(message));
        }

    }

    /**
     * testGetNamespaceName02()
     * <br><br>
     * 
     *  (�ُ�n)
     * <br>
     * �ϓ_�FC,G
     * <br><br>
     * ���͒l�F(����) not null<br>
     *         (���) this.namespaceProperties:null<br>
     *         (���) this.NAME_SPACE_SUFFIX:".Namespace"<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalStateException{<br>
     *                      message= Namespace properties is null.<br>
     *                    }<br>
     *         (��ԕω�) ���O:���O���x��:error<br>
     *                    Namespace properties is null.<br>
     *         
     * <br>
     * this.namespaceProperties��null�̏ꍇ�A��O���X���[����邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetNamespaceName02() throws Exception {
        // �O����
        setUpValidator.namespaceProperties = null;
        setUpValidator.namespace = true;

        // �e�X�g���{
        try {
            setUpValidator.getNamespaceName(new Object());
            fail();
        } catch (IllegalStateException e) {
            // ����
            String message = "Namespace property is not set. "
            	+ "Put namespaces.properties file on your classpath, and call SchemaValidatorImpl initNamespaceProperties() after instanciate.";
            assertEquals(message, e.getMessage());

            // ���O�m�F
            assertTrue(LogUTUtil.checkError(message));
        }

    }

    /**
     * testGetNamespaceName03()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FA,C
     * <br><br>
     * ���͒l�F(����) object:jp.terasoluna.fw.oxm.xsd.xerces.SchemaValidatorImpl�C���X�^���X<br>
     *         (���) this.namespaceProperties:���Properties�C���X�^���X<br>
     *         (���) this.NAME_SPACE_SUFFIX:".Namespace"<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) NamespaceNotFoundException<br>
     *         
     * <br>
     * object�̃N���X���ɍ��v����v���p�e�B�����݂��Ȃ������ꍇ�A
     * ��O���X���[����邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetNamespaceName03() throws Exception {
        // �O����
        Properties prop = new Properties();
        setUpValidator.namespaceProperties = prop;
        setUpValidator.namespace = true;

        // �e�X�g���{������
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
     * (����n)
     * <br>
     * �ϓ_�FA,C,D
     * <br><br>
     * ���͒l�F(����) object:jp.terasoluna.fw.oxm.xsd.xerces.SchemaValidatorImpl�C���X�^���X<br>
     *         (���) this.namespaceProperties:Properties�C���X�^���X{<br>
     *                  "jp.terasoluna.fw.oxm.xsd.xerces.SchemaValidatorImpl"= "123"<br>
     *                }<br>
     *         (���) this.NAME_SPACE_SUFFIX:".Namespace"<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) String:"123"<br>
     *         
     * <br>
     * object�̃N���X���ɍ��v����v���p�e�B�����݂����ꍇ�A�l��ԋp���邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetNamespaceName04() throws Exception {
        // �O����
        Properties prop = new Properties();
        prop.setProperty(setUpValidator.getClass().getName() + ".Namespace", "123");
        setUpValidator.namespace = true;
        setUpValidator.namespaceProperties = prop;
        
        // �e�X�g���{������
        assertEquals("123", setUpValidator.getNamespaceName(setUpValidator));
    }

    /**
     * testGetNamespaceName05()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) object:not null<br>
     *         (���) this.namespaceProperties:-<br>
     *         (���) this.namespace:false<br>
     *         (���) this.NAME_SPACE_SUFFIX:-<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) null<br>
     *         
     * <br>
     * ���O��Ԃ��g��Ȃ��ꍇ�̃e�X�g�BNull��Ԃ��B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetNamespaceName05() throws Exception {
        // �O����
        setUpValidator.namespace = false;
        
        // �e�X�g���{������
        assertNull(setUpValidator.getNamespaceName(new Object()));
    }
}
