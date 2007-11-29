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

import java.util.Locale;

import jp.terasoluna.fw.message.DataSourceMessageSource;


/**
 * DataSourceMessageSource�N���X�ŗ��p����X�^�u
 *
 */
public class DataSourceMessageSource_DataSourceMessageSourceStub04 extends
        DataSourceMessageSource {
    /**
     * �Ăяo���m�F
     */
    protected boolean isRead = false;
        
    /**
     * ����code���i�[
     */
    protected String code = null;
    
    /**
     * ����locale���i�[
     */
    protected Locale locale = null;
    
    protected String msg = null;
    
    @Override
    protected String internalResolveCodeWithoutArguments(
            String code,
            Locale locale) {
        this.isRead = true;
        this.code = code;
        this.locale = locale;
        return msg;
    }
}
