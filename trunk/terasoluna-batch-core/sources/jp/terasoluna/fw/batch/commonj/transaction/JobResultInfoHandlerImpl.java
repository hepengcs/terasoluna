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
 * ジョブ実行結果テーブルに登録するハンドラ。<br>
 * 
 * 
 */
public class JobResultInfoHandlerImpl implements JobResultInfoHandler {

    /**
     * ログインスタンス。
     */
    private static Log log = LogFactory.getLog(
                JobResultInfoHandlerImpl.class);

    /**
     * UPDATE用DAO。 SpringのDI機能を用いて設定する。
     */
    private UpdateDAO updateDAO = null;

    /**
     * トランザクションマネージャ。
     */
    private PlatformTransactionManager transactionManager = null;

    /**
     * ジョブ実行管理へ登録するSQLID。
     */
    private static final String INSERT_JOB_RESULT 
            = "jobResult.INSERT_JOB_RESULT";

    /**
     * JOB_RESULTテーブルにジョブの実行結果を登録する。
     * 
     * @param jobMessageInfo ジョブ管理情報
     * @return 登録件数
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
     * UPDATE用DAO。
     * 
     * @param updateDAO UPDATE用DAO
     */
    public void setUpdateDAO(UpdateDAO updateDAO) {
        this.updateDAO = updateDAO;
    }

    /**
     * トランザクションマネージャ。
     * 
     * @param transactionManager トランザクションマネージャ
     */
    public void setTransactionManager(
            PlatformTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

}
