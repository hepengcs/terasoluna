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

package jp.terasoluna.fw.web.rich.context.support;

import jp.terasoluna.utlib.LogUTUtil;
import jp.terasoluna.utlib.MockHttpServletRequest;
import jp.terasoluna.utlib.UTUtil;
import jp.terasoluna.fw.web.rich.context.RequestContext;
import jp.terasoluna.fw.web.rich.context.RequestContextManager;
import jp.terasoluna.fw.web.rich.context.exception.IllegalContextPropertyClassTypeException;
import junit.framework.TestCase;

/**
 * {@link jp.terasoluna.fw.web.rich.context.support.AbstractRequestContextSupport} �N���X�̃u���b�N�{�b�N�X�e�X�g�B
 * 
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4>
 * ��������������߂̕⏕���W�b�N�C���^�t�F�[�X���p���������ۃN���X�B<br>
 * �O������F
 * <p>
 * 
 * @see jp.terasoluna.fw.web.rich.context.support.AbstractRequestContextSupport
 */
public class AbstractRequestContextSupportTest extends TestCase {

    /**
     * ���̃e�X�g�P�[�X�����s����ׂ�
     * GUI �A�v���P�[�V�������N������B
     * 
     * @param args java �R�}���h�ɐݒ肳�ꂽ�p�����[�^
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(AbstractRequestContextSupportTest.class);
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
    public AbstractRequestContextSupportTest(String name) {
        super(name);
    }

    /**
     * testGetRequestName01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(���) getRequestContext():not null<br>
     *                �inull�͕Ԃ�Ȃ��j<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) String:getRequestContext().getRequestName()�̖߂�l�Ɠ��������Ƃ��m�F����B<br>
     *         
     * <br>
     * ���N�G�X�g�����擾���郁�\�b�h�̃e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetRequestName01() throws Exception {
        // AbstractRequestContextSupport�����N���X�̐���
        AbstractRequestContextSupportImpl01 target 
        	= new AbstractRequestContextSupportImpl01();
        
        // RequestContext�̐ݒ�
        RequestContext ctx = new RequestContext();
        ctx.setRequestName("test");
        target.setTestCtx(ctx);
        
        // �e�X�g���{�E����
        assertEquals(target.getRequestContext().getRequestName(), target
                .getRequestName());
    }

    /**
     * testGetProperty01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) key:"arg1"<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) Object:getRequestContext().getProperty()�̖߂�l�Ɠ��������Ƃ��m�F����B<br>
     *         
     * <br>
     * �Ɩ��p�����[�^���擾���郁�\�b�h�̃e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetProperty01() throws Exception {
        // AbstractRequestContextSupport�����N���X�̐���
        AbstractRequestContextSupportImpl01 target = new AbstractRequestContextSupportImpl01();
        
        // RequestContext�̐ݒ�
        RequestContext ctx = new RequestContext();
        Object object = new Object();
        ctx.setProperty("arg1", object);
        target.setTestCtx(ctx);

        // �e�X�g���{�E����
        assertSame(object, target.getProperty("arg1"));
    }
    
    /**
     * testGetPropertyStringClass01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) key:"arg1"<br>
     *                clazz:Object.class<br>
     * <br>
     * ���Ғl�F(�߂�l) Object:getRequestContext().getProperty()�̖߂�l�Ɠ��������Ƃ��m�F����B<br>
     *         
     * <br>
     * �Ɩ��p�����[�^���擾���郁�\�b�h�̃e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetPropertyStringClass01() throws Exception {
        // AbstractRequestContextSupport�����N���X�̐���
        AbstractRequestContextSupportImpl01 target = new AbstractRequestContextSupportImpl01();
        
        // RequestContext�̐ݒ�
        RequestContext ctx = new RequestContext();
        String string = "";
        ctx.setProperty("arg1", string);
        target.setTestCtx(ctx);

        // �e�X�g���{�E����
        assertSame(string, target.getProperty("arg1",String.class));
    }
    
    /**
     * testGetPropertyStringClass02()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) key:"arg1"<br>
     *                clazz:String.class<br>
     * <br>
     * ���Ғl�F(��O) IllegalArgumentException:<br>
     *                 ���b�Z�[�W�FMust not use null for clazz of an argument.
     *         
     * <br>
     * �擾����Ɩ��v���p�e�B�̌^���w�肵�Ȃ��ꍇ�̃e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetPropertyStringClass02() throws Exception {
        // AbstractRequestContextSupport�����N���X�̐���
        AbstractRequestContextSupportImpl01 target = new AbstractRequestContextSupportImpl01();
        
        // RequestContext�̐ݒ�
        RequestContext ctx = new RequestContext();
        String string = "";
        ctx.setProperty("arg1", string);
        target.setTestCtx(ctx);

        // �e�X�g���{
        try {
        	target.getProperty("arg1", null);
            fail();
        } catch (IllegalArgumentException e) {  
            // ����
            assertEquals("Must not use null for clazz of an argument.",e.getMessage());
        }
    }
    
    /**
     * testGetPropertyStringClass03()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) key:"arg1"<br>
     *                clazz:Integer.class<br>
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalContextPropertyClassTypeException<br>
     *         
     * <br>
     * �擾����Ɩ��v���p�e�B�̌^�Ǝw�肳�ꂽ�^���Ⴄ�ꍇ�̃e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetPropertyStringClass03() throws Exception {
        // AbstractRequestContextSupport�����N���X�̐���
        AbstractRequestContextSupportImpl01 target = new AbstractRequestContextSupportImpl01();
        
        // RequestContext�̐ݒ�
        RequestContext ctx = new RequestContext();
        String string = "abc";
        ctx.setProperty("arg1", string);
        target.setTestCtx(ctx);

        // �e�X�g���{
        try {
        	target.getProperty("arg1",Integer.class);
            fail();
        } catch (IllegalContextPropertyClassTypeException e) { 
            // ����
            return;
        }
    }
    
    /**
     * testGetPropertyString01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) key:"arg1"<br>
     * <br>
     * ���Ғl�F(�߂�l) String:getRequestContext().getProperty()�̖߂�l�Ɠ��������Ƃ��m�F����B<br>
     *         
     * <br>
     * �Ɩ��p�����[�^���擾���郁�\�b�h�̃e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetPropertyString01() throws Exception {
        // AbstractRequestContextSupport�����N���X�̐���
        AbstractRequestContextSupportImpl01 target = new AbstractRequestContextSupportImpl01();
        
        // RequestContext�̐ݒ�
        RequestContext ctx = new RequestContext();
        String string = "";
        ctx.setProperty("arg1", string);
        target.setTestCtx(ctx);

        // �e�X�g���{�E����
        assertSame(string, target.getPropertyString("arg1"));
    }
    
    /**
     * testGetPropertyString02()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) key:"arg1"<br>
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalContextPropertyClassTypeException<br>
     *         
     * <br>
     * �擾����Ɩ��v���p�e�B�̌^��String�^�łȂ��ꍇ�̃e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetPropertyString02() throws Exception {
        // AbstractRequestContextSupport�����N���X�̐���
        AbstractRequestContextSupportImpl01 target = new AbstractRequestContextSupportImpl01();
        
        // RequestContext�̐ݒ�
        RequestContext ctx = new RequestContext();
        Integer integer = new Integer(1);
        ctx.setProperty("arg1", integer);
        target.setTestCtx(ctx);

        
        // �e�X�g���{
        try {
        	target.getPropertyString("arg1");
            fail();
        } catch (IllegalContextPropertyClassTypeException e) {  
            // ����
            return;
        }
        
    }
    
    /**
     * testGenerateContext01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) request:not null<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) doGenerateContext():���\�b�h���Ăяo���ꂽ���Ƃ��m�F����B<br>
     *         (��ԕω�) RequestContextManager.bindRequestContext():���\�b�h���Ăяo���ꂽ���Ƃ��m�F����B<br>
     *         
     * <br>
     * ���N�G�X�g�R���e�L�X�g�𐶐����郁�\�b�h�̃e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGenerateContext01() throws Exception {
        // AbstractRequestContextSupport�����N���X�̐���
        AbstractRequestContextSupportImpl01 target = new AbstractRequestContextSupportImpl01();

        // RequestContext�̐ݒ�
        RequestContext ctx = new RequestContext();
        ctx.setRequestName("test");
        target.setTestCtx(ctx);

        // �����̐���
        MockHttpServletRequest request = new MockHttpServletRequest();

        // �e�X�g���{
        target.generateContext(request);

        // ����
        assertTrue(target.isDoGenerateContextCalled());
        assertSame(request, target.getDoGenerateContextArg());
        
        // ���s���ʂ������ČĂяo���m�F�Ƃ���
        assertSame(ctx, RequestContextManager.getRequestContext());
    }

    /**
     * testDestroyContext01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(���) RequestContextManager.hasRequestContext():true<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) RequestContextManager.unbindRequestContext():���\�b�h���Ăяo���ꂽ���Ƃ��m�F����B<br>
     *         
     * <br>
     * ���N�G�X�g�R���e�L�X�g���폜���郁�\�b�h�̃e�X�g�B���N�G�X�g�R���e�L�X�g����������Ă������߁A����ɍ폜�����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDestroyContext01() throws Exception {
        // RequestContextManager�̐ݒ�
        ThreadLocal<RequestContext> tlocal = new ThreadLocal<RequestContext>();
        RequestContext ctx = new RequestContext();
        ctx.setRequestName("test");
        tlocal.set(ctx);
        UTUtil.setPrivateField(
                RequestContextManager.class, "resources", tlocal);

        // AbstractRequestContextSupport�����N���X�̐���
        AbstractRequestContextSupportImpl02 target = new AbstractRequestContextSupportImpl02();

        // �e�X�g���{
        target.destroyContext();

        // ����
        assertFalse(RequestContextManager.hasRequestContext());
    }

    /**
     * testDestroyContext02()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(���) RequestContextManager.hasRequestContext():false<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) RequestContextManager.unbindRequestContext():���\�b�h���Ăяo����Ă��Ȃ����Ƃ��m�F����B<br>
     *         
     * <br>
     * ���N�G�X�g�R���e�L�X�g���폜���郁�\�b�h�̃e�X�g�B���N�G�X�g�R���e�L�X�g����������Ă��Ȃ��������߁A�������Ȃ��ŏI������B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDestroyContext02() throws Exception {
        // AbstractRequestContextSupport�����N���X�̐���
        AbstractRequestContextSupportImpl02 target = new AbstractRequestContextSupportImpl02();

        // �e�X�g���{
        target.destroyContext();

        // ����
        assertFalse(RequestContextManager.hasRequestContext());
        
        // ���̏�����unbindRequestContext()���Ă΂��΁A�G���[���O���o�͂����
        assertFalse(LogUTUtil.checkError("No RequestContext bound to thread!"));
    }

    /**
     * testGetRequestContext01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F
     * <br>
     * ���Ғl�F(�߂�l) RequestContextManager.getRequestContext()�̖߂�l�Ɠ��������Ƃ��m�F����B<br>
     *         
     * <br>
     * ���N�G�X�g�R���e�L�X�g���擾���郁�\�b�h�̃e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetRequestContext01() throws Exception {
        // RequestContextManager�̐ݒ�
        ThreadLocal<RequestContext> tlocal = new ThreadLocal<RequestContext>();
        RequestContext ctx = new RequestContext();
        ctx.setRequestName("test");
        tlocal.set(ctx);
        UTUtil.setPrivateField(
                RequestContextManager.class, "resources", tlocal);

        // AbstractRequestContextSupport�����N���X�̐���
        AbstractRequestContextSupportImpl02 target = new AbstractRequestContextSupportImpl02();

        // �e�X�g���{
        RequestContext result = target.getRequestContext();

        // ����
        assertSame(RequestContextManager.getRequestContext(), result);
    }

}
