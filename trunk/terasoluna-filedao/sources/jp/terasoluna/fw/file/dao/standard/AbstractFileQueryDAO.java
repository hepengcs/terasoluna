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

import jp.terasoluna.fw.file.dao.FileLineIterator;
import jp.terasoluna.fw.file.dao.FileQueryDAO;

/**
 * �t�@�C���ǎ�p��FileLineIterator�����p�̃N���X�B
 * 
 * <p>
 * �t�@�C���A�N�Z�X(�f�[�^�擾)���s��3�̃N���X(CSV�A�Œ蒷�A�ϒ�)
 * �ɋ��ʂ��鏈�����܂Ƃ߂����ۃN���X�B
 * �t�@�C���̎�ނɑΉ�����T�u�N���X���������s���B<br>
 * �ݒ���{@link jp.terasoluna.fw.file.dao.FileQueryDAO}���Q�Ƃ̂��ƁB
 * </p>
 * 
 * @see jp.terasoluna.fw.file.dao.FileQueryDAO
 * @see jp.terasoluna.fw.file.dao.standard.CSVFileQueryDAO
 * @see jp.terasoluna.fw.file.dao.standard.FixedFileQueryDAO
 * @see jp.terasoluna.fw.file.dao.standard.VariableFileQueryDAO
 * @see jp.terasoluna.fw.file.dao.standard.PlainFileQueryDAO
 */
public abstract class AbstractFileQueryDAO implements FileQueryDAO {

    /**
     * �J�����p�[�U�[���i�[����}�b�v�B
     */
    private Map<String, ColumnParser> columnParserMap = null;

    /**
     * �t�@�C�������w�肵�āA<code>FileLineIterator</code>���擾����B
     * 
     * @param <T> 1�s���̕�������i�[����t�@�C���s�I�u�W�F�N�g�N���X
     * @param fileName �t�@�C�����i��΃p�X�܂��͑��΃p�X�̂ǂ��炩�j
     * @param clazz 1�s���̕�������i�[����t�@�C���s�I�u�W�F�N�g�N���X
     * @return �t�@�C���s�I�u�W�F�N�g�����p�̃C�e���[�^
     */
    public abstract <T> FileLineIterator<T> execute(String fileName,
            Class<T> clazz);

    /**
     * �J�����p�[�U�[���i�[����}�b�v���擾����B
     * 
     * @return �J�����p�[�U�[���i�[����}�b�v
     */
    protected Map<String, ColumnParser> getColumnParserMap() {
        return columnParserMap;
    }

    /**
     * �J�����p�[�U�[���i�[����}�b�v��ݒ肷��B
     * 
     * @param columnParserMap �J�����p�[�U�[���i�[����}�b�v
     */
    public void setColumnParserMap(Map<String, ColumnParser> columnParserMap) {
        this.columnParserMap = columnParserMap;
    }

}
