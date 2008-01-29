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

package jp.terasoluna.fw.web.rich.springmvc.servlet.view.castor;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import jp.terasoluna.fw.oxm.mapper.OXMapper;
import jp.terasoluna.utlib.LogUTUtil;
import jp.terasoluna.utlib.MockHttpServletRequest;
import jp.terasoluna.utlib.MockHttpServletResponse;
import jp.terasoluna.utlib.UTUtil;
import jp.terasoluna.fw.web.rich.springmvc.Constants;
import jp.terasoluna.fw.web.rich.springmvc.servlet.view.castor.CastorView;
import junit.framework.TestCase;

/**
 * {@link jp.terasoluna.fw.web.rich.springmvc.servlet.view.castor.CastorView} �N���X�̃u���b�N�{�b�N�X�e�X�g�B
 * 
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4>
 * Castor�𗘗p����HTTP���X�|���X�������s���N���X�B<br>
 * �O������Fmodel�AHTTP���N�G�X�g�AHTTP���X�|���X�Aoxmapper��Null�l�ɂȂ�Ȃ��B
 * <p>
 * 
 * @see jp.terasoluna.fw.web.rich.springmvc.servlet.view.castor.
 * CastorView
 */
public class CastorViewTest extends TestCase {

    /**
     * ���̃e�X�g�P�[�X�����s����ׂ�
     * GUI �A�v���P�[�V�������N������B
     * 
     * @param args java �R�}���h�ɐݒ肳�ꂽ�p�����[�^
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(CastorViewTest.class);
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
    public CastorViewTest(String name) {
        super(name);
    }
    
    /**
     * testRenderMergedOutputModel01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) model:not null<br>
     *         (����) request:not null<br>
     *         (����) response:not null<br>
     *         (���) this.getContentType():null<br>
     *         (���) response.getWriter():�o�̓X�g���[����Ԃ��B<br>
     *         (���) responseWriter.close():����ɃN���[�Y����B<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) response:oxmapper.marshal()�̌��ʂ��������܂��B
     * �R���e���c�^�C�v���ݒ肳��Ȃ��B<br>
     *         (��ԕω�) oxmapper.marshal�i�j:�Ăяo���ꂽ���Ƃ��m�F����B<br>
     *         (��ԕω�) response.getWriter():�Ăяo���ꂽ���Ƃ��m�F����B<br>
     *         (��ԕω�) responseWriter.write():�Ăяo���ꂽ���Ƃ��m�F����B<br>
     *         (��ԕω�) responseWriter.close():�Ăяo���ꂽ���Ƃ��m�F����B<br>
     *         
     * <br>
     * �I�u�W�F�N�g�f�[�^��XML�ɕϊ����AHTTP���X�|���X�ɐݒ肷��e�X�g�B
     * �R���e���c�^�C�v��HTTP���X�|���X�ɏ������܂Ȃ��p�^�[���B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testRenderMergedOutputModel01() throws Exception {
        // �O����
    	CastorViewStub01 view = new CastorViewStub01();
        UTUtil.setPrivateField(view, "contentType", null);
        CastorView_OXMapperImplStub01 oxmapper = 
            new CastorView_OXMapperImplStub01();
        UTUtil.setPrivateField(view, "oxmapper", oxmapper);
        
        MockHttpServletRequest request = new MockHttpServletRequest();
        CastorView_MockHttpServletResponseStub02 response = 
            new CastorView_MockHttpServletResponseStub02();
        CastorView_PrintWriterStub01 printWriter = 
            new CastorView_PrintWriterStub01(System.out);
        response.setWriter(printWriter);
        Map<String, String> model = new HashMap<String, String>();
        model.put(Constants.RESULT_KEY, "test");

        // �e�X�g���{
        view.renderMergedOutputModel(model, request, response);

        // ����
        assertTrue(oxmapper.isMarshal);
        Object resultContentType = 
            UTUtil.getPrivateField(response, "contentType");
        assertEquals(null, resultContentType);
        assertTrue(response.isGetWriter);
        assertTrue(printWriter.isWrite);
        assertTrue(printWriter.isClose);
        
        // addHeader()�̌Ăяo���m�F
        assertEquals(model, view.modelData);
        assertEquals(request, view.requestData);
        assertEquals(response, view.responseData);
    }

    /**
     * testRenderMergedOutputModel02()
     * <br><br>
     * 
     * (����n) 
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) model:not null<br>
     *         (����) request:not null<br>
     *         (����) response:not null<br>
     *         (���) this.getContentType():not null<br>
     *         (���) response.getWriter():�o�̓X�g���[����Ԃ��B<br>
     *         (���) responseWriter.close():����ɃN���[�Y����B<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) response:oxmapper.marshal()�̌��ʂ��������܂��B
     * �R���e���c�^�C�v���ݒ肳���B<br>
     *         (��ԕω�) oxmapper.marshal�i�j:�Ăяo���ꂽ���Ƃ��m�F����B<br>
     *         (��ԕω�) response.getWriter():�Ăяo���ꂽ���Ƃ��m�F����B<br>
     *         (��ԕω�) responseWriter.write():�Ăяo���ꂽ���Ƃ��m�F����B<br>
     *         (��ԕω�) responseWriter.close():�Ăяo���ꂽ���Ƃ��m�F����B<br>
     *         
     * <br>
     * �I�u�W�F�N�g�f�[�^��XML�ɕϊ����AHTTP���X�|���X�ɐݒ肷��e�X�g�B
     * �R���e���c�^�C�v��HTTP���X�|���X�ɏ������ރp�^�[���B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testRenderMergedOutputModel02() throws Exception {
        // �O����
    	CastorViewStub01 view = new CastorViewStub01();
        String contentType = "text/xml;charset=UTF-8";
        UTUtil.setPrivateField(view, "contentType", contentType);
        CastorView_OXMapperImplStub01 oxmapper = 
            new CastorView_OXMapperImplStub01();
        UTUtil.setPrivateField(view, "oxmapper", oxmapper);
        
        MockHttpServletRequest request = new MockHttpServletRequest();
        CastorView_MockHttpServletResponseStub02 response = 
            new CastorView_MockHttpServletResponseStub02();
        CastorView_PrintWriterStub01 printWriter = 
            new CastorView_PrintWriterStub01(System.out);
        response.setWriter(printWriter);
        Map<String, String> model = new HashMap<String, String>();
        model.put(Constants.RESULT_KEY, "test");

        // �e�X�g���{
        view.renderMergedOutputModel(model, request, response);

        // ����
        assertTrue(oxmapper.isMarshal);
        Object resultContentType = 
            UTUtil.getPrivateField(response, "contentType");
        assertEquals(contentType, resultContentType);
        assertTrue(response.isGetWriter);
        assertTrue(printWriter.isWrite);
        assertTrue(printWriter.isClose);
        
        // addHeader()�̌Ăяo���m�F
        assertEquals(model, view.modelData);
        assertEquals(request, view.requestData);
        assertEquals(response, view.responseData);
    }

    /**
     * testRenderMergedOutputModel03()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) model:not null<br>
     *         (����) request:not null<br>
     *         (����) response:not null<br>
     *         (���) this.getContentType():not null<br>
     *         (���) response.getWriter():IO��O��Ԃ��B<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IOException<br>
     *         (��ԕω�) ���O:Cannot get Response Writer object.<br>
     *         
     * <br>
     * �o�̓X�g���[���擾����IO��O����������p�^�[���̃e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testRenderMergedOutputModel03() throws Exception {
        // �O����
        CastorView view = new CastorView();
        String contentType = "text/xml;charset=UTF-8";
        UTUtil.setPrivateField(view, "contentType", contentType);
        CastorView_OXMapperImplStub01 oxmapper = 
            new CastorView_OXMapperImplStub01();
        UTUtil.setPrivateField(view, "oxmapper", oxmapper);
        MockHttpServletResponse response = 
            new CastorView_MockHttpServletResponseStub01();
        response.setWriter(new PrintWriter(System.out));
        Map<String, String> model = new HashMap<String, String>();
        model.put(Constants.RESULT_KEY, "test");

        // �e�X�g���{
        try {
            view.renderMergedOutputModel(model, null, response);
            fail();
        } catch (IOException e) {
            // ���ʊm�F
            assertTrue(LogUTUtil.checkError(
                    "Cannot get Response Writer object."));
        }
    }

    /**
     * testRenderMergedOutputModel04()
     * <br><br>
     * 
     * (����n) 
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) model:not null<br>
     *         (����) request:not null<br>
     *         (����) response:not null<br>
     *         (���) this.getContentType():not null<br>
     *         (���) response.getWriter():Null��Ԃ��B<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) oxmapper.marshal�i�j:�Ăяo���ꂽ���Ƃ��m�F����B<br>
     *         (��ԕω�) ��O:NullPointerException<br>
     *         
     * <br>
     * �o�̓X�g���[����Null�̏ꍇ�Aclose���\�b�h���Ă΂�Ȃ����Ƃ��m�F����e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testRenderMergedOutputModel04() throws Exception {
        // �O����
        CastorView view = new CastorView();
        String contentType = "text/xml;charset=UTF-8";
        UTUtil.setPrivateField(view, "contentType", contentType);
        CastorView_OXMapperImplStub01 oxmapper = 
            new CastorView_OXMapperImplStub01();
        UTUtil.setPrivateField(view, "oxmapper", oxmapper);
        CastorView_MockHttpServletResponseStub02 response = 
            new CastorView_MockHttpServletResponseStub02();
        response.setWriter(null);
        Map<String, String> model = new HashMap<String, String>();
        model.put(Constants.RESULT_KEY, "test");

        // �e�X�g���{
        try {
            view.renderMergedOutputModel(model, null, response);
            fail();
        } catch (NullPointerException e) {
            // OK
        	return;
        }

        // ����
        assertTrue(oxmapper.isMarshal);
        Object resultContentType = 
            UTUtil.getPrivateField(response, "contentType");
        assertEquals("text/xml;charset=UTF-8", resultContentType);
        assertTrue(response.isGetWriter);
    }
    
    /**
     * testGetOxmapper01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(���) this.oxmapper:not null<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) OXMapper:this.oxmapper<br>
     *         
     * <br>
     * oxmapper������getter���\�b�h�̃e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetOxmapper01() throws Exception {
        // �O����
        CastorView view = new CastorView();
        OXMapper oxmapper = new CastorView_OXMapperImplStub01();
        UTUtil.setPrivateField(view, "oxmapper", oxmapper);

        // �e�X�g���{
        Object result = view.getOxmapper();

        // ����
        assertSame(oxmapper, result);
    }

    /**
     * testSetOxmapper01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) oxmapper:not null<br>
     *         (���) this.oxmapper:null<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) this.oxmapper:�����Őݒ肵���l<br>
     *         
     * <br>
     * oxmapper������setter���\�b�h�̃e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetOxmapper01() throws Exception {
        // �O����
        CastorView view = new CastorView();
        UTUtil.setPrivateField(view, "oxmapper", null);

        // �e�X�g���{
        OXMapper oxmapper = new CastorView_OXMapperImplStub01();
        view.setOxmapper(oxmapper);

        // ����
        Object result = UTUtil.getPrivateField(view, "oxmapper");
        assertSame(oxmapper, result);
    }
}
