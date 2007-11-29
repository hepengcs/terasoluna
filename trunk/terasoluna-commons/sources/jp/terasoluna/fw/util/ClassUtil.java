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

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 *  ������(String)����A�C���X�^���X�𐶐����郆�[�e�B���e�B�N���X�B
 * 
 * <li>�ʏ�̃C���X�^���X��������</li>
 * <code><pre>
 *     Integer integer = new Integer("12");
 * </pre></code>
 * 
 * <li>�ʏ�̕�����(String)����̃C���X�^���X��������</li>
 * <code><pre>
 *     Integer integer = null;
 * 
 *     Class createClass = null;
 *     Class paramClass = null;
 * 
 *     //�N���X���[�_���擾����
 *     Thread t = Thread.currentThread();
 *     ClassLoader cl = t.getContextClassLoader();
 * 
 *     try {
 * 
 *         //��������N���X��Class�I�u�W�F�N�g���擾
 *         createClass = cl.loadClass("java.lang.Integer");
 *         //�R���X�g���N�^�̈����ƂȂ�N���X��Class�I�u�W�F�N�g���擾
 *         paramClass = cl.loadClass("java.lang.String");
 * 
 *     } catch(ClassNotFoundException e) {
 *         //�N���X�t�@�C����������Ȃ������ꍇ�B
 *     }
 * 
 *     try {
 * 
 *         //Constructor�I�u�W�F�N�g���擾
 *         Constructor constructor =
 *             classObject.getConstructor(new Class[]{paramClassObject});
 * 
 *     } catch(NoSuchMethodException e) {
 *         //�w�肳�ꂽ�������`�����R���X�g���N�^�����������ꍇ
 *     } catch(SecurityException e) {
 *         //���ւ̃A�N�Z�X�����ۂ��ꂽ�ꍇ
 *     }
 * 
 *     try {
 * 
 *         //�C���X�^���X�̐���
 *         integer = constructor.newInstance(new Object{"12"});
 * 
 *     } catch (IllegalArgumentException e) {
 *         //�s���Ȉ������n���ꂽ�ꍇ
 *     } catch (InstantiationException e) {
 *         //���ۃN���X�������ꍇ
 *     } catch (IllegalAccessException e) {
 *         //�R���X�g���N�^�ɃA�N�Z�X�o���Ȃ������ꍇ
 *     } catch (InvocationTargetException e) {
 *         //�R���X�g���N�^����O���X���[�����ꍇ
 *     }
 * 
 * </pre></code>
 * 
 * <li>���̃N���X���g�p�����ꍇ�̃C���X�^���X��������</li>
 * <code><pre>
 *     Integer integer = null;
 * 
 *     try {
 *         integer = (Integer) ClassUtil.create(
 *             "java.lang.Integer", new Object[] {"12"});
 *     } catch(ClassLoadException e) {
 *         //�C���X�^���X�������ɗ�O�����������ꍇ
 *     }
 * </pre></code>
 * 
 * @see jp.terasoluna.fw.util.ClassLoadException
 * 
 */
public final class ClassUtil {

    /**
     * ��������I�u�W�F�N�g�̃N���X�������ɃC���X�^���X�𐶐����܂��B
     * 
     * �N���X���� null �œn���ꂽ�ꍇ�A
     *  NullPointerException ���X���[����܂��B
     * @param className
     * ��������I�u�W�F�N�g�̃N���X��
     * @return
     * ���������C���X�^���X
     * @throws ClassLoadException
     * �C���X�^���X�������ɔ���������O�����b�v������O
     */
    public static Object create(String className) throws ClassLoadException {

        // �Q�Ƃ𐶐�
        Object object = null;

        // �N���X���[�_���擾����
        Thread t = Thread.currentThread();
        ClassLoader cl = t.getContextClassLoader();

        try {
            // Class�C���X�^���X�𐶐����A�I�u�W�F�N�g�𐶐�����B
            object = cl.loadClass(className).newInstance();

        } catch (InstantiationException e) {
            // ���ۃN���X�������ꍇ
            throw new ClassLoadException(e);
        } catch (IllegalAccessException e) {
            // �R���X�g���N�^�ɃA�N�Z�X�o���Ȃ������ꍇ
            throw new ClassLoadException(e);
        } catch (ClassNotFoundException e) {
            // *.class�t�@�C����������Ȃ��ꍇ
            throw new ClassLoadException(e);
        }

        // �������ꂽ�I�u�W�F�N�g��Ԃ��B
        return object;
    }

    /**
     * ��������I�u�W�F�N�g�̃N���X�������ɃC���X�^���X�𐶐����܂��B
     * 
     * �N���X���� null �œn���ꂽ�ꍇ�A
     *  NullPointerException ���X���[����܂��B
     * @param className
     * ��������I�u�W�F�N�g�̃N���X��
     * @param constructorParameter
     * ��������I�u�W�F�N�g�̃R���X�g���N�^�̃p�����[�^<br>
     * (��:)���̃p�����[�^�͐�������I�u�W�F�N�g�̈����̏��ԂƑΉ�����K�v������܂��B
     * @return
     * ���������C���X�^���X
     * @throws ClassLoadException
     * �C���X�^���X�������ɔ���������O�����b�v������O
     */
    public static Object create(String className,
                                 Object[] constructorParameter)
                                 throws ClassLoadException {

        // �Q�Ƃ̐���
        Constructor[] constructors = null;

        // �N���X���[�_���擾����
        Thread t = Thread.currentThread();
        ClassLoader cl = t.getContextClassLoader();

        try {
            // ����Class�C���X�^���X�̎��A
            //�S�ẴR���X�g���N�^�I�u�W�F�N�g���擾�B
            constructors = cl.loadClass(className).getConstructors();
        } catch (SecurityException e) {
            // ���ւ̃A�N�Z�X�����ۂ��ꂽ�ꍇ
            throw new ClassLoadException(e);
        } catch (ClassNotFoundException e) {
            // *.class�t�@�C����������Ȃ��ꍇ
            throw new ClassLoadException(e);
        }

        // �C�ӂ̃I�u�W�F�N�g�����������܂ŁA
        // �S�ẴR���X�g���N�^�I�u�W�F�N�g����̐��������݂�
        for (int i = 0; i < constructors.length; i++) {

            // �Q�Ƃ𐶐�
            Object object = null;

            try {
                // �R���X�g���N�^�Ɉ�����n���A�I�u�W�F�N�g�̐��������݂�B
                object = constructors[i].newInstance(constructorParameter);
            } catch (IllegalArgumentException e) {
                // �s���Ȉ������n���ꂽ�ꍇ
                continue;
            } catch (InstantiationException e) {
                // ���ۃN���X�������ꍇ
                throw new ClassLoadException(e);
            } catch (IllegalAccessException e) {
                // �R���X�g���N�^�ɃA�N�Z�X�o���Ȃ������ꍇ
                throw new ClassLoadException(e);
            } catch (InvocationTargetException e) {
                // �R���X�g���N�^���X���[�����O�����b�v
                throw new ClassLoadException(e);
            }

            // �I�u�W�F�N�g����������Ă����ꍇ�������I��
            if (object != null) {
                return object;
            }

        }

        // �I�u�W�F�N�g���A�����ł��Ȃ������ꍇ�́A��O���X���[����
        throw new ClassLoadException(
            new IllegalArgumentException("class name is " + className));
    }

}

