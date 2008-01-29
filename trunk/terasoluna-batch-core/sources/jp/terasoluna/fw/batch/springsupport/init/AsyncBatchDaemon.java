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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * �񓯊��o�b�`�f�[�����N���X�B
 * AsyncBatchDaemonBean.xml�̐ݒ���e�ɏ]��
 * �񓯊��o�b�`�f�[�������N������B
 * 
 * <ul>AsyncBatchDaemonBean.xml�̐ݒ���e
 * <pre><code>
 * &lt;?xml version="1.0" encoding="UTF-8"?&gt;
 * &lt;!DOCTYPE beans PUBLIC "-//SPRING//DTD BEAN//EN"
 *     "http://www.springframework.org/dtd/spring-beans.dtd"&gt;
 * &lt;beans&gt;
 *     &lt;!-- ===================== �}�l�[�W����` ===================== --&gt;
 *     &lt;!-- �񓯊��o�b�`�N���p�̃}�l�[�W�� --&gt;
 *     &lt;bean id="jobManager" parent="baseManager"&gt;
 *         &lt;property name="name" value="AsyncBatchDaemonManager" /&gt;
 *         &lt;property name="collector" ref="batchDaemonJobCollector" /&gt;
 *         &lt;property name="workQueueFactory"&gt;
 *             &lt;bean class="jp.terasoluna.fw.batch.standard.StandardWorkQueueFactory"&gt;
 *                 &lt;property name="workerExecutorService" ref="workerExecutorService"/&gt;
 *                 &lt;property name="queueProcessor" ref="asyncBatchDaemonQueueProcessor" /&gt;
 *                 &lt;property name="queueLength" value="10"/&gt;
 *                 &lt;property name="multiplicity" ref="multiplicity"/&gt;
 *             &lt;/bean&gt;
 *         &lt;/property&gt;
 *         &lt;!-- �e�W���u�O���� --&gt;
 *         &lt;property name="preProcessor" ref="parentJobPreProcessor"/&gt;
 * 
 *         &lt;!-- �e�W���u�㏈�� --&gt;
 *         &lt;property name="postProcessor" ref="parentJobPostProcessor"/&gt;
 *     &lt;/bean&gt;
 * 
 *     &lt;!-- �e�W���u�O���� --&gt;
 *     &lt;bean id="parentJobPreProcessor" parent="transactionalSupportProcessor"&gt;
 *         &lt;property name="supportProcessor"&gt;
 *             &lt;bean parent="standardSupportProcessor"&gt;
 *                 &lt;property name="supportProcessorName" ref="parentJobPreProcessorName"/&gt;
 *             &lt;/bean&gt;
 *         &lt;/property&gt;
 *     &lt;/bean&gt;
 * 
 *     &lt;!-- �e�W���u�㏈�� --&gt;
 *     &lt;bean id="parentJobPostProcessor" parent="transactionalSupportProcessor"&gt;
 *         &lt;property name="supportProcessor"&gt;
 *             &lt;bean parent="standardSupportProcessor"&gt;
 *                 &lt;property name="supportProcessorName" ref="parentJobPostProcessorName"/&gt;
 *             &lt;/bean&gt;
 *         &lt;/property&gt;
 *     &lt;/bean&gt;
 * 
 *     &lt;!-- �W���u�����R���e�N�X�g --&gt;
 *     &lt;bean name="jobContext" class="jp.terasoluna.fw.batch.openapi.JobContext" /&gt;
 * 
 *     &lt;!-- ===================== �L���[�v���Z�b�T��` ===================== --&gt;
 *     &lt;bean id="asyncBatchDaemonQueueProcessor" parent="baseQueueProcessor"&gt;
 *         &lt;property name="name" value="asyncBatchDaemonQueueProcessor" /&gt;
 *         &lt;property name="worker" ref="childJobManager" /&gt;
 *     &lt;/bean&gt;
 * 
 *     &lt;!-- �o�b�`�N���p���[�J --&gt;
 *     &lt;bean id="childJobManager" class="jp.terasoluna.fw.batch.springsupport.init.JobExecutor"&gt;
 *         &lt;constructor-arg&gt;
 *             &lt;value&gt;/common/FrameworkBean.xml,/common/dataAccessContext-batch.xml,/common/ThreadPoolContext-AsyncBatch.xml,/common/ValidationContext-AsyncBatch.xml&lt;/value&gt;
 *         &lt;/constructor-arg&gt;
 *         &lt;property name="async" value="true" /&gt;
 *         &lt;!-- �e�W���u�O���� --&gt;
 *         &lt;property name="preJobProcessor" ref="preJobProcessor"/&gt;
 *         &lt;!-- �e�W���u�㏈�� --&gt;
 *         &lt;property name="postJobProcessor" ref="postJobProcessor"/&gt;
 *         &lt;!-- �W���u�p�����[�^�̋�؂蕶�� --&gt;
 *         &lt;property name="jobParametersSplitStr" value="\\,"/&gt;
 *     &lt;/bean&gt;
 * 
 *     &lt;!-- �W���u�O���� --&gt;
 *     &lt;bean id="preJobProcessor" parent="transactionalSupportProcessor"&gt;
 *         &lt;property name="supportProcessor"&gt;
 *             &lt;bean parent="standardSupportProcessor"&gt;
 *                 &lt;property name="supportProcessorName" ref="jobPreProcessorName"/&gt;
 *                 &lt;property name="supportLogicList"&gt;
 *                     &lt;list&gt;
 *                         &lt;bean class="jp.terasoluna.fw.batch.springsupport.init.AsyncJobPreLogic"&gt;
 *                             &lt;property name="jobControlTableHandler" ref="JobControlTableHandler" /&gt;
 *                         &lt;/bean&gt;
 *                     &lt;/list&gt;
 *                 &lt;/property&gt;    
 *             &lt;/bean&gt;
 *         &lt;/property&gt;
 *     &lt;/bean&gt;
 * 
 *     &lt;!-- �W���u�㏈�� --&gt;
 *     &lt;bean id="postJobProcessor" parent="transactionalSupportProcessor"&gt;
 *         &lt;property name="supportProcessor"&gt;
 *             &lt;bean parent="standardSupportProcessor"&gt;
 *                 &lt;property name="supportProcessorName" ref="jobPostProcessorName"/&gt;
 *                 &lt;property name="supportLogicList"&gt;
 *                     &lt;list&gt;
 *                         &lt;bean class="jp.terasoluna.fw.batch.springsupport.init.AsyncJobPostLogic"&gt;
 *                             &lt;property name="jobControlTableHandler" ref="JobControlTableHandler" /&gt;
 *                         &lt;/bean&gt;
 *                     &lt;/list&gt;
 *                 &lt;/property&gt;    
 *             &lt;/bean&gt;
 *         &lt;/property&gt;
 *     &lt;/bean&gt;
 * 
 *     &lt;!-- �R���N�^ --&gt;
 *     &lt;bean id="batchDaemonJobCollector" class="jp.terasoluna.fw.batch.init.JobRequestInfoCollector"&gt;
 *         &lt;property name="collectedDataHandlerFactory"&gt;
 *             &lt;bean class="jp.terasoluna.fw.batch.init.JobRequestInfoHandlerFactory"/&gt;
 *         &lt;/property&gt;
 *         &lt;property name="jobControlTableHandler" ref="JobControlTableHandler" /&gt;
 *         &lt;property name="intervalSeconds" value="10" /&gt;
 *         &lt;property name="refreshCount" value="20" /&gt;
 *     &lt;/bean&gt;
 *     
 * 
 *     &lt;!-- JobStatus�ݒ� --&gt;
 *     &lt;bean id="MonitorableJobStatus" class="jp.terasoluna.fw.batch.monitor.MonitorableTransactionalJobStatus"&gt;
 *         &lt;property name="mbeanRegister" ref="MBeanRegister"/&gt;
 *         &lt;property name="transactionManager" ref="transactionManager" /&gt;
 *         &lt;property name="useSavepoint" ref="useSavepoint" /&gt;
 *         &lt;property name="partitionNo" value="-1"/&gt;
 *     &lt;/bean&gt;
 * 
 *     &lt;bean id="JobStatus" class="jp.terasoluna.fw.batch.springsupport.transaction.TransactionalJobStatus"&gt;
 *         &lt;property name="transactionManager" ref="transactionManager" /&gt;
 *         &lt;property name="useSavepoint" ref="useSavepoint" /&gt;
 *         &lt;property name="partitionNo" value="-1"/&gt;
 *     &lt;/bean&gt;
 * 
 *     &lt;!-- �W���u�Ď����s�����H --&gt;
 *     &lt;bean id="useMonitorable" class="org.springframework.beans.factory.config.FieldRetrievingFactoryBean"&gt;
 *           &lt;property name="staticField" value="java.lang.Boolean.FALSE"/&gt;
 *     &lt;/bean&gt;
 * 
 *     &lt;!-- ===================== �W���u�Ǘ��e�[�u���p�n���h�� ===================== --&gt;
 *     &lt;bean id="JobControlTableHandler" class="jp.terasoluna.fw.batch.init.JobControlTableHandlerImpl"&gt;
 *         &lt;property name="queryDAO" ref="queryDAO" /&gt;
 *         &lt;property name="updateDAO" ref="updateDAO" /&gt;
 *     &lt;/bean&gt;
 * 
 *     &lt;!-- ===================== �X���b�h�v�[���쐬���̃T�C�Y ===================== --&gt;
 *     &lt;bean id="threadSize" class="jp.terasoluna.fw.batch.springsupport.standard.ThreadSizeFactoryBean"&gt;
 *         &lt;property name="multiplicity" ref="multiplicity"/&gt;
 *     &lt;/bean&gt;
 *     &lt;bean id="multiplicity" class="java.lang.Integer"&gt;
 *         &lt;constructor-arg&gt;
 *             &lt;value&gt;3&lt;/value&gt;
 *         &lt;/constructor-arg&gt;
 *     &lt;/bean&gt;
 *     
 * &lt;/beans&gt;
 *</code></pre>
 * </ul>
 *
 */
public class AsyncBatchDaemon {
    /**
     * ���O�N���X�B
     */
    private static Log log = LogFactory.getLog(AsyncBatchDaemon.class);

    /**
     * �t���[�����[�NBean��`�t�@�C�����B
     */
    private static final String ASYNC_BATCH_DAEMON_BEAN_DEFINITION_NAME = 
        "common/AsyncBatchDaemonBean.xml";

    /**
     * �񓯊��o�b�`�f�[�����N���p�����B
     */
    private static final String[] ASYNCBATCHDAEMON_START_PARAMETER = 
        {"BATCH_DAEMON", ASYNC_BATCH_DAEMON_BEAN_DEFINITION_NAME};

    /**
     * �񓯊��W���u�N���pMain���\�b�h�B
     *
     * @param args �N�����̈����i�g�p����Ȃ��j
     */
    public static void main(String[] args) {
        if (log.isDebugEnabled()) {
            log.debug("start batch daemon");
        }
        JobStarter jobStarter = new JobStarter();
        int exitCode = jobStarter.execute(ASYNCBATCHDAEMON_START_PARAMETER);

        if (log.isDebugEnabled()) {
            log.debug("end batch daemon");
        }
        System.exit(exitCode);
    }
    
}
