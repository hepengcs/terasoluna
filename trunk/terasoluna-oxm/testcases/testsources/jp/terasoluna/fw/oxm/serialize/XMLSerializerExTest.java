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

package jp.terasoluna.fw.oxm.serialize;

import java.io.StringBufferInputStream;
import java.io.StringWriter;
import java.io.Writer;

import org.xml.sax.AttributeList;
import org.xml.sax.Attributes;
import org.xml.sax.DocumentHandler;
import org.xml.sax.helpers.AttributeListImpl;
import org.xml.sax.helpers.AttributesImpl;

import jp.terasoluna.fw.oxm.serialize.XMLSerializerEx;
import junit.framework.TestCase;

/**
 * {@link jp.terasoluna.fw.oxm.serialize.XMLSerializerEx}
 * �N���X�̃u���b�N�{�b�N�X�e�X�g�B
 * 
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4>
 * Castor�ɓn��Serializer�N���X�B<br>
 * �O������F<br>
 * </p>
 * 
 * @see jp.terasoluna.fw.oxm.serialize.XMLSerializerEx
 */
public class XMLSerializerExTest extends TestCase {

    /**
     * ���̃e�X�g�P�[�X�����s����ׂ� GUI �A�v���P�[�V�������N������B
     * 
     * @param args
     *            java �R�}���h�ɐݒ肳�ꂽ�p�����[�^
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(XMLSerializerExTest.class);
    }

    /**
     * �������������s���B
     * 
     * @throws Exception
     *             ���̃��\�b�h�Ŕ���������O
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    /**
     * �I���������s���B
     * 
     * @throws Exception
     *             ���̃��\�b�h�Ŕ���������O
     * @see junit.framework.TestCase#tearDown()
     */
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * �R���X�g���N�^�B
     * 
     * @param name
     *            ���̃e�X�g�P�[�X�̖��O�B
     */
    public XMLSerializerExTest(String name) {
        super(name);
    }

    /**
     * testStartElementStringAttributeList01() <br>
     * <br>
     * 
     * (����n) <br>
     * �ϓ_�FA <br>
     * <br>
     * ���͒l�F<br>
     * (����) tagName : "element"<br>
     * (����) AttributeList : new AttributeListImpl()<br>
     * (����) atRoot : True<br>
     * (����) preserveWhitespace : True<br>
     * 
     * <br>
     * ���Ғl�F<br>
     * (��ԕω�) Writer : ���[�g�m�[�h�� xml:space="preserve" �L��<br>
     * (��ԕω�) atRoot : False<br>
     * 
     * <br>
     * ���[�g�m�[�h��xml:space="preserve" ���t������Ă��邱�Ƃ��m�F���� <br>
     * 
     * @throws Exception
     *             ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("deprecation")
    public void testStartElementStringAttributeList01() throws Exception {
        // �O����
        Writer writer = new StringWriter();

        XMLSerializerEx serializerEx = new XMLSerializerEx(writer, null);
        serializerEx.preserveWhitespace = true;
        serializerEx.atRoot = true;

        AttributeList attributeList = new AttributeListImpl();

        // �e�X�g���{
        serializerEx.startDocument();
        serializerEx.startElement("element", attributeList);
        serializerEx.endElement("element");
        serializerEx.endDocument();

        // ����
        assertEquals(
                "<?xml version=\"1.0\"?>\n<element xml:space=\"preserve\"/>",
                writer.toString());
        assertFalse(serializerEx.atRoot);
    }

    /**
     * testStartElementStringAttributeList02() <br>
     * <br>
     * 
     * (����n) <br>
     * �ϓ_�FA <br>
     * <br>
     * ���͒l�F<br>
     * (����) tagName : "element"<br>
     * (����) AttributeList : new AttributeListImpl()<br>
     * (����) atRoot : True<br>
     * (����) preserveWhitespace : False<br>
     * 
     * <br>
     * ���Ғl�F<br>
     * (��ԕω�) Writer : ���[�g�m�[�h�� xml:space="preserve" ����<br>
     * (��ԕω�) atRoot : True<br>
     * 
     * <br>
     * ���[�g�m�[�h��xml:space="preserve" ���t������Ă��Ȃ����Ƃ��m�F���� <br>
     * 
     * @throws Exception
     *             ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("deprecation")
    public void testStartElementStringAttributeList02() throws Exception {
        // �O����
        Writer writer = new StringWriter();

        XMLSerializerEx serializerEx = new XMLSerializerEx(writer, null);
        serializerEx.preserveWhitespace = false;
        serializerEx.atRoot = true;

        AttributeList attributeList = new AttributeListImpl();

        // �e�X�g���{
        serializerEx.startDocument();
        serializerEx.startElement("element", attributeList);
        serializerEx.endElement("element");
        serializerEx.endDocument();

        // ����
        assertEquals(
                "<?xml version=\"1.0\"?>\n<element/>",
                writer.toString());
        assertTrue(serializerEx.atRoot);
    }

    /**
     * testStartElementStringAttributeList03() <br>
     * <br>
     * 
     * (����n) <br>
     * �ϓ_�FA <br>
     * <br>
     * ���͒l�F<br>
     * (����) tagName : "element"<br>
     * (����) AttributeList : new AttributeListImpl()<br>
     * (����) atRoot : False<br>
     * (����) preserveWhitespace : True<br>
     * 
     * <br>
     * ���Ғl�F<br>
     * (��ԕω�) Writer : ���[�g�m�[�h�� xml:space="preserve" ����<br>
     * (��ԕω�) atRoot : False<br>
     * 
     * <br>
     * ���[�g�m�[�h��xml:space="preserve" ���t������Ă��Ȃ����Ƃ��m�F���� <br>
     * 
     * @throws Exception
     *             ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("deprecation")
    public void testStartElementStringAttributeList03() throws Exception {
        // �O����
        Writer writer = new StringWriter();
    
        XMLSerializerEx serializerEx = new XMLSerializerEx(writer, null);
        serializerEx.preserveWhitespace = true;
        serializerEx.atRoot = false;
    
        AttributeList attributeList = new AttributeListImpl();
    
        // �e�X�g���{
        serializerEx.startDocument();
        serializerEx.startElement("element", attributeList);
        serializerEx.endElement("element");
        serializerEx.endDocument();
    
        // ����
        assertEquals(
                "<?xml version=\"1.0\"?>\n<element/>",
                writer.toString());
        assertFalse(serializerEx.atRoot);
    }

    /**
     * testStartElementStringAttributeList04() <br>
     * <br>
     * 
     * (����n) <br>
     * �ϓ_�FA <br>
     * <br>
     * ���͒l�F<br>
     * (����) tagName : "element"<br>
     * (����) AttributeList : new AttributeListImpl()<br>
     * (����) atRoot : False<br>
     * (����) preserveWhitespace : False<br>
     * 
     * <br>
     * ���Ғl�F<br>
     * (��ԕω�) Writer : ���[�g�m�[�h�� xml:space="preserve" ����<br>
     * (��ԕω�) atRoot : False<br>
     * 
     * <br>
     * ���[�g�m�[�h��xml:space="preserve" ���t������Ă��Ȃ����Ƃ��m�F���� <br>
     * 
     * @throws Exception
     *             ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("deprecation")
    public void testStartElementStringAttributeList04() throws Exception {
        // �O����
        Writer writer = new StringWriter();
    
        XMLSerializerEx serializerEx = new XMLSerializerEx(writer, null);
        serializerEx.preserveWhitespace = false;
        serializerEx.atRoot = false;
    
        AttributeList attributeList = new AttributeListImpl();
    
        // �e�X�g���{
        serializerEx.startDocument();
        serializerEx.startElement("element", attributeList);
        serializerEx.endElement("element");
        serializerEx.endDocument();
    
        // ����
        assertEquals(
                "<?xml version=\"1.0\"?>\n<element/>",
                writer.toString());
        assertFalse(serializerEx.atRoot);
    }

    /**
     * testStartElementStringStringStringAttributes01() <br>
     * <br>
     * 
     * (����n) <br>
     * �ϓ_�FA <br>
     * <br>
     * ���͒l�F<br>
     * (����) namespaceURI : "namespaceURI"<br>
     * (����) localName : "localName"<br>
     * (����) rawName : "rawName"<br>
     * (����) Attributes : new AttributesImpl()<br>
     * (����) atRoot : True<br>
     * (����) preserveWhitespace : True<br>
     * 
     * <br>
     * ���Ғl�F<br>
     * (��ԕω�) Writer : ���[�g�m�[�h�� xml:space="preserve" �L��<br>
     * (��ԕω�) atRoot : False<br>
     * 
     * <br>
     * ���[�g�m�[�h��xml:space="preserve" ���t������Ă��邱�Ƃ��m�F���� <br>
     * 
     * @throws Exception
     *             ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("deprecation")
    public void testStartElementStringStringStringAttributes01() throws Exception {
        // �O����
        Writer writer = new StringWriter();

        XMLSerializerEx serializerEx = new XMLSerializerEx();
        serializerEx.setOutputCharStream(writer);
        serializerEx.preserveWhitespace = true;
        serializerEx.atRoot = true;

        Attributes attributes = new AttributesImpl();

        // �e�X�g���{
        serializerEx.startDocument();
        serializerEx.startElement("namespaceURI", "localName", "rawName", attributes);
        serializerEx.endElement("namespaceURI", "localName", "rawName");
        serializerEx.endDocument();

        // ����
        assertEquals(
                "<?xml version=\"1.0\"?>\n<rawName xml:space=\"preserve\"/>",
                writer.toString());
        assertFalse(serializerEx.atRoot);
    }

    /**
     * testStartElementStringStringStringAttributes02() <br>
     * <br>
     * 
     * (����n) <br>
     * �ϓ_�FA <br>
     * <br>
     * ���͒l�F<br>
     * (����) namespaceURI : "namespaceURI"<br>
     * (����) localName : "localName"<br>
     * (����) rawName : "rawName"<br>
     * (����) Attributes : new AttributesImpl()<br>
     * (����) atRoot : True<br>
     * (����) preserveWhitespace : False<br>
     * 
     * <br>
     * ���Ғl�F<br>
     * (��ԕω�) Writer : ���[�g�m�[�h�� xml:space="preserve" ����<br>
     * (��ԕω�) atRoot : True<br>
     * 
     * <br>
     * ���[�g�m�[�h��xml:space="preserve" ���t������Ă��Ȃ����Ƃ��m�F���� <br>
     * 
     * @throws Exception
     *             ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("deprecation")
    public void testStartElementStringStringStringAttributes02() throws Exception {
        // �O����
        Writer writer = new StringWriter();

        XMLSerializerEx serializerEx = new XMLSerializerEx();
        serializerEx.setOutputCharStream(writer);
        serializerEx.preserveWhitespace = false;
        serializerEx.atRoot = true;

        Attributes attributes = new AttributesImpl();

        // �e�X�g���{
        serializerEx.startDocument();
        serializerEx.startElement("namespaceURI", "localName", "rawName", attributes);
        serializerEx.endElement("namespaceURI", "localName", "rawName");
        serializerEx.endDocument();

        // ����
        assertEquals(
                "<?xml version=\"1.0\"?>\n<rawName/>",
                writer.toString());
        assertTrue(serializerEx.atRoot);
    }
    
    /**
     * testStartElementStringStringStringAttributes01() <br>
     * <br>
     * 
     * (����n) <br>
     * �ϓ_�FA <br>
     * <br>
     * ���͒l�F<br>
     * (����) namespaceURI : "namespaceURI"<br>
     * (����) localName : "localName"<br>
     * (����) rawName : "rawName"<br>
     * (����) Attributes : new AttributesImpl()<br>
     * (����) atRoot : False<br>
     * (����) preserveWhitespace : True<br>
     * 
     * <br>
     * ���Ғl�F<br>
     * (��ԕω�) Writer : ���[�g�m�[�h�� xml:space="preserve" ����<br>
     * (��ԕω�) atRoot : False<br>
     * 
     * <br>
     * ���[�g�m�[�h��xml:space="preserve" ���t������Ă��Ȃ����Ƃ��m�F���� <br>
     * 
     * @throws Exception
     *             ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("deprecation")
    public void testStartElementStringStringStringAttributes03() throws Exception {
        // �O����
        Writer writer = new StringWriter();
    
        XMLSerializerEx serializerEx = new XMLSerializerEx();
        serializerEx.setOutputCharStream(writer);
        serializerEx.preserveWhitespace = true;
        serializerEx.atRoot = false;
    
        Attributes attributes = new AttributesImpl();
    
        // �e�X�g���{
        serializerEx.startDocument();
        serializerEx.startElement("namespaceURI", "localName", "rawName", attributes);
        serializerEx.endElement("namespaceURI", "localName", "rawName");
        serializerEx.endDocument();
    
        // ����
        assertEquals(
                "<?xml version=\"1.0\"?>\n<rawName/>",
                writer.toString());
        assertFalse(serializerEx.atRoot);
    }

    /**
     * testStartElementStringStringStringAttributes02() <br>
     * <br>
     * 
     * (����n) <br>
     * �ϓ_�FA <br>
     * <br>
     * ���͒l�F<br>
     * (����) namespaceURI : "namespaceURI"<br>
     * (����) localName : "localName"<br>
     * (����) rawName : "rawName"<br>
     * (����) Attributes : new AttributesImpl()<br>
     * (����) atRoot : False<br>
     * (����) preserveWhitespace : False<br>
     * 
     * <br>
     * ���Ғl�F<br>
     * (��ԕω�) Writer : ���[�g�m�[�h�� xml:space="preserve" ����<br>
     * (��ԕω�) atRoot : False<br>
     * 
     * <br>
     * ���[�g�m�[�h��xml:space="preserve" ���t������Ă��Ȃ����Ƃ��m�F���� <br>
     * 
     * @throws Exception
     *             ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("deprecation")
    public void testStartElementStringStringStringAttributes04() throws Exception {
        // �O����
        Writer writer = new StringWriter();
    
        XMLSerializerEx serializerEx = new XMLSerializerEx();
        serializerEx.setOutputCharStream(writer);
        serializerEx.preserveWhitespace = false;
        serializerEx.atRoot = false;
    
        Attributes attributes = new AttributesImpl();
    
        // �e�X�g���{
        serializerEx.startDocument();
        serializerEx.startElement("namespaceURI", "localName", "rawName", attributes);
        serializerEx.endElement("namespaceURI", "localName", "rawName");
        serializerEx.endDocument();
    
        // ����
        assertEquals(
                "<?xml version=\"1.0\"?>\n<rawName/>",
                writer.toString());
        assertFalse(serializerEx.atRoot);
    }

    /**
     * testSetPreserveWhitespace01() <br>
     * <br>
     * 
     * (����n) <br>
     * �ϓ_�FA <br>
     * <br>
     * ���͒l�F<br>
     * (����) setPreserveWhitespace : true<br>
     * 
     * <br>
     * ���Ғl�F<br>
     * (��ԕω�) preserveWhitespace : true<br>
     * 
     * <br>
     * �Y���t�B�[���h�� true �ɐݒ肳��邱�Ƃ��m�F���� <br>
     * 
     * @throws Exception
     *             ���̃��\�b�h�Ŕ���������O
     */
    public void testSetPreserveWhitespace01() throws Exception {
        // �O����
        XMLSerializerEx serializerEx = new XMLSerializerEx();

        // �e�X�g���{
        serializerEx.setPreserveWhitespace(true);
    
        // ����
        assertEquals(true, serializerEx.preserveWhitespace);
    }

    /**
     * testSetPreserveWhitespace02() <br>
     * <br>
     * 
     * (����n) <br>
     * �ϓ_�FA <br>
     * <br>
     * ���͒l�F<br>
     * (����) setPreserveWhitespace : false<br>
     * 
     * <br>
     * ���Ғl�F<br>
     * (��ԕω�) preserveWhitespace : false<br>
     * 
     * <br>
     * �Y���t�B�[���h�� true �ɐݒ肳��邱�Ƃ��m�F���� <br>
     * 
     * @throws Exception
     *             ���̃��\�b�h�Ŕ���������O
     */
    public void testSetPreserveWhitespace02() throws Exception {
        // �O����
        XMLSerializerEx serializerEx = new XMLSerializerEx();

        // �e�X�g���{
        serializerEx.setPreserveWhitespace(false);
    
        // ����
        assertEquals(false, serializerEx.preserveWhitespace);
    }    
}
