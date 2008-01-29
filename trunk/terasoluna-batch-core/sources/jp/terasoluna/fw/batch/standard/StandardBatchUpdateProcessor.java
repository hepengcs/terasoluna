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
 * �o�b�`�X�V�������s���W�������N���X�B
 * 
 * <p>���̎����ł́A<code>UpdateDAO</code> �� <code>addBatch()</code>
 * ���\�b�h�ɂ���āA�o�b�`�X�V�������s���B</p>
 * 
 */
public class StandardBatchUpdateProcessor implements BatchUpdateProcessor {

    /**
     * �o�b�`�X�V�ɗp����X�V�pDAO�B
     */
    private UpdateDAO updateDAO = null;

    /**
     * �o�b�`�X�V�������s���B
     * 
     * @param batchUpdateMapList �o�b�`�X�V���X�g
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
     * SqlHolder�𐶐�����B
     * @param sqlId SQLID
     * @param bindParams �p�����[�^
     * @return SqlHolder�C���X�^���X
     */
    private SqlHolder createSqlHolder(String sqlId, Object bindParams) {
        SqlHolder sqlHolder = new SqlHolder(sqlId, bindParams);
        return sqlHolder;
    }

    /**
     * �o�b�`�X�V�ɗp����X�V�pDAO��ݒ肷��B
     * 
     * @param updateDAO �o�b�`�X�V�ɗp����X�V�pDAO
     */
    public void setUpdateDAO(UpdateDAO updateDAO) {
        this.updateDAO = updateDAO;
    }

    /**
     * sqlHolderList�̃\�[�g�pComparator�N���X�B
     */
    private static class SqlHolderListComparator
            implements Comparator<SqlHolder> {
        
        /**
         * SQLID���r����B
         * 
         * @param obj1 sqlHolderList��SqlHolder�N���X
         * @param obj2 sqlHolderList��SqlHolder�N���X
         * @return ��r����
         */       
        public int compare(SqlHolder obj1, SqlHolder obj2) {
            return obj1.getSqlID().compareTo(obj2.getSqlID());
        }
    }
}
