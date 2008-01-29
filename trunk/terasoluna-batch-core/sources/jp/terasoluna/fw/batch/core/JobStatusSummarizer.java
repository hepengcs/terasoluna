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

/**
 * 処理状態のサマライザ。<BR>
 * 子供の処理状態リストを確認し、サマライザする。<BR>
 * ジョブ毎に<code>bean</code>定義の<code>id="JobStatusSummarizer"</code>を
 * 定義することで実装を変更することができる。<BR>
 * ジョブ毎の設定が無い場合はフレームワークのデフォルト設定に従い処理を行う。
 * <BR>フレームワークのデフォルト設定は<code>id="JobStatusSummarizer"</code>を
 * 参照のこと。
 *
 */
public interface JobStatusSummarizer {

    /**
     * ジョブ処理状態の結果ハンドリング。
     * 
     * @param jobStatus
     *            処理対象の処理状況
     */
    void summarize(JobStatus jobStatus);
}
