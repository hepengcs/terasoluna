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

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import jp.terasoluna.fw.batch.core.Chunk;
import jp.terasoluna.fw.batch.core.JobStatus;
import jp.terasoluna.fw.batch.core.JobWorker;

/**
 * コントロールブレイク用ワーカクラス。 
 * <code>JobWorker</code>の実行後チャンクコントロールブレイク処理を実行する。
 * 
 */
public class ControlBreakWorker extends JobWorker {

    /**
     * コントロールブレイク処理用クラス。
     */
    private ControlBreakProcessor controlBreakProcessor = null;
    
    /**
     * <code>Chunk</code> の処理後、コントロールブレイクの設定があれば
     * コントロールブレイクハンドラを実行する。
     *
     * @param chunk コントロールブレイク用処理対象データを格納したチャンク
     * @param jobStatus ジョブステータス
     */
    @Override
    public void work(Chunk chunk, JobStatus jobStatus) {
        ControlBreakChunk controlBreakChunk = (ControlBreakChunk) chunk;
        super.work(chunk, jobStatus);

        // 終了のときは、コントロールブレイク処理を行わない
        if (!jobStatus.isContinue()) {
            return;
        }

        List<LinkedHashMap<String, Object>> batchUpdateMapList = 
            new ArrayList<LinkedHashMap<String, Object>>();

        // チャンク範囲とコントロールブレイク範囲が等しいコントロールブレイクに
        // ついてコントロールブレイク処理を行う。
        if (controlBreakChunk.size() > 1) {
            controlBreakProcessor.doChunkControlBreak(controlBreakChunk,
                    jobStatus, batchUpdateMapList);
        }

        if (jobStatus.getJobState() == JobStatus.STATE.ENDING_NORMALLY) {
            // コントロールブレイクハンドラによって正常終了したとき
            processBatchUpdate(controlBreakChunk.getJobContext(), jobStatus,
                    batchUpdateMapList);
            return;
        } else if (!jobStatus.isContinue()) {
            // 異常終了、中断終了のときは、後続のコントロールブレイク処理を
            // 行わない
            return;
        }

        // 最終チャンクである場合は、全トランスチャックブレイク処理を実行する。
        if (controlBreakChunk.isEndChunk()) {
            // 最終チャンクがEND_MARKのみのときは処理しない
            if (controlBreakChunk.size() > 1) {
                controlBreakProcessor.doAllTransChunkControlBreak(
                        controlBreakChunk, jobStatus, batchUpdateMapList);
            }
        } else {
            // チャンク範囲よりもコントロールブレイク範囲が広いコントロールブレ
            // イクについてコントロールブレイク処理を行う。
            controlBreakProcessor.doTransChunkControlBreak(controlBreakChunk,
                    jobStatus, batchUpdateMapList);
        }

        if (jobStatus.getJobState() == JobStatus.STATE.ENDING_NORMALLY
                || jobStatus.isContinue()) {
            // コントロールブレイクハンドラによって正常終了したとき、または処理
            // 継続状態であるとき
            processBatchUpdate(controlBreakChunk.getJobContext(), jobStatus,
                    batchUpdateMapList);
        }
    }
    
    /**
     * コントロールブレイク処理用クラスを取得する。
     * 
     * @param controlBreakProcessor
     *            コントロールブレイク処理用クラス
     */
    public void setControlBreakProcessor(
            ControlBreakProcessor controlBreakProcessor) {
        this.controlBreakProcessor = controlBreakProcessor;
    }
}
