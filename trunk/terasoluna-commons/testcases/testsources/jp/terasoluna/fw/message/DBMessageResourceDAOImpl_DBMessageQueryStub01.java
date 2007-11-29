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

package jp.terasoluna.fw.message;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import jp.terasoluna.fw.message.DBMessageQuery;

public class DBMessageResourceDAOImpl_DBMessageQueryStub01 extends
DBMessageQuery {
    public DBMessageResourceDAOImpl_DBMessageQueryStub01(DataSource ds,
            String sql, String codeColumn, String languageColumn,
            String countryColumn, String variantColumn, String messageColumn) {
        super(ds, sql, codeColumn, languageColumn, countryColumn, variantColumn,
                messageColumn);
    }

    /**
     * �Ăяo���m�F
     */
    protected boolean isRead = false;
    
    /**
     * �ԋp�l����
     */
    protected List list = new ArrayList();
    
    @Override
    public List execute() {
        return list;
    }
}
