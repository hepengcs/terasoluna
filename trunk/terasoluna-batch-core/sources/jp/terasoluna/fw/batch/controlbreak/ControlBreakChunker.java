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
import java.util.List;

import org.apache.commons.collections.FastHashMap;

import jp.terasoluna.fw.batch.core.CollectedDataHandler;
import jp.terasoluna.fw.batch.core.WorkQueue;
import jp.terasoluna.fw.batch.openapi.JobContext;


/**
 * コントロールブレイク単位に、チャンクを作成する <code>CollectedDataHandler
 * </code>の実装クラス。
 * 
 * <p>1つのジョブに対して、複数のコントロールブレイクを定義することができる。
 * またコントロールブレイクのうちの１つは、トランザクション境界と一致するように
 * 定義する必要があり、そのようなコントロールブレイクを
 * <em>"トランザクショナルコントロールブレイク"</em>と呼ぶ。</p>
 * 
 * <p><code>Collector</code> から、handle()メソッドで収集したデータの処理を依頼
 * された際に、<code>transactinalBreakKey</p> に設定されているトランザクショナル
 * コントロールブレイクキーの定義に基づいて、チャンクを分割する。</p>
 * 
 * <p><code>controlBreakHandlerMap</code> に設定されているコントロールブレイクの
 * 定義に基づいて、handle()メソッドに渡されたビジネスロジック入力データでコント
 * ロールブレイクが発生しているかどうをチェックする。コントロールブレイクが発生
 * していた場合には、ブレイクキーとそれぞれのブレイクキーの値を設定する。</p>
 * 
 * <p>作成したチャンクは、コンストラクタで渡された <code>WorkQueue</code> に設定
 * する。</p>
 * 
 */
public class ControlBreakChunker implements CollectedDataHandler {

    /**
     * 作成したチャンクをキューイングする <code>WorkQueue</code> インスタンス。
     */
    private WorkQueue queue;
    
    /**
     * コントロールブレイクの定義情報。
     */
    private final ControlBreakDef controlBreakDef;

    /**
     * 処理中のチャンク。
     */
    private ControlBreakChunk currentChunk;
    
    /**
     * ジョブコンテクスト。
     */
    private JobContext jobContext;
    
    /**
     * チャンクサイズ。
     */
    private int chunkSize = 20;
    
    /**
     * チャンクコントロールブレイクの設定有無。
     */
    private boolean chunkControlFlg = false;

    
    /**
     * 次の処理データ
     */
    private ControlBreakRowObject newRow = null;

    
    /**
     * コンストラクタ。
     * 
     * @param queue 作成したチャンクをキューイングする <code>WorkQueue</code> 
     * インスタンス
     * @param jobContext ジョブコンテクスト
     * @param controlBreakDef コントロールブレイクの定義情報
     * @param chunkSize チャンクサイズ
     */
    public ControlBreakChunker(WorkQueue queue, JobContext jobContext,
            ControlBreakDef controlBreakDef, int chunkSize) {
        this.queue = queue;
        this.jobContext = jobContext;
        this.controlBreakDef = controlBreakDef;
        this.chunkSize = chunkSize;
        if (controlBreakDef.getChunkControlBreakDefItem() == null
                || controlBreakDef.getChunkControlBreakDefItem().getBreakKey()
                        .size() == 0) {
            this.chunkControlFlg = true;
        }
        currentChunk = new ControlBreakChunk(jobContext, controlBreakDef,
                chunkSize);
    }

    /**
     * コレクタで収集したデータを処理する。
     * 
     * <p>
     * コントロールブレイクが発生しているかどうかを判定し、トランザクショナルコ
     * ントロールブレイクが発生している場合には、前データまでをチャンクとしてキ
     * ューイングする。
     * </p>
     * 
     * @param row
     *            コレクタで収集したデータ
     * @param collected
     *            <code>row</code> が、収集したデータで何番目のデータであるかを
     *            示すインデックス
     */
    public void handle(Object row, int collected) {
        newRow = currentChunk.setNext(row);

        // コントロールブレイク情報の保持
        if (currentChunk.isChunkControlBreak()
                || (chunkControlFlg && currentChunk.size() >= chunkSize)) {
            // チャンクの終端マークを設定 （次のデータからコントロールブレイク
            // 情報を受け取る）
            ControlBreakRowObject chunkRow = new ControlBreakRowObject(
                    ControlBreakChunk.CHUNK_BREAK_MARK, newRow
                            .getControlBreakKeyList(), newRow
                            .getControlBreakMap());
            currentChunk.add(chunkRow);
            // チャンクデータの処理
            queue.put(currentChunk);
            FastHashMap methodMap = currentChunk.getMethodMap();
            // 現在のコントロールブレイクキー情報を次のチャンクに引継ぐ
            currentChunk = new ControlBreakChunk(jobContext, controlBreakDef,
                    chunkSize, currentChunk.getNextData(), currentChunk
                            .getChunkNextData());
            currentChunk.setMethodMap(methodMap);
            // コントロールブレイク情報をクリア（チャンクの終端マークに移譲した
            // ため）
            newRow = new ControlBreakRowObject(newRow.getRowObject(),
                    new ArrayList<List<String>>(), null);
        }

        currentChunk.add(newRow);
    }

    /**
     * 終了処理を行う。
     * 
     * <p>処理中のチャンクをキューイングし、<code>WorkQueue</code> インスタンス
     * のクローズ処理を行う。</p>
     */
    public void close() {
        ControlBreakRowObject row = null;
        if (newRow != null) {
            row = currentChunk.setLastData(newRow.getRowObject());
        } else {
            row = new ControlBreakRowObject(ControlBreakChunk.END_MARK, null,
                    null);
        }
        currentChunk.add(row);
        currentChunk.setEndChunk(true);
        queue.put(currentChunk);
        currentChunk = null;
        queue.close();
    }
}
