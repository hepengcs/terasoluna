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

/**
 * ジョブ例外クラス。
 * 
 * <p>フレームワークのすべての例外の親クラスとなる例外クラス。</p>
 *
 */
public class JobException extends RuntimeException {

    /**
     * Serializable用バージョンID。
     */
    private static final long serialVersionUID = -4180753456812020755L;

    /**
     * コンストラクタ。
     */
    public JobException() {
    }

    /**
     * コンストラクタ。
     *
     * @param e 原因例外
     */
    public JobException(Exception e) {
        super(e);
    }

    /**
     * コンストラクタ。
     *
     * @param message 例外メッセージ
     */
    public JobException(String message) {
        super(message);
    }
    
    /**
     * コンストラクタ。
     *
     * @param message 例外メッセージ
     * @param e 原因例外
     */
    public JobException(String message, Exception e) {
        super(message, e);
    }
}
