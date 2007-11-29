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

package jp.terasoluna.fw.web.struts.reset;

import java.util.List;
import java.util.Map;

import jp.terasoluna.fw.web.struts.form.ValidatorActionFormEx;

/**
 * ResetterImplTestで使用するValidatorActionFormExのスタブクラス。
 *
 */
@SuppressWarnings("serial")
public class ResetterImpl_ValidatorActionFormExStub01 extends
        ValidatorActionFormEx {

    /**
     * String[]型のフィールド
     */
    String[] values = null;

    /**
     * valuesを取得する。
     *
     * @return values
     */
    public String[] getValues() {
        return values;
    }

    /**
     * valuesを設定する。
     *
     * @param values
     *            String[]
     */
    public void setValues(String[] values) {
        this.values = values;
    }

    /**
     * String型のフィールド。
     */
    String value = null;

    /**
     * valueを返却する。
     *
     * @return value
     */
    public String getValue() {
        return value;
    }

    /**
     * valueを設定する。
     *
     * @param value
     *            value
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * List型のフィールド。
     */
    List valueList = null;

    /**
     * valueListを返却する。
     *
     * @return valueList
     */
    public List getValueList() {
        return valueList;
    }

    /**
     * valueListを設定する。
     *
     * @param valueList
     *            valueList
     */
    public void setValueList(List valueList) {
        this.valueList = valueList;
    }

    /**
     * Map型のフィールド。
     */
    Map map = null;

    /**
     * mapを返却する。
     * @return map
     */
    public Map getMap() {
        return map;
    }

    /**
     * mapを設定する。
     * @param map map
     */
    public void setMap(Map map) {
        this.map = map;
    }

    /**
     * JavaBean(POJO)のフィールド。
     */
    ResetterImpl_JavaBeanStub01 row = null;

    /**
     * rowを返却する。
     * @return row
     */
    public ResetterImpl_JavaBeanStub01 getRow() {
        return row;
    }

    /**
     * rowを設定する。
     * @param row row
     */
    public void setRow(ResetterImpl_JavaBeanStub01 row) {
        this.row = row;
    }

    /**
     * JavaBean(POJO)の配列型フィールド。
     */
    ResetterImpl_JavaBeanStub01[] rows = null;

    /**
     * rowsを返却する。
     * @return rows
     */
    public ResetterImpl_JavaBeanStub01[] getRows() {
        return rows;
    }

    /**
     * rowsを設定する。
     * @param rows rows
     */
    public void setRows(ResetterImpl_JavaBeanStub01[] rows) {
        this.rows = rows;
    }

    /**
     * JavaBean(POJO)のList型フィールド。
     */
    List<ResetterImpl_JavaBeanStub01> rowList = null;

    /**
     * rowListを返却する。
     * @return rowList
     */
    public List<ResetterImpl_JavaBeanStub01> getRowList() {
        return rowList;
    }

    /**
     * rowListを設定する。
     * @param rowList rowList
     */
    public void setRowList(List<ResetterImpl_JavaBeanStub01> rowList) {
        this.rowList = rowList;
    }

    /**
     * Boolean型のフィールド。
     */
    Boolean booleanValue1 = null;

    /**
     * booleanValue1を返却する。
     * @return booleanValue1
     */
    public Boolean getBooleanValue1() {
        return booleanValue1;
    }

    /**
     * booleanValue1を設定する。
     * @param booleanValue1 booleanValue1
     */
    public void setBooleanValue1(Boolean booleanValue1) {
        this.booleanValue1 = booleanValue1;
    }

    /**
     * Byte[]型のフィールド。
     */
    Byte[] byteArray1 = null;

    /**
     * byteArray1を返却する。
     * @return byteArray1
     */
    public Byte[] getByteArray1() {
        return byteArray1;
    }

    /**
     * byteArray1を設定する。
     * @param byteArray1 byteArray1
     */
    public void setByteArray1(Byte[] byteArray1) {
        this.byteArray1 = byteArray1;
    }

    /**
     * List<Character>型のフィールド。
     */
    List<Character> charList1 = null;

    /**
     * charList1を返却する。
     * @return charList1
     */
    public List<Character> getCharList1() {
        return charList1;
    }

    /**
     * charList1を設定する。
     * @param charList1 charList1
     */
    public void setCharList1(List<Character> charList1) {
        this.charList1 = charList1;
    }
}
