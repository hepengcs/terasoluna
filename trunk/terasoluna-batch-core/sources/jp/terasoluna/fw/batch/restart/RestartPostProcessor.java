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

import java.util.List;

import jp.terasoluna.fw.batch.core.JobStatus;
import jp.terasoluna.fw.batch.core.SupportProcessor;
import jp.terasoluna.fw.batch.openapi.JobContext;
import jp.terasoluna.fw.batch.openapi.SupportLogic;

/**
 * �W���u���X�^�[�g���̃W���u�㏈���p�N���X�B<BR>
 * �W���u�㏈��������ɏI�������烊�X�^�[�g�|�C���g�N���A�������s���B
 * 
 */
public class RestartPostProcessor implements SupportProcessor {

    /**
     * �W���u���X�^�[�g�e�[�u���n���h���̃C���X�^���X
     */
    private JobRestartTableHandler jobRestartTableHandler = null;

    /**
     * �W���u�㏈���p�v���Z�b�T�[
     */
    private SupportProcessor postProcessor = null;

    /**
     * �T�|�[�g���W�b�N�̃��X�g�B
     */
    private List<SupportLogic<JobContext>> supportLogicList = null;

    /**
     * ���X�^�[�g�|�C���g�N���A�pSQL�L�[�B
     */
    private String sqlKey = null;
    
    /**
     * Root�}�l�W���[�L��
     */
    private boolean parentManager = false;

    /**
     * �W���u�㏈���p�v���Z�b�T�[���\�b�h�B<BR>
     * �W���u�㏈��������ɏI�������烊�X�^�[�g�|�C���g�N���A�������s���B
     * 
     * @param jobContext
     *            �W���u�R���e�L�X�g
     * @param jobStatus
     *            �W���u������
     */
    public void process(JobContext jobContext, JobStatus jobStatus) {
        postProcessor.process(jobContext, jobStatus);

        //���X�^�[�g�|�C���g�̃N���A�����i�폜�j
        //�W���u���ُ�I�����Ă��Ȃ�
        if (!isAbendOrSuspending(jobStatus)
                && (parentManager || (supportLogicList != null 
                && supportLogicList.size() > 0))) {
            jobRestartTableHandler.restartPointClear(jobStatus, sqlKey);
        }
    }

    /**
     * �W���u���X�^�[�g�e�[�u���n���h���̃C���X�^���X��ݒ肷��B
     * 
     * @param jobRestartTableHandler
     *            �W���u���X�^�[�g�e�[�u���n���h���̃C���X�^���X
     */
    public void setJobRestartTableHandler(
            JobRestartTableHandler jobRestartTableHandler) {
        this.jobRestartTableHandler = jobRestartTableHandler;
    }

    /**
     * �����X�L�b�v��]������B
     * 
     * @return �]�����ʁi���X�^�[�g�e�[�u���N���A�����́A�Ɩ��I�Ȍ㏈�����Ȃ�
     * �ꍇ�ł��X�L�b�v���Ȃ��j
     */
    public boolean canSkip() {
        // ���X�^�[�g�e�[�u���N���A�����́A�Ɩ��I�Ȍ㏈�����Ȃ��ꍇ�ł��X�L�b�v
        // ���Ȃ�
        return false;
    }

    /**
     * �W���u�㏈����ݒ肷��B
     * 
     * @param postProcessor
     *            �W���u�㏈��
     */
    public void setPostProcessor(SupportProcessor postProcessor) {
        this.postProcessor = postProcessor;
    }

    /**
     * �T�|�[�g���W�b�N�̃��X�g��ݒ肷��B
     * 
     * @param supportLogicList �T�|�[�g���W�b�N�̃��X�g
     */
    public void setSupportLogicList(
            List<SupportLogic<JobContext>> supportLogicList) {
        this.supportLogicList = supportLogicList;
    }
    
    /**
     * ���X�^�[�g�|�C���g�N���A�pSQL�L�[��ݒ肷��B
     * 
     * @param sqlKey ���X�^�[�g�|�C���g�N���A�pSQL�L�[
     */
    public void setSqlKey(String sqlKey) {
        this.sqlKey = sqlKey;
    }

    /**
     *  Root�}�l�W���[�L����ݒ肷��B
     * 
     * @param parentManager Root�}�l�W���[�L��
     */
    public void setParentManager(boolean parentManager) {
        this.parentManager = parentManager;
    }
    
    /**
     *  JobStatus�̏�Ԃ��m�F����B
     * 
     * @param jobStatus �W���u������
     * @return �e�A�q��JobStatus�̏�����Ԓ�<code>ENDING_ABNORMALLY</code>�A
     * <code>SUSPENDING</code>����ł������<code>true</code>��Ԃ��B
     */
    private boolean isAbendOrSuspending(JobStatus jobStatus) {
        if (jobStatus.getChildJobStatusList() == null
                || jobStatus.getChildJobStatusList().isEmpty()) {

            if (jobStatus.getJobState() == JobStatus.STATE.ENDING_ABNORMALLY
                    || jobStatus.getJobState() == JobStatus.STATE.SUSPENDING) {
                return true;
            } else {
                return false;
            }
        }

        for (JobStatus childJobStatus : jobStatus.getChildJobStatusList()) {
            if (isAbendOrSuspending(childJobStatus)) {
                return true;
            }
        }
        return false;
    }
}
