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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import commonj.work.WorkException;
import commonj.work.WorkItem;
import commonj.work.WorkManager;

/**
 * CommonJ用ジョブ起動クラス。
 * 
 * 
 */
public class JobStarterImpl implements JobStarter {

    /**
     * ログインスタンス。
     */
    private static Log log = LogFactory.getLog(JobStarterImpl.class);

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
     * @param jobId ジョブＩＤ
     * @param beanFileName ジョブBean定義ファイル
     * @param args ジョブパラメータ
     * @return 実行結果
     */
    public int execute(String jobId, String beanFileName, String[] args) {
        if (workManager == null) {
            throw new InitializeException("WorkManager is required.");
        }
        if (workListener == null) {
            throw new InitializeException("WorkListener is required.");
        }
        
        // 引数を１つの配列にまとめる
        String[] newArgs = new String[args.length + 2];
        newArgs[0] = jobId;
        newArgs[1] = beanFileName;
        System.arraycopy(args, 0, newArgs, 2, args.length);
        
        JobRequestInfo jobInfo = new JobRequestInfo(newArgs);
        jobInfo.init();
        JobExecutor jobExecutor = new JobExecutor();
        jobExecutor.setJobInfo(jobInfo);
        try {
            WorkItem workItem = workManager.schedule(jobExecutor, workListener);
            workListener.addWork(workItem, jobExecutor);
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
