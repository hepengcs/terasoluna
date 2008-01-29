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

package jp.terasoluna.fw.web.rich.springmvc;

/**
 * 定数クラス。
 *
 */
public class Constants {
    /**
     * エラーコードをモデルに設定するキー。
     * TODO 定数クラスに移動
     */
    public static final String ERRORCODE_KEY = "errorCode";
    
    /**
     * 業務処理結果をモデルに設定するためのキー。
     * TODO viewと同時にret⇒resultに修正。
     */
    public static final String RESULT_KEY = "ret";
}
