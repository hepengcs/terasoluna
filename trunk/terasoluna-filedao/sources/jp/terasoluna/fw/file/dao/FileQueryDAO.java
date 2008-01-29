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

package jp.terasoluna.fw.file.dao;

/**
 * �t�@�C���s�I�u�W�F�N�g�����p�̃C�e���[�^�𐶐����邽�߂̃C���^�t�F�[�X�B
 * <p>
 * �����X�g���[������t�@�C���s�I�u�W�F�N�g�𐶐�����<code>FileLineIterator</code>
 * �𐶐����邽�߂̃C���^�t�F�[�X�ł���B
 * �T�u�N���X�Ŏ������郁�\�b�h��<code>execute</code>�̂݁B
 * �����ɂ̓f�[�^��ǂݎ��t�@�C���̃p�X�i���΃p�X/��΃p�X�j�ƁA
 * �t�@�C���s�I�u�W�F�N�g�̃N���X��ݒ肷��B<br> 
 * FileLineIterator�𐶐�������@���ȉ��ɂ�����B
 * </p>
 * <p>
 * <strong>�ݒ��</strong><br>
 * �r�W�l�X���W�b�N(SampleLogic)�̒���FileLineIterator�𐶐������
 * <pre>
 * <li>1.FileQueryDAO�̃C���X�^���X�̏����W���uBean��`�t�@�C���ɐݒ肷��B</li>
 * <code>
 * &lt;bean id=&quot;blogic&quot; 
 *�@�@class=&quot;jp.terasoluna.batch.sample.SampleLogic&quot;&gt;
 *  &lt;property name=&quot;fileQueryDAO&quot; ref=&quot;csvFileQueryDao&quot; /&gt;
 * &lt;/bean&gt;
 * </code>
 * �Q�Ƃ���FileQueryDAO�̃T�u�N���X�́uFileAccessBean.xml�v���Q�Ƃ̂��ƁB
 * 
 * <li>2.FileLineIterator���r�W�l�X���W�b�N�̒��Ő�������B</li>
 * <code>
 * FileQueryDAO fileDao = null;�@�@�@//FileQueryDAO�̃C���X�^���X�̏��̓W���uBean��`�t�@�C���ɐݒ肷��Bsetter�͏ȗ��B
 * �c�c
 * // FileLineIterator�𐶐��B
 * FileLineIterator fileLineIterator 
 *     = fileDao.execute("�y�A�N�Z�X����t�@�C�����z", �y�t�@�C���s�I�u�W�F�N�g�̃N���X�z);
 * �c�c
 * </code>
 * </pre>
 * 
 * FileLineIterator�ɂ��Ắo@link jp.terasoluna.fw.file.dao.FileLineIterator}���Q�Ƃ̂��ƁB
 * 
 *
 */
public interface FileQueryDAO {
    
    /**
     * �t�@�C�������w�肵�āA<code>FileLineIterator</code>���擾����B
     * 
     * @param fileName �t�@�C�����i��΃p�X�܂��͑��΃p�X�̂ǂ��炩�j
     * @param clazz 1�s���̕�������i�[����t�@�C���s�I�u�W�F�N�g�N���X
     * @param <T> �t�@�C���s�I�u�W�F�N�g
     * @return �t�@�C���s�I�u�W�F�N�g�����p�̃C�e���[�^
     */
    <T> FileLineIterator<T> execute(String fileName, Class<T> clazz);
}