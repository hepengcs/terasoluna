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
 * �R���g���[���u���C�N���������s����N���X�B
 * 
 */
public class ControlBreakProcessor {

    /**
     * �R���g���[���u���C�N�ݒ���B
     */
    private ControlBreakDef controlBreakDef = null;

    /**
     * �r�W�l�X���W�b�N���ʏ����n���h���B
     */
    private BLogicResultHandler blogicResultHandler = null;
    
    /**
     * ��O�n���h�����i�[����Map�B
     */
    private Map<JobException, JobExceptionHandler> exceptionHandlerMap = null;

    /**
     * �f�t�H���g��O�n���h���B
     */
    private JobExceptionHandler defaultJobExceptionHandler = null;

    /**
     * �`�����N�͈͂ƃR���g���[���u���C�N�͈͂�������
     * �R���g���[���u���C�N�ɂ��� �R���g���[���u���C�N�������s���B
     * 
     * @param controlBreakChunk �R���g���[���u���C�N�p�`�����N
     * @param jobStatus �W���u�X�e�[�^�X
     * @param batchUpdateMapList �o�b�`�X�V���X�g
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

        // �R���g���[���u���C�N�������N��
        processControlBreak(controlBreakChunk.getJobContext(), jobStatus,
                batchUpdateMapList, chunkControlBreakKeyMap,
                controlBreakHandler);
    }

    /**
     * �`�����N�͈͂����R���g���[���u���C�N�͈͂��L��
     * �R���g���[���u���C�N�ɂ��� �R���g���[���u���C�N�������s���B
     * 
     * @param controlBreakChunk �R���g���[���u���C�N�p�`�����N
     * @param jobStatus �W���u�X�e�[�^�X
     * @param batchUpdateMapList �o�b�`�X�V���X�g
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
                // �R���g���[���u���C�N�n���h���ɂ���ďI����ԂɂȂ����ꍇ�ɂ́A
                // �㑱�̃R���g���[���u���C�N�������s��Ȃ�
                return;
            }

            ControlBreakHandler<JobContext> controlBreakHandler = 
                controlBreakDef
                   .getTransChunkControlBreakHandler(transChunkControlBreakKey);
            for (String propertyName : transChunkControlBreakKey) {
                Object value = chunkControlBreakKeyMap.get(propertyName);
                transChunkControlBreakKeyMap.put(propertyName, value);
            }
            // �R���g���[���u���C�N�������N��
            processControlBreak(controlBreakChunk.getJobContext(), jobStatus,
                    batchUpdateMapList, transChunkControlBreakKeyMap,
                    controlBreakHandler);

        }
    }

    /**
     * �S�g�����X�`�����N�R���g���[���u���C�N���N������B
     * �ŏI�`�����N�̂̏ꍇ�͑S�g�����X�`�����N�R���g���[���u���C�N�����s�B
     * 
     * @param controlBreakChunk �R���g���[���u���C�N�p�`�����N
     * @param jobStatus �W���u�X�e�[�^�X
     * @param batchUpdateMapList �o�b�`�X�V���X�g
     */
    public void doAllTransChunkControlBreak(
            ControlBreakChunk controlBreakChunk, JobStatus jobStatus,
            List<LinkedHashMap<String, Object>> batchUpdateMapList) {
        // �`�����N���R���g���[���u���C�N�����ʂ�����тȂ���
        Map<String, Object> allChunkControlBreakKeyMap = controlBreakChunk
                .getChunkControlBreakMap();

        // �`�����N���R���g���[���u���C�N�����ʂ�����тȂ���
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

            // �W���u�X�e�[�^�X���`�F�b�N����
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

            // �R���g���[���u���C�N�������N��
            processControlBreak(controlBreakChunk.getJobContext(), jobStatus,
                    batchUpdateMapList, breakKeyValueMap, controlBreakHandler);

        }
    }
    
    /**
     * �`�����N�͈͂Ɗ֌W�Ȃ��ABLogic���s����
     * �R���g���[���u���C�N�ɂ��� �R���g���[���u���C�N�������s���B
     * 
     * @param controlBreakRowObject �R���g���[���u���C�N�pRow�I�u�W�F�N�g
     * @param jobContext �W���u�R���e�L�X�g
     * @param jobStatus �W���u�X�e�[�^�X
     * @param batchUpdateMapList �o�b�`�X�V���X�g
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
                    // �R���g���[���u���C�N�n���h���ɂ���ďI����ԂɂȂ����ꍇ�ɂ́A
                    // �㑱�̃R���g���[���u���C�N�������s��Ȃ�
                    return;
                }
                ControlBreakHandler<JobContext> controlBreakHandler = 
                    controlBreakDef.getControlBreakHandler(controlBreakKey);
                for (String propertyName : controlBreakKey) {
                    Object value = allControlBreakKeyMap.get(propertyName);
                    controlBreakKeyMap.put(propertyName, value);
                }
                // �R���g���[���u���C�N�������N��
                processControlBreak(jobContext, jobStatus, batchUpdateMapList,
                        controlBreakKeyMap, controlBreakHandler);
            }
        }
    }

    /**
     * �S�R���g���[���u���C�N���N������B �ŏI�`�����N�̍Ō�̃f�[�^�������̑S
     * �R���g���[���u���C�N�����s�B
     * 
     * @param controlBreakRowObject
     *            �R���g���[���u���C�N�pRow�I�u�W�F�N�g
     * @param jobContext
     *            �W���u�R���e�L�X�g
     * @param jobStatus
     *            �W���u�X�e�[�^�X
     * @param batchUpdateMapList
     *            �o�b�`�X�V���X�g
     */
    public void doAllChunkInternalControlBreak(
            ControlBreakRowObject controlBreakRowObject, JobContext jobContext,
            JobStatus jobStatus,
            List<LinkedHashMap<String, Object>> batchUpdateMapList) {
        // �`�����N���R���g���[���u���C�N�����ʂ�����тȂ���
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

            // �W���u�X�e�[�^�X���`�F�b�N����
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
     * �R���g���[���u���C�N�����s����B
     * 
     * @param jobContext
     *            �W���u�R���e�L�X�g
     * @param jobStatus
     *            �W���u�X�e�[�^�X
     * @param batchUpdateMapList
     *            �o�b�`�X�V���X�g
     * @param chunkControlBreakKeyMap
     *            �R���g���[���u���C�N�L�[�}�b�v
     * @param controlBreakHandler
     *            �R���g���[���u���C�N�n���h��
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
            // BLogic�ABLogic�������ʃn���h���Ŕ���������O�̏���
            BLogicException wrappingException = new BLogicException(e,
                    chunkControlBreakKeyMap, handlerResult);

            JobExceptionHandler handler = ExceptionHandlerUtil
                    .getJobExceptionHandler(wrappingException,
                            exceptionHandlerMap, defaultJobExceptionHandler);

            handler.handlException(jobContext, wrappingException, jobStatus);
        }
    }

    /**
     * BLogic���ʃn���h����ݒ肷��B
     * 
     * @param blogicResultHandler
     *            BLogic���ʃn���h��
     */
    public void setBlogicResultHandler(
            BLogicResultHandler blogicResultHandler) {
        this.blogicResultHandler = blogicResultHandler;
    }

    /**
     * �R���g���[���u���C�N�L�[��`����ݒ肷��B
     * 
     * @param controlBreakDef �R���g���[���u���C�N�L�[��`���
     */
    public void setControlBreakDef(ControlBreakDef controlBreakDef) {
        this.controlBreakDef = controlBreakDef;
    }

    /**
     * �f�t�H���g��O�n���h����ݒ肷��B
     * 
     * @param defaultJobExceptionHandler �f�t�H���g��O�n���h��
     */
    public void setDefaultJobExceptionHandler(
            JobExceptionHandler defaultJobExceptionHandler) {
        this.defaultJobExceptionHandler = defaultJobExceptionHandler;
    }

    /**
     * ��O�n���h���}�b�v��ݒ肷��B
     * 
     * @param exceptionHandlerMap ��O�n���h���}�b�v
     */
    public void setExceptionHandlerMap(
            Map<JobException, JobExceptionHandler> exceptionHandlerMap) {
        this.exceptionHandlerMap = exceptionHandlerMap;
    }
}
