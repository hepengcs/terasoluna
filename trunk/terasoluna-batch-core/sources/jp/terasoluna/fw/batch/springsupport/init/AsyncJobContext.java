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

package jp.terasoluna.fw.batch.springsupport.init;

import jp.terasoluna.fw.batch.init.JobInfo;
import jp.terasoluna.fw.batch.openapi.JobContext;

/**
 * 非同期型ジョブ起動処理において、フレームワークで内部的に使用されるジョブコン
 * テクスト。
 * 
 * <p>ジョブ依頼情報を保持する。</p>
 * 
 * <p>非同期型ジョブ起動でのフレームワーク内部で使用される非同期型ジョブ起動ジョ
 * ブ前処理、および非同期型ジョブ起動ジョブ後処理において、起動対象のジョブの情
 * 報を取得するために利用される。</p>
 * 
 */
public class AsyncJobContext extends JobContext {

    /**
     * Serializable用バージョンID。
     */
    private static final long serialVersionUID = 8917004959942137002L;

    /**
     * ジョブ依頼情報。
     */
    private JobInfo jobInfo = null;
    
    /**
     * コンストラクタ。
     */
    public AsyncJobContext() {
    }

    /**
     * コンストラクタ。
     * 
     * <p>ジョブ依頼情報を設定する。</p>
     * 
     * @param jobInfo ジョブ依頼情報
     */
    public AsyncJobContext(JobInfo jobInfo) {
        this.jobInfo = jobInfo;
    }
    
    /**
     * ジョブ依頼情報を取得する。
     * 
     * @return ジョブ依頼情報
     */
    public JobInfo getJobInfo() {
        return jobInfo;
    }

    /**
     * パーティションキーを取得する。
     * 
     * <p>このクラスの実装では、常に<code>null</code>を返却する。</p>
     *
     * @return String null
     */
    @Override
    public String getPartitionKey() {
        return null;
    }

    /**
     * 起動時の引数をジョブコンテキストの設定する。<BR>
     *
     *<p>このクラスの実装では何もしない。</p>
     * @param arg 起動時に指定した第3引数以降の値
     */
    @Override
    public void setParameter(String[] arg) {
    }
    
}
