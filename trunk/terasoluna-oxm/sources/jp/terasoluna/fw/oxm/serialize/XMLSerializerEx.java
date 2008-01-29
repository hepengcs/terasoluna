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
 * XMLSerializer���p���N���X�B
 * 
 * <p>���[�g�m�[�h��xml:space="preserve"��ݒ肷��B</p>
 * 
 */
@SuppressWarnings("deprecation")
public class XMLSerializerEx extends XMLSerializer {
    
    /**
     * <code>Sax</code>�ŗ��p����<code>CDATA</code>�^�B
     */
    public static final String CDATA = "CDATA";

    /**
     * <code>xml:space</code>�̒l�A<code>preserve</code>�B
     */
    public static final String PRESERVE_OPTION = "preserve";

    /**
     * ���[�g�m�[�h��ʉ߂������ǂ������ʂ��邽�߂̃t���O�B<br>
     * ���[�g�m�[�h��xml:space="preserve"��ݒ��A���̃t���O��false�ɂȂ�B
     */
    protected boolean atRoot = true;

    /**
     * ���[�g�m�[�h��xml:space="preserve"��ݒ肷�邩�ǂ����B
     * true�̏ꍇ�ɐݒ肷��B
     */
    protected boolean preserveWhitespace = true;

    /**
     * �R���X�g���N�^�B
     */
    @SuppressWarnings("deprecation")
    public XMLSerializerEx() {
        super();
    }

    /**
     * �R���X�g���N�^�B
     * 
     * @param writer �����o���Ɏg�p����<code>Writer</code>�C���X�^���X�B
     * @param format �o�̓t�H�[�}�b�g�B
     *          <code>null</code>���w�肵���ꍇ�̓f�t�H���g�l���g�p�����B
     */
    @SuppressWarnings("deprecation")
    public XMLSerializerEx(Writer writer, OutputFormat format) {
        super(writer, format);
    }


    /**
     * ���[�g�m�[�h�̏ꍇ�̂݁Axml:space="preserve"��t�^����B
     * 
     * @param tagName �^�O���B
     * @param attrs �����B
     * @throws SAXException SAX�G���[�B
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
     * ���[�g�m�[�h�̏ꍇ�̂݁Axml:space="preserve"��t�^����B
     * 
     * @param namespaceURI �l�[���X�y�[�X<code>URI</code>�B
     * @param localName ���P�[�����B
     * @param rawName ���O�B
     * @param attrs �����B
     * @throws SAXException SAX�G���[�B
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
     * ���[�g�m�[�h��xml:space="preserve"��ݒ肷�邩���擾����B
     * <p>
     * �f�t�H���g�l��true�ł���B
     *
     * @return ���[�g�m�[�h��xml:space="preserve"��ݒ肷��ꍇ��true�B
     */
    public boolean isPreserveWhitespace() {
        return preserveWhitespace;
    }

    /**
     * ���[�g�m�[�h��xml:space="preserve"��t�^���邩�ǂ�����ݒ肷��B
     * <p>
     * �f�t�H���g�l��true�ł���B
     * 
     * @param preserveWhitespace ���[�g�m�[�h��xml:space="preserve"��
     *      �t�^����ꍇ��true�B
     */
    public void setPreserveWhitespace(boolean preserveWhitespace) {
        this.preserveWhitespace = preserveWhitespace;
    }

}
