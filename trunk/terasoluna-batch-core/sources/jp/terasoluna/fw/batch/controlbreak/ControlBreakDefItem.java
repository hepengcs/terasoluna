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

import jp.terasoluna.fw.batch.openapi.ControlBreakHandler;
import jp.terasoluna.fw.batch.openapi.JobContext;

/**
 * 各ブレイクキー毎のハンドラクラス情報を保持するクラス。
 * 
 */
public class ControlBreakDefItem {

    /**
     * ブレイクキー。
     */
    private List<String> breakKey = new ArrayList<String>();
    
    /**
     * コントロールブレイクハンドラ。
     */
    private ControlBreakHandler<JobContext> controlBreakHandler = null;

    /**
     * ブレイクキーを取得する。
     * 
     * @return ブレイクキー 
     */
    public List<String> getBreakKey() {
        return breakKey;
    }

    /**
     * ブレイクキーを設定する。
     * 
     * @param breakkey ブレイクキー 
     */
    public void setBreakKey(List<String> breakkey) {
        this.breakKey = breakkey;
    }

    /**
     * コントロールブレイクハンドラを取得する。
     * 
     * @return コントロールブレイクハンドラ
     */
    public ControlBreakHandler<JobContext> getControlBreakHandler() {
        return controlBreakHandler;
    }

    /**
     * コントロールブレイクハンドラを設定する。
     * 
     * @param controlBreakHandler コントロールブレイクハンドラ
     */
    public void setControlBreakHandler(
            ControlBreakHandler<JobContext> controlBreakHandler) {
        this.controlBreakHandler = controlBreakHandler;
    }
}
