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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import jp.terasoluna.fw.batch.openapi.JobContext;

/**
 * チャンククラス。
 * 
 * <p>ビジネスロジックの入力データとなるオブジェクトを一定数分
 * （あるいは特定のルールに従って）保持するコンテナクラスであり、
 * {@link JobWorker} の入力となる。</p>
 * 
 * <p>ジョブを実行する際の処理単位であり、トランザクションや、
 * バッチ更新プロセッサ{@link BatchUpdateProcessor} によるバッチ更新などの単位と
 * なる。</p>
 * 
 * <p>チャンクをデータ数で指定する処理モデルのジョブの場合には、ジョブBean定義
 * ファイルにおいて以下のような設定を行うことでチャンクサイズの指定を行うことが
 * できる。</p>
 * 
 * <p><strong>設定例</strong></p>
 * <pre><code>
 *  &lt;!-- チャンクサイズの指定 --&gt;
 *  &lt;bean id="chunkSize" class="java.lang.Integer"&gt;
 *      &lt;constructor-arg>&lt;value>50&lt;/value&gt;&lt;/constructor-arg&gt;
 *  &lt;/bean&gt;
 * </code></pre>
 * 
 * <p>ジョブBean定義ファイルにおいて、チャンクサイズを指定しなかった場合には、
 * フレームワークBean定義ファイルに設定されているチャンクサイズが使用される。
 * </p>
 * 
 */
public class Chunk implements Iterable, WorkUnit {

    /**
     * データを格納するリスト。
     */
    private List<Object> data;

    /**
     * ジョブコンテクスト。
     */
    private JobContext jobContext;
    
    /**
     * コンストラクタ。
     * 引数で渡されたチャンクサイズを用いてデータを格納するリストオブジェクトを
     * 初期化する。
     *
     * @param chunkSize チャンクサイズ
     * @param jobContext ジョブコンテクスト
     */
    public Chunk(int chunkSize, JobContext jobContext) {
        this.data = new ArrayList<Object>(chunkSize);
        this.jobContext = jobContext;
    }

    /**
     * コンストラクタ。
     */
    public Chunk() {
        this.data = new ArrayList<Object>();
        this.jobContext = null;
    }

    /**
     * チャンクサイズのサイズを取得する。
     *
     * @return チャンクサイズ
     */
    public int size() {
        return data.size();
    }

    /**
     * チャンクにデータを追加する。
     *
     * @param obj 追加対象のデータ
     */
    public void add(Object obj) {
        data.add(obj);
    }

    /**
     * チャンクで保持されているデータの反復子を返す。
     *
     * @return 反復子
     */
    public Iterator<Object> iterator() {
        return data.iterator();
    }
    
    /**
     * チャンクの終端であるかを評価する。
     *
     * @return 評価結果
     */
    public boolean isEndMark() {
        return false;
    }

    /**
     * ジョブコンテクストを取得する。
     * 
     * @return このチャンクに関連付けられているジョブコンテクスト
     */
    public JobContext getJobContext() {
        return jobContext;
    }

    /**
     * ジョブコンテクストを設定する。
     * 
     * @param jobContext このチャンクと関連付けるジョブコンテクスト
     */
    public void setJobContext(JobContext jobContext) {
        this.jobContext = jobContext;
    }
}
