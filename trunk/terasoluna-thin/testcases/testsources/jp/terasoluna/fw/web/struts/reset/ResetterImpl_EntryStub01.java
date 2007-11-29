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

import java.util.Map.Entry;

/**
 * ResetterImplTestで使用するEntryのスタブ実装。
 *
 */
public class ResetterImpl_EntryStub01 implements Entry {

    /**
     * getKeyの戻り値。
     */
    Object key = null;

    /**
     * getValueの戻り値。
     */
    Object value = null;

    /**
     * keyを返却する。
     * @return key
     */
    public Object getKey() {
        return this.key;
    }

    /**
     * valueを返却する。
     * @return value
     */
    public Object getValue() {
        return this.value;
    }

    /**
     * 何もしない。
     * @param value 値
     * @return null
     */
    public Object setValue(Object value) {
        return null;
    }

}
