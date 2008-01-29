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
 * ��Ƃ����s�ł���N���X����������C���^�t�F�[�X�B
 * 
 * <p>���̃C���^�t�F�[�X�́A�t���[�����[�N�̒��j�ƂȂ�C���^�t�F�[�X�ł���A
 * <code>JobManager</code>�A<code>JobWorker</code> �Ƃ������t���[�����[�N��
 * ��v�ȃN���X�Ŏ��������B</p>
 * 
 * <p><code>Workable</code> �C���^�t�F�[�X�̓���̎����N���X�ŏ��������"���"�́A
 * ���̎����N���X�ɂ����ĕK�v�ɉ����ĕ��������B�������ꂽ"���"�́A
 * ���̃C���^�t�F�[�X�����������ʂ̃N���X�̃C���X�^���X�Q�ɂ���ĕ��S����邱��
 * ������B���̂悤�� <code>Workable</code> �C���^�t�F�[�X�́A�W���u�̎��s�ɕK�v
 * ��"���"���K�w�I�ɕ�������ۂɁA�R���|�W�b�g�ȍ\������邽�߂Ɏg�p�����B</p>
 * 
 * <p><code>Workable</code> �C���^�t�F�[�X�̎����́A�W���u�ƂP�F�P�ɑΉ��������
 * �ł͂Ȃ��B<code>Workable</code> �C���^�t�F�[�X�̎������\�������Ƃ̊K�w�̂�
 * ���̂ЂƂ̑w���A�W���u�ƑΉ�����B���������� <code>Workable</code> �C���^�t
 * �F�[�X�̎����ł́A�W���u�̈ꕔ�݂̂����s���Ă��邱�Ƃ����邪�A������ <code>
 * Workable</code>�C���^�t�F�[�X�̎����ŃW���u�S�̂̏�ԁi�W���u�X�e�[�^�X�j����
 * �L���邽�߁A�p�����[�^�� <code>JobStatus</code> ���n�����B</p>
 *
 * @param <T> ��ƒP�ʃp�����[�^�̃N���X
 */
public interface Workable<T extends WorkUnit> {

    /**
     * ��Ƃ��s���B
     *
     * @param workUnit ��ƒP��
     * @param jobStatus �W���u�X�e�[�^�X
     */
    void work(T workUnit, JobStatus jobStatus);
}
