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

package jp.terasoluna.fw.web.rich.springmvc.servlet.handler;

import java.util.HashMap;
import java.util.Map;

import jp.terasoluna.fw.web.rich.context.support.DefaultRequestContextSupportImpl;
import jp.terasoluna.fw.web.rich.context.support.RequestContextSupport;
import jp.terasoluna.utlib.LogUTUtil;
import jp.terasoluna.utlib.MockHttpServletRequest;
import jp.terasoluna.utlib.UTUtil;
import jp.terasoluna.fw.web.rich.springmvc.servlet.handler.BeanNameUrlHandlerMappingEx;
import junit.framework.TestCase;

/**
 * {@link jp.terasoluna.fw.web.rich.springmvc.servlet.handler.
 * BeanNameUrlHandlerMappingEx} �N���X�̃u���b�N�{�b�N�X�e�X�g�B
 * 
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4>
 * ���N�G�X�g���Ǝ��s���郊�N�G�X�g�R���g���[��Bean��`�̃}�b�s���O��
 * �s���n���h���B<br>
 * �O������FBean��`�t�@�C���ɃR���g���[�����`���Ă����K�v������B
 * <p>
 * 
 * @see jp.terasoluna.fw.web.rich.springmvc.servlet.handler.
 * BeanNameUrlHandlerMappingEx
 */
public class BeanNameUrlHandlerMappingExTest extends TestCase {

    /**
     * ���̃e�X�g�P�[�X�����s����ׂ�
     * GUI �A�v���P�[�V�������N������B
     * 
     * @param args java �R�}���h�ɐݒ肳�ꂽ�p�����[�^
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(BeanNameUrlHandlerMappingExTest.class);
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
    public BeanNameUrlHandlerMappingExTest(String name) {
        super(name);
    }

    /**
     * testSetPrefix01()
     * <br><br>
     * 
     * (����n) 
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) prefix:not null<br>
     *                �i�h/�h�j<br>
     *         (���) this.prefix:not null<br>
     *                �i�h�h�j<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) prefix:�����Őݒ肵���l���ݒ肳��Ă���B<br>
     *         
     * <br>
     * prefix������set���\�b�h�̃e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetPrefix01() throws Exception {
        // �O����
        BeanNameUrlHandlerMappingEx handlerMapping = 
            new BeanNameUrlHandlerMappingEx();
        UTUtil.setPrivateField(handlerMapping, "prefix", null);

        // �e�X�g���{
        String value = "/";
        handlerMapping.setPrefix(value);

        // ����
        Object result = UTUtil.getPrivateField(handlerMapping, "prefix");
        assertEquals(value, result);
    }

    /**
     * testSetSuffix01()
     * <br><br>
     * 
     * (����n) 
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) suffix:not null<br>
     *                �i�hController�h�j<br>
     *         (���) this.suffix:not null<br>
     *                �i�h�h�j<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) suffix:�����Őݒ肵���l���ݒ肳��Ă���B<br>
     *         
     * <br>
     * suffix������set���\�b�h�̃e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetSuffix01() throws Exception {
        // �O����
        BeanNameUrlHandlerMappingEx handlerMapping = 
            new BeanNameUrlHandlerMappingEx();
        UTUtil.setPrivateField(handlerMapping, "suffix", null);

        // �e�X�g���{
        String value = "Controller";
        handlerMapping.setSuffix(value);

        // ����
        Object result = UTUtil.getPrivateField(handlerMapping, "suffix");
        assertEquals(value, result);
    }

    /**
     * testSetCtxSupport01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) ctxSupport:not null<br>
     *         (���) this.ctxSupport:null<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) ctxSupport:�����Őݒ肵���l���ݒ肳��Ă���B<br>
     *         
     * <br>
     * ctxSupport������set���\�b�h�̃e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetCtxSupport01() throws Exception {
        // �O����
        BeanNameUrlHandlerMappingEx handlerMapping = 
            new BeanNameUrlHandlerMappingEx();
        UTUtil.setPrivateField(handlerMapping, "ctxSupport", null);

        // �e�X�g���{
        RequestContextSupport ctxSupport = 
            new DefaultRequestContextSupportImpl();
        handlerMapping.setCtxSupport(ctxSupport);

        // ����
        Object result = UTUtil.getPrivateField(handlerMapping, "ctxSupport");
        assertSame(ctxSupport, result);
    }

    /**
     * testLookupHandler01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(���) ctxSupport.getRequestName():"sum"<br>
     *         (���) prefix:"/"<br>
     *         (���) suffix:"Controller"<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) Object:super.lookupHandler�i�j�̖߂�l�Ɠ��������Ƃ�
     *         �m�F����B<br>
     *         (��ԕω�) super.lookupHandler�i�j:
     *         �Ăяo���ꂽ���Ƃ��m�F����B<br>
     *                    �������󂯎�������Ƃ��m�F����B<br>
     *         
     * <br>
     * �R���g���[���擾���\�b�h�̃e�X�g�B
     * �v���t�B�b�N�X�E�T�t�B�b�N�X���g�p���ăR���g���[����ID���w�肷��B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testLookupHandler01() throws Exception {
        // �O����
        BeanNameUrlHandlerMappingEx handlerMapping = 
            new BeanNameUrlHandlerMappingEx();
        Object dummy = new Object();
        Map<Object, Object> handlerMap = new HashMap<Object, Object>();
        handlerMap.put("/sumController", dummy);
        UTUtil.setPrivateField(handlerMapping, "handlerMap", handlerMap);
        String prefix = "/";
        UTUtil.setPrivateField(handlerMapping, "prefix", prefix);
        String suffix = "Controller";
        UTUtil.setPrivateField(handlerMapping, "suffix", suffix);
        BeanNameUrlHandlerMappingEx_DefaultRequestContextSupportImplStub01 ctxSupport = 
            new BeanNameUrlHandlerMappingEx_DefaultRequestContextSupportImplStub01();
        ctxSupport.setRequestName("sum");
        UTUtil.setPrivateField(handlerMapping, "ctxSupport", ctxSupport); 
        MockHttpServletRequest request = new MockHttpServletRequest();

        // �e�X�g���{
        Object result = handlerMapping.lookupHandler(null, request);

        // ����
        assertSame(dummy, result);
    }

    /**
     * testLookupHandler02()
     * <br><br>
     * 
     * (����n) 
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(���) ctxSupport.getRequestName():null<br>
     *         (���) prefix:null<br>
     *         (���) suffix:null<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) Object:super.lookupHandler�i�j�̖߂�l�Ɠ��������Ƃ�
     *         �m�F����B<br>
     *         (��ԕω�) super.lookupHandler�i�j:
     *         �Ăяo���ꂽ���Ƃ��m�F����B<br>
     *                    �������󂯎�������Ƃ��m�F����B<br>
     *         
     * <br>
     * �R���g���[���擾���\�b�h�̃e�X�g�B���N�G�X�g����Null�̏ꍇ�̃e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testLookupHandler02() throws Exception {
        // �O����
        BeanNameUrlHandlerMappingEx handlerMapping = 
            new BeanNameUrlHandlerMappingEx();
        Object dummy = new Object();
        Map<Object, Object> handlerMap = new HashMap<Object, Object>();
        handlerMap.put("/nullController", dummy);
        UTUtil.setPrivateField(handlerMapping, "handlerMap", handlerMap);
        String prefix = "/";
        UTUtil.setPrivateField(handlerMapping, "prefix", prefix);
        String suffix = "Controller";
        UTUtil.setPrivateField(handlerMapping, "suffix", suffix);
        BeanNameUrlHandlerMappingEx_DefaultRequestContextSupportImplStub01 ctxSupport = 
            new BeanNameUrlHandlerMappingEx_DefaultRequestContextSupportImplStub01();
        ctxSupport.setRequestName(null);
        UTUtil.setPrivateField(handlerMapping, "ctxSupport", ctxSupport);
        
        MockHttpServletRequest request = new MockHttpServletRequest();

        // �e�X�g���{
        Object result = handlerMapping.lookupHandler(null, request);

        // ����
        assertSame(dummy, result);
    }

    /**
     * testLookupHandler03()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(���) ctxSupport.getRequestName()���P:�󕶎�<br>
     *         (���) prefix:�󕶎�<br>
     *         (���) suffix:�󕶎�<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) Object:super.lookupHandler�i�j�̖߂�l�Ɠ��������Ƃ�
     *         �m�F����B<br>
     *         (��ԕω�) super.lookupHandler�i�j:
     *         �Ăяo���ꂽ���Ƃ��m�F����B<br>
     *                    �������󂯎�������Ƃ��m�F����B<br>
     *         
     * <br>
     * �R���g���[���擾���\�b�h�̃e�X�g�B���N�G�X�g�����󕶎��̏ꍇ�̃e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testLookupHandler03() throws Exception {
        // �O����
        BeanNameUrlHandlerMappingEx handlerMapping = 
            new BeanNameUrlHandlerMappingEx();
        Object dummy = new Object();
        Map<Object, Object> handlerMap = new HashMap<Object, Object>();
        handlerMap.put("/Controller", dummy);
        UTUtil.setPrivateField(handlerMapping, "handlerMap", handlerMap);
        String prefix = "/";
        UTUtil.setPrivateField(handlerMapping, "prefix", prefix);
        String suffix = "Controller";
        UTUtil.setPrivateField(handlerMapping, "suffix", suffix);
        BeanNameUrlHandlerMappingEx_DefaultRequestContextSupportImplStub01 ctxSupport = 
            new BeanNameUrlHandlerMappingEx_DefaultRequestContextSupportImplStub01();
        ctxSupport.setRequestName("");
        UTUtil.setPrivateField(handlerMapping, "ctxSupport", ctxSupport);
        
        MockHttpServletRequest request = new MockHttpServletRequest();

        // �e�X�g���{
        Object result = handlerMapping.lookupHandler(null, request);

        // ����
        assertSame(dummy, result);    
    }

    /**
     * testLookupHandler04()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(���) ctxSupport.getRequestName()���P:"sum"<br>
     *         (���) prefix:"/"<br>
     *         (���) suffix:"Controller"<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) Object:Null<br>
     *         (��ԕω�) super.lookupHandler�i�j:�Ăяo���ꂽ���Ƃ��m�F����B<br>
     *                    �������󂯎�������Ƃ��m�F����B<br>
     *         (��ԕω�) ���O:���O���x���F�G���[<br>
     *                    "Controller is not found. " + "BeanName:'" + newUrlPath + "'"<br>
     *         
     * <br>
     * �w�肵���R���g���[�����Ȃ��ꍇ�̃e�X�g�BNull��Ԃ��B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testLookupHandler04() throws Exception {
        // �O����
        BeanNameUrlHandlerMappingEx handlerMapping = 
            new BeanNameUrlHandlerMappingEx();
        Map<Object, Object> handlerMap = new HashMap<Object, Object>();
        UTUtil.setPrivateField(handlerMapping, "handlerMap", handlerMap);
        String prefix = "/";
        UTUtil.setPrivateField(handlerMapping, "prefix", prefix);
        String suffix = "Controller";
        UTUtil.setPrivateField(handlerMapping, "suffix", suffix);
        BeanNameUrlHandlerMappingEx_DefaultRequestContextSupportImplStub01 ctxSupport = 
            new BeanNameUrlHandlerMappingEx_DefaultRequestContextSupportImplStub01();
        ctxSupport.setRequestName("sum");
        UTUtil.setPrivateField(handlerMapping, "ctxSupport", ctxSupport);
        
        MockHttpServletRequest request = new MockHttpServletRequest();

        // �e�X�g���{
        Object result = handlerMapping.lookupHandler(null, request);

        // ����
        assertSame(null, result);
        assertTrue(LogUTUtil.checkError("Controller is not found. " + "BeanName:" 
                + "'/sumController'"));
    }

    /**
     * testLookupHandler05()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(���) ctxSupport.getRequestName()���P:"sum"<br>
     *         (���) prefix:"/"<br>
     *         (���) suffix:"Controller"<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) Object:super.lookupHandler�i�j�̖߂�l�Ɠ��������Ƃ��m�F����B<br>
     *         (��ԕω�) super.lookupHandler�i�j:�Ăяo���ꂽ���Ƃ��m�F����B<br>
     *                    �������󂯎�������Ƃ��m�F����B<br>
     *         
     * <br>
     * �R���g���[����Name��ANT�`���̃p�X�w��������ꍇ�̃e�X�g�B�p�^�[���Ɉ�v����A�ł�����������̃R���g���[����Ԃ��B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testLookupHandler05() throws Exception {
        // �O����
        BeanNameUrlHandlerMappingEx handlerMapping = 
            new BeanNameUrlHandlerMappingEx();
        Object dummy1 = new Object();
        Object dummy2 = new Object();
        Object dummy3 = new Object();
        Map<Object, Object> handlerMap = new HashMap<Object, Object>();
        handlerMap.put("/s*", dummy1);
        handlerMap.put("/sum*", dummy2);
        handlerMap.put("/sumControll*", dummy3);
        UTUtil.setPrivateField(handlerMapping, "handlerMap", handlerMap);
        String prefix = "/";
        UTUtil.setPrivateField(handlerMapping, "prefix", prefix);
        String suffix = "Controller";
        UTUtil.setPrivateField(handlerMapping, "suffix", suffix);
        BeanNameUrlHandlerMappingEx_DefaultRequestContextSupportImplStub01 ctxSupport = 
            new BeanNameUrlHandlerMappingEx_DefaultRequestContextSupportImplStub01();
        ctxSupport.setRequestName("sum");
        UTUtil.setPrivateField(handlerMapping, "ctxSupport", ctxSupport);
        
        MockHttpServletRequest request = new MockHttpServletRequest();

        // �e�X�g���{
        Object result = handlerMapping.lookupHandler(null, request);

        // ����
        assertSame(dummy3, result);
    }

    /**
     * testAfterPropertiesSet01()
     * <br><br>
     * 
     * (����n) 
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(���) this.ctxSupport:not null<br>
     *         
     * <br>
     * ���Ғl�F
     * <br>
     * �C���X�^���X���ɌĂ΂�郁�\�b�h�B
     * �R���e�L�X�g�T�|�[�g���ݒ肳��Ă��邩�`�F�b�N����B
     * �R���e�L�X�g�T�|�[�g���ݒ肳��Ă��邽�߁A�������Ȃ��ŏI������p�^�[���B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testAfterPropertiesSet01() throws Exception {
        // �O����
        BeanNameUrlHandlerMappingEx handlerMapping = 
            new BeanNameUrlHandlerMappingEx();
        RequestContextSupport ctxSupport = 
            new DefaultRequestContextSupportImpl();
        UTUtil.setPrivateField(handlerMapping, "ctxSupport", ctxSupport);

        // �e�X�g���{
        handlerMapping.afterPropertiesSet();

        // ���ʊm�F�i��O���������Ȃ����OK�j
    }

    /**
     * testAfterPropertiesSet02()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(���) this.ctxSupport:null<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalStateException( "BeanNameUrlHandlerMappingEx must be set ctxSupport.")<br>
     *         (��ԕω�) ���O:BeanNameUrlHandlerMappingEx must be set ctxSupport.<br>
     *         
     * <br>
     * �C���X�^���X���ɌĂ΂�郁�\�b�h�B�R���e�L�X�g�T�|�[�g���ݒ肳��Ă��邩�`�F�b�N����B�R���e�L�X�g�T�|�[�g���ݒ肳��Ă��Ȃ����߁A��O�𓊂���B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testAfterPropertiesSet02() throws Exception {
        // �O����
        BeanNameUrlHandlerMappingEx handlerMapping = 
            new BeanNameUrlHandlerMappingEx();
        UTUtil.setPrivateField(handlerMapping, "ctxSupport", null);

        // �e�X�g���{
        try {
            handlerMapping.afterPropertiesSet();
            fail();
        } catch (IllegalStateException e) {
            String expect = 
                "BeanNameUrlHandlerMappingEx must be set ctxSupport.";
            assertEquals(expect, e.getMessage());
            assertTrue(LogUTUtil.checkError(expect));
        }
    }
}
