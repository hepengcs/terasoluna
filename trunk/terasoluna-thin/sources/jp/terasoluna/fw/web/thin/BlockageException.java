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
 * 業務閉塞例外クラス。
 *
 * <p>
 * 業務閉塞していることをフレームワークが検知した時に、
 * それを通知するために用いられる。{@link BlockageControlFilter}
 * が発行し、コンテナによりエラーページが表示される。
 * </p>
 *
 * <h5>設定例</h5>
 * デプロイメントディスクリプタ（web.xml）で、以下のように記述する。
 * <code><pre>
 * &lt;web-app&gt;
 *   …
 *   &lt;error-page&gt;
 *     &lt;exception-type&gt;
 *       jp.terasoluna.fw.web.thin.BlockageException
 *     &lt;/exception-type&gt;
 *     &lt;location&gt;
 *       /blockageError.html
 *     &lt;/location&gt;
 *   &lt;/error-page&gt;
 *   …
 * &lt;/web-app&gt;
 * </pre></code>
 * <p>
 * 業務閉塞処理については、{@link BlockageControlFilter}、
 * {@link BlockageController}を参照のこと。
 * </p>
 *
 * @see jp.terasoluna.fw.web.thin.BlockageControlFilter
 * @see jp.terasoluna.fw.web.thin.BlockageController
 *
 */
public class BlockageException extends ServletException {

	/**
	 * シリアルバージョンID
	 */
	private static final long serialVersionUID = -7618181046238093719L;

}
