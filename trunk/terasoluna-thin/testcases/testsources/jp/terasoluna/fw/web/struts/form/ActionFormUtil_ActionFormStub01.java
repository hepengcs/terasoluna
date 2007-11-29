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


/**
 * ActionFormUtilTestクラスで利用する。
 *
 * @version 2005/10/28
 */
@SuppressWarnings("unused")
public class ActionFormUtil_ActionFormStub01
        extends ValidatorActionFormEx {

    /**
	 * シリアルバージョンID
	 */
	private static final long serialVersionUID = -1064691779244028892L;

	/**
     * field。
     */
    private String field = null;

    /**
     * booleanField。
     */
    private boolean booleanField = false;


    /**
     * hogeBoolean
     */
    private boolean hogeBoolean = false;

    /**
     * hogeByte
     */
    private byte hogeByte = 0;

    /**
     * hogeChar
     */
    private char hogeChar = 'A';

    /**
     * hogeDouble
     */
    private double hogeDouble = 0;

    /**
     * hogeFloat
     */
    private float hogeFloat = 0;

    /**
     * hogeLong
     */
    private long hogeLong = 0;

    /**
     * hogeShort
     */
    private short hogeShort = 0;

    /**
     * hoge
     */
    private String hoge = null;

    /**
     * hogeInt
     */
    private int hogeInt = 0;

    /**
     * hogeArray
     */
    private String[] hogeArray = new String[0];

    /**
     * hogeIntArray
     */
    private int[] hogeIntArray = new int[0];

    /**
     * hogeFinal
     */
    private final String hogeFinal = "world";


    /**
     * 会社ID
     */
    private String companyId = null;

    /**
     * 会社IDを設定する。
     *
     * @param companyId
     */
    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    /**
     * 会社IDを取得する。
     *
     * @return 会社ID
     */
    public String getCompanyId() {
        return companyId;
    }

    /**
     * fieldを取得する。
     * @return field
     */
    public String getField() {
        return field;
    }

    /**
     * fieldを設定する。
     * @param field field
     */
    public void setField(String field) {
        this.field = field;
    }

    /**
     * booleanField を戻します。
     * @return booleanField を戻します。
     */
    public boolean isBooleanField() {
        return booleanField;
    }

    /**
     * booleanFieldを設定する。
     * @param booleanField 設定する booleanField。
     */
    public void setBooleanField(boolean booleanField) {
        this.booleanField = booleanField;
    }

}
