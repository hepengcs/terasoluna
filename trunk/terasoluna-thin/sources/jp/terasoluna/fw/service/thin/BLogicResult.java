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
 * ビジネスロジック出力情報クラス。
 *
 * <p>
 *  フォーム、セッションといったWeb層へのデータ反映を行う。
 *  反映処理自身はBLogicMapperが行うためコーディングは不要だが、
 *  アクションごとに、blogic-io.xmlに反映するデータの内容、
 *  Web層への反映先を指定する必要がある。
 *  この設定については、
 *  {@link jp.terasoluna.fw.web.struts.plugins.BLogicIOPlugIn}
 *  を参照のこと。<br>
 *  このクラスで設定すべき情報は、下記のものがある。
 * <ul>
 *   <li>ビジネスロジック内で取得され、Web層に渡すべき情報を、
 *   JavaBeanでBLogicResult内に設定する</li>
 *   <li>ビジネスロジック内で業務エラーが発生した場合、
 *   BLogicMessagesを設定する。</li>
 *   <li>ビジネスロジックの実行結果を表す文字列を設定する。
 *   これはstruts-config.xmlで設定される
 *   論理フォワード名（&lt;forward&gt;要素で指定）に相当する</li>
 * </ul>
 *
 *  なお、MVCにStrutsを使用する場合、以降の処理でBLogicMessagesの内容は
 *  ActionMessagesに置き換えられる。<br>
 *
 *  下記は、BLogic実装クラス内から返却される、
 *  BLogicResultの使用例である。
 * </p>
 *
 * <h5>BLogicResultの使用例（BLogic#execute()を実装）
 * </h5>
 * <p>
 * <code><pre>
 * public BLogicResult execute(ParamsBean params) {
 *
 *     // BLogic内でnewを行い、BLogicResultを生成する。
 *     BLogicResult result = new BLogicResult();
 *     ・・・
 *     //ビジネスロジック
 *     ・・・
 *     //エラー判定
 *     if (// エラー判定処理 ) {
 *         // フォーム、セッションに反映すべき情報を設定する。
 *         ResultBean bean = new ResultBean();
 *         bean.setUserId(userId);
 *         result.setResultObject(bean);
 *         // 実行結果に"success"を指定
 *         result.setResultString("success");
 *         return result;
 *     } else {
 *         // ビジネスロジック内のエラーが発生
 *         // エラー用BLogicMessagesをBLogicResultに設定
 *         result.setErrors(errorMessages);
 *         // 実行結果に"failure"を指定
 *         result.setResultString("failure");
 *         return result;
 *     }
 * }
 * </pre></code>
 * </p>
 *
 * @see jp.terasoluna.fw.web.struts.plugins.BLogicIOPlugIn
 * @see jp.terasoluna.fw.service.thin.AbstractBLogicMapper
 * @see jp.terasoluna.fw.service.thin.BLogicMapper
 * @see jp.terasoluna.fw.service.thin.BLogicIO
 * @see jp.terasoluna.fw.service.thin.BLogicProperty
 * @see jp.terasoluna.fw.service.thin.BLogicResources
 * @see jp.terasoluna.fw.web.struts.actions.AbstractBLogicAction
 * @see jp.terasoluna.fw.web.struts.actions.BLogicAction
 *
 */
public class BLogicResult implements Serializable {

    /**
     * シリアルバージョンID
     */
    private static final long serialVersionUID = -1861617801019993048L;

    /**
     * ビジネスロジックの実行結果を格納したJavaBean。
     */
    private Object resultObject = null;

    /**
     * ビジネスロジックの実行結果を表す文字列。
     */
    private String resultString = null;

    /**
     * ビジネスロジック内で生成されるエラー用BLogicMessages。
     */
    private BLogicMessages errors = null;

    /**
     * ビジネスロジック内で生成されるメッセージ用BLogicMessages。
     */
    private BLogicMessages messages = null;
    
    /**
     * ビジネスロジック内で生成された、エラー用BLogicMessagesを取得する。
     *
     * @return ビジネスロジック内で生成された、エラー用BLogicMessages
     */
    public BLogicMessages getErrors() {
        return this.errors;
    }

    /**
     * ビジネスロジック内で生成された、メッセージ用BLogicMessagesを取得する。
     *
     * @return ビジネスロジック内で生成された、メッセージ用BLogicMessages
     */
    public BLogicMessages getMessages() {
        return this.messages;
    }

    /**
     * ビジネスロジック内で生成された、エラー用BLogicMessagesを設定する。
     *
     * @param paramErrors
     *            ビジネスロジック内で生成された、エラー用BLogicMessages
     */
    public void setErrors(BLogicMessages paramErrors) {
        this.errors = paramErrors;
    }

    /**
     * ビジネスロジック内で生成された、メッセージ用BLogicMessagesを設定する。
     *
     * @param paramMessages
     *            ビジネスロジック内で生成された、メッセージ用BLogicMessages
     */
    public void setMessages(BLogicMessages paramMessages) {
        this.messages = paramMessages;
    }

    /**
     * ビジネスロジックの実行結果を表す文字列を取得する。
     *
     * @return ビジネスロジックの実行結果を表す文字列
     */
    public String getResultString() {
        return this.resultString;
    }

    /**
     * ビジネスロジックの実行結果を表す文字列を設定する。
     *
     * @param resultString ビジネスロジックの実行結果を表す文字列
     */
    public void setResultString(String resultString) {
        this.resultString = resultString;
    }

    /**
     * ビジネスロジックの実行結果を格納したJavaBeanを取得する。
     *
     * @return ビジネスロジックの実行結果を格納したJavaBean
     */
    public Object getResultObject() {
        return resultObject;
    }

    /**
     * ビジネスロジックの実行結果を格納したJavaBeanを設定する。
     *
     * @param resultObject ビジネスロジックの実行結果を格納したJavaBean
     */
    public void setResultObject(Object resultObject) {
        this.resultObject = resultObject;
    }
    
}
