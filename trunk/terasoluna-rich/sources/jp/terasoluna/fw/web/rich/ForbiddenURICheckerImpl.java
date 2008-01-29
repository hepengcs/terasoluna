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

package jp.terasoluna.fw.web.rich;

import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 文字列が許可するURIか判定するチェッカのデフォルト実装クラス。
 * 
 * <p>
 * 引き数の文字列が、
 * あらかじめ設定された許可文字列のリストにあてはまるがどうかをチェックする。
 * </p>
 * 
 * <p>
 * 本クラスでは、システムの要件を満たせない場合のみ、
 * 本クラスを使用せずForbiddenURICheckerの実装クラスを新規作成し、
 * チェックメソッドを実装すること。
 * 詳細は、ForbiddenURICheckerを参照すること。
 * </p>
 * 
 * <p>
 * 本クラスを利用する場合、Bean定義すること。
 * 定義方法は、ForbiddenURICheckerを参照すること。
 * また、以下のプロパティを必ず設定すること。
 * </p>
 * <p>
 *   <table border="1" CELLPADDING="8">
 *     <th>属性名</th>
 *     <th>必須</th>
 *     <th>説明</th>
 *     
 *     <tr>
 *       <td align=center><b>allowedURISet</b></td>
 *       <td>○</td>
 *       <td>許可するURI(セット形式)</td>
 *     </tr>
 *  </table>
 * </p>
 * 
 * <p>
 * 【<code>Bean定義ファイル</code>の設定例】<br>
 * <code><pre>
 * &lt;bean id="forbiddenURIChecker"
 *      class="jp.terasoluna.fw.web.rich.ForbiddenURICheckerImpl"&gt;
 *   &lt;property name="allowedURISet"&gt;
 *     &lt;set&gt;
 *       &lt;value&gt;/secure/blogic.do&lt;/value&gt;
 *     &lt;/set&gt;
 *   &lt;/property&gt;
 * &lt;/bean&gt;
 * </pre></code>
 * </p>
 * 
 */
public class ForbiddenURICheckerImpl implements ForbiddenURIChecker {
    /**
     * ログ。
     */
    private static Log logger
        = LogFactory.getLog(ForbiddenURICheckerImpl.class);

    /**
     * 許可するURIのセット。
     */
    private Set<String> allowedURISet = null;
    
    /**
     * 許可するURIのリストを設定する。
     * 
     * @param allowedURISet 許可するURIのリスト
     */
    public void setAllowedURISet(Set<String> allowedURISet) {
        this.allowedURISet = allowedURISet;
    }
    
    /**
     * 許可されているURIかチェックする。
     * 
     * @param requestURI チェック対象のURI
     * @return チェック結果（許可されていればtrue）
     */
    public boolean isAllowedURI(String requestURI) {
        if (allowedURISet == null) {
            if (logger.isDebugEnabled()) {
                logger.debug("allowedURISet is null.");
            }
            return false;
        }
        
        // URIの許否判定処理
        return allowedURISet.contains(requestURI);
    }
}
