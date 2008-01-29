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

import jp.terasoluna.fw.batch.core.CollectorResult;
import jp.terasoluna.fw.batch.core.CollectorResultHandler;
import jp.terasoluna.fw.batch.core.JobStatus;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <code>CollectorResultHandler</code> �C���^�t�F�[�X�̕W�������N���X�B
 * 
 * <p>���̃N���X�ł́A�R���N�^�������ʂ�0���ł���ꍇ�ɂ��G���[�Ƃ����ɂ��̂܂�
 * �p������B</p>
 * 
 * <p>���̃N���X�ł́A�R���N�^�̏������ʂɂ�����炸��ɏ��/�G���[���O�ɏo��
 * ����B�������A��񃍃O�̓R���N�^�������ʂ�0���ł������ꍇ�ɏo�͂���B</p>
 * 
 */
public class StandardCollectorResultHandler 
    implements CollectorResultHandler {

    /**
     * ���O�C���X�^���X�B
     */
    private static Log log = 
        LogFactory.getLog(StandardCollectorResultHandler.class);

    /**
     * �R���N�^�������ʂ���������B
     * 
     * @param collectorResult �R���N�^��������
     * @param jobStatus �W���u�X�e�[�^�X
     */
    public void handle(CollectorResult collectorResult, JobStatus jobStatus) {
        switch (collectorResult.getReturnCode()) {
            case NORMAL_CONTINUE:
            case NORMAL_END:
                if (collectorResult.getCollected() == 0) {
                    writeInfoLog(jobStatus, collectorResult);
                }
                break;
            case ERROR_CONTINUE:
                writeErrorLog(jobStatus, collectorResult);
                break;
            case ERROR_END:
                jobStatus.setJobState(JobStatus.STATE.ENDING_ABNORMALLY);
                writeErrorLog(jobStatus, collectorResult);
                break;
            default:
                throw new IllegalArgumentException(
                        collectorResult.getCollected() + " illegal ReturnCode");
        }
    }

    /**
     * �R���N�^�������ʂł̏�񃍃O���o�͂���B
     * 
     * @param jobStatus �W���u�X�e�[�^�X
     * @param collectorResult �R���N�^��������
     */
    protected void writeInfoLog(JobStatus jobStatus,
            CollectorResult collectorResult) {
        StringBuilder logStr = new StringBuilder();
        logStr.append(" [jobId=");
        logStr.append(jobStatus.getJobId());
        logStr.append("]");
        logStr.append(" [jobRequestNo=");
        logStr.append(jobStatus.getJobRequestNo());
        logStr.append("]");
        logStr.append(" [partitionNo=");
        logStr.append(jobStatus.getPartitionNo());
        logStr.append("]");
        logStr.append(" [CollectorResult Info:");
        logStr.append(collectorResult);
        logStr.append("]");
        log.info(logStr.toString());
    }

    /**
     * �R���N�^�������ʂł̃G���[���O���o�͂���B
     * 
     * @param jobStatus �W���u�X�e�[�^�X
     * @param collectorResult �R���N�^��������
     */
    protected void writeErrorLog(JobStatus jobStatus,
            CollectorResult collectorResult) {
        StringBuilder logStr = new StringBuilder();
        logStr.append(" [jobId=");
        logStr.append(jobStatus.getJobId());
        logStr.append("]");
        logStr.append(" [jobRequestNo=");
        logStr.append(jobStatus.getJobRequestNo());
        logStr.append("]");
        logStr.append(" [partitionNo=");
        logStr.append(jobStatus.getPartitionNo());
        logStr.append("]");
        logStr.append(" [CollectorResult Error:");
        logStr.append(collectorResult);
        logStr.append("]");
        log.error(logStr.toString());
    }
}
