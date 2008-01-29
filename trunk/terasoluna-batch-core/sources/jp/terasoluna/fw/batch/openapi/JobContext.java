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

import java.io.Serializable;

import jp.terasoluna.fw.batch.core.JobException;

/**
 * 基本ジョブコンテキストクラス。
 *
 * <p>
 * 対象データ取得処理、ビジネスロジック、前処理／後処理で共有され、
 * フレームワークにより基本属性が設定される。<br> 
 * 個別のジョブを定義する際に、開発者がそのジョブの引継ぎ情報の項目に対応して
 * カスタム属性を拡張して定義することができる。
 * </p>
 * 
 * <p><strong>拡張ジョブコンテキストのBean定義例</strong></p>
 * 
 * <p>開発者が拡張したジョブコンテキストをジョブBean定義ファイルに設定する。</p>
 * <code><pre>
 *     &lt;bean id=&quot;jobContext&quot;
 *              class=&quot;jp.terasoluna.fw.batch.sample.checksample.SampleJobContext&quot; /&gt;
 * </pre></code>
 * 
 * 
 */
public class JobContext implements Serializable {
    
    /**
     * Serializable用バージョンID。
     */
    private static final long serialVersionUID = 8920476023805712633L;

    /**
     * ジョブ起動種別。
     */
    public static enum START_TYPE {
        /**
         * 非同期。
         */
        ASYNC,
        /**
         * 同期。
         */
        SYNC
    }

    /**
     * ジョブID。
     */
    protected String jobId = null;

    /**
     * ジョブリクエスト番号。
     */
    protected String jobRequestNo = null;

    /**
     * 分割ジョブのパーティションキー。
     */
    protected String partitionKey = "NO_Partition";
    
    /**
     * 分割ジョブのパーティション番号。
     */
    protected int partitionNo = -1;

    /**
     * リスタート可能フラグ。
     */
    protected boolean restartable = false;

    /**
     * リスタート実行フラグ。
     */
    protected boolean restarted = false;

    /**
     * リスタートポイント。
     */
    protected int restartPoint = 0;

    /**
     * ジョブ起動種別。
     */
    protected START_TYPE startType = null;
    
    /**
     * 起動時の引数。
     */
    protected String[] parameter = null;
    
    /**
     * パーティションキーが設定される子ジョブコンテキストを返却する。<br>
     * 分割ジョブ時、親ジョブから呼ばれる。<br>
     * パーティション番号はハンドラから設定される。<br>
     *
     * @param partitionKey パーティションキー
     * @return 子ジョブのジョブコンテキスト
     * @throws IllegalArgumentException パーティションキーがNULLまたは空白文字列
     */
    public JobContext getChildJobContext(Object partitionKey) {
        if (partitionKey == null 
                || "".equals(partitionKey.toString().trim())) {
            throw new IllegalArgumentException(
                    "partitionKey is NULL or Whitespace.");
        }
        
        JobContext childJobContext = null;
        try {
            childJobContext = this.getClass().newInstance();
        } catch (InstantiationException e) {
            throw new JobException(e);
        } catch (IllegalAccessException e) {
            throw new JobException(e);
        }
        
        childJobContext.setJobId(this.jobId);
        childJobContext.setJobRequestNo(this.jobRequestNo);
        childJobContext.setRestartable(this.restartable);
        childJobContext.setRestarted(this.restarted);
        childJobContext.setStartType(this.startType);
        childJobContext.setPartitionKey(String.class.cast(partitionKey));
        childJobContext.setParameter(this.parameter);
        
        return childJobContext;
    }

    /**
     * ジョブIDを返却する。
     *
     * @return ジョブID
     */
    public String getJobId() {
        return this.jobId;
    };

    /**
     * ジョブ依頼番号を返却する。
     *
     * @return ジョブ依頼番号
     */
    public String getJobRequestNo() {
        return this.jobRequestNo;
    }

    /**
     * パーティションキーを取得する。
     *
     * @return 文字例のパーティションキー
     */
    public String getPartitionKey() {
        return this.partitionKey;
    }
    
    /**
     * パーティション番号を返却する。
     *
     * @return パーティション番号
     */
    public int getPartitionNo() {
        return this.partitionNo;
    }

    /**
     * リスタートポイントを返却する。
     *
     * @return リスタートポイント
     */
    public int getRestartPoint() {
        return this.restartPoint;
    }

    /**
     * 起動種別を返却する。
     *
     * @return 起動種別
     */
    public START_TYPE getStartType() {
        return this.startType;
    }

    /**
     * 起動時の引数を返却する。
     * 
     * @return 起動時の引数
     */
    public String[] getParameter() {
        return this.parameter;
    }
    
    /**
     * 実行中のジョブがリスタート可能なジョブとして設定されているかを取得する。
     *
     * @return リスタート可能なジョブであれば <code>true</code>
     */
    public boolean isRestartable() {
        return this.restartable;
    }

    /**
     * リスタート実行フラグを取得する。
     *
     * @return リスタート実行フラグ。
     *           スタートポイントから再開されたジョブの実行である場合には、
     *           <code>true</code>
     */
    public boolean isRestarted() {
        return this.restarted;
    }

    /**
     * ジョブIDを設定する。
     *
     * @param jobId ジョブID
     */
    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    /**
     * ジョブ依頼番号を設定する。
     *
     * @param jobRequestNo ジョブ依頼番号
     */
    public void setJobRequestNo(String jobRequestNo) {
        this.jobRequestNo = jobRequestNo;
    }

    /**
     * 起動時の引数をジョブコンテキストに設定する。
     *
     * @param arg 起動時に指定した第3引数以降の値
     */
    public void setParameter(String[] arg) {
        this.parameter = arg;
    }

    /**
     * パーティション番号を設定する。
     *
     * @param partitionNo パーティション番号
     */
    public void setPartitionNo(int partitionNo) {
        this.partitionNo = partitionNo;
    }

    /**
     * 実行中のジョブがリスタート可能なジョブかどうかを設定する。
     * 
     * <p>フレームワークによってジョブ初期化時に設定される</p>
     *
     * @param restartable リスタート可能なジョブであれば <code>true</code>
     */
    public void setRestartable(boolean restartable) {
        this.restartable = restartable;
    }

    /**
     * リスタート実行フラグを設定する。
     * 
     * <p>リスタート実行フラグは、フレームワークによってジョブ初期化時に設定され
     * る</p>
     *
     * @param restarted
     *           スタートポイントから再開されたジョブの実行である場合には、
     *           <code>true</code>
     */
    public void setRestarted(boolean restarted) {
        this.restarted = restarted;
    }

    /**
     * リスタートポイントを設定する。
     *
     * <p>リスタートポイントは、フレームワークによってジョブ初期化時に設定され
     * る</p>
     * 
     * @param restartPoint リスタートポイント
     */
    public void setRestartPoint(int restartPoint) {
        this.restartPoint = restartPoint;
    }

    /**
     * 起動種別を設定する。
     *
     * <p>起動種別は、フレームワークによってジョブ初期化時に設定される</p>
     * 
     * @param startType 起動種別
     */
    public void setStartType(START_TYPE startType) {
        this.startType = startType;
    }
    
    /**
     * パーティションキーを設定する。
     *
     * <p>パーティションキーは、分割ジョブ時、フレームワークによって子ジョブの
     * ジョブコンテキストに設定される</p>
     * 
     * @param partitionKey パーティションキー
     */
    public void setPartitionKey(String partitionKey) {
        this.partitionKey = partitionKey;
    }
    
}
