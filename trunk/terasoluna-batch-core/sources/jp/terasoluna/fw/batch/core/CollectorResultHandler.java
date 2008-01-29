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


/**
 * �Ώۃf�[�^�擾���ʂ̃n���h���C���^�t�F�[�X�B
 * 
 * <p>�Ώۃf�[�^�擾�����̏I����ɁA�Ώۃf�[�^�擾�������ԋp�����Ώۃf�[�^�擾���ʂ���������
 * ���߂ɋN�������B�Ώۃf�[�^�擾���ʂ̃n���h���ł́A�Ώۃf�[�^�擾���ʁA
 * ����уW���u�X�e�[�^�X���n�����B
 * </p>
 * 
 * <p>�Ώۃf�[�^�擾���ʂ��W���u�X�e�[�^�X�֔��f���邱�Ƃ��ł���B</p>
 *
 */
public interface CollectorResultHandler {

    /**
     * �Ώۃf�[�^�擾���ʂ���������B
     * 
     * @param result �Ώۃf�[�^�擾����
     * @param jobStatus �W���u�X�e�[�^�X
     */
    void handle(CollectorResult result, JobStatus jobStatus);

}