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

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import jp.terasoluna.fw.batch.openapi.JobContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <code>Chunk</code> �������P�ʂƂ��āA<code>Chunk</code> �����f�[�^�����
 * �Ƃ���r�W�l�X���W�b�N�����s����B
 *
 * <p>�W���u���\������ <code>Workable</code> �C���^�t�F�[�X�̎����̊K�w�\��
 * �ɂ����āA�ł��ŉ��w�i�ł������ȍ�ƒP�ʁj�Ɉʒu����B<code>JobWorker</code>
 * �́A<code>Chunk</code> �Ɋ܂܂�邷�ׂẴf�[�^�ɑ΂��āA
 * �J��Ԃ��r�W�l�X���W�b�N���N������B</p>
 *
 * <p>�r�W�l�X���W�b�N�̋N���̍ۂɂ́A<code>Chunk</code> �Ɋ܂܂��
 * <code>JobContext</code> ���n�����B
 * �r�W�l�X���W�b�N�̎��s�́A<code>blogicExecutor</code> �����ɐݒ肳��Ă���
 * �r�W�l�X���W�b�N���s�N���X�̃C���X�^���X�ɈϏ������B</p>
 *
 * <p><code>JobWorker</code> �ł́A<code>Chunk</code> �Ɋ܂܂�邷�ׂẴf�[�^��
 * �n���āA�o�b�`�X�V���X�g���ێ������B�o�b�`�X�V���X�g�́A
 * �r�W�l�X���W�b�N����o�b�`�X�V�̈˗������閈�ɐ����ǉ������B
 * �o�b�`�X�V���X�g�́A<code>Chunk</code>�Ɋ܂܂�邷�ׂẴf�[�^�ɑ΂���
 * �r�W�l�X���W�b�N�̎��s���I��������A���������B
 * �������A�o�b�`�X�V���X�g�̏����́A<code>batchUpdateResultHandler</code>
 * �����ɐݒ肳��Ă���o�b�`�X�V�v���Z�b�T�ɈϏ������B</p>
 *
 * <p>�o�b�`�X�V�v���Z�b�T���N����ɁA�o�b�`�X�V�̏������ʂ��W���u�X�e�[�^�X�ւ�
 * ���f��A���O�o�͂Ȃǂ��s����悤�� <code>batchUpdateResultHandler</code>
 * �����ɐݒ肳�ꂽ�o�b�`�X�V�������ʃn���h�����N�������B</p>
 *
 * <p>�o�b�`�X�V�v���Z�b�T�A����уo�b�`�X�V�������ʃn���h���ŗ�O����������
 * �ꍇ�ɂ́A��O�n���h���ɂ���ď��������B��O�n���h���́A
 * <code>exceptionHandlerMap</code>�����ɐݒ肳��Ă����O�n���h����`����
 * �擾����B�o�b�`�X�V�v���Z�b�T�A����уo�b�`�X�V�������ʂŗ�O�����������ꍇ
 * �̃n���h����O�n���h���ɂ́A<code>BatchUpdateException</code> ��O���L�[��
 * ���Ē�`����Ă�����̂��g����B</p>
 *
 * <p>��O�n���h����`����A<code>BatchUpdateException</code> ��O���L�[���Ƃ���
 * ��O�n���h�����擾�ł��Ȃ������ꍇ�ɂ́A
 * <code>defaultJobExceptionHandler</code>�����ɐݒ肳��Ă���
 * �f�t�H���g��O�n���h�����A��O�����Ɏg����B</p>
 *
 */
public class JobWorker implements Workable<Chunk> {

    /**
     * ���O�C���X�^���X�B
     */
    private static Log log = LogFactory.getLog(JobWorker.class);

    /**
     * ��O�n���h�����i�[����Map�B
     */
    protected LinkedHashMap<JobException, JobExceptionHandler> 
               exceptionHandlerMap = null;

    /**
     * �f�t�H���g��O�n���h���B
     */
    protected JobExceptionHandler defaultJobExceptionHandler = null;

    /**
     * �o�b�`�X�V�v���Z�b�T�B
     */
    protected BatchUpdateProcessor batchUpdateProcessor = null;
    
    /**
     * �o�b�`�X�V�̏������ʃn���h���B
     */
    protected BatchUpdateResultHandler batchUpdateResultHandler = null;

    /**
     * �r�W�l�X���W�b�N���s�N���X�̃C���X�^���X�B
     */
    private BLogicExecutor blogicExecutor = null;
    
    /**
     * <code>Chunk</code> ����������B
     *
     * @param chunk �����Ώۃf�[�^���i�[�����`�����N
     * @param jobStatus �W���u�X�e�[�^�X
     */
    public void work(Chunk chunk, JobStatus jobStatus) {
        writeStartLog(jobStatus);

        if (chunk.size() == 0) {
            throw new IllegalArgumentException("chunk size is 0");
        }
        
        List<LinkedHashMap<String, Object>> batchUpdateMapList
            = new ArrayList<LinkedHashMap<String, Object>>();
        
        for (Object blogicInputData : chunk) {

            //�����I��
            if (jobStatus.isShutdownImmediate()) {
                jobStatus.suspend();
                return;
            }

            // BLogic�ABLogic�������ʃn���h���̌Ăяo��
            blogicExecutor.executeBLogic(blogicInputData, chunk.getJobContext(),
                    jobStatus, batchUpdateMapList);
            
            if (!jobStatus.isContinue()) {
                return;
            }
        }

        // �o�b�`�X�V���s
        processBatchUpdate(chunk.getJobContext(), jobStatus,
                batchUpdateMapList);

        writeEndLog(jobStatus);
    }

    /**
     * �o�b�`�X�V���s���B
     *
     * @param jobContext �W���u�R���e�L�X�g
     * @param jobStatus �W���u������
     * @param batchUpdateMapList �o�b�`�X�V���X�g
     */
    protected void processBatchUpdate(JobContext jobContext,
            JobStatus jobStatus,
            List<LinkedHashMap<String, Object>> batchUpdateMapList) {
        if (batchUpdateMapList.size() == 0) {
            return;
        }
        try {
            batchUpdateProcessor.processBatchUpdate(batchUpdateMapList);
            batchUpdateResultHandler.handle(jobStatus, batchUpdateMapList);
        } catch (RuntimeException e) {
            // BLogic�ABLogic�������ʃn���h���Ŕ���������O�̏���
            BatchUpdateException wrappingException
                = new BatchUpdateException(e, batchUpdateMapList);

            JobExceptionHandler handler
                = ExceptionHandlerUtil.getJobExceptionHandler(wrappingException,
                                                            exceptionHandlerMap,
                                                    defaultJobExceptionHandler);

            handler.handlException(jobContext, wrappingException, jobStatus);
        }
    }

    /**
     * ��O�n���h����`��ݒ肷��B
     *
     * @param exceptionHandlerMap ��O�n���h����`
     */
    public void setExceptionHandlerMap(
         LinkedHashMap<JobException, JobExceptionHandler> exceptionHandlerMap) {
        this.exceptionHandlerMap = exceptionHandlerMap;
    }

    /**
     * �o�b�`�X�V�p�N���X�̃C���X�^���X��ݒ肷��B
     *
     * @param batchUpdateProcessor �o�b�`�X�V�p�N���X�̃C���X�^���X
     */
    public void setBatchUpdateProcessor(
            BatchUpdateProcessor batchUpdateProcessor) {
        this.batchUpdateProcessor = batchUpdateProcessor;
    }

    /**
     * �r�W�l�X���W�b�N���s�p�N���X�̃C���X�^���X��ݒ肷��B
     *
     * @param blogicExecutor �r�W�l�X���W�b�N���s�p�N���X�̃C���X�^���X
     */
    public void setBlogicExecutor(BLogicExecutor blogicExecutor) {
        this.blogicExecutor = blogicExecutor;
    }

    /**
     * �o�b�`�X�V�̏������ʃn���h����ݒ肷��B
     * 
     * @param batchUpdateResultHandler �o�b�`�X�V�̏������ʃn���h��
     */
    public void setBatchUpdateResultHandler(
            BatchUpdateResultHandler batchUpdateResultHandler) {
        this.batchUpdateResultHandler = batchUpdateResultHandler;
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
     * JobWorker�̊J�n���O���o�͂���B
     * 
     * @param jobStatus �W���u�X�e�[�^�X
     */
    private void writeStartLog(JobStatus jobStatus) {
        if (log.isDebugEnabled()) {
            StringBuilder logStr = new StringBuilder();
            logStr.append("�ySTART�z");
            logStr.append(" [jobId=");
            logStr.append(jobStatus.getJobId());
            logStr.append("]");
            logStr.append(" [jobRequestNo=");
            logStr.append(jobStatus.getJobRequestNo());
            logStr.append("]");
            logStr.append(" [partitionNo=");
            logStr.append(jobStatus.getPartitionNo());
            logStr.append("]");
            logStr.append(" [BLogicExecutorName=");
            logStr.append(blogicExecutor.getClass().getSimpleName());
            logStr.append("]");
            logStr.append(" [BatchUpdateProcessorName=");
            logStr.append(batchUpdateProcessor.getClass().getSimpleName());
            logStr.append("]");
            logStr.append(" [BatchUpdateResultHandlerName=");
            logStr.append(batchUpdateResultHandler.getClass().getSimpleName());
            logStr.append("]");
            log.debug(logStr.toString());
        }
    }

    /**
     * JobWorker�̏I�����O���o�͂���B
     * 
     * @param jobStatus �W���u�X�e�[�^�X
     */
    private void writeEndLog(JobStatus jobStatus) {
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
            logStr.append(" [BLogicExecutorName=");
            logStr.append(blogicExecutor.getClass().getSimpleName());
            logStr.append("]");
            logStr.append(" [BatchUpdateProcessorName=");
            logStr.append(batchUpdateProcessor.getClass().getSimpleName());
            logStr.append("]");
            logStr.append(" [BatchUpdateResultHandlerName=");
            logStr.append(batchUpdateResultHandler.getClass().getSimpleName());
            logStr.append("]");
            logStr.append(" [BatchUpdateCount=");
            logStr.append(jobStatus.getBatchUpdateCount());
            logStr.append("]");
            log.debug(logStr.toString());
        }
    }
}
