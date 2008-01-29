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

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import jp.terasoluna.fw.exception.SystemException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanNotOfRequiredTypeException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * beanとして実装されたコントローラを用いてアクセス制御を行う抽象クラス。
 * 
 * <p>
 * このクラスは、DIコンテナから取得したbeanを用いてアクセス制御を行うクラスの
 * 上位クラスである。<br>
 * </p>
 * 
 * <h5>使用方法</h5>
 * <p>
 * このクラスの実装クラスを使用するには デプロイメントディスクリプタ（web.xml）
 * とBean定義ファイルに以下のように設定する。
 * このとき、Bean定義ファイルに定義するid属性が、
 * sampleXxxControllerである&lt;bean&gt;要素の
 * class属性には、各フィルタが指定するインタフェース（ここではxxxController）を
 * 実装したクラス（ここではSampleXxxController）を設定する。<br>
 * ※Xxxは各機能を提供するFilterによって変わる。<br>
 *</p>
 * デプロイメントディスクリプタ（web.xml）<pre><code>
 * &lt;filter&gt;
 *   &lt;filter-name&gt;
 *     xxxControlFilter
 *   &lt;/filter-name&gt;
 *   &lt;filter-class&gt;
 *     jp.terasoluna.sample.XxxControlFilter
 *   &lt;/filter-class&gt;
 *   &lt;init-param&gt;
 *     &lt;param-name&gt;controller&lt;/param-name&gt;
 *     &lt;param-value&gt;
 *       "sampleXxxController"
 *     &lt;/param-value&gt;
 *   &lt;/init-param&gt;
 * &lt;/filter&gt;
 *
 * &lt;filter-mapping&gt;
 *   &lt;filter-name&gt;xxxControlFilter&lt;/filter-name&gt;
 *   &lt;url-pattern&gt;/*&lt;/url-pattern&gt;
 * &lt;/filter-mapping&gt;
 * 
 * &lt;error-page&gt;
 *   &lt;exception-type&gt;
 *     jp.terasoluna.sample.XxxException
 *   &lt;/exception-type&gt;
 *   &lt;location&gt;/XxxError.jsp&lt;/location&gt;
 * &lt;/error-page&gt;
 * </pre></code>
 * 
 * Bean定義ファイル
 * <pre><code>
 * &lt;bean id=&quot;sampleXxxController&quot;
 *       class=&quot;jp.terasoluna…SampleXxxController&quot; /&gt;
 * </code></pre>
 * 
 * <p>
 * なお、フィルタによっては、Bean定義ファイルに定義する&lt;bean&gt;要素の
 * id属性にデフォルト値を用意している。
 * その場合は、デプロイメントディスクリプタ（web.xml）内の&lt;filter&gt;要素から
 * &lt;init-param&gt;要素を省略することができる。
 * </p>
 * 
 * @param <E> コントローラクラスを指定する。
 * 
 */
public abstract class AbstractControlFilter<E> implements Filter {
   
  
    /**
     * ログクラス。
     */
    private static Log log 
        = LogFactory.getLog(AbstractControlFilter.class);

    /**
     * フィルタ設定情報。
     */
    protected FilterConfig config = null;
   
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
     */
    public void init(FilterConfig config) throws ServletException {
        this.setConfig(config);
    }
    
    /**
     * フィルタ設定情報を設定する。
     * 
     * @param config フィルタ設定情報
     */
    protected void setConfig(FilterConfig config) {
        if (log.isDebugEnabled()) {
            log.debug("setConfig() called.");
        }
        this.config = config;
    }
    
    /**
     * DIコンテナからコントローラインスタンスを取得してくる。
     * 
     * @return E 取得したコントローラインスタンス
     */
    @SuppressWarnings("unchecked")
    protected E getController() {

        if (log.isDebugEnabled()) {
            log.debug("setController() called.");
        }
        
        // WebApplicationContext取得
        WebApplicationContext wac 
            = WebApplicationContextUtils
                .getWebApplicationContext(config.getServletContext());


        // フィルタの初期パラメータとしてBean定義ファイルの
        // id属性が渡されているか
        String controllerId = config.getInitParameter("controller");
        if (controllerId == null || "".equals(controllerId)) {
            if (log.isDebugEnabled()) {
                log.debug("init parameter 'controller' isn't defined or "
                          + "empty");
            }
            controllerId = getDefaultControllerBeanId();
        }
        
        // コントローラとして定義されたbean idを表示
        if (log.isDebugEnabled()) {
            log.debug("controller bean id = \"" + controllerId + "\"");
        }
        
        // DIコンテナからコントローラインスタンス取得
        E controller = null;
        try {    
            controller = (E) wac.getBean(controllerId, getControllerClass());
        } catch (NoSuchBeanDefinitionException e) {
            // Bean定義ファイルに指定されているBeanが定義されていない場合
            log.error("not found " + controllerId + ". "
                      + "controller bean not defined in Beans definition file.",
                      e);
            throw new SystemException(e, getErrorCode());
        } catch (BeanNotOfRequiredTypeException e) {
            // Bean定義ファイルに指定されているBeanが、指定されているクラスか
            // その子孫クラスではない場合
            log.error("controller not implemented " 
                      + getControllerClass().toString() + ".",
                      e);
            throw new SystemException(e, getErrorCode());
        } catch (BeansException e) {
            // インスタンス生成に失敗した場合
            log.error("bean generation failed.", e);
            throw new SystemException(e, getErrorCode());
        }
        
        return controller;
    }
    
    /**
     * アクセス制御を行うクラスが実装すべきインタフェースを返す。
     * 
     * @return このフィルタで使用するコントローラのクラス
     */
    protected abstract Class getControllerClass();
    
    /**
     * コントローラの生成失敗を示すエラーコードを返す。
     * 
     * @return エラーコード
     */
    protected abstract String getErrorCode();

    /**
     * DIコンテナからコントローラを取得する際のデフォルトのidを返す。
     * 
     * @return デフォルトのid属性値
     */
    public abstract String getDefaultControllerBeanId();

    /**
     * アクセス制御を行う。
     * 
     * @param req HTTPリクエスト
     * @param res HTTPレスポンス
     * @param chain フィルタチェーン
     * 
     * @throws IOException I/Oエラー
     * @throws ServletException サーブレット例外
     * 
     * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, 
     *                                    javax.servlet.ServletResponse,
     *                                    javax.servlet.FilterChain)
     */
    public abstract void doFilter(ServletRequest req, 
                         ServletResponse res,
                         FilterChain chain) 
            throws IOException, 
                   ServletException;


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
