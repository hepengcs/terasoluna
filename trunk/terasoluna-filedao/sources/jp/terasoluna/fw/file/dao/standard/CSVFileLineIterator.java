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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jp.terasoluna.fw.file.annotation.FileFormat;
import jp.terasoluna.fw.file.dao.FileException;

/**
 * CSV�t�@�C���p�̃t�@�C���A�N�Z�X(�f�[�^�擾)�N���X�B
 * 
 * <p>
 * CSV�t�@�C������f�[�^��ǂݍ��݁A1�s���̃f�[�^���t�@�C���s�I�u�W�F�N�g�Ɋi�[����B
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
 *   </tr>
 *   <tr>
 *    <td> <code>�͂ݕ���</code> </td>
 *    <td> <code>encloseChar</code> </td>
 *    <td> <code>�Ȃ�('\u0000')</code> </td>
 *    <td> <code>�I�v�V����</code> </td>
 *   </tr>
 *   <tr>
 *    <td> <code>�t�@�C���G���R�[�f�B���O</code> </td>
 *    <td> <code>fileEncodeing</code> </td>
 *    <td> <code>�V�X�e���̃t�@�C���G���R�[�f�B���O</code> </td>
 *    <td> <code>�I�v�V����</code> </td>
 *   </tr>
 *   <tr>
 *    <td> <code>�w�b�_�s��</code> </td>
 *    <td> <code>headerLineCount</code> </td>
 *    <td> <code>0</code> </td>
 *    <td> <code>�I�v�V����</code> </td>
 *   </tr>
 *   <tr>
 *    <td> <code>�g���C��s��</code> </td>
 *    <td> <code>trailerLineCount</code> </td>
 *    <td> <code>0</code> </td>
 *    <td> <code>�I�v�V����</code> </td>
 *   </tr>
 *  </table>
 * </div>
 * <br>
 * �A�D@{@link jp.terasoluna.fw.file.annotation.InputFileColumn}�A@{@link jp.terasoluna.fw.file.annotation.OutputFileColumn}�̐ݒ荀��<br>
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
 *    <td> - </td>
 *    <td> <code>�K�{</code> </td>
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
 * �@<li>��؂蕶����<code>','</cod>�ȊO�ɐݒ肷�邱�Ƃ͏o���Ȃ��B(�G���[����)</li>
 * </ul>
 * 
 * @param <T>
 *            �t�@�C���s�I�u�W�F�N�g�B
 */
public class CSVFileLineIterator<T> extends AbstractFileLineIterator<T> {

    /**
     * ��؂蕶���BCSV�t�@�C���́u,(�J���})�v�ŌŒ�B
     */
    private static final char DELIMITER = ',';

    /**
     * �͂ݕ����B
     */
    private char encloseChar = Character.MIN_VALUE;

    /**
     * �R���X�g���N�^�B
     * 
     * @param fileName
     *            �t�@�C����
     * @param clazz
     *            �t�@�C���s���ʃN���X
     * @param textSetterMap
     *            �J�����t�H�[�}�b�g�������i�[����}�b�v
     */
    public CSVFileLineIterator(String fileName, Class<T> clazz,
            Map<String, ColumnParser> textSetterMap) {

        super(fileName, clazz, textSetterMap);

        FileFormat fileFormat = clazz.getAnnotation(FileFormat.class);

        // ��؂蕶���������l�ȊO�̏ꍇ�A��O���X���[����B
        if (fileFormat.delimiter() != DELIMITER) {
            throw new FileException("Delimiter can not change.",
                    new IllegalStateException(), fileName);
        }

        // �͂ݕ�����ݒ肷��B
        setEncloseChar(fileFormat.encloseChar());
    }

    /**
     * �����񕪊������B �f�[�^���̃f�[�^�P�s�����t�@�C���s�I�u�W�F�N�g��
     * �A�m�e�[�V�����̋L�q�ɏ]���J�����ɕ�������B
     * 
     * @param fileLineString
     *            CSV��1���R�[�h���̕�����
     * @return ������z��
     */
    @Override
    protected String[] separateColumns(String fileLineString) {

        if (fileLineString == null || "".equals(fileLineString)) {
            return new String[0];
        }

        // 1�J�������̕�������i�[���镶���V�[�P���X
        StringBuilder columnBuilder = new StringBuilder();

        // reader���ǂݎ����1�O�̕������i�[���镶���V�[�P���X
        StringBuilder beforeChararacter = new StringBuilder(1);

        // 1�J�����Ɋi�[����镶����̂�����ԍŏ��̕������i�[���镶���V�[�P���X
        StringBuilder columnIni = new StringBuilder(1);

        // ��������i�[���邽�߂̃��X�g
        List<String> columnList = new ArrayList<String>(1);

        boolean isEnclosed = true;
        boolean isEscaped = false;

        if (encloseChar == Character.MIN_VALUE) {
            return fileLineString.split(Character.toString(DELIMITER), -1);
        } else {
            for (char currentChar : fileLineString.toCharArray()) {
                if (columnIni.length() == 0) {
                    columnIni.append(currentChar);
                }
                if (columnIni.charAt(0) == encloseChar) {
                    if (isEnclosed) {
                        if (currentChar == encloseChar) {
                            isEnclosed = false;
                        }
                    } else {
                        if (currentChar == encloseChar && !isEscaped) {
                            isEscaped = true;
                        } else if (currentChar == encloseChar && isEscaped) {
                            columnBuilder.append(currentChar);
                            isEscaped = false;
                        } else if (currentChar == DELIMITER) {
                            if (isEscaped) {
                                columnList.add(columnBuilder.toString());
                                columnIni.delete(0, columnIni.length());
                                columnBuilder.delete(0, columnBuilder.length());
                                isEnclosed = true;
                                isEscaped = false;
                            } else {
                                columnBuilder.append(currentChar);
                                isEscaped = false;
                            }
                        } else {
                            columnBuilder.append(currentChar);
                        }
                    }
                } else {
                    if (currentChar != DELIMITER) {
                        columnBuilder.append(currentChar);
                    } else {
                        columnList.add(columnBuilder.toString());
                        columnIni.delete(0, columnIni.length());
                        columnBuilder.delete(0, columnBuilder.length());
                    }
                }
                beforeChararacter.delete(0, beforeChararacter.length());
                beforeChararacter.append(currentChar);
            }
            columnList.add(columnBuilder.toString());
            return columnList.toArray(new String[columnList.size()]);
        }
    }

    /**
     * ��؂蕶�����擾����B<br>
     * CSV�t�@�C���́u,(�J���})�v�ŌŒ�B
     * 
     * @return delimiter �s��؂蕶��
     */
    @Override
    public char getDelimiter() {

        return DELIMITER;
    }

    /**
     * �͂ݕ������擾����B
     * 
     * @return encloseChar �͂ݕ���
     */
    public char getEncloseChar() {

        return encloseChar;
    }

    /**
     * �͂ݕ�����ݒ肷��B
     * 
     * @param encloseChar
     *            �͂ݕ���
     */
    public void setEncloseChar(char encloseChar) {

        this.encloseChar = encloseChar;
    }
}