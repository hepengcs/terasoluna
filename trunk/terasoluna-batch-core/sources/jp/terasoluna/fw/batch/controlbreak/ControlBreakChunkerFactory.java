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

import jp.terasoluna.fw.batch.core.CollectedDataHandler;
import jp.terasoluna.fw.batch.core.CollectedDataHandlerFactory;
import jp.terasoluna.fw.batch.core.WorkQueue;
import jp.terasoluna.fw.batch.openapi.JobContext;

/**
 * �g�����U�N�V�����i���R���g���[���u���C�N�P�ʂɃ`�����N���쐬����
 * <code>ControlBreakChunker</code> �𐶐�����N���X�B
 * 
 */
public class ControlBreakChunkerFactory 
    implements CollectedDataHandlerFactory {

    /**
     * �R���g���[���u���C�N�̒�`���B
     */
    private ControlBreakDef controlBreakDef = null;
    
    /**
     * �`�����N�T�C�Y�B
     */
    private int chunkSize = 20;
    
    /**
     * �g�����U�N�V�����i���R���g���[���u���C�N�P�ʂɃ`�����N���쐬����
     * <code>ControlBreakChunker</code> �𐶐�����B
     * 
     * @param workQueue ���[�N�L���[
     * @param jobContext �W���u�R���e�N�X�g
     * @return �g�����U�N�V�����i���R���g���[���u���C�N�P�ʂɃ`�����N���쐬����
     * �`�����J�[
     */
    public CollectedDataHandler getHandler(WorkQueue workQueue,
            JobContext jobContext) {
        return new ControlBreakChunker(workQueue, jobContext, controlBreakDef,
                chunkSize);
    }

    /**
     * �R���g���[���u���C�N�̒�`����ݒ肷��B
     * 
     * @param controlBreakDef �R���g���[���u���C�N�̒�`���
     */
    public void setControlBreakDef(ControlBreakDef controlBreakDef) {
        this.controlBreakDef = controlBreakDef;
    }
    
    /**
     * �`�����N�T�C�Y��ݒ肷��B
     * 
     * @param chunkSize �`�����N�T�C�Y
     */
    public void setChunkSize(int chunkSize) {
        if (chunkSize > 0) {
            this.chunkSize = chunkSize;
        }
    }
}
