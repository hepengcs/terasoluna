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

package jp.terasoluna.fw.web.thin;

import javax.servlet.ServletRequest;

/**
 * Filterでのログオン済みチェックを行なうインタフェース。
 * 
 * <p>
 * このインタフェースを実装したクラスはログオン済みチェック機能を提供する。
 * </p>
 * <p>
 * なお、このインタフェースの実装クラスはスレッドセーフである必要がある。
 * </p>
 * <p>
 * ※このインタフェースの実装クラスの設定方法は
 * {@link AuthenticationControlFilter}を参照のこと。
 * </p>
 *
 * @see jp.terasoluna.fw.web.thin.AuthenticationControlFilter
 * @see jp.terasoluna.fw.web.thin.AuthorizationControlFilter
 * @see jp.terasoluna.fw.web.thin.AuthorizationController
 * @see jp.terasoluna.fw.web.thin.BlockageControlFilter
 * @see jp.terasoluna.fw.web.thin.BlockageController
 * @see jp.terasoluna.fw.web.thin.ServerBlockageControlFilter
 * @see jp.terasoluna.fw.web.thin.ServerBlockageController
 *
 */
public interface AuthenticationController {

    /**
     * リクエストのパス情報に対して、指定されたHTTPセッションが
     * 認証済みであるかどうかを判定する。
     *
     * @param pathInfo パス情報
     * @param req HTTPリクエスト
     * 
     * @return 認証に成功すれば <code>true</code>
     */
    boolean isAuthenticated(String pathInfo, ServletRequest req);
 
    /**
     * ログオン済みチェックが必要かどうかを返す。
     * 
     * @param req 判定対象となる <code>ServletRequest</code> インスタンス
     * 
     * @return チェックが必要であれば<code>true</code>
     */
    boolean isCheckRequired(ServletRequest req);

}
