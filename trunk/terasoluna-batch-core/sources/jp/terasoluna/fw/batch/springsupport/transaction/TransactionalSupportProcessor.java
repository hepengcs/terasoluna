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
import jp.terasoluna.fw.batch.core.SupportProcessor;
import jp.terasoluna.fw.batch.openapi.JobContext;

/**
 * トランザクション処理を行う <code>SupportProcessor</code> インタフェースの実装
 * クラス。
 * 
 * <p><code>supportProcessor</code> 属性に設定されたサポートプロセッサの処理を
 * トランザクションで行う。<code>supportProcessor</code> 属性のサポートプロセッ
 * サ起動前にトランザクションを開始し、サポートプロセッサ起動後のジョブステータ
 * スによってトランザクションのコミット／ロールバックを行う。</p>
 * 
 * <p><code>supportProcessor</code> 属性のサポートプロセッサ起動後のジョブステー
 * タスが、<code>JobStatus.STATE.STARTED</code>、<code>JobStatus.STATE.RESTARTED
 * </code>、<code>JobStatus.STATE.ENDING_NORMALLY</code>
 * である場合にはコミットし、そのほかの場合にはロールバックする。</p>
 * 
 * <p>トランザクション制御を行うため、ジョブステータスとして 
 * <code>TransactionalJobStatus</code>を使用する必要がある。</p>
 * 
 */
public class TransactionalSupportProcessor implements SupportProcessor {

    /**
     * サポートプロセッサ。
     */
    private SupportProcessor supportProcessor = null;
    
    /**
     * サポート処理を行う。
     *
     * @param jobContext ジョブコンテキスト
     * @param jobStatus トランザクショナルジョブステータス
     */
    public void process(JobContext jobContext, JobStatus jobStatus) {
        if (supportProcessor.canSkip()) {
            return;
        }

        // サポート処理用トランザクション開始
        TransactionalJobStatus transactionalJobStatus = 
            (TransactionalJobStatus) jobStatus;
        transactionalJobStatus.beginTransaction();

        // セーブポイントを有効時に最初のセーブポイントを作成
        if (transactionalJobStatus.useSavepoint()) {
            transactionalJobStatus.releaseAndCreateSavepoint();
        }

        supportProcessor.process(jobContext, jobStatus);
        
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
    }

    /**
     * サポートプロセッサを設定する。
     * 
     * @param supportProcessor サポートプロセッサ
     */
    public void setSupportProcessor(SupportProcessor supportProcessor) {
        this.supportProcessor = supportProcessor;
    }

    /**
     * ジョブ前処理がスキップできる場合には、<code>true</code> を返す。 
     * 
     * @return ジョブ前処理がスキップできる場合には、<code>true</code>
     */
    public boolean canSkip() {
        return supportProcessor.canSkip();
    }
}
