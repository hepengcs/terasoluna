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
import jp.terasoluna.fw.batch.core.WorkQueue;
import jp.terasoluna.fw.batch.core.WorkUnit;
import jp.terasoluna.fw.batch.core.Workable;
import jp.terasoluna.fw.batch.standard.QueueProcessor;

/**
 * ���X�^�[�g���̃L���[�����p�N���X�B
 *
 * <p>�L���[����v�f�����o���A���o�������ꂼ��̗v�f���p�����[�^�Ƃ��ă��[�J
 * �[���N������B</p>
 * 
 * <p>�L���[�̍Ō�̗v�f�ł���΃��X�^�[�g�p���[�J���N������B
 * �L���[�̍Ō�̗v�f�ł͂Ȃ��ꍇ�͒ʏ�̃��[�J���N������B</p>
 *
 */
public class RestartQueueProcessor extends QueueProcessor {

    /**
     * ���[�J�[�B
     */
    private Workable<WorkUnit> normalWorker = null;

    /**
     * ���X�^�[�g�p���[�J�[�B
     */
    private Workable<WorkUnit> restartWorker = null;

    /**
     * �L���[�̏������s���B
     *
     * @param workQueue �L���[
     * @param jobStatus �W���u�X�e�[�^�X
     */
    public void process(WorkQueue workQueue, JobStatus jobStatus) {
        printStartLog(jobStatus);

        WorkUnit prev = null;
        while (true) {
            WorkUnit element = workQueue.take();
            
            if (element.isEndMark()) {
                // �{����
                if (prev != null) {
                    restartWorker.work(prev, jobStatus);
                }
                break;
            } else if (prev != null) {
                // �{����
                normalWorker.work(prev, jobStatus);
            }

            if (!jobStatus.isContinue()) {
                break;
            }
            
            prev = element;
        }

        printEndLog(jobStatus);
    }

    /**
     * ���[�J�[��ݒ肷��B
     *
     * @param worker ���[�J�[
     */
    public void setWorker(Workable<WorkUnit> worker) {
        this.normalWorker = worker;
    }

    /**
     * ���X�^�[�g�p���[�J�[��ݒ肷��B
     *
     * @param restartWorker ���X�^�[�g�p���[�J�[
     */
    public void setRestartWorker(Workable<WorkUnit> restartWorker) {
        this.restartWorker = restartWorker;
    }

}
