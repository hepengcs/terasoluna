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
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import jp.terasoluna.fw.util.PropertyUtil;
import jp.terasoluna.fw.util.StringUtil;
import jp.terasoluna.fw.web.RequestUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 拡張子チェックを行う。
 *
 * <p>
 * 指定された禁止拡張子をもつパスへのアクセス要求に対しては、
 * SC_NOT_FOUND(404)エラーを返す。これにより、ファイル
 * への直接アクセスを禁止する。<br>
 * 禁止拡張子へのアクセス制限を行う場合でそのチェック対象からはずしたい
 * 特別なパスがあれば、プロパティファイルにrestrictionEscape.
 * というプレフィクスをつけた数字をキーとして定義することでチェック非対象の
 * パスを1から複数定義できる。<br>
 * また、直接アクセス禁止対象の拡張子は
 * access.control.prohibited.extension. というプレフィクスをつけたキー名で
 * 1から指定すること。
 * </p>
 *
 * <h5>プロパティファイル設定例</h5>
 * <p>
 * <code><pre>
 * # 拡張子制限チェック対象外にするパスを1から順に指定する。
 * restrictionEscape.1=/sample/logon/index.jsp
 * restrictionEscape.2=/sample/error/error.jsp
 *
 * # 拡張子ごとの直接アクセス制限チェック対象の拡張子を1から順に指定する。
 * access.control.prohibited.extension.1=.jsp
 * access.control.prohibited.extension.2=.csv
 * access.control.prohibited.extension.3=.pdf
 * </pre></code>
 * </p>
 * <h5>使用方法</h5>
 * <p>
 * この機能を使用するには デプロイメントディスクリプタ（web.xml）に
 * 以下のように設定する。<br>
 * <br>
 * <code><pre>
 * &lt;filter&gt;
 *   &lt;filter-name&gt;extensionFilter&lt;/filter-name&gt;
 *   &lt;filter-class&gt;
 *     jp.terasoluna.fw.web.thin.ExtensionFilter
 *   &lt;/filter-class&gt;
 * &lt;/filter&gt;
 *
 * &lt;filter-mapping&gt;
 *   &lt;filter-name&gt;extensionFilter&lt;/filter-name&gt;
 *   &lt;url-pattern&gt;/*&lt;/url-pattern&gt;
 * &lt;/filter-mapping&gt;
 * </pre></code>
 * </p>
 */
public class ExtensionFilter implements Filter {

    /**
     * リクエストがフィルタを通過したことを示すリクエスト属性のキー。
     */
    public static final String EXTENSION_THRU_KEY = "EXTENSION_THRU_KEY";

    /**
     * ApplicationResourceから取得する、拡張子制限チェックを対象外にする
     * パスのキーにつけるプレフィックス。
     */
    public static final String RESTRICTION_ESCAPE_PREFIX = "restrictionEscape.";

    /**
     * ログクラス。
     */
    private static Log log
        = LogFactory.getLog(ExtensionFilter.class);

    /**
     * ApplicationResourceから取得する、直接アクセスを禁止する拡張子の
     * キーにつけるプレフィクス。
     */
    private static final String PROHIBITED_EXTENSION_PREFIX 
        = "access.control.prohibited.extension.";

    /**
     * Webブラウザからの直接アクセスを禁止する拡張子のリスト。
     */
    private static List<String> prohibitedExtensionList 
        = new ArrayList<String>();

    /**
     * 拡張子制限チェックの対象外となるパスのリスト。
     */
    private static List<String> restrictionEscapePaths 
        = new ArrayList<String>();

    /**
     * フィルタがサービス開始状態になる際に、コンテナによって呼び出される。
     *  
     * コンテナは、Filterをインスタンス化した後に、init メソッドを
     * 1 回だけ呼び出す。<br>
     * Filterにフィルタ処理作業を実行するように要求するには、
     * init メソッドが正常に 終了していなければならない。
     * initメソッドが 次のいずれかの状態の場合、コンテナは
     * Filterをサービス状態にできない。<br>
     * <ul>
     *  <li>ServletException をスローした。 </li>
     *  <li>コンテナによって定義された時間内に、復帰しない。</li>
     * </ul>
     * 
     * @param config FilterConfigインスタンス。
     * 
     * @throws javax.servlet.ServletException 初期化異常時にスローされる例外。
     *             
     * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
     */
    public void init(FilterConfig config) throws ServletException {

        //直接アクセスを禁止する拡張子のリストを取得する。
        for (int i = 1;; i++) {
            String extension 
                = PropertyUtil.getProperty(PROHIBITED_EXTENSION_PREFIX + i);
            
            // 直接アクセス禁止拡張子がなければ、あるいは数字が途切れれば終了
            if (extension == null) {
                break;
            }
            // 拡張子が.で始まっていないときは.を付加する
            if (!extension.startsWith(".")) {
                extension = "." + extension;
            }
            if (log.isDebugEnabled()) {
                log.debug("prohibitedExtension:" + extension);
            }
            prohibitedExtensionList.add(extension);
        }
        //直接アクセス禁止処理を行わないパスを取得する。
        for (int i = 1;; i++) {
            String extensionCheckEscapePath 
                = PropertyUtil.getProperty(RESTRICTION_ESCAPE_PREFIX + i);
            
            // 直接アクセスチェックをしないパスがない、
            // あるいは数字が途切れれば終了
            if (extensionCheckEscapePath == null) {
                break;
            }
            if (log.isDebugEnabled()) {
                log.debug("extensionCheckEscapePath:["
                          + extensionCheckEscapePath + "]");
            }
            restrictionEscapePaths.add(extensionCheckEscapePath);
        }
    }


    /**
     * 拡張子チェックを行う。
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
    public void doFilter(ServletRequest req,
                         ServletResponse res,
                         FilterChain chain)
            throws IOException, ServletException {

        //リクエストがフィルタを通過したかどうかを判定。
        if (req.getAttribute(EXTENSION_THRU_KEY) == null) {

            //フィルタの通過情報をセット
            req.setAttribute(EXTENSION_THRU_KEY, "true");

            //リクエストパスが拡張子チェックを行わないパスリストに
            //一致していた場合は処理を終了
            String pathInfo = RequestUtil.getPathInfo(req);
            if (pathInfo != null
                && !restrictionEscapePaths.contains(pathInfo)) {

                //拡張子チェックを行う
                //リクエストパスの拡張子を取得する。
                String extension = StringUtil.getExtension(pathInfo);
                if (prohibitedExtensionList.contains(extension)) {

                    if (log.isDebugEnabled()) {
                        log.debug("requestURI[" + pathInfo
                                  + "] has prohibited extension");
                    }

                    // HTTPエラー404を返す
                    ((HttpServletResponse) res)
                    .sendError(HttpServletResponse.SC_NOT_FOUND);
                    return; // 以降は実行しない
                }
            }
        }

        // 次のフィルタまたはサーブレットへ
        chain.doFilter(req, res);
    }

    /**
     * フィルタ処理時に呼び出される。<br>
     * このクラスでは処理は行なわない。
     * 
     * @see javax.servlet.Filter#destroy()
     */
    public void destroy() {
        // 特になし
    }

}
