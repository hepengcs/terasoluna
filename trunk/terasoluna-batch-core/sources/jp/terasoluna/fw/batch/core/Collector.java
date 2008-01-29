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
 * 対象データ取得クラスが実装するインタフェース。
 * 
 * <p>このインタフェースの実装では、ビジネスロジックの入力データをデータベースや
 * ファイルから取得する。</p>
 * 
 * <p>対象データ取得クラスでは、引数で渡された <code>WorkQueue</code> に取得した
 * 対象データを順次キューイングする。</p>
 *
 * @param <T> ジョブコンテクストのサブクラス
 */
public interface Collector<T extends JobContext> {

    /**
     * 処理対象データの取得用メソッド。
     * 処理対象データを取得し、キューに格納する。
     *
     * @param jobContext ジョブコンテキスト
     * @param workQueue 処理対象データを格納するキュー
     * @param jobStatus ジョブ処理状況
     * @return コレクタ結果
     */
    CollectorResult collect(T jobContext, WorkQueue workQueue,
            JobStatus jobStatus);
}
