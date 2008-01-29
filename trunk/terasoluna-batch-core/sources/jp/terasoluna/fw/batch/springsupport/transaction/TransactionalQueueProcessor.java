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
import jp.terasoluna.fw.batch.core.QueueingException;
import jp.terasoluna.fw.batch.core.WorkQueue;
import jp.terasoluna.fw.batch.standard.QueueProcessor;

/**
 * キューの処理をトランザクションで行うキュープロセッサクラス。
 * 
 * <p>キューのすべての要素の処理を行った後で、ジョブステータスが
 * <code>JobStatus.STATE.STARTED</code>、<code>JobStatus.STATE.RESTARTED</code>
 * 、あるいは<code>JobStatus.STATE.ENDING_NORMALLY</code>
 * である場合にはコミットし、そのほかの場合にはロールバックする。</p>
 *
 * <p>トランザクション制御を行うため、ジョブステータスとして 
 * <code>TransactionalJobStatus</code>を使用する必要がある。</p>
 * 
 */
public class TransactionalQueueProcessor extends QueueProcessor {

    /**
     * キューの処理を行う。
     *
     * @param workQueue キュー
     * @param jobStatus トランザクショナルジョブステータス
     */
    @Override
    public void process(WorkQueue workQueue, JobStatus jobStatus) {
        
        TransactionalJobStatus transactionalJobStatus = 
            (TransactionalJobStatus) jobStatus;
        transactionalJobStatus.beginTransaction();
        
        try {
            super.process(workQueue, jobStatus);
        } catch (QueueingException e) {
            // 強制終了/中断終了のときはロールバック
            if (jobStatus.getJobState() == JobStatus.STATE.SUSPENDING) {
                transactionalJobStatus.rollback();
                return;
            } else {
                throw e;
            }
        }

        if (jobStatus.isExecuting()
                || jobStatus.getJobState() == JobStatus.STATE.ENDING_NORMALLY) {
            transactionalJobStatus.commit();
        } else {
            transactionalJobStatus.rollback();
        }
    }
}
