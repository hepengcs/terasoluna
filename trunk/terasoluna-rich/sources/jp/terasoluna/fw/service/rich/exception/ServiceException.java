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

package jp.terasoluna.fw.service.rich.exception;

/**
 * サービス層のクラスで新規発生する例外をあらわすクラス。
 * 
 * <p>
 *  サービス層のクラスで、エラーが新規発生した場合に利用する。
 *  例えば、業務的にデータの不整合等が起きている場合に利用する。
 * </p>
 * 
 * <p>
 * 本クラスで設定したエラーコードおよび置換文字列は、プレゼンテーション層・クライアントサイド等で
 * 適切なメッセージに変換すること。
 * </p>
 * 
 */
public class ServiceException extends RuntimeException {

    /**
     * シリアルバージョンID。
     */
    private static final long serialVersionUID = 3011574799470623851L;

    /**
     * エラーコード。
     */
    private String errorCode = null;

    /**
     * エラーメッセージの置換文字列。
     */
    private String[] options = null;
    
    /**
     * 生成処理。
     */
    public ServiceException() {
    }

    /**
     * 生成処理。
     *
     * @param errorCode エラーコード
     * @param optionStrings メッセージ中の{n}を置換する文字列の配列
     */
    public ServiceException(String errorCode,
                            String... optionStrings) {
        this.errorCode = errorCode;
        this.options = optionStrings;
    }

    /**
     * エラーコードを取得する。
     *
     * @return エラーコード
     */
    public String getErrorCode() {
        return this.errorCode;
    }

    /**
     * エラーメッセージの置換文字列を取得する。
     *
     * @return エラーメッセージ置換文字列
     */
    public String[] getOptions() {
        return this.options;
    }
}
