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

package jp.terasoluna.fw.batch.standard;

import java.util.LinkedHashMap;

import jp.terasoluna.fw.batch.core.ExceptionHandlerUtil;
import jp.terasoluna.fw.batch.core.JobException;
import jp.terasoluna.fw.batch.core.JobExceptionHandler;
import jp.terasoluna.fw.batch.core.JobStatus;
import jp.terasoluna.fw.batch.core.WorkQueue;

/**
 * �L���[�v���Z�b�T���}���`�X���b�h�Ŏ��s���邽�߂̃��b�p�[�N���X�B
 *
 */
public class RunnableQueueProcessor implements Runnable {

    /**
     * �L���[�v���Z�b�T�B
     */
    private QueueProcessor queueProcessor;

    /**
     * �����Ώ����i�[����L���[�B
     */
    private WorkQueue workQueue;

    /**
     * �W���u�X�e�[�^�X�B
     */
    private JobStatus jobStatus;

    /**
     * ��O�n���h�����i�[����Map�B
     */
    private LinkedHashMap<JobException, JobExceptionHandler> 
        exceptionHandlerMap;

    /**
     * �f�t�H���g��O�n���h���B
     */
    private JobExceptionHandler defaultJobExceptionHandler;

    /**
     * �R���X�g���N�^�B
     * 
     * <p>�����œn���ꂽ�C���X�^���X��ݒ肷��B</p>
     *
     * @param queueProcessor �L���[�v���Z�b�T
     * @param workQueue ���[�N�L���[
     * @param jobStatus �W���u�X�e�[�^�X
     * @param exceptionHandlerMap ��O�n���h�����i�[����Map
     * @param defaultJobExceptionHandler �f�t�H���g��O�n���h��
     */
    public RunnableQueueProcessor(
            QueueProcessor queueProcessor,
            WorkQueue workQueue,
            JobStatus jobStatus,
            LinkedHashMap<JobException, JobExceptionHandler> 
                exceptionHandlerMap,
            JobExceptionHandler defaultJobExceptionHandler) {
        this.queueProcessor = queueProcessor;
        this.workQueue = workQueue;
        this.jobStatus = jobStatus;
        this.exceptionHandlerMap = exceptionHandlerMap;
        this.defaultJobExceptionHandler = defaultJobExceptionHandler;
    }

    /**
     * �L���[�v���Z�b�T���N������B
     *
     */
    public void run() {
        try {
            queueProcessor.process(workQueue, jobStatus);
        } catch (Exception e) {
            // BLogic�ABLogic�������ʃn���h���Ŕ���������O�̏���
            JobException jobException = new JobException(e);

            JobExceptionHandler handler
                = ExceptionHandlerUtil.getJobExceptionHandler(jobException,
                                                            exceptionHandlerMap,
                                                    defaultJobExceptionHandler);
            handler.handlException(null, jobException, jobStatus);
        }
    }
}
