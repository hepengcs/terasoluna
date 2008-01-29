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

import java.util.List;

import jp.terasoluna.fw.batch.core.InitializeException;
import jp.terasoluna.fw.batch.openapi.ControlBreakHandler;
import jp.terasoluna.fw.batch.openapi.JobContext;

/**
 * <P>コントロールブレイクキー定義情報。</P>
 * <P>ユーザが定義するコントロールブレイクのブレイクキー情報を格納するクラス。</P>
 * <P>コントロールブレイクキーリストはチャンクコントロールブレイクキーリストの
 *  範囲を超えることはできない。</P>
 * <P>トランスコントロールブレイクキーリストはチャンクコントロールブレイクキー
 * リストを 含む定義にする必要がある。</P>
 * 
 */
public class ControlBreakDef {

    /**
     * コントロールブレイクの「ブレイクキー、ハンドラ」リスト。
     * （コントロールブレイク範囲：小）
     */
    private List<ControlBreakDefItem> controlBreakDefItemList = null;

    /**
     * チャンクコントロールブレイクの「ブレイクキー、ハンドラ」。
     * （コントロールブレイク範囲：中）
     */
    private ControlBreakDefItem chunkControlBreakDefItem = null;

    /**
     * トランスチャンクコントロールブレイクの「ブレイクキー、ハンドラ」リスト。
     * （コントロールブレイク範囲：大）
     */
    private List<ControlBreakDefItem> transChunkControlBreakDefItemList = null;

    /**
     * <P>初期化処理。</P>
     * <P>ブレイクキーの設定内容をチェックする。</P>
     * <P>コントロールブレイクキーリストがチャンクコントロールブレイクキーリスト
     * を含めてない場合は<code>InitializeException</code>を発生させ処理を中断す
     * る。</P>
     * <P>チャンクコントロールブレイクキーリストの定義がある場合、 
     * チャックコントロールブレイクキーリストがチャンクコントロールブレイクキー
     * リストを含めてない場合は<code>InitializeException</code>を発生させ処理を
     * 中断する。</P>
     */
    public void init() {
        
        List breakKeyList = null;
        
        //【チャンクコントロールブレイクキー】の設定が無く、
        //【チャンクコントロールブレイクハンドラ】の設定が無い場合は
        //【トランスコントロールブレイクキー】は設定できない
        if (chunkControlBreakDefItem == null
                || (chunkControlBreakDefItem.getBreakKey().size() == 0 
                && chunkControlBreakDefItem.getControlBreakHandler() == null)) {
            if (transChunkControlBreakDefItemList != null
                    && transChunkControlBreakDefItemList.size() != 0) {
                throw new InitializeException(
                        "TransChunkControlBreakDefItemList setup of "
                        + "ChunkControlBreakDefItem is required to set up "
                        + "TransChunkControlBreakDefItemList.");
            }
        } else {
            breakKeyList = chunkControlBreakDefItem.getBreakKey();
        }

        // 【チャンクコントロールブレイクキー】に設定さえてないキーを【トランス
        //  コントロールブレイクキー】に設定することはできない。
        //  トランスコントロールブレイクキーの設定は包含関係である必要がある。
        for (int i = 0; breakKeyList != null
                && transChunkControlBreakDefItemList != null
                && i < transChunkControlBreakDefItemList.size(); i++) {
            List checkBreakKeyList = transChunkControlBreakDefItemList.get(i)
                    .getBreakKey();

            List nextBreakKeyList = null;
            if (i + 1 < transChunkControlBreakDefItemList.size()) {
                nextBreakKeyList = transChunkControlBreakDefItemList.get(i + 1)
                        .getBreakKey();
            }

            for (int k = 0; k < checkBreakKeyList.size(); k++) {
                if (k >= breakKeyList.size()
                        || !checkBreakKeyList.get(k)
                                .equals(breakKeyList.get(k))) {
                    throw new InitializeException(
                            "It is necessary to define TransChunkControlBreak"
                            + "DefItemList within the limits of ChunkControl"
                            + "BreakDefItem");
                }
            }

            for (int k = 0; nextBreakKeyList != null
                    && k < nextBreakKeyList.size(); k++) {
                if (k >= checkBreakKeyList.size()) {
                    throw new InitializeException(
                            "A transChunkControlBreakDefItemList needs to"
                            + " define it as becoming the break of a higher "
                            + "rank from a low-ranking break.");
                }
                if (!checkBreakKeyList.get(k).equals(nextBreakKeyList.get(k))) {
                    throw new InitializeException(
                            "A setup of a BreakKey needs to be an inclusive "
                            + "relation.");
                }
            }

            // 同一のブレイクキーに2つ以上コントローラが定義されているかチェック
            if (nextBreakKeyList != null
                    && checkBreakKeyList.size() == nextBreakKeyList.size()) {
                int sameCount = 0;
                for (int m = 0; m < checkBreakKeyList.size(); m++) {
                    for (int n = 0; n < nextBreakKeyList.size(); n++) {
                        if (checkBreakKeyList.get(m).equals(
                                nextBreakKeyList.get(n))) {
                            sameCount++;
                            break;
                        }
                    }
                }
                if (checkBreakKeyList.size() == sameCount) {
                    throw new InitializeException(
                            "Two or more TransChunkControlBreak cannot"
                            + " be defined as the same Break key.");
                }
            }
        }

        // 【コントロールブレイクキー】は【チャンクコントロールブレイクキー】を
        //  包含したキーリストである必要がある。
        for (int i = 0; breakKeyList != null 
                && controlBreakDefItemList != null
                && i < controlBreakDefItemList.size(); i++) {
            List checkBreakKeyList = controlBreakDefItemList.get(i)
                    .getBreakKey();
            for (int k = 0; k < breakKeyList.size(); k++) {
                if (k >= checkBreakKeyList.size()
                        || !checkBreakKeyList.get(k)
                                .equals(breakKeyList.get(k))) {
                    throw new InitializeException(
                            "It is necessary to define ChunkControl"
                            + "BreakDefItem within the limits of Control"
                            + "BreakDefItemList");
                }
            }
        }

        // コントロールブレイクキーの設定は包含関係である必要がある。
        for (int i = 0; controlBreakDefItemList != null
                && i < controlBreakDefItemList.size(); i++) {
            List checkBreakKeyList = controlBreakDefItemList.get(i)
                    .getBreakKey();
            List nextBreakKeyList = null;
            if (i + 1 < controlBreakDefItemList.size()) {
                nextBreakKeyList = controlBreakDefItemList.get(i + 1)
                        .getBreakKey();
            }

            for (int k = 0; nextBreakKeyList != null
                    && k < nextBreakKeyList.size(); k++) {
                if (k >= checkBreakKeyList.size()) {
                    throw new InitializeException(
                            "A controlBreakDefItemList needs to define"
                            + " it as becoming the break of a higher rank"
                            + " from a low-ranking break.");
                }
                if (!checkBreakKeyList.get(k).equals(nextBreakKeyList.get(k))) {
                    throw new InitializeException(
                            "A setup of a BreakKey needs to be an inclusive"
                            + " relation.");
                }
            }

            // 同一のブレイクキーに2つ以上コントローラが定義されているかチェック
            if (nextBreakKeyList != null
                    && checkBreakKeyList.size() == nextBreakKeyList.size()) {
                int sameCount = 0;
                for (int m = 0; m < checkBreakKeyList.size(); m++) {
                    for (int n = 0; n < nextBreakKeyList.size(); n++) {
                        if (checkBreakKeyList.get(m).equals(
                                nextBreakKeyList.get(n))) {
                            sameCount++;
                            break;
                        }
                    }
                }
                if (checkBreakKeyList.size() == sameCount) {
                    throw new InitializeException(
                            "Two or more ControlBreak cannot be defined"
                            + " as the same Break key.");
                }
            }
        }

    }

    /**
     * トランザクショナルコントロールブレイクキーリストを取得する。
     * 
     * @return トランザクショナルコントロールブレイクキーリスト。
     */
    public ControlBreakDefItem getChunkControlBreakDefItem() {
        return chunkControlBreakDefItem;
    }

    /**
     * チェック別コントロールブレイクキーリストを設定する。
     * 
     * @param chunkControlBreakDefItem
     *            チェック別コントロールブレイクキーリスト。
     */
    public void setChunkControlBreakDefItem(
            ControlBreakDefItem chunkControlBreakDefItem) {
        this.chunkControlBreakDefItem = chunkControlBreakDefItem;
    }

    /**
     * 単一コントロールブレイクキーリストを取得する。
     * 
     * @return チェック別コントロールブレイクキーリスト。
     */
    public List<ControlBreakDefItem> getControlBreakDefItemList() {
        return controlBreakDefItemList;
    }

    /**
     * 単一コントロールブレイクキーリストを取得する。
     * 
     * @param controlBreakDefItemList
     *            単一コントロールブレイクキーリスト。
     */
    public void setControlBreakDefItemList(
            List<ControlBreakDefItem> controlBreakDefItemList) {
        this.controlBreakDefItemList = controlBreakDefItemList;
    }

    /**
     * トランコントロールブレイクキーリストを取得する。
     * 
     * @return トランコントロールブレイクキーリスト。
     */
    public List<ControlBreakDefItem> getTransChunkControlBreakDefItemList() {
        return transChunkControlBreakDefItemList;
    }

    /**
     * トランコントロールブレイクキーリストを取得する。
     * 
     * @param transChunkControlBreakDefItemList
     *            トランコントロールブレイクキーリスト。
     */
    public void setTransChunkControlBreakDefItemList(
            List<ControlBreakDefItem> transChunkControlBreakDefItemList) {
        this.transChunkControlBreakDefItemList = 
            transChunkControlBreakDefItemList;
    }

    /**
     * トランコントロールブレイクキーリストのキーリスト値を取得する。
     * 
     * @param transChunkControlBreakkey トランコントロールブレイクキーリストの
     * キー
     * @return トランコントロールブレイクキーリストのハンドラリスト値。
     */
    public ControlBreakHandler<JobContext> getTransChunkControlBreakHandler(
            List<String> transChunkControlBreakkey) {
        if (transChunkControlBreakkey == null
                || transChunkControlBreakkey.size() == 0) {
            throw new NullPointerException("TransChunkControlBreakkey is Null");
        }

        if (transChunkControlBreakDefItemList == null
                || transChunkControlBreakDefItemList.size() == 0) {
            throw new IllegalArgumentException(
                    "ControlBreakHandler not found: "
                            + transChunkControlBreakkey.toString());
        }

        for (ControlBreakDefItem controlBreakDefItem
                : transChunkControlBreakDefItemList) {
            if (controlBreakDefItem.getBreakKey().equals(
                    transChunkControlBreakkey)) {
                return controlBreakDefItem.getControlBreakHandler();
            }
        }

        throw new IllegalArgumentException("ControlBreakHandler not found: "
                + transChunkControlBreakkey.toString());
    }

    /**
     * トランコントロールブレイクキーリストのキーリスト値を取得する。
     * 
     * @param controlBreakkey トランコントロールブレイクキーリストのキー
     * @return トランコントロールブレイクキーリストのハンドラリスト値。
     */
    public ControlBreakHandler<JobContext> getControlBreakHandler(
            List<String> controlBreakkey) {
        if (controlBreakkey == null || controlBreakkey.size() == 0) {
            throw new NullPointerException("ControlBreakkey is Null");
        }

        if (controlBreakDefItemList == null
                || controlBreakDefItemList.size() == 0) {
            throw new IllegalArgumentException(
                    "ControlBreakHandler not found: "
                            + controlBreakkey.toString());
        }

        for (ControlBreakDefItem controlBreakDefItem : controlBreakDefItemList)
        {
            if (controlBreakDefItem.getBreakKey().equals(controlBreakkey)) {
                return controlBreakDefItem.getControlBreakHandler();
            }
        }

        throw new IllegalArgumentException("ControlBreakHandler not found: "
                + controlBreakkey.toString());
    }
}
