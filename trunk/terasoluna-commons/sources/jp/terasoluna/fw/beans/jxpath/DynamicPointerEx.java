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
import org.apache.commons.jxpath.ri.QName;
import org.apache.commons.jxpath.ri.model.NodePointer;
import org.apache.commons.jxpath.ri.model.beans.PropertyPointer;
import org.apache.commons.jxpath.ri.model.dynamic.DynamicPointer;

/**
 * DynamicPointer�̊g���N���X�B
 */
public class DynamicPointerEx extends DynamicPointer {

    /**
     * �V���A���o�[�W����ID�B
     */
    private static final long serialVersionUID = 5068434119249899985L;
    
    /**
     * �v���p�e�B�n���h���B
     */
    private DynamicPropertyHandler handler;
    
    /**
     * �R���X�g���N�^�B
     * @param name QName
     * @param bean �^�[�Q�b�g��Map
     * @param handler �v���p�e�B�n���h��
     * @param locale ���P�[��
     */
    public DynamicPointerEx(QName name, Object bean,
            DynamicPropertyHandler handler, Locale locale) {
        super(name, bean, handler, locale);
        this.handler = handler;
    }

    /**
     * �R���X�g���N�^�B
     * @param parent �e��Map�|�C���^
     * @param name QName
     * @param bean �^�[�Q�b�g��Map
     * @param handler �v���p�e�B�n���h��
     */
    public DynamicPointerEx(NodePointer parent, QName name, Object bean,
            DynamicPropertyHandler handler) {
        super(parent, name, bean, handler);
        this.handler = handler;
    }
    
    /**
     * �v���p�e�B�|�C���^���擾����B
     * @return Map�p�v���p�e�B�|�C���^
     */
    @Override
    public PropertyPointer getPropertyPointer() {
        return new DynamicPropertyPointerEx(this, handler);
    }
}
