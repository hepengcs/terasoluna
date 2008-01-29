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

package jp.terasoluna.fw.batch.core;

import java.util.LinkedHashMap;
import java.util.List;


/**
 * �o�b�`�X�V��O�B
 * 
 * <p>������O�A����ї�O�������Ɏ��s���Ă����o�b�`�X�V���X�g��ێ�����B</p>
 *
 */
public class BatchUpdateException extends JobException {

    /**
     * Serializable�p�o�[�W����ID
     */
    private static final long serialVersionUID = 4805446643634322477L;

    /**
     * ��O�������Ɏ��s���Ă����o�b�`�X�V���X�g�B
     */
    private List<LinkedHashMap<String, Object>> batchUpdateMapList;

    /**
     * �R���X�g���N�^�B
     *
     */
    public BatchUpdateException(){
        
    }

    /**
     * �R���X�g���N�^�B
     *
     * @param e �o�b�`�X�V���ɔ�������������O
     * @param batchUpdateMapList ��O�������Ɏ��s���Ă����o�b�`�X�V���X�g
     */
    public BatchUpdateException(Exception e,
            List<LinkedHashMap<String, Object>> batchUpdateMapList) {
        super(e);
        this.batchUpdateMapList = batchUpdateMapList;
    }

    /**
     * ��O�������Ɏ��s���Ă����o�b�`�X�V���X�g���擾����B
     *
     * @return ��O�������Ɏ��s���Ă����o�b�`�X�V���X�g
     */
    public List<LinkedHashMap<String, Object>> getBatchUpdateMapList() {
        return batchUpdateMapList;
    }
}
