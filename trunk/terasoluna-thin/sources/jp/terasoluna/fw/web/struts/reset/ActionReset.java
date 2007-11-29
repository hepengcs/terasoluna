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
import java.util.Iterator;
import java.util.Map;

/**
 *  アクションパス単位のリセット用の設定情報を保持する Bean クラス。
 *
 * <p>
 * フォームリセット定義ファイル(reset.xml)から読み込まれた設定情報は
 * アクションパス毎にこのクラスに格納される保持する。
 * リセット対象となるフィールドの設定情報は {@link FieldReset}
 * インスタンスとして保持する。<br>
 * このクラスのインスタンスは {@link ResetterResources} インスタンスに
 * 格納され、サーブレットコンテキストに
 * RESETTER_RESOURCES + "モジュール名"というキー名で保存される。
 * </p>
 *
 * @see jp.terasoluna.fw.web.struts.plugins.ResetterPlugIn
 * @see jp.terasoluna.fw.web.struts.reset.Resetter
 * @see jp.terasoluna.fw.web.struts.reset.FieldReset
 * @see jp.terasoluna.fw.web.struts.reset.ResetterResources
 * @see jp.terasoluna.fw.web.struts.form.FormEx
 * @see 
 *  jp.terasoluna.fw.web.struts.form.DynaValidatorActionFormEx
 * @see jp.terasoluna.fw.web.struts.form.ValidatorActionFormEx
 *
 */
public class ActionReset {

    /**
     * リセット処理対象となるアクションパス。
     */
    private String path = null;

    /**
     * リセット処理対象となるフィールド要素を保持する Map。
     */
    private Map<String, FieldReset> fieldResets = 
        new HashMap<String, FieldReset>();

    /**
     * リセット処理対象となるフィールド要素を設定する。
     *
     * @param fieldReset リセット処理対象となるフィールド要素
     */
    public void setFieldReset(FieldReset fieldReset) {
        fieldResets.put(fieldReset.getFieldName(), fieldReset);
    }

    /**
     * リセット処理対象となるアクションパスを取得する。
     *
     * @return リセット処理対象となるアクションパス
     */
    public String getPath() {
        return path;
    }

    /**
     * リセット処理対象となるアクションパスを設定する。
     *
     * @param path リセット処理対象となるアクションパス
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * リセット処理対象となるフィールド名の一覧を取得する。
     *
     * @return リセット処理対象となるフィールド名一覧
     */
    public Iterator<String> getFieldNames() {
        return this.fieldResets.keySet().iterator();
    }

    /**
     * 指定されたフィールドの指定範囲リセット機能選択フラグを取得する。
     *
     * @param fieldName フィールド名
     * @return 指定範囲リセット機能選択フラグ。
     */
    public boolean isSelectField(String fieldName) {
        FieldReset field = fieldResets.get(fieldName);
        if (field != null) {
            return field.isSelect();
        }
        return false;
    }

}
