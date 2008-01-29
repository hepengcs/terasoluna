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

package jp.terasoluna.fw.batch.springsupport.transaction;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;

import jp.terasoluna.fw.batch.core.JobStatus;
import jp.terasoluna.fw.batch.openapi.JobContext;

/**
 * トランザクション情報を保持するジョブステータスクラス。
 * 
 */
public class TransactionalJobStatus extends JobStatus {
    
    /**
     * セーブポイント。 
     */
    private Object savepoint = null;
    
    /**
     * トランザクションマネージャ。 
     */
    private PlatformTransactionManager transactionManager = null;
    
    /**
     * 実行中のトランザクションのトランザクションステータス。 
     */
    private TransactionStatus transactionStatus = null;
    
    /**
     * セーブポイントを使うかどうかのフラグ。
     */
    private boolean useSavepoint = false;

    /**
     * トランザクションを取得し設定する。
     *
     */
    public void beginTransaction() {
        transactionStatus = transactionManager.getTransaction(null);
    }
    
    /**
     * トランザクションをコミットする。
     *
     */
    public void commit() {
        if (!transactionStatus.isCompleted()) {
            transactionManager.commit(transactionStatus);
            savepoint = null;
            incrementCommitCount();
        }
    }

    /**
     * 子ジョブ処理状況を取得する。
     * 
     * @param jobContext ジョブコンテキスト
     * @return 子ジョブ処理状況
     */
    public JobStatus getChild(JobContext jobContext) {
        TransactionalJobStatus childJobStatus = new TransactionalJobStatus();
        childJobStatus.setTransactionManager(this.transactionManager);
        childJobStatus.setUseSavepoint(this.useSavepoint);
        resetChildData(childJobStatus, jobContext);
        return childJobStatus;    
    }

    /**
     * トランザクションマネージャを取得する。
     * 
     * @return トランザクションマネージャ
     */
    protected PlatformTransactionManager getTransactionManager() {
        return transactionManager;
    }

    /**
     * セーブポイントを作成していた場合には解放し、新たにセーブポイントを作成
     * する。 
     *
     */
    public void releaseAndCreateSavepoint() {
        if (!useSavepoint) {
            return;
        }
        
        if (savepoint != null) {
            transactionStatus.releaseSavepoint(savepoint);
        }
    
        // セーブポイントの作成
        savepoint = transactionStatus.createSavepoint();
    }

    /**
     * トランザクションをロールバックする。
     *
     */
    public void rollback() {
        if (!transactionStatus.isCompleted() && useSavepoint 
                && savepoint != null) {
            // セーブポイントまでのロールバック
            transactionStatus.rollbackToSavepoint(savepoint);
        } else if (!transactionStatus.isCompleted()) {
            transactionManager.rollback(transactionStatus);
        }
    }

    /**
     * セーブポイントまでトランザクションをロールバックする。
     *
     */
    public void rollbackToSavepoint() {
        if (!useSavepoint) {
            return;
        }

        if (!transactionStatus.isCompleted() && savepoint != null) {
            // セーブポイントまでのロールバック
            transactionStatus.rollbackToSavepoint(savepoint);
        }
    }

    /**
     * トランザクションマネージャを設定する。
     * 
     * @param transactionManager トランザクションマネージャ
     */
    public void setTransactionManager(
            PlatformTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    /**
     * セーブポイントを使うかどうかを設定する。
     * 
     * @param useSavepoint セーブポイントを使う場合には、<code>true</code>
     */
    public void setUseSavepoint(boolean useSavepoint) {
        this.useSavepoint = useSavepoint;
    }

    /**
     * セーブポイントを使うかどうかを返す。
     * 
     * @return セーブポイントを使う場合には、<code>true</code>
     */
    public boolean useSavepoint() {
        return useSavepoint;
    }
    
}
