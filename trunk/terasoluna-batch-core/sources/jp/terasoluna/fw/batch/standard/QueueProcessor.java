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
import jp.terasoluna.fw.batch.core.SupportProcessor;
import jp.terasoluna.fw.batch.core.WorkQueue;
import jp.terasoluna.fw.batch.core.WorkUnit;
import jp.terasoluna.fw.batch.core.Workable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * キュー処理用クラス。
 *
 * <p>キューから要素を取り出し、取り出したそれぞれの要素をパラメータとしてワーカ
 * ーを起動する。</p>
 * 
 * <p>キューの要素の処理をはじめる前には、設定された前処理プロセッサを起動する。
 * 前処理プロセッサを起動する際には、キューの先頭要素の <code>JobContext</code> 
 * が使用される。</p>
 * 
 * <p>すべてのキューの要素の処理が終わった後に、設定された後処理プロセッサを起動
 * する。後処理プロセッサを起動する際には、キューの最終要素の <code>JobContext
 * </code> が使用される。</p>
 *
 * <p>キューに要素が一個も存在しなかった場合には、前処理プロセッサ、後処理プロセ
 * ッサの両方とも起動されない。</p>
 *
 */
public class QueueProcessor {

    /**
     * ログインスタンス。
     */
    private static Log log = LogFactory.getLog(QueueProcessor.class);

    /**
     * ワーカー。
     */
    private Workable<WorkUnit> worker = null;

    /**
     * キュープロセッサの名称。
     */
    private String name = null;

    /**
     * 前処理プロセッサ。キューの要素を処理する前に起動される。
     */
    private SupportProcessor preProcessor = null;

    /**
     * 後処理プロセッサ。キューの要素をすべて処理した後で起動される。
     */
    private SupportProcessor postProcessor = null;

    /**
     * キューの処理を行う。
     *
     * @param workQueue キュー
     * @param jobStatus ジョブステータス
     */
    public void process(WorkQueue workQueue, JobStatus jobStatus) {
        printStartLog(jobStatus);
        
        //  前処理プロセッサの起動済みフラグ
        boolean donePreProcess = false;
        
        //　処理済みのキューの前要素(後処理で使用する最終キューの要素)
        WorkUnit preElement = null;
        
        while (true) {
            WorkUnit element = workQueue.take();
            
            if (element.isEndMark()) {
                if (preElement != null && postProcessor != null) {
                    postProcessor.process(preElement.getJobContext(),
                            jobStatus);
                }
                break;
            }

            if (!donePreProcess && preProcessor != null) {
                preProcessor.process(element.getJobContext(), jobStatus);
                if (!jobStatus.isContinue()) {
                    break;
                }

                donePreProcess = true;
            }
            
            // 本処理
            worker.work(element, jobStatus);
            if (!jobStatus.isContinue()) {
                break;
            }
            
            preElement = element;
        }

        printEndLog(jobStatus);
    }

    /**
     * ワーカーを設定する。
     *
     * @param worker ワーカー
     */
    public void setWorker(Workable<WorkUnit> worker) {
        this.worker = worker;
    }

    /**
     * 名称を設定する。
     *
     * @param name 名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 後処理プロセッサを設定する。
     *
     * @param postProcessor 後処理プロセッサ
     */
    public void setPostProcessor(SupportProcessor postProcessor) {
        this.postProcessor = postProcessor;
    }

    /**
     * 前処理プロセッサを設定する。
     *
     * @param preProcessor 前処理プロセッサ
     */
    public void setPreProcessor(SupportProcessor preProcessor) {
        this.preProcessor = preProcessor;
    }
    
    /**
     * QueueProcessorの開始ログを出力する。
     * 
     * @param jobStatus ジョブステータス
     */
    protected void printStartLog(JobStatus jobStatus) {
        if (log.isDebugEnabled()) {
            StringBuilder logStr = new StringBuilder();
            logStr.append("【START】");
            logStr.append(" [jobId=");
            logStr.append(jobStatus.getJobId());
            logStr.append("]");
            logStr.append(" [jobRequestNo=");
            logStr.append(jobStatus.getJobRequestNo());
            logStr.append("]");
            logStr.append(" [partitionNo=");
            logStr.append(jobStatus.getPartitionNo());
            logStr.append("]");
            logStr.append(" [QueueProcessorName=");
            logStr.append(name);
            logStr.append("]");
            logStr.append(" [JobState=");
            logStr.append(jobStatus.getJobState());
            logStr.append("]");
            log.debug(logStr.toString());
        }
    }

    /**
     * QueueProcessorの終了ログを出力する。
     * 
     * @param jobStatus ジョブステータス
     */
    protected void printEndLog(JobStatus jobStatus) {
        if (log.isDebugEnabled()) {
            StringBuilder logStr = new StringBuilder();
            logStr.append("【 END 】");
            logStr.append(" [jobId=");
            logStr.append(jobStatus.getJobId());
            logStr.append("]");
            logStr.append(" [jobRequestNo=");
            logStr.append(jobStatus.getJobRequestNo());
            logStr.append("]");
            logStr.append(" [partitionNo=");
            logStr.append(jobStatus.getPartitionNo());
            logStr.append("]");
            logStr.append(" [QueueProcessorName=");
            logStr.append(name);
            logStr.append("]");
            logStr.append(" [JobState=");
            logStr.append(jobStatus.getJobState());
            logStr.append("]");
            log.debug(logStr.toString());
        }
    }
}
