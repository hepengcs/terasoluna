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

package jp.terasoluna.fw.batch.partition;

import jp.terasoluna.fw.batch.core.WorkUnit;
import jp.terasoluna.fw.batch.openapi.JobContext;

/**
 * �����L�[�N���X�B
 * 
 * <p>�C�ӂ̌^�̕����L�[��<code>WorkQueue</code>�ɓ���邽�߂Ƀ��b�v����B</p>
 * 
 * <p>�����L�[�����Ԗڂ̃L�[�ł��邩��ێ�����B</p>
 * 
 */
public class PartitionRowObject implements WorkUnit {

    /**
     * �����L�[�ԍ��B�����L�[�����Ԗڂ̃L�[�ł��邩�������B
     */
    private int partitionNo;

    /**
     * �����L�[�B
     */
    private Object partitionKey;

    /**
     * �W���u�R���e�N�X�g�B
     */
    private JobContext jobContext;

    /**
     * �R���X�g���N�^�B
     * 
     * @param partitionNo �����L�[�ԍ�
     * @param partitionKey �����L�[
     * @param jobContext �W���u�R���e�N�X�g
     */
    public PartitionRowObject(int partitionNo, Object partitionKey,
            JobContext jobContext) {
        this.partitionNo = partitionNo;
        this.partitionKey = partitionKey;
        this.jobContext = jobContext;
    }

    /**
     * �����L�[���擾����B
     * 
     * @return �����L�[
     */
    public Object getRowObject() {
        return partitionKey;
    }

    /**
     * �����L�[�ԍ����擾����B
     * 
     * @return �����L�[�ԍ�
     */
    public int getPartitionNo() {
        return partitionNo;
    }

    /**
     * ���̃I�u�W�F�N�g���L���[�̍Ō�̃I�u�W�F�N�g�ł���ł��邩��Ԃ��B
     * 
     * @return ���̃N���X�ł͏�� <code>false</code> ��Ԃ��B
     */
    public boolean isEndMark() {
        return false;
    }

    /**
     * �W���u�R���e�N�X�g���擾����B
     * 
     * @return �W���u�R���e�N�X�g
     */
    public JobContext getJobContext() {
        return jobContext;
    }
    
    /**
     * �W���u�R���e�N�X�g��ݒ肷��B
     * 
     * @param jobContext �W���u�R���e�N�X�g
     */
    public void setJobContext(JobContext jobContext) {
        this.jobContext = jobContext;
    }
}
