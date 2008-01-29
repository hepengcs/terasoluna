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
 * 監視用ジョブ処理状況クラス。
 * 
 * <p>
 * JMXによる監視対象となるクラス。
 * </p>
 * 
 */
public class MonitorableTransactionalJobStatus extends TransactionalJobStatus
        implements MonitorableTransactionalJobStatusMBean {

    /**
     * 監視用MBean登録用インスタンス。
     */
    private MBeanRegister mbeanRegister = null;

    /**
     * 子ジョブ処理状況を取得する。
     * 
     * @param jobContext
     *            ジョブコンテキスト
     * @return 子ジョブ処理状況
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
     * 監視用MBean登録用インスタンスを設定する。
     * 
     * @param mbeanRegister 監視用MBean登録用インスタンス
     */
    public void setMbeanRegister(MBeanRegister mbeanRegister) {
        this.mbeanRegister = mbeanRegister;
    }
}
