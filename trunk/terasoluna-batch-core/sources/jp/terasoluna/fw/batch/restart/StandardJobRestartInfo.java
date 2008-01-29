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

package jp.terasoluna.fw.batch.restart;

import java.util.Date;

/**
 * ジョブリスタート情報格納用実装クラス。 <BR>
 * <code>jobRestart-sqlMap.xml</code>に定義された以下のクラス定義を変更することで
 * 独自のパラメータクラスを作成することが出来る。<BR>
 * <code>&lt;typeAlias alias="jobRestartInfo" type="jp.terasoluna.fw.batch.restart.JobRestartInfoImpl"/&gt;</code>
 * 
 */
public class StandardJobRestartInfo implements JobRestartInfo {

    /**
     * Serializable用バージョンID
     */
    private static final long serialVersionUID = -1860477193281337863L;

    /**
     * ジョブリクエスト番号
     */
    private String requestNo = null;

    /**
     * ジョブID
     */
    private String jobId = null;

    /**
     * ジョブパーティション番号
     */
    private int partitionNo = 0;

    /**
     * ジョブパーティションキー
     */
    private String partitionKey = null;

    /**
     * リスタートポイント
     */
    private int restartPoint = 0;

    /**
     * ジョブコンテキスト
     */
    private byte[] jobContext = null;

    /**
     * 処理状況
     */
    private String state = null;

    /**
     * リスタート情報の更新日時
     */
    private Date updateTime = null;

    /**
     * リスタート情報の登録日時
     */
    private Date registerTime = null;

    /**
     * リクエスト情報を取得。
     * 
     * @return requestNo リクエスト情報
     */
    public String getRequestNo() {
        return requestNo;
    }

    /**
     * リクエスト情報を設定。
     * 
     * @param requestNo
     *            リクエスト情報
     */
    public void setRequestNo(String requestNo) {
        this.requestNo = requestNo;
    }

    /**
     * ジョブIDを取得。
     * 
     * @return jobId ジョブID
     */
    public String getJobId() {
        return jobId;
    }

    /**
     * ジョブIDを設定。
     * 
     * @param jobId
     *            ジョブID
     */
    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    /**
     * パーティション番号を取得。
     * 
     * @return partitionNo パーティション番号
     */
    public int getPartitionNo() {
        return partitionNo;
    }

    /**
     * パーティション番号を設定。
     * 
     * @param partitionNo
     *            パーティション番号
     */
    public void setPartitionNo(int partitionNo) {
        this.partitionNo = partitionNo;
    }

    /**
     * パーティションキーを取得。
     * 
     * @return partitionKey パーティションキー
     */
    public String getPartitionKey() {
        return partitionKey;
    }

    /**
     * パーティションキーを設定。
     * 
     * @param partitionKey
     *            パーティションキー
     */
    public void setPartitionKey(String partitionKey) {
        this.partitionKey = partitionKey;
    }

    /**
     * リスタートポイントを取得。
     * 
     * @return restartPoint リスタートポイント
     */
    public int getRestartPoint() {
        return restartPoint;
    }

    /**
     * リスタートポイントを設定。
     * 
     * @param restartPoint
     *            リスタートポイント
     */
    public void setRestartPoint(int restartPoint) {
        this.restartPoint = restartPoint;
    }

    /**
     * ジョブコンテキストを取得。
     * 
     * @return jobContext ジョブコンテキスト
     */
    public byte[] getJobContext() {
        return jobContext;
    }

    /**
     * ジョブコンテキストを設定。
     * 
     * @param jobContext
     *            ジョブコンテキスト
     */
    public void setJobContext(byte[] jobContext) {
        this.jobContext = jobContext;
    }

    /**
     * 起動状況を取得。
     * 
     * @return state 起動状況
     */
    public String getState() {
        return state;
    }

    /**
     * 起動状況を設定。
     * 
     * @param state
     *            起動状況
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * リスタート情報登録時刻を取得。
     * 
     * @return registerTime リスタート情報登録時刻
     */
    public Date getRegisterTime() {
        return registerTime;
    }

    /**
     * リスタート情報登録時刻を設定。
     * 
     * @param registerTime
     *            リスタート情報登録時刻
     */
    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }

    /**
     * リスタート情報更新時刻を取得。
     * 
     * @return updateTime リスタート情報更新時刻
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * リスタート情報更新時刻を設定。
     * 
     * @param updateTime
     *            リスタート情報更新時刻
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

}
