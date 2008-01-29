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

import org.apache.commons.jxpath.JXPathBeanInfo;
import org.apache.commons.jxpath.ri.model.NodePointer;
import org.apache.commons.jxpath.ri.model.beans.BeanPropertyPointer;

/**
 * null�l���������߂�Bean�v���p�e�B�|�C���^�g���N���X�B
 * 
 * <p>�f�t�H���g��Bean�v���p�e�B�|�C���^�ł́A
 * final�w�肳��Ă��Ȃ��N���X�iObject,Date�AArrayList�Aetc�j�̑�����
 * null�������Ă����ꍇ�A�l���Ȃ����̂Ƃ��Ĉ�����B
 * null���擾�������ꍇ�A�{�N���X���g�p����K�v������B</p>
 * 
 * @see jp.terasoluna.fw.beans.jxpath.BeanPointerFactoryEx
 */
public class BeanPropertyPointerEx extends BeanPropertyPointer {

    /**
     * �V���A���o�[�W����ID�B
     */
    private static final long serialVersionUID = -4617365154553497991L;

    /**
     * �R���X�g���N�^�B
     * @param parent �e�ƂȂ�Bean�|�C���^
     * @param beanInfo �^�[�Q�b�g��Bean�̏��
     */
    public BeanPropertyPointerEx(NodePointer parent, JXPathBeanInfo beanInfo) {
        super(parent, beanInfo);
    }

    /**
     * �v�f�����擾����B
     * @return �v�f��
     */
    @Override
    public int getLength() {
        int length = super.getLength();
        
        // �v�f�̒l��null�̏ꍇ�A�v�f�����P�Ƃ���
        if (length == 0 && getBaseValue() == null) { 
            return 1;
        }
        return length;
    }
    
    /**
     * �z�񂩂ǂ������f����B
     * null�̏ꍇ�A�z��Ƃ݂͂Ȃ��Ȃ��B
     * @return �z��̏ꍇ�Atrue��Ԃ��B����ȊO��false��Ԃ��B
     */
    @Override
    public boolean isCollection() {
        if (getBaseValue() == null) {
        	return false;
        }
        return super.isCollection();
    }
}
