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

package jp.terasoluna.fw.batch.commonj.listener;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import jp.terasoluna.fw.batch.commonj.init.JobExecutor;
import jp.terasoluna.fw.batch.commonj.transaction.JobResultInfoHandler;
import jp.terasoluna.fw.batch.core.JobStatus;
import jp.terasoluna.fw.batch.jobmessage.JobMessageInfo;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import commonj.work.Work;
import commonj.work.WorkEvent;
import commonj.work.WorkItem;

/**
 * ワークマネージャ上で実行されるワーク（ジョブ）の監視を行うクラス。<br>
 * ワークの完了(Completed)、拒否(Rejected)時に実行結果ハンドラによる
 * ハンドリングを行う。
 * 
 * 
 */
public class JobExecutorListener implements WorkMapListener {

    /**
     * ログインスタンス。
     */
    private static Log log = LogFactory.getLog(JobExecutorListener.class);

    /**
     * 作業依頼を拒否されたときの終了コード。
     */
    private static final String REJECTED_JOB_EXIT_CODE = "-1";
    
    /**
     * スケジュールされたワークを保持するマップ。
     */
    private Map<WorkItem, Work> map = 
        Collections.synchronizedMap(new HashMap<WorkItem, Work>());

    /**
     * ジョブ実行結果ハンドラ。
     */
    private JobResultInfoHandler jobResultInfoHandler = null;

    /**
     * スケジュールされたワークの登録。
     * 
     * @param workItem ワークをワークマネジャーでスケジュールしたときの返却値
     * @param work スケジュールしたワーク
     */
    public void addWork(WorkItem workItem, Work work) {
        map.put(workItem, work);
        if (log.isDebugEnabled()) {
            log.debug("Map add work: " + workItem);
        }
    }

    /**
     * スケジュールされているワークの取得。
     * 
     * @param workItem ワークをワークマネジャーでスケジュールしたときの返却値
     * @return スケジュールされているワーク
     */
    public Object getWork(WorkItem workItem) {
        return map.get(workItem);
    }

    /**
     * 作業が完了したワークを削除する。
     * 
     * @param workItem ワークをワークマネジャーでスケジュールしたときの返却値
     * @return スケジュールされているワーク
     */
    public Object removeWork(WorkItem workItem) {
        return map.remove(workItem);
    }

    /**
     * ワークのスケジューリング受入。
     * 
     * @param we ワークイベント
     */
    public void workAccepted(WorkEvent we) {
        printSimpleLog("Work accepted: ", getWork(we.getWorkItem()));
    }

    /**
     * ワークの実行完了。
     * 
     * @param we ワークイベント
     */
    public void workCompleted(WorkEvent we) {
        JobExecutor jobExecutor = (JobExecutor) removeWork(we.getWorkItem());
        JobStatus jobStatus = jobExecutor.getJobStatus();

        JobMessageInfo jobMessageInfo = 
            createJobMessageInfo(jobStatus, jobExecutor);

        printLog("Work completed: ", jobMessageInfo, jobStatus);

        jobResultInfoHandler.handle(jobMessageInfo);
        
        jobExecutor.destroy();
    }

    /**
     * ワークのスケジューリング拒否。
     * 
     * @param we ワークイベント
     */
    public void workRejected(WorkEvent we) {
        JobExecutor jobExecutor = (JobExecutor) removeWork(we.getWorkItem());
        JobStatus jobStatus = null;
        JobMessageInfo jobMessageInfo = null;
        if (jobExecutor != null) {
            jobStatus = jobExecutor.getJobStatus();
            jobMessageInfo = createJobMessageInfo(jobStatus, jobExecutor);
            jobMessageInfo.setJobExitCode(REJECTED_JOB_EXIT_CODE);
            jobResultInfoHandler.handle(jobMessageInfo);
            printLog("Work rejected: ", jobMessageInfo, jobStatus);
            
            jobExecutor.destroy();
        }

    }

    /**
     * ワークの実行開始。
     * 
     * @param we ワークイベント
     */
    public void workStarted(WorkEvent we) {
        printSimpleLog("Work started: ", getWork(we.getWorkItem()));
    }

    /**
     * ジョブ実行結果ハンドラの設定。
     * 
     * @param jobResultInfoHandler ジョブ実行結果ハンドラ
     */
    public void setJobResultInfoHandler(
            JobResultInfoHandler jobResultInfoHandler) {
        this.jobResultInfoHandler = jobResultInfoHandler;
    }

    
    /**
     * ジョブメッセージ管理情報を作成する。
     *  
     * @param jobStatus ジョブステータス
     * @param jobExecutor 作業依頼されたジョブ起動クラス
     * @return ジョブメッセージ管理情報
     */
    private JobMessageInfo createJobMessageInfo(
            JobStatus jobStatus, JobExecutor jobExecutor) {
        JobMessageInfo jobMessageInfo = new JobMessageInfo();
        jobMessageInfo.setJobRequestNo(jobStatus.getJobRequestNo());
        jobMessageInfo.setJobId(jobStatus.getJobId());
        jobMessageInfo.setJobExitCode(String
                .valueOf(jobStatus.getJobExitCode()));
        jobMessageInfo.setJobState(String.valueOf(jobStatus.getJobState()
                .ordinal()));
        jobMessageInfo.setPartitionNo(jobStatus.getPartitionNo());
        jobMessageInfo.setPartitionKey(jobStatus.getPartitionKey());
        jobMessageInfo.setJobDiscriptorPath(jobExecutor.getJobInfo()
                .getJobDiscriptorPath());
        
        return jobMessageInfo;
    }
    
    /**
     * ログ出力。
     * 
     * @param message ログメッセージ
     * @param jobMessageInfo ジョブメッセージ管理情報
     * @param jobStatus ジョブメッセージ　
     */
    private void printLog(String message, JobMessageInfo jobMessageInfo, 
            JobStatus jobStatus) {
        if (log.isDebugEnabled()) {
            StringBuilder builder = new StringBuilder(message);
            builder.append("[");
            builder.append("JobRequestNo = ");
            builder.append(jobStatus.getJobRequestNo());
            builder.append("] [");
            builder.append("JobId = ");
            builder.append(jobStatus.getJobId());
            builder.append("] [");
            builder.append("PartitionNo = ");
            builder.append(jobMessageInfo.getPartitionNo());
            builder.append("] [");
            builder.append("ExitCode = ");
            builder.append(jobMessageInfo.getJobExitCode());
            builder.append("] [");
            builder.append("JobState = ");
            builder.append(jobMessageInfo.getJobState());
            builder.append("]");
            log.debug(builder.toString());
        }
    }
    
    /**
     * 簡単なログ出力。
     * 
     * @param message ログメッセージ
     * @param object 出力オブジェクト
     */
    private void printSimpleLog(String message, Object object) {
        if (log.isDebugEnabled()) {
            StringBuilder builder = new StringBuilder(message);
            builder.append("[");
            builder.append(object);
            builder.append("]");
            log.debug(builder.toString());
        }
    }
}
