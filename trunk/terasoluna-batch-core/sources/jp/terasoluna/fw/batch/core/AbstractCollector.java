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

package jp.terasoluna.fw.batch.core;

import jp.terasoluna.fw.batch.openapi.JobContext;
import jp.terasoluna.fw.batch.openapi.ReturnCode;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * �R���N�^�[���ۃN���X�B
 *
 * <p>
 * �Ώۃf�[�^�擾�������J�n����B<br>
 * �Ώۃf�[�^�擾�����͂���<code>Abstract</code>
 * �N���X�����������N���X�ɂčs���B
 * </p>
 * 
 * @see jp.terasoluna.fw.batch.ibatissupport.IBatisDbCollectorImpl
 * @see jp.terasoluna.fw.batch.init.JobRequestInfoCollector
 * @see jp.terasoluna.fw.batch.standard.ListPropertyCollector
 * @see jp.terasoluna.fw.batch.standard.StringArrayPropertyCollector
 * @param <T> �W���u�R���e�N�X�g�̃T�u�N���X
 */
public abstract class AbstractCollector<T extends JobContext> 
    implements Collector<T> {

    /**
     * ���O�C���X�^���X�B
     */
    private static Log log = LogFactory.getLog(AbstractCollector.class);

    /**
     * �Ώۃf�[�^�n���h���B
     */
    private CollectedDataHandlerFactory collectedDataHandlerFactory = null;
    
    /**
     * �Ώۃf�[�^�擾�������J�n����B
     *
     * @param jobContext �W���u�R���e�N�X�g
     * @param workQueue ���[�N�L���[
     * @param jobStatus �W���u�X�e�[�^�X
     * @return �R���N�^�̏�������
     */
    public CollectorResult collect(T jobContext, WorkQueue workQueue,
            JobStatus jobStatus) {
        //�J�n���O
        writeStartLog(jobStatus);
        CollectedDataHandler collectedDataHandler
            = collectedDataHandlerFactory.getHandler(workQueue, jobContext);
        CollectorResult collectorResult = null;
        try {
            collectorResult = doCollect(jobContext, collectedDataHandler,
                    jobStatus);
            collectedDataHandler.close();
        } catch (QueueingException e) {
            if (jobStatus.isExecuting()) {
                collectedDataHandler.close();
                throw e;
            } else {
                collectorResult =
                    new CollectorResult(ReturnCode.NORMAL_END, -1);
            }
        } catch (RuntimeException e) {
            collectedDataHandler.close();
            throw e;
        }


        writeEndLog(jobStatus);
        return collectorResult;
    }

    /**
     * ���W�f�[�^�����n���h����ݒ肷��B
     * 
     * @param collectedDataHandlerFactory ���W�����f�[�^����������n���h��
     */
    public void setCollectedDataHandlerFactory(
            CollectedDataHandlerFactory collectedDataHandlerFactory) {
        this.collectedDataHandlerFactory = collectedDataHandlerFactory;
    }

    /**
     * �Ώۃf�[�^���擾����B
     *
     * @param jobContext �W���u�R���e�N�X�g
     * @param collectedDataHandler ���W�����f�[�^����������n���h��
     * @param jobStatus �W���u�X�e�[�^�X
     * @return �R���N�^�̏�������
     */
    protected abstract CollectorResult doCollect(T jobContext,
            CollectedDataHandler collectedDataHandler, JobStatus jobStatus);

    /**
     * AbstractCollector�̊J�n���O���o�͂���B
     * 
     * @param jobStatus �W���u�X�e�[�^�X
     */
    private void writeStartLog(JobStatus jobStatus) {
        if (log.isDebugEnabled()) {
            StringBuilder logStr = new StringBuilder();
            logStr.append("�ySTART�z");
            logStr.append(" [jobId=");
            logStr.append(jobStatus.getJobId());
            logStr.append("]");
            logStr.append(" [jobRequestNo=");
            logStr.append(jobStatus.getJobRequestNo());
            logStr.append("]");
            logStr.append(" [partitionNo=");
            logStr.append(jobStatus.getPartitionNo());
            logStr.append("]");
            logStr.append(" [ClassName=");
            logStr.append(this.getClass().getSimpleName());
            logStr.append("]");
            logStr.append(" [JobState=");
            logStr.append(jobStatus.getJobState());
            logStr.append("]");
            log.debug(logStr.toString());
        }
    }

    /**
     * AbstractCollector�̏I�����O���o�͂���B
     * 
     * @param jobStatus �W���u�X�e�[�^�X
     */
    private void writeEndLog(JobStatus jobStatus) {
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
            logStr.append(" [ClassName=");
            logStr.append(this.getClass().getSimpleName());
            logStr.append("]");
            logStr.append(" [JobState=");
            logStr.append(jobStatus.getJobState());
            logStr.append("]");
            logStr.append(" [collected=");
            logStr.append(jobStatus.getCollected());
            logStr.append("]");
            log.debug(logStr.toString());
        }
    }
}
