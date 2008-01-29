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

import java.util.ArrayList;
import java.util.List;

import jp.terasoluna.fw.batch.core.InitializeException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * ジョブ依頼情報の格納クラス。
 * 
 */
public class JobRequestInfo implements JobInfo {

    /**
     * Serializable用バージョンID
     */
    private static final long serialVersionUID = 3134293738030810122L;

    /**
     * ログクラス。
     */
    private static Log log = LogFactory.getLog(JobRequestInfo.class);

    /**
     * プロセスIDの識別子。
     */
    private static final String PROCESS_ID_SPLIT = "-P";

    /**
     * ジョブID。
     */
    private String jobId = null;

    /**
     * ジョブBean定義ファイルの相対パス。
     */
    private String jobDiscriptorPath = null;

    /**
     * ジョブパラメータ格納値。
     */
    private String[] jobParameters = null;

    /**
     * ジョブプロセスID。
     */
    private String jobRequestNo = "";

    /**
     * 起動引数。
     */
    private String[] inParameters = null;

    /**
     * ジョブ依頼情報の格納クラスの設定を行うコンストラクタ。
     * 
     * @param inStr
     *            起動時の引数
     */
    public JobRequestInfo(String[] inStr) {
        this.inParameters = inStr;
    }

    /**
     * ジョブ依頼情報の初期化用メソッド。 引数の<code>String</code>配列を適切な
     * 値に設定する。
     * 
     */
    public void init() {
        if (inParameters == null || inParameters.length < 2) {
            throw new InitializeException(
                   "There is not a required argument at the time of job start");
        }
        setJobId(inParameters[0]);
        setJobDiscriptorPath(inParameters[1]);
        setJobParameters(inParameters);
        if (log.isDebugEnabled()) {
            log.debug("parameter values：" + toString());
        }
    }

    /**
     * ジョブパラメータ入力値を返す。
     * 
     * @return ジョブパラメータ入力値
     */
    public String[] getJobParameters() {
        return jobParameters;
    }

    /**
     * ジョブ定義ファイルの相対パスを返す。
     * 
     * @return ジョブ定義ファイルの相対パス
     */
    public String getJobDiscriptorPath() {
        return jobDiscriptorPath;
    }

    /**
     * ジョブIDを返す。
     * 
     * @return ジョブID
     */
    public String getJobId() {
        return jobId;
    }

    /**
     * ジョブプロセスIDを返す。
     * 
     * @return ジョブプロセスID
     */
    public String getJobRequestNo() {
        return jobRequestNo;
    }

    /**
     * ジョブコンテキスト格納用パラメータ設定。<BR>
     * ジョブプロセスIDを設定。
     * 
     * @param inStr
     *            起動時の引数
     */
    private void setJobParameters(String[] inStr) {
        List<String> tempArray = new ArrayList<String>();
        
        for (int index = 2; index < inStr.length ; index++) {
            
            String tempStr = inStr[index];
            
            if (tempStr != null
                  && PROCESS_ID_SPLIT.equalsIgnoreCase(tempStr)) {
                // ジョブプロセスIDを設定
                if (index < inStr.length - 1) {
                    this.jobRequestNo = inStr[++index];
                }
            } else {
                tempArray.add(tempStr);
            }
        }

        jobParameters = 
            (String[]) tempArray.toArray(new String[tempArray.size()]);
    }
    
    /**
     * ジョブBean設定ファイルの相対パスの設定。
     * 
     * @param jobDiscriptorPath
     *            起動時の第２引数
     */
    private void setJobDiscriptorPath(String jobDiscriptorPath) {
        if (jobDiscriptorPath == null) {
            throw new InitializeException(
                    "A job Bean definition file is null");
        }
        this.jobDiscriptorPath = jobDiscriptorPath;
    }

    /**
     * ジョブIDの設定。
     * 
     * @param jobId
     *            起動時の第１引数
     */
    private void setJobId(String jobId) {
        if (jobId == null) {
            throw new InitializeException("jobID is null");
        }
        this.jobId = jobId;
    }

    /**
     * 引数の設定値を文字列を作成。
     * 
     * @return 設定値の文字列
     */
    public String toString() {
        StringBuilder returnStr = new StringBuilder();
        returnStr.append("[jobId=" + jobId + "]");
        returnStr.append("[jobDiscriptPath：" + jobDiscriptorPath + "]");
        returnStr.append("[parameters：[");
        for (int i = 0; i < jobParameters.length; i++) {
            returnStr.append(jobParameters[i]);
            if (i != jobParameters.length - 1) {
                returnStr.append(", ");
            }
        }
        returnStr.append("]]");
        returnStr.append("[jobRequestNo：" + jobRequestNo + "]");

        return returnStr.toString();
    }

}
