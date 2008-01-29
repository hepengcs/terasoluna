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

import java.util.Date;

/**
 * ジョブ管理テーブルのデータ取得用パラメータクラス。<BR>
 * <code>jobControl-sqlMap.xml</code>に定義された以下のクラス定義を変更することで
 * 独自のパラメータクラスを作成することが出来る。<BR>
 * <code>&lt;typeAlias alias="jobControlInfo" 
 * type="jp.terasoluna.fw.batch.init.JobControlInfo"/&gt;</code>
 * 
 */
public class JobControlInfo extends AbstractJobControlInfo {

    /**
     * Serializable用バージョンID
     */
    private static final long serialVersionUID = 3551061550379453205L;

    /**
     * ジョブリクエスト番号
     */
    private String jobRequestNo = null;

    /**
     * ジョブID
     */
    private String jobId = null;

    /**
     * ジョブBean定義ファイルのPATH
     */
    private String jobDiscriptorPath = null;

    /**
     * ジョブパラメータ
     */
    private String jobParameters = null;

    /**
     * ジョブ起動状況
     */
    private String jobState = null;

    /**
     * ジョブ終了コード
     */
    private String jobExitCode = null;

    /**
     * ジョブ依頼情報の更新日時
     */
    private Date updateTime = null;

    /**
     * ジョブ依頼情報の登録日時
     */
    private Date registerTime = null;

    /**
     * ジョブ終了コードを取得。
     * 
     * @return ジョブ終了コード
     */
    public String getJobExitCode() {
        return jobExitCode;
    }

    /**
     * ジョブ終了コードを設定。
     * 
     * @param jobExitCode
     *            ジョブ終了コード
     */
    public void setJobExitCode(String jobExitCode) {
        this.jobExitCode = jobExitCode;
    }

    /**
     * ジョブBean定義ファイルのPATHを取得。
     * 
     * @return ジョブBean定義ファイルのPATH
     */
    public String getJobDiscriptorPath() {
        return jobDiscriptorPath;
    }

    /**
     * ジョブBean定義ファイルのPATHを設定。
     * 
     * @param jobDiscriptorPath
     *            ジョブBean定義ファイルのPATH
     */
    public void setJobDiscriptorPath(String jobDiscriptorPath) {
        this.jobDiscriptorPath = jobDiscriptorPath;
    }

    /**
     * ジョブIDを取得。
     * 
     * @return String ジョブID
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
     * ジョブパラメータを取得。
     * 
     * @return ジョブパラメータ
     */
    public String[] getJobParameters() {
        if (jobParameters == null) {
            return new String[0];
        }
        return jobParameters.split(getJobParametersSplitStr());
    }

    /**
     * ジョブ起動パラメータを設定。
     * 
     * @param jobParameters
     *            ジョブ起動パラメータ
     */
    public void setJobParameters(String jobParameters) {
        this.jobParameters = jobParameters;
    }

    /**
     * ジョブ依頼情報の登録日時を取得。
     * 
     * @return ジョブ依頼情報の登録日時
     */
    public Date getRegisterTime() {
        return registerTime;
    }

    /**
     * ジョブ依頼情報の登録日時を設定。
     * 
     * @param registerTime
     *            ジョブ依頼情報の登録日時
     */
    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }

    /**
     * ジョブリクエスト番号を取得。
     * 
     * @return ジョブリクエスト番号
     */
    public String getJobRequestNo() {
        return jobRequestNo;
    }

    /**
     * ジョブリクエスト番号を設定。
     * 
     * @param jobRequestNo
     *            ジョブリクエスト番号
     */
    public void setJobRequestNo(String jobRequestNo) {
        this.jobRequestNo = jobRequestNo;
    }

    /**
     * ジョブ起動状況を取得。
     * 
     * @return ジョブ起動状況
     */
    public String getJobState() {
        return jobState;
    }

    /**
     * ジョブ起動状況を設定。
     * 
     * @param jobState
     *            ジョブ起動状況
     */
    public void setJobState(String jobState) {
        this.jobState = jobState;
    }

    /**
     * ジョブ依頼情報の更新日時を取得。
     * 
     * @return ジョブ依頼情報の更新日時
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * ジョブ依頼情報の更新日時を設定。
     * 
     * @param updateTime
     *            ジョブ依頼情報の更新日時
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
