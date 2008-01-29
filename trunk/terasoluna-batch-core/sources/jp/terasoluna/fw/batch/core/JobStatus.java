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

package jp.terasoluna.fw.batch.core;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.terasoluna.fw.batch.openapi.JobContext;
import jp.terasoluna.fw.batch.openapi.ReturnCode;

/**
 * �W���u�̏����󋵂�ێ�����N���X�B
 * 
 */
public class JobStatus {

    /**
     * �W���u��Ԃ̗񋓃N���X�B
     *
     * <p><code>JobState</code> �ł́A�ȉ��̋N���󋵂�񋟂���</p>
     *
     * <div align="center">
     *  <table width="50%" border="1">
     *   <tr>
     *    <td> <b>�N����</b> </td>
     *    <td> <b>�N���󋵔ԍ�</b> </td>
     *    <td> <b>�T�v</b> </td>
     *   </tr>
     *   <tr>
     *    <td> <code>SUBMITTED</code> </td>
     *    <td> <code>0</code> </td>
     *    <td align="left">
     *      �N���O
     *    </td>
     *   </tr>
     *   <tr>
     *    <td> <code>STARTED</code> </td>
     *    <td> <code>1</code> </td>
     *    <td align="left">
     *      �N����
     *    </td>
     *   </tr>
     *   <tr>
     *    <td> <code>RESTARTED</code> </td>
     *    <td> <code>2</code> </td>
     *    <td align="left">
     *      �ċN����
     *    </td>
     *   </tr>
     *   <tr>
     *    <td> <code>ENDING_NORMALLY</code> </td>
     *    <td> <code>3</code> </td>
     *    <td align="left">
     *      ����I��
     *    </td>
     *   </tr>
     *   <tr>
     *    <td> <code>ENDING_ABNORMALLY</code> </td>
     *    <td> <code>4</code> </td>
     *    <td align="left">
     *      �ُ�I��
     *    </td>
     *   </tr>
     *   <tr>
     *    <td> <code>INTERRUPTED_FOR_GRACEFUL_SHUTDOWN</code> </td>
     *    <td> <code>5</code> </td>
     *    <td align="left">
     *      ���f�I����
     *    </td>
     *   </tr>
     *   <tr>
     *    <td> <code>INTERRUPTED_FOR_IMMEDIATE_SHUTDOWN</code> </td>
     *    <td> <code>6</code> </td>
     *    <td align="left">
     *      �����I����
     *    </td>
     *   </tr>
     *   <tr>
     *    <td> <code>SUSPENDING</code> </td>
     *    <td> <code>7</code> </td>
     *    <td align="left">
     *      ���f/�����I��
     *    </td>
     *   </tr>
     *  </table>
     * </div>
     * <p>
     */
    public enum STATE {

        /**
         * �N���O�B
         */
        SUBMITTED(false),
        /**
         * �N�����B
         */
        STARTED(false),
        /**
         * �ċN�����B
         */
        RESTARTED(false),
        /**
         * ����I���B
         */
        ENDING_NORMALLY(true),
        /**
         * �ُ�I���B
         */
        ENDING_ABNORMALLY(true),
        /**
         * ���f�I�����B
         */
        INTERRUPTED_FOR_GRACEFUL_SHUTDOWN(false),
        /**
         * �����I�����B
         */
        INTERRUPTED_FOR_IMMEDIATE_SHUTDOWN(false),
        /**
         * ���f/�����I��
         */
        SUSPENDING(true);

        /**
         * �W���u��Ԃ��I����Ԃł��邩�ǂ����������t���O�B
         */
        private final boolean isEndStatus;

        /**
         * �R���X�g���N�^�B
         * 
         * @param isEndStatus �I����Ԃł���ꍇ�ɂ́A<code>true</code>
         */
        private STATE(boolean isEndStatus) {
            this.isEndStatus = isEndStatus;
        }

        /**
         * �I���󋵂𔻒f����B
         *
         * @return �I����
         */
        public boolean isEndStatus() {
            return isEndStatus;
        }
    }

    /**
     * �o�b�`�X�V�����B
     */
    private int batchUpdateCount = 0;

    /**
     * �Ώۃf�[�^�擾�����B
     */
    private int collected = 0;

    /**
     * �W���u�̃R�~�b�g�����B
     */
    private int commitCount = 0;

    /**
     * �����I�������B
     */
    private long jobEndTime = 0;

    /**
     * �W���u�I���R�[�h�B
     */
    private Integer jobExitCode = null;

    /**
     * �����J�n�����B
     */
    private long jobStartTime = 0;

    /**
     * �����󋵁B
     */
    private STATE jobState = STATE.SUBMITTED;

    /**
     * �W���u�̏����󋵂̃��X�g�B
     */
    private List<JobStatus> childJobStatusList;

    /**
     * ���X�^�[�g�����L���B
     */
    private boolean restartable = false;

    /**
     * �������X�^�[�g���B
     */
    private int restartPoint = 0;

    /**
     * BLogic�̏��������B
     */
    private ResultCounter resultCounter;

    /**
     * �W���u���N�G�X�g�ԍ��B
     */
    private String jobRequestNo = null;

    /**
     * �W���uID�B
     */
    private String jobId = null;

    /**
     * �W���u�p�[�e�B�V�����ԍ��B
     */
    private int partitionNo = 0;

    /**
     * �W���u�����L�[�B
     */
    private String partitionKey = null;

    /**
     * �R���X�g���N�^�B �W���u�̏����󋵂�����������B
     */
    public JobStatus() {
        childJobStatusList = new ArrayList<JobStatus>();
        resultCounter = new ResultCounter();
    }

    /**
     * �u���f/�����I���t���O�v�� <code>true</code> �̏ꍇ�A�����󋵂�
     * �u���f/�����I���v�ɐݒ肷��B
     * 
     */
    public void suspend() {
        if (jobState == STATE.INTERRUPTED_FOR_IMMEDIATE_SHUTDOWN
                || jobState == STATE.INTERRUPTED_FOR_GRACEFUL_SHUTDOWN) {
            setJobState(STATE.SUSPENDING);
        }
    }
    /**
     * �q�W���u�̏������ʂ����X�g�ɒǉ�����B
     * 
     * @param jobStatus �q�W���u�̏�����
     */
    protected void addChildJobStatus(JobStatus jobStatus) {
        childJobStatusList.add(jobStatus);
    }

    /**
     * �r�W�l�X���W�b�N�̎��s���J�E���g����B
     * 
     * @param returnCode �r�W�l�X���W�b�N�̃��^�[���R�[�h
     */
    public void countBLogic(ReturnCode returnCode) {
        resultCounter.count(returnCode);
    }

    /**
     * �q�W���u�����󋵂��擾����B
     * 
     * @param jobContext �W���u�R���e�L�X�g
     * @return �q�W���u������
     */
    public JobStatus getChild(JobContext jobContext) {
        JobStatus childJobStatus = new JobStatus();
        resetChildData(childJobStatus, jobContext);
        return childJobStatus;    
    }

    /**
     * �q�W���u�����󋵂ɐe�W���u�����󋵂�ݒ肷��B
     * 
     * @param childJobStatus �q�W���u��
     * @param jobContext �W���u�R���e�L�X�g
     */
    protected void resetChildData(JobStatus childJobStatus ,
            JobContext jobContext) {
        childJobStatus.setJobId(this.getJobId());
        childJobStatus.setJobRequestNo(this.getJobRequestNo());
        childJobStatus.setRestartPoint(this.getRestartPoint());
        childJobStatus.setRestartable(this.isRestartable());
        childJobStatus.setJobState(this.getJobState());
        childJobStatus.setPartitionNo(jobContext.getPartitionNo());
        childJobStatus.setPartitionKey(jobContext.getPartitionKey());

        if (isShutdownImmediate() || isShutdownGraceful()) {
            childJobStatus.suspend();
        }
        addChildJobStatus(childJobStatus);
    }
    
    /**
     * Collector�̏���������ԋp����B
     * 
     * @return Collector�̏�������
     */
    public int getCollected() {
        return collected;
    }

    /**
     * �R�~�b�g�����擾����B
     * 
     * @return �R�~�b�g��
     */
    public int getCommitCount() {

        int count = 0;

        if (!childJobStatusList.isEmpty()) {
            for (JobStatus result : childJobStatusList) {
                count += result.getCommitCount();
            }
        } else {
            count = commitCount;
        }

        return count;
    }

    /**
     * �����p�������擾����B
     * 
     * @return �����p����
     */
    public int getErrorContinueCount() {

        int count = 0;

        if (!childJobStatusList.isEmpty()) {
            for (JobStatus result : childJobStatusList) {
                count += result.getErrorContinueCount();
            }
        } else {
            count = resultCounter.getErrorContinueCount();
        }

        return count;
    }

    /**
     * �W���u�I���R�[�h���擾����B
     * 
     * @return �W���u�I���R�[�h
     */
    public Integer getJobExitCode() {
        return jobExitCode;
    }

    /**
     * �o�b�`�X�V�������擾����B
     * 
     * @return �o�b�`�X�V����
     */
    public int getBatchUpdateCount() {
        return batchUpdateCount;
    }

    /**
     * �W���u�J�n�������擾����B
     * 
     * @return �W���u�J�n����
     */
    public String getJobStartTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        return sdf.format(new Date(jobStartTime));
    }

    /**
     * �W���u�̏�Ԃ��擾����B
     * 
     * @return �������
     */
    public STATE getJobState() {
        return jobState;
    }

    /**
     * �W���u��Ԃ̕�������擾����B
     * 
     * @return ������Ԃ̕�����
     */
    public String getJobStateStr() {

        if (jobState != null) {
            return jobState.toString();
        }

        return null;
    }

    /**
     * �q�W���u�̏������ʂ�ێ��������X�g���擾����B
     * 
     * @return �q�W���u�̏������ʃ��X�g
     */
    public List<JobStatus> getChildJobStatusList() {
        return childJobStatusList;
    }

    /**
     * �����p�������擾����B
     * 
     * @return �����p����
     */
    public int getNormalContinueCount() {

        int count = 0;

        if (!childJobStatusList.isEmpty()) {
            for (JobStatus result : childJobStatusList) {
                count += result.getNormalContinueCount();
            }
        } else {
            count = resultCounter.getNormalContinueCount();
        }

        return count;
    }

    /**
     * �W���u�������Ԃ��擾����B(ms�P��)
     * 
     * @return �W���u�������Ԃ̕�����(xxxxxms)
     */
    public String getProcessingTime() {
        long now = System.currentTimeMillis();
        if (jobState.isEndStatus()) {
            now = jobEndTime;
        }
        return String.valueOf(now - jobStartTime) + "ms";
    }

    /**
     * ���X�^�[�g�|�C���g���擾����B
     * 
     * @return ���X�^�[�g�|�C���g
     */
    public int getRestartPoint() {
        return restartPoint + resultCounter.getToralCount();
    }

    /**
     * �o�b�`�����X�V�����J�E���g����B
     * 
     * @param batchUpdateCount �o�b�`�����X�V��
     */
    public void incrementBatchUpdateCount(int batchUpdateCount) {
        this.batchUpdateCount += batchUpdateCount;
    }

    /**
     * Collecter�̏����������C���N�������g����B
     * 
     */
    public void incrementCollected() {
        collected++;
    }

    /**
     * �R�~�b�g�����C���N�������g����B
     * 
     */
    public void incrementCommitCount() {
        commitCount++;
    }

    /**
     * �W���u���p���󋵂ł��邩�ǂ������擾����B
     * 
     * @return �W���u���p���󋵂ł���ꍇ�� <code>true</code>
     */
    public boolean isContinue() {
        return !jobState.isEndStatus();
    }

    /**
     * �W���u���N��/�ċN���󋵂ł��邩�ǂ������擾����B
     * 
     * @return �W���u���N��/�ċN���󋵂ł���ꍇ�� <code>true</code>
     */
    public boolean isExecuting() {
        return jobState == STATE.STARTED || jobState == STATE.RESTARTED;
    }

    /**
     * �W���u�����X�^�[�g�\�ȃW���u�ł��邩�ǂ������擾����B
     * 
     * @return �W���u�����X�^�[�g�\�ȃW���u�ł���ꍇ�� <code>true</code>
     */
    public boolean isRestartable() {
        return restartable;
    }

    /**
     * ���f�I�����ݒ肳��Ă��邩�ǂ������擾����B
     * 
     * @return ���f�I�����ݒ肳��Ă���ꍇ�� <code>true</code>
     */
    public boolean isShutdownGraceful() {
        return jobState == STATE.INTERRUPTED_FOR_GRACEFUL_SHUTDOWN;
    }

    /**
     * �����I�����ݒ肳��Ă��邩�ǂ������擾����B
     * 
     * @return �����I�����ݒ肳��Ă���ꍇ�� <code>true</code>
     */
    public boolean isShutdownImmediate() {
        return jobState == STATE.INTERRUPTED_FOR_IMMEDIATE_SHUTDOWN;
    }

    /**
     * �I���R�[�h��ݒ肷��B
     * 
     * @param jobExitCode �I���R�[�h
     */
    public void setJobExitCode(Integer jobExitCode) {
        this.jobExitCode = jobExitCode;
    }

    /**
     * �W���u�̏�Ԃ�ݒ肷��B
     * 
     * @param jobState �������
     */
    public void setJobState(STATE jobState) {
        this.jobState = jobState;
        if (jobState.isEndStatus()) {
            jobEndTime = System.currentTimeMillis();
        }
        if (jobState == STATE.STARTED
            || jobState == STATE.RESTARTED) {
            jobStartTime = System.currentTimeMillis();
        }
    }

    /**
     * �W���u�����X�^�[�g�\�ȃW���u�ł��邩�ǂ�����ݒ肷��B
     * 
     * @param restartable �W���u�����X�^�[�g�\�ȃW���u�ł���ꍇ�� 
     * <code>true</code>
     */
    public void setRestartable(boolean restartable) {
        this.restartable = restartable;
    }

    /**
     * ���X�^�[�g�|�C���g��ݒ肷��B
     * 
     * @param restartPoint ���X�^�[�g�|�C���g
     */
    public void setRestartPoint(int restartPoint) {
        this.restartPoint = restartPoint;
    }

    /**
     * ���f�I������ݒ肷��B
     * 
     */
    public void shutdownGraceful() {
        if (jobState.isEndStatus()
                || jobState == STATE.INTERRUPTED_FOR_GRACEFUL_SHUTDOWN
                || jobState == STATE.INTERRUPTED_FOR_IMMEDIATE_SHUTDOWN) {
            return;
        }
        
        setJobState(STATE.INTERRUPTED_FOR_GRACEFUL_SHUTDOWN);

        if (!childJobStatusList.isEmpty()) {
            for (JobStatus result : childJobStatusList) {
                result.shutdownGraceful();
            }
        }
    }

    /**
     * �����I������ݒ肷��B
     * 
     */
    public void shutdownImmediate() {
        if (jobState.isEndStatus()
                || jobState == STATE.INTERRUPTED_FOR_IMMEDIATE_SHUTDOWN) {
            return;
        }

        setJobState(STATE.INTERRUPTED_FOR_IMMEDIATE_SHUTDOWN);

        if (!childJobStatusList.isEmpty()) {
            for (JobStatus result : childJobStatusList) {
                result.shutdownImmediate();
            }
        }
    }

    /**
     * �W���uID���擾����B
     * 
     * @return �W���uID
     */
    public String getJobId() {
        return jobId;
    }

    /**
     * �W���uID��ݒ肷��B
     * 
     * @param jobId �W���uID
     */
    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    /**
     * �����L�[���擾����B
     * 
     * @return �����L�[
     */
    public String getPartitionKey() {
        return partitionKey;
    }

    /**
     * �����L�[��ݒ肷��B
     * 
     * @param partitionKey �����L�[
     */
    public void setPartitionKey(String partitionKey) {
        this.partitionKey = partitionKey;
    }

    /**
     * �����ԍ����擾����B
     * 
     * @return �����ԍ�
     */
    public int getPartitionNo() {
        return partitionNo;
    }

    /**
     * �����ԍ���ݒ肷��B
     * 
     * @param partitionNo �����ԍ�
     */
    public void setPartitionNo(int partitionNo) {
        this.partitionNo = partitionNo;
    }

    /**
     * �W���u���N�G�X�g�ԍ����擾����B
     * 
     * @return �W���u���N�G�X�g�ԍ�
     */
    public String getJobRequestNo() {
        return jobRequestNo;
    }

    /**
     * �W���u���N�G�X�g�ԍ���ݒ肷��B
     * 
     * @param jobRequestNo �W���u���N�G�X�g�ԍ�
     */
    public void setJobRequestNo(String jobRequestNo) {
        this.jobRequestNo = jobRequestNo;
    }
}
