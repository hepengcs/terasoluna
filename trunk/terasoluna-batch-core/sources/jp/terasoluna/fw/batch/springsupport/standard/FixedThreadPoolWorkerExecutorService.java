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

package jp.terasoluna.fw.batch.springsupport.standard;

import java.util.LinkedHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import jp.terasoluna.fw.batch.core.InitializeException;
import jp.terasoluna.fw.batch.core.JobException;
import jp.terasoluna.fw.batch.core.JobExceptionHandler;
import jp.terasoluna.fw.batch.core.JobStatus;
import jp.terasoluna.fw.batch.core.WorkQueue;
import jp.terasoluna.fw.batch.standard.QueueProcessor;
import jp.terasoluna.fw.batch.standard.RunnableQueueProcessor;
import jp.terasoluna.fw.batch.standard.WorkerExecutorService;

import org.springframework.beans.factory.DisposableBean;

/**
 * <code>WorkerExecutorService</code> �C���^�t�F�[�X�̕W�������N���X�B
 * 
 * <p><code>Executors.newFixedThreadPool()</code> �ō쐬�����X���b�h�v�[���ŁA
 * ���[�J�����s����B</p>
 * 
 */
public class FixedThreadPoolWorkerExecutorService
        implements WorkerExecutorService, DisposableBean {

    /**
     * ���s�T�[�r�X�B
     */
    private ExecutorService executorService = null;

    /**
     * ��O�n���h�����i�[����Map�B
     */
    private LinkedHashMap<JobException, JobExceptionHandler> 
        exceptionHandlerMap = null;

    /**
     * �f�t�H���g��O�n���h���B
     */
    private JobExceptionHandler defaultJobExceptionHandler = null;

    /**
     * �R���X�g���N�^�B
     * 
     * @param threads �X���b�h��
     */
    public FixedThreadPoolWorkerExecutorService(int threads) {
        if (threads <= 0) {
                    StringBuilder builder = 
                        new StringBuilder("Threads is illegal. ");
                    builder.append(threads);
                throw new InitializeException(builder.toString());
           }
        executorService = Executors.newFixedThreadPool(threads);
    }

    /**
     * ���[�J�[���s��o�^����B
     *
     * @param queueProcessor �L���[�����v���Z�b�T
     * @param workQueue �L���[
     * @param jobStatus �W���u�X�e�[�^�X
     * @return ���[�J�̏������ʂɔ񓯊��ŃA�N�Z�X�����߂̃t���[�`���[
     */
    public Future< ? > submit(QueueProcessor queueProcessor,
            WorkQueue workQueue, JobStatus jobStatus) {
        return executorService.submit(new RunnableQueueProcessor(
                queueProcessor, workQueue, jobStatus, exceptionHandlerMap,
                defaultJobExceptionHandler));
    }

    /**
     * �C���X�^���X��j������ۂɁASpring fremework����Ă΂��R�[��
     * �o�b�N���\�b�h�B
     * 
     * <p>���s�T�[�r�X���V���b�g�_�E������B</p>
     */
    public void destroy() {
        executorService.shutdown();
    }
    /**
     * ��O�n���h�����i�[����Map��ݒ肷��B
     *
     * @param exceptionHandlerMap ��O�n���h�����i�[����Map
     */
    public void setExceptionHandlerMap(
         LinkedHashMap<JobException, JobExceptionHandler> exceptionHandlerMap) {
        this.exceptionHandlerMap = exceptionHandlerMap;
    }

    /**
     * �f�t�H���g��O�n���h����ݒ肷��B
     * @param defaultJobExceptionHandler �f�t�H���g��O�n���h��
     */
    public void setDefaultJobExceptionHandler(
        JobExceptionHandler defaultJobExceptionHandler) {
        this.defaultJobExceptionHandler = defaultJobExceptionHandler;
    }
}
