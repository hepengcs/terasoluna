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

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import jp.terasoluna.fw.batch.core.BLogicException;
import jp.terasoluna.fw.batch.core.BLogicResultHandler;
import jp.terasoluna.fw.batch.core.ExceptionHandlerUtil;
import jp.terasoluna.fw.batch.core.JobException;
import jp.terasoluna.fw.batch.core.JobExceptionHandler;
import jp.terasoluna.fw.batch.core.JobStatus;
import jp.terasoluna.fw.batch.openapi.BLogicResult;
import jp.terasoluna.fw.batch.openapi.ControlBreakHandler;
import jp.terasoluna.fw.batch.openapi.JobContext;

/**
 * コントロールブレイク処理を実行するクラス。
 * 
 */
public class ControlBreakProcessor {

    /**
     * コントロールブレイク設定情報。
     */
    private ControlBreakDef controlBreakDef = null;

    /**
     * ビジネスロジック結果処理ハンドラ。
     */
    private BLogicResultHandler blogicResultHandler = null;
    
    /**
     * 例外ハンドラを格納したMap。
     */
    private Map<JobException, JobExceptionHandler> exceptionHandlerMap = null;

    /**
     * デフォルト例外ハンドラ。
     */
    private JobExceptionHandler defaultJobExceptionHandler = null;

    /**
     * チャンク範囲とコントロールブレイク範囲が等しい
     * コントロールブレイクについて コントロールブレイク処理を行う。
     * 
     * @param controlBreakChunk コントロールブレイク用チャンク
     * @param jobStatus ジョブステータス
     * @param batchUpdateMapList バッチ更新リスト
     */
    public void doChunkControlBreak(
            ControlBreakChunk controlBreakChunk,
            JobStatus jobStatus, 
            List<LinkedHashMap<String, Object>> batchUpdateMapList) {
        ControlBreakDefItem chunkControlBreakDefItem = controlBreakDef
                .getChunkControlBreakDefItem();

        if (chunkControlBreakDefItem.getBreakKey().size() == 0) {
            return;
        }

        Map<String, Object> chunkControlBreakKeyMap = 
            controlBreakChunk.getChunkControlBreakMap();
        ControlBreakHandler<JobContext> controlBreakHandler = 
            chunkControlBreakDefItem.getControlBreakHandler();

        // コントロールブレイク処理を起動
        processControlBreak(controlBreakChunk.getJobContext(), jobStatus,
                batchUpdateMapList, chunkControlBreakKeyMap,
                controlBreakHandler);
    }

    /**
     * チャンク範囲よりもコントロールブレイク範囲が広い
     * コントロールブレイクについて コントロールブレイク処理を行う。
     * 
     * @param controlBreakChunk コントロールブレイク用チャンク
     * @param jobStatus ジョブステータス
     * @param batchUpdateMapList バッチ更新リスト
     */
    public void doTransChunkControlBreak(ControlBreakChunk controlBreakChunk,
            JobStatus jobStatus,
            List<LinkedHashMap<String, Object>> batchUpdateMapList) {

        List<List<String>> transChunkControlBreakKeyList = controlBreakChunk
                .getTransChunkControlBreakKeyList();

        Map<String, Object> chunkControlBreakKeyMap = controlBreakChunk
                .getChunkControlBreakMap();

        Map<String, Object> transChunkControlBreakKeyMap = 
            new HashMap<String, Object>();
        for (List<String> transChunkControlBreakKey
                : transChunkControlBreakKeyList) {
            transChunkControlBreakKeyMap.clear();
            if (transChunkControlBreakKey.size() == 0) {
                continue;
            }
            if (!jobStatus.isContinue()) {
                // コントロールブレイクハンドラによって終了状態になった場合には、
                // 後続のコントロールブレイク処理を行わない
                return;
            }

            ControlBreakHandler<JobContext> controlBreakHandler = 
                controlBreakDef
                   .getTransChunkControlBreakHandler(transChunkControlBreakKey);
            for (String propertyName : transChunkControlBreakKey) {
                Object value = chunkControlBreakKeyMap.get(propertyName);
                transChunkControlBreakKeyMap.put(propertyName, value);
            }
            // コントロールブレイク処理を起動
            processControlBreak(controlBreakChunk.getJobContext(), jobStatus,
                    batchUpdateMapList, transChunkControlBreakKeyMap,
                    controlBreakHandler);

        }
    }

    /**
     * 全トランスチャンクコントロールブレイクを起動する。
     * 最終チャンクのの場合は全トランスチャンクコントロールブレイクを実行。
     * 
     * @param controlBreakChunk コントロールブレイク用チャンク
     * @param jobStatus ジョブステータス
     * @param batchUpdateMapList バッチ更新リスト
     */
    public void doAllTransChunkControlBreak(
            ControlBreakChunk controlBreakChunk, JobStatus jobStatus,
            List<LinkedHashMap<String, Object>> batchUpdateMapList) {
        // チャンク内コントロールブレイクを下位から並びなおす
        Map<String, Object> allChunkControlBreakKeyMap = controlBreakChunk
                .getChunkControlBreakMap();

        // チャンク内コントロールブレイクを下位から並びなおす
        List<ControlBreakDefItem> transChunkControlBreakDefItemList = 
            controlBreakDef.getTransChunkControlBreakDefItemList();

        HashMap<String, Object> breakKeyValueMap =
            new HashMap<String, Object>();
        for (ControlBreakDefItem transChunkControlBreakDefItem
                : transChunkControlBreakDefItemList) {
            breakKeyValueMap.clear();
            if (transChunkControlBreakDefItem.getBreakKey().size() == 0) {
                continue;
            }

            // ジョブステータスをチェックする
            if (!jobStatus.isContinue()) {
                return;
            }
            for (String propertyName : transChunkControlBreakDefItem
                    .getBreakKey()) {
                Object value = allChunkControlBreakKeyMap.get(propertyName);
                breakKeyValueMap.put(propertyName, value);
            }

            ControlBreakHandler<JobContext> controlBreakHandler = 
                transChunkControlBreakDefItem.getControlBreakHandler();

            // コントロールブレイク処理を起動
            processControlBreak(controlBreakChunk.getJobContext(), jobStatus,
                    batchUpdateMapList, breakKeyValueMap, controlBreakHandler);

        }
    }
    
    /**
     * チャンク範囲と関係なく、BLogic実行毎に
     * コントロールブレイクについて コントロールブレイク処理を行う。
     * 
     * @param controlBreakRowObject コントロールブレイク用Rowオブジェクト
     * @param jobContext ジョブコンテキスト
     * @param jobStatus ジョブステータス
     * @param batchUpdateMapList バッチ更新リスト
     */
    public void doControlBreak(ControlBreakRowObject controlBreakRowObject,
            JobContext jobContext, JobStatus jobStatus,
            List<LinkedHashMap<String, Object>> batchUpdateMapList) {

        List<List<String>> controlBreakKeyList = controlBreakRowObject
                .getControlBreakKeyList();

        Map<String, Object> allControlBreakKeyMap = controlBreakRowObject
                .getControlBreakMap();

        Map<String, Object> controlBreakKeyMap = new HashMap<String, Object>();
        if (controlBreakKeyList.size() > 0) {
            for (List<String> controlBreakKey : controlBreakKeyList) {
                controlBreakKeyMap.clear();
                if (controlBreakKey.size() == 0) {
                    continue;
                }
                if (!jobStatus.isContinue()) {
                    // コントロールブレイクハンドラによって終了状態になった場合には、
                    // 後続のコントロールブレイク処理を行わない
                    return;
                }
                ControlBreakHandler<JobContext> controlBreakHandler = 
                    controlBreakDef.getControlBreakHandler(controlBreakKey);
                for (String propertyName : controlBreakKey) {
                    Object value = allControlBreakKeyMap.get(propertyName);
                    controlBreakKeyMap.put(propertyName, value);
                }
                // コントロールブレイク処理を起動
                processControlBreak(jobContext, jobStatus, batchUpdateMapList,
                        controlBreakKeyMap, controlBreakHandler);
            }
        }
    }

    /**
     * 全コントロールブレイクを起動する。 最終チャンクの最後のデータ処理をの全
     * コントロールブレイクを実行。
     * 
     * @param controlBreakRowObject
     *            コントロールブレイク用Rowオブジェクト
     * @param jobContext
     *            ジョブコンテキスト
     * @param jobStatus
     *            ジョブステータス
     * @param batchUpdateMapList
     *            バッチ更新リスト
     */
    public void doAllChunkInternalControlBreak(
            ControlBreakRowObject controlBreakRowObject, JobContext jobContext,
            JobStatus jobStatus,
            List<LinkedHashMap<String, Object>> batchUpdateMapList) {
        // チャンク内コントロールブレイクを下位から並びなおす
        Map<String, Object> allControlBreakKeyMap = controlBreakRowObject
                .getControlBreakMap();
        List<ControlBreakDefItem> controlBreakDefItemListReverseOrder = 
            controlBreakDef.getControlBreakDefItemList();

        HashMap<String, Object> breakKeyValueMap = 
            new HashMap<String, Object>();
        for (ControlBreakDefItem controlBreakDefItem
                : controlBreakDefItemListReverseOrder) {
            breakKeyValueMap.clear();
            if (controlBreakDefItem.getBreakKey().size() == 0) {
                continue;
            }

            // ジョブステータスをチェックする
            if (!jobStatus.isContinue()) {
                return;
            }
            for (String propertyName : controlBreakDefItem.getBreakKey()) {
                Object value = allControlBreakKeyMap.get(propertyName);
                breakKeyValueMap.put(propertyName, value);
            }

            ControlBreakHandler<JobContext> controlBreakHandler =
                controlBreakDefItem.getControlBreakHandler();

            processControlBreak(jobContext, jobStatus, batchUpdateMapList,
                    breakKeyValueMap, controlBreakHandler);
        }
    }

    /**
     * コントロールブレイクを実行する。
     * 
     * @param jobContext
     *            ジョブコンテキスト
     * @param jobStatus
     *            ジョブステータス
     * @param batchUpdateMapList
     *            バッチ更新リスト
     * @param chunkControlBreakKeyMap
     *            コントロールブレイクキーマップ
     * @param controlBreakHandler
     *            コントロールブレイクハンドラ
     */
    private void processControlBreak(JobContext jobContext,
            JobStatus jobStatus,
            List<LinkedHashMap<String, Object>> batchUpdateMapList,
            Map<String, Object> chunkControlBreakKeyMap,
            ControlBreakHandler<JobContext> controlBreakHandler) {

        BLogicResult handlerResult = null;
        try {
            handlerResult = controlBreakHandler.handleControlBreak(
                    chunkControlBreakKeyMap, jobContext);

            blogicResultHandler.handle(handlerResult, chunkControlBreakKeyMap,
                    jobStatus, batchUpdateMapList);
        } catch (RuntimeException e) {
            // BLogic、BLogic処理結果ハンドラで発生した例外の処理
            BLogicException wrappingException = new BLogicException(e,
                    chunkControlBreakKeyMap, handlerResult);

            JobExceptionHandler handler = ExceptionHandlerUtil
                    .getJobExceptionHandler(wrappingException,
                            exceptionHandlerMap, defaultJobExceptionHandler);

            handler.handlException(jobContext, wrappingException, jobStatus);
        }
    }

    /**
     * BLogic結果ハンドラを設定する。
     * 
     * @param blogicResultHandler
     *            BLogic結果ハンドラ
     */
    public void setBlogicResultHandler(
            BLogicResultHandler blogicResultHandler) {
        this.blogicResultHandler = blogicResultHandler;
    }

    /**
     * コントロールブレイクキー定義情報を設定する。
     * 
     * @param controlBreakDef コントロールブレイクキー定義情報
     */
    public void setControlBreakDef(ControlBreakDef controlBreakDef) {
        this.controlBreakDef = controlBreakDef;
    }

    /**
     * デフォルト例外ハンドラを設定する。
     * 
     * @param defaultJobExceptionHandler デフォルト例外ハンドラ
     */
    public void setDefaultJobExceptionHandler(
            JobExceptionHandler defaultJobExceptionHandler) {
        this.defaultJobExceptionHandler = defaultJobExceptionHandler;
    }

    /**
     * 例外ハンドラマップを設定する。
     * 
     * @param exceptionHandlerMap 例外ハンドラマップ
     */
    public void setExceptionHandlerMap(
            Map<JobException, JobExceptionHandler> exceptionHandlerMap) {
        this.exceptionHandlerMap = exceptionHandlerMap;
    }
}
