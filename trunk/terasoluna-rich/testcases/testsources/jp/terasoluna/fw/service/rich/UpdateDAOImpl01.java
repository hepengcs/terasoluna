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

import java.util.List;

import jp.terasoluna.fw.dao.SqlHolder;
import jp.terasoluna.fw.dao.UpdateDAO;

/**
 * UpdateDAOŽÀ‘•ƒNƒ‰ƒX
 */
public class UpdateDAOImpl01 implements UpdateDAO {

    public int execute(String sqlID, Object bindParams) {
        return 0;
    }

    public void startBatch() {

    }

    @SuppressWarnings("deprecation")
    public void addBatch(String sqlID, Object bindParams) {

    }

    @SuppressWarnings("deprecation")
    public int executeBatch() {
        return 0;
    }

    public int executeBatch(List<SqlHolder> arg0) {
        return 0;
    }

}
