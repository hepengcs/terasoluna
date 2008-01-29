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

package jp.terasoluna.fw.web.rich.springmvc.bind.creator;


import javax.servlet.http.HttpServletRequest;
import jp.terasoluna.fw.oxm.mapper.OXMapper;
import jp.terasoluna.fw.oxm.mapper.castor.CastorOXMapperImpl;
import jp.terasoluna.fw.oxm.xsd.SchemaValidator;
import jp.terasoluna.fw.oxm.xsd.xerces.SchemaValidatorImpl;
import jp.terasoluna.utlib.LogUTUtil;
import jp.terasoluna.utlib.MockHttpServletRequest;
import jp.terasoluna.utlib.UTUtil;
import jp.terasoluna.fw.web.rich.springmvc.bind.creator.XMLServletRequestDataBinderCreator;
import junit.framework.TestCase;
import org.springframework.web.bind.ServletRequestDataBinder;

/**
 * {@link jp.terasoluna.fw.web.rich.springmvc.bind.creator.XMLServletRequestDataBinderCreator}
 * �N���X�̃u���b�N�{�b�N�X�e�X�g�B
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4>
 * XML�`���Œ�`���ꂽ���N�G�X�g�f�[�^���o�C���h����N���X�𐶐�������������N���X�B<br>
 * �E�O�����<br>
 * create���\�b�h�̓R���g���[������Ăяo����A������request,command,requestName��null�̏�Ԃ͑��݂��Ȃ��B
 * <p>
 * 
 * @see jp.terasoluna.fw.web.rich.springmvc.bind.creator.XMLServletRequestDataBinderCreator
 */
public class XMLServletRequestDataBinderCreatorTest extends TestCase {

    /**
     * ���̃e�X�g�P�[�X�����s����ׂ� GUI �A�v���P�[�V�������N������B
     * 
     * @param args java �R�}���h�ɐݒ肳�ꂽ�p�����[�^
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner
                .run(XMLServletRequestDataBinderCreatorTest.class);
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
    public XMLServletRequestDataBinderCreatorTest(String name) {
        super(name);
    }

    /**
     * testCreate01() <br>
     * <br>
     * (����n)<br>
     * �ϓ_�FA <br>
     * <br>
     * ���͒l�F(����) request:not null<br>
     * (����) command:not null<br>
     * (����) requestName:not null<br>
     * (���) this.oxmapper:not null<br>
     * <br>
     * ���Ғl�F(�߂�l) result:ServletRequestDataBinder{<br>
     * target = ������command<br>
     * oxmapper = this.oxmapper,<br>
     * schemaValidator = this.schemaValidator<br> }<br>
     * <br>
     * XML�`���̃o�C���_�𐶐�����e�X�g�B�߂�l��XMLServletRequestDataBinder�ɁA
     * ������command�Ƒ�����oxmapper,schemaValidator������ɃZ�b�g����邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testCreate01() throws Exception {
        // �O����
        XMLServletRequestDataBinderCreator creator = new XMLServletRequestDataBinderCreator();
        OXMapper oxmapper = new CastorOXMapperImpl();
        SchemaValidator schemaValidator = new SchemaValidatorImpl();
        HttpServletRequest request = new MockHttpServletRequest();
        Object command = new Object();
        String str = "abc";
        
        UTUtil.setPrivateField(creator, "oxmapper", oxmapper);        
        UTUtil.setPrivateField(creator, "schemaValidator", schemaValidator);
        
        // �e�X�g���{
        ServletRequestDataBinder result = creator.create(request, command, str);
        
        assertSame(command, (UTUtil.getPrivateField(result,
        "target")));
        assertSame(oxmapper, (UTUtil.getPrivateField(result,
        "oxmapper")));
        assertSame(schemaValidator, (UTUtil.getPrivateField(result,
        "schemaValidator")));
    }

    /**
     * testCreate02() <br>
     * <br>
     * (����n)<br>
     * �ϓ_�FA <br>
     * <br>
     * ���͒l�F(����) request:not null<br>
     * (����) command:not null<br>
     * (����) requestName:not null<br>
     * (���) this.oxmapper:not null<br>
     * <br>
     * ���Ғl�F(�߂�l) result:ServletRequestDataBinder{<br>
     * target = ������command<br>
     * oxmapper = this.oxmapper,<br>
     * schemaValidator = this.schemaValidator,<br> }<br>
     * <br>
     * XML�`���̃o�C���_�𐶐�����e�X�g�B�߂�l��XMLServletRequestDataBinder�ɁA������command�Ƒ�����oxmapper,schemaValidator������ɃZ�b�g����邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testCreate02() throws Exception {
        // �O����
        XMLServletRequestDataBinderCreator creator = new XMLServletRequestDataBinderCreator();
        OXMapper oxmapper = new CastorOXMapperImpl();
        SchemaValidator schemaValidator = null;
        HttpServletRequest request = new MockHttpServletRequest();
        Object command = new Object();
        String str = "abc";
        
        UTUtil.setPrivateField(creator, "oxmapper", oxmapper);        
        UTUtil.setPrivateField(creator, "schemaValidator", schemaValidator);
        
        // �e�X�g���{
        ServletRequestDataBinder result = creator.create(request, command, str);
        
        assertSame(command, (UTUtil.getPrivateField(result,
        "target")));
        assertSame(oxmapper, (UTUtil.getPrivateField(result,
        "oxmapper")));
        assertNull(UTUtil.getPrivateField(result,
        "schemaValidator"));
        
    } 
    
    /**
     * testGetOxmapper01() <br>
     * <br>
     * (����n)<br>
     * �ϓ_�FA <br>
     * <br>
     * ���͒l�F(���) this.binder:not null<br>
     * <br>
     * ���Ғl�F(�߂�l) binder:not null<br>
     * <br>
     * �������������ԋp����邱�Ƃ��m�F����B <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetOxmapper01() throws Exception {
        // �O����
        XMLServletRequestDataBinderCreator creator = new XMLServletRequestDataBinderCreator();
        OXMapper oxmapper = new CastorOXMapperImpl();
        UTUtil.setPrivateField(creator, "oxmapper", oxmapper);

        // �e�X�g���{
        OXMapper result = creator.getOxmapper();

        // ����
        assertSame(oxmapper, result);

    }

    /**
     * testSetOxmapper01() <br>
     * <br>
     * (����n)<br>
     * �ϓ_�FA,C <br>
     * <br>
     * ���͒l�F(����) binder:not null<br>
     * (���) this.binder:null<br>
     * <br>
     * ���Ғl�F(��ԕω�) this.binder:not null<br>
     * <br>
     * �����������ɐ������ݒ肳��邱�Ƃ��m�F����B <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetOxmapper01() throws Exception {
        // �O����
        XMLServletRequestDataBinderCreator creator = new XMLServletRequestDataBinderCreator();
        UTUtil.setPrivateField(creator, "oxmapper", null);

        OXMapper oxmapper = new CastorOXMapperImpl();

        // �e�X�g���{
        creator.setOxmapper(oxmapper);

        // ����
        OXMapper result = ((OXMapper) UTUtil.getPrivateField(creator,
                "oxmapper"));

        assertSame(oxmapper, result);

    }

    /**
     * testGetSchemaValidator01() <br>
     * <br>
     * (����n)<br>
     * �ϓ_�FA <br>
     * <br>
     * ���͒l�F(���) this.binder:not null<br>
     * <br>
     * ���Ғl�F(�߂�l) binder:not null<br>
     * <br>
     * �������������ԋp����邱�Ƃ��m�F����B <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetSchemaValidator01() throws Exception {
        // �O����
        XMLServletRequestDataBinderCreator creator = new XMLServletRequestDataBinderCreator();
        SchemaValidator schemaValidator = new SchemaValidatorImpl();
        UTUtil.setPrivateField(creator, "schemaValidator", schemaValidator);

        // �e�X�g���{
        SchemaValidator result = creator.getSchemaValidator();

        // ����
        assertSame(schemaValidator, result);

    }

    /**
     * testSetSchemaValidator01() <br>
     * <br>
     * (����n)<br>
     * �ϓ_�FA,C <br>
     * <br>
     * ���͒l�F(����) binder:not null<br>
     * (���) this.binder:null<br>
     * <br>
     * ���Ғl�F(��ԕω�) this.binder:not null<br>
     * <br>
     * �����������ɐ������ݒ肳��邱�Ƃ��m�F����B <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetSchemaValidator01() throws Exception {
        // �O����
        XMLServletRequestDataBinderCreator creator = new XMLServletRequestDataBinderCreator();
        UTUtil.setPrivateField(creator, "oxmapper", null);

        SchemaValidator schemaValidator = new SchemaValidatorImpl();

        // �e�X�g���{
        creator.setSchemaValidator(schemaValidator);

        // ����
        SchemaValidator result = ((SchemaValidator) UTUtil.getPrivateField(creator,
                "schemaValidator"));

        assertSame(schemaValidator, result);

    }
    
    /**
     * testAfterPropertiesSet01() <br>
     * <br>
     * (�ُ�n)<br>
     * �ϓ_�FA,C,G <br>
     * <br>
     * ���͒l�F(���) this.oxmapper:null<br>
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalStateException{<br>
     * message="OXMapper isn't set in ServletRequestDataBinder. Check
     * Spring Bean definition file."<br> }<br>
     * (��ԕω�) ���O:���O���x���Ferror<br>
     * OXMapper isn't set in ServletRequestDataBinder. Check Spring
     * Bean definition file.<br>
     * <br>
     * �{�N���X���C���X�^���X�����ꂽ����ɁAoxmapper�������ݒ肳��Ă��Ȃ��ꍇ�A��O���������邱�Ƃ��m�F����B <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testAfterPropertiesSet01() throws Exception {
        // �O����
        XMLServletRequestDataBinderCreator creator = new XMLServletRequestDataBinderCreator();
        UTUtil.setPrivateField(creator, "oxmapper", null);

        // �e�X�g���{
        try {
            creator.afterPropertiesSet();
            fail();
        } catch (IllegalStateException e) {

            String message = "OXMapper isn't set in ServletRequestDataBinder. "
                    + "Check Spring Bean definition file.";

            // ����
            assertEquals(message, e.getMessage());
            assertTrue(LogUTUtil.checkError(message));

        }
    }

    /**
     * testAfterPropertiesSet02() <br>
     * <br>
     * (����n)<br>
     * �ϓ_�FA<br>
     * <br>
     * ���͒l�F(���) this.oxmapper:not null<br>
     * <br>
     * ���Ғl�F <br>
     * �{�N���X���C���X�^���X�����ꂽ����ɁAoxmapper�������ݒ肳��Ă���ꍇ�A�����s��Ȃ����Ƃ��m�F����B <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testAfterPropertiesSet02() throws Exception {
        // �O����
        XMLServletRequestDataBinderCreator creator = new XMLServletRequestDataBinderCreator();
        OXMapper oxmapper = new CastorOXMapperImpl();
        UTUtil.setPrivateField(creator, "oxmapper", oxmapper);

        // �e�X�g���{
        creator.afterPropertiesSet();

        // �i��O���������Ȃ����True)
    }

}
