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

import java.lang.reflect.Type;
import java.util.Date;

import org.springframework.beans.PropertyEditorRegistrar;
import org.springframework.validation.AbstractBindingResult;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingErrorProcessor;
import org.springframework.validation.DefaultBindingErrorProcessor;
import org.springframework.validation.DefaultMessageCodesResolver;
import org.springframework.validation.MessageCodesResolver;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;

import jp.terasoluna.fw.util.ClassLoadException;
import jp.terasoluna.fw.web.rich.context.support.DefaultRequestContextSupportImpl;
import jp.terasoluna.fw.web.rich.context.support.RequestContextSupport;
import jp.terasoluna.utlib.LogUTUtil;
import jp.terasoluna.utlib.MockHttpServletRequest;
import jp.terasoluna.utlib.MockHttpServletResponse;
import jp.terasoluna.utlib.UTUtil;
import jp.terasoluna.fw.web.rich.springmvc.Constants;
import jp.terasoluna.fw.web.rich.springmvc.bind.creator.ServletRequestDataBinderCreator;
import jp.terasoluna.fw.web.rich.springmvc.bind.creator.XMLServletRequestDataBinderCreator;
import jp.terasoluna.fw.web.rich.springmvc.controller.TerasolunaController;
import junit.framework.TestCase;
/**
 * {@link jp.terasoluna.fw.web.rich.springmvc.controller.
 * TerasolunaController} 
 * �N���X�̃u���b�N�{�b�N�X�e�X�g�B
 * 
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4>
 * �T�[�r�X�w�̃N���X�����s���郊�N�G�X�g�R���g���[�����ۃN���X�B<br>
 * �O������FHTTP���N�G�X�g�AHTTP���X�|���X�A�R�}���h�I�u�W�F�N�g��
 * Null�l�ɂȂ�Ȃ��B
 * <p>
 * 
 * @see jp.terasoluna.fw.web.rich.springmvc.controller.
 * TerasolunaController
 */
public class TerasolunaControllerTest extends TestCase {

    /**
     * ���̃e�X�g�P�[�X�����s����ׂ�
     * GUI �A�v���P�[�V�������N������B
     * 
     * @param args java �R�}���h�ɐݒ肳�ꂽ�p�����[�^
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(TerasolunaControllerTest.class);
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
    public TerasolunaControllerTest(String name) {
        super(name);
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
     * ���Ғl�F(��ԕω�) this.ctxSupport:�����Őݒ肵���l�B<br>
     *         
     * <br>
     * ctxSupport������Setter���\�b�h�̃e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetCtxSupport01() throws Exception {
        // �O����
        TerasolunaController controller = new TerasolunaControllerImpl01();
        UTUtil.setPrivateField(controller, "ctxSupport", null);
        RequestContextSupport ctxSupport = 
            new DefaultRequestContextSupportImpl();

        // �e�X�g���{
        controller.setCtxSupport(ctxSupport);

        // ����
        Object result = UTUtil.getPrivateField(controller, "ctxSupport");
        assertSame(ctxSupport, result);
    }

    /**
     * testSetDataBinderCreator01()
     * <br><br>
     * 
     * (����n) 
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) dataBindExecuter:not null<br>
     *         (���) this.dataBinderCreator:null<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) this.dataBinderCreator:�����Őݒ肵���l�B<br>
     *         
     * <br>
     * dataBinderCreator������Setter���\�b�h�̃e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetDataBinderCreator01() throws Exception {
        // �O����
        TerasolunaController controller = new TerasolunaControllerImpl01();
        UTUtil.setPrivateField(controller, "dataBinderCreator", null);
        ServletRequestDataBinderCreator creator = 
            new XMLServletRequestDataBinderCreator();

        // �e�X�g���{
        controller.setDataBinderCreator(creator);

        // ����
        Object result = UTUtil.getPrivateField(controller, "dataBinderCreator");
        assertSame(creator, result);
    }

    /**
     * testSetViewName01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) viewName:"sampleView"<br>
     *         (���) this.viewName:null<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) this.viewName:�����Őݒ肵���l�B<br>
     *         
     * <br>
     * viewName������Setter���\�b�h�̃e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetViewName01() throws Exception {
        // �O����
        TerasolunaController controller = new TerasolunaControllerImpl01();
        UTUtil.setPrivateField(controller, "viewName", null);
        String argString = "sampleView";

        // �e�X�g���{
        controller.setViewName(argString);

        // ����
        Object result = UTUtil.getPrivateField(controller, "viewName");
        assertEquals(argString, result);
    }

    /**
     * testSetUseVelocityView01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) useVelocityView:true<br>
     *         (���) this.useVelocityView:false<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) this.useVelocityView:true<br>
     *         
     * <br>
     * useVelocityView������Setter���\�b�h�̃e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetUseVelocityView01() throws Exception {
        // �O����
        TerasolunaController controller = new TerasolunaControllerImpl01();
        UTUtil.setPrivateField(controller, "useRequestNameView", false);

        // �e�X�g���{
        controller.setUseRequestNameView(true);

        // ����
        Boolean result = (Boolean)UTUtil.getPrivateField(
                controller, "useRequestNameView");
        assertTrue(result);
    }
    
    /**
     * testAfterPropertiesSet01()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(���) this.dataBinderCreator:null<br>
     *         (���) this.ctxSupport:not null<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalStateException(
     *          "DataBinderCreator is Null.")<br>
     *         (��ԕω�) ���O:���O���x���F�G���[DataBinderCreator is Null.<br>
     *         
     * <br>
     * �C���X�^���X����DI�R���e�i���Ă΂�郁�\�b�h�B
     * �f�[�^�o�C���_�����N���X���ݒ肳��Ă��Ȃ��ꍇ�̃e�X�g�B
     * ��O�𔭐�������B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testAfterPropertiesSet01() throws Exception {
        // �O����
        TerasolunaController controller = new TerasolunaControllerImpl01();
        UTUtil.setPrivateField(controller, "dataBinderCreator", null);
        UTUtil.setPrivateField(controller, "ctxSupport", 
                new DefaultRequestContextSupportImpl());

        // �e�X�g���{
        try {
            controller.afterPropertiesSet();
            fail();
        } catch (IllegalStateException e) {
            // OK
            assertEquals("DataBinderCreator is Null.", e.getMessage());
            assertTrue(LogUTUtil.checkError("DataBinderCreator is Null."));
        }
    }

    /**
     * testAfterPropertiesSet02()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(���) this.dataBinderCreator:not null<br>
     *         (���) this.ctxSupport:null<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalStateException(
     *           "ContextSupport is Null.")<br>
     *         (��ԕω�) ���O:���O���x���F�G���[<br>
     *                    ContextSupport is Null.<br>
     *         
     * <br>
     * �C���X�^���X����DI�R���e�i���Ă΂�郁�\�b�h�B
     * ctxSupport��Null�ł��邽�߁A��O����������p�^�[���B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testAfterPropertiesSet02() throws Exception {
        // �O����
        TerasolunaController controller = new TerasolunaControllerImpl01();
        UTUtil.setPrivateField(controller, "ctxSupport", null);
        UTUtil.setPrivateField(controller, "dataBinderCreator", 
                new XMLServletRequestDataBinderCreator());

        // �e�X�g���{
        try {
            controller.afterPropertiesSet();
            fail();
        } catch (IllegalStateException e) {
            // OK
            assertEquals("ContextSupport is Null.", e.getMessage());
            assertTrue(LogUTUtil.checkError("ContextSupport is Null."));
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
    @SuppressWarnings("unchecked")
    public void testAfterPropertiesSet03() throws Exception {
        // �O����
        TerasolunaController controller = new TerasolunaControllerImpl01();
        UTUtil.setPrivateField(controller, "dataBinderCreator", 
                new XMLServletRequestDataBinderCreator());
        UTUtil.setPrivateField(controller, "ctxSupport", 
                new DefaultRequestContextSupportImpl());

        // �e�X�g���{
        controller.afterPropertiesSet();

        // ���ʊm�F�i��O���������Ȃ����OK)
    }

    /**
     * testGetCommand01()
     * <br><br>
     * 
     * (����n) 
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) request:�g�p���Ȃ�<br>
     *         (���) getCommandType:�N���X<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) Object:getCommand()�̖߂�l�̃N���X��
     * �C���X�^���X�����ꂽ���Ƃ��m�F����B<br>
     *         
     * <br>
     * �^�p�����[�^���N���X�̃p�^�[���̃e�X�g�B����ɃC���X�^���X�����s���B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetCommand01() throws Exception {
        // �O����
        TerasolunaController controller = new TerasolunaControllerImpl08();
        MockHttpServletRequest request = new MockHttpServletRequest();

        // �e�X�g���{
        Object result = controller.getCommand(request);

        // ����
        assertEquals(Date.class, result.getClass());
    }

    /**
     * testGetCommand02()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) request:�g�p���Ȃ�<br>
     *         (���) getCommandType:���ۃN���X<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalStateException("Invalid Command type.")<br>
     *         (��ԕω�) ���O:���O���x���F�G���[
     *                         "Invalid Command type.", ClassLoadException<br>
     *         
     * <br>
     * �^�p�����[�^�����ۃN���X�̃p�^�[���̃e�X�g�B
     * �C���X�^���X���Ɏ��s���A��O�𓊂���B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetCommand02() throws Exception {
        // �O����
        TerasolunaController controller = new TerasolunaControllerImpl01();
        UTUtil.setPrivateField(controller, "commandType", TerasolunaController.class);
        MockHttpServletRequest request = new MockHttpServletRequest();

        // �e�X�g���{
        try {
            controller.getCommand(request);
            fail();
        } catch (IllegalStateException e) {
            // OK
            assertEquals("Invalid Command type.", e.getMessage());
            assertTrue(LogUTUtil.checkError("Invalid Command type.", 
                new ClassLoadException(new InstantiationException())));
        }
    }

    /**
     * testGetCommand03()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) request:�g�p���Ȃ�<br>
     *         (���) getCommandType:�N���X<br>
     *                �i�R���X�g���N�^��private�錾����Ă���j<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalStateException("Invalid Command type.")<br>
     *         (��ԕω�) ���O:���O���x���F�G���[
     *                         "Invalid Command type.", ClassLoadException<br>
     *         
     * <br>
     * �^�p�����[�^���N���X�̃p�^�[���̃e�X�g�B
     * �R���X�g���N�^���Ăׂ��A�C���X�^���X���Ɏ��s���A��O�𓊂���B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetCommand03() throws Exception {
        // �O����
        TerasolunaController controller = new TerasolunaControllerImpl01();
        UTUtil.setPrivateField(controller, "commandType", Math.class);
        MockHttpServletRequest request = new MockHttpServletRequest();

        // �e�X�g���{
        try {
            controller.getCommand(request);
            fail();
        } catch (IllegalStateException e) {
            // OK
            assertEquals("Invalid Command type.", e.getMessage());
            assertTrue(LogUTUtil.checkError("Invalid Command type.", 
                new ClassLoadException(new IllegalAccessException())));
        }
    }
    
    /**
     * testGetCommand04()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) request:�g�p���Ȃ�<br>
     *         (���) getCommandType:Object.class<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalStateException("Cannot get Command type. Controller cannot specify the Object type for parameterized type P.")<br>
     *         (��ԕω�) ���O:���O���x���F�G���[�@Cannot get Command type. Controller cannot specify the Object type for parameterized type P.<br>
     *         
     * <br>
     * �^�p�����[�^���N���X�̃p�^�[���̃e�X�g�B
     * �R���X�g���N�^���Ăׂ��A�C���X�^���X���Ɏ��s���A��O�𓊂���B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetCommand04() throws Exception {
        // �O����
        TerasolunaController controller = new TerasolunaControllerImpl07();
        MockHttpServletRequest request = new MockHttpServletRequest();

        // �e�X�g���{
        try {
            controller.getCommand(request);
            fail();
        } catch (IllegalStateException e) {
            // OK
            String expect = "Cannot get Command type. " +
                    "Controller cannot specify the Object type for parameterized type P.";
            assertEquals(expect, e.getMessage());
            assertTrue(LogUTUtil.checkError(expect));
        }
    }

    /**
     * testGetCommandType01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(���) this�i�R���g���[�������N���X�j:
     *                 �e�N���X��`�FTerasolunaController<P,R><br>
     *                 this��`�FMyController extends TerasolunaController
     *                  <Integer,Boolean><br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) Type:Integer<br>
     *         
     * <br>
     * Terasoluna�R���g���[�����p�����A�^�p�����[�^P�̌^���w�肷��p�^�[���B
     * ����P�[�X�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testGetCommandType01() throws Exception {
        // �O����
        TerasolunaController controller =
            new TerasolunaControllerImpl03();

        // �e�X�g���{
        Type type = controller.getCommandType();

        // ����
        assertSame(Integer.class, type);
    }

    /**
     * testGetCommandType02()
     * <br><br>
     * 
     *  (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) request:�g�p���Ȃ�<br>
     *         (���) getCommandType:���ۃN���X<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalStateException("Invalid Command type.")<br>
     *         (��ԕω�) ���O:���O���x���F�G���[Invalid Command type.<br>
     *         
     * <br>
     * �^�p�����[�^�����ۃN���X�̃p�^�[���̃e�X�g�B
     * �C���X�^���X���Ɏ��s���A��O�𓊂���B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testGetCommandType02() throws Exception {
        // �O����
        TerasolunaController controller =
            new TerasolunaControllerImpl02();

        // �e�X�g���{
        try {
            controller.getCommandType();
            fail();
        } catch (IllegalStateException e) {
            // �e�X�g���ʊm�F
            String expect = "Controller class must be set ParameterizedType"; 
            assertEquals(expect, e.getMessage());
            assertTrue(LogUTUtil.checkError(expect));
        }
    }

    /**
     * testGetCommandType03()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(���) this�i�R���g���[�������N���X�j:�e�̐e�̐e�N���X��`�F
     *                TerasolunaController<P, R><br>
     *                �e�̐e�N���X��`�FTerasolunaControllerImpl04<P,R> 
     *                extends TerasolunaController<Date,Long><br>
     *                �e�N���X��`�FTerasolunaControllerImpl05<P,R> 
     *                extends TerasolunaControllerImpl04<Double, String><br>
     *                this��`�FTerasolunaControllerImpl06
     *                extends TerasolunaControllerImpl05<Float,Short><br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) Type:Date<br>
     *         
     * <br>
     * �R���g���[�����R��p�����Ă���p�^�[���B
     * TerasolunaController�N���X�̌^�p�����[�^���g�p����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testGetCommandType03() throws Exception {
        // �O����
        TerasolunaController controller =
            new TerasolunaControllerImpl06();

        // �e�X�g���{
        Type type = controller.getCommandType();

        // ����
        assertSame(Date.class, type);
    }

    /**
     * testCreateBinder01()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) request:�g�p���Ȃ�<br>
     *         (����) command:not null<br>
     *         (���) this.dataBinderCreator:not null<br>
     *         (���) this.dataBinderCreator.create():null<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalStateException("DataBinder is Null.")<br>
     *         (��ԕω�) ���O:���O���x���F�G���[DataBinder is Null.<br>
     *         
     * <br>
     * �f�[�^�o�C���_�̐�����Null���Ԃ����p�^�[���B��O�𔭐�������B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testCreateBinder01() throws Exception {
        // �O����
        TerasolunaController controller = new TerasolunaControllerImpl01();
        UTUtil.setPrivateField(controller, "dataBinderCreator", 
                new TerasolunaController_ServletRequestDataBinderCreatorStub01());
        UTUtil.setPrivateField(controller, "ctxSupport", 
                new TerasolunaController_DefaultRequestContextSupportImplStub01());
        MockHttpServletRequest request = new MockHttpServletRequest();
        Object command = new Object();
        
        // �e�X�g���{
        try {
            controller.createBinder(request, command);
            fail();
        } catch (IllegalStateException e) {
            assertEquals("DataBinder is Null.", e.getMessage());
            assertTrue(LogUTUtil.checkError("DataBinder is Null."));
        }
    }

    /**
     * testCreateBinder02()
     * <br><br>
     * 
     * (����n) 
     * <br>
     * �ϓ_�FE
     * <br><br>
     * ���͒l�F(����) request:�g�p���Ȃ�<br>
     *         (����) command:not null<br>
     *         (���) this.dataBinderCreator:not null<br>
     *         (���) this.dataBinderCreator.create():not null<br>
     *         (���) this.getMessageCodesResolver():not null<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) ServletRequestDataBinder:
     *                  ��������ServletRequestDataBinder�I�u�W�F�N�g�B<br>
     *                  This.messageCodeResolver���ݒ肳��Ă���B<br>
     *         (��ԕω�) initBinder():���\�b�h���Ăяo���ꂽ���Ƃ��m�F����B<br>
     *                    �������󂯎�������Ƃ��m�F����B<br>
     *         
     * <br>
     * ����Ƀf�[�^�o�C���_�𐶐�����p�^�[���BmessageCodeResolver���Z�b�g����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testCreateBinder02() throws Exception {
        // �O���� -----------------------------------------------------
        TerasolunaControllerImpl01 controller = 
            new TerasolunaControllerImpl01();
        
        // �f�[�^�o�C���_
        ServletRequestDataBinder binder = 
            new TerasolunaController_ServletRequestDataBinderStub01();
        
        // �f�[�^�o�C���_�����N���X
        TerasolunaController_ServletRequestDataBinderCreatorStub02 creator = 
            new TerasolunaController_ServletRequestDataBinderCreatorStub02();
        creator.setBinder(binder);
        UTUtil.setPrivateField(controller, "dataBinderCreator", creator);
        
        // �R���e�L�X�g�T�|�[�g�N���X
        UTUtil.setPrivateField(controller, "ctxSupport", 
                new TerasolunaController_DefaultRequestContextSupportImplStub01());

        // ���b�Z�[�W�R�[�h���]���o
        MessageCodesResolver msgCodesResolver = 
            new TerasolunaController_MessageCodesResolverStub01(); 
        UTUtil.setPrivateField(controller, "messageCodesResolver", 
                msgCodesResolver);
        
        // �o�C���h�G���[�v���Z�b�T
        BindingErrorProcessor errorProcessor = new DefaultBindingErrorProcessor();
        controller.setBindingErrorProcessor(errorProcessor);
        
        // �v���p�e�B�G�f�B�^
        PropertyEditorRegistrar[] editorRegistrars 
            = new PropertyEditorRegistrar[3];
        TerasolunaController_PropertyEditorRegistrarStab01 registrar1 = new TerasolunaController_PropertyEditorRegistrarStab01();
        TerasolunaController_PropertyEditorRegistrarStab01 registrar2 = new TerasolunaController_PropertyEditorRegistrarStab01();
        TerasolunaController_PropertyEditorRegistrarStab01 registrar3 = new TerasolunaController_PropertyEditorRegistrarStab01();
        editorRegistrars[0] = registrar1;
        editorRegistrars[1] = registrar2;
        editorRegistrars[2] = registrar3;
        controller.setPropertyEditorRegistrars(editorRegistrars);
        
        MockHttpServletRequest request = new MockHttpServletRequest();
        Object command = new Object();
        
        // �e�X�g���{ --------------------------------------------------
        ServletRequestDataBinder result = 
            controller.createBinder(request, command);

        // ���� --------------------------------------------------------
        assertSame(binder, result);

        assertSame(msgCodesResolver,((AbstractBindingResult) binder.getBindingResult()).getMessageCodesResolver());
        Object resultInitBinderRequest = 
            UTUtil.getPrivateField(controller, "initBinderRequest");
        assertSame(request, resultInitBinderRequest);
        Object resultBinder = UTUtil.getPrivateField(controller, "binder");
        assertSame(result, resultBinder);
        assertSame(errorProcessor, result.getBindingErrorProcessor());
        assertSame(result, registrar1.editorRegistry);
        assertSame(result, registrar2.editorRegistry);
        assertSame(result, registrar3.editorRegistry);
    }

    /**
     * testCreateBinder03()
     * <br><br>
     * 
     * (����n) 
     * <br>
     * �ϓ_�FE
     * <br><br>
     * ���͒l�F(����) request:�g�p���Ȃ�<br>
     *         (����) command:not null<br>
     *         (���) this.dataBinderCreator:not null<br>
     *         (���) this.dataBinderCreator.create():not null<br>
     *         (���) this.getMessageCodesResolver():null<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) ServletRequestDataBinder:��������
     *          ServletRequestDataBinder�I�u�W�F�N�g�B
     *          �f�t�H���g��messageCodeResolver�iDefaultMessageCodesResolver�j��
     *          �ݒ肳��Ă���B<br>
     *         (��ԕω�) initBinder():���\�b�h���Ăяo���ꂽ���Ƃ��m�F����B<br>
     *                    �������󂯎�������Ƃ��m�F����B<br>
     *         
     * <br>
     * ����Ƀf�[�^�o�C���_�𐶐�����p�^�[���B
     * messageCodeResolver���Z�b�g���Ȃ����߁A
     * Null���ݒ肳��Ă���B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testCreateBinder03() throws Exception {
        // �O���� -----------------------------------------------------
        TerasolunaControllerImpl01 controller = new TerasolunaControllerImpl01();
        
        // �f�[�^�o�C���_
        ServletRequestDataBinder binder = 
            new TerasolunaController_ServletRequestDataBinderStub01();
        
        // �f�[�^�o�C���_�����N���X
        TerasolunaController_ServletRequestDataBinderCreatorStub02 creator = 
            new TerasolunaController_ServletRequestDataBinderCreatorStub02();
        creator.setBinder(binder);
        UTUtil.setPrivateField(controller, "dataBinderCreator", creator);
        
        // �R���e�L�X�g�T�|�[�g�N���X
        UTUtil.setPrivateField(controller, "ctxSupport", 
                new TerasolunaController_DefaultRequestContextSupportImplStub01());

        // ���b�Z�[�W�R�[�h���]���o�͐ݒ肵�Ȃ�
        
        MockHttpServletRequest request = new MockHttpServletRequest();
        Object command = new Object();
        
        // �e�X�g���{ --------------------------------------------------
        ServletRequestDataBinder result = 
            controller.createBinder(request, command);

        // ���� --------------------------------------------------------
        assertSame(binder, result);
        
        assertSame(DefaultMessageCodesResolver.class,
                ((AbstractBindingResult) binder.getBindingResult())
                        .getMessageCodesResolver().getClass());
        
        Object resultInitBinderRequest = 
            UTUtil.getPrivateField(controller, "initBinderRequest");
        assertSame(request, resultInitBinderRequest);
        Object resultBinder = UTUtil.getPrivateField(controller, "binder");
        assertSame(result, resultBinder);
    }

    /**
     * testOnBind01()
     * <br><br>
     * 
     * (����n) 
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) request:�g�p���Ȃ�<br>
     *         (����) command:�g�p���Ȃ�<br>
     *         (����) errors:�G���[����<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) ��O:errors�𓊂���B<br>
     *         
     * <br>
     * �o�C���h�����ŃG���[����������p�^�[���̃e�X�g�B��O�𓊂���B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testOnBind01() throws Exception {
        // �O����
        TerasolunaController controller = new TerasolunaControllerImpl01();
        BindException errors = new TerasolunaController_BindExceptionStub01();
        MockHttpServletRequest request = new MockHttpServletRequest();
        Object command = new Object();

        // �e�X�g���{
        try {
            controller.onBind(request, command, errors);
            fail();
        } catch (BindException e) {
            // OK
            assertSame(errors, e);
        }
    }

    /**
     * testOnBind02()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) request:�g�p���Ȃ�<br>
     *         (����) command:�g�p���Ȃ�<br>
     *         (����) errors:�G���[�Ȃ�<br>
     *         
     * <br>
     * ���Ғl�F
     * <br>
     * �o�C���h�����ŃG���[���������Ȃ��p�^�[���̃e�X�g�B�������Ȃ��B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testOnBind02() throws Exception {
        // �O����
        TerasolunaController controller = new TerasolunaControllerImpl01();
        BindException errors = new TerasolunaController_BindExceptionStub02();
        MockHttpServletRequest request = new MockHttpServletRequest();
        Object command = new Object();

        // �e�X�g���{
        controller.onBind(request, command, errors);
        
        // ���ʊm�F(��O���������Ȃ����OK�j
    }

    /**
     * testOnBindAndValidate01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) request:�g�p���Ȃ�<br>
     *         (����) command:�g�p���Ȃ�<br>
     *         (����) errors:�G���[����<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) ��O:errors�𓊂���B<br>
     *         
     * <br>
     * ���̓`�F�b�N�����ŃG���[����������p�^�[���̃e�X�g�B��O�𓊂���B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testOnBindAndValidate01() throws Exception {
        // �O����
        TerasolunaController controller = new TerasolunaControllerImpl01();
        BindException errors = new TerasolunaController_BindExceptionStub01();
        MockHttpServletRequest request = new MockHttpServletRequest();
        Object command = new Object();

        // �e�X�g���{
        try {
            controller.onBindAndValidate(request, command, errors);
            fail();
        } catch (BindException e) {
            // OK
            assertSame(errors, e);
        }
    }

    /**
     * testOnBindAndValidate02()
     * <br><br>
     * 
     * (����n) 
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) request:�g�p���Ȃ�<br>
     *         (����) command:�g�p���Ȃ�<br>
     *         (����) errors:�G���[�Ȃ�<br>
     *         
     * <br>
     * ���Ғl�F
     * <br>
     * ���̓`�F�b�N�����ŃG���[���������Ȃ��p�^�[���̃e�X�g�B�������Ȃ��B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testOnBindAndValidate02() throws Exception {
        // �O����
        TerasolunaController controller = new TerasolunaControllerImpl01();
        BindException errors = new TerasolunaController_BindExceptionStub02();
        MockHttpServletRequest request = new MockHttpServletRequest();
        Object command = new Object();

        // �e�X�g���{
        controller.onBindAndValidate(request, command, errors);
        
        // ���ʊm�F(��O���������Ȃ����OK�j
    }

    /**
     * testHandle01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) request:not null<br>
     *         (����) response:not null<br>
     *         (����) command:not null<br>
     *         (����) errors:�g�p���Ȃ�<br>
     *         (���) this.viewName:�hsample�h<br>
     *         (���) this.useRequestNameView:false<br>
     *         (���) ctxSupport.getRequestName():�hcommand�h<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) ModelAndView:ModelAndView��Ԃ��B<br>
     *                  �r���[���Fsample<br>
     *         (��ԕω�) executeService�i�j:���\�b�h���Ăяo���ꂽ���Ƃ��m�F����B<br>
     *                    �������󂯎�������Ƃ��m�F����B<br>
     *         
     * <br>
     * �r���[���𒼐ړ��͂���p�^�[���̃e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testHandle01() throws Exception {
        // �O���� --------------------------------------------------------------
        TerasolunaControllerImpl01 controller = new TerasolunaControllerImpl01();
        
        // �r���[��
        String viewName = "sample";
        UTUtil.setPrivateField(controller, "viewName", viewName);
        
        // �R���e�L�X�g�T�|�[�g�N���X
        UTUtil.setPrivateField(controller, "ctxSupport", 
                new TerasolunaController_DefaultRequestContextSupportImplStub01());

        // Velocity�g�p�t���O
        UTUtil.setPrivateField(controller, "useRequestNameView", false);
        
        // �Ɩ����ʃI�u�W�F�N�g
        Object model = new Object();
        
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        BindException errors = new BindException(new Object(), "");
        
        // �e�X�g���{ ----------------------------------------------------------
        ModelAndView mv = controller.handle(request, response, model, errors);

        // ���� ----------------------------------------------------------------
        assertEquals(viewName, mv.getViewName());
        assertTrue(controller.isExecuteService);
        assertSame(model, mv.getModel().get(Constants.RESULT_KEY));
    }

    /**
     * testHandle02()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) request:not null<br>
     *         (����) response:not null<br>
     *         (����) command:not null<br>
     *         (����) errors:�g�p���Ȃ�<br>
     *         (���) this.viewName:null<br>
     *         (���) this.useRequestNameView:true<br>
     *         (���) ctxSupport.getRequestName():�hcommand�h<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) ModelAndView:ModelAndView��Ԃ��B<br>
     *                  �r���[���F/sum<br>
     *         (��ԕω�) executeService�i�j:
     *         ���\�b�h���Ăяo���ꂽ���Ƃ��m�F����B<br>
     *                    �������󂯎�������Ƃ��m�F����B<br>
     *         
     * <br>
     * Velocity�r���[���g�p����p�^�[���̃e�X�g�B
     * �r���[����"/"�{���N�G�X�g�����g�p�����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testHandle02() throws Exception {
        // �O���� --------------------------------------------------------------
        TerasolunaControllerImpl01 controller = 
            new TerasolunaControllerImpl01();
        
        // �r���[��
        UTUtil.setPrivateField(controller, "viewName", null);
        
        // �R���e�L�X�g�T�|�[�g�N���X
        UTUtil.setPrivateField(controller, "ctxSupport", 
                new TerasolunaController_DefaultRequestContextSupportImplStub01());
        
        // Velocity�g�p�t���O
        UTUtil.setPrivateField(controller, "useRequestNameView", true);

        // �Ɩ����ʃI�u�W�F�N�g
        Object model = new Object();
        
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        BindException errors = new BindException(new Object(), "");
        
        
        // �e�X�g���{ ----------------------------------------------------------
        ModelAndView mv = controller.handle(request, response, model, errors);

        // ���� ----------------------------------------------------------------
        assertEquals("/command", mv.getViewName());
        assertTrue(controller.isExecuteService);
        assertEquals(model, mv.getModel().get(Constants.RESULT_KEY));
    }

    /**
     * testHandle03()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) request:not null<br>
     *         (����) response:not null<br>
     *         (����) command:not null<br>
     *         (����) errors:�g�p���Ȃ�<br>
     *         (���) this.viewName:�hsample�h<br>
     *         (���) this.useRequestNameView:true<br>
     *         (���) ctxSupport.getRequestName():�hcommand�h<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) ModelAndView:ModelAndView��Ԃ��B<br>
     *                  �r���[���Fsample<br>
     *         (��ԕω�) executeService�i�j:
     *         ���\�b�h���Ăяo���ꂽ���Ƃ��m�F����B<br>
     *                    �������󂯎�������Ƃ��m�F����B<br>
     *         
     * <br>
     * �r���[���𒼐ړ��͂��A����Velocity�r���[���g�p����ݒ肪
     * ����p�^�[���̃e�X�g�B
     * ���ړ��͂��ꂽ�r���[�����D�悳���B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testHandle03() throws Exception {
        // �O���� --------------------------------------------------------------
        TerasolunaControllerImpl01 controller = new TerasolunaControllerImpl01();
        
        // �r���[��
        String viewName = "sample";
        UTUtil.setPrivateField(controller, "viewName", viewName);
        
        // �R���e�L�X�g�T�|�[�g�N���X
        UTUtil.setPrivateField(controller, "ctxSupport", 
                new TerasolunaController_DefaultRequestContextSupportImplStub01());

        // Velocity�g�p�t���O
        UTUtil.setPrivateField(controller, "useRequestNameView", true);
        
        // �Ɩ����ʃI�u�W�F�N�g
        Object model = new Object();
        
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        BindException errors = new BindException(new Object(), "");
        
        // �e�X�g���{ ----------------------------------------------------------
        ModelAndView mv = controller.handle(request, response, model, errors);

        // ���� ----------------------------------------------------------------
        assertEquals(viewName, mv.getViewName());
        assertTrue(controller.isExecuteService);
        assertSame(model, mv.getModel().get(Constants.RESULT_KEY));
    }

    /**
     * testHandle04()
     * <br><br>
     * 
     * (����n) 
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) request:not null<br>
     *         (����) response:not null<br>
     *         (����) command:not null<br>
     *         (����) errors:�g�p���Ȃ�<br>
     *         (���) this.viewName:null<br>
     *         (���) this.useRequestNameView:false<br>
     *         (���) ctxSupport.getRequestName():�hcommand�h<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) ModelAndView:ModelAndView��Ԃ��B<br>
     *                  �r���[���F�i�󕶎��j<br>
     *         (��ԕω�) executeService�i�j:
     *         ���\�b�h���Ăяo���ꂽ���Ƃ��m�F����B<br>
     *                    �������󂯎�������Ƃ��m�F����B<br>
     *         
     * <br>
     * �r���[���̎w����AVelocity�r���[���g�p����ݒ�����Ă��Ȃ��ꍇ�̃e�X�g�B
     * �󕶎��̃r���[�����ݒ肳���BCastor�r���[���g�p����ꍇ�̐ݒ�ƂȂ�B
     * TERASOLUNA�̃r���[�@�\�ł̓f�t�H���g�d�l�ƂȂ��Ă���B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testHandle04() throws Exception {
        // �O���� --------------------------------------------------------------
        TerasolunaControllerImpl01 controller = new TerasolunaControllerImpl01();
        
        // �r���[��
        UTUtil.setPrivateField(controller, "viewName", null);
        
        // �R���e�L�X�g�T�|�[�g�N���X
        UTUtil.setPrivateField(controller, "ctxSupport", 
                new TerasolunaController_DefaultRequestContextSupportImplStub01());
        
        // Velocity�g�p�t���O
        UTUtil.setPrivateField(controller, "useRequestNameView", false);

        // �Ɩ����ʃI�u�W�F�N�g
        Object model = new Object();
        
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        BindException errors = new BindException(new Object(), "");
        
        // �e�X�g���{ ----------------------------------------------------------
        ModelAndView mv = controller.handle(request, response, model, errors);

        // ���� ----------------------------------------------------------------
        assertEquals("", mv.getViewName());
        assertTrue(controller.isExecuteService);
        assertSame(model, mv.getModel().get(Constants.RESULT_KEY));
    }

    /**
     * testExecuteService01()
     * <br><br>
     * 
     * (����n) 
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) request:�g�p���Ȃ�<br>
     *         (����) response:�g�p���Ȃ�<br>
     *         (����) command:not null<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) R:executeService�i�j
     *          ���\�b�h�̖߂�l�Ɠ��������Ƃ��m�F����B<br>
     *         (��ԕω�) preService�i�j:
     *         ���\�b�h���Ăяo���ꂽ���Ƃ��m�F����B<br>
     *                    �������󂯎�������Ƃ��m�F����B<br>
     *         (��ԕω�) executeService�i�j:
     *         ���\�b�h���Ăяo���ꂽ���Ƃ��m�F����B<br>
     *                    �������󂯎�������Ƃ��m�F����B<br>
     *         (��ԕω�) postService�i�j:
     *         ���\�b�h���Ăяo���ꂽ���Ƃ��m�F����B<br>
     *                    �������󂯎�������Ƃ��m�F����B<br>
     *         
     * <br>
     * �T�[�r�X���W�b�N�̃e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testExecuteService01() throws Exception {
        // �O����
        TerasolunaControllerImpl01 controller = 
            new TerasolunaControllerImpl01();
        String model = "aaa";
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        
        // �e�X�g���{
        Object result = controller.executeService(request, response, model);

        // ����
        assertEquals(model, result);
        assertTrue(controller.isPreService);
        assertTrue(controller.isExecuteService);
        assertTrue(controller.isPostService);
    }
}
