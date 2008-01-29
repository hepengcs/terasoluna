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

package jp.terasoluna.fw.batch.commonj.init;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ConfigurableApplicationContext;

import jp.terasoluna.fw.batch.core.JobStatus;
import jp.terasoluna.fw.batch.core.JobStatusSummarizer;
import jp.terasoluna.fw.batch.core.WorkUnit;
import jp.terasoluna.fw.batch.core.Workable;
import jp.terasoluna.fw.batch.init.JobInfo;
import jp.terasoluna.fw.batch.openapi.JobContext;

/**
 * 分散処理における子ジョブ起動用メインクラス。<br>
 * 親ジョブからのメッセージで受け取った子ジョブ用のジョブコンテキストを<br>
 * 使用して子ジョブのジョブを実行する。
 * 
 * 
 */
public class ChildJobExecutor extends JobExecutor {

    /**
     * ログインスタンス。
     */
    private static Log log = LogFactory.getLog(ChildJobExecutor.class);

    /**
     * ジョブコンテキスト。
     */
    private JobContext jobContext = null;

    /**
     * ジョブを実行する。<br>
     * 指定されたジョブコンテキストの情報に基づいてジョブを実行する。
     * 
     * @param jobInfo ジョブ依頼情報
     * @param jobContext ジョブコンテキスト
     * @return ジョブ実行結果
     */
    public JobStatus execute(JobInfo jobInfo, JobContext jobContext) {
        // 開始ログ出力
        printStartLog(jobContext);
        
        // ジョブBeanFactory
        ConfigurableApplicationContext jobFactory = getJobBeanFactory(jobInfo);

        // マネジャー取得
        Workable<WorkUnit> jobManager = getJobManager(jobFactory);

        // ジョブ状態の初期化
        jobStatus = this.getJobStatus(jobFactory, jobContext);

        // 終了ファイル監視スレッドに登録
        initEndFileChecker(jobStatus, jobContext);

        WorkUnit rootWorkQueueElement = getWorkUnit(jobContext);

        jobManager.work(rootWorkQueueElement, jobStatus);

        ((JobStatusSummarizer) jobFactory.getBean(JOBSTATUS_SUMMARIZER))
                .summarize(jobStatus);

        setDefaultJobExitCode(jobStatus);

        if (!(Boolean) jobFactory.getBean(USECACHE_NAME)) {
            jobFactory.close();
        }

        // ジョブの終了ログ出力
        printEndLog(jobStatus);

        return jobStatus;
    }

    /**
     * ジョブの非同期並列実行。
     * 
     */
    @Override
    public void run() {
        jobStatus = execute(getJobInfo(), jobContext);
    }

    /**
     * ジョブコンテキストからジョブステータスを作成する。
     * 
     * @param jobFactory ジョブ毎の<code>BeanFactory</code>
     * @param jobContext ジョブコンテキスト
     * @return 初期ジョブステータス
     */
    protected JobStatus getJobStatus(ConfigurableApplicationContext jobFactory,
            JobContext jobContext) {
        
        JobStatus initJobStatus = null;

        if ((Boolean) jobFactory.getBean(USE_MONITORABLE)) {
            initJobStatus = (JobStatus) jobFactory
                    .getBean(MONITORABLE_JOBSTATUS_NAME);
        } else {
            initJobStatus = (JobStatus) jobFactory.getBean(JOBSTATUS_NAME);
        }

        initJobStatus.setJobRequestNo(jobContext.getJobRequestNo());
        initJobStatus.setJobId(jobContext.getJobId());
        initJobStatus.setJobState(JobStatus.STATE.STARTED);
        initJobStatus.setPartitionNo(jobContext.getPartitionNo());
        initJobStatus.setPartitionKey(jobContext.getPartitionKey());

        return initJobStatus;
    }

    /**
     * ジョブの開始ログ。
     * 
     * @param jobContext ジョブコンテキスト
     */
    protected void printStartLog(JobContext jobContext) {
        if (log.isDebugEnabled()) {
            StringBuilder logStr = new StringBuilder();
            logStr.append("【START】");
            logStr.append(" [jobId=");
            logStr.append(jobContext.getJobId());
            logStr.append("]");
            logStr.append(" [jobRequestNo=");
            logStr.append(jobContext.getJobRequestNo());
            logStr.append("]");
            logStr.append(" [partitionNo=");
            logStr.append(jobContext.getPartitionNo());
            logStr.append("]");
            logStr.append(" [StartType=");
            logStr.append(isAsync() ? JobContext.START_TYPE.ASYNC
                    : JobContext.START_TYPE.SYNC);
            logStr.append("]");
            log.debug(logStr.toString());
        }
    }

    /**
     * ジョブの終了ログ。
     * 
     * @param jobStatus ジョブステータス
     */
    protected void printEndLog(JobStatus jobStatus) {
        if (log.isDebugEnabled()) {
            StringBuilder logStr = new StringBuilder();
            logStr.append("【 END 】");
            logStr.append(" [jobId=");
            logStr.append(jobStatus.getJobId());
            logStr.append("]");
            logStr.append(" [jobRequestNo=");
            logStr.append(jobStatus.getJobRequestNo());
            logStr.append("]");
            logStr.append(" [partitionNo=");
            logStr.append(jobStatus.getPartitionNo());
            logStr.append("]");
            logStr.append(" [StartType=");
            logStr.append(isAsync() ? JobContext.START_TYPE.ASYNC
                    : JobContext.START_TYPE.SYNC);
            logStr.append("]");
            logStr.append(" [jobExitCode=");
            logStr.append(jobStatus.getJobExitCode());
            logStr.append("]");
            log.debug(logStr.toString());
        }
    }

    /**
     * ジョブステータスの取得。
     * 
     * @return jobStatus ジョブステータス
     */
    @Override
    public JobStatus getJobStatus() {
        return jobStatus;
    }

    /**
     * ジョブコンテキストの設定。
     * 
     * @param jobContext ジョブコンテキスト
     */
    public void setJobContext(JobContext jobContext) {
        this.jobContext = jobContext;
    }
}
