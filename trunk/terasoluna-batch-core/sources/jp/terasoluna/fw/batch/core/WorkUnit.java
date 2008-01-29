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
 * <code>Workable<code> �C���^�t�F�[�X�̍�Ƃ̍�ƒP�ʁB
 * 
 * <p>���̃C���^�t�F�[�X�́A<code>JobManager</code>�A<code>JobWorker</code> �Ȃ�
 * ��<code>Workable</code> �C���^�t�F�[�X����������N���X����Ƃ��s���ۂ̍�ƒP
 * �ʂ�\���B��̓I�ɂ́A�r�W�l�X���W�b�N�̓��̓f�[�^��A���邢�́A�W���u�𕪊�
 * ���邽�߂̕����L�[��\���B�܂��A��ƒP�ʂ́A���̍�ƒP�ʂɊ֘A����W���u�R��
 * �e�N�X�g��ݒ�A�擾���邱�Ƃ��ł���B</p>
 * 
 * <p>���̃C���^�t�F�[�X�̃C���X�^���X�́A<code>CollectedDataHandler</code> �ō�
 * ������A�L���[�C���O����� <code>Workable</code> �C���^�t�F�[�X�̎����N���X��
 * �n�����B���̃C���^�t�F�[�X�ł́A�L���[�̍Ō�̃f�[�^�ł��邩�ǂ��𔻒肷��
 * ���߂�<code>isEndMark()</code> ���\�b�h���K�肷��B</p>
 * 
 */
public interface WorkUnit {
    
    /**
     * ���̃C���^�t�F�[�X����������C���X�^���X���A�L���[�̍Ō�̃f�[�^�ł��邩
     * �ǂ������擾����B
     * 
     * @return �L���[�̍Ō�̃f�[�^�ł���ꍇ�� <code>true</code>
     */
    boolean isEndMark();
    
    /**
     * �W���u�R���e�N�X�g���擾����B
     * 
     * @return �W���u�R���e�N�X�g
     */
    JobContext getJobContext();
    
    /**
     * �W���u�R���e�N�X�g��ݒ肷��B
     * 
     * @param jobContext �W���u�R���e�N�X�g
     */
    void setJobContext(JobContext jobContext);
}
