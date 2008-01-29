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

import org.apache.commons.beanutils.PropertyUtilsBean;

/**
 * {@link GenericPropertyUtil}���e�X�g���邽�߂�{@link PropertyUtilsBean}�����N���X�B
 * 
 * @see org.apache.commons.beanutils.PropertyUtilsBean
 * @see jp.terasoluna.fw.util.GenericPropertyUtilTest
 */
public class GenericPropertyUtil_PropertyUtilsBeanStub01 extends PropertyUtilsBean {

    /**
     * ���s����IllegalAccessException���X���[����
     * 
     * @param bean
     *            Bean for which a property descriptor is requested
     * @param name
     *            Possibly indexed and/or nested name of the property for which
     *            a property descriptor is requested
     */
    @Override
    public PropertyDescriptor getPropertyDescriptor(Object bean, String name)
            throws IllegalAccessException, InvocationTargetException,
            NoSuchMethodException {
        throw new IllegalAccessException();
    }


}
