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

package jp.terasoluna.fw.web.thin;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import jp.terasoluna.fw.web.RequestUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 業務閉塞状態かどうかのチェックを行う。
 *
 * <p>
 * このクラスではブラウザからのリクエストに対するフィルタ処理を、
 * Bean定義ファイルで指定された任意の{@link BlockageController}インスタンスを
 * 呼び出し、 指定されたパスが業務閉塞状態かどうかのチェック処理を委譲する。
 * </p>
 *
 * <h5>業務閉塞チェック機能</h5>
 * <p>
 * アクセスしたパスが業務閉塞状態だった場合は、
 * {@link BlockageException} をスローする。
 * </p>
 * <h5>使用方法</h5>
 * <p>
 * この機能を使用するにはデプロイメントディスクリプタ（web.xml）と
 * Bean定義ファイルに以下のように設定する。このとき、Bean定義ファイルに
 * 定義するid属性が、 sampleBlockageControllerである&lt;bean&gt;要素の
 * class属性には、{@link BlockageController}インタフェースを
 * 実装したクラスを設定する。<br>
 * ※注意として、このフィルタを用いる場合には、リクエストパスから業務が判別できるようになっていなければ
 * ならない。
 * 
 * </p>
 * デプロイメントディスクリプタ（web.xml）
 * <code><pre>
 * &lt;filter&gt;
 *   &lt;filter-name&gt;
 *     blockageControlFilter]
 *   &lt;/filter-name&gt;
 *   &lt;filter-class&gt;
 *       jp.terasoluna.fw.web.thin.BlockageControlFilter
 *   &lt;/filter-class&gt;
 *   &lt;init-param&gt;
 *     &lt;param-name&gt;controller&lt;/param-name&gt;
 *     &lt;param-value&gt;
 *       "sampleBlockageController"
 *     &lt;/param-value&gt;
 *   &lt;/init-param&gt;
 * &lt;/filter&gt;
 *
 * &lt;filter-mapping&gt;
 *   &lt;filter-name&gt;blockageControlFilter&lt;/filter-name&gt;
 *   &lt;url-pattern&gt;/*&lt;/url-pattern&gt;
 * &lt;/filter-mapping&gt;
 *
 * &lt;error-page&gt;
 *   &lt;exception-type&gt;
 *     jp.terasoluna.fw.web.thin.BlockageException
 *   &lt;/exception-type&gt;
 *   &lt;location&gt;
 *     /blockageError.jsp
 *   &lt;/location&gt;
 * &lt;/error-page&gt;
 * </pre></code>
 *
 * Bean定義ファイル
 * <code><pre>
 * &lt;bean id=&quot;sampleBlockageController&quot;
 *       class=&quot;jp.terasoluna…SampleBlockageController&quot; /&gt;
 * </pre></code>
 * 
 * なお、Bean定義ファイルに定義する&lt;bean&gt;要素のid属性をデフォルト値である
 * "blockageController"に設定する場合には、
 * デプロイメントディスクリプタ（web.xml）内の&lt;filter&gt;要素から
 * &lt;init-param&gt;要素を省略することができる。
 *
 * @see jp.terasoluna.fw.web.thin.AuthenticationControlFilter
 * @see jp.terasoluna.fw.web.thin.AuthenticationController
 * @see jp.terasoluna.fw.web.thin.AuthorizationControlFilter
 * @see jp.terasoluna.fw.web.thin.AuthorizationController
 * @see jp.terasoluna.fw.web.thin.BlockageController
 * @see jp.terasoluna.fw.web.thin.ServerBlockageControlFilter
 * @see jp.terasoluna.fw.web.thin.ServerBlockageController
 *
 */
public class BlockageControlFilter 
        extends AbstractControlFilter<BlockageController> {
    
    /**
     * リクエストがフィルタを通過したことを示すリクエスト属性のキー。
     */
    public static final String BLOCKAGE_THRU_KEY 
        = "BLOCKAGE_THRU_KEY";   
    
    /**
     * DIコンテナからコントローラーの実装クラスを取得するための
     * &lt;bean&gt;要素のid属性に使用されるデフォルトid。
     */
    public static final String DEFAULT_BLOCKAGE_BEAN_ID
        = "blockageController";
    
    /**
     * 業務閉塞コントローラの生成失敗を示すエラーコード。
     */
    private static final String BLOCKAGE_CONTROLLER_ERROR 
        = "errors.blockage.controller";

    /**
     * 業務閉塞処理を委譲するコントローラクラス。
     */
    private static final Class BLOCKAGE_CONTROLLER_CLASS 
        = BlockageController.class;
   
    /**
     * ログクラス。
     */
    private static Log log 
       = LogFactory
           .getLog(BlockageControlFilter.class);

    /**
     * BlockageControllerインスタンス。
     */
    protected static BlockageController controller = null;

    /**
     * BlockageControllerインスタンスを戻す。
     *
     * @return このフィルタに設定されているBlockageControllerインスタンス
     */
    public static BlockageController getBlockageController() {
        return controller;
    }
    
    /**
     * フィルタがサービス開始状態になる際に、コンテナによって呼び出される。 
     * 
     * コンテナは、Filterをインスタンス化した後に、initメソッドを
     * 1 回だけ呼び出す。<br>
     * Filterにフィルタ処理作業を実行するように要求するには、
     * init メソッドが正常に 終了していなければならない。
     * initメソッドが 次のいずれかの状態の場合、コンテナは
     * Filterをサービス状態にできない。<br>
     * <ul>
     *  <li>ServletException をスローした。</li>
     *  <li>コンテナによって定義された時間内に、復帰しない。</li>
     *  <li>設定されたコントローラの実装クラスが存在しない、 
     *      または設定異常時。</li>
     * </ul>
     * 
     * @param config FilterConfigインスタンス。
     * 
     * @throws javax.servlet.ServletException 初期化異常時にスローされる例外。
     *             
     * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
     * @see jp.terasoluna.fw.web.thin.AbstractControlFilter
     */
    @Override
    public void init(FilterConfig config) throws ServletException {
        super.init(config);
        if (controller == null) {
            controller = getController();
        }
    }

    /**
     * 業務閉塞チェックを行う。
     *
     * @param req HTTPリクエスト
     * @param res HTTPレスポンス
     * @param chain フィルタチェーン
     *
     * @throws IOException I/Oエラー
     * @throws ServletException サーブレット例外
     *
     * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
     */
    @Override
    public void doFilter(ServletRequest req,
                         ServletResponse res,
                         FilterChain chain)
            throws IOException, 
                   ServletException {

        // リクエストがフィルタを通過したかどうかを判定。
        if (req.getAttribute(BLOCKAGE_THRU_KEY) == null) {

            // フィルタの通過情報をセット
            req.setAttribute(BLOCKAGE_THRU_KEY, "true");

            if (controller.isCheckRequired(req)) {
                // 業務閉塞チェック
                if (controller
                    .isBlockaded(RequestUtil.getPathInfo(req), req)) {
                    if (log.isDebugEnabled()) {
                        log.debug("isBlockaded() failed.");
                    }
                    throw new BlockageException();
                }
            }
        }

        // 次のフィルタまたはサーブレットへ
        chain.doFilter(req, res);

    }

    /**
     * アクセス制御を行うクラスが実装すべきインタフェースを返す。
     * 
     * @return このフィルタで使用するコントローラのクラス
     */
    @Override
    protected Class getControllerClass() {
        return BLOCKAGE_CONTROLLER_CLASS;
    }

    /**
     * コントローラの生成失敗を示すエラーコードを返す。
     * 
     * @return エラーコード
     */
    @Override
    protected String getErrorCode() {
        return BLOCKAGE_CONTROLLER_ERROR;
    }

    /**
     * DIコンテナからコントローラを取得する際のデフォルトのidを返す。
     * 
     * @return デフォルトのid属性値
     */
    @Override
    public String getDefaultControllerBeanId() {
        return DEFAULT_BLOCKAGE_BEAN_ID;
    }
    
}
