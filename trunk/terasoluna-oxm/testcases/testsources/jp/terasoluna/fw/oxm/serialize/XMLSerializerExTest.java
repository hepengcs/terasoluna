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
 * クラスのブラックボックステスト。
 * 
 * <p>
 * <h4>【クラスの概要】</h4>
 * Castorに渡すSerializerクラス。<br>
 * 前提条件：<br>
 * </p>
 * 
 * @see jp.terasoluna.fw.oxm.serialize.XMLSerializerEx
 */
public class XMLSerializerExTest extends TestCase {

    /**
     * このテストケースを実行する為の GUI アプリケーションを起動する。
     * 
     * @param args
     *            java コマンドに設定されたパラメータ
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(XMLSerializerExTest.class);
    }

    /**
     * 初期化処理を行う。
     * 
     * @throws Exception
     *             このメソッドで発生した例外
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    /**
     * 終了処理を行う。
     * 
     * @throws Exception
     *             このメソッドで発生した例外
     * @see junit.framework.TestCase#tearDown()
     */
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * コンストラクタ。
     * 
     * @param name
     *            このテストケースの名前。
     */
    public XMLSerializerExTest(String name) {
        super(name);
    }

    /**
     * testStartElementStringAttributeList01() <br>
     * <br>
     * 
     * (正常系) <br>
     * 観点：A <br>
     * <br>
     * 入力値：<br>
     * (引数) tagName : "element"<br>
     * (引数) AttributeList : new AttributeListImpl()<br>
     * (引数) atRoot : True<br>
     * (引数) preserveWhitespace : True<br>
     * 
     * <br>
     * 期待値：<br>
     * (状態変化) Writer : ルートノードに xml:space="preserve" 有り<br>
     * (状態変化) atRoot : False<br>
     * 
     * <br>
     * ルートノードにxml:space="preserve" が付加されていることを確認する <br>
     * 
     * @throws Exception
     *             このメソッドで発生した例外
     */
    @SuppressWarnings("deprecation")
    public void testStartElementStringAttributeList01() throws Exception {
        // 前処理
        Writer writer = new StringWriter();

        XMLSerializerEx serializerEx = new XMLSerializerEx(writer, null);
        serializerEx.preserveWhitespace = true;
        serializerEx.atRoot = true;

        AttributeList attributeList = new AttributeListImpl();

        // テスト実施
        serializerEx.startDocument();
        serializerEx.startElement("element", attributeList);
        serializerEx.endElement("element");
        serializerEx.endDocument();

        // 判定
        assertEquals(
                "<?xml version=\"1.0\"?>\n<element xml:space=\"preserve\"/>",
                writer.toString());
        assertFalse(serializerEx.atRoot);
    }

    /**
     * testStartElementStringAttributeList02() <br>
     * <br>
     * 
     * (正常系) <br>
     * 観点：A <br>
     * <br>
     * 入力値：<br>
     * (引数) tagName : "element"<br>
     * (引数) AttributeList : new AttributeListImpl()<br>
     * (引数) atRoot : True<br>
     * (引数) preserveWhitespace : False<br>
     * 
     * <br>
     * 期待値：<br>
     * (状態変化) Writer : ルートノードに xml:space="preserve" 無し<br>
     * (状態変化) atRoot : True<br>
     * 
     * <br>
     * ルートノードにxml:space="preserve" が付加されていないことを確認する <br>
     * 
     * @throws Exception
     *             このメソッドで発生した例外
     */
    @SuppressWarnings("deprecation")
    public void testStartElementStringAttributeList02() throws Exception {
        // 前処理
        Writer writer = new StringWriter();

        XMLSerializerEx serializerEx = new XMLSerializerEx(writer, null);
        serializerEx.preserveWhitespace = false;
        serializerEx.atRoot = true;

        AttributeList attributeList = new AttributeListImpl();

        // テスト実施
        serializerEx.startDocument();
        serializerEx.startElement("element", attributeList);
        serializerEx.endElement("element");
        serializerEx.endDocument();

        // 判定
        assertEquals(
                "<?xml version=\"1.0\"?>\n<element/>",
                writer.toString());
        assertTrue(serializerEx.atRoot);
    }

    /**
     * testStartElementStringAttributeList03() <br>
     * <br>
     * 
     * (正常系) <br>
     * 観点：A <br>
     * <br>
     * 入力値：<br>
     * (引数) tagName : "element"<br>
     * (引数) AttributeList : new AttributeListImpl()<br>
     * (引数) atRoot : False<br>
     * (引数) preserveWhitespace : True<br>
     * 
     * <br>
     * 期待値：<br>
     * (状態変化) Writer : ルートノードに xml:space="preserve" 無し<br>
     * (状態変化) atRoot : False<br>
     * 
     * <br>
     * ルートノードにxml:space="preserve" が付加されていないことを確認する <br>
     * 
     * @throws Exception
     *             このメソッドで発生した例外
     */
    @SuppressWarnings("deprecation")
    public void testStartElementStringAttributeList03() throws Exception {
        // 前処理
        Writer writer = new StringWriter();
    
        XMLSerializerEx serializerEx = new XMLSerializerEx(writer, null);
        serializerEx.preserveWhitespace = true;
        serializerEx.atRoot = false;
    
        AttributeList attributeList = new AttributeListImpl();
    
        // テスト実施
        serializerEx.startDocument();
        serializerEx.startElement("element", attributeList);
        serializerEx.endElement("element");
        serializerEx.endDocument();
    
        // 判定
        assertEquals(
                "<?xml version=\"1.0\"?>\n<element/>",
                writer.toString());
        assertFalse(serializerEx.atRoot);
    }

    /**
     * testStartElementStringAttributeList04() <br>
     * <br>
     * 
     * (正常系) <br>
     * 観点：A <br>
     * <br>
     * 入力値：<br>
     * (引数) tagName : "element"<br>
     * (引数) AttributeList : new AttributeListImpl()<br>
     * (引数) atRoot : False<br>
     * (引数) preserveWhitespace : False<br>
     * 
     * <br>
     * 期待値：<br>
     * (状態変化) Writer : ルートノードに xml:space="preserve" 無し<br>
     * (状態変化) atRoot : False<br>
     * 
     * <br>
     * ルートノードにxml:space="preserve" が付加されていないことを確認する <br>
     * 
     * @throws Exception
     *             このメソッドで発生した例外
     */
    @SuppressWarnings("deprecation")
    public void testStartElementStringAttributeList04() throws Exception {
        // 前処理
        Writer writer = new StringWriter();
    
        XMLSerializerEx serializerEx = new XMLSerializerEx(writer, null);
        serializerEx.preserveWhitespace = false;
        serializerEx.atRoot = false;
    
        AttributeList attributeList = new AttributeListImpl();
    
        // テスト実施
        serializerEx.startDocument();
        serializerEx.startElement("element", attributeList);
        serializerEx.endElement("element");
        serializerEx.endDocument();
    
        // 判定
        assertEquals(
                "<?xml version=\"1.0\"?>\n<element/>",
                writer.toString());
        assertFalse(serializerEx.atRoot);
    }

    /**
     * testStartElementStringStringStringAttributes01() <br>
     * <br>
     * 
     * (正常系) <br>
     * 観点：A <br>
     * <br>
     * 入力値：<br>
     * (引数) namespaceURI : "namespaceURI"<br>
     * (引数) localName : "localName"<br>
     * (引数) rawName : "rawName"<br>
     * (引数) Attributes : new AttributesImpl()<br>
     * (引数) atRoot : True<br>
     * (引数) preserveWhitespace : True<br>
     * 
     * <br>
     * 期待値：<br>
     * (状態変化) Writer : ルートノードに xml:space="preserve" 有り<br>
     * (状態変化) atRoot : False<br>
     * 
     * <br>
     * ルートノードにxml:space="preserve" が付加されていることを確認する <br>
     * 
     * @throws Exception
     *             このメソッドで発生した例外
     */
    @SuppressWarnings("deprecation")
    public void testStartElementStringStringStringAttributes01() throws Exception {
        // 前処理
        Writer writer = new StringWriter();

        XMLSerializerEx serializerEx = new XMLSerializerEx();
        serializerEx.setOutputCharStream(writer);
        serializerEx.preserveWhitespace = true;
        serializerEx.atRoot = true;

        Attributes attributes = new AttributesImpl();

        // テスト実施
        serializerEx.startDocument();
        serializerEx.startElement("namespaceURI", "localName", "rawName", attributes);
        serializerEx.endElement("namespaceURI", "localName", "rawName");
        serializerEx.endDocument();

        // 判定
        assertEquals(
                "<?xml version=\"1.0\"?>\n<rawName xml:space=\"preserve\"/>",
                writer.toString());
        assertFalse(serializerEx.atRoot);
    }

    /**
     * testStartElementStringStringStringAttributes02() <br>
     * <br>
     * 
     * (正常系) <br>
     * 観点：A <br>
     * <br>
     * 入力値：<br>
     * (引数) namespaceURI : "namespaceURI"<br>
     * (引数) localName : "localName"<br>
     * (引数) rawName : "rawName"<br>
     * (引数) Attributes : new AttributesImpl()<br>
     * (引数) atRoot : True<br>
     * (引数) preserveWhitespace : False<br>
     * 
     * <br>
     * 期待値：<br>
     * (状態変化) Writer : ルートノードに xml:space="preserve" 無し<br>
     * (状態変化) atRoot : True<br>
     * 
     * <br>
     * ルートノードにxml:space="preserve" が付加されていないことを確認する <br>
     * 
     * @throws Exception
     *             このメソッドで発生した例外
     */
    @SuppressWarnings("deprecation")
    public void testStartElementStringStringStringAttributes02() throws Exception {
        // 前処理
        Writer writer = new StringWriter();

        XMLSerializerEx serializerEx = new XMLSerializerEx();
        serializerEx.setOutputCharStream(writer);
        serializerEx.preserveWhitespace = false;
        serializerEx.atRoot = true;

        Attributes attributes = new AttributesImpl();

        // テスト実施
        serializerEx.startDocument();
        serializerEx.startElement("namespaceURI", "localName", "rawName", attributes);
        serializerEx.endElement("namespaceURI", "localName", "rawName");
        serializerEx.endDocument();

        // 判定
        assertEquals(
                "<?xml version=\"1.0\"?>\n<rawName/>",
                writer.toString());
        assertTrue(serializerEx.atRoot);
    }
    
    /**
     * testStartElementStringStringStringAttributes01() <br>
     * <br>
     * 
     * (正常系) <br>
     * 観点：A <br>
     * <br>
     * 入力値：<br>
     * (引数) namespaceURI : "namespaceURI"<br>
     * (引数) localName : "localName"<br>
     * (引数) rawName : "rawName"<br>
     * (引数) Attributes : new AttributesImpl()<br>
     * (引数) atRoot : False<br>
     * (引数) preserveWhitespace : True<br>
     * 
     * <br>
     * 期待値：<br>
     * (状態変化) Writer : ルートノードに xml:space="preserve" 無し<br>
     * (状態変化) atRoot : False<br>
     * 
     * <br>
     * ルートノードにxml:space="preserve" が付加されていないことを確認する <br>
     * 
     * @throws Exception
     *             このメソッドで発生した例外
     */
    @SuppressWarnings("deprecation")
    public void testStartElementStringStringStringAttributes03() throws Exception {
        // 前処理
        Writer writer = new StringWriter();
    
        XMLSerializerEx serializerEx = new XMLSerializerEx();
        serializerEx.setOutputCharStream(writer);
        serializerEx.preserveWhitespace = true;
        serializerEx.atRoot = false;
    
        Attributes attributes = new AttributesImpl();
    
        // テスト実施
        serializerEx.startDocument();
        serializerEx.startElement("namespaceURI", "localName", "rawName", attributes);
        serializerEx.endElement("namespaceURI", "localName", "rawName");
        serializerEx.endDocument();
    
        // 判定
        assertEquals(
                "<?xml version=\"1.0\"?>\n<rawName/>",
                writer.toString());
        assertFalse(serializerEx.atRoot);
    }

    /**
     * testStartElementStringStringStringAttributes02() <br>
     * <br>
     * 
     * (正常系) <br>
     * 観点：A <br>
     * <br>
     * 入力値：<br>
     * (引数) namespaceURI : "namespaceURI"<br>
     * (引数) localName : "localName"<br>
     * (引数) rawName : "rawName"<br>
     * (引数) Attributes : new AttributesImpl()<br>
     * (引数) atRoot : False<br>
     * (引数) preserveWhitespace : False<br>
     * 
     * <br>
     * 期待値：<br>
     * (状態変化) Writer : ルートノードに xml:space="preserve" 無し<br>
     * (状態変化) atRoot : False<br>
     * 
     * <br>
     * ルートノードにxml:space="preserve" が付加されていないことを確認する <br>
     * 
     * @throws Exception
     *             このメソッドで発生した例外
     */
    @SuppressWarnings("deprecation")
    public void testStartElementStringStringStringAttributes04() throws Exception {
        // 前処理
        Writer writer = new StringWriter();
    
        XMLSerializerEx serializerEx = new XMLSerializerEx();
        serializerEx.setOutputCharStream(writer);
        serializerEx.preserveWhitespace = false;
        serializerEx.atRoot = false;
    
        Attributes attributes = new AttributesImpl();
    
        // テスト実施
        serializerEx.startDocument();
        serializerEx.startElement("namespaceURI", "localName", "rawName", attributes);
        serializerEx.endElement("namespaceURI", "localName", "rawName");
        serializerEx.endDocument();
    
        // 判定
        assertEquals(
                "<?xml version=\"1.0\"?>\n<rawName/>",
                writer.toString());
        assertFalse(serializerEx.atRoot);
    }

    /**
     * testSetPreserveWhitespace01() <br>
     * <br>
     * 
     * (正常系) <br>
     * 観点：A <br>
     * <br>
     * 入力値：<br>
     * (引数) setPreserveWhitespace : true<br>
     * 
     * <br>
     * 期待値：<br>
     * (状態変化) preserveWhitespace : true<br>
     * 
     * <br>
     * 該当フィールドが true に設定されることを確認する <br>
     * 
     * @throws Exception
     *             このメソッドで発生した例外
     */
    public void testSetPreserveWhitespace01() throws Exception {
        // 前処理
        XMLSerializerEx serializerEx = new XMLSerializerEx();

        // テスト実施
        serializerEx.setPreserveWhitespace(true);
    
        // 判定
        assertEquals(true, serializerEx.preserveWhitespace);
    }

    /**
     * testSetPreserveWhitespace02() <br>
     * <br>
     * 
     * (正常系) <br>
     * 観点：A <br>
     * <br>
     * 入力値：<br>
     * (引数) setPreserveWhitespace : false<br>
     * 
     * <br>
     * 期待値：<br>
     * (状態変化) preserveWhitespace : false<br>
     * 
     * <br>
     * 該当フィールドが true に設定されることを確認する <br>
     * 
     * @throws Exception
     *             このメソッドで発生した例外
     */
    public void testSetPreserveWhitespace02() throws Exception {
        // 前処理
        XMLSerializerEx serializerEx = new XMLSerializerEx();

        // テスト実施
        serializerEx.setPreserveWhitespace(false);
    
        // 判定
        assertEquals(false, serializerEx.preserveWhitespace);
    }    
}
