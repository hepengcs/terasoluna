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

package jp.terasoluna.fw.batch.partition;

import jp.terasoluna.fw.batch.core.WorkUnit;
import jp.terasoluna.fw.batch.openapi.JobContext;

/**
 * 分割キークラス。
 * 
 * <p>任意の型の分割キーを<code>WorkQueue</code>に入れるためにラップする。</p>
 * 
 * <p>分割キーが何番目のキーであるかを保持する。</p>
 * 
 */
public class PartitionRowObject implements WorkUnit {

    /**
     * 分割キー番号。分割キーが何番目のキーであるかを示す。
     */
    private int partitionNo;

    /**
     * 分割キー。
     */
    private Object partitionKey;

    /**
     * ジョブコンテクスト。
     */
    private JobContext jobContext;

    /**
     * コンストラクタ。
     * 
     * @param partitionNo 分割キー番号
     * @param partitionKey 分割キー
     * @param jobContext ジョブコンテクスト
     */
    public PartitionRowObject(int partitionNo, Object partitionKey,
            JobContext jobContext) {
        this.partitionNo = partitionNo;
        this.partitionKey = partitionKey;
        this.jobContext = jobContext;
    }

    /**
     * 分割キーを取得する。
     * 
     * @return 分割キー
     */
    public Object getRowObject() {
        return partitionKey;
    }

    /**
     * 分割キー番号を取得する。
     * 
     * @return 分割キー番号
     */
    public int getPartitionNo() {
        return partitionNo;
    }

    /**
     * このオブジェクトがキューの最後のオブジェクトであるであるかを返す。
     * 
     * @return このクラスでは常に <code>false</code> を返す。
     */
    public boolean isEndMark() {
        return false;
    }

    /**
     * ジョブコンテクストを取得する。
     * 
     * @return ジョブコンテクスト
     */
    public JobContext getJobContext() {
        return jobContext;
    }
    
    /**
     * ジョブコンテクストを設定する。
     * 
     * @param jobContext ジョブコンテクスト
     */
    public void setJobContext(JobContext jobContext) {
        this.jobContext = jobContext;
    }
}
