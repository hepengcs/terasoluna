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

import org.springframework.web.context.support.GenericWebApplicationContext;
import org.springframework.web.servlet.view.AbstractUrlBasedView;

import jp.terasoluna.fw.oxm.mapper.OXMapper;
import jp.terasoluna.utlib.LogUTUtil;
import jp.terasoluna.utlib.UTUtil;
import jp.terasoluna.fw.web.rich.springmvc.servlet.view.castor.CastorView;
import jp.terasoluna.fw.web.rich.springmvc.servlet.view.castor.CastorViewResolver;
import junit.framework.TestCase;

/**
 * {@link jp.terasoluna.fw.web.rich.springmvc.servlet.view.castor.CastorViewResolver} �N���X�̃u���b�N�{�b�N�X�e�X�g�B
 * 
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4>
 * Castor�p��ViewResolver�����N���X�B<br>
 * �O������Fmodel�AHTTP���N�G�X�g�AHTTP���X�|���X��Null�l�ɂȂ�Ȃ��B
 * <p>
 * 
 * @see jp.terasoluna.fw.web.rich.springmvc.servlet.view.castor.CastorViewResolver
 */
public class CastorViewResolverTest extends TestCase {

    /**
     * ���̃e�X�g�P�[�X�����s����ׂ�
     * GUI �A�v���P�[�V�������N������B
     * 
     * @param args java �R�}���h�ɐݒ肳�ꂽ�p�����[�^
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(CastorViewResolverTest.class);
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
    public CastorViewResolverTest(String name) {
        super(name);
    }

    /**
     * testCastorViewResolver01()
     * <br><br>
     * 
     * (����n) 
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F
     * <br>
     * ���Ғl�F(��ԕω�) this.viewClass:CastorView�N���X���ݒ肳���B<br>
     *         
     * <br>
     * �R���X�g���N�^�̃e�X�g�B�g�p����r���[�N���X��ݒ肷��B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testCastorViewResolver01() throws Exception {
        // �O����

        // �e�X�g���{
        CastorViewResolver viewResolver = new CastorViewResolver();

        // ����
        Object result = UTUtil.getPrivateField(viewResolver, "viewClass");
        assertSame(CastorView.class, result);
    }

    /**
     * testSetOrder01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) order:not null<br>
     *         (���) this.order:Integer.MAX_VALUE<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) this.order:�����Őݒ肵���l<br>
     *         
     * <br>
     * order������setter���\�b�h�̃e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetOrder01() throws Exception {
        // �O����
        CastorViewResolver viewResolver = new CastorViewResolver();
        UTUtil.setPrivateField(viewResolver, "order", Integer.MAX_VALUE);

        // �e�X�g���{
        int order = 100;
        viewResolver.setOrder(order);

        // ����
        Object result = UTUtil.getPrivateField(viewResolver, "order");
        assertEquals(order, result);
    }

    /**
     * testGetOrder01()
     * <br><br>
     * 
     * (����n) 
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(���) this.order:not null<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) int:this.order<br>
     *         
     * <br>
     * order������getter���\�b�h�̃e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetOrder01() throws Exception {
        // �O����
        CastorViewResolver viewResolver = new CastorViewResolver();
        int order = 100;
        UTUtil.setPrivateField(viewResolver, "order", order);

        // �e�X�g���{
        Object result = viewResolver.getOrder();

        // ����
        assertEquals(order, result);
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
     * ���Ғl�F(�߂�l) int:this.oxmapper<br>
     *         
     * <br>
     * oxmapper������getter���\�b�h�̃e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetOxmapper01() throws Exception {
        // �O����
        CastorViewResolver viewResolver = new CastorViewResolver();
        OXMapper oxmapper = new CastorViewResolver_OXMapperImplStub01();
        UTUtil.setPrivateField(viewResolver, "oxmapper", oxmapper);

        // �e�X�g���{
        Object result = viewResolver.getOxmapper();

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
        CastorViewResolver viewResolver = new CastorViewResolver();
        UTUtil.setPrivateField(viewResolver, "oxmapper", null);

        // �e�X�g���{
        OXMapper oxmapper = new CastorViewResolver_OXMapperImplStub01();
        viewResolver.setOxmapper(oxmapper);

        // ����
        Object result = UTUtil.getPrivateField(viewResolver, "oxmapper");
        assertSame(oxmapper, result);
    }

    /**
     * testAfterPropertiesSet01()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(���) this.oxmapper:null<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalStateException<br>
     *                    �iOXMapper class isn't set in CastorViewResolver. 
     *                    Check Spring Bean definition file.�j<br>
     *         (��ԕω�) ���O:���O���x���Ferror<br>
     *                    OXMapper class isn't set in CastorViewResolver. 
     *                    Check Spring Bean definition file.<br>
     *         
     * <br>
     * OXMapper���ݒ肳��Ă��邩�`�F�b�N����e�X�g�B
     * Null��ݒ肵�A��O���������邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testAfterPropertiesSet01() throws Exception {
        // �O����
        CastorViewResolver viewResolver = new CastorViewResolver();
        UTUtil.setPrivateField(viewResolver, "oxmapper", null);

        // �e�X�g���{
        try {
            viewResolver.afterPropertiesSet();
            fail();
        } catch (IllegalStateException e) {
            String expect = "OXMapper class isn't set in CastorViewResolver. " +
                    "Check Spring Bean definition file.";
            assertEquals(expect, e.getMessage());
            assertTrue(LogUTUtil.checkError(expect));
        }
    }

    /**
     * testAfterPropertiesSet02()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(���) this.oxmapper:not null<br>
     *         
     * <br>
     * ���Ғl�F
     * <br>
     * OXMapper���ݒ肳��Ă��邩�`�F�b�N����e�X�g�B
     * OXMapper��ݒ肵�A��O���������Ȃ����Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testAfterPropertiesSet02() throws Exception {
        // �O����
        CastorViewResolver viewResolver = new CastorViewResolver();
        OXMapper oxmapper = new CastorViewResolver_OXMapperImplStub01();
        UTUtil.setPrivateField(viewResolver, "oxmapper", oxmapper);

        // �e�X�g���{
        try {
            viewResolver.afterPropertiesSet();
        } catch (Exception e) {
            fail();
        }
        // �i��O���������Ȃ����OK)
    }

    /**
     * testRequiredViewClass01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F
     * <br>
     * ���Ғl�F(�߂�l) Class:CastorView�N���X��Ԃ��B<br>
     *         
     * <br>
     * ���N���X���g�p����ׂ��r���[�N���X��ݒ肷�郁�\�b�h�B
     * �Œ��Castor�r���[�N���X��Ԃ��B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testRequiredViewClass01() throws Exception {
        // �O����
        CastorViewResolver viewResolver = new CastorViewResolver();

        // �e�X�g���{
        Class result = viewResolver.requiredViewClass();

        // ����
        assertSame(CastorView.class, result);
    }

    /**
     * testLoadView01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) viewName:not null<br>
     *                �i�󕶎��j<br>
     *         (����) locale:�g�p���Ȃ�<br>
     *         (���) buildView�i�j:not null<br>
     *                �iviewName���󕶎��̏ꍇ�ACastor�r���[��Ԃ��d�l�j<br>
     *         (���) this.oxmapper:not null<br>
     *                �i���̃��\�b�h�ł�Null�ɂȂ�Ȃ��j<br>
     *         (���) super.getApplicationContext():not null<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) View:Castor�r���[<br>
     *                  oxmapper�A�A�v���P�[�V�����R���e�L�X�g���ݒ肳��Ă���B<br>
     *         (��ԕω�) view.afterPropertiesSet():�Ăяo���m�F���s��<br>
     *         
     * <br>
     * �r���[���ɋ󕶎������ACastor�r���[���ԋp����邱�Ƃ��m�F����e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testLoadView01() throws Exception {
        // �O����
        GenericWebApplicationContext context = 
            new GenericWebApplicationContext();
        CastorViewResolver viewResolver = new CastorViewResolverImpl01();
        viewResolver.setApplicationContext(context);
        OXMapper oxmapper = new CastorViewResolver_OXMapperImplStub01();
        UTUtil.setPrivateField(viewResolver, "oxmapper", oxmapper);

        // �e�X�g���{
        String viewName = "";
        Object result = viewResolver.loadView(viewName, null);

        // ����
        assertSame(result.getClass(), CastorViewResolver_CastorViewStub01.class);
        CastorViewResolver_CastorViewStub01 resultView = 
            (CastorViewResolver_CastorViewStub01) result;
        assertSame(oxmapper, resultView.getOxmapper());
        assertTrue(resultView.isAfterPropertiesSet);
    }

    /**
     * testLoadView02()
     * <br><br>
     * 
     * (����n) 
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) viewName:not null<br>
     *                �i�P�����ȏ�j<br>
     *         (����) locale:�g�p���Ȃ�<br>
     *         (���) buildView�i�j:null<br>
     *                �iviewName���P�����ȏ�̏ꍇ�ANull��Ԃ��d�l�j<br>
     *         (���) this.oxmapper:not null<br>
     *                �i���̃��\�b�h�ł�Null�ɂȂ�Ȃ��j<br>
     *         (���) super.getApplicationContext():not null<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) View:null<br>
     *         (��ԕω�) -<br>
     *         
     * <br>
     * �r���[���ɂP�����ȏ�̕���������ANull���ԋp����邱�Ƃ��m�F����e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testLoadView02() throws Exception {
        // �O����
        GenericWebApplicationContext context = 
            new GenericWebApplicationContext();
        CastorViewResolver viewResolver = new CastorViewResolver();
        viewResolver.setApplicationContext(context);
        OXMapper oxmapper = new CastorViewResolver_OXMapperImplStub01();
        UTUtil.setPrivateField(viewResolver, "oxmapper", oxmapper);

        // �e�X�g���{
        String viewName = "viewName";
        Object result = viewResolver.loadView(viewName, null);

        // ����
        assertNull(result);
    }

    /**
     * testLoadView03()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) viewName:null<br>
     *         (����) locale:�g�p���Ȃ�<br>
     *         (���) buildView�i�j:not null<br>
     *                �iviewName��Null�̏ꍇ�ACastor�r���[��Ԃ��d�l�j<br>
     *         (���) this.oxmapper:not null<br>
     *                �i���̃��\�b�h�ł�Null�ɂȂ�Ȃ��j<br>
     *         (���) super.getApplicationContext():not null<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) View:Castor�r���[<br>
     *                  oxmapper�A�A�v���P�[�V�����R���e�L�X�g���ݒ肳��Ă���B<br>
     *         (��ԕω�) view.afterPropertiesSet():�Ăяo���m�F���s��<br>
     *         
     * <br>
     * �r���[����Null�����ACastor�r���[���ԋp����邱�Ƃ��m�F����e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testLoadView03() throws Exception {
        // �O����
        GenericWebApplicationContext context = 
            new GenericWebApplicationContext();
        CastorViewResolver viewResolver = new CastorViewResolverImpl01();
        viewResolver.setApplicationContext(context);
        OXMapper oxmapper = new CastorViewResolver_OXMapperImplStub01();
        UTUtil.setPrivateField(viewResolver, "oxmapper", oxmapper);

        // �e�X�g���{
        String viewName = null;
        Object result = viewResolver.loadView(viewName, null);

        // ����
        assertTrue(result.getClass() == CastorViewResolver_CastorViewStub01.class);
        CastorViewResolver_CastorViewStub01 resultView = 
            (CastorViewResolver_CastorViewStub01) result;
        assertSame(oxmapper, resultView.getOxmapper());
        assertTrue(resultView.isAfterPropertiesSet);
    }

    /**
     * testBuildView01()
     * <br><br>
     * 
     * (����n) 
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) viewName:not null<br>
     *                �i�󕶎��j<br>
     *         (���) super.buildView():Castor�r���[���쐬����B<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) AbstractUrlBasedView:Castor�r���[<br>
     *         
     * <br>
     * �r���[���ɋ󕶎������ACastor�r���[���ԋp����邱�Ƃ��m�F����e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testBuildView01() throws Exception {
        // �O����
        CastorViewResolver viewResolver = new CastorViewResolver();

        // �e�X�g���{
        String viewName = "";
        AbstractUrlBasedView result = viewResolver.buildView(viewName);

        // ����
        assertSame(CastorView.class, result.getClass());
    }

    /**
     * testBuildView02()
     * <br><br>
     * 
     * (����n) 
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) viewName:not null<br>
     *                �i�P�����ȏ�j<br>
     *         (���) super.buildView():Castor�r���[���쐬����B<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) AbstractUrlBasedView:null<br>
     *         
     * <br>
     * �r���[���ɂP�����ȏ�̕���������ANull���ԋp����邱�Ƃ��m�F����e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testBuildView02() throws Exception {
        // �O����
        CastorViewResolver viewResolver = new CastorViewResolver();

        // �e�X�g���{
        String viewName = "viewName";
        AbstractUrlBasedView result = viewResolver.buildView(viewName);

        // ����
        assertNull(result);
    }

    /**
     * testBuildView03()
     * <br><br>
     * 
     * (����n) 
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) viewName:null<br>
     *         (���) super.buildView():Castor�r���[���쐬����B<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) AbstractUrlBasedView:Castor�r���[<br>
     *         
     * <br>
     * �r���[����Null�����ACastor�r���[���ԋp����邱�Ƃ��m�F����e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testBuildView03() throws Exception {
        // �O����
        CastorViewResolver viewResolver = new CastorViewResolver();

        // �e�X�g���{
        String viewName = null;
        AbstractUrlBasedView result = viewResolver.buildView(viewName);

        // ����
        assertSame(CastorView.class, result.getClass());
    }

}
