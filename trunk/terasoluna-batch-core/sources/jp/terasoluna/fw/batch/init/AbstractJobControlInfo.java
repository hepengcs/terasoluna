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

import jp.terasoluna.fw.batch.core.WorkUnit;
import jp.terasoluna.fw.batch.openapi.JobContext;

/**
 * �W���u�Ǘ��e�[�u������擾�����W���u�˗����p<code>Abstract</code>�B
 * �W���u�Ǘ��e�[�u������擾�����W���u�˗����i�[�p�N���X�͂���
 * <code>Abstract</code>�N���X����������K�v������B
 *
 */
public abstract class AbstractJobControlInfo implements JobInfo, WorkUnit {

    /**
     * �W���u�f�[�����I���p�W���uID�̕�����B
     */
    public static final String STOP_DEMON = "STOP"; 
    
    /**
     * �W���u�p�����[�^�̋�؂蕶���B
     */
    private String jobParametersSplitStr = null;

    /**
     * �W���u�I���R�[�h��ݒ�B
     *
     * @param jobExitCode �W���u�I���R�[�h
     */
    public abstract void setJobExitCode(String jobExitCode);
    
    /**
     * �W���u�N���󋵂�ݒ�B
     *
     * @param jobState �W���u�N����
     */
    public abstract void setJobState(String jobState);
    
    /**
     * �L���[�̏I�[�ł��邩�]������B
     *
     * @return <code>false</code>��Ԃ�
     */
    public final boolean isEndMark() {
        return false;
    }
    
    /**
     * �W���u�p�����[�^�̋�؂蕶���B
     * 
     * @param jobParametersSplitStr �W���u�p�����[�^�̋�؂蕶��
     */
    public void setJobParametersSplitStr(String jobParametersSplitStr) {
        this.jobParametersSplitStr = jobParametersSplitStr;
    }

    /**
     * �W���u�R���e�L�X�g���擾����B
     *
     * <p>���̃N���X�̎����ł́A��� <code>UnsupportedOperationException</code>
     * ���X���[����B</p>
     * 
     * @return ��� <code>UnsupportedOperationException</code> ���X���[���邽��
     * �A���^�[�����Ȃ�
     */
    public final JobContext getJobContext() {
        throw new UnsupportedOperationException();
    }

    /**
     * �W���u�R���e�L�X�g��ݒ肷��B
     * 
     * <p>���̃N���X�̎����ł́A��� <code>UnsupportedOperationException</code>
     * ���X���[����B</p>
     *
     * @param jobContext �W���u�R���e�N�X�g
     */
    public final void setJobContext(JobContext jobContext) {
        throw new UnsupportedOperationException();
    }

    /**
     * �W���u�p�����[�^�̋�؂蕶�����擾����B
     * 
     * @return �W���u�p�����[�^�̋�؂蕶��
     */
    protected String getJobParametersSplitStr() {
        return jobParametersSplitStr;
    }

}
