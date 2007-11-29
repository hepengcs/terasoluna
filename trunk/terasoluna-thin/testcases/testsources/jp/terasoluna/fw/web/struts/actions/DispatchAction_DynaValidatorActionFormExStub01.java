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

package jp.terasoluna.fw.web.struts.actions;

import jp.terasoluna.fw.web.struts.form.DynaValidatorActionFormEx;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * DispatchActionTestで使用する。
 *
 * @version 2004/04/12
 */
public class DispatchAction_DynaValidatorActionFormExStub01
    extends DynaValidatorActionFormEx {

    /**
	 * シリアルバージョンID
	 */
	private static final long serialVersionUID = 9102721715771892955L;

	private final static Log log = LogFactory
            .getLog(DispatchAction_DynaValidatorActionFormExStub01.class);

    /**
     * ログ出力後、super.set(name, value)を実行する。
     * 
     * @param name
     * @param value
     * @see org.apache.commons.beanutils.DynaBean#set(java.lang.String,
     *      java.lang.Object)
     */
    @Override
    public void set(String name, Object value) {
        // Log
        log.fatal(
            "set(String, Object) called. name=" + name + " value=" + value);
        super.set(name, value);
    }

    /**
     * ログ出力後、super.set(name, value)を実行する。
     *
     * @param name
     * @param key
     * @param value
     * @see org.apache.commons.beanutils.DynaBean#set(java.lang.String, java.lang.String, java.lang.Object)
     */
    @Override
    public void set(String name, String key, Object value) {
        // Log
        log.fatal(
            "set(String, String, Object) called. name="
                + name
                + " key="
                + key
                + " value="
                + value);
        super.set(name, key, value);
    }

}
