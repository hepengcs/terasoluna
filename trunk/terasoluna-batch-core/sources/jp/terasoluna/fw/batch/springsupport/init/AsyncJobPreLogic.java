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

import jp.terasoluna.fw.batch.openapi.BLogicResult;
import jp.terasoluna.fw.batch.openapi.ReturnCode;
import jp.terasoluna.fw.batch.openapi.SupportLogic;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 非同期型ジョブ起動において、ジョブを実行前に実行するサポート処理。
 * 
 * <p>起動対象のジョブのジョブ管理テーブルの起動状況を「起動中」に更新する。</p>
 * 
 */
public class AsyncJobPreLogic implements SupportLogic<AsyncJobContext> {

    /**
     * ログインスタンス。
     */
    private static Log log = LogFactory.getLog(AsyncJobPreLogic.class);

    /**
     * ログメッセージ（正常起動用）。
     */
    private String successMessage = "job starting by BatchDaemon";

    /**
     * コンストラクタ
     *
     */
    public AsyncJobPreLogic() {
    }
    
    /**
     * コンストラクタ
     * 
     * @param successMessage ログメッセージ（正常終了用）
     */
    protected AsyncJobPreLogic(String successMessage) {
        this.successMessage = successMessage;
    }

    /**
     * ジョブ起動前処理。<BR>
     * 起動対象のジョブ依頼情報の起動状況を起動中に更新する処理。
     * 
     * @param jobContext ジョブ依頼情報を格納したジョブコンテキスト
     * @return 非同期ジョブ実行前処理の処理結果。起動状況を更新できなかった
     * 場合には、<code>ReturnCode.ERROR_END</code>。
     */
    public BLogicResult execute(AsyncJobContext jobContext) {
        // ジョブ開始ログ
        if (log.isDebugEnabled()) {
            printDebugLog(successMessage, jobContext.getJobInfo().getJobId());
        }
        
        return new BLogicResult(ReturnCode.NORMAL_CONTINUE);
    }

    /**
     * デバックメッセージの出力。
     * 
     * @param message ログメッセージ
     * @param requestID ジョブ依頼番号
     */
    private void printDebugLog(String message, String requestID) {
        StringBuilder builder = new StringBuilder(message);
        builder.append(" 【Job RequestID : ");
        builder.append(requestID);
        builder.append("】");
        log.debug(builder.toString());
    }
}
