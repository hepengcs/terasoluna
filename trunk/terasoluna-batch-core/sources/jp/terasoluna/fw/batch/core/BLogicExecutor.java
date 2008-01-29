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

import jp.terasoluna.fw.batch.openapi.JobContext;

/**
 * �r�W�l�X���W�b�N���s�N���X����������C���^�t�F�[�X�B
 * 
 * <p>�r�W�l�X���W�b�N���s�N���X�ł́A�����œn���ꂽ���̓f�[�^�A
 * �W���u�R���e�N�X�g�Ȃǂ�p���āA�P��̃r�W�l�X���W�b�N���s���s���B</p>
 * 
 * <p>���̂ق��ɁA���̃C���^�t�F�[�X�̎����N���X�ł́A�r�W�l�X���s���ʂɑ΂���
 * �������ʃn���h���̌Ăяo����A��O�n���h���̌Ăяo���Ȃǂ��s���B</p>
 * 
 */
public interface BLogicExecutor {

    /**
     * �r�W�l�X���W�b�N�����s����B
     *
     * @param blogicInputData �����Ώۃf�[�^
     * @param jobContext �W���u�R���e�L�X�g
     * @param jobStatus �W���u������
     * @param batchUpdateMapList �o�b�`�X�V���X�g
     */
    void executeBLogic(Object blogicInputData, JobContext jobContext,
            JobStatus jobStatus,
            List<LinkedHashMap<String, Object>> batchUpdateMapList);
}
