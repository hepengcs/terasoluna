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

import java.util.Date;

/**
 * DateFormatterTagクラスのテストを行う際に使用するbean。
 *
 */
public class DateFormatterTagBase_BeanStub01 {
    
    /**
     * String型のフィールド。
     */
    private String testField = null;

    /**
     * Date型のフィールド。
     */
    private Date dateField = null;
    
    /**
     * Integer型のフィールド。
     */
    private Integer timeField = null;

    /**
     * timeField を取得する。
     * @return timeField を表すフィールド値。
     */
    public Integer getTimeField() {
        return timeField;
    }
    
    /**
     * timeField を設定する。
     * @param timeField timeFieldを表すフィールド値。
     */
    public void setTimeField(Integer timeField) {
        this.timeField = timeField;
    }
    
    /**
     * dateField を取得する。
     * @return dateFieldを表すフィールド値。
     */
    public Date getDateField() {
        return dateField;
    }
    
    /**
     * dateField を設定する。
     * @param dateField を表すフィールド値。
     */
    public void setDateField(Date dateField) {
        this.dateField = dateField;
    }
    
    /**
     * testField を取得する。
     * @return testField を表すフィールド値。
     */
    public String getTestField() {
        return testField;
    }

    /**
     * testField を設定する。
     * @param testField testFieldを表すフィールド値。
     */
    public void setTestField(String testField) {
        this.testField = testField;
    }
    
    
}
