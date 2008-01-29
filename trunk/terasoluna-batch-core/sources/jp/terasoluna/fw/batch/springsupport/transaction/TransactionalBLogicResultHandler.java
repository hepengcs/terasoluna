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

import java.util.LinkedHashMap;
import java.util.List;

import jp.terasoluna.fw.batch.core.JobStatus;
import jp.terasoluna.fw.batch.openapi.BLogicResult;
import jp.terasoluna.fw.batch.standard.StandardBLogicResultHandler;

/**
 * �g�����U�N�V�����������s�� <code>BLogicResultHandler</code> �C���^�t�F�[�X��
 * �����N���X�B
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
 * </code>�A�܂���<code>ERROR_END</code> ���Ԃ��ꂽ�ꍇ�ɂ́A�Z�[�u�|�C���g�܂�
 * �g�����U�N�V���������[���o�b�N����B</p>
 * 
 * <p><strong>�Z�[�u�|�C���g�𗘗p���Ă��Ȃ��ꍇ</strong></p>
 * 
 * <p><code>BLogicResult</code> �̃��^�[���R�[�h�Ƃ��� <code>NORMAL_CONTINUE
 * </code>�A���邢��<code>ERROR_CONTINUE</code> ���Ԃ��ꂽ�ꍇ�ɂ́A�g�����U�N�V
 * �����Ɋւ�鏈���͎��s���Ȃ��B</p>
 * 
 * <p><code>BLogicResult</code> �̃��^�[���R�[�h�Ƃ��� <code>ERROR_END</code> ��
 * �Ԃ��ꂽ�ꍇ�ɂ́A���s���̃g�����U�N�V���������[���o�b�N����B</p>
 * 
 */
public class TransactionalBLogicResultHandler 
    extends StandardBLogicResultHandler {

    /**
     * <code>BLogicResult</code> �̃��^�[���R�[�h�� <code>NORMAL_CONTINUE</code>
     *  �ł���Ƃ��̏������s���B
     * 
     * <p>�Z�[�u�|�C���g�𗘗p���Ă���ꍇ�ɂ̓Z�[�u�|�C���g���쐬�������A�e�N��
     * �X�̏������Ăяo���B</p>
     *
     * @param jobStatus �W���u�X�e�[�^�X
     * @param blogicResult �r�W�l�X���W�b�N��������
     * @param batchUpdateMapList �o�b�`�X�V���X�g
     */
    @Override
    protected void processNormalContinue(JobStatus jobStatus, 
            BLogicResult blogicResult, List<LinkedHashMap<String, 
            Object>> batchUpdateMapList) {
        TransactionalJobStatus transactionalJobStatus = 
            (TransactionalJobStatus) jobStatus;

        // �Z�[�u�|�C���g�̊J���A�쐬
        if (transactionalJobStatus.useSavepoint()) {
            transactionalJobStatus.releaseAndCreateSavepoint();
        }
        
        super.processNormalContinue(jobStatus, blogicResult,
                batchUpdateMapList);
    }

    /**
     * <code>BLogicResult</code> �̃��^�[���R�[�h�� <code>ERROR_CONTINUE</code> 
     * �ł���Ƃ��̏������s���B
     *
     * <p>�Z�[�u�|�C���g�𗘗p���Ă���ꍇ�ɂ̓Z�[�u�|�C���g�܂Ńg�����U�N�V����
     * �����[���o�b�N���A�e�N���X�̏������Ăяo���B</p>
     *
     * @param blogicInputData �r�W�l�X���W�b�N�̓��̓f�[�^
     * @param jobStatus �W���u�X�e�[�^�X
     * @param blogicResult �r�W�l�X���W�b�N��������
     */
    @Override
    protected void processErrorContinue(Object blogicInputData,
            JobStatus jobStatus, BLogicResult blogicResult) {
        TransactionalJobStatus transactionalJobStatus = 
            (TransactionalJobStatus) jobStatus;
        // ���[���o�b�N
        if (transactionalJobStatus.useSavepoint()) {
            transactionalJobStatus.rollbackToSavepoint();
        }
        
        super.processErrorContinue(blogicInputData, jobStatus, blogicResult);
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
     * @param blogicInputData �r�W�l�X���W�b�N�̓��̓f�[�^
     * @param jobStatus �W���u�X�e�[�^�X
     * @param blogicResult �r�W�l�X���W�b�N��������
     */
    @Override
    protected void processErrorEnd(Object blogicInputData,
            JobStatus jobStatus, BLogicResult blogicResult) {
        TransactionalJobStatus transactionalJobStatus = 
            (TransactionalJobStatus) jobStatus;
        // ���[���o�b�N
        if (transactionalJobStatus.useSavepoint()) {
            transactionalJobStatus.rollbackToSavepoint();
        } else {
            transactionalJobStatus.rollback();
        }
    
        super.processErrorEnd(blogicInputData, jobStatus, blogicResult);
    }
}
