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

import jp.terasoluna.fw.batch.core.CollectedDataHandler;
import jp.terasoluna.fw.batch.core.WorkQueue;
import jp.terasoluna.fw.batch.core.WorkUnit;

/**
 * �񓯊��W���u�N�����̃W���u�˗����i�[�p�L���[�N���X�B
 *
 */
public class JobRequestInfoHandler implements CollectedDataHandler {

    /**
     * �L���[�B
     */
    private WorkQueue queue;

    /**
     * �R���X�g���N�^�B
     *
     * @param queue ���[�N�L���[
     */
    public JobRequestInfoHandler(WorkQueue queue) {
        this.queue = queue;
    }

    /**
     * �L���[���I������B
     * �L���[�̏I�[�ł���C���X�^���X���L���[�ɒǉ�����B
     */
    public void close() {
        queue.close();
    }

    /**
     * �W���u�˗������L���[�ɒǉ�����B
     *
     * @param obj �W���u�˗����
     * @param collected �擾�ԍ�
     */
    public void handle(Object obj, int collected) {
        queue.put((WorkUnit) obj);
    }
}
