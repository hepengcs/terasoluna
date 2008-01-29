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

import jp.terasoluna.fw.batch.core.AbstractCollector;
import jp.terasoluna.fw.batch.core.CollectedDataHandler;
import jp.terasoluna.fw.batch.core.CollectorResult;
import jp.terasoluna.fw.batch.core.JobStatus;
import jp.terasoluna.fw.batch.openapi.JobContext;
import jp.terasoluna.fw.batch.openapi.ReturnCode;
import jp.terasoluna.fw.file.dao.FileLineException;
import jp.terasoluna.fw.file.dao.FileLineIterator;
import jp.terasoluna.fw.file.dao.FileQueryDAO;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * ファイルCollectorの標準実装クラス。
 * <p>
 *   フレームワークのファイルアクセス機能を用いて入力ファイル(CSV形式、固定長形
 *   式、可変長形式)からの処理対象データの取得機能を提供。
 * </p>
 * <p>
 *  <li>ファイルQueryDAO</li> <li>入力ファイル名</li> <li>結果クラス(ファイル行オブジェ
 *  クト)</li><li>FileLineException時、後続処理判定用フラグ<p>を属性値として
 *  設定する必要がある。</li></p>
 * </p>
 * <br>
 *<strong>設定例</strong>
 * <code><pre>
 *  &lt;!-- コレクター定義 --&gt;
 *   &lt;bean id="collector" parent="fileChunkCollector"&gt
 *       &lt;property name="fileQueryDao" ref="csvFileQueryDAO" /&gt
 *       &lt;property name="inputFileName" value="../INPUTFILE/SAMPLE/sampledata.csv" /&gt
 *       &lt;property name="resultClass"&gt
 *           &lt;bean class="jp.terasoluna.batch.sample.FileLine001" /&gt
 *       &lt;/property&gt
 *       &lt;property name="readNextLine" value="true" /&gt
 *   &lt;/bean&gt
 * </pre></code>
 * 
 */
public class StandardFileCollector extends AbstractCollector {
    
    /**
     * ファイルアクセス用DAO。
     */
    private FileQueryDAO fileQueryDao = null;

    /**
     * 結果クラス。
     */
    private Object resultClass = null;

    /**
     * 入力ファイル名。
     */
    private String inputFileName = null;
    
    /**
     * FileLineExceptionスロー時、後続処理判定用のフラグ。
     */
    private boolean readNextLine = false;
    
    /**
     * ログインスタンス。
     */
    private static Log log = LogFactory.getLog(StandardFileCollector.class);

    /**
     * 入力ファイルからデータを取得する。
     *
     * @param jobContext ジョブコンテクスト
     * @param collectedDataHandler 収集したデータを処理するハンドラ
     * @param jobStatus ジョブステータス
     * @return コレクタの処理結果
     */
    @Override
    protected CollectorResult doCollect(JobContext jobContext,
            CollectedDataHandler collectedDataHandler, JobStatus jobStatus) {
        
        // 処理済行のカウント
        int collected = 0;
        
        // ファイル行オブジェクトを保持するイテレータを取得する
        FileLineIterator fileLineIterator = fileQueryDao.execute(inputFileName,
            resultClass.getClass());
        
        if (fileLineIterator == null) {
            return new CollectorResult(ReturnCode.NORMAL_END, collected);
        }
        
        try {
            while (fileLineIterator.hasNext()) {
                try {
                    // 入力ファイルの1行を取得する
                    Object fileLineObject = fileLineIterator.next();
                    
                    collectedDataHandler.handle(fileLineObject, collected++);
                  
                    // 処理件数を上げる
                    jobStatus.incrementCollected();
                } catch (FileLineException ex) {
                    if (readNextLine) {
                        // ログ出力後、次の行を読む
                        writeWarnLog(ex);
                        continue;
                    } else {
                        throw ex;
                    }
                }
            }
        } finally {
            fileLineIterator.closeFile();
        }
        
        // コレクター結果オブジェクトを返却する
        return new CollectorResult(ReturnCode.NORMAL_END, collected);
        
    }

    /**
     * ファイルアクセス用DAOを設定する。
     *
     * @param fileQueryDao ファイルアクセス用DAO
     */
    public void setFileQueryDao(FileQueryDAO fileQueryDao) {
        this.fileQueryDao = fileQueryDao;
    }

    /**
     * 入力ファイル名を設定する。
     *
     * @param inputFileName 入力ファイル名
     */
    public void setInputFileName(String inputFileName) {
        this.inputFileName = inputFileName;
    }

    /**
     * 結果クラスを設定する。
     *
     * @param resultClass 結果クラス
     */
    public void setResultClass(Object resultClass) {
        this.resultClass = resultClass;
    }
    
    /**
     * ファイル行Iteratorの処理判定用フラグを設定する。
     * 
     * @param readNextLine フラグ
     */
    public void setReadNextLine(boolean readNextLine) {
        this.readNextLine = readNextLine;
    }

    /**
     * 行例外が発生した場合の警告ログを出力する。
     * 
     * @param fileLineException ファイル行例外
     */
    protected void writeWarnLog(FileLineException fileLineException) {
        StringBuilder builder = new StringBuilder();
        builder.append(fileLineException.toString());
        builder.append(" [FileName]:");
        builder.append(fileLineException.getFileName());
        builder.append(" [LineNo]:");
        builder.append(fileLineException.getLineNo());
        builder.append(" [ColumnName]:");
        builder.append(fileLineException.getColumnName());
        log.warn(builder.toString());
    }
    
}
