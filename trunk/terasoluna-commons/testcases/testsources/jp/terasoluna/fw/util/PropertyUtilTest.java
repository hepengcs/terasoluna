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

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.Vector;

import jp.terasoluna.utlib.LogUTUtil;
import jp.terasoluna.utlib.PropertyTestCase;
import jp.terasoluna.utlib.UTUtil;

/**
 * 
 * PropertyUtil �u���b�N�{�b�N�X�e�X�g�B<br>
 *
 * (�O�����)<br>
 *�@�E�v���p�e�B�t�@�C���Ɉȉ��̂悤�Ȑݒ�����Ă���<br>
 *         property.test001.id.0 = test<br>
 *         property.test002.id.0 = test0<br>
 *         property.test002.id.1 = test1<br>
 *         property.test002.id.2 = test2<br>
 *         property.test003.id.0 =<br>
 *         property.test004.id.0 = testA<br>
 *         property.test004.id.0 = testB<br>
 *         property.test004.id.1 = testA<br>
 *         fileutiltest.dir.base = /tmp/test<br>
 *         codelist.gengo1.define.1 = �]��<br>
 *         codelist.gengo1.define.2 = ����<br>
 *         codelist.gengo1.define.3 = �吳<br>
 *         codelist.gengo2.define.1 = ���a<br>
 *         codelist.gengo2.define.2 = ����<br>
 *         codelist.sql1.sql.0=select values01,values01,values01 from table_kamoTest where Key1 between ? and ?<br>
 *         @property.test0 = testtest<br>
 *         property.test100.id.0 = @property.test100.id.0<br>
 *         property.test005 = @property.test001.id.0<br>
 *         property.test007.id.0=@@test007<br>
 *         property.test008.id.0=@@<br>
 *         property.test009.id.0=@<br>
 * 
 */
@SuppressWarnings("unused")
public class PropertyUtilTest extends PropertyTestCase {

    /**
     * Constructor for PropertyUtilTest.
     * @param arg0
     */
    public PropertyUtilTest(String arg0) {
        super(arg0);
    }

    @Override
    protected void setUpData() throws Exception {
        addProperty("system.name", "SAMPLE1");
        addProperty("fileutiltest.dir.base", "/tmp/test");
        addProperty("property.test001.id.0", "test");
        addProperty("property.test002.id.0", "test0");
        addProperty("property.test002.id.1", "test1");
        addProperty("property.test002.id.2", "test2");
        addProperty("property.test003.id.0", "");
        addProperty("property.test004.id.0", "testA");
        addProperty("property.test004.id.0", "testB");
        addProperty("@property.test0", "testtest");
        addProperty("property.test100.id.0", "@property.test100.id.0");
        addProperty("property.test005", "@property.test001.id.0");
        addProperty("property.test006.id.0", "testA");
        addProperty("property.test007.id.0", "@@test007");
        addProperty("property.test008.id.0", "@@");
        addProperty("property.test009.id.0", "@");
        addProperty("codelist.gengo1.define.1", "�]��");
        addProperty("codelist.gengo1.define.2", "����");
        addProperty("codelist.gengo1.define.3", "�吳");
        addProperty("codelist.gengo2.define.1", "���a");
        addProperty("codelist.gengo2.define.2", "����");
        addProperty("codelist.sql1.sql.0", "select values01,values01,values01 from table_kamoTest where Key1 between ? and ?");
    }

    @Override
    protected void cleanUpData() throws Exception {
        clearProperty();
    }

    /**
     * testAddPropertyFile01()�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FF<br>
     * 
     * ���͒l�F���݂���v���p�e�B�t�@�C��(.properties�Ȃ�)<br>
     * ���Ғl�FPropertyUtil�N���X��files�t�B�[���h�Ƀt�@�C�������܂܂�Ă��邱��<br>
     * @throws Exception ��O */
    public void testAddPropertyFile01() throws Exception {
        // ���͒l�̐ݒ�
        String input = "system";

        // �e�X�g�Ώۂ̎��s
        PropertyUtil.addPropertyFile(input);

        // ���ʊm�F
        // PropertyUtil�N���X��files�t�B�[���h�Ƀt�@�C�������܂܂�Ă��邱��
        Set st = (Set) UTUtil.getPrivateField(PropertyUtil.class, "files");
        assertTrue(st.contains("system.properties"));
    }

    /**
     * testAddPropertyFile02()�B<br>
     *
     * (����n)<br>
     * �ϓ_�FF<br>
     * 
     * ���͒l�F���݂���v���p�e�B�t�@�C��(.properties����)<br>
     * ���Ғl�FPropertyUtil�N���X��files�t�B�[���h�Ƀt�@�C�������܂܂�Ă��邱��<br>
     * @throws Exception ��O */
    public void testAddPropertyFile02() throws Exception {
        // ���͒l�̐ݒ�
        String input = "system.properties";

        // �e�X�g�Ώۂ̎��s
        PropertyUtil.addPropertyFile(input);

        // ���ʊm�F
        // PropertyUtil�N���X��files�t�B�[���h�Ƀt�@�C�������܂܂�Ă��邱��
        Set st = (Set) UTUtil.getPrivateField(PropertyUtil.class, "files");
        assertTrue(st.contains("system.properties"));
    }

    /**
     * testAddPropertyFile03()�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FC,F<br>
     * 
     * ���͒l�F���݂��Ȃ��v���p�e�B�t�@�C��<br>
     * ���Ғl�FPropertyUtil�N���X��files�t�B�[���h�Ƀt�@�C�������܂܂�Ă��Ȃ�����<br>
     * @throws Exception ��O */
    public void testAddPropertyFile03() throws Exception {
        // ���͒l�̐ݒ�
        String input = "xxxxx";

        // �e�X�g�Ώۂ̎��s
        PropertyUtil.addPropertyFile(input);

        // ���ʂ̊m�F
        // PropertyUtil�N���X��files�t�B�[���h�Ƀt�@�C�������܂܂�Ă��Ȃ�����
        Set st = (Set) UTUtil.getPrivateField(PropertyUtil.class, "files");
        assertFalse(st.contains("xxxxx.properties"));
    }

    /**
     * testAddPropertyFile04()�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FC,G<br>
     * 
     * ���͒l�Fnull<br>
     * ���Ғl�FNullPointerException<br>
     * @throws Exception ��O */
    public void testAddPropertyFile04() throws Exception {
        // ���͒l�̐ݒ�
        String input = null;

        // �e�X�g�Ώۂ̎��s
        try {
            PropertyUtil.addPropertyFile(input);
            fail();
        } catch (NullPointerException e) {
            return;
        }
    }

    /**
     * testAddPropertyFile05()�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FC,F<br>
     * 
     * ���͒l�F""(�󕶎�)<br>
     * ���Ғl�F<br>
     * @throws Exception ��O */
    public void testAddPropertyFile05() throws Exception {
        // ���͒l�̐ݒ�
        String input = "";

        // �e�X�g�Ώۂ̎��s
        PropertyUtil.addPropertyFile(input);

        // ���ʂ̊m�F
        // PropertyUtil�N���X��files�t�B�[���h�Ƀt�@�C�������܂܂�Ă��Ȃ�����
        Set st = (Set) UTUtil.getPrivateField(PropertyUtil.class, "files");
        assertFalse(st.contains(".properties"));
    }

    /**
     * testAddPropertyFile06()�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FF<br>
     * 
     * ���͒l�F���݂���v���p�e�B�t�@�C���A������ǂݍ���<br>
     * ���Ғl�F��x�����ǂݍ��܂�Ȃ����Ƃ�<br>
     * @throws Exception ��O */
    public void testAddPropertyFile06() throws Exception {
        // ���͒l�̐ݒ�
        String input = "system";

        // �e�X�g�Ώۂ̎��s
        PropertyUtil.addPropertyFile(input);
        PropertyUtil.addPropertyFile(input);
        // ���ʊm�F
        // PropertyUtil�N���X��files�t�B�[���h�Ƀt�@�C�������܂܂�Ă��邱��
        Set st = (Set) UTUtil.getPrivateField(PropertyUtil.class, "files");
        assertTrue(st.contains("system.properties"));
        assertTrue(st.size() == 1);
    }

    /**
     * testGetProperty01(String)�B<br> 
     * 
     * (����n)<br>
     * �ϓ_�FF<br>
     * 
     * ���͒l�F���݂���L�[<br>
     * ���Ғl�F�L�[�̃v���p�e�B�l<br>
     * @throws Exception ��O */
    public void testGetPropertyString01() throws Exception {
        // ���͒l�̐ݒ�
        String input = "property.test001.id.0";

        // �e�X�g�Ώۂ̎��s
        String str = PropertyUtil.getProperty(input);

        // ���ʊm�F
        assertEquals("test", str);
    }

    /**
     * testGetProperty02(String)�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FC,F<br>
     * 
     * ���͒l�F���݂��Ȃ��L�[<br>
     * ���Ғl�FNull�l<br>
     * @throws Exception ��O */
    public void testGetPropertyString02() throws Exception {
        // ���͒l�̐ݒ�
        String input = "property.test001.id.1";

        // �e�X�g�Ώۂ̎��s
        String str = PropertyUtil.getProperty(input);

        // ���ʊm�F
        assertNull(str);
    }

    /**
     * testGetProperty03(String) �B<br>
     * 
     * (�ُ�n)<br>
     * �ϓ_�FG<br>
     * 
     * ���͒l�Fnull<br>
     * ���Ғl�FNullPointerException<br>
     * @throws Exception ��O */
    public void testGetPropertyString03() throws Exception {
        // ���͒l�̐ݒ�
        String input = null;

        // �e�X�g�Ώۂ̎��s
        try {
            String str = PropertyUtil.getProperty(input);
            fail();
        } catch (NullPointerException e) {
            return;
        }
    }

    /**
     * testGetProperty04(String)�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FC<br>
     * 
     * ���͒l�F""(�󕶎�)<br>
     * ���Ғl�FNull�l<br>
     * @throws Exception ��O */
    public void testGetPropertyString04() throws Exception {
        // ���͒l�̐ݒ�
        String input = "";

        // �e�X�g�Ώۂ̎��s
        String str = PropertyUtil.getProperty(input);

        // ���ʊm�F
        assertNull(str);
    }

    /**
     * testGetProperty05(String) �B<br>
     * 
     * (����n)<br>
     * �ϓ_�FC,F<br>
     * 
     * ���͒l�F�v���p�e�B�l���󕶎��̃L�[<br>
     * ���Ғl�F�󕶎�<br>
     * @throws Exception ��O */
    public void testGetPropertyString05() throws Exception {
        // ���͒l�̐ݒ�
        String input = "property.test003.id.0";

        // �e�X�g�Ώۂ̎��s
        String str = PropertyUtil.getProperty(input);

        // ���ʊm�F
        assertEquals("", str);
    }

    /**
     * testGetProperty06(String)�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FF<br>
     * 
     * ���͒l�F�������݂���L�[<br>
     * ���Ғl�F��ɐݒ肳�ꂽ�L�[�̃v���p�e�B�l<br>
     * @throws Exception ��O */
    public void testGetPropertyString06() throws Exception {
        // ���͒l�̐ݒ�
        String input = "property.test004.id.0";

        // �e�X�g�Ώۂ̎��s
        String str = PropertyUtil.getProperty(input);

        // ���ʊm�F
        assertEquals("testB", str);
    }

    /**
     * testGetProperty07(String) �B<br>
     * 
     * (����n)<br>
     * �ϓ_�FF<br>
     * 
     * ���͒l�F<code>@key</code><br>
     * ���Ғl�F��ɐݒ肳�ꂽ�L�[�̃v���p�e�B�l<br>
     * @throws Exception ��O */
    public void testGetPropertyString07() throws Exception {
        // ���͒l�̐ݒ�
        String input = "@property.test0";

        // �e�X�g�Ώۂ̎��s
        String str = PropertyUtil.getProperty(input);

        // ���ʊm�F
        assertEquals("testtest", str);
    }

    /**
     * testGetProperty08(String)�B<br> 
     * 
     * (����n)<br>
     * �ϓ_�FA,F<br>
     * 
     * ���͒l�F<code>key=@key</code><br>
     * ���Ғl�F��ɐݒ肳�ꂽ�L�[�̃v���p�e�B�l<br>
     * @throws Exception ��O */
    public void testGetPropertyString08() throws Exception {
        // ���͒l�̐ݒ�
        String input = "property.test100.id.0";

        // �e�X�g�Ώۂ̎��s
        String str = PropertyUtil.getProperty(input);

        // ���ʊm�F
        assertEquals("@property.test100.id.0", str);
    }

    /**
     * testGetProperty09(String)�B<br> 
     * 
     * (����n)<br>
     * �ϓ_�FA,F<br>
     * 
     * ���͒l�F<code>key=@value</code><br>
     * ���Ғl�F<code>@</code>���O�����v���p�e�B�l<br>
     * @throws Exception ��O */
    public void testGetPropertyString09() throws Exception {
        // ���͒l�̐ݒ�
        String input = "property.test005";

        // �e�X�g�Ώۂ̎��s
        String str = PropertyUtil.getProperty(input);

        // ���ʊm�F
        assertEquals("test", str);
    }

    /**
     * testGetProperty10(String)�B<br> 
     * 
     * (����n)<br>
     * �ϓ_�FA,F<br>
     * 
     * ���͒l�F<code>key=@@value</code><br>
     * ���Ғl�F@value<br>
     * property.test007.id.0=@@test007�ƃv���p�e�B�t�@�C���ɐݒ肵�A
     * @test007�������邱�Ƃ��m�F����B
     * @throws Exception ��O */
    public void testGetPropertyString10() throws Exception {
        // ���͒l�̐ݒ�
        String input = "property.test007.id.0";

        // �e�X�g�Ώۂ̎��s
        String str = PropertyUtil.getProperty(input);

        // ���ʊm�F
        assertEquals("@test007", str);
    }
    
    /**
     * testGetProperty12(String)�B<br> 
     * 
     * (����n)<br>
     * �ϓ_�FA,F<br>
     * 
     * ���͒l�F<code>key=@@</code><br>
     * ���Ғl�F@<br>
     * property.test008.id.0=@@�ƃv���p�e�B�t�@�C���ɐݒ肵�A
     * @�������邱�Ƃ��m�F����B
     * @throws Exception ��O */
    public void testGetPropertyString11() throws Exception {
        // ���͒l�̐ݒ�
        String input = "property.test008.id.0";

        // �e�X�g�Ώۂ̎��s
        String str = PropertyUtil.getProperty(input);

        // ���ʊm�F
        assertEquals("@", str);
    }
    
    /**
     * testGetProperty13(String)�B<br> 
     * 
     * (����n)<br>
     * �ϓ_�FA,F<br>
     * 
     * ���͒l�F<code>key=@</code><br>
     * ���Ғl�F@<br>
     * property.test009.id.0=@�ƃv���p�e�B�t�@�C���ɐݒ肵�A
     * Null���Ԃ��ė��邱�Ƃ��m�F����B
     * @throws Exception ��O */
    public void testGetPropertyString12() throws Exception {
        // ���͒l�̐ݒ�
        String input = "property.test009.id.0";

        // �e�X�g�Ώۂ̎��s
        String str = PropertyUtil.getProperty(input);

        // ���ʊm�F
        assertNull(str);
    }
    
    /**
     * testGetProperty01(String, String)�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FF<br>
     * 
     * ���͒l�Fkey=���݂���L�[<br>
     * �@�@�@�@default=�f�t�H���g�l<br>
     * ���Ғl�F�L�[�̃v���p�e�B�l<br>
     * @throws Exception ��O */
    public void testGetPropertyStringString01() throws Exception {
        // ���͒l�̐ݒ�
        String input1 = "property.test001.id.0";
        String input2 = "default";

        // �e�X�g�Ώۂ̎��s
        String str = PropertyUtil.getProperty(input1, input2);

        // ���ʊm�F
        assertEquals("test", str);
    }

    /**
     * testGetProperty02(String, String)�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FF<br>
     * 
     * ���͒l�Fkey=���݂��Ȃ��L�[<br>
     * �@�@�@�@default=�f�t�H���g�l<br>
     * ���Ғl�F�f�t�H���g�l<br>
     * @throws Exception ��O */
    public void testGetPropertyStringString02() throws Exception {
        // ���͒l�̐ݒ�
        String input1 = "property.test001.id.1";
        String input2 = "default";

        // �e�X�g�Ώۂ̎��s
        String str = PropertyUtil.getProperty(input1, input2);

        // ���ʊm�F
        assertEquals("default", str);
    }

    /**
     * testGetProperty03(String, String)�B<br>
     * 
     * (�ُ�n)<br>
     * �ϓ_�FC,F<br>
     * 
     * ���͒l�Fkey=null<br>
     * �@�@�@�@default=�f�t�H���g�l<br>
     * ���Ғl�FNullPointerException<br>
     * @throws Exception ��O */
    public void testGetPropertyStringString03() throws Exception {
        // ���͒l�̐ݒ�
        String input1 = null;
        String input2 = "default";

        // �e�X�g�Ώۂ̎��s
        try {
            String str = PropertyUtil.getProperty(input1, input2);
            fail();
        } catch (NullPointerException e) {
            return;
        }
    }

    /**
     * testGetProperty04(String, String)�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FC,F<br>
     * 
     * ���͒l�Fkey=���݂���L�[<br>
     * �@�@�@�@default=null<br>
     * ���Ғl�F�L�[�̃v���p�e�B�l<br>
     * @throws Exception ��O */
    public void testGetPropertyStringString04() throws Exception {
        // ���͒l�̐ݒ�
        String input1 = "property.test001.id.0";
        String input2 = null;

        // �e�X�g�Ώۂ̎��s
        String str = PropertyUtil.getProperty(input1, input2);

        // ���ʊm�F
        assertEquals("test", str);
    }

    /**
     * testGetProperty05(String, String)�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FC<br>
     * 
     * ���͒l�Fkey=���݂��Ȃ��L�[<br>
     * �@�@�@�@default=null<br>
     * ���Ғl�Fnull(�f�t�H���g)<br>
     * @throws Exception ��O */
    public void testGetPropertyStringString05() throws Exception {
        // ���͒l�̐ݒ�
        String input1 = "property.test001.id.1";
        String input2 = null;

        // �e�X�g�Ώۂ̎��s
        String str = PropertyUtil.getProperty(input1, input2);

        // ���ʊm�F
        assertNull(str);
    }

    /**
     * testGetProperty06(String, String) �B<br>
     * 
     * (����n)<br>
     * �ϓ_�FC,F<br>
     * 
     * ���͒l�Fkey=""(�󕶎�)<br>
     * �@�@�@�@default=�f�t�H���g�l<br>
     * ���Ғl�F�f�t�H���g�l<br>
     * @throws Exception ��O */
    public void testGetPropertyStringString06() throws Exception {
        // ���͒l�̐ݒ�
        String input1 = "";
        String input2 = "default";

        // �e�X�g�Ώۂ̎��s
        String str = PropertyUtil.getProperty(input1, input2);

        // ���ʊm�F
        assertEquals("default", str);
    }

    /**
     * testGetProperty07(String, String)�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FC,F<br>
     * 
     * ���͒l�Fkey=���݂���L�[<br>
     * �@�@�@�@default=""(�󕶎�)<br>
     * ���Ғl�F�L�[�̃v���p�e�B�l<br>
     * @throws Exception ��O */
    public void testGetPropertyStringString07() throws Exception {
        // ���͒l�̐ݒ�
        String input1 = "property.test001.id.0";
        String input2 = "";

        // �e�X�g�Ώۂ̎��s
        String str = PropertyUtil.getProperty(input1, input2);

        // ���ʊm�F
        assertEquals("test", str);
    }

    /**
     * testGetProperty08(String, String)�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FC,F<br>
     * 
     * ���͒l�Fkey=���݂��Ȃ��L�[<br>
     * �@�@�@�@default=""(�󕶎�)<br>
     * ���Ғl�F""(�f�t�H���g)<br>
     * @throws Exception ��O */
    public void testGetPropertyStringString08() throws Exception {
        // ���͒l�̐ݒ�
        String input1 = "property.test001.id.1";
        String input2 = "";

        // �e�X�g�Ώۂ̎��s
        String str = PropertyUtil.getProperty(input1, input2);

        // ���ʊm�F
        assertEquals("", str);
    }

    /**
     * testGetProperty09(String, String)�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FC,F<br>
     * 
     * ���͒l�Fkey=�v���p�e�B�l��""(�󕶎�)�̃L�[<br>
     * �@�@�@�@default=�f�t�H���g�l<br>
     * ���Ғl�F�󕶎�<br>
     * @throws Exception ��O */
    public void testGetPropertyStringString09() throws Exception {
        // ���͒l�̐ݒ�
        String input1 = "property.test003.id.0";
        String input2 = "default";

        // �e�X�g�Ώۂ̎��s
        String str = PropertyUtil.getProperty(input1, input2);

        // ���ʊm�F
        assertEquals("", str);
    }

    /**
     * testGetProperty10(String, String)�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FF<br>
     * 
     * ���͒l�Fkey=�������݂���L�[<br>
     * �@�@�@�@default=�f�t�H���g�l<br>
     * ���Ғl�F�L�[�̃v���p�e�B�l<br>
     * @throws Exception ��O */
    public void testGetPropertyStringString10() throws Exception {
        // ���͒l�̐ݒ�
        String input1 = "property.test004.id.0";
        String input2 = "default";

        // �e�X�g�Ώۂ̎��s
        String str = PropertyUtil.getProperty(input1, input2);

        // ���ʊm�F
        assertEquals("testB", str);
    }

    /**
     * testGetPropertyNames01()�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FF<br>
     * 
     * ���͒l�F�Ȃ�<br>
     * ���Ғl�F���ׂẴL�[<br>
     * @throws Exception ��O */
    public void testGetPropertyNames01() throws Exception {
        // �e�X�g�Ώۂ̎��s
        Enumeration en = PropertyUtil.getPropertyNames();

        // ���ʂ̊m�F
        // Enumeration����v�f�����o���AVector�z��ɒǉ�����
        Vector<String> v = new Vector<String>();
        while (en.hasMoreElements()) {
            String key = (String) en.nextElement();
            v.add(key);
        }
        // �L�[�̑��݊m�F
        assertTrue(v.contains("system.name"));
        assertTrue(v.contains("property.test001.id.0"));
        assertTrue(v.contains("property.test002.id.0"));
        assertTrue(v.contains("property.test002.id.1"));
        assertTrue(v.contains("property.test002.id.2"));
        assertTrue(v.contains("property.test003.id.0"));
        assertTrue(v.contains("property.test004.id.0"));
        assertTrue(v.contains("property.test004.id.0"));
        assertTrue(v.contains("@property.test0"));
        assertTrue(v.contains("fileutiltest.dir.base"));
        assertTrue(v.contains("property.test100.id.0"));
        assertTrue(v.contains("property.test005"));
        assertTrue(v.contains("property.test006.id.0"));
        assertTrue(v.contains("property.test007.id.0"));
        assertTrue(v.contains("property.test008.id.0"));
        assertTrue(v.contains("property.test009.id.0"));
        assertTrue(v.contains("codelist.gengo1.define.1"));
        assertTrue(v.contains("codelist.gengo1.define.2"));
        assertTrue(v.contains("codelist.gengo1.define.3"));
        assertTrue(v.contains("codelist.gengo2.define.1"));
        assertTrue(v.contains("codelist.gengo2.define.2"));
        assertTrue(v.contains("codelist.sql1.sql.0"));
    }

    /**
     * testGetPropertyNames01(String) �B<br>
     *  
     * (����n)<br>
     * �ϓ_�FF<br>
     * 
     * ���͒l�Fkey=���݂���v���t�B�b�N�X<br>
     * ���Ғl�F�L�[���X�g(1��)<br>
     * @throws Exception ��O */
    public void testGetPropertyNamesString01() throws Exception {
        // ���͒l�̐ݒ�
        String input1 = "property.test001.id";

        // �e�X�g�Ώۂ̎��s
        Enumeration enume = PropertyUtil.getPropertyNames(input1);

        // ���ʊm�F
        assertEquals("property.test001.id.0", enume.nextElement());
        assertFalse(enume.hasMoreElements());
    }

    /**
     * testGetPropertyNames02(String)�B<br>
     *  
     * (����n)<br>
     * �ϓ_�FC,F<br>
     * 
     * ���͒l�Fkey=���݂��Ȃ��v���t�B�b�N�X<br>
     * ���Ғl�F�L�[���X�g(0��)<br>
     * @throws Exception ��O */
    public void testGetPropertyNamesString02() throws Exception {
        // ���͒l�̐ݒ�
        String input1 = "property.test999.id";

        // �e�X�g�Ώۂ̎��s
        Enumeration enume = PropertyUtil.getPropertyNames(input1);

        // ���ʊm�F
        assertFalse(enume.hasMoreElements());
    }

    /**
     * testGetPropertyNames03(String)�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FF<br>
     * 
     * ���͒l�Fkey=���݂���v���t�B�b�N�X<br>
     * ���Ғl�F�L�[���X�g(3��)<br>
     * @throws Exception ��O */
    public void testGetPropertyNamesString03() throws Exception {
        // ���͒l�̐ݒ�
        String input1 = "property.test002.id";

        // �e�X�g�Ώۂ̎��s
        Enumeration enume = PropertyUtil.getPropertyNames(input1);

        // ���ʊm�F
        assertEquals("property.test002.id.0", enume.nextElement());
        assertEquals("property.test002.id.1", enume.nextElement());
        assertEquals("property.test002.id.2", enume.nextElement());
        assertFalse(enume.hasMoreElements());
    }

    /**
     * testGetPropertyNames04(String) �B<br>
     * 
     * (�ُ�n)<br>
     * �ϓ_�FC,G<br>
     * 
     * ���͒l�Fkey=null<br>
     * ���Ғl�FNullPointerException<br>
     * @throws Exception ��O */
    public void testGetPropertyNamesString04() throws Exception {
        // ���͒l�̐ݒ�
        String input1 = null;

        // �e�X�g�Ώۂ̎��s
        try {
            Enumeration enume = PropertyUtil.getPropertyNames(input1);
            fail();
        } catch (NullPointerException e) {
            return;
        }
    }

    /**
     * testGetPropertyNames05(String)�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FC,F<br>
     * 
     * ���͒l�Fkey=""<br>
     * ���Ғl�F�L�[���X�g(�S��)<br>
     * @throws Exception ��O */
    @SuppressWarnings("unchecked")
    public void testGetPropertyNamesString05() throws Exception {
        // ���͒l�̐ݒ�
        String input1 = "";

        // �e�X�g�Ώۂ̎��s
        Enumeration actualEnum = PropertyUtil.getPropertyNames(input1);

        // ���ʊm�F
        Map expectedProps =
            (Map) UTUtil.getPrivateField(PropertyUtil.class, "props");
        Enumeration expectedEnum = Collections.enumeration(expectedProps.keySet());
        while (expectedEnum.hasMoreElements()) {
            String actualStr = (String) actualEnum.nextElement();
            String expectedStr = (String) expectedEnum.nextElement();
            assertEquals(expectedStr, actualStr);
        }
    }

    /**
     * testGetPropertyNames06(String)�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FF<br>
     * 
     * ���͒l�Fkey=�������݂���L�[�̃v���t�B�b�N�X<br>
     * ���Ғl�F�L�[���X�g(1��)<br>
     * @throws Exception ��O */
    public void testGetPropertyNamesString06() throws Exception {
        // ���͒l�̐ݒ�
        String input1 = "property.test004.id";

        // �e�X�g�Ώۂ̎��s
        Enumeration enume = PropertyUtil.getPropertyNames(input1);

        // ���ʊm�F
        assertEquals("property.test004.id.0", enume.nextElement());
        assertFalse(enume.hasMoreElements());
    }

    /**
     * testGetPropertiesValues01(String, String)�B<br>
     * 
     * �i����n�j<br>
     * �ϓ_�FF<br>
     * <br>
     * ���͒l :�v���p�e�B�t�@�C����,�����L�[������<br>
     * ���Ғl :�l�Z�b�g�i���g���P�j<br>
     *
     * �����F�����L�[������ɊY������l���P�̎��A
     * �w�肳�ꂽ�v���p�e�B�t�@�C������l���擾����邱�Ƃ��m�F����B
     *
     * @throws Exception ��O */
    public void testGetPropertiesValuesString01() throws Exception {
        // ���͒l�̐ݒ�
        //�v���p�e�B�t�@�C����
        String input = "test";
        //�����L�[������
        String key = "file";
        // �e�X�g�Ώۂ̎��s
        Set result = PropertyUtil.getPropertiesValues(input, key);

        // ���ʊm�F
        assertTrue(result.contains("/tmp/test"));
    }

    /**
     * testGetPropertiesValues02(String, String)�B<br>
     * 
     * �i����n�j<br>
     * �ϓ_�FA,F<br>
     * <br>
     * ���͒l :�v���p�e�B�t�@�C����,�����L�[������<br>
     * ���Ғl :�l�Z�b�g�i���g�������j<br>
     *
     * �����F�����L�[������ɊY������l�������̎��A
     * �w�肳�ꂽ�v���p�e�B�t�@�C������l�Z�b�g���擾����邱�Ƃ��m�F����B
     *
     * @throws Exception ��O */
    public void testGetPropertiesValuesString02() throws Exception {
        // ���͒l�̐ݒ�
        //�v���p�e�B�t�@�C����
        String input = "test";
        //�����L�[������
        String key = "code";
        // �e�X�g�Ώۂ̎��s
        Set result = PropertyUtil.getPropertiesValues(input, key);

        // ���ʊm�F
        assertTrue(
            result.contains(
                "select values01,values01,values01 "
                    + "from table_kamoTest where Key1 between ? and ?"));
        assertTrue(result.contains("\u660e\u6cbb"));
        assertTrue(result.contains("\u662d\u548c"));
        assertTrue(result.contains("\u6c5f\u6238"));
        assertTrue(result.contains("\u5e73\u6210"));
        assertTrue(result.contains("\u5927\u6b63"));
    }

    /**
     * testGetPropertiesValues03(String, String)�B<br>
     * 
     * �i����n�j<br>
     * �ϓ_�FC<br>
     * <br>
     * ���͒l :�v���p�e�B�t�@�C����,�����L�[������null<br>
     * ���Ғl :null<br>
     *
     * �����F�����L�[������Null�̎��Anull���ԋp����邱�Ƃ��m�F����B
     * 
     * @throws Exception ��O */
    public void testGetPropertiesValuesString03() throws Exception {
        // ���͒l�̐ݒ�
        //�v���p�e�B�t�@�C����
        String input = "test";
        //�����L�[������
        String key = null;
        // �e�X�g�Ώۂ̎��s
        Set result = PropertyUtil.getPropertiesValues(input, key);

        // ���ʊm�F
        assertNull(result);
    }

    /**
     * testGetPropertiesValues04(String, String)�B<br>
     * 
     * �i����n�j<br>
     * �ϓ_�FC<br>
     * <br>
     * ���͒l :�v���p�e�B�t�@�C����null,�����L�[������<br>
     * ���Ғl :null<br>
     *
     * �����F�v���p�e�B�t�@�C������Null�̎��A
     * Null��߂�l�Ƃ��ď������I�����邱�Ƃ��m�F����B
     *
     * @throws Exception ��O */
    public void testGetPropertiesValuesString04() throws Exception {
        // ���͒l�̐ݒ�
        //�v���p�e�B�t�@�C����
        String input = null;
        //�����L�[������
        String key = "file";

        // �e�X�g�Ώۂ̎��s
        Set result = PropertyUtil.getPropertiesValues(input, key);

        //���ʊm�F
        assertNull(result);
    }

    /**
     * testGetPropertiesValues05(String, String)�B<br>
     * 
     * �i����n�j<br>
     * �ϓ_�FA,F<br>
     * <br>
     * ���͒l :�v���p�e�B�t�@�C����,�Y������L�[���Ȃ������L�[������<br>
     * ���Ғl :�l�Z�b�g�i���g����j<br>
     *
     * �����F�����L�[������ɊY������l���Ȃ��ꍇ�A
     * ���"Enumeration"���ԋp����邱�Ƃ��m�F����B
     *
     * @throws Exception ��O */
    public void testGetPropertiesValuesString05() throws Exception {
        // ���͒l�̐ݒ�
        //�v���p�e�B�t�@�C����
        String input = "test_message_01";
        //�����L�[������
        String key = "file";
        // �e�X�g�Ώۂ̎��s
        Set result = PropertyUtil.getPropertiesValues(input, key);

        // ���ʊm�F
        assertTrue(result.isEmpty());
    }

    /**
     * testGetPropertiesValues06(String, String)�B<br>
     * 
     * �i����n�j<br>
     * �ϓ_�FC<br>
     * <br>
     * ���͒l :�v���p�e�B�t�@�C�����󕶎�,�����L�[������<br>
     * ���Ғl :null<br>
     *
     * �����F�v���p�e�B�t�@�C�������󕶎��̎��A
     * Null��߂�l�Ƃ��ď������I�����邱�Ƃ��m�F����B
     *
     * @throws Exception ��O */
    public void testGetPropertiesValuesString06() throws Exception {
        // ���͒l�̐ݒ�
        //�v���p�e�B�t�@�C����
        String input = "";
        //�����L�[������
        String key = "file";

        // �e�X�g�Ώۂ̎��s
        Set result = PropertyUtil.getPropertiesValues(input, key);

        //���ʊm�F
        assertNull(result);
    }

    /**
     * testGetPropertiesValues07(String, String)�B<br>
     * 
     * �i����n�j<br>
     * �ϓ_�FA,F<br>
     * <br>
     * ���͒l :�v���p�e�B�t�@�C����,�����L�[������͋󕶎�<br>
     * ���Ғl :�l�Z�b�g�i�S�đI�������j<br>
     *
     * �����F�����L�[�����񂪋󕶎��̏ꍇ�A
     * �I�����ꂽ�v���p�e�B�t�@�C���̑S�Ēl���ԋp����邱�Ƃ��m�F����B
     *
     * @throws Exception ��O */
    public void testGetPropertiesValuesString07() throws Exception {
        // ���͒l�̐ݒ�
        //�v���p�e�B�t�@�C����
        String input = "test_message_01";
        //�����L�[������
        String key = "";
        // �e�X�g�Ώۂ̎��s
        Set result = PropertyUtil.getPropertiesValues(input, key);

        // ���ʊm�F
        assertTrue(result.contains("{0}�f�t�H���g���b�Z�[�W"));
        assertTrue(result.contains("��O���b�Z�[�W"));
        assertTrue(result.contains(""));
    }

    /**
     * testGetPropertiesValues08(String, String)�B<br>
     * 
     * �i����n�j<br>
     * �ϓ_�FA,F<br>
     * <br>
     * ���͒l :�v���p�e�B�t�@�C����,�����L�[������(�������݂���L�[���܂�)<br>
     * ���Ғl :�l�Z�b�g�i���g�������j<br>
     *
     * �����F�����L�[������ɕ������݂���L�[���܂߂����A
     * �l�Z�b�g��1�擾����邱�Ƃ��m�F����B
     *
     * @throws Exception ��O */
    public void testGetPropertiesValuesString08() throws Exception {
        // ���͒l�̐ݒ�
        //�v���p�e�B�t�@�C����
        String input = "test";
        //�����L�[������
        String key = "property.test004";
        // �e�X�g�Ώۂ̎��s
        Set result = PropertyUtil.getPropertiesValues(input, key);

        // ���ʊm�F
        assertTrue(result.contains("testB"));
        assertFalse(result.contains("testA"));
    }

    /**
     * testGetPropertyNames01(Properties, String)�B<br>
     * 
     * �i����n�j<br>
     * �ϓ_�FA,F<br>
     * <br>
     * ���͒l :�v���p�e�B�i1�j�A�����L�[�v���t�B�b�N�X<br>
     * ���Ғl :�Ή�����L�[�ꗗ�i1�j<br>
     *
     * �����F�v���p�e�B�I�u�W�F�N�g�̒��g���P�̎��A
     * �Ή�����L�[��1�擾����Ă��邱�Ƃ��m�F����B
     *
     * @throws Exception ��O */
    @SuppressWarnings("unchecked")
    public void testGetPropertyNamesPropertiesString01() throws Exception {
        // ���͒l�̐ݒ�
        String key1 = "SystemExceptionHandlerTest.key";
        String value1 = "{0}message";

        Properties input = new Properties();
        input.setProperty(key1, value1);

        String keyprefix = "System";

        // �e�X�g�Ώۂ̎��s
        Enumeration result = PropertyUtil.getPropertyNames(input, keyprefix);

        // ���ʊm�F
        Set set = new HashSet();
        set.add("SystemExceptionHandlerTest.key");
        assertTrue(set.contains(result.nextElement()));
        assertFalse(result.hasMoreElements());
    }

    /**
     * testGetPropertyNames02(Properties, String)�B<br>
     * 
     * �i����n�j<br>
     * �ϓ_�FA,F<br>
     * <br>
     * ���͒l :�v���p�e�B�i�����j�A�����L�[�v���t�B�b�N�X<br>
     * ���Ғl :�Ή�����L�[�ꗗ�i1�j<br>
     *
     * �����F�v���p�e�B�I�u�W�F�N�g�̒��g�������̎��A
     * �Ή�����L�[��1�擾����Ă��邱�Ƃ��m�F����B
     *
     * @throws Exception ��O */
    @SuppressWarnings("unchecked")
    public void testGetPropertyNamesPropertiesString02() throws Exception {
        // ���͒l�̐ݒ�
        String key1 = "SystemExceptionHandlerTest.key";
        String value1 = "{0}message";

        String key2 = "property.test004.id.0";
        String value2 = "testA";

        Properties input = new Properties();
        input.setProperty(key1, value1);
        input.setProperty(key2, value2);

        String keyprefix = "System";

        // �e�X�g�Ώۂ̎��s
        Enumeration result = PropertyUtil.getPropertyNames(input, keyprefix);

        // ���ʊm�F
        Set set = new HashSet();
        set.add("SystemExceptionHandlerTest.key");
        assertTrue(set.contains(result.nextElement()));
        assertFalse(result.hasMoreElements());
    }

    /**
     * testGetPropertyNames03(Properties, String)�B<br>
     * 
     * �i����n�j<br>
     * �ϓ_�FA,F<br>
     * <br>
     * ���͒l :�v���p�e�B�i�����j�A�����L�[�v���t�B�b�N�X<br>
     * ���Ғl :�Ή�����L�[�ꗗ�i�����j<br>
     *
     * �����F�v���p�e�B�I�u�W�F�N�g�̒��g�������̎��A
     * �Ή�����L�[�������擾����Ă��邱�Ƃ��m�F����B
     *
     * @throws Exception ��O */
    @SuppressWarnings("unchecked")
    public void testGetPropertyNamesPropertiesString03() throws Exception {
        // ���͒l�̐ݒ�
        String key1 = "SystemExceptionHandlerTest.key";
        String value1 = "{0}message";

        String key2 = "property.test002.id.2";
        String value2 = "test2";

        String key3 = "property.test004.id.0";
        String value3 = "testB";

        Properties input = new Properties();
        input.setProperty(key1, value1);
        input.setProperty(key2, value2);
        input.setProperty(key3, value3);

        String keyprefix = "property";

        // �e�X�g�Ώۂ̎��s
        Enumeration result = PropertyUtil.getPropertyNames(input, keyprefix);

        // ���ʊm�F
        //�������ꂽEnumuration�ɓ����Ă邱�Ƃ̊m�F
        Set set = new HashSet();
        set.add("property.test002.id.2");
        set.add("property.test004.id.0");
        assertTrue(set.contains(result.nextElement()));
        assertTrue(set.contains(result.nextElement()));
        assertFalse(result.hasMoreElements());
    }

    /**
     * testGetPropertyNames04(Properties, String)�B<br>
     * 
     * �i����n�j<br>
     * �ϓ_�FA,F<br>
     * <br>
     * ���͒l :�v���p�e�B�i�����j�A�����L�[�v���t�B�b�N�X<br>
     * ���Ғl :���Enumeration<br>
     *
     * �����F�v���p�e�B�I�u�W�F�N�g�ɑΉ����镔���L�[�v���t�B�b�N�X���Ȃ��ꍇ�A
     * ���Enumeration���ԋp����Ă��邱�Ƃ��m�F����B
     *
     * @throws Exception ��O */
    public void testGetPropertyNamesPropertiesString04() throws Exception {
        // ���͒l�̐ݒ�
        String key1 = "SystemExceptionHandlerTest.key";
        String value1 = "{0}message";

        String key2 = "property.test002.id.2";
        String value2 = "test2";

        String key3 = "property.test004.id.0";
        String value3 = "testB";

        Properties input = new Properties();
        input.setProperty(key1, value1);
        input.setProperty(key2, value2);
        input.setProperty(key3, value3);

        String keyprefix = "a";

        // �e�X�g�Ώۂ̎��s
        Enumeration result = PropertyUtil.getPropertyNames(input, keyprefix);

        // ���ʊm�F
        assertFalse(result.hasMoreElements());
    }

    /**
     * testGetPropertyNames05(Properties, String)�B<br>
     * 
     * �i����n�j<br>
     * �ϓ_�FC<br>
     * <br>
     * ���͒l :�v���p�e�B�iNull�j�A�����L�[�v���t�B�b�N�X<br>
     * ���Ғl :null<br>
     *
     * �����F�v���p�e�B�I�u�W�F�N�g��null�̏ꍇ�A
     * null��߂�l�Ƃ��ď������I�����邱�Ƃ��m�F����B
     *
     * @throws Exception ��O */
    public void testGetPropertyNamesPropertiesString05() throws Exception {
        // ���͒l�̐ݒ�
        Properties input = null;

        String keyprefix = "properties";

        // �e�X�g�Ώۂ̎��s
        Enumeration result = PropertyUtil.getPropertyNames(input, keyprefix);

        // ���ʊm�F
        assertNull(result);
    }

    /**
     * testGetPropertyNames06(Properties, String)�B<br>
     * 
     * �i����n�j<br>
     * �ϓ_�FC,F<br>
     * <br>
     * ���͒l :�v���p�e�B�A�����L�[�v���t�B�b�N�X(null)<br>
     * ���Ғl :null<br>
     *
     * �����F�����L�[�v���t�B�b�N�X��null�̏ꍇ�A
     * null��߂�l�Ƃ��ď������I�����邱�Ƃ��m�F����B
     *
     * @throws Exception ��O */
    public void testGetPropertyNamesPropertiesString06() throws Exception {
        // ���͒l�̐ݒ�
        Properties input = new Properties();
        String key1 = "SystemExceptionHandlerTest.key";
        String value1 = "{0}message";
        input.setProperty(key1, value1);

        String keyprefix = null;

        // �e�X�g�Ώۂ̎��s
        Enumeration result = PropertyUtil.getPropertyNames(input, keyprefix);

        // ���ʊm�F
        assertNull(result);
    }

    /**
     * testGetPropertyNames07(Properties, String)�B<br>
     * 
     * �i����n�j<br>
     * �ϓ_�FC,F<br>
     * <br>
     * ���͒l :�v���p�e�B�i��j�A�����L�[�v���t�B�b�N�X<br>
     * ���Ғl :���Enumeration<br>
     *
     * �����F�v���p�e�B�I�u�W�F�N�g����̏ꍇ�A
     * ���Enumeration���ԋp����Ă��邱�Ƃ��m�F����B
     *
     * @throws Exception ��O */
    public void testGetPropertyNamesPropertiesString07() throws Exception {
        // ���͒l�̐ݒ�
        Properties input = new Properties();

        String keyprefix = "properties";

        // �e�X�g�Ώۂ̎��s
        Enumeration result = PropertyUtil.getPropertyNames(input, keyprefix);

        // ���ʊm�F
        assertFalse(result.hasMoreElements());
    }

    /**
     * testGetPropertyNames08(Properties, String)�B<br>
     * 
     * �i����n�j<br>
     * �ϓ_�FC,F<br>
     * <br>
     * ���͒l :�v���p�e�B�A�����L�[�v���t�B�b�N�X(�󕶎�)<br>
     * ���Ғl :�Ή�����S�ẴL�[�ꗗ<br>
     *
     * �����F�����L�[�v���t�B�b�N�X���󕶎��̏ꍇ�A
     * �Ή�����S�ẴL�[�ꗗ���ԋp����邱�Ƃ��m�F����B
     *
     * @throws Exception ��O */
    @SuppressWarnings("unchecked")
    public void testGetPropertyNamesPropertiesString08() throws Exception {
        // ���͒l�̐ݒ�
        Properties input = new Properties();

        String key1 = "property.test002.id.2";
        String value1 = "test2";

        String key2 = "property.test004.id.0";
        String value2 = "testB";

        String key3 = "property.test001.id.0";
        String value3 = "test";

        input.setProperty(key1, value1);
        input.setProperty(key2, value2);
        input.setProperty(key3, value3);

        String keyprefix = "";

        // �e�X�g�Ώۂ̎��s
        Enumeration result = PropertyUtil.getPropertyNames(input, keyprefix);

        // ���ʊm�F
        Set set = new HashSet();
        set.add("property.test002.id.2");
        set.add("property.test004.id.0");
        set.add("property.test001.id.0");
        assertTrue(set.contains(result.nextElement()));
        assertTrue(set.contains(result.nextElement()));
        assertTrue(set.contains(result.nextElement()));
        assertFalse(result.hasMoreElements());
    }

    /**
     * testGetPropertyNames09(Properties, String)�B<br>
     * 
     * �i����n�j<br>
     * �ϓ_�FA,F<br>
     * <br>
     * ���͒l :�L�[���������݂���v���p�e�B�I�u�W�F�N�g<br>
     * ���Ғl :�Ή�����S�ẴL�[�ꗗ<br>
     *
     * �����F�L�[���������݂���v���p�e�B�I�u�W�F�N�g�̏ꍇ
     * �����̂���1�ԋp����邱�Ƃ��m�F����B
     *
     * @throws Exception ��O */
    @SuppressWarnings("unchecked")
    public void testGetPropertyNamesPropertiesString09() throws Exception {
        // ���͒l�̐ݒ�
        String key1 = "SystemExceptionHandlerTest.key";
        String value1 = "{0}message";

        String key2 = "property.test002.id.2";
        String value2 = "test2";

        String key3 = "property.test004.id.0";
        String value3 = "testA";

        String key4 = "property.test004.id.0";
        String value4 = "testB";

        Properties input = new Properties();
        input.setProperty(key1, value1);
        input.setProperty(key2, value2);
        input.setProperty(key3, value3);
        input.setProperty(key4, value4);

        String keyprefix = "pro";

        // �e�X�g�Ώۂ̎��s
        Enumeration result = PropertyUtil.getPropertyNames(input, keyprefix);

        // ���ʊm�F
        //�������ꂽEnumuration�ɓ����Ă邱�Ƃ̊m�F
        Set set = new HashSet();
        set.add("property.test002.id.2");
        set.add("property.test004.id.0");
        assertTrue(set.contains(result.nextElement()));
        assertTrue(set.contains(result.nextElement()));
        assertFalse(result.hasMoreElements());
    }
    /**
     * testGetPropertiesValues01(Properties, Enumeration)�B<br>
     * 
     * �i����n�j<br>
     * �ϓ_�FA,F<br>
     * <br>
     * ���͒l :�v���p�e�B�i1�j�A�L�[�̈ꗗ�i1�j<br>
     * ���Ғl :�l�Z�b�g�i1�j<br>
     *
     * �����F�v���p�e�B�I�u�W�F�N�g�̒��g���P�ŁA�L�[�ꗗ�̒��g���P�̎��A
     * �w�肳�ꂽ�v���p�e�B����l1�擾����Ă��邱�Ƃ��m�F����B
     *
     * @throws Exception ��O */
    @SuppressWarnings("unchecked")
    public void testGetPropertiesValues01() throws Exception {
        // ���͒l�̐ݒ�
        String key1 = "SystemExceptionHandlerTest.key";
        String value1 = "{0}message";

        Properties input = new Properties();
        input.setProperty(key1, value1);

        Enumeration em = new StringTokenizer("SystemExceptionHandlerTest.key");
        // �e�X�g�Ώۂ̎��s
        Set result = PropertyUtil.getPropertiesValues(input, em);

        // ���ʊm�F
        assertTrue(result.contains("{0}message"));
    }

    /**
     * testGetPropertiesValues02(Properties, Enumeration)�B<br>
     * 
     * �i����n�j<br>
     * �ϓ_�FA,F<br>
     * <br>
     * ���͒l :�v���p�e�B�i�����j�A�L�[�̈ꗗ�i�����j<br>
     * ���Ғl :�l�Z�b�g�i�����j<br>
     *
     * �����F�v���p�e�B�I�u�W�F�N�g�̒��g�������̎��A
     * �w�肳�ꂽ�v���p�e�B����l�������擾����Ă��邱�Ƃ��m�F����B
     *
     * @throws Exception ��O */
    @SuppressWarnings("unchecked")
    public void testGetPropertiesValues02() throws Exception {
        // ���͒l�̐ݒ�
        String key1 = "property.test001.id.0";
        String value1 = "test";

        String key2 = "property.test002.id.0";
        String value2 = "test0";

        String key3 = "property.test002.id.1";
        String value3 = "test1";

        Properties input = new Properties();
        input.setProperty(key1, value1);
        input.setProperty(key2, value2);
        input.setProperty(key3, value3);

        Enumeration em =
            new StringTokenizer("property.test001.id.0 property.test002.id.0 property.test002.id.1");

        // �e�X�g�Ώۂ̎��s
        Set result = PropertyUtil.getPropertiesValues(input, em);

        // ���ʊm�F
        assertTrue(result.contains("test0"));
        assertTrue(result.contains("test1"));
        assertTrue(result.contains("test"));
    }

    /**
     * testGetPropertiesValues03(Properties, Enumeration)�B<br>
     * 
     * �i����n�j<br>
     * �ϓ_�FA,F<br>
     * <br>
     * ���͒l :�v���p�e�B�i�l�ɋ󂪂�����̂��܂ށj�A�L�[�̈ꗗ�i�����j<br>
     * ���Ғl :�l�Z�b�g�i�l����̂��̂ɂ��ẮA��ƕ\�������j<br>
     *
     * �����F�v���p�e�B�I�u�W�F�N�g�̒��ɁA�L�[�ɑ΂���l����Ȃ��̂��܂܂��ꍇ
     * " "�Ŏ擾����Ă��邱�Ƃ��m�F����B
     *
     * @throws Exception ��O */
    @SuppressWarnings("unchecked")
    public void testGetPropertiesValues03() throws Exception {
        // ���͒l�̐ݒ�
        String key1 = "property.test001.id.0";
        String value1 = "test";

        String key2 = "property.test002.id.0";
        String value2 = "test0";

        String key3 = "property.test003.id.0";
        String value3 = "";

        Properties input = new Properties();
        input.setProperty(key1, value1);
        input.setProperty(key2, value2);
        input.setProperty(key3, value3);

        Enumeration em =
            new StringTokenizer("property.test001.id.0 property.test002.id.0 property.test003.id.0");

        // �e�X�g�Ώۂ̎��s
        Set result = PropertyUtil.getPropertiesValues(input, em);

        // ���ʊm�F
        assertTrue(result.contains("test0"));
        assertTrue(result.contains("test"));
        assertTrue(result.contains(""));
    }

    /**
     * testGetPropertiesValues04(Properties, Enumeration)�B<br>
     * 
     * �i����n�j<br>
     * �ϓ_�FC,F<br>
     * <br>
     * ���͒l :�v���p�e�B��null�A�L�[�̈ꗗ<br>
     * ���Ғl :null<br>
     *
     * �����F�v���p�e�B��null�̎��Anull��߂�l�Ƃ��ď������I�����邱�Ƃ��m�F����B
     *
     * @throws Exception ��O */
    @SuppressWarnings("unchecked")
    public void testGetPropertiesValues04() throws Exception {
        // ���͒l�̐ݒ�
        Properties input = null;

        Enumeration em = new StringTokenizer("property.test001.id.0");
        // �e�X�g�Ώۂ̎��s
        Set result = PropertyUtil.getPropertiesValues(input, em);

        // ���ʊm�F
        assertNull(result);
    }

    /**
     * testGetPropertiesValues05(Properties, Enumeration)�B<br>
     * 
     * �i����n�j<br>
     * �ϓ_�FA,F<br>
     * <br>
     * ���͒l :�v���p�e�B�A�L�[�̈ꗗ��null<br>
     * ���Ғl :null<br>
     *
     * �����F�L�[�̈ꗗ��null�̎��Anull��߂�l�Ƃ��ď������I�����邱�Ƃ��m�F����B
     *
     * @throws Exception ��O */
    @SuppressWarnings("unchecked")
    public void testGetPropertiesValues05() throws Exception {
        // ���͒l�̐ݒ�
        Properties input = new Properties();
        String key1 = "property.test001.id.0";
        String value1 = "test";
        input.setProperty(key1, value1);

        Enumeration em = null;
        // �e�X�g�Ώۂ̎��s
        Set result = PropertyUtil.getPropertiesValues(input, em);

        // ���ʊm�F
        assertNull(result);
    }

    /**
     * testGetPropertiesValues06(Properties, Enumeration)�B<br>
     * 
     * �i����n�j<br>
     * �ϓ_�FC,F<br>
     * <br>
     * ���͒l :�v���p�e�B����A�L�[�̈ꗗ<br>
     * ���Ғl :"null"<br>
     *
     * �����F�v���p�e�B����̎��A"null"�Ŏ擾����邱�Ƃ��m�F����B
     *
     * @throws Exception ��O */
    @SuppressWarnings("unchecked")
    public void testGetPropertiesValues06() throws Exception {
        // ���͒l�̐ݒ�
        Properties input = new Properties();

        Enumeration em = new StringTokenizer("property.test001.id.0");
        // �e�X�g�Ώۂ̎��s
        Set result = PropertyUtil.getPropertiesValues(input, em);

        // ���ʊm�F
        assertTrue(result.contains(null));
    }

    /**
     * testGetPropertiesValues07(Properties, Enumeration)�B<br>
     * 
     * �i����n�j<br>
     * �ϓ_�FA,F<br>
     * <br>
     * ���͒l :�v���p�e�B�A�L�[�̈ꗗ����<br>
     * ���Ғl :��<br>
     *
     * �����F�L�[�̈ꗗ����̎��A�󂪎擾����邱�Ƃ��m�F����B
     *
     * @throws Exception ��O */
    @SuppressWarnings("unchecked")
    public void testGetPropertiesValues07() throws Exception {
        // ���͒l�̐ݒ�
        Properties input = new Properties();
        String key1 = "property.test001.id.0";
        String value1 = "test";
        input.setProperty(key1, value1);

        Enumeration em = new StringTokenizer("");

        // �e�X�g�Ώۂ̎��s
        Set result = PropertyUtil.getPropertiesValues(input, em);

        // ���ʊm�F
        assertTrue(result.isEmpty());
    }

    /**
     * testGetPropertiesValues08(Properties, Enumeration)�B<br>
     * 
     * �i����n�j<br>
     * �ϓ_�FA,F<br>
     * <br>
     * ���͒l :�L�[���������݂���v���p�e�B�I�u�W�F�N�g<br>
     * ���Ғl :�l�Z�b�g<br>
     *
     * �����F�L�[���������݂���v���p�e�B�I�u�W�F�N�g�̏ꍇ
     * �����̂���1�̒l���擾����Ă��邱�Ƃ��m�F����B
     * 
     * @throws Exception ��O */
    @SuppressWarnings("unchecked")
    public void testGetPropertiesValues08() throws Exception {
        // ���͒l�̐ݒ�
        Properties input = new Properties();
        String key1 = "property.test004.id.0";
        String value1 = "testA";

        String key2 = "property.test004.id.0";
        String value2 = "testB";

        input.setProperty(key1, value1);
        input.setProperty(key2, value2);

        Enumeration em = new StringTokenizer("property.test004.id.0");

        // �e�X�g�Ώۂ̎��s
        Set result = PropertyUtil.getPropertiesValues(input, em);

        // ���ʊm�F
        assertTrue(result.contains("testB"));
        assertFalse(result.contains("testA"));
    }

    /**
     * testGetPropertiesValues09(Properties, Enumeration)�B<br>
     * 
     * �i����n�j<br>
     * �ϓ_�FA,F<br>
     * <br>
     * ���͒l :�L�[�ꗗ�ɑ��݂��Ȃ��v���p�e�B�L�[<br>
     * ���Ғl :"null"<br>
     *
     * �����F�v���p�e�B�̃L�[���L�[�ꗗ�ɑ��݂��Ȃ��ꍇ�A"null"�Ŏ擾����邱�Ƃ��m�F����B
     * 
     * @throws Exception ��O */
    @SuppressWarnings("unchecked")
    public void testGetPropertiesValues09() throws Exception {
        // ���͒l�̐ݒ�
        Properties input = new Properties();
        String key1 = "property.test001.id.0";
        String value1 = "test";

        input.setProperty(key1, value1);

        Enumeration em = new StringTokenizer("property.test004.id.0");

        // �e�X�g�Ώۂ̎��s
        Set result = PropertyUtil.getPropertiesValues(input, em);

        // ���ʊm�F
        assertTrue(result.contains(null));
        assertFalse(result.contains("test"));
    }

    /**
     * testLoadProperties01(String)�B<br>
     * 
     * �i����n�j<br>
     * �ϓ_�FA,F<br>
     * <br>
     * ���͒l :�v���p�e�B�t�@�C����<br>
     * ���Ғl :�v���p�e�B�I�u�W�F�N�g�i���g���P�j<br>
     *
     * �����F�v���p�e�B�I�u�W�F�N�g�̒��g���P�̎��A
     * �w�肳�ꂽ�v���p�e�B�t�@�C�������[�h����Ă��邱�Ƃ��m�F����B
     *
     * @throws Exception ��O */
    @SuppressWarnings("unchecked")
    public void testLoadProperties01() throws Exception {
        // ���͒l�̐ݒ�
        String input = "test_message_01_en_US";

        // �e�X�g�Ώۂ̎��s
        Properties result = PropertyUtil.loadProperties(input);

        // ���ʊm�F
        Set set = new HashSet();
        set.add("{SystemExceptionHandlerTest.key={0}message}");

        assertTrue(result.containsKey("SystemExceptionHandlerTest.key"));
        assertTrue(result.containsValue("{0}message"));
    }

    /**
     * testLoadProperties02(String)�B<br>
     * 
     * �i����n�j<br>
     * �ϓ_�FA,F<br>
     * <br>
     * ���͒l :�v���p�e�B�t�@�C����<br>
     * ���Ғl :�v���p�e�B�I�u�W�F�N�g�i���g�������j<br>
     *
     * �����F�w�肳�ꂽ�v���p�e�B�t�@�C���̒��g�̌������[�h����Ă��邱�Ƃ��m�F����B
     *
     * @throws Exception ��O */
    public void testLoadProperties02() throws Exception {
        // ���͒l�̐ݒ�
        String input = "test_message_01";

        // �e�X�g�Ώۂ̎��s
        Properties result = PropertyUtil.loadProperties(input);

        // ���ʊm�F
        assertTrue(
            result.containsKey("SystemExceptionHandlerTest.error.message"));
        assertTrue(
            result.containsValue("\u4f8b\u5916\u30e1\u30c3\u30bb\u30fc\u30b8"));
        assertTrue(
            result.containsKey(
                "SystemExceptionHandlerTest.error.message.null"));
        assertTrue(result.containsValue(""));
        assertTrue(result.containsKey("SystemExceptionHandlerTest.key"));
        assertTrue(
            result.containsValue(
                "{0}\u30c7\u30d5\u30a9\u30eb\u30c8\u30e1\u30c3\u30bb\u30fc\u30b8"));
    }

    /**
     * testLoadProperties03(String)�B<br>
     * 
     * �i����n�j<br>
     * �ϓ_�FC,F<br>
     * <br>
     * ���͒l :null<br>
     * ���Ғl :null<br>
     *
     * �����F�v���p�e�B�t�@�C���������݂��Ȃ��ꍇNull��߂�l�Ƃ��ď������I���邱�Ƃ��m�F����B
     * @throws Exception ��O */
    public void testLoadProperties03() throws Exception {
        // ���͒l�̐ݒ�
        String input = null;

        // �e�X�g�Ώۂ̎��s
        Properties result = PropertyUtil.loadProperties(input);

        //���ʊm�F
        assertNull(result);
    }

    /**
     * testLoadProperties04(String)�B<br>
     * 
     * �i����n�j<br>
     * �ϓ_�FC,F<br>
     * <br>
     * ���͒l :""(�󕶎�)<br>
     * ���Ғl :null<br>
     *
     * �����F�v���p�e�B�t�@�C���������݂��Ȃ��ꍇNull��߂�l�Ƃ��ď������I�����邱�Ƃ��m�F����B
     *
     * @throws Exception ��O */
    public void testLoadProperties04() throws Exception {
        // ���͒l�̐ݒ�
        String input = "";

        // �e�X�g�Ώۂ̎��s
        Properties result = PropertyUtil.loadProperties(input);

        //���ʊm�F
        assertNull(result);
    }

    /**
     * testLoadProperties05(String)�B<br>
     * 
     * �i����n�j<br>
     * �ϓ_�FA,F<br>
     * <br>
     * ���͒l :���g�ɉ��������Ă��Ȃ��v���p�e�B�t�@�C����<br>
     * ���Ғl :��̃v���p�e�B�I�u�W�F�N�g<br>
     *
     * �����F�w�肳�ꂽ�v���p�e�B�t�@�C���̒��g����̎��A
     * ��̃v���p�e�B�I�u�W�F�N�g�����o����Ă��邱�Ƃ��m�F����B
     *
     * @throws Exception ��O */
    public void testLoadProperties05() throws Exception {
        // ���͒l�̐ݒ�
        String input = "test_message_10";

        // �e�X�g�Ώۂ̎��s
        Properties result = PropertyUtil.loadProperties(input);

        // ���ʊm�F
        assertTrue(result.isEmpty());
    }

    /**
     * testLoadProperties06(String)�B<br>
     * 
     * �i����n�j<br>
     * �ϓ_�FF<br>
     * <br>
     * ���͒l :���݂��Ȃ��v���p�e�B�t�@�C����<br>
     * ���Ғl :null<br>
     * ���O�F"*** Can not find property-file [test_me.properties] ***"
     *
     * �����F���݂��Ȃ��t�@�C�������w�肳�ꂽ���A
     * null��߂�l�Ƃ��ď������I�����邱�Ƃ��m�F����B
     *
     * @throws Exception ��O */
    public void testLoadProperties06() throws Exception {
        // ���͒l�̐ݒ�
        String input = "test_me";

        // �e�X�g�Ώۂ̎��s
        Properties result = PropertyUtil.loadProperties(input);

        // ���ʊm�F
        LogUTUtil.checkWarn("*** Can not find property-file" +
                " [test_me.properties] ***");
        assertNull(result);
    }

    /**
     * testGetPropertiesPathStringString01()�B<br>
     * 
     * �i����n�j<br>
     * �ϓ_�FA<br>
     * <br>
     * ���͒l :�f�B���N�g���t���t�@�C����=
     * subDir/PropertyUtil.class<br>
     * �����Ώۂ̃t�@�C����=hoge.txt
     * ���Ғl :subDir"�t�@�C���Z�p���[�^(OS�ɂ��قȂ�)"hoge.txt<br>
     *
     * �������̃t���p�X�t�@�C��������A�f�B���N�g���{�������t�@�C����
     * ���o�͂���邱�Ƃ��m�F����B
     * 
     * @throws Exception
     * @throws Exception ��O */
    public void testGetPropertiesPathStringString01() throws Exception {
        // �e�X�g�ݒ�
        // getPropertiesPath�̈����N���X�v�f
        Class[] clz = new Class[]{String.class, String.class};
        // getPropertiesPath�̈����I�u�W�F�N�g�v�f
        Object[] obj = new Object[]{"subDir/PropertyUtil.class", "hoge.txt"};

        // �e�X�g���s
        Object retObj = UTUtil.invokePrivate(PropertyUtil.class,
            "getPropertiesPath", clz, obj);
        
        // �e�X�g����
        assertEquals("subDir" + System.getProperty("file.separator")
            + "hoge.txt", retObj);
    }

    /**
     * testGetPropertiesPathStringString02()�B<br>
     * 
     * �i����n�j<br>
     * �ϓ_�FA<br>
     * <br>
     * ���͒l :
     * (����)resource�Fnull<br>
     * (����)addFile�F"/hoge.txt"<br>
     * ���Ғl :
     * (�߂�l)String�F-<br>
     * (��O)�FNullPointerException
     *
     * ����resource��null�̏ꍇ
     * 
     * @throws Exception
     * @throws Exception ��O */
    public void testGetPropertiesPathStringString02() throws Exception {
        // �e�X�g�ݒ�
        // getPropertiesPath�̈����N���X�v�f
        Class[] clz = new Class[]{String.class, String.class};
        // getPropertiesPath�̈����I�u�W�F�N�g�v�f
        Object[] obj = new Object[]{null, "hoge.txt"};

        // �e�X�g���s
        try {
            UTUtil.invokePrivate(PropertyUtil.class,
                    "getPropertiesPath", clz, obj);
            fail();
        } catch (NullPointerException e) {
            // �e�X�g����
        	return;
        }
    }

    /**
     * testGetPropertiesPathStringString03()�B<br>
     * 
     * �i����n�j<br>
     * �ϓ_�FA<br>
     * <br>
     * ���͒l :
     * (����)resource�F"subDir/PropertyUtil.class"<br>
     * (����)addFile�Fnull<br>
     * ���Ғl :
     * (�߂�l)String�F"subDir/null"<br>
     * (��O)�F-
     *
     * ����addFile��null�̏ꍇ
     * 
     * @throws Exception
     * @throws Exception ��O */
    public void testGetPropertiesPathStringString03() throws Exception {
        // �e�X�g�ݒ�
        // getPropertiesPath�̈����N���X�v�f
        Class[] clz = new Class[]{String.class, String.class};
        // getPropertiesPath�̈����I�u�W�F�N�g�v�f
        Object[] obj = new Object[]{"subDir/PropertyUtil.class", null};

        // �e�X�g���s
        Object retObj = UTUtil.invokePrivate(PropertyUtil.class,
            "getPropertiesPath", clz, obj);
        
        // �e�X�g����
        assertEquals("subDir" + System.getProperty("file.separator")
            + "null", retObj);
    }

    /**
     * testGetPropertiesPathStringString04()�B<br>
     * 
     * �i����n�j<br>
     * �ϓ_�FA<br>
     * <br>
     * ���͒l :
     * (����)resource�F""<br>
     * (����)addFile�F""<br>
     * ���Ғl :
     * (�߂�l)String�F""<br>
     * (��O)�F-
     *
     * �������󔒂̏ꍇ
     * 
     * @throws Exception
     * @throws Exception ��O */
    public void testGetPropertiesPathStringString04() throws Exception {
        // �e�X�g�ݒ�
        // getPropertiesPath�̈����N���X�v�f
        Class[] clz = new Class[]{String.class, String.class};
        // getPropertiesPath�̈����I�u�W�F�N�g�v�f
        Object[] obj = new Object[]{"", ""};

        // �e�X�g���s
        Object retObj = UTUtil.invokePrivate(PropertyUtil.class,
            "getPropertiesPath", clz, obj);
        
        // �e�X�g����
        assertEquals("", retObj);
    }

}
