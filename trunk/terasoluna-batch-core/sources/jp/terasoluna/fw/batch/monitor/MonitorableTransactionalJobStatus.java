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

import jp.terasoluna.fw.batch.core.JobStatus;
import jp.terasoluna.fw.batch.openapi.JobContext;
import jp.terasoluna.fw.batch.springsupport.transaction.TransactionalJobStatus;

/**
 * �Ď��p�W���u�����󋵃N���X�B
 * 
 * <p>
 * JMX�ɂ��Ď��ΏۂƂȂ�N���X�B
 * </p>
 * 
 */
public class MonitorableTransactionalJobStatus extends TransactionalJobStatus
        implements MonitorableTransactionalJobStatusMBean {

    /**
     * �Ď��pMBean�o�^�p�C���X�^���X�B
     */
    private MBeanRegister mbeanRegister = null;

    /**
     * �q�W���u�����󋵂��擾����B
     * 
     * @param jobContext
     *            �W���u�R���e�L�X�g
     * @return �q�W���u������
     */
    @Override
    public JobStatus getChild(JobContext jobContext) {
        MonitorableTransactionalJobStatus childJobStatus = 
            new MonitorableTransactionalJobStatus();
        childJobStatus.setMbeanRegister(this.mbeanRegister);
        childJobStatus.setTransactionManager(this.getTransactionManager());
        childJobStatus.setUseSavepoint(this.useSavepoint());
        resetChildData(childJobStatus, jobContext);
        mbeanRegister.registerMBean(childJobStatus);

        return childJobStatus;
    }

    /**
     * �Ď��pMBean�o�^�p�C���X�^���X��ݒ肷��B
     * 
     * @param mbeanRegister �Ď��pMBean�o�^�p�C���X�^���X
     */
    public void setMbeanRegister(MBeanRegister mbeanRegister) {
        this.mbeanRegister = mbeanRegister;
    }
}
