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

package jp.terasoluna.fw.batch.commonj.init;

import jp.terasoluna.fw.batch.commonj.listener.WorkMapListener;
import jp.terasoluna.fw.batch.core.InitializeException;
import jp.terasoluna.fw.batch.init.JobRequestInfo;
import jp.terasoluna.fw.batch.openapi.JobContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import commonj.work.WorkException;
import commonj.work.WorkItem;
import commonj.work.WorkManager;

/**
 * CommonJ用子ジョブ起動クラス。<br>
 * 子ジョブ用のJobExecutorをWorkManagerにスケジュールする。
 * 
 * 
 */
public class ChildJobStarter {

    /**
     * ログインスタンス。
     */
    private static Log log = LogFactory.getLog(ChildJobStarter.class);

    /**
     * ワークマネージャへの作業依頼成功。
     */
    private static final int SCHDULE_SUCCESS = 0;
    
    /**
     * ワークマネージャへの作業依頼失敗。
     */
    private static final int SCHDULE_ERROR = -1;
    
    /**
     * ワークマネージャー。
     */
    private WorkManager workManager = null;

    /**
     * ワークリスナー。
     */
    private WorkMapListener workListener = null;

    /**
     * ジョブの実行。
     * 
     * @param args ジョブパラメータ
     * @param jobContext ジョブコンテキスト
     * @return 実行結果
     */
    public int execute(String[] args, JobContext jobContext) {
        if (workManager == null) {
            throw new InitializeException("WorkManager is required.");
        }
        if (workListener == null) {
            throw new InitializeException("WorkListener is required.");
        }
        JobRequestInfo jobInfo = new JobRequestInfo(args);
        jobInfo.init();
        ChildJobExecutor childJobExecutor = new ChildJobExecutor();
        childJobExecutor.setJobInfo(jobInfo);
        childJobExecutor.setJobContext(jobContext);
        try {
            WorkItem workItem = workManager.schedule(
                    childJobExecutor, workListener);
            workListener.addWork(workItem, childJobExecutor);
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage());
            return SCHDULE_ERROR;
        } catch (WorkException e) {
            log.error(e.getMessage());
            return SCHDULE_ERROR;
        }
        return SCHDULE_SUCCESS;
    }

    /**
     * ワークリスナーの設定。
     * 
     * @param workListener ワークリスナー
     */
    public void setWorkListener(WorkMapListener workListener) {
        this.workListener = workListener;
    }

    /**
     * ワークマネージャーの設定。
     * 
     * @param workManager ワークマネージャー
     */
    public void setWorkManager(WorkManager workManager) {
        this.workManager = workManager;
    }

}
