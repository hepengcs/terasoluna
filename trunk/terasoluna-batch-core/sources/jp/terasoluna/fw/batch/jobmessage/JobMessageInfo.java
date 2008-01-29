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

package jp.terasoluna.fw.batch.jobmessage;

import java.util.Date;

import jp.terasoluna.fw.batch.init.JobControlInfo;

/**
 * �W���u�˗����b�Z�[�W�Ǘ��e�[�u���̃p�����[�^�N���X�B
 * 
 * 
 */
public class JobMessageInfo extends JobControlInfo {

    /**
     * Serializable�p�o�[�W����ID�B
     */
    private static final long serialVersionUID = -2755193066158860641L;

    /**
     * �A���W���u�p�����[�^�B
     */
    private String jobConcatParameters;

    /**
     * �p�[�e�B�V����No�B
     */
    private int partitionNo = -1;

    /**
     * �p�[�e�B�V�����L�[�B
     */
    private String partitionKey = null;

    /**
     * ���b�Z�[�W����M�󋵁B
     */
    private String messageState;

    /**
     * ���b�Z�[�W��M�����B
     */
    private Date receiveTime;

    /**
     * ���b�Z�[�W���M�����B
     */
    private Date sendTime;

    /**
     * �W���u�I�����ԁB
     */
    private Date finishTime;

    /**
     * ���b�Z�[�W����M�󋵂̎擾�B
     * 
     * @return ���b�Z�[�W����M��
     */
    public String getMessageState() {
        return messageState;
    }

    /**
     * ���b�Z�[�W����M�󋵂̐ݒ�B
     * 
     * @param messageState ���b�Z�[�W����M��
     */
    public void setMessageState(String messageState) {
        this.messageState = messageState;
    }

    /**
     * ���b�Z�[�W��M�����̎擾�B
     * 
     * @return ���b�Z�[�W��M����
     */
    public Date getReceiveTime() {
        return receiveTime;
    }

    /**
     * ���b�Z�[�W��M�����̐ݒ�B
     * 
     * @param receiveTime ���b�Z�[�W��M����
     */
    public void setReceiveTime(Date receiveTime) {
        this.receiveTime = receiveTime;
    }

    /**
     * ���b�Z�[�W���M�����̎擾�B
     * 
     * @return ���b�Z�[�W���M����
     */
    public Date getSendTime() {
        return sendTime;
    }

    /**
     * ���b�Z�[�W���M�����̐ݒ�B
     * 
     * @param sendTime ���b�Z�[�W���M����
     */
    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    /**
     * �A���W���u�p�����[�^�̎擾�B
     * 
     * @return jobConcatParameters �A���W���u�p�����[�^
     */
    public String getJobConcatParameters() {
        return jobConcatParameters;
    }

    /**
     * �A���W���u�p�����[�^�̐ݒ�B
     * 
     * @param jobConcatParameters �A���W���u�p�����[�^
     */
    public void setJobConcatParameters(String jobConcatParameters) {
        this.jobConcatParameters = jobConcatParameters;
        super.setJobParameters(jobConcatParameters);
    }

    /**
     * �W���u�p�����[�^�̐ݒ�B
     * 
     * @param jobParameters �W���u�p�����[�^
     */
    @Override
    public void setJobParameters(String jobParameters) {
        setJobConcatParameters(jobParameters);
    }

    /**
     * �W���u�I�����Ԃ̎擾�B
     * 
     * @return �W���u�I������
     */
    public Date getFinishTime() {
        return finishTime;
    }

    /**
     * �W���u�I�����Ԃ̐ݒ�B
     * 
     * @param finishTime �W���u�I������
     */
    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    /**
     * �p�[�e�B�V�����L�[���擾����B
     * 
     * @return partitionKey �p�[�e�B�V�����L�[
     */
    public String getPartitionKey() {
        return partitionKey;
    }

    /**
     * �p�[�e�B�V�����L�[��ݒ肷��B
     * 
     * @param partitionKey �p�[�e�B�V�����L�[
     */
    public void setPartitionKey(String partitionKey) {
        this.partitionKey = partitionKey;
    }

    /**
     * �p�[�e�B�V����NO���擾����B
     * 
     * @return partitionNo �p�[�e�B�V����NO
     */
    public int getPartitionNo() {
        return partitionNo;
    }

    /**
     * �p�[�e�B�V����NO��ݒ肷��B
     * 
     * @param partitionNo �p�[�e�B�V����NO
     */
    public void setPartitionNo(int partitionNo) {
        this.partitionNo = partitionNo;
    }
}
