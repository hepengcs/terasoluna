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

package jp.terasoluna.fw.web.taglib;

import javax.servlet.ServletRequest;

import jp.terasoluna.fw.web.thin.AuthorizationController;

/**
 * IfAuthorizedTag_AccessControllerStub01。<br>
 *
 * <code>AccessController</code>を継承したStubクラス。
 *
 */
public class IfAuthorizedTag_AccessControllerStub01
    implements AuthorizationController {

    /**
     * AccessControllerクラスの <code>isAuthorized(String pathInfo, 
     * ServletRequest req)</code> をオーバーライドする。
     * 常に <code>true</code> を返すため、
     *
     * @param pathInfo パス情報
     * @param req HTTPリクエスト
     * @return 権限チェックに成功すれば <code>true</code>。
     *         このクラスの実装では常に <code>true</code>
     */
    public boolean isAuthorized(String pathInfo, ServletRequest req) {
        // サブクラスでオーバーライドする
        return true;
    }

    /**
     * リクエストのパス情報に対して、指定されたHTTPセッションが
     * 認証済みであるかどうかを判定する。
     *
     * @param pathInfo パス情報
     * @param req HTTPリクエスト
     * @return 認証に成功すれば <code>true</code>。
     *         このクラスの実装では常に <code>false</code>
     */
    public boolean isAuthenticated(String pathInfo, ServletRequest req) {
        return false;
    }

    /**
     * 別業務に移ったかどうかパスチェックを行い、判定する。
     * @param req 判定対象となる <code>ServletRequest</code> クラスインスタンス
     * @return 別業務に移っていれば<code>true</code>。
     *         このクラスの実装では常に <code>false</code>
     */
    public boolean isCheckRequired(ServletRequest req) {
        return false;
    }

}
