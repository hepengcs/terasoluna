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
import jp.terasoluna.fw.batch.core.SupportProcessor;
import jp.terasoluna.fw.batch.openapi.JobContext;

/**
 * �g�����U�N�V�����������s�� <code>SupportProcessor</code> �C���^�t�F�[�X�̎���
 * �N���X�B
 * 
 * <p><code>supportProcessor</code> �����ɐݒ肳�ꂽ�T�|�[�g�v���Z�b�T�̏�����
 * �g�����U�N�V�����ōs���B<code>supportProcessor</code> �����̃T�|�[�g�v���Z�b
 * �T�N���O�Ƀg�����U�N�V�������J�n���A�T�|�[�g�v���Z�b�T�N����̃W���u�X�e�[�^
 * �X�ɂ���ăg�����U�N�V�����̃R�~�b�g�^���[���o�b�N���s���B</p>
 * 
 * <p><code>supportProcessor</code> �����̃T�|�[�g�v���Z�b�T�N����̃W���u�X�e�[
 * �^�X���A<code>JobStatus.STATE.STARTED</code>�A<code>JobStatus.STATE.RESTARTED
 * </code>�A<code>JobStatus.STATE.ENDING_NORMALLY</code>
 * �ł���ꍇ�ɂ̓R�~�b�g���A���̂ق��̏ꍇ�ɂ̓��[���o�b�N����B</p>
 * 
 * <p>�g�����U�N�V����������s�����߁A�W���u�X�e�[�^�X�Ƃ��� 
 * <code>TransactionalJobStatus</code>���g�p����K�v������B</p>
 * 
 */
public class TransactionalSupportProcessor implements SupportProcessor {

    /**
     * �T�|�[�g�v���Z�b�T�B
     */
    private SupportProcessor supportProcessor = null;
    
    /**
     * �T�|�[�g�������s���B
     *
     * @param jobContext �W���u�R���e�L�X�g
     * @param jobStatus �g�����U�N�V���i���W���u�X�e�[�^�X
     */
    public void process(JobContext jobContext, JobStatus jobStatus) {
        if (supportProcessor.canSkip()) {
            return;
        }

        // �T�|�[�g�����p�g�����U�N�V�����J�n
        TransactionalJobStatus transactionalJobStatus = 
            (TransactionalJobStatus) jobStatus;
        transactionalJobStatus.beginTransaction();

        // �Z�[�u�|�C���g��L�����ɍŏ��̃Z�[�u�|�C���g���쐬
        if (transactionalJobStatus.useSavepoint()) {
            transactionalJobStatus.releaseAndCreateSavepoint();
        }

        supportProcessor.process(jobContext, jobStatus);
        
        if (jobStatus.isExecuting() 
                || jobStatus.getJobState() == JobStatus.STATE.ENDING_NORMALLY
                || jobStatus.isShutdownGraceful()) {
            transactionalJobStatus.commit();
        } else {
            transactionalJobStatus.rollback();
            // �Z�[�u�|�C���g�L�����͍Ō�ɃR�~�b�g����
            if (transactionalJobStatus.useSavepoint()) {
                transactionalJobStatus.commit();
            }
        }
    }

    /**
     * �T�|�[�g�v���Z�b�T��ݒ肷��B
     * 
     * @param supportProcessor �T�|�[�g�v���Z�b�T
     */
    public void setSupportProcessor(SupportProcessor supportProcessor) {
        this.supportProcessor = supportProcessor;
    }

    /**
     * �W���u�O�������X�L�b�v�ł���ꍇ�ɂ́A<code>true</code> ��Ԃ��B 
     * 
     * @return �W���u�O�������X�L�b�v�ł���ꍇ�ɂ́A<code>true</code>
     */
    public boolean canSkip() {
        return supportProcessor.canSkip();
    }
}
