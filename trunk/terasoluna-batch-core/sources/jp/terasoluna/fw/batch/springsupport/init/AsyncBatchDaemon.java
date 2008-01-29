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
 * 非同期バッチデーモンクラス。
 * AsyncBatchDaemonBean.xmlの設定内容に従い
 * 非同期バッチデーモンを起動する。
 * 
 * <ul>AsyncBatchDaemonBean.xmlの設定内容
 * <pre><code>
 * &lt;?xml version="1.0" encoding="UTF-8"?&gt;
 * &lt;!DOCTYPE beans PUBLIC "-//SPRING//DTD BEAN//EN"
 *     "http://www.springframework.org/dtd/spring-beans.dtd"&gt;
 * &lt;beans&gt;
 *     &lt;!-- ===================== マネージャ定義 ===================== --&gt;
 *     &lt;!-- 非同期バッチ起動用のマネージャ --&gt;
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
 *         &lt;!-- 親ジョブ前処理 --&gt;
 *         &lt;property name="preProcessor" ref="parentJobPreProcessor"/&gt;
 * 
 *         &lt;!-- 親ジョブ後処理 --&gt;
 *         &lt;property name="postProcessor" ref="parentJobPostProcessor"/&gt;
 *     &lt;/bean&gt;
 * 
 *     &lt;!-- 親ジョブ前処理 --&gt;
 *     &lt;bean id="parentJobPreProcessor" parent="transactionalSupportProcessor"&gt;
 *         &lt;property name="supportProcessor"&gt;
 *             &lt;bean parent="standardSupportProcessor"&gt;
 *                 &lt;property name="supportProcessorName" ref="parentJobPreProcessorName"/&gt;
 *             &lt;/bean&gt;
 *         &lt;/property&gt;
 *     &lt;/bean&gt;
 * 
 *     &lt;!-- 親ジョブ後処理 --&gt;
 *     &lt;bean id="parentJobPostProcessor" parent="transactionalSupportProcessor"&gt;
 *         &lt;property name="supportProcessor"&gt;
 *             &lt;bean parent="standardSupportProcessor"&gt;
 *                 &lt;property name="supportProcessorName" ref="parentJobPostProcessorName"/&gt;
 *             &lt;/bean&gt;
 *         &lt;/property&gt;
 *     &lt;/bean&gt;
 * 
 *     &lt;!-- ジョブ分割コンテクスト --&gt;
 *     &lt;bean name="jobContext" class="jp.terasoluna.fw.batch.openapi.JobContext" /&gt;
 * 
 *     &lt;!-- ===================== キュープロセッサ定義 ===================== --&gt;
 *     &lt;bean id="asyncBatchDaemonQueueProcessor" parent="baseQueueProcessor"&gt;
 *         &lt;property name="name" value="asyncBatchDaemonQueueProcessor" /&gt;
 *         &lt;property name="worker" ref="childJobManager" /&gt;
 *     &lt;/bean&gt;
 * 
 *     &lt;!-- バッチ起動用ワーカ --&gt;
 *     &lt;bean id="childJobManager" class="jp.terasoluna.fw.batch.springsupport.init.JobExecutor"&gt;
 *         &lt;constructor-arg&gt;
 *             &lt;value&gt;/common/FrameworkBean.xml,/common/dataAccessContext-batch.xml,/common/ThreadPoolContext-AsyncBatch.xml,/common/ValidationContext-AsyncBatch.xml&lt;/value&gt;
 *         &lt;/constructor-arg&gt;
 *         &lt;property name="async" value="true" /&gt;
 *         &lt;!-- 親ジョブ前処理 --&gt;
 *         &lt;property name="preJobProcessor" ref="preJobProcessor"/&gt;
 *         &lt;!-- 親ジョブ後処理 --&gt;
 *         &lt;property name="postJobProcessor" ref="postJobProcessor"/&gt;
 *         &lt;!-- ジョブパラメータの区切り文字 --&gt;
 *         &lt;property name="jobParametersSplitStr" value="\\,"/&gt;
 *     &lt;/bean&gt;
 * 
 *     &lt;!-- ジョブ前処理 --&gt;
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
 *     &lt;!-- ジョブ後処理 --&gt;
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
 *     &lt;!-- コレクタ --&gt;
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
 *     &lt;!-- JobStatus設定 --&gt;
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
 *     &lt;!-- ジョブ監視を行うか？ --&gt;
 *     &lt;bean id="useMonitorable" class="org.springframework.beans.factory.config.FieldRetrievingFactoryBean"&gt;
 *           &lt;property name="staticField" value="java.lang.Boolean.FALSE"/&gt;
 *     &lt;/bean&gt;
 * 
 *     &lt;!-- ===================== ジョブ管理テーブル用ハンドラ ===================== --&gt;
 *     &lt;bean id="JobControlTableHandler" class="jp.terasoluna.fw.batch.init.JobControlTableHandlerImpl"&gt;
 *         &lt;property name="queryDAO" ref="queryDAO" /&gt;
 *         &lt;property name="updateDAO" ref="updateDAO" /&gt;
 *     &lt;/bean&gt;
 * 
 *     &lt;!-- ===================== スレッドプール作成時のサイズ ===================== --&gt;
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
     * ログクラス。
     */
    private static Log log = LogFactory.getLog(AsyncBatchDaemon.class);

    /**
     * フレームワークBean定義ファイル名。
     */
    private static final String ASYNC_BATCH_DAEMON_BEAN_DEFINITION_NAME = 
        "common/AsyncBatchDaemonBean.xml";

    /**
     * 非同期バッチデーモン起動用引数。
     */
    private static final String[] ASYNCBATCHDAEMON_START_PARAMETER = 
        {"BATCH_DAEMON", ASYNC_BATCH_DAEMON_BEAN_DEFINITION_NAME};

    /**
     * 非同期ジョブ起動用Mainメソッド。
     *
     * @param args 起動時の引数（使用されない）
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
