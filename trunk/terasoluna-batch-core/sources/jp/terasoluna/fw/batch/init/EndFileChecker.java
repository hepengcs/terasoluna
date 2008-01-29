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

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimerTask;

import jp.terasoluna.fw.batch.core.JobStatus;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * ジョブ終了ファイルチェッククラス。
 *
 */
public class EndFileChecker extends TimerTask {

    /**
     * ログインスタンス。
     */
    private static Log log = LogFactory.getLog(EndFileChecker.class);

    /**
     * 強制終了識別キー。
     */
    private static final String EXTENSION_IMMEDIATE = "end";

    /**
     * 中断終了識別キー。
     */
    private static final String EXTENSION_GRACEFUL = "irp";

    /**
     * 処理状況格納用Map。
     */
    private Map<String, JobStatus> jobStatusMap;

    /**
     * 終了ファイルディレクトリ。
     */
    private String endFileDir = null;

    /**
     * コンストラクタ。
     * ジョブ状況格納用Mapを初期化する。
     *
     */
    public EndFileChecker() {
        jobStatusMap = new HashMap<String, JobStatus>();
    }

    /**
     * ジョブ終了ファイル監視起動用メソッド。
     *
     */
    @Override
    public synchronized void run() {
        File dir = new File(endFileDir);

        if (!jobStatusMap.isEmpty()) {
            processEnd(dir);
        }
    }

    /**
     * 親ジョブのjobStatusを設定する。
     *
     * @param jobStatus
     *            親ジョブの実績
     * @param jobID
     *            ジョブID
     * @param jobOptionID
     *            ジョブプロセスID または ジョブ実行依頼番号
     */
    public void addParentJobStatus(JobStatus jobStatus, String jobID,
            String jobOptionID) {
        
        if (jobID == null || "".equals(jobID)) {
            return;
        }
        
        if (jobOptionID == null || "".equals(jobOptionID)) {
            jobStatusMap.put(jobID , jobStatus);    
        } else {
            jobStatusMap.put(jobID + "_" + jobOptionID, jobStatus);
        }
    }

    /**
     * ジョブ終了制御を行う。
     *
     * @param endFileDir
     *            終了ファイルディレクトリ
     */
    private void processEnd(File endFileDir) {
                
        String[] fileNames = endFileDir.list();

        if (fileNames == null || fileNames.length == 0) {
            return;
        }
        for (String fileName : fileNames) {
            String[] splitFileName = fileName.split("[.]");

            if (splitFileName.length == 2) {
                String endFileName = splitFileName[0];
                String endType = splitFileName[1];
                List<String> processKey = getProcessKey(endFileName);
                endJob(endType, processKey);
            }

        }

    }

    /**
     * ジョブを終了する。
     *
     * @param endType 終了種別
     * @param processKey 終了制御対象のMap
     */
    private void endJob(String endType, List<String> processKey) {

        for (String key : processKey) {

            JobStatus jobStatus = jobStatusMap.get(key);

            if (EXTENSION_IMMEDIATE.equals(endType)) {
                jobStatus.shutdownImmediate();

                if (log.isDebugEnabled()) {
                    log.debug(key + " shutdown_immediate");
                }

            } else if (EXTENSION_GRACEFUL.equals(endType)) {
                jobStatus.shutdownGraceful();

                if (log.isDebugEnabled()) {
                    log.debug(key + " shutdown_graceful");
                }
            } else {
                if (log.isDebugEnabled()) {
                   log.debug("\"." + endType + "\" is invalid file extension.");
                }
            }

        }
    }

    /**
     * 終了制御対象のMapに登録されたキーを取得。
     *
     * @param endFileName 終了ファイル名
     * @return 終了制御対象のMapに登録されたキー
     */
    private List<String> getProcessKey(String endFileName) {
        
        Set<String> keyset = jobStatusMap.keySet();
        List<String> processKey = new ArrayList<String>();
        
        String processKeyPrefix = endFileName + "_";
        
        for (String jobStatusKey : keyset) {
                if (jobStatusKey.equals(endFileName)
                        || jobStatusKey.startsWith(processKeyPrefix)) {
                processKey.add(jobStatusKey);
            }
        }

        return processKey;
    }

    /**
     * 終了ファイルディレクトリを設定する。
     *
     * @param endFileDir 終了ファイルディレクトリ
     */
    public void setEndFileDir(String endFileDir) {
        this.endFileDir = endFileDir;
    }
}
