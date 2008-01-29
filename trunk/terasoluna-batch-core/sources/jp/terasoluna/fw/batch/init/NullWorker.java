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

package jp.terasoluna.fw.batch.init;

import jp.terasoluna.fw.batch.core.JobStatus;
import jp.terasoluna.fw.batch.core.WorkUnit;
import jp.terasoluna.fw.batch.core.Workable;

/**
 * �W���u�f�[�����I���p�}�l�W���[�N���X�B
 *
 */
public class NullWorker implements Workable<WorkUnit> {

    /**
     * �}�l�[�W���[�̃��C�������p���\�b�h�B
     * <code>jobStatus</code>�̏��<code>��ENDING_NORMALLY</code>�ɕύX�B
     *
     * @param workUnit ��ƒP��
     * @param jobStatus �W���u������
     */
    public void work(WorkUnit workUnit, JobStatus jobStatus) {
        JobStatus childJobStatus = jobStatus.getChild(workUnit.getJobContext());
        childJobStatus.setJobState(JobStatus.STATE.ENDING_NORMALLY);
    }
    
}
