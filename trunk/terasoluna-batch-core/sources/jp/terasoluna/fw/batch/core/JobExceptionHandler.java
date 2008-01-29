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

import jp.terasoluna.fw.batch.openapi.JobContext;

/**
 * ジョブ例外ハンドラインタフェース。
 *
 * <p>ジョブ例外ハンドラは、このインタフェースを実装して作成する。</p>
 *
 * <p>ジョブ例外ハンドラは、ジョブ例外クラスをキーとする例外ハンドラマップに
 * 登録される。ジョブ実行中に例外が発生した場合、例外発生箇所に対応してジョブ
 * 例外のサブクラスでラップされ、例外ハンドラマップにしたがって例外に対応した
 * 例外ハンドラが起動される。</p>
 * 
 * <p>ジョブ例外ハンドラでは、渡された例外やジョブコンテキストからログの出力など
 * を行う他に、ジョブステータスを更新して、ジョブの終了、継続などを決定すること
 * ができる。</p>
 *
 */
public interface JobExceptionHandler {

    /**
     * 例外処理を行う。
     *
     * @param jobContext ジョブコンテキスト
     * @param jobException ジョブ例外
     * @param jobStatus ジョブステータス
     */
    void handlException(JobContext jobContext, JobException jobException,
            JobStatus jobStatus);
}
