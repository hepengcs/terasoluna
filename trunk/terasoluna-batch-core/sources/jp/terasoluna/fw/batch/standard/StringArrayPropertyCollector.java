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

package jp.terasoluna.fw.batch.standard;

import jp.terasoluna.fw.batch.core.AbstractCollector;
import jp.terasoluna.fw.batch.core.CollectedDataHandler;
import jp.terasoluna.fw.batch.core.CollectorResult;
import jp.terasoluna.fw.batch.core.JobStatus;
import jp.terasoluna.fw.batch.openapi.JobContext;
import jp.terasoluna.fw.batch.openapi.ReturnCode;

/**
 * 属性に設定された文字列配列から、取得処理を行うコレクタ。
 * 
 * <p>Springの定義ファイルで、取得データをこのコレクタの属性値としてインジェクシ
 * ョンして使うことができる。</p>
 * <p>Bean定義ファイルにプロパティ設定が存在しなかった場合には例外として処理さ
 * れる。</p>
 * 
 */
public class StringArrayPropertyCollector extends AbstractCollector {

    /**
     * 取得対象の配列。
     */
    private String[] dataArray = null;
    
    /**
     * 取得処理を行う。
     * 
     * <p>取得対象の配列からデータを取得する。</p>
     * 
     * @param jobContext ジョブコンテクスト
     * @param collectedDataHandler 取得データを処理するハンドラ
     * @param jobStatus ジョブステータス
     * @return 取得処理結果
     */
    @Override
    protected CollectorResult doCollect(JobContext jobContext,
            CollectedDataHandler collectedDataHandler, JobStatus jobStatus) {
        int collected = 0;
        for (Object listItem : dataArray) {
            collectedDataHandler.handle(listItem, collected++);
            jobStatus.incrementCollected();
        }
        return new CollectorResult(ReturnCode.NORMAL_END, collected);
    }
    
    /**
     * 取得対象の配列を設定する。
     * 
     * @param dataArray 取得対象の配列
     */
    public void setDataArray(String[] dataArray) {
        this.dataArray = dataArray;
    }
}
