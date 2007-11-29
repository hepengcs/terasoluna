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

package jp.terasoluna.fw.web.codelist;

import java.io.Serializable;

/**
 * コードとコード値(表示文字列)を保持するBeanクラス。
 *
 */
public class CodeBean implements Serializable {

    /**
	 * シリアルバージョンID
	 */
	private static final long serialVersionUID = -6727581752645857389L;

	/**
     * idを表すフィールド値。
     */
    private String id = "";

    /**
     * nameを表すフィールド値。
     */
    private String name = "";

    /**
     * idを設定する。
     *
     * @param id idを表すフィールド値
     */
    public void setId(String id) {
        this.id = (id == null) ? "" : id;
    }

    /**
     * idを返却する。
     *
     * @return idを表すフィールド値
     */
    public String getId() {
        return this.id;
    }

    /**
     * nameを設定する。
     *
     * @param name nameを表すフィールド値
     */
    public void setName(String name) {
        this.name = (name == null) ? "" : name;
    }

    /**
     * nameを返却する。
     *
     * @return nameを表すフィールド値
     */
    public String getName() {
        return name;
    }

    /**
     * コードとコード値(表示文字列)を半角スペースで連結した文字列を取得する。
     *
     * <p>出力される結果は、下記のとおりになる。<br>
     *  <code>id</code>+(半角スペース)+ <code>name</code>
     * </p>
     *
     * @return コードとコード値(表示文字列)を半角スペースで連結した文字列
     */
    public String getLabel() {
        return id + " " + name;
    }

    /**
     * コードとコード値(表示文字列)をカンマ(",")で連結した文字列を取得する。
     *
     * <p>出力される結果は、下記のとおりになる。<br>
     *  <code>id</code> + , + <code>name</code>
     * </p>
     *
     * @return コードとコード値(表示文字列)をカンマで連結した文字列
     */
    public String getCodeCommaName() {
        return id + "," + name;
    }
}
