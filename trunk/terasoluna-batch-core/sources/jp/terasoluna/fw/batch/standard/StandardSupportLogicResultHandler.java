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

package jp.terasoluna.fw.batch.standard;

import jp.terasoluna.fw.batch.core.JobStatus;
import jp.terasoluna.fw.batch.core.SupportLogicResultHandler;
import jp.terasoluna.fw.batch.openapi.BLogicResult;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <code>SupportLogicResultHandler</code> �C���^�t�F�[�X�̕W�������N���X�B
 * 
 * <p>���̃N���X�ł́A�T�|�[�g���W�b�N�������ʂ� <code>NORMAL_END</code>�A
 * <code>ERROR_END</code>�ł���Ƃ��ɁA�W���u�X�e�[�^�X���X�V����B</p>
 * 
 */
public class StandardSupportLogicResultHandler 
    implements SupportLogicResultHandler {

    /**
     * ���O�C���X�^���X�B
     */
    private static Log log = 
        LogFactory.getLog(StandardSupportLogicResultHandler.class);

    /**
     * �T�|�[�g�����̎��s���ʂ���������B
     * 
     * @param blogicResult �T�|�[�g�����̎��s����
     * @param jobStatus �W���u�X�e�[�^�X
     * @param name �T�|�[�g�����N���X�ɐݒ肳�ꂽ���O
     */
    public void handle(BLogicResult blogicResult, JobStatus jobStatus,
            String name) {
        switch (blogicResult.getReturnCode()) {
            case NORMAL_CONTINUE:
                processNormalContinue(jobStatus, blogicResult, name);
                break;
            case NORMAL_END:
                processNormalEnd(jobStatus, blogicResult, name);
                break;
            case ERROR_CONTINUE:
                processErrorContinue(jobStatus, blogicResult, name);
                break;
            case ERROR_END:
                processErrorEnd(jobStatus, blogicResult, name);
                break;
            default:
                throw new IllegalArgumentException(blogicResult.getReturnCode()
                        + " illegal ReturnCode");
            
        }
    }

    /**
     * <code>BLogicResult</code> �̃��^�[���R�[�h�� <code>NORMAL_CONTINUE</code>
     *  �ł���Ƃ��̏������s���B
     * 
     * @param jobStatus �W���u�X�e�[�^�X
     * @param bLogicResult �r�W�l�X���W�b�N��������
     * @param name �T�|�[�g�����N���X�ɐݒ肳�ꂽ���O
     */
    protected void processNormalContinue(JobStatus jobStatus,
            BLogicResult bLogicResult, String name) {
    }

    /**
     * <code>BLogicResult</code> �̃��^�[���R�[�h�� <code>NORMAL_END</code> ��
     * ����Ƃ��̏������s���B
     * 
     * <p>JobStatus</p> �̃W���u��� <code>JobStatus.STATE.ENDING_NORMALLY
     * </code> �ɍX�V���A<code>BLogicResult<code>
     * �̃W���u�I���R�[�h�� <code>JobStatus</code> �ɔ��f����B</p>
     *
     * @param jobStatus �W���u�X�e�[�^�X
     * @param bLogicResult �r�W�l�X���W�b�N��������
     * @param name �T�|�[�g�����N���X�ɐݒ肳�ꂽ���O
     */
    protected void processNormalEnd(JobStatus jobStatus,
            BLogicResult bLogicResult, String name) {
        jobStatus.setJobState(JobStatus.STATE.ENDING_NORMALLY);
        jobStatus.setJobExitCode(bLogicResult.getJobExitCode());
    }

    /**
     * <code>BLogicResult</code> �̃��^�[���R�[�h�� <code>ERROR_CONTINUE</code>
     *  �ł���Ƃ��̏������s���B
     *
     * <p>�G���[���O���o�͂���B</p>
     * 
     * @param jobStatus �W���u�X�e�[�^�X
     * @param bLogicResult �T�|�[�g�����̎��s����
     * @param name �T�|�[�g�����N���X�ɐݒ肳�ꂽ���O
     */
    protected void processErrorContinue(JobStatus jobStatus, 
            BLogicResult bLogicResult, String name) {
        writeErrorLog(jobStatus, bLogicResult, name);
    }

    /**
     * <code>BLogicResult</code> �̃��^�[���R�[�h�� <code>ERROR_END</code> ��
     * ����Ƃ��̏������s���B
     *
     * <p>�G���[���O���o�͂���B</p>
     * 
     * <p>JobStatus</p> �̃W���u��� <code>JobStatus.STATE.ENDING_ABNORMALLY
     * </code> �ɍX�V���A<code>BLogicResult<code>
     * �̃W���u�I���R�[�h�� <code>JobStatus</code> �ɔ��f����B</p>
     *
     * @param jobStatus �W���u�X�e�[�^�X
     * @param bLogicResult �r�W�l�X���W�b�N��������
     * @param name �T�|�[�g�����N���X�ɐݒ肳�ꂽ���O
     */
    protected void processErrorEnd(JobStatus jobStatus, 
            BLogicResult bLogicResult, String name) {
        writeErrorLog(jobStatus, bLogicResult, name);
        jobStatus.setJobState(JobStatus.STATE.ENDING_ABNORMALLY);
        jobStatus.setJobExitCode(bLogicResult.getJobExitCode());
    }

    /**
     * �T�|�[�g���W�b�N�̏������ʂ̃G���[���O���o�͂���B
     * 
     * @param jobStatus �W���u�X�e�[�^�X
     * @param result �T�|�[�g���W�b�N�̏�������
     * @param name �T�|�[�g�����N���X�ɐݒ肳�ꂽ���O
     */
    protected void writeErrorLog(JobStatus jobStatus, BLogicResult result,
            String name) {
        log.error("SupportLogic Name: " + name + " Error JobID: "
                + jobStatus.getJobId() + " SupportLogic result: " + result);
    }
}
