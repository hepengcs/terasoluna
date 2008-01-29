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
 * <code>Workable<code> インタフェースの作業の作業単位。
 * 
 * <p>このインタフェースは、<code>JobManager</code>、<code>JobWorker</code> など
 * の<code>Workable</code> インタフェースを実装するクラスが作業を行う際の作業単
 * 位を表す。具体的には、ビジネスロジックの入力データや、あるいは、ジョブを分割
 * するための分割キーを表す。また、作業単位は、その作業単位に関連するジョブコン
 * テクストを設定、取得することができる。</p>
 * 
 * <p>このインタフェースのインスタンスは、<code>CollectedDataHandler</code> で作
 * 成され、キューイングされて <code>Workable</code> インタフェースの実装クラスへ
 * 渡される。このインタフェースでは、キューの最後のデータであるかどうを判定する
 * ための<code>isEndMark()</code> メソッドを規定する。</p>
 * 
 */
public interface WorkUnit {
    
    /**
     * このインタフェースを実装するインスタンスが、キューの最後のデータであるか
     * どうかを取得する。
     * 
     * @return キューの最後のデータである場合に <code>true</code>
     */
    boolean isEndMark();
    
    /**
     * ジョブコンテクストを取得する。
     * 
     * @return ジョブコンテクスト
     */
    JobContext getJobContext();
    
    /**
     * ジョブコンテクストを設定する。
     * 
     * @param jobContext ジョブコンテクスト
     */
    void setJobContext(JobContext jobContext);
}
