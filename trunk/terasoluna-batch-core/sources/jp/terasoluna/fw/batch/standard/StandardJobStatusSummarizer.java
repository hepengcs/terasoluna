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

import jp.terasoluna.fw.batch.core.JobStatus;
import jp.terasoluna.fw.batch.core.JobStatusSummarizer;

/**
 * 処理状態のサマライザ実装クラス。<BR>
 * 子供の処理状態リストを確認し、サマライザする。<BR>
 * デフォルト実装では、子供の処理結果から重要な結果を優先し、親状態として設定
 * する。
 * （起動状況番号が大きい方が重要度が高いと判断する。）><BR>ジョブ毎に
 * インタフェース<code>JobStatusSummarizer</code>の実装クラスを 
 * <code>bean</code>定義の<code>id="JobStatusSummarizer"</code>に定義することで
 * 実装を変更することができる。<BR>
 * 
 */
public class StandardJobStatusSummarizer implements JobStatusSummarizer {

    /**
     * ジョブ処理状態の結果サマライザ。
     * ジョブ処理状態を設定する。 
     * 子ジョブの終了状態を確認し、大きいものを優先して設定する。
     * 
     * @param jobStatus
     *            処理対象の処理状況
     */
    public void summarize(JobStatus jobStatus) {
        if (jobStatus.getChildJobStatusList() == null
                || jobStatus.getChildJobStatusList().isEmpty()) {
            return;
        }

        // 子供の処理状況を確認し、一番重大な結果(ジョブ状態のint表現が大きい
        // 数字)を親の処理状況に設定する
        for (JobStatus childJobStatus : jobStatus.getChildJobStatusList()) {
            summarize(childJobStatus);

            if (jobStatus.getJobState().ordinal()
                    < childJobStatus.getJobState().ordinal()
                    && (jobStatus.getJobState() != JobStatus.STATE.STARTED
                    || childJobStatus.getJobState() 
                    != JobStatus.STATE.RESTARTED)) {

                jobStatus.setJobState(childJobStatus.getJobState());
                jobStatus.setJobExitCode(childJobStatus.getJobExitCode());
            }
        }
        
        summarizeExitCode(jobStatus);
    }
    
    
    /**
     * ジョブ処理状態の終了コードサマライザ。
     * ジョブ処理状態を設定する。 
     * 親の終了コードが設定されていない場合
     * 子ジョブの終了コードを確認し、大きいものを優先して設定する。
     * 
     * @param jobStatus
     *            処理対象の処理状況
     */
    private void summarizeExitCode(JobStatus jobStatus) {
        if (jobStatus.getJobExitCode() != null) {
            return;
        }
        Integer exitCode = null;
        for (JobStatus childJobStatus : jobStatus.getChildJobStatusList()) {
            summarizeExitCode(childJobStatus);
            if (childJobStatus.getJobExitCode() != null
                    && (jobStatus.getJobState() 
                            == childJobStatus.getJobState())) {
                if (exitCode == null) {
                    exitCode = childJobStatus.getJobExitCode();
                } else {
                    if ((exitCode < childJobStatus.getJobExitCode())) {
                        exitCode = childJobStatus.getJobExitCode();
                    }
                }

            }
        }
        jobStatus.setJobExitCode(exitCode);
    }
}
