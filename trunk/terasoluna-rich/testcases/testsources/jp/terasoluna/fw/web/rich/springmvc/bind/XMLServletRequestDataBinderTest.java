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

package jp.terasoluna.fw.web.rich.springmvc.bind;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletInputStream;
import jp.terasoluna.fw.oxm.mapper.OXMapper;
import jp.terasoluna.fw.oxm.mapper.castor.CastorOXMapperImpl;
import jp.terasoluna.fw.oxm.xsd.SchemaValidator;
import jp.terasoluna.fw.oxm.xsd.message.ErrorMessages;
import jp.terasoluna.fw.oxm.xsd.xerces.SchemaValidatorImpl;
import jp.terasoluna.utlib.LogUTUtil;
import jp.terasoluna.utlib.MockHttpServletRequest;
import jp.terasoluna.utlib.UTUtil;
import jp.terasoluna.fw.web.rich.springmvc.bind.XMLServletRequestDataBinder;
import jp.terasoluna.fw.web.rich.springmvc.bind.creator.exception.XMLRequestIOException;
import junit.framework.TestCase;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.w3c.dom.Document;
import com.sun.org.apache.xerces.internal.dom.DocumentImpl;

/**
 * {@link jp.terasoluna.fw.web.rich.springmvc.bind.XMLServletRequestDataBinder}
 * �N���X�̃u���b�N�{�b�N�X�e�X�g�B
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4>
 * XML�`���̃��N�G�X�g�f�[�^���R�}���h�I�u�W�F�N�g�Ƀo�C���h����N���X�B<br>
 * �O������Fcreator���琶������邽�߁A�R���X�g���N�^�̈���target,oxmapper��null�̏�Ԃ͑��݂��Ȃ��B
 * <p>
 * 
 * @see jp.terasoluna.fw.web.rich.springmvc.bind.XMLServletRequestDataBinder
 */
public class XMLServletRequestDataBinderTest extends TestCase {

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
    public XMLServletRequestDataBinderTest(String name) {
        super(name);
    }

    /**
     * testXMLServletRequestDataBinder01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) target:not null<br>
     *         (����) objectName:not null<br>
     *         (����) oxmapper:not null<br>
     *         (����) schemaValidator:not null<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) this.bindingResult.target:not null<br>
     *         (��ԕω�) this.oxmapper:not null<br>
     *         (��ԕω�) this.schemaValidator:not null<br>
     *         
     * <br>
     * �����̒l������ɑ����ɐݒ肳��邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testXMLServletRequestDataBinder01() throws Exception {
        // �O����
        OXMapper oxmapper = new CastorOXMapperImpl();
        SchemaValidator schemaValidator = new SchemaValidatorImpl();
        Object target = new Object();
        String objectName = "";

        // �e�X�g���{
        XMLServletRequestDataBinder servletDataBinder = new XMLServletRequestDataBinder(
                target, oxmapper, schemaValidator, objectName);

        // ����        
        // �����������ɐݒ肳�ꂽ���Ƃ̊m�F
        assertSame(oxmapper, UTUtil.getPrivateField(
                servletDataBinder, "oxmapper"));
        assertSame(schemaValidator, UTUtil.getPrivateField(
                servletDataBinder, "schemaValidator"));
        // �Ăяo���m�F
        BindingResult errors = servletDataBinder.getBindingResult();
        assertSame(target, errors.getTarget());
    }

    /**
     * testXMLServletRequestDataBinder02()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA,C
     * <br><br>
     * ���͒l�F(����) target:not null<br>
     *         (����) oxmapper:not null<br>
     *         (����) schemaValidator:null<br>
     *         (����) objectName:not null<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) this.bindingResult.target:not null<br>
     *         (��ԕω�) this.oxmapper:not null<br>
     *         (��ԕω�) this.schemaValidator:null<br>
     *         
     * <br>
     * �����̒l������ɑ����ɐݒ肳��邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testXMLServletRequestDataBinder02() throws Exception {
        // �O����
        OXMapper oxmapper = new CastorOXMapperImpl();
        SchemaValidator schemaValidator = null;
        Object target = new Object();
        String objectName = "target";

        // �e�X�g���{
        XMLServletRequestDataBinder servletDataBinder = new XMLServletRequestDataBinder(
                target, oxmapper, schemaValidator, objectName);

        // ����
        // �����������ɐݒ肳�ꂽ���Ƃ̊m�F
        assertSame(oxmapper, UTUtil.getPrivateField(servletDataBinder, "oxmapper"));
        assertNull(UTUtil.getPrivateField(servletDataBinder, "schemaValidator"));
        // �Ăяo���m�F
        BindingResult errors = servletDataBinder.getBindingResult();
        assertSame(target, errors.getTarget());
    }

    /**
     * testBind01()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FA,G
     * <br><br>
     * ���͒l�F(����) request:�s���ȓ��̓X�g���[��<br>
     *         (���) this.oxmapper:not null<br>
     *         (���) this.schemaValidator:not null<br>
     *         (���) this.bindingResult.target:not null<br>
     *         (���) this.bindingResult.errors:�v�f��0��errors<br>
     *         (���) request.servletInputStream:not null<br>
     *         (���) request.getInputStream():IOException���X���[����<br>
     *         (���) request.env:not null<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) ��O:XMLRequestIOException{<br>
     *                      cause = IOException<br>
     *                    }<br>
     *         (��ԕω�) ���O:���O���x���Ferror<br>
     *                    Request stream is Invalid.<br>
     *         
     * <br>
     * request.getInputStream()���\�b�h�̎��s����IO��O�����������ꍇ�AXMLRequestIOException���X���[����A���O���o�͂���邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testBind01() throws Exception {
        // �O����
        OXMapper oxmapper = null;
        SchemaValidator schemaValidator = null;
        Object target = new Object();
        String objectName = "target";
        
        XMLServletRequestDataBinder servletDataBinder = new XMLServletRequestDataBinder(
                target, oxmapper, schemaValidator, objectName);

        XMLServletRequestDataBinder_MockHttpServletRequestStub01 request = new XMLServletRequestDataBinder_MockHttpServletRequestStub01();

        try {
            // �e�X�g���{
            servletDataBinder.bind(request);
            fail();
        } catch (XMLRequestIOException e) {
            // ����
            assertSame(IOException.class, e.getCause().getClass());

            // ���O�m�F
            String message = "Request stream error.";
            assertTrue(LogUTUtil.checkError(message, e.getCause()));
            
        }
    }

    /**
     * testBind02()
     * <br><br>
     * 
     * (����n) 
     * <br>
     * �ϓ_�FA,C
     * <br><br>
     * ���͒l�F(����) request:not null<br>
     *         (���) this.oxmapper:not null<br>
     *         (���) this.schemaValidator:null<br>
     *         (���) this.bindingResult.target:not null<br>
     *         (���) this.bindingResult.errors:�v�f��0��errors<br>
     *         (���) request.servletInputStream:null<br>
     *         (���) request.env:not null<br>
     *         (���) oxmapper.unmarshal(InputStream, String, Object):�Ăяo���ꂽ���Ƃ̊m�F���s���B<br>
     *                �������n���ꂽ���Ƃ̊m�F���s���B<br>
     *         
     * <br>
     * ���Ғl�F
     * <br>
     * ������schemaValidator��null�̏ꍇ�Aunmarshal(InputStream, String, Object)���\�b�h�����s����邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testBind02() throws Exception {
        // �O����
        XMLServletRequestDataBinder_OXMapperStub01 oxmapper = new XMLServletRequestDataBinder_OXMapperStub01();
        SchemaValidator schemaValidator = null;
        Object out = new Object();
        String objectName = "target";
        
        XMLServletRequestDataBinder servletDataBinder = new XMLServletRequestDataBinder(
                out, oxmapper, schemaValidator, objectName);

        MockHttpServletRequest request = new MockHttpServletRequest();
        ServletInputStream inputStream = null;
        request.setInputStream(inputStream);
        request.setCharacterEncoding("abc");

        // �e�X�g���{
        servletDataBinder.bind(request);

        // ����
        // �Ăяo���m�F
        assertNull(oxmapper.is);
        assertEquals("abc", oxmapper.argCharset);
        assertSame(out, oxmapper.out);

    }

    /**
     * testBind03()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FA,G
     * <br><br>
     * ���͒l�F(����) request:not null<br>
     *         (���) this.oxmapper:not null<br>
     *         (���) this.schemaValidator:not null<br>
     *         (���) this.bindingResult.target:not null<br>
     *         (���) this.bindingResult.errors:�v�f��0��errors<br>
     *         (���) request.servletInputStream:not null<br>
     *         (���) request.env:not null<br>
     *         (���) validate():�Ăяo���ꂽ���Ƃ̊m�F���s���B<br>
     *                �������n���ꂽ���Ƃ̊m�F���s���B<br>
     *         (���) in.close():IOException���X���[����<br>
     *         (���) oxmapper.unmarshal(Document, Object):�Ăяo���ꂽ���Ƃ̊m�F���s���B<br>
     *                �������n���ꂽ���Ƃ̊m�F���s���B<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) ���O:���O���x���Ferror<br>
     *                    "Failed to close request stream.", IOException<br>
     *         
     * <br>
     * ������schemaValidator��not null�̏ꍇ�Avalidate���\�b�h�����s����Aunmarshal(Document, Object)���\�b�h�����s����邱�Ƃ��m�F����B<br>
     * �X�g���[���̃N���[�Y�Ɏ��s�����ꍇ�A���O���o�͂���A���������s����邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testBind03() throws Exception {
        // �O����
        XMLServletRequestDataBinder_OXMapperStub01 oxmapper = new XMLServletRequestDataBinder_OXMapperStub01();
        XMLServletRequestDataBinder_SchemaValidatorStub01 schemaValidator = new XMLServletRequestDataBinder_SchemaValidatorStub01();
        Object out = new Object();
        String objectName = "target";
        
        XMLServletRequestDataBinder_XMLServletRequestDataBinderStub01 servletDataBinder = new XMLServletRequestDataBinder_XMLServletRequestDataBinderStub01(
                out, oxmapper, schemaValidator, objectName);

        MockHttpServletRequest request = new MockHttpServletRequest();
        XMLServletRequestDataBinder_ServletInputStreamStub01 inputStream = new XMLServletRequestDataBinder_ServletInputStreamStub01();
        request.setInputStream(inputStream);
        request.setCharacterEncoding("abc");

        Document doc = new DocumentImpl();
        servletDataBinder.doc = doc;

        // �e�X�g���{
        servletDataBinder.bind(request);

        // ����
        // �Ăяo���m�F
        assertSame(inputStream, servletDataBinder.in);
        assertSame(doc, oxmapper.doc);
        assertSame(out, oxmapper.out);

        // ���O�m�F
        String message = "Failed to close request stream.";
        assertTrue(LogUTUtil.checkError(message, new IOException()));
    }

    /**
     * testBind04()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA,C
     * <br><br>
     * ���͒l�F(����) request:not null<br>
     *         (���) this.oxmapper:not null<br>
     *         (���) this.schemaValidator:not null<br>
     *         (���) this.bindingResult.target:not null<br>
     *         (���) this.bindingResult.errors:�v�f��1��errors<br>
     *         (���) request.servletInputStream:not null<br>
     *         (���) request.env:not null<br>
     *         (���) validate():�Ăяo���ꂽ���Ƃ̊m�F���s���B<br>
     *                �������n���ꂽ���Ƃ̊m�F���s���B<br>
     *         (���) in.close():�Ăяo���ꂽ���Ƃ̊m�F���s���B<br>
     *         
     * <br>
     * ���Ғl�F
     * <br>
     * ������schemaValidator��not null�ABindException�ɃG���[���i�[����Ă���ꍇ�Avalidate���\�b�h�����s����邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testBind04() throws Exception {
        // �O����
        XMLServletRequestDataBinder_OXMapperStub01 oxmapper = new XMLServletRequestDataBinder_OXMapperStub01();
        SchemaValidator schemaValidator = new SchemaValidatorImpl();
        Object out = new Object();
        String objectName = "target";

        XMLServletRequestDataBinder_XMLServletRequestDataBinderStub01 servletDataBinder = new XMLServletRequestDataBinder_XMLServletRequestDataBinderStub01(
                out, oxmapper, schemaValidator, objectName);
        FieldError error = new FieldError("", "", null, false, new String[] {},
                new Object[] {}, "");
        BindingResult errors = servletDataBinder.getBindingResult();
        errors.addError(error);

        MockHttpServletRequest request = new MockHttpServletRequest();
        XMLServletRequestDataBinder_ServletInputStreamStub02 inputStream = new XMLServletRequestDataBinder_ServletInputStreamStub02();
        request.setInputStream(inputStream);
        request.setCharacterEncoding("abc");

        Document doc = new DocumentImpl();
        servletDataBinder.doc = doc;

        // �e�X�g���{
        servletDataBinder.bind(request);

        // ����
        // �Ăяo���m�F
        assertSame(inputStream, servletDataBinder.in);
        assertTrue(inputStream.read);

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
     *         (���) this.schemaValidator:not null<br>
     *         (���) this.bindingResult.target:not null<br>
     *         (���) this.bindingResult.errors:�v�f��0��errors<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalArgumentException{<br>
     *                      message = InputStream is null.<br>
     *                    }<br>
     *         (��ԕω�) ���O:���O���x���Ferror<br>
     *                    InputStream is null.<br>
     *         
     * <br>
     * ����in��null�̏ꍇ�AIllegalArgumentException���X���[����A���O���o�͂���邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidate01() throws Exception {
        // �O����
        CastorOXMapperImpl oxmapper = new CastorOXMapperImpl();
        SchemaValidator schemaValidator = new SchemaValidatorImpl();
        Object out = new Object();
        String objectName = "target";

        XMLServletRequestDataBinder servletDataBinder = new XMLServletRequestDataBinder(
                out, oxmapper, schemaValidator, objectName);

        ServletInputStream inputStream = null;

        // �e�X�g���{
        try {
            servletDataBinder.validate(inputStream);
            fail();
        } catch (IllegalArgumentException e) {
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
     * (����n)
     * <br>
     * �ϓ_�FA,D
     * <br><br>
     * ���͒l�F(����) in:not null<br>
     *         (���) this.schemaValidator:not null<br>
     *         (���) this.bindingResult.target:not null<br>
     *         (���) this.bindingResult.errors:�v�f��0��errors<br>
     *         (���) schemaValidator.validate():�Ăяo���ꂽ���Ƃ̊m�F���s���B<br>
     *                �������n���ꂽ���Ƃ̊m�F���s���B<br>
     *                <br>
     *                ErrorMassages�ɃG���[���i�[���Ȃ��B<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) -:Document�C���X�^���X<br>
     *         (��ԕω�) this.bindingResult.errors:�v�f��0��errors<br>
     *         
     * <br>
     * �`���`�F�b�N�ŃG���[��������Ȃ������ꍇ�Athis.bindingResult.error�ɃG���[���i�[����Ă��Ȃ����Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidate02() throws Exception {
        // �O����
        CastorOXMapperImpl oxmapper = new CastorOXMapperImpl();
        XMLServletRequestDataBinder_SchemaValidatorStub01 schemaValidator = new XMLServletRequestDataBinder_SchemaValidatorStub01();
        Document doc = new DocumentImpl();
        schemaValidator.doc = doc;        
        Object target = new Object();
        String objectName = "target";

        XMLServletRequestDataBinder servletDataBinder = new XMLServletRequestDataBinder(
                target, oxmapper, schemaValidator, objectName);
        ServletInputStream inputStream = new XMLServletRequestDataBinder_ServletInputStreamStub02();

        // �e�X�g���{
        Document result = servletDataBinder.validate(inputStream);
        
        // ����
        // �߂�l
        assertSame(doc, result);
        
        // �Ăяo���m�F
        assertSame(inputStream, schemaValidator.in);
        assertSame(target, schemaValidator.object);
        assertSame(ErrorMessages.class, schemaValidator.errorMessages.getClass());
        
        // ��ԕω�
        BindingResult errors = servletDataBinder.getBindingResult();
        assertEquals(0, errors.getErrorCount());
        
    }

    /**
     * testValidate03()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA,D
     * <br><br>
     * ���͒l�F(����) in:not null<br>
     *         (���) this.schemaValidator:not null<br>
     *         (���) this.bindingResult.target:not null<br>
     *         (���) this.bindingResult.errors:�v�f��0��errors<br>
     *         (���) schemaValidator.validate():�Ăяo���ꂽ���Ƃ̊m�F���s���B<br>
     *                �������n���ꂽ���Ƃ̊m�F���s���B<br>
     *                <br>
     *                ErrorMassages�ɂP�̃G���[���i�[����B<br>
     *                ErrorMessage{<br>
     *                  field = "abc",<br>
     *                  key = "123",<br>
     *                  replaceValues[]{"a"}<br>
     *                }<br>
     *         (���) createReplaceValues():1�x�Ăяo���ꂽ���Ƃ̊m�F���s���B<br>
     *                �������n���ꂽ���Ƃ̊m�F���s���B<br>
     *                String[]{"jkl","j"}��ԋp����<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) -:Document�C���X�^���X<br>
     *         (��ԕω�) this.bindingResult.errors:�v�f��1��errors<br>
     *                    FieldError{<br>
     *                      objectName = not null,<br>
     *                      field = "abc",<br>
     *                      rejectedValue = null,<br>
     *                      bindingFailure = false,<br>
     *                      codes[] = {"abc.null.123", "abc.123", "abc"},<br>
     *                      arguments[] = {"abc","a"},<br>
     *                      defaultMessage = null<br>
     *                    }<br>
     *         
     * <br>
     * �`���`�F�b�N�ŃG���[���P���������ꍇ�Athis.bindingResult.error��FieldError�C���X�^���X���P�i�[����邱�Ƃ��m�F����B<br>
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidate03() throws Exception {
        // �O����
        CastorOXMapperImpl oxmapper = new CastorOXMapperImpl();
        XMLServletRequestDataBinder_SchemaValidatorStub02 schemaValidator = new XMLServletRequestDataBinder_SchemaValidatorStub02();
        Document doc = new DocumentImpl();
        schemaValidator.doc = doc;
        
        Object target = new Object();
        String objectName = "target";
        
        //DataBinder d = new DataBinder(target,"");

        XMLServletRequestDataBinder_XMLServletRequestDataBinderStub02 servletDataBinder = new XMLServletRequestDataBinder_XMLServletRequestDataBinderStub02(
                target, oxmapper, schemaValidator, objectName);
        
        ServletInputStream inputStream = new XMLServletRequestDataBinder_ServletInputStreamStub02();
                        
        // �e�X�g���{
         Document result = servletDataBinder.validate(inputStream);
        
        // ����
        // �߂�l
        assertSame(doc, result);
        
        // schemaValidator.validate���\�b�h�̌Ăяo���m�F
        assertSame(inputStream, schemaValidator.in);
        assertSame(target, schemaValidator.object);
        assertSame(ErrorMessages.class, schemaValidator.errorMessages.getClass());
        
        // createReplaceValues���\�b�h�̌Ăяo���m�F
        assertEquals(1, servletDataBinder.count);
        assertEquals("abc", servletDataBinder.field);
        assertEquals("a", servletDataBinder.replaceValues[0]);
        assertEquals(1, servletDataBinder.replaceValues.length);
        
        // �������ꂽFieldError�̊m�F
        BindingResult errors = servletDataBinder.getBindingResult();
        List errorResults = errors.getAllErrors();
        assertEquals(1, errorResults.size());
        FieldError errorResult = null;
        errorResult = (FieldError) errorResults.get(0);
        assertNotNull(errorResult.getObjectName());
        assertEquals("abc", errorResult.getField());
        assertNull(errorResult.getRejectedValue());
        assertFalse(errorResult.isBindingFailure());
        assertEquals("123", errorResult.getCode());
        assertEquals(3, errorResult.getCodes().length);
        assertEquals("jkl", errorResult.getArguments()[0]);
        assertEquals("j", errorResult.getArguments()[1]);       
        assertEquals(2, errorResult.getArguments().length);
        assertNull(errorResult.getDefaultMessage());
        
    }

    /**
     * testValidate04()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA,D
     * <br><br>
     * ���͒l�F(����) in:not null<br>
     *         (���) this.schemaValidator:not null<br>
     *         (���) this.bindingResult.target:not null<br>
     *         (���) this.bindingResult.errors:�v�f��0��errors<br>
     *         (���) schemaValidator.validate():�Ăяo���ꂽ���Ƃ̊m�F���s���B<br>
     *                �������n���ꂽ���Ƃ̊m�F���s���B<br>
     *                <br>
     *                ErrorMassages�ɂR�̃G���[���i�[����B<br>
     *                ErrorMessage{<br>
     *                {<br>
     *                  field = "abc",<br>
     *                  key = "123",<br>
     *                  replaceValues[]{"a"}<br>
     *                  }<br>
     *                },<br>
     *                ErrorMessage<br>
     *                {<br>
     *                  field = "def",<br>
     *                  key = "456",<br>
     *                  replaceValues[]{"b"}<br>
     *                  }<br>
     *                },<br>
     *                ErrorMessage<br>
     *                {<br>
     *                  field = "ghi",<br>
     *                  key = "789",<br>
     *                  replaceValues[]{"c"}<br>
     *                }<br>
     *         (���) createReplaceValues():3�x�Ăяo���ꂽ���Ƃ̊m�F���s���B<br>
     *                �������n���ꂽ���Ƃ̊m�F���s���B<br>
     *                <br>
     *                �P��ڂɌĂяo���ꂽ�Ƃ��AString[]{"jkl","j"}��ԋp����<br>
     *                �Q��ڂɌĂяo���ꂽ�Ƃ��AString[]{"mno","m"}��ԋp����<br>
     *                �R��ڂɌĂяo���ꂽ�Ƃ��AString[]{"pqr","p"}��ԋp����<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) -:Document�C���X�^���X<br>
     *         (��ԕω�) this.bindingResult.errors:�v�f��3��errors<br>
     *                    FieldError<br>
     *                    {<br>
     *                      objectName = not null,<br>
     *                      field = "abc",<br>
     *                      rejectedValue = null,<br>
     *                      bindingFailure = false,<br>
     *                      codes[] = {"123"},<br>
     *                      arguments[] = {"jkl","j"},<br>
     *                      defaultMessage = null<br>
     *                    },<br>
     *                    FieldError<br>
     *                    {<br>
     *                      objectName = not null,<br>
     *                      field = "def",<br>
     *                      rejectedVal = null,<br>
     *                      bindingFailure = false,<br>
     *                      codes[] = {"456"},<br>
     *                      arguments[] = {"mno","k"},<br>
     *                      defaultMessage = null<br>
     *                    },<br>
     *                    FieldError<br>
     *                    {<br>
     *                      objectName = not null,<br>
     *                      field = "ghi",<br>
     *                      rejectedVal = null,<br>
     *                      bindingFailure = false,<br>
     *                      codes[] = {"789"},<br>
     *                      arguments[] = {"pqr","l"},<br>
     *                      defaultMessage = null<br>
     *                    }<br>
     *         
     * <br>
     * �`���`�F�b�N�ŃG���[���R���������ꍇ�Athis.bindingResult.error��FieldError�C���X�^���X���R�i�[����邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidate04() throws Exception {
        // �O����
        CastorOXMapperImpl oxmapper = new CastorOXMapperImpl();
        XMLServletRequestDataBinder_SchemaValidatorStub03 schemaValidator = new XMLServletRequestDataBinder_SchemaValidatorStub03();
        Document doc = new DocumentImpl();
        schemaValidator.doc = doc;
        String objectName = "target";
        
        Object target = new Object();

        XMLServletRequestDataBinder_XMLServletRequestDataBinderStub03 servletDataBinder = new XMLServletRequestDataBinder_XMLServletRequestDataBinderStub03(
                target, oxmapper, schemaValidator, objectName);
        ServletInputStream inputStream = new XMLServletRequestDataBinder_ServletInputStreamStub02();

        // �e�X�g���{
        Document result = servletDataBinder.validate(inputStream);
        
        // ����
        // �߂�l
        assertSame(doc, result);
        
        // schemaValidator.validate���\�b�h�̌Ăяo���m�F
        assertSame(inputStream, schemaValidator.in);
        assertSame(target, schemaValidator.object);
        assertSame(ErrorMessages.class, schemaValidator.errorMessages.getClass());
        
        // createReplaceValues���\�b�h�̌Ăяo���m�F        
        assertEquals(3, servletDataBinder.count);        
        assertEquals("abc", servletDataBinder.field01);
        assertEquals("a", servletDataBinder.replaceValues01[0]);
        assertEquals(1, servletDataBinder.replaceValues01.length);
        assertEquals("def", servletDataBinder.field02);
        assertEquals("b", servletDataBinder.replaceValues02[0]);
        assertEquals(1, servletDataBinder.replaceValues02.length);
        assertEquals("ghi", servletDataBinder.field03);
        assertEquals("c", servletDataBinder.replaceValues03[0]);
        assertEquals(1, servletDataBinder.replaceValues03.length);
        
        // �������ꂽFieldError�̊m�F
        BindingResult errors = servletDataBinder.getBindingResult();
        List errorResults = errors.getAllErrors();
        assertEquals(3, errorResults.size());
        FieldError errorResult = null;
        
        // 1�ڂ�FieldError
        errorResult = (FieldError) errorResults.get(0);
        assertNotNull(errorResult.getObjectName());
        assertEquals("abc", errorResult.getField());
        assertNull(errorResult.getRejectedValue());
        assertFalse(errorResult.isBindingFailure());
        assertEquals("123", errorResult.getCode());
        assertEquals(3, errorResult.getCodes().length);
        assertEquals("jkl", errorResult.getArguments()[0]);
        assertEquals("j", errorResult.getArguments()[1]);       
        assertEquals(2, errorResult.getArguments().length);
        assertNull(errorResult.getDefaultMessage());
        
        // 2�ڂ�FieldError
        errorResult = (FieldError) errorResults.get(1);
        assertNotNull(errorResult.getObjectName());
        assertEquals("def", errorResult.getField());
        assertNull(errorResult.getRejectedValue());
        assertFalse(errorResult.isBindingFailure());
        assertEquals("456", errorResult.getCode());
        assertEquals(3, errorResult.getCodes().length);
        assertEquals("mno", errorResult.getArguments()[0]);
        assertEquals("k", errorResult.getArguments()[1]);       
        assertEquals(2, errorResult.getArguments().length);
        assertNull(errorResult.getDefaultMessage());

        // 3�ڂ�FieldError
        errorResult = (FieldError) errorResults.get(2);
        assertNotNull(errorResult.getObjectName());
        assertEquals("ghi", errorResult.getField());
        assertNull(errorResult.getRejectedValue());
        assertFalse(errorResult.isBindingFailure());
        assertEquals("789", errorResult.getCode());
        assertEquals(3, errorResult.getCodes().length);
        assertEquals("pqr", errorResult.getArguments()[0]);
        assertEquals("l", errorResult.getArguments()[1]);       
        assertEquals(2, errorResult.getArguments().length);
        assertNull(errorResult.getDefaultMessage());

    }

    /**
     * testCreateReplaceValues01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA,C
     * <br><br>
     * ���͒l�F(����) field:"abc"<br>
     *         (����) replaceValues:�v�f��3��String[]{"value","str1","str2"}<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) resultReplaceValues:�v�f��4��String[]{"abc","str1","str2","value"}<br>
     *         
     * <br>
     * �ȉ��̃��[���Ŋi�[���ꂽ�u�������񂪕ԋp����邱�Ƃ��m�F����B<br>
     * �E����field�̒l���u��������̐擪<br>
     * �E����replaceValues�̈�Ԗڂ̒l���u��������̖���
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testCreateReplaceValues01() throws Exception {
        CastorOXMapperImpl oxmapper = new CastorOXMapperImpl();
        SchemaValidator schemaValidator = new SchemaValidatorImpl();
        Object target = new Object();
        String objectName = "target";
        
        XMLServletRequestDataBinder servletDataBinder = new XMLServletRequestDataBinder(
                target, oxmapper, schemaValidator, objectName);

        String field = "abc";
        String[] replaceValues = new String[]{"value","str1","str2"};
            
        // �e�X�g���{
        String[] result = servletDataBinder.createReplaceValues(field, replaceValues);

        // ����
        assertEquals(4, result.length);
        assertEquals("abc", result[0]);
        assertEquals("str1", result[1]);
        assertEquals("str2", result[2]);
        assertEquals("value", result[3]);
        
    }

    /**
     * testCreateReplaceValues02()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA,C
     * <br><br>
     * ���͒l�F(����) field:""<br>
     *         (����) replaceValues:�v�f��3��String[]{"value","str1","str2"}<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) resultReplaceValues:�v�f��4��String[]<br>
     *                  String{"","str1","str2","value"}<br>
     *         
     * <br>
     * ����field�̒l���󕶎��̏ꍇ�A�擪�ɋ󕶎����i�[���ꂽ�u�������񂪕ԋp����邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testCreateReplaceValues02() throws Exception {
        CastorOXMapperImpl oxmapper = new CastorOXMapperImpl();
        SchemaValidator schemaValidator = new SchemaValidatorImpl();
        Object target = new Object();
        String objectName = "target";
        
        XMLServletRequestDataBinder servletDataBinder = new XMLServletRequestDataBinder(
                target, oxmapper, schemaValidator, objectName);

        String field = "";
        String[] replaceValues = new String[]{"value","str1","str2"};
            
        // �e�X�g���{
        String[] result = servletDataBinder.createReplaceValues(field, replaceValues);

        // ����
        assertEquals(4, result.length);
        assertEquals("", result[0]);
        assertEquals("str1", result[1]);
        assertEquals("str2", result[2]);
        assertEquals("value", result[3]);
    }

    /**
     * testCreateReplaceValues03()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA,C
     * <br><br>
     * ���͒l�F(����) field:null<br>
     *         (����) replaceValues:�v�f��3��String[]{null,null,null}<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) resultReplaceValues:�v�f��4��String[]<br>
     *                  String[]{"",null,null,null}<br>
     *         
     * <br>
     * ����field�̒l��null�̏ꍇ�A�擪�ɋ󕶎����i�[���ꂽ�u�������񂪕ԋp����邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testCreateReplaceValues03() throws Exception {
        CastorOXMapperImpl oxmapper = new CastorOXMapperImpl();
        SchemaValidator schemaValidator = new SchemaValidatorImpl();
        Object target = new Object();
        String objectName = "target";
        
        XMLServletRequestDataBinder servletDataBinder = new XMLServletRequestDataBinder(
                target, oxmapper, schemaValidator, objectName);

        String field = null;
        String[] replaceValues = new String[]{null,null,null};
            
        // �e�X�g���{
        String[] result = servletDataBinder.createReplaceValues(field, replaceValues);

        // ����
        assertEquals(4, result.length);
        assertEquals("", result[0]);
        assertNull(result[1]);
        assertNull(result[2]);
        assertNull(result[3]);
    }

    /**
     * testCreateReplaceValues04()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA,D
     * <br><br>
     * ���͒l�F(����) field:"abc"<br>
     *         (����) replaceValues:�v�f��0��String[]{}<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) resultReplaceValues:�v�f��1��String[]<br>
     *                  String[]{"abc"}<br>
     *         
     * <br>
     * ����replaceValues�̃T�C�Y��0�̏ꍇ�Afield�݂̂��i�[���ꂽ�u�������񂪕ԋp����邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testCreateReplaceValues04() throws Exception {
        CastorOXMapperImpl oxmapper = new CastorOXMapperImpl();
        SchemaValidator schemaValidator = new SchemaValidatorImpl();
        Object target = new Object();
        String objectName = "target";
        
        XMLServletRequestDataBinder servletDataBinder = new XMLServletRequestDataBinder(
                target, oxmapper, schemaValidator, objectName);

        String field = "abc";
        String[] replaceValues = new String[]{};
            
        // �e�X�g���{
        String[] result = servletDataBinder.createReplaceValues(field, replaceValues);

        // ����
        assertEquals(1, result.length);
        assertEquals("abc", result[0]);
    }

    /**
     * testCreateReplaceValues05()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA,D
     * <br><br>
     * ���͒l�F(����) field:"abc"<br>
     *         (����) replaceValues:�v�f��1��String[]{"value"}<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) resultReplaceValues:�v�f��2��String[]<br>
     *                  String[]{"abc","value"}<br>
     *         
     * <br>
     * ����replaceValues�̃T�C�Y��1�̏ꍇ�A�ȉ��̃��[���Ŋi�[���ꂽ�u�������񂪕ԋp����邱�Ƃ��m�F����B<br>
     * �E����field�̒l���u��������̐擪<br>
     * �E����replaceValues�̈�Ԗڂ̒l���u��������̖���
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testCreateReplaceValues05() throws Exception {
        CastorOXMapperImpl oxmapper = new CastorOXMapperImpl();
        SchemaValidator schemaValidator = new SchemaValidatorImpl();
        Object target = new Object();
        String objectName = "target";
        
        XMLServletRequestDataBinder servletDataBinder = new XMLServletRequestDataBinder(
                target, oxmapper, schemaValidator, objectName);

        String field = "abc";
        String[] replaceValues = new String[]{"value"};
            
        // �e�X�g���{
        String[] result = servletDataBinder.createReplaceValues(field, replaceValues);

        // ����
        assertEquals(2, result.length);
        assertEquals("abc", result[0]);
        assertEquals("value", result[1]);
    }

    /**
     * testCreateReplaceValues06()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA,C,D
     * <br><br>
     * ���͒l�F(����) field:"abc"<br>
     *         (����) replaceValues:null<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) resultReplaceValues:String[]{"abc"}<br>
     *         
     * <br>
     * ����replaceValues�̃T�C�Y��null�̏ꍇ�Afield�݂̂��i�[���ꂽ�u�������񂪕ԋp����邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testCreateReplaceValues06() throws Exception {
        CastorOXMapperImpl oxmapper = new CastorOXMapperImpl();
        SchemaValidator schemaValidator = new SchemaValidatorImpl();
        Object target = new Object();
        String objectName = "target";
        
        XMLServletRequestDataBinder servletDataBinder = new XMLServletRequestDataBinder(
                target, oxmapper, schemaValidator, objectName);

        String field = "abc";
        String[] replaceValues = null;
            
        // �e�X�g���{
        String[] result = servletDataBinder.createReplaceValues(field, replaceValues);

        // ����
        assertEquals(1, result.length);
        assertEquals("abc", result[0]);
    }

}
