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

package jp.terasoluna.fw.batch.controlbreak;

import java.util.List;
import java.util.Map;

/**
 * �R���g���[���u���C�N�pRow�I�u�W�F�N�g�N���X�B 
 * �����Ώۃf�[�^�����b�v���A�Ώۃf�[�^�� �R���g���[���u���C�N��
 * �������Ă��邩�ǂ�����ێ�������B
 * 
 */
public class ControlBreakRowObject {

    /**
     * �����Ώۃf�[�^�B
     */
    private Object rowObject;

    /**
     * �R���g���[���u���C�N�L�[���X�g�B
     */
    private List<List<String>> controlBreakKeyList;

    /**
     * �R���g���[���u���C�N�L�[�}�b�v�B
     */
    private Map<String, Object> controlBreakMap;

    /**
     * �R���X�g���N�^�B
     * 
     * @param rowObject �����Ώۃf�[�^
     * @param controlBreakKeyList �R���g���[���u���C�N�L�[���X�g
     * @param controlBreakMap �R���g���[���u���C�N�L�[�}�b�v
     */
    public ControlBreakRowObject(Object rowObject,
            List<List<String>> controlBreakKeyList,
            Map<String, Object> controlBreakMap) {
        this.rowObject = rowObject;
        this.controlBreakKeyList = controlBreakKeyList;
        this.controlBreakMap = controlBreakMap;
    }

    /**
     * �����Ώۃf�[�^���擾����B
     * 
     * @return �����Ώۃf�[�^
     */
    public Object getRowObject() {
        return rowObject;
    }

    /**
     * �R���g���[���u���C�N�L�[���X�g���擾����B
     * 
     * @return �R���g���[���u���C�N�L�[���X�g
     */
    public List<List<String>> getControlBreakKeyList() {
        return controlBreakKeyList;
    }

    /**
     * �R���g���[���u���C�N�L�[�}�b�v���擾����B
     * 
     * @return �R���g���[���u���C�N�L�[�}�b�v
     */
    public Map<String, Object> getControlBreakMap() {
        return controlBreakMap;
    }
}
