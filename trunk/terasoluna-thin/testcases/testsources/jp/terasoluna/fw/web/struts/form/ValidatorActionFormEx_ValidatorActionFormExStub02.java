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
 * {@link ValidatorActionFormExTest}にて使用するスタブクラス。
 *
 */
@SuppressWarnings("serial")
public class ValidatorActionFormEx_ValidatorActionFormExStub02
    extends ValidatorActionFormEx {

    private String[] stringValues = new String[]{};

    /**
     * stringValuesを返却する。
     * @return stringValues
     */
    public String[] getStringValues() {
        return stringValues;
    }

    /**
     * stringValuesを設定する。
     * @param stringValues stringValues
     */
    public void setStringValues(@SuppressWarnings("unused") String[] stringValues) {
        throw new RuntimeException();
    }

    private List valueList = new ArrayList();

    /**
     * valueListを返却する。
     * @return valueList
     */
    public List getValueList() {
        return valueList;
    }

    /**
     * valueListを設定する。
     * @param valueList valueList
     */
    public void setValueList(List valueList) {
        throw new RuntimeException();
    }


}
