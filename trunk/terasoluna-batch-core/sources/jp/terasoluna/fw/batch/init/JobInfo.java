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

import java.io.Serializable;

/**
 * ジョブ依頼情報取得用インタフェース。
 *
 */
public interface JobInfo extends Serializable {

    /**
     * ジョブリクエスト番号を取得する。
     *
     * @return ジョブリクエスト番号
     */
    String getJobRequestNo();

    /**
     * ジョブIDを取得する。
     *
     * @return ジョブID
     */
    String getJobId();

    /**
     * ジョブBean定義ファイルのPATHを取得する。
     *
     * @return ジョブBean定義ファイルのPATH
     */
    String getJobDiscriptorPath();

    /**
     * ジョブ起動パラメータを取得する。
     *
     * @return ジョブ起動パラメータ
     */
    String[] getJobParameters();
}
