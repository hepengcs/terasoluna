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

package jp.terasoluna.fw.batch.commonj.listener;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import jp.terasoluna.fw.batch.commonj.init.JobExecutor;
import jp.terasoluna.fw.batch.commonj.transaction.JobResultInfoHandler;
import jp.terasoluna.fw.batch.core.JobStatus;
import jp.terasoluna.fw.batch.jobmessage.JobMessageInfo;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import commonj.work.Work;
import commonj.work.WorkEvent;
import commonj.work.WorkItem;

/**
 * ���[�N�}�l�[�W����Ŏ��s����郏�[�N�i�W���u�j�̊Ď����s���N���X�B<br>
 * ���[�N�̊���(Completed)�A����(Rejected)���Ɏ��s���ʃn���h���ɂ��
 * �n���h�����O���s���B
 * 
 * 
 */
public class JobExecutorListener implements WorkMapListener {

    /**
     * ���O�C���X�^���X�B
     */
    private static Log log = LogFactory.getLog(JobExecutorListener.class);

    /**
     * ��ƈ˗������ۂ��ꂽ�Ƃ��̏I���R�[�h�B
     */
    private static final String REJECTED_JOB_EXIT_CODE = "-1";
    
    /**
     * �X�P�W���[�����ꂽ���[�N��ێ�����}�b�v�B
     */
    private Map<WorkItem, Work> map = 
        Collections.synchronizedMap(new HashMap<WorkItem, Work>());

    /**
     * �W���u���s���ʃn���h���B
     */
    private JobResultInfoHandler jobResultInfoHandler = null;

    /**
     * �X�P�W���[�����ꂽ���[�N�̓o�^�B
     * 
     * @param workItem ���[�N�����[�N�}�l�W���[�ŃX�P�W���[�������Ƃ��̕ԋp�l
     * @param work �X�P�W���[���������[�N
     */
    public void addWork(WorkItem workItem, Work work) {
        map.put(workItem, work);
        if (log.isDebugEnabled()) {
            log.debug("Map add work: " + workItem);
        }
    }

    /**
     * �X�P�W���[������Ă��郏�[�N�̎擾�B
     * 
     * @param workItem ���[�N�����[�N�}�l�W���[�ŃX�P�W���[�������Ƃ��̕ԋp�l
     * @return �X�P�W���[������Ă��郏�[�N
     */
    public Object getWork(WorkItem workItem) {
        return map.get(workItem);
    }

    /**
     * ��Ƃ������������[�N���폜����B
     * 
     * @param workItem ���[�N�����[�N�}�l�W���[�ŃX�P�W���[�������Ƃ��̕ԋp�l
     * @return �X�P�W���[������Ă��郏�[�N
     */
    public Object removeWork(WorkItem workItem) {
        return map.remove(workItem);
    }

    /**
     * ���[�N�̃X�P�W���[�����O����B
     * 
     * @param we ���[�N�C�x���g
     */
    public void workAccepted(WorkEvent we) {
        printSimpleLog("Work accepted: ", getWork(we.getWorkItem()));
    }

    /**
     * ���[�N�̎��s�����B
     * 
     * @param we ���[�N�C�x���g
     */
    public void workCompleted(WorkEvent we) {
        JobExecutor jobExecutor = (JobExecutor) removeWork(we.getWorkItem());
        JobStatus jobStatus = jobExecutor.getJobStatus();

        JobMessageInfo jobMessageInfo = 
            createJobMessageInfo(jobStatus, jobExecutor);

        printLog("Work completed: ", jobMessageInfo, jobStatus);

        jobResultInfoHandler.handle(jobMessageInfo);
        
        jobExecutor.destroy();
    }

    /**
     * ���[�N�̃X�P�W���[�����O���ہB
     * 
     * @param we ���[�N�C�x���g
     */
    public void workRejected(WorkEvent we) {
        JobExecutor jobExecutor = (JobExecutor) removeWork(we.getWorkItem());
        JobStatus jobStatus = null;
        JobMessageInfo jobMessageInfo = null;
        if (jobExecutor != null) {
            jobStatus = jobExecutor.getJobStatus();
            jobMessageInfo = createJobMessageInfo(jobStatus, jobExecutor);
            jobMessageInfo.setJobExitCode(REJECTED_JOB_EXIT_CODE);
            jobResultInfoHandler.handle(jobMessageInfo);
            printLog("Work rejected: ", jobMessageInfo, jobStatus);
            
            jobExecutor.destroy();
        }

    }

    /**
     * ���[�N�̎��s�J�n�B
     * 
     * @param we ���[�N�C�x���g
     */
    public void workStarted(WorkEvent we) {
        printSimpleLog("Work started: ", getWork(we.getWorkItem()));
    }

    /**
     * �W���u���s���ʃn���h���̐ݒ�B
     * 
     * @param jobResultInfoHandler �W���u���s���ʃn���h��
     */
    public void setJobResultInfoHandler(
            JobResultInfoHandler jobResultInfoHandler) {
        this.jobResultInfoHandler = jobResultInfoHandler;
    }

    
    /**
     * �W���u���b�Z�[�W�Ǘ������쐬����B
     *  
     * @param jobStatus �W���u�X�e�[�^�X
     * @param jobExecutor ��ƈ˗����ꂽ�W���u�N���N���X
     * @return �W���u���b�Z�[�W�Ǘ����
     */
    private JobMessageInfo createJobMessageInfo(
            JobStatus jobStatus, JobExecutor jobExecutor) {
        JobMessageInfo jobMessageInfo = new JobMessageInfo();
        jobMessageInfo.setJobRequestNo(jobStatus.getJobRequestNo());
        jobMessageInfo.setJobId(jobStatus.getJobId());
        jobMessageInfo.setJobExitCode(String
                .valueOf(jobStatus.getJobExitCode()));
        jobMessageInfo.setJobState(String.valueOf(jobStatus.getJobState()
                .ordinal()));
        jobMessageInfo.setPartitionNo(jobStatus.getPartitionNo());
        jobMessageInfo.setPartitionKey(jobStatus.getPartitionKey());
        jobMessageInfo.setJobDiscriptorPath(jobExecutor.getJobInfo()
                .getJobDiscriptorPath());
        
        return jobMessageInfo;
    }
    
    /**
     * ���O�o�́B
     * 
     * @param message ���O���b�Z�[�W
     * @param jobMessageInfo �W���u���b�Z�[�W�Ǘ����
     * @param jobStatus �W���u���b�Z�[�W�@
     */
    private void printLog(String message, JobMessageInfo jobMessageInfo, 
            JobStatus jobStatus) {
        if (log.isDebugEnabled()) {
            StringBuilder builder = new StringBuilder(message);
            builder.append("[");
            builder.append("JobRequestNo = ");
            builder.append(jobStatus.getJobRequestNo());
            builder.append("] [");
            builder.append("JobId = ");
            builder.append(jobStatus.getJobId());
            builder.append("] [");
            builder.append("PartitionNo = ");
            builder.append(jobMessageInfo.getPartitionNo());
            builder.append("] [");
            builder.append("ExitCode = ");
            builder.append(jobMessageInfo.getJobExitCode());
            builder.append("] [");
            builder.append("JobState = ");
            builder.append(jobMessageInfo.getJobState());
            builder.append("]");
            log.debug(builder.toString());
        }
    }
    
    /**
     * �ȒP�ȃ��O�o�́B
     * 
     * @param message ���O���b�Z�[�W
     * @param object �o�̓I�u�W�F�N�g
     */
    private void printSimpleLog(String message, Object object) {
        if (log.isDebugEnabled()) {
            StringBuilder builder = new StringBuilder(message);
            builder.append("[");
            builder.append(object);
            builder.append("]");
            log.debug(builder.toString());
        }
    }
}
