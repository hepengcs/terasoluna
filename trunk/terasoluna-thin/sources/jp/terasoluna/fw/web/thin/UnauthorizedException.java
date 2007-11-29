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
 * {@link AuthorizationControlFilter}が発行し、
 * コンテナによりエラーページが表示される。 
 * 例外発生についての詳細は{@link AuthorizationControlFilter}を参照のこと。
 * </p>
 * 
 * デプロイメントディスクリプタ（web.xml）で、以下のように記述する必要がある。
 * <code><pre>
 * &lt;web-app&gt;
 *   …
 *   &lt;error-page&gt;
 *     &lt;exception-type&gt;
 *       jp.terasoluna.fw.web.thin.UnauthorizedException
 *     &lt;/exception-type&gt;
 *     &lt;location&gt;/error/authorized-error.do&lt;/location&gt;
 *   &lt;/error-page&gt;
 *   …
 * &lt;/web-app&gt;
 * </pre></code>
 * 
 * @see jp.terasoluna.fw.web.thin.AuthorizationControlFilter
 * @see jp.terasoluna.fw.web.thin.AuthorizationController
 * 
 */
public class UnauthorizedException extends ServletException {

	/**
	 * シリアルバージョンID
	 */
	private static final long serialVersionUID = -2222131583647234479L;

}
