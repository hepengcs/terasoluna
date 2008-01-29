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

package jp.terasoluna.fw.batch.core;

import jp.terasoluna.fw.batch.openapi.JobContext;

/**
 * �Ώۃf�[�^�n���h�� <code>CollectedDataHandler</code> ��
 * �t�@�N�g���C���^�t�F�[�X�B
 * 
 * <p>�Ώۃf�[�^�n���h���𐶐�����B</p>
 * 
 */
public interface CollectedDataHandlerFactory {

    /**
     * �Ώۃf�[�^�n���h���𐶐�����B
     * 
     * @param workQueue ���[�N�L���[
     * @param jobContext �W���u�R���e�N�X�g
     * @return �R���N�^�f�[�^�n���h��
     */
    CollectedDataHandler getHandler(WorkQueue workQueue, JobContext jobContext);
}
