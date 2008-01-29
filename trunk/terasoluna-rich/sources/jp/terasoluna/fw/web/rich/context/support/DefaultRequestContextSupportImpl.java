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

package jp.terasoluna.fw.web.rich.context.support;

import javax.servlet.http.HttpServletRequest;

import jp.terasoluna.fw.web.rich.context.RequestContext;

/**
 * 制御情報を扱うための補助ロジックのデフォルト実装クラス。
 * 
 * <p>
 * リクエストヘッダ"requestName"に設定してある文字列をリクエスト名とする。
 * （ヘッダ名はBean定義ファイルの設定により変更可能。）
 * </p>
 * 
 * <p>
 * 本クラスを利用する場合、Bean定義を行うこと。
 * また、AbstractRequestContextSupportのプロパティに加えて、以下のプロパティを設定することが可能である。
 * </p>
 *
 * <p>
 *   <table border="1" CELLPADDING="8">
 *     <th>属性名</th>
 *     <th>必須</th>
 *     <th>説明</th>
 *     
 *     <tr>
 *       <td align=center><b>requestNameHeaderKey</b></td>
 *       <td>×</td>
 *       <td>リクエスト名を保持するリクエストヘッダ名。
 *       デフォルトのヘッダ名”requestName”以外のヘッダからリクエスト名を取得したい場合のみ設定すること。</td>
 *     </tr>
 *  
 *  </table>
 * </p>
 * 
 * <p>
 * 【<code>Bean定義ファイル</code>の設定例】<br>
 * <code><pre>
 *   &lt;bean id="ctxSupport"
 *       class="jp.terasoluna.fw.web.rich.context.support.DefaultRequestContextSupportImpl"&gt;
 *     &lt;property name="ctxClass"&gt;
 *       &lt;value&gt;jp.terasoluna.fw.web.rich.context.RequestContext&lt;/value&gt;
 *     &lt;/property&gt;
 *   &lt;/bean&gt;
 * </pre></code>
 * </p>
 * 
 */
public class DefaultRequestContextSupportImpl extends
        AbstractRequestContextSupport {
    
    /**
     * リクエスト名を保持するリクエストヘッダ名。
     */
    protected String requestNameHeaderKey = DEFAULT_REQUEST_NAME_HEADER_KEY;
    
    /**
     * リクエスト名を保持するリクエストヘッダ名のデフォルト値。
     */
    private static final String DEFAULT_REQUEST_NAME_HEADER_KEY 
    	= "requestName";
    
    /**
     * リクエスト名を保持するリクエストヘッダ名を設定する。
     * @param requestNameHeaderKey リクエスト名を保持するリクエストヘッダ名。
     */
    public void setRequestNameHeaderKey(String requestNameHeaderKey) {
        this.requestNameHeaderKey = requestNameHeaderKey;
    }

    /**
     * 制御情報を生成・初期化して返却する。
     * @param request HTTPリクエスト
     * @return 生成した空の制御情報。
     */
    @Override
    protected RequestContext doGenerateContext(
            HttpServletRequest request) {
        RequestContext ctx = new RequestContext();
        ctx.setRequestName(request.getHeader(this.requestNameHeaderKey));
        return ctx;
    }
}
