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

package jp.terasoluna.fw.batch.init;

import jp.terasoluna.fw.batch.core.WorkUnit;
import jp.terasoluna.fw.batch.openapi.JobContext;

/**
 * ジョブ管理テーブルから取得したジョブ依頼情報用<code>Abstract</code>。
 * ジョブ管理テーブルから取得したジョブ依頼情報格納用クラスはこの
 * <code>Abstract</code>クラスを実装する必要がある。
 *
 */
public abstract class AbstractJobControlInfo implements JobInfo, WorkUnit {

    /**
     * ジョブデーモン終了用ジョブIDの文字例。
     */
    public static final String STOP_DEMON = "STOP"; 
    
    /**
     * ジョブパラメータの区切り文字。
     */
    private String jobParametersSplitStr = null;

    /**
     * ジョブ終了コードを設定。
     *
     * @param jobExitCode ジョブ終了コード
     */
    public abstract void setJobExitCode(String jobExitCode);
    
    /**
     * ジョブ起動状況を設定。
     *
     * @param jobState ジョブ起動状況
     */
    public abstract void setJobState(String jobState);
    
    /**
     * キューの終端であるか評価する。
     *
     * @return <code>false</code>を返す
     */
    public final boolean isEndMark() {
        return false;
    }
    
    /**
     * ジョブパラメータの区切り文字。
     * 
     * @param jobParametersSplitStr ジョブパラメータの区切り文字
     */
    public void setJobParametersSplitStr(String jobParametersSplitStr) {
        this.jobParametersSplitStr = jobParametersSplitStr;
    }

    /**
     * ジョブコンテキストを取得する。
     *
     * <p>このクラスの実装では、常に <code>UnsupportedOperationException</code>
     * をスローする。</p>
     * 
     * @return 常に <code>UnsupportedOperationException</code> をスローするため
     * 、リターンしない
     */
    public final JobContext getJobContext() {
        throw new UnsupportedOperationException();
    }

    /**
     * ジョブコンテキストを設定する。
     * 
     * <p>このクラスの実装では、常に <code>UnsupportedOperationException</code>
     * をスローする。</p>
     *
     * @param jobContext ジョブコンテクスト
     */
    public final void setJobContext(JobContext jobContext) {
        throw new UnsupportedOperationException();
    }

    /**
     * ジョブパラメータの区切り文字を取得する。
     * 
     * @return ジョブパラメータの区切り文字
     */
    protected String getJobParametersSplitStr() {
        return jobParametersSplitStr;
    }

}
