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

import org.apache.struts.config.ExceptionConfig;

/**
 * システム例外のマッピング情報クラス。
 * 
 * <p>
 * ExceptionConfigを継承し、モジュール名のフィールドを追加する。
 * </p>
 * 
 * @see org.apache.struts.config.ExceptionConfig
 * @see jp.terasoluna.fw.web.struts.action.SystemExceptionHandler
 * 
 */
public class ExceptionConfigEx extends ExceptionConfig {

    /**
	 * シリアルバージョンID
	 */
	private static final long serialVersionUID = -1181268336500823437L;

	/**
     * モジュール名。
     */
    private String module;

    /**
     * モジュール名を取得する。
     * 
     * @return モジュール名
     */
    public String getModule() {
        return module;
    }

    /**
     * モジュール名を設定する。
     * 
     * @param module モジュール名
     */
    public void setModule(String module) {
        this.module = module;
    }
}