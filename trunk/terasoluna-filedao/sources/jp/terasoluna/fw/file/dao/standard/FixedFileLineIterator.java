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
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jp.terasoluna.fw.file.annotation.FileFormat;
import jp.terasoluna.fw.file.annotation.InputFileColumn;
import jp.terasoluna.fw.file.dao.FileException;

/**
 * 固定長ファイル用のファイルアクセス(データ取得)クラス。
 * 
 * <p>
 * 固定長ファイルからデータを読み込み、
 * 1行分のデータをファイル行オブジェクトに格納する。
 * </p>
 * 
 * <b>※利用するファイル行オブジェクトのアノテーション項目</b><br>
 * ⅰ．@{@link FileFormat}の設定項目<br>
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
 *   <tr>
 *   <tr>
 *    <td> <code>ファイルエンコーディング</code> </td>
 *    <td> <code>fileEncodeing</code> </td>
 *    <td> <code>システムのファイルエンコーディング</code> </td>
 *    <td> <code>オプション</code> </td>
 *   <tr>
 *   <tr>
 *    <td> <code>ヘッダ行数</code> </td>
 *    <td> <code>headerLineCount</code> </td>
 *    <td> <code>0</code> </td>
 *    <td> <code>オプション</code> </td>
 *   <tr>
 *   <tr>
 *    <td> <code>トレイら行数</code> </td>
 *    <td> <code>trailerLineCount</code> </td>
 *    <td> <code>0</code> </td>
 *    <td> <code>オプション</code> </td>
 *   <tr>
 *  </table>
 * </div>
 * <br>
 * ⅱ．@{@link InputFileColumn}、@{@link jp.terasoluna.fw.file.annotation.OutputFileColumn}の設定項目<br>
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
 *    <td> <code>-1</code> </td>
 *    <td> <code>オプション</code> </td>
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
 * 　<li>区切り文字を設定することは出来ない。(エラー発生)</li>
 * 　<li>囲み文字を設定することは出来ない。(エラー発生)</li>
 * </ul>
 * 
 * 
 * @param <T>
 *            ファイル行オブジェクト。
 */
public class FixedFileLineIterator<T> extends AbstractFileLineIterator<T> {

    /**
     * 区切り文字。固定長ファイルは「,(カンマ)」で固定。
     */
    private static final char DELIMITER = ',';

    /**
     * 囲み文字。固定長ファイルは「'\u0000'」で固定。
     */
    private static final char ENCLOSE_CHAR = Character.MIN_VALUE;

    /**
     * コンストラクタ。
     * 
     * @param fileName
     *            ファイル名
     * @param clazz
     *            結果クラス
     * @param textSetterMap
     *            フォーマット処理リスト
     */
    public FixedFileLineIterator(String fileName, Class<T> clazz,
            Map<String, ColumnParser> textSetterMap) {

        super(fileName, clazz, textSetterMap);

        FileFormat fileFormat = clazz.getAnnotation(FileFormat.class);

        // 区切り文字が初期値以外の場合、例外をスローする。
        if (fileFormat.delimiter() != DELIMITER) {
            throw new FileException("Delimiter can not change.",
                    new IllegalStateException(), fileName);
        }

        // 囲み文字が初期値以外の場合、例外をスローする。
        if (fileFormat.encloseChar() != ENCLOSE_CHAR) {
            throw new FileException("EncloseChar can not change.",
                    new IllegalStateException(), fileName);
        }
    }

    /**
     * 読み込んだ固定長のレコードをアノテーションのbyte数、
     * columnIndex従って分解する。
     * 
     * <p>
     * 処理の順序は、<br>
     * <ul>
     * <li>ファイル行オブジェクトのアノテーションで定義したcolumnIndexの順に並び替える</li>
     * <li>ファイル行オブジェクトのアノテーションで定義したbytes数分の文字列を生成する</li>
     * <li>2バイト文字を確認する際、bytes数を越えて格納しようとすると例外をスローする</li>
     * <li>columnIndexの抜け(途中の番号がない場合、抜けている場合)は処理を継続するが、
     * columnIndexに重複があった場合は、その時点で例外をスローする</li>
     * </ul>
     * <p>
     * 
     * @param fileLineString
     *            固定長ファイルのの1レコード分の文字列
     * @return データ部１行の文字列を分解した文字配列
     */
    protected String[] separateColumns(String fileLineString) {

        // ファイル行オブジェクトで定義されているカラムのバイト数の合計を格納する。
        int columnBytesTotal = 0;

        if (fileLineString == null || "".equals(fileLineString)) {
            return new String[0];
        }

        Field[] fieldList = new Field[getFields().length];

        List<String> columnList = new ArrayList<String>();

        // ファイル行オブジェクトの属性を,アノテーションで定義したcolumnIndexの
        // 順に並べ替える。重複があった場合はエラーとする。
        for (Field field : getFields()) {
            if (field != null) {
                InputFileColumn inputFileColumn = field
                        .getAnnotation(InputFileColumn.class);
                if (inputFileColumn != null) {
                    if (fieldList[inputFileColumn.columnIndex()] == null) {
                        fieldList[inputFileColumn.columnIndex()] = field;
                    } else {
                        throw new FileException("Column index is repeated.",
                                this.getFileName());
                    }
                }
            }
        }
        StringBuilder columnBuilder = new StringBuilder();
        int fileLineStringIndex = 0;
        for (Field field : fieldList) {
            int columnBytes = 0;
            // columnBuilderのデータをクリア
            columnBuilder.delete(0, columnBuilder.length());
            if (field != null) {
                InputFileColumn inputFileColumn = field
                        .getAnnotation(InputFileColumn.class);
                // カラムのバイト数がアノテーションで設定したバイトすうよりも
                // 少ない間、ループを回す。
                while (columnBytes < inputFileColumn.bytes()) {

                    if (fileLineString.length() <= fileLineStringIndex) {
                        throw new FileException("Data size is different from"
                                + " a set point of a column.",
                                this.getFileName());
                    }

                    // 1行分の文字列から1文字取り出す
                    String columnChar = fileLineString.substring(
                            fileLineStringIndex, fileLineStringIndex + 1);
                    try {
                        // 今までに読み取っているカラムのバイト数と、読み取った
                        // 文字のバイト数がアノテーションよりも小さいことを確認する。
                        if ((columnChar.getBytes(getFileEncoding()).length
                                + columnBytes) <= inputFileColumn.bytes()) {
                            // 読み取った文字をcolumnBuilderへ追加。
                            columnBuilder.append(columnChar);
                            // 読み取った文字のバイト数を追加。
                            columnBytes = columnBytes
                                + columnChar.getBytes(getFileEncoding()).length;
                            // 読み取る文字のインデックスを1つずらす。
                            fileLineStringIndex++;
                        } else {
                            throw new FileException("Data size is different "
                                    + "from a set point of a column.",
                                    this.getFileName());
                        }
                    } catch (UnsupportedEncodingException e) {
                        throw new FileException(e, this.getFileName());
                    }
                }
                columnBytesTotal += inputFileColumn.bytes();
                columnList.add(columnBuilder.toString());
            }
        }
        // ファイルから読み取った1行あたりのバイト数と、ファイル行オブジェクトで
        // 定義したバイト数の合計を比較する。
        try {
            if (columnBytesTotal
                    != fileLineString.getBytes(getFileEncoding()).length) {
                throw new FileException("Total Columns byte is not different "
                        + "from Total FileLineObject's columns byte.",
                        new IllegalStateException(), this.getFileName());
            }
        } catch (UnsupportedEncodingException e) {
            throw new FileException(e, this.getFileName());
        }
        return columnList.toArray(new String[columnList.size()]);
    }

    /**
     * 区切り文字を取得する。<br>
     * 固定長ファイルは「,(カンマ)」で固定。
     * 
     * @return 行区切り文字
     */
    @Override
    public char getDelimiter() {

        return DELIMITER;
    }

    /**
     * 囲み文字を取得する。<br>
     * 固定長ファイルは「'\u0000'」で固定。
     * 
     * @return 囲み文字
     */
    @Override
    public char getEncloseChar() {

        return ENCLOSE_CHAR;
    }
}
