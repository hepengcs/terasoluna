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

import jp.terasoluna.fw.batch.core.JobStatus;
import jp.terasoluna.fw.batch.openapi.BLogicResult;
import jp.terasoluna.fw.batch.standard.StandardSupportLogicResultHandler;

/**
 * トランザクション処理を行う <code>SupportLogicResultHandler</code> インタフェ
 * ースの実装クラス。
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
 * </code>、または <code>ERROR_END</code> が返された場合には、セーブポイントまで
 * トランザクションをロールバックする。</p>
 * 
 * <p><strong>セーブポイントを利用していない場合</strong></p>
 * 
 * <p><code>BLogicResult</code> のリターンコードとして <code>NORMAL_CONTINUE
 * </code>、あるいは <code>ERROR_CONTINUE</code> が返された場合には、トランザク
 * ションに関わる処理は実行しない。</p>
 * 
 * <p><code>BLogicResult</code> のリターンコードとして <code>ERROR_END</code> が
 * 返された場合には、実行中のトランザクションをロールバックする。</p>
 * 
 */
public class TransactionalSupportLogicResultHandler 
    extends StandardSupportLogicResultHandler {

    /**
     * <code>BLogicResult</code> のリターンコードが <code>NORMAL_CONTINUE</code>
     *  であるときの処理を行う。
     * 
     * <p>セーブポイントを利用している場合にはセーブポイントを作成し直し、親クラ
     * スの処理を呼び出す。</p>
     *
     * @param jobStatus ジョブステータス
     * @param blogicResult ビジネスロジック処理結果
     * @param name サポート処理名
     */
    @Override
    protected void processNormalContinue(JobStatus jobStatus, 
            BLogicResult blogicResult, String name) {
        TransactionalJobStatus transactionalJobStatus = 
            (TransactionalJobStatus) jobStatus;

        // セーブポイントの開放、作成
        if (transactionalJobStatus.useSavepoint()) {
            transactionalJobStatus.releaseAndCreateSavepoint();
        }
        
        super.processNormalContinue(jobStatus, blogicResult, name);
    }

    /**
     * <code>BLogicResult</code> のリターンコードが <code>ERROR_CONTINUE</code>
     *  であるときの処理を行う。
     *
     * <p>セーブポイントを利用している場合にはセーブポイントまでトランザクション
     * をロールバックし、親クラスの処理を呼び出す。</p>
     *
     * @param jobStatus ジョブステータス
     * @param bLogicResult ビジネスロジック処理結果
     * @param name サポート処理名
     */
    @Override
    protected void processErrorContinue(JobStatus jobStatus, 
            BLogicResult bLogicResult, String name) {
        TransactionalJobStatus transactionalJobStatus = 
            (TransactionalJobStatus) jobStatus;
        // ロールバック
        if (transactionalJobStatus.useSavepoint()) {
            transactionalJobStatus.rollbackToSavepoint();
        }
        
        super.processErrorContinue(jobStatus, bLogicResult, name);
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
     * @param jobStatus ジョブステータス
     * @param blogicResult ビジネスロジック処理結果
     * @param name サポート処理名
     */
    @Override
    protected void processErrorEnd(JobStatus jobStatus, 
            BLogicResult blogicResult, String name) {
        TransactionalJobStatus transactionalJobStatus = 
            (TransactionalJobStatus) jobStatus;
        // ロールバック
        if (transactionalJobStatus.useSavepoint()) {
            transactionalJobStatus.rollbackToSavepoint();
        } else {
            transactionalJobStatus.rollback();
        }
    
        super.processErrorEnd(jobStatus, blogicResult, name);
    }
}
