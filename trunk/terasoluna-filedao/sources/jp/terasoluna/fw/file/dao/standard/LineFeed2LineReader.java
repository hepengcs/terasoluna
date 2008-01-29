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
public class LineFeed2LineReader implements LineReader {

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
     * @param reader ファイルアクセス用の文字ストリーム
     * @param lineFeedChar 行区切り文字
     */
    public LineFeed2LineReader(Reader reader, String lineFeedChar) {
        this.reader = reader;
        this.lineFeedChar = lineFeedChar;
    }

    /**
     * ファイルからデータ部のデータを1行分読み取り、文字列として呼出元に返却する。
     * 
     * @return データ部の１行分の文字列
     */
    public String readLine() {
        StringBuilder currentLineString = new StringBuilder();
        StringBuilder columnBuilder = new StringBuilder(1);

        try {
            while (reader.ready()) {
                char currentChar = (char) reader.read();
                if (currentChar == lineFeedChar.charAt(1)) {
                    if (columnBuilder.charAt(0) == lineFeedChar
                            .charAt(0)) {
                        currentLineString.delete((currentLineString
                                .length() - 1), (currentLineString
                                .length()));
                        break;
                    }
                }
                columnBuilder.delete(0, 1);
                columnBuilder.append(currentChar);
                currentLineString.append(currentChar);
            }
        } catch (IOException e) {
            throw new FileException(e);
        }
        return currentLineString.toString();
    }

}
