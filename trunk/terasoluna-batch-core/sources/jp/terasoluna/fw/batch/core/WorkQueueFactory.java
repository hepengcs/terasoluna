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
 * 作業キューのファクトリインタフェース。
 * 
 * <p><code>JobManager</code> がジョブ（あるいは、ジョブの一部）を実行する際に、
 * その作業対象の入力処理と出力処理を結びつけるキューを作成する。
 * <code>JobManager</code> のひとつのインスタンスが、複数回作業対象の処理を行う
 * ため、作業対象の処理を行う毎にこのファクトリインタフェースから作業キューを
 * 取得する。</p>
 *
 */
public interface WorkQueueFactory {

    /**
     * 作業キューを生成する。
     *
     * @param jobStatus ジョブステータス
     * @return ファクトリが生成した作業キュー
     */
    WorkQueue getWorkQueue(JobStatus jobStatus);
}
