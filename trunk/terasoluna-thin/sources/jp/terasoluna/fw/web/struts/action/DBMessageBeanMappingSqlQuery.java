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

package jp.terasoluna.fw.web.struts.action;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import jp.terasoluna.fw.exception.SystemException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.object.MappingSqlQuery;
/**
 * DBから取得した行をDBMessageBeanインスタンスに詰め替えて返すメソッドを
 * 実装したMappingSqlQuery実装クラス。
 * 
 * @see jp.terasoluna.fw.web.struts.action.DBMessageBean
 * @see jp.terasoluna.fw.web.struts.action.MessageResourcesDAOImpl
 * 
 */
public class DBMessageBeanMappingSqlQuery extends MappingSqlQuery {
    
    /**
     * SQLの設定に誤りが見られる場合をあらわすエラーコード
     */
    private static final String DB_MESSAGE_BEAN_MAPPING_SQL_QUERY
        = "errors.db.message.bean.mapping.sql.query";
    
    /**
     * ログクラス。
     */ 
    private static Log log 
        = LogFactory.getLog(DBMessageBeanMappingSqlQuery.class);

    /**
     * コンストラクタ。
     * 
     * @param dataSource データソース
     * @param sql SQL
     */
    DBMessageBeanMappingSqlQuery(DataSource dataSource, String sql) {
        super(dataSource, sql);
    }
    
    /**
     * 行をDBMessageBeanに詰め替えて返す。
     * 
     * @param rs 行
     * @param rowNum 処理している行番号
     * 
     * @return 行から詰め替えられたDBMessageBean
     * 
     * @throws SQLException 設定されたSQLが誤っていた場合
     */
    @Override
    protected DBMessageBean mapRow(ResultSet rs, int rowNum)
    throws SQLException {
        // 行をDBMessageBeanインスタンスに詰め替え
        return createDBMessageBean(rs); 
    }

    /**
     * 行をDBMessageBeanに詰め替えて返す。
     * 
     * @param rs 行
     * 
     * @return 行から詰め替えられたDBMessageBean
     * 
     * @throws SQLException 設定されたSQLが誤っていた場合
     */
    protected DBMessageBean createDBMessageBean(ResultSet rs) 
    throws SQLException {
        DBMessageBean msg = new DBMessageBean();
        int columnCount = 0;
        String key = null;
        String value = null;
        
        // メッセージリソースの入っているテーブルが適切な形でないときは
        // 例外をスロー。
        columnCount = rs.getMetaData().getColumnCount();
        if (columnCount != 2) {
            // SQLの返すビューが2列ではない場合、SQLが誤っている
            log.error("SQL for DB message returns "
                      + columnCount + " column(s) result set.");
            throw new SystemException(null, DB_MESSAGE_BEAN_MAPPING_SQL_QUERY);
        }
        
        // keyを設定
        key = rs.getString(1);
        if (key == null || "".equals(key)) {
            if (log.isWarnEnabled()) {
                log.warn("DBMessage resource keys contain null or empty.");
            }
            key = "";
        }        
        msg.setKey(key);        
        
        // valueを設定
        value = rs.getString(2);
        if (value == null) {
            value = "";
        }
        msg.setValue(value);
        
        return msg;
    }
}
