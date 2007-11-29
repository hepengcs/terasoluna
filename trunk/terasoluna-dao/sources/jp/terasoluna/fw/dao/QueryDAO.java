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
import java.util.Map;

/**
 * QueryDAO�C���^�t�F�[�X�B
 * 
 * �Q�ƌnSQL�����s���邽�߂�DAO�C���^�t�F�[�X�ł���B
 *
 */
public interface QueryDAO {

    /**
     * SQL�̎��s���ʂ��w�肳�ꂽ�^�ɂ��ĕԋp����B
     * 
     * @param <E> �ԋp�l�̌^
     * @param sqlID ���s����SQL��ID
     * @param bindParams SQL�Ƀo�C���h����l���i�[�����I�u�W�F�N�g
     * @param clazz �ԋp�l�̃N���X
     * @return SQL�̎��s����
     */
    <E> E executeForObject(String sqlID, Object bindParams, Class clazz);

    /**
     * SQL�̎��s���ʂ�Map�Ɋi�[���ĕԋp����B
     * 
     * @param sqlID ���s����SQL��ID
     * @param bindParams SQL�Ƀo�C���h����l���i�[�����I�u�W�F�N�g
     * @return SQL�̎��s����
     */
    Map<String, Object> executeForMap(String sqlID, Object bindParams);

    /**
     * SQL�̎��s���ʂ��w�肳�ꂽ�^�̔z��ɂ��ĕԋp����B
     * 
     * @param <E> �ԋp�l�̌^
     * @param sqlID ���s����SQL��ID
     * @param bindParams SQL�Ƀo�C���h����l���i�[�����I�u�W�F�N�g
     * @param clazz �ԋp�l�̃N���X
     * @return SQL�̎��s����
     */
    <E> E[] executeForObjectArray(String sqlID,
            Object bindParams, Class clazz);

    /**
     * SQL�̎��s���ʂ�Map�̔z��Ɋi�[���ĕԋp����B
     * 
     * @param sqlID ���s����SQL��ID
     * @param bindParams SQL�Ƀo�C���h����l���i�[�����I�u�W�F�N�g
     * @return SQL�̎��s����
     */
    Map<String, Object>[] executeForMapArray(String sqlID,
            Object bindParams);

    /**
     * SQL�̎��s���ʂ��w�肳�ꂽ�C���f�b�N�X����w�肳�ꂽ�s�����A
     * �w�肳�ꂽ�^�̔z��ɂ��ĕԋp����B
     * 
     * @param <E> �ԋp�l�̌^
     * @param sqlID ���s����SQL��ID
     * @param bindParams SQL�Ƀo�C���h����l���i�[�����I�u�W�F�N�g
     * @param clazz �ԋp�l�̃N���X
     * @param beginIndex �擾����J�n�C���f�b�N�X
     * @param maxCount �擾���錏��
     * @return SQL�̎��s����
     */
    <E> E[] executeForObjectArray(String sqlID,
            Object bindParams, Class clazz, int beginIndex, int maxCount);

    /**
     * SQL�̎��s���ʂ��w�肳�ꂽ�C���f�b�N�X����w�肳�ꂽ�s�����A
     * Map�̔z��ɂ��ĕԋp����B
     * 
     * @param sqlID ���s����SQL��ID
     * @param bindParams SQL�Ƀo�C���h����l���i�[�����I�u�W�F�N�g
     * @param beginIndex �擾����J�n�C���f�b�N�X
     * @param maxCount �擾���錏��
     * @return SQL�̎��s����
     */
    Map<String, Object>[] executeForMapArray(String sqlID,
            Object bindParams, int beginIndex, int maxCount);

    /**
     * SQL�̎��s���ʂ��w�肳�ꂽ�^��List�ŕԋp����B
     * 
     * @param <E> �ԋp�l�̌^
     * @param sqlID ���s����SQL��ID
     * @param bindParams SQL�Ƀo�C���h����l���i�[�����I�u�W�F�N�g
     * @return SQL�̎��s����
     */
    <E> List<E> executeForObjectList(String sqlID,
            Object bindParams);

    /**
     * SQL�̎��s���ʂ�Map��List�Ɋi�[���ĕԋp����B
     * 
     * @param sqlID ���s����SQL��ID
     * @param bindParams SQL�Ƀo�C���h����l���i�[�����I�u�W�F�N�g
     * @return SQL�̎��s����
     */
    List<Map<String, Object>> executeForMapList(String sqlID,
            Object bindParams);

    /**
     * SQL�̎��s���ʂ��w�肳�ꂽ�C���f�b�N�X����w�肳�ꂽ�s�����A
     * �w�肳�ꂽ�^��List�ŕԋp����B
     * 
     * @param <E> �ԋp�l�̌^
     * @param sqlID ���s����SQL��ID
     * @param bindParams SQL�Ƀo�C���h����l���i�[�����I�u�W�F�N�g
     * @param beginIndex �擾����J�n�C���f�b�N�X
     * @param maxCount �擾���錏��
     * @return SQL�̎��s����
     */
    <E> List<E> executeForObjectList(String sqlID,
            Object bindParams, int beginIndex, int maxCount);

    /**
     * SQL�̎��s���ʂ��w�肳�ꂽ�C���f�b�N�X����w�肳�ꂽ�s�����A
     * Map��List�ɂ��ĕԋp����B
     * 
     * @param sqlID ���s����SQL��ID
     * @param bindParams SQL�Ƀo�C���h����l���i�[�����I�u�W�F�N�g
     * @param beginIndex �擾����J�n�C���f�b�N�X
     * @param maxCount �擾���錏��
     * @return SQL�̎��s����
     */
    List<Map<String, Object>> executeForMapList(String sqlID,
            Object bindParams, int beginIndex, int maxCount);


}