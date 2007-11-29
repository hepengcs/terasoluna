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

import java.util.List;

/**
 * UpdateDAO�C���^�t�F�[�X�B
 * 
 * �X�V�nSQL�����s���邽�߂�DAO�C���^�t�F�[�X�ł���B
 * 
 */
public interface UpdateDAO {

    /**
     * ����sqlID�Ŏw�肳�ꂽSQL�����s���āA���ʌ�����ԋp����B
     * ���s����SQL�́uinsert, update delete�v��3��ނƂ���B
     * 
     * @param sqlID ���s����SQL��ID
     * @param bindParams SQL�Ƀo�C���h����l���i�[�����I�u�W�F�N�g
     * @return SQL�̎��s���ʌ�����ԋp
     */
    int execute(String sqlID, Object bindParams);

    /**
     * �o�b�`�ǉ����\�b�h�B
     * �o�b�`�����Ƃ��Ēǉ�������SQL��SQLID�ƃo�C���h�p�����[�^��
     * �����ɓn���B
     * 
     * @param sqlID ���s����SQL��ID
     * @param bindParams SQL�Ƀo�C���h����l���i�[�����I�u�W�F�N�g
     * @deprecated addBatch�̑����{@link #executeBatch(List)}
     * ���g�p���邱��
     */
    @Deprecated
    void addBatch(String sqlID, Object bindParams);

    /**
     * �o�b�`�����̎��s���\�b�h�B
     * 
     * @return SQL�̎��s����
     * @deprecated executeBatch�̑����{@link #executeBatch(List)}
     * ���g�p���邱��
     */
    @Deprecated
    int executeBatch();
    
    /**
     * �o�b�`�X�V�������s�����\�b�h�B<br/>
     * ������{@link SqlHolder}�̃��X�g�Ŏw�肳�ꂽ���ׂĂ�SQL�����s����B
     * DAO�C���X�^���X�ɏ�Ԃ��������Ȃ��ׁA�o�b�`�X�V�Ώۂ�SQL�͂��ׂ�
     * ���̃��\�b�h���Ŏ��s�܂Ŋ�������K�v������B
     * 
     * @param sqlHolders �o�b�`�X�V�Ώۂ�sqlId�A�p�����[�^���i�[����
     * SqlHolder�C���X�^���X�̃��X�g
     * @return SQL�̎��s���ʌ���
     */
    int executeBatch(List<SqlHolder> sqlHolders);

}
