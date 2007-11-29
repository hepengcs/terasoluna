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

import junit.framework.TestCase;

/**
 * ClassUtil �u���b�N�{�b�N�X�e�X�g�B<br>
 * <br>
 * (�O�����)<br>
 *     �Ȃ�
 * <br>
 */
@SuppressWarnings("unused")
public class ClassUtilTest extends TestCase {

    /**
     * Constructor for ClassUtilTest.
     * @param arg0
     */
    public ClassUtilTest(String arg0) {
        super(arg0);
    }

    /*
     * @see TestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    /*
     * @see TestCase#tearDown()
     */
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * testCreate01(String)�B<br>
     * 
     * �i����n�j<br>
     * �ϓ_�FE<br>
     * <br>
     * ���͒l :��������I�u�W�F�N�g�̃N���X��<br>
     * ���Ғl :���������C���X�^���X<br>
     *
     * ��������I�u�W�F�N�g�̃N���X�������ɃC���X�^���X�𐶐����邱�Ƃ��m�F����B<br>
     *
     */
    public void testCreateString01() {
        //�����ݒ�
        String input = "java.lang.String";

        try {
            //�e�X�g���s
            Object obj = ClassUtil.create(input);

            //���ʊm�F
            assertEquals("java.lang.String", obj.getClass().getName());
        } catch (ClassLoadException e) {
            fail();
        }
    }

    /**
     * testCreate02(String)�B<br>
     * 
     * �i����n�j<br>
     * �ϓ_�FE<br>
     * <br>
     * ���͒l :��������I�u�W�F�N�g�̃N���X��(Terasoluna�Ή�)<br>
     * ���Ғl :���������C���X�^���X<br>
     *
     * ��������I�u�W�F�N�g��Terasoluna�ɑΉ������N���X��������
     * �C���X�^���X�𐶐����邱�Ƃ��m�F����B<br>
     *
     */
    public void testCreateString02() {
        //�����ݒ�
        String input = "jp.terasoluna.fw.util.ClassUtil";

        try {
            //�e�X�g���s
            Object obj = ClassUtil.create(input);

            //���ʊm�F
            assertEquals(
                "jp.terasoluna.fw.util.ClassUtil",
                obj.getClass().getName());
        } catch (ClassLoadException e) {
            fail();
        }
    }

    /**
     * testCreate03(String)�B<br>
     * 
     * �i����n�j<br>
     * �ϓ_�FG<br>
     * <br>
     * ���͒l :���ۃN���X�A�C���^�t�F�[�X�N���X��<br>
     * ���Ғl :InstantiationException<br>
     *
     * �w�肵���N���X��null�R���X�g���N�^��ێ����Ȃ��N���X�̏ꍇ�A
     * InstantiationException���������邱�Ƃ��m�F����B<br>
     *
     */
    public void testCreateString03() {
        //�����ݒ�
        String input = "javax.swing.AbstractAction";

        try {
            //�e�X�g���s
            ClassUtil.create(input);
            fail();
        } catch (ClassLoadException e) {
            if (e.getMessage().indexOf("InstantiationException") == -1) {
                //���ʊm�F
                fail();
            }
        }
    }

    /**
     * testCreate04(String)�B<br>
     * 
     * �i����n�j<br>
     * �ϓ_�FG<br>
     * <br>
     * ���͒l :*.class���Ȃ��N���X��<br>
     * ���Ғl :ClassNotFoundException<br>
     *
     * �w�肵���N���X�����݂��Ȃ��ꍇ�A
     * ClassNotFoundException���������邱�Ƃ��m�F����B<br>
     *
     */
    public void testCreateString04() {
        //�����ݒ�
        String input = "java.lang.Str";

        try {
            //�e�X�g���s
            ClassUtil.create(input);
            fail();
        } catch (ClassLoadException e) {
            if (e.getMessage().indexOf("ClassNotFoundException") == -1) {
                //���ʊm�F
                fail();
            }
        }
    }

    /**
     * testCreate05(String)�B<br>
     * 
     * �i����n�j<br>
     * �ϓ_�FG<br>
     * <br>
     * ���͒l :�A�N�Z�X�ł��Ȃ��R���X�g���N�^<br>
     * ���Ғl :IllegalAccessException<br>
     *
     * �w�肵���N���X�̃R���X�g���N�^�ɃA�N�Z�X�ł��Ȃ��ꍇ�A
     * IllegalAccessException���������邱�Ƃ��m�F����B<br>
     *
     */
    public void testCreateString05() {
        //�����ݒ�
        String input = "java.lang.Void";

        try {
            //�e�X�g���s
            ClassUtil.create(input);

        } catch (ClassLoadException e) {
            if (e.getMessage().indexOf("IllegalAccessException") == -1) {
                //���ʊm�F
                fail();
            }
        }
    }

    /**
     * testCreate01(String, Object[])�B<br>
     * 
     * �i����n�j<br>
     * �ϓ_�FE<br>
     * <br>
     * ���͒l :��������I�u�W�F�N�g�̃N���X���A�R���X�g���N�^�̃p�����[�^1��<br>
     * ���Ғl :���������C���X�^���X<br>
     *
     * �R���X�g���N�^�̈�����1�̎��A
     * ��������I�u�W�F�N�g�̃N���X�������ɃC���X�^���X�𐶐����邱�Ƃ��m�F����B<br>
     *
     */
    public void testCreateStringObjectArray01() {
        //�����ݒ�
        String input = "java.lang.Integer";

        try {
            //�e�X�g���s
            Object obj = ClassUtil.create(input, new Object[] { "12" });
            java.lang.Integer resultObj = (java.lang.Integer) obj;

            //���ʊm�F
            //�N���X��
            assertEquals("java.lang.Integer", obj.getClass().getName());

            //���e
            assertEquals("12", resultObj.toString());

        } catch (ClassLoadException e) {
            fail();
        }
    }

    /**
     * testCreate02(String, Object[])�B<br>
     * 
     * �i����n�j<br>
     * �ϓ_�FE<br>
     * <br>
     * ���͒l :��������I�u�W�F�N�g�̃N���X���A�R���X�g���N�^�̃p�����[�^����<br>
     * ���Ғl :���������C���X�^���X<br>
     *
     * �R���X�g���N�^�̈�������̎��A
     * ��������I�u�W�F�N�g�̃N���X�������ɃC���X�^���X�𐶐����邱�Ƃ��m�F����B<br>
     *
     */
    public void testCreateStringObjectArray02() {
        //�����ݒ�
        String input = "java.lang.String";

        try {
            //�e�X�g���s
            Object obj = ClassUtil.create(input, new Object[]{});
            java.lang.String resultObj = (java.lang.String) obj;

            //���ʊm�F
            //�N���X��
            assertEquals("java.lang.String", obj.getClass().getName());

            //���e
            assertEquals("", resultObj);

        } catch (ClassLoadException e) {
            fail();
        }
    }

    /**
     * testCreate03(String, Object[])�B<br>
     * 
     * �i����n�j<br>
     * �ϓ_�FE<br>
     * <br>
     * ���͒l :��������I�u�W�F�N�g�̃N���X���A�R���X�g���N�^�̃p�����[�^2��<br>
     * ���Ғl :���������C���X�^���X<br>
     *
     * �R���X�g���N�^�̈�����2�̎��A
     * ��������I�u�W�F�N�g�̃N���X�������ɃC���X�^���X�𐶐����邱�Ƃ��m�F����B<br>
     *
     */
    public void testCreateStringObjectArray03() {
        //�����ݒ�
        String input = "java.util.Locale";
        String language = "es";
        String country = "MEXICO";

        try {
            //�e�X�g���s
            Object obj =
                ClassUtil.create(input, new Object[] { language, country });
            java.util.Locale resultObj = (java.util.Locale) obj;

            //���ʊm�F
            //�N���X��
            assertEquals("java.util.Locale", obj.getClass().getName());

            //���e
            assertEquals("MEXICO", resultObj.getCountry());
            assertEquals("es", resultObj.getLanguage());

        } catch (ClassLoadException e) {
            fail();
        }
    }

    /**
     * testCreate04(String, Object[])�B<br>
     * 
     * �i����n�j<br>
     * �ϓ_�FE<br>
     * <br>
     * ���͒l :��������I�u�W�F�N�g�̃N���X��(Terasoluna�Ή�)�A�R���X�g���N�^�̃p�����[�^<br>
     * ���Ғl :���������C���X�^���X<br>
     *
     * ��������I�u�W�F�N�g��Terasoluna�ɑΉ������N���X��������
     * �C���X�^���X�𐶐����邱�Ƃ��m�F����B<br>
     *
     */
    public void testCreateStringObjectArray04() {
        //�����ݒ�
        String input = ClassUtil.class.getName();

        try {
            //�e�X�g���s
            Object obj = ClassUtil.create(input, new Object[] {});

            //���ʊm�F
            assertEquals(input, obj.getClass().getName());

        } catch (ClassLoadException e) {
            fail();
        }
    }

    /**
     * testCreate05(String, Object[])�B<br>
     * 
     * �i����n�j<br>
     * �ϓ_�FG<br>
     * <br>
     * ���͒l :*.class���Ȃ��N���X��<br>
     * ���Ғl :ClassNotFoundException<br>
     *
     * �w�肵���N���X�����݂��Ȃ��ꍇ�AClassNotFoundException���������A
     * ��������b�v����ClassLoadException���������邱�Ƃ��m�F����B<br>
     *
     */
    public void testCreateStringObjectArray05() {
        //�����ݒ�
        String input = "java.lang.Str";

        try {
            //�e�X�g���s
            ClassUtil.create(input, new Object[] {});
        } catch (ClassLoadException e) {
            if (e.getMessage().indexOf("ClassNotFoundException") == -1) {
                //���ʊm�F
                fail();
            }
        }
    }

    /**
     * testCreate06(String, Object[])�B<br>
     * 
     * �i����n�j<br>
     * �ϓ_�FG<br>
     * <br>
     * ���͒l :���ۃN���X<br>
     * ���Ғl :InstantiationException<br>
     * 
     * �w�肵���N���X�����ۃN���X�̏ꍇ�AInstantiationException���������A
     * ��������b�v�����OClassLoadException���������邱�Ƃ��m�F����B<br>
     *
     */
    public void testCreateStringObjectArray06() {
        //�����ݒ�
        String input = "javax.swing.AbstractAction";

        try {
            //�e�X�g���s
            ClassUtil.create(input, new Object[] { "1", "2" });
        } catch (ClassLoadException e) {
            if (e.getMessage().indexOf("InstantiationException") == -1) {
                //���ʊm�F
                fail();
            }
        }
    }

    /**
     * testCreate07(String, Object[])�B<br>
     * 
     * �i����n�j<br>
     * �ϓ_�FG<br>
     * <br>
     * ���͒l :��������I�u�W�F�N�g�̃N���X���A�R���X�g���N�^�̃p�����[�^null<br>
     * ���Ғl :InvocationTargetException<br>
     *
     * �I�u�W�F�N�g�������ł��Ȃ������ꍇ�AInvocationTargetException���������A
     * ��������b�v�����OClassLoadException���������邱�Ƃ��m�F����B<br>
     *
     */
    public void testCreateStringObjectArray07() {
        //�����ݒ�
        String input = "java.lang.String";

        try {
            //�e�X�g���s
            ClassUtil.create(input, new Object[] { null });
        } catch (ClassLoadException e) {
            if (e.getMessage().indexOf("InvocationTargetException") == -1) {
                //���ʊm�F
                fail();
            }
        }
    }

    /**
     * testCreate08(String, Object[])�B<br>
     * 
     * �i����n�j<br>
     * �ϓ_�FG<br>
     * <br>
     * ���͒l :��������I�u�W�F�N�g�̃N���X���A�R���X�g���N�^�̃p�����[�^<br>
     * ���Ғl :IllegalArgumentException<br>
     *
     * �I�u�W�F�N�g�������ł��Ȃ������ꍇ�AIllegalArgumentException���������A
     * ��������b�v�����OClassLoadException���������邱�Ƃ��m�F����B<br>
     *
     */
    public void testCreateStringObjectArray08() {
        //�����ݒ�
        String input = "java.lang.Void";
        try {
            //�e�X�g���s
            ClassUtil.create(input, new Object[] {});
        } catch (ClassLoadException e) {
            if (e.getMessage().indexOf("IllegalArgumentException") == -1) {
                //���ʊm�F
                fail();
            }
            if (e.getCause().getMessage().indexOf("class name is java.lang.Void") == -1) {
                //���ʊm�F
                fail();
            }
        }
    }
}
