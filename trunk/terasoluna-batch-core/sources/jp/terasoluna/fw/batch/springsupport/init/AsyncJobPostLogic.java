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

import jp.terasoluna.fw.batch.init.JobControlTableHandler;
import jp.terasoluna.fw.batch.init.JobInfo;
import jp.terasoluna.fw.batch.openapi.BLogicResult;
import jp.terasoluna.fw.batch.openapi.ReturnCode;
import jp.terasoluna.fw.batch.openapi.SupportLogic;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 非同期型ジョブ起動において、ジョブを実行した後に実行するサポート処理。
 * 
 * <p>実行したジョブのジョブ管理テーブルの起動状況を「正常終了」等に更新する。</p>
 * 
 */
public class AsyncJobPostLogic implements SupportLogic<AsyncJobContext> {

    /**
     * ログインスタンス。
     */
    private static Log log = LogFactory.getLog(AsyncJobPostLogic.class);

    /**
     * ジョブ管理テーブルハンドラ。
     */
    private JobControlTableHandler jobControlTableHandler = null;

    /**
     * ログメッセージ（異常終了用）。
     */
    private String failureMessage = "job ended in failure by BatchDaemon";
    
    /**
     * ログメッセージ（正常終了用）。
     */
    private String successMessage = "job ending by BatchDaemon";
    

    /**
     * コンストラクタ
     *
     */
    public AsyncJobPostLogic() {
    }
    
    /**
     * コンストラクタ
     * 
     * @param faulureMessage ログメッセージ（異常終了用）
     * @param successMessage ログメッセージ（正常終了用）
     */
    protected AsyncJobPostLogic(String faulureMessage, String successMessage) {
        this.failureMessage = faulureMessage;
        this.successMessage = successMessage;
    }

    /**
     * 非同期型ジョブ起動において、ジョブを実行後処理を行う。
     * 
     * @param jobContext ジョブ依頼情報を格納したジョブコンテキスト
     * @return 非同期ジョブ実行後処理の処理結果。起動状況を更新できなかった場合
     * には、<code>ReturnCode.ERROR_END</code>。
     */
    public BLogicResult execute(AsyncJobContext jobContext) {

        // ジョブ実行結果DB反映
        if (handle(jobContext.getJobInfo()) != 1) {
            if (log.isDebugEnabled()) {
                printDebugLog(failureMessage,
                        jobContext.getJobInfo().getJobId());
            }
            return new BLogicResult(ReturnCode.ERROR_END);
        }
        
        if (log.isDebugEnabled()) {
            printDebugLog(successMessage, jobContext.getJobInfo().getJobId());
        }

        return new BLogicResult(ReturnCode.NORMAL_END);
    }

    /**
     * ジョブ管理テーブルへのハンドリング
     * 
     * @param jobInfo ジョブ依頼情報
     * @return 処理数
     */
    protected int handle(JobInfo jobInfo) {
        return jobControlTableHandler.updateJobEnd(jobInfo);
    }
    
    /**
     * ジョブ管理テーブルハンドラを設定する。
     * 
     * @param jobControlTableHandler ジョブ管理テーブルハンドラ
     */
    public void setJobControlTableHandler(
            JobControlTableHandler jobControlTableHandler) {
        this.jobControlTableHandler = jobControlTableHandler;
    }

    /**
     * デバックメッセージの出力。
     * 
     * @param message ログメッセージ
     * @param jobId ジョブＩＤ
     */
    private void printDebugLog(String message, String jobId) {
        StringBuilder builder = new StringBuilder(message);
        builder.append(" 【Job ID : ");
        builder.append(jobId);
        builder.append("】");
        log.debug(builder.toString());
    }

}
