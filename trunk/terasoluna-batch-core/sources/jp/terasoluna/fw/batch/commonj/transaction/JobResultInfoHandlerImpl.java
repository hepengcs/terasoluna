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

package jp.terasoluna.fw.batch.commonj.transaction;

import jp.terasoluna.fw.dao.UpdateDAO;
import jp.terasoluna.fw.batch.jobmessage.JobMessageInfo;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;

/**
 * �W���u���s���ʃe�[�u���ɓo�^����n���h���B<br>
 * 
 * 
 */
public class JobResultInfoHandlerImpl implements JobResultInfoHandler {

    /**
     * ���O�C���X�^���X�B
     */
    private static Log log = LogFactory.getLog(
                JobResultInfoHandlerImpl.class);

    /**
     * UPDATE�pDAO�B Spring��DI�@�\��p���Đݒ肷��B
     */
    private UpdateDAO updateDAO = null;

    /**
     * �g�����U�N�V�����}�l�[�W���B
     */
    private PlatformTransactionManager transactionManager = null;

    /**
     * �W���u���s�Ǘ��֓o�^����SQLID�B
     */
    private static final String INSERT_JOB_RESULT 
            = "jobResult.INSERT_JOB_RESULT";

    /**
     * JOB_RESULT�e�[�u���ɃW���u�̎��s���ʂ�o�^����B
     * 
     * @param jobMessageInfo �W���u�Ǘ����
     * @return �o�^����
     */
    public int handle(JobMessageInfo jobMessageInfo) {
        if (log.isDebugEnabled()) {
            log.debug("Job Result Handler [regist] transaction start...");
        }
        int count = 0;
        TransactionStatus transactionStatus = transactionManager
                .getTransaction(null);
        try {
            count = updateDAO.execute(INSERT_JOB_RESULT, jobMessageInfo);
            if (!transactionStatus.isCompleted()) {
                transactionManager.commit(transactionStatus);
                if (log.isDebugEnabled()) {
                    log.debug(
                            "Job Result Handler [regist] transaction commit.");
                }
            }
        } catch (UncategorizedSQLException e) {
            if (!transactionStatus.isCompleted()) {
                transactionManager.rollback(transactionStatus);
            }
            log.error(e.getMessage());
        }
        return count;
    }

    /**
     * UPDATE�pDAO�B
     * 
     * @param updateDAO UPDATE�pDAO
     */
    public void setUpdateDAO(UpdateDAO updateDAO) {
        this.updateDAO = updateDAO;
    }

    /**
     * �g�����U�N�V�����}�l�[�W���B
     * 
     * @param transactionManager �g�����U�N�V�����}�l�[�W��
     */
    public void setTransactionManager(
            PlatformTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

}
