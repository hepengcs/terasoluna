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

import java.io.IOException;
import java.util.Map;

import jp.terasoluna.fw.file.dao.FileException;

/**
 * �t�@�C���s�I�u�W�F�N�g��p���Ȃ��t�@�C�������@�\�B
 * 
 * <p>
 * �r�W�l�X���W�b�N�Ȃǂ���󂯎������������t�@�C���ɏo�͂���B 
 * ���̃t�@�C���A�N�Z�X�@�\�Ƃ͈قȂ�A�t�@�C���s�I�u�W�F�N�g���g��Ȃ��B
 * </p>
 * 
 * <b>�����p����t�@�C���s�I�u�W�F�N�g�̃A�m�e�[�V��������</b><br>
 * �@�D@{@link jp.terasoluna.fw.file.annotation.FileFormat}�̐ݒ荀��<br>
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
 * 
 * <b>�����ӎ���</b><br>
 * <ul>
 * �@<li>��؂蕶���ƈ͂ݕ����̐ݒ�͖�������B</li>
 * </ul>
 * 
 */
public class PlainFileLineWriter
        extends AbstractFileLineWriter<String> {

    /**
     * �R���X�g���N�^�B
     * 
     * @param fileName �t�@�C����
     * @param clazz �p�����[�^�N���X
     * @param columnFormatterMap �e�L�X�g�擾���[��
     */
    @SuppressWarnings("unchecked")
    public PlainFileLineWriter(String fileName, Class clazz,
            Map<String, ColumnFormatter> columnFormatterMap) {
        super(fileName, clazz, columnFormatterMap);
        
        //����������
        super.init();
    }

    /**
     * �t�@�C���s�I�u�W�F�N�g�̃f�[�^���������ށB
     * 
     * @param t �t�@�C���s�I�u�W�F�N�g
     */
    @Override
    public void printDataLine(String t) {
        checkWriteTrailer();
        try {
            getWriter().write(t);
            getWriter().write(getLineFeedChar());
        } catch (IOException e) {
            throw new FileException(e, getFileName());
        }
        setWriteData(true);
    }

    /**
     * ��؂蕶�����擾����B
     * @return ��؂蕶��
     */
    @Override
    public char getDelimiter() {
        return 0;
    }

    /**
     * �͂ݕ������擾����B
     * @return �͂ݕ���
     */
    @Override
    public char getEncloseChar() {
        return 0;
    }
}
