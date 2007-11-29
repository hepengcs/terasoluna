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

package jp.terasoluna.fw.dao;

/**
 * StoredProcedureDAO�C���^�t�F�[�X�B
 * 
 * StoredProcedure�����s���邽�߂�DAO�C���^�t�F�[�X�ł���B
 *
 */
public interface StoredProcedureDAO {

    /**
     * �w�肳�ꂽSQLID�̃X�g�A�h�v���V�[�W���[�����s����B
     * �X�g�A�h�v���V�[�W���[�̌��ʂł���A�E�g�p�����[�^�́A
     * ������bindParams�ɔ��f�����B
     * 
     * @param sqlID ���s����SQL��ID
     * @param bindParams SQL�Ƀo�C���h����l���i�[�����I�u�W�F�N�g
     */
    void executeForObject(String sqlID, Object bindParams);
}
