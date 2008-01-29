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

package jp.terasoluna.fw.batch.restart;

import jp.terasoluna.fw.batch.core.JobStatus;
import jp.terasoluna.fw.batch.core.SupportProcessor;
import jp.terasoluna.fw.batch.openapi.JobContext;

/**
 * �W���u���X�^�[�g���̃W���u�O�����p�N���X�B<BR>
 * �W���u�̃��X�^�[�g�ł���΃W���u�W���u�O�������X�L�b�v����B<BR>
 * �����W���u�̐e�W���u�̑O�����̏ꍇ�͐e�W���u�p���X�^�[�g�|�C���g��o�^����B
 * 
 */
public class RestartPreProcessor implements SupportProcessor {

    /**
     * ���X�^�[�g�e�[�u���n���h���̃C���X�^���X
     */
    private JobRestartTableHandler jobRestartTableHandler = null;

    /**
     * ���s����W���u�O����
     */
    private SupportProcessor preProcessor = null;
    
    /**
     * Root�}�l�W���[�L��
     */
    private boolean parentManager = false;

    /**
     * ���X�^�[�g���̃W���u�O���������s����B
     * 
     * @param jobContext �W���u�R���e�L�X�g
     * @param jobStatus �W���u�����󋵁@
     */
    public void process(JobContext jobContext, JobStatus jobStatus) {
        // ���X�^�[�g�̏ꍇ�ɂ́A�O�����͋N�����Ȃ�
        if (jobStatus.getJobState() == JobStatus.STATE.RESTARTED) {
            return;
        }
        
        preProcessor.process(jobContext, jobStatus);
        if (!jobStatus.isContinue()) {
            return;
        }

        //Partition�̏ꍇ�̓��X�^�[�g�pRoot�o�^
        if (parentManager) {
            jobRestartTableHandler.registerRestartPoint(jobContext, jobStatus);
        }
    }
    
    /**
     * ���X�^�[�g�e�[�u���n���h���̃C���X�^���X��ݒ肷��B
     * 
     * @param jobRestartTableHandler ���X�^�[�g�e�[�u���n���h���̃C���X�^���X
     */
    public void setJobRestartTableHandler(
            JobRestartTableHandler jobRestartTableHandler) {
        this.jobRestartTableHandler = jobRestartTableHandler;
    }

    /**
     * �O�������X�L�b�v���邩��]������B
     * 
     * @return �]������
     */
    public boolean canSkip() {
        return false;
    }

    /**
     * ���s����W���u�O������ݒ肷��B
     * 
     * @param preProcessor ���s����W���u�O����
     */
    public void setPreProcessor(SupportProcessor preProcessor) {
        this.preProcessor = preProcessor;
    }

    /**
     *  Root�}�l�W���[�L����ݒ肷��B
     * 
     * @param parentManager Root�}�l�W���[�L��
     */
    public void setParentManager(boolean parentManager) {
        this.parentManager = parentManager;
    }
}
