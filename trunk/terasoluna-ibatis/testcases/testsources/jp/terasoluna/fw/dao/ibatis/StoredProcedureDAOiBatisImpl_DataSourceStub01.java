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

package jp.terasoluna.fw.dao.ibatis;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import jp.terasoluna.fw.dao.ibatis.StoredProcedureDAOiBatisImpl;

/**
 * {@link StoredProcedureDAOiBatisImpl}�̎����̂��߂Ɏg�p�����B
 * 
 * {@link StoredProcedureDAOiBatisImpl_SqlMapClientTemplateStub01}
 * ����g�p�����B
 * 
 */
public class StoredProcedureDAOiBatisImpl_DataSourceStub01 implements DataSource {

    public Connection getConnection() throws SQLException {
        // TODO �����������ꂽ���\�b�h�E�X�^�u
        return null;
    }

    public Connection getConnection(String username, String password) throws SQLException {
        // TODO �����������ꂽ���\�b�h�E�X�^�u
        return null;
    }

    public PrintWriter getLogWriter() throws SQLException {
        // TODO �����������ꂽ���\�b�h�E�X�^�u
        return null;
    }

    public void setLogWriter(PrintWriter out) throws SQLException {
        // TODO �����������ꂽ���\�b�h�E�X�^�u
        
    }

    public void setLoginTimeout(int seconds) throws SQLException {
        // TODO �����������ꂽ���\�b�h�E�X�^�u
        
    }

    public int getLoginTimeout() throws SQLException {
        // TODO �����������ꂽ���\�b�h�E�X�^�u
        return 0;
    }

}