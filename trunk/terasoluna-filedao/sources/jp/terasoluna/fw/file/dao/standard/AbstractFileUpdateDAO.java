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

package jp.terasoluna.fw.file.dao.standard;

import java.util.Map;

import jp.terasoluna.fw.file.dao.FileLineWriter;
import jp.terasoluna.fw.file.dao.FileUpdateDAO;

/**
 * �t�@�C���������ݗp��FileLineWriter�����N���X�B
 * 
 * <p>
 * �t�@�C���A�N�Z�X(�f�[�^����)���s��3�̃N���X(CSV�A�Œ蒷�A�ϒ�)
 * �ɋ��ʂ��鏈�����܂Ƃ߂����ۃN���X�B
 * �t�@�C���̎�ނɑΉ�����T�u�N���X���������s���B<br>
 * �ݒ���{@link jp.terasoluna.fw.file.dao.FileUpdateDAO}���Q�Ƃ̂��ƁB
 * </p>
 * 
 * @see jp.terasoluna.fw.file.dao.FileUpdateDAO
 * @see jp.terasoluna.fw.file.dao.standard.CSVFileUpdateDAO
 * @see jp.terasoluna.fw.file.dao.standard.FixedFileUpdateDAO
 * @see jp.terasoluna.fw.file.dao.standard.VariableFileUpdateDAO
 * @see jp.terasoluna.fw.file.dao.standard.PlainFileUpdateDAO
 */
public abstract class AbstractFileUpdateDAO implements FileUpdateDAO {

    /**
     * �����t�H�[�}�b�g�����}�b�v�B
     */
    private Map<String, ColumnFormatter> columnFormatterMap = null;
    
    /**
     * �t�@�C�������w�肵�āA<code>FileLineWriter</code> ���擾����B
     * 
     * @param <T> 1�s���̕�������i�[����t�@�C���s�I�u�W�F�N�g�N���X
     * @param fileName �t�@�C�����i��΃p�X�܂��͑��΃p�X�̂ǂ��炩�j
     * @param clazz 1�s���̕�������i�[����t�@�C���s�I�u�W�F�N�g�N���X
     * @return �t�@�C���o�͗pWriter
     */
    public abstract <T> FileLineWriter<T> execute(String fileName,
            Class<T> clazz);

    /**
     * �����t�H�[�}�b�g�����}�b�v���擾����B
     * 
     * @return  �����t�H�[�}�b�g�����}�b�v
     */
    public Map<String, ColumnFormatter> getColumnFormatterMap() {
        return columnFormatterMap;
    }

    /**
     * �����t�H�[�}�b�g�����}�b�v��ݒ肷��B
     * 
     * @param columnFormatterMap �����t�H�[�}�b�g�����}�b�v
     */
    public void setColumnFormatterMap(
            Map<String, ColumnFormatter> columnFormatterMap) {
        this.columnFormatterMap = columnFormatterMap;
    }
}
