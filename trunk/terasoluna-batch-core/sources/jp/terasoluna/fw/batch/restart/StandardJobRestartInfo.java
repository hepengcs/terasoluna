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

package jp.terasoluna.fw.batch.restart;

import java.util.Date;

/**
 * �W���u���X�^�[�g���i�[�p�����N���X�B <BR>
 * <code>jobRestart-sqlMap.xml</code>�ɒ�`���ꂽ�ȉ��̃N���X��`��ύX���邱�Ƃ�
 * �Ǝ��̃p�����[�^�N���X���쐬���邱�Ƃ��o����B<BR>
 * <code>&lt;typeAlias alias="jobRestartInfo" type="jp.terasoluna.fw.batch.restart.JobRestartInfoImpl"/&gt;</code>
 * 
 */
public class StandardJobRestartInfo implements JobRestartInfo {

    /**
     * Serializable�p�o�[�W����ID
     */
    private static final long serialVersionUID = -1860477193281337863L;

    /**
     * �W���u���N�G�X�g�ԍ�
     */
    private String requestNo = null;

    /**
     * �W���uID
     */
    private String jobId = null;

    /**
     * �W���u�p�[�e�B�V�����ԍ�
     */
    private int partitionNo = 0;

    /**
     * �W���u�p�[�e�B�V�����L�[
     */
    private String partitionKey = null;

    /**
     * ���X�^�[�g�|�C���g
     */
    private int restartPoint = 0;

    /**
     * �W���u�R���e�L�X�g
     */
    private byte[] jobContext = null;

    /**
     * ������
     */
    private String state = null;

    /**
     * ���X�^�[�g���̍X�V����
     */
    private Date updateTime = null;

    /**
     * ���X�^�[�g���̓o�^����
     */
    private Date registerTime = null;

    /**
     * ���N�G�X�g�����擾�B
     * 
     * @return requestNo ���N�G�X�g���
     */
    public String getRequestNo() {
        return requestNo;
    }

    /**
     * ���N�G�X�g����ݒ�B
     * 
     * @param requestNo
     *            ���N�G�X�g���
     */
    public void setRequestNo(String requestNo) {
        this.requestNo = requestNo;
    }

    /**
     * �W���uID���擾�B
     * 
     * @return jobId �W���uID
     */
    public String getJobId() {
        return jobId;
    }

    /**
     * �W���uID��ݒ�B
     * 
     * @param jobId
     *            �W���uID
     */
    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    /**
     * �p�[�e�B�V�����ԍ����擾�B
     * 
     * @return partitionNo �p�[�e�B�V�����ԍ�
     */
    public int getPartitionNo() {
        return partitionNo;
    }

    /**
     * �p�[�e�B�V�����ԍ���ݒ�B
     * 
     * @param partitionNo
     *            �p�[�e�B�V�����ԍ�
     */
    public void setPartitionNo(int partitionNo) {
        this.partitionNo = partitionNo;
    }

    /**
     * �p�[�e�B�V�����L�[���擾�B
     * 
     * @return partitionKey �p�[�e�B�V�����L�[
     */
    public String getPartitionKey() {
        return partitionKey;
    }

    /**
     * �p�[�e�B�V�����L�[��ݒ�B
     * 
     * @param partitionKey
     *            �p�[�e�B�V�����L�[
     */
    public void setPartitionKey(String partitionKey) {
        this.partitionKey = partitionKey;
    }

    /**
     * ���X�^�[�g�|�C���g���擾�B
     * 
     * @return restartPoint ���X�^�[�g�|�C���g
     */
    public int getRestartPoint() {
        return restartPoint;
    }

    /**
     * ���X�^�[�g�|�C���g��ݒ�B
     * 
     * @param restartPoint
     *            ���X�^�[�g�|�C���g
     */
    public void setRestartPoint(int restartPoint) {
        this.restartPoint = restartPoint;
    }

    /**
     * �W���u�R���e�L�X�g���擾�B
     * 
     * @return jobContext �W���u�R���e�L�X�g
     */
    public byte[] getJobContext() {
        return jobContext;
    }

    /**
     * �W���u�R���e�L�X�g��ݒ�B
     * 
     * @param jobContext
     *            �W���u�R���e�L�X�g
     */
    public void setJobContext(byte[] jobContext) {
        this.jobContext = jobContext;
    }

    /**
     * �N���󋵂��擾�B
     * 
     * @return state �N����
     */
    public String getState() {
        return state;
    }

    /**
     * �N���󋵂�ݒ�B
     * 
     * @param state
     *            �N����
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * ���X�^�[�g���o�^�������擾�B
     * 
     * @return registerTime ���X�^�[�g���o�^����
     */
    public Date getRegisterTime() {
        return registerTime;
    }

    /**
     * ���X�^�[�g���o�^������ݒ�B
     * 
     * @param registerTime
     *            ���X�^�[�g���o�^����
     */
    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }

    /**
     * ���X�^�[�g���X�V�������擾�B
     * 
     * @return updateTime ���X�^�[�g���X�V����
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * ���X�^�[�g���X�V������ݒ�B
     * 
     * @param updateTime
     *            ���X�^�[�g���X�V����
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

}
