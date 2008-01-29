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

package jp.terasoluna.fw.batch.monitor;

/**
 * JMX�ɂ��Ď��Ώۂ���������C���^�t�F�[�X�B
 * 
 */
public interface MonitorableJobStatusMBean {


    /**
     * �W���u��Ԃ�ԋp����B
     * @return �W���u���
     */
    String getJobStateStr();

    /**
     * �W���u�J�n������ԋp����A
     * @return �W���u�J�n����
     */
    String getJobStartTime();

    /**
     * �W���u�������Ԃ�ԋp����B
     * @return ��������
     */
    String getProcessingTime();

    /**
     * �R�~�b�g�񐔂�ԋp����B
     * @return �R�~�b�g��
     */
    int getCommitCount();

    /**
     * �r�W�l�X���W�b�N���ԋp����NORMAL_CONTINUE�̌�����ԋp����B
     * @return NORMAL_CONTINUE����
     */
    int getNormalContinueCount();

    /**
     * �r�W�l�X���W�b�N���ԋp����ERROR_CONTINUE�̌�����ԋp����B
     * @return ERROR_CONTINUE����
     */
    int getErrorContinueCount();

    /**
     * �W���u�i�q�W���u�j�������I������B
     */
    void shutdownGraceful();

    /**
     * �W���u�i�q�W���u�j�𒆒f�I������B
     */
    void shutdownImmediate();

}
