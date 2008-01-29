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

package jp.terasoluna.fw.batch.standard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;

import jp.terasoluna.fw.dao.SqlHolder;
import jp.terasoluna.fw.dao.UpdateDAO;
import jp.terasoluna.fw.batch.core.BatchUpdateProcessor;

/**
 * バッチ更新処理を行う標準実装クラス。
 * 
 * <p>この実装では、<code>UpdateDAO</code> の <code>addBatch()</code>
 * メソッドによって、バッチ更新処理を行う。</p>
 * 
 */
public class StandardBatchUpdateProcessor implements BatchUpdateProcessor {

    /**
     * バッチ更新に用いる更新用DAO。
     */
    private UpdateDAO updateDAO = null;

    /**
     * バッチ更新処理を行う。
     * 
     * @param batchUpdateMapList バッチ更新リスト
     */
    public void processBatchUpdate(
            List<LinkedHashMap<String, Object>> batchUpdateMapList) {
        if (batchUpdateMapList.size() == 0) {
            return;
        }
        
        int headSize = batchUpdateMapList.get(0).size();
        List<SqlHolder> sqlHolderList
            = new ArrayList<SqlHolder>(headSize * batchUpdateMapList.size());
        
        for (LinkedHashMap<String, Object> sqlMap : batchUpdateMapList) {
            Set<Entry<String, Object>> entries = sqlMap.entrySet();

            for (Entry<String, Object> entry : entries) {
                SqlHolder sqlHolder =
                    createSqlHolder(entry.getKey(), entry.getValue());
                sqlHolderList.add(sqlHolder);
            }
        }

        if (sqlHolderList.size() > 0) {
            Collections.sort(sqlHolderList, new SqlHolderListComparator());
            updateDAO.executeBatch(sqlHolderList);
        }
    }

    /**
     * SqlHolderを生成する。
     * @param sqlId SQLID
     * @param bindParams パラメータ
     * @return SqlHolderインスタンス
     */
    private SqlHolder createSqlHolder(String sqlId, Object bindParams) {
        SqlHolder sqlHolder = new SqlHolder(sqlId, bindParams);
        return sqlHolder;
    }

    /**
     * バッチ更新に用いる更新用DAOを設定する。
     * 
     * @param updateDAO バッチ更新に用いる更新用DAO
     */
    public void setUpdateDAO(UpdateDAO updateDAO) {
        this.updateDAO = updateDAO;
    }

    /**
     * sqlHolderListのソート用Comparatorクラス。
     */
    private static class SqlHolderListComparator
            implements Comparator<SqlHolder> {
        
        /**
         * SQLIDを比較する。
         * 
         * @param obj1 sqlHolderListのSqlHolderクラス
         * @param obj2 sqlHolderListのSqlHolderクラス
         * @return 比較結果
         */       
        public int compare(SqlHolder obj1, SqlHolder obj2) {
            return obj1.getSqlID().compareTo(obj2.getSqlID());
        }
    }
}
