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

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * <code>JavaBean</code>�̃v���p�e�B��
 * <code>Generics</code>���������߂̃��[�e�B���e�B�N���X�B
 *
 */
public class GenericPropertyUtil {

    /**
     * ���O�N���X�B
     */
    private static final Log log =
        LogFactory.getLog(GenericPropertyUtil.class);

    /**
     * <code>JavaBean</code>��
     * <code>Collection</code>�^�v���p�e�B�̗v�f�̌^���擾����B
     * <p>
     * <h5>�擾��</h5>
     * <pre><code>
     * public class Bean {
     *     private List&lt;String&gt; list;
     *     public List&lt;String&gt; getList() {
     *         return this.list;
     *     }
     * }
     * </code></pre>
     * ��L�̂悤��<code>Bean</code>�ɑ΂��āA�ȉ��̂悤�Ɏg�p����ƁA
     * String.class���擾�ł���B
     * <pre><code>
     * Bean bean = new Bean();
     * Class elementType =
     *     GenericCollectionUtil.resolveCollectionType(
     *         bean, "list");
     * </code></pre>
     *
     * @param bean <code>JavaBean</code>�C���X�^���X�B
     * @param name <code>Collection</code>�^�v���p�e�B�̖��O�B
     * @return <code>Collection</code>�̗v�f�̌^�B
     *      ����ł��Ȃ��ꍇ��<code>Object</code>�^���ԋp�����B
     * @throws IllegalArgumentException ����<code>bean</code>��
     *      <code>null</code>�̏ꍇ�B����<code>name</code>��
     *      <code>null</code>�A�󕶎��A�󔒕�����̏ꍇ�B
     *      <code>JavaBean</code>�̃v���p�e�B��
     *      �擾���\�b�h���擾�ł��Ȃ������ꍇ
     * @throws IllegalStateException �w�肳�ꂽ�v���p�e�B��<code>Collection</code>�����N���X
     *      �ł͂Ȃ��ꍇ�B
     */
    @SuppressWarnings("unchecked")
    public static Class resolveCollectionType(Object bean, String name)
            throws IllegalArgumentException, IllegalStateException {
        return resolveType(bean, name, Collection.class, 0);
    }

    /**
     * <code>JavaBean</code>��
     * <code>Generics</code>�^�v���p�e�B�Ŏw�肳�ꂽ�^���擾����B
     * <p>
     * <h5>�擾��</h5>
     * <pre><code>
     * public class Bean {
     *     private Map&lt;String, Boolean&gt; map;
     *     public Map&lt;String, Boolean&gt; getMap() {
     *         return this.map;
     *     }
     * }
     * </code></pre>
     * ��L�̂悤��<code>Bean</code>�ɑ΂��āA�ȉ��̂悤�Ɏg�p����ƁA
     * String.class���擾�ł���B
     * <pre><code>
     * Bean bean = new Bean();
     * Class keyType =
     *     GenericCollectionUtil.resolveType(
     *         bean, "map", Map.class, 0);
     * </code></pre>
     *
     * @param bean <code>JavaBean</code>�C���X�^���X�B
     * @param name <code>Generics</code>�^�v���p�e�B�̖��O�B
     * @param genericClass <code>Generics</code>�^�v���p�e�B��
     *      �^��`���s���Ă���N���X�B
     * @param index �^�p�����[�^�̐錾�����B
     * @return <code>Generics</code>�^�v���p�e�B�Ŏw�肳�ꂽ�^�B
     *      ����ł��Ȃ��ꍇ��<code>Object</code>�^���ԋp�����B
     * @throws IllegalArgumentException ����<code>bean</code>��
     *      <code>null</code>�̏ꍇ�B����<code>name</code>��
     *      <code>null</code>�A�󕶎��A�󔒕�����̏ꍇ�B
     *      ����<code>genericClass</code>��<code>null</code>�̏ꍇ�B
     *      ����<code>index</code>��<code>0</code>��菬�����A�܂��́A
     *      �錾���ꂽ�^�p�����[�^���ȏ�̏ꍇ�B
     *      <code>JavaBean</code>�̃v���p�e�B��
     *      �擾���\�b�h���擾�ł��Ȃ������ꍇ
     * @throws IllegalStateException �^�p�����[�^��<code>WildCardType</code>�ł���ꍇ�B
     */
    @SuppressWarnings("unchecked")
    public static Class resolveType(Object bean, String name,
            Class genericClass, int index)
            throws IllegalArgumentException, IllegalStateException {
        if (bean == null) {
            throw new IllegalArgumentException(
                    "Argument 'bean' ("
                    + Object.class.getName() + " is null");
        }
        if (StringUtils.isBlank(name)) {
            throw new IllegalArgumentException(
                    "Argument 'name' ("
                    + String.class.getName() + " is empty");
        }

        Method method = getMethod(bean, name);
        return resolveType(
                genericClass, method.getReturnType(),
                method.getGenericReturnType(), index);
    }

    /**
     * <code>JavaBean</code>�̃v���p�e�B�̎擾���\�b�h��
     * �擾����B
     *
     * @param bean <code>JavaBean</code>�C���X�^���X�B
     * @param name <code>Generics</code>�^�v���p�e�B�̖��O�B
     * @return <code>JavaBean</code>�ɒ�`���ꂽ�v���p�e�B�̎擾���\�b�h�B
     * @throws IllegalArgumentException <code>JavaBean</code>�̃v���p�e�B��
     * �擾���\�b�h���擾�ł��Ȃ������ꍇ�B
     */
    protected static Method getMethod(Object bean, String name)
            throws IllegalArgumentException {
        PropertyDescriptor descriptor = null;
        try {
            descriptor = PropertyUtils.getPropertyDescriptor(bean, name);
        } catch (IllegalAccessException e) {
            throw new IllegalArgumentException("Failed to detect getter for "
                    + bean.getClass().getName() + "#" + name, e);
        } catch (InvocationTargetException e) {
            throw new IllegalArgumentException("Failed to detect getter for "
                    + bean.getClass().getName() + "#" + name, e);
        } catch (NoSuchMethodException e) {
            throw new IllegalArgumentException("Failed to detect getter for "
                    + bean.getClass().getName() + "#" + name, e);
        }
        Method method = null;
        if (descriptor != null) {
            method = descriptor.getReadMethod();
        }
        if (method == null) {
            throw new IllegalArgumentException(bean.getClass().getName()
                    + " has no getter for property " + name);
        }
        return method;
    }

    /**
     * �t�B�[���h�A�܂��́A���\�b�h�̏�������
     * <code>Generics</code>�^�Ŏw�肳�ꂽ�^���擾����B
     *
     * @param genericClass <code>Generics</code>�^�v���p�e�B��
     *      �^��`���s���Ă���N���X�B
     * @param clazz ��̓I�Ȍ^�p�����[�^���w�肵���N���X�B
     * @param type ��̓I�Ȍ^�p�����[�^���w�肵���N���X�̃C���X�^���X��
     *      <code>Type</code>�C���X�^���X�B
     * @param index �^�p�����[�^�̐錾�����B
     * @return <code>Generics</code>�^�Ŏw�肳�ꂽ�^�B
     *      ����ł��Ȃ��ꍇ��<code>Object</code>�^���ԋp�����B
     * @throws IllegalArgumentException ����<code>genericClass</code>��
     *      <code>null</code>�̏ꍇ�B
     *      ����<code>clazz</code>��<code>null</code>�̏ꍇ�B
     *      ����<code>index</code>��<code>0</code>��菬�����A�܂��́A
     *      �錾���ꂽ�^�p�����[�^���ȏ�̏ꍇ�B
     * @throws IllegalStateException �^�p�����[�^��<code>WildCardType</code>�ł���ꍇ�B
     */
    @SuppressWarnings("unchecked")
    protected static Class resolveType(Class genericClass, Class clazz,
            Type type, int index)
            throws IllegalArgumentException, IllegalStateException {
        if (genericClass == null) {
            throw new IllegalArgumentException(
                    "Argument 'genericsClass' ("
                    + Class.class.getName() + ") is null");
        }
        if (clazz == null || !genericClass.isAssignableFrom(clazz)) {
            throw new IllegalStateException(
                    genericClass+ " is not assignable from " + clazz);
        }

        List<ParameterizedType> ancestorTypeList = null;
        try {
            ancestorTypeList =
                GenericsUtil.getAncestorTypeList(genericClass, clazz);
        } catch (IllegalStateException e) {
            if (log.isTraceEnabled()) {
                log.trace(e.getMessage());
            }
        }
        if (ancestorTypeList == null) {
            ancestorTypeList = new ArrayList<ParameterizedType>();
        }
        if (type instanceof ParameterizedType) {
            ancestorTypeList.add(0, (ParameterizedType) type);
        }
        if (ancestorTypeList.size() <= 0) {
            throw new IllegalStateException(
                    "No parameterizedType was detected.");
        }
        ParameterizedType parameterizedType =
            ancestorTypeList.get(ancestorTypeList.size() - 1);
        Type[] actualTypes = parameterizedType.getActualTypeArguments();

        // �C���X�^���X�Ő錾���ꂽ�^�p�����[�^�����ۂ̌^�ɉ�������B
        if (index < 0 || index >= actualTypes.length) {
            throw new IllegalArgumentException(
                    "Argument 'index'(" + Integer.toString(index)
                    + ") is out of bounds of"
                    + " generics parameters");
        }

        Class resolved = Object.class;
        try {
            resolved = GenericsUtil.resolveTypeVariable(
                    actualTypes[index], ancestorTypeList);
        } catch (IllegalStateException e) {
            if (log.isTraceEnabled()) {
                log.trace(e.getMessage());
            }
        }
        return resolved;
    }

}
