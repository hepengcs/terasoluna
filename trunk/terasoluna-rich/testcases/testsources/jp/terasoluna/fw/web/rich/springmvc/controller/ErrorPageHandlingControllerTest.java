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

package jp.terasoluna.fw.web.rich.springmvc.controller;

import jp.terasoluna.utlib.MockHttpServletRequest;
import jp.terasoluna.utlib.MockHttpServletResponse;
import jp.terasoluna.fw.web.rich.springmvc.controller.ErrorPageHandlingController;
import junit.framework.TestCase;

/**
 * {@link jp.terasoluna.fw.web.rich.springmvc.controller.ErrorPageHandlingController} �N���X�̃u���b�N�{�b�N�X�e�X�g�B
 * 
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4>
 * web.xml��error-page�ݒ�ɂ���O�n���h�����O���s���Ƃ��Ɏg�p����R���g���[���B
 * <p>
 * 
 * @see jp.terasoluna.fw.web.rich.springmvc.controller.ErrorPageHandlingController
 */
public class ErrorPageHandlingControllerTest extends TestCase {

    /**
     * ���̃e�X�g�P�[�X�����s����ׂ�
     * GUI �A�v���P�[�V�������N������B
     * 
     * @param args java �R�}���h�ɐݒ肳�ꂽ�p�����[�^
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(ErrorPageHandlingControllerTest.class);
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
    public ErrorPageHandlingControllerTest(String name) {
        super(name);
    }

    /**
     * testHandleRequest01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) request:not null<br>
     *                (javax.servlet.error.exception={Exception�I�u�W�F�N�g}�j<br>
     *         (����) response:not null<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) -:�����ŕێ����Ă���Exception�I�u�W�F�N�g<br>
     *         
     * <br>
     * handleRequest���\�b�h�̃e�X�g�B���N�G�X�g���ŕێ����Ă����O�𓊂���B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testHandleRequest01() throws Exception {
        // �O����
        ErrorPageHandlingController controller = new ErrorPageHandlingController();
        Exception exception = new Exception();
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setAttribute("javax.servlet.error.exception", exception);
        MockHttpServletResponse response = new MockHttpServletResponse();

        // �e�X�g���{
        try {
            controller.handleRequest(request, response);
            fail();
        } catch(IllegalStateException e) {
            fail();
        } catch(Exception e) {
            assertSame(exception, e);
        }
    }

    /**
     * testHandleRequest02()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FA,G
     * <br><br>
     * ���͒l�F(����) request:not null<br>
     *         (����) response:not null<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalStateException<br>
     *         
     * <br>
     * handleRequest���\�b�h�̃e�X�g�B���N�G�X�g���ŕێ����Ă����O���Ȃ����߁A<br>
     * IllegalStateException���X���[����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testHandleRequest02() throws Exception {
        // �O����
        ErrorPageHandlingController controller = new ErrorPageHandlingController();
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        // �e�X�g���{
        try {
            controller.handleRequest(request, response);
            fail();
        } catch(IllegalStateException e) {
            // OK
        	return;
        }
    }

}
