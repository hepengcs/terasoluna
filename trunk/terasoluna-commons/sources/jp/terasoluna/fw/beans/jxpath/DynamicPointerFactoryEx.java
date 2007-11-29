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

package jp.terasoluna.fw.beans.jxpath;

import java.util.Locale;

import org.apache.commons.jxpath.DynamicPropertyHandler;
import org.apache.commons.jxpath.JXPathBeanInfo;
import org.apache.commons.jxpath.JXPathIntrospector;
import org.apache.commons.jxpath.ri.QName;
import org.apache.commons.jxpath.ri.model.NodePointer;
import org.apache.commons.jxpath.ri.model.beans.NullPointer;
import org.apache.commons.jxpath.ri.model.dynamic.DynamicPointerFactory;
import org.apache.commons.jxpath.util.ValueUtils;

/**
 * Map�p�|�C���^�t�@�N�g���̊g���N���X�B
 * {@link #getOrder()}�ɂ�DynamicPointerFactory����Ƀ��[�h�����
 * �K�v������B
 */
public class DynamicPointerFactoryEx extends DynamicPointerFactory {

    /**
     * Map�p�|�C���^�t�@�N�g�����\�[�g����鏇�ԁB
     */
    public static final int DYNAMIC_POINTER_FACTORY_EX_ORDER = 750;
    
    /**
     * �R���X�g���N�^�B
     */
    public DynamicPointerFactoryEx() {
        super();
    }

    /**
     * �\�[�g�����擾����B
     * @return �\�[�g��
     */
    @Override
    public int getOrder() {
        return DYNAMIC_POINTER_FACTORY_EX_ORDER;
    }
    
    /**
     * �m�[�h�|�C���^�𐶐�����B
     * @param name QName
     * @param bean �^�[�Q�b�g��Map
     * @param locale ���P�[��
     * @return Map�p�|�C���^
     */
    @Override
    public NodePointer createNodePointer(
        QName name, Object bean, Locale locale) {
        JXPathBeanInfo bi = JXPathIntrospector.getBeanInfo(bean.getClass());
        if (bi.isDynamic()) {
            DynamicPropertyHandler handler =
                ValueUtils.getDynamicPropertyHandler(
                        bi.getDynamicPropertyHandlerClass());
            return new DynamicPointerEx(name, bean, handler, locale);
        }
        return null;
    }

    /**
     * �m�[�h�|�C���^�𐶐�����B
     * @param parent �eMap�|�C���^
     * @param name QName
     * @param bean �^�[�Q�b�g��Map
     * @return Map�p�|�C���^
     */
    @Override
    public NodePointer createNodePointer(
        NodePointer parent, QName name, Object bean) {
        if (bean == null) {
            return new NullPointer(parent, name);
        }

        JXPathBeanInfo bi = JXPathIntrospector.getBeanInfo(bean.getClass());
        if (bi.isDynamic()) {
            DynamicPropertyHandler handler =
                ValueUtils.getDynamicPropertyHandler(
                        bi.getDynamicPropertyHandlerClass());
            return new DynamicPointerEx(parent, name, bean, handler);
        }
        return null;
    }
}
