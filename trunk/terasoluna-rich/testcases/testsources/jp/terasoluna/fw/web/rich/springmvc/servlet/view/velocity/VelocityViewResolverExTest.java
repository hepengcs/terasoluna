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

package jp.terasoluna.fw.web.rich.springmvc.servlet.view.velocity;

import org.springframework.web.servlet.view.velocity.VelocityView;

import jp.terasoluna.utlib.UTUtil;
import jp.terasoluna.fw.web.rich.springmvc.servlet.view.velocity.VelocityViewResolverEx;
import junit.framework.TestCase;

/**
 * {@link jp.terasoluna.fw.web.rich.springmvc.servlet.view.velocity.
 * VelocityViewResolverEx} �N���X�̃u���b�N�{�b�N�X�e�X�g�B
 * 
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4>
 * �G���R�[�f�B���O�w�肪�\��VelocityViewResolver�g���N���X�B<br>
 * �O������FbuidlView���\�b�h�̈���viewName�ɂ͋󕶎��ANull�͓���Ȃ��B
 * <p>
 * 
 * @see jp.terasoluna.fw.web.rich.springmvc.servlet.view.velocity.
 * VelocityViewResolverEx
 */
public class VelocityViewResolverExTest extends TestCase {

    /**
     * ���̃e�X�g�P�[�X�����s����ׂ�
     * GUI �A�v���P�[�V�������N������B
     * 
     * @param args java �R�}���h�ɐݒ肳�ꂽ�p�����[�^
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(VelocityViewResolverExTest.class);
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
    public VelocityViewResolverExTest(String name) {
        super(name);
    }

    /**
     * testGetEncoding01()
     * <br><br>
     * 
     * (����n) 
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(���) this.encoding:not null<br>
     *                (�htext/xml;charset=UTF-8�h)<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) String:�O������Ŏw�肵��this.encoding�̒l�B<br>
     *         
     * <br>
     * encoding������get���\�b�h�̃e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetEncoding01() throws Exception {
        // �O����
        VelocityViewResolverEx viewResolver = new VelocityViewResolverEx();
        String encoding = "text/xml;charset=UTF-8";
        UTUtil.setPrivateField(viewResolver, "encoding", encoding);

        // �e�X�g���{
        Object result = viewResolver.getEncoding();

        // ����
        assertEquals(encoding, result);
    }

    /**
     * testSetEncoding01()
     * <br><br>
     * 
     * (����n) 
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) encoding:not null<br>
     *                (�htext/xml;charset=UTF-8�h)<br>
     *         (���) this.encoding:null<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) this.encoding:�����Őݒ肵���l���ݒ肳��Ă���B<br>
     *         
     * <br>
     * encoding������set���\�b�h�̃e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetEncoding01() throws Exception {
        // �O����
        VelocityViewResolverEx viewResolver = new VelocityViewResolverEx();
        UTUtil.setPrivateField(viewResolver, "encoding", null);

        // �e�X�g���{
        String encoding = "text/xml;charset=UTF-8";
        viewResolver.setEncoding(encoding);

        // ����
        Object result = UTUtil.getPrivateField(viewResolver, "encoding");
        assertEquals(encoding, result);
    }

    /**
     * testBuildView01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) viewName:�hsum�h<br>
     *         (���) this.encoding:not null<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) AbstractUrlBasedView:
     *         �O�������encoding���ݒ肳�ꂽVelocity�r���[<br>
     *         (��ԕω�) super.buildView():
     *         ���\�b�h���Ăяo���ꂽ���Ƃ��m�F����B<br>
     *                    �������󂯎�������Ƃ��m�F����B<br>
     *         
     * <br>
     * Velocity�r���[�̐���n�e�X�g�B�G���R�[�f�B���O���w�肳��Ă��邩�m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testBuildView01() throws Exception {
        // �O����
        VelocityViewResolverEx viewResolver = new VelocityViewResolverEx();
        String encoding = "text/xml;charset=UTF-8";
        UTUtil.setPrivateField(viewResolver, "encoding", encoding);

        // �e�X�g���{
        String viewName = "sum";
        Object result = viewResolver.buildView(viewName);

        // ����
        assertTrue(result instanceof VelocityView);
        VelocityView resultView = (VelocityView) result;
        assertEquals(encoding, UTUtil.getPrivateField(resultView, "encoding"));
        assertEquals(viewName, UTUtil.getPrivateField(resultView, "url"));
    }

    /**
     * testBuildView02()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) viewName:�hsum�h<br>
     *         (���) this.encoding:null<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) AbstractUrlBasedView:
     *         �O�������encoding���ݒ肳�ꂽVelocity�r���[<br>
     *         (��ԕω�) super.buildView():
     *         ���\�b�h���Ăяo���ꂽ���Ƃ��m�F����B<br>
     *                    �������󂯎�������Ƃ��m�F����B<br>
     *         
     * <br>
     * Velocity�r���[�̃G���R�[�f�B���O��Null�̏ꍇ�̃e�X�g�B
     * �G���R�[�f�B���ONull��Velocity�r���[���쐬�����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testBuildView02() throws Exception {
        // �O����
        VelocityViewResolverEx viewResolver = new VelocityViewResolverEx();
        UTUtil.setPrivateField(viewResolver, "encoding", null);

        // �e�X�g���{
        String viewName = "sum";
        Object result = viewResolver.buildView(viewName);

        // ����
        assertTrue(result instanceof VelocityView);
        VelocityView resultView = (VelocityView) result;
        assertEquals(null, UTUtil.getPrivateField(resultView, "encoding"));
        assertEquals(viewName, UTUtil.getPrivateField(resultView, "url"));
    }

    /**
     * testBuildView03()
     * <br><br>
     * 
     * (����n) 
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) viewName:�hsum�h<br>
     *         (���) this.encoding:not null<br>
     *                �i�󕶎��j<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) AbstractUrlBasedView:
     *         �O�������encoding���ݒ肳�ꂽVelocity�r���[<br>
     *         (��ԕω�) super.buildView():
     *         ���\�b�h���Ăяo���ꂽ���Ƃ��m�F����B<br>
     *                    �������󂯎�������Ƃ��m�F����B<br>
     *         
     * <br>
     * Velocity�r���[�̃G���R�[�f�B���O���󕶎��̏ꍇ�̃e�X�g�B
     * �G���R�[�f�B���O���󕶎���Velocity�r���[���쐬�����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testBuildView03() throws Exception {
        // �O����
        VelocityViewResolverEx viewResolver = new VelocityViewResolverEx();
        String encoding = "";
        UTUtil.setPrivateField(viewResolver, "encoding", encoding);

        // �e�X�g���{
        String viewName = "sum";
        Object result = viewResolver.buildView(viewName);

        // ����
        assertTrue(result instanceof VelocityView);
        VelocityView resultView = (VelocityView) result;
        assertEquals(encoding, UTUtil.getPrivateField(resultView, "encoding"));
        assertEquals(viewName, UTUtil.getPrivateField(resultView, "url"));
    }

}
