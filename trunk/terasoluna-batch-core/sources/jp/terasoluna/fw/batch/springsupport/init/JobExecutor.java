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

package jp.terasoluna.fw.batch.springsupport.init;

import java.util.HashMap;
import java.util.Map;

import jp.terasoluna.fw.batch.core.JobException;
import jp.terasoluna.fw.batch.core.JobStatus;
import jp.terasoluna.fw.batch.core.JobStatusSummarizer;
import jp.terasoluna.fw.batch.core.SupportProcessor;
import jp.terasoluna.fw.batch.core.WorkUnit;
import jp.terasoluna.fw.batch.core.Workable;
import jp.terasoluna.fw.batch.init.AbstractJobControlInfo;
import jp.terasoluna.fw.batch.init.EndFileChecker;
import jp.terasoluna.fw.batch.init.JobInfo;
import jp.terasoluna.fw.batch.openapi.JobContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * ジョブ起動用メインクラス。<BR>
 * ジョブ起動前の<code>JobBeanFactory</code>の生成や破棄処理を行う。<BR>
 * 生成された<code>JobBeanFactory</code>はキャッシュされ同じジョブが実行された
 * 場合は キャッシュした<code>JobBeanFactory</code>を返す。
 * 
 */
public class JobExecutor implements DisposableBean,
        Workable<AbstractJobControlInfo> {

    /**
     * ログインスタンス。
     */
    private static Log log = LogFactory.getLog(JobExecutor.class);

    /**
     * フレームワークBean定義ファイル名。
     */
    private static final String FRAMEWORK_BEAN_DEFINITION_NAME = 
        "/common/FrameworkBean.xml";
    
    /**
     * データアクセス用Bean定義ファイル名。
     */
    protected static final String DATA_ACCESS_CONTEXT_DEFINITION_NAME = 
        "/common/dataAccessContext-batch.xml";

    /**
     * スレッドプール用Bean定義ファイル名。
     */
    private static final String THREAD_POOL_DEFINITION_NAME = 
        "/common/ThreadPoolContext-batch.xml";

    /**
     * スレッドプール用Bean定義ファイル名。
     */
    protected static final String VALIDATE_DEFINITION_NAME = 
        "/common/ValidationContext-batch.xml";

    /**
     * キャッシュ有無用Bean名。
     */
    protected static final String USECACHE_NAME = "useCache";
    
    /**
     * ジョブコンテキストBean名。
     */
    private static final String JOBCONTEXT_NAME = "jobContext";
    
    /**
     * ジョブマネージャBean名。
     */
    private static final String JOBMANAGER_NAME = "jobManager";
    
    /**
     * ジョブ状況用Bean名。
     */
    protected static final String JOBSTATUS_NAME = "JobStatus";

    /**
     * モニター用ジョブ状況用Bean名。
     */
    protected static final String MONITORABLE_JOBSTATUS_NAME = 
        "MonitorableJobStatus";

    /**
     * ジョブ終了監視用Bean名。
     */
    private static final String ENDFILECHECKER_NAME = "endFileChecker";
    
    /**
     * ジョブ終了監視用Bean名。
     */
    protected static final String USE_MONITORABLE = "useMonitorable";
    
    /**
     * 処理状態のサマライザ。
     */
    protected static final String JOBSTATUS_SUMMARIZER = "JobStatusSummarizer";

    /**
     * デフォルト終了コード。
     */
    private static final String EXITCODE_MAP = "exitCodeMap";

    /**
     * フレームワークBeanFactory。
     */
    private ConfigurableApplicationContext frameworkFactory = null;

    /**
     * ジョブBeanFactoryのキャッシュ用Map。
     */
    private Map<String, ConfigurableApplicationContext> jobFactoryMap = 
        new HashMap<String, ConfigurableApplicationContext>();

    /**
     * 非同期起動有無フラグ。
     */
    private JobContext.START_TYPE async = JobContext.START_TYPE.SYNC;

    /**
     * ジョブ前処理用インスタンス。
     */
    private SupportProcessor preJobProcessor = null;

    /**
     * ジョブ後処理用インスタンス。
     */
    private SupportProcessor postJobProcessor = null;

    /**
     * デフォルトのジョブ終了コードのマップ。
     */
    private Map<String, String> jobExitCodeMap = null;
    
    /**
     * ジョブパラメータの区切り文字。
     */
    private String jobParametersSplitStr = null;
    
    /**
     * コンストラクタ。
     * 
     * <p><code>FrameworkFactory</code> 初期化処理を行う。</p>
     * 
     */
    protected JobExecutor() {
        frameworkFactory = new ClassPathXmlApplicationContext(
                FRAMEWORK_BEAN_DEFINITION_NAME);
        initDefaultJobExitCodeMap();
    }

    /**
     * コンストラクタ。(非同期ジョブ起動用)
     * 
     * <p><code>FrameworkFactory</code> 初期化処理を行う。</p>
     * 
     * @param beanFileName Bean定義ファイル
     */
    protected JobExecutor(String[] beanFileName) {
        frameworkFactory = new ClassPathXmlApplicationContext(beanFileName);
        initDefaultJobExitCodeMap();
    }
    
    /**
     * ジョブ毎の<code>BeanFactory</code>を取得するメソッド。<BR>
     * 生成された<code>BeanFactory</code>はキャッシュし再利用される。
     * 
     * @param jobInfo ジョブ依頼情報
     * @return ジョブ毎の<code>BeanFactory</code>
     */
    protected synchronized ConfigurableApplicationContext getJobBeanFactory(
            JobInfo jobInfo) {
        ConfigurableApplicationContext jobFactory = null;
        if (isAsync()) {
            // キャッシュされた情報があるか確認
            if (jobFactoryMap.containsKey(jobInfo.getJobDiscriptorPath())) {
                if (log.isDebugEnabled()) {
                    log.debug("get the cashed JobBeanFactory");
                }
                jobFactory = jobFactoryMap.get(jobInfo.getJobDiscriptorPath());
            } else {
                if (log.isDebugEnabled()) {
                    log.debug("get the newly created JobBeanFactory");
                }
                jobFactory = new ClassPathXmlApplicationContext(
                        new String[] { jobInfo.getJobDiscriptorPath() },
                        frameworkFactory);
                if ((Boolean) jobFactory.getBean(USECACHE_NAME)) {
                    // ジョブBeanFactoryのキャッシュ
                    jobFactoryMap.put(jobInfo.getJobDiscriptorPath(),
                            jobFactory);
                }
            }
        } else {
            jobFactory = new ClassPathXmlApplicationContext(new String[] {
                    jobInfo.getJobDiscriptorPath(),
                    DATA_ACCESS_CONTEXT_DEFINITION_NAME,
                    THREAD_POOL_DEFINITION_NAME, VALIDATE_DEFINITION_NAME },
                    frameworkFactory);
        }
        return jobFactory;
    }

    /**
     * 非同期ジョブ起動であるかを評価する。
     * 
     * @return 非同期ジョブの起動であれば<code>true</code>を返す。
     */
    protected boolean isAsync() {
        return async == JobContext.START_TYPE.ASYNC;
    }

    /**
     * ジョブマネージャを取得する。
     * 
     * @param jobFactory ジョブ毎の<code>BeanFactory</code>
     * @return ジョブマネージャ
     */
    @SuppressWarnings("unchecked")
    protected Workable<WorkUnit> getJobManager(
            ConfigurableApplicationContext jobFactory) {
        Workable<WorkUnit> jobManager = (Workable<WorkUnit>) jobFactory
                .getBean(JOBMANAGER_NAME);
        return jobManager;
    }

    /**
     * ジョブコンテキストの取得。<BR>
     * ジョブコンテキストを取得し、初期値を設定する。
     * 
     * @param jobFactory ジョブ毎の<code>BeanFactory</code>
     * @param jobInfo ジョブ依頼情報
     * @return ジョブコンテキスト
     */
    protected JobContext getJobContext(
            ConfigurableApplicationContext jobFactory, JobInfo jobInfo) {
        JobContext jobContext = (JobContext) jobFactory
                .getBean(JOBCONTEXT_NAME);
        jobContext.setStartType(async);
        jobContext.setPartitionNo(-1);
        jobContext.setJobId(jobInfo.getJobId());
        jobContext.setJobRequestNo(jobInfo.getJobRequestNo());
        jobContext.setParameter(jobInfo.getJobParameters());
        return jobContext;
    }

    /**
     * ジョブ処理状態格納用<code>JobStatus</code>を取得する。<BR>
     * <code>JobStatus</code>を取得し、初期値を設定する。
     * 
     * @param jobFactory ジョブ毎の<code>BeanFactory</code>
     * @param jobInfo ジョブ依頼情報
     * @return ジョブ処理状態格納用<code>JobStatus</code>
     */
    protected JobStatus getJobStatus(ConfigurableApplicationContext jobFactory,
            JobInfo jobInfo) {
        JobStatus jobStatus = null;
        if ((Boolean) jobFactory.getBean(USE_MONITORABLE)) {
            jobStatus = (JobStatus) jobFactory.getBean(
                    MONITORABLE_JOBSTATUS_NAME);
        } else {
            jobStatus = (JobStatus) jobFactory.getBean(JOBSTATUS_NAME);
        }

        jobStatus.setJobState(JobStatus.STATE.STARTED);
        jobStatus.setJobId(jobInfo.getJobId());
        jobStatus.setJobRequestNo(jobInfo.getJobRequestNo());
        return jobStatus;
    }

    /**
     * <code>WorkUnit</code>を生成し、ジョブコンテキストを設定する。<BR>
     * 
     * @param jobContext ジョブコンテキスト
     * @return <code>WorkUnit</code>
     */
    protected WorkUnit getWorkUnit(JobContext jobContext) {
        WorkUnit rootWorkQueueElement = new WorkUnit() {

            private JobContext workUnitJobContext;

            public boolean isEndMark() {
                return false;
            }

            public JobContext getJobContext() {
                return workUnitJobContext;
            }

            public void setJobContext(JobContext jobContext) {
                this.workUnitJobContext = jobContext;
            }
        };
        rootWorkQueueElement.setJobContext(jobContext);
        return rootWorkQueueElement;
    }

    /**
     * 終了ファイル監視スレッドの登録を行う。
     * 
     * @param jobStatus ジョブの処理状態
     * @param jobContext ジョブコンテキスト
     */
    protected void initEndFileChecker(JobStatus jobStatus,
            JobContext jobContext) {
        EndFileChecker endFileChecker
            = (EndFileChecker) frameworkFactory.getBean(ENDFILECHECKER_NAME);
        if (endFileChecker != null) {
            endFileChecker.addParentJobStatus(jobStatus, jobContext.getJobId(),
                    jobContext.getJobRequestNo());
        }
    }

    /**
     * ジョブを実行する。<BR>
     * ジョブ依頼情報を用いて指定されたジョブを起動する。<BR>
     * 
     * @param jobInfo ジョブ依頼情報
     * @return ジョブ実行結果
     */
    protected JobStatus execute(JobInfo jobInfo) {
        //開始ログ出力
        printStartLog(jobInfo);
        
        //ジョブ起動情報のチェック
        checkJobInfo(jobInfo);
        
        // ジョブBeanFactory
        ConfigurableApplicationContext jobFactory = getJobBeanFactory(jobInfo);

        // マネジャー取得
        Workable<WorkUnit> jobManager = getJobManager(jobFactory);

        // ジョブコンテキスト初期化
        JobContext jobContext = getJobContext(jobFactory, jobInfo);
        
        // ジョブ状態の初期化
        JobStatus jobStatus = getJobStatus(jobFactory, jobInfo);

        // 終了ファイル監視スレッドに登録
        initEndFileChecker(jobStatus, jobContext);
        
        WorkUnit rootWorkQueueElement = getWorkUnit(jobContext);
        
        jobManager.work(rootWorkQueueElement, jobStatus);

        ((JobStatusSummarizer) jobFactory.getBean(JOBSTATUS_SUMMARIZER))
                .summarize(jobStatus);

        setDefaultJobExitCode(jobStatus);
        
        if (!(Boolean) jobFactory.getBean(USECACHE_NAME)) {
            jobFactory.close();
        }

        //ジョブの終了ログ出力
        printEndLog(jobStatus);
        
        return jobStatus;
    }

    /**
     * ジョブの開始ログを出力する。
     * @param jobInfo ジョブ依頼情報
     */
    protected void printStartLog(JobInfo jobInfo) {
        if (log.isDebugEnabled()) {
            StringBuilder logStr = new StringBuilder();
            logStr.append("【START】");
            logStr.append(" [jobId=");
            logStr.append(jobInfo.getJobId());
            logStr.append("]");
            logStr.append(" [jobRequestNo=");
            logStr.append(jobInfo.getJobRequestNo());
            logStr.append("]");
            logStr.append(" [StartType=");
            logStr.append(async);
            logStr.append("]");
            log.debug(logStr.toString());
        }
    }

    /**
     * ジョブの開始ログを出力する。
     * @param jobStatus ジョブの処理状態
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
            logStr.append(" [StartType=");
            logStr.append(async);
            logStr.append("]");
            logStr.append(" [jobExitCode=");
            logStr.append(jobStatus.getJobExitCode());
            logStr.append("]");
            log.debug(logStr.toString());
        }
    }

    /**
     * 英数字文字例であるかをチェックする。
     * 
     * @param jobInfo チェック対象の文字例
     */
    private void checkJobInfo(JobInfo jobInfo) {
        for (char c : jobInfo.getJobId().toCharArray()) {
            if (!(('0' <= c && c <= '9')
                    || ('a' <= c && c <= 'z')
                    || ('A' <= c && c <= 'Z')
                    || c == '-'
                    || c == '_')
                    ) {
                throw new JobException("JobID should use only an "
                        + "alphanumeric character");
            } 
        }
    }


    /**
     * ジョブ終了コードを設定する。
     * 終了コードの設定がNullの場合、Bean定義の状態別終了コードを
     * デフォルト終了コードとして設定する。
     * 
     * @param jobStatus
     *            処理対象の処理状況
     */
    protected void setDefaultJobExitCode(JobStatus jobStatus) {
        if (jobStatus.getJobExitCode() != null) {
            return;
        }
        String code = jobExitCodeMap.get(jobStatus.getJobStateStr());
        jobStatus.setJobExitCode(Integer.valueOf(code));
    }

    /**
     * ジョブ終了コードのマップを初期化する。
     *
     */
    @SuppressWarnings("unchecked")
    private void initDefaultJobExitCodeMap() {
        jobExitCodeMap = 
            (Map<String, String>) frameworkFactory.getBean(EXITCODE_MAP);

        // 終了コードが設定されて無い場合はエラーとする。
        if (jobExitCodeMap == null || jobExitCodeMap.size() < 0) {
            throw new JobException("There is no setup of Default exitCodeMap");
        }
    }
    
    /**
     * 非同期型ジョブ起動時のワーカメソッド。
     * 
     * @param jobInfo ジョブ依頼情報
     * @param jobStatus ジョブステータス
     */
    public void work(AbstractJobControlInfo jobInfo, JobStatus jobStatus) {
        jobInfo.setJobParametersSplitStr(jobParametersSplitStr);
        JobStatus workJobStatus = jobStatus.getChild(new JobContext());
        workJobStatus.setJobState(JobStatus.STATE.STARTED);

        AsyncJobContext asyncJobContext = new AsyncJobContext(jobInfo);

        // ジョブ開始更新処理
        preJobProcessor.process(asyncJobContext, workJobStatus);

        if (workJobStatus.getJobState() != JobStatus.STATE.STARTED) {
            return;
        }

        JobStatus resultJobStatus = null;
        try {
            resultJobStatus = execute(jobInfo);
        } catch (RuntimeException e) {
            log.error(e.getMessage(), e);
            resultJobStatus = new JobStatus();
            resultJobStatus.setJobState(JobStatus.STATE.ENDING_ABNORMALLY);
            
            String exitCode = 
                jobExitCodeMap.get(resultJobStatus.getJobStateStr());
            resultJobStatus.setJobExitCode(Integer.valueOf(exitCode));
            
        }
        
        // ジョブ処理結果の設定
        jobInfo.setJobExitCode(
                String.valueOf(resultJobStatus.getJobExitCode()));
        // ジョブ終了状態設定
        jobInfo.setJobState(String.valueOf(
                resultJobStatus.getJobState().ordinal()));
        
        // ジョブ実行結果DB反映
        postJobProcessor.process(asyncJobContext, workJobStatus);
    }

    /**
     * 作成した<code>BeanFactory</code>の破棄処理を行う。<BR>
     * キャッシュした<code>JobBeanFactory</code>及ぶ<code>FrameworkBeanFactory
     * </code>を破棄する。
     * 
     */
    public void destroy() {
        // ジョブBeanFactroyの終了
        for (ConfigurableApplicationContext context : jobFactoryMap.values()) {
            context.close();
        }
        // フレームワークBeanFactroyの終了
        if (frameworkFactory != null) {
            frameworkFactory.close();
        }
    }

    /**
     * 非同期起動有無フラグを設定する。<BR>
     * Springからの値をインジェクションする。
     * 
     * @param async 非同期起動有無フラグ
     */
    public void setAsync(boolean async) {
        if (async) {
            this.async = JobContext.START_TYPE.ASYNC;
        } else {
            this.async = JobContext.START_TYPE.SYNC;
        }
    }

    /**
     * ジョブ起動後処理を設定する。
     * 
     * @param preJobProcessor ジョブ起動前処理
     */
    public void setPreJobProcessor(SupportProcessor preJobProcessor) {
        this.preJobProcessor = preJobProcessor;
    }

    /**
     * ジョブ起動前処理を設定する。
     * 
     * @param postJobProcessor ジョブ起動前処理
     */
    public void setPostJobProcessor(SupportProcessor postJobProcessor) {
        this.postJobProcessor = postJobProcessor;
    }

    /**
     * ジョブパラメータの区切り文字を設定する。
     * 
     * @param jobParametersSplitStr ジョブパラメータの区切り文字
     */
    public void setJobParametersSplitStr(String jobParametersSplitStr) {
        this.jobParametersSplitStr = jobParametersSplitStr;
    }
    
    /**
     * フレームワークBeanFactoryの取得
     * 
     * @return frameworkFactory フレームワークBeanFactory
     */
    protected ConfigurableApplicationContext getFrameworkFactory() {
        return frameworkFactory;
    }
}
