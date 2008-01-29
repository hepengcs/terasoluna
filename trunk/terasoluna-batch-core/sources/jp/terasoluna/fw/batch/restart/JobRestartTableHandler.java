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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import jp.terasoluna.fw.dao.QueryDAO;
import jp.terasoluna.fw.dao.UpdateDAO;
import jp.terasoluna.fw.batch.core.JobException;
import jp.terasoluna.fw.batch.core.JobStatus;
import jp.terasoluna.fw.batch.openapi.JobContext;

/**
 * リスタート情報取得・更新用クラス。
 * 
 * <li><code>jobRestart-sqlMap.xml</code>に定義された以下の<code>SQL</code>文を実行する。</li>
 * 
 * <pre><code>
 *         &lt;!-- リスタート情報更新（更新） --&gt;
 *         &lt;update id=&quot;UPDATE_JOB_RESTART_POINT&quot; parameterClass=&quot;jobRestartInfo&quot;&gt;
 *             UPDATE RESTART_CONTROL SET 
 *                 RESTART_POINT = #restartPoint# , 
 *                 JOB_CONTEXT   = #jobContext#, 
 *                 STATE         = #state#, 
 *                 UPDATE_TIME   = current_timestamp 
 *             WHERE REQUEST_NO  = #requestNo# 
 *             AND  JOB_ID       = #jobId#  
 *             AND  PARTITION_NO = #partitionNo#
 *         &lt;/update&gt;
 *     
 *         &lt;!-- リスタート情報更新（新規追加） --&gt;
 *         &lt;insert id=&quot;INSERT_JOB_RESTART_POINT&quot; parameterClass=&quot;jobRestartInfo&quot;&gt;
 *             INSERT INTO RESTART_CONTROL 
 *                   (REQUEST_NO   , JOB_ID   , PARTITION_NO   , PARTITION_KEY   , RESTART_POINT   , JOB_CONTEXT   , STATE , UPDATE_TIME       , REGISTER_TIME) 
 *             VALUES(#requestNo# , #jobId# , #partitionNo# , #partitionKey# , #restartPoint# , #jobContext# , #state#   , current_timestamp , current_timestamp) 
 *         &lt;/insert&gt;
 *     
 *      ※リスタート情報クリア用SQLは以下の二つから選択することが出来る。
 *      ※デフォルトSQLは対象データ削除である。
 *         &lt;!-- リスタート情報クリア（処理完了後対象データの状態更新） --&gt;
 *         &lt;update id=&quot;UPDATE_JOB_RESTART_CLEAR&quot; parameterClass=&quot;jobRestartInfo&quot;&gt;
 *             UPDATE RESTART_CONTROL SET 
 *                 STATE       = #state# , 
 *                 UPDATE_TIME = current_timestamp 
 *             WHERE REQUEST_NO   = #requestNo# 
 *             AND   JOB_ID       = #jobId#  
 *             AND   PARTITION_NO = #partitionNo#
 *         &lt;/update&gt;
 *         &lt;!-- リスタート情報クリア（処理完了後対象データ削除） --&gt;
 *         &lt;delete id=&quot;UPDATE_JOB_RESTART_CLEAR&quot; parameterClass=&quot;jobRestartInfo&quot;&gt;
 *             DELETE FROM RESTART_CONTROL 
 *             WHERE REQUEST_NO   = #requestNo# 
 *             AND   JOB_ID       = #jobId#  
 *             AND   PARTITION_NO = #partitionNo#
 *         &lt;/delete&gt;
 *     
 *         &lt;!-- リスタート依頼情報取得 --&gt;
 *         &lt;select id=&quot;SELECT_JOB_RESTART_INFO&quot; parameterClass=&quot;jobRestartInfo&quot; resultClass=&quot;jobRestartInfo&quot;&gt;
 *             SELECT REQUEST_NO AS requestNo, 
 *                    JOB_ID as jobId, 
 *                    PARTITION_KEY as partitionKey, 
 *                    RESTART_POINT as restartPoint, 
 *                    JOB_CONTEXT as jobContext, 
 *                    STATE as state, 
 *                    UPDATE_TIME,
 *                    REGISTER_TIME as registerTime 
 *             FROM RESTART_CONTROL 
 *             WHERE REQUEST_NO    = #requestNo# 
 *             AND   JOB_ID        = #jobId#  
 *             AND   PARTITION_NO  = #partitionNo#
 *             AND   STATE         = #state#
 *         &lt;/select&gt;
 * </code></pre>
 * 
 */
public class JobRestartTableHandler {

    
    /**
     * リスタート処理状況：処理完了。
     */
    private static final String JOB_END_STATE = "2";

    /**
     * リスタート処理状況：起動中。
     */
    private static final String JOB_START_STATE = "1";

    /**
     * リスタート情報更新（新規追加）用SQLキー。
     */
    private static final String INSERT_JOB_RESTART_POINT = 
        "jobRestart.INSERT_JOB_RESTART_POINT";

    /**
     * リスタート情報更新（更新）用SQLキー。
     */
    private static final String UPDATE_JOB_RESTART_POINT = 
        "jobRestart.UPDATE_JOB_RESTART_POINT";

    /**
     * ジョブリスタート情報格納用Object。 SpringのDI機能を用いて設定する。
     */
    private JobRestartInfoFactory jobRestartInfoFactory = null;

    /**
     * SELECT用DAO。 SpringのDI機能を用いて設定する。
     */
    private QueryDAO queryDAO = null;

    /**
     * UPDATE用DAO。 SpringのDI機能を用いて設定する。
     */
    private UpdateDAO updateDAO = null;

    /**
     * ジョブコンテキスト復元処理。
     * 
     * @param jobContext ジョブコンテキスト
     * @param jobStatus ジョブステータス
     * @return 復元されたジョブコンテキスト
     */
    public JobContext getRestartJobContext(JobContext jobContext,
            JobStatus jobStatus) {
        // ジョブリスタート情報取得
        JobRestartInfo jobRestartInfo = jobRestartInfoFactory.getInstance();
        initJobRestartInfo(jobRestartInfo, jobContext, jobStatus);
        // 復元対象は起動中の中断されたデータのみ
        jobRestartInfo.setState(JOB_START_STATE);
        // DBからリスタート情報取得
        JobRestartInfo restoreJobRestartInfo = queryDAO.executeForObject(
                "jobRestart.SELECT_JOB_RESTART_INFO", jobRestartInfo,
                JobRestartInfo.class);

        if (restoreJobRestartInfo == null) {
            // リスタート処理ではない。
            jobContext.setRestarted(false);
            jobStatus.setJobState(jobStatus.getJobState());
            return jobContext;
        } else if (JOB_END_STATE.equals(restoreJobRestartInfo.getState())) {
            // 終了した処理。
            jobStatus.setJobState(JobStatus.STATE.ENDING_NORMALLY);
            return jobContext;
        }
        
        JobContext newjobContext = 
            changeByteToJobContext(restoreJobRestartInfo.getJobContext());
        newjobContext.setRestartPoint(restoreJobRestartInfo.getRestartPoint());
        jobStatus.setRestartPoint(restoreJobRestartInfo.getRestartPoint());
        // リスタート処理である。
        newjobContext.setRestarted(true);
        jobStatus.setJobState(JobStatus.STATE.RESTARTED);
        
        return newjobContext;
    }

    /**
     * リスタートポイント更新処理。
     * 
     * @param jobStatus
     *            ジョブの状況
     * @param jobContext
     *            ジョブコンテキスト
     */
    public void registerRestartPoint(JobContext jobContext,
            JobStatus jobStatus) {
        if (!jobStatus.isRestartable()) {
            return;
        }

        JobRestartInfo jobRestartInfo = jobRestartInfoFactory.getInstance();
        initJobRestartInfo(jobRestartInfo, jobContext, jobStatus);
        jobRestartInfo.setRestartPoint(jobStatus.getRestartPoint());
        jobRestartInfo.setState(JOB_START_STATE);

        // コミット数が０の場合はリスタートポイント新規追加
        if (queryDAO.executeForObject("jobRestart.SELECT_JOB_RESTART_INFO",
                jobRestartInfo, JobRestartInfo.class) != null) {
            updateRestartPoint(jobRestartInfo);
        } else {
            // リスタート情報の追加
            insertRestartPoint(jobRestartInfo);
        }
    }

    /**
     * ジョブリスタート情報格納用実装クラスのファクトリを設定する。
     * 
     * @param jobRestartInfoFactory
     *            ジョブリスタート情報格納用クラスのファクトリ
     */
    public void setJobRestartInfoFactory(
            JobRestartInfoFactory jobRestartInfoFactory) {
        this.jobRestartInfoFactory = jobRestartInfoFactory;
    }

    /**
     * SELECT用DAOのセッター。
     * 
     * @param queryDAO
     *            SELECT用DAO
     */
    public void setQueryDAO(QueryDAO queryDAO) {
        this.queryDAO = queryDAO;
    }

    /**
     * 更新用DAOのセッター。
     * 
     * @param updateDAO
     *            更新用DAO
     */
    public void setUpdateDAO(UpdateDAO updateDAO) {
        this.updateDAO = updateDAO;
    }

    /**
     * リスタート情報のクリア処理用メソッド(レコード更新)。
     * 
     * @param jobStatus ジョブ処理状況
     * @param sqlkey リスタート情報のクリア用SQLキー
     * @return 更新されたレコード数。
     */
    public int restartPointClear(JobStatus jobStatus, String sqlkey) {
        JobRestartInfo jobRestartInfo = jobRestartInfoFactory.getInstance();
        jobRestartInfo.setRequestNo(jobStatus.getJobRequestNo());
        jobRestartInfo.setJobId(jobStatus.getJobId());
        jobRestartInfo.setPartitionNo(jobStatus.getPartitionNo());
        jobRestartInfo.setState(JOB_END_STATE);

        // リスタート情報のクリア
        return updateDAO.execute(sqlkey, jobRestartInfo);
    }

    /**
     * ジョブ処理状況、ジョブコンテキストのデータを更新対象のリスタート情報へ
     * 設定する。<BR>
     * 以下の情報を設定する。<BR>
     * ジョブリクエスト番号、ジョブID、パーティション番号、 パーティションキー、
     * ジョブコンテキスト（byte[]に変換し設定）
     * 
     * 
     * @param jobRestartInfo
     *            更新対象のリスタート情報
     * @param jobContext
     *            ジョブコンテキスト
     * @param jobStatus
     *            ジョブ処理状況
     */
    protected void initJobRestartInfo(JobRestartInfo jobRestartInfo,
            JobContext jobContext, JobStatus jobStatus) {
        if (jobContext == null) {
            throw new IllegalArgumentException("JobContext is NULL");
        }
        jobRestartInfo.setRequestNo(jobStatus.getJobRequestNo());
        jobRestartInfo.setJobId(jobStatus.getJobId());
        jobRestartInfo.setPartitionNo(jobStatus.getPartitionNo());
        jobRestartInfo.setPartitionKey(jobStatus.getPartitionKey());
        jobRestartInfo.setJobContext(changeJobContextToByte(jobContext));
    }

    /**
     * ジョブコンテキストオブジェクトを<code>byte</code>配列に変換する。<BR>
     * 
     * @param jobContext
     *            ジョブコンテキスト
     * @return ジョブコンテキストのバイト配列。
     */
    protected byte[] changeJobContextToByte(JobContext jobContext) {
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        ObjectOutputStream out = null;
        try {
            out = new ObjectOutputStream(byteOut);
            out.writeObject(jobContext);
        } catch (IOException e) {
            throw new JobException(e);
        }
        return byteOut.toByteArray();
    }
        
    /**
     * <code>byte</code>配列をジョブコンテキストオブジェクトに変換する。<BR>
     * 
     * @param obj 変換対象のObject
     * @return ジョブコンテキスト。
     */
    protected JobContext changeByteToJobContext(byte[] obj) {
        try {
            ByteArrayInputStream byteIn = new ByteArrayInputStream(obj);
            ObjectInputStream in = new ObjectInputStream(byteIn);
            return (JobContext) in.readObject();
        } catch (IOException e) {
            throw new JobException(e);
        } catch (ClassNotFoundException e) {
            throw new JobException(e);
        }
    }

    /**
     * リスタート情報の「起動状況」更新用メソッド。
     * 
     * @param jobRestartInfo
     *            更新対象のリスタート情報
     * @return int 更新されたレコード数。
     */
    private int insertRestartPoint(JobRestartInfo jobRestartInfo) {
        return updateDAO.execute(INSERT_JOB_RESTART_POINT, jobRestartInfo);
    }

    /**
     * リスタート情報の「起動状況」更新用メソッド。
     * 
     * @param jobRestartInfo
     *            更新対象のリスタート情報
     * @return int 更新されたレコード数。
     */
    private int updateRestartPoint(JobRestartInfo jobRestartInfo) {
        return updateDAO.execute(UPDATE_JOB_RESTART_POINT, jobRestartInfo);
    }
}
