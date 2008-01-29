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

package jp.terasoluna.fw.batch.standard;

import jp.terasoluna.fw.batch.core.JobStatus;
import jp.terasoluna.fw.batch.core.SupportLogicResultHandler;
import jp.terasoluna.fw.batch.openapi.BLogicResult;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <code>SupportLogicResultHandler</code> インタフェースの標準実装クラス。
 * 
 * <p>このクラスでは、サポートロジック処理結果が <code>NORMAL_END</code>、
 * <code>ERROR_END</code>であるときに、ジョブステータスを更新する。</p>
 * 
 */
public class StandardSupportLogicResultHandler 
    implements SupportLogicResultHandler {

    /**
     * ログインスタンス。
     */
    private static Log log = 
        LogFactory.getLog(StandardSupportLogicResultHandler.class);

    /**
     * サポート処理の実行結果を処理する。
     * 
     * @param blogicResult サポート処理の実行結果
     * @param jobStatus ジョブステータス
     * @param name サポート処理クラスに設定された名前
     */
    public void handle(BLogicResult blogicResult, JobStatus jobStatus,
            String name) {
        switch (blogicResult.getReturnCode()) {
            case NORMAL_CONTINUE:
                processNormalContinue(jobStatus, blogicResult, name);
                break;
            case NORMAL_END:
                processNormalEnd(jobStatus, blogicResult, name);
                break;
            case ERROR_CONTINUE:
                processErrorContinue(jobStatus, blogicResult, name);
                break;
            case ERROR_END:
                processErrorEnd(jobStatus, blogicResult, name);
                break;
            default:
                throw new IllegalArgumentException(blogicResult.getReturnCode()
                        + " illegal ReturnCode");
            
        }
    }

    /**
     * <code>BLogicResult</code> のリターンコードが <code>NORMAL_CONTINUE</code>
     *  であるときの処理を行う。
     * 
     * @param jobStatus ジョブステータス
     * @param bLogicResult ビジネスロジック処理結果
     * @param name サポート処理クラスに設定された名前
     */
    protected void processNormalContinue(JobStatus jobStatus,
            BLogicResult bLogicResult, String name) {
    }

    /**
     * <code>BLogicResult</code> のリターンコードが <code>NORMAL_END</code> で
     * あるときの処理を行う。
     * 
     * <p>JobStatus</p> のジョブ状態 <code>JobStatus.STATE.ENDING_NORMALLY
     * </code> に更新し、<code>BLogicResult<code>
     * のジョブ終了コードを <code>JobStatus</code> に反映する。</p>
     *
     * @param jobStatus ジョブステータス
     * @param bLogicResult ビジネスロジック処理結果
     * @param name サポート処理クラスに設定された名前
     */
    protected void processNormalEnd(JobStatus jobStatus,
            BLogicResult bLogicResult, String name) {
        jobStatus.setJobState(JobStatus.STATE.ENDING_NORMALLY);
        jobStatus.setJobExitCode(bLogicResult.getJobExitCode());
    }

    /**
     * <code>BLogicResult</code> のリターンコードが <code>ERROR_CONTINUE</code>
     *  であるときの処理を行う。
     *
     * <p>エラーログを出力する。</p>
     * 
     * @param jobStatus ジョブステータス
     * @param bLogicResult サポート処理の実行結果
     * @param name サポート処理クラスに設定された名前
     */
    protected void processErrorContinue(JobStatus jobStatus, 
            BLogicResult bLogicResult, String name) {
        writeErrorLog(jobStatus, bLogicResult, name);
    }

    /**
     * <code>BLogicResult</code> のリターンコードが <code>ERROR_END</code> で
     * あるときの処理を行う。
     *
     * <p>エラーログを出力する。</p>
     * 
     * <p>JobStatus</p> のジョブ状態 <code>JobStatus.STATE.ENDING_ABNORMALLY
     * </code> に更新し、<code>BLogicResult<code>
     * のジョブ終了コードを <code>JobStatus</code> に反映する。</p>
     *
     * @param jobStatus ジョブステータス
     * @param bLogicResult ビジネスロジック処理結果
     * @param name サポート処理クラスに設定された名前
     */
    protected void processErrorEnd(JobStatus jobStatus, 
            BLogicResult bLogicResult, String name) {
        writeErrorLog(jobStatus, bLogicResult, name);
        jobStatus.setJobState(JobStatus.STATE.ENDING_ABNORMALLY);
        jobStatus.setJobExitCode(bLogicResult.getJobExitCode());
    }

    /**
     * サポートロジックの処理結果のエラーログを出力する。
     * 
     * @param jobStatus ジョブステータス
     * @param result サポートロジックの処理結果
     * @param name サポート処理クラスに設定された名前
     */
    protected void writeErrorLog(JobStatus jobStatus, BLogicResult result,
            String name) {
        log.error("SupportLogic Name: " + name + " Error JobID: "
                + jobStatus.getJobId() + " SupportLogic result: " + result);
    }
}
