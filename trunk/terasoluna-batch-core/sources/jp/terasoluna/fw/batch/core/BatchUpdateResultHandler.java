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
 * �o�b�`�X�V�������ʂ̃n���h���N���X�B
 * 
 * <p>�o�b�`�X�V�������ɂ̂݌Ă΂�A�W���u�X�e�[�^�X�A����ю��s����������
 * �o�b�`�X�V���X�g���n�����B
 * �o�b�`�X�V���X�g�̏����W���u�X�e�[�^�X�ɔ��f���邱�Ƃ��ł���B</p>
 *
 */
public interface BatchUpdateResultHandler {

    /**
     * �o�b�`�X�V�������ʂ���������B
     * 
     * @param jobStatus �W���u�X�e�[�^�X
     * @param batchUpdateMapList �o�b�`�X�V�p��SQLID�ƃp�����[�^��ێ�����Map�̃��X�g
     */
    void handle(JobStatus jobStatus,
            List<LinkedHashMap<String, Object>> batchUpdateMapList);
}
