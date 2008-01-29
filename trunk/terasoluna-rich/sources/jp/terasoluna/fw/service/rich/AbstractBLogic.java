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

package jp.terasoluna.fw.service.rich;

import jp.terasoluna.fw.dao.QueryDAO;
import jp.terasoluna.fw.dao.StoredProcedureDAO;
import jp.terasoluna.fw.dao.UpdateDAO;



/**
 * DAOのアクセサメソッドを持つBLogicの抽象クラス。
 * 
 * <p>
 * DAOのgetter/setterメソッドを持つ。
 * 実装クラスは、本クラスが用意したgetterメソッドを利用して、
 * DAOを参照することが出来る。
 * </p>
 * 
 * <p>
 * 通常は本クラスを利用せず、
 * BLogicインタフェースを直接実装したBLogicクラスを作成し、
 * DIコンテナより必要なDAOおよび制御情報サポートクラスを設定すること。
 * DAOのgetter/setterメソッドを業務開発者に記述させたくない場合のみ、
 * 本クラスを実装したBLogicを作成し利用すること。
 * </p>
 * 
 * <p>
 * 本クラスを利用する際はBean定義が必要である。
 * 属性にDAOをDIコンテナより設定すること。
 * 単一のデータベースを利用することを前提としているため、設定できるDAOは各一つである。
 * </p>
 * 
 * @param <P> ビジネスロジックの引数の型。
 * @param <R> ビジネスロジックの戻り値の型。
 *
 */
public abstract class AbstractBLogic<P, R> implements BLogic<P, R> {
    /**
     * 参照系DAO。
     */
    private QueryDAO queryDAO = null;
    
    /**
     * 更新系DAO
     */
    private UpdateDAO updateDAO = null;

    /**
     * ストアドプロシージャDAO。
     */
    private StoredProcedureDAO storedProcedureDAO = null;

    /**
     * 参照系DAOを設定する。
     * @param queryDAO 参照系DAO。
     */
    public void setQueryDAO(QueryDAO queryDAO) {
        this.queryDAO = queryDAO;
    }

    /**
     * 参照系DAOを取得する。
     * @return queryDAO 参照系DAO。
     */
    protected QueryDAO getQueryDAO() {
        return queryDAO;
    }

    /**
     * 更新系DAOを設定する。
     * @param updateDAO 更新系DAO。
     */
    public void setUpdateDAO(UpdateDAO updateDAO) {
        this.updateDAO = updateDAO;
    }

    /**
     * 更新系DAOを取得する。
     * @return updateDAO 更新系DAO。
     */
    protected UpdateDAO getUpdateDAO() {
        return updateDAO;
    }

    /**
     * ストアドプロシージャDAOを設定する。
     * @param storedProcedureDAO ストアドプロシージャDAO。
     */
    public void setStoredProcedureDAO(StoredProcedureDAO storedProcedureDAO) {
        this.storedProcedureDAO = storedProcedureDAO;
    }

    /**
     * ストアドプロシージャDAOを取得する。
     * @return storedProcedureDAO ストアドプロシージャDAO。
     */
    protected StoredProcedureDAO getStoredProcedureDAO() {
        return storedProcedureDAO;
    }
}
