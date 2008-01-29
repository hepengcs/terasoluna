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
 * �W���u��O�n���h���C���^�t�F�[�X�B
 *
 * <p>�W���u��O�n���h���́A���̃C���^�t�F�[�X���������č쐬����B</p>
 *
 * <p>�W���u��O�n���h���́A�W���u��O�N���X���L�[�Ƃ����O�n���h���}�b�v��
 * �o�^�����B�W���u���s���ɗ�O�����������ꍇ�A��O�����ӏ��ɑΉ����ăW���u
 * ��O�̃T�u�N���X�Ń��b�v����A��O�n���h���}�b�v�ɂ��������ė�O�ɑΉ�����
 * ��O�n���h�����N�������B</p>
 * 
 * <p>�W���u��O�n���h���ł́A�n���ꂽ��O��W���u�R���e�L�X�g���烍�O�̏o�͂Ȃ�
 * ���s�����ɁA�W���u�X�e�[�^�X���X�V���āA�W���u�̏I���A�p���Ȃǂ����肷�邱��
 * ���ł���B</p>
 *
 */
public interface JobExceptionHandler {

    /**
     * ��O�������s���B
     *
     * @param jobContext �W���u�R���e�L�X�g
     * @param jobException �W���u��O
     * @param jobStatus �W���u�X�e�[�^�X
     */
    void handlException(JobContext jobContext, JobException jobException,
            JobStatus jobStatus);
}
