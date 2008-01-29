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
 * �W���u�˗����擾�E�X�V�p�N���X�B
 * 
 * <li><code>jobControl-sqlMap.xml</code>�ɒ�`���ꂽ�ȉ���<code>SQL</code>�������s
 * ����B</li>
 * <pre><code>
 *     &lt;!-- �W���u�˗����擾 --&gt;
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
 *     &lt;!-- �W���u�˗����擾 --&gt;
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
 *     &lt;!-- �W���u�˗����X�V --&gt;
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
 *     &lt;!-- �W���u�I�����X�V --&gt;
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
     * SELECT�pDAO�B Spring��DI�@�\��p���Đݒ肷��B
     */
    private QueryDAO queryDAO = null;

    /**
     * UPDATE�pDAO�B Spring��DI�@�\��p���Đݒ肷��B
     */
    private UpdateDAO updateDAO = null;

    /**
     * �ujobControl-sqlMap.xml�v�ɒ�`���ꂽ�W���u�˗����擾�pSQL�L�[�B
     */
    private static final String GET_JOB_REQUEST_DATA = 
        "jobControl.SELECT_JOB_INFO";

    /**
     * �ujobControl-sqlMap.xml�v�ɒ�`���ꂽ�W���u�˗����擾�pSQL�L�[�B
     */
    private static final String GET_JOB_REQUEST_DATA_ALL =
        "jobControl.SELECT_JOB_INFO_ALL";

    /**
     * �ujobControl-sqlMap.xml�v�ɒ�`���ꂽ�W���u�J�n�����pSQL�L�[�B
     */
    private static final String SET_JOB_START = "jobControl.UPDATE_JOB_START";

    /**
     * �ujobControl-sqlMap.xml�v�ɒ�`���ꂽ�W���u�������ʍX�V�pSQL�L�[�B
     */
    private static final String SET_JOB_END = "jobControl.UPDATE_JOB_RESULT";

    /**
     * �W���u�˗���񌟍��p���\�b�h�B
     *
     * @return �W���u�˗����B
     */
    public JobInfo getJobRequestData() {
        return queryDAO.executeForObject(GET_JOB_REQUEST_DATA_ALL, null, 
                JobInfo.class);
    }

    /**
     * �W���u�˗���񌟍��p���\�b�h�B
     *
     * @param jobInfo �W���u�˗����̌��������B
     * @return �������ꂽ�W���u�˗����B
     */
    public JobInfo getJobRequestData(JobInfo jobInfo) {
        return queryDAO.executeForObject(GET_JOB_REQUEST_DATA, jobInfo, 
                JobInfo.class);
    }

    /**
     * �W���u�˗����́u�N���󋵁v�X�V�p���\�b�h�B
     *
     * @param jobInfo
     *            �X�V�Ώۂ̃W���u�˗����
     * @return int �X�V���ꂽ���R�[�h���B
     */
    public int updateJobStart(JobInfo jobInfo) {
        return updateDAO.execute(SET_JOB_START, jobInfo);
    }

    /**
     * �W���u�������ʂ̍X�V�����B
     *
     * @param jobInfo
     *            �X�V�Ώۂ̃W���u�˗����
     * @return int �X�V���ꂽ���R�[�h���B
     */
    public int updateJobEnd(JobInfo jobInfo) {
        return updateDAO.execute(SET_JOB_END, jobInfo);
    }

    /**
     * SELECT�pDAO��ݒ肷��B
     *
     * @param queryDAO SELECT�pDAO
     */
    public void setQueryDAO(QueryDAO queryDAO) {
        this.queryDAO = queryDAO;
    }

    /**
     * �X�V�pDAO��ݒ肷��B
     *
     * @param updateDAO �X�V�pDAO
     */
    public void setUpdateDAO(UpdateDAO updateDAO) {
        this.updateDAO = updateDAO;
    }
}
