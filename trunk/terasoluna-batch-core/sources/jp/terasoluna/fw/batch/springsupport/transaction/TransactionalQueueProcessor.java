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

import jp.terasoluna.fw.batch.core.JobStatus;
import jp.terasoluna.fw.batch.core.QueueingException;
import jp.terasoluna.fw.batch.core.WorkQueue;
import jp.terasoluna.fw.batch.standard.QueueProcessor;

/**
 * �L���[�̏������g�����U�N�V�����ōs���L���[�v���Z�b�T�N���X�B
 * 
 * <p>�L���[�̂��ׂĂ̗v�f�̏������s������ŁA�W���u�X�e�[�^�X��
 * <code>JobStatus.STATE.STARTED</code>�A<code>JobStatus.STATE.RESTARTED</code>
 * �A���邢��<code>JobStatus.STATE.ENDING_NORMALLY</code>
 * �ł���ꍇ�ɂ̓R�~�b�g���A���̂ق��̏ꍇ�ɂ̓��[���o�b�N����B</p>
 *
 * <p>�g�����U�N�V����������s�����߁A�W���u�X�e�[�^�X�Ƃ��� 
 * <code>TransactionalJobStatus</code>���g�p����K�v������B</p>
 * 
 */
public class TransactionalQueueProcessor extends QueueProcessor {

    /**
     * �L���[�̏������s���B
     *
     * @param workQueue �L���[
     * @param jobStatus �g�����U�N�V���i���W���u�X�e�[�^�X
     */
    @Override
    public void process(WorkQueue workQueue, JobStatus jobStatus) {
        
        TransactionalJobStatus transactionalJobStatus = 
            (TransactionalJobStatus) jobStatus;
        transactionalJobStatus.beginTransaction();
        
        try {
            super.process(workQueue, jobStatus);
        } catch (QueueingException e) {
            // �����I��/���f�I���̂Ƃ��̓��[���o�b�N
            if (jobStatus.getJobState() == JobStatus.STATE.SUSPENDING) {
                transactionalJobStatus.rollback();
                return;
            } else {
                throw e;
            }
        }

        if (jobStatus.isExecuting()
                || jobStatus.getJobState() == JobStatus.STATE.ENDING_NORMALLY) {
            transactionalJobStatus.commit();
        } else {
            transactionalJobStatus.rollback();
        }
    }
}
