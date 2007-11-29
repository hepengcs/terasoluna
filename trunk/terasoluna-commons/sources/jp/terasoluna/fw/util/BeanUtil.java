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

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.DynaProperty;
import org.apache.commons.beanutils.PropertyUtils;


/**
 *  Bean�֘A�̃��[�e�B���e�B�N���X�B
 *
 * @see jp.terasoluna.fw.util.PropertyAccessException
 *
 */
public final class BeanUtil {

    /**
     * �w�肵��JavaBean�̃v���p�e�B�ɒl���i�[����B
     *
     * @param bean
     *            �l�̊i�[��Ƃ���JavaBean
     * @param property
     *            JavaBean�̃v���p�e�B
     * @param value
     *            �i�[����l
     * @throws PropertyAccessException
     *             �l�i�[���ɔ���������O�����b�v������O
     */
    public static void setBeanProperty(Object bean, String property,
            Object value) throws PropertyAccessException {

        try {
            // ���͒l�ݒ胁�\�b�h�����s
            PropertyUtils.setProperty(bean, property, value);
        } catch (IllegalArgumentException e) {
            throw new PropertyAccessException(e);
        } catch (IllegalAccessException e) {
            throw new PropertyAccessException(e);
        } catch (InvocationTargetException e) {
            throw new PropertyAccessException(e.getTargetException());
        } catch (NoSuchMethodException e) {
            throw new PropertyAccessException(e);
        }
    }

    /**
     * �w�肵��JavaBean�̃v���p�e�B����l���擾����B
     *
     * @param bean
     *            �l�̎擾���Ƃ���JavaBean
     * @param property
     *            JavaBean�̃v���p�e�B
     * @return value �擾�����l
     * @throws PropertyAccessException
     *             �l�擾���ɔ���������O�����b�v������O
     */
    public static Object getBeanProperty(Object bean, String property)
            throws PropertyAccessException {

        Object value = null;
        try {
            value = PropertyUtils.getProperty(bean, property);
        } catch (IllegalArgumentException e) {
            throw new PropertyAccessException(e);
        } catch (IllegalAccessException e) {
            throw new PropertyAccessException(e);
        } catch (InvocationTargetException e) {
            throw new PropertyAccessException(e.getTargetException());
        } catch (NoSuchMethodException e) {
            throw new PropertyAccessException(e);
        }
        return value;
    }
    
    /**
     * �w�肵��JavaBean�̃v���p�e�B����^���擾����B
     * @param bean
     *            �l�̎擾���Ƃ���JavaBean
     * @param property
     *            JavaBean�̃v���p�e�B
     * @return �����̃N���X�B
     * @throws PropertyAccessException �l�擾���ɔ���������O�����b�v������O
     */
    public static Class getBeanPropertyType(Object bean, String property) 
        throws PropertyAccessException {
        try {
            Class type = null;
            if (bean instanceof DynaBean) {
                DynaProperty descriptor = ((DynaBean) bean).getDynaClass()
                    .getDynaProperty(property);
                if (descriptor != null){
                    type = descriptor.getType();
                }
            }
            else{
                type = PropertyUtils.getPropertyType(bean, property);
            }
            return type;
        } catch (IllegalArgumentException e) {
            throw new PropertyAccessException(e);
        } catch (IllegalAccessException e) {
            throw new PropertyAccessException(e);
        } catch (InvocationTargetException e) {
            throw new PropertyAccessException(e);
        } catch (NoSuchMethodException e) {
            throw new PropertyAccessException(e);
        }
    }
}

