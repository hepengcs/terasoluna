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

import jp.terasoluna.utlib.MockHttpServletRequest;
import jp.terasoluna.utlib.UTUtil;
import jp.terasoluna.fw.web.rich.springmvc.servlet.handler.ErrorPageHandlerMapping;
import junit.framework.TestCase;

/**
 * {@link jp.terasoluna.fw.web.rich.springmvc.servlet.handler.ErrorPageHandlerMapping} �N���X�̃u���b�N�{�b�N�X�e�X�g�B
 * 
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4>
 * ���N�G�X�g���ێ������O�̗L���ɂ���ăR���g���[����Ԃ��n���h���B<br>
 * �O������F
 * <p>
 * 
 * @see jp.terasoluna.fw.web.rich.springmvc.servlet.handler.ErrorPageHandlerMapping
 */
public class ErrorPageHandlerMappingTest extends TestCase {

    /**
     * ���̃e�X�g�P�[�X�����s����ׂ�
     * GUI �A�v���P�[�V�������N������B
     * 
     * @param args java �R�}���h�ɐݒ肳�ꂽ�p�����[�^
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(ErrorPageHandlerMappingTest.class);
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
    public ErrorPageHandlerMappingTest(String name) {
        super(name);
    }

    /**
     * testGetBeanId01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(���) this.beanId:"/testController"<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) -:"/testController"<br>
     *         
     * <br>
     * beanId������get���\�b�h�̃e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetBeanId01() throws Exception {
        // �O����
        ErrorPageHandlerMapping hm = new ErrorPageHandlerMapping();
        UTUtil.setPrivateField(hm, "beanId", "/testController");

        // �e�X�g���{
        assertEquals("/testController", hm.getBeanId());
    }

    /**
     * testSetBeanId01()
     * <br><br>
     * 
     * (����n) 
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) beanId:"/testController"<br>
     *         (���) this.beanId:null<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) this.beanId:"/testController"<br>
     *         
     * <br>
     * beanId������set���\�b�h�̃e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetBeanId01() throws Exception {
        // �O����
        ErrorPageHandlerMapping hm = new ErrorPageHandlerMapping();

        // �e�X�g���{
        hm.setBeanId("/testController");

        // ����
        assertEquals("/testController", UTUtil.getPrivateField(hm, "beanId"));
    }

    /**
     * testGetHandlerInternal01()
     * <br><br>
     * 
     * (����n) 
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) request:not null<br>
     *                (javax.servlet.error.exception={Exception�I�u�W�F�N�g}�j<br>
     *         (���) this.handlerMap:Map{<br>
     *                  "/exceptionController"=�I�u�W�F�N�g<br>
     *                }<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) Object:this.handlerMap�ŕێ����Ă���I�u�W�F�N�g<br>
     *         
     * <br>
     * ���N�G�X�g�ŗ�O��ێ����Ă������߁A<br>
     * ��O�p�R���g���[����Ԃ��p�^�[���B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testGetHandlerInternal01() throws Exception {
        // �O����
        ErrorPageHandlerMapping hm = new ErrorPageHandlerMapping();
        
        // �n���h���}�b�s���O�̃v���p�e�B
        Map handlerMap = new HashMap();
        Object object = new Object();
        handlerMap.put("/exceptionController", object);
        UTUtil.setPrivateField(hm, "handlerMap", handlerMap);
        
        // ���N�G�X�g
        Exception exception = new Exception();
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setAttribute("javax.servlet.error.exception", exception);

        // �e�X�g���{
        assertSame(object, hm.getHandlerInternal(request));
    }

    /**
     * testGetHandlerInternal02()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) request:not null<br>
     *                (javax.servlet.error.exception={Exception�I�u�W�F�N�g}�j<br>
     *         (���) this.handlerMap:Map{}<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) Object:null<br>
     *         
     * <br>
     * ���N�G�X�g�ŗ�O��ێ����Ă������A<br>
     * ��O�p�R���g���[�����Ȃ������p�^�[���B<br>
     * Null��Ԃ��B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetHandlerInternal02() throws Exception {
        // �O����
        ErrorPageHandlerMapping hm = new ErrorPageHandlerMapping();
        
        // ���N�G�X�g
        Exception exception = new Exception();
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setAttribute("javax.servlet.error.exception", exception);

        // �e�X�g���{
        assertNull(hm.getHandlerInternal(request));
    }

    /**
     * testGetHandlerInternal03()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) request:not null<br>
     *         (���) this.handlerMap:Map{
     *         "/exceptionController"=�I�u�W�F�N�g}<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) Object:null<br>
     *         
     * <br>
     * ���N�G�X�g�ŗ�O���������Ă��Ȃ������p�^�[���BNull��Ԃ��B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testGetHandlerInternal03() throws Exception {
        // �O����
        ErrorPageHandlerMapping hm = new ErrorPageHandlerMapping();
        
        // �n���h���}�b�s���O�̃v���p�e�B
        Map handlerMap = new HashMap();
        Object object = new Object();
        handlerMap.put("/exceptionController", object);
        UTUtil.setPrivateField(hm, "handlerMap", handlerMap);
        
        // ���N�G�X�g
        MockHttpServletRequest request = new MockHttpServletRequest();

        // �e�X�g���{
        assertNull(hm.getHandlerInternal(request));
    }

}
