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
 * �g�����U�N�V��������ێ�����W���u�X�e�[�^�X�N���X�B
 * 
 */
public class TransactionalJobStatus extends JobStatus {
    
    /**
     * �Z�[�u�|�C���g�B 
     */
    private Object savepoint = null;
    
    /**
     * �g�����U�N�V�����}�l�[�W���B 
     */
    private PlatformTransactionManager transactionManager = null;
    
    /**
     * ���s���̃g�����U�N�V�����̃g�����U�N�V�����X�e�[�^�X�B 
     */
    private TransactionStatus transactionStatus = null;
    
    /**
     * �Z�[�u�|�C���g���g�����ǂ����̃t���O�B
     */
    private boolean useSavepoint = false;

    /**
     * �g�����U�N�V�������擾���ݒ肷��B
     *
     */
    public void beginTransaction() {
        transactionStatus = transactionManager.getTransaction(null);
    }
    
    /**
     * �g�����U�N�V�������R�~�b�g����B
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
     * �q�W���u�����󋵂��擾����B
     * 
     * @param jobContext �W���u�R���e�L�X�g
     * @return �q�W���u������
     */
    public JobStatus getChild(JobContext jobContext) {
        TransactionalJobStatus childJobStatus = new TransactionalJobStatus();
        childJobStatus.setTransactionManager(this.transactionManager);
        childJobStatus.setUseSavepoint(this.useSavepoint);
        resetChildData(childJobStatus, jobContext);
        return childJobStatus;    
    }

    /**
     * �g�����U�N�V�����}�l�[�W�����擾����B
     * 
     * @return �g�����U�N�V�����}�l�[�W��
     */
    protected PlatformTransactionManager getTransactionManager() {
        return transactionManager;
    }

    /**
     * �Z�[�u�|�C���g���쐬���Ă����ꍇ�ɂ͉�����A�V���ɃZ�[�u�|�C���g���쐬
     * ����B 
     *
     */
    public void releaseAndCreateSavepoint() {
        if (!useSavepoint) {
            return;
        }
        
        if (savepoint != null) {
            transactionStatus.releaseSavepoint(savepoint);
        }
    
        // �Z�[�u�|�C���g�̍쐬
        savepoint = transactionStatus.createSavepoint();
    }

    /**
     * �g�����U�N�V���������[���o�b�N����B
     *
     */
    public void rollback() {
        if (!transactionStatus.isCompleted() && useSavepoint 
                && savepoint != null) {
            // �Z�[�u�|�C���g�܂ł̃��[���o�b�N
            transactionStatus.rollbackToSavepoint(savepoint);
        } else if (!transactionStatus.isCompleted()) {
            transactionManager.rollback(transactionStatus);
        }
    }

    /**
     * �Z�[�u�|�C���g�܂Ńg�����U�N�V���������[���o�b�N����B
     *
     */
    public void rollbackToSavepoint() {
        if (!useSavepoint) {
            return;
        }

        if (!transactionStatus.isCompleted() && savepoint != null) {
            // �Z�[�u�|�C���g�܂ł̃��[���o�b�N
            transactionStatus.rollbackToSavepoint(savepoint);
        }
    }

    /**
     * �g�����U�N�V�����}�l�[�W����ݒ肷��B
     * 
     * @param transactionManager �g�����U�N�V�����}�l�[�W��
     */
    public void setTransactionManager(
            PlatformTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    /**
     * �Z�[�u�|�C���g���g�����ǂ�����ݒ肷��B
     * 
     * @param useSavepoint �Z�[�u�|�C���g���g���ꍇ�ɂ́A<code>true</code>
     */
    public void setUseSavepoint(boolean useSavepoint) {
        this.useSavepoint = useSavepoint;
    }

    /**
     * �Z�[�u�|�C���g���g�����ǂ�����Ԃ��B
     * 
     * @return �Z�[�u�|�C���g���g���ꍇ�ɂ́A<code>true</code>
     */
    public boolean useSavepoint() {
        return useSavepoint;
    }
    
}
