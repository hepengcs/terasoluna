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

import jp.terasoluna.fw.batch.core.Chunk;
import jp.terasoluna.fw.batch.core.CollectedDataHandler;
import jp.terasoluna.fw.batch.core.WorkQueue;
import jp.terasoluna.fw.batch.openapi.JobContext;

/**
 * 処理対象データを指定されたサイズ分まとめて、チャンクを作成するクラス。
 *
 */
public class Chunker implements CollectedDataHandler {

    /**
     * キュー。
     */
    private WorkQueue queue;

    /**
     * チャンクサイズ。
     */
    private int chunkSize;

    /**
     * 作成中のチャンク。
     */
    private Chunk currentChunk;
    
    /**
     * ジョブコンテクスト。
     */
    private JobContext jobContext;

    /**
     * コンストラクタ。
     *
     * @param queue ワークキュー
     * @param chunkSize チャンクサイズ
     * @param jobContext ジョブコンテクスト
     */
    public Chunker(WorkQueue queue, int chunkSize, JobContext jobContext) {
        this.queue = queue;
        this.chunkSize = chunkSize;
        this.jobContext = jobContext;
        currentChunk = new Chunk(chunkSize, jobContext);
    }

    /**
     * 処理対象データをキューに追加する。
     *
     * @param row 処理対象データ
     * @param index 処理対象データのインデックス
     */
    public void handle(Object row, int index) {
        currentChunk.add(row);
        if (currentChunk.size() >= chunkSize) {
            queue.put(currentChunk);
            currentChunk = new Chunk(chunkSize, jobContext);
        }
    }

    /**
     * クローズ処理を行う。
     *
     */
    public void close() {
        if (currentChunk.size() > 0) {
            queue.put(currentChunk);
        }
        queue.close();
    }
}
