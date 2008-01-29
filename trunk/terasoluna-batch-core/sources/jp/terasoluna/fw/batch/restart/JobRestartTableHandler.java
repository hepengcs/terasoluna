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
 * ���X�^�[�g���擾�E�X�V�p�N���X�B
 * 
 * <li><code>jobRestart-sqlMap.xml</code>�ɒ�`���ꂽ�ȉ���<code>SQL</code>�������s����B</li>
 * 
 * <pre><code>
 *         &lt;!-- ���X�^�[�g���X�V�i�X�V�j --&gt;
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
 *         &lt;!-- ���X�^�[�g���X�V�i�V�K�ǉ��j --&gt;
 *         &lt;insert id=&quot;INSERT_JOB_RESTART_POINT&quot; parameterClass=&quot;jobRestartInfo&quot;&gt;
 *             INSERT INTO RESTART_CONTROL 
 *                   (REQUEST_NO   , JOB_ID   , PARTITION_NO   , PARTITION_KEY   , RESTART_POINT   , JOB_CONTEXT   , STATE , UPDATE_TIME       , REGISTER_TIME) 
 *             VALUES(#requestNo# , #jobId# , #partitionNo# , #partitionKey# , #restartPoint# , #jobContext# , #state#   , current_timestamp , current_timestamp) 
 *         &lt;/insert&gt;
 *     
 *      �����X�^�[�g���N���A�pSQL�͈ȉ��̓����I�����邱�Ƃ��o����B
 *      ���f�t�H���gSQL�͑Ώۃf�[�^�폜�ł���B
 *         &lt;!-- ���X�^�[�g���N���A�i����������Ώۃf�[�^�̏�ԍX�V�j --&gt;
 *         &lt;update id=&quot;UPDATE_JOB_RESTART_CLEAR&quot; parameterClass=&quot;jobRestartInfo&quot;&gt;
 *             UPDATE RESTART_CONTROL SET 
 *                 STATE       = #state# , 
 *                 UPDATE_TIME = current_timestamp 
 *             WHERE REQUEST_NO   = #requestNo# 
 *             AND   JOB_ID       = #jobId#  
 *             AND   PARTITION_NO = #partitionNo#
 *         &lt;/update&gt;
 *         &lt;!-- ���X�^�[�g���N���A�i����������Ώۃf�[�^�폜�j --&gt;
 *         &lt;delete id=&quot;UPDATE_JOB_RESTART_CLEAR&quot; parameterClass=&quot;jobRestartInfo&quot;&gt;
 *             DELETE FROM RESTART_CONTROL 
 *             WHERE REQUEST_NO   = #requestNo# 
 *             AND   JOB_ID       = #jobId#  
 *             AND   PARTITION_NO = #partitionNo#
 *         &lt;/delete&gt;
 *     
 *         &lt;!-- ���X�^�[�g�˗����擾 --&gt;
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
     * ���X�^�[�g�����󋵁F���������B
     */
    private static final String JOB_END_STATE = "2";

    /**
     * ���X�^�[�g�����󋵁F�N�����B
     */
    private static final String JOB_START_STATE = "1";

    /**
     * ���X�^�[�g���X�V�i�V�K�ǉ��j�pSQL�L�[�B
     */
    private static final String INSERT_JOB_RESTART_POINT = 
        "jobRestart.INSERT_JOB_RESTART_POINT";

    /**
     * ���X�^�[�g���X�V�i�X�V�j�pSQL�L�[�B
     */
    private static final String UPDATE_JOB_RESTART_POINT = 
        "jobRestart.UPDATE_JOB_RESTART_POINT";

    /**
     * �W���u���X�^�[�g���i�[�pObject�B Spring��DI�@�\��p���Đݒ肷��B
     */
    private JobRestartInfoFactory jobRestartInfoFactory = null;

    /**
     * SELECT�pDAO�B Spring��DI�@�\��p���Đݒ肷��B
     */
    private QueryDAO queryDAO = null;

    /**
     * UPDATE�pDAO�B Spring��DI�@�\��p���Đݒ肷��B
     */
    private UpdateDAO updateDAO = null;

    /**
     * �W���u�R���e�L�X�g���������B
     * 
     * @param jobContext �W���u�R���e�L�X�g
     * @param jobStatus �W���u�X�e�[�^�X
     * @return �������ꂽ�W���u�R���e�L�X�g
     */
    public JobContext getRestartJobContext(JobContext jobContext,
            JobStatus jobStatus) {
        // �W���u���X�^�[�g���擾
        JobRestartInfo jobRestartInfo = jobRestartInfoFactory.getInstance();
        initJobRestartInfo(jobRestartInfo, jobContext, jobStatus);
        // �����Ώۂ͋N�����̒��f���ꂽ�f�[�^�̂�
        jobRestartInfo.setState(JOB_START_STATE);
        // DB���烊�X�^�[�g���擾
        JobRestartInfo restoreJobRestartInfo = queryDAO.executeForObject(
                "jobRestart.SELECT_JOB_RESTART_INFO", jobRestartInfo,
                JobRestartInfo.class);

        if (restoreJobRestartInfo == null) {
            // ���X�^�[�g�����ł͂Ȃ��B
            jobContext.setRestarted(false);
            jobStatus.setJobState(jobStatus.getJobState());
            return jobContext;
        } else if (JOB_END_STATE.equals(restoreJobRestartInfo.getState())) {
            // �I�����������B
            jobStatus.setJobState(JobStatus.STATE.ENDING_NORMALLY);
            return jobContext;
        }
        
        JobContext newjobContext = 
            changeByteToJobContext(restoreJobRestartInfo.getJobContext());
        newjobContext.setRestartPoint(restoreJobRestartInfo.getRestartPoint());
        jobStatus.setRestartPoint(restoreJobRestartInfo.getRestartPoint());
        // ���X�^�[�g�����ł���B
        newjobContext.setRestarted(true);
        jobStatus.setJobState(JobStatus.STATE.RESTARTED);
        
        return newjobContext;
    }

    /**
     * ���X�^�[�g�|�C���g�X�V�����B
     * 
     * @param jobStatus
     *            �W���u�̏�
     * @param jobContext
     *            �W���u�R���e�L�X�g
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

        // �R�~�b�g�����O�̏ꍇ�̓��X�^�[�g�|�C���g�V�K�ǉ�
        if (queryDAO.executeForObject("jobRestart.SELECT_JOB_RESTART_INFO",
                jobRestartInfo, JobRestartInfo.class) != null) {
            updateRestartPoint(jobRestartInfo);
        } else {
            // ���X�^�[�g���̒ǉ�
            insertRestartPoint(jobRestartInfo);
        }
    }

    /**
     * �W���u���X�^�[�g���i�[�p�����N���X�̃t�@�N�g����ݒ肷��B
     * 
     * @param jobRestartInfoFactory
     *            �W���u���X�^�[�g���i�[�p�N���X�̃t�@�N�g��
     */
    public void setJobRestartInfoFactory(
            JobRestartInfoFactory jobRestartInfoFactory) {
        this.jobRestartInfoFactory = jobRestartInfoFactory;
    }

    /**
     * SELECT�pDAO�̃Z�b�^�[�B
     * 
     * @param queryDAO
     *            SELECT�pDAO
     */
    public void setQueryDAO(QueryDAO queryDAO) {
        this.queryDAO = queryDAO;
    }

    /**
     * �X�V�pDAO�̃Z�b�^�[�B
     * 
     * @param updateDAO
     *            �X�V�pDAO
     */
    public void setUpdateDAO(UpdateDAO updateDAO) {
        this.updateDAO = updateDAO;
    }

    /**
     * ���X�^�[�g���̃N���A�����p���\�b�h(���R�[�h�X�V)�B
     * 
     * @param jobStatus �W���u������
     * @param sqlkey ���X�^�[�g���̃N���A�pSQL�L�[
     * @return �X�V���ꂽ���R�[�h���B
     */
    public int restartPointClear(JobStatus jobStatus, String sqlkey) {
        JobRestartInfo jobRestartInfo = jobRestartInfoFactory.getInstance();
        jobRestartInfo.setRequestNo(jobStatus.getJobRequestNo());
        jobRestartInfo.setJobId(jobStatus.getJobId());
        jobRestartInfo.setPartitionNo(jobStatus.getPartitionNo());
        jobRestartInfo.setState(JOB_END_STATE);

        // ���X�^�[�g���̃N���A
        return updateDAO.execute(sqlkey, jobRestartInfo);
    }

    /**
     * �W���u�����󋵁A�W���u�R���e�L�X�g�̃f�[�^���X�V�Ώۂ̃��X�^�[�g����
     * �ݒ肷��B<BR>
     * �ȉ��̏���ݒ肷��B<BR>
     * �W���u���N�G�X�g�ԍ��A�W���uID�A�p�[�e�B�V�����ԍ��A �p�[�e�B�V�����L�[�A
     * �W���u�R���e�L�X�g�ibyte[]�ɕϊ����ݒ�j
     * 
     * 
     * @param jobRestartInfo
     *            �X�V�Ώۂ̃��X�^�[�g���
     * @param jobContext
     *            �W���u�R���e�L�X�g
     * @param jobStatus
     *            �W���u������
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
     * �W���u�R���e�L�X�g�I�u�W�F�N�g��<code>byte</code>�z��ɕϊ�����B<BR>
     * 
     * @param jobContext
     *            �W���u�R���e�L�X�g
     * @return �W���u�R���e�L�X�g�̃o�C�g�z��B
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
     * <code>byte</code>�z����W���u�R���e�L�X�g�I�u�W�F�N�g�ɕϊ�����B<BR>
     * 
     * @param obj �ϊ��Ώۂ�Object
     * @return �W���u�R���e�L�X�g�B
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
     * ���X�^�[�g���́u�N���󋵁v�X�V�p���\�b�h�B
     * 
     * @param jobRestartInfo
     *            �X�V�Ώۂ̃��X�^�[�g���
     * @return int �X�V���ꂽ���R�[�h���B
     */
    private int insertRestartPoint(JobRestartInfo jobRestartInfo) {
        return updateDAO.execute(INSERT_JOB_RESTART_POINT, jobRestartInfo);
    }

    /**
     * ���X�^�[�g���́u�N���󋵁v�X�V�p���\�b�h�B
     * 
     * @param jobRestartInfo
     *            �X�V�Ώۂ̃��X�^�[�g���
     * @return int �X�V���ꂽ���R�[�h���B
     */
    private int updateRestartPoint(JobRestartInfo jobRestartInfo) {
        return updateDAO.execute(UPDATE_JOB_RESTART_POINT, jobRestartInfo);
    }
}
