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

package jp.terasoluna.fw.web.taglib;

import java.math.BigDecimal;

/**
 * DecimalTagのテストに使用するbean。
 *
 *
 */
public class DecimalTag_BeanStub01 {
    /**
     * String 型のフィールド値。
     */
    private String field1 = null;
    
    /**
     * BigDecimal 型のフィールド値。
     */
    private BigDecimal field2 = null;
    
    /**
     * Integer 型のフィールド値。
     */
    private Integer field3 = null;
    
    /**
     * field1 を取得する。
     * @return field1 を表すフィールド値。
     */
    public String getField1() {
        return field1;
    }
    
    /**
     * field1 を設定する。
     * @param field1 field1 を表すフィールド値。
     */
    public void setField1(String field1) {
        this.field1 = field1;
    }
    
    /**
     * field2 を取得する。
     * @return field2 を表すフィールド値。
     */
    public BigDecimal getField2() {
        return field2;
    }
    
    /**
     * field2 を設定する。
     * @param field2 field2 を表すフィールド値。
     */
    public void setField2(BigDecimal field2) {
        this.field2 = field2;
    }

    /**
     * field3 を取得する。
     * @return field3 を表すフィールド値。
     */
    public Integer getField3() {
        return field3;
    }
    
    /**
     * field3 を設定する。
     * @param field3 field3 を表すフィールド値。
     */
    public void setField3(Integer field3) {
        this.field3 = field3;
    }
}
