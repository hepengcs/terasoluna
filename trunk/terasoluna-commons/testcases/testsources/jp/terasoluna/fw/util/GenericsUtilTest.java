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

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

import junit.framework.TestCase;

/**
 * {@link jp.terasoluna.fw.util.GenericsUtil} �N���X�̃e�X�g�B
 *
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4>
 * <code>Generics</code>���������߂̃��[�e�B���e�B�N���X�B
 * <p>
 *
 * @see jp.terasoluna.fw.client.util.GenericsUtil
 */
public class GenericsUtilTest extends TestCase {

    /**
     * ���̃e�X�g�P�[�X�����s����ׂ�
     * GUI �A�v���P�[�V�������N������B
     *
     * @param args java �R�}���h�ɐݒ肳�ꂽ�p�����[�^
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(GenericsUtilTest.class);
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
    public GenericsUtilTest(String name) {
        super(name);
    }

    /**
     * testResolveParameterizedClassClassClass01()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) genericClass:null<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalArgumentException<br>
     *                    ���b�Z�[�W�F"Argument 'genericsClass' ("<br>
     *                    + Class.class.getName() + ") is null"<br>
     *
     * <br>
     * ����genericClass��null�̏ꍇ��IllegalArgumentException��
     * �X���[����邱�Ƃ��m�F����e�X�g�B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testResolveParameterizedClassClassClass01() throws Exception {

        try {
            //  �e�X�g���{
            GenericsUtil.resolveParameterizedClass(null, null);
            fail("��O���X���[����܂���ł����B");
        } catch (IllegalArgumentException e) {
            //  ����
            assertEquals("Argument 'genericsClass' ("
                    + Class.class.getName() + ") is null",
                    e.getMessage());
            assertEquals(IllegalArgumentException.class.getName(),
                    e.getClass().getName());
        }
    }

    /**
     * testResolveParameterizedClassClassClass02()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) genericClass:Object��Class�C���X�^���X<br>
     *         (����) descendantClass:null<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalArgumentException<br>
     *                    ���b�Z�[�W�F"Argument 'descendantClass'("<br>
     *                    + Class.class.getName() + ") is null"<br>
     *
     * <br>
     * ����descendantClass��null�̏ꍇ��IllegalArgumentException��
     * �X���[����邱�Ƃ��m�F����e�X�g�B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testResolveParameterizedClassClassClass02() throws Exception {
        try {
            //  �e�X�g���{
            GenericsUtil.resolveParameterizedClass(Object.class, null);
            fail("��O���X���[����܂���ł����B");
        } catch (IllegalArgumentException e) {
            //  ����
            assertEquals("Argument 'descendantClass'("
                    + Class.class.getName() + ") is null",
                    e.getMessage());
            assertEquals(IllegalArgumentException.class.getName(),
                    e.getClass().getName());
        }
    }

    /**
     * testResolveParameterizedClassClassClass03()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) genericClass:�^�p�����[�^�錾�������Ȃ��e�N���X<br>
     *         (����) descendantClass:genericClass�̎q�N���X<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalStateException<br>
     *
     * <br>
     * ����genericClass�^�p�����[�^�錾�������Ȃ��e�N���X�ł���ꍇ�ɁA
     * IllegalStateException���X���[����邱�Ƃ��m�F����e�X�g�B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testResolveParameterizedClassClassClass03() throws Exception {
        try {
            //  �e�X�g���{
            GenericsUtil.resolveParameterizedClass(Object.class, String.class);
            fail("��O���X���[����܂���ł����B");
        } catch (IllegalStateException e) {
            //  ����
            assertEquals(IllegalStateException.class.getName(),
                    e.getClass().getName());

        }
    }

    /**
     * testResolveParameterizedClassClassClass04()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) genericClass:�^�p�����[�^�錾��1���e�N���X<br>
     *         (����) descendantClass:genericClass�̎q�N���X
     *                  (�^�p�����[�^�̋�̃N���X���w�肵�Ă��Ȃ�)<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalStateException<br>
     *
     * <br>
     * �^�p�����[�^�ɋ�̃N���X���w�肳��Ă��Ȃ��ꍇ�ɁA
     * IllegalStateException���X���[����邱�Ƃ��m�F����e�X�g�B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testResolveParameterizedClassClassClass04() throws Exception {
        try {
            //  �e�X�g���{
            GenericsUtil.resolveParameterizedClass(
                    FutureTask.class, GenericsUtil_FutureTaskStub01.class);
            fail("��O���X���[����܂���ł����B");
        } catch (IllegalStateException e) {
            //  ����
            assertEquals(IllegalStateException.class.getName(),
                    e.getClass().getName());
        }
    }

    /**
     * testResolveParameterizedClassClassClass05()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FE
     * <br><br>
     * ���͒l�F(����) genericClass:�^�p�����[�^�錾��1���e�N���X<br>
     *         (����) descendantClass:genericClass�̎q�N���X
     *                  (�^�p�����[�^�̋�̃N���X��String���w��)<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) Class[]:�v�f0 = String.class<br>
     *
     * <br>
     * descendantClass���q�N���X�ł���ꍇ�ɁA
     * �^�p�����[�^�̋�̃N���X���擾�ł��邱�Ƃ��m�F����e�X�g�B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testResolveParameterizedClassClassClass05() throws Exception {
        //  �e�X�g���{
        Class[] result = GenericsUtil.resolveParameterizedClass(
                FutureTask.class, GenericsUtil_FutureTaskStub02.class);

        //  ����
        assertNotNull(result);
        assertEquals(1, result.length);
        assertEquals(String.class, result[0]);
    }

    /**
     * testResolveParameterizedClassClassClass06()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FE
     * <br><br>
     * ���͒l�F(����) genericClass:�^�p�����[�^�錾��1���e�N���X<br>
     *         (����) descendantClass:genericClass�̑��N���X
     *                  (�^�p�����[�^�̋�̃N���X��Map<String, Object>���w��)<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) Class[]:�v�f0 = Map.class<br>
     *
     * <br>
     * descendantClass�����N���X�ł���ꍇ�ɁA�^�p�����[�^�̋�̃N���X��
     * �擾�ł��邱�Ƃ��m�F����e�X�g�B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testResolveParameterizedClassClassClass06() throws Exception {
        //  �e�X�g���{
        Class[] result = GenericsUtil.resolveParameterizedClass(
                FutureTask.class, GenericsUtil_FutureTaskStub03.class);

        //  ����
        assertNotNull(result);
        assertEquals(1, result.length);
        assertEquals(Map.class, result[0]);
    }

    /**
     * testResolveParameterizedClassClassClass07()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FE
     * <br><br>
     * ���͒l�F(����) genericClass:�^�p�����[�^�錾��3���e�N���X<br>
     *         (����) descendantClass:genericClass�̎q�N���X
     *                  (�^�p�����[�^�̋�̃N���X��String[], Integer, Boolean���w��)<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) Class[]:�v�f0 = String[].class<br>
     *                  �v�f1 = Integer.class<br>
     *                  �v�f2 = Boolean.class<br>
     *
     * <br>
     * �^�p�����[�^�������̏ꍇ�ɁA�^�p�����[�^�̋�̃N���X���擾�ł��邱�Ƃ��m�F����e�X�g�B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testResolveParameterizedClassClassClass07() throws Exception {
        //  �e�X�g���{
        Class[] result = GenericsUtil.resolveParameterizedClass(
                GenericsUtil_Stub01.class, GenericsUtil_Stub02.class);

        //  ����
        assertNotNull(result);
        assertEquals(3, result.length);
        assertEquals(String[].class, result[0]);
        assertEquals(Integer.class, result[1]);
        assertEquals(Boolean.class, result[2]);
    }

    /**
     * testResolveParameterizedClassClassClass08()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FE
     * <br><br>
     * ���͒l�F(����) genericClass:�^�p�����[�^�錾��3���e�C���^�t�F�[�X<br>
     *         (����) descendantClass:genericClass�̎����N���X
     *              (�^�p�����[�^�̋�̃N���X��String[], Integer, Boolean���w��)<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) Class[]:�v�f0 = String[].class<br>
     *                  �v�f1 = Integer.class<br>
     *                  �v�f2 = Boolean.class<br>
     *
     * <br>
     * �^�p�����[�^�錾���C���^�t�F�[�X�ōs���Ă���ꍇ�ɁA�^�p�����[�^�̋�̃N���X���擾�ł��邱�Ƃ��m�F����e�X�g�B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testResolveParameterizedClassClassClass08() throws Exception {
        //  �e�X�g���{
        Class[] result = GenericsUtil.resolveParameterizedClass(
                GenericsUtil_Stub03.class, GenericsUtil_Stub04.class);

        //  ����
        assertNotNull(result);
        assertEquals(3, result.length);
        assertEquals(String[].class, result[0]);
        assertEquals(Integer.class, result[1]);
        assertEquals(Boolean.class, result[2]);
    }

    /**
     * testResolveParameterizedClassClassClass09()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FE
     * <br><br>
     * ���͒l�F(����) genericClass:�^�p�����[�^�錾��3���e�C���^�t�F�[�X<br>
     *         (����) descendantClass:genericClass�̌p���C���^�t�F�[�X�̎����N���X
     *              (�^�p�����[�^�̋�̃N���X��String[], Integer, Boolean���w��)<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) Class[]:�v�f0 = String[].class<br>
     *                  �v�f1 = Integer.class<br>
     *                  �v�f2 = Boolean.class<br>
     *
     * <br>
     * �^�p�����[�^�錾���C���^�t�F�[�X�ōs���Ă���A�C���^�t�F�[�X���p������Ă���ꍇ�ɁA
     * �^�p�����[�^�̋�̃N���X���擾�ł��邱�Ƃ��m�F����e�X�g�B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testResolveParameterizedClassClassClass09() throws Exception {
        //  �e�X�g���{
        Class[] result = GenericsUtil.resolveParameterizedClass(
                GenericsUtil_Stub03.class, GenericsUtil_Stub06.class);

        //  ����
        assertNotNull(result);
        assertEquals(3, result.length);
        assertEquals(String[].class, result[0]);
        assertEquals(Integer.class, result[1]);
        assertEquals(Boolean.class, result[2]);
    }

    /**
     * testResolveParameterizedClassClassClass10()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FE
     * <br><br>
     * ���͒l�F(����) genericClass:�^�p�����[�^�錾��3���e�C���^�t�F�[�X<br>
     *         (����) descendantClass:genericClass�̌p���C���^�t�F�[�X�̎����N���X�̃T�u�N���X
     *              (�^�p�����[�^�͎����N���X�Ŏw��)
     *              (�^�p�����[�^�̋�̃N���X��String[], Integer, Boolean���w��)<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) Class[]:�v�f0 = String[].class<br>
     *                  �v�f1 = Integer.class<br>
     *                  �v�f2 = Boolean.class<br>
     *
     * <br>
     * �^�p�����[�^�錾���C���^�t�F�[�X�ōs���Ă���A�C���^�t�F�[�X���p������A
     * ���A�����N���X���p������Ă���ꍇ�ɁA
     * �^�p�����[�^�̋�̃N���X���擾�ł��邱�Ƃ��m�F����e�X�g�B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testResolveParameterizedClassClassClass10() throws Exception {
        //  �e�X�g���{
        Class[] result = GenericsUtil.resolveParameterizedClass(
                GenericsUtil_Stub03.class, GenericsUtil_Stub07.class);

        //  ����
        assertNotNull(result);
        assertEquals(3, result.length);
        assertEquals(String[].class, result[0]);
        assertEquals(Integer.class, result[1]);
        assertEquals(Boolean.class, result[2]);
    }

    /**
     * testResolveParameterizedClassClassClass11()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FE
     * <br><br>
     * ���͒l�F(����) genericClass:�^�p�����[�^�錾��3���e�C���^�t�F�[�X<br>
     *         (����) descendantClass:genericClass�̌p���C���^�t�F�[�X�̎����N���X�̃T�u�N���X
     *              (�^�p�����[�^�̓T�u�N���X�Ŏw��)
     *              (�^�p�����[�^�̋�̃N���X��String[], Integer, Boolean���w��)<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) Class[]:
     *                  �v�f0 = Boolean.class<br>
     *                  �v�f1 = String[].class<br>
     *                  �v�f2 = Integer.class<br>
     *
     * <br>
     * �^�p�����[�^�錾���C���^�t�F�[�X�ōs���Ă���A�C���^�t�F�[�X���p������A
     * ���A�����N���X���p������Ă���ꍇ�ɁA
     * �^�p�����[�^�̋�̃N���X���擾�ł��邱�Ƃ��m�F����e�X�g�B
     * �^�p�����[�^�̐錾��������ւ����Ă����ꍇ�ł��������擾�ł��邱�Ƃ�
     * �e�X�g���܂���B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testResolveParameterizedClassClassClass11() throws Exception {
        //  �e�X�g���{
        Class[] result = GenericsUtil.resolveParameterizedClass(
                GenericsUtil_Stub03.class, GenericsUtil_Stub09.class);

        //  ����
        assertNotNull(result);
        assertEquals(3, result.length);
        assertEquals(Boolean.class, result[0]);
        assertEquals(String[].class, result[1]);
        assertEquals(Integer.class, result[2]);
    }

    /**
     * testResolveParameterizedClassClassClassint01()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) genericClass:null<br>
     *         (����) descendantClass:null<br>
     *         (����) index:0<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalArgumentException<br>
     *                    ���b�Z�[�W�F"Argument 'genericsClass' ("<br>
     *                    + Class.class.getName() + ") is null"<br>
     *
     * <br>
     * ����genericClass��null�̏ꍇ��IllegalArgumentException��
     * �X���[����邱�Ƃ��m�F����e�X�g�B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testResolveParameterizedClassClassClassint01() throws Exception {

        try {
            //  �e�X�g���{
            GenericsUtil.resolveParameterizedClass(null, null, 0);
            fail("��O���X���[����܂���ł����B");
        } catch (IllegalArgumentException e) {
            //  ����
            assertEquals("Argument 'genericsClass' ("
                    + Class.class.getName() + ") is null",
                    e.getMessage());
            assertEquals(IllegalArgumentException.class.getName(),
                    e.getClass().getName());
        }
    }

    /**
     * testResolveParameterizedClassClassClassint02()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) genericClass:Object��Class�C���X�^���X<br>
     *         (����) descendantClass:null<br>
     *         (����) index:0<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalArgumentException<br>
     *                    ���b�Z�[�W�F"Argument 'descendantClass'("<br>
     *                    + Class.class.getName() + ") is null"<br>
     *
     * <br>
     * ����descendantClass��null�̏ꍇ��IllegalArgumentException��
     * �X���[����邱�Ƃ��m�F����e�X�g�B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testResolveParameterizedClassClassClassint02() throws Exception {
        try {
            //  �e�X�g���{
            GenericsUtil.resolveParameterizedClass(Object.class, null, 0);
            fail("��O���X���[����܂���ł����B");
        } catch (IllegalArgumentException e) {
            //  ����
            assertEquals("Argument 'descendantClass'("
                    + Class.class.getName() + ") is null",
                    e.getMessage());
            assertEquals(IllegalArgumentException.class.getName(),
                    e.getClass().getName());
        }
    }

    /**
     * testResolveParameterizedClassClassClassint03()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) genericClass:�^�p�����[�^�錾�������Ȃ��e�N���X<br>
     *         (����) descendantClass:genericClass�̎q�N���X<br>
     *         (����) index:0<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalStateException<br>
     *
     * <br>
     * ����genericClass�^�p�����[�^�錾�������Ȃ��e�N���X�ł���ꍇ�ɁA
     * IllegalStateException���X���[����邱�Ƃ��m�F����e�X�g�B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testResolveParameterizedClassClassClassint03() throws Exception {
        try {
            //  �e�X�g���{
            GenericsUtil.resolveParameterizedClass(Object.class, String.class, 0);
            fail("��O���X���[����܂���ł����B");
        } catch (IllegalStateException e) {
            //  ����
            assertEquals(IllegalStateException.class.getName(),
                    e.getClass().getName());
        }
    }

    /**
     * testResolveParameterizedClassClassClassint04()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) genericClass:�^�p�����[�^�錾��1���e�N���X<br>
     *         (����) descendantClass:genericClass�̎q�N���X
     *                  (�^�p�����[�^�̋�̃N���X���w�肵�Ă��Ȃ�)<br>
     *         (����) index:0<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalStateException<br>
     *
     * <br>
     * �^�p�����[�^�ɋ�̃N���X���w�肳��Ă��Ȃ��ꍇ�ɁA
     * IllegalStateException���X���[����邱�Ƃ��m�F����e�X�g�B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testResolveParameterizedClassClassClassint04() throws Exception {
        try {
            //  �e�X�g���{
            GenericsUtil.resolveParameterizedClass(
                    FutureTask.class, GenericsUtil_FutureTaskStub01.class, 0);
            fail("��O���X���[����܂���ł����B");
        } catch (IllegalStateException e) {
            //  ����
            assertEquals(IllegalStateException.class.getName(),
                    e.getClass().getName());
        }
    }

    /**
     * testResolveParameterizedClassClassClassint05()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FE
     * <br><br>
     * ���͒l�F(����) genericClass:�^�p�����[�^�錾��1���e�N���X<br>
     *         (����) descendantClass:genericClass�̎q�N���X
     *                  (�^�p�����[�^�̋�̃N���X��String���w��)<br>
     *         (����) index:0<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) Class:String.class<br>
     *
     * <br>
     * descendantClass���q�N���X�ł���ꍇ�ɁA
     * �^�p�����[�^�̋�̃N���X���擾�ł��邱�Ƃ��m�F����e�X�g�B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testResolveParameterizedClassClassClassint05() throws Exception {
        //  �e�X�g���{
        Class result = GenericsUtil.resolveParameterizedClass(
                FutureTask.class, GenericsUtil_FutureTaskStub02.class, 0);

        //  ����
        assertEquals(String.class, result);
    }

    /**
     * testResolveParameterizedClassClassClassint06()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FE
     * <br><br>
     * ���͒l�F(����) genericClass:�^�p�����[�^�錾��1���e�N���X<br>
     *         (����) descendantClass:genericClass�̑��N���X
     *                  (�^�p�����[�^�̋�̃N���X��Map<String, Object>���w��)<br>
     *         (����) index:0<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) Class:Map.class<br>
     *
     * <br>
     * descendantClass�����N���X�ł���ꍇ�ɁA�^�p�����[�^�̋�̃N���X��
     * �擾�ł��邱�Ƃ��m�F����e�X�g�B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testResolveParameterizedClassClassClassint06() throws Exception {
        //  �e�X�g���{
        Class result = GenericsUtil.resolveParameterizedClass(
                FutureTask.class, GenericsUtil_FutureTaskStub03.class, 0);

        //  ����
        assertEquals(Map.class, result);
    }

    /**
     * testResolveParameterizedClassClassClassint07()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) genericClass:�^�p�����[�^�錾��1���e�N���X<br>
     *         (����) descendantClass:genericClass�̎q�N���X
     *                  (�^�p�����[�^�̋�̃N���X��String���w��)<br>
     *         (����) index:-1<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalArgumentException<br>
     *                    ���b�Z�[�W�F"Argument 'index'(-1) is out of
     *                    bounds of generics parameters"<br>
     * <br>
     * ����index�����̐��ł���ꍇ�ɁAIllegalArgumentException��
     * �X���[����邱�Ƃ��m�F����e�X�g�B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testResolveParameterizedClassClassClassint07() throws Exception {
        try {
            //  �e�X�g���{
            GenericsUtil.resolveParameterizedClass(
                    FutureTask.class, GenericsUtil_FutureTaskStub02.class, -1);
        } catch (IllegalArgumentException e) {
            //  ����
            assertEquals("Argument 'index'(-1) is out of bounds of " +
                    "generics parameters",
                    e.getMessage());
            assertEquals(IllegalArgumentException.class.getName(),
                    e.getClass().getName());
        }
    }

    /**
     * testResolveParameterizedClassClassClassint08()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) genericClass:�^�p�����[�^�錾��1���e�N���X<br>
     *         (����) descendantClass:genericClass�̑��N���X
     *                  (�^�p�����[�^�̋�̃N���X��Integer���w��)<br>
     *         (����) index:1<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalArgumentException<br>
     *                    ���b�Z�[�W�F"Argument 'index'(1) is out of
     *                    bounds of generics parameters"<br>
     *
     * <br>
     * ����index���p�����[�^����葽���ꍇ�ɁAIllegalArgumentException��
     * �X���[����邱�Ƃ��m�F����e�X�g�B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testResolveParameterizedClassClassClassint08() throws Exception {
        try {
            //  �e�X�g���{
            GenericsUtil.resolveParameterizedClass(
                    FutureTask.class, GenericsUtil_FutureTaskStub03.class, 1);
        } catch (IllegalArgumentException e) {
            //  ����
            assertEquals("Argument 'index'(1) is out of bounds of " +
                    "generics parameters",
                    e.getMessage());
            assertEquals(IllegalArgumentException.class.getName(),
                    e.getClass().getName());
        }
    }

    /**
     * testResolveParameterizedClassClassClassint09()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FE
     * <br><br>
     * ���͒l�F(����) genericClass:�^�p�����[�^�錾��3���e�N���X<br>
     *         (����) descendantClass:genericClass�̎q�N���X
     *                  (�^�p�����[�^�̋�̃N���X��String[], Integer, Boolean���w��)<br>
     *         (����) index:0<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) Class:String[].class<br>
     *
     * <br>
     * �^�p�����[�^�������̏ꍇ�ɁA�^�p�����[�^�̋�̃N���X���擾�ł��邱�Ƃ��m�F����e�X�g�B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testResolveParameterizedClassClassClassint09() throws Exception {
        //  �e�X�g���{
        Class result = GenericsUtil.resolveParameterizedClass(
                GenericsUtil_Stub01.class, GenericsUtil_Stub02.class, 0);

        //  ����
        assertEquals(String[].class, result);
    }

    /**
     * testResolveParameterizedClassClassClassint10()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FE
     * <br><br>
     * ���͒l�F(����) genericClass:�^�p�����[�^�錾��3���e�C���^�t�F�[�X<br>
     *         (����) descendantClass:genericClass�̎����N���X
     *              (�^�p�����[�^�̋�̃N���X��String[], Integer, Boolean���w��)<br>
     *         (����) index:2<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) Class:Boolean.class<br>
     *
     * <br>
     * �^�p�����[�^�錾���C���^�t�F�[�X�ōs���Ă���ꍇ�ɁA�^�p�����[�^�̋�̃N���X���擾�ł��邱�Ƃ��m�F����e�X�g�B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testResolveParameterizedClassClassClassint10() throws Exception {
        //  �e�X�g���{
        Class result = GenericsUtil.resolveParameterizedClass(
                GenericsUtil_Stub03.class, GenericsUtil_Stub04.class, 2);

        //  ����
        assertEquals(Boolean.class, result);
    }


    /**
     * testResolveParameterizedClassClassClassint11()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FE
     * <br><br>
     * ���͒l�F(����) genericClass:�^�p�����[�^�錾��3���e�C���^�t�F�[�X<br>
     *         (����) descendantClass:genericClass�̌p���C���^�t�F�[�X�̎����N���X
     *              (�^�p�����[�^�̋�̃N���X��String[], Integer, Boolean���w��)<br>
     *         (����) index:1<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) Class:Integer.class<br>
     *
     * <br>
     * �^�p�����[�^�錾���C���^�t�F�[�X�ōs���Ă���A�C���^�t�F�[�X���p������Ă���ꍇ�ɁA
     * �^�p�����[�^�̋�̃N���X���擾�ł��邱�Ƃ��m�F����e�X�g�B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testResolveParameterizedClassClassClassint11() throws Exception {
        //  �e�X�g���{
        Class result = GenericsUtil.resolveParameterizedClass(
                GenericsUtil_Stub03.class, GenericsUtil_Stub06.class, 1);

        //  ����
        assertEquals(Integer.class, result);
    }

    /**
     * testResolveParameterizedClassClassClassint12()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FE
     * <br><br>
     * ���͒l�F(����) genericClass:�^�p�����[�^�錾��3���e�C���^�t�F�[�X<br>
     *         (����) descendantClass:genericClass�̌p���C���^�t�F�[�X�̎����N���X�̃T�u�N���X
     *              (�^�p�����[�^�̋�̃N���X��String[], Integer, Boolean���w��)<br>
     *         (����) index:1<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) Class:Integer.class<br>
     *
     * <br>
     * �^�p�����[�^�錾���C���^�t�F�[�X�ōs���Ă���A�C���^�t�F�[�X���p������A
     * ���A�����N���X���p������Ă���ꍇ�ɁA
     * �^�p�����[�^�̋�̃N���X���擾�ł��邱�Ƃ��m�F����e�X�g�B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testResolveParameterizedClassClassClassint12() throws Exception {
        //  �e�X�g���{
        Class result = GenericsUtil.resolveParameterizedClass(
                GenericsUtil_Stub03.class, GenericsUtil_Stub07.class, 1);

        //  ����
        assertEquals(Integer.class, result);
    }
    /**
     * testGetAncestorTypeList01()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) genericClass:�^�p�����[�^�錾�������Ȃ��e�N���X<br>
     *         (����) descendantClass:genericClass�̎q�N���X<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalStateException<br>
     *                    ���b�Z�[�W�F"Argument 'genericClass'("<br>
     *                    + genericClass.getName()<br>
     *                    + ") does not declare type parameter"<br>
     *
     * <br>
     * ����genericClass�^�p�����[�^�錾�������Ȃ��e�N���X�ł���ꍇ�ɁA
     * IllegalStateException���X���[����邱�Ƃ��m�F����e�X�g�B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetAncestorTypeList01() throws Exception {
        try {
            //  �e�X�g���{
            GenericsUtil.getAncestorTypeList(Object.class, String.class);
            fail("��O���X���[����܂���ł����B");
        } catch (IllegalStateException e) {
            //  ����
            assertEquals("Argument 'genericClass'("
                        + Object.class.getName()
                        + ") does not declare type parameter",
                    e.getMessage());
            assertEquals(IllegalStateException.class.getName(),
                    e.getClass().getName());
        }
    }

    /**
     * testGetAncestorTypeList02()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FE
     * <br><br>
     * ���͒l�F(����) genericClass:�^�p�����[�^�錾��1���e�N���X<br>
     *         (����) descendantClass:genericClass�̎q�N���X
     *              (�^�p�����[�^�̋�̃N���X��String���w��)<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) List<ParameterizedType>:�v�f0 = genericClass��<br>
     *                  ParameterizedType<br>
     *
     * <br>
     * 1����p�����Ă���ꍇ�ɁAParameterizedType�̃��X�g���擾�ł��邱�Ƃ��m�F����e�X�g�B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetAncestorTypeList02() throws Exception {
        //  �e�X�g���{
        List<ParameterizedType> result = GenericsUtil.getAncestorTypeList(
                FutureTask.class, GenericsUtil_FutureTaskStub02.class);

        //  ����
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(GenericsUtil_FutureTaskStub02.class.getGenericSuperclass(),
                result.get(0));
    }

    /**
     * testGetAncestorTypeList03()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FE
     * <br><br>
     * ���͒l�F(����) genericClass:�^�p�����[�^�錾��1���e�N���X<br>
     *         (����) descendantClass:genericClass�̑��N���X
     *                  (�^�p�����[�^�̋�̃N���X��Integer���w��)<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) List<ParameterizedType>:�v�f0 = genericClass��<br>
     *                  ParameterizedType<br>
     *                  �v�f1 = genericClass�̎q�N���X��<br>
     *                  ParameterizedType<br>
     *
     * <br>
     * ��������p�����Ă���ꍇ�ɁAParameterizedType�̃��X�g���擾�ł��邱�Ƃ�
     * �m�F����e�X�g�B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetAncestorTypeList03() throws Exception {
        //  �e�X�g���{
        List<ParameterizedType> result = GenericsUtil.getAncestorTypeList(
                FutureTask.class, GenericsUtil_FutureTaskStub03.class);

        //  ����
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(
                getParameterizedClassList(
                        FutureTask.class, GenericsUtil_FutureTaskStub03.class),
                result);
    }

    /**
     * testGetAncestorTypeList04()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FE
     * <br><br>
     * ���͒l�F(����) genericClass:�^�p�����[�^�錾��1���C���^�t�F�[�X<br>
     *         (����) descendantClass:genericClass�̎����N���X
     *                  (�^�p�����[�^�̋�̃N���X��Integer���w��)<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) List<ParameterizedType>:�v�f0 = genericClass��<br>
     *                  ParameterizedType<br>
     *
     * <br>
     * �C���^�t�F�[�X���P�������Ă���ꍇ�ɁAParameterizedType�̃��X�g��
     * �擾�ł��邱�Ƃ��m�F����e�X�g�B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetAncestorTypeList04() throws Exception {
        //  �e�X�g���{
        List<ParameterizedType> result = GenericsUtil.getAncestorTypeList(
                Callable.class, GenericsUtil_CallableStub01.class);

        //  ����
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(
                GenericsUtil_CallableStub01.class.getGenericInterfaces()[0],
                result.get(0));
    }

    /**
     * testGetAncestorTypeList05()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FE
     * <br><br>
     * ���͒l�F(����) genericClass:�^�p�����[�^�錾��1���C���^�t�F�[�X<br>
     *         (����) descendantClass:genericClass�̎����N���X�̎q�N���X
     *              (�^�p�����[�^�̋�̃N���X��Integer���w��A
     *              �܂��AComparable<T>, Comparator<T>������)<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) List<ParameterizedType>:�v�f0 = genericClass��<br>
     *                  ParameterizedType<br>
     *
     * <br>
     * �C���^�t�F�[�X�𕡐��������Ă���ꍇ�ɁAParameterizedType�̃��X�g��
     * �擾�ł��邱�Ƃ��m�F����e�X�g�B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetAncestorTypeList05() throws Exception {
        //  �e�X�g���{
        List<ParameterizedType> result = GenericsUtil.getAncestorTypeList(
                Callable.class, GenericsUtil_CallableStub02.class);

        //  ����
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(
                GenericsUtil_CallableStub02.class.getGenericInterfaces()[0],
                result.get(0));
    }

    /**
     * testGetAncestorTypeList06()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FE
     * <br><br>
     * ���͒l�F(����) genericClass:Comparable.class<br>
     *         (����) descendantClass:Callable�̎����N���X�̎q�N���X
     *                  (�^�p�����[�^�̋�̃N���X��Integer���w��A
     *                  �܂��AComparable<T>, Comparator<T>������)<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) List<ParameterizedType>:�v�f0 = Comparable��<br>
     *                  ParameterizedType<br>
     *                  �v�f1 = Comparator��<br>
     *                  ParameterizedType<br>
     *
     * <br>
     * �C���^�t�F�[�X�𕡐��������Ă���A���A��������Ŏ�������Ă���ꍇ�ɁA
     * ParameterizedType�̃��X�g���擾�ł��邱�Ƃ��m�F����e�X�g�B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetAncestorTypeList06() throws Exception {
        //  �e�X�g���{
        List<ParameterizedType> result = GenericsUtil.getAncestorTypeList(
                Callable.class, GenericsUtil_CallableStub03.class);

        //  ����
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(
                GenericsUtil_CallableStub03.class.getGenericInterfaces()[0],
                result.get(0));
    }

    /**
     * testCheckParameterizedType01()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) type:String.class<br>
     *         (����) genericClass:ArrayList.class<br>
     *         (����) ancestorTypeList:���ArrayList<ParameterizedType><br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:false<br>
     *         (��ԕω�) ancestorTypeList:�ω��Ȃ�<br>
     *
     * <br>
     * type��ParameterizedType�ł͂Ȃ��ꍇ�ɁA���X�g�ɉ����ǉ����ꂸ
     * false���ԋp����邱�Ƃ̃e�X�g�B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testCheckParameterizedType01() throws Exception {
        //  �O����
        List<ParameterizedType> list = new ArrayList<ParameterizedType>();

        //  �e�X�g���{
        boolean result = GenericsUtil.checkParameterizedType(
                ArrayList.class, List.class, list);

        //  ����
        assertFalse(result);
        assertTrue(list.isEmpty());
    }

    /**
     * testCheckParameterizedType02()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) type:ArrayList�p���N���X�ɂ��āA
     *              ArrayList<E>��getParameterizedType()<br>
     *         (����) genericClass:Map.class<br>
     *         (����) ancestorTypeList:���ArrayList<ParameterizedType><br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:false<br>
     *         (��ԕω�) ancestorTypeList:�ω��Ȃ�<br>
     *
     * <br>
     * type��genericClass�̎q�N���X�ł͂Ȃ��ꍇ�ɁA
     * ���X�g�ɉ����ǉ����ꂸfalse���ԋp����邱�Ƃ̃e�X�g�B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testCheckParameterizedType02() throws Exception {
        //  �O����
        List<ParameterizedType> list = new ArrayList<ParameterizedType>();

        //  �e�X�g���{
        boolean result = GenericsUtil.checkParameterizedType(
                GenericsUtil_ArrayListStub01.class, Map.class, list);

        //  ����
        assertFalse(result);
        assertTrue(list.isEmpty());
    }

    /**
     * testCheckParameterizedType03()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC, E
     * <br><br>
     * ���͒l�F(����) type:ArrayList�p���N���X�ɂ��āA
     *                  ArrayList<E>��getParameterizedType()<br>
     *         (����) genericClass:List.class<br>
     *         (����) ancestorTypeList:���ArrayList<ParameterizedType><br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:false<br>
     *         (��ԕω�) ancestorTypeList:
     *              �v�f0 = ArrayList<E>��getParameterizedType()<br>
     *
     * <br>
     * type�̎��ۂ̃N���X��genericClass�Ɠ���ł͂Ȃ��ꍇ�ɁA���X�g�ɒǉ�����A
     * false���ԋp����邱�Ƃ̃e�X�g�B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testCheckParameterizedType03() throws Exception {
        //  �O����
        List<ParameterizedType> list = new ArrayList<ParameterizedType>();

        //  �e�X�g���{
        boolean result = GenericsUtil.checkParameterizedType(
                GenericsUtil_ArrayListStub01.class.getGenericSuperclass(),
                List.class, list);

        //  ����
        assertFalse(result);
        assertEquals(1, list.size());
        assertEquals(
                GenericsUtil_ArrayListStub01.class.getGenericSuperclass(),
                list.get(0));
    }

    /**
     * testCheckParameterizedType04()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC, E
     * <br><br>
     * ���͒l�F(����) type:ArrayList�p���N���X�ɂ��āA
     *                  List<E>��getParameterizedType()<br>
     *         (����) genericClass:List.class<br>
     *         (����) ancestorTypeList:���ArrayList<ParameterizedType><br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *         (��ԕω�) ancestorTypeList:�v�f0 = List<E>��getParameterizedType()<br>
     *
     * <br>
     * type�̎��ۂ̃N���X��genericClass�Ɠ���ł���ꍇ�ɁA���X�g�ɒǉ�����A
     * true���ԋp����邱�Ƃ̃e�X�g�B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings({ "null", "unchecked" })
    public void testCheckParameterizedType04() throws Exception {
        //  �O����
        Class listClass = GenericsUtil_ArrayListStub01.class;
        while (listClass != null && !listClass.equals(AbstractList.class)) {
            listClass = listClass.getSuperclass();
        }
        if (listClass == null) {
            fail(AbstractList.class.getName() + "���擾�ł��܂���B");
        }
        Type type = listClass.getGenericInterfaces()[0];

        List<ParameterizedType> list = new ArrayList<ParameterizedType>();

        //  �e�X�g���{
        boolean result = GenericsUtil.checkParameterizedType(
                type, List.class, list);

        // ����
        assertTrue(result);
        assertEquals(1, list.size());
        assertEquals(type, list.get(0));
    }

    /**
     * testResolveTypeVariable01()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) type:String.class<br>
     *         (����) ancestorTypeList:���ArrayList<ParameterizedType><br>
     *
     * <br>
     * ���Ғl�F(�߂�l) Class:String.class<br>
     *
     * <br>
     * type��Class�^�ł���ꍇ�ɁAtype�����̂܂ܕԋp����邱�Ƃ̃e�X�g�B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testResolveTypeVariable01() throws Exception {
        // �O����
        Class stringClass = String.class;

        //  �e�X�g���{
        Class result = GenericsUtil.resolveTypeVariable(
                stringClass, new ArrayList<ParameterizedType>());

        //  ����
        assertSame(stringClass, result);
    }

    /**
     * testResolveTypeVariable02()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FC, G
     * <br><br>
     * ���͒l�F(����) type:ArrayList�p���N���X<String>��List<E>�ɂ��Č^�ϐ�E<br>
     *         (����) ancestorTypeList:���ArrayList<ParameterizedType><br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalStateException<br>
     *                    ���b�Z�[�W�F"Concrete type of Type(E) was not
     *                    found in ancestorList("<br>
     *                    + ancestorTypeList + ")"<br>
     *
     * <br>
     * ancestorTypeList����type�Ɋ֘A����e���Ȃ��ꍇ�A
     * IllegalStateException���X���[����邱�Ƃ��m�F����e�X�g�B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testResolveTypeVariable02() throws Exception {
        // �O����
        Type type = getTypeVariable(
                AbstractList.class, GenericsUtil_ArrayListStub01.class, 0);

        List<ParameterizedType> list = new ArrayList<ParameterizedType>();
        try {
            //  �e�X�g���{
            GenericsUtil.resolveTypeVariable(
                    type, list);
            fail("��O���������܂���ł����B");
        } catch (IllegalStateException e) {
            //  ����
            assertEquals(
                    "Concrete type of Type(" + type
                    + ") was not found in ancestorList("
                    + list + ")",
                    e.getMessage());
            assertEquals(IllegalStateException.class.getName(),
                    e.getClass().getName());
        }
    }

    /**
     * testResolveTypeVariable03()
     * <br><br>
     *
     *  (�ُ�n)
     * <br>
     * �ϓ_�FC, G
     * <br><br>
     * ���͒l�F(����) type:GenericArrayType�C���X�^���X<br>
     *         (����) ancestorTypeList:���ArrayList<ParameterizedType><br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalStateException<br>
     *
     * <br>
     * isClassOrTypeVariable��IllegalStateException���X���[����邱�Ƃ��m�F����e�X�g�B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testResolveTypeVariable03() throws Exception {
        try {
            //  �e�X�g���{
            GenericsUtil.resolveTypeVariable(
                    new GenericsUtil_GenericArrayTypeStub03(),
                    new ArrayList<ParameterizedType>());
            fail("��O���������܂���ł����B");
        } catch (IllegalStateException e) {
            //  ����
            assertNotNull(e);
            assertEquals(IllegalStateException.class.getName(),
                    e.getClass().getName());
        }
    }

    /**
     * testResolveTypeVariable04()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FC, G
     * <br><br>
     * ���͒l�F(����) type:Method�Ő錾���ꂽ�^�ϐ�<br>
     *         (����) ancestorTypeList:ArrayList<ParameterizedType><br>
     *                ���e�́AArrayList�p���N���X<String>��
     *                �e�N���X��ParameterizedType�̑S��<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalStateException<br>
     *                    ���b�Z�[�W�F"TypeVariable("<br>
     *                    + targetType.getName()
     *                    + " is not declared at Class "<br>
     *                    + "(ie. is declared at Method or Constructor)")<br>
     *
     * <br>
     * type���N���X�Ő錾����Ă��Ȃ��ꍇ�AIllegalStateException��
     * �X���[����邱�Ƃ��m�F����e�X�g�B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testResolveTypeVariable04() throws Exception {
        // �O����
        Method emptyListMethod =
            Collections.class.getMethod("emptyList", new Class[0]);
        TypeVariable type = emptyListMethod.getTypeParameters()[0];

        List<ParameterizedType> list = getParameterizedClassList(
                List.class, GenericsUtil_ArrayListStub01.class);

        try {
            //  �e�X�g���{
            GenericsUtil.resolveTypeVariable(type, list);
            fail("��O���������܂���ł����B");
        } catch (IllegalStateException e) {
            //  ����
            assertEquals("TypeVariable("
                    + type.getName()
                    + " is not declared at Class "
                    + "(ie. is declared at Method or Constructor)",
                e.getMessage());
            assertEquals(IllegalStateException.class.getName(),
                    e.getClass().getName());
        }
    }

    /**
     * testResolveTypeVariable05()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) type:ArrayList�p���N���X<String>��List<E>�ɂ��Č^�ϐ�E<br>
     *         (����) ancestorTypeList:ArrayList<ParameterizedType><br>
     *                ���e�́AArrayList�p���N���X<String>�̐e�N���X��
     *                ParameterizedType�̑S�Ăɉ����A
     *                Comparator��ParameterizedType<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) Class:Map.class<br>
     *
     * <br>
     * �^�ϐ��錾��1�̏ꍇ�A�w�肵���^�ϐ��̎��ۂ̌^���ԋp����邱�Ƃ̃e�X�g�B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testResolveTypeVariable05() throws Exception {
        // �O����
        Type type = getTypeVariable(
                AbstractList.class, GenericsUtil_ArrayListStub01.class, 0);

        List<ParameterizedType> list = getParameterizedClassList(
                List.class, GenericsUtil_ArrayListStub01.class);
        list.add((ParameterizedType) Integer.class.getGenericInterfaces()[0]);

        //  �e�X�g���{
        Class result = GenericsUtil.resolveTypeVariable(type, list);

        //  ����
        assertEquals(Map.class, result);
    }

    /**
     * testResolveTypeVariable06()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) type:HashMap�p���N���X<String, Integer>(
     *              K��V�̏��Ԃ����ւ��邱��)��
     *              Map<K, V>�ɂ��Č^�ϐ�V<br>
     *         (����) ancestorTypeList:ArrayList<ParameterizedType><br>
     *                ���e�́AHashMap�p���N���X<String, Integer>��
     *                �e�N���X��ParameterizedType�̑S�āB<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) Class:Integer.class<br>
     *
     * <br>
     * �^�ϐ��錾�������̏ꍇ�A�w�肵���^�ϐ��̎��ۂ̌^���ԋp����邱�Ƃ̃e�X�g�B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testResolveTypeVariable06() throws Exception {
        // �O����
        Type type = getTypeVariable(
                HashMap.class, GenericsUtil_HashMapStub02.class, 1);

        List<ParameterizedType> list = getParameterizedClassList(
                Map.class, GenericsUtil_HashMapStub02.class);

        //  �e�X�g���{
        Class result = GenericsUtil.resolveTypeVariable(type, list);

        //  ����
        assertEquals(Integer.class, result);
    }

    /**
     * testisNotTypeVariable01()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) type:String.class<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *
     * <br>
     * type��Class�ł���ꍇ�ɁAtrue���ԋp����邱�Ƃ��m�F����e�X�g�B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testsNotTypeVariable01() throws Exception {
        //  �e�X�g���{
        boolean result = GenericsUtil.isNotTypeVariable(String.class);

        //  ����
        assertTrue(result);
    }

    /**
     * testIsNotTypeVariable02()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) type:ArrayList�p���N���X�ɂ��āA
     * ArrayList<E>��E<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:false<br>
     *
     * <br>
     * type��TypeValiable�ł���ꍇ�ɁAfalse���ԋp����邱�Ƃ��m�F����e�X�g�B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testIsNotTypeVariable02() throws Exception {
        //  �O����
        Type type = getTypeVariable(
                AbstractList.class, GenericsUtil_ArrayListStub01.class, 0);

        //  �e�X�g���{
        boolean result = GenericsUtil.isNotTypeVariable(type);

        //  ����
        assertFalse(result);
    }

    /**
     * testIsNotTypeVariable03()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FC, G
     * <br><br>
     * ���͒l�F(����) type:WildCardType�C���X�^���X<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalStateException<br>
     *                    ���b�Z�[�W�F"Type("<br>
     *                    + type + ") is not instance of "<br>
     *                    + TypeVariable.class.getName() + " nor "<br>
     *                    + Class.class.getName()<br>
     *
     * <br>
     * type��Class�ł�TypeValiable�ł��Ȃ��ꍇ�AIllegalStateException��
     * �X���[����邱�Ƃ��m�F����e�X�g�B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testIsNotTypeVariable03() throws Exception {

        // �O����
        Type type = new GenericsUtil_WildCardTypeStub01();

        try {
            //  �e�X�g���{
            GenericsUtil.isNotTypeVariable(type);
            fail("��O���X���[����܂���ł����B");
        } catch (IllegalStateException e) {
            //  ����
            assertEquals("Type("
                    + type + ") is not instance of "
                    + TypeVariable.class.getName() + ", "
                    + ParameterizedType.class.getName() + ", "
                    + GenericArrayType.class.getName() + " nor "
                    + Class.class.getName(),
                e.getMessage());
            assertEquals(IllegalStateException.class.getName(),
                    e.getClass().getName());
        }
    }

    /**
     * testIsNotTypeVariable04()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) type:ArrayList�p���N���X�ɂ��āA
     * ArrayList<E>��ParameterizedType()<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *
     * <br>
     * type��ParameterizedType�ł���ꍇ�ɁAtrue���ԋp����邱�Ƃ��m�F����e�X�g�B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testIsNotTypeVariable04() throws Exception {
        //  �O����
        Type type = GenericsUtil_ArrayListStub01.class.getGenericSuperclass();

        //  �e�X�g���{
        boolean result = GenericsUtil.isNotTypeVariable(type);

        //  ����
        assertTrue(result);
    }

    /**
     * testIsNotTypeVariable05()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) type: GenericArrayType�C���X�^���X<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *
     * <br>
     * type��GenericArrayType�ł���ꍇ�ɁAtrue���ԋp����邱�Ƃ��m�F����e�X�g�B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testIsNotTypeVariable05() throws Exception {
        //  �O����
        Type type = new GenericsUtil_GenericArrayTypeStub01();

        //  �e�X�g���{
        boolean result = GenericsUtil.isNotTypeVariable(type);

        //  ����
        assertTrue(result);
    }

    /**
     * <code>startClass</code>����<code>endClass</code>�܂ł�
     * �p���֌W�ɂ���A<code>ParameterizedType</code>�����X�g�ɂ���
     * �ԋp����B
     *
     * @param <T> �N���X�B
     * @param endClass �I�[�N���X�B
     * @param startClass �J�n�N���X�B
     * @return <code>ParameterizedType</code>�̃��X�g�B
     */
    @SuppressWarnings("unchecked")
    private <T> List<ParameterizedType> getParameterizedClassList(
            Class<T> endClass, Class<? extends T> startClass) {
        List<ParameterizedType> result = new ArrayList<ParameterizedType>();
        Class clazz = startClass;

        while (clazz != null) {
            Type type = clazz.getGenericSuperclass();
            if (type instanceof ParameterizedType) {
                result.add((ParameterizedType) type);
            }
            if (clazz.equals(endClass)) {
                break;
            }
           clazz = clazz.getSuperclass();
        }
        return result;
    }

    /**
     * �C���^�t�F�[�X�N���X�̌^�ϐ����擾����B
     *
     * @param <T> �N���X�B
     * @param firstImplementation �C���^�t�F�[�X���ŏ��Ɏ��������N���X�B
     * @param implementClass �擾���Ƃ̎����N���X�B
     * @param index ���ԖڂɌ^�ϐ����錾���ꂽ���B
     * @return �^�ϐ��B
     */
    @SuppressWarnings({ "null", "unchecked" })
    private <T> Type getTypeVariable(Class<T> firstImplementation,
            Class<? extends T> implementClass, int index) {
        Class clazz = implementClass;
        while (clazz != null && !clazz.equals(firstImplementation)) {
            clazz = clazz.getSuperclass();
        }
        if (clazz == null) {
            fail(firstImplementation.getName() + "���擾�ł��܂���B");
        }
        ParameterizedType parameterizedClass =
            (ParameterizedType) clazz.getGenericInterfaces()[0];
        Type type = parameterizedClass.getActualTypeArguments()[index];
        return type;
    }

    /**
     * �^�ϐ����擾����B
     * @param clazz �擾���Ƃ̎����N���X�B
     * @param index ���ԖڂɌ^�ϐ����錾���ꂽ���B
     *
     * @return �^�ϐ��B
     */
    private <T> Type getTypeArgument(Class<T> clazz, int index) {
        Type type = clazz.getGenericSuperclass();
        if (!(type instanceof ParameterizedType)) {
            fail("ParameterizedType���擾�ł��܂���B");
        }
        Type argument = ((ParameterizedType) type).getActualTypeArguments()[index];
        return argument;
    }

    /**
     * testGetRawClass01()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) type:String.class<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) Class:String.class<br>
     *
     * <br>
     * type��Class�ł���ꍇ�ɁA���̂܂ܕԋp����邱�Ƃ��m�F����e�X�g�B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testGetRawClass01() throws Exception {
        //  �O����
        Type type = String.class;

        //  �e�X�g���{
        Class result = GenericsUtil.getRawClass(type);

        //  ����
        assertEquals(String.class, result);
    }

    /**
     * testGetRawClass02()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) type:ArrayList�p���N���X�ɂ��āAArrayList<E>��E<br>
     *                (E��Map<String, Object>��\��ParameterizedType)<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) Class:Map.class<br>
     *
     * <br>
     * type��ParameterizedType�ł���ꍇ�ɁA���̌��ƂȂ�N���X���ԋp����邱�Ƃ��m�F����e�X�g�B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testGetRawClass02() throws Exception {
        //  �O����
        Type type = getTypeArgument(
                GenericsUtil_ArrayListStub01.class, 0);

        //  �e�X�g���{
        Class result = GenericsUtil.getRawClass(type);

        //  ����
        assertEquals(Map.class, result);
    }

    /**
     * testGetRawClass03()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) type:ArrayList�p���N���X�ɂ��āAArrayList<E>��E<br>
     *                (E��String[]��\��GenericArrayType)<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) Class:String[].class<br>
     *
     * <br>
     * type��GenericArrayType�ł���ꍇ�ɁA���̌��ƂȂ�z��N���X���ԋp����邱�Ƃ��m�F����e�X�g�B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testGetRawClass03() throws Exception {
        //  �O����
        Type type = getTypeArgument(
                GenericsUtil_ArrayListStub02.class, 0);

        //  �e�X�g���{
        Class result = GenericsUtil.getRawClass(type);

        //  ����
        assertEquals(String[].class, result);
    }

    /**
     * testGetRawClass04()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FC, G
     * <br><br>
     * ���͒l�F(����) type:WildCardType�C���X�^���X<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalStateException<br>
     *                    ���b�Z�[�W�F"Type("<br>
     *                    + type + ") is not instance of "<br>
     *                    + ParameterizedType.class.getName() + ", "<br>
     *                    + GenericArrayType.class.getName() + " nor "<br>
     *                    + Class.class.getName()<br>
     *
     * <br>
     * type��Class�ł�TypeValiable�ł��Ȃ��ꍇ�AIllegalStateException���X���[����邱�Ƃ��m�F����e�X�g�B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetRawClass04() throws Exception {
        // �O����
        Type type = new GenericsUtil_WildCardTypeStub01();

        try {
            //  �e�X�g���{
            GenericsUtil.getRawClass(type);
            fail("��O���X���[����܂���ł����B");
        } catch (IllegalStateException e) {
            //  ����
            assertEquals("Type("
                    + type + ") is not instance of "
                    + ParameterizedType.class.getName() + ", "
                    + GenericArrayType.class.getName() + " nor "
                    + Class.class.getName(),
                e.getMessage());
            assertEquals(IllegalStateException.class.getName(),
                    e.getClass().getName());
        }
    }

    /**
     * testGetRawClass05()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) type:GenericArrayType�C���X�^���X
     *        �igetGenericComponentType�̖߂�l:GenericArrayType�C���X�^���X
     *            (getGenericComponentType�̖߂�l:String.class)�j<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) Class:String[].class<br>
     *
     * <br>
     * type��GenericArrayType�ł��AgetGenericComponentClass�̖߂�l��
     * GenericArrayType�ł���ꍇ�ɁA����ɓ��삵�Ȃ�����
     * �i����getGenericComponentClass��2���z�񂪎擾�����j���m�F����e�X�g�B
     * �������JDK�ł́A���̂悤�Ȏ��Ԃ͔������Ȃ��B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testGetRawClass05() throws Exception {
        //  �O����
        Type type = new GenericsUtil_GenericArrayTypeStub01();

        //  �e�X�g���{
        Class result = GenericsUtil.getRawClass(type);

        //  ����
        assertEquals(String[][].class, result);
    }
}
