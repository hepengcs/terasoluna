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

package jp.terasoluna.fw.web.codelist;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.jdbc.object.MappingSqlQuery;

/**
 * データベースからコードリスト取得を行う RDBMSオペレーションクラス。
 *
 * データベースに接続するデータソースと使用するSQL文をコンストラクタで指定して、
 * executeメソッドを実行することで、データベースからコードリストを取得することが
 * できる。
 * このクラスは
 * {@link jp.terasoluna.fw.web.codelist.DBCodeListLoader}
 * でのみ利用される。
 *
 *
 */
public class DBCodeListQuery extends MappingSqlQuery {

    /**
     * データソースとSQL文の設定を行うコンストラクタ。
     *
     * @param dataSource データベース接続に使用するデータソース。
     * @param sql コードリスト取得に使用するSQL文。
     */
    public DBCodeListQuery(DataSource dataSource, String sql) {
        super(dataSource, sql);
    }

    /**
     * 1行取得するごとに呼ばれる。
     *
     * <p>
     * 取得した行の1列目をidと2列目をnameとしてデータベースから取得した値と
     * CodeBeanインスタンスを結びつける。
     * </p>
     *
     * @param rs 現在の行情報を持つResultSet。
     * @param rowNum 現在参照している行番号。（最初は0行目）
     * @throws SQLException SQL例外。
     * @return 取得した結果が格納されたインスタンス。
     */
    @Override
    protected Object mapRow(ResultSet rs, int rowNum)
        throws SQLException {
        return createCodeBean(rs);
    }

    /**
     * ResultSetから値を取得し、CodeBeanインスタンスを生成する。
     *
     * @param rs 値を保持するResultSet。
     * @return 値が格納されたCodeBeanインスタンス。
     * @throws SQLException SQL例外。
     */
    private CodeBean createCodeBean(ResultSet rs) throws SQLException {
        CodeBean cb = new CodeBean();
        int columnCount = rs.getMetaData().getColumnCount();
        if (columnCount > 0) {
            // 1列目が存在する場合。
            String id = rs.getString(1);
            if (id == null) {
                id = "";
            }
            cb.setId(id);
        }

        if (columnCount > 1) {
            // 2列目が存在する場合。
            String name = rs.getString(2);
            if (name == null) {
                name = "";
            }
            cb.setName(name);
        }
        return cb;
    }
}
