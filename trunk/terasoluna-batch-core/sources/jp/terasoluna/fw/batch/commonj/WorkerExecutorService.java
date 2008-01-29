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

package jp.terasoluna.fw.batch.commonj;

import java.util.LinkedHashMap;

import jp.terasoluna.fw.batch.commonj.listener.WorkMapListener;
import jp.terasoluna.fw.batch.core.JobException;
import jp.terasoluna.fw.batch.core.JobExceptionHandler;
import jp.terasoluna.fw.batch.core.JobStatus;
import jp.terasoluna.fw.batch.core.WorkQueue;
import jp.terasoluna.fw.batch.standard.QueueProcessor;

import commonj.work.WorkItem;
import commonj.work.WorkManager;


/**
 * ���[�J�[���s�T�[�r�X(commonJ)�̃C���^�t�F�[�X�B
 *
 *
 */
public interface WorkerExecutorService {
    /**
     * �f�t�H���g��O�n���h���̎擾�B
     * 
     * @return �f�t�H���g��O�n���h��
     */
    JobExceptionHandler getDefaultJobExceptionHandler();

    /**
     * ��O�n���h�����i�[����Map�̎擾�B
     * 
     * @return ��O�n���h�����i�[�����}�b�v
     */
    LinkedHashMap<JobException, JobExceptionHandler> getExceptionHandlerMap();

    /**
     * ���[�N�}�l�[�W���̎擾�B
     * 
     * @return ���[�N�}�l�[�W��
     */
    WorkManager getWorkManager();

    /**
     * ���[�N���X�i�[�̎擾�B
     * 
     * @return ���[�N���X�i�[
     */
    WorkMapListener getWorkMapListener();
    
    /**
     * Worker�̎��s��o�^����B
     * 
     * @param queueProcessor �L���[�����v���Z�b�T
     * @param workQueue �L���[
     * @param jobStatus �W���u�X�e�[�^�X
     * @return ���[�J�̏������ʂɔ񓯊��ŃA�N�Z�X�����߂̃��[�N�A�C�e��
     */
     WorkItem submit(QueueProcessor queueProcessor,
            WorkQueue workQueue, JobStatus jobStatus);

}
