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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import jp.terasoluna.fw.web.struts.form.FormEx;

import org.apache.struts.action.ActionMapping;

/**
 * ResetterImplTestで使用するスタブクラス。
 *
 *
 */
public class ResetterImplStub01 extends ResetterImpl {

    /**
     * getActionResetの戻り値となるインスタンス。
     */
    public ActionReset actionReset = null;

    /**
     * resetSelectFieldが呼び出された回数。
     */
    public int resetSelectFieldCount = 0;

    /**
     * resetSelectFieldの第一引数をキャッシュするList。
     */
    public List<FormEx> resetSelectFieldArg0 = new ArrayList<FormEx>();

    /**
     * resetSelectFieldの第二引数をキャッシュするList。
     */
    public List<Map> resetSelectFieldArg1 = new ArrayList<Map>();

    /**
     * resetSelectFieldの第三引数をキャッシュするList。
     */
    public List<HttpServletRequest> resetSelectFieldArg2 =
    	new ArrayList<HttpServletRequest>();

    /**
     * resetValueが呼び出された回数。
     */
    public int resetValueCount = 0;

    /**
     * resetValueの第一引数をキャッシュするList。
     */
    public List<FormEx> resetValueArg0 = new ArrayList<FormEx>();

    /**
     * resetValueの第二引数をキャッシュするList。
     */
    public List<Entry> resetValueArg1 = new ArrayList<Entry>();

    /**
     * インスタンス変数actionResetを返却する。
     */
    @Override
    protected ActionReset getActionReset(ActionMapping mapping,
            HttpServletRequest request) {
        return this.actionReset;
    }

    /**
     * 呼び出しの内容をキャッシュする。
     */
    @Override
    protected void resetSelectField(FormEx form,
            Map<String, Object> propMap, HttpServletRequest request) {
        this.resetSelectFieldCount++;
        this.resetSelectFieldArg0.add(form);
        this.resetSelectFieldArg1.add(propMap);
        this.resetSelectFieldArg2.add(request);
    }

    /**
     * 呼び出しの内容をキャッシュする。
     */
    @Override
    protected void resetValue(FormEx form, Entry<String, Object> entry) {
        this.resetValueCount++;
        this.resetValueArg0.add(form);
        this.resetValueArg1.add(entry);
    }

}
