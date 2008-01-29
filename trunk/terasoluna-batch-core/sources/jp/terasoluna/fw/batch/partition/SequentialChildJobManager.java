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

package jp.terasoluna.fw.batch.partition;

import jp.terasoluna.fw.batch.core.JobManager;
import jp.terasoluna.fw.batch.core.JobStatus;

/**
 * <p>�����W���u�ɂ����āA�q�W���u�𑽏d�x�P�Œ������s���邽�߂̃W���u�}�l�[�W
 * ���B�������s���Ă���q�W���u�̂ǂꂩ���r���ŏI�������ꍇ�ɁA�c��̎q�W���u��
 * ���s�����ɃW���u���~����B</p>
 * 
 * <p>��ƏI�����ɁA���̃W���u�}�l�[�W���̃W���u�X�e�[�^�X���I����Ԃł���Ƃ���
 * �e�̃W���u�X�e�[�^�X�����̃W���u�Ɠ����W���u�X�e�[�^�X�ɍX�V����B</p>
 * 
 */
public class SequentialChildJobManager extends JobManager {

    /**
     * ��ƏI���������s���B
     * 
     * @param parentJobStatus �N�����̃W���u�X�e�[�^�X
     * @param jobStatus ���̃W���u�}�l�[�W���̃W���u�X�e�[�^�X
     */
    @Override
    protected void finishWork(
            JobStatus parentJobStatus, JobStatus jobStatus) {
        if (jobStatus.isExecuting() || jobStatus.isShutdownGraceful()
                || jobStatus.isShutdownImmediate()) {
            jobStatus.setJobState(JobStatus.STATE.ENDING_NORMALLY);
        } else {
            parentJobStatus.setJobState(jobStatus.getJobState());
        }
    }

}
