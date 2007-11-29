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

package jp.terasoluna.fw.web.struts;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import jp.terasoluna.fw.web.RequestUtil;

import org.apache.struts.Globals;
import org.apache.struts.config.ModuleConfig;

/**
 * <p>
 *  <code>ModuleConfig</code> に関するユーティリティ。
 * </p>
 *
 */
public class ModuleUtil {

    /**
     * リクエストに関連した <code>ModuleConfig</code> インスタンスを返します。
     *
     * @param request リクエスト情報
     * @return ModuleConfigインスタンス。引数のリクエストがnullの場合、
     *     リクエスト、サーブレットコンテキストからモジュールコンフィグが
     *     取得できない場合はnullを返却する。
     */
    public static ModuleConfig getModuleConfig(HttpServletRequest request) {

        if (request == null) {
            return null;
        }

        //ServletContextを取得
        ServletContext application = RequestUtil.getServletContext(request);

        //ModuleConfigを取得
        ModuleConfig config =
            (ModuleConfig) request.getAttribute(Globals.MODULE_KEY);

        //リクエストにモジュールが無い場合はデフォルトモジュールを返す
        if (config == null) {
            config =
                (ModuleConfig) application.getAttribute(Globals.MODULE_KEY);
        }

        return config;

    }

    /**
     * リクエストに関連したモジュール毎のプリフィックスパスを返します。
     *
     * @param request リクエスト情報
     * @return モジュールプリフィックス。引数のrequestがnullの場合、
     *     getModuleConfigの結果がnullの場合はnullを返却する。
     */
    public static String getPrefix(HttpServletRequest request) {
        if (request == null) {
            return null;
        }
        ModuleConfig config = getModuleConfig(request);
        //Prefixを取得
        String prefix = null;
        if (config != null) {
            prefix = config.getPrefix();
        }
        return prefix;
    }


}
