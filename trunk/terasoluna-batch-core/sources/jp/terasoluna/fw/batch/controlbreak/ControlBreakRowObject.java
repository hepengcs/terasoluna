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
import java.util.Map;

/**
 * コントロールブレイク用Rowオブジェクトクラス。 
 * 処理対象データをラップし、対象データへ コントロールブレイクが
 * 発生しているかどうかを保持させる。
 * 
 */
public class ControlBreakRowObject {

    /**
     * 処理対象データ。
     */
    private Object rowObject;

    /**
     * コントロールブレイクキーリスト。
     */
    private List<List<String>> controlBreakKeyList;

    /**
     * コントロールブレイクキーマップ。
     */
    private Map<String, Object> controlBreakMap;

    /**
     * コンストラクタ。
     * 
     * @param rowObject 処理対象データ
     * @param controlBreakKeyList コントロールブレイクキーリスト
     * @param controlBreakMap コントロールブレイクキーマップ
     */
    public ControlBreakRowObject(Object rowObject,
            List<List<String>> controlBreakKeyList,
            Map<String, Object> controlBreakMap) {
        this.rowObject = rowObject;
        this.controlBreakKeyList = controlBreakKeyList;
        this.controlBreakMap = controlBreakMap;
    }

    /**
     * 処理対象データを取得する。
     * 
     * @return 処理対象データ
     */
    public Object getRowObject() {
        return rowObject;
    }

    /**
     * コントロールブレイクキーリストを取得する。
     * 
     * @return コントロールブレイクキーリスト
     */
    public List<List<String>> getControlBreakKeyList() {
        return controlBreakKeyList;
    }

    /**
     * コントロールブレイクキーマップを取得する。
     * 
     * @return コントロールブレイクキーマップ
     */
    public Map<String, Object> getControlBreakMap() {
        return controlBreakMap;
    }
}
