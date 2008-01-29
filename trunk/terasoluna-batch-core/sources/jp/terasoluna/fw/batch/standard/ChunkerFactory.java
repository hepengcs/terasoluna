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

import jp.terasoluna.fw.batch.core.CollectedDataHandlerFactory;
import jp.terasoluna.fw.batch.core.InitializeException;
import jp.terasoluna.fw.batch.core.WorkQueue;
import jp.terasoluna.fw.batch.openapi.JobContext;

/**
 * チャンカーを生成するファクトリクラス。
 *
 */
public class ChunkerFactory implements CollectedDataHandlerFactory {

    /**
     * チャンクサイズ。
     */
    private int chunkSize = 0;
    
    /**
     * チャンカーを生成する。
     *
     * @param queue チャンカーが生成したチャンクをセットするキュー
     * @param jobContext ジョブコンテクスト
     * @return チャンカー
     */
    public Chunker getHandler(WorkQueue queue, JobContext jobContext) {
        return new Chunker(queue, chunkSize, jobContext);
    }

    /**
     * チャンクサイズを設定する。
     * 
     * @param chunkSize チャンクサイズ
     */
    public void setChunkSize(int chunkSize) {
        
        if (chunkSize <= 0) {
        StringBuilder builder = new StringBuilder("ChunkSize is illegal. ");
        builder.append(chunkSize);
            throw new InitializeException(builder.toString());
        }
            
        this.chunkSize = chunkSize;
    }
}
