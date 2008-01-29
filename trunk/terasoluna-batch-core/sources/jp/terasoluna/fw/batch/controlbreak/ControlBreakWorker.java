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
import java.util.LinkedHashMap;
import java.util.List;

import jp.terasoluna.fw.batch.core.Chunk;
import jp.terasoluna.fw.batch.core.JobStatus;
import jp.terasoluna.fw.batch.core.JobWorker;

/**
 * �R���g���[���u���C�N�p���[�J�N���X�B 
 * <code>JobWorker</code>�̎��s��`�����N�R���g���[���u���C�N���������s����B
 * 
 */
public class ControlBreakWorker extends JobWorker {

    /**
     * �R���g���[���u���C�N�����p�N���X�B
     */
    private ControlBreakProcessor controlBreakProcessor = null;
    
    /**
     * <code>Chunk</code> �̏�����A�R���g���[���u���C�N�̐ݒ肪�����
     * �R���g���[���u���C�N�n���h�������s����B
     *
     * @param chunk �R���g���[���u���C�N�p�����Ώۃf�[�^���i�[�����`�����N
     * @param jobStatus �W���u�X�e�[�^�X
     */
    @Override
    public void work(Chunk chunk, JobStatus jobStatus) {
        ControlBreakChunk controlBreakChunk = (ControlBreakChunk) chunk;
        super.work(chunk, jobStatus);

        // �I���̂Ƃ��́A�R���g���[���u���C�N�������s��Ȃ�
        if (!jobStatus.isContinue()) {
            return;
        }

        List<LinkedHashMap<String, Object>> batchUpdateMapList = 
            new ArrayList<LinkedHashMap<String, Object>>();

        // �`�����N�͈͂ƃR���g���[���u���C�N�͈͂��������R���g���[���u���C�N��
        // ���ăR���g���[���u���C�N�������s���B
        if (controlBreakChunk.size() > 1) {
            controlBreakProcessor.doChunkControlBreak(controlBreakChunk,
                    jobStatus, batchUpdateMapList);
        }

        if (jobStatus.getJobState() == JobStatus.STATE.ENDING_NORMALLY) {
            // �R���g���[���u���C�N�n���h���ɂ���Đ���I�������Ƃ�
            processBatchUpdate(controlBreakChunk.getJobContext(), jobStatus,
                    batchUpdateMapList);
            return;
        } else if (!jobStatus.isContinue()) {
            // �ُ�I���A���f�I���̂Ƃ��́A�㑱�̃R���g���[���u���C�N������
            // �s��Ȃ�
            return;
        }

        // �ŏI�`�����N�ł���ꍇ�́A�S�g�����X�`���b�N�u���C�N���������s����B
        if (controlBreakChunk.isEndChunk()) {
            // �ŏI�`�����N��END_MARK�݂̂̂Ƃ��͏������Ȃ�
            if (controlBreakChunk.size() > 1) {
                controlBreakProcessor.doAllTransChunkControlBreak(
                        controlBreakChunk, jobStatus, batchUpdateMapList);
            }
        } else {
            // �`�����N�͈͂����R���g���[���u���C�N�͈͂��L���R���g���[���u��
            // �C�N�ɂ��ăR���g���[���u���C�N�������s���B
            controlBreakProcessor.doTransChunkControlBreak(controlBreakChunk,
                    jobStatus, batchUpdateMapList);
        }

        if (jobStatus.getJobState() == JobStatus.STATE.ENDING_NORMALLY
                || jobStatus.isContinue()) {
            // �R���g���[���u���C�N�n���h���ɂ���Đ���I�������Ƃ��A�܂��͏���
            // �p����Ԃł���Ƃ�
            processBatchUpdate(controlBreakChunk.getJobContext(), jobStatus,
                    batchUpdateMapList);
        }
    }
    
    /**
     * �R���g���[���u���C�N�����p�N���X���擾����B
     * 
     * @param controlBreakProcessor
     *            �R���g���[���u���C�N�����p�N���X
     */
    public void setControlBreakProcessor(
            ControlBreakProcessor controlBreakProcessor) {
        this.controlBreakProcessor = controlBreakProcessor;
    }
}
