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

import jp.terasoluna.fw.file.annotation.FileFormat;
import jp.terasoluna.fw.file.annotation.OutputFileColumn;
import jp.terasoluna.fw.file.dao.FileException;

/**
 * �Œ蒷�t�@�C���p�̃t�@�C���A�N�Z�X(�f�[�^����)�N���X.
 * 
 * <p>
 * �t�@�C���s�I�u�W�F�N�g����f�[�^��ǂݍ��݁A 
 * 1�s���̃f�[�^���Œ蒷�`���Ńt�@�C���ɏ������ށB
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
 *    <td> <code>�t�@�C���G���R�[�f�B���O</code> </td>
 *    <td> <code>fileEncodeing</code> </td>
 *    <td> <code>�V�X�e���̃t�@�C���G���R�[�f�B���O</code> </td>
 *    <td> <code>�I�v�V����</code> </td>
 *   </tr>
 *   <tr>
 *    <td> <code>�t�@�C���㏑���t���O</code> </td>
 *    <td> <code>overWriteFlg</code> </td>
 *    <td> <code>false</code> </td>
 *    <td> <code>�I�v�V����</code> </td>
 *   </tr>
 *  </table>
 * </div>
 * <br>
 * �A�D@{@link jp.terasoluna.fw.file.annotation.InputFileColumn}�A@{@link OutputFileColumn}�̐ݒ荀��<br>
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
 * @param <T>
 *            �t�@�C���s�I�u�W�F�N�g�B
 */
public class FixedFileLineWriter<T> extends AbstractFileLineWriter<T> {

    /**
     * ��؂蕶���B�Œ蒷�t�@�C���́u'\u0000'�v�ŌŒ�B
     */
    private static final char DELIMITER = Character.MIN_VALUE;

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
     *            �p�����[�^�N���X
     * @param columnFormatterrMap
     *            �e�L�X�g�擾���[��
     */
    public FixedFileLineWriter(String fileName, Class<T> clazz,
            Map<String, ColumnFormatter> columnFormatterrMap) {

        super(fileName, clazz, columnFormatterrMap);

        FileFormat fileFormat = clazz.getAnnotation(FileFormat.class);

        // ��؂蕶���������l�ȊO�̏ꍇ�A��O���X���[����B
        if (fileFormat.delimiter() != ',') {
            throw new FileException("Delimiter can not change.",
                    new IllegalStateException(), fileName);
        }

        // �͂ݕ����������l�ȊO�̏ꍇ�A��O���X���[����B
        if (fileFormat.encloseChar() != ENCLOSE_CHAR) {
            throw new FileException("EncloseChar can not change.",
                    new IllegalStateException(), fileName);
        }
        
        //����������
        super.init();
    }

    /**
     * �o�C�g���`�F�b�N���s�����ǂ����B �Œ蒷�̏ꍇ�A
     * �K���`�F�b�N���s������true��ԋp����B
     * 
     * @param outputFileColumn
     *            �o�̓J����
     * @return true
     */
    protected boolean isCheckByte(OutputFileColumn outputFileColumn) {

        return true;
    }

    /**
     * ��؂蕶�����擾����B<br>
     * �Œ蒷�t�@�C���́u'\u0000'�v�ŌŒ�B
     * 
     * @return ��؂蕶��
     */
    public char getDelimiter() {

        return DELIMITER;
    }

    /**
     * �͂ݕ������擾����B<br>
     * �Œ蒷�t�@�C���́u'\u0000'�v�ŌŒ�B
     * 
     * @return �͂ݕ���
     */
    public char getEncloseChar() {

        return ENCLOSE_CHAR;
    }

}
