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

package jp.terasoluna.fw.batch.commonj.listener;

import commonj.work.Work;
import commonj.work.WorkItem;
import commonj.work.WorkListener;

/**
 * �X�P�W���[�����ꂽ���[�N���Ď����邽�߂̃C���^�t�F�[�X�B<br>
 * commonj.work.WorkListener���g�����āAWorkItem��Work��R�t�����邽�߂� 
 * ���\�b�h��p�ӂ��Ă���B
 * 
 * 
 */
public interface WorkMapListener extends WorkListener {

    /**
     * �X�P�W���[�����ꂽ���[�N�̓o�^�B
     * 
     * @param workItem ���[�N�����[�N�}�l�W���[�ŃX�P�W���[�������Ƃ��̕ԋp�l
     * @param work
     *            �X�P�W���[���������[�N
     */
    void addWork(WorkItem workItem, Work work);

    /**
     * �X�P�W���[������Ă��郏�[�N�̎擾�B
     * 
     * @param workItem ���[�N�����[�N�}�l�W���[�ŃX�P�W���[�������Ƃ��̕ԋp�l
     * @return �X�P�W���[������Ă��郏�[�N
     */
    Object getWork(WorkItem workItem);

    /**
     * ��Ƃ������������[�N���폜����B
     * 
     * @param workItem ���[�N�����[�N�}�l�W���[�ŃX�P�W���[�������Ƃ��̕ԋp�l
     * @return �X�P�W���[������Ă��郏�[�N
     */
    Object removeWork(WorkItem workItem);

}
