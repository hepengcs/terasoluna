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


/**
 * 
 * ビジネスロジック処理クラスが実装すべきインタフェース。
 *
 * <p>
 * ビジネスロジックは、このインタフェースを実装することにより作成する。
 * ビジネスロジックのエントリポイントとなるexecute()メソッドには、
 * JavaBeanが渡される。<br>
 * 下記は、典型的なビジネスロジックの実装例（ログイン処理）である。
 * </p>
 * <h5>実装例</h5>
 * <code><pre>
 * ・・・
 * public BLogicResult execute(LogonBean params) {
 *
 *     BLogicResult result = new BLogicResult();
 *
 *     // ユーザID
 *     String userId = params.getUserId();
 *     // パスワード
 *     String password = params.getPassword();
 *     // ログイン失敗回数
 *     String failures = params.getFailures();
 *
 *     int failCount = Integer.parseInt(failures);
 *
 *     // ログイン失敗回数が規定回数以上の時、無条件で失敗
 *     if (failCount &gt; MAX_COUNT ) {
 *         result.setResultString("loginError");
 *         return result;
 *     }
 *
 *     // ユーザがDBに存在するか
 *     if (isAuthenticated(userId, password)) {
 *         // 出力値とするJavaBeanを生成
 *         LogonResultBean bean = new LogonResultBean();
 *         // 存在する場合、UVOを生成し、結果オブジェクトに格納
 *         sampleUVO uvo = UserValueObject.createUserValueObject();
 *         bean.setUvo(uvo);
 *         result.setResultObject(bean);
 *         // 認証成功として遷移
 *         result.setResultString("success");
 *     } else {
 *         BLogicMessages errors = new BLogicMessages();
 *         BLogicMessage error = new BLogicMessage("message.error.login");
 *         
 *         errors.add(ActionMessages.GLOBAL_MESSAGE, error);
 *         result.setErrors(errors);
 *         ・・・
 *         // 認証失敗として遷移
 *         result.setResultString("failure");
 *     }
 *     return result;
 * }
 * </pre></code>
 * <p>
 * 下記は、ビジネスロジック内部からDAOを実行する例である。
 * DAOは予め設定されているものとする。DAOの設定については、
 * 各DAO実装クラスを参照のこと。
 * </p>
 * <h5>ビジネスロジックからのDAO実行例</h5>
 * <code><pre>
 * private boolean isAuthenticated(String id, String pass) {
 *
 *     // DAOに渡すパラメータの作成
 *     Map&lt;String, String&gt; parameters = new HashMap&lt;String, String&gt;();
 *     parameters.put("ID", id);
 *     parameters.put("PASSWORD", pass);
 *
 *     // DAOの実行
 *     Map&lt;String, Object&gt;[] result = dao.executeForMapList(
 *                                             USER_AUTHENTICATION, parameters);
 *     if (result != null) {
 *        // ユーザが存在
 *        return true;
 *     }
 *     // ユーザが存在しない
 *     return false;
 * }
 * </pre></code>
 * <p>
 *  DBのコミット、ロールバックは宣言的トランザクションを用いて行うため、
 *  実装者は直接DBコネクションに対して、コミット・ロールバックは行わない。<br>
 *
 *  関連する機能については、下記を参照のこと。
 *  <ul>
 *  <li>出力情報の詳細：BLogicResult</li>
 *  <li>Web層のオブジェクトと、ビジネスロジックとのマッピング設定について：
 *      BLogicIOPlugIn</li>
 *  </ul>
 * </p>
 *
 * @see jp.terasoluna.fw.web.struts.plugins.BLogicIOPlugIn
 * @see jp.terasoluna.fw.service.thin.AbstractBLogicMapper
 * @see jp.terasoluna.fw.service.thin.BLogicMapper
 * @see jp.terasoluna.fw.service.thin.BLogicIO
 * @see jp.terasoluna.fw.service.thin.BLogicProperty
 * @see jp.terasoluna.fw.service.thin.BLogicResources
 * @see jp.terasoluna.fw.service.thin.BLogicResult
 * @see jp.terasoluna.fw.web.struts.actions.AbstractBLogicAction
 * @see jp.terasoluna.fw.web.struts.actions.BLogicAction
 *
 * @param <P> ビジネスロジックへの入力値となるJavaBeanを指定する
 *
 */
public interface BLogic<P> {

    /**
     * ビジネスロジックを実行する。
     *
     * @param params ビジネスロジック入力情報
     * @return ビジネスロジック処理結果
     */
    BLogicResult execute(P params);

}
