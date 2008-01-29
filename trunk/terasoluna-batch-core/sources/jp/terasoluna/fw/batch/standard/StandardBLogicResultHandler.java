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


import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import jp.terasoluna.fw.batch.core.BLogicResultHandler;
import jp.terasoluna.fw.batch.core.JobStatus;
import jp.terasoluna.fw.batch.messages.BLogicMessage;
import jp.terasoluna.fw.batch.messages.BLogicMessages;
import jp.terasoluna.fw.batch.messages.MessageAccessor;
import jp.terasoluna.fw.batch.openapi.BLogicResult;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <code>BLogicResultHandler</code> インタフェースの標準実装クラス。
 * 
 * <p>ビジネスロジック処理結果を <code>JobStatus</code> に反映し、エラーである
 * 場合にはログを出力する。</p>
 * 
 */
public class StandardBLogicResultHandler implements BLogicResultHandler {

    /**
     * ログインスタンス。
     */
    private static Log log =
        LogFactory.getLog(StandardBLogicResultHandler.class);

    /**
     * メッセージ取得クラスのインスタンス。
     */
    private MessageAccessor messageAccessor = null;
    
    /**
     * ログレベル。 
     *
     */
    protected enum LOG_TYPE {
        /**
         * TRACE。
         */
        TRACE,
        /**
         * DEBUG。
         */
        DEBUG,
        /** 
         * INFO。
         */
        INFO,
        /** 
         * WARN。
         */
        WARN,
        /** 
         * ERROR。
         */
        ERROR,
        /** 
         * FATAL。
         */
        FATAL
    }
    
    /**
     * BLogicの処理結果を処理する。
     * 
     * @param blogicResult ビジネスロジック処理結果
     * @param blogicInputData ビジネスロジックの入力データ
     * @param jobStatus ジョブステータス
     * @param batchUpdateMapList バッチ更新リスト
     */
    public void handle(BLogicResult blogicResult, Object blogicInputData,
            JobStatus jobStatus, 
            List<LinkedHashMap<String, Object>> batchUpdateMapList) {
        jobStatus.countBLogic(blogicResult.getReturnCode());
        
        // BLogicResultに設定されたメッセージの処理を行う。
        processBLogicMessages(blogicResult);

        switch (blogicResult.getReturnCode()) {
        case NORMAL_CONTINUE:
            processNormalContinue(jobStatus, blogicResult, batchUpdateMapList);
            break;
        case NORMAL_END:
            processNormalEnd(jobStatus, blogicResult, batchUpdateMapList);
            break;
        case ERROR_CONTINUE:
            processErrorContinue(blogicInputData, jobStatus, blogicResult);
            break;
        case ERROR_END:
            processErrorEnd(blogicInputData, jobStatus, blogicResult);
            break;
        default:
            throw new IllegalArgumentException(blogicResult.getReturnCode()
                    + " illegal ReturnCode");
        }
    }
    
    /**
     * {@link BLogicMessages}の処理を行う。<br />
     * {@link BLogicResult}の{@link BLogicResult#setErrors(BLogicMessages)}と
     * {@link BLogicResult#setMessages(BLogicMessages)}で設定されたメッセージの
     * 処理を行う。<br />
     * <br />
     * デフォルト処理は以下である。<br />
     * <li>setMessages(BLogicMessages): INFOレベルのログの出力</li>
     * <li>setErrors(BLogicMessages): ERRORレベルのログの出力</li>
     * <br /><br />
     * このメソッドを再定義することでBLogicMessageの処理を変更することができる。
     * 
     * @param blogicResult ビジネスロジック処理結果
     */
    protected void processBLogicMessages(BLogicResult blogicResult) {
        // messagesのinfoログ出力
        writeBLogicMessagesLog(blogicResult.getMessages(), LOG_TYPE.INFO);
        
        // errorsのerrorログ出力
        writeBLogicMessagesLog(blogicResult.getErrors(), LOG_TYPE.ERROR);
    }

    /**
     * <code>BLogicResult</code> のリターンコードが <code>NORMAL_CONTINUE</code>
     *  であるときの処理を行う。
     * 
     * <p>BLogicResultがバッチ更新情報を保持していた場合には、バッチ更新リストに
     * 追加する。</p>
     *
     * @param jobStatus ジョブステータス
     * @param bLogicResult ビジネスロジック処理結果
     * @param batchUpdateMapList バッチ更新リスト
     */
    protected void processNormalContinue(JobStatus jobStatus, 
            BLogicResult bLogicResult, 
            List<LinkedHashMap<String, Object>> batchUpdateMapList) {
        if (bLogicResult.getBatchUpdateMap() != null 
                && bLogicResult.getBatchUpdateMap().size() > 0) {
            batchUpdateMapList.add(bLogicResult.getBatchUpdateMap());
        }
    }

    /**
     * <code>BLogicResult</code> のリターンコードが <code>NORMAL_END</code> で
     * あるときの処理を行う。
     * 
     * <p>BLogicResultがバッチ更新情報を保持していた場合には、バッチ更新リストに
     * 追加する。</p>
     * 
     * <p>JobStatus</p> のジョブ状態 <code>JobStatus.STATE.ENDING_NORMALLY
     * </code> に更新し、<code>BLogicResult<code>
     * のジョブ終了コードを <code>JobStatus</code> に反映する。</p>
     *
     * @param jobStatus ジョブステータス
     * @param bLogicResult ビジネスロジック処理結果
     * @param batchUpdateMapList バッチ更新リスト
     */
    protected void processNormalEnd(JobStatus jobStatus,
            BLogicResult bLogicResult,
            List<LinkedHashMap<String, Object>> batchUpdateMapList) {
        if (bLogicResult.getBatchUpdateMap() != null 
                && bLogicResult.getBatchUpdateMap().size() > 0) {
            batchUpdateMapList.add(bLogicResult.getBatchUpdateMap());
        }
        jobStatus.setJobState(JobStatus.STATE.ENDING_NORMALLY);
        jobStatus.setJobExitCode(bLogicResult.getJobExitCode());
    }

    /**
     * <code>BLogicResult</code> のリターンコードが <code>ERROR_CONTINUE</code>
     *  であるときの処理を行う。
     *
     * <p>エラーログを出力する。</p>
     * 
     * <p><code>BLogicResult</code> がバッチ更新情報や、ジョブ終了コードを持って
     * いた場合でも無視される。</p>
     *
     * @param blogicInputData ビジネスロジックの入力データ
     * @param jobStatus ジョブステータス
     * @param blogicResult ビジネスロジック処理結果
     */
    protected void processErrorContinue(Object blogicInputData,
            JobStatus jobStatus, BLogicResult blogicResult) {
        writeErrorLog(jobStatus, blogicResult, blogicInputData);
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
     * @param blogicInputData ビジネスロジックの入力データ
     * @param jobStatus ジョブステータス
     * @param bLogicResult ビジネスロジック処理結果
     */
    protected void processErrorEnd(Object blogicInputData,
            JobStatus jobStatus, BLogicResult bLogicResult) {
        writeErrorLog(jobStatus, bLogicResult, blogicInputData);
        jobStatus.setJobState(JobStatus.STATE.ENDING_ABNORMALLY);
        jobStatus.setJobExitCode(bLogicResult.getJobExitCode());
    }

    /**
     * ビジネスロジックでのエラーデータをログに出力する。
     * 
     * @param jobStatus ジョブステータス
     * @param bLogicResult ビジネスロジック処理結果
     * @param blogicInputData ビジネスロジックの入力データ
     */
    protected void writeErrorLog(JobStatus jobStatus,
            BLogicResult bLogicResult, Object blogicInputData) {
        log.error("Error JobID: " + jobStatus.getJobId() + " InputData: " 
                + blogicInputData);
    }

    /**
     * BLogicMessageリストを指定のログレベルへ出力する。
     * 
     * @param blogicMessages BLogicMessageリスト
     * @param logType ログレベル
     */
    protected void writeBLogicMessagesLog(BLogicMessages blogicMessages,
            LOG_TYPE logType) {
        //メッセージがNullか一つもない場合は何もしない。
        if (blogicMessages == null || blogicMessages.isEmpty()) {
            return;
        }
        // BLogicMessages取得用イテレータ
        Iterator itr = blogicMessages.get();
        while (itr.hasNext()) {
            // BLogicMessageを取得
            BLogicMessage blogicMessage = (BLogicMessage) itr.next();

            if (blogicMessage.isResource()) {
                writeLog(messageAccessor.getMessage(blogicMessage.getKey(),
                        blogicMessage.getValues()), logType);
            } else {
                writeLog(blogicMessage.getKey(), logType);
            }
        }
    }

    /**
     * 指定のログレベルへメッセージを出力する。
     * 
     * @param message メッセージ
     * @param logType ログレベル
     */
    protected void writeLog(String message, LOG_TYPE logType) {
        switch (logType) {
        case TRACE:
            log.trace(message);
            break;
        case DEBUG:
            log.debug(message);
            break;
        case INFO:
            log.info(message);
            break;
        case WARN:
            log.warn(message);
            break;
        case ERROR:
            log.error(message);
            break;
        case FATAL:
            log.fatal(message);
            break;
        default:
            throw new IllegalArgumentException("No log Type which agrees.");
        }
    }
    
    /**
     * メッセージ取得クラスのインスタンスを設定する。
     * 
     * @param messageAccessor
     *            メッセージ取得クラスのインスタンス
     */
    public void setMessageAccessor(MessageAccessor messageAccessor) {
        this.messageAccessor = messageAccessor;
    }
}
