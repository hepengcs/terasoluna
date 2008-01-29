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

package jp.terasoluna.fw.batch.init;

import java.util.ArrayList;
import java.util.List;

import jp.terasoluna.fw.batch.core.InitializeException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * �W���u�˗����̊i�[�N���X�B
 * 
 */
public class JobRequestInfo implements JobInfo {

    /**
     * Serializable�p�o�[�W����ID
     */
    private static final long serialVersionUID = 3134293738030810122L;

    /**
     * ���O�N���X�B
     */
    private static Log log = LogFactory.getLog(JobRequestInfo.class);

    /**
     * �v���Z�XID�̎��ʎq�B
     */
    private static final String PROCESS_ID_SPLIT = "-P";

    /**
     * �W���uID�B
     */
    private String jobId = null;

    /**
     * �W���uBean��`�t�@�C���̑��΃p�X�B
     */
    private String jobDiscriptorPath = null;

    /**
     * �W���u�p�����[�^�i�[�l�B
     */
    private String[] jobParameters = null;

    /**
     * �W���u�v���Z�XID�B
     */
    private String jobRequestNo = "";

    /**
     * �N�������B
     */
    private String[] inParameters = null;

    /**
     * �W���u�˗����̊i�[�N���X�̐ݒ���s���R���X�g���N�^�B
     * 
     * @param inStr
     *            �N�����̈���
     */
    public JobRequestInfo(String[] inStr) {
        this.inParameters = inStr;
    }

    /**
     * �W���u�˗����̏������p���\�b�h�B ������<code>String</code>�z���K�؂�
     * �l�ɐݒ肷��B
     * 
     */
    public void init() {
        if (inParameters == null || inParameters.length < 2) {
            throw new InitializeException(
                   "There is not a required argument at the time of job start");
        }
        setJobId(inParameters[0]);
        setJobDiscriptorPath(inParameters[1]);
        setJobParameters(inParameters);
        if (log.isDebugEnabled()) {
            log.debug("parameter values�F" + toString());
        }
    }

    /**
     * �W���u�p�����[�^���͒l��Ԃ��B
     * 
     * @return �W���u�p�����[�^���͒l
     */
    public String[] getJobParameters() {
        return jobParameters;
    }

    /**
     * �W���u��`�t�@�C���̑��΃p�X��Ԃ��B
     * 
     * @return �W���u��`�t�@�C���̑��΃p�X
     */
    public String getJobDiscriptorPath() {
        return jobDiscriptorPath;
    }

    /**
     * �W���uID��Ԃ��B
     * 
     * @return �W���uID
     */
    public String getJobId() {
        return jobId;
    }

    /**
     * �W���u�v���Z�XID��Ԃ��B
     * 
     * @return �W���u�v���Z�XID
     */
    public String getJobRequestNo() {
        return jobRequestNo;
    }

    /**
     * �W���u�R���e�L�X�g�i�[�p�p�����[�^�ݒ�B<BR>
     * �W���u�v���Z�XID��ݒ�B
     * 
     * @param inStr
     *            �N�����̈���
     */
    private void setJobParameters(String[] inStr) {
        List<String> tempArray = new ArrayList<String>();
        
        for (int index = 2; index < inStr.length ; index++) {
            
            String tempStr = inStr[index];
            
            if (tempStr != null
                  && PROCESS_ID_SPLIT.equalsIgnoreCase(tempStr)) {
                // �W���u�v���Z�XID��ݒ�
                if (index < inStr.length - 1) {
                    this.jobRequestNo = inStr[++index];
                }
            } else {
                tempArray.add(tempStr);
            }
        }

        jobParameters = 
            (String[]) tempArray.toArray(new String[tempArray.size()]);
    }
    
    /**
     * �W���uBean�ݒ�t�@�C���̑��΃p�X�̐ݒ�B
     * 
     * @param jobDiscriptorPath
     *            �N�����̑�Q����
     */
    private void setJobDiscriptorPath(String jobDiscriptorPath) {
        if (jobDiscriptorPath == null) {
            throw new InitializeException(
                    "A job Bean definition file is null");
        }
        this.jobDiscriptorPath = jobDiscriptorPath;
    }

    /**
     * �W���uID�̐ݒ�B
     * 
     * @param jobId
     *            �N�����̑�P����
     */
    private void setJobId(String jobId) {
        if (jobId == null) {
            throw new InitializeException("jobID is null");
        }
        this.jobId = jobId;
    }

    /**
     * �����̐ݒ�l�𕶎�����쐬�B
     * 
     * @return �ݒ�l�̕�����
     */
    public String toString() {
        StringBuilder returnStr = new StringBuilder();
        returnStr.append("[jobId=" + jobId + "]");
        returnStr.append("[jobDiscriptPath�F" + jobDiscriptorPath + "]");
        returnStr.append("[parameters�F[");
        for (int i = 0; i < jobParameters.length; i++) {
            returnStr.append(jobParameters[i]);
            if (i != jobParameters.length - 1) {
                returnStr.append(", ");
            }
        }
        returnStr.append("]]");
        returnStr.append("[jobRequestNo�F" + jobRequestNo + "]");

        return returnStr.toString();
    }

}
