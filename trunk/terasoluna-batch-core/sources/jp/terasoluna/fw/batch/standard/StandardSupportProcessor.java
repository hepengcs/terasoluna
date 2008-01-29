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

import java.util.LinkedHashMap;
import java.util.List;

import jp.terasoluna.fw.batch.core.ExceptionHandlerUtil;
import jp.terasoluna.fw.batch.core.JobException;
import jp.terasoluna.fw.batch.core.JobExceptionHandler;
import jp.terasoluna.fw.batch.core.JobStatus;
import jp.terasoluna.fw.batch.core.SupportLogicException;
import jp.terasoluna.fw.batch.core.SupportLogicResultHandler;
import jp.terasoluna.fw.batch.core.SupportProcessor;
import jp.terasoluna.fw.batch.openapi.BLogicResult;
import jp.terasoluna.fw.batch.openapi.JobContext;
import jp.terasoluna.fw.batch.openapi.SupportLogic;


/**
 * <code>SupportProcessor</code> �C���^�t�F�[�X�̕W�������N���X�B
 *
 * <p>�T�|�[�g���W�b�N�̃��X�g�ɃT�|�[�g���W�b�N�������N������B
 * �N�������T�|�[�g���W�b�N�� <code>ERROR_END</code>�A���邢�� 
 * <code>NORMAL_END</code>��ԋp�����ꍇ�ɂ͂��̎��_�ŏ����𒆎~����B</p>
 * 
 * <p><code>canSkip()</code> �� <code>true</code> ��Ԃ��Ƃ��ɂ́A�T�|�[�g���W�b
 * �N�͋N�����Ȃ��B�܂��A�����œn���ꂽ <code>jobStatus</code> ���p����ԂłȂ�
 * �ꍇ�i�I����Ԃł���ꍇ�j�ɂ́A�T�|�[�g���W�b�N���N�������ɒ����Ƀ��^�[��
 * ����B</p>
 * 
 */
public class StandardSupportProcessor implements SupportProcessor {

    /**
     * �T�|�[�g���W�b�N�̃��X�g�B
     */
    private List<SupportLogic<JobContext>> supportLogicList = null;
    
    /**
     * �T�|�[�g���W�b�N�̏������ʃn���h���B
     */
    private SupportLogicResultHandler supportLogicResultHandler = null;

    /**
     * ��O�n���h���}�b�v�B
     */
    private LinkedHashMap<JobException, JobExceptionHandler> 
        exceptionHandlerMap = null;

    /**
     * �f�t�H���g��O�n���h���B
     */
    private JobExceptionHandler defaultJobExceptionHandler = null;
    
    /**
     * �������B
     */
    private String supportProcessorName = null;

    /**
     * �T�|�[�g�������s���B
     * 
     * <p>�����ɐݒ肳�ꂽ�T�|�[�g���W�b�N�̃��X�g���������s����B</p>
     * 
     * @param jobContext �W���u�R���e�N�X�g
     * @param jobStatus �W���u�X�e�[�^�X
     */
    public void process(JobContext jobContext, JobStatus jobStatus) {
        if (canSkip()) {
            return;
        }
        
        if (!jobStatus.isContinue()) {
            return;
        }
        
        BLogicResult supportLogicResult = null;
        for (SupportLogic<JobContext> supportLogic : supportLogicList) {
            try {
                supportLogicResult = supportLogic.execute(jobContext);
                supportLogicResultHandler.handle(supportLogicResult, jobStatus,
                        supportProcessorName);
            } catch (RuntimeException e) {
                // PreLogic�Ŕ���������O�̏���
                SupportLogicException wrappingException 
                    = createSupportLogicException(supportLogicResult, e);

                JobExceptionHandler handler = ExceptionHandlerUtil
                        .getJobExceptionHandler(wrappingException,
                               exceptionHandlerMap, defaultJobExceptionHandler);

                handler.handlException(jobContext, wrappingException,
                        jobStatus);
            }
                
            if (!jobStatus.isContinue()) {
                break;
            }
        }
    }

    /**
     * �T�|�[�g������O�𐶐�����B
     * @param supportLogicResult �T�|�[�g��������
     * @param e ��O
     * @return �T�|�[�g������O�C���X�^���X
     */
    private SupportLogicException createSupportLogicException(
        BLogicResult supportLogicResult, RuntimeException e) {
        SupportLogicException wrappingException = new SupportLogicException(
                e, supportLogicResult, supportProcessorName);
        return wrappingException;
    }

    /**
     * �T�|�[�g�������X�L�b�v�ł���ꍇ�ɂ́A<code>true</code> ��Ԃ��B 
     * 
     * @return �T�|�[�g���W�b�N���ЂƂ��ݒ肳��Ă��Ȃ��ꍇ�ɂ�
     *  <code>true</code>
     */
    public boolean canSkip() {
        return supportLogicList == null || supportLogicList.size() == 0;
    }

    /**
     * �T�|�[�g���W�b�N�̃��X�g��ݒ肷��B
     * 
     * @param supportLogicList �T�|�[�g���W�b�N�̃��X�g
     */
    public void setSupportLogicList(
            List<SupportLogic<JobContext>> supportLogicList) {
        this.supportLogicList = supportLogicList;
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
     * ��O�n���h���}�b�v��ݒ肷��B
     * 
     * @param exceptionHandlerMap ��O�n���h���}�b�v
     */
    public void setExceptionHandlerMap(
            LinkedHashMap<JobException,
            JobExceptionHandler> exceptionHandlerMap) {
        this.exceptionHandlerMap = exceptionHandlerMap;
    }

    /**
     * �T�|�[�g���W�b�N�̏������ʃn���h����ݒ肷��B
     * 
     * @param supportLogicResultHandler �T�|�[�g���W�b�N�̏������ʃn���h��
     */
    public void setSupportLogicResultHandler(
            SupportLogicResultHandler supportLogicResultHandler) {
        this.supportLogicResultHandler = supportLogicResultHandler;
    }

    /**
     * ��������ݒ肷��B
     * 
     * @param supportProcessorName ������
     */
    public void setSupportProcessorName(String supportProcessorName) {
        this.supportProcessorName = supportProcessorName;
    }
}
