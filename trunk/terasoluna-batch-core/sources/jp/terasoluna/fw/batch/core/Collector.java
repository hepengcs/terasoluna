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
 * �Ώۃf�[�^�擾�N���X����������C���^�t�F�[�X�B
 * 
 * <p>���̃C���^�t�F�[�X�̎����ł́A�r�W�l�X���W�b�N�̓��̓f�[�^���f�[�^�x�[�X��
 * �t�@�C������擾����B</p>
 * 
 * <p>�Ώۃf�[�^�擾�N���X�ł́A�����œn���ꂽ <code>WorkQueue</code> �Ɏ擾����
 * �Ώۃf�[�^�������L���[�C���O����B</p>
 *
 * @param <T> �W���u�R���e�N�X�g�̃T�u�N���X
 */
public interface Collector<T extends JobContext> {

    /**
     * �����Ώۃf�[�^�̎擾�p���\�b�h�B
     * �����Ώۃf�[�^���擾���A�L���[�Ɋi�[����B
     *
     * @param jobContext �W���u�R���e�L�X�g
     * @param workQueue �����Ώۃf�[�^���i�[����L���[
     * @param jobStatus �W���u������
     * @return �R���N�^����
     */
    CollectorResult collect(T jobContext, WorkQueue workQueue,
            JobStatus jobStatus);
}
