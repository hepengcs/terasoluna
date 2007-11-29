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

import org.apache.commons.jxpath.JXPathBeanInfo;
import org.apache.commons.jxpath.JXPathIntrospector;
import org.apache.commons.jxpath.ri.QName;
import org.apache.commons.jxpath.ri.model.NodePointer;
import org.apache.commons.jxpath.ri.model.beans.BeanPointerFactory;
import org.apache.commons.jxpath.ri.model.beans.NullPointer;

/**
 * Bean�|�C���^�t�@�N�g���̊g���N���X�B
 * {@link #getOrder()}�ɂ�BeanPointerFactory����Ƀ��[�h�����
 * �K�v������B
 *
 */
public class BeanPointerFactoryEx extends BeanPointerFactory {

    /**
     * Bean�|�C���^�t�@�N�g�����\�[�g����鏇�ԁB
     */
    public static final int BEAN_POINTER_FACTORY_EX_ORDER = 850;
    
    /**
     * �\�[�g�����擾����B
     * @return �\�[�g��
     */
    @Override
    public int getOrder() {
        return BEAN_POINTER_FACTORY_EX_ORDER;
    }
    
    /**
     * �m�[�h�|�C���^�𐶐�����B
     * @param name QName
     * @param bean �^�[�Q�b�g�ƂȂ�Bean
     * @param locale ���P�[��
     * @return Bean�|�C���^
     */
    @Override
    public NodePointer createNodePointer(
        QName name, Object bean, Locale locale) {
        JXPathBeanInfo bi = JXPathIntrospector.getBeanInfo(bean.getClass());
        return new BeanPointerEx(name, bean, bi, locale);
    }

    /**
     * �m�[�h�|�C���^�𐶐�����B
     * @param parent �e�̃|�C���^
     * @param name QName
     * @param bean �^�[�Q�b�g�ƂȂ�Bean
     * @return Bean�|�C���^
     */
    @Override
    public NodePointer createNodePointer(
        NodePointer parent, QName name, Object bean) {
        if (bean == null) {
            return new NullPointer(parent, name);
        }
        JXPathBeanInfo bi = JXPathIntrospector.getBeanInfo(bean.getClass());
        return new BeanPointerEx(parent, name, bean, bi);
    }
}
