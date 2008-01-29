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
 * ファイルからデータ部のデータを1行分読み取り、文字列として呼出元に返却する。
 * 
 *
 */
public class EncloseCharLineFeed2LineReader implements LineReader {

    /**
     * 区切り文字。
     */
    private char delimiterCharacter;
    
    /**
     * 囲み文字。
     */
    private char encloseCharacter;
    
    /**
     * ファイルアクセス用の文字ストリーム。
     */
    private Reader reader;
    
    /**
     * 行区切り文字。
     */
    private String lineFeedChar;
    
    /**
     * コンストラクタ。
     * @param delimiterCharacter 区切り文字
     * @param encloseCharacter 囲み文字
     * @param reader ファイルアクセス用の文字ストリーム
     * @param lineFeedChar 行区切り文字
     */
    public EncloseCharLineFeed2LineReader(char delimiterCharacter,
            char encloseCharacter, Reader reader, String lineFeedChar) {
        this.delimiterCharacter = delimiterCharacter;
        this.encloseCharacter = encloseCharacter;
        this.reader = reader;
        this.lineFeedChar = lineFeedChar;
    }
    
    /**
     * ファイルからデータ部のデータを1行分読み取り、文字列として呼出元に返却する。
     * 
     * @return データ部の１行分の文字列
     */
    public String readLine() {
        
        // 1カラム分の文字列を格納するバッファ
        StringBuilder currentLineString = new StringBuilder();
        
        // カラムの先頭の文字を格納するバッファ
        StringBuilder columnIni = new StringBuilder(1);

        // 囲み文字が終了しているか確認するフラグ。trueならカラムは囲み文字で囲まれている。
        boolean isEnclosed = true;
        
        // エスケープシーケンスを読み込んだらtrueに。それ以外の場合はfalse。
        boolean isEscape = false;

        // 行区切り文字の1文字目。
        char lineFeedChar1 = lineFeedChar.charAt(0);
        
        // 行区切り文字の2文字目。
        
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
