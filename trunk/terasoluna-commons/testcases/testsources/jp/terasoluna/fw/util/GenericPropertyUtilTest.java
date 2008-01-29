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
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.PropertyUtilsBean;

import jp.terasoluna.utlib.LogUTUtil;
import jp.terasoluna.utlib.UTUtil;
import junit.framework.TestCase;

/**
 * {@link jp.terasoluna.fw.GenericPropertyUtil} �N���X�̃e�X�g�B
 *
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4>
 * <code>JavaBean</code>�̃v���p�e�B��<code>Generics</code>���������߂̃��[�e�B���e�B�N���X�B
 * <p>
 *
 * @see jp.terasoluna.fw.util.GenericPropertyUtil
 */
public class GenericPropertyUtilTest extends TestCase {

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
        junit.swingui.TestRunner.run(GenericPropertyUtilTest.class);
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
        LogUTUtil.flush();
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
    public GenericPropertyUtilTest(String name) {
        super(name);
    }

    /**
     * testResolveCollectionType01()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) bean:null<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalArgumentException<br>
     *
     * <br>
     * ����bean��null�̏ꍇ��IllegalArgumentException���X���[����邱�Ƃ��m�F����e�X�g�B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testResolveCollectionType01() throws Exception {
        try {
            // �e�X�g���{
            GenericPropertyUtil.resolveCollectionType(null, null);
            // ����
            fail("��O���X���[����܂���ł����B");
        } catch (IllegalArgumentException e) {
            assertEquals(IllegalArgumentException.class.getName(),
                    e.getClass().getName());
        }
    }

    /**
     * testResolveCollectionType02()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) bean:Object�C���X�^���X<br>
     *         (����) name:null<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalArgumentException<br>
     *
     * <br>
     * ����name��null�̏ꍇ��IllegalArgumentException���X���[����邱�Ƃ��m�F����e�X�g�B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testResolveCollectionType02() throws Exception {
        try {
            // �e�X�g���{
            GenericPropertyUtil.resolveCollectionType(
                    new Object(), null);
            // ����
            fail("��O���X���[����܂���ł����B");
        } catch (IllegalArgumentException e) {
            assertEquals(IllegalArgumentException.class.getName(),
                    e.getClass().getName());
        }
    }

    /**
     * testResolveCollectionType03()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) bean:Object��Class�C���X�^���X<br>
     *         (����) name:""�i�󕶎��j<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalArgumentException<br>
     *
     * <br>
     * ����name���󕶎��̏ꍇ��IllegalArgumentException���X���[����邱�Ƃ��m�F����e�X�g�B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testResolveCollectionType03() throws Exception {
        try {
            // �e�X�g���{
            GenericPropertyUtil.resolveCollectionType(
                    new Object(), "");
            // ����
            fail("��O���X���[����܂���ł����B");
        } catch (IllegalArgumentException e) {
            assertEquals(IllegalArgumentException.class.getName(),
                    e.getClass().getName());
        }
    }

    /**
     * testResolveCollectionType04()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) bean:Object��Class�C���X�^���X<br>
     *         (����) name:"   "�i�󔒕�����j<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalArgumentException<br>
     *
     * <br>
     * ����name���󔒕�����̏ꍇ��IllegalArgumentException���X���[����邱�Ƃ��m�F����e�X�g�B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testResolveCollectionType04() throws Exception {
        try {
            // �e�X�g���{
            GenericPropertyUtil.resolveCollectionType(
                    new Object(), "   ");
            // ����
            fail("��O���X���[����܂���ł����B");
        } catch (IllegalArgumentException e) {
            assertEquals(IllegalArgumentException.class.getName(),
                    e.getClass().getName());
        }
    }

    /**
     * testResolveCollectionType05()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) bean:�t�B�[���hList<String> list0�������A����getter�������Ȃ��N���X�B<br>
     *         (����) name:"list0"<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalArgumentException<br>
     *
     * <br>
     * �Ή����郁�\�b�h�����݂��Ȃ��ꍇ��IllegalArgumentException���X���[����邱�Ƃ��m�F����e�X�g�B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testResolveCollectionType05() throws Exception {
        try {
            // �e�X�g���{
            GenericPropertyUtil.resolveCollectionType(
                    new GenericPropertyUtil_Stub01(),
                    "list0");
            // ����
            fail("��O���X���[����܂���ł����B");
        } catch (IllegalArgumentException e) {
            assertEquals(IllegalArgumentException.class.getName(),
                    e.getClass().getName());
        }
    }

    /**
     * testResolveCollectionType06()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FE
     * <br><br>
     * ���͒l�F(����) bean:�t�B�[���hList<String> list1�ƁA����getter�����N���X�B<br>
     *         (����) name:"list1"<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) Class:String.class<br>
     *
     * <br>
     * �v�f�^���擾�ł��邱�Ƃ��m�F����e�X�g�B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testResolveCollectionType06() throws Exception {

        // �e�X�g���{
        Class actual = GenericPropertyUtil.resolveCollectionType(
                new GenericPropertyUtil_Stub01(),
                "list1");

        // ����
        assertEquals(String.class, actual);
    }

    /**
     * testResolveCollectionType07()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FE
     * <br><br>
     * ���͒l�F(����) bean:�t�B�[���hList<Map<String, Object>> list2�ƁA����getter�����N���X�B<br>
     *         (����) name:"list2"<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) Class:Map.class<br>
     *
     * <br>
     * �p�����[�^�t�̌^���擾�ł��邱�Ƃ��m�F����e�X�g�B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testResolveCollectionType07() throws Exception {
        // �e�X�g���{
        Class actual = GenericPropertyUtil.resolveCollectionType(
                new GenericPropertyUtil_Stub01(),
                "list2");

        // ����
        assertEquals(Map.class, actual);
    }

    /**
     * testResolveCollectionType08()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FE
     * <br><br>
     * ���͒l�F(����) bean:�t�B�[���hList<String[]> list3�ƁA����getter�����N���X�B<br>
     *         (����) name:"list3"<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) Class:String[].class<br>
     *
     * <br>
     * �z��^���擾�ł��邱�Ƃ��m�F����e�X�g�B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testResolveCollectionType08() throws Exception {
        // �e�X�g���{
        Class actual = GenericPropertyUtil.resolveCollectionType(
                new GenericPropertyUtil_Stub01(),
                "list3");

        // ����
        assertEquals(String[].class, actual);
    }

    /**
     * testResolveCollectionType09()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) bean:�t�B�[���hint integer�ƁA����getter�����N���X�B<br>
     *         (����) name:"integer"<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalStateException<br>
     *
     * <br>
     * �w�肵���v���p�e�B���v���~�e�B�u�^�̏ꍇ�AIllegalStateException���X���[����邱�Ƃ��m�F����e�X�g�B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testResolveCollectionType09() throws Exception {
        try {
            // �e�X�g���{
            GenericPropertyUtil.resolveCollectionType(
                    new GenericPropertyUtil_Stub01(),
                    "integer");
            // ����
            fail("��O���X���[����܂���ł����B");
        } catch (IllegalStateException e) {
            assertEquals(IllegalStateException.class.getName(),
                    e.getClass().getName());
        }
    }

    /**
     * testResolveCollectionType10()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) bean:�t�B�[���hObject object�ƁA����getter�����N���X�B<br>
     *         (����) name:"object"<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalStateException<br>
     *
     * <br>
     * �w�肵���v���p�e�B��Collection�ȊO�̌^�̏ꍇ�AIllegalStateException���X���[����邱�Ƃ��m�F����e�X�g�B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testResolveCollectionType10() throws Exception {
        try {
            // �e�X�g���{
            GenericPropertyUtil.resolveCollectionType(
                    new GenericPropertyUtil_Stub01(),
                    "object");
            // ����
            fail("��O���X���[����܂���ł����B");
        } catch (IllegalStateException e) {
            assertEquals(IllegalStateException.class.getName(),
                    e.getClass().getName());
        }
    }

    /**
     * testResolveCollectionType11()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) bean:�t�B�[���hList<? extends String> list5�Ƃ���getter�����N���X�B<br>
     *         (����) name:"list5"<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) Class:Object.class<br>
     *
     * <br>
     * �^�p�����[�^�w�肪���C���h�J�[�h�̏ꍇ�AObject���ԋp����邱�Ƃ��m�F����e�X�g�B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testResolveCollectionType11() throws Exception {
        // �e�X�g���{
        Class actual = GenericPropertyUtil.resolveCollectionType(
                new GenericPropertyUtil_Stub01(),
                    "list5");
        // ����
        assertEquals(Object.class, actual);
    }

    /**
     * testResolveTypeObjectStringClassint01()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) bean:null<br>
     *         (����) index:0<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalArgumentException<br>
     *                    ���b�Z�[�W�F"Argument 'bean' ("<br>
     *                    + Object.class.getName() + " is null"<br>
     *
     * <br>
     * ����bean��null�̏ꍇ��IllegalArgumentException���X���[����邱�Ƃ��m�F����e�X�g�B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testResolveTypeObjectStringClassint01() throws Exception {
        try {
            // �e�X�g���{
            GenericPropertyUtil.resolveType(null, (String) null, null, 0);
            // ����
            fail("��O���X���[����܂���ł����B");
        } catch (IllegalArgumentException e) {
            String message = "Argument 'bean' ("
                + Object.class.getName() + " is null";
            assertEquals(message ,e.getMessage());
            assertEquals(IllegalArgumentException.class.getName(),
                    e.getClass().getName());
        }
    }

    /**
     * testResolveTypeObjectStringClassint02()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) bean:Object�C���X�^���X<br>
     *         (����) name:null<br>
     *         (����) index:0<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalArgumentException<br>
     *                    ���b�Z�[�W�F"Argument 'name' ("<br>
     *                    + String.class.getName() + " is empty"<br>
     *
     * <br>
     * ����name��null�̏ꍇ��IllegalArgumentException���X���[����邱�Ƃ��m�F����e�X�g�B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testResolveTypeObjectStringClassint02() throws Exception {
        try {
            // �e�X�g���{
            GenericPropertyUtil.resolveType(
                    new Object(), (String) null, null, 0);
            // ����
            fail("��O���X���[����܂���ł����B");
        } catch (IllegalArgumentException e) {
            String message = "Argument 'name' ("
                + String.class.getName() + " is empty";
            assertEquals(message ,e.getMessage());
            assertEquals(IllegalArgumentException.class.getName(),
                    e.getClass().getName());
        }
    }

    /**
     * testResolveTypeObjectStringClassint03()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) bean:Object�C���X�^���X<br>
     *         (����) name:""�i�󕶎��j<br>
     *         (����) index:0<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalArgumentException<br>
     *                    ���b�Z�[�W�F"Argument 'name' ("<br>
     *                    + String.class.getName() + " is empty"<br>
     *
     * <br>
     * ����name���󕶎��̏ꍇ��IllegalArgumentException���X���[����邱�Ƃ��m�F����e�X�g�B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testResolveTypeObjectStringClassint03() throws Exception {
        try {
            // �e�X�g���{
            GenericPropertyUtil.resolveType(
                    new Object(), "", null, 0);
            // ����
            fail("��O���X���[����܂���ł����B");
        } catch (IllegalArgumentException e) {
            String message = "Argument 'name' ("
                + String.class.getName() + " is empty";
            assertEquals(message ,e.getMessage());
            assertEquals(IllegalArgumentException.class.getName(),
                    e.getClass().getName());
        }
    }

    /**
     * testResolveTypeObjectStringClassint04()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) bean:Object�C���X�^���X<br>
     *         (����) name:"   "�i�󔒕�����j<br>
     *         (����) index:0<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalArgumentException<br>
     *                    ���b�Z�[�W�F"Argument 'name' ("<br>
     *                    + String.class.getName() + " is empty"<br>
     *
     * <br>
     * ����name���󔒕�����̏ꍇ��IllegalArgumentException���X���[����邱�Ƃ��m�F����e�X�g�B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testResolveTypeObjectStringClassint04() throws Exception {
        try {
            // �e�X�g���{
            GenericPropertyUtil.resolveType(
                    new Object(), "   ", null, 0);
            // ����
            fail("��O���X���[����܂���ł����B");
        } catch (IllegalArgumentException e) {
            String message = "Argument 'name' ("
                + String.class.getName() + " is empty";
            assertEquals(message ,e.getMessage());
            assertEquals(IllegalArgumentException.class.getName(),
                    e.getClass().getName());
        }
    }

    /**
     * testResolveTypeObjectStringClassint05()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) bean:�t�B�[���hMap<String, Object> map0�������A����getter�������Ȃ��N���X�B<br>
     *         (����) name:"map0"<br>
     *         (����) genericClass:Map.class<br>
     *         (����) index:0<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalArgumentException<br>
     *
     * <br>
     * �Ή����郁�\�b�h�����݂��Ȃ��ꍇ��IllegalArgumentException���X���[����邱�Ƃ��m�F����e�X�g�B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testResolveTypeObjectStringClassint05() throws Exception {
        try {
            // �e�X�g���{
            GenericPropertyUtil.resolveType(
                    new GenericPropertyUtil_Stub01(),
                    "map0", Map.class, 0);
            // ����
            fail("��O���X���[����܂���ł����B");
        } catch (IllegalArgumentException e) {
            assertEquals(IllegalArgumentException.class.getName(),
                    e.getClass().getName());
        }
    }

    /**
     * testResolveTypeObjectStringClassint06()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) bean:�t�B�[���hMap<String, Object> map1�Ƃ���getter�����N���X�B<br>
     *         (����) name:"map1"<br>
     *         (����) genericClass:Map.class<br>
     *         (����) index:-1<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalArgumentException<br>
     *
     * <br>
     * ����index�����̐��ł���ꍇ��IllegalArgumentException���X���[����邱�Ƃ��m�F����e�X�g�B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testResolveTypeObjectStringClassint06() throws Exception {
        try {
            // �e�X�g���{
            GenericPropertyUtil.resolveType(
                    new GenericPropertyUtil_Stub01(),
                    "map1", Map.class, -1);
            // ����
            fail("��O���X���[����܂���ł����B");
        } catch (IllegalArgumentException e) {
            assertEquals(IllegalArgumentException.class.getName(),
                    e.getClass().getName());
        }
    }

    /**
     * testResolveTypeObjectStringClassint07()
     * <br><br>
     *
     * �i����n�j
     * <br>
     * �ϓ_�FE
     * <br><br>
     * ���͒l�F(����) bean:�t�B�[���hMap<String, Object> map1�Ƃ���getter�����N���X�B<br>
     *         (����) name:"map1"<br>
     *         (����) genericClass:Map.class<br>
     *         (����) index:0<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) Class:String.class<br>
     *
     * <br>
     * ����index���^�p�����[�^���͈͓̔��ł���ꍇ�Ɍ^���擾�ł��邱�Ƃ��m�F����e�X�g�B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testResolveTypeObjectStringClassint07() throws Exception {

        // �e�X�g���{
        Class actual = GenericPropertyUtil.resolveType(
                new GenericPropertyUtil_Stub01(),
                "map1", Map.class, 0);

        // ����
        assertEquals(String.class, actual);
    }

    /**
     * testResolveTypeObjectStringClassint08()
     * <br><br>
     *
     * �i����n�j
     * <br>
     * �ϓ_�FE
     * <br><br>
     * ���͒l�F(����) bean:�t�B�[���hMap<String, Object> map1�Ƃ���getter�����N���X�B<br>
     *         (����) name:"map1"<br>
     *         (����) genericClass:Map.class<br>
     *         (����) index:1<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) Class:Object.class<br>
     *
     * <br>
     * ����index���^�p�����[�^���͈͓̔��ł���ꍇ�Ɍ^���擾�ł��邱�Ƃ��m�F����e�X�g�B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testResolveTypeObjectStringClassint08() throws Exception {
        // �e�X�g���{
        Class actual = GenericPropertyUtil.resolveType(
                new GenericPropertyUtil_Stub01(),
                "map1", Map.class, 0);

        // ����
        assertEquals(String.class, actual);
    }

    /**
     * testResolveTypeObjectStringClassint09()
     * <br><br>
     *
     * �i�ُ�n�j
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) bean:�t�B�[���hMap<String, Object> map1�Ƃ���getter�����N���X�B<br>
     *         (����) name:"map1"<br>
     *         (����) genericClass:Map.class<br>
     *         (����) index:2<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalArgumentException<br>
     *
     * <br>
     * ����index���^�p�����[�^���͈̔͊O�ł���ꍇ��IllegalArgumentException���X���[����邱�Ƃ��m�F����e�X�g�B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testResolveTypeObjectStringClassint09() throws Exception {
        try {
            // �e�X�g���{
            GenericPropertyUtil.resolveType(
                    new GenericPropertyUtil_Stub01(),
                    "map1", Map.class, 2);
            // ����
            fail("��O���X���[����܂���ł����B");
        } catch (IllegalArgumentException e) {
            assertEquals(IllegalArgumentException.class.getName(),
                    e.getClass().getName());
        }
    }

    /**
     * testResolveTypeObjectStringClassint10()
     * <br><br>
     *
     * �i�ُ�n�j
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) bean:�t�B�[���hMap<String, Object> map1�Ƃ���getter�����N���X�B<br>
     *         (����) name:"map1"<br>
     *         (����) genericClass:null<br>
     *         (����) index:1<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalArgumentException<br>
     *
     * <br>
     * ����genericClass��null�ł���ꍇ��IllegalArgumentException���X���[����邱�Ƃ��m�F����e�X�g�B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testResolveTypeObjectStringClassint10() throws Exception {
        try {
            // �e�X�g���{
            GenericPropertyUtil.resolveType(
                    new GenericPropertyUtil_Stub01(),
                    "map1", null, 1);
            // ����
            fail("��O���X���[����܂���ł����B");
        } catch (IllegalArgumentException e) {
            assertEquals(IllegalArgumentException.class.getName(),
                    e.getClass().getName());
        }
    }

    /**
     * testResolveTypeObjectStringClassint11()
     * <br><br>
     *
     * �i����n�j
     * <br>
     * �ϓ_�FE
     * <br><br>
     * ���͒l�F(����) bean:�t�B�[���hMap<String[], List<String>> map2�Ƃ���getter�����N���X�B<br>
     *         (����) name:"map2"<br>
     *         (����) genericClass:Map.class<br>
     *         (����) index:0<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) Class:String[].class<br>
     *
     * <br>
     * �z��^���擾�ł��邱�Ƃ��m�F����e�X�g�B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testResolveTypeObjectStringClassint11() throws Exception {

        // �e�X�g���{
        Class actual = GenericPropertyUtil.resolveType(
                new GenericPropertyUtil_Stub01(),
                "map2", Map.class, 0);

        // ����
        assertEquals(String[].class, actual);
    }

    /**
     * testResolveTypeObjectStringClassint12()
     * <br><br>
     *
     * �i����n�j
     * <br>
     * �ϓ_�FE
     * <br><br>
     * ���͒l�F(����) bean:�t�B�[���hMap<String[], List<String>> map2�Ƃ���getter�����N���X�B<br>
     *         (����) name:"map2"<br>
     *         (����) genericClass:Map.class<br>
     *         (����) index:1<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) Class:List.class<br>
     *
     * <br>
     * �p�����[�^�t�̌^���擾�ł��邱�Ƃ��m�F����e�X�g�B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testResolveTypeObjectStringClassint12() throws Exception {
        // �e�X�g���{
        Class actual = GenericPropertyUtil.resolveType(
                new GenericPropertyUtil_Stub01(),
                "map2", Map.class, 1);

        // ����
        assertEquals(List.class, actual);
    }

    /**
     * testResolveTypeObjectStringClassint13()
     * <br><br>
     *
     * �i�ُ�n�j
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) bean:�t�B�[���hMap<String[], List<String>> map2�Ƃ���getter�����N���X�B<br>
     *         (����) name:"map2"<br>
     *         (����) genericClass:String.class<br>
     *         (����) index:1<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalStateException<br>
     *
     * <br>
     * genericClass���t�B�[���h�̌^�ƍ��v���Ȃ��ꍇ�A
     * IllegalStateException���X���[����邱�Ƃ��m�F����e�X�g�B�B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testResolveTypeObjectStringClassint13() throws Exception {
        try {
            // �e�X�g���{
            GenericPropertyUtil.resolveType(new GenericPropertyUtil_Stub01(),
                    "map2", String.class, 1);
            // ����
            fail("��O���X���[����܂���ł����B");
        } catch (IllegalStateException e) {
            assertEquals(IllegalStateException.class.getName(),
                    e.getClass().getName());
        }
    }

    /**
     * testResolveTypeObjectStringClassint14()
     * <br><br>
     *
     * �i�ُ�n�j
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) bean:�t�B�[���h��int integer�Ƃ���getter�����N���X�B<br>
     *         (����) name:"integer"<br>
     *         (����) genericClass:int.class<br>
     *         (����) index:1<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalStateException<br>
     *                            ���b�Z�[�W�F"No parameterizedType was detected."<br>
     *
     * <br>
     * �w�肵���v���p�e�B���v���~�e�B�u�^�̏ꍇ�A
     * IllegalStateException���X���[����邱�Ƃ��m�F����e�X�g�B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testResolveTypeObjectStringClassint14() throws Exception {
        try {
            // �e�X�g���{
            GenericPropertyUtil.resolveType(
                    new GenericPropertyUtil_Stub01(),
                    "integer", int.class, 1);
            // ����
            fail("��O���X���[����܂���ł����B");
        } catch (IllegalStateException e) {
            assertEquals("No parameterizedType was detected.",
                    e.getMessage());
            assertEquals(IllegalStateException.class.getName(),
                    e.getClass().getName());
        }
    }

    /**
     * testResolveTypeObjectStringClassint15()
     * <br><br>
     *
     * �i�ُ�n�j
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) bean:�t�B�[���h��Object object�Ƃ���getter�����N���X�B<br>
     *         (����) name:"object"<br>
     *         (����) genericClass:Object.class<br>
     *         (����) index:1<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalStateException<br>
     *                            ���b�Z�[�W�F"No parameterizedType was detected."<br>
     *
     * <br>
     * �w�肵���v���p�e�B���I�u�W�F�N�g�^�̏ꍇ�A
     * IllegalStateException���X���[����邱�Ƃ��m�F����e�X�g�B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testResolveTypeObjectStringClassint15() throws Exception {
        try {
            // �e�X�g���{
            GenericPropertyUtil.resolveType(
                    new GenericPropertyUtil_Stub01(),
                    "object", Object.class, 1);
            // ����
            fail("��O���X���[����܂���ł����B");
        } catch (IllegalStateException e) {
            assertEquals("No parameterizedType was detected.",
                    e.getMessage());
            assertEquals(IllegalStateException.class.getName(),
                    e.getClass().getName());
        }
    }

    /**
     * testResolveTypeObjectStringClassint16()
     * <br><br>
     *
     * �i����n�j
     * <br>
     * �ϓ_�FE
     * <br><br>
     * ���͒l�F(����) bean:�t�B�[���hMap<?, ?> map3�Ƃ���getter�����N���X�B<br>
     *         (����) name:"map3"<br>
     *         (����) genericClass:Map.class<br>
     *         (����) index:1<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) Class:Object.class<br>
     *
     * <br>
     * �^�p�����[�^�w�肪���C���h�J�[�h�̏ꍇ�AObject���ԋp����邱�Ƃ��m�F����e�X�g�B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testResolveTypeObjectStringClassint16() throws Exception {
        // �e�X�g���{
        Class actual = GenericPropertyUtil.resolveType(
                new GenericPropertyUtil_Stub01(),
                "map3", Map.class, 0);

        // ����
        assertEquals(Object.class, actual);
    }

    /**
     * testGetMethod01()
     * <br><br>
     *
     * �i�ُ�n�j
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) bean:�t�B�[���hString string�Agetter, setter�������Ȃ��N���X�B<br>
     *         (����) name:"string"<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalArgumentException<br>
     *                    ���b�Z�[�W�Fbean�̊��S�C���N���X�� + " has no getter for property string"<br>
     *
     * <br>
     * �Ή�����t�B�[���h�����݂��Ȃ��ꍇ��IllegalArgumentException���X���[����邱�Ƃ��m�F����e�X�g�B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetMethod01() throws Exception {
        try {
            // �e�X�g���{
            GenericPropertyUtil.getMethod(
                    new GenericPropertyUtil_Stub02(), "string");
            // ����
            fail("��O���X���[����܂���ł����B");
        } catch (IllegalArgumentException e) {
            String message = GenericPropertyUtil_Stub02.class.getName()
                + " has no getter for property string";
            assertEquals(message, e.getMessage());
            assertEquals(IllegalArgumentException.class.getName(),
                    e.getClass().getName());
        }
    }

    /**
     * testGetMethod02()
     * <br><br>
     *
     * �i�ُ�n�j
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) bean:�t�B�[���hString string1�������A����getter�������Ȃ��N���X�B<br>
     *         (����) name:"string1"<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalArgumentException<br>
     *                    ���b�Z�[�W�Fbean�̊��S�C���N���X�� + " has no getter for property string1"<br>
     *
     * <br>
     * �Ή����郁�\�b�h�����݂��Ȃ��ꍇ��IllegalArgumentException���X���[����邱�Ƃ��m�F����e�X�g�B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetMethod02() throws Exception {
        try {
            // �e�X�g���{
            GenericPropertyUtil.getMethod(
                    new GenericPropertyUtil_Stub02(), "string1");
            // ����
            fail("��O���X���[����܂���ł����B");
        } catch (IllegalArgumentException e) {
            String message = GenericPropertyUtil_Stub02.class.getName()
                + " has no getter for property string1";
            assertEquals(message, e.getMessage());
            assertEquals(IllegalArgumentException.class.getName(),
                    e.getClass().getName());
        }
    }

    /**
     * testGetMethod03()
     * <br><br>
     *
     * �i����n�j
     * <br>
     * �ϓ_�FE
     * <br><br>
     * ���͒l�F(����) bean:�t�B�[���hString string2�Ƃ���getter�����N���X�B<br>
     *         (����) name:"string2"<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) Method:bean��getString2���\�b�h<br>
     *
     * <br>
     * �Ή����郁�\�b�h���ԋp����邱�Ƃ��m�F����e�X�g�B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetMethod03() throws Exception {
        // �e�X�g���{
        Method actual = GenericPropertyUtil.getMethod(
                new GenericPropertyUtil_Stub02(), "string2");

        // ����
        Method expected =
            GenericPropertyUtil_Stub02.class.getDeclaredMethod(
                    "getString2", new Class[0]);
        assertEquals(expected, actual);
    }

    /**
     * testGetMethod04()
     * <br><br>
     *
     * �i�ُ�n�j
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) bean:�t�B�[���hbean1�������A����getter�������Ȃ��N���X�B<br>
     *         (����) name:"bean1.string"<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalArgumentException<br>
     *                    ���b�Z�[�W�F"Failed to detect getter for "<br>
     *                    + bean�̊��S�C���N���X�� + "#bean1.string"<br>
     *                    ���b�v���ꂽ��O�FNoSuchMethodException<br>
     *
     * <br>
     * PropertyUtils#getPropertyDescriptor��NoSuchMethodException���X���[�����ꍇ�̃e�X�g�B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetMethod04() throws Exception {
        try {
            // �e�X�g���{
            GenericPropertyUtil.getMethod(
                    new GenericPropertyUtil_Stub02(), "bean1.string");
            // ����
            fail("��O���X���[����܂���ł����B");
        } catch (IllegalArgumentException e) {
            String message = "Failed to detect getter for "
                + GenericPropertyUtil_Stub02.class.getName() + "#bean1.string";
            assertEquals(message, e.getMessage());
            assertEquals(IllegalArgumentException.class.getName(),
                    e.getClass().getName());
            assertTrue(e.getCause() instanceof NoSuchMethodException);
        }
    }

    /**
     * testGetMethod05()
     * <br><br>
     *
     * �i�ُ�n�j
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) bean:�t�B�[���hbean2�Ƃ���getter�����N���X�B<br>
     *                (getter�ł�RuntimeException���X���[�����)<br>
     *         (����) name:"bean2.string"<br>
     *         (���) PropertyUtils#getPropertyDescriptor�̎��s����:
     *                  InvocationTargetException���X���[
     *                    ��JavaBean��getter��RuntimeException���X���[<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalArgumentException<br>
     *                    ���b�Z�[�W�F"Failed to detect getter for "<br>
     *                    + bean�̊��S�C���N���X�� + "#bean2.string"<br>
     *                    ���b�v���ꂽ��O�FInvocationTargetException<br>
     *
     * <br>
     * PropertyUtils#getPropertyDescriptor��InvocationTargetException���X���[�����ꍇ�̃e�X�g�B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetMethod05() throws Exception {
        try {
            // �e�X�g���{
            GenericPropertyUtil.getMethod(
                    new GenericPropertyUtil_Stub02(), "bean2.string");
            // ����
            fail("��O���X���[����܂���ł����B");
        } catch (IllegalArgumentException e) {
            String message = "Failed to detect getter for "
                + GenericPropertyUtil_Stub02.class.getName() + "#bean2.string";
            assertEquals(message, e.getMessage());
            assertEquals(IllegalArgumentException.class.getName(),
                    e.getClass().getName());
            assertTrue(e.getCause() instanceof InvocationTargetException);
        }
    }

    /**
     * testGetMethod06()
     * <br><br>
     *
     * �i�ُ�n�j
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) bean:�t�B�[���hString string2�Ƃ���getter�����N���X�B<br>
     *         (����) name:"string2"<br>
     *         (���) PropertyUtils#getPropertyDescriptor�̎��s����:
     *                  IllegalAccessException���X���[
     *                    ��PropertyUtilsBean�̃X�^�u��IllegalAccessException���X���[<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalArgumentException<br>
     *                    ���b�Z�[�W�F"Failed to detect getter for "<br>
     *                    + bean�̊��S�C���N���X�� + "#string2"<br>
     *                    ���b�v���ꂽ��O�FIllegalAccessException<br>
     *
     * <br>
     * PropertyUtils#getPropertyDescriptor��IllegalAccessException���X���[�����ꍇ�̃e�X�g�B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetMethod06() throws Exception {
        // �O����
        BeanUtilsBean beanUtilsBean = BeanUtilsBean.getInstance();
        UTUtil.setPrivateField(beanUtilsBean, "propertyUtilsBean",
                new GenericPropertyUtil_PropertyUtilsBeanStub01());
        try {
            
            // �e�X�g���{
            GenericPropertyUtil.getMethod(
                    new GenericPropertyUtil_Stub02(), "string2");
            // ����
            fail("��O���X���[����܂���ł����B");
        } catch (IllegalArgumentException e) {
            String message = "Failed to detect getter for "
                + GenericPropertyUtil_Stub02.class.getName() + "#string2";
            assertEquals(message, e.getMessage());
            assertEquals(IllegalArgumentException.class.getName(),
                    e.getClass().getName());
            assertTrue(e.getCause() instanceof IllegalAccessException);
        }
    }

    /**
     * testResolveTypeClassClassTypeint01()
     * <br><br>
     *
     * �i�ُ�n�j
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) genericClass:null<br>
     *         (����) index:0<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalArgumentException<br>
     *                    ���b�Z�[�W�F"Argument 'genericsClass' ("<br>
     *                    + Class.class.getName() + ") is null"<br>
     *
     * <br>
     * ����genericClass��null�̏ꍇ��IllegalArgumentException���X���[����邱�Ƃ��m�F����e�X�g�B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testResolveTypeClassClassTypeint01() throws Exception {
        try {
            // �e�X�g���{
            GenericPropertyUtil.resolveType((Class) null, (Class) null, null, 0);
            // ����
            fail("��O���X���[����܂���ł����B");
        } catch (IllegalArgumentException e) {
            String message = "Argument 'genericsClass' ("
                    + Class.class.getName() + ") is null";
            assertEquals(message ,e.getMessage());
            assertEquals(IllegalArgumentException.class.getName(),
                    e.getClass().getName());
        }
    }

    /**
     * testResolveTypeClassClassTypeint02()
     * <br><br>
     *
     * �i�ُ�n�j
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) genericClass:List.class<br>
     *         (����) clazz:null<br>
     *         (����) index:0<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalStateException<br>
     *                    ���b�Z�[�W�FgenericClass+ " is not assignable from " + clazz<br>
     *
     * <br>
     * ����clazz��null�̏ꍇ��IllegalStateException���X���[����邱�Ƃ��m�F����e�X�g�B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testResolveTypeClassClassTypeint02() throws Exception {
        try {
            // �e�X�g���{
            GenericPropertyUtil.resolveType(List.class, (Class) null, null, 0);
            // ����
            fail("��O���X���[����܂���ł����B");
        } catch (IllegalStateException e) {
            String message = List.class + " is not assignable from "
                + "null";
            assertEquals(message ,e.getMessage());
            assertEquals(IllegalStateException.class.getName(),
                    e.getClass().getName());
        }
    }

    /**
     * testResolveTypeClassClassTypeint03()
     * <br><br>
     *
     * �i�ُ�n�j
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) genericClass:List.class<br>
     *         (����) clazz:Object.class<br>
     *         (����) index:0<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalStateException<br>
     *                    ���b�Z�[�W�FgenericClass+ " is not assignable from " + clazz<br>
     *
     * <br>
     * ����clazz��genericClass�̃T�u�N���X�ł͂Ȃ��ꍇ��IllegalStateException���X���[����邱�Ƃ��m�F����e�X�g�B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testResolveTypeClassClassTypeint03() throws Exception {
        try {
            // �e�X�g���{
            GenericPropertyUtil.resolveType(List.class, Object.class, null, 0);
            // ����
            fail("��O���X���[����܂���ł����B");
        } catch (IllegalStateException e) {
            String message = List.class + " is not assignable from "
                + Object.class;
            assertEquals(message ,e.getMessage());
            assertEquals(IllegalStateException.class.getName(),
                    e.getClass().getName());
        }
    }

    /**
     * testResolveTypeClassClassTypeint04()
     * <br><br>
     *
     * �i�ُ�n�j
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) genericClass:List.class<br>
     *         (����) clazz:ArrayList�p���N���X(�p�����[�^��String���w��)<br>
     *         (����) type:null<br>
     *         (����) index:-1<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalArgumentException<br>
     *                    ���b�Z�[�W�F"Argument 'index'(-1) is out of bounds of"<br>
     *                    + " generics parameters")<br>
     *
     * <br>
     * ����index�����̐��ł���ꍇ��IllegalArgumentException���X���[����邱�Ƃ��m�F����e�X�g�B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testResolveTypeClassClassTypeint04() throws Exception {
        try {
            // �e�X�g���{
            GenericPropertyUtil.resolveType(List.class,
                    GenericPropertyUtil_ArrayListStub01.class, null, -1);
            // ����
            fail("��O���X���[����܂���ł����B");
        } catch (IllegalArgumentException e) {
            String message = "Argument 'index'(-1) is out of bounds of"
                + " generics parameters";
            assertEquals(message ,e.getMessage());
            assertEquals(IllegalArgumentException.class.getName(),
                    e.getClass().getName());
        }
    }

    /**
     * testResolveTypeClassClassTypeint05()
     * <br><br>
     *
     * �i�ُ�n�j
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) genericClass:List.class<br>
     *         (����) clazz:ArrayList�p���N���X(�p�����[�^��String���w��)<br>
     *         (����) type:null<br>
     *         (����) index:0<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) Class:String.class<br>
     *
     * <br>
     * ����index���^�p�����[�^���͈͓̔��ł���ꍇ�Ɍ^���擾�ł��邱�Ƃ��m�F����e�X�g�B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testResolveTypeClassClassTypeint05() throws Exception {
        // �e�X�g���{
        Class actual = GenericPropertyUtil.resolveType(List.class,
                GenericPropertyUtil_ArrayListStub01.class, null, 0);
        // ����
        assertEquals(String.class, actual);
    }

    /**
     * testResolveTypeClassClassTypeint06()
     * <br><br>
     *
     * �i�ُ�n�j
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) genericClass:List.class<br>
     *         (����) clazz:ArrayList�p���N���X(�p�����[�^��String���w��)<br>
     *         (����) type:null<br>
     *         (����) index:1<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalArgumentException<br>
     *                    ���b�Z�[�W�F"Argument 'index'(1) is out of bounds of"<br>
     *                    + " generics parameters")<br>
     *
     * <br>
     * ����index���^�p�����[�^���͈̔͊O�ł���ꍇ��IllegalArgumentException���X���[����邱�Ƃ��m�F����e�X�g�B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testResolveTypeClassClassTypeint06() throws Exception {
        try {
            // �e�X�g���{
            GenericPropertyUtil.resolveType(List.class,
                    GenericPropertyUtil_ArrayListStub01.class, null, 1);
            // ����
            fail("��O���X���[����܂���ł����B");
        } catch (IllegalArgumentException e) {
            String message = "Argument 'index'(1) is out of bounds of"
                + " generics parameters";
            assertEquals(message ,e.getMessage());
            assertEquals(IllegalArgumentException.class.getName(),
                    e.getClass().getName());
        }
    }

    /**
     * testResolveTypeClassClassTypeint07()
     * <br><br>
     *
     * �i����n�j
     * <br>
     * �ϓ_�FE
     * <br><br>
     * ���͒l�F(����) genericClass:List.class<br>
     *         (����) clazz:ArrayList.class<br>
     *         (����) type:List.class<br>
     *         (����) index:0<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) Class:Object.class<br>
     *            (��ԕω�) ���O�o��:<br>
     *                    ���O���x���FTRACE
     *                    ���b�Z�[�W�F"Concrete type of Type(E) was not
     *                    found in ancestorList([java.util.AbstractList<E>, java.util.List<E>])"<br>
     *
     * <br>
     * �^������ł��Ȃ��ꍇ��Object���ԋp����邱�Ƃ��m�F����e�X�g�B
     * �i�o�H�m�F�̂��߂ɓ��ʂɃg���[�X���O���`�F�b�N����j
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testResolveTypeClassClassTypeint07() throws Exception {
        // �e�X�g���{
        Class actual = GenericPropertyUtil.resolveType(List.class,
                ArrayList.class, List.class, 0);
        // ����
        assertEquals(Object.class, actual);
        assertTrue(LogUTUtil.checkTrace(
                "Concrete type of Type(E) was "
                + "not found in ancestorList([java.util.AbstractList<E>, "
                + "java.util.List<E>])"));
    }

    /**
     * testResolveTypeClassClassTypeint08()
     * <br><br>
     *
     * �i����n�j
     * <br>
     * �ϓ_�FE
     * <br><br>
     * ���͒l�F(����) genericClass:List.class<br>
     *         (����) clazz:ArrayList.class<br>
     *         (����) type:List<String>��\��ParameterizedType<br>
     *         (����) index:0<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) Class:String.class<br>
     *            (��ԕω�) ���O�o��:<br>
     *                    ���O���x���FTRACE
     *                    ���b�Z�[�W�F"Argument 'genericClass'(java.util.List)
     *                    does not declare type parameter"<br>
     *
     * <br>
     * type�Ō^�����肳���ꍇ�ɁA�^�p�����[�^���ԋp����邱�Ƃ��m�F����e�X�g�B
     * �i�o�H�m�F�̂��߂ɓ��ʂɃg���[�X���O���`�F�b�N����j
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testResolveTypeClassClassTypeint08() throws Exception {
        //  �O����
        Method method = GenericPropertyUtil_Stub01.class.getMethod(
                "getList1", new Class[0]);
        Type type = method.getGenericReturnType();

        // �e�X�g���{
        Class actual = GenericPropertyUtil.resolveType(List.class,
                List.class, type, 0);
        // ����
        assertEquals(String.class, actual);
        LogUTUtil.checkTrace("",
                new IllegalStateException("Argument "
                    + "'genericClass'(java.util.List) does not "
                    + "declare type parameter"));
    }
}
