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

import jp.terasoluna.fw.batch.init.JobInfo;
import jp.terasoluna.fw.batch.openapi.JobContext;

/**
 * �񓯊��^�W���u�N�������ɂ����āA�t���[�����[�N�œ����I�Ɏg�p�����W���u�R��
 * �e�N�X�g�B
 * 
 * <p>�W���u�˗�����ێ�����B</p>
 * 
 * <p>�񓯊��^�W���u�N���ł̃t���[�����[�N�����Ŏg�p�����񓯊��^�W���u�N���W��
 * �u�O�����A����є񓯊��^�W���u�N���W���u�㏈���ɂ����āA�N���Ώۂ̃W���u�̏�
 * ����擾���邽�߂ɗ��p�����B</p>
 * 
 */
public class AsyncJobContext extends JobContext {

    /**
     * Serializable�p�o�[�W����ID�B
     */
    private static final long serialVersionUID = 8917004959942137002L;

    /**
     * �W���u�˗����B
     */
    private JobInfo jobInfo = null;
    
    /**
     * �R���X�g���N�^�B
     */
    public AsyncJobContext() {
    }

    /**
     * �R���X�g���N�^�B
     * 
     * <p>�W���u�˗�����ݒ肷��B</p>
     * 
     * @param jobInfo �W���u�˗����
     */
    public AsyncJobContext(JobInfo jobInfo) {
        this.jobInfo = jobInfo;
    }
    
    /**
     * �W���u�˗������擾����B
     * 
     * @return �W���u�˗����
     */
    public JobInfo getJobInfo() {
        return jobInfo;
    }

    /**
     * �p�[�e�B�V�����L�[���擾����B
     * 
     * <p>���̃N���X�̎����ł́A���<code>null</code>��ԋp����B</p>
     *
     * @return String null
     */
    @Override
    public String getPartitionKey() {
        return null;
    }

    /**
     * �N�����̈������W���u�R���e�L�X�g�̐ݒ肷��B<BR>
     *
     *<p>���̃N���X�̎����ł͉������Ȃ��B</p>
     * @param arg �N�����Ɏw�肵����3�����ȍ~�̒l
     */
    @Override
    public void setParameter(String[] arg) {
    }
    
}