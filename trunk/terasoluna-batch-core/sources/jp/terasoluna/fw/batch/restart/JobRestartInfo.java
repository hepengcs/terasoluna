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

package jp.terasoluna.fw.batch.restart;

import java.io.Serializable;

/**
 * リスタート情報取得・更新クラス用インタフェース。
 * 
 */
public interface JobRestartInfo extends Serializable {

    /**
     * リスタートポイントを取得。
     * 
     * @return restartPoint リスタートポイント
     */
    int getRestartPoint();

    /**
     * 起動状況を取得。
     * 
     * @return 起動状況
     */
    String getState();

    /**
     * リスタートポイントを設定。
     * 
     * @param restartPoint
     *            リスタートポイント
     */
    void setRestartPoint(int restartPoint);

    /**
     * ジョブコンテキストを取得。
     * 
     * @return jobContext ジョブコンテキスト
     */
    byte[] getJobContext();

    /**
     * 起動状況を設定。
     * 
     * @param state
     *            起動状況
     */
    void setState(String state);

    /**
     * リクエスト情報を設定。
     * 
     * @param requestNo
     *            リクエスト情報
     */
    void setRequestNo(String requestNo);

    /**
     * ジョブIDを設定。
     * 
     * @param jobId
     *            ジョブID
     */
    void setJobId(String jobId);

    /**
     * パーティション番号を設定。
     * 
     * @param partitionNo
     *            パーティション番号
     */
    void setPartitionNo(int partitionNo);

    /**
     * パーティションキーを設定。
     * 
     * @param partitionKey
     *            パーティションキー
     */
    void setPartitionKey(String partitionKey);

    /**
     * ジョブコンテキストを設定。
     * 
     * @param jobContext
     *            ジョブコンテキスト
     */
    void setJobContext(byte[] jobContext);
}
