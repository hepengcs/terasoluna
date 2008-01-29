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

package jp.terasoluna.fw.file.dao;

/**
 * ファイルアクセス機能で発生した例外をラップするクラス。
 * <p>
 * ファイルから行のデータを読み取る際に発生した例外をラップするクラス。
 * </p>
 */
public class FileLineException extends FileException {

    /**
     * シリアルバージョンUID。
     */
    private static final long serialVersionUID = 3669406429664627698L;

    /**
     * エラーが発生したカラム名。
     */
    private final String columnName;

    /**
     * エラーが発生したカラム番号。
     */
    private final int columnIndex;

    /**
     * エラーが発生した行の行番号。
     */
    private final int lineNo;
    
    /**
     * コンストラクタ。
     * @param e 原因例外
     */
    public FileLineException(Exception e) {
        super(e);
        columnName = null;
        columnIndex = -1;
        lineNo = -1;
    }
    
    /**
     * コンストラクタ。
     * @param message メッセージ
     */
    public FileLineException(String message) {
        super(message);
        columnName = null;
        columnIndex = -1;
        lineNo = -1;
    }
    
    /**
     * コンストラクタ。
     * @param message メッセージ
     * @param e 原因例外
     */
    public FileLineException(String message, Exception e) {
        super(message, e);
        columnName = null;
        columnIndex = -1;
        lineNo = -1;
    }
    
    /**
     * コンストラクタ。
     * @param e 原因例外
     * @param fileName ファイル名
     * @param lineNo エラーが発生した行の行番号
     */
    public FileLineException(Exception e, String fileName, int lineNo) {
        super(e, fileName);
        this.lineNo = lineNo;
        columnName = null;
        columnIndex = -1;
    }

    /**
     * コンストラクタ。
     * @param e 原因例外
     * @param fileName ファイル名
     * @param lineNo エラーが発生した行の行番号
     * @param columnName カラム名
     * @param columnIndex エラーが発生したカラム番号
     */
    public FileLineException(Exception e, String fileName, int lineNo, String columnName, int columnIndex) {
        super(e, fileName);
        this.columnName = columnName;
        this.columnIndex = columnIndex;
        this.lineNo = lineNo;
    }
    
    /**
     * コンストラクタ。
     * @param message メッセージ
     * @param e 原因例外
     * @param fileName ファイル名
     * @param lineNo エラーが発生した行の行番号
     * @param columnName カラム名
     * @param columnIndex エラーが発生したカラム番号
     */
    public FileLineException(String message, Exception e, String fileName, int lineNo, String columnName, int columnIndex) {
        super(message, e, fileName);
        this.columnName = columnName;
        this.columnIndex = columnIndex;
        this.lineNo = lineNo;
    }


    /**
     * カラム名を取得する。
     * @return カラム名
     */
    public String getColumnName() {
        return this.columnName;
    }

    /**
     * エラーが発生した行の行番号を取得する。
     * @return エラーが発生した行の行番号
     */
    public int getLineNo() {
        return this.lineNo;
    }

    /**
     * エラーが発生したカラムのカラム番号を取得する。
     * @return エラーが発生したカラムのカラム番号（0から開始）
     */
    public int getColumnIndex() {
        return columnIndex;
    }
    
    

}
