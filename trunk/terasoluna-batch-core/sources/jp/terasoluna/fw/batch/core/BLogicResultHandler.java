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

import java.util.LinkedHashMap;
import java.util.List;

import jp.terasoluna.fw.batch.openapi.BLogicResult;

/**
 * �r�W�l�X���W�b�N���s���ʂ̃n���h���C���^�t�F�[�X�B
 * 
 * <p>���̃C���^�t�F�[�X�́A�r�W�l�X���W�b�N���N������閈�ɁA
 * ���̃r�W�l�X���W�b�N���ԋp�����r�W�l�X���W�b�N���s���ʂ��������邽�߂�
 * �N�������B
 * </p>
 * 
 * <p>���̃C���^�t�F�[�X�̎����ł́A�p�����[�^�Ƃ��ēn���ꂽ�r�W�l�X���W�b�N
 * ���s���ʂɊ�Â��ăW���u�X�e�[�^�X�ւ̔��f��A���O�o�͂Ȃǂ��s�����Ƃ��ł���B
 * �܂��A�W���u�X�e�[�^�X��ύX���邱�ƂŃW���u�̌p���A�I�����w�肷�邱�Ƃ��ł�
 * ��B</p>
 *
 */
public interface BLogicResultHandler {

    /**
     * �r�W�l�X���W�b�N���s���ʂ���������B
     *
     * @param result �r�W�l�X���W�b�N���s����
     * @param blogicInputData �r�W�l�X���W�b�N���̓f�[�^
     * @param jobStatus �W���u�X�e�[�^�X
     * @param batchUpdateMapList �o�b�`�X�V���X�g
     */
    void handle(BLogicResult result, Object blogicInputData,
            JobStatus jobStatus,
            List<LinkedHashMap<String, Object>> batchUpdateMapList);

}
