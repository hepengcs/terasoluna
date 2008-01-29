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

package jp.terasoluna.fw.batch.openapi;

import java.util.Map;

/**
 * コントロールブレイク処理を行うハンドラのインタフェース。
 *
 * @param <T> ジョブコンテキスト
 */
public interface ControlBreakHandler<T extends JobContext> {

    /**
     * コントロールブレイク処理を行う。
     *
     * @param breakKeyValueMap ブレイクキーをキーとして、ブレイクしたときの値を
     *         保持したマップ
     * @param jobContext ジョブコンテキスト
     * @return 処理結果
     */
    BLogicResult handleControlBreak(
            Map<String, Object> breakKeyValueMap,
            T jobContext);
}
