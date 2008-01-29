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

import jp.terasoluna.fw.batch.core.JobStatus;
import jp.terasoluna.fw.batch.init.JobInfo;
import jp.terasoluna.fw.batch.init.JobRequestInfo;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import commonj.work.Work;

/**
 * CommonJ用ジョブ起動クラス。<br>
 * スレッド管理をワークマネージャに設定をする。<br>
 * commonj.worker.Workをimplmentすることで、ワークマネージャからの起動に対応する。
 * 
 * 
 */
public class JobExecutor extends
        jp.terasoluna.fw.batch.springsupport.init.JobExecutor
        implements Work {

    /**
     * CommonJ用Bean定義ファイル名。
     */
    private static final String COMMONJ_DEFINITION_NAME 
            = "/common/WorkManagerTaskContext-batch.xml";

    /**
     * ジョブ依頼情報。
     */
    private JobRequestInfo jobInfo = null;

    /**
     * ジョブステータス。
     */
    protected JobStatus jobStatus = null;

    /**
     * コンストラクタ。
     * 
     * <p>
     * <code>FrameworkFactory</code> 初期化処理を行う。
     * </p>
     * 
     */
    protected JobExecutor() {
        super();
    }

    /**
     * コンストラクタ。(非同期ジョブ起動用)
     * 
     * <p>
     * <code>FrameworkFactory</code> 初期化処理を行う。
     * </p>
     * 
     * @param beanFileName Bean定義ファイル名
     */
    protected JobExecutor(String[] beanFileName) {
        super(beanFileName);
    }

    /**
     * ジョブを実行する。<BR>
     * ジョブ依頼情報を用いて指定されたジョブを起動する。<BR>
     * 
     * @param jobInfo ジョブ依頼情報
     * @return ジョブ実行結果
     */
    @Override
    protected JobStatus execute(JobInfo jobInfo) {
        return super.execute(jobInfo);
    }

    /**
     * ジョブ毎の<code>BeanFactory</code>を取得するメソッド。<BR>
     * 生成された<code>BeanFactory</code>はキャッシュし再利用される。
     * 
     * @param jobInfo ジョブ依頼情報
     * @return ジョブ毎の<code>BeanFactory</code>
     */
    @Override
    protected synchronized ConfigurableApplicationContext getJobBeanFactory(
            JobInfo jobInfo) {
        ConfigurableApplicationContext jobFactory = null;
        if (isAsync()) {
            jobFactory = super.getJobBeanFactory(jobInfo);
        } else {
            jobFactory = new ClassPathXmlApplicationContext(new String[] {
                    jobInfo.getJobDiscriptorPath(),
                    DATA_ACCESS_CONTEXT_DEFINITION_NAME,
                    COMMONJ_DEFINITION_NAME, VALIDATE_DEFINITION_NAME }, this
                    .getFrameworkFactory());
        }

        return jobFactory;
    }

    /**
     * ジョブ依頼情報の取得。
     * 
     * @return jobInfo ジョブ依頼情報
     */
    public JobRequestInfo getJobInfo() {
        return jobInfo;
    }

    /**
     * ジョブ依頼情報の設定。
     * 
     * @param jobInfo ジョブ依頼情報
     */
    public void setJobInfo(JobRequestInfo jobInfo) {
        this.jobInfo = jobInfo;
    }

    /**
     * ジョブステータスの取得。
     * 
     * @return jobStatus ジョブステータス
     */
    public JobStatus getJobStatus() {
        return jobStatus;
    }

    /**
     * Workオブジェクトのライフスタイルを決定する。<br>
     * falseを返す：この場合、非デーモン型のWorkオブジェクトとなる。 
     * 同オブジェクトは呼び出し元のサーブレットのリクエスト処理や
     * EJBコンポーネントのビジネス・メソッド処理が終了するまで存続する。<br>
     * trueを返す：この場合、デーモン型のWorkオブジェクトとなる。<br>
     * 同オブジェクトは、呼び出し元のJava EEコンポーネントのライフ・サイクル
     * （サーブレットのリクエスト処理やEJBコンポーネントのビジネス・メソッド処理）
     * とは 関係なく存続することができる。<br>
     * ただし、永続性はないため、アプリケーション・サーバが終了すると、
     * 同オブジェクトは消失する。<br>
     * なお、メソッドisDamonがtrueを返す場合、WorkManagerオブジェクトが管理する。
     * スレッド・プールのスレッド・オブジェクトは使用されない。<br>
     * 
     * @return Workオブジェクトのライフスタイル
     * 
     * @see commonj.work.Work#isDaemon()
     */
    public boolean isDaemon() {
        return false;
    }

    /**
     * ワークの終了時の処理。
     * 
     * @see commonj.work.Work#release()
     */
    public void release() {
    }

    /**
     * 非同期並列実行を行う。
     */
    public void run() {
        try {
            jobStatus = this.execute(jobInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
