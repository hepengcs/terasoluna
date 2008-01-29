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

package jp.terasoluna.fw.batch.core;

import jp.terasoluna.fw.batch.openapi.ReturnCode;

/**
 * 対象データ取得結果。
 * 
 * <p>対象データ取得結果は、リターンコードとコレクタで取得されたデータ件数を
 * 保持する。</p>
 *
 */
public class CollectorResult {

    /**
     * リターンコード。
     */
    private ReturnCode returnCode;
    
    /**
     * コレクタで取得されたデータ件数。
     */
    private int collected;

    /**
     * コンストラクタ。
     *
     * @param returnCode 処理結果に設定するリターンコード
     * @param collected コレクタで取得されたデータ件数
     */
    public CollectorResult(ReturnCode returnCode, int collected) {
        this.returnCode = returnCode;
        this.collected = collected;
    }

    /**
     * リターンコードを取得する。
     * 
     * @return リターンコード
     */
    public ReturnCode getReturnCode() {
        return returnCode;
    }

    /**
     * 対象データ取得結果の文字列表現を取得する。
     * 
     * @return 対象データ取得結果の文字列表現
     */
    public String toString() {
        StringBuilder buffer = new StringBuilder();
        buffer.append("ReturnCode: " + getReturnCode() + " ");

        return buffer.toString();
    }

    /**
     * コレクタで取得されたデータ件数を取得する。
     * 
     * @return コレクタで取得されたデータ件数
     */
    public int getCollected() {
        return collected;
    }

}
