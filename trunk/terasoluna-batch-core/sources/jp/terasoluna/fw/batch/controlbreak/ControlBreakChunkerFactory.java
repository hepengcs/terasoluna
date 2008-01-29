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

import jp.terasoluna.fw.batch.core.CollectedDataHandler;
import jp.terasoluna.fw.batch.core.CollectedDataHandlerFactory;
import jp.terasoluna.fw.batch.core.WorkQueue;
import jp.terasoluna.fw.batch.openapi.JobContext;

/**
 * トランザクションナルコントロールブレイク単位にチャンクを作成する
 * <code>ControlBreakChunker</code> を生成するクラス。
 * 
 */
public class ControlBreakChunkerFactory 
    implements CollectedDataHandlerFactory {

    /**
     * コントロールブレイクの定義情報。
     */
    private ControlBreakDef controlBreakDef = null;
    
    /**
     * チャンクサイズ。
     */
    private int chunkSize = 20;
    
    /**
     * トランザクションナルコントロールブレイク単位にチャンクを作成する
     * <code>ControlBreakChunker</code> を生成する。
     * 
     * @param workQueue ワークキュー
     * @param jobContext ジョブコンテクスト
     * @return トランザクションナルコントロールブレイク単位にチャンクを作成する
     * チャンカー
     */
    public CollectedDataHandler getHandler(WorkQueue workQueue,
            JobContext jobContext) {
        return new ControlBreakChunker(workQueue, jobContext, controlBreakDef,
                chunkSize);
    }

    /**
     * コントロールブレイクの定義情報を設定する。
     * 
     * @param controlBreakDef コントロールブレイクの定義情報
     */
    public void setControlBreakDef(ControlBreakDef controlBreakDef) {
        this.controlBreakDef = controlBreakDef;
    }
    
    /**
     * チャンクサイズを設定する。
     * 
     * @param chunkSize チャンクサイズ
     */
    public void setChunkSize(int chunkSize) {
        if (chunkSize > 0) {
            this.chunkSize = chunkSize;
        }
    }
}
