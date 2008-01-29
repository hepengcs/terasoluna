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

import org.apache.commons.lang.StringUtils;

import jp.terasoluna.fw.file.annotation.PaddingType;
import jp.terasoluna.fw.file.annotation.TrimType;
import jp.terasoluna.fw.file.dao.FileException;

/**
 * FileDAO�p�̃��[�e�B���e�B�B
 * <p>
 * �p�f�B���O�����A�g����������񋟂���B
 * </p>
 */
public class FileDAOUtility {
    /**
     * �p�f�B���O�����B<br>
     * <br>
     * 
     * �J�����̕�������A�m�e�[�V�����Ŏw�肳�ꂽ�����Ńp�f�B���O����B<br>
     * �����񂩂��菜�����̂́A�p�f�B���O�����upaddingChar�v�Ŏw�肵�������B<br>
     * �p�f�B���O�����͔��p1�����ł���̂ŁA�S�p���������͂��ꂽ�ꍇ�͓��̓G���[�ƂȂ�B
     * 
     * @param columnString �p�f�B���O�����O�̂P�J�������̕�����
     * @param fileEncoding �t�@�C���G���R�[�f�B���O
     * @param columnBytes �p�f�B���O�������1�J�����̃o�C�g��
     * @param paddingChar �p�f�B���O����
     * @param paddingType �p�f�B���O�^�C�v
     * @return �p�f�B���O�����ς̂P�J�������̕�����
     * 
     */
    public static String padding(String columnString, String fileEncoding,
            int columnBytes, char paddingChar, PaddingType paddingType) {
        try {
            StringBuilder columnBuilder = new StringBuilder();
            if (1 < Character.toString(paddingChar)
                    .getBytes(fileEncoding).length) {
                throw new FileException(
                        "Padding char is not half-width character.");
            }

            if (PaddingType.LEFT.equals(paddingType)) {
                for (int i = 0; i < (columnBytes - columnString
                        .getBytes(fileEncoding).length); i++) {
                    columnBuilder.append(paddingChar);
                }
                columnBuilder.append(columnString);
                return columnBuilder.toString();
            } else if (PaddingType.RIGHT.equals(paddingType)) {
                columnBuilder.append(columnString);
                for (int i = 0; i < (columnBytes - columnString
                        .getBytes(fileEncoding).length); i++) {
                    columnBuilder.append(paddingChar);
                }
                return columnBuilder.toString();
            } else {
                return columnString;
            }
        } catch (UnsupportedEncodingException e) {
            throw new FileException(e);
        }
    }

    /**
     * �g���������B<br>
     * <br>
     * 
     * �J�����̕�������A�m�e�[�V�����Ŏw�肳�ꂽ�����Ńg��������B<br>
     * �����񂩂��菜�����̂́A�g���������utrimChar�v�Ŏw�肵�������B<br>
     * �g���������͔��p1�����ł���̂ŁA�S�p���������͂��ꂽ�ꍇ�͓��̓G���[�ƂȂ�B
     * 
     * @param columnString �g���������O�̂P�J�������̕�����
     * @param fileEncoding �t�@�C���G���R�[�f�B���O
     * @param trimChar �g��������(���p)
     * @param trimType �g�����^�C�v
     * @return �g����������̂P�J�������̕�����
     */
    public static String trim(String columnString, String fileEncoding,
            char trimChar, TrimType trimType) {
        StringBuilder columnBuffer = new StringBuilder();
        boolean trimFlag = false;

        try {
            if (1 < Character.toString(trimChar)
                    .getBytes(fileEncoding).length) {
                throw new FileException(
                        "Trim char is not half-width character.");
            }
        } catch (UnsupportedEncodingException e) {
            throw new FileException(e);
        }
        
        if (TrimType.LEFT.equals(trimType)) {
            for (char columnChar : columnString.toCharArray()) {
                if (trimFlag) {
                    columnBuffer.append((char) columnChar);
                } else {
                    if (columnChar != trimChar) {
                        trimFlag = true;
                        columnBuffer.append((char) columnChar);
                    }
                }
            }
            return columnBuffer.toString();
        } else if (TrimType.RIGHT.equals(trimType)) {
            String reverseColumn = StringUtils.reverse(columnString);
            for (char columnChar : reverseColumn.toCharArray()) {
                if (trimFlag) {
                    columnBuffer.append((char) columnChar);
                } else {
                    if (columnChar != trimChar) {
                        trimFlag = true;
                        columnBuffer.append((char) columnChar);
                    }
                }
            }
            return StringUtils.reverse(columnBuffer.toString());
        }
        return columnString;
    }
}
