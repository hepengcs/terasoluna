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

package jp.terasoluna.fw.batch.restart;

import jp.terasoluna.fw.batch.core.JobStatus;
import jp.terasoluna.fw.batch.openapi.JobContext;

/**
 * ���X�^�[�g�p�W���u�̏����󋵂�ێ�����N���X�B<BR>
 * JobStatus�̃��b�v�N���X�B
 * 
 */
public class RestartJobStatus extends JobStatus {

    /**
     * �����󋵁B
     */
    private JobStatus jobStatus;
    
    /**
     * �R���X�g���N�^�B
     * ���X�^�[�g����JobStatus�����b�v����B
     * 
     * @param jobStatus ������
     */
    protected RestartJobStatus(JobStatus jobStatus) {
        this.jobStatus = jobStatus;
    }

    /**
     * �q�W���u�����󋵂��擾����B
     *
     * @param jobContext �W���u�R���e�L�X�g
     * @return �q�W���u������
     */
    @Override
    public JobStatus getChild(JobContext jobContext) {
        return jobStatus;
    }
}
