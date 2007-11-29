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
import java.util.ArrayList;
import java.util.List;

/**
 * ビジネスロジック入出力情報を保持するクラス。
 *
 * <p>
 *  BLogicIOPlugInでビジネスロジック入出力情報定義ファイルである
 *  blogic-io.xmlから、アクションパス名と
 *  そのアクションが起動された時の入力情報、出力情報を保持する。
 *  設定については、BLogicIOPlugInを参照のこと。
 * </p>
 *
 * @see jp.terasoluna.fw.web.struts.plugins.BLogicIOPlugIn
 * @see jp.terasoluna.fw.service.thin.AbstractBLogicMapper
 * @see jp.terasoluna.fw.service.thin.BLogicMapper
 * @see jp.terasoluna.fw.service.thin.BLogicProperty
 * @see jp.terasoluna.fw.service.thin.BLogicResources
 * @see jp.terasoluna.fw.service.thin.BLogicResult
 * @see jp.terasoluna.fw.web.struts.actions.AbstractBLogicAction
 * @see jp.terasoluna.fw.web.struts.actions.BLogicAction
 *
 */
public class BLogicIO implements Serializable {

    /**
     * シリアルバージョンID
     */
    private static final long serialVersionUID = 5498810299684603811L;

    /**
     * アクションパス名。
     */
    private String path = null;

    /**
     * ビジネスロジック入力情報。
     */
    private List<BLogicProperty> blogicParams = new ArrayList<BLogicProperty>();

    /**
     * ビジネスロジック出力情報。
     */
    private List<BLogicProperty> blogicResults  =
        new ArrayList<BLogicProperty>();

    /**
     * ビジネスロジック入力情報となるJavaBean名。
     */
    private String inputBeanName = null;

    /**
     * ビジネスロジック入力情報となるJavaBean名を取得する。
     *
     * @return ビジネスロジック入力情報JavaBean名
     */
    public String getInputBeanName() {
        return inputBeanName;
    }

    /**
     * ビジネスロジック入力情報となるJavaBean名を設定する。
     *
     * @param inputBeanName ビジネスロジック入力情報JavaBean名
     */
    public void setInputBeanName(String inputBeanName) {
        this.inputBeanName = inputBeanName;
    }

    /**
     * ビジネスロジック入力情報を取得する。
     *
     * @return ビジネスロジック入力情報
     */
    public List<BLogicProperty> getBLogicParams() {
        return blogicParams;
    }

    /**
     * ビジネスロジック入力情報を設定する。
     *
     * @param blogicParam ビジネスロジック入力情報
     */
    public void setBLogicParam(BLogicProperty blogicParam) {
        this.blogicParams.add(blogicParam);
    }

    /**
     * アクションパス名を設定する。
     *
     * @param path アクションパス名
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * アクションパス名を取得する。
     *
     * @return アクションパス名
     */
    public String getPath() {
        return path;
    }

    /**
     * ビジネスロジック出力情報を設定する。
     *
     * @param blogicResult ビジネスロジック出力情報
     */
    public void setBLogicResult(BLogicProperty blogicResult) {
        this.blogicResults.add(blogicResult);
    }

    /**
     * ビジネスロジック出力情報を取得する。
     *
     * @return ビジネスロジック出力情報
     */
    public List<BLogicProperty> getBLogicResults() {
        return blogicResults;
    }
}
