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

package jp.terasoluna.fw.batch.init;

import jp.terasoluna.fw.dao.QueryDAO;
import jp.terasoluna.fw.dao.UpdateDAO;

/**
 * ジョブ依頼情報取得・更新用クラス。
 * 
 * <li><code>jobControl-sqlMap.xml</code>に定義された以下の<code>SQL</code>文を実行
 * する。</li>
 * <pre><code>
 *     &lt;!-- ジョブ依頼情報取得 --&gt;
 *    &lt;select id="SELECT_JOB_INFO_ALL" resultClass="jobControlInfo"&gt;
 *        SELECT 
 *            REQUEST_NO    AS jobRequestNo , 
 *            JOB_ID        AS jobId , 
 *            JOB_FILE      AS jobDiscriptorPath , 
 *            PARAMETER     AS jobParameters, 
 *            STATE         AS jobState, 
 *            END_CODE      AS jobExitCode, 
 *            UPDATE_TIME   AS updateTime, 
 *            REGISTER_TIME AS registerTime
 *        FROM JOB_CONTROL 
 *        WHERE REQUEST_NO = ( 
 *            SELECT 
 *                MIN(REQUEST_NO) 
 *            FROM 
 *                JOB_CONTROL 
 *            WHERE 
 *                STATE = '0'
 *            )  
 *    &lt;/select&gt;
 *
 *     &lt;!-- ジョブ依頼情報取得 --&gt;
 *     &lt;select id="SELECT_JOB_INFO"  parameterClass="jobControlInfo" 
 *     resultClass="jobControlInfo"&gt;
 *        SELECT 
 *            REQUEST_NO    AS jobRequestNo , 
 *            JOB_ID        AS jobId , 
 *            JOB_FILE      AS jobDiscriptorPath , 
 *            PARAMETER     AS jobParameters, 
 *            STATE         AS jobState, 
 *             END_CODE      AS jobExitCode, 
 *            UPDATE_TIME   AS updateTime, 
 *            REGISTER_TIME AS registerTime
 *        FROM JOB_CONTROL 
 *        WHERE REQUEST_NO = ( 
 *            SELECT 
 *                MIN(REQUEST_NO) 
 *            FROM 
 *                JOB_CONTROL 
 *            WHERE 
 *                STATE = '0' AND
 *                REQUEST_NO &gt; #jobRequestNo#
 *            )  
 *     &lt;/select&gt;
 *     
 *     &lt;!-- ジョブ依頼情報更新 --&gt;
 *     &lt;update id="UPDATE_JOB_START" parameterClass="jobControlInfo"&gt;
 *        UPDATE JOB_CONTROL SET 
 *            STATE       = '1' , 
 *            UPDATE_TIME = current_timestamp , 
 *            START_TIME  = current_timestamp 
 *        WHERE 
 *            REQUEST_NO    = #jobRequestNo#     AND
 *            JOB_ID        = #jobId#            AND
 *            JOB_FILE      = #jobDiscriptorPath#  AND
 *            STATE         = '0'  
 *     &lt;/update&gt;
 * 
 *     &lt;!-- ジョブ終了情報更新 --&gt;
 *     &lt;update id="UPDATE_JOB_RESULT" parameterClass="jobControlInfo"&gt;
 *         UPDATE JOB_CONTROL SET 
 *             STATE       = #jobState# , 
 *             END_CODE    = #jobExitCode# ,
 *             UPDATE_TIME = current_timestamp 
 *         WHERE 
 *             REQUEST_NO = #jobRequestNo#
 *     &lt;/update&gt;
 *</code></pre>
 * 
 * 
 *
 */
public class JobControlTableHandlerImpl implements JobControlTableHandler {


    /**
     * SELECT用DAO。 SpringのDI機能を用いて設定する。
     */
    private QueryDAO queryDAO = null;

    /**
     * UPDATE用DAO。 SpringのDI機能を用いて設定する。
     */
    private UpdateDAO updateDAO = null;

    /**
     * 「jobControl-sqlMap.xml」に定義されたジョブ依頼情報取得用SQLキー。
     */
    private static final String GET_JOB_REQUEST_DATA = 
        "jobControl.SELECT_JOB_INFO";

    /**
     * 「jobControl-sqlMap.xml」に定義されたジョブ依頼情報取得用SQLキー。
     */
    private static final String GET_JOB_REQUEST_DATA_ALL =
        "jobControl.SELECT_JOB_INFO_ALL";

    /**
     * 「jobControl-sqlMap.xml」に定義されたジョブ開始処理用SQLキー。
     */
    private static final String SET_JOB_START = "jobControl.UPDATE_JOB_START";

    /**
     * 「jobControl-sqlMap.xml」に定義されたジョブ処理結果更新用SQLキー。
     */
    private static final String SET_JOB_END = "jobControl.UPDATE_JOB_RESULT";

    /**
     * ジョブ依頼情報検索用メソッド。
     *
     * @return ジョブ依頼情報。
     */
    public JobInfo getJobRequestData() {
        return queryDAO.executeForObject(GET_JOB_REQUEST_DATA_ALL, null, 
                JobInfo.class);
    }

    /**
     * ジョブ依頼情報検索用メソッド。
     *
     * @param jobInfo ジョブ依頼情報の検索条件。
     * @return 検索されたジョブ依頼情報。
     */
    public JobInfo getJobRequestData(JobInfo jobInfo) {
        return queryDAO.executeForObject(GET_JOB_REQUEST_DATA, jobInfo, 
                JobInfo.class);
    }

    /**
     * ジョブ依頼情報の「起動状況」更新用メソッド。
     *
     * @param jobInfo
     *            更新対象のジョブ依頼情報
     * @return int 更新されたレコード数。
     */
    public int updateJobStart(JobInfo jobInfo) {
        return updateDAO.execute(SET_JOB_START, jobInfo);
    }

    /**
     * ジョブ処理結果の更新処理。
     *
     * @param jobInfo
     *            更新対象のジョブ依頼情報
     * @return int 更新されたレコード数。
     */
    public int updateJobEnd(JobInfo jobInfo) {
        return updateDAO.execute(SET_JOB_END, jobInfo);
    }

    /**
     * SELECT用DAOを設定する。
     *
     * @param queryDAO SELECT用DAO
     */
    public void setQueryDAO(QueryDAO queryDAO) {
        this.queryDAO = queryDAO;
    }

    /**
     * 更新用DAOを設定する。
     *
     * @param updateDAO 更新用DAO
     */
    public void setUpdateDAO(UpdateDAO updateDAO) {
        this.updateDAO = updateDAO;
    }
}
