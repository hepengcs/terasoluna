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

import java.util.Date;

/**
 * �W���u�Ǘ��e�[�u���̃f�[�^�擾�p�p�����[�^�N���X�B<BR>
 * <code>jobControl-sqlMap.xml</code>�ɒ�`���ꂽ�ȉ��̃N���X��`��ύX���邱�Ƃ�
 * �Ǝ��̃p�����[�^�N���X���쐬���邱�Ƃ��o����B<BR>
 * <code>&lt;typeAlias alias="jobControlInfo" 
 * type="jp.terasoluna.fw.batch.init.JobControlInfo"/&gt;</code>
 * 
 */
public class JobControlInfo extends AbstractJobControlInfo {

    /**
     * Serializable�p�o�[�W����ID
     */
    private static final long serialVersionUID = 3551061550379453205L;

    /**
     * �W���u���N�G�X�g�ԍ�
     */
    private String jobRequestNo = null;

    /**
     * �W���uID
     */
    private String jobId = null;

    /**
     * �W���uBean��`�t�@�C����PATH
     */
    private String jobDiscriptorPath = null;

    /**
     * �W���u�p�����[�^
     */
    private String jobParameters = null;

    /**
     * �W���u�N����
     */
    private String jobState = null;

    /**
     * �W���u�I���R�[�h
     */
    private String jobExitCode = null;

    /**
     * �W���u�˗����̍X�V����
     */
    private Date updateTime = null;

    /**
     * �W���u�˗����̓o�^����
     */
    private Date registerTime = null;

    /**
     * �W���u�I���R�[�h���擾�B
     * 
     * @return �W���u�I���R�[�h
     */
    public String getJobExitCode() {
        return jobExitCode;
    }

    /**
     * �W���u�I���R�[�h��ݒ�B
     * 
     * @param jobExitCode
     *            �W���u�I���R�[�h
     */
    public void setJobExitCode(String jobExitCode) {
        this.jobExitCode = jobExitCode;
    }

    /**
     * �W���uBean��`�t�@�C����PATH���擾�B
     * 
     * @return �W���uBean��`�t�@�C����PATH
     */
    public String getJobDiscriptorPath() {
        return jobDiscriptorPath;
    }

    /**
     * �W���uBean��`�t�@�C����PATH��ݒ�B
     * 
     * @param jobDiscriptorPath
     *            �W���uBean��`�t�@�C����PATH
     */
    public void setJobDiscriptorPath(String jobDiscriptorPath) {
        this.jobDiscriptorPath = jobDiscriptorPath;
    }

    /**
     * �W���uID���擾�B
     * 
     * @return String �W���uID
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
     * �W���u�p�����[�^���擾�B
     * 
     * @return �W���u�p�����[�^
     */
    public String[] getJobParameters() {
        if (jobParameters == null) {
            return new String[0];
        }
        return jobParameters.split(getJobParametersSplitStr());
    }

    /**
     * �W���u�N���p�����[�^��ݒ�B
     * 
     * @param jobParameters
     *            �W���u�N���p�����[�^
     */
    public void setJobParameters(String jobParameters) {
        this.jobParameters = jobParameters;
    }

    /**
     * �W���u�˗����̓o�^�������擾�B
     * 
     * @return �W���u�˗����̓o�^����
     */
    public Date getRegisterTime() {
        return registerTime;
    }

    /**
     * �W���u�˗����̓o�^������ݒ�B
     * 
     * @param registerTime
     *            �W���u�˗����̓o�^����
     */
    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }

    /**
     * �W���u���N�G�X�g�ԍ����擾�B
     * 
     * @return �W���u���N�G�X�g�ԍ�
     */
    public String getJobRequestNo() {
        return jobRequestNo;
    }

    /**
     * �W���u���N�G�X�g�ԍ���ݒ�B
     * 
     * @param jobRequestNo
     *            �W���u���N�G�X�g�ԍ�
     */
    public void setJobRequestNo(String jobRequestNo) {
        this.jobRequestNo = jobRequestNo;
    }

    /**
     * �W���u�N���󋵂��擾�B
     * 
     * @return �W���u�N����
     */
    public String getJobState() {
        return jobState;
    }

    /**
     * �W���u�N���󋵂�ݒ�B
     * 
     * @param jobState
     *            �W���u�N����
     */
    public void setJobState(String jobState) {
        this.jobState = jobState;
    }

    /**
     * �W���u�˗����̍X�V�������擾�B
     * 
     * @return �W���u�˗����̍X�V����
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * �W���u�˗����̍X�V������ݒ�B
     * 
     * @param updateTime
     *            �W���u�˗����̍X�V����
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
