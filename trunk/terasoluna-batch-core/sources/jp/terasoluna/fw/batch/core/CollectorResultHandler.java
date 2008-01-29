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
 * 対象データ取得結果のハンドラインタフェース。
 * 
 * <p>対象データ取得処理の終了後に、対象データ取得処理が返却した対象データ取得結果を処理する
 * ために起動される。対象データ取得結果のハンドラでは、対象データ取得結果、
 * およびジョブステータスが渡される。
 * </p>
 * 
 * <p>対象データ取得結果をジョブステータスへ反映することができる。</p>
 *
 */
public interface CollectorResultHandler {

    /**
     * 対象データ取得結果を処理する。
     * 
     * @param result 対象データ取得結果
     * @param jobStatus ジョブステータス
     */
    void handle(CollectorResult result, JobStatus jobStatus);

}