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
 * ValidatorActionFormEx,DynaValidatorActionFormEx
 * の共通メソッドを定義するインタフェース。
 *
 * <p>
 *  TERASOLUNAフレームワーク内部ではこのインターフェイスの型を
 *  通してアクションフォームにアクセスすることで各フォームの差分を
 *  吸収している。
 * </p>
 *
 * @see jp.terasoluna.fw.web.struts.form.ValidatorActionFormEx
 * @see
 *  jp.terasoluna.fw.web.struts.form.DynaValidatorActionFormEx
 *
 */
public interface FormEx {

    /**
     * 指定したインデックスのプロパティ値を取得する。
     *
     * @param name 取得対象のプロパティ名
     * @param index 取得対象のインデックス
     * @return プロパティ値
     */
    Object getIndexedValue(String name, int index);

    /**
     * 指定した名前のプロパティ値の件数を取得する。
     *
     * @param name 取得対象のプロパティ名
     * @return プロパティ値
     * <p>プロパティ名が取得できなかった場合は0を返す。</p>
     */
    int getIndexCount(String name);

    /**
     * 指定したインデックスの位置にプロパティ値を設定する。
     *
     * @param name 設定対象のインデックス付プロパティ名
     * @param index 設定対象のインデックス位置
     * @param value 設定するプロパティ値
     */
    void setIndexedValue(String name, int index, Object value);

    /**
     * 値変更フラグを取得する。
     *
     * @return 値変更フラグ
     */
    boolean isModified();

    /**
     * 値変更フラグを設定する。
     *
     * @param modified 値変更フラグ
     */
    void setModified(boolean modified);

}
