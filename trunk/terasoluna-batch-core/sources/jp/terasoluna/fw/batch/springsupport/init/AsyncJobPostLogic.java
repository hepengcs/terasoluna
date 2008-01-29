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

package jp.terasoluna.fw.batch.springsupport.init;

import jp.terasoluna.fw.batch.init.JobControlTableHandler;
import jp.terasoluna.fw.batch.init.JobInfo;
import jp.terasoluna.fw.batch.openapi.BLogicResult;
import jp.terasoluna.fw.batch.openapi.ReturnCode;
import jp.terasoluna.fw.batch.openapi.SupportLogic;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * �񓯊��^�W���u�N���ɂ����āA�W���u�����s������Ɏ��s����T�|�[�g�����B
 * 
 * <p>���s�����W���u�̃W���u�Ǘ��e�[�u���̋N���󋵂��u����I���v���ɍX�V����B</p>
 * 
 */
public class AsyncJobPostLogic implements SupportLogic<AsyncJobContext> {

    /**
     * ���O�C���X�^���X�B
     */
    private static Log log = LogFactory.getLog(AsyncJobPostLogic.class);

    /**
     * �W���u�Ǘ��e�[�u���n���h���B
     */
    private JobControlTableHandler jobControlTableHandler = null;

    /**
     * ���O���b�Z�[�W�i�ُ�I���p�j�B
     */
    private String failureMessage = "job ended in failure by BatchDaemon";
    
    /**
     * ���O���b�Z�[�W�i����I���p�j�B
     */
    private String successMessage = "job ending by BatchDaemon";
    

    /**
     * �R���X�g���N�^
     *
     */
    public AsyncJobPostLogic() {
    }
    
    /**
     * �R���X�g���N�^
     * 
     * @param faulureMessage ���O���b�Z�[�W�i�ُ�I���p�j
     * @param successMessage ���O���b�Z�[�W�i����I���p�j
     */
    protected AsyncJobPostLogic(String faulureMessage, String successMessage) {
        this.failureMessage = faulureMessage;
        this.successMessage = successMessage;
    }

    /**
     * �񓯊��^�W���u�N���ɂ����āA�W���u�����s�㏈�����s���B
     * 
     * @param jobContext �W���u�˗������i�[�����W���u�R���e�L�X�g
     * @return �񓯊��W���u���s�㏈���̏������ʁB�N���󋵂��X�V�ł��Ȃ������ꍇ
     * �ɂ́A<code>ReturnCode.ERROR_END</code>�B
     */
    public BLogicResult execute(AsyncJobContext jobContext) {

        // �W���u���s����DB���f
        if (handle(jobContext.getJobInfo()) != 1) {
            if (log.isDebugEnabled()) {
                printDebugLog(failureMessage,
                        jobContext.getJobInfo().getJobId());
            }
            return new BLogicResult(ReturnCode.ERROR_END);
        }
        
        if (log.isDebugEnabled()) {
            printDebugLog(successMessage, jobContext.getJobInfo().getJobId());
        }

        return new BLogicResult(ReturnCode.NORMAL_END);
    }

    /**
     * �W���u�Ǘ��e�[�u���ւ̃n���h�����O
     * 
     * @param jobInfo �W���u�˗����
     * @return ������
     */
    protected int handle(JobInfo jobInfo) {
        return jobControlTableHandler.updateJobEnd(jobInfo);
    }
    
    /**
     * �W���u�Ǘ��e�[�u���n���h����ݒ肷��B
     * 
     * @param jobControlTableHandler �W���u�Ǘ��e�[�u���n���h��
     */
    public void setJobControlTableHandler(
            JobControlTableHandler jobControlTableHandler) {
        this.jobControlTableHandler = jobControlTableHandler;
    }

    /**
     * �f�o�b�N���b�Z�[�W�̏o�́B
     * 
     * @param message ���O���b�Z�[�W
     * @param jobId �W���u�h�c
     */
    private void printDebugLog(String message, String jobId) {
        StringBuilder builder = new StringBuilder(message);
        builder.append(" �yJob ID : ");
        builder.append(jobId);
        builder.append("�z");
        log.debug(builder.toString());
    }

}
