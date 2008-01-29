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

import java.io.Serializable;

/**
 * ���X�^�[�g���擾�E�X�V�N���X�p�C���^�t�F�[�X�B
 * 
 */
public interface JobRestartInfo extends Serializable {

    /**
     * ���X�^�[�g�|�C���g���擾�B
     * 
     * @return restartPoint ���X�^�[�g�|�C���g
     */
    int getRestartPoint();

    /**
     * �N���󋵂��擾�B
     * 
     * @return �N����
     */
    String getState();

    /**
     * ���X�^�[�g�|�C���g��ݒ�B
     * 
     * @param restartPoint
     *            ���X�^�[�g�|�C���g
     */
    void setRestartPoint(int restartPoint);

    /**
     * �W���u�R���e�L�X�g���擾�B
     * 
     * @return jobContext �W���u�R���e�L�X�g
     */
    byte[] getJobContext();

    /**
     * �N���󋵂�ݒ�B
     * 
     * @param state
     *            �N����
     */
    void setState(String state);

    /**
     * ���N�G�X�g����ݒ�B
     * 
     * @param requestNo
     *            ���N�G�X�g���
     */
    void setRequestNo(String requestNo);

    /**
     * �W���uID��ݒ�B
     * 
     * @param jobId
     *            �W���uID
     */
    void setJobId(String jobId);

    /**
     * �p�[�e�B�V�����ԍ���ݒ�B
     * 
     * @param partitionNo
     *            �p�[�e�B�V�����ԍ�
     */
    void setPartitionNo(int partitionNo);

    /**
     * �p�[�e�B�V�����L�[��ݒ�B
     * 
     * @param partitionKey
     *            �p�[�e�B�V�����L�[
     */
    void setPartitionKey(String partitionKey);

    /**
     * �W���u�R���e�L�X�g��ݒ�B
     * 
     * @param jobContext
     *            �W���u�R���e�L�X�g
     */
    void setJobContext(byte[] jobContext);
}
