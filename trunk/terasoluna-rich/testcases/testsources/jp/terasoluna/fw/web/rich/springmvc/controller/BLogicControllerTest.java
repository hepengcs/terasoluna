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

import jp.terasoluna.fw.service.rich.BLogic;
import jp.terasoluna.fw.web.rich.context.support.DefaultRequestContextSupportImpl;
import jp.terasoluna.utlib.LogUTUtil;
import jp.terasoluna.utlib.UTUtil;
import jp.terasoluna.fw.web.rich.springmvc.bind.creator.XMLServletRequestDataBinderCreator;
import jp.terasoluna.fw.web.rich.springmvc.controller.BLogicController;
import junit.framework.TestCase;

/**
 * {@link jp.terasoluna.fw.web.rich.springmvc.controller.BLogicController} �N���X�̃u���b�N�{�b�N�X�e�X�g�B
 * 
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4>
 * BLogic�C���^�t�F�[�X�����N���X���s�p���N�G�X�g�R���g���[���B<br>
 * �O������Fblogic��null�ɂȂ�Ȃ�
 * <p>
 * 
 * @see jp.terasoluna.fw.web.rich.springmvc.controller.BLogicController
 */
public class BLogicControllerTest extends TestCase {

    /**
     * ���̃e�X�g�P�[�X�����s����ׂ�
     * GUI �A�v���P�[�V�������N������B
     * 
     * @param args java �R�}���h�ɐݒ肳�ꂽ�p�����[�^
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(BLogicControllerTest.class);
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
    public BLogicControllerTest(String name) {
        super(name);
    }

    /**
     * testSetBlogic01()
     * <br><br>
     * 
     * (����n) 
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) blogic:Blogic�����N���X<br>
     *         (���) this.blogic:null<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) this.blogic:�����Ŏw�肵��blogic<br>
     *         
     * <br>
     * blogic������Setter���\�b�h�̃e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetBlogic01() throws Exception {
        // �O���� 
        BLogicController controller = new BLogicController();
        BLogic<Object, Object> blogic = new BLogicController_BLogicImplStub01();
        UTUtil.setPrivateField(controller, "blogic", null);
        
        // �e�X�g���{ 
        controller.setBlogic(blogic);

        // ���� 
        assertSame(blogic, UTUtil.getPrivateField(controller, "blogic"));
    }

    /**
     * testAfterPropertiesSet01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(���) this.dataBinderCreator:null<br>
     *         (���) this.ctxSupport:null<br>
     *         (���) this.blogic:null<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalStateException("DataBinderCreator is Null.")<br>
     *         (��ԕω�) ���O:���O���x���F�G���[DataBinderCreator is Null.<br>
     *         
     * <br>
     * �e�N���X�̃��\�b�h���Ă΂�Ă��邩�m�F����e�X�g�B
     * �f�[�^�o�C���_�����N���X���ݒ肳��Ă��Ȃ����ߗ�O�𔭐�������B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testAfterPropertiesSet01() throws Exception {
        // �O���� 
        BLogicController controller = new BLogicController();
        UTUtil.setPrivateField(controller, "dataBinderCreator", null);
        UTUtil.setPrivateField(controller, "ctxSupport", null);
        UTUtil.setPrivateField(controller, "blogic", null);
        
        // �e�X�g���{
        try {
            controller.afterPropertiesSet();
            fail();
        } catch (IllegalStateException e) {
            // OK
            String expect = "DataBinderCreator is Null.";
            assertEquals(expect, e.getMessage());
            LogUTUtil.checkError(expect);
        }
    }

    /**
     * testAfterPropertiesSet02()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(���) this.dataBinderCreator:not null<br>
     *         (���) this.ctxSupport:not null<br>
     *         (���) this.blogic:null<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalStateException<br>
     *                    �i�hCannot create BLogicController without blogic(BLogic) being set. 
     *                    Check Bean definition file."�j<br>
     *         (��ԕω�) ���O:���O���x���F�G���[<br>
     *                    Cannot create BLogicController without blogic(BLogic) being set. 
     *                    Check Bean definition file.<br>
     *         
     * <br>
     * �C���X�^���X����DI�R���e�i���Ă΂�郁�\�b�h�B
     * blogic��Null�ł��邽�߁A��O����������p�^�[���B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testAfterPropertiesSet02() throws Exception {
        // �O���� 
        BLogicController controller = new BLogicController();
        UTUtil.setPrivateField(controller, "dataBinderCreator", 
                new XMLServletRequestDataBinderCreator());
        UTUtil.setPrivateField(controller, "ctxSupport", 
                new DefaultRequestContextSupportImpl());
        UTUtil.setPrivateField(controller, "blogic", null);
        
        // �e�X�g���{
        try {
            controller.afterPropertiesSet();
            fail();
        } catch (IllegalStateException e) {
            // OK
            String expect = "Cannot create BLogicController without blogic(BLogic) being set. "
                + "Check Bean definition file.";
            assertEquals(expect, e.getMessage());
            LogUTUtil.checkError(expect);
        }
    }

    /**
     * testAfterPropertiesSet03()
     * <br><br>
     * 
     * (����n) 
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(���) this.dataBinderCreator:not null<br>
     *         (���) this.ctxSupport:not null<br>
     *         (���) this.blogic:not null<br>
     *         
     * <br>
     * ���Ғl�F
     * <br>
     * �C���X�^���X����DI�R���e�i���Ă΂�郁�\�b�h�B
     * ����ɑ������ݒ肳��Ă��邽�߁A�������������Ȃ��B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testAfterPropertiesSet03() throws Exception {
        // �O���� 
        BLogicController controller = new BLogicController();
        UTUtil.setPrivateField(controller, "dataBinderCreator", 
                new XMLServletRequestDataBinderCreator());
        UTUtil.setPrivateField(controller, "ctxSupport", 
                new DefaultRequestContextSupportImpl());
        UTUtil.setPrivateField(controller, "blogic", 
                new BLogicController_BLogicImplStub01());
        
        // �e�X�g���{
        controller.afterPropertiesSet();
        
        // OK
    }

    /**
     * testGetCommandType01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(���) this.blogic:BlogicImp implements Blogic<Integer, Long><br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) Type:Integer<br>
     *         
     * <br>
     * Blogic���^�p�����[�^���`���Ă��鐳��P�[�X�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
//    BLogicController���g���������ߍ폜
//    public void testGetCommandType01() throws Exception {
//        // �O���� 
//        BLogicController controller = new BLogicController();
//        BLogic<Integer, Long> blogic = new BLogicController_BLogicImplStub02();
//        UTUtil.setPrivateField(controller, "blogic", blogic);
//        
//        // �e�X�g���{ 
//        Type result = controller.getCommandType();
//
//        // ���� 
//        assertSame(Integer.class, result);
//    }

    /**
     * testGetCommandType02()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(���) this.blogic:Proxy�C���X�^���X<br>
     *                �E���b�v���Ă���Blogic<br>
     *                BlogicImp implements Blogic<Integer, Long><br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) Type:Integer<br>
     *         
     * <br>
     * �v���L�V��Blogic�����b�v����Ă���p�^�[���̃e�X�g�B
     * ���b�v����Ă���Blogic�̃p�����[�^���擾����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
//  BLogicController���g���������ߍ폜
//    public void testGetCommandType02() throws Exception {
//        // �O���� 
//        BLogicController controller = new BLogicController();
//        ProxyFactory pf = new ProxyFactory(new BLogicController_BLogicImplStub02());
//        BLogic blogic = (BLogic) pf.getProxy();
//        UTUtil.setPrivateField(controller, "blogic", blogic);
        
        // �e�X�g���{ 
//        Type result = controller.getCommandType();

        // ���� 
//        assertSame(Integer.class, result);
//    }

    /**
     * testGetCommandType03()
     * <br><br>
     * 
     * (����n) 
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(���) this.blogic:BlogicImp implements Blogic<Integer, Long> , <br>
     *                Iterator<Float>,<br>
     *                List<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) Type:Integer<br>
     *         
     * <br>
     * Blogic���R�̃C���^�t�F�[�X���������Ă���p�^�[���B
     * �^�p�����[�^���`���Ă���C���^�t�F�[�X���������B
     * Blogic�̌^�p�����[�^��Ԃ��B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
//    public void testGetCommandType03() throws Exception {
    
    // FW���P�ɂ��폜
    //  �R�}���h�^�̎擾���@���A�^�p�����[�^�ł͂Ȃ��A
    //  execute���\�b�h�̃p�����[�^�Ŏ擾����悤�ύX�������߁A�폜
    
    
//        // �O���� 
//        BLogicController controller = new BLogicController();
//        BLogic<Integer, Long> blogic = new BLogicController_BLogicImplStub03();
//        UTUtil.setPrivateField(controller, "blogic", blogic);
//        
//        // �e�X�g���{ 
//        Type result = controller.getCommandType();
//
//        // ���� 
//        assertSame(Integer.class, result);
//    }

    /**
     * testGetCommandType04()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(���) this.blogic:BlogicImp implements Blogic<br>
     *                (�^�p�����[�^�w��Ȃ��j<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalStateException<br>
     *                    ("Cannot get BLogic<P, R> parameter P.")<br>
     *         (��ԕω�) ���O:���O���x��:�G���[<br>
     *                    Cannot get BLogic<P, R> parameter P.<br>
     *         
     * <br>
     * Blogic���^�p�����[�^���`���Ă��Ȃ��p�^�[���B��O���X���[����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
//  BLogicController���g���������ߍ폜
//    public void testGetCommandType04() throws Exception {
//        // �O���� 
//        BLogicController controller = new BLogicController();
//        BLogic blogic = new BLogicController_BLogicImplStub04();
//        UTUtil.setPrivateField(controller, "blogic", blogic);
//        
//        // �e�X�g���{ 
//        try {
//            controller.getCommandType();
//            fail();
//        } catch (IllegalStateException e) {
//            // OK
//            String expect = "Cannot get Command type. "
//                + "execute(P) method argument P must not use Object class.";
//            assertEquals(expect, e.getMessage());
//            assertTrue(LogUTUtil.checkError(expect));
//        }
//    }
    
    /**
     * testGetCommandType05()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(���) this.blogic:�E���ۃN���X���p������Blogic<br>
     *                public class BLogicController_BLogicImplStub06<br>
     *                    extends BLogicController_BLogicImplStub05<Integer, Long><br>
     *                public abstract class BLogicController_BLogicImplStub05<P, R><br>
     *                    extends AbstractBLogic<P, R><br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) Type:Integer<br>
     *         
     * <br>
     * Blogic�����ۃN���X���p�����Ă���p�^�[���B�������^�p�����[�^���擾����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
//  BLogicController���g���������ߍ폜
//    public void testGetCommandType05() throws Exception {
//        // �O���� 
//        BLogicController controller = new BLogicController();
//        BLogic<Integer, Long> blogic = new BLogicController_BLogicImplStub06();
//        UTUtil.setPrivateField(controller, "blogic", blogic);
//        
//        // �e�X�g���{ 
//        Type result = controller.getCommandType();
//
//        // ���� 
//        assertSame(Integer.class, result);
//    }

    /**
     * testGetCommandType06()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(���) this.blogic:execute���\�b�h���I�[�o�[���[�h���Ă���Blogic<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalStateException<br>
     *                    ("Cannot get Command type. BLogic class cannot be overload execute(P) method.")<br>
     *         (��ԕω�) ���O:���O���x��:�G���[<br>
     *                    Cannot get Command type. BLogic class cannot be overload execute(P) method.<br>
     *         
     * <br>
     * Blogic��execute���\�b�h���I�[�o�[���[�h���Ă���p�^�[���B��O���X���[����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
//  BLogicController���g���������ߍ폜
//    public void testGetCommandType06() throws Exception {
//        // �O���� 
//        BLogicController controller = new BLogicController();
//        BLogic blogic = new BLogicController_BLogicImplStub07();
//        UTUtil.setPrivateField(controller, "blogic", blogic);
//        
//        // �e�X�g���{ 
//        try {
//            controller.getCommandType();
//            fail();
//        } catch (IllegalStateException e) {
//            // OK
//            String expect = "Cannot get Command type. "
//                + "BLogic class cannot be overload execute(P) method.";
//            assertEquals(expect, e.getMessage());
//            assertTrue(LogUTUtil.checkError(expect));
//        }
//    }

    /**
     * testExecuteService01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) command:not null<br>
     *         (���) this.blogic:not null<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) Object:blogic.execute()�̖߂�l�Ɠ��������Ƃ��m�F����B<br>
     *         (��ԕω�) blogic.execute():���\�b�h���Ăяo���ꂽ���Ƃ��m�F����B<br>
     *                    �������󂯎�������Ƃ��m�F����B<br>
     *         
     * <br>
     * �T�[�r�X���W�b�N�̐���n�e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testExecuteService01() throws Exception {
        // �O���� 
        BLogicController controller = new BLogicController();
        BLogic<Object, Object> blogic = new BLogicController_BLogicImplStub01();
        UTUtil.setPrivateField(controller, "blogic", blogic);
        Object command = new Object();
        
        // �e�X�g���{ 
        Object result = controller.executeService(command);

        // ���� 
        assertSame(command, result);
        assertSame(command, UTUtil.getPrivateField(blogic, "command"));
    }
    
    /**
     * testSetBusinessLogic01()
     * <br><br>
     * 
     * (����n) 
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) blogic:Blogic�����N���X<br>
     *         (���) this.blogic:null<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) this.blogic:�����Ŏw�肵��blogic<br>
     *         
     * <br>
     * blogic������Setter���\�b�h�̃e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetBusinessLogic01() throws Exception {

        // �O���� 
        BLogicController controller = new BLogicController();
        BLogic<Object, Object> blogic = new BLogicController_BLogicImplStub01();
        UTUtil.setPrivateField(controller, "blogic", null);

        // �e�X�g���{ 
        controller.setBusinessLogic(blogic);

        // ���� 
        assertSame(blogic, UTUtil.getPrivateField(controller, "blogic"));
    }


}
