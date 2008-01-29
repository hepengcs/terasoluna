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

import jp.terasoluna.fw.batch.openapi.BLogicResult;

/**
 * �T�|�[�g�������s���ʂ̃n���h���C���^�t�F�[�X�B
 * 
 * <p>���̃C���^�t�F�[�X�́A�T�|�[�g�����i<code>SupportLogic</code> �C���^�t�F�[
 * �X�̎����N���X�j���N������閈�ɁA���̃T�|�[�g�������ԋp�����T�|�[�g�����̎�
 * �s���ʂ��������邽�߂ɋN�������B</p>
 * 
 * <p>���̃C���^�t�F�[�X�̎����ł́A�p�����[�^�Ƃ��ēn���ꂽ�T�|�[�g�����̎��s��
 * �ʂɊ�Â��ăW���u�X�e�[�^�X�ւ̔��f��A���O�o�͂Ȃǂ��s�����Ƃ��ł���B
 * �܂��A�W���u�X�e�[�^�X��ύX���邱�ƂŃW���u�̌p���A�I�����w�肷�邱�Ƃ��ł�
 * ��B</p>
 * 
 */
public interface SupportLogicResultHandler {

    /**
     * �T�|�[�g�����̎��s���ʂ���������B
     * 
     * @param result �T�|�[�g�����̎��s����
     * @param jobStatus �W���u�X�e�[�^�X
     * @param name �T�|�[�g�����N���X�ɐݒ肳�ꂽ���O
     */
    void handle(BLogicResult result, JobStatus jobStatus, String name);
}
