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

import jp.terasoluna.fw.batch.core.JobException;
import jp.terasoluna.fw.batch.core.JobExceptionHandler;
import jp.terasoluna.fw.batch.core.JobStatus;
import jp.terasoluna.fw.batch.openapi.JobContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <code>JobExceptionHandler</code> �C���^�t�F�[�X�̕W�������N���X�B
 * 
 * <p>���̃N���X�ł́A��O�������O�ɏo�͂��A�W���u�X�e�[�^�X�� 
 * <code>JobStatus.STATE.ENDING_ABNORMALLY</code>
 * �ɍX�V����B</p>
 * 
 */
public class StandardJobExceptionHandler implements JobExceptionHandler {

    /**
     * ���O�C���X�^���X�B
     */
    private static Log log = 
        LogFactory.getLog(StandardJobExceptionHandler.class);

    /**
     * �W���u��O����������B
     * 
     * @param jobContext �W���u�R���e�N�X�g
     * @param jobException �W���u��O
     * @param jobStatus �W���u�X�e�[�^�X
     */
    public void handlException(JobContext jobContext, 
            JobException jobException, JobStatus jobStatus) {
        writeErrorLog(jobStatus, jobException);
        jobStatus.setJobState(JobStatus.STATE.ENDING_ABNORMALLY);
    }

    /**
     * �W���u��O�̃G���[���O���o�͂���B
     * 
     * @param jobStatus �W���u�X�e�[�^�X
     * @param jobException �W���u��O
     */
    protected void writeErrorLog(JobStatus jobStatus, 
            JobException jobException) {
        log.error("Error JobID: " + jobStatus.getJobId(), jobException);
    }
}
