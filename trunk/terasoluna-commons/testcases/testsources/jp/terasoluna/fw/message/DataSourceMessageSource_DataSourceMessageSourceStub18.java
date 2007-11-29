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

import java.text.MessageFormat;
import java.util.Locale;
import java.util.Properties;

import jp.terasoluna.fw.message.DataSourceMessageSource;


/**
 * DataSourceMessageSource�N���X�ŗ��p����X�^�u
 *
 */
public class DataSourceMessageSource_DataSourceMessageSourceStub18 extends
        DataSourceMessageSource {
    /**
     * �Ăяo���m�F
     */
    protected boolean isRead_A1 = false;
    
    /**
     * ���P�[������
     */
    Locale locale_A1 = new Locale("ja", "JP", "");
    /**
     * ����code���i�[
     */
    protected String code = "code01";
    
    protected String msg = null;
    
    protected Locale locale = null;
    
    protected Properties prop = new Properties();
    
    protected MessageFormat messageFormat = null;
    
    @Override
    protected Properties getMessages(Locale locale) {

        if (locale.equals(locale_A1)) {
            prop.put(code, "�e�X�g���b�Z�[�W�O�P");
            this.isRead_A1 = true;
            return prop;
        }
        return null;
    }

    @Override
    protected MessageFormat createMessageFormat(String msg, Locale locale) {
        this.msg = msg;
        this.locale = locale;
        return messageFormat;
    }
    
}
