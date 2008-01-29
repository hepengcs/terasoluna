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
import java.util.HashMap;
import java.util.Map;

/**
 * ビジネスロジック入出力情報を保持するクラス。
 * <p>
 *  blogic-io.xmlから読み込まれた設定情報はこのクラスが
 *  親となり保持する。
 *  個々の設定情報はBLogicIO、BLogicProperty
 *  のインスタンスとして保持する。<br>
 *  このクラスのインスタンスはサーブレットコンテキストに
 *  『BLOGIC_RESOURCES + モジュール名』というキー名で保存される。
 *  <br>
 *  blogic-io.xmlの読み込みについての詳細は、
 *  {@link jp.terasoluna.fw.web.struts.plugins.BLogicIOPlugIn}
 *  を参照のこと。
 * </p>
 *
 * @see jp.terasoluna.fw.web.struts.plugins.BLogicIOPlugIn
 * @see jp.terasoluna.fw.service.thin.AbstractBLogicMapper
 * @see jp.terasoluna.fw.service.thin.BLogicMapper
 * @see jp.terasoluna.fw.service.thin.BLogicIO
 * @see jp.terasoluna.fw.service.thin.BLogicProperty
 * @see jp.terasoluna.fw.service.thin.BLogicResult
 * @see jp.terasoluna.fw.web.struts.actions.AbstractBLogicAction
 * @see jp.terasoluna.fw.web.struts.actions.BLogicAction
 *
 */
public class BLogicResources implements Serializable {

    /**
     * シリアルバージョンID
     */
    private static final long serialVersionUID = 4787121961877175389L;

    /**
     * このクラスのインスタンスをサーブレットコンテキストに保存
     * する際のキーのプレフィックス値。
     */
    public static final String BLOGIC_RESOURCES_KEY = "BLOGIC_RESOURCES";

    /**
     * アクションパスをキーに入出力情報を保持するMap。
     */
    private Map<String, BLogicIO> blogicIO = new HashMap<String, BLogicIO>();

    /**
     * アクションパス名をキーにして入出力情報を取得する。
     *
     * @param path アクションパス名
     * @return 指定されたアクションパスに紐づいた入出力情報
     */
    public BLogicIO getBLogicIO(String path) {
        return blogicIO.get(path);
    }

    /**
     * 入出力情報を設定する。
     * 入出力情報はアクションパス名をキーにして保存される。
     * なお、引数blogicIOがnullの場合は、NullPointerExceptionが発生する。
     *
     * @param blogicIO ビジネスロジック入出力情報
     */
    public void setBLogicIO(BLogicIO blogicIO) {
        this.blogicIO.put(blogicIO.getPath(), blogicIO);
    }

}
