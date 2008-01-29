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

package jp.terasoluna.fw.batch.jobmessage;

import java.util.Date;

import jp.terasoluna.fw.batch.init.JobControlInfo;

/**
 * ジョブ依頼メッセージ管理テーブルのパラメータクラス。
 * 
 * 
 */
public class JobMessageInfo extends JobControlInfo {

    /**
     * Serializable用バージョンID。
     */
    private static final long serialVersionUID = -2755193066158860641L;

    /**
     * 連結ジョブパラメータ。
     */
    private String jobConcatParameters;

    /**
     * パーティションNo。
     */
    private int partitionNo = -1;

    /**
     * パーティションキー。
     */
    private String partitionKey = null;

    /**
     * メッセージ送受信状況。
     */
    private String messageState;

    /**
     * メッセージ受信日時。
     */
    private Date receiveTime;

    /**
     * メッセージ送信日時。
     */
    private Date sendTime;

    /**
     * ジョブ終了時間。
     */
    private Date finishTime;

    /**
     * メッセージ送受信状況の取得。
     * 
     * @return メッセージ送受信状況
     */
    public String getMessageState() {
        return messageState;
    }

    /**
     * メッセージ送受信状況の設定。
     * 
     * @param messageState メッセージ送受信状況
     */
    public void setMessageState(String messageState) {
        this.messageState = messageState;
    }

    /**
     * メッセージ受信日時の取得。
     * 
     * @return メッセージ受信日時
     */
    public Date getReceiveTime() {
        return receiveTime;
    }

    /**
     * メッセージ受信日時の設定。
     * 
     * @param receiveTime メッセージ受信日時
     */
    public void setReceiveTime(Date receiveTime) {
        this.receiveTime = receiveTime;
    }

    /**
     * メッセージ送信日時の取得。
     * 
     * @return メッセージ送信日時
     */
    public Date getSendTime() {
        return sendTime;
    }

    /**
     * メッセージ送信日時の設定。
     * 
     * @param sendTime メッセージ送信日時
     */
    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    /**
     * 連結ジョブパラメータの取得。
     * 
     * @return jobConcatParameters 連結ジョブパラメータ
     */
    public String getJobConcatParameters() {
        return jobConcatParameters;
    }

    /**
     * 連結ジョブパラメータの設定。
     * 
     * @param jobConcatParameters 連結ジョブパラメータ
     */
    public void setJobConcatParameters(String jobConcatParameters) {
        this.jobConcatParameters = jobConcatParameters;
        super.setJobParameters(jobConcatParameters);
    }

    /**
     * ジョブパラメータの設定。
     * 
     * @param jobParameters ジョブパラメータ
     */
    @Override
    public void setJobParameters(String jobParameters) {
        setJobConcatParameters(jobParameters);
    }

    /**
     * ジョブ終了時間の取得。
     * 
     * @return ジョブ終了時間
     */
    public Date getFinishTime() {
        return finishTime;
    }

    /**
     * ジョブ終了時間の設定。
     * 
     * @param finishTime ジョブ終了時間
     */
    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    /**
     * パーティションキーを取得する。
     * 
     * @return partitionKey パーティションキー
     */
    public String getPartitionKey() {
        return partitionKey;
    }

    /**
     * パーティションキーを設定する。
     * 
     * @param partitionKey パーティションキー
     */
    public void setPartitionKey(String partitionKey) {
        this.partitionKey = partitionKey;
    }

    /**
     * パーティションNOを取得する。
     * 
     * @return partitionNo パーティションNO
     */
    public int getPartitionNo() {
        return partitionNo;
    }

    /**
     * パーティションNOを設定する。
     * 
     * @param partitionNo パーティションNO
     */
    public void setPartitionNo(int partitionNo) {
        this.partitionNo = partitionNo;
    }
}
