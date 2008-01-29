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
 * �t�@�C���s�I�u�W�F�N�g�̒l���o�͂���FileLineWriter�𐶐����邽�߂̃C���^�t�F�[�X�B
 * <p>
 * �t�@�C���s�I�u�W�F�N�g����l�����o���ăe�L�X�g�t�@�C���ɏo�͂���<code>FileLineWriter</code>
 * �𐶐����邽�߂̃C���^�t�F�[�X�ł���B
 * �T�u�N���X�Ŏ������郁�\�b�h��<code>execute</code>�̂݁B
 * �����ɂ̓f�[�^���������ރt�@�C���̃p�X�i���΃p�X/��΃p�X�j�ƁA
 * �t�@�C���s�I�u�W�F�N�g�̃N���X��ݒ肷��B<br>
 * FileLineWriter�𐶐�������@���ȉ��ɂ�����B
 * </p>
 * <p>
 * <strong>�ݒ��</strong><br>
 * �r�W�l�X���W�b�N(SampleLogic)�̒���FileLineWriter�𐶐������B
 * <pre>
 * <li>1.FileUpdateDAO�̃C���X�^���X�̏����W���uBean��`�t�@�C���ɐݒ肷��B</li>
 * <code>
 * &lt;bean id=&quot;blogic&quot; 
 *�@�@class=&quot;jp.terasoluna.batch.sample.SampleLogic&quot;&gt;
 *  &lt;property name=&quot;fileUpdateDAO&quot; ref=&quot;csvFileUpdateDao&quot; /&gt;
 * &lt;/bean&gt;
 * </code>
 * �Q�Ƃ���FileQueryDAO�̃T�u�N���X�́uFileAccessBean.xml�v���Q�Ƃ̂��ƁB
 * 
 * <li>2.FileLineWriter���r�W�l�X���W�b�N�̒��Ő�������B
 * <code>
 * FileUpdateDAO fileDao = null;   //FileUpdateDAO�̃C���X�^���X�����̓W���uBean��`�t�@�C���ɋL�q����Bsetter�͏ȗ��B
 * �c�c
 * // FileLineWriter�𐶐��B
 * FileLineWriter fileLineWriter = fileDao.execute("�y�A�N�Z�X����t�@�C�����z", �y�t�@�C���s�I�u�W�F�N�g�̃N���X�z);
 * �c�c
 * </code>
 * </pre>
 * </p>
 * FileLineWriter�̏ڍׂ́A{@link jp.terasoluna.fw.file.dao.FileLineWriter}���Q�Ƃ̂��ƁB
 * 
 *
 */
public interface FileUpdateDAO {
    
    /**
     * �t�@�C�������w�肵�āA<code>FileLineWriter</code>���擾����B
     * 
     * @param fileName �t�@�C�����i��΃p�X�܂��͑��΃p�X�̂ǂ��炩�j
     * @param clazz 1�s���̕�������i�[����t�@�C���s�I�u�W�F�N�g�N���X
     * @param <T> �t�@�C���s�I�u�W�F�N�g
     * @return �t�@�C���o�͗pWriter
     */
    <T> FileLineWriter<T> execute(String fileName, Class<T> clazz);

}
