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

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jp.terasoluna.fw.file.annotation.FileFormat;
import jp.terasoluna.fw.file.annotation.InputFileColumn;
import jp.terasoluna.fw.file.dao.FileException;

/**
 * �Œ蒷�t�@�C���p�̃t�@�C���A�N�Z�X(�f�[�^�擾)�N���X�B
 * 
 * <p>
 * �Œ蒷�t�@�C������f�[�^��ǂݍ��݁A
 * 1�s���̃f�[�^���t�@�C���s�I�u�W�F�N�g�Ɋi�[����B
 * </p>
 * 
 * <b>�����p����t�@�C���s�I�u�W�F�N�g�̃A�m�e�[�V��������</b><br>
 * �@�D@{@link FileFormat}�̐ݒ荀��<br>
 * <div align="center">
 *  <table width="90%" border="1" bgcolor="#FFFFFF">
 *   <tr>
 *    <td> <b>�_�����ږ�</b> </td>
 *    <td> <b>�������ږ�</b> </td>
 *    <td> <b>�f�t�H���g�l</b> </td>
 *    <td> <b>�K�{��</b> </td>
 *   </tr>
 *   <tr>
 *    <td> <code>�s��؂蕶��</code> </td>
 *    <td> <code>lineFeedChar</code> </td>
 *    <td> <code>�V�X�e���̍s��؂蕶��</code> </td>
 *    <td> <code>�I�v�V����</code> </td>
 *   <tr>
 *   <tr>
 *    <td> <code>�t�@�C���G���R�[�f�B���O</code> </td>
 *    <td> <code>fileEncodeing</code> </td>
 *    <td> <code>�V�X�e���̃t�@�C���G���R�[�f�B���O</code> </td>
 *    <td> <code>�I�v�V����</code> </td>
 *   <tr>
 *   <tr>
 *    <td> <code>�w�b�_�s��</code> </td>
 *    <td> <code>headerLineCount</code> </td>
 *    <td> <code>0</code> </td>
 *    <td> <code>�I�v�V����</code> </td>
 *   <tr>
 *   <tr>
 *    <td> <code>�g���C��s��</code> </td>
 *    <td> <code>trailerLineCount</code> </td>
 *    <td> <code>0</code> </td>
 *    <td> <code>�I�v�V����</code> </td>
 *   <tr>
 *  </table>
 * </div>
 * <br>
 * �A�D@{@link InputFileColumn}�A@{@link jp.terasoluna.fw.file.annotation.OutputFileColumn}�̐ݒ荀��<br>
 * <div align="center">
 *  <table width="90%" border="1" bgcolor="#FFFFFF">
 *   <tr>
 *    <td> <b>�_�����ږ�</b> </td>
 *    <td> <b>�������ږ�</b> </td>
 *    <td> <b>�f�t�H���g�l</b> </td>
 *    <td> <b>�K�{��</b> </td>
 *   </tr>
 *   <tr>
 *    <td> <code>�J�����C���f�b�N�X</code> </td>
 *    <td> <code>columnIndex</code> </td>
 *    <td> - </td>
 *    <td> <code>�K�{</code> </td>
 *   </tr>
 *   <tr>
 *    <td> <code>�t�H�[�}�b�g</code> </td>
 *    <td> <code>columnFormat</code> </td>
 *    <td> <code>""</code> </td>
 *    <td> <code>�I�v�V����</code> </td>
 *   </tr>
 *   <tr>
 *    <td> <code>�o�C�g��</code> </td>
 *    <td> <code>bytes</code> </td>
 *    <td> <code>-1</code> </td>
 *    <td> <code>�I�v�V����</code> </td>
 *   </tr>
 *   <tr>
 *    <td> <code>�p�f�B���O���</code> </td>
 *    <td> <code>paddingType</code> </td>
 *    <td> <code>�p�f�B���O�Ȃ�</code> </td>
 *    <td> <code>�I�v�V����</code> </td>
 *   </tr>
 *   <tr>
 *    <td> <code>�p�f�B���O����</code> </td>
 *    <td> <code>paddingChar</code> </td>
 *    <td> <code>' '</code> </td>
 *    <td> <code>�I�v�V����</code> </td>
 *   </tr>
 *   <tr>
 *    <td> <code>�g�������</code> </td>
 *    <td> <code>trimType</code> </td>
 *    <td> <code>�g�����Ȃ�</code> </td>
 *    <td> <code>�I�v�V����</code> </td>
 *   </tr>
 *   <tr>
 *    <td> <code>�g��������</code> </td>
 *    <td> <code>trimChar</code> </td>
 *    <td> <code>' '</code> </td>
 *    <td> <code>�I�v�V����</code> </td>
 *   </tr>
 *   <tr>
 *    <td> <code>�����ϊ����</code> </td>
 *    <td> <code>stringConverter</code> </td>
 *    <td> <code>NullStringConverter.class</code> </td>
 *    <td> <code>�I�v�V����</code> </td>
 *   </tr>
 *  </table>
 * </div>
 * <br>
 * <b>�����ӎ���</b><br>
 * <ul>
 * �@<li>��؂蕶����ݒ肷�邱�Ƃ͏o���Ȃ��B(�G���[����)</li>
 * �@<li>�͂ݕ�����ݒ肷�邱�Ƃ͏o���Ȃ��B(�G���[����)</li>
 * </ul>
 * 
 * 
 * @param <T>
 *            �t�@�C���s�I�u�W�F�N�g�B
 */
public class FixedFileLineIterator<T> extends AbstractFileLineIterator<T> {

    /**
     * ��؂蕶���B�Œ蒷�t�@�C���́u,(�J���})�v�ŌŒ�B
     */
    private static final char DELIMITER = ',';

    /**
     * �͂ݕ����B�Œ蒷�t�@�C���́u'\u0000'�v�ŌŒ�B
     */
    private static final char ENCLOSE_CHAR = Character.MIN_VALUE;

    /**
     * �R���X�g���N�^�B
     * 
     * @param fileName
     *            �t�@�C����
     * @param clazz
     *            ���ʃN���X
     * @param textSetterMap
     *            �t�H�[�}�b�g�������X�g
     */
    public FixedFileLineIterator(String fileName, Class<T> clazz,
            Map<String, ColumnParser> textSetterMap) {

        super(fileName, clazz, textSetterMap);

        FileFormat fileFormat = clazz.getAnnotation(FileFormat.class);

        // ��؂蕶���������l�ȊO�̏ꍇ�A��O���X���[����B
        if (fileFormat.delimiter() != DELIMITER) {
            throw new FileException("Delimiter can not change.",
                    new IllegalStateException(), fileName);
        }

        // �͂ݕ����������l�ȊO�̏ꍇ�A��O���X���[����B
        if (fileFormat.encloseChar() != ENCLOSE_CHAR) {
            throw new FileException("EncloseChar can not change.",
                    new IllegalStateException(), fileName);
        }
    }

    /**
     * �ǂݍ��񂾌Œ蒷�̃��R�[�h���A�m�e�[�V������byte���A
     * columnIndex�]���ĕ�������B
     * 
     * <p>
     * �����̏����́A<br>
     * <ul>
     * <li>�t�@�C���s�I�u�W�F�N�g�̃A�m�e�[�V�����Œ�`����columnIndex�̏��ɕ��ёւ���</li>
     * <li>�t�@�C���s�I�u�W�F�N�g�̃A�m�e�[�V�����Œ�`����bytes�����̕�����𐶐�����</li>
     * <li>2�o�C�g�������m�F����ہAbytes�����z���Ċi�[���悤�Ƃ���Ɨ�O���X���[����</li>
     * <li>columnIndex�̔���(�r���̔ԍ����Ȃ��ꍇ�A�����Ă���ꍇ)�͏������p�����邪�A
     * columnIndex�ɏd�����������ꍇ�́A���̎��_�ŗ�O���X���[����</li>
     * </ul>
     * <p>
     * 
     * @param fileLineString
     *            �Œ蒷�t�@�C���̂�1���R�[�h���̕�����
     * @return �f�[�^���P�s�̕�����𕪉����������z��
     */
    protected String[] separateColumns(String fileLineString) {

        // �t�@�C���s�I�u�W�F�N�g�Œ�`����Ă���J�����̃o�C�g���̍��v���i�[����B
        int columnBytesTotal = 0;

        if (fileLineString == null || "".equals(fileLineString)) {
            return new String[0];
        }

        Field[] fieldList = new Field[getFields().length];

        List<String> columnList = new ArrayList<String>();

        // �t�@�C���s�I�u�W�F�N�g�̑�����,�A�m�e�[�V�����Œ�`����columnIndex��
        // ���ɕ��בւ���B�d�����������ꍇ�̓G���[�Ƃ���B
        for (Field field : getFields()) {
            if (field != null) {
                InputFileColumn inputFileColumn = field
                        .getAnnotation(InputFileColumn.class);
                if (inputFileColumn != null) {
                    if (fieldList[inputFileColumn.columnIndex()] == null) {
                        fieldList[inputFileColumn.columnIndex()] = field;
                    } else {
                        throw new FileException("Column index is repeated.",
                                this.getFileName());
                    }
                }
            }
        }
        StringBuilder columnBuilder = new StringBuilder();
        int fileLineStringIndex = 0;
        for (Field field : fieldList) {
            int columnBytes = 0;
            // columnBuilder�̃f�[�^���N���A
            columnBuilder.delete(0, columnBuilder.length());
            if (field != null) {
                InputFileColumn inputFileColumn = field
                        .getAnnotation(InputFileColumn.class);
                // �J�����̃o�C�g�����A�m�e�[�V�����Őݒ肵���o�C�g��������
                // ���Ȃ��ԁA���[�v���񂷁B
                while (columnBytes < inputFileColumn.bytes()) {

                    if (fileLineString.length() <= fileLineStringIndex) {
                        throw new FileException("Data size is different from"
                                + " a set point of a column.",
                                this.getFileName());
                    }

                    // 1�s���̕����񂩂�1�������o��
                    String columnChar = fileLineString.substring(
                            fileLineStringIndex, fileLineStringIndex + 1);
                    try {
                        // ���܂łɓǂݎ���Ă���J�����̃o�C�g���ƁA�ǂݎ����
                        // �����̃o�C�g�����A�m�e�[�V�����������������Ƃ��m�F����B
                        if ((columnChar.getBytes(getFileEncoding()).length
                                + columnBytes) <= inputFileColumn.bytes()) {
                            // �ǂݎ����������columnBuilder�֒ǉ��B
                            columnBuilder.append(columnChar);
                            // �ǂݎ���������̃o�C�g����ǉ��B
                            columnBytes = columnBytes
                                + columnChar.getBytes(getFileEncoding()).length;
                            // �ǂݎ�镶���̃C���f�b�N�X��1���炷�B
                            fileLineStringIndex++;
                        } else {
                            throw new FileException("Data size is different "
                                    + "from a set point of a column.",
                                    this.getFileName());
                        }
                    } catch (UnsupportedEncodingException e) {
                        throw new FileException(e, this.getFileName());
                    }
                }
                columnBytesTotal += inputFileColumn.bytes();
                columnList.add(columnBuilder.toString());
            }
        }
        // �t�@�C������ǂݎ����1�s������̃o�C�g���ƁA�t�@�C���s�I�u�W�F�N�g��
        // ��`�����o�C�g���̍��v���r����B
        try {
            if (columnBytesTotal
                    != fileLineString.getBytes(getFileEncoding()).length) {
                throw new FileException("Total Columns byte is not different "
                        + "from Total FileLineObject's columns byte.",
                        new IllegalStateException(), this.getFileName());
            }
        } catch (UnsupportedEncodingException e) {
            throw new FileException(e, this.getFileName());
        }
        return columnList.toArray(new String[columnList.size()]);
    }

    /**
     * ��؂蕶�����擾����B<br>
     * �Œ蒷�t�@�C���́u,(�J���})�v�ŌŒ�B
     * 
     * @return �s��؂蕶��
     */
    @Override
    public char getDelimiter() {

        return DELIMITER;
    }

    /**
     * �͂ݕ������擾����B<br>
     * �Œ蒷�t�@�C���́u'\u0000'�v�ŌŒ�B
     * 
     * @return �͂ݕ���
     */
    @Override
    public char getEncloseChar() {

        return ENCLOSE_CHAR;
    }
}
