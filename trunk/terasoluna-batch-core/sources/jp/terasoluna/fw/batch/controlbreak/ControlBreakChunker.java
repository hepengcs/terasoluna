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
import java.util.List;

import org.apache.commons.collections.FastHashMap;

import jp.terasoluna.fw.batch.core.CollectedDataHandler;
import jp.terasoluna.fw.batch.core.WorkQueue;
import jp.terasoluna.fw.batch.openapi.JobContext;


/**
 * �R���g���[���u���C�N�P�ʂɁA�`�����N���쐬���� <code>CollectedDataHandler
 * </code>�̎����N���X�B
 * 
 * <p>1�̃W���u�ɑ΂��āA�����̃R���g���[���u���C�N���`���邱�Ƃ��ł���B
 * �܂��R���g���[���u���C�N�̂����̂P�́A�g�����U�N�V�������E�ƈ�v����悤��
 * ��`����K�v������A���̂悤�ȃR���g���[���u���C�N��
 * <em>"�g�����U�N�V���i���R���g���[���u���C�N"</em>�ƌĂԁB</p>
 * 
 * <p><code>Collector</code> ����Ahandle()���\�b�h�Ŏ��W�����f�[�^�̏������˗�
 * ���ꂽ�ۂɁA<code>transactinalBreakKey</p> �ɐݒ肳��Ă���g�����U�N�V���i��
 * �R���g���[���u���C�N�L�[�̒�`�Ɋ�Â��āA�`�����N�𕪊�����B</p>
 * 
 * <p><code>controlBreakHandlerMap</code> �ɐݒ肳��Ă���R���g���[���u���C�N��
 * ��`�Ɋ�Â��āAhandle()���\�b�h�ɓn���ꂽ�r�W�l�X���W�b�N���̓f�[�^�ŃR���g
 * ���[���u���C�N���������Ă��邩�ǂ����`�F�b�N����B�R���g���[���u���C�N������
 * ���Ă����ꍇ�ɂ́A�u���C�N�L�[�Ƃ��ꂼ��̃u���C�N�L�[�̒l��ݒ肷��B</p>
 * 
 * <p>�쐬�����`�����N�́A�R���X�g���N�^�œn���ꂽ <code>WorkQueue</code> �ɐݒ�
 * ����B</p>
 * 
 */
public class ControlBreakChunker implements CollectedDataHandler {

    /**
     * �쐬�����`�����N���L���[�C���O���� <code>WorkQueue</code> �C���X�^���X�B
     */
    private WorkQueue queue;
    
    /**
     * �R���g���[���u���C�N�̒�`���B
     */
    private final ControlBreakDef controlBreakDef;

    /**
     * �������̃`�����N�B
     */
    private ControlBreakChunk currentChunk;
    
    /**
     * �W���u�R���e�N�X�g�B
     */
    private JobContext jobContext;
    
    /**
     * �`�����N�T�C�Y�B
     */
    private int chunkSize = 20;
    
    /**
     * �`�����N�R���g���[���u���C�N�̐ݒ�L���B
     */
    private boolean chunkControlFlg = false;

    
    /**
     * ���̏����f�[�^
     */
    private ControlBreakRowObject newRow = null;

    
    /**
     * �R���X�g���N�^�B
     * 
     * @param queue �쐬�����`�����N���L���[�C���O���� <code>WorkQueue</code> 
     * �C���X�^���X
     * @param jobContext �W���u�R���e�N�X�g
     * @param controlBreakDef �R���g���[���u���C�N�̒�`���
     * @param chunkSize �`�����N�T�C�Y
     */
    public ControlBreakChunker(WorkQueue queue, JobContext jobContext,
            ControlBreakDef controlBreakDef, int chunkSize) {
        this.queue = queue;
        this.jobContext = jobContext;
        this.controlBreakDef = controlBreakDef;
        this.chunkSize = chunkSize;
        if (controlBreakDef.getChunkControlBreakDefItem() == null
                || controlBreakDef.getChunkControlBreakDefItem().getBreakKey()
                        .size() == 0) {
            this.chunkControlFlg = true;
        }
        currentChunk = new ControlBreakChunk(jobContext, controlBreakDef,
                chunkSize);
    }

    /**
     * �R���N�^�Ŏ��W�����f�[�^����������B
     * 
     * <p>
     * �R���g���[���u���C�N���������Ă��邩�ǂ����𔻒肵�A�g�����U�N�V���i���R
     * ���g���[���u���C�N���������Ă���ꍇ�ɂ́A�O�f�[�^�܂ł��`�����N�Ƃ��ăL
     * ���[�C���O����B
     * </p>
     * 
     * @param row
     *            �R���N�^�Ŏ��W�����f�[�^
     * @param collected
     *            <code>row</code> ���A���W�����f�[�^�ŉ��Ԗڂ̃f�[�^�ł��邩��
     *            �����C���f�b�N�X
     */
    public void handle(Object row, int collected) {
        newRow = currentChunk.setNext(row);

        // �R���g���[���u���C�N���̕ێ�
        if (currentChunk.isChunkControlBreak()
                || (chunkControlFlg && currentChunk.size() >= chunkSize)) {
            // �`�����N�̏I�[�}�[�N��ݒ� �i���̃f�[�^����R���g���[���u���C�N
            // �����󂯎��j
            ControlBreakRowObject chunkRow = new ControlBreakRowObject(
                    ControlBreakChunk.CHUNK_BREAK_MARK, newRow
                            .getControlBreakKeyList(), newRow
                            .getControlBreakMap());
            currentChunk.add(chunkRow);
            // �`�����N�f�[�^�̏���
            queue.put(currentChunk);
            FastHashMap methodMap = currentChunk.getMethodMap();
            // ���݂̃R���g���[���u���C�N�L�[�������̃`�����N�Ɉ��p��
            currentChunk = new ControlBreakChunk(jobContext, controlBreakDef,
                    chunkSize, currentChunk.getNextData(), currentChunk
                            .getChunkNextData());
            currentChunk.setMethodMap(methodMap);
            // �R���g���[���u���C�N�����N���A�i�`�����N�̏I�[�}�[�N�Ɉڏ�����
            // ���߁j
            newRow = new ControlBreakRowObject(newRow.getRowObject(),
                    new ArrayList<List<String>>(), null);
        }

        currentChunk.add(newRow);
    }

    /**
     * �I���������s���B
     * 
     * <p>�������̃`�����N���L���[�C���O���A<code>WorkQueue</code> �C���X�^���X
     * �̃N���[�Y�������s���B</p>
     */
    public void close() {
        ControlBreakRowObject row = null;
        if (newRow != null) {
            row = currentChunk.setLastData(newRow.getRowObject());
        } else {
            row = new ControlBreakRowObject(ControlBreakChunk.END_MARK, null,
                    null);
        }
        currentChunk.add(row);
        currentChunk.setEndChunk(true);
        queue.put(currentChunk);
        currentChunk = null;
        queue.close();
    }
}
