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

import jp.terasoluna.fw.batch.core.JobStatus;
import jp.terasoluna.fw.batch.core.JobStatusSummarizer;

/**
 * ������Ԃ̃T�}���C�U�����N���X�B<BR>
 * �q���̏�����ԃ��X�g���m�F���A�T�}���C�U����B<BR>
 * �f�t�H���g�����ł́A�q���̏������ʂ���d�v�Ȍ��ʂ�D�悵�A�e��ԂƂ��Đݒ�
 * ����B
 * �i�N���󋵔ԍ����傫�������d�v�x�������Ɣ��f����B�j><BR>�W���u����
 * �C���^�t�F�[�X<code>JobStatusSummarizer</code>�̎����N���X�� 
 * <code>bean</code>��`��<code>id="JobStatusSummarizer"</code>�ɒ�`���邱�Ƃ�
 * ������ύX���邱�Ƃ��ł���B<BR>
 * 
 */
public class StandardJobStatusSummarizer implements JobStatusSummarizer {

    /**
     * �W���u������Ԃ̌��ʃT�}���C�U�B
     * �W���u������Ԃ�ݒ肷��B 
     * �q�W���u�̏I����Ԃ��m�F���A�傫�����̂�D�悵�Đݒ肷��B
     * 
     * @param jobStatus
     *            �����Ώۂ̏�����
     */
    public void summarize(JobStatus jobStatus) {
        if (jobStatus.getChildJobStatusList() == null
                || jobStatus.getChildJobStatusList().isEmpty()) {
            return;
        }

        // �q���̏����󋵂��m�F���A��ԏd��Ȍ���(�W���u��Ԃ�int�\�����傫��
        // ����)��e�̏����󋵂ɐݒ肷��
        for (JobStatus childJobStatus : jobStatus.getChildJobStatusList()) {
            summarize(childJobStatus);

            if (jobStatus.getJobState().ordinal()
                    < childJobStatus.getJobState().ordinal()
                    && (jobStatus.getJobState() != JobStatus.STATE.STARTED
                    || childJobStatus.getJobState() 
                    != JobStatus.STATE.RESTARTED)) {

                jobStatus.setJobState(childJobStatus.getJobState());
                jobStatus.setJobExitCode(childJobStatus.getJobExitCode());
            }
        }
        
        summarizeExitCode(jobStatus);
    }
    
    
    /**
     * �W���u������Ԃ̏I���R�[�h�T�}���C�U�B
     * �W���u������Ԃ�ݒ肷��B 
     * �e�̏I���R�[�h���ݒ肳��Ă��Ȃ��ꍇ
     * �q�W���u�̏I���R�[�h���m�F���A�傫�����̂�D�悵�Đݒ肷��B
     * 
     * @param jobStatus
     *            �����Ώۂ̏�����
     */
    private void summarizeExitCode(JobStatus jobStatus) {
        if (jobStatus.getJobExitCode() != null) {
            return;
        }
        Integer exitCode = null;
        for (JobStatus childJobStatus : jobStatus.getChildJobStatusList()) {
            summarizeExitCode(childJobStatus);
            if (childJobStatus.getJobExitCode() != null
                    && (jobStatus.getJobState() 
                            == childJobStatus.getJobState())) {
                if (exitCode == null) {
                    exitCode = childJobStatus.getJobExitCode();
                } else {
                    if ((exitCode < childJobStatus.getJobExitCode())) {
                        exitCode = childJobStatus.getJobExitCode();
                    }
                }

            }
        }
        jobStatus.setJobExitCode(exitCode);
    }
}
