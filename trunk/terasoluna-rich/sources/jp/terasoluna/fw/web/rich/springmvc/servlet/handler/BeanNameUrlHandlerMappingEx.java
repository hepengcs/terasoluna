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

package jp.terasoluna.fw.web.rich.springmvc.servlet.handler;

import javax.servlet.http.HttpServletRequest;
import jp.terasoluna.fw.web.rich.context.support.RequestContextSupport;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.web.servlet.handler.BeanNameUrlHandlerMapping;

/**
 * リクエスト名と実行するリクエストコントローラBean定義のマッピングを行うハンドラ。
 * 
 * <p>
 * DispacherServletより実行され、
 * 制御情報のリクエスト名とあらかじめ定義した接頭辞・接尾辞を結合した文字列をBean名として、
 * DIコンテナよりリクエストコントローラの取得を行い、返却する。
 * DispacherServletは、返却されたリクエストコントローラを実行する。
 * </p>
 * 
 * <p>
 * 本クラスを利用する場合、本クラスのBean定義を行うこと。
 * また、以下のプロパティを必ず設定すること。
 * </p>
 * 
 * <p>
 *   <table border="1" CELLPADDING="8">
 *     <th>属性名</th>
 *     <th>説明</th>
 * 
 *     <tr>
 *       <td align=center><b>ctxSupport</b></td>
 *       <td>リクエスト名を取得するためのサポートロジッククラス。</td>
 *     </tr>
 *     
 *     <tr>
 *       <td align=center><b>prefix</b></td>
 *       <td>リクエスト名に付与する接頭辞。</td>
 *     </tr>
 * 
 *     <tr>
 *       <td align=center><b>suffix</b></td>
 *       <td>リクエスト名に付与する接尾辞。</td>
 *     </tr>
 *     
 *     <tr>
 *       <td align=center><b>defaultHandler</b></td>
 *       <td>リクエスト名に対応するコントローラが存在しない場合のコントローラ。</td>
 *     </tr>
 * 
 *  </table>
 * 
 * </p>
 * 
 * <p>
 * 【<code>Bean定義ファイル</code>の設定例】<br>
 * <code><pre>
 *   &lt;bean id=&quot;defaultHandlerMapping&quot;
 *       class=&quot;jp.terasoluna.fw.web.rich.springmvc.servlet.handler.BeanNameUrlHandlerMappingEx&quot;&gt;
 *     &lt;property name=&quot;ctxSupport&quot; ref=&quot;ctxSupport&quot;/&gt;
 *     &lt;property name=&quot;prefix&quot; value=&quot;/secure/blogic/&quot;/&gt;
 *     &lt;property name=&quot;suffix&quot; value=&quot;.do&quot;/&gt;
 *     &lt;property name=&quot;defaultHandler&quot; ref=&quot;unknownRequestNameController&quot;/&gt;
 *   &lt;/bean&gt;
 *   ※ ctxSupportは、 制御情報を扱うサポートロジックのBean定義。
 *   ※ unknownRequestNameControllerは、リクエスト名に対応するコントローラが存在しない場合に実行されるコントローラのBean定義。
 * </pre></code>
 * </p>
 * 
 * <p>
 * 上記の設定を行った場合、
 * たとえば、リクエスト名が「sum」だった場合、
 * DIコンテナより「/secure/blogic/sum.do」という名称のBeanを取得し、返却する。
 * ただし、Beanを取得できなかった(存在しなかった)場合は、
 * 「unknownRequestNameController」という名称の
 * BeanをDIコンテナより取得し、返却する。
 * （Beanを取得できなかった場合の詳細は、UnkownRequestNameControllerを参照のこと。）
 * </p>
 * 
 */
public class BeanNameUrlHandlerMappingEx extends BeanNameUrlHandlerMapping 
    implements InitializingBean {
    /**
     * ログクラス。
     */
    private static Log log = 
        LogFactory.getLog(BeanNameUrlHandlerMappingEx.class);
    
    /**
     * 制御情報サポートロジッククラス。
     */
    protected RequestContextSupport ctxSupport = null;
    
    /**
     * リクエスト名に付与する接頭辞。
     */
    protected String prefix = "";

    /**
     * リクエスト名に付与する接尾辞。
     */
    protected String suffix = "";
    
    /**
     * リクエスト名に付与する接頭辞を設定する。
     * @param prefix リクエスト名に付与する接頭辞。
     */
    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    /**
     * リクエスト名に付与する接尾辞を設定する。
     * @param suffix リクエスト名に付与する接尾辞。
     */
    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    /**
     * 制御情報サポートロジッククラスを設定する。
     * @param ctxSupport 制御情報サポートロジッククラス
     */
    public void setCtxSupport(
            RequestContextSupport ctxSupport) {
        this.ctxSupport = ctxSupport;
    }
    
    /**
     * リクエスト名をもとに、リクエストコントローラのインスタンスをルックアップする。
     * @param urlPath URLパス
     * @param request HttpServletRequest
     * @return リクエストコントローラのインスタンス
     */
    @Override
    protected Object lookupHandler(String urlPath, HttpServletRequest request) {
        String requestName = ctxSupport.getRequestName();
        String newUrlPath = prefix + requestName + suffix;
        
        // コントローラの取得
        Object handler = super.lookupHandler(newUrlPath, request); 
        
        if (handler == null) {
            log.error("Controller is not found. " + "BeanName:'" 
                    + newUrlPath + "'");
        }
        return handler; 
    }

    /**
     * 本クラスがインスタンス化された直後に呼ばれるメソッド。
     * コンテキスト取得クラスのNullチェックを行う。
     */
    public void afterPropertiesSet() {
        if (ctxSupport == null) {
            log.error("BeanNameUrlHandlerMappingEx must be set ctxSupport.");
            throw new IllegalStateException(
                    "BeanNameUrlHandlerMappingEx must be set ctxSupport.");
        }
    }
}
