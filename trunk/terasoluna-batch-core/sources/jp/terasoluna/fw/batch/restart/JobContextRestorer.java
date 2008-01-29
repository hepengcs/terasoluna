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

package jp.terasoluna.fw.batch.restart;

import jp.terasoluna.fw.batch.core.JobException;
import jp.terasoluna.fw.batch.core.JobManager;
import jp.terasoluna.fw.batch.core.JobStatus;
import jp.terasoluna.fw.batch.core.WorkUnit;
import jp.terasoluna.fw.batch.core.Workable;
import jp.terasoluna.fw.batch.openapi.JobContext;
import jp.terasoluna.fw.batch.springsupport.init.JobStarter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>ジョブコンテクストの復元処理を行うクラス。</p>
 * 
 * <p>リスタート処理を行うジョブでは、ジョブマネージャが呼ばれる箇所に
 * このクラスを挟み込むように設定する。実際に処理を行うジョブマネージャは、
 * このクラスの <code>jobManager</code> プロパティに設定する。
 * このクラスでの <code>work()</code> メソッドでは、
 * リスタート処理を行った後で、実際の処理を <code>jobManager</code> 
 * プロパティに設定されたジョブマネージャに処理を委譲する。</p>
 * 
 * <p>ジョブ管理テーブルからのジョブコンテクストの復元は、<code>
 * jobRestartTableHandler</code> プロパティに設定されたジョブリスタート
 * テーブルハンドラによって行われる。</p>
 * 
 */
public class JobContextRestorer implements Workable<WorkUnit> {

    /**
     * ログインスタンス。
     */
    private static Log log = LogFactory.getLog(JobStarter.class);

    /**
     * ジョブマネージャ
     */
    private JobManager jobManager = null;
    
    /**
     * ジョブリスタートテーブルハンドラ
     */
    private JobRestartTableHandler jobRestartTableHandler = null;
    
    /**
     * ジョブマネージャを設定する。
     *
     * @param jobManager ジョブマネージャ
     */
    public void setJobManager(JobManager jobManager) {
        this.jobManager = jobManager;
    }

    /**
     * ジョブリスタートテーブルハンドラを設定する。
     *
     * @param jobRestartTableHandler ジョブリスタートテーブルハンドラ
     */
    public void setJobRestartTableHandler(
            JobRestartTableHandler jobRestartTableHandler) {
        this.jobRestartTableHandler = jobRestartTableHandler;
    }

    /**
     * ジョブコンテキスト用処理。
     * リスタート処理の場合はジョブリスタート管理テーブルからジョブコンテキスト
     * を取得し、前回ジョブ終了時点のジョブコンテキスト状態を復元する。
     * 
     * @param element 作業単位
     * @param jobStatus ジョブステータス
     */
    public void work(WorkUnit element, JobStatus jobStatus) {
        //ジョブコンテキストにリスタート有無設定
        JobContext jobContext = element.getJobContext();
        JobStatus childJobStatus = jobStatus.getChild(element.getJobContext());
        JobContext newJobContext = null;
        try {
            newJobContext = jobRestartTableHandler.getRestartJobContext(
                    jobContext, childJobStatus);
        } catch (JobException e) {
            log.error(e.getMessage(), e);
            childJobStatus.setJobState(JobStatus.STATE.ENDING_ABNORMALLY);
            return;
        }

        //終了済みジョブは実行しない。
        if (!childJobStatus.isContinue()) {
            return;
        }
        
        newJobContext.setRestartable(true);
        childJobStatus.setRestartable(true);

        element.setJobContext(newJobContext);
        
        RestartJobStatus restartJobStatus = 
            new RestartJobStatus(childJobStatus);
        jobManager.work(element, restartJobStatus);
    }
}
