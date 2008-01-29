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

package jp.terasoluna.fw.batch.controlbreak;

import java.util.LinkedHashMap;
import java.util.List;

import jp.terasoluna.fw.batch.core.BLogicExecutor;
import jp.terasoluna.fw.batch.core.JobStatus;
import jp.terasoluna.fw.batch.openapi.JobContext;

/**
 * ビジネスロジック実行クラスが実装するインタフェースの実装クラス。
 * ビジネスロジックの事項後、コントロールブレイク処理を行う。
 * 
 */
public class ControlBreakBLogicExecutor implements BLogicExecutor {

    /**
     * ビジネスロジック実行クラスのインスタンス。
     * 
     * <p>ビジネスロジック実行処理は、このインスタンスに委譲される。</p>
     */
    private BLogicExecutor blogicExecutor = null;
    
    /**
     * 前回呼び出し時のビジネスロジック入力データ。
     */
    private ControlBreakRowObject previousData = null;
    
    /**
     * ブレイク処理クラス。
     */
    private ControlBreakProcessor controlBreakProcessor = null;
    
    /**
     * ビジネスロジックを実行後、指定されたコントロールブレイク処理があれば、
     * ブレイク処理を実行する。
     * また、処理の最後であれば定義された全てのブレイク処理を実行する。
     * 
     *
     * @param blogicInputData 処理対象データを保持するコントロールブレイクRowデ
     * ータ
     * @param jobContext ジョブコンテキスト
     * @param jobStatus ジョブ処理状況
     * @param batchUpdateMapList バッチ更新リスト
     */
    public void executeBLogic(Object blogicInputData, JobContext jobContext,
            JobStatus jobStatus,
            List<LinkedHashMap<String, Object>> batchUpdateMapList) {
        
        // 最終のRowオブジェクトクラス。
        ControlBreakRowObject lastData = null;
        
        ControlBreakRowObject controlBreakRowObject = 
            (ControlBreakRowObject) blogicInputData;
        if (previousData == null) {
            previousData = controlBreakRowObject;
            return;
        }

        // 終了状態の場合にはコントロールブレイク処理を
        // 行わず、そのままリターンする。
        if (!jobStatus.isContinue()) {
            return;
        }

        /*
         * 前回データまでのコントロールブレイク処理を行う
         */
        if (previousData.getControlBreakKeyList().size() > 0) {
            controlBreakProcessor.doControlBreak(previousData, jobContext,
                    jobStatus, batchUpdateMapList);
        }

        // 終了状態の場合にはビジネスロジック処理を
        // 行わず、そのままリターンする。
        if (!jobStatus.isContinue()) {
            return;
        }

        // 前回データでビジネスロジック呼び出しを委譲する。
        blogicExecutor.executeBLogic(previousData.getRowObject(), jobContext,
                jobStatus, batchUpdateMapList);

        /*
         * チャンク終端マーク（前回データ処理用のダミー）のとき コントロールブレ
         * イク処理を行う。
         */
        if (controlBreakRowObject.getRowObject() == ControlBreakChunk.
                CHUNK_BREAK_MARK) {
            // 終了状態の場合にはコントロールブレイク処理を
            // 行わず、そのままリターンする。
            if (!jobStatus.isContinue()) {
                return;
            }
            if (controlBreakRowObject.getControlBreakKeyList().size() > 0) {
                controlBreakProcessor.doControlBreak(controlBreakRowObject,
                        jobContext, jobStatus, batchUpdateMapList);
            }
            previousData = null;
        } else {
            previousData = controlBreakRowObject;
        }

        // 最終データのときは、全体実行
        if (controlBreakRowObject.getRowObject()
                == ControlBreakChunk.END_MARK) {
            lastData = new ControlBreakRowObject(previousData.getRowObject(),
                    controlBreakRowObject.getControlBreakKeyList(),
                    controlBreakRowObject.getControlBreakMap());
            controlBreakProcessor.doAllChunkInternalControlBreak(lastData,
                    jobContext, jobStatus, batchUpdateMapList);
            return;
        }

    }

    /**
     * ビジネスロジック実行クラスのインスタンスを設定する。
     * 
     * @param blogicExecutor ビジネスロジック実行クラスのインスタンス
     */
    public void setBlogicExecutor(BLogicExecutor blogicExecutor) {
        this.blogicExecutor = blogicExecutor;
    }

    /**
     * ブレイク処理クラスを設定する。
     * 
     * @param controlBreakProcessor ブレイク処理クラスのインスタンス
     */
    public void setControlBreakProcessor(
            ControlBreakProcessor controlBreakProcessor) {
        this.controlBreakProcessor = controlBreakProcessor;
    }
}
