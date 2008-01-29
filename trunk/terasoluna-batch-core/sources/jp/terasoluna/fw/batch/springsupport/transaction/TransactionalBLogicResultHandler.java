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

package jp.terasoluna.fw.batch.springsupport.transaction;

import java.util.LinkedHashMap;
import java.util.List;

import jp.terasoluna.fw.batch.core.JobStatus;
import jp.terasoluna.fw.batch.openapi.BLogicResult;
import jp.terasoluna.fw.batch.standard.StandardBLogicResultHandler;

/**
 * トランザクション処理を行う <code>BLogicResultHandler</code> インタフェースの
 * 実装クラス。
 * 
 * <p><code>BLogicResult</code> のリターンコードに応じて、トランザクション処理
 * （コミット、ロールバック）を行う。トランザクション状態は、
 * <code>{@link TransactionalJobStatus}<code> に保持されている。</p>
 * 
 * <p><strong>セーブポイントを利用している場合</strong></p>
 * 
 * <p><code>BLogicResult</code> のリターンコードとして <code>NORMAL_CONTINUE
 * </code> が返される毎に、セーブポイントを開放、作成する。</p>
 * 
 * <p><code>BLogicResult</code> のリターンコードとして <code>ERROR_CONTINUE
 * </code>、または<code>ERROR_END</code> が返された場合には、セーブポイントまで
 * トランザクションをロールバックする。</p>
 * 
 * <p><strong>セーブポイントを利用していない場合</strong></p>
 * 
 * <p><code>BLogicResult</code> のリターンコードとして <code>NORMAL_CONTINUE
 * </code>、あるいは<code>ERROR_CONTINUE</code> が返された場合には、トランザクシ
 * ョンに関わる処理は実行しない。</p>
 * 
 * <p><code>BLogicResult</code> のリターンコードとして <code>ERROR_END</code> が
 * 返された場合には、実行中のトランザクションをロールバックする。</p>
 * 
 */
public class TransactionalBLogicResultHandler 
    extends StandardBLogicResultHandler {

    /**
     * <code>BLogicResult</code> のリターンコードが <code>NORMAL_CONTINUE</code>
     *  であるときの処理を行う。
     * 
     * <p>セーブポイントを利用している場合にはセーブポイントを作成し直し、親クラ
     * スの処理を呼び出す。</p>
     *
     * @param jobStatus ジョブステータス
     * @param blogicResult ビジネスロジック処理結果
     * @param batchUpdateMapList バッチ更新リスト
     */
    @Override
    protected void processNormalContinue(JobStatus jobStatus, 
            BLogicResult blogicResult, List<LinkedHashMap<String, 
            Object>> batchUpdateMapList) {
        TransactionalJobStatus transactionalJobStatus = 
            (TransactionalJobStatus) jobStatus;

        // セーブポイントの開放、作成
        if (transactionalJobStatus.useSavepoint()) {
            transactionalJobStatus.releaseAndCreateSavepoint();
        }
        
        super.processNormalContinue(jobStatus, blogicResult,
                batchUpdateMapList);
    }

    /**
     * <code>BLogicResult</code> のリターンコードが <code>ERROR_CONTINUE</code> 
     * であるときの処理を行う。
     *
     * <p>セーブポイントを利用している場合にはセーブポイントまでトランザクション
     * をロールバックし、親クラスの処理を呼び出す。</p>
     *
     * @param blogicInputData ビジネスロジックの入力データ
     * @param jobStatus ジョブステータス
     * @param blogicResult ビジネスロジック処理結果
     */
    @Override
    protected void processErrorContinue(Object blogicInputData,
            JobStatus jobStatus, BLogicResult blogicResult) {
        TransactionalJobStatus transactionalJobStatus = 
            (TransactionalJobStatus) jobStatus;
        // ロールバック
        if (transactionalJobStatus.useSavepoint()) {
            transactionalJobStatus.rollbackToSavepoint();
        }
        
        super.processErrorContinue(blogicInputData, jobStatus, blogicResult);
    }

    /**
     * <code>BLogicResult</code> のリターンコードが <code>ERROR_END</code> で
     * あるときの処理を行う。
     *
     * <p>セーブポイントを利用している場合にはセーブポイントまでトランザクション
     * をロールバックし、親クラスの処理を呼び出す。</p>
     * 
     * <p>セーブポイントを利用していない場合にはトランザクションをロールバックし、
     * 親クラスの処理を呼び出す。</p>
     *
     * @param blogicInputData ビジネスロジックの入力データ
     * @param jobStatus ジョブステータス
     * @param blogicResult ビジネスロジック処理結果
     */
    @Override
    protected void processErrorEnd(Object blogicInputData,
            JobStatus jobStatus, BLogicResult blogicResult) {
        TransactionalJobStatus transactionalJobStatus = 
            (TransactionalJobStatus) jobStatus;
        // ロールバック
        if (transactionalJobStatus.useSavepoint()) {
            transactionalJobStatus.rollbackToSavepoint();
        } else {
            transactionalJobStatus.rollback();
        }
    
        super.processErrorEnd(blogicInputData, jobStatus, blogicResult);
    }
}
