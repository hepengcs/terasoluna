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

package jp.terasoluna.fw.web.struts.action;

import org.apache.struts.action.ActionMapping;

/**
 * ActionMapping拡張クラス。
 *
 * <p>
 *  StrutsのActionMappingに対して、以下の点が拡張されている。
 *  <ul>
 *    <li>ActionFormの初期化指定：
 *        RequestProcessorExを参照のこと</li>
 *    <li>cancelPopulateフラグ指定：
 *        RequestProcessorExを参照のこと</li>
 *  </ul>
 *  フィールドのgetterとsetterを同名にすると、
 *  プロパティが設定されないことがあるため、getterと
 *  setterではメソッドを同名にしないこと。<br>
 *  また、struts-config.xmlのset-property指定の
 *  プロパティは、名称をsetterに合わせること。<br>
 *  本機能を利用するためには、struts-config.xmlに対し、
 *  &lt;action-mappings&gt;要素のtype属性に
 *  クラス名を指定する。
 * </p>
 * <p>
 *  <strong>struts-config.xml によるActionMappingExの設定例</strong>
 *  <code><pre>
 *  &lt;struts-config&gt;
 *    ・・・
 *   &lt;action-mappings
 *    <b>type="jp.terasoluna.fw.web.struts.action.ActionMappingEx"</b>&gt;
 *    ・・・
 *     &lt;action path="/start"
 *       name="validateSampleForm"
 *       scope="session"&gt;
 *       ・・・
 *     &lt;/action&gt;
 *    ・・・
 *   &lt;/action-mappings&gt;
 * ・・・
 * </pre></code>
 * </p>
 *
 * @see jp.terasoluna.fw.web.struts.action.RequestProcessorEx
 *
 */
public class ActionMappingEx extends ActionMapping {

    /**
	 * シリアルバージョンID
	 */
	private static final long serialVersionUID = -7859832817953363932L;

	/**
     * アクションフォームを強制的に新規作成するかどうか。
     * デフォルトはfalse（既存があれば再利用）。
     */
    private boolean clearForm = false;

    /**
     * processPopulate をスキップするかどうか。
     * デフォルトはfalse（スキップしない）。
     */
    private boolean cancelPopulate = false;

    /**
     * アクションフォームクリアフラグを設定する。
     *
     * @param clearForm アクションフォームクリアフラグ
     */
    public void setClearForm(boolean clearForm) {
        this.clearForm = clearForm;
    }

    /**
     * アクションフォームクリアフラグを取得する。
     *
     * @return アクションフォームクリアフラグ
     */
    public boolean getClearForm() {
        return this.clearForm;
    }

    /**
     * ポピュレーションキャンセルフラグを設定する。
     *
     * @param cancelPopulate ポピュレーションキャンセルフラグ
     */
    public void setCancelPopulate(boolean cancelPopulate) {
        this.cancelPopulate = cancelPopulate;
    }

    /**
     * ポピュレーションキャンセルフラグを取得する。
     *
     * @return ポピュレーションキャンセルフラグ
     */
    public boolean getCancelPopulate() {
        return this.cancelPopulate;
    }
}
