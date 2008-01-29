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
import jp.terasoluna.fw.web.rich.springmvc.controller.UnkownRequestNameController;
import jp.terasoluna.fw.web.rich.springmvc.exception.UnknownRequestNameException;
import junit.framework.TestCase;

/**
 * {@link jp.terasoluna.fw.web.rich.springmvc.controller.
 * UnkownRequestNameController} �N���X�̃u���b�N�{�b�N�X�e�X�g�B
 * 
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4>
 * �s���ȃ��N�G�X�g�������N�G�X�g����Ă����ꍇ�Ɏ��s�����
 * ���N�G�X�g�R���g���[���B<br>
 * �O������FHTTP���N�G�X�g�AHTTP���X�|���X��Null�l�ɂȂ�Ȃ��B
 * <p>
 * 
 * @see jp.terasoluna.fw.web.rich.springmvc.controller.
 * UnkownRequestNameController
 */
public class UnkownRequestNameControllerTest extends TestCase {

    /**
     * ���̃e�X�g�P�[�X�����s����ׂ�
     * GUI �A�v���P�[�V�������N������B
     * 
     * @param args java �R�}���h�ɐݒ肳�ꂽ�p�����[�^
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(UnkownRequestNameControllerTest.class);
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
    public UnkownRequestNameControllerTest(String name) {
        super(name);
    }

    /**
     * testHandleRequest01()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) request:�g�p���Ȃ�<br>
     *         (����) response:�g�p���Ȃ�<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) ��O:UnknownRequestNameException<br>
     *         
     * <br>
     * ���N�G�X�g�R���g���[����������Ȃ��ꍇ�ɌĂ΂��R���g���[���̃e�X�g�B
     * �K����O�𔭐�������B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testHandleRequest01() throws Exception {
        // �O����
        UnkownRequestNameController controller = 
            new UnkownRequestNameController();
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        // �e�X�g���{
        try {
            controller.handleRequest(request, response);
            fail();
        } catch (UnknownRequestNameException e) {
            // OK
        	return;
        }
    }
}
