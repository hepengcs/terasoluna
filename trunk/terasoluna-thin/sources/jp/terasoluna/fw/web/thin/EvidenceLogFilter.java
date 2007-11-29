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
import java.util.Iterator;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * エビデンスログを出力処理を行う。
 * 
 * <p>
 * エビデンスログ出力とは、リクエストパラメタ情報をログ出力することである。
 * </p>
 * <h5>使用方法</h5>
 * この機能を使用するにはデプロイメントディスクリプタ（web.xml）に以下のように
 * 設定する。<br>
 * <br>
 * <code><pre>
 * &lt;filter&gt;
 *   &lt;filter-name&gt;evidenceLogFilter&lt;/filter-name&gt;
 *   &lt;filter-class&gt;
 *     jp.terasoluna.fw.web.thin.EvidenceLogFilter
 *   &lt;/filter-class&gt;
 * &lt;/filter&gt;
 *
 * &lt;filter-mapping&gt;
 *   &lt;filter-name&gt;evidenceLogFilter&lt;/filter-name&gt;
 *   &lt;url-pattern&gt;/*&lt;/url-pattern&gt;
 * &lt;/filter-mapping&gt;
 * </pre></code>
 * 
 *
 */
public class EvidenceLogFilter implements Filter {
    
    /**
     * リクエストがフィルターを通過したことを示すリクエスト属性のキー。
     */
    public static final String EVIDENCELOG_THRU_KEY = "EVIDENCELOG_THRU_KEY";
    
    /**
     * ログクラス
     */
    private static Log log
        = LogFactory.getLog(EvidenceLogFilter.class);

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
     * <br>
     * ※このクラスでは処理は行わない。
     * 
     * @param config FilterConfigインスタンス。
     * 
     * @throws javax.servlet.ServletException 初期化異常時にスローされる例外。
     *             
     * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
     */
    public void init(FilterConfig config) throws ServletException {
        //特になし
    }
    
    /**
     * エビデンスログを出力する。
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
        if (req.getAttribute(EVIDENCELOG_THRU_KEY) == null) {

            req.setAttribute(EVIDENCELOG_THRU_KEY, "true");

            evidenceLog("--------------------------------------------");

            // リクエストURIを出力
            evidenceLog(
                "RequestURI = " + ((HttpServletRequest) req).getRequestURI());

            // パラメータ一覧を出力
            evidenceLog("Parameters = {");
            Map paramMap = ((HttpServletRequest) req).getParameterMap();
            Iterator iter = paramMap.keySet().iterator();
            while (iter.hasNext()) {
                String key = (String) iter.next();
                Object value = paramMap.get(key);
                if (value == null) {
                    evidenceLog("  " + key + " = null");
                } else if (value.getClass().isArray()) {
                    Object[] values = (Object[]) value;
                    for (int i = 0; i < values.length; i++) {
                        String valueView = "null";
                        if (values[i] != null) {
                            valueView = values[i].toString();
                        }
                        evidenceLog("  " + key + "[" + i + "] = " + valueView);
                    }
                } else {
                    evidenceLog("  " + key + " = " + value.toString());
                }
            }
            evidenceLog("}");

            evidenceLog("--------------------------------------------");

        }

        // 次のフィルタまたはサーブレットへ
        chain.doFilter(req, res);
    }


    /**
     * エビデンスログを出力する。
     *
     * @param s ログに出力する文字列
     */
    private void evidenceLog(String s) {
        if (log.isDebugEnabled()) {
            log.debug("**** EVIDENCE ***: " + s);
        }
    }
    
    /**
     * フィルタ処理時に呼び出される。<br>
     * このクラスでは処理は行わない。
     * 
     * @see javax.servlet.Filter#destroy()
     */
    public void destroy() {
        // 特になし
    }

}
