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

package jp.terasoluna.fw.batch.springsupport.transaction;

import jp.terasoluna.fw.batch.core.Chunk;
import jp.terasoluna.fw.batch.core.JobStatus;
import jp.terasoluna.fw.batch.core.Workable;

/**
 * �g�����U�N�V�����������s�����[�J�[�����N���X�B
 * 
 * <p><code>jobWorker</code> �����ɐݒ肳�ꂽ���[�J�̌Ăяo�����g�����U�N�V����
 * �Ŏ��s����B</p>
 * 
 * <p>�g�����U�N�V����������s�����߁A�W���u�X�e�[�^�X�Ƃ��� 
 * <code>TransactionalJobStatus</code>���g�p����K�v������B</p>
 * 
 */
public class TransactionalWorker implements Workable<Chunk> {

    /**
     * �W���u���[�J�[�B
     */
    private Workable<Chunk> jobWorker = null;
    
    /**
     * ���[�J�[�̏������s���B
     * 
     * @param chunk �`�����N
     * @param jobStatus �g�����U�N�V���i���W���u�X�e�[�^�X
     */
    public void work(Chunk chunk, JobStatus jobStatus) {
        
        TransactionalJobStatus transactionalJobStatus = 
            (TransactionalJobStatus) jobStatus;
        transactionalJobStatus.beginTransaction();
        
        // �Z�[�u�|�C���g��L�����ɍŏ��̃Z�[�u�|�C���g���쐬
        if (transactionalJobStatus.useSavepoint()) {
            transactionalJobStatus.releaseAndCreateSavepoint();
        }

        jobWorker.work(chunk, transactionalJobStatus);
        
        //�����I��
        if (jobStatus.isShutdownImmediate()) {
            jobStatus.suspend();
            transactionalJobStatus.rollback();
            return;
        }

        if (jobStatus.isExecuting()
                || jobStatus.getJobState() == JobStatus.STATE.ENDING_NORMALLY
                || jobStatus.isShutdownGraceful()) {
            transactionalJobStatus.commit();
        } else {
            transactionalJobStatus.rollback();
            // �Z�[�u�|�C���g�L�����͍Ō�ɃR�~�b�g����
            if (transactionalJobStatus.useSavepoint()) {
                transactionalJobStatus.commit();
            }
        }

        //���f�I��
        if (jobStatus.isShutdownGraceful()) {
            jobStatus.suspend();
            return;
        }
    }

    /**
     * �W���u���[�J�[��ݒ肷��B
     * 
     * @param jobWorker �W���u���[�J�[
     */
    public void setJobWorker(Workable<Chunk> jobWorker) {
        this.jobWorker = jobWorker;
    }
}
