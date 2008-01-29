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

import javax.servlet.ServletException;

/**
 * 認証例外クラス。
 * 
 * <p>
 * ユーザから動作する権限が無いパスのリクエストが行われていることを 
 * フレームワークが検知した時に、それを通知するために用いられる。
 * {@link AuthenticationControlFilter}が発行し、 
 * コンテナによりエラーページが表示される。 
 * 例外発生についての詳細は{@link AuthenticationControlFilter}を参照のこと。
 * </p>
 * 
 * <h5>記述例</h5>
 * デプロイメントディスクリプタ（web.xml）で、以下のように記述する必要がある。
 * <code><pre>
 * &lt;web-app&gt;
 *   …
 *   &lt;error-page&gt;
 *     &lt;exception-type&gt;
 *       jp.terasoluna.fw.web.thin.UnauthenticatedException
 *     &lt;/exception-type&gt;
 *     &lt;location&gt;/error/authenticated-error.do&lt;/location&gt;
 *   &lt;/error-page&gt;
 *   …
 * &lt;/web-app&gt;
 * </pre></code>
 * 
 * @see jp.terasoluna.fw.web.thin.AuthenticationControlFilter
 * @see jp.terasoluna.fw.web.thin.AuthenticationController
 * 
 */
public class UnauthenticatedException extends ServletException {

    /**
     * シリアルバージョンID
     */
    private static final long serialVersionUID = 8581556608555574183L;

}
