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

import java.io.Writer;

import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Namespaces;
import org.xml.sax.AttributeList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributeListImpl;
import org.xml.sax.helpers.AttributesImpl;

/**
 * XMLSerializerを継承クラス。
 * 
 * <p>ルートノードにxml:space="preserve"を設定する。</p>
 * 
 */
@SuppressWarnings("deprecation")
public class XMLSerializerEx extends XMLSerializer {
    
    /**
     * <code>Sax</code>で利用する<code>CDATA</code>型。
     */
    public static final String CDATA = "CDATA";

    /**
     * <code>xml:space</code>の値、<code>preserve</code>。
     */
    public static final String PRESERVE_OPTION = "preserve";

    /**
     * ルートノードを通過したかどうか判別するためのフラグ。<br>
     * ルートノードにxml:space="preserve"を設定後、このフラグはfalseになる。
     */
    protected boolean atRoot = true;

    /**
     * ルートノードにxml:space="preserve"を設定するかどうか。
     * trueの場合に設定する。
     */
    protected boolean preserveWhitespace = true;

    /**
     * コンストラクタ。
     */
    @SuppressWarnings("deprecation")
    public XMLSerializerEx() {
        super();
    }

    /**
     * コンストラクタ。
     * 
     * @param writer 書き出しに使用する<code>Writer</code>インスタンス。
     * @param format 出力フォーマット。
     *          <code>null</code>を指定した場合はデフォルト値が使用される。
     */
    @SuppressWarnings("deprecation")
    public XMLSerializerEx(Writer writer, OutputFormat format) {
        super(writer, format);
    }


    /**
     * ルートノードの場合のみ、xml:space="preserve"を付与する。
     * 
     * @param tagName タグ名。
     * @param attrs 属性。
     * @throws SAXException SAXエラー。
     */
    @SuppressWarnings({ "deprecation", "static-access" })
    @Override
    public void startElement(String tagName, AttributeList attrs)
            throws SAXException {
        
        if(atRoot && isPreserveWhitespace()){
            if (attrs instanceof AttributeListImpl) {
                AttributeListImpl attr = (AttributeListImpl)attrs;
                attr.addAttribute(Marshaller.XML_SPACE_ATTR,
                        CDATA, PRESERVE_OPTION);
            }
            atRoot = false;
        }
        super.startElement(tagName, attrs);
    }

    /**
     * ルートノードの場合のみ、xml:space="preserve"を付与する。
     * 
     * @param namespaceURI ネームスペース<code>URI</code>。
     * @param localName ロケール名。
     * @param rawName 名前。
     * @param attrs 属性。
     * @throws SAXException SAXエラー。
     * @see org.apache.xml.serialize.XMLSerializer#startElement(java.lang.String, java.lang.String, java.lang.String, org.xml.sax.Attributes)
     */
    @SuppressWarnings("static-access")
    @Override
    public void startElement(String namespaceURI, String localName,
            String rawName, Attributes attrs) throws SAXException {
        if(atRoot && isPreserveWhitespace()){
            if (attrs instanceof AttributesImpl) {
                AttributesImpl attr = (AttributesImpl)attrs;
                attr.addAttribute(Namespaces.XML_NAMESPACE,
                        Marshaller.SPACE_ATTR,
                        Marshaller.XML_SPACE_ATTR,
                        CDATA, 
                        PRESERVE_OPTION);
            }
            atRoot = false;
        }
        super.startElement(namespaceURI, localName, rawName, attrs);
    }

    /**
     * ルートノードにxml:space="preserve"を設定するかを取得する。
     * <p>
     * デフォルト値はtrueである。
     *
     * @return ルートノードにxml:space="preserve"を設定する場合はtrue。
     */
    public boolean isPreserveWhitespace() {
        return preserveWhitespace;
    }

    /**
     * ルートノードにxml:space="preserve"を付与するかどうかを設定する。
     * <p>
     * デフォルト値はtrueである。
     * 
     * @param preserveWhitespace ルートノードにxml:space="preserve"を
     *      付与する場合はtrue。
     */
    public void setPreserveWhitespace(boolean preserveWhitespace) {
        this.preserveWhitespace = preserveWhitespace;
    }

}
