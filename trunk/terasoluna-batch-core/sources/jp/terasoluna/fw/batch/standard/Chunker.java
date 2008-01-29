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

import jp.terasoluna.fw.batch.core.Chunk;
import jp.terasoluna.fw.batch.core.CollectedDataHandler;
import jp.terasoluna.fw.batch.core.WorkQueue;
import jp.terasoluna.fw.batch.openapi.JobContext;

/**
 * �����Ώۃf�[�^���w�肳�ꂽ�T�C�Y���܂Ƃ߂āA�`�����N���쐬����N���X�B
 *
 */
public class Chunker implements CollectedDataHandler {

    /**
     * �L���[�B
     */
    private WorkQueue queue;

    /**
     * �`�����N�T�C�Y�B
     */
    private int chunkSize;

    /**
     * �쐬���̃`�����N�B
     */
    private Chunk currentChunk;
    
    /**
     * �W���u�R���e�N�X�g�B
     */
    private JobContext jobContext;

    /**
     * �R���X�g���N�^�B
     *
     * @param queue ���[�N�L���[
     * @param chunkSize �`�����N�T�C�Y
     * @param jobContext �W���u�R���e�N�X�g
     */
    public Chunker(WorkQueue queue, int chunkSize, JobContext jobContext) {
        this.queue = queue;
        this.chunkSize = chunkSize;
        this.jobContext = jobContext;
        currentChunk = new Chunk(chunkSize, jobContext);
    }

    /**
     * �����Ώۃf�[�^���L���[�ɒǉ�����B
     *
     * @param row �����Ώۃf�[�^
     * @param index �����Ώۃf�[�^�̃C���f�b�N�X
     */
    public void handle(Object row, int index) {
        currentChunk.add(row);
        if (currentChunk.size() >= chunkSize) {
            queue.put(currentChunk);
            currentChunk = new Chunk(chunkSize, jobContext);
        }
    }

    /**
     * �N���[�Y�������s���B
     *
     */
    public void close() {
        if (currentChunk.size() > 0) {
            queue.put(currentChunk);
        }
        queue.close();
    }
}
