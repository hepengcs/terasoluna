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

package jp.terasoluna.fw.batch.commonj.usequeue;

import jp.terasoluna.fw.batch.commonj.WorkerExecutorService;
import jp.terasoluna.fw.batch.core.InitializeException;
import jp.terasoluna.fw.batch.core.JobStatus;
import jp.terasoluna.fw.batch.core.WorkQueue;
import jp.terasoluna.fw.batch.core.WorkQueueFactory;
import jp.terasoluna.fw.batch.standard.QueueProcessor;

import commonj.work.WorkItem;
import commonj.work.WorkManager;

/**
 * <code>WorkQueueFactory</code> �C���^�t�F�[�X��Commonj�p�����N���X�B
 * 
 * <p>
 * ���̃N���X�ł́A<coed>WorkManagerTaskWorkQueue</code> �̃C���X�^���X���쐬���A 
 * �쐬����<coed>WorkManagerTaskWorkQueue</code> �̃C���X�^���X���p�����[�^�Ƃ���
 * �L���[�����v���Z�b�T���N���������ƂŁA�Ăяo�����Ƃ� 
 * <coed>WorkManagerTaskWorkQueue</code> �̃C���X�^���X��Ԃ��B
 * </p>
 * 
 */
public class WorkManagerTaskWorkQueueFactory implements WorkQueueFactory {

    /**
     * �L���[�̒����B
     */
    private int queueLength = 0;

    /**
     * �L���[�����v���Z�b�T�B
     */
    private QueueProcessor queueProcessor = null;

    /**
     * ���[�J�[���s�T�[�r�X�B
     */
    private WorkerExecutorService workerExecutorService = null;

    /**
     * ���d���s���B
     */
    private int multiplicity = 1;

    /**
     * �W���u���J�n����܂ł̃^�C���A�E�g�i�~���b�j�B
     */
    private long workTimeout = WorkManager.INDEFINITE;

    /**
     * ���[�N�L���[���擾����B
     * 
     * @param jobStatus �W���u�X�e�[�^�X
     * @return ���[�N�L���[
     */
    public WorkQueue getWorkQueue(JobStatus jobStatus) {
        WorkManagerTaskWorkQueue workQueue = new WorkManagerTaskWorkQueue(
                queueLength, jobStatus);

        workQueue.setWorkManager(workerExecutorService.getWorkManager());
        workQueue.setWorkTimeout(workTimeout);

        for (int i = 0; i < multiplicity; i++) {
            WorkItem workItem = workerExecutorService.submit(queueProcessor,
                    workQueue, jobStatus);
            workQueue.addWorkItem(workItem);
        }

        return workQueue;
    }

    /**
     * �L���[�̒�����ݒ肷��B
     * 
     * @param queueLength �L���[�̒���
     */
    public void setQueueLength(int queueLength) {
        if (queueLength <= 0) {
            StringBuffer buffer = new StringBuffer("QueueLength is illegal. ");
            buffer.append(queueLength);
            throw new InitializeException(buffer.toString());
        }
        this.queueLength = queueLength;
    }

    /**
     * �L���[�����v���Z�b�T��ݒ肷��B
     * 
     * @param queueProcessor �L���[�����v���Z�b�T
     */
    public void setQueueProcessor(QueueProcessor queueProcessor) {
        this.queueProcessor = queueProcessor;
    }

    /**
     * ���[�J�[���s�T�[�r�X��ݒ肷��B
     * 
     * @param workerExecutorService ���[�J�[���s�T�[�r�X
     */
    public void setWorkerExecutorService(
            WorkerExecutorService workerExecutorService) {
        this.workerExecutorService = workerExecutorService;
    }

    /**
     * ���d���s����ݒ肷��B
     * 
     * @param multiplicity ���d���s��
     */
    public void setMultiplicity(int multiplicity) {
        if (multiplicity <= 0) {
            StringBuffer buffer = new StringBuffer("Multiplicity is illegal. ");
            buffer.append(multiplicity);
            throw new InitializeException(buffer.toString());
        }
        this.multiplicity = multiplicity;
    }

    /**
     * �W���u���J�n����܂ł̃^�C���A�E�g�i�~���b�j�̐ݒ�B
     * 
     * @param workTimeout �^�C���A�E�g�i�~���b�j
     */
    public void setWorkTimeout(long workTimeout) {
        this.workTimeout = workTimeout;
    }
    
}
