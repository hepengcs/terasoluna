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

import jp.terasoluna.fw.batch.commonj.listener.WorkMapListener;
import jp.terasoluna.fw.batch.core.InitializeException;
import jp.terasoluna.fw.batch.init.JobRequestInfo;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import commonj.work.WorkException;
import commonj.work.WorkItem;
import commonj.work.WorkManager;

/**
 * CommonJ�p�W���u�N���N���X�B
 * 
 * 
 */
public class JobStarterImpl implements JobStarter {

    /**
     * ���O�C���X�^���X�B
     */
    private static Log log = LogFactory.getLog(JobStarterImpl.class);

    /**
     * ���[�N�}�l�[�W���ւ̍�ƈ˗������B
     */
    private static final int SCHDULE_SUCCESS = 0;
    
    /**
     * ���[�N�}�l�[�W���ւ̍�ƈ˗����s�B
     */
    private static final int SCHDULE_ERROR = -1;
 
    /**
     * ���[�N�}�l�[�W���[�B
     */
    private WorkManager workManager = null;

    /**
     * ���[�N���X�i�[�B
     */
    private WorkMapListener workListener = null;

    /**
     * �W���u�̎��s�B
     * @param jobId �W���u�h�c
     * @param beanFileName �W���uBean��`�t�@�C��
     * @param args �W���u�p�����[�^
     * @return ���s����
     */
    public int execute(String jobId, String beanFileName, String[] args) {
        if (workManager == null) {
            throw new InitializeException("WorkManager is required.");
        }
        if (workListener == null) {
            throw new InitializeException("WorkListener is required.");
        }
        
        // �������P�̔z��ɂ܂Ƃ߂�
        String[] newArgs = new String[args.length + 2];
        newArgs[0] = jobId;
        newArgs[1] = beanFileName;
        System.arraycopy(args, 0, newArgs, 2, args.length);
        
        JobRequestInfo jobInfo = new JobRequestInfo(newArgs);
        jobInfo.init();
        JobExecutor jobExecutor = new JobExecutor();
        jobExecutor.setJobInfo(jobInfo);
        try {
            WorkItem workItem = workManager.schedule(jobExecutor, workListener);
            workListener.addWork(workItem, jobExecutor);
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage());
            return SCHDULE_ERROR;
        } catch (WorkException e) {
            log.error(e.getMessage());
            return SCHDULE_ERROR;
        }
        return SCHDULE_SUCCESS;
    }

    /**
     * ���[�N���X�i�[�̐ݒ�B
     * 
     * @param workListener ���[�N���X�i�[
     */
    public void setWorkListener(WorkMapListener workListener) {
        this.workListener = workListener;
    }

    /**
     * ���[�N�}�l�[�W���[�̐ݒ�B
     * 
     * @param workManager ���[�N�}�l�[�W���[
     */
    public void setWorkManager(WorkManager workManager) {
        this.workManager = workManager;
    }

}
