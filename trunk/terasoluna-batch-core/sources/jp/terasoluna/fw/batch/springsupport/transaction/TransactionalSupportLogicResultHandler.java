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

import jp.terasoluna.fw.batch.core.JobStatus;
import jp.terasoluna.fw.batch.openapi.BLogicResult;
import jp.terasoluna.fw.batch.standard.StandardSupportLogicResultHandler;

/**
 * �g�����U�N�V�����������s�� <code>SupportLogicResultHandler</code> �C���^�t�F
 * �[�X�̎����N���X�B
 * 
 * <p><code>BLogicResult</code> �̃��^�[���R�[�h�ɉ����āA�g�����U�N�V��������
 * �i�R�~�b�g�A���[���o�b�N�j���s���B�g�����U�N�V������Ԃ́A
 * <code>{@link TransactionalJobStatus}<code> �ɕێ�����Ă���B</p>
 * 
 * <p><strong>�Z�[�u�|�C���g�𗘗p���Ă���ꍇ</strong></p>
 * 
 * <p><code>BLogicResult</code> �̃��^�[���R�[�h�Ƃ��� <code>NORMAL_CONTINUE
 * </code> ���Ԃ���閈�ɁA�Z�[�u�|�C���g���J���A�쐬����B</p>
 * 
 * <p><code>BLogicResult</code> �̃��^�[���R�[�h�Ƃ��� <code>ERROR_CONTINUE
 * </code>�A�܂��� <code>ERROR_END</code> ���Ԃ��ꂽ�ꍇ�ɂ́A�Z�[�u�|�C���g�܂�
 * �g�����U�N�V���������[���o�b�N����B</p>
 * 
 * <p><strong>�Z�[�u�|�C���g�𗘗p���Ă��Ȃ��ꍇ</strong></p>
 * 
 * <p><code>BLogicResult</code> �̃��^�[���R�[�h�Ƃ��� <code>NORMAL_CONTINUE
 * </code>�A���邢�� <code>ERROR_CONTINUE</code> ���Ԃ��ꂽ�ꍇ�ɂ́A�g�����U�N
 * �V�����Ɋւ�鏈���͎��s���Ȃ��B</p>
 * 
 * <p><code>BLogicResult</code> �̃��^�[���R�[�h�Ƃ��� <code>ERROR_END</code> ��
 * �Ԃ��ꂽ�ꍇ�ɂ́A���s���̃g�����U�N�V���������[���o�b�N����B</p>
 * 
 */
public class TransactionalSupportLogicResultHandler 
    extends StandardSupportLogicResultHandler {

    /**
     * <code>BLogicResult</code> �̃��^�[���R�[�h�� <code>NORMAL_CONTINUE</code>
     *  �ł���Ƃ��̏������s���B
     * 
     * <p>�Z�[�u�|�C���g�𗘗p���Ă���ꍇ�ɂ̓Z�[�u�|�C���g���쐬�������A�e�N��
     * �X�̏������Ăяo���B</p>
     *
     * @param jobStatus �W���u�X�e�[�^�X
     * @param blogicResult �r�W�l�X���W�b�N��������
     * @param name �T�|�[�g������
     */
    @Override
    protected void processNormalContinue(JobStatus jobStatus, 
            BLogicResult blogicResult, String name) {
        TransactionalJobStatus transactionalJobStatus = 
            (TransactionalJobStatus) jobStatus;

        // �Z�[�u�|�C���g�̊J���A�쐬
        if (transactionalJobStatus.useSavepoint()) {
            transactionalJobStatus.releaseAndCreateSavepoint();
        }
        
        super.processNormalContinue(jobStatus, blogicResult, name);
    }

    /**
     * <code>BLogicResult</code> �̃��^�[���R�[�h�� <code>ERROR_CONTINUE</code>
     *  �ł���Ƃ��̏������s���B
     *
     * <p>�Z�[�u�|�C���g�𗘗p���Ă���ꍇ�ɂ̓Z�[�u�|�C���g�܂Ńg�����U�N�V����
     * �����[���o�b�N���A�e�N���X�̏������Ăяo���B</p>
     *
     * @param jobStatus �W���u�X�e�[�^�X
     * @param bLogicResult �r�W�l�X���W�b�N��������
     * @param name �T�|�[�g������
     */
    @Override
    protected void processErrorContinue(JobStatus jobStatus, 
            BLogicResult bLogicResult, String name) {
        TransactionalJobStatus transactionalJobStatus = 
            (TransactionalJobStatus) jobStatus;
        // ���[���o�b�N
        if (transactionalJobStatus.useSavepoint()) {
            transactionalJobStatus.rollbackToSavepoint();
        }
        
        super.processErrorContinue(jobStatus, bLogicResult, name);
    }

    /**
     * <code>BLogicResult</code> �̃��^�[���R�[�h�� <code>ERROR_END</code> ��
     * ����Ƃ��̏������s���B
     *
     * <p>�Z�[�u�|�C���g�𗘗p���Ă���ꍇ�ɂ̓Z�[�u�|�C���g�܂Ńg�����U�N�V����
     * �����[���o�b�N���A�e�N���X�̏������Ăяo���B</p>
     * 
     * <p>�Z�[�u�|�C���g�𗘗p���Ă��Ȃ��ꍇ�ɂ̓g�����U�N�V���������[���o�b�N���A
     * �e�N���X�̏������Ăяo���B</p>
     *
     * @param jobStatus �W���u�X�e�[�^�X
     * @param blogicResult �r�W�l�X���W�b�N��������
     * @param name �T�|�[�g������
     */
    @Override
    protected void processErrorEnd(JobStatus jobStatus, 
            BLogicResult blogicResult, String name) {
        TransactionalJobStatus transactionalJobStatus = 
            (TransactionalJobStatus) jobStatus;
        // ���[���o�b�N
        if (transactionalJobStatus.useSavepoint()) {
            transactionalJobStatus.rollbackToSavepoint();
        } else {
            transactionalJobStatus.rollback();
        }
    
        super.processErrorEnd(jobStatus, blogicResult, name);
    }
}
