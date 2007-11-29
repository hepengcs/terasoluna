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

/**
 *   フィールドのリセット情報を保持する Bean クラス。
 *
 * <p>
 * フォームリセット定義ファイル(reset.xml)から読み込まれた設定情報は
 * フィールド毎にこのクラスに格納され保持する。
 * このクラスのインスタンスは {@link ActionReset} インスタンスに
 * 格納される。
 * </p>
 * 
 * @see jp.terasoluna.fw.web.struts.plugins.ResetterPlugIn
 * @see jp.terasoluna.fw.web.struts.reset.Resetter
 * @see jp.terasoluna.fw.web.struts.reset.ActionReset
 * @see jp.terasoluna.fw.web.struts.reset.ResetterResources
 * @see jp.terasoluna.fw.web.struts.form.FormEx
 * @see 
 *  jp.terasoluna.fw.web.struts.form.DynaValidatorActionFormEx
 * @see jp.terasoluna.fw.web.struts.form.ValidatorActionFormEx
 * 
 */
public class FieldReset {

    /**
     * リセット処理対象となるフィールド名。
     */
    private String fieldName = null;

    /**
     * 指定範囲リセット機能選択フラグ。
     */
    private boolean select = false;

    /**
     * リセット処理対象となるフィールド名を取得する。
     *
     * @return リセット処理対象となるフィールド名
     */
    public String getFieldName() {
        return fieldName;
    }

    /**
     * リセット処理対象となるフィールド名を設定する。
     *
     * @param fieldName リセット処理対象となるフィールド名
     */
    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    /**
     * 指定範囲リセット機能選択フラグを取得する。
     *
     * @return 指定範囲リセット機能選択フラグ。
     */
    public boolean isSelect() {
        return select;
    }

    /**
     * 指定範囲リセット機能選択フラグを設定する。
     *
     * @param select 指定範囲リセット機能選択ドフラグ
     */
    public void setSelect(boolean select) {
        this.select = select;
    }

}
