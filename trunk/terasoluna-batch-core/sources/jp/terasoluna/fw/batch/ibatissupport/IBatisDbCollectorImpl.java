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

package jp.terasoluna.fw.batch.ibatissupport;


import jp.terasoluna.fw.batch.core.AbstractCollector;
import jp.terasoluna.fw.batch.core.CollectedDataHandler;
import jp.terasoluna.fw.batch.core.CollectorResult;
import jp.terasoluna.fw.batch.core.JobStatus;
import jp.terasoluna.fw.batch.core.QueueingException;
import jp.terasoluna.fw.batch.openapi.JobContext;
import jp.terasoluna.fw.batch.openapi.ReturnCode;

import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.ibatis.sqlmap.client.event.RowHandler;

/**
 * iBatis��p�����Ώۃf�[�^�擾�̎����N���X�B
 *
 */
public class IBatisDbCollectorImpl extends AbstractCollector<JobContext> {

    /**
     * �Ώۃf�[�^�擾�pSQL���L�[�B
     */
        private String sql = null;

    /**
     * <code>SqlMapClient</code>�f�[�^�A�N�Z�X<code>Object</code>�B
     */
    private SqlMapClientDaoSupport queryDAO = null;

    /**
     * �Ώۃf�[�^�擾���������s���郁�\�b�h�B
     *
     * @param jobContext �W���u�R���e�L�X�g
     * @param collectedDataHandler ���[�J�L���[
     * @param jobStatus ������
     * @return �R���N�^��������
     */
    @Override
    protected CollectorResult doCollect(JobContext jobContext,
            CollectedDataHandler collectedDataHandler, JobStatus jobStatus) {
        SqlMapClientTemplate sqlMapClientTemplate = 
            queryDAO.getSqlMapClientTemplate();
        QueuePutRowHandler handler = 
            new QueuePutRowHandler(collectedDataHandler, jobStatus);
        
        try {
            sqlMapClientTemplate.queryWithRowHandler(sql, jobContext, handler);
        } catch (RuntimeException e) {
            if (e.getCause() != null && e.getCause().getCause() != null
                    && (e.getCause().getCause() instanceof QueueingException)
                    && !jobStatus.isExecuting()) {
                throw new QueueingException(e);
            } else {
                throw e;
            }
        }
        return 
            new CollectorResult(ReturnCode.NORMAL_END, handler.getCollected());
    }


    /**
     * <code>Row</code>�n���h���̎����N���X�B
     *
     */
    private static class QueuePutRowHandler implements RowHandler {

        /**
         * �Ώۃf�[�^�擾���B
         */
        private int collected = 0;

        /**
         * �Ώۃf�[�^�擾����������n���h���B
         */
        private CollectedDataHandler collectedDataHandler;

        /**
         * �����󋵁B
         */
        private JobStatus jobStatus;

        /**
         * �R���X�g���N�^�B
         *
         * @param collectedDataHandler �Ώۃf�[�^�擾���i�[���郏�[�J�L���[
         * @param jobStatus ������
         */
        public QueuePutRowHandler(CollectedDataHandler collectedDataHandler,
                JobStatus jobStatus) {
            this.collectedDataHandler = collectedDataHandler;
            this.jobStatus = jobStatus;
        }

        /**
         * <code>Row</code>�n���h�����\�b�h�B
         * DB����擾����<code>Row</code>�f�[�^���L���[�Ɋi�[����B
         *
         * @param row DB����擾����<code>Row</code>
         */
        public void handleRow(Object row) {
            collectedDataHandler.handle(row, collected++);
            jobStatus.incrementCollected();
        }

        /**
         * �Ώۃf�[�^�擾�����擾����B
         *
         * @return �Ώۃf�[�^�擾��
         */
        public int getCollected() {
            return collected;
        }
    }

    /**
     * <code>SqlMapClient</code>�f�[�^�A�N�Z�X<code>Object</code>��ݒ肷��B
     *
     * @param queryDAO <code>SqlMapClient</code>�f�[�^�A�N�Z�X<code>Object</code>
     */
    public void setQueryDAO(SqlMapClientDaoSupport queryDAO) {
        this.queryDAO = queryDAO;
    }

    /**
     * �Ώۃf�[�^�擾�pSQL���L�[��ݒ肷��B
     *
     * @param sql �Ώۃf�[�^�擾�pSQL���L�[
     */
    public void setSql(String sql) {
        this.sql = sql;
    }
}
