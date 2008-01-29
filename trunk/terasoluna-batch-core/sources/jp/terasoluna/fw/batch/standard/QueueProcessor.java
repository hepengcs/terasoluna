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
import jp.terasoluna.fw.batch.core.SupportProcessor;
import jp.terasoluna.fw.batch.core.WorkQueue;
import jp.terasoluna.fw.batch.core.WorkUnit;
import jp.terasoluna.fw.batch.core.Workable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * �L���[�����p�N���X�B
 *
 * <p>�L���[����v�f�����o���A���o�������ꂼ��̗v�f���p�����[�^�Ƃ��ă��[�J
 * �[���N������B</p>
 * 
 * <p>�L���[�̗v�f�̏������͂��߂�O�ɂ́A�ݒ肳�ꂽ�O�����v���Z�b�T���N������B
 * �O�����v���Z�b�T���N������ۂɂ́A�L���[�̐擪�v�f�� <code>JobContext</code> 
 * ���g�p�����B</p>
 * 
 * <p>���ׂẴL���[�̗v�f�̏������I�������ɁA�ݒ肳�ꂽ�㏈���v���Z�b�T���N��
 * ����B�㏈���v���Z�b�T���N������ۂɂ́A�L���[�̍ŏI�v�f�� <code>JobContext
 * </code> ���g�p�����B</p>
 *
 * <p>�L���[�ɗv�f��������݂��Ȃ������ꍇ�ɂ́A�O�����v���Z�b�T�A�㏈���v���Z
 * �b�T�̗����Ƃ��N������Ȃ��B</p>
 *
 */
public class QueueProcessor {

    /**
     * ���O�C���X�^���X�B
     */
    private static Log log = LogFactory.getLog(QueueProcessor.class);

    /**
     * ���[�J�[�B
     */
    private Workable<WorkUnit> worker = null;

    /**
     * �L���[�v���Z�b�T�̖��́B
     */
    private String name = null;

    /**
     * �O�����v���Z�b�T�B�L���[�̗v�f����������O�ɋN�������B
     */
    private SupportProcessor preProcessor = null;

    /**
     * �㏈���v���Z�b�T�B�L���[�̗v�f�����ׂď���������ŋN�������B
     */
    private SupportProcessor postProcessor = null;

    /**
     * �L���[�̏������s���B
     *
     * @param workQueue �L���[
     * @param jobStatus �W���u�X�e�[�^�X
     */
    public void process(WorkQueue workQueue, JobStatus jobStatus) {
        printStartLog(jobStatus);
        
        //  �O�����v���Z�b�T�̋N���ς݃t���O
        boolean donePreProcess = false;
        
        //�@�����ς݂̃L���[�̑O�v�f(�㏈���Ŏg�p����ŏI�L���[�̗v�f)
        WorkUnit preElement = null;
        
        while (true) {
            WorkUnit element = workQueue.take();
            
            if (element.isEndMark()) {
                if (preElement != null && postProcessor != null) {
                    postProcessor.process(preElement.getJobContext(),
                            jobStatus);
                }
                break;
            }

            if (!donePreProcess && preProcessor != null) {
                preProcessor.process(element.getJobContext(), jobStatus);
                if (!jobStatus.isContinue()) {
                    break;
                }

                donePreProcess = true;
            }
            
            // �{����
            worker.work(element, jobStatus);
            if (!jobStatus.isContinue()) {
                break;
            }
            
            preElement = element;
        }

        printEndLog(jobStatus);
    }

    /**
     * ���[�J�[��ݒ肷��B
     *
     * @param worker ���[�J�[
     */
    public void setWorker(Workable<WorkUnit> worker) {
        this.worker = worker;
    }

    /**
     * ���̂�ݒ肷��B
     *
     * @param name ����
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * �㏈���v���Z�b�T��ݒ肷��B
     *
     * @param postProcessor �㏈���v���Z�b�T
     */
    public void setPostProcessor(SupportProcessor postProcessor) {
        this.postProcessor = postProcessor;
    }

    /**
     * �O�����v���Z�b�T��ݒ肷��B
     *
     * @param preProcessor �O�����v���Z�b�T
     */
    public void setPreProcessor(SupportProcessor preProcessor) {
        this.preProcessor = preProcessor;
    }
    
    /**
     * QueueProcessor�̊J�n���O���o�͂���B
     * 
     * @param jobStatus �W���u�X�e�[�^�X
     */
    protected void printStartLog(JobStatus jobStatus) {
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
            logStr.append(" [QueueProcessorName=");
            logStr.append(name);
            logStr.append("]");
            logStr.append(" [JobState=");
            logStr.append(jobStatus.getJobState());
            logStr.append("]");
            log.debug(logStr.toString());
        }
    }

    /**
     * QueueProcessor�̏I�����O���o�͂���B
     * 
     * @param jobStatus �W���u�X�e�[�^�X
     */
    protected void printEndLog(JobStatus jobStatus) {
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
            logStr.append(" [QueueProcessorName=");
            logStr.append(name);
            logStr.append("]");
            logStr.append(" [JobState=");
            logStr.append(jobStatus.getJobState());
            logStr.append("]");
            log.debug(logStr.toString());
        }
    }
}
