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

import java.util.LinkedHashMap;
import java.util.List;

import jp.terasoluna.fw.batch.core.ExceptionHandlerUtil;
import jp.terasoluna.fw.batch.core.JobException;
import jp.terasoluna.fw.batch.core.JobExceptionHandler;
import jp.terasoluna.fw.batch.core.JobStatus;
import jp.terasoluna.fw.batch.core.SupportLogicException;
import jp.terasoluna.fw.batch.core.SupportLogicResultHandler;
import jp.terasoluna.fw.batch.core.SupportProcessor;
import jp.terasoluna.fw.batch.openapi.BLogicResult;
import jp.terasoluna.fw.batch.openapi.JobContext;
import jp.terasoluna.fw.batch.openapi.SupportLogic;


/**
 * <code>SupportProcessor</code> インタフェースの標準実装クラス。
 *
 * <p>サポートロジックのリストにサポートロジックを順次起動する。
 * 起動したサポートロジックが <code>ERROR_END</code>、あるいは 
 * <code>NORMAL_END</code>を返却した場合にはその時点で処理を中止する。</p>
 * 
 * <p><code>canSkip()</code> が <code>true</code> を返すときには、サポートロジッ
 * クは起動しない。また、引数で渡された <code>jobStatus</code> が継続状態でない
 * 場合（終了状態である場合）には、サポートロジックを起動せずに直ちにリターン
 * する。</p>
 * 
 */
public class StandardSupportProcessor implements SupportProcessor {

    /**
     * サポートロジックのリスト。
     */
    private List<SupportLogic<JobContext>> supportLogicList = null;
    
    /**
     * サポートロジックの処理結果ハンドラ。
     */
    private SupportLogicResultHandler supportLogicResultHandler = null;

    /**
     * 例外ハンドラマップ。
     */
    private LinkedHashMap<JobException, JobExceptionHandler> 
        exceptionHandlerMap = null;

    /**
     * デフォルト例外ハンドラ。
     */
    private JobExceptionHandler defaultJobExceptionHandler = null;
    
    /**
     * 処理名。
     */
    private String supportProcessorName = null;

    /**
     * サポート処理を行う。
     * 
     * <p>属性に設定されたサポートロジックのリストを順次実行する。</p>
     * 
     * @param jobContext ジョブコンテクスト
     * @param jobStatus ジョブステータス
     */
    public void process(JobContext jobContext, JobStatus jobStatus) {
        if (canSkip()) {
            return;
        }
        
        if (!jobStatus.isContinue()) {
            return;
        }
        
        BLogicResult supportLogicResult = null;
        for (SupportLogic<JobContext> supportLogic : supportLogicList) {
            try {
                supportLogicResult = supportLogic.execute(jobContext);
                supportLogicResultHandler.handle(supportLogicResult, jobStatus,
                        supportProcessorName);
            } catch (RuntimeException e) {
                // PreLogicで発生した例外の処理
                SupportLogicException wrappingException 
                    = createSupportLogicException(supportLogicResult, e);

                JobExceptionHandler handler = ExceptionHandlerUtil
                        .getJobExceptionHandler(wrappingException,
                               exceptionHandlerMap, defaultJobExceptionHandler);

                handler.handlException(jobContext, wrappingException,
                        jobStatus);
            }
                
            if (!jobStatus.isContinue()) {
                break;
            }
        }
    }

    /**
     * サポート処理例外を生成する。
     * @param supportLogicResult サポート処理結果
     * @param e 例外
     * @return サポート処理例外インスタンス
     */
    private SupportLogicException createSupportLogicException(
        BLogicResult supportLogicResult, RuntimeException e) {
        SupportLogicException wrappingException = new SupportLogicException(
                e, supportLogicResult, supportProcessorName);
        return wrappingException;
    }

    /**
     * サポート処理がスキップできる場合には、<code>true</code> を返す。 
     * 
     * @return サポートロジックがひとつも設定されていない場合には
     *  <code>true</code>
     */
    public boolean canSkip() {
        return supportLogicList == null || supportLogicList.size() == 0;
    }

    /**
     * サポートロジックのリストを設定する。
     * 
     * @param supportLogicList サポートロジックのリスト
     */
    public void setSupportLogicList(
            List<SupportLogic<JobContext>> supportLogicList) {
        this.supportLogicList = supportLogicList;
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
            LinkedHashMap<JobException,
            JobExceptionHandler> exceptionHandlerMap) {
        this.exceptionHandlerMap = exceptionHandlerMap;
    }

    /**
     * サポートロジックの処理結果ハンドラを設定する。
     * 
     * @param supportLogicResultHandler サポートロジックの処理結果ハンドラ
     */
    public void setSupportLogicResultHandler(
            SupportLogicResultHandler supportLogicResultHandler) {
        this.supportLogicResultHandler = supportLogicResultHandler;
    }

    /**
     * 処理名を設定する。
     * 
     * @param supportProcessorName 処理名
     */
    public void setSupportProcessorName(String supportProcessorName) {
        this.supportProcessorName = supportProcessorName;
    }
}
