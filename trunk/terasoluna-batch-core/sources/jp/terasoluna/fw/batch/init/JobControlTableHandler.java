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

package jp.terasoluna.fw.batch.init;

/**
 * ジョブ管理テーブル管理用インタフェース。<BR>
 *  このインタフェースを実装することでジョブ管理テーブルの
 * ジョブ以来情報取得、ジョブ開始処理、ジョブ終了処理を行うことが出来る。
 *
 *
 * @see JobControlTableHandlerImpl
 */
public interface JobControlTableHandler {

    /**
     * ジョブ依頼情報の取得。
     *
     * @return ジョブ依頼情報を格納した<code>JobInfo</code>のインスタン
     * ス。
     */
    JobInfo getJobRequestData();

    /**
     * 引数(ジョブ依頼)の次のジョブ依頼情報の取得。
     * 
     * @param jobInfo 前回取得した<code>JobInfo</code>のインスタンス。
     * @return 引数(ジョブ依頼)の次のジョブ依頼情報を格納した
     * <code>JobInfo</code>のインスタンス。
     */
    JobInfo getJobRequestData(JobInfo jobInfo);

    /**
     * 対象のジョブの「起動状況」を起動中に更新。
     *
     * @param jobInfo 更新対象のジョブ依頼情報
     * @return 更新されたレコード数。
     */
    int updateJobStart(JobInfo jobInfo);

    /**
     * 対象のジョブの「起動状況」を終了状態に更新。
     *
     * @param jobInfo 更新対象のジョブ依頼情報
     * @return 更新されたレコード数。
     */
    int updateJobEnd(JobInfo jobInfo);

}
