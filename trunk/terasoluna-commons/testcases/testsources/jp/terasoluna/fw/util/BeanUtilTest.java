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

package jp.terasoluna.fw.util;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import jp.terasoluna.utlib.UTUtil;
import junit.framework.TestCase;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.DynaProperty;

/**
 * {@link jp.terasoluna.fw.util.BeanUtil} �N���X��
 * �u���b�N�{�b�N�X�e�X�g�B
 *
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4>
 * Bean�֘A�̃��[�e�B���e�B�N���X�B
 * <p>
 *
 * @see jp.terasoluna.fw.util.BeanUtil
 */
@SuppressWarnings("unused")
public class BeanUtilTest extends TestCase {

    /**
     * ���̃e�X�g�P�[�X�����s����ׂ�
     * GUI �A�v���P�[�V�������N������B
     *
     * @param args java �R�}���h�ɐݒ肳�ꂽ�p�����[�^
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(BeanUtilTest.class);
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
    public BeanUtilTest(String name) {
        super(name);
    }

    /**
     * testSetBeanProperty01()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) bean:setParam1()���\�b�h����Exception����<br>
     *         (����) property:"param1"<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:PropertyAccessException�F<br>
     *                    ���b�v������O�FException<br>
     *
     * <br>
     * PropertyUtils#setProperty()���\�b�h��InvocationTargetException�������������A
     * InvocationTargetException�����b�v���Ă�����O�C���X�^���X��
     * PropertyAccessException�Ń��b�v���邱�ƁB
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetBeanProperty01() throws Exception {
        // �e�X�g�pJavaBean����
        BeanUtil_BeanStub01 bean = new BeanUtil_BeanStub01();

        // �e�X�g���s
        try {
            BeanUtil.setBeanProperty(bean, "param1", "PARAM1");
            fail();
        } catch (PropertyAccessException e) {
            // �e�X�g���ʊm�F
            assertEquals(Exception.class.getName(), e
                    .getCause().getClass().getName());
        }
    }

    /**
     * testSetBeanProperty02()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) bean:param3�������Ȃ�<br>
     *         (����) property:"param3"<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:PropertyAccessException�F<br>
     *                    ���b�v������O�FNoSuchMethodException<br>
     *
     * <br>
     * PropertyUtils#setProperty()���\�b�h��NoSuchMethodException�������������APropertyAccessException�Ń��b�v���邱�ƁB
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetBeanProperty02() throws Exception {
        // �e�X�g�pJavaBean����
        BeanUtil_BeanStub01 bean = new BeanUtil_BeanStub01();

        // �e�X�g���s
        try {
            BeanUtil.setBeanProperty(bean, "param3", "PARAM3");
            fail();
        } catch (PropertyAccessException e) {
            // �e�X�g���ʊm�F
            assertEquals(NoSuchMethodException.class.getName(), e.getCause()
                    .getClass().getName());
        }
    }

    /**
     * testSetBeanProperty03()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) bean:null<br>
     *         (����) property:not null<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:PropertyAccessException�F<br>
     *                    ���b�v������O�FIllegalArgumentException<br>
     *
     * <br>
     * PropertyUtils#setProperty()���\�b�h��IllegalArgumentException�������������APropertyAccessException�Ń��b�v���邱�ƁB
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetBeanProperty03() throws Exception {
        // �e�X�g���s
        try {
            BeanUtil.setBeanProperty(null, "param1", "PARAM1");
            fail();
        } catch (PropertyAccessException e) {
            // �e�X�g���ʊm�F
            assertEquals(IllegalArgumentException.class.getName(), e.getCause()
                    .getClass().getName());
        }
    }

    /**
     * testSetBeanProperty04()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) bean:not null<br>
     *         (����) property:null<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:PropertyAccessException�F<br>
     *                    ���b�v������O�FIllegalArgumentException<br>
     *
     * <br>
     * PropertyUtils#setProperty()���\�b�h��IllegalArgumentException�������������APropertyAccessException�Ń��b�v���邱�ƁB
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetBeanProperty04() throws Exception {
        // �e�X�g�pJavaBean����
        BeanUtil_BeanStub01 bean = new BeanUtil_BeanStub01();

        // �e�X�g���s
        try {
            BeanUtil.setBeanProperty(bean, null, "PARAM1");
            fail();
        } catch (PropertyAccessException e) {
            // �e�X�g���ʊm�F
            assertEquals(IllegalArgumentException.class.getName(), e.getCause()
                    .getClass().getName());
        }
    }

    /**
     * testSetBeanProperty05()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) bean:param2=null<br>
     *         (����) property:"param2"<br>
     *         (����) value:"PARAM2"<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) bean:param2="PARAM2"<br>
     *
     * <br>
     * PropertyUtils#setProperty()���\�b�h���������Ăяo����A����������ɍs�Ȃ��邱�ƁB
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetBeanProperty05() throws Exception {
        // �e�X�g�pJavaBean����
        BeanUtil_BeanStub01 bean = new BeanUtil_BeanStub01();

        // �e�X�g���s
        BeanUtil.setBeanProperty(bean, "param2", "PARAM2");

        // �e�X�g���ʊm�F
        assertEquals("PARAM2", UTUtil.getPrivateField(bean, "param2"));
    }

    /**
     * testSetBeanProperty06()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) bean:param2="PARAM2"<br>
     *         (����) property:"param2"<br>
     *         (����) value:null<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) bean:param2=null<br>
     *
     * <br>
     * PropertyUtils#setProperty()���\�b�h���������Ăяo����A����������ɍs�Ȃ��邱�ƁB
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetBeanProperty06() throws Exception {
        // �e�X�g�pJavaBean����
        BeanUtil_BeanStub01 bean = new BeanUtil_BeanStub01();
        bean.setParam2("PARAM2");

        // �e�X�g���s
        BeanUtil.setBeanProperty(bean, "param2", null);

        // �e�X�g���ʊm�F
        assertNull(UTUtil.getPrivateField(bean, "param2"));
    }

    /**
     * testSetBeanProperty07()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) bean:not null<br>
     *         (����) property:""<br>
     *         (����) value:"PARAM"<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:PropertyAccessException�F<br>
     *                    ���b�v������O�FNoSuchMethodException<br>
     *
     * <br>
     * property���󕶎��̎��APropertyAccessException���X���[���邱�ƁB
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetBeanProperty07() throws Exception {
        // �e�X�g�pJavaBean����
        BeanUtil_BeanStub01 bean = new BeanUtil_BeanStub01();

        // �e�X�g���s
        try {
            BeanUtil.setBeanProperty(bean, "", "PARAM");
            fail();
        } catch (PropertyAccessException e) {
            // �e�X�g���ʊm�F
            assertEquals(NoSuchMethodException.class.getName(), e.getCause()
                    .getClass().getName());
        }
    }

    /**
     * testGetBeanProperty01()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) bean:getParam1()���\�b�h����Exception����<br>
     *         (����) property:"param1"<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:PropertyAccessException�F<br>
     *                    ���b�v������O�FException<br>
     *
     * <br>
     * PropertyUtils#getProperty()���\�b�h��InvocationTargetException�������������A
     * InvocationTargetException�����b�v���Ă�����O�C���X�^���X��
     * PropertyAccessException�Ń��b�v���邱�ƁB
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetBeanProperty01() throws Exception {
        // �e�X�g�pJavaBean����
        BeanUtil_BeanStub01 bean = new BeanUtil_BeanStub01();

        // �e�X�g���s
        try {
            BeanUtil.getBeanProperty(bean, "param1");
            fail();
        } catch (PropertyAccessException e) {
            // �e�X�g���ʊm�F
            assertEquals(Exception.class.getName(), e
                    .getCause().getClass().getName());
        }
    }

    /**
     * testGetBeanProperty02()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) bean:param3�������Ȃ�<br>
     *         (����) property:"param3"<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:PropertyAccessException�F<br>
     *                    ���b�v������O�FNoSuchMethodException<br>
     *
     * <br>
     * PropertyUtils#getProperty()���\�b�h��NoSuchMethodException�������������APropertyAccessException�Ń��b�v���邱�ƁB
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetBeanProperty02() throws Exception {
        // �e�X�g�pJavaBean����
        BeanUtil_BeanStub01 bean = new BeanUtil_BeanStub01();

        // �e�X�g���s
        try {
            BeanUtil.getBeanProperty(bean, "param3");
            fail();
        } catch (PropertyAccessException e) {
            // �e�X�g���ʊm�F
            assertEquals(NoSuchMethodException.class.getName(), e.getCause()
                    .getClass().getName());
        }
    }

    /**
     * testGetBeanProperty03()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) bean:null<br>
     *         (����) property:not null<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:PropertyAccessException�F<br>
     *                    ���b�v������O�FIllegalArgumentException<br>
     *
     * <br>
     * PropertyUtils#getProperty()���\�b�h��IllegalArgumentException�������������APropertyAccessException�Ń��b�v���邱�ƁB
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetBeanProperty03() throws Exception {
        // �e�X�g���s
        try {
            BeanUtil.getBeanProperty(null, "param1");
            fail();
        } catch (PropertyAccessException e) {
            // �e�X�g���ʊm�F
            assertEquals(IllegalArgumentException.class.getName(), e.getCause()
                    .getClass().getName());
        }
    }

    /**
     * testGetBeanProperty04()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) bean:not null<br>
     *         (����) property:null<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:PropertyAccessException�F<br>
     *                    ���b�v������O�FIllegalArgumentException<br>
     *
     * <br>
     * PropertyUtils#getProperty()���\�b�h��IllegalArgumentException�������������APropertyAccessException�Ń��b�v���邱�ƁB
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetBeanProperty04() throws Exception {
        // �e�X�g�pJavaBean����
        BeanUtil_BeanStub01 bean = new BeanUtil_BeanStub01();

        // �e�X�g���s
        try {
            BeanUtil.getBeanProperty(bean, null);
            fail();
        } catch (PropertyAccessException e) {
            // �e�X�g���ʊm�F
            assertEquals(IllegalArgumentException.class.getName(), e.getCause()
                    .getClass().getName());
        }
    }

    /**
     * testGetBeanProperty05()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) bean:param2="PARAM2"<br>
     *         (����) property:"param2"<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) Object:"PARAM2"<br>
     *
     * <br>
     * PropertyUtils#getProperty()���\�b�h���������Ăяo����A����������ɍs�Ȃ��邱�ƁB
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetBeanProperty05() throws Exception {
        // �e�X�g�pJavaBean����
        BeanUtil_BeanStub01 bean = new BeanUtil_BeanStub01();
        bean.setParam2("PARAM2");

        // �e�X�g���s�E����
        assertEquals("PARAM2", BeanUtil.getBeanProperty(bean, "param2"));
    }

    /**
     * testGetBeanProperty06()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) bean:param2=null<br>
     *         (����) property:"param2"<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) Object:null<br>
     *
     * <br>
     * PropertyUtils#getProperty()���\�b�h���������Ăяo����A����������ɍs�Ȃ��邱�ƁB
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetBeanProperty06() throws Exception {
        // �e�X�g�pJavaBean����
        BeanUtil_BeanStub01 bean = new BeanUtil_BeanStub01();

        // �e�X�g���s�E����
        assertNull(BeanUtil.getBeanProperty(bean, "param2"));
    }

    /**
     * testGetBeanProperty07()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) bean:not null<br>
     *         (����) property:""<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:PropertyAccessException�F<br>
     *                    ���b�v������O�FNoSuchMethodException<br>
     *
     * <br>
     * property���󕶎��̎��APropertyAccessException���X���[���邱�ƁB
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetBeanProperty07() throws Exception {
        // �e�X�g�pJavaBean����
        BeanUtil_BeanStub01 bean = new BeanUtil_BeanStub01();

        // �e�X�g���s
        try {
            BeanUtil.getBeanProperty(bean, "");
            fail();
        } catch (PropertyAccessException e) {
            // �e�X�g���ʊm�F
            assertEquals(NoSuchMethodException.class.getName(), e.getCause()
                    .getClass().getName());
        }
    }
    
    /**
     * testGetBeanPropertyType01()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) bean:getParam1()���\�b�h����Exception����<br>
     *         (����) property:"param1"<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:PropertyAccessException�F<br>
     *                    ���b�v������O�FException<br>
     *
     * <br>
     * PropertyUtils#getProperty()���\�b�h��InvocationTargetException�������������A
     * InvocationTargetException�����b�v���Ă�����O�C���X�^���X��
     * PropertyAccessException�Ń��b�v���邱�ƁB
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetBeanPropertyType01() throws Exception {
        // �e�X�g�pJavaBean����
        BeanUtil_BeanStub01 bean = new BeanUtil_BeanStub01();

        // �e�X�g���s
        try {
            BeanUtil.getBeanPropertyType(bean, "param1(0).");
            fail();
        } catch (PropertyAccessException e) {
            // �e�X�g���ʊm�F
            assertSame(InvocationTargetException.class, e.getCause().getClass());
        }
    }

    /**
     * testGetBeanPropertyType02()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) bean:param3�������Ȃ�<br>
     *         (����) property:"param3"<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:PropertyAccessException�F<br>
     *                    ���b�v������O�FNoSuchMethodException<br>
     *
     * <br>
     * PropertyUtils#getProperty()���\�b�h��NoSuchMethodException�������������A
     * PropertyAccessException�Ń��b�v���邱�ƁB
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetBeanPropertyType02() throws Exception {
        // �e�X�g�pJavaBean����
        BeanUtil_BeanStub01 bean = new BeanUtil_BeanStub01();

        // �e�X�g���s
        assertNull(BeanUtil.getBeanPropertyType(bean, "param3"));
            
    }

    /**
     * testGetBeanPropertyType03()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) bean:null<br>
     *         (����) property:not null<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:PropertyAccessException�F<br>
     *                    ���b�v������O�FIllegalArgumentException<br>
     *
     * <br>
     * PropertyUtils#getProperty()���\�b�h��IllegalArgumentException�������������APropertyAccessException�Ń��b�v���邱�ƁB
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetBeanPropertyType03() throws Exception {
        // �e�X�g���s
        try {
            BeanUtil.getBeanPropertyType(null, "param1");
            fail();
        } catch (PropertyAccessException e) {
            // �e�X�g���ʊm�F
            assertEquals(IllegalArgumentException.class.getName(), e.getCause()
                    .getClass().getName());
        }
    }

    /**
     * testGetBeanPropertyType04()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) bean:not null<br>
     *         (����) property:null<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:PropertyAccessException�F<br>
     *                    ���b�v������O�FIllegalArgumentException<br>
     *
     * <br>
     * PropertyUtils#getProperty()���\�b�h��IllegalArgumentException�������������APropertyAccessException�Ń��b�v���邱�ƁB
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetBeanPropertyType04() throws Exception {
        // �e�X�g�pJavaBean����
        BeanUtil_BeanStub01 bean = new BeanUtil_BeanStub01();

        // �e�X�g���s
        try {
            BeanUtil.getBeanPropertyType(bean, null);
            fail();
        } catch (PropertyAccessException e) {
            // �e�X�g���ʊm�F
            assertEquals(IllegalArgumentException.class.getName(), e.getCause()
                    .getClass().getName());
        }
    }

    /**
     * testGetBeanPropertyType05()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) bean:param2="PARAM2"<br>
     *         (����) property:"param2"<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) Object:"PARAM2"<br>
     *
     * <br>
     * PropertyUtils#getProperty()���\�b�h���������Ăяo����A����������ɍs�Ȃ��邱�ƁB
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetBeanPropertyType05() throws Exception {
        // �e�X�g�pJavaBean����
        BeanUtil_BeanStub01 bean = new BeanUtil_BeanStub01();
        bean.setParam2("PARAM2");

        // �e�X�g���s�E����
        assertSame(String.class, BeanUtil.getBeanPropertyType(bean, "param2"));
    }

    /**
     * testGetBeanPropertyType06()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) bean:param2=null<br>
     *         (����) property:"param2"<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) Object:null<br>
     *
     * <br>
     * PropertyUtils#getProperty()���\�b�h���������Ăяo����A����������ɍs�Ȃ��邱�ƁB
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetBeanPropertyType06() throws Exception {
        // �e�X�g�pJavaBean����
        BeanUtil_BeanStub01 bean = new BeanUtil_BeanStub01();

        // �e�X�g���s�E����
        assertSame(String.class, BeanUtil.getBeanPropertyType(bean, "param2"));
    }

    /**
     * testGetBeanPropertyType07()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) bean:not null<br>
     *         (����) property:""<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:PropertyAccessException�F<br>
     *                    ���b�v������O�FNoSuchMethodException<br>
     *
     * <br>
     * property���󕶎��̎��APropertyAccessException���X���[���邱�ƁB
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetBeanPropertyType07() throws Exception {
        // �e�X�g�pJavaBean����
        Map map = new HashMap();

        // �e�X�g���s
        try {
            BeanUtil.getBeanPropertyType(map, "aa.(a)");
            fail();
        } catch (PropertyAccessException e) {
            // �e�X�g���ʊm�F
            assertEquals(NoSuchMethodException.class.getName(), e.getCause()
                    .getClass().getName());
        }
    }

    /**
     * testGetBeanPropertyType08()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) bean:BeanUtil_DynaBeanImpl01<br>
     *                testString(String�^)<br>
     *         (����) property:"testString"<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) Object:String.class<br>
     *         
     * <br>
     * DynaBean�̃t�B�[���h���z��ECollection�^�ł͂Ȃ��ꍇ �A����������ɍs�Ȃ��邱�ƁB
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetBeanPropertyType08() throws Exception {
        // �O����
        DynaProperty dynaProperty = new DynaProperty("testString", String.class);
        BeanUtil_DynaClassImpl01 dynaClass = new BeanUtil_DynaClassImpl01();
        dynaClass.setDynaProperty(dynaProperty);
        DynaBean bean = new BeanUtil_DynaBeanImpl01(dynaClass);        
        
        // �e�X�g���{
        // ����
        assertEquals(String.class, 
                BeanUtil.getBeanPropertyType(bean, "testString"));
    }

    /**
     * testGetBeanPropertyType09()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) bean:BeanUtil_DynaBeanImpl01<br>
     *                testArray(String[]�^)<br>
     *         (����) property:"testArray"<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) Object:String[].class<br>
     *         
     * <br>
     * DynaBean�̃t�B�[���h���z��ECollection�^�ł��ꍇ �A����������ɍs�Ȃ��邱�ƁB
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetBeanPropertyType09() throws Exception {
        // �O����
        DynaProperty dynaProperty = new DynaProperty("testArray", String[].class);
        BeanUtil_DynaClassImpl01 dynaClass = new BeanUtil_DynaClassImpl01();
        dynaClass.setDynaProperty(dynaProperty);
        DynaBean bean = new BeanUtil_DynaBeanImpl01(dynaClass);        
        
        // �e�X�g���{
        // ����
        assertEquals(String[].class, 
                BeanUtil.getBeanPropertyType(bean, "testArray"));
    }

    /**
     * testGetBeanPropertyType10()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) bean:BeanUtil_DynaBeanImpl01<br>
     *         (����) property:"testNull"<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) Object:null<br>
     *         
     * <br>
     * �w�肳�ꂽ������DynaBean�ɑ��݂��Ȃ��ꍇ�A����������ɍs�Ȃ��邱�ƁB
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetBeanPropertyType10() throws Exception {
        // �O����
        BeanUtil_DynaClassImpl01 dynaClass = new BeanUtil_DynaClassImpl01();
        dynaClass.setDynaProperty(null);
        DynaBean bean = new BeanUtil_DynaBeanImpl01(dynaClass);        
        
        // �e�X�g���{
        // ����
        assertEquals(null, 
                BeanUtil.getBeanPropertyType(bean, "testNull"));
    }    
}
