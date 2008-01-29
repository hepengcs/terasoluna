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

import jp.terasoluna.fw.batch.core.CollectedDataHandlerFactory;
import jp.terasoluna.fw.batch.core.WorkQueue;
import jp.terasoluna.fw.batch.openapi.JobContext;

/**
 * �����L�[�����p�n���h���̃t�@�N�g���N���X�B
 *
 */
public class PartitionKeyHandlerFactory 
    implements CollectedDataHandlerFactory {

    /**
     * �����L�[�����p�n���h���𐶐�����B
     * 
     * @param workQueue ���[�N�L���[
     * @param jobContext �W���u�R���e�N�X�g
     * @return �����L�[�����p�n���h��
     */
    public PartitionKeyHandler getHandler(WorkQueue workQueue, 
            JobContext jobContext) {
        return new PartitionKeyHandler(workQueue, jobContext);
    }
}
