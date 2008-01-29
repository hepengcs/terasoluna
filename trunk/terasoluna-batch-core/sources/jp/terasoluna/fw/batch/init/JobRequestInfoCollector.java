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

package jp.terasoluna.fw.batch.init;

import jp.terasoluna.fw.batch.core.AbstractCollector;
import jp.terasoluna.fw.batch.core.CollectedDataHandler;
import jp.terasoluna.fw.batch.core.CollectorException;
import jp.terasoluna.fw.batch.core.CollectorResult;
import jp.terasoluna.fw.batch.core.JobStatus;
import jp.terasoluna.fw.batch.openapi.JobContext;
import jp.terasoluna.fw.batch.openapi.ReturnCode;
import jp.terasoluna.fw.batch.springsupport.transaction.TransactionalJobStatus;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * �񓯊��W���u�N�����̃W���u�˗����擾�p�N���X�B
 *
 */
public class JobRequestInfoCollector extends AbstractCollector {

    /**
     * ���O�C���X�^���X�B
     */
    private static Log log = LogFactory
            .getLog(JobRequestInfoCollector.class);
    
    /**
     * �W���u�Ǘ��e�[�u���n���h���N���X�̃C���X�^���X�B
     */
    protected JobControlTableHandler jobControlTableHandler = null;

    /**
     * �W���u�Ǘ��e�[�u���̊Ď������i�b�j�B
     */
    private long intervalSeconds = 0L;

    /**
     * ���t���b�V���J�E���g�B
     */
    private int refreshCount = 0;

    /**
     * ���s�W���u��`�t�@�C���p�X�B
     */
    protected String jobBeanPath = null;

    /**
     * �W���u�˗����擾�A�Ď������̎��s�B<BR>
     * �W���u�Ď����s���擾�Ώۂ̃W���u�������ꍇ�̓f�[�����N���p�W���uBean��`
     * �̊Ď��������Ԃ�ҋ@������A�Ď擾�������s���B<BR>
     * �W���u�˗����̎擾�͒��O�擾�����W���u���N�G�X�g�ԍ����傫�����̂���
     * �����邪���̏����̓f�[�����N���p�W���uBean��`�̃��t���b�V���ԍ��̉񐔖�
     * �Ƀ��t���b�V�������B
     * 
     *
     * @param jobContext �W���u�R���e�L�X�g
     * @param collectedDataHandler ���[�J�L���[
     * @param jobStatus ������
     * @return �R���N�^��������
     */
    @Override
    protected CollectorResult doCollect(JobContext jobContext, 
            CollectedDataHandler collectedDataHandler,
            JobStatus jobStatus) {

        int collected = 0;
        int selectCount = 0;
        JobInfo jobControlInfo = null;
        
        TransactionalJobStatus transactionalJobStatus = 
            (TransactionalJobStatus) jobStatus;

        while (true) {
            
            jobControlInfo = getJobMessageInfo(selectCount, jobControlInfo);

            if (jobControlInfo == null) {
                try {
                    Thread.sleep(intervalSeconds * 1000);
                    selectCount = 0;
                } catch (InterruptedException e) {
                    throw new CollectorException(e, null);
                }
            } else {
                transactionalJobStatus.beginTransaction();
                //�L���[�ɓ����O�ɃW���u�N���󋵂��J�n�ɍX�V����B
                int count = updateJobStart(jobControlInfo);
                
                // �X�V�ɐ��������ꍇ�̂݌��ʂ��L���[�ɓ����B
                if (count == 1) {
                    collectedDataHandler.handle(jobControlInfo, collected++);
                    transactionalJobStatus.commit();
                } else {
                    transactionalJobStatus.rollback();
                }
                
                if (jobBeanPath != null) {
                    overrideDescriptionPath(jobControlInfo);
                }

                // �f�[�����I���`�F�b�N
                if (AbstractJobControlInfo.STOP_DEMON.equals(jobControlInfo
                        .getJobId())) {
                    if (log.isDebugEnabled()) {
                        log.debug("stopped batch daemon ");
                    }
                    collectedDataHandler.close();
                    return 
                        new CollectorResult(ReturnCode.NORMAL_END, collected);
                }
                
                // �e�[�u���̌�����ʒu������������B
                if (selectCount >= refreshCount) {
                    selectCount = 0;
                } else {
                    selectCount++;
                }
            }
        }
    }

    
    /**
     * �L���[�ɓ����O�ɃW���u�N���󋵂��J�n�ɍX�V����
     * 
     * @param jobInfo �ΏۃW���u���
     * @return �X�V�s��
     */
    protected int updateJobStart(JobInfo jobInfo) {
        return jobControlTableHandler.updateJobStart(jobInfo);
    }
    
    /**
     * �����Ώۃf�[�^�̎擾
     * 
     * @param selectCount �擾�f�[�^��
     * @param jobInfo �ΏۃW���u���
     * @return �ΏۃW���u���
     */
    protected JobInfo getJobMessageInfo(int selectCount, JobInfo jobInfo) {
        // �W���u�˗����̎擾
        if (selectCount == 0) {
            return jobControlTableHandler.getJobRequestData();
        } else {
            return jobControlTableHandler.getJobRequestData(jobInfo);
        }
    }

    /**
     * �W���uBean��`�p�X�̏㏑��
     * 
     * @param jobInfo �ΏۃW���u��� 
     */
    protected void overrideDescriptionPath(JobInfo jobInfo) {
        // �㏑���͂��Ȃ�
    }
    
    /**
     * �W���u�Ǘ��e�[�u���n���h���N���X�̃C���X�^���X��ݒ肷��B
     *
     * @param jobControlTableHandler �W���u�Ǘ��e�[�u���n���h���N���X�̃C���X�^
     * ���X
     */
    public void setJobControlTableHandler(
            JobControlTableHandler jobControlTableHandler) {
        this.jobControlTableHandler = jobControlTableHandler;
    }

    /**
     * �Ď�������ݒ肷��B
     *
     * @param intervalSeconds �Ď������i�b�j
     */
    public void setIntervalSeconds(long intervalSeconds) {
        this.intervalSeconds = intervalSeconds;
    }
    
    /**
     * ���t���b�V���J�E���g��ݒ肷��B
     * @param refreshCount ���t���b�V���J�E���g�B
     */
    public void setRefreshCount(int refreshCount) {
        this.refreshCount = refreshCount;
    }
    
    /**
     * ���s�W���u��`�t�@�C���p�X�B
     * 
     * @param jobBeanPath ���s�W���u��`�t�@�C���p�X
     */
    public void setJobBeanPath(String jobBeanPath) {
        throw new UnsupportedOperationException();
    }
}
