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

package jp.terasoluna.fw.oxm.mapper.castor;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import jp.terasoluna.fw.oxm.mapper.OXMapper;
import jp.terasoluna.fw.oxm.mapper.castor.exception.CastorCreateMarshallerIOException;
import jp.terasoluna.fw.oxm.mapper.castor.exception.CastorMappingException;
import jp.terasoluna.fw.oxm.mapper.castor.exception.CastorMarshalException;
import jp.terasoluna.fw.oxm.mapper.castor.exception.CastorUnsupportedEncodingException;
import jp.terasoluna.fw.oxm.mapper.castor.exception.CastorValidationException;
import jp.terasoluna.fw.oxm.serialize.XMLSerializerEx;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.xml.serialize.Method;
import org.apache.xml.serialize.OutputFormat;
import org.exolab.castor.mapping.Mapping;
import org.exolab.castor.mapping.MappingException;
import org.exolab.castor.xml.ClassDescriptorResolver;
import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;
import org.exolab.castor.xml.ValidationException;
import org.exolab.castor.xml.XMLClassDescriptorResolver;
import org.w3c.dom.Document;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;

/**
 * Castor�𗘗p�����I�u�W�F�N�g-XML�ϊ��N���X�B
 * 
 * <p>
 * �I�u�W�F�N�g-XML�̕ϊ��𖾎��I�Ɏw�肷��ꍇ�́ACastor�}�b�s���O��`�t�@�C�����K�v�ƂȂ�B
 * ���Castor�}�b�s���O��`�t�@�C���ŃI�u�W�F�N�g��XML�AXML���I�u�W�F�N�g ���݂̕ϊ����s�����Ƃ��ł���B
 * Castor�}�b�s���O��`�t�@�C���͕ϊ����s���I�u�W�F�N�g�̃N���X�� �����p�b�P�[�W�A�������O�A�g���q�h.xml�h�Ŕz�u���邱�ƁB
 * </p>
 * 
 * <p>
 * Castor�}�b�s���O��`�t�@�C�����ȗ������ꍇ�́ACastor�̃f�t�H���g�ϊ����[�����K�p�����B
 * </p>
 * 
 * <fieldset style="border:1pt solid black;padding:10px;width:100%;">
 * <legend>�f�t�H���g�ϊ����g�p����ꍇ�̃I�v�V����</legend> �f�t�H���g�ϊ����g�p����ꍇ�́ACastor��XML�l�[�~���O�I�v�V������
 * ���L�̂悤�� mixed �Ǝw�肵�Ă������ƁB<br>
 * <fieldset style="border:1pt solid black;padding:10px;width:100%;">
 * <legend>castor.properties</legend> org.exolab.castor.xml.naming=mixed
 * </fieldset> <br>
 * ���̃I�v�V�����̐ݒ�L���ɂ���āA���L�̂悤��Marshall���̏o��XML���ω�����B<br>
 * �f�t�H���g�̂܂܂��ƁAUnmarshall���ɖ�肪���邽�߁A���̃I�v�V������ݒ肷��B<br>
 * <fieldset style="border:1pt solid black;padding:10px;width:100%;">
 * <legend>�yorg.exolab.castor.xml.naming=lower (�f�t�H���g)�z</legend>
 * <code>UserBean �� &lt;user-bean&gt;</code><br>
 * </fieldset> <fieldset style="border:1pt solid
 * black;padding:10px;width:100%;"> <legend>�yorg.exolab.castor.xml.naming=mixed�z</legend>
 * <code>UserBean �� &lt;userBean&gt;</code> </fieldset> </fieldset>
 * 
 * <p>
 * ��jXML�ϊ��Ώۂ̃I�u�W�F�N�g���usample.SampleBean�v�N���X�̏ꍇ�A
 * Castor�}�b�s���O��`�t�@�C���̓N���X�p�X��́usample/SampleBean.xml�v �t�@�C���ƂȂ�B
 * </p>
 * 
 * <p>
 * Castor�}�b�s���O��`�t�@�C���̋L�q���@�́A Castor�̎d�l�ɏ����Ă���B �ڍׂ�TERASOUNA�̃h�L�������g���Q�Ƃ��邱�ƁB
 * </p>
 * 
 * <p>
 * �{�N���X�ł́A��xCastor�}�b�s���O��`�t�@�C����ǂݍ��ނ� �����ŃL���b�V�������B �L���b�V���𗘗p���Ȃ��ꍇ�A�{�N���X��
 * {@link #cache}������false�ɂ��邱�ƁB
 * </p>
 * 
 * <p>
 * <strong>�g�p��</strong>
 * </p>
 * 
 * <p>
 * �y�ϊ��Ώۂ̃I�u�W�F�N�g�z <code><pre>
 *       public class SampleDto {
 *           private int userid;
 *           private String username;
 *           private Item[] item;
 *           �E�E�E
 *           �igetter�Asetter�j
 *       }
 *       public class Item {
 *           private int id;
 *           private String name;
 *           private int price;
 *           �E�E�E
 *           �igetter�Asetter�j
 *       }
 * </pre></code>
 * </p>
 * 
 * <p>
 * �y�ϊ��Ώۂ�XML�f�[�^�z <code><pre>
 *       &lt;sample-dto&gt;
 *        &lt;user-id&gt;15&lt;/user-id&gt;
 *        &lt;user-name&gt;user1&lt;/user-name&gt;
 *        &lt;item&gt;
 *          &lt;id&gt;100&lt;/id&gt;
 *          &lt;name&gt;item1&lt;/name&gt;
 *          &lt;price&gt;1000&lt;/price&gt;
 *        &lt;/item&gt;
 *        &lt;item&gt;
 *          &lt;id&gt;101&lt;/id&gt;
 *          &lt;name&gt;item2&lt;/name&gt;
 *          &lt;price&gt;2000&lt;/price&gt;
 *        &lt;/item&gt;
 *       &lt;/sample-dto&gt;
 * </pre></code>
 * </p>
 * 
 * <p>
 * �y�ϊ��Ώۂ̃I�u�W�F�N�g�z <code><pre>
 *       SampleDto[0].userid[0] = 15
 *       SampleDto[0].name[0] = user1
 *       SampleDto[0].Item[0].id[0] = 100
 *       SampleDto[0].Item[0].name[0] = &quot;item1&quot;
 *       SampleDto[0].Item[0].price[0] = 1000
 *       SampleDto[0].Item[1].id[0] = 101
 *       SampleDto[0].Item[1].name[0] = &quot;item2&quot;
 *       SampleDto[0].Item[1].price[0] = 200
 * </pre></code>
 * </p>
 * 
 * <p>
 * �yCastor�}�b�s���O��`�t�@�C���z <code><pre>
 *       &lt;?xml version=&quot;1.0&quot; encoding=&quot;UTF-8&quot;?&gt;
 *       &lt;!DOCTYPE mapping PUBLIC &quot;-//EXOLAB/Castor Object Mapping DTD Version 1.0//EN&quot;
 *                                       &quot;http://castor.exolab.org/mapping.dtd&quot;&gt;
 *       &lt;mapping&gt;
 *         &lt;class name=&quot;sample.dto.SampleDto&quot;&gt;
 *           &lt;map-to xml=&quot;sample-dto&quot;/&gt;
 *           &lt;field name=&quot;userid&quot; type=&quot;int&quot;&gt;
 *             &lt;bind-xml name=&quot;user-id&quot; node=&quot;element&quot;/&gt;
 *           &lt;/field&gt;
 *            &lt;field name=&quot;username&quot; type=&quot;string&quot;&gt;
 *             &lt;bind-xml name=&quot;user-name&quot; node=&quot;element&quot;/&gt;
 *           &lt;/field&gt;
 *           &lt;field name=&quot;item&quot; type=&quot;sample.dto.Item&quot; collection=&quot;array&quot;&gt;
 *             &lt;bind-xml name=&quot;Item&quot; node=&quot;element&quot;/&gt;
 *          &lt;/field&gt;
 *         &lt;/class&gt;
 *         &lt;class name=&quot;sample.dto.Item&quot;&gt;
 *           &lt;field name=&quot;id&quot; type=&quot;int&quot;&gt;
 *           &lt;/field&gt;
 *           &lt;field name=&quot;name&quot; type=&quot;string&quot;&gt;
 *           &lt;/field&gt;
 *           &lt;field name=&quot;price&quot; type=&quot;int&quot;&gt;
 *           &lt;/field&gt;
 *         &lt;/class&gt;
 *       &lt;/mapping&gt; 
 * </pre></code>
 * </p>
 * 
 * <p>
 * �y�����R�[�h�iXML���I�u�W�F�N�g�j�z
 * 
 * <pre><code>
 * CastorOXMapper oxmapper = new CastorOXMapperImpl();
 * SampleDto bean = new SampleDto(); // ���̃I�u�W�F�N�g��XML�f�[�^���i�[�����
 * Reader reader = new FileReader(&quot;C:/sample/sampleDto.xml&quot;); // �ϊ�����XML�f�[�^
 * 
 * // XML���I�u�W�F�N�g�ϊ�
 * oxmapper.unmarshal(reader, bean);
 * </code></pre>
 * 
 * </p>
 * 
 * <p>
 * �y�����R�[�h�i�I�u�W�F�N�g��XML�j�z
 * 
 * <pre><code>
 * CastorOXMapper oxmapper = new CastorOXMapperImpl();
 * Writer writer = new OutputStreamWriter(System.out);
 * 
 * // �I�u�W�F�N�g��XML
 * oxmapper.marshal(bean, writer);
 * </code></pre>
 * 
 * </p>
 * 
 * @see jp.terasoluna.fw.web.rich.springmvc.bind.XMLServletRequestDataBinder
 * 
 */
public class CastorOXMapperImpl implements OXMapper {

    /**
     * ���O�N���X�B
     */
    private static Log log = LogFactory.getLog(CastorOXMapperImpl.class);

    /**
     * �f�t�H���g�̕����Z�b�g�B
     */
    protected static final String DEFAULT_CHARSET = "UTF-8";

    /**
     * �����Z�b�g�B
     */
    private String charset = DEFAULT_CHARSET;

    /**
     * Castor�}�b�s���O��`�t�@�C���̃L���b�V���ݒ�B�f�t�H���g��true�B
     */
    private boolean cache = true;

    /**
     * Marshaller�I�v�V���� suppressXSIType��false��ݒ肷��ƃ}�b�s���O��`���ݒ莞��
     * ��������h�L�������g�Ɂuxmlns:xsi�v�y�сuxsi:type�v��t�^����
     */
    private boolean suppressXSIType = true;

    /**
     * Marshaller�I�v�V���� preserveWhitespace��true��ݒ肷��ƁA
     * ��������h�L�������g�̃��[�g�m�[�h�Ɂuxml:space="preserve"�v��t�^����B<br>
     * �f�t�H���g�l��true�ł���B �S�d���ɑ΂��ėL���ɂȂ邽�߁A�g�p�ɂ͒��ӂ��K�v�B
     */
    private boolean preserveWhitespaceAtMarshal = true;

    /**
     * Unmarshaller�I�v�V���� whitespacePreserve��true��ݒ肷��ƁA �g�b�v���x���̋󔒂�ێ�����悤�ɂȂ�B<br>
     * �f�t�H���g�l��false�ł���B �S�d���ɑ΂��ėL���ɂȂ邽�߁A�g�p�ɂ͒��ӂ��K�v�B
     */
    private boolean preserveWhitespaceAtUnmarshal = false;

    /**
     * Marshaller�I�v�V���� indenting��true��ݒ肷��ƁA���������xml���C���f���g�����B<br>
     * <b>�����ӁFpreserveWhitespaceAtMarshal �� <u>false</u> �̎��̂ݗL��<b><br>
     */
    private boolean indenting = true;
    
    /**
     * �p�b�P�[�W�̃Z�p���[�^�B
     */
    public static final String NESTED_PACKAGE_SEPARATOR = ".";

    /**
     * �t�H���_�̃Z�p���[�^�B
     */
    public static final String NESTED_FOLDER_SEPARATOR = "/";

    /**
     * Castor�}�b�s���O��`�t�@�C���̃T�t�B�b�N�X�B
     */
    public static final String CASTOR_MAPPINGFILE_SUFFIX = ".xml";

    /**
     * Castor�}�b�s���O��`�t�@�C�����L���b�V������Map�B
     */
    private Map<Class, Mapping> mappingFileCache = new HashMap<Class, Mapping>();

    /**
     * Marshaller�ŋ��L����XMLClassDescriptorResolver�B
     */
    private XMLClassDescriptorResolver sharedResolverForMarshaller = null;

    /**
     * Marshaller�ɐݒ�ς݂�Castor�}�b�s���O��Class�̏W���B
     */
    private Set<Class> hasSetMappingSetForMarshaller = new HashSet<Class>();

    /**
     * DOM�c���[���I�u�W�F�N�g�ɕϊ�����B
     * 
     * @param doc
     *            DOM�c���[�B
     * @param out
     *            XML����ϊ����ꂽ�I�u�W�F�N�g�B
     */
    public void unmarshal(Document doc, Object out) {

        if (doc == null) {
            log.error("DOM tree is null.");
            throw new IllegalArgumentException("DOM tree is null.");
        }

        Unmarshaller unmarshaller = createUnmarshaller(out);

        // XML��Object
        try {
            unmarshaller.unmarshal(doc);
        } catch (MarshalException e) {
            // XML�f�[�^�̃}�b�s���O�Ɏ��s
            log.error("Castor unmarshal failure.", e);
            throw new CastorMarshalException(e);
        } catch (ValidationException e) {
            // XML�f�[�^�̌��؂Ɏ��s
            log.error("Castor validation error.", e);
            throw new CastorValidationException(e);
        }
    }

    /**
     * �X�g���[������XML�f�[�^�����o���A�I�u�W�F�N�g�ɕϊ�����B
     * 
     * @param reader
     *            XML�f�[�^�B�����Z�b�g���w�肳��Ă��Ȃ��ꍇ�A VM�̃f�t�H���g�����Z�b�g���g�p�����B
     * @param out
     *            XML����ϊ����ꂽ�I�u�W�F�N�g�B
     */
    public void unmarshal(Reader reader, Object out) {

        if (reader == null) {
            log.error("Reader is null.");
            throw new IllegalArgumentException("Reader is null.");
        }

        Unmarshaller unmarshaller = createUnmarshaller(out);

        // XML��Object
        try {
            unmarshaller.unmarshal(reader);
        } catch (MarshalException e) {
            // XML�f�[�^�̃}�b�s���O�Ɏ��s
            log.error("Castor unmarshal failure.", e);
            throw new CastorMarshalException(e);
        } catch (ValidationException e) {
            // XML�f�[�^�̌��؂Ɏ��s
            log.error("Castor validation error.", e);
            throw new CastorValidationException(e);
        }
    }

    /**
     * �X�g���[������XML�f�[�^�����o���A�I�u�W�F�N�g�ɕϊ�����B
     * 
     * <p>
     * ����argCharset��null�܂��͋󕶎��̏ꍇ�A InputStreamReader�̕����Z�b�g�Ƃ��đ���{@link #charset}�̒l���g�p�����B<br>
     * �f�t�H���g�ݒ�ł́A����{@link #charset}�̒l��"UTF-8"�ł���B
     * </p>
     * 
     * @param is
     *            XML�f�[�^�B
     * @param argCharset
     *            �����Z�b�g�B
     * @param out
     *            XML����ϊ����ꂽ�I�u�W�F�N�g�B
     */
    public void unmarshal(InputStream is, String argCharset, Object out) {

        if (is == null) {
            log.error("InputStream is null.");
            throw new IllegalArgumentException("InputStream is null.");
        }

        String charset = argCharset;

        // �����Z�b�g���w�肳��Ă��Ȃ��ꍇ�́AUTF-8���g�p����
        if (charset == null || charset.length() < 1) {
            if (log.isDebugEnabled()) {
                log.debug("Character encoding is not found. " + DEFAULT_CHARSET
                        + " is used.");
            }
            charset = getCharset();
        }

        InputStreamReader isr = null;

        // XML��Object
        try {
            isr = new InputStreamReader(is, charset);
            unmarshal(isr, out);
        } catch (UnsupportedEncodingException e) {
            // �T�|�[�g���Ă��Ȃ��G���R�[�f�B���O
            log.error("Character encoding error.", e);
            throw new CastorUnsupportedEncodingException(e);
        } finally {
            try {
                if (isr != null) {
                    isr.close();
                }
            } catch (IOException e) {
                log.error("Failed to close input stream.", e);
            }
        }
    }

    /**
     * �A���}�[�V�����[�𐶐�����B Castor�}�b�s���O��`�̐ݒ���s���B
     * 
     * @param out
     *            �o�͑Ώۂ̃I�u�W�F�N�g
     * @return �A���}�[�V�����[
     */
    protected Unmarshaller createUnmarshaller(Object out) {

        if (out == null) {
            log.error("Unmarshal object is null.");
            throw new IllegalArgumentException("Unmarshal object is null.");
        }

        Unmarshaller unmarshaller = new Unmarshaller(out);

        Class mappingClass = out.getClass();

        // Castor�}�b�s���O��`
        Mapping mapping = getCastorMapping(mappingClass);

        if (mapping != null) {
            try {
                unmarshaller.setMapping(mapping);
            } catch (MappingException e) {
                // Castor�}�b�s���O��`�t�@�C���ɖ�肪����
                log.error("Castor mapping file is invalid. "
                        + "- [root-classpath]/"
                        + getMappingFilePath(mappingClass), e);
                throw new CastorMappingException(e);
            }

            // Castor�}�b�s���O��`�t�@�C�����L���b�V������
            if (cache && !mappingFileCache.containsKey(mappingClass)) {
                mappingFileCache.put(mappingClass, mapping);
            }
        }

        // Castor�Ńo���f�[�V�����i�`���`�F�b�N�j�͍s��Ȃ�
        unmarshaller.setValidation(false);

        // Unmarshaller�I�v�V���� whitespacePreserve
        unmarshaller.setWhitespacePreserve(preserveWhitespaceAtUnmarshal);

        return unmarshaller;
    }

    /**
     * �I�u�W�F�N�g��XML�ɕϊ����A�X�g���[���ɏ������ށB
     * 
     * @param in
     *            XML�ɕϊ�����I�u�W�F�N�g
     * @param writer
     *            �ϊ�����XML���������ރ��C�^�[
     */
    public void marshal(Object in, Writer writer) {
        Marshaller marshaller = createMarshaller(in, writer);

        // Object��XML
        try {
            marshaller.marshal(in);
        } catch (MarshalException e) {
            // XML�f�[�^�̃}�b�s���O�Ɏ��s
            log.error("Castor marshal failure.", e);
            throw new CastorMarshalException(e);
        } catch (ValidationException e) {
            // XML�f�[�^�̌��؂Ɏ��s
            log.error("Castor validation error.", e);
            throw new CastorValidationException(e);
        }
    }

    /**
     * �}�[�V�����[�𐶐�����B Castor�}�b�s���O��`��ݒ肷��B
     * 
     * @param in
     *            XML�ϊ��Ώۂ̃I�u�W�F�N�g
     * @param writer
     *            �o�͗p���C�^�[
     * @return �}�[�V�����[
     */
    @SuppressWarnings("deprecation")
    protected Marshaller createMarshaller(Object in, Writer writer) {

        if (in == null) {
            log.error("Marshall object is null.");
            throw new IllegalArgumentException("Marshall object is null.");
        }

        if (writer == null) {
            log.error("Writer is null.");
            throw new IllegalArgumentException("Writer is null.");
        }

        Marshaller marshaller = null;

        // �}�[�V�����[�̐���
        try {
            XMLSerializerEx serializer = new XMLSerializerEx(writer,
                    new OutputFormat(Method.XML, charset, indenting));
            serializer.setPreserveWhitespace(preserveWhitespaceAtMarshal);

            marshaller = new Marshaller((ContentHandler) serializer);
            if (sharedResolverForMarshaller == null) {
                ClassDescriptorResolver resolver = marshaller.getResolver();
                if (resolver instanceof XMLClassDescriptorResolver) {
                    sharedResolverForMarshaller = (XMLClassDescriptorResolver) resolver;
                }
            } else {
                marshaller.setResolver(sharedResolverForMarshaller);
            }
        } catch (IOException e) {
            log.error("Marshaling io error.", e);
            throw new CastorCreateMarshallerIOException(e);
        }

        Class mappingClass = in.getClass();

        if (!hasSetMappingSetForMarshaller.contains(mappingClass)) {
            // Castor�}�b�s���O��`
            Mapping mapping = getCastorMapping(mappingClass);

            if (mapping != null) {
                try {
                    marshaller.setMapping(mapping);
                    hasSetMappingSetForMarshaller.add(mappingClass);
                } catch (MappingException e) {
                    // Castor�}�b�s���O��`�t�@�C���ɖ�肪����
                    log.error("Castor mapping file is invalid. "
                            + "- [root-classpath]/"
                            + getMappingFilePath(mappingClass), e);
                    throw new CastorMappingException(e);
                }

                // Castor�}�b�s���O��`�t�@�C�����L���b�V������
                if (cache && !mappingFileCache.containsKey(mappingClass)) {
                    mappingFileCache.put(mappingClass, mapping);
                }
            }
        }

        marshaller.setValidation(false);

        // Marshaller�I�v�V���� suppressXSIType
        marshaller.setSuppressXSIType(suppressXSIType);

        return marshaller;
    }

    /**
     * Castor�}�b�s���O��`�t�@�C�����擾����B {@link #cache}������true�̏ꍇ�A�ǂݍ���Castor�}�b�s���O��`�t�@�C����
     * �L���b�V������Bfalse�̏ꍇ�A�L���b�V���͍s��Ȃ��B
     * 
     * @param mappingClass
     *            �}�b�s���O�Ώۂ̃N���X
     * @return Castor�}�b�s���O��`�t�@�C��
     */
    protected Mapping getCastorMapping(Class mappingClass) {
        Mapping mapping = null;

        // �L���b�V��������ΕԂ�
        if (cache) {
            mapping = mappingFileCache.get(mappingClass);
            if (mapping != null) {
                return mapping;
            }
        }

        // Castor�}�b�s���O��`�t�@�C�����擾����
        mapping = new Mapping();
        URL mappingURL = getUrl(mappingClass);

        if (mappingURL == null) {
            // Castor�}�b�s���O��`�t�@�C�����Ȃ��ꍇ��null��ԋp���ACastor�̃f�t�H���g���[����K�p����
            return null;
        }

        // �}�b�s���O�C���X�^���X��Castor�}�b�s���O��`�t�@�C�������[�h����
        mapping.loadMapping(new InputSource(mappingURL.toExternalForm()));

        return mapping;
    }

    /**
     * URL���擾����B
     * 
     * @param mappingClass
     *            �}�b�s���O�Ώۂ̃N���X
     * @return ���\�[�X��URL�C���X�^���X
     */
    protected URL getUrl(Class mappingClass) {
        return Thread.currentThread().getContextClassLoader().getResource(
                getMappingFilePath(mappingClass));
    }

    /**
     * Castor�}�b�s���O�t�@�C���̃p�X���擾����B
     * 
     * @param mappingClass
     *            �}�b�s���O����N���X�B
     * @return Castor�}�b�s���O�t�@�C���̃p�X
     */
    protected String getMappingFilePath(Class mappingClass) {
        StringBuilder buf = new StringBuilder();
        buf.append(mappingClass.getName().replace(NESTED_PACKAGE_SEPARATOR,
                NESTED_FOLDER_SEPARATOR));
        buf.append(CASTOR_MAPPINGFILE_SUFFIX);
        return buf.toString();
    }

    /**
     * cache���擾����B
     * 
     * @return cache����
     */
    public boolean isCache() {
        return cache;
    }

    /**
     * cache��ݒ肷��B
     * 
     * @param cache
     *            cache�����ɐݒ肷��l
     */
    public void setCache(boolean cache) {
        this.cache = cache;
    }

    /**
     * �����Z�b�g���擾����B
     * 
     * @return �����Z�b�g�B
     */
    public String getCharset() {
        return charset;
    }

    /**
     * �����Z�b�g��ݒ肷��B
     * 
     * @param charset
     *            �����Z�b�g�B
     */
    public void setCharset(String charset) {
        this.charset = charset;
    }

    /**
     * Marshaller�I�v�V���� suppressXSIType<br>
     * <br>
     * <b>Castor�}�b�s���O���ݒ艻���ɃX�L�[�}�o���f�[�^��K�p����ꍇ��true��ݒ肷��B</b><br>
     * <br>
     * false��ݒ肷��ƃl�X�g����Bean��Marshall���鎞�ɁA
     * ��������XML�h�L�������g�Ɂuxmlns:xsi�v�y�сuxsi:type�v��t�^����B<br>
     * ���̃l�[���X�y�[�X���t�^�����ƁACastor�}�b�s���O���ݒ艻���� �X�L�[�}�o���f�[�^���g�p�����ꍇ�A������Unmarshall�ł��Ȃ��B<br>
     * ���̂��߁A�X�L�[�}�o���f�[�^���g�p����ꍇ�ɂ́A���̃I�v�V������true�� �ݒ肷��B<br>
     * <fieldset style="border:1pt solid black;padding:10px;width:100%;">
     * <legend>false</legend>
     * 
     * <pre>
     *       &lt;resultReserveParam&gt;
     *         &lt;reserveDetailList 
     *         xmlns:xsi=&quot;http://www.w3.org/2001/XMLSchema-instance&quot; 
     *         xsi:type=&quot;java:sample.ReserveDetail&quot;&gt;
     *         &lt;/reserveDetailList&gt;
     *       &lt;/resultReserveParam&gt;
     * </pre>
     * 
     * </fieldset><br>
     * <fieldset style="border:1pt solid black;padding:10px;width:100%;">
     * <legend>true�i�f�t�H���g�j</legend>
     * 
     * <pre>
     *       &lt;resultReserveParam&gt;
     *         &lt;reserveDetailList&gt;
     *         &lt;/reserveDetailList&gt;
     *       &lt;/resultReserveParam&gt;
     * </pre>
     * 
     * </fieldset><br>
     * 
     * @param suppressXSIType
     *            �ݒ肷�� suppressXSIType
     */
    public void setSuppressXSIType(boolean suppressXSIType) {
        this.suppressXSIType = suppressXSIType;
    }

    /**
     * Unmarshaller�I�v�V���� whitespacePreserve��ݒ肷��B
     * <p>
     * true��ݒ肷��ƁA�S�Ă̗v�f�ɂ��ċ󔒂�ێ� �����܂܃A���}�[�V�������s����B<br>
     * false�̏ꍇ�ł��A�uxml:space="preserve"�v���w�肳�ꂽ �v�f�ɂ��ẮA�󔒂�ێ������܂܃A���}�[�V���������B
     * 
     * �S�d���ɑ΂��ėL���ɂȂ邽�߁A�g�p�ɂ͒��ӂ��K�v�ł���B �f�t�H���g�l��<code>false</code>�B
     * 
     * @param preserveWhitespaceAtUnmarshal
     *            �ݒ肷�� whitespacePreserve
     */
    public void setPreserveWhitespaceAtUnmarshal(
            boolean preserveWhitespaceAtUnmarshal) {
        this.preserveWhitespaceAtUnmarshal = preserveWhitespaceAtUnmarshal;
    }

    /**
     * Marshaller�I�v�V���� preserveWhitespace��ݒ肷��B
     * <p>
     * true��ݒ肷��Ɛ�������h�L�������g�̃��[�g�m�[�h�� �uxml:space="preserve"�v��t�^����B<br>
     * �S�d���ɑ΂��ėL���ɂȂ邽�߁A�g�p�ɂ͒��ӂ��K�v�ł���B �f�t�H���g�l��<code>true</code>�B
     * 
     * <fieldset style="border:1pt solid black;padding:10px;width:100%;">
     * <legend>false</legend>
     * 
     * <pre>
     *       &lt;resultReserveParam&gt;
     *         &lt;reserveDetailList&gt;
     *         &lt;/reserveDetailList&gt;
     *       &lt;/resultReserveParam&gt;
     * </pre>
     * 
     * </fieldset><br>
     * <fieldset style="border:1pt solid black;padding:10px;width:100%;">
     * <legend>true�i�f�t�H���g�j</legend>
     * 
     * <pre>
     *       &lt;resultReserveParam xml:space=&quot;preserve&quot;&gt;
     *         &lt;reserveDetailList&gt;
     *         &lt;/reserveDetailList&gt;
     *       &lt;/resultReserveParam&gt;
     * </pre>
     * 
     * </fieldset><br> *
     * 
     * @param preserveWhitespaceAtMarshal
     *            �ݒ肷��preserveWhitespace
     */
    public void setPreserveWhitespaceAtMarshal(
            boolean preserveWhitespaceAtMarshal) {
        this.preserveWhitespaceAtMarshal = preserveWhitespaceAtMarshal;
    }

    /**
     * Marshaller�I�v�V���� indenting��ݒ肷��<br>
     * <br>
     * true��ݒ肷��ƁA���������xml���C���f���g�����B<br>
     * <br>
     * <b>�����ӁFpreserveWhitespaceAtMarshal �� <u>false</u> �̎��̂ݗL��<b><br>
     * 
     * @param indenting �ݒ肷�� indenting
     */
    public void setIndenting(boolean indenting) {
        this.indenting = indenting;
    }

}
