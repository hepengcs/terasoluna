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
import org.apache.commons.jxpath.ri.QName;
import org.apache.commons.jxpath.ri.model.NodePointer;
import org.apache.commons.jxpath.ri.model.beans.BeanPointer;
import org.apache.commons.jxpath.ri.model.beans.PropertyPointer;

/**
 * Bean�|�C���^�̊g���N���X�B
 */
public class BeanPointerEx extends BeanPointer {

    /**
     * �V���A���o�[�W����ID�B
     */
    private static final long serialVersionUID = -7414445615036653661L;
    
    /**
     * �^�[�Q�b�g��Bean�̏��B
     */
    private JXPathBeanInfo beanInfo;
    
    /**
     * �R���X�g���N�^�B
     * @param name QName
     * @param bean �^�[�Q�b�g��Bean
     * @param beanInfo �^�[�Q�b�g��Bean�̏��
     * @param locale ���P�[��
     */
    public BeanPointerEx(QName name, Object bean, JXPathBeanInfo beanInfo,
            Locale locale) {
        super(name, bean, beanInfo, locale);
        this.beanInfo = beanInfo;
    }

    /**
     * �R���X�g���N�^�B
     * @param parent �eBean�|�C���^
     * @param name QName
     * @param bean �^�[�Q�b�g��Bean
     * @param beanInfo �^�[�Q�b�g��Bean�̏��
     */
    public BeanPointerEx(NodePointer parent, QName name, Object bean,
            JXPathBeanInfo beanInfo) {
        super(parent, name, bean, beanInfo);
        this.beanInfo = beanInfo;
    }

    /**
     * �v���p�e�B�|�C���^���擾����B
     * @return Bean�v���p�e�B�|�C���^
     */
    @Override
    public PropertyPointer getPropertyPointer() {
        return new BeanPropertyPointerEx(this, beanInfo);
    }
}
