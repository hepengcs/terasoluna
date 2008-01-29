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

import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.GenericDeclaration;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * <code>Generics</code>���������߂̃��[�e�B���e�B�N���X�B
 *
 */
public class GenericsUtil {
    /**
     * ���O�N���X�B
     */
    private static final Log log = LogFactory.getLog(GenericsUtil.class);

    /**
     * �N���X�̌^�p�����[�^�̎��ۂ̌^���擾����B
     * <p>
     * <h5>���ۂ̌^�̎擾�̉�</h5>
     * ���̃N���X�Ŏ��ۂ̌^���擾�ł���̂́A�N���X�錾�Ŏ��ۂ̌^��
     * �w�肳��Ă���ꍇ�ł���B �N���X�錾�Ŏ��ۂ̌^���w�肳��Ă��Ȃ��ꍇ�A
     * �N���X�錾��<code>WildCardType</code>��
     * �w�肳��Ă���ꍇ�A����сA�R�[�h���ŕϐ��錾�̍ۂɎ��ۂ̌^��
     * �w�肳��Ă���ꍇ�͎擾�ł��Ȃ��B
     * <ul>
     *   <li>�擾�ł����(�q�N���X�̂ŋ�̃N���X���w�肳��Ă���)
     *     <code><pre>
     *     public class Descendant extends Generic&lt;String, Integer&gt; {
     *        ...
     *     }
     *     </pre></code>
     * ���̏ꍇ�A������[<code>String</code>, <code>Integer</code>]��
     * �z�񂪎擾�ł���
     *   </li>
     *   <li>�擾�ł����(�q�N���X�̃N���X�錾�Ŕz�񂪎w�肳��Ă���)
     *     <code><pre>
     *     public class Descendant extends Generic&lt;String[], Integer&gt; {
     *        ...
     *     }
     *     </pre></code>
     * ���̏ꍇ�A������[<code>String[]</code>, <code>Integer</code>]��
     * �z�񂪎擾�ł���
     *   </li>
     *   <li>�擾�ł����(�q�N���X�̃N���X�錾�Ō^�p�����[�^������̃N���X���w�肳��Ă���)
     *     <code><pre>
     *     public class Descendant
     *         extends Generic&lt;String[], Map&lt;String, Object&gt;&gt; {
     *        ...
     *     }
     *     </pre></code>
     * ���̏ꍇ�A������[<code>String[]</code>, <code>Map</code>]��
     * �z�񂪎擾�ł���
     *   </li>
     *   <li>�擾�ł��Ȃ���(�q�N���X�̃N���X�錾�Ō^�p�����[�^���w�肳��Ă���)
     *     <code><pre>
     *     public class Descendant&lt;P, Q&gt; extends Generic&lt;S, T&gt; {
     *        ...
     *     }
     *     </pre></code>
     *   </li>
     *   <li>�擾�ł��Ȃ���(�q�N���X�̃N���X�錾�Ń��C���h�J�[�h���w�肳��Ă���)
     *     <code><pre>
     *     public class Descendant&lt;P extends String, Q super Bean&gt;
     *         extends Generic&lt;S, T&gt; {
     *        ...
     *     }
     *     </pre></code>
     *   </li>
     *   <li>�擾�ł��Ȃ���(�R�[�h���ŋ�̃N���X���w�肳��Ă���)
     *     <code><pre>
     *     Generic&lt;String, Integer&gt; descendant =
     *         new Generic&lt;String, Integer&gt;();
     *     </pre></code>
     *   </li>
     * </ul>
     * </p>
     * <p>
     * <h5>������p���̏ꍇ</h5>
     * <code>genericType</code>����<code>descendantClass</code>�܂�
     * ������̌p��������ꍇ���A���ۂ̌^���擾���邱�Ƃ��ł���B
     * <ul>
     *   <li>������p���̗�
     *     <code><pre>
     *       public class Child&lt;S, T&gt; extends Generic&lt;S, T&gt; {
     *          ...
     *       }
     *
     *       public class GrandChild&lt;S, T&gt; extends Child&lt;S, T&gt; {
     *          ...
     *       }
     *
     *       public class Descendant extends GrandChild&lt;String, Integer&gt; {
     *          ...
     *       }
     * </pre></code>
     * ���̏ꍇ�A������[<code>String</code>, <code>Integer</code>]��
     * �z�񂪎擾�ł���
     *   </li>
     * </ul>
     * </p>
     * <p>
     * <h5>�^�p�����[�^�̏��Ԃ��ύX���ꂽ�ꍇ</h5>
     * <code>genericType</code>����<code>descendantClass</code>�܂ł�
     * �p���̉ߒ��Ō^�p�����[�^�̏��Ԃ�����ւ�����ꍇ�ł��A
     * <code>genercClass</code>�Ő錾���ꂽ���ԂŎ��ۂ̌^���擾�ł���B
     * <ul>
     *   <li>���Ԃ�����ւ��ꍇ�̗�
     *     <code><pre>
     *       public class Generic&lt;S, T&gt; {
     *          ...
     *       }
     *
     *       public class Child&lt;A, T, B, S&gt; extends Generic&lt;S, T&gt; {
     *          ...
     *       }
     *
     *       public class Descendant
     *            extends Generic&lt;Boolean, Integer, Double, String&gt; {
     *          ...
     *       }
     * </pre></code>
     * ���̏ꍇ�A������[<code>String</code>, <code>Integer</code>]��
     * �z�񂪎擾�ł���
     *    </li>
     * </ul>
     * </p>
     *
     * @param <T> �^�p�����[�^�錾�������N���X�̌^�B
     * @param genericClass �^�p�����[�^�錾�������N���X�B
     * @param descendantClass <code>genericsClass</code>���p�����A
     *      ��̓I�Ȍ^�p�����[�^���w�肵���N���X�B
     * @return ���ۂ̌^��\��<code>Class</code>�C���X�^���X�̔z��B
     *               ���Ԃ�<code>genercClass</code>�Ő錾���ꂽ���Ԃł���B
     * @throws IllegalArgumentException ����<code>genericClass</code>��
     *      <code>null</code>�̏ꍇ�B
     *      ����<code>descendantClass</code>��<code>null</code>�̏ꍇ�B
     * @throws IllegalStateException
     *       <code>descendantClass</code>�̎����Ō^�p�����[�^��
     *          ��̃N���X�Ƃ��Ďw�肳��Ă��Ȃ��ꍇ�B
     *      ����<code>genercClass</code>���^�p�����[�^��錾�����N���X�ł�
     *      �Ȃ������ꍇ�B
     */
    @SuppressWarnings("unchecked")
    public static <T>  Class[] resolveParameterizedClass(
            Class<T> genericClass, Class<? extends T> descendantClass)
            throws IllegalArgumentException, IllegalStateException {
        if (genericClass == null) {
            throw new IllegalArgumentException(
                    "Argument 'genericsClass' ("
                    + Class.class.getName() + ") is null");
        }
        if (descendantClass == null) {
            throw new IllegalArgumentException(
                    "Argument 'descendantClass'("
                    + Class.class.getName() + ") is null");
        }

        List<ParameterizedType> ancestorTypeList =
                getAncestorTypeList(genericClass, descendantClass);

        ParameterizedType parameterizedType =
            ancestorTypeList.get(ancestorTypeList.size() - 1);
        // parameterizedType�̎��ۂ̌^������\�� Type �I�u�W�F�N�g�̔z��
        // ��FAbstractBLogic<P, R>�̌^������P��R�B
        Type[] actualTypes = parameterizedType.getActualTypeArguments();

        // �C���X�^���X�Ő錾���ꂽ�^�p�����[�^�����ۂ̌^�ɉ�������B
        Class[] actualClasses = new Class[actualTypes.length];
        for (int i = 0; i < actualTypes.length; i++) {
            // actualTypes[i]��i�Ԗڂ̌^�����B
            // ancestorList���^�p�����[�^�錾���Ă���N���X�̃��X�g�B
            actualClasses[i] =
                resolveTypeVariable(actualTypes[i], ancestorTypeList);
        }
        return actualClasses;
    }

    /**
     * �^�p�����[�^�̎��ۂ̌^���擾����B
     * <p>
     * <h5>���ۂ̌^�̎擾�̉�</h5>
     * ���̃N���X�Ŏ��ۂ̌^���擾�ł���̂́A�N���X�錾�Ŏ��ۂ̌^��
     * �w�肳��Ă���ꍇ�ł���B �N���X�錾�Ŏ��ۂ̌^���w�肳��Ă��Ȃ��ꍇ�A
     * �N���X�錾��<code>WildCardType</code>��
     * �w�肳��Ă���ꍇ�A����сA�R�[�h���ŕϐ��錾�̍ۂɎ��ۂ̌^��
     * �w�肳��Ă���ꍇ�͎擾�ł��Ȃ��B
     * <ul>
     *   <li>�擾�ł����(�q�N���X�̂ŋ�̃N���X���w�肳��Ă���)
     *     <code><pre>
     *     public class Descendant extends Generic&lt;String, Integer&gt; {
     *        ...
     *     }
     *     </pre></code>
     * ���̏ꍇ�A������<code>String</code>�܂���<code>Integer</code>��
     * �擾�ł���
     *   </li>
     *   <li>�擾�ł����(�q�N���X�̃N���X�錾�Ŕz�񂪎w�肳��Ă���)
     *     <code><pre>
     *     public class Descendant extends Generic&lt;String[], Integer&gt; {
     *        ...
     *     }
     *     </pre></code>
     * ���̏ꍇ�A������<code>String[]</code>�܂���<code>Integer</code>��
     * �擾�ł���
     *   </li>
     *   <li>�擾�ł����(�q�N���X�̃N���X�錾�Ō^�p�����[�^������̃N���X���w�肳��Ă���)
     *     <code><pre>
     *     public class Descendant
     *         extends Generic&lt;String[], Map&lt;String, Object&gt;&gt; {
     *        ...
     *     }
     *     </pre></code>
     * ���̏ꍇ�A������<code>String[]</code>�܂���<code>Map</code>
     * ���擾�ł���
     *   </li>
     *   <li>�擾�ł��Ȃ���(�q�N���X�̃N���X�錾�Ō^�p�����[�^���w�肳��Ă���)
     *     <code><pre>
     *     public class Descendant&lt;P, Q&gt; extends Generic&lt;S, T&gt; {
     *        ...
     *     }
     *     </pre></code>
     *   </li>
     *   <li>�擾�ł��Ȃ���(�q�N���X�̃N���X�錾�Ń��C���h�J�[�h���w�肳��Ă���)
     *     <code><pre>
     *     public class Descendant&lt;P extends String, Q super Bean&gt;
     *         extends Generic&lt;S, T&gt; {
     *        ...
     *     }
     *     </pre></code>
     *   </li>
     *   <li>�擾�ł��Ȃ���(�R�[�h���ŋ�̃N���X���w�肳��Ă���)
     *     <code><pre>
     *     Generic&lt;String, Integer&gt; descendant =
     *         new Generic&lt;String, Integer&gt;();
     *     </pre></code>
     *   </li>
     * </ul>

     * </p>
     * <p>
     * <h5>������p���̏ꍇ</h5>
     * <code>genericType</code>����<code>descendantClass</code>�܂�
     * ������̌p��������ꍇ���A���ۂ̌^���擾���邱�Ƃ��ł���B
     * <ul>
     *   <li>������p���̗�
     *     <code><pre>
     *       public class Child&lt;S, T&gt; extends Generic&lt;S, T&gt; {
     *          ...
     *       }
     *
     *       public class GrandChild&lt;S, T&gt; extends Child&lt;S, T&gt; {
     *          ...
     *       }
     *
     *       public class Descendant extends GrandChild&lt;String, Integer&gt; {
     *          ...
     *       }
     * </pre></code>
     * ���̏ꍇ�A������<code>String</code>�܂���<code>Integer</code>
     * ���擾�ł���
     *   </li>
     * </ul>
     * </p>
     * <p>
     * <h5>�^�p�����[�^�̏��Ԃ��ύX���ꂽ�ꍇ</h5>
     * <code>genericType</code>����<code>descendantClass</code>�܂ł�
     * �p���̉ߒ��Ō^�p�����[�^�̏��Ԃ�����ւ�����ꍇ�ł��A
     * <code>genercClass</code>�Ő錾���ꂽ���ԂŎ��ۂ̌^���擾�ł���B
     * <ul>
     *   <li>���Ԃ�����ւ��ꍇ�̗�
     *     <code><pre>
     *       public class Generic&lt;S, T&gt; {
     *          ...
     *       }
     *
     *       public class Child&lt;A, T, B, S&gt; extends Generic&lt;S, T&gt; {
     *          ...
     *       }
     *
     *       public class Descendant
     *            extends Generic&lt;Boolean, Integer, Double, String&gt; {
     *          ...
     *       }
     * </pre></code>
     * ���̏ꍇ�A������<code>String</code>�܂���<code>Integer</code>
     * ���擾�ł���
     *    </li>
     * </ul>
     * </p>
     *
     * @param <T> �^�p�����[�^�錾�������N���X�̌^�B
     * @param genericClass �^�p�����[�^�錾�������N���X�B
     * @param descendantClass <code>genericsClass</code>���p�����A
     *      ��̓I�Ȍ^�p�����[�^���w�肵���N���X�B
     * @param index ���ۂ̌^���擾����^�p�����[�^�̐錾�����B
     * @return ���ۂ̌^��\��<code>Class</code>�C���X�^���X�B
     * @throws IllegalArgumentException ����<code>genericClass</code>��
     *      <code>null</code>�̏ꍇ�B
     *      ����<code>descendantClass</code>��<code>null</code>�̏ꍇ�B
     *      ����<code>index</code>��<code>0</code>��菬�����A�܂��́A
     *      �錾���ꂽ�^�p�����[�^���ȏ�̏ꍇ�B
     * @throws IllegalStateException
     *       <code>descendantClass</code>�̎����Ō^�p�����[�^��
     *          ��̃N���X�Ƃ��Ďw�肳��Ă��Ȃ��ꍇ�B
     *      ����<code>genercClass</code>���^�p�����[�^��錾�����N���X�ł�
     *      �Ȃ������ꍇ�B
     */
    @SuppressWarnings("unchecked")
    public static <T> Class resolveParameterizedClass(
            Class<T> genericClass, Class<? extends T> descendantClass,
            int index)
            throws IllegalArgumentException, IllegalStateException {
        if (genericClass == null) {
            throw new IllegalArgumentException(
                    "Argument 'genericsClass' ("
                    + Class.class.getName() + ") is null");
        }
        if (descendantClass == null) {
            throw new IllegalArgumentException(
                    "Argument 'descendantClass'("
                    + Class.class.getName() + ") is null");
        }

        List<ParameterizedType> ancestorTypeList =
                getAncestorTypeList(genericClass, descendantClass);

        ParameterizedType parameterizedType =
            ancestorTypeList.get(ancestorTypeList.size() - 1);
        // parameterizedType�̎��ۂ̌^������\�� Type �I�u�W�F�N�g�̔z��
        // ��FAbstractBLogic<P, R>�̌^������P��R�B
        Type[] actualTypes = parameterizedType.getActualTypeArguments();

        // �C���X�^���X�Ő錾���ꂽ�^�p�����[�^�����ۂ̌^�ɉ�������B
        if (index < 0 || index >= actualTypes.length) {
            throw new IllegalArgumentException(
                    "Argument 'index'(" + Integer.toString(index)
                    + ") is out of bounds of"
                    + " generics parameters");
        }

        // actualTypes[index]��index�Ԗڂ̌^�����B
        // ancestorList���^�p�����[�^�錾���Ă���N���X�̃��X�g�B
        return resolveTypeVariable(actualTypes[index], ancestorTypeList);
    }

    /**
     * ����̌^����^�p�����[�^��錾�����N���X�܂ł�
     * <code>ParameterizedType</code>�̃��X�g���擾����B
     *
     * @param <T> �^�p�����[�^�錾�������N���X�̌^�B
     * @param genericClass �^�p�����[�^�錾�������N���X�B
     * @param descendantClass <code>genericsClass</code>���p�����A
     *      ��̓I�Ȍ^�p�����[�^���w�肵���N���X�B
     * @return ����̌^����^�p�����[�^��錾�����N���X�܂ł�
     *      <code>ParameterizedType</code>�̃��X�g�B
     * @throws IllegalStateException <code>descendantClass</code>��
     *      �����Ō^�p�����[�^����̃N���X�Ƃ��Ďw�肳��Ă��Ȃ��ꍇ�B
     *      ����<code>genercClass</code>���^�p�����[�^��錾�����N���X�ł�
     *      �Ȃ������ꍇ�B
     */
    @SuppressWarnings("unchecked")
    protected static <T> List<ParameterizedType> getAncestorTypeList(
            Class<T> genericClass, Class<? extends T> descendantClass)
           throws IllegalStateException {
        List<ParameterizedType> ancestorTypeList =
            new ArrayList<ParameterizedType>();
        Class clazz = descendantClass;
        boolean isInterface = genericClass.isInterface();

        while (clazz != null) {
            Type type = clazz.getGenericSuperclass();
            if (checkParameterizedType(type, genericClass, ancestorTypeList)) {
                break;
            }

            // �^�p�����[�^��錾�����N���X���C���^�t�F�[�X�̏ꍇ�A
            // �C���^�t�F�[�X�ɂ��Ă��`�F�b�N���s���B
            if (!isInterface) {
                clazz = clazz.getSuperclass();
                continue;
            }
            if (checkInterfaceAncestors(
                    genericClass, ancestorTypeList, clazz)) {
                break;
            }

            // �N���X�̃C���^�t�F�[�X���ɁA�w�肵���C���^�t�F�[�X�����݂��Ȃ�����
            // �ꍇ�ɔ����āA�e�N���X���`�F�b�N�Ώۂɂ���B
            // ���󂱂̉ӏ���ʉ߂��邱�Ƃ͂Ȃ��Ǝv����B
            // �O�̂��߁A�`�F�b�N���c���Ă����B
            // ���R�́AGenerics�̃��t���N�V����API�ɂ��Ă͎������s��ł��邽�߂ł���B
            // ��肪����ꍇ�́A�폜���邱�ƁB
            clazz = clazz.getSuperclass();
        }

        // �^�p�����[�^�錾���Ă���N���X�̃C���X�^���X���擾�B
        // ��FAbstractBLogic<P, R>�N���X�B
        if (ancestorTypeList.isEmpty()) {
            throw new IllegalStateException(
                    "Argument 'genericClass'("
                    + genericClass.getName()
                    + ") does not declare type parameter");
        }

        // ���̉ӏ��̃`�F�b�N�ŗ�O���o��ꍇ�͂Ȃ��Ǝv���邪�A
        // �O�̂��߁A�`�F�b�N���c���Ă����B
        // ���R�́AGenerics�̃��t���N�V����API�ɂ��Ă͎������s��ł��邽�߂ł���B
        // ��肪����ꍇ�́A�폜���邱�ƁB
        ParameterizedType targetType =
            ancestorTypeList.get(ancestorTypeList.size() - 1);
        if (!targetType.getRawType().equals(genericClass)) {
            throw new IllegalStateException("Class("
                    + descendantClass.getName()
                    + ") is not concrete class of Class("
                    + genericClass.getName() + ")");
        }
        return ancestorTypeList;
    }

    /**
     * �C���^�t�F�[�X�^����^�p�����[�^��錾�����N���X�܂ł�
     * <code>ParameterizedType</code>�̃��X�g���擾����B
     *
     * @param <T> �^�p�����[�^��錾�����N���X�̌^�B
     *
     * @param genericClass �^�p�����[�^��錾�����N���X�B
     * @param ancestorTypeList <code>ParameterizedType</code>��
     *      �ǉ����郊�X�g�B
     * @param clazz �����Ώۂ̃C���^�t�F�[�X�^�B
     * @return �^�p�����[�^��錾�����N���X�����������ꍇ��<code>true</code>�B
     *      ������Ȃ������ꍇ��<code>false</code>�B
     */
    @SuppressWarnings("unchecked")
    protected static <T> boolean checkInterfaceAncestors(Class<T> genericClass,
            List<ParameterizedType> ancestorTypeList, Class clazz) {
        boolean genericTypeFound = false;
        Type[] interfaceTypes = clazz.getGenericInterfaces();
        for (Type interfaceType : interfaceTypes) {
            genericTypeFound = checkParameterizedType(
                    interfaceType, genericClass, ancestorTypeList);
            if (genericTypeFound) {
                return true;
            }
            Class[] interfaces = clazz.getInterfaces();
            for (Class interfaceClass : interfaces) {
                if (checkInterfaceAncestors(
                        genericClass, ancestorTypeList, interfaceClass)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * <code>Type</code>�^���`�F�b�N���A<code>ParameterizedType</code>
     * ���A�^�p�����[�^��錾�����N���X�̃T�u�N���X�ł���ꍇ�A���X�g�ɒǉ�����B
     *
     * @param <T> �^�p�����[�^��錾�����N���X�̌^�B
     *
     * @param type �����Ώۂ̌^�B
     * @param genericClass �^�p�����[�^��錾�����N���X�B
     * @param ancestorTypeList <code>ParameterizedType</code>��
     *      �ǉ����郊�X�g�B
     * @return <code>type</code>���^�p�����[�^��錾�����N���X�Ɠ����N���X�̏ꍇ�B
     */
    @SuppressWarnings("unchecked")
    protected static <T> boolean checkParameterizedType(
            Type type, Class<T> genericClass,
            List<ParameterizedType> ancestorTypeList) {
        if (!(type instanceof ParameterizedType)) {
            return false;
        }

        // ParameterizedType�̃C���X�^���X�ł���ꍇ�AParameterizedType
        // �ɃL���X�g����B
        ParameterizedType parameterlizedType = (ParameterizedType) type;

        // �C���^�t�F�[�X��Generics�̏ꍇ�A�قȂ�ParameterizedType��
        // �n����邩������Ȃ��̂Ń`�F�b�N�B
        // �������A����ł͈قȂ���̂��n����邱�Ƃ͂Ȃ��Ǝv����B
        // �O�̂��߁A�`�F�b�N���c���Ă����B
        // ���R�́AGenerics�̃��t���N�V����API�ɂ��Ă͎������s��ł��邽�߂ł���B
        // ��肪����ꍇ�́A�폜���邱�ƁB
        if (!genericClass.isAssignableFrom(
                (Class) parameterlizedType.getRawType())) {
            return false;
        }
        ancestorTypeList.add(parameterlizedType);

        // #getRawType�^�p�����[�^�錾�̂Ȃ�Type���擾����B
        if (parameterlizedType.getRawType().equals(genericClass)) {
            return true;
        }
        return false;
    }

    /**
     * �^�p�����[�^�̋�̓I��<code>Type</code>���擾����B
     *
     * @param type ��������K�v�̂���<code>Type</code>�C���X�^���X�B
     * @param ancestorTypeList <code>type</code>�̋�̓I�Ȍ^��
     *      �錾����Ă���\���̂���<code>ParameterizedType</code>�̃��X�g�B
     * @return ���s���̌^�ϐ��B
     * @throws IllegalStateException ����<code>type</code>��
     *      <code>Class</code>�^�A����сA
     *      <code>TypeVariable</code>�^�ł͂Ȃ��ꍇ�B
     *      ����<code>type</code>�����\�b�h�A
     *      �܂��́A�R���X�g���N�^�Ő錾����Ă���ꍇ�B
     *      ����<code>type</code>�̎��ۂ̌^��<code>Class</code>�ł͂Ȃ�
     *      (���C���h�J�[�h�A�z��)�ꍇ�B
     */
    @SuppressWarnings("unchecked")
    protected static Class resolveTypeVariable (Type type,
            List<ParameterizedType> ancestorTypeList)
            throws IllegalStateException {

        if (isNotTypeVariable(type)) {
            return getRawClass(type);
        }

        // TypeVariable:�^�ϐ����`����C���^�t�F�[�X�B
        TypeVariable targetType = (TypeVariable) type;
        Type actualType = null;
        for (int i = ancestorTypeList.size() - 1; i >= 0; i--) {
            ParameterizedType ancestorType = ancestorTypeList.get(i);

            // �^�p�����[�^���錾����Ă���N���X���擾
            GenericDeclaration declaration = targetType.getGenericDeclaration();
            if (!(declaration instanceof Class)) {
                throw new IllegalStateException("TypeVariable("
                        + targetType.getName() + " is not declared at Class "
                        + "(ie. is declared at Method or Constructor)");
            }

            // �c��N���X��Generics�̐錾���łȂ��ꍇ�͔�΂��B
            Class declaredClass = (Class) declaration;
            if (declaredClass != ancestorType.getRawType()) {
                continue;
            }

            // �^�p�����[�^�̐錾�������������āA������ꂽ�^�������擾�B
            // ��FConcreteAbstractBLogic<R,P> extends AbstractBLogic<P,R>
            //      �̂悤�ȏꍇ�ɐ�����type�ɑΉ�����p�����[�^�����o���B
            Type[] parameterTypes = declaredClass.getTypeParameters();
            int index = ArrayUtils.indexOf(parameterTypes, targetType);
            if (index == -1) {
                // ���̉ӏ��̃`�F�b�N�ŗ�O���o��ꍇ�͂Ȃ��Ǝv���邪�A
                // �O�̂��߁A�`�F�b�N���c���Ă����B
                // ���R�́AGenerics�̃��t���N�V����API�ɂ��Ă͎������s��ł��邽�߂ł���B
                // ��肪����ꍇ�́A�폜���邱�ƁB
                throw new IllegalStateException("Class("
                        + declaredClass.getName()
                        + ") does not declare TypeValidable("
                        + targetType.getName() + ")");
            }
            actualType = ancestorType.getActualTypeArguments()[index];
            if (log.isDebugEnabled()) {
                log.debug("resolved " + targetType.getName()
                        + " -> " + actualType);
            }

            if (isNotTypeVariable(actualType)) {
                return getRawClass(actualType);
            }
            targetType = (TypeVariable) actualType;
        }

        throw new IllegalStateException("Concrete type of Type(" + type
                + ") was not found in ancestorList("
                + ancestorTypeList + ")");
    }

    /**
     * ����<code>type</code>��<code>Class</code>�^
     * �ł��邩�A<code>TypeVariable</code>�^���𔻒肷��B
     *
     * @param type <code>Type</code>�C���X�^���X�B
     * @return ����<code>type</code>��
     *      <code>Class, ParameterizedType, GenericArrayType</code>�̏ꍇ
     *        <code>true</code>�B
     *      ����<code>type</code>��<code>TypeVariable</code>�̏ꍇ
     *        <code>false</code>�B
     * @throws IllegalStateException ����<code>type</code>��
     *      <code>Class</code>�A<code>ParameterizedType</code>�A
     *      <code>GenericArrayType</code>�A<code>TypeVariable</code>��
     *      ������ł��Ȃ��ꍇ�B
     */
    protected static boolean isNotTypeVariable(Type type)
        throws IllegalStateException {
        if (type instanceof Class) {
            return true;
        } else if (type instanceof TypeVariable) {
            return false;
        } else if (type instanceof ParameterizedType) {
            return true;
        } else if (type instanceof GenericArrayType) {
            return true;
        }
        throw new IllegalStateException("Type("
                + type + ") is not instance of "
                + TypeVariable.class.getName() + ", "
                + ParameterizedType.class.getName() + ", "
                + GenericArrayType.class.getName() + " nor "
                + Class.class.getName());
    }

    /**
     * ����<code>type</code>�̎��ۂ̌^��ԋp����B
     *
     * @param type <code>Type</code>�C���X�^���X�B
     * @return <code>Class</code>�C���X�^���X�B
     * @throws IllegalStateException ����<code>type</code>��
     *      <code>Class</code>�A<code>ParameterizedType</code>�A
     *      <code>GenericArrayType</code>�̂�����ł��Ȃ��ꍇ�B
     */
    @SuppressWarnings("unchecked")
    protected static Class getRawClass(Type type)
            throws IllegalStateException {
        if (type instanceof Class) {
            return (Class) type;
        } else if (type instanceof ParameterizedType) {
            return getRawClass(((ParameterizedType) type).getRawType());
        } else if (type instanceof GenericArrayType) {
            Type componentType =
                ((GenericArrayType) type).getGenericComponentType();
            Class componentClass = getRawClass(componentType);
            return Array.newInstance(componentClass, 0).getClass();
        }
        throw new IllegalStateException("Type("
                + type + ") is not instance of "
                + ParameterizedType.class.getName() + ", "
                + GenericArrayType.class.getName() + " nor "
                + Class.class.getName());
    }

}
