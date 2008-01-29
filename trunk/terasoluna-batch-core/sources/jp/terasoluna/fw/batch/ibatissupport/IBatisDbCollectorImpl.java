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
 * iBatisを用いた対象データ取得の実装クラス。
 *
 */
public class IBatisDbCollectorImpl extends AbstractCollector<JobContext> {

    /**
     * 対象データ取得用SQL文キー。
     */
        private String sql = null;

    /**
     * <code>SqlMapClient</code>データアクセス<code>Object</code>。
     */
    private SqlMapClientDaoSupport queryDAO = null;

    /**
     * 対象データ取得処理を実行するメソッド。
     *
     * @param jobContext ジョブコンテキスト
     * @param collectedDataHandler ワーカキュー
     * @param jobStatus 処理状況
     * @return コレクタ処理結果
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
     * <code>Row</code>ハンドラの実装クラス。
     *
     */
    private static class QueuePutRowHandler implements RowHandler {

        /**
         * 対象データ取得数。
         */
        private int collected = 0;

        /**
         * 対象データ取得を処理するハンドラ。
         */
        private CollectedDataHandler collectedDataHandler;

        /**
         * 処理状況。
         */
        private JobStatus jobStatus;

        /**
         * コンストラクタ。
         *
         * @param collectedDataHandler 対象データ取得を格納するワーカキュー
         * @param jobStatus 処理状況
         */
        public QueuePutRowHandler(CollectedDataHandler collectedDataHandler,
                JobStatus jobStatus) {
            this.collectedDataHandler = collectedDataHandler;
            this.jobStatus = jobStatus;
        }

        /**
         * <code>Row</code>ハンドラメソッド。
         * DBから取得した<code>Row</code>データをキューに格納する。
         *
         * @param row DBから取得した<code>Row</code>
         */
        public void handleRow(Object row) {
            collectedDataHandler.handle(row, collected++);
            jobStatus.incrementCollected();
        }

        /**
         * 対象データ取得数を取得する。
         *
         * @return 対象データ取得数
         */
        public int getCollected() {
            return collected;
        }
    }

    /**
     * <code>SqlMapClient</code>データアクセス<code>Object</code>を設定する。
     *
     * @param queryDAO <code>SqlMapClient</code>データアクセス<code>Object</code>
     */
    public void setQueryDAO(SqlMapClientDaoSupport queryDAO) {
        this.queryDAO = queryDAO;
    }

    /**
     * 対象データ取得用SQL文キーを設定する。
     *
     * @param sql 対象データ取得用SQL文キー
     */
    public void setSql(String sql) {
        this.sql = sql;
    }
}
