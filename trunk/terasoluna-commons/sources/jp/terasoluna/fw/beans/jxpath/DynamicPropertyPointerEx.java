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

import org.apache.commons.jxpath.DynamicPropertyHandler;
import org.apache.commons.jxpath.ri.model.NodePointer;
import org.apache.commons.jxpath.ri.model.dynamic.DynamicPropertyPointer;
import org.apache.commons.jxpath.util.ValueUtils;

/**
 * null�l���������߂�Map�p�v���p�e�B�|�C���^�g���N���X�B
 * 
 * <p>�f�t�H���g��Map�v���p�e�B�|�C���^�ł́A
 * Map�̒l��null�������Ă����ꍇ�A�l���Ȃ����̂Ƃ��Ĉ�����B
 * null���擾�������ꍇ�A�{�N���X���g�p����K�v������B</p>
 * 
 */
public class DynamicPropertyPointerEx extends DynamicPropertyPointer {

    /**
     * �V���A���o�[�W����ID�B
     */
    private static final long serialVersionUID = -2235777024563491060L;

    /**
     * �R���X�g���N�^�B
     * @param parent �eMap�p�|�C���^
     * @param handler �v���p�e�B�n���h��
     */
    public DynamicPropertyPointerEx(NodePointer parent,
            DynamicPropertyHandler handler) {
        super(parent, handler);
    }

    /**
     * �v�f�����擾����B
     * @return �v�f��
     */
    @Override
    public int getLength() {
        // �v�f�̒l��null�Ȃ�΁A�v�f���P�Ƃ���
        Object value = getBaseValue();
        if (value == null) {
            return 1;
        }
        return ValueUtils.getLength(value);
    }
}
