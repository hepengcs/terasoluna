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

package jp.terasoluna.fw.batch.openapi;

import java.io.Serializable;

import jp.terasoluna.fw.batch.core.JobException;

/**
 * ��{�W���u�R���e�L�X�g�N���X�B
 *
 * <p>
 * �Ώۃf�[�^�擾�����A�r�W�l�X���W�b�N�A�O�����^�㏈���ŋ��L����A
 * �t���[�����[�N�ɂ���{�������ݒ肳���B<br> 
 * �ʂ̃W���u���`����ۂɁA�J���҂����̃W���u�̈��p�����̍��ڂɑΉ�����
 * �J�X�^���������g�����Ē�`���邱�Ƃ��ł���B
 * </p>
 * 
 * <p><strong>�g���W���u�R���e�L�X�g��Bean��`��</strong></p>
 * 
 * <p>�J���҂��g�������W���u�R���e�L�X�g���W���uBean��`�t�@�C���ɐݒ肷��B</p>
 * <code><pre>
 *     &lt;bean id=&quot;jobContext&quot;
 *              class=&quot;jp.terasoluna.fw.batch.sample.checksample.SampleJobContext&quot; /&gt;
 * </pre></code>
 * 
 * 
 */
public class JobContext implements Serializable {
    
    /**
     * Serializable�p�o�[�W����ID�B
     */
    private static final long serialVersionUID = 8920476023805712633L;

    /**
     * �W���u�N����ʁB
     */
    public static enum START_TYPE {
        /**
         * �񓯊��B
         */
        ASYNC,
        /**
         * �����B
         */
        SYNC
    }

    /**
     * �W���uID�B
     */
    protected String jobId = null;

    /**
     * �W���u���N�G�X�g�ԍ��B
     */
    protected String jobRequestNo = null;

    /**
     * �����W���u�̃p�[�e�B�V�����L�[�B
     */
    protected String partitionKey = "NO_Partition";
    
    /**
     * �����W���u�̃p�[�e�B�V�����ԍ��B
     */
    protected int partitionNo = -1;

    /**
     * ���X�^�[�g�\�t���O�B
     */
    protected boolean restartable = false;

    /**
     * ���X�^�[�g���s�t���O�B
     */
    protected boolean restarted = false;

    /**
     * ���X�^�[�g�|�C���g�B
     */
    protected int restartPoint = 0;

    /**
     * �W���u�N����ʁB
     */
    protected START_TYPE startType = null;
    
    /**
     * �N�����̈����B
     */
    protected String[] parameter = null;
    
    /**
     * �p�[�e�B�V�����L�[���ݒ肳���q�W���u�R���e�L�X�g��ԋp����B<br>
     * �����W���u���A�e�W���u����Ă΂��B<br>
     * �p�[�e�B�V�����ԍ��̓n���h������ݒ肳���B<br>
     *
     * @param partitionKey �p�[�e�B�V�����L�[
     * @return �q�W���u�̃W���u�R���e�L�X�g
     * @throws IllegalArgumentException �p�[�e�B�V�����L�[��NULL�܂��͋󔒕�����
     */
    public JobContext getChildJobContext(Object partitionKey) {
        if (partitionKey == null 
                || "".equals(partitionKey.toString().trim())) {
            throw new IllegalArgumentException(
                    "partitionKey is NULL or Whitespace.");
        }
        
        JobContext childJobContext = null;
        try {
            childJobContext = this.getClass().newInstance();
        } catch (InstantiationException e) {
            throw new JobException(e);
        } catch (IllegalAccessException e) {
            throw new JobException(e);
        }
        
        childJobContext.setJobId(this.jobId);
        childJobContext.setJobRequestNo(this.jobRequestNo);
        childJobContext.setRestartable(this.restartable);
        childJobContext.setRestarted(this.restarted);
        childJobContext.setStartType(this.startType);
        childJobContext.setPartitionKey(String.class.cast(partitionKey));
        childJobContext.setParameter(this.parameter);
        
        return childJobContext;
    }

    /**
     * �W���uID��ԋp����B
     *
     * @return �W���uID
     */
    public String getJobId() {
        return this.jobId;
    };

    /**
     * �W���u�˗��ԍ���ԋp����B
     *
     * @return �W���u�˗��ԍ�
     */
    public String getJobRequestNo() {
        return this.jobRequestNo;
    }

    /**
     * �p�[�e�B�V�����L�[���擾����B
     *
     * @return ������̃p�[�e�B�V�����L�[
     */
    public String getPartitionKey() {
        return this.partitionKey;
    }
    
    /**
     * �p�[�e�B�V�����ԍ���ԋp����B
     *
     * @return �p�[�e�B�V�����ԍ�
     */
    public int getPartitionNo() {
        return this.partitionNo;
    }

    /**
     * ���X�^�[�g�|�C���g��ԋp����B
     *
     * @return ���X�^�[�g�|�C���g
     */
    public int getRestartPoint() {
        return this.restartPoint;
    }

    /**
     * �N����ʂ�ԋp����B
     *
     * @return �N�����
     */
    public START_TYPE getStartType() {
        return this.startType;
    }

    /**
     * �N�����̈�����ԋp����B
     * 
     * @return �N�����̈���
     */
    public String[] getParameter() {
        return this.parameter;
    }
    
    /**
     * ���s���̃W���u�����X�^�[�g�\�ȃW���u�Ƃ��Đݒ肳��Ă��邩���擾����B
     *
     * @return ���X�^�[�g�\�ȃW���u�ł���� <code>true</code>
     */
    public boolean isRestartable() {
        return this.restartable;
    }

    /**
     * ���X�^�[�g���s�t���O���擾����B
     *
     * @return ���X�^�[�g���s�t���O�B
     *           �X�^�[�g�|�C���g����ĊJ���ꂽ�W���u�̎��s�ł���ꍇ�ɂ́A
     *           <code>true</code>
     */
    public boolean isRestarted() {
        return this.restarted;
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
     * �W���u�˗��ԍ���ݒ肷��B
     *
     * @param jobRequestNo �W���u�˗��ԍ�
     */
    public void setJobRequestNo(String jobRequestNo) {
        this.jobRequestNo = jobRequestNo;
    }

    /**
     * �N�����̈������W���u�R���e�L�X�g�ɐݒ肷��B
     *
     * @param arg �N�����Ɏw�肵����3�����ȍ~�̒l
     */
    public void setParameter(String[] arg) {
        this.parameter = arg;
    }

    /**
     * �p�[�e�B�V�����ԍ���ݒ肷��B
     *
     * @param partitionNo �p�[�e�B�V�����ԍ�
     */
    public void setPartitionNo(int partitionNo) {
        this.partitionNo = partitionNo;
    }

    /**
     * ���s���̃W���u�����X�^�[�g�\�ȃW���u���ǂ�����ݒ肷��B
     * 
     * <p>�t���[�����[�N�ɂ���ăW���u���������ɐݒ肳���</p>
     *
     * @param restartable ���X�^�[�g�\�ȃW���u�ł���� <code>true</code>
     */
    public void setRestartable(boolean restartable) {
        this.restartable = restartable;
    }

    /**
     * ���X�^�[�g���s�t���O��ݒ肷��B
     * 
     * <p>���X�^�[�g���s�t���O�́A�t���[�����[�N�ɂ���ăW���u���������ɐݒ肳��
     * ��</p>
     *
     * @param restarted
     *           �X�^�[�g�|�C���g����ĊJ���ꂽ�W���u�̎��s�ł���ꍇ�ɂ́A
     *           <code>true</code>
     */
    public void setRestarted(boolean restarted) {
        this.restarted = restarted;
    }

    /**
     * ���X�^�[�g�|�C���g��ݒ肷��B
     *
     * <p>���X�^�[�g�|�C���g�́A�t���[�����[�N�ɂ���ăW���u���������ɐݒ肳��
     * ��</p>
     * 
     * @param restartPoint ���X�^�[�g�|�C���g
     */
    public void setRestartPoint(int restartPoint) {
        this.restartPoint = restartPoint;
    }

    /**
     * �N����ʂ�ݒ肷��B
     *
     * <p>�N����ʂ́A�t���[�����[�N�ɂ���ăW���u���������ɐݒ肳���</p>
     * 
     * @param startType �N�����
     */
    public void setStartType(START_TYPE startType) {
        this.startType = startType;
    }
    
    /**
     * �p�[�e�B�V�����L�[��ݒ肷��B
     *
     * <p>�p�[�e�B�V�����L�[�́A�����W���u���A�t���[�����[�N�ɂ���Ďq�W���u��
     * �W���u�R���e�L�X�g�ɐݒ肳���</p>
     * 
     * @param partitionKey �p�[�e�B�V�����L�[
     */
    public void setPartitionKey(String partitionKey) {
        this.partitionKey = partitionKey;
    }
    
}
