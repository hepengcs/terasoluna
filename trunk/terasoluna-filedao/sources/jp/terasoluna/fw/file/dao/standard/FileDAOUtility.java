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
 * FileDAO用のユーティリティ。
 * <p>
 * パディング処理、トリム処理を提供する。
 * </p>
 */
public class FileDAOUtility {
    /**
     * パディング処理。<br>
     * <br>
     * 
     * カラムの文字列をアノテーションで指定された文字でパディングする。<br>
     * 文字列から取り除かれるのは、パディング文字「paddingChar」で指定した文字。<br>
     * パディング文字は半角1文字であるので、全角文字が入力された場合は入力エラーとなる。
     * 
     * @param columnString パディング処理前の１カラム分の文字列
     * @param fileEncoding ファイルエンコーディング
     * @param columnBytes パディング処理後の1カラムのバイト数
     * @param paddingChar パディング文字
     * @param paddingType パディングタイプ
     * @return パディング処理済の１カラム分の文字列
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
     * トリム処理。<br>
     * <br>
     * 
     * カラムの文字列をアノテーションで指定された文字でトリムする。<br>
     * 文字列から取り除かれるのは、トリム文字「trimChar」で指定した文字。<br>
     * トリム文字は半角1文字であるので、全角文字が入力された場合は入力エラーとなる。
     * 
     * @param columnString トリム処理前の１カラム分の文字列
     * @param fileEncoding ファイルエンコーディング
     * @param trimChar トリム文字(半角)
     * @param trimType トリムタイプ
     * @return トリム処理後の１カラム分の文字列
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
