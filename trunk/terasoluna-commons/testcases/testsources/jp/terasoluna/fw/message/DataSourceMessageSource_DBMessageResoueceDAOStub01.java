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

import java.util.List;

import jp.terasoluna.fw.message.DBMessage;
import jp.terasoluna.fw.message.DBMessageResourceDAO;

/**
 * DataSourceMessageSource�Ŏg�p����X�^�u�N���X�B �C���^�[�t�F�C�XDBMessageResourceDAO�̎������\�b�h
 * 
 */
public class DataSourceMessageSource_DBMessageResoueceDAOStub01 implements
        DBMessageResourceDAO {

    /**
     * �Ăяo���m�F
     */
    protected boolean isRead = false;

    protected List<DBMessage> list = null;

    /**
     * @return �v�f����1����List
     */
    public List<DBMessage> findDBMessages() {
        this.isRead = true;
        return list;
    }
}
