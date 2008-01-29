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

import java.util.Map;


/**
 * 例外ハンドラのユーティリティクラス。
 * 
 * <p>フレームワークが、必要な例外ハンドラを取得する際にユーティリティとして
 * 利用する。</p>
 * 
 * <p><code>JobExceptionHandler</code> インタフェースを実装した例外ハンドラから
 * 利用することを想定したユーティリティではないことに留意すること。</p>
 * 
 */
public final class ExceptionHandlerUtil {

    /**
     * コンストラクタ。
     *
     */
    private ExceptionHandlerUtil() {
    }

    /**
     * 指定された例外に対応する例外ハンドラを取得する。
     *
     * @param e ハンドリング対象の例外
     * @param exceptionHandlerMap 例外ハンドラを格納したMap
     * @param defaultJobExceptionHandler デフォルト例外ハンドラ
     * @return ハンドリング対象の例外に対応した例外ハンドラ
     */
    public static JobExceptionHandler getJobExceptionHandler(Exception e,
            Map<JobException, JobExceptionHandler> exceptionHandlerMap,
            JobExceptionHandler defaultJobExceptionHandler) {
        JobExceptionHandler handler = null;
        for (Map.Entry<JobException, JobExceptionHandler> entry
                : exceptionHandlerMap.entrySet()) {
            JobException entryJobException = entry.getKey();
            if (entryJobException.getClass().isAssignableFrom(e.getClass())) {
                handler = entry.getValue();
                break;
            }
        }
        if (handler == null) {
            handler = defaultJobExceptionHandler;
        }
        return handler;
    }

}
