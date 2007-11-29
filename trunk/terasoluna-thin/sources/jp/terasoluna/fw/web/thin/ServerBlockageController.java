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

import java.util.Date;

/**
 * Filterでのサーバ閉塞チェックを行なうインタフェース。
 * <p>
 * このインタフェースを実装したクラスはサーバ閉塞チェック機能を提供する。 
 * </p>
 * <p>
 * なお、このインタフェースの実装クラスはスレッドセーフである必要がある。
 * </p>
 * <p>
 * ※このインタフェースの実装クラスの設定方法は
 * {@link ServerBlockageControlFilter} を参照のこと。
 * </p>
 * 
 * @see jp.terasoluna.fw.web.thin.AuthorizationControlFilter
 * @see jp.terasoluna.fw.web.thin.AuthorizationController
 * @see jp.terasoluna.fw.web.thin.AuthenticationControlFilter
 * @see jp.terasoluna.fw.web.thin.AuthenticationController
 * @see jp.terasoluna.fw.web.thin.BlockageControlFilter
 * @see jp.terasoluna.fw.web.thin.BlockageController
 * @see jp.terasoluna.fw.web.thin.ServerBlockageControlFilter
 *
 */
public interface ServerBlockageController {

    /**
     * 閉塞状態に遷移する。
     */
    void blockade();

    /**
     * 閉塞状態かどうかを判別する。
     *
     * @return 閉塞状態なら true
     */
    boolean isBlockaded();

    /**
     * 閉塞状態かどうかを判別する。
     *
     * @param pathInfo パス情報
     * 
     * @return 閉塞状態なら true
     */
    boolean isBlockaded(String pathInfo);

    /**
     * 予閉塞状態かどうかを判別する。
     *
     * @return 予閉塞状態なら true
     */
    boolean isPreBlockaded();

    /**
     * 通常状態に遷移する。
     */
    void open();

    /**
     * 予閉塞状態に遷移する。
     */
    void preBlockade();

    /**
     * 予閉塞状態に遷移し、
     * 指定された日時になったときに閉塞状態に遷移する。
     *
     * @param time 閉塞状態に遷移する日時
     */
    void preBlockade(Date time);
}
