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

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import jp.terasoluna.fw.oxm.xsd.SchemaValidator;
import jp.terasoluna.fw.oxm.xsd.exception.NamespaceNotFoundException;
import jp.terasoluna.fw.oxm.xsd.exception.NamespaceNotUniqueException;
import jp.terasoluna.fw.oxm.xsd.exception.ParserIOException;
import jp.terasoluna.fw.oxm.xsd.exception.ParserNotSupportedException;
import jp.terasoluna.fw.oxm.xsd.exception.ParserSAXException;
import jp.terasoluna.fw.oxm.xsd.exception.PropertyIOException;
import jp.terasoluna.fw.oxm.xsd.exception.SchemaFileNotFoundException;
import jp.terasoluna.fw.oxm.xsd.message.ErrorMessages;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.xerces.impl.Constants;
import org.apache.xerces.parsers.DOMParser;
import org.apache.xerces.util.XMLGrammarPoolImpl;
import org.apache.xerces.xni.grammars.XMLGrammarPool;
import org.apache.xerces.xni.parser.XMLParserConfiguration;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;

/**
 * XML�f�[�^�̌`���`�F�b�N���s��SchemaValidator�����N���X�B
 * <p>
 * �`���`�F�b�N�ɂ̓X�L�[�}��`�t�@�C�����g�p����B �{�N���X�̓V���O���g���Ƃ��Ďg�p���邱�ƁB
 * </p>
 * <p>
 * �X�L�[�}��`�t�@�C���͕ϊ����s���I�u�W�F�N�g�̃N���X�� �����p�b�P�[�W�A �������O�A�g���q�h.xsd�h�Ŕz�u���邱�ƁB <br>
 * ��jXML�ϊ��Ώۂ̃I�u�W�F�N�g���usample.SampleBean�v�N���X�̏ꍇ�A
 * �X�L�[�}��`�t�@�C���̓N���X�p�X��́usample/SampleBean.xsd�v �t�@�C���ƂȂ�B
 * </p>
 * <p>
 * XML�̃p�[�X�ɂ�DOM�p�[�T���K�v�ƂȂ�B TERASOLUNA�ł͏ڍׂȃG���[�����擾���邽�߂ɁA �g������{@link jp.terasoluna.fw.oxm.xsd.xerces.XML11ConfigurationEx}�A
 * {@link jp.terasoluna.fw.oxm.xsd.xerces.XMLErrorReporterEx}�𗘗p����B
 * </p>
 * 
 * <hr>
 * <h4>���O��Ԃ̐ݒ�</h4>
 * <p>
 * �{�N���X�́A�X�L�[�}��`�ɖ��O��Ԃ��g�p���邱�Ƃ��ł���B ���O��Ԃ��g�p����ꍇ�A{@link #namespace}������true��ݒ肷��K�v������B
 * �f�t�H���g��false�ŁA���O��Ԃ��g�p���Ȃ��ݒ�ɂȂ��Ă���B ���O��Ԃ��g�p����ꍇ�A�X�L�[�}��`�t�@�C���̃L���b�V�����s�����Ƃ��ł���B
 * ���O��Ԃ��g�p���邽�߂ɂ́A�ȉ��̐ݒ肪�K�v�ł���B
 * <ul>
 * <li>�v���p�e�B�t�@�C��(namespaces.properties)�ɁA XML�ϊ��Ώۂ̃N���X���Ǝg�p���閼�O��Ԃ̑Ή��t�����L�q����B
 * �i�ڍׂ͉��L�̐ݒ����Q�Ɓj</li>
 * <li>�v���p�e�B�t�@�C��(namespaces.properties)���N���X�p�X��ɒu���B</li>
 * <li>�{�N���X�̃C���X�^���X�𐶐��inew�j����B</li>
 * <li>�C���X�^���X��{@link #namespace}������true��ݒ肷��B</li>
 * <li>�X�L�[�}��`�̃L���b�V���ݒ������B�f�t�H���g�̓L���b�V�����L���ł���B �L���b�V���𖳌��ɂ���ꍇ�A�C���X�^���X��{@link #cache}������false�ɐݒ肷��B</li>
 * <li>�C���X�^���X��{@link #initNamespaceProperties()}���\�b�h���Ăяo���āA �v���p�e�B�t�@�C����ǂݍ��ށB</li>
 * </ul>
 * 
 * <br>
 * �y���O��Ԃ̃v���p�e�B�t�@�C���inemaspace.properties�j�̐ݒ��z<br>
 * ��jXML�ϊ��Ώۂ̃I�u�W�F�N�g���usample.SampleBean�v�N���X�A���O��Ԃ�
 * �uhttp://xxx.co.jp/sample/samplebean�v�̏ꍇ�A �v���p�e�B�t�@�C���ɉ��L�̐ݒ���s���B<br>
 * <br>
 * jp.terasoluna.sample2.dto.SumParam.Namespace =
 * http://xxx.co.jp/sample/samplebean
 * </p>
 * 
 * <hr>
 * <p>
 * <strong>���O��Ԃ��g�p���Ȃ��ꍇ�̃X�L�[�}�t�@�C���ݒ��</strong>
 * </p>
 * 
 * <p>
 * �y�`���`�F�b�N�Ώۂ�XML�f�[�^�z <code><pre>
 *   &lt;sample-dto&gt;
 *     &lt;user-id&gt;15&lt;/user-id&gt;
 *     &lt;user-name&gt;user1&lt;/user-name&gt;
 *     &lt;item&gt;
 *       &lt;id&gt;100&lt;/id&gt;
 *       &lt;name&gt;item1&lt;/name&gt;
 *       &lt;price&gt;1000&lt;/price&gt;
 *     &lt;/item&gt;
 *     &lt;item&gt;
 *       &lt;id&gt;101&lt;/id&gt;
 *       &lt;name&gt;item2&lt;/name&gt;
 *       &lt;price&gt;2000&lt;/price&gt;
 *     &lt;/item&gt;
 *   &lt;/sample-dto&gt;
 * </pre></code>
 * </p>
 * 
 * <p>
 * �y�X�L�[�}��`�t�@�C���̐ݒ��z<br>
 * <code><pre>
 *   &lt;?xml version=&quot;1.0&quot; encoding=&quot;UTF-8&quot;?&gt;
 *   &lt;xs:schema xmlns:xs=&quot;http://www.w3.org/2001/XMLSchema&quot;&gt;
 *     &lt;xs:element name=&quot;sample-dto&quot; type=&quot;sample-dto-type&quot;/&gt;
 *     &lt;xs:complexType name=&quot;sample-dto-type&quot;&gt;
 *       &lt;xs:sequence&gt;
 *         &lt;xs:element name=&quot;user-id&quot; type=&quot;xs:int&quot; /&gt;
 *         &lt;xs:element name=&quot;user-name&quot; type=&quot;xs:string&quot; /&gt;
 *         &lt;xs:element name=&quot;item&quot; type=&quot;item-type&quot; minOccurs=&quot;0&quot; maxOccurs=&quot;unbounded&quot; /&gt;
 *       &lt;/xs:sequence&gt;
 *     &lt;/xs:complexType&gt;
 *     &lt;xs:complexType name=&quot;item-type&quot;&gt;
 *       &lt;xs:sequence&gt;
 *          &lt;xs:element name=&quot;id&quot; type=&quot;xs:int&quot; /&gt;
 *         &lt;xs:element name=&quot;name&quot; type=&quot;xs:string&quot; /&gt;
 *         &lt;xs:element name=&quot;price&quot; type=&quot;xs:int&quot; /&gt;
 *       &lt;/xs:sequence&gt;
 *     &lt;/xs:complexType&gt;
 *   &lt;/xs:schema&gt;
 * </pre></code>
 * </p>
 * 
 * <hr>
 * <p>
 * <strong>���O��Ԃ��g�p����ꍇ�̃X�L�[�}�t�@�C���ݒ��</strong>
 * </p>
 * 
 * �y�`���`�F�b�N�Ώۂ�XML�f�[�^�T���v���z
 * 
 * &lt;sample-dto xmlns=&quot;http://xxx.co.jp/sample/samplebean&quot;&gt;
 * &lt;user-id&gt;15&lt;/user-id&gt; &lt;user-name&gt;user1&lt;/user-name&gt;
 * &lt;item&gt; &lt;id&gt;100&lt;/id&gt; &lt;name&gt;item1&lt;/name&gt;
 * &lt;price&gt;1000&lt;/price&gt; &lt;/item&gt; &lt;item&gt;
 * &lt;id&gt;101&lt;/id&gt; &lt;name&gt;item2&lt;/name&gt;
 * &lt;price&gt;2000&lt;/price&gt; &lt;/item&gt; &lt;/sample-dto&gt;
 * 
 * �y�X�L�[�}��`�t�@�C���̐ݒ�T���v���z
 * 
 * &lt;?xml version=&quot;1.0&quot; encoding=&quot;UTF-8&quot;?&gt;
 * &lt;xs:schema xmlns:xs=&quot;http://www.w3.org/2001/XMLSchema&quot;
 * xmlns:tns=&quot;http://xxx.co.jp/sample/samplebean&quot;
 * targetNamespace=&quot;http://xxx.co.jp/sample/samplebean&quot;
 * elementFormDefault=&quot;qualified&quot;&gt; &lt;xs:element
 * name=&quot;sample-dto&quot; type=&quot;tns:sample-dto-type&quot;/&gt;
 * &lt;xs:complexType name=&quot;sample-dto-type&quot;&gt; &lt;xs:sequence&gt;
 * &lt;xs:element name=&quot;user-id&quot; type=&quot;xs:int&quot;/&gt;
 * &lt;xs:element name=&quot;user-name&quot; type=&quot;xs:string&quot;/&gt;
 * &lt;xs:element name=&quot;item&quot; type=&quot;tns:item-type&quot;
 * minOccurs=&quot;0&quot; maxOccurs=&quot;unbounded&quot;/&gt;
 * &lt;/xs:sequence&gt; &lt;/xs:complexType&gt; &lt;xs:complexType
 * name=&quot;item-type&quot;&gt; &lt;xs:sequence&gt; &lt;xs:element
 * name=&quot;id&quot; type=&quot;xs:int&quot;/&gt; &lt;xs:element
 * name=&quot;name&quot; type=&quot;xs:string&quot;/&gt; &lt;xs:element
 * name=&quot;price&quot; type=&quot;xs:int&quot;/&gt; &lt;/xs:sequence&gt;
 * &lt;/xs:complexType&gt; &lt;/xs:schema&gt;
 * 
 * @see jp.terasoluna.fw.oxm.xsd.xerces.XML11ConfigurationEx
 * @see jp.terasoluna.fw.oxm.xsd.message.ErrorMessages
 * @see jp.terasoluna.fw.oxm.xsd.xerces.XMLErrorReporterEx
 * @see jp.terasoluna.fw.web.rich.springmvc.bind.XMLServletRequestDataBinder
 * @see org.apache.xerces.util.XMLGrammarPoolImpl
 * 
 */
public class SchemaValidatorImpl implements SchemaValidator {

    /**
     * ���O�N���X�B
     */
    private static Log log = LogFactory.getLog(SchemaValidatorImpl.class);

    /**
     * �p�b�P�[�W�̃Z�p���[�^�B
     */
    public static final String NESTED_PACKAGE_SEPARATOR = ".";

    /**
     * �t�H���_�̃Z�p���[�^�B
     */
    public static final String NESTED_FOLDER_SEPARATOR = "/";

    /**
     * �X�L�[�}��`�t�@�C���̃T�t�B�b�N�X�B
     */
    public static final String XSD_FILE_SUFFIX = ".xsd";

    /**
     * �v���p�e�B�t�@�C������l�[���X�y�[�X�����擾����L�[�̐ڔ����B
     */
    private static final String NAME_SPACE_SUFFIX = ".Namespace";

    /**
     * �N���X���Ɩ��O��Ԃ̃}�b�s���O���`�����v���p�e�B�t�@�C�����B
     */
    protected String namespacePropertyFileName = "namespaces.properties";

    /**
     * �X�L�[�}��`�̃L���b�V���g�p�ݒ�B �����O��Ԃ��g�p����ꍇ�̂݁A�L���b�V�����s�Ȃ���B
     */
    protected boolean cache = true;

    /**
     * ���O��Ԃ̎g�p�ݒ�B
     */
    protected boolean namespace = false;

    /**
     * ���O��Ԃ̕��@�v�[���B
     */
    protected XMLGrammarPool grammarPool = new XMLGrammarPoolImpl();

    /**
     * �N���X���Ɩ��O��Ԃ̃}�b�s���O���`�����v���p�e�B�B
     */
    protected Properties namespaceProperties = null;

    /**
     * ���O��Ԃ̃`�F�b�N�ݒ�B
     */
    protected boolean namespaceCheck = true;

    /**
     * �X�L�[�}��`�̃L���b�V���g�p�ݒ���s���B
     * 
     * @param cache
     *            �X�L�[�}��`�t�@�C���̃L���b�V�����s���ꍇ�Atrue
     */
    public void setCache(boolean cache) {
        this.cache = cache;
    }

    /**
     * ���O��Ԃ̎g�p�ݒ���s���B
     * 
     * @param namespace
     *            ���O��Ԃ��g�p����ꍇ�Atrue
     */
    public void setNamespace(boolean namespace) {
        this.namespace = namespace;
    }

    /**
     * ���O��Ԃ̃`�F�b�N�ݒ���s���B
     * 
     * @param namespaceCheck
     *            ���O��Ԃ��`�F�b�N����ꍇ�Atrue
     */
    public void setNamespaceCheck(boolean namespaceCheck) {
        this.namespaceCheck = namespaceCheck;
    }

    /**
     * �N���X���Ɩ��O��Ԃ̃}�b�s���O���`�����v���p�e�B��ݒ肷��
     * 
     * @param namespaceProperties
     *            �N���X���Ɩ��O��Ԃ̃}�b�s���O���`�����v���p�e�B
     */
    public void setNamespaceProperties(Properties namespaceProperties) {
        this.namespaceProperties = namespaceProperties;
    }

    /**
     * �N���X���Ɩ��O��Ԃ̃}�b�s���O���`�����v���p�e�B�t�@�C������ݒ肷��B
     * 
     * @param namespacePropertyFileName
     *            �N���X���Ɩ��O��Ԃ̃}�b�s���O���`�����v���p�e�B�t�@�C����
     */
    public void setNamespacePropertyFileName(String namespacePropertyFileName) {
        this.namespacePropertyFileName = namespacePropertyFileName;
    }

    /**
     * ���������ɖ��O��Ԃ��v���p�e�B�ɐݒ肷��B
     */
    public void initNamespaceProperties() {
        loadNamespaceProperties();

        if (this.namespaceCheck) {
            checkNamespaceProperties();
        }
    }

    /**
     * ���O��Ԃ���`���ꂽ�v���p�e�B�t�@�C����ǂݍ��݁A�����ɃZ�b�g����B
     */
    protected void loadNamespaceProperties() {
        // propertyName��null�܂��͋󕶎��̏ꍇ�A�Ȍ�̏������s��Ȃ��B
        if (namespacePropertyFileName == null
                || "".equals(namespacePropertyFileName)) {
            return;
        }

        // �J�����g�X���b�h�̃R���e�L�X�g�N���X���[�_���g�p�����
        // WEB-INF/classes�̃v���p�e�B�t�@�C����ǂނ��Ƃ��ł��Ȃ��ꍇ������B
        // ����JNLP�Ń��\�[�X���擾����ɂ́A���C���X���b�h�̃R���e�L�X�g
        // �N���X���[�_�𗘗p���Ȃ���΂Ȃ�Ȃ����ߗ����𕹗p����B
        InputStream is = Thread.currentThread().getContextClassLoader()
                .getResourceAsStream(namespacePropertyFileName);
        if (is == null) {
            is = this.getClass().getResourceAsStream(
                    "/" + namespacePropertyFileName);
            if (is == null) {
                log.warn("Can not find property-file ["
                        + namespacePropertyFileName + "]");
                return;
            }
        }

        this.namespaceProperties = new Properties();

        try {
            this.namespaceProperties.load(is);
        } catch (IOException e) {
            log.error("Can not read property-file ["
                    + namespacePropertyFileName);
            throw new PropertyIOException(e);
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                log.error("Failed to close inputStream.", e);
            }
        }
    }

    /**
     * ���O��Ԃ��d�����Ă��Ȃ����̃`�F�b�N���s���B
     */
    protected void checkNamespaceProperties() {
        StringBuilder logStr = new StringBuilder();
        // namespaceProperties��null�܂��͋�̏ꍇ�A�Ȍ�̏������s��Ȃ��B
        if (namespaceProperties == null || namespaceProperties.isEmpty()) {
            return;
        }

        List<String> namespacePropertiesList = new ArrayList<String>();
        for (Object namespaceKey : namespaceProperties.keySet()) {
            String namespaceValue = namespaceProperties
                    .getProperty((String) namespaceKey);
            // ���O��Ԃ��d�����Ă���ꍇ�A�G���[���O�̏o�͂��s���A��O���X���[����B
            if (namespacePropertiesList.contains(namespaceValue)) {
                logStr.setLength(0);
                logStr.append("Namespace name [");
                logStr.append(namespaceValue);
                logStr.append("] is not unique. ");
                logStr.append("Namespace must be unique. ");
                logStr.append("(key = [");
                logStr.append(namespaceKey);
                logStr.append("])");
                log.error(logStr.toString());
                throw new NamespaceNotUniqueException();
            }
            namespacePropertiesList.add(namespaceValue);
        }
    }

    /**
     * XML�f�[�^�̌`���`�F�b�N���s�����\�b�h�B
     * <p>
     * XML�f�[�^��DOM�c���[�ɕϊ�����ۂɁAXML�X�L�[�}�ɂ�� �`���`�F�b�N�i�Ó������؁j�����s����B<br>
     * �`���`�F�b�N�ɂ́A�X�L�[�}��`�t�@�C����p����B
     * </p>
     * <p>
     * ����Ɍ`���`�F�b�N���I�������ꍇ�ADOM�c���[��ԋp����B<br>
     * ���[�U�̓��͂������ƍl������f�[�^�^�̃G���[�����������ꍇ�A������errorMessages�ɃG���[�����i�[���āAnull��ԋp����B<br>
     * ����ȊO�̃G���[�Ɋւ��ẮAOXMappingException�̃T�u�N���X�Ƀ��b�v���ăX���[����B
     * </p>
     * 
     * @param in
     *            XML�f�[�^
     * @param object
     *            �ϊ��Ώۂ̃I�u�W�F�N�g
     * @param errorMessages
     *            �G���[���b�Z�[�W���i�[�����C���X�^���X
     * @return DOM�c���[
     */
    public Document validate(InputStream in, Object object,
            ErrorMessages errorMessages) {
        if (in == null) {
            log.error("InputStream is null.");
            throw new IllegalArgumentException("InputStream is null.");
        }
        if (errorMessages == null) {
            log.error("ErrorMessages is null.");
            throw new IllegalArgumentException("ErrorMessages is null.");
        }

        // DOM�p�[�T�̍쐬
        DOMParser parser = null;
        try {
            parser = createDomParser(object);
            setCommonParserProperty(parser, errorMessages);
            setCommonParserFeature(parser);
        } catch (SAXNotRecognizedException e) {
            // �F���ł��Ȃ��L�[���ݒ肳�ꂽ�ꍇ�ɃX���[������O
            log.error("Schema property error.", e);
            throw new ParserNotSupportedException(e);
        } catch (SAXNotSupportedException e) {
            // �T�|�[�g����Ă��Ȃ��l���ݒ肳�ꂽ�ꍇ�ɃX���[������O
            log.error("Schema property error.", e);
            throw new ParserNotSupportedException(e);
        }

        // �p�[�X
        try {
            parser.parse(new InputSource(in));
        } catch (SAXException e) {
            log.error("Schema parse error.", e);
            throw new ParserSAXException(e);
        } catch (IOException e) {
            log.error("Schema io error.", e);
            throw new ParserIOException(e);
        }

        if (errorMessages.hasErrorMessage()) {
            return null;
        }
        return parser.getDocument();
    }

    /**
     * DOM�p�[�T�𐶐�����B
     * 
     * @param object
     *            �p�[�X�Ώۂ̃I�u�W�F�N�g
     * @return DOM�p�[�T
     * @throws SAXNotSupportedException
     *             �F���ł��Ȃ��L�[���ݒ肳�ꂽ�ꍇ�ɃX���[������O
     * @throws SAXNotRecognizedException
     *             �T�|�[�g����Ă��Ȃ��l���ݒ肳�ꂽ�ꍇ�ɃX���[������O
     */
    protected DOMParser createDomParser(Object object)
            throws SAXNotRecognizedException, SAXNotSupportedException {
        DOMParser parser = new DOMParser(createXmlParserConfiguration());

        // �X�L�[�}��`�t�@�C����URL
        URL schemaURL = getUrl(object);
        if (schemaURL == null) {
            log.error("Schema file is not found. Set schema file in "
                    + "[root-classpath]/" + getSchemaFilePath(object));
            throw new SchemaFileNotFoundException();
        }

        // �X�L�[�}��`�t�@�C���̃��P�[�V�����ݒ�
        if (namespace) {
            StringBuilder key = new StringBuilder();
            key.append(Constants.XERCES_PROPERTY_PREFIX);
            key.append(Constants.SCHEMA_LOCATION);

            StringBuilder location = new StringBuilder();
            location.append(getNamespaceName(object));
            location.append(" ");
            location.append(schemaURL.toExternalForm());

            parser.setProperty(key.toString(), location.toString());
        } else {
            parser.setProperty(Constants.XERCES_PROPERTY_PREFIX
                    + Constants.SCHEMA_NONS_LOCATION, schemaURL
                    .toExternalForm());
        }
        return parser;
    }

    /**
     * XMLParserConfiguration�𐶐�����B ���O��ԂƃL���b�V���̎g�p��L���ɂ��Ă���ꍇ�A
     * �X�L�[�}�t�@�C���̃L���b�V���𗘗p����B
     * 
     * @return XMLParserConfiguration XML�p�[�T�̐ݒ��ێ�����I�u�W�F�N�g
     */
    protected XMLParserConfiguration createXmlParserConfiguration() {
        if (namespace && cache) {
            // ���O��Ԃ̕��@�v�[����ݒ肷�邱�ƂŃL���b�V����L���ɂ���
            return new XML11ConfigurationEx(grammarPool);
        }
        return new XML11ConfigurationEx();
    }

    /**
     * �p�[�T���ʂ�Property��ݒ肷��B
     * 
     * @param parser
     *            DOM�p�[�T
     * @param errorMessages
     *            �G���[
     * @throws SAXNotRecognizedException
     *             �F���ł��Ȃ��L�[���ݒ肳�ꂽ�ꍇ�ɃX���[������O
     * @throws SAXNotSupportedException
     *             �T�|�[�g����Ă��Ȃ��l���ݒ肳�ꂽ�ꍇ�ɃX���[������O
     */
    protected void setCommonParserProperty(DOMParser parser,
            ErrorMessages errorMessages) throws SAXNotRecognizedException,
            SAXNotSupportedException {
        // �p�[�X���ɔ��������G���[����������C���X�^���X
        parser.setProperty(Constants.XERCES_PROPERTY_PREFIX
                + Constants.ERROR_REPORTER_PROPERTY, new XMLErrorReporterEx(
                errorMessages));
    }

    /**
     * �p�[�T���ʂ�Feature��ݒ肷��
     * 
     * @param parser
     *            DOM�p�[�T
     * @throws SAXNotRecognizedException
     *             �F���ł��Ȃ��L�[���ݒ肳�ꂽ�ꍇ�ɃX���[������O
     * @throws SAXNotSupportedException
     *             �T�|�[�g����Ă��Ȃ��l���ݒ肳�ꂽ�ꍇ�ɃX���[������O
     */
    protected void setCommonParserFeature(DOMParser parser)
            throws SAXNotRecognizedException, SAXNotSupportedException {
        // ���ׂĂ̑Ó��������G���[��ʒm����
        parser.setFeature(Constants.SAX_FEATURE_PREFIX
                + Constants.VALIDATION_FEATURE, true);

        // �`���`�F�b�N�ɃX�L�[�}��`�t�@�C�����g�p����ݒ�
        parser.setFeature(Constants.XERCES_FEATURE_PREFIX
                + Constants.SCHEMA_VALIDATION_FEATURE, true);
    }

    /**
     * URL���擾����B
     * <p>
     * �����̃I�u�W�F�N�g�Ɠ���̃N���X�p�X����A��`�t�@�C�����擾����
     * </p>
     * 
     * @param object
     *            �I�u�W�F�N�g
     * @return ���\�[�X��URL�C���X�^���X
     */
    protected URL getUrl(Object object) {
        return Thread.currentThread().getContextClassLoader().getResource(
                getSchemaFilePath(object));
    }

    /**
     * �X�L�[�}�t�@�C���̃p�X���擾����B
     * 
     * @param object
     *            �X�L�[�}�`�F�b�N�Ώۂ̃I�u�W�F�N�g
     * @return �X�L�[�}�t�@�C���̃p�X
     */
    protected String getSchemaFilePath(Object object) {
        if (object == null) {
            log.error("Argument is null.");
            throw new IllegalArgumentException("Argument is null.");
        }

        StringBuilder retStr = new StringBuilder();
        retStr.append(object.getClass().getName().replace(
                NESTED_PACKAGE_SEPARATOR, NESTED_FOLDER_SEPARATOR));
        retStr.append(XSD_FILE_SUFFIX);
        return retStr.toString();
    }

    /**
     * �v���p�e�B�t�@�C�����疼�O��Ԃ��擾����B ���O��Ԃ��g�p���Ȃ��ꍇ�Anull��Ԃ��B �K�v�Ȗ��O��Ԃ��ݒ肳��Ă��Ȃ��ꍇ�A���s����O�𓊂���B
     * 
     * @param object
     *            �I�u�W�F�N�g
     * @return ���\�[�X��URL�C���X�^���X
     */
    protected String getNamespaceName(Object object) {
        if (object == null) {
            log.error("Argument is null.");
            throw new IllegalArgumentException("Argument is null.");
        }

        // ���O��Ԃ��g��Ȃ�
        if (!namespace) {
            return null;
        }

        // ���O��Ԃ��`����t�@�C�����Ȃ�
        if (this.namespaceProperties == null) {
            String message = "Namespace property is not set. " + "Put "
                    + namespacePropertyFileName + " file on your classpath, "
                    + "and call SchemaValidatorImpl initNamespaceProperties() "
                    + "after instanciate.";
            log.error(message);
            throw new IllegalStateException(message);
        }

        // ���O��Ԃ��擾����
        StringBuilder namespaceKey = new StringBuilder(object.getClass()
                .getName());
        namespaceKey.append(NAME_SPACE_SUFFIX);
        String namespaceName = namespaceProperties.getProperty(namespaceKey
                .toString());

        if (namespaceName == null) {
            log.error("Schema namespace is not found. Set namespace key - "
                    + namespaceKey.toString() + " in "
                    + namespacePropertyFileName + " file.");
            throw new NamespaceNotFoundException();
        }
        return namespaceName;
    }
}
