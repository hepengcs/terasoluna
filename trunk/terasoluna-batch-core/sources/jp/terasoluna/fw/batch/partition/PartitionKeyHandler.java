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

package jp.terasoluna.fw.batch.partition;

import jp.terasoluna.fw.batch.core.CollectedDataHandler;
import jp.terasoluna.fw.batch.core.WorkQueue;
import jp.terasoluna.fw.batch.openapi.JobContext;

/**
 * �����������Ƀp�[�e�B�V�����L�[����������Ώۃf�[�^�n���h���B
 *
 */
public class PartitionKeyHandler implements CollectedDataHandler {

    /**
     * �L���[�B
     */
    private WorkQueue queue;
    
    /**
     * �W���u�R���e�N�X�g�B
     */
    private JobContext jobContext;

    /**
     * �R���X�g���N�^�B
     *
     * @param queue ���[�N�L���[
     * @param jobContext �W���u�R���e�N�X�g
     */
    public PartitionKeyHandler(WorkQueue queue, JobContext jobContext) {
        this.queue = queue;
        this.jobContext = jobContext;
    }

    /**
     * �����L�[���L���[�ɒǉ�����B
     *
     * @param partitionKey �����L�[
     * @param partitonNo �����ԍ�
     */
    public void handle(Object partitionKey, int partitonNo) {
        JobContext partitionJobContext = 
            jobContext.getChildJobContext(partitionKey);
        partitionJobContext.setPartitionNo(partitonNo);
        PartitionRowObject partitionRowObject
            = new PartitionRowObject(partitonNo, partitionKey,
                    partitionJobContext);
        queue.put(partitionRowObject);
    }

    /**
     * �L���[���I������B
     * �L���[�̏I�[�ł���C���X�^���X���L���[�ɒǉ�����B
     */
    public void close() {
        queue.close();
    }
}
