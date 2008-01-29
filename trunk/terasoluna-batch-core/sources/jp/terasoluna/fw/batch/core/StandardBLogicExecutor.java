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

package jp.terasoluna.fw.batch.core;

import java.util.LinkedHashMap;
import java.util.List;

import jp.terasoluna.fw.batch.openapi.BLogic;
import jp.terasoluna.fw.batch.openapi.BLogicResult;
import jp.terasoluna.fw.batch.openapi.JobContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * �r�W�l�X���W�b�N���s�N���X�̕W�������B
 * 
 * <p>���̃N���X�ł́A�A�v���P�[�V�����̃r�W�l�X���W�b�N�i<code>BLogic<code> 
 * �C���^�t�F�[�X�����������N���X�j�� �N�����A�N�����ʂ��W���u�X�e�[�^�X�֔��f��
 * ��B</p>
 * 
 * <p>�r�W�l�X���W�b�N�̏������ʂ́A���̃N���X�̑����ɐݒ肳��Ă���r�W�l�X���W
 * �b�N�������ʃn���h���ɂ���ăW���u�X�e�[�^�X�ɔ��f�����B�r�W�l�X���W�b�N��
 * �����ʃn���h���́A�r�W�l�X���W�b�N���ԋp�������^�[���R�[�h�Ɋւ�炸�N������
 * ��B�܂�A�r�W�l�X���W�b�N�����푱�s(<code>NORMAL_CONTINUE</code>)��ԋp��
 * ���ꍇ�ł��A�ُ�I��(<code>ERROR_END</code>)��ԋp�����ꍇ�ł����Ă���������
 * �n���h�����Ă΂��B</p>
 * 
 * <p>�r�W�l�X���W�b�N�ŗ�O�����������ꍇ�ɂ́A���̃N���X�̑����ɐݒ肳��Ă���
 * ��O�n���h���̐ݒ�ɂ��������ė�O�n���h�����N�������B
 * �܂��A�r�W�l�X���W�b�N����X���[���ꂽ��O�́A��O�n���h���ɓn�����O�Ƀt��
 * �[�����[�N�̃r�W�l�X���W�b�N��O�N���X(<code>BLogicException<code>)�ɂ���āA
 * ���b�v�����B</p>
 * 
 * <p>��O�����������ꍇ�ɂ́A�r�W�l�X���W�b�N�������ʃn���h���͌Ă΂�Ȃ����Ƃ�
 * ���ӂ��邱�ƁB
 * </p>
 * 
 * <p>���̃N���X�ł̓g�����U�N�V�����Ɋւ�鏈���͍s���Ȃ��B</p>
 * 
 * @see jp.terasoluna.fw.batch.openapi.BLogic
 * @see jp.terasoluna.fw.batch.core.BLogicResultHandler
 * @see jp.terasoluna.fw.batch.core.JobExceptionHandler
 * 
 */
public class StandardBLogicExecutor implements BLogicExecutor {

    /**
     * ���O�C���X�^���X�B
     */
    private static Log log = LogFactory.getLog(StandardBLogicExecutor.class);

    /**
     * �r�W�l�X���W�b�N���ʏ����n���h���B
     */
    private BLogicResultHandler bLogicResultHandler = null;

    /**
     * �r�W�l�X���W�b�N�B
     */
    private BLogic<Object, JobContext> blogic = null;

    /**
     * ��O�n���h�����i�[����Map�B
     */
    private LinkedHashMap<JobException, JobExceptionHandler>
            exceptionHandlerMap = null;

    /**
     * �f�t�H���g��O�n���h���B
     */
    private JobExceptionHandler defaultJobExceptionHandler = null;

    /**
     * �r�W�l�X���W�b�N�����s���A�r�W�l�X���W�b�N���s���ʂ̏������s���B
     *
     * @param blogicInputData �����Ώۃf�[�^
     * @param jobContext �W���u�R���e�L�X�g
     * @param jobStatus �W���u������
     * @param batchUpdateMapList �o�b�`�X�V���X�g
     */
    public void executeBLogic(Object blogicInputData, JobContext jobContext,
            JobStatus jobStatus,
            List<LinkedHashMap<String, Object>> batchUpdateMapList) {
        BLogicResult blogicResult = null;
        try {

            blogicResult = blogic.execute(blogicInputData, jobContext);

            bLogicResultHandler.handle(blogicResult, blogicInputData, jobStatus,
                    batchUpdateMapList);

            writeLog(jobStatus, blogicResult);
        } catch (RuntimeException e) {
            // BLogic�ABLogic�������ʃn���h���Ŕ���������O�̏���
            BLogicException wrappingException
                = new BLogicException(e, blogicInputData, blogicResult);

            JobExceptionHandler handler
                = ExceptionHandlerUtil.getJobExceptionHandler(wrappingException,
                                                            exceptionHandlerMap,
                                                    defaultJobExceptionHandler);

            handler.handlException(jobContext, wrappingException, jobStatus);
        }
    }

    /**
     * �W���u���ʃn���h����ݒ肷��B
     *
     * @param bLogicResultHandler �W���u���ʃn���h��
     */
    public void setBlogicResultHandler(
            BLogicResultHandler bLogicResultHandler) {
        this.bLogicResultHandler = bLogicResultHandler;
    }

    /**
     * �r�W�l�X���W�b�N��ݒ肷��B
     *
     * @param blogic �r�W�l�X���W�b�N
     */
    public void setBlogic(BLogic<Object, JobContext> blogic) {
        this.blogic = blogic;
    }

    /**
     * ��O�n���h�����i�[����Map��ݒ肷��B
     *
     * @param exceptionHandlerMap ��O�n���h�����i�[����Map
     */
    public void setExceptionHandlerMap(
         LinkedHashMap<JobException, JobExceptionHandler> exceptionHandlerMap) {
        this.exceptionHandlerMap = exceptionHandlerMap;
    }

    /**
     * �f�t�H���g��O�n���h����ݒ肷��B
     * @param defaultJobExceptionHandler �f�t�H���g��O�n���h��
     */
    public void setDefaultJobExceptionHandler(
        JobExceptionHandler defaultJobExceptionHandler) {
        this.defaultJobExceptionHandler = defaultJobExceptionHandler;
    }

    /**
     * BLogicExecutor�̌��ʃ��O���o�͂���B
     * 
     * @param jobStatus �W���u�X�e�[�^�X
     * @param blogicResult �r�W�l�X���W�b�N���s����
     */
    private void writeLog(JobStatus jobStatus, BLogicResult blogicResult) {
        if (log.isDebugEnabled()) {
            StringBuilder logStr = new StringBuilder();
            logStr.append("�y END �z");
            logStr.append(" [jobId=");
            logStr.append(jobStatus.getJobId());
            logStr.append("]");
            logStr.append(" [jobRequestNo=");
            logStr.append(jobStatus.getJobRequestNo());
            logStr.append("]");
            logStr.append(" [partitionNo=");
            logStr.append(jobStatus.getPartitionNo());
            logStr.append("]");
            logStr.append(" [BLogicName=");
            logStr.append(blogic.getClass().getSimpleName());
            logStr.append("]");
            logStr.append(" [ResultHandler=");
            logStr.append(bLogicResultHandler.getClass().getSimpleName());
            logStr.append("]");
            logStr.append(" [ReturnCode�F");
            logStr.append(blogicResult.getReturnCode());
            logStr.append(" JobExitCode=");
            logStr.append(blogicResult.getClass().getSimpleName());
            logStr.append("]");
            logStr.append(" [JobState=");
            logStr.append(jobStatus.getJobState());
            logStr.append("]");
            log.debug(logStr.toString());
        }
    }
}
