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

import java.io.Serializable;

/**
 * �W���u�˗����擾�p�C���^�t�F�[�X�B
 *
 */
public interface JobInfo extends Serializable {

    /**
     * �W���u���N�G�X�g�ԍ����擾����B
     *
     * @return �W���u���N�G�X�g�ԍ�
     */
    String getJobRequestNo();

    /**
     * �W���uID���擾����B
     *
     * @return �W���uID
     */
    String getJobId();

    /**
     * �W���uBean��`�t�@�C����PATH���擾����B
     *
     * @return �W���uBean��`�t�@�C����PATH
     */
    String getJobDiscriptorPath();

    /**
     * �W���u�N���p�����[�^���擾����B
     *
     * @return �W���u�N���p�����[�^
     */
    String[] getJobParameters();
}
