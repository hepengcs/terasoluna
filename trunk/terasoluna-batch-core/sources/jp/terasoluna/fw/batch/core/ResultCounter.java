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
 * 処理結果件数をカウントするクラス。
 * 
 * <p>ビジネスロジックや、前処理・後処理などのサポート処理など、
 * <code>ReturnCode</code>を返却するものについて、その実行件数をカウントする。
 * <code>ReturnCode</code> のうち、正常継続（<code>ReturnCode.NORMAL_CONTINUE
 * </code>）、異常継続（<code>ReturnCode.ERROR_CONTINUE</code>）のみカウントし、
 * その件数を保持する。</p>
 * 
 */
public class ResultCounter {

    /**
     * 正常継続の処理結果件数。
     */
    private int normalContinueCount = 0;

    /**
     * 異常継続の処理結果件数。
     */
    private int errorContinueCount = 0;

    /**
     * 処理件数をカウントする。
     * 
     * @param returnCode カウントするリターンコード
     */
    public void count(ReturnCode returnCode) {
        switch (returnCode) {
            case NORMAL_CONTINUE :
                normalContinueCount++;
                break;
            case ERROR_CONTINUE :
                errorContinueCount++;
                break;
            default :
                // NORMAL_CONTINUE、ERROR_CONTINUE以外はカウントしない。
                break;
        }
    }

    /**
     * 異常継続の処理結果件数を取得する。
     * 
     * @return 異常継続の処理結果件数
     */
    public int getErrorContinueCount() {
        return errorContinueCount;
    }

    /**
     * 正常継続の処理結果件数を取得する。
     * 
     * @return 正常継続の処理結果件数
     */
    public int getNormalContinueCount() {
        return normalContinueCount;
    }
    
    /**
     * 総件数を取得する。
     * 
     * @return 総件数
     */
    public int getToralCount() {
        return normalContinueCount + errorContinueCount;
    }
}
