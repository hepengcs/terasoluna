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
 * �W���u�I���t�@�C���`�F�b�N�N���X�B
 *
 */
public class EndFileChecker extends TimerTask {

    /**
     * ���O�C���X�^���X�B
     */
    private static Log log = LogFactory.getLog(EndFileChecker.class);

    /**
     * �����I�����ʃL�[�B
     */
    private static final String EXTENSION_IMMEDIATE = "end";

    /**
     * ���f�I�����ʃL�[�B
     */
    private static final String EXTENSION_GRACEFUL = "irp";

    /**
     * �����󋵊i�[�pMap�B
     */
    private Map<String, JobStatus> jobStatusMap;

    /**
     * �I���t�@�C���f�B���N�g���B
     */
    private String endFileDir = null;

    /**
     * �R���X�g���N�^�B
     * �W���u�󋵊i�[�pMap������������B
     *
     */
    public EndFileChecker() {
        jobStatusMap = new HashMap<String, JobStatus>();
    }

    /**
     * �W���u�I���t�@�C���Ď��N���p���\�b�h�B
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
     * �e�W���u��jobStatus��ݒ肷��B
     *
     * @param jobStatus
     *            �e�W���u�̎���
     * @param jobID
     *            �W���uID
     * @param jobOptionID
     *            �W���u�v���Z�XID �܂��� �W���u���s�˗��ԍ�
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
     * �W���u�I��������s���B
     *
     * @param endFileDir
     *            �I���t�@�C���f�B���N�g��
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
     * �W���u���I������B
     *
     * @param endType �I�����
     * @param processKey �I������Ώۂ�Map
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
     * �I������Ώۂ�Map�ɓo�^���ꂽ�L�[���擾�B
     *
     * @param endFileName �I���t�@�C����
     * @return �I������Ώۂ�Map�ɓo�^���ꂽ�L�[
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
     * �I���t�@�C���f�B���N�g����ݒ肷��B
     *
     * @param endFileDir �I���t�@�C���f�B���N�g��
     */
    public void setEndFileDir(String endFileDir) {
        this.endFileDir = endFileDir;
    }
}
