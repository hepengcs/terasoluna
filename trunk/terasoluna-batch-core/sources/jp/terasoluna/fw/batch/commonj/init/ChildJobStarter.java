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
import jp.terasoluna.fw.batch.openapi.JobContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import commonj.work.WorkException;
import commonj.work.WorkItem;
import commonj.work.WorkManager;

/**
 * CommonJ�p�q�W���u�N���N���X�B<br>
 * �q�W���u�p��JobExecutor��WorkManager�ɃX�P�W���[������B
 * 
 * 
 */
public class ChildJobStarter {

    /**
     * ���O�C���X�^���X�B
     */
    private static Log log = LogFactory.getLog(ChildJobStarter.class);

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
     * 
     * @param args �W���u�p�����[�^
     * @param jobContext �W���u�R���e�L�X�g
     * @return ���s����
     */
    public int execute(String[] args, JobContext jobContext) {
        if (workManager == null) {
            throw new InitializeException("WorkManager is required.");
        }
        if (workListener == null) {
            throw new InitializeException("WorkListener is required.");
        }
        JobRequestInfo jobInfo = new JobRequestInfo(args);
        jobInfo.init();
        ChildJobExecutor childJobExecutor = new ChildJobExecutor();
        childJobExecutor.setJobInfo(jobInfo);
        childJobExecutor.setJobContext(jobContext);
        try {
            WorkItem workItem = workManager.schedule(
                    childJobExecutor, workListener);
            workListener.addWork(workItem, childJobExecutor);
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
