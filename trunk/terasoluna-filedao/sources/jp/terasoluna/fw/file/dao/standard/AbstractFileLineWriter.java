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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import jp.terasoluna.fw.file.annotation.FileFormat;
import jp.terasoluna.fw.file.annotation.OutputFileColumn;
import jp.terasoluna.fw.file.annotation.StringConverter;
import jp.terasoluna.fw.file.dao.FileException;
import jp.terasoluna.fw.file.dao.FileLineException;
import jp.terasoluna.fw.file.dao.FileLineWriter;

/**
 * ファイルアクセス(データ書込)用の共通クラス。
 * 
 * <p>
 * ファイルアクセス(データ書込)を行う3つのクラス(CSV、固定長、可変長) に共通する処理をまとめた抽象クラス。
 * ファイルの種類に対応するサブクラスが処理を行う。<br>
 * 使用例は{@link jp.terasoluna.fw.file.dao.FileLineWriter}を参照のこと。
 * </p>
 * <p>
 * ファイル取得処理は下記の手順で呼び出されるように実装すること。
 * <ul>
 * <li>初期化処理(init()、インスタンス生成時必ず１回行なう)</li>
 * <li>ヘッダ部取得(printHeaderLine())</li>
 * <li>データ部取得処理(printDataLine())</li>
 * <li>トレイラ部取得(printTrailerLine())</li>
 * </ul>
 * 上記の順番でのみ正確に出力できる。<br>
 * 
 * </p>
 * 
 * @see jp.terasoluna.fw.file.dao.FileLineWriter
 * @see jp.terasoluna.fw.file.dao.standard.CSVFileLineWriter
 * @see jp.terasoluna.fw.file.dao.standard.FixedFileLineWriter
 * @see jp.terasoluna.fw.file.dao.standard.VariableFileLineWriter
 * @see jp.terasoluna.fw.file.dao.standard.PlainFileLineWriter
 * @param <T> ファイル行オブジェクト。
 */
public abstract class AbstractFileLineWriter<T>
        implements FileLineWriter<T> {

    /**
     * ファイルアクセス（出力）用の文字ストリーム。
     */
    private Writer writer = null;

    /**
     * ファイルアクセスを行うファイル名。
     */
    private String fileName = null;

    /**
     * ファイルエンコーディング。
     */
    private String fileEncoding = System.getProperty("file.encoding");

    /**
     * パラメータクラスのクラス。
     */
    private Class<T> clazz = null;

    /**
     * 行区切り文字。
     */
    private String lineFeedChar = System.getProperty("line.separator");

    /**
     * ファイル行オブジェクトのField情報（Annotation）を格納する変数。
     */
    private Field[] fields = null;

    /**
     * メソッドオブジェクト
     */
    private Method[] methods = null;

    /**
     * カラムフォーマット(ファイル書込）を格納するマップ。
     */
    private Map<String, ColumnFormatter> columnFormatterMap = null;

    /**
     * データ部出力確認用フラグ。
     */
    private boolean writeData = false;

    /**
     * トレイラ部出力確認用フラグ。
     */
    private boolean writeTrailer = false;

    /**
     * 書き込み処理済みデータ部の行数。
     */
    private int currentLineCount = 0;

    /**
     * コンストラクタ。
     * 
     * @param fileName ファイル名
     * @param clazz パラメータクラス
     * @param columnFormatterMap テキスト取得ルール
     */
    public AbstractFileLineWriter(String fileName, Class<T> clazz,
            Map<String, ColumnFormatter> columnFormatterMap) {

        this.fileName = fileName;
        this.clazz = clazz;
        this.columnFormatterMap = columnFormatterMap;
    }

    /**
     * 初期化処理。
     */
    protected void init() {
        // ファイルフォーマットを取得する。
        FileFormat fileFormat = clazz.getAnnotation(FileFormat.class);

        // @FileFormatが無い場合、例外をスローする。
        if (fileFormat == null) {
            throw new FileException("FileFormat annotation is not found.",
                    new IllegalStateException(), fileName);
        }

        // 文字コードを初期化する。
        if (fileFormat.fileEncoding() != null
                && !"".equals(fileFormat.fileEncoding())) {
            this.fileEncoding = fileFormat.fileEncoding();
        }

        // 行区切り文字を初期化する。
        if (fileFormat.lineFeedChar() != null
                && !"".equals(fileFormat.lineFeedChar())) {
            this.lineFeedChar = fileFormat.lineFeedChar();
        }

        // 行区切り文字が3文字以上の場合、例外をスローする。
        if (lineFeedChar.length() > 2) {
            throw new FileException(new IllegalStateException(
                    "lineFeedChar length must be 1 or 2. but: "
                            + lineFeedChar.length()), fileName);
        }

        buildFields();

        // 上書きフラグを確認
        if (fileFormat.overWriteFlg()) {
            File file = new File(fileName);
            file.delete();
        }

        // ファイルオープン
        try {
            writer = new BufferedWriter(new OutputStreamWriter(
                    (new FileOutputStream(fileName, true)), fileEncoding));
        } catch (UnsupportedEncodingException e) {
            throw new FileException(e, fileName);
        } catch (FileNotFoundException e) {
            throw new FileException(e, fileName);
        }
    }

    /**
     * ファイル行オブジェクトの属性のフィールドオブジェクトの配列を生成する。
     */
    private void buildFields() {
        // フィールドオブジェクトを生成
        fields = clazz.getDeclaredFields();

        Field[] fieldArray = new Field[fields.length];

        for (Field field : getFields()) {
            OutputFileColumn outputFileColumn = field
                    .getAnnotation(OutputFileColumn.class);
            if (outputFileColumn != null) {
                if (fieldArray[outputFileColumn.columnIndex()] == null) {
                    fieldArray[outputFileColumn.columnIndex()] = field;
                } else {
                    throw new FileException("Column Index is duplicate : "
                            + outputFileColumn.columnIndex(), fileName);
                }
            }
        }
        buildMethods();
    }

    /**
     * ファイル行オブジェクトの属性のgetterメソッドのメソッドオブジェクトの配列を生成する。
     */
    private void buildMethods() {
        // メソッドオブジェクトを生成
        List<Method> methodList = new ArrayList<Method>();
        List<Field> fieldList = new ArrayList<Field>();
        StringBuilder getterName = new StringBuilder();
        for (Field field : fields) {
            if (field != null
                    && field.getAnnotation(OutputFileColumn.class) != null) {

                // JavaBeanから処理の対象となる属性の属性名を取得する。
                String fieldName = field.getName();

                // 属性名を元に、setterメソッドの名前を生成する。
                getterName.setLength(0);
                getterName.append("get");
                getterName.append(StringUtils.upperCase(fieldName.substring(0,
                        1)));
                getterName.append(fieldName.substring(1, fieldName.length()));

                // setterのリフレクションオブジェクトを取得する。
                // fields[i].getType()で引数の型を指定している。
                try {
                    methodList.add(clazz.getMethod(getterName.toString()));
                    fieldList.add(field);
                } catch (NoSuchMethodException e) {
                    throw new FileException(e, fileName);
                }
            }
        }
        methods = methodList.toArray(new Method[methodList.size()]);
        fields = fieldList.toArray(new Field[fieldList.size()]);
    }

    /**
     * ヘッダ部への書込み処理。
     * 
     * @param headerLine ヘッダ部へ書き込む文字列のリスト
     */
    public void printHeaderLine(List<String> headerLine) {
        if (writeData || writeTrailer) {
            throw new FileException("Header part should be called before "
                    + "data part or trailer part.",
                    new IllegalStateException(), fileName);
        }
        printList(headerLine);
    }

    /**
     * データ部への書き込み処理。
     * 
     * @param t データ部へ書き込むファイル行オブジェクト
     */
    public void printDataLine(T t) {
        checkWriteTrailer();
        // ファイル書き込みの初期化
        StringBuilder fileLineBuilder = new StringBuilder();
        
        // 固定長ファイルの場合
        // (区切り文字、囲み文字がない場合は固定長ファイルと判断する。)
        if (getDelimiter() == Character.MIN_VALUE
                && getEncloseChar() == Character.MIN_VALUE) {
            for (int i = 0; i < getFields().length; i++) {
                fileLineBuilder.append(getColumn(t, i));
            }
        } else {
            for (int i = 0; i < getFields().length; i++) {
                // 囲み文字、区切り文字の追加処理。
                if (getEncloseChar() != Character.MIN_VALUE) {
                    fileLineBuilder.append(getEncloseChar());
                    fileLineBuilder.append(getColumn(t, i));
                    fileLineBuilder.append(getEncloseChar());
                } else {
                    fileLineBuilder.append(getColumn(t, i));
                }
                fileLineBuilder.append(getDelimiter());
            }
            // 一番最後の区切り文字を削除する。
            if (fileLineBuilder.length() > 0) {
                fileLineBuilder.deleteCharAt(fileLineBuilder.length() - 1);
            }
        }

        // 行区切り文字を追加する。
        fileLineBuilder.append(getLineFeedChar());

        // ファイルへの書き込み処理。
        try {
            getWriter().write(fileLineBuilder.toString());
        } catch (IOException e) {
            throw new FileException(e, fileName);
        }
        currentLineCount++;
        setWriteData(true);
    }

    /**
     * トレイラ部への書込み処理。
     * 
     * @param trailerLine トレイラ部へ書き込む文字列のリスト
     */
    public void printTrailerLine(List<String> trailerLine) {
        printList(trailerLine);
        writeTrailer = true;
    }

    /**
     * ヘッダ部、トレイラ部の書き込み用の共通メソッド。
     * 
     * @param stringList 文字列のリスト
     */
    private void printList(List<String> stringList) {
        for (String header : stringList) {
            try {
                writer.write(header);
                writer.write(lineFeedChar);
            } catch (IOException e) {
                throw new FileException(e, fileName);
            }
        }
    }

    /**
     * ファイルクローズ処理。
     */
    public void closeFile() {
        try {
            writer.flush();
            writer.close();
        } catch (IOException e) {
            throw new FileException(e, fileName);
        }
    }

    /**
     * <p>
     * ファイル行オブジェクトからカラムインデックスと一致する属性の値を取得する。
     * </p>
     * 
     * <p>
     * 属性を取得する際、ファイル行オブジェクトのアノテーションの記述により<br>
     * <li>パディング<br>
     * <li>トリム処理<br>
     * <li>文字変換処理を行う。<br>
     * <br>
     * ファイル行オブジェクトのアノテーションでカラムのバイト長が指定されている場合、<br>
     * 返却する文字列がバイト長と一致しているか確認する。
     * </p>
     * 
     * @param t ファイル行オブジェクト
     * @param index カラムのインデックス
     * @return カラムの文字列
     */
    protected String getColumn(T t, int index) {
        // ファイルに書き込むカラムの文字列。
        String columnString = null;

        OutputFileColumn outputFileColumn = fields[index]
                .getAnnotation(OutputFileColumn.class);

        // ファイル行オブジェクト(t)からカラムインデックスと一致する属性の値を取得する。
        ColumnFormatter columnFormatter = columnFormatterMap.get(methods[index]
                .getReturnType().getName());
        try {
            columnString = columnFormatter.format(t, methods[index],
                    outputFileColumn.columnFormat());
        } catch (IllegalArgumentException e) {
            throw new FileLineException(e, fileName, currentLineCount + 1,
                    fields[index].getName(),
                    outputFileColumn.columnIndex());
        } catch (IllegalAccessException e) {
            throw new FileLineException(e, fileName, currentLineCount + 1,
                    fields[index].getName(),
                    outputFileColumn.columnIndex());
        } catch (InvocationTargetException e) {
            throw new FileLineException(e, fileName, currentLineCount + 1,
                    fields[index].getName(),
                    outputFileColumn.columnIndex());
        }

        if (columnString == null) {
            columnString = "";
        }

        // トリム処理
        columnString = FileDAOUtility.trim(columnString, fileEncoding,
                outputFileColumn.trimChar(), outputFileColumn.trimType());

        // パディング処理
        columnString = FileDAOUtility.padding(columnString, fileEncoding,
                outputFileColumn.bytes(), outputFileColumn.paddingChar(),
                outputFileColumn.paddingType());

        // 大文字変換、小文字変換。
        // outputFileColumn.textTransform()の内容により処理を振り分ける。
        try {
            StringConverter stringConverter = outputFileColumn
                    .stringConverter().newInstance();
            columnString = stringConverter.convert(columnString);
        } catch (InstantiationException e) {
            throw new FileLineException(e, fileName, currentLineCount + 1, 
                    fields[index].getName(),
                    outputFileColumn.columnIndex());
        } catch (IllegalAccessException e) {
            throw new FileLineException(e, fileName, currentLineCount + 1, 
                    fields[index].getName(),
                    outputFileColumn.columnIndex());
        }

        // カラムのバイト数チェック。
        if (isCheckByte(outputFileColumn)) {
            try {
                //固定長出力時、Bytes値の設定が時の例外
                if (0 >= outputFileColumn.bytes()) {
                    throw new FileLineException("bytes is not set "
                            + "or a number equal to or less than 0 is set.",
                            new IllegalStateException(), getFileName(),
                            currentLineCount + 1, fields[index].getName(),
                            outputFileColumn.columnIndex());
                }
                //設定されたBytes値とデータのサイズが違う場合は例外発生
                if (columnString.getBytes(fileEncoding).length
                        != outputFileColumn.bytes()) {
                    throw new FileLineException(
                            "The data size is different from bytes value of "
                            + "the set value of the column .",
                            new IllegalStateException(), fileName,
                            currentLineCount + 1, fields[index].getName(),
                            outputFileColumn.columnIndex());
                }
            } catch (UnsupportedEncodingException e) {
                throw new FileException(e, fileName);
            }
        }
        return columnString;
    }
    
    /**
     * ファイル名を取得する。
     * 
     * @return fileName ファイル名
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * 行区切り文字を設定する。
     * 
     * @return lineFeedChar 行区切り文字
     */
    protected String getLineFeedChar() {
        return lineFeedChar;
    }

    /**
     * カラムフォーマット(ファイル書込）処理を格納するマップを取得する。
     * 
     * @param columnFormatterMap
     *            カラムフォーマット(ファイル書込）を格納するマップ
     */
    public void setColumnFormatterMap(
            Map<String, ColumnFormatter> columnFormatterMap) {
        this.columnFormatterMap = columnFormatterMap;
    }

    /**
     * ファイルアクセス（出力）用の文字ストリームを取得する。
     * 
     * @return bufferedWriter ファイルアクセス（出力）用の文字ストリーム
     */
    protected Writer getWriter() {
        return writer;
    }

    /**
     * ファイル行オブジェクトのField情報（Annotation）を格納する変数を取得する。
     * 
     * @return fields ファイル行オブジェクトのField情報（Annotation）を格納する変数
     */
    protected Field[] getFields() {
        return fields;
    }

    /**
     * ファイル行オブジェクトのField情報に対応するgetterメソッドを格納する変数を取得する。
     * 
     * @return methods ファイル行オブジェクトのField情報に対応するgetterメソッドを格納する変数
     */
    protected Method[] getMethods() {
        return methods;
    }

    /**
     * データ部の出力が開始されているかどうかを判定するフラグ。
     * 
     * @param writeData フラグ
     */
    protected void setWriteData(boolean writeData) {
        this.writeData = writeData;
    }

    /**
     * トレイラ部の処理が終わっているかどうかを判定する。<br>
     * 処理が完了している場合、例外をスローする。
     */
    protected void checkWriteTrailer() {
        if (writeTrailer) {
            throw new FileException("Header part or data part should be "
                    + "called before TrailerPart",
                    new IllegalStateException(), fileName);
        }
    }

    /**
     * 区切り文字を取得する。
     * 
     * @return 区切り文字
     */
    public abstract char getDelimiter();

    /**
     * 囲み文字を取得する。
     * 
     * @return 囲み文字
     */
    public abstract char getEncloseChar();


    /**
     * バイト数チェックを行うかどうか。
     * @param outputFileColumn 出力カラム
     * @return バイト数が設定されている(1バイト以上)場合はtrue。
     */
    protected boolean isCheckByte(OutputFileColumn outputFileColumn) {

        if (0 < outputFileColumn.bytes()) {
            return true;
        }

        return false;
    }
}
