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

package jp.terasoluna.fw.service.thin;

import java.io.Serializable;

/**
 * ビジネスロジック入出力情報の個々のプロパティ情報を保持するクラス。
 *
 * <p>
 *  BLogicIOPlugInでビジネスロジック入出力情報定義ファイルである
 *  blogic-io.xmlからビジネスロジックに対する
 *  入力JavaBean及び出力JavaBeanのプロパティ設定を保持する。<br>
 *  このクラスを含むビジネスロジック入出力情報の設定については、
 *  {@link jp.terasoluna.fw.web.struts.plugins.BLogicIOPlugIn}
 *  を参照のこと。
 * </p>
 *
 * @see jp.terasoluna.fw.web.struts.plugins.BLogicIOPlugIn
 * @see jp.terasoluna.fw.service.thin.AbstractBLogicMapper
 * @see jp.terasoluna.fw.service.thin.BLogicMapper
 * @see jp.terasoluna.fw.service.thin.BLogicIO
 * @see jp.terasoluna.fw.service.thin.BLogicResources
 * @see jp.terasoluna.fw.service.thin.BLogicResult
 * @see jp.terasoluna.fw.web.struts.actions.AbstractBLogicAction
 * @see jp.terasoluna.fw.web.struts.actions.BLogicAction
 *
 */
public class BLogicProperty implements Serializable {

    /**
     * シリアルバージョンID
     */
    private static final long serialVersionUID = 7045786022774532390L;

    /**
     * Web層のオブジェクトのプロパティ名。
     */
    private String property = null;

    /**
     * ビジネスロジック内で扱われるプロパティ名。
     */
    private String blogicProperty = null;

    /**
     * 入力元、または出力先のWeb層のオブジェクトが何かを識別する値。
     */
    private String sourceOrDest = null;

    /**
     * プロパティ名を設定する。
     *
     * @param property プロパティ名
     */
    public void setProperty(String property) {
        this.property = property;
    }

    /**
     * プロパティ名を取得する。
     *
     * @return プロパティ名
     */
    public String getProperty() {
        return property;
    }

    /**
     * ビジネスロジック内で扱われるプロパティ名を設定する。
     *
     * @param blogicProperty プロパティ名
     */
    public void setBLogicProperty(String blogicProperty) {
        this.blogicProperty = blogicProperty;
    }

    /**
     * ビジネスロジック内で扱われるプロパティ名を取得する。
     *
     * @return プロパティ名
     */
    public String getBLogicProperty() {
        return blogicProperty;
    }

    /**
     * 入力元を設定する。
     *
     * @param source 入力元
     */
    public void setSource(String source) {
        this.sourceOrDest = source;
    }

    /**
     * 入力元を取得する。
     *
     * @return 入力元
     */
    public String getSource() {
        return this.sourceOrDest;
    }

    /**
     * 出力先を設定する。
     *
     * @param dest 出力先
     */
    public void setDest(String dest) {
        this.sourceOrDest = dest;
    }

    /**
     * 出力先を取得する。
     *
     * @return 出力先
     */
    public String getDest() {
        return sourceOrDest;
    }

}
