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

package jp.terasoluna.fw.web.struts.form;

import java.util.ArrayList;
import java.util.List;

/**
 * FieldChecksExのテストで使用するValidatorActionFormExの実装クラス。
 *
 */
@SuppressWarnings("serial")
public class FieldChecksEx_ValidatorActionFormExStub01 extends
        ValidatorActionFormEx {

    /**
     * テストで使用するプロパティ。
     */
    public String testArray = null;

    /**
     * テストで使用するプロパティ。
     */
    public ArrayList codeArray = null;

    /**
     * テストで使用するプロパティ。
     */
    public String[] stringArray = null;

    /**
     * @return stringArray を戻します。
     */
    public String[] getStringArray() {
        return stringArray;
    }

    /**
     * @param stringArray 設定する stringArray。
     */
    public void setStringArray(String[] stringArray) {
        this.stringArray = stringArray;
    }

    /**
     * @return codeArray を戻します。
     */
    public ArrayList getCodeArray() {
        return codeArray;
    }

    /**
     * @param codeArray 設定する codeArray。
     */
    public void setCodeArray(ArrayList codeArray) {
        this.codeArray = codeArray;
    }

    /**
     * @return testArray を戻します。
     */
    public String getTestArray() {
        return testArray;
    }

    /**
     * @param testArray 設定する testArray。
     */
    public void setTestArray(String testArray) {
        this.testArray = testArray;
    }

    /**
     * JavaBeanのフィールド。
     */
    FieldChecksEx_JavaBeanStub01 row = null;

    /**
     * rowを返却する。
     * @return row
     */
    public FieldChecksEx_JavaBeanStub01 getRow() {
        return row;
    }

    /**
     * rowを設定する。
     * @param row row
     */
    public void setRow(FieldChecksEx_JavaBeanStub01 row) {
        this.row = row;
    }

    /**
     * String型のフィールド。
     */
    String value = null;

    /**
     * valueを返却する。
     * @return value
     */
    public String getValue() {
        return value;
    }

    /**
     * valueを設定する。
     * @param value value
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * JavaBean[]型のフィールド。
     */
    FieldChecksEx_JavaBeanStub01[] rows = null;

    /**
     * rowsを返却する。
     * @return rows
     */
    public FieldChecksEx_JavaBeanStub01[] getRows() {
        return rows;
    }

    /**
     * rowsを設定する。
     * @param rows rows
     */
    public void setRows(FieldChecksEx_JavaBeanStub01[] rows) {
        this.rows = rows;
    }

    /**
     * List<JavaBean>型のフィールド。
     */
    List<FieldChecksEx_JavaBeanStub01> rowList = null;

    /**
     * rowListを返却する。
     * @return rowList
     */
    public List<FieldChecksEx_JavaBeanStub01> getRowList() {
        return rowList;
    }

    /**
     * rowListを設定する。
     * @param rowList rowList
     */
    public void setRowList(List<FieldChecksEx_JavaBeanStub01> rowList) {
        this.rowList = rowList;
    }

}
