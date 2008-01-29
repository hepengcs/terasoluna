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
 * CSVファイル用のファイルアクセス(データ取得)クラス。
 * 
 * <p>
 * CSVファイルからデータを読み込み、1行分のデータをファイル行オブジェクトに格納する。
 * </p>
 * 
 * <b>※利用するファイル行オブジェクトのアノテーション項目</b><br>
 * �@．@{@link FileFormat}の設定項目<br>
 * <div align="center">
 *  <table width="90%" border="1" bgcolor="#FFFFFF">
 *   <tr>
 *    <td> <b>論理項目名</b> </td>
 *    <td> <b>物理項目名</b> </td>
 *    <td> <b>デフォルト値</b> </td>
 *    <td> <b>必須性</b> </td>
 *   </tr>
 *   <tr>
 *    <td> <code>行区切り文字</code> </td>
 *    <td> <code>lineFeedChar</code> </td>
 *    <td> <code>システムの行区切り文字</code> </td>
 *    <td> <code>オプション</code> </td>
 *   </tr>
 *   <tr>
 *    <td> <code>囲み文字</code> </td>
 *    <td> <code>encloseChar</code> </td>
 *    <td> <code>なし('\u0000')</code> </td>
 *    <td> <code>オプション</code> </td>
 *   </tr>
 *   <tr>
 *    <td> <code>ファイルエンコーディング</code> </td>
 *    <td> <code>fileEncodeing</code> </td>
 *    <td> <code>システムのファイルエンコーディング</code> </td>
 *    <td> <code>オプション</code> </td>
 *   </tr>
 *   <tr>
 *    <td> <code>ヘッダ行数</code> </td>
 *    <td> <code>headerLineCount</code> </td>
 *    <td> <code>0</code> </td>
 *    <td> <code>オプション</code> </td>
 *   </tr>
 *   <tr>
 *    <td> <code>トレイら行数</code> </td>
 *    <td> <code>trailerLineCount</code> </td>
 *    <td> <code>0</code> </td>
 *    <td> <code>オプション</code> </td>
 *   </tr>
 *  </table>
 * </div>
 * <br>
 * �A．@{@link jp.terasoluna.fw.file.annotation.InputFileColumn}、@{@link jp.terasoluna.fw.file.annotation.OutputFileColumn}の設定項目<br>
 * <div align="center">
 *  <table width="90%" border="1" bgcolor="#FFFFFF">
 *   <tr>
 *    <td> <b>論理項目名</b> </td>
 *    <td> <b>物理項目名</b> </td>
 *    <td> <b>デフォルト値</b> </td>
 *    <td> <b>必須性</b> </td>
 *   </tr>
 *   <tr>
 *    <td> <code>カラムインデックス</code> </td>
 *    <td> <code>columnIndex</code> </td>
 *    <td> - </td>
 *    <td> <code>必須</code> </td>
 *   </tr>
 *   <tr>
 *    <td> <code>フォーマット</code> </td>
 *    <td> <code>columnFormat</code> </td>
 *    <td> <code>""</code> </td>
 *    <td> <code>オプション</code> </td>
 *   </tr>
 *   <tr>
 *    <td> <code>バイト長</code> </td>
 *    <td> <code>bytes</code> </td>
 *    <td> - </td>
 *    <td> <code>必須</code> </td>
 *   </tr>
 *   <tr>
 *    <td> <code>パディング種別</code> </td>
 *    <td> <code>paddingType</code> </td>
 *    <td> <code>パディングなし</code> </td>
 *    <td> <code>オプション</code> </td>
 *   </tr>
 *   <tr>
 *    <td> <code>パディング文字</code> </td>
 *    <td> <code>paddingChar</code> </td>
 *    <td> <code>' '</code> </td>
 *    <td> <code>オプション</code> </td>
 *   </tr>
 *   <tr>
 *    <td> <code>トリム種別</code> </td>
 *    <td> <code>trimType</code> </td>
 *    <td> <code>トリムなし</code> </td>
 *    <td> <code>オプション</code> </td>
 *   </tr>
 *   <tr>
 *    <td> <code>トリム文字</code> </td>
 *    <td> <code>trimChar</code> </td>
 *    <td> <code>' '</code> </td>
 *    <td> <code>オプション</code> </td>
 *   </tr>
 *   <tr>
 *    <td> <code>文字変換種別</code> </td>
 *    <td> <code>stringConverter</code> </td>
 *    <td> <code>NullStringConverter.class</code> </td>
 *    <td> <code>オプション</code> </td>
 *   </tr>
 *  </table>
 * </div>
 * <br>
 * <b>※注意事項</b><br>
 * <ul>
 * 　<li>区切り文字を<code>','</cod>以外に設定することは出来ない。(エラー発生)</li>
 * </ul>
 * 
 * @param <T>
 *            ファイル行オブジェクト。
 */
public class CSVFileLineIterator<T> extends AbstractFileLineIterator<T> {

    /**
     * 区切り文字。CSVファイルは「,(カンマ)」で固定。
     */
    private static final char DELIMITER = ',';

    /**
     * 囲み文字。
     */
    private char encloseChar = Character.MIN_VALUE;

    /**
     * コンストラクタ。
     * 
     * @param fileName
     *            ファイル名
     * @param clazz
     *            ファイル行結果クラス
     * @param textSetterMap
     *            カラムフォーマット処理を格納するマップ
     */
    public CSVFileLineIterator(String fileName, Class<T> clazz,
            Map<String, ColumnParser> textSetterMap) {

        super(fileName, clazz, textSetterMap);

        FileFormat fileFormat = clazz.getAnnotation(FileFormat.class);

        // 区切り文字が初期値以外の場合、例外をスローする。
        if (fileFormat.delimiter() != DELIMITER) {
            throw new FileException("Delimiter can not change.",
                    new IllegalStateException(), fileName);
        }

        // 囲み文字を設定する。
        setEncloseChar(fileFormat.encloseChar());
    }

    /**
     * 文字列分割処理。 データ部のデータ１行分をファイル行オブジェクトの
     * アノテーションの記述に従いカラムに分割する。
     * 
     * @param fileLineString
     *            CSVの1レコード分の文字列
     * @return 文字列配列
     */
    @Override
    protected String[] separateColumns(String fileLineString) {

        if (fileLineString == null || "".equals(fileLineString)) {
            return new String[0];
        }

        // 1カラム分の文字列を格納する文字シーケンス
        StringBuilder columnBuilder = new StringBuilder();

        // readerが読み取った1つ前の文字を格納する文字シーケンス
        StringBuilder beforeChararacter = new StringBuilder(1);

        // 1カラムに格納される文字列のうち一番最初の文字を格納する文字シーケンス
        StringBuilder columnIni = new StringBuilder(1);

        // 文字列を格納するためのリスト
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
     * 区切り文字を取得する。<br>
     * CSVファイルは「,(カンマ)」で固定。
     * 
     * @return delimiter 行区切り文字
     */
    @Override
    public char getDelimiter() {

        return DELIMITER;
    }

    /**
     * 囲み文字を取得する。
     * 
     * @return encloseChar 囲み文字
     */
    public char getEncloseChar() {

        return encloseChar;
    }

    /**
     * 囲み文字を設定する。
     * 
     * @param encloseChar
     *            囲み文字
     */
    public void setEncloseChar(char encloseChar) {

        this.encloseChar = encloseChar;
    }
}
