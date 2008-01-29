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
 * �T�|�[�g�����v���Z�b�T�̃C���^�t�F�[�X�B
 * 
 * <p>�r�W�l�X���W�b�N�̃T�|�[�g�̂��߁A�W���u�ɂ͑O�����E�㏈�����`���邱�Ƃ�
 * �ł���B�O�����E�㏈���́A<code>SupportLogic</code> �C���^�t�F�[�X����������
 * �W���u�̊J���҂��쐬���邱�Ƃ��ł���B</p>
 * 
 * <p>�O�����E�㏈���́A�r�W�l�X���W�b�N�̃g�����U�N�V�����Ƃ̊֌W��N���̃^�C�~
 * ���O�ɂ���āA�t���[�����[�N�̂������̃N���X����N�������B�����̃N���X
 * �́A<code>SupportProcessor</code> �C���^�t�F�[�X��ʂ��āA���̎����N���X�ɑO
 * �����E�㏈�����s���˗�����B</p>
 * 
 * <p><code>SupportProcessor</code> �C���^�t�F�[�X�̎����N���X�ł́A�O�����E�㏈
 * ���̃g�����U�N�V������A���s���ʂ̃W���u�X�e�[�^�X�ւ̔��f�A��O�������̏���
 * �A����у��O�o�͂Ȃǂ��s���B</p>
 *
 */
public interface SupportProcessor {

    /**
     * �T�|�[�g�������s���B
     *
     * @param jobContext �W���u�R���e�L�X�g
     * @param jobStatus �W���u������
     */
    void process(JobContext jobContext, JobStatus jobStatus);
    
    /**
     * �T�|�[�g�������X�L�b�v�ł���ꍇ�ɂ́A<code>true</code> ��Ԃ��B 
     * 
     * @return �T�|�[�g�������X�L�b�v�ł���ꍇ�ɂ́A<code>true</code>
     */
    boolean canSkip();
}
