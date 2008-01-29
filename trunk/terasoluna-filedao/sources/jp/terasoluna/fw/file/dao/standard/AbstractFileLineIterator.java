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

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

import jp.terasoluna.fw.file.annotation.FileFormat;
import jp.terasoluna.fw.file.annotation.InputFileColumn;
import jp.terasoluna.fw.file.annotation.StringConverter;
import jp.terasoluna.fw.file.dao.FileException;
import jp.terasoluna.fw.file.dao.FileLineException;
import jp.terasoluna.fw.file.dao.FileLineIterator;

import org.apache.commons.lang.StringUtils;

/**
 * ファイルアクセス(データ取得)用の共通クラス。
 * 
 * <p>
 * ファイルアクセス(データ取得)を行う3つのクラス(CSV、固定長、可変長) に共通する処理をまとめた抽象クラス。
 * ファイルの種類に対応するサブクラスが処理を行う。<br>
 * 使用例は{@link jp.terasoluna.fw.file.dao.FileLineIterator}を参照のこと。
 * </p>
 * 
 * ファイル取得処理は下記の手順で呼び出されるように実装すること。<br>
 * <ul>
 * <li>ヘッダ部取得(getHeader())</li>
 * <li>スキップ処理(skip())</li>
 * <li>データ部取得処理(readLine())</li>
 * <li>トレイラ部取得(getTrailer())</li>
 * </ul>
 * 上記の順番でのみ正確にデータ取得できる。上記の順位外で処理を実行すると<code>IllegalStateException<code>が発生する。<br>
 * ジョブ再実行までにファイルを更新するとリスタート機能が正常に動作しない。
 * 
 * @see jp.terasoluna.fw.file.dao.FileLineIterator
 * @see jp.terasoluna.fw.file.dao.standard.CSVFileLineIterator
 * @see jp.terasoluna.fw.file.dao.standard.FixedFileLineIterator
 * @see jp.terasoluna.fw.file.dao.standard.VariableFileLineIterator
 * @see jp.terasoluna.fw.file.dao.standard.PlainFileLineIterator
 * 
 * @param <T> ファイル行オブジェクト。
 */
public abstract class AbstractFileLineIterator<T> implements
        FileLineIterator<T> {

    /**
     * ファイル名。
     */
    private String fileName = null;

    /**
     * 結果クラス。
     */
    private Class<T> clazz = null;

    /**
     * 行区切り文字。
     */
    private String lineFeedChar = System.getProperty("line.separator");

    /**
     * ファイルエンコーディング。
     */
    private String fileEncoding = System.getProperty("file.encoding");

    /**
     * ヘッダ行数。
     */
    private int headerLineCount = 0;

    /**
     * トレイラ行数。
     */
    private int trailerLineCount = 0;

    /**
     * 現在のデータ部の1行分の文字列。
     */
    private String currentLineString = null;

    /**
     * ファイル入力処理済みのデータ部の行数。
     */
    private int currentLineCount = 0;

    /**
     * ファイルアクセス用の文字ストリーム。
     */
    private Reader reader = null;

    /**
     * ファイル行オブジェクトのField情報（Annotation）を格納する変数。
     */
    private Field[] fields = null;

    /**
     * ファイル行オブジェクトのFieldに対応するsetterメソッドを格納する。
     */
    private Method[] methods = null;

    /**
     * カラムパーザーを格納するマップ。
     */
    private Map<String, ColumnParser> columnParserMap = null;

    /**
     * ヘッダ部の文字列リスト。
     */
    private List<String> header = new ArrayList<String>();

    /**
     * トレイラ部の文字列リスト。
     */
    private List<String> trailer = new ArrayList<String>();

    /**
     * ヘッダ部処理確認用フラグ。
     */
    private boolean readHeader = false;

    /**
     * データ部処理確認用フラグ。
     */
    private boolean readData = false;

    /**
     * トレイラ部処理確認用フラグ。
     */
    private boolean readTrailer = false;

    /**
     * トレイラ部の一時格納用のキュー。
     */
    private Queue<String> trailerQueue = null;

    /**
     * 1行分の文字列を読み込むオブジェクト
     */
    private LineReader lineReader = null;

    /**
     * ファイル行オブジェクトで定義されているカラム数（@InputFileColumnが付いている属性の数）を格納する。
     */
    private int columnCount = 0;

    /**
     * コンストラクタ。
     * 
     * @param fileName ファイル名
     * @param clazz 結果クラス
     * @param columnParserMap フォーマット処理リスト
     */
    public AbstractFileLineIterator(String fileName, Class<T> clazz,
            Map<String, ColumnParser> columnParserMap) {
        if (fileName == null || "".equals(fileName)) {
            throw new FileException("File is not found.", 
                    new IllegalStateException(), fileName);
        }
        FileFormat fileFormat = clazz.getAnnotation(FileFormat.class);
        if (fileFormat == null) {
            throw new FileException("FileFormat annotation is not found.", 
                    new IllegalStateException(), fileName);
        }
        this.fileName = fileName;
        this.clazz = clazz;
        if (fileFormat.lineFeedChar() != null
                && !"".equals(fileFormat.lineFeedChar())) {
            this.lineFeedChar = fileFormat.lineFeedChar();
        }
        if (fileFormat.fileEncoding() != null
                && !"".equals(fileFormat.fileEncoding())) {
            this.fileEncoding = fileFormat.fileEncoding();
        }
        this.headerLineCount = fileFormat.headerLineCount();
        this.trailerLineCount = fileFormat.trailerLineCount();
        this.columnParserMap = columnParserMap;
    }

    /**
     * 次の行のレコードがあるかどうか確認するためのメソッド。<br>
     * 繰り返し処理でさらに要素がある場合に true を返します。
     * 
     * @return 繰り返し処理でさらに要素がある場合に <code>true</code>
     */
    public boolean hasNext() {
        try {
            if (reader.ready()) {
                return true;
            }
        } catch (IOException e) {
            throw new FileException(e, fileName);
        }
        return false;
    }

    /**
     * 繰り返し処理でファイル行オブジェクトを返却する。
     * 
     * <p>
     * 次の行のレコードの情報をファイル行オブジェクトに格納して返却します。<br>
     * 繰り返し処理で次の要素を返します。
     * </p>
     * 
     * @return ファイル行オブジェクト
     */
    public T next() {
        if (readTrailer) {
            throw new FileLineException(
                    "Data part should be called before trailer part.", 
                    new IllegalStateException(),
                    fileName, currentLineCount, null, -1);
        }
        if (!hasNext()) {
            throw new FileLineException(new NoSuchElementException(), 
                fileName, currentLineCount, null, -1);
        }

        T fileLineObject = null;

        String currentString = readLine();

        // ファイル行オブジェクトを新たに生成する処理。
        try {
            fileLineObject = clazz.newInstance();
        } catch (InstantiationException e) {
            throw new FileLineException(e, fileName, currentLineCount + 1);
        } catch (IllegalAccessException e) {
            throw new FileLineException(e, fileName, currentLineCount + 1);
        }

        // CSVの区切り文字にしたがって入力データを分解する。
        // 区切り文字はアノテーションから取得する。
        String[] columns = separateColumns(currentString);

        // ファイルから読み取ったカラム数とファイル行オブジェクトのカラム数を比較する。
        if (columnCount != columns.length) {
            throw new FileLineException("Column Count is not different from "
                    + "FileLineObject's column counts",
                    new IllegalStateException(),
                    fileName,
                    currentLineCount,
                    null,
                    -1);
        }

        for (int i = 0; i < fields.length; i++) {
            // JavaBeanの入力用のアノテーションを設定する。
            InputFileColumn inputFileColumn = null;

            if (fields[i] != null) {
                inputFileColumn = fields[i]
                        .getAnnotation(InputFileColumn.class);
            }

            // ファイル行オブジェクトのアノテーションがnullでなければ処理を継続。
            if (inputFileColumn != null) {

                // 1カラムの文字列をセットする。
                String columnString = columns[inputFileColumn.columnIndex()];

                // 文字サイズの確認。
                if (0 < inputFileColumn.bytes()) {
                    try {
                        if (columnString.getBytes(fileEncoding).length
                                != inputFileColumn.bytes()) {
                            throw new FileLineException(
                                    "Data size is different from a set point "
                                    + "of a column.", 
                                    new IllegalStateException(),
                                    fileName, currentLineCount + 1,
                                    fields[i].getName(),
                                    inputFileColumn.columnIndex());
                        }
                    } catch (UnsupportedEncodingException e) {
                        throw new FileException(e, fileName);
                    }
                }

                // トリム処理
                columnString = FileDAOUtility.trim(columnString, fileEncoding,
                        inputFileColumn.trimChar(), inputFileColumn.trimType());

                // パディング処理
                columnString = FileDAOUtility.padding(columnString,
                        fileEncoding, inputFileColumn.bytes(), inputFileColumn
                                .paddingChar(), inputFileColumn.paddingType());

                // 大文字変換、小文字変換。
                // inputFileColumn.stringConverter()の内容により処理を振り分ける。
                try {
                    StringConverter stringConverter = inputFileColumn
                            .stringConverter().newInstance();
                    columnString = stringConverter.convert(columnString);
                } catch (InstantiationException e) {
                    throw new FileLineException(e, fileName,  
                            currentLineCount + 1, fields[i].getName(),
                            inputFileColumn.columnIndex());
                } catch (IllegalAccessException e) {
                    throw new FileLineException(e, fileName,  
                            currentLineCount + 1, fields[i].getName(),
                            inputFileColumn.columnIndex());
                }

                // 値を格納する処理。
                // JavaBeanの属性の型の名前によって処理を振り分ける。
                ColumnParser textSetter = columnParserMap.get(fields[i]
                        .getType().getName());
                try {
                    textSetter.parse(columnString, fileLineObject, methods[i],
                            inputFileColumn.columnFormat());
                } catch (IllegalArgumentException e) {
                    throw new FileLineException(e, fileName,  
                            currentLineCount + 1, fields[i].getName(),
                            inputFileColumn.columnIndex());
                } catch (IllegalAccessException e) {
                    throw new FileLineException(e, fileName,  
                            currentLineCount + 1, fields[i].getName(),
                            inputFileColumn.columnIndex());
                } catch (InvocationTargetException e) {
                    throw new FileLineException(e, fileName,  
                            currentLineCount + 1, fields[i].getName(),
                            inputFileColumn.columnIndex());
                } catch (ParseException e) {
                    throw new FileLineException(e, fileName,  
                            currentLineCount + 1, fields[i].getName(),
                            inputFileColumn.columnIndex());
                }

            }
        }
        readData = true;
        currentLineCount++;
        return fileLineObject;
    }

    /**
     * Iteratorで定義されているメソッド。
     * FileQueryDAOでは実装しないので、他のクラスから呼び出した場合、
     * UnsupportedOperationExceptionをスローする。
     */
    public void remove() {
        throw new UnsupportedOperationException();
    }

    /**
     * 初期化処理。 <br>
     * 初期化処理で行うのは以下の３つ。
     * <ul>
     * <li>ファイル行オブジェクトの属性(Field)の取得</li>
     * <li>トレイラキューの初期化</li>
     * <li>ファイルオープン処理</li>
     * </ul>
     */
    protected void init() {
        try {
            this.reader = new BufferedReader(new InputStreamReader(
                    (new FileInputStream(fileName)), fileEncoding));
        } catch (UnsupportedEncodingException e) {
            throw new FileException(e, fileName);
        } catch (FileNotFoundException e) {
            throw new FileException(e, fileName);
        }

        if (1 <= trailerLineCount) {
            trailerQueue = new ArrayBlockingQueue<String>(trailerLineCount);
        }

        buildFields();
        buildMethods();

        // ファイルから1文字ずつ読んで、行区切り文字と一致したら1行とカウントする。
        if (getEncloseChar() == Character.MIN_VALUE) {
            // 囲み文字無し、行区切り文字が2文字
            if (lineFeedChar.length() == 2) {
                lineReader = new LineFeed2LineReader(reader, lineFeedChar);
            } else if (lineFeedChar.length() == 1) {
                // 囲み文字無し、行区切り文字が1文字
                lineReader = new LineFeed1LineReader(reader, lineFeedChar);
            } else {
                throw new FileException(
                        "lineFeedChar length must be 1 or 2. but: "
                        + lineFeedChar.length(), new IllegalStateException(),
                        fileName);
            }
        } else {
            // 囲み文字あり、行区切り文字が1文字
            if (lineFeedChar.length() == 1) {
                lineReader = new EncloseCharLineFeed1LineReader(getDelimiter(),
                       getEncloseChar(), reader, lineFeedChar);
                    // 囲み文字あり、行区切り文字2文字
            } else if (lineFeedChar.length() == 2) {
                lineReader = new EncloseCharLineFeed2LineReader(getDelimiter(),
                        getEncloseChar(), reader, lineFeedChar);
            } else {
                throw new FileException(
                        "lineFeedChar length must be 1 or 2. but: "
                        + lineFeedChar.length(), new IllegalStateException(),
                        fileName);
            }
        }
    }

    /**
     * ファイル行オブジェクトの属性のフィールドオブジェクトの配列を生成する。<br>
     */
    private void buildFields() {
        // フィールドオブジェクトを生成
        fields = clazz.getDeclaredFields();

        Field[] fieldArray = new Field[fields.length];

        for (Field field : getFields()) {
            InputFileColumn inputFileColumn = field
                    .getAnnotation(InputFileColumn.class);
            if (inputFileColumn != null) {
                if (fieldArray[inputFileColumn.columnIndex()] == null) {
                    fieldArray[inputFileColumn.columnIndex()] = field;
                } else {
                    throw new FileException("Column Index: "
                            + inputFileColumn.columnIndex()
                            + " is duplicated.", fileName);
                }
                columnCount++;
            }
        }
    }

    /**
     * ファイル行オブジェクトの属性のsetterメソッドのメソッドオブジェクトの配列を生成する。
     */
    private void buildMethods() {
        List<Method> methodList = new ArrayList<Method>();
        StringBuilder setterName = new StringBuilder();

        for (Field field : fields) {
            if (field.getAnnotation(InputFileColumn.class) != null) {
                // JavaBeanから処理の対象となる属性の属性名を取得する。
                String fieldName = field.getName();

                // 属性名を元に、setterメソッドの名前を生成する。
                setterName.setLength(0);
                setterName.append("set");
                setterName.append(StringUtils.upperCase(fieldName.substring(0,
                        1)));
                setterName.append(fieldName.substring(1, fieldName.length()));

                // setterのリフレクションオブジェクトを取得する。
                // fields[i].getType()で引数の型を指定している。
                try {
                    methodList.add(clazz.getMethod(setterName.toString(),
                            new Class[] { field.getType() }));
                } catch (NoSuchMethodException e) {
                    throw new FileException(e, fileName);
                }
            } else {
                methodList.add(null);
            }
        }
        methods = methodList.toArray(new Method[methodList.size()]);
    }

    /**
     * ファイル閉塞処理。
     */
    public void closeFile() {
        try {
            reader.close();
        } catch (IOException e) {
            throw new FileException(e, fileName);
        }
    }

    /**
     * ヘッダ部のデータを取得するメソッド。
     * 
     * @return header ヘッダ部の文字列リスト
     */
    public List<String> getHeader() {
        if (readTrailer || readData) {
            throw new FileException(new IllegalStateException(), fileName);
        }
        if (!readHeader) {
            readHeader = true;
            if (0 < headerLineCount) {
                for (int i = 0; i < headerLineCount; i++) {
                    if (!hasNext()) {
                        throw new FileException(new NoSuchElementException(),
                                fileName);
                    }
                    try {
                        header.add(lineReader.readLine());
                    } catch (FileException e) {
                        throw new FileException(e, fileName);
                    }
                }
            }
        }
        return header;
    }

    /**
     * トレイラ部のデータを取得するメソッド.
     * 
     * @return トレイラ部の文字列リスト
     */
    public List<String> getTrailer() {
        if (0 < trailerLineCount) {
            while (hasNext()) {
                if (!readHeader) {
                    getHeader();
                }
                if (trailerLineCount <= trailerQueue.size()) {
                    currentLineString = trailerQueue.poll();
                }
                try {
                    trailerQueue.add(lineReader.readLine());
                } catch (FileException e) {
                    throw new FileException(e, fileName);
                }
            }
            for (String fileLineBuilder : trailerQueue) {
                trailer.add(fileLineBuilder);
            }
        }
        readTrailer = true;
        return trailer;
    }

    /**
     * ファイルからデータ部のデータを1行分読み取り、文字列として呼出元に返却する.
     * 
     * @return データ部の１行分の文字列
     */
    protected String readLine() {
        if (!hasNext()) {
            throw new FileException(new NoSuchElementException(), fileName);
        }

        if (!readHeader) {
            getHeader();
        }

        if (1 <= trailerLineCount) {
            if (trailerQueue.size() < trailerLineCount) {
                int loopCount = trailerLineCount - trailerQueue.size();
                for (int i = 0; i < loopCount; i++) {
                    if (!hasNext()) {
                        throw new FileException(new NoSuchElementException(),
                                fileName);
                    }
                    try {
                        trailerQueue.add(lineReader.readLine());
                    } catch (FileException e) {
                        throw new FileException(e, fileName);
                    }
                }
                if (!hasNext()) {
                    return null;
                }
            }

            currentLineString = trailerQueue.poll();
            try {
                trailerQueue.add(lineReader.readLine());
            } catch (FileException e) {
                throw new FileException(e, fileName);
            }
        } else {
            try {
                currentLineString = lineReader.readLine();
            } catch (FileException e) {
                throw new FileException(e, fileName);
            }
        }

        return currentLineString;
    }

    /**
     * リスタート時に処理済のデータ部のデータを読み飛ばす処理を実行する。
     * 
     * @param skipLines 読み飛ばす行数。
     */
    public void skip(int skipLines) {
        for (int i = 0; i < skipLines; i++) {
            readLine();
        }
    }

    /**
     * 区切り文字を取得する。
     * 
     * @return 行区切り文字。
     */
    protected abstract char getDelimiter();

    /**
     * 囲み文字を取得する。
     * 
     * @return 囲み文字。
     */
    protected abstract char getEncloseChar();

    /**
     * 文字列分割処理.
     * <p>
     * データ部のデータ１行分をファイル行オブジェクトのアノテーションの記述に
     * 従いカラムに分割する。
     * </p>
     * 
     * @param fileLineString データ部のデータ１行分
     * @return データ部１行の文字列を分解した文字配列
     */
    protected abstract String[] separateColumns(String fileLineString);

    /**
     * 行区切り文字を取得する。
     * 
     * @return 行区切り文字
     */
    protected String getLineFeedChar() {
        return lineFeedChar;
    }

    /**
     * ファイルエンコーディング取得する。
     * 
     * @return ファイルエンコーディング
     */
    protected String getFileEncoding() {
        return fileEncoding;
    }

    /**
     * ヘッダ行数を取得する。
     * 
     * @return ヘッダ行数
     */
    protected int getHeaderLineCount() {
        return headerLineCount;
    }

    /**
     * トレイラ行数を取得する。
     * 
     * @return トレイラ行数
     */
    protected int getTrailerLineCount() {
        return trailerLineCount;
    }

    /**
     * ファイル入力処理済みのデータ部の行数を取得する。
     * 
     * @return ファイル入力処理済みのデータ部の行数。
     */
    public int getCurrentLineCount() {
        return currentLineCount;
    }

    /**
     * ファイル行オブジェクトのField情報（Annotation）を格納する変数を取得する。
     * 
     * @return ファイル行オブジェクトのField情報（Annotation）を格納する変数
     */
    protected Field[] getFields() {
        return fields;
    }

    /**
     * ファイル名を取得する。
     * @return fileName ファイル名
     */
    protected String getFileName() {
        return fileName;
    }
}
