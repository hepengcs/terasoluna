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

import jp.terasoluna.fw.batch.core.JobException;
import jp.terasoluna.fw.batch.core.JobManager;
import jp.terasoluna.fw.batch.core.JobStatus;
import jp.terasoluna.fw.batch.core.WorkUnit;
import jp.terasoluna.fw.batch.core.Workable;
import jp.terasoluna.fw.batch.openapi.JobContext;
import jp.terasoluna.fw.batch.springsupport.init.JobStarter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>�W���u�R���e�N�X�g�̕����������s���N���X�B</p>
 * 
 * <p>���X�^�[�g�������s���W���u�ł́A�W���u�}�l�[�W�����Ă΂��ӏ���
 * ���̃N���X�����ݍ��ނ悤�ɐݒ肷��B���ۂɏ������s���W���u�}�l�[�W���́A
 * ���̃N���X�� <code>jobManager</code> �v���p�e�B�ɐݒ肷��B
 * ���̃N���X�ł� <code>work()</code> ���\�b�h�ł́A
 * ���X�^�[�g�������s������ŁA���ۂ̏����� <code>jobManager</code> 
 * �v���p�e�B�ɐݒ肳�ꂽ�W���u�}�l�[�W���ɏ������Ϗ�����B</p>
 * 
 * <p>�W���u�Ǘ��e�[�u������̃W���u�R���e�N�X�g�̕����́A<code>
 * jobRestartTableHandler</code> �v���p�e�B�ɐݒ肳�ꂽ�W���u���X�^�[�g
 * �e�[�u���n���h���ɂ���čs����B</p>
 * 
 */
public class JobContextRestorer implements Workable<WorkUnit> {

    /**
     * ���O�C���X�^���X�B
     */
    private static Log log = LogFactory.getLog(JobStarter.class);

    /**
     * �W���u�}�l�[�W��
     */
    private JobManager jobManager = null;
    
    /**
     * �W���u���X�^�[�g�e�[�u���n���h��
     */
    private JobRestartTableHandler jobRestartTableHandler = null;
    
    /**
     * �W���u�}�l�[�W����ݒ肷��B
     *
     * @param jobManager �W���u�}�l�[�W��
     */
    public void setJobManager(JobManager jobManager) {
        this.jobManager = jobManager;
    }

    /**
     * �W���u���X�^�[�g�e�[�u���n���h����ݒ肷��B
     *
     * @param jobRestartTableHandler �W���u���X�^�[�g�e�[�u���n���h��
     */
    public void setJobRestartTableHandler(
            JobRestartTableHandler jobRestartTableHandler) {
        this.jobRestartTableHandler = jobRestartTableHandler;
    }

    /**
     * �W���u�R���e�L�X�g�p�����B
     * ���X�^�[�g�����̏ꍇ�̓W���u���X�^�[�g�Ǘ��e�[�u������W���u�R���e�L�X�g
     * ���擾���A�O��W���u�I�����_�̃W���u�R���e�L�X�g��Ԃ𕜌�����B
     * 
     * @param element ��ƒP��
     * @param jobStatus �W���u�X�e�[�^�X
     */
    public void work(WorkUnit element, JobStatus jobStatus) {
        //�W���u�R���e�L�X�g�Ƀ��X�^�[�g�L���ݒ�
        JobContext jobContext = element.getJobContext();
        JobStatus childJobStatus = jobStatus.getChild(element.getJobContext());
        JobContext newJobContext = null;
        try {
            newJobContext = jobRestartTableHandler.getRestartJobContext(
                    jobContext, childJobStatus);
        } catch (JobException e) {
            log.error(e.getMessage(), e);
            childJobStatus.setJobState(JobStatus.STATE.ENDING_ABNORMALLY);
            return;
        }

        //�I���ς݃W���u�͎��s���Ȃ��B
        if (!childJobStatus.isContinue()) {
            return;
        }
        
        newJobContext.setRestartable(true);
        childJobStatus.setRestartable(true);

        element.setJobContext(newJobContext);
        
        RestartJobStatus restartJobStatus = 
            new RestartJobStatus(childJobStatus);
        jobManager.work(element, restartJobStatus);
    }
}
