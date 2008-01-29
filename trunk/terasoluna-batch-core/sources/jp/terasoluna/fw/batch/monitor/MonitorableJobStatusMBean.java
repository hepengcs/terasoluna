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

package jp.terasoluna.fw.batch.monitor;

/**
 * JMXによる監視対象が実装するインタフェース。
 * 
 */
public interface MonitorableJobStatusMBean {


    /**
     * ジョブ状態を返却する。
     * @return ジョブ状態
     */
    String getJobStateStr();

    /**
     * ジョブ開始日時を返却する、
     * @return ジョブ開始日時
     */
    String getJobStartTime();

    /**
     * ジョブ処理時間を返却する。
     * @return 処理時間
     */
    String getProcessingTime();

    /**
     * コミット回数を返却する。
     * @return コミット数
     */
    int getCommitCount();

    /**
     * ビジネスロジックが返却したNORMAL_CONTINUEの件数を返却する。
     * @return NORMAL_CONTINUE件数
     */
    int getNormalContinueCount();

    /**
     * ビジネスロジックが返却したERROR_CONTINUEの件数を返却する。
     * @return ERROR_CONTINUE件数
     */
    int getErrorContinueCount();

    /**
     * ジョブ（子ジョブ）を強制終了する。
     */
    void shutdownGraceful();

    /**
     * ジョブ（子ジョブ）を中断終了する。
     */
    void shutdownImmediate();

}
