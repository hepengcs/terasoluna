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

package jp.terasoluna.fw.batch.commonj.init;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ConfigurableApplicationContext;

import jp.terasoluna.fw.batch.core.JobStatus;
import jp.terasoluna.fw.batch.core.JobStatusSummarizer;
import jp.terasoluna.fw.batch.core.WorkUnit;
import jp.terasoluna.fw.batch.core.Workable;
import jp.terasoluna.fw.batch.init.JobInfo;
import jp.terasoluna.fw.batch.openapi.JobContext;

/**
 * ���U�����ɂ�����q�W���u�N���p���C���N���X�B<br>
 * �e�W���u����̃��b�Z�[�W�Ŏ󂯎�����q�W���u�p�̃W���u�R���e�L�X�g��<br>
 * �g�p���Ďq�W���u�̃W���u�����s����B
 * 
 * 
 */
public class ChildJobExecutor extends JobExecutor {

    /**
     * ���O�C���X�^���X�B
     */
    private static Log log = LogFactory.getLog(ChildJobExecutor.class);

    /**
     * �W���u�R���e�L�X�g�B
     */
    private JobContext jobContext = null;

    /**
     * �W���u�����s����B<br>
     * �w�肳�ꂽ�W���u�R���e�L�X�g�̏��Ɋ�Â��ăW���u�����s����B
     * 
     * @param jobInfo �W���u�˗����
     * @param jobContext �W���u�R���e�L�X�g
     * @return �W���u���s����
     */
    public JobStatus execute(JobInfo jobInfo, JobContext jobContext) {
        // �J�n���O�o��
        printStartLog(jobContext);
        
        // �W���uBeanFactory
        ConfigurableApplicationContext jobFactory = getJobBeanFactory(jobInfo);

        // �}�l�W���[�擾
        Workable<WorkUnit> jobManager = getJobManager(jobFactory);

        // �W���u��Ԃ̏�����
        jobStatus = this.getJobStatus(jobFactory, jobContext);

        // �I���t�@�C���Ď��X���b�h�ɓo�^
        initEndFileChecker(jobStatus, jobContext);

        WorkUnit rootWorkQueueElement = getWorkUnit(jobContext);

        jobManager.work(rootWorkQueueElement, jobStatus);

        ((JobStatusSummarizer) jobFactory.getBean(JOBSTATUS_SUMMARIZER))
                .summarize(jobStatus);

        setDefaultJobExitCode(jobStatus);

        if (!(Boolean) jobFactory.getBean(USECACHE_NAME)) {
            jobFactory.close();
        }

        // �W���u�̏I�����O�o��
        printEndLog(jobStatus);

        return jobStatus;
    }

    /**
     * �W���u�̔񓯊�������s�B
     * 
     */
    @Override
    public void run() {
        jobStatus = execute(getJobInfo(), jobContext);
    }

    /**
     * �W���u�R���e�L�X�g����W���u�X�e�[�^�X���쐬����B
     * 
     * @param jobFactory �W���u����<code>BeanFactory</code>
     * @param jobContext �W���u�R���e�L�X�g
     * @return �����W���u�X�e�[�^�X
     */
    protected JobStatus getJobStatus(ConfigurableApplicationContext jobFactory,
            JobContext jobContext) {
        
        JobStatus initJobStatus = null;

        if ((Boolean) jobFactory.getBean(USE_MONITORABLE)) {
            initJobStatus = (JobStatus) jobFactory
                    .getBean(MONITORABLE_JOBSTATUS_NAME);
        } else {
            initJobStatus = (JobStatus) jobFactory.getBean(JOBSTATUS_NAME);
        }

        initJobStatus.setJobRequestNo(jobContext.getJobRequestNo());
        initJobStatus.setJobId(jobContext.getJobId());
        initJobStatus.setJobState(JobStatus.STATE.STARTED);
        initJobStatus.setPartitionNo(jobContext.getPartitionNo());
        initJobStatus.setPartitionKey(jobContext.getPartitionKey());

        return initJobStatus;
    }

    /**
     * �W���u�̊J�n���O�B
     * 
     * @param jobContext �W���u�R���e�L�X�g
     */
    protected void printStartLog(JobContext jobContext) {
        if (log.isDebugEnabled()) {
            StringBuilder logStr = new StringBuilder();
            logStr.append("�ySTART�z");
            logStr.append(" [jobId=");
            logStr.append(jobContext.getJobId());
            logStr.append("]");
            logStr.append(" [jobRequestNo=");
            logStr.append(jobContext.getJobRequestNo());
            logStr.append("]");
            logStr.append(" [partitionNo=");
            logStr.append(jobContext.getPartitionNo());
            logStr.append("]");
            logStr.append(" [StartType=");
            logStr.append(isAsync() ? JobContext.START_TYPE.ASYNC
                    : JobContext.START_TYPE.SYNC);
            logStr.append("]");
            log.debug(logStr.toString());
        }
    }

    /**
     * �W���u�̏I�����O�B
     * 
     * @param jobStatus �W���u�X�e�[�^�X
     */
    protected void printEndLog(JobStatus jobStatus) {
        if (log.isDebugEnabled()) {
            StringBuilder logStr = new StringBuilder();
            logStr.append("�y END �z");
            logStr.append(" [jobId=");
            logStr.append(jobStatus.getJobId());
            logStr.append("]");
            logStr.append(" [jobRequestNo=");
            logStr.append(jobStatus.getJobRequestNo());
            logStr.append("]");
            logStr.append(" [partitionNo=");
            logStr.append(jobStatus.getPartitionNo());
            logStr.append("]");
            logStr.append(" [StartType=");
            logStr.append(isAsync() ? JobContext.START_TYPE.ASYNC
                    : JobContext.START_TYPE.SYNC);
            logStr.append("]");
            logStr.append(" [jobExitCode=");
            logStr.append(jobStatus.getJobExitCode());
            logStr.append("]");
            log.debug(logStr.toString());
        }
    }

    /**
     * �W���u�X�e�[�^�X�̎擾�B
     * 
     * @return jobStatus �W���u�X�e�[�^�X
     */
    @Override
    public JobStatus getJobStatus() {
        return jobStatus;
    }

    /**
     * �W���u�R���e�L�X�g�̐ݒ�B
     * 
     * @param jobContext �W���u�R���e�L�X�g
     */
    public void setJobContext(JobContext jobContext) {
        this.jobContext = jobContext;
    }
}
