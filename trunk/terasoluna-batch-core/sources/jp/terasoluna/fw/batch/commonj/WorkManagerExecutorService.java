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

import org.springframework.scheduling.commonj.DelegatingWork;

import jp.terasoluna.fw.batch.commonj.listener.WorkMapListener;
import jp.terasoluna.fw.batch.core.JobException;
import jp.terasoluna.fw.batch.core.JobExceptionHandler;
import jp.terasoluna.fw.batch.core.JobStatus;
import jp.terasoluna.fw.batch.core.WorkQueue;
import jp.terasoluna.fw.batch.standard.QueueProcessor;
import jp.terasoluna.fw.batch.standard.RunnableQueueProcessor;

import commonj.work.WorkException;
import commonj.work.WorkItem;
import commonj.work.WorkManager;
import commonj.work.WorkRejectedException;

/**
 * <code>WorkerExecutorService</code> �C���^�t�F�[�X��Commonj�p�����N���X�B
 * 
 * <p>
 * <code>WorkManager</code> �Ń��[�J�����s����B
 * </p>
 * 
 *
 */
public class WorkManagerExecutorService implements WorkerExecutorService {

    /**
     * ���[�N�}�l�[�W���B
     */
    private WorkManager workManager;
    
    /**
     * ���[�N���X�i�[�B
     */
    private WorkMapListener workMapListener;
    
    /**
     * ��O�n���h�����i�[�����}�b�v�B
     */
    private LinkedHashMap<JobException, JobExceptionHandler> 
        exceptionHandlerMap = null;

    /**
     * �f�t�H���g��O�n���h���B
     */
    private JobExceptionHandler defaultJobExceptionHandler = null;

    /**
     * �f�t�H���g��O�n���h���̎擾�B
     * 
     * @return �f�t�H���g��O�n���h��
     */
    public JobExceptionHandler getDefaultJobExceptionHandler() {
        return defaultJobExceptionHandler;
    }
    
    /**
     * �f�t�H���g��O�n���h���̐ݒ�B
     * 
     * @param defaultJobExceptionHandler �f�t�H���g��O�n���h��
     */
    public void setDefaultJobExceptionHandler(
            JobExceptionHandler defaultJobExceptionHandler) {
        this.defaultJobExceptionHandler = defaultJobExceptionHandler;
    }

    /**
     * ��O�n���h�����i�[�����}�b�v�̎擾�B
     * 
     * @return ��O�n���h�����i�[�����}�b�v
     */
    public LinkedHashMap<JobException, JobExceptionHandler> 
        getExceptionHandlerMap() {
        return exceptionHandlerMap;
    }

    /**
     * ��O�n���h�����i�[�����}�b�v�̐ݒ�B
     * 
     * @param exceptionHandlerMap ��O�n���h�����i�[�����}�b�v
     */
    public void setExceptionHandlerMap(
            LinkedHashMap<JobException, JobExceptionHandler> 
                exceptionHandlerMap) {
        this.exceptionHandlerMap = exceptionHandlerMap;
    }

    /**
     * ���[�N�}�l�[�W���̎擾�B
     * 
     * @return ���[�N�}�l�[�W��
     */
    public WorkManager getWorkManager() {
        return workManager;
    }

    /**
     * ���[�N�}�l�[�W���̐ݒ�B
     * 
     * @param workManager ���[�N�}�l�[�W��
     */
    public void setWorkManager(WorkManager workManager) {
        this.workManager = workManager;
    }

    /**
     * ���[�N���X�i�[�̎擾�B
     * 
     * @return ���[�N���X�i�[
     */
    public WorkMapListener getWorkMapListener() {
        return workMapListener;
    }

    /**
     * ���[�N���X�i�[�̐ݒ�B
     * 
     * @param workMapListener ���[�N���X�i�[
     */
    public void setWorkMapListener(WorkMapListener workMapListener) {
        this.workMapListener = workMapListener;
    }

    /**
     * Worker�̎��s��o�^����B
     * 
     * @param queueProcessor �L���[�����v���Z�b�T
     * @param workQueue �L���[
     * @param jobStatus �W���u�X�e�[�^�X
     * @return ���[�J�̏������ʂɔ񓯊��ŃA�N�Z�X�����߂̃��[�N�A�C�e��
     */
   public WorkItem submit(QueueProcessor queueProcessor, 
           WorkQueue workQueue, JobStatus jobStatus) {
       try {
           DelegatingWork work = new DelegatingWork(
                   new RunnableQueueProcessor(queueProcessor, workQueue,
                           jobStatus, exceptionHandlerMap,
                           defaultJobExceptionHandler));
           if (workMapListener == null) {
               return workManager.schedule(work);
           } else {
               WorkItem workItem = workManager.schedule(work, workMapListener);
               workMapListener.addWork(workItem, work);
               return workItem;
           }
       } catch (IllegalArgumentException e) {
           throw new JobException(e);
       } catch (WorkRejectedException e) {
           throw new JobException(e);
       } catch (WorkException e) {
           throw new JobException(e);
       }
    }
    
}
