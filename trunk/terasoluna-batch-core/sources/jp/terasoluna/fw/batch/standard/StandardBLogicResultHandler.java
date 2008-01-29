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


import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import jp.terasoluna.fw.batch.core.BLogicResultHandler;
import jp.terasoluna.fw.batch.core.JobStatus;
import jp.terasoluna.fw.batch.messages.BLogicMessage;
import jp.terasoluna.fw.batch.messages.BLogicMessages;
import jp.terasoluna.fw.batch.messages.MessageAccessor;
import jp.terasoluna.fw.batch.openapi.BLogicResult;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <code>BLogicResultHandler</code> �C���^�t�F�[�X�̕W�������N���X�B
 * 
 * <p>�r�W�l�X���W�b�N�������ʂ� <code>JobStatus</code> �ɔ��f���A�G���[�ł���
 * �ꍇ�ɂ̓��O���o�͂���B</p>
 * 
 */
public class StandardBLogicResultHandler implements BLogicResultHandler {

    /**
     * ���O�C���X�^���X�B
     */
    private static Log log =
        LogFactory.getLog(StandardBLogicResultHandler.class);

    /**
     * ���b�Z�[�W�擾�N���X�̃C���X�^���X�B
     */
    private MessageAccessor messageAccessor = null;
    
    /**
     * ���O���x���B 
     *
     */
    protected enum LOG_TYPE {
        /**
         * TRACE�B
         */
        TRACE,
        /**
         * DEBUG�B
         */
        DEBUG,
        /** 
         * INFO�B
         */
        INFO,
        /** 
         * WARN�B
         */
        WARN,
        /** 
         * ERROR�B
         */
        ERROR,
        /** 
         * FATAL�B
         */
        FATAL
    }
    
    /**
     * BLogic�̏������ʂ���������B
     * 
     * @param blogicResult �r�W�l�X���W�b�N��������
     * @param blogicInputData �r�W�l�X���W�b�N�̓��̓f�[�^
     * @param jobStatus �W���u�X�e�[�^�X
     * @param batchUpdateMapList �o�b�`�X�V���X�g
     */
    public void handle(BLogicResult blogicResult, Object blogicInputData,
            JobStatus jobStatus, 
            List<LinkedHashMap<String, Object>> batchUpdateMapList) {
        jobStatus.countBLogic(blogicResult.getReturnCode());
        
        // BLogicResult�ɐݒ肳�ꂽ���b�Z�[�W�̏������s���B
        processBLogicMessages(blogicResult);

        switch (blogicResult.getReturnCode()) {
        case NORMAL_CONTINUE:
            processNormalContinue(jobStatus, blogicResult, batchUpdateMapList);
            break;
        case NORMAL_END:
            processNormalEnd(jobStatus, blogicResult, batchUpdateMapList);
            break;
        case ERROR_CONTINUE:
            processErrorContinue(blogicInputData, jobStatus, blogicResult);
            break;
        case ERROR_END:
            processErrorEnd(blogicInputData, jobStatus, blogicResult);
            break;
        default:
            throw new IllegalArgumentException(blogicResult.getReturnCode()
                    + " illegal ReturnCode");
        }
    }
    
    /**
     * {@link BLogicMessages}�̏������s���B<br />
     * {@link BLogicResult}��{@link BLogicResult#setErrors(BLogicMessages)}��
     * {@link BLogicResult#setMessages(BLogicMessages)}�Őݒ肳�ꂽ���b�Z�[�W��
     * �������s���B<br />
     * <br />
     * �f�t�H���g�����͈ȉ��ł���B<br />
     * <li>setMessages(BLogicMessages): INFO���x���̃��O�̏o��</li>
     * <li>setErrors(BLogicMessages): ERROR���x���̃��O�̏o��</li>
     * <br /><br />
     * ���̃��\�b�h���Ē�`���邱�Ƃ�BLogicMessage�̏�����ύX���邱�Ƃ��ł���B
     * 
     * @param blogicResult �r�W�l�X���W�b�N��������
     */
    protected void processBLogicMessages(BLogicResult blogicResult) {
        // messages��info���O�o��
        writeBLogicMessagesLog(blogicResult.getMessages(), LOG_TYPE.INFO);
        
        // errors��error���O�o��
        writeBLogicMessagesLog(blogicResult.getErrors(), LOG_TYPE.ERROR);
    }

    /**
     * <code>BLogicResult</code> �̃��^�[���R�[�h�� <code>NORMAL_CONTINUE</code>
     *  �ł���Ƃ��̏������s���B
     * 
     * <p>BLogicResult���o�b�`�X�V����ێ����Ă����ꍇ�ɂ́A�o�b�`�X�V���X�g��
     * �ǉ�����B</p>
     *
     * @param jobStatus �W���u�X�e�[�^�X
     * @param bLogicResult �r�W�l�X���W�b�N��������
     * @param batchUpdateMapList �o�b�`�X�V���X�g
     */
    protected void processNormalContinue(JobStatus jobStatus, 
            BLogicResult bLogicResult, 
            List<LinkedHashMap<String, Object>> batchUpdateMapList) {
        if (bLogicResult.getBatchUpdateMap() != null 
                && bLogicResult.getBatchUpdateMap().size() > 0) {
            batchUpdateMapList.add(bLogicResult.getBatchUpdateMap());
        }
    }

    /**
     * <code>BLogicResult</code> �̃��^�[���R�[�h�� <code>NORMAL_END</code> ��
     * ����Ƃ��̏������s���B
     * 
     * <p>BLogicResult���o�b�`�X�V����ێ����Ă����ꍇ�ɂ́A�o�b�`�X�V���X�g��
     * �ǉ�����B</p>
     * 
     * <p>JobStatus</p> �̃W���u��� <code>JobStatus.STATE.ENDING_NORMALLY
     * </code> �ɍX�V���A<code>BLogicResult<code>
     * �̃W���u�I���R�[�h�� <code>JobStatus</code> �ɔ��f����B</p>
     *
     * @param jobStatus �W���u�X�e�[�^�X
     * @param bLogicResult �r�W�l�X���W�b�N��������
     * @param batchUpdateMapList �o�b�`�X�V���X�g
     */
    protected void processNormalEnd(JobStatus jobStatus,
            BLogicResult bLogicResult,
            List<LinkedHashMap<String, Object>> batchUpdateMapList) {
        if (bLogicResult.getBatchUpdateMap() != null 
                && bLogicResult.getBatchUpdateMap().size() > 0) {
            batchUpdateMapList.add(bLogicResult.getBatchUpdateMap());
        }
        jobStatus.setJobState(JobStatus.STATE.ENDING_NORMALLY);
        jobStatus.setJobExitCode(bLogicResult.getJobExitCode());
    }

    /**
     * <code>BLogicResult</code> �̃��^�[���R�[�h�� <code>ERROR_CONTINUE</code>
     *  �ł���Ƃ��̏������s���B
     *
     * <p>�G���[���O���o�͂���B</p>
     * 
     * <p><code>BLogicResult</code> ���o�b�`�X�V����A�W���u�I���R�[�h��������
     * �����ꍇ�ł����������B</p>
     *
     * @param blogicInputData �r�W�l�X���W�b�N�̓��̓f�[�^
     * @param jobStatus �W���u�X�e�[�^�X
     * @param blogicResult �r�W�l�X���W�b�N��������
     */
    protected void processErrorContinue(Object blogicInputData,
            JobStatus jobStatus, BLogicResult blogicResult) {
        writeErrorLog(jobStatus, blogicResult, blogicInputData);
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
     * @param blogicInputData �r�W�l�X���W�b�N�̓��̓f�[�^
     * @param jobStatus �W���u�X�e�[�^�X
     * @param bLogicResult �r�W�l�X���W�b�N��������
     */
    protected void processErrorEnd(Object blogicInputData,
            JobStatus jobStatus, BLogicResult bLogicResult) {
        writeErrorLog(jobStatus, bLogicResult, blogicInputData);
        jobStatus.setJobState(JobStatus.STATE.ENDING_ABNORMALLY);
        jobStatus.setJobExitCode(bLogicResult.getJobExitCode());
    }

    /**
     * �r�W�l�X���W�b�N�ł̃G���[�f�[�^�����O�ɏo�͂���B
     * 
     * @param jobStatus �W���u�X�e�[�^�X
     * @param bLogicResult �r�W�l�X���W�b�N��������
     * @param blogicInputData �r�W�l�X���W�b�N�̓��̓f�[�^
     */
    protected void writeErrorLog(JobStatus jobStatus,
            BLogicResult bLogicResult, Object blogicInputData) {
        log.error("Error JobID: " + jobStatus.getJobId() + " InputData: " 
                + blogicInputData);
    }

    /**
     * BLogicMessage���X�g���w��̃��O���x���֏o�͂���B
     * 
     * @param blogicMessages BLogicMessage���X�g
     * @param logType ���O���x��
     */
    protected void writeBLogicMessagesLog(BLogicMessages blogicMessages,
            LOG_TYPE logType) {
        //���b�Z�[�W��Null������Ȃ��ꍇ�͉������Ȃ��B
        if (blogicMessages == null || blogicMessages.isEmpty()) {
            return;
        }
        // BLogicMessages�擾�p�C�e���[�^
        Iterator itr = blogicMessages.get();
        while (itr.hasNext()) {
            // BLogicMessage���擾
            BLogicMessage blogicMessage = (BLogicMessage) itr.next();

            if (blogicMessage.isResource()) {
                writeLog(messageAccessor.getMessage(blogicMessage.getKey(),
                        blogicMessage.getValues()), logType);
            } else {
                writeLog(blogicMessage.getKey(), logType);
            }
        }
    }

    /**
     * �w��̃��O���x���փ��b�Z�[�W���o�͂���B
     * 
     * @param message ���b�Z�[�W
     * @param logType ���O���x��
     */
    protected void writeLog(String message, LOG_TYPE logType) {
        switch (logType) {
        case TRACE:
            log.trace(message);
            break;
        case DEBUG:
            log.debug(message);
            break;
        case INFO:
            log.info(message);
            break;
        case WARN:
            log.warn(message);
            break;
        case ERROR:
            log.error(message);
            break;
        case FATAL:
            log.fatal(message);
            break;
        default:
            throw new IllegalArgumentException("No log Type which agrees.");
        }
    }
    
    /**
     * ���b�Z�[�W�擾�N���X�̃C���X�^���X��ݒ肷��B
     * 
     * @param messageAccessor
     *            ���b�Z�[�W�擾�N���X�̃C���X�^���X
     */
    public void setMessageAccessor(MessageAccessor messageAccessor) {
        this.messageAccessor = messageAccessor;
    }
}
