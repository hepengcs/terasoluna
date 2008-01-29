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
 * �t�@�C���A�N�Z�X�@�\�Ŕ���������O�����b�v����N���X�B
 * <p>
 * �t�@�C������s�̃f�[�^��ǂݎ��ۂɔ���������O�����b�v����N���X�B
 * </p>
 */
public class FileLineException extends FileException {

    /**
     * �V���A���o�[�W����UID�B
     */
    private static final long serialVersionUID = 3669406429664627698L;

    /**
     * �G���[�����������J�������B
     */
    private final String columnName;

    /**
     * �G���[�����������J�����ԍ��B
     */
    private final int columnIndex;

    /**
     * �G���[�����������s�̍s�ԍ��B
     */
    private final int lineNo;
    
    /**
     * �R���X�g���N�^�B
     * @param e ������O
     */
    public FileLineException(Exception e) {
        super(e);
        columnName = null;
        columnIndex = -1;
        lineNo = -1;
    }
    
    /**
     * �R���X�g���N�^�B
     * @param message ���b�Z�[�W
     */
    public FileLineException(String message) {
        super(message);
        columnName = null;
        columnIndex = -1;
        lineNo = -1;
    }
    
    /**
     * �R���X�g���N�^�B
     * @param message ���b�Z�[�W
     * @param e ������O
     */
    public FileLineException(String message, Exception e) {
        super(message, e);
        columnName = null;
        columnIndex = -1;
        lineNo = -1;
    }
    
    /**
     * �R���X�g���N�^�B
     * @param e ������O
     * @param fileName �t�@�C����
     * @param lineNo �G���[�����������s�̍s�ԍ�
     */
    public FileLineException(Exception e, String fileName, int lineNo) {
        super(e, fileName);
        this.lineNo = lineNo;
        columnName = null;
        columnIndex = -1;
    }

    /**
     * �R���X�g���N�^�B
     * @param e ������O
     * @param fileName �t�@�C����
     * @param lineNo �G���[�����������s�̍s�ԍ�
     * @param columnName �J������
     * @param columnIndex �G���[�����������J�����ԍ�
     */
    public FileLineException(Exception e, String fileName, int lineNo, String columnName, int columnIndex) {
        super(e, fileName);
        this.columnName = columnName;
        this.columnIndex = columnIndex;
        this.lineNo = lineNo;
    }
    
    /**
     * �R���X�g���N�^�B
     * @param message ���b�Z�[�W
     * @param e ������O
     * @param fileName �t�@�C����
     * @param lineNo �G���[�����������s�̍s�ԍ�
     * @param columnName �J������
     * @param columnIndex �G���[�����������J�����ԍ�
     */
    public FileLineException(String message, Exception e, String fileName, int lineNo, String columnName, int columnIndex) {
        super(message, e, fileName);
        this.columnName = columnName;
        this.columnIndex = columnIndex;
        this.lineNo = lineNo;
    }


    /**
     * �J���������擾����B
     * @return �J������
     */
    public String getColumnName() {
        return this.columnName;
    }

    /**
     * �G���[�����������s�̍s�ԍ����擾����B
     * @return �G���[�����������s�̍s�ԍ�
     */
    public int getLineNo() {
        return this.lineNo;
    }

    /**
     * �G���[�����������J�����̃J�����ԍ����擾����B
     * @return �G���[�����������J�����̃J�����ԍ��i0����J�n�j
     */
    public int getColumnIndex() {
        return columnIndex;
    }
    
    

}
