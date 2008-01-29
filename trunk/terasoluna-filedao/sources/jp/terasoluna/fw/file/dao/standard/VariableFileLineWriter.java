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
import jp.terasoluna.fw.file.dao.FileException;

/**
 * �ϒ��t�@�C���p�̃t�@�C���A�N�Z�X(�f�[�^����)�N���X�B
 * 
 * <p>
 * �t�@�C���s�I�u�W�F�N�g����f�[�^��ǂݍ��݁A1�s���̃f�[�^���ϒ��`����
 * �t�@�C���ɏ������ށB
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
 *    <td> <code>��؂蕶��</code> </td>
 *    <td> <code>delimiter</code> </td>
 *    <td> <code>','</code> </td>
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
 *    <td> <code>�t�@�C���㏑���t���O</code> </td>
 *    <td> <code>overWriteFlg</code> </td>
 *    <td> <code>false</code> </td>
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
 * 
 * <b>�����ӎ���</b><br>
 * <ul>
 * �@<li>��؂蕶����Caracter.MIN_VALUE��ݒ肷�邱�Ƃ͏o���Ȃ��B(�G���[����)</li>
 * </ul>
 * 
 * 
 * @param <T>
 *            �t�@�C���s�I�u�W�F�N�g
 */
public class VariableFileLineWriter<T> extends AbstractFileLineWriter<T> {

    /**
     * ��؂蕶���B�ϒ��t�@�C���o�͂̏ꍇ�A�f�t�H���g�́u,(�J���})�v�B
     */
    private char delimiter = ',';

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
     *            �p�����[�^�N���X
     * @param columnFormatterMap
     *            �e�L�X�g�擾���[��
     */
    public VariableFileLineWriter(String fileName, Class<T> clazz,
            Map<String, ColumnFormatter> columnFormatterMap) {
    
        super(fileName, clazz, columnFormatterMap);
    
        FileFormat fileFormat = clazz.getAnnotation(FileFormat.class);

        // ��؂蕶����Character.MIN_VALUE�̏ꍇ�A��O���X���[����B
        if (fileFormat.delimiter() == Character.MIN_VALUE) {
            throw new FileException("Delimiter can not use '\\u0000'.",
                    new IllegalStateException(), fileName);
        }
        
        // ��؂蕶����ݒ肷��B
        delimiter = fileFormat.delimiter();
    
        // �͂ݕ�����ݒ肷��B
        encloseChar = fileFormat.encloseChar();
        
        //����������
        super.init();
    }

    /**
     * ��؂蕶�����擾����B
     * 
     * @return ��؂蕶��
     */
    public char getDelimiter() {

        return delimiter;
    }

    /**
     * �͂ݕ������擾����B
     * 
     * @return �͂ݕ���
     */
    public char getEncloseChar() {

        return encloseChar;
    }
}