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
 * ��ƃL���[�̃t�@�N�g���C���^�t�F�[�X�B
 * 
 * <p><code>JobManager</code> ���W���u�i���邢�́A�W���u�̈ꕔ�j�����s����ۂɁA
 * ���̍�ƑΏۂ̓��͏����Əo�͏��������т���L���[���쐬����B
 * <code>JobManager</code> �̂ЂƂ̃C���X�^���X���A�������ƑΏۂ̏������s��
 * ���߁A��ƑΏۂ̏������s�����ɂ��̃t�@�N�g���C���^�t�F�[�X�����ƃL���[��
 * �擾����B</p>
 *
 */
public interface WorkQueueFactory {

    /**
     * ��ƃL���[�𐶐�����B
     *
     * @param jobStatus �W���u�X�e�[�^�X
     * @return �t�@�N�g��������������ƃL���[
     */
    WorkQueue getWorkQueue(JobStatus jobStatus);
}
