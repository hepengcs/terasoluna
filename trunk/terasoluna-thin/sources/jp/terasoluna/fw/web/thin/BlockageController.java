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
 * Filterでの業務閉塞チェックを行なうインタフェース。
 * <p>
 * このインタフェースを実装したクラスは業務閉塞チェック機能を提供する。
 * </p>
 * <p>
 * なお、このインタフェースの実装クラスはスレッドセーフである必要がある。
 * </p>
 * ※このインタフェースの実装クラスの設定方法は
 * {@link BlockageControlFilter} を参照のこと。
 * </p>
 *
 * @see jp.terasoluna.fw.web.thin.AuthenticationControlFilter
 * @see jp.terasoluna.fw.web.thin.AuthenticationController
 * @see jp.terasoluna.fw.web.thin.AuthorizationControlFilter
 * @see jp.terasoluna.fw.web.thin.AuthorizationController
 * @see jp.terasoluna.fw.web.thin.BlockageControlFilter
 * @see jp.terasoluna.fw.web.thin.ServerBlockageControlFilter
 * @see jp.terasoluna.fw.web.thin.ServerBlockageController
 *
 */
public interface BlockageController {

    /**
     * 閉塞状態にするパスを設定する。
     *
     * @param path 閉塞状態にするパス
     */
    void blockade(String path);

    /**
     * 閉塞状態にするパスを設定する。
     *
     * @param path パス情報
     * @param req HTTPリクエスト
     */
    void blockade(String path, ServletRequest req);

    /**
     * 指定パスのアクションが業務閉塞状態であるかチェックする。
     *
     * @param path パス情報
     * 
     * @return 業務閉塞状態であれば<code>true</code>
     */
    boolean isBlockaded(String path);

    /**
     * 指定パスのアクションが業務閉塞状態であるかチェックする。
     *
     * @param path パス情報
     * @param req HTTPリクエスト
     * 
     * @return 業務閉塞状態であれば <code>true</code>
     */
    boolean isBlockaded(String path, ServletRequest req);

    /**
     * 業務閉塞チェックが必要かどうかを返す。
     *
     * @param req 判定対象となるServletRequestインスタンス
     * 
     * @return チェックが必要であれば<code>true</code>
     */
    boolean isCheckRequired(ServletRequest req);

    /**
     * 閉塞状態にあるパスを開放する。
     *
     * @param path 開放対象のパス
     */
    void open(String path);

    /**
     * 閉塞状態にあるパスを開放する。
     *
     * @param path 開放対象のパス
     * @param req HTTPリクエスト
     */
    void open(String path, ServletRequest req);

}
