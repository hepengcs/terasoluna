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
import java.io.Reader;

import jp.terasoluna.fw.file.dao.FileException;


/**
 * �t�@�C������f�[�^���̃f�[�^��1�s���ǂݎ��A������Ƃ��Čďo���ɕԋp����B
 * 
 *
 */
public class EncloseCharLineFeed2LineReader implements LineReader {

    /**
     * ��؂蕶���B
     */
    private char delimiterCharacter;
    
    /**
     * �͂ݕ����B
     */
    private char encloseCharacter;
    
    /**
     * �t�@�C���A�N�Z�X�p�̕����X�g���[���B
     */
    private Reader reader;
    
    /**
     * �s��؂蕶���B
     */
    private String lineFeedChar;
    
    /**
     * �R���X�g���N�^�B
     * @param delimiterCharacter ��؂蕶��
     * @param encloseCharacter �͂ݕ���
     * @param reader �t�@�C���A�N�Z�X�p�̕����X�g���[��
     * @param lineFeedChar �s��؂蕶��
     */
    public EncloseCharLineFeed2LineReader(char delimiterCharacter,
            char encloseCharacter, Reader reader, String lineFeedChar) {
        this.delimiterCharacter = delimiterCharacter;
        this.encloseCharacter = encloseCharacter;
        this.reader = reader;
        this.lineFeedChar = lineFeedChar;
    }
    
    /**
     * �t�@�C������f�[�^���̃f�[�^��1�s���ǂݎ��A������Ƃ��Čďo���ɕԋp����B
     * 
     * @return �f�[�^���̂P�s���̕�����
     */
    public String readLine() {
        
        // 1�J�������̕�������i�[����o�b�t�@
        StringBuilder currentLineString = new StringBuilder();
        
        // �J�����̐擪�̕������i�[����o�b�t�@
        StringBuilder columnIni = new StringBuilder(1);

        // �͂ݕ������I�����Ă��邩�m�F����t���O�Btrue�Ȃ�J�����͈͂ݕ����ň͂܂�Ă���B
        boolean isEnclosed = true;
        
        // �G�X�P�[�v�V�[�P���X��ǂݍ��񂾂�true�ɁB����ȊO�̏ꍇ��false�B
        boolean isEscape = false;

        // �s��؂蕶����1�����ځB
        char lineFeedChar1 = lineFeedChar.charAt(0);
        
        // �s��؂蕶����2�����ځB
        
        char lineFeedChar2 = lineFeedChar.charAt(1);
        try {
            while (reader.ready()) {
                char currentChar = (char) reader.read();
                if (columnIni == null || columnIni.length() == 0) {
                    columnIni.append(currentChar);
                }
                if (columnIni.charAt(0) == encloseCharacter) {
                    if (isEnclosed) {
                        if (currentChar == encloseCharacter) {
                            isEnclosed = false;
                            currentLineString.append(currentChar);
                        }
                    } else {
                        if (currentChar == encloseCharacter
                                && !isEscape) {
                            isEscape = true;
                        } else if (currentChar == encloseCharacter
                                && isEscape) {
                            isEscape = false;
                            currentLineString.append(currentChar);
                            currentLineString.append(currentChar);
                        } else if (currentChar == delimiterCharacter) {
                            if (isEscape) {
                                currentLineString
                                        .append(encloseCharacter);
                                columnIni.delete(0, 1);
                                isEnclosed = true;
                                isEscape = false;
                            }
                            currentLineString.append(currentChar);
                        } else if (currentChar == lineFeedChar1) {
                            if (isEscape) {
                                currentLineString
                                        .append(encloseCharacter);
                                columnIni.delete(0, 1);
                                columnIni.append(currentChar);
                                isEnclosed = true;
                                isEscape = false;
                            }
                            currentLineString.append(currentChar);
                        } else {
                            if (isEscape) {
                                currentLineString
                                        .append(encloseCharacter);
                                columnIni.delete(0, 1);
                                columnIni.append(currentChar);
                                isEnclosed = true;
                                isEscape = false;
                            }
                            currentLineString.append(currentChar);
                        }
                    }
                } else if (columnIni.charAt(0) == lineFeedChar1) {
                    if (currentChar == lineFeedChar2) {
                        if (currentLineString.length() - 1 >= 0
                                && currentLineString.charAt(currentLineString
                                .length() - 1) == lineFeedChar1) {
                            currentLineString
                                    .deleteCharAt(currentLineString
                                            .length() - 1);
                            columnIni.delete(0, 1);
                            isEnclosed = true;
                            isEscape = false;
                            break;
                        }
                    }
                    currentLineString.append(currentChar);
                } else {
                    if (currentChar == delimiterCharacter) {
                        currentLineString.append(currentChar);
                        columnIni.delete(0, 1);
                        isEnclosed = true;
                        isEscape = false;
                    } else if (currentChar == lineFeedChar2) {
                        if (currentLineString.length() - 1 >= 0
                                && currentLineString.charAt(currentLineString
                                .length() - 1) == lineFeedChar1) {
                            currentLineString
                                    .deleteCharAt(currentLineString
                                            .length() - 1);
                            columnIni.delete(0, 1);
                            isEnclosed = true;
                            isEscape = false;
                            break;
                        } else {
                            currentLineString.append(currentChar);
                        }
                    } else {
                        currentLineString.append(currentChar);
                    }
                }
            }
        } catch (IOException e) {
            throw new FileException(e);
        }
        
        return currentLineString.toString();
    }

}
