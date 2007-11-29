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

import java.util.Date;

import jp.terasoluna.fw.web.thin.ServerBlockageController;

/**
 * IfPreBlockadeTagTestで使用する。
 * 
 */
public class IfPreBlockageTag_ServerBlockageControllerStub01 implements
        ServerBlockageController {

    /**
     * コンストラクタ。
     */
    public IfPreBlockageTag_ServerBlockageControllerStub01() {
        super();
    }

    /**
     * 閉塞状態に遷移する。
     */
    public void blockade() {
    }

    /**
     * 閉塞状態かどうかを判別する。
     *
     * @return 閉塞状態なら <code>true</code>
     */
    public boolean isBlockaded() {
        return false;
    }

    /**
     * 閉塞状態かどうかを判別する。指定されたパス情報が
     * <code>ADMIN_PATH_PREFIX</code> または
     * <code>ERROR_PATH_PREFIX</code> で始まるときは、
     * 閉塞チェックを行わず、常に非閉塞状態とみなす。
     *
     * @param pathInfo パス情報
     * @return 閉塞状態なら <code>true</code>
     */
    public boolean isBlockaded(String pathInfo) {
        return false;
    }

    /**
     * 予閉塞状態かどうかを判別する。
     *
     * @return 予閉塞状態なら <code>true</code>
     */
    public boolean isPreBlockaded() {
        return false;
    }

    /**
     * 通常状態に遷移する。
     */
    public void open() {
    }

    /**
     * 予閉塞状態に遷移する。
     */
    public void preBlockade() {
    }

    /**
     * 予閉塞状態に遷移し
     * 指定された日時になったときに閉塞状態に遷移する。
     *
     * @param time 閉塞状態に遷移する日時
     */
    public void preBlockade(Date time) {
    }

}
