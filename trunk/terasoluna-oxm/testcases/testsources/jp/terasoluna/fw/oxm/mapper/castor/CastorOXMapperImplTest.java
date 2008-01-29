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
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import jp.terasoluna.utlib.LogUTUtil;
import jp.terasoluna.utlib.UTUtil;
import jp.terasoluna.fw.oxm.mapper.OXMapper;
import jp.terasoluna.fw.oxm.mapper.castor.CastorOXMapperImpl;
import jp.terasoluna.fw.oxm.mapper.castor.exception.CastorMappingException;
import jp.terasoluna.fw.oxm.mapper.castor.exception.CastorMarshalException;
import jp.terasoluna.fw.oxm.mapper.castor.exception.CastorUnsupportedEncodingException;
import jp.terasoluna.fw.oxm.mapper.castor.exception.CastorValidationException;
import jp.terasoluna.fw.oxm.serialize.XMLSerializerEx;
import junit.framework.TestCase;

import org.apache.xerces.dom.DocumentImpl;
import org.exolab.castor.mapping.Mapping;
import org.exolab.castor.mapping.MappingException;
import org.exolab.castor.mapping.loader.AbstractMappingLoader2;
import org.exolab.castor.mapping.xml.ClassMapping;
import org.exolab.castor.xml.ClassDescriptorResolver;
import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;
import org.exolab.castor.xml.ValidationException;
import org.exolab.castor.xml.util.XMLClassDescriptorAdapter;
import org.w3c.dom.Document;

/**
 * {@link jp.terasoluna.fw.oxm.mapper.castor.CastorOXMapperImpl}
 * �N���X�̃u���b�N�{�b�N�X�e�X�g�B
 * 
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4>
 * Castor�𗘗p�����I�u�W�F�N�g-XML�ϊ��N���X�B<br>
 * �O������F<br>
 * getCastorMapping�̈���mappingClass�AgetUrl���\�b�h�̈���path��null�ɂȂ邱�Ƃ͂Ȃ��B
 * </p>
 * 
 * @see jp.terasoluna.fw.oxm.mapper.castor.CastorOXMapperImpl
 */
public class CastorOXMapperImplTest extends TestCase {

    /**
     * ���̃N���X�̃p�b�P�[�W�p�X <br>
     * ���̃N���X��jp.co.sample.SampleTest�̏ꍇ�A�p�b�P�[�W�p�X�́ujp/co/sample/�v�B <br>
     */
    private String packagePath = this.getClass().getPackage().getName()
            .replace(".", "/")
            + "/";

    /**
     * ��`�t�@�C����suffix
     */
    private String mappingSuffix = ".xml";

    /**
     * ���̃e�X�g�P�[�X�����s����ׂ� GUI �A�v���P�[�V�������N������B
     * 
     * @param args
     *            java �R�}���h�ɐݒ肳�ꂽ�p�����[�^
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(CastorOXMapperImplTest.class);
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
    public CastorOXMapperImplTest(String name) {
        super(name);
    }

    /**
     * testUnmarshalDocumentObject01() <br>
     * <br>
     * 
     * (�ُ�n) <br>
     * �ϓ_�FA,C,G <br>
     * <br>
     * ���͒l�F(����) doc:null<br>
     * (����) out:not null<br>
     * 
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalArgumentException{<br>
     * message = "DOM tree is null!"<br> }<br>
     * (��ԕω�) ���O:���O���x��:error<br>
     * DOM tree is null.<br>
     * 
     * <br>
     * ����doc��null�̏ꍇ�A��O���X���[����邱�Ƃ��m�F����B <br>
     * 
     * @throws Exception
     *             ���̃��\�b�h�Ŕ���������O
     */
    public void testUnmarshalDocumentObject01() throws Exception {
        // �O����
        OXMapper oxmapper = new CastorOXMapperImpl();

        Document doc = null;
        Object out = new Object();

        try {
            // �e�X�g���{
            oxmapper.unmarshal(doc, out);
            fail();
        } catch (IllegalArgumentException e) {
            // ����
            String message = "DOM tree is null.";
            assertEquals(message, e.getMessage());

            // ���O�m�F
            assertTrue(LogUTUtil.checkError(message));
        }
    }

    /**
     * testUnmarshalDocumentObject02() <br>
     * <br>
     * 
     * (�ُ�n) <br>
     * �ϓ_�FA,C,G <br>
     * <br>
     * ���͒l�F(����) doc:not null<br>
     * (����) out:null<br>
     * (���) createUnmarshaller():�Ăяo���ꂽ���Ƃ��m�F����B<br>
     * �������n���ꂽ���Ƃ��m�F����B<br>
     * (���) unmarshaller.unmarshal():�Ăяo���ꂽ���Ƃ��m�F����B<br>
     * �������n���ꂽ���Ƃ��m�F����B<br>
     * <br>
     * MarshalException���X���[����B<br>
     * 
     * <br>
     * ���Ғl�F(��ԕω�) ��O:CastorMarshalException{<br>
     * cause = MarshalException<br> }<br>
     * (��ԕω�) ���O:���O���x��:error<br>
     * Castor unmarshal failure.<br>
     * 
     * <br>
     * �A���}�[�V��������MarshalException���X���[���ꂽ�ꍇ�A���b�v����CastorMarshalException���X���[����A���O���o�͂���邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception
     *             ���̃��\�b�h�Ŕ���������O
     */
    public void testUnmarshalDocumentObject02() throws Exception {
        // �O����
        CastorOXMapperImpl_CastorOXMapperImplStub01 oxmapper = new CastorOXMapperImpl_CastorOXMapperImplStub01();
        CastorOXMapperImpl_UnmarshallerStub01 unmarshaller = new CastorOXMapperImpl_UnmarshallerStub01();
        oxmapper.unmarshaller = unmarshaller;

        Document doc = new DocumentImpl();
        Object out = null;

        try {
            // �e�X�g���{
            oxmapper.unmarshal(doc, out);
            fail();
        } catch (CastorMarshalException e) {
            // ����
            String message = "Castor unmarshal failure.";
            assertSame(MarshalException.class, e.getCause().getClass());

            // ���O�m�F
            assertTrue(LogUTUtil.checkError(message, e.getCause()));

            // createUnmarshaller���\�b�h�Ɉ������n���ꂽ���Ƃ̊m�F
            assertNull(oxmapper.out);

            // unmarshaller.unmarshal���\�b�h�Ɉ������n���ꂽ���Ƃ̊m�F
            assertSame(doc, unmarshaller.node);
        }
    }

    /**
     * testUnmarshalDocumentObject03() <br>
     * <br>
     * 
     * (�ُ�n) <br>
     * �ϓ_�FA,G <br>
     * <br>
     * ���͒l�F(����) doc:not null<br>
     * (����) out:not null<br>
     * (���) createUnmarshaller():�Ăяo���ꂽ���Ƃ��m�F����B<br>
     * �������n���ꂽ���Ƃ��m�F����B<br>
     * (���) unmarshaller.unmarshal():�Ăяo���ꂽ���Ƃ��m�F����B<br>
     * �������n���ꂽ���Ƃ��m�F����B<br>
     * <br>
     * ValidationException���X���[����B<br>
     * 
     * <br>
     * ���Ғl�F(��ԕω�) ��O:CastorValidationException{<br>
     * cause = ValidationException<br> }<br>
     * (��ԕω�) ���O:���O���x��:error<br>
     * Castor validation error.<br>
     * 
     * <br>
     * �A���}�[�V��������ValidationException���X���[���ꂽ�ꍇ�A���b�v����CastorValudationException���X���[����A���O���o�͂���邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception
     *             ���̃��\�b�h�Ŕ���������O
     */
    public void testUnmarshalDocumentObject03() throws Exception {
        // �O����
        CastorOXMapperImpl_CastorOXMapperImplStub01 oxmapper = new CastorOXMapperImpl_CastorOXMapperImplStub01();
        CastorOXMapperImpl_UnmarshallerStub02 unmarshaller = new CastorOXMapperImpl_UnmarshallerStub02();
        oxmapper.unmarshaller = unmarshaller;

        Document doc = new DocumentImpl();
        Object out = new Object();

        try {
            // �e�X�g���{
            oxmapper.unmarshal(doc, out);
            fail();
        } catch (CastorValidationException e) {
            // ����
            String message = "Castor validation error.";
            assertSame(ValidationException.class, e.getCause().getClass());

            // ���O�m�F
            assertTrue(LogUTUtil.checkError(message, e.getCause()));

            // createUnmarshaller���\�b�h�Ɉ������n���ꂽ���Ƃ̊m�F
            assertSame(out, oxmapper.out);

            // unmarshaller.unmarshal���\�b�h�Ɉ������n���ꂽ���Ƃ̊m�F
            assertSame(doc, unmarshaller.node);
        }
    }

    /**
     * testUnmarshalDocumentObject04() <br>
     * <br>
     * 
     * (����n) <br>
     * �ϓ_�FA <br>
     * <br>
     * ���͒l�F(����) doc:not null<br>
     * (����) out:not null<br>
     * (���) createUnmarshaller():�Ăяo���ꂽ���Ƃ��m�F����B<br>
     * �������n���ꂽ���Ƃ��m�F����B<br>
     * (���) unmarshaller.unmarshal():�Ăяo���ꂽ���Ƃ��m�F����B<br>
     * �������n���ꂽ���Ƃ��m�F����B<br>
     * 
     * <br>
     * ���Ғl�F <br>
     * ����n�̃p�^�[���B<br>
     * unmarshal���\�b�h���Ăяo���ꂽ���Ƃ̊m�F���s���B <br>
     * 
     * @throws Exception
     *             ���̃��\�b�h�Ŕ���������O
     */
    public void testUnmarshalDocumentObject04() throws Exception {
        // �O����
        CastorOXMapperImpl_CastorOXMapperImplStub01 oxmapper = new CastorOXMapperImpl_CastorOXMapperImplStub01();
        CastorOXMapperImpl_UnmarshallerStub03 unmarshaller = new CastorOXMapperImpl_UnmarshallerStub03();
        oxmapper.unmarshaller = unmarshaller;

        Document doc = new DocumentImpl();
        Object out = new Object();

        // �e�X�g���{
        oxmapper.unmarshal(doc, out);

        // createUnmarshaller���\�b�h�Ɉ������n���ꂽ���Ƃ̊m�F
        assertSame(out, oxmapper.out);

        // unmarshaller.unmarshal���\�b�h�Ɉ������n���ꂽ���Ƃ̊m�F
        assertSame(doc, unmarshaller.node);
    }

    /**
     * testUnmarshalReaderObject01() <br>
     * <br>
     * 
     * (�ُ�n) <br>
     * �ϓ_�FA,C,G <br>
     * <br>
     * ���͒l�F(����) Reader:null<br>
     * (����) out:not null<br>
     * 
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalArgumentException{<br>
     * message = "Reader is null!!"<br> }<br>
     * (��ԕω�) ���O:���O���x��:error<br>
     * Reader is null.<br>
     * 
     * <br>
     * ����doc��null�̏ꍇ�A��O���X���[����邱�Ƃ��m�F����B <br>
     * 
     * @throws Exception
     *             ���̃��\�b�h�Ŕ���������O
     */
    public void testUnmarshalReaderObject01() throws Exception {
        // �O����
        OXMapper oxmapper = new CastorOXMapperImpl();

        Reader reader = null;
        Object out = new Object();

        try {
            // �e�X�g���{
            oxmapper.unmarshal(reader, out);
            fail();
        } catch (IllegalArgumentException e) {
            // ����
            String message = "Reader is null.";
            assertEquals(message, e.getMessage());

            // ���O�m�F
            assertTrue(LogUTUtil.checkError(message));
        }
    }

    /**
     * testUnmarshalReaderObject02() <br>
     * <br>
     * 
     * (�ُ�n) <br>
     * �ϓ_�FA,C,G <br>
     * <br>
     * ���͒l�F(����) Reader:not null<br>
     * (����) out:null<br>
     * (���) createUnmarshaller():�Ăяo���ꂽ���Ƃ��m�F����B<br>
     * �������n���ꂽ���Ƃ��m�F����B<br>
     * (���) unmarshaller.unmarshal():�Ăяo���ꂽ���Ƃ��m�F����B<br>
     * �������n���ꂽ���Ƃ��m�F����B<br>
     * <br>
     * MarshalException���X���[����<br>
     * 
     * <br>
     * ���Ғl�F(��ԕω�) ��O:CastorMarshalException{<br>
     * cause = MarshalException<br> }<br>
     * (��ԕω�) ���O:���O���x��:error<br>
     * Castor unmarshal failure.<br>
     * 
     * <br>
     * �A���}�[�V��������MarshalException���X���[���ꂽ�ꍇ�A���b�v����CastorMarshalException���X���[����A���O���o�͂���邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception
     *             ���̃��\�b�h�Ŕ���������O
     */
    public void testUnmarshalReaderObject02() throws Exception {
        // �O����
        CastorOXMapperImpl_CastorOXMapperImplStub01 oxmapper = new CastorOXMapperImpl_CastorOXMapperImplStub01();
        CastorOXMapperImpl_UnmarshallerStub01 unmarshaller = new CastorOXMapperImpl_UnmarshallerStub01();
        oxmapper.unmarshaller = unmarshaller;

        Reader reader = new StringReader("");
        Object out = null;

        try {
            // �e�X�g���{
            oxmapper.unmarshal(reader, out);
            fail();
        } catch (CastorMarshalException e) {
            // ����
            String message = "Castor unmarshal failure.";
            assertSame(MarshalException.class, e.getCause().getClass());

            // ���O�m�F
            assertTrue(LogUTUtil.checkError(message, e.getCause()));

            // createUnmarshaller���\�b�h�Ɉ������n���ꂽ���Ƃ̊m�F
            assertNull(oxmapper.out);

            // unmarshaller.unmarshal���\�b�h�Ɉ������n���ꂽ���Ƃ̊m�F
            assertSame(reader, unmarshaller.reader);
        }
    }

    /**
     * testUnmarshalReaderObject03() <br>
     * <br>
     * 
     * (�ُ�n) <br>
     * �ϓ_�FA,G <br>
     * <br>
     * ���͒l�F(����) Reader:not null<br>
     * (����) out:not null<br>
     * (���) createUnmarshaller():�Ăяo���ꂽ���Ƃ��m�F����B<br>
     * �������n���ꂽ���Ƃ��m�F����B<br>
     * (���) unmarshaller.unmarshal():�Ăяo���ꂽ���Ƃ��m�F����B<br>
     * �������n���ꂽ���Ƃ��m�F����B<br>
     * <br>
     * ValidationException���X���[����<br>
     * 
     * <br>
     * ���Ғl�F(��ԕω�) ��O:CastorValidationException{<br>
     * cause = ValidationException<br> }<br>
     * (��ԕω�) ���O:���O���x��:error<br>
     * Castor validation error.<br>
     * 
     * <br>
     * �A���}�[�V��������ValidationException���X���[���ꂽ�ꍇ�A���b�v����CastorValudationException���X���[����A���O���o�͂���邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception
     *             ���̃��\�b�h�Ŕ���������O
     */
    public void testUnmarshalReaderObject03() throws Exception {
        // �O����
        CastorOXMapperImpl_CastorOXMapperImplStub01 oxmapper = new CastorOXMapperImpl_CastorOXMapperImplStub01();
        CastorOXMapperImpl_UnmarshallerStub02 unmarshaller = new CastorOXMapperImpl_UnmarshallerStub02();
        oxmapper.unmarshaller = unmarshaller;

        Reader reader = new StringReader("");
        Object out = new Object();

        try {
            // �e�X�g���{
            oxmapper.unmarshal(reader, out);
            fail();
        } catch (CastorValidationException e) {
            // ����
            String message = "Castor validation error.";
            assertSame(ValidationException.class, e.getCause().getClass());

            // ���O�m�F
            assertTrue(LogUTUtil.checkError(message, e.getCause()));

            // createUnmarshaller���\�b�h�Ɉ������n���ꂽ���Ƃ̊m�F
            assertSame(out, oxmapper.out);

            // unmarshaller.unmarshal���\�b�h�Ɉ������n���ꂽ���Ƃ̊m�F
            assertSame(reader, unmarshaller.reader);
        }
    }

    /**
     * testUnmarshalReaderObject04() <br>
     * <br>
     * 
     * (����n) <br>
     * �ϓ_�FA <br>
     * <br>
     * ���͒l�F(����) Reader:not null<br>
     * (����) out:not null<br>
     * (���) createUnmarshaller():�Ăяo���ꂽ���Ƃ��m�F����B<br>
     * �������n���ꂽ���Ƃ��m�F����B<br>
     * (���) unmarshaller.unmarshal():�Ăяo���ꂽ���Ƃ��m�F����B<br>
     * �������n���ꂽ���Ƃ��m�F����B<br>
     * 
     * <br>
     * ���Ғl�F <br>
     * ����n�̃p�^�[���B<br>
     * unmarshal���\�b�h���Ăяo���ꂽ���Ƃ̊m�F���s���B <br>
     * 
     * @throws Exception
     *             ���̃��\�b�h�Ŕ���������O
     */
    public void testUnmarshalReaderObject04() throws Exception {
        // �O����
        CastorOXMapperImpl_CastorOXMapperImplStub01 oxmapper = new CastorOXMapperImpl_CastorOXMapperImplStub01();
        CastorOXMapperImpl_UnmarshallerStub03 unmarshaller = new CastorOXMapperImpl_UnmarshallerStub03();
        oxmapper.unmarshaller = unmarshaller;

        Reader reader = new StringReader("");
        Object out = new Object();

        // �e�X�g���{
        oxmapper.unmarshal(reader, out);

        // createUnmarshaller���\�b�h�Ɉ������n���ꂽ���Ƃ̊m�F
        assertSame(out, oxmapper.out);

        // unmarshaller.unmarshal���\�b�h�Ɉ������n���ꂽ���Ƃ̊m�F
        assertSame(reader, unmarshaller.reader);

    }

    /**
     * testUnmarshalInputStreamStringObject01() <br>
     * <br>
     * 
     * (�ُ�n) <br>
     * �ϓ_�FA,C,G <br>
     * <br>
     * ���͒l�F(����) is:null<br>
     * (����) argCharset:not null<br>
     * (����) out:not null<br>
     * 
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalArgumentException{<br>
     * message="InputStream is null."<br> }<br>
     * (��ԕω�) ���O:���O���x��:error<br>
     * InputStream is null.<br>
     * 
     * <br>
     * ����is��null�̏ꍇ�A��O���X���[����A���O���o�͂���邱�Ƃ��m�F����B <br>
     * 
     * @throws Exception
     *             ���̃��\�b�h�Ŕ���������O
     */
    public void testUnmarshalInputStreamStringObject01() throws Exception {
        // �O����
        CastorOXMapperImpl oxmapper = new CastorOXMapperImpl();

        // ����
        InputStream in = null;
        String argCharset = "";
        Object out = new Object();

        // �e�X�g���{
        try {
            oxmapper.unmarshal(in, argCharset, out);
            fail();
        } catch (IllegalArgumentException e) {
            // ����
            String message = "InputStream is null.";
            assertEquals(message, e.getMessage());

            // ���O�m�F
            assertTrue(LogUTUtil.checkError(message));
        }

    }

    /**
     * testUnmarshalInputStreamStringObject02() <br>
     * <br>
     * 
     * (����n) <br>
     * �ϓ_�FA,C <br>
     * <br>
     * ���͒l�F(����) is:not null<br>
     * (����) argCharset:""<br>
     * (����) out:not null<br>
     * (����) unmarshal():�Ăяo���ꂽ���Ƃ��m�F����B<br>
     * �������n���ꂽ���Ƃ��m�F����B<br>
     * (����) close():�Ăяo���ꂽ���Ƃ��m�F����B<br>
     * �������n���ꂽ���Ƃ��m�F����B<br>
     * (���) this.charset:"UTF-8"<br>
     * 
     * <br>
     * ���Ғl�F <br>
     * ����argCharset���󕶎��̏ꍇ�A�����R�[�h�Ƃ���"UTF-8"���ݒ肳��Aunmarshal���\�b�h�Aisr.close���\�b�h���Ăяo����邱�Ƃ��m�F����B<br>
     * ���O���o�͂���邱�Ƃ��m�F����B <br>
     * 
     * @throws Exception
     *             ���̃��\�b�h�Ŕ���������O
     */
    public void testUnmarshalInputStreamStringObject02() throws Exception {
        // �O����
        CastorOXMapperImpl_CastorOXMapperImplStub02 oxmapper = new CastorOXMapperImpl_CastorOXMapperImplStub02();
        UTUtil.setPrivateField(oxmapper, "charset", "UTF-8");

        // ����
        CastorOXMapperImpl_InputStreamStub01 in = new CastorOXMapperImpl_InputStreamStub01();
        String argCharset = "";
        Object out = new Object();

        // �e�X�g���{
        oxmapper.unmarshal(in, argCharset, out);

        // ����
        // unmarshal���\�b�h�Ɉ������n���ꂽ���Ƃ̊m�F
        assertSame(in, UTUtil.getPrivateField(oxmapper.reader, "lock"));
        assertSame(out, oxmapper.out);
        assertEquals("UTF8", oxmapper._charset);

        // close���\�b�h���Ăяo���ꂽ���Ƃ̊m�F
        assertTrue(in.isClose);
    }

    /**
     * testUnmarshalInputStreamStringObject03() <br>
     * <br>
     * 
     * (����n) <br>
     * �ϓ_�FA,C <br>
     * <br>
     * ���͒l�F(����) is:not null<br>
     * (����) argCharset:null<br>
     * (����) out:null<br>
     * (����) unmarshal():�Ăяo���ꂽ���Ƃ��m�F����B<br>
     * �������n���ꂽ���Ƃ��m�F����B<br>
     * (����) close():IOException���X���[����B<br>
     * (���) this.charset:"UTF-8"<br>
     * 
     * <br>
     * ���Ғl�F <br>
     * ����argCharset��null�̏ꍇ�A�����R�[�h�Ƃ���"UTF-8"���ݒ肳��Aunmarshal���\�b�h���Ăяo����邱�Ƃ��m�F����B<br>
     * �X�g���[�����N���[�Y����ۂ�IOException���X���[���ꂽ�ꍇ�A���O���o�͂���邱�Ƃ��m�F����B <br>
     * 
     * @throws Exception
     *             ���̃��\�b�h�Ŕ���������O
     */
    public void testUnmarshalInputStreamStringObject03() throws Exception {
        // �O����
        CastorOXMapperImpl_CastorOXMapperImplStub02 oxmapper = new CastorOXMapperImpl_CastorOXMapperImplStub02();
        UTUtil.setPrivateField(oxmapper, "charset", "UTF-8");

        // ����
        CastorOXMapperImpl_InputStreamStub02 in = new CastorOXMapperImpl_InputStreamStub02();
        String argCharset = null;
        Object out = null;

        // �e�X�g���{
        oxmapper.unmarshal(in, argCharset, out);

        // ����
        // unmarshal���\�b�h�Ɉ������n���ꂽ���Ƃ̊m�F
        assertSame(in, UTUtil.getPrivateField(oxmapper.reader, "lock"));
        assertNull(out);
        assertEquals("UTF8", oxmapper._charset);

        // ���O�m�F
        String message = "Failed to close input stream.";
        assertTrue(LogUTUtil.checkError(message, new IOException()));

    }

    /**
     * testUnmarshalInputStreamStringObject04() <br>
     * <br>
     * 
     * (�ُ�n) <br>
     * �ϓ_�FA,G <br>
     * <br>
     * ���͒l�F(����) is:not null<br>
     * (����) argCharset:"abc"<br>
     * (����) out:not null<br>
     * (����) close():�Ăяo����Ȃ��������Ƃ��m�F����B<br>
     * <br>
     * ���Ғl�F(��ԕω�) ��O:CastorUnsupportedEncodingException{<br>
     * cause = UnsupportedEncodingException<br> }<br>
     * (��ԕω�) ���O:���O���x��:error<br>
     * Character encoding error.<br>
     * 
     * <br>
     * ����argCharset���s���ȕ����R�[�h�̏ꍇ�A��O���X���[����A�A���O���o�͂���邱�Ƃ��m�F����B<br>
     * isr.close���\�b�h���Ăяo����Ȃ��������Ƃ��m�F����B <br>
     * 
     * @throws Exception
     *             ���̃��\�b�h�Ŕ���������O
     */
    public void testUnmarshalInputStreamStringObject04() throws Exception {
        // �O����
        CastorOXMapperImpl_CastorOXMapperImplStub02 oxmapper = new CastorOXMapperImpl_CastorOXMapperImplStub02();

        // ����
        CastorOXMapperImpl_InputStreamStub01 in = new CastorOXMapperImpl_InputStreamStub01();
        String argCharset = "abc";
        Object out = new Object();

        // �e�X�g���{
        try {
            oxmapper.unmarshal(in, argCharset, out);
            fail();
        } catch (CastorUnsupportedEncodingException e) {
            // ����
            assertSame(UnsupportedEncodingException.class, e.getCause()
                    .getClass());

            // ���O�m�F
            String message = "Character encoding error.";
            assertTrue(LogUTUtil.checkError(message, e.getCause()));

            // close���\�b�h���Ăяo����Ȃ��������Ƃ̊m�F
            assertFalse(in.isClose);
        }

    }

    /**
     * testUnmarshalInputStreamStringObject05() <br>
     * <br>
     * 
     * (�ُ�n) <br>
     * �ϓ_�FA,G <br>
     * <br>
     * ���͒l�F(����) is:not null<br>
     * (����) argCharset:"UTF-8"<br>
     * (����) out:not null<br>
     * (����) unmarshal():�Ăяo���ꂽ���Ƃ��m�F����B<br>
     * �������n���ꂽ���Ƃ��m�F����B<br>
     * (����) close():�Ăяo���ꂽ���Ƃ��m�F����B<br>
     * �������n���ꂽ���Ƃ��m�F����B<br>
     * (���) this.charset:"abc"<br>
     * 0
     * 
     * <br>
     * ���Ғl�F <br>
     * ����argCharset�̕����R�[�h���ݒ肳��Aunmarshal���\�b�h�Aisr.close���\�b�h���Ăяo����邱�Ƃ��m�F����B <br>
     * 
     * @throws Exception
     *             ���̃��\�b�h�Ŕ���������O
     */
    public void testUnmarshalInputStreamStringObject05() throws Exception {
        // �O����
        CastorOXMapperImpl_CastorOXMapperImplStub02 oxmapper = new CastorOXMapperImpl_CastorOXMapperImplStub02();
        UTUtil.setPrivateField(oxmapper, "charset", "abc");

        // ����
        CastorOXMapperImpl_InputStreamStub01 in = new CastorOXMapperImpl_InputStreamStub01();
        String argCharset = "UTF-8";
        Object out = new Object();

        // �e�X�g���{
        oxmapper.unmarshal(in, argCharset, out);

        // ����
        // unmarshal���\�b�h�Ɉ������n���ꂽ���Ƃ̊m�F
        assertSame(in, UTUtil.getPrivateField(oxmapper.reader, "lock"));
        assertSame(out, oxmapper.out);
        assertEquals("UTF8", oxmapper._charset);

        // close���\�b�h���Ăяo����Ȃ��������Ƃ̊m�F
        assertTrue(in.isClose);
    }

    /**
     * testCreateUnmarshaller01() <br>
     * <br>
     * 
     * (�ُ�n) <br>
     * �ϓ_�FA,C,G <br>
     * <br>
     * ���͒l�F(����) out:null<br>
     * (���) this.cache:false<br>
     * (���) this.mappingFileCache:���HashMap<br>
     * 
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalArgumentException{<br>
     * message = "Unmarshal object is null."<br> }<br>
     * (��ԕω�) ���O:���O���x��:error<br>
     * Unmarshal object is null.<br>
     * (��ԕω�) this.mappingFileCache:���HashMap<br>
     * 
     * <br>
     * ����out��null�̏ꍇ�A��O���X���[����A���O���o�͂���邱�Ƃ��m�F���� <br>
     * 
     * @throws Exception
     *             ���̃��\�b�h�Ŕ���������O
     */
    public void testCreateUnmarshaller01() throws Exception {
        // �O����
        CastorOXMapperImpl oxmapper = new CastorOXMapperImpl();

        // �L���b�V��
        UTUtil.setPrivateField(oxmapper, "cache", false);
        Map<Class, Mapping> map = new HashMap<Class, Mapping>();
        UTUtil.setPrivateField(oxmapper, "mappingFileCache", map);

        // ����
        Object out = null;

        // �e�X�g���{
        try {
            oxmapper.createUnmarshaller(out);
            fail();
        } catch (IllegalArgumentException e) {

            // ����
            String message = "Unmarshal object is null.";
            assertEquals(message, e.getMessage());

            // ���O�m�F
            assertTrue(LogUTUtil.checkError(message));
        }
    }

    /**
     * testCreateUnmarshaller02() <br>
     * <br>
     * 
     * (�ُ�n) <br>
     * �ϓ_�FA,G <br>
     * <br>
     * ���͒l�F(����) out:CastorOXMapperImpl_Stub01<br>
     * (���) �}�b�s���O��`�t�@�C��:�s���ȃ}�b�s���O��`�t�@�C��<br>
     * <?xml version="1.0"?><br>
     * <!DOCTYPE mapping PUBLIC "-//EXOLAB/Castor Object Mapping DTD Version
     * 1.0//EN" "http://castor.exolab.org/mapping.dtd"><br>
     * <mapping><br>
     * <class name="jp.terasoluna.fw.oxm.mapper.castor.xxxDTO"><br>
     * <field name="param1" type="int" /><br>
     * <field name="param2" type="date" /><br>
     * <field name="param3" type="string" /><br>
     * </class><br>
     * </mapping><br>
     * (���) getCastorMapping():�Ăяo���ꂽ���Ƃ��m�F����B<br>
     * �������n���ꂽ���Ƃ��m�F����B<br>
     * (���) this.cache:false<br>
     * (���) this.mappingFileCache:���HashMap<br>
     * 
     * <br>
     * ���Ғl�F(��ԕω�) ��O:CastorMappingException{<br>
     * cause = MappingException<br> }<br>
     * (��ԕω�) ���O:���O���x��:error<br>
     * Castor mapping file is invalid.<br>
     * (��ԕω�) this.mappingFileCache:���HashMap<br>
     * 
     * <br>
     * �}�b�s���O��`�t�@�C���̒�`�ɖ�肪����ꍇ�A��O���X���[����A���O���o�͂���邱�Ƃ��m�F����B <br>
     * 
     * @throws Exception
     *             ���̃��\�b�h�Ŕ���������O
     */
    public void testCreateUnmarshaller02() throws Exception {
        // �O����
        CastorOXMapperImpl_CastorOXMapperImplStub03 oxmapper = new CastorOXMapperImpl_CastorOXMapperImplStub03();
        oxmapper.mappingPath = packagePath
                + "CastorOXMapperImpl_JavaBeanStub02" + mappingSuffix;

        // �L���b�V��
        UTUtil.setPrivateField(oxmapper, "cache", false);
        Map<Class, Mapping> map = new HashMap<Class, Mapping>();
        UTUtil.setPrivateField(oxmapper, "mappingFileCache", map);

        // ����
        Object out = new Object();

        // �e�X�g���{
        try {
            oxmapper.createUnmarshaller(out);
            fail();
        } catch (CastorMappingException e) {
            assertSame(MappingException.class, e.getCause().getClass());
            // ���O�m�F
            String message = "Castor mapping file is invalid. "
                    + "- [root-classpath]/java/lang/Object.xml";
            assertTrue(LogUTUtil.checkError(message, e.getCause()));
        }

    }

    /**
     * testCreateUnmarshaller03() <br>
     * <br>
     * 
     * (����n) <br>
     * �ϓ_�FA,D <br>
     * <br>
     * ���͒l�F(����) out:CastorOXMapperImpl_Stub01<br>
     * (���) �}�b�s���O��`�t�@�C��:����ȃ}�b�s���O��`�t�@�C��<br>
     * <?xml version="1.0"?><br>
     * <!DOCTYPE mapping PUBLIC "-//EXOLAB/Castor Object Mapping DTD Version
     * 1.0//EN" "http://castor.exolab.org/mapping.dtd"><br>
     * <mapping><br>
     * <class
     * name=""jp.terasoluna.fw.oxm.mapper.castor.CastorOXMapperImpl_Stub01"><br>
     * <field name="param1" type="int" /><br>
     * <field name="param2" type="date" /><br>
     * <field name="param3" type="string" /><br>
     * </class><br>
     * </mapping><br>
     * (���) getCastorMapping():�Ăяo���ꂽ���Ƃ��m�F����B<br>
     * �������n���ꂽ���Ƃ��m�F����B<br>
     * (���) this.cache:false<br>
     * (���) this.mappingFileCache:���HashMap<br>
     * 
     * <br>
     * ���Ғl�F(�߂�l) Unmarshaller:Unmarshaller{<br>
     * _validate = false;<br>
     * _cdResolver._mappingLoader._javaClass[0]=
     * .CastorOXMapperImpl_Stub01.class<br> }<br>
     * (��ԕω�) this.mappingFileCache:���HashMap<br>
     * 
     * <br>
     * �}�b�s���O��`�ɖ�肪�Ȃ��ꍇ�A��`���ꂽ�N���X��ݒ肵���A���}�[�V������ԋp���邱�Ƃ��m�F����B<br>
     * �}�b�s���O�I�u�W�F�N�g�̃L���b�V�����s�Ȃ��Ȃ����Ƃ��m�F����B <br>
     * 
     * @throws Exception
     *             ���̃��\�b�h�Ŕ���������O
     */
    public void testCreateUnmarshaller03() throws Exception {
        // �O����
        CastorOXMapperImpl_CastorOXMapperImplStub03 oxmapper = new CastorOXMapperImpl_CastorOXMapperImplStub03();
        oxmapper.mappingPath = packagePath
                + "CastorOXMapperImpl_JavaBeanStub01" + mappingSuffix;
        Class testClass = CastorOXMapperImpl_JavaBeanStub01.class;

        // �L���b�V��
        UTUtil.setPrivateField(oxmapper, "cache", false);
        Map<Class, Mapping> map = new HashMap<Class, Mapping>();
        UTUtil.setPrivateField(oxmapper, "mappingFileCache", map);

        // ����
        Object out = new Object();

        // �e�X�g���{
        Unmarshaller unmarshaller = oxmapper.createUnmarshaller(out);
        // ����
        // unmarshal���\�b�h�Ɉ������n���ꂽ���Ƃ̊m�F
        assertEquals(out.getClass(), oxmapper.mappingClass);
        // Unmarshaller��validation��false�ɂȂ��Ă��邱�Ƃ̊m�F
        Boolean validate = (Boolean) UTUtil.getPrivateField(unmarshaller,
                "_validate");
        assertFalse(validate.booleanValue());
        // Unmarshaller��CastorOXMapperImpl_Stub01�N���X���ݒ肳��Ă��邱�Ƃ��m�F����
        ClassDescriptorResolver _cdResolver = (ClassDescriptorResolver) UTUtil
                .getPrivateField(unmarshaller, "_cdResolver");
        AbstractMappingLoader2 ml = (AbstractMappingLoader2) _cdResolver
                .getMappingLoader();
        Vector descriptors = (Vector) UTUtil
                .getPrivateField(ml, "_descriptors");
        assertEquals(1, descriptors.size());
        XMLClassDescriptorAdapter adapter = (XMLClassDescriptorAdapter) descriptors
                .get(0);
        assertEquals(testClass, adapter.getJavaClass());

        // �}�b�s���O�I�u�W�F�N�g���L���b�V������Ă��Ȃ����Ƃ��m�F����
        Map resultMap = (HashMap) UTUtil.getPrivateField(oxmapper,
                "mappingFileCache");
        assertEquals(0, resultMap.size());
    }

    /**
     * testCreateUnmarshaller04() <br>
     * <br>
     * 
     * (����n) <br>
     * �ϓ_�FA,D <br>
     * <br>
     * ���͒l�F(����) out:CastorOXMapperImpl_Stub01<br>
     * (���) �}�b�s���O��`�t�@�C��:����ȃ}�b�s���O��`�t�@�C��<br>
     * <?xml version="1.0"?><br>
     * <!DOCTYPE mapping PUBLIC "-//EXOLAB/Castor Object Mapping DTD Version
     * 1.0//EN" "http://castor.exolab.org/mapping.dtd"><br>
     * <mapping><br>
     * <class
     * name=""jp.terasoluna.fw.oxm.mapper.castor.CastorOXMapperImpl_Stub01"><br>
     * <field name="param1" type="int" /><br>
     * <field name="param2" type="date" /><br>
     * <field name="param3" type="string" /><br>
     * </class><br>
     * </mapping><br>
     * (���) getCastorMapping():�Ăяo���ꂽ���Ƃ��m�F����B<br>
     * �������n���ꂽ���Ƃ��m�F����B<br>
     * (���) this.cache:true<br>
     * (���) this.mappingFileCache:���HashMap<br>
     * 
     * <br>
     * ���Ғl�F(�߂�l) Unmarshaller:Unmarshaller{<br>
     * _validate = false;<br>
     * _cdResolver._mappingLoader._javaClass[0]=
     * .CastorOXMapperImpl_Stub01.class<br> }<br>
     * (��ԕω�) this.mappingFileCache:�v�f����1��HashMap<br>
     * HashMap<br> �o<br>
     * ������Class�C���X�^���X=Mapping�C���X�^���X<br> }<br>
     * 
     * <br>
     * �}�b�s���O��`�ɖ�肪�Ȃ��ꍇ�A��`���ꂽ�N���X��ݒ肵���A���}�[�V������ԋp���邱�Ƃ��m�F����B<br>
     * �}�b�s���O�I�u�W�F�N�g�̃L���b�V�����s�Ȃ��Ă��邱�Ƃ��m�F����B <br>
     * 
     * @throws Exception
     *             ���̃��\�b�h�Ŕ���������O
     */
    public void testCreateUnmarshaller04() throws Exception {
        // �O����
        CastorOXMapperImpl_CastorOXMapperImplStub03 oxmapper = new CastorOXMapperImpl_CastorOXMapperImplStub03();
        oxmapper.mappingPath = packagePath
                + "CastorOXMapperImpl_JavaBeanStub01" + mappingSuffix;
        Class testClass = CastorOXMapperImpl_JavaBeanStub01.class;

        // �L���b�V��
        UTUtil.setPrivateField(oxmapper, "cache", true);
        Map<Class, Mapping> map = new HashMap<Class, Mapping>();
        UTUtil.setPrivateField(oxmapper, "mappingFileCache", map);

        // ����
        Object out = new Object();

        // �e�X�g���{
        Unmarshaller unmarshaller = oxmapper.createUnmarshaller(out);
        // ����
        // unmarshal���\�b�h�Ɉ������n���ꂽ���Ƃ̊m�F
        assertEquals(out.getClass(), oxmapper.mappingClass);
        // Unmarshaller��validation��false�ɂȂ��Ă��邱�Ƃ̊m�F
        Boolean validate = (Boolean) UTUtil.getPrivateField(unmarshaller,
                "_validate");
        assertFalse(validate.booleanValue());
        // Unmarshaller��CastorOXMapperImpl_Stub01�N���X���ݒ肳��Ă��邱�Ƃ��m�F����
        ClassDescriptorResolver _cdResolver = (ClassDescriptorResolver) UTUtil
                .getPrivateField(unmarshaller, "_cdResolver");
        AbstractMappingLoader2 ml = (AbstractMappingLoader2) _cdResolver
                .getMappingLoader();
        Vector descriptors = (Vector) UTUtil
                .getPrivateField(ml, "_descriptors");
        assertEquals(1, descriptors.size());
        XMLClassDescriptorAdapter adapter = (XMLClassDescriptorAdapter) descriptors
                .get(0);
        assertEquals(testClass, adapter.getJavaClass());

        // �}�b�s���O�I�u�W�F�N�g���L���b�V������Ă��邱�Ƃ��m�F����
        Map resultMap = (HashMap) UTUtil.getPrivateField(oxmapper,
                "mappingFileCache");
        assertEquals(1, resultMap.size());
        assertSame(oxmapper.mapping, resultMap.get(out.getClass()));

    }

    /**
     * testCreateUnmarshaller05() <br>
     * <br>
     * 
     * (����n) <br>
     * �ϓ_�FA,D <br>
     * <br>
     * ���͒l�F(����) out:CastorOXMapperImpl_Stub01<br>
     * (���) �}�b�s���O��`�t�@�C��:����ȃ}�b�s���O��`�t�@�C��<br>
     * <?xml version="1.0"?><br>
     * <!DOCTYPE mapping PUBLIC "-//EXOLAB/Castor Object Mapping DTD Version
     * 1.0//EN" "http://castor.exolab.org/mapping.dtd"><br>
     * <mapping><br>
     * <class
     * name=""jp.terasoluna.fw.oxm.mapper.castor.CastorOXMapperImpl_Stub01"><br>
     * <field name="param1" type="int" /><br>
     * <field name="param2" type="date" /><br>
     * <field name="param3" type="string" /><br>
     * </class><br>
     * </mapping><br>
     * (���) getCastorMapping():�Ăяo���ꂽ���Ƃ��m�F����B<br>
     * �������n���ꂽ���Ƃ��m�F����B<br>
     * (���) this.cache:true<br>
     * (���) this.mappingFileCache:�v�f����1��HashMap<br>
     * HashMap<br> �o<br>
     * ������Class�C���X�^���X=Mapping�C���X�^���X<br> }<br>
     * 
     * <br>
     * ���Ғl�F(�߂�l) Unmarshaller:Unmarshaller{<br>
     * _validate = false;<br>
     * _cdResolver._mappingLoader._javaClass[0]=
     * .CastorOXMapperImpl_Stub01.class<br> }<br>
     * (��ԕω�) this.mappingFileCache:�v�f����1��HashMap<br>
     * HashMap<br> �o<br>
     * ������Class�C���X�^���X=Mapping�C���X�^���X<br> }<br>
     * 
     * <br>
     * �}�b�s���O��`�ɖ�肪�Ȃ��ꍇ�A��`���ꂽ�N���X��ݒ肵���A���}�[�V������ԋp���邱�Ƃ��m�F����B<br>
     * �}�b�s���O�I�u�W�F�N�g�̃L���b�V�����s�Ȃ�ꂸ�A�v�f���������Ă��Ȃ����Ƃ��m�F����B <br>
     * 
     * @throws Exception
     *             ���̃��\�b�h�Ŕ���������O
     */
    public void testCreateUnmarshaller05() throws Exception {
        // �O����
        CastorOXMapperImpl_CastorOXMapperImplStub03 oxmapper = new CastorOXMapperImpl_CastorOXMapperImplStub03();
        oxmapper.mappingPath = packagePath
                + "CastorOXMapperImpl_JavaBeanStub01" + mappingSuffix;
        Class testClass = CastorOXMapperImpl_JavaBeanStub01.class;

        // ����
        Object out = new Object();

        // �L���b�V��
        UTUtil.setPrivateField(oxmapper, "cache", true);
        Map<Class, Mapping> map = new HashMap<Class, Mapping>();
        Mapping mapping = new Mapping();
        map.put(out.getClass(), mapping);
        UTUtil.setPrivateField(oxmapper, "mappingFileCache", map);

        // �e�X�g���{
        Unmarshaller unmarshaller = oxmapper.createUnmarshaller(out);
        // ����
        // unmarshal���\�b�h�Ɉ������n���ꂽ���Ƃ̊m�F
        assertEquals(out.getClass(), oxmapper.mappingClass);
        // Unmarshaller��validation��false�ɂȂ��Ă��邱�Ƃ̊m�F
        Boolean validate = (Boolean) UTUtil.getPrivateField(unmarshaller,
                "_validate");
        assertFalse(validate.booleanValue());
        // Unmarshaller��CastorOXMapperImpl_Stub01�N���X���ݒ肳��Ă��邱�Ƃ��m�F����
        ClassDescriptorResolver _cdResolver = (ClassDescriptorResolver) UTUtil
                .getPrivateField(unmarshaller, "_cdResolver");
        AbstractMappingLoader2 ml = (AbstractMappingLoader2) _cdResolver
                .getMappingLoader();
        Vector descriptors = (Vector) UTUtil
                .getPrivateField(ml, "_descriptors");
        assertEquals(1, descriptors.size());
        XMLClassDescriptorAdapter adapter = (XMLClassDescriptorAdapter) descriptors
                .get(0);
        assertEquals(testClass, adapter.getJavaClass());

        // �}�b�s���O�I�u�W�F�N�g���L���b�V������Ă��Ȃ����Ƃ��m�F����
        Map resultMap = (HashMap) UTUtil.getPrivateField(oxmapper,
                "mappingFileCache");
        assertEquals(1, resultMap.size());
        assertSame(mapping, resultMap.get(out.getClass()));
    }

    /**
     * testCreateUnmarshaller06() <br>
     * <br>
     * 
     * (����n) <br>
     * �ϓ_�FA,D <br>
     * <br>
     * ���͒l�F<br>
     * (����) out:CastorOXMapperImpl_Stub01<br>
     * (���) preserveWhitespaceAtUnmarshal : true<br>
     * (���) �}�b�s���O��`�t�@�C��:����ȃ}�b�s���O��`�t�@�C��<br>
     * <?xml version="1.0"?><br>
     * <!DOCTYPE mapping PUBLIC "-//EXOLAB/Castor Object Mapping DTD Version
     * 1.0//EN" "http://castor.exolab.org/mapping.dtd"><br>
     * <mapping><br>
     * <class
     * name=""jp.terasoluna.fw.oxm.mapper.castor.CastorOXMapperImpl_Stub01"><br>
     * <field name="param1" type="int" /><br>
     * <field name="param2" type="date" /><br>
     * <field name="param3" type="string" /><br>
     * </class><br>
     * </mapping><br>
     * (���) getCastorMapping():�Ăяo���ꂽ���Ƃ��m�F����B<br>
     * �������n���ꂽ���Ƃ��m�F����B<br>
     * (���) this.cache:false<br>
     * (���) this.mappingFileCache:���HashMap<br>
     * 
     * <br>
     * ���Ғl�F<br>
     * (��ԕω�) Unmarshaller�̃t�B�[���h_wsPreserve : true<br>
     * 
     * <br>
     * setPreserveWhitespaceAtUnmarshal�Őݒ肳�ꂽ�I�v�V������ Unmarshaller�ɓn����Ă��邱�Ƃ��m�F����B<br>
     * 
     * @throws Exception
     *             ���̃��\�b�h�Ŕ���������O
     */
    public void testCreateUnmarshaller06() throws Exception {
        // �O����
        CastorOXMapperImpl_CastorOXMapperImplStub03 oxmapper = new CastorOXMapperImpl_CastorOXMapperImplStub03();
        oxmapper.mappingPath = packagePath
                + "CastorOXMapperImpl_JavaBeanStub01" + mappingSuffix;

        // �L���b�V��
        UTUtil.setPrivateField(oxmapper, "cache", false);
        Map<Class, Mapping> map = new HashMap<Class, Mapping>();
        UTUtil.setPrivateField(oxmapper, "mappingFileCache", map);

        // ����
        Object out = new Object();

        UTUtil.setPrivateField(oxmapper, "preserveWhitespaceAtUnmarshal", true);

        // �e�X�g���{
        Unmarshaller unmarshaller = oxmapper.createUnmarshaller(out);

        // ����
        Boolean wsPreserve = (Boolean) UTUtil.getPrivateField(unmarshaller,
                "_wsPreserve");
        assertTrue(wsPreserve);
    }

    /**
     * testCreateUnmarshaller07() <br>
     * <br>
     * 
     * (����n) <br>
     * �ϓ_�FA,D <br>
     * <br>
     * ���͒l�F<br>
     * (����) out:CastorOXMapperImpl_Stub01<br>
     * (���) preserveWhitespaceAtUnmarshal : false<br>
     * (���) �}�b�s���O��`�t�@�C��:����ȃ}�b�s���O��`�t�@�C��<br>
     * <?xml version="1.0"?><br>
     * <!DOCTYPE mapping PUBLIC "-//EXOLAB/Castor Object Mapping DTD Version
     * 1.0//EN" "http://castor.exolab.org/mapping.dtd"><br>
     * <mapping><br>
     * <class
     * name=""jp.terasoluna.fw.oxm.mapper.castor.CastorOXMapperImpl_Stub01"><br>
     * <field name="param1" type="int" /><br>
     * <field name="param2" type="date" /><br>
     * <field name="param3" type="string" /><br>
     * </class><br>
     * </mapping><br>
     * (���) getCastorMapping():�Ăяo���ꂽ���Ƃ��m�F����B<br>
     * �������n���ꂽ���Ƃ��m�F����B<br>
     * (���) this.cache:false<br>
     * (���) this.mappingFileCache:���HashMap<br>
     * 
     * <br>
     * ���Ғl�F<br>
     * (��ԕω�) Unmarshaller�̃t�B�[���h_wsPreserve : false<br>
     * 
     * <br>
     * setPreserveWhitespaceAtUnmarshal�Őݒ肳�ꂽ�I�v�V������ Unmarshaller�ɓn����Ă��邱�Ƃ��m�F����B<br>
     * 
     * @throws Exception
     *             ���̃��\�b�h�Ŕ���������O
     */
    public void testCreateUnmarshaller07() throws Exception {
        // �O����
        CastorOXMapperImpl_CastorOXMapperImplStub03 oxmapper = new CastorOXMapperImpl_CastorOXMapperImplStub03();
        oxmapper.mappingPath = packagePath
                + "CastorOXMapperImpl_JavaBeanStub01" + mappingSuffix;

        // �L���b�V��
        UTUtil.setPrivateField(oxmapper, "cache", false);
        Map<Class, Mapping> map = new HashMap<Class, Mapping>();
        UTUtil.setPrivateField(oxmapper, "mappingFileCache", map);

        // ����
        Object out = new Object();

        UTUtil.setPrivateField(oxmapper, "preserveWhitespaceAtUnmarshal", false);

        // �e�X�g���{
        Unmarshaller unmarshaller = oxmapper.createUnmarshaller(out);

        // ����
        Boolean wsPreserve = (Boolean) UTUtil.getPrivateField(unmarshaller,
                "_wsPreserve");
        assertFalse(wsPreserve);
    }

    /**
     * testMarshal01() <br>
     * <br>
     * 
     * (�ُ�n) <br>
     * �ϓ_�FA,C,G <br>
     * <br>
     * ���͒l�F(����) in:not null<br>
     * (����) writer:not null<br>
     * (���) createMarshaller():�Ăяo���ꂽ���Ƃ��m�F����B<br>
     * �������n���ꂽ���Ƃ��m�F����B<br>
     * (���) marshaller.marshal():MarshalException���X���[����<br>
     * 
     * <br>
     * ���Ғl�F(��ԕω�) ��O:CastorMarshalException{<br>
     * cause = MarshalException<br> }<br>
     * (��ԕω�) ���O:���O���x��:error<br>
     * Writer object is invalid.<br>
     * 
     * <br>
     * �s���ȃI�u�W�F�N�g���}�[�V�����ɐݒ肳�ꂽ�ꍇ�A��O���X���[����邱�Ƃ��m�F����B <br>
     * 
     * @throws Exception
     *             ���̃��\�b�h�Ŕ���������O
     */
    public void testMarshal01() throws Exception {
        // �O����
        CastorOXMapperImpl_CastorOXMapperImplStub01 oxmapper = new CastorOXMapperImpl_CastorOXMapperImplStub01();
        CastorOXMapperImpl_MarshallerStub01 marshaller = new CastorOXMapperImpl_MarshallerStub01(
                new StringWriter());
        oxmapper.marshaller = marshaller;

        Object in = new Object();
        Writer writer = new StringWriter();

        try {
            // �e�X�g���{
            oxmapper.marshal(in, writer);
            fail();
        } catch (CastorMarshalException e) {
            // ����
            assertSame(MarshalException.class, e.getCause().getClass());

            // ���O�m�F
            String message = "Castor marshal failure.";
            assertTrue(LogUTUtil.checkError(message, e.getCause()));

            // createMarshaller���\�b�h�Ɉ������n���ꂽ���Ƃ̊m�F
            assertSame(in, oxmapper.in);
            assertSame(writer, oxmapper.writer);

        }
    }

    /**
     * testMarshal02() <br>
     * <br>
     * 
     * (�ُ�n) <br>
     * �ϓ_�FA,C,G <br>
     * <br>
     * ���͒l�F(����) in:not null<br>
     * (����) writer:not null<br>
     * (���) createMarshaller():�Ăяo���ꂽ���Ƃ��m�F����B<br>
     * �������n���ꂽ���Ƃ��m�F����B<br>
     * (���) marshaller.marshal():ValidationException���X���[����<br>
     * 
     * <br>
     * ���Ғl�F(��ԕω�) ��O:CastorValidationException{<br>
     * cause = ValidationException<br> }<br>
     * (��ԕω�) ���O:���O���x��:error<br>
     * Castor validation error.<br>
     * 
     * <br>
     * �}�[�V�������Ƀo���f�[�V�����G���[�����������ꍇ�A��O���X���[����邱�Ƃ��m�F����B<br>
     * �i���ۂɂ͂��̗�O�̓X���[����Ȃ��j <br>
     * 
     * @throws Exception
     *             ���̃��\�b�h�Ŕ���������O
     */
    public void testMarshal02() throws Exception {
        // �O����
        CastorOXMapperImpl_CastorOXMapperImplStub01 oxmapper = new CastorOXMapperImpl_CastorOXMapperImplStub01();
        CastorOXMapperImpl_MarshallerStub02 marshaller = new CastorOXMapperImpl_MarshallerStub02(
                new StringWriter());
        oxmapper.marshaller = marshaller;

        Object in = new Object();
        Writer writer = new StringWriter();

        try {
            // �e�X�g���{
            oxmapper.marshal(in, writer);
            fail();
        } catch (CastorValidationException e) {
            // ����
            assertSame(ValidationException.class, e.getCause().getClass());

            // ���O�m�F
            String message = "Castor validation error.";
            assertTrue(LogUTUtil.checkError(message, e.getCause()));

            // createMarshaller���\�b�h�Ɉ������n���ꂽ���Ƃ̊m�F
            assertSame(in, oxmapper.in);
            assertSame(writer, oxmapper.writer);
        }
    }

    /**
     * testMarshal03() <br>
     * <br>
     * 
     * (����n) <br>
     * �ϓ_�FA <br>
     * <br>
     * ���͒l�F<br>
     * (����) in:null<br>
     * (����) writer:null<br>
     * (���) createMarshaller():�Ăяo���ꂽ���Ƃ��m�F����B<br>
     * �������n���ꂽ���Ƃ��m�F����B<br>
     * (���) marshaller.marshal():�Ăяo���ꂽ���Ƃ��m�F����B<br>
     * �������n���ꂽ���Ƃ��m�F����B<br>
     * 
     * <br>
     * ���Ғl�F <br>
     * ����n�̃p�^�[���B<br>
     * ����out,writer��null�̏ꍇ�Amarsharl���\�b�h���Ăяo���ꂽ���Ƃ̊m�F���s���B <br>
     * 
     * @throws Exception
     *             ���̃��\�b�h�Ŕ���������O
     */
    public void testMarshal03() throws Exception {
        // �O����
        CastorOXMapperImpl_CastorOXMapperImplStub01 oxmapper = new CastorOXMapperImpl_CastorOXMapperImplStub01();
        CastorOXMapperImpl_MarshallerStub03 marshaller = new CastorOXMapperImpl_MarshallerStub03(
                new StringWriter());
        oxmapper.marshaller = marshaller;

        CastorOXMapperImpl_UnmarshallerStub01 unmarshaller = new CastorOXMapperImpl_UnmarshallerStub01();
        oxmapper.unmarshaller = unmarshaller;

        Object in = null;
        Writer writer = null;

        // �e�X�g���{
        oxmapper.marshal(in, writer);

        // ����
        // createMarshaller���\�b�h�Ɉ������n���ꂽ���Ƃ̊m�F
        assertNull(oxmapper.in);
        assertNull(oxmapper.writer);

        // marshaller���\�b�h�Ɉ������n���ꂽ���Ƃ̊m�F
        assertNull(marshaller.in);

    }

    /**
     * testMarshal04() <br>
     * <br>
     * 
     * (����n) <br>
     * �ϓ_�FA <br>
     * <br>
     * ���͒l�F<br>
     * (����) in:not null<br>
     * (����) writer:not null<br>
     * (���) createMarshaller():�Ăяo���ꂽ���Ƃ��m�F����B<br>
     * �������n���ꂽ���Ƃ��m�F����B<br>
     * (���) marshaller.marshal():�Ăяo���ꂽ���Ƃ��m�F����B<br>
     * �������n���ꂽ���Ƃ��m�F����B<br>
     * 
     * <br>
     * ���Ғl�F <br>
     * ����n�̃p�^�[���B<br>
     * marsharl���\�b�h���Ăяo���ꂽ���Ƃ̊m�F���s���B <br>
     * 
     * @throws Exception
     *             ���̃��\�b�h�Ŕ���������O
     */
    public void testMarshal04() throws Exception {
        // �O����
        CastorOXMapperImpl_CastorOXMapperImplStub01 oxmapper = new CastorOXMapperImpl_CastorOXMapperImplStub01();
        CastorOXMapperImpl_MarshallerStub03 marshaller = new CastorOXMapperImpl_MarshallerStub03(
                new StringWriter());
        oxmapper.marshaller = marshaller;

        Object in = new Object();
        Writer writer = new StringWriter();

        // �e�X�g���{
        oxmapper.marshal(in, writer);

        // ����
        // createMarshaller���\�b�h�Ɉ������n���ꂽ���Ƃ̊m�F
        assertSame(in, oxmapper.in);
        assertSame(writer, oxmapper.writer);

        // marshaller���\�b�h�Ɉ������n���ꂽ���Ƃ̊m�F
        assertSame(in, marshaller.in);

    }

    /**
     * testCreateMarshaller01() <br>
     * <br>
     * 
     * (�ُ�n) <br>
     * �ϓ_�FA,C,G <br>
     * <br>
     * ���͒l�F(����) in:null<br>
     * (����) writer:not null<br>
     * (���) this.cache:false<br>
     * (���) this.mappingFileCache:���HashMap<br>
     * 
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalArgumentException{<br>
     * message="Marshal object is null."<br> }<br>
     * (��ԕω�) ���O:���O���x��:error<br>
     * Marshal object is null.<br>
     * (��ԕω�) this.mappingFileCache:���HashMap<br>
     * 
     * <br>
     * ����in��null�̏ꍇ�A��O���X���[����邱�Ƃ��m�F���� <br>
     * 
     * @throws Exception
     *             ���̃��\�b�h�Ŕ���������O
     */
    public void testCreateMarshaller01() throws Exception {
        // �O����
        CastorOXMapperImpl oxmapper = new CastorOXMapperImpl();

        // ����
        Object in = null;
        Writer writer = new StringWriter();

        // �e�X�g���{
        try {
            oxmapper.createMarshaller(in, writer);
            fail();
        } catch (IllegalArgumentException e) {

            // ����
            String message = "Marshall object is null.";
            assertEquals(message, e.getMessage());

            // ���O�m�F
            assertTrue(LogUTUtil.checkError(message));
        }
    }

    /**
     * testCreateMarshaller02() <br>
     * <br>
     * 
     * (�ُ�n) <br>
     * �ϓ_�FA,C,G <br>
     * <br>
     * ���͒l�F(����) in:not null<br>
     * (����) writer:null<br>
     * (���) this.cache:false<br>
     * (���) this.mappingFileCache:���HashMap<br>
     * 
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalArgumentException{<br>
     * message="Writer is null."<br> }<br>
     * (��ԕω�) ���O:���O���x��:error<br>
     * Writer is null.<br>
     * (��ԕω�) this.mappingFileCache:���HashMap<br>
     * 
     * <br>
     * ����writer��null�̏ꍇ�A��O���X���[����邱�Ƃ��m�F���� <br>
     * 
     * @throws Exception
     *             ���̃��\�b�h�Ŕ���������O
     */
    public void testCreateMarshaller02() throws Exception {
        // �O����
        CastorOXMapperImpl oxmapper = new CastorOXMapperImpl();

        // ����
        Object in = new Object();
        Writer writer = null;

        // �e�X�g���{
        try {
            oxmapper.createMarshaller(in, writer);
            fail();
        } catch (IllegalArgumentException e) {

            // ����
            String message = "Writer is null.";
            assertEquals(message, e.getMessage());

            // ���O�m�F
            assertTrue(LogUTUtil.checkError(message));
        }
    }

    /**
     * testCreateMarshaller03() <br>
     * <br>
     * 
     * (�ُ�n) <br>
     * �ϓ_�FA,C,G <br>
     * <br>
     * ���͒l�F(����) in:CastorOXMapperImpl_Stub01<br>
     * (����) writer:not null<br>
     * (���) �}�b�s���O��`�t�@�C��:�s���ȃ}�b�s���O��`�t�@�C��<br>
     * <?xml version="1.0"?><br>
     * <!DOCTYPE mapping PUBLIC "-//EXOLAB/Castor Object Mapping DTD Version
     * 1.0//EN" "http://castor.exolab.org/mapping.dtd"><br>
     * <mapping><br>
     * <class name="jp.terasoluna.fw.oxm.mapper.castor.xxxDTO"><br>
     * <field name="param1" type="int" /><br>
     * <field name="param2" type="date" /><br>
     * <field name="param3" type="int" /><br>
     * </class><br>
     * </mapping><br>
     * (���) getCastorMapping():�Ăяo���ꂽ���Ƃ��m�F����B<br>
     * �������n���ꂽ���Ƃ��m�F����B<br>
     * (���) this.cache:false<br>
     * (���) this.mappingFileCache:���HashMap<br>
     * 
     * <br>
     * ���Ғl�F(��ԕω�) ��O:CastorMappingException{<br>
     * cause = MappingException<br> }<br>
     * (��ԕω�) ���O:���O���x��:error<br>
     * Castor mapping file is invalid.<br>
     * (��ԕω�) this.mappingFileCache:���HashMap<br>
     * 
     * <br>
     * �}�b�s���O��`�t�@�C�����s���ȏꍇ�A��O���X���[����邱�Ƃ��m�F����B <br>
     * 
     * @throws Exception
     *             ���̃��\�b�h�Ŕ���������O
     */
    public void testCreateMarshaller03() throws Exception {
        // �O����
        CastorOXMapperImpl_CastorOXMapperImplStub03 oxmapper = new CastorOXMapperImpl_CastorOXMapperImplStub03();
        oxmapper.mappingPath = packagePath
                + "CastorOXMapperImpl_JavaBeanStub02" + mappingSuffix;

        // ����
        Object in = new Object();
        Writer writer = new StringWriter();

        // �e�X�g���{
        try {
            oxmapper.createMarshaller(in, writer);
            fail();
        } catch (CastorMappingException e) {
            assertSame(MappingException.class, e.getCause().getClass());
            // ���O�m�F
            String message = "Castor mapping file is invalid. "
                    + "- [root-classpath]/java/lang/Object.xml";
            assertTrue(LogUTUtil.checkError(message, e.getCause()));
        }
    }

    /**
     * testCreateMarshaller04() <br>
     * <br>
     * 
     * (����n) <br>
     * �ϓ_�FA,D <br>
     * <br>
     * ���͒l�F(����) in:CastorOXMapperImpl_Stub01<br>
     * (����) writer:not null<br>
     * (���) �}�b�s���O��`�t�@�C��:����ȃ}�b�s���O��`�t�@�C��<br>
     * <?xml version="1.0"?><br>
     * <!DOCTYPE mapping PUBLIC "-//EXOLAB/Castor Object Mapping DTD Version
     * 1.0//EN" "http://castor.exolab.org/mapping.dtd"><br>
     * <mapping><br>
     * <class
     * name="jp.terasoluna.fw.oxm.mapper.castor.CastorOXMapperImpl_Stub01"><br>
     * <field name="param1" type="int" /><br>
     * <field name="param2" type="date" /><br>
     * <field name="param3" type="string" /><br>
     * </class><br>
     * </mapping><br>
     * (���) getCastorMapping():�Ăяo���ꂽ���Ƃ��m�F����B<br>
     * �������n���ꂽ���Ƃ��m�F����B<br>
     * (���) this.cache:false<br>
     * (���) this.mappingFileCache:���HashMap<br>
     * 
     * <br>
     * ���Ғl�F(�߂�l) Marshaller:Marshaller{<br>
     * _validate = false;<br>
     * _cdResolver._mappingLoader._javaClass[0]=CastorOXMapperImpl_Stub01.class<br> }<br>
     * (��ԕω�) this.mappingFileCache:�v�f����1��HashMap<br>
     * HashMap<br> �o<br>
     * ������Class�C���X�^���X=Mapping�C���X�^���X<br> }<br>
     * 
     * <br>
     * �}�b�s���O��`�t�@�C�����������ꍇ�A����Ƀ}�[�V��������������邱�Ƃ��m�F����B<br>
     * �}�b�s���O�I�u�W�F�N�g�̃L���b�V�����s�Ȃ��Ȃ����Ƃ��m�F����B <br>
     * 
     * @throws Exception
     *             ���̃��\�b�h�Ŕ���������O
     */
    public void testCreateMarshaller04() throws Exception {
        // �O����
        CastorOXMapperImpl_CastorOXMapperImplStub03 oxmapper = new CastorOXMapperImpl_CastorOXMapperImplStub03();
        oxmapper.mappingPath = packagePath
                + "CastorOXMapperImpl_JavaBeanStub01" + mappingSuffix;
        Class testClass = CastorOXMapperImpl_JavaBeanStub01.class;

        // �L���b�V��
        UTUtil.setPrivateField(oxmapper, "cache", false);
        Map<Class, Mapping> map = new HashMap<Class, Mapping>();
        UTUtil.setPrivateField(oxmapper, "mappingFileCache", map);

        // ����
        Object in = new Object();
        Writer writer = new StringWriter();

        // �e�X�g���{
        Marshaller marshaller = oxmapper.createMarshaller(in, writer);

        // ����
        // marshal���\�b�h�Ɉ������n���ꂽ���Ƃ̊m�F
        // ����out
        assertSame(in.getClass(), oxmapper.mappingClass);

        // Marshaller��validation��false�ɂȂ��Ă��邱�Ƃ̊m�F
        Boolean validate = (Boolean) UTUtil.getPrivateField(marshaller,
                "_validate");
        assertFalse(validate.booleanValue());
        // Marshaller��CastorOXMapperImpl_Stub01�N���X���ݒ肳��Ă��邱�Ƃ��m�F����
        ClassDescriptorResolver _cdResolver = (ClassDescriptorResolver) UTUtil
                .getPrivateField(marshaller, "_cdResolver");
        AbstractMappingLoader2 ml = (AbstractMappingLoader2) _cdResolver
                .getMappingLoader();
        Vector descriptors = (Vector) UTUtil
                .getPrivateField(ml, "_descriptors");
        assertEquals(1, descriptors.size());
        XMLClassDescriptorAdapter adapter = (XMLClassDescriptorAdapter) descriptors
                .get(0);
        assertEquals(testClass, adapter.getJavaClass());

        // �}�b�s���O�I�u�W�F�N�g���L���b�V������Ă��Ȃ����Ƃ��m�F����
        Map resultMap = (HashMap) UTUtil.getPrivateField(oxmapper,
                "mappingFileCache");
        assertEquals(0, resultMap.size());
    }

    /**
     * testCreateMarshaller05() <br>
     * <br>
     * 
     * (����n) <br>
     * �ϓ_�FA,D <br>
     * <br>
     * ���͒l�F(����) in:CastorOXMapperImpl_Stub01<br>
     * (����) writer:not null<br>
     * (���) �}�b�s���O��`�t�@�C��:����ȃ}�b�s���O��`�t�@�C��<br>
     * <?xml version="1.0"?><br>
     * <!DOCTYPE mapping PUBLIC "-//EXOLAB/Castor Object Mapping DTD Version
     * 1.0//EN" "http://castor.exolab.org/mapping.dtd"><br>
     * <mapping><br>
     * <class
     * name="jp.terasoluna.fw.oxm.mapper.castor.CastorOXMapperImpl_Stub01"><br>
     * <field name="param1" type="int" /><br>
     * <field name="param2" type="date" /><br>
     * <field name="param3" type="string" /><br>
     * </class><br>
     * </mapping><br>
     * (���) getCastorMapping():�Ăяo���ꂽ���Ƃ��m�F����B<br>
     * �������n���ꂽ���Ƃ��m�F����B<br>
     * (���) this.cache:true<br>
     * (���) this.mappingFileCache:���HashMap<br>
     * 
     * <br>
     * ���Ғl�F(�߂�l) Marshaller:Marshaller{<br>
     * _validate = false;<br>
     * _cdResolver._mappingLoader._javaClass[0]=CastorOXMapperImpl_Stub01.class<br> }<br>
     * (��ԕω�) this.mappingFileCache:�v�f����1��HashMap<br>
     * HashMap<br> �o<br>
     * ������Class�C���X�^���X=Mapping�C���X�^���X<br> }<br>
     * 
     * <br>
     * �}�b�s���O��`�t�@�C�����������ꍇ�A����Ƀ}�[�V��������������邱�Ƃ��m�F����B<br>
     * �}�b�s���O�I�u�W�F�N�g�̃L���b�V�����s�Ȃ��Ă��邱�Ƃ��m�F����B <br>
     * 
     * @throws Exception
     *             ���̃��\�b�h�Ŕ���������O
     */
    public void testCreateMarshaller05() throws Exception {
        // �O����
        CastorOXMapperImpl_CastorOXMapperImplStub03 oxmapper = new CastorOXMapperImpl_CastorOXMapperImplStub03();
        oxmapper.mappingPath = packagePath
                + "CastorOXMapperImpl_JavaBeanStub01" + mappingSuffix;
        Class testClass = CastorOXMapperImpl_JavaBeanStub01.class;

        // �L���b�V��
        UTUtil.setPrivateField(oxmapper, "cache", true);
        Map<Class, Mapping> map = new HashMap<Class, Mapping>();
        UTUtil.setPrivateField(oxmapper, "mappingFileCache", map);

        // ����
        Object in = new Object();
        Writer writer = new StringWriter();

        // �e�X�g���{
        Marshaller marshaller = oxmapper.createMarshaller(in, writer);

        // ����
        // marshal���\�b�h�Ɉ������n���ꂽ���Ƃ̊m�F
        // ����out
        assertSame(in.getClass(), oxmapper.mappingClass);

        // Marshaller��validation��false�ɂȂ��Ă��邱�Ƃ̊m�F
        Boolean validate = (Boolean) UTUtil.getPrivateField(marshaller,
                "_validate");
        assertFalse(validate.booleanValue());
        // Marshaller��CastorOXMapperImpl_Stub01�N���X���ݒ肳��Ă��邱�Ƃ��m�F����
        ClassDescriptorResolver _cdResolver = (ClassDescriptorResolver) UTUtil
                .getPrivateField(marshaller, "_cdResolver");
        AbstractMappingLoader2 ml = (AbstractMappingLoader2) _cdResolver
                .getMappingLoader();
        Vector descriptors = (Vector) UTUtil
                .getPrivateField(ml, "_descriptors");
        assertEquals(1, descriptors.size());
        XMLClassDescriptorAdapter adapter = (XMLClassDescriptorAdapter) descriptors
                .get(0);
        assertEquals(testClass, adapter.getJavaClass());

        // �}�b�s���O�I�u�W�F�N�g���L���b�V������Ă��邱�Ƃ��m�F����
        Map resultMap = (HashMap) UTUtil.getPrivateField(oxmapper,
                "mappingFileCache");
        assertEquals(1, resultMap.size());
        assertSame(oxmapper.mapping, resultMap.get(in.getClass()));

    }

    /**
     * testCreateMarshaller06() <br>
     * <br>
     * 
     * (����n) <br>
     * �ϓ_:A,D <br>
     * <br>
     * ���͒l�F(����) in:CastorOXMapperImpl_Stub01<br>
     * (����) writer:not null<br>
     * (���) �}�b�s���O��`�t�@�C��:����ȃ}�b�s���O��`�t�@�C��<br>
     * <?xml version="1.0"?><br>
     * <!DOCTYPE mapping PUBLIC "-//EXOLAB/Castor Object Mapping DTD Version
     * 1.0//EN" "http://castor.exolab.org/mapping.dtd"><br>
     * <mapping><br>
     * <class
     * name="jp.terasoluna.fw.oxm.mapper.castor.CastorOXMapperImpl_Stub01"><br>
     * <field name="param1" type="int" /><br>
     * <field name="param2" type="date" /><br>
     * <field name="param3" type="string" /><br>
     * </class><br>
     * </mapping><br>
     * (���) getCastorMapping():�Ăяo���ꂽ���Ƃ��m�F����B<br>
     * �������n���ꂽ���Ƃ��m�F����B<br>
     * (���) this.cache:true<br>
     * (���) this.mappingFileCache:�v�f����1��HashMap<br>
     * HashMap<br> �o<br>
     * ������Class�C���X�^���X=Mapping�C���X�^���X<br> }<br>
     * 
     * <br>
     * ���Ғl�F(�߂�l) Marshaller:Marshaller{<br>
     * _validate = false;<br>
     * _cdResolver._mappingLoader._javaClass[0]=CastorOXMapperImpl_Stub01.class<br> }<br>
     * (��ԕω�) this.mappingFileCache:�v�f����1��HashMap<br>
     * HashMap<br> �o<br>
     * ������Class�C���X�^���X=Mapping�C���X�^���X<br> }<br>
     * 
     * <br>
     * �}�b�s���O��`�t�@�C�����������ꍇ�A����Ƀ}�[�V��������������邱�Ƃ��m�F����B<br>
     * �}�b�s���O�I�u�W�F�N�g�̃L���b�V�����s�Ȃ�ꂸ�A�v�f���������Ă��Ȃ����Ƃ��m�F����B <br>
     * 
     * @throws Exception
     *             ���̃��\�b�h�Ŕ���������O
     */
    public void testCreateMarshaller06() throws Exception {
        // �O����
        CastorOXMapperImpl_CastorOXMapperImplStub03 oxmapper = new CastorOXMapperImpl_CastorOXMapperImplStub03();
        oxmapper.mappingPath = packagePath
                + "CastorOXMapperImpl_JavaBeanStub01" + mappingSuffix;
        Class testClass = CastorOXMapperImpl_JavaBeanStub01.class;

        // ����
        Object in = new Object();
        Writer writer = new StringWriter();

        // �L���b�V��
        UTUtil.setPrivateField(oxmapper, "cache", true);
        Map<Class, Mapping> map = new HashMap<Class, Mapping>();
        Mapping mapping = new Mapping();
        map.put(in.getClass(), mapping);
        UTUtil.setPrivateField(oxmapper, "mappingFileCache", map);

        // �e�X�g���{
        Marshaller marshaller = oxmapper.createMarshaller(in, writer);

        // ����
        // marshal���\�b�h�Ɉ������n���ꂽ���Ƃ̊m�F
        // ����out
        assertSame(in.getClass(), oxmapper.mappingClass);

        // Marshaller��validation��false�ɂȂ��Ă��邱�Ƃ̊m�F
        Boolean validate = (Boolean) UTUtil.getPrivateField(marshaller,
                "_validate");
        assertFalse(validate.booleanValue());
        // Marshaller��CastorOXMapperImpl_Stub01�N���X���ݒ肳��Ă��邱�Ƃ��m�F����
        ClassDescriptorResolver _cdResolver = (ClassDescriptorResolver) UTUtil
                .getPrivateField(marshaller, "_cdResolver");
        AbstractMappingLoader2 ml = (AbstractMappingLoader2) _cdResolver
                .getMappingLoader();
        Vector descriptors = (Vector) UTUtil
                .getPrivateField(ml, "_descriptors");
        assertEquals(1, descriptors.size());
        XMLClassDescriptorAdapter adapter = (XMLClassDescriptorAdapter) descriptors
                .get(0);
        assertEquals(testClass, adapter.getJavaClass());

        // �}�b�s���O�I�u�W�F�N�g���L���b�V������Ă��Ȃ����Ƃ��m�F����
        Map resultMap = (HashMap) UTUtil.getPrivateField(oxmapper,
                "mappingFileCache");
        assertEquals(1, resultMap.size());
        assertSame(mapping, resultMap.get(in.getClass()));
    }

    /**
     * testCreateMarshaller07() <br>
     * <br>
     * 
     * (����n) <br>
     * �ϓ_:A,D <br>
     * <br>
     * ���͒l�F(����) in:CastorOXMapperImpl_Stub01<br>
     * (����) writer:not null<br>
     * (���) �}�b�s���O��`�t�@�C��:����ȃ}�b�s���O��`�t�@�C��<br>
     * <?xml version="1.0"?><br>
     * <!DOCTYPE mapping PUBLIC "-//EXOLAB/Castor Object Mapping DTD Version
     * 1.0//EN" "http://castor.exolab.org/mapping.dtd"><br>
     * <mapping><br>
     * <class
     * name="jp.terasoluna.fw.oxm.mapper.castor.CastorOXMapperImpl_Stub01"><br>
     * <field name="param1" type="int" /><br>
     * <field name="param2" type="date" /><br>
     * <field name="param3" type="string" /><br>
     * </class><br>
     * </mapping><br>
     * (���) this.sharedResolverForMarshaller:�L���b�V������Ă��邱�Ƃ��m�F����i1��ځj<br>
     * (���) this.sharedResolverForMarshaller:�L���b�V������Ă��邱�Ƃ��m�F����i2��ځj<br>
     * (���) this.sharedResolverForMarshaller:1��ڂ�2��ڂ̃C���X�^���X�������ł��邱�Ƃ��m�F����<br>
     * 
     * <br>
     * �}�b�s���O��`�t�@�C�����������ꍇ�A�����ClassDescriptorResolver���L���b�V������Ă��邱�Ƃ��m�F����B<br>
     * ClassDescriptorResolver�̃L���b�V�����s�Ȃ��A2��ڂɓ���C���X�^���X�ł��邱�Ƃ��m�F����B<br>
     * 
     * @throws Exception
     *             ���̃��\�b�h�Ŕ���������O
     */
    public void testCreateMarshaller07() throws Exception {
        // �O����
        CastorOXMapperImpl_CastorOXMapperImplStub03 oxmapper = new CastorOXMapperImpl_CastorOXMapperImplStub03();
        oxmapper.mappingPath = packagePath
                + "CastorOXMapperImpl_JavaBeanStub01" + mappingSuffix;

        // ����
        Object in = new Object();
        Writer writer = new StringWriter();

        // �L���b�V��
        UTUtil.setPrivateField(oxmapper, "cache", true);
        Map<Class, Mapping> map = new HashMap<Class, Mapping>();
        Mapping mapping = new Mapping();
        map.put(in.getClass(), mapping);
        UTUtil.setPrivateField(oxmapper, "mappingFileCache", map);

        // �e�X�g���{
        Marshaller marshaller = oxmapper.createMarshaller(in, writer);
        assertNotNull(marshaller);

        // ���]���o���L���b�V������Ă��邱�Ƃ̊m�F
        Object resolver1 = UTUtil.getPrivateField(oxmapper,
                "sharedResolverForMarshaller");
        assertNotNull(resolver1);
        assertTrue(resolver1 instanceof org.exolab.castor.xml.XMLClassDescriptorResolver);
        assertTrue(resolver1 instanceof org.exolab.castor.xml.ClassDescriptorResolver);

        // �e�X�g���{
        marshaller = oxmapper.createMarshaller(in, writer);
        assertNotNull(marshaller);

        // ���]���o���L���b�V������Ă��邱�Ƃ̊m�F
        Object resolver2 = UTUtil.getPrivateField(oxmapper,
                "sharedResolverForMarshaller");
        assertNotNull(resolver2);
        assertTrue(resolver2 instanceof org.exolab.castor.xml.XMLClassDescriptorResolver);
        assertTrue(resolver2 instanceof org.exolab.castor.xml.ClassDescriptorResolver);

        // �����C���X�^���X�ł��邱�Ƃ̊m�F
        assertEquals(resolver1, resolver2);

    }

    /**
     * testCreateMarshaller08() <br>
     * <br>
     * 
     * (����n) <br>
     * �ϓ_�FA,D <br>
     * <br>
     * ���͒l�F<br>
     * (����) in:CastorOXMapperImpl_Stub01<br>
     * (����) writer:not null<br>
     * (����) suppressXSIType : true<br>
     * (���) �}�b�s���O��`�t�@�C��:����ȃ}�b�s���O��`�t�@�C��<br>
     * <?xml version="1.0"?><br>
     * <!DOCTYPE mapping PUBLIC "-//EXOLAB/Castor Object Mapping DTD Version
     * 1.0//EN" "http://castor.exolab.org/mapping.dtd"><br>
     * <mapping><br>
     * <class
     * name="jp.terasoluna.fw.oxm.mapper.castor.CastorOXMapperImpl_Stub01"><br>
     * <field name="param1" type="int" /><br>
     * <field name="param2" type="date" /><br>
     * <field name="param3" type="string" /><br>
     * </class><br>
     * </mapping><br>
     * (���) getCastorMapping():�Ăяo���ꂽ���Ƃ��m�F����B<br>
     * �������n���ꂽ���Ƃ��m�F����B<br>
     * (���) this.cache:true<br>
     * (���) this.mappingFileCache:���HashMap<br>
     * 
     * <br>
     * ���Ғl�F<br>
     * (��ԕω�) Marshaller �� _suppressXSIType : true<br>
     * 
     * <br>
     * suppressXSIType �̐ݒ肪 Marshaller �ɓn����邱�Ƃ��m�F����B<br>
     * 
     * @throws Exception
     *             ���̃��\�b�h�Ŕ���������O
     */
    public void testCreateMarshaller08() throws Exception {
        // �O����
        CastorOXMapperImpl_CastorOXMapperImplStub03 oxmapper = new CastorOXMapperImpl_CastorOXMapperImplStub03();
        oxmapper.mappingPath = packagePath
                + "CastorOXMapperImpl_JavaBeanStub01" + mappingSuffix;

        // �L���b�V��
        UTUtil.setPrivateField(oxmapper, "cache", true);
        Map<Class, Mapping> map = new HashMap<Class, Mapping>();
        UTUtil.setPrivateField(oxmapper, "mappingFileCache", map);

        // ����
        Object in = new Object();
        Writer writer = new StringWriter();

        UTUtil.setPrivateField(oxmapper, "suppressXSIType", true);

        // �e�X�g���{
        Marshaller marshaller = oxmapper.createMarshaller(in, writer);

        // ����
        Boolean suppressXSIType = (Boolean) UTUtil.getPrivateField(marshaller, "_suppressXSIType");
        
        assertTrue(suppressXSIType);
    }

    /**
     * testCreateMarshaller09() <br>
     * <br>
     * 
     * (����n) <br>
     * �ϓ_�FA,D <br>
     * <br>
     * ���͒l�F<br>
     * (����) in:CastorOXMapperImpl_Stub01<br>
     * (����) writer:not null<br>
     * (����) suppressXSIType : true<br>
     * (���) �}�b�s���O��`�t�@�C��:����ȃ}�b�s���O��`�t�@�C��<br>
     * <?xml version="1.0"?><br>
     * <!DOCTYPE mapping PUBLIC "-//EXOLAB/Castor Object Mapping DTD Version
     * 1.0//EN" "http://castor.exolab.org/mapping.dtd"><br>
     * <mapping><br>
     * <class
     * name="jp.terasoluna.fw.oxm.mapper.castor.CastorOXMapperImpl_Stub01"><br>
     * <field name="param1" type="int" /><br>
     * <field name="param2" type="date" /><br>
     * <field name="param3" type="string" /><br>
     * </class><br>
     * </mapping><br>
     * (���) getCastorMapping():�Ăяo���ꂽ���Ƃ��m�F����B<br>
     * �������n���ꂽ���Ƃ��m�F����B<br>
     * (���) this.cache:true<br>
     * (���) this.mappingFileCache:���HashMap<br>
     * 
     * <br>
     * ���Ғl�F<br>
     * (��ԕω�) Marshaller �� _suppressXSIType : false<br>
     * 
     * <br>
     * suppressXSIType �̐ݒ肪 Marshaller �ɓn����邱�Ƃ��m�F����B<br>
     * 
     * @throws Exception
     *             ���̃��\�b�h�Ŕ���������O
     */
    public void testCreateMarshaller09() throws Exception {
        // �O����
        CastorOXMapperImpl_CastorOXMapperImplStub03 oxmapper = new CastorOXMapperImpl_CastorOXMapperImplStub03();
        oxmapper.mappingPath = packagePath
                + "CastorOXMapperImpl_JavaBeanStub01" + mappingSuffix;

        // �L���b�V��
        UTUtil.setPrivateField(oxmapper, "cache", true);
        Map<Class, Mapping> map = new HashMap<Class, Mapping>();
        UTUtil.setPrivateField(oxmapper, "mappingFileCache", map);

        // ����
        Object in = new Object();
        Writer writer = new StringWriter();

        UTUtil.setPrivateField(oxmapper, "suppressXSIType", false);

        // �e�X�g���{
        Marshaller marshaller = oxmapper.createMarshaller(in, writer);

        // ����
        Boolean suppressXSIType = (Boolean) UTUtil.getPrivateField(marshaller, "_suppressXSIType");
        
        assertFalse(suppressXSIType);
    }    

    /**
     * testCreateMarshaller10() <br>
     * <br>
     * 
     * (����n) <br>
     * �ϓ_�FA,D <br>
     * <br>
     * ���͒l�F<br>
     * (����) in:CastorOXMapperImpl_Stub01<br>
     * (����) writer:not null<br>
     * (����) preserveWhitespaceAtMarshal : true<br>
     * (���) �}�b�s���O��`�t�@�C��:����ȃ}�b�s���O��`�t�@�C��<br>
     * <?xml version="1.0"?><br>
     * <!DOCTYPE mapping PUBLIC "-//EXOLAB/Castor Object Mapping DTD Version
     * 1.0//EN" "http://castor.exolab.org/mapping.dtd"><br>
     * <mapping><br>
     * <class
     * name="jp.terasoluna.fw.oxm.mapper.castor.CastorOXMapperImpl_Stub01"><br>
     * <field name="param1" type="int" /><br>
     * <field name="param2" type="date" /><br>
     * <field name="param3" type="string" /><br>
     * </class><br>
     * </mapping><br>
     * (���) getCastorMapping():�Ăяo���ꂽ���Ƃ��m�F����B<br>
     * �������n���ꂽ���Ƃ��m�F����B<br>
     * (���) this.cache:true<br>
     * (���) this.mappingFileCache:���HashMap<br>
     * 
     * <br>
     * ���Ғl�F<br>
     * (��ԕω�) Marshaller �� ContentHandler �� preserveWhitespace : true<br>
     * 
     * <br>
     * preserveWhitespace �̐ݒ肪 Marshaller �� ContentHandler �ɓn����邱�Ƃ��m�F����B<br>
     * 
     * @throws Exception
     *             ���̃��\�b�h�Ŕ���������O
     */
    public void testCreateMarshaller10() throws Exception {
        // �O����
        CastorOXMapperImpl_CastorOXMapperImplStub03 oxmapper = new CastorOXMapperImpl_CastorOXMapperImplStub03();
        oxmapper.mappingPath = packagePath
                + "CastorOXMapperImpl_JavaBeanStub01" + mappingSuffix;

        // �L���b�V��
        UTUtil.setPrivateField(oxmapper, "cache", true);
        Map<Class, Mapping> map = new HashMap<Class, Mapping>();
        UTUtil.setPrivateField(oxmapper, "mappingFileCache", map);

        // ����
        CastorOXMapperImpl_JavaBeanStub01 in = new CastorOXMapperImpl_JavaBeanStub01();
        Writer writer = new StringWriter();

        UTUtil.setPrivateField(oxmapper, "preserveWhitespaceAtMarshal", true);

        // �e�X�g���{
        Marshaller marshaller = oxmapper.createMarshaller(in, writer);
        marshaller.marshal(in);
        
        // ����
        XMLSerializerEx handler = (XMLSerializerEx) UTUtil.getPrivateField(marshaller, "_handler");
        Boolean preserveWhitespace = (Boolean) UTUtil.getPrivateField(handler, "preserveWhitespace");
        
        assertTrue(preserveWhitespace);
    }       

    /**
     * testCreateMarshaller11() <br>
     * <br>
     * 
     * (����n) <br>
     * �ϓ_�FA,D <br>
     * <br>
     * ���͒l�F<br>
     * (����) in:CastorOXMapperImpl_Stub01<br>
     * (����) writer:not null<br>
     * (����) preserveWhitespaceAtMarshal : false<br>
     * (���) �}�b�s���O��`�t�@�C��:����ȃ}�b�s���O��`�t�@�C��<br>
     * <?xml version="1.0"?><br>
     * <!DOCTYPE mapping PUBLIC "-//EXOLAB/Castor Object Mapping DTD Version
     * 1.0//EN" "http://castor.exolab.org/mapping.dtd"><br>
     * <mapping><br>
     * <class
     * name="jp.terasoluna.fw.oxm.mapper.castor.CastorOXMapperImpl_Stub01"><br>
     * <field name="param1" type="int" /><br>
     * <field name="param2" type="date" /><br>
     * <field name="param3" type="string" /><br>
     * </class><br>
     * </mapping><br>
     * (���) getCastorMapping():�Ăяo���ꂽ���Ƃ��m�F����B<br>
     * �������n���ꂽ���Ƃ��m�F����B<br>
     * (���) this.cache:true<br>
     * (���) this.mappingFileCache:���HashMap<br>
     * 
     * <br>
     * ���Ғl�F<br>
     * (��ԕω�) Marshaller �� ContentHandler �� preserveWhitespace : false<br>
     * 
     * <br>
     * preserveWhitespace �̐ݒ肪 Marshaller �� ContentHandler �ɓn����邱�Ƃ��m�F����B<br>
     * 
     * @throws Exception
     *             ���̃��\�b�h�Ŕ���������O
     */
    public void testCreateMarshaller11() throws Exception {
        // �O����
        CastorOXMapperImpl_CastorOXMapperImplStub03 oxmapper = new CastorOXMapperImpl_CastorOXMapperImplStub03();
        oxmapper.mappingPath = packagePath
                + "CastorOXMapperImpl_JavaBeanStub01" + mappingSuffix;

        // �L���b�V��
        UTUtil.setPrivateField(oxmapper, "cache", true);
        Map<Class, Mapping> map = new HashMap<Class, Mapping>();
        UTUtil.setPrivateField(oxmapper, "mappingFileCache", map);

        // ����
        CastorOXMapperImpl_JavaBeanStub01 in = new CastorOXMapperImpl_JavaBeanStub01();
        Writer writer = new StringWriter();

        UTUtil.setPrivateField(oxmapper, "preserveWhitespaceAtMarshal", false);

        // �e�X�g���{
        Marshaller marshaller = oxmapper.createMarshaller(in, writer);
        marshaller.marshal(in);

        // ����
        XMLSerializerEx handler = (XMLSerializerEx) UTUtil.getPrivateField(marshaller, "_handler");
        Boolean preserveWhitespace = (Boolean) UTUtil.getPrivateField(handler, "preserveWhitespace");
        
        assertFalse(preserveWhitespace);
    }     
    
    /**
     * testGetCastorMapping01() <br>
     * <br>
     * 
     * (����n) <br>
     * �ϓ_�FA <br>
     * <br>
     * ���͒l�F(����) mappingClass:Class�C���X�^���X<br>
     * (���) this.cache:true<br>
     * (���) this.mappingFileCache:�v�f����1��HashMap<br>
     * HashMap<br> �o<br>
     * ������Class�C���X�^���X=Mapping�C���X�^���X<br> }<br>
     * 
     * <br>
     * ���Ғl�F(�߂�l) Mapping:�L���b�V�����ꂽMapping�C���X�^���X<br>
     * (��ԕω�) this.mappingFileCache:�v�f����1��HashMap<br>
     * HashMap<br> �o<br>
     * ������Class�C���X���X=Mapping�C���X�^���X<br> }<br>
     * 
     * <br>
     * �����̃C���X�^���X���L�[�Ƃ����}�b�s���O�C���X�^���X���L���b�V���ɑ��݂���ꍇ�A�L���b�V�����ꂽ�C���X�^���X���ԋp����邱�Ƃ��m�F����B <br>
     * 
     * @throws Exception
     *             ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testGetCastorMapping01() throws Exception {
        // �O����
        CastorOXMapperImpl oxmapper = new CastorOXMapperImpl();
        UTUtil.setPrivateField(oxmapper, "cache", true);

        Class testClass = CastorOXMapperImpl_JavaBeanStub01.class;
        Mapping mapping = new Mapping();
        HashMap map = (HashMap) UTUtil.getPrivateField(oxmapper,
                "mappingFileCache");
        map.put(testClass, mapping);

        // �e�X�g���{
        Mapping result = oxmapper.getCastorMapping(testClass);

        // ����
        assertSame(mapping, result);

    }

    /**
     * testGetCastorMapping02() <br>
     * <br>
     * 
     * (����n) <br>
     * �ϓ_�FA <br>
     * <br>
     * ���͒l�F(����) mappingClass:Class�C���X�^���X<br>
     * (���) this.cache:true<br>
     * (���) this.mappingFileCache:not null<br>
     * (���) getUrl():null<br>
     * 
     * <br>
     * ���Ғl�F(�߂�l) null<br>
     * 
     * <br>
     * �����̃I�u�W�F�N�g�Ɠ����p�X�Ƀt�@�@�C�������݂��Ȃ��ꍇ�Anull���ԋp����邱�Ƃ��m�F����B <br>
     * 
     * @throws Exception
     *             ���̃��\�b�h�Ŕ���������O
     */
    public void testGetCastorMapping02() throws Exception {
        // �O����
        CastorOXMapperImpl_CastorOXMapperImplStub04 oxmapper = new CastorOXMapperImpl_CastorOXMapperImplStub04();
        UTUtil.setPrivateField(oxmapper, "cache", true);

        Class testClass = CastorOXMapperImpl_JavaBeanStub01.class;

        // �e�X�g���{
        Mapping result = oxmapper.getCastorMapping(testClass);
        assertNull(result);
    }

    /**
     * testGetCastorMapping03() <br>
     * <br>
     * 
     * (�ُ�n) <br>
     * �ϓ_�FA,G <br>
     * <br>
     * ���͒l�F(����) mappingClass:Class�C���X�^���X<br>
     * (���) this.cache:true<br>
     * (���) this.mappingFileCache:not null<br>
     * (���) getUrl():�Ăяo���ꂽ���Ƃ��m�F����B<br>
     * �������n���ꂽ���Ƃ��m�F����B<br>
     * (���) �}�b�s���O��`�t�@�C��:�s���ȃ}�b�s���O��`�t�@�C��<br>
     * <?xml version="1.0"?><br>
     * <!DOCTYPE mapping PUBLIC "-//EXOLAB/Castor Object Mapping DTD Version
     * 1.0//EN" "http://castor.exolab.org/mapping.dtd"><br>
     * <mapping><br>
     * <class name="jp.terasoluna.fw.oxm.mapper.castor.xxxDTO"><br>
     * <field name="param1" type="int" /><br>
     * <field name="param2" type="date" /><br>
     * <field name="param3" type="string" /><br>
     * </class><br>
     * </mapping<br>
     * 
     * <br>
     * ���Ғl�F(��ԕω�) ��O:MappingException<br>
     * (��ԕω�) ���O:���O���x��:error<br>
     * Nested error: XML document structures must start and end within the same
     * entity.<br>
     * 
     * <br>
     * �}�b�s���O��`�t�@�C���̌`�����s���ȏꍇ�A��O���X���[����邱�Ƃ��m�F����B<br>
     * �i�}�b�s���O��`�̌`���ɖ�肪����ƁA�p�[�X���ɔ�������SAXException�����b�v����MappingException���X���[�����j
     * <br>
     * 
     * @throws Exception
     *             ���̃��\�b�h�Ŕ���������O
     */
    public void testGetCastorMapping03() throws Exception {
        // �O����
        CastorOXMapperImpl_CastorOXMapperImplStub05 oxmapper = new CastorOXMapperImpl_CastorOXMapperImplStub05();
        UTUtil.setPrivateField(oxmapper, "cache", true);

        // getUrl���\�b�h�̌Ăяo���m�F�Ɏg�p����N���X
        Class testClass = CastorOXMapperImpl_JavaBeanStub01.class;

        // �`�����s���ȃ}�b�s���O��`�t�@�C���̃p�X
        oxmapper.path = packagePath + "CastorOXMapperImpl_JavaBeanStub03"
                + mappingSuffix;

        // �e�X�g���{
        try {
            Unmarshaller unmarshaller = new Unmarshaller(new Object());
            Mapping mapping = oxmapper.getCastorMapping(testClass);
            unmarshaller.setMapping(mapping);
            fail();
        } catch (MappingException e) {
            // ����
            assertSame(MappingException.class, e.fillInStackTrace().getClass());

            // ���O�m�F
            String message = "Nested error: XML document structures must start and end within the same entity.";
            assertTrue(e.getMessage().startsWith(message));
        }
    }

    /**
     * testGetCastorMapping04() <br>
     * <br>
     * 
     * (����n) <br>
     * �ϓ_�FA,D <br>
     * <br>
     * ���͒l�F(����) mappingClass:Class�C���X�^���X<br>
     * (���) this.cache:false<br>
     * (���) this.mappingFileCache:���HashMap<br>
     * (���) getUrl():�Ăяo���ꂽ���Ƃ��m�F����B<br>
     * �������n���ꂽ���Ƃ��m�F����B<br>
     * (���) �}�b�s���O��`�t�@�C��:����ȃ}�b�s���O��`�t�@�C��<br>
     * 
     * <br>
     * ���Ғl�F(�߂�l) Mapping:�������ꂽMapping�C���X�^���X<br>
     * (��ԕω�) this.mappingFileCache:���HashMap<br>
     * 
     * <br>
     * �}�b�s���O��`�t�@�C���̌`��������ȏꍇ�A�}�b�s���O�C���X�^���X���ԋp����邱�Ƃ��m�F����B this.cache��false�̏ꍇ�̃p�^�[���B
     * <br>
     * 
     * @throws Exception
     *             ���̃��\�b�h�Ŕ���������O
     */
    public void testGetCastorMapping04() throws Exception {
        // �O����
        CastorOXMapperImpl_CastorOXMapperImplStub05 oxmapper = new CastorOXMapperImpl_CastorOXMapperImplStub05();
        UTUtil.setPrivateField(oxmapper, "cache", false);

        // getUrl���\�b�h�̌Ăяo���m�F�Ɏg�p����N���X
        Class testClass = CastorOXMapperImpl_JavaBeanStub01.class;

        // ����ȃ}�b�s���O��`�t�@�C���̃p�X
        oxmapper.path = packagePath + "CastorOXMapperImpl_JavaBeanStub01"
                + mappingSuffix;

        // ���Map��ݒ肷��
        UTUtil.setPrivateField(oxmapper, "mappingFileCache", new HashMap());

        // �e�X�g���{
        Unmarshaller unmarshaller = new Unmarshaller(new Object());
        Mapping mapping = oxmapper.getCastorMapping(testClass);
        unmarshaller.setMapping(mapping);

        // ����
        // getUrl���\�b�h�Ɉ������n����Ă��邱�Ƃ��m�F����
        assertEquals(testClass, oxmapper.mappingClass);

        // �}�b�s���O��`�����[�h����Ă��邱�Ƃ��m�F����
        ClassMapping[] classMappingList = mapping.getRoot().getClassMapping();
        assertEquals(1, classMappingList.length);
        assertEquals(testClass.getName(), classMappingList[0].getName());

    }

    /**
     * testGetCastorMapping05() <br>
     * <br>
     * 
     * (����n) <br>
     * �ϓ_�FA,D <br>
     * <br>
     * ���͒l�F(����) mappingClass:Class�C���X�^���X<br>
     * (���) this.cache:true<br>
     * (���) this.mappingFileCache:���HashMap<br>
     * (���) getUrl():�Ăяo���ꂽ���Ƃ��m�F����B<br>
     * �������n���ꂽ���Ƃ��m�F����B<br>
     * (���) �}�b�s���O��`�t�@�C��:����ȃ}�b�s���O��`�t�@�C��<br>
     * 
     * <br>
     * ���Ғl�F(�߂�l) Mapping:�������ꂽMapping�C���X�^���X<br>
     * (��ԕω�) this.mappingFileCache:�v�f����1��HashMap<br>
     * HashMap<br> �o<br>
     * ������Class�C���X���X=�������ꂽMapping�C���X�^���X<br> }<br>
     * 
     * <br>
     * �}�b�s���O��`�t�@�C���̌`��������ȏꍇ�A�}�b�s���O�C���X�^���X���ԋp����邱�Ƃ��m�F����B<br>
     * this.cache��true�Ń}�b�s���O�C���X�^���X���L���b�V���ɑ��݂��Ȃ��p�^�[���B <br>
     * 
     * @throws Exception
     *             ���̃��\�b�h�Ŕ���������O
     */
    public void testGetCastorMapping05() throws Exception {
        // �O����
        CastorOXMapperImpl_CastorOXMapperImplStub05 oxmapper = new CastorOXMapperImpl_CastorOXMapperImplStub05();
        UTUtil.setPrivateField(oxmapper, "cache", true);

        // getUrl���\�b�h�̌Ăяo���m�F�Ɏg�p����N���X
        Class testClass = CastorOXMapperImpl_JavaBeanStub01.class;

        // ����ȃ}�b�s���O��`�t�@�C���̃p�X
        oxmapper.path = packagePath + "CastorOXMapperImpl_JavaBeanStub01"
                + mappingSuffix;

        // �L���b�V������ɂ���
        UTUtil.setPrivateField(oxmapper, "mappingFileCache", new HashMap());

        // �e�X�g���{
        Unmarshaller unmarshaller = new Unmarshaller(new Object());
        Mapping mapping = oxmapper.getCastorMapping(testClass);
        unmarshaller.setMapping(mapping);

        // ����
        // getUrl���\�b�h�Ɉ������n����Ă��邱�Ƃ��m�F����
        assertEquals(testClass, oxmapper.mappingClass);

        // �}�b�s���O��`�����[�h����Ă��邱�Ƃ��m�F����
        ClassMapping[] classMappingList = mapping.getRoot().getClassMapping();
        assertEquals(1, classMappingList.length);
        assertEquals(testClass.getName(), classMappingList[0].getName());

    }

    /**
     * testGetUrl01() <br>
     * <br>
     * 
     * (����n) <br>
     * �ϓ_�FA <br>
     * <br>
     * ���͒l�F(����) mappingClass:not null<br>
     * �i�Ή�����}�b�s���O��`�t�@�C�������݂���N���X�j<br>
     * 
     * <br>
     * ���Ғl�F(�߂�l) URL:URL�C���X�^���X<br>
     * 
     * <br>
     * �����̃N���X�ɑΉ�����p�X�imappingClass�̃p�b�P�[�W�p�X + mappingClass�̃N���X�� +
     * ".xml"�j�Ƀ}�b�s���O��`�t�@�C�������݂���ꍇ�A URL�C���X�^���X��ԋp���邱�Ƃ��m�F����B <br>
     * 
     * @throws Exception
     *             ���̃��\�b�h�Ŕ���������O
     */
    public void testGetUrl01() throws Exception {
        // �O����
        CastorOXMapperImpl oxmapper = new CastorOXMapperImpl();

        // �Ή�����}�b�s���O��`�t�@�C�������݂���N���X
        Class mappingClass = CastorOXMapperImpl_JavaBeanStub01.class;

        // �e�X�g���{
        URL result = oxmapper.getUrl(mappingClass);

        // ����
        // �������ꂽURL�C���X�^���X��path���܂�ł��邱�Ƃ��m�F����
        String mappingPath = packagePath + mappingClass.getSimpleName()
                + mappingSuffix;
        assertTrue(result.getPath().contains(mappingPath));
    }

    /**
     * testGetUrl02() <br>
     * <br>
     * 
     * (����n) <br>
     * �ϓ_�FA <br>
     * <br>
     * ���͒l�F(����) mappingClass:not null<br>
     * �i�Ή�����}�b�s���O��`�t�@�C�������݂���N���X�j<br>
     * 
     * <br>
     * ���Ғl�F(�߂�l) URL:null<br>
     * 
     * <br>
     * �����̃N���X�ɑΉ�����p�X�imappingClass�̃p�b�P�[�W�p�X + mappingClass�̃N���X�� +
     * ".xml"�j�Ƀ}�b�s���O��`�t�@�C�������݂��Ȃ��ꍇ�A null��ԋp���邱�Ƃ��m�F����B <br>
     * 
     * @throws Exception
     *             ���̃��\�b�h�Ŕ���������O
     */
    public void testGetUrl02() throws Exception {
        // �O����
        CastorOXMapperImpl oxmapper = new CastorOXMapperImpl();

        // �Ή�����}�b�s���O��`�t�@�C�������݂��Ȃ��N���X
        Class mappingClass = CastorOXMapperImpl.class;

        // �e�X�g���{
        URL result = oxmapper.getUrl(mappingClass);

        // ����
        assertNull(result);
    }

    /**
     * testIsCache01() <br>
     * <br>
     * 
     * (����n) <br>
     * �ϓ_�FA <br>
     * <br>
     * ���͒l�F(���) this.cache:true<br>
     * 
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     * 
     * <br>
     * �������������ԋp����邱�Ƃ��m�F����B <br>
     * 
     * @throws Exception
     *             ���̃��\�b�h�Ŕ���������O
     */
    public void testIsCache01() throws Exception {
        // �O����
        CastorOXMapperImpl oxmapper = new CastorOXMapperImpl();
        UTUtil.setPrivateField(oxmapper, "cache", false);

        // �e�X�g���{
        boolean result = oxmapper.isCache();

        // ����
        assertFalse(result);
    }

    /**
     * testSetCache01() <br>
     * <br>
     * 
     * (����n) <br>
     * �ϓ_�FA <br>
     * <br>
     * ���͒l�F(����) cache:false<br>
     * (���) this.cache:true<br>
     * 
     * <br>
     * ���Ғl�F(��ԕω�) this.cache:false<br>
     * 
     * <br>
     * �����̒l�������ɐ������ݒ肳��邱�Ƃ��m�F����B <br>
     * 
     * @throws Exception
     *             ���̃��\�b�h�Ŕ���������O
     */
    public void testSetCache01() throws Exception {
        // �O����
        CastorOXMapperImpl oxmapper = new CastorOXMapperImpl();
        UTUtil.setPrivateField(oxmapper, "cache", true);

        // �e�X�g���{
        oxmapper.setCache(false);

        // ����
        assertFalse(((Boolean) UTUtil.getPrivateField(oxmapper, "cache"))
                .booleanValue());
    }

    /**
     * testGetCharset01() <br>
     * <br>
     * 
     * (����n) <br>
     * �ϓ_�FA <br>
     * <br>
     * ���͒l�F(���) this.charset:"EUC"<br>
     * 
     * <br>
     * ���Ғl�F(�߂�l) charset:"EUC"<br>
     * 
     * <br>
     * �����̒l���������ԋp����邱�Ƃ��m�F����B <br>
     * 
     * @throws Exception
     *             ���̃��\�b�h�Ŕ���������O
     */
    public void testGetCharset01() throws Exception {
        // �O����
        CastorOXMapperImpl oxmapper = new CastorOXMapperImpl();
        UTUtil.setPrivateField(oxmapper, "charset", "EUC");

        // �e�X�g���{
        String result = oxmapper.getCharset();

        // ����
        assertEquals("EUC", result);

    }

    /**
     * testSetCharset01() <br>
     * <br>
     * 
     * (����n) <br>
     * �ϓ_�FA <br>
     * <br>
     * ���͒l�F(����) charset:"EUC"<br>
     * (���) this.charset:"UTF-8"<br>
     * 
     * <br>
     * ���Ғl�F(��ԕω�) this.charset:"EUC"<br>
     * 
     * <br>
     * �����̒l�������ɐ������ݒ肳��邱�Ƃ��m�F����B <br>
     * 
     * @throws Exception
     *             ���̃��\�b�h�Ŕ���������O
     */
    public void testSetCharset01() throws Exception {
        // �O����
        CastorOXMapperImpl oxmapper = new CastorOXMapperImpl();
        UTUtil.setPrivateField(oxmapper, "charset", "UTF-8");

        // �e�X�g���{
        oxmapper.setCharset("EUC");

        // ����
        assertEquals("EUC", UTUtil.getPrivateField(oxmapper, "charset"));
    }

    /**
     * testSetSuppressXSIType01() <br>
     * <br>
     * 
     * (����n) <br>
     * �ϓ_�FA <br>
     * <br>
     * ���͒l�F(����) suppressXSIType:true<br>
     * (���) this.suppressXSIType:false<br>
     * 
     * <br>
     * ���Ғl�F(��ԕω�) this.suppressXSIType:true<br>
     * 
     * <br>
     * �����̒l�������ɐ������ݒ肳��邱�Ƃ��m�F����B <br>
     * 
     * @throws Exception
     *             ���̃��\�b�h�Ŕ���������O
     */
    public void testSetSuppressXSIType01() throws Exception {
        // �O����
        CastorOXMapperImpl oxmapper = new CastorOXMapperImpl();
        UTUtil.setPrivateField(oxmapper, "suppressXSIType", false);

        // �e�X�g���{
        oxmapper.setSuppressXSIType(true);

        // ����
        assertEquals(true, UTUtil.getPrivateField(oxmapper, "suppressXSIType"));
    }

    /**
     * testSetPreserveWhitespaceAtUnmarshal01() <br>
     * <br>
     * 
     * (����n) <br>
     * �ϓ_�FA <br>
     * <br>
     * ���͒l�F(����) whitespacePreserve:true<br>
     * (���) this.whitespacePreserve:false<br>
     * 
     * <br>
     * ���Ғl�F(��ԕω�) this.whitespacePreserve:true<br>
     * 
     * <br>
     * �����̒l�������ɐ������ݒ肳��邱�Ƃ��m�F����B <br>
     * 
     * @throws Exception
     *             ���̃��\�b�h�Ŕ���������O
     */
    public void testSetPreserveWhitespaceAtUnmarshal01() throws Exception {
        // �O����
        CastorOXMapperImpl oxmapper = new CastorOXMapperImpl();
        UTUtil
                .setPrivateField(oxmapper, "preserveWhitespaceAtUnmarshal",
                        false);

        // �e�X�g���{
        oxmapper.setPreserveWhitespaceAtUnmarshal(true);

        // ����
        assertEquals(true, UTUtil.getPrivateField(oxmapper,
                "preserveWhitespaceAtUnmarshal"));
    }

    /**
     * testSetPreserveWhitespaceAtMarshal01() <br>
     * <br>
     * 
     * (����n) <br>
     * �ϓ_�FA <br>
     * <br>
     * ���͒l�F(����) whitespacePreserve:true<br>
     * (���) this.whitespacePreserve:false<br>
     * 
     * <br>
     * ���Ғl�F(��ԕω�) this.whitespacePreserve:true<br>
     * 
     * <br>
     * �����̒l�������ɐ������ݒ肳��邱�Ƃ��m�F����B <br>
     * 
     * @throws Exception
     *             ���̃��\�b�h�Ŕ���������O
     */
    public void testSetPreserveWhitespaceAtMarshal01() throws Exception {
        // �O����
        CastorOXMapperImpl oxmapper = new CastorOXMapperImpl();
        UTUtil.setPrivateField(oxmapper, "preserveWhitespaceAtMarshal", false);

        // �e�X�g���{
        oxmapper.setPreserveWhitespaceAtMarshal(true);

        // ����
        assertEquals(true, UTUtil.getPrivateField(oxmapper,
                "preserveWhitespaceAtMarshal"));
    }
    
    /**
     * testSetIndenting01() <br>
     * <br>
     * 
     * (����n) <br>
     * �ϓ_�FA <br>
     * <br>
     * ���͒l�F(����) indenting:false<br>
     * (���) this.indenting:true<br>
     * 
     * <br>
     * ���Ғl�F(��ԕω�) this.indenting:false<br>
     * 
     * <br>
     * �����̒l�������ɐ������ݒ肳��邱�Ƃ��m�F����B <br>
     * 
     * @throws Exception
     *             ���̃��\�b�h�Ŕ���������O
     */
    public void testSetIndenting01() throws Exception {
        // �O����
        CastorOXMapperImpl oxmapper = new CastorOXMapperImpl();
        UTUtil.setPrivateField(oxmapper, "indenting", true);

        // �e�X�g���{
        oxmapper.setIndenting(false);

        // ����
        assertEquals(false, UTUtil.getPrivateField(oxmapper, "indenting"));
    }

}
