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

package jp.terasoluna.fw.batch.openapi;

import java.util.LinkedHashMap;

import jp.terasoluna.fw.batch.messages.BLogicMessages;

/**
 * ビジネスロジック等の結果を保持するオブジェクト。 <BR>
 * ビジネスロジック、ジョブ前処理、ジョブ後処理、 先頭チャンク前処理、最終チャン
 * ク後処理および
 * コントロールブレイクハンドラでの結果。
 *
 */
public class BLogicResult {

    /**
     * 返却コード。
     */
    private ReturnCode returnCode;

    /**
     * バッチ更新用のMap。
     */
    private LinkedHashMap<String, Object> batchUpdateMap = null;

    /**
     * ジョブ終了コード。
     */
    private Integer jobExitCode = null;

    /**
     * ビジネスロジック内で生成されるエラー用BLogicMessages。
     */
    private BLogicMessages errors = null;

    /**
     * ビジネスロジック内で生成されるメッセージ用BLogicMessages。
     */
    private BLogicMessages messages = null;

    /**
     * コンストラクタ。<BR>
     *
     * @param returnCode 返却コード
     */
    public BLogicResult(ReturnCode returnCode) {
        this.returnCode = returnCode;
    }

    /**
     * コンストラクタ。<BR>
     *
     * @param returnCode 返却コード
     * @param jobExitCode ジョブ終了コード
     */
    public BLogicResult(ReturnCode returnCode, Integer jobExitCode) {
        this.returnCode = returnCode;
        this.jobExitCode = jobExitCode;
    }

    /**
     * コンストラクタ。<BR>
     *
     * @param returnCode 返却コード
     * @param jobExitCode ジョブ終了コード
     * @param batchUpdateMap バッチ更新リスト
     */
    public BLogicResult(ReturnCode returnCode, Integer jobExitCode, 
            LinkedHashMap<String, Object> batchUpdateMap) {
        this.returnCode = returnCode;
        this.jobExitCode = jobExitCode;
        this.batchUpdateMap = batchUpdateMap;
    }

    /**
     * コンストラクタ。<BR>
     *  BLogicResultのインスタンスは大量に作成されるため、 バッチ更新用のMapは
     *  遅延初期化する。
     *
     * @param returnCode 返却コード
     * @param batchUpdateMap バッチ更新リスト
     */
    public BLogicResult(ReturnCode returnCode, 
            LinkedHashMap<String, Object> batchUpdateMap) {
        this.returnCode = returnCode;
        this.batchUpdateMap = batchUpdateMap;
    }

    /**
     * パラメータを文字列に設定。
     * 
     * @return ビジネスロジック結果の文字列表現
     */
    @Override
    public String toString() {
        StringBuilder buffer = new StringBuilder();
        buffer.append("ReturnCode:");
        buffer.append(getReturnCode());
        buffer.append(" JobExitCode:");
        buffer.append(getJobExitCode());
        buffer.append(" ");
                
        return buffer.toString();
    }

    /**
     * リターンコードを返却する。
     *
     * @return リターンコード
     */
    public ReturnCode getReturnCode() {
        return returnCode;
    }

    /**
     * バッチ更新用のSQLIDとパラメータを保持したMapを返却する。
     *
     * @return SQLIDとパラメータを保持したMap。
     *          バッチ更新用のSQLIDとパラメータが設定されていない場合には 
     *          <code>null</code>
     */
    public LinkedHashMap<String, Object> getBatchUpdateMap() {
        return batchUpdateMap;
    }

    /**
     * ジョブ終了コードを返却する。
     *
     * @return ジョブ終了コード。
     *          ジョブ終了コードが設定されていない場合には <code>null</code>
     */
    public Integer getJobExitCode() {
        return jobExitCode;
    }
    
    /**
     * ビジネスロジック内で生成された、エラー用BLogicMessagesを取得する。
     *
     * @return ビジネスロジック内で生成された、エラー用BLogicMessages
     */
    public BLogicMessages getErrors() {
        return this.errors;
    }

    /**
     * ビジネスロジック内で生成された、メッセージ用BLogicMessagesを取得する。
     *
     * @return ビジネスロジック内で生成された、メッセージ用BLogicMessages
     */
    public BLogicMessages getMessages() {
        return this.messages;
    }

    /**
     * ビジネスロジック内で生成された、エラー用BLogicMessagesを設定する。
     *
     * @param paramErrors
     *            ビジネスロジック内で生成された、エラー用BLogicMessages
     */
    public void setErrors(BLogicMessages paramErrors) {
        this.errors = paramErrors;
    }

    /**
     * ビジネスロジック内で生成された、メッセージ用BLogicMessagesを設定する。
     *
     * @param paramMessages
     *            ビジネスロジック内で生成された、メッセージ用BLogicMessages
     */
    public void setMessages(BLogicMessages paramMessages) {
        this.messages = paramMessages;
    }
}