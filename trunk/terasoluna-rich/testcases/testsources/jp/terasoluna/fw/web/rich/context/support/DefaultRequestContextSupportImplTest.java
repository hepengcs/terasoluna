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

import jp.terasoluna.utlib.MockHttpServletRequest;
import jp.terasoluna.utlib.UTUtil;
import jp.terasoluna.fw.web.rich.context.RequestContext;
import jp.terasoluna.fw.web.rich.context.support.DefaultRequestContextSupportImpl;
import junit.framework.TestCase;

/**
 * {@link jp.terasoluna.fw.web.rich.context.support.DefaultRequestContextSupportImpl} �N���X�̃u���b�N�{�b�N�X�e�X�g�B
 * 
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4>
 * ��������������߂̕⏕���W�b�N�̃f�t�H���g�����N���X�B<br>
 * �O������FHTTP���N�G�X�g��Null�l�ɂȂ�Ȃ��B
 * <p>
 * 
 * @see jp.terasoluna.fw.web.rich.context.support.DefaultRequestContextSupportImpl
 */
public class DefaultRequestContextSupportImplTest extends TestCase {

    /**
     * ���̃e�X�g�P�[�X�����s����ׂ�
     * GUI �A�v���P�[�V�������N������B
     * 
     * @param args java �R�}���h�ɐݒ肳�ꂽ�p�����[�^
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(DefaultRequestContextSupportImplTest.class);
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
    public DefaultRequestContextSupportImplTest(String name) {
        super(name);
    }

    /**
     * testSetRequestNameHeaderKey01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) requestName:"command"<br>
     *         (���) this.requestNameHeaderKey:"requestName"<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) this.requestNameHeaderKey:"command"<br>
     *         
     * <br>
     * ���N�G�X�g����ێ����郊�N�G�X�g�w�b�_����ݒ肷�鑮����setter���\�b�h�̃e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetRequestNameHeaderKey01() throws Exception {
        // �e�X�g���{
        DefaultRequestContextSupportImpl target = new DefaultRequestContextSupportImpl();
        target.setRequestNameHeaderKey("command");

        // ����
        assertEquals("command", UTUtil.getPrivateField(target, "requestNameHeaderKey"));
    }

    /**
     * testDoGenerateContext01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) request:���N�G�X�g�w�b�_<br>
     *                requestName=sum<br>
     *         (���) this.requestName:"requestName"<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) RequestContext:RequestContext�o<br>
     *                  �@requestName="sum"<br>
     *                  �p<br>
     *         (��ԕω�) getBLogicContext().getPropertyString():���\�b�h���Ăяo���ꂽ���Ƃ��m�F����B<br>
     *         
     * <br>
     * ���N�G�X�g�R���e�L�X�g�𐶐����郁�\�b�h�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDoGenerateContext01() throws Exception {
        // �O����
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setHeader("requestName", "sum");
        
        // �e�X�g���{
        DefaultRequestContextSupportImpl target = new DefaultRequestContextSupportImpl();
        RequestContext result = target.doGenerateContext(request);

        // ����
        assertEquals("sum", result.getRequestName());
    }

}
