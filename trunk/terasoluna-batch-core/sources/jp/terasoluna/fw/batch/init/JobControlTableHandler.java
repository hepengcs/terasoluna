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

/**
 * �W���u�Ǘ��e�[�u���Ǘ��p�C���^�t�F�[�X�B<BR>
 *  ���̃C���^�t�F�[�X���������邱�ƂŃW���u�Ǘ��e�[�u����
 * �W���u�ȗ����擾�A�W���u�J�n�����A�W���u�I���������s�����Ƃ��o����B
 *
 *
 * @see JobControlTableHandlerImpl
 */
public interface JobControlTableHandler {

    /**
     * �W���u�˗����̎擾�B
     *
     * @return �W���u�˗������i�[����<code>JobInfo</code>�̃C���X�^��
     * �X�B
     */
    JobInfo getJobRequestData();

    /**
     * ����(�W���u�˗�)�̎��̃W���u�˗����̎擾�B
     * 
     * @param jobInfo �O��擾����<code>JobInfo</code>�̃C���X�^���X�B
     * @return ����(�W���u�˗�)�̎��̃W���u�˗������i�[����
     * <code>JobInfo</code>�̃C���X�^���X�B
     */
    JobInfo getJobRequestData(JobInfo jobInfo);

    /**
     * �Ώۂ̃W���u�́u�N���󋵁v���N�����ɍX�V�B
     *
     * @param jobInfo �X�V�Ώۂ̃W���u�˗����
     * @return �X�V���ꂽ���R�[�h���B
     */
    int updateJobStart(JobInfo jobInfo);

    /**
     * �Ώۂ̃W���u�́u�N���󋵁v���I����ԂɍX�V�B
     *
     * @param jobInfo �X�V�Ώۂ̃W���u�˗����
     * @return �X�V���ꂽ���R�[�h���B
     */
    int updateJobEnd(JobInfo jobInfo);

}
