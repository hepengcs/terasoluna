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

import jp.terasoluna.fw.batch.openapi.JobContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * �W���u���̃t���[�����[�N�ŋK�肷���ƒP�ʂ��Ǘ��A���s����N���X�B
 * 
 * <p><code>{@link JobWorker}</code> �Ȃǂ̑��� <code>{@link Workable}</code>
 * �C���^�t�F�[�X�̎����N���X�Ƌ��ɁA��Ƃ̊K�w�\�����\������B<code>JobManager
 * </code>�́A��Ƃ̊K�w�\���̒��œ��͏����Ƃ��̓��͂ɑΉ����鏈�����Ǘ�����B
 * <code>JobManager</code> �́A��ƊK�w�̒��ŃW���u�ɑΉ������Ƃ����s���邾��
 * �łȂ��A������"���͏����Ƃ��̓��͂ɑΉ����鏈��"�ō\������邷�ׂĂ̍�Ƃ�
 * ���s����B</p>
 * 
 * <p>��Ƃ̊K�w�̒��ŁA�����Ƃ��ŉ��w�i�����Ƃ������ȍ�ƒP�ʁj�̍�Ƃ� 
 * <code>JobWorker</code>�ɂ���čs���邪�A<code>JobWorker</code> ����͏�����
 * ���т��ĊǗ������Ƃ�<code>JobManager</code> ���s���B����ɂ́A�����W���u
 * �ȂǁA<code>JobManager</code>�����̓��͂����т���K�v������ꍇ�ɂ́A
 * ��ʊK�w�� <code>JobManager</code> �ɂ���āA���͏����Ƃ��̓��͂���������
 *  <code>JobManager</code> �����ѕt������B</p>
 * 
 * <p>��ƊK�w�́A<code>Workable</code> �C���^�t�F�[�X�̎����N���X���K�w�I��
 * �ςݏグ��ꂽ�R���|�W�b�g�ȍ\���ɂȂ��Ă���B<code>JobManager</code> �́A
 * �ŉ��w�ȊO�̕������\������B</p>
 * 
 * <p><strong>�L���[�̍쐬�A�R���N�^�Ăяo��</strong></p>
 *
 * <p><code>JobManager</code> �ł́A<code>workQueueFactory</code> ������
 * �ݒ肳��Ă����ƃL���[�t�@�N�g������A���͏����ƁA���̓��͂����������Ƃ�
 * �Ԃ̃L���[���擾����B</p>
 * 
 * <p>�擾�����L���[�́A���͏������s�� <code>collector</code> �����̃R���N�^��
 * �n�����B�܂��A���̃N���X�ł́A�R���N�^�̏������ʃn���h���̌Ăяo���ƁA
 * �����̓��͏����ŗ�O�����������ꍇ�̗�O�n���h���Ăяo�����s���B</p>
 * 
 * <p><strong>�I�����̏���</strong></p>
 *
 * <p>�R���N�^�ɂ����͏����A����э�ƃL���[�ɃL���[�C���O���ꂽ��Ƃ̂��ׂĂ�
 * �I����҂������ƂŁA���̃N���X�̏����̓��^�[������B���̃N���X�̏�������
 * ���^�[������ۂɂ́A�W���u�X�e�[�^�X�����s���i<code>JobStatus.STATE.STARTED
 * </code>�A���邢��<code>JobStatus.STATE.RESTARTED</code>)�ł���ꍇ�ɂ͐���I
 * ���i<code>JobStatus.STATE.ENDING_NORMALLY</code>�j�ɍX�V����B</p>
 * 
 * <p><strong>�O����</strong></p>
 * 
 * <p>�R���N�^�ɂ����͏����̋N�����s����O�ɁA<code>preProcessor</code> ����
 * �ɐݒ肳�ꂽ�T�|�[�g�v���Z�b�T�ɂ���đO�������s����B�O�������s��ꂽ���
 * �A�W���u�X�e�[�^�X�̃`�F�b�N���s����B�W���u�X�e�[�^�X���p����ԂłȂ��ꍇ
 * �ɂ́A�R���N�^�ɂ����͏����Ȃǂ͋N�����ꂸ�A�����ɂ��̃N���X���烊�^�[����
 * ��B</p>
 * 
 * <p><strong>�㏈��</strong></p>
 * 
 * <p>�R���N�^�ɂ����͏����A����э�ƃL���[�ɃL���[�C���O���ꂽ��Ƃ��I�����
 * ��ł́A<code>postProcessor</code> �����ɐݒ肳�ꂽ�T�|�[�g�v���Z�b�T�ɂ����
 * �㏈�����s����B�R���N�^�ɂ����͏����A����э�ƃL���[�ɃL���[�C���O����
 * ����Ƃɂ���ăW���u�X�e�[�^�X������I���A�ُ�I���Ȃǂ̏I����ԂɍX�V�����
 * �����ꍇ�ɂ́A�W���u�㏈���͋N������Ȃ��B</p>
 *
 */
public class JobManager implements Workable<WorkUnit> {

    /**
     * ���O�C���X�^���X�B
     */
    private static Log log = LogFactory.getLog(JobManager.class);

    /**
     * �R���N�^�B
     */
    private Collector<JobContext> collector = null;

    /**
     * �Ώۃf�[�^�擾���ʂ̃n���h���B
     */
    private CollectorResultHandler collectorResultHandler = null;

    /**
     * ��ƃL���[�̃t�@�N�g���B
     */
    private WorkQueueFactory workQueueFactory = null;

    /**
     * �W���u�}�l�W���[���B
     */
    private String name = null;

    /**
     * ��O�n���h����`�B��O���L�[�Ƃ��āA�L�[�̗�O�ɑΉ������O�n���h����
     * �ݒ肷��B
     */
    private LinkedHashMap<JobException, JobExceptionHandler> 
        exceptionHandlerMap = null;

    /**
     * �f�t�H���g��O�n���h���B
     */
    private JobExceptionHandler defaultJobExceptionHandler = null;

    /**
     * �W���u�O�������s���T�|�[�g�v���Z�b�T�B
     */
    private SupportProcessor preProcessor = null;

    /**
     * �W���u�㏈�����s���T�|�[�g�v���Z�b�T�B
     */
    private SupportProcessor postProcessor = null;

    /**
     * ��Ƃ��s���B
     *
     * @param workUnit ��ƒP��
     * @param jobStatus �W���u�X�e�[�^�X
     */
    public void work(WorkUnit workUnit, JobStatus jobStatus) {
        JobStatus childJobStatus = null;
        childJobStatus = jobStatus.getChild(workUnit.getJobContext());
        
        //�J�n���O
        writeStartLog(childJobStatus);

        preProcessor.process(workUnit.getJobContext(), childJobStatus);
        if (!childJobStatus.isContinue()) {
            return;
        }

        // �{����
        WorkQueue managerQueue = workQueueFactory.getWorkQueue(childJobStatus);

        processCollect(workUnit.getJobContext(), managerQueue, childJobStatus);

        managerQueue.waitForAllWorkers();

        postProcessor.process(workUnit.getJobContext(), childJobStatus);

        finishWork(jobStatus, childJobStatus);
        //�I�����O
        writeEndLog(childJobStatus);
    }

    /**
     * ��ƏI���������s���B
     * 
     * @param parentJobStatus �N�����̃W���u�X�e�[�^�X
     * @param jobStatus ���̃W���u�}�l�[�W���̃W���u�X�e�[�^�X
     */
    protected void finishWork(JobStatus parentJobStatus, JobStatus jobStatus) {
        if (jobStatus.isExecuting() || jobStatus.isShutdownGraceful()
                || jobStatus.isShutdownImmediate()) {
            jobStatus.setJobState(JobStatus.STATE.ENDING_NORMALLY);
        }
    }

    /**
     * �Ώۃf�[�^�擾���s���B
     *
     * @param jobContext �W���u�R���e�L�X�g
     * @param workQueue �Ώۃf�[�^�i�[�p�L���[
     * @param jobStatus �W���u�X�e�[�^�X
     */
    private void processCollect(JobContext jobContext, WorkQueue workQueue,
             JobStatus jobStatus) {
        CollectorResult collectorResult = null;
        try {
            collectorResult    = collector.collect(jobContext, workQueue,
                    jobStatus);

            collectorResultHandler.handle(collectorResult, jobStatus);
        } catch (RuntimeException e) {
            // Collector�ACollector�������ʃn���h���Ŕ���������O�̏���

            CollectorException wrappingException
                = new CollectorException(e, collectorResult);

            JobExceptionHandler handler
                = ExceptionHandlerUtil.getJobExceptionHandler(wrappingException,
                            exceptionHandlerMap, defaultJobExceptionHandler);

            handler.handlException(jobContext, wrappingException, jobStatus);
        }
    }

    /**
     * �Ώۃf�[�^�擾���ʂ̃n���h����ݒ肷��B
     *
     * @param collectorResultHandler �Ώۃf�[�^�擾���ʂ̃n���h��
     */
    public void setCollectorResultHandler(
            CollectorResultHandler collectorResultHandler) {
        this.collectorResultHandler = collectorResultHandler;
    }

    /**
     * ��ƃL���[�̃t�@�N�g����ݒ肷��B
     *
     * @param workQueueFactory ��ƃL���[�̃t�@�N�g��
     */
    public void setWorkQueueFactory(WorkQueueFactory workQueueFactory) {
        this.workQueueFactory = workQueueFactory;
    }

    /**
     * �R���N�^��ݒ肷��B
     *
     * @param collector �R���N�^
     */
    public void setCollector(Collector<JobContext> collector) {
        this.collector = collector;
    }

    /**
     * �W���u�}�l�W���[����ݒ肷��B
     *
     * @param name �W���u�}�l�W���[��
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * �W���u�㏈�����s���T�|�[�g�v���Z�b�T��ݒ肷��B
     *
     * @param postProcessor �W���u�㏈�����s���T�|�[�g�v���Z�b�T
     */
    public void setPostProcessor(SupportProcessor postProcessor) {
        this.postProcessor = postProcessor;
    }

    /**
     * �W���u�O�������s���T�|�[�g�v���Z�b�T��ݒ肷��B
     *
     * @param preProcessor �W���u�O�������s���T�|�[�g�v���Z�b�T
     */
    public void setPreProcessor(SupportProcessor preProcessor) {
        this.preProcessor = preProcessor;
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
     * �f�t�H���g��O�n���h����ݒ肷��B
     * 
     * @param defaultJobExceptionHandler �f�t�H���g��O�n���h��
     */
    public void setDefaultJobExceptionHandler(
        JobExceptionHandler defaultJobExceptionHandler) {
        this.defaultJobExceptionHandler = defaultJobExceptionHandler;
    }
    
    /**
     * JobManager�̊J�n���O���o�͂���B
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
            logStr.append(" [JobManagerName=");
            logStr.append(name);
            logStr.append("]");
            logStr.append(" [JobState=");
            logStr.append(jobStatus.getJobState());
            logStr.append("]");
            log.debug(logStr.toString());
        }
    }

    /**
     * JobManager�̏I�����O���o�͂���B
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
            logStr.append(" [JobManagerName=");
            logStr.append(name);
            logStr.append("]");
            logStr.append(" [JobState=");
            logStr.append(jobStatus.getJobState());
            logStr.append("]");
            log.debug(logStr.toString());
        }
    }
}
