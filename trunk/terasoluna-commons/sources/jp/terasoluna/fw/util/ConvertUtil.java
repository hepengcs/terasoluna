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
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.PropertyUtils;


/**
 * �^�ϊ����s�����߂̃��[�e�B���e�B�N���X�B
 * 
 */
public class ConvertUtil {

    /**
     * <code>class</code>�t�B�[���h��\���t�B�[���h��
     */
    public static final String CLASS_FIELDNAME = "class";

    /**
     * �I�u�W�F�N�g��z��ɕϊ�����B
     * <ul>
     *   <li><code>null</code>�̏ꍇ - <code>Object[0]</code>��ԋp</li>
     *   <li><code>Object[]</code>�̏ꍇ - ���̂܂ܕԋp</li>
     *   <li><code>Collection</code>�̏ꍇ - �z��ɕϊ����ĕԋp</li>
     *   <li>����ȊO�̏ꍇ - �v�f��1���z��Ƃ��ĕԋp</li>
     * </ul>
     * 
     * <p>
     * �^�ۏႳ�ꂽ�z�񂪗~�����ꍇ�́A{@link #toList(Object, Class)}��
     * �g�p���āA���L�̂悤�ɂ��邱�ƁB
     * <code><pre>
     * List<String> list = ConvertUtil.toList(value, String.class);
     * String[] array = list.toArray(new String[list.size()]);
     * </pre></code>
     * </p>
     * 
     * @param obj �I�u�W�F�N�g�B
     * @return �I�u�W�F�N�g��ϊ������z��B
     */
    public static Object[] toArray(Object obj) {
        if (obj == null) {
            return new Object[0];
        } else if (obj.getClass().isArray()) {
            return (Object[]) obj;
        } else if (obj instanceof Collection) {
            return ((Collection) obj).toArray();
        }
        return new Object[]{ obj };
    }

    /**
     * �I�u�W�F�N�g�����X�g�ɕϊ�����B
     * <ul>
     *   <li><code>null</code>�̏ꍇ - �v�f�������Ȃ�<code>T</code>�^�̃��X�g�Ƃ��ĕԋp</li>
     *   <li><code>Object[]</code>�̏ꍇ - <code>T</code>�^�̃��X�g�ɕϊ����ĕԋp</li>
     *   <li><code>Collection</code>�̏ꍇ - <code>T</code>�^�̃��X�g�Ƃ��ĕԋp</li>
     *   <li>����ȊO�̏ꍇ - �v�f��1����<code>T</code>�^�̃��X�g�Ƃ��ĕԋp</li>
     * </ul>
     * 
     * @param <E> �ԋp���郊�X�g�̗v�f��\���^�B
     * @param obj �I�u�W�F�N�g�B
     * @param elementClass �ԋp���郊�X�g�̗v�f��\���^�B 
     * @return �I�u�W�F�N�g��ϊ��������X�g�B
     * @throws IllegalArgumentException ����<code>clazz</code>��
     *           <code>null</code>�̏ꍇ
     *           <code>obj</code>�܂��́A���̗v�f��<code>T</code>�^
     *           �ł͂Ȃ��ꍇ�B
     */
    @SuppressWarnings("unchecked")
    public static <E> List<E> toList(Object obj, Class<E> elementClass)
            throws IllegalArgumentException {
        if (elementClass == null) {
            throw new IllegalArgumentException("Argument 'elementClass' ("
                    +  Class.class.getName() + ") is null");
        }
        
        Object[] array = toArray(obj);
        List<E> result = new ArrayList<E>();
        for (Object element : array) {
            if (element != null
                    && !elementClass.isAssignableFrom(element.getClass())) {
                String message = "Unable to cast '"
                    + element.getClass().getName()
                    + "' to '" + elementClass.getName() + "'";
                throw new IllegalArgumentException(
                        message, new ClassCastException(message));
            }
            result.add((E) element);
        }
        return result;
    }

    /**
     * �I�u�W�F�N�g��<code>T</code>�^�ɕϊ�����B
     * 
     * @param <T> �ϊ���̌^�B
     * @param obj �I�u�W�F�N�g�B
     * @param clazz �ϊ���̌^�B
     * @return �ϊ���̃I�u�W�F�N�g�B
     * @throws IllegalArgumentException �ϊ��Ɏ��s�����ꍇ�B
     */
    public static <T> T convert(Object obj, Class<T> clazz)
            throws IllegalArgumentException {
        return convert(obj, clazz, true);
    }

    /**
     * <code>null</code>�ł͂Ȃ��I�u�W�F�N�g��
     * <code>T</code>�^�ɕϊ�����B
     * <p>
     * �v���~�e�B�u�^�ɑΉ�����l�Ȃǂ̕ϊ��ɗ��p����B
     * </p>
     * 
     * @param <T> �ϊ���̌^�B
     * @param obj �I�u�W�F�N�g�B
     * @param clazz �ϊ���̌^�B
     * @return �ϊ���̃I�u�W�F�N�g�B
     * @throws IllegalArgumentException �ϊ��Ɏ��s�����ꍇ�B
     *      ����<code>obj</code>��<code>null</code>�̏ꍇ�B
     */
    public static <T> T convertIfNotNull(Object obj, Class<T> clazz)
            throws IllegalArgumentException {
        return convert(obj, clazz, false);
    }
    
    /**
     * �I�u�W�F�N�g��<code>T</code>�^�ɕϊ�����B
     * <p>
     * <ul>
     *  <li><code>allowsNull</code>��<code>false</code>����
     *        <code>obj</code>��<code>null</code> - ��O���X���[�B
     *  <li><code>allowsNull</code>��<code>true</code>����
     *        <code>obj</code>��<code>null</code> - <code>null</code>��ԋp�B
     *  <li><code>obj</code>��<code>clazz</code>�^ - ���̂܂ܕԋp�B
     *  <li><code>obj</code>��<code>clazz</code>�^�ł͂Ȃ�
     *        - <code>ConvertUtils</code>���g�p���ēK�؂Ȍ^�ɕϊ����ĕԋp�B
     * </ul>
     * </p>
     * 
     * @param <T> �ϊ���̌^�B
     * @param obj �I�u�W�F�N�g�B
     * @param clazz �ϊ���̌^�B
     * @param allowsNull ����<code>obj</code>��<code>null</code>��
     *      �ꍇ�����e���邩�ǂ����B
     * @return �ϊ���̃I�u�W�F�N�g�B
     * @throws IllegalArgumentException ����<code>clazz</code>��
     *      <code>null</code>�̏ꍇ�B
     *      ����<code>allowsNull</code>��<code>false</code>����
     *        ����<code>obj</code>��<code>null</code>�̏ꍇ�B
     *      �ϊ��Ɏ��s�����ꍇ�B
     */
    @SuppressWarnings("unchecked")
    public static <T> T convert(
            Object obj, Class<T> clazz, boolean allowsNull)
            throws IllegalArgumentException {

        if (clazz == null) {
            throw new IllegalArgumentException("Argument 'clazz' ("
                    +  Object.class.getName() + ") is null");
        }

        if (obj == null) {
            if (!allowsNull) {
                String message =
                    "Unable to cast 'null' to '" + clazz.getName() + "'";
                throw new IllegalArgumentException(
                        message, new ClassCastException(message));
            }
            return null;
        }

        if (clazz.isAssignableFrom(obj.getClass())) {
            return (T) obj;
        }
        
        Object result = null;
        try {
            result = ConvertUtils.convert(obj.toString(), clazz);
        } catch (ConversionException e) {
            throw new IllegalArgumentException(e);
        }
        return (T) result;
    }

    /**
     * ����<code>value</code>���v���~�e�B�u�^�̔z��ł���΁A
     * �v�f��<code>String</code>�ɕϊ�����<code>List</code>�Ɋi�[
     * ���郆�[�e�B���e�B���\�b�h�B
     * 
     * @param value �v���~�e�B�u�^�̔z��B
     * @return �������v���~�e�B�u�^�̔z��̏ꍇ�A�S�v�f���i�[����<code>List</code>�B
     *          ����ȊO�̏ꍇ�͈�����<code>value</code>���̂��́B
     */
    public static Object convertPrimitiveArrayToList(Object value) {
        if (value == null) {
            return value;
        }
        Class type = value.getClass().getComponentType();
        
        // value���z��^�ł͂Ȃ��ꍇ
        if (type == null) {
            return value;
        }
        
        // �z��̗v�f���v���~�e�B�u�^�ł͂Ȃ��ꍇ
        if (!type.isPrimitive()) {
            return value;
        }
        
        List<Object> list = new ArrayList<Object>();
        
        if (value instanceof boolean[]) {
            for (boolean data : (boolean[]) value) {
                // String�^�ɕϊ�����K�v�͂Ȃ��B
                list.add(data);
            }
        } else if (value instanceof byte[]) {
            for (byte data : (byte[]) value) {
                list.add(Byte.toString(data));
            }
        } else if (value instanceof char[]) {
            for (char data : (char[]) value) {
                list.add(Character.toString(data));
            }
        } else if (value instanceof double[]) {
            for (double data : (double[]) value) {
                list.add(Double.toString(data));
            }
        } else if (value instanceof float[]) {
            for (float data : (float[]) value) {
                list.add(Float.toString(data));
            }
        } else if (value instanceof int[]) {
            for (int data : (int[]) value) {
                list.add(Integer.toString(data));
            }
        } else if (value instanceof long[]) {
            for (long data : (long[]) value) {
                list.add(Long.toString(data));
            }
        } else if (value instanceof short[]) {
            for (short data : (short[]) value) {
                list.add(Short.toString(data));
            }
        } 
        return list;
    }

    /**
     * �I�u�W�F�N�g�̃R���N�V�����܂��͔z����}�b�v�̃��X�g�ɕϊ�����B
     * <p>
     * ���X�g�̗v�f�ł���}�b�v��{@link #CLASS_FIELDNAME}�������S�Ă�
     * �t�B�[���h�ɂ��āA�t�B�[���h�����L�[�Ƃ���l�����B
     * �������A�t�B�[���h�����啶���Ŏn�܂��Ă���ꍇ�A�ŏ���1������
     * �������ɕϊ������̂Œ��ӂ��邱�ƁB
     * </p>
     * <ul>
     *   <li><code>null</code>�̏ꍇ - �v�f�������Ȃ��}�b�v�̃��X�g�Ƃ��ĕԋp</li>
     *   <li><code>Object[]</code>�̏ꍇ - �}�b�v�̃��X�g�ɕϊ����ĕԋp</li>
     *   <li><code>Collection</code>�̏ꍇ - �}�b�v�̃��X�g�Ƃ��ĕԋp</li>
     *   <li>����ȊO�̏ꍇ - �v�f��1���}�b�v�̃��X�g�Ƃ��ĕԋp</li>
     * </ul>
     * @param obj �I�u�W�F�N�g�B
     * @return �I�u�W�F�N�g��ϊ������}�b�v�̃��X�g�B
     * @throws IllegalArgumentException �ϊ����ɗ\�����Ȃ���O�����������ꍇ�B
     */
    @SuppressWarnings("unchecked")
    public static List<Map<String, Object>> toListOfMap(Object obj)
            throws IllegalArgumentException {
        Object[] array = ConvertUtil.toArray(obj);
        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        for (Object object : array) {
            
            Map<String, Object> map = null;
            if (object instanceof Map) {
                map = (Map) object;
            } else {
                try {
                    map = PropertyUtils.describe(object);
                } catch (IllegalAccessException e) {
                    throw new IllegalArgumentException(e);
                } catch (InvocationTargetException e) {
                    throw new IllegalArgumentException(e);
                } catch (NoSuchMethodException e) {
                    throw new IllegalArgumentException(e);
                }
            }

            map.remove(CLASS_FIELDNAME);
            result.add(map);
        }
    
        return result;
    }
}
