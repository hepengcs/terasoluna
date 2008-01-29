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

package jp.terasoluna.fw.batch.core;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.terasoluna.fw.batch.openapi.JobContext;
import jp.terasoluna.fw.batch.openapi.ReturnCode;

/**
 * ジョブの処理状況を保持するクラス。
 * 
 */
public class JobStatus {

    /**
     * ジョブ状態の列挙クラス。
     *
     * <p><code>JobState</code> では、以下の起動状況を提供する</p>
     *
     * <div align="center">
     *  <table width="50%" border="1">
     *   <tr>
     *    <td> <b>起動状況</b> </td>
     *    <td> <b>起動状況番号</b> </td>
     *    <td> <b>概要</b> </td>
     *   </tr>
     *   <tr>
     *    <td> <code>SUBMITTED</code> </td>
     *    <td> <code>0</code> </td>
     *    <td align="left">
     *      起動前
     *    </td>
     *   </tr>
     *   <tr>
     *    <td> <code>STARTED</code> </td>
     *    <td> <code>1</code> </td>
     *    <td align="left">
     *      起動中
     *    </td>
     *   </tr>
     *   <tr>
     *    <td> <code>RESTARTED</code> </td>
     *    <td> <code>2</code> </td>
     *    <td align="left">
     *      再起動中
     *    </td>
     *   </tr>
     *   <tr>
     *    <td> <code>ENDING_NORMALLY</code> </td>
     *    <td> <code>3</code> </td>
     *    <td align="left">
     *      正常終了
     *    </td>
     *   </tr>
     *   <tr>
     *    <td> <code>ENDING_ABNORMALLY</code> </td>
     *    <td> <code>4</code> </td>
     *    <td align="left">
     *      異常終了
     *    </td>
     *   </tr>
     *   <tr>
     *    <td> <code>INTERRUPTED_FOR_GRACEFUL_SHUTDOWN</code> </td>
     *    <td> <code>5</code> </td>
     *    <td align="left">
     *      中断終了中
     *    </td>
     *   </tr>
     *   <tr>
     *    <td> <code>INTERRUPTED_FOR_IMMEDIATE_SHUTDOWN</code> </td>
     *    <td> <code>6</code> </td>
     *    <td align="left">
     *      強制終了中
     *    </td>
     *   </tr>
     *   <tr>
     *    <td> <code>SUSPENDING</code> </td>
     *    <td> <code>7</code> </td>
     *    <td align="left">
     *      中断/強制終了
     *    </td>
     *   </tr>
     *  </table>
     * </div>
     * <p>
     */
    public enum STATE {

        /**
         * 起動前。
         */
        SUBMITTED(false),
        /**
         * 起動中。
         */
        STARTED(false),
        /**
         * 再起動中。
         */
        RESTARTED(false),
        /**
         * 正常終了。
         */
        ENDING_NORMALLY(true),
        /**
         * 異常終了。
         */
        ENDING_ABNORMALLY(true),
        /**
         * 中断終了中。
         */
        INTERRUPTED_FOR_GRACEFUL_SHUTDOWN(false),
        /**
         * 強制終了中。
         */
        INTERRUPTED_FOR_IMMEDIATE_SHUTDOWN(false),
        /**
         * 中断/強制終了
         */
        SUSPENDING(true);

        /**
         * ジョブ状態が終了状態であるかどうかを示すフラグ。
         */
        private final boolean isEndStatus;

        /**
         * コンストラクタ。
         * 
         * @param isEndStatus 終了状態である場合には、<code>true</code>
         */
        private STATE(boolean isEndStatus) {
            this.isEndStatus = isEndStatus;
        }

        /**
         * 終了状況を判断する。
         *
         * @return 終了状況
         */
        public boolean isEndStatus() {
            return isEndStatus;
        }
    }

    /**
     * バッチ更新件数。
     */
    private int batchUpdateCount = 0;

    /**
     * 対象データ取得件数。
     */
    private int collected = 0;

    /**
     * ジョブのコミット件数。
     */
    private int commitCount = 0;

    /**
     * 処理終了時刻。
     */
    private long jobEndTime = 0;

    /**
     * ジョブ終了コード。
     */
    private Integer jobExitCode = null;

    /**
     * 処理開始時刻。
     */
    private long jobStartTime = 0;

    /**
     * 処理状況。
     */
    private STATE jobState = STATE.SUBMITTED;

    /**
     * ジョブの処理状況のリスト。
     */
    private List<JobStatus> childJobStatusList;

    /**
     * リスタート処理有無。
     */
    private boolean restartable = false;

    /**
     * 既存リスタート情報。
     */
    private int restartPoint = 0;

    /**
     * BLogicの処理件数。
     */
    private ResultCounter resultCounter;

    /**
     * ジョブリクエスト番号。
     */
    private String jobRequestNo = null;

    /**
     * ジョブID。
     */
    private String jobId = null;

    /**
     * ジョブパーティション番号。
     */
    private int partitionNo = 0;

    /**
     * ジョブ分割キー。
     */
    private String partitionKey = null;

    /**
     * コンストラクタ。 ジョブの処理状況を初期化する。
     */
    public JobStatus() {
        childJobStatusList = new ArrayList<JobStatus>();
        resultCounter = new ResultCounter();
    }

    /**
     * 「中断/強制終了フラグ」が <code>true</code> の場合、処理状況を
     * 「中断/強制終了」に設定する。
     * 
     */
    public void suspend() {
        if (jobState == STATE.INTERRUPTED_FOR_IMMEDIATE_SHUTDOWN
                || jobState == STATE.INTERRUPTED_FOR_GRACEFUL_SHUTDOWN) {
            setJobState(STATE.SUSPENDING);
        }
    }
    /**
     * 子ジョブの処理結果をリストに追加する。
     * 
     * @param jobStatus 子ジョブの処理状況
     */
    protected void addChildJobStatus(JobStatus jobStatus) {
        childJobStatusList.add(jobStatus);
    }

    /**
     * ビジネスロジックの実行をカウントする。
     * 
     * @param returnCode ビジネスロジックのリターンコード
     */
    public void countBLogic(ReturnCode returnCode) {
        resultCounter.count(returnCode);
    }

    /**
     * 子ジョブ処理状況を取得する。
     * 
     * @param jobContext ジョブコンテキスト
     * @return 子ジョブ処理状況
     */
    public JobStatus getChild(JobContext jobContext) {
        JobStatus childJobStatus = new JobStatus();
        resetChildData(childJobStatus, jobContext);
        return childJobStatus;    
    }

    /**
     * 子ジョブ処理状況に親ジョブ処理状況を設定する。
     * 
     * @param childJobStatus 子ジョブ状況
     * @param jobContext ジョブコンテキスト
     */
    protected void resetChildData(JobStatus childJobStatus ,
            JobContext jobContext) {
        childJobStatus.setJobId(this.getJobId());
        childJobStatus.setJobRequestNo(this.getJobRequestNo());
        childJobStatus.setRestartPoint(this.getRestartPoint());
        childJobStatus.setRestartable(this.isRestartable());
        childJobStatus.setJobState(this.getJobState());
        childJobStatus.setPartitionNo(jobContext.getPartitionNo());
        childJobStatus.setPartitionKey(jobContext.getPartitionKey());

        if (isShutdownImmediate() || isShutdownGraceful()) {
            childJobStatus.suspend();
        }
        addChildJobStatus(childJobStatus);
    }
    
    /**
     * Collectorの処理件数を返却する。
     * 
     * @return Collectorの処理件数
     */
    public int getCollected() {
        return collected;
    }

    /**
     * コミット数を取得する。
     * 
     * @return コミット数
     */
    public int getCommitCount() {

        int count = 0;

        if (!childJobStatusList.isEmpty()) {
            for (JobStatus result : childJobStatusList) {
                count += result.getCommitCount();
            }
        } else {
            count = commitCount;
        }

        return count;
    }

    /**
     * 処理継続数を取得する。
     * 
     * @return 処理継続数
     */
    public int getErrorContinueCount() {

        int count = 0;

        if (!childJobStatusList.isEmpty()) {
            for (JobStatus result : childJobStatusList) {
                count += result.getErrorContinueCount();
            }
        } else {
            count = resultCounter.getErrorContinueCount();
        }

        return count;
    }

    /**
     * ジョブ終了コードを取得する。
     * 
     * @return ジョブ終了コード
     */
    public Integer getJobExitCode() {
        return jobExitCode;
    }

    /**
     * バッチ更新件数を取得する。
     * 
     * @return バッチ更新件数
     */
    public int getBatchUpdateCount() {
        return batchUpdateCount;
    }

    /**
     * ジョブ開始時刻を取得する。
     * 
     * @return ジョブ開始時刻
     */
    public String getJobStartTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        return sdf.format(new Date(jobStartTime));
    }

    /**
     * ジョブの状態を取得する。
     * 
     * @return 処理状態
     */
    public STATE getJobState() {
        return jobState;
    }

    /**
     * ジョブ状態の文字例を取得する。
     * 
     * @return 処理状態の文字例
     */
    public String getJobStateStr() {

        if (jobState != null) {
            return jobState.toString();
        }

        return null;
    }

    /**
     * 子ジョブの処理結果を保持したリストを取得する。
     * 
     * @return 子ジョブの処理結果リスト
     */
    public List<JobStatus> getChildJobStatusList() {
        return childJobStatusList;
    }

    /**
     * 処理継続数を取得する。
     * 
     * @return 処理継続数
     */
    public int getNormalContinueCount() {

        int count = 0;

        if (!childJobStatusList.isEmpty()) {
            for (JobStatus result : childJobStatusList) {
                count += result.getNormalContinueCount();
            }
        } else {
            count = resultCounter.getNormalContinueCount();
        }

        return count;
    }

    /**
     * ジョブ処理時間を取得する。(ms単位)
     * 
     * @return ジョブ処理時間の文字列(xxxxxms)
     */
    public String getProcessingTime() {
        long now = System.currentTimeMillis();
        if (jobState.isEndStatus()) {
            now = jobEndTime;
        }
        return String.valueOf(now - jobStartTime) + "ms";
    }

    /**
     * リスタートポイントを取得する。
     * 
     * @return リスタートポイント
     */
    public int getRestartPoint() {
        return restartPoint + resultCounter.getToralCount();
    }

    /**
     * バッチ処理更新数をカウントする。
     * 
     * @param batchUpdateCount バッチ処理更新数
     */
    public void incrementBatchUpdateCount(int batchUpdateCount) {
        this.batchUpdateCount += batchUpdateCount;
    }

    /**
     * Collecterの処理件数をインクリメントする。
     * 
     */
    public void incrementCollected() {
        collected++;
    }

    /**
     * コミット数をインクリメントする。
     * 
     */
    public void incrementCommitCount() {
        commitCount++;
    }

    /**
     * ジョブが継続状況であるかどうかを取得する。
     * 
     * @return ジョブが継続状況である場合に <code>true</code>
     */
    public boolean isContinue() {
        return !jobState.isEndStatus();
    }

    /**
     * ジョブが起動/再起動状況であるかどうかを取得する。
     * 
     * @return ジョブが起動/再起動状況である場合に <code>true</code>
     */
    public boolean isExecuting() {
        return jobState == STATE.STARTED || jobState == STATE.RESTARTED;
    }

    /**
     * ジョブがリスタート可能なジョブであるかどうかを取得する。
     * 
     * @return ジョブがリスタート可能なジョブである場合に <code>true</code>
     */
    public boolean isRestartable() {
        return restartable;
    }

    /**
     * 中断終了が設定されているかどうかを取得する。
     * 
     * @return 中断終了が設定されている場合に <code>true</code>
     */
    public boolean isShutdownGraceful() {
        return jobState == STATE.INTERRUPTED_FOR_GRACEFUL_SHUTDOWN;
    }

    /**
     * 強制終了が設定されているかどうかを取得する。
     * 
     * @return 強制終了が設定されている場合に <code>true</code>
     */
    public boolean isShutdownImmediate() {
        return jobState == STATE.INTERRUPTED_FOR_IMMEDIATE_SHUTDOWN;
    }

    /**
     * 終了コードを設定する。
     * 
     * @param jobExitCode 終了コード
     */
    public void setJobExitCode(Integer jobExitCode) {
        this.jobExitCode = jobExitCode;
    }

    /**
     * ジョブの状態を設定する。
     * 
     * @param jobState 処理状態
     */
    public void setJobState(STATE jobState) {
        this.jobState = jobState;
        if (jobState.isEndStatus()) {
            jobEndTime = System.currentTimeMillis();
        }
        if (jobState == STATE.STARTED
            || jobState == STATE.RESTARTED) {
            jobStartTime = System.currentTimeMillis();
        }
    }

    /**
     * ジョブがリスタート可能なジョブであるかどうかを設定する。
     * 
     * @param restartable ジョブがリスタート可能なジョブである場合に 
     * <code>true</code>
     */
    public void setRestartable(boolean restartable) {
        this.restartable = restartable;
    }

    /**
     * リスタートポイントを設定する。
     * 
     * @param restartPoint リスタートポイント
     */
    public void setRestartPoint(int restartPoint) {
        this.restartPoint = restartPoint;
    }

    /**
     * 中断終了中を設定する。
     * 
     */
    public void shutdownGraceful() {
        if (jobState.isEndStatus()
                || jobState == STATE.INTERRUPTED_FOR_GRACEFUL_SHUTDOWN
                || jobState == STATE.INTERRUPTED_FOR_IMMEDIATE_SHUTDOWN) {
            return;
        }
        
        setJobState(STATE.INTERRUPTED_FOR_GRACEFUL_SHUTDOWN);

        if (!childJobStatusList.isEmpty()) {
            for (JobStatus result : childJobStatusList) {
                result.shutdownGraceful();
            }
        }
    }

    /**
     * 強制終了中を設定する。
     * 
     */
    public void shutdownImmediate() {
        if (jobState.isEndStatus()
                || jobState == STATE.INTERRUPTED_FOR_IMMEDIATE_SHUTDOWN) {
            return;
        }

        setJobState(STATE.INTERRUPTED_FOR_IMMEDIATE_SHUTDOWN);

        if (!childJobStatusList.isEmpty()) {
            for (JobStatus result : childJobStatusList) {
                result.shutdownImmediate();
            }
        }
    }

    /**
     * ジョブIDを取得する。
     * 
     * @return ジョブID
     */
    public String getJobId() {
        return jobId;
    }

    /**
     * ジョブIDを設定する。
     * 
     * @param jobId ジョブID
     */
    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    /**
     * 分割キーを取得する。
     * 
     * @return 分割キー
     */
    public String getPartitionKey() {
        return partitionKey;
    }

    /**
     * 分割キーを設定する。
     * 
     * @param partitionKey 分割キー
     */
    public void setPartitionKey(String partitionKey) {
        this.partitionKey = partitionKey;
    }

    /**
     * 分割番号を取得する。
     * 
     * @return 分割番号
     */
    public int getPartitionNo() {
        return partitionNo;
    }

    /**
     * 分割番号を設定する。
     * 
     * @param partitionNo 分割番号
     */
    public void setPartitionNo(int partitionNo) {
        this.partitionNo = partitionNo;
    }

    /**
     * ジョブリクエスト番号を取得する。
     * 
     * @return ジョブリクエスト番号
     */
    public String getJobRequestNo() {
        return jobRequestNo;
    }

    /**
     * ジョブリクエスト番号を設定する。
     * 
     * @param jobRequestNo ジョブリクエスト番号
     */
    public void setJobRequestNo(String jobRequestNo) {
        this.jobRequestNo = jobRequestNo;
    }
}
