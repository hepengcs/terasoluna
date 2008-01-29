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

import jp.terasoluna.fw.batch.core.Chunk;
import jp.terasoluna.fw.batch.core.JobStatus;
import jp.terasoluna.fw.batch.core.Workable;

/**
 * トランザクション処理を行うワーカー実装クラス。
 * 
 * <p><code>jobWorker</code> 属性に設定されたワーカの呼び出しをトランザクション
 * で実行する。</p>
 * 
 * <p>トランザクション制御を行うため、ジョブステータスとして 
 * <code>TransactionalJobStatus</code>を使用する必要がある。</p>
 * 
 */
public class TransactionalWorker implements Workable<Chunk> {

    /**
     * ジョブワーカー。
     */
    private Workable<Chunk> jobWorker = null;
    
    /**
     * ワーカーの処理を行う。
     * 
     * @param chunk チャンク
     * @param jobStatus トランザクショナルジョブステータス
     */
    public void work(Chunk chunk, JobStatus jobStatus) {
        
        TransactionalJobStatus transactionalJobStatus = 
            (TransactionalJobStatus) jobStatus;
        transactionalJobStatus.beginTransaction();
        
        // セーブポイントを有効時に最初のセーブポイントを作成
        if (transactionalJobStatus.useSavepoint()) {
            transactionalJobStatus.releaseAndCreateSavepoint();
        }

        jobWorker.work(chunk, transactionalJobStatus);
        
        //強制終了
        if (jobStatus.isShutdownImmediate()) {
            jobStatus.suspend();
            transactionalJobStatus.rollback();
            return;
        }

        if (jobStatus.isExecuting()
                || jobStatus.getJobState() == JobStatus.STATE.ENDING_NORMALLY
                || jobStatus.isShutdownGraceful()) {
            transactionalJobStatus.commit();
        } else {
            transactionalJobStatus.rollback();
            // セーブポイント有効時は最後にコミットする
            if (transactionalJobStatus.useSavepoint()) {
                transactionalJobStatus.commit();
            }
        }

        //中断終了
        if (jobStatus.isShutdownGraceful()) {
            jobStatus.suspend();
            return;
        }
    }

    /**
     * ジョブワーカーを設定する。
     * 
     * @param jobWorker ジョブワーカー
     */
    public void setJobWorker(Workable<Chunk> jobWorker) {
        this.jobWorker = jobWorker;
    }
}
