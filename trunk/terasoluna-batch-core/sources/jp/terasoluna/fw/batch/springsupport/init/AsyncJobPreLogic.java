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

import jp.terasoluna.fw.batch.openapi.BLogicResult;
import jp.terasoluna.fw.batch.openapi.ReturnCode;
import jp.terasoluna.fw.batch.openapi.SupportLogic;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * �񓯊��^�W���u�N���ɂ����āA�W���u�����s�O�Ɏ��s����T�|�[�g�����B
 * 
 * <p>�N���Ώۂ̃W���u�̃W���u�Ǘ��e�[�u���̋N���󋵂��u�N�����v�ɍX�V����B</p>
 * 
 */
public class AsyncJobPreLogic implements SupportLogic<AsyncJobContext> {

    /**
     * ���O�C���X�^���X�B
     */
    private static Log log = LogFactory.getLog(AsyncJobPreLogic.class);

    /**
     * ���O���b�Z�[�W�i����N���p�j�B
     */
    private String successMessage = "job starting by BatchDaemon";

    /**
     * �R���X�g���N�^
     *
     */
    public AsyncJobPreLogic() {
    }
    
    /**
     * �R���X�g���N�^
     * 
     * @param successMessage ���O���b�Z�[�W�i����I���p�j
     */
    protected AsyncJobPreLogic(String successMessage) {
        this.successMessage = successMessage;
    }

    /**
     * �W���u�N���O�����B<BR>
     * �N���Ώۂ̃W���u�˗����̋N���󋵂��N�����ɍX�V���鏈���B
     * 
     * @param jobContext �W���u�˗������i�[�����W���u�R���e�L�X�g
     * @return �񓯊��W���u���s�O�����̏������ʁB�N���󋵂��X�V�ł��Ȃ�����
     * �ꍇ�ɂ́A<code>ReturnCode.ERROR_END</code>�B
     */
    public BLogicResult execute(AsyncJobContext jobContext) {
        // �W���u�J�n���O
        if (log.isDebugEnabled()) {
            printDebugLog(successMessage, jobContext.getJobInfo().getJobId());
        }
        
        return new BLogicResult(ReturnCode.NORMAL_CONTINUE);
    }

    /**
     * �f�o�b�N���b�Z�[�W�̏o�́B
     * 
     * @param message ���O���b�Z�[�W
     * @param requestID �W���u�˗��ԍ�
     */
    private void printDebugLog(String message, String requestID) {
        StringBuilder builder = new StringBuilder(message);
        builder.append(" �yJob RequestID : ");
        builder.append(requestID);
        builder.append("�z");
        log.debug(builder.toString());
    }
}
