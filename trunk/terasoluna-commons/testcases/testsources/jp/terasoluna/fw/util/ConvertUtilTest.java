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
import java.math.BigInteger;
import java.security.AlgorithmParameterGenerator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.JWindow;

import jp.terasoluna.utlib.UTUtil;
import junit.framework.TestCase;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.beanutils.PropertyUtilsBean;

/**
 * {@link jp.terasoluna.fw.client.util.ConvertUtil} �N���X�̃e�X�g�B
 * 
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4>
 * �^�ϊ����s�����߂̃��[�e�B���e�B�N���X�B
 * <p>
 * 
 * @see jp.terasoluna.fw.client.util.ConvertUtil
 */
public class ConvertUtilTest extends TestCase {
    
    /**
     * PropertyUtilsBean�B
     */
    private PropertyUtilsBean defaultPropertyUtilsBean = null;

    /**
     * ���̃e�X�g�P�[�X�����s����ׂ�
     * GUI �A�v���P�[�V�������N������B
     * 
     * @param args java �R�}���h�ɐݒ肳�ꂽ�p�����[�^
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(ConvertUtilTest.class);
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
        BeanUtilsBean beanUtilsBean = BeanUtilsBean.getInstance();
        defaultPropertyUtilsBean = beanUtilsBean.getPropertyUtils();
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
        BeanUtilsBean beanUtilsBean = BeanUtilsBean.getInstance();
        UTUtil.setPrivateField(beanUtilsBean, "propertyUtilsBean",
                defaultPropertyUtilsBean);
    }

    /**
     * �R���X�g���N�^�B
     * 
     * @param name ���̃e�X�g�P�[�X�̖��O�B
     */
    public ConvertUtilTest(String name) {
        super(name);
    }

    /**
     * testToArray01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) obj:null<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) Object[]:�v�f�������Ȃ�Object[] (�v�f����0�ł��邱�Ƃ��m�F)<br>
     *         
     * <br>
     * ����obj��null�������ꍇ�A�v�f�������Ȃ�Object[]���ԋp����邱�Ƃ��m�F����e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testToArray01() throws Exception {
        // �e�X�g���{
        Object[] result = ConvertUtil.toArray(null); 
        
        // ����
        assertEquals(0, result.length);
    }

    /**
     * testToArray02()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) obj:""(�󕶎�)<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) Object[]:�P�v�f������Object[] (�v�f�����P�ł��邱�Ƃ��m�F)<br>
     *                   *�v�f0:""(�󕶎�)<br>
     *         
     * <br>
     * ����obj��""(�󕶎�)�������ꍇ�A�P�v�f������Object[]���ԋp����邱�Ƃ��m�F����e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testToArray02() throws Exception {
        // �e�X�g���{
        Object[] result = ConvertUtil.toArray(""); 
        
        // ����
        assertEquals(1, result.length);
        assertEquals("", result[0]);
    }

    /**
     * testToArray03()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) obj:"  "(�󔒕�����)<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) Object[]:�P�v�f������Object[] (�v�f�����P�ł��邱�Ƃ��m�F)<br>
     *                   *�v�f0:"  "(�󔒕�����)<br>
     *         
     * <br>
     * ����obj��"  "(�󔒕�����)�������ꍇ�A�P�v�f������Object[]���ԋp����邱�Ƃ��m�F����e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testToArray03() throws Exception {
        // �e�X�g���{
        Object[] result = ConvertUtil.toArray("  "); 
        
        // ����
        assertEquals(1, result.length);
        assertEquals("  ", result[0]);
    }

    /**
     * testToArray04()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FE
     * <br><br>
     * ���͒l�F(����) obj:"array"<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) Object[]:�P�v�f������Object[] (�v�f�����P�ł��邱�Ƃ��m�F)<br>
     *                   *�v�f0:"array"<br>
     *         
     * <br>
     * ����obj���ʏ�̕����񂾂����ꍇ�A�P�v�f������Object[]���ԋp����邱�Ƃ��m�F����e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testToArray04() throws Exception {
        // �e�X�g���{
        Object[] result = ConvertUtil.toArray("array"); 
        
        // ����
        assertEquals(1, result.length);
        assertEquals("array", result[0]);
    }

    /**
     * testToArray05()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FD
     * <br><br>
     * ���͒l�F(����) obj:�v�f�������Ȃ�Object[]<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) Object[]:�v�f�������Ȃ�Object[] (�v�f�����O�ł��邱�Ƃ��m�F)<br>
     *         
     * <br>
     * ����obj���v�f�������Ȃ��z�񂾂����ꍇ�A�v�f�������Ȃ�Object[]���ԋp����邱�Ƃ��m�F����e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testToArray05() throws Exception {
        // �O����
        Object obj = new Object[0];

        // �e�X�g���{
        Object[] result = ConvertUtil.toArray(obj); 
        
        // ����
        assertEquals(0, result.length);
    }

    /**
     * testToArray06()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FD
     * <br><br>
     * ���͒l�F(����) obj:�P�v�f��ێ�����String[]<br>
     *                 *�v�f�O:"array"<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) Object[]:�P�v�f������Object[] (�v�f�����P�ł��邱�Ƃ��m�F)<br>
     *                   *�v�f0:"array"<br>
     *         
     * <br>
     * ����obj���P�v�f��ێ�����z�񂾂����ꍇ�A�P�v�f��ێ�����Object[]���ԋp����邱�Ƃ��m�F����e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testToArray06() throws Exception {
        // �O����
        String[] obj = {"array"};

        // �e�X�g���{
        Object[] result = ConvertUtil.toArray(obj); 
        
        // ����
        assertEquals(1, result.length);
        assertEquals("array", result[0]);
    }

    /**
     * testToArray07()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FD
     * <br><br>
     * ���͒l�F(����) obj:�R�v�f��ێ�����Object[]<br>
     *                 *�v�f�O:"array"<br>
     *                 *�v�f�P:1<br>
     *                 *�v�f�Q:Map�C���X�^���X (key="foo" value="something")<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) Object[]:�R�v�f������Object[] (�v�f�����R�ł��邱�Ƃ��m�F)<br>
     *                   *�v�f0:"array"<br>
     *                   *�v�f1:1<br>
     *                   *�v�f2:Map�C���X�^���X(key="foo" value="something")<br>
     *         
     * <br>
     * ����obj���R�v�f��ێ�����z�񂾂����ꍇ�A�R�v�f��ێ�����Object[]���ԋp����邱�Ƃ��m�F����e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testToArray07() throws Exception {
        // �O����
        Object[] obj = new Object[3];
        obj[0] = "array";
        obj[1] = 1;
        Map map = new HashMap();
        map.put("foo", "something");
        obj[2] = map;

        // �e�X�g���{
        Object[] result = ConvertUtil.toArray(obj); 
        
        // ����
        assertEquals(3, result.length);
        assertEquals("array", result[0]);
        assertEquals(1, result[1]);
        assertEquals(map, result[2]);
        Map mapResult = (Map) result[2];
        assertEquals("something", mapResult.get("foo"));
    }

    /**
     * testToArray08()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FD
     * <br><br>
     * ���͒l�F(����) obj:�v�f�������Ȃ�Collection<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) Object[]:�v�f�������Ȃ�Object[] (�v�f�����O�ł��邱�Ƃ��m�F)<br>
     *         
     * <br>
     * ����obj���v�f�������Ȃ�Collection�������ꍇ�A�v�f�������Ȃ�Object[]���ԋp����邱�Ƃ��m�F����e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testToArray08() throws Exception {
        // �O����
        Collection obj = new Vector();

        // �e�X�g���{
        Object[] result = ConvertUtil.toArray(obj); 
        
        // ����
        assertEquals(0, result.length);
    }

    /**
     * testToArray09()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FD
     * <br><br>
     * ���͒l�F(����) obj:�P�v�f��ێ�����Collection<br>
     *                 *�v�f0:"collection"<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) Object[]:�P�v�f������Object[] (�v�f�����P�ł��邱�Ƃ��m�F)<br>
     *                   *�v�f0:"collection"<br>
     *         
     * <br>
     * ����obj���P�v�f��ێ�����Collection�������ꍇ�A�P�v�f��ێ�����Object[]���ԋp����邱�Ƃ��m�F����e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testToArray09() throws Exception {
        // �O����
        Collection obj = new Vector();
        obj.add("collection");

        // �e�X�g���{
        Object[] result = ConvertUtil.toArray(obj); 
        
        // ����
        assertEquals(1, result.length);
        assertEquals("collection", result[0]);
    }

    /**
     * testToArray10()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FD
     * <br><br>
     * ���͒l�F(����) obj:�R�v�f��ێ�����Collection<br>
     *                 *�v�f0:"collection"<br>
     *                 *�v�f1:1<br>
     *                 *�v�f2:Map�C���X�^���X(key="key" value="something")<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) Object[]:�R�v�f������Object[] (�v�f�����R�ł��邱�Ƃ��m�F)<br>
     *                   *�v�f0:"collection"<br>
     *                   *�v�f1:1<br>
     *                   *�v�f2:Map�C���X�^���X(key="foo" value="something")<br>
     *         
     * <br>
     * ����obj���R�v�f��ێ�����collection�������ꍇ�A�R�v�f��ێ�����Object[]���ԋp����邱�Ƃ��m�F����e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testToArray10() throws Exception {
        // �O����
        Collection obj = new Vector();
        obj.add("collection");
        obj.add(1);
        Map map = new HashMap();
        map.put("foo", "something");
        obj.add(map);

        // �e�X�g���{
        Object[] result = ConvertUtil.toArray(obj); 
        
        // ����
        assertEquals(3, result.length);
        assertEquals("collection", result[0]);
        assertEquals(1, result[1]);
        Map mapResult = (Map) result[2];
        assertEquals("something", mapResult.get("foo"));
    }

    /**
     * testToList01()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FC,G
     * <br><br>
     * ���͒l�F(����) obj:null<br>
     *         (����) elementClass:null<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalArgumentException<br>
     *                    ��O�̃��b�Z�[�W:
     *                    "Argument 'elementClass' (" + Class.class.getName() 
     *                    �@+ ") is null"<br>
     *         
     * <br>
     * ����elementClass��null�������ꍇ�AIllegalArgumentException���X���[����邱�Ƃ��m�F����e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testToList01() throws Exception {
        // �e�X�g���{
        try {
            ConvertUtil.toList(null, null);
            fail("IllegalArgumentException���X���[����܂���ł����B");
        } catch (IllegalArgumentException e) {
            // ����
            assertEquals(IllegalArgumentException.class.getName(),
                    e.getClass().getName());
            assertEquals("Argument 'elementClass' (" + Class.class.getName()
                    + ") is null",
                    e.getMessage());
        }

    }

    /**
     * testToList02()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) obj:null<br>
     *         (����) elementClass:Object.class<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) List:�v�f�������Ȃ�List<Object> (�v�f�����O�ł��邱�Ƃ��m�F)<br>
     *         
     * <br>
     * ����obj��null�������ꍇ�AelementsClass�Ŏw�肵���^��List���ԋp����邱�Ƃ��m�F����e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testToList02() throws Exception {
        // �e�X�g���{
        List<Object> result = ConvertUtil.toList(null, Object.class);

        // ����
        assertEquals(0, result.size());
    }

    /**
     * testToList03()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) obj:""(�󕶎�)<br>
     *         (����) elementClass:Object.class<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) List:�P�v�f������List<Object> (�v�f�����P�ł��邱�Ƃ��m�F)<br>
     *                   *�v�f0:""(�󕶎�)<br>
     *         
     * <br>
     * ����obj��""(�󕶎�)�������ꍇ�AelementsClass�Ŏw�肵���^��List���ԋp����邱�Ƃ��m�F����e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testToList03() throws Exception {
        // �e�X�g���{
        List<Object> result = ConvertUtil.toList("", Object.class);

        // ����
        assertEquals(1, result.size());
        assertEquals("", result.get(0));
        
    }

    /**
     * testToList04()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) obj:"  "(�󔒕�����)<br>
     *         (����) elementClass:Object.class<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) List:�P�v�f������List<Object> (�v�f�����P�ł��邱�Ƃ��m�F)<br>
     *                   *�v�f0:"  "(�󔒕�����)<br>
     *         
     * <br>
     * ����obj��""(�󕶎�)�������ꍇ�AelementsClass�Ŏw�肵���^��List���ԋp����邱�Ƃ��m�F����e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testToList04() throws Exception {
        // �e�X�g���{
        List<Object> result = ConvertUtil.toList("  ", Object.class);

        // ����
        assertEquals(1, result.size());
        assertEquals("  ", result.get(0));
    }

    /**
     * testToList05()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FE
     * <br><br>
     * ���͒l�F(����) obj:"list"<br>
     *         (����) elementClass:Object.class<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) List:�P�v�f������List<Object> (�v�f�����P�ł��邱�Ƃ��m�F)<br>
     *                   *�v�f0:"list"<br>
     *         
     * <br>
     * ����obj���ʏ핶���񂾂����ꍇ�AelementsClass�Ŏw�肵���^��List���ԋp����邱�Ƃ��m�F����e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testToList05() throws Exception {
        // �e�X�g���{
        List<Object> result = ConvertUtil.toList("list", Object.class);

        // ����
        assertEquals(1, result.size());
        assertEquals("list", result.get(0));
    }

    /**
     * testToList06()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) obj:"list"<br>
     *         (����) elementClass:JWindow.class<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalArgumentException<br>
     *                    ��O�̃��b�Z�[�W:
     *                    "Unable to cast '" + ����obj�̊��S�C���N���X�� 
     *                    �@+ "' to '" + ����elementClass�̊��S�C���N���X�� + "'"<br>
     *                    ���b�v���ꂽ��O:ClassCastException<br>
     *                    ���b�v���ꂽ��O�̃��b�Z�[�W:"Unable to cast '" 
     *                    �@+ ����obj�̊��S�C���N���X�� + "' to '" 
     *                    + ����elementClass�̊��S�C���N���X�� + "'"<br>
     *         
     * <br>
     * ����obj�̌^��elementClass�Ŏw�肵���^�Ɠ���A�������̓T�u�N���X�ł�
     * �Ȃ������ꍇ�AIllegalArgumentException���X���[����邱�Ƃ��m�F����e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testToList06() throws Exception {
        // �e�X�g���{
        try {
            ConvertUtil.toList("list", JWindow.class);
            fail("IllegalArgumentException���X���[����܂���ł����B");
        } catch (IllegalArgumentException e) {
            // ����
            assertEquals(IllegalArgumentException.class.getName(),
                    e.getClass().getName());
            assertEquals("Unable to cast '" + "list".getClass().getName() 
                    + "' to '" + JWindow.class.getName() + "'",
                    e.getMessage());
            assertEquals(ClassCastException.class.getName(),
                    e.getCause().getClass().getName());
            assertEquals("Unable to cast '" + "list".getClass().getName() 
                    + "' to '" + JWindow.class.getName() + "'",
                    e.getCause().getMessage());
        }

    }

    /**
     * testToList07()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FD
     * <br><br>
     * ���͒l�F(����) obj:�v�f�������Ȃ�Object[]<br>
     *         (����) elementClass:String.class<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) List:�v�f�������Ȃ�List<String> (�v�f�����O�ł��邱�Ƃ��m�F)<br>
     *         
     * <br>
     * ����obj���v�f�������Ȃ��z�񂾂����ꍇ�AelementsClass�Ŏw�肵���^��List���ԋp����邱�Ƃ��m�F����e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testToList07() throws Exception {
        // �O����
        Object[] obj = new Object[0];
        
        // �e�X�g���{
        List<String> result = ConvertUtil.toList(obj, String.class);

        // ����
        assertEquals(0, result.size());
    }

    /**
     * testToList08()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FD
     * <br><br>
     * ���͒l�F(����) obj:�P�v�f��ێ�����Object[]<br>
     *                 *�v�f0:"foo"<br>
     *         (����) elementClass:String.class<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) List:�P�v�f������List<String> (�v�f�����P�ł��邱�Ƃ��m�F)<br>
     *                   *�v�f0:"foo"<br>
     *         
     * <br>
     * ����obj���P�v�f��ێ�����z�񂾂����ꍇ�AelementsClass�Ŏw�肵���^��List���ԋp����邱�Ƃ��m�F����e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testToList08() throws Exception {
        // �O����
        Object[] obj = {"foo"};
        
        // �e�X�g���{
        List<String> result = ConvertUtil.toList(obj, String.class);

        // ����
        assertEquals(1, result.size());
        assertEquals("foo", result.get(0));
    }

    /**
     * testToList09()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FD
     * <br><br>
     * ���͒l�F(����) obj:�R�v�f��ێ�����Object[]<br>
     *                 *�v�f0:"foo"<br>
     *                 *�v�f2:"bar"<br>
     *                 *�v�f3:"baz"<br>
     *         (����) elementClass:String.class<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) List:�R�v�f������List<String> (�v�f�����R�ł��邱�Ƃ��m�F)<br>
     *                   *�v�f0:"foo"<br>
     *                   *�v�f1:"bar"<br>
     *                   *�v�f2:"baz"<br>
     *         
     * <br>
     * ����obj���R�v�f��ێ�����z�񂾂����ꍇ�AelementsClass�Ŏw�肵���^��List���ԋp����邱�Ƃ��m�F����e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testToList09() throws Exception {
        // �O����
        Object[] obj = new Object[3];
        obj[0] = "foo";
        obj[1] = "bar";
        obj[2] = "baz";
        
        // �e�X�g���{
        List<String> result = ConvertUtil.toList(obj, String.class);

        // ����
        assertEquals(3, result.size());
        assertEquals("foo", result.get(0));
        assertEquals("bar", result.get(1));
        assertEquals("baz", result.get(2));
    }

    /**
     * testToList10()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) obj:�R�v�f��ێ�����Object[]<br>
     *                 *�v�f0:"foo"<br>
     *                 *�v�f2:JWindow�C���X�^���X<br>
     *                 *�v�f3:"baz"<br>
     *         (����) elementClass:String.class<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalArgumentException<br>
     *                    ��O�̃��b�Z�[�W:
     *                    �@"Unable to cast '" + JWindow�̊��S�C���N���X�� 
     *                    �@+ "' to '" + ����elementClass�̊��S�C���N���X�� + "'"<br>
     *                    ���b�v���ꂽ��O:ClassCastException<br>
     *                    ���b�v���ꂽ��O�̃��b�Z�[�W:
     *                    �@"Unable to cast '" + JWindow�̊��S�C���N���X�� 
     *                    + "' to '" + ����elementClass�̊��S�C���N���X�� + "'"<br>
     *         
     * <br>
     * ����obj�̔z���elementClass�Ŏw�肵���^�Ɠ���A�������̓T�u�N���X�ł͂Ȃ�
     * �v�f���܂܂�Ă����ꍇ�AIllegalArgumentException���X���[����邱�Ƃ�
     * �m�F����e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testToList10() throws Exception {
        // �O����
        Object[] obj = new Object[3];
        obj[0] = "foo";
        obj[1] = new JWindow();
        obj[2] = "baz";

        // �e�X�g���{
        try {
            ConvertUtil.toList(obj, String.class);
            fail("IllegalArgumentException���X���[����܂���ł����B");
        } catch (IllegalArgumentException e) {
            // ����
            assertEquals(IllegalArgumentException.class.getName(),
                    e.getClass().getName());
            assertEquals("Unable to cast '" + JWindow.class.getName() 
                    + "' to '" + String.class.getName() + "'",
                    e.getMessage());
            assertEquals(ClassCastException.class.getName(),
                    e.getCause().getClass().getName());
            assertEquals("Unable to cast '" + JWindow.class.getName() 
                    + "' to '" + String.class.getName() + "'",
                    e.getCause().getMessage());
        }
    }

    /**
     * testToList11()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FD
     * <br><br>
     * ���͒l�F(����) obj:�v�f�������Ȃ�Collection<br>
     *         (����) elementClass:String.class<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) List:�v�f�������Ȃ�List<String> (�v�f�����O�ł��邱�Ƃ��m�F)<br>
     *         
     * <br>
     * ����obj���v�f�������Ȃ�Collection�������ꍇ�AelementsClass�Ŏw�肵���^��List���ԋp����邱�Ƃ��m�F����e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testToList11() throws Exception {
        // �O����
        Collection obj = new Vector(); 
        
        // �e�X�g���{
        List<String> result = ConvertUtil.toList(obj, String.class);

        // ����
        assertEquals(0, result.size());

    }

    /**
     * testToList12()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FD
     * <br><br>
     * ���͒l�F(����) obj:�P�v�f��ێ�����Collection<br>
     *                 *�v�f0:"foo"<br>
     *         (����) elementClass:String.class<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) List:�P�v�f������List<String> (�v�f�����P�ł��邱�Ƃ��m�F)<br>
     *                   *�v�f0:"foo"<br>
     *         
     * <br>
     * ����obj���P�v�f��ێ�����Collection�������ꍇ�AelementsClass�Ŏw�肵���^��List���ԋp����邱�Ƃ��m�F����e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testToList12() throws Exception {
        // �O����
        Collection obj = new Vector(); 
        obj.add("foo");
        
        // �e�X�g���{
        List<String> result = ConvertUtil.toList(obj, String.class);

        // ����
        assertEquals(1, result.size());
        assertEquals("foo", result.get(0));
    }

    /**
     * testToList13()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FD
     * <br><br>
     * ���͒l�F(����) obj:�R�v�f��ێ�����Collection<br>
     *                 *�v�f0:"foo"<br>
     *                 *�v�f2:"bar"<br>
     *                 *�v�f3:"baz"<br>
     *         (����) elementClass:String.class<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) List:�R�v�f������List<String> (�v�f�����R�ł��邱�Ƃ��m�F)<br>
     *                   *�v�f0:"foo"<br>
     *                   *�v�f1:"bar"<br>
     *                   *�v�f2:"baz"<br>
     *         
     * <br>
     * ����obj���R�v�f��ێ�����Collection�������ꍇ�AelementsClass�Ŏw�肵���^��List���ԋp����邱�Ƃ��m�F����e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testToList13() throws Exception {
        // �O����
        Collection obj = new Vector(); 
        obj.add("foo");
        obj.add("bar");
        obj.add("baz");
        
        // �e�X�g���{
        List<String> result = ConvertUtil.toList(obj, String.class);

        // ����
        assertEquals(3, result.size());
        assertEquals("foo", result.get(0));    
        assertEquals("bar", result.get(1));    
        assertEquals("baz", result.get(2));    
    }

    /**
     * testToList14()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) obj:�R�v�f��ێ�����Collection<br>
     *                 *�v�f0:"foo"<br>
     *                 *�v�f2:JWindow�C���X�^���X<br>
     *                 *�v�f3:"baz"<br>
     *         (����) elementClass:String.class<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalArgumentException<br>
     *                    ��O�̃��b�Z�[�W:
     *                    "Unable to cast '" + JWindow�̊��S�C���N���X�� 
     *                    �@+ "' to '" + ����elementClass�̊��S�C���N���X�� + "'"<br>
     *                    ���b�v���ꂽ��O:ClassCastException<br>
     *                    ���b�v���ꂽ��O�̃��b�Z�[�W:
     *                    "Unable to cast '" + JWindow�̊��S�C���N���X�� 
     *                    + "' to '" + ����elementClass�̊��S�C���N���X�� + "'"<br>
     *         
     * <br>
     * ����obj��Collection��elementClass�Ŏw�肵���^�Ɠ���A�������̓T�u�N���X
     * �ł͂Ȃ��v�f���܂܂�Ă����ꍇ�AIllegalArgumentException���X���[����邱��
     * ���m�F����e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testToList14() throws Exception {
        // �O����
        Object[] obj = new Object[3];
        obj[0] = "foo";
        obj[1] = new JWindow();
        obj[2] = "baz";

        // �e�X�g���{
        try {
            ConvertUtil.toList(obj, String.class);
            fail("IllegalArgumentException���X���[����܂���ł����B");
        } catch (IllegalArgumentException e) {
            // ����
            assertEquals(IllegalArgumentException.class.getName(),
                    e.getClass().getName());
            assertEquals("Unable to cast '" + JWindow.class.getName() 
                    + "' to '" + String.class.getName() + "'",
                    e.getMessage());
            assertEquals(ClassCastException.class.getName(),
                    e.getCause().getClass().getName());
            assertEquals("Unable to cast '" + JWindow.class.getName() 
                    + "' to '" + String.class.getName() + "'",
                    e.getCause().getMessage());
        }
    }

    /**
     * testConvertObjectClass01()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) obj:"object"<br>
     *         (����) clazz:null<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalArgumentException<br>
     *                    ��O�̃��b�Z�[�W:
     *                    "Argument 'clazz' (" + Object.class.getName() 
     *                    + ") is null"<br>
     *         
     * <br>
     * ����clazz��null�������ꍇ�AIllegalArgumentException���X���[����邱�Ƃ��m�F����e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testConvertObjectClass01() throws Exception {
        // �e�X�g���{
        try {
            ConvertUtil.convert("object", null);
            fail("IllegalArgumentException���X���[����܂���ł����B");
        } catch (IllegalArgumentException e) {
            // ����
            assertEquals(IllegalArgumentException.class.getName(),
                    e.getClass().getName());
            assertEquals("Argument 'clazz' (" + Object.class.getName()
                    + ") is null",
                    e.getMessage());
        }

    }

    /**
     * testConvertObjectClass02()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) obj:null<br>
     *         (����) clazz:Object.class<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) <T>:null<br>
     *         
     * <br>
     * ����obj��null�������ꍇ�Anull���ԋp����邱�Ƃ��m�F����e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testConvertObjectClass02() throws Exception {
        // �e�X�g���{
        // ����
        assertNull(ConvertUtil.convert(null, Object.class));
    }

    /**
     * testConvertObjectClass03()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FE
     * <br><br>
     * ���͒l�F(����) obj:List (ArrayList�ŃC���X�^���X������)<br>
     *         (����) clazz:ArrayList.class<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) <T>:List�C���X�^���X (����obj�Ɠ����C���X�^���X�ł��邱�Ƃ��m�F)<br>
     *         
     * <br>
     * ����obj�Ɠ����C���X�^���X���ԋp�����̂��m�F����e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testConvertObjectClass03() throws Exception {
        // �O����
        List list = new ArrayList();

        // �e�X�g���{
        List result = ConvertUtil.convert(list, ArrayList.class);

        // ����
        assertSame(list, result);
    }

    /**
     * testConvertObjectClass04()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FE
     * <br><br>
     * ���͒l�F(����) obj:Jwindow�C���X�^���X<br>
     *         (����) clazz:AlgorithmParameterGenerator.class<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) <T>:Jwindow�C���X�^���X�̕�����\��<br>
     *         
     * <br>
     * ����obj�̌^��clazz�̌^�ƌ݊������Ȃ��ꍇ�Aobj�̕�����\�����ԋp����邱�Ƃ��m�F����e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testConvertObjectClass04() throws Exception {
        // �O����
        JWindow window = new JWindow();

        // �e�X�g���{
        Object result = ConvertUtil.convert(window,
                AlgorithmParameterGenerator.class);

        // ����
        assertTrue(result instanceof String);
        assertEquals(window.toString(), result);
    }

    /**
     * testConvertIfNotNull01()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) obj:"object"<br>
     *         (����) clazz:null<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalArgumentException<br>
     *                    ��O�̃��b�Z�[�W:
     *                    "Argument 'clazz' (" + Object.class.getName() 
     *                    �@+ ") is null"<br>
     *         
     * <br>
     * ����clazz��null�������ꍇ�AIllegalArgumentException���X���[����邱�Ƃ��m�F����e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testConvertIfNotNull01() throws Exception {
        // �e�X�g���{
        try {
            ConvertUtil.convertIfNotNull("object", null);
            fail("IllegalArgumentException���X���[����܂���ł����B");
        } catch (IllegalArgumentException e) {
            // ����
            assertEquals(IllegalArgumentException.class.getName(),
                    e.getClass().getName());
            assertEquals("Argument 'clazz' (" + Object.class.getName()
                    + ") is null",
                    e.getMessage());
        }
    }

    /**
     * testConvertIfNotNull02()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) obj:null<br>
     *         (����) clazz:Object.class<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalArgumentException<br>
     *                    ��O�̃��b�Z�[�W:
     *                    "Unable to cast 'null' to '" 
     *                    �@+ ����clazz�̊��S�C���N���X�� + "'"<br>
     *                    ���b�v���ꂽ��O:ClassCastException<br>
     *                    ���b�v���ꂽ��O�̃��b�Z�[�W:
     *                    "Unable to cast 'null' to '" 
     *                    �@+ ����clazz�̊��S�C���N���X�� + "'"<br>
     *         
     * <br>
     * ����obj��null�������ꍇ�AIllegalArgumentException���X���[����邱�Ƃ��m�F����e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testConvertIfNotNull02() throws Exception {
        // �e�X�g���{
        try {
            ConvertUtil.convertIfNotNull(null, Object.class);
            fail("IllegalArgumentException���X���[����܂���ł����B");
        } catch (IllegalArgumentException e) {
            // ����
            assertEquals(IllegalArgumentException.class.getName(),
                    e.getClass().getName());
            assertEquals("Unable to cast 'null' to '" + Object.class.getName()
                    + "'", e.getMessage());
            assertEquals(ClassCastException.class.getName(),
                    e.getCause().getClass().getName());
            assertEquals("Unable to cast 'null' to '" + Object.class.getName()
                    + "'", e.getCause().getMessage());
        }
    }

    /**
     * testConvertIfNotNull03()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FE
     * <br><br>
     * ���͒l�F(����) obj:List (ArrayList�ŃC���X�^���X������)<br>
     *         (����) clazz:ArrayList.class<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) <T>:List�C���X�^���X (����obj�Ɠ����C���X�^���X�ł��邱�Ƃ��m�F)<br>
     *         
     * <br>
     * ����obj�Ɠ����C���X�^���X���ԋp�����̂��m�F����e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testConvertIfNotNull03() throws Exception {
        // �O����
        List list = new ArrayList();

        // �e�X�g���{
        List result = ConvertUtil.convertIfNotNull(list, ArrayList.class);

        // ����
        assertSame(list, result);
    }

    /**
     * testConvertIfNotNull04()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FE
     * <br><br>
     * ���͒l�F(����) obj:Jwindow�C���X�^���X<br>
     *         (����) clazz:AlgorithmParameterGenerator.class<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) <T>:Jwindow�C���X�^���X�̕�����\��<br>
     *         
     * <br>
     * ����obj�̌^��clazz�̌^�ƌ݊������Ȃ��ꍇ�Aobj�̕�����\�����ԋp����邱�Ƃ��m�F����e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testConvertIfNotNull04() throws Exception {
        // �O����
        JWindow window = new JWindow();

        // �e�X�g���{
        Object result = ConvertUtil.convert(window,
                AlgorithmParameterGenerator.class);

        // ����
        assertTrue(result instanceof String);
        assertEquals(window.toString(), result);
    }

    /**
     * testConvertObjectClassboolean01()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) obj:null<br>
     *         (����) clazz:null<br>
     *         (����) allowsNull:true<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalArgumentException<br>
     *                    ��O�̃��b�Z�[�W:
     *                    "Argument 'clazz' (" + Object.class.getName() 
     *                      + ") is null"<br>
     *         
     * <br>
     * ����clazz��null�������ꍇ�AIllegalArgumentException���X���[����邱�Ƃ��m�F����e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testConvertObjectClassboolean01() throws Exception {
        // �e�X�g���{
        try {
            ConvertUtil.convert(null, null, true);
            fail("IllegalArgumentException���X���[����܂���ł����B");
        } catch (IllegalArgumentException e) {
            // ����
            assertEquals(IllegalArgumentException.class.getName(),
                    e.getClass().getName());
            assertEquals("Argument 'clazz' (" + Object.class.getName()
                    + ") is null", e.getMessage());
        }

    }

    /**
     * testConvertObjectClassboolean02()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) obj:null<br>
     *         (����) clazz:Object.class<br>
     *         (����) allowsNull:false<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalArgumentException<br>
     *                    ��O�̃��b�Z�[�W:
     *                    "Unable to cast 'null' to '" 
     *                      + ����clazz�̊��S�C���N���X�� + "'"<br>
     *                    ���b�v���ꂽ��O:ClassCastException<br>
     *                    ���b�v���ꂽ��O�̃��b�Z�[�W:
     *                    "Unable to cast 'null' to '" 
     *                      + ����clazz�̊��S�C���N���X�� + "'"<br>
     *         
     * <br>
     * ����clazz��null�ł͂Ȃ��Aobj��null�AallowsNull��false�������ꍇ�A
     * IllegalArgumentException���X���[����邱�Ƃ��m�F����e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testConvertObjectClassboolean02() throws Exception {
        // �e�X�g���{
        try {
            ConvertUtil.convert(null, Object.class, false);
            fail("IllegalArgumentException���X���[����܂���ł����B");
        } catch (IllegalArgumentException e) {
            // ����
            assertEquals(IllegalArgumentException.class.getName(),
                    e.getClass().getName());
            assertEquals("Unable to cast 'null' to '" + Object.class.getName()
                    + "'", e.getMessage());
            assertEquals(ClassCastException.class.getName(),
                    e.getCause().getClass().getName());
            assertEquals("Unable to cast 'null' to '" + Object.class.getName()
                    + "'", e.getCause().getMessage());
        }    
    }

    /**
     * testConvertObjectClassboolean03()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) obj:null<br>
     *         (����) clazz:Object.class<br>
     *         (����) allowsNull:true<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) <T>:null<br>
     *         
     * <br>
     * ����clazz��null�ł͂Ȃ��Aobj��null�AallowsNull��true�������ꍇ�Anull���ԋp����邱�Ƃ��m�F����e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testConvertObjectClassboolean03() throws Exception {
        // �e�X�g���{
        // ����
        assertNull(ConvertUtil.convert(null, Object.class, true));
    }

    /**
     * testConvertObjectClassboolean04()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FE
     * <br><br>
     * ���͒l�F(����) obj:JWindow�C���X�^���X<br>
     *         (����) clazz:JWindow.class<br>
     *         (����) allowsNull:true<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) <T>:JWindow�C���X�^���X (����obj�Ɠ���C���X�^���X�ł��邱�Ƃ��m�F)<br>
     *         
     * <br>
     * ����clazz�̌^��obj�̌^�������������ꍇ�Aclazz�̌^�̃C���X�^���X���ԋp����邱�Ƃ��m�F����e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testConvertObjectClassboolean04() throws Exception {
        // �O����
        JWindow window = new JWindow();
        
        // �e�X�g���{
        JWindow result = ConvertUtil.convert(window, JWindow.class, true);

        // ����
        assertSame(window, result);
    }

    /**
     * testConvertObjectClassboolean05()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FE
     * <br><br>
     * ���͒l�F(����) obj:List (ArrayList�ŃC���X�^���X������)<br>
     *         (����) clazz:ArrayList.class<br>
     *         (����) allowsNull:true<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) <T>:List�C���X�^���X (����obj�Ɠ����C���X�^���X�ł��邱�Ƃ��m�F)<br>
     *         
     * <br>
     * ����obj�Ɠ����C���X�^���X���ԋp�����̂��m�F����e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testConvertObjectClassboolean05() throws Exception {
        //  �O����
        List list = new ArrayList();
        
        // �e�X�g���{
        List result = ConvertUtil.convert(list, ArrayList.class, true);

        // ����
        assertNotNull(result);
        assertSame(list, result);
    }

    /**
     * testConvertObjectClassboolean06()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FE
     * <br><br>
     * ���͒l�F(����) obj:Jwindow�C���X�^���X<br>
     *         (����) clazz:AlgorithmParameterGenerator.class<br>
     *         (����) allowsNull:true<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) <T>:Jwindow�C���X�^���X�̕�����\��<br>
     *         
     * <br>
     * ����clazz�̌^��obj�̌^�ƌ݊������Ȃ������ꍇ�Aobj�̕�����\�����ԋp����邱�Ƃ��m�F����e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testConvertObjectClassboolean06() throws Exception {
        //  �O����
        JWindow window = new JWindow();
        
        // �e�X�g���{
        Object result = ConvertUtil.convert(window,
                AlgorithmParameterGenerator.class,
                true);

        // ����
        assertTrue(result instanceof String);
        assertEquals(window.toString(), result);
    }

    /**
     * testConvertObjectClassboolean07()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) obj:"abc"<br>
     *         (����) clazz:BigInteger.class<br>
     *         (����) allowsNull:false<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalArgumentException<br>
     *                    ���b�v���ꂽ��O:ConversionException<br>
     *         
     * <br>
     * CnvertUtils#convert�ŗ�O�����������ꍇ�AIllegalArgumentException���X���[����邱�Ƃ��m�F����e�X�g
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testConvertObjectClassboolean07() throws Exception {
        
        // �e�X�g���{
        try {
            ConvertUtil.convert("abc", BigInteger.class, false);
            fail("IllegalArgumentException���X���[����܂���ł����B");
        } catch (IllegalArgumentException e) {
            // ����
            assertEquals(IllegalArgumentException.class.getName(),
                    e.getClass().getName());
            assertTrue(e.getCause() instanceof ConversionException);
        }
    }

    /**
     * testConvertPrimitiveArrayToList01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) value:null<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) Object:null<br>
     *         
     * <br>
     * ����value��null�������ꍇ�Anull���ԋp����邱�Ƃ��m�F����e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testConvertPrimitiveArrayToList01() throws Exception {
        // �e�X�g���{
        // ����
        assertNull(ConvertUtil.convertPrimitiveArrayToList(null));
    }

    /**
     * testConvertPrimitiveArrayToList02()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) value:""(�󕶎�)<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) Object:""(�󕶎�)<br>
     *         
     * <br>
     * ����value��""(�󕶎�)�������ꍇ�A""(�󕶎�)���ԋp����邱�Ƃ��m�F����e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testConvertPrimitiveArrayToList02() throws Exception {
        // �e�X�g���{
        Object result = ConvertUtil.convertPrimitiveArrayToList("");
        
        // ����
        assertNotNull(result);
        assertEquals("", result);
    }

    /**
     * testConvertPrimitiveArrayToList03()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) value:"  "(�󔒕�����)<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) Object:"  "(�󔒕�����)<br>
     *         
     * <br>
     * ����value��"  "(�󔒕�����)�������ꍇ�A""(�󕶎�)���ԋp����邱�Ƃ��m�F����e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testConvertPrimitiveArrayToList03() throws Exception {
        // �e�X�g���{
        Object result = ConvertUtil.convertPrimitiveArrayToList("  ");
        
        // ����
        assertNotNull(result);
        assertEquals("  ", result);
    }

    /**
     * testConvertPrimitiveArrayToList04()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FE
     * <br><br>
     * ���͒l�F(����) value:"noArray"<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) Object:"noArray"<br>
     *         
     * <br>
     * ����value���ʏ핶���񂾂����ꍇ�A�����̒l�����̂܂ܕԋp����邱�Ƃ��m�F����e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testConvertPrimitiveArrayToList04() throws Exception {
        // �e�X�g���{
        Object result = ConvertUtil.convertPrimitiveArrayToList("noarray");
        
        // ����
        assertNotNull(result);
        assertEquals("noarray", result);
    }

    /**
     * testConvertPrimitiveArrayToList05()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FD,E
     * <br><br>
     * ���͒l�F(����) value:�v�f��ێ����Ȃ�Object[]<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) Object:�v�f��ێ����Ȃ�Object[] (�v�f����0�ł��邱�Ƃ��m�F)<br>
     *         
     * <br>
     * ����value���v���~�e�B�u�^�ȊO�̔z��(�v�f��0)�������ꍇ�A�����̒l�����̂܂ܕԋp����邱�Ƃ��m�F����e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testConvertPrimitiveArrayToList05() throws Exception {
        // �O����
        Object[] value = new Object[0];

        // �e�X�g���{
        Object result = ConvertUtil.convertPrimitiveArrayToList(value);
        
        // ����
        assertNotNull(result);
        assertTrue(result instanceof Object[]);
        Object[] arrayResult = (Object[]) result;
        assertEquals(0, arrayResult.length);
    }

    /**
     * testConvertPrimitiveArrayToList06()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FD,E
     * <br><br>
     * ���͒l�F(����) value:�ȉ��̗v�f��ێ�����Object[]<br>
     *                 *�v�f0:"foo"<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) Object:�ȉ��̗v�f��ێ�����Object[] (�v�f����1�ł��邱�Ƃ��m�F)<br>
     *                   *�v�f0:"foo"<br>
     *         
     * <br>
     * ����value���v���~�e�B�u�^�ȊO�̔z��(�v�f��1)�������ꍇ�A�����̒l�����̂܂ܕԋp����邱�Ƃ��m�F����e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testConvertPrimitiveArrayToList06() throws Exception {
        // �O����
        Object[] value = {"foo"};

        // �e�X�g���{
        Object result = ConvertUtil.convertPrimitiveArrayToList(value);
        
        // ����
        assertNotNull(result);
        assertTrue(result instanceof Object[]);
        Object[] arrayResult = (Object[]) result;
        assertEquals(1, arrayResult.length);
        assertEquals("foo", arrayResult[0]);
        
    }

    /**
     * testConvertPrimitiveArrayToList07()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FD,E
     * <br><br>
     * ���͒l�F(����) value:�ȉ��̗v�f��ێ�����Object[]<br>
     *                 *�v�f0:"foo"<br>
     *                 *�v�f1:"bar"<br>
     *                 *�v�f2:"baz"<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) Object:�ȉ��̗v�f��ێ�����Object[] (�v�f����3�ł��邱�Ƃ��m�F)<br>
     *                   *�v�f0:"foo"<br>
     *                   *�v�f1:"bar"<br>
     *                   *�v�f3:"baz"<br>
     *         
     * <br>
     * ����value���v���~�e�B�u�^�ȊO�̔z��(�v�f��3)�������ꍇ�A�����̒l�����̂܂ܕԋp����邱�Ƃ��m�F����e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testConvertPrimitiveArrayToList07() throws Exception {
        // �O����
        Object[] value = {"foo", "bar", "baz"};

        // �e�X�g���{
        Object result = ConvertUtil.convertPrimitiveArrayToList(value);
        
        // ����
        assertNotNull(result);
        assertTrue(result instanceof Object[]);
        Object[] arrayResult = (Object[]) result;
        assertEquals(3, arrayResult.length);
        assertEquals("foo", arrayResult[0]);
        assertEquals("bar", arrayResult[1]);
        assertEquals("baz", arrayResult[2]);
    }

    /**
     * testConvertPrimitiveArrayToList08()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FD,E
     * <br><br>
     * ���͒l�F(����) value:�v�f��ێ����Ȃ�boolean[]<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) Object:�v�f��ێ����Ȃ�List (size��0�ł��邱�Ƃ��m�F)<br>
     *         
     * <br>
     * ����value���v�f��ێ����Ȃ�boolean�^�̔z�񂾂����ꍇ�A�v�f��ێ����Ȃ�List���ԋp����邱�Ƃ��m�F����e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testConvertPrimitiveArrayToList08() throws Exception {
        // �O����
        boolean[] value = new boolean[0];

        // �e�X�g���{
        Object result = ConvertUtil.convertPrimitiveArrayToList(value);
        
        // ����
        assertNotNull(result);
        assertTrue(result instanceof List);
        List listResult = (List) result;
        assertEquals(0, listResult.size());
    }

    /**
     * testConvertPrimitiveArrayToList09()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FD,E
     * <br><br>
     * ���͒l�F(����) value:�ȉ��̗v�f��ێ�����boolean[]<br>
     *                 *�v�f0:true<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) Object:�ȉ��̗v�f��ێ�����List(size��1�ł��邱�Ƃ��m�F)<br>
     *                   *�v�f0:true<br>
     *         
     * <br>
     * ����value��boolean�^�̔z��(�v�f��1)�������ꍇ�Aboolean�^�̒l��ێ�����List���ԋp����邱�Ƃ��m�F����e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testConvertPrimitiveArrayToList09() throws Exception {
        // �O����
        boolean[] value = {true};

        // �e�X�g���{
        Object result = ConvertUtil.convertPrimitiveArrayToList(value);
        
        // ����
        assertNotNull(result);
        assertTrue(result instanceof List);
        List listResult = (List) result;
        assertEquals(1, listResult.size());
        assertTrue((Boolean) listResult.get(0));
    }

    /**
     * testConvertPrimitiveArrayToList10()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FD,E
     * <br><br>
     * ���͒l�F(����) value:�ȉ��̗v�f��ێ�����boolean[]<br>
     *                 *�v�f0:true<br>
     *                 *�v�f1:false<br>
     *                 *�v�f2:true<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) Object:�ȉ��̗v�f��ێ�����List(size��3�ł��邱�Ƃ��m�F)<br>
     *                   *�v�f0:true<br>
     *                   *�v�f1:false<br>
     *                   *�v�f2:true<br>
     *         
     * <br>
     * ����value��boolean�^�̔z��(�v�f��3)�������ꍇ�Aboolean�^�̒l��ێ�����List���ԋp����邱�Ƃ��m�F����e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testConvertPrimitiveArrayToList10() throws Exception {
        // �O����
        boolean[] value = {true, false, true};

        // �e�X�g���{
        Object result = ConvertUtil.convertPrimitiveArrayToList(value);
        
        // ����
        assertNotNull(result);
        assertTrue(result instanceof List);
        List listResult = (List) result;
        assertEquals(3, listResult.size());
        assertTrue((Boolean) listResult.get(0));
        assertFalse((Boolean) listResult.get(1));
        assertTrue((Boolean) listResult.get(2));
    }

    /**
     * testConvertPrimitiveArrayToList11()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FD,E
     * <br><br>
     * ���͒l�F(����) value:�v�f��ێ����Ȃ�byte[]<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) Object:�v�f��ێ����Ȃ�List (size��0�ł��邱�Ƃ��m�F)<br>
     *         
     * <br>
     * ����value���v�f��ێ����Ȃ�byte�^�̔z�񂾂����ꍇ�A�v�f��ێ����Ȃ�List���ԋp����邱�Ƃ��m�F����e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testConvertPrimitiveArrayToList11() throws Exception {
        // �O����
        byte[] value = new byte[0];

        // �e�X�g���{
        Object result = ConvertUtil.convertPrimitiveArrayToList(value);
        
        // ����
        assertNotNull(result);
        assertTrue(result instanceof List);
        List listResult = (List) result;
        assertEquals(0, listResult.size());
    }

    /**
     * testConvertPrimitiveArrayToList12()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FD,E
     * <br><br>
     * ���͒l�F(����) value:�ȉ��̗v�f��ێ�����byte[]<br>
     *                 *�v�f0: (byte) 1<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) Object:�ȉ��̗v�f��ێ�����List(size��1�ł��邱�Ƃ��m�F)<br>
     *                   *�v�f0:"1"<br>
     *         
     * <br>
     * ����value��byte�^�̔z��(�v�f��1)�������ꍇ�AString�^�ɕϊ����ꂽ�l��ێ�����List���ԋp����邱�Ƃ��m�F����e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testConvertPrimitiveArrayToList12() throws Exception {
        // �O����
        byte[] value = {1};

        // �e�X�g���{
        Object result = ConvertUtil.convertPrimitiveArrayToList(value);
        
        // ����
        assertNotNull(result);
        assertTrue(result instanceof List);
        List listResult = (List) result;
        assertEquals(1, listResult.size());
        assertEquals("1", listResult.get(0));
    }

    /**
     * testConvertPrimitiveArrayToList13()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FD,E
     * <br><br>
     * ���͒l�F(����) value:�ȉ��̗v�f��ێ�����byte[]<br>
     *                 *�v�f0: (byte) 1<br>
     *                 *�v�f1: (byte) 2<br>
     *                 *�v�f2: (byte) 3<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) Object:�ȉ��̗v�f��ێ�����List(size��3�ł��邱�Ƃ��m�F)<br>
     *                   *�v�f0:"1"<br>
     *                   *�v�f1:"2"<br>
     *                   *�v�f2:"3"<br>
     *         
     * <br>
     * ����value��byte�^�̔z��(�v�f��3)�������ꍇ�AString�^�ɕϊ����ꂽ�l��ێ�����List���ԋp����邱�Ƃ��m�F����e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testConvertPrimitiveArrayToList13() throws Exception {
        // �O����
        byte[] value = {1, 2, 3};

        // �e�X�g���{
        Object result = ConvertUtil.convertPrimitiveArrayToList(value);
        
        // ����
        assertNotNull(result);
        assertTrue(result instanceof List);
        List listResult = (List) result;
        assertEquals(3, listResult.size());
        assertEquals("1", listResult.get(0));    
        assertEquals("2", listResult.get(1));    
        assertEquals("3", listResult.get(2));    
    }

    /**
     * testConvertPrimitiveArrayToList14()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FD,E
     * <br><br>
     * ���͒l�F(����) value:�v�f��ێ����Ȃ�char[]<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) Object:�v�f��ێ����Ȃ�List (size��0�ł��邱�Ƃ��m�F)<br>
     *         
     * <br>
     * ����value���v�f��ێ����Ȃ�char�^�̔z�񂾂����ꍇ�A�v�f��ێ����Ȃ�List���ԋp����邱�Ƃ��m�F����e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testConvertPrimitiveArrayToList14() throws Exception {
        // �O����
        char[] value = new char[0];

        // �e�X�g���{
        Object result = ConvertUtil.convertPrimitiveArrayToList(value);
        
        // ����
        assertNotNull(result);
        assertTrue(result instanceof List);
        List listResult = (List) result;
        assertEquals(0, listResult.size());
    }

    /**
     * testConvertPrimitiveArrayToList15()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FD,E
     * <br><br>
     * ���͒l�F(����) value:�ȉ��̗v�f��ێ�����char[]<br>
     *                 *�v�f0: 'A'<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) Object:�ȉ��̗v�f��ێ�����List(size��1�ł��邱�Ƃ��m�F)<br>
     *                   *�v�f0: "A"<br>
     *         
     * <br>
     * ����value��char�^�̔z��(�v�f��1)�������ꍇ�AString�^�ɕϊ����ꂽ�l��ێ�����List���ԋp����邱�Ƃ��m�F����e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testConvertPrimitiveArrayToList15() throws Exception {
        // �O����
        char[] value = {'A'};

        // �e�X�g���{
        Object result = ConvertUtil.convertPrimitiveArrayToList(value);
        
        // ����
        assertNotNull(result);
        assertTrue(result instanceof List);
        List listResult = (List) result;
        assertEquals(1, listResult.size());
        assertEquals("A", listResult.get(0));    
    }

    /**
     * testConvertPrimitiveArrayToList16()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FD,E
     * <br><br>
     * ���͒l�F(����) value:�ȉ��̗v�f��ێ�����char[]<br>
     *                 *�v�f0: 'A'<br>
     *                 *�v�f1: 'B'<br>
     *                 *�v�f2: 'C'<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) Object:�ȉ��̗v�f��ێ�����List(size��3�ł��邱�Ƃ��m�F)<br>
     *                   *�v�f0: "A"<br>
     *                   *�v�f1: "B"<br>
     *                   *�v�f2: "C"<br>
     *         
     * <br>
     * ����value��char�^�̔z��(�v�f��3)�������ꍇ�AString�^�ɕϊ����ꂽ�l��ێ�����List���ԋp����邱�Ƃ��m�F����e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testConvertPrimitiveArrayToList16() throws Exception {
        // �O����
        char[] value = {'A', 'B', 'C'};

        // �e�X�g���{
        Object result = ConvertUtil.convertPrimitiveArrayToList(value);
        
        // ����
        assertNotNull(result);
        assertTrue(result instanceof List);
        List listResult = (List) result;
        assertEquals(3, listResult.size());
        assertEquals("A", listResult.get(0));    
        assertEquals("B", listResult.get(1));    
        assertEquals("C", listResult.get(2));    
    }

    /**
     * testConvertPrimitiveArrayToList17()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FD,E
     * <br><br>
     * ���͒l�F(����) value:�v�f��ێ����Ȃ�double[]<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) Object:�v�f��ێ����Ȃ�List (size��0�ł��邱�Ƃ��m�F)<br>
     *         
     * <br>
     * ����value���v�f��ێ����Ȃ�double�^�̔z�񂾂����ꍇ�A�v�f��ێ����Ȃ�List���ԋp����邱�Ƃ��m�F����e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testConvertPrimitiveArrayToList17() throws Exception {
        // �O����
        double[] value = new double[0];

        // �e�X�g���{
        Object result = ConvertUtil.convertPrimitiveArrayToList(value);
        
        // ����
        assertNotNull(result);
        assertTrue(result instanceof List);
        List listResult = (List) result;
        assertEquals(0, listResult.size());
    }

    /**
     * testConvertPrimitiveArrayToList18()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FD,E
     * <br><br>
     * ���͒l�F(����) value:�ȉ��̗v�f��ێ�����double[]<br>
     *                 *�v�f0: 123.456<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) Object:�ȉ��̗v�f��ێ�����List(size��1�ł��邱�Ƃ��m�F)<br>
     *                   *�v�f0: "123.456"<br>
     *         
     * <br>
     * ����value��double�^�̔z��(�v�f��1)�������ꍇ�AString�^�ɕϊ����ꂽ�l��ێ�����List���ԋp����邱�Ƃ��m�F����e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testConvertPrimitiveArrayToList18() throws Exception {
        // �O����
        double[] value = {123.456};

        // �e�X�g���{
        Object result = ConvertUtil.convertPrimitiveArrayToList(value);
        
        // ����
        assertNotNull(result);
        assertTrue(result instanceof List);
        List listResult = (List) result;
        assertEquals(1, listResult.size());
        assertEquals("123.456", listResult.get(0));    
    }

    /**
     * testConvertPrimitiveArrayToList19()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FD,E
     * <br><br>
     * ���͒l�F(����) value:�ȉ��̗v�f��ێ�����double[]<br>
     *                 *�v�f0: 123.456<br>
     *                 *�v�f1: 12.34<br>
     *                 *�v�f2: 1.2<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) Object:�ȉ��̗v�f��ێ�����List(size��3�ł��邱�Ƃ��m�F)<br>
     *                   *�v�f0: "123.456"<br>
     *                   *�v�f1: "12.34"<br>
     *                   *�v�f2: "1.2"<br>
     *         
     * <br>
     * ����value��double�^�̔z��(�v�f��3)�������ꍇ�AString�^�ɕϊ����ꂽ�l��ێ�����List���ԋp����邱�Ƃ��m�F����e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testConvertPrimitiveArrayToList19() throws Exception {
        // �O����
        double[] value = {123.456, 12.34, 1.2};

        // �e�X�g���{
        Object result = ConvertUtil.convertPrimitiveArrayToList(value);
        
        // ����
        assertNotNull(result);
        assertTrue(result instanceof List);
        List listResult = (List) result;
        assertEquals(3, listResult.size());
        assertEquals("123.456", listResult.get(0));    
        assertEquals("12.34", listResult.get(1));    
        assertEquals("1.2", listResult.get(2));    
    }

    /**
     * testConvertPrimitiveArrayToList20()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FD,E
     * <br><br>
     * ���͒l�F(����) value:�v�f��ێ����Ȃ�float[]<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) Object:�v�f��ێ����Ȃ�List (size��0�ł��邱�Ƃ��m�F)<br>
     *         
     * <br>
     * ����value���v�f��ێ����Ȃ�float�^�̔z�񂾂����ꍇ�A�v�f��ێ����Ȃ�List���ԋp����邱�Ƃ��m�F����e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testConvertPrimitiveArrayToList20() throws Exception {
        // �O����
        float[] value = new float[0];

        // �e�X�g���{
        Object result = ConvertUtil.convertPrimitiveArrayToList(value);
        
        // ����
        assertNotNull(result);
        assertTrue(result instanceof List);
        List listResult = (List) result;
        assertEquals(0, listResult.size());
    }

    /**
     * testConvertPrimitiveArrayToList21()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FD,E
     * <br><br>
     * ���͒l�F(����) value:�ȉ��̗v�f��ێ�����float[]<br>
     *                 *�v�f0: 12.3F<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) Object:�ȉ��̗v�f��ێ�����List(size��1�ł��邱�Ƃ��m�F)<br>
     *                   *�v�f0: "12.3"<br>
     *         
     * <br>
     * ����value��float�^�̔z��(�v�f��1)�������ꍇ�AString�^�ɕϊ����ꂽ�l��ێ�����List���ԋp����邱�Ƃ��m�F����e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testConvertPrimitiveArrayToList21() throws Exception {
        // �O����
        float[] value = {12.3F};

        // �e�X�g���{
        Object result = ConvertUtil.convertPrimitiveArrayToList(value);
        
        // ����
        assertNotNull(result);
        assertTrue(result instanceof List);
        List listResult = (List) result;
        assertEquals(1, listResult.size());
        assertEquals("12.3", listResult.get(0));    
    }

    /**
     * testConvertPrimitiveArrayToList22()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FD,E
     * <br><br>
     * ���͒l�F(����) value:�ȉ��̗v�f��ێ�����float[]<br>
     *                 *�v�f0: 12.3F<br>
     *                 *�v�f1: 1.2F<br>
     *                 *�v�f2: 1F<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) Object:�ȉ��̗v�f��ێ�����List(size��3�ł��邱�Ƃ��m�F)<br>
     *                   *�v�f0: "12.3"<br>
     *                   *�v�f1: "1.2"<br>
     *                   *�v�f2: "1.0"<br>
     *         
     * <br>
     * ����value��float�^�̔z��(�v�f��3)�������ꍇ�AString�^�ɕϊ����ꂽ�l��ێ�����List���ԋp����邱�Ƃ��m�F����e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testConvertPrimitiveArrayToList22() throws Exception {
        // �O����
        float[] value = {12.3F, 1.2F, 1F};

        // �e�X�g���{
        Object result = ConvertUtil.convertPrimitiveArrayToList(value);
        
        // ����
        assertNotNull(result);
        assertTrue(result instanceof List);
        List listResult = (List) result;
        assertEquals(3, listResult.size());
        assertEquals("12.3", listResult.get(0));    
        assertEquals("1.2", listResult.get(1));    
        assertEquals("1.0", listResult.get(2));    
    }

    /**
     * testConvertPrimitiveArrayToList23()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FD,E
     * <br><br>
     * ���͒l�F(����) value:�v�f��ێ����Ȃ�int[]<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) Object:�v�f��ێ����Ȃ�List (size��0�ł��邱�Ƃ��m�F)<br>
     *         
     * <br>
     * ����value���v�f��ێ����Ȃ�int�^�̔z�񂾂����ꍇ�A�v�f��ێ����Ȃ�List���ԋp����邱�Ƃ��m�F����e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testConvertPrimitiveArrayToList23() throws Exception {
        // �O����
        int[] value = new int[0];

        // �e�X�g���{
        Object result = ConvertUtil.convertPrimitiveArrayToList(value);
        
        // ����
        assertNotNull(result);
        assertTrue(result instanceof List);
        List listResult = (List) result;
        assertEquals(0, listResult.size());
    }

    /**
     * testConvertPrimitiveArrayToList24()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FD,E
     * <br><br>
     * ���͒l�F(����) value:�ȉ��̗v�f��ێ�����int[]<br>
     *                 *�v�f0: 1<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) Object:�ȉ��̗v�f��ێ�����List(size��1�ł��邱�Ƃ��m�F)<br>
     *                   *�v�f0: "1"<br>
     *         
     * <br>
     * ����value��int�^�̔z��(�v�f��1)�������ꍇ�AString�^�ɕϊ����ꂽ�l��ێ�����List���ԋp����邱�Ƃ��m�F����e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testConvertPrimitiveArrayToList24() throws Exception {
        // �O����
        int[] value = {1};

        // �e�X�g���{
        Object result = ConvertUtil.convertPrimitiveArrayToList(value);
        
        // ����
        assertNotNull(result);
        assertTrue(result instanceof List);
        List listResult = (List) result;
        assertEquals(1, listResult.size());
        assertEquals("1", listResult.get(0));    
    }

    /**
     * testConvertPrimitiveArrayToList25()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FD,E
     * <br><br>
     * ���͒l�F(����) value:�ȉ��̗v�f��ێ�����int[]<br>
     *                 *�v�f0: 1<br>
     *                 *�v�f1: 2<br>
     *                 *�v�f2: 3<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) Object:�ȉ��̗v�f��ێ�����List(size��3�ł��邱�Ƃ��m�F)<br>
     *                   *�v�f0: "1"<br>
     *                   *�v�f1: "2"<br>
     *                   *�v�f2: "3"<br>
     *         
     * <br>
     * ����value��int�^�̔z��(�v�f��3)�������ꍇ�AString�^�ɕϊ����ꂽ�l��ێ�����List���ԋp����邱�Ƃ��m�F����e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testConvertPrimitiveArrayToList25() throws Exception {
        // �O����
        int[] value = {1, 2, 3};

        // �e�X�g���{
        Object result = ConvertUtil.convertPrimitiveArrayToList(value);
        
        // ����
        assertNotNull(result);
        assertTrue(result instanceof List);
        List listResult = (List) result;
        assertEquals(3, listResult.size());
        assertEquals("1", listResult.get(0));    
        assertEquals("2", listResult.get(1));    
        assertEquals("3", listResult.get(2));    
    }

    /**
     * testConvertPrimitiveArrayToList26()
     * <br><br>
     * 
     * (����n) or (�ُ�n)
     * <br>
     * �ϓ_�FD,E
     * <br><br>
     * ���͒l�F(����) value:�v�f��ێ����Ȃ�long[]<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) Object:�v�f��ێ����Ȃ�List (size��0�ł��邱�Ƃ��m�F)<br>
     *         
     * <br>
     * ����value���v�f��ێ����Ȃ�long�^�̔z�񂾂����ꍇ�A�v�f��ێ����Ȃ�List���ԋp����邱�Ƃ��m�F����e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testConvertPrimitiveArrayToList26() throws Exception {
        // �O����
        long[] value = new long[0];

        // �e�X�g���{
        Object result = ConvertUtil.convertPrimitiveArrayToList(value);
        
        // ����
        assertNotNull(result);
        assertTrue(result instanceof List);
        List listResult = (List) result;
        assertEquals(0, listResult.size());
    }

    /**
     * testConvertPrimitiveArrayToList27()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FD,E
     * <br><br>
     * ���͒l�F(����) value:�ȉ��̗v�f��ێ�����lobg[]<br>
     *                 *�v�f0: 1L<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) Object:�ȉ��̗v�f��ێ�����List(size��1�ł��邱�Ƃ��m�F)<br>
     *                   *�v�f0: "1"<br>
     *         
     * <br>
     * ����value��long�^�̔z��(�v�f��1)�������ꍇ�AString�^�ɕϊ����ꂽ�l��ێ�����List���ԋp����邱�Ƃ��m�F����e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testConvertPrimitiveArrayToList27() throws Exception {
        // �O����
        long[] value = {1L};

        // �e�X�g���{
        Object result = ConvertUtil.convertPrimitiveArrayToList(value);
        
        // ����
        assertNotNull(result);
        assertTrue(result instanceof List);
        List listResult = (List) result;
        assertEquals(1, listResult.size());
        assertEquals("1", listResult.get(0));    
    }

    /**
     * testConvertPrimitiveArrayToList28()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FD,E
     * <br><br>
     * ���͒l�F(����) value:�ȉ��̗v�f��ێ�����long[]<br>
     *                 *�v�f0: 1L<br>
     *                 *�v�f1: 2L<br>
     *                 *�v�f2: 3L<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) Object:�ȉ��̗v�f��ێ�����List(size��3�ł��邱�Ƃ��m�F)<br>
     *                   *�v�f0: "1"<br>
     *                   *�v�f1: "2"<br>
     *                   *�v�f2: "3"<br>
     *         
     * <br>
     * ����value��long�^�̔z��(�v�f��3)�������ꍇ�AString�^�ɕϊ����ꂽ�l��ێ�����List���ԋp����邱�Ƃ��m�F����e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testConvertPrimitiveArrayToList28() throws Exception {
        // �O����
        long[] value = {1L, 2L, 3L};

        // �e�X�g���{
        Object result = ConvertUtil.convertPrimitiveArrayToList(value);
        
        // ����
        assertNotNull(result);
        assertTrue(result instanceof List);
        List listResult = (List) result;
        assertEquals(3, listResult.size());
        assertEquals("1", listResult.get(0));    
        assertEquals("2", listResult.get(1));    
        assertEquals("3", listResult.get(2));    
    }

    /**
     * testConvertPrimitiveArrayToList29()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FD,E
     * <br><br>
     * ���͒l�F(����) value:�v�f��ێ����Ȃ�short[]<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) Object:�v�f��ێ����Ȃ�List (size��0�ł��邱�Ƃ��m�F)<br>
     *         
     * <br>
     * ����value���v�f��ێ����Ȃ�short�^�̔z�񂾂����ꍇ�A�v�f��ێ����Ȃ�List���ԋp����邱�Ƃ��m�F����e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testConvertPrimitiveArrayToList29() throws Exception {
        // �O����
        short[] value = new short[0];

        // �e�X�g���{
        Object result = ConvertUtil.convertPrimitiveArrayToList(value);
        
        // ����
        assertNotNull(result);
        assertTrue(result instanceof List);
        List listResult = (List) result;
        assertEquals(0, listResult.size());

    }

    /**
     * testConvertPrimitiveArrayToList30()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FD,E
     * <br><br>
     * ���͒l�F(����) value:�ȉ��̗v�f��ێ�����short[]<br>
     *                 *�v�f0: (short) 1<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) Object:�ȉ��̗v�f��ێ�����List(size��1�ł��邱�Ƃ��m�F)<br>
     *                   *�v�f0: "1"<br>
     *         
     * <br>
     * ����value��short�^�̔z��(�v�f��1)�������ꍇ�AString�^�ɕϊ����ꂽ�l��ێ�����List���ԋp����邱�Ƃ��m�F����e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testConvertPrimitiveArrayToList30() throws Exception {
        // �O����
        short[] value = {(short) 1};

        // �e�X�g���{
        Object result = ConvertUtil.convertPrimitiveArrayToList(value);
        
        // ����
        assertNotNull(result);
        assertTrue(result instanceof List);
        List listResult = (List) result;
        assertEquals(1, listResult.size());
    }

    /**
     * testConvertPrimitiveArrayToList31()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FD,E
     * <br><br>
     * ���͒l�F(����) value:�ȉ��̗v�f��ێ�����short[]<br>
     *                 *�v�f0: (short) 1<br>
     *                 *�v�f1: (short) 2<br>
     *                 *�v�f2: (short) 3<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) Object:�ȉ��̗v�f��ێ�����List(size��3�ł��邱�Ƃ��m�F)<br>
     *                   *�v�f0: "1"<br>
     *                   *�v�f1: "2"<br>
     *                   *�v�f2: "3"<br>
     *         
     * <br>
     * ����value��short�^�̔z��(�v�f��3)�������ꍇ�AString�^�ɕϊ����ꂽ�l��ێ�����List���ԋp����邱�Ƃ��m�F����e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testConvertPrimitiveArrayToList31() throws Exception {
        // �O����
        short[] value = {(short) 1, (short) 2, (short) 3};

        // �e�X�g���{
        Object result = ConvertUtil.convertPrimitiveArrayToList(value);
        
        // ����
        assertNotNull(result);
        assertTrue(result instanceof List);
        List listResult = (List) result;
        assertEquals(3, listResult.size());
        assertEquals("1", listResult.get(0));    
        assertEquals("2", listResult.get(1));    
        assertEquals("3", listResult.get(2));    
    }

    /**
     * testToListOfMap01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FD, E
     * <br><br>
     * ���͒l�F(����) obj:null<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) List<Map<String, Object>:�v�f�������Ȃ�Object[]<br>
     *         
     * <br>
     * ����obj��null�̏ꍇ�A�v�f�������Ȃ�List<Map<String,Object>�C���X�^���X���Ԃ邱�Ƃ��m�F����e�X�g
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testToListOfMap01() throws Exception {
        // �O����
        // �e�X�g���{
        List<Map<String, Object>> actual = ConvertUtil.toListOfMap(null);

        // ����
        assertNotNull(actual);
        assertTrue(actual.isEmpty());
    }

    /**
     * testToListOfMap02()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FD, E
     * <br><br>
     * ���͒l�F(����) obj:3�v�f������JavaBean�X�^�u�̃��X�g<br>
     *                �v�f0=JavaBean�X�^�u�C���X�^���X<br>
     *                ���t�B�[���hA="value00"<br>
     *                �v�f1=JavaBean�X�^�u�C���X�^���X<br>
     *                ���t�B�[���hA="value01"<br>
     *                �v�f2=JavaBean�X�^�u�C���X�^���X<br>
     *                ���t�B�[���hA="value02"<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) List<Map<String, Object>:
     *      3�v�f������List<Map<String, Object>>�C���X�^���X<br>
     *                  <br>
     *                  �v�f0-=Map<String,Object>�C���X�^���X<br>
     *                  ��key="a",value="value00"<br>
     *                  �v�f1=Map<String,Object>�C���X�^���X<br>
     *                  ��key="a",value="value01"<br>
     *                  �v�f2=Map<String,Object>�C���X�^���X<br>
     *                  ��key="a",value="value02"<br>
     *         
     * <br>
     * ����obj��������JavaBean�v�f�������X�g�̏ꍇ�A
     * 3�̗v�f������List<Map<String,Object>�C���X�^���X���Ԃ邱�Ƃ��m�F����e�X�g
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testToListOfMap02() throws Exception {

        List<ConvertUtil_Stub01> obj = new ArrayList<ConvertUtil_Stub01>();
        ConvertUtil_Stub01 bean = new ConvertUtil_Stub01();
        bean.setA("value00");
        obj.add(bean);
        ConvertUtil_Stub01 bean02 = new ConvertUtil_Stub01();
        bean02.setA("value01");
        obj.add(bean02);
        ConvertUtil_Stub01 bean03 = new ConvertUtil_Stub01();
        bean03.setA("value02");
        obj.add(bean03);

        // �e�X�g���{
        List<Map<String, Object>> actual = ConvertUtil.toListOfMap(obj);

        // ����
        assertNotNull(actual);
        assertEquals(3, actual.size());
        assertEquals("value00", actual.get(0).get("a"));
        assertEquals("value01", actual.get(1).get("a"));
        assertEquals("value02", actual.get(2).get("a"));
    }

    /**
     * testToListOfMap03()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FD, E
     * <br><br>
     * ���͒l�F(����) obj:3�v�f������JavaBean�X�^�u�z��<br>
     *                �v�f0=JavaBean�X�^�u�C���X�^���X<br>
     *                ���t�B�[���hA="value00"<br>
     *                �v�f1=JavaBean�X�^�u�C���X�^���X<br>
     *                ���t�B�[���hA="value01"<br>
     *                �v�f2=JavaBean�X�^�u�C���X�^���X<br>
     *                ���t�B�[���hA="value02"<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) List<Map<String, Object>:
     * 3�v�f������List<Map<String, Object>>�C���X�^���X<br>
     *                  <br>
     *                  �v�f0-=Map<String,Object>�C���X�^���X<br>
     *                  ��key="a",value="value00"<br>
     *                  �v�f1=Map<String,Object>�C���X�^���X<br>
     *                  ��key="a",value="value01"<br>
     *                  �v�f2=Map<String,Object>�C���X�^���X<br>
     *                  ��key="a",value="value02"<br>
     *         
     * <br>
     * ����obj��������JavaBean�v�f�����z��̏ꍇ�A
     * 3�̗v�f������List<Map<String,Object>�C���X�^���X���Ԃ邱�Ƃ��m�F����e�X�g
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testToListOfMap03() throws Exception {
        ConvertUtil_Stub01 bean = new ConvertUtil_Stub01();
        bean.setA("value00");
        ConvertUtil_Stub01 bean02 = new ConvertUtil_Stub01();
        bean02.setA("value01");
        ConvertUtil_Stub01 bean03 = new ConvertUtil_Stub01();
        bean03.setA("value02");
        
        ConvertUtil_Stub01[] obj =
            new ConvertUtil_Stub01[] {bean, bean02, bean03};

        // �e�X�g���{
        List<Map<String, Object>> actual = ConvertUtil.toListOfMap(obj);

        // ����
        assertNotNull(actual);
        assertEquals(3, actual.size());
        assertEquals("value00", actual.get(0).get("a"));
        assertEquals("value01", actual.get(1).get("a"));
        assertEquals("value02", actual.get(2).get("a"));
    }

    /**
     * testToListOfMap04()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FD, E
     * <br><br>
     * ���͒l�F(����) obj:3�v�f������List<Map<String, Object>>�C���X�^���X<br>
     *                <br>
     *                �v�f0=Map<String,Object>�C���X�^���X<br>
     *                ��key="a",value="value00"<br>
     *                �v�f1=Map<String,Object>�C���X�^���X<br>
     *                ��key="a",value="value01"<br>
     *                �v�f2=Map<String,Object>�C���X�^���X<br>
     *                ��key="a",value="value02"<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) List<Map<String, Object>:
     *      3�v�f������List<Map<String, Object>>�C���X�^���X<br>
     *                  <br>
     *                  �v�f0-=Map<String,Object>�C���X�^���X<br>
     *                  ��key="a",value="value00"<br>
     *                  �v�f1=Map<String,Object>�C���X�^���X<br>
     *                  ��key="a",value="value01"<br>
     *                  �v�f2=Map<String,Object>�C���X�^���X<br>
     *                  ��key="a",value="value02"<br>
     *         
     * <br>
     * ����obj��������Map�v�f�������X�g�̏ꍇ�A
     * 3�̗v�f������List<Map<String,Object>�C���X�^���X���Ԃ邱�Ƃ��m�F����e�X�g
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testToListOfMap04() throws Exception {
        List<Map<String, Object>> obj = new ArrayList<Map<String, Object>>();
        Map<String, Object> row = new HashMap<String, Object>();
        row.put("a", "value00");
        obj.add(row);
        Map<String, Object> row02 = new HashMap<String, Object>();
        row02.put("a", "value01");
        obj.add(row02);
        Map<String, Object> row03 = new HashMap<String, Object>();
        row03.put("a", "value02");
        obj.add(row03);


        // �e�X�g���{
        List<Map<String, Object>> actual = ConvertUtil.toListOfMap(obj);

        // ����
        assertNotNull(actual);
        assertEquals(3, actual.size());
        assertEquals("value00", actual.get(0).get("a"));
        assertEquals("value01", actual.get(1).get("a"));
        assertEquals("value02", actual.get(2).get("a"));
    }

    /**
     * testToListOfMap05()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FD, E
     * <br><br>
     * ���͒l�F(����) obj:3�v�f������Map<String, Object>�C���X�^���X�̔z��<br>
     *                <br>
     *                �v�f0=Map<String,Object>�C���X�^���X<br>
     *                ��key="a",value="value00"<br>
     *                �v�f1=Map<String,Object>�C���X�^���X<br>
     *                ��key="a",value="value01"<br>
     *                �v�f2=Map<String,Object>�C���X�^���X<br>
     *                ��key="a",value="value02"<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) List<Map<String, Object>:
     *      3�v�f������List<Map<String, Object>>�C���X�^���X<br>
     *                  <br>
     *                  �v�f0-=Map<String,Object>�C���X�^���X<br>
     *                  ��key="a",value="value00"<br>
     *                  �v�f1=Map<String,Object>�C���X�^���X<br>
     *                  ��key="a",value="value01"<br>
     *                  �v�f2=Map<String,Object>�C���X�^���X<br>
     *                  ��key="a",value="value02"<br>
     *         
     * <br>
     * ����obj��������Map�v�f�����z��̏ꍇ�A
     * 3�̗v�f������List<Map<String,Object>�C���X�^���X���Ԃ邱�Ƃ��m�F����e�X�g
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testToListOfMap05() throws Exception {
        Map<String, Object> row = new HashMap<String, Object>();
        row.put("a", "value00");
        Map<String, Object> row02 = new HashMap<String, Object>();
        row02.put("a", "value01");
        Map<String, Object> row03 = new HashMap<String, Object>();
        row03.put("a", "value02");
        
        Map[] obj = new Map[] {row, row02, row03};


        // �e�X�g���{
        List<Map<String, Object>> actual = ConvertUtil.toListOfMap(obj);

        // ����
        assertNotNull(actual);
        assertEquals(3, actual.size());
        assertEquals("value00", actual.get(0).get("a"));
        assertEquals("value01", actual.get(1).get("a"));
        assertEquals("value02", actual.get(2).get("a"));
    }
    
    /**
     * testToListOfMap06()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) obj:JavaBean�X�^�u�C���X�^���X<br>
     *                ���t�B�[���hA="value00"<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) List<Map<String, Object>:
     *      1�v�f������List<Map<String, Object>>�C���X�^���X<br>
     *                  <br>
     *                  �v�f0-=Map<String,Object>�C���X�^���X<br>
     *                  ��key=A,value="value00"<br>
     *         
     * <br>
     * ����obj��JavaBean�̏ꍇ�A1�̗v�f������
     * List<Map<String,Object>�C���X�^���X���Ԃ邱�Ƃ��m�F����e�X�g
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testToListOfMap06() throws Exception {
        ConvertUtil_Stub01 obj = new ConvertUtil_Stub01();
        obj.setA("value00");

        // �e�X�g���{
        List<Map<String, Object>> actual = ConvertUtil.toListOfMap(obj);

        // ����
        assertNotNull(actual);
        assertEquals(1, actual.size());
        assertEquals("value00", actual.get(0).get("a"));
    }

    /**
     * testToListOfMap08()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) obj:JavaBean�X�^�u�C���X�^���X<br>
     *                ���t�B�[���hA="value00"<br>
     *         (���) PropertyUtils#describe�̎��s����:
     *                  InvocationTargetException���X���[
     *                    ��JavaBean��getter��RuntimeException���X���[<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalArgumentException<br>
     *                    ���b�v���ꂽ��O�FInvocationTargetException<br>
     *         
     * <br>
     * PropertyUtils#descrive��InvocationTargetException���X���[���ꂽ�ꍇ�̃e�X�g
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testToListOfMap08() throws Exception {
        // �O����
        ConvertUtil_Stub02 obj = new ConvertUtil_Stub02();
        obj.setA("value00");

        try {
            // �e�X�g���{
            ConvertUtil.toListOfMap(obj);
            fail();
        } catch (IllegalArgumentException e) {
            // ����
            assertEquals(IllegalArgumentException.class.getName(),
                    e.getClass().getName());
            assertTrue(e.getCause() instanceof InvocationTargetException);
        }

    }

    /**
     * testToListOfMap09()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) obj:JavaBean�X�^�u�C���X�^���X<br>
     *                ���t�B�[���hA="value00"<br>
     *         (���) PropertyUtils#describe�̎��s����:
     *                  IllegalAccessException���X���[
     *                    ��PropertyUtilsBean�̃X�^�u��IllegalAccessException���X���[<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalArgumentException<br>
     *                    ���b�v���ꂽ��O�FIllegalAccessException<br>
     *         
     * <br>
     * PropertyUtils#descrive��IllegalAccessException���X���[���ꂽ�ꍇ�̃e�X�g
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testToListOfMap09() throws Exception {
        // �O����
        List<ConvertUtil_Stub01> obj = new ArrayList<ConvertUtil_Stub01>();
        ConvertUtil_Stub01 bean = new ConvertUtil_Stub01();
        bean.setA("value00");
        obj.add(bean);
        BeanUtilsBean beanUtilsBean = BeanUtilsBean.getInstance();
        UTUtil.setPrivateField(beanUtilsBean, "propertyUtilsBean",
                new ConvertUtil_PropertyUtilsBeanStub01());
        
        try {
            // �e�X�g���{
            ConvertUtil.toListOfMap(obj);
            fail();
        } catch (IllegalArgumentException e) {
            // ����
            assertEquals(IllegalArgumentException.class.getName(),
                    e.getClass().getName());
            assertTrue(e.getCause() instanceof IllegalAccessException);
        }

    }

    /**
     * testToListOfMap10()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) obj:JavaBean�X�^�u�C���X�^���X<br>
     *                ���t�B�[���hA="value00"<br>
     *         (���) PropertyUtils#describe�̎��s����:
     *                  NoSuchMethodException���X���[
     *                    ��PropertyUtilsBean�̃X�^�u��NoSuchMethodException���X���[<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalArgumentException<br>
     *                    ���b�v���ꂽ��O�FNoSuchMethodException<br>
     *         
     * <br>
     * PropertyUtils#descrive��NoSuchMethodException���X���[���ꂽ�ꍇ�̃e�X�g
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testToListOfMap10() throws Exception {
        // �O����
        List<ConvertUtil_Stub01> obj = new ArrayList<ConvertUtil_Stub01>();
        ConvertUtil_Stub01 bean = new ConvertUtil_Stub01();
        bean.setA("value00");
        obj.add(bean);
        BeanUtilsBean beanUtilsBean = BeanUtilsBean.getInstance();
        UTUtil.setPrivateField(beanUtilsBean, "propertyUtilsBean",
                new ConvertUtil_PropertyUtilsBeanStub02());
        
        try {
            // �e�X�g���{
            ConvertUtil.toListOfMap(obj);
            fail();
        } catch (IllegalArgumentException e) {
            // ����
            assertEquals(IllegalArgumentException.class.getName(),
                    e.getClass().getName());
            assertTrue(e.getCause() instanceof NoSuchMethodException);
        }

    }
}
