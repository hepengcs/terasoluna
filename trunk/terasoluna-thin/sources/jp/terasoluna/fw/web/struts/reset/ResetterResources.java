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

import java.util.HashMap;
import java.util.Map;

/**
 * リセット用の設定情報を保持するクラス。
 *
 * <p>
 * フォームリセット定義ファイル(reset.xml)から読み込まれた設定情報は
 * このクラスに格納され保持する。
 * 個々の設定情報は{@link ActionReset}、{@link FieldReset}
 * のインスタンスとして保持する。<br>
 * </p>
 *

 * @see jp.terasoluna.fw.web.struts.reset.Resetter
 * @see jp.terasoluna.fw.web.struts.plugins.ResetterPlugIn
 * @see jp.terasoluna.fw.web.struts.reset.ActionReset
 * @see jp.terasoluna.fw.web.struts.reset.FieldReset
 * @see jp.terasoluna.fw.web.struts.reset.ResetterResources
 * @see jp.terasoluna.fw.web.struts.form.FormEx
 * @see 
 *  jp.terasoluna.fw.web.struts.form.DynaValidatorActionFormEx
 * @see jp.terasoluna.fw.web.struts.form.ValidatorActionFormEx
 *
 */
public class ResetterResources {

    /**
     * このクラスのインスタンスをサーブレットコンテキストに保存
     * する際のキー。
     */
    public static final String RESETTER_RESOURCES_KEY = "RESETTER_RESOURCES";

    /**
     * アクション要素を保持する Map。
     */
    private Map<String, ActionReset> actionResets =
            new HashMap<String, ActionReset>();

    /**
     * ActionReset を設定する。
     *
     * @param actionReset アクション要素の設定情報
     * @see jp.terasoluna.fw.web.struts.reset.ActionReset
     */
    public void setActionReset(ActionReset actionReset) {
        this.actionResets.put(actionReset.getPath(), actionReset);
    }

    /**
     * アクションパスをキーとして ActionReset 
     * インスタンスを取得する。
     *
     * @param path アクションパス
     * @return 引数のパスと紐づいたアクション要素
     * @see jp.terasoluna.fw.web.struts.reset.ActionReset
     */
    public ActionReset get(String path) {
        return actionResets.get(path);
    }

}
