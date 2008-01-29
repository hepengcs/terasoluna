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

import java.util.LinkedHashMap;
import java.util.List;

import jp.terasoluna.fw.batch.core.BLogicExecutor;
import jp.terasoluna.fw.batch.core.JobStatus;
import jp.terasoluna.fw.batch.openapi.JobContext;

/**
 * �r�W�l�X���W�b�N���s�N���X����������C���^�t�F�[�X�̎����N���X�B
 * �r�W�l�X���W�b�N�̎�����A�R���g���[���u���C�N�������s���B
 * 
 */
public class ControlBreakBLogicExecutor implements BLogicExecutor {

    /**
     * �r�W�l�X���W�b�N���s�N���X�̃C���X�^���X�B
     * 
     * <p>�r�W�l�X���W�b�N���s�����́A���̃C���X�^���X�ɈϏ������B</p>
     */
    private BLogicExecutor blogicExecutor = null;
    
    /**
     * �O��Ăяo�����̃r�W�l�X���W�b�N���̓f�[�^�B
     */
    private ControlBreakRowObject previousData = null;
    
    /**
     * �u���C�N�����N���X�B
     */
    private ControlBreakProcessor controlBreakProcessor = null;
    
    /**
     * �r�W�l�X���W�b�N�����s��A�w�肳�ꂽ�R���g���[���u���C�N����������΁A
     * �u���C�N���������s����B
     * �܂��A�����̍Ō�ł���Β�`���ꂽ�S�Ẵu���C�N���������s����B
     * 
     *
     * @param blogicInputData �����Ώۃf�[�^��ێ�����R���g���[���u���C�NRow�f
     * �[�^
     * @param jobContext �W���u�R���e�L�X�g
     * @param jobStatus �W���u������
     * @param batchUpdateMapList �o�b�`�X�V���X�g
     */
    public void executeBLogic(Object blogicInputData, JobContext jobContext,
            JobStatus jobStatus,
            List<LinkedHashMap<String, Object>> batchUpdateMapList) {
        
        // �ŏI��Row�I�u�W�F�N�g�N���X�B
        ControlBreakRowObject lastData = null;
        
        ControlBreakRowObject controlBreakRowObject = 
            (ControlBreakRowObject) blogicInputData;
        if (previousData == null) {
            previousData = controlBreakRowObject;
            return;
        }

        // �I����Ԃ̏ꍇ�ɂ̓R���g���[���u���C�N������
        // �s�킸�A���̂܂܃��^�[������B
        if (!jobStatus.isContinue()) {
            return;
        }

        /*
         * �O��f�[�^�܂ł̃R���g���[���u���C�N�������s��
         */
        if (previousData.getControlBreakKeyList().size() > 0) {
            controlBreakProcessor.doControlBreak(previousData, jobContext,
                    jobStatus, batchUpdateMapList);
        }

        // �I����Ԃ̏ꍇ�ɂ̓r�W�l�X���W�b�N������
        // �s�킸�A���̂܂܃��^�[������B
        if (!jobStatus.isContinue()) {
            return;
        }

        // �O��f�[�^�Ńr�W�l�X���W�b�N�Ăяo�����Ϗ�����B
        blogicExecutor.executeBLogic(previousData.getRowObject(), jobContext,
                jobStatus, batchUpdateMapList);

        /*
         * �`�����N�I�[�}�[�N�i�O��f�[�^�����p�̃_�~�[�j�̂Ƃ� �R���g���[���u��
         * �C�N�������s���B
         */
        if (controlBreakRowObject.getRowObject() == ControlBreakChunk.
                CHUNK_BREAK_MARK) {
            // �I����Ԃ̏ꍇ�ɂ̓R���g���[���u���C�N������
            // �s�킸�A���̂܂܃��^�[������B
            if (!jobStatus.isContinue()) {
                return;
            }
            if (controlBreakRowObject.getControlBreakKeyList().size() > 0) {
                controlBreakProcessor.doControlBreak(controlBreakRowObject,
                        jobContext, jobStatus, batchUpdateMapList);
            }
            previousData = null;
        } else {
            previousData = controlBreakRowObject;
        }

        // �ŏI�f�[�^�̂Ƃ��́A�S�̎��s
        if (controlBreakRowObject.getRowObject()
                == ControlBreakChunk.END_MARK) {
            lastData = new ControlBreakRowObject(previousData.getRowObject(),
                    controlBreakRowObject.getControlBreakKeyList(),
                    controlBreakRowObject.getControlBreakMap());
            controlBreakProcessor.doAllChunkInternalControlBreak(lastData,
                    jobContext, jobStatus, batchUpdateMapList);
            return;
        }

    }

    /**
     * �r�W�l�X���W�b�N���s�N���X�̃C���X�^���X��ݒ肷��B
     * 
     * @param blogicExecutor �r�W�l�X���W�b�N���s�N���X�̃C���X�^���X
     */
    public void setBlogicExecutor(BLogicExecutor blogicExecutor) {
        this.blogicExecutor = blogicExecutor;
    }

    /**
     * �u���C�N�����N���X��ݒ肷��B
     * 
     * @param controlBreakProcessor �u���C�N�����N���X�̃C���X�^���X
     */
    public void setControlBreakProcessor(
            ControlBreakProcessor controlBreakProcessor) {
        this.controlBreakProcessor = controlBreakProcessor;
    }
}
