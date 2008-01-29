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
 * サポート処理プロセッサのインタフェース。
 * 
 * <p>ビジネスロジックのサポートのため、ジョブには前処理・後処理を定義することが
 * できる。前処理・後処理は、<code>SupportLogic</code> インタフェースを実装して
 * ジョブの開発者が作成することができる。</p>
 * 
 * <p>前処理・後処理は、ビジネスロジックのトランザクションとの関係や起動のタイミ
 * ングによって、フレームワークのいくつかのクラスから起動される。これらのクラス
 * は、<code>SupportProcessor</code> インタフェースを通して、その実装クラスに前
 * 処理・後処理実行を依頼する。</p>
 * 
 * <p><code>SupportProcessor</code> インタフェースの実装クラスでは、前処理・後処
 * 理のトランザクションや、実行結果のジョブステータスへの反映、例外発生時の処理
 * 、およびログ出力などを行う。</p>
 *
 */
public interface SupportProcessor {

    /**
     * サポート処理を行う。
     *
     * @param jobContext ジョブコンテキスト
     * @param jobStatus ジョブ処理状況
     */
    void process(JobContext jobContext, JobStatus jobStatus);
    
    /**
     * サポート処理がスキップできる場合には、<code>true</code> を返す。 
     * 
     * @return サポート処理がスキップできる場合には、<code>true</code>
     */
    boolean canSkip();
}
