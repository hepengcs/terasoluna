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

import jp.terasoluna.fw.batch.core.Chunk;
import jp.terasoluna.fw.batch.core.JobStatus;
import jp.terasoluna.fw.batch.core.JobWorker;
import jp.terasoluna.fw.batch.core.Workable;

/**
 * ���X�^�[�g�|�C���g�o�^�p���[�J�N���X�B
 * 
 */
public class RestartPointUpdater implements Workable<Chunk> {

    /**
     * �N�����郏�[�J�B
     */
    private JobWorker jobWorker = null;
    
    /**
     * �W���u���X�^�[�g�e�[�u���n���h���B
     */
    private JobRestartTableHandler jobRestartTableHandler = null;

    /**
     * �`�����N�̏������s���B
     * 
     * <p>
     * �`�����N�̏��������������ꍇ�ɁA�W���u���X�^�[�g�|�C���g�o�^�������s���B
     * </p>
     * 
     * @param chunk �`�����N
     * @param jobStatus �W���u���
     */
    public void work(Chunk chunk, JobStatus jobStatus) {
        jobWorker.work(chunk, jobStatus);

        //���X�^�[�g�|�C���g�o�^����
        if (jobStatus.isExecuting()
                || jobStatus.getJobState() == JobStatus.STATE.ENDING_NORMALLY
                || jobStatus.isShutdownGraceful()) {
            jobRestartTableHandler.registerRestartPoint(chunk.getJobContext(),
                    jobStatus);
        }
    }

    /**
     * �W���u���X�^�[�g�e�[�u���n���h����ݒ肷��B
     * 
     * @param jobRestartTableHandler �W���u���X�^�[�g�e�[�u���n���h��
     */
    public void setJobRestartTableHandler(
            JobRestartTableHandler jobRestartTableHandler) {
        this.jobRestartTableHandler = jobRestartTableHandler;
    }

    /**
     * �W���u���[�J��ݒ肷��B
     * 
     * @param jobWorker �W���u���[�J
     */
    public void setJobWorker(JobWorker jobWorker) {
        this.jobWorker = jobWorker;
    }
}
